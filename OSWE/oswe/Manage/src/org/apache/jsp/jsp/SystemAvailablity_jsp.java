/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.MultiPieChart;
/*      */ import com.manageengine.it360.util.SLAUtil;
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
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ 
/*      */ public final class SystemAvailablity_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 1291 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
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
/* 1989 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
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
/* 2186 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2187 */   static { _jspx_dependants.put("/jsp/includes/SlaLinks.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2206 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2210 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2221 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2225 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2226 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2227 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2229 */     this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.release();
/* 2230 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2231 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2232 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2241 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2244 */     JspWriter out = null;
/* 2245 */     Object page = this;
/* 2246 */     JspWriter _jspx_out = null;
/* 2247 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2251 */       response.setContentType("text/html;charset=UTF-8");
/* 2252 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2254 */       _jspx_page_context = pageContext;
/* 2255 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2256 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2257 */       session = pageContext.getSession();
/* 2258 */       out = pageContext.getOut();
/* 2259 */       _jspx_out = out;
/*      */       
/* 2261 */       out.write("<!DOCTYPE html>\n");
/* 2262 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2263 */       out.write(10);
/* 2264 */       out.write(10);
/*      */       
/* 2266 */       request.setAttribute("HelpKey", "Manager Console");
/* 2267 */       boolean isIT360SLAEnabled = SLAUtil.isIT360SLAEnabled(request);
/* 2268 */       String isdefaultview = request.getAttribute("defaultView") != null ? (String)request.getAttribute("defaultView") : "";
/* 2269 */       boolean isAdminUser = request.isUserInRole("ADMIN");
/*      */       
/* 2271 */       out.write("\n\n\n\n\n\n\n\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2272 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2274 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2276 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2280 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2285 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2286 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2287 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2290 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2291 */         String available = null;
/* 2292 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2293 */         out.write(10);
/*      */         
/* 2295 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2297 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2301 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2306 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2307 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2308 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2311 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2312 */           String unavailable = null;
/* 2313 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2314 */           out.write(10);
/*      */           
/* 2316 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2318 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2322 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2327 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2328 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2329 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2332 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2333 */             String unmanaged = null;
/* 2334 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2335 */             out.write(10);
/*      */             
/* 2337 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2339 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2343 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2348 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2349 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2350 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2353 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2354 */               String scheduled = null;
/* 2355 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2356 */               out.write(10);
/*      */               
/* 2358 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2360 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2364 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2369 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2370 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2371 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2374 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2375 */                 String critical = null;
/* 2376 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2377 */                 out.write(10);
/*      */                 
/* 2379 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2381 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2385 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2390 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2391 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2392 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2395 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2396 */                   String clear = null;
/* 2397 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2398 */                   out.write(10);
/*      */                   
/* 2400 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2402 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2406 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2411 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2412 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2413 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2416 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2417 */                     String warning = null;
/* 2418 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2419 */                     out.write(10);
/* 2420 */                     out.write(10);
/*      */                     
/* 2422 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2423 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/* 2427 */                     out.write(10);
/* 2428 */                     out.write("\n\n\n<script>\nvar searchForm;\nvar reportForm;\nvar showReportsForm;\nfunction myOnLoad()\n{\n\treportForm = document.forms[0];\n\tshowReportsForm = document.forms[1];\n\t");
/* 2429 */                     if ((isdefaultview.equals("true")) && (isAdminUser)) {
/* 2430 */                       out.write("\n    \t searchForm = document.forms[0];\n        reportForm = document.forms[1];\n        showReportsForm = document.forms[2];\n\n        ");
/*      */                     }
/* 2432 */                     out.write("\n  SORTTABLENAME = 'serverSlaTable';\n  numberOfColumnsToBeSorted=1;\n  ignoreCheckBox=false;\n  sortables_init(numberOfColumnsToBeSorted,ignoreCheckBox);\n}\n\nfunction fnCheckCustomTime(f)\n {\n if(f.startDate.value=='')\n {\n alert(\"");
/* 2433 */                     out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/* 2434 */                     out.write("\");\n return false\n }\n else if(f.endDate.value=='')\n {\n alert(\"");
/* 2435 */                     out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/* 2436 */                     out.write("\")\n return false\n }\n var s=f.startDate.value;\n var e=f.endDate.value;\n    if(s>e)\n    {\n     alert(\"");
/* 2437 */                     out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/* 2438 */                     out.write("\" );\n     return false;\n    }\n    f.reportType.value=\"\"\n return true\n }\n function fnSetEndTime(a)\n{\n\tshowReportsForm.endDate.value=a;\n}\nfunction fnSetStartTime(a)\n{\n\tshowReportsForm.startDate.value=a;\n}\n\n function fnPeriod(periodcombo)\n\n{\n\t \treportForm.reportType.value=\"\";\t \n     \treportForm.submit();\n}\n function generateReportFile(reportType){\n\t if(showReportsForm.startDate && showReportsForm.startDate.value==\"\" && showReportsForm.endDate.value==\"\"){\n\t\t reportForm.reportType.value=reportType;\t \n\t\t reportForm.submit();\n\t }\n\t else{\t\n\t\t showReportsForm.reportType.value=reportType;\t \n\t\t showReportsForm.submit();\n\t }\n\t \n }\n//TODO: Need to betterment the implementation by using obj ref 'this' rather than using 4 js method.\n function showAvailabilitySLATab()\n {\t\n \tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n \tjQuery(\"#showAvailabilitySLATab\").show(); //No I18N\n \tjQuery(\"#showServerSLATab\").hide(); //No I18N\n \tjQuery(\"#showEventVolumeSLATab\").hide(); //No I18N\n \tjQuery(\"#showSLASettingsTab\").hide(); //No I18N\n \tjQuery(\"#loadingg\").show(); //No I18N\n");
/* 2439 */                     out.write(" \tdocument.location.href=\"/showBussiness.do?method=generateApplicationAvailablity\";\n }\n function showServerSLATab()\n {\n \tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n \tjQuery(\"#showEventVolumeSLATab\").hide(); //No I18N\n \tjQuery(\"#showServerSLATab\").show(); //No I18N\n \tjQuery(\"#showAvailabilitySLATab\").hide(); //No I18N\n \tjQuery(\"#showSLASettingsTab\").hide(); //No I18N\n \tjQuery(\"#loadingg\").show(); //No I18N\n \tdocument.location.href=\"/showBussiness.do?method=generateSystemAvailablity\";\n }\n function showEventVolumeSLATab()\n {\n \tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n \tjQuery(\"#showServerSLATab\").hide(); //No I18N\n \tjQuery(\"#showEventVolumeSLATab\").show(); //No I18N\n \tjQuery(\"#showAvailabilitySLATab\").hide(); //No I18N\n \tjQuery(\"#showSLASettingsTab\").hide(); //No I18N\n \tjQuery(\"#loadingg\").show(); //No I18N\n \tdocument.location.href=\"/showBussiness.do?method=generateTroubleTicket\";\n }\n function showSLASettingsTab()\n {\n \tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n \tjQuery(\"#showSLASettingsTab\").show(); //No I18N\n");
/* 2440 */                     out.write(" \tjQuery(\"#showServerSLATab\").hide(); //No I18N\n \tjQuery(\"#showEventVolumeSLATab\").hide(); //No I18N\n \tjQuery(\"#showAvailabilitySLATab\").hide(); //No I18N\n \tjQuery(\"#loadingg\").show(); //No I18N\n \tdocument.location.href=\"/showBussiness.do?method=generateSLA&sla=true\";\n }\n</script>\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n<body onLoad=\"javascript:myOnLoad()\"></body>\n");
/*      */                     
/* 2442 */                     String layOutPage = null;
/* 2443 */                     String titleName = null;
/* 2444 */                     String headerPage = null;
/* 2445 */                     String noSLAMsg = "am.webclient.manager.slatab.noslamessage.text";
/* 2446 */                     if (isIT360SLAEnabled)
/*      */                     {
/* 2448 */                       noSLAMsg = "am.webclient.slatab.noslamessage.text";
/* 2449 */                       layOutPage = "/jsp/BasicLayoutNoLeft.jsp";
/* 2450 */                       titleName = FormatUtil.getString("am.webclient.sla.slaview.txt");
/* 2451 */                       headerPage = "/jsp/header.jsp";
/* 2452 */                       request.setAttribute("oldtab", "7");
/*      */                     }
/* 2454 */                     else if ((isdefaultview != null) && (isdefaultview.trim().equalsIgnoreCase("true")))
/*      */                     {
/* 2456 */                       layOutPage = "/jsp/ManagerLayout.jsp";
/* 2457 */                       headerPage = "/jsp/header.jsp";
/*      */                     }
/*      */                     else
/*      */                     {
/* 2461 */                       layOutPage = "/jsp/ManagerLayout.jsp";
/* 2462 */                       headerPage = "/jsp/ManagerHeader.jsp";
/*      */                     }
/*      */                     
/* 2465 */                     out.write(10);
/*      */                     
/* 2467 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2468 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2469 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2471 */                     _jspx_th_tiles_005finsert_005f0.setPage(layOutPage);
/* 2472 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2473 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2475 */                         out.write(10);
/*      */                         
/* 2477 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2478 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2479 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2481 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2483 */                         _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.manager.serverslatab.title.text"));
/* 2484 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2485 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2486 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2489 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2490 */                         out.write(32);
/*      */                         
/* 2492 */                         PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2493 */                         _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2494 */                         _jspx_th_tiles_005fput_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2496 */                         _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */                         
/* 2498 */                         _jspx_th_tiles_005fput_005f1.setValue(headerPage);
/* 2499 */                         int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2500 */                         if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2501 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1); return;
/*      */                         }
/*      */                         
/* 2504 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2505 */                         out.write(10);
/*      */                         
/* 2507 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2508 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2509 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2511 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2513 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2514 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2515 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2516 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2517 */                             out = _jspx_page_context.pushBody();
/* 2518 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2519 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2522 */                             out.write(10);
/* 2523 */                             out.write(10);
/* 2524 */                             if (isIT360SLAEnabled) {
/* 2525 */                               out.write("\n\t\n\t");
/*      */                               
/* 2527 */                               String titles = SLAUtil.getSLASubTabsTitle();
/* 2528 */                               String functions = SLAUtil.getSLASubTabsJS();
/* 2529 */                               String selectedTab = "am.webclient.manager.serversla.text";
/*      */                               
/* 2531 */                               out.write(10);
/* 2532 */                               out.write(9);
/* 2533 */                               JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(titles), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(titles), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(functions), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedTab), request.getCharacterEncoding()), out, true);
/* 2534 */                               out.write(10);
/* 2535 */                               out.write(9);
/*      */                             }
/*      */                             
/* 2538 */                             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\" align=\"center\" id=\"ServerSLA\">\n");
/*      */                             try {
/* 2540 */                               String Periodvalue = (String)request.getAttribute("pvalue");
/*      */                               
/* 2542 */                               ArrayList sysdata = (ArrayList)request.getAttribute("system");
/*      */                               
/* 2544 */                               int wide = 0;
/* 2545 */                               if (sysdata != null) {
/* 2546 */                                 if ((sysdata.size() == 0) || (sysdata.size() == 1))
/*      */                                 {
/* 2548 */                                   wide = 300;
/*      */                                 }
/* 2550 */                                 else if ((sysdata.size() > 1) && (sysdata.size() < 5))
/*      */                                 {
/* 2552 */                                   wide = sysdata.size() * 150;
/*      */                                 }
/* 2554 */                                 else if ((sysdata.size() > 5) && (sysdata.size() < 10))
/*      */                                 {
/* 2556 */                                   wide = sysdata.size() * 60;
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2560 */                                   wide = sysdata.size() * 38;
/*      */                                 }
/*      */                               }
/* 2563 */                               String width = String.valueOf(wide);
/*      */                               
/* 2565 */                               String serverSla = request.getParameter("sla");
/*      */                               
/*      */ 
/* 2568 */                               out.write("\n\n<tr>\n<td width='80%' valign='top'>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" class='grayfullborder'>\n    <tr>\n\t      <td  width=\"80%\" class=\"tableheadingbborder\" >");
/* 2569 */                               out.print(FormatUtil.getString("am.webclient.manager.serverslatab.graphheading.text", new String[] { Periodvalue }));
/* 2570 */                               out.write("\n\t      </td>\n\n\t </tr>\n   <tr>\n          <td height=\"195\" align=\"center\" class=\"bodytext\">\n          ");
/*      */                               
/* 2572 */                               MultiPieChart _jspx_th_awolf_005fmultipiechart_005f0 = (MultiPieChart)this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.get(MultiPieChart.class);
/* 2573 */                               _jspx_th_awolf_005fmultipiechart_005f0.setPageContext(_jspx_page_context);
/* 2574 */                               _jspx_th_awolf_005fmultipiechart_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2576 */                               _jspx_th_awolf_005fmultipiechart_005f0.setDataSetProducer("sysavailgraph");
/*      */                               
/* 2578 */                               _jspx_th_awolf_005fmultipiechart_005f0.setWidth(width);
/*      */                               
/* 2580 */                               _jspx_th_awolf_005fmultipiechart_005f0.setHeight("195");
/*      */                               
/* 2582 */                               _jspx_th_awolf_005fmultipiechart_005f0.setLegend("true");
/*      */                               
/* 2584 */                               _jspx_th_awolf_005fmultipiechart_005f0.setUrl(false);
/*      */                               
/* 2586 */                               _jspx_th_awolf_005fmultipiechart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slaview.noserver.text"));
/* 2587 */                               int _jspx_eval_awolf_005fmultipiechart_005f0 = _jspx_th_awolf_005fmultipiechart_005f0.doStartTag();
/* 2588 */                               if (_jspx_eval_awolf_005fmultipiechart_005f0 != 0) {
/* 2589 */                                 if (_jspx_eval_awolf_005fmultipiechart_005f0 != 1) {
/* 2590 */                                   out = _jspx_page_context.pushBody();
/* 2591 */                                   _jspx_th_awolf_005fmultipiechart_005f0.setBodyContent((BodyContent)out);
/* 2592 */                                   _jspx_th_awolf_005fmultipiechart_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2595 */                                   out.write("\n            ");
/* 2596 */                                   int evalDoAfterBody = _jspx_th_awolf_005fmultipiechart_005f0.doAfterBody();
/* 2597 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2600 */                                 if (_jspx_eval_awolf_005fmultipiechart_005f0 != 1) {
/* 2601 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2604 */                               if (_jspx_th_awolf_005fmultipiechart_005f0.doEndTag() == 5) {
/* 2605 */                                 this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fmultipiechart_005f0); return;
/*      */                               }
/*      */                               
/* 2608 */                               this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fmultipiechart_005f0);
/* 2609 */                               out.write("\n          </td>\n\n        </tr>\n\n      </table></td>\n<td  width=\"20%\" valign='top'>\n");
/* 2610 */                               out.write("<!--$Id$-->\n\n");
/*      */                               
/* 2612 */                               String uri = request.getRequestURI();
/* 2613 */                               if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin())
/*      */                               {
/* 2615 */                                 out.write("\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  style=\"Display:inline: padding:5px;margin-bottom:10px;\" class=\"lrtbdarkborder\">\n    <tr>\n    <td class=\"leftlinksheading\">");
/* 2616 */                                 out.print(FormatUtil.getString("am.webclient.jboss.quicklinks.text"));
/* 2617 */                                 out.write("</td>\n    </tr>\n    <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateSLA&showdiv=true\" class=\"new-left-links\">");
/* 2618 */                                 out.print(FormatUtil.getString("am.webclient.manager.newsla.text"));
/* 2619 */                                 out.write("</a></td>\n   </tr>\n   ");
/* 2620 */                                 if (!uri.contains("Bussiness")) {
/* 2621 */                                   out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"new-left-links\">");
/* 2622 */                                   out.print(FormatUtil.getString("Application"));
/* 2623 */                                   out.write(32);
/* 2624 */                                   out.write(32);
/* 2625 */                                   out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/* 2626 */                                   out.write("</a></td>\n   </tr>\n   "); }
/* 2627 */                                 if (!uri.contains("SystemAvailablity")) {
/* 2628 */                                   out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"new-left-links\">");
/* 2629 */                                   out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/* 2630 */                                   out.write("</a></td>\n   </tr>\n   "); }
/* 2631 */                                 if (!uri.contains("BAEventReport")) {
/* 2632 */                                   out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"new-left-links\">");
/* 2633 */                                   out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/* 2634 */                                   out.write("</a></td>\n   </tr>\n   ");
/*      */                                 }
/* 2636 */                                 out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"new-left-links\">");
/* 2637 */                                 out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/* 2638 */                                 out.write("</a></td>\n   </tr>\n</table>\n");
/*      */                               }
/* 2640 */                               out.write(" \n<table width=\"95%\" class=\"lrtbdarkborder\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align='right'>\n <tr>\n <td  align='left' >\n    ");
/*      */                               
/* 2642 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2643 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2644 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2646 */                               _jspx_th_html_005fform_005f0.setAction("/showBussiness.do?method=generateSystemAvailablity");
/*      */                               
/* 2648 */                               _jspx_th_html_005fform_005f0.setStyle("Display:inline");
/* 2649 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2650 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2652 */                                   out.write("\n\t<input type=hidden name=\"reportType\" id=\"reportType\" value=\"\" >\n                <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n                <tr>\n                <td class=\"tableheadingbborder\"  >");
/* 2653 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.text"));
/* 2654 */                                   out.write("</td>\n              </tr>\n              <tr>\n\n               <td class=\"leftlinkstd\"> ");
/*      */                                   
/* 2656 */                                   SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2657 */                                   _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2658 */                                   _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2660 */                                   _jspx_th_html_005fselect_005f0.setProperty("period");
/*      */                                   
/* 2662 */                                   _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnPeriod(this)");
/*      */                                   
/* 2664 */                                   _jspx_th_html_005fselect_005f0.setStyleClass("formtextselected marg-tbtm-7px");
/* 2665 */                                   int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2666 */                                   if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2667 */                                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2668 */                                       out = _jspx_page_context.pushBody();
/* 2669 */                                       _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2670 */                                       _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2673 */                                       out.write("\n                  ");
/*      */                                       
/* 2675 */                                       OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2676 */                                       _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2677 */                                       _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2679 */                                       _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */                                       
/* 2681 */                                       _jspx_th_html_005foption_005f0.setValue("0");
/* 2682 */                                       int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2683 */                                       if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2684 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                       }
/*      */                                       
/* 2687 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/* 2688 */                                       out.write(32);
/*      */                                       
/* 2690 */                                       OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2691 */                                       _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2692 */                                       _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2694 */                                       _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */                                       
/* 2696 */                                       _jspx_th_html_005foption_005f1.setValue("3");
/* 2697 */                                       int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2698 */                                       if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2699 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                       }
/*      */                                       
/* 2702 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/* 2703 */                                       out.write("\n                  ");
/*      */                                       
/* 2705 */                                       OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2706 */                                       _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2707 */                                       _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2709 */                                       _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */                                       
/* 2711 */                                       _jspx_th_html_005foption_005f2.setValue("6");
/* 2712 */                                       int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2713 */                                       if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2714 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                       }
/*      */                                       
/* 2717 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/*      */                                       
/* 2719 */                                       OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2720 */                                       _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2721 */                                       _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2723 */                                       _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                                       
/* 2725 */                                       _jspx_th_html_005foption_005f3.setValue("1");
/* 2726 */                                       int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2727 */                                       if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2728 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                       }
/*      */                                       
/* 2731 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/* 2732 */                                       out.write(32);
/*      */                                       
/* 2734 */                                       OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2735 */                                       _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2736 */                                       _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2738 */                                       _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */                                       
/* 2740 */                                       _jspx_th_html_005foption_005f4.setValue("12");
/* 2741 */                                       int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2742 */                                       if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2743 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                       }
/*      */                                       
/* 2746 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/* 2747 */                                       out.write("\n                   ");
/*      */                                       
/* 2749 */                                       OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2750 */                                       _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2751 */                                       _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2753 */                                       _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */                                       
/* 2755 */                                       _jspx_th_html_005foption_005f5.setValue("7");
/* 2756 */                                       int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2757 */                                       if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2758 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                       }
/*      */                                       
/* 2761 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/* 2762 */                                       out.write(32);
/*      */                                       
/* 2764 */                                       OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2765 */                                       _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2766 */                                       _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2768 */                                       _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */                                       
/* 2770 */                                       _jspx_th_html_005foption_005f6.setValue("11");
/* 2771 */                                       int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2772 */                                       if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2773 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                       }
/*      */                                       
/* 2776 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*      */                                       
/* 2778 */                                       OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2779 */                                       _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 2780 */                                       _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2782 */                                       _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                                       
/* 2784 */                                       _jspx_th_html_005foption_005f7.setValue("2");
/* 2785 */                                       int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 2786 */                                       if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 2787 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                       }
/*      */                                       
/* 2790 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/* 2791 */                                       out.write(32);
/*      */                                       
/* 2793 */                                       OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2794 */                                       _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 2795 */                                       _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2797 */                                       _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */                                       
/* 2799 */                                       _jspx_th_html_005foption_005f8.setValue("9");
/* 2800 */                                       int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 2801 */                                       if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 2802 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                       }
/*      */                                       
/* 2805 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/* 2806 */                                       out.write("\n                    ");
/*      */                                       
/* 2808 */                                       OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2809 */                                       _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 2810 */                                       _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2812 */                                       _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */                                       
/* 2814 */                                       _jspx_th_html_005foption_005f9.setValue("8");
/* 2815 */                                       int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 2816 */                                       if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 2817 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                       }
/*      */                                       
/* 2820 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/*      */                                       
/* 2822 */                                       OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2823 */                                       _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 2824 */                                       _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2826 */                                       _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                                       
/* 2828 */                                       _jspx_th_html_005foption_005f10.setValue("5");
/* 2829 */                                       int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 2830 */                                       if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 2831 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                       }
/*      */                                       
/* 2834 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/* 2835 */                                       out.write("\n                  ");
/* 2836 */                                       int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 2837 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2840 */                                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2841 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2844 */                                   if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 2845 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                   }
/*      */                                   
/* 2848 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 2849 */                                   out.write(" <input type=hidden name=\"resourcename\" value=\"");
/* 2850 */                                   out.print(request.getParameter("resourcename"));
/* 2851 */                                   out.write("\" >\n                  <input type=hidden name=\"resourceid\" value=\"");
/* 2852 */                                   out.print(request.getParameter("resourceid"));
/* 2853 */                                   out.write("\">\n                  <input type=hidden name=\"defaultView\" value=\"");
/* 2854 */                                   out.print(isdefaultview);
/* 2855 */                                   out.write("\">\n                 ");
/*      */                                   
/* 2857 */                                   if ((serverSla != null) && (serverSla.trim().equals("true"))) {
/* 2858 */                                     out.write("\n                  <input type=hidden name='sla' value='true'>\n                  ");
/*      */                                   }
/* 2860 */                                   out.write("\n            </td></tr></table>\n\t\t");
/* 2861 */                                   if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2863 */                                   out.write("\n            ");
/* 2864 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2865 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2869 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2870 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 2873 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2874 */                               out.write("</td>\n            </tr>\n\n             <tr>\n          <td  align='left'> ");
/*      */                               
/* 2876 */                               FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2877 */                               _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 2878 */                               _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2880 */                               _jspx_th_html_005fform_005f1.setAction("/showBussiness.do?method=generateSystemAvailablity&value=4");
/*      */                               
/* 2882 */                               _jspx_th_html_005fform_005f1.setStyle("Display:inline");
/* 2883 */                               int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 2884 */                               if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                                 for (;;) {
/* 2886 */                                   out.write("\n          <input type=hidden name=\"reportType\" id=\"reportType\" value=\"\" >\n            <table width=\"100%\" height=\"30%\"border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td height=\"22\" class=\"leftlinksheading\">");
/* 2887 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.customtime.text"));
/* 2888 */                                   out.write("</td>\n              </tr>\n              <tr >\n                <td width=\"50%\" height=\"24\"  class=\"columnheading\" > ");
/* 2889 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.starttime.text"));
/* 2890 */                                   out.write("\n                </td>\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td height=\"38\">&nbsp;");
/* 2891 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2893 */                                   out.write("\n                  <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/* 2894 */                                   out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 2895 */                                   out.write("\"></a>\n                  <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT></td>\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td height=\"24\" class=\"columnheading\" width=\"50%\">");
/* 2896 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.endtime.text"));
/* 2897 */                                   out.write("</td>\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td height=\"24\" >&nbsp;");
/* 2898 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2900 */                                   out.write("\n                 <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=endTrigger title=\"");
/* 2901 */                                   out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 2902 */                                   out.write("\"></a>\n                  <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"end\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT> </td>\n              </tr>\n              <input type=hidden name=\"resourcename\" value=\"");
/* 2903 */                                   out.print(request.getParameter("resourcename"));
/* 2904 */                                   out.write("\" >\n                  <input type=hidden name=\"resourceid\" value=\"");
/* 2905 */                                   out.print(request.getParameter("resourceid"));
/* 2906 */                                   out.write("\" >\n                  <input type=hidden name=\"defaultView\" value=\"");
/* 2907 */                                   out.print(isdefaultview);
/* 2908 */                                   out.write("\" >\n                  ");
/*      */                                   
/* 2910 */                                   if ((serverSla != null) && (serverSla.trim().equals("true"))) {
/* 2911 */                                     out.write("\n                  <input type=hidden name='sla' value='true'>\n                  ");
/*      */                                   }
/* 2913 */                                   out.write("\n              <tr class=\"whitegrayborder\">\n                <td height=\"24\" align=\"center\" class=\"tablebottom\"> <input type=\"submit\" name=\"show\" value=\"");
/* 2914 */                                   out.print(FormatUtil.getString("am.webclient.historydata.showreport.text"));
/* 2915 */                                   out.write("\" class=\"buttons btn_highlt\" onClick=\"return fnCheckCustomTime(this.form)\">\n                </td>\n              </tr>\n            </table>\n\t\t");
/* 2916 */                                   if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2918 */                                   out.write("\n            ");
/* 2919 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 2920 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2924 */                               if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 2925 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                               }
/*      */                               
/* 2928 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 2929 */                               out.write(" </td>\n        </tr>\n      </table></td>\n\n\t</tr>\n\n<tr>\n<td width='100%' colspan='2'>\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding-top:10px;\">\n<tr >\n <td width=\"95%\" valign='top' >\n\n <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\"  align=\"left\" class='lrtborder' >\n  <tr>\n\n\t      <td  width=\"60%\" class=\"tableheadingbborder\" colspan=\"9\" >");
/* 2930 */                               out.print(FormatUtil.getString("am.webclient.manager.serverslatab.heading.text"));
/* 2931 */                               out.write("\n\t      </td>\n\t\t                 <td class=\"tableheadingbborder\" align=\"right\" >\n\t\t\t");
/* 2932 */                               if ((serverSla != null) && (serverSla.trim().equals("true"))) {
/* 2933 */                                 out.write("\n\n                        <a href=\"/showBussiness.do?method=generateSystemAvailablity&defaultView=");
/* 2934 */                                 out.print(isdefaultview.equals("true") ? isdefaultview : Boolean.valueOf(false));
/* 2935 */                                 out.write("\" class=\"staticlinks\">");
/* 2936 */                                 out.print(FormatUtil.getString("am.webclient.manager.all.server"));
/* 2937 */                                 out.write("</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;\n                        <a href=\"#\" class=\"disabledlink\">");
/* 2938 */                                 out.print(FormatUtil.getString("am.webclient.manager.sla.server"));
/* 2939 */                                 out.write("</a>\n\n\t\t\t");
/*      */                               } else {
/* 2941 */                                 out.write("\n\n                        <a href=\"#\" class=\"disabledlink\">");
/* 2942 */                                 out.print(FormatUtil.getString("am.webclient.manager.all.server"));
/* 2943 */                                 out.write("</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;\n                        <a href=\"/showBussiness.do?method=generateSystemAvailablity&sla=true&defaultView=");
/* 2944 */                                 out.print(isdefaultview.equals("true") ? isdefaultview : Boolean.valueOf(false));
/* 2945 */                                 out.write("\" class=\"staticlinks\">");
/* 2946 */                                 out.print(FormatUtil.getString("am.webclient.manager.sla.server"));
/* 2947 */                                 out.write("</a>\n\n\t\t\t");
/*      */                               }
/* 2949 */                               out.write("\n\t\t\t\t<a href=\"javascript:generateReportFile('pdf');\"><img border=\"0\" align=\"top\" title=\"PDF Report\" alt=\"PDF Report\" src=\"../images/icon_pdf.gif\"></a>\n\t\t\t\t<a href=\"javascript:generateReportFile('csv');\"><img border=\"0\" align=\"top\" title=\"CSV Report\" alt=\"CSV Report\" src=\"../images/icon_csv.gif\"></a>\n                </td>\n\n    </tr>\n\t</table>\n   <tr>\n   <table id=\"serverSlaTable\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\"  align=\"left\" class='lrbborder' >\n\n    <tr>\n      <td width=\"17%\" height=\"28\"  class=\"columnheading\">");
/* 2950 */                               out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.name"));
/* 2951 */                               out.write("</td>\n      <td width=\"9%\" height=\"28\"  class=\"columnheading\">");
/* 2952 */                               out.print(FormatUtil.getString("am.webclient.manager.slatab.slaname.text"));
/* 2953 */                               out.write("</td>\n      <td width=\"8%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2954 */                               out.print(FormatUtil.getString("am.webclient.manager.slatab.meetssla.text"));
/* 2955 */                               out.write("</td>\n      <td width=\"15%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2956 */                               out.print(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/* 2957 */                               out.write("</td>\n      <td width=\"8%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2958 */                               out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 2959 */                               out.write("%</td>\n\n       <td width=\"17%\" align=\"left\" height=\"28\"  class=\"columnheading\" title='");
/* 2960 */                               out.print(FormatUtil.getString("am.webclient.historydata.mttr.text"));
/* 2961 */                               out.write(39);
/* 2962 */                               out.write(62);
/* 2963 */                               out.print(FormatUtil.getString("am.webclient.historydata.capmttr.text"));
/* 2964 */                               out.write("</td>\n      <td width=\"17%\" align=\"left\" height=\"28\"  class=\"columnheading\"  title='");
/* 2965 */                               out.print(FormatUtil.getString("am.webclient.historydata.mtbf.text"));
/* 2966 */                               out.write(39);
/* 2967 */                               out.write(62);
/* 2968 */                               out.print(FormatUtil.getString("am.webclient.historydata.capmtbf.text"));
/* 2969 */                               out.write("</td>\n      <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2970 */                               out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/* 2971 */                               out.write("</td>\n      <td width=\"4%\" align=\"left\" height=\"28\"  class=\"columnheading\">&nbsp;</td>\n\n    </tr>\n    ");
/* 2972 */                               ArrayList data = new ArrayList();
/*      */                               
/* 2974 */                               String resid = null;
/* 2975 */                               String resname = null;
/* 2976 */                               String sla = null;
/*      */                               
/* 2978 */                               String down = null;
/* 2979 */                               String mttr = null;
/* 2980 */                               String mtbf = null;
/* 2981 */                               String slid = null;
/* 2982 */                               String sname = null;
/* 2983 */                               String slaop = null;
/* 2984 */                               String slaval = null;
/* 2985 */                               String troubleticket = null;
/*      */                               
/* 2987 */                               for (int k = 0; k < sysdata.size(); k++)
/*      */                               {
/* 2989 */                                 String bgcolor = null;
/* 2990 */                                 if (k % 2 == 0)
/*      */                                 {
/* 2992 */                                   bgcolor = "class=\"whitegrayborder\"";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2996 */                                   bgcolor = "class=\"yellowgrayborder\"";
/*      */                                 }
/* 2998 */                                 data = (ArrayList)sysdata.get(k);
/* 2999 */                                 resid = (String)data.get(0);
/* 3000 */                                 resname = (String)data.get(1);
/* 3001 */                                 slid = (String)data.get(2);
/* 3002 */                                 sname = (String)data.get(3);
/* 3003 */                                 slaop = FormatUtil.getString((String)data.get(4));
/* 3004 */                                 slaval = (String)data.get(5);
/* 3005 */                                 Properties p1 = (Properties)data.get(6);
/* 3006 */                                 String sysavailable = p1.getProperty("available");
/* 3007 */                                 down = p1.getProperty("totaldowntime");
/* 3008 */                                 mttr = p1.getProperty("mttr");
/* 3009 */                                 mtbf = p1.getProperty("mtbf");
/* 3010 */                                 sla = p1.getProperty("SLA");
/* 3011 */                                 ArrayList alist = (ArrayList)data.get(10);
/* 3012 */                                 String ticketvalue = "";
/* 3013 */                                 String ticksla = "";
/* 3014 */                                 for (int j = 0; j < alist.size(); j++)
/*      */                                 {
/* 3016 */                                   ticketvalue = (String)alist.get(1);
/* 3017 */                                   ticksla = (String)alist.get(3);
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 3022 */                                 out.write("\n\t\t\t\t <tr ");
/* 3023 */                                 out.print(bgcolor);
/* 3024 */                                 out.write(" class=\"alarm-links \" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\" class=\"alarmheader\">\n\t\t\t\t");
/*      */                                 
/* 3026 */                                 String title = "";
/* 3027 */                                 if (EnterpriseUtil.isAdminServer())
/*      */                                 {
/* 3029 */                                   if (CommDBUtil.getManagedServerNameWithPort(resid).equals("Admin Server"))
/*      */                                   {
/* 3031 */                                     title = FormatUtil.getString("am.webclient.gettingstarted.adminserver.text");
/* 3032 */                                     resname = resname + "_" + title;
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3036 */                                     title = FormatUtil.getString("am.webclient.managedserver.tooltip.monitorname", new String[] { CommDBUtil.getManagedServerNameWithPort(resid) });
/* 3037 */                                     resname = resname + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 3041 */                                 out.write("\n              <td title=\"");
/* 3042 */                                 out.print(title);
/* 3043 */                                 out.write("\" class=\"yellowgrayborder\">");
/* 3044 */                                 out.print(resname);
/* 3045 */                                 out.write("</td>\n              ");
/* 3046 */                                 if (!sname.equals("not set"))
/*      */                                 {
/* 3048 */                                   out.write("\n              <td title='");
/* 3049 */                                   out.print(slaop);
/* 3050 */                                   out.write("&nbsp;");
/* 3051 */                                   out.print(slaval);
/* 3052 */                                   out.write("' class=\"yellowgrayborder\"><a href=\"javascript:void(0)\" onClick=\"javascript:window.open('../jsp/Popup_SLA.jsp?sid=");
/* 3053 */                                   out.print(slid);
/* 3054 */                                   out.write("','','resizable=yes,width=390,height=170')\" class=\"alarm-links \">");
/* 3055 */                                   out.print(FormatUtil.getString(sname));
/* 3056 */                                   out.write("</a></td>\n              ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3060 */                                   out.write("\n                    <td title='");
/* 3061 */                                   out.print(slaop);
/* 3062 */                                   out.write("&nbsp;");
/* 3063 */                                   out.print(slaval);
/* 3064 */                                   out.write("' class='yellowgrayborder'>");
/* 3065 */                                   out.print(FormatUtil.getString(sname));
/* 3066 */                                   out.write("</td>\n                   ");
/*      */                                 }
/* 3068 */                                 out.write("\n                    ");
/* 3069 */                                 if ((sla.equals("PASS")) && (!sname.equals("not set")) && (ticksla.equals("PASS")))
/*      */                                 {
/* 3071 */                                   out.write("\n           <td align=\"left\" class='yellowgrayborder' ><img border=\"0\" src=\"/images/icon_tickmark.gif\"></td>\n           ");
/*      */                                 }
/* 3073 */                                 else if ((sla.equals("FAIL")) || (ticksla.equals("FAIL")))
/*      */                                 {
/* 3075 */                                   out.write("\n                   <td align=\"left\" class='yellowgrayborder' ><img border=\"0\" src=\"/images/cross.gif\"></td>\n                     ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3079 */                                   out.write("\n                   <td align=\"left\" class='yellowgrayborder' ><img border=\"0\" src=\"/images/questionmark.gif\"></td>\n                   ");
/*      */                                 }
/* 3081 */                                 out.write("\n\n                  <td class=\"yellowgrayborder\">");
/* 3082 */                                 out.print(down);
/* 3083 */                                 out.write("</td>\n\n\n\n              ");
/* 3084 */                                 if (sla.equals("PASS"))
/*      */                                 {
/* 3086 */                                   out.write("\n      <td align=\"left\" class='yellowgrayborder' >");
/* 3087 */                                   out.print(sysavailable);
/* 3088 */                                   out.write("</td>\n      ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3092 */                                   out.write("\n           <td align=\"left\"  class='yellowgrayborder'><span class=\"navigation\">");
/* 3093 */                                   out.print(sysavailable);
/* 3094 */                                   out.write("</span></td>\n          ");
/*      */                                 }
/* 3096 */                                 out.write("\n           <td class=\"yellowgrayborder\">");
/* 3097 */                                 out.print(mttr);
/* 3098 */                                 out.write("</td>\n       <td align=\"left\" class='yellowgrayborder' >");
/* 3099 */                                 out.print(mtbf);
/* 3100 */                                 out.write("</td>\n        ");
/* 3101 */                                 if (ticksla.equals("PASS"))
/*      */                                 {
/* 3103 */                                   out.write("\n       <td class=\"yellowgrayborder\">");
/* 3104 */                                   out.print(ticketvalue);
/* 3105 */                                   out.write("</td>\n        ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3109 */                                   out.write("\n           <td class=\"yellowgrayborder\"><span class=\"navigation\">");
/* 3110 */                                   out.print(ticketvalue);
/* 3111 */                                   out.write("</span></td>\n            ");
/*      */                                 }
/* 3113 */                                 out.write("\n\n       <td align=\"left\" class=\"yellowgrayborder\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3114 */                                 out.print(resid);
/* 3115 */                                 out.write("&period=1')\"><img border=\"0\" src=\"../images/icon_7daysdata.gif\"   title=\"");
/* 3116 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3117 */                                 out.write("\" ></a></td>\n\n\n\n    </tr>\n\n\n");
/*      */                               }
/*      */                             } catch (NullPointerException npe) {}catch (Exception e) {
/* 3120 */                               e.printStackTrace(); }
/* 3121 */                             out.write(32);
/* 3122 */                             out.write("\n   </table></tr>\n</td></tr></table>\n\n\n</td></tr>\n\n\n\n");
/* 3123 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3124 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3127 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3128 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3131 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3132 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 3135 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3136 */                         out.write(10);
/* 3137 */                         out.write(10);
/* 3138 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 3140 */                         out.write(10);
/* 3141 */                         out.write(10);
/* 3142 */                         out.write(10);
/* 3143 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3144 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3148 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3149 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 3152 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3153 */                       out.write("\n\n</body>\n");
/*      */                     }
/* 3155 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3156 */         out = _jspx_out;
/* 3157 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3158 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3159 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3162 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3168 */     PageContext pageContext = _jspx_page_context;
/* 3169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3171 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3172 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3173 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3175 */     _jspx_th_c_005fif_005f0.setTest("${PRINTER_FRIENDLY != null && PRINTER_FRIENDLY == 'true'}");
/* 3176 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3177 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3179 */         out.write("\n                 <input type='hidden' name='PRINTER_FRIENDLY' value='true'>\n\t\t");
/* 3180 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3181 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3185 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3186 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3187 */       return true;
/*      */     }
/* 3189 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3195 */     PageContext pageContext = _jspx_page_context;
/* 3196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3198 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3199 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3200 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3202 */     _jspx_th_html_005ftext_005f0.setSize("15");
/*      */     
/* 3204 */     _jspx_th_html_005ftext_005f0.setProperty("startDate");
/*      */     
/* 3206 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/* 3208 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/* 3210 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetStartTime(this.value)");
/* 3211 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3212 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3213 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3214 */       return true;
/*      */     }
/* 3216 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3217 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3222 */     PageContext pageContext = _jspx_page_context;
/* 3223 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3225 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3226 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3227 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3229 */     _jspx_th_html_005ftext_005f1.setProperty("endDate");
/*      */     
/* 3231 */     _jspx_th_html_005ftext_005f1.setSize("15");
/*      */     
/* 3233 */     _jspx_th_html_005ftext_005f1.setStyleId("end");
/*      */     
/* 3235 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 3237 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetEndTime(this.value)");
/* 3238 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3239 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3240 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3241 */       return true;
/*      */     }
/* 3243 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3249 */     PageContext pageContext = _jspx_page_context;
/* 3250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3252 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3253 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3254 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3256 */     _jspx_th_c_005fif_005f1.setTest("${PRINTER_FRIENDLY != null && PRINTER_FRIENDLY == 'true'}");
/* 3257 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3258 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3260 */         out.write("\n                 <input type='hidden' name='PRINTER_FRIENDLY' value='true'>\n\t\t");
/* 3261 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3262 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3266 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3267 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3268 */       return true;
/*      */     }
/* 3270 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3276 */     PageContext pageContext = _jspx_page_context;
/* 3277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3279 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3280 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3281 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3283 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 3285 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 3286 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3287 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3288 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3289 */       return true;
/*      */     }
/* 3291 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3292 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SystemAvailablity_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */