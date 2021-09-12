/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
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
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.FileTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class Bulk_005fAddMonitors_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   52 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   55 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   56 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   57 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   64 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   69 */     ArrayList list = null;
/*   70 */     StringBuffer sbf = new StringBuffer();
/*   71 */     ManagedApplication mo = new ManagedApplication();
/*   72 */     if (distinct)
/*      */     {
/*   74 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   78 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   81 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   83 */       ArrayList row = (ArrayList)list.get(i);
/*   84 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   85 */       if (distinct) {
/*   86 */         sbf.append(row.get(0));
/*      */       } else
/*   88 */         sbf.append(row.get(1));
/*   89 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   92 */     return sbf.toString(); }
/*      */   
/*   94 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   97 */     if (severity == null)
/*      */     {
/*   99 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  101 */     if (severity.equals("5"))
/*      */     {
/*  103 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  105 */     if (severity.equals("1"))
/*      */     {
/*  107 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  112 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  119 */     if (severity == null)
/*      */     {
/*  121 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  123 */     if (severity.equals("1"))
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  127 */     if (severity.equals("4"))
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  131 */     if (severity.equals("5"))
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  138 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  144 */     if (severity == null)
/*      */     {
/*  146 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  148 */     if (severity.equals("5"))
/*      */     {
/*  150 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  152 */     if (severity.equals("1"))
/*      */     {
/*  154 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  158 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  164 */     if (severity == null)
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  168 */     if (severity.equals("1"))
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  172 */     if (severity.equals("4"))
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  176 */     if (severity.equals("5"))
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  182 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  188 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  194 */     if (severity == 5)
/*      */     {
/*  196 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  198 */     if (severity == 1)
/*      */     {
/*  200 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  205 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  211 */     if (severity == null)
/*      */     {
/*  213 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  215 */     if (severity.equals("5"))
/*      */     {
/*  217 */       if (isAvailability) {
/*  218 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  221 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  224 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  226 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  228 */     if (severity.equals("1"))
/*      */     {
/*  230 */       if (isAvailability) {
/*  231 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  234 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  241 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  248 */     if (severity == null)
/*      */     {
/*  250 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  252 */     if (severity.equals("5"))
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  256 */     if (severity.equals("4"))
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  260 */     if (severity.equals("1"))
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  267 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  273 */     if (severity == null)
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  277 */     if (severity.equals("5"))
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  281 */     if (severity.equals("4"))
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  285 */     if (severity.equals("1"))
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  292 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  299 */     if (severity == null)
/*      */     {
/*  301 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  303 */     if (severity.equals("5"))
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  307 */     if (severity.equals("4"))
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  311 */     if (severity.equals("1"))
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  318 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  326 */     StringBuffer out = new StringBuffer();
/*  327 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  328 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  329 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  330 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  331 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  332 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  333 */     out.append("</tr>");
/*  334 */     out.append("</form></table>");
/*  335 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  342 */     if (val == null)
/*      */     {
/*  344 */       return "-";
/*      */     }
/*      */     
/*  347 */     String ret = FormatUtil.formatNumber(val);
/*  348 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  349 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  352 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  356 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  364 */     StringBuffer out = new StringBuffer();
/*  365 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  366 */     out.append("<tr>");
/*  367 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  369 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  371 */     out.append("</tr>");
/*  372 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  376 */       if (j % 2 == 0)
/*      */       {
/*  378 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  382 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  385 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  387 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  390 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  394 */       out.append("</tr>");
/*      */     }
/*  396 */     out.append("</table>");
/*  397 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  398 */     out.append("<tr>");
/*  399 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  400 */     out.append("</tr>");
/*  401 */     out.append("</table>");
/*  402 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  408 */     StringBuffer out = new StringBuffer();
/*  409 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  410 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  411 */     out.append("<tr>");
/*  412 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  413 */     out.append("<tr>");
/*  414 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  415 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  416 */     out.append("</tr>");
/*  417 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  420 */       out.append("<tr>");
/*  421 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  422 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  423 */       out.append("</tr>");
/*      */     }
/*      */     
/*  426 */     out.append("</table>");
/*  427 */     out.append("</table>");
/*  428 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  433 */     if (severity.equals("0"))
/*      */     {
/*  435 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  439 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  446 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  459 */     StringBuffer out = new StringBuffer();
/*  460 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  461 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  463 */       out.append("<tr>");
/*  464 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  465 */       out.append("</tr>");
/*      */       
/*      */ 
/*  468 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  470 */         String borderclass = "";
/*      */         
/*      */ 
/*  473 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  475 */         out.append("<tr>");
/*      */         
/*  477 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  478 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  479 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  485 */     out.append("</table><br>");
/*  486 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  487 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  489 */       List sLinks = secondLevelOfLinks[0];
/*  490 */       List sText = secondLevelOfLinks[1];
/*  491 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  494 */         out.append("<tr>");
/*  495 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  496 */         out.append("</tr>");
/*  497 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  499 */           String borderclass = "";
/*      */           
/*      */ 
/*  502 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  504 */           out.append("<tr>");
/*      */           
/*  506 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  507 */           if (sLinks.get(i).toString().length() == 0) {
/*  508 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  511 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  513 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  517 */     out.append("</table>");
/*  518 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  525 */     StringBuffer out = new StringBuffer();
/*  526 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  527 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  529 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  531 */         out.append("<tr>");
/*  532 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  533 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  537 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  539 */           String borderclass = "";
/*      */           
/*      */ 
/*  542 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  544 */           out.append("<tr>");
/*      */           
/*  546 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  547 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  548 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  551 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  554 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  559 */     out.append("</table><br>");
/*  560 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  561 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  563 */       List sLinks = secondLevelOfLinks[0];
/*  564 */       List sText = secondLevelOfLinks[1];
/*  565 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  568 */         out.append("<tr>");
/*  569 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  570 */         out.append("</tr>");
/*  571 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  573 */           String borderclass = "";
/*      */           
/*      */ 
/*  576 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  578 */           out.append("<tr>");
/*      */           
/*  580 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  581 */           if (sLinks.get(i).toString().length() == 0) {
/*  582 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  585 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  587 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  591 */     out.append("</table>");
/*  592 */     return out.toString();
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
/*  605 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  608 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  611 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  614 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  617 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  620 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  623 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  626 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  634 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  639 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  644 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  649 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  654 */     if (val != null)
/*      */     {
/*  656 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  660 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  665 */     if (val == null) {
/*  666 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  670 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  675 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  681 */     if (val != null)
/*      */     {
/*  683 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  687 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  693 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  698 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  702 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  707 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  712 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  717 */     String hostaddress = "";
/*  718 */     String ip = request.getHeader("x-forwarded-for");
/*  719 */     if (ip == null)
/*  720 */       ip = request.getRemoteAddr();
/*  721 */     InetAddress add = null;
/*  722 */     if (ip.equals("127.0.0.1")) {
/*  723 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  727 */       add = InetAddress.getByName(ip);
/*      */     }
/*  729 */     hostaddress = add.getHostName();
/*  730 */     if (hostaddress.indexOf('.') != -1) {
/*  731 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  732 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  736 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  741 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  747 */     if (severity == null)
/*      */     {
/*  749 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  751 */     if (severity.equals("5"))
/*      */     {
/*  753 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  755 */     if (severity.equals("1"))
/*      */     {
/*  757 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  762 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  767 */     ResultSet set = null;
/*  768 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  769 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  771 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  772 */       if (set.next()) { String str1;
/*  773 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  774 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  777 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  782 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  785 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  787 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  791 */     StringBuffer rca = new StringBuffer();
/*  792 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  793 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  796 */     int rcalength = key.length();
/*  797 */     String split = "6. ";
/*  798 */     int splitPresent = key.indexOf(split);
/*  799 */     String div1 = "";String div2 = "";
/*  800 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  802 */       if (rcalength > 180) {
/*  803 */         rca.append("<span class=\"rca-critical-text\">");
/*  804 */         getRCATrimmedText(key, rca);
/*  805 */         rca.append("</span>");
/*      */       } else {
/*  807 */         rca.append("<span class=\"rca-critical-text\">");
/*  808 */         rca.append(key);
/*  809 */         rca.append("</span>");
/*      */       }
/*  811 */       return rca.toString();
/*      */     }
/*  813 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  814 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  815 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  816 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  817 */     getRCATrimmedText(div1, rca);
/*  818 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  821 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  822 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  823 */     getRCATrimmedText(div2, rca);
/*  824 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  826 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  831 */     String[] st = msg.split("<br>");
/*  832 */     for (int i = 0; i < st.length; i++) {
/*  833 */       String s = st[i];
/*  834 */       if (s.length() > 180) {
/*  835 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  837 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  841 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  842 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  844 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  848 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  849 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  850 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  853 */       if (key == null) {
/*  854 */         return ret;
/*      */       }
/*      */       
/*  857 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  858 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  861 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  862 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  863 */       set = AMConnectionPool.executeQueryStmt(query);
/*  864 */       if (set.next())
/*      */       {
/*  866 */         String helpLink = set.getString("LINK");
/*  867 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  870 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  876 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  895 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  886 */         if (set != null) {
/*  887 */           AMConnectionPool.closeStatement(set);
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
/*  901 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  902 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  904 */       String entityStr = (String)keys.nextElement();
/*  905 */       String mmessage = temp.getProperty(entityStr);
/*  906 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  907 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  909 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  915 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  916 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  918 */       String entityStr = (String)keys.nextElement();
/*  919 */       String mmessage = temp.getProperty(entityStr);
/*  920 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  921 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  923 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  928 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  938 */     String des = new String();
/*  939 */     while (str.indexOf(find) != -1) {
/*  940 */       des = des + str.substring(0, str.indexOf(find));
/*  941 */       des = des + replace;
/*  942 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  944 */     des = des + str;
/*  945 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  952 */       if (alert == null)
/*      */       {
/*  954 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  956 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  958 */         return "&nbsp;";
/*      */       }
/*      */       
/*  961 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  963 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  966 */       int rcalength = test.length();
/*  967 */       if (rcalength < 300)
/*      */       {
/*  969 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  973 */       StringBuffer out = new StringBuffer();
/*  974 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  975 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  976 */       out.append("</div>");
/*  977 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  978 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  979 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  984 */       ex.printStackTrace();
/*      */     }
/*  986 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  992 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  997 */     ArrayList attribIDs = new ArrayList();
/*  998 */     ArrayList resIDs = new ArrayList();
/*  999 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1001 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1003 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1005 */       String resourceid = "";
/* 1006 */       String resourceType = "";
/* 1007 */       if (type == 2) {
/* 1008 */         resourceid = (String)row.get(0);
/* 1009 */         resourceType = (String)row.get(3);
/*      */       }
/* 1011 */       else if (type == 3) {
/* 1012 */         resourceid = (String)row.get(0);
/* 1013 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1016 */         resourceid = (String)row.get(6);
/* 1017 */         resourceType = (String)row.get(7);
/*      */       }
/* 1019 */       resIDs.add(resourceid);
/* 1020 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1021 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1023 */       String healthentity = null;
/* 1024 */       String availentity = null;
/* 1025 */       if (healthid != null) {
/* 1026 */         healthentity = resourceid + "_" + healthid;
/* 1027 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1030 */       if (availid != null) {
/* 1031 */         availentity = resourceid + "_" + availid;
/* 1032 */         entitylist.add(availentity);
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
/* 1046 */     Properties alert = getStatus(entitylist);
/* 1047 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1052 */     int size = monitorList.size();
/*      */     
/* 1054 */     String[] severity = new String[size];
/*      */     
/* 1056 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1058 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1059 */       String resourceName1 = (String)row1.get(7);
/* 1060 */       String resourceid1 = (String)row1.get(6);
/* 1061 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1062 */       if (severity[j] == null)
/*      */       {
/* 1064 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1068 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1070 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1072 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1075 */         if (sev > 0) {
/* 1076 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1077 */           monitorList.set(k, monitorList.get(j));
/* 1078 */           monitorList.set(j, t);
/* 1079 */           String temp = severity[k];
/* 1080 */           severity[k] = severity[j];
/* 1081 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1087 */     int z = 0;
/* 1088 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1091 */       int i = 0;
/* 1092 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1095 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1099 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1103 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1105 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1108 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1112 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1115 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1116 */       String resourceName1 = (String)row1.get(7);
/* 1117 */       String resourceid1 = (String)row1.get(6);
/* 1118 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1119 */       if (hseverity[j] == null)
/*      */       {
/* 1121 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1126 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1128 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1131 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1134 */         if (hsev > 0) {
/* 1135 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1136 */           monitorList.set(k, monitorList.get(j));
/* 1137 */           monitorList.set(j, t);
/* 1138 */           String temp1 = hseverity[k];
/* 1139 */           hseverity[k] = hseverity[j];
/* 1140 */           hseverity[j] = temp1;
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
/* 1152 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1153 */     boolean forInventory = false;
/* 1154 */     String trdisplay = "none";
/* 1155 */     String plusstyle = "inline";
/* 1156 */     String minusstyle = "none";
/* 1157 */     String haidTopLevel = "";
/* 1158 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1160 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1162 */         haidTopLevel = request.getParameter("haid");
/* 1163 */         forInventory = true;
/* 1164 */         trdisplay = "table-row;";
/* 1165 */         plusstyle = "none";
/* 1166 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1173 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1176 */     ArrayList listtoreturn = new ArrayList();
/* 1177 */     StringBuffer toreturn = new StringBuffer();
/* 1178 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1179 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1180 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1182 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1184 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1185 */       String childresid = (String)singlerow.get(0);
/* 1186 */       String childresname = (String)singlerow.get(1);
/* 1187 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1188 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1189 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1190 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1191 */       String unmanagestatus = (String)singlerow.get(5);
/* 1192 */       String actionstatus = (String)singlerow.get(6);
/* 1193 */       String linkclass = "monitorgp-links";
/* 1194 */       String titleforres = childresname;
/* 1195 */       String titilechildresname = childresname;
/* 1196 */       String childimg = "/images/trcont.png";
/* 1197 */       String flag = "enable";
/* 1198 */       String dcstarted = (String)singlerow.get(8);
/* 1199 */       String configMonitor = "";
/* 1200 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1201 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1203 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1205 */       if (singlerow.get(7) != null)
/*      */       {
/* 1207 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1209 */       String haiGroupType = "0";
/* 1210 */       if ("HAI".equals(childtype))
/*      */       {
/* 1212 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1214 */       childimg = "/images/trend.png";
/* 1215 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1216 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1217 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1219 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1221 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1223 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1224 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1227 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1229 */         linkclass = "disabledtext";
/* 1230 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1232 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1233 */       String availmouseover = "";
/* 1234 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1236 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1238 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1239 */       String healthmouseover = "";
/* 1240 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1242 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1245 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1246 */       int spacing = 0;
/* 1247 */       if (level >= 1)
/*      */       {
/* 1249 */         spacing = 40 * level;
/*      */       }
/* 1251 */       if (childtype.equals("HAI"))
/*      */       {
/* 1253 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1254 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1255 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1257 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1258 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1259 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1260 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1261 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1262 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1263 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1264 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1265 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1266 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1267 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1269 */         if (!forInventory)
/*      */         {
/* 1271 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1274 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1276 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1278 */           actions = editlink + actions;
/*      */         }
/* 1280 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1282 */           actions = actions + associatelink;
/*      */         }
/* 1284 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1285 */         String arrowimg = "";
/* 1286 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1288 */           actions = "";
/* 1289 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1290 */           checkbox = "";
/* 1291 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1293 */         if (isIt360)
/*      */         {
/* 1295 */           actionimg = "";
/* 1296 */           actions = "";
/* 1297 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1298 */           checkbox = "";
/*      */         }
/*      */         
/* 1301 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1303 */           actions = "";
/*      */         }
/* 1305 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1307 */           checkbox = "";
/*      */         }
/*      */         
/* 1310 */         String resourcelink = "";
/*      */         
/* 1312 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1314 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1318 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1321 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1327 */         if (!isIt360)
/*      */         {
/* 1329 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1333 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1336 */         toreturn.append("</tr>");
/* 1337 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1339 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1340 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1344 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1345 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1348 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1352 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1354 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1355 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1356 */             toreturn.append(assocMessage);
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1358 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1360 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1366 */         String resourcelink = null;
/* 1367 */         boolean hideEditLink = false;
/* 1368 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1370 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1371 */           hideEditLink = true;
/* 1372 */           if (isIt360)
/*      */           {
/* 1374 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1378 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1380 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1382 */           hideEditLink = true;
/* 1383 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1384 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1389 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1392 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1393 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1394 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1395 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1396 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1397 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1398 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1399 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1400 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1401 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1402 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1403 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1404 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1406 */         if (hideEditLink)
/*      */         {
/* 1408 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1410 */         if (!forInventory)
/*      */         {
/* 1412 */           removefromgroup = "";
/*      */         }
/* 1414 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1415 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1416 */           actions = actions + configcustomfields;
/*      */         }
/* 1418 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1420 */           actions = editlink + actions;
/*      */         }
/* 1422 */         String managedLink = "";
/* 1423 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1425 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1426 */           actions = "";
/* 1427 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1428 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1431 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1433 */           checkbox = "";
/*      */         }
/*      */         
/* 1436 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1438 */           actions = "";
/*      */         }
/* 1440 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1441 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1442 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1443 */         if (isIt360)
/*      */         {
/* 1445 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1451 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1452 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1453 */         if (!isIt360)
/*      */         {
/* 1455 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1459 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1461 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1464 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1471 */       StringBuilder toreturn = new StringBuilder();
/* 1472 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1473 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1474 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1475 */       String title = "";
/* 1476 */       message = EnterpriseUtil.decodeString(message);
/* 1477 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1478 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1479 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1481 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1483 */       else if ("5".equals(severity))
/*      */       {
/* 1485 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1489 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1491 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1492 */       toreturn.append(v);
/*      */       
/* 1494 */       toreturn.append(link);
/* 1495 */       if (severity == null)
/*      */       {
/* 1497 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1499 */       else if (severity.equals("5"))
/*      */       {
/* 1501 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1503 */       else if (severity.equals("4"))
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1507 */       else if (severity.equals("1"))
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1516 */       toreturn.append("</a>");
/* 1517 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1521 */       ex.printStackTrace();
/*      */     }
/* 1523 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1530 */       StringBuilder toreturn = new StringBuilder();
/* 1531 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1532 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1533 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1534 */       if (message == null)
/*      */       {
/* 1536 */         message = "";
/*      */       }
/*      */       
/* 1539 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1540 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1542 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1543 */       toreturn.append(v);
/*      */       
/* 1545 */       toreturn.append(link);
/*      */       
/* 1547 */       if (severity == null)
/*      */       {
/* 1549 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1551 */       else if (severity.equals("5"))
/*      */       {
/* 1553 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1555 */       else if (severity.equals("1"))
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1562 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1564 */       toreturn.append("</a>");
/* 1565 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1571 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1574 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1575 */     if (invokeActions != null) {
/* 1576 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1577 */       while (iterator.hasNext()) {
/* 1578 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1579 */         if (actionmap.containsKey(actionid)) {
/* 1580 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1585 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1589 */     String actionLink = "";
/* 1590 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1591 */     String query = "";
/* 1592 */     ResultSet rs = null;
/* 1593 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1594 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1595 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1596 */       actionLink = "method=" + methodName;
/*      */     }
/* 1598 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1599 */       actionLink = methodName;
/*      */     }
/* 1601 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1602 */     Iterator itr = methodarglist.iterator();
/* 1603 */     boolean isfirstparam = true;
/* 1604 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1605 */     while (itr.hasNext()) {
/* 1606 */       HashMap argmap = (HashMap)itr.next();
/* 1607 */       String argtype = (String)argmap.get("TYPE");
/* 1608 */       String argname = (String)argmap.get("IDENTITY");
/* 1609 */       String paramname = (String)argmap.get("PARAMETER");
/* 1610 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1611 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1612 */         isfirstparam = false;
/* 1613 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1615 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1619 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1623 */         actionLink = actionLink + "&";
/*      */       }
/* 1625 */       String paramValue = null;
/* 1626 */       String tempargname = argname;
/* 1627 */       if (commonValues.getProperty(tempargname) != null) {
/* 1628 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1631 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1632 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1633 */           if (dbType.equals("mysql")) {
/* 1634 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1637 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1639 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1641 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1642 */             if (rs.next()) {
/* 1643 */               paramValue = rs.getString("VALUE");
/* 1644 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1648 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1652 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1655 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1660 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1661 */           paramValue = rowId;
/*      */         }
/* 1663 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1664 */           paramValue = managedObjectName;
/*      */         }
/* 1666 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1667 */           paramValue = resID;
/*      */         }
/* 1669 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1670 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1673 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1675 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1676 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1677 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1679 */     return actionLink;
/*      */   }
/*      */   
/* 1682 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1683 */     String dependentAttribute = null;
/* 1684 */     String align = "left";
/*      */     
/* 1686 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1687 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1688 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1689 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1690 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1691 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1692 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1693 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1694 */       align = "center";
/*      */     }
/*      */     
/* 1697 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1698 */     String actualdata = "";
/*      */     
/* 1700 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1701 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1702 */         actualdata = availValue;
/*      */       }
/* 1704 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1705 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1709 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1710 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1713 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1719 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1720 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1721 */       toreturn.append("<table>");
/* 1722 */       toreturn.append("<tr>");
/* 1723 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1724 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1725 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1726 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1727 */         String toolTip = "";
/* 1728 */         String hideClass = "";
/* 1729 */         String textStyle = "";
/* 1730 */         boolean isreferenced = true;
/* 1731 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1732 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1733 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1734 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1736 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1737 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1738 */           while (valueList.hasMoreTokens()) {
/* 1739 */             String dependentVal = valueList.nextToken();
/* 1740 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1741 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1742 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1744 */               toolTip = "";
/* 1745 */               hideClass = "";
/* 1746 */               isreferenced = false;
/* 1747 */               textStyle = "disabledtext";
/* 1748 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1752 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1753 */           toolTip = "";
/* 1754 */           hideClass = "";
/* 1755 */           isreferenced = false;
/* 1756 */           textStyle = "disabledtext";
/* 1757 */           if (dependentImageMap != null) {
/* 1758 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1759 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1762 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1766 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1767 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1768 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1769 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1770 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1771 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1773 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1774 */           if (isreferenced) {
/* 1775 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1779 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1780 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1781 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1782 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1783 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1784 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1786 */           toreturn.append("</span>");
/* 1787 */           toreturn.append("</a>");
/* 1788 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1791 */       toreturn.append("</tr>");
/* 1792 */       toreturn.append("</table>");
/* 1793 */       toreturn.append("</td>");
/*      */     } else {
/* 1795 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1798 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1802 */     String colTime = null;
/* 1803 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1804 */     if ((rows != null) && (rows.size() > 0)) {
/* 1805 */       Iterator<String> itr = rows.iterator();
/* 1806 */       String maxColQuery = "";
/* 1807 */       for (;;) { if (itr.hasNext()) {
/* 1808 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1809 */           ResultSet maxCol = null;
/*      */           try {
/* 1811 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1812 */             while (maxCol.next()) {
/* 1813 */               if (colTime == null) {
/* 1814 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1817 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1826 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1828 */               if (maxCol != null)
/* 1829 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1831 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1826 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1828 */               if (maxCol != null)
/* 1829 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1831 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1836 */     return colTime;
/*      */   }
/*      */   
/* 1839 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1840 */     tablename = null;
/* 1841 */     ResultSet rsTable = null;
/* 1842 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1844 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1845 */       while (rsTable.next()) {
/* 1846 */         tablename = rsTable.getString("DATATABLE");
/* 1847 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1848 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1861 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1852 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1855 */         if (rsTable != null)
/* 1856 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1858 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1864 */     String argsList = "";
/* 1865 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1867 */       if (showArgsMap.get(row) != null) {
/* 1868 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1869 */         if (showArgslist != null) {
/* 1870 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1871 */             if (argsList.trim().equals("")) {
/* 1872 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1875 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1882 */       e.printStackTrace();
/* 1883 */       return "";
/*      */     }
/* 1885 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1890 */     String argsList = "";
/* 1891 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1894 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1896 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1897 */         if (hideArgsList != null)
/*      */         {
/* 1899 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1901 */             if (argsList.trim().equals(""))
/*      */             {
/* 1903 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1907 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1915 */       ex.printStackTrace();
/*      */     }
/* 1917 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1921 */     StringBuilder toreturn = new StringBuilder();
/* 1922 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1929 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1930 */       Iterator itr = tActionList.iterator();
/* 1931 */       while (itr.hasNext()) {
/* 1932 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1933 */         String confirmmsg = "";
/* 1934 */         String link = "";
/* 1935 */         String isJSP = "NO";
/* 1936 */         HashMap tactionMap = (HashMap)itr.next();
/* 1937 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1938 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1939 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1940 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1941 */           (actionmap.containsKey(actionId))) {
/* 1942 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1943 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1944 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1945 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1946 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1948 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1954 */           if (isTableAction) {
/* 1955 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1958 */             tableName = "Link";
/* 1959 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1960 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1961 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1962 */             toreturn.append("</a></td>");
/*      */           }
/* 1964 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1965 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1973 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1979 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1981 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1982 */       Properties prop = (Properties)node.getUserObject();
/* 1983 */       String mgID = prop.getProperty("label");
/* 1984 */       String mgName = prop.getProperty("value");
/* 1985 */       String isParent = prop.getProperty("isParent");
/* 1986 */       int mgIDint = Integer.parseInt(mgID);
/* 1987 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1989 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1991 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1992 */       if (node.getChildCount() > 0)
/*      */       {
/* 1994 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1996 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1998 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2000 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2004 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2009 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2011 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2013 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2015 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2019 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2022 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2023 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2025 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2029 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2031 */       if (node.getChildCount() > 0)
/*      */       {
/* 2033 */         builder.append("<UL>");
/* 2034 */         printMGTree(node, builder);
/* 2035 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2040 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2041 */     StringBuffer toReturn = new StringBuffer();
/* 2042 */     String table = "-";
/*      */     try {
/* 2044 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2045 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2046 */       float total = 0.0F;
/* 2047 */       while (it.hasNext()) {
/* 2048 */         String attName = (String)it.next();
/* 2049 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2050 */         boolean roundOffData = false;
/* 2051 */         if ((data != null) && (!data.equals(""))) {
/* 2052 */           if (data.indexOf(",") != -1) {
/* 2053 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2056 */             float value = Float.parseFloat(data);
/* 2057 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2060 */             total += value;
/* 2061 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2064 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2069 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2070 */       while (attVsWidthList.hasNext()) {
/* 2071 */         String attName = (String)attVsWidthList.next();
/* 2072 */         String data = (String)attVsWidthProps.get(attName);
/* 2073 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2074 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2075 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2076 */         String className = (String)graphDetails.get("ClassName");
/* 2077 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2078 */         if (percentage < 1.0F)
/*      */         {
/* 2080 */           data = percentage + "";
/*      */         }
/* 2082 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2084 */       if (toReturn.length() > 0) {
/* 2085 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2089 */       e.printStackTrace();
/*      */     }
/* 2091 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2097 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2098 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2099 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2100 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2101 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2102 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2103 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2104 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2105 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2108 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2109 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2110 */       splitvalues[0] = multiplecondition.toString();
/* 2111 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2114 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2119 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2120 */     if (thresholdType != 3) {
/* 2121 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2122 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2123 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2124 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2125 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2126 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2128 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2129 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2130 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2131 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2132 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2133 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2135 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2136 */     if (updateSelected != null) {
/* 2137 */       updateSelected[0] = "selected";
/*      */     }
/* 2139 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2144 */       StringBuffer toreturn = new StringBuffer("");
/* 2145 */       if (commaSeparatedMsgId != null) {
/* 2146 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2147 */         int count = 0;
/* 2148 */         while (msgids.hasMoreTokens()) {
/* 2149 */           String id = msgids.nextToken();
/* 2150 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2151 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2152 */           count++;
/* 2153 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2154 */             if (toreturn.length() == 0) {
/* 2155 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2157 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2158 */             if (!image.trim().equals("")) {
/* 2159 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2161 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2162 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2165 */         if (toreturn.length() > 0) {
/* 2166 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2170 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2173 */       e.printStackTrace(); }
/* 2174 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isColumnNotEmpty(String colValue)
/*      */   {
/* 2183 */     if ((colValue.equalsIgnoreCase("")) || (colValue.trim().length() == 0)) {
/* 2184 */       return false;
/*      */     }
/*      */     
/* 2187 */     return true;
/*      */   }
/*      */   
/*      */ 
/* 2191 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2197 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2198 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2199 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ffile_0026_005fstyle_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2221 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2225 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fstyle_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2240 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2244 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/* 2248 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.release();
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fstyle_005fsize_005fproperty_005fnobody.release();
/* 2256 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2257 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2264 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2267 */     JspWriter out = null;
/* 2268 */     Object page = this;
/* 2269 */     JspWriter _jspx_out = null;
/* 2270 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2274 */       response.setContentType("text/html;charset=UTF-8");
/* 2275 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2277 */       _jspx_page_context = pageContext;
/* 2278 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2279 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2280 */       session = pageContext.getSession();
/* 2281 */       out = pageContext.getOut();
/* 2282 */       _jspx_out = out;
/*      */       
/* 2284 */       out.write(10);
/*      */       
/* 2286 */       request.setAttribute("HelpKey", "File Uploads");
/*      */       
/* 2288 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2289 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2291 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2292 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2293 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2295 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2297 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2299 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2301 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2302 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2303 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2304 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2307 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2308 */         String available = null;
/* 2309 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2310 */         out.write(10);
/*      */         
/* 2312 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2313 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2314 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2316 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2318 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2320 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2322 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2323 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2324 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2325 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2328 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2329 */           String unavailable = null;
/* 2330 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2331 */           out.write(10);
/*      */           
/* 2333 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2334 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2335 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2337 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2339 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2341 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2343 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2344 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2345 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2346 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2349 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2350 */             String unmanaged = null;
/* 2351 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2352 */             out.write(10);
/*      */             
/* 2354 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2355 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2356 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2358 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2360 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2362 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2364 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2365 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2366 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2367 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2370 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2371 */               String scheduled = null;
/* 2372 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2373 */               out.write(10);
/*      */               
/* 2375 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2376 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2377 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2379 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2381 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2383 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2385 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2386 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2387 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2388 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2391 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2392 */                 String critical = null;
/* 2393 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2394 */                 out.write(10);
/*      */                 
/* 2396 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2397 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2398 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2400 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2402 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2404 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2406 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2407 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2408 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2409 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2412 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2413 */                   String clear = null;
/* 2414 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2415 */                   out.write(10);
/*      */                   
/* 2417 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2418 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2419 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2421 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2423 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2425 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2427 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2428 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2429 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2430 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2433 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2434 */                     String warning = null;
/* 2435 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2436 */                     out.write(10);
/* 2437 */                     out.write(10);
/*      */                     
/* 2439 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2440 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2442 */                     out.write(10);
/* 2443 */                     out.write(10);
/* 2444 */                     out.write(10);
/* 2445 */                     out.write(10);
/* 2446 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2447 */                     out.write("\n\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2448 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2450 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script>\n\n<html>\n<body onLoad=\"myOnLoad()\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n<tr height=\"60\" class=\"itadmin-hide\">\n <td class=\"darkheaderbg\">&nbsp;<span class=\"headingboldwhite\">");
/* 2451 */                     out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 2452 */                     out.write("</span></td>\n</tr>\n\n<tr style=\"padding:10px; \"><td>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<script LANGUAGE=\"JavaScript1.2\">\nvar confMonitorList = new Array();\n\tfunction goBack()\n\t{\n\t\t\n\t\tif (document.referrer.toLowerCase().indexOf( \"adminlayout\")!=-1) \n\t\t{\n\t\t\t\t history.back();\n\t\t} \n\t\telse \n\t\t{ \n\t\t\thistory.back();\n\t\t\twindow.close();\n   \t\t\n\t\t}\n\t\t\n\t}\nfunction fnFormSubmit()\n{\n\t");
/* 2453 */                     if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                     {
/* 2455 */                       out.write("\n\t\tdocument.BulkMonitorForm.target=\"_blank\";\n\t\t");
/*      */                     }
/*      */                     
/* 2458 */                     out.write("\n\t\n   var val = document.BulkMonitorForm.theFile.value;\n   if(document.BulkMonitorForm.type.value=='Node')\n   {\n     alert('");
/* 2459 */                     out.print(FormatUtil.getString("am.webclient.admin.bulkimport.monitortype.alert"));
/* 2460 */                     out.write("');\n     return false;\n   }\n   if(trimAll(val)==\"\")\n   {\n        window.alert('");
/* 2461 */                     out.print(FormatUtil.getString("am.webclient.fileupload.alert1.text"));
/* 2462 */                     out.write("');\n   \treturn false;\n   }\n   ");
/* 2463 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2465 */                     out.write("\n   document.BulkMonitorForm.submit();\n}\n\nfunction fnFormSubmit1()\n{\n\t");
/* 2466 */                     if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                       return;
/* 2468 */                     out.write("\ndocument.bulkdiscoveryform.submit();\n}\n\nfunction showhelp()\n{\nhideDiv('Node');\nhideDiv('SYSTEM');\nhideDiv('JBoss');\nhideDiv('JDK1.5');\nhideDiv('.Net');\nhideDiv('ORACLEAS');\nhideDiv('Tomcat');\nhideDiv('WLI');\nhideDiv('WebLogic');\nhideDiv('WebSphere');\nhideDiv('DB2');\nhideDiv('MSSQLDB');\nhideDiv('MYSQLDB');\nhideDiv('ORACLEDB');\nhideDiv('SYBASEDB');\nhideDiv('UrlMonitor');\nhideDiv('SERVICE');\nhideDiv('Web Service');\n\n\thideDiv('CONF');\n\tvar isConfMonitor=false;\n\tvar selected=BulkMonitorForm.type.value;\n\tfor(i=0;i<confMonitorList.length;i++){\n\n\t\tif(selected==confMonitorList[i]){\n\t\t\tisConfMonitor=true;\n\t\t}\n\t}\n\n\tif(isConfMonitor==true){\n\t\thttp=getHTTPObject();\n\t\tURL=\"/BulkAddMonitors.do?method=getConfMonitorHeader&resType=\"+selected;// No I18N\n\t\thttp.open(\"GET\",URL,true);\n\t\thttp.onreadystatechange=function () { AjaxCall(selected);};\n\t\thttp.send(null);\n\n\n\t}\n\telse{\nshowDiv(BulkMonitorForm.type.value);\n}\n\n}\nfunction AjaxCall(selected){\n\tif(http.readyState==4){\n\t\tisrequestSuccess=true;\n\t\tvar result = http.responseText;\n\t\tdocument.getElementById('CONF').innerHTML=result;\n");
/* 2469 */                     out.write("\t\tshowDiv('CONF');\n\t}\n}\n</script>\n\n");
/*      */                     try
/*      */                     {
/* 2472 */                       ConfMonitorConfiguration conf = ConfMonitorConfiguration.getInstance();
/* 2473 */                       Hashtable confmonitors = com.adventnet.appmanager.util.Constants.getCustomMonitors();
/* 2474 */                       List confMonitorsList = new ArrayList();
/* 2475 */                       confMonitorsList.addAll(confmonitors.keySet());
/* 2476 */                       ArrayList passWordColumns = (ArrayList)request.getAttribute("passWordColumns");
/* 2477 */                       ArrayList passWordColIndex = new ArrayList();
/* 2478 */                       if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                       {
/*      */ 
/*      */ 
/* 2482 */                         out.write("\n\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" class=\"itadmin-hide\">\n\t<tr>\n          <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2483 */                         out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2484 */                         out.write(" &gt;<span class=\"bcactive\">");
/* 2485 */                         out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 2486 */                         out.write("</span></td>\n\t</tr>\n</table>\n\n");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/*      */ 
/* 2492 */                         out.write("\n<br>\n<br>\n");
/*      */                       }
/*      */                       
/* 2495 */                       if ((EnterpriseUtil.isIt360MSPEdition()) && (!CustomerManagementAPI.getInstance().checkCustomerAvailability()))
/*      */                       {
/*      */ 
/* 2498 */                         out.write("\n<table align=\"center\" valign=\"center\" width=\"45%\" height=\"10%\" border=\"0\" class=\"messageboxfailure\">\n\t\t\t<tr>\n    \t        <td align=\"left\" valign=\"center\"><img src=\"/images/prereq_notconfigured.gif\" hspace=\"0\"></td>\n\t\t\t\t<td align=\"left\" valign=\"center\" class=\"bodytext\" ><b>");
/* 2499 */                         out.print(FormatUtil.getString("am.webclient.custmgmt.associatecustomer"));
/* 2500 */                         out.write(" </b> </td>\n\t\t\t</tr>\n</table>\n");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/*      */ 
/* 2506 */                         out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n<tr>\n    <td valign=\"top\">\n <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n\n        ");
/*      */                         
/* 2508 */                         MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 2509 */                         _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 2510 */                         _jspx_th_html_005fmessages_005f0.setParent(null);
/*      */                         
/* 2512 */                         _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                         
/* 2514 */                         _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 2515 */                         int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 2516 */                         if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 2517 */                           String msg = null;
/* 2518 */                           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 2519 */                             out = _jspx_page_context.pushBody();
/* 2520 */                             _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 2521 */                             _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                           }
/* 2523 */                           msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                           for (;;) {
/* 2525 */                             out.write("\n        <tr>\n          <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n              <tr>\n                <td width=\"5%\" align=\"center\"><img src=\"/images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" height=\"28\" class=\"message\">");
/* 2526 */                             if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                               return;
/* 2528 */                             out.write("</td>\n              </tr>\n            </table>\n            <br></td>\n        </tr>\n        ");
/* 2529 */                             int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 2530 */                             msg = (String)_jspx_page_context.findAttribute("msg");
/* 2531 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2534 */                           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 2535 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2538 */                         if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 2539 */                           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                         }
/*      */                         
/* 2542 */                         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 2543 */                         out.write(32);
/*      */                         
/* 2545 */                         MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 2546 */                         _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 2547 */                         _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*      */                         
/* 2549 */                         _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 2550 */                         int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 2551 */                         if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                           for (;;) {
/* 2553 */                             out.write("\n\n        <tr>\n          <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"5\" cellpadding=\"5\" class=\"messagebox\">\n              <tr>\n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" class=\"message\"> ");
/*      */                             
/* 2555 */                             MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 2556 */                             _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 2557 */                             _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                             
/* 2559 */                             _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                             
/* 2561 */                             _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 2562 */                             int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 2563 */                             if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 2564 */                               String msg = null;
/* 2565 */                               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 2566 */                                 out = _jspx_page_context.pushBody();
/* 2567 */                                 _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 2568 */                                 _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                               }
/* 2570 */                               msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                               for (;;) {
/* 2572 */                                 out.write("\n                  ");
/* 2573 */                                 if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                                   return;
/* 2575 */                                 out.write("<br>\n                  ");
/* 2576 */                                 int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 2577 */                                 msg = (String)_jspx_page_context.findAttribute("msg");
/* 2578 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2581 */                               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 2582 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2585 */                             if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 2586 */                               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                             }
/*      */                             
/* 2589 */                             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 2590 */                             out.write(" </td>\n              </tr>\n            </table>\n            <br></td>\n        </tr>\n        ");
/* 2591 */                             int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 2592 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2596 */                         if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 2597 */                           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                         }
/*      */                         
/* 2600 */                         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 2601 */                         out.write("\n        </table>\n</td></tr></table>\n");
/*      */                         
/* 2603 */                         if ((request.getParameter("showUploadForm") != null) && (request.getParameter("showUploadForm").equals("true")))
/*      */                         {
/*      */ 
/* 2606 */                           out.write("\n<script>\n\nfunction myOnLoad()\n{\n\n\t");
/* 2607 */                           for (int i = 0; i < confMonitorsList.size(); i++) {
/* 2608 */                             out.write("\n\t\t\t\tconfMonitorList[");
/* 2609 */                             out.print(i);
/* 2610 */                             out.write(93);
/* 2611 */                             out.write(61);
/* 2612 */                             out.write(34);
/* 2613 */                             out.print((String)confMonitorsList.get(i));
/* 2614 */                             out.write("\";\n\t");
/*      */                           }
/* 2616 */                           out.write(10);
/* 2617 */                           if (request.getParameter("type") != null)
/*      */                           {
/* 2619 */                             String mtype = request.getParameter("type");
/* 2620 */                             if (mtype.equals("WEBLOGIC"))
/*      */                             {
/* 2622 */                               mtype = "WebLogic";
/*      */                             }
/* 2624 */                             else if (mtype.equals("WEBSPHERE"))
/*      */                             {
/* 2626 */                               mtype = "WebSphere";
/*      */                             }
/* 2628 */                             else if ((mtype.equals("AIX")) || (mtype.equals("AS400/iSeries")) || (mtype.equals("FreeBSD / OpenBSD")) || (mtype.equals("HP-UX / Tru64")) || (mtype.equals("Linux")) || (mtype.equals("Mac OS")) || (mtype.equals("Sun Solaris")) || (mtype.equals("Windows")) || (mtype.equals("Novell")) || (mtype.equals("FreeBSD")))
/*      */                             {
/* 2630 */                               mtype = "SYSTEM";
/*      */                             }
/*      */                             
/*      */ 
/* 2634 */                             out.write("\nvar toselect='");
/* 2635 */                             out.print(mtype);
/* 2636 */                             out.write(39);
/* 2637 */                             out.write(59);
/* 2638 */                             out.write(10);
/*      */                             
/* 2640 */                             if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                             {
/*      */ 
/* 2643 */                               out.write("\ndocument.BulkMonitorForm.type.value=toselect;\n$('[name=\"type\"]').val(toselect);\n\nshowhelp('");
/* 2644 */                               out.print(mtype);
/* 2645 */                               out.write("');\n\n\n\t\t");
/*      */                             }
/*      */                           }
/*      */                           
/* 2649 */                           out.write("\n\n}\n</script>\n\n");
/*      */                           
/* 2651 */                           FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.get(FormTag.class);
/* 2652 */                           _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2653 */                           _jspx_th_html_005fform_005f0.setParent(null);
/*      */                           
/* 2655 */                           _jspx_th_html_005fform_005f0.setAction("/BulkAddMonitors.do?method=uploadFile");
/*      */                           
/* 2657 */                           _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/*      */                           
/* 2659 */                           _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2660 */                           int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2661 */                           if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                             for (;;) {
/* 2663 */                               out.write(32);
/*      */                               
/* 2665 */                               IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2666 */                               _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2667 */                               _jspx_th_c_005fif_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                               
/* 2669 */                               _jspx_th_c_005fif_005f0.setTest("${!empty param.returnpath}");
/* 2670 */                               int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2671 */                               if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                 for (;;) {
/* 2673 */                                   out.write("\n<input name=\"method\" type=\"hidden\" value=\"uploadFile\">\n<input name=\"returnpath\" type=\"hidden\" value=\"");
/* 2674 */                                   out.print(request.getParameter("returnpath"));
/* 2675 */                                   out.write(34);
/* 2676 */                                   out.write(62);
/* 2677 */                                   out.write(10);
/* 2678 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2679 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2683 */                               if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2684 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                               }
/*      */                               
/* 2687 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2688 */                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n  <tr>\n    <td class=\"tableheading\" height=\"26\"><span>");
/* 2689 */                               out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 2690 */                               out.write("</span></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\" align=\"center\">\n\n\n    ");
/*      */                               
/* 2692 */                               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                               {
/*      */ 
/* 2695 */                                 out.write("\n\n  <tr>\n      <td width=\"15%\" height=\"35\" class=\"bodytext label-align\" align=\"left\">");
/* 2696 */                                 out.print(FormatUtil.getString("am.webclient.maintenance.MonitorType"));
/* 2697 */                                 out.write("    :</td>\n      <td width=\"85%\" height=\"35\" class=\"bodytext\">\n       <select name=\"type\" onChange=\"showhelp(this.form)\" class=\"formtext normal chosenselect\">\n       <option value=\"Node\" selected=\"selected\">--");
/* 2698 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 2699 */                                 out.write("--</option>\n       <optgroup label=\"");
/* 2700 */                                 out.print(FormatUtil.getString("Servers"));
/* 2701 */                                 out.write("\"></optgroup>");
/* 2702 */                                 out.print(FormatUtil.getString("Servers"));
/* 2703 */                                 out.write("\n       <option value=\"SYSTEM\" >");
/* 2704 */                                 out.print(FormatUtil.getString("Servers"));
/* 2705 */                                 out.write("</option>\n\n\n       \t\t\t");
/* 2706 */                                 if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2708 */                                 out.write("\n\n       <optgroup label=\"");
/* 2709 */                                 out.print(FormatUtil.getString("Application Servers"));
/* 2710 */                                 out.write("\"></optgroup>\n       \n        ");
/*      */                                 
/* 2712 */                                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2713 */                                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2714 */                                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2716 */                                 _jspx_th_c_005fif_005f2.setTest("${productEdition!='CLOUD'}");
/* 2717 */                                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2718 */                                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                   for (;;) {
/* 2720 */                                     out.write("\n       ");
/* 2721 */                                     if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 2722 */                                       out.write("\n       <option value=\".Net\">");
/* 2723 */                                       out.print(FormatUtil.getString("Microsoft .NET"));
/* 2724 */                                       out.write("</option>\n       ");
/*      */                                     }
/* 2726 */                                     out.write("\n       ");
/* 2727 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2728 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2732 */                                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2733 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                 }
/*      */                                 
/* 2736 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2737 */                                 out.write("\n       <option value=\"JDK1.5\">");
/* 2738 */                                 out.print(FormatUtil.getString("Java Runtime"));
/* 2739 */                                 out.write("</option>\n       ");
/*      */                                 
/* 2741 */                                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2742 */                                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2743 */                                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2745 */                                 _jspx_th_c_005fif_005f3.setTest("${productEdition!='CLOUD'}");
/* 2746 */                                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2747 */                                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                   for (;;) {
/* 2749 */                                     out.write("\n       <option value=\"ORACLEAS\">");
/* 2750 */                                     out.print(FormatUtil.getString("Oracle AS"));
/* 2751 */                                     out.write("</option>\n       ");
/* 2752 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2753 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2757 */                                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2758 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                 }
/*      */                                 
/* 2761 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2762 */                                 out.write("\n       \n       ");
/*      */                                 
/* 2764 */                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2765 */                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2766 */                                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2768 */                                 _jspx_th_c_005fif_005f4.setTest("${productEdition!='CLOUD'}");
/* 2769 */                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2770 */                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                   for (;;) {
/* 2772 */                                     out.write("\n       <option value=\"WebLogic\">");
/* 2773 */                                     out.print(FormatUtil.getString("WebLogic Server"));
/* 2774 */                                     out.write("</option>\n         ");
/* 2775 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2776 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2780 */                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2781 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                 }
/*      */                                 
/* 2784 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2785 */                                 out.write("\n\n\n       \t\t\t");
/* 2786 */                                 if (_jspx_meth_c_005fif_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2788 */                                 out.write("\n                                     ");
/*      */                                 
/* 2790 */                                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2791 */                                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2792 */                                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2794 */                                 _jspx_th_c_005fif_005f6.setTest("${productEdition!='CLOUD'}");
/* 2795 */                                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2796 */                                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                   for (;;) {
/* 2798 */                                     out.write("\n       <optgroup label=\"");
/* 2799 */                                     out.print(FormatUtil.getString("ERP"));
/* 2800 */                                     out.write("\"></optgroup>\n       \t");
/* 2801 */                                     if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 2803 */                                     out.write(10);
/* 2804 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2805 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2809 */                                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2810 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                 }
/*      */                                 
/* 2813 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2814 */                                 out.write("\n\n       <optgroup label=\"");
/* 2815 */                                 out.print(FormatUtil.getString("Database Servers"));
/* 2816 */                                 out.write("\"></optgroup>\n            ");
/*      */                                 
/* 2818 */                                 IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2819 */                                 _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2820 */                                 _jspx_th_c_005fif_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2822 */                                 _jspx_th_c_005fif_005f8.setTest("${productEdition!='CLOUD'}");
/* 2823 */                                 int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2824 */                                 if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                   for (;;) {
/* 2826 */                                     out.write("\n       <option value=\"DB2\">");
/* 2827 */                                     out.print(FormatUtil.getString("DB2"));
/* 2828 */                                     out.write("</option>\n       \n       <option value=\"MSSQLDB\">");
/* 2829 */                                     out.print(FormatUtil.getString("MS SQL"));
/* 2830 */                                     out.write("</option>\n       ");
/* 2831 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2832 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2836 */                                 if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2837 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                 }
/*      */                                 
/* 2840 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2841 */                                 out.write("\n       <option value=\"MYSQLDB\">");
/* 2842 */                                 out.print(FormatUtil.getString("MySQL"));
/* 2843 */                                 out.write("</option>\n            ");
/*      */                                 
/* 2845 */                                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2846 */                                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2847 */                                 _jspx_th_c_005fif_005f9.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2849 */                                 _jspx_th_c_005fif_005f9.setTest("${productEdition!='CLOUD'}");
/* 2850 */                                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2851 */                                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                   for (;;) {
/* 2853 */                                     out.write("\n       \n       <option value=\"SYBASEDB\">");
/* 2854 */                                     out.print(FormatUtil.getString("Sybase"));
/* 2855 */                                     out.write("</option>\n           ");
/* 2856 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2857 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2861 */                                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2862 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                 }
/*      */                                 
/* 2865 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2866 */                                 out.write("\n\n\n\t\t");
/* 2867 */                                 if (_jspx_meth_c_005fif_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2869 */                                 out.write("\n\n       <optgroup label=\"");
/* 2870 */                                 out.print(FormatUtil.getString("Web Server/Services"));
/* 2871 */                                 out.write("\"></optgroup>\n\t   <option value=\"UrlMonitor\" >");
/* 2872 */                                 out.print(FormatUtil.getString("HTTP(s) URLs"));
/* 2873 */                                 out.write("</option>\n\t   ");
/* 2874 */                                 if (_jspx_meth_c_005fif_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2876 */                                 out.write("\n\t\t<option value=\"Web Service\" >");
/* 2877 */                                 out.print(FormatUtil.getString("Web Services"));
/* 2878 */                                 out.write("</option>\n\t   <optgroup label=\"");
/* 2879 */                                 out.print(FormatUtil.getString("Services"));
/* 2880 */                                 out.write("\"></optgroup>\n\t   \n       <!--option value=\"Ping Monitor\"><!%=FormatUtil.getString(\"Ping Monitor\")%></option-->\n       \n\n\n\n\t\t");
/* 2881 */                                 if (_jspx_meth_c_005fif_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2883 */                                 out.write("\n\n\t<optgroup label=\"");
/* 2884 */                                 out.print(FormatUtil.getString("Mail Servers"));
/* 2885 */                                 out.write("\"></optgroup>\n       \t");
/* 2886 */                                 if (_jspx_meth_c_005fif_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2888 */                                 out.write("\n            ");
/*      */                                 
/* 2890 */                                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2891 */                                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 2892 */                                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2894 */                                 _jspx_th_c_005fif_005f14.setTest("${productEdition!='CLOUD'}");
/* 2895 */                                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 2896 */                                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                   for (;;) {
/* 2898 */                                     out.write("\n\t<optgroup label=\"");
/* 2899 */                                     out.print(FormatUtil.getString("Middleware/Portal"));
/* 2900 */                                     out.write("\"></optgroup>\n       \t");
/*      */                                     
/* 2902 */                                     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2903 */                                     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 2904 */                                     _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f14);
/*      */                                     
/* 2906 */                                     _jspx_th_c_005fif_005f15.setTest("${momserver != null}");
/* 2907 */                                     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 2908 */                                     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                       for (;;) {
/* 2910 */                                         out.write("\n       \t   <option value=\"WLI\">");
/* 2911 */                                         out.print(FormatUtil.getString("WebLogic Integration"));
/* 2912 */                                         out.write("</option>\n\t\t\t");
/* 2913 */                                         if (_jspx_meth_c_005fforEach_005f13(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                           return;
/* 2915 */                                         out.write(10);
/* 2916 */                                         out.write(9);
/* 2917 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 2918 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2922 */                                     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 2923 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                     }
/*      */                                     
/* 2926 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2927 */                                     out.write("\n               ");
/* 2928 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 2929 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2933 */                                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 2934 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                 }
/*      */                                 
/* 2937 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 2938 */                                 out.write("\n\t<optgroup label=\"");
/* 2939 */                                 out.print(FormatUtil.getString("Virtualization"));
/* 2940 */                                 out.write("\"></optgroup>\n       \t");
/* 2941 */                                 if (_jspx_meth_c_005fif_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2943 */                                 out.write("\n\t<optgroup label=\"");
/* 2944 */                                 out.print(FormatUtil.getString("Cloud Apps"));
/* 2945 */                                 out.write("\"></optgroup>\n       \t");
/* 2946 */                                 if (_jspx_meth_c_005fif_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2948 */                                 out.write("\n       </select>\n      </td>\n  </tr>\n  ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 2954 */                                 out.write("\n   <input type=\"hidden\" name=\"type\" value='");
/* 2955 */                                 out.print(FormatUtil.getString("MS SQL"));
/* 2956 */                                 out.write("'>\n  ");
/*      */                               }
/*      */                               
/*      */ 
/* 2960 */                               out.write("\n\n  <tr>\n    <td width=\"15%\" height=\"35\" class=\"bodytext label-align\" align=\"left\">");
/* 2961 */                               out.print(FormatUtil.getString("am.webclient.fileupload.filetoupload.text"));
/* 2962 */                               out.write("    :</td>\n    <td width=\"85%\" height=\"35\" class=\"bodytext\"> ");
/* 2963 */                               if (_jspx_meth_html_005ffile_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 2965 */                               out.write("\n    </td>\n  </tr>\n</table>\n");
/*      */                               
/* 2967 */                               String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/* 2968 */                               String upload = "&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupfirst.next") + "&nbsp;&nbsp;&nbsp;";
/*      */                               
/* 2970 */                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" align=\"center\">\n  <tr>\n    <td height=\"40\" width=\"15%\"  align=\"center\"  class=\"tablebottom\">&nbsp; </td>\n    <td height=\"40\" width=\"85%\" align=\"left\"  class=\"tablebottom\">\n      <input name=\"button1\" type=\"button\" class=\"buttons btn_next\" value=\"");
/* 2971 */                               out.print(upload);
/* 2972 */                               out.write("\" onClick=\"javascript:fnFormSubmit()\">\n      <input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2973 */                               out.print(cancel);
/* 2974 */                               out.write("\" onClick=\"goBack()\">    \n   </td>\n    <td height=\"40\" width=\"60%\"  align=\"center\"  class=\"tablebottom\">&nbsp; </td>\n  </tr>\n</table>\n<br>\n\n");
/* 2975 */                               int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2976 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2980 */                           if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2981 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                           }
/*      */                           
/* 2984 */                           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2985 */                           out.write("\n\n\n\n\n<br>\n\n\n\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardHdrTopLeft\"/>\n\t\t\t<td class=\"helpCardHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t<tr>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 2986 */                           out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 2987 */                           out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t</table></td>\n\t\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\n\t\t\t<td valign=\"top\">\n\t\t\t<!--//include your Helpcard template table here..-->\n\n\n\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n    <td style=\"padding-top: 10px;\" class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n        <tr>\n          <td class=\"hCardInnerTopLeft\"/>\n          <td class=\"hCardInnerTopBg\"/>\n          <td class=\"hCardInnerTopRight\"/>\n        </tr>\n        <tr>\n\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                <td class=\"hCardInnerBoxBg product-help\" >\n");
/* 2988 */                           out.write("\n\n\n\n\n\n\t\t   <div id=\"Node\" style=\"display:block\" >");
/* 2989 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.node.help.text"));
/* 2990 */                           out.write("</div>\n\t\t   ");
/*      */                           
/* 2992 */                           String supportedmode = FormatUtil.getString("am.servermonitor.bulkimport.modeofmoniotring.text");
/* 2993 */                           if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows")))
/*      */                           {
/* 2995 */                             supportedmode = FormatUtil.getString("am.servermonitor.bulkimport.modeofmoniotring.text");
/*      */                           }
/*      */                           else
/*      */                           {
/* 2999 */                             supportedmode = "<b>SNMP</b> or <b>SSH</b> or <b>TELNET</b>";
/*      */                           }
/*      */                           
/* 3002 */                           out.write("\n\t\t   <div id=\"SYSTEM\" style=\"display:none\">");
/* 3003 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.system.help.text", new String[] { supportedmode }));
/* 3004 */                           out.write("</div>\n\n\n\t\t   <div id=\"JBoss\" style=\"display:none\">");
/* 3005 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.jboss.help.text"));
/* 3006 */                           out.write("</div>\n\t\t   \n\t\t   <div id=\"JDK1.5\" style=\"display:none\">");
/* 3007 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.jdk15.help.text"));
/* 3008 */                           out.write("</div>\n\n\t\t   <div id=\".Net\" style=\"display:none\">");
/* 3009 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.dotnet.help.text"));
/* 3010 */                           out.write("</div>\n\n\t\t   <div id=\"ORACLEAS\" style=\"display:none\">");
/* 3011 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.oracleas.help.text"));
/* 3012 */                           out.write("</div>\n\n\t\t   <div id=\"Tomcat\" style=\"display:none\">");
/* 3013 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.tomcat.help.text"));
/* 3014 */                           out.write("</div>\n\n\t\t   <div id=\"WLI\" style=\"display:none\">");
/* 3015 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.wli.help.text"));
/* 3016 */                           out.write("</div>\n\n\t\t   <div id=\"WebLogic\" style=\"display:none\">");
/* 3017 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.weblogic.help.text"));
/* 3018 */                           out.write("</div>\n\n\t\t   <div id=\"WebSphere\" style=\"display:none\">");
/* 3019 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.websphere.help.text"));
/* 3020 */                           out.write("</div>\n\n\t\t   <div id=\"DB2\" style=\"display:none\">");
/* 3021 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.db2.help.text"));
/* 3022 */                           out.write("</div>\n\n\t\t   <div id=\"MSSQLDB\" style=\"display:none\">");
/* 3023 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.mssql.help.text"));
/* 3024 */                           out.write("</div>\n\n\t\t   <div id=\"MYSQLDB\" style=\"display:none\">");
/* 3025 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.mysql.help.text"));
/* 3026 */                           out.write("</div>\n\n\t\t   <div id=\"ORACLEDB\" style=\"display:none\">");
/* 3027 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.orqcledb.help.text"));
/* 3028 */                           out.write("</div>\n\n\t\t   <div id=\"SYBASEDB\" style=\"display:none\">");
/* 3029 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.sybase.help.text"));
/* 3030 */                           out.write("</div>\n\n<div id=\"UrlMonitor\" style=\"display:none\">");
/* 3031 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.url.help.text"));
/* 3032 */                           out.write("</div>\n<div id=\"Ping Monitor\" style=\"display:none\">");
/* 3033 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.ping.help.text"));
/* 3034 */                           out.write("</div>\n<div id=\"SERVICE\" style=\"display:none\">");
/* 3035 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.servicemonitor.help.text"));
/* 3036 */                           out.write("</div>\n<div id=\"Web Service\" style=\"display:none;\">");
/* 3037 */                           out.print(FormatUtil.getString("am.webclient.admin.bulkimport.webservices.help.text"));
/* 3038 */                           out.write("</div>\n\n\t\t   <div id=\"CONF\" style=\"display:none\"></div>\n\n\n\n\n\n\n\n\n\n            </td>\n\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t</table>\n\n");
/*      */                         } else {
/* 3040 */                           out.write("\n<script>\nfunction myOnLoad()\n{\n}\n</script>\n");
/*      */                           
/* 3042 */                           int pass_col = -1;
/* 3043 */                           String password_col = (String)request.getAttribute("password_col");
/*      */                           try
/*      */                           {
/* 3046 */                             if (password_col != null) {
/* 3047 */                               pass_col = Integer.parseInt(password_col);
/*      */                             }
/*      */                           }
/*      */                           catch (Exception ex) {
/* 3051 */                             pass_col = -1;
/* 3052 */                             ex.printStackTrace();
/*      */                           }
/*      */                           
/*      */ 
/* 3056 */                           out.write("\n\n\n<form name=\"bulkdiscoveryform\" method=\"get\" action=\"/BulkAddMonitors.do\">\n\n");
/*      */                           
/* 3058 */                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3059 */                           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3060 */                           _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                           
/* 3062 */                           _jspx_th_logic_005fnotEmpty_005f0.setName("data_table");
/* 3063 */                           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3064 */                           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                             for (;;) {
/* 3066 */                               out.write("\n<input type=\"hidden\" name=\"method\" value=\"startBulkDiscovery\"/>\n<input type=\"hidden\" name=\"filename\" value=\"");
/* 3067 */                               out.print(URLEncoder.encode((String)request.getAttribute("filename")));
/* 3068 */                               out.write("\"/>\n<input type=\"hidden\" name=\"monitortype\" value=\"");
/* 3069 */                               out.print(request.getAttribute("monitortype"));
/* 3070 */                               out.write("\"/>\n<br>\n<table width=\"99%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td colspan=\"50\"  class=\"border_btm\" height=\"26\">\n\n\n<table width=\"100%\" align=\"center\"  class=\"tableheading\"><tr>\n\t\t<td>");
/* 3071 */                               out.print(FormatUtil.getString("am.webclient.admin.bulkimport.listofmonitors.text"));
/* 3072 */                               out.write(" </td>\n\t\t</tr></table>\n</td>\n\n\n  </tr>\n  <tr>\n\t  \n");
/*      */                               
/* 3074 */                               int tempIndex = 0;
/* 3075 */                               int monitorGroupIndex = -1;
/* 3076 */                               int displayNameIdx = -1;
/* 3077 */                               int hostNameIdx = -1;
/* 3078 */                               int subnetmaskIdx = -1;
/* 3079 */                               int OStypeIdx = -1;
/* 3080 */                               int modeofMonIdx = -1;
/* 3081 */                               int SMTPPortIdx = -1;
/* 3082 */                               int PortIdx = -1;
/* 3083 */                               int HostNameIPIdx = -1;
/* 3084 */                               int JBOSSVersionIdx = -1;
/* 3085 */                               int OracleASVersionIdx = -1;
/* 3086 */                               int TomcatVersionIdx = -1;
/* 3087 */                               int WeblogicVersionIdx = -1;
/* 3088 */                               int JNDIPathIdx = -1;
/* 3089 */                               int JMXUrlIdx = -1;
/* 3090 */                               int SSLEnabledIdx = -1;
/* 3091 */                               int DatabaseNameIdx = -1;
/* 3092 */                               int TargetAddressIdx = -1;
/* 3093 */                               int LDAPServerIdx = -1;
/* 3094 */                               int IsSecuredIdx = -1;
/* 3095 */                               int SearchBaseIdx = -1;
/* 3096 */                               int DownloadFileIdx = -1;
/* 3097 */                               int ShowavailabilitydownwhendownloadfailsIdx = -1;
/* 3098 */                               int UploadFileIdx = -1;
/* 3099 */                               int MailSubjectIdx = -1;
/* 3100 */                               int ServerConnectionChannelIdx = -1;
/* 3101 */                               int QueueManagerIdx = -1;
/* 3102 */                               int AccessKeyIDIdx = -1;
/* 3103 */                               int SecretAccessKeyIdx = -1;
/* 3104 */                               int URLAddressIdx = -1;
/* 3105 */                               int DomainIdx = -1;
/* 3106 */                               int LookupAddressIdx = -1;
/* 3107 */                               int TimeoutIdx = -1;
/*      */                               
/* 3109 */                               boolean isDisplaynamePresent = true;
/* 3110 */                               boolean isHostnamePresent = true;
/* 3111 */                               boolean isSubnetmaskPresent = true;
/* 3112 */                               boolean isOSTypePresent = true;
/* 3113 */                               boolean isModeofMonPresent = true;
/* 3114 */                               boolean isMonitorGroupExist = true;
/* 3115 */                               boolean isSMTPPortIdxPresent = true;
/* 3116 */                               boolean isPortIdxPresent = true;
/* 3117 */                               boolean isHostNameIPIdxPresent = true;
/* 3118 */                               boolean isJBOSSVersionIdxPresent = true;
/* 3119 */                               boolean isOracleASVersionIdxPresent = true;
/* 3120 */                               boolean isTomcatVersionIdxPresent = true;
/* 3121 */                               boolean isWeblogicVersionIdxPresent = true;
/* 3122 */                               boolean isJNDIPathIdxPresent = true;
/* 3123 */                               boolean isSSLEnabledIdxPresent = true;
/* 3124 */                               boolean isDatabaseNameIdxPresent = true;
/* 3125 */                               boolean isTargetAddressIdxPresent = true;
/* 3126 */                               boolean isLDAPServerIdxPresent = true;
/* 3127 */                               boolean isIsSecuredIdxPresent = true;
/* 3128 */                               boolean isSearchBaseIdxPresent = true;
/* 3129 */                               boolean isDownloadFileIdxPresent = true;
/* 3130 */                               boolean isShowavailabilitydownwhendownloadfailsIdxPresent = true;
/* 3131 */                               boolean isUploadFileIdxPresent = true;
/* 3132 */                               boolean isMailSubjectIdxPresent = true;
/* 3133 */                               boolean isServerConnectionChannelIdxPresent = true;
/* 3134 */                               boolean isQueueManagerIdxPresent = true;
/* 3135 */                               boolean isAccessKeyIDIdxPresent = true;
/* 3136 */                               boolean isSecretAccessKeyIdxPresent = true;
/* 3137 */                               boolean isURLAddressIdxPresent = true;
/* 3138 */                               boolean isDomainIdxPresent = true;
/* 3139 */                               boolean isLookupAddressIdxPresent = true;
/* 3140 */                               boolean isTimeoutIdxPresent = true;
/*      */                               
/* 3142 */                               out.write(10);
/* 3143 */                               out.write(10);
/* 3144 */                               out.write(10);
/*      */                               
/* 3146 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3147 */                               _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3148 */                               _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                               
/* 3150 */                               _jspx_th_logic_005fnotEmpty_005f1.setName("headerlist");
/* 3151 */                               int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3152 */                               if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                 for (;;) {
/* 3154 */                                   out.write(10);
/* 3155 */                                   out.write(10);
/*      */                                   
/* 3157 */                                   IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3158 */                                   _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3159 */                                   _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                   
/* 3161 */                                   _jspx_th_logic_005fiterate_005f0.setName("headerlist");
/*      */                                   
/* 3163 */                                   _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                   
/* 3165 */                                   _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                   
/* 3167 */                                   _jspx_th_logic_005fiterate_005f0.setType("String");
/* 3168 */                                   int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3169 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3170 */                                     String row = null;
/* 3171 */                                     Integer j = null;
/* 3172 */                                     if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3173 */                                       out = _jspx_page_context.pushBody();
/* 3174 */                                       _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3175 */                                       _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                     }
/* 3177 */                                     row = (String)_jspx_page_context.findAttribute("row");
/* 3178 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                     for (;;) {
/* 3180 */                                       out.write("\n<td class=\"bulk-import-monitors-bg\" nowrap=\"true\">\n");
/* 3181 */                                       if ((passWordColumns != null) && (passWordColumns.contains(row))) {
/* 3182 */                                         passWordColIndex.add(new Integer(j.intValue()));
/*      */                                       }
/*      */                                       
/* 3185 */                                       out.write(10);
/* 3186 */                                       out.print(row);
/* 3187 */                                       out.write(10);
/* 3188 */                                       out.write(10);
/*      */                                       
/*      */ 
/* 3191 */                                       if (row.equalsIgnoreCase("DISPLAYNAME"))
/*      */                                       {
/* 3193 */                                         displayNameIdx = tempIndex;
/*      */                                       }
/* 3195 */                                       if (row.equalsIgnoreCase("HOSTNAME"))
/*      */                                       {
/* 3197 */                                         hostNameIdx = tempIndex;
/*      */                                       }
/* 3199 */                                       if (row.equalsIgnoreCase("SUBNETMASK"))
/*      */                                       {
/* 3201 */                                         subnetmaskIdx = tempIndex;
/*      */                                       }
/* 3203 */                                       if (row.equalsIgnoreCase("OPERATINGSYSTEM"))
/*      */                                       {
/* 3205 */                                         OStypeIdx = tempIndex;
/*      */                                       }
/* 3207 */                                       if (row.equalsIgnoreCase("MODEOFMONITORING"))
/*      */                                       {
/* 3209 */                                         modeofMonIdx = tempIndex;
/*      */                                       }
/* 3211 */                                       if (row.equalsIgnoreCase("MONITORGROUP"))
/*      */                                       {
/* 3213 */                                         monitorGroupIndex = tempIndex;
/*      */                                       }
/* 3215 */                                       if (row.equalsIgnoreCase("SMTP PORT")) {
/* 3216 */                                         SMTPPortIdx = tempIndex;
/*      */                                       }
/* 3218 */                                       if ((row.equalsIgnoreCase("PORT")) || (row.equalsIgnoreCase("Port No")) || (row.equalsIgnoreCase("LDAP Server Port")) || (row.equalsIgnoreCase("Listener Port"))) {
/* 3219 */                                         PortIdx = tempIndex;
/*      */                                       }
/* 3221 */                                       if (row.equalsIgnoreCase("HOSTNAME / IP ADDRESS")) {
/* 3222 */                                         HostNameIPIdx = tempIndex;
/*      */                                       }
/* 3224 */                                       if (row.equalsIgnoreCase("JBOSSVERSION")) {
/* 3225 */                                         JBOSSVersionIdx = tempIndex;
/*      */                                       }
/* 3227 */                                       if (row.equalsIgnoreCase("ORACLEASVERSION")) {
/* 3228 */                                         OracleASVersionIdx = tempIndex;
/*      */                                       }
/* 3230 */                                       if (row.equalsIgnoreCase("TOMCATVERSION")) {
/* 3231 */                                         TomcatVersionIdx = tempIndex;
/*      */                                       }
/* 3233 */                                       if (row.equalsIgnoreCase("WEBLOGICVERSION")) {
/* 3234 */                                         WeblogicVersionIdx = tempIndex;
/*      */                                       }
/* 3236 */                                       if (row.equalsIgnoreCase("JNDIPATH")) {
/* 3237 */                                         JNDIPathIdx = tempIndex;
/*      */                                       }
/* 3239 */                                       if (row.equalsIgnoreCase("JMXURL")) {
/* 3240 */                                         JMXUrlIdx = tempIndex;
/*      */                                       }
/* 3242 */                                       if (row.equalsIgnoreCase("SSL ENABLED")) {
/* 3243 */                                         SSLEnabledIdx = tempIndex;
/*      */                                       }
/* 3245 */                                       if ((row.equalsIgnoreCase("DATABASENAME")) || (row.equalsIgnoreCase("DBname"))) {
/* 3246 */                                         DatabaseNameIdx = tempIndex;
/*      */                                       }
/* 3248 */                                       if (row.equalsIgnoreCase("TARGET ADDRESS")) {
/* 3249 */                                         TargetAddressIdx = tempIndex;
/*      */                                       }
/* 3251 */                                       if (row.equalsIgnoreCase("LDAP SERVER")) {
/* 3252 */                                         LDAPServerIdx = tempIndex;
/*      */                                       }
/* 3254 */                                       if (row.equalsIgnoreCase("IS SECURED")) {
/* 3255 */                                         IsSecuredIdx = tempIndex;
/*      */                                       }
/* 3257 */                                       if (row.equalsIgnoreCase("SEARCH BASE")) {
/* 3258 */                                         SearchBaseIdx = tempIndex;
/*      */                                       }
/* 3260 */                                       if (row.equalsIgnoreCase("DOWNLOAD FILE")) {
/* 3261 */                                         DownloadFileIdx = tempIndex;
/*      */                                       }
/* 3263 */                                       if (row.equalsIgnoreCase("SHOW AVAILABILITY DOWN WHEN DOWNLOAD FAILS")) {
/* 3264 */                                         ShowavailabilitydownwhendownloadfailsIdx = tempIndex;
/*      */                                       }
/* 3266 */                                       if (row.equalsIgnoreCase("UPLOAD FILE")) {
/* 3267 */                                         UploadFileIdx = tempIndex;
/*      */                                       }
/* 3269 */                                       if (row.equalsIgnoreCase("MAIL SUBJECT")) {
/* 3270 */                                         MailSubjectIdx = tempIndex;
/*      */                                       }
/* 3272 */                                       if (row.equalsIgnoreCase("SERVER CONNECTION CHANNEL")) {
/* 3273 */                                         ServerConnectionChannelIdx = tempIndex;
/*      */                                       }
/* 3275 */                                       if (row.equalsIgnoreCase("QUEUE MANAGER")) {
/* 3276 */                                         QueueManagerIdx = tempIndex;
/*      */                                       }
/* 3278 */                                       if (row.equalsIgnoreCase("ACCESS KEY ID")) {
/* 3279 */                                         AccessKeyIDIdx = tempIndex;
/*      */                                       }
/* 3281 */                                       if (row.equalsIgnoreCase("SECRET ACCESS KEY")) {
/* 3282 */                                         SecretAccessKeyIdx = tempIndex;
/*      */                                       }
/* 3284 */                                       if (row.equalsIgnoreCase("URLADDRESS")) {
/* 3285 */                                         URLAddressIdx = tempIndex;
/*      */                                       }
/* 3287 */                                       if (row.equalsIgnoreCase("DOMAIN")) {
/* 3288 */                                         DomainIdx = tempIndex;
/*      */                                       }
/* 3290 */                                       if (row.equalsIgnoreCase("LOOKUP ADDRESS")) {
/* 3291 */                                         LookupAddressIdx = tempIndex;
/*      */                                       }
/* 3293 */                                       if (row.equalsIgnoreCase("TIMEOUT")) {
/* 3294 */                                         TimeoutIdx = tempIndex;
/*      */                                       }
/* 3296 */                                       tempIndex++;
/*      */                                       
/* 3298 */                                       out.write("\n\n</td>\n");
/* 3299 */                                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3300 */                                       row = (String)_jspx_page_context.findAttribute("row");
/* 3301 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 3302 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3305 */                                     if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3306 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3309 */                                   if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3310 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                   }
/*      */                                   
/* 3313 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3314 */                                   out.write(10);
/* 3315 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3316 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3320 */                               if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3321 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                               }
/*      */                               
/* 3324 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3325 */                               out.write("\n</tr>\n\n");
/*      */                               
/* 3327 */                               boolean isDiscovery = true;
/*      */                               
/* 3329 */                               out.write(10);
/* 3330 */                               out.write(10);
/*      */                               
/* 3332 */                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3333 */                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 3334 */                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                               
/* 3336 */                               _jspx_th_logic_005fiterate_005f1.setName("data_table");
/*      */                               
/* 3338 */                               _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                               
/* 3340 */                               _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                               
/* 3342 */                               _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 3343 */                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 3344 */                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 3345 */                                 ArrayList row = null;
/* 3346 */                                 Integer j = null;
/* 3347 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3348 */                                   out = _jspx_page_context.pushBody();
/* 3349 */                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 3350 */                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                 }
/* 3352 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3353 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                 for (;;) {
/* 3355 */                                   out.write("\n<tr>\n\n\n");
/*      */                                   
/* 3357 */                                   int i = 0;
/* 3358 */                                   for (i = 0; i < row.size(); i++)
/*      */                                   {
/* 3360 */                                     String colvalue = getTrimmedText((String)row.get(i), 35);
/* 3361 */                                     if (i == monitorGroupIndex)
/*      */                                     {
/* 3363 */                                       colvalue = getTrimmedText((String)row.get(i), 102);
/*      */                                     }
/* 3365 */                                     if (i == displayNameIdx) {
/* 3366 */                                       isDisplaynamePresent = (isDisplaynamePresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3368 */                                     else if (i == hostNameIdx) {
/* 3369 */                                       isHostnamePresent = (isHostnamePresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3371 */                                     else if (i == subnetmaskIdx) {
/* 3372 */                                       isSubnetmaskPresent = (isSubnetmaskPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3374 */                                     else if (i == OStypeIdx) {
/* 3375 */                                       isOSTypePresent = (isOSTypePresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3377 */                                     else if (i == modeofMonIdx) {
/* 3378 */                                       isModeofMonPresent = (isModeofMonPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3380 */                                     else if (i == SMTPPortIdx) {
/* 3381 */                                       isSMTPPortIdxPresent = (isSMTPPortIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3383 */                                     else if (i == PortIdx) {
/* 3384 */                                       isPortIdxPresent = (isPortIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3386 */                                     else if (i == HostNameIPIdx) {
/* 3387 */                                       isHostNameIPIdxPresent = (isHostNameIPIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3389 */                                     else if (i == JBOSSVersionIdx) {
/* 3390 */                                       isJBOSSVersionIdxPresent = (isJBOSSVersionIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3392 */                                     else if (i == OracleASVersionIdx) {
/* 3393 */                                       isOracleASVersionIdxPresent = (isOracleASVersionIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3395 */                                     else if (i == TomcatVersionIdx) {
/* 3396 */                                       isTomcatVersionIdxPresent = (isTomcatVersionIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3398 */                                     else if (i == WeblogicVersionIdx) {
/* 3399 */                                       isWeblogicVersionIdxPresent = (isWeblogicVersionIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3401 */                                     else if (i == JNDIPathIdx) {
/* 3402 */                                       isJNDIPathIdxPresent = (isJNDIPathIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3404 */                                     else if (i == SSLEnabledIdx) {
/* 3405 */                                       isSSLEnabledIdxPresent = (isSSLEnabledIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3407 */                                     else if (i == DatabaseNameIdx) {
/* 3408 */                                       isDatabaseNameIdxPresent = (isDatabaseNameIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3410 */                                     else if (i == TargetAddressIdx) {
/* 3411 */                                       isTargetAddressIdxPresent = (isTargetAddressIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3413 */                                     else if (i == LDAPServerIdx) {
/* 3414 */                                       isLDAPServerIdxPresent = (isLDAPServerIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3416 */                                     else if (i == IsSecuredIdx) {
/* 3417 */                                       isIsSecuredIdxPresent = (isIsSecuredIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3419 */                                     else if (i == SearchBaseIdx) {
/* 3420 */                                       isSearchBaseIdxPresent = (isSearchBaseIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3422 */                                     else if (i == DownloadFileIdx) {
/* 3423 */                                       isDownloadFileIdxPresent = (isDownloadFileIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3425 */                                     else if (i == ShowavailabilitydownwhendownloadfailsIdx) {
/* 3426 */                                       isShowavailabilitydownwhendownloadfailsIdxPresent = (isShowavailabilitydownwhendownloadfailsIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3428 */                                     else if (i == UploadFileIdx) {
/* 3429 */                                       isUploadFileIdxPresent = (isUploadFileIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3431 */                                     else if (i == MailSubjectIdx) {
/* 3432 */                                       isMailSubjectIdxPresent = (isMailSubjectIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3434 */                                     else if (i == ServerConnectionChannelIdx) {
/* 3435 */                                       isServerConnectionChannelIdxPresent = (isServerConnectionChannelIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3437 */                                     else if (i == QueueManagerIdx) {
/* 3438 */                                       isQueueManagerIdxPresent = (isQueueManagerIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3440 */                                     else if (i == AccessKeyIDIdx) {
/* 3441 */                                       isAccessKeyIDIdxPresent = (isAccessKeyIDIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3443 */                                     else if (i == SecretAccessKeyIdx) {
/* 3444 */                                       isSecretAccessKeyIdxPresent = (isSecretAccessKeyIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3446 */                                     else if (i == URLAddressIdx) {
/* 3447 */                                       isURLAddressIdxPresent = (isURLAddressIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3449 */                                     else if (i == DomainIdx) {
/* 3450 */                                       isDomainIdxPresent = (isDomainIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3452 */                                     else if (i == LookupAddressIdx) {
/* 3453 */                                       isLookupAddressIdxPresent = (isLookupAddressIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3455 */                                     else if (i == TimeoutIdx) {
/* 3456 */                                       isTimeoutIdxPresent = (isTimeoutIdxPresent) && (isColumnNotEmpty(colvalue));
/*      */                                     }
/* 3458 */                                     if ((isDisplaynamePresent) && (isHostnamePresent) && (isSubnetmaskPresent) && (isOSTypePresent) && (isModeofMonPresent) && (isSMTPPortIdxPresent) && (isPortIdxPresent) && (isHostNameIPIdxPresent) && (isJBOSSVersionIdxPresent) && (isOracleASVersionIdxPresent) && (isTomcatVersionIdxPresent) && (isWeblogicVersionIdxPresent) && (isJNDIPathIdxPresent) && (isSSLEnabledIdxPresent) && (isDatabaseNameIdxPresent) && (isTargetAddressIdxPresent) && (isLDAPServerIdxPresent) && (isIsSecuredIdxPresent) && (isSearchBaseIdxPresent) && (isDownloadFileIdxPresent) && (isShowavailabilitydownwhendownloadfailsIdxPresent) && (isUploadFileIdxPresent) && (isMailSubjectIdxPresent) && (isServerConnectionChannelIdxPresent) && (isQueueManagerIdxPresent) && (isAccessKeyIDIdxPresent) && (isSecretAccessKeyIdxPresent) && (isURLAddressIdxPresent) && (isDomainIdxPresent) && (isLookupAddressIdxPresent) && (isTimeoutIdxPresent)) {
/* 3459 */                                       isDiscovery = isDiscovery;
/*      */                                     }
/*      */                                     else {
/* 3462 */                                       isDiscovery = false;
/*      */                                     }
/*      */                                     
/* 3465 */                                     if ((i == pass_col) || (passWordColIndex.contains(new Integer(i))))
/*      */                                     {
/* 3467 */                                       int password_length = colvalue.length();
/* 3468 */                                       colvalue = "";
/* 3469 */                                       for (int k = 0; k < password_length; k++)
/*      */                                       {
/* 3471 */                                         colvalue = colvalue + "*";
/*      */                                       }
/*      */                                     }
/* 3474 */                                     if (EnterpriseUtil.isIt360MSPEdition())
/*      */                                     {
/* 3476 */                                       if (i == monitorGroupIndex)
/*      */                                       {
/* 3478 */                                         isMonitorGroupExist = CustomerManagementAPI.getInstance().validateCustomerAvailability(colvalue);
/* 3479 */                                         if ((!isMonitorGroupExist) && (isDiscovery))
/*      */                                         {
/* 3481 */                                           isDiscovery = false;
/*      */                                         }
/*      */                                         
/* 3484 */                                         if (isMonitorGroupExist)
/*      */                                         {
/* 3486 */                                           out.println("<td class=\"whitegrayrightalign-reports\" nowrap=\"true\">");
/* 3487 */                                           out.println("&nbsp;" + colvalue);
/* 3488 */                                           out.println("</td>");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 3492 */                                           out.println("<td class=\"whitegrayrightalign-reports\" nowrap=\"true\">");
/* 3493 */                                           out.println("&nbsp;<font color=\"ff0000\">" + colvalue + "</font>");
/* 3494 */                                           out.println("</td>");
/*      */                                         }
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3499 */                                         out.println("<td class=\"whitegrayrightalign-reports\" nowrap=\"true\">");
/* 3500 */                                         out.println("&nbsp;" + colvalue);
/* 3501 */                                         out.println("</td>");
/*      */                                       }
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3506 */                                       out.println("<td class=\"whitegrayrightalign-reports\" nowrap=\"true\">");
/* 3507 */                                       out.println("&nbsp;" + colvalue);
/* 3508 */                                       out.println("</td>");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3512 */                                   out.write("\n</tr>\n");
/* 3513 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3514 */                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3515 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 3516 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3519 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3520 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3523 */                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3524 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                               }
/*      */                               
/* 3527 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3528 */                               out.write(10);
/*      */                               
/* 3530 */                               if (isDiscovery)
/*      */                               {
/*      */ 
/* 3533 */                                 out.write("\n\n  <tr>\n    <td  colspan=\"50\" align=\"center\"  class=\"tablebottom\" width=\"100%\">\n      <input name=\"button1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 3534 */                                 out.print(FormatUtil.getString("am.webclient.networkdiscovery.startdiscovery.text"));
/* 3535 */                                 out.write("\" onClick=\"javascript:fnFormSubmit1()\">\n\t  <input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 3536 */                                 out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3537 */                                 out.write("\" onClick=\"goBack()\"/>\n   </td>\n  </tr>\n");
/*      */                               }
/*      */                               
/*      */ 
/* 3541 */                               out.write("\n  \n</table>\n\n");
/*      */                               
/* 3543 */                               if (!isDiscovery)
/*      */                               {
/*      */ 
/* 3546 */                                 out.write("\n\t<table width=\"100%\" align=\"center\" class=\"IT-alert-message\">\n\t<tr><td class=\"whitegrayrightalign-reports\" nowrap><span>");
/* 3547 */                                 out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.invalid.msg.text"));
/* 3548 */                                 out.write("</span></td></tr>\n\t\t");
/* 3549 */                                 if (!isMonitorGroupExist) {
/* 3550 */                                   out.write("\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3551 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.invalidmg.msg.text"));
/* 3552 */                                   out.write("</td></tr>\n\t\t");
/*      */                                 }
/* 3554 */                                 out.write(10);
/* 3555 */                                 out.write(9);
/* 3556 */                                 out.write(9);
/* 3557 */                                 if (!isDisplaynamePresent) {
/* 3558 */                                   out.write("\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3559 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.nodisplayname.msg.text"));
/* 3560 */                                   out.write("</td></tr>\n\t\t");
/*      */                                 }
/* 3562 */                                 out.write(10);
/* 3563 */                                 out.write(9);
/* 3564 */                                 out.write(9);
/* 3565 */                                 if (!isHostnamePresent) {
/* 3566 */                                   out.write("\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3567 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Hostname" }));
/* 3568 */                                   out.write("</td></tr>\n\t\t");
/*      */                                 }
/* 3570 */                                 out.write(10);
/* 3571 */                                 out.write(9);
/* 3572 */                                 out.write(9);
/* 3573 */                                 if (!isSubnetmaskPresent) {
/* 3574 */                                   out.write("\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3575 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "SubNetMask" }));
/* 3576 */                                   out.write("</td></tr>\n\t\t");
/*      */                                 }
/* 3578 */                                 out.write(10);
/* 3579 */                                 out.write(9);
/* 3580 */                                 out.write(9);
/* 3581 */                                 if (!isOSTypePresent) {
/* 3582 */                                   out.write("\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3583 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Operating System" }));
/* 3584 */                                   out.write("</td></tr>\n\t\t");
/*      */                                 }
/* 3586 */                                 out.write(10);
/* 3587 */                                 out.write(9);
/* 3588 */                                 out.write(9);
/* 3589 */                                 if (!isModeofMonPresent) {
/* 3590 */                                   out.write("\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3591 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Mode of Monitoring" }));
/* 3592 */                                   out.write("</td></tr>\n\t\t");
/*      */                                 }
/* 3594 */                                 out.write(10);
/* 3595 */                                 out.write(9);
/* 3596 */                                 out.write(9);
/* 3597 */                                 if (!isSMTPPortIdxPresent) {
/* 3598 */                                   out.write("\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3599 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "SMTP Port" }));
/* 3600 */                                   out.write("</td></tr>\n\t\t");
/*      */                                 }
/* 3602 */                                 out.write(10);
/* 3603 */                                 out.write(9);
/* 3604 */                                 out.write(9);
/* 3605 */                                 if (!isPortIdxPresent) {
/* 3606 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3607 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Port" }));
/* 3608 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3610 */                                 out.write(10);
/* 3611 */                                 out.write(9);
/* 3612 */                                 out.write(9);
/* 3613 */                                 if (!isHostNameIPIdxPresent) {
/* 3614 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3615 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "HostName / IP Address" }));
/* 3616 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3618 */                                 out.write(10);
/* 3619 */                                 out.write(9);
/* 3620 */                                 out.write(9);
/* 3621 */                                 if (!isJBOSSVersionIdxPresent) {
/* 3622 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3623 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "JBOSSVersion" }));
/* 3624 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3626 */                                 out.write(10);
/* 3627 */                                 out.write(9);
/* 3628 */                                 out.write(9);
/* 3629 */                                 if (!isOracleASVersionIdxPresent) {
/* 3630 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3631 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "OracleASVersion" }));
/* 3632 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3634 */                                 out.write(10);
/* 3635 */                                 out.write(9);
/* 3636 */                                 out.write(9);
/* 3637 */                                 if (!isTomcatVersionIdxPresent) {
/* 3638 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3639 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "TomcatVersion" }));
/* 3640 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3642 */                                 out.write(10);
/* 3643 */                                 out.write(9);
/* 3644 */                                 out.write(9);
/* 3645 */                                 if (!isWeblogicVersionIdxPresent) {
/* 3646 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3647 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "WeblogicVersion" }));
/* 3648 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3650 */                                 out.write(10);
/* 3651 */                                 out.write(9);
/* 3652 */                                 out.write(9);
/* 3653 */                                 if (!isJNDIPathIdxPresent) {
/* 3654 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3655 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "JNDIPath" }));
/* 3656 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3658 */                                 out.write(10);
/* 3659 */                                 out.write(9);
/* 3660 */                                 out.write(9);
/* 3661 */                                 if (!isSSLEnabledIdxPresent) {
/* 3662 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3663 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "SSL Enabled" }));
/* 3664 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3666 */                                 out.write(10);
/* 3667 */                                 out.write(9);
/* 3668 */                                 out.write(9);
/* 3669 */                                 if (!isDatabaseNameIdxPresent) {
/* 3670 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3671 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Database Name" }));
/* 3672 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3674 */                                 out.write(10);
/* 3675 */                                 out.write(9);
/* 3676 */                                 out.write(9);
/* 3677 */                                 if (!isTargetAddressIdxPresent) {
/* 3678 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3679 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Target Address" }));
/* 3680 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3682 */                                 out.write(10);
/* 3683 */                                 out.write(9);
/* 3684 */                                 out.write(9);
/* 3685 */                                 if (!isLDAPServerIdxPresent) {
/* 3686 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3687 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "LDAP Server" }));
/* 3688 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3690 */                                 out.write(10);
/* 3691 */                                 out.write(9);
/* 3692 */                                 out.write(9);
/* 3693 */                                 if (!isIsSecuredIdxPresent) {
/* 3694 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3695 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Is Secured" }));
/* 3696 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3698 */                                 out.write(10);
/* 3699 */                                 out.write(9);
/* 3700 */                                 out.write(9);
/* 3701 */                                 if (!isSearchBaseIdxPresent) {
/* 3702 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3703 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Search Base" }));
/* 3704 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3706 */                                 out.write(10);
/* 3707 */                                 out.write(9);
/* 3708 */                                 out.write(9);
/* 3709 */                                 if (!isDownloadFileIdxPresent) {
/* 3710 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3711 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Download File" }));
/* 3712 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3714 */                                 out.write(10);
/* 3715 */                                 out.write(9);
/* 3716 */                                 out.write(9);
/* 3717 */                                 if (!isShowavailabilitydownwhendownloadfailsIdxPresent) {
/* 3718 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3719 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Show availability down when download fails" }));
/* 3720 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3722 */                                 out.write(10);
/* 3723 */                                 out.write(9);
/* 3724 */                                 out.write(9);
/* 3725 */                                 if (!isUploadFileIdxPresent) {
/* 3726 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3727 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Upload File" }));
/* 3728 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3730 */                                 out.write(10);
/* 3731 */                                 out.write(9);
/* 3732 */                                 out.write(9);
/* 3733 */                                 if (!isMailSubjectIdxPresent) {
/* 3734 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3735 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Mail Subject" }));
/* 3736 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3738 */                                 out.write(10);
/* 3739 */                                 out.write(9);
/* 3740 */                                 out.write(9);
/* 3741 */                                 if (!isServerConnectionChannelIdxPresent) {
/* 3742 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3743 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Server Connection Channel" }));
/* 3744 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3746 */                                 out.write(10);
/* 3747 */                                 out.write(9);
/* 3748 */                                 out.write(9);
/* 3749 */                                 if (!isQueueManagerIdxPresent) {
/* 3750 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3751 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Queue Manager" }));
/* 3752 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3754 */                                 out.write(10);
/* 3755 */                                 out.write(9);
/* 3756 */                                 out.write(9);
/* 3757 */                                 if (!isAccessKeyIDIdxPresent) {
/* 3758 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3759 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Access Key ID" }));
/* 3760 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3762 */                                 out.write(10);
/* 3763 */                                 out.write(9);
/* 3764 */                                 out.write(9);
/* 3765 */                                 if (!isSecretAccessKeyIdxPresent) {
/* 3766 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3767 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Secret Access Key" }));
/* 3768 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3770 */                                 out.write(10);
/* 3771 */                                 out.write(9);
/* 3772 */                                 out.write(9);
/* 3773 */                                 if (!isURLAddressIdxPresent) {
/* 3774 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3775 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "URLAddress" }));
/* 3776 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3778 */                                 out.write(10);
/* 3779 */                                 out.write(9);
/* 3780 */                                 out.write(9);
/* 3781 */                                 if (!isDomainIdxPresent) {
/* 3782 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3783 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Domain" }));
/* 3784 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3786 */                                 out.write(10);
/* 3787 */                                 out.write(9);
/* 3788 */                                 out.write(9);
/* 3789 */                                 if (!isLookupAddressIdxPresent) {
/* 3790 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3791 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Lookup Address" }));
/* 3792 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3794 */                                 out.write(10);
/* 3795 */                                 out.write(9);
/* 3796 */                                 out.write(9);
/* 3797 */                                 if (!isTimeoutIdxPresent) {
/* 3798 */                                   out.write("\t\n\t\t\t<tr><td class=\"whitegrayrightalign-reports\" nowrap>* ");
/* 3799 */                                   out.print(FormatUtil.getString("it360.webclient.admin.bulkimport.mandatory.field.msg.text", new String[] { "Timeout" }));
/* 3800 */                                   out.write("</td></tr>\t\n\t\t");
/*      */                                 }
/* 3802 */                                 out.write("\n\t\t<tr><td align=\"center\" nowrap><input type=\"button\" class=\"buttons btn_back\" value=\"");
/* 3803 */                                 out.print(FormatUtil.getString("it360.webclient.networkdiscovery.back.text"));
/* 3804 */                                 out.write("\" onClick=\"goBack();\"></input></td></tr>\n\t</table>\n  \t ");
/*      */                               }
/*      */                               
/*      */ 
/* 3808 */                               out.write(10);
/* 3809 */                               out.write(10);
/* 3810 */                               out.write(10);
/* 3811 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3812 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3816 */                           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3817 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                           }
/*      */                           
/* 3820 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3821 */                           out.write("\n</form>\n");
/*      */                         }
/* 3823 */                         out.write("\n\n\t\t");
/*      */                       }
/*      */                       
/*      */ 
/* 3827 */                       out.write("\n</td></tr>\n</table>\n</body>\n</html>\n");
/* 3828 */                       out.write(10);
/* 3829 */                       out.write(10);
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 3833 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 3836 */                     out.write("\n<script>\n$(document).ready(function(){\t\t\n\tsortSelectItems(\"type\");// NO I18N\t\n\tjQuery(\".chosenselect\").chosen();\t\t// NO I18N\n\tif (document.referrer.toLowerCase().indexOf( \"adminlayout\")!=-1) \n\t{\n\t\tjQuery(\".itadmin-hide\").hide();// NO I18N\n\t}\t\n});\n\n\t</script>\n");
/*      */                   }
/* 3838 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3839 */         out = _jspx_out;
/* 3840 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3841 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3842 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3845 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3851 */     PageContext pageContext = _jspx_page_context;
/* 3852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3854 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3855 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3856 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3858 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3860 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3861 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3862 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3863 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3864 */       return true;
/*      */     }
/* 3866 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3867 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3872 */     PageContext pageContext = _jspx_page_context;
/* 3873 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3875 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3876 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3877 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3879 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3880 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3881 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3883 */         out.write("\n\talertUser();\n\treturn false;\n\t");
/* 3884 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3885 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3889 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3890 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3891 */       return true;
/*      */     }
/* 3893 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3894 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3899 */     PageContext pageContext = _jspx_page_context;
/* 3900 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3902 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3903 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3904 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3906 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3907 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3908 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3910 */         out.write("\n\talertUser();\n\treturn false;\n\t");
/* 3911 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3912 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3916 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3917 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3918 */       return true;
/*      */     }
/* 3920 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3921 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3926 */     PageContext pageContext = _jspx_page_context;
/* 3927 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3929 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 3930 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 3931 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 3933 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 3935 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 3936 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 3937 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 3938 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3939 */       return true;
/*      */     }
/* 3941 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3947 */     PageContext pageContext = _jspx_page_context;
/* 3948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3950 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 3951 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 3952 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 3954 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 3956 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 3957 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 3958 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 3959 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3960 */       return true;
/*      */     }
/* 3962 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3968 */     PageContext pageContext = _jspx_page_context;
/* 3969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3971 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3972 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3973 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3975 */     _jspx_th_c_005fif_005f1.setTest("${servers != null}");
/* 3976 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3977 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3979 */         out.write("\n       \t\t\t\t");
/* 3980 */         if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3981 */           return true;
/* 3982 */         out.write("\n       \t\t\t");
/* 3983 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3984 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3988 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3989 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3990 */       return true;
/*      */     }
/* 3992 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3993 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3998 */     PageContext pageContext = _jspx_page_context;
/* 3999 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4001 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4002 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4003 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4005 */     _jspx_th_c_005fforEach_005f0.setItems("${servers}");
/*      */     
/* 4007 */     _jspx_th_c_005fforEach_005f0.setVar("prop");
/*      */     
/* 4009 */     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 4010 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 4012 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4013 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 4015 */           out.write("\n       \t\t\t\t\t");
/* 4016 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4017 */             return true;
/* 4018 */           out.write("\n       \t\t\t\t");
/* 4019 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4020 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4024 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 4025 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4028 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f0; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 4029 */         out = _jspx_page_context.popBody(); }
/* 4030 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4032 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4033 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 4035 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4040 */     PageContext pageContext = _jspx_page_context;
/* 4041 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4043 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4044 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4045 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4047 */     _jspx_th_c_005fout_005f1.setValue("${prop.name}");
/* 4048 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4049 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4050 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4051 */       return true;
/*      */     }
/* 4053 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4059 */     PageContext pageContext = _jspx_page_context;
/* 4060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4062 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4063 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 4064 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4066 */     _jspx_th_c_005fif_005f5.setTest("${appservers != null}");
/* 4067 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 4068 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 4070 */         out.write("\n       \t\t\t\t");
/* 4071 */         if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 4072 */           return true;
/* 4073 */         out.write("\n       \t\t\t");
/* 4074 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 4075 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4079 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 4080 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4081 */       return true;
/*      */     }
/* 4083 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4084 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4089 */     PageContext pageContext = _jspx_page_context;
/* 4090 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4092 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4093 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4094 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 4096 */     _jspx_th_c_005fforEach_005f1.setItems("${appservers}");
/*      */     
/* 4098 */     _jspx_th_c_005fforEach_005f1.setVar("prop");
/*      */     
/* 4100 */     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 4101 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 4103 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4104 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 4106 */           out.write("\n       \t\t\t\t    ");
/* 4107 */           if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4108 */             return true;
/* 4109 */           out.write("\n       \t\t\t\t");
/* 4110 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4111 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4115 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 4116 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4119 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f1; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 4120 */         out = _jspx_page_context.popBody(); }
/* 4121 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 4123 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4124 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 4126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4131 */     PageContext pageContext = _jspx_page_context;
/* 4132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4134 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 4135 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 4136 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4138 */     _jspx_th_c_005fforEach_005f2.setItems("${prop}");
/*      */     
/* 4140 */     _jspx_th_c_005fforEach_005f2.setVar("entry");
/* 4141 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 4143 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 4144 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 4146 */           out.write("\n       \t\t\t\t    <option value='");
/* 4147 */           boolean bool; if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 4148 */             return true;
/* 4149 */           out.write(39);
/* 4150 */           out.write(32);
/* 4151 */           out.write(62);
/* 4152 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 4153 */             return true;
/* 4154 */           out.write("</option>\n\n\n       \t\t\t\t    ");
/* 4155 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 4156 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4160 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 4161 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4164 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f2; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 4165 */         out = _jspx_page_context.popBody(); }
/* 4166 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 4168 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 4169 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 4171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 4176 */     PageContext pageContext = _jspx_page_context;
/* 4177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4179 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4180 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4181 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 4183 */     _jspx_th_c_005fout_005f2.setValue("${entry.key}");
/* 4184 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4185 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4186 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4187 */       return true;
/*      */     }
/* 4189 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 4195 */     PageContext pageContext = _jspx_page_context;
/* 4196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4198 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4199 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4200 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 4202 */     _jspx_th_c_005fout_005f3.setValue("${entry.value}");
/* 4203 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4204 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4205 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4206 */       return true;
/*      */     }
/* 4208 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4214 */     PageContext pageContext = _jspx_page_context;
/* 4215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4217 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4218 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4219 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4221 */     _jspx_th_c_005fif_005f7.setTest("${erp != null}");
/* 4222 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4223 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 4225 */         out.write("\n\t\t\t");
/* 4226 */         if (_jspx_meth_c_005fforEach_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 4227 */           return true;
/* 4228 */         out.write(10);
/* 4229 */         out.write(9);
/* 4230 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4231 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4235 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4236 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4237 */       return true;
/*      */     }
/* 4239 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4245 */     PageContext pageContext = _jspx_page_context;
/* 4246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4248 */     ForEachTag _jspx_th_c_005fforEach_005f3 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4249 */     _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
/* 4250 */     _jspx_th_c_005fforEach_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4252 */     _jspx_th_c_005fforEach_005f3.setItems("${erp}");
/*      */     
/* 4254 */     _jspx_th_c_005fforEach_005f3.setVar("prop");
/*      */     
/* 4256 */     _jspx_th_c_005fforEach_005f3.setVarStatus("status");
/* 4257 */     int[] _jspx_push_body_count_c_005fforEach_005f3 = { 0 };
/*      */     try {
/* 4259 */       int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
/* 4260 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f3 != 0) {
/*      */         for (;;) {
/* 4262 */           out.write("\n\t\t\t    ");
/* 4263 */           if (_jspx_meth_c_005fforEach_005f4(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 4264 */             return true;
/* 4265 */           out.write("\n\t\t\t");
/* 4266 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
/* 4267 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4271 */       if (_jspx_th_c_005fforEach_005f3.doEndTag() == 5)
/* 4272 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4275 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f3; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 4276 */         out = _jspx_page_context.popBody(); }
/* 4277 */       _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
/*      */     } finally {
/* 4279 */       _jspx_th_c_005fforEach_005f3.doFinally();
/* 4280 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
/*      */     }
/* 4282 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f4(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 4287 */     PageContext pageContext = _jspx_page_context;
/* 4288 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4290 */     ForEachTag _jspx_th_c_005fforEach_005f4 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 4291 */     _jspx_th_c_005fforEach_005f4.setPageContext(_jspx_page_context);
/* 4292 */     _jspx_th_c_005fforEach_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 4294 */     _jspx_th_c_005fforEach_005f4.setItems("${prop}");
/*      */     
/* 4296 */     _jspx_th_c_005fforEach_005f4.setVar("entry");
/* 4297 */     int[] _jspx_push_body_count_c_005fforEach_005f4 = { 0 };
/*      */     try {
/* 4299 */       int _jspx_eval_c_005fforEach_005f4 = _jspx_th_c_005fforEach_005f4.doStartTag();
/* 4300 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f4 != 0) {
/*      */         for (;;) {
/* 4302 */           out.write("\n\t\t\t    <option value='");
/* 4303 */           boolean bool; if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 4304 */             return true;
/* 4305 */           out.write(39);
/* 4306 */           out.write(32);
/* 4307 */           out.write(62);
/* 4308 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 4309 */             return true;
/* 4310 */           out.write("</option>\n\n\n\t\t\t    ");
/* 4311 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f4.doAfterBody();
/* 4312 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4316 */       if (_jspx_th_c_005fforEach_005f4.doEndTag() == 5)
/* 4317 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4320 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f4; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 4321 */         out = _jspx_page_context.popBody(); }
/* 4322 */       _jspx_th_c_005fforEach_005f4.doCatch(_jspx_exception);
/*      */     } finally {
/* 4324 */       _jspx_th_c_005fforEach_005f4.doFinally();
/* 4325 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4);
/*      */     }
/* 4327 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 4332 */     PageContext pageContext = _jspx_page_context;
/* 4333 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4335 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4336 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4337 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 4339 */     _jspx_th_c_005fout_005f4.setValue("${entry.key}");
/* 4340 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4341 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4342 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4343 */       return true;
/*      */     }
/* 4345 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4346 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 4351 */     PageContext pageContext = _jspx_page_context;
/* 4352 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4354 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4355 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4356 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 4358 */     _jspx_th_c_005fout_005f5.setValue("${entry.value}");
/* 4359 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4360 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4361 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4362 */       return true;
/*      */     }
/* 4364 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4365 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4370 */     PageContext pageContext = _jspx_page_context;
/* 4371 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4373 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4374 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 4375 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4377 */     _jspx_th_c_005fif_005f10.setTest("${dbservers != null}");
/* 4378 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 4379 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 4381 */         out.write("\n\t\t\t");
/* 4382 */         if (_jspx_meth_c_005fforEach_005f5(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 4383 */           return true;
/* 4384 */         out.write(10);
/* 4385 */         out.write(9);
/* 4386 */         out.write(9);
/* 4387 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4388 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4392 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4393 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4394 */       return true;
/*      */     }
/* 4396 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4397 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f5(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4402 */     PageContext pageContext = _jspx_page_context;
/* 4403 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4405 */     ForEachTag _jspx_th_c_005fforEach_005f5 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4406 */     _jspx_th_c_005fforEach_005f5.setPageContext(_jspx_page_context);
/* 4407 */     _jspx_th_c_005fforEach_005f5.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4409 */     _jspx_th_c_005fforEach_005f5.setItems("${dbservers}");
/*      */     
/* 4411 */     _jspx_th_c_005fforEach_005f5.setVar("prop");
/*      */     
/* 4413 */     _jspx_th_c_005fforEach_005f5.setVarStatus("status");
/* 4414 */     int[] _jspx_push_body_count_c_005fforEach_005f5 = { 0 };
/*      */     try {
/* 4416 */       int _jspx_eval_c_005fforEach_005f5 = _jspx_th_c_005fforEach_005f5.doStartTag();
/* 4417 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f5 != 0) {
/*      */         for (;;) {
/* 4419 */           out.write("\n\t\t\t    ");
/* 4420 */           if (_jspx_meth_c_005fforEach_005f6(_jspx_th_c_005fforEach_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
/* 4421 */             return true;
/* 4422 */           out.write("\n\t\t\t");
/* 4423 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f5.doAfterBody();
/* 4424 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4428 */       if (_jspx_th_c_005fforEach_005f5.doEndTag() == 5)
/* 4429 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4432 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f5; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 4433 */         out = _jspx_page_context.popBody(); }
/* 4434 */       _jspx_th_c_005fforEach_005f5.doCatch(_jspx_exception);
/*      */     } finally {
/* 4436 */       _jspx_th_c_005fforEach_005f5.doFinally();
/* 4437 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5);
/*      */     }
/* 4439 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f6(JspTag _jspx_th_c_005fforEach_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5) throws Throwable
/*      */   {
/* 4444 */     PageContext pageContext = _jspx_page_context;
/* 4445 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4447 */     ForEachTag _jspx_th_c_005fforEach_005f6 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 4448 */     _jspx_th_c_005fforEach_005f6.setPageContext(_jspx_page_context);
/* 4449 */     _jspx_th_c_005fforEach_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f5);
/*      */     
/* 4451 */     _jspx_th_c_005fforEach_005f6.setItems("${prop}");
/*      */     
/* 4453 */     _jspx_th_c_005fforEach_005f6.setVar("entry");
/* 4454 */     int[] _jspx_push_body_count_c_005fforEach_005f6 = { 0 };
/*      */     try {
/* 4456 */       int _jspx_eval_c_005fforEach_005f6 = _jspx_th_c_005fforEach_005f6.doStartTag();
/* 4457 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f6 != 0) {
/*      */         for (;;) {
/* 4459 */           out.write("\n\t\t\t    <option value='");
/* 4460 */           boolean bool; if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 4461 */             return true;
/* 4462 */           out.write(39);
/* 4463 */           out.write(32);
/* 4464 */           out.write(62);
/* 4465 */           if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 4466 */             return true;
/* 4467 */           out.write("</option>\n\n\n\t\t\t    ");
/* 4468 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f6.doAfterBody();
/* 4469 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4473 */       if (_jspx_th_c_005fforEach_005f6.doEndTag() == 5)
/* 4474 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4477 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f6; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 4478 */         out = _jspx_page_context.popBody(); }
/* 4479 */       _jspx_th_c_005fforEach_005f6.doCatch(_jspx_exception);
/*      */     } finally {
/* 4481 */       _jspx_th_c_005fforEach_005f6.doFinally();
/* 4482 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6);
/*      */     }
/* 4484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 4489 */     PageContext pageContext = _jspx_page_context;
/* 4490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4492 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4493 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4494 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f6);
/*      */     
/* 4496 */     _jspx_th_c_005fout_005f6.setValue("${entry.key}");
/* 4497 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4498 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4499 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4500 */       return true;
/*      */     }
/* 4502 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4503 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 4508 */     PageContext pageContext = _jspx_page_context;
/* 4509 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4511 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4512 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4513 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f6);
/*      */     
/* 4515 */     _jspx_th_c_005fout_005f7.setValue("${entry.value}");
/* 4516 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4517 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4518 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4519 */       return true;
/*      */     }
/* 4521 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4522 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4527 */     PageContext pageContext = _jspx_page_context;
/* 4528 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4530 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4531 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4532 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4534 */     _jspx_th_c_005fif_005f11.setTest("${webservers != null}");
/* 4535 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4536 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 4538 */         out.write("\n\t\t\t");
/* 4539 */         if (_jspx_meth_c_005fforEach_005f7(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 4540 */           return true;
/* 4541 */         out.write(10);
/* 4542 */         out.write(9);
/* 4543 */         out.write(9);
/* 4544 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4545 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4549 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4550 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4551 */       return true;
/*      */     }
/* 4553 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4554 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f7(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4559 */     PageContext pageContext = _jspx_page_context;
/* 4560 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4562 */     ForEachTag _jspx_th_c_005fforEach_005f7 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4563 */     _jspx_th_c_005fforEach_005f7.setPageContext(_jspx_page_context);
/* 4564 */     _jspx_th_c_005fforEach_005f7.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4566 */     _jspx_th_c_005fforEach_005f7.setItems("${webservers}");
/*      */     
/* 4568 */     _jspx_th_c_005fforEach_005f7.setVar("prop");
/*      */     
/* 4570 */     _jspx_th_c_005fforEach_005f7.setVarStatus("status");
/* 4571 */     int[] _jspx_push_body_count_c_005fforEach_005f7 = { 0 };
/*      */     try {
/* 4573 */       int _jspx_eval_c_005fforEach_005f7 = _jspx_th_c_005fforEach_005f7.doStartTag();
/* 4574 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f7 != 0) {
/*      */         for (;;) {
/* 4576 */           out.write("\n\t\t\t    ");
/* 4577 */           if (_jspx_meth_c_005fforEach_005f8(_jspx_th_c_005fforEach_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/* 4578 */             return true;
/* 4579 */           out.write("\n\t\t\t");
/* 4580 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f7.doAfterBody();
/* 4581 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4585 */       if (_jspx_th_c_005fforEach_005f7.doEndTag() == 5)
/* 4586 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4589 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f7; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 4590 */         out = _jspx_page_context.popBody(); }
/* 4591 */       _jspx_th_c_005fforEach_005f7.doCatch(_jspx_exception);
/*      */     } finally {
/* 4593 */       _jspx_th_c_005fforEach_005f7.doFinally();
/* 4594 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f7);
/*      */     }
/* 4596 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f8(JspTag _jspx_th_c_005fforEach_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 4601 */     PageContext pageContext = _jspx_page_context;
/* 4602 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4604 */     ForEachTag _jspx_th_c_005fforEach_005f8 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 4605 */     _jspx_th_c_005fforEach_005f8.setPageContext(_jspx_page_context);
/* 4606 */     _jspx_th_c_005fforEach_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f7);
/*      */     
/* 4608 */     _jspx_th_c_005fforEach_005f8.setItems("${prop}");
/*      */     
/* 4610 */     _jspx_th_c_005fforEach_005f8.setVar("entry");
/* 4611 */     int[] _jspx_push_body_count_c_005fforEach_005f8 = { 0 };
/*      */     try {
/* 4613 */       int _jspx_eval_c_005fforEach_005f8 = _jspx_th_c_005fforEach_005f8.doStartTag();
/* 4614 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f8 != 0) {
/*      */         for (;;) {
/* 4616 */           out.write("\n\t\t\t    <option value='");
/* 4617 */           boolean bool; if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f8))
/* 4618 */             return true;
/* 4619 */           out.write(39);
/* 4620 */           out.write(32);
/* 4621 */           out.write(62);
/* 4622 */           if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f8))
/* 4623 */             return true;
/* 4624 */           out.write("</option>\n\n\n\t\t\t    ");
/* 4625 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f8.doAfterBody();
/* 4626 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4630 */       if (_jspx_th_c_005fforEach_005f8.doEndTag() == 5)
/* 4631 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4634 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f8; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 4635 */         out = _jspx_page_context.popBody(); }
/* 4636 */       _jspx_th_c_005fforEach_005f8.doCatch(_jspx_exception);
/*      */     } finally {
/* 4638 */       _jspx_th_c_005fforEach_005f8.doFinally();
/* 4639 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8);
/*      */     }
/* 4641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f8) throws Throwable
/*      */   {
/* 4646 */     PageContext pageContext = _jspx_page_context;
/* 4647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4649 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4650 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4651 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f8);
/*      */     
/* 4653 */     _jspx_th_c_005fout_005f8.setValue("${entry.key}");
/* 4654 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4655 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4656 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4657 */       return true;
/*      */     }
/* 4659 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f8) throws Throwable
/*      */   {
/* 4665 */     PageContext pageContext = _jspx_page_context;
/* 4666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4668 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4669 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4670 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f8);
/*      */     
/* 4672 */     _jspx_th_c_005fout_005f9.setValue("${entry.value}");
/* 4673 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4674 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4675 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4676 */       return true;
/*      */     }
/* 4678 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4684 */     PageContext pageContext = _jspx_page_context;
/* 4685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4687 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4688 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 4689 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4691 */     _jspx_th_c_005fif_005f12.setTest("${services != null}");
/* 4692 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 4693 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 4695 */         out.write("\n\t\t\t");
/* 4696 */         if (_jspx_meth_c_005fforEach_005f9(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 4697 */           return true;
/* 4698 */         out.write(10);
/* 4699 */         out.write(9);
/* 4700 */         out.write(9);
/* 4701 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 4702 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4706 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 4707 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4708 */       return true;
/*      */     }
/* 4710 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4711 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f9(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4716 */     PageContext pageContext = _jspx_page_context;
/* 4717 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4719 */     ForEachTag _jspx_th_c_005fforEach_005f9 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4720 */     _jspx_th_c_005fforEach_005f9.setPageContext(_jspx_page_context);
/* 4721 */     _jspx_th_c_005fforEach_005f9.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 4723 */     _jspx_th_c_005fforEach_005f9.setItems("${services}");
/*      */     
/* 4725 */     _jspx_th_c_005fforEach_005f9.setVar("prop");
/*      */     
/* 4727 */     _jspx_th_c_005fforEach_005f9.setVarStatus("status");
/* 4728 */     int[] _jspx_push_body_count_c_005fforEach_005f9 = { 0 };
/*      */     try {
/* 4730 */       int _jspx_eval_c_005fforEach_005f9 = _jspx_th_c_005fforEach_005f9.doStartTag();
/* 4731 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f9 != 0) {
/*      */         for (;;) {
/* 4733 */           out.write("\n\t\t\t    ");
/* 4734 */           if (_jspx_meth_c_005fforEach_005f10(_jspx_th_c_005fforEach_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f9))
/* 4735 */             return true;
/* 4736 */           out.write("\n\t\t\t");
/* 4737 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f9.doAfterBody();
/* 4738 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4742 */       if (_jspx_th_c_005fforEach_005f9.doEndTag() == 5)
/* 4743 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4746 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f9; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 4747 */         out = _jspx_page_context.popBody(); }
/* 4748 */       _jspx_th_c_005fforEach_005f9.doCatch(_jspx_exception);
/*      */     } finally {
/* 4750 */       _jspx_th_c_005fforEach_005f9.doFinally();
/* 4751 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f9);
/*      */     }
/* 4753 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f10(JspTag _jspx_th_c_005fforEach_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f9) throws Throwable
/*      */   {
/* 4758 */     PageContext pageContext = _jspx_page_context;
/* 4759 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4761 */     ForEachTag _jspx_th_c_005fforEach_005f10 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 4762 */     _jspx_th_c_005fforEach_005f10.setPageContext(_jspx_page_context);
/* 4763 */     _jspx_th_c_005fforEach_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f9);
/*      */     
/* 4765 */     _jspx_th_c_005fforEach_005f10.setItems("${prop}");
/*      */     
/* 4767 */     _jspx_th_c_005fforEach_005f10.setVar("entry");
/* 4768 */     int[] _jspx_push_body_count_c_005fforEach_005f10 = { 0 };
/*      */     try {
/* 4770 */       int _jspx_eval_c_005fforEach_005f10 = _jspx_th_c_005fforEach_005f10.doStartTag();
/* 4771 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f10 != 0) {
/*      */         for (;;) {
/* 4773 */           out.write("\n\t\t\t    <option value='");
/* 4774 */           boolean bool; if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f10))
/* 4775 */             return true;
/* 4776 */           out.write(39);
/* 4777 */           out.write(32);
/* 4778 */           out.write(62);
/* 4779 */           if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f10))
/* 4780 */             return true;
/* 4781 */           out.write("</option>\n\n\n\t\t\t    ");
/* 4782 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f10.doAfterBody();
/* 4783 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4787 */       if (_jspx_th_c_005fforEach_005f10.doEndTag() == 5)
/* 4788 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4791 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f10; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 4792 */         out = _jspx_page_context.popBody(); }
/* 4793 */       _jspx_th_c_005fforEach_005f10.doCatch(_jspx_exception);
/*      */     } finally {
/* 4795 */       _jspx_th_c_005fforEach_005f10.doFinally();
/* 4796 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f10);
/*      */     }
/* 4798 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f10) throws Throwable
/*      */   {
/* 4803 */     PageContext pageContext = _jspx_page_context;
/* 4804 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4806 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4807 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4808 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f10);
/*      */     
/* 4810 */     _jspx_th_c_005fout_005f10.setValue("${entry.key}");
/* 4811 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4812 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4813 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4814 */       return true;
/*      */     }
/* 4816 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f10) throws Throwable
/*      */   {
/* 4822 */     PageContext pageContext = _jspx_page_context;
/* 4823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4825 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4826 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4827 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f10);
/*      */     
/* 4829 */     _jspx_th_c_005fout_005f11.setValue("${entry.value}");
/* 4830 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4831 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4832 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4833 */       return true;
/*      */     }
/* 4835 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4841 */     PageContext pageContext = _jspx_page_context;
/* 4842 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4844 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4845 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4846 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4848 */     _jspx_th_c_005fif_005f13.setTest("${mailservers != null}");
/* 4849 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4850 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 4852 */         out.write("\n\t\t\t");
/* 4853 */         if (_jspx_meth_c_005fforEach_005f11(_jspx_th_c_005fif_005f13, _jspx_page_context))
/* 4854 */           return true;
/* 4855 */         out.write(10);
/* 4856 */         out.write(9);
/* 4857 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4858 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4862 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4863 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4864 */       return true;
/*      */     }
/* 4866 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4867 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f11(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4872 */     PageContext pageContext = _jspx_page_context;
/* 4873 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4875 */     ForEachTag _jspx_th_c_005fforEach_005f11 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4876 */     _jspx_th_c_005fforEach_005f11.setPageContext(_jspx_page_context);
/* 4877 */     _jspx_th_c_005fforEach_005f11.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 4879 */     _jspx_th_c_005fforEach_005f11.setItems("${mailservers}");
/*      */     
/* 4881 */     _jspx_th_c_005fforEach_005f11.setVar("prop");
/*      */     
/* 4883 */     _jspx_th_c_005fforEach_005f11.setVarStatus("status");
/* 4884 */     int[] _jspx_push_body_count_c_005fforEach_005f11 = { 0 };
/*      */     try {
/* 4886 */       int _jspx_eval_c_005fforEach_005f11 = _jspx_th_c_005fforEach_005f11.doStartTag();
/* 4887 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f11 != 0) {
/*      */         for (;;) {
/* 4889 */           out.write("\n\t\t\t    ");
/* 4890 */           if (_jspx_meth_c_005fforEach_005f12(_jspx_th_c_005fforEach_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f11))
/* 4891 */             return true;
/* 4892 */           out.write("\n\t\t\t");
/* 4893 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f11.doAfterBody();
/* 4894 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4898 */       if (_jspx_th_c_005fforEach_005f11.doEndTag() == 5)
/* 4899 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4902 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f11; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 4903 */         out = _jspx_page_context.popBody(); }
/* 4904 */       _jspx_th_c_005fforEach_005f11.doCatch(_jspx_exception);
/*      */     } finally {
/* 4906 */       _jspx_th_c_005fforEach_005f11.doFinally();
/* 4907 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f11);
/*      */     }
/* 4909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f12(JspTag _jspx_th_c_005fforEach_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f11) throws Throwable
/*      */   {
/* 4914 */     PageContext pageContext = _jspx_page_context;
/* 4915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4917 */     ForEachTag _jspx_th_c_005fforEach_005f12 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 4918 */     _jspx_th_c_005fforEach_005f12.setPageContext(_jspx_page_context);
/* 4919 */     _jspx_th_c_005fforEach_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f11);
/*      */     
/* 4921 */     _jspx_th_c_005fforEach_005f12.setItems("${prop}");
/*      */     
/* 4923 */     _jspx_th_c_005fforEach_005f12.setVar("entry");
/* 4924 */     int[] _jspx_push_body_count_c_005fforEach_005f12 = { 0 };
/*      */     try {
/* 4926 */       int _jspx_eval_c_005fforEach_005f12 = _jspx_th_c_005fforEach_005f12.doStartTag();
/* 4927 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f12 != 0) {
/*      */         for (;;) {
/* 4929 */           out.write("\n\t\t\t    <option value='");
/* 4930 */           boolean bool; if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f12))
/* 4931 */             return true;
/* 4932 */           out.write(39);
/* 4933 */           out.write(32);
/* 4934 */           out.write(62);
/* 4935 */           if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f12))
/* 4936 */             return true;
/* 4937 */           out.write("</option>\n\n\n\t\t\t    ");
/* 4938 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f12.doAfterBody();
/* 4939 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4943 */       if (_jspx_th_c_005fforEach_005f12.doEndTag() == 5)
/* 4944 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4947 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f12; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 4948 */         out = _jspx_page_context.popBody(); }
/* 4949 */       _jspx_th_c_005fforEach_005f12.doCatch(_jspx_exception);
/*      */     } finally {
/* 4951 */       _jspx_th_c_005fforEach_005f12.doFinally();
/* 4952 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f12);
/*      */     }
/* 4954 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f12) throws Throwable
/*      */   {
/* 4959 */     PageContext pageContext = _jspx_page_context;
/* 4960 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4962 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4963 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4964 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f12);
/*      */     
/* 4966 */     _jspx_th_c_005fout_005f12.setValue("${entry.key}");
/* 4967 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4968 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4969 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4970 */       return true;
/*      */     }
/* 4972 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4973 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f12) throws Throwable
/*      */   {
/* 4978 */     PageContext pageContext = _jspx_page_context;
/* 4979 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4981 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4982 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4983 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f12);
/*      */     
/* 4985 */     _jspx_th_c_005fout_005f13.setValue("${entry.value}");
/* 4986 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4987 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4988 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4989 */       return true;
/*      */     }
/* 4991 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f13(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4997 */     PageContext pageContext = _jspx_page_context;
/* 4998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5000 */     ForEachTag _jspx_th_c_005fforEach_005f13 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5001 */     _jspx_th_c_005fforEach_005f13.setPageContext(_jspx_page_context);
/* 5002 */     _jspx_th_c_005fforEach_005f13.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 5004 */     _jspx_th_c_005fforEach_005f13.setItems("${momserver}");
/*      */     
/* 5006 */     _jspx_th_c_005fforEach_005f13.setVar("prop");
/*      */     
/* 5008 */     _jspx_th_c_005fforEach_005f13.setVarStatus("status");
/* 5009 */     int[] _jspx_push_body_count_c_005fforEach_005f13 = { 0 };
/*      */     try {
/* 5011 */       int _jspx_eval_c_005fforEach_005f13 = _jspx_th_c_005fforEach_005f13.doStartTag();
/* 5012 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f13 != 0) {
/*      */         for (;;) {
/* 5014 */           out.write("\n\t\t\t    ");
/* 5015 */           if (_jspx_meth_c_005fforEach_005f14(_jspx_th_c_005fforEach_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f13))
/* 5016 */             return true;
/* 5017 */           out.write("\n\t\t\t");
/* 5018 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f13.doAfterBody();
/* 5019 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5023 */       if (_jspx_th_c_005fforEach_005f13.doEndTag() == 5)
/* 5024 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5027 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f13; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 5028 */         out = _jspx_page_context.popBody(); }
/* 5029 */       _jspx_th_c_005fforEach_005f13.doCatch(_jspx_exception);
/*      */     } finally {
/* 5031 */       _jspx_th_c_005fforEach_005f13.doFinally();
/* 5032 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f13);
/*      */     }
/* 5034 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f14(JspTag _jspx_th_c_005fforEach_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f13) throws Throwable
/*      */   {
/* 5039 */     PageContext pageContext = _jspx_page_context;
/* 5040 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5042 */     ForEachTag _jspx_th_c_005fforEach_005f14 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 5043 */     _jspx_th_c_005fforEach_005f14.setPageContext(_jspx_page_context);
/* 5044 */     _jspx_th_c_005fforEach_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f13);
/*      */     
/* 5046 */     _jspx_th_c_005fforEach_005f14.setItems("${prop}");
/*      */     
/* 5048 */     _jspx_th_c_005fforEach_005f14.setVar("entry");
/* 5049 */     int[] _jspx_push_body_count_c_005fforEach_005f14 = { 0 };
/*      */     try {
/* 5051 */       int _jspx_eval_c_005fforEach_005f14 = _jspx_th_c_005fforEach_005f14.doStartTag();
/* 5052 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f14 != 0) {
/*      */         for (;;) {
/* 5054 */           out.write("\n\t\t\t    <option value='");
/* 5055 */           boolean bool; if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f14))
/* 5056 */             return true;
/* 5057 */           out.write(39);
/* 5058 */           out.write(32);
/* 5059 */           out.write(62);
/* 5060 */           if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f14))
/* 5061 */             return true;
/* 5062 */           out.write("</option>\n\n\n\t\t\t    ");
/* 5063 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f14.doAfterBody();
/* 5064 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5068 */       if (_jspx_th_c_005fforEach_005f14.doEndTag() == 5)
/* 5069 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5072 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f14; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 5073 */         out = _jspx_page_context.popBody(); }
/* 5074 */       _jspx_th_c_005fforEach_005f14.doCatch(_jspx_exception);
/*      */     } finally {
/* 5076 */       _jspx_th_c_005fforEach_005f14.doFinally();
/* 5077 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f14);
/*      */     }
/* 5079 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f14) throws Throwable
/*      */   {
/* 5084 */     PageContext pageContext = _jspx_page_context;
/* 5085 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5087 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5088 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5089 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f14);
/*      */     
/* 5091 */     _jspx_th_c_005fout_005f14.setValue("${entry.key}");
/* 5092 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5093 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5094 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5095 */       return true;
/*      */     }
/* 5097 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5098 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f14) throws Throwable
/*      */   {
/* 5103 */     PageContext pageContext = _jspx_page_context;
/* 5104 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5106 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5107 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5108 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f14);
/*      */     
/* 5110 */     _jspx_th_c_005fout_005f15.setValue("${entry.value}");
/* 5111 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5112 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5113 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5114 */       return true;
/*      */     }
/* 5116 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5117 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5122 */     PageContext pageContext = _jspx_page_context;
/* 5123 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5125 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5126 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 5127 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5129 */     _jspx_th_c_005fif_005f16.setTest("${virservers != null}");
/* 5130 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 5131 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 5133 */         out.write("\n\t\t\t");
/* 5134 */         if (_jspx_meth_c_005fforEach_005f15(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 5135 */           return true;
/* 5136 */         out.write(10);
/* 5137 */         out.write(9);
/* 5138 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 5139 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5143 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 5144 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 5145 */       return true;
/*      */     }
/* 5147 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 5148 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f15(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5153 */     PageContext pageContext = _jspx_page_context;
/* 5154 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5156 */     ForEachTag _jspx_th_c_005fforEach_005f15 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5157 */     _jspx_th_c_005fforEach_005f15.setPageContext(_jspx_page_context);
/* 5158 */     _jspx_th_c_005fforEach_005f15.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 5160 */     _jspx_th_c_005fforEach_005f15.setItems("${virservers}");
/*      */     
/* 5162 */     _jspx_th_c_005fforEach_005f15.setVar("prop");
/*      */     
/* 5164 */     _jspx_th_c_005fforEach_005f15.setVarStatus("status");
/* 5165 */     int[] _jspx_push_body_count_c_005fforEach_005f15 = { 0 };
/*      */     try {
/* 5167 */       int _jspx_eval_c_005fforEach_005f15 = _jspx_th_c_005fforEach_005f15.doStartTag();
/* 5168 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f15 != 0) {
/*      */         for (;;) {
/* 5170 */           out.write("\n\t\t\t    ");
/* 5171 */           if (_jspx_meth_c_005fforEach_005f16(_jspx_th_c_005fforEach_005f15, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f15))
/* 5172 */             return true;
/* 5173 */           out.write("\n\t\t\t");
/* 5174 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f15.doAfterBody();
/* 5175 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5179 */       if (_jspx_th_c_005fforEach_005f15.doEndTag() == 5)
/* 5180 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5183 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f15; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 5184 */         out = _jspx_page_context.popBody(); }
/* 5185 */       _jspx_th_c_005fforEach_005f15.doCatch(_jspx_exception);
/*      */     } finally {
/* 5187 */       _jspx_th_c_005fforEach_005f15.doFinally();
/* 5188 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f15);
/*      */     }
/* 5190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f16(JspTag _jspx_th_c_005fforEach_005f15, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f15) throws Throwable
/*      */   {
/* 5195 */     PageContext pageContext = _jspx_page_context;
/* 5196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5198 */     ForEachTag _jspx_th_c_005fforEach_005f16 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 5199 */     _jspx_th_c_005fforEach_005f16.setPageContext(_jspx_page_context);
/* 5200 */     _jspx_th_c_005fforEach_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f15);
/*      */     
/* 5202 */     _jspx_th_c_005fforEach_005f16.setItems("${prop}");
/*      */     
/* 5204 */     _jspx_th_c_005fforEach_005f16.setVar("entry");
/* 5205 */     int[] _jspx_push_body_count_c_005fforEach_005f16 = { 0 };
/*      */     try {
/* 5207 */       int _jspx_eval_c_005fforEach_005f16 = _jspx_th_c_005fforEach_005f16.doStartTag();
/* 5208 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f16 != 0) {
/*      */         for (;;) {
/* 5210 */           out.write("\n\t\t\t    <option value='");
/* 5211 */           boolean bool; if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fforEach_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f16))
/* 5212 */             return true;
/* 5213 */           out.write(39);
/* 5214 */           out.write(32);
/* 5215 */           out.write(62);
/* 5216 */           if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f16))
/* 5217 */             return true;
/* 5218 */           out.write("</option>\n\n\n\t\t\t    ");
/* 5219 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f16.doAfterBody();
/* 5220 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5224 */       if (_jspx_th_c_005fforEach_005f16.doEndTag() == 5)
/* 5225 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5228 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f16; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 5229 */         out = _jspx_page_context.popBody(); }
/* 5230 */       _jspx_th_c_005fforEach_005f16.doCatch(_jspx_exception);
/*      */     } finally {
/* 5232 */       _jspx_th_c_005fforEach_005f16.doFinally();
/* 5233 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f16);
/*      */     }
/* 5235 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fforEach_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f16) throws Throwable
/*      */   {
/* 5240 */     PageContext pageContext = _jspx_page_context;
/* 5241 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5243 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5244 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5245 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f16);
/*      */     
/* 5247 */     _jspx_th_c_005fout_005f16.setValue("${entry.key}");
/* 5248 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5249 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5250 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5251 */       return true;
/*      */     }
/* 5253 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5254 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f16) throws Throwable
/*      */   {
/* 5259 */     PageContext pageContext = _jspx_page_context;
/* 5260 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5262 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5263 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5264 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f16);
/*      */     
/* 5266 */     _jspx_th_c_005fout_005f17.setValue("${entry.value}");
/* 5267 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5268 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5269 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5270 */       return true;
/*      */     }
/* 5272 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5273 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5278 */     PageContext pageContext = _jspx_page_context;
/* 5279 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5281 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5282 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 5283 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5285 */     _jspx_th_c_005fif_005f17.setTest("${cloudapps != null}");
/* 5286 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 5287 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 5289 */         out.write("\n\t\t\t");
/* 5290 */         if (_jspx_meth_c_005fforEach_005f17(_jspx_th_c_005fif_005f17, _jspx_page_context))
/* 5291 */           return true;
/* 5292 */         out.write(10);
/* 5293 */         out.write(9);
/* 5294 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 5295 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5299 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 5300 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 5301 */       return true;
/*      */     }
/* 5303 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 5304 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f17(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5309 */     PageContext pageContext = _jspx_page_context;
/* 5310 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5312 */     ForEachTag _jspx_th_c_005fforEach_005f17 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5313 */     _jspx_th_c_005fforEach_005f17.setPageContext(_jspx_page_context);
/* 5314 */     _jspx_th_c_005fforEach_005f17.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5316 */     _jspx_th_c_005fforEach_005f17.setItems("${cloudapps}");
/*      */     
/* 5318 */     _jspx_th_c_005fforEach_005f17.setVar("prop");
/*      */     
/* 5320 */     _jspx_th_c_005fforEach_005f17.setVarStatus("status");
/* 5321 */     int[] _jspx_push_body_count_c_005fforEach_005f17 = { 0 };
/*      */     try {
/* 5323 */       int _jspx_eval_c_005fforEach_005f17 = _jspx_th_c_005fforEach_005f17.doStartTag();
/* 5324 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f17 != 0) {
/*      */         for (;;) {
/* 5326 */           out.write("\n\t\t\t    ");
/* 5327 */           if (_jspx_meth_c_005fforEach_005f18(_jspx_th_c_005fforEach_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f17))
/* 5328 */             return true;
/* 5329 */           out.write("\n\t\t\t");
/* 5330 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f17.doAfterBody();
/* 5331 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5335 */       if (_jspx_th_c_005fforEach_005f17.doEndTag() == 5)
/* 5336 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5339 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f17; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 5340 */         out = _jspx_page_context.popBody(); }
/* 5341 */       _jspx_th_c_005fforEach_005f17.doCatch(_jspx_exception);
/*      */     } finally {
/* 5343 */       _jspx_th_c_005fforEach_005f17.doFinally();
/* 5344 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f17);
/*      */     }
/* 5346 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f18(JspTag _jspx_th_c_005fforEach_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f17) throws Throwable
/*      */   {
/* 5351 */     PageContext pageContext = _jspx_page_context;
/* 5352 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5354 */     ForEachTag _jspx_th_c_005fforEach_005f18 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 5355 */     _jspx_th_c_005fforEach_005f18.setPageContext(_jspx_page_context);
/* 5356 */     _jspx_th_c_005fforEach_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f17);
/*      */     
/* 5358 */     _jspx_th_c_005fforEach_005f18.setItems("${prop}");
/*      */     
/* 5360 */     _jspx_th_c_005fforEach_005f18.setVar("entry");
/* 5361 */     int[] _jspx_push_body_count_c_005fforEach_005f18 = { 0 };
/*      */     try {
/* 5363 */       int _jspx_eval_c_005fforEach_005f18 = _jspx_th_c_005fforEach_005f18.doStartTag();
/* 5364 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f18 != 0) {
/*      */         for (;;) {
/* 5366 */           out.write("\n\t\t\t    <option value='");
/* 5367 */           boolean bool; if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fforEach_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f18))
/* 5368 */             return true;
/* 5369 */           out.write(39);
/* 5370 */           out.write(32);
/* 5371 */           out.write(62);
/* 5372 */           if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fforEach_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f18))
/* 5373 */             return true;
/* 5374 */           out.write("</option>\n\n\n\t\t\t    ");
/* 5375 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f18.doAfterBody();
/* 5376 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5380 */       if (_jspx_th_c_005fforEach_005f18.doEndTag() == 5)
/* 5381 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5384 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f18; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 5385 */         out = _jspx_page_context.popBody(); }
/* 5386 */       _jspx_th_c_005fforEach_005f18.doCatch(_jspx_exception);
/*      */     } finally {
/* 5388 */       _jspx_th_c_005fforEach_005f18.doFinally();
/* 5389 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f18);
/*      */     }
/* 5391 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fforEach_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f18) throws Throwable
/*      */   {
/* 5396 */     PageContext pageContext = _jspx_page_context;
/* 5397 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5399 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5400 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5401 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f18);
/*      */     
/* 5403 */     _jspx_th_c_005fout_005f18.setValue("${entry.key}");
/* 5404 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5405 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5406 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5407 */       return true;
/*      */     }
/* 5409 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5410 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fforEach_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f18) throws Throwable
/*      */   {
/* 5415 */     PageContext pageContext = _jspx_page_context;
/* 5416 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5418 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5419 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5420 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fforEach_005f18);
/*      */     
/* 5422 */     _jspx_th_c_005fout_005f19.setValue("${entry.value}");
/* 5423 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5424 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5425 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5426 */       return true;
/*      */     }
/* 5428 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5429 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ffile_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5434 */     PageContext pageContext = _jspx_page_context;
/* 5435 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5437 */     FileTag _jspx_th_html_005ffile_005f0 = (FileTag)this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fstyle_005fsize_005fproperty_005fnobody.get(FileTag.class);
/* 5438 */     _jspx_th_html_005ffile_005f0.setPageContext(_jspx_page_context);
/* 5439 */     _jspx_th_html_005ffile_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5441 */     _jspx_th_html_005ffile_005f0.setSize("30");
/*      */     
/* 5443 */     _jspx_th_html_005ffile_005f0.setProperty("theFile");
/*      */     
/* 5445 */     _jspx_th_html_005ffile_005f0.setStyle("overflow:hidden");
/* 5446 */     int _jspx_eval_html_005ffile_005f0 = _jspx_th_html_005ffile_005f0.doStartTag();
/* 5447 */     if (_jspx_th_html_005ffile_005f0.doEndTag() == 5) {
/* 5448 */       this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fstyle_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 5449 */       return true;
/*      */     }
/* 5451 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fstyle_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 5452 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Bulk_005fAddMonitors_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */