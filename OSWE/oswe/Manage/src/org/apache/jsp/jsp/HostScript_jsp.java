/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.beans.CAMGraphs;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.TimeChart;
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
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class HostScript_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  816 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  817 */     getRCATrimmedText(div1, rca);
/*  818 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  821 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  822 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  857 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  858 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  861 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  862 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  863 */       set = AMConnectionPool.executeQueryStmt(query);
/*  864 */       if (set.next())
/*      */       {
/*  866 */         String helpLink = set.getString("LINK");
/*  867 */         DBUtil.searchLinks.put(key, helpLink);
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
/*  975 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 2180 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2186 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2187 */   static { _jspx_dependants.put("/jsp/includes/ScriptMonitor.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2189 */     _jspx_dependants.put("/jsp/includes/ScriptTableDetails.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2203 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2207 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2214 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2218 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2220 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2221 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2223 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2230 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2233 */     JspWriter out = null;
/* 2234 */     Object page = this;
/* 2235 */     JspWriter _jspx_out = null;
/* 2236 */     PageContext _jspx_page_context = null;
/*      */     
/* 2238 */     String _jspx_colnames_1 = null;
/* 2239 */     Integer _jspx_k_1 = null;
/* 2240 */     String _jspx_rowid_1 = null;
/* 2241 */     Integer _jspx_i_1 = null;
/* 2242 */     String _jspx_colvalues_2 = null;
/* 2243 */     Integer _jspx_k_2 = null;
/*      */     try
/*      */     {
/* 2246 */       response.setContentType("text/html;charset=UTF-8");
/* 2247 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2249 */       _jspx_page_context = pageContext;
/* 2250 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2251 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2252 */       session = pageContext.getSession();
/* 2253 */       out = pageContext.getOut();
/* 2254 */       _jspx_out = out;
/*      */       
/* 2256 */       out.write("<!--$Id$-->\n");
/*      */       
/* 2258 */       request.setAttribute("HelpKey", "Script Monitor Details");
/*      */       
/* 2260 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2261 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2263 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2264 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2265 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2267 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2269 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2271 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2273 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2274 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2275 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2276 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2279 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2280 */         String available = null;
/* 2281 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2282 */         out.write(10);
/*      */         
/* 2284 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2285 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2286 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2288 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2290 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2292 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2294 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2295 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2296 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2297 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2300 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2301 */           String unavailable = null;
/* 2302 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2303 */           out.write(10);
/*      */           
/* 2305 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2306 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2307 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2309 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2311 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2313 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2315 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2316 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2317 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2318 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2321 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2322 */             String unmanaged = null;
/* 2323 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2324 */             out.write(10);
/*      */             
/* 2326 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2327 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2328 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2330 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2332 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2334 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2336 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2337 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2338 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2339 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2342 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2343 */               String scheduled = null;
/* 2344 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2345 */               out.write(10);
/*      */               
/* 2347 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2348 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2349 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2351 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2353 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2355 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2357 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2358 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2359 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2360 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2363 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2364 */                 String critical = null;
/* 2365 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2366 */                 out.write(10);
/*      */                 
/* 2368 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2369 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2370 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2372 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2374 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2376 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2378 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2379 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2380 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2381 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2384 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2385 */                   String clear = null;
/* 2386 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2387 */                   out.write(10);
/*      */                   
/* 2389 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2390 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2391 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2393 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2395 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2397 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2399 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2400 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2401 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2402 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2405 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2406 */                     String warning = null;
/* 2407 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2408 */                     out.write(10);
/* 2409 */                     out.write(10);
/*      */                     
/* 2411 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2412 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2414 */                     out.write(10);
/* 2415 */                     out.write(10);
/* 2416 */                     out.write(10);
/* 2417 */                     out.write("\n\n\n\n\n\n\n\n\n  \n");
/* 2418 */                     GetWLSGraph wlsGraph = null;
/* 2419 */                     wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2420 */                     if (wlsGraph == null) {
/* 2421 */                       wlsGraph = new GetWLSGraph();
/* 2422 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2424 */                     out.write(10);
/* 2425 */                     CAMGraphs camGraph = null;
/* 2426 */                     camGraph = (CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 2427 */                     if (camGraph == null) {
/* 2428 */                       camGraph = new CAMGraphs();
/* 2429 */                       _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                     }
/* 2431 */                     out.write(10);
/* 2432 */                     Hashtable motypedisplaynames = null;
/* 2433 */                     synchronized (application) {
/* 2434 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2435 */                       if (motypedisplaynames == null) {
/* 2436 */                         motypedisplaynames = new Hashtable();
/* 2437 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2440 */                     out.write(10);
/* 2441 */                     Hashtable availabilitykeys = null;
/* 2442 */                     synchronized (application) {
/* 2443 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2444 */                       if (availabilitykeys == null) {
/* 2445 */                         availabilitykeys = new Hashtable();
/* 2446 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2449 */                     out.write(10);
/* 2450 */                     Hashtable healthkeys = null;
/* 2451 */                     synchronized (application) {
/* 2452 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2453 */                       if (healthkeys == null) {
/* 2454 */                         healthkeys = new Hashtable();
/* 2455 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2458 */                     out.write("\n\n<script>\n\nfunction enableSelections(temp)\n{\n\tvar sel = false;\n\tfor(i=0;i<document.form2.elements.length;i++)\n\t{\n\t\tif(document.form2.elements[i].type==\"checkbox\")\n\t               {\n\t                        var name = document.form2.elements[i].name;\n\t                        if(name==\"checkbox\")\n\t                        {\n\t                        \tvar value = document.form2.elements[i].value;\n\t                        \tsel=document.form2.elements[i].checked;\n\t                        \tif(sel)\n\t                        \t{\n\t                        \t\tbreak;\n\t                        \t}\n\t                        }\n\t                 }\n               }\n               if(!sel)\n               {\n                  alert(\"Select the Attribute(s) you want to enable or disable Report\");\n               }\nelse \n{\n    if(temp==1)\n\tdocument.form2.action=\"/updateScript.do?method=enableReports&resourceid=");
/* 2459 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2461 */                     out.write("\";\n    else if(temp==2)\n    {\n        if(confirm('Are you sure you want to disable the reports for the selected attributes?'))\n            document.form2.action=\"/updateScript.do?method=disableReports&resourceid=");
/* 2462 */                     if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                       return;
/* 2464 */                     out.write("\";\n        else\n            return;\n    }\n        \n\tdocument.form2.method=\"Post\"\n\tdocument.form2.submit();\n}\n\n}\n\n/*function fnSelectAll(e)\n{\n\tToggleAll(e,document.form2,\"checkbox\")\n}*/\n\n</script>\n\n");
/*      */                     
/* 2466 */                     String dispname = (String)motypedisplaynames.get(request.getParameter("type")) + " Details";
/* 2467 */                     String resourceName = request.getParameter("resourcename");
/* 2468 */                     String resID = request.getParameter("resourceid");
/* 2469 */                     String resourceid = resID;
/* 2470 */                     request.setAttribute("resourceid", resID);
/* 2471 */                     ArrayList attribIDs = new ArrayList();
/* 2472 */                     String haid = request.getParameter("haid");
/* 2473 */                     String moname = request.getParameter("moname");
/* 2474 */                     ArrayList resIDs = new ArrayList();
/* 2475 */                     resIDs.add(resID);
/* 2476 */                     attribIDs.add("2200");
/* 2477 */                     attribIDs.add("2201");
/* 2478 */                     attribIDs.add("2202");
/* 2479 */                     Properties ess_atts = (Properties)request.getAttribute("ess_atts");
/* 2480 */                     String resourcetype = request.getParameter("type");
/* 2481 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */                     
/* 2483 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + request.getParameter("hostid"));
/* 2484 */                     String tipCurStatus = FormatUtil.getString("am.webclient.script.tooltip");
/* 2485 */                     HashMap ht_numeric = (HashMap)request.getAttribute("numeric_data");
/* 2486 */                     HashMap ht_string = (HashMap)request.getAttribute("string_data");
/* 2487 */                     HashMap displayname_attributeid = (HashMap)request.getAttribute("display_attributeid");
/* 2488 */                     int attributeid1 = -1;
/* 2489 */                     ArrayList numeric = (ArrayList)request.getAttribute("numeric");
/* 2490 */                     ArrayList all_attributes = (ArrayList)request.getAttribute("attributes");
/*      */                     
/* 2492 */                     if (all_attributes != null)
/*      */                     {
/* 2494 */                       for (int i = 0; i < all_attributes.size(); i++)
/*      */                       {
/* 2496 */                         if (String.valueOf(displayname_attributeid.get(all_attributes.get(i))) != null)
/*      */                         {
/*      */                           try
/*      */                           {
/* 2500 */                             attributeid1 = Integer.parseInt(String.valueOf(displayname_attributeid.get(all_attributes.get(i))));
/* 2501 */                             attribIDs.add("" + attributeid1);
/*      */                           }
/*      */                           catch (Exception exc) {}
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2511 */                     int num_data = ((Integer)request.getAttribute("numeric_size")).intValue();
/* 2512 */                     int str_data = ((Integer)request.getAttribute("string_size")).intValue();
/* 2513 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/* 2515 */                     out.write(10);
/* 2516 */                     out.write(10);
/* 2517 */                     out.write("<!--$Id$-->\n\n\n");
/*      */                     
/* 2519 */                     String baseid = "-1";
/* 2520 */                     if (request.getAttribute("baseid") != null)
/*      */                     {
/* 2522 */                       baseid = (String)request.getAttribute("baseid");
/*      */                     }
/*      */                     
/* 2525 */                     out.write("\n<Script>\n\nfunction fnSelectAllform(e,frm,name)\n{\n\tToggleAll(e,frm,name)\n}\n\nfunction deleteTablesAlert(resid,tableid,formid,frm)\n{\n\tdocument.getElementById(formid).resourceid.value=resid;\n\tdocument.getElementById(formid).tableid.value=tableid;\n\tif(confirm(\"");
/* 2526 */                     out.print(FormatUtil.getString("am.webclient.scripttable.delete.alert"));
/* 2527 */                     out.write("\"))\n\t{\n\tdocument.getElementById(formid).action=\"/updateScript.do?method=deleteTableByUser&resourceid=\"+resid+\"&tableid=\"+tableid;\n\tdocument.getElementById(formid).submit();\n\t}\n\telse\n\t{\n\t\treturn;\n\t}\n}\n\nfunction deleteRows(e,frm,objname,frmname,formid)\n{\n\tvar resid=\"\";\n\tvar count=0;\n\tif(!checkforOneSelected(frmname,objname))\n\t{\n\t  alert(\"");
/* 2528 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.delete"));
/* 2529 */                     out.write("\");\n\t  return;\n\t}\n\telse\n\t{\n\t\tfor(var i=0;i<document.getElementsByName(objname).length;i++)\n\t\t{\n\t\t\tif(document.getElementsByName(objname)[i].checked==true)\n\t\t\t{\n\t\t\t\tvar temp=document.getElementsByName(objname)[i].value;\n\t\t\t\tvar test=temp.split(\",\");\n\t\t\t\tvar res=test[0];\n\t\t\t\tif(resid!=\"\")\n\t\t\t\t{\n\t\t\t\tresid=resid+\",\"+res;\n\t\t\t\tcount++;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\tresid=res;\n\t\t\t\t}\n\t\t\t\t\n\t\t\t}\n\t\t\t\n\t\t}\n\t}\n\tif(confirm(\"");
/* 2530 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.delete.confirm"));
/* 2531 */                     out.write("\"))\n\t{\n\tdocument.getElementById(formid).action=\"/updateScript.do?method=deleteRowByUser&resourceid=\"+resid;\n\tdocument.getElementById(formid).method.value='deleteRowByUser';\n\tdocument.getElementById(formid).resourceid.value=resid;\n\tdocument.getElementById(formid).tableid.value=");
/* 2532 */                     out.print(resID);
/* 2533 */                     out.write(";\n\tdocument.getElementById(formid).submit();\n\t}\n\telse\n\t{\n\t\treturn;\n\t}\n}\n\nfunction showGraphForthis(attid,rowid,attribute)\n{\n    var count=1;\n    MM_openBrWindow('/jsp/PopUp_Graph.jsp?baseid=");
/* 2534 */                     out.print(baseid);
/* 2535 */                     out.write("&restype=");
/* 2536 */                     out.print(resourcetype);
/* 2537 */                     out.write("&resids='+rowid+'&attids='+attid+'&listsize='+count+'&attName='+attribute,'ExecutionTimeStatistic','width=800,height=500,top=200,left=200,scrollbars=yes,resizable=yes');\n}\n\nfunction showGraph(e,frm,objname,formid)\n{\n\tvar resid=\"\";\n\tvar attid=\"\";\n\tvar count=0;\n\tif(!checkforOneSelected(document.forms[frm],objname))\n\t{\n\t  alert(\"");
/* 2538 */                     out.print(FormatUtil.getString("am.comparereport.jsalert.text"));
/* 2539 */                     out.write("\");\n\t  return;\n\t}\n\telse\n\t{\n\tfor(var i=0;i<document.getElementsByName(objname).length;i++)\n\t\t{\n\t\t\tif(document.getElementsByName(objname)[i].checked==true)\n\t\t\t{\n\t\t\t\tvar temp=document.getElementsByName(objname)[i].value;\n\t\t\t\tvar test=temp.split(\",\");\n\t\t\t\tvar res=test[0];\n\t\t\t\tif(resid!=\"\")\n\t\t\t\t{\n\t\t\t\tresid=resid+\",\"+res;\n\t\t\t\tcount++;\n\t\t\t\t//attid=attid+\",\"+att;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\tresid=res;\n\t\t\t//\tattid=att;\n\t\t\t\t}\n\t\t\t\t\n\t\t\t}\n\t\t\t\n\t}\nattid=document.getElementById(formid).attList[document.getElementById(formid).attList.selectedIndex].value;\nMM_openBrWindow('/jsp/PopUp_Graph.jsp?baseid=");
/* 2540 */                     out.print(baseid);
/* 2541 */                     out.write("&restype=");
/* 2542 */                     out.print(resourcetype);
/* 2543 */                     out.write("&resids='+resid+'&attids='+attid+'&listsize='+count,'ExecutionTimeStatistic','width=800,height=500,top=200,left=200,scrollbars=yes,resizable=yes');\n\t\n        }\n}\n\n</Script>\n");
/*      */                     
/* 2545 */                     ArrayList table_details = (ArrayList)request.getAttribute("tabledetails");
/* 2546 */                     ArrayList tableids = new ArrayList();
/* 2547 */                     pageContext.setAttribute("table_ids", tableids);
/* 2548 */                     Hashtable table_resids = new Hashtable();
/* 2549 */                     Hashtable tabid_tabname = new Hashtable();
/* 2550 */                     Hashtable attid_details = new Hashtable();
/* 2551 */                     Hashtable table_data = new Hashtable();
/* 2552 */                     Hashtable table_health = new Hashtable();
/* 2553 */                     Hashtable table_avail = new Hashtable();
/* 2554 */                     Hashtable table_avail_data = new Hashtable();
/* 2555 */                     Hashtable table_rowsDisabled = new Hashtable();
/*      */                     
/* 2557 */                     if ((table_details != null) && (table_details.size() > 0))
/*      */                     {
/* 2559 */                       tableids = (ArrayList)table_details.get(0);
/* 2560 */                       pageContext.setAttribute("table_ids", tableids);
/* 2561 */                       table_resids = (Hashtable)table_details.get(1);
/* 2562 */                       tabid_tabname = (Hashtable)table_details.get(2);
/* 2563 */                       attid_details = (Hashtable)table_details.get(3);
/* 2564 */                       table_data = (Hashtable)table_details.get(4);
/* 2565 */                       table_health = (Hashtable)table_details.get(5);
/* 2566 */                       table_avail = (Hashtable)table_details.get(6);
/* 2567 */                       table_avail_data = (Hashtable)table_details.get(7);
/* 2568 */                       table_rowsDisabled = (Hashtable)table_details.get(8);
/*      */                     }
/* 2570 */                     int id1 = 0;
/*      */                     
/* 2572 */                     long curvalue = -1L;
/* 2573 */                     String temp = "-1";
/* 2574 */                     if (request.getAttribute("responsetime") != null)
/*      */                     {
/* 2576 */                       temp = (String)request.getAttribute("responsetime");
/*      */                     }
/* 2578 */                     String responsetimeid = ess_atts.getProperty("ResponseTime");
/* 2579 */                     String tipCurValue = "The time taken to get a response from this service.";
/* 2580 */                     wlsGraph.setParam(resID, "RESPONSETIME");
/* 2581 */                     HashMap hm_enable = (HashMap)request.getAttribute("hm_reports");
/* 2582 */                     ArrayList al = (ArrayList)request.getAttribute("enable");
/* 2583 */                     String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 2584 */                     String yaxis_restime = FormatUtil.getString("am.webclient.common.responsetime.text") + " " + FormatUtil.getString("am.webclient.common.units.ms.text");
/*      */                     
/*      */ 
/* 2587 */                     out.write("\n\n<table width=\"100%\">\n<tr>\n<td>\n\n<div id=\"reports\" style=\"DISPLAY: none\">\n<br>\n<form name=\"form2\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n<tr> \n<td width=\"72%\" height=\"31\" class=\"tableheading\" > ");
/* 2588 */                     out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 2589 */                     out.write("</td>\n\n</tr>\n</table>\n<table align=\"left\" width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n<tr>\n<td>\n<table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n<tr>\n<td width=\"10%\" height=\"28\" valign=\"center\"  class=\"columnheading\">\n<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAll(this)\"></td>\n<td width=\"40%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 2590 */                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                       return;
/* 2592 */                     out.write("</span></td>\n<td width=\"50%\" height=\"28%\" valign=\"center\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 2593 */                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */                       return;
/* 2595 */                     out.write("</span></td>\n</tr>\n\n");
/*      */                     
/* 2597 */                     String reports_enabled = FormatUtil.getString("am.webclient.script.reportsenabled");
/* 2598 */                     String reports_disabled = FormatUtil.getString("am.webclient.script.reportsdisabled");
/* 2599 */                     int bgclasschanger = 0;
/*      */                     
/* 2601 */                     out.write(10);
/* 2602 */                     out.write(10);
/*      */                     
/* 2604 */                     IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2605 */                     _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2606 */                     _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */                     
/* 2608 */                     _jspx_th_logic_005fiterate_005f0.setName("enable");
/*      */                     
/* 2610 */                     _jspx_th_logic_005fiterate_005f0.setId("attribute");
/*      */                     
/* 2612 */                     _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                     
/* 2614 */                     _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 2615 */                     int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2616 */                     if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2617 */                       String attribute = null;
/* 2618 */                       Integer j = null;
/* 2619 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2620 */                         out = _jspx_page_context.pushBody();
/* 2621 */                         _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2622 */                         _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                       }
/* 2624 */                       attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 2625 */                       j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                       for (;;) {
/* 2627 */                         out.write("\n<tr>\n\n");
/*      */                         
/* 2629 */                         String bgclass = "whitegrayborder";
/* 2630 */                         if (j.intValue() % 2 != 0)
/*      */                         {
/* 2632 */                           bgclass = "yellowgrayborder";
/*      */                         }
/* 2634 */                         bgclasschanger++;
/*      */                         
/* 2636 */                         out.write("\n\n<td width=\"10%\" height=\"22\"  class=\"");
/* 2637 */                         out.print(bgclass);
/* 2638 */                         out.write("\">\n<input type=\"checkbox\" name=\"checkbox\" value=\"");
/* 2639 */                         out.print(displayname_attributeid.get(attribute));
/* 2640 */                         out.write("\"/></td>\n<td width=\"40%\"  class=\"");
/* 2641 */                         out.print(bgclass);
/* 2642 */                         out.write("\" ><span class=\"bodytext\">");
/* 2643 */                         out.print(attribute);
/* 2644 */                         out.write("</span></td>\n");
/*      */                         
/* 2646 */                         if (hm_enable.get(attribute).equals("enabled"))
/*      */                         {
/*      */ 
/* 2649 */                           out.write("\n<td width=\"50%\" class=\"");
/* 2650 */                           out.print(bgclass);
/* 2651 */                           out.write("\"><img alt=\"Reports Enabled\" title=\"");
/* 2652 */                           out.print(reports_enabled);
/* 2653 */                           out.write("\" src=\"../images/cam_report_enabled.gif\" border=\"0\"></td>\n");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/* 2659 */                           out.write("\n<td width=\"50%\" class=\"");
/* 2660 */                           out.print(bgclass);
/* 2661 */                           out.write("\" ><img alt=\"Reports Disabled\" title=\"");
/* 2662 */                           out.print(reports_disabled);
/* 2663 */                           out.write("\" src=\"../images/cam_report_disabled.gif\" border=\"0\"></td>\n\n");
/*      */                         }
/*      */                         
/*      */ 
/* 2667 */                         out.write("\n</tr>\n");
/* 2668 */                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2669 */                         attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 2670 */                         j = (Integer)_jspx_page_context.findAttribute("j");
/* 2671 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2674 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2675 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2678 */                     if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2679 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*      */                     }
/*      */                     else {
/* 2682 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2683 */                       out.write(10);
/*      */                       
/* 2685 */                       IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2686 */                       _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2687 */                       _jspx_th_logic_005fiterate_005f1.setParent(null);
/*      */                       
/* 2689 */                       _jspx_th_logic_005fiterate_005f1.setName("table_ids");
/*      */                       
/* 2691 */                       _jspx_th_logic_005fiterate_005f1.setId("attribute");
/*      */                       
/* 2693 */                       _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                       
/* 2695 */                       _jspx_th_logic_005fiterate_005f1.setType("java.lang.String");
/* 2696 */                       int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2697 */                       if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2698 */                         String attribute = null;
/* 2699 */                         Integer j = null;
/* 2700 */                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2701 */                           out = _jspx_page_context.pushBody();
/* 2702 */                           _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2703 */                           _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                         }
/* 2705 */                         attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 2706 */                         j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                         for (;;) {
/* 2708 */                           out.write(10);
/*      */                           
/* 2710 */                           ArrayList attids = (ArrayList)((Hashtable)table_data.get(attribute)).get("column");
/* 2711 */                           pageContext.setAttribute("attids", attids);
/*      */                           
/* 2713 */                           out.write(10);
/*      */                           
/* 2715 */                           IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2716 */                           _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 2717 */                           _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                           
/* 2719 */                           _jspx_th_logic_005fiterate_005f2.setName("attids");
/*      */                           
/* 2721 */                           _jspx_th_logic_005fiterate_005f2.setId("colnames");
/*      */                           
/* 2723 */                           _jspx_th_logic_005fiterate_005f2.setIndexId("k");
/*      */                           
/* 2725 */                           _jspx_th_logic_005fiterate_005f2.setType("java.lang.String");
/* 2726 */                           int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 2727 */                           if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 2728 */                             String colnames = null;
/* 2729 */                             Integer k = null;
/* 2730 */                             if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 2731 */                               out = _jspx_page_context.pushBody();
/* 2732 */                               _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 2733 */                               _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                             }
/* 2735 */                             colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 2736 */                             k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                             for (;;) {
/* 2738 */                               out.write(10);
/*      */                               
/* 2740 */                               String bgclass = "whitegrayborder";
/* 2741 */                               if ((attid_details.get(colnames) != null) && (((ArrayList)attid_details.get(colnames)).get(1).equals("0")))
/*      */                               {
/*      */ 
/* 2744 */                                 if (bgclasschanger % 2 != 0)
/*      */                                 {
/* 2746 */                                   bgclass = "yellowgrayborder";
/*      */                                 }
/* 2748 */                                 bgclasschanger++;
/*      */                                 
/* 2750 */                                 out.write("\n<tr>\n<td width=\"10%\" height=\"22\"  class=\"");
/* 2751 */                                 out.print(bgclass);
/* 2752 */                                 out.write("\">\n<input type=\"checkbox\" name=\"checkbox\" value=\"");
/* 2753 */                                 out.print(colnames);
/* 2754 */                                 out.write("\"/></td>\n<td width=\"40%\"  class=\"");
/* 2755 */                                 out.print(bgclass);
/* 2756 */                                 out.write("\" ><span class=\"bodytext\">");
/* 2757 */                                 out.print(tabid_tabname.get(tableids.get(j.intValue())));
/* 2758 */                                 out.write(58);
/* 2759 */                                 out.print(((ArrayList)attid_details.get(colnames)).get(0));
/* 2760 */                                 out.write("</span></td>\n");
/*      */                                 
/* 2762 */                                 if (((ArrayList)attid_details.get(colnames)).get(2).equals("0"))
/*      */                                 {
/*      */ 
/* 2765 */                                   out.write("\n<td width=\"50%\" class=\"");
/* 2766 */                                   out.print(bgclass);
/* 2767 */                                   out.write("\" ><img alt=\"Reports Disabled\" title=\"");
/* 2768 */                                   out.print(reports_disabled);
/* 2769 */                                   out.write("\" src=\"../images/cam_report_disabled.gif\" border=\"0\"></td>\n");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 2775 */                                   out.write("\n<td width=\"50%\" class=\"");
/* 2776 */                                   out.print(bgclass);
/* 2777 */                                   out.write("\"><img alt=\"Reports Enabled\" title=\"");
/* 2778 */                                   out.print(reports_enabled);
/* 2779 */                                   out.write("\" src=\"../images/cam_report_enabled.gif\" border=\"0\"></td>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2783 */                                 out.write("\n</tr>\n");
/*      */                               }
/*      */                               
/*      */ 
/* 2787 */                               out.write(10);
/* 2788 */                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 2789 */                               colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 2790 */                               k = (Integer)_jspx_page_context.findAttribute("k");
/* 2791 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2794 */                             if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 2795 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2798 */                           if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 2799 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                           }
/*      */                           
/* 2802 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 2803 */                           out.write(10);
/* 2804 */                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 2805 */                           attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 2806 */                           j = (Integer)_jspx_page_context.findAttribute("j");
/* 2807 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2810 */                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2811 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2814 */                       if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 2815 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*      */                       }
/*      */                       else {
/* 2818 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 2819 */                         out.write("\n</table>\n</td>\n</tr>\n<tr>\n<td>\n<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"tablebottom\" >\n<tr>\n<td width=\"5%\" colspan=2 heigth=\"26\" class=\"bodytext\"><a href=\"javascript:enableSelections(1);\" class=\"links\">");
/* 2820 */                         out.print(FormatUtil.getString("am.webclient.script.enablereports"));
/* 2821 */                         out.write("</a> | <a href=\"javascript:enableSelections(2);\" class=\"links\">");
/* 2822 */                         out.print(FormatUtil.getString("am.webclient.script.disablereports"));
/* 2823 */                         out.write("</a></td>\n\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</form>\n<br>\n</div>\n</td>\n</tr>\n\n\n<tr>\n<td>\n\n\n");
/* 2824 */                         if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                           return;
/* 2826 */                         out.write(10);
/* 2827 */                         out.write(10);
/*      */                         
/* 2829 */                         String class1 = null;
/* 2830 */                         String class2 = null;
/* 2831 */                         if ((request.getParameter("fromhost") != null) && (request.getParameter("fromhost").equals("true")))
/*      */                         {
/* 2833 */                           class1 = "lrbborder";
/* 2834 */                           class2 = "tablebottom";
/*      */                         }
/*      */                         else
/*      */                         {
/* 2838 */                           class1 = "lrtbdarkborder";
/* 2839 */                           class2 = "tableheading";
/*      */                         }
/*      */                         
/*      */ 
/* 2843 */                         out.write("\n</tr>\n</td>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"");
/* 2844 */                         out.print(class1);
/* 2845 */                         out.write("\">\n  <tr> \n    <td height=\"26\" class=\"tableheading\">");
/* 2846 */                         out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 2847 */                         out.write(32);
/* 2848 */                         out.write(45);
/* 2849 */                         out.write(32);
/* 2850 */                         out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 2851 */                         out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr> \n    <td width=\"50%\" height=\"127\" valign=\"top\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\">\n        <tr> \n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2852 */                         out.print(resID);
/* 2853 */                         out.write("&attributeid=");
/* 2854 */                         out.print(responsetimeid);
/* 2855 */                         out.write("&period=-7',740,550)\"> \n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2856 */                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2857 */                         out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2858 */                         out.print(resID);
/* 2859 */                         out.write("&attributeid=");
/* 2860 */                         out.print(responsetimeid);
/* 2861 */                         out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2862 */                         out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2863 */                         out.write("\"></a></td>\n        </tr>\n        <tr> \n          <td colspan=\"2\"> ");
/*      */                         
/* 2865 */                         TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 2866 */                         _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 2867 */                         _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */                         
/* 2869 */                         _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wlsGraph");
/*      */                         
/* 2871 */                         _jspx_th_awolf_005ftimechart_005f0.setWidth("320");
/*      */                         
/* 2873 */                         _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                         
/* 2875 */                         _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                         
/* 2877 */                         _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                         
/* 2879 */                         _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_restime);
/*      */                         
/* 2881 */                         _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 2882 */                         int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 2883 */                         if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 2884 */                           if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2885 */                             out = _jspx_page_context.pushBody();
/* 2886 */                             _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 2887 */                             _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2890 */                             out.write(" \n            ");
/* 2891 */                             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 2892 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2895 */                           if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2896 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2899 */                         if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 2900 */                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*      */                         }
/*      */                         else {
/* 2903 */                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 2904 */                           out.write(" </td>\n        </tr>\n      </table></td>\n    <td width=\"50%\" valign=\"top\"> <br> <br> \n\n      <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrbborder\">\n\n\t  \n        <tr> \n          <td class=\"columnheadingtb\"><span class=\"bodytextbold\">");
/* 2905 */                           if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */                             return;
/* 2907 */                           out.write("</span></td>");
/* 2908 */                           out.write(" \n          <td class=\"columnheadingtb\"><span class=\"bodytextbold\">");
/* 2909 */                           if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */                             return;
/* 2911 */                           out.write("</span></td>");
/* 2912 */                           out.write(" \n          <td class=\"columnheadingtb\" colspan=\"2\"><span class=\"bodytextbold\">\n          ");
/*      */                           
/* 2914 */                           if (!temp.equals("-1"))
/*      */                           {
/* 2916 */                             out.write("\n          ");
/* 2917 */                             if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */                               return;
/* 2919 */                             out.write("</span>\n          ");
/*      */                           } else {
/* 2921 */                             out.println("&nbsp;");
/*      */                           }
/* 2923 */                           out.write("\n          </td>\n        </tr>\n        <tr> \n\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 2924 */                           out.print(tipCurValue);
/* 2925 */                           out.write(34);
/* 2926 */                           out.write(62);
/* 2927 */                           out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 2928 */                           out.write(32);
/* 2929 */                           out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 2930 */                           out.write("</td>\n          ");
/*      */                           
/* 2932 */                           if (!temp.equals("-1"))
/*      */                           {
/*      */ 
/* 2935 */                             out.write("\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 2936 */                             out.print(temp);
/* 2937 */                             out.write(32);
/* 2938 */                             out.print(FormatUtil.getString("ms"));
/* 2939 */                             out.write("</td>\n             \n            ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 2945 */                             out.write("\n          <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 2946 */                             out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 2947 */                             out.write("</td>\n            ");
/*      */                           }
/*      */                           
/* 2950 */                           out.write(10);
/* 2951 */                           out.write(9);
/* 2952 */                           out.write(32);
/*      */                           
/* 2954 */                           if (!temp.equals("-1"))
/*      */                           {
/*      */ 
/* 2957 */                             out.write("\n          <Td width=\"16%\" class=\"whitegrayborder\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2958 */                             out.print(resID);
/* 2959 */                             out.write("&attributeid=");
/* 2960 */                             out.print(responsetimeid);
/* 2961 */                             out.write("&alertconfigurl=");
/* 2962 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + responsetimeid + "attributeToSelect=" + responsetimeid + "&redirectto=" + encodeurl));
/* 2963 */                             out.write("')\">");
/* 2964 */                             out.print(getSeverityImage(alert.getProperty(resID + "#" + responsetimeid)));
/* 2965 */                             out.write(" </a>\n          </td>\n          ");
/*      */                           }
/*      */                           
/*      */ 
/* 2969 */                           out.write("\n        </tr>\n        <tr> \n          <td  colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2970 */                           out.print(resID);
/* 2971 */                           out.write("&attributeIDs=");
/* 2972 */                           out.print(responsetimeid);
/* 2973 */                           out.write("&attributeToSelect=");
/* 2974 */                           out.print(responsetimeid);
/* 2975 */                           out.write("&redirectto=");
/* 2976 */                           out.print(encodeurl);
/* 2977 */                           out.write("\" class=\"staticlinks\">");
/* 2978 */                           out.print(ALERTCONFIG_TEXT);
/* 2979 */                           out.write("</a></td>\n        </tr>\n      </table></td>\n  </tr>\n</table>    \n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr> \n    <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n  </tr>\n</table>\n\n\n\n\n");
/*      */                           
/* 2981 */                           int localattributes = 0;
/* 2982 */                           int widthOfTopMostTable = num_data == 1 ? 49 : 99;
/* 2983 */                           int secondLevelTableWidth = num_data == 1 ? 99 : 49;
/* 2984 */                           int widthofinnertables = num_data == 1 ? 100 : 98;
/*      */                           
/* 2986 */                           out.write("\n\n<br>\n<!--c:if test=\"${string_attributes!=0}\"-->\n\n");
/*      */                           
/* 2988 */                           if (str_data > 0)
/*      */                           {
/* 2990 */                             out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tr><td>\n<table WIDTH=\"99%\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" class=lrbtborder>\n<tr>\n<td>\n<table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n\t  <tr>\n\t  <td colspan=\"5\" class=\"tableheadingbborder\">");
/* 2991 */                             out.print(FormatUtil.getString("am.webclient.script.stringattheading"));
/* 2992 */                             out.write("</td>\n\t  </tr>\n        <tr>\n            <td width=\"50%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 2993 */                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */                               return;
/* 2995 */                             out.write("</span></td>");
/* 2996 */                             out.write("\n            <td width=\"15%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 2997 */                             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */                               return;
/* 2999 */                             out.write("</span></td>");
/* 3000 */                             out.write("\n          <td width=\"15%\" class=\"columnheading\" align=\"center\"><span class=\"bodytextbold\">");
/* 3001 */                             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */                               return;
/* 3003 */                             out.write("</span></td>");
/* 3004 */                             out.write("\n          <td width=\"20%\" class=\"columnheading\" colspan=\"2\" align=\"center\"><span class=\"bodytextbold\">");
/* 3005 */                             out.print(ALERTCONFIG_TEXT);
/* 3006 */                             out.write("</span></td>");
/* 3007 */                             out.write("\n        </tr>\n</table>\n        <tr>\n        <td>\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" >\n");
/*      */                             
/* 3009 */                             IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3010 */                             _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 3011 */                             _jspx_th_logic_005fiterate_005f3.setParent(null);
/*      */                             
/* 3013 */                             _jspx_th_logic_005fiterate_005f3.setName("non_numeric");
/*      */                             
/* 3015 */                             _jspx_th_logic_005fiterate_005f3.setId("attribute");
/*      */                             
/* 3017 */                             _jspx_th_logic_005fiterate_005f3.setIndexId("i");
/*      */                             
/* 3019 */                             _jspx_th_logic_005fiterate_005f3.setType("java.lang.String");
/* 3020 */                             int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 3021 */                             if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 3022 */                               String attribute = null;
/* 3023 */                               Integer i = null;
/* 3024 */                               if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3025 */                                 out = _jspx_page_context.pushBody();
/* 3026 */                                 _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 3027 */                                 _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                               }
/* 3029 */                               attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3030 */                               i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                               for (;;) {
/* 3032 */                                 out.write(10);
/*      */                                 
/* 3034 */                                 if (displayname_attributeid.get(attribute) != null)
/*      */                                 {
/* 3036 */                                   int attributeid = Integer.parseInt(String.valueOf(displayname_attributeid.get(attribute)));
/*      */                                   
/* 3038 */                                   out.write(10);
/*      */                                   
/* 3040 */                                   int j = i.intValue();
/* 3041 */                                   String border = "whitegrayborder";
/* 3042 */                                   if (j % 2 == 0) {
/* 3043 */                                     border = "whitegrayborder";
/*      */                                   } else {
/* 3045 */                                     border = "yellowgrayborder";
/*      */                                   }
/* 3047 */                                   String value = FormatUtil.getString("am.webclient.nodata.text");
/* 3048 */                                   if ((ht_string.get(attribute) != null) && (!String.valueOf(ht_string.get(attribute)).equalsIgnoreCase("null"))) {
/* 3049 */                                     value = (String)ht_string.get(attribute);
/*      */                                   }
/* 3051 */                                   out.write("\n         <tr>\n          <td  height=\"25\" colspan=\"0\" width=\"50%\" class=\"");
/* 3052 */                                   out.print(border);
/* 3053 */                                   out.write("\" align=\"left\" >");
/* 3054 */                                   out.print(attribute);
/* 3055 */                                   out.write(" </td>\n          <td  height=\"25\" colspan=\"2\" width=\"15%\" align=\"left\" class=\"");
/* 3056 */                                   out.print(border);
/* 3057 */                                   out.write(34);
/* 3058 */                                   out.write(32);
/* 3059 */                                   out.write(62);
/* 3060 */                                   out.print(value);
/* 3061 */                                   out.write(" </td>\n          <td  width=\"15%\" colspan=\"2\" align=\"center\" class=\"");
/* 3062 */                                   out.print(border);
/* 3063 */                                   out.write("\" >  <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3064 */                                   out.print(resID);
/* 3065 */                                   out.write("&attributeid=");
/* 3066 */                                   out.print(attributeid);
/* 3067 */                                   out.write("&alertconfigurl=");
/* 3068 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + attributeid + "attributeToSelect=" + attributeid + "&redirectto=" + encodeurl));
/* 3069 */                                   out.write("')\">");
/* 3070 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + attributeid)));
/* 3071 */                                   out.write(" </a></td>\n          <td  width=\"20%\" colspan=\"2\" height=\"35\" align=\"center\" class=\"");
/* 3072 */                                   out.print(border);
/* 3073 */                                   out.write("\" align=\"center\" ><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3074 */                                   out.print(resID);
/* 3075 */                                   out.write("&attributeIDs=");
/* 3076 */                                   out.print(attributeid);
/* 3077 */                                   out.write("&attributeToSelect=");
/* 3078 */                                   out.print(attributeid);
/* 3079 */                                   out.write("&redirectto=");
/* 3080 */                                   out.print(encodeurl);
/* 3081 */                                   out.write("\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 10px; height: 13px;\" border=\"0\" title=\"\"></a></td>\n        </tr>\n        ");
/*      */                                 }
/* 3083 */                                 out.write(10);
/* 3084 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 3085 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3086 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/* 3087 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3090 */                               if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3091 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3094 */                             if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 3095 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                             }
/*      */                             
/* 3098 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 3099 */                             out.write("\n</table>\n</td>\n</tr>\n</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n");
/*      */                           }
/* 3101 */                           out.write("\n\n<br>\n");
/*      */                           
/* 3103 */                           if (num_data > 0)
/*      */                           {
/* 3105 */                             out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tr><td>\n<table WIDTH=\"99%\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" class=lrtborder>\n<tr>\n<td>\n<table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n\t  <tr>\n\t  <td colspan=\"5\" class=\"tableheading\">");
/* 3106 */                             out.print(FormatUtil.getString("Numeric Attributes Details"));
/* 3107 */                             out.write("</td>\n\t  </tr>\n        <tr>\n          <td width=\"50%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 3108 */                             if (_jspx_meth_fmt_005fmessage_005f8(_jspx_page_context))
/*      */                               return;
/* 3110 */                             out.write("</span></td>");
/* 3111 */                             out.write("\n          <td width=\"15%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 3112 */                             if (_jspx_meth_fmt_005fmessage_005f9(_jspx_page_context))
/*      */                               return;
/* 3114 */                             out.write("</span></td>");
/* 3115 */                             out.write("\n          <td width=\"15%\" class=\"columnheading\" align=\"center\"><span class=\"bodytextbold\">");
/* 3116 */                             if (_jspx_meth_fmt_005fmessage_005f10(_jspx_page_context))
/*      */                               return;
/* 3118 */                             out.write("</span></td>");
/* 3119 */                             out.write("\n          <td width=\"20%\" class=\"columnheading\" colspan=\"2\" align=\"center\"><span class=\"bodytextbold\">");
/* 3120 */                             out.print(ALERTCONFIG_TEXT);
/* 3121 */                             out.write("</span></td>");
/* 3122 */                             out.write("\n        </tr>\n</table>\n        <tr>\n        <td>\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" >\n");
/*      */                             
/* 3124 */                             IterateTag _jspx_th_logic_005fiterate_005f4 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3125 */                             _jspx_th_logic_005fiterate_005f4.setPageContext(_jspx_page_context);
/* 3126 */                             _jspx_th_logic_005fiterate_005f4.setParent(null);
/*      */                             
/* 3128 */                             _jspx_th_logic_005fiterate_005f4.setName("numeric");
/*      */                             
/* 3130 */                             _jspx_th_logic_005fiterate_005f4.setId("attribute");
/*      */                             
/* 3132 */                             _jspx_th_logic_005fiterate_005f4.setIndexId("i");
/*      */                             
/* 3134 */                             _jspx_th_logic_005fiterate_005f4.setType("java.lang.String");
/* 3135 */                             int _jspx_eval_logic_005fiterate_005f4 = _jspx_th_logic_005fiterate_005f4.doStartTag();
/* 3136 */                             if (_jspx_eval_logic_005fiterate_005f4 != 0) {
/* 3137 */                               String attribute = null;
/* 3138 */                               Integer i = null;
/* 3139 */                               if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 3140 */                                 out = _jspx_page_context.pushBody();
/* 3141 */                                 _jspx_th_logic_005fiterate_005f4.setBodyContent((BodyContent)out);
/* 3142 */                                 _jspx_th_logic_005fiterate_005f4.doInitBody();
/*      */                               }
/* 3144 */                               attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3145 */                               i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                               for (;;) {
/* 3147 */                                 out.write(10);
/*      */                                 
/* 3149 */                                 if (displayname_attributeid.get(attribute) != null)
/*      */                                 {
/* 3151 */                                   int attributeid = Integer.parseInt(String.valueOf(displayname_attributeid.get(attribute)));
/*      */                                   
/* 3153 */                                   out.write(10);
/*      */                                   
/* 3155 */                                   int j = i.intValue();
/* 3156 */                                   String border = "whitegrayborder";
/* 3157 */                                   if (j % 2 == 0) {
/* 3158 */                                     border = "whitegrayborder";
/*      */                                   } else {
/* 3160 */                                     border = "yellowgrayborder";
/*      */                                   }
/* 3162 */                                   String value = FormatUtil.getString("am.webclient.nodata.text");
/* 3163 */                                   if ((ht_numeric.get(attribute) != null) && (!String.valueOf(ht_numeric.get(attribute)).equalsIgnoreCase("null"))) {
/* 3164 */                                     value = (String)ht_numeric.get(attribute);
/*      */                                   }
/* 3166 */                                   out.write("\n         <tr>\n          <td  height=\"25\" colspan=\"0\" width=\"50%\" class=\"");
/* 3167 */                                   out.print(border);
/* 3168 */                                   out.write("\" align=\"left\" >");
/* 3169 */                                   out.print(attribute);
/* 3170 */                                   out.write(" </td>\n          <td  height=\"25\" colspan=\"2\" width=\"15%\" align=\"left\" class=\"");
/* 3171 */                                   out.print(border);
/* 3172 */                                   out.write(34);
/* 3173 */                                   out.write(32);
/* 3174 */                                   out.write(62);
/* 3175 */                                   out.print(value);
/* 3176 */                                   out.write(" </td>\n          <td  width=\"15%\" colspan=\"2\" align=\"center\" class=\"");
/* 3177 */                                   out.print(border);
/* 3178 */                                   out.write("\" >  <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3179 */                                   out.print(resID);
/* 3180 */                                   out.write("&attributeid=");
/* 3181 */                                   out.print(attributeid);
/* 3182 */                                   out.write("&alertconfigurl=");
/* 3183 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + attributeid + "attributeToSelect=" + attributeid + "&redirectto=" + encodeurl));
/* 3184 */                                   out.write("')\">");
/* 3185 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + attributeid)));
/* 3186 */                                   out.write(" </a></td>\n          <td  width=\"20%\" colspan=\"2\" height=\"35\" align=\"center\" class=\"");
/* 3187 */                                   out.print(border);
/* 3188 */                                   out.write("\" align=\"center\" ><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3189 */                                   out.print(resID);
/* 3190 */                                   out.write("&attributeIDs=");
/* 3191 */                                   out.print(attributeid);
/* 3192 */                                   out.write("&attributeToSelect=");
/* 3193 */                                   out.print(attributeid);
/* 3194 */                                   out.write("&redirectto=");
/* 3195 */                                   out.print(encodeurl);
/* 3196 */                                   out.write("\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 10px; height: 13px;\" border=\"0\" title=\"\"></a></td>\n        </tr>\n        ");
/*      */                                 }
/* 3198 */                                 out.write(10);
/* 3199 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f4.doAfterBody();
/* 3200 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3201 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/* 3202 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3205 */                               if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 3206 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3209 */                             if (_jspx_th_logic_005fiterate_005f4.doEndTag() == 5) {
/* 3210 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4); return;
/*      */                             }
/*      */                             
/* 3213 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4);
/* 3214 */                             out.write("\n</table>\n</td>\n</tr>\n</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n");
/*      */                           }
/* 3216 */                           out.write("\n\n<br>\n");
/*      */                           
/* 3218 */                           if (num_data > 0)
/*      */                           {
/* 3220 */                             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tr><td>\n<table WIDTH=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\n<tr>\n");
/*      */                             
/* 3222 */                             IterateTag _jspx_th_logic_005fiterate_005f5 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3223 */                             _jspx_th_logic_005fiterate_005f5.setPageContext(_jspx_page_context);
/* 3224 */                             _jspx_th_logic_005fiterate_005f5.setParent(null);
/*      */                             
/* 3226 */                             _jspx_th_logic_005fiterate_005f5.setName("numeric");
/*      */                             
/* 3228 */                             _jspx_th_logic_005fiterate_005f5.setId("attribute");
/*      */                             
/* 3230 */                             _jspx_th_logic_005fiterate_005f5.setIndexId("i");
/*      */                             
/* 3232 */                             _jspx_th_logic_005fiterate_005f5.setType("java.lang.String");
/* 3233 */                             int _jspx_eval_logic_005fiterate_005f5 = _jspx_th_logic_005fiterate_005f5.doStartTag();
/* 3234 */                             if (_jspx_eval_logic_005fiterate_005f5 != 0) {
/* 3235 */                               String attribute = null;
/* 3236 */                               Integer i = null;
/* 3237 */                               if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 3238 */                                 out = _jspx_page_context.pushBody();
/* 3239 */                                 _jspx_th_logic_005fiterate_005f5.setBodyContent((BodyContent)out);
/* 3240 */                                 _jspx_th_logic_005fiterate_005f5.doInitBody();
/*      */                               }
/* 3242 */                               attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3243 */                               i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                               for (;;) {
/* 3245 */                                 out.write(10);
/* 3246 */                                 out.write(10);
/*      */                                 
/* 3248 */                                 boolean isLeftBox = localattributes % 2 == 0;
/* 3249 */                                 boolean isLastBox = localattributes == num_data - 1;
/* 3250 */                                 localattributes++;
/* 3251 */                                 int attributeid = Integer.parseInt(String.valueOf(displayname_attributeid.get(attribute)));
/*      */                                 
/* 3253 */                                 if ((resourcetype != null) && (!resourcetype.equals("Script Monitor")))
/*      */                                 {
/* 3255 */                                   camGraph.setParam(attributeid, resourcetype, attribute, Integer.parseInt(resID), true, baseid);
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3259 */                                   camGraph.setParam(attributeid, resourcetype, attribute, Integer.parseInt(resID), false);
/*      */                                 }
/*      */                                 
/* 3262 */                                 String value = FormatUtil.getString("am.webclient.nodata.text");
/* 3263 */                                 if ((ht_numeric.get(attribute) != null) && (!String.valueOf(ht_numeric.get(attribute)).equalsIgnoreCase("null")))
/*      */                                 {
/* 3265 */                                   value = String.valueOf(ht_numeric.get(attribute));
/*      */                                 }
/*      */                                 
/*      */ 
/* 3269 */                                 out.write("\n\n<td  width=\"");
/* 3270 */                                 out.print(secondLevelTableWidth);
/* 3271 */                                 out.write("%\" align=\"left\" >\n  \t<table WIDTH=\"");
/* 3272 */                                 out.print(widthofinnertables);
/* 3273 */                                 out.write("%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"grayfullborder\">\n          <tr> \n            <td height=\"26\" class=\"tableheading\">");
/* 3274 */                                 out.print(attribute);
/* 3275 */                                 out.write(32);
/* 3276 */                                 out.write(45);
/* 3277 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3278 */                                 out.write("</td>   \n          </tr>\n          <tr>\n <td width=\"100%\" height=\"127\" valign=\"top\" colspan=2> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"50%\">\n ");
/*      */                                 
/* 3280 */                                 if (hm_enable.get(attribute).equals("enabled"))
/*      */                                 {
/*      */ 
/* 3283 */                                   out.write("\n        <tr> \n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3284 */                                   out.print(resID);
/* 3285 */                                   out.write("&attributeid=");
/* 3286 */                                   out.print(attributeid);
/* 3287 */                                   out.write("&period=-7',740,550)\"> \n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last seven days data\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3288 */                                   out.print(resID);
/* 3289 */                                   out.write("&attributeid=");
/* 3290 */                                   out.print(attributeid);
/* 3291 */                                   out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last thirty days data\"></a></td>\n        </tr>\n");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3297 */                                   out.write("\n        <tr> \n          <td width=\"90%\" height=\"35\" align=\"right\"></td>\n          <td width=\"10%\" height=\"35\"></td>\n        </tr>\n    ");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 3302 */                                 out.write("\n        <tr>\n          <td colspan=\"2\"> ");
/*      */                                 
/* 3304 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3305 */                                 _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3306 */                                 _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_logic_005fiterate_005f5);
/*      */                                 
/* 3308 */                                 _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("camGraph");
/*      */                                 
/* 3310 */                                 _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                                 
/* 3312 */                                 _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                                 
/* 3314 */                                 _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                                 
/* 3316 */                                 _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.historydata.time.text"));
/*      */                                 
/* 3318 */                                 _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(attribute);
/*      */                                 
/* 3320 */                                 _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 3321 */                                 int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3322 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3323 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3324 */                                     out = _jspx_page_context.pushBody();
/* 3325 */                                     _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3326 */                                     _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3329 */                                     out.write("\n            ");
/* 3330 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3331 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3334 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3335 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3338 */                                 if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3339 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                 }
/*      */                                 
/* 3342 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3343 */                                 out.write(" </td>\n        </tr>\n      </table></td>\n</tr>\n<!--tr>\n<td>\n<table align=\"left\" width=\"100%\" border=0>\n</tr>\n<td colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\"><a href=\"/updateScript.do?method=enableReports&resourceid=");
/* 3344 */                                 out.print(resID);
/* 3345 */                                 out.write("&attributeid=");
/* 3346 */                                 out.print(attributeid);
/* 3347 */                                 out.write("\" class=\"staticlinks\">Enable Reports</a>\n</td>\n</tr>\n</table>\n</td>\n</tr-->\n          \n          <tr>\n          <td  align=left colspan=2 valign=\"top\">\n      <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\" >\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3348 */                                 if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_logic_005fiterate_005f5, _jspx_page_context))
/*      */                                   return;
/* 3350 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" align=\"center\"><span class=\"bodytextbold\">");
/* 3351 */                                 if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_logic_005fiterate_005f5, _jspx_page_context))
/*      */                                   return;
/* 3353 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\" align=\"center\"><span class=\"bodytextbold\">");
/* 3354 */                                 out.print(ALERTCONFIG_TEXT);
/* 3355 */                                 out.write("</span></td>\n        </tr>\n\n        <tr>\n          <td  height=\"25\" class=\"bodytext\">");
/* 3356 */                                 out.print(value);
/* 3357 */                                 out.write("</td>\n          <td width=\"16%\" align=\"center\"> <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3358 */                                 out.print(resID);
/* 3359 */                                 out.write("&attributeid=");
/* 3360 */                                 out.print(attributeid);
/* 3361 */                                 out.write("&alertconfigurl=");
/* 3362 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + attributeid + "attributeToSelect=" + attributeid + "&redirectto=" + encodeurl));
/* 3363 */                                 out.write("')\">");
/* 3364 */                                 out.print(getSeverityImage(alert.getProperty(resID + "#" + attributeid)));
/* 3365 */                                 out.write(" </a></td>\n          <td  colspan=\"4\" height=\"35\"  align=\"center\" ><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3366 */                                 out.print(resID);
/* 3367 */                                 out.write("&attributeIDs=");
/* 3368 */                                 out.print(attributeid);
/* 3369 */                                 out.write("&attributeToSelect=");
/* 3370 */                                 out.print(attributeid);
/* 3371 */                                 out.write("&redirectto=");
/* 3372 */                                 out.print(encodeurl);
/* 3373 */                                 out.write("\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" border=\"0\" style=\"width: 10px; height: 13px;\" title=\"\"></a></td>\n        </tr>\n      </table>\n          </td>\n          </tr>\n        </table>\n        \n       </td>\n\n");
/*      */                                 
/* 3375 */                                 if ((isLastBox) && (isLeftBox)) {
/* 3376 */                                   out.println("<tr><td>&nbsp;</td></tr>");
/*      */                                 }
/*      */                                 
/* 3379 */                                 if (!isLeftBox) {
/* 3380 */                                   out.println("<tr><td>&nbsp;</td></tr>");
/*      */                                 }
/*      */                                 
/* 3383 */                                 out.write("\t\n\n\n");
/* 3384 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f5.doAfterBody();
/* 3385 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3386 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/* 3387 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3390 */                               if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 3391 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3394 */                             if (_jspx_th_logic_005fiterate_005f5.doEndTag() == 5) {
/* 3395 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5); return;
/*      */                             }
/*      */                             
/* 3398 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5);
/* 3399 */                             out.write("\n</table>\n</td>\n</tr>\n</table>\n");
/*      */                           }
/* 3401 */                           out.write(10);
/* 3402 */                           out.write(10);
/* 3403 */                           out.write("<!--$Id$-->\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/* 3404 */                           String mon_health = ess_atts.getProperty("Health");
/* 3405 */                           out.write("\n<script>\n  \t     /**\n  \t      * This Script is for disabling the Rows ... DC will not happen for these rows unlike Delete\n  \t      */\n  \t     function disableRows(e,frm,objname,frmname,formid,tableid)\n  \t     {\n  \t         var resid=\"\";\n  \t         var count=0;\n  \t         if(!checkforOneSelected(frmname,objname))\n  \t         {\n  \t           alert(\"");
/* 3406 */                           out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.disable"));
/* 3407 */                           out.write("\");\n  \t           return;\n  \t         }\n  \t         else\n  \t         {\n  \t                 for(var i=0;i<document.getElementsByName(objname).length;i++)\n  \t                 {\n  \t                         if(document.getElementsByName(objname)[i].checked==true)\n  \t                         {\n  \t                                 var temp=document.getElementsByName(objname)[i].value;\n  \t                                 var test=temp.split(\",\");\n  \t                                 var res=test[0];\n  \t                                 if(resid!=\"\")\n  \t                                 {\n  \t                                 resid=resid+\",\"+res;\n  \t                                 count++;\n  \t                                 }\n  \t                                 else\n  \t                                 {\n  \t                                 resid=res;\n  \t                                 }\n  \t \n  \t                         }\n  \t \n  \t                 }\n  \t         }\n  \t         if(confirm(\"");
/* 3408 */                           out.print(FormatUtil.getString("am.webclient.scriptrow.disable.confirm"));
/* 3409 */                           out.write("\"))\n  \t         {\n                 document.getElementById(formid).action=\"/updateScript.do?method=disableRowByUser&resourceid=\"+resid+\"&tableid=\"+tableid+\"&scriptid=");
/* 3410 */                           out.print(resID);
/* 3411 */                           out.write("\";\n  \t         document.getElementById(formid).method.value='disableRowByUser';\n  \t         document.getElementById(formid).resourceid.value=resid;\n  \t         document.getElementById(formid).tableid.value=tableid;\n  \t         document.getElementById(formid).submit();\n  \t         }\n  \t         else\n  \t         {\n  \t                 return;\n  \t         }\n  \t     }\n  \t \n  \t     function enableRows(e,frm,objname,frmname,formid,tableid,table_health)\n  \t     {\n  \t         var resid=\"\";\n  \t         var count=0;\n  \t         if(!checkforOneSelected(frmname,objname))\n  \t         {\n  \t           alert(\"");
/* 3412 */                           out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.disable"));
/* 3413 */                           out.write("\");\n  \t           return;\n  \t         }\n  \t         else\n  \t         {\n  \t                 for(var i=0;i<document.getElementsByName(objname).length;i++)\n  \t                 {\n  \t                         if(document.getElementsByName(objname)[i].checked==true)\n  \t                         {\n  \t                                 var temp=document.getElementsByName(objname)[i].value;\n  \t                                 var test=temp.split(\",\");\n  \t                                 var res=test[0];\n  \t                                 if(resid!=\"\")\n  \t                                 {\n  \t                                 resid=resid+\",\"+res;\n  \t                                 count++;\n  \t                                 }\n  \t                                 else\n  \t                                 {\n  \t                                 resid=res;\n  \t                                 }\n  \t \n  \t                         }\n  \t \n  \t                 }\n  \t         }\n  \t         if(confirm(\"");
/* 3414 */                           out.print(FormatUtil.getString("am.webclient.scriptrow.enable.confirm"));
/* 3415 */                           out.write("\"))\n  \t         {\n  \t         document.getElementById(formid).action=\"/updateScript.do?method=enableRowByUser&resourceid=\"+resid+\"&tableid=\"+tableid+\"&scriptid=");
/* 3416 */                           out.print(resID);
/* 3417 */                           out.write("&table_health=\"+table_health+\"&mon_health=");
/* 3418 */                           out.print(mon_health);
/* 3419 */                           out.write("\";\n  \t         document.getElementById(formid).method.value='enableRowByUser';\n  \t         document.getElementById(formid).resourceid.value=resid;\n  \t         document.getElementById(formid).tableid.value=tableid;\n  \t         document.getElementById(formid).submit();\n  \t         }\n  \t         else\n  \t         {\n  \t                 return;\n  \t         }\n  \t     }\n  \t \n  \t </script>\n<br>\n<table width=\"100%\" border=\"0\" style=\"table-layout:fixed\">\n<tr>\n<td>\n");
/*      */                           
/* 3421 */                           IterateTag _jspx_th_logic_005fiterate_005f6 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3422 */                           _jspx_th_logic_005fiterate_005f6.setPageContext(_jspx_page_context);
/* 3423 */                           _jspx_th_logic_005fiterate_005f6.setParent(null);
/*      */                           
/* 3425 */                           _jspx_th_logic_005fiterate_005f6.setName("table_ids");
/*      */                           
/* 3427 */                           _jspx_th_logic_005fiterate_005f6.setId("attribute");
/*      */                           
/* 3429 */                           _jspx_th_logic_005fiterate_005f6.setIndexId("j");
/*      */                           
/* 3431 */                           _jspx_th_logic_005fiterate_005f6.setType("java.lang.String");
/* 3432 */                           int _jspx_eval_logic_005fiterate_005f6 = _jspx_th_logic_005fiterate_005f6.doStartTag();
/* 3433 */                           if (_jspx_eval_logic_005fiterate_005f6 != 0) {
/* 3434 */                             String attribute = null;
/* 3435 */                             Integer j = null;
/* 3436 */                             if (_jspx_eval_logic_005fiterate_005f6 != 1) {
/* 3437 */                               out = _jspx_page_context.pushBody();
/* 3438 */                               _jspx_th_logic_005fiterate_005f6.setBodyContent((BodyContent)out);
/* 3439 */                               _jspx_th_logic_005fiterate_005f6.doInitBody();
/*      */                             }
/* 3441 */                             attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3442 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                             for (;;) {
/* 3444 */                               out.write(10);
/* 3445 */                               out.write(10);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3472 */                               boolean isQMExTimeTable = false;
/* 3473 */                               ArrayList rowsDisabled = (ArrayList)table_rowsDisabled.get(attribute);
/* 3474 */                               ArrayList attids = (ArrayList)((Hashtable)table_data.get(attribute)).get("column");
/* 3475 */                               pageContext.setAttribute("attids", attids);
/* 3476 */                               id1++;
/* 3477 */                               int num_count = 0;
/* 3478 */                               Properties num_mapper = new Properties();
/* 3479 */                               for (int i = 0; i < attids.size(); i++)
/*      */                               {
/* 3481 */                                 if ((attid_details.get(attids.get(i)) != null) && (((ArrayList)attid_details.get(attids.get(i))).get(1).equals("0")))
/*      */                                 {
/* 3483 */                                   num_count++;
/* 3484 */                                   num_mapper.setProperty((String)attids.get(i), "true");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3488 */                                   num_mapper.setProperty((String)attids.get(i), "false");
/*      */                                 }
/*      */                               }
/* 3491 */                               Hashtable data = (Hashtable)((Hashtable)table_data.get(attribute)).get("data");
/*      */                               
/* 3493 */                               ArrayList rowids = (ArrayList)table_resids.get(tableids.get(j.intValue()));
/* 3494 */                               pageContext.setAttribute("rowids", rowids);
/* 3495 */                               int col_count = attids.size() + 3;
/* 3496 */                               if (num_count == 0) {}
/*      */                               
/*      */ 
/*      */ 
/* 3500 */                               float width = 98 / (attids.size() + 2);
/* 3501 */                               String attids_commas = "";
/* 3502 */                               String att_to_select = "";
/* 3503 */                               if (attids.size() > 0)
/*      */                               {
/* 3505 */                                 att_to_select = att_to_select + attids.get(0);
/*      */                               }
/* 3507 */                               String rowids_asString = "";
/* 3508 */                               String fname = "forms" + j + "_" + resID;
/* 3509 */                               String formid = "fid" + j + "_" + resID;
/* 3510 */                               String checkname = "resrows" + j;
/* 3511 */                               String checkid = "checkID" + j;
/* 3512 */                               String colavaid = (String)table_avail.get(attribute);
/* 3513 */                               if (colavaid == null)
/*      */                               {
/* 3515 */                                 colavaid = "";
/*      */                               }
/* 3517 */                               String table_healthid = (String)table_health.get(attribute);
/* 3518 */                               boolean datapresent = false;
/* 3519 */                               if (table_avail_data.get(attribute) != null)
/* 3520 */                                 datapresent = table_avail_data.get(attribute).toString().equals("YES");
/* 3521 */                               boolean setHeight = (rowids != null) && (rowids.size() > 1);
/* 3522 */                               String heightCss = setHeight ? "" : "style=height:50px;white-space:nowrap;";
/* 3523 */                               String overflowCss = setHeight ? "overflow:auto;" : "overflow-x:auto;";
/*      */                               
/* 3525 */                               out.write("\n<form name=\"");
/* 3526 */                               out.print(fname);
/* 3527 */                               out.write("\" id=\"");
/* 3528 */                               out.print(formid);
/* 3529 */                               out.write("\" method=post>\n<input type=\"hidden\" name=\"method\" value=\"deleteTableByUser\"/>\n<input type=\"hidden\" name=\"resourceid\" value=\"\"/>\n<input type=\"hidden\" name=\"tableid\" value=\"\"/>\n<input type=\"hidden\" name=\"sqlmanid\" value=\"\"/>\n<input type=\"hidden\" name=\"baseid\" id=\"baseid\" value=\"");
/* 3530 */                               if (_jspx_meth_c_005fout_005f2(_jspx_th_logic_005fiterate_005f6, _jspx_page_context))
/*      */                                 return;
/* 3532 */                               out.write("\"/>\n<input type=\"hidden\" name=\"type\" value=\"");
/* 3533 */                               out.print(resourcetype);
/* 3534 */                               out.write("\" />\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbtborder\" >\n<tr> \n            <td height=\"26\"  class=\"tableheading\" colspan=\"");
/* 3535 */                               out.print(col_count - 3);
/* 3536 */                               out.write("\"> <!--%=FormatUtil.getString(\"am.monitortab.table.text\")%--> ");
/* 3537 */                               out.print(FormatUtil.getString((String)tabid_tabname.get(tableids.get(j.intValue()))));
/* 3538 */                               out.write("</td>   \n            ");
/*      */                               
/* 3540 */                               String tabName = (String)tabid_tabname.get(tableids.get(j.intValue()));
/*      */                               
/*      */ 
/* 3543 */                               if ((resourcetype != null) && (resourcetype.equals("Script Monitor")) && (!request.isUserInRole("ENTERPRISEADMIN")))
/*      */                               {
/*      */ 
/* 3546 */                                 out.write("\n<td height=\"26\"  width=\"15%\" class=\"tableheading\" align=\"right\" colspan=\"1\"><a class=\"bodytextboldwhiteun\" href=\"javascript:deleteTablesAlert('");
/* 3547 */                                 out.print(resID);
/* 3548 */                                 out.write(39);
/* 3549 */                                 out.write(44);
/* 3550 */                                 out.write(39);
/* 3551 */                                 out.print(tableids.get(j.intValue()));
/* 3552 */                                 out.write(39);
/* 3553 */                                 out.write(44);
/* 3554 */                                 out.write(39);
/* 3555 */                                 out.print(formid);
/* 3556 */                                 out.write("',this.form)\" style=\"margin-right:10px;\">");
/* 3557 */                                 out.print(FormatUtil.getString("am.script.deletetable.text"));
/* 3558 */                                 out.write("</a></td> \n            ");
/*      */                               }
/* 3560 */                               else if ((resourcetype != null) && (resourcetype.equals("QueryMonitor")) && (!tabName.equals("Execution Time")))
/*      */                               {
/*      */ 
/* 3563 */                                 out.write("\n\n<td height=\"26\" colspan=\"2\"  width=\"25%\" class=\"tableheading\" align=\"right\" colspan=\"1\"><a class=\"bodytextboldwhiteun\" href=\"javascript:MM_openBrWindow('/jsp/queryEdit.jsp?tableId=");
/* 3564 */                                 out.print(tableids.get(j.intValue()));
/* 3565 */                                 out.write("&resourceid=");
/* 3566 */                                 out.print(resID);
/* 3567 */                                 out.write("','Personalize','width=390,height=300,screenX=100,screenY=300,scrollbars=yes')\" style=\"margin-right:10px;\">");
/* 3568 */                                 out.print(FormatUtil.getString("Primary Keys"));
/* 3569 */                                 out.write("</a></td>\n\n\n\n");
/*      */ 
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/*      */ 
/* 3577 */                                 if ((resourcetype != null) && (resourcetype.equals("QueryMonitor")) && (tabName.equals("Execution Time"))) {
/* 3578 */                                   isQMExTimeTable = true;
/*      */                                 }
/* 3580 */                                 out.write("\n                <td height=\"26\"  width=\"15%\" class=\"tableheading\" align=\"right\" colspan=\"1\"></td> \n                ");
/*      */                               }
/*      */                               
/*      */ 
/* 3584 */                               out.write("\n</tr>\n</table>\n<div id=\"");
/* 3585 */                               out.print(id1);
/* 3586 */                               out.write("\" style=\"width:99%;");
/* 3587 */                               out.print(overflowCss);
/* 3588 */                               out.write("\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\" id=\"scriptTableOutput");
/* 3589 */                               out.print(id1);
/* 3590 */                               out.write("\">\n<tr>\n\n");
/*      */                               
/* 3592 */                               if (num_count >= 0)
/*      */                               {
/*      */ 
/* 3595 */                                 out.write("\n<td height=\"26\" align=\"center\" width=\"2%\" class=\"columnheading\"><input type=\"checkbox\"  name=\"headercheckbox\" onclick=\"javascript:fnSelectAllform(this,this.form,'");
/* 3596 */                                 out.print(checkname);
/* 3597 */                                 out.write("')\" /> </td>\n");
/*      */                               }
/*      */                               
/* 3600 */                               boolean toshow = true;
/*      */                               
/* 3602 */                               out.write(10);
/*      */                               
/* 3604 */                               IterateTag _jspx_th_logic_005fiterate_005f7 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3605 */                               _jspx_th_logic_005fiterate_005f7.setPageContext(_jspx_page_context);
/* 3606 */                               _jspx_th_logic_005fiterate_005f7.setParent(_jspx_th_logic_005fiterate_005f6);
/*      */                               
/* 3608 */                               _jspx_th_logic_005fiterate_005f7.setName("attids");
/*      */                               
/* 3610 */                               _jspx_th_logic_005fiterate_005f7.setId("colnames");
/*      */                               
/* 3612 */                               _jspx_th_logic_005fiterate_005f7.setIndexId("k");
/*      */                               
/* 3614 */                               _jspx_th_logic_005fiterate_005f7.setType("java.lang.String");
/* 3615 */                               int _jspx_eval_logic_005fiterate_005f7 = _jspx_th_logic_005fiterate_005f7.doStartTag();
/* 3616 */                               if (_jspx_eval_logic_005fiterate_005f7 != 0) {
/* 3617 */                                 String colnames = null;
/* 3618 */                                 Integer k = null;
/* 3619 */                                 if (_jspx_eval_logic_005fiterate_005f7 != 1) {
/* 3620 */                                   out = _jspx_page_context.pushBody();
/* 3621 */                                   _jspx_th_logic_005fiterate_005f7.setBodyContent((BodyContent)out);
/* 3622 */                                   _jspx_th_logic_005fiterate_005f7.doInitBody();
/*      */                                 }
/* 3624 */                                 colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 3625 */                                 k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                 for (;;) {
/* 3627 */                                   out.write(10);
/* 3628 */                                   out.write(9);
/*      */                                   
/*      */ 
/* 3631 */                                   String col_name = (String)((ArrayList)attid_details.get(colnames)).get(0);
/* 3632 */                                   if ((col_name.equals("Availability")) && (!datapresent))
/*      */                                   {
/* 3634 */                                     toshow = false;
/*      */                                   }
/*      */                                   else {
/* 3637 */                                     attids_commas = attids_commas + colnames + ",";
/* 3638 */                                     toshow = true;
/*      */                                   }
/* 3640 */                                   if (toshow)
/*      */                                   {
/* 3642 */                                     if (col_name.equals("Execution Time")) {
/* 3643 */                                       col_name = col_name + " (ms) ";
/*      */                                     }
/*      */                                     
/* 3646 */                                     out.write("\n            <td height=\"26\" align=\"left\" width=\"25%\"  class=\"columnheading\"><a id=\"scriptTableOutput");
/* 3647 */                                     out.print(id1);
/* 3648 */                                     out.write("_header");
/* 3649 */                                     out.print(k);
/* 3650 */                                     out.write("\" href=\"#\" style=\"text-decoration: none; color: rgb(0, 0, 0);\" onclick=\"ts_resortTable(this,'scriptTableOutput");
/* 3651 */                                     out.print(id1);
/* 3652 */                                     out.write("',0);return false;\"><span class=\"bodytextbold\">");
/* 3653 */                                     out.print(FormatUtil.getString(col_name));
/* 3654 */                                     out.write("</span><span class=\"sortarrow\">&nbsp;</span></a></td>\n            ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3658 */                                   out.write(10);
/* 3659 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f7.doAfterBody();
/* 3660 */                                   colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 3661 */                                   k = (Integer)_jspx_page_context.findAttribute("k");
/* 3662 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3665 */                                 if (_jspx_eval_logic_005fiterate_005f7 != 1) {
/* 3666 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3669 */                               if (_jspx_th_logic_005fiterate_005f7.doEndTag() == 5) {
/* 3670 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f7); return;
/*      */                               }
/*      */                               
/* 3673 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f7);
/* 3674 */                               out.write(10);
/*      */                               
/* 3676 */                               if ((attids_commas != null) && (attids_commas.length() > 0))
/*      */                               {
/* 3678 */                                 attids_commas = attids_commas.substring(0, attids_commas.length() - 1);
/*      */                               }
/* 3680 */                               attids_commas = attids_commas + "," + table_healthid;
/* 3681 */                               String colsp = "";
/* 3682 */                               if (!toshow)
/*      */                               {
/* 3684 */                                 colsp = "2";
/*      */                               }
/*      */                               
/* 3687 */                               out.write("\n\n<td height=\"26\" align=\"center\" width=\"20%\"  class=\"columnheading\"><a id=\"scriptTableOutput");
/* 3688 */                               out.print(id1);
/* 3689 */                               out.write("_header");
/* 3690 */                               out.print(attids.size());
/* 3691 */                               out.write("\" href=\"#\" style=\"text-decoration: none; color: rgb(0, 0, 0);\" onclick=\"ts_resortTable(this,'scriptTableOutput");
/* 3692 */                               out.print(id1);
/* 3693 */                               out.write("',0);return false;\"><span class=\"bodytextbold\">");
/* 3694 */                               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3695 */                               out.write("</span><span class=\"sortarrow\">&nbsp;</span></a></td> \n\n<td height=\"26\" align=\"center\" colspan=\"");
/* 3696 */                               out.print(colsp);
/* 3697 */                               out.write("\" width=\"20%\" class=\"columnheading\" nowrap=\"nowrap\"><font style=\"margin-right:10px;\">");
/* 3698 */                               out.print(ALERTCONFIG_TEXT);
/* 3699 */                               out.write("</font></td> \n\n</tr>\n\n");
/* 3700 */                               if ((rowids != null) && (rowids.size() > 0)) {
/* 3701 */                                 out.write(10);
/* 3702 */                                 out.write(10);
/*      */                                 
/* 3704 */                                 IterateTag _jspx_th_logic_005fiterate_005f8 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3705 */                                 _jspx_th_logic_005fiterate_005f8.setPageContext(_jspx_page_context);
/* 3706 */                                 _jspx_th_logic_005fiterate_005f8.setParent(_jspx_th_logic_005fiterate_005f6);
/*      */                                 
/* 3708 */                                 _jspx_th_logic_005fiterate_005f8.setName("rowids");
/*      */                                 
/* 3710 */                                 _jspx_th_logic_005fiterate_005f8.setId("rowid");
/*      */                                 
/* 3712 */                                 _jspx_th_logic_005fiterate_005f8.setIndexId("i");
/*      */                                 
/* 3714 */                                 _jspx_th_logic_005fiterate_005f8.setType("java.lang.String");
/* 3715 */                                 int _jspx_eval_logic_005fiterate_005f8 = _jspx_th_logic_005fiterate_005f8.doStartTag();
/* 3716 */                                 if (_jspx_eval_logic_005fiterate_005f8 != 0) {
/* 3717 */                                   String rowid = null;
/* 3718 */                                   Integer i = null;
/* 3719 */                                   if (_jspx_eval_logic_005fiterate_005f8 != 1) {
/* 3720 */                                     out = _jspx_page_context.pushBody();
/* 3721 */                                     _jspx_th_logic_005fiterate_005f8.setBodyContent((BodyContent)out);
/* 3722 */                                     _jspx_th_logic_005fiterate_005f8.doInitBody();
/*      */                                   }
/* 3724 */                                   rowid = (String)_jspx_page_context.findAttribute("rowid");
/* 3725 */                                   i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                   for (;;) {
/* 3727 */                                     out.write(10);
/* 3728 */                                     out.write(10);
/*      */                                     
/* 3730 */                                     boolean isdisable = false;
/* 3731 */                                     if (rowsDisabled.contains(rowid)) {
/* 3732 */                                       isdisable = true;
/*      */                                     }
/* 3734 */                                     rowids_asString = rowids_asString + rowid + ",";
/* 3735 */                                     int temp_val = i.intValue();
/* 3736 */                                     String bgclass = "whitegrayrightalign-reports-normal";
/* 3737 */                                     String textclass = "";
/* 3738 */                                     if (isdisable) {
/* 3739 */                                       textclass = "class=\"disabledtext\"";
/*      */                                     }
/* 3741 */                                     if (temp_val % 2 == 0)
/*      */                                     {
/* 3743 */                                       bgclass = "whitegrayrightalign-reports-normal";
/*      */                                     }
/*      */                                     try {
/* 3746 */                                       if (((Hashtable)data.get(rowid) != null) || (isdisable)) {
/* 3747 */                                         out.write("\n<tr>\n");
/*      */                                         
/* 3749 */                                         if (num_count >= 0)
/*      */                                         {
/*      */ 
/* 3752 */                                           out.write("\n<td width=\"5%\" align=\"center\" class=\"");
/* 3753 */                                           out.print(bgclass);
/* 3754 */                                           out.write(34);
/* 3755 */                                           out.write(32);
/* 3756 */                                           out.print(heightCss);
/* 3757 */                                           out.write(" ><input type=\"checkbox\" name=\"");
/* 3758 */                                           out.print(checkname);
/* 3759 */                                           out.write("\" id=\"");
/* 3760 */                                           out.print(checkid);
/* 3761 */                                           out.write("\" value=\"");
/* 3762 */                                           out.print(rowid);
/* 3763 */                                           out.write("\"/> </td>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 3767 */                                         out.write(10);
/*      */                                         
/* 3769 */                                         IterateTag _jspx_th_logic_005fiterate_005f9 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3770 */                                         _jspx_th_logic_005fiterate_005f9.setPageContext(_jspx_page_context);
/* 3771 */                                         _jspx_th_logic_005fiterate_005f9.setParent(_jspx_th_logic_005fiterate_005f8);
/*      */                                         
/* 3773 */                                         _jspx_th_logic_005fiterate_005f9.setName("attids");
/*      */                                         
/* 3775 */                                         _jspx_th_logic_005fiterate_005f9.setId("colvalues");
/*      */                                         
/* 3777 */                                         _jspx_th_logic_005fiterate_005f9.setIndexId("k");
/*      */                                         
/* 3779 */                                         _jspx_th_logic_005fiterate_005f9.setType("java.lang.String");
/* 3780 */                                         int _jspx_eval_logic_005fiterate_005f9 = _jspx_th_logic_005fiterate_005f9.doStartTag();
/* 3781 */                                         if (_jspx_eval_logic_005fiterate_005f9 != 0) {
/* 3782 */                                           String colvalues = null;
/* 3783 */                                           Integer k = null;
/* 3784 */                                           if (_jspx_eval_logic_005fiterate_005f9 != 1) {
/* 3785 */                                             out = _jspx_page_context.pushBody();
/* 3786 */                                             _jspx_th_logic_005fiterate_005f9.setBodyContent((BodyContent)out);
/* 3787 */                                             _jspx_th_logic_005fiterate_005f9.doInitBody();
/*      */                                           }
/* 3789 */                                           colvalues = (String)_jspx_page_context.findAttribute("colvalues");
/* 3790 */                                           k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                           for (;;) {
/* 3792 */                                             out.write("\n    ");
/*      */                                             
/* 3794 */                                             toshow = true;
/* 3795 */                                             if ((colavaid.equals(colvalues)) && (!datapresent))
/*      */                                             {
/* 3797 */                                               toshow = false;
/*      */                                             }
/* 3799 */                                             if (!colavaid.equals(colvalues))
/*      */                                             {
/*      */ 
/* 3802 */                                               out.write("\n    \n<td width=\"26%\" align=\"left\" class=\"");
/* 3803 */                                               out.print(bgclass);
/* 3804 */                                               out.write(34);
/* 3805 */                                               out.write(32);
/* 3806 */                                               out.print(heightCss);
/* 3807 */                                               out.write(62);
/* 3808 */                                               out.write(10);
/*      */                                               
/* 3810 */                                               String data1 = "";
/*      */                                               try {
/* 3812 */                                                 data1 = (String)((Hashtable)data.get(rowid)).get(colvalues);
/* 3813 */                                                 data1 = StrUtil.findReplace(data1, "<", "&lt;");
/* 3814 */                                                 data1 = StrUtil.findReplace(data1, ">", "&gt;");
/*      */                                               }
/*      */                                               catch (Exception ex) {
/* 3817 */                                                 ex.printStackTrace();
/*      */                                               }
/* 3819 */                                               if ((data1 == null) || (data1.equals("")))
/*      */                                               {
/* 3821 */                                                 data1 = "NA";
/*      */                                               }
/*      */                                               
/* 3824 */                                               if ((num_mapper.getProperty(colvalues) != null) && (num_mapper.getProperty(colvalues).equals("true")))
/*      */                                               {
/*      */ 
/*      */ 
/* 3828 */                                                 out.write("\n<a href=\"javascript:showGraphForthis('");
/* 3829 */                                                 out.print(colvalues);
/* 3830 */                                                 out.write(39);
/* 3831 */                                                 out.write(44);
/* 3832 */                                                 out.write(39);
/* 3833 */                                                 out.print(rowid);
/* 3834 */                                                 out.write(39);
/* 3835 */                                                 out.write(44);
/* 3836 */                                                 out.write(39);
/* 3837 */                                                 out.print(((ArrayList)attid_details.get(colvalues)).get(0));
/* 3838 */                                                 out.write(39);
/* 3839 */                                                 out.write(44);
/* 3840 */                                                 out.write(39);
/* 3841 */                                                 out.print(isQMExTimeTable);
/* 3842 */                                                 out.write("')\" class=\"staticlinks\">\n<span ");
/* 3843 */                                                 out.print(textclass);
/* 3844 */                                                 out.write(62);
/* 3845 */                                                 out.write(10);
/* 3846 */                                                 out.print(data1);
/* 3847 */                                                 out.write("</span></a>\n");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 3852 */                                                 out.write("\n<span ");
/* 3853 */                                                 out.print(textclass);
/* 3854 */                                                 out.write(62);
/* 3855 */                                                 out.write(10);
/* 3856 */                                                 out.print(data1);
/* 3857 */                                                 out.write("</span></a>\n");
/*      */                                               }
/*      */                                               
/*      */ 
/* 3861 */                                               out.write("\n </td>\n ");
/*      */ 
/*      */                                             }
/* 3864 */                                             else if (datapresent)
/*      */                                             {
/*      */ 
/* 3867 */                                               out.write("\n <td width=\"15%\" align=\"center\" class=\"");
/* 3868 */                                               out.print(bgclass);
/* 3869 */                                               out.write(34);
/* 3870 */                                               out.write(32);
/* 3871 */                                               out.print(heightCss);
/* 3872 */                                               out.write(" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3873 */                                               out.print(rowid);
/* 3874 */                                               out.write("&attributeid=");
/* 3875 */                                               out.print(colvalues);
/* 3876 */                                               out.write("&alertconfigurl=");
/* 3877 */                                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + rowid + "&attributeIDs=" + availabilitykeys.get(resourcetype) + "&attributeToSelect=" + availabilitykeys.get(resourcetype) + "&redirectto=" + encodeurl));
/* 3878 */                                               out.write("')\"> \n            ");
/* 3879 */                                               out.print(getSeverityImageForAvailability(alert.getProperty(rowid + "#" + colvalues)));
/* 3880 */                                               out.write(" \n            </a>\n </td>\n ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 3884 */                                             out.write(10);
/* 3885 */                                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f9.doAfterBody();
/* 3886 */                                             colvalues = (String)_jspx_page_context.findAttribute("colvalues");
/* 3887 */                                             k = (Integer)_jspx_page_context.findAttribute("k");
/* 3888 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3891 */                                           if (_jspx_eval_logic_005fiterate_005f9 != 1) {
/* 3892 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3895 */                                         if (_jspx_th_logic_005fiterate_005f9.doEndTag() == 5) {
/* 3896 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f9); return;
/*      */                                         }
/*      */                                         
/* 3899 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f9);
/* 3900 */                                         out.write("\n<td width=\"20%\"  align=\"center\" height=\"25\" align=\"left\" class=\"");
/* 3901 */                                         out.print(bgclass);
/* 3902 */                                         out.write(34);
/* 3903 */                                         out.write(32);
/* 3904 */                                         out.print(heightCss);
/* 3905 */                                         out.write(" ><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3906 */                                         out.print(rowid);
/* 3907 */                                         out.write("&attributeid=");
/* 3908 */                                         out.print(table_health.get(attribute));
/* 3909 */                                         out.write("&alertconfigurl=");
/* 3910 */                                         out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + rowid + "&attributeIDs=" + availabilitykeys.get(resourcetype) + "&attributeToSelect=" + availabilitykeys.get(resourcetype) + "&redirectto=" + encodeurl));
/* 3911 */                                         out.write("')\"> \n            ");
/* 3912 */                                         out.print(getSeverityImageForHealth(alert.getProperty(rowid + "#" + table_health.get(attribute))));
/* 3913 */                                         out.write("<span style=\"display:none\">");
/* 3914 */                                         out.print(alert.getProperty(rowid + "#" + table_health.get(attribute)));
/* 3915 */                                         out.write("</span> \n            </a></td>\n<td width=\"20%\" colspan=\"2\" align=\"center\" class=\"");
/* 3916 */                                         out.print(bgclass);
/* 3917 */                                         out.write(34);
/* 3918 */                                         out.write(32);
/* 3919 */                                         out.print(heightCss);
/* 3920 */                                         out.write(" ><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3921 */                                         out.print(rowid);
/* 3922 */                                         out.write("&attributeIDs=");
/* 3923 */                                         out.print(attids_commas);
/* 3924 */                                         out.write("&attributeToSelect=");
/* 3925 */                                         out.print(att_to_select);
/* 3926 */                                         out.write("&redirectto=");
/* 3927 */                                         out.print(encodeurl);
/* 3928 */                                         out.write("' class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" hspace=\"4\" border=\"0\" align=\"absmiddle\"></a></td>\n</tr>\n");
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Exception ex) {
/* 3932 */                                       ex.printStackTrace();
/*      */                                     }
/*      */                                     
/* 3935 */                                     out.write(10);
/* 3936 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f8.doAfterBody();
/* 3937 */                                     rowid = (String)_jspx_page_context.findAttribute("rowid");
/* 3938 */                                     i = (Integer)_jspx_page_context.findAttribute("i");
/* 3939 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3942 */                                   if (_jspx_eval_logic_005fiterate_005f8 != 1) {
/* 3943 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3946 */                                 if (_jspx_th_logic_005fiterate_005f8.doEndTag() == 5) {
/* 3947 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f8); return;
/*      */                                 }
/*      */                                 
/* 3950 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f8);
/* 3951 */                                 out.write(10);
/*      */                               } else {
/* 3953 */                                 out.write("\n\n<tr>\n<td class=\"whitegrayrightalign-reports-normal\" colspan=\"");
/* 3954 */                                 out.print(col_count - 3);
/* 3955 */                                 out.write("\" align=\"center\" style=\"line-height: 45px;border: none;\">\n");
/* 3956 */                                 out.print(FormatUtil.getString("No_Data_Available"));
/* 3957 */                                 out.write("\n</td>\n</tr>\n\n");
/*      */                               }
/* 3959 */                               out.write("\n</table>\n</div>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n<tr > \n<td colspan=\"3\" width=\"90%\" height=\"31\" align=\"left\" class=\"tablebottom\">\n<!-- scripttable disable starts-->\n<a class=\"staticlinks\" href=\"javascript:disableRows(this,'");
/* 3960 */                               out.print(fname);
/* 3961 */                               out.write(39);
/* 3962 */                               out.write(44);
/* 3963 */                               out.write(39);
/* 3964 */                               out.print(checkname);
/* 3965 */                               out.write(39);
/* 3966 */                               out.write(44);
/* 3967 */                               out.print(fname);
/* 3968 */                               out.write(44);
/* 3969 */                               out.write(39);
/* 3970 */                               out.print(formid);
/* 3971 */                               out.write(39);
/* 3972 */                               out.write(44);
/* 3973 */                               out.write(39);
/* 3974 */                               out.print(attribute);
/* 3975 */                               out.write("')\">");
/* 3976 */                               out.print(FormatUtil.getString("Disable"));
/* 3977 */                               out.write("</a>&nbsp;<span class=\"bodytext\">|&nbsp;<a class=\"staticlinks\" href=\"javascript:enableRows(this,'");
/* 3978 */                               out.print(fname);
/* 3979 */                               out.write(39);
/* 3980 */                               out.write(44);
/* 3981 */                               out.write(39);
/* 3982 */                               out.print(checkname);
/* 3983 */                               out.write(39);
/* 3984 */                               out.write(44);
/* 3985 */                               out.print(fname);
/* 3986 */                               out.write(44);
/* 3987 */                               out.write(39);
/* 3988 */                               out.print(formid);
/* 3989 */                               out.write(39);
/* 3990 */                               out.write(44);
/* 3991 */                               out.write(39);
/* 3992 */                               out.print(attribute);
/* 3993 */                               out.write(39);
/* 3994 */                               out.write(44);
/* 3995 */                               out.write(39);
/* 3996 */                               out.print(table_healthid);
/* 3997 */                               out.write("')\">");
/* 3998 */                               out.print(FormatUtil.getString("Enable"));
/* 3999 */                               out.write("</a>&nbsp;<span class=\"bodytext\">|&nbsp;<a name=\"");
/* 4000 */                               out.print(attribute);
/* 4001 */                               out.write("\" class=\"staticlinks\" href=\"#");
/* 4002 */                               out.print(attribute);
/* 4003 */                               out.write("\" onClick=\"javascript:deleteRows(this,'");
/* 4004 */                               out.print(fname);
/* 4005 */                               out.write(39);
/* 4006 */                               out.write(44);
/* 4007 */                               out.write(39);
/* 4008 */                               out.print(checkname);
/* 4009 */                               out.write(39);
/* 4010 */                               out.write(44);
/* 4011 */                               out.print(fname);
/* 4012 */                               out.write(44);
/* 4013 */                               out.write(39);
/* 4014 */                               out.print(formid);
/* 4015 */                               out.write("')\">");
/* 4016 */                               out.print(FormatUtil.getString("am.webclient.cam.delete.link"));
/* 4017 */                               out.write("</a>&nbsp;<span class=\"bodytext\">|&nbsp;\n<!-- scripttable disable ends-->\n");
/*      */                               
/* 4019 */                               if (num_count > 0)
/*      */                               {
/*      */ 
/* 4022 */                                 out.write("\n<a href=\"javascript:showGraph(this,'");
/* 4023 */                                 out.print(fname);
/* 4024 */                                 out.write(39);
/* 4025 */                                 out.write(44);
/* 4026 */                                 out.write(39);
/* 4027 */                                 out.print(checkname);
/* 4028 */                                 out.write(39);
/* 4029 */                                 out.write(44);
/* 4030 */                                 out.write(39);
/* 4031 */                                 out.print(formid);
/* 4032 */                                 out.write(39);
/* 4033 */                                 out.write(44);
/* 4034 */                                 out.write(39);
/* 4035 */                                 out.print(isQMExTimeTable);
/* 4036 */                                 out.write("')\" class=\"staticlinks\">");
/* 4037 */                                 out.print(FormatUtil.getString("am.webclient.report.compare.text"));
/* 4038 */                                 out.write("</a> ");
/*      */                                 
/* 4040 */                                 if (num_count > 0)
/*      */                                 {
/*      */ 
/* 4043 */                                   out.write("\n    <select name=\"attList\" class=\"formtext\" onchange=\"javascript:showGraph(this,'");
/* 4044 */                                   out.print(fname);
/* 4045 */                                   out.write(39);
/* 4046 */                                   out.write(44);
/* 4047 */                                   out.write(39);
/* 4048 */                                   out.print(checkname);
/* 4049 */                                   out.write(39);
/* 4050 */                                   out.write(44);
/* 4051 */                                   out.write(39);
/* 4052 */                                   out.print(formid);
/* 4053 */                                   out.write(39);
/* 4054 */                                   out.write(44);
/* 4055 */                                   out.write(39);
/* 4056 */                                   out.print(isQMExTimeTable);
/* 4057 */                                   out.write("')\">\n    ");
/*      */                                   
/* 4059 */                                   IterateTag _jspx_th_logic_005fiterate_005f10 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4060 */                                   _jspx_th_logic_005fiterate_005f10.setPageContext(_jspx_page_context);
/* 4061 */                                   _jspx_th_logic_005fiterate_005f10.setParent(_jspx_th_logic_005fiterate_005f6);
/*      */                                   
/* 4063 */                                   _jspx_th_logic_005fiterate_005f10.setName("attids");
/*      */                                   
/* 4065 */                                   _jspx_th_logic_005fiterate_005f10.setId("colnames");
/*      */                                   
/* 4067 */                                   _jspx_th_logic_005fiterate_005f10.setIndexId("k");
/*      */                                   
/* 4069 */                                   _jspx_th_logic_005fiterate_005f10.setType("java.lang.String");
/* 4070 */                                   int _jspx_eval_logic_005fiterate_005f10 = _jspx_th_logic_005fiterate_005f10.doStartTag();
/* 4071 */                                   if (_jspx_eval_logic_005fiterate_005f10 != 0) {
/* 4072 */                                     String colnames = null;
/* 4073 */                                     Integer k = null;
/* 4074 */                                     if (_jspx_eval_logic_005fiterate_005f10 != 1) {
/* 4075 */                                       out = _jspx_page_context.pushBody();
/* 4076 */                                       _jspx_th_logic_005fiterate_005f10.setBodyContent((BodyContent)out);
/* 4077 */                                       _jspx_th_logic_005fiterate_005f10.doInitBody();
/*      */                                     }
/* 4079 */                                     colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 4080 */                                     k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                     for (;;) {
/* 4082 */                                       out.write(10);
/* 4083 */                                       out.write(9);
/*      */                                       
/* 4085 */                                       if ((attid_details.get(colnames) != null) && (((ArrayList)attid_details.get(colnames)).get(1).equals("0")))
/*      */                                       {
/*      */ 
/* 4088 */                                         out.write("\n    \n    <option value=\"");
/* 4089 */                                         out.print(colnames);
/* 4090 */                                         out.write(34);
/* 4091 */                                         out.write(62);
/* 4092 */                                         out.print(FormatUtil.getString((String)((ArrayList)attid_details.get(colnames)).get(0)));
/* 4093 */                                         out.write("</option>\n");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4097 */                                       out.write("\n    ");
/* 4098 */                                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f10.doAfterBody();
/* 4099 */                                       colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 4100 */                                       k = (Integer)_jspx_page_context.findAttribute("k");
/* 4101 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 4104 */                                     if (_jspx_eval_logic_005fiterate_005f10 != 1) {
/* 4105 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 4108 */                                   if (_jspx_th_logic_005fiterate_005f10.doEndTag() == 5) {
/* 4109 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f10); return;
/*      */                                   }
/*      */                                   
/* 4112 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f10);
/* 4113 */                                   out.write("\n</select>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4117 */                                 out.write(32);
/* 4118 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */ 
/* 4122 */                               out.write("\n</span> </td> \n    <td colspan=");
/* 4123 */                               out.print(col_count - 1);
/* 4124 */                               out.write(" width=\"10%\" height=\"31\" align=\"right\" class=\"tablebottom\"><a href=\"#top\" class=\"staticlinks\" style=\"margin-right:10px;\">");
/* 4125 */                               out.print(FormatUtil.getString("am.webclient.common.top.text"));
/* 4126 */                               out.write("</a></td>\n  </tr>\n</table>\n<br>\n");
/*      */                               
/* 4128 */                               if ((rowids_asString != null) && (rowids_asString.length() > 0))
/*      */                               {
/* 4130 */                                 rowids_asString = rowids_asString.substring(0, rowids_asString.length() - 1);
/*      */                               }
/*      */                               
/* 4133 */                               out.write("\n</form>\n<br>\n");
/* 4134 */                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f6.doAfterBody();
/* 4135 */                               attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4136 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 4137 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 4140 */                             if (_jspx_eval_logic_005fiterate_005f6 != 1) {
/* 4141 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 4144 */                           if (_jspx_th_logic_005fiterate_005f6.doEndTag() == 5) {
/* 4145 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f6);
/*      */                           }
/*      */                           else {
/* 4148 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f6);
/* 4149 */                             out.write("\n</td>\n</tr>\n\n</table>\n");
/* 4150 */                             out.write(10);
/* 4151 */                             out.write(10);
/*      */                           }
/* 4153 */                         } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4154 */         out = _jspx_out;
/* 4155 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4156 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 4157 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4160 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4166 */     PageContext pageContext = _jspx_page_context;
/* 4167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4169 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4170 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4171 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 4173 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 4174 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4175 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4176 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4177 */       return true;
/*      */     }
/* 4179 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4185 */     PageContext pageContext = _jspx_page_context;
/* 4186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4188 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4189 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4190 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 4192 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 4193 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4194 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4195 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4196 */       return true;
/*      */     }
/* 4198 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4199 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4204 */     PageContext pageContext = _jspx_page_context;
/* 4205 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4207 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4208 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4209 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 4210 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4211 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 4212 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4213 */         out = _jspx_page_context.pushBody();
/* 4214 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 4215 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4218 */         out.write("Attribute");
/* 4219 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 4220 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4223 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4224 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4227 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4228 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4229 */       return true;
/*      */     }
/* 4231 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4237 */     PageContext pageContext = _jspx_page_context;
/* 4238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4240 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4241 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4242 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/* 4243 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4244 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 4245 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4246 */         out = _jspx_page_context.pushBody();
/* 4247 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 4248 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4251 */         out.write("Status");
/* 4252 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 4253 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4256 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4257 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4260 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4261 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4262 */       return true;
/*      */     }
/* 4264 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4265 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4270 */     PageContext pageContext = _jspx_page_context;
/* 4271 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4273 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4274 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4275 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 4277 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.reports}");
/* 4278 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4279 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4281 */         out.write("\n<script>\njavascript:showDiv('reports');\n</script>\n");
/* 4282 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4283 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4287 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4288 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4289 */       return true;
/*      */     }
/* 4291 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4297 */     PageContext pageContext = _jspx_page_context;
/* 4298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4300 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4301 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4302 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/* 4303 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4304 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 4305 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4306 */         out = _jspx_page_context.pushBody();
/* 4307 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 4308 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4311 */         out.write("table.heading.attribute");
/* 4312 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 4313 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4316 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4317 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4320 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4321 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4322 */       return true;
/*      */     }
/* 4324 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4330 */     PageContext pageContext = _jspx_page_context;
/* 4331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4333 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4334 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4335 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/* 4336 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4337 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 4338 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4339 */         out = _jspx_page_context.pushBody();
/* 4340 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 4341 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4344 */         out.write("table.heading.value");
/* 4345 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 4346 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4349 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4350 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4353 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4354 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4355 */       return true;
/*      */     }
/* 4357 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4363 */     PageContext pageContext = _jspx_page_context;
/* 4364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4366 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4367 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 4368 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/* 4369 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 4370 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 4371 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 4372 */         out = _jspx_page_context.pushBody();
/* 4373 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 4374 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4377 */         out.write("table.heading.status");
/* 4378 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 4379 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4382 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 4383 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4386 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 4387 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4388 */       return true;
/*      */     }
/* 4390 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4391 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4396 */     PageContext pageContext = _jspx_page_context;
/* 4397 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4399 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4400 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 4401 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/* 4402 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 4403 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 4404 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 4405 */         out = _jspx_page_context.pushBody();
/* 4406 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 4407 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4410 */         out.write("Name");
/* 4411 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 4412 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4415 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 4416 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4419 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 4420 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 4421 */       return true;
/*      */     }
/* 4423 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 4424 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4429 */     PageContext pageContext = _jspx_page_context;
/* 4430 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4432 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4433 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 4434 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/* 4435 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 4436 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 4437 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 4438 */         out = _jspx_page_context.pushBody();
/* 4439 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 4440 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4443 */         out.write("table.heading.value");
/* 4444 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 4445 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4448 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 4449 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4452 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 4453 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4454 */       return true;
/*      */     }
/* 4456 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4457 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4462 */     PageContext pageContext = _jspx_page_context;
/* 4463 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4465 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4466 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 4467 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/* 4468 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 4469 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 4470 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 4471 */         out = _jspx_page_context.pushBody();
/* 4472 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 4473 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4476 */         out.write("table.heading.status");
/* 4477 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 4478 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4481 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 4482 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4485 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 4486 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4487 */       return true;
/*      */     }
/* 4489 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4495 */     PageContext pageContext = _jspx_page_context;
/* 4496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4498 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4499 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 4500 */     _jspx_th_fmt_005fmessage_005f8.setParent(null);
/* 4501 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 4502 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 4503 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 4504 */         out = _jspx_page_context.pushBody();
/* 4505 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 4506 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4509 */         out.write("Name");
/* 4510 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 4511 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4514 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 4515 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4518 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 4519 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4520 */       return true;
/*      */     }
/* 4522 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4523 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4528 */     PageContext pageContext = _jspx_page_context;
/* 4529 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4531 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4532 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 4533 */     _jspx_th_fmt_005fmessage_005f9.setParent(null);
/* 4534 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 4535 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 4536 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 4537 */         out = _jspx_page_context.pushBody();
/* 4538 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 4539 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4542 */         out.write("table.heading.value");
/* 4543 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 4544 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4547 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 4548 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4551 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 4552 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 4553 */       return true;
/*      */     }
/* 4555 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 4556 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4561 */     PageContext pageContext = _jspx_page_context;
/* 4562 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4564 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4565 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 4566 */     _jspx_th_fmt_005fmessage_005f10.setParent(null);
/* 4567 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 4568 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 4569 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 4570 */         out = _jspx_page_context.pushBody();
/* 4571 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 4572 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4575 */         out.write("table.heading.status");
/* 4576 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 4577 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4580 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 4581 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4584 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 4585 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 4586 */       return true;
/*      */     }
/* 4588 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 4589 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_logic_005fiterate_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4594 */     PageContext pageContext = _jspx_page_context;
/* 4595 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4597 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4598 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 4599 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_logic_005fiterate_005f5);
/* 4600 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 4601 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 4602 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 4603 */         out = _jspx_page_context.pushBody();
/* 4604 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 4605 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4608 */         out.write("table.heading.value");
/* 4609 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 4610 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4613 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 4614 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4617 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 4618 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4619 */       return true;
/*      */     }
/* 4621 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_logic_005fiterate_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4627 */     PageContext pageContext = _jspx_page_context;
/* 4628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4630 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4631 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 4632 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_logic_005fiterate_005f5);
/* 4633 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 4634 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 4635 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 4636 */         out = _jspx_page_context.pushBody();
/* 4637 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 4638 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4641 */         out.write("table.heading.status");
/* 4642 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 4643 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4646 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 4647 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4650 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 4651 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 4652 */       return true;
/*      */     }
/* 4654 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 4655 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_logic_005fiterate_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4660 */     PageContext pageContext = _jspx_page_context;
/* 4661 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4663 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4664 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4665 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_logic_005fiterate_005f6);
/*      */     
/* 4667 */     _jspx_th_c_005fout_005f2.setValue("${baseid}");
/* 4668 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4669 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4670 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4671 */       return true;
/*      */     }
/* 4673 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4674 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostScript_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */