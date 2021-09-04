/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.beans.CAMGraphs;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
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
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class FileMonitor_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   63 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   66 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   67 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   68 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   75 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   80 */     ArrayList list = null;
/*   81 */     StringBuffer sbf = new StringBuffer();
/*   82 */     ManagedApplication mo = new ManagedApplication();
/*   83 */     if (distinct)
/*      */     {
/*   85 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   89 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   92 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   94 */       ArrayList row = (ArrayList)list.get(i);
/*   95 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   96 */       if (distinct) {
/*   97 */         sbf.append(row.get(0));
/*      */       } else
/*   99 */         sbf.append(row.get(1));
/*  100 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  103 */     return sbf.toString(); }
/*      */   
/*  105 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  108 */     if (severity == null)
/*      */     {
/*  110 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  112 */     if (severity.equals("5"))
/*      */     {
/*  114 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  116 */     if (severity.equals("1"))
/*      */     {
/*  118 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  123 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  130 */     if (severity == null)
/*      */     {
/*  132 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  134 */     if (severity.equals("1"))
/*      */     {
/*  136 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  138 */     if (severity.equals("4"))
/*      */     {
/*  140 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  142 */     if (severity.equals("5"))
/*      */     {
/*  144 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  149 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  155 */     if (severity == null)
/*      */     {
/*  157 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  159 */     if (severity.equals("5"))
/*      */     {
/*  161 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  163 */     if (severity.equals("1"))
/*      */     {
/*  165 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  169 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  175 */     if (severity == null)
/*      */     {
/*  177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  179 */     if (severity.equals("1"))
/*      */     {
/*  181 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  183 */     if (severity.equals("4"))
/*      */     {
/*  185 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  187 */     if (severity.equals("5"))
/*      */     {
/*  189 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  193 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  199 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  205 */     if (severity == 5)
/*      */     {
/*  207 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  209 */     if (severity == 1)
/*      */     {
/*  211 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  216 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  222 */     if (severity == null)
/*      */     {
/*  224 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  226 */     if (severity.equals("5"))
/*      */     {
/*  228 */       if (isAvailability) {
/*  229 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  232 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  235 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  237 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  239 */     if (severity.equals("1"))
/*      */     {
/*  241 */       if (isAvailability) {
/*  242 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  245 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  252 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  259 */     if (severity == null)
/*      */     {
/*  261 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  263 */     if (severity.equals("5"))
/*      */     {
/*  265 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  267 */     if (severity.equals("4"))
/*      */     {
/*  269 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  271 */     if (severity.equals("1"))
/*      */     {
/*  273 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  278 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  284 */     if (severity == null)
/*      */     {
/*  286 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  288 */     if (severity.equals("5"))
/*      */     {
/*  290 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  292 */     if (severity.equals("4"))
/*      */     {
/*  294 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  296 */     if (severity.equals("1"))
/*      */     {
/*  298 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  303 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  310 */     if (severity == null)
/*      */     {
/*  312 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  314 */     if (severity.equals("5"))
/*      */     {
/*  316 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  318 */     if (severity.equals("4"))
/*      */     {
/*  320 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  322 */     if (severity.equals("1"))
/*      */     {
/*  324 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  329 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  337 */     StringBuffer out = new StringBuffer();
/*  338 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  339 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  340 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  341 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  342 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  343 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  344 */     out.append("</tr>");
/*  345 */     out.append("</form></table>");
/*  346 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  353 */     if (val == null)
/*      */     {
/*  355 */       return "-";
/*      */     }
/*      */     
/*  358 */     String ret = FormatUtil.formatNumber(val);
/*  359 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  360 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  363 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  367 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  375 */     StringBuffer out = new StringBuffer();
/*  376 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  377 */     out.append("<tr>");
/*  378 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  380 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  382 */     out.append("</tr>");
/*  383 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  387 */       if (j % 2 == 0)
/*      */       {
/*  389 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  393 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  396 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  398 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  401 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  405 */       out.append("</tr>");
/*      */     }
/*  407 */     out.append("</table>");
/*  408 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  409 */     out.append("<tr>");
/*  410 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  411 */     out.append("</tr>");
/*  412 */     out.append("</table>");
/*  413 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  419 */     StringBuffer out = new StringBuffer();
/*  420 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  421 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  422 */     out.append("<tr>");
/*  423 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  424 */     out.append("<tr>");
/*  425 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  426 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  427 */     out.append("</tr>");
/*  428 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  431 */       out.append("<tr>");
/*  432 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  433 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  434 */       out.append("</tr>");
/*      */     }
/*      */     
/*  437 */     out.append("</table>");
/*  438 */     out.append("</table>");
/*  439 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  444 */     if (severity.equals("0"))
/*      */     {
/*  446 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  450 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  457 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  470 */     StringBuffer out = new StringBuffer();
/*  471 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  472 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  474 */       out.append("<tr>");
/*  475 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  476 */       out.append("</tr>");
/*      */       
/*      */ 
/*  479 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  481 */         String borderclass = "";
/*      */         
/*      */ 
/*  484 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  486 */         out.append("<tr>");
/*      */         
/*  488 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  489 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  490 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  496 */     out.append("</table><br>");
/*  497 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  498 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  500 */       List sLinks = secondLevelOfLinks[0];
/*  501 */       List sText = secondLevelOfLinks[1];
/*  502 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  505 */         out.append("<tr>");
/*  506 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  507 */         out.append("</tr>");
/*  508 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  510 */           String borderclass = "";
/*      */           
/*      */ 
/*  513 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  515 */           out.append("<tr>");
/*      */           
/*  517 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  518 */           if (sLinks.get(i).toString().length() == 0) {
/*  519 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  522 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  524 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  528 */     out.append("</table>");
/*  529 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  536 */     StringBuffer out = new StringBuffer();
/*  537 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  538 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  540 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  542 */         out.append("<tr>");
/*  543 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  544 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  548 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  550 */           String borderclass = "";
/*      */           
/*      */ 
/*  553 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  555 */           out.append("<tr>");
/*      */           
/*  557 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  558 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  559 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  562 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  565 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  570 */     out.append("</table><br>");
/*  571 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  572 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  574 */       List sLinks = secondLevelOfLinks[0];
/*  575 */       List sText = secondLevelOfLinks[1];
/*  576 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  579 */         out.append("<tr>");
/*  580 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  581 */         out.append("</tr>");
/*  582 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  584 */           String borderclass = "";
/*      */           
/*      */ 
/*  587 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  589 */           out.append("<tr>");
/*      */           
/*  591 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  592 */           if (sLinks.get(i).toString().length() == 0) {
/*  593 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  596 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  598 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  602 */     out.append("</table>");
/*  603 */     return out.toString();
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
/*  616 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  622 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  625 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  628 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  631 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  634 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  637 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  645 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  650 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  655 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  660 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  665 */     if (val != null)
/*      */     {
/*  667 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  671 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  676 */     if (val == null) {
/*  677 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  681 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  686 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  692 */     if (val != null)
/*      */     {
/*  694 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  698 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  704 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  709 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  713 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  718 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  723 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  728 */     String hostaddress = "";
/*  729 */     String ip = request.getHeader("x-forwarded-for");
/*  730 */     if (ip == null)
/*  731 */       ip = request.getRemoteAddr();
/*  732 */     InetAddress add = null;
/*  733 */     if (ip.equals("127.0.0.1")) {
/*  734 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  738 */       add = InetAddress.getByName(ip);
/*      */     }
/*  740 */     hostaddress = add.getHostName();
/*  741 */     if (hostaddress.indexOf('.') != -1) {
/*  742 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  743 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  747 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  752 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  758 */     if (severity == null)
/*      */     {
/*  760 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  762 */     if (severity.equals("5"))
/*      */     {
/*  764 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  766 */     if (severity.equals("1"))
/*      */     {
/*  768 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  773 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  778 */     ResultSet set = null;
/*  779 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  780 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  782 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  783 */       if (set.next()) { String str1;
/*  784 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  785 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  788 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  793 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  796 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  798 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  802 */     StringBuffer rca = new StringBuffer();
/*  803 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  804 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  807 */     int rcalength = key.length();
/*  808 */     String split = "6. ";
/*  809 */     int splitPresent = key.indexOf(split);
/*  810 */     String div1 = "";String div2 = "";
/*  811 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  813 */       if (rcalength > 180) {
/*  814 */         rca.append("<span class=\"rca-critical-text\">");
/*  815 */         getRCATrimmedText(key, rca);
/*  816 */         rca.append("</span>");
/*      */       } else {
/*  818 */         rca.append("<span class=\"rca-critical-text\">");
/*  819 */         rca.append(key);
/*  820 */         rca.append("</span>");
/*      */       }
/*  822 */       return rca.toString();
/*      */     }
/*  824 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  825 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  826 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  827 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  828 */     getRCATrimmedText(div1, rca);
/*  829 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  832 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  833 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  834 */     getRCATrimmedText(div2, rca);
/*  835 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  837 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  842 */     String[] st = msg.split("<br>");
/*  843 */     for (int i = 0; i < st.length; i++) {
/*  844 */       String s = st[i];
/*  845 */       if (s.length() > 180) {
/*  846 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  848 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  852 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  853 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  855 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  859 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  860 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  861 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  864 */       if (key == null) {
/*  865 */         return ret;
/*      */       }
/*      */       
/*  868 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  869 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  872 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  873 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  874 */       set = AMConnectionPool.executeQueryStmt(query);
/*  875 */       if (set.next())
/*      */       {
/*  877 */         String helpLink = set.getString("LINK");
/*  878 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  881 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  887 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  906 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  897 */         if (set != null) {
/*  898 */           AMConnectionPool.closeStatement(set);
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
/*  912 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  913 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  915 */       String entityStr = (String)keys.nextElement();
/*  916 */       String mmessage = temp.getProperty(entityStr);
/*  917 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  918 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  920 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  926 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  927 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  929 */       String entityStr = (String)keys.nextElement();
/*  930 */       String mmessage = temp.getProperty(entityStr);
/*  931 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  932 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  934 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  939 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  949 */     String des = new String();
/*  950 */     while (str.indexOf(find) != -1) {
/*  951 */       des = des + str.substring(0, str.indexOf(find));
/*  952 */       des = des + replace;
/*  953 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  955 */     des = des + str;
/*  956 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  963 */       if (alert == null)
/*      */       {
/*  965 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  967 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  969 */         return "&nbsp;";
/*      */       }
/*      */       
/*  972 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  974 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  977 */       int rcalength = test.length();
/*  978 */       if (rcalength < 300)
/*      */       {
/*  980 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  984 */       StringBuffer out = new StringBuffer();
/*  985 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  986 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  987 */       out.append("</div>");
/*  988 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  989 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  990 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  995 */       ex.printStackTrace();
/*      */     }
/*  997 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1003 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1008 */     ArrayList attribIDs = new ArrayList();
/* 1009 */     ArrayList resIDs = new ArrayList();
/* 1010 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1012 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1014 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1016 */       String resourceid = "";
/* 1017 */       String resourceType = "";
/* 1018 */       if (type == 2) {
/* 1019 */         resourceid = (String)row.get(0);
/* 1020 */         resourceType = (String)row.get(3);
/*      */       }
/* 1022 */       else if (type == 3) {
/* 1023 */         resourceid = (String)row.get(0);
/* 1024 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1027 */         resourceid = (String)row.get(6);
/* 1028 */         resourceType = (String)row.get(7);
/*      */       }
/* 1030 */       resIDs.add(resourceid);
/* 1031 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1032 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1034 */       String healthentity = null;
/* 1035 */       String availentity = null;
/* 1036 */       if (healthid != null) {
/* 1037 */         healthentity = resourceid + "_" + healthid;
/* 1038 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1041 */       if (availid != null) {
/* 1042 */         availentity = resourceid + "_" + availid;
/* 1043 */         entitylist.add(availentity);
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
/* 1057 */     Properties alert = getStatus(entitylist);
/* 1058 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1063 */     int size = monitorList.size();
/*      */     
/* 1065 */     String[] severity = new String[size];
/*      */     
/* 1067 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1069 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1070 */       String resourceName1 = (String)row1.get(7);
/* 1071 */       String resourceid1 = (String)row1.get(6);
/* 1072 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1073 */       if (severity[j] == null)
/*      */       {
/* 1075 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1079 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1081 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1083 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1086 */         if (sev > 0) {
/* 1087 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1088 */           monitorList.set(k, monitorList.get(j));
/* 1089 */           monitorList.set(j, t);
/* 1090 */           String temp = severity[k];
/* 1091 */           severity[k] = severity[j];
/* 1092 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1098 */     int z = 0;
/* 1099 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1102 */       int i = 0;
/* 1103 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1106 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1110 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1114 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1116 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1119 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1123 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1126 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1127 */       String resourceName1 = (String)row1.get(7);
/* 1128 */       String resourceid1 = (String)row1.get(6);
/* 1129 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1130 */       if (hseverity[j] == null)
/*      */       {
/* 1132 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1137 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1139 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1142 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1145 */         if (hsev > 0) {
/* 1146 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1147 */           monitorList.set(k, monitorList.get(j));
/* 1148 */           monitorList.set(j, t);
/* 1149 */           String temp1 = hseverity[k];
/* 1150 */           hseverity[k] = hseverity[j];
/* 1151 */           hseverity[j] = temp1;
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
/* 1163 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1164 */     boolean forInventory = false;
/* 1165 */     String trdisplay = "none";
/* 1166 */     String plusstyle = "inline";
/* 1167 */     String minusstyle = "none";
/* 1168 */     String haidTopLevel = "";
/* 1169 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1171 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1173 */         haidTopLevel = request.getParameter("haid");
/* 1174 */         forInventory = true;
/* 1175 */         trdisplay = "table-row;";
/* 1176 */         plusstyle = "none";
/* 1177 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1184 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1187 */     ArrayList listtoreturn = new ArrayList();
/* 1188 */     StringBuffer toreturn = new StringBuffer();
/* 1189 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1190 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1191 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1193 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1195 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1196 */       String childresid = (String)singlerow.get(0);
/* 1197 */       String childresname = (String)singlerow.get(1);
/* 1198 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1199 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1200 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1201 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1202 */       String unmanagestatus = (String)singlerow.get(5);
/* 1203 */       String actionstatus = (String)singlerow.get(6);
/* 1204 */       String linkclass = "monitorgp-links";
/* 1205 */       String titleforres = childresname;
/* 1206 */       String titilechildresname = childresname;
/* 1207 */       String childimg = "/images/trcont.png";
/* 1208 */       String flag = "enable";
/* 1209 */       String dcstarted = (String)singlerow.get(8);
/* 1210 */       String configMonitor = "";
/* 1211 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1212 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1214 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1216 */       if (singlerow.get(7) != null)
/*      */       {
/* 1218 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1220 */       String haiGroupType = "0";
/* 1221 */       if ("HAI".equals(childtype))
/*      */       {
/* 1223 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1225 */       childimg = "/images/trend.png";
/* 1226 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1227 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1228 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1230 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1232 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1234 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1235 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1238 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1240 */         linkclass = "disabledtext";
/* 1241 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1243 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1244 */       String availmouseover = "";
/* 1245 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1247 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1249 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1250 */       String healthmouseover = "";
/* 1251 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1253 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1256 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1257 */       int spacing = 0;
/* 1258 */       if (level >= 1)
/*      */       {
/* 1260 */         spacing = 40 * level;
/*      */       }
/* 1262 */       if (childtype.equals("HAI"))
/*      */       {
/* 1264 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1265 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1266 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1268 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1269 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1270 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1271 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1272 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1273 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1274 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1275 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1276 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1277 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1278 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1280 */         if (!forInventory)
/*      */         {
/* 1282 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1285 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1287 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1289 */           actions = editlink + actions;
/*      */         }
/* 1291 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1293 */           actions = actions + associatelink;
/*      */         }
/* 1295 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1296 */         String arrowimg = "";
/* 1297 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1299 */           actions = "";
/* 1300 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1301 */           checkbox = "";
/* 1302 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1304 */         if (isIt360)
/*      */         {
/* 1306 */           actionimg = "";
/* 1307 */           actions = "";
/* 1308 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1309 */           checkbox = "";
/*      */         }
/*      */         
/* 1312 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1314 */           actions = "";
/*      */         }
/* 1316 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1318 */           checkbox = "";
/*      */         }
/*      */         
/* 1321 */         String resourcelink = "";
/*      */         
/* 1323 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1325 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1329 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1332 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1333 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1334 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1335 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1336 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1337 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1338 */         if (!isIt360)
/*      */         {
/* 1340 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1344 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1347 */         toreturn.append("</tr>");
/* 1348 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1350 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1351 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1355 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1356 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1359 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1363 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1365 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1366 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1367 */             toreturn.append(assocMessage);
/* 1368 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1369 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1370 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1371 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1377 */         String resourcelink = null;
/* 1378 */         boolean hideEditLink = false;
/* 1379 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1381 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1382 */           hideEditLink = true;
/* 1383 */           if (isIt360)
/*      */           {
/* 1385 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1389 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1391 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1393 */           hideEditLink = true;
/* 1394 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1395 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1400 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1403 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1404 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1405 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1406 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1407 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1408 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1409 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1410 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1411 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1412 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1413 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1414 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1415 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1417 */         if (hideEditLink)
/*      */         {
/* 1419 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1421 */         if (!forInventory)
/*      */         {
/* 1423 */           removefromgroup = "";
/*      */         }
/* 1425 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1426 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1427 */           actions = actions + configcustomfields;
/*      */         }
/* 1429 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1431 */           actions = editlink + actions;
/*      */         }
/* 1433 */         String managedLink = "";
/* 1434 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1436 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1437 */           actions = "";
/* 1438 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1439 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1442 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1444 */           checkbox = "";
/*      */         }
/*      */         
/* 1447 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1449 */           actions = "";
/*      */         }
/* 1451 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1452 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1454 */         if (isIt360)
/*      */         {
/* 1456 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1460 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1462 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1463 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1464 */         if (!isIt360)
/*      */         {
/* 1466 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1470 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1472 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1475 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1482 */       StringBuilder toreturn = new StringBuilder();
/* 1483 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1484 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1485 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1486 */       String title = "";
/* 1487 */       message = EnterpriseUtil.decodeString(message);
/* 1488 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1489 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1490 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1492 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1494 */       else if ("5".equals(severity))
/*      */       {
/* 1496 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1500 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1502 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1503 */       toreturn.append(v);
/*      */       
/* 1505 */       toreturn.append(link);
/* 1506 */       if (severity == null)
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1510 */       else if (severity.equals("5"))
/*      */       {
/* 1512 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1514 */       else if (severity.equals("4"))
/*      */       {
/* 1516 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1518 */       else if (severity.equals("1"))
/*      */       {
/* 1520 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1525 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1527 */       toreturn.append("</a>");
/* 1528 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1532 */       ex.printStackTrace();
/*      */     }
/* 1534 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1541 */       StringBuilder toreturn = new StringBuilder();
/* 1542 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1543 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1544 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1545 */       if (message == null)
/*      */       {
/* 1547 */         message = "";
/*      */       }
/*      */       
/* 1550 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1551 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1553 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1554 */       toreturn.append(v);
/*      */       
/* 1556 */       toreturn.append(link);
/*      */       
/* 1558 */       if (severity == null)
/*      */       {
/* 1560 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1562 */       else if (severity.equals("5"))
/*      */       {
/* 1564 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1566 */       else if (severity.equals("1"))
/*      */       {
/* 1568 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1573 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1575 */       toreturn.append("</a>");
/* 1576 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1582 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1585 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1586 */     if (invokeActions != null) {
/* 1587 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1588 */       while (iterator.hasNext()) {
/* 1589 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1590 */         if (actionmap.containsKey(actionid)) {
/* 1591 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1596 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1600 */     String actionLink = "";
/* 1601 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1602 */     String query = "";
/* 1603 */     ResultSet rs = null;
/* 1604 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1605 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1606 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1607 */       actionLink = "method=" + methodName;
/*      */     }
/* 1609 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1610 */       actionLink = methodName;
/*      */     }
/* 1612 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1613 */     Iterator itr = methodarglist.iterator();
/* 1614 */     boolean isfirstparam = true;
/* 1615 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1616 */     while (itr.hasNext()) {
/* 1617 */       HashMap argmap = (HashMap)itr.next();
/* 1618 */       String argtype = (String)argmap.get("TYPE");
/* 1619 */       String argname = (String)argmap.get("IDENTITY");
/* 1620 */       String paramname = (String)argmap.get("PARAMETER");
/* 1621 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1622 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1623 */         isfirstparam = false;
/* 1624 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1626 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1630 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1634 */         actionLink = actionLink + "&";
/*      */       }
/* 1636 */       String paramValue = null;
/* 1637 */       String tempargname = argname;
/* 1638 */       if (commonValues.getProperty(tempargname) != null) {
/* 1639 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1642 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1643 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1644 */           if (dbType.equals("mysql")) {
/* 1645 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1648 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1650 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1652 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1653 */             if (rs.next()) {
/* 1654 */               paramValue = rs.getString("VALUE");
/* 1655 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1659 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1663 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1666 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1671 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1672 */           paramValue = rowId;
/*      */         }
/* 1674 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1675 */           paramValue = managedObjectName;
/*      */         }
/* 1677 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1678 */           paramValue = resID;
/*      */         }
/* 1680 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1681 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1684 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1686 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1687 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1688 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1690 */     return actionLink;
/*      */   }
/*      */   
/* 1693 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1694 */     String dependentAttribute = null;
/* 1695 */     String align = "left";
/*      */     
/* 1697 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1698 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1699 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1700 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1701 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1702 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1703 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1704 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1705 */       align = "center";
/*      */     }
/*      */     
/* 1708 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1709 */     String actualdata = "";
/*      */     
/* 1711 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1712 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1713 */         actualdata = availValue;
/*      */       }
/* 1715 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1716 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1720 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1721 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1724 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1730 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1731 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1732 */       toreturn.append("<table>");
/* 1733 */       toreturn.append("<tr>");
/* 1734 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1735 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1736 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1737 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1738 */         String toolTip = "";
/* 1739 */         String hideClass = "";
/* 1740 */         String textStyle = "";
/* 1741 */         boolean isreferenced = true;
/* 1742 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1743 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1744 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1745 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1747 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1748 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1749 */           while (valueList.hasMoreTokens()) {
/* 1750 */             String dependentVal = valueList.nextToken();
/* 1751 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1752 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1753 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1755 */               toolTip = "";
/* 1756 */               hideClass = "";
/* 1757 */               isreferenced = false;
/* 1758 */               textStyle = "disabledtext";
/* 1759 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1763 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1764 */           toolTip = "";
/* 1765 */           hideClass = "";
/* 1766 */           isreferenced = false;
/* 1767 */           textStyle = "disabledtext";
/* 1768 */           if (dependentImageMap != null) {
/* 1769 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1770 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1773 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1777 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1778 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1779 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1780 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1781 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1782 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1784 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1785 */           if (isreferenced) {
/* 1786 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1790 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1791 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1792 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1793 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1794 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1795 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1797 */           toreturn.append("</span>");
/* 1798 */           toreturn.append("</a>");
/* 1799 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1802 */       toreturn.append("</tr>");
/* 1803 */       toreturn.append("</table>");
/* 1804 */       toreturn.append("</td>");
/*      */     } else {
/* 1806 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1809 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1813 */     String colTime = null;
/* 1814 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1815 */     if ((rows != null) && (rows.size() > 0)) {
/* 1816 */       Iterator<String> itr = rows.iterator();
/* 1817 */       String maxColQuery = "";
/* 1818 */       for (;;) { if (itr.hasNext()) {
/* 1819 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1820 */           ResultSet maxCol = null;
/*      */           try {
/* 1822 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1823 */             while (maxCol.next()) {
/* 1824 */               if (colTime == null) {
/* 1825 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1828 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1837 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1839 */               if (maxCol != null)
/* 1840 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1842 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1837 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1839 */               if (maxCol != null)
/* 1840 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1842 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1847 */     return colTime;
/*      */   }
/*      */   
/* 1850 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1851 */     tablename = null;
/* 1852 */     ResultSet rsTable = null;
/* 1853 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1855 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1856 */       while (rsTable.next()) {
/* 1857 */         tablename = rsTable.getString("DATATABLE");
/* 1858 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1859 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1872 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1863 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1866 */         if (rsTable != null)
/* 1867 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1869 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1875 */     String argsList = "";
/* 1876 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1878 */       if (showArgsMap.get(row) != null) {
/* 1879 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1880 */         if (showArgslist != null) {
/* 1881 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1882 */             if (argsList.trim().equals("")) {
/* 1883 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1886 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1893 */       e.printStackTrace();
/* 1894 */       return "";
/*      */     }
/* 1896 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1901 */     String argsList = "";
/* 1902 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1905 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1907 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1908 */         if (hideArgsList != null)
/*      */         {
/* 1910 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1912 */             if (argsList.trim().equals(""))
/*      */             {
/* 1914 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1918 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1926 */       ex.printStackTrace();
/*      */     }
/* 1928 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1932 */     StringBuilder toreturn = new StringBuilder();
/* 1933 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1940 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1941 */       Iterator itr = tActionList.iterator();
/* 1942 */       while (itr.hasNext()) {
/* 1943 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1944 */         String confirmmsg = "";
/* 1945 */         String link = "";
/* 1946 */         String isJSP = "NO";
/* 1947 */         HashMap tactionMap = (HashMap)itr.next();
/* 1948 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1949 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1950 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1951 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1952 */           (actionmap.containsKey(actionId))) {
/* 1953 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1954 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1955 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1956 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1957 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1959 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1965 */           if (isTableAction) {
/* 1966 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1969 */             tableName = "Link";
/* 1970 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1971 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1972 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1973 */             toreturn.append("</a></td>");
/*      */           }
/* 1975 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1976 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1977 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1978 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1984 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1990 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1992 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1993 */       Properties prop = (Properties)node.getUserObject();
/* 1994 */       String mgID = prop.getProperty("label");
/* 1995 */       String mgName = prop.getProperty("value");
/* 1996 */       String isParent = prop.getProperty("isParent");
/* 1997 */       int mgIDint = Integer.parseInt(mgID);
/* 1998 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2000 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2002 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2003 */       if (node.getChildCount() > 0)
/*      */       {
/* 2005 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2007 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2009 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2011 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2015 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2020 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2022 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2024 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2026 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2030 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2033 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2034 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2036 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2040 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2042 */       if (node.getChildCount() > 0)
/*      */       {
/* 2044 */         builder.append("<UL>");
/* 2045 */         printMGTree(node, builder);
/* 2046 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2051 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2052 */     StringBuffer toReturn = new StringBuffer();
/* 2053 */     String table = "-";
/*      */     try {
/* 2055 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2056 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2057 */       float total = 0.0F;
/* 2058 */       while (it.hasNext()) {
/* 2059 */         String attName = (String)it.next();
/* 2060 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2061 */         boolean roundOffData = false;
/* 2062 */         if ((data != null) && (!data.equals(""))) {
/* 2063 */           if (data.indexOf(",") != -1) {
/* 2064 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2067 */             float value = Float.parseFloat(data);
/* 2068 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2071 */             total += value;
/* 2072 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2075 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2080 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2081 */       while (attVsWidthList.hasNext()) {
/* 2082 */         String attName = (String)attVsWidthList.next();
/* 2083 */         String data = (String)attVsWidthProps.get(attName);
/* 2084 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2085 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2086 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2087 */         String className = (String)graphDetails.get("ClassName");
/* 2088 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2089 */         if (percentage < 1.0F)
/*      */         {
/* 2091 */           data = percentage + "";
/*      */         }
/* 2093 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2095 */       if (toReturn.length() > 0) {
/* 2096 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2100 */       e.printStackTrace();
/*      */     }
/* 2102 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2108 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2109 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2110 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2111 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2112 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2113 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2114 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2115 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2116 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2119 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2120 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2121 */       splitvalues[0] = multiplecondition.toString();
/* 2122 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2125 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2130 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2131 */     if (thresholdType != 3) {
/* 2132 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2133 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2134 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2135 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2136 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2137 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2139 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2140 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2141 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2142 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2143 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2144 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2146 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2147 */     if (updateSelected != null) {
/* 2148 */       updateSelected[0] = "selected";
/*      */     }
/* 2150 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2155 */       StringBuffer toreturn = new StringBuffer("");
/* 2156 */       if (commaSeparatedMsgId != null) {
/* 2157 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2158 */         int count = 0;
/* 2159 */         while (msgids.hasMoreTokens()) {
/* 2160 */           String id = msgids.nextToken();
/* 2161 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2162 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2163 */           count++;
/* 2164 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2165 */             if (toreturn.length() == 0) {
/* 2166 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2168 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2169 */             if (!image.trim().equals("")) {
/* 2170 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2172 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2173 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2176 */         if (toreturn.length() > 0) {
/* 2177 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2181 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2184 */       e.printStackTrace(); }
/* 2185 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2191 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2197 */   private static Map<String, Long> _jspx_dependants = new HashMap(7);
/* 2198 */   static { _jspx_dependants.put("/jsp/includes/ScriptLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2199 */     _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2200 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2201 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2202 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2203 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2204 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2232 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2236 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2257 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2261 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2262 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2263 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2265 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2270 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2271 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2272 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2273 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2274 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2275 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2276 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2277 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2278 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2279 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2280 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2287 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2290 */     JspWriter out = null;
/* 2291 */     Object page = this;
/* 2292 */     JspWriter _jspx_out = null;
/* 2293 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2297 */       response.setContentType("text/html;charset=UTF-8");
/* 2298 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2300 */       _jspx_page_context = pageContext;
/* 2301 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2302 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2303 */       session = pageContext.getSession();
/* 2304 */       out = pageContext.getOut();
/* 2305 */       _jspx_out = out;
/*      */       
/* 2307 */       out.write("<!DOCTYPE html>\n");
/* 2308 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2310 */       request.setAttribute("HelpKey", "File Monitor Details");
/*      */       
/* 2312 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2313 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2315 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2316 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2317 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2319 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2321 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2323 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2325 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2326 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2327 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2328 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2331 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2332 */         String available = null;
/* 2333 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2334 */         out.write(10);
/*      */         
/* 2336 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2337 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2338 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2340 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2342 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2344 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2346 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2347 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2348 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2349 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2352 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2353 */           String unavailable = null;
/* 2354 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2355 */           out.write(10);
/*      */           
/* 2357 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2358 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2359 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2361 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2363 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2365 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2367 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2368 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2369 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2370 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2373 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2374 */             String unmanaged = null;
/* 2375 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2376 */             out.write(10);
/*      */             
/* 2378 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2379 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2380 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2382 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2384 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2386 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2388 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2389 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2390 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2391 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2394 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2395 */               String scheduled = null;
/* 2396 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2397 */               out.write(10);
/*      */               
/* 2399 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2400 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2401 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2403 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2405 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2407 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2409 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2410 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2411 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2412 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2415 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2416 */                 String critical = null;
/* 2417 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2418 */                 out.write(10);
/*      */                 
/* 2420 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2421 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2422 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2424 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2426 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2428 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2430 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2431 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2432 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2433 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2436 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2437 */                   String clear = null;
/* 2438 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2439 */                   out.write(10);
/*      */                   
/* 2441 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2442 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2443 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2445 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2447 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2449 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2451 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2452 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2453 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2454 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2457 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2458 */                     String warning = null;
/* 2459 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2460 */                     out.write(10);
/* 2461 */                     out.write(10);
/*      */                     
/* 2463 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2464 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2466 */                     out.write(10);
/* 2467 */                     out.write(10);
/* 2468 */                     out.write(10);
/* 2469 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2470 */                     GetWLSGraph wlsGraph = null;
/* 2471 */                     wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2472 */                     if (wlsGraph == null) {
/* 2473 */                       wlsGraph = new GetWLSGraph();
/* 2474 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2476 */                     out.write(10);
/* 2477 */                     CAMGraphs camGraph = null;
/* 2478 */                     camGraph = (CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 2479 */                     if (camGraph == null) {
/* 2480 */                       camGraph = new CAMGraphs();
/* 2481 */                       _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                     }
/* 2483 */                     out.write(10);
/* 2484 */                     Hashtable motypedisplaynames = null;
/* 2485 */                     synchronized (application) {
/* 2486 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2487 */                       if (motypedisplaynames == null) {
/* 2488 */                         motypedisplaynames = new Hashtable();
/* 2489 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2492 */                     out.write(10);
/* 2493 */                     Hashtable availabilitykeys = null;
/* 2494 */                     synchronized (application) {
/* 2495 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2496 */                       if (availabilitykeys == null) {
/* 2497 */                         availabilitykeys = new Hashtable();
/* 2498 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2501 */                     out.write(10);
/* 2502 */                     Hashtable healthkeys = null;
/* 2503 */                     synchronized (application) {
/* 2504 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2505 */                       if (healthkeys == null) {
/* 2506 */                         healthkeys = new Hashtable();
/* 2507 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2510 */                     out.write("\n\n\n\n");
/*      */                     
/* 2512 */                     String responsetimeid = null;
/* 2513 */                     Properties ess_atts = new Properties();
/* 2514 */                     String dispname = (String)motypedisplaynames.get(request.getParameter("type")) + " Details";
/* 2515 */                     String resourceName = (String)request.getAttribute("monitorname");
/* 2516 */                     String resID = request.getParameter("resourceid");
/* 2517 */                     String resourceid = resID;
/* 2518 */                     request.setAttribute("resourceid", resID);
/* 2519 */                     ArrayList attribIDs = new ArrayList();
/* 2520 */                     String haid = request.getParameter("haid");
/* 2521 */                     String moname = request.getParameter("moname");
/* 2522 */                     ArrayList resIDs = new ArrayList();
/* 2523 */                     resIDs.add(resID);
/* 2524 */                     attribIDs.add("6000");
/* 2525 */                     attribIDs.add("6001");
/* 2526 */                     attribIDs.add("6002");
/* 2527 */                     attribIDs.add("6003");
/* 2528 */                     attribIDs.add("6004");
/* 2529 */                     attribIDs.add("6005");
/* 2530 */                     attribIDs.add("6006");
/* 2531 */                     attribIDs.add("6007");
/* 2532 */                     attribIDs.add("6008");
/* 2533 */                     attribIDs.add("6009");
/* 2534 */                     attribIDs.add("6010");
/* 2535 */                     attribIDs.add("6011");
/* 2536 */                     attribIDs.add("6012");
/*      */                     
/* 2538 */                     attribIDs.add("6013");
/* 2539 */                     attribIDs.add("6014");
/* 2540 */                     String resourcetype = request.getParameter("type");
/* 2541 */                     if (resourcetype.equals("File System Monitor")) {
/* 2542 */                       resourcetype = request.getParameter("mtype");
/*      */                     }
/* 2544 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2545 */                     String encodeurl = URLEncoder.encode("/showresource.do?type=" + resourcetype + "&moname=" + moname + "&method=showdetails&resourcename=" + resourceName + "&resourceid=" + resID + "&haid=" + haid);
/* 2546 */                     String tipCurStatus = FormatUtil.getString("am.webclient.script.tooltip");
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2551 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/* 2553 */                     String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2554 */                     String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/* 2555 */                     HashMap filedata = new HashMap();
/* 2556 */                     HashMap dirdata = new HashMap();
/* 2557 */                     String aid1 = null;
/* 2558 */                     String hid = null;
/* 2559 */                     String attribute = null;
/* 2560 */                     String theading = null;
/*      */                     
/* 2562 */                     String mtype = null;
/*      */                     
/* 2564 */                     String fname = "Script Name";
/*      */                     
/*      */                     try
/*      */                     {
/* 2568 */                       mtype = request.getParameter("mtype");
/*      */                       
/* 2570 */                       if ((mtype == null) && (resourcetype.equals("file"))) {
/* 2571 */                         mtype = "file";
/* 2572 */                       } else if ((mtype == null) && (resourcetype.equals("directory"))) {
/* 2573 */                         mtype = "directory";
/* 2574 */                         System.out.println("DIRECTORY");
/*      */                       }
/*      */                       
/* 2577 */                       if ((resourcetype.equals("file")) || (mtype.equals("file"))) {
/* 2578 */                         filedata = (HashMap)request.getAttribute("filedata");
/* 2579 */                         aid1 = "6008";
/* 2580 */                         hid = "6009";
/* 2581 */                         responsetimeid = "6013";
/* 2582 */                         attribute = "File Size";
/* 2583 */                         theading = "File Statistics";
/* 2584 */                         fname = "File Name";
/* 2585 */                         mtype = "file";
/*      */ 
/*      */                       }
/* 2588 */                       else if ((resourcetype.equals("directory")) || (mtype.equals("directory"))) {
/* 2589 */                         dirdata = (HashMap)request.getAttribute("dirdata");
/* 2590 */                         System.out.println("the dirdata:" + dirdata);
/* 2591 */                         aid1 = "6000";
/* 2592 */                         hid = "6001";
/* 2593 */                         responsetimeid = "6014";
/* 2594 */                         fname = "Directory Name";
/* 2595 */                         attribute = "Directory Size";
/* 2596 */                         theading = "Directory Statistics";
/* 2597 */                         mtype = "directory";
/*      */                       }
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2602 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 2605 */                     String funit = FormatUtil.getString("File Size") + " (" + AMAutomaticPortChanger.getFileUnit() + ")";
/* 2606 */                     String dunit = FormatUtil.getString("Directory Size") + " (" + AMAutomaticPortChanger.getDirUnit() + ")";
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2611 */                     out.write(10);
/*      */                     
/* 2613 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2614 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2615 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2617 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2618 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2619 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2621 */                         out.write(32);
/*      */                         
/* 2623 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2624 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2625 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2627 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2629 */                         _jspx_th_tiles_005fput_005f0.setValue(dispname);
/* 2630 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2631 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2632 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2635 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2636 */                         out.write(10);
/* 2637 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2639 */                         out.write(10);
/* 2640 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2642 */                         out.write(10);
/*      */                         
/* 2644 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2645 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2646 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2648 */                         _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */                         
/* 2650 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2651 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2652 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2653 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2654 */                             out = _jspx_page_context.pushBody();
/* 2655 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2656 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2659 */                             out.write(10);
/* 2660 */                             out.write("<!--$Id$-->\n\n\n\n");
/*      */                             
/* 2662 */                             int healthid = 2201;
/* 2663 */                             int availabilityid = 2200;
/* 2664 */                             String haid1 = "";
/* 2665 */                             String head = "Script Monitor";
/* 2666 */                             String mon_type = null;
/* 2667 */                             String rtype = request.getParameter("type");
/* 2668 */                             head = rtype;
/* 2669 */                             if (rtype.equals("File System Monitor"))
/*      */                             {
/* 2671 */                               head = "File / Directory Monitor";
/*      */                             }
/*      */                             
/* 2674 */                             String typedisplayname = head;
/* 2675 */                             if (request.getParameter("typedisplayname") != null)
/*      */                             {
/* 2677 */                               typedisplayname = request.getParameter("typedisplayname");
/*      */                             }
/* 2679 */                             else if (request.getAttribute("typedisplayname") != null)
/*      */                             {
/* 2681 */                               typedisplayname = (String)request.getAttribute("typedisplayname");
/*      */                             }
/*      */                             
/* 2684 */                             int baseid = -1;
/*      */                             try
/*      */                             {
/* 2687 */                               if (request.getAttribute("baseid") != null) {
/* 2688 */                                 baseid = Integer.parseInt((String)request.getAttribute("baseid"));
/*      */                               }
/*      */                             }
/*      */                             catch (Exception e2)
/*      */                             {
/* 2693 */                               e2.printStackTrace();
/*      */                             }
/* 2695 */                             String appendParams = "&baseid=" + baseid;
/* 2696 */                             if (baseid != -1)
/*      */                             {
/* 2698 */                               appendParams = appendParams + "&customType=true&basetype=Script Monitor";
/*      */                             }
/* 2700 */                             if ((rtype.equals("file")) || (rtype.equals("directory")) || (rtype.equals("File System Monitor")))
/*      */                             {
/* 2702 */                               rtype = "File System Monitor";
/* 2703 */                               head = "File / Directory Monitor";
/* 2704 */                               typedisplayname = "File / Directory Monitor";
/* 2705 */                             } else if (rtype.equals("Ping Monitor"))
/*      */                             {
/* 2707 */                               head = "Ping Monitor";
/* 2708 */                               rtype = "Ping Monitor";
/* 2709 */                               typedisplayname = "Ping Monitor";
/* 2710 */                               healthid = 9001;
/* 2711 */                               availabilityid = 9000;
/*      */                             }
/*      */                             else
/*      */                             {
/* 2715 */                               availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2716 */                               healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                             }
/*      */                             
/*      */ 
/* 2720 */                             if (request.getParameter("mtype") != null) {
/* 2721 */                               mon_type = request.getParameter("mtype");
/*      */                             }
/*      */                             else {
/* 2724 */                               mon_type = request.getParameter("type");
/*      */                             }
/*      */                             
/* 2727 */                             if (request.getParameter("type").equals("QENGINE"))
/*      */                             {
/* 2729 */                               healthid = 4301;
/* 2730 */                               availabilityid = 4300;
/*      */                             }
/* 2732 */                             else if (mon_type.equals("file")) {
/* 2733 */                               head = "File System Monitor";
/*      */                               
/* 2735 */                               healthid = 6009;
/* 2736 */                               availabilityid = 6008;
/*      */                             }
/* 2738 */                             else if (mon_type.equals("directory"))
/*      */                             {
/* 2740 */                               head = "File System Monitor";
/* 2741 */                               healthid = 6001;
/* 2742 */                               availabilityid = 6000;
/*      */ 
/*      */                             }
/* 2745 */                             else if (request.getParameter("type").equals("Script Monitor"))
/*      */                             {
/* 2747 */                               healthid = 2201;
/* 2748 */                               availabilityid = 2200;
/*      */                             }
/* 2750 */                             else if (mon_type.equals("Ping Monitor"))
/*      */                             {
/* 2752 */                               healthid = 9001;
/* 2753 */                               availabilityid = 9000;
/*      */                             }
/*      */                             else
/*      */                             {
/* 2757 */                               availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2758 */                               healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                             }
/*      */                             
/*      */ 
/* 2762 */                             out.write("\n\n<script language=\"JavaScript\">\n");
/*      */                             
/* 2764 */                             if (request.getParameter("editPage") != null)
/*      */                             {
/* 2766 */                               out.write(10);
/*      */                             }
/*      */                             
/* 2769 */                             out.write("\n\n\n\tfunction confirmDelete()\n \t {\n  var s = confirm(\"");
/* 2770 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2771 */                             out.write("\");\n  if (s)\n         document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 2772 */                             out.print(head);
/* 2773 */                             out.write("&baseid=");
/* 2774 */                             out.print(baseid);
/* 2775 */                             out.write("&select=");
/* 2776 */                             if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2778 */                             out.write("\";\n\t }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2779 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2780 */                             out.write("\");\n  if (s){\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2781 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2783 */                             out.write("&type=");
/* 2784 */                             out.print(rtype);
/* 2785 */                             out.write("&moname=");
/* 2786 */                             out.print(moname);
/* 2787 */                             out.write("&resourcename=");
/* 2788 */                             out.print(resourceName);
/* 2789 */                             out.write("\";\n\n}\n\t }\n\n         function confirmUnManage()\n \t {\n        \t  var show_msg=\"false\";\n              var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2790 */                             out.print(request.getParameter("resourceid"));
/* 2791 */                             out.write("; //No i18n\n              $.ajax({\n                type:'POST', //No i18n\n                url:url,\n                async:false,\n                success: function(data)\n                {\n                  show_msg=data\n                }\n              });\n              if(show_msg.indexOf(\"true\")>-1)\n              {\n                  alert(\"");
/* 2792 */                             out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2793 */                             out.write("\");\n\t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2794 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2796 */                             out.write("&type=");
/* 2797 */                             out.print(rtype);
/* 2798 */                             out.write("&moname=");
/* 2799 */                             out.print(moname);
/* 2800 */                             out.write("&resourcename=");
/* 2801 */                             out.print(resourceName);
/* 2802 */                             out.write("\";\n             }\n           else { \n        \t   \tvar s = confirm(\"");
/* 2803 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2804 */                             out.write("\");\n       \t\t if (s){\n   \t\t \t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2805 */                             if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2807 */                             out.write("&type=");
/* 2808 */                             out.print(rtype);
/* 2809 */                             out.write("&moname=");
/* 2810 */                             out.print(moname);
/* 2811 */                             out.write("&resourcename=");
/* 2812 */                             out.print(resourceName);
/* 2813 */                             out.write("\";\n\t\t      }\n\t      }\n       }\n  </script>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n    ");
/* 2814 */                             out.print(FormatUtil.getString(typedisplayname));
/* 2815 */                             out.write("\n      </td>\n  </tr>\n      ");
/* 2816 */                             if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2818 */                             out.write("\n      ");
/* 2819 */                             if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2821 */                             out.write(10);
/* 2822 */                             out.write(9);
/* 2823 */                             out.write(9);
/* 2824 */                             if (_jspx_meth_c_005fif_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2826 */                             out.write(10);
/* 2827 */                             out.write("\n\n\n\n");
/*      */                             
/* 2829 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2830 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2831 */                             _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2833 */                             _jspx_th_c_005fif_005f5.setTest("${!empty param.haid && empty invalidhaid}");
/* 2834 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2835 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/* 2837 */                                 out.write(10);
/*      */                                 
/* 2839 */                                 haid1 = "&haid=" + request.getParameter("haid");
/*      */                                 
/* 2841 */                                 out.write(10);
/* 2842 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2843 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2847 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2848 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                             }
/*      */                             
/* 2851 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2852 */                             out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                             
/* 2854 */                             IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2855 */                             _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2856 */                             _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2858 */                             _jspx_th_c_005fif_005f6.setTest("${param.method=='editScript'}");
/* 2859 */                             int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2860 */                             if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                               for (;;) {
/* 2862 */                                 out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 2863 */                                 if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 2865 */                                 out.write("&method=showResourceForResourceID");
/* 2866 */                                 out.print(haid1);
/* 2867 */                                 out.write("\"  class=\"new-left-links\">\n    ");
/* 2868 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2869 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2873 */                             if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2874 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                             }
/*      */                             
/* 2877 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2878 */                             out.write("\n    ");
/* 2879 */                             out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2880 */                             out.write("\n    ");
/* 2881 */                             if (_jspx_meth_c_005fif_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2883 */                             out.write("\n     </td>\n  </tr>\n");
/* 2884 */                             out.write(10);
/* 2885 */                             out.write(32);
/* 2886 */                             out.write(32);
/*      */                             
/* 2888 */                             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2889 */                             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2890 */                             _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2892 */                             _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN || !empty DEMO }");
/* 2893 */                             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2894 */                             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                               for (;;) {
/* 2896 */                                 out.write("\n\n\n\t<tr>\n  ");
/*      */                                 
/* 2898 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2899 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2900 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 2902 */                                 _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2903 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2904 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2906 */                                     out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 2907 */                                     out.print(ALERTCONFIG_TEXT);
/* 2908 */                                     out.write("</a> </td>\n  ");
/* 2909 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2910 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2914 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2915 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2918 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2919 */                                 out.write(10);
/* 2920 */                                 out.write(9);
/*      */                                 
/* 2922 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2923 */                                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2924 */                                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 2926 */                                 _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2927 */                                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2928 */                                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2930 */                                     out.write("\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                     
/* 2932 */                                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2933 */                                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2934 */                                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                     
/* 2936 */                                     _jspx_th_c_005fif_005f9.setTest("${param.method=='editScript'}");
/* 2937 */                                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2938 */                                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                       for (;;) {
/* 2940 */                                         out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 2941 */                                         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                                           return;
/* 2943 */                                         out.write("&method=showResourceForResourceID&alert=true");
/* 2944 */                                         out.print(haid1);
/* 2945 */                                         out.write("\"  class=\"new-left-links\">\n    ");
/* 2946 */                                         out.print(ALERTCONFIG_TEXT);
/* 2947 */                                         out.write("\n    </a>\n    ");
/* 2948 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2949 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2953 */                                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2954 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                     }
/*      */                                     
/* 2957 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2958 */                                     out.write("\n    ");
/*      */                                     
/* 2960 */                                     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2961 */                                     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2962 */                                     _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                     
/* 2964 */                                     _jspx_th_c_005fif_005f10.setTest("${param.method!='editScript'}");
/* 2965 */                                     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2966 */                                     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                       for (;;) {
/* 2968 */                                         out.write("\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2969 */                                         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                           return;
/* 2971 */                                         out.write("&mtype=");
/* 2972 */                                         out.print(mon_type);
/* 2973 */                                         out.write("\" class=\"new-left-links\">\n    ");
/* 2974 */                                         out.print(ALERTCONFIG_TEXT);
/* 2975 */                                         out.write("\n    </a>\n    ");
/* 2976 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2977 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2981 */                                     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2982 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                     }
/*      */                                     
/* 2985 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2986 */                                     out.write("\n    </td>\n");
/* 2987 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2988 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2992 */                                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2993 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2996 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2997 */                                 out.write("\n  </tr>\n  ");
/* 2998 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2999 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3003 */                             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3004 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                             }
/*      */                             
/* 3007 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3008 */                             out.write(10);
/*      */                             
/* 3010 */                             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3011 */                             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3012 */                             _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3014 */                             _jspx_th_c_005fif_005f11.setTest("${param.type=='Script Monitor'}");
/* 3015 */                             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3016 */                             if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                               for (;;) {
/* 3018 */                                 out.write(10);
/* 3019 */                                 out.write(32);
/* 3020 */                                 out.write(32);
/*      */                                 
/* 3022 */                                 IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3023 */                                 _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3024 */                                 _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fif_005f11);
/*      */                                 
/* 3026 */                                 _jspx_th_c_005fif_005f12.setTest("${!empty ADMIN || !empty DEMO }");
/* 3027 */                                 int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3028 */                                 if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                   for (;;) {
/* 3030 */                                     out.write(10);
/* 3031 */                                     out.write(32);
/* 3032 */                                     out.write(32);
/*      */                                     
/* 3034 */                                     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3035 */                                     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3036 */                                     _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f12);
/*      */                                     
/* 3038 */                                     _jspx_th_c_005fif_005f13.setTest("${param.method=='editScript'}");
/* 3039 */                                     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3040 */                                     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                       for (;;) {
/* 3042 */                                         out.write("\n\t <tr>\n  ");
/*      */                                         
/* 3044 */                                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3045 */                                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3046 */                                         _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                         
/* 3048 */                                         _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3049 */                                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3050 */                                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 3052 */                                             out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3053 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3054 */                                             out.write("</a> </td>\n  ");
/* 3055 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3056 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3060 */                                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3061 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3064 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3065 */                                         out.write("\n        ");
/*      */                                         
/* 3067 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3068 */                                         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3069 */                                         _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                         
/* 3071 */                                         _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3072 */                                         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3073 */                                         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 3075 */                                             out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n\n    <a href=\"/showresource.do?resourceid=");
/* 3076 */                                             if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                                               return;
/* 3078 */                                             out.write("&method=showResourceForResourceID&reports=true");
/* 3079 */                                             out.print(haid1);
/* 3080 */                                             out.write("\"  class=\"new-left-links\">\n    ");
/* 3081 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3082 */                                             out.write("\n    </a>\n     </td>\n");
/* 3083 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3084 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3088 */                                         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3089 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3092 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3093 */                                         out.write("\n  </tr>\n  ");
/* 3094 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3095 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3099 */                                     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3100 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                     }
/*      */                                     
/* 3103 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3104 */                                     out.write(10);
/* 3105 */                                     out.write(32);
/* 3106 */                                     out.write(32);
/* 3107 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3108 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3112 */                                 if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3113 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                                 }
/*      */                                 
/* 3116 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3117 */                                 out.write("\n\n  ");
/*      */                                 
/* 3119 */                                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3120 */                                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3121 */                                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f11);
/*      */                                 
/* 3123 */                                 _jspx_th_c_005fif_005f14.setTest("${!empty ADMIN || !empty DEMO }");
/* 3124 */                                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3125 */                                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                   for (;;) {
/* 3127 */                                     out.write(10);
/* 3128 */                                     out.write(32);
/* 3129 */                                     out.write(32);
/*      */                                     
/* 3131 */                                     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3132 */                                     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3133 */                                     _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f14);
/*      */                                     
/* 3135 */                                     _jspx_th_c_005fif_005f15.setTest("${param.method!='editScript'}");
/* 3136 */                                     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3137 */                                     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                       for (;;) {
/* 3139 */                                         out.write("\n  <tr>\n");
/*      */                                         
/* 3141 */                                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3142 */                                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3143 */                                         _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 3145 */                                         _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3146 */                                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3147 */                                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                           for (;;) {
/* 3149 */                                             out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3150 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3151 */                                             out.write("</a> </td>\n  ");
/* 3152 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3153 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3157 */                                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3158 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                         }
/*      */                                         
/* 3161 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3162 */                                         out.write("\n        ");
/*      */                                         
/* 3164 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3165 */                                         _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3166 */                                         _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 3168 */                                         _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3169 */                                         int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3170 */                                         if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                           for (;;) {
/* 3172 */                                             out.write("\n\n    <td class=\"leftlinkstd\"><a href=\"javascript:enableReports()\" class=\"new-left-links\">");
/* 3173 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3174 */                                             out.write("</a></td>\n   </td>\n");
/* 3175 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3176 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3180 */                                         if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3181 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                         }
/*      */                                         
/* 3184 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3185 */                                         out.write("\n  </tr>\n  ");
/* 3186 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3187 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3191 */                                     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3192 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                     }
/*      */                                     
/* 3195 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3196 */                                     out.write(10);
/* 3197 */                                     out.write(32);
/* 3198 */                                     out.write(32);
/* 3199 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3200 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3204 */                                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3205 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                 }
/*      */                                 
/* 3208 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3209 */                                 out.write(10);
/* 3210 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3211 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3215 */                             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3216 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                             }
/*      */                             
/* 3219 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3220 */                             out.write(10);
/* 3221 */                             out.write(10);
/*      */                             
/* 3223 */                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3224 */                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3225 */                             _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3227 */                             _jspx_th_logic_005fpresent_005f3.setRole("ENTERPRISEADMIN");
/* 3228 */                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3229 */                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                               for (;;) {
/* 3231 */                                 out.write("\n  \n");
/*      */                                 
/* 3233 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3234 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3235 */                                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                 
/* 3237 */                                 _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3238 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3239 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3241 */                                     out.write("\n<tr>\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3242 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3243 */                                     out.write("</a> </td> \n</tr>\n");
/* 3244 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3245 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3249 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3250 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 3253 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3254 */                                 out.write(10);
/* 3255 */                                 out.write(10);
/*      */                                 
/* 3257 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3258 */                                 _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3259 */                                 _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                 
/* 3261 */                                 _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3262 */                                 int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3263 */                                 if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                   for (;;) {
/* 3265 */                                     out.write(10);
/* 3266 */                                     if (AMAutomaticPortChanger.isSsoEnabled()) {
/* 3267 */                                       out.write("\n\t<tr>\n   <td height=\"21\" class=\"leftlinkstd\">\n   ");
/*      */                                       
/* 3269 */                                       String temphaid = request.getParameter("haid");
/*      */                                       try
/*      */                                       {
/* 3272 */                                         Integer.parseInt(temphaid);
/*      */                                         
/* 3274 */                                         if (rtype.equals("Ping Monitor")) {
/* 3275 */                                           out.write("\n <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3276 */                                           if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3278 */                                           out.write("&method=editScript");
/* 3279 */                                           out.print(appendParams);
/* 3280 */                                           out.write("&resourceid=");
/* 3281 */                                           if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3283 */                                           out.write("&aam_jump=true&type=");
/* 3284 */                                           out.print(request.getParameter("type"));
/* 3285 */                                           if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3287 */                                           out.write("&resourcename=");
/* 3288 */                                           out.print(resourceName);
/* 3289 */                                           out.write("&mtype=");
/* 3290 */                                           out.print(mon_type);
/* 3291 */                                           out.write("\"  class=\"new-left-links\">\n\n");
/*      */                                         } else {
/* 3293 */                                           out.write("\n    <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3294 */                                           if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3296 */                                           out.write("&method=editScript");
/* 3297 */                                           out.print(appendParams);
/* 3298 */                                           out.write("&resourceid=");
/* 3299 */                                           if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3301 */                                           out.write("&aam_jump=true&type=");
/* 3302 */                                           out.print(request.getParameter("type"));
/* 3303 */                                           if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3305 */                                           out.write("&resourcename=");
/* 3306 */                                           out.print(resourceName);
/* 3307 */                                           out.write("&mtype=");
/* 3308 */                                           out.print(mon_type);
/* 3309 */                                           out.write("\"  class=\"new-left-links\">\n   ");
/*      */                                         }
/*      */                                         
/*      */                                       }
/*      */                                       catch (NumberFormatException ne)
/*      */                                       {
/* 3315 */                                         if (rtype.equals("Ping Monitor")) {
/* 3316 */                                           out.write("\n<a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 3317 */                                           out.print(request.getParameter("resourceid"));
/* 3318 */                                           out.write("&type=");
/* 3319 */                                           out.print(request.getParameter("type"));
/* 3320 */                                           out.write("&moname=");
/* 3321 */                                           out.print(moname);
/* 3322 */                                           out.write("&method=showdetails&resourcename=");
/* 3323 */                                           out.print(request.getParameter("resourcename"));
/* 3324 */                                           out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n\n");
/*      */                                         } else {
/* 3326 */                                           out.write("\n\n    <a target=\"mas_window\" href=\"/updateScript.do?method=editScript");
/* 3327 */                                           out.print(appendParams);
/* 3328 */                                           out.write("&resourceid=");
/* 3329 */                                           if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3331 */                                           out.write("&aam_jump=true&type=");
/* 3332 */                                           out.print(request.getParameter("type"));
/* 3333 */                                           if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3335 */                                           out.write("&resourcename=");
/* 3336 */                                           out.print(resourceName);
/* 3337 */                                           out.write("&mtype=");
/* 3338 */                                           out.print(mon_type);
/* 3339 */                                           out.write("\"  class=\"new-left-links\">\n   ");
/*      */                                         }
/*      */                                       }
/*      */                                       
/* 3343 */                                       out.write(10);
/* 3344 */                                       out.write(32);
/* 3345 */                                       out.write(32);
/* 3346 */                                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3347 */                                       out.write("\n   </td> </tr>\n   ");
/*      */                                     }
/* 3349 */                                     out.write(10);
/* 3350 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3351 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3355 */                                 if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3356 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                                 }
/*      */                                 
/* 3359 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3360 */                                 out.write(10);
/* 3361 */                                 out.write(32);
/* 3362 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3363 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3367 */                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3368 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                             }
/*      */                             
/* 3371 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3372 */                             out.write(10);
/* 3373 */                             out.write(32);
/* 3374 */                             out.write(10);
/*      */                             
/* 3376 */                             IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3377 */                             _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3378 */                             _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3380 */                             _jspx_th_c_005fif_005f16.setTest("${(!empty ADMIN || !empty DEMO) }");
/* 3381 */                             int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3382 */                             if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                               for (;;) {
/* 3384 */                                 out.write("\n  <tr>\n");
/*      */                                 
/* 3386 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3387 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3388 */                                 _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f16);
/*      */                                 
/* 3390 */                                 _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3391 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3392 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3394 */                                     out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3395 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3396 */                                     out.write("</a> </td>\n  ");
/* 3397 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3398 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3402 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3403 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3406 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3407 */                                 out.write("\n        ");
/*      */                                 
/* 3409 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3410 */                                 _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3411 */                                 _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_c_005fif_005f16);
/*      */                                 
/* 3413 */                                 _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 3414 */                                 int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 3415 */                                 if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3417 */                                     out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                     
/* 3419 */                                     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3420 */                                     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3421 */                                     _jspx_th_c_005fif_005f17.setParent(_jspx_th_logic_005fnotPresent_005f4);
/*      */                                     
/* 3423 */                                     _jspx_th_c_005fif_005f17.setTest("${param.actionmethod!='editScript' }");
/* 3424 */                                     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3425 */                                     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                       for (;;) {
/* 3427 */                                         out.write("\n    ");
/*      */                                         
/* 3429 */                                         String temphaid = request.getParameter("haid");
/*      */                                         try
/*      */                                         {
/* 3432 */                                           Integer.parseInt(temphaid);
/*      */                                           
/* 3434 */                                           if (rtype.equals("Ping Monitor")) {
/* 3435 */                                             out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                           } else {
/* 3437 */                                             out.write("\n\n<a href=\"/updateScript.do?haid=");
/* 3438 */                                             if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3440 */                                             out.write("&method=editScript");
/* 3441 */                                             out.print(appendParams);
/* 3442 */                                             out.write("&resourceid=");
/* 3443 */                                             if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3445 */                                             out.write("&type=");
/* 3446 */                                             out.print(rtype);
/* 3447 */                                             if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3449 */                                             out.write("&resourcename=");
/* 3450 */                                             out.print(resourceName);
/* 3451 */                                             out.write("&mtype=");
/* 3452 */                                             out.print(mon_type);
/* 3453 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                           }
/*      */                                           
/*      */                                         }
/*      */                                         catch (NumberFormatException ne)
/*      */                                         {
/* 3459 */                                           if (rtype.equals("Ping Monitor")) {
/* 3460 */                                             out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                           } else {
/* 3462 */                                             out.write("\n\n\n<a href=\"/updateScript.do?method=editScript");
/* 3463 */                                             out.print(appendParams);
/* 3464 */                                             out.write("&resourceid=");
/* 3465 */                                             if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3467 */                                             out.write("&type=");
/* 3468 */                                             out.print(rtype);
/* 3469 */                                             if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3471 */                                             out.write("&resourcename=");
/* 3472 */                                             out.print(resourceName);
/* 3473 */                                             out.write("&mtype=");
/* 3474 */                                             out.print(mon_type);
/* 3475 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 3479 */                                         out.write(10);
/* 3480 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 3481 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3485 */                                     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 3486 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                                     }
/*      */                                     
/* 3489 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 3490 */                                     out.write("\n\n    ");
/* 3491 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3492 */                                     out.write("\n    ");
/* 3493 */                                     if (_jspx_meth_c_005fif_005f18(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                       return;
/* 3495 */                                     out.write("</td>\n");
/* 3496 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 3497 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3501 */                                 if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 3502 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 3505 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 3506 */                                 out.write("\n  </tr>\n  ");
/* 3507 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3508 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3512 */                             if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3513 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                             }
/*      */                             
/* 3516 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3517 */                             out.write("\n\n  ");
/*      */                             
/* 3519 */                             IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3520 */                             _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3521 */                             _jspx_th_c_005fif_005f19.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3523 */                             _jspx_th_c_005fif_005f19.setTest("${!empty ADMIN || !empty DEMO }");
/* 3524 */                             int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3525 */                             if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                               for (;;) {
/* 3527 */                                 out.write(10);
/*      */                                 
/* 3529 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3530 */                                 _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 3531 */                                 _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3533 */                                 _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 3534 */                                 int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 3535 */                                 if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3537 */                                     out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 3538 */                                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3539 */                                     out.write("</a></td>\n  </tr>\n");
/* 3540 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 3541 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3545 */                                 if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 3546 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3549 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 3550 */                                 out.write(10);
/*      */                                 
/* 3552 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3553 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3554 */                                 _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3556 */                                 _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 3557 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3558 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;) {
/* 3560 */                                     out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3561 */                                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3562 */                                     out.write("</a></td>\n\n");
/* 3563 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3564 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3568 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3569 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                 }
/*      */                                 
/* 3572 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3573 */                                 out.write("\n\n  ");
/* 3574 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3575 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3579 */                             if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3580 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                             }
/*      */                             
/* 3583 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3584 */                             out.write(10);
/* 3585 */                             out.write(32);
/* 3586 */                             out.write(32);
/*      */                             
/* 3588 */                             IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3589 */                             _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3590 */                             _jspx_th_c_005fif_005f20.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3592 */                             _jspx_th_c_005fif_005f20.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 3593 */                             int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3594 */                             if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                               for (;;) {
/* 3596 */                                 out.write("\n\n  <tr>\n\n  ");
/*      */                                 
/* 3598 */                                 if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                 {
/*      */ 
/* 3601 */                                   out.write(10);
/* 3602 */                                   out.write(9);
/*      */                                   
/* 3604 */                                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3605 */                                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3606 */                                   _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3608 */                                   _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 3609 */                                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3610 */                                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 3612 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3613 */                                       out.print(FormatUtil.getString("Manage"));
/* 3614 */                                       out.write("</a> </td>\n  ");
/* 3615 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3616 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3620 */                                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3621 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 3624 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3625 */                                   out.write("\n        ");
/*      */                                   
/* 3627 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3628 */                                   _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 3629 */                                   _jspx_th_logic_005fnotPresent_005f6.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3631 */                                   _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 3632 */                                   int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 3633 */                                   if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 3635 */                                       out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 3636 */                                       out.print(FormatUtil.getString("Manage"));
/* 3637 */                                       out.write("</A></td>\n");
/* 3638 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 3639 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3643 */                                   if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 3644 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                                   }
/*      */                                   
/* 3647 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 3648 */                                   out.write("\n    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3654 */                                   out.write(10);
/*      */                                   
/* 3656 */                                   PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3657 */                                   _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3658 */                                   _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3660 */                                   _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 3661 */                                   int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3662 */                                   if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                     for (;;) {
/* 3664 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3665 */                                       out.print(FormatUtil.getString("UnManage"));
/* 3666 */                                       out.write("</a> </td>\n  ");
/* 3667 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3668 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3672 */                                   if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3673 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                   }
/*      */                                   
/* 3676 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3677 */                                   out.write("\n        ");
/*      */                                   
/* 3679 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3680 */                                   _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 3681 */                                   _jspx_th_logic_005fnotPresent_005f7.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3683 */                                   _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 3684 */                                   int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 3685 */                                   if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 3687 */                                       out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 3688 */                                       out.print(FormatUtil.getString("UnManage"));
/* 3689 */                                       out.write("</A></td>\n");
/* 3690 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 3691 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3695 */                                   if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 3696 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 3699 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 3700 */                                   out.write("\n    ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3704 */                                 out.write("\n  </tr>\n  ");
/* 3705 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3706 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3710 */                             if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3711 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                             }
/*      */                             
/* 3714 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3715 */                             out.write("\n   ");
/*      */                             
/* 3717 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3718 */                             _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 3719 */                             _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3721 */                             _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 3722 */                             int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 3723 */                             if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                               for (;;) {
/* 3725 */                                 out.write("\n    ");
/*      */                                 
/* 3727 */                                 String resourceid_poll = request.getParameter("resourceid");
/* 3728 */                                 String resourcetype_poll = request.getParameter("type");
/*      */                                 
/* 3730 */                                 out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3731 */                                 out.print(resourceid_poll);
/* 3732 */                                 out.write("&baseid=");
/* 3733 */                                 out.print(baseid);
/* 3734 */                                 out.write("&resourcetype=");
/* 3735 */                                 out.print(resourcetype_poll);
/* 3736 */                                 out.write("\" class=\"new-left-links\"> ");
/* 3737 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3738 */                                 out.write("</a></td>\n    </tr>\n    ");
/* 3739 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 3740 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3744 */                             if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 3745 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8); return;
/*      */                             }
/*      */                             
/* 3748 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 3749 */                             out.write("\n    ");
/*      */                             
/* 3751 */                             PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3752 */                             _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3753 */                             _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3755 */                             _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 3756 */                             int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3757 */                             if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                               for (;;) {
/* 3759 */                                 out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3760 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3761 */                                 out.write("</a></td>\n          </td>\n    ");
/* 3762 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3763 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3767 */                             if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3768 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                             }
/*      */                             
/* 3771 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3772 */                             out.write("\n    ");
/* 3773 */                             out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                             
/* 3775 */                             if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                             {
/* 3777 */                               Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3778 */                               String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                               
/* 3780 */                               String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3781 */                               String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3782 */                               if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                               {
/* 3784 */                                 if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                                 {
/*      */ 
/* 3787 */                                   out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3788 */                                   out.print(ciInfoUrl);
/* 3789 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3790 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3791 */                                   out.write("</a></td>");
/* 3792 */                                   out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3793 */                                   out.print(ciRLUrl);
/* 3794 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3795 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3796 */                                   out.write("</a></td>");
/* 3797 */                                   out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                                 }
/* 3801 */                                 else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                                 {
/*      */ 
/* 3804 */                                   out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3805 */                                   out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3806 */                                   out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3807 */                                   out.print(ciInfoUrl);
/* 3808 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3809 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3810 */                                   out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3811 */                                   out.print(ciRLUrl);
/* 3812 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3813 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3814 */                                   out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/* 3819 */                             out.write("\n \n \n\n");
/* 3820 */                             out.write("\n</table>\n\n <br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3821 */                             out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3822 */                             out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3823 */                             if (_jspx_meth_c_005fout_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3825 */                             out.write("&attributeid=");
/* 3826 */                             out.print(healthid);
/* 3827 */                             out.write("')\"\n      class=\"new-left-links\">");
/* 3828 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3829 */                             out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3830 */                             if (_jspx_meth_c_005fout_005f25(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3832 */                             out.write("&attributeid=");
/* 3833 */                             out.print(healthid);
/* 3834 */                             out.write("')\">");
/* 3835 */                             out.print(getSeverityImageForHealth(alert.getProperty(request.getParameter("resourceid") + "#" + healthid)));
/* 3836 */                             out.write("</a></td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3837 */                             if (_jspx_meth_c_005fout_005f26(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3839 */                             out.write("&attributeid=");
/* 3840 */                             out.print(availabilityid);
/* 3841 */                             out.write("')\"\n      class=\"new-left-links\">");
/* 3842 */                             out.print(FormatUtil.getString("Availability"));
/* 3843 */                             out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3844 */                             if (_jspx_meth_c_005fout_005f27(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3846 */                             out.write("&attributeid=");
/* 3847 */                             out.print(availabilityid);
/* 3848 */                             out.write("')\">");
/* 3849 */                             out.print(getSeverityImageForAvailability(alert.getProperty(request.getParameter("resourceid") + "#" + availabilityid)));
/* 3850 */                             out.write("</a></td>\n  </tr>\n</table>\n\n<br>\n\n<br>\n");
/* 3851 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                             
/*      */ 
/*      */ 
/* 3855 */                             boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3856 */                             if (EnterpriseUtil.isIt360MSPEdition)
/*      */                             {
/* 3858 */                               showAssociatedBSG = false;
/*      */                               
/*      */ 
/*      */ 
/* 3862 */                               CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3863 */                               CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3864 */                               String loginName = request.getUserPrincipal().getName();
/* 3865 */                               CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                               
/* 3867 */                               if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                               {
/* 3869 */                                 showAssociatedBSG = true;
/*      */                               }
/*      */                             }
/* 3872 */                             String monitorType = request.getParameter("type");
/* 3873 */                             ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3874 */                             boolean mon = conf1.isConfMonitor(monitorType);
/* 3875 */                             if (showAssociatedBSG)
/*      */                             {
/* 3877 */                               Hashtable associatedmgs = new Hashtable();
/* 3878 */                               String resId = request.getParameter("resourceid");
/* 3879 */                               request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3880 */                               if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                               {
/* 3882 */                                 mon = false;
/*      */                               }
/*      */                               
/* 3885 */                               if (!mon)
/*      */                               {
/* 3887 */                                 out.write(10);
/* 3888 */                                 out.write(10);
/*      */                                 
/* 3890 */                                 IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3891 */                                 _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 3892 */                                 _jspx_th_c_005fif_005f21.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3894 */                                 _jspx_th_c_005fif_005f21.setTest("${!empty associatedmgs}");
/* 3895 */                                 int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 3896 */                                 if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                   for (;;) {
/* 3898 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3899 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3900 */                                     out.write("</td>\n        </tr>\n        ");
/*      */                                     
/* 3902 */                                     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3903 */                                     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3904 */                                     _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f21);
/*      */                                     
/* 3906 */                                     _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                                     
/* 3908 */                                     _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                                     
/* 3910 */                                     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3911 */                                     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                     try {
/* 3913 */                                       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3914 */                                       if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                         for (;;) {
/* 3916 */                                           out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3917 */                                           if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3975 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3976 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3919 */                                           out.write("&method=showApplication\" title=\"");
/* 3920 */                                           if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3975 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3976 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3922 */                                           out.write("\"  class=\"new-left-links\">\n         ");
/* 3923 */                                           if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3975 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3976 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3925 */                                           out.write("\n    \t");
/* 3926 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3927 */                                           out.write("\n         </a></td>\n        <td>");
/*      */                                           
/* 3929 */                                           PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3930 */                                           _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3931 */                                           _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                           
/* 3933 */                                           _jspx_th_logic_005fpresent_005f10.setRole("ADMIN");
/* 3934 */                                           int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3935 */                                           if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                             for (;;) {
/* 3937 */                                               out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3938 */                                               if (_jspx_meth_c_005fout_005f31(_jspx_th_logic_005fpresent_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3975 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3976 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3940 */                                               out.write(39);
/* 3941 */                                               out.write(44);
/* 3942 */                                               out.write(39);
/* 3943 */                                               out.print(resId);
/* 3944 */                                               out.write(39);
/* 3945 */                                               out.write(44);
/* 3946 */                                               out.write(39);
/* 3947 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3948 */                                               out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3949 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3950 */                                               out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3951 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 3952 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3956 */                                           if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 3957 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3975 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3976 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3960 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 3961 */                                           out.write("</td>\n        </tr>\n\t");
/* 3962 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3963 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3967 */                                       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3975 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3976 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 3971 */                                         int tmp10870_10869 = 0; int[] tmp10870_10867 = _jspx_push_body_count_c_005fforEach_005f0; int tmp10872_10871 = tmp10870_10867[tmp10870_10869];tmp10870_10867[tmp10870_10869] = (tmp10872_10871 - 1); if (tmp10872_10871 <= 0) break;
/* 3972 */                                         out = _jspx_page_context.popBody(); }
/* 3973 */                                       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 3975 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3976 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                     }
/* 3978 */                                     out.write("\n      </table>\n ");
/* 3979 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 3980 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3984 */                                 if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 3985 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                                 }
/*      */                                 
/* 3988 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 3989 */                                 out.write(10);
/* 3990 */                                 out.write(32);
/*      */                                 
/* 3992 */                                 IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3993 */                                 _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 3994 */                                 _jspx_th_c_005fif_005f22.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3996 */                                 _jspx_th_c_005fif_005f22.setTest("${empty associatedmgs}");
/* 3997 */                                 int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 3998 */                                 if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                   for (;;) {
/* 4000 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 4001 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4002 */                                     out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 4003 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4004 */                                     out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 4005 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 4006 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4010 */                                 if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 4011 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                                 }
/*      */                                 
/* 4014 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 4015 */                                 out.write(10);
/* 4016 */                                 out.write(32);
/* 4017 */                                 out.write(10);
/*      */ 
/*      */                               }
/* 4020 */                               else if (mon)
/*      */                               {
/*      */ 
/*      */ 
/* 4024 */                                 out.write(10);
/*      */                                 
/* 4026 */                                 IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4027 */                                 _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 4028 */                                 _jspx_th_c_005fif_005f23.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 4030 */                                 _jspx_th_c_005fif_005f23.setTest("${!empty associatedmgs}");
/* 4031 */                                 int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 4032 */                                 if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                   for (;;) {
/* 4034 */                                     out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 4035 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                       return;
/* 4037 */                                     out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                     
/* 4039 */                                     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4040 */                                     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4041 */                                     _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f23);
/*      */                                     
/* 4043 */                                     _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                     
/* 4045 */                                     _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                     
/* 4047 */                                     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 4048 */                                     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                     try {
/* 4050 */                                       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4051 */                                       if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                         for (;;) {
/* 4053 */                                           out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 4054 */                                           if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4056 */                                           out.write("&method=showApplication\" title=\"");
/* 4057 */                                           if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4059 */                                           out.write("\"  class=\"staticlinks\">\n         ");
/* 4060 */                                           if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4062 */                                           out.write("\n    \t");
/* 4063 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4064 */                                           out.write("</a></span>\t\n\t\t ");
/*      */                                           
/* 4066 */                                           PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4067 */                                           _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 4068 */                                           _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                           
/* 4070 */                                           _jspx_th_logic_005fpresent_005f11.setRole("ADMIN");
/* 4071 */                                           int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 4072 */                                           if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                             for (;;) {
/* 4074 */                                               out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 4075 */                                               if (_jspx_meth_c_005fout_005f35(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 4077 */                                               out.write(39);
/* 4078 */                                               out.write(44);
/* 4079 */                                               out.write(39);
/* 4080 */                                               out.print(resId);
/* 4081 */                                               out.write(39);
/* 4082 */                                               out.write(44);
/* 4083 */                                               out.write(39);
/* 4084 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4085 */                                               out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 4086 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4087 */                                               out.write("\"  title=\"");
/* 4088 */                                               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 4090 */                                               out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 4091 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 4092 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4096 */                                           if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 4097 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4100 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4101 */                                           out.write("\n\n\t\t \t");
/* 4102 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4103 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4107 */                                       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 4111 */                                         int tmp11896_11895 = 0; int[] tmp11896_11893 = _jspx_push_body_count_c_005fforEach_005f1; int tmp11898_11897 = tmp11896_11893[tmp11896_11895];tmp11896_11893[tmp11896_11895] = (tmp11898_11897 - 1); if (tmp11898_11897 <= 0) break;
/* 4112 */                                         out = _jspx_page_context.popBody(); }
/* 4113 */                                       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 4115 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     }
/* 4118 */                                     out.write("\n\t\n\t\t\t</td>\n\t ");
/* 4119 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 4120 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4124 */                                 if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 4125 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                                 }
/*      */                                 
/* 4128 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 4129 */                                 out.write(10);
/* 4130 */                                 out.write(32);
/* 4131 */                                 if (_jspx_meth_c_005fif_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 4133 */                                 out.write(32);
/* 4134 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */                             }
/* 4138 */                             else if (mon)
/*      */                             {
/*      */ 
/* 4141 */                               out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4142 */                               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 4144 */                               out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 4148 */                             out.write(9);
/* 4149 */                             out.write(9);
/* 4150 */                             out.write(10);
/*      */                             
/* 4152 */                             ArrayList menupos = new ArrayList(5);
/* 4153 */                             menupos.add("350");
/* 4154 */                             pageContext.setAttribute("menupos", menupos);
/*      */                             
/* 4156 */                             out.write(10);
/* 4157 */                             out.write(10);
/* 4158 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4159 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4162 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4163 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4166 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4167 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 4170 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4171 */                         out.write(10);
/*      */                         
/* 4173 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4174 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4175 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 4177 */                         _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                         
/* 4179 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 4180 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4181 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 4182 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4183 */                             out = _jspx_page_context.pushBody();
/* 4184 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 4185 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4188 */                             out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n        ");
/*      */                             
/* 4190 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 4191 */                             String scriptname = (String)request.getAttribute("scriptname");
/* 4192 */                             String aid = request.getParameter("haid");
/* 4193 */                             String haName = null;
/* 4194 */                             if (aid != null)
/*      */                             {
/* 4196 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 4199 */                             out.write("\n        ");
/*      */                             
/* 4201 */                             IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4202 */                             _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 4203 */                             _jspx_th_c_005fif_005f25.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4205 */                             _jspx_th_c_005fif_005f25.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4206 */                             int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 4207 */                             if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                               for (;;) {
/* 4209 */                                 out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4210 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 4211 */                                 out.write(" &gt; ");
/* 4212 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes("File System Monitor"));
/* 4213 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 4214 */                                 out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 4215 */                                 out.write(" </span></td>\n        ");
/* 4216 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 4217 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4221 */                             if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 4222 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                             }
/*      */                             
/* 4225 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 4226 */                             out.write(10);
/* 4227 */                             String type = request.getParameter("type");
/*      */                             
/* 4229 */                             out.write("\n        ");
/*      */                             
/* 4231 */                             IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4232 */                             _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 4233 */                             _jspx_th_c_005fif_005f26.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4235 */                             _jspx_th_c_005fif_005f26.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4236 */                             int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 4237 */                             if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                               for (;;) {
/* 4239 */                                 out.write("\n   <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4240 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 4241 */                                 out.write(" &gt; ");
/* 4242 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes("File System Monitor"));
/* 4243 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 4244 */                                 out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 4245 */                                 out.write("</span></td>\n        ");
/* 4246 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 4247 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4251 */                             if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 4252 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                             }
/*      */                             
/* 4255 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 4256 */                             out.write("\n    </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n  <td width=\"55%\" height=\"123\" valign=\"top\"> <table width=\"96%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n  <tr>\n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 4257 */                             out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 4258 */                             out.write(" </td>\n        </tr>\n        <tr>\n          <td width=\"32%\" class=\"monitorinfoeven\">");
/* 4259 */                             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4260 */                             out.write(" </td>\n          <td width=\"68%\" class=\"monitorinfoeven\"> ");
/* 4261 */                             out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 4262 */                             out.write("\n          </td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 4263 */                             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 4264 */                             out.write(" </td>\n          <td class=\"monitorinfoeven\">");
/* 4265 */                             out.print(FormatUtil.getString("File / Directory Monitor"));
/* 4266 */                             out.write("</td>\n        </tr>\n        ");
/* 4267 */                             out.write("<!--$Id$-->\n");
/*      */                             
/* 4269 */                             String hostName = "localhost";
/*      */                             try {
/* 4271 */                               hostName = InetAddress.getLocalHost().getHostName();
/*      */                             } catch (Exception ex) {
/* 4273 */                               ex.printStackTrace();
/*      */                             }
/* 4275 */                             String portNumber = System.getProperty("webserver.port");
/* 4276 */                             String styleClass = "monitorinfoodd";
/* 4277 */                             if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 4278 */                               styleClass = "whitegrayborder-conf-mon";
/*      */                             }
/*      */                             
/* 4281 */                             out.write(10);
/*      */                             
/* 4283 */                             PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4284 */                             _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 4285 */                             _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4287 */                             _jspx_th_logic_005fpresent_005f12.setRole("ENTERPRISEADMIN");
/* 4288 */                             int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 4289 */                             if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                               for (;;) {
/* 4291 */                                 out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 4292 */                                 out.print(styleClass);
/* 4293 */                                 out.write(34);
/* 4294 */                                 out.write(62);
/* 4295 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 4296 */                                 out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 4297 */                                 out.print(styleClass);
/* 4298 */                                 out.write(34);
/* 4299 */                                 out.write(62);
/* 4300 */                                 out.print(hostName);
/* 4301 */                                 out.write(95);
/* 4302 */                                 out.print(portNumber);
/* 4303 */                                 out.write("</td>\n</tr>\n");
/* 4304 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 4305 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4309 */                             if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 4310 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                             }
/*      */                             
/* 4313 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 4314 */                             out.write(10);
/* 4315 */                             out.write("\n        <tr>\n\t<td class=\"monitorinfoeven\" valign=\"top\">");
/* 4316 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4317 */                             out.write("</td>\n\t<td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4318 */                             out.print(resID);
/* 4319 */                             out.write("&attributeid=");
/* 4320 */                             out.print(hid);
/* 4321 */                             out.write("')\">");
/* 4322 */                             out.print(getSeverityImageForHealth(alert.getProperty(resID + "#" + hid)));
/* 4323 */                             out.write("</a>\n                  ");
/* 4324 */                             out.print(getHideAndShowRCAMessage(alert.getProperty(resID + "#" + hid + "#" + "MESSAGE"), hid, alert.getProperty(resID + "#" + hid), resID));
/* 4325 */                             out.write("\n                  ");
/* 4326 */                             if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resID, hid) != 0) {
/* 4327 */                               out.write("\n                  <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 4328 */                               out.print(resID + "_" + hid);
/* 4329 */                               out.write("&monitortype=");
/* 4330 */                               out.print(resourcetype);
/* 4331 */                               out.write("')\">");
/* 4332 */                               out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 4333 */                               out.write("</a></span>\n                  ");
/*      */                             }
/* 4335 */                             out.write("\n        </td>\n</tr>\n                <tr>\n                <td class=\"monitorinfoeven\" valign=\"top\">");
/* 4336 */                             out.print(FormatUtil.getString(fname));
/* 4337 */                             out.write("</td>\n                <td class=\"monitorinfoeven\" title=\"");
/* 4338 */                             out.print(scriptname);
/* 4339 */                             out.write(34);
/* 4340 */                             out.write(62);
/* 4341 */                             out.print(getTrimmedText(scriptname, 30));
/* 4342 */                             out.write("</td>\n\n                </tr>\n\t");
/*      */                             
/* 4344 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4345 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 4346 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4348 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("content");
/* 4349 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 4350 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 4352 */                                 out.write("\n\n\t\t");
/* 4353 */                                 String content = (String)request.getAttribute("content");
/* 4354 */                                 if ((!content.equals("-NA-")) && (!content.equals("null"))) {
/* 4355 */                                   out.write("\n\n\t <td class=\"monitorinfoeven\" valign=\"top\">");
/* 4356 */                                   out.print(FormatUtil.getString("Content"));
/* 4357 */                                   out.write("</td>\n\t\t<td class=\"monitorinfoeven\" title=\"");
/* 4358 */                                   out.print(content);
/* 4359 */                                   out.write(34);
/* 4360 */                                   out.write(62);
/* 4361 */                                   out.print(getTrimmedText(content, 46));
/* 4362 */                                   out.write("</td>\n\t");
/*      */                                 }
/* 4364 */                                 out.write(10);
/* 4365 */                                 out.write(9);
/* 4366 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 4367 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4371 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 4372 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 4375 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 4376 */                             out.write("\n                ");
/*      */                             
/* 4378 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4379 */                             _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 4380 */                             _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4382 */                             _jspx_th_logic_005fnotEmpty_005f1.setName("hostname");
/* 4383 */                             int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 4384 */                             if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                               for (;;) {
/* 4386 */                                 out.write("\n\t\t<tr>\n                <td class=\"monitorinfoeven\" valign=\"top\">");
/* 4387 */                                 out.print(FormatUtil.getString("am.webclient.script.remoteserver"));
/* 4388 */                                 out.write("</td>\n                        <td class=\"monitorinfoeven\">");
/* 4389 */                                 if (_jspx_meth_c_005fout_005f36(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/*      */                                   return;
/* 4391 */                                 out.write("</td>\n\t\t</tr>\n                ");
/* 4392 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 4393 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4397 */                             if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 4398 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                             }
/*      */                             
/* 4401 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 4402 */                             out.write("\n\n\n        ");
/*      */                             
/* 4404 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4405 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 4406 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4408 */                             _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 4409 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 4410 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 4412 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 4413 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4414 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 4415 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4416 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        ");
/* 4417 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 4418 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4422 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 4423 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 4426 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 4427 */                             out.write("\n        ");
/*      */                             
/* 4429 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4430 */                             _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 4431 */                             _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4433 */                             _jspx_th_logic_005fnotEmpty_005f2.setName("systeminfo");
/* 4434 */                             int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 4435 */                             if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                               for (;;) {
/* 4437 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 4438 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4439 */                                 out.write("</td>\n\t\t");
/* 4440 */                                 if (systeminfo.get("LASTDC") != null) {
/* 4441 */                                   out.write("\n          <td class=\"monitorinfoeven\">");
/* 4442 */                                   out.print(formatDT(((Long)systeminfo.get("LASTDC")).toString()));
/* 4443 */                                   out.write("</td>\n\t\t");
/*      */                                 } else {
/* 4445 */                                   out.write("\n\t\t\t<td class=\"monitorinfoeven\">");
/* 4446 */                                   out.print("-");
/* 4447 */                                   out.write("</td>\n\t\t");
/*      */                                 }
/* 4449 */                                 out.write("\n\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 4450 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4451 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 4452 */                                 out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 4453 */                                 out.write("</td>\n        </tr>\n        ");
/* 4454 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 4455 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4459 */                             if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 4460 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                             }
/*      */                             
/* 4463 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 4464 */                             out.write("\n        ");
/* 4465 */                             out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 4466 */                             out.write("\n\t{\n\t\t");
/* 4467 */                             if (_jspx_meth_c_005fif_005f27(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4469 */                             out.write(10);
/* 4470 */                             out.write(9);
/* 4471 */                             out.write(9);
/* 4472 */                             if (_jspx_meth_c_005fif_005f28(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4474 */                             out.write("\n\t\tgetCustomFields('");
/* 4475 */                             if (_jspx_meth_c_005fout_005f39(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4477 */                             out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 4478 */                             out.write("\n\t}\n\n});\n</script>\n");
/* 4479 */                             if (_jspx_meth_c_005fif_005f29(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4481 */                             out.write(10);
/* 4482 */                             out.write(10);
/* 4483 */                             if (_jspx_meth_c_005fif_005f30(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4485 */                             out.write(10);
/* 4486 */                             out.write(10);
/* 4487 */                             if (_jspx_meth_c_005fset_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4489 */                             out.write(10);
/* 4490 */                             if (_jspx_meth_c_005fset_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4492 */                             out.write(10);
/* 4493 */                             out.write(10);
/* 4494 */                             out.write(10);
/* 4495 */                             if (_jspx_meth_c_005fif_005f31(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4497 */                             out.write(10);
/* 4498 */                             out.write(10);
/* 4499 */                             out.write(10);
/* 4500 */                             if (_jspx_meth_c_005fif_005f32(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4502 */                             out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 4503 */                             if (_jspx_meth_c_005fout_005f46(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4505 */                             out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 4506 */                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4508 */                             out.write("\" onclick=\"getCustomFields('");
/* 4509 */                             if (_jspx_meth_c_005fout_005f47(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4511 */                             out.write(39);
/* 4512 */                             out.write(44);
/* 4513 */                             out.write(39);
/* 4514 */                             if (_jspx_meth_c_005fout_005f48(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4516 */                             out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 4517 */                             out.write("\n</td>\n</tr>\n\n\n");
/* 4518 */                             out.write("\n    </table>\n  </td>\n  <td width=\"43%\" valign=\"top\"><table align=left width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n          <td height=\"28\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 4519 */                             out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 4520 */                             out.write("</td>\n        </tr>\n <tr>\n          <td colspan=\"3\"> <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">                                                            <tr>\n             <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4521 */                             if (_jspx_meth_c_005fout_005f49(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4523 */                             out.write("&period=1&resourcename=");
/* 4524 */                             if (_jspx_meth_c_005fout_005f50(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4526 */                             out.write("')\">                                                                                                                               <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4527 */                             out.print(seven_days_text);
/* 4528 */                             out.write("\"></a></td>\n                            <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4529 */                             if (_jspx_meth_c_005fout_005f51(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4531 */                             out.write("&period=2&resourcename=");
/* 4532 */                             if (_jspx_meth_c_005fout_005f52(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4534 */                             out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4535 */                             out.print(thiry_days_text);
/* 4536 */                             out.write("\"\n></a></td>                                                                                                                                </tr>\n            </table></td>                                                                                                             ");
/*      */                             
/* 4538 */                             wlsGraph.setParam(resID, "AVAILABILITY");
/* 4539 */                             out.write("\n        </tr>                                                                                                                       <tr align=\"center\">\n          <td height=\"28\" colspan=\"3\" class=\"whitegrayborder\"> ");
/* 4540 */                             if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4542 */                             out.write("</td>\n        </tr>                                                                                                                       <tr>\n\n         <td width=\"49%\"  class=\"yellowgrayborder\" colspan=\"2\" title=\"");
/* 4543 */                             out.print(tipCurStatus);
/* 4544 */                             out.write(34);
/* 4545 */                             out.write(62);
/* 4546 */                             out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 4547 */                             out.write(" :\n          <a style=\"position:relative; top:2px;\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4548 */                             out.print(resID);
/* 4549 */                             out.write("&attributeid=");
/* 4550 */                             out.print(aid1);
/* 4551 */                             out.write("&alertconfigurl=");
/* 4552 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "&attributeIDs=" + aid1 + "&attributeToSelect=" + aid1 + "&redirectto=" + encodeurl));
/* 4553 */                             out.write("')\">\n             ");
/* 4554 */                             out.print(getSeverityImageForAvailability(alert.getProperty(resID + "#" + aid1)));
/* 4555 */                             out.write("                                      </a></td>\n         <td width=\"51%\" align=\"left\" class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4556 */                             out.print(resID);
/* 4557 */                             out.write("&attributeIDs=");
/* 4558 */                             out.print(aid1);
/* 4559 */                             out.write(44);
/* 4560 */                             out.print(hid);
/* 4561 */                             out.write("&attributeToSelect=");
/* 4562 */                             out.print(aid1);
/* 4563 */                             out.write("&redirectto=");
/* 4564 */                             out.print(encodeurl);
/* 4565 */                             out.write("\" class=\"links\">");
/* 4566 */                             out.print(ALERTCONFIG_TEXT);
/* 4567 */                             out.write("</a>&nbsp;\n           </td>\n</tr>\n      </table></td>\n  </tr>\n</table>\n\n <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 4568 */                             out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 4569 */                             out.write("</td></tr></table>\n");
/*      */                             
/* 4571 */                             String class1 = null;
/* 4572 */                             String class2 = null;
/* 4573 */                             if ((request.getParameter("fromhost") != null) && (request.getParameter("fromhost").equals("true")))
/*      */                             {
/* 4575 */                               class1 = "lrbborder";
/* 4576 */                               class2 = "tablebottom";
/*      */                             }
/*      */                             else
/*      */                             {
/* 4580 */                               class1 = "lrtbdarkborder";
/* 4581 */                               class2 = "tableheading";
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/* 4586 */                             long curvalue = -1L;
/* 4587 */                             String temp = "-1";
/* 4588 */                             if (request.getAttribute("responsetime") != null)
/*      */                             {
/* 4590 */                               temp = (String)request.getAttribute("responsetime");
/*      */                             }
/* 4592 */                             String tipCurValue = "The time taken to get a response from this service.";
/* 4593 */                             wlsGraph.setParam(resID, "RESPONSETIME");
/* 4594 */                             String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 4595 */                             String yaxis_restime = FormatUtil.getString("am.webclient.common.responsetime.text") + " " + FormatUtil.getString("am.webclient.common.units.ms.text");
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4611 */                             int rid = Integer.parseInt(resourceid);
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4624 */                             camGraph.setParam(0, mtype, attribute, rid);
/*      */                             
/* 4626 */                             out.write("\n\n<table border=\"0\">\n<Tr><td>&nbsp;</td></tr>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"");
/* 4627 */                             out.print(class1);
/* 4628 */                             out.write("\">\n  <tr>\n  <!--td height=\"26\" width=\"50%\" class=\"tableheading\">");
/* 4629 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 4630 */                             out.write(32);
/* 4631 */                             out.write(45);
/* 4632 */                             out.write(32);
/* 4633 */                             out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4634 */                             out.write("</td-->\n <td height=\"26\"  align=\"left\" class=\"tableheading\">");
/* 4635 */                             out.print(FormatUtil.getString(theading));
/* 4636 */                             out.write("</td>\n\n  </tr>\n</table>\n\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n<!--tr>\n   <td width=\"50%\" height=\"127\" valign=\"top\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" class=\"rbborder\">\n       <tr>\n       <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4637 */                             out.print(resID);
/* 4638 */                             out.write("&attributeid=");
/* 4639 */                             out.print(responsetimeid);
/* 4640 */                             out.write("&period=-7',740,550)\">\n          <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4641 */                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4642 */                             out.write("\"></a></td>\n        <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4643 */                             out.print(resID);
/* 4644 */                             out.write("&attributeid=");
/* 4645 */                             out.print(responsetimeid);
/* 4646 */                             out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4647 */                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4648 */                             out.write("\"></a></td>\n       </tr>\n       <tr>\n        <td colspan=\"2\"> ");
/*      */                             
/* 4650 */                             TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4651 */                             _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 4652 */                             _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4654 */                             _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wlsGraph");
/*      */                             
/* 4656 */                             _jspx_th_awolf_005ftimechart_005f0.setWidth("320");
/*      */                             
/* 4658 */                             _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                             
/* 4660 */                             _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                             
/* 4662 */                             _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                             
/* 4664 */                             _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_restime);
/*      */                             
/* 4666 */                             _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 4667 */                             int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 4668 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 4669 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4670 */                                 out = _jspx_page_context.pushBody();
/* 4671 */                                 _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 4672 */                                 _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4675 */                                 out.write("\n            ");
/* 4676 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 4677 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4680 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4681 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4684 */                             if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 4685 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                             }
/*      */                             
/* 4688 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 4689 */                             out.write(" </td>\n        </tr>\n      </table></td>\n<td width=\"50%\" height=\"127\" valign=\"top\" class=\"bottomborder\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" >\n<tr>\n<td height=\"35\"></td></tr-->\n\n");
/* 4690 */                             if ((resourcetype.equals("file")) || (mtype.equals("file"))) {
/* 4691 */                               out.write("\n<tr>\n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4692 */                               out.print(resID);
/* 4693 */                               out.write("&attributeid=6010&period=-7',740,550)\">\n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4694 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4695 */                               out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4696 */                               out.print(resID);
/* 4697 */                               out.write("&attributeid=6010&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4698 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4699 */                               out.write("\"></a></td>\n </tr>\n\n <tr>\n\n\n       <td colspan=\"2\" align=\"center\"> ");
/*      */                               
/* 4701 */                               TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4702 */                               _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 4703 */                               _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 4705 */                               _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("camGraph");
/*      */                               
/* 4707 */                               _jspx_th_awolf_005ftimechart_005f1.setWidth("500");
/*      */                               
/* 4709 */                               _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                               
/* 4711 */                               _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                               
/* 4713 */                               _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                               
/* 4715 */                               _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(funit);
/*      */                               
/* 4717 */                               _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 4718 */                               int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 4719 */                               if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 4720 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 4721 */                                   out = _jspx_page_context.pushBody();
/* 4722 */                                   _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 4723 */                                   _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 4726 */                                   out.write("\n            ");
/* 4727 */                                   int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 4728 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4731 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 4732 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4735 */                               if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 4736 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                               }
/*      */                               
/* 4739 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 4740 */                               out.write(" </td>\n        </tr>\n\n");
/* 4741 */                             } else if ((resourcetype.equals("directory")) || (mtype.equals("directory"))) {
/* 4742 */                               out.write("\n\n\t<tr>\n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4743 */                               out.print(resID);
/* 4744 */                               out.write("&attributeid=6002&period=-7',740,550)\">\n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4745 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4746 */                               out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4747 */                               out.print(resID);
/* 4748 */                               out.write("&attributeid=6002&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4749 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4750 */                               out.write("\"></a></td>\n        </tr>\n\n\n <tr>\n\n       <td colspan=\"2\" align=\"center\"> ");
/*      */                               
/* 4752 */                               TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4753 */                               _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 4754 */                               _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 4756 */                               _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("camGraph");
/*      */                               
/* 4758 */                               _jspx_th_awolf_005ftimechart_005f2.setWidth("500");
/*      */                               
/* 4760 */                               _jspx_th_awolf_005ftimechart_005f2.setHeight("170");
/*      */                               
/* 4762 */                               _jspx_th_awolf_005ftimechart_005f2.setLegend("false");
/*      */                               
/* 4764 */                               _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(xaxis_time);
/*      */                               
/* 4766 */                               _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(dunit);
/*      */                               
/* 4768 */                               _jspx_th_awolf_005ftimechart_005f2.setDateFormat("HH:mm");
/* 4769 */                               int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 4770 */                               if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 4771 */                                 if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 4772 */                                   out = _jspx_page_context.pushBody();
/* 4773 */                                   _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 4774 */                                   _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 4777 */                                   out.write("\n            ");
/* 4778 */                                   int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 4779 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4782 */                                 if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 4783 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4786 */                               if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 4787 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                               }
/*      */                               
/* 4790 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 4791 */                               out.write(" </td>\n        </tr>\n\n");
/*      */                             }
/* 4793 */                             out.write("\n    <!--td width=\"50%\" valign=\"top\"> <br> <br>\n<tr-->\n<!--td class=\"rborder\" valign=\"top\">\n     <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n       <tr>\n         <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4794 */                             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4796 */                             out.write("</span></td>\n         <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4797 */                             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4799 */                             out.write("</span></td>\n         <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">\n          ");
/*      */                             
/* 4801 */                             if (!temp.equals("-1"))
/*      */                             {
/* 4803 */                               out.write("\n          ");
/* 4804 */                               if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4806 */                               out.write("</span>\n");
/*      */                             } else {
/* 4808 */                               out.println("&nbsp;");
/*      */                             }
/* 4810 */                             out.write("\n          </td>\n        </tr>\n        <tr>\n\n        <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 4811 */                             out.print(tipCurValue);
/* 4812 */                             out.write(34);
/* 4813 */                             out.write(62);
/* 4814 */                             out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 4815 */                             out.write(32);
/* 4816 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 4817 */                             out.write("</td>\n          ");
/*      */                             
/* 4819 */                             if (!temp.equals("-1"))
/*      */                             {
/*      */ 
/* 4822 */                               out.write("\n         <td  height=\"25\" class=\"whitegrayborder\">");
/* 4823 */                               out.print(temp);
/* 4824 */                               out.write(32);
/* 4825 */                               out.print(FormatUtil.getString("ms"));
/* 4826 */                               out.write("</td>\n\n            ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 4832 */                               out.write("\n         <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 4833 */                               out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 4834 */                               out.write("\n</td>\n            ");
/*      */                             }
/*      */                             
/* 4837 */                             out.write("\n         ");
/*      */                             
/* 4839 */                             if (!temp.equals("-1"))
/*      */                             {
/*      */ 
/* 4842 */                               out.write("\n        <Td width=\"16%\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4843 */                               out.print(resID);
/* 4844 */                               out.write("&attributeid=");
/* 4845 */                               out.print(responsetimeid);
/* 4846 */                               out.write("&alertconfigurl=");
/* 4847 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + responsetimeid + "attributeToSelect=" + responsetimeid + "&redirectto=" + encodeurl));
/* 4848 */                               out.write("')\">");
/* 4849 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + responsetimeid)));
/* 4850 */                               out.write(" </a>\n          </td>\n          ");
/*      */                             }
/*      */                             
/*      */ 
/* 4854 */                             out.write("\n        </tr>\n        <tr>\n<td  colspan=\"4\" height=\"35\" class=\"yellowgrayborder\" align=\"right\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4855 */                             out.print(resID);
/* 4856 */                             out.write("&attributeIDs=");
/* 4857 */                             out.print(responsetimeid);
/* 4858 */                             out.write("&attributeToSelect=");
/* 4859 */                             out.print(responsetimeid);
/* 4860 */                             out.write("&redirectto=");
/* 4861 */                             out.print(encodeurl);
/* 4862 */                             out.write("\" class=\"staticlinks\">");
/* 4863 */                             out.print(ALERTCONFIG_TEXT);
/* 4864 */                             out.write("</a>&nbsp;</td>\n        </tr></table>\n</td-->\n\n<td  align=\"center\" colspan=\"2\">\n");
/* 4865 */                             if ((resourcetype.equals("file")) || (mtype.equals("file"))) {
/* 4866 */                               out.write("\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\" >\n        <tr>\n        <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4867 */                               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4869 */                               out.write("</span></td>\n        <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4870 */                               if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4872 */                               out.write("</span></td>\n        <td class=\"columnheadingnotop\"><span class=\"bodytextbold\" colspan=\"2\">");
/* 4873 */                               if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4875 */                               out.write("</span>\n\n</tr>\n");
/*      */                               
/*      */ 
/* 4878 */                               if (filedata != null) {
/* 4879 */                                 if (filedata.size() != 0) {
/* 4880 */                                   double fsize = ((Double)filedata.get("size")).doubleValue();
/* 4881 */                                   double fincsize = ((Double)filedata.get("sizevalueincreased")).doubleValue();
/*      */                                   
/* 4883 */                                   out.write("\n<tr>\n<td height=\"25\" class=\"whitegrayborder\">");
/* 4884 */                                   out.print(funit);
/* 4885 */                                   out.write("</td>\n");
/* 4886 */                                   if (fsize == -1.0D) {
/* 4887 */                                     out.write("\n\t<td height=\"25\" class=\"whitegrayborder\">");
/* 4888 */                                     out.print("-");
/* 4889 */                                     out.write("</td>\n");
/*      */                                   } else {
/* 4891 */                                     out.write("\n<td height=\"25\" class=\"whitegrayborder\">");
/* 4892 */                                     out.print(FormatUtil.formatNumber(Double.valueOf(fsize)));
/* 4893 */                                     out.write("</td>\n");
/*      */                                   }
/* 4895 */                                   out.write("\n<Td width=\"16%\" class=\"whitegrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4896 */                                   out.print(resID);
/* 4897 */                                   out.write("&attributeid=6010&alertconfigurl=");
/* 4898 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6010&attributeToSelect=6010&redirectto=" + encodeurl));
/* 4899 */                                   out.write("')\">");
/* 4900 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 6010)));
/* 4901 */                                   out.write(" </a></td>\n<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4902 */                                   out.print(resID);
/* 4903 */                                   out.write("&attributeIDs=6010&attributeToSelect=6010&redirectto=");
/* 4904 */                                   out.print(encodeurl);
/* 4905 */                                   out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=0></a>&nbsp;</td></tr></table>\n</td>\n</tr>\n<tr>\n<td height=\"25\" class=\"yellowgrayborder\">");
/* 4906 */                                   out.print(FormatUtil.getString("am.webclient.fincsizech.text"));
/* 4907 */                                   out.write(32);
/* 4908 */                                   out.write(40);
/* 4909 */                                   out.print(AMAutomaticPortChanger.getFileUnit());
/* 4910 */                                   out.write(")</td>\n");
/* 4911 */                                   if (fincsize == -1.0D) {
/* 4912 */                                     out.write("\n\t<td height=\"25\" class=\"whitegrayborder\">");
/* 4913 */                                     out.print("-");
/* 4914 */                                     out.write("</td>\n");
/*      */                                   } else {
/* 4916 */                                     out.write("\n\t<td height=\"25\" class=\"yellowgrayborder\">");
/* 4917 */                                     out.print(FormatUtil.formatNumber(Double.valueOf(fincsize)));
/* 4918 */                                     out.write("</td>\n");
/*      */                                   }
/* 4920 */                                   out.write("\n<Td width=\"16%\" class=\"yellowgrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4921 */                                   out.print(resID);
/* 4922 */                                   out.write("&attributeid=6012&alertconfigurl=");
/* 4923 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6012&attributeToSelect=6012&redirectto=" + encodeurl));
/* 4924 */                                   out.write("')\">");
/* 4925 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 6012)));
/* 4926 */                                   out.write(" </a></td>\n<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4927 */                                   out.print(resID);
/* 4928 */                                   out.write("&attributeIDs=6012&attributeToSelect=6012&redirectto=");
/* 4929 */                                   out.print(encodeurl);
/* 4930 */                                   out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=0></a>&nbsp;</td></tr></table>\n</td>\n</tr>\n<tr>\n<td height=\"25\" class=\"whitegrayborder\">");
/* 4931 */                                   out.print(FormatUtil.getString("am.webclient.fsizech.text"));
/* 4932 */                                   out.write("</td>\n");
/* 4933 */                                   if (fincsize == -1.0D) {
/* 4934 */                                     out.write("\n\t<td height=\"25\" class=\"whitegrayborder\">");
/* 4935 */                                     out.print("-");
/* 4936 */                                     out.write("</td>\n");
/*      */                                   } else {
/* 4938 */                                     out.write("\n\t<td height=\"25\" class=\"whitegrayborder\">");
/* 4939 */                                     out.print(filedata.get("sizeincreased"));
/* 4940 */                                     out.write("</td>\n");
/*      */                                   }
/* 4942 */                                   out.write("\n<Td width=\"16%\" class=\"whitegrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4943 */                                   out.print(resID);
/* 4944 */                                   out.write("&attributeid=6011&alertconfigurl=");
/* 4945 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6011&attributeToSelect=6011&redirectto=" + encodeurl));
/* 4946 */                                   out.write("')\">");
/* 4947 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 6011)));
/* 4948 */                                   out.write(" </a></td>\n<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4949 */                                   out.print(resID);
/* 4950 */                                   out.write("&attributeIDs=6011&attributeToSelect=6011&redirectto=");
/* 4951 */                                   out.print(encodeurl);
/* 4952 */                                   out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=0></a>&nbsp;</td></tr></table>\n</td>\n</tr>\n\n<tr>\n<td height=\"25\" class=\"yellowgrayborder\">");
/* 4953 */                                   out.print(FormatUtil.getString("am.webclient.lastmtime.text"));
/* 4954 */                                   out.write("</td>\n");
/* 4955 */                                   if ((filedata.get("mod") != null) && (!((String)filedata.get("mod")).equals("0"))) {
/* 4956 */                                     out.write("\n<td height=\"25\" class=\"yellowgrayborder\">");
/* 4957 */                                     out.print(formatDT((String)filedata.get("mod")));
/* 4958 */                                     out.write("</td>\n");
/*      */                                   } else {
/* 4960 */                                     out.write(10);
/* 4961 */                                     out.write(10);
/* 4962 */                                     out.write(10);
/*      */                                   }
/* 4964 */                                   out.write("\n<td  width=\"16%\" class=\"whitegrayborder\">&nbsp; </td>\n</tr>\n");
/* 4965 */                                   if (!((String)filedata.get("lastmodfile")).equals("-1")) {
/* 4966 */                                     String lastmodfile = (String)filedata.get("lastmodfile");
/*      */                                     
/* 4968 */                                     out.write("\n<tr>\n\t<td height=\"25\" class=\"whitegrayborder\">");
/* 4969 */                                     out.print(FormatUtil.getString("am.webclient.lastmodfile.text"));
/* 4970 */                                     out.write("</td>\n\t<td height=\"25\" class=\"whitegrayborder\" title=\"");
/* 4971 */                                     out.print(lastmodfile);
/* 4972 */                                     out.write(34);
/* 4973 */                                     out.write(62);
/* 4974 */                                     out.print(getTrimmedText(lastmodfile, 60));
/* 4975 */                                     out.write("</td>\n\t<td  width=\"16%\" class=\"whitegrayborder\">&nbsp; </td>\n</tr>\n");
/*      */                                   }
/*      */                                 } else {
/* 4978 */                                   out.write("\n\n<tr>\n<td height=\"25\" colspan=\"4\" class=\"yellowgrayborder\">");
/* 4979 */                                   out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4980 */                                   out.write("</td>\n</tr>\n");
/*      */                                 }
/*      */                               } else {
/* 4983 */                                 out.write("\n<tr>\n<td height=\"25\" colspan=\"4\" class=\"yellowgrayborder\">");
/* 4984 */                                 out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4985 */                                 out.write("</td>\n</tr>\n");
/*      */                               }
/* 4987 */                               out.write("\n\n</table>\n\n\n\n\n\n\n");
/* 4988 */                             } else if ((resourcetype.equals("directory")) || (mtype.equals("directory"))) {
/* 4989 */                               out.write("\n\n      <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n        <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4990 */                               if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4992 */                               out.write("</span>\n</td>\n        <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4993 */                               if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4995 */                               out.write("</span></td>\n        <td class=\"columnheadingnotop\"><span class=\"bodytextbold\" colspan=\"2\">");
/* 4996 */                               if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4998 */                               out.write("</span>\n\n</tr>\n");
/* 4999 */                               if (dirdata != null) {
/* 5000 */                                 if (dirdata.size() != 0) {
/* 5001 */                                   double dsize = ((Double)dirdata.get("size")).doubleValue();
/* 5002 */                                   double dincsize = ((Double)dirdata.get("sizevalueincreased")).doubleValue();
/*      */                                   
/* 5004 */                                   out.write("\n\n\n<tr>\n<td height=\"25\" class=\"whitegrayborder\">");
/* 5005 */                                   out.print(dunit);
/* 5006 */                                   out.write("</td>\n");
/* 5007 */                                   if (dsize == -1.0D) {
/* 5008 */                                     out.write("\n        <td height=\"25\" class=\"whitegrayborder\">");
/* 5009 */                                     out.print("-");
/* 5010 */                                     out.write("</td>\n");
/*      */                                   } else {
/* 5012 */                                     out.write("\n<td height=\"25\" class=\"whitegrayborder\">");
/* 5013 */                                     out.print(FormatUtil.formatNumber(Double.valueOf(dsize)));
/* 5014 */                                     out.write("</td>                                            ");
/*      */                                   }
/* 5016 */                                   out.write("\n<Td width=\"16%\" class=\"whitegrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5017 */                                   out.print(resID);
/* 5018 */                                   out.write("&attributeid=6002&alertconfigurl=");
/* 5019 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6002&attributeToSelect=6002&redirectto=" + encodeurl));
/* 5020 */                                   out.write("')\">");
/* 5021 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 6002)));
/* 5022 */                                   out.write(" </a></td>\n<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5023 */                                   out.print(resID);
/* 5024 */                                   out.write("&attributeIDs=6002&attributeToSelect=6002&redirectto=");
/* 5025 */                                   out.print(encodeurl);
/* 5026 */                                   out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=\"0\" ></a>&nbsp;</td></tr></table>\n</td>\n</tr>\n<tr>\n<td height=\"25\" class=\"yellowgrayborder\">");
/* 5027 */                                   out.print(FormatUtil.getString("am.webclient.dincsizech.text"));
/* 5028 */                                   out.write(32);
/* 5029 */                                   out.write(40);
/* 5030 */                                   out.print(AMAutomaticPortChanger.getDirUnit());
/* 5031 */                                   out.write(")</td>\n");
/* 5032 */                                   if (dsize == -1.0D) {
/* 5033 */                                     out.write("\n        <td height=\"25\" class=\"whitegrayborder\">");
/* 5034 */                                     out.print("-");
/* 5035 */                                     out.write("</td>\n");
/*      */                                   } else {
/* 5037 */                                     out.write("\n      <td height=\"25\" class=\"yellowgrayborder\">");
/* 5038 */                                     out.print(FormatUtil.formatNumber(Double.valueOf(dincsize)));
/* 5039 */                                     out.write("</td>\n");
/*      */                                   }
/* 5041 */                                   out.write("\n<td width=\"16%\" class=\"yellowgrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5042 */                                   out.print(resID);
/* 5043 */                                   out.write("&attributeid=6013&alertconfigurl=");
/* 5044 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6013&attributeToSelect=6013&redirectto=" + encodeurl));
/* 5045 */                                   out.write("')\">");
/* 5046 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 6013)));
/* 5047 */                                   out.write(" </a></td>\n<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5048 */                                   out.print(resID);
/* 5049 */                                   out.write("&attributeIDs=6013&attributeToSelect=6013&redirectto=");
/* 5050 */                                   out.print(encodeurl);
/* 5051 */                                   out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=\"0\"></a>&nbsp;</td></tr></table>\n</td>\n</tr>\n<tr>\n<td height=\"25\" class=\"whitegrayborder\">");
/* 5052 */                                   out.print(FormatUtil.getString("am.webclient.dsizech.text"));
/* 5053 */                                   out.write("</td>\n");
/* 5054 */                                   if (dsize == -1.0D) {
/* 5055 */                                     out.write("\n        <td height=\"25\" class=\"whitegrayborder\">");
/* 5056 */                                     out.print("-");
/* 5057 */                                     out.write("</td>\n");
/*      */                                   } else {
/* 5059 */                                     out.write("\n      <td height=\"25\" class=\"whitegrayborder\">");
/* 5060 */                                     out.print(dirdata.get("sizeincreased"));
/* 5061 */                                     out.write("</td>\n");
/*      */                                   }
/* 5063 */                                   out.write("\n<Td width=\"16%\" class=\"whitegrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5064 */                                   out.print(resID);
/* 5065 */                                   out.write("&attributeid=6003&alertconfigurl=");
/* 5066 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6003&attributeToSelect=6003&redirectto=" + encodeurl));
/* 5067 */                                   out.write("')\">");
/* 5068 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 6003)));
/* 5069 */                                   out.write(" </a></td>\n<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5070 */                                   out.print(resID);
/* 5071 */                                   out.write("&attributeIDs=6003&attributeToSelect=6003&redirectto=");
/* 5072 */                                   out.print(encodeurl);
/* 5073 */                                   out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=\"0\"></a>&nbsp;</td></tr></table>\n</td>\n</tr>\n\n");
/* 5074 */                                   String sdcount = ((Integer)dirdata.get("subdircount")).toString();
/*      */                                   
/* 5076 */                                   if (!sdcount.equals("-1"))
/*      */                                   {
/*      */ 
/* 5079 */                                     out.write("\n\n<tr>\n\t<td height=\"25\" class=\"whitegrayborder\">");
/* 5080 */                                     out.print(FormatUtil.getString("am.webclient.totSubDirectories.text"));
/* 5081 */                                     out.write("</td>                                                     \n\t<td height=\"25\" class=\"yellowgrayborder\">");
/* 5082 */                                     out.print(dirdata.get("subdircount"));
/* 5083 */                                     out.write("</td>");
/* 5084 */                                     out.write("\n\t<Td width=\"16%\" class=\"whitegrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5085 */                                     out.print(resID);
/* 5086 */                                     out.write("&attributeid=6005&alertconfigurl=");
/* 5087 */                                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6005&attributeToSelect=6005&redirectto=" + encodeurl));
/* 5088 */                                     out.write("')\">");
/* 5089 */                                     out.print(getSeverityImage(alert.getProperty(resID + "#" + 6005)));
/* 5090 */                                     out.write(" </a></td>\n\t<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5091 */                                     out.print(resID);
/* 5092 */                                     out.write("&attributeIDs=6005&attributeToSelect=6005&redirectto=");
/* 5093 */                                     out.print(encodeurl);
/* 5094 */                                     out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=\"0\"></a>&nbsp;</td></tr></table>\n\t</td>\n</tr>\n");
/*      */                                   }
/* 5096 */                                   String dcount = ((Integer)dirdata.get("filescount")).toString();
/*      */                                   
/* 5098 */                                   if (!dcount.equals("-1"))
/*      */                                   {
/*      */ 
/* 5101 */                                     out.write("\n\n<tr>\n<td height=\"25\" class=\"yellowgrayborder\">");
/* 5102 */                                     out.print(FormatUtil.getString("am.webclient.dtotfiles.text"));
/* 5103 */                                     out.write(" </td>\n<td height=\"25\" class=\"yellowgrayborder\">");
/* 5104 */                                     out.print(dirdata.get("filescount"));
/* 5105 */                                     out.write("</td>\n<Td width=\"16%\" class=\"yellowgrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5106 */                                     out.print(resID);
/* 5107 */                                     out.write("&attributeid=6004&alertconfigurl=");
/* 5108 */                                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6004&attributeToSelect=6004&redirectto=" + encodeurl));
/* 5109 */                                     out.write("')\">");
/* 5110 */                                     out.print(getSeverityImage(alert.getProperty(resID + "#" + 6004)));
/* 5111 */                                     out.write(" </a></td>\n<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5112 */                                     out.print(resID);
/* 5113 */                                     out.write("&attributeIDs=6004&attributeToSelect=6004&redirectto=");
/* 5114 */                                     out.print(encodeurl);
/* 5115 */                                     out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=\"0\"></a>&nbsp;</td></tr></table>\n</td>\n</tr>\n");
/*      */                                   }
/* 5117 */                                   if ((dirdata.get("mod") != null) && (!((String)dirdata.get("mod")).equals("0"))) {
/* 5118 */                                     out.write("\n        <tr>\n        \t<td height=\"25\" class=\"yellowgrayborder\">");
/* 5119 */                                     out.print(FormatUtil.getString("am.webclient.lastmtime.text"));
/* 5120 */                                     out.write("</td>\n        \t<td height=\"25\" class=\"yellowgrayborder\">");
/* 5121 */                                     out.print(formatDT((String)dirdata.get("mod")));
/* 5122 */                                     out.write("</td>\n        </tr>\n        \t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5126 */                                   out.write("\n<!--tr>\n<td height=\"25\" class=\"whitegrayborder\">No. of New Files Created</td>                                                      <td height=\"25\" class=\"yellowgrayborder\">");
/* 5127 */                                   out.print(dirdata.get("newfcount"));
/* 5128 */                                   out.write("</td>");
/* 5129 */                                   out.write("\n<Td width=\"16%\" class=\"whitegrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5130 */                                   out.print(resID);
/* 5131 */                                   out.write("&attributeid=6005&alertconfigurl=");
/* 5132 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6005&attributeToSelect=6005&redirectto=" + encodeurl));
/* 5133 */                                   out.write("')\">");
/* 5134 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 6005)));
/* 5135 */                                   out.write(" </a></td>\n<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5136 */                                   out.print(resID);
/* 5137 */                                   out.write("&attributeIDs=6005&attributeToSelect=6005&redirectto=");
/* 5138 */                                   out.print(encodeurl);
/* 5139 */                                   out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=\"0\"></a>&nbsp;</td></tr></table>\n</td>\n</tr>\n<tr>\n<td height=\"25\" class=\"yellowgrayborder\">No. of Files Deleted </td>                                                       <td height=\"25\" class=\"whitegrayborder\">");
/* 5140 */                                   out.print(dirdata.get("dfcount"));
/* 5141 */                                   out.write("</td>");
/* 5142 */                                   out.write("\n<Td width=\"16%\" class=\"yellowgrayborder\"><table><tr><td> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5143 */                                   out.print(resID);
/* 5144 */                                   out.write("&attributeid=6006&alertconfigurl=");
/* 5145 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=6006&attributeToSelect=6006&redirectto=" + encodeurl));
/* 5146 */                                   out.write("')\">");
/* 5147 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 6006)));
/* 5148 */                                   out.write(" </a></td>\n<td><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5149 */                                   out.print(resID);
/* 5150 */                                   out.write("&attributeIDs=6006&attributeToSelect=6006&redirectto=");
/* 5151 */                                   out.print(encodeurl);
/* 5152 */                                   out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=\"0\"></a>&nbsp;</td></tr></table>\n</td>\n</tr>\n<tr>\n</tr-->\n");
/*      */                                 } else {
/* 5154 */                                   out.write("\n<tr>\n<td height=\"25\" colspan=\"4\" class=\"whitegrayborder\"> ");
/* 5155 */                                   out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 5156 */                                   out.write("</td>\n</tr>\n");
/*      */                                 }
/*      */                               } else {
/* 5159 */                                 out.write("\n<tr>\n<td height=\"25\" colspan=\"4\" class=\"whitegrayborder\">");
/* 5160 */                                 out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 5161 */                                 out.write(" </td>\n</tr>\n");
/*      */                               }
/*      */                               
/* 5164 */                               out.write("\n</table>\n");
/*      */                             }
/* 5166 */                             out.write("\n</td>\n  </tr>\n<tr><td>&nbsp;</td></tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n  <tr>\n    <td height=\"26\"></td>\n  </tr>\n</table>\n");
/* 5167 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 5168 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5171 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 5172 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5175 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5176 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 5179 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 5180 */                         out.write(10);
/* 5181 */                         if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 5183 */                         out.write(10);
/* 5184 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5185 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5189 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5190 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 5193 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5194 */                       out.write(10);
/*      */                     }
/* 5196 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5197 */         out = _jspx_out;
/* 5198 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5199 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 5200 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5203 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5209 */     PageContext pageContext = _jspx_page_context;
/* 5210 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5212 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5213 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5214 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5216 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 5217 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5218 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5220 */         out.write(10);
/* 5221 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 5222 */           return true;
/* 5223 */         out.write(10);
/* 5224 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5225 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5229 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5230 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5231 */       return true;
/*      */     }
/* 5233 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5239 */     PageContext pageContext = _jspx_page_context;
/* 5240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5242 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5243 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5244 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5246 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5248 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 5249 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5250 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5251 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5252 */       return true;
/*      */     }
/* 5254 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5260 */     PageContext pageContext = _jspx_page_context;
/* 5261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5263 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5264 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5265 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5267 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 5268 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5269 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5271 */         out.write(10);
/* 5272 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 5273 */           return true;
/* 5274 */         out.write(10);
/* 5275 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5276 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5280 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5281 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5282 */       return true;
/*      */     }
/* 5284 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5285 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5290 */     PageContext pageContext = _jspx_page_context;
/* 5291 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5293 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5294 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 5295 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5297 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 5299 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 5300 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 5301 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5302 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5303 */       return true;
/*      */     }
/* 5305 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5311 */     PageContext pageContext = _jspx_page_context;
/* 5312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5314 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5315 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5316 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5318 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 5319 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5320 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5321 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5322 */       return true;
/*      */     }
/* 5324 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5330 */     PageContext pageContext = _jspx_page_context;
/* 5331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5333 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5334 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5335 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5337 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 5338 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5339 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5340 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5341 */       return true;
/*      */     }
/* 5343 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5344 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5349 */     PageContext pageContext = _jspx_page_context;
/* 5350 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5352 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5353 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5354 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5356 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5357 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5358 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5359 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5360 */       return true;
/*      */     }
/* 5362 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5368 */     PageContext pageContext = _jspx_page_context;
/* 5369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5371 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5372 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5373 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5375 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 5376 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5377 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5378 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5379 */       return true;
/*      */     }
/* 5381 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5382 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5387 */     PageContext pageContext = _jspx_page_context;
/* 5388 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5390 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5391 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5392 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5394 */     _jspx_th_c_005fif_005f2.setTest("${!empty param.parentid}");
/* 5395 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5396 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5398 */         out.write("\n      ");
/* 5399 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5400 */           return true;
/* 5401 */         out.write("\n      ");
/* 5402 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5403 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5407 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5408 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5409 */       return true;
/*      */     }
/* 5411 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5412 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5417 */     PageContext pageContext = _jspx_page_context;
/* 5418 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5420 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5421 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5422 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5424 */     _jspx_th_c_005fset_005f0.setVar("parentids");
/* 5425 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5426 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5427 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5428 */         out = _jspx_page_context.pushBody();
/* 5429 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5430 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5433 */         out.write("\n      &parentname=");
/* 5434 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5435 */           return true;
/* 5436 */         out.write("&parentid=");
/* 5437 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5438 */           return true;
/* 5439 */         out.write("\n      ");
/* 5440 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5441 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5444 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5445 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5448 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5449 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5450 */       return true;
/*      */     }
/* 5452 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5453 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5458 */     PageContext pageContext = _jspx_page_context;
/* 5459 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5461 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5462 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5463 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5465 */     _jspx_th_c_005fout_005f4.setValue("${param.parentname}");
/* 5466 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5467 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5468 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5469 */       return true;
/*      */     }
/* 5471 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5472 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5477 */     PageContext pageContext = _jspx_page_context;
/* 5478 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5480 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5481 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5482 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5484 */     _jspx_th_c_005fout_005f5.setValue("${param.parentid}");
/* 5485 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5486 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5487 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5488 */       return true;
/*      */     }
/* 5490 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5496 */     PageContext pageContext = _jspx_page_context;
/* 5497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5499 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5500 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5501 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5503 */     _jspx_th_c_005fif_005f3.setTest("${empty param.parentid}");
/* 5504 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5505 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 5507 */         out.write("\n            ");
/* 5508 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5509 */           return true;
/* 5510 */         out.write("\n      ");
/* 5511 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5512 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5516 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5517 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5518 */       return true;
/*      */     }
/* 5520 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5526 */     PageContext pageContext = _jspx_page_context;
/* 5527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5529 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 5530 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5531 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5533 */     _jspx_th_c_005fset_005f1.setVar("parentids");
/*      */     
/* 5535 */     _jspx_th_c_005fset_005f1.setValue("");
/* 5536 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5537 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5538 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 5539 */       return true;
/*      */     }
/* 5541 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 5542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5547 */     PageContext pageContext = _jspx_page_context;
/* 5548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5550 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5551 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5552 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5554 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.haid}");
/* 5555 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5556 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 5558 */         out.write(10);
/* 5559 */         out.write(9);
/* 5560 */         out.write(9);
/* 5561 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 5562 */           return true;
/* 5563 */         out.write(10);
/* 5564 */         out.write(9);
/* 5565 */         out.write(9);
/* 5566 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5567 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5571 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5572 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5573 */       return true;
/*      */     }
/* 5575 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5581 */     PageContext pageContext = _jspx_page_context;
/* 5582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5584 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5585 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5586 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5588 */     _jspx_th_c_005fset_005f2.setVar("haid");
/* 5589 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5590 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5591 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5592 */         out = _jspx_page_context.pushBody();
/* 5593 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5594 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5597 */         out.write("\n\t\t&haid=");
/* 5598 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5599 */           return true;
/* 5600 */         out.write(10);
/* 5601 */         out.write(9);
/* 5602 */         out.write(9);
/* 5603 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5604 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5607 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5608 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5611 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5612 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5613 */       return true;
/*      */     }
/* 5615 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5621 */     PageContext pageContext = _jspx_page_context;
/* 5622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5624 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5625 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5626 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5628 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 5629 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5630 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5631 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5632 */       return true;
/*      */     }
/* 5634 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5640 */     PageContext pageContext = _jspx_page_context;
/* 5641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5643 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5644 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5645 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5647 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 5648 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5649 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5650 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5651 */       return true;
/*      */     }
/* 5653 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5659 */     PageContext pageContext = _jspx_page_context;
/* 5660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5662 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5663 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5664 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5666 */     _jspx_th_c_005fif_005f7.setTest("${param.method=='editScript'}");
/* 5667 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5668 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5670 */         out.write("\n    </a>\n    ");
/* 5671 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5672 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5676 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5677 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5678 */       return true;
/*      */     }
/* 5680 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5686 */     PageContext pageContext = _jspx_page_context;
/* 5687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5689 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5690 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5691 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 5693 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 5694 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5695 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5696 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5697 */       return true;
/*      */     }
/* 5699 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5705 */     PageContext pageContext = _jspx_page_context;
/* 5706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5708 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5709 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5710 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5712 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 5713 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5714 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5715 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5716 */       return true;
/*      */     }
/* 5718 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5719 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5724 */     PageContext pageContext = _jspx_page_context;
/* 5725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5727 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5728 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5729 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 5731 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 5732 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5733 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5734 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5735 */       return true;
/*      */     }
/* 5737 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5743 */     PageContext pageContext = _jspx_page_context;
/* 5744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5746 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5747 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5748 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5750 */     _jspx_th_c_005fout_005f11.setValue("${param.haid}");
/* 5751 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5752 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5753 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5754 */       return true;
/*      */     }
/* 5756 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5762 */     PageContext pageContext = _jspx_page_context;
/* 5763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5765 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5766 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5767 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5769 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 5770 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5771 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5772 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5773 */       return true;
/*      */     }
/* 5775 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5781 */     PageContext pageContext = _jspx_page_context;
/* 5782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5784 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5785 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5786 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5788 */     _jspx_th_c_005fout_005f13.setValue("${parentids}");
/* 5789 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5790 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5791 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5792 */       return true;
/*      */     }
/* 5794 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5800 */     PageContext pageContext = _jspx_page_context;
/* 5801 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5803 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5804 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5805 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5807 */     _jspx_th_c_005fout_005f14.setValue("${param.haid}");
/* 5808 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5809 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5810 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5811 */       return true;
/*      */     }
/* 5813 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5819 */     PageContext pageContext = _jspx_page_context;
/* 5820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5822 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5823 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5824 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5826 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 5827 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5828 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5829 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5830 */       return true;
/*      */     }
/* 5832 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5838 */     PageContext pageContext = _jspx_page_context;
/* 5839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5841 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5842 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5843 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5845 */     _jspx_th_c_005fout_005f16.setValue("${parentids}");
/* 5846 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5847 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5848 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5849 */       return true;
/*      */     }
/* 5851 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5857 */     PageContext pageContext = _jspx_page_context;
/* 5858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5860 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5861 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5862 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5864 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 5865 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5866 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5867 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5868 */       return true;
/*      */     }
/* 5870 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5876 */     PageContext pageContext = _jspx_page_context;
/* 5877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5879 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5880 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5881 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5883 */     _jspx_th_c_005fout_005f18.setValue("${parentids}");
/* 5884 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5885 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5886 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5887 */       return true;
/*      */     }
/* 5889 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5895 */     PageContext pageContext = _jspx_page_context;
/* 5896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5898 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5899 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5900 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5902 */     _jspx_th_c_005fout_005f19.setValue("${param.haid}");
/* 5903 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5904 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5905 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5906 */       return true;
/*      */     }
/* 5908 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5914 */     PageContext pageContext = _jspx_page_context;
/* 5915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5917 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5918 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5919 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5921 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 5922 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5923 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5924 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5925 */       return true;
/*      */     }
/* 5927 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5933 */     PageContext pageContext = _jspx_page_context;
/* 5934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5936 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5937 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5938 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5940 */     _jspx_th_c_005fout_005f21.setValue("${parentids}");
/* 5941 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5942 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5943 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5944 */       return true;
/*      */     }
/* 5946 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5952 */     PageContext pageContext = _jspx_page_context;
/* 5953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5955 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5956 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5957 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5959 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 5960 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5961 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5962 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5963 */       return true;
/*      */     }
/* 5965 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5971 */     PageContext pageContext = _jspx_page_context;
/* 5972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5974 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5975 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5976 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5978 */     _jspx_th_c_005fout_005f23.setValue("${parentids}");
/* 5979 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5980 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5981 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5982 */       return true;
/*      */     }
/* 5984 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5985 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5990 */     PageContext pageContext = _jspx_page_context;
/* 5991 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5993 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5994 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 5995 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 5997 */     _jspx_th_c_005fif_005f18.setTest("${param.actionmethod!='editScript'}");
/* 5998 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 5999 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 6001 */         out.write("\n    </a>\n    ");
/* 6002 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 6003 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6007 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 6008 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 6009 */       return true;
/*      */     }
/* 6011 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 6012 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6017 */     PageContext pageContext = _jspx_page_context;
/* 6018 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6020 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6021 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6022 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6024 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 6025 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6026 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6027 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6028 */       return true;
/*      */     }
/* 6030 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6031 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6036 */     PageContext pageContext = _jspx_page_context;
/* 6037 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6039 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6040 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 6041 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6043 */     _jspx_th_c_005fout_005f25.setValue("${param.resourceid}");
/* 6044 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 6045 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 6046 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6047 */       return true;
/*      */     }
/* 6049 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6055 */     PageContext pageContext = _jspx_page_context;
/* 6056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6058 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6059 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 6060 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6062 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 6063 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 6064 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 6065 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6066 */       return true;
/*      */     }
/* 6068 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6074 */     PageContext pageContext = _jspx_page_context;
/* 6075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6077 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6078 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 6079 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6081 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 6082 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 6083 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 6084 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6085 */       return true;
/*      */     }
/* 6087 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6093 */     PageContext pageContext = _jspx_page_context;
/* 6094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6096 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6097 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 6098 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6100 */     _jspx_th_c_005fout_005f28.setValue("${ha.key}");
/* 6101 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 6102 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 6103 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6104 */       return true;
/*      */     }
/* 6106 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6112 */     PageContext pageContext = _jspx_page_context;
/* 6113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6115 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6116 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 6117 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6119 */     _jspx_th_c_005fout_005f29.setValue("${ha.value}");
/* 6120 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 6121 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 6122 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6123 */       return true;
/*      */     }
/* 6125 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6131 */     PageContext pageContext = _jspx_page_context;
/* 6132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6134 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6135 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 6136 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6138 */     _jspx_th_c_005fset_005f3.setVar("monitorName");
/* 6139 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 6140 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 6141 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 6142 */         out = _jspx_page_context.pushBody();
/* 6143 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 6144 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 6145 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6148 */         if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fset_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6149 */           return true;
/* 6150 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 6151 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6154 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 6155 */         out = _jspx_page_context.popBody();
/* 6156 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 6159 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 6160 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 6161 */       return true;
/*      */     }
/* 6163 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 6164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6169 */     PageContext pageContext = _jspx_page_context;
/* 6170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6172 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6173 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 6174 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 6176 */     _jspx_th_c_005fout_005f30.setValue("${ha.value}");
/* 6177 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 6178 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 6179 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6180 */       return true;
/*      */     }
/* 6182 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_logic_005fpresent_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6188 */     PageContext pageContext = _jspx_page_context;
/* 6189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6191 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6192 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 6193 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_logic_005fpresent_005f10);
/*      */     
/* 6195 */     _jspx_th_c_005fout_005f31.setValue("${ha.key}");
/* 6196 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 6197 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 6198 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6199 */       return true;
/*      */     }
/* 6201 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6207 */     PageContext pageContext = _jspx_page_context;
/* 6208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6210 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6211 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6212 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 6214 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6215 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6216 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6217 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6218 */       return true;
/*      */     }
/* 6220 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6226 */     PageContext pageContext = _jspx_page_context;
/* 6227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6229 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6230 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 6231 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6233 */     _jspx_th_c_005fout_005f32.setValue("${ha.key}");
/* 6234 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 6235 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 6236 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6237 */       return true;
/*      */     }
/* 6239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6245 */     PageContext pageContext = _jspx_page_context;
/* 6246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6248 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6249 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 6250 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6252 */     _jspx_th_c_005fout_005f33.setValue("${ha.value}");
/* 6253 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 6254 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 6255 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6256 */       return true;
/*      */     }
/* 6258 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6264 */     PageContext pageContext = _jspx_page_context;
/* 6265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6267 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6268 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 6269 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6271 */     _jspx_th_c_005fset_005f4.setVar("monitorName");
/* 6272 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 6273 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 6274 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6275 */         out = _jspx_page_context.pushBody();
/* 6276 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 6277 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 6278 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6281 */         if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fset_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6282 */           return true;
/* 6283 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 6284 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6287 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6288 */         out = _jspx_page_context.popBody();
/* 6289 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 6292 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 6293 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 6294 */       return true;
/*      */     }
/* 6296 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 6297 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6302 */     PageContext pageContext = _jspx_page_context;
/* 6303 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6305 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6306 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 6307 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 6309 */     _jspx_th_c_005fout_005f34.setValue("${ha.value}");
/* 6310 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 6311 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 6312 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6313 */       return true;
/*      */     }
/* 6315 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6316 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6321 */     PageContext pageContext = _jspx_page_context;
/* 6322 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6324 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6325 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 6326 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 6328 */     _jspx_th_c_005fout_005f35.setValue("${ha.key}");
/* 6329 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 6330 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 6331 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6332 */       return true;
/*      */     }
/* 6334 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6335 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6340 */     PageContext pageContext = _jspx_page_context;
/* 6341 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6343 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6344 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 6345 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 6347 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 6348 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 6349 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 6350 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6351 */       return true;
/*      */     }
/* 6353 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6354 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6359 */     PageContext pageContext = _jspx_page_context;
/* 6360 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6362 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6363 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 6364 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6366 */     _jspx_th_c_005fif_005f24.setTest("${empty associatedmgs}");
/* 6367 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 6368 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 6370 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 6371 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f24, _jspx_page_context))
/* 6372 */           return true;
/* 6373 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 6374 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f24, _jspx_page_context))
/* 6375 */           return true;
/* 6376 */         out.write("</td>\n\t ");
/* 6377 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 6378 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6382 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 6383 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 6384 */       return true;
/*      */     }
/* 6386 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 6387 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6392 */     PageContext pageContext = _jspx_page_context;
/* 6393 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6395 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6396 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 6397 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f24);
/*      */     
/* 6399 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6400 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 6401 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 6402 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6403 */       return true;
/*      */     }
/* 6405 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6406 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6411 */     PageContext pageContext = _jspx_page_context;
/* 6412 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6414 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6415 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 6416 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f24);
/*      */     
/* 6418 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 6419 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 6420 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 6421 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6422 */       return true;
/*      */     }
/* 6424 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6425 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6430 */     PageContext pageContext = _jspx_page_context;
/* 6431 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6433 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6434 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 6435 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6437 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6438 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 6439 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6440 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6441 */       return true;
/*      */     }
/* 6443 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6444 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6449 */     PageContext pageContext = _jspx_page_context;
/* 6450 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6452 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6453 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 6454 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 6456 */     _jspx_th_c_005fout_005f36.setValue("${hostname}");
/* 6457 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 6458 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 6459 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6460 */       return true;
/*      */     }
/* 6462 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6463 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f27(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6468 */     PageContext pageContext = _jspx_page_context;
/* 6469 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6471 */     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6472 */     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 6473 */     _jspx_th_c_005fif_005f27.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6475 */     _jspx_th_c_005fif_005f27.setTest("${not empty param.haid}");
/* 6476 */     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 6477 */     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */       for (;;) {
/* 6479 */         out.write(10);
/* 6480 */         out.write(9);
/* 6481 */         out.write(9);
/* 6482 */         if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f27, _jspx_page_context))
/* 6483 */           return true;
/* 6484 */         out.write(10);
/* 6485 */         out.write(9);
/* 6486 */         out.write(9);
/* 6487 */         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 6488 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6492 */     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 6493 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 6494 */       return true;
/*      */     }
/* 6496 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 6497 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f27, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6502 */     PageContext pageContext = _jspx_page_context;
/* 6503 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6505 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6506 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 6507 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f27);
/*      */     
/* 6509 */     _jspx_th_c_005fset_005f5.setVar("myfield_paramresid");
/*      */     
/* 6511 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 6512 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 6513 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 6514 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6515 */         out = _jspx_page_context.pushBody();
/* 6516 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 6517 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6520 */         if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 6521 */           return true;
/* 6522 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 6523 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6526 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6527 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6530 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 6531 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 6532 */       return true;
/*      */     }
/* 6534 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 6535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6540 */     PageContext pageContext = _jspx_page_context;
/* 6541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6543 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6544 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 6545 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 6547 */     _jspx_th_c_005fout_005f37.setValue("${param.haid}");
/* 6548 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 6549 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 6550 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6551 */       return true;
/*      */     }
/* 6553 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6554 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f28(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6559 */     PageContext pageContext = _jspx_page_context;
/* 6560 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6562 */     IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6563 */     _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 6564 */     _jspx_th_c_005fif_005f28.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6566 */     _jspx_th_c_005fif_005f28.setTest("${not empty param.resourceid}");
/* 6567 */     int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 6568 */     if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */       for (;;) {
/* 6570 */         out.write(10);
/* 6571 */         out.write(9);
/* 6572 */         out.write(9);
/* 6573 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f28, _jspx_page_context))
/* 6574 */           return true;
/* 6575 */         out.write(10);
/* 6576 */         out.write(9);
/* 6577 */         out.write(9);
/* 6578 */         int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 6579 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6583 */     if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 6584 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 6585 */       return true;
/*      */     }
/* 6587 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 6588 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f28, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6593 */     PageContext pageContext = _jspx_page_context;
/* 6594 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6596 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6597 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 6598 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f28);
/*      */     
/* 6600 */     _jspx_th_c_005fset_005f6.setVar("myfield_paramresid");
/*      */     
/* 6602 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 6603 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 6604 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 6605 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6606 */         out = _jspx_page_context.pushBody();
/* 6607 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 6608 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6611 */         if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 6612 */           return true;
/* 6613 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 6614 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6617 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6618 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6621 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 6622 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 6623 */       return true;
/*      */     }
/* 6625 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 6626 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6631 */     PageContext pageContext = _jspx_page_context;
/* 6632 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6634 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6635 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 6636 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 6638 */     _jspx_th_c_005fout_005f38.setValue("${param.resourceid}");
/* 6639 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 6640 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 6641 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6642 */       return true;
/*      */     }
/* 6644 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6650 */     PageContext pageContext = _jspx_page_context;
/* 6651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6653 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6654 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 6655 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6657 */     _jspx_th_c_005fout_005f39.setValue("${myfield_paramresid}");
/* 6658 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 6659 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 6660 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6661 */       return true;
/*      */     }
/* 6663 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f29(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6669 */     PageContext pageContext = _jspx_page_context;
/* 6670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6672 */     IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6673 */     _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 6674 */     _jspx_th_c_005fif_005f29.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6676 */     _jspx_th_c_005fif_005f29.setTest("${not empty param.haid}");
/* 6677 */     int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 6678 */     if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */       for (;;) {
/* 6680 */         out.write(10);
/* 6681 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f29, _jspx_page_context))
/* 6682 */           return true;
/* 6683 */         out.write(10);
/* 6684 */         int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 6685 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6689 */     if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 6690 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 6691 */       return true;
/*      */     }
/* 6693 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 6694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f29, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6699 */     PageContext pageContext = _jspx_page_context;
/* 6700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6702 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6703 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 6704 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f29);
/*      */     
/* 6706 */     _jspx_th_c_005fset_005f7.setVar("myfield_resid");
/*      */     
/* 6708 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 6709 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 6710 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 6711 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6712 */         out = _jspx_page_context.pushBody();
/* 6713 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 6714 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6717 */         if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 6718 */           return true;
/* 6719 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 6720 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6723 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6724 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6727 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 6728 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6729 */       return true;
/*      */     }
/* 6731 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6737 */     PageContext pageContext = _jspx_page_context;
/* 6738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6740 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6741 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 6742 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 6744 */     _jspx_th_c_005fout_005f40.setValue("${param.haid}");
/* 6745 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 6746 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 6747 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6748 */       return true;
/*      */     }
/* 6750 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f30(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6756 */     PageContext pageContext = _jspx_page_context;
/* 6757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6759 */     IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6760 */     _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 6761 */     _jspx_th_c_005fif_005f30.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6763 */     _jspx_th_c_005fif_005f30.setTest("${not empty param.resourceid}");
/* 6764 */     int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 6765 */     if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */       for (;;) {
/* 6767 */         out.write(10);
/* 6768 */         if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fif_005f30, _jspx_page_context))
/* 6769 */           return true;
/* 6770 */         out.write(10);
/* 6771 */         int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 6772 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6776 */     if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 6777 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 6778 */       return true;
/*      */     }
/* 6780 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 6781 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6786 */     PageContext pageContext = _jspx_page_context;
/* 6787 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6789 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6790 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 6791 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 6793 */     _jspx_th_c_005fset_005f8.setVar("myfield_resid");
/*      */     
/* 6795 */     _jspx_th_c_005fset_005f8.setScope("page");
/* 6796 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 6797 */     if (_jspx_eval_c_005fset_005f8 != 0) {
/* 6798 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6799 */         out = _jspx_page_context.pushBody();
/* 6800 */         _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 6801 */         _jspx_th_c_005fset_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6804 */         if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fset_005f8, _jspx_page_context))
/* 6805 */           return true;
/* 6806 */         int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 6807 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6810 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6811 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6814 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 6815 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 6816 */       return true;
/*      */     }
/* 6818 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 6819 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fset_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6824 */     PageContext pageContext = _jspx_page_context;
/* 6825 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6827 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6828 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 6829 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fset_005f8);
/*      */     
/* 6831 */     _jspx_th_c_005fout_005f41.setValue("${param.resourceid}");
/* 6832 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 6833 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 6834 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6835 */       return true;
/*      */     }
/* 6837 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6838 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6843 */     PageContext pageContext = _jspx_page_context;
/* 6844 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6846 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6847 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 6848 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6850 */     _jspx_th_c_005fset_005f9.setVar("trstripclass");
/*      */     
/* 6852 */     _jspx_th_c_005fset_005f9.setScope("page");
/* 6853 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 6854 */     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 6855 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6856 */         out = _jspx_page_context.pushBody();
/* 6857 */         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 6858 */         _jspx_th_c_005fset_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6861 */         if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fset_005f9, _jspx_page_context))
/* 6862 */           return true;
/* 6863 */         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 6864 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6867 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6868 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6871 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 6872 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 6873 */       return true;
/*      */     }
/* 6875 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 6876 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fset_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6881 */     PageContext pageContext = _jspx_page_context;
/* 6882 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6884 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6885 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 6886 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fset_005f9);
/*      */     
/* 6888 */     _jspx_th_c_005fout_005f42.setValue("");
/* 6889 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 6890 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 6891 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6892 */       return true;
/*      */     }
/* 6894 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6895 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6900 */     PageContext pageContext = _jspx_page_context;
/* 6901 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6903 */     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6904 */     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 6905 */     _jspx_th_c_005fset_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6907 */     _jspx_th_c_005fset_005f10.setVar("myfield_entity");
/*      */     
/* 6909 */     _jspx_th_c_005fset_005f10.setScope("page");
/* 6910 */     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 6911 */     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 6912 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6913 */         out = _jspx_page_context.pushBody();
/* 6914 */         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 6915 */         _jspx_th_c_005fset_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6918 */         if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fset_005f10, _jspx_page_context))
/* 6919 */           return true;
/* 6920 */         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 6921 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6924 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6925 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6928 */     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 6929 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10);
/* 6930 */       return true;
/*      */     }
/* 6932 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10);
/* 6933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fset_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6938 */     PageContext pageContext = _jspx_page_context;
/* 6939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6941 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6942 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 6943 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fset_005f10);
/*      */     
/* 6945 */     _jspx_th_c_005fout_005f43.setValue("noalarms");
/* 6946 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 6947 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 6948 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6949 */       return true;
/*      */     }
/* 6951 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f31(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6957 */     PageContext pageContext = _jspx_page_context;
/* 6958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6960 */     IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6961 */     _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 6962 */     _jspx_th_c_005fif_005f31.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6964 */     _jspx_th_c_005fif_005f31.setTest("${not empty param.entity}");
/* 6965 */     int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 6966 */     if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */       for (;;) {
/* 6968 */         out.write(10);
/* 6969 */         if (_jspx_meth_c_005fset_005f11(_jspx_th_c_005fif_005f31, _jspx_page_context))
/* 6970 */           return true;
/* 6971 */         out.write(10);
/* 6972 */         int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 6973 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6977 */     if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 6978 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 6979 */       return true;
/*      */     }
/* 6981 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 6982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f11(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6987 */     PageContext pageContext = _jspx_page_context;
/* 6988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6990 */     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6991 */     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 6992 */     _jspx_th_c_005fset_005f11.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 6994 */     _jspx_th_c_005fset_005f11.setVar("myfield_entity");
/*      */     
/* 6996 */     _jspx_th_c_005fset_005f11.setScope("page");
/* 6997 */     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 6998 */     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 6999 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 7000 */         out = _jspx_page_context.pushBody();
/* 7001 */         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 7002 */         _jspx_th_c_005fset_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7005 */         if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fset_005f11, _jspx_page_context))
/* 7006 */           return true;
/* 7007 */         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 7008 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7011 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 7012 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7015 */     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 7016 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f11);
/* 7017 */       return true;
/*      */     }
/* 7019 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f11);
/* 7020 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fset_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7025 */     PageContext pageContext = _jspx_page_context;
/* 7026 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7028 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7029 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 7030 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fset_005f11);
/*      */     
/* 7032 */     _jspx_th_c_005fout_005f44.setValue("${param.entity}");
/* 7033 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 7034 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 7035 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 7036 */       return true;
/*      */     }
/* 7038 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 7039 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f32(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7044 */     PageContext pageContext = _jspx_page_context;
/* 7045 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7047 */     IfTag _jspx_th_c_005fif_005f32 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7048 */     _jspx_th_c_005fif_005f32.setPageContext(_jspx_page_context);
/* 7049 */     _jspx_th_c_005fif_005f32.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7051 */     _jspx_th_c_005fif_005f32.setTest("${not empty param.includeClass}");
/* 7052 */     int _jspx_eval_c_005fif_005f32 = _jspx_th_c_005fif_005f32.doStartTag();
/* 7053 */     if (_jspx_eval_c_005fif_005f32 != 0) {
/*      */       for (;;) {
/* 7055 */         out.write(10);
/* 7056 */         if (_jspx_meth_c_005fset_005f12(_jspx_th_c_005fif_005f32, _jspx_page_context))
/* 7057 */           return true;
/* 7058 */         out.write(10);
/* 7059 */         int evalDoAfterBody = _jspx_th_c_005fif_005f32.doAfterBody();
/* 7060 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7064 */     if (_jspx_th_c_005fif_005f32.doEndTag() == 5) {
/* 7065 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 7066 */       return true;
/*      */     }
/* 7068 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 7069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f12(JspTag _jspx_th_c_005fif_005f32, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7074 */     PageContext pageContext = _jspx_page_context;
/* 7075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7077 */     SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7078 */     _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 7079 */     _jspx_th_c_005fset_005f12.setParent((Tag)_jspx_th_c_005fif_005f32);
/*      */     
/* 7081 */     _jspx_th_c_005fset_005f12.setVar("trstripclass");
/*      */     
/* 7083 */     _jspx_th_c_005fset_005f12.setScope("page");
/* 7084 */     int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 7085 */     if (_jspx_eval_c_005fset_005f12 != 0) {
/* 7086 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 7087 */         out = _jspx_page_context.pushBody();
/* 7088 */         _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 7089 */         _jspx_th_c_005fset_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7092 */         if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fset_005f12, _jspx_page_context))
/* 7093 */           return true;
/* 7094 */         int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 7095 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7098 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 7099 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7102 */     if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 7103 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f12);
/* 7104 */       return true;
/*      */     }
/* 7106 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f12);
/* 7107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fset_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7112 */     PageContext pageContext = _jspx_page_context;
/* 7113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7115 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7116 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 7117 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fset_005f12);
/*      */     
/* 7119 */     _jspx_th_c_005fout_005f45.setValue("${param.includeClass}");
/* 7120 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 7121 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 7122 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 7123 */       return true;
/*      */     }
/* 7125 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 7126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7131 */     PageContext pageContext = _jspx_page_context;
/* 7132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7134 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7135 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 7136 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7138 */     _jspx_th_c_005fout_005f46.setValue("${trstripclass}");
/* 7139 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 7140 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 7141 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 7142 */       return true;
/*      */     }
/* 7144 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 7145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7150 */     PageContext pageContext = _jspx_page_context;
/* 7151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7153 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7154 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 7155 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7156 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 7157 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 7158 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 7159 */         out = _jspx_page_context.pushBody();
/* 7160 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 7161 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7164 */         out.write("am.myfield.customfield.text");
/* 7165 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 7166 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7169 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 7170 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7173 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 7174 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7175 */       return true;
/*      */     }
/* 7177 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7183 */     PageContext pageContext = _jspx_page_context;
/* 7184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7186 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7187 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 7188 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7190 */     _jspx_th_c_005fout_005f47.setValue("${myfield_resid}");
/* 7191 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 7192 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 7193 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 7194 */       return true;
/*      */     }
/* 7196 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 7197 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7202 */     PageContext pageContext = _jspx_page_context;
/* 7203 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7205 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7206 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 7207 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7209 */     _jspx_th_c_005fout_005f48.setValue("${myfield_entity}");
/* 7210 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 7211 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 7212 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 7213 */       return true;
/*      */     }
/* 7215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 7216 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7221 */     PageContext pageContext = _jspx_page_context;
/* 7222 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7224 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7225 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 7226 */     _jspx_th_c_005fout_005f49.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7228 */     _jspx_th_c_005fout_005f49.setValue("${param.resourceid}");
/* 7229 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 7230 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 7231 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 7232 */       return true;
/*      */     }
/* 7234 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 7235 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7240 */     PageContext pageContext = _jspx_page_context;
/* 7241 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7243 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7244 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 7245 */     _jspx_th_c_005fout_005f50.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7247 */     _jspx_th_c_005fout_005f50.setValue("${param.resourcename}");
/* 7248 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 7249 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 7250 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 7251 */       return true;
/*      */     }
/* 7253 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 7254 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7259 */     PageContext pageContext = _jspx_page_context;
/* 7260 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7262 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7263 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 7264 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7266 */     _jspx_th_c_005fout_005f51.setValue("${param.resourceid}");
/* 7267 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 7268 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 7269 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 7270 */       return true;
/*      */     }
/* 7272 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 7273 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7278 */     PageContext pageContext = _jspx_page_context;
/* 7279 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7281 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7282 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 7283 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7285 */     _jspx_th_c_005fout_005f52.setValue("${param.resourcename}");
/* 7286 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 7287 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 7288 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 7289 */       return true;
/*      */     }
/* 7291 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 7292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7297 */     PageContext pageContext = _jspx_page_context;
/* 7298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7300 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 7301 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 7302 */     _jspx_th_awolf_005fpiechart_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7304 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 7306 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("300");
/*      */     
/* 7308 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */     
/* 7310 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 7312 */     _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */     
/* 7314 */     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */     
/* 7316 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 7317 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 7318 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 7319 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 7320 */         out = _jspx_page_context.pushBody();
/* 7321 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 7322 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7325 */         out.write("\n            ");
/* 7326 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 7327 */           return true;
/* 7328 */         out.write(32);
/* 7329 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 7330 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7333 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 7334 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7337 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 7338 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 7339 */       return true;
/*      */     }
/* 7341 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 7342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7347 */     PageContext pageContext = _jspx_page_context;
/* 7348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7350 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 7351 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 7352 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 7354 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 7355 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 7356 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 7357 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 7358 */         out = _jspx_page_context.pushBody();
/* 7359 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 7360 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7363 */         out.write(32);
/* 7364 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 7365 */           return true;
/* 7366 */         out.write(32);
/* 7367 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 7368 */           return true;
/* 7369 */         out.write("                      ");
/* 7370 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 7371 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7374 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 7375 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7378 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 7379 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 7380 */       return true;
/*      */     }
/* 7382 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 7383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7388 */     PageContext pageContext = _jspx_page_context;
/* 7389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7391 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 7392 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 7393 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 7395 */     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */     
/* 7397 */     _jspx_th_awolf_005fparam_005f0.setValue("#29FF29");
/* 7398 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 7399 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 7400 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 7401 */       return true;
/*      */     }
/* 7403 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 7404 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7409 */     PageContext pageContext = _jspx_page_context;
/* 7410 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7412 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 7413 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 7414 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 7416 */     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */     
/* 7418 */     _jspx_th_awolf_005fparam_005f1.setValue("#FF0000");
/* 7419 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 7420 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 7421 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 7422 */       return true;
/*      */     }
/* 7424 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 7425 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7430 */     PageContext pageContext = _jspx_page_context;
/* 7431 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7433 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7434 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 7435 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7436 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 7437 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 7438 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 7439 */         out = _jspx_page_context.pushBody();
/* 7440 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 7441 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7444 */         out.write("table.heading.attribute");
/* 7445 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 7446 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7449 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 7450 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7453 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 7454 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7455 */       return true;
/*      */     }
/* 7457 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7458 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7463 */     PageContext pageContext = _jspx_page_context;
/* 7464 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7466 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7467 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 7468 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7469 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 7470 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 7471 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 7472 */         out = _jspx_page_context.pushBody();
/* 7473 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 7474 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7477 */         out.write("table.heading.value");
/* 7478 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 7479 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7482 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 7483 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7486 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 7487 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 7488 */       return true;
/*      */     }
/* 7490 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 7491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7496 */     PageContext pageContext = _jspx_page_context;
/* 7497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7499 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7500 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 7501 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7502 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 7503 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 7504 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 7505 */         out = _jspx_page_context.pushBody();
/* 7506 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 7507 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7510 */         out.write("table.heading.status");
/* 7511 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 7512 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7515 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 7516 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7519 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 7520 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 7521 */       return true;
/*      */     }
/* 7523 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 7524 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7529 */     PageContext pageContext = _jspx_page_context;
/* 7530 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7532 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7533 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 7534 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7535 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 7536 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 7537 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 7538 */         out = _jspx_page_context.pushBody();
/* 7539 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 7540 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7543 */         out.write("table.heading.attribute");
/* 7544 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 7545 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7548 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 7549 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7552 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 7553 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 7554 */       return true;
/*      */     }
/* 7556 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 7557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7562 */     PageContext pageContext = _jspx_page_context;
/* 7563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7565 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7566 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 7567 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7568 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 7569 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 7570 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 7571 */         out = _jspx_page_context.pushBody();
/* 7572 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 7573 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7576 */         out.write("table.heading.value");
/* 7577 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 7578 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7581 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 7582 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7585 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 7586 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 7587 */       return true;
/*      */     }
/* 7589 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 7590 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7595 */     PageContext pageContext = _jspx_page_context;
/* 7596 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7598 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7599 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 7600 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7601 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 7602 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 7603 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 7604 */         out = _jspx_page_context.pushBody();
/* 7605 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 7606 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7609 */         out.write("table.heading.status");
/* 7610 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 7611 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7614 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 7615 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7618 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 7619 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 7620 */       return true;
/*      */     }
/* 7622 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 7623 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7628 */     PageContext pageContext = _jspx_page_context;
/* 7629 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7631 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7632 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 7633 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7634 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 7635 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 7636 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 7637 */         out = _jspx_page_context.pushBody();
/* 7638 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 7639 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7642 */         out.write("table.heading.attribute");
/* 7643 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 7644 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7647 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 7648 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7651 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 7652 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 7653 */       return true;
/*      */     }
/* 7655 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 7656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7661 */     PageContext pageContext = _jspx_page_context;
/* 7662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7664 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7665 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 7666 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7667 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 7668 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 7669 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 7670 */         out = _jspx_page_context.pushBody();
/* 7671 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 7672 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7675 */         out.write("table.heading.value");
/* 7676 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 7677 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7680 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 7681 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7684 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 7685 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 7686 */       return true;
/*      */     }
/* 7688 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 7689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7694 */     PageContext pageContext = _jspx_page_context;
/* 7695 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7697 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7698 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 7699 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7700 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 7701 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 7702 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 7703 */         out = _jspx_page_context.pushBody();
/* 7704 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 7705 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7708 */         out.write("table.heading.status");
/* 7709 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 7710 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7713 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 7714 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7717 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 7718 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 7719 */       return true;
/*      */     }
/* 7721 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 7722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7727 */     PageContext pageContext = _jspx_page_context;
/* 7728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7730 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 7731 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 7732 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 7734 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 7736 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 7737 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 7738 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 7739 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 7740 */       return true;
/*      */     }
/* 7742 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 7743 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\FileMonitor_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */