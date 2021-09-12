/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
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
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ProcessesInVM_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 2186 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2187 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2206 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2210 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2222 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2226 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2233 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2234 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2243 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2246 */     JspWriter out = null;
/* 2247 */     Object page = this;
/* 2248 */     JspWriter _jspx_out = null;
/* 2249 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2253 */       response.setContentType("text/html;charset=UTF-8");
/* 2254 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2256 */       _jspx_page_context = pageContext;
/* 2257 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2258 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2259 */       session = pageContext.getSession();
/* 2260 */       out = pageContext.getOut();
/* 2261 */       _jspx_out = out;
/*      */       
/* 2263 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/* 2264 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2266 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2267 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2268 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2270 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2272 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2274 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2276 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2277 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2278 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2279 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2282 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2283 */         String available = null;
/* 2284 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2285 */         out.write(10);
/*      */         
/* 2287 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2288 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2289 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2291 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2293 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2295 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2297 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2298 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2299 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2300 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2303 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2304 */           String unavailable = null;
/* 2305 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2306 */           out.write(10);
/*      */           
/* 2308 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2309 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2310 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2312 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2314 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2316 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2318 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2319 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2320 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2321 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2324 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2325 */             String unmanaged = null;
/* 2326 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2327 */             out.write(10);
/*      */             
/* 2329 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2330 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2331 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2333 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2335 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2337 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2339 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2340 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2341 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2342 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2345 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2346 */               String scheduled = null;
/* 2347 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2348 */               out.write(10);
/*      */               
/* 2350 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2351 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2352 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2354 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2356 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2358 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2360 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2361 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2362 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2363 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2366 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2367 */                 String critical = null;
/* 2368 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2369 */                 out.write(10);
/*      */                 
/* 2371 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2372 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2373 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2375 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2377 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2379 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2381 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2382 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2383 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2384 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2387 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2388 */                   String clear = null;
/* 2389 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2390 */                   out.write(10);
/*      */                   
/* 2392 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2393 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2394 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2396 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2398 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2400 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2402 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2403 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2404 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2405 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2408 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2409 */                     String warning = null;
/* 2410 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2411 */                     out.write(10);
/* 2412 */                     out.write(10);
/*      */                     
/* 2414 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2415 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2417 */                     out.write(10);
/* 2418 */                     out.write(10);
/* 2419 */                     out.write(10);
/* 2420 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/validation.js\"></SCRIPT>\n<SCRIPT>\nfunction enableReports()\n{\n\tif(!checkforOneSelected(document.removemonitor,\"monitors\"))\n\t{\n\t\talert(\"");
/* 2421 */                     out.print(FormatUtil.getString("am.webclient.hostresourceprocess.alert.enable"));
/* 2422 */                     out.write("\");\n\t\treturn;\n\t}\n\tdocument.removemonitor.enabled.value='true';\n\tdocument.removemonitor.submit();\n}\n\nfunction disableReports()\n{\n\tif(!checkforOneSelected(document.removemonitor,\"monitors\"))\n\t{\n\t\talert(\"");
/* 2423 */                     out.print(FormatUtil.getString("am.webclient.hostresourceprocess.alert.disable"));
/* 2424 */                     out.write("\");\n\t\treturn;\n\t}\n\tdocument.removemonitor.enabled.value ='false';\n\tdocument.removemonitor.submit();\n}\nfunction removeMonitors()\n{\n\tif(!checkforOneSelected(document.removemonitor,\"monitors\"))\n\t{\n\t\talert(\"");
/* 2425 */                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.processdel"));
/* 2426 */                     out.write("\");\n\t\treturn;\n\t}\n\n\tif(confirm(\"");
/* 2427 */                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.processdelconfirm"));
/* 2428 */                     out.write("\"))\n\t{\n\tdocument.removemonitor.submit();\n\t}\n}\nfunction fnSelectAll(e)\n{\n \tToggleAll(e,document.removemonitor,\"monitors\"); //NO I18N\n}\n</SCRIPT>\n<title>");
/* 2429 */                     out.print(FormatUtil.getString("Process"));
/* 2430 */                     out.write("</title>\n  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n  <link href=\"/images/");
/* 2431 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2433 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\"/>\n  <link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\" />\n</head>\n<body ><br/>\n");
/*      */                     
/* 2435 */                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2436 */                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2437 */                     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2438 */                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2439 */                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                       for (;;) {
/* 2441 */                         out.write(10);
/*      */                         
/* 2443 */                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2444 */                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2445 */                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                         
/* 2447 */                         _jspx_th_c_005fwhen_005f0.setTest("${isGuestConfigured}");
/* 2448 */                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2449 */                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                           for (;;) {
/* 2451 */                             out.write("\n\t  ");
/*      */                             
/* 2453 */                             Properties alert = null;
/* 2454 */                             String resourceid = request.getParameter("resourceid");
/* 2455 */                             String tabId = request.getParameter("tabId");
/* 2456 */                             String redirecting = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid;
/* 2457 */                             String encodeurl = URLEncoder.encode(redirecting);
/* 2458 */                             String resourcename = null;
/* 2459 */                             ArrayList attribIDs = new ArrayList();
/* 2460 */                             attribIDs.add("715");
/* 2461 */                             attribIDs.add("716");
/* 2462 */                             ArrayList resIDs = new ArrayList();
/* 2463 */                             ArrayList processlists = (ArrayList)request.getAttribute("processList");
/* 2464 */                             if (processlists != null)
/*      */                             {
/* 2466 */                               for (int i = 0; i < processlists.size(); i++)
/*      */                               {
/* 2468 */                                 ArrayList processlist = (ArrayList)processlists.get(i);
/* 2469 */                                 resIDs.add((String)processlist.get(0));
/*      */                               }
/*      */                             }
/* 2472 */                             alert = getStatus(resIDs, attribIDs);
/*      */                             
/* 2474 */                             out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n<td>\n<div class=\"apmconf-table-frame\">\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"   style=\"width:99%; margin-left:10px;\">\n\t <tr align=\"left\">\n\t <td width=\"72%\" height=\"31\" class=\"conf-mon-txt\">");
/* 2475 */                             out.print(FormatUtil.getString("am.webclient.hostResource.servers.processdetail"));
/* 2476 */                             out.write(" <a name=\"Process\" id=\"Process\"></a></td>\n\t \t");
/*      */                             
/* 2478 */                             PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2479 */                             _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2480 */                             _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 2482 */                             _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2483 */                             int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2484 */                             if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                               for (;;) {
/* 2486 */                                 out.write("\n\t\t<td width=\"28%\" height=\"31\" align=\"right\" class=\"conf-mon-txt\">&nbsp;<a href=\"javascript:alertUser()\" class=\"bodytextboldwhiteun\">");
/* 2487 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.addprocesslink"));
/* 2488 */                                 out.write("</a>&nbsp;</td>\n\t\t");
/* 2489 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2490 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2494 */                             if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2495 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                             }
/*      */                             
/* 2498 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2499 */                             out.write(10);
/* 2500 */                             out.write(9);
/* 2501 */                             out.write(9);
/*      */                             
/* 2503 */                             PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2504 */                             _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2505 */                             _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 2507 */                             _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2508 */                             int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2509 */                             if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                               for (;;) {
/* 2511 */                                 out.write("\n\t\t<td width=\"28%\" height=\"31\" align=\"right\" class=\"conf-mon-txt\">&nbsp;<a href=\"javascript:void(0)\" class=\"staticlinks\" onClick=\"fnOpenNewWindow('../HostResource.do?getProcessList=true&resourceid=");
/* 2512 */                                 if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                                   return;
/* 2514 */                                 out.write("&resType=");
/* 2515 */                                 if (_jspx_meth_c_005fout_005f2(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                                   return;
/* 2517 */                                 out.write("&tabId=");
/* 2518 */                                 out.print(tabId);
/* 2519 */                                 out.write("')\">");
/* 2520 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.addprocesslink"));
/* 2521 */                                 out.write("</a>&nbsp;</td>\n\t  \t");
/* 2522 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2523 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2527 */                             if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2528 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                             }
/*      */                             
/* 2531 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2532 */                             out.write("\n\t </tr>\n\t </table>\n\t  <form action=\"/HostResource.do?removeProcess=true\" name=\"removemonitor\" method=\"post\" style=\"display:inline\">\n\t  ");
/* 2533 */                             out.write("\n\t  <input type=\"hidden\" name=\"enabled\" value=\"NA\"/>\n\t  <input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2534 */                             out.print(resourceid);
/* 2535 */                             out.write("\"/>\n\t  <input type=\"hidden\" size=\"15\" name=\"configured\" value=\"true\"/>\n\t  <input type=\"hidden\" name=\"isVirtualMachine\" value=\"true\"/>\n\t  <input type=\"hidden\" name=\"tabId\" value=\"");
/* 2536 */                             out.print(tabId);
/* 2537 */                             out.write("\"/>\n\t  ");
/* 2538 */                             out.write("\n\t  ");
/* 2539 */                             out.write("\n\t  ");
/*      */                             
/* 2541 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2542 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2543 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 2545 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("processList");
/* 2546 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2547 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 2549 */                                 out.write("\n\t    <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table\" style=\"width:99%; margin-left:10px;\">\t \n\t      <tr>\n\t\t    <td width=\"2%\" height=\"28\" class=\"whitegrayborder-conf-mon\"><input type=\"checkbox\" name=\"headercheckbox\" onClick=\"javascript:fnSelectAll(this)\"></td>\t\t\n\t\t    <td width=\"12%\" height=\"28\"   class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 2550 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2551 */                                 out.write("<span class=\"sortarrow\">&nbsp;</span></td>\n\t\t    <td width=\"12%\" height=\"28\"   class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 2552 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.process"));
/* 2553 */                                 out.write("<span class=\"sortarrow\">&nbsp;</span></td>\n\t\t    <td width=\"10%\" height=\"28\"   class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 2554 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.availability"));
/* 2555 */                                 out.write("<span class=\"sortarrow\">&nbsp;</span></td>\n\t\t    <td width=\"10%\" height=\"28\" class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 2556 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.health"));
/* 2557 */                                 out.write("<span class=\"sortarrow\">&nbsp;</span></td>\n\t\t    <td width=\"10%\" height=\"28\"  class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 2558 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.instance"));
/* 2559 */                                 out.write("<span class=\"sortarrow\">&nbsp;</span></td>\n\t\t    <td width=\"10%\" height=\"28\"   class=\"monitorinfoodd-conf apmconf-dullhead\" align=\"center\">");
/* 2560 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.cpu"));
/* 2561 */                                 out.write("<span class=\"sortarrow\">&nbsp;</span></td>\n\t\t    <td width=\"6%\" height=\"28\"   class=\"monitorinfoodd-conf apmconf-dullhead\">&nbsp;</td>\n\t\t    <td width=\"10%\" height=\"28\"   class=\"monitorinfoodd-conf apmconf-dullhead\" align=\"center\">");
/* 2562 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.mem"));
/* 2563 */                                 out.write("<span class=\"sortarrow\">&nbsp;</span></td>\n\t\t    <td width=\"6%\" height=\"28\"   class=\"monitorinfoodd-conf apmconf-dullhead\">&nbsp;</td>\n\t\t    ");
/*      */                                 
/* 2565 */                                 PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2566 */                                 _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2567 */                                 _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                 
/* 2569 */                                 _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2570 */                                 int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2571 */                                 if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                   for (;;) {
/* 2573 */                                     out.write("\n\t\t    <td width=\"12%\" height=\"28\" align=\"center\"  class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 2574 */                                     out.print(FormatUtil.getString("Actions"));
/* 2575 */                                     out.write("</td>\n\t\t    ");
/* 2576 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2577 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2581 */                                 if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2582 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                 }
/*      */                                 
/* 2585 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2586 */                                 out.write("\t  \n\t      </tr>\n\n\t       ");
/*      */                                 
/* 2588 */                                 Hashtable reportsEnabled = new Hashtable();
/*      */                                 
/*      */                                 try
/*      */                                 {
/* 2592 */                                   AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2593 */                                   String selectQuery = "select RESOURCEID as RESID from AM_HOST_PROCESS_ENABLEREPORTS where PARENTID=" + resourceid;
/* 2594 */                                   ResultSet rs = AMConnectionPool.executeQueryStmt(selectQuery);
/* 2595 */                                   while (rs.next())
/*      */                                   {
/* 2597 */                                     String resId = rs.getString("RESID");
/* 2598 */                                     reportsEnabled.put(resId, resId);
/*      */                                   }
/* 2600 */                                   rs.close();
/*      */                                 }
/*      */                                 catch (Exception e)
/*      */                                 {
/* 2604 */                                   e.printStackTrace();
/*      */                                 }
/*      */                                 
/* 2607 */                                 out.write(10);
/* 2608 */                                 out.write(9);
/* 2609 */                                 out.write(32);
/*      */                                 
/* 2611 */                                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2612 */                                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2613 */                                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                 
/* 2615 */                                 _jspx_th_logic_005fiterate_005f0.setName("processList");
/*      */                                 
/* 2617 */                                 _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                 
/* 2619 */                                 _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                 
/* 2621 */                                 _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2622 */                                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2623 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2624 */                                   ArrayList row = null;
/* 2625 */                                   Integer j = null;
/* 2626 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2627 */                                     out = _jspx_page_context.pushBody();
/* 2628 */                                     _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2629 */                                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                   }
/* 2631 */                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2632 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                   for (;;) {
/* 2634 */                                     out.write(10);
/* 2635 */                                     out.write(9);
/* 2636 */                                     out.write(32);
/*      */                                     
/* 2638 */                                     String processid = (String)row.get(0);
/* 2639 */                                     String processname = (String)row.get(1);
/* 2640 */                                     String process = (String)row.get(2);
/* 2641 */                                     String command = (String)row.get(3);
/* 2642 */                                     String instance1 = (String)row.get(4);
/* 2643 */                                     String pcpu = (String)row.get(5);
/* 2644 */                                     if ((instance1 == null) || (instance1.equals("null")) || (instance1.equals("NULL")) || (instance1.equals("-1")) || (instance1.equals("-1.0")))
/*      */                                     {
/* 2646 */                                       instance1 = "-";
/*      */                                     }
/* 2648 */                                     if ((pcpu == null) || (pcpu.equals("null")) || (pcpu.equals("NULL")) || (pcpu.equals("-1")) || (pcpu.equals("-1.0")) || (pcpu.equals("Idle")))
/*      */                                     {
/* 2650 */                                       pcpu = "-";
/*      */                                     }
/* 2652 */                                     String pmem = (String)row.get(6);
/* 2653 */                                     if ((pmem == null) || (pmem.equals("null")) || (pmem.equals("NULL")) || (pmem.equals("-1.0")) || (pmem.equals("-1")) || (pmem.equals("Process")))
/*      */                                     {
/* 2655 */                                       pmem = "-";
/*      */                                     }
/*      */                                     
/* 2658 */                                     String bgclass = "class=\"whitegrayborder-conf-mon\"";
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2668 */                                     out.write("\n\t<tr onmouseout=\"this.className='confheader'\" onmouseover=\"this.className='confHeaderHover'\" class=\"confheader\">\n\t<td   ");
/* 2669 */                                     out.print(bgclass);
/* 2670 */                                     out.write(" ><input type=\"checkbox\" name=\"monitors\" value=\"");
/* 2671 */                                     out.print(processid);
/* 2672 */                                     out.write("\">\n\t </td>\n\t <td  ");
/* 2673 */                                     out.print(bgclass);
/* 2674 */                                     out.write(" > <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2675 */                                     out.print(processid);
/* 2676 */                                     out.write("&period=0&resourcename=");
/* 2677 */                                     out.print(processname);
/* 2678 */                                     out.write("')\" class=\"resourcename\">");
/* 2679 */                                     out.print(processname);
/* 2680 */                                     out.write("</a> ");
/* 2681 */                                     if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.underMaintenance(processid)) {
/* 2682 */                                       out.write("<img src=\"/images/down-time-icon.gif\">");
/*      */                                     }
/* 2684 */                                     out.write(" </td>\n\t <td  ");
/* 2685 */                                     out.print(bgclass);
/* 2686 */                                     out.write(" title=\"");
/* 2687 */                                     out.print(process);
/* 2688 */                                     out.write("\" > ");
/* 2689 */                                     out.print(getTrimmedText(process, 15));
/* 2690 */                                     out.write("</td>\n\t <td  ");
/* 2691 */                                     out.print(bgclass);
/* 2692 */                                     out.write("  ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2693 */                                     out.print(processid);
/* 2694 */                                     out.write("&attributeid=715')\">");
/* 2695 */                                     out.print(getSeverityImageForAvailability(alert.getProperty(processid + "#" + "715")));
/* 2696 */                                     out.write("</a><span style=\"display: none;\">");
/* 2697 */                                     out.print(alert.getProperty(processid + "#" + "715"));
/* 2698 */                                     out.write("</span></td>\n\t <td  ");
/* 2699 */                                     out.print(bgclass);
/* 2700 */                                     out.write("  ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2701 */                                     out.print(processid);
/* 2702 */                                     out.write("&attributeid=716')\">");
/* 2703 */                                     out.print(getSeverityImageForHealth(alert.getProperty(processid + "#" + "716")));
/* 2704 */                                     out.write("&nbsp;</a><span style=\"display: none;\">");
/* 2705 */                                     out.print(alert.getProperty(processid + "#" + "716"));
/* 2706 */                                     out.write("</span></td>\n");
/*      */                                     
/* 2708 */                                     if (instance1.equals("-"))
/*      */                                     {
/* 2710 */                                       out.write("\n\t <td  ");
/* 2711 */                                       out.print(bgclass);
/* 2712 */                                       out.write(" align=\"left\" title=\"");
/* 2713 */                                       out.print(instance1);
/* 2714 */                                       out.write("\"><span>");
/* 2715 */                                       out.print(instance1);
/* 2716 */                                       out.write("</span></td>\n");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2720 */                                       out.write("\n\t <td  ");
/* 2721 */                                       out.print(bgclass);
/* 2722 */                                       out.write(" align=\"left\" title=\"");
/* 2723 */                                       out.print(instance1);
/* 2724 */                                       out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2725 */                                       out.print(processid);
/* 2726 */                                       out.write("&attributeid=717&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span>");
/* 2727 */                                       out.print(instance1);
/* 2728 */                                       out.write("</span></a></td>\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 2732 */                                     out.write("\n\t <!--<td   ");
/* 2733 */                                     out.print(bgclass);
/* 2734 */                                     out.write(" title=\"");
/* 2735 */                                     out.print(command);
/* 2736 */                                     out.write("\" > ");
/* 2737 */                                     out.print(getTrimmedText(command, 15));
/* 2738 */                                     out.write("</td>-->\n");
/*      */                                     
/* 2740 */                                     if (pcpu.equals("-"))
/*      */                                     {
/* 2742 */                                       out.write("\n\t <td  ");
/* 2743 */                                       out.print(bgclass);
/* 2744 */                                       out.write(" align=\"center\" title=\"");
/* 2745 */                                       out.print(pcpu);
/* 2746 */                                       out.write("\"><span>");
/* 2747 */                                       out.print(pcpu);
/* 2748 */                                       out.write("</span></td>\n");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2752 */                                       out.write("\n\t <td   ");
/* 2753 */                                       out.print(bgclass);
/* 2754 */                                       out.write(" align=\"center\" title=\"");
/* 2755 */                                       out.print(pcpu);
/* 2756 */                                       out.write("\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2757 */                                       out.print(processid);
/* 2758 */                                       out.write("&attributeid=718&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span>");
/* 2759 */                                       out.print(FormatUtil.formatNumber(pcpu));
/* 2760 */                                       out.write("</span></a></td>\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 2764 */                                     out.write("\n\n\t ");
/*      */                                     
/* 2766 */                                     String modeOfMon = (String)request.getAttribute("mode");
/* 2767 */                                     if ((reportsEnabled.get(processid) != null) && (!modeOfMon.equals("SNMP")) && (!modeOfMon.equals("WMI")))
/*      */                                     {
/*      */ 
/* 2770 */                                       out.write("\n\t <td   ");
/* 2771 */                                       out.print(bgclass);
/* 2772 */                                       out.write(" align=\"left\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2773 */                                       out.print(processid);
/* 2774 */                                       out.write("&attributeid=718&period=-7')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2775 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2776 */                                       out.write("\"></a></td>\n\t ");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 2782 */                                       out.write("\n\t   <td   ");
/* 2783 */                                       out.print(bgclass);
/* 2784 */                                       out.write(" align=\"left\">&nbsp;</td>\n\t   ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 2788 */                                     out.write(10);
/*      */                                     
/* 2790 */                                     if (pmem.equals("-"))
/*      */                                     {
/* 2792 */                                       out.write("\n\t <td  ");
/* 2793 */                                       out.print(bgclass);
/* 2794 */                                       out.write(" align=\"center\" title=\"");
/* 2795 */                                       out.print(pmem);
/* 2796 */                                       out.write("\"><span>");
/* 2797 */                                       out.print(pmem);
/* 2798 */                                       out.write("</span></td>\n");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2802 */                                       out.write("\n\t <td   ");
/* 2803 */                                       out.print(bgclass);
/* 2804 */                                       out.write(" align=\"center\" title=\"");
/* 2805 */                                       out.print(pmem);
/* 2806 */                                       out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2807 */                                       out.print(processid);
/* 2808 */                                       out.write("&attributeid=719&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span>");
/* 2809 */                                       out.print(getTrimmedText(FormatUtil.formatNumber(pmem), 7));
/* 2810 */                                       out.write("</span></a></td>\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 2814 */                                     out.write(10);
/* 2815 */                                     out.write(9);
/* 2816 */                                     out.write(32);
/*      */                                     
/* 2818 */                                     if (reportsEnabled.get(processid) != null)
/*      */                                     {
/*      */ 
/* 2821 */                                       out.write("\n\t <td   ");
/* 2822 */                                       out.print(bgclass);
/* 2823 */                                       out.write(" align=\"left\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2824 */                                       out.print(processid);
/* 2825 */                                       out.write("&attributeid=719&period=-7')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2826 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2827 */                                       out.write("\"></a></td>\n\t ");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 2833 */                                       out.write("\n\t  <td  ");
/* 2834 */                                       out.print(bgclass);
/* 2835 */                                       out.write(" align=\"left\">&nbsp;</td>\n\t ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 2839 */                                     out.write("\n         ");
/*      */                                     
/* 2841 */                                     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2842 */                                     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2843 */                                     _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                     
/* 2845 */                                     _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 2846 */                                     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2847 */                                     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                       for (;;) {
/* 2849 */                                         out.write("\n\t<td    ");
/* 2850 */                                         out.print(bgclass);
/* 2851 */                                         out.write("  align=\"center\"><a href=\"/HostResource.do?editProcess=true&processid=");
/* 2852 */                                         out.print(processid);
/* 2853 */                                         out.write("&resourceid=");
/* 2854 */                                         out.print(resourceid);
/* 2855 */                                         out.write("&name=");
/* 2856 */                                         out.print(resourcename);
/* 2857 */                                         out.write("\"><img src=\"/images/icon_edit.gif\" title=\"");
/* 2858 */                                         out.print(FormatUtil.getString("am.webclient.hostresourceconfig.editprocess"));
/* 2859 */                                         out.write("\"  border=\"0\"></a>&nbsp;&nbsp;&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2860 */                                         out.print(processid);
/* 2861 */                                         out.write("&attributeIDs=715,716,717,718,719&attributeToSelect=715&redirectto=");
/* 2862 */                                         out.print(encodeurl);
/* 2863 */                                         out.write("'> <img src=\"/images/icon_associateaction.gif\" title=\"");
/* 2864 */                                         out.print(ALERTCONFIG_TEXT);
/* 2865 */                                         out.write("\" border=\"0\" ></a></td>\n           ");
/* 2866 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2867 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2871 */                                     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2872 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                     }
/*      */                                     
/* 2875 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2876 */                                     out.write("\n\t</tr>\n\t");
/* 2877 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2878 */                                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2879 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 2880 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2883 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2884 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2887 */                                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2888 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                 }
/*      */                                 
/* 2891 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2892 */                                 out.write("\n\t    </table>\n\t  ");
/* 2893 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2894 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2898 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2899 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 2902 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2903 */                             out.write("\n\t  </form>\n\t  ");
/*      */                             
/* 2905 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2906 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2907 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 2909 */                             _jspx_th_logic_005fempty_005f0.setName("processList");
/* 2910 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2911 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 2913 */                                 out.write("\n\t  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table\">\n\t    <tr>\n\t      <td class=\"bodytext\" align=\"center\" height=\"26\"> ");
/* 2914 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.noprocess"));
/* 2915 */                                 out.write(" \t\n\t\t");
/*      */                                 
/* 2917 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2918 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2919 */                                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_logic_005fempty_005f0);
/*      */                                 
/* 2921 */                                 _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 2922 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2923 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 2925 */                                     out.write("<a href=\"javascript:alertUser()\" class=\"staticlinks\">");
/* 2926 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.addprocesslink.second"));
/* 2927 */                                     out.write("</a>\t");
/* 2928 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2929 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2933 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2934 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 2937 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2938 */                                 out.write(32);
/*      */                                 
/* 2940 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2941 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 2942 */                                 _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_logic_005fempty_005f0);
/*      */                                 
/* 2944 */                                 _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 2945 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 2946 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 2948 */                                     out.write("<a href=\"#\" onClick=\"fnOpenNewWindow('../HostResource.do?getProcessList=true&resourceid=");
/* 2949 */                                     if (_jspx_meth_c_005fout_005f3(_jspx_th_logic_005fpresent_005f5, _jspx_page_context))
/*      */                                       return;
/* 2951 */                                     out.write("&resType=");
/* 2952 */                                     if (_jspx_meth_c_005fout_005f4(_jspx_th_logic_005fpresent_005f5, _jspx_page_context))
/*      */                                       return;
/* 2954 */                                     out.write("&tabId=");
/* 2955 */                                     out.print(tabId);
/* 2956 */                                     out.write("')\" class=\"staticlinks\"\t>");
/* 2957 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.addprocesslink.second"));
/* 2958 */                                     out.write("</a>");
/* 2959 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 2960 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2964 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 2965 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 2968 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 2969 */                                 out.write("\n\t      </td>\n\t    </tr>\n\t  </table>\n\t  ");
/* 2970 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2971 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2975 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2976 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 2979 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2980 */                             out.write("\n\t \n\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"width:99%; margin-left:10px;\">\n\t<tr><td style=\"height:30px\">\n\t");
/*      */                             
/* 2982 */                             PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2983 */                             _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 2984 */                             _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 2986 */                             _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 2987 */                             int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 2988 */                             if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                               for (;;) {
/* 2990 */                                 out.write("\n\t<a href=\"javascript:alertUser();\" class=\"staticlinks\">");
/* 2991 */                                 out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 2992 */                                 out.write("</a>\n\t");
/* 2993 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 2994 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2998 */                             if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 2999 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                             }
/*      */                             
/* 3002 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3003 */                             out.write(10);
/* 3004 */                             out.write(9);
/*      */                             
/* 3006 */                             PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3007 */                             _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3008 */                             _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 3010 */                             _jspx_th_logic_005fpresent_005f7.setRole("ADMIN");
/* 3011 */                             int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3012 */                             if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                               for (;;) {
/* 3014 */                                 out.write("\n<span class=\"bodytext\">\t<a href=\"javascript:removeMonitors();\" class=\"staticlinks\">");
/* 3015 */                                 out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3016 */                                 out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;<A HREF=\"javascript:enableReports();\" title=\"");
/* 3017 */                                 out.print(FormatUtil.getString("am.webclient.hostresourceprocess.tooltip.enablemessage"));
/* 3018 */                                 out.write("\" class=\"staticlinks\">");
/* 3019 */                                 out.print(FormatUtil.getString("am.webclient.script.enablereports"));
/* 3020 */                                 out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;<A HREF=\"javascript:disableReports();\" title=\"");
/* 3021 */                                 out.print(FormatUtil.getString("am.webclient.hostresourceprocess.tooltip.disablemessage"));
/* 3022 */                                 out.write("\" class=\"staticlinks\">");
/* 3023 */                                 out.print(FormatUtil.getString("am.webclient.script.disablereports"));
/* 3024 */                                 out.write("</a></span></td>\n\t<td height=\"15\" align=\"right\" >&nbsp;</td>\n\t");
/* 3025 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3026 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3030 */                             if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3031 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                             }
/*      */                             
/* 3034 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3035 */                             out.write("\n\t</tr>\n\t</table>\n</div>\n</td>\n</tr>\n</table>\n");
/* 3036 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3037 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3041 */                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3042 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                         }
/*      */                         
/* 3045 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3046 */                         out.write(10);
/*      */                         
/* 3048 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3049 */                         _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3050 */                         _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 3051 */                         int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3052 */                         if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                           for (;;) {
/* 3054 */                             out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"messagebox\">\n\t    <tr height=\"34px\">\n\t      <td class=\"bodytext\" align=\"center\" height=\"26\">\n\t\t  ");
/*      */                             
/* 3056 */                             PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3057 */                             _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3058 */                             _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                             
/* 3060 */                             _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 3061 */                             int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3062 */                             if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                               for (;;) {
/* 3064 */                                 out.write("\n\t\t ");
/* 3065 */                                 out.print(FormatUtil.getString("am.webclient.vm.config.os.text"));
/* 3066 */                                 out.write(" <a href=\"javascript:alertUser();\" class=\"staticlinks\"\t>");
/* 3067 */                                 out.print(FormatUtil.getString("am.webclient.common.clickhere.text"));
/* 3068 */                                 out.write("</a>\n\t\t");
/* 3069 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3070 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3074 */                             if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3075 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                             }
/*      */                             
/* 3078 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3079 */                             out.write(10);
/* 3080 */                             out.write(9);
/* 3081 */                             out.write(9);
/*      */                             
/* 3083 */                             PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3084 */                             _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3085 */                             _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                             
/* 3087 */                             _jspx_th_logic_005fpresent_005f9.setRole("ADMIN");
/* 3088 */                             int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3089 */                             if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                               for (;;) {
/* 3091 */                                 out.write("\n\t\t ");
/* 3092 */                                 out.print(FormatUtil.getString("am.webclient.vm.config.os.text"));
/* 3093 */                                 out.write(" <a href=\"#\" onClick=\"fnOpenNewWindow('../configureGuestOS.do?method=configureOS&resourceid=");
/* 3094 */                                 if (_jspx_meth_c_005fout_005f5(_jspx_th_logic_005fpresent_005f9, _jspx_page_context))
/*      */                                   return;
/* 3096 */                                 out.write("')\" class=\"staticlinks\"\t>");
/* 3097 */                                 out.print(FormatUtil.getString("am.webclient.common.clickhere.text"));
/* 3098 */                                 out.write("</a>\n\t  \t");
/* 3099 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3100 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3104 */                             if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3105 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                             }
/*      */                             
/* 3108 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3109 */                             out.write("\n\t\t ");
/*      */                             
/* 3111 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3112 */                             _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3113 */                             _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                             
/* 3115 */                             _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO,ADMIN");
/* 3116 */                             int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3117 */                             if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                               for (;;) {
/* 3119 */                                 out.write("\n\t\t      ");
/* 3120 */                                 out.print(FormatUtil.getString("am.webclient.vm.config.os.text"));
/* 3121 */                                 out.write(" <a href=\"javascript:alert('");
/* 3122 */                                 out.print(FormatUtil.getString("am.webclient.vm.config.os.nonadmin.text"));
/* 3123 */                                 out.write("');\" class=\"staticlinks\"\t>");
/* 3124 */                                 out.print(FormatUtil.getString("am.webclient.common.clickhere.text"));
/* 3125 */                                 out.write("</a>\n\t\t  ");
/* 3126 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3127 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3131 */                             if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3132 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                             }
/*      */                             
/* 3135 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3136 */                             out.write("\n\t      </td>\n\t    </tr>\n\t  </table>\n\t  <br/>\n");
/* 3137 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3138 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3142 */                         if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3143 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                         }
/*      */                         
/* 3146 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3147 */                         out.write(10);
/* 3148 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3149 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3153 */                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3154 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */                     }
/*      */                     else {
/* 3157 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3158 */                       out.write("\n</body>\n</html>\n");
/*      */                     }
/* 3160 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3161 */         out = _jspx_out;
/* 3162 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3163 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3164 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3167 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3173 */     PageContext pageContext = _jspx_page_context;
/* 3174 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3176 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3177 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3178 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3180 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3182 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3183 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3184 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3185 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3186 */       return true;
/*      */     }
/* 3188 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3189 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3194 */     PageContext pageContext = _jspx_page_context;
/* 3195 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3197 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3198 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3199 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 3201 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 3202 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3203 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3204 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3205 */       return true;
/*      */     }
/* 3207 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3213 */     PageContext pageContext = _jspx_page_context;
/* 3214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3216 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3217 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3218 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 3220 */     _jspx_th_c_005fout_005f2.setValue("${osType}");
/* 3221 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3222 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3223 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3224 */       return true;
/*      */     }
/* 3226 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3227 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3232 */     PageContext pageContext = _jspx_page_context;
/* 3233 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3235 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3236 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3237 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 3239 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 3240 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3241 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3242 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3243 */       return true;
/*      */     }
/* 3245 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3246 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3251 */     PageContext pageContext = _jspx_page_context;
/* 3252 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3254 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3255 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3256 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 3258 */     _jspx_th_c_005fout_005f4.setValue("${osType}");
/* 3259 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3260 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3261 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3262 */       return true;
/*      */     }
/* 3264 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3265 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_logic_005fpresent_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3270 */     PageContext pageContext = _jspx_page_context;
/* 3271 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3273 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3274 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3275 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f9);
/*      */     
/* 3277 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 3278 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3279 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3280 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3281 */       return true;
/*      */     }
/* 3283 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3284 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ProcessesInVM_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */