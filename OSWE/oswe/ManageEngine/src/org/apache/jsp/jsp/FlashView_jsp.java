/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class FlashView_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  901 */     Properties temp = FaultUtil.getStatus(entitylist, false);
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
/*  915 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*      */   public Properties getStatusForMgs(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/* 2183 */     return FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*      */   }
/*      */   
/* 2186 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2192 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2193 */   static { _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2194 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2195 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2196 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2197 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2219 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2238 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/* 2254 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2262 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2265 */     JspWriter out = null;
/* 2266 */     Object page = this;
/* 2267 */     JspWriter _jspx_out = null;
/* 2268 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2272 */       response.setContentType("text/html;charset=UTF-8");
/* 2273 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2275 */       _jspx_page_context = pageContext;
/* 2276 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2277 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2278 */       session = pageContext.getSession();
/* 2279 */       out = pageContext.getOut();
/* 2280 */       _jspx_out = out;
/*      */       
/* 2282 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 2283 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2285 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2286 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2288 */       out.write(10);
/* 2289 */       out.write(10);
/* 2290 */       out.write(10);
/* 2291 */       out.write(10);
/* 2292 */       out.write(10);
/* 2293 */       com.adventnet.appmanager.struts.form.FlashForm FlashForm = null;
/* 2294 */       FlashForm = (com.adventnet.appmanager.struts.form.FlashForm)_jspx_page_context.getAttribute("FlashForm", 2);
/* 2295 */       if (FlashForm == null) {
/* 2296 */         FlashForm = new com.adventnet.appmanager.struts.form.FlashForm();
/* 2297 */         _jspx_page_context.setAttribute("FlashForm", FlashForm, 2);
/*      */       }
/* 2299 */       out.write(10);
/* 2300 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2301 */       out.write(10);
/*      */       
/* 2303 */       SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2304 */       _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2305 */       _jspx_th_c_005fset_005f0.setParent(null);
/*      */       
/* 2307 */       _jspx_th_c_005fset_005f0.setVar("readOnlyMode");
/* 2308 */       int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2309 */       if (_jspx_eval_c_005fset_005f0 != 0) {
/* 2310 */         if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2311 */           out = _jspx_page_context.pushBody();
/* 2312 */           _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 2313 */           _jspx_th_c_005fset_005f0.doInitBody();
/*      */         }
/*      */         for (;;) {
/* 2316 */           out.print(com.adventnet.appmanager.util.DashboardUtil.isReadOnlyVewForUser(request.getRemoteUser(), (String)request.getAttribute("pageid")));
/* 2317 */           int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 2318 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/* 2321 */         if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2322 */           out = _jspx_page_context.popBody();
/*      */         }
/*      */       }
/* 2325 */       if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2326 */         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*      */       }
/*      */       else {
/* 2329 */         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 2330 */         out.write(10);
/*      */         
/*      */         try
/*      */         {
/* 2334 */           String haid_flash = request.getParameter("haid");
/* 2335 */           String isPopUp = request.getParameter("isPopUp");
/* 2336 */           String viewid = "-1";
/* 2337 */           if (request.getAttribute("viewid") != null)
/*      */           {
/* 2339 */             viewid = (String)request.getAttribute("viewid");
/*      */           }
/* 2341 */           if (request.getParameter("viewid") != null)
/*      */           {
/* 2343 */             viewid = request.getParameter("viewid");
/*      */           }
/*      */           
/*      */ 
/* 2347 */           String hostname = "localhost:9090";
/*      */           try
/*      */           {
/* 2350 */             hostname = request.getServerName() + ":" + request.getServerPort();
/*      */           }
/*      */           catch (Exception e) {
/* 2353 */             e.printStackTrace();
/*      */           }
/*      */           
/* 2356 */           out.write(10);
/* 2357 */           if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */             return;
/* 2359 */           out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/Dialog.js\"></SCRIPT>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/swfobject_swfformfix.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/appmanager.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/fjscriptapi.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/z-menu.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/Utils.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/jsonpath.js\"></script>\n");
/* 2360 */           out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2361 */           if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */             return;
/* 2363 */           out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2364 */           out.write(10);
/* 2365 */           out.write("\n\n<script type=\"text/javascript\">\n\nvar isHover = false;\nvar isXMLLoaded=\"false\";\nvar viewid=\"");
/* 2366 */           out.print(viewid);
/* 2367 */           out.write("\";\nvar timeOutHandle;\nvar isHtml = ");
/* 2368 */           if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */             return;
/* 2370 */           out.write(";\nfunction getXMLURL()\n{\n\t\t");
/*      */           
/* 2372 */           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2373 */           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2374 */           _jspx_th_c_005fif_005f1.setParent(null);
/*      */           
/* 2376 */           _jspx_th_c_005fif_005f1.setTest("${param.viewid != \"-1\"}");
/* 2377 */           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2378 */           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */             for (;;) {
/* 2380 */               out.write("\n\t\treturn \"/GraphicalView.do?method=getMGXML&haid=");
/* 2381 */               out.print(haid_flash);
/* 2382 */               out.write("&viewid=");
/* 2383 */               out.print(viewid);
/* 2384 */               out.write("&currentime=\"+");
/* 2385 */               out.print(System.currentTimeMillis());
/* 2386 */               out.write("+\"&isHtml=false\";\t\t\t// No I18N\n\t\t");
/* 2387 */               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2388 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2392 */           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2393 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */           }
/*      */           
/* 2396 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2397 */           out.write(10);
/* 2398 */           out.write(9);
/* 2399 */           out.write(9);
/*      */           
/* 2401 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2402 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2403 */           _jspx_th_c_005fif_005f2.setParent(null);
/*      */           
/* 2405 */           _jspx_th_c_005fif_005f2.setTest("${param.viewid == \"-1\"}");
/* 2406 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2407 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */             for (;;) {
/* 2409 */               out.write("\n\t\treturn \"/GraphicalView.do?method=getMGXML&haid=");
/* 2410 */               out.print(haid_flash);
/* 2411 */               out.write("&currentime=\"+");
/* 2412 */               out.print(System.currentTimeMillis());
/* 2413 */               out.write("+\"&isHtml=false\";\t\t\t\t\t\t\t// No I18N\n\t\t");
/* 2414 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2415 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2419 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2420 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */           }
/*      */           
/* 2423 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2424 */           out.write("\n\n}\n\nfunction getLineThickness()\n{\nreturn ");
/* 2425 */           if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */             return;
/* 2427 */           out.write(";\n}\n\nfunction getLineColor()\n{\nreturn \"");
/* 2428 */           if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */             return;
/* 2430 */           out.write("\";\n}\n\nfunction getLineTransparency()\n{\nreturn ");
/* 2431 */           if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*      */             return;
/* 2433 */           out.write(";\n}\n\nfunction getLabelColor()\n{\nreturn \"");
/* 2434 */           if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*      */             return;
/* 2436 */           out.write("\";\n}\n\nfunction saveFlashXML()\n{\n\n\t var tempDisplayname=\"\";\n\t");
/*      */           
/* 2438 */           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2439 */           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2440 */           _jspx_th_c_005fif_005f3.setParent(null);
/*      */           
/* 2442 */           _jspx_th_c_005fif_005f3.setTest("${ FlashForm.fromMonitorTab == true}");
/* 2443 */           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2444 */           if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */             for (;;) {
/* 2446 */               out.write("\n\tif(document.FlashForm.selectedView.length>1 && document.FlashForm.selectedView.value=='-10')\n\t{\n\t  var name=prompt(\"");
/* 2447 */               out.print(FormatUtil.getString("am.webclient.flashview.newview.confirm.text"));
/* 2448 */               out.write(34);
/* 2449 */               out.write(44);
/* 2450 */               out.write(34);
/* 2451 */               out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/* 2452 */               out.write("\");\n\t  if (name!=null && name.trim()!=\"\")\n\t  {\n\t\ttempDisplayname=\"&displayName=\"+name;//No I18N\n\t  }\n\t  else\n\t  {\n\t   alert(\"");
/* 2453 */               out.print(FormatUtil.getString("am.webclient.flashview.displayname.empty.text"));
/* 2454 */               out.write("\");         //No I18N\n\t   return false;\n\t  }\n\t}\n\t");
/* 2455 */               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2456 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2460 */           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2461 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */           }
/*      */           
/* 2464 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2465 */           out.write("\n\ttempviewid=viewid;\n\t");
/* 2466 */           if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*      */             return;
/* 2468 */           out.write("\n\turl='/GraphicalView.do';\n\thttp3=getHTTPObject();\n\thttp3.onreadystatechange = getSaveStatus;\n\thttp3.open(\"POST\", url, true);\n\thttp3.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded\");\n\tif(isHtml){\n\t\tvar jsonForSave = JSON.stringify(getJITJSON());\n\t\tvar nodesForSave = getJITNodes();\n\t\tvar pos = getCanvasPos();\n\t\thttp3.send(\"method=saveState&haid=");
/* 2469 */           out.print(haid_flash);
/* 2470 */           out.write("\"+tempDisplayname+\"&currentime=\"+");
/* 2471 */           out.print(System.currentTimeMillis());
/* 2472 */           out.write("+\"&viewid=\"+tempviewid+\"&nodesForSave=\"+nodesForSave+\"&isHtml=\"+isHtml+\"&toSave=true&xcanvas=\"+pos.x+\"&ycanvas=\"+pos.y);//No I18N\n\t}else{\n\t\t//var savexml=escape(getXML());\n\t\tvar savexml= encodeURIComponent(getXML());\n\t\thttp3.send('method=saveState&haid=");
/* 2473 */           out.print(haid_flash);
/* 2474 */           out.write("'+tempDisplayname+'&viewid='+tempviewid+'&xml='+savexml+'&isHtml='+isHtml);//No I18N\n\t}\n}\n\nfunction getSaveStatus()\n{\nif(http3.readyState == 4)\n {\n// alert(\"saved the status\"+ http3.status);\n       if( http3.status == 200)\n\t{\n\t\t//alert(\"saved the status\");\n\n\t       var name=\"");
/* 2475 */           out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/* 2476 */           out.write("\";\n\t       var nl = http3.responseXML.getElementsByTagName('SAVEINFO');\n\t        for( var i = 0; i < nl.length; i++ )\n\t        {\n                  var nli = nl.item( i );\n                  var viewidaftersaving = nli.getElementsByTagName( 'VIEWID' );\n                  var nametag=nli.getElementsByTagName( 'VIEWNAME' );\n\n\t       \t viewid=viewidaftersaving.item(0).firstChild.nodeValue;\n\t       \t name=nametag.item(0).firstChild.nodeValue;\n                }\n                    ");
/* 2477 */           if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */             return;
/* 2479 */           out.write("\n\n\t\tdocument.getElementById(\"saveresult\").innerHTML='");
/* 2480 */           out.print(FormatUtil.getString("am.webclient.flashview.save.success.text"));
/* 2481 */           out.write("';\n\t\tdocument.getElementById(\"savediv\").style.display='block';\n\t\tstartHideFade(\"savediv\",0.005);\n\t}\n  }\n}\n\nfunction resetDesign()\n{\n\n\t   if(isXMLLoaded!='true' && !isHtml)\n\t   {\n\t   alert(\"Please wait for the Flash to load.\");\n\t   return;\n\t   }\n\t   if(confirm('");
/* 2482 */           out.print(FormatUtil.getString("am.webclient.flashview.resetdesign.confirm.text"));
/* 2483 */           out.write("'))\n\t   {\n\t   \n\t\tvar url = \"method=resetDesign&haid=");
/* 2484 */           out.print(haid_flash);
/* 2485 */           out.write("&viewid=\"+viewid+\"&isHtml=\"+isHtml;//No i18n\n\t\tif(isHtml){\n\t\t\t$.ajax(\n\t\t\t{\n\t\t\t\t\ttype:\"POST\", //No I18N\n\t\t\t\t\turl:\"/GraphicalView.do\", //No I18N\n\t\t\t\t\tdata:url,\n\t\t\t\t\tsuccess: function(response)\n\t\t\t\t\t{      \n\t\t\t\t\t   location.reload();\n\t\t\t\t\t}\n\t\t\t});\n\t\t}else{\n\t\t\turl =\"/GraphicalView.do?\" + url;// No I18n\n\t\t\tgetSWF(\"monitorreport\").setXMLUrl(url);// No I18n\n\t\t}\n\t   }\n}\n\nfunction getViewType()\n{\n\treturn getSWF(\"monitorreport\").getCurrentView();\n}\n\nfunction onXMLLoaded()\n{\n  \tisXMLLoaded=\"true\";\n}\n\nfunction refreshCurrentView()\n{\n        ");
/*      */           
/* 2487 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2488 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2489 */           _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2490 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2491 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/* 2493 */               out.write("\n        ");
/*      */               
/* 2495 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2496 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2497 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */               
/* 2499 */               _jspx_th_c_005fwhen_005f0.setTest("${ FlashForm.showTopLevelMgs==true }");
/* 2500 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2501 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                 for (;;) {
/* 2503 */                   out.write(10);
/* 2504 */                   out.write(9);
/* 2505 */                   if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 2506 */                     out.write("\n        history.go();\n\t");
/*      */                   }
/* 2508 */                   out.write("\n        ");
/* 2509 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2510 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2514 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2515 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */               }
/*      */               
/* 2518 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2519 */               out.write("\n        ");
/*      */               
/* 2521 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2522 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2523 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2524 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2525 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                 for (;;) {
/* 2527 */                   out.write("\n\t\t if(isHtml){\n\t\t\trefreshResponse();\t\t\t\n\t\t }else{\n\t\t\tvar rand_number = Math.random();\n\t\t\tvar url = \"/GraphicalView.do?method=getMGXML&haid=");
/* 2528 */                   out.print(haid_flash);
/* 2529 */                   out.write("&viewid=\"+viewid+\"&rand_number=\"+rand_number+\"&isHtml=\"+isHtml; // No i18N\n\t\t\tgetSWF(\"monitorreport\").setXMLUrl(url);\n\t\t }\n\t\t \n        ");
/* 2530 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2531 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2535 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2536 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */               }
/*      */               
/* 2539 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2540 */               out.write("\n        ");
/* 2541 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2542 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2546 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2547 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/* 2550 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2551 */           out.write("\n}\n\n\n\nfunction popOut()\n{\n\tvar popoutwindow=window.open('/GraphicalView.do?method=popUp&haid=");
/* 2552 */           out.print(haid_flash);
/* 2553 */           out.write("&isPopUp=true&viewid='+viewid,'FlashView','scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25');\n\tpopoutwindow.focus();\n        disableTimer();\n}\n\n\nfunction editProperties()\n{\n\n");
/*      */           
/* 2555 */           IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2556 */           _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2557 */           _jspx_th_c_005fif_005f6.setParent(null);
/*      */           
/* 2559 */           _jspx_th_c_005fif_005f6.setTest("${ FlashForm.fromMonitorTab == true}");
/* 2560 */           int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2561 */           if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */             for (;;) {
/* 2563 */               out.write("\nif(document.FlashForm.selectedView.value=='-10' && ! document.FlashForm.selectedView.length==1)\n   {\n    alert(\"'");
/* 2564 */               out.print(FormatUtil.getString("am.webclient.flashview.viewnotselected.text"));
/* 2565 */               out.write("'\");\n    return;\n   }\n");
/* 2566 */               int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2567 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2571 */           if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2572 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */           }
/*      */           
/* 2575 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2576 */           out.write(10);
/*      */           
/* 2578 */           IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2579 */           _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2580 */           _jspx_th_c_005fif_005f7.setParent(null);
/*      */           
/* 2582 */           _jspx_th_c_005fif_005f7.setTest("${empty param.isPopUp}");
/* 2583 */           int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2584 */           if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */             for (;;) {
/* 2586 */               out.write(10);
/* 2587 */               out.write(9);
/*      */               
/* 2589 */               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2590 */               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2591 */               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f7);
/* 2592 */               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2593 */               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                 for (;;) {
/* 2595 */                   out.write(10);
/* 2596 */                   out.write(9);
/*      */                   
/* 2598 */                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2599 */                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2600 */                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                   
/* 2602 */                   _jspx_th_c_005fwhen_005f1.setTest("${FlashForm.fromWhere==\"hometab\"}");
/* 2603 */                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2604 */                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                     for (;;) {
/* 2606 */                       out.write("\n\tfnOpenNewWindow('/GraphicalView.do?method=showEditView&fromWhere=hometab&viewid='+viewid+'&haid=");
/* 2607 */                       out.print(haid_flash);
/* 2608 */                       out.write("','Flash View','scrollbars=1,resizable=1,width=1000,height=650,left=50,top=25,screenX=250,screenY=25');\n\t");
/* 2609 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2610 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2614 */                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2615 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                   }
/*      */                   
/* 2618 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2619 */                   out.write("\n        ");
/*      */                   
/* 2621 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2622 */                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2623 */                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2624 */                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2625 */                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                     for (;;) {
/* 2627 */                       out.write("\n\t        ");
/*      */                       
/* 2629 */                       ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2630 */                       _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2631 */                       _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/* 2632 */                       int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2633 */                       if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                         for (;;) {
/* 2635 */                           out.write("\n\t     \t\t");
/*      */                           
/* 2637 */                           WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2638 */                           _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2639 */                           _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                           
/* 2641 */                           _jspx_th_c_005fwhen_005f2.setTest("${not empty param.customizetab && param.customizetab==true}");
/* 2642 */                           int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2643 */                           if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                             for (;;) {
/* 2645 */                               out.write("\n\t     \t\tfnOpenNewWindow('/GraphicalView.do?method=showEditView&viewid='+viewid+'&haid=");
/* 2646 */                               out.print(haid_flash);
/* 2647 */                               out.write("&customizetab=true','Flash View','scrollbars=1,resizable=1,width=1000,height=650,left=50,top=25,screenX=250,screenY=25'); // NO I18N\n\t     \t\t");
/* 2648 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2649 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2653 */                           if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2654 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                           }
/*      */                           
/* 2657 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2658 */                           out.write("\n\t     \t\t");
/*      */                           
/* 2660 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2661 */                           _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2662 */                           _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2663 */                           int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2664 */                           if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                             for (;;) {
/* 2666 */                               out.write("\n        \t\t\tfnOpenNewWindow('/GraphicalView.do?method=showEditView&viewid='+viewid+'&haid=");
/* 2667 */                               out.print(haid_flash);
/* 2668 */                               out.write("','Flash View','scrollbars=1,resizable=1,width=1000,height=650,left=50,top=25,screenX=250,screenY=25');\n        \t\t");
/* 2669 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2670 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2674 */                           if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2675 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                           }
/*      */                           
/* 2678 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2679 */                           out.write("\n        \t");
/* 2680 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2681 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2685 */                       if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2686 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                       }
/*      */                       
/* 2689 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2690 */                       out.write("\n        ");
/* 2691 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2692 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2696 */                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2697 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                   }
/*      */                   
/* 2700 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2701 */                   out.write("\n    ");
/* 2702 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2703 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2707 */               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2708 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */               }
/*      */               
/* 2711 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2712 */               out.write(10);
/* 2713 */               int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2714 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2718 */           if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2719 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */           }
/*      */           
/* 2722 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2723 */           out.write(10);
/*      */           
/* 2725 */           IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2726 */           _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2727 */           _jspx_th_c_005fif_005f8.setParent(null);
/*      */           
/* 2729 */           _jspx_th_c_005fif_005f8.setTest("${ param.isPopUp}");
/* 2730 */           int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2731 */           if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */             for (;;) {
/* 2733 */               out.write("\n\tfnOpenNewWindow('/GraphicalView.do?method=showEditView&viewid='+viewid+'&haid=");
/* 2734 */               out.print(haid_flash);
/* 2735 */               out.write("&isPopUp=true','Flash View','scrollbars=1,resizable=1,width=1000,height=650,left=50,top=25,screenX=250,screenY=25');\n");
/* 2736 */               int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2737 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2741 */           if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2742 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */           }
/*      */           
/* 2745 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2746 */           out.write("\n}\n\nfunction createNewView()\n{\n");
/*      */           
/* 2748 */           IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2749 */           _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2750 */           _jspx_th_c_005fif_005f9.setParent(null);
/*      */           
/* 2752 */           _jspx_th_c_005fif_005f9.setTest("${empty param.isPopUp}");
/* 2753 */           int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2754 */           if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */             for (;;) {
/* 2756 */               out.write(10);
/*      */               
/* 2758 */               ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2759 */               _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2760 */               _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f9);
/* 2761 */               int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2762 */               if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                 for (;;) {
/* 2764 */                   out.write(10);
/*      */                   
/* 2766 */                   WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2767 */                   _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2768 */                   _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                   
/* 2770 */                   _jspx_th_c_005fwhen_005f3.setTest("${FlashForm.fromWhere==\"hometab\"}");
/* 2771 */                   int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2772 */                   if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                     for (;;) {
/* 2774 */                       out.write("\nfnOpenNewWindow('/GraphicalView.do?method=createNewView&fromWhere=hometab&viewid='+viewid+'&haid=");
/* 2775 */                       out.print(haid_flash);
/* 2776 */                       out.write("&isHtml='+isHtml,'Flash View','scrollbars=1,resizable=1,width=900,height=650,left=50,top=25,screenX=250,screenY=25');//No I18N\n");
/* 2777 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2778 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2782 */                   if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2783 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                   }
/*      */                   
/* 2786 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2787 */                   out.write(10);
/*      */                   
/* 2789 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2790 */                   _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2791 */                   _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 2792 */                   int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2793 */                   if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                     for (;;) {
/* 2795 */                       out.write("\nfnOpenNewWindow('/GraphicalView.do?method=createNewView&viewid='+viewid+'&haid=");
/* 2796 */                       out.print(haid_flash);
/* 2797 */                       out.write("&isHtml='+isHtml ,'Flash View','scrollbars=1,resizable=1,width=900,height=650,left=50,top=25,screenX=250,screenY=25');//No I18N\n");
/* 2798 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2799 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2803 */                   if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2804 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                   }
/*      */                   
/* 2807 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2808 */                   out.write(10);
/* 2809 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2810 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2814 */               if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2815 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */               }
/*      */               
/* 2818 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2819 */               out.write(10);
/* 2820 */               int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2821 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2825 */           if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2826 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */           }
/*      */           
/* 2829 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2830 */           out.write(10);
/*      */           
/* 2832 */           IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2833 */           _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2834 */           _jspx_th_c_005fif_005f10.setParent(null);
/*      */           
/* 2836 */           _jspx_th_c_005fif_005f10.setTest("${ param.isPopUp}");
/* 2837 */           int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2838 */           if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */             for (;;) {
/* 2840 */               out.write("\nfnOpenNewWindow('/GraphicalView.do?method=createNewView&viewid='+viewid+'&haid=");
/* 2841 */               out.print(haid_flash);
/* 2842 */               out.write("&isPopUp=true&isHtml='+isHtml,'Flash View','scrollbars=1,resizable=1,width=900,height=650,left=50,top=25,screenX=250,screenY=25');//No I18N\n");
/* 2843 */               int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2844 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2848 */           if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2849 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */           }
/*      */           
/* 2852 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2853 */           out.write("\n}\nvar noOfRequests=0;\nfunction flashviewOnLoad()\n{\nsetTitle();\nscheduleResfresh();\n}\nfunction scheduleResfresh()\n{\n  ");
/* 2854 */           if (_jspx_meth_c_005fchoose_005f4(_jspx_page_context))
/*      */             return;
/* 2856 */           out.write("\n}\n\nfunction autoRefreshStatus()\n{\n        //console.log(\"auto refreshing\");\n\t\tif(isHtml){\n\t\t\tsetRecentStatusForJIT(");
/* 2857 */           out.print(haid_flash);
/* 2858 */           out.write(", viewid);\n\t\t\tscheduleResfresh();\n\t\t}else{\n\t\t\tnoOfRequests=noOfRequests+1; \t \t\n\t\t\turl='/GraphicalView.do?method=getLatestStatus&haid=");
/* 2859 */           out.print(haid_flash);
/* 2860 */           out.write("&viewid='+viewid+'&noOfRequests='+noOfRequests; \t \t\n\t\t\t\thttp1=getHTTPObject(); \t \t\n\t\t\thttp1.onreadystatechange = getLatestStatus; \t \t\n\t\t\t\thttp1.open(\"GET\", url, true); \t \t\n\t\t\thttp1.send(null); \n\t\t}\n}\n\nfunction  getLatestStatus()\n{\n     if(http1.readyState == 4)\n     {\n        try{\n\n\t\tif(http1.status == 200)\n\t\t{\n\t\t  document.getElementById(\"productdown\").style.display =\"none\";\n\t\t\t  var latestxml = http1.responseText;\n\t\t\t  getSWF(\"monitorreport\").updateMonitors(latestxml);\n\t\t}\n\t\telse\n\t\t{\n\t\t  document.getElementById(\"productdown\").style.display =\"block\";\n\n\t\t}\n\t   }\n\t\tcatch(e)\n\t      {\n\t\t   try{\n\t\t   if(http1.status != 200)\n\t\t   {\n\t\t     document.getElementById(\"productdown\").style.display =\"block\";\n\t\t   }\n\t\t   }\n\t\t   catch(e)\n\t\t   {\n\t\t    document.getElementById(\"productdown\").style.display =\"block\";\n\t\t   }\n\t      }\n        scheduleResfresh();\n     }\n}\n\nfunction onfnSubmit()\n{\n\n  var viewid1=document.FlashForm.selectedView.value;\n  if(document.FlashForm.selectedView.value=='-10')\n   {\n    return;\n   }\n  ");
/*      */           
/* 2862 */           ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2863 */           _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2864 */           _jspx_th_c_005fchoose_005f5.setParent(null);
/* 2865 */           int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2866 */           if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */             for (;;) {
/* 2868 */               out.write(10);
/* 2869 */               out.write(32);
/* 2870 */               out.write(32);
/*      */               
/* 2872 */               WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2873 */               _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2874 */               _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */               
/* 2876 */               _jspx_th_c_005fwhen_005f5.setTest("${empty param.isPopUp}");
/* 2877 */               int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2878 */               if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                 for (;;) {
/* 2880 */                   out.write("\n            if(viewid1==viewid && '0'!='");
/* 2881 */                   out.print(haid_flash);
/* 2882 */                   out.write("')\n            {\n               location.href=\"/showapplication.do?haid=");
/* 2883 */                   out.print(haid_flash);
/* 2884 */                   out.write("&method=showApplication&selectM=flashview\";\n            }\n            else\n            {\n                ");
/*      */                   
/* 2886 */                   ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2887 */                   _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 2888 */                   _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_c_005fwhen_005f5);
/* 2889 */                   int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 2890 */                   if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                     for (;;) {
/* 2892 */                       out.write("\n                  ");
/* 2893 */                       if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/*      */                         return;
/* 2895 */                       out.write("\n\t    \t  ");
/*      */                       
/* 2897 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2898 */                       _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 2899 */                       _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f6);
/* 2900 */                       int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 2901 */                       if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                         for (;;) {
/* 2903 */                           out.write("\n\t    \t  \n\t    \t \t");
/*      */                           
/* 2905 */                           ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2906 */                           _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 2907 */                           _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_c_005fotherwise_005f5);
/* 2908 */                           int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 2909 */                           if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                             for (;;) {
/* 2911 */                               out.write("\n\t     \t\t\t");
/*      */                               
/* 2913 */                               WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2914 */                               _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 2915 */                               _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                               
/* 2917 */                               _jspx_th_c_005fwhen_005f7.setTest("${(not empty param.customizetab && param.customizetab==true)}");
/* 2918 */                               int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 2919 */                               if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                 for (;;) {
/* 2921 */                                   out.write("\n\t     \t\t\t\tlocation.href=\"/GraphicalView.do?method=popUp&customizetab=true&haid=");
/* 2922 */                                   out.print(haid_flash);
/* 2923 */                                   out.write("&viewid=\"+viewid1;\n\t     \t\t\t");
/* 2924 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 2925 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2929 */                               if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 2930 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                               }
/*      */                               
/* 2933 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2934 */                               out.write("\n\t     \t\t\t");
/*      */                               
/* 2936 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2937 */                               _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 2938 */                               _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f7);
/* 2939 */                               int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 2940 */                               if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                 for (;;) {
/* 2942 */                                   out.write("\n\t     \t\t\t\t/* Individual monitor group's flashview pops out of the page,which need to be restricted. */\n\t     \t\t\t\tvar locationStr = parent.window.document.location.href;\n\t     \t\t\t\tif(locationStr.indexOf(\"method=showApplication\") > -1) \n\t     \t\t\t\t{\n\t     \t\t\t\t\tlocation.href=\"/showapplication.do?&method=showApplication&haid=");
/* 2943 */                                   out.print(haid_flash);
/* 2944 */                                   out.write("&viewid=\"+viewid1;\n\t     \t\t\t\t}\n\t     \t\t\t\telse\n\t     \t\t\t\t{\n\t     \t\t\t\t\tvar windowpopup= window.open('/GraphicalView.do?method=popUp&haid=0&isPopUp=true&viewid='+viewid1,'FlashView','scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25');\n\t      \t\t    \t    windowpopup.focus();\t\n\t     \t\t\t\t}\n      \t\t\t\t");
/* 2945 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 2946 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2950 */                               if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 2951 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                               }
/*      */                               
/* 2954 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 2955 */                               out.write("\n      \t\t\t");
/* 2956 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 2957 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2961 */                           if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 2962 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                           }
/*      */                           
/* 2965 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 2966 */                           out.write("\n\t    \t   ");
/* 2967 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 2968 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2972 */                       if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 2973 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                       }
/*      */                       
/* 2976 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 2977 */                       out.write("\n\t    \t");
/* 2978 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 2979 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2983 */                   if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 2984 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                   }
/*      */                   
/* 2987 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 2988 */                   out.write("\n\t    }\n  ");
/* 2989 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2990 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2994 */               if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2995 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */               }
/*      */               
/* 2998 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2999 */               out.write(10);
/* 3000 */               out.write(32);
/* 3001 */               out.write(32);
/*      */               
/* 3003 */               OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3004 */               _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 3005 */               _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f5);
/* 3006 */               int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 3007 */               if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                 for (;;) {
/* 3009 */                   out.write("\n            location.href=\"/GraphicalView.do?method=popUp&haid=");
/* 3010 */                   out.print(haid_flash);
/* 3011 */                   out.write("&isPopUp=");
/* 3012 */                   if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/*      */                     return;
/* 3014 */                   out.write("&viewid=\"+viewid1;\n  ");
/* 3015 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3016 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3020 */               if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3021 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */               }
/*      */               
/* 3024 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3025 */               out.write(10);
/* 3026 */               out.write(32);
/* 3027 */               out.write(32);
/* 3028 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3029 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3033 */           if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3034 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */           }
/*      */           
/* 3037 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3038 */           out.write("\n\n\n}\n\n\nfunction setAsDefaultView()\n{\n   ");
/*      */           
/* 3040 */           IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3041 */           _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3042 */           _jspx_th_c_005fif_005f11.setParent(null);
/*      */           
/* 3044 */           _jspx_th_c_005fif_005f11.setTest("${ FlashForm.fromMonitorTab == true}");
/* 3045 */           int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3046 */           if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */             for (;;) {
/* 3048 */               out.write("\n   if(document.FlashForm.selectedView.value=='-10')\n      {\n       alert('");
/* 3049 */               out.print(FormatUtil.getString("am.webclient.flashview.viewnotselected.text"));
/* 3050 */               out.write("');\n       return;\n      }\n");
/* 3051 */               int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3052 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3056 */           if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3057 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */           }
/*      */           
/* 3060 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3061 */           out.write("\n   var viewid1=document.FlashForm.selectedView.value;\n   ");
/*      */           
/* 3063 */           ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3064 */           _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 3065 */           _jspx_th_c_005fchoose_005f8.setParent(null);
/* 3066 */           int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 3067 */           if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */             for (;;) {
/* 3069 */               out.write("\n   ");
/*      */               
/* 3071 */               WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3072 */               _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3073 */               _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */               
/* 3075 */               _jspx_th_c_005fwhen_005f8.setTest("${FlashForm.fromWhere==\"hometab\"}");
/* 3076 */               int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3077 */               if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                 for (;;) {
/* 3079 */                   out.write("\n   location.href=\"/GraphicalView.do?method=setAsDefaultView&fromWhere=hometab&haid=");
/* 3080 */                   out.print(haid_flash);
/* 3081 */                   out.write("&viewid=\"+viewid1;\n   ");
/* 3082 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3083 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3087 */               if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3088 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */               }
/*      */               
/* 3091 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3092 */               out.write("\n   ");
/*      */               
/* 3094 */               OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3095 */               _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 3096 */               _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 3097 */               int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 3098 */               if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                 for (;;) {
/* 3100 */                   out.write(10);
/* 3101 */                   out.write(9);
/* 3102 */                   out.write(9);
/*      */                   
/* 3104 */                   ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3105 */                   _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 3106 */                   _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_c_005fotherwise_005f8);
/* 3107 */                   int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 3108 */                   if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                     for (;;) {
/* 3110 */                       out.write("\n\t\t\t");
/*      */                       
/* 3112 */                       WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3113 */                       _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 3114 */                       _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                       
/* 3116 */                       _jspx_th_c_005fwhen_005f9.setTest("${not empty param.customizetab && param.customizetab==true}");
/* 3117 */                       int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 3118 */                       if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                         for (;;) {
/* 3120 */                           out.write("\n\t\t\t\tlocation.href=\"/GraphicalView.do?method=setAsDefaultView&customizetab=true&haid=");
/* 3121 */                           out.print(haid_flash);
/* 3122 */                           out.write("&viewid=\"+viewid1;\n\t\t\t");
/* 3123 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 3124 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3128 */                       if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 3129 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                       }
/*      */                       
/* 3132 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3133 */                       out.write("\n\t\t\t");
/*      */                       
/* 3135 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3136 */                       _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 3137 */                       _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 3138 */                       int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 3139 */                       if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                         for (;;) {
/* 3141 */                           out.write("\n\t\t\t\tlocation.href=\"/GraphicalView.do?method=setAsDefaultView&haid=");
/* 3142 */                           out.print(haid_flash);
/* 3143 */                           out.write("&viewid=\"+viewid1;\n\t\t\t");
/* 3144 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 3145 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3149 */                       if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 3150 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                       }
/*      */                       
/* 3153 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 3154 */                       out.write(10);
/* 3155 */                       out.write(9);
/* 3156 */                       out.write(9);
/* 3157 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 3158 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3162 */                   if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 3163 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                   }
/*      */                   
/* 3166 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 3167 */                   out.write(10);
/* 3168 */                   out.write(9);
/* 3169 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 3170 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3174 */               if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 3175 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */               }
/*      */               
/* 3178 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 3179 */               out.write("\n   ");
/* 3180 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 3181 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3185 */           if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 3186 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */           }
/*      */           
/* 3189 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 3190 */           out.write("\n}\n\n\nfunction deleteView()\n{\n ");
/*      */           
/* 3192 */           IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3193 */           _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3194 */           _jspx_th_c_005fif_005f12.setParent(null);
/*      */           
/* 3196 */           _jspx_th_c_005fif_005f12.setTest("${ FlashForm.fromMonitorTab == true}");
/* 3197 */           int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3198 */           if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */             for (;;) {
/* 3200 */               out.write("\n if(document.FlashForm.selectedView.value=='-10')\n    {\n     alert('");
/* 3201 */               out.print(FormatUtil.getString("am.webclient.flashview.viewnotselected.text"));
/* 3202 */               out.write("');\n     return;\n    }\n");
/* 3203 */               int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3204 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3208 */           if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3209 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */           }
/*      */           
/* 3212 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3213 */           out.write("\n  if(confirm('");
/* 3214 */           out.print(FormatUtil.getString("am.webclient.flashview.deleteview.confirm.text"));
/* 3215 */           out.write("'))\n\t{\n\t var viewid1=document.FlashForm.selectedView.value;\n        ");
/*      */           
/* 3217 */           ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3218 */           _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 3219 */           _jspx_th_c_005fchoose_005f10.setParent(null);
/* 3220 */           int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 3221 */           if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */             for (;;) {
/* 3223 */               out.write("\n\t\t\t");
/*      */               
/* 3225 */               WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3226 */               _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 3227 */               _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */               
/* 3229 */               _jspx_th_c_005fwhen_005f10.setTest("${FlashForm.fromWhere==\"hometab\"}");
/* 3230 */               int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 3231 */               if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                 for (;;) {
/* 3233 */                   out.write("\n         \t\tdocument.location.href=\"/GraphicalView.do?method=deleteView&fromWhere=hometab&haid=");
/* 3234 */                   out.print(haid_flash);
/* 3235 */                   out.write("&viewid=\"+viewid1;\n        \t");
/* 3236 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 3237 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3241 */               if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 3242 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */               }
/*      */               
/* 3245 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3246 */               out.write("\n        \t");
/*      */               
/* 3248 */               OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3249 */               _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 3250 */               _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 3251 */               int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 3252 */               if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                 for (;;) {
/* 3254 */                   out.write("\n\t        \t");
/*      */                   
/* 3256 */                   ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3257 */                   _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 3258 */                   _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_c_005fotherwise_005f10);
/* 3259 */                   int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 3260 */                   if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                     for (;;) {
/* 3262 */                       out.write("\n\t\t \t\t\t");
/*      */                       
/* 3264 */                       WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3265 */                       _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 3266 */                       _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                       
/* 3268 */                       _jspx_th_c_005fwhen_005f11.setTest("${not empty param.customizetab && param.customizetab==true}");
/* 3269 */                       int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 3270 */                       if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                         for (;;) {
/* 3272 */                           out.write("\n\t\t \t\t\t\tdocument.location.href=\"/GraphicalView.do?method=deleteView&customizetab=true&haid=");
/* 3273 */                           out.print(haid_flash);
/* 3274 */                           out.write("&viewid=\"+viewid1;\n\t\t \t\t\t");
/* 3275 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 3276 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3280 */                       if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 3281 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                       }
/*      */                       
/* 3284 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 3285 */                       out.write("\n\t\t \t\t\t");
/*      */                       
/* 3287 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3288 */                       _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 3289 */                       _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 3290 */                       int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 3291 */                       if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                         for (;;) {
/* 3293 */                           out.write("\n\t\t \t\t\t\tdocument.location.href=\"/GraphicalView.do?method=deleteView&haid=");
/* 3294 */                           out.print(haid_flash);
/* 3295 */                           out.write("&viewid=\"+viewid1;\n\t\t\t\t\t");
/* 3296 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 3297 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3301 */                       if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 3302 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                       }
/*      */                       
/* 3305 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 3306 */                       out.write("\n\t\t\t\t");
/* 3307 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 3308 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3312 */                   if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 3313 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                   }
/*      */                   
/* 3316 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 3317 */                   out.write("\n        \t");
/* 3318 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 3319 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3323 */               if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 3324 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */               }
/*      */               
/* 3327 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 3328 */               out.write("\n        ");
/* 3329 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 3330 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3334 */           if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 3335 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */           }
/*      */           
/* 3338 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 3339 */           out.write("\n\t}\n\telse\n\t{\n\treturn;\n\t}\n\n}\n\nfunction showExternalLink()\n{\ndocument.getElementById(\"txtbx\").innerHTML='<textarea name=\"extlink\" rows=\"4\" cols=\"50\">&lt;iframe src=\"");
/* 3340 */           out.print(request.getScheme());
/* 3341 */           out.write(58);
/* 3342 */           out.write(47);
/* 3343 */           out.write(47);
/* 3344 */           out.print(hostname);
/* 3345 */           out.write("/GraphicalView.do?publishview=true&amp;method=popUp&amp;haid=");
/* 3346 */           out.print(haid_flash);
/* 3347 */           out.write("&amp;viewid='+viewid+'\" scrolling=\"no\" align=\"center\" height=\"400\" width=\"700\" border=\"0\" frameborder=\"0\"&gt; &lt;/iframe&gt;</textarea>'  ");
/* 3348 */           out.write("\nshowDialog(document.getElementById(\"externallink\").innerHTML,'position=relative,srcElement=settingsAnchor,left=30,top=-10,title=");
/* 3349 */           out.print(FormatUtil.getString("am.webclient.flashview.makepublic.text"));
/* 3350 */           out.write("' );\n}\n\n\nfunction showSwitchOptions() {\n       \tcreateMenu(\"insertMenuContainer\", { inputType : \"html\" });\n        showMenu(\"insertMenu\");\n}\n\n function setTitle() {\n         var title=\"\";\n         ");
/* 3351 */           if (_jspx_meth_c_005fif_005f13(_jspx_page_context))
/*      */             return;
/* 3353 */           out.write("\n         title=title+\"");
/* 3354 */           if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*      */             return;
/* 3356 */           out.write("\";\n         document.title=title;\n      }\n\n function disableTimer()\n {\n clearTimeout(timeOutHandle);\n document.getElementById(\"popupdiv\").style.display=\"block\";\n }\n\nfunction alertxml()\n{\nalert(getXML());\n}\n\nfunction switchToOtherView(haid){\n\ttempviewid=viewid;\n\t");
/* 3357 */           if (_jspx_meth_c_005fif_005f14(_jspx_page_context))
/*      */             return;
/* 3359 */           out.write("\n\tif(isHtml){\n\t\tif(haid === \"0\") {\n\t\t\tdocument.location.href=\"/MyPage.do?method=viewDashBoard&forpage=1&selectedpageid=\"+haid+\"&selectedTab=1&isHtml=false&isSwitchToOtherView=true\";\n\t\t}else{\n\t\t\tdocument.location.href=\"/showapplication.do?haid=\"+haid+\"&method=showApplication&selectM=flashview&isHtml=false&isSwitchToOtherView=true\";\n\t\t}\n                               \n\t}else{\n\t\tisHtml = true;\n\t\t$(\"#swfHolder\").remove();\n\t\t");
/* 3360 */           if (_jspx_meth_c_005fset_005f1(_jspx_page_context))
/*      */             return;
/* 3362 */           out.write("\n\t\t$(\"#graphHolder\").html('<td id=\"monitorReportInHtml\"  width=\"100%\" height=\"100%\" > <div id=\"monitorReportInHtmlJSP\"></div></td>');\n\t\t$(\"#monitorReportInHtmlJSP\").load(\"/jsp/ShowRelationshipView.jsp?&haid=\"+haid);\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n\t}\n}\n\n</script>\n<div id=\"savediv\" style=\"display: none;\">\n            <table border=\"0\" width=\"100%\">\n              <tr align=\"center\">\n                <td class=\"bodytextbold\"><span id=\"saveresult\" style=\"font-weight: bold; color :#000000; font-size: 11px;\"></span></td>\n              </tr>\n            </table>\n          </div>\n<div id=\"popupdiv\" style=\"display: none;\">\n            <table border=\"0\" width=\"100%\">\n              <tr align=\"center\">\n                <td class=\"bodytextbold\"><span id=\"popupdivtext\" style=\"font-weight: bold; color :#000000; font-size: 11px;\">");
/* 3363 */           out.print(FormatUtil.getString("am.webclient.flashview.popupopen.text", new String[] { "history.go();" }));
/* 3364 */           out.write("</span></td>\n              </tr>\n            </table>\n          </div>\n<div id=\"productdown\" style=\"display: none;\">\n<table width=60% align=\"center\" valign=\"center\">\n  <tr>\n\t  <td width=\"100%\" height=\"170\">");
/* 3365 */           out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3366 */           out.write("\n\t  <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" height=\"100\">\n\t\t  <tr>\n\t\t      <td  width=\"15%\" align=\"center\" valign=\"top\"><img src=\"/images/icon_cross.gif\" alt=\"Icon\" vspace=\"5\"></td>\n\t\t      <td  width=\"85%\">\n\t\t      <table width=100% align=\"center\" valign=\"center\">\n\t\t      <tr>\n\t\t      <td >\n\t\t       <span style=\"font-size:20px; color :#FF0000; font-weight:bold;\" >");
/* 3367 */           out.print(FormatUtil.getString("am.webclient.product.notreachable.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name"), com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/* 3368 */           out.write("</span>\n\t\t      </td>\n\t\t      </tr>\n\t\t      <tr>\n\t\t      <td BACKGROUND=\"/images/spacer.gif\">&nbsp;\n\t\t      </td>\n\t\t      </tr>\n\t\t      </table>\n\t\t      </td>\n\t\t  </tr>\n\t  </td>\n  </tr>\n  </table>");
/* 3369 */           out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 3370 */           out.write("</td></tr></table>\n  </div>\n\n\n\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"bsm-border\">\n");
/*      */           
/* 3372 */           if ((request.getParameter("hideControls") == null) || (!request.getParameter("hideControls").equals("true"))) {
/* 3373 */             out.write("\n   <tr>\n      <td valign=\"top\" height=\"32\">\n       <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n               <tr>\n                 <td  class=\"tp-header-tile\"  height=\"32\"  >\n\n                         <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" height=\"32\" border=\"0\">\n                          ");
/*      */             
/* 3375 */             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 3376 */             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3377 */             _jspx_th_html_005fform_005f0.setParent(null);
/*      */             
/* 3379 */             _jspx_th_html_005fform_005f0.setAction("/GraphicalView.do");
/* 3380 */             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3381 */             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */               for (;;) {
/* 3383 */                 out.write("\n                           <tr>\n                               ");
/*      */                 
/* 3385 */                 IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3386 */                 _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3387 */                 _jspx_th_c_005fif_005f15.setParent(_jspx_th_html_005fform_005f0);
/*      */                 
/* 3389 */                 _jspx_th_c_005fif_005f15.setTest("${  empty publishview}");
/* 3390 */                 int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3391 */                 if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                   for (;;) {
/* 3393 */                     out.write("\n                               ");
/*      */                     
/* 3395 */                     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3396 */                     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3397 */                     _jspx_th_c_005fif_005f16.setParent(_jspx_th_c_005fif_005f15);
/*      */                     
/* 3399 */                     _jspx_th_c_005fif_005f16.setTest("${not empty pageContext.request.remoteUser}");
/* 3400 */                     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3401 */                     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                       for (;;) {
/* 3403 */                         out.write("\n                               ");
/*      */                         
/* 3405 */                         ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3406 */                         _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 3407 */                         _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_c_005fif_005f16);
/* 3408 */                         int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 3409 */                         if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                           for (;;) {
/* 3411 */                             out.write("\n\t\t\t\t       ");
/*      */                             
/* 3413 */                             WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3414 */                             _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 3415 */                             _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                             
/* 3417 */                             _jspx_th_c_005fwhen_005f12.setTest("${ FlashForm.fromMonitorTab != true and empty param.isPopUp}");
/* 3418 */                             int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 3419 */                             if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                               for (;;) {
/* 3421 */                                 out.write("\n\t\t\t\t       \t   <td width=\"6%\" valign=\"middle\">\n\t\t\t\t       \t   <span class=\"bodytext\">&nbsp;");
/* 3422 */                                 out.print(FormatUtil.getString("am.webclient.flashview.relatedviews.text"));
/* 3423 */                                 out.write(" :</span>\n\t\t\t\t           </td>\n\t\t\t\t\t   <td width=\"1%\" height=\"32\"  align=\"left\" valign=\"middle\" >\n\t\t\t\t       ");
/* 3424 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 3425 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3429 */                             if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 3430 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                             }
/*      */                             
/* 3433 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3434 */                             out.write("\n\t\t\t\t       ");
/* 3435 */                             if (_jspx_meth_c_005fotherwise_005f12(_jspx_th_c_005fchoose_005f12, _jspx_page_context))
/*      */                               return;
/* 3437 */                             out.write("\n                               ");
/* 3438 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 3439 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3443 */                         if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 3444 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                         }
/*      */                         
/* 3447 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 3448 */                         out.write("\n\n                               ");
/*      */                         
/* 3450 */                         ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3451 */                         _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 3452 */                         _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_c_005fif_005f16);
/* 3453 */                         int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 3454 */                         if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                           for (;;) {
/* 3456 */                             out.write("\n\t\t\t\t       ");
/*      */                             
/* 3458 */                             WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3459 */                             _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 3460 */                             _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                             
/* 3462 */                             _jspx_th_c_005fwhen_005f13.setTest("${ FlashForm.fromMonitorTab == true or  empty param.isPopUp}");
/* 3463 */                             int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 3464 */                             if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                               for (;;) {
/* 3466 */                                 out.write("\n\t\t\t\t\t        <input type=\"hidden\" name=\"method\" value=\"popUp\"/>\n\t\t\t\t\t        &nbsp;");
/*      */                                 
/* 3468 */                                 SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3469 */                                 _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3470 */                                 _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fwhen_005f13);
/*      */                                 
/* 3472 */                                 _jspx_th_html_005fselect_005f0.setProperty("selectedView");
/*      */                                 
/* 3474 */                                 _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                 
/* 3476 */                                 _jspx_th_html_005fselect_005f0.setStyle(" vertical-align:middle;");
/*      */                                 
/* 3478 */                                 _jspx_th_html_005fselect_005f0.setOnchange("javascript:onfnSubmit(this);");
/* 3479 */                                 int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3480 */                                 if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3481 */                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3482 */                                     out = _jspx_page_context.pushBody();
/* 3483 */                                     _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3484 */                                     _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3487 */                                     out.write("\n\t\t\t\t\t\t");
/*      */                                     
/* 3489 */                                     OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3490 */                                     _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3491 */                                     _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3493 */                                     _jspx_th_html_005foption_005f0.setValue("-10");
/* 3494 */                                     int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3495 */                                     if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3496 */                                       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3497 */                                         out = _jspx_page_context.pushBody();
/* 3498 */                                         _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3499 */                                         _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3502 */                                         out.write(45);
/* 3503 */                                         out.write(45);
/* 3504 */                                         out.write(45);
/* 3505 */                                         out.print(FormatUtil.getString("am.monitortab.selectview.text"));
/* 3506 */                                         out.write(45);
/* 3507 */                                         out.write(45);
/* 3508 */                                         out.write(45);
/* 3509 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3510 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3513 */                                       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3514 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3517 */                                     if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3518 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                     }
/*      */                                     
/* 3521 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3522 */                                     out.write("\n\t\t\t\t\t\t");
/* 3523 */                                     if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/*      */                                       return;
/* 3525 */                                     out.write("\n\t\t\t\t\t        ");
/* 3526 */                                     int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3527 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3530 */                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3531 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3534 */                                 if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3535 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                 }
/*      */                                 
/* 3538 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3539 */                                 out.write("</td>\n\t\t\t\t       ");
/* 3540 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 3541 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3545 */                             if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 3546 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                             }
/*      */                             
/* 3549 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 3550 */                             out.write("\n\t\t\t\t       ");
/* 3551 */                             if (_jspx_meth_c_005fotherwise_005f13(_jspx_th_c_005fchoose_005f13, _jspx_page_context))
/*      */                               return;
/* 3553 */                             out.write("\n                               ");
/* 3554 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 3555 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3559 */                         if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 3560 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                         }
/*      */                         
/* 3563 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 3564 */                         out.write("\n\n\t\t\t       ");
/* 3565 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3566 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3570 */                     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3571 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                     }
/*      */                     
/* 3574 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3575 */                     out.write("\n\t\t\t       ");
/* 3576 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3577 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3581 */                 if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3582 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                 }
/*      */                 
/* 3585 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3586 */                 out.write("\n\t\t\t       \n\t\t\t\t\t");
/* 3587 */                 if (_jspx_meth_c_005fif_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/* 3589 */                 out.write("\n\n\n\n\n\n\n\t\t\t       <td width=\"40%\" align=\"left\">\n\n                                ");
/*      */                 
/* 3591 */                 IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3592 */                 _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 3593 */                 _jspx_th_c_005fif_005f18.setParent(_jspx_th_html_005fform_005f0);
/*      */                 
/* 3595 */                 _jspx_th_c_005fif_005f18.setTest("${  empty publishview}");
/* 3596 */                 int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 3597 */                 if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                   for (;;) {
/* 3599 */                     out.write("\n                                ");
/*      */                     
/* 3601 */                     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3602 */                     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3603 */                     _jspx_th_c_005fif_005f19.setParent(_jspx_th_c_005fif_005f18);
/*      */                     
/* 3605 */                     _jspx_th_c_005fif_005f19.setTest("${not empty pageContext.request.remoteUser}");
/* 3606 */                     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3607 */                     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                       for (;;) {
/* 3609 */                         out.write("\n                                 ");
/*      */                         
/* 3611 */                         IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3612 */                         _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3613 */                         _jspx_th_c_005fif_005f20.setParent(_jspx_th_c_005fif_005f19);
/*      */                         
/* 3615 */                         _jspx_th_c_005fif_005f20.setTest("${ FlashForm.showTopLevelMgs!=true }");
/* 3616 */                         int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3617 */                         if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                           for (;;) {
/* 3619 */                             out.write("\n\t\t\t\t &nbsp;\n \t                         <span onClick=\"saveFlashXML()\" style=\"cursor:pointer;\"><img src=\"/images/save.gif\" border=\"0\">&nbsp;<a href=\"javascript:void(0);\" title='");
/* 3620 */                             out.print(FormatUtil.getString("am.webclient.flashview.saveconfig.text"));
/* 3621 */                             out.write("' style=\"color:#000; font-size:10px; font-family:Arial, Helvetica, sans-serif;\">");
/* 3622 */                             out.print(FormatUtil.getString("am.webclient.flashview.saveconfig.text"));
/* 3623 */                             out.write("</a>&nbsp; |</span>&nbsp;\n \t                         ");
/* 3624 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3625 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3629 */                         if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3630 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                         }
/*      */                         
/* 3633 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3634 */                         out.write("\n \t                         ");
/*      */                         
/* 3636 */                         IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3637 */                         _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 3638 */                         _jspx_th_c_005fif_005f21.setParent(_jspx_th_c_005fif_005f19);
/*      */                         
/* 3640 */                         _jspx_th_c_005fif_005f21.setTest("${!readOnlyMode}");
/* 3641 */                         int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 3642 */                         if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                           for (;;) {
/* 3644 */                             out.write("\n \t                         <span onclick=\"showSwitchOptions();return false;\" style=\"cursor:pointer;\"><img src=\"/images/settings.gif\" border=\"0\"/>&nbsp;<a href=\"javascript:void(0);\"  id='settingsAnchor' style=\"color:#000000; font-size:10px; font-family:Arial, Helvetica, sans-serif;\" title='");
/* 3645 */                             out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/* 3646 */                             out.write(39);
/* 3647 */                             out.write(62);
/* 3648 */                             out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/* 3649 */                             out.write("</a> <img src=\"/images/icon_black_arrow.gif\"  border=\"0\"/> &nbsp;| </span>&nbsp;\n\t\t\t\t\t\t\t");
/* 3650 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 3651 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3655 */                         if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 3656 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                         }
/*      */                         
/* 3659 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 3660 */                         out.write("\n \t                         <div id=\"insertMenuContainer\" style=\"display: none;\">\n\t\t\t\t   <ul id=\"insertMenu\" forelement=\"settingsAnchor\">\n\n\n\t\t\t\t     ");
/* 3661 */                         if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 3662 */                           out.write("\n\t\t\t\t     ");
/*      */                           
/* 3664 */                           PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3665 */                           _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3666 */                           _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f19);
/*      */                           
/* 3668 */                           _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/* 3669 */                           int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3670 */                           if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                             for (;;) {
/* 3672 */                               out.write("\n\t\t\t             <li onClick=\"editProperties()\">");
/* 3673 */                               out.print(FormatUtil.getString("am.webclient.flashview.editview.text"));
/* 3674 */                               out.write("</li>\t\t     \n\t\t\t\t     <li onClick=\"createNewView()\">");
/* 3675 */                               out.print(FormatUtil.getString("am.webclient.flashview.createview.text"));
/* 3676 */                               out.write("</li>                                                       ");
/*      */                               
/* 3678 */                               IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3679 */                               _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 3680 */                               _jspx_th_c_005fif_005f22.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                               
/* 3682 */                               _jspx_th_c_005fif_005f22.setTest("${ FlashForm.fromMonitorTab == true}");
/* 3683 */                               int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 3684 */                               if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                 for (;;) {
/* 3686 */                                   out.write("\n\t\t\t\t     <li onClick=\"deleteView()\">");
/* 3687 */                                   out.print(FormatUtil.getString("am.webclient.flashview.deleteview.text"));
/* 3688 */                                   out.write("</li>\n                                     ");
/* 3689 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 3690 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3694 */                               if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 3695 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                               }
/*      */                               
/* 3698 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 3699 */                               out.write("\n\t\t\t\t     ");
/* 3700 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3701 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3705 */                           if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3706 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                           }
/*      */                           
/* 3709 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3710 */                           out.write("\n\t\t\t\t     ");
/*      */                         } else {
/* 3712 */                           out.write("\t\t\t\t     \n\t\t\t\t     <li onClick=\"editProperties()\">");
/* 3713 */                           out.print(FormatUtil.getString("am.webclient.flashview.editview.text"));
/* 3714 */                           out.write("</li>\n\t\t\t\t     ");
/*      */                           
/* 3716 */                           PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3717 */                           _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3718 */                           _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f19);
/*      */                           
/* 3720 */                           _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/* 3721 */                           int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3722 */                           if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                             for (;;) {
/* 3724 */                               out.write("\n\t\t\t\t     <li onClick=\"createNewView()\">");
/* 3725 */                               out.print(FormatUtil.getString("am.webclient.flashview.createview.text"));
/* 3726 */                               out.write("</li>\n                                     ");
/* 3727 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3728 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3732 */                           if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3733 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                           }
/*      */                           
/* 3736 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3737 */                           out.write("\n                                     ");
/*      */                           
/* 3739 */                           IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3740 */                           _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 3741 */                           _jspx_th_c_005fif_005f23.setParent(_jspx_th_c_005fif_005f19);
/*      */                           
/* 3743 */                           _jspx_th_c_005fif_005f23.setTest("${ FlashForm.fromMonitorTab == true}");
/* 3744 */                           int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 3745 */                           if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                             for (;;) {
/* 3747 */                               out.write("\n\t\t\t\t     <li onClick=\"deleteView()\">");
/* 3748 */                               out.print(FormatUtil.getString("am.webclient.flashview.deleteview.text"));
/* 3749 */                               out.write("</li>\n                                     ");
/* 3750 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 3751 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3755 */                           if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 3756 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                           }
/*      */                           
/* 3759 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 3760 */                           out.write("\n\t\t\t\t     ");
/*      */                         }
/* 3762 */                         out.write("\n\n                                     <li sep=\"true\"></li>\n                                     ");
/*      */                         
/* 3764 */                         IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3765 */                         _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 3766 */                         _jspx_th_c_005fif_005f24.setParent(_jspx_th_c_005fif_005f19);
/*      */                         
/* 3768 */                         _jspx_th_c_005fif_005f24.setTest("${ FlashForm.fromMonitorTab == true}");
/* 3769 */                         int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 3770 */                         if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */                           for (;;) {
/* 3772 */                             out.write("\n                                     <li onClick=\"setAsDefaultView()\">");
/* 3773 */                             out.print(FormatUtil.getString("am.webclient.flashview.defaultview.text"));
/* 3774 */                             out.write("</li>\n                                     ");
/* 3775 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 3776 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3780 */                         if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 3781 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24); return;
/*      */                         }
/*      */                         
/* 3784 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 3785 */                         out.write("\n                                     ");
/*      */                         
/* 3787 */                         IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3788 */                         _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 3789 */                         _jspx_th_c_005fif_005f25.setParent(_jspx_th_c_005fif_005f19);
/*      */                         
/* 3791 */                         _jspx_th_c_005fif_005f25.setTest("${ FlashForm.showTopLevelMgs!=true }");
/* 3792 */                         int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 3793 */                         if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                           for (;;) {
/* 3795 */                             out.write("\n                                     <li onClick=\"resetDesign()\">");
/* 3796 */                             out.print(FormatUtil.getString("am.webclient.flashview.resetdesign.text"));
/* 3797 */                             out.write("</li>\n\t\t\t\t     ");
/* 3798 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 3799 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3803 */                         if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 3804 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                         }
/*      */                         
/* 3807 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 3808 */                         out.write("\n\t\t\t\t     <li onClick=\"showExternalLink()\">");
/* 3809 */                         out.print(FormatUtil.getString("am.webclient.flashview.makepublic.text"));
/* 3810 */                         out.write("</li>\n\t\t\t\t   </ul>\n\n                                  </div>\n \t                         ");
/* 3811 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3812 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3816 */                     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3817 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                     }
/*      */                     
/* 3820 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3821 */                     out.write("\n \t                         ");
/* 3822 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3823 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3827 */                 if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3828 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                 }
/*      */                 
/* 3831 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3832 */                 out.write("\n\t\t\t\t\t\t\t <span style=\"cursor:pointer;\" onClick=\"refreshCurrentView()\"><img src=\"/images/refresh-flash.gif\" border=\"0\">&nbsp;<a href=\"javascript:void(0);\" title=\"");
/* 3833 */                 out.print(FormatUtil.getString("am.webclient.flashview.refreshview.text"));
/* 3834 */                 out.write("\" style=\"color:#000; font-size:10px;  font-family:Arial, Helvetica, sans-serif;\">");
/* 3835 */                 out.print(FormatUtil.getString("am.webclient.flashview.refreshview.text"));
/* 3836 */                 out.write("</a>&nbsp; </span>\n                                                  ");
/*      */                 
/* 3838 */                 IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3839 */                 _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 3840 */                 _jspx_th_c_005fif_005f26.setParent(_jspx_th_html_005fform_005f0);
/*      */                 
/* 3842 */                 _jspx_th_c_005fif_005f26.setTest("${(empty param.isPopUp || param.isPopUp == 'null')}");
/* 3843 */                 int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 3844 */                 if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                   for (;;) {
/* 3846 */                     out.write("\n                                                    <span style=\"cursor:pointer;\" onClick=\"popOut();\">| &nbsp;<img src=\"/images/popup.gif\" border=\"0\"/> &nbsp;<a href=\"javascript:void(0);\" title=\"");
/* 3847 */                     out.print(FormatUtil.getString("Popup"));
/* 3848 */                     out.write("\" style=\"font-family: Arial, Helvetica, sans-serif; color :#000000; font-size: 10px;\">");
/* 3849 */                     out.print(FormatUtil.getString("Popup"));
/* 3850 */                     out.write("&nbsp;</a></span>\n                                                  ");
/* 3851 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 3852 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3856 */                 if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 3857 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                 }
/*      */                 
/* 3860 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 3861 */                 out.write("\n\n                               </td>\n\t\t\t\t\t\t\t\t\n\n\n\n\n                           </tr>\n                            ");
/* 3862 */                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3863 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 3867 */             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3868 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */             }
/*      */             
/* 3871 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3872 */             out.write("\n                        </table>\n\t\t\t\t\t\t\n\t\t\t\t\t\t   <div id=\"externallink\" style=\"display:none\">\n                               <table width=\"100%\" align=\"right\" cellspacing=\"1\" cellpadding=\"2\" border=\"0\" bgcolor=\"#FFFFFF\">\n                               <tr>\n                               <td class=\"bodytext\">");
/* 3873 */             out.print(FormatUtil.getString("am.webclient.flashview.publicview.text"));
/* 3874 */             out.write(":</td>\n                               </tr>\n                               <tr>\n                               <td>\n                               <form name=\"dummyform\">\n                               <div id=\"txtbx\"></div>\n                               </form>\n                               </td>\n                               </tr>\n                               <tr>\n                               <td class=\"bodytext\">\n                               ");
/* 3875 */             out.print(FormatUtil.getString("am.webclient.flashview.copylink.text"));
/* 3876 */             out.write(":\n                               </td>\n                               </tr>\n                               </table>\n\n                               </div>\n\n                   </td>\n               </tr>\n           </table>\n</td>\n</tr>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 3881 */             out.write("\n  \t<tr>\n\t\t  <td valign=\"top\" height=\"32\">\n\t\t\t  <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t  <tr>\n\t\t\t  \t<td  class=\"tp-header-tile\"  height=\"32\"  align=\"right\">\n\t\t\t  \t\t<a href=\"javascript:void(0);\" onClick=\"refreshCurrentView()\" title=\"");
/* 3882 */             out.print(FormatUtil.getString("am.webclient.flashview.refreshview.text"));
/* 3883 */             out.write("\"><img src=\"/images/refresh-flash.gif\" border=\"0\"> <span class=\"bsm-icon\"  style=\"color:#000; font-size:10px;  font-family:Arial, Helvetica, sans-serif;\">");
/* 3884 */             out.print(FormatUtil.getString("am.webclient.flashview.refreshview.text"));
/* 3885 */             out.write("&nbsp;</span> </a>\n  \t\t\t  \t</td>\n  \t\t\t  </tr>\n  \t\t\t  </table>\n\t\t  </td>\n\t</tr>\n");
/*      */           }
/*      */           
/*      */ 
/* 3889 */           out.write(10);
/*      */           
/* 3891 */           ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3892 */           _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 3893 */           _jspx_th_c_005fchoose_005f14.setParent(null);
/* 3894 */           int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 3895 */           if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */             for (;;) {
/* 3897 */               out.write(10);
/* 3898 */               if (_jspx_meth_c_005fwhen_005f14(_jspx_th_c_005fchoose_005f14, _jspx_page_context))
/*      */                 return;
/* 3900 */               out.write(10);
/* 3901 */               out.write(10);
/* 3902 */               out.write(9);
/*      */               
/* 3904 */               OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3905 */               _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 3906 */               _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f14);
/* 3907 */               int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 3908 */               if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                 for (;;) {
/* 3910 */                   out.write(10);
/* 3911 */                   out.write(9);
/*      */                   
/* 3913 */                   ArrayList resIDs_mg = new ArrayList();
/* 3914 */                   ArrayList attribIDs_mg = new ArrayList();
/* 3915 */                   attribIDs_mg.add("17");
/* 3916 */                   attribIDs_mg.add("18");
/*      */                   
/* 3918 */                   out.write(10);
/* 3919 */                   out.write(9);
/*      */                   
/* 3921 */                   ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3922 */                   _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3923 */                   _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fotherwise_005f15);
/*      */                   
/* 3925 */                   _jspx_th_c_005fforEach_005f0.setVar("monitorgroup");
/*      */                   
/* 3927 */                   _jspx_th_c_005fforEach_005f0.setItems("${FlashForm.monitorGroups}");
/*      */                   
/* 3929 */                   _jspx_th_c_005fforEach_005f0.setVarStatus("counter");
/* 3930 */                   int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                   try {
/* 3932 */                     int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3933 */                     if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                       for (;;) {
/* 3935 */                         out.write(10);
/* 3936 */                         out.write(9);
/*      */                         
/* 3938 */                         resIDs_mg.add((String)pageContext.getAttribute("monitorgroup"));
/*      */                         
/* 3940 */                         out.write(10);
/* 3941 */                         out.write(9);
/* 3942 */                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3943 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3947 */                     if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3955 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3956 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/*      */                   }
/*      */                   catch (Throwable _jspx_exception)
/*      */                   {
/*      */                     for (;;)
/*      */                     {
/* 3951 */                       int tmp10370_10369 = 0; int[] tmp10370_10367 = _jspx_push_body_count_c_005fforEach_005f0; int tmp10372_10371 = tmp10370_10367[tmp10370_10369];tmp10370_10367[tmp10370_10369] = (tmp10372_10371 - 1); if (tmp10372_10371 <= 0) break;
/* 3952 */                       out = _jspx_page_context.popBody(); }
/* 3953 */                     _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                   } finally {
/* 3955 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3956 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                   }
/* 3958 */                   out.write(10);
/* 3959 */                   out.write(9);
/*      */                   
/* 3961 */                   Properties alert_mg = getStatusForMgs(resIDs_mg, attribIDs_mg);
/* 3962 */                   request.setAttribute("alert_mg", alert_mg);
/*      */                   
/* 3964 */                   out.write("\n\t<tr>\n        <td valign=\"top\">\n         <table width=\"100%\"  cellpadding=\"0\" cellspacing=\"0\" height=\"80px\">\n\t");
/*      */                   
/* 3966 */                   ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3967 */                   _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3968 */                   _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fotherwise_005f15);
/*      */                   
/* 3970 */                   _jspx_th_c_005fforEach_005f1.setVar("monitorgroup");
/*      */                   
/* 3972 */                   _jspx_th_c_005fforEach_005f1.setItems("${FlashForm.monitorGroups}");
/*      */                   
/* 3974 */                   _jspx_th_c_005fforEach_005f1.setVarStatus("counter");
/* 3975 */                   int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                   try {
/* 3977 */                     int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3978 */                     if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                       for (;;) {
/* 3980 */                         out.write(10);
/* 3981 */                         out.write(9);
/* 3982 */                         out.write(9);
/* 3983 */                         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/* 3985 */                         out.write(10);
/* 3986 */                         out.write(9);
/* 3987 */                         out.write(32);
/* 3988 */                         if (_jspx_meth_c_005fif_005f27(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/* 3990 */                         out.write("\n\t <td align=\"center\">\n\t ");
/*      */                         
/* 3992 */                         SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3993 */                         _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3994 */                         _jspx_th_c_005fset_005f3.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                         
/* 3996 */                         _jspx_th_c_005fset_005f3.setVar("key1");
/* 3997 */                         int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3998 */                         if (_jspx_eval_c_005fset_005f3 != 0) {
/* 3999 */                           if (_jspx_eval_c_005fset_005f3 != 1) {
/* 4000 */                             out = _jspx_page_context.pushBody();
/* 4001 */                             _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4002 */                             _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 4003 */                             _jspx_th_c_005fset_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4006 */                             out.write(32);
/* 4007 */                             out.print(pageContext.getAttribute("monitorgroup") + "#" + "18" + "#" + "MESSAGE");
/* 4008 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 4009 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4012 */                           if (_jspx_eval_c_005fset_005f3 != 1) {
/* 4013 */                             out = _jspx_page_context.popBody();
/* 4014 */                             _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */                           }
/*      */                         }
/* 4017 */                         if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 4018 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/* 4021 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 4022 */                         out.write(10);
/* 4023 */                         out.write(9);
/* 4024 */                         out.write(32);
/*      */                         
/* 4026 */                         SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4027 */                         _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 4028 */                         _jspx_th_c_005fset_005f4.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                         
/* 4030 */                         _jspx_th_c_005fset_005f4.setVar("key2");
/* 4031 */                         int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 4032 */                         if (_jspx_eval_c_005fset_005f4 != 0) {
/* 4033 */                           if (_jspx_eval_c_005fset_005f4 != 1) {
/* 4034 */                             out = _jspx_page_context.pushBody();
/* 4035 */                             _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4036 */                             _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 4037 */                             _jspx_th_c_005fset_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4040 */                             out.write(32);
/* 4041 */                             out.print(pageContext.getAttribute("monitorgroup") + "#" + "18");
/* 4042 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 4043 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4046 */                           if (_jspx_eval_c_005fset_005f4 != 1) {
/* 4047 */                             out = _jspx_page_context.popBody();
/* 4048 */                             _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */                           }
/*      */                         }
/* 4051 */                         if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 4052 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/* 4055 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 4056 */                         out.write(10);
/* 4057 */                         out.write(9);
/* 4058 */                         out.write(32);
/*      */                         
/* 4060 */                         SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4061 */                         _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 4062 */                         _jspx_th_c_005fset_005f5.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                         
/* 4064 */                         _jspx_th_c_005fset_005f5.setVar("key3");
/* 4065 */                         int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 4066 */                         if (_jspx_eval_c_005fset_005f5 != 0) {
/* 4067 */                           if (_jspx_eval_c_005fset_005f5 != 1) {
/* 4068 */                             out = _jspx_page_context.pushBody();
/* 4069 */                             _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4070 */                             _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 4071 */                             _jspx_th_c_005fset_005f5.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4074 */                             out.write(32);
/* 4075 */                             out.print(pageContext.getAttribute("monitorgroup") + "#" + "17");
/* 4076 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 4077 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4080 */                           if (_jspx_eval_c_005fset_005f5 != 1) {
/* 4081 */                             out = _jspx_page_context.popBody();
/* 4082 */                             _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */                           }
/*      */                         }
/* 4085 */                         if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 4086 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/* 4089 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 4090 */                         out.write("\n\n\t         <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\t         <tr>\n\t         <td align=\"center\" >\n\t          ");
/*      */                         
/* 4092 */                         ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4093 */                         _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 4094 */                         _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_c_005fforEach_005f1);
/* 4095 */                         int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 4096 */                         if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                           for (;;) {
/* 4098 */                             out.write("\n\t\t ");
/* 4099 */                             if (_jspx_meth_c_005fwhen_005f16(_jspx_th_c_005fchoose_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 4101 */                             out.write("\n\t\t ");
/* 4102 */                             if (_jspx_meth_c_005fwhen_005f17(_jspx_th_c_005fchoose_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 4104 */                             out.write("\n\t\t ");
/* 4105 */                             if (_jspx_meth_c_005fwhen_005f20(_jspx_th_c_005fchoose_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 4107 */                             out.write("\n\t\t ");
/*      */                             
/* 4109 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4110 */                             _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 4111 */                             _jspx_th_c_005fotherwise_005f17.setParent(_jspx_th_c_005fchoose_005f16);
/* 4112 */                             int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 4113 */                             if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */                               for (;;) {
/* 4115 */                                 out.write("\n\t\t <a target=\"_top\" href=\"");
/* 4116 */                                 if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fotherwise_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 4118 */                                 out.write("\" onMouseOut=\"hideddrivetip()\" onMouseOver=\"ddrivetip(this,event,'");
/* 4119 */                                 out.print(FormatUtil.getString("am.webclient.rcamessage.healthunknown.text"));
/* 4120 */                                 out.write("',false,true,'#000000',200,'lightyellow')\" style=\"cursor: pointer;\"><img  border=\"0\" src=\"/images/mg_unknown.gif\"/></a>\n\t\t ");
/* 4121 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 4122 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4126 */                             if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 4127 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 4130 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 4131 */                             out.write(10);
/* 4132 */                             out.write(9);
/* 4133 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 4134 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4138 */                         if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 4139 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/* 4142 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 4143 */                         out.write("\n\t</td>\n\t\t\t </tr>\n\t\t\t <tr>\n\t\t\t <td align=\"center\">\n\t\t\t \t<a class=\"staticlinks\" target=\"_top\" href=\"");
/* 4144 */                         if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/* 4146 */                         out.write(34);
/* 4147 */                         out.write(62);
/* 4148 */                         if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/* 4150 */                         out.write("</a>\n\t\t\t </td>\n\t\t\t </tr>\n\t\t\t <tr>\n\t\t\t <td height=\"3px\">&nbsp;\n\n\t\t\t </td>\n\t\t\t </tr>\n\t\t </table>\n\t</td>\n\t ");
/* 4151 */                         if (_jspx_meth_c_005fif_005f28(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/* 4153 */                         out.write("\n        ");
/* 4154 */                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4155 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 4159 */                     if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4167 */                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                     }
/*      */                   }
/*      */                   catch (Throwable _jspx_exception)
/*      */                   {
/*      */                     for (;;)
/*      */                     {
/* 4163 */                       int tmp12053_12052 = 0; int[] tmp12053_12050 = _jspx_push_body_count_c_005fforEach_005f1; int tmp12055_12054 = tmp12053_12050[tmp12053_12052];tmp12053_12050[tmp12053_12052] = (tmp12055_12054 - 1); if (tmp12055_12054 <= 0) break;
/* 4164 */                       out = _jspx_page_context.popBody(); }
/* 4165 */                     _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                   } finally {
/* 4167 */                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 4168 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                   }
/* 4170 */                   out.write("\n        </table>\n\t");
/* 4171 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 4172 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4176 */               if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 4177 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */               }
/*      */               
/* 4180 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 4181 */               out.write(10);
/* 4182 */               out.write(9);
/* 4183 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 4184 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 4188 */           if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 4189 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */           }
/*      */           
/* 4192 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 4193 */           out.write("\n        </td>\n     </tr>\n</table>\n");
/* 4194 */           if (_jspx_meth_c_005fif_005f29(_jspx_page_context))
/*      */             return;
/* 4196 */           out.write(10);
/*      */ 
/*      */         }
/*      */         catch (Exception exx)
/*      */         {
/* 4201 */           exx.printStackTrace();
/*      */         }
/*      */         
/* 4204 */         out.write(10);
/* 4205 */         out.write(10);
/* 4206 */         out.write("\n\n<script type=\"text/javascript\">\n\n$(document).ready(alterContentAreaSize);\nfunction alterContentAreaSize()\n{\n\tvar locationStr = parent.window.document.location.href;\n\tif(locationStr.indexOf(\"method=popOut\") > -1 || locationStr.indexOf(\"method=popUp\") > -1) \n\t{\n\t\tvar availHeight = screen.availHeight;\n\t\tvar parentDiv = parent.window.document.getElementById('NavBar');\n\t\t$(parentDiv).find(\"table.lrtbdarkborder-dashboards\").css(\"height\",(availHeight-20));  //NO I18N\n\t\tvar iframe= parent.window.document.getElementById('_iframe_view');\n    \t$(\"#bvDivFlash\").css(\"height\",availHeight-50); // NO I18N\n    \t$(iframe).attr(\"height\",availHeight-10); // NO I18N\n\t}\n\t $('#infovis').mouseenter(function(){isHover = true;}).mouseleave(function(){isHover = false;});\n}\n\n</script>\n\n");
/*      */       }
/* 4208 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4209 */         out = _jspx_out;
/* 4210 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4211 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4212 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4215 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4221 */     PageContext pageContext = _jspx_page_context;
/* 4222 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4224 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4225 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4226 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 4228 */     _jspx_th_c_005fif_005f0.setTest("${not empty param.isPopUp}");
/* 4229 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4230 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4232 */         out.write("\n<html>\n<head>\n<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"86400\">\n</head>\n<body onLoad=\"flashviewOnLoad();\">\n<div id=\"dhtmltooltip\"></div>\n<div id=\"dhtmlpointer\" style=\"display:none\"><img src=\"/images/arrow2.gif\"></div>\n");
/* 4233 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4234 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4238 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4239 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4240 */       return true;
/*      */     }
/* 4242 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4243 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4248 */     PageContext pageContext = _jspx_page_context;
/* 4249 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4251 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4252 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4253 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 4255 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 4257 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 4258 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4259 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4260 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4261 */       return true;
/*      */     }
/* 4263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4269 */     PageContext pageContext = _jspx_page_context;
/* 4270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4272 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4273 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4274 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 4276 */     _jspx_th_c_005fout_005f1.setValue("${FlashForm.isHtml}");
/* 4277 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4278 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4279 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4280 */       return true;
/*      */     }
/* 4282 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4288 */     PageContext pageContext = _jspx_page_context;
/* 4289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4291 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4292 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4293 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 4295 */     _jspx_th_c_005fout_005f2.setValue("${FlashForm.linethickness}");
/* 4296 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4297 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4298 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4299 */       return true;
/*      */     }
/* 4301 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4307 */     PageContext pageContext = _jspx_page_context;
/* 4308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4310 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4311 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4312 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 4314 */     _jspx_th_c_005fout_005f3.setValue("${FlashForm.lineColor}");
/* 4315 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4316 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4317 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4318 */       return true;
/*      */     }
/* 4320 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4326 */     PageContext pageContext = _jspx_page_context;
/* 4327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4329 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4330 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4331 */     _jspx_th_c_005fout_005f4.setParent(null);
/*      */     
/* 4333 */     _jspx_th_c_005fout_005f4.setValue("${FlashForm.lineTransparency}");
/* 4334 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4335 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4336 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4337 */       return true;
/*      */     }
/* 4339 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4345 */     PageContext pageContext = _jspx_page_context;
/* 4346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4348 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4349 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4350 */     _jspx_th_c_005fout_005f5.setParent(null);
/*      */     
/* 4352 */     _jspx_th_c_005fout_005f5.setValue("${FlashForm.labelColor}");
/* 4353 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4354 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4355 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4356 */       return true;
/*      */     }
/* 4358 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4364 */     PageContext pageContext = _jspx_page_context;
/* 4365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4367 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4368 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 4369 */     _jspx_th_c_005fif_005f4.setParent(null);
/*      */     
/* 4371 */     _jspx_th_c_005fif_005f4.setTest("${ FlashForm.fromMonitorTab == true}");
/* 4372 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 4373 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 4375 */         out.write("\n\tif(document.FlashForm.selectedView.value=='-10')\n\t{\n\t  tempviewid=\"-1\";\n\t}\n\t");
/* 4376 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4377 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4381 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 4382 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4383 */       return true;
/*      */     }
/* 4385 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4391 */     PageContext pageContext = _jspx_page_context;
/* 4392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4394 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4395 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 4396 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 4398 */     _jspx_th_c_005fif_005f5.setTest("${ FlashForm.fromMonitorTab == true}");
/* 4399 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 4400 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 4402 */         out.write("\n                   if(document.FlashForm.selectedView.value=='-10')\n\t\t   {\n                   var newoption=document.createElement('option');\n\t\t   newoption.text=name;\n\t\t   newoption.value=viewid;\n\t\t   var selview=document.FlashForm.selectedView;\n\t\t   try\n\t\t   {\n\t\t     selview.add(newoption,null); // standards compliant\n\t\t   }\n\t\t   catch(ex)\n\t\t   {\n\t\t     selview.add(newoption); // for Internet explorer\n\t\t   }\n\t\t   try\n\t\t   {\n\t\t   document.FlashForm.selectedView.selectedIndex=document.FlashForm.selectedView.length-1;\n\t\t   }\n\t\t   catch(ex)\n\t\t   {\n\t\t   }\n\t\t   }\n\t\t   ");
/* 4403 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 4404 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4408 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 4409 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4410 */       return true;
/*      */     }
/* 4412 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4418 */     PageContext pageContext = _jspx_page_context;
/* 4419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4421 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4422 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 4423 */     _jspx_th_c_005fchoose_005f4.setParent(null);
/* 4424 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 4425 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 4427 */         out.write(10);
/* 4428 */         out.write(32);
/* 4429 */         out.write(32);
/* 4430 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 4431 */           return true;
/* 4432 */         out.write(10);
/* 4433 */         out.write(32);
/* 4434 */         out.write(32);
/* 4435 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 4436 */           return true;
/* 4437 */         out.write(10);
/* 4438 */         out.write(32);
/* 4439 */         out.write(32);
/* 4440 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 4441 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4445 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 4446 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 4447 */       return true;
/*      */     }
/* 4449 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 4450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4455 */     PageContext pageContext = _jspx_page_context;
/* 4456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4458 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4459 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 4460 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 4462 */     _jspx_th_c_005fwhen_005f4.setTest("${FlashForm.showCriticalMonitors==\"true\" || FlashForm.showTopLevelMgs==\"true\"}");
/* 4463 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 4464 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 4466 */         out.write("\n  timeOutHandle=setTimeout(\"refreshCurrentView()\",");
/* 4467 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/* 4468 */           return true;
/* 4469 */         out.write(");\n  ");
/* 4470 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 4471 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4475 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 4476 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 4477 */       return true;
/*      */     }
/* 4479 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 4480 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4485 */     PageContext pageContext = _jspx_page_context;
/* 4486 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4488 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4489 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4490 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 4492 */     _jspx_th_c_005fout_005f6.setValue("${FlashForm.refreshInterval}");
/* 4493 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4494 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4495 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4496 */       return true;
/*      */     }
/* 4498 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4504 */     PageContext pageContext = _jspx_page_context;
/* 4505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4507 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4508 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 4509 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 4510 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 4511 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 4513 */         out.write("\n  timeOutHandle=setTimeout(\"autoRefreshStatus()\",");
/* 4514 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 4515 */           return true;
/* 4516 */         out.write(");\n  ");
/* 4517 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 4518 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4522 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 4523 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 4524 */       return true;
/*      */     }
/* 4526 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 4527 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4532 */     PageContext pageContext = _jspx_page_context;
/* 4533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4535 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4536 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4537 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 4539 */     _jspx_th_c_005fout_005f7.setValue("${FlashForm.refreshInterval}");
/* 4540 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4541 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4542 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4543 */       return true;
/*      */     }
/* 4545 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4551 */     PageContext pageContext = _jspx_page_context;
/* 4552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4554 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4555 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 4556 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*      */     
/* 4558 */     _jspx_th_c_005fwhen_005f6.setTest("${FlashForm.fromWhere==\"hometab\"}");
/* 4559 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 4560 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 4562 */         out.write("\n                    location.href=\"/applications.do?selectTab=MGStatus&haid=0&viewid=\"+viewid1;\n\t    \t  ");
/* 4563 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 4564 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4568 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 4569 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 4570 */       return true;
/*      */     }
/* 4572 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 4573 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4578 */     PageContext pageContext = _jspx_page_context;
/* 4579 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4581 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4582 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4583 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/*      */     
/* 4585 */     _jspx_th_c_005fout_005f8.setValue("${param.isPopUp}");
/* 4586 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4587 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4588 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4589 */       return true;
/*      */     }
/* 4591 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4597 */     PageContext pageContext = _jspx_page_context;
/* 4598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4600 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4601 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4602 */     _jspx_th_c_005fif_005f13.setParent(null);
/*      */     
/* 4604 */     _jspx_th_c_005fif_005f13.setTest("${not empty mgDisplayName}");
/* 4605 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4606 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 4608 */         out.write("\n         title=title+\"");
/* 4609 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f13, _jspx_page_context))
/* 4610 */           return true;
/* 4611 */         out.write(" -\";\n         ");
/* 4612 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4613 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4617 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4618 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4619 */       return true;
/*      */     }
/* 4621 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4627 */     PageContext pageContext = _jspx_page_context;
/* 4628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4630 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4631 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4632 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 4634 */     _jspx_th_c_005fout_005f9.setValue("${mgDisplayName}");
/* 4635 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4636 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4637 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4638 */       return true;
/*      */     }
/* 4640 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4646 */     PageContext pageContext = _jspx_page_context;
/* 4647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4649 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4650 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4651 */     _jspx_th_c_005fout_005f10.setParent(null);
/*      */     
/* 4653 */     _jspx_th_c_005fout_005f10.setValue("${FlashForm.displayName}");
/* 4654 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4655 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4656 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4657 */       return true;
/*      */     }
/* 4659 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4665 */     PageContext pageContext = _jspx_page_context;
/* 4666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4668 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4669 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 4670 */     _jspx_th_c_005fif_005f14.setParent(null);
/*      */     
/* 4672 */     _jspx_th_c_005fif_005f14.setTest("${ FlashForm.fromMonitorTab == true}");
/* 4673 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 4674 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 4676 */         out.write("\n\tif(document.FlashForm.selectedView.value=='-10')\n\t{\n\t  tempviewid=\"-1\";\n\t}\n\t");
/* 4677 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 4678 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4682 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 4683 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4684 */       return true;
/*      */     }
/* 4686 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4687 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4692 */     PageContext pageContext = _jspx_page_context;
/* 4693 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4695 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4696 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4697 */     _jspx_th_c_005fset_005f1.setParent(null);
/*      */     
/* 4699 */     _jspx_th_c_005fset_005f1.setVar("${requestScope.isHtml}");
/*      */     
/* 4701 */     _jspx_th_c_005fset_005f1.setValue("true");
/* 4702 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4703 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4704 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4705 */       return true;
/*      */     }
/* 4707 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f12(JspTag _jspx_th_c_005fchoose_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4713 */     PageContext pageContext = _jspx_page_context;
/* 4714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4716 */     OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4717 */     _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 4718 */     _jspx_th_c_005fotherwise_005f12.setParent((Tag)_jspx_th_c_005fchoose_005f12);
/* 4719 */     int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 4720 */     if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */       for (;;) {
/* 4722 */         out.write("\n\t\t\t\t           <td height=\"32\"  align=\"left\" valign=\"middle\" width=\"10%\">\n\t\t\t\t       ");
/* 4723 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 4724 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4728 */     if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 4729 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 4730 */       return true;
/*      */     }
/* 4732 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 4733 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4738 */     PageContext pageContext = _jspx_page_context;
/* 4739 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4741 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4742 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 4743 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 4745 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("availableViews");
/* 4746 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 4747 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 4748 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4749 */       return true;
/*      */     }
/* 4751 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4752 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f13(JspTag _jspx_th_c_005fchoose_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4757 */     PageContext pageContext = _jspx_page_context;
/* 4758 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4760 */     OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4761 */     _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 4762 */     _jspx_th_c_005fotherwise_005f13.setParent((Tag)_jspx_th_c_005fchoose_005f13);
/* 4763 */     int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 4764 */     if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */       for (;;) {
/* 4766 */         out.write("\n\t\t\t\t        \t<span class=\"bodytext\">&nbsp;");
/* 4767 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fotherwise_005f13, _jspx_page_context))
/* 4768 */           return true;
/* 4769 */         out.write("</span></td>\n\t\t\t\t       ");
/* 4770 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 4771 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4775 */     if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 4776 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 4777 */       return true;
/*      */     }
/* 4779 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 4780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fotherwise_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4785 */     PageContext pageContext = _jspx_page_context;
/* 4786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4788 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4789 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4790 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f13);
/*      */     
/* 4792 */     _jspx_th_c_005fout_005f11.setValue("${FlashForm.displayName}");
/* 4793 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4794 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4795 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4796 */       return true;
/*      */     }
/* 4798 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4799 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4804 */     PageContext pageContext = _jspx_page_context;
/* 4805 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4807 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4808 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 4809 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4811 */     _jspx_th_c_005fif_005f17.setTest("${publishview}");
/* 4812 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 4813 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 4815 */         out.write("\n\t\t\t\t\t<td width=\"10%\"><span class=\"bodytextbold\">&nbsp;&nbsp;");
/* 4816 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f17, _jspx_page_context))
/* 4817 */           return true;
/* 4818 */         out.write("</span></td>\n\t\t\t\t\t");
/* 4819 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 4820 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4824 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 4825 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4826 */       return true;
/*      */     }
/* 4828 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4829 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4834 */     PageContext pageContext = _jspx_page_context;
/* 4835 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4837 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4838 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4839 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 4841 */     _jspx_th_c_005fout_005f12.setValue("${FlashForm.displayName}");
/* 4842 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4843 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4844 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4845 */       return true;
/*      */     }
/* 4847 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f14(JspTag _jspx_th_c_005fchoose_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4853 */     PageContext pageContext = _jspx_page_context;
/* 4854 */     JspWriter out = _jspx_page_context.getOut();
/* 4855 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 4856 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 4858 */     WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4859 */     _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 4860 */     _jspx_th_c_005fwhen_005f14.setParent((Tag)_jspx_th_c_005fchoose_005f14);
/*      */     
/* 4862 */     _jspx_th_c_005fwhen_005f14.setTest("${ FlashForm.showTopLevelMgs!=true }");
/* 4863 */     int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 4864 */     if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */       for (;;) {
/* 4866 */         out.write("\n<tr>\n<td id=\"bvDivFlash\" style=\"height:600px;\">\n           <table width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" border=\"3\">\n               <tr id=\"graphHolder\">\n\t\t\t\t\t\t ");
/* 4867 */         if (_jspx_meth_c_005fchoose_005f15(_jspx_th_c_005fwhen_005f14, _jspx_page_context))
/* 4868 */           return true;
/* 4869 */         out.write("\n               </tr>\n            </table>\n            </td>\n            </tr>\n    \t");
/* 4870 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 4871 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4875 */     if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 4876 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 4877 */       return true;
/*      */     }
/* 4879 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 4880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f15(JspTag _jspx_th_c_005fwhen_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4885 */     PageContext pageContext = _jspx_page_context;
/* 4886 */     JspWriter out = _jspx_page_context.getOut();
/* 4887 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 4888 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 4890 */     ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4891 */     _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 4892 */     _jspx_th_c_005fchoose_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f14);
/* 4893 */     int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 4894 */     if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */       for (;;) {
/* 4896 */         out.write("\n\t\t\t\t\t");
/* 4897 */         if (_jspx_meth_c_005fwhen_005f15(_jspx_th_c_005fchoose_005f15, _jspx_page_context))
/* 4898 */           return true;
/* 4899 */         out.write("\n\t\t\t\t   ");
/* 4900 */         if (_jspx_meth_c_005fotherwise_005f14(_jspx_th_c_005fchoose_005f15, _jspx_page_context))
/* 4901 */           return true;
/* 4902 */         out.write("\n\t\t\t\t   ");
/* 4903 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 4904 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4908 */     if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 4909 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 4910 */       return true;
/*      */     }
/* 4912 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 4913 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f15(JspTag _jspx_th_c_005fchoose_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4918 */     PageContext pageContext = _jspx_page_context;
/* 4919 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4921 */     WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4922 */     _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 4923 */     _jspx_th_c_005fwhen_005f15.setParent((Tag)_jspx_th_c_005fchoose_005f15);
/*      */     
/* 4925 */     _jspx_th_c_005fwhen_005f15.setTest("${ FlashForm.isHtml != true}");
/* 4926 */     int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 4927 */     if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */       for (;;) {
/* 4929 */         out.write("\n                   <td  width=\"100%\" height=\"100%\"  id=\"swfHolder\" bgcolor='");
/* 4930 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f15, _jspx_page_context))
/* 4931 */           return true;
/* 4932 */         out.write("' >\n\t\t\t\t\t<script type=\"text/javascript\" >\n\t\t\t\t\t\tvar locationStr = parent.window.document.location.href;\n\t\t\t\t\t\tif(locationStr.indexOf(\"method=popOut\") > -1 || locationStr.indexOf(\"method=popUp\") > -1) \n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tvar scrHeight = screen.availHeight-100;\n\t\t\t\t\t\t\tgenerateSWFObject(\"monitorreport\",\"/flash/index.swf\",'");
/* 4933 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f15, _jspx_page_context))
/* 4934 */           return true;
/* 4935 */         out.write("',scrHeight); //NO I18N\n\t\t\t\t\t\t}\n\t\t\t\t\t\telse\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tgenerateSWFObject(\"monitorreport\",\"/flash/index.swf\",'");
/* 4936 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fwhen_005f15, _jspx_page_context))
/* 4937 */           return true;
/* 4938 */         out.write("',\"800px\"); //NO I18N\n\t\t\t\t\t\t}\n\t\t\t\t\t\t\n                                        </script>\n                   </td>\n\t\t\t\t   \n\t\t\t\t   ");
/* 4939 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 4940 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4944 */     if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 4945 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 4946 */       return true;
/*      */     }
/* 4948 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 4949 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4954 */     PageContext pageContext = _jspx_page_context;
/* 4955 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4957 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4958 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4959 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f15);
/*      */     
/* 4961 */     _jspx_th_c_005fout_005f13.setValue("${FlashForm.bgColor}");
/* 4962 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4963 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4964 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4965 */       return true;
/*      */     }
/* 4967 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4973 */     PageContext pageContext = _jspx_page_context;
/* 4974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4976 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4977 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4978 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f15);
/*      */     
/* 4980 */     _jspx_th_c_005fout_005f14.setValue("${FlashForm.bgColor}");
/* 4981 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4982 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4983 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4984 */       return true;
/*      */     }
/* 4986 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4987 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fwhen_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4992 */     PageContext pageContext = _jspx_page_context;
/* 4993 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4995 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4996 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4997 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f15);
/*      */     
/* 4999 */     _jspx_th_c_005fout_005f15.setValue("${FlashForm.bgColor}");
/* 5000 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5001 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5002 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5003 */       return true;
/*      */     }
/* 5005 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5006 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f14(JspTag _jspx_th_c_005fchoose_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5011 */     PageContext pageContext = _jspx_page_context;
/* 5012 */     JspWriter out = _jspx_page_context.getOut();
/* 5013 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5014 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5016 */     OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5017 */     _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 5018 */     _jspx_th_c_005fotherwise_005f14.setParent((Tag)_jspx_th_c_005fchoose_005f15);
/* 5019 */     int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 5020 */     if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */       for (;;) {
/* 5022 */         out.write("\n\t\t\t\t\t\t <td  width=\"100%\" height=\"100%\"  id=\"monitorReportInHtml\"  >\n\t\t\t\t\t\n\t\t\t\t\t\t\t<div  id=\"monitorReportInHtmlJSP\">\n\t\t\t\t\t\t\t\t");
/* 5023 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/ShowRelationshipView.jsp", out, true);
/* 5024 */         out.write("\n\t\t\t\t\t\t\t</div></td>\n\t\t\t\t   ");
/* 5025 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 5026 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5030 */     if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 5031 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 5032 */       return true;
/*      */     }
/* 5034 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 5035 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5040 */     PageContext pageContext = _jspx_page_context;
/* 5041 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5043 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5044 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5045 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5047 */     _jspx_th_c_005fset_005f2.setVar("mgurl");
/* 5048 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5049 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5050 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5051 */         out = _jspx_page_context.pushBody();
/* 5052 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 5053 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5054 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5057 */         out.write("/showapplication.do?method=showApplication&haid=");
/* 5058 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fset_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5059 */           return true;
/* 5060 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5061 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5064 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5065 */         out = _jspx_page_context.popBody();
/* 5066 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 5069 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5070 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5071 */       return true;
/*      */     }
/* 5073 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5079 */     PageContext pageContext = _jspx_page_context;
/* 5080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5082 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5083 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5084 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5086 */     _jspx_th_c_005fout_005f16.setValue("${monitorgroup }");
/* 5087 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5088 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5089 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5090 */       return true;
/*      */     }
/* 5092 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f27(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5098 */     PageContext pageContext = _jspx_page_context;
/* 5099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5101 */     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5102 */     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 5103 */     _jspx_th_c_005fif_005f27.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5105 */     _jspx_th_c_005fif_005f27.setTest("${counter.count % FlashForm.noOfColumns==1}");
/* 5106 */     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 5107 */     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */       for (;;) {
/* 5109 */         out.write("\n\t <tr valign=\"top\"  >\n\t ");
/* 5110 */         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 5111 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5115 */     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 5116 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 5117 */       return true;
/*      */     }
/* 5119 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 5120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f16(JspTag _jspx_th_c_005fchoose_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5125 */     PageContext pageContext = _jspx_page_context;
/* 5126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5128 */     WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5129 */     _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 5130 */     _jspx_th_c_005fwhen_005f16.setParent((Tag)_jspx_th_c_005fchoose_005f16);
/*      */     
/* 5132 */     _jspx_th_c_005fwhen_005f16.setTest("${alert_mg[key2]=='5'}");
/* 5133 */     int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 5134 */     if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */       for (;;) {
/* 5136 */         out.write("\n\t\t <a href=\"");
/* 5137 */         if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fwhen_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5138 */           return true;
/* 5139 */         out.write("\" target=\"_top\" onMouseOut=\"hideddrivetip()\" onMouseOver=\"ddrivetip(this,event,'");
/* 5140 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fwhen_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5141 */           return true;
/* 5142 */         out.write("',false,true,'#000000',200,'lightyellow')\" style=\"cursor: pointer;\"><img  border=\"0\"  src=\"/images/mg_clear.gif\"/></a>\n\t\t ");
/* 5143 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 5144 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5148 */     if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 5149 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 5150 */       return true;
/*      */     }
/* 5152 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 5153 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fwhen_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5158 */     PageContext pageContext = _jspx_page_context;
/* 5159 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5161 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5162 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5163 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fwhen_005f16);
/*      */     
/* 5165 */     _jspx_th_c_005fout_005f17.setValue("${mgurl}");
/* 5166 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5167 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5168 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5169 */       return true;
/*      */     }
/* 5171 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5172 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fwhen_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5177 */     PageContext pageContext = _jspx_page_context;
/* 5178 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5180 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5181 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5182 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fwhen_005f16);
/*      */     
/* 5184 */     _jspx_th_c_005fout_005f18.setValue("${alert_mg[key1]}");
/* 5185 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5186 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5187 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5188 */       return true;
/*      */     }
/* 5190 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5191 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f17(JspTag _jspx_th_c_005fchoose_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5196 */     PageContext pageContext = _jspx_page_context;
/* 5197 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5199 */     WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5200 */     _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 5201 */     _jspx_th_c_005fwhen_005f17.setParent((Tag)_jspx_th_c_005fchoose_005f16);
/*      */     
/* 5203 */     _jspx_th_c_005fwhen_005f17.setTest("${alert_mg[key2]=='1'}");
/* 5204 */     int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 5205 */     if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */       for (;;) {
/* 5207 */         out.write("\n\t\t  ");
/* 5208 */         if (_jspx_meth_c_005fchoose_005f17(_jspx_th_c_005fwhen_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5209 */           return true;
/* 5210 */         out.write("\n\t\t ");
/* 5211 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 5212 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5216 */     if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 5217 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 5218 */       return true;
/*      */     }
/* 5220 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 5221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f17(JspTag _jspx_th_c_005fwhen_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5226 */     PageContext pageContext = _jspx_page_context;
/* 5227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5229 */     ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5230 */     _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 5231 */     _jspx_th_c_005fchoose_005f17.setParent((Tag)_jspx_th_c_005fwhen_005f17);
/* 5232 */     int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 5233 */     if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */       for (;;) {
/* 5235 */         out.write("\n\t\t\t");
/* 5236 */         if (_jspx_meth_c_005fwhen_005f18(_jspx_th_c_005fchoose_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5237 */           return true;
/* 5238 */         out.write("\n\t\t\t");
/* 5239 */         if (_jspx_meth_c_005fwhen_005f19(_jspx_th_c_005fchoose_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5240 */           return true;
/* 5241 */         out.write("\n\t\t\t");
/* 5242 */         if (_jspx_meth_c_005fotherwise_005f16(_jspx_th_c_005fchoose_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5243 */           return true;
/* 5244 */         out.write("\n\t\t  ");
/* 5245 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 5246 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5250 */     if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 5251 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 5252 */       return true;
/*      */     }
/* 5254 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 5255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f18(JspTag _jspx_th_c_005fchoose_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5260 */     PageContext pageContext = _jspx_page_context;
/* 5261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5263 */     WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5264 */     _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 5265 */     _jspx_th_c_005fwhen_005f18.setParent((Tag)_jspx_th_c_005fchoose_005f17);
/*      */     
/* 5267 */     _jspx_th_c_005fwhen_005f18.setTest("${alert_mg[key3] == '1'}");
/* 5268 */     int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 5269 */     if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */       for (;;) {
/* 5271 */         out.write("\n\t\t\t <a href=\"");
/* 5272 */         if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fwhen_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5273 */           return true;
/* 5274 */         out.write("\" target=\"_top\"  onMouseOut=\"hideddrivetip()\" onMouseOver=\"ddrivetip(this,event,'");
/* 5275 */         if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fwhen_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5276 */           return true;
/* 5277 */         out.write("',false,true,'#000000',200,'lightyellow')\" style=\"cursor: pointer;\"><img border=\"0\" src=\"/images/mg_down.gif\"/></a>\n\t\t\t");
/* 5278 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 5279 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5283 */     if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 5284 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 5285 */       return true;
/*      */     }
/* 5287 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 5288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fwhen_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5293 */     PageContext pageContext = _jspx_page_context;
/* 5294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5296 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5297 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5298 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fwhen_005f18);
/*      */     
/* 5300 */     _jspx_th_c_005fout_005f19.setValue("${mgurl}");
/* 5301 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5302 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5303 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5304 */       return true;
/*      */     }
/* 5306 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5307 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fwhen_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5312 */     PageContext pageContext = _jspx_page_context;
/* 5313 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5315 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5316 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5317 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fwhen_005f18);
/*      */     
/* 5319 */     _jspx_th_c_005fout_005f20.setValue("${alert_mg[key1]}");
/* 5320 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5321 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5322 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5323 */       return true;
/*      */     }
/* 5325 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f19(JspTag _jspx_th_c_005fchoose_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5331 */     PageContext pageContext = _jspx_page_context;
/* 5332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5334 */     WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5335 */     _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 5336 */     _jspx_th_c_005fwhen_005f19.setParent((Tag)_jspx_th_c_005fchoose_005f17);
/*      */     
/* 5338 */     _jspx_th_c_005fwhen_005f19.setTest("${alert_mg[key3] == '5'}");
/* 5339 */     int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 5340 */     if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */       for (;;) {
/* 5342 */         out.write("\n\t\t\t <a href=\"");
/* 5343 */         if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fwhen_005f19, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5344 */           return true;
/* 5345 */         out.write("\" target=\"_top\" onMouseOut=\"hideddrivetip()\" onMouseOver=\"ddrivetip(this,event,'");
/* 5346 */         if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fwhen_005f19, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5347 */           return true;
/* 5348 */         out.write("',false,true,'#000000',200,'lightyellow')\" style=\"cursor: pointer;\"><img border=\"0\"  src=\"/images/mg_critical.gif\"/></a>\n\t\t\t");
/* 5349 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 5350 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5354 */     if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 5355 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 5356 */       return true;
/*      */     }
/* 5358 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 5359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fwhen_005f19, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5364 */     PageContext pageContext = _jspx_page_context;
/* 5365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5367 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5368 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5369 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fwhen_005f19);
/*      */     
/* 5371 */     _jspx_th_c_005fout_005f21.setValue("${mgurl}");
/* 5372 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5373 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5374 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5375 */       return true;
/*      */     }
/* 5377 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fwhen_005f19, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5383 */     PageContext pageContext = _jspx_page_context;
/* 5384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5386 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5387 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5388 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fwhen_005f19);
/*      */     
/* 5390 */     _jspx_th_c_005fout_005f22.setValue("${alert_mg[key1]}");
/* 5391 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5392 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5393 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5394 */       return true;
/*      */     }
/* 5396 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5397 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f16(JspTag _jspx_th_c_005fchoose_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5402 */     PageContext pageContext = _jspx_page_context;
/* 5403 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5405 */     OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5406 */     _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 5407 */     _jspx_th_c_005fotherwise_005f16.setParent((Tag)_jspx_th_c_005fchoose_005f17);
/* 5408 */     int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 5409 */     if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */       for (;;) {
/* 5411 */         out.write("\n\t\t\t<a href=\"");
/* 5412 */         if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fotherwise_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5413 */           return true;
/* 5414 */         out.write("\" target=\"_top\" onMouseOut=\"hideddrivetip()\" onMouseOver=\"ddrivetip(this,event,'");
/* 5415 */         if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fotherwise_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5416 */           return true;
/* 5417 */         out.write("',false,true,'#000000',200,'lightyellow')\" style=\"cursor: pointer;\"><img border=\"0\"  src=\"/images/mg_critical.gif\"/></a>\n\t\t\t");
/* 5418 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 5419 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5423 */     if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 5424 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 5425 */       return true;
/*      */     }
/* 5427 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 5428 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fotherwise_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5433 */     PageContext pageContext = _jspx_page_context;
/* 5434 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5436 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5437 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5438 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fotherwise_005f16);
/*      */     
/* 5440 */     _jspx_th_c_005fout_005f23.setValue("${mgurl}");
/* 5441 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5442 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5443 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5444 */       return true;
/*      */     }
/* 5446 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5447 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fotherwise_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5452 */     PageContext pageContext = _jspx_page_context;
/* 5453 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5455 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5456 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5457 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fotherwise_005f16);
/*      */     
/* 5459 */     _jspx_th_c_005fout_005f24.setValue("${alert_mg[key1]}");
/* 5460 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5461 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5462 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5463 */       return true;
/*      */     }
/* 5465 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5466 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f20(JspTag _jspx_th_c_005fchoose_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5471 */     PageContext pageContext = _jspx_page_context;
/* 5472 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5474 */     WhenTag _jspx_th_c_005fwhen_005f20 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5475 */     _jspx_th_c_005fwhen_005f20.setPageContext(_jspx_page_context);
/* 5476 */     _jspx_th_c_005fwhen_005f20.setParent((Tag)_jspx_th_c_005fchoose_005f16);
/*      */     
/* 5478 */     _jspx_th_c_005fwhen_005f20.setTest("${alert_mg[key2]=='4'}");
/* 5479 */     int _jspx_eval_c_005fwhen_005f20 = _jspx_th_c_005fwhen_005f20.doStartTag();
/* 5480 */     if (_jspx_eval_c_005fwhen_005f20 != 0) {
/*      */       for (;;) {
/* 5482 */         out.write("\n\t\t <a href=\"");
/* 5483 */         if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fwhen_005f20, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5484 */           return true;
/* 5485 */         out.write("\"  target=\"_top\" onMouseOut=\"hideddrivetip()\" onMouseOver=\"ddrivetip(this,event,'");
/* 5486 */         if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fwhen_005f20, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5487 */           return true;
/* 5488 */         out.write("',false,true,'#000000',200,'lightyellow')\" style=\"cursor: pointer;\"><img border=\"0\"  src=\"/images/mg_warning.gif\"/></a>\n\t\t ");
/* 5489 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f20.doAfterBody();
/* 5490 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5494 */     if (_jspx_th_c_005fwhen_005f20.doEndTag() == 5) {
/* 5495 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 5496 */       return true;
/*      */     }
/* 5498 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 5499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fwhen_005f20, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5504 */     PageContext pageContext = _jspx_page_context;
/* 5505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5507 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5508 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5509 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fwhen_005f20);
/*      */     
/* 5511 */     _jspx_th_c_005fout_005f25.setValue("${mgurl}");
/* 5512 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5513 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5514 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5515 */       return true;
/*      */     }
/* 5517 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5518 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fwhen_005f20, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5523 */     PageContext pageContext = _jspx_page_context;
/* 5524 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5526 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5527 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5528 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fwhen_005f20);
/*      */     
/* 5530 */     _jspx_th_c_005fout_005f26.setValue("${alert_mg[key1]}");
/* 5531 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5532 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5533 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5534 */       return true;
/*      */     }
/* 5536 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5537 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fotherwise_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5542 */     PageContext pageContext = _jspx_page_context;
/* 5543 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5545 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5546 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5547 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fotherwise_005f17);
/*      */     
/* 5549 */     _jspx_th_c_005fout_005f27.setValue("${mgurl}");
/* 5550 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5551 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5552 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5553 */       return true;
/*      */     }
/* 5555 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5556 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5561 */     PageContext pageContext = _jspx_page_context;
/* 5562 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5564 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5565 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5566 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5568 */     _jspx_th_c_005fout_005f28.setValue("${mgurl}");
/* 5569 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5570 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5571 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5572 */       return true;
/*      */     }
/* 5574 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5575 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5580 */     PageContext pageContext = _jspx_page_context;
/* 5581 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5583 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5584 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5585 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5587 */     _jspx_th_c_005fout_005f29.setValue("${displayNames[monitorgroup]}");
/* 5588 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5589 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5590 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5591 */       return true;
/*      */     }
/* 5593 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5594 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f28(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5599 */     PageContext pageContext = _jspx_page_context;
/* 5600 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5602 */     IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5603 */     _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 5604 */     _jspx_th_c_005fif_005f28.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5606 */     _jspx_th_c_005fif_005f28.setTest("${counter.count % FlashForm.noOfColumns==0}");
/* 5607 */     int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 5608 */     if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */       for (;;) {
/* 5610 */         out.write("\n         </tr>\n\t ");
/* 5611 */         int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 5612 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5616 */     if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 5617 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 5618 */       return true;
/*      */     }
/* 5620 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 5621 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f29(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5626 */     PageContext pageContext = _jspx_page_context;
/* 5627 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5629 */     IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5630 */     _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 5631 */     _jspx_th_c_005fif_005f29.setParent(null);
/*      */     
/* 5633 */     _jspx_th_c_005fif_005f29.setTest("${not empty param.isPopUp}");
/* 5634 */     int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 5635 */     if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */       for (;;) {
/* 5637 */         out.write("\n\n</body>\n</html>\n");
/* 5638 */         int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 5639 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5643 */     if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 5644 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 5645 */       return true;
/*      */     }
/* 5647 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 5648 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\FlashView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */