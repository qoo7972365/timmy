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
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
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
/*      */ public final class pingDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2229 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2233 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2253 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2257 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2258 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2259 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2260 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2261 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2265 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2266 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2268 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2270 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2271 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2272 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2273 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2274 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2275 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2282 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2285 */     JspWriter out = null;
/* 2286 */     Object page = this;
/* 2287 */     JspWriter _jspx_out = null;
/* 2288 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2292 */       response.setContentType("text/html;charset=UTF-8");
/* 2293 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2295 */       _jspx_page_context = pageContext;
/* 2296 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2297 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2298 */       session = pageContext.getSession();
/* 2299 */       out = pageContext.getOut();
/* 2300 */       _jspx_out = out;
/*      */       
/* 2302 */       out.write("<!DOCTYPE html>\n");
/* 2303 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2305 */       request.setAttribute("HelpKey", "Ping Monitor");
/*      */       
/* 2307 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2308 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2310 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2311 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2312 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2314 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2316 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2318 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2320 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2321 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2322 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2323 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2326 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2327 */         String available = null;
/* 2328 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2329 */         out.write(10);
/*      */         
/* 2331 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2332 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2333 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2335 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2337 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2339 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2341 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2342 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2343 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2344 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2347 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2348 */           String unavailable = null;
/* 2349 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2350 */           out.write(10);
/*      */           
/* 2352 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2353 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2354 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2356 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2358 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2360 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2362 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2363 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2364 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2365 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2368 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2369 */             String unmanaged = null;
/* 2370 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2371 */             out.write(10);
/*      */             
/* 2373 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2374 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2375 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2377 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2379 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2381 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2383 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2384 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2385 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2386 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2389 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2390 */               String scheduled = null;
/* 2391 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2392 */               out.write(10);
/*      */               
/* 2394 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2395 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2396 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2398 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2400 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2402 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2404 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2405 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2406 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2407 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2410 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2411 */                 String critical = null;
/* 2412 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2413 */                 out.write(10);
/*      */                 
/* 2415 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2416 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2417 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2419 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2421 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2423 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2425 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2426 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2427 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2428 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2431 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2432 */                   String clear = null;
/* 2433 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2434 */                   out.write(10);
/*      */                   
/* 2436 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2437 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2438 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2440 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2442 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2444 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2446 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2447 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2448 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2449 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2452 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2453 */                     String warning = null;
/* 2454 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2455 */                     out.write(10);
/* 2456 */                     out.write(10);
/*      */                     
/* 2458 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2459 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2461 */                     out.write(10);
/* 2462 */                     out.write(10);
/* 2463 */                     out.write(10);
/* 2464 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */                     
/* 2466 */                     Properties ess_atts = new Properties();
/* 2467 */                     String resourceName = request.getParameter("resourcename");
/* 2468 */                     String resID = request.getParameter("resourceid");
/* 2469 */                     String resourceid = resID;
/* 2470 */                     request.setAttribute("resourceid", resID);
/* 2471 */                     ArrayList attribIDs = new ArrayList();
/* 2472 */                     String haid = request.getParameter("haid");
/* 2473 */                     String moname = request.getParameter("moname");
/* 2474 */                     ArrayList resIDs = new ArrayList();
/* 2475 */                     resIDs.add(resID);
/*      */                     
/* 2477 */                     attribIDs.add("9000");
/* 2478 */                     attribIDs.add("9001");
/*      */                     
/* 2480 */                     attribIDs.add("9002");
/* 2481 */                     attribIDs.add("9003");
/* 2482 */                     String resourcetype = request.getParameter("type");
/* 2483 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2484 */                     String encodeurl = URLEncoder.encode("/createPing.do?resourceid=" + resourceid + "&type=Ping Monitor&moname=" + moname + "&method=showPingMonitorDetails&resourcename=" + resourceName + "&viewType=showResourceTypes");
/* 2485 */                     String tipCurStatus = FormatUtil.getString("am.webclient.script.tooltip");
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2490 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2491 */                     String moquery = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resID;
/* 2492 */                     String monitorname = null;
/* 2493 */                     String displayname = null;
/* 2494 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */                     try
/*      */                     {
/* 2497 */                       ResultSet rs = AMConnectionPool.executeQueryStmt(moquery);
/* 2498 */                       if (rs.next())
/*      */                       {
/* 2500 */                         monitorname = rs.getString("RESOURCENAME");
/* 2501 */                         displayname = rs.getString("DISPLAYNAME");
/*      */                       }
/*      */                       try
/*      */                       {
/* 2505 */                         rs.close();
/*      */                       }
/*      */                       catch (Exception exc) {}
/*      */                     }
/*      */                     catch (Exception exc) {}
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2524 */                     out.write(10);
/* 2525 */                     out.write(10);
/* 2526 */                     GetWLSGraph wlsGraph = null;
/* 2527 */                     wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2528 */                     if (wlsGraph == null) {
/* 2529 */                       wlsGraph = new GetWLSGraph();
/* 2530 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2532 */                     out.write(10);
/* 2533 */                     CAMGraphs camGraph = null;
/* 2534 */                     camGraph = (CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 2535 */                     if (camGraph == null) {
/* 2536 */                       camGraph = new CAMGraphs();
/* 2537 */                       _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                     }
/* 2539 */                     out.write(10);
/* 2540 */                     Hashtable motypedisplaynames = null;
/* 2541 */                     synchronized (application) {
/* 2542 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2543 */                       if (motypedisplaynames == null) {
/* 2544 */                         motypedisplaynames = new Hashtable();
/* 2545 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2548 */                     out.write(10);
/* 2549 */                     Hashtable availabilitykeys = null;
/* 2550 */                     synchronized (application) {
/* 2551 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2552 */                       if (availabilitykeys == null) {
/* 2553 */                         availabilitykeys = new Hashtable();
/* 2554 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2557 */                     out.write(10);
/* 2558 */                     Hashtable healthkeys = null;
/* 2559 */                     synchronized (application) {
/* 2560 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2561 */                       if (healthkeys == null) {
/* 2562 */                         healthkeys = new Hashtable();
/* 2563 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2566 */                     out.write(10);
/* 2567 */                     out.write(10);
/*      */                     
/* 2569 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2570 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2571 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2573 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2574 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2575 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2577 */                         out.write(32);
/* 2578 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2580 */                         out.write(10);
/* 2581 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2583 */                         out.write(10);
/* 2584 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2586 */                         out.write("\n                         ");
/*      */                         
/* 2588 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2589 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2590 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2592 */                         _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */                         
/* 2594 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2595 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2596 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2597 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2598 */                             out = _jspx_page_context.pushBody();
/* 2599 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2600 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2603 */                             out.write("\n                         ");
/* 2604 */                             out.write("<!--$Id$-->\n\n\n\n");
/*      */                             
/* 2606 */                             int healthid = 2201;
/* 2607 */                             int availabilityid = 2200;
/* 2608 */                             String haid1 = "";
/* 2609 */                             String head = "Script Monitor";
/* 2610 */                             String mon_type = null;
/* 2611 */                             String rtype = request.getParameter("type");
/* 2612 */                             head = rtype;
/* 2613 */                             if (rtype.equals("File System Monitor"))
/*      */                             {
/* 2615 */                               head = "File / Directory Monitor";
/*      */                             }
/*      */                             
/* 2618 */                             String typedisplayname = head;
/* 2619 */                             if (request.getParameter("typedisplayname") != null)
/*      */                             {
/* 2621 */                               typedisplayname = request.getParameter("typedisplayname");
/*      */                             }
/* 2623 */                             else if (request.getAttribute("typedisplayname") != null)
/*      */                             {
/* 2625 */                               typedisplayname = (String)request.getAttribute("typedisplayname");
/*      */                             }
/*      */                             
/* 2628 */                             int baseid = -1;
/*      */                             try
/*      */                             {
/* 2631 */                               if (request.getAttribute("baseid") != null) {
/* 2632 */                                 baseid = Integer.parseInt((String)request.getAttribute("baseid"));
/*      */                               }
/*      */                             }
/*      */                             catch (Exception e2)
/*      */                             {
/* 2637 */                               e2.printStackTrace();
/*      */                             }
/* 2639 */                             String appendParams = "&baseid=" + baseid;
/* 2640 */                             if (baseid != -1)
/*      */                             {
/* 2642 */                               appendParams = appendParams + "&customType=true&basetype=Script Monitor";
/*      */                             }
/* 2644 */                             if ((rtype.equals("file")) || (rtype.equals("directory")) || (rtype.equals("File System Monitor")))
/*      */                             {
/* 2646 */                               rtype = "File System Monitor";
/* 2647 */                               head = "File / Directory Monitor";
/* 2648 */                               typedisplayname = "File / Directory Monitor";
/* 2649 */                             } else if (rtype.equals("Ping Monitor"))
/*      */                             {
/* 2651 */                               head = "Ping Monitor";
/* 2652 */                               rtype = "Ping Monitor";
/* 2653 */                               typedisplayname = "Ping Monitor";
/* 2654 */                               healthid = 9001;
/* 2655 */                               availabilityid = 9000;
/*      */                             }
/*      */                             else
/*      */                             {
/* 2659 */                               availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2660 */                               healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                             }
/*      */                             
/*      */ 
/* 2664 */                             if (request.getParameter("mtype") != null) {
/* 2665 */                               mon_type = request.getParameter("mtype");
/*      */                             }
/*      */                             else {
/* 2668 */                               mon_type = request.getParameter("type");
/*      */                             }
/*      */                             
/* 2671 */                             if (request.getParameter("type").equals("QENGINE"))
/*      */                             {
/* 2673 */                               healthid = 4301;
/* 2674 */                               availabilityid = 4300;
/*      */                             }
/* 2676 */                             else if (mon_type.equals("file")) {
/* 2677 */                               head = "File System Monitor";
/*      */                               
/* 2679 */                               healthid = 6009;
/* 2680 */                               availabilityid = 6008;
/*      */                             }
/* 2682 */                             else if (mon_type.equals("directory"))
/*      */                             {
/* 2684 */                               head = "File System Monitor";
/* 2685 */                               healthid = 6001;
/* 2686 */                               availabilityid = 6000;
/*      */ 
/*      */                             }
/* 2689 */                             else if (request.getParameter("type").equals("Script Monitor"))
/*      */                             {
/* 2691 */                               healthid = 2201;
/* 2692 */                               availabilityid = 2200;
/*      */                             }
/* 2694 */                             else if (mon_type.equals("Ping Monitor"))
/*      */                             {
/* 2696 */                               healthid = 9001;
/* 2697 */                               availabilityid = 9000;
/*      */                             }
/*      */                             else
/*      */                             {
/* 2701 */                               availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2702 */                               healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                             }
/*      */                             
/*      */ 
/* 2706 */                             out.write("\n\n<script language=\"JavaScript\">\n");
/*      */                             
/* 2708 */                             if (request.getParameter("editPage") != null)
/*      */                             {
/* 2710 */                               out.write(10);
/*      */                             }
/*      */                             
/* 2713 */                             out.write("\n\n\n\tfunction confirmDelete()\n \t {\n  var s = confirm(\"");
/* 2714 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2715 */                             out.write("\");\n  if (s)\n         document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 2716 */                             out.print(head);
/* 2717 */                             out.write("&baseid=");
/* 2718 */                             out.print(baseid);
/* 2719 */                             out.write("&select=");
/* 2720 */                             if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2722 */                             out.write("\";\n\t }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2723 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2724 */                             out.write("\");\n  if (s){\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2725 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2727 */                             out.write("&type=");
/* 2728 */                             out.print(rtype);
/* 2729 */                             out.write("&moname=");
/* 2730 */                             out.print(moname);
/* 2731 */                             out.write("&resourcename=");
/* 2732 */                             out.print(resourceName);
/* 2733 */                             out.write("\";\n\n}\n\t }\n\n         function confirmUnManage()\n \t {\n        \t  var show_msg=\"false\";\n              var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2734 */                             out.print(request.getParameter("resourceid"));
/* 2735 */                             out.write("; //No i18n\n              $.ajax({\n                type:'POST', //No i18n\n                url:url,\n                async:false,\n                success: function(data)\n                {\n                  show_msg=data\n                }\n              });\n              if(show_msg.indexOf(\"true\")>-1)\n              {\n                  alert(\"");
/* 2736 */                             out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2737 */                             out.write("\");\n\t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2738 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2740 */                             out.write("&type=");
/* 2741 */                             out.print(rtype);
/* 2742 */                             out.write("&moname=");
/* 2743 */                             out.print(moname);
/* 2744 */                             out.write("&resourcename=");
/* 2745 */                             out.print(resourceName);
/* 2746 */                             out.write("\";\n             }\n           else { \n        \t   \tvar s = confirm(\"");
/* 2747 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2748 */                             out.write("\");\n       \t\t if (s){\n   \t\t \t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2749 */                             if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2751 */                             out.write("&type=");
/* 2752 */                             out.print(rtype);
/* 2753 */                             out.write("&moname=");
/* 2754 */                             out.print(moname);
/* 2755 */                             out.write("&resourcename=");
/* 2756 */                             out.print(resourceName);
/* 2757 */                             out.write("\";\n\t\t      }\n\t      }\n       }\n  </script>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n    ");
/* 2758 */                             out.print(FormatUtil.getString(typedisplayname));
/* 2759 */                             out.write("\n      </td>\n  </tr>\n      ");
/* 2760 */                             if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2762 */                             out.write("\n      ");
/* 2763 */                             if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2765 */                             out.write(10);
/* 2766 */                             out.write(9);
/* 2767 */                             out.write(9);
/* 2768 */                             if (_jspx_meth_c_005fif_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2770 */                             out.write(10);
/* 2771 */                             out.write("\n\n\n\n");
/*      */                             
/* 2773 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2774 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2775 */                             _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2777 */                             _jspx_th_c_005fif_005f5.setTest("${!empty param.haid && empty invalidhaid}");
/* 2778 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2779 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/* 2781 */                                 out.write(10);
/*      */                                 
/* 2783 */                                 haid1 = "&haid=" + request.getParameter("haid");
/*      */                                 
/* 2785 */                                 out.write(10);
/* 2786 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2787 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2791 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2792 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                             }
/*      */                             
/* 2795 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2796 */                             out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                             
/* 2798 */                             IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2799 */                             _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2800 */                             _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2802 */                             _jspx_th_c_005fif_005f6.setTest("${param.method=='editScript'}");
/* 2803 */                             int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2804 */                             if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                               for (;;) {
/* 2806 */                                 out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 2807 */                                 if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 2809 */                                 out.write("&method=showResourceForResourceID");
/* 2810 */                                 out.print(haid1);
/* 2811 */                                 out.write("\"  class=\"new-left-links\">\n    ");
/* 2812 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2813 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2817 */                             if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2818 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                             }
/*      */                             
/* 2821 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2822 */                             out.write("\n    ");
/* 2823 */                             out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2824 */                             out.write("\n    ");
/* 2825 */                             if (_jspx_meth_c_005fif_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2827 */                             out.write("\n     </td>\n  </tr>\n");
/* 2828 */                             out.write(10);
/* 2829 */                             out.write(32);
/* 2830 */                             out.write(32);
/*      */                             
/* 2832 */                             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2833 */                             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2834 */                             _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2836 */                             _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN || !empty DEMO }");
/* 2837 */                             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2838 */                             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                               for (;;) {
/* 2840 */                                 out.write("\n\n\n\t<tr>\n  ");
/*      */                                 
/* 2842 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2843 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2844 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 2846 */                                 _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2847 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2848 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2850 */                                     out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 2851 */                                     out.print(ALERTCONFIG_TEXT);
/* 2852 */                                     out.write("</a> </td>\n  ");
/* 2853 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2854 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2858 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2859 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2862 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2863 */                                 out.write(10);
/* 2864 */                                 out.write(9);
/*      */                                 
/* 2866 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2867 */                                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2868 */                                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 2870 */                                 _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2871 */                                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2872 */                                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2874 */                                     out.write("\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                     
/* 2876 */                                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2877 */                                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2878 */                                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                     
/* 2880 */                                     _jspx_th_c_005fif_005f9.setTest("${param.method=='editScript'}");
/* 2881 */                                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2882 */                                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                       for (;;) {
/* 2884 */                                         out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 2885 */                                         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                                           return;
/* 2887 */                                         out.write("&method=showResourceForResourceID&alert=true");
/* 2888 */                                         out.print(haid1);
/* 2889 */                                         out.write("\"  class=\"new-left-links\">\n    ");
/* 2890 */                                         out.print(ALERTCONFIG_TEXT);
/* 2891 */                                         out.write("\n    </a>\n    ");
/* 2892 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2893 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2897 */                                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2898 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                     }
/*      */                                     
/* 2901 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2902 */                                     out.write("\n    ");
/*      */                                     
/* 2904 */                                     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2905 */                                     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2906 */                                     _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                     
/* 2908 */                                     _jspx_th_c_005fif_005f10.setTest("${param.method!='editScript'}");
/* 2909 */                                     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2910 */                                     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                       for (;;) {
/* 2912 */                                         out.write("\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2913 */                                         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                           return;
/* 2915 */                                         out.write("&mtype=");
/* 2916 */                                         out.print(mon_type);
/* 2917 */                                         out.write("\" class=\"new-left-links\">\n    ");
/* 2918 */                                         out.print(ALERTCONFIG_TEXT);
/* 2919 */                                         out.write("\n    </a>\n    ");
/* 2920 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2921 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2925 */                                     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2926 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                     }
/*      */                                     
/* 2929 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2930 */                                     out.write("\n    </td>\n");
/* 2931 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2932 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2936 */                                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2937 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2940 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2941 */                                 out.write("\n  </tr>\n  ");
/* 2942 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2943 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2947 */                             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2948 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                             }
/*      */                             
/* 2951 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2952 */                             out.write(10);
/*      */                             
/* 2954 */                             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2955 */                             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2956 */                             _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2958 */                             _jspx_th_c_005fif_005f11.setTest("${param.type=='Script Monitor'}");
/* 2959 */                             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2960 */                             if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                               for (;;) {
/* 2962 */                                 out.write(10);
/* 2963 */                                 out.write(32);
/* 2964 */                                 out.write(32);
/*      */                                 
/* 2966 */                                 IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2967 */                                 _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2968 */                                 _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fif_005f11);
/*      */                                 
/* 2970 */                                 _jspx_th_c_005fif_005f12.setTest("${!empty ADMIN || !empty DEMO }");
/* 2971 */                                 int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2972 */                                 if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                   for (;;) {
/* 2974 */                                     out.write(10);
/* 2975 */                                     out.write(32);
/* 2976 */                                     out.write(32);
/*      */                                     
/* 2978 */                                     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2979 */                                     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 2980 */                                     _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f12);
/*      */                                     
/* 2982 */                                     _jspx_th_c_005fif_005f13.setTest("${param.method=='editScript'}");
/* 2983 */                                     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 2984 */                                     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                       for (;;) {
/* 2986 */                                         out.write("\n\t <tr>\n  ");
/*      */                                         
/* 2988 */                                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2989 */                                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2990 */                                         _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                         
/* 2992 */                                         _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 2993 */                                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2994 */                                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 2996 */                                             out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 2997 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 2998 */                                             out.write("</a> </td>\n  ");
/* 2999 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3000 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3004 */                                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3005 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3008 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3009 */                                         out.write("\n        ");
/*      */                                         
/* 3011 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3012 */                                         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3013 */                                         _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                         
/* 3015 */                                         _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3016 */                                         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3017 */                                         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 3019 */                                             out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n\n    <a href=\"/showresource.do?resourceid=");
/* 3020 */                                             if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                                               return;
/* 3022 */                                             out.write("&method=showResourceForResourceID&reports=true");
/* 3023 */                                             out.print(haid1);
/* 3024 */                                             out.write("\"  class=\"new-left-links\">\n    ");
/* 3025 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3026 */                                             out.write("\n    </a>\n     </td>\n");
/* 3027 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3028 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3032 */                                         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3033 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3036 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3037 */                                         out.write("\n  </tr>\n  ");
/* 3038 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3039 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3043 */                                     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3044 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                     }
/*      */                                     
/* 3047 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3048 */                                     out.write(10);
/* 3049 */                                     out.write(32);
/* 3050 */                                     out.write(32);
/* 3051 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3052 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3056 */                                 if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3057 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                                 }
/*      */                                 
/* 3060 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3061 */                                 out.write("\n\n  ");
/*      */                                 
/* 3063 */                                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3064 */                                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3065 */                                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f11);
/*      */                                 
/* 3067 */                                 _jspx_th_c_005fif_005f14.setTest("${!empty ADMIN || !empty DEMO }");
/* 3068 */                                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3069 */                                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                   for (;;) {
/* 3071 */                                     out.write(10);
/* 3072 */                                     out.write(32);
/* 3073 */                                     out.write(32);
/*      */                                     
/* 3075 */                                     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3076 */                                     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3077 */                                     _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f14);
/*      */                                     
/* 3079 */                                     _jspx_th_c_005fif_005f15.setTest("${param.method!='editScript'}");
/* 3080 */                                     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3081 */                                     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                       for (;;) {
/* 3083 */                                         out.write("\n  <tr>\n");
/*      */                                         
/* 3085 */                                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3086 */                                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3087 */                                         _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 3089 */                                         _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3090 */                                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3091 */                                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                           for (;;) {
/* 3093 */                                             out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3094 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3095 */                                             out.write("</a> </td>\n  ");
/* 3096 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3097 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3101 */                                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3102 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                         }
/*      */                                         
/* 3105 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3106 */                                         out.write("\n        ");
/*      */                                         
/* 3108 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3109 */                                         _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3110 */                                         _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 3112 */                                         _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3113 */                                         int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3114 */                                         if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                           for (;;) {
/* 3116 */                                             out.write("\n\n    <td class=\"leftlinkstd\"><a href=\"javascript:enableReports()\" class=\"new-left-links\">");
/* 3117 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3118 */                                             out.write("</a></td>\n   </td>\n");
/* 3119 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3120 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3124 */                                         if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3125 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                         }
/*      */                                         
/* 3128 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3129 */                                         out.write("\n  </tr>\n  ");
/* 3130 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3131 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3135 */                                     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3136 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                     }
/*      */                                     
/* 3139 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3140 */                                     out.write(10);
/* 3141 */                                     out.write(32);
/* 3142 */                                     out.write(32);
/* 3143 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3144 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3148 */                                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3149 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                 }
/*      */                                 
/* 3152 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3153 */                                 out.write(10);
/* 3154 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3155 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3159 */                             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3160 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                             }
/*      */                             
/* 3163 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3164 */                             out.write(10);
/* 3165 */                             out.write(10);
/*      */                             
/* 3167 */                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3168 */                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3169 */                             _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3171 */                             _jspx_th_logic_005fpresent_005f3.setRole("ENTERPRISEADMIN");
/* 3172 */                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3173 */                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                               for (;;) {
/* 3175 */                                 out.write("\n  \n");
/*      */                                 
/* 3177 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3178 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3179 */                                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                 
/* 3181 */                                 _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3182 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3183 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3185 */                                     out.write("\n<tr>\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3186 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3187 */                                     out.write("</a> </td> \n</tr>\n");
/* 3188 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3189 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3193 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3194 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 3197 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3198 */                                 out.write(10);
/* 3199 */                                 out.write(10);
/*      */                                 
/* 3201 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3202 */                                 _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3203 */                                 _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                 
/* 3205 */                                 _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3206 */                                 int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3207 */                                 if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                   for (;;) {
/* 3209 */                                     out.write(10);
/* 3210 */                                     if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 3211 */                                       out.write("\n\t<tr>\n   <td height=\"21\" class=\"leftlinkstd\">\n   ");
/*      */                                       
/* 3213 */                                       String temphaid = request.getParameter("haid");
/*      */                                       try
/*      */                                       {
/* 3216 */                                         Integer.parseInt(temphaid);
/*      */                                         
/* 3218 */                                         if (rtype.equals("Ping Monitor")) {
/* 3219 */                                           out.write("\n <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3220 */                                           if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3222 */                                           out.write("&method=editScript");
/* 3223 */                                           out.print(appendParams);
/* 3224 */                                           out.write("&resourceid=");
/* 3225 */                                           if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3227 */                                           out.write("&aam_jump=true&type=");
/* 3228 */                                           out.print(request.getParameter("type"));
/* 3229 */                                           if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3231 */                                           out.write("&resourcename=");
/* 3232 */                                           out.print(resourceName);
/* 3233 */                                           out.write("&mtype=");
/* 3234 */                                           out.print(mon_type);
/* 3235 */                                           out.write("\"  class=\"new-left-links\">\n\n");
/*      */                                         } else {
/* 3237 */                                           out.write("\n    <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3238 */                                           if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3240 */                                           out.write("&method=editScript");
/* 3241 */                                           out.print(appendParams);
/* 3242 */                                           out.write("&resourceid=");
/* 3243 */                                           if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3245 */                                           out.write("&aam_jump=true&type=");
/* 3246 */                                           out.print(request.getParameter("type"));
/* 3247 */                                           if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3249 */                                           out.write("&resourcename=");
/* 3250 */                                           out.print(resourceName);
/* 3251 */                                           out.write("&mtype=");
/* 3252 */                                           out.print(mon_type);
/* 3253 */                                           out.write("\"  class=\"new-left-links\">\n   ");
/*      */                                         }
/*      */                                         
/*      */                                       }
/*      */                                       catch (NumberFormatException ne)
/*      */                                       {
/* 3259 */                                         if (rtype.equals("Ping Monitor")) {
/* 3260 */                                           out.write("\n<a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 3261 */                                           out.print(request.getParameter("resourceid"));
/* 3262 */                                           out.write("&type=");
/* 3263 */                                           out.print(request.getParameter("type"));
/* 3264 */                                           out.write("&moname=");
/* 3265 */                                           out.print(moname);
/* 3266 */                                           out.write("&method=showdetails&resourcename=");
/* 3267 */                                           out.print(request.getParameter("resourcename"));
/* 3268 */                                           out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n\n");
/*      */                                         } else {
/* 3270 */                                           out.write("\n\n    <a target=\"mas_window\" href=\"/updateScript.do?method=editScript");
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
/*      */                                       }
/*      */                                       
/* 3287 */                                       out.write(10);
/* 3288 */                                       out.write(32);
/* 3289 */                                       out.write(32);
/* 3290 */                                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3291 */                                       out.write("\n   </td> </tr>\n   ");
/*      */                                     }
/* 3293 */                                     out.write(10);
/* 3294 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3295 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3299 */                                 if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3300 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                                 }
/*      */                                 
/* 3303 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3304 */                                 out.write(10);
/* 3305 */                                 out.write(32);
/* 3306 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3307 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3311 */                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3312 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                             }
/*      */                             
/* 3315 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3316 */                             out.write(10);
/* 3317 */                             out.write(32);
/* 3318 */                             out.write(10);
/*      */                             
/* 3320 */                             IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3321 */                             _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3322 */                             _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3324 */                             _jspx_th_c_005fif_005f16.setTest("${(!empty ADMIN || !empty DEMO) }");
/* 3325 */                             int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3326 */                             if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                               for (;;) {
/* 3328 */                                 out.write("\n  <tr>\n");
/*      */                                 
/* 3330 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3331 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3332 */                                 _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f16);
/*      */                                 
/* 3334 */                                 _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3335 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3336 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3338 */                                     out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3339 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3340 */                                     out.write("</a> </td>\n  ");
/* 3341 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3342 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3346 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3347 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3350 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3351 */                                 out.write("\n        ");
/*      */                                 
/* 3353 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3354 */                                 _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3355 */                                 _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_c_005fif_005f16);
/*      */                                 
/* 3357 */                                 _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 3358 */                                 int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 3359 */                                 if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3361 */                                     out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                     
/* 3363 */                                     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3364 */                                     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3365 */                                     _jspx_th_c_005fif_005f17.setParent(_jspx_th_logic_005fnotPresent_005f4);
/*      */                                     
/* 3367 */                                     _jspx_th_c_005fif_005f17.setTest("${param.actionmethod!='editScript' }");
/* 3368 */                                     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3369 */                                     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                       for (;;) {
/* 3371 */                                         out.write("\n    ");
/*      */                                         
/* 3373 */                                         String temphaid = request.getParameter("haid");
/*      */                                         try
/*      */                                         {
/* 3376 */                                           Integer.parseInt(temphaid);
/*      */                                           
/* 3378 */                                           if (rtype.equals("Ping Monitor")) {
/* 3379 */                                             out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                           } else {
/* 3381 */                                             out.write("\n\n<a href=\"/updateScript.do?haid=");
/* 3382 */                                             if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3384 */                                             out.write("&method=editScript");
/* 3385 */                                             out.print(appendParams);
/* 3386 */                                             out.write("&resourceid=");
/* 3387 */                                             if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3389 */                                             out.write("&type=");
/* 3390 */                                             out.print(rtype);
/* 3391 */                                             if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3393 */                                             out.write("&resourcename=");
/* 3394 */                                             out.print(resourceName);
/* 3395 */                                             out.write("&mtype=");
/* 3396 */                                             out.print(mon_type);
/* 3397 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                           }
/*      */                                           
/*      */                                         }
/*      */                                         catch (NumberFormatException ne)
/*      */                                         {
/* 3403 */                                           if (rtype.equals("Ping Monitor")) {
/* 3404 */                                             out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                           } else {
/* 3406 */                                             out.write("\n\n\n<a href=\"/updateScript.do?method=editScript");
/* 3407 */                                             out.print(appendParams);
/* 3408 */                                             out.write("&resourceid=");
/* 3409 */                                             if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3411 */                                             out.write("&type=");
/* 3412 */                                             out.print(rtype);
/* 3413 */                                             if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3415 */                                             out.write("&resourcename=");
/* 3416 */                                             out.print(resourceName);
/* 3417 */                                             out.write("&mtype=");
/* 3418 */                                             out.print(mon_type);
/* 3419 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 3423 */                                         out.write(10);
/* 3424 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 3425 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3429 */                                     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 3430 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                                     }
/*      */                                     
/* 3433 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 3434 */                                     out.write("\n\n    ");
/* 3435 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3436 */                                     out.write("\n    ");
/* 3437 */                                     if (_jspx_meth_c_005fif_005f18(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                       return;
/* 3439 */                                     out.write("</td>\n");
/* 3440 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 3441 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3445 */                                 if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 3446 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 3449 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 3450 */                                 out.write("\n  </tr>\n  ");
/* 3451 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3452 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3456 */                             if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3457 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                             }
/*      */                             
/* 3460 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3461 */                             out.write("\n\n  ");
/*      */                             
/* 3463 */                             IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3464 */                             _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3465 */                             _jspx_th_c_005fif_005f19.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3467 */                             _jspx_th_c_005fif_005f19.setTest("${!empty ADMIN || !empty DEMO }");
/* 3468 */                             int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3469 */                             if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                               for (;;) {
/* 3471 */                                 out.write(10);
/*      */                                 
/* 3473 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3474 */                                 _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 3475 */                                 _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3477 */                                 _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 3478 */                                 int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 3479 */                                 if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3481 */                                     out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 3482 */                                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3483 */                                     out.write("</a></td>\n  </tr>\n");
/* 3484 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 3485 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3489 */                                 if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 3490 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3493 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 3494 */                                 out.write(10);
/*      */                                 
/* 3496 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3497 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3498 */                                 _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3500 */                                 _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 3501 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3502 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;) {
/* 3504 */                                     out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3505 */                                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3506 */                                     out.write("</a></td>\n\n");
/* 3507 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3508 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3512 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3513 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                 }
/*      */                                 
/* 3516 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3517 */                                 out.write("\n\n  ");
/* 3518 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3519 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3523 */                             if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3524 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                             }
/*      */                             
/* 3527 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3528 */                             out.write(10);
/* 3529 */                             out.write(32);
/* 3530 */                             out.write(32);
/*      */                             
/* 3532 */                             IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3533 */                             _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3534 */                             _jspx_th_c_005fif_005f20.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3536 */                             _jspx_th_c_005fif_005f20.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 3537 */                             int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3538 */                             if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                               for (;;) {
/* 3540 */                                 out.write("\n\n  <tr>\n\n  ");
/*      */                                 
/* 3542 */                                 if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                 {
/*      */ 
/* 3545 */                                   out.write(10);
/* 3546 */                                   out.write(9);
/*      */                                   
/* 3548 */                                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3549 */                                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3550 */                                   _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3552 */                                   _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 3553 */                                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3554 */                                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 3556 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3557 */                                       out.print(FormatUtil.getString("Manage"));
/* 3558 */                                       out.write("</a> </td>\n  ");
/* 3559 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3560 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3564 */                                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3565 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 3568 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3569 */                                   out.write("\n        ");
/*      */                                   
/* 3571 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3572 */                                   _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 3573 */                                   _jspx_th_logic_005fnotPresent_005f6.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3575 */                                   _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 3576 */                                   int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 3577 */                                   if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 3579 */                                       out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 3580 */                                       out.print(FormatUtil.getString("Manage"));
/* 3581 */                                       out.write("</A></td>\n");
/* 3582 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 3583 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3587 */                                   if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 3588 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                                   }
/*      */                                   
/* 3591 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 3592 */                                   out.write("\n    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3598 */                                   out.write(10);
/*      */                                   
/* 3600 */                                   PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3601 */                                   _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3602 */                                   _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3604 */                                   _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 3605 */                                   int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3606 */                                   if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                     for (;;) {
/* 3608 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3609 */                                       out.print(FormatUtil.getString("UnManage"));
/* 3610 */                                       out.write("</a> </td>\n  ");
/* 3611 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3612 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3616 */                                   if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3617 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                   }
/*      */                                   
/* 3620 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3621 */                                   out.write("\n        ");
/*      */                                   
/* 3623 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3624 */                                   _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 3625 */                                   _jspx_th_logic_005fnotPresent_005f7.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3627 */                                   _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 3628 */                                   int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 3629 */                                   if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 3631 */                                       out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 3632 */                                       out.print(FormatUtil.getString("UnManage"));
/* 3633 */                                       out.write("</A></td>\n");
/* 3634 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 3635 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3639 */                                   if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 3640 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 3643 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 3644 */                                   out.write("\n    ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3648 */                                 out.write("\n  </tr>\n  ");
/* 3649 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3650 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3654 */                             if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3655 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                             }
/*      */                             
/* 3658 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3659 */                             out.write("\n   ");
/*      */                             
/* 3661 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3662 */                             _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 3663 */                             _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3665 */                             _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 3666 */                             int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 3667 */                             if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                               for (;;) {
/* 3669 */                                 out.write("\n    ");
/*      */                                 
/* 3671 */                                 String resourceid_poll = request.getParameter("resourceid");
/* 3672 */                                 String resourcetype_poll = request.getParameter("type");
/*      */                                 
/* 3674 */                                 out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3675 */                                 out.print(resourceid_poll);
/* 3676 */                                 out.write("&baseid=");
/* 3677 */                                 out.print(baseid);
/* 3678 */                                 out.write("&resourcetype=");
/* 3679 */                                 out.print(resourcetype_poll);
/* 3680 */                                 out.write("\" class=\"new-left-links\"> ");
/* 3681 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3682 */                                 out.write("</a></td>\n    </tr>\n    ");
/* 3683 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 3684 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3688 */                             if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 3689 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8); return;
/*      */                             }
/*      */                             
/* 3692 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 3693 */                             out.write("\n    ");
/*      */                             
/* 3695 */                             PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3696 */                             _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3697 */                             _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3699 */                             _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 3700 */                             int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3701 */                             if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                               for (;;) {
/* 3703 */                                 out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3704 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3705 */                                 out.write("</a></td>\n          </td>\n    ");
/* 3706 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3707 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3711 */                             if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3712 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                             }
/*      */                             
/* 3715 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3716 */                             out.write("\n    ");
/* 3717 */                             out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                             
/* 3719 */                             if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                             {
/* 3721 */                               Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3722 */                               String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                               
/* 3724 */                               String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3725 */                               String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3726 */                               if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                               {
/* 3728 */                                 if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                                 {
/*      */ 
/* 3731 */                                   out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3732 */                                   out.print(ciInfoUrl);
/* 3733 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3734 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3735 */                                   out.write("</a></td>");
/* 3736 */                                   out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3737 */                                   out.print(ciRLUrl);
/* 3738 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3739 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3740 */                                   out.write("</a></td>");
/* 3741 */                                   out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                                 }
/* 3745 */                                 else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                                 {
/*      */ 
/* 3748 */                                   out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3749 */                                   out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3750 */                                   out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3751 */                                   out.print(ciInfoUrl);
/* 3752 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3753 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3754 */                                   out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3755 */                                   out.print(ciRLUrl);
/* 3756 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3757 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3758 */                                   out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/* 3763 */                             out.write("\n \n \n\n");
/* 3764 */                             out.write("\n</table>\n\n <br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3765 */                             out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3766 */                             out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3767 */                             if (_jspx_meth_c_005fout_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3769 */                             out.write("&attributeid=");
/* 3770 */                             out.print(healthid);
/* 3771 */                             out.write("')\"\n      class=\"new-left-links\">");
/* 3772 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3773 */                             out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3774 */                             if (_jspx_meth_c_005fout_005f25(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3776 */                             out.write("&attributeid=");
/* 3777 */                             out.print(healthid);
/* 3778 */                             out.write("')\">");
/* 3779 */                             out.print(getSeverityImageForHealth(alert.getProperty(request.getParameter("resourceid") + "#" + healthid)));
/* 3780 */                             out.write("</a></td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3781 */                             if (_jspx_meth_c_005fout_005f26(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3783 */                             out.write("&attributeid=");
/* 3784 */                             out.print(availabilityid);
/* 3785 */                             out.write("')\"\n      class=\"new-left-links\">");
/* 3786 */                             out.print(FormatUtil.getString("Availability"));
/* 3787 */                             out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3788 */                             if (_jspx_meth_c_005fout_005f27(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3790 */                             out.write("&attributeid=");
/* 3791 */                             out.print(availabilityid);
/* 3792 */                             out.write("')\">");
/* 3793 */                             out.print(getSeverityImageForAvailability(alert.getProperty(request.getParameter("resourceid") + "#" + availabilityid)));
/* 3794 */                             out.write("</a></td>\n  </tr>\n</table>\n\n<br>\n\n<br>\n");
/* 3795 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                             
/*      */ 
/*      */ 
/* 3799 */                             boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3800 */                             if (EnterpriseUtil.isIt360MSPEdition)
/*      */                             {
/* 3802 */                               showAssociatedBSG = false;
/*      */                               
/*      */ 
/*      */ 
/* 3806 */                               CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3807 */                               CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3808 */                               String loginName = request.getUserPrincipal().getName();
/* 3809 */                               CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                               
/* 3811 */                               if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                               {
/* 3813 */                                 showAssociatedBSG = true;
/*      */                               }
/*      */                             }
/* 3816 */                             String monitorType = request.getParameter("type");
/* 3817 */                             ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3818 */                             boolean mon = conf1.isConfMonitor(monitorType);
/* 3819 */                             if (showAssociatedBSG)
/*      */                             {
/* 3821 */                               Hashtable associatedmgs = new Hashtable();
/* 3822 */                               String resId = request.getParameter("resourceid");
/* 3823 */                               request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3824 */                               if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                               {
/* 3826 */                                 mon = false;
/*      */                               }
/*      */                               
/* 3829 */                               if (!mon)
/*      */                               {
/* 3831 */                                 out.write(10);
/* 3832 */                                 out.write(10);
/*      */                                 
/* 3834 */                                 IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3835 */                                 _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 3836 */                                 _jspx_th_c_005fif_005f21.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3838 */                                 _jspx_th_c_005fif_005f21.setTest("${!empty associatedmgs}");
/* 3839 */                                 int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 3840 */                                 if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                   for (;;) {
/* 3842 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3843 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3844 */                                     out.write("</td>\n        </tr>\n        ");
/*      */                                     
/* 3846 */                                     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3847 */                                     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3848 */                                     _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f21);
/*      */                                     
/* 3850 */                                     _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                                     
/* 3852 */                                     _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                                     
/* 3854 */                                     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3855 */                                     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                     try {
/* 3857 */                                       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3858 */                                       if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                         for (;;) {
/* 3860 */                                           out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3861 */                                           if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3919 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3920 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3863 */                                           out.write("&method=showApplication\" title=\"");
/* 3864 */                                           if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3919 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3920 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3866 */                                           out.write("\"  class=\"new-left-links\">\n         ");
/* 3867 */                                           if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3919 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3920 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3869 */                                           out.write("\n    \t");
/* 3870 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3871 */                                           out.write("\n         </a></td>\n        <td>");
/*      */                                           
/* 3873 */                                           PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3874 */                                           _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3875 */                                           _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                           
/* 3877 */                                           _jspx_th_logic_005fpresent_005f10.setRole("ADMIN");
/* 3878 */                                           int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3879 */                                           if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                             for (;;) {
/* 3881 */                                               out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3882 */                                               if (_jspx_meth_c_005fout_005f31(_jspx_th_logic_005fpresent_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3919 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3920 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3884 */                                               out.write(39);
/* 3885 */                                               out.write(44);
/* 3886 */                                               out.write(39);
/* 3887 */                                               out.print(resId);
/* 3888 */                                               out.write(39);
/* 3889 */                                               out.write(44);
/* 3890 */                                               out.write(39);
/* 3891 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3892 */                                               out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3893 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3894 */                                               out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3895 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 3896 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3900 */                                           if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 3901 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3919 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3920 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3904 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 3905 */                                           out.write("</td>\n        </tr>\n\t");
/* 3906 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3907 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3911 */                                       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3919 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3920 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 3915 */                                         int tmp10353_10352 = 0; int[] tmp10353_10350 = _jspx_push_body_count_c_005fforEach_005f0; int tmp10355_10354 = tmp10353_10350[tmp10353_10352];tmp10353_10350[tmp10353_10352] = (tmp10355_10354 - 1); if (tmp10355_10354 <= 0) break;
/* 3916 */                                         out = _jspx_page_context.popBody(); }
/* 3917 */                                       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 3919 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3920 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                     }
/* 3922 */                                     out.write("\n      </table>\n ");
/* 3923 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 3924 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3928 */                                 if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 3929 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                                 }
/*      */                                 
/* 3932 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 3933 */                                 out.write(10);
/* 3934 */                                 out.write(32);
/*      */                                 
/* 3936 */                                 IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3937 */                                 _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 3938 */                                 _jspx_th_c_005fif_005f22.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3940 */                                 _jspx_th_c_005fif_005f22.setTest("${empty associatedmgs}");
/* 3941 */                                 int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 3942 */                                 if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                   for (;;) {
/* 3944 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3945 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3946 */                                     out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3947 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3948 */                                     out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3949 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 3950 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3954 */                                 if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 3955 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                                 }
/*      */                                 
/* 3958 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 3959 */                                 out.write(10);
/* 3960 */                                 out.write(32);
/* 3961 */                                 out.write(10);
/*      */ 
/*      */                               }
/* 3964 */                               else if (mon)
/*      */                               {
/*      */ 
/*      */ 
/* 3968 */                                 out.write(10);
/*      */                                 
/* 3970 */                                 IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3971 */                                 _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 3972 */                                 _jspx_th_c_005fif_005f23.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3974 */                                 _jspx_th_c_005fif_005f23.setTest("${!empty associatedmgs}");
/* 3975 */                                 int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 3976 */                                 if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                   for (;;) {
/* 3978 */                                     out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 3979 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                       return;
/* 3981 */                                     out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                     
/* 3983 */                                     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3984 */                                     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3985 */                                     _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f23);
/*      */                                     
/* 3987 */                                     _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                     
/* 3989 */                                     _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                     
/* 3991 */                                     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3992 */                                     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                     try {
/* 3994 */                                       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3995 */                                       if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                         for (;;) {
/* 3997 */                                           out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3998 */                                           if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4059 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4060 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4000 */                                           out.write("&method=showApplication\" title=\"");
/* 4001 */                                           if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4059 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4060 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4003 */                                           out.write("\"  class=\"staticlinks\">\n         ");
/* 4004 */                                           if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4059 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4060 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4006 */                                           out.write("\n    \t");
/* 4007 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4008 */                                           out.write("</a></span>\t\n\t\t ");
/*      */                                           
/* 4010 */                                           PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4011 */                                           _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 4012 */                                           _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                           
/* 4014 */                                           _jspx_th_logic_005fpresent_005f11.setRole("ADMIN");
/* 4015 */                                           int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 4016 */                                           if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                             for (;;) {
/* 4018 */                                               out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 4019 */                                               if (_jspx_meth_c_005fout_005f35(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4059 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4060 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 4021 */                                               out.write(39);
/* 4022 */                                               out.write(44);
/* 4023 */                                               out.write(39);
/* 4024 */                                               out.print(resId);
/* 4025 */                                               out.write(39);
/* 4026 */                                               out.write(44);
/* 4027 */                                               out.write(39);
/* 4028 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4029 */                                               out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 4030 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4031 */                                               out.write("\"  title=\"");
/* 4032 */                                               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 4059 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4060 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 4034 */                                               out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 4035 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 4036 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4040 */                                           if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 4041 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4059 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4060 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4044 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4045 */                                           out.write("\n\n\t\t \t");
/* 4046 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4047 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4051 */                                       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4059 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4060 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 4055 */                                         int tmp11379_11378 = 0; int[] tmp11379_11376 = _jspx_push_body_count_c_005fforEach_005f1; int tmp11381_11380 = tmp11379_11376[tmp11379_11378];tmp11379_11376[tmp11379_11378] = (tmp11381_11380 - 1); if (tmp11381_11380 <= 0) break;
/* 4056 */                                         out = _jspx_page_context.popBody(); }
/* 4057 */                                       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 4059 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4060 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     }
/* 4062 */                                     out.write("\n\t\n\t\t\t</td>\n\t ");
/* 4063 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 4064 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4068 */                                 if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 4069 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                                 }
/*      */                                 
/* 4072 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 4073 */                                 out.write(10);
/* 4074 */                                 out.write(32);
/* 4075 */                                 if (_jspx_meth_c_005fif_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 4077 */                                 out.write(32);
/* 4078 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */                             }
/* 4082 */                             else if (mon)
/*      */                             {
/*      */ 
/* 4085 */                               out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4086 */                               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 4088 */                               out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 4092 */                             out.write(9);
/* 4093 */                             out.write(9);
/* 4094 */                             out.write(10);
/*      */                             
/* 4096 */                             ArrayList menupos = new ArrayList(5);
/* 4097 */                             menupos.add("350");
/* 4098 */                             pageContext.setAttribute("menupos", menupos);
/*      */                             
/* 4100 */                             out.write(10);
/* 4101 */                             out.write("\n                         ");
/* 4102 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4103 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4106 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4107 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4110 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4111 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 4114 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4115 */                         out.write(10);
/* 4116 */                         out.write(10);
/*      */                         
/* 4118 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4119 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4120 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 4122 */                         _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                         
/* 4124 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 4125 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4126 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 4127 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4128 */                             out = _jspx_page_context.pushBody();
/* 4129 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 4130 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4133 */                             out.write("\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n");
/*      */                             
/* 4135 */                             String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 4136 */                             String yaxis_restime = FormatUtil.getString("am.webclient.common.responsetime.text") + " " + FormatUtil.getString("am.webclient.common.units.ms.text");
/* 4137 */                             String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 4138 */                             String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/* 4139 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 4140 */                             String aid = request.getParameter("haid");
/* 4141 */                             String haName = null;
/* 4142 */                             Hashtable pingData = (Hashtable)request.getAttribute("pingData");
/* 4143 */                             if (aid != null)
/*      */                             {
/* 4145 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 4148 */                             out.write("\n        ");
/*      */                             
/* 4150 */                             IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4151 */                             _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 4152 */                             _jspx_th_c_005fif_005f25.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4154 */                             _jspx_th_c_005fif_005f25.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4155 */                             int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 4156 */                             if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                               for (;;) {
/* 4158 */                                 out.write("\n              <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4159 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 4160 */                                 out.write(" &gt; ");
/* 4161 */                                 out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 4162 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 4163 */                                 out.print(getTrimmedText((String)request.getAttribute("moname"), 35));
/* 4164 */                                 out.write(" </span></td>\n                      ");
/* 4165 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 4166 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4170 */                             if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 4171 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                             }
/*      */                             
/* 4174 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 4175 */                             out.write("\n                      ");
/* 4176 */                             String type = request.getParameter("type");
/*      */                             
/* 4178 */                             out.write("\n                              ");
/*      */                             
/* 4180 */                             IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4181 */                             _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 4182 */                             _jspx_th_c_005fif_005f26.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4184 */                             _jspx_th_c_005fif_005f26.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4185 */                             int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 4186 */                             if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                               for (;;) {
/* 4188 */                                 out.write("\n                                 <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4189 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 4190 */                                 out.write(" &gt; ");
/* 4191 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes("Ping Monitor"));
/* 4192 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 4193 */                                 out.print(getTrimmedText(resourceName, 35));
/* 4194 */                                 out.write("</span></td>\n                                         ");
/* 4195 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 4196 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4200 */                             if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 4201 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                             }
/*      */                             
/* 4204 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 4205 */                             out.write("\n                                             </tr>\n</table>\n\n<div id=\"edit\" style=\"DISPLAY: none\">\n");
/* 4206 */                             JspRuntimeLibrary.include(request, response, "/jsp/pingConfig.jsp?reconfigure=true&configured=true", out, false);
/* 4207 */                             out.write("\n<br>\n</div>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n <td width=\"55%\" height=\"123\" valign=\"top\"> <table width=\"96%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n  <tr>\n         <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 4208 */                             out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 4209 */                             out.write(" </td>\n        </tr>\n        <tr>\n         <td width=\"32%\" class=\"monitorinfoodd\">");
/* 4210 */                             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4211 */                             out.write(" </td>\n         <td width=\"68%\" class=\"monitorinfoodd\"> ");
/* 4212 */                             out.print(getTrimmedText(displayname, 35));
/* 4213 */                             out.write("\n          </td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 4214 */                             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 4215 */                             out.write(" </td>\n          <td class=\"monitorinfoodd\">");
/* 4216 */                             out.print(FormatUtil.getString("Ping Monitor"));
/* 4217 */                             out.write("</td>\n        </tr>\n        ");
/* 4218 */                             out.write("<!--$Id$-->\n");
/*      */                             
/* 4220 */                             String hostName = "localhost";
/*      */                             try {
/* 4222 */                               hostName = InetAddress.getLocalHost().getHostName();
/*      */                             } catch (Exception ex) {
/* 4224 */                               ex.printStackTrace();
/*      */                             }
/* 4226 */                             String portNumber = System.getProperty("webserver.port");
/* 4227 */                             String styleClass = "monitorinfoodd";
/* 4228 */                             if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 4229 */                               styleClass = "whitegrayborder-conf-mon";
/*      */                             }
/*      */                             
/* 4232 */                             out.write(10);
/*      */                             
/* 4234 */                             PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4235 */                             _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 4236 */                             _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4238 */                             _jspx_th_logic_005fpresent_005f12.setRole("ENTERPRISEADMIN");
/* 4239 */                             int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 4240 */                             if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                               for (;;) {
/* 4242 */                                 out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 4243 */                                 out.print(styleClass);
/* 4244 */                                 out.write(34);
/* 4245 */                                 out.write(62);
/* 4246 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 4247 */                                 out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 4248 */                                 out.print(styleClass);
/* 4249 */                                 out.write(34);
/* 4250 */                                 out.write(62);
/* 4251 */                                 out.print(hostName);
/* 4252 */                                 out.write(95);
/* 4253 */                                 out.print(portNumber);
/* 4254 */                                 out.write("</td>\n</tr>\n");
/* 4255 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 4256 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4260 */                             if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 4261 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                             }
/*      */                             
/* 4264 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 4265 */                             out.write(10);
/* 4266 */                             out.write("\n        <tr>\n        <td class=\"monitorinfoeven\" valign=\"top\">");
/* 4267 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4268 */                             out.write("</\ntd>\n       <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4269 */                             out.print(resID);
/* 4270 */                             out.write("&attributeid=9001')\">");
/* 4271 */                             out.print(getSeverityImageForHealth(alert.getProperty(resID + "#" + 9001)));
/* 4272 */                             out.write("</a>\n               ");
/* 4273 */                             out.print(getHideAndShowRCAMessage(alert.getProperty(resID + "#" + 9001 + "#" + "MESSAGE"), "9001", alert.getProperty(resID + "#" + 9001), resID));
/* 4274 */                             out.write("\n               ");
/* 4275 */                             if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resID, "9001") != 0) {
/* 4276 */                               out.write("\n               <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 4277 */                               out.print(resID + "_9001");
/* 4278 */                               out.write("&monitortype=");
/* 4279 */                               out.print(resourcetype);
/* 4280 */                               out.write("')\">");
/* 4281 */                               out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 4282 */                               out.write("</a></span>\n              ");
/*      */                             }
/* 4284 */                             out.write("   \n        </td>\n</tr>\n                <tr>\n                <td class=\"monitorinfoodd\" valign=\"top\">");
/* 4285 */                             out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 4286 */                             out.write("</td>\n                <td class=\"monitorinfoodd\" title=\"");
/* 4287 */                             out.print(monitorname);
/* 4288 */                             out.write(34);
/* 4289 */                             out.write(62);
/* 4290 */                             out.print(getTrimmedText(monitorname, 30));
/* 4291 */                             out.write("</td>\n\n\t\t </tr>\n\n\n        ");
/*      */                             
/* 4293 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4294 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 4295 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4297 */                             _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 4298 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 4299 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 4301 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 4302 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4303 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 4304 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4305 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        ");
/* 4306 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 4307 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4311 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 4312 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 4315 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 4316 */                             out.write("\n        ");
/*      */                             
/* 4318 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4319 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 4320 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4322 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 4323 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 4324 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 4326 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 4327 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4328 */                                 out.write("</td>\n                ");
/* 4329 */                                 if (systeminfo.get("LASTDC") != null) {
/* 4330 */                                   out.write("\n          <td class=\"monitorinfoeven\">");
/* 4331 */                                   out.print(formatDT(((Long)systeminfo.get("LASTDC")).toString()));
/* 4332 */                                   out.write("</td>\n                ");
/*      */                                 } else {
/* 4334 */                                   out.write("\n\n\t\t<td class=\"monitorinfoeven\">");
/* 4335 */                                   out.print("-");
/* 4336 */                                   out.write("</td>\n                ");
/*      */                                 }
/* 4338 */                                 out.write("\n\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 4339 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4340 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 4341 */                                 out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 4342 */                                 out.write("</td>\n        </tr>\n        ");
/* 4343 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 4344 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4348 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 4349 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 4352 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 4353 */                             out.write("\n        ");
/* 4354 */                             JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 4355 */                             out.write("\n    </table>\n\n</td>\n  <td width=\"43%\" valign=\"top\"><table align=left width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n          <td height=\"28\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 4356 */                             out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 4357 */                             out.write("</td>\n        </tr>\n <tr>\n          <td colspan=\"3\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">                                                            <tr>\n            <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4358 */                             if (_jspx_meth_c_005fout_005f36(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4360 */                             out.write("&period=1&resourcename=");
/* 4361 */                             if (_jspx_meth_c_005fout_005f37(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4363 */                             out.write("')\">\n                                                                                                                              <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4364 */                             out.print(seven_days_text);
/* 4365 */                             out.write("\"></a></td>\n                            <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4366 */                             if (_jspx_meth_c_005fout_005f38(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4368 */                             out.write("&period=2&resourcename=");
/* 4369 */                             if (_jspx_meth_c_005fout_005f39(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4371 */                             out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4372 */                             out.print(thiry_days_text);
/* 4373 */                             out.write("\"\n></a></td>                                                                                                                                </tr>\n            </table></td>                                                                                                             ");
/*      */                             
/* 4375 */                             wlsGraph.setParam(resID, "AVAILABILITY");
/* 4376 */                             out.write("\n\n </tr>                                                                                                                       <tr align=\"center\">\n          <td height=\"28\" colspan=\"3\" class=\"whitegrayborder\"> ");
/* 4377 */                             if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4379 */                             out.write("</td>\n\t\t          </tr>                                                                                                                       <tr>\n\t\t        <td width=\"49%\" height=\"25\" class=\"yellowgrayborder\"  title=\"");
/* 4380 */                             out.print(tipCurStatus);
/* 4381 */                             out.write(34);
/* 4382 */                             out.write(62);
/* 4383 */                             out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 4384 */                             out.write(" :\n\t\t       <a style=\"position:relative; top:2px;\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4385 */                             out.print(resID);
/* 4386 */                             out.write("&attributeid=");
/* 4387 */                             out.print(9000);
/* 4388 */                             out.write("&alertconfigurl=");
/* 4389 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "&attributeIDs=" + 9000 + "&attributeToSelect=" + 9000 + "&redirectto=" + encodeurl));
/* 4390 */                             out.write("')\">\n\t\t              ");
/* 4391 */                             out.print(getSeverityImageForAvailability(alert.getProperty(resID + "#" + 9000)));
/* 4392 */                             out.write("                                      </a></td>\n\t\t         <td width=\"51%\" align=\"right\" class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4393 */                             out.print(resID);
/* 4394 */                             out.write("&attributeIDs=");
/* 4395 */                             out.print(9000);
/* 4396 */                             out.write(44);
/* 4397 */                             out.print(9001);
/* 4398 */                             out.write("&attributeToSelect=");
/* 4399 */                             out.print(9000);
/* 4400 */                             out.write("&redirectto=");
/* 4401 */                             out.print(encodeurl);
/* 4402 */                             out.write("\" class=\"links\">");
/* 4403 */                             out.print(ALERTCONFIG_TEXT);
/* 4404 */                             out.write("</a>\n          </td>\n</tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 4405 */                             JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 4406 */                             out.write("</td></tr></table>\n<table border=0>\n<tr><td>&nbsp;</td></tr>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n <td height=\"26\" width = \"52%\" align=\"left\" class=\"tableheading\" >");
/* 4407 */                             out.print(FormatUtil.getString("Packet Statistics"));
/* 4408 */                             out.write("</td>\n\t<td  height=\"26\" class=\"tableheading\">");
/* 4409 */                             out.print(FormatUtil.getString("Round Trip Time"));
/* 4410 */                             out.write(" </td>\n  </tr>\n</table>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n <tr>\n          <td width=\"52%\" height=\"35\" align=\"right\" class=\"rborder\">&nbsp;  </td>\n\n<td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4411 */                             out.print(resID);
/* 4412 */                             out.write("&attributeid=9003&period=-7',740,550)\">                             <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4413 */                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4414 */                             out.write("\"></a>\n      <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4415 */                             out.print(resID);
/* 4416 */                             out.write("&attributeid=9003&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4417 */                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4418 */                             out.write("\"></a></td>\n\n\n        </tr>\n\n\n <tr>\n");
/* 4419 */                             camGraph.setParam(9002, "Ping Monitor", "Packet Loss", Integer.parseInt(resID));
/* 4420 */                             out.write("\n<td  align=\"center\" class=\"rbborder\"> ");
/*      */                             
/* 4422 */                             TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4423 */                             _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 4424 */                             _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4426 */                             _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("camGraph");
/*      */                             
/* 4428 */                             _jspx_th_awolf_005ftimechart_005f0.setWidth("320");
/*      */                             
/* 4430 */                             _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                             
/* 4432 */                             _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                             
/* 4434 */                             _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                             
/* 4436 */                             _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.ping.packloss"));
/*      */                             
/* 4438 */                             _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 4439 */                             int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 4440 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 4441 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4442 */                                 out = _jspx_page_context.pushBody();
/* 4443 */                                 _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 4444 */                                 _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4447 */                                 out.write("\n            ");
/* 4448 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 4449 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4452 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4453 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4456 */                             if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 4457 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                             }
/*      */                             
/* 4460 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 4461 */                             out.write(" </td>\n");
/* 4462 */                             camGraph.setParam(9003, "Ping Monitor", "Round Trip Time", Integer.parseInt(resID));
/* 4463 */                             out.write("\n<td  align=\"center\" class=\"bottomborder\"> ");
/*      */                             
/* 4465 */                             TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4466 */                             _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 4467 */                             _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4469 */                             _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("camGraph");
/*      */                             
/* 4471 */                             _jspx_th_awolf_005ftimechart_005f1.setWidth("320");
/*      */                             
/* 4473 */                             _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                             
/* 4475 */                             _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                             
/* 4477 */                             _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                             
/* 4479 */                             _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.ping.rtt"));
/*      */                             
/* 4481 */                             _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 4482 */                             int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 4483 */                             if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 4484 */                               if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 4485 */                                 out = _jspx_page_context.pushBody();
/* 4486 */                                 _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 4487 */                                 _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4490 */                                 out.write(10);
/* 4491 */                                 out.write(10);
/* 4492 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 4493 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4496 */                               if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 4497 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4500 */                             if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 4501 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                             }
/*      */                             
/* 4504 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 4505 */                             out.write("\n\n\n</td>\n\n</tr>\n<tr>\n<td >\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"rborder\" >\n        <tr>\n      <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4506 */                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4508 */                             out.write("</span></td>\n      <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4509 */                             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4511 */                             out.write("</span></td>\n      <td class=\"columnheadingnotop\"><span class=\"bodytextbold\" colspan=\"2\">");
/* 4512 */                             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4514 */                             out.write("</span>\n");
/* 4515 */                             if ((pingData != null) && (pingData.size() != 0))
/*      */                             {
/* 4517 */                               String packsent = (String)pingData.get("packsent");
/* 4518 */                               String packrecvd = (String)pingData.get("packrecvd");
/* 4519 */                               String packloss = (String)pingData.get("packloss");
/*      */                               
/*      */ 
/* 4522 */                               out.write("\n<tr>\n<td height=\"25\" class=\"whitegrayborder\">");
/* 4523 */                               out.print(FormatUtil.getString("am.webclient.ping.packloss"));
/* 4524 */                               out.write("</td>\n");
/* 4525 */                               if (packloss.equals("-1"))
/*      */                               {
/* 4527 */                                 packloss = "-";
/*      */                                 
/* 4529 */                                 out.write("\n<td height=\"25\" class=\"whitegrayborder\">");
/* 4530 */                                 out.print(packloss);
/* 4531 */                                 out.write("</td>\n");
/*      */                               } else {
/* 4533 */                                 out.write("\n\n<td height=\"25\" class=\"whitegrayborder\">");
/* 4534 */                                 out.print(Integer.parseInt(packloss));
/* 4535 */                                 out.write("</td>\n");
/*      */                               }
/* 4537 */                               out.write("\n<td height=\"25\" class=\"whitegrayborder\"> &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4538 */                               out.print(resID);
/* 4539 */                               out.write("&attributeid=9002&alertconfigurl=");
/* 4540 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=9002&attributeToSelect=9002&redirectto=" + encodeurl));
/* 4541 */                               out.write("')\">");
/* 4542 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + 9002)));
/* 4543 */                               out.write(" </a>\n<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4544 */                               out.print(resID);
/* 4545 */                               out.write("&attributeIDs=9002&attributeToSelect=9002&redirectto=");
/* 4546 */                               out.print(encodeurl);
/* 4547 */                               out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=0 align=\"absmiddle\"></a>&nbsp;</td>\n</tr>\n");
/* 4548 */                               if (packsent.equals("-1"))
/* 4549 */                                 packsent = "-";
/* 4550 */                               if (packrecvd.equals("-1")) {
/* 4551 */                                 packrecvd = "-";
/*      */                               }
/*      */                               
/*      */ 
/* 4555 */                               out.write("\n\n<tr>\n<td height=\"25\" class=\"yellowgrayborder\">");
/* 4556 */                               out.print(FormatUtil.getString("am.webclient.ping.packsent"));
/* 4557 */                               out.write("</td>\n<td height=\"25\" class=\"yellowgrayborder\">");
/* 4558 */                               out.print(packsent);
/* 4559 */                               out.write("</td>\n<Td width=\"16%\" class=\"yellowgrayborder\">  </td>\n</tr>\n<tr>\n<td height=\"25\" class=\"whitegrayborder\">");
/* 4560 */                               out.print(FormatUtil.getString("am.webclient.ping.packrecvd"));
/* 4561 */                               out.write("</td>\n<td height=\"25\" class=\"whitegrayborder\">");
/* 4562 */                               out.print(packrecvd);
/* 4563 */                               out.write("</td>\n<Td width=\"16%\" class=\"whitegrayborder\">  </td>\n</tr>\n\n\n\n\n");
/*      */                             } else {
/* 4565 */                               out.write("\n\n<tr>\n<td height=\"25\" colspan=\"4\" class=\"whitegrayborder\">");
/* 4566 */                               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4567 */                               out.write("</td>\n</tr>\n");
/*      */                             }
/* 4569 */                             out.write("\n\n</table>\n<td>\n<table width=\"100%\" border=\"0\" valign = \"top\" cellspacing=\"0\" cellpadding=\"0\"  >\n        <tr>\n     <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4570 */                             if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4572 */                             out.write("</span></td>\n     <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4573 */                             if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4575 */                             out.write("</span></td>\n     <td class=\"columnheadingnotop\"><span class=\"bodytextbold\" colspan=\"2\">");
/* 4576 */                             if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4578 */                             out.write("</span>\n</td>\n</td>\n");
/* 4579 */                             if ((pingData != null) && (pingData.size() != 0)) {
/* 4580 */                               String rtt = (String)pingData.get("rtt");
/*      */                               
/* 4582 */                               out.write("\n\n<tr>                                                                                                           <td height=\"25\" class=\"whitegrayborder\">");
/* 4583 */                               out.print(FormatUtil.getString("am.webclient.ping.rtt"));
/* 4584 */                               out.write("</td>\n                                                                                                               <td height=\"25\" class=\"whitegrayborder\">");
/* 4585 */                               out.print(Integer.parseInt(rtt));
/* 4586 */                               out.write("</td>\n<td height=\"25\" class=\"whitegrayborder\"> &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4587 */                               out.print(resID);
/* 4588 */                               out.write("&attributeid=9003&alertconfigurl=");
/* 4589 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=9003&attributeToSelect=9003&redirectto=" + encodeurl));
/* 4590 */                               out.write("')\">");
/* 4591 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + 9003)));
/* 4592 */                               out.write(" </a>\n<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4593 */                               out.print(resID);
/* 4594 */                               out.write("&attributeIDs=9003&attributeToSelect=9003&redirectto=");
/* 4595 */                               out.print(encodeurl);
/* 4596 */                               out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" border=0 align=\"absmiddle\"></a>&nbsp;</\ntd>                                                                                                            </tr>\n<tr> <td colspan=\"3\" height=\"25\" class=\"yellowgrayborder\">&nbsp;</td><tr>\n<tr> <td height=\"25\"  class=\"whitegrayborder\">&nbsp;</td><tr>\n\n");
/*      */                             } else {
/* 4598 */                               out.write("\n\n<tr>\n<td height=\"25\" colspan=\"4\" class=\"whitegrayborder\">");
/* 4599 */                               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4600 */                               out.write("</td>\n</tr>\n");
/*      */                             }
/* 4602 */                             out.write("\n</td>\n </tr>\n</table></tr>\n</table>\n\n");
/* 4603 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 4604 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4607 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4608 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4611 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4612 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 4615 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 4616 */                         out.write(10);
/* 4617 */                         if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 4619 */                         out.write(10);
/* 4620 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4621 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 4625 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4626 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 4629 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4630 */                       out.write(10);
/*      */                     }
/* 4632 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4633 */         out = _jspx_out;
/* 4634 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4635 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 4636 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4639 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4645 */     PageContext pageContext = _jspx_page_context;
/* 4646 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4648 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4649 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4650 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4652 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4654 */     _jspx_th_tiles_005fput_005f0.setValue("Ping Monitor Details");
/* 4655 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4656 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4657 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4658 */       return true;
/*      */     }
/* 4660 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4666 */     PageContext pageContext = _jspx_page_context;
/* 4667 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4669 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4670 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4671 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4673 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4674 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4675 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4677 */         out.write(10);
/* 4678 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 4679 */           return true;
/* 4680 */         out.write(10);
/* 4681 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4682 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4686 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4687 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4688 */       return true;
/*      */     }
/* 4690 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4691 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4696 */     PageContext pageContext = _jspx_page_context;
/* 4697 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4699 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4700 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4701 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4703 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4705 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 4706 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4707 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4708 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4709 */       return true;
/*      */     }
/* 4711 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4712 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4717 */     PageContext pageContext = _jspx_page_context;
/* 4718 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4720 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4721 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4722 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4724 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4725 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4726 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4728 */         out.write(10);
/* 4729 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4730 */           return true;
/* 4731 */         out.write(10);
/* 4732 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4733 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4737 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4738 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4739 */       return true;
/*      */     }
/* 4741 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4742 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4747 */     PageContext pageContext = _jspx_page_context;
/* 4748 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4750 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4751 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4752 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4754 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 4756 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 4757 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4758 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4759 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4760 */       return true;
/*      */     }
/* 4762 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4763 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4768 */     PageContext pageContext = _jspx_page_context;
/* 4769 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4771 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4772 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4773 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4775 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 4776 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4777 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4778 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4779 */       return true;
/*      */     }
/* 4781 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4782 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4787 */     PageContext pageContext = _jspx_page_context;
/* 4788 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4790 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4791 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4792 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4794 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 4795 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4796 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4797 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4798 */       return true;
/*      */     }
/* 4800 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4801 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4806 */     PageContext pageContext = _jspx_page_context;
/* 4807 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4809 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4810 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4811 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4813 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 4814 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4815 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4816 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4817 */       return true;
/*      */     }
/* 4819 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4820 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4825 */     PageContext pageContext = _jspx_page_context;
/* 4826 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4828 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4829 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4830 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4832 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 4833 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4834 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4835 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4836 */       return true;
/*      */     }
/* 4838 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4839 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4844 */     PageContext pageContext = _jspx_page_context;
/* 4845 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4847 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4848 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 4849 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4851 */     _jspx_th_c_005fif_005f2.setTest("${!empty param.parentid}");
/* 4852 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 4853 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 4855 */         out.write("\n      ");
/* 4856 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4857 */           return true;
/* 4858 */         out.write("\n      ");
/* 4859 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 4860 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4864 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 4865 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4866 */       return true;
/*      */     }
/* 4868 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4874 */     PageContext pageContext = _jspx_page_context;
/* 4875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4877 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4878 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4879 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4881 */     _jspx_th_c_005fset_005f0.setVar("parentids");
/* 4882 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4883 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4884 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4885 */         out = _jspx_page_context.pushBody();
/* 4886 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4887 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4890 */         out.write("\n      &parentname=");
/* 4891 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 4892 */           return true;
/* 4893 */         out.write("&parentid=");
/* 4894 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 4895 */           return true;
/* 4896 */         out.write("\n      ");
/* 4897 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4898 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4901 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4902 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4905 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4906 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4907 */       return true;
/*      */     }
/* 4909 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4910 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4915 */     PageContext pageContext = _jspx_page_context;
/* 4916 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4918 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4919 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4920 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4922 */     _jspx_th_c_005fout_005f4.setValue("${param.parentname}");
/* 4923 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4924 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4925 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4926 */       return true;
/*      */     }
/* 4928 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4929 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4934 */     PageContext pageContext = _jspx_page_context;
/* 4935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4937 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4938 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4939 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4941 */     _jspx_th_c_005fout_005f5.setValue("${param.parentid}");
/* 4942 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4943 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4944 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4945 */       return true;
/*      */     }
/* 4947 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4948 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4953 */     PageContext pageContext = _jspx_page_context;
/* 4954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4956 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4957 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4958 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4960 */     _jspx_th_c_005fif_005f3.setTest("${empty param.parentid}");
/* 4961 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4962 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 4964 */         out.write("\n            ");
/* 4965 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 4966 */           return true;
/* 4967 */         out.write("\n      ");
/* 4968 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4969 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4973 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4974 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4975 */       return true;
/*      */     }
/* 4977 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4978 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4983 */     PageContext pageContext = _jspx_page_context;
/* 4984 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4986 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4987 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4988 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4990 */     _jspx_th_c_005fset_005f1.setVar("parentids");
/*      */     
/* 4992 */     _jspx_th_c_005fset_005f1.setValue("");
/* 4993 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4994 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4995 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4996 */       return true;
/*      */     }
/* 4998 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4999 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5004 */     PageContext pageContext = _jspx_page_context;
/* 5005 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5007 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5008 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5009 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5011 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.haid}");
/* 5012 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5013 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 5015 */         out.write(10);
/* 5016 */         out.write(9);
/* 5017 */         out.write(9);
/* 5018 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 5019 */           return true;
/* 5020 */         out.write(10);
/* 5021 */         out.write(9);
/* 5022 */         out.write(9);
/* 5023 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5024 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5028 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5029 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5030 */       return true;
/*      */     }
/* 5032 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5033 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5038 */     PageContext pageContext = _jspx_page_context;
/* 5039 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5041 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5042 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5043 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5045 */     _jspx_th_c_005fset_005f2.setVar("haid");
/* 5046 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5047 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5048 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5049 */         out = _jspx_page_context.pushBody();
/* 5050 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5051 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5054 */         out.write("\n\t\t&haid=");
/* 5055 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5056 */           return true;
/* 5057 */         out.write(10);
/* 5058 */         out.write(9);
/* 5059 */         out.write(9);
/* 5060 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5061 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5064 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5065 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5068 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5069 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5070 */       return true;
/*      */     }
/* 5072 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5073 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5078 */     PageContext pageContext = _jspx_page_context;
/* 5079 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5081 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5082 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5083 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5085 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 5086 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5087 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5088 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5089 */       return true;
/*      */     }
/* 5091 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5092 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5097 */     PageContext pageContext = _jspx_page_context;
/* 5098 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5100 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5101 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5102 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5104 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 5105 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5106 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5107 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5108 */       return true;
/*      */     }
/* 5110 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5111 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5116 */     PageContext pageContext = _jspx_page_context;
/* 5117 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5119 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5120 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5121 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5123 */     _jspx_th_c_005fif_005f7.setTest("${param.method=='editScript'}");
/* 5124 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5125 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5127 */         out.write("\n    </a>\n    ");
/* 5128 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5129 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5133 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5134 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5135 */       return true;
/*      */     }
/* 5137 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5138 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5143 */     PageContext pageContext = _jspx_page_context;
/* 5144 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5146 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5147 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5148 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 5150 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 5151 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5152 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5153 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5154 */       return true;
/*      */     }
/* 5156 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5157 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5162 */     PageContext pageContext = _jspx_page_context;
/* 5163 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5165 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5166 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5167 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5169 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 5170 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5171 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5172 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5173 */       return true;
/*      */     }
/* 5175 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5176 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5181 */     PageContext pageContext = _jspx_page_context;
/* 5182 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5184 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5185 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5186 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 5188 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 5189 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5190 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5191 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5192 */       return true;
/*      */     }
/* 5194 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5195 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5200 */     PageContext pageContext = _jspx_page_context;
/* 5201 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5203 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5204 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5205 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5207 */     _jspx_th_c_005fout_005f11.setValue("${param.haid}");
/* 5208 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5209 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5210 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5211 */       return true;
/*      */     }
/* 5213 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5214 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5219 */     PageContext pageContext = _jspx_page_context;
/* 5220 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5222 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5223 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5224 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5226 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 5227 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5228 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5229 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5230 */       return true;
/*      */     }
/* 5232 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5233 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5238 */     PageContext pageContext = _jspx_page_context;
/* 5239 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5241 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5242 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5243 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5245 */     _jspx_th_c_005fout_005f13.setValue("${parentids}");
/* 5246 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5247 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5248 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5249 */       return true;
/*      */     }
/* 5251 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5257 */     PageContext pageContext = _jspx_page_context;
/* 5258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5260 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5261 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5262 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5264 */     _jspx_th_c_005fout_005f14.setValue("${param.haid}");
/* 5265 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5266 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5267 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5268 */       return true;
/*      */     }
/* 5270 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5276 */     PageContext pageContext = _jspx_page_context;
/* 5277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5279 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5280 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5281 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5283 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 5284 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5285 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5286 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5287 */       return true;
/*      */     }
/* 5289 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5290 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5295 */     PageContext pageContext = _jspx_page_context;
/* 5296 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5298 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5299 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5300 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5302 */     _jspx_th_c_005fout_005f16.setValue("${parentids}");
/* 5303 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5304 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5305 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5306 */       return true;
/*      */     }
/* 5308 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5309 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5314 */     PageContext pageContext = _jspx_page_context;
/* 5315 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5317 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5318 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5319 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5321 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 5322 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5323 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5324 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5325 */       return true;
/*      */     }
/* 5327 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5328 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5333 */     PageContext pageContext = _jspx_page_context;
/* 5334 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5336 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5337 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5338 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5340 */     _jspx_th_c_005fout_005f18.setValue("${parentids}");
/* 5341 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5342 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5343 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5344 */       return true;
/*      */     }
/* 5346 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5347 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5352 */     PageContext pageContext = _jspx_page_context;
/* 5353 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5355 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5356 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5357 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5359 */     _jspx_th_c_005fout_005f19.setValue("${param.haid}");
/* 5360 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5361 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5362 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5363 */       return true;
/*      */     }
/* 5365 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5366 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5371 */     PageContext pageContext = _jspx_page_context;
/* 5372 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5374 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5375 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5376 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5378 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 5379 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5380 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5381 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5382 */       return true;
/*      */     }
/* 5384 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5390 */     PageContext pageContext = _jspx_page_context;
/* 5391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5393 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5394 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5395 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5397 */     _jspx_th_c_005fout_005f21.setValue("${parentids}");
/* 5398 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5399 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5400 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5401 */       return true;
/*      */     }
/* 5403 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5404 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5409 */     PageContext pageContext = _jspx_page_context;
/* 5410 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5412 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5413 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5414 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5416 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 5417 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5418 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5419 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5420 */       return true;
/*      */     }
/* 5422 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5423 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5428 */     PageContext pageContext = _jspx_page_context;
/* 5429 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5431 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5432 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5433 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5435 */     _jspx_th_c_005fout_005f23.setValue("${parentids}");
/* 5436 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5437 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5438 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5439 */       return true;
/*      */     }
/* 5441 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5447 */     PageContext pageContext = _jspx_page_context;
/* 5448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5450 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5451 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 5452 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 5454 */     _jspx_th_c_005fif_005f18.setTest("${param.actionmethod!='editScript'}");
/* 5455 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 5456 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 5458 */         out.write("\n    </a>\n    ");
/* 5459 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 5460 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5464 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 5465 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 5466 */       return true;
/*      */     }
/* 5468 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 5469 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5474 */     PageContext pageContext = _jspx_page_context;
/* 5475 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5477 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5478 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5479 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5481 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 5482 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5483 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5484 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5485 */       return true;
/*      */     }
/* 5487 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5488 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5493 */     PageContext pageContext = _jspx_page_context;
/* 5494 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5496 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5497 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5498 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5500 */     _jspx_th_c_005fout_005f25.setValue("${param.resourceid}");
/* 5501 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5502 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5503 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5504 */       return true;
/*      */     }
/* 5506 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5507 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5512 */     PageContext pageContext = _jspx_page_context;
/* 5513 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5515 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5516 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5517 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5519 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 5520 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5521 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5522 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5523 */       return true;
/*      */     }
/* 5525 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5531 */     PageContext pageContext = _jspx_page_context;
/* 5532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5534 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5535 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5536 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5538 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 5539 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5540 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5541 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5542 */       return true;
/*      */     }
/* 5544 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5545 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5550 */     PageContext pageContext = _jspx_page_context;
/* 5551 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5553 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5554 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5555 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5557 */     _jspx_th_c_005fout_005f28.setValue("${ha.key}");
/* 5558 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5559 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5560 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5561 */       return true;
/*      */     }
/* 5563 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5564 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5569 */     PageContext pageContext = _jspx_page_context;
/* 5570 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5572 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5573 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5574 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5576 */     _jspx_th_c_005fout_005f29.setValue("${ha.value}");
/* 5577 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5578 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5579 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5580 */       return true;
/*      */     }
/* 5582 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5583 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5588 */     PageContext pageContext = _jspx_page_context;
/* 5589 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5591 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5592 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5593 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5595 */     _jspx_th_c_005fset_005f3.setVar("monitorName");
/* 5596 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5597 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 5598 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5599 */         out = _jspx_page_context.pushBody();
/* 5600 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 5601 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 5602 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5605 */         if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fset_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5606 */           return true;
/* 5607 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 5608 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5611 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5612 */         out = _jspx_page_context.popBody();
/* 5613 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 5616 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5617 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 5618 */       return true;
/*      */     }
/* 5620 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 5621 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5626 */     PageContext pageContext = _jspx_page_context;
/* 5627 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5629 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5630 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 5631 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 5633 */     _jspx_th_c_005fout_005f30.setValue("${ha.value}");
/* 5634 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 5635 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 5636 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5637 */       return true;
/*      */     }
/* 5639 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5640 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_logic_005fpresent_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5645 */     PageContext pageContext = _jspx_page_context;
/* 5646 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5648 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5649 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 5650 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_logic_005fpresent_005f10);
/*      */     
/* 5652 */     _jspx_th_c_005fout_005f31.setValue("${ha.key}");
/* 5653 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 5654 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 5655 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5656 */       return true;
/*      */     }
/* 5658 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5659 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5664 */     PageContext pageContext = _jspx_page_context;
/* 5665 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5667 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5668 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 5669 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 5671 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5672 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 5673 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 5674 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5675 */       return true;
/*      */     }
/* 5677 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5678 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5683 */     PageContext pageContext = _jspx_page_context;
/* 5684 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5686 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5687 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 5688 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5690 */     _jspx_th_c_005fout_005f32.setValue("${ha.key}");
/* 5691 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 5692 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 5693 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5694 */       return true;
/*      */     }
/* 5696 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5697 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5702 */     PageContext pageContext = _jspx_page_context;
/* 5703 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5705 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5706 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 5707 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5709 */     _jspx_th_c_005fout_005f33.setValue("${ha.value}");
/* 5710 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 5711 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 5712 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5713 */       return true;
/*      */     }
/* 5715 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5716 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5721 */     PageContext pageContext = _jspx_page_context;
/* 5722 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5724 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5725 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 5726 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5728 */     _jspx_th_c_005fset_005f4.setVar("monitorName");
/* 5729 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 5730 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 5731 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5732 */         out = _jspx_page_context.pushBody();
/* 5733 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 5734 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 5735 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5738 */         if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fset_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5739 */           return true;
/* 5740 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 5741 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5744 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5745 */         out = _jspx_page_context.popBody();
/* 5746 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 5749 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 5750 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 5751 */       return true;
/*      */     }
/* 5753 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 5754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5759 */     PageContext pageContext = _jspx_page_context;
/* 5760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5762 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5763 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 5764 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 5766 */     _jspx_th_c_005fout_005f34.setValue("${ha.value}");
/* 5767 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 5768 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 5769 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5770 */       return true;
/*      */     }
/* 5772 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5773 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5778 */     PageContext pageContext = _jspx_page_context;
/* 5779 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5781 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5782 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 5783 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 5785 */     _jspx_th_c_005fout_005f35.setValue("${ha.key}");
/* 5786 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 5787 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 5788 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5789 */       return true;
/*      */     }
/* 5791 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5792 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5797 */     PageContext pageContext = _jspx_page_context;
/* 5798 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5800 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5801 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 5802 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 5804 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 5805 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 5806 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 5807 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5808 */       return true;
/*      */     }
/* 5810 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5816 */     PageContext pageContext = _jspx_page_context;
/* 5817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5819 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5820 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 5821 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5823 */     _jspx_th_c_005fif_005f24.setTest("${empty associatedmgs}");
/* 5824 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 5825 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 5827 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 5828 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f24, _jspx_page_context))
/* 5829 */           return true;
/* 5830 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 5831 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f24, _jspx_page_context))
/* 5832 */           return true;
/* 5833 */         out.write("</td>\n\t ");
/* 5834 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 5835 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5839 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 5840 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 5841 */       return true;
/*      */     }
/* 5843 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 5844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5849 */     PageContext pageContext = _jspx_page_context;
/* 5850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5852 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5853 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 5854 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f24);
/*      */     
/* 5856 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5857 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 5858 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 5859 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5860 */       return true;
/*      */     }
/* 5862 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5868 */     PageContext pageContext = _jspx_page_context;
/* 5869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5871 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5872 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 5873 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f24);
/*      */     
/* 5875 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 5876 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 5877 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 5878 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5879 */       return true;
/*      */     }
/* 5881 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5887 */     PageContext pageContext = _jspx_page_context;
/* 5888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5890 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5891 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5892 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5894 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5895 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5896 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 5897 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5898 */       return true;
/*      */     }
/* 5900 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5906 */     PageContext pageContext = _jspx_page_context;
/* 5907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5909 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5910 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 5911 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5913 */     _jspx_th_c_005fout_005f36.setValue("${param.resourceid}");
/* 5914 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 5915 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 5916 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5917 */       return true;
/*      */     }
/* 5919 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5925 */     PageContext pageContext = _jspx_page_context;
/* 5926 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5928 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5929 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 5930 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5932 */     _jspx_th_c_005fout_005f37.setValue("${param.resourcename}");
/* 5933 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 5934 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 5935 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5936 */       return true;
/*      */     }
/* 5938 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5944 */     PageContext pageContext = _jspx_page_context;
/* 5945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5947 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5948 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 5949 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5951 */     _jspx_th_c_005fout_005f38.setValue("${param.resourceid}");
/* 5952 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 5953 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 5954 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 5955 */       return true;
/*      */     }
/* 5957 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 5958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5963 */     PageContext pageContext = _jspx_page_context;
/* 5964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5966 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5967 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 5968 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5970 */     _jspx_th_c_005fout_005f39.setValue("${param.resourcename}");
/* 5971 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 5972 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 5973 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 5974 */       return true;
/*      */     }
/* 5976 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 5977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5982 */     PageContext pageContext = _jspx_page_context;
/* 5983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5985 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 5986 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 5987 */     _jspx_th_awolf_005fpiechart_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5989 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 5991 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("240");
/*      */     
/* 5993 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */     
/* 5995 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 5997 */     _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */     
/* 5999 */     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */     
/* 6001 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 6002 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 6003 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 6004 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 6005 */         out = _jspx_page_context.pushBody();
/* 6006 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 6007 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6010 */         out.write("\n\t\t              ");
/* 6011 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 6012 */           return true;
/* 6013 */         out.write(32);
/* 6014 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 6015 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6018 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 6019 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6022 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 6023 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 6024 */       return true;
/*      */     }
/* 6026 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 6027 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6032 */     PageContext pageContext = _jspx_page_context;
/* 6033 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6035 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 6036 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 6037 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 6039 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 6040 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 6041 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 6042 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 6043 */         out = _jspx_page_context.pushBody();
/* 6044 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 6045 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6048 */         out.write(32);
/* 6049 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 6050 */           return true;
/* 6051 */         out.write(32);
/* 6052 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 6053 */           return true;
/* 6054 */         out.write("                      ");
/* 6055 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 6056 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6059 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 6060 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6063 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 6064 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 6065 */       return true;
/*      */     }
/* 6067 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 6068 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6073 */     PageContext pageContext = _jspx_page_context;
/* 6074 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6076 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6077 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 6078 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 6080 */     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */     
/* 6082 */     _jspx_th_awolf_005fparam_005f0.setValue("#29FF29");
/* 6083 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 6084 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 6085 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 6086 */       return true;
/*      */     }
/* 6088 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 6089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6094 */     PageContext pageContext = _jspx_page_context;
/* 6095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6097 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6098 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 6099 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 6101 */     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */     
/* 6103 */     _jspx_th_awolf_005fparam_005f1.setValue("#FF0000");
/* 6104 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 6105 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 6106 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 6107 */       return true;
/*      */     }
/* 6109 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 6110 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6115 */     PageContext pageContext = _jspx_page_context;
/* 6116 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6118 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6119 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 6120 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6121 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 6122 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 6123 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6124 */         out = _jspx_page_context.pushBody();
/* 6125 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 6126 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6129 */         out.write("table.heading.attribute");
/* 6130 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 6131 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6134 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6135 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6138 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 6139 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6140 */       return true;
/*      */     }
/* 6142 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6148 */     PageContext pageContext = _jspx_page_context;
/* 6149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6151 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6152 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 6153 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6154 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 6155 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 6156 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6157 */         out = _jspx_page_context.pushBody();
/* 6158 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 6159 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6162 */         out.write("table.heading.value");
/* 6163 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 6164 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6167 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6168 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6171 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 6172 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6173 */       return true;
/*      */     }
/* 6175 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6176 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6181 */     PageContext pageContext = _jspx_page_context;
/* 6182 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6184 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6185 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 6186 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6187 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 6188 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 6189 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6190 */         out = _jspx_page_context.pushBody();
/* 6191 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 6192 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6195 */         out.write("table.heading.status");
/* 6196 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 6197 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6200 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6201 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6204 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 6205 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6206 */       return true;
/*      */     }
/* 6208 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6214 */     PageContext pageContext = _jspx_page_context;
/* 6215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6217 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6218 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 6219 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6220 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 6221 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 6222 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6223 */         out = _jspx_page_context.pushBody();
/* 6224 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 6225 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6228 */         out.write("table.heading.attribute");
/* 6229 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 6230 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6233 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6234 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6237 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 6238 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6239 */       return true;
/*      */     }
/* 6241 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6242 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6247 */     PageContext pageContext = _jspx_page_context;
/* 6248 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6250 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6251 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 6252 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6253 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 6254 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 6255 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6256 */         out = _jspx_page_context.pushBody();
/* 6257 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 6258 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6261 */         out.write("table.heading.value");
/* 6262 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 6263 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6266 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6267 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6270 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 6271 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6272 */       return true;
/*      */     }
/* 6274 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6275 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6280 */     PageContext pageContext = _jspx_page_context;
/* 6281 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6283 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6284 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 6285 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6286 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 6287 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 6288 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6289 */         out = _jspx_page_context.pushBody();
/* 6290 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 6291 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6294 */         out.write("table.heading.status");
/* 6295 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 6296 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6299 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6300 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6303 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 6304 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6305 */       return true;
/*      */     }
/* 6307 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6308 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6313 */     PageContext pageContext = _jspx_page_context;
/* 6314 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6316 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6317 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 6318 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6320 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 6322 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 6323 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 6324 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 6325 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6326 */       return true;
/*      */     }
/* 6328 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6329 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\pingDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */