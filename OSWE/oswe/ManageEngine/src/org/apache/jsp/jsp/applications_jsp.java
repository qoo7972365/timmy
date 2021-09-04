/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.struts.beans.HAAlertGraph;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
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
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class applications_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  827 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  828 */     getRCATrimmedText(div1, rca);
/*  829 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  832 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  833 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  868 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  869 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  872 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  873 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  874 */       set = AMConnectionPool.executeQueryStmt(query);
/*  875 */       if (set.next())
/*      */       {
/*  877 */         String helpLink = set.getString("LINK");
/*  878 */         DBUtil.searchLinks.put(key, helpLink);
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
/*  986 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1031 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1032 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
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
/* 1198 */       childresname = ExtProdUtil.decodeString(childresname);
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
/*      */ 
/*      */   public String getGroupType(String resourceid)
/*      */   {
/* 2194 */     String groupType = FormatUtil.getString("am.webclient.common.util.MGSTR");
/* 2195 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2196 */     ResultSet rs = null;
/* 2197 */     String query = null;
/*      */     try
/*      */     {
/* 2200 */       query = "select TYPE from AM_HOLISTICAPPLICATION where TYPE=1 and AM_HOLISTICAPPLICATION.HAID ='" + resourceid + "'";
/* 2201 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2202 */       if (rs.next())
/*      */       {
/* 2204 */         groupType = FormatUtil.getString("am.webclient.reports.excel.subgroup.text");
/*      */       }
/* 2206 */       rs.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2210 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 2213 */     return groupType;
/*      */   }
/*      */   
/* 2216 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2222 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2223 */   static { _jspx_dependants.put("/jsp/includes/MonitorTemplate.jspf", Long.valueOf(1473429417000L));
/* 2224 */     _jspx_dependants.put("/jsp/includes/applications_init.jspf", Long.valueOf(1473429417000L));
/* 2225 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2226 */     _jspx_dependants.put("/jsp/includes/Recent5Alarms.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2250 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2254 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2271 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2275 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2276 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2277 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2278 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2279 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2280 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2281 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2282 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2286 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2288 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2289 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.release();
/* 2290 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2297 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2300 */     JspWriter out = null;
/* 2301 */     Object page = this;
/* 2302 */     JspWriter _jspx_out = null;
/* 2303 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2307 */       response.setContentType("text/html;charset=UTF-8");
/* 2308 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2310 */       _jspx_page_context = pageContext;
/* 2311 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2312 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2313 */       session = pageContext.getSession();
/* 2314 */       out = pageContext.getOut();
/* 2315 */       _jspx_out = out;
/*      */       
/* 2317 */       out.write("<!DOCTYPE html>\n");
/* 2318 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n");
/* 2319 */       out.write("<!--$Id$-->\n\n");
/* 2320 */       Hashtable availabilitykeys = null;
/* 2321 */       synchronized (application) {
/* 2322 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2323 */         if (availabilitykeys == null) {
/* 2324 */           availabilitykeys = new Hashtable();
/* 2325 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/* 2328 */       out.write(10);
/* 2329 */       Hashtable healthkeys = null;
/* 2330 */       synchronized (application) {
/* 2331 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2332 */         if (healthkeys == null) {
/* 2333 */           healthkeys = new Hashtable();
/* 2334 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/* 2337 */       out.write(10);
/*      */       
/* 2339 */       request.setAttribute("HelpKey", "Home Page");
/*      */       
/*      */ 
/* 2342 */       out.write("\n\n\n\n\n\n\n\n\n  \n\n\n\n\n\n\n\n");
/* 2343 */       ManagedApplication mo = null;
/* 2344 */       synchronized (application) {
/* 2345 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2346 */         if (mo == null) {
/* 2347 */           mo = new ManagedApplication();
/* 2348 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2351 */       out.write(10);
/* 2352 */       HAAlertGraph graph = null;
/* 2353 */       graph = (HAAlertGraph)_jspx_page_context.getAttribute("graph", 1);
/* 2354 */       if (graph == null) {
/* 2355 */         graph = new HAAlertGraph();
/* 2356 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*      */       }
/* 2358 */       out.write(10);
/* 2359 */       out.write(10);
/* 2360 */       out.write(10);
/* 2361 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2363 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2364 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2365 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2367 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2369 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2371 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2373 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2374 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2375 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2376 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2379 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2380 */         String available = null;
/* 2381 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2382 */         out.write(10);
/*      */         
/* 2384 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2385 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2386 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2388 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2390 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2392 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2394 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2395 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2396 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2397 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2400 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2401 */           String unavailable = null;
/* 2402 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2403 */           out.write(10);
/*      */           
/* 2405 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2406 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2407 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2409 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2411 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2413 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2415 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2416 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2417 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2418 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2421 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2422 */             String unmanaged = null;
/* 2423 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2424 */             out.write(10);
/*      */             
/* 2426 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2427 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2428 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2430 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2432 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2434 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2436 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2437 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2438 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2439 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2442 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2443 */               String scheduled = null;
/* 2444 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2445 */               out.write(10);
/*      */               
/* 2447 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2448 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2449 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2451 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2453 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2455 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2457 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2458 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2459 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2460 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2463 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2464 */                 String critical = null;
/* 2465 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2466 */                 out.write(10);
/*      */                 
/* 2468 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2469 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2470 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2472 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2474 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2476 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2478 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2479 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2480 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2481 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2484 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2485 */                   String clear = null;
/* 2486 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2487 */                   out.write(10);
/*      */                   
/* 2489 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2490 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2491 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2493 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2495 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2497 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2499 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2500 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2501 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2502 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2505 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2506 */                     String warning = null;
/* 2507 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2508 */                     out.write(10);
/* 2509 */                     out.write(10);
/*      */                     
/* 2511 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2512 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2514 */                     out.write(10);
/* 2515 */                     out.write(10);
/* 2516 */                     out.write(10);
/* 2517 */                     out.write(10);
/*      */                     
/* 2519 */                     ArrayList attribIDs = new ArrayList(2);
/* 2520 */                     attribIDs.add("17");
/* 2521 */                     attribIDs.add("18");
/*      */                     
/* 2523 */                     attribIDs.add("7000");
/* 2524 */                     attribIDs.add("7001");
/* 2525 */                     attribIDs.add("7010");
/* 2526 */                     attribIDs.add("7011");
/* 2527 */                     attribIDs.add("7020");
/* 2528 */                     attribIDs.add("7021");
/* 2529 */                     attribIDs.add("7030");
/* 2530 */                     attribIDs.add("7031");
/* 2531 */                     attribIDs.add("7040");
/* 2532 */                     attribIDs.add("7041");
/* 2533 */                     attribIDs.add("7060");
/* 2534 */                     attribIDs.add("7061");
/* 2535 */                     attribIDs.add("7050");
/* 2536 */                     attribIDs.add("7051");
/* 2537 */                     attribIDs.add("3201");
/* 2538 */                     attribIDs.add("3202");
/* 2539 */                     attribIDs.add("7070");
/* 2540 */                     attribIDs.add("7071");
/* 2541 */                     attribIDs.add(String.valueOf(700));
/* 2542 */                     attribIDs.add(String.valueOf(701));
/* 2543 */                     attribIDs.add(String.valueOf(800));
/* 2544 */                     attribIDs.add(String.valueOf(801));
/* 2545 */                     attribIDs.add(String.valueOf(1000));
/* 2546 */                     attribIDs.add(String.valueOf(1001));
/* 2547 */                     attribIDs.add(String.valueOf(1100));
/* 2548 */                     attribIDs.add(String.valueOf(1101));
/* 2549 */                     attribIDs.add(String.valueOf(1200));
/* 2550 */                     attribIDs.add(String.valueOf(1201));
/* 2551 */                     attribIDs.add(String.valueOf(1300));
/* 2552 */                     attribIDs.add(String.valueOf(1301));
/* 2553 */                     attribIDs.add(String.valueOf(1650));
/* 2554 */                     attribIDs.add(String.valueOf(1651));
/* 2555 */                     attribIDs.add(String.valueOf(1350));
/* 2556 */                     attribIDs.add(String.valueOf(1351));
/* 2557 */                     attribIDs.add(String.valueOf(1450));
/* 2558 */                     attribIDs.add(String.valueOf(1451));
/* 2559 */                     attribIDs.add(String.valueOf(1950));
/* 2560 */                     attribIDs.add(String.valueOf(1951));
/* 2561 */                     attribIDs.add(String.valueOf(1930));
/* 2562 */                     attribIDs.add(String.valueOf(1931));
/* 2563 */                     attribIDs.add(String.valueOf(1470));
/* 2564 */                     attribIDs.add(String.valueOf(1471));
/*      */                     
/* 2566 */                     attribIDs.add(String.valueOf(1370));
/* 2567 */                     attribIDs.add(String.valueOf(1371));
/* 2568 */                     attribIDs.add(String.valueOf(1378));
/* 2569 */                     attribIDs.add(String.valueOf(1390));
/* 2570 */                     attribIDs.add(String.valueOf(1391));
/* 2571 */                     attribIDs.add(String.valueOf(1395));
/*      */                     
/* 2573 */                     attribIDs.add(String.valueOf(3700));
/* 2574 */                     attribIDs.add(String.valueOf(3701));
/* 2575 */                     attribIDs.add(String.valueOf(7052));
/* 2576 */                     attribIDs.add(String.valueOf(7053));
/*      */                     
/* 2578 */                     ArrayList resIDs = new ArrayList();
/* 2579 */                     ArrayList dashboardentity = new ArrayList();
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2584 */                     ArrayList resourceTypeIDs = new ArrayList();
/* 2585 */                     StringBuffer queryBuff = new StringBuffer();
/*      */                     
/* 2587 */                     ResultSet rs = null;
/* 2588 */                     AMConnectionPool dbConnection = AMConnectionPool.getInstance();
/* 2589 */                     for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                     {
/* 2591 */                       queryBuff.delete(0, queryBuff.length());
/* 2592 */                       queryBuff.append("select AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject join AM_ATTRIBUTES on AM_ManagedObject.TYPE= AM_ATTRIBUTES.RESOURCETYPE where AM_ATTRIBUTES.RESOURCETYPE='");
/* 2593 */                       queryBuff.append(com.adventnet.appmanager.util.Constants.categoryLink[i]);
/* 2594 */                       queryBuff.append("'");
/* 2595 */                       queryBuff.append(" order by ATTRIBUTEID");
/* 2596 */                       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/* 2597 */                       if (rs != null)
/*      */                       {
/* 2599 */                         if (rs.next())
/*      */                         {
/* 2601 */                           String resTypeID = rs.getString(1);
/* 2602 */                           resIDs.add("" + resTypeID);
/* 2603 */                           dashboardentity.add(resTypeID + "_" + rs.getString(2));
/*      */                           
/* 2605 */                           resourceTypeIDs.add(com.adventnet.appmanager.util.Constants.categoryLink[i]);
/* 2606 */                           resourceTypeIDs.add(resTypeID);
/* 2607 */                           resourceTypeIDs.add(rs.getString(2));
/*      */                           
/* 2609 */                           rs.next();
/* 2610 */                           dashboardentity.add(resTypeID + "_" + rs.getString(2));
/*      */                           
/* 2612 */                           resourceTypeIDs.add(rs.getString(2));
/*      */                         } }
/*      */                     }
/* 2615 */                     if (rs != null)
/*      */                     {
/* 2617 */                       AMConnectionPool.closeStatement(rs);
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2668 */                     String cachehealth = null;
/* 2669 */                     String cacheavail = null;
/*      */                     
/*      */ 
/* 2672 */                     String categorytype1 = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 2673 */                     pageContext.setAttribute("categorytype1", categorytype1);
/*      */                     
/* 2675 */                     out.write("\n\n\n\n");
/* 2676 */                     out.write("\n<script type=\"text/javascript\" src=\"/template/appmanager.js\"></script>\n\n\n\n\n\n\n");
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2681 */                     if (com.adventnet.appmanager.util.Constants.isFirstVisit)
/*      */                     {
/*      */                       try
/*      */                       {
/*      */ 
/* 2686 */                         AMConnectionPool pool = AMConnectionPool.getInstance();
/* 2687 */                         AMConnectionPool.executeUpdateStmt("UPDATE AM_GLOBALCONFIG SET VALUE='false' WHERE NAME='firstvisit'");
/* 2688 */                         com.adventnet.appmanager.util.Constants.isFirstVisit = false;
/*      */                       }
/*      */                       catch (Exception e)
/*      */                       {
/* 2692 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/* 2695 */                     String selectedFunction = "am.webclient.dasboard.summarytab.title";
/* 2696 */                     Cookie[] cookies = request.getCookies();
/* 2697 */                     for (int i = 0; i < cookies.length; i++) {
/* 2698 */                       if (cookies[i].getName().equals("am_applicationsview"))
/*      */                       {
/* 2700 */                         if (cookies[i].getValue().equals("summary"))
/*      */                         {
/* 2702 */                           selectedFunction = "am.webclient.dasboard.summarytab.title";
/* 2703 */                           break;
/*      */                         }
/* 2705 */                         if (cookies[i].getValue().equals("business"))
/*      */                         {
/* 2707 */                           if (request.isUserInRole("OPERATOR")) break;
/* 2708 */                           selectedFunction = "am.webclient.flashview.displayname";
/* 2709 */                           break;
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/* 2715 */                         if (cookies[i].getValue().equals("availability"))
/*      */                         {
/* 2717 */                           selectedFunction = "am.webclient.dasboard.availabilitytab.title";
/* 2718 */                           break;
/*      */                         }
/* 2720 */                         if (cookies[i].getValue().equals("performance"))
/*      */                         {
/* 2722 */                           selectedFunction = "am.webclient.dasboard.performancetab.title";
/* 2723 */                           break;
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     
/* 2728 */                     out.write("\n\n\n\n");
/* 2729 */                     List topNServers = com.adventnet.appmanager.client.views.ViewsCreator.getTopNServers();
/*      */                     
/* 2731 */                     request.setAttribute("topNServers", topNServers);
/*      */                     
/* 2733 */                     List topAppsRT = com.adventnet.appmanager.client.views.ViewsCreator.getTopNApps();
/*      */                     
/* 2735 */                     request.setAttribute("topappsrt", topAppsRT);
/*      */                     
/* 2737 */                     ArrayList entitylist = new ArrayList();
/*      */                     
/* 2739 */                     out.write(10);
/* 2740 */                     out.write(10);
/*      */                     
/* 2742 */                     IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2743 */                     _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2744 */                     _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */                     
/* 2746 */                     _jspx_th_logic_005fiterate_005f0.setName("topNServers");
/*      */                     
/* 2748 */                     _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                     
/* 2750 */                     _jspx_th_logic_005fiterate_005f0.setType("java.util.Map");
/*      */                     
/* 2752 */                     _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/* 2753 */                     int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2754 */                     if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2755 */                       Map row = null;
/* 2756 */                       Integer i = null;
/* 2757 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2758 */                         out = _jspx_page_context.pushBody();
/* 2759 */                         _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2760 */                         _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                       }
/* 2762 */                       row = (Map)_jspx_page_context.findAttribute("row");
/* 2763 */                       i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                       for (;;) {
/* 2765 */                         out.write(10);
/*      */                         
/* 2767 */                         resIDs.add(row.get("RESOURCEID"));
/*      */                         
/* 2769 */                         out.write(10);
/* 2770 */                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2771 */                         row = (Map)_jspx_page_context.findAttribute("row");
/* 2772 */                         i = (Integer)_jspx_page_context.findAttribute("i");
/* 2773 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2776 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2777 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2780 */                     if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2781 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*      */                     }
/*      */                     else {
/* 2784 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2785 */                       out.write(10);
/* 2786 */                       out.write(10);
/*      */                       
/* 2788 */                       IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2789 */                       _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2790 */                       _jspx_th_logic_005fiterate_005f1.setParent(null);
/*      */                       
/* 2792 */                       _jspx_th_logic_005fiterate_005f1.setName("topappsrt");
/*      */                       
/* 2794 */                       _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                       
/* 2796 */                       _jspx_th_logic_005fiterate_005f1.setType("java.util.Map");
/*      */                       
/* 2798 */                       _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/* 2799 */                       int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2800 */                       if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2801 */                         Map row = null;
/* 2802 */                         Integer i = null;
/* 2803 */                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2804 */                           out = _jspx_page_context.pushBody();
/* 2805 */                           _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2806 */                           _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                         }
/* 2808 */                         row = (Map)_jspx_page_context.findAttribute("row");
/* 2809 */                         i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                         for (;;) {
/* 2811 */                           out.write(10);
/*      */                           
/* 2813 */                           String resourceid = (String)row.get("RESOURCEID");
/* 2814 */                           String healthid = AMAttributesCache.getHealthId((String)row.get("TYPE"));
/* 2815 */                           String availabilityid = AMAttributesCache.getAvailabilityId((String)row.get("TYPE"));
/* 2816 */                           if (healthid != null) {
/* 2817 */                             entitylist.add(resourceid + "_" + healthid);
/*      */                           }
/*      */                           
/* 2820 */                           if (availabilityid != null) {
/* 2821 */                             entitylist.add(resourceid + "_" + availabilityid);
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/* 2826 */                           out.write(10);
/* 2827 */                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 2828 */                           row = (Map)_jspx_page_context.findAttribute("row");
/* 2829 */                           i = (Integer)_jspx_page_context.findAttribute("i");
/* 2830 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2833 */                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2834 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2837 */                       if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 2838 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*      */                       }
/*      */                       else {
/* 2841 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 2842 */                         out.write(10);
/*      */                         
/*      */ 
/* 2845 */                         Properties alert = getStatus(entitylist);
/* 2846 */                         request.setAttribute("alert", alert);
/* 2847 */                         String resourceName = request.getParameter("type");
/* 2848 */                         String appname = request.getParameter("name");
/* 2849 */                         String haid = request.getParameter("haid");
/* 2850 */                         String encodeurl = URLEncoder.encode("/applications.do");
/*      */                         
/* 2852 */                         out.write("\n\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*      */                         
/*      */ 
/* 2855 */                         String requestpath = "/images/mm_menu2.jsp";
/*      */                         
/* 2857 */                         out.write(10);
/* 2858 */                         JspRuntimeLibrary.include(request, response, requestpath, out, false);
/* 2859 */                         out.write("\n</script>\n\n\n<script>\nfunction showApplicationAlarms(alertcount,haid)\n{\n\tvar d=new Date();\n\tif(alertcount!=0)\n\t{\n\t\t//MM_openBrWindow('/RecentAlarms.do?method=getApplicationAlarms&haid='+haid+'&d='+d,'AlarmSummary','width=600,height=450, scrollbars=yes');\n\t\tlocation.href=\"/fault/AMAlarmView.do?displayName=Alerts&haid=\"+haid;\n\t}\n}\n\nfunction popout()\n{\nvar popoutwindow=window.open('/GraphicalView.do?method=popUp&haid=0','FlashView','scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25');\npopoutwindow.focus();\n}\n\n</script>\n\n\n\n");
/*      */                         
/* 2861 */                         InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2862 */                         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2863 */                         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                         
/* 2865 */                         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 2866 */                         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2867 */                         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                           for (;;) {
/* 2869 */                             out.write(10);
/*      */                             
/* 2871 */                             PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2872 */                             _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2873 */                             _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                             
/* 2875 */                             _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                             
/* 2877 */                             _jspx_th_tiles_005fput_005f0.setValue(MGSTRs);
/* 2878 */                             int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2879 */                             if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2880 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                             }
/*      */                             
/* 2883 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2884 */                             out.write(32);
/* 2885 */                             if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                               return;
/* 2887 */                             out.write(32);
/*      */                             
/* 2889 */                             PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2890 */                             _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2891 */                             _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                             
/* 2893 */                             _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                             
/* 2895 */                             _jspx_th_tiles_005fput_005f2.setType("string");
/* 2896 */                             int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2897 */                             if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2898 */                               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2899 */                                 out = _jspx_page_context.pushBody();
/* 2900 */                                 _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2901 */                                 _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2904 */                                 out.write("\n\n\n<!-- <body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onload=\"setPref('home');\" onunload=\"savePref('home');\"> //-->\n\n<SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n\n\n");
/*      */                                 
/* 2906 */                                 ArrayList menupos = new ArrayList(5);
/*      */                                 
/* 2908 */                                 if (request.isUserInRole("OPERATOR"))
/*      */                                 {
/* 2910 */                                   menupos.add("141");
/* 2911 */                                   menupos.add("183");
/* 2912 */                                   menupos.add("204");
/* 2913 */                                   menupos.add("226");
/* 2914 */                                   menupos.add("121");
/* 2915 */                                   menupos.add("162");
/* 2916 */                                   menupos.add("266");
/* 2917 */                                   menupos.add("247");
/* 2918 */                                   menupos.add("290");
/* 2919 */                                   menupos.add("310");
/*      */                                 }
/* 2921 */                                 else if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2922 */                                   menupos.add("250");
/* 2923 */                                   menupos.add("290");
/* 2924 */                                   menupos.add("310");
/* 2925 */                                   menupos.add("333");
/* 2926 */                                   menupos.add("230");
/* 2927 */                                   menupos.add("270");
/* 2928 */                                   menupos.add("355");
/* 2929 */                                   menupos.add("375");
/* 2930 */                                   menupos.add("395");
/* 2931 */                                   menupos.add("415");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                 }
/* 2947 */                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                 {
/*      */ 
/* 2950 */                                   menupos.add("240");
/* 2951 */                                   menupos.add("342");
/* 2952 */                                   menupos.add("261");
/* 2953 */                                   menupos.add("282");
/* 2954 */                                   menupos.add("219");
/* 2955 */                                   menupos.add("303");
/* 2956 */                                   menupos.add("343");
/* 2957 */                                   menupos.add("324");
/*      */                                 }
/* 2959 */                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                 {
/*      */ 
/* 2962 */                                   menupos.add("323");
/* 2963 */                                   menupos.add("240");
/* 2964 */                                   menupos.add("261");
/* 2965 */                                   menupos.add("282");
/* 2966 */                                   menupos.add("219");
/* 2967 */                                   menupos.add("303");
/* 2968 */                                   menupos.add("345");
/* 2969 */                                   menupos.add("324");
/*      */                                 }
/* 2971 */                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                 {
/*      */ 
/* 2974 */                                   menupos.add("323");
/* 2975 */                                   menupos.add("219");
/* 2976 */                                   menupos.add("240");
/* 2977 */                                   menupos.add("261");
/* 2978 */                                   menupos.add("365");
/* 2979 */                                   menupos.add("282");
/* 2980 */                                   menupos.add("324");
/* 2981 */                                   menupos.add("303");
/*      */                                 }
/* 2983 */                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                 {
/*      */ 
/* 2986 */                                   menupos.add("323");
/* 2987 */                                   menupos.add("219");
/* 2988 */                                   menupos.add("240");
/* 2989 */                                   menupos.add("344");
/* 2990 */                                   menupos.add("365");
/* 2991 */                                   menupos.add("261");
/* 2992 */                                   menupos.add("303");
/* 2993 */                                   menupos.add("282");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2997 */                                   menupos.add("310");
/* 2998 */                                   menupos.add("350");
/* 2999 */                                   menupos.add("369");
/* 3000 */                                   menupos.add("391");
/* 3001 */                                   menupos.add("290");
/* 3002 */                                   menupos.add("330");
/* 3003 */                                   menupos.add("412");
/* 3004 */                                   menupos.add("435");
/* 3005 */                                   menupos.add("455");
/* 3006 */                                   menupos.add("475");
/*      */                                 }
/*      */                                 
/* 3009 */                                 pageContext.setAttribute("menupos", menupos);
/*      */                                 
/* 3011 */                                 out.write(10);
/* 3012 */                                 out.write(10);
/*      */                                 
/* 3014 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3015 */                                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3016 */                                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                 
/* 3018 */                                 _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 3019 */                                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3020 */                                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 3022 */                                     out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td colspan=\"3\" align=\"left\" class=\"leftlinksheading\">");
/* 3023 */                                     out.print(FormatUtil.getString("am.webclient.gmap.businessview.text"));
/* 3024 */                                     out.write("</td>\n  </tr>\n  <tr>\n    <td colspan=\"3\" align=\"left\" class=\"leftlinkstd\">\n    <a href=\"/showBussiness.do?method=generateApplicationAvailablity&selectTabName=SLA\" class=\"new-left-links\">");
/* 3025 */                                     out.print(FormatUtil.getString("am.webclient.selectmonitorview.SLAview.text"));
/* 3026 */                                     out.write("</a>\n    </td>\n  </tr>\n  <tr>\n   <td colspan=\"3\" align=\"left\" class=\"leftlinkstd\">\n    <a href=\"javascript:void(0);\"  onClick=\"popout()\" class=\"new-left-links\">");
/* 3027 */                                     out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/* 3028 */                                     out.write("</a>\n   </td>\n  </tr>\n</table>\n");
/* 3029 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3030 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3034 */                                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3035 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 3038 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3039 */                                 out.write(10);
/* 3040 */                                 if (_jspx_meth_logic_005fnotPresent_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                   return;
/* 3042 */                                 out.write(10);
/* 3043 */                                 out.write(10);
/* 3044 */                                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*      */                                 
/* 3046 */                                 String categorytype = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 3047 */                                 pageContext.setAttribute("categorytype", categorytype);
/*      */                                 
/* 3049 */                                 out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n      <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3050 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 3051 */                                 out.write("</td>\n  </tr>\n\n\n");
/*      */                                 
/* 3053 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3054 */                                 _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3055 */                                 _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                 
/* 3057 */                                 _jspx_th_logic_005fnotPresent_005f2.setRole("OPERATOR");
/* 3058 */                                 int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3059 */                                 if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                   for (;;) {
/* 3061 */                                     out.write(10);
/* 3062 */                                     out.write(10);
/*      */                                     
/* 3064 */                                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3065 */                                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3066 */                                     _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                                     
/* 3068 */                                     _jspx_th_c_005fif_005f0.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 3069 */                                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3070 */                                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                       for (;;) {
/* 3072 */                                         out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 3073 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 3074 */                                         out.write("</a>\n\n</td>\n</tr>\n");
/* 3075 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3076 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3080 */                                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3081 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                     }
/*      */                                     
/* 3084 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3085 */                                     out.write(10);
/*      */                                     
/* 3087 */                                     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3088 */                                     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3089 */                                     _jspx_th_c_005fif_005f1.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                                     
/* 3091 */                                     _jspx_th_c_005fif_005f1.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 3092 */                                     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3093 */                                     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                       for (;;) {
/* 3095 */                                         out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 3096 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 3097 */                                         out.write("</a>\n\n</td>\n</tr>\n");
/* 3098 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3099 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3103 */                                     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3104 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                     }
/*      */                                     
/* 3107 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3108 */                                     out.write(10);
/*      */                                     
/* 3110 */                                     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3111 */                                     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3112 */                                     _jspx_th_c_005fif_005f2.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                                     
/* 3114 */                                     _jspx_th_c_005fif_005f2.setTest("${categorytype!='J2EE'}");
/* 3115 */                                     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3116 */                                     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                       for (;;) {
/* 3118 */                                         out.write("\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 3119 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3120 */                                         out.write("</a>\n\n</td>\n</tr>\n");
/* 3121 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3122 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3126 */                                     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3127 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                     }
/*      */                                     
/* 3130 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3131 */                                     out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 3132 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3133 */                                     out.write("</a>\n\n</td>\n</tr>\n");
/*      */                                     
/* 3135 */                                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3136 */                                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3137 */                                     _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                                     
/* 3139 */                                     _jspx_th_c_005fif_005f3.setTest("${categorytype!='DATABASE'}");
/* 3140 */                                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3141 */                                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                       for (;;) {
/* 3143 */                                         out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 3144 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 3145 */                                         out.write("</a>\n\n</td>\n</tr>\n");
/* 3146 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3147 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3151 */                                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3152 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                     }
/*      */                                     
/* 3155 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3156 */                                     out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 3157 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3158 */                                     out.write("    </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 3159 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3160 */                                     out.write("</a>\n\n</td>\n</tr>\n\n");
/* 3161 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3162 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3166 */                                 if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3167 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                 }
/*      */                                 
/* 3170 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3171 */                                 out.write(10);
/* 3172 */                                 out.write(10);
/*      */                                 
/* 3174 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3175 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3176 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                 
/* 3178 */                                 _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 3179 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3180 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 3182 */                                     out.write(10);
/* 3183 */                                     out.write(10);
/*      */                                     
/* 3185 */                                     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3186 */                                     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3187 */                                     _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                                     
/* 3189 */                                     _jspx_th_c_005fif_005f4.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 3190 */                                     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3191 */                                     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                       for (;;) {
/* 3193 */                                         out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 3194 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 3195 */                                         out.write("</a>\n\n</td>\n</tr>\n");
/* 3196 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3197 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3201 */                                     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3202 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                     }
/*      */                                     
/* 3205 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3206 */                                     out.write(10);
/*      */                                     
/* 3208 */                                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3209 */                                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3210 */                                     _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                                     
/* 3212 */                                     _jspx_th_c_005fif_005f5.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 3213 */                                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3214 */                                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                       for (;;) {
/* 3216 */                                         out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 3217 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 3218 */                                         out.write("</a>\n\n</td>\n</tr>\n");
/* 3219 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3220 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3224 */                                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3225 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                     }
/*      */                                     
/* 3228 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3229 */                                     out.write("\n\n\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 3230 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3231 */                                     out.write("</a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 3232 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3233 */                                     out.write("</a>\n\n</td>\n</tr>\n\n");
/*      */                                     
/* 3235 */                                     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3236 */                                     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3237 */                                     _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                                     
/* 3239 */                                     _jspx_th_c_005fif_005f6.setTest("${categorytype!='DATABASE'}");
/* 3240 */                                     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3241 */                                     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                       for (;;) {
/* 3243 */                                         out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 3244 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 3245 */                                         out.write("</a>\n\n</td>\n</tr>\n");
/* 3246 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3247 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3251 */                                     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3252 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                     }
/*      */                                     
/* 3255 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3256 */                                     out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 3257 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3258 */                                     out.write(" </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 3259 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3260 */                                     out.write("</a>\n\n</td>\n</tr>\n");
/* 3261 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3262 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3266 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3267 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 3270 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3271 */                                 out.write("\n\n  <tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=CAM&network=Custom-Application\"   class=\"new-left-links\">");
/* 3272 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 3273 */                                 out.write("</a></td>\n\n</tr>\n");
/*      */                                 
/* 3275 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3276 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3277 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                 
/* 3279 */                                 _jspx_th_c_005fif_005f7.setTest("${categorytype!='CLOUD'}");
/* 3280 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3281 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 3283 */                                     out.write("\n<tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MOM\"   class=\"new-left-links\">");
/* 3284 */                                     out.print(FormatUtil.getString("Middleware/Portal"));
/* 3285 */                                     out.write("</a></td>\n \n</tr>\n");
/* 3286 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3287 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3291 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3292 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 3295 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3296 */                                 out.write(10);
/*      */                                 
/* 3298 */                                 String[] categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3299 */                                 for (int i = 0; i < categoryLink.length; i++)
/*      */                                 {
/* 3301 */                                   if ((!categoryLink[i].equals("APP")) && (!categoryLink[i].equals("TM")) && (!categoryLink[i].equals("ERP")) && (!categoryLink[i].equals("DBS")) && (!categoryLink[i].equals("SYS")) && (!categoryLink[i].equals("MS")) && (!categoryLink[i].equals("SCR")) && (!categoryLink[i].equals("NWD")) && (!categoryLink[i].equals("SER")) && (!categoryLink[i].equals("URL")) && (!categoryLink[i].equals("CAM")) && (!categoryLink[i].equals("MOM")) && (!categoryLink[i].equals("SAN")) && (!categoryLink[i].equals("EMO")))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3310 */                                     out.write("\n<tr>\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href='");
/* 3311 */                                     out.print("/showresource.do?method=showResourceTypes&detailspage=true&group=" + categoryLink[i]);
/* 3312 */                                     out.write(39);
/* 3313 */                                     out.write(62);
/* 3314 */                                     out.print(FormatUtil.getString(com.adventnet.appmanager.util.Constants.categoryTitle[i]));
/* 3315 */                                     out.write("</a>\n</td>\n</tr>\n");
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 3319 */                                 out.write("\n\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 3320 */                                 out.write("\n<br>\n\n");
/*      */                                 
/*      */ 
/* 3323 */                                 String method = "showResourceTypes";
/* 3324 */                                 String network = request.getParameter("selectedNetwork");
/* 3325 */                                 String toAppendLink = "";
/* 3326 */                                 if (network != null)
/*      */                                 {
/* 3328 */                                   toAppendLink = "&selectedNetwork=" + network;
/*      */                                 }
/*      */                                 
/* 3331 */                                 out.write(10);
/*      */                                 
/* 3333 */                                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3334 */                                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3335 */                                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                 
/* 3337 */                                 _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,USERS");
/* 3338 */                                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3339 */                                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 3341 */                                     out.write(10);
/*      */                                     
/* 3343 */                                     if (!EnterpriseUtil.isAdminServer())
/*      */                                     {
/*      */ 
/*      */ 
/* 3347 */                                       out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td colspan=\"3\" align=\"left\" class=\"leftlinksheading\">");
/* 3348 */                                       out.print(FormatUtil.getString("am.webclient.hometab.networkview.heading"));
/* 3349 */                                       out.write("</td>\n  </tr>\n  <tr>\n    <td colspan=\"3\" align=\"left\" class=\"leftlinkstd\"><a\n    href=\"/showresource.do?method=showResourceTypes\" class=\"new-left-links\">");
/* 3350 */                                       out.print(FormatUtil.getString("am.webclient.networkdetails.title"));
/* 3351 */                                       out.write("\n</a> </td>\n  </tr>\n  ");
/*      */                                       
/*      */ 
/* 3354 */                                       ArrayList list = mo.getRows("select AM_ManagedObject.RESOURCENAME from AM_ManagedObject,Network,IpAddress where Network.NAME=IpAddress.PARENTNET and AM_ManagedObject.RESOURCENAME=IpAddress.PARENTNET group by RESOURCENAME");
/*      */                                       
/* 3356 */                                       for (int i = 0; i < list.size(); i++)
/*      */                                       {
/* 3358 */                                         ArrayList oneList = (ArrayList)list.get(i);
/*      */                                         
/* 3360 */                                         out.write("\n  <tr>\n    <td colspan=\"3\" align=\"left\" class=\"leftlinkstd\"><a\n    ");
/*      */                                         
/* 3362 */                                         if (!oneList.get(0).equals(network))
/*      */                                         {
/*      */ 
/* 3365 */                                           out.write("\n\thref=\"/showresource.do?method=");
/* 3366 */                                           out.print(method);
/* 3367 */                                           out.write("&selectedNetwork=");
/* 3368 */                                           out.print(oneList.get(0));
/* 3369 */                                           out.write("\" class=\"new-left-links\"\n    ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 3373 */                                         out.write(10);
/* 3374 */                                         out.write(62);
/* 3375 */                                         out.print(oneList.get(0));
/* 3376 */                                         out.write("</a> </td>\n  </tr>\n\n ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3380 */                                       out.write("\n\n</table>\n\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3384 */                                     out.write(10);
/* 3385 */                                     out.write(10);
/* 3386 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3387 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3391 */                                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3392 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 3395 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3396 */                                 out.write(" <br>\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr>\n <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 3397 */                                 out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 3398 */                                 out.write("</td>\n<td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 3399 */                                 if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                   return;
/* 3401 */                                 out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n  </tr>\n  <tr>\n <td colspan=\"2\" class=\"quicknote\">");
/* 3402 */                                 out.print(FormatUtil.getString("am.webclient.hometab.quicknote", new String[] { MGSTR, MGSTRs }));
/* 3403 */                                 out.write("</td>\n  </tr>\n  <tr>\n  <td align=\"right\" colspan=\"2\"  class=\"quicknote\"><a href=\"/help/getting-started/system-network-monitoring-ui.html\" target=\"_blank\" class=\"selectedmenu\"><img src=\"/images/icon_quicknote_help.gif\" hspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">");
/* 3404 */                                 out.print(FormatUtil.getString("am.webclient.hometab.availabilitycheck.helpmessage"));
/* 3405 */                                 out.write("</a></td>\n  </tr>\n</table>\n\n &nbsp; ");
/* 3406 */                                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3407 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3410 */                               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3411 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3414 */                             if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3415 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                             }
/*      */                             
/* 3418 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3419 */                             out.write(32);
/*      */                             
/* 3421 */                             PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3422 */                             _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3423 */                             _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                             
/* 3425 */                             _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                             
/* 3427 */                             _jspx_th_tiles_005fput_005f3.setType("string");
/* 3428 */                             int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3429 */                             if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 3430 */                               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3431 */                                 out = _jspx_page_context.pushBody();
/* 3432 */                                 _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 3433 */                                 _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3436 */                                 out.write("\n\n\n<script>\nfunction fnSelectAll2(e,str)\n{\n\tToggleAll(e,document.frm,str)\n}\n\nfunction myOnLoad()\n{\n    ");
/*      */                                 
/* 3438 */                                 if (selectedFunction.equals("am.webclient.flashview.displayname"))
/*      */                                 {
/* 3440 */                                   out.write("\n                SetTabStyle('am.webclient.flashview.displayname',tabsTableID);//No I18N\n        document.getElementById(\"loadingg\").style.display=\"none\";\n\tdocument.getElementById(\"realtimedata\").style.display=\"none\";\n        document.getElementById(\"mgstatus\").style.display=\"inline\";\n         document.getElementById(\"mgstatus\").style.height=\"100%\";\n\n        ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3444 */                                 out.write("\n}\n\n\nfunction fnSelectAll(e)\n{\n\tToggleAll(e,document.frm,\"select\")\n}\nfunction fnDelete()\n{\n\n\t");
/* 3445 */                                 if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 3447 */                                 out.write(10);
/* 3448 */                                 out.write(9);
/*      */                                 
/* 3450 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3451 */                                 _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3452 */                                 _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3454 */                                 _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3455 */                                 int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3456 */                                 if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                   for (;;) {
/* 3458 */                                     out.write("\n\nif(!checkforOneSelected(document.frm,\"select\"))\n{\n\talert('Select atleast one ");
/* 3459 */                                     out.print(MGSTR);
/* 3460 */                                     out.write(" to delete');\n\treturn;\n}\nif(confirm('Do you wish to remove the selected ");
/* 3461 */                                     out.print(MGSTRs);
/* 3462 */                                     out.write("'))\n{\n\tdocument.frm.action=\"/manageApplications.do?method=delete\";\n\tdocument.frm.method=\"Post\"\n\tdocument.frm.submit();\n}\n");
/* 3463 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3464 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3468 */                                 if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3469 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                                 }
/*      */                                 
/* 3472 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3473 */                                 out.write("\n\n}\n\n\nfunction fnCallLink(name,value)\n{\n\t// location.href=\"/showapplicationalerts.do?appname=\"+name;\n\tif(value=='0')\n\t{\n\t\treturn;\n\t}\n\tlocation.href=\"/jsp/HAAlert.jsp?appName=\" + name;\n}\n</script>\n");
/*      */                                 
/*      */ 
/* 3476 */                                 if (request.isUserInRole("OPERATOR"))
/*      */                                 {
/*      */ 
/* 3479 */                                   out.write("\n\n  \t ");
/* 3480 */                                   JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.summarytab.title,am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.summarytab.title,am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeTabSnapshot,getHomeTabAvailabilityData,getHomePerformanceData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("HAI", request.getCharacterEncoding()), out, true);
/* 3481 */                                   out.write("\n  \t      ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3486 */                                   out.write("\n    ");
/* 3487 */                                   JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.summarytab.title,am.webclient.flashview.displayname,am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.summarytab.title,am.webclient.flashview.displayname,am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeTabSnapshot,getMGStatus,getHomeTabAvailabilityData,getHomePerformanceData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("HAI", request.getCharacterEncoding()), out, true);
/* 3488 */                                   out.write(10);
/*      */                                 }
/*      */                                 
/*      */ 
/* 3492 */                                 out.write("\n\n<div id=\"health\" style=\"display:none;\">\n</div>\n<div id=\"mgstatus\" style=\"display:none;\">\n ");
/*      */                                 
/* 3494 */                                 if (selectedFunction.equals("am.webclient.flashview.displayname"))
/*      */                                 {
/* 3496 */                                   out.write("\n<table  width=\"100%\" border=\"0\" style=\" height:600px;\">\n<tr>\n<td  width=\"100%\" valign=\"top\" style=\" height:600px;\">\n\n");
/* 3497 */                                   JspRuntimeLibrary.include(request, response, "/GraphicalView.do?method=homeTab&haid=0", out, false);
/* 3498 */                                   out.write("\n</td>\n</tr>\n</table>\n<div  style=\"position:relative; bottom:4px; left:4px; border-top:1px solid #b3c0c6; width:99%\"></div>\n");
/*      */                                 }
/* 3500 */                                 out.write("\n</div>\n<div id=\"availability\" style=\"display:none; style=\"height:100%\">\n<br>\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td class=\"tableheading\">");
/* 3501 */                                 out.print(FormatUtil.getString("am.webclient.dasboard.availability.title"));
/* 3502 */                                 out.write(" </td>\n<td align=\"right\" class=\"tableheading\">\n<SELECT id=\"historyperiod\" onchange=\"getHomeAvailabilityData('HAI')\" class=\"formtext\">\n\t<OPTION ");
/* 3503 */                                 if (_jspx_meth_c_005fif_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 3505 */                                 out.write(" value=\"1\">");
/* 3506 */                                 out.print(FormatUtil.getString("am.webclient.period.last24hours"));
/* 3507 */                                 out.write(" </OPTION>\n\t<OPTION ");
/* 3508 */                                 if (_jspx_meth_c_005fif_005f9(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 3510 */                                 out.write(" value=\"2\">");
/* 3511 */                                 out.print(FormatUtil.getString("Last 30 Days"));
/* 3512 */                                 out.write("</OPTION>\n</SELECT>\n</td>\n</tr>\n</table>\n<div id=\"availabilitydata\" style=\"display:block;\">\n</div>\n\n</div>\n\n<div id=\"realtimedata\">\n<br>\n");
/*      */                                 
/* 3514 */                                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3515 */                                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3516 */                                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3518 */                                 _jspx_th_c_005fif_005f10.setTest("${param.selectTab!=\"MGStatus\"}");
/* 3519 */                                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3520 */                                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                   for (;;) {
/* 3522 */                                     out.write("\n<form name=\"frm\" action=\"UpdateApplication.jsp\" method=\"post\" style=\"display: inline\">\n");
/*      */                                     
/* 3524 */                                     if ((FaultUtil.GATEWAY_CHECK_ENABLED) && (FaultUtil.getCurrentGatewayStatus() == 1))
/*      */                                     {
/*      */ 
/* 3527 */                                       out.write("\n  <table width=\"99%\" border=\"0\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#F0F0F0\">\n    <tr>\n      <td height=\"30\" >&nbsp;&nbsp;<img src=\"/images/icon_monitor_failure.gif\"></td>\n      <td height=\"30\" class=\"bodytext\">&nbsp;&nbsp;");
/* 3528 */                                       out.print(FormatUtil.getString("am.webclient.hometab.availabilitycheck.message"));
/* 3529 */                                       out.write("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/help/administrative-operation/global-setting-administrator.html#localhost\" target=\"_blank\" class=\"selectedmenu\">");
/* 3530 */                                       out.print(FormatUtil.getString("am.webclient.hometab.availabilitycheck.helpmessage"));
/* 3531 */                                       out.write("&gt;&gt;</a></td>\n    </tr>\n</table>\n<br>\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3535 */                                     out.write(10);
/* 3536 */                                     if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                       return;
/* 3538 */                                     out.write(10);
/*      */                                     
/* 3540 */                                     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3541 */                                     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3542 */                                     _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fif_005f10);
/*      */                                     
/* 3544 */                                     _jspx_th_c_005fif_005f12.setTest("${param.technician!=null && param.technician!=''}");
/* 3545 */                                     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3546 */                                     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                       for (;;) {
/* 3548 */                                         out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n  <tr>\n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\">\n\t");
/*      */                                         
/* 3550 */                                         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3551 */                                         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3552 */                                         _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f12);
/* 3553 */                                         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3554 */                                         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                           for (;;) {
/* 3556 */                                             out.write("\n\t  ");
/*      */                                             
/* 3558 */                                             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3559 */                                             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3560 */                                             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                             
/* 3562 */                                             _jspx_th_c_005fwhen_005f0.setTest("${param.technician=='pickUpAlarm'}");
/* 3563 */                                             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3564 */                                             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                               for (;;) {
/* 3566 */                                                 out.write("\n\t    ");
/* 3567 */                                                 out.print(FormatUtil.getString("webclient.fault.alarm.pickup.status.success"));
/* 3568 */                                                 out.write("\n\t  ");
/* 3569 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3570 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3574 */                                             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3575 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                             }
/*      */                                             
/* 3578 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3579 */                                             out.write("\n\t  ");
/*      */                                             
/* 3581 */                                             OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3582 */                                             _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3583 */                                             _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 3584 */                                             int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3585 */                                             if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                               for (;;) {
/* 3587 */                                                 out.write("\n\t    ");
/* 3588 */                                                 out.print(FormatUtil.getString("webclient.fault.alarm.unpick.status.success"));
/* 3589 */                                                 out.write("\n\t  ");
/* 3590 */                                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3591 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3595 */                                             if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3596 */                                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                             }
/*      */                                             
/* 3599 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3600 */                                             out.write(10);
/* 3601 */                                             out.write(9);
/* 3602 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3603 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3607 */                                         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3608 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                         }
/*      */                                         
/* 3611 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3612 */                                         out.write("\n\t</td>\n  </tr>\n</table>\n<br>\n");
/* 3613 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3614 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3618 */                                     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3619 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                                     }
/*      */                                     
/* 3622 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3623 */                                     out.write("\n<script type=\"text/javascript\" src=\"../template/dnd.js\"></script>\n");
/*      */                                     
/* 3625 */                                     if ((EnterpriseUtil.isAdminServer()) && (request.getAttribute("noMASServer") != null))
/*      */                                     {
/* 3627 */                                       out.write("\n\n<table class=\"messagebox\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" width=\"99%\">\n <tbody><tr>\n  <td align=\"center\" width=\"4%\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" height=\"23\" width=\"23\"></td>\n  <td class=\"message\" height=\"34\" width=\"94%\">\n   ");
/* 3628 */                                       out.print(FormatUtil.getString("am.webclient.managedserver.nomanagedserver.message"));
/* 3629 */                                       out.write("\n  </td>\n </tr></tbody>\n<table>\n<tr>\n<td>&nbsp;</td>\n</tr>\n");
/*      */                                     }
/* 3631 */                                     out.write("\n<div id=\"GHOST\" style=\"background-color:transparent;position:absolute;cursor:default;cursor:default;z-index:999;visibility:hidden;filter:alpha(opacity=50);-moz-opacity:0.5;opacity: 0.5;\" class=\"bodyText\"></div>\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n<tr>\n <td id=\"DRAG_APPEND_1\" valign=\"top\" onmouseup=\"drop(event)\" onmousemove=\"move(event)\" onmousedown=\"drag(event)\" style=\"border: 0pt none ;\">\n<div id=\"DRAG_1\" style=\"display:;\" >\n   ");
/*      */                                     
/* 3633 */                                     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3634 */                                     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3635 */                                     _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f10);
/*      */                                     
/* 3637 */                                     _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,USERS,ENTERPRISEADMIN");
/* 3638 */                                     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3639 */                                     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                       for (;;) {
/* 3641 */                                         out.write("\n  <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n    <tr>\n      <td height=\"18\" colspan=\"2\" class=\"dragndroptblheading\" title=\"");
/* 3642 */                                         out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 3643 */                                         out.write("\" onmousedown = \"drag(event)\" onMouseMove=\"move(event)\" onMouseUp=\"drop(event)\" >");
/* 3644 */                                         out.print(FormatUtil.getString("am.webclient.hometab.dashboard.title"));
/* 3645 */                                         out.write("</td>\n    </tr>\n    <tr>\n      <td width=\"55%\" height=\"26\" align=\"center\" class=\"dashboardbg\">&nbsp;&nbsp;");
/* 3646 */                                         out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.title"));
/* 3647 */                                         out.write("</td>\n      <td width=\"45%\" align=\"center\" class=\"dashboardbg\">");
/* 3648 */                                         out.print(FormatUtil.getString("am.webclient.hometab.mostcriticalmonitorgroups.title", new String[] { MGSTRs }));
/* 3649 */                                         out.write("</td>\n    </tr>\n    <tr>\n      <td height=\"200\" align=\"center\" valign=\"top\" class=\"dashboardbg\">\n        <table width=\"85%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n          <tr>\n            <td height=\"63\" align=\"center\" valign=\"top\">\n            ");
/* 3650 */                                         JspRuntimeLibrary.include(request, response, "/jsp/Dashboard.jsp", out, true);
/* 3651 */                                         out.write("\n            </td>\n          </tr>\n        </table>\n\n      </td>\n      <td align=\"center\" valign=\"top\" class=\"dashboardbg\">\n      ");
/* 3652 */                                         JspRuntimeLibrary.include(request, response, "/jsp/showCriticalMonitors.jsp", out, true);
/* 3653 */                                         out.write("\n\n      </td>\n    </tr>\n\t<tr class=\"dashboardbg\">\n\t<td colspan=\"2\" class=\"bodytext\" height=\"20\">\n\t\t&nbsp;&nbsp;");
/* 3654 */                                         out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.bottomnote"));
/* 3655 */                                         out.write("\n\t</td>\n\t</tr>\n  </table>\n  ");
/* 3656 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3657 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3661 */                                     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3662 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                     }
/*      */                                     
/* 3665 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3666 */                                     out.write("\n  <br>\n  </div>\n    </td></tr>\n\t<tr><td>\n\t<div id=\"DRAG_2\" style=\"display:;\">\n\t <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n           ");
/* 3667 */                                     JspRuntimeLibrary.include(request, response, "/jsp/ShowGroups.jsp", out, true);
/* 3668 */                                     out.write("<br>\n    </td>\n  </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td class=\"bodytext yellowgrayborder\">&nbsp;");
/* 3669 */                                     out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.availability"));
/* 3670 */                                     out.write("</td>\n    <td class=\"bodytext yellowgrayborder\"><img src=\"/images/icon_availability_down.gif\" > ");
/* 3671 */                                     out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down"));
/* 3672 */                                     out.write("</td>\n    <!--td class=\"bodytext\">Availability Down</td-->\n    <td class=\"bodytext yellowgrayborder\"><img src=\"/images/icon_availability_up.gif\" > ");
/* 3673 */                                     out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up"));
/* 3674 */                                     out.write("</td>\n\t<!--td class=\"bodytext\">Availability Up</td-->\n\t <td class=\"bodytext yellowgrayborder\"><img src=\"/images/icon_availability_unknown.gif\" > ");
/* 3675 */                                     out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown"));
/* 3676 */                                     out.write("</td>\n\t<!--td class=\"bodytext\">Status Unknown</td-->\n\t<td width=100>&nbsp;</td>\n\t <td class=\"bodytext yellowgrayborder\" align=\"right\">");
/* 3677 */                                     out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.health"));
/* 3678 */                                     out.write(": &nbsp;&nbsp;</td>\n\t    <td class=\"bodytext yellowgrayborder\"><img src=\"/images/icon_health_critical.gif\"> ");
/* 3679 */                                     out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical"));
/* 3680 */                                     out.write("</td>\n\t    <!--td class=\"bodytext\">Critical Health</td-->\n\t    <td class=\"bodytext yellowgrayborder\"><img src=\"/images/icon_health_warning.gif\" > ");
/* 3681 */                                     out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning"));
/* 3682 */                                     out.write("</td>\n\t\t<!--td class=\"bodytext\">Warning Health</td-->\n\t\t<td class=\"bodytext yellowgrayborder\"><img src=\"/images/icon_health_clear.gif\"> ");
/* 3683 */                                     out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear"));
/* 3684 */                                     out.write("</td>\n\t<!--td class=\"bodytext\">Clear Health</td-->\n  </tr>\n\n</table>\n\t<br>\n\t</div>\n</td></tr>\n\n\n\n<tr><td>\n       <div id=\"DRAG_4\" style=\"border: 0pt none ;\" >\n");
/* 3685 */                                     out.write("<!--$Id$-->\n\n\n\n\n\n<script language=\"JavaScript1.2\" src=\"/webclient/fault/js/fault.js\"></script>\n");
/* 3686 */                                     if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                       return;
/* 3688 */                                     out.write(10);
/*      */                                     
/* 3690 */                                     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3691 */                                     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3692 */                                     _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f10);
/*      */                                     
/* 3694 */                                     _jspx_th_c_005fif_005f14.setTest("${param.method !='showApplications'}");
/* 3695 */                                     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3696 */                                     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                       for (;;) {
/* 3698 */                                         out.write("\n<script>\nfunction hideAllalarmPages()\n{\ndocument.getElementById(\"recentalarms\").style.display='none';\ndocument.getElementById(\"recentevents\").style.display='none';\ndocument.getElementById(\"recentdowntimes\").style.display='none';\n}\nfunction getRecentEvents(resourceid)\n{\nhideAllalarmPages();\ndocument.getElementById(\"recentevents\").style.display=\"inline\";\nvar url = \"/showapplication.do?method=recentEvents&resourceid=\"+resourceid;\nvar ajax1=getHTTPObject();\najax1.onreadystatechange =function () { setEventContent(ajax1);} ;//No I18N\najax1.open(\"GET\", url, true);//No I18N\najax1.send(null);\n}\nfunction getRecentAlarms(resourceid)\n{\nhideAllalarmPages();\ndocument.getElementById(\"recentalarms\").style.display='inline';\ndocument.getElementById(\"loadingg\").style.display=\"none\";\n}\nfunction setEventContent(ajax1)\n{\n        if(ajax1.readyState == 4)\n        {\n           if( ajax1.status == 200)\n           {\n               document.getElementById(\"recentevents\").innerHTML = ajax1.responseText;\n\t\tdocument.getElementById(\"recentevents\").style.display='inline';\n");
/* 3699 */                                         out.write("                document.getElementById(\"loadingg\").style.display=\"none\";\n           }\n        }\n\n\n}\nfunction getRecentDowntimes(resourceid)\n{\n        hideAllalarmPages();\n        var url = \"/showapplication.do?method=recentdownTimes&resourceid=\"+resourceid;\n\t$('#recentdowntimes').load(url, function() {//No I18N\n\tdocument.getElementById(\"recentdowntimes\").style.display=\"inline\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n\t});\n}\n\n</script>\n");
/* 3700 */                                         request.setAttribute("fullpercent", "true");
/* 3701 */                                         out.write(10);
/* 3702 */                                         JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.common.recentalerts.text,am.webclient.common.eventsummary.text,am.webclient.common.recentdowntimes.text", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.common.recentalerts.text,am.webclient.common.eventsummary.text,am.webclient.common.recentdowntimes.text", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getRecentAlarms,getRecentEvents,getRecentDowntimes", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.common.recentalerts.text", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(request.getParameter("haid")), request.getCharacterEncoding()), out, true);
/* 3703 */                                         out.write("\n<table><tr><td height=\"7\"></td></tr></table>\n<div id=\"recentevents\" style=\"dsplay:none\">\n</div>\n<div id=\"recentdowntimes\" style=\"dsplay:none\">\n</div>\n");
/* 3704 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3705 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3709 */                                     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3710 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                     }
/*      */                                     
/* 3713 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3714 */                                     out.write("\n\n<div id=\"recentalarms\">\n");
/*      */                                     
/* 3716 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3717 */                                     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3718 */                                     _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fif_005f10);
/*      */                                     
/* 3720 */                                     _jspx_th_logic_005fnotEmpty_005f0.setName("recent5Alarms");
/* 3721 */                                     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3722 */                                     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                       for (;;) {
/* 3724 */                                         out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  align=\"left\" class=\"lrtbdarkborder\">\n<tr >\n ");
/*      */                                         
/* 3726 */                                         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3727 */                                         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3728 */                                         _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/* 3729 */                                         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3730 */                                         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                           for (;;)
/*      */                                           {
/* 3733 */                                             WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3734 */                                             _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3735 */                                             _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                             
/* 3737 */                                             _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showApplications'}");
/* 3738 */                                             int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3739 */                                             if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                               for (;;) {
/* 3741 */                                                 out.write("\n<td height=\"26\" colspan=\"6\" class=\"dragndroptblheading\" title=\"");
/* 3742 */                                                 out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 3743 */                                                 out.write(34);
/* 3744 */                                                 out.write(62);
/* 3745 */                                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.title"));
/* 3746 */                                                 out.write("</td>\n");
/* 3747 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3748 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3752 */                                             if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3753 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                             }
/*      */                                             
/* 3756 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3757 */                                             out.write(10);
/*      */                                             
/* 3759 */                                             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3760 */                                             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3761 */                                             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 3762 */                                             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3763 */                                             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                               for (;;) {
/* 3765 */                                                 out.write("\n<td height=\"26\" colspan=\"6\" class=\"tableheadingbborder\">");
/* 3766 */                                                 out.print(FormatUtil.getString("am.webclient.alerttab.recentcrictical.text"));
/* 3767 */                                                 out.write("</td>\n");
/* 3768 */                                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3769 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3773 */                                             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3774 */                                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                             }
/*      */                                             
/* 3777 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3778 */                                             out.write(10);
/* 3779 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3780 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3784 */                                         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3785 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                         }
/*      */                                         
/* 3788 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3789 */                                         out.write("\n</tr>\n\n  <TR >\n\n   <TD width=\"28%\"  class=\"columnheading\" > ");
/* 3790 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 3791 */                                         out.write(" </TD>\n    <TD width=\"10%\"  class=\"columnheading\"> ");
/* 3792 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.type"));
/* 3793 */                                         out.write(" </TD>\n     <TD  width=\"7%\" align=\"left\"  class=\"columnheading\" > ");
/* 3794 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 3795 */                                         out.write(" </TD>\n    <TD  width=\"23%\" class=\"columnheading\"> ");
/* 3796 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 3797 */                                         out.write("</TD>\n    <td   width=\"17%\" class=\"columnheading\" >");
/* 3798 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/* 3799 */                                         out.write(" </TD >\n    <td   width=\"15%\" class=\"columnheading\" > ");
/* 3800 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.technician"));
/* 3801 */                                         out.write("</TD >\n  </TR>\n\n");
/* 3802 */                                         int j = 0;
/* 3803 */                                         out.write("\n   ");
/*      */                                         
/* 3805 */                                         HashMap extDeviceMap = null;
/* 3806 */                                         if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*      */                                         {
/* 3808 */                                           extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink(false);
/*      */                                         }
/* 3810 */                                         HashMap<String, String> trimmedInfo = (HashMap)request.getAttribute("trimmedChildMonitorInfo");
/*      */                                         
/* 3812 */                                         out.write("\n\n  ");
/*      */                                         
/* 3814 */                                         IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.get(IterateTag.class);
/* 3815 */                                         _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 3816 */                                         _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                         
/* 3818 */                                         _jspx_th_logic_005fiterate_005f2.setName("recent5Alarms");
/*      */                                         
/* 3820 */                                         _jspx_th_logic_005fiterate_005f2.setId("recentAlarm");
/*      */                                         
/* 3822 */                                         _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 3823 */                                         int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 3824 */                                         if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 3825 */                                           ArrayList recentAlarm = null;
/* 3826 */                                           if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3827 */                                             out = _jspx_page_context.pushBody();
/* 3828 */                                             _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 3829 */                                             _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                           }
/* 3831 */                                           recentAlarm = (ArrayList)_jspx_page_context.findAttribute("recentAlarm");
/*      */                                           for (;;) {
/* 3833 */                                             out.write(10);
/* 3834 */                                             out.write(32);
/* 3835 */                                             out.write(32);
/*      */                                             
/* 3837 */                                             String alarm = (String)recentAlarm.get(1);
/* 3838 */                                             if ((alarm.trim().equals("1")) || (alarm.trim().equals("4")))
/*      */                                             {
/* 3840 */                                               j++;
/* 3841 */                                               String wsSeverity = (String)recentAlarm.get(0);
/* 3842 */                                               String category = (String)recentAlarm.get(6);
/* 3843 */                                               String sourcetype = (String)recentAlarm.get(7);
/* 3844 */                                               String annotation = (String)recentAlarm.get(10);
/* 3845 */                                               String mmessage = (String)recentAlarm.get(3);
/* 3846 */                                               mmessage = ExtProdUtil.decodeString(mmessage);
/* 3847 */                                               mmessage = mmessage.replaceAll("\"", "&quot;");
/*      */                                               
/* 3849 */                                               String sourcename = (String)recentAlarm.get(4);
/* 3850 */                                               sourcename = sourcename.replaceAll("\"", "&quot;");
/* 3851 */                                               String resourcetype1 = (String)recentAlarm.get(7);
/*      */                                               
/* 3853 */                                               String url = null;
/*      */                                               
/* 3855 */                                               if (resourcetype1.equalsIgnoreCase("HAI"))
/*      */                                               {
/* 3857 */                                                 url = "/showapplication.do?haid=" + category + "&method=showApplication";
/*      */ 
/*      */ 
/*      */                                               }
/* 3861 */                                               else if ((extDeviceMap != null) && (extDeviceMap.get(category) != null))
/*      */                                               {
/* 3863 */                                                 url = (String)extDeviceMap.get(category);
/*      */                                               }
/* 3865 */                                               else if (sourcetype.contains("Site24x7"))
/*      */                                               {
/* 3867 */                                                 url = "/extDeviceAction.do?method=site24x7Reports&resourceid=" + category;
/*      */                                               }
/* 3869 */                                               else if (com.adventnet.appmanager.util.ChildMOHandler.isChildMonitorTypeSupportedForMG(sourcetype))
/*      */                                               {
/* 3871 */                                                 url = "/showapplication.do?resId=" + category + "&method=showChildApplicationDetail";
/*      */                                               } else {
/* 3873 */                                                 url = "/showresource.do?resourceid=" + category + "&type=" + resourcetype1 + "&moname=" + sourcename + "&method=showdetails&resourcename=" + (String)recentAlarm.get(4) + "";
/*      */                                               }
/*      */                                               
/* 3876 */                                               if (j % 2 == 0)
/*      */                                               {
/* 3878 */                                                 wsSeverity = "class=\"yellowgrayborder\"";
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 3882 */                                                 wsSeverity = "class=\"whitegrayborder\"";
/*      */                                               }
/*      */                                               
/*      */ 
/* 3886 */                                               out.write("\n\t\t\t<!--\n\t\t\tif ((Integer.parseInt((String)recentAlarm.get(1))==1))\n\t\t\t{\n\t\t\t\twsSeverity=\"class=\\\"errorgrayborder\\\"\";\n\t\t\t}\n\t\t-->\n\t\t\t<TR class=\"mgsummary-links\" onmouseover=\"this.className='mgsummaryHeaderHover'\"  onmouseout=\"this.className='mgsummaryHeader'\">\n\n\t\t\t<TD ");
/* 3887 */                                               out.print(wsSeverity);
/* 3888 */                                               out.write(" >\n\t\t\t");
/*      */                                               
/* 3890 */                                               if ((annotation != null) && (annotation.equalsIgnoreCase("YES")))
/*      */                                               {
/* 3892 */                                                 out.write("\n\t\t\t\t<a href=\"javascript:MM_openBrWindow('/fault/AlarmDetails.do?method=viewAnnotationAndHistory&displayname=Annotations&popup=true&entity=");
/* 3893 */                                                 out.print(recentAlarm.get(2));
/* 3894 */                                                 out.write("','','resizable=yes,width=650,height=200')\" title=\"");
/* 3895 */                                                 out.print(FormatUtil.getString("am.webclient.alerttab.recent5alarm.viewannotation.text"));
/* 3896 */                                                 out.write("\"><img src=\"/images/icon_alert_annotated.gif\" style=\"position:relative; right:1px; top:3px;\"border=\"0\"></a>\n\t\t\t\t");
/*      */                                               } else {
/* 3898 */                                                 out.write("\n\t\t\t\t\t<a href=\"javascript:openWindow('/fault/AlarmDetails.do?method=doAnnotate&entity=");
/* 3899 */                                                 out.print(recentAlarm.get(2));
/* 3900 */                                                 out.write("&userName=");
/* 3901 */                                                 out.print(request.getRemoteUser());
/* 3902 */                                                 out.write("&source=");
/* 3903 */                                                 out.print(recentAlarm.get(6));
/* 3904 */                                                 out.write("&category=");
/* 3905 */                                                 out.print(recentAlarm.get(0));
/* 3906 */                                                 out.write("&displayname=");
/* 3907 */                                                 out.print(recentAlarm.get(4));
/* 3908 */                                                 out.write("&redirectto=");
/* 3909 */                                                 out.print(URLEncoder.encode(encodeurl));
/* 3910 */                                                 out.write("&fromIcon=true','annotate','450','188')\" title=\"");
/* 3911 */                                                 out.print(FormatUtil.getString("am.webclient.alerttab.recent5alarm.addannotation.text"));
/* 3912 */                                                 out.write("\"><img src=\"/images/icon_alert_add_annotation.gif\" border=\"0\" ></a>\n\t\t\t\t\t");
/*      */                                               }
/* 3914 */                                               out.write("\n\t\t\t\t\t");
/*      */                                               
/* 3916 */                                               String extDispName = ExtProdUtil.decodeString((String)recentAlarm.get(4));
/* 3917 */                                               extDispName = extDispName.replaceAll("\"", "&quot;");
/* 3918 */                                               extDispName = extDispName.replaceAll("<", "&lt;");
/* 3919 */                                               extDispName = extDispName.replaceAll(">", "&gt;");
/* 3920 */                                               String trimmedExtDispName = getTrimmedText(extDispName, 40);
/* 3921 */                                               if (((extDeviceMap != null) && (extDeviceMap.get(category) != null)) || (sourcetype.contains("Site24x7")))
/*      */                                               {
/*      */ 
/*      */ 
/* 3925 */                                                 out.write("\n\t\t\t\t\t\t<a class=\"mgsummary-links\" title=");
/* 3926 */                                                 out.print(extDispName);
/* 3927 */                                                 out.write(" href=\"javascript:MM_openBrWindow('");
/* 3928 */                                                 out.print(url);
/* 3929 */                                                 out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" > ");
/* 3930 */                                                 out.print(trimmedExtDispName);
/* 3931 */                                                 out.write("</a></TD>\n\t\t\t\t\t");
/*      */                                               }
/* 3933 */                                               else if (com.adventnet.appmanager.util.ChildMOHandler.isChildMonitorTypeSupportedForMG(sourcetype)) {
/* 3934 */                                                 String toolTipTitle = extDispName;
/* 3935 */                                                 if ((trimmedInfo != null) && (trimmedInfo.containsKey(category)))
/*      */                                                 {
/* 3937 */                                                   trimmedExtDispName = ExtProdUtil.decodeString((String)trimmedInfo.get(category));
/*      */                                                 }
/*      */                                                 
/* 3940 */                                                 out.write("\n\t\t\t\t\t\t<a class=\"mgsummary-links\" title=\"");
/* 3941 */                                                 out.print(toolTipTitle);
/* 3942 */                                                 out.write("\"  href=\"javascript:MM_openBrWindow('");
/* 3943 */                                                 out.print(url);
/* 3944 */                                                 out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" > ");
/* 3945 */                                                 out.print(trimmedExtDispName);
/* 3946 */                                                 out.write("</a></TD>\n\t\t\t\t\t");
/*      */                                               } else {
/* 3948 */                                                 out.write("\n\t\t\t\t\t   <a class=\"mgsummary-links\" title=");
/* 3949 */                                                 out.print(extDispName);
/* 3950 */                                                 out.write("  href=\"");
/* 3951 */                                                 out.print(url);
/* 3952 */                                                 out.write("\" > ");
/* 3953 */                                                 out.print(trimmedExtDispName);
/* 3954 */                                                 out.write("</a></TD>\n\t\t\t\t\t");
/*      */                                               }
/* 3956 */                                               out.write("\n\t\t\t\t\t\t");
/*      */                                               
/*      */ 
/* 3959 */                                               pageContext.setAttribute("type", sourcetype);
/* 3960 */                                               String ResType = (String)recentAlarm.get(9);
/* 3961 */                                               if (ResType.equals("Monitor Group"))
/*      */                                               {
/*      */ 
/* 3964 */                                                 ResType = getGroupType(category);
/*      */                                               }
/*      */                                               
/*      */ 
/* 3968 */                                               out.write("\n\t\t\t\t\t\t<TD ");
/* 3969 */                                               out.print(wsSeverity);
/* 3970 */                                               out.write(" width=\"10%\"> <span class=\"footer\">");
/* 3971 */                                               out.print(FormatUtil.getString(ResType));
/* 3972 */                                               out.write("</span></TD>\n\t\t\t\t\t\t<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\n\n\t\t\t\t\t\t<TD ");
/* 3973 */                                               out.print(wsSeverity);
/* 3974 */                                               out.write(" align=\"left\" width=\"5%\"><a  href=\"javascript:void(0)\" onmouseover=\"ddrivetip(this,event,'");
/* 3975 */                                               out.print(mmessage);
/* 3976 */                                               out.write("<br>'+v+'");
/* 3977 */                                               out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 3978 */                                               out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3979 */                                               out.print(recentAlarm.get(6));
/* 3980 */                                               out.write("&attributeid=");
/* 3981 */                                               out.print(recentAlarm.get(0));
/* 3982 */                                               out.write("')\"> ");
/* 3983 */                                               out.print(getSeverityImageForHealth((String)recentAlarm.get(1)));
/* 3984 */                                               out.write(" </a>");
/* 3985 */                                               out.write("\n\n\n\t\t\t\t\t\t</TD>\n\n                       ");
/*      */                                               
/* 3987 */                                               if ((extDeviceMap != null) && (extDeviceMap.get(category) != null))
/*      */                                               {
/*      */ 
/* 3990 */                                                 out.write("\n\t\t\t\t\t\t<TD ");
/* 3991 */                                                 out.print(wsSeverity);
/* 3992 */                                                 out.write(" > <a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&monitortype=");
/* 3993 */                                                 out.print(resourcetype1);
/* 3994 */                                                 out.write("&extDevice=true&entity=");
/* 3995 */                                                 out.print(recentAlarm.get(2));
/* 3996 */                                                 out.write("&source=");
/* 3997 */                                                 out.print(recentAlarm.get(4));
/* 3998 */                                                 out.write("&category=");
/* 3999 */                                                 out.print(recentAlarm.get(0));
/* 4000 */                                                 out.write("&redirectto=");
/* 4001 */                                                 out.print(encodeurl);
/* 4002 */                                                 out.write("\" class=\"mgsummary-links\"  title=\"");
/* 4003 */                                                 out.print(removeBr(mmessage));
/* 4004 */                                                 out.write(34);
/* 4005 */                                                 out.write(32);
/* 4006 */                                                 out.write(62);
/* 4007 */                                                 out.print(getTruncatedAlertMessage(mmessage));
/* 4008 */                                                 out.write(" </a></TD>\n\t\t\t\t\t   ");
/*      */                                               } else {
/* 4010 */                                                 out.write("\n\t\t\t\t\t   <TD ");
/* 4011 */                                                 out.print(wsSeverity);
/* 4012 */                                                 out.write(" > <a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&monitortype=");
/* 4013 */                                                 out.print(resourcetype1);
/* 4014 */                                                 out.write("&entity=");
/* 4015 */                                                 out.print(recentAlarm.get(2));
/* 4016 */                                                 out.write("&source=");
/* 4017 */                                                 out.print(recentAlarm.get(4));
/* 4018 */                                                 out.write("&category=");
/* 4019 */                                                 out.print(recentAlarm.get(0));
/* 4020 */                                                 out.write("&redirectto=");
/* 4021 */                                                 out.print(encodeurl);
/* 4022 */                                                 out.write("\" class=\"mgsummary-links\"  title=\"");
/* 4023 */                                                 out.print(removeBr((String)recentAlarm.get(3)));
/* 4024 */                                                 out.write(34);
/* 4025 */                                                 out.write(32);
/* 4026 */                                                 out.write(62);
/* 4027 */                                                 out.print(getTruncatedAlertMessage(mmessage));
/* 4028 */                                                 out.write(" </a\n\t\t\t\t\t   ></TD>\n\t\t\t\t\t   ");
/*      */                                               }
/* 4030 */                                               out.write("\n\t\t\t\t\t\t<TD ");
/* 4031 */                                               out.print(wsSeverity);
/* 4032 */                                               out.write(" align=\"left\" >");
/* 4033 */                                               out.print(formatDT((String)recentAlarm.get(5)));
/* 4034 */                                               out.write("\n\n\t\t\t\t\t\t</TD>\n\t\t\t\t\t\t");
/*      */                                               
/* 4036 */                                               if ((recentAlarm.get(11) == null) || (((String)recentAlarm.get(11)).equalsIgnoreCase("null")))
/*      */                                               {
/*      */ 
/* 4039 */                                                 out.write("\n\t\t\t\t\t\t\t\t<td ");
/* 4040 */                                                 out.print(wsSeverity);
/* 4041 */                                                 out.write(" title=\"");
/* 4042 */                                                 out.print(FormatUtil.getString("am.webclient.alerttab.recent5alarm.pickupalert.text"));
/* 4043 */                                                 out.write("\"><a href=\"/fault/AlarmOperations.do?methodCall=pickUpAlarm&selectedEntity=");
/* 4044 */                                                 out.print(recentAlarm.get(2));
/* 4045 */                                                 out.write("&redirectto=");
/* 4046 */                                                 out.print(encodeurl);
/* 4047 */                                                 out.write("&displayName=");
/* 4048 */                                                 out.print(recentAlarm.get(4));
/* 4049 */                                                 out.write("\" class=\"mgsummary-links\"><img src=\"/images/icon_alert_unacknowleged.gif\" border=\"0\" style=\"position:relative; top:3px; right:2px;\"><span class=\"mgsummary-links\">");
/* 4050 */                                                 out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4051 */                                                 out.write("</span></a></td>\n\t\t\t\t\t\t\t\t");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 4055 */                                                 out.write("\n\t\t\t\t\t\t\t\t<td ");
/* 4056 */                                                 out.print(wsSeverity);
/* 4057 */                                                 out.write(" title='");
/* 4058 */                                                 out.print(FormatUtil.getString("am.webclient.alerttab.recent5alarm.pickupalertacknowledgement.text", new String[] { (String)recentAlarm.get(11), (String)recentAlarm.get(11) }));
/* 4059 */                                                 out.write("'><a href=\"/fault/AlarmOperations.do?methodCall=unPickAlarm&selectedEntity=");
/* 4060 */                                                 out.print(recentAlarm.get(2));
/* 4061 */                                                 out.write("&redirectto=");
/* 4062 */                                                 out.print(encodeurl);
/* 4063 */                                                 out.write("&displayName=");
/* 4064 */                                                 out.print(recentAlarm.get(4));
/* 4065 */                                                 out.write("\" class=\"arial10\" ><img src=\"/images/icon_alert_acknowleged.gif\" border=\"0\"><span class=\"mgsummary-links\">");
/* 4066 */                                                 out.print(recentAlarm.get(11));
/* 4067 */                                                 out.write("</span></a></td>\n\t\t\t\t\t\t\t\t");
/*      */                                               }
/* 4069 */                                               out.write("\n\t\t\t\t\t\t\t\t</TR>\n\t\t\t\t\t\t\t\t");
/*      */                                             }
/*      */                                             
/*      */ 
/* 4073 */                                             out.write(10);
/* 4074 */                                             out.write(32);
/* 4075 */                                             out.write(32);
/* 4076 */                                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 4077 */                                             recentAlarm = (ArrayList)_jspx_page_context.findAttribute("recentAlarm");
/* 4078 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4081 */                                           if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 4082 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4085 */                                         if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 4086 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                         }
/*      */                                         
/* 4089 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 4090 */                                         out.write("\n\n\n\n</table>\n\n");
/* 4091 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 4092 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4096 */                                     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 4097 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                     }
/*      */                                     
/* 4100 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 4101 */                                     out.write(10);
/*      */                                     
/* 4103 */                                     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4104 */                                     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 4105 */                                     _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fif_005f10);
/*      */                                     
/* 4107 */                                     _jspx_th_logic_005fempty_005f0.setName("recent5Alarms");
/* 4108 */                                     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 4109 */                                     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                                       for (;;) {
/* 4111 */                                         out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\"  class=\"lrtbdarkborder\">\n<tr>\n ");
/*      */                                         
/* 4113 */                                         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4114 */                                         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 4115 */                                         _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_logic_005fempty_005f0);
/* 4116 */                                         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 4117 */                                         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                           for (;;)
/*      */                                           {
/* 4120 */                                             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4121 */                                             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 4122 */                                             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                             
/* 4124 */                                             _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showApplications'}");
/* 4125 */                                             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 4126 */                                             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                               for (;;) {
/* 4128 */                                                 out.write("\n<td height=\"26\" class=\"dragndroptblheading\" title=\"");
/* 4129 */                                                 out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 4130 */                                                 out.write(34);
/* 4131 */                                                 out.write(62);
/* 4132 */                                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.title"));
/* 4133 */                                                 out.write("</td>\n");
/* 4134 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 4135 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 4139 */                                             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 4140 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                             }
/*      */                                             
/* 4143 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 4144 */                                             out.write(10);
/*      */                                             
/* 4146 */                                             OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4147 */                                             _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 4148 */                                             _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 4149 */                                             int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 4150 */                                             if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                               for (;;) {
/* 4152 */                                                 out.write("\n<td height=\"26\" class=\"tableheadingbborder\">");
/* 4153 */                                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.title"));
/* 4154 */                                                 out.write("</td>\n");
/* 4155 */                                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 4156 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 4160 */                                             if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 4161 */                                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                             }
/*      */                                             
/* 4164 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 4165 */                                             out.write(10);
/* 4166 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 4167 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4171 */                                         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 4172 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                         }
/*      */                                         
/* 4175 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 4176 */                                         out.write("\n\n</tr>\n\n\t\t  <tr>\n\t\t  <td  height=\"40\" class=\"tdindent\">\n\t\t  <span class=\"bodytext\">\n\t\t  ");
/* 4177 */                                         out.print(FormatUtil.getString("am.webclient.alerttab.norecentalarmmessage.text"));
/* 4178 */                                         out.write("\n\t\t  </span></td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t  <td class=\"tablebottom\">&nbsp;\n\n\t\t  </td>\n\t\t  </table>\n");
/* 4179 */                                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 4180 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4184 */                                     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 4185 */                                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                                     }
/*      */                                     
/* 4188 */                                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 4189 */                                     out.write("\n</div>\n");
/* 4190 */                                     out.write(10);
/* 4191 */                                     out.write("\n<br>\n</div>\n\n</td></tr></table><br>\n\n</br>\n</form>\n");
/* 4192 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4193 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4197 */                                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4198 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                 }
/*      */                                 
/* 4201 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4202 */                                 out.write("\n</div>\n<script>\n    var temp=Get_Cookie('am_applicationsview');\n    if(temp=='summary')\n        {\n          //  getHomeTabSnapshot();\n        }\n        else if(temp=='business')\n            {\n            }\n            else if(temp=='availability')\n                {\n                    getHomeTabAvailabilityData('HAI','null','null')\n                }\n                else if(temp=='performance')\n                {\n                    getHomePerformanceData('HAI','null','null')\n                }\n    </script>\n\n");
/* 4203 */                                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4204 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4207 */                               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4208 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4211 */                             if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4212 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                             }
/*      */                             
/* 4215 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4216 */                             out.write(32);
/* 4217 */                             if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                               return;
/* 4219 */                             out.write(32);
/* 4220 */                             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4221 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4225 */                         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4226 */                           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                         }
/*      */                         else {
/* 4229 */                           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4230 */                           out.write(10);
/*      */                         }
/* 4232 */                       } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4233 */         out = _jspx_out;
/* 4234 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4235 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 4236 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4239 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4245 */     PageContext pageContext = _jspx_page_context;
/* 4246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4248 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4249 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4250 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4252 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4254 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 4255 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4256 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4257 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4258 */       return true;
/*      */     }
/* 4260 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4261 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4266 */     PageContext pageContext = _jspx_page_context;
/* 4267 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4269 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4270 */     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 4271 */     _jspx_th_logic_005fnotPresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4273 */     _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/* 4274 */     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 4275 */     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */       for (;;) {
/* 4277 */         out.write("<br>");
/* 4278 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 4279 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4283 */     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 4284 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 4285 */       return true;
/*      */     }
/* 4287 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 4288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4293 */     PageContext pageContext = _jspx_page_context;
/* 4294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4296 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4297 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4298 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4300 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 4302 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 4303 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4304 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4305 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4306 */       return true;
/*      */     }
/* 4308 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4309 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4314 */     PageContext pageContext = _jspx_page_context;
/* 4315 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4317 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4318 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4319 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4321 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 4322 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4323 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 4325 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 4326 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4327 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4331 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4332 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4333 */       return true;
/*      */     }
/* 4335 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4336 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4341 */     PageContext pageContext = _jspx_page_context;
/* 4342 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4344 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4345 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4346 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4348 */     _jspx_th_c_005fif_005f8.setTest("${param.period == '1'}");
/* 4349 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4350 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 4352 */         out.write("SELECTED");
/* 4353 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4354 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4358 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4359 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4360 */       return true;
/*      */     }
/* 4362 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4368 */     PageContext pageContext = _jspx_page_context;
/* 4369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4371 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4372 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4373 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4375 */     _jspx_th_c_005fif_005f9.setTest("${param.period == '2'}");
/* 4376 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4377 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 4379 */         out.write("SELECTED");
/* 4380 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4381 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4385 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4386 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4387 */       return true;
/*      */     }
/* 4389 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4390 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4395 */     PageContext pageContext = _jspx_page_context;
/* 4396 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4398 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4399 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4400 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4402 */     _jspx_th_c_005fif_005f11.setTest("${param.alertMessage!=null && param.alertMessage!=''}");
/* 4403 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4404 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 4406 */         out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n  <tr>\n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/* 4407 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 4408 */           return true;
/* 4409 */         out.write("</td>\n  </tr>\n</table>\n<br>\n");
/* 4410 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4411 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4415 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4416 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4417 */       return true;
/*      */     }
/* 4419 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4420 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4425 */     PageContext pageContext = _jspx_page_context;
/* 4426 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4428 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4429 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4430 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4432 */     _jspx_th_c_005fout_005f1.setValue("${param.alertMessage}");
/* 4433 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4434 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4435 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4436 */       return true;
/*      */     }
/* 4438 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4439 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4444 */     PageContext pageContext = _jspx_page_context;
/* 4445 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4447 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4448 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4449 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4451 */     _jspx_th_c_005fif_005f13.setTest("${param.method =='showApplications'}");
/* 4452 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4453 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 4455 */         out.write(10);
/* 4456 */         if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_c_005fif_005f13, _jspx_page_context))
/* 4457 */           return true;
/* 4458 */         out.write(10);
/* 4459 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4460 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4464 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4465 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4466 */       return true;
/*      */     }
/* 4468 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4469 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4474 */     PageContext pageContext = _jspx_page_context;
/* 4475 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4477 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4478 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 4479 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 4481 */     _jspx_th_logic_005fpresent_005f4.setRole("OPERATOR");
/* 4482 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 4483 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 4485 */         out.write("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>");
/* 4486 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 4487 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4491 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 4492 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4493 */       return true;
/*      */     }
/* 4495 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4496 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4501 */     PageContext pageContext = _jspx_page_context;
/* 4502 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4504 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4505 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4506 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4508 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 4510 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 4511 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4512 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4513 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4514 */       return true;
/*      */     }
/* 4516 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4517 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\applications_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */