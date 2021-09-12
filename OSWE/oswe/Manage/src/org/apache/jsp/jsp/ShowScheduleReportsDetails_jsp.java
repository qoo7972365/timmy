/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.EnterpriseAdminLink;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
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
/*      */ public final class ShowScheduleReportsDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  670 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
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
/*  842 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/* 1383 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1428 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 2187 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2189 */     _jspx_dependants.put("/jsp/includes/EnterpriseAdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2211 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2215 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2230 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2234 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2237 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2244 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2246 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2254 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2257 */     JspWriter out = null;
/* 2258 */     Object page = this;
/* 2259 */     JspWriter _jspx_out = null;
/* 2260 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2264 */       response.setContentType("text/html;charset=UTF-8");
/* 2265 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2267 */       _jspx_page_context = pageContext;
/* 2268 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2269 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2270 */       session = pageContext.getSession();
/* 2271 */       out = pageContext.getOut();
/* 2272 */       _jspx_out = out;
/*      */       
/* 2274 */       out.write("<!DOCTYPE html>\n");
/* 2275 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2276 */       out.write(10);
/*      */       
/* 2278 */       request.setAttribute("HelpKey", "Schedule Reports");
/*      */       
/* 2280 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2281 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2283 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2285 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2287 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2289 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2291 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2293 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2294 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2295 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2296 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2299 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2300 */         String available = null;
/* 2301 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2302 */         out.write(10);
/*      */         
/* 2304 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2306 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2308 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2310 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2312 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2314 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2315 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2316 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2317 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2320 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2321 */           String unavailable = null;
/* 2322 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2323 */           out.write(10);
/*      */           
/* 2325 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2327 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2329 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2331 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2333 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2335 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2336 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2337 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2338 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2341 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2342 */             String unmanaged = null;
/* 2343 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2344 */             out.write(10);
/*      */             
/* 2346 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2348 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2350 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2352 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2354 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2356 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2357 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2358 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2359 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2362 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2363 */               String scheduled = null;
/* 2364 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2365 */               out.write(10);
/*      */               
/* 2367 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2369 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2371 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2373 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2375 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2377 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2378 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2379 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2380 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2383 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2384 */                 String critical = null;
/* 2385 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2386 */                 out.write(10);
/*      */                 
/* 2388 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2390 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2392 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2394 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2396 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2398 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2399 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2400 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2401 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2404 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2405 */                   String clear = null;
/* 2406 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2407 */                   out.write(10);
/*      */                   
/* 2409 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2411 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2413 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2415 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2417 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2419 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2420 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2421 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2422 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2425 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2426 */                     String warning = null;
/* 2427 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2428 */                     out.write(10);
/* 2429 */                     out.write(10);
/*      */                     
/* 2431 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2432 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2434 */                     out.write(10);
/* 2435 */                     out.write(10);
/* 2436 */                     out.write(10);
/* 2437 */                     out.write("\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2438 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2440 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<script>\n\n function getEnable(a)\n{ \n\t\t");
/* 2441 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2443 */                     out.write("\n    url=\"/scheduleReports.do?method=sendEnableDetails&id=\"+a;\n    http.open(\"POST\",url,true);\n    http.onreadystatechange = getValueForEnable;\n    http.send(null);\n }\n function getValueForEnable()\n{ \n    if(http.readyState == 4)\n    {\n      var result = http.responseText;\n      var id=result;\n      var stringtokens=id.split(\",\");\n      svalue=stringtokens[0].trim(); //No I18N\n      sid=stringtokens[1].trim(); //No I18N\n      if(svalue=='enable')\n      {\n        document.getElementById(sid).src='/images/icon_tickmark.gif';\n\tdocument.getElementById(sid).title = '");
/* 2444 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.enable.text"));
/* 2445 */                     out.write("';\n      }\n      else\n      {\n        document.getElementById(sid).src='/images/cross.gif';\n\tdocument.getElementById(sid).title = '");
/* 2446 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.disable.text"));
/* 2447 */                     out.write("';\n      }\n        \n     }  \n }\n\nfunction fnSelectAll(e,name)\n{\n    ToggleAll(e,document.AMActionForm,name)\n}\n\n\nfunction removeScheduler()\n{\n\n ");
/* 2448 */                     if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                       return;
/* 2450 */                     out.write(10);
/* 2451 */                     out.write(32);
/*      */                     
/* 2453 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2454 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2455 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/* 2457 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2458 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2459 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2461 */                         out.write(" \n if(!checkforOneSelected(document.AMActionForm,\"scheduleids\"))\n\t{\n\t\talert('");
/* 2462 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.jsalertfordelete.text"));
/* 2463 */                         out.write("');\n\t\treturn;\n\t}\n\t\n\tif(confirm('");
/* 2464 */                         out.print(FormatUtil.getString("am.webclient.schedulereports.delete.alert.text"));
/* 2465 */                         out.write("'))\n\t{\n\tdocument.AMActionForm.method.value=\"removeScheduler\";\n\tdocument.AMActionForm.submit();\n\t\n\t}\n ");
/* 2466 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2467 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2471 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2472 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     }
/*      */                     else {
/* 2475 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2476 */                       out.write("\n\n}\n</script>\n\n");
/*      */                       
/* 2478 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2479 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2480 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2482 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2483 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2484 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2486 */                           out.write(10);
/* 2487 */                           out.write(9);
/* 2488 */                           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2490 */                           out.write(9);
/* 2491 */                           out.write(10);
/* 2492 */                           out.write(9);
/* 2493 */                           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2495 */                           out.write("\t\t\n\t");
/*      */                           
/* 2497 */                           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2498 */                           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2499 */                           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2501 */                           _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                           
/* 2503 */                           _jspx_th_tiles_005fput_005f2.setType("string");
/* 2504 */                           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2505 */                           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2506 */                             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2507 */                               out = _jspx_page_context.pushBody();
/* 2508 */                               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2509 */                               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2512 */                               out.write("\t\t\n\t");
/*      */                               
/* 2514 */                               if (EnterpriseUtil.isAdminServer())
/*      */                               {
/*      */ 
/* 2517 */                                 out.write("\t\t\n\t   ");
/* 2518 */                                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                                 
/*      */ 
/* 2521 */                                 String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                                 
/* 2523 */                                 out.write("\n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n <tr>\n\t     <td height=\"21\"  class=\"leftlinksheading\">");
/* 2524 */                                 out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 2525 */                                 out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 2527 */                                 ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2528 */                                 _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2529 */                                 _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/* 2530 */                                 int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2531 */                                 if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                   for (;;) {
/* 2533 */                                     out.write(10);
/*      */                                     
/* 2535 */                                     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2536 */                                     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2537 */                                     _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                     
/* 2539 */                                     _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 2540 */                                     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2541 */                                     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                       for (;;) {
/* 2543 */                                         out.write("\n        ");
/* 2544 */                                         out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 2545 */                                         out.write(10);
/* 2546 */                                         out.write(32);
/* 2547 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2548 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2552 */                                     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2553 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                     }
/*      */                                     
/* 2556 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2557 */                                     out.write(10);
/* 2558 */                                     out.write(32);
/*      */                                     
/* 2560 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2561 */                                     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2562 */                                     _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2563 */                                     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2564 */                                     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                       for (;;) {
/* 2566 */                                         out.write("\n     ");
/*      */                                         
/* 2568 */                                         EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f0 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 2569 */                                         _jspx_th_ea_005feeadminlink_005f0.setPageContext(_jspx_page_context);
/* 2570 */                                         _jspx_th_ea_005feeadminlink_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                                         
/* 2572 */                                         _jspx_th_ea_005feeadminlink_005f0.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*      */                                         
/* 2574 */                                         _jspx_th_ea_005feeadminlink_005f0.setEnableClass("new-left-links");
/* 2575 */                                         int _jspx_eval_ea_005feeadminlink_005f0 = _jspx_th_ea_005feeadminlink_005f0.doStartTag();
/* 2576 */                                         if (_jspx_eval_ea_005feeadminlink_005f0 != 0) {
/* 2577 */                                           if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/* 2578 */                                             out = _jspx_page_context.pushBody();
/* 2579 */                                             _jspx_th_ea_005feeadminlink_005f0.setBodyContent((BodyContent)out);
/* 2580 */                                             _jspx_th_ea_005feeadminlink_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 2583 */                                             out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 2584 */                                             int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f0.doAfterBody();
/* 2585 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 2588 */                                           if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/* 2589 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 2592 */                                         if (_jspx_th_ea_005feeadminlink_005f0.doEndTag() == 5) {
/* 2593 */                                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0); return;
/*      */                                         }
/*      */                                         
/* 2596 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0);
/* 2597 */                                         out.write(10);
/* 2598 */                                         out.write(32);
/* 2599 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2600 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2604 */                                     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2605 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                     }
/*      */                                     
/* 2608 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2609 */                                     out.write(10);
/* 2610 */                                     out.write(32);
/* 2611 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2612 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2616 */                                 if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2617 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                 }
/*      */                                 
/* 2620 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2621 */                                 out.write("\n    </td>\n</tr>\n\n<tr>\n\n<tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 2623 */                                 ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2624 */                                 _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2625 */                                 _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/* 2626 */                                 int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2627 */                                 if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                   for (;;) {
/* 2629 */                                     out.write(10);
/*      */                                     
/* 2631 */                                     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2632 */                                     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2633 */                                     _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                     
/* 2635 */                                     _jspx_th_c_005fwhen_005f1.setTest("${param.method!='showMailServerConfiguration'}");
/* 2636 */                                     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2637 */                                     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                       for (;;) {
/* 2639 */                                         out.write("\n    ");
/*      */                                         
/* 2641 */                                         EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f1 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 2642 */                                         _jspx_th_ea_005feeadminlink_005f1.setPageContext(_jspx_page_context);
/* 2643 */                                         _jspx_th_ea_005feeadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                                         
/* 2645 */                                         _jspx_th_ea_005feeadminlink_005f1.setHref("/adminAction.do?method=showMailServerConfiguration");
/*      */                                         
/* 2647 */                                         _jspx_th_ea_005feeadminlink_005f1.setEnableClass("new-left-links");
/* 2648 */                                         int _jspx_eval_ea_005feeadminlink_005f1 = _jspx_th_ea_005feeadminlink_005f1.doStartTag();
/* 2649 */                                         if (_jspx_eval_ea_005feeadminlink_005f1 != 0) {
/* 2650 */                                           if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/* 2651 */                                             out = _jspx_page_context.pushBody();
/* 2652 */                                             _jspx_th_ea_005feeadminlink_005f1.setBodyContent((BodyContent)out);
/* 2653 */                                             _jspx_th_ea_005feeadminlink_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 2656 */                                             out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 2657 */                                             int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f1.doAfterBody();
/* 2658 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 2661 */                                           if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/* 2662 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 2665 */                                         if (_jspx_th_ea_005feeadminlink_005f1.doEndTag() == 5) {
/* 2666 */                                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1); return;
/*      */                                         }
/*      */                                         
/* 2669 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1);
/* 2670 */                                         out.write(10);
/* 2671 */                                         out.write(32);
/* 2672 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2673 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2677 */                                     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2678 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                     }
/*      */                                     
/* 2681 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2682 */                                     out.write(10);
/* 2683 */                                     out.write(32);
/*      */                                     
/* 2685 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2686 */                                     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2687 */                                     _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2688 */                                     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2689 */                                     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                       for (;;) {
/* 2691 */                                         out.write(10);
/* 2692 */                                         out.write(9);
/* 2693 */                                         out.write(32);
/* 2694 */                                         out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 2695 */                                         out.write(10);
/* 2696 */                                         out.write(32);
/* 2697 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2698 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2702 */                                     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2703 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                     }
/*      */                                     
/* 2706 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2707 */                                     out.write(10);
/* 2708 */                                     out.write(32);
/* 2709 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2710 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2714 */                                 if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2715 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                 }
/*      */                                 
/* 2718 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2719 */                                 out.write("\n    </td>\n</tr>\n\n");
/* 2720 */                                 if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 2721 */                                   out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                                   
/* 2723 */                                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2724 */                                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2725 */                                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/* 2726 */                                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2727 */                                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                     for (;;) {
/* 2729 */                                       out.write(10);
/*      */                                       
/* 2731 */                                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2732 */                                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2733 */                                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                       
/* 2735 */                                       _jspx_th_c_005fwhen_005f2.setTest("${param.method!='SMSServerConfiguration'}");
/* 2736 */                                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2737 */                                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                         for (;;) {
/* 2739 */                                           out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 2740 */                                           out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2741 */                                           out.write("\n    </a>\n ");
/* 2742 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2743 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2747 */                                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2748 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                       }
/*      */                                       
/* 2751 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2752 */                                       out.write(10);
/* 2753 */                                       out.write(32);
/*      */                                       
/* 2755 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2756 */                                       _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2757 */                                       _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2758 */                                       int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2759 */                                       if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                         for (;;) {
/* 2761 */                                           out.write("\n         ");
/* 2762 */                                           out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2763 */                                           out.write(10);
/* 2764 */                                           out.write(32);
/* 2765 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2766 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2770 */                                       if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2771 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                       }
/*      */                                       
/* 2774 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2775 */                                       out.write(10);
/* 2776 */                                       out.write(32);
/* 2777 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2778 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2782 */                                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2783 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                   }
/*      */                                   
/* 2786 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2787 */                                   out.write("\n    </td>\n</tr>\n");
/*      */                                 }
/* 2789 */                                 out.write("\n\n\n<tr>\n\n    <td class=\"leftlinkstd\">\n");
/*      */                                 
/* 2791 */                                 ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2792 */                                 _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2793 */                                 _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/* 2794 */                                 int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2795 */                                 if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                   for (;;) {
/* 2797 */                                     out.write(10);
/*      */                                     
/* 2799 */                                     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2800 */                                     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2801 */                                     _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                     
/* 2803 */                                     _jspx_th_c_005fwhen_005f3.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 2804 */                                     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2805 */                                     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                       for (;;) {
/* 2807 */                                         out.write("\n    ");
/*      */                                         
/* 2809 */                                         EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f2 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 2810 */                                         _jspx_th_ea_005feeadminlink_005f2.setPageContext(_jspx_page_context);
/* 2811 */                                         _jspx_th_ea_005feeadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                                         
/* 2813 */                                         _jspx_th_ea_005feeadminlink_005f2.setHref("/jsp/ProxyConfiguration.jsp");
/*      */                                         
/* 2815 */                                         _jspx_th_ea_005feeadminlink_005f2.setEnableClass("new-left-links");
/* 2816 */                                         int _jspx_eval_ea_005feeadminlink_005f2 = _jspx_th_ea_005feeadminlink_005f2.doStartTag();
/* 2817 */                                         if (_jspx_eval_ea_005feeadminlink_005f2 != 0) {
/* 2818 */                                           if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/* 2819 */                                             out = _jspx_page_context.pushBody();
/* 2820 */                                             _jspx_th_ea_005feeadminlink_005f2.setBodyContent((BodyContent)out);
/* 2821 */                                             _jspx_th_ea_005feeadminlink_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 2824 */                                             out.write("\n    ");
/* 2825 */                                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2826 */                                             out.write("\n    ");
/* 2827 */                                             int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f2.doAfterBody();
/* 2828 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 2831 */                                           if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/* 2832 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 2835 */                                         if (_jspx_th_ea_005feeadminlink_005f2.doEndTag() == 5) {
/* 2836 */                                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2); return;
/*      */                                         }
/*      */                                         
/* 2839 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2);
/* 2840 */                                         out.write(10);
/* 2841 */                                         out.write(32);
/* 2842 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2843 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2847 */                                     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2848 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                     }
/*      */                                     
/* 2851 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2852 */                                     out.write(10);
/* 2853 */                                     out.write(32);
/*      */                                     
/* 2855 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2856 */                                     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2857 */                                     _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 2858 */                                     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2859 */                                     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                       for (;;) {
/* 2861 */                                         out.write(10);
/* 2862 */                                         out.write(9);
/* 2863 */                                         out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2864 */                                         out.write(10);
/* 2865 */                                         out.write(32);
/* 2866 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2867 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2871 */                                     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2872 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                     }
/*      */                                     
/* 2875 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2876 */                                     out.write(10);
/* 2877 */                                     out.write(32);
/* 2878 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2879 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2883 */                                 if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2884 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                 }
/*      */                                 
/* 2887 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2888 */                                 out.write("\n    </td>\n</tr>\n<tr>\n\n<td class=\"leftlinkstd\">\n");
/*      */                                 
/* 2890 */                                 ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2891 */                                 _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2892 */                                 _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/* 2893 */                                 int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2894 */                                 if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                   for (;;) {
/* 2896 */                                     out.write(10);
/*      */                                     
/* 2898 */                                     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2899 */                                     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2900 */                                     _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                     
/* 2902 */                                     _jspx_th_c_005fwhen_005f4.setTest("${uri !='/admin/userconfiguration.do'}");
/* 2903 */                                     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2904 */                                     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                       for (;;) {
/* 2906 */                                         out.write("\n\n        ");
/*      */                                         
/* 2908 */                                         EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f3 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 2909 */                                         _jspx_th_ea_005feeadminlink_005f3.setPageContext(_jspx_page_context);
/* 2910 */                                         _jspx_th_ea_005feeadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                                         
/* 2912 */                                         _jspx_th_ea_005feeadminlink_005f3.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                         
/* 2914 */                                         _jspx_th_ea_005feeadminlink_005f3.setEnableClass("new-left-links");
/* 2915 */                                         int _jspx_eval_ea_005feeadminlink_005f3 = _jspx_th_ea_005feeadminlink_005f3.doStartTag();
/* 2916 */                                         if (_jspx_eval_ea_005feeadminlink_005f3 != 0) {
/* 2917 */                                           if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/* 2918 */                                             out = _jspx_page_context.pushBody();
/* 2919 */                                             _jspx_th_ea_005feeadminlink_005f3.setBodyContent((BodyContent)out);
/* 2920 */                                             _jspx_th_ea_005feeadminlink_005f3.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 2923 */                                             out.write("\n       ");
/* 2924 */                                             out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2925 */                                             out.write("\n        ");
/* 2926 */                                             int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f3.doAfterBody();
/* 2927 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 2930 */                                           if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/* 2931 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 2934 */                                         if (_jspx_th_ea_005feeadminlink_005f3.doEndTag() == 5) {
/* 2935 */                                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3); return;
/*      */                                         }
/*      */                                         
/* 2938 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3);
/* 2939 */                                         out.write(10);
/* 2940 */                                         out.write(10);
/* 2941 */                                         out.write(32);
/* 2942 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2943 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2947 */                                     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2948 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                     }
/*      */                                     
/* 2951 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2952 */                                     out.write(10);
/* 2953 */                                     out.write(32);
/*      */                                     
/* 2955 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2956 */                                     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2957 */                                     _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 2958 */                                     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2959 */                                     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                       for (;;) {
/* 2961 */                                         out.write(10);
/* 2962 */                                         out.write(9);
/* 2963 */                                         out.write(32);
/* 2964 */                                         out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2965 */                                         out.write(10);
/* 2966 */                                         out.write(32);
/* 2967 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2968 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2972 */                                     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2973 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                     }
/*      */                                     
/* 2976 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2977 */                                     out.write(10);
/* 2978 */                                     out.write(32);
/* 2979 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2980 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2984 */                                 if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2985 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                                 }
/*      */                                 
/* 2988 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2989 */                                 out.write("\n</td>\n</tr>\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                                 
/* 2991 */                                 ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2992 */                                 _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2993 */                                 _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/* 2994 */                                 int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2995 */                                 if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                                   for (;;) {
/* 2997 */                                     out.write("\n   ");
/*      */                                     
/* 2999 */                                     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3000 */                                     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 3001 */                                     _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                     
/* 3003 */                                     _jspx_th_c_005fwhen_005f5.setTest("${param.method!='showManagedServers'}");
/* 3004 */                                     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 3005 */                                     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                       for (;;) {
/* 3007 */                                         out.write("\n    ");
/*      */                                         
/* 3009 */                                         EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f4 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 3010 */                                         _jspx_th_ea_005feeadminlink_005f4.setPageContext(_jspx_page_context);
/* 3011 */                                         _jspx_th_ea_005feeadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                                         
/* 3013 */                                         _jspx_th_ea_005feeadminlink_005f4.setHref("/adminAction.do?method=showManagedServers");
/*      */                                         
/* 3015 */                                         _jspx_th_ea_005feeadminlink_005f4.setEnableClass("new-left-links");
/* 3016 */                                         int _jspx_eval_ea_005feeadminlink_005f4 = _jspx_th_ea_005feeadminlink_005f4.doStartTag();
/* 3017 */                                         if (_jspx_eval_ea_005feeadminlink_005f4 != 0) {
/* 3018 */                                           if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/* 3019 */                                             out = _jspx_page_context.pushBody();
/* 3020 */                                             _jspx_th_ea_005feeadminlink_005f4.setBodyContent((BodyContent)out);
/* 3021 */                                             _jspx_th_ea_005feeadminlink_005f4.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3024 */                                             out.write("\n     ");
/* 3025 */                                             out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 3026 */                                             out.write(10);
/* 3027 */                                             out.write(9);
/* 3028 */                                             int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f4.doAfterBody();
/* 3029 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3032 */                                           if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/* 3033 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3036 */                                         if (_jspx_th_ea_005feeadminlink_005f4.doEndTag() == 5) {
/* 3037 */                                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4); return;
/*      */                                         }
/*      */                                         
/* 3040 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4);
/* 3041 */                                         out.write("\n   ");
/* 3042 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 3043 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3047 */                                     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 3048 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                     }
/*      */                                     
/* 3051 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 3052 */                                     out.write("\n   ");
/*      */                                     
/* 3054 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3055 */                                     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3056 */                                     _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 3057 */                                     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3058 */                                     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                       for (;;) {
/* 3060 */                                         out.write("\n     ");
/* 3061 */                                         out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 3062 */                                         out.write("\n   ");
/* 3063 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3064 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3068 */                                     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3069 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                                     }
/*      */                                     
/* 3072 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3073 */                                     out.write("\n   ");
/* 3074 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3075 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3079 */                                 if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3080 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                                 }
/*      */                                 
/* 3083 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3084 */                                 out.write("\n  </td>\n</tr>\n\n\n<td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 3086 */                                 ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3087 */                                 _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 3088 */                                 _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/* 3089 */                                 int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 3090 */                                 if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                                   for (;;) {
/* 3092 */                                     out.write(10);
/*      */                                     
/* 3094 */                                     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3095 */                                     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3096 */                                     _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                                     
/* 3098 */                                     _jspx_th_c_005fwhen_005f6.setTest("${param.server!='admin'}");
/* 3099 */                                     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3100 */                                     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                                       for (;;) {
/* 3102 */                                         out.write(10);
/*      */                                         
/* 3104 */                                         EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f5 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 3105 */                                         _jspx_th_ea_005feeadminlink_005f5.setPageContext(_jspx_page_context);
/* 3106 */                                         _jspx_th_ea_005feeadminlink_005f5.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                                         
/* 3108 */                                         _jspx_th_ea_005feeadminlink_005f5.setHref("/adminAction.do?method=showActionProfiles");
/*      */                                         
/* 3110 */                                         _jspx_th_ea_005feeadminlink_005f5.setEnableClass("new-left-links");
/* 3111 */                                         int _jspx_eval_ea_005feeadminlink_005f5 = _jspx_th_ea_005feeadminlink_005f5.doStartTag();
/* 3112 */                                         if (_jspx_eval_ea_005feeadminlink_005f5 != 0) {
/* 3113 */                                           if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/* 3114 */                                             out = _jspx_page_context.pushBody();
/* 3115 */                                             _jspx_th_ea_005feeadminlink_005f5.setBodyContent((BodyContent)out);
/* 3116 */                                             _jspx_th_ea_005feeadminlink_005f5.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3119 */                                             out.write(10);
/* 3120 */                                             out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 3121 */                                             out.write(10);
/* 3122 */                                             int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f5.doAfterBody();
/* 3123 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3126 */                                           if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/* 3127 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3130 */                                         if (_jspx_th_ea_005feeadminlink_005f5.doEndTag() == 5) {
/* 3131 */                                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5); return;
/*      */                                         }
/*      */                                         
/* 3134 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5);
/* 3135 */                                         out.write(10);
/* 3136 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3137 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3141 */                                     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3142 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                                     }
/*      */                                     
/* 3145 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3146 */                                     out.write(10);
/*      */                                     
/* 3148 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3149 */                                     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3150 */                                     _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 3151 */                                     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3152 */                                     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                       for (;;) {
/* 3154 */                                         out.write(10);
/* 3155 */                                         out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 3156 */                                         out.write(10);
/* 3157 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3158 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3162 */                                     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3163 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                                     }
/*      */                                     
/* 3166 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3167 */                                     out.write(10);
/* 3168 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3169 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3173 */                                 if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3174 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                                 }
/*      */                                 
/* 3177 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3178 */                                 out.write("\n</td>\n</tr>\n");
/*      */                                 
/* 3180 */                                 if ((!usertype.equals("F")) || (com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                 {
/* 3182 */                                   out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                                   
/* 3184 */                                   ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3185 */                                   _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 3186 */                                   _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/* 3187 */                                   int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 3188 */                                   if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                                     for (;;) {
/* 3190 */                                       out.write(10);
/* 3191 */                                       out.write(32);
/* 3192 */                                       out.write(32);
/*      */                                       
/* 3194 */                                       WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3195 */                                       _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3196 */                                       _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                                       
/* 3198 */                                       _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/* 3199 */                                       int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3200 */                                       if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                         for (;;) {
/* 3202 */                                           out.write(10);
/* 3203 */                                           out.write(32);
/* 3204 */                                           out.write(32);
/* 3205 */                                           out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3206 */                                           out.write(10);
/* 3207 */                                           out.write(32);
/* 3208 */                                           out.write(32);
/* 3209 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3210 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3214 */                                       if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3215 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                                       }
/*      */                                       
/* 3218 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3219 */                                       out.write(10);
/* 3220 */                                       out.write(32);
/*      */                                       
/* 3222 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3223 */                                       _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 3224 */                                       _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 3225 */                                       int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 3226 */                                       if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                                         for (;;) {
/* 3228 */                                           out.write("\n   ");
/*      */                                           
/* 3230 */                                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f6 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 3231 */                                           _jspx_th_ea_005feeadminlink_005f6.setPageContext(_jspx_page_context);
/* 3232 */                                           _jspx_th_ea_005feeadminlink_005f6.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                                           
/* 3234 */                                           _jspx_th_ea_005feeadminlink_005f6.setHref("/scheduleReports.do?method=showScheduleReports");
/*      */                                           
/* 3236 */                                           _jspx_th_ea_005feeadminlink_005f6.setEnableClass("new-left-links");
/* 3237 */                                           int _jspx_eval_ea_005feeadminlink_005f6 = _jspx_th_ea_005feeadminlink_005f6.doStartTag();
/* 3238 */                                           if (_jspx_eval_ea_005feeadminlink_005f6 != 0) {
/* 3239 */                                             if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 3240 */                                               out = _jspx_page_context.pushBody();
/* 3241 */                                               _jspx_th_ea_005feeadminlink_005f6.setBodyContent((BodyContent)out);
/* 3242 */                                               _jspx_th_ea_005feeadminlink_005f6.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3245 */                                               out.write("\n   ");
/* 3246 */                                               out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3247 */                                               out.write(10);
/* 3248 */                                               out.write(32);
/* 3249 */                                               out.write(32);
/* 3250 */                                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f6.doAfterBody();
/* 3251 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3254 */                                             if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 3255 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3258 */                                           if (_jspx_th_ea_005feeadminlink_005f6.doEndTag() == 5) {
/* 3259 */                                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6); return;
/*      */                                           }
/*      */                                           
/* 3262 */                                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6);
/* 3263 */                                           out.write(10);
/* 3264 */                                           out.write(32);
/* 3265 */                                           out.write(32);
/* 3266 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3267 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3271 */                                       if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3272 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                                       }
/*      */                                       
/* 3275 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3276 */                                       out.write(10);
/* 3277 */                                       out.write(32);
/* 3278 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 3279 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3283 */                                   if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 3284 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                                   }
/*      */                                   
/* 3287 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3288 */                                   out.write("\n</td>\n</tr>\n");
/*      */                                 } else {
/* 3290 */                                   out.write("\n<tr>\n    <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 3291 */                                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3292 */                                   out.write("\n    </td>\n</tr>\n");
/*      */                                 }
/* 3294 */                                 out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                                 
/* 3296 */                                 ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3297 */                                 _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 3298 */                                 _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/* 3299 */                                 int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 3300 */                                 if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                                   for (;;) {
/* 3302 */                                     out.write(10);
/* 3303 */                                     out.write(32);
/* 3304 */                                     out.write(32);
/*      */                                     
/* 3306 */                                     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3307 */                                     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3308 */                                     _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                                     
/* 3310 */                                     _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showDataCleanUp'}");
/* 3311 */                                     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3312 */                                     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                       for (;;) {
/* 3314 */                                         out.write(10);
/* 3315 */                                         out.write(32);
/* 3316 */                                         out.write(32);
/* 3317 */                                         out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3318 */                                         out.write(10);
/* 3319 */                                         out.write(32);
/* 3320 */                                         out.write(32);
/* 3321 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3322 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3326 */                                     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3327 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                     }
/*      */                                     
/* 3330 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3331 */                                     out.write(10);
/* 3332 */                                     out.write(32);
/*      */                                     
/* 3334 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3335 */                                     _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 3336 */                                     _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 3337 */                                     int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 3338 */                                     if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                                       for (;;) {
/* 3340 */                                         out.write("\n   ");
/*      */                                         
/* 3342 */                                         EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f7 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 3343 */                                         _jspx_th_ea_005feeadminlink_005f7.setPageContext(_jspx_page_context);
/* 3344 */                                         _jspx_th_ea_005feeadminlink_005f7.setParent(_jspx_th_c_005fotherwise_005f8);
/*      */                                         
/* 3346 */                                         _jspx_th_ea_005feeadminlink_005f7.setHref("/adminAction.do?method=showDataCleanUp");
/*      */                                         
/* 3348 */                                         _jspx_th_ea_005feeadminlink_005f7.setEnableClass("new-left-links");
/* 3349 */                                         int _jspx_eval_ea_005feeadminlink_005f7 = _jspx_th_ea_005feeadminlink_005f7.doStartTag();
/* 3350 */                                         if (_jspx_eval_ea_005feeadminlink_005f7 != 0) {
/* 3351 */                                           if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 3352 */                                             out = _jspx_page_context.pushBody();
/* 3353 */                                             _jspx_th_ea_005feeadminlink_005f7.setBodyContent((BodyContent)out);
/* 3354 */                                             _jspx_th_ea_005feeadminlink_005f7.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3357 */                                             out.write("\n   ");
/* 3358 */                                             out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3359 */                                             out.write(10);
/* 3360 */                                             out.write(32);
/* 3361 */                                             out.write(32);
/* 3362 */                                             int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f7.doAfterBody();
/* 3363 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3366 */                                           if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 3367 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3370 */                                         if (_jspx_th_ea_005feeadminlink_005f7.doEndTag() == 5) {
/* 3371 */                                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7); return;
/*      */                                         }
/*      */                                         
/* 3374 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7);
/* 3375 */                                         out.write(10);
/* 3376 */                                         out.write(32);
/* 3377 */                                         out.write(32);
/* 3378 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 3379 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3383 */                                     if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 3384 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                                     }
/*      */                                     
/* 3387 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 3388 */                                     out.write(10);
/* 3389 */                                     out.write(32);
/* 3390 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 3391 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3395 */                                 if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 3396 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                                 }
/*      */                                 
/* 3399 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 3400 */                                 out.write("\n</td>\n</tr>\n</table>\n\n");
/* 3401 */                                 out.write("\t\t\n\t");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3407 */                                 out.write("       \t\t\n\t  ");
/* 3408 */                                 out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                                 
/*      */ 
/* 3411 */                                 String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                                 
/* 3413 */                                 out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 3414 */                                 out.print(FormatUtil.getString("wizard.disabled"));
/* 3415 */                                 out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 3416 */                                 out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 3417 */                                 out.write("</td>\n  </tr>\n  \n ");
/*      */                                 
/* 3419 */                                 if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                 {
/*      */ 
/* 3422 */                                   out.write("  \n  <tr>\n\n  ");
/*      */                                   
/* 3424 */                                   if (request.getParameter("wiz") != null)
/*      */                                   {
/* 3426 */                                     out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 3427 */                                     out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 3428 */                                     out.write("\" class='disabledlink'>");
/* 3429 */                                     out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 3430 */                                     out.write("</a></td>\n  ");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3434 */                                     out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                                     
/* 3436 */                                     ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3437 */                                     _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 3438 */                                     _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 3439 */                                     int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 3440 */                                     if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                                       for (;;) {
/* 3442 */                                         out.write(10);
/*      */                                         
/* 3444 */                                         WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3445 */                                         _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 3446 */                                         _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                                         
/* 3448 */                                         _jspx_th_c_005fwhen_005f9.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 3449 */                                         int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 3450 */                                         if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                           for (;;) {
/* 3452 */                                             out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 3453 */                                             out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 3454 */                                             out.write("\n    </a>\n ");
/* 3455 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 3456 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3460 */                                         if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 3461 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                                         }
/*      */                                         
/* 3464 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3465 */                                         out.write(10);
/* 3466 */                                         out.write(32);
/*      */                                         
/* 3468 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3469 */                                         _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 3470 */                                         _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 3471 */                                         int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 3472 */                                         if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                                           for (;;) {
/* 3474 */                                             out.write(10);
/* 3475 */                                             out.write(9);
/* 3476 */                                             out.write(32);
/* 3477 */                                             out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 3478 */                                             out.write(10);
/* 3479 */                                             out.write(32);
/* 3480 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 3481 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3485 */                                         if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 3486 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                                         }
/*      */                                         
/* 3489 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 3490 */                                         out.write(10);
/* 3491 */                                         out.write(32);
/* 3492 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 3493 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3497 */                                     if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 3498 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                                     }
/*      */                                     
/* 3501 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 3502 */                                     out.write("\n    </td>\n\t");
/*      */                                   }
/* 3504 */                                   out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                                   
/* 3506 */                                   if (request.getParameter("wiz") != null)
/*      */                                   {
/* 3508 */                                     out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 3509 */                                     out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 3510 */                                     out.write("\" class='disabledlink'>");
/* 3511 */                                     out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 3512 */                                     out.write("</a></td>\n   ");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3516 */                                     out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                                     
/* 3518 */                                     ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3519 */                                     _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 3520 */                                     _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 3521 */                                     int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 3522 */                                     if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                                       for (;;) {
/* 3524 */                                         out.write(10);
/*      */                                         
/* 3526 */                                         WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3527 */                                         _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 3528 */                                         _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                                         
/* 3530 */                                         _jspx_th_c_005fwhen_005f10.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 3531 */                                         int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 3532 */                                         if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                           for (;;) {
/* 3534 */                                             out.write("\n   ");
/* 3535 */                                             out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 3536 */                                             out.write(10);
/* 3537 */                                             out.write(32);
/* 3538 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 3539 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3543 */                                         if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 3544 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                                         }
/*      */                                         
/* 3547 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3548 */                                         out.write(10);
/* 3549 */                                         out.write(32);
/*      */                                         
/* 3551 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3552 */                                         _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 3553 */                                         _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 3554 */                                         int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 3555 */                                         if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                                           for (;;) {
/* 3557 */                                             out.write(10);
/* 3558 */                                             String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 3559 */                                             out.write("\n\t \n <a href=\"");
/* 3560 */                                             out.print(link);
/* 3561 */                                             out.write("\" class=\"new-left-links\">\n               ");
/* 3562 */                                             out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 3563 */                                             out.write("\n    </a>    \n ");
/* 3564 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 3565 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3569 */                                         if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 3570 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                                         }
/*      */                                         
/* 3573 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 3574 */                                         out.write(10);
/* 3575 */                                         out.write(32);
/* 3576 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 3577 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3581 */                                     if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 3582 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                                     }
/*      */                                     
/* 3585 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 3586 */                                     out.write("\n</td>\n");
/*      */                                   }
/* 3588 */                                   out.write("\n</tr>\n\n ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3592 */                                 out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 3594 */                                 ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3595 */                                 _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 3596 */                                 _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 3597 */                                 int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 3598 */                                 if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                                   for (;;) {
/* 3600 */                                     out.write(10);
/*      */                                     
/* 3602 */                                     WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3603 */                                     _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 3604 */                                     _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                                     
/* 3606 */                                     _jspx_th_c_005fwhen_005f11.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 3607 */                                     int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 3608 */                                     if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                                       for (;;) {
/* 3610 */                                         out.write("\n    \n       ");
/* 3611 */                                         out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 3612 */                                         out.write(10);
/* 3613 */                                         out.write(32);
/* 3614 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 3615 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3619 */                                     if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 3620 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                                     }
/*      */                                     
/* 3623 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 3624 */                                     out.write(10);
/* 3625 */                                     out.write(32);
/*      */                                     
/* 3627 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3628 */                                     _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 3629 */                                     _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 3630 */                                     int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 3631 */                                     if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                                       for (;;) {
/* 3633 */                                         out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 3634 */                                         out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 3635 */                                         out.write("\n    </a>\n ");
/* 3636 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 3637 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3641 */                                     if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 3642 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                                     }
/*      */                                     
/* 3645 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 3646 */                                     out.write(10);
/* 3647 */                                     out.write(32);
/* 3648 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 3649 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3653 */                                 if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 3654 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                                 }
/*      */                                 
/* 3657 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 3658 */                                 out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 3660 */                                 ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3661 */                                 _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 3662 */                                 _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 3663 */                                 int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 3664 */                                 if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                                   for (;;) {
/* 3666 */                                     out.write(10);
/*      */                                     
/* 3668 */                                     WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3669 */                                     _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 3670 */                                     _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                                     
/* 3672 */                                     _jspx_th_c_005fwhen_005f12.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 3673 */                                     int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 3674 */                                     if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                                       for (;;) {
/* 3676 */                                         out.write("\n    \n       ");
/* 3677 */                                         out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 3678 */                                         out.write(10);
/* 3679 */                                         out.write(32);
/* 3680 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 3681 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3685 */                                     if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 3686 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                                     }
/*      */                                     
/* 3689 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3690 */                                     out.write(10);
/* 3691 */                                     out.write(32);
/*      */                                     
/* 3693 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3694 */                                     _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 3695 */                                     _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 3696 */                                     int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 3697 */                                     if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                                       for (;;) {
/* 3699 */                                         out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 3700 */                                         out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 3701 */                                         out.write("\n\t </a>\n ");
/* 3702 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 3703 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3707 */                                     if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 3708 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                                     }
/*      */                                     
/* 3711 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 3712 */                                     out.write(10);
/* 3713 */                                     out.write(32);
/* 3714 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 3715 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3719 */                                 if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 3720 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                                 }
/*      */                                 
/* 3723 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 3724 */                                 out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                                 
/* 3726 */                                 if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                 {
/*      */ 
/* 3729 */                                   out.write(32);
/* 3730 */                                   out.write(32);
/* 3731 */                                   out.write(10);
/*      */                                   
/* 3733 */                                   ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3734 */                                   _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 3735 */                                   _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 3736 */                                   int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 3737 */                                   if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                                     for (;;) {
/* 3739 */                                       out.write(10);
/*      */                                       
/* 3741 */                                       WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3742 */                                       _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 3743 */                                       _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                                       
/* 3745 */                                       _jspx_th_c_005fwhen_005f13.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 3746 */                                       int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 3747 */                                       if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                                         for (;;) {
/* 3749 */                                           out.write("\n<tr>\n    ");
/* 3750 */                                           if (!request.isUserInRole("OPERATOR")) {
/* 3751 */                                             out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 3752 */                                             out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3753 */                                             out.write("\n    </a>\n        </td>\n     ");
/*      */                                           } else {
/* 3755 */                                             out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 3756 */                                             out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3757 */                                             out.write("\n\t</a>\n\t </td>\n\t");
/*      */                                           }
/* 3759 */                                           out.write("\n    </tr>\n ");
/* 3760 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 3761 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3765 */                                       if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 3766 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                                       }
/*      */                                       
/* 3769 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 3770 */                                       out.write(10);
/* 3771 */                                       out.write(32);
/*      */                                       
/* 3773 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3774 */                                       _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 3775 */                                       _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 3776 */                                       int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 3777 */                                       if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                                         for (;;) {
/* 3779 */                                           out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 3780 */                                           out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3781 */                                           out.write("\n\t </td>\n ");
/* 3782 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 3783 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3787 */                                       if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 3788 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                                       }
/*      */                                       
/* 3791 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 3792 */                                       out.write(10);
/* 3793 */                                       out.write(32);
/* 3794 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 3795 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3799 */                                   if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 3800 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                                   }
/*      */                                   
/* 3803 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 3804 */                                   out.write("\n \n  ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3808 */                                 out.write("  \n \n ");
/*      */                                 
/* 3810 */                                 if (!usertype.equals("F"))
/*      */                                 {
/* 3812 */                                   out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                                   
/* 3814 */                                   ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3815 */                                   _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 3816 */                                   _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 3817 */                                   int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 3818 */                                   if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                                     for (;;) {
/* 3820 */                                       out.write(10);
/* 3821 */                                       out.write(9);
/*      */                                       
/* 3823 */                                       WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3824 */                                       _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 3825 */                                       _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                                       
/* 3827 */                                       _jspx_th_c_005fwhen_005f14.setTest("${param.method !='maintenanceTaskListView'}");
/* 3828 */                                       int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 3829 */                                       if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                                         for (;;) {
/* 3831 */                                           out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3832 */                                           out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3833 */                                           out.write("</a>\n  \t");
/* 3834 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 3835 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3839 */                                       if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 3840 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                                       }
/*      */                                       
/* 3843 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 3844 */                                       out.write("\n  \t");
/*      */                                       
/* 3846 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3847 */                                       _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 3848 */                                       _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 3849 */                                       int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 3850 */                                       if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                                         for (;;) {
/* 3852 */                                           out.write("\n \t\t");
/* 3853 */                                           out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3854 */                                           out.write("\n  \t");
/* 3855 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 3856 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3860 */                                       if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 3861 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                                       }
/*      */                                       
/* 3864 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 3865 */                                       out.write("\n  \t");
/* 3866 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 3867 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3871 */                                   if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 3872 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                                   }
/*      */                                   
/* 3875 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 3876 */                                   out.write("\n     </td>\n </tr>   \n \n ");
/*      */                                   
/* 3878 */                                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                   {
/*      */ 
/* 3881 */                                     out.write(32);
/* 3882 */                                     out.write(32);
/* 3883 */                                     out.write(10);
/*      */                                     
/* 3885 */                                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3886 */                                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3887 */                                     _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                     
/* 3889 */                                     _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/* 3890 */                                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3891 */                                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                       for (;;) {
/* 3893 */                                         out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                                         
/* 3895 */                                         ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3896 */                                         _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 3897 */                                         _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_c_005fif_005f0);
/* 3898 */                                         int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 3899 */                                         if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                                           for (;;) {
/* 3901 */                                             out.write(10);
/* 3902 */                                             out.write(32);
/* 3903 */                                             out.write(9);
/*      */                                             
/* 3905 */                                             WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3906 */                                             _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 3907 */                                             _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                                             
/* 3909 */                                             _jspx_th_c_005fwhen_005f15.setTest("${param.method !='listTrapListener'}");
/* 3910 */                                             int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 3911 */                                             if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                               for (;;) {
/* 3913 */                                                 out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 3914 */                                                 out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3915 */                                                 out.write("</a>\n   \t");
/* 3916 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 3917 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3921 */                                             if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 3922 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                                             }
/*      */                                             
/* 3925 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 3926 */                                             out.write("\n   \t");
/*      */                                             
/* 3928 */                                             OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3929 */                                             _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 3930 */                                             _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 3931 */                                             int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 3932 */                                             if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                               for (;;) {
/* 3934 */                                                 out.write("\n  \t\t");
/* 3935 */                                                 out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3936 */                                                 out.write(" \n   \t");
/* 3937 */                                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 3938 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3942 */                                             if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 3943 */                                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                                             }
/*      */                                             
/* 3946 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 3947 */                                             out.write("\n   \t");
/* 3948 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 3949 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3953 */                                         if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 3954 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                                         }
/*      */                                         
/* 3957 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 3958 */                                         out.write("\n      </td>\n  </tr>   \n");
/* 3959 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3960 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3964 */                                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3965 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                     }
/*      */                                     
/* 3968 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3969 */                                     out.write(10);
/* 3970 */                                     out.write(32);
/*      */                                   }
/*      */                                   
/*      */ 
/* 3974 */                                   out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                   
/* 3976 */                                   ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3977 */                                   _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 3978 */                                   _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_tiles_005fput_005f2);
/* 3979 */                                   int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 3980 */                                   if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                                     for (;;) {
/* 3982 */                                       out.write(10);
/*      */                                       
/* 3984 */                                       WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3985 */                                       _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 3986 */                                       _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */                                       
/* 3988 */                                       _jspx_th_c_005fwhen_005f16.setTest("${param.method =='showScheduleReports'}");
/* 3989 */                                       int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 3990 */                                       if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                                         for (;;) {
/* 3992 */                                           out.write("\n       ");
/* 3993 */                                           out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3994 */                                           out.write(10);
/* 3995 */                                           out.write(32);
/* 3996 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 3997 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4001 */                                       if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 4002 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */                                       }
/*      */                                       
/* 4005 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 4006 */                                       out.write(10);
/* 4007 */                                       out.write(32);
/*      */                                       
/* 4009 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4010 */                                       _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 4011 */                                       _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/* 4012 */                                       int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 4013 */                                       if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                                         for (;;) {
/* 4015 */                                           out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 4016 */                                           out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 4017 */                                           out.write("\n\t </a>\n ");
/* 4018 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 4019 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4023 */                                       if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 4024 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */                                       }
/*      */                                       
/* 4027 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 4028 */                                       out.write(10);
/* 4029 */                                       out.write(32);
/* 4030 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 4031 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4035 */                                   if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 4036 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */                                   }
/*      */                                   
/* 4039 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 4040 */                                   out.write("\n    </td>\n</tr> \n");
/*      */                                 } else {
/* 4042 */                                   out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 4043 */                                   out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 4044 */                                   out.write("</a>\n     </td>\n </tr>   \n");
/*      */                                   
/* 4046 */                                   IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4047 */                                   _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4048 */                                   _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 4050 */                                   _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 4051 */                                   int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4052 */                                   if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                     for (;;) {
/* 4054 */                                       out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 4055 */                                       out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 4056 */                                       out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 4057 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4058 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4062 */                                   if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4063 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                   }
/*      */                                   
/* 4066 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4067 */                                   out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 4068 */                                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 4069 */                                   out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                                 }
/* 4071 */                                 out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 4073 */                                 ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4074 */                                 _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 4075 */                                 _jspx_th_c_005fchoose_005f17.setParent(_jspx_th_tiles_005fput_005f2);
/* 4076 */                                 int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 4077 */                                 if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */                                   for (;;) {
/* 4079 */                                     out.write(10);
/*      */                                     
/* 4081 */                                     WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4082 */                                     _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 4083 */                                     _jspx_th_c_005fwhen_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/*      */                                     
/* 4085 */                                     _jspx_th_c_005fwhen_005f17.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 4086 */                                     int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 4087 */                                     if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */                                       for (;;) {
/* 4089 */                                         out.write("\n        ");
/* 4090 */                                         out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 4091 */                                         out.write(10);
/* 4092 */                                         out.write(32);
/* 4093 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 4094 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4098 */                                     if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 4099 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17); return;
/*      */                                     }
/*      */                                     
/* 4102 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 4103 */                                     out.write(10);
/* 4104 */                                     out.write(32);
/*      */                                     
/* 4106 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4107 */                                     _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 4108 */                                     _jspx_th_c_005fotherwise_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/* 4109 */                                     int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 4110 */                                     if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */                                       for (;;) {
/* 4112 */                                         out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 4113 */                                         out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 4114 */                                         out.write("\n\t </a>\n ");
/* 4115 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 4116 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4120 */                                     if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 4121 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17); return;
/*      */                                     }
/*      */                                     
/* 4124 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 4125 */                                     out.write(10);
/* 4126 */                                     out.write(32);
/* 4127 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 4128 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4132 */                                 if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 4133 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17); return;
/*      */                                 }
/*      */                                 
/* 4136 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 4137 */                                 out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 4139 */                                 ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4140 */                                 _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 4141 */                                 _jspx_th_c_005fchoose_005f18.setParent(_jspx_th_tiles_005fput_005f2);
/* 4142 */                                 int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 4143 */                                 if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */                                   for (;;) {
/* 4145 */                                     out.write(10);
/*      */                                     
/* 4147 */                                     WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4148 */                                     _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 4149 */                                     _jspx_th_c_005fwhen_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/*      */                                     
/* 4151 */                                     _jspx_th_c_005fwhen_005f18.setTest("${param.method!='showMailServerConfiguration'}");
/* 4152 */                                     int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 4153 */                                     if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */                                       for (;;) {
/* 4155 */                                         out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 4156 */                                         out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 4157 */                                         out.write("\n    </a>    \n ");
/* 4158 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 4159 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4163 */                                     if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 4164 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18); return;
/*      */                                     }
/*      */                                     
/* 4167 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 4168 */                                     out.write(10);
/* 4169 */                                     out.write(32);
/*      */                                     
/* 4171 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4172 */                                     _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 4173 */                                     _jspx_th_c_005fotherwise_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/* 4174 */                                     int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 4175 */                                     if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */                                       for (;;) {
/* 4177 */                                         out.write(10);
/* 4178 */                                         out.write(9);
/* 4179 */                                         out.write(32);
/* 4180 */                                         out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 4181 */                                         out.write(10);
/* 4182 */                                         out.write(32);
/* 4183 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 4184 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4188 */                                     if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 4189 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18); return;
/*      */                                     }
/*      */                                     
/* 4192 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 4193 */                                     out.write(10);
/* 4194 */                                     out.write(32);
/* 4195 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 4196 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4200 */                                 if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 4201 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18); return;
/*      */                                 }
/*      */                                 
/* 4204 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 4205 */                                 out.write("\n    </td>\n</tr>\n\n\n");
/* 4206 */                                 if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 4207 */                                   out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                                   
/* 4209 */                                   ChooseTag _jspx_th_c_005fchoose_005f19 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4210 */                                   _jspx_th_c_005fchoose_005f19.setPageContext(_jspx_page_context);
/* 4211 */                                   _jspx_th_c_005fchoose_005f19.setParent(_jspx_th_tiles_005fput_005f2);
/* 4212 */                                   int _jspx_eval_c_005fchoose_005f19 = _jspx_th_c_005fchoose_005f19.doStartTag();
/* 4213 */                                   if (_jspx_eval_c_005fchoose_005f19 != 0) {
/*      */                                     for (;;) {
/* 4215 */                                       out.write(10);
/*      */                                       
/* 4217 */                                       WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4218 */                                       _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 4219 */                                       _jspx_th_c_005fwhen_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/*      */                                       
/* 4221 */                                       _jspx_th_c_005fwhen_005f19.setTest("${param.method!='SMSServerConfiguration'}");
/* 4222 */                                       int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 4223 */                                       if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */                                         for (;;) {
/* 4225 */                                           out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 4226 */                                           out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 4227 */                                           out.write("\n    </a>\n ");
/* 4228 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 4229 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4233 */                                       if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 4234 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19); return;
/*      */                                       }
/*      */                                       
/* 4237 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 4238 */                                       out.write(10);
/* 4239 */                                       out.write(32);
/*      */                                       
/* 4241 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f19 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4242 */                                       _jspx_th_c_005fotherwise_005f19.setPageContext(_jspx_page_context);
/* 4243 */                                       _jspx_th_c_005fotherwise_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/* 4244 */                                       int _jspx_eval_c_005fotherwise_005f19 = _jspx_th_c_005fotherwise_005f19.doStartTag();
/* 4245 */                                       if (_jspx_eval_c_005fotherwise_005f19 != 0) {
/*      */                                         for (;;) {
/* 4247 */                                           out.write("\n         ");
/* 4248 */                                           out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 4249 */                                           out.write(10);
/* 4250 */                                           out.write(32);
/* 4251 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f19.doAfterBody();
/* 4252 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4256 */                                       if (_jspx_th_c_005fotherwise_005f19.doEndTag() == 5) {
/* 4257 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19); return;
/*      */                                       }
/*      */                                       
/* 4260 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 4261 */                                       out.write(10);
/* 4262 */                                       out.write(32);
/* 4263 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f19.doAfterBody();
/* 4264 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4268 */                                   if (_jspx_th_c_005fchoose_005f19.doEndTag() == 5) {
/* 4269 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19); return;
/*      */                                   }
/*      */                                   
/* 4272 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 4273 */                                   out.write("\n    </td>\n</tr>\n");
/*      */                                 }
/* 4275 */                                 out.write("\n\n\n ");
/*      */                                 
/* 4277 */                                 if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                 {
/*      */ 
/* 4280 */                                   out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                                   
/* 4282 */                                   ChooseTag _jspx_th_c_005fchoose_005f20 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4283 */                                   _jspx_th_c_005fchoose_005f20.setPageContext(_jspx_page_context);
/* 4284 */                                   _jspx_th_c_005fchoose_005f20.setParent(_jspx_th_tiles_005fput_005f2);
/* 4285 */                                   int _jspx_eval_c_005fchoose_005f20 = _jspx_th_c_005fchoose_005f20.doStartTag();
/* 4286 */                                   if (_jspx_eval_c_005fchoose_005f20 != 0) {
/*      */                                     for (;;) {
/* 4288 */                                       out.write(10);
/*      */                                       
/* 4290 */                                       WhenTag _jspx_th_c_005fwhen_005f20 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4291 */                                       _jspx_th_c_005fwhen_005f20.setPageContext(_jspx_page_context);
/* 4292 */                                       _jspx_th_c_005fwhen_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/*      */                                       
/* 4294 */                                       _jspx_th_c_005fwhen_005f20.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 4295 */                                       int _jspx_eval_c_005fwhen_005f20 = _jspx_th_c_005fwhen_005f20.doStartTag();
/* 4296 */                                       if (_jspx_eval_c_005fwhen_005f20 != 0) {
/*      */                                         for (;;) {
/* 4298 */                                           out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 4299 */                                           out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 4300 */                                           out.write("\n    </a>\n ");
/* 4301 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f20.doAfterBody();
/* 4302 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4306 */                                       if (_jspx_th_c_005fwhen_005f20.doEndTag() == 5) {
/* 4307 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20); return;
/*      */                                       }
/*      */                                       
/* 4310 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 4311 */                                       out.write(10);
/* 4312 */                                       out.write(32);
/*      */                                       
/* 4314 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f20 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4315 */                                       _jspx_th_c_005fotherwise_005f20.setPageContext(_jspx_page_context);
/* 4316 */                                       _jspx_th_c_005fotherwise_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/* 4317 */                                       int _jspx_eval_c_005fotherwise_005f20 = _jspx_th_c_005fotherwise_005f20.doStartTag();
/* 4318 */                                       if (_jspx_eval_c_005fotherwise_005f20 != 0) {
/*      */                                         for (;;) {
/* 4320 */                                           out.write(10);
/* 4321 */                                           out.write(9);
/* 4322 */                                           out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 4323 */                                           out.write(10);
/* 4324 */                                           out.write(32);
/* 4325 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f20.doAfterBody();
/* 4326 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4330 */                                       if (_jspx_th_c_005fotherwise_005f20.doEndTag() == 5) {
/* 4331 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20); return;
/*      */                                       }
/*      */                                       
/* 4334 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20);
/* 4335 */                                       out.write(10);
/* 4336 */                                       out.write(32);
/* 4337 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f20.doAfterBody();
/* 4338 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4342 */                                   if (_jspx_th_c_005fchoose_005f20.doEndTag() == 5) {
/* 4343 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20); return;
/*      */                                   }
/*      */                                   
/* 4346 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20);
/* 4347 */                                   out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                   
/* 4349 */                                   ChooseTag _jspx_th_c_005fchoose_005f21 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4350 */                                   _jspx_th_c_005fchoose_005f21.setPageContext(_jspx_page_context);
/* 4351 */                                   _jspx_th_c_005fchoose_005f21.setParent(_jspx_th_tiles_005fput_005f2);
/* 4352 */                                   int _jspx_eval_c_005fchoose_005f21 = _jspx_th_c_005fchoose_005f21.doStartTag();
/* 4353 */                                   if (_jspx_eval_c_005fchoose_005f21 != 0) {
/*      */                                     for (;;) {
/* 4355 */                                       out.write(10);
/*      */                                       
/* 4357 */                                       WhenTag _jspx_th_c_005fwhen_005f21 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4358 */                                       _jspx_th_c_005fwhen_005f21.setPageContext(_jspx_page_context);
/* 4359 */                                       _jspx_th_c_005fwhen_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/*      */                                       
/* 4361 */                                       _jspx_th_c_005fwhen_005f21.setTest("${uri !='/Upload.do'}");
/* 4362 */                                       int _jspx_eval_c_005fwhen_005f21 = _jspx_th_c_005fwhen_005f21.doStartTag();
/* 4363 */                                       if (_jspx_eval_c_005fwhen_005f21 != 0) {
/*      */                                         for (;;) {
/* 4365 */                                           out.write("   \n        ");
/*      */                                           
/* 4367 */                                           AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4368 */                                           _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 4369 */                                           _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f21);
/*      */                                           
/* 4371 */                                           _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                                           
/* 4373 */                                           _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 4374 */                                           int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 4375 */                                           if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 4376 */                                             if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 4377 */                                               out = _jspx_page_context.pushBody();
/* 4378 */                                               _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 4379 */                                               _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 4382 */                                               out.write("\n           ");
/* 4383 */                                               out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 4384 */                                               out.write("\n            ");
/* 4385 */                                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 4386 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 4389 */                                             if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 4390 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 4393 */                                           if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 4394 */                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                           }
/*      */                                           
/* 4397 */                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 4398 */                                           out.write(10);
/* 4399 */                                           out.write(10);
/* 4400 */                                           out.write(32);
/* 4401 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f21.doAfterBody();
/* 4402 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4406 */                                       if (_jspx_th_c_005fwhen_005f21.doEndTag() == 5) {
/* 4407 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21); return;
/*      */                                       }
/*      */                                       
/* 4410 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21);
/* 4411 */                                       out.write(10);
/* 4412 */                                       out.write(32);
/*      */                                       
/* 4414 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f21 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4415 */                                       _jspx_th_c_005fotherwise_005f21.setPageContext(_jspx_page_context);
/* 4416 */                                       _jspx_th_c_005fotherwise_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/* 4417 */                                       int _jspx_eval_c_005fotherwise_005f21 = _jspx_th_c_005fotherwise_005f21.doStartTag();
/* 4418 */                                       if (_jspx_eval_c_005fotherwise_005f21 != 0) {
/*      */                                         for (;;) {
/* 4420 */                                           out.write(10);
/* 4421 */                                           out.write(9);
/* 4422 */                                           out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 4423 */                                           out.write(10);
/* 4424 */                                           out.write(32);
/* 4425 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f21.doAfterBody();
/* 4426 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4430 */                                       if (_jspx_th_c_005fotherwise_005f21.doEndTag() == 5) {
/* 4431 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21); return;
/*      */                                       }
/*      */                                       
/* 4434 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21);
/* 4435 */                                       out.write(10);
/* 4436 */                                       out.write(32);
/* 4437 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f21.doAfterBody();
/* 4438 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4442 */                                   if (_jspx_th_c_005fchoose_005f21.doEndTag() == 5) {
/* 4443 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21); return;
/*      */                                   }
/*      */                                   
/* 4446 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21);
/* 4447 */                                   out.write("\n    </td>\n</tr>\n \n ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4451 */                                 out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                                 
/* 4453 */                                 ChooseTag _jspx_th_c_005fchoose_005f22 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4454 */                                 _jspx_th_c_005fchoose_005f22.setPageContext(_jspx_page_context);
/* 4455 */                                 _jspx_th_c_005fchoose_005f22.setParent(_jspx_th_tiles_005fput_005f2);
/* 4456 */                                 int _jspx_eval_c_005fchoose_005f22 = _jspx_th_c_005fchoose_005f22.doStartTag();
/* 4457 */                                 if (_jspx_eval_c_005fchoose_005f22 != 0) {
/*      */                                   for (;;) {
/* 4459 */                                     out.write(10);
/*      */                                     
/* 4461 */                                     WhenTag _jspx_th_c_005fwhen_005f22 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4462 */                                     _jspx_th_c_005fwhen_005f22.setPageContext(_jspx_page_context);
/* 4463 */                                     _jspx_th_c_005fwhen_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/*      */                                     
/* 4465 */                                     _jspx_th_c_005fwhen_005f22.setTest("${uri !='/admin/userconfiguration.do'}");
/* 4466 */                                     int _jspx_eval_c_005fwhen_005f22 = _jspx_th_c_005fwhen_005f22.doStartTag();
/* 4467 */                                     if (_jspx_eval_c_005fwhen_005f22 != 0) {
/*      */                                       for (;;) {
/* 4469 */                                         out.write("\n    \n        ");
/*      */                                         
/* 4471 */                                         AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4472 */                                         _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 4473 */                                         _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f22);
/*      */                                         
/* 4475 */                                         _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                         
/* 4477 */                                         _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 4478 */                                         int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 4479 */                                         if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 4480 */                                           if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 4481 */                                             out = _jspx_page_context.pushBody();
/* 4482 */                                             _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 4483 */                                             _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4486 */                                             out.write("\n       ");
/* 4487 */                                             out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 4488 */                                             out.write("\n        ");
/* 4489 */                                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 4490 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4493 */                                           if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 4494 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4497 */                                         if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 4498 */                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                         }
/*      */                                         
/* 4501 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 4502 */                                         out.write(10);
/* 4503 */                                         out.write(10);
/* 4504 */                                         out.write(32);
/* 4505 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f22.doAfterBody();
/* 4506 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4510 */                                     if (_jspx_th_c_005fwhen_005f22.doEndTag() == 5) {
/* 4511 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22); return;
/*      */                                     }
/*      */                                     
/* 4514 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22);
/* 4515 */                                     out.write(10);
/* 4516 */                                     out.write(32);
/*      */                                     
/* 4518 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f22 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4519 */                                     _jspx_th_c_005fotherwise_005f22.setPageContext(_jspx_page_context);
/* 4520 */                                     _jspx_th_c_005fotherwise_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/* 4521 */                                     int _jspx_eval_c_005fotherwise_005f22 = _jspx_th_c_005fotherwise_005f22.doStartTag();
/* 4522 */                                     if (_jspx_eval_c_005fotherwise_005f22 != 0) {
/*      */                                       for (;;) {
/* 4524 */                                         out.write(10);
/* 4525 */                                         out.write(9);
/* 4526 */                                         out.write(32);
/* 4527 */                                         out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 4528 */                                         out.write(10);
/* 4529 */                                         out.write(32);
/* 4530 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f22.doAfterBody();
/* 4531 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4535 */                                     if (_jspx_th_c_005fotherwise_005f22.doEndTag() == 5) {
/* 4536 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22); return;
/*      */                                     }
/*      */                                     
/* 4539 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22);
/* 4540 */                                     out.write(10);
/* 4541 */                                     out.write(32);
/* 4542 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f22.doAfterBody();
/* 4543 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4547 */                                 if (_jspx_th_c_005fchoose_005f22.doEndTag() == 5) {
/* 4548 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22); return;
/*      */                                 }
/*      */                                 
/* 4551 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22);
/* 4552 */                                 out.write("\n    </td>\n</tr>\n   \n\n ");
/* 4553 */                                 if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 4554 */                                   out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                                   
/* 4556 */                                   ChooseTag _jspx_th_c_005fchoose_005f23 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4557 */                                   _jspx_th_c_005fchoose_005f23.setPageContext(_jspx_page_context);
/* 4558 */                                   _jspx_th_c_005fchoose_005f23.setParent(_jspx_th_tiles_005fput_005f2);
/* 4559 */                                   int _jspx_eval_c_005fchoose_005f23 = _jspx_th_c_005fchoose_005f23.doStartTag();
/* 4560 */                                   if (_jspx_eval_c_005fchoose_005f23 != 0) {
/*      */                                     for (;;) {
/* 4562 */                                       out.write("\n   ");
/*      */                                       
/* 4564 */                                       WhenTag _jspx_th_c_005fwhen_005f23 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4565 */                                       _jspx_th_c_005fwhen_005f23.setPageContext(_jspx_page_context);
/* 4566 */                                       _jspx_th_c_005fwhen_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/*      */                                       
/* 4568 */                                       _jspx_th_c_005fwhen_005f23.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 4569 */                                       int _jspx_eval_c_005fwhen_005f23 = _jspx_th_c_005fwhen_005f23.doStartTag();
/* 4570 */                                       if (_jspx_eval_c_005fwhen_005f23 != 0) {
/*      */                                         for (;;) {
/* 4572 */                                           out.write("\n    ");
/*      */                                           
/* 4574 */                                           AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4575 */                                           _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 4576 */                                           _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f23);
/*      */                                           
/* 4578 */                                           _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                                           
/* 4580 */                                           _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 4581 */                                           int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 4582 */                                           if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 4583 */                                             if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 4584 */                                               out = _jspx_page_context.pushBody();
/* 4585 */                                               _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 4586 */                                               _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 4589 */                                               out.write(10);
/* 4590 */                                               out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 4591 */                                               out.write("\n    ");
/* 4592 */                                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 4593 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 4596 */                                             if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 4597 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 4600 */                                           if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 4601 */                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                                           }
/*      */                                           
/* 4604 */                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 4605 */                                           out.write("\n   ");
/* 4606 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f23.doAfterBody();
/* 4607 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4611 */                                       if (_jspx_th_c_005fwhen_005f23.doEndTag() == 5) {
/* 4612 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23); return;
/*      */                                       }
/*      */                                       
/* 4615 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23);
/* 4616 */                                       out.write("\n   ");
/*      */                                       
/* 4618 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f23 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4619 */                                       _jspx_th_c_005fotherwise_005f23.setPageContext(_jspx_page_context);
/* 4620 */                                       _jspx_th_c_005fotherwise_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/* 4621 */                                       int _jspx_eval_c_005fotherwise_005f23 = _jspx_th_c_005fotherwise_005f23.doStartTag();
/* 4622 */                                       if (_jspx_eval_c_005fotherwise_005f23 != 0) {
/*      */                                         for (;;) {
/* 4624 */                                           out.write(10);
/* 4625 */                                           out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 4626 */                                           out.write("\n   ");
/* 4627 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f23.doAfterBody();
/* 4628 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4632 */                                       if (_jspx_th_c_005fotherwise_005f23.doEndTag() == 5) {
/* 4633 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23); return;
/*      */                                       }
/*      */                                       
/* 4636 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23);
/* 4637 */                                       out.write(10);
/* 4638 */                                       out.write(32);
/* 4639 */                                       out.write(32);
/* 4640 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f23.doAfterBody();
/* 4641 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4645 */                                   if (_jspx_th_c_005fchoose_005f23.doEndTag() == 5) {
/* 4646 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23); return;
/*      */                                   }
/*      */                                   
/* 4649 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23);
/* 4650 */                                   out.write("\n </td>\n</tr>\n  ");
/*      */                                 }
/* 4652 */                                 out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                                 
/* 4654 */                                 ChooseTag _jspx_th_c_005fchoose_005f24 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4655 */                                 _jspx_th_c_005fchoose_005f24.setPageContext(_jspx_page_context);
/* 4656 */                                 _jspx_th_c_005fchoose_005f24.setParent(_jspx_th_tiles_005fput_005f2);
/* 4657 */                                 int _jspx_eval_c_005fchoose_005f24 = _jspx_th_c_005fchoose_005f24.doStartTag();
/* 4658 */                                 if (_jspx_eval_c_005fchoose_005f24 != 0) {
/*      */                                   for (;;) {
/* 4660 */                                     out.write("\n   ");
/*      */                                     
/* 4662 */                                     WhenTag _jspx_th_c_005fwhen_005f24 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4663 */                                     _jspx_th_c_005fwhen_005f24.setPageContext(_jspx_page_context);
/* 4664 */                                     _jspx_th_c_005fwhen_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/*      */                                     
/* 4666 */                                     _jspx_th_c_005fwhen_005f24.setTest("${param.method!='showDataCleanUp'}");
/* 4667 */                                     int _jspx_eval_c_005fwhen_005f24 = _jspx_th_c_005fwhen_005f24.doStartTag();
/* 4668 */                                     if (_jspx_eval_c_005fwhen_005f24 != 0) {
/*      */                                       for (;;) {
/* 4670 */                                         out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 4671 */                                         out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 4672 */                                         out.write("\n    </a>\n   ");
/* 4673 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f24.doAfterBody();
/* 4674 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4678 */                                     if (_jspx_th_c_005fwhen_005f24.doEndTag() == 5) {
/* 4679 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24); return;
/*      */                                     }
/*      */                                     
/* 4682 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24);
/* 4683 */                                     out.write("\n   ");
/*      */                                     
/* 4685 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f24 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4686 */                                     _jspx_th_c_005fotherwise_005f24.setPageContext(_jspx_page_context);
/* 4687 */                                     _jspx_th_c_005fotherwise_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/* 4688 */                                     int _jspx_eval_c_005fotherwise_005f24 = _jspx_th_c_005fotherwise_005f24.doStartTag();
/* 4689 */                                     if (_jspx_eval_c_005fotherwise_005f24 != 0) {
/*      */                                       for (;;) {
/* 4691 */                                         out.write(10);
/* 4692 */                                         out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 4693 */                                         out.write("\n   ");
/* 4694 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f24.doAfterBody();
/* 4695 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4699 */                                     if (_jspx_th_c_005fotherwise_005f24.doEndTag() == 5) {
/* 4700 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24); return;
/*      */                                     }
/*      */                                     
/* 4703 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24);
/* 4704 */                                     out.write(10);
/* 4705 */                                     out.write(32);
/* 4706 */                                     out.write(32);
/* 4707 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f24.doAfterBody();
/* 4708 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4712 */                                 if (_jspx_th_c_005fchoose_005f24.doEndTag() == 5) {
/* 4713 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24); return;
/*      */                                 }
/*      */                                 
/* 4716 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24);
/* 4717 */                                 out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 4718 */                                 out.write("\t\t\n\t");
/*      */                               }
/* 4720 */                               out.write("  \t\t\n\t   <br>\t\t\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr> \t\t\n\t    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 4721 */                               out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 4722 */                               out.write("</td>\t\t\n\t    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 4723 */                               if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 4725 */                               out.write("/img_quicknote.gif\" hspace=\"5\"></td>\t\t\n\t  </tr>\t\t\n\t  <tr> \t\t\n\t    <td colspan=\"2\" class=\"quicknote\">");
/* 4726 */                               out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.quicknote.text"));
/* 4727 */                               out.write("</td>\t\t\n\t  </tr>\t\t\n\t</table>\t\t\n\t");
/* 4728 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 4729 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 4732 */                             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 4733 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 4736 */                           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4737 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                           }
/*      */                           
/* 4740 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 4741 */                           out.write("      \n\n\n\n");
/*      */                           
/* 4743 */                           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4744 */                           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4745 */                           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 4747 */                           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                           
/* 4749 */                           _jspx_th_tiles_005fput_005f3.setType("string");
/* 4750 */                           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4751 */                           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 4752 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4753 */                               out = _jspx_page_context.pushBody();
/* 4754 */                               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 4755 */                               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 4758 */                               out.write(10);
/* 4759 */                               out.write(10);
/*      */                               
/* 4761 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 4762 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 4763 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 4765 */                               _jspx_th_html_005fform_005f0.setAction("/scheduleReports");
/*      */                               
/* 4767 */                               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 4768 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 4769 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 4771 */                                   out.write("\n<input type=hidden name='method'>\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\">\n\t<tr>\n\t ");
/*      */                                   
/* 4773 */                                   if (EnterpriseUtil.isAdminServer())
/*      */                                   {
/*      */ 
/* 4776 */                                     out.write("\n\t  <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4777 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 4778 */                                     out.write(" &gt; <span class=\"bcactive\">");
/* 4779 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.scheduleheading.text"));
/* 4780 */                                     out.write("</span></td>\n\t ");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 4784 */                                     out.write("\n\t\t <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4785 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 4786 */                                     out.write(" &gt; <span class=\"bcactive\">");
/* 4787 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.scheduleheading.text"));
/* 4788 */                                     out.write("</span></td>\n\t ");
/*      */                                   }
/* 4790 */                                   out.write("\n\t  \n\t</tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\"  class=\"lrtbdarkborder itadmin-no-decor\" >\n\t <tr>\n\t <td width=\"35%\" height=\"31\" class=\"tableheadingbborder itadmin-hide\" colspan='8' align=left>");
/* 4791 */                                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 4792 */                                   out.write(" <a name=\"Process\" id=\"Process\"></a></td>\n\t \t\n\t    <td width=\"65%\" height=\"31\" align=\"right\" class=\"tableheadingbborder itadmin-dotted-border\"  colspan='3'>&nbsp;<a href=\"/scheduleReports.do?method=newScheduleReports\" class=\"bodytextboldwhiteun itadmin-buttons\">");
/* 4793 */                                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.newschedule.text"));
/* 4794 */                                   out.write("</a>&nbsp;</td>\n\t\t  \n\t </tr>\n");
/* 4795 */                                   ArrayList row = (ArrayList)request.getAttribute("data");
/* 4796 */                                   if (row.size() == 0)
/*      */                                   {
/*      */ 
/* 4799 */                                     out.write("\n           \n\t \n\t <tr>\n\t    <td class=\"bodytext\" align=\"center\" height=\"26\"  colspan='10'> \n            \n\t\t  ");
/* 4800 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.noschedulemessage.text"));
/* 4801 */                                     out.write("\n\t\t  </td> \n\t\t  \n\t </tr>\n\t </table>\n\t");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 4806 */                                     out.write("\n\n\t  <tr>\n\t <td width=\"2%\" height=\"28\"  class=\"columnheading\">\n\t\n\t <input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAll(this,'scheduleids');\"> </td>\n\t\n\t\n        <td width=\"15%\" height=\"28\"  class=\"columnheading\" align=\"left\">");
/* 4807 */                                     out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4808 */                                     out.write("</td>\n          ");
/*      */                                     
/* 4810 */                                     String loginUserName = request.getRemoteUser();
/* 4811 */                                     if (!DBUtil.isDelegatedAdmin(loginUserName))
/*      */                                     {
/* 4813 */                                       out.write("\n        <td width=\"10%\" height=\"28\"  class=\"columnheading\" align=\"left\">");
/* 4814 */                                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.createdby"));
/* 4815 */                                       out.write("</td>\n        ");
/*      */                                     }
/* 4817 */                                     out.write("\n        <td width=\"5%\" align=\"left\" height=\"28\"  class=\"columnheading\" title='");
/* 4818 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.statusmessage.text"));
/* 4819 */                                     out.write("' nowrap>");
/* 4820 */                                     out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 4821 */                                     out.write("<br>\n         </td>\n      \n        <td width=\"15%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 4822 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.reporttype.text"));
/* 4823 */                                     out.write("</td>\n        <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 4824 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.reportperiod.text"));
/* 4825 */                                     out.write("</td>\n\t<td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 4826 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.reporttime.text"));
/* 4827 */                                     out.write("</td>\n        <td width=\"10%\" height=\"28\"  class=\"columnheading\" align=\"left\">");
/* 4828 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.scheduleperiod.text"));
/* 4829 */                                     out.write("</td>\n\t <td width=\"18%\" height=\"28\"  class=\"columnheading\" align=\"left\">");
/* 4830 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.lastexecution.status"));
/* 4831 */                                     out.write("</td>\n        <td width=\"8%\" height=\"28\"  class=\"columnheading\" align=\"center\">");
/* 4832 */                                     out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 4833 */                                     out.write("</td>\n\t<td width=\"7%\" height=\"28\"  class=\"columnheading\" align=\"center\">");
/* 4834 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.runnow"));
/* 4835 */                                     out.write("</td>\n      </tr>\n      ");
/*      */                                     
/*      */ 
/*      */ 
/* 4839 */                                     for (int i = 0; i < row.size(); i++)
/*      */                                     {
/* 4841 */                                       ArrayList data = (ArrayList)row.get(i);
/* 4842 */                                       String sid = null;
/* 4843 */                                       String sname = null;
/* 4844 */                                       String status = null;
/* 4845 */                                       String reporttype = null;
/* 4846 */                                       String period = null;
/* 4847 */                                       String speriod = null;
/* 4848 */                                       String bgcolor = null;
/* 4849 */                                       String shour = null;
/* 4850 */                                       String smin = null;
/* 4851 */                                       String createdby = "";
/* 4852 */                                       if (i % 2 == 0)
/*      */                                       {
/* 4854 */                                         bgcolor = "class=\"whitegrayborder\"";
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4858 */                                         bgcolor = "class=\"yellowgrayborder\"";
/*      */                                       }
/*      */                                       
/*      */ 
/*      */ 
/* 4863 */                                       sid = (String)data.get(0);
/* 4864 */                                       sname = (String)data.get(1);
/* 4865 */                                       status = (String)data.get(2);
/* 4866 */                                       reporttype = (String)data.get(3);
/*      */                                       
/* 4868 */                                       period = (String)data.get(4);
/* 4869 */                                       speriod = (String)data.get(5);
/* 4870 */                                       shour = (String)data.get(6);
/* 4871 */                                       smin = (String)data.get(7);
/* 4872 */                                       String lastExecTime = (String)data.get(8);
/* 4873 */                                       String comment = (String)data.get(9);
/* 4874 */                                       if (data.size() == 11) {
/* 4875 */                                         createdby = (String)data.get(10);
/*      */                                       }
/*      */                                       
/* 4878 */                                       out.write("\n             <tr> \n      <td   ");
/* 4879 */                                       out.print(bgcolor);
/* 4880 */                                       out.write(" ><input type=\"checkbox\" name=\"scheduleids\" value=\"");
/* 4881 */                                       out.print(sid);
/* 4882 */                                       out.write("\"> </td>\n      <td ");
/* 4883 */                                       out.print(bgcolor);
/* 4884 */                                       out.write(" align=\"left\" ><a href=\"/scheduleReports.do?method=editScheduler&sid=");
/* 4885 */                                       out.print(sid);
/* 4886 */                                       out.write("\"><span class='staticlinks'>");
/* 4887 */                                       out.print(sname);
/* 4888 */                                       out.write("</span></a></td>\n      ");
/*      */                                       
/* 4890 */                                       if (!DBUtil.isDelegatedAdmin(loginUserName))
/*      */                                       {
/* 4892 */                                         out.write("\n      <td ");
/* 4893 */                                         out.print(bgcolor);
/* 4894 */                                         out.write(" align=\"left\" >");
/* 4895 */                                         out.print(createdby);
/* 4896 */                                         out.write("</td>\n      ");
/*      */                                       }
/* 4898 */                                       out.write("\n      <td ");
/* 4899 */                                       out.print(bgcolor);
/* 4900 */                                       out.write(" align=\"left\" > <a href=\"javascript:getEnable('");
/* 4901 */                                       out.print(sid);
/* 4902 */                                       out.write("');\" class='staticlinks' >\n      ");
/* 4903 */                                       if ((status != null) && (status.equalsIgnoreCase("enable")))
/*      */                                       {
/* 4905 */                                         out.write("\n      <img border=\"0\" src=\"/images/icon_tickmark.gif\" title='");
/* 4906 */                                         out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.enable.text"));
/* 4907 */                                         out.write("' id='");
/* 4908 */                                         out.print(sid);
/* 4909 */                                         out.write("'>\n      ");
/*      */                                       }
/*      */                                       else {
/* 4912 */                                         out.write("\n       <img border=\"0\" src=\"/images/cross.gif\" title='");
/* 4913 */                                         out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.disable.text"));
/* 4914 */                                         out.write("' id='");
/* 4915 */                                         out.print(sid);
/* 4916 */                                         out.write("'>\n       ");
/*      */                                       }
/* 4918 */                                       out.write("\n       \n      </a></td>\n      <td ");
/* 4919 */                                       out.print(bgcolor);
/* 4920 */                                       out.write(" align=\"left\">");
/* 4921 */                                       out.print(FormatUtil.getString(reporttype));
/* 4922 */                                       out.write(" </td>\t\n      \n      <td ");
/* 4923 */                                       out.print(bgcolor);
/* 4924 */                                       out.write(" align=\"left\">");
/* 4925 */                                       out.print(period);
/* 4926 */                                       out.write("</td>\n\t<td ");
/* 4927 */                                       out.print(bgcolor);
/* 4928 */                                       out.write(" align=\"left\">");
/* 4929 */                                       out.print(shour);
/* 4930 */                                       out.write(58);
/* 4931 */                                       out.print(smin);
/* 4932 */                                       out.write("</td>\n       <td ");
/* 4933 */                                       out.print(bgcolor);
/* 4934 */                                       out.write(" align=\"left\">");
/* 4935 */                                       out.print(FormatUtil.getString(speriod));
/* 4936 */                                       out.write("</td>\n");
/* 4937 */                                       if (!lastExecTime.equals("-")) {
/* 4938 */                                         out.write("\n\t<td ");
/* 4939 */                                         out.print(bgcolor);
/* 4940 */                                         out.write(" align=\"left\">");
/* 4941 */                                         out.print(lastExecTime);
/* 4942 */                                         out.write("&nbsp;, ");
/* 4943 */                                         out.print(FormatUtil.getString(comment));
/* 4944 */                                         out.write(" </td>\n");
/*      */                                       } else {
/* 4946 */                                         out.write("\n        <td ");
/* 4947 */                                         out.print(bgcolor);
/* 4948 */                                         out.write(" align=\"left\">-</td>\n");
/*      */                                       }
/* 4950 */                                       out.write("\n      <td ");
/* 4951 */                                       out.print(bgcolor);
/* 4952 */                                       out.write("  align=\"center\"><a href=\"/scheduleReports.do?method=editScheduler&sid=");
/* 4953 */                                       out.print(sid);
/* 4954 */                                       out.write("\"><img src=\"/images/icon_edit.gif\" title=\"");
/* 4955 */                                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.editschedule.text"));
/* 4956 */                                       out.write("\"  border=\"0\" ></a></td>\n\t<td ");
/* 4957 */                                       out.print(bgcolor);
/* 4958 */                                       out.write("  align=\"center\">\n\t\t");
/*      */                                       
/* 4960 */                                       PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4961 */                                       _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4962 */                                       _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 4964 */                                       _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 4965 */                                       int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4966 */                                       if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                         for (;;) {
/* 4968 */                                           out.write("\t\t\t\n\t\t\t<a href=\"javascript:alertUser();\"><img src=\"/images/icon_executeaction.gif\" title=\"");
/* 4969 */                                           out.print(FormatUtil.getString("am.webclient.schedulereport.runnow"));
/* 4970 */                                           out.write("\"  border=\"0\" ></a>\t\n\t\t");
/* 4971 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4972 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4976 */                                       if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4977 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                       }
/*      */                                       
/* 4980 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4981 */                                       out.write(10);
/* 4982 */                                       out.write(9);
/* 4983 */                                       out.write(9);
/*      */                                       
/* 4985 */                                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4986 */                                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 4987 */                                       _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 4989 */                                       _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 4990 */                                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 4991 */                                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                         for (;;) {
/* 4993 */                                           out.write("\t\n\t\t\t<a href=\"/scheduleReports.do?method=testAction&sid=");
/* 4994 */                                           out.print(sid);
/* 4995 */                                           out.write("\"><img src=\"/images/icon_executeaction.gif\" title=\"");
/* 4996 */                                           out.print(FormatUtil.getString("am.webclient.schedulereport.runnow"));
/* 4997 */                                           out.write("\"  border=\"0\" ></a>\n\t\t");
/* 4998 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 4999 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 5003 */                                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 5004 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                       }
/*      */                                       
/* 5007 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 5008 */                                       out.write("\t\n\t</td>\n          </tr>   \n             \n            \n\n\n            ");
/*      */                                     }
/* 5010 */                                     out.write("\n            <tr class=\"columnheading\">\n\t <td width=\"100%\" height=\"24\" colspan='10'   >\n\t <a href=\"javascript:removeScheduler();\" class='staticlinks'>");
/* 5011 */                                     out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 5012 */                                     out.write("</a>&nbsp;|&nbsp;<a href=\"/scheduleReports.do?method=newScheduleReports\" class=\"staticlinks\">");
/* 5013 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.newschedule.text"));
/* 5014 */                                     out.write("</a></td>\n\t </tr>\n\t </table>\n            ");
/*      */                                   }
/* 5016 */                                   out.write("\n              \n             \n\n");
/* 5017 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5018 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5022 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5023 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 5026 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5027 */                               out.write(10);
/* 5028 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 5029 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 5032 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 5033 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 5036 */                           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 5037 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                           }
/*      */                           
/* 5040 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 5041 */                           out.write(" \n    ");
/* 5042 */                           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 5044 */                           out.write(10);
/* 5045 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5046 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5050 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5051 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                       }
/*      */                       else {
/* 5054 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5055 */                         out.write(10);
/*      */                       }
/* 5057 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5058 */         out = _jspx_out;
/* 5059 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5060 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5061 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5064 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5070 */     PageContext pageContext = _jspx_page_context;
/* 5071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5073 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 5074 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5075 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 5077 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 5079 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 5080 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5081 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5082 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5083 */       return true;
/*      */     }
/* 5085 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5086 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5091 */     PageContext pageContext = _jspx_page_context;
/* 5092 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5094 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5095 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 5096 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 5098 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 5099 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 5100 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 5102 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 5103 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 5104 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5108 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 5109 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5110 */       return true;
/*      */     }
/* 5112 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5113 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5118 */     PageContext pageContext = _jspx_page_context;
/* 5119 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5121 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5122 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 5123 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 5125 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 5126 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 5127 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 5129 */         out.write("\n alertUser();\n return;\n ");
/* 5130 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 5131 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5135 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 5136 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5137 */       return true;
/*      */     }
/* 5139 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5140 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5145 */     PageContext pageContext = _jspx_page_context;
/* 5146 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5148 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5149 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 5150 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5152 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 5154 */     _jspx_th_tiles_005fput_005f0.setValue("Schedule Reports");
/* 5155 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 5156 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 5157 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5158 */       return true;
/*      */     }
/* 5160 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5161 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5166 */     PageContext pageContext = _jspx_page_context;
/* 5167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5169 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5170 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5171 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5173 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5175 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 5176 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5177 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5178 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5179 */       return true;
/*      */     }
/* 5181 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5182 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5187 */     PageContext pageContext = _jspx_page_context;
/* 5188 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5190 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 5191 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5192 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5194 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*      */     
/* 5196 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 5197 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5198 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5199 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5200 */       return true;
/*      */     }
/* 5202 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5203 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5208 */     PageContext pageContext = _jspx_page_context;
/* 5209 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5211 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5212 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 5213 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5215 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 5217 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 5218 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 5219 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5220 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5221 */       return true;
/*      */     }
/* 5223 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5224 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ShowScheduleReportsDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */