/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.beans.CAMGraphs;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
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
/*      */ import org.apache.struts.taglib.logic.IterateTag;
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
/*      */ public final class QEngineScriptMonitor_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  939 */     AMLog.debug("JSP : " + debugMessage);
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
/* 1837 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1839 */               if (maxCol != null)
/* 1840 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1842 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1837 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 2197 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2198 */   static { _jspx_dependants.put("/jsp/includes/ScriptLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2199 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2200 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2201 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2202 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2232 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2236 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2259 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2263 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2265 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2266 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2268 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2270 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2271 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2272 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2274 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2275 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2276 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2277 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2278 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2279 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2280 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2281 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2282 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.release();
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2291 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2294 */     JspWriter out = null;
/* 2295 */     Object page = this;
/* 2296 */     JspWriter _jspx_out = null;
/* 2297 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2301 */       response.setContentType("text/html;charset=UTF-8");
/* 2302 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2304 */       _jspx_page_context = pageContext;
/* 2305 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2306 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2307 */       session = pageContext.getSession();
/* 2308 */       out = pageContext.getOut();
/* 2309 */       _jspx_out = out;
/*      */       
/* 2311 */       out.write("<!DOCTYPE html>\n");
/* 2312 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\t\n");
/* 2313 */       out.write(10);
/*      */       
/* 2315 */       request.setAttribute("HelpKey", "Script Monitor Details");
/*      */       
/* 2317 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2318 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2320 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2321 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2322 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2324 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2326 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2328 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2330 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2331 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2332 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2333 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2336 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2337 */         String available = null;
/* 2338 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2339 */         out.write(10);
/*      */         
/* 2341 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2342 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2343 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2345 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2347 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2349 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2351 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2352 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2353 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2354 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2357 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2358 */           String unavailable = null;
/* 2359 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2360 */           out.write(10);
/*      */           
/* 2362 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2363 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2364 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2366 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2368 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2370 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2372 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2373 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2374 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2375 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2378 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2379 */             String unmanaged = null;
/* 2380 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2381 */             out.write(10);
/*      */             
/* 2383 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2384 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2385 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2387 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2389 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2391 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2393 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2394 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2395 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2396 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2399 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2400 */               String scheduled = null;
/* 2401 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2402 */               out.write(10);
/*      */               
/* 2404 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2405 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2406 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2408 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2410 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2412 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2414 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2415 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2416 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2417 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2420 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2421 */                 String critical = null;
/* 2422 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2423 */                 out.write(10);
/*      */                 
/* 2425 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2426 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2427 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2429 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2431 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2433 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2435 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2436 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2437 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2438 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2441 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2442 */                   String clear = null;
/* 2443 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2444 */                   out.write(10);
/*      */                   
/* 2446 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2447 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2448 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2450 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2452 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2454 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2456 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2457 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2458 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2459 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2462 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2463 */                     String warning = null;
/* 2464 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2465 */                     out.write(10);
/* 2466 */                     out.write(10);
/*      */                     
/* 2468 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2469 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2471 */                     out.write(10);
/* 2472 */                     out.write(10);
/* 2473 */                     out.write(10);
/* 2474 */                     out.write("\n\n\n\n\n\n\n\n\n  \n");
/* 2475 */                     GetWLSGraph wlsGraph = null;
/* 2476 */                     wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2477 */                     if (wlsGraph == null) {
/* 2478 */                       wlsGraph = new GetWLSGraph();
/* 2479 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2481 */                     out.write(10);
/* 2482 */                     CAMGraphs camGraph = null;
/* 2483 */                     camGraph = (CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 2484 */                     if (camGraph == null) {
/* 2485 */                       camGraph = new CAMGraphs();
/* 2486 */                       _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                     }
/* 2488 */                     out.write(10);
/* 2489 */                     Hashtable motypedisplaynames = null;
/* 2490 */                     synchronized (application) {
/* 2491 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2492 */                       if (motypedisplaynames == null) {
/* 2493 */                         motypedisplaynames = new Hashtable();
/* 2494 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2497 */                     out.write(10);
/* 2498 */                     Hashtable availabilitykeys = null;
/* 2499 */                     synchronized (application) {
/* 2500 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2501 */                       if (availabilitykeys == null) {
/* 2502 */                         availabilitykeys = new Hashtable();
/* 2503 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2506 */                     out.write(10);
/* 2507 */                     Hashtable healthkeys = null;
/* 2508 */                     synchronized (application) {
/* 2509 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2510 */                       if (healthkeys == null) {
/* 2511 */                         healthkeys = new Hashtable();
/* 2512 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2515 */                     out.write("\n<script>\nfunction enableSelections(temp)\n{\n\tvar sel = false;  \n\tfor(i=0;i<document.form2.elements.length;i++)\n\t{\n\t\tif(document.form2.elements[i].type==\"checkbox\")\n\t               {\n\t                        var name = document.form2.elements[i].name;\n\t                        if(name==\"checkbox\")\n\t                        {\n\t                        \tvar value = document.form2.elements[i].value;\n\t                        \tsel=document.form2.elements[i].checked;\n\t                        \tif(sel)\n\t                        \t{\n\t                        \t\tbreak;\n\t                        \t}\n\t                        }\n\t                 }\n               }\n               if(!sel)\n               {\n                  alert(\"");
/* 2516 */                     out.print(FormatUtil.getString("am.webclient.qengine.jsalertforreports.text"));
/* 2517 */                     out.write("\");\n               }\nelse \n{\n    if(temp==1)\n\tdocument.form2.action=\"/updateScript.do?method=enableReports&resourceid=");
/* 2518 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2520 */                     out.write("\";\n    else if(temp==2)\n    {\n        if(confirm('");
/* 2521 */                     out.print(FormatUtil.getString("am.webclient.qengine.jsalertfordisablereports.text"));
/* 2522 */                     out.write("'))\n            document.form2.action=\"/updateScript.do?method=disableReports&resourceid=");
/* 2523 */                     if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                       return;
/* 2525 */                     out.write("\";\n        else\n            return;\n    }\n        \n\tdocument.form2.method=\"Post\"\n\tdocument.form2.submit();\n}\n\n}\n</script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/hostdiscoveryform.js\"></SCRIPT>  \n\n");
/*      */                     
/* 2527 */                     Properties ess_atts = new Properties();
/* 2528 */                     String dispname = (String)motypedisplaynames.get(request.getParameter("type")) + " Details";
/* 2529 */                     String resourceName = request.getParameter("resourcename");
/* 2530 */                     String resID = request.getParameter("resourceid");
/* 2531 */                     String resourceid = resID;
/* 2532 */                     request.setAttribute("resourceid", resID);
/* 2533 */                     ArrayList attribIDs = new ArrayList();
/* 2534 */                     String haid = request.getParameter("haid");
/* 2535 */                     String moname = request.getParameter("moname");
/* 2536 */                     ArrayList resIDs = new ArrayList();
/* 2537 */                     resIDs.add(resID);
/* 2538 */                     if (request.getParameter("type").equals("Script Monitor"))
/*      */                     {
/* 2540 */                       attribIDs.add("2200");
/* 2541 */                       attribIDs.add("2201");
/* 2542 */                       attribIDs.add("2202");
/*      */                     }
/*      */                     else
/*      */                     {
/* 2546 */                       attribIDs.add("4300");
/* 2547 */                       attribIDs.add("4301");
/* 2548 */                       attribIDs.add("4303");
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 2553 */                     String resourcetype = request.getParameter("type");
/* 2554 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2555 */                     String encodeurl = URLEncoder.encode("/showresource.do?type=" + resourcetype + "&moname=" + moname + "&method=showdetails&resourcename=" + resourceName + "&resourceid=" + resID + "&haid=" + haid);
/* 2556 */                     String tipCurStatus = FormatUtil.getString("am.webclient.qenginescript.curstatus.tooltip");
/* 2557 */                     HashMap ht_numeric = (HashMap)request.getAttribute("numeric_data");
/* 2558 */                     HashMap ht_string = (HashMap)request.getAttribute("string_data");
/* 2559 */                     HashMap displayname_attributeid = (HashMap)request.getAttribute("display_attributeid");
/* 2560 */                     int attributeid1 = -1;
/* 2561 */                     ArrayList numeric = (ArrayList)request.getAttribute("numeric");
/*      */                     
/*      */ 
/* 2564 */                     ArrayList all_attributes = (ArrayList)request.getAttribute("attributes");
/* 2565 */                     for (int i = 0; i < all_attributes.size(); i++)
/*      */                     {
/*      */                       try
/*      */                       {
/* 2569 */                         attributeid1 = Integer.parseInt(String.valueOf(displayname_attributeid.get(all_attributes.get(i))));
/* 2570 */                         attribIDs.add("" + attributeid1);
/*      */                       }
/*      */                       catch (NumberFormatException e) {}
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 2577 */                     int num_data = ((Integer)request.getAttribute("numeric_size")).intValue();
/* 2578 */                     int str_data = ((Integer)request.getAttribute("string_size")).intValue();
/* 2579 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/* 2581 */                     out.write(10);
/* 2582 */                     out.write(10);
/*      */                     
/* 2584 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2585 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2586 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2588 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2589 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2590 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2592 */                         out.write(32);
/*      */                         
/* 2594 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2595 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2596 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2598 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2600 */                         _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString(dispname));
/* 2601 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2602 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2603 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2606 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2607 */                         out.write(32);
/* 2608 */                         out.write(10);
/* 2609 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2611 */                         out.write(10);
/* 2612 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2614 */                         out.write(10);
/* 2615 */                         out.write(10);
/* 2616 */                         out.write(10);
/*      */                         
/* 2618 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2619 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2620 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2622 */                         _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */                         
/* 2624 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2625 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2626 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2627 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2628 */                             out = _jspx_page_context.pushBody();
/* 2629 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2630 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2633 */                             out.write(10);
/* 2634 */                             out.write("<!--$Id$-->\n\n\n\n");
/*      */                             
/* 2636 */                             int healthid = 2201;
/* 2637 */                             int availabilityid = 2200;
/* 2638 */                             String haid1 = "";
/* 2639 */                             String head = "Script Monitor";
/* 2640 */                             String mon_type = null;
/* 2641 */                             String rtype = request.getParameter("type");
/* 2642 */                             head = rtype;
/* 2643 */                             if (rtype.equals("File System Monitor"))
/*      */                             {
/* 2645 */                               head = "File / Directory Monitor";
/*      */                             }
/*      */                             
/* 2648 */                             String typedisplayname = head;
/* 2649 */                             if (request.getParameter("typedisplayname") != null)
/*      */                             {
/* 2651 */                               typedisplayname = request.getParameter("typedisplayname");
/*      */                             }
/* 2653 */                             else if (request.getAttribute("typedisplayname") != null)
/*      */                             {
/* 2655 */                               typedisplayname = (String)request.getAttribute("typedisplayname");
/*      */                             }
/*      */                             
/* 2658 */                             int baseid = -1;
/*      */                             try
/*      */                             {
/* 2661 */                               if (request.getAttribute("baseid") != null) {
/* 2662 */                                 baseid = Integer.parseInt((String)request.getAttribute("baseid"));
/*      */                               }
/*      */                             }
/*      */                             catch (Exception e2)
/*      */                             {
/* 2667 */                               e2.printStackTrace();
/*      */                             }
/* 2669 */                             String appendParams = "&baseid=" + baseid;
/* 2670 */                             if (baseid != -1)
/*      */                             {
/* 2672 */                               appendParams = appendParams + "&customType=true&basetype=Script Monitor";
/*      */                             }
/* 2674 */                             if ((rtype.equals("file")) || (rtype.equals("directory")) || (rtype.equals("File System Monitor")))
/*      */                             {
/* 2676 */                               rtype = "File System Monitor";
/* 2677 */                               head = "File / Directory Monitor";
/* 2678 */                               typedisplayname = "File / Directory Monitor";
/* 2679 */                             } else if (rtype.equals("Ping Monitor"))
/*      */                             {
/* 2681 */                               head = "Ping Monitor";
/* 2682 */                               rtype = "Ping Monitor";
/* 2683 */                               typedisplayname = "Ping Monitor";
/* 2684 */                               healthid = 9001;
/* 2685 */                               availabilityid = 9000;
/*      */                             }
/*      */                             else
/*      */                             {
/* 2689 */                               availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2690 */                               healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                             }
/*      */                             
/*      */ 
/* 2694 */                             if (request.getParameter("mtype") != null) {
/* 2695 */                               mon_type = request.getParameter("mtype");
/*      */                             }
/*      */                             else {
/* 2698 */                               mon_type = request.getParameter("type");
/*      */                             }
/*      */                             
/* 2701 */                             if (request.getParameter("type").equals("QENGINE"))
/*      */                             {
/* 2703 */                               healthid = 4301;
/* 2704 */                               availabilityid = 4300;
/*      */                             }
/* 2706 */                             else if (mon_type.equals("file")) {
/* 2707 */                               head = "File System Monitor";
/*      */                               
/* 2709 */                               healthid = 6009;
/* 2710 */                               availabilityid = 6008;
/*      */                             }
/* 2712 */                             else if (mon_type.equals("directory"))
/*      */                             {
/* 2714 */                               head = "File System Monitor";
/* 2715 */                               healthid = 6001;
/* 2716 */                               availabilityid = 6000;
/*      */ 
/*      */                             }
/* 2719 */                             else if (request.getParameter("type").equals("Script Monitor"))
/*      */                             {
/* 2721 */                               healthid = 2201;
/* 2722 */                               availabilityid = 2200;
/*      */                             }
/* 2724 */                             else if (mon_type.equals("Ping Monitor"))
/*      */                             {
/* 2726 */                               healthid = 9001;
/* 2727 */                               availabilityid = 9000;
/*      */                             }
/*      */                             else
/*      */                             {
/* 2731 */                               availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2732 */                               healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                             }
/*      */                             
/*      */ 
/* 2736 */                             out.write("\n\n<script language=\"JavaScript\">\n");
/*      */                             
/* 2738 */                             if (request.getParameter("editPage") != null)
/*      */                             {
/* 2740 */                               out.write(10);
/*      */                             }
/*      */                             
/* 2743 */                             out.write("\n\n\n\tfunction confirmDelete()\n \t {\n  var s = confirm(\"");
/* 2744 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2745 */                             out.write("\");\n  if (s)\n         document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 2746 */                             out.print(head);
/* 2747 */                             out.write("&baseid=");
/* 2748 */                             out.print(baseid);
/* 2749 */                             out.write("&select=");
/* 2750 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2752 */                             out.write("\";\n\t }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2753 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2754 */                             out.write("\");\n  if (s){\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2755 */                             if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2757 */                             out.write("&type=");
/* 2758 */                             out.print(rtype);
/* 2759 */                             out.write("&moname=");
/* 2760 */                             out.print(moname);
/* 2761 */                             out.write("&resourcename=");
/* 2762 */                             out.print(resourceName);
/* 2763 */                             out.write("\";\n\n}\n\t }\n\n         function confirmUnManage()\n \t {\n        \t  var show_msg=\"false\";\n              var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2764 */                             out.print(request.getParameter("resourceid"));
/* 2765 */                             out.write("; //No i18n\n              $.ajax({\n                type:'POST', //No i18n\n                url:url,\n                async:false,\n                success: function(data)\n                {\n                  show_msg=data\n                }\n              });\n              if(show_msg.indexOf(\"true\")>-1)\n              {\n                  alert(\"");
/* 2766 */                             out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2767 */                             out.write("\");\n\t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2768 */                             if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2770 */                             out.write("&type=");
/* 2771 */                             out.print(rtype);
/* 2772 */                             out.write("&moname=");
/* 2773 */                             out.print(moname);
/* 2774 */                             out.write("&resourcename=");
/* 2775 */                             out.print(resourceName);
/* 2776 */                             out.write("\";\n             }\n           else { \n        \t   \tvar s = confirm(\"");
/* 2777 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2778 */                             out.write("\");\n       \t\t if (s){\n   \t\t \t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2779 */                             if (_jspx_meth_c_005fout_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2781 */                             out.write("&type=");
/* 2782 */                             out.print(rtype);
/* 2783 */                             out.write("&moname=");
/* 2784 */                             out.print(moname);
/* 2785 */                             out.write("&resourcename=");
/* 2786 */                             out.print(resourceName);
/* 2787 */                             out.write("\";\n\t\t      }\n\t      }\n       }\n  </script>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n    ");
/* 2788 */                             out.print(FormatUtil.getString(typedisplayname));
/* 2789 */                             out.write("\n      </td>\n  </tr>\n      ");
/* 2790 */                             if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2792 */                             out.write("\n      ");
/* 2793 */                             if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2795 */                             out.write(10);
/* 2796 */                             out.write(9);
/* 2797 */                             out.write(9);
/* 2798 */                             if (_jspx_meth_c_005fif_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2800 */                             out.write(10);
/* 2801 */                             out.write("\n\n\n\n");
/*      */                             
/* 2803 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2804 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2805 */                             _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2807 */                             _jspx_th_c_005fif_005f5.setTest("${!empty param.haid && empty invalidhaid}");
/* 2808 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2809 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/* 2811 */                                 out.write(10);
/*      */                                 
/* 2813 */                                 haid1 = "&haid=" + request.getParameter("haid");
/*      */                                 
/* 2815 */                                 out.write(10);
/* 2816 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2817 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2821 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2822 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                             }
/*      */                             
/* 2825 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2826 */                             out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                             
/* 2828 */                             IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2829 */                             _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2830 */                             _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2832 */                             _jspx_th_c_005fif_005f6.setTest("${param.method=='editScript'}");
/* 2833 */                             int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2834 */                             if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                               for (;;) {
/* 2836 */                                 out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 2837 */                                 if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 2839 */                                 out.write("&method=showResourceForResourceID");
/* 2840 */                                 out.print(haid1);
/* 2841 */                                 out.write("\"  class=\"new-left-links\">\n    ");
/* 2842 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2843 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2847 */                             if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2848 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                             }
/*      */                             
/* 2851 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2852 */                             out.write("\n    ");
/* 2853 */                             out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2854 */                             out.write("\n    ");
/* 2855 */                             if (_jspx_meth_c_005fif_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2857 */                             out.write("\n     </td>\n  </tr>\n");
/* 2858 */                             out.write(10);
/* 2859 */                             out.write(32);
/* 2860 */                             out.write(32);
/*      */                             
/* 2862 */                             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2863 */                             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2864 */                             _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2866 */                             _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN || !empty DEMO }");
/* 2867 */                             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2868 */                             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                               for (;;) {
/* 2870 */                                 out.write("\n\n\n\t<tr>\n  ");
/*      */                                 
/* 2872 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2873 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2874 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 2876 */                                 _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2877 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2878 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2880 */                                     out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 2881 */                                     out.print(ALERTCONFIG_TEXT);
/* 2882 */                                     out.write("</a> </td>\n  ");
/* 2883 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2884 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2888 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2889 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2892 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2893 */                                 out.write(10);
/* 2894 */                                 out.write(9);
/*      */                                 
/* 2896 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2897 */                                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2898 */                                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 2900 */                                 _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2901 */                                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2902 */                                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2904 */                                     out.write("\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                     
/* 2906 */                                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2907 */                                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2908 */                                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                     
/* 2910 */                                     _jspx_th_c_005fif_005f9.setTest("${param.method=='editScript'}");
/* 2911 */                                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2912 */                                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                       for (;;) {
/* 2914 */                                         out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 2915 */                                         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                                           return;
/* 2917 */                                         out.write("&method=showResourceForResourceID&alert=true");
/* 2918 */                                         out.print(haid1);
/* 2919 */                                         out.write("\"  class=\"new-left-links\">\n    ");
/* 2920 */                                         out.print(ALERTCONFIG_TEXT);
/* 2921 */                                         out.write("\n    </a>\n    ");
/* 2922 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2923 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2927 */                                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2928 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                     }
/*      */                                     
/* 2931 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2932 */                                     out.write("\n    ");
/*      */                                     
/* 2934 */                                     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2935 */                                     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2936 */                                     _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                     
/* 2938 */                                     _jspx_th_c_005fif_005f10.setTest("${param.method!='editScript'}");
/* 2939 */                                     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2940 */                                     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                       for (;;) {
/* 2942 */                                         out.write("\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2943 */                                         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                           return;
/* 2945 */                                         out.write("&mtype=");
/* 2946 */                                         out.print(mon_type);
/* 2947 */                                         out.write("\" class=\"new-left-links\">\n    ");
/* 2948 */                                         out.print(ALERTCONFIG_TEXT);
/* 2949 */                                         out.write("\n    </a>\n    ");
/* 2950 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2951 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2955 */                                     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2956 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                     }
/*      */                                     
/* 2959 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2960 */                                     out.write("\n    </td>\n");
/* 2961 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2962 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2966 */                                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2967 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2970 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2971 */                                 out.write("\n  </tr>\n  ");
/* 2972 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2973 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2977 */                             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2978 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                             }
/*      */                             
/* 2981 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2982 */                             out.write(10);
/*      */                             
/* 2984 */                             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2985 */                             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2986 */                             _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2988 */                             _jspx_th_c_005fif_005f11.setTest("${param.type=='Script Monitor'}");
/* 2989 */                             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2990 */                             if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                               for (;;) {
/* 2992 */                                 out.write(10);
/* 2993 */                                 out.write(32);
/* 2994 */                                 out.write(32);
/*      */                                 
/* 2996 */                                 IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2997 */                                 _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2998 */                                 _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fif_005f11);
/*      */                                 
/* 3000 */                                 _jspx_th_c_005fif_005f12.setTest("${!empty ADMIN || !empty DEMO }");
/* 3001 */                                 int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3002 */                                 if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                   for (;;) {
/* 3004 */                                     out.write(10);
/* 3005 */                                     out.write(32);
/* 3006 */                                     out.write(32);
/*      */                                     
/* 3008 */                                     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3009 */                                     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3010 */                                     _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f12);
/*      */                                     
/* 3012 */                                     _jspx_th_c_005fif_005f13.setTest("${param.method=='editScript'}");
/* 3013 */                                     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3014 */                                     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                       for (;;) {
/* 3016 */                                         out.write("\n\t <tr>\n  ");
/*      */                                         
/* 3018 */                                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3019 */                                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3020 */                                         _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                         
/* 3022 */                                         _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3023 */                                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3024 */                                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 3026 */                                             out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3027 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3028 */                                             out.write("</a> </td>\n  ");
/* 3029 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3030 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3034 */                                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3035 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3038 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3039 */                                         out.write("\n        ");
/*      */                                         
/* 3041 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3042 */                                         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3043 */                                         _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                         
/* 3045 */                                         _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3046 */                                         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3047 */                                         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 3049 */                                             out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n\n    <a href=\"/showresource.do?resourceid=");
/* 3050 */                                             if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                                               return;
/* 3052 */                                             out.write("&method=showResourceForResourceID&reports=true");
/* 3053 */                                             out.print(haid1);
/* 3054 */                                             out.write("\"  class=\"new-left-links\">\n    ");
/* 3055 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3056 */                                             out.write("\n    </a>\n     </td>\n");
/* 3057 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3058 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3062 */                                         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3063 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3066 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3067 */                                         out.write("\n  </tr>\n  ");
/* 3068 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3069 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3073 */                                     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3074 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                     }
/*      */                                     
/* 3077 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3078 */                                     out.write(10);
/* 3079 */                                     out.write(32);
/* 3080 */                                     out.write(32);
/* 3081 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3082 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3086 */                                 if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3087 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                                 }
/*      */                                 
/* 3090 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3091 */                                 out.write("\n\n  ");
/*      */                                 
/* 3093 */                                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3094 */                                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3095 */                                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f11);
/*      */                                 
/* 3097 */                                 _jspx_th_c_005fif_005f14.setTest("${!empty ADMIN || !empty DEMO }");
/* 3098 */                                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3099 */                                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                   for (;;) {
/* 3101 */                                     out.write(10);
/* 3102 */                                     out.write(32);
/* 3103 */                                     out.write(32);
/*      */                                     
/* 3105 */                                     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3106 */                                     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3107 */                                     _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f14);
/*      */                                     
/* 3109 */                                     _jspx_th_c_005fif_005f15.setTest("${param.method!='editScript'}");
/* 3110 */                                     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3111 */                                     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                       for (;;) {
/* 3113 */                                         out.write("\n  <tr>\n");
/*      */                                         
/* 3115 */                                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3116 */                                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3117 */                                         _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 3119 */                                         _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3120 */                                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3121 */                                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                           for (;;) {
/* 3123 */                                             out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3124 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3125 */                                             out.write("</a> </td>\n  ");
/* 3126 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3127 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3131 */                                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3132 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                         }
/*      */                                         
/* 3135 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3136 */                                         out.write("\n        ");
/*      */                                         
/* 3138 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3139 */                                         _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3140 */                                         _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 3142 */                                         _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3143 */                                         int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3144 */                                         if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                           for (;;) {
/* 3146 */                                             out.write("\n\n    <td class=\"leftlinkstd\"><a href=\"javascript:enableReports()\" class=\"new-left-links\">");
/* 3147 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3148 */                                             out.write("</a></td>\n   </td>\n");
/* 3149 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3150 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3154 */                                         if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3155 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                         }
/*      */                                         
/* 3158 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3159 */                                         out.write("\n  </tr>\n  ");
/* 3160 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3161 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3165 */                                     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3166 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                     }
/*      */                                     
/* 3169 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3170 */                                     out.write(10);
/* 3171 */                                     out.write(32);
/* 3172 */                                     out.write(32);
/* 3173 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3174 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3178 */                                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3179 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                 }
/*      */                                 
/* 3182 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3183 */                                 out.write(10);
/* 3184 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3185 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3189 */                             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3190 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                             }
/*      */                             
/* 3193 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3194 */                             out.write(10);
/* 3195 */                             out.write(10);
/*      */                             
/* 3197 */                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3198 */                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3199 */                             _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3201 */                             _jspx_th_logic_005fpresent_005f3.setRole("ENTERPRISEADMIN");
/* 3202 */                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3203 */                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                               for (;;) {
/* 3205 */                                 out.write("\n  \n");
/*      */                                 
/* 3207 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3208 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3209 */                                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                 
/* 3211 */                                 _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3212 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3213 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3215 */                                     out.write("\n<tr>\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3216 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3217 */                                     out.write("</a> </td> \n</tr>\n");
/* 3218 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3219 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3223 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3224 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 3227 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3228 */                                 out.write(10);
/* 3229 */                                 out.write(10);
/*      */                                 
/* 3231 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3232 */                                 _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3233 */                                 _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                 
/* 3235 */                                 _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3236 */                                 int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3237 */                                 if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                   for (;;) {
/* 3239 */                                     out.write(10);
/* 3240 */                                     if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 3241 */                                       out.write("\n\t<tr>\n   <td height=\"21\" class=\"leftlinkstd\">\n   ");
/*      */                                       
/* 3243 */                                       String temphaid = request.getParameter("haid");
/*      */                                       try
/*      */                                       {
/* 3246 */                                         Integer.parseInt(temphaid);
/*      */                                         
/* 3248 */                                         if (rtype.equals("Ping Monitor")) {
/* 3249 */                                           out.write("\n <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3250 */                                           if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3252 */                                           out.write("&method=editScript");
/* 3253 */                                           out.print(appendParams);
/* 3254 */                                           out.write("&resourceid=");
/* 3255 */                                           if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3257 */                                           out.write("&aam_jump=true&type=");
/* 3258 */                                           out.print(request.getParameter("type"));
/* 3259 */                                           if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3261 */                                           out.write("&resourcename=");
/* 3262 */                                           out.print(resourceName);
/* 3263 */                                           out.write("&mtype=");
/* 3264 */                                           out.print(mon_type);
/* 3265 */                                           out.write("\"  class=\"new-left-links\">\n\n");
/*      */                                         } else {
/* 3267 */                                           out.write("\n    <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3268 */                                           if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3270 */                                           out.write("&method=editScript");
/* 3271 */                                           out.print(appendParams);
/* 3272 */                                           out.write("&resourceid=");
/* 3273 */                                           if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3275 */                                           out.write("&aam_jump=true&type=");
/* 3276 */                                           out.print(request.getParameter("type"));
/* 3277 */                                           if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3279 */                                           out.write("&resourcename=");
/* 3280 */                                           out.print(resourceName);
/* 3281 */                                           out.write("&mtype=");
/* 3282 */                                           out.print(mon_type);
/* 3283 */                                           out.write("\"  class=\"new-left-links\">\n   ");
/*      */                                         }
/*      */                                         
/*      */                                       }
/*      */                                       catch (NumberFormatException ne)
/*      */                                       {
/* 3289 */                                         if (rtype.equals("Ping Monitor")) {
/* 3290 */                                           out.write("\n<a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 3291 */                                           out.print(request.getParameter("resourceid"));
/* 3292 */                                           out.write("&type=");
/* 3293 */                                           out.print(request.getParameter("type"));
/* 3294 */                                           out.write("&moname=");
/* 3295 */                                           out.print(moname);
/* 3296 */                                           out.write("&method=showdetails&resourcename=");
/* 3297 */                                           out.print(request.getParameter("resourcename"));
/* 3298 */                                           out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n\n");
/*      */                                         } else {
/* 3300 */                                           out.write("\n\n    <a target=\"mas_window\" href=\"/updateScript.do?method=editScript");
/* 3301 */                                           out.print(appendParams);
/* 3302 */                                           out.write("&resourceid=");
/* 3303 */                                           if (_jspx_meth_c_005fout_005f19(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3305 */                                           out.write("&aam_jump=true&type=");
/* 3306 */                                           out.print(request.getParameter("type"));
/* 3307 */                                           if (_jspx_meth_c_005fout_005f20(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3309 */                                           out.write("&resourcename=");
/* 3310 */                                           out.print(resourceName);
/* 3311 */                                           out.write("&mtype=");
/* 3312 */                                           out.print(mon_type);
/* 3313 */                                           out.write("\"  class=\"new-left-links\">\n   ");
/*      */                                         }
/*      */                                       }
/*      */                                       
/* 3317 */                                       out.write(10);
/* 3318 */                                       out.write(32);
/* 3319 */                                       out.write(32);
/* 3320 */                                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3321 */                                       out.write("\n   </td> </tr>\n   ");
/*      */                                     }
/* 3323 */                                     out.write(10);
/* 3324 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3325 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3329 */                                 if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3330 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                                 }
/*      */                                 
/* 3333 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3334 */                                 out.write(10);
/* 3335 */                                 out.write(32);
/* 3336 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3337 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3341 */                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3342 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                             }
/*      */                             
/* 3345 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3346 */                             out.write(10);
/* 3347 */                             out.write(32);
/* 3348 */                             out.write(10);
/*      */                             
/* 3350 */                             IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3351 */                             _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3352 */                             _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3354 */                             _jspx_th_c_005fif_005f16.setTest("${(!empty ADMIN || !empty DEMO) }");
/* 3355 */                             int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3356 */                             if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                               for (;;) {
/* 3358 */                                 out.write("\n  <tr>\n");
/*      */                                 
/* 3360 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3361 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3362 */                                 _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f16);
/*      */                                 
/* 3364 */                                 _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3365 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3366 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3368 */                                     out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3369 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3370 */                                     out.write("</a> </td>\n  ");
/* 3371 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3372 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3376 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3377 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3380 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3381 */                                 out.write("\n        ");
/*      */                                 
/* 3383 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3384 */                                 _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3385 */                                 _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_c_005fif_005f16);
/*      */                                 
/* 3387 */                                 _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 3388 */                                 int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 3389 */                                 if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3391 */                                     out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                     
/* 3393 */                                     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3394 */                                     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3395 */                                     _jspx_th_c_005fif_005f17.setParent(_jspx_th_logic_005fnotPresent_005f4);
/*      */                                     
/* 3397 */                                     _jspx_th_c_005fif_005f17.setTest("${param.actionmethod!='editScript' }");
/* 3398 */                                     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3399 */                                     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                       for (;;) {
/* 3401 */                                         out.write("\n    ");
/*      */                                         
/* 3403 */                                         String temphaid = request.getParameter("haid");
/*      */                                         try
/*      */                                         {
/* 3406 */                                           Integer.parseInt(temphaid);
/*      */                                           
/* 3408 */                                           if (rtype.equals("Ping Monitor")) {
/* 3409 */                                             out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                           } else {
/* 3411 */                                             out.write("\n\n<a href=\"/updateScript.do?haid=");
/* 3412 */                                             if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3414 */                                             out.write("&method=editScript");
/* 3415 */                                             out.print(appendParams);
/* 3416 */                                             out.write("&resourceid=");
/* 3417 */                                             if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3419 */                                             out.write("&type=");
/* 3420 */                                             out.print(rtype);
/* 3421 */                                             if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3423 */                                             out.write("&resourcename=");
/* 3424 */                                             out.print(resourceName);
/* 3425 */                                             out.write("&mtype=");
/* 3426 */                                             out.print(mon_type);
/* 3427 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                           }
/*      */                                           
/*      */                                         }
/*      */                                         catch (NumberFormatException ne)
/*      */                                         {
/* 3433 */                                           if (rtype.equals("Ping Monitor")) {
/* 3434 */                                             out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                           } else {
/* 3436 */                                             out.write("\n\n\n<a href=\"/updateScript.do?method=editScript");
/* 3437 */                                             out.print(appendParams);
/* 3438 */                                             out.write("&resourceid=");
/* 3439 */                                             if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3441 */                                             out.write("&type=");
/* 3442 */                                             out.print(rtype);
/* 3443 */                                             if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3445 */                                             out.write("&resourcename=");
/* 3446 */                                             out.print(resourceName);
/* 3447 */                                             out.write("&mtype=");
/* 3448 */                                             out.print(mon_type);
/* 3449 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 3453 */                                         out.write(10);
/* 3454 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 3455 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3459 */                                     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 3460 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                                     }
/*      */                                     
/* 3463 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 3464 */                                     out.write("\n\n    ");
/* 3465 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3466 */                                     out.write("\n    ");
/* 3467 */                                     if (_jspx_meth_c_005fif_005f18(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                       return;
/* 3469 */                                     out.write("</td>\n");
/* 3470 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 3471 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3475 */                                 if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 3476 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 3479 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 3480 */                                 out.write("\n  </tr>\n  ");
/* 3481 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3482 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3486 */                             if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3487 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                             }
/*      */                             
/* 3490 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3491 */                             out.write("\n\n  ");
/*      */                             
/* 3493 */                             IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3494 */                             _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3495 */                             _jspx_th_c_005fif_005f19.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3497 */                             _jspx_th_c_005fif_005f19.setTest("${!empty ADMIN || !empty DEMO }");
/* 3498 */                             int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3499 */                             if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                               for (;;) {
/* 3501 */                                 out.write(10);
/*      */                                 
/* 3503 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3504 */                                 _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 3505 */                                 _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3507 */                                 _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 3508 */                                 int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 3509 */                                 if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3511 */                                     out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 3512 */                                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3513 */                                     out.write("</a></td>\n  </tr>\n");
/* 3514 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 3515 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3519 */                                 if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 3520 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3523 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 3524 */                                 out.write(10);
/*      */                                 
/* 3526 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3527 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3528 */                                 _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3530 */                                 _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 3531 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3532 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;) {
/* 3534 */                                     out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3535 */                                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3536 */                                     out.write("</a></td>\n\n");
/* 3537 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3538 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3542 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3543 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                 }
/*      */                                 
/* 3546 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3547 */                                 out.write("\n\n  ");
/* 3548 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3549 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3553 */                             if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3554 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                             }
/*      */                             
/* 3557 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3558 */                             out.write(10);
/* 3559 */                             out.write(32);
/* 3560 */                             out.write(32);
/*      */                             
/* 3562 */                             IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3563 */                             _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3564 */                             _jspx_th_c_005fif_005f20.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3566 */                             _jspx_th_c_005fif_005f20.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 3567 */                             int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3568 */                             if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                               for (;;) {
/* 3570 */                                 out.write("\n\n  <tr>\n\n  ");
/*      */                                 
/* 3572 */                                 if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                 {
/*      */ 
/* 3575 */                                   out.write(10);
/* 3576 */                                   out.write(9);
/*      */                                   
/* 3578 */                                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3579 */                                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3580 */                                   _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3582 */                                   _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 3583 */                                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3584 */                                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 3586 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3587 */                                       out.print(FormatUtil.getString("Manage"));
/* 3588 */                                       out.write("</a> </td>\n  ");
/* 3589 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3590 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3594 */                                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3595 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 3598 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3599 */                                   out.write("\n        ");
/*      */                                   
/* 3601 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3602 */                                   _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 3603 */                                   _jspx_th_logic_005fnotPresent_005f6.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3605 */                                   _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 3606 */                                   int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 3607 */                                   if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 3609 */                                       out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 3610 */                                       out.print(FormatUtil.getString("Manage"));
/* 3611 */                                       out.write("</A></td>\n");
/* 3612 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 3613 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3617 */                                   if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 3618 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                                   }
/*      */                                   
/* 3621 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 3622 */                                   out.write("\n    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3628 */                                   out.write(10);
/*      */                                   
/* 3630 */                                   PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3631 */                                   _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3632 */                                   _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3634 */                                   _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 3635 */                                   int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3636 */                                   if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                     for (;;) {
/* 3638 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3639 */                                       out.print(FormatUtil.getString("UnManage"));
/* 3640 */                                       out.write("</a> </td>\n  ");
/* 3641 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3642 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3646 */                                   if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3647 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                   }
/*      */                                   
/* 3650 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3651 */                                   out.write("\n        ");
/*      */                                   
/* 3653 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3654 */                                   _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 3655 */                                   _jspx_th_logic_005fnotPresent_005f7.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3657 */                                   _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 3658 */                                   int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 3659 */                                   if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 3661 */                                       out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 3662 */                                       out.print(FormatUtil.getString("UnManage"));
/* 3663 */                                       out.write("</A></td>\n");
/* 3664 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 3665 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3669 */                                   if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 3670 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 3673 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 3674 */                                   out.write("\n    ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3678 */                                 out.write("\n  </tr>\n  ");
/* 3679 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3680 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3684 */                             if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3685 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                             }
/*      */                             
/* 3688 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3689 */                             out.write("\n   ");
/*      */                             
/* 3691 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3692 */                             _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 3693 */                             _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3695 */                             _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 3696 */                             int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 3697 */                             if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                               for (;;) {
/* 3699 */                                 out.write("\n    ");
/*      */                                 
/* 3701 */                                 String resourceid_poll = request.getParameter("resourceid");
/* 3702 */                                 String resourcetype_poll = request.getParameter("type");
/*      */                                 
/* 3704 */                                 out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3705 */                                 out.print(resourceid_poll);
/* 3706 */                                 out.write("&baseid=");
/* 3707 */                                 out.print(baseid);
/* 3708 */                                 out.write("&resourcetype=");
/* 3709 */                                 out.print(resourcetype_poll);
/* 3710 */                                 out.write("\" class=\"new-left-links\"> ");
/* 3711 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3712 */                                 out.write("</a></td>\n    </tr>\n    ");
/* 3713 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 3714 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3718 */                             if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 3719 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8); return;
/*      */                             }
/*      */                             
/* 3722 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 3723 */                             out.write("\n    ");
/*      */                             
/* 3725 */                             PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3726 */                             _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3727 */                             _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3729 */                             _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 3730 */                             int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3731 */                             if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                               for (;;) {
/* 3733 */                                 out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3734 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3735 */                                 out.write("</a></td>\n          </td>\n    ");
/* 3736 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3737 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3741 */                             if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3742 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                             }
/*      */                             
/* 3745 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3746 */                             out.write("\n    ");
/* 3747 */                             out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                             
/* 3749 */                             if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                             {
/* 3751 */                               Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3752 */                               String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                               
/* 3754 */                               String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3755 */                               String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3756 */                               if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                               {
/* 3758 */                                 if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                                 {
/*      */ 
/* 3761 */                                   out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3762 */                                   out.print(ciInfoUrl);
/* 3763 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3764 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3765 */                                   out.write("</a></td>");
/* 3766 */                                   out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3767 */                                   out.print(ciRLUrl);
/* 3768 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3769 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3770 */                                   out.write("</a></td>");
/* 3771 */                                   out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                                 }
/* 3775 */                                 else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                                 {
/*      */ 
/* 3778 */                                   out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3779 */                                   out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3780 */                                   out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3781 */                                   out.print(ciInfoUrl);
/* 3782 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3783 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3784 */                                   out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3785 */                                   out.print(ciRLUrl);
/* 3786 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3787 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3788 */                                   out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/* 3793 */                             out.write("\n \n \n\n");
/* 3794 */                             out.write("\n</table>\n\n <br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3795 */                             out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3796 */                             out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3797 */                             if (_jspx_meth_c_005fout_005f26(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3799 */                             out.write("&attributeid=");
/* 3800 */                             out.print(healthid);
/* 3801 */                             out.write("')\"\n      class=\"new-left-links\">");
/* 3802 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3803 */                             out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3804 */                             if (_jspx_meth_c_005fout_005f27(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3806 */                             out.write("&attributeid=");
/* 3807 */                             out.print(healthid);
/* 3808 */                             out.write("')\">");
/* 3809 */                             out.print(getSeverityImageForHealth(alert.getProperty(request.getParameter("resourceid") + "#" + healthid)));
/* 3810 */                             out.write("</a></td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3811 */                             if (_jspx_meth_c_005fout_005f28(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3813 */                             out.write("&attributeid=");
/* 3814 */                             out.print(availabilityid);
/* 3815 */                             out.write("')\"\n      class=\"new-left-links\">");
/* 3816 */                             out.print(FormatUtil.getString("Availability"));
/* 3817 */                             out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3818 */                             if (_jspx_meth_c_005fout_005f29(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3820 */                             out.write("&attributeid=");
/* 3821 */                             out.print(availabilityid);
/* 3822 */                             out.write("')\">");
/* 3823 */                             out.print(getSeverityImageForAvailability(alert.getProperty(request.getParameter("resourceid") + "#" + availabilityid)));
/* 3824 */                             out.write("</a></td>\n  </tr>\n</table>\n\n<br>\n\n<br>\n");
/* 3825 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                             
/*      */ 
/*      */ 
/* 3829 */                             boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3830 */                             if (EnterpriseUtil.isIt360MSPEdition)
/*      */                             {
/* 3832 */                               showAssociatedBSG = false;
/*      */                               
/*      */ 
/*      */ 
/* 3836 */                               CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3837 */                               CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3838 */                               String loginName = request.getUserPrincipal().getName();
/* 3839 */                               CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                               
/* 3841 */                               if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                               {
/* 3843 */                                 showAssociatedBSG = true;
/*      */                               }
/*      */                             }
/* 3846 */                             String monitorType = request.getParameter("type");
/* 3847 */                             ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3848 */                             boolean mon = conf1.isConfMonitor(monitorType);
/* 3849 */                             if (showAssociatedBSG)
/*      */                             {
/* 3851 */                               Hashtable associatedmgs = new Hashtable();
/* 3852 */                               String resId = request.getParameter("resourceid");
/* 3853 */                               request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3854 */                               if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                               {
/* 3856 */                                 mon = false;
/*      */                               }
/*      */                               
/* 3859 */                               if (!mon)
/*      */                               {
/* 3861 */                                 out.write(10);
/* 3862 */                                 out.write(10);
/*      */                                 
/* 3864 */                                 IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3865 */                                 _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 3866 */                                 _jspx_th_c_005fif_005f21.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3868 */                                 _jspx_th_c_005fif_005f21.setTest("${!empty associatedmgs}");
/* 3869 */                                 int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 3870 */                                 if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                   for (;;) {
/* 3872 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3873 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3874 */                                     out.write("</td>\n        </tr>\n        ");
/*      */                                     
/* 3876 */                                     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3877 */                                     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3878 */                                     _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f21);
/*      */                                     
/* 3880 */                                     _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                                     
/* 3882 */                                     _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                                     
/* 3884 */                                     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3885 */                                     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                     try {
/* 3887 */                                       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3888 */                                       if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                         for (;;) {
/* 3890 */                                           out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3891 */                                           if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3949 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3950 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3893 */                                           out.write("&method=showApplication\" title=\"");
/* 3894 */                                           if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3949 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3950 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3896 */                                           out.write("\"  class=\"new-left-links\">\n         ");
/* 3897 */                                           if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3949 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3950 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3899 */                                           out.write("\n    \t");
/* 3900 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3901 */                                           out.write("\n         </a></td>\n        <td>");
/*      */                                           
/* 3903 */                                           PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3904 */                                           _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3905 */                                           _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                           
/* 3907 */                                           _jspx_th_logic_005fpresent_005f10.setRole("ADMIN");
/* 3908 */                                           int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3909 */                                           if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                             for (;;) {
/* 3911 */                                               out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3912 */                                               if (_jspx_meth_c_005fout_005f33(_jspx_th_logic_005fpresent_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3949 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3950 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3914 */                                               out.write(39);
/* 3915 */                                               out.write(44);
/* 3916 */                                               out.write(39);
/* 3917 */                                               out.print(resId);
/* 3918 */                                               out.write(39);
/* 3919 */                                               out.write(44);
/* 3920 */                                               out.write(39);
/* 3921 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3922 */                                               out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3923 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3924 */                                               out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3925 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 3926 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3930 */                                           if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 3931 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3949 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3950 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3934 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 3935 */                                           out.write("</td>\n        </tr>\n\t");
/* 3936 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3937 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3941 */                                       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3949 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3950 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 3945 */                                         int tmp10711_10710 = 0; int[] tmp10711_10708 = _jspx_push_body_count_c_005fforEach_005f0; int tmp10713_10712 = tmp10711_10708[tmp10711_10710];tmp10711_10708[tmp10711_10710] = (tmp10713_10712 - 1); if (tmp10713_10712 <= 0) break;
/* 3946 */                                         out = _jspx_page_context.popBody(); }
/* 3947 */                                       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 3949 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3950 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                     }
/* 3952 */                                     out.write("\n      </table>\n ");
/* 3953 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 3954 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3958 */                                 if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 3959 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                                 }
/*      */                                 
/* 3962 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 3963 */                                 out.write(10);
/* 3964 */                                 out.write(32);
/*      */                                 
/* 3966 */                                 IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3967 */                                 _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 3968 */                                 _jspx_th_c_005fif_005f22.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3970 */                                 _jspx_th_c_005fif_005f22.setTest("${empty associatedmgs}");
/* 3971 */                                 int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 3972 */                                 if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                   for (;;) {
/* 3974 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3975 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3976 */                                     out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3977 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3978 */                                     out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3979 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 3980 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3984 */                                 if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 3985 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                                 }
/*      */                                 
/* 3988 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 3989 */                                 out.write(10);
/* 3990 */                                 out.write(32);
/* 3991 */                                 out.write(10);
/*      */ 
/*      */                               }
/* 3994 */                               else if (mon)
/*      */                               {
/*      */ 
/*      */ 
/* 3998 */                                 out.write(10);
/*      */                                 
/* 4000 */                                 IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4001 */                                 _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 4002 */                                 _jspx_th_c_005fif_005f23.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 4004 */                                 _jspx_th_c_005fif_005f23.setTest("${!empty associatedmgs}");
/* 4005 */                                 int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 4006 */                                 if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                   for (;;) {
/* 4008 */                                     out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 4009 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                       return;
/* 4011 */                                     out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                     
/* 4013 */                                     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4014 */                                     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4015 */                                     _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f23);
/*      */                                     
/* 4017 */                                     _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                     
/* 4019 */                                     _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                     
/* 4021 */                                     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 4022 */                                     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                     try {
/* 4024 */                                       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4025 */                                       if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                         for (;;) {
/* 4027 */                                           out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 4028 */                                           if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4089 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4090 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4030 */                                           out.write("&method=showApplication\" title=\"");
/* 4031 */                                           if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4089 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4090 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4033 */                                           out.write("\"  class=\"staticlinks\">\n         ");
/* 4034 */                                           if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4089 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4090 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4036 */                                           out.write("\n    \t");
/* 4037 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4038 */                                           out.write("</a></span>\t\n\t\t ");
/*      */                                           
/* 4040 */                                           PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4041 */                                           _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 4042 */                                           _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                           
/* 4044 */                                           _jspx_th_logic_005fpresent_005f11.setRole("ADMIN");
/* 4045 */                                           int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 4046 */                                           if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                             for (;;) {
/* 4048 */                                               out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 4049 */                                               if (_jspx_meth_c_005fout_005f37(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4089 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4090 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 4051 */                                               out.write(39);
/* 4052 */                                               out.write(44);
/* 4053 */                                               out.write(39);
/* 4054 */                                               out.print(resId);
/* 4055 */                                               out.write(39);
/* 4056 */                                               out.write(44);
/* 4057 */                                               out.write(39);
/* 4058 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4059 */                                               out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 4060 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4061 */                                               out.write("\"  title=\"");
/* 4062 */                                               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4089 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4090 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 4064 */                                               out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 4065 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 4066 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4070 */                                           if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 4071 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4089 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4090 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4074 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4075 */                                           out.write("\n\n\t\t \t");
/* 4076 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4077 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4081 */                                       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4089 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4090 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 4085 */                                         int tmp11737_11736 = 0; int[] tmp11737_11734 = _jspx_push_body_count_c_005fforEach_005f1; int tmp11739_11738 = tmp11737_11734[tmp11737_11736];tmp11737_11734[tmp11737_11736] = (tmp11739_11738 - 1); if (tmp11739_11738 <= 0) break;
/* 4086 */                                         out = _jspx_page_context.popBody(); }
/* 4087 */                                       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 4089 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4090 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     }
/* 4092 */                                     out.write("\n\t\n\t\t\t</td>\n\t ");
/* 4093 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 4094 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4098 */                                 if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 4099 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                                 }
/*      */                                 
/* 4102 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 4103 */                                 out.write(10);
/* 4104 */                                 out.write(32);
/* 4105 */                                 if (_jspx_meth_c_005fif_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 4107 */                                 out.write(32);
/* 4108 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */                             }
/* 4112 */                             else if (mon)
/*      */                             {
/*      */ 
/* 4115 */                               out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4116 */                               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 4118 */                               out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 4122 */                             out.write(9);
/* 4123 */                             out.write(9);
/* 4124 */                             out.write(10);
/*      */                             
/* 4126 */                             ArrayList menupos = new ArrayList(5);
/* 4127 */                             menupos.add("350");
/* 4128 */                             pageContext.setAttribute("menupos", menupos);
/*      */                             
/* 4130 */                             out.write(10);
/* 4131 */                             out.write(10);
/* 4132 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4133 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4136 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4137 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4140 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4141 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 4144 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4145 */                         out.write(10);
/* 4146 */                         out.write(10);
/*      */                         
/* 4148 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4149 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4150 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 4152 */                         _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                         
/* 4154 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 4155 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4156 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 4157 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4158 */                             out = _jspx_page_context.pushBody();
/* 4159 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 4160 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4163 */                             out.write(" \n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n\t");
/*      */                             
/* 4165 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 4166 */                             String aid = request.getParameter("haid");
/* 4167 */                             String haName = null;
/* 4168 */                             if (aid != null)
/*      */                             {
/* 4170 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 4173 */                             out.write(10);
/* 4174 */                             out.write(9);
/*      */                             
/* 4176 */                             IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4177 */                             _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 4178 */                             _jspx_th_c_005fif_005f25.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4180 */                             _jspx_th_c_005fif_005f25.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4181 */                             int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 4182 */                             if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                               for (;;) {
/* 4184 */                                 out.write("\n        <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 4185 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 4186 */                                 out.write(" &gt; ");
/* 4187 */                                 out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 4188 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 4189 */                                 out.print(resourceName);
/* 4190 */                                 out.write(" </span></td>\n\t");
/* 4191 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 4192 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4196 */                             if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 4197 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                             }
/*      */                             
/* 4200 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 4201 */                             out.write(10);
/* 4202 */                             out.write(9);
/*      */                             
/* 4204 */                             IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4205 */                             _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 4206 */                             _jspx_th_c_005fif_005f26.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4208 */                             _jspx_th_c_005fif_005f26.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4209 */                             int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 4210 */                             if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                               for (;;) {
/* 4212 */                                 out.write("\t\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 4213 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 4214 */                                 out.write(" &gt; ");
/* 4215 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes(request.getParameter("type")));
/* 4216 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 4217 */                                 out.print(resourceName);
/* 4218 */                                 out.write("</span></td>\n\t");
/* 4219 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 4220 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4224 */                             if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 4225 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                             }
/*      */                             
/* 4228 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 4229 */                             out.write("\n    </tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n<br>\n \n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr> \n  <td width=\"573\" height=\"123\" valign=\"top\"> <table width=\"96%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n  <tr> \n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 4230 */                             out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 4231 */                             out.write(" </td>\n        </tr>\n        <tr> \n          <td width=\"32%\" class=\"monitorinfoodd\">");
/* 4232 */                             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4233 */                             out.write(" </td>\n          <td width=\"68%\" class=\"monitorinfoodd\"> ");
/* 4234 */                             out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 4235 */                             out.write("\n          </td>\n        </tr>\n        <tr> \n          <td class=\"monitorinfoodd\">");
/* 4236 */                             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 4237 */                             out.write(" </td>\n          <td class=\"monitorinfoodd\">");
/* 4238 */                             out.print(FormatUtil.getString("am.webclient.qengine.type.text"));
/* 4239 */                             out.write("</td>\n        </tr>\n\t\t");
/* 4240 */                             out.write("<!--$Id$-->\n");
/*      */                             
/* 4242 */                             String hostName = "localhost";
/*      */                             try {
/* 4244 */                               hostName = InetAddress.getLocalHost().getHostName();
/*      */                             } catch (Exception ex) {
/* 4246 */                               ex.printStackTrace();
/*      */                             }
/* 4248 */                             String portNumber = System.getProperty("webserver.port");
/* 4249 */                             String styleClass = "monitorinfoodd";
/* 4250 */                             if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 4251 */                               styleClass = "whitegrayborder-conf-mon";
/*      */                             }
/*      */                             
/* 4254 */                             out.write(10);
/*      */                             
/* 4256 */                             PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4257 */                             _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 4258 */                             _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4260 */                             _jspx_th_logic_005fpresent_005f12.setRole("ENTERPRISEADMIN");
/* 4261 */                             int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 4262 */                             if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                               for (;;) {
/* 4264 */                                 out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 4265 */                                 out.print(styleClass);
/* 4266 */                                 out.write(34);
/* 4267 */                                 out.write(62);
/* 4268 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 4269 */                                 out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 4270 */                                 out.print(styleClass);
/* 4271 */                                 out.write(34);
/* 4272 */                                 out.write(62);
/* 4273 */                                 out.print(hostName);
/* 4274 */                                 out.write(95);
/* 4275 */                                 out.print(portNumber);
/* 4276 */                                 out.write("</td>\n</tr>\n");
/* 4277 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 4278 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4282 */                             if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 4283 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                             }
/*      */                             
/* 4286 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 4287 */                             out.write(10);
/* 4288 */                             out.write("\n\t\t<tr> \n          <td class=\"monitorinfoeven\" valign=\"top\">");
/* 4289 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4290 */                             out.write("</td>\n          <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4291 */                             out.print(resID);
/* 4292 */                             out.write("&attributeid=");
/* 4293 */                             out.print((String)systeminfo.get("HEALTHID"));
/* 4294 */                             out.write("')\">");
/* 4295 */                             out.print(getSeverityImageForHealth(alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"))));
/* 4296 */                             out.write("</a>\n\t\t  ");
/* 4297 */                             out.print(getHideAndShowRCAMessage(alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID") + "#" + "MESSAGE"), (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"), alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID")), resID));
/* 4298 */                             out.write("\n\t\t  ");
/* 4299 */                             if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resID, (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID")) != 0) {
/* 4300 */                               out.write("\n\t\t  <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 4301 */                               out.print(resID + "_" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"));
/* 4302 */                               out.write("&monitortype=");
/* 4303 */                               out.print(resourcetype);
/* 4304 */                               out.write("')\">");
/* 4305 */                               out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 4306 */                               out.write("</a></span>\n          ");
/*      */                             }
/* 4308 */                             out.write("\n\t</td>\n        </tr>\n        ");
/*      */                             
/* 4310 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4311 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 4312 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4314 */                             _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 4315 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 4316 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 4318 */                                 out.write("\n        <tr> \n          <td class=\"monitorinfoeven\">");
/* 4319 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4320 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr> \n          <td class=\"monitorinfoodd\">");
/* 4321 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4322 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        ");
/* 4323 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 4324 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4328 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 4329 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 4332 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 4333 */                             out.write("\n        ");
/*      */                             
/* 4335 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4336 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 4337 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4339 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 4340 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 4341 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 4343 */                                 out.write("\n        <tr> \n          <td class=\"monitorinfoeven\">");
/* 4344 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4345 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 4346 */                                 out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 4347 */                                 out.write("</td>\n        </tr>\n        <tr> \n          <td class=\"monitorinfoodd\">");
/* 4348 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4349 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 4350 */                                 out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 4351 */                                 out.write("</td>\n        </tr>\n        ");
/* 4352 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 4353 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4357 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 4358 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 4361 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 4362 */                             out.write("\n        ");
/* 4363 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 4364 */                             out.write("\n    </table>\n  </td>\n  <td width=\"355\" valign=\"top\"><table align=left width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr> \n          <td height=\"28\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 4365 */                             out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 4366 */                             out.write("</td>\n        </tr>\n        <tr> \n          <td colspan=\"3\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n              <tr> \n                <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4367 */                             if (_jspx_meth_c_005fout_005f38(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4369 */                             out.write("&period=1&resourcename=");
/* 4370 */                             if (_jspx_meth_c_005fout_005f39(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4372 */                             out.write("')\"> \n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4373 */                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4374 */                             out.write("\"></a></td>\n                <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4375 */                             if (_jspx_meth_c_005fout_005f40(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4377 */                             out.write("&period=2&resourcename=");
/* 4378 */                             if (_jspx_meth_c_005fout_005f41(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4380 */                             out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4381 */                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4382 */                             out.write("\"></a></td>\n              </tr>\n            </table></td>\n          ");
/*      */                             
/*      */ 
/* 4385 */                             wlsGraph.setParam(resID, "AVAILABILITY");
/*      */                             
/* 4387 */                             out.write("\n        </tr>\n        <tr align=\"center\"> \n          <td height=\"28\" colspan=\"3\" class=\"whitegrayborder\"> ");
/* 4388 */                             if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4390 */                             out.write("</td>\n        </tr>\n        <tr> \n          <td width=\"39%\" height=\"25\" class=\"yellowgrayborder\"  title=\"");
/* 4391 */                             out.print(tipCurStatus);
/* 4392 */                             out.write(34);
/* 4393 */                             out.write(62);
/* 4394 */                             out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 4395 */                             out.write(" :</td>\n          <td width=\"10%\" height=\"25\" align=\"left\" class=\"yellowgrayborder\"> <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4396 */                             out.print(resID);
/* 4397 */                             out.write("&attributeid=");
/* 4398 */                             out.print(availabilitykeys.get(resourcetype));
/* 4399 */                             out.write("&alertconfigurl=");
/* 4400 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "&attributeIDs=" + availabilitykeys.get(resourcetype) + "&attributeToSelect=" + availabilitykeys.get(resourcetype) + "&redirectto=" + encodeurl));
/* 4401 */                             out.write("')\"> \n            ");
/* 4402 */                             out.print(getSeverityImageForAvailability(alert.getProperty(resID + "#" + availabilitykeys.get(resourcetype))));
/* 4403 */                             out.write(" \n            </a></td>\n          <td width=\"51%\" align=\"left\" class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4404 */                             out.print(resID);
/* 4405 */                             out.write("&attributeIDs=");
/* 4406 */                             out.print(availabilitykeys.get(resourcetype));
/* 4407 */                             out.write(44);
/* 4408 */                             out.print(healthkeys.get(resourcetype));
/* 4409 */                             out.write("&attributeToSelect=");
/* 4410 */                             out.print(availabilitykeys.get(resourcetype));
/* 4411 */                             out.write("&redirectto=");
/* 4412 */                             out.print(encodeurl);
/* 4413 */                             out.write("\" class=\"links\">");
/* 4414 */                             out.print(ALERTCONFIG_TEXT);
/* 4415 */                             out.write("</a> \n          </td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 4416 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 4417 */                             out.write("</td></tr></table>\n<br>\n\n");
/*      */                             
/* 4419 */                             long curvalue = -1L;
/* 4420 */                             String temp = "-1";
/* 4421 */                             if (request.getAttribute("responsetime") != null)
/*      */                             {
/* 4423 */                               temp = (String)request.getAttribute("responsetime");
/*      */                             }
/* 4425 */                             String responsetimeid = "4303";
/* 4426 */                             String tipCurValue = FormatUtil.getString("am.webclient.qenginescript.tooltip");
/* 4427 */                             wlsGraph.setParam(resID, "QRESPONSETIME");
/* 4428 */                             HashMap hm_enable = (HashMap)request.getAttribute("hm_reports");
/* 4429 */                             ArrayList al = (ArrayList)request.getAttribute("enable");
/*      */                             
/* 4431 */                             out.write("\n\n<table width=\"100%\">\n<tr>\n<td>\n\n<div id=\"reports\" style=\"DISPLAY: none\">\n<form name=\"form2\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n<tr> \n<td width=\"72%\" height=\"31\" class=\"tableheading\" >&nbsp; ");
/* 4432 */                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 4433 */                             out.write("</td>\n</tr>\n</table>\n<table align=\"left\" width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td>\n<table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n<tr>\n<td width=\"20%\" height=\"28\" valign=\"center\"  class=\"columnheading\">\n<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAll(this)\"></td>\n<td width=\"30%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 4434 */                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4436 */                             out.write("</span></td>\n<td width=\"50%\" height=\"28%\" valign=\"center\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 4437 */                             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4439 */                             out.write("</span></td>\n</tr>\n\n");
/*      */                             
/* 4441 */                             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4442 */                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 4443 */                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4445 */                             _jspx_th_logic_005fiterate_005f0.setName("enable");
/*      */                             
/* 4447 */                             _jspx_th_logic_005fiterate_005f0.setId("attribute");
/*      */                             
/* 4449 */                             _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                             
/* 4451 */                             _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 4452 */                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 4453 */                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 4454 */                               String attribute = null;
/* 4455 */                               Integer j = null;
/* 4456 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4457 */                                 out = _jspx_page_context.pushBody();
/* 4458 */                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 4459 */                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                               }
/* 4461 */                               attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4462 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                               for (;;) {
/* 4464 */                                 out.write("\n<tr>\n\n");
/*      */                                 
/* 4466 */                                 String bgclass = "whitegrayborder";
/* 4467 */                                 if (j.intValue() % 2 != 0)
/*      */                                 {
/* 4469 */                                   bgclass = "yellowgrayborder";
/*      */                                 }
/*      */                                 
/* 4472 */                                 out.write("\n\n<td width=\"20%\" height=\"22\"  class=\"");
/* 4473 */                                 out.print(bgclass);
/* 4474 */                                 out.write("\">\n<input type=\"checkbox\" name=\"checkbox\" value=\"");
/* 4475 */                                 out.print(displayname_attributeid.get(attribute));
/* 4476 */                                 out.write("\"/></td>\n<td width=\"30%\"  class=\"");
/* 4477 */                                 out.print(bgclass);
/* 4478 */                                 out.write("\" ><span class=\"bodytext\">");
/* 4479 */                                 out.print(attribute);
/* 4480 */                                 out.write("</span></td>\n");
/*      */                                 
/* 4482 */                                 if ((hm_enable.get(attribute) != null) && (hm_enable.get(attribute).equals("enabled")))
/*      */                                 {
/*      */ 
/* 4485 */                                   out.write("\n<td width=\"50%\" class=\"");
/* 4486 */                                   out.print(bgclass);
/* 4487 */                                   out.write("\"><img alt=\"Reports Enabled\" title=\"");
/* 4488 */                                   out.print(FormatUtil.getString("am.webclient.script.reportsenabled"));
/* 4489 */                                   out.write("\" src=\"../images/cam_report_enabled.gif\" border=\"0\"></td>\n");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 4495 */                                   out.write("\n<td width=\"50%\" class=\"");
/* 4496 */                                   out.print(bgclass);
/* 4497 */                                   out.write("\" ><img alt=\"Reports Disabled\" title=\"");
/* 4498 */                                   out.print(FormatUtil.getString("am.webclient.script.reportsdisabled"));
/* 4499 */                                   out.write("\" src=\"../images/cam_report_disabled.gif\" border=\"0\"></td>\n\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4503 */                                 out.write("\n</tr>\n");
/* 4504 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 4505 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4506 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 4507 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4510 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4511 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4514 */                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 4515 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                             }
/*      */                             
/* 4518 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 4519 */                             out.write("\n\n</table>\n</td>\n</tr>\n<tr>\n<td>\n<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"tablebottom\" >\n<tr>\n<td width=\"5%\" colspan=2 heigth=\"26\" ><a href=\"javascript:enableSelections(1);\" class=\"links\">");
/* 4520 */                             out.print(FormatUtil.getString("am.webclient.script.enablereports"));
/* 4521 */                             out.write("</a> </td>\n<td width=\"5%\" colspan=2 heigth=\"26\" ><a href=\"javascript:enableSelections(2);\" class=\"links\">");
/* 4522 */                             out.print(FormatUtil.getString("am.webclient.script.disablereports"));
/* 4523 */                             out.write("</a> </td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</form>\n<br>\n</div>\n</td>\n</tr>\n<br>\n\n<tr>\n<td>\n\n\n");
/* 4524 */                             if (_jspx_meth_c_005fif_005f27(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4526 */                             out.write("\n\n<br>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr> \n    <td height=\"26\" class=\"tableheading\">");
/* 4527 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 4528 */                             out.write(45);
/* 4529 */                             out.write(32);
/* 4530 */                             out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4531 */                             out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr> \n    <td width=\"405\" height=\"127\" valign=\"top\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n        <tr> \n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4532 */                             out.print(resID);
/* 4533 */                             out.write("&attributeid=");
/* 4534 */                             out.print(responsetimeid);
/* 4535 */                             out.write("&period=-7',740,550)\"> \n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4536 */                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4537 */                             out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4538 */                             out.print(resID);
/* 4539 */                             out.write("&attributeid=");
/* 4540 */                             out.print(responsetimeid);
/* 4541 */                             out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4542 */                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4543 */                             out.write("\"></a></td>\n        </tr>\n        <tr> \n          <td colspan=\"2\"> ");
/*      */                             
/* 4545 */                             TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4546 */                             _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 4547 */                             _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4549 */                             _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wlsGraph");
/*      */                             
/* 4551 */                             _jspx_th_awolf_005ftimechart_005f0.setWidth("320");
/*      */                             
/* 4553 */                             _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                             
/* 4555 */                             _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                             
/* 4557 */                             _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                             
/* 4559 */                             _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.responsetimeinms"));
/*      */                             
/* 4561 */                             _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 4562 */                             int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 4563 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 4564 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4565 */                                 out = _jspx_page_context.pushBody();
/* 4566 */                                 _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 4567 */                                 _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4570 */                                 out.write(" \n            ");
/* 4571 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 4572 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4575 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4576 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4579 */                             if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 4580 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                             }
/*      */                             
/* 4583 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 4584 */                             out.write(" </td>\n        </tr>\n      </table></td>\n    <td width=\"562\" valign=\"top\"> <br> <br> \n\n      <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr> \n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4585 */                             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4587 */                             out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4588 */                             if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4590 */                             out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">\n          ");
/*      */                             
/* 4592 */                             if (!temp.equals("-1"))
/*      */                             {
/* 4594 */                               out.write("\n          ");
/* 4595 */                               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4597 */                               out.write("</span>\n          ");
/*      */                             } else {
/* 4599 */                               out.println("&nbsp;");
/*      */                             }
/* 4601 */                             out.write("\n          </td>\n        </tr>\n        <tr> \n\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 4602 */                             out.print(tipCurValue);
/* 4603 */                             out.write(34);
/* 4604 */                             out.write(62);
/* 4605 */                             out.print(FormatUtil.getString("am.webclient.websphere.currentresponse"));
/* 4606 */                             out.write("</td>\n          ");
/*      */                             
/* 4608 */                             if (!temp.equals("-1"))
/*      */                             {
/*      */ 
/* 4611 */                               out.write("\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 4612 */                               out.print(temp);
/* 4613 */                               out.write(32);
/* 4614 */                               out.print(FormatUtil.getString("ms"));
/* 4615 */                               out.write("</td>\n             \n            ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 4621 */                               out.write("\n          <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 4622 */                               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4623 */                               out.write(" </td>\n            ");
/*      */                             }
/*      */                             
/* 4626 */                             out.write(10);
/* 4627 */                             out.write(9);
/* 4628 */                             out.write(32);
/*      */                             
/* 4630 */                             if (!temp.equals("-1"))
/*      */                             {
/*      */ 
/* 4633 */                               out.write("\n          <Td width=\"16%\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4634 */                               out.print(resID);
/* 4635 */                               out.write("&attributeid=");
/* 4636 */                               out.print(responsetimeid);
/* 4637 */                               out.write("&alertconfigurl=");
/* 4638 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + responsetimeid + "attributeToSelect=" + responsetimeid + "&redirectto=" + encodeurl));
/* 4639 */                               out.write("')\">");
/* 4640 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + responsetimeid)));
/* 4641 */                               out.write(" </a>\n          </td>\n          ");
/*      */                             }
/*      */                             
/*      */ 
/* 4645 */                             out.write("\n        </tr>\n        <tr> \n          <td  colspan=\"4\" height=\"35\" class=\"yellowgrayborder\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4646 */                             out.print(resID);
/* 4647 */                             out.write("&attributeIDs=");
/* 4648 */                             out.print(responsetimeid);
/* 4649 */                             out.write("&attributeToSelect=");
/* 4650 */                             out.print(responsetimeid);
/* 4651 */                             out.write("&redirectto=");
/* 4652 */                             out.print(encodeurl);
/* 4653 */                             out.write("\" class=\"staticlinks\">");
/* 4654 */                             out.print(ALERTCONFIG_TEXT);
/* 4655 */                             out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>    \n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr> \n    <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n  </tr>\n</table>\n\n</td>\n</tr>\n</table>\n\n\n<br>\n\n\n<br>\n\n\n");
/*      */                             
/* 4657 */                             int localattributes = 0;
/* 4658 */                             int widthOfTopMostTable = num_data == 1 ? 50 : 99;
/* 4659 */                             int secondLevelTableWidth = num_data == 1 ? 99 : 49;
/*      */                             
/* 4661 */                             out.write("\n\n<br>\n\n\n");
/*      */                             
/* 4663 */                             if (str_data > 0)
/*      */                             {
/* 4665 */                               out.write("\n\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=lrtbdarkborder><tr><td>\n<table WIDTH=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n<td>\n\n        <tr>\n        <td>\n        \n<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" >\n\n\n");
/*      */                               
/* 4667 */                               ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.get(ForEachTag.class);
/* 4668 */                               _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 4669 */                               _jspx_th_c_005fforEach_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 4671 */                               _jspx_th_c_005fforEach_005f2.setBegin("0");
/*      */                               
/* 4673 */                               _jspx_th_c_005fforEach_005f2.setEnd("${urlsize-1}");
/*      */                               
/* 4675 */                               _jspx_th_c_005fforEach_005f2.setVarStatus("status");
/* 4676 */                               int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */                               try {
/* 4678 */                                 int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 4679 */                                 if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */                                   for (;;) {
/* 4681 */                                     out.write(10);
/* 4682 */                                     if (_jspx_meth_c_005fif_005f28(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                       _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                     }
/* 4684 */                                     out.write(10);
/* 4685 */                                     if (_jspx_meth_c_005fif_005f29(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                       _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                     }
/* 4687 */                                     out.write(10);
/* 4688 */                                     out.write(32);
/* 4689 */                                     out.write(32);
/* 4690 */                                     if (_jspx_meth_c_005fif_005f30(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                       _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                     }
/* 4692 */                                     out.write("\n  <TD class=\"bodytext\">");
/* 4693 */                                     if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                       _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                     }
/* 4695 */                                     out.write("</TD>\n  ");
/*      */                                     
/* 4697 */                                     ForEachTag _jspx_th_c_005fforEach_005f3 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.get(ForEachTag.class);
/* 4698 */                                     _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
/* 4699 */                                     _jspx_th_c_005fforEach_005f3.setParent(_jspx_th_c_005fforEach_005f2);
/*      */                                     
/* 4701 */                                     _jspx_th_c_005fforEach_005f3.setBegin("${(status.count -1) * 4}");
/*      */                                     
/* 4703 */                                     _jspx_th_c_005fforEach_005f3.setEnd("${(status.count-1) * 4+3}");
/*      */                                     
/* 4705 */                                     _jspx_th_c_005fforEach_005f3.setVarStatus("colcount");
/* 4706 */                                     int[] _jspx_push_body_count_c_005fforEach_005f3 = { 0 };
/*      */                                     try {
/* 4708 */                                       int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
/* 4709 */                                       if (_jspx_eval_c_005fforEach_005f3 != 0) {
/*      */                                         for (;;) {
/* 4711 */                                           out.write("\n \n   ");
/*      */                                           
/* 4713 */                                           IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4714 */                                           _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 4715 */                                           _jspx_th_c_005fif_005f31.setParent(_jspx_th_c_005fforEach_005f3);
/*      */                                           
/* 4717 */                                           _jspx_th_c_005fif_005f31.setTest("${colcount.count==1}");
/* 4718 */                                           int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 4719 */                                           if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */                                             for (;;) {
/* 4721 */                                               out.write("\n     <td class=\"bodytext\" aling=\"left\">\n   \n   ");
/*      */                                               
/* 4723 */                                               OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 4724 */                                               _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 4725 */                                               _jspx_th_c_005fout_005f43.setParent(_jspx_th_c_005fif_005f31);
/*      */                                               
/* 4727 */                                               _jspx_th_c_005fout_005f43.setValue("${string_data_id_value[urlids[(status.count -1)]]}");
/* 4728 */                                               int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 4729 */                                               if (_jspx_eval_c_005fout_005f43 != 0) {
/* 4730 */                                                 if (_jspx_eval_c_005fout_005f43 != 1) {
/* 4731 */                                                   out = _jspx_page_context.pushBody();
/* 4732 */                                                   _jspx_push_body_count_c_005fforEach_005f3[0] += 1;
/* 4733 */                                                   _jspx_th_c_005fout_005f43.setBodyContent((BodyContent)out);
/* 4734 */                                                   _jspx_th_c_005fout_005f43.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4737 */                                                   out.print(FormatUtil.getString("am.webclient.qengine.novaluemessage.text"));
/* 4738 */                                                   int evalDoAfterBody = _jspx_th_c_005fout_005f43.doAfterBody();
/* 4739 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4742 */                                                 if (_jspx_eval_c_005fout_005f43 != 1) {
/* 4743 */                                                   out = _jspx_page_context.popBody();
/* 4744 */                                                   _jspx_push_body_count_c_005fforEach_005f3[0] -= 1;
/*      */                                                 }
/*      */                                               }
/* 4747 */                                               if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 4748 */                                                 this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f43);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4786 */                                                 _jspx_th_c_005fforEach_005f3.doFinally();
/* 4787 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f3);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                                 _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                               }
/* 4751 */                                               this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f43);
/* 4752 */                                               out.write("\n     </td>\n   ");
/* 4753 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 4754 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4758 */                                           if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 4759 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4786 */                                             _jspx_th_c_005fforEach_005f3.doFinally();
/* 4787 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f3);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                             _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                           }
/* 4762 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 4763 */                                           out.write("\n       ");
/* 4764 */                                           if (_jspx_meth_c_005fif_005f32(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
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
/* 4786 */                                             _jspx_th_c_005fforEach_005f3.doFinally();
/* 4787 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f3);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                             _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                           }
/* 4766 */                                           out.write("\n         ");
/* 4767 */                                           if (_jspx_meth_c_005fif_005f33(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
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
/* 4786 */                                             _jspx_th_c_005fforEach_005f3.doFinally();
/* 4787 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f3);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                             _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                           }
/* 4769 */                                           out.write("\n     ");
/* 4770 */                                           if (_jspx_meth_c_005fif_005f34(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
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
/* 4786 */                                             _jspx_th_c_005fforEach_005f3.doFinally();
/* 4787 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f3);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                             _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                           }
/* 4772 */                                           out.write("\n   \n \n  ");
/* 4773 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
/* 4774 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4778 */                                       if (_jspx_th_c_005fforEach_005f3.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4786 */                                         _jspx_th_c_005fforEach_005f3.doFinally();
/* 4787 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f3);
/*      */                                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                         _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 4782 */                                         int tmp16958_16957 = 0; int[] tmp16958_16955 = _jspx_push_body_count_c_005fforEach_005f3; int tmp16960_16959 = tmp16958_16955[tmp16958_16957];tmp16958_16955[tmp16958_16957] = (tmp16960_16959 - 1); if (tmp16960_16959 <= 0) break;
/* 4783 */                                         out = _jspx_page_context.popBody(); }
/* 4784 */                                       _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
/*      */                                     }
/*      */                                     finally {}
/*      */                                     
/*      */ 
/* 4789 */                                     out.write("\n   </tr>\n");
/* 4790 */                                     int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 4791 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4795 */                                 if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4803 */                                   _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                 }
/*      */                               }
/*      */                               catch (Throwable _jspx_exception)
/*      */                               {
/*      */                                 for (;;)
/*      */                                 {
/* 4799 */                                   int tmp17101_17100 = 0; int[] tmp17101_17098 = _jspx_push_body_count_c_005fforEach_005f2; int tmp17103_17102 = tmp17101_17098[tmp17101_17100];tmp17101_17098[tmp17101_17100] = (tmp17103_17102 - 1); if (tmp17103_17102 <= 0) break;
/* 4800 */                                   out = _jspx_page_context.popBody(); }
/* 4801 */                                 _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */                               } finally {
/* 4803 */                                 _jspx_th_c_005fforEach_005f2.doFinally();
/* 4804 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f2);
/*      */                               }
/* 4806 */                               out.write("\n\n</table>\n</td>\n</tr>\n</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n");
/*      */                             }
/* 4808 */                             out.write("\n\n<br>\n\n");
/*      */                             
/* 4810 */                             if (num_data > 0)
/*      */                             {
/* 4812 */                               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tr><td>\n<table WIDTH=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n");
/*      */                               
/* 4814 */                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4815 */                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 4816 */                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 4818 */                               _jspx_th_logic_005fiterate_005f1.setName("numeric");
/*      */                               
/* 4820 */                               _jspx_th_logic_005fiterate_005f1.setId("attribute");
/*      */                               
/* 4822 */                               _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/*      */                               
/* 4824 */                               _jspx_th_logic_005fiterate_005f1.setType("java.lang.String");
/* 4825 */                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 4826 */                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 4827 */                                 String attribute = null;
/* 4828 */                                 Integer i = null;
/* 4829 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4830 */                                   out = _jspx_page_context.pushBody();
/* 4831 */                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 4832 */                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                 }
/* 4834 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4835 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                 for (;;) {
/* 4837 */                                   out.write(10);
/* 4838 */                                   out.write(10);
/*      */                                   
/*      */ 
/* 4841 */                                   if (!attribute.endsWith("response_code"))
/*      */                                   {
/*      */ 
/*      */ 
/* 4845 */                                     boolean isLeftBox = localattributes % 2 == 0;
/* 4846 */                                     boolean isLastBox = localattributes == num_data - 1;
/* 4847 */                                     localattributes++;
/* 4848 */                                     int attributeid = Integer.parseInt(String.valueOf(displayname_attributeid.get(attribute)));
/* 4849 */                                     camGraph.setParam(attributeid, "Script Monitor", attribute);
/* 4850 */                                     String value = FormatUtil.getString("am.webclient.nodata.text");
/* 4851 */                                     if ((ht_numeric.get(attribute) != null) && (!String.valueOf(ht_numeric.get(attribute)).equalsIgnoreCase("null")))
/*      */                                     {
/* 4853 */                                       value = String.valueOf(ht_numeric.get(attribute));
/*      */                                     }
/*      */                                     
/*      */ 
/* 4857 */                                     out.write("\n\n<td  width=\"");
/* 4858 */                                     out.print(secondLevelTableWidth);
/* 4859 */                                     out.write("%\">\n  \t<table WIDTH=\"96%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"grayfullborder\">\n          <tr> \n            <td height=\"26\" class=\"tableheading\">");
/* 4860 */                                     out.print(attribute);
/* 4861 */                                     out.write(32);
/* 4862 */                                     out.write(45);
/* 4863 */                                     out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4864 */                                     out.write("</td>   \n          </tr>\n          <tr>\n <td width=\"300\" height=\"127\" valign=\"top\" colspan=2> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"50%\">\n ");
/*      */                                     
/* 4866 */                                     if ((hm_enable.get(attribute) != null) && (hm_enable.get(attribute).equals("enabled")))
/*      */                                     {
/*      */ 
/* 4869 */                                       out.write("\n        <tr> \n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4870 */                                       out.print(resID);
/* 4871 */                                       out.write("&attributeid=");
/* 4872 */                                       out.print(attributeid);
/* 4873 */                                       out.write("&period=-7',740,550)\"> \n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4874 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4875 */                                       out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4876 */                                       out.print(resID);
/* 4877 */                                       out.write("&attributeid=");
/* 4878 */                                       out.print(attributeid);
/* 4879 */                                       out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4880 */                                       out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4881 */                                       out.write("\"></a></td>\n        </tr>\n");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 4887 */                                       out.write("\n        <tr> \n          <td width=\"90%\" height=\"35\" align=\"right\">&nbsp;&nbsp;&nbsp;</td>\n          <td width=\"10%\" height=\"35\">&nbsp;&nbsp;&nbsp;</td>\n        </tr>\n    ");
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/* 4892 */                                     out.write("\n        <tr>\n          <td colspan=\"2\"> ");
/*      */                                     
/* 4894 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4895 */                                     _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 4896 */                                     _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                     
/* 4898 */                                     _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("camGraph");
/*      */                                     
/* 4900 */                                     _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                                     
/* 4902 */                                     _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                                     
/* 4904 */                                     _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                                     
/* 4906 */                                     _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 4908 */                                     _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(attribute);
/*      */                                     
/* 4910 */                                     _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 4911 */                                     int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 4912 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 4913 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 4914 */                                         out = _jspx_page_context.pushBody();
/* 4915 */                                         _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 4916 */                                         _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4919 */                                         out.write("\n            ");
/* 4920 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 4921 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4924 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 4925 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4928 */                                     if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 4929 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                     }
/*      */                                     
/* 4932 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 4933 */                                     out.write(" </td>\n        </tr>\n      </table></td>\n</tr>\n\n          \n          <tr>\n          <td  align=left colspan=2 valign=\"top\">\n      <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4934 */                                     if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                       return;
/* 4936 */                                     out.write("</span></td>\n          <td class=\"columnheadingnotop\" align=\"center\"><span class=\"bodytextbold\">");
/* 4937 */                                     if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                       return;
/* 4939 */                                     out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\" align=\"center\"><span class=\"bodytextbold\">");
/*      */                                     
/* 4941 */                                     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4942 */                                     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 4943 */                                     _jspx_th_fmt_005fmessage_005f16.setParent(_jspx_th_logic_005fiterate_005f1);
/* 4944 */                                     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 4945 */                                     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 4946 */                                       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 4947 */                                         out = _jspx_page_context.pushBody();
/* 4948 */                                         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 4949 */                                         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4952 */                                         out.print(ALERTCONFIG_TEXT);
/* 4953 */                                         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 4954 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4957 */                                       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 4958 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4961 */                                     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 4962 */                                       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16); return;
/*      */                                     }
/*      */                                     
/* 4965 */                                     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 4966 */                                     out.write("</span></td>\n        </tr>\n\n        <tr>\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 4967 */                                     out.print(value);
/* 4968 */                                     out.write("</td>\n          <td width=\"16%\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4969 */                                     out.print(resID);
/* 4970 */                                     out.write("&attributeid=");
/* 4971 */                                     out.print(attributeid);
/* 4972 */                                     out.write("&alertconfigurl=");
/* 4973 */                                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + attributeid + "attributeToSelect=" + attributeid + "&redirectto=" + encodeurl));
/* 4974 */                                     out.write("')\">");
/* 4975 */                                     out.print(getSeverityImage(alert.getProperty(resID + "#" + attributeid)));
/* 4976 */                                     out.write(" </a></td>\n          <td  colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"center\" ><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4977 */                                     out.print(resID);
/* 4978 */                                     out.write("&attributeIDs=");
/* 4979 */                                     out.print(attributeid);
/* 4980 */                                     out.write("&attributeToSelect=");
/* 4981 */                                     out.print(attributeid);
/* 4982 */                                     out.write("&redirectto=");
/* 4983 */                                     out.print(encodeurl);
/* 4984 */                                     out.write("\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" border=\"0\" style=\"width: 13px; height: 13px;\" title=\"\"></a>&nbsp;</td>\n        </tr>\n      </table>\n          </td>\n          </tr>\n        </table>\n        \n       </td>\n\n");
/*      */                                     
/* 4986 */                                     if ((isLastBox) && (isLeftBox)) {
/* 4987 */                                       out.println("<td>&nbsp;</td></tr>");
/*      */                                     }
/*      */                                     
/* 4990 */                                     if (!isLeftBox) {
/* 4991 */                                       out.println("<tr><td>&nbsp;</td></tr>");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 4995 */                                   out.write("\t\n\n\n");
/* 4996 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 4997 */                                   attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4998 */                                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 4999 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 5002 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5003 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 5006 */                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 5007 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                               }
/*      */                               
/* 5010 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 5011 */                               out.write("\n</table>\n</td>\n</tr>\n</table>\n");
/*      */                             }
/* 5013 */                             out.write("\n\n\n\n");
/* 5014 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 5015 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5018 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 5019 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5022 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5023 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 5026 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 5027 */                         out.write(10);
/* 5028 */                         if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 5030 */                         out.write(32);
/* 5031 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5032 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5036 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5037 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 5040 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5041 */                       out.write(32);
/* 5042 */                       out.write(10);
/*      */                     }
/* 5044 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5045 */         out = _jspx_out;
/* 5046 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5047 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 5048 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5051 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5057 */     PageContext pageContext = _jspx_page_context;
/* 5058 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5060 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5061 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5062 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 5064 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 5065 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5066 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5067 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5068 */       return true;
/*      */     }
/* 5070 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5071 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5076 */     PageContext pageContext = _jspx_page_context;
/* 5077 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5079 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5080 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5081 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 5083 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 5084 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5085 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5086 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5087 */       return true;
/*      */     }
/* 5089 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5095 */     PageContext pageContext = _jspx_page_context;
/* 5096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5098 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5099 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5100 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5102 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 5103 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5104 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5106 */         out.write(10);
/* 5107 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 5108 */           return true;
/* 5109 */         out.write(10);
/* 5110 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5111 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5115 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5116 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5117 */       return true;
/*      */     }
/* 5119 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5125 */     PageContext pageContext = _jspx_page_context;
/* 5126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5128 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5129 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5130 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5132 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5134 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 5135 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5136 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5137 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5138 */       return true;
/*      */     }
/* 5140 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5146 */     PageContext pageContext = _jspx_page_context;
/* 5147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5149 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5150 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5151 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5153 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 5154 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5155 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5157 */         out.write(10);
/* 5158 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 5159 */           return true;
/* 5160 */         out.write(10);
/* 5161 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5162 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5166 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5167 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5168 */       return true;
/*      */     }
/* 5170 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5176 */     PageContext pageContext = _jspx_page_context;
/* 5177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5179 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5180 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 5181 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5183 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 5185 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 5186 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 5187 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5188 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5189 */       return true;
/*      */     }
/* 5191 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5197 */     PageContext pageContext = _jspx_page_context;
/* 5198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5200 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5201 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5202 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5204 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5205 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5206 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5207 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5208 */       return true;
/*      */     }
/* 5210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5216 */     PageContext pageContext = _jspx_page_context;
/* 5217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5219 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5220 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5221 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5223 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 5224 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5225 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5226 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5227 */       return true;
/*      */     }
/* 5229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5230 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5235 */     PageContext pageContext = _jspx_page_context;
/* 5236 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5238 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5239 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5240 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5242 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 5243 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5244 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5245 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5246 */       return true;
/*      */     }
/* 5248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5249 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5254 */     PageContext pageContext = _jspx_page_context;
/* 5255 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5257 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5258 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5259 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5261 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 5262 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5263 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5264 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5265 */       return true;
/*      */     }
/* 5267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5273 */     PageContext pageContext = _jspx_page_context;
/* 5274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5276 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5277 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5278 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5280 */     _jspx_th_c_005fif_005f2.setTest("${!empty param.parentid}");
/* 5281 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5282 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5284 */         out.write("\n      ");
/* 5285 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5286 */           return true;
/* 5287 */         out.write("\n      ");
/* 5288 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5289 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5293 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5294 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5295 */       return true;
/*      */     }
/* 5297 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5303 */     PageContext pageContext = _jspx_page_context;
/* 5304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5306 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5307 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5308 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5310 */     _jspx_th_c_005fset_005f0.setVar("parentids");
/* 5311 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5312 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5313 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5314 */         out = _jspx_page_context.pushBody();
/* 5315 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5316 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5319 */         out.write("\n      &parentname=");
/* 5320 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5321 */           return true;
/* 5322 */         out.write("&parentid=");
/* 5323 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5324 */           return true;
/* 5325 */         out.write("\n      ");
/* 5326 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5327 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5330 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5331 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5334 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5335 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5336 */       return true;
/*      */     }
/* 5338 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5344 */     PageContext pageContext = _jspx_page_context;
/* 5345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5347 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5348 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5349 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5351 */     _jspx_th_c_005fout_005f6.setValue("${param.parentname}");
/* 5352 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5353 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5354 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5355 */       return true;
/*      */     }
/* 5357 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5363 */     PageContext pageContext = _jspx_page_context;
/* 5364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5366 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5367 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5368 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5370 */     _jspx_th_c_005fout_005f7.setValue("${param.parentid}");
/* 5371 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5372 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5373 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5374 */       return true;
/*      */     }
/* 5376 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5382 */     PageContext pageContext = _jspx_page_context;
/* 5383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5385 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5386 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5387 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5389 */     _jspx_th_c_005fif_005f3.setTest("${empty param.parentid}");
/* 5390 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5391 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 5393 */         out.write("\n            ");
/* 5394 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5395 */           return true;
/* 5396 */         out.write("\n      ");
/* 5397 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5398 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5402 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5403 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5404 */       return true;
/*      */     }
/* 5406 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5412 */     PageContext pageContext = _jspx_page_context;
/* 5413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5415 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 5416 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5417 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5419 */     _jspx_th_c_005fset_005f1.setVar("parentids");
/*      */     
/* 5421 */     _jspx_th_c_005fset_005f1.setValue("");
/* 5422 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5423 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5424 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 5425 */       return true;
/*      */     }
/* 5427 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 5428 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5433 */     PageContext pageContext = _jspx_page_context;
/* 5434 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5436 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5437 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5438 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5440 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.haid}");
/* 5441 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5442 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 5444 */         out.write(10);
/* 5445 */         out.write(9);
/* 5446 */         out.write(9);
/* 5447 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 5448 */           return true;
/* 5449 */         out.write(10);
/* 5450 */         out.write(9);
/* 5451 */         out.write(9);
/* 5452 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5453 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5457 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5458 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5459 */       return true;
/*      */     }
/* 5461 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5462 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5467 */     PageContext pageContext = _jspx_page_context;
/* 5468 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5470 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5471 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5472 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5474 */     _jspx_th_c_005fset_005f2.setVar("haid");
/* 5475 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5476 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5477 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5478 */         out = _jspx_page_context.pushBody();
/* 5479 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5480 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5483 */         out.write("\n\t\t&haid=");
/* 5484 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5485 */           return true;
/* 5486 */         out.write(10);
/* 5487 */         out.write(9);
/* 5488 */         out.write(9);
/* 5489 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5490 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5493 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5494 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5497 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5498 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5499 */       return true;
/*      */     }
/* 5501 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5502 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5507 */     PageContext pageContext = _jspx_page_context;
/* 5508 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5510 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5511 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5512 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5514 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 5515 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5516 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5517 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5518 */       return true;
/*      */     }
/* 5520 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5526 */     PageContext pageContext = _jspx_page_context;
/* 5527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5529 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5530 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5531 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5533 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 5534 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5535 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5536 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5537 */       return true;
/*      */     }
/* 5539 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5540 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5545 */     PageContext pageContext = _jspx_page_context;
/* 5546 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5548 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5549 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5550 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5552 */     _jspx_th_c_005fif_005f7.setTest("${param.method=='editScript'}");
/* 5553 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5554 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5556 */         out.write("\n    </a>\n    ");
/* 5557 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5558 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5562 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5563 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5564 */       return true;
/*      */     }
/* 5566 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5567 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5572 */     PageContext pageContext = _jspx_page_context;
/* 5573 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5575 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5576 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5577 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 5579 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 5580 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5581 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5582 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5583 */       return true;
/*      */     }
/* 5585 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5586 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5591 */     PageContext pageContext = _jspx_page_context;
/* 5592 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5594 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5595 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5596 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5598 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 5599 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5600 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5601 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5602 */       return true;
/*      */     }
/* 5604 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5610 */     PageContext pageContext = _jspx_page_context;
/* 5611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5613 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5614 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5615 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 5617 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 5618 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5619 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5620 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5621 */       return true;
/*      */     }
/* 5623 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5624 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5629 */     PageContext pageContext = _jspx_page_context;
/* 5630 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5632 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5633 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5634 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5636 */     _jspx_th_c_005fout_005f13.setValue("${param.haid}");
/* 5637 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5638 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5639 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5640 */       return true;
/*      */     }
/* 5642 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5643 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5648 */     PageContext pageContext = _jspx_page_context;
/* 5649 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5651 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5652 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5653 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5655 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 5656 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5657 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5658 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5659 */       return true;
/*      */     }
/* 5661 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5662 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5667 */     PageContext pageContext = _jspx_page_context;
/* 5668 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5670 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5671 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5672 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5674 */     _jspx_th_c_005fout_005f15.setValue("${parentids}");
/* 5675 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5676 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5677 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5678 */       return true;
/*      */     }
/* 5680 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5686 */     PageContext pageContext = _jspx_page_context;
/* 5687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5689 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5690 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5691 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5693 */     _jspx_th_c_005fout_005f16.setValue("${param.haid}");
/* 5694 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5695 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5696 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5697 */       return true;
/*      */     }
/* 5699 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5705 */     PageContext pageContext = _jspx_page_context;
/* 5706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5708 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5709 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5710 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5712 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 5713 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5714 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5715 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5716 */       return true;
/*      */     }
/* 5718 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5719 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5724 */     PageContext pageContext = _jspx_page_context;
/* 5725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5727 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5728 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5729 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5731 */     _jspx_th_c_005fout_005f18.setValue("${parentids}");
/* 5732 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5733 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5734 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5735 */       return true;
/*      */     }
/* 5737 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5743 */     PageContext pageContext = _jspx_page_context;
/* 5744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5746 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5747 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5748 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5750 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 5751 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5752 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5753 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5754 */       return true;
/*      */     }
/* 5756 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5762 */     PageContext pageContext = _jspx_page_context;
/* 5763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5765 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5766 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5767 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5769 */     _jspx_th_c_005fout_005f20.setValue("${parentids}");
/* 5770 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5771 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5772 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5773 */       return true;
/*      */     }
/* 5775 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5781 */     PageContext pageContext = _jspx_page_context;
/* 5782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5784 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5785 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5786 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5788 */     _jspx_th_c_005fout_005f21.setValue("${param.haid}");
/* 5789 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5790 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5791 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5792 */       return true;
/*      */     }
/* 5794 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5800 */     PageContext pageContext = _jspx_page_context;
/* 5801 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5803 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5804 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5805 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5807 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 5808 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5809 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5810 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5811 */       return true;
/*      */     }
/* 5813 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5819 */     PageContext pageContext = _jspx_page_context;
/* 5820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5822 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5823 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5824 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5826 */     _jspx_th_c_005fout_005f23.setValue("${parentids}");
/* 5827 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5828 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5829 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5830 */       return true;
/*      */     }
/* 5832 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5838 */     PageContext pageContext = _jspx_page_context;
/* 5839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5841 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5842 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5843 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5845 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 5846 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5847 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5848 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5849 */       return true;
/*      */     }
/* 5851 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5857 */     PageContext pageContext = _jspx_page_context;
/* 5858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5860 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5861 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5862 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5864 */     _jspx_th_c_005fout_005f25.setValue("${parentids}");
/* 5865 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5866 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5867 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5868 */       return true;
/*      */     }
/* 5870 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5876 */     PageContext pageContext = _jspx_page_context;
/* 5877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5879 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5880 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 5881 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 5883 */     _jspx_th_c_005fif_005f18.setTest("${param.actionmethod!='editScript'}");
/* 5884 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 5885 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 5887 */         out.write("\n    </a>\n    ");
/* 5888 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 5889 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5893 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 5894 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 5895 */       return true;
/*      */     }
/* 5897 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 5898 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5903 */     PageContext pageContext = _jspx_page_context;
/* 5904 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5906 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5907 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5908 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5910 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 5911 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5912 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5913 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5914 */       return true;
/*      */     }
/* 5916 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5917 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5922 */     PageContext pageContext = _jspx_page_context;
/* 5923 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5925 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5926 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5927 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5929 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 5930 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5931 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5932 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5933 */       return true;
/*      */     }
/* 5935 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5936 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5941 */     PageContext pageContext = _jspx_page_context;
/* 5942 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5944 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5945 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5946 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5948 */     _jspx_th_c_005fout_005f28.setValue("${param.resourceid}");
/* 5949 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5950 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5951 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5952 */       return true;
/*      */     }
/* 5954 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5955 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5960 */     PageContext pageContext = _jspx_page_context;
/* 5961 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5963 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5964 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5965 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5967 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 5968 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5969 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5970 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5971 */       return true;
/*      */     }
/* 5973 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5974 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5979 */     PageContext pageContext = _jspx_page_context;
/* 5980 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5982 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5983 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 5984 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5986 */     _jspx_th_c_005fout_005f30.setValue("${ha.key}");
/* 5987 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 5988 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 5989 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5990 */       return true;
/*      */     }
/* 5992 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5993 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5998 */     PageContext pageContext = _jspx_page_context;
/* 5999 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6001 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6002 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 6003 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6005 */     _jspx_th_c_005fout_005f31.setValue("${ha.value}");
/* 6006 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 6007 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 6008 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6009 */       return true;
/*      */     }
/* 6011 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6012 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6017 */     PageContext pageContext = _jspx_page_context;
/* 6018 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6020 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6021 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 6022 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6024 */     _jspx_th_c_005fset_005f3.setVar("monitorName");
/* 6025 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 6026 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 6027 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 6028 */         out = _jspx_page_context.pushBody();
/* 6029 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 6030 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 6031 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6034 */         if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fset_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6035 */           return true;
/* 6036 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 6037 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6040 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 6041 */         out = _jspx_page_context.popBody();
/* 6042 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 6045 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 6046 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 6047 */       return true;
/*      */     }
/* 6049 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 6050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6055 */     PageContext pageContext = _jspx_page_context;
/* 6056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6058 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6059 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 6060 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 6062 */     _jspx_th_c_005fout_005f32.setValue("${ha.value}");
/* 6063 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 6064 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 6065 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6066 */       return true;
/*      */     }
/* 6068 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_logic_005fpresent_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6074 */     PageContext pageContext = _jspx_page_context;
/* 6075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6077 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6078 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 6079 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_logic_005fpresent_005f10);
/*      */     
/* 6081 */     _jspx_th_c_005fout_005f33.setValue("${ha.key}");
/* 6082 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 6083 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 6084 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6085 */       return true;
/*      */     }
/* 6087 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6093 */     PageContext pageContext = _jspx_page_context;
/* 6094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6096 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6097 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6098 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 6100 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6101 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6102 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6103 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6104 */       return true;
/*      */     }
/* 6106 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6112 */     PageContext pageContext = _jspx_page_context;
/* 6113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6115 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6116 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 6117 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6119 */     _jspx_th_c_005fout_005f34.setValue("${ha.key}");
/* 6120 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 6121 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 6122 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6123 */       return true;
/*      */     }
/* 6125 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6131 */     PageContext pageContext = _jspx_page_context;
/* 6132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6134 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6135 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 6136 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6138 */     _jspx_th_c_005fout_005f35.setValue("${ha.value}");
/* 6139 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 6140 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 6141 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6142 */       return true;
/*      */     }
/* 6144 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6150 */     PageContext pageContext = _jspx_page_context;
/* 6151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6153 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6154 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 6155 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6157 */     _jspx_th_c_005fset_005f4.setVar("monitorName");
/* 6158 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 6159 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 6160 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6161 */         out = _jspx_page_context.pushBody();
/* 6162 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 6163 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 6164 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6167 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fset_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6168 */           return true;
/* 6169 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 6170 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6173 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6174 */         out = _jspx_page_context.popBody();
/* 6175 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 6178 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 6179 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 6180 */       return true;
/*      */     }
/* 6182 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 6183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6188 */     PageContext pageContext = _jspx_page_context;
/* 6189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6191 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6192 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 6193 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 6195 */     _jspx_th_c_005fout_005f36.setValue("${ha.value}");
/* 6196 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 6197 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 6198 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6199 */       return true;
/*      */     }
/* 6201 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6207 */     PageContext pageContext = _jspx_page_context;
/* 6208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6210 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6211 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 6212 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 6214 */     _jspx_th_c_005fout_005f37.setValue("${ha.key}");
/* 6215 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 6216 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 6217 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6218 */       return true;
/*      */     }
/* 6220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6226 */     PageContext pageContext = _jspx_page_context;
/* 6227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6229 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6230 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 6231 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 6233 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 6234 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 6235 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 6236 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6237 */       return true;
/*      */     }
/* 6239 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6245 */     PageContext pageContext = _jspx_page_context;
/* 6246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6248 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6249 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 6250 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6252 */     _jspx_th_c_005fif_005f24.setTest("${empty associatedmgs}");
/* 6253 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 6254 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 6256 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 6257 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f24, _jspx_page_context))
/* 6258 */           return true;
/* 6259 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 6260 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f24, _jspx_page_context))
/* 6261 */           return true;
/* 6262 */         out.write("</td>\n\t ");
/* 6263 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 6264 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6268 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 6269 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 6270 */       return true;
/*      */     }
/* 6272 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 6273 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6278 */     PageContext pageContext = _jspx_page_context;
/* 6279 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6281 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6282 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 6283 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f24);
/*      */     
/* 6285 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6286 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 6287 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 6288 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6289 */       return true;
/*      */     }
/* 6291 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6297 */     PageContext pageContext = _jspx_page_context;
/* 6298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6300 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6301 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 6302 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f24);
/*      */     
/* 6304 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 6305 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 6306 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 6307 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6308 */       return true;
/*      */     }
/* 6310 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6311 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6316 */     PageContext pageContext = _jspx_page_context;
/* 6317 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6319 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6320 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 6321 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6323 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6324 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 6325 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6326 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6327 */       return true;
/*      */     }
/* 6329 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6330 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6335 */     PageContext pageContext = _jspx_page_context;
/* 6336 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6338 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6339 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 6340 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6342 */     _jspx_th_c_005fout_005f38.setValue("${param.resourceid}");
/* 6343 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 6344 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 6345 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6346 */       return true;
/*      */     }
/* 6348 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6349 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6354 */     PageContext pageContext = _jspx_page_context;
/* 6355 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6357 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6358 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 6359 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6361 */     _jspx_th_c_005fout_005f39.setValue("${param.resourcename}");
/* 6362 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 6363 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 6364 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6365 */       return true;
/*      */     }
/* 6367 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6368 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6373 */     PageContext pageContext = _jspx_page_context;
/* 6374 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6376 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6377 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 6378 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6380 */     _jspx_th_c_005fout_005f40.setValue("${param.resourceid}");
/* 6381 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 6382 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 6383 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6384 */       return true;
/*      */     }
/* 6386 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6387 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6392 */     PageContext pageContext = _jspx_page_context;
/* 6393 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6395 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6396 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 6397 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6399 */     _jspx_th_c_005fout_005f41.setValue("${param.resourcename}");
/* 6400 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 6401 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 6402 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6403 */       return true;
/*      */     }
/* 6405 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6406 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6411 */     PageContext pageContext = _jspx_page_context;
/* 6412 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6414 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 6415 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 6416 */     _jspx_th_awolf_005fpiechart_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6418 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 6420 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("240");
/*      */     
/* 6422 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */     
/* 6424 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 6426 */     _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */     
/* 6428 */     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */     
/* 6430 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 6431 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 6432 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 6433 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 6434 */         out = _jspx_page_context.pushBody();
/* 6435 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 6436 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6439 */         out.write(" \n            ");
/* 6440 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 6441 */           return true;
/* 6442 */         out.write(32);
/* 6443 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 6444 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6447 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 6448 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6451 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 6452 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 6453 */       return true;
/*      */     }
/* 6455 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 6456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6461 */     PageContext pageContext = _jspx_page_context;
/* 6462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6464 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 6465 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 6466 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 6468 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 6469 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 6470 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 6471 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 6472 */         out = _jspx_page_context.pushBody();
/* 6473 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 6474 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6477 */         out.write(32);
/* 6478 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 6479 */           return true;
/* 6480 */         out.write(32);
/* 6481 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 6482 */           return true;
/* 6483 */         out.write(" \n            ");
/* 6484 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 6485 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6488 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 6489 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6492 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 6493 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 6494 */       return true;
/*      */     }
/* 6496 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 6497 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6502 */     PageContext pageContext = _jspx_page_context;
/* 6503 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6505 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6506 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 6507 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 6509 */     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */     
/* 6511 */     _jspx_th_awolf_005fparam_005f0.setValue("#29FF29");
/* 6512 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 6513 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 6514 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 6515 */       return true;
/*      */     }
/* 6517 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 6518 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6523 */     PageContext pageContext = _jspx_page_context;
/* 6524 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6526 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6527 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 6528 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 6530 */     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */     
/* 6532 */     _jspx_th_awolf_005fparam_005f1.setValue("#FF0000");
/* 6533 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 6534 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 6535 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 6536 */       return true;
/*      */     }
/* 6538 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 6539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6544 */     PageContext pageContext = _jspx_page_context;
/* 6545 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6547 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6548 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 6549 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6550 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 6551 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 6552 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6553 */         out = _jspx_page_context.pushBody();
/* 6554 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 6555 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6558 */         out.write("Attribute");
/* 6559 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 6560 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6563 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6564 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6567 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 6568 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6569 */       return true;
/*      */     }
/* 6571 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6572 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6577 */     PageContext pageContext = _jspx_page_context;
/* 6578 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6580 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6581 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 6582 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6583 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 6584 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 6585 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6586 */         out = _jspx_page_context.pushBody();
/* 6587 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 6588 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6591 */         out.write("Status");
/* 6592 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 6593 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6596 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6597 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6600 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 6601 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6602 */       return true;
/*      */     }
/* 6604 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f27(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6610 */     PageContext pageContext = _jspx_page_context;
/* 6611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6613 */     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6614 */     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 6615 */     _jspx_th_c_005fif_005f27.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6617 */     _jspx_th_c_005fif_005f27.setTest("${!empty param.reports}");
/* 6618 */     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 6619 */     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */       for (;;) {
/* 6621 */         out.write("\n<script>\nalert('temp');\njavascript:showDiv('reports');\n</script>\n");
/* 6622 */         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 6623 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6627 */     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 6628 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 6629 */       return true;
/*      */     }
/* 6631 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 6632 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6637 */     PageContext pageContext = _jspx_page_context;
/* 6638 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6640 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6641 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 6642 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6643 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 6644 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 6645 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6646 */         out = _jspx_page_context.pushBody();
/* 6647 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 6648 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6651 */         out.write("table.heading.attribute");
/* 6652 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 6653 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6656 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6657 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6660 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 6661 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6662 */       return true;
/*      */     }
/* 6664 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6665 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6670 */     PageContext pageContext = _jspx_page_context;
/* 6671 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6673 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6674 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 6675 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6676 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 6677 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 6678 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6679 */         out = _jspx_page_context.pushBody();
/* 6680 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 6681 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6684 */         out.write("table.heading.value");
/* 6685 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 6686 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6689 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6690 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6693 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 6694 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6695 */       return true;
/*      */     }
/* 6697 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6698 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6703 */     PageContext pageContext = _jspx_page_context;
/* 6704 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6706 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6707 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 6708 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6709 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 6710 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 6711 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6712 */         out = _jspx_page_context.pushBody();
/* 6713 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 6714 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6717 */         out.write("table.heading.status");
/* 6718 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 6719 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6722 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6723 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6726 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 6727 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6728 */       return true;
/*      */     }
/* 6730 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6731 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f28(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6736 */     PageContext pageContext = _jspx_page_context;
/* 6737 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6739 */     IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6740 */     _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 6741 */     _jspx_th_c_005fif_005f28.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 6743 */     _jspx_th_c_005fif_005f28.setTest("${status.count  == 1}");
/* 6744 */     int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 6745 */     if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */       for (;;) {
/* 6747 */         out.write("\n        <tr>\n        <td width=\"5%\" class=\"columnheadingnotop\">&nbsp;</td>\n          <td width=\"50%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 6748 */         if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fif_005f28, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 6749 */           return true;
/* 6750 */         out.write("</span></td>\n          <td width=\"15%\" class=\"columnheadingnotop\" align=\"center\"><span class=\"bodytextbold\">");
/* 6751 */         if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fif_005f28, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 6752 */           return true;
/* 6753 */         out.write("</span></td>\n          <td width=\"15%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 6754 */         if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_c_005fif_005f28, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 6755 */           return true;
/* 6756 */         out.write("</span></td>\n          <td width=\"15%\" class=\"columnheadingnotop\" align=\"center\"><span class=\"bodytextbold\">");
/* 6757 */         if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_c_005fif_005f28, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 6758 */           return true;
/* 6759 */         out.write("</span></td>\n          \n        </tr>\n");
/* 6760 */         int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 6761 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6765 */     if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 6766 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 6767 */       return true;
/*      */     }
/* 6769 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 6770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fif_005f28, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6775 */     PageContext pageContext = _jspx_page_context;
/* 6776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6778 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6779 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 6780 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fif_005f28);
/* 6781 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 6782 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 6783 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6784 */         out = _jspx_page_context.pushBody();
/* 6785 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 6786 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 6787 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6790 */         out.write(85);
/* 6791 */         out.write(82);
/* 6792 */         out.write(76);
/* 6793 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 6794 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6797 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6798 */         out = _jspx_page_context.popBody();
/* 6799 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 6802 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 6803 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6804 */       return true;
/*      */     }
/* 6806 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fif_005f28, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6812 */     PageContext pageContext = _jspx_page_context;
/* 6813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6815 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6816 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 6817 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fif_005f28);
/* 6818 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 6819 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 6820 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6821 */         out = _jspx_page_context.pushBody();
/* 6822 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 6823 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 6824 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6827 */         out.write("Response Time");
/* 6828 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 6829 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6832 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6833 */         out = _jspx_page_context.popBody();
/* 6834 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 6837 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 6838 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6839 */       return true;
/*      */     }
/* 6841 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6842 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_c_005fif_005f28, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6847 */     PageContext pageContext = _jspx_page_context;
/* 6848 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6850 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6851 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 6852 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_c_005fif_005f28);
/* 6853 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 6854 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 6855 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6856 */         out = _jspx_page_context.pushBody();
/* 6857 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 6858 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 6859 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6862 */         out.write("Response Code");
/* 6863 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 6864 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6867 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6868 */         out = _jspx_page_context.popBody();
/* 6869 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 6872 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 6873 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6874 */       return true;
/*      */     }
/* 6876 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6877 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_c_005fif_005f28, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6882 */     PageContext pageContext = _jspx_page_context;
/* 6883 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6885 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6886 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 6887 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_c_005fif_005f28);
/* 6888 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 6889 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 6890 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6891 */         out = _jspx_page_context.pushBody();
/* 6892 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 6893 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 6894 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6897 */         out.write("Content length");
/* 6898 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 6899 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6902 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6903 */         out = _jspx_page_context.popBody();
/* 6904 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 6907 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 6908 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6909 */       return true;
/*      */     }
/* 6911 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6912 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f29(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6917 */     PageContext pageContext = _jspx_page_context;
/* 6918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6920 */     IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6921 */     _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 6922 */     _jspx_th_c_005fif_005f29.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 6924 */     _jspx_th_c_005fif_005f29.setTest("${status.count %2 == 1}");
/* 6925 */     int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 6926 */     if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */       for (;;) {
/* 6928 */         out.write("\n <tr class=\"oddrowbgcolor\" >\n  ");
/* 6929 */         int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 6930 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6934 */     if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 6935 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 6936 */       return true;
/*      */     }
/* 6938 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 6939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f30(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6944 */     PageContext pageContext = _jspx_page_context;
/* 6945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6947 */     IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6948 */     _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 6949 */     _jspx_th_c_005fif_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 6951 */     _jspx_th_c_005fif_005f30.setTest("${status.count %2 == 0}");
/* 6952 */     int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 6953 */     if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */       for (;;) {
/* 6955 */         out.write("\n     \t<tr class=\"evenrowbgcolor\" >\n  ");
/* 6956 */         int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 6957 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6961 */     if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 6962 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 6963 */       return true;
/*      */     }
/* 6965 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 6966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6971 */     PageContext pageContext = _jspx_page_context;
/* 6972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6974 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6975 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 6976 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 6978 */     _jspx_th_c_005fout_005f42.setValue("${status.count}");
/* 6979 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 6980 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 6981 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6982 */       return true;
/*      */     }
/* 6984 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6985 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f32(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 6990 */     PageContext pageContext = _jspx_page_context;
/* 6991 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6993 */     IfTag _jspx_th_c_005fif_005f32 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6994 */     _jspx_th_c_005fif_005f32.setPageContext(_jspx_page_context);
/* 6995 */     _jspx_th_c_005fif_005f32.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 6997 */     _jspx_th_c_005fif_005f32.setTest("${colcount.count==1 }");
/* 6998 */     int _jspx_eval_c_005fif_005f32 = _jspx_th_c_005fif_005f32.doStartTag();
/* 6999 */     if (_jspx_eval_c_005fif_005f32 != 0) {
/*      */       for (;;) {
/* 7001 */         out.write("\n         <td class=\"bodytext\" aling=\"center\">\n       \n       ");
/* 7002 */         if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fif_005f32, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 7003 */           return true;
/* 7004 */         out.write("\n         </td>\n   ");
/* 7005 */         int evalDoAfterBody = _jspx_th_c_005fif_005f32.doAfterBody();
/* 7006 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7010 */     if (_jspx_th_c_005fif_005f32.doEndTag() == 5) {
/* 7011 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 7012 */       return true;
/*      */     }
/* 7014 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 7015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fif_005f32, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 7020 */     PageContext pageContext = _jspx_page_context;
/* 7021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7023 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7024 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 7025 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fif_005f32);
/*      */     
/* 7027 */     _jspx_th_c_005fout_005f44.setValue("${numeric_data_id_value[responsetimeids[(status.count -1)]]}");
/* 7028 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 7029 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 7030 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 7031 */       return true;
/*      */     }
/* 7033 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 7034 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f33(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 7039 */     PageContext pageContext = _jspx_page_context;
/* 7040 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7042 */     IfTag _jspx_th_c_005fif_005f33 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7043 */     _jspx_th_c_005fif_005f33.setPageContext(_jspx_page_context);
/* 7044 */     _jspx_th_c_005fif_005f33.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 7046 */     _jspx_th_c_005fif_005f33.setTest("${colcount.count==3 }");
/* 7047 */     int _jspx_eval_c_005fif_005f33 = _jspx_th_c_005fif_005f33.doStartTag();
/* 7048 */     if (_jspx_eval_c_005fif_005f33 != 0) {
/*      */       for (;;) {
/* 7050 */         out.write("\n           <td class=\"bodytext\" aling=\"center\">\n         \n         ");
/* 7051 */         if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fif_005f33, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 7052 */           return true;
/* 7053 */         out.write("\n           </td>\n   ");
/* 7054 */         int evalDoAfterBody = _jspx_th_c_005fif_005f33.doAfterBody();
/* 7055 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7059 */     if (_jspx_th_c_005fif_005f33.doEndTag() == 5) {
/* 7060 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
/* 7061 */       return true;
/*      */     }
/* 7063 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
/* 7064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fif_005f33, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 7069 */     PageContext pageContext = _jspx_page_context;
/* 7070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7072 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 7073 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 7074 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fif_005f33);
/*      */     
/* 7076 */     _jspx_th_c_005fout_005f45.setValue("${numeric_data_id_value[responsecodes[(status.count -1)]]}");
/* 7077 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 7078 */     if (_jspx_eval_c_005fout_005f45 != 0) {
/* 7079 */       if (_jspx_eval_c_005fout_005f45 != 1) {
/* 7080 */         out = _jspx_page_context.pushBody();
/* 7081 */         _jspx_push_body_count_c_005fforEach_005f3[0] += 1;
/* 7082 */         _jspx_th_c_005fout_005f45.setBodyContent((BodyContent)out);
/* 7083 */         _jspx_th_c_005fout_005f45.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7086 */         out.write(45);
/* 7087 */         int evalDoAfterBody = _jspx_th_c_005fout_005f45.doAfterBody();
/* 7088 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7091 */       if (_jspx_eval_c_005fout_005f45 != 1) {
/* 7092 */         out = _jspx_page_context.popBody();
/* 7093 */         _jspx_push_body_count_c_005fforEach_005f3[0] -= 1;
/*      */       }
/*      */     }
/* 7096 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 7097 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f45);
/* 7098 */       return true;
/*      */     }
/* 7100 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f45);
/* 7101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f34(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 7106 */     PageContext pageContext = _jspx_page_context;
/* 7107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7109 */     IfTag _jspx_th_c_005fif_005f34 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7110 */     _jspx_th_c_005fif_005f34.setPageContext(_jspx_page_context);
/* 7111 */     _jspx_th_c_005fif_005f34.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 7113 */     _jspx_th_c_005fif_005f34.setTest("${colcount.count==4}");
/* 7114 */     int _jspx_eval_c_005fif_005f34 = _jspx_th_c_005fif_005f34.doStartTag();
/* 7115 */     if (_jspx_eval_c_005fif_005f34 != 0) {
/*      */       for (;;) {
/* 7117 */         out.write("\n       <td class=\"bodytext\" aling=\"center\">\n     \n     ");
/* 7118 */         if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fif_005f34, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 7119 */           return true;
/* 7120 */         out.write("\n       </td>\n   ");
/* 7121 */         int evalDoAfterBody = _jspx_th_c_005fif_005f34.doAfterBody();
/* 7122 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7126 */     if (_jspx_th_c_005fif_005f34.doEndTag() == 5) {
/* 7127 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 7128 */       return true;
/*      */     }
/* 7130 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 7131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fif_005f34, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 7136 */     PageContext pageContext = _jspx_page_context;
/* 7137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7139 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 7140 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 7141 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fif_005f34);
/*      */     
/* 7143 */     _jspx_th_c_005fout_005f46.setValue("${numeric_data_id_value[attributeslist[(status.count -1) * 4 + (colcount.count -1 )]]}");
/* 7144 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 7145 */     if (_jspx_eval_c_005fout_005f46 != 0) {
/* 7146 */       if (_jspx_eval_c_005fout_005f46 != 1) {
/* 7147 */         out = _jspx_page_context.pushBody();
/* 7148 */         _jspx_push_body_count_c_005fforEach_005f3[0] += 1;
/* 7149 */         _jspx_th_c_005fout_005f46.setBodyContent((BodyContent)out);
/* 7150 */         _jspx_th_c_005fout_005f46.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7153 */         out.write(45);
/* 7154 */         int evalDoAfterBody = _jspx_th_c_005fout_005f46.doAfterBody();
/* 7155 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7158 */       if (_jspx_eval_c_005fout_005f46 != 1) {
/* 7159 */         out = _jspx_page_context.popBody();
/* 7160 */         _jspx_push_body_count_c_005fforEach_005f3[0] -= 1;
/*      */       }
/*      */     }
/* 7163 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 7164 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f46);
/* 7165 */       return true;
/*      */     }
/* 7167 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f46);
/* 7168 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7173 */     PageContext pageContext = _jspx_page_context;
/* 7174 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7176 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7177 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 7178 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/* 7179 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 7180 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 7181 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 7182 */         out = _jspx_page_context.pushBody();
/* 7183 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 7184 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7187 */         out.write("table.heading.value");
/* 7188 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 7189 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7192 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 7193 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7196 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 7197 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 7198 */       return true;
/*      */     }
/* 7200 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 7201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7206 */     PageContext pageContext = _jspx_page_context;
/* 7207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7209 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7210 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 7211 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/* 7212 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 7213 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 7214 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 7215 */         out = _jspx_page_context.pushBody();
/* 7216 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 7217 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7220 */         out.write("table.heading.status");
/* 7221 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 7222 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7225 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 7226 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7229 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 7230 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 7231 */       return true;
/*      */     }
/* 7233 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 7234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7239 */     PageContext pageContext = _jspx_page_context;
/* 7240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7242 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 7243 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 7244 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 7246 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 7248 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 7249 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 7250 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 7251 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 7252 */       return true;
/*      */     }
/* 7254 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 7255 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\QEngineScriptMonitor_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */