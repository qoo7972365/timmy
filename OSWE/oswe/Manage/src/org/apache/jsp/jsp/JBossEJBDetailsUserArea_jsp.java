/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.tags.FormatTag;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.BarChart;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.ResultSet;
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
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ 
/*      */ public final class JBossEJBDetailsUserArea_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   42 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   45 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   46 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   47 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   54 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   59 */     ArrayList list = null;
/*   60 */     StringBuffer sbf = new StringBuffer();
/*   61 */     ManagedApplication mo = new ManagedApplication();
/*   62 */     if (distinct)
/*      */     {
/*   64 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   68 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   71 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   73 */       ArrayList row = (ArrayList)list.get(i);
/*   74 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   75 */       if (distinct) {
/*   76 */         sbf.append(row.get(0));
/*      */       } else
/*   78 */         sbf.append(row.get(1));
/*   79 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   82 */     return sbf.toString(); }
/*      */   
/*   84 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   87 */     if (severity == null)
/*      */     {
/*   89 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   91 */     if (severity.equals("5"))
/*      */     {
/*   93 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   95 */     if (severity.equals("1"))
/*      */     {
/*   97 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  102 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  109 */     if (severity == null)
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  113 */     if (severity.equals("1"))
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  117 */     if (severity.equals("4"))
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  121 */     if (severity.equals("5"))
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  128 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  134 */     if (severity == null)
/*      */     {
/*  136 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  138 */     if (severity.equals("5"))
/*      */     {
/*  140 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  142 */     if (severity.equals("1"))
/*      */     {
/*  144 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  148 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  154 */     if (severity == null)
/*      */     {
/*  156 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  158 */     if (severity.equals("1"))
/*      */     {
/*  160 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  162 */     if (severity.equals("4"))
/*      */     {
/*  164 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  166 */     if (severity.equals("5"))
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  172 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  178 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  184 */     if (severity == 5)
/*      */     {
/*  186 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  188 */     if (severity == 1)
/*      */     {
/*  190 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  195 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  201 */     if (severity == null)
/*      */     {
/*  203 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  205 */     if (severity.equals("5"))
/*      */     {
/*  207 */       if (isAvailability) {
/*  208 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  211 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  214 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  216 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  218 */     if (severity.equals("1"))
/*      */     {
/*  220 */       if (isAvailability) {
/*  221 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  224 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  231 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  238 */     if (severity == null)
/*      */     {
/*  240 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  242 */     if (severity.equals("5"))
/*      */     {
/*  244 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  246 */     if (severity.equals("4"))
/*      */     {
/*  248 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  250 */     if (severity.equals("1"))
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  257 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  263 */     if (severity == null)
/*      */     {
/*  265 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  267 */     if (severity.equals("5"))
/*      */     {
/*  269 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  271 */     if (severity.equals("4"))
/*      */     {
/*  273 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  275 */     if (severity.equals("1"))
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  282 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  289 */     if (severity == null)
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  293 */     if (severity.equals("5"))
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  297 */     if (severity.equals("4"))
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  301 */     if (severity.equals("1"))
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  308 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  316 */     StringBuffer out = new StringBuffer();
/*  317 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  318 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  319 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  320 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  321 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  322 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  323 */     out.append("</tr>");
/*  324 */     out.append("</form></table>");
/*  325 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  332 */     if (val == null)
/*      */     {
/*  334 */       return "-";
/*      */     }
/*      */     
/*  337 */     String ret = FormatUtil.formatNumber(val);
/*  338 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  339 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  342 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  346 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  354 */     StringBuffer out = new StringBuffer();
/*  355 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  356 */     out.append("<tr>");
/*  357 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  359 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  361 */     out.append("</tr>");
/*  362 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  366 */       if (j % 2 == 0)
/*      */       {
/*  368 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  372 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  375 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  377 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  380 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  384 */       out.append("</tr>");
/*      */     }
/*  386 */     out.append("</table>");
/*  387 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  388 */     out.append("<tr>");
/*  389 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  390 */     out.append("</tr>");
/*  391 */     out.append("</table>");
/*  392 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  398 */     StringBuffer out = new StringBuffer();
/*  399 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  400 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  401 */     out.append("<tr>");
/*  402 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  403 */     out.append("<tr>");
/*  404 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  405 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  406 */     out.append("</tr>");
/*  407 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  410 */       out.append("<tr>");
/*  411 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  412 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  413 */       out.append("</tr>");
/*      */     }
/*      */     
/*  416 */     out.append("</table>");
/*  417 */     out.append("</table>");
/*  418 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  423 */     if (severity.equals("0"))
/*      */     {
/*  425 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  429 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  436 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  449 */     StringBuffer out = new StringBuffer();
/*  450 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  451 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  453 */       out.append("<tr>");
/*  454 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  455 */       out.append("</tr>");
/*      */       
/*      */ 
/*  458 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  460 */         String borderclass = "";
/*      */         
/*      */ 
/*  463 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  465 */         out.append("<tr>");
/*      */         
/*  467 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  468 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  469 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  475 */     out.append("</table><br>");
/*  476 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  477 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  479 */       List sLinks = secondLevelOfLinks[0];
/*  480 */       List sText = secondLevelOfLinks[1];
/*  481 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  484 */         out.append("<tr>");
/*  485 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  486 */         out.append("</tr>");
/*  487 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  489 */           String borderclass = "";
/*      */           
/*      */ 
/*  492 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  494 */           out.append("<tr>");
/*      */           
/*  496 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  497 */           if (sLinks.get(i).toString().length() == 0) {
/*  498 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  501 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  503 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  507 */     out.append("</table>");
/*  508 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  515 */     StringBuffer out = new StringBuffer();
/*  516 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  517 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  519 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  521 */         out.append("<tr>");
/*  522 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  523 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  527 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  529 */           String borderclass = "";
/*      */           
/*      */ 
/*  532 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  534 */           out.append("<tr>");
/*      */           
/*  536 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  537 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  538 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  541 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  544 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  549 */     out.append("</table><br>");
/*  550 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  551 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  553 */       List sLinks = secondLevelOfLinks[0];
/*  554 */       List sText = secondLevelOfLinks[1];
/*  555 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  558 */         out.append("<tr>");
/*  559 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  560 */         out.append("</tr>");
/*  561 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  563 */           String borderclass = "";
/*      */           
/*      */ 
/*  566 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  568 */           out.append("<tr>");
/*      */           
/*  570 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  571 */           if (sLinks.get(i).toString().length() == 0) {
/*  572 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  575 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  577 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  581 */     out.append("</table>");
/*  582 */     return out.toString();
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
/*  595 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  598 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  601 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  604 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  607 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  610 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  613 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  616 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  624 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  629 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  634 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  639 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  644 */     if (val != null)
/*      */     {
/*  646 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  650 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  655 */     if (val == null) {
/*  656 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  660 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  665 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  671 */     if (val != null)
/*      */     {
/*  673 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  677 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  683 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  688 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  692 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  697 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  702 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  707 */     String hostaddress = "";
/*  708 */     String ip = request.getHeader("x-forwarded-for");
/*  709 */     if (ip == null)
/*  710 */       ip = request.getRemoteAddr();
/*  711 */     InetAddress add = null;
/*  712 */     if (ip.equals("127.0.0.1")) {
/*  713 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  717 */       add = InetAddress.getByName(ip);
/*      */     }
/*  719 */     hostaddress = add.getHostName();
/*  720 */     if (hostaddress.indexOf('.') != -1) {
/*  721 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  722 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  726 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  731 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  737 */     if (severity == null)
/*      */     {
/*  739 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  741 */     if (severity.equals("5"))
/*      */     {
/*  743 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  745 */     if (severity.equals("1"))
/*      */     {
/*  747 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  752 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  757 */     ResultSet set = null;
/*  758 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  759 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  761 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  762 */       if (set.next()) { String str1;
/*  763 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  764 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  767 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  772 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  775 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  777 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  781 */     StringBuffer rca = new StringBuffer();
/*  782 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  783 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  786 */     int rcalength = key.length();
/*  787 */     String split = "6. ";
/*  788 */     int splitPresent = key.indexOf(split);
/*  789 */     String div1 = "";String div2 = "";
/*  790 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  792 */       if (rcalength > 180) {
/*  793 */         rca.append("<span class=\"rca-critical-text\">");
/*  794 */         getRCATrimmedText(key, rca);
/*  795 */         rca.append("</span>");
/*      */       } else {
/*  797 */         rca.append("<span class=\"rca-critical-text\">");
/*  798 */         rca.append(key);
/*  799 */         rca.append("</span>");
/*      */       }
/*  801 */       return rca.toString();
/*      */     }
/*  803 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  804 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  805 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  806 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  807 */     getRCATrimmedText(div1, rca);
/*  808 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  811 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  812 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  813 */     getRCATrimmedText(div2, rca);
/*  814 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  816 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  821 */     String[] st = msg.split("<br>");
/*  822 */     for (int i = 0; i < st.length; i++) {
/*  823 */       String s = st[i];
/*  824 */       if (s.length() > 180) {
/*  825 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  827 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  831 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  832 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  834 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  838 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  839 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  840 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  843 */       if (key == null) {
/*  844 */         return ret;
/*      */       }
/*      */       
/*  847 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  848 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  851 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  852 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  853 */       set = AMConnectionPool.executeQueryStmt(query);
/*  854 */       if (set.next())
/*      */       {
/*  856 */         String helpLink = set.getString("LINK");
/*  857 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  860 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  866 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  885 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  876 */         if (set != null) {
/*  877 */           AMConnectionPool.closeStatement(set);
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
/*  891 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  892 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  894 */       String entityStr = (String)keys.nextElement();
/*  895 */       String mmessage = temp.getProperty(entityStr);
/*  896 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  897 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  899 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  905 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  906 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  908 */       String entityStr = (String)keys.nextElement();
/*  909 */       String mmessage = temp.getProperty(entityStr);
/*  910 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  911 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  913 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  918 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  928 */     String des = new String();
/*  929 */     while (str.indexOf(find) != -1) {
/*  930 */       des = des + str.substring(0, str.indexOf(find));
/*  931 */       des = des + replace;
/*  932 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  934 */     des = des + str;
/*  935 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  942 */       if (alert == null)
/*      */       {
/*  944 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  946 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  948 */         return "&nbsp;";
/*      */       }
/*      */       
/*  951 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  953 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  956 */       int rcalength = test.length();
/*  957 */       if (rcalength < 300)
/*      */       {
/*  959 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  963 */       StringBuffer out = new StringBuffer();
/*  964 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  965 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  966 */       out.append("</div>");
/*  967 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  968 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  969 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  974 */       ex.printStackTrace();
/*      */     }
/*  976 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  982 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  987 */     ArrayList attribIDs = new ArrayList();
/*  988 */     ArrayList resIDs = new ArrayList();
/*  989 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  991 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  993 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  995 */       String resourceid = "";
/*  996 */       String resourceType = "";
/*  997 */       if (type == 2) {
/*  998 */         resourceid = (String)row.get(0);
/*  999 */         resourceType = (String)row.get(3);
/*      */       }
/* 1001 */       else if (type == 3) {
/* 1002 */         resourceid = (String)row.get(0);
/* 1003 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1006 */         resourceid = (String)row.get(6);
/* 1007 */         resourceType = (String)row.get(7);
/*      */       }
/* 1009 */       resIDs.add(resourceid);
/* 1010 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1011 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1013 */       String healthentity = null;
/* 1014 */       String availentity = null;
/* 1015 */       if (healthid != null) {
/* 1016 */         healthentity = resourceid + "_" + healthid;
/* 1017 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1020 */       if (availid != null) {
/* 1021 */         availentity = resourceid + "_" + availid;
/* 1022 */         entitylist.add(availentity);
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
/* 1036 */     Properties alert = getStatus(entitylist);
/* 1037 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1042 */     int size = monitorList.size();
/*      */     
/* 1044 */     String[] severity = new String[size];
/*      */     
/* 1046 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1048 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1049 */       String resourceName1 = (String)row1.get(7);
/* 1050 */       String resourceid1 = (String)row1.get(6);
/* 1051 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1052 */       if (severity[j] == null)
/*      */       {
/* 1054 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1058 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1060 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1062 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1065 */         if (sev > 0) {
/* 1066 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1067 */           monitorList.set(k, monitorList.get(j));
/* 1068 */           monitorList.set(j, t);
/* 1069 */           String temp = severity[k];
/* 1070 */           severity[k] = severity[j];
/* 1071 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1077 */     int z = 0;
/* 1078 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1081 */       int i = 0;
/* 1082 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1085 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1089 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1093 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1095 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1098 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1102 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1105 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1106 */       String resourceName1 = (String)row1.get(7);
/* 1107 */       String resourceid1 = (String)row1.get(6);
/* 1108 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1109 */       if (hseverity[j] == null)
/*      */       {
/* 1111 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1116 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1118 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1121 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1124 */         if (hsev > 0) {
/* 1125 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1126 */           monitorList.set(k, monitorList.get(j));
/* 1127 */           monitorList.set(j, t);
/* 1128 */           String temp1 = hseverity[k];
/* 1129 */           hseverity[k] = hseverity[j];
/* 1130 */           hseverity[j] = temp1;
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
/* 1142 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1143 */     boolean forInventory = false;
/* 1144 */     String trdisplay = "none";
/* 1145 */     String plusstyle = "inline";
/* 1146 */     String minusstyle = "none";
/* 1147 */     String haidTopLevel = "";
/* 1148 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1150 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1152 */         haidTopLevel = request.getParameter("haid");
/* 1153 */         forInventory = true;
/* 1154 */         trdisplay = "table-row;";
/* 1155 */         plusstyle = "none";
/* 1156 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1163 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1166 */     ArrayList listtoreturn = new ArrayList();
/* 1167 */     StringBuffer toreturn = new StringBuffer();
/* 1168 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1169 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1170 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1172 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1174 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1175 */       String childresid = (String)singlerow.get(0);
/* 1176 */       String childresname = (String)singlerow.get(1);
/* 1177 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1178 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1179 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1180 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1181 */       String unmanagestatus = (String)singlerow.get(5);
/* 1182 */       String actionstatus = (String)singlerow.get(6);
/* 1183 */       String linkclass = "monitorgp-links";
/* 1184 */       String titleforres = childresname;
/* 1185 */       String titilechildresname = childresname;
/* 1186 */       String childimg = "/images/trcont.png";
/* 1187 */       String flag = "enable";
/* 1188 */       String dcstarted = (String)singlerow.get(8);
/* 1189 */       String configMonitor = "";
/* 1190 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1191 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1193 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1195 */       if (singlerow.get(7) != null)
/*      */       {
/* 1197 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1199 */       String haiGroupType = "0";
/* 1200 */       if ("HAI".equals(childtype))
/*      */       {
/* 1202 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1204 */       childimg = "/images/trend.png";
/* 1205 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1206 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1207 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1209 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1211 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1213 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1214 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1217 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1219 */         linkclass = "disabledtext";
/* 1220 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1222 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1223 */       String availmouseover = "";
/* 1224 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1226 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1228 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1229 */       String healthmouseover = "";
/* 1230 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1232 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1235 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1236 */       int spacing = 0;
/* 1237 */       if (level >= 1)
/*      */       {
/* 1239 */         spacing = 40 * level;
/*      */       }
/* 1241 */       if (childtype.equals("HAI"))
/*      */       {
/* 1243 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1244 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1245 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1247 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1248 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1249 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1250 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1251 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1252 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1253 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1254 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1255 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1256 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1257 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1259 */         if (!forInventory)
/*      */         {
/* 1261 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1264 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1266 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1268 */           actions = editlink + actions;
/*      */         }
/* 1270 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1272 */           actions = actions + associatelink;
/*      */         }
/* 1274 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1275 */         String arrowimg = "";
/* 1276 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1278 */           actions = "";
/* 1279 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1280 */           checkbox = "";
/* 1281 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1283 */         if (isIt360)
/*      */         {
/* 1285 */           actionimg = "";
/* 1286 */           actions = "";
/* 1287 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1288 */           checkbox = "";
/*      */         }
/*      */         
/* 1291 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1293 */           actions = "";
/*      */         }
/* 1295 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1297 */           checkbox = "";
/*      */         }
/*      */         
/* 1300 */         String resourcelink = "";
/*      */         
/* 1302 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1304 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1308 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1311 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1312 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1313 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1314 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1315 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1317 */         if (!isIt360)
/*      */         {
/* 1319 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1323 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1326 */         toreturn.append("</tr>");
/* 1327 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1329 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1330 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1334 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1335 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1338 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1342 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1344 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1345 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1346 */             toreturn.append(assocMessage);
/* 1347 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1348 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1349 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1350 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1356 */         String resourcelink = null;
/* 1357 */         boolean hideEditLink = false;
/* 1358 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1360 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1361 */           hideEditLink = true;
/* 1362 */           if (isIt360)
/*      */           {
/* 1364 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1368 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1370 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1372 */           hideEditLink = true;
/* 1373 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1374 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1379 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1382 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1383 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1384 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1385 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1386 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1387 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1388 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1389 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1390 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1391 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1392 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1393 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1394 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1396 */         if (hideEditLink)
/*      */         {
/* 1398 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1400 */         if (!forInventory)
/*      */         {
/* 1402 */           removefromgroup = "";
/*      */         }
/* 1404 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1405 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1406 */           actions = actions + configcustomfields;
/*      */         }
/* 1408 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1410 */           actions = editlink + actions;
/*      */         }
/* 1412 */         String managedLink = "";
/* 1413 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1415 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1416 */           actions = "";
/* 1417 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1418 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1421 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1423 */           checkbox = "";
/*      */         }
/*      */         
/* 1426 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1428 */           actions = "";
/*      */         }
/* 1430 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1431 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1432 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1433 */         if (isIt360)
/*      */         {
/* 1435 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1439 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1441 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1442 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1443 */         if (!isIt360)
/*      */         {
/* 1445 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1451 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1454 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1461 */       StringBuilder toreturn = new StringBuilder();
/* 1462 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1463 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1464 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1465 */       String title = "";
/* 1466 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1467 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1468 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1469 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1471 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1473 */       else if ("5".equals(severity))
/*      */       {
/* 1475 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1479 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1481 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1482 */       toreturn.append(v);
/*      */       
/* 1484 */       toreturn.append(link);
/* 1485 */       if (severity == null)
/*      */       {
/* 1487 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1489 */       else if (severity.equals("5"))
/*      */       {
/* 1491 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1493 */       else if (severity.equals("4"))
/*      */       {
/* 1495 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1497 */       else if (severity.equals("1"))
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1506 */       toreturn.append("</a>");
/* 1507 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1511 */       ex.printStackTrace();
/*      */     }
/* 1513 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1520 */       StringBuilder toreturn = new StringBuilder();
/* 1521 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1522 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1523 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1524 */       if (message == null)
/*      */       {
/* 1526 */         message = "";
/*      */       }
/*      */       
/* 1529 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1530 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1532 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1533 */       toreturn.append(v);
/*      */       
/* 1535 */       toreturn.append(link);
/*      */       
/* 1537 */       if (severity == null)
/*      */       {
/* 1539 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1541 */       else if (severity.equals("5"))
/*      */       {
/* 1543 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1545 */       else if (severity.equals("1"))
/*      */       {
/* 1547 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1552 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1554 */       toreturn.append("</a>");
/* 1555 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1561 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1564 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1565 */     if (invokeActions != null) {
/* 1566 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1567 */       while (iterator.hasNext()) {
/* 1568 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1569 */         if (actionmap.containsKey(actionid)) {
/* 1570 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1575 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1579 */     String actionLink = "";
/* 1580 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1581 */     String query = "";
/* 1582 */     ResultSet rs = null;
/* 1583 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1584 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1585 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1586 */       actionLink = "method=" + methodName;
/*      */     }
/* 1588 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1589 */       actionLink = methodName;
/*      */     }
/* 1591 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1592 */     Iterator itr = methodarglist.iterator();
/* 1593 */     boolean isfirstparam = true;
/* 1594 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1595 */     while (itr.hasNext()) {
/* 1596 */       HashMap argmap = (HashMap)itr.next();
/* 1597 */       String argtype = (String)argmap.get("TYPE");
/* 1598 */       String argname = (String)argmap.get("IDENTITY");
/* 1599 */       String paramname = (String)argmap.get("PARAMETER");
/* 1600 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1601 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1602 */         isfirstparam = false;
/* 1603 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1605 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1609 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1613 */         actionLink = actionLink + "&";
/*      */       }
/* 1615 */       String paramValue = null;
/* 1616 */       String tempargname = argname;
/* 1617 */       if (commonValues.getProperty(tempargname) != null) {
/* 1618 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1621 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1622 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1623 */           if (dbType.equals("mysql")) {
/* 1624 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1627 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1629 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1631 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1632 */             if (rs.next()) {
/* 1633 */               paramValue = rs.getString("VALUE");
/* 1634 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1638 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1642 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1645 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1650 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1651 */           paramValue = rowId;
/*      */         }
/* 1653 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1654 */           paramValue = managedObjectName;
/*      */         }
/* 1656 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1657 */           paramValue = resID;
/*      */         }
/* 1659 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1660 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1663 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1665 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1666 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1667 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1669 */     return actionLink;
/*      */   }
/*      */   
/* 1672 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1673 */     String dependentAttribute = null;
/* 1674 */     String align = "left";
/*      */     
/* 1676 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1677 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1678 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1679 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1680 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1681 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1682 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1683 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1684 */       align = "center";
/*      */     }
/*      */     
/* 1687 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1688 */     String actualdata = "";
/*      */     
/* 1690 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1691 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1692 */         actualdata = availValue;
/*      */       }
/* 1694 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1695 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1699 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1700 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1703 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1709 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1710 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1711 */       toreturn.append("<table>");
/* 1712 */       toreturn.append("<tr>");
/* 1713 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1714 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1715 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1716 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1717 */         String toolTip = "";
/* 1718 */         String hideClass = "";
/* 1719 */         String textStyle = "";
/* 1720 */         boolean isreferenced = true;
/* 1721 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1722 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1723 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1724 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1726 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1727 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1728 */           while (valueList.hasMoreTokens()) {
/* 1729 */             String dependentVal = valueList.nextToken();
/* 1730 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1731 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1732 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1734 */               toolTip = "";
/* 1735 */               hideClass = "";
/* 1736 */               isreferenced = false;
/* 1737 */               textStyle = "disabledtext";
/* 1738 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1742 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1743 */           toolTip = "";
/* 1744 */           hideClass = "";
/* 1745 */           isreferenced = false;
/* 1746 */           textStyle = "disabledtext";
/* 1747 */           if (dependentImageMap != null) {
/* 1748 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1749 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1752 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1756 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1757 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1758 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1759 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1760 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1761 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1763 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1764 */           if (isreferenced) {
/* 1765 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1769 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1770 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1771 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1772 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1773 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1774 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1776 */           toreturn.append("</span>");
/* 1777 */           toreturn.append("</a>");
/* 1778 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1781 */       toreturn.append("</tr>");
/* 1782 */       toreturn.append("</table>");
/* 1783 */       toreturn.append("</td>");
/*      */     } else {
/* 1785 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1788 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1792 */     String colTime = null;
/* 1793 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1794 */     if ((rows != null) && (rows.size() > 0)) {
/* 1795 */       Iterator<String> itr = rows.iterator();
/* 1796 */       String maxColQuery = "";
/* 1797 */       for (;;) { if (itr.hasNext()) {
/* 1798 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1799 */           ResultSet maxCol = null;
/*      */           try {
/* 1801 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1802 */             while (maxCol.next()) {
/* 1803 */               if (colTime == null) {
/* 1804 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1807 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1816 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1818 */               if (maxCol != null)
/* 1819 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1821 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1816 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1818 */               if (maxCol != null)
/* 1819 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1821 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1826 */     return colTime;
/*      */   }
/*      */   
/* 1829 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1830 */     tablename = null;
/* 1831 */     ResultSet rsTable = null;
/* 1832 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1834 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1835 */       while (rsTable.next()) {
/* 1836 */         tablename = rsTable.getString("DATATABLE");
/* 1837 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1838 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1851 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1842 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1845 */         if (rsTable != null)
/* 1846 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1848 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1854 */     String argsList = "";
/* 1855 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1857 */       if (showArgsMap.get(row) != null) {
/* 1858 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1859 */         if (showArgslist != null) {
/* 1860 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1861 */             if (argsList.trim().equals("")) {
/* 1862 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1865 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1872 */       e.printStackTrace();
/* 1873 */       return "";
/*      */     }
/* 1875 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1880 */     String argsList = "";
/* 1881 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1884 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1886 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1887 */         if (hideArgsList != null)
/*      */         {
/* 1889 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1891 */             if (argsList.trim().equals(""))
/*      */             {
/* 1893 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1897 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1905 */       ex.printStackTrace();
/*      */     }
/* 1907 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1911 */     StringBuilder toreturn = new StringBuilder();
/* 1912 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1919 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1920 */       Iterator itr = tActionList.iterator();
/* 1921 */       while (itr.hasNext()) {
/* 1922 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1923 */         String confirmmsg = "";
/* 1924 */         String link = "";
/* 1925 */         String isJSP = "NO";
/* 1926 */         HashMap tactionMap = (HashMap)itr.next();
/* 1927 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1928 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1929 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1930 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1931 */           (actionmap.containsKey(actionId))) {
/* 1932 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1933 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1934 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1935 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1936 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1938 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1944 */           if (isTableAction) {
/* 1945 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1948 */             tableName = "Link";
/* 1949 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1950 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1951 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1952 */             toreturn.append("</a></td>");
/*      */           }
/* 1954 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1955 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1956 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1957 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1963 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1969 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1971 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1972 */       Properties prop = (Properties)node.getUserObject();
/* 1973 */       String mgID = prop.getProperty("label");
/* 1974 */       String mgName = prop.getProperty("value");
/* 1975 */       String isParent = prop.getProperty("isParent");
/* 1976 */       int mgIDint = Integer.parseInt(mgID);
/* 1977 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1979 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1981 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1982 */       if (node.getChildCount() > 0)
/*      */       {
/* 1984 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1986 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1988 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1990 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1994 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 1999 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2001 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2003 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2005 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2009 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2012 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2013 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2015 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2019 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2021 */       if (node.getChildCount() > 0)
/*      */       {
/* 2023 */         builder.append("<UL>");
/* 2024 */         printMGTree(node, builder);
/* 2025 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2030 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2031 */     StringBuffer toReturn = new StringBuffer();
/* 2032 */     String table = "-";
/*      */     try {
/* 2034 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2035 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2036 */       float total = 0.0F;
/* 2037 */       while (it.hasNext()) {
/* 2038 */         String attName = (String)it.next();
/* 2039 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2040 */         boolean roundOffData = false;
/* 2041 */         if ((data != null) && (!data.equals(""))) {
/* 2042 */           if (data.indexOf(",") != -1) {
/* 2043 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2046 */             float value = Float.parseFloat(data);
/* 2047 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2050 */             total += value;
/* 2051 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2054 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2059 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2060 */       while (attVsWidthList.hasNext()) {
/* 2061 */         String attName = (String)attVsWidthList.next();
/* 2062 */         String data = (String)attVsWidthProps.get(attName);
/* 2063 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2064 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2065 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2066 */         String className = (String)graphDetails.get("ClassName");
/* 2067 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2068 */         if (percentage < 1.0F)
/*      */         {
/* 2070 */           data = percentage + "";
/*      */         }
/* 2072 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2074 */       if (toReturn.length() > 0) {
/* 2075 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2079 */       e.printStackTrace();
/*      */     }
/* 2081 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2087 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2088 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2089 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2090 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2091 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2092 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2093 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2094 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2095 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2098 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2099 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2100 */       splitvalues[0] = multiplecondition.toString();
/* 2101 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2104 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2109 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2110 */     if (thresholdType != 3) {
/* 2111 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2112 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2113 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2114 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2115 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2116 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2118 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2119 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2120 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2121 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2122 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2123 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2125 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2126 */     if (updateSelected != null) {
/* 2127 */       updateSelected[0] = "selected";
/*      */     }
/* 2129 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2134 */       StringBuffer toreturn = new StringBuffer("");
/* 2135 */       if (commaSeparatedMsgId != null) {
/* 2136 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2137 */         int count = 0;
/* 2138 */         while (msgids.hasMoreTokens()) {
/* 2139 */           String id = msgids.nextToken();
/* 2140 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2141 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2142 */           count++;
/* 2143 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2144 */             if (toreturn.length() == 0) {
/* 2145 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2147 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2148 */             if (!image.trim().equals("")) {
/* 2149 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2151 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2152 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2155 */         if (toreturn.length() > 0) {
/* 2156 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2160 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2163 */       e.printStackTrace(); }
/* 2164 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void initAttributeIds(HttpServletRequest request)
/*      */   {
/* 2173 */     Hashtable table = new Hashtable(4);
/* 2174 */     String ids = getAttributeIds("StatelessSessionBean");
/* 2175 */     table.put("StatelessSessionBean", ids);
/*      */     
/* 2177 */     ids = getAttributeIds("EntityBean");
/* 2178 */     table.put("EntityBean", ids);
/*      */     
/* 2180 */     ids = getAttributeIds("StatefulSessionBean");
/* 2181 */     table.put("StatefulSessionBean", ids);
/*      */     
/* 2183 */     ids = getAttributeIds("MessageDrivenBean");
/* 2184 */     table.put("MessageDrivenBean", ids);
/* 2185 */     request.setAttribute("jbossejbids", table);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getAttributeIds(String type)
/*      */   {
/* 2192 */     String attributeids = "307,301";
/* 2193 */     if (type.startsWith("State"))
/*      */     {
/* 2195 */       attributeids = attributeids + ",303";
/*      */     }
/* 2197 */     if (!type.equals("StatelessSessionBean"))
/*      */     {
/*      */ 
/*      */ 
/* 2201 */       if (type.equals("EntityBean"))
/*      */       {
/* 2203 */         attributeids = attributeids + ",304,305";
/*      */       }
/* 2205 */       else if (type.equals("StatefulSessionBean"))
/*      */       {
/* 2207 */         attributeids = attributeids + ",306";
/*      */       }
/* 2209 */       else if (type.equals("MessageDrivenBean"))
/*      */       {
/* 2211 */         attributeids = attributeids + ",302"; }
/*      */     }
/* 2213 */     return attributeids;
/*      */   }
/*      */   
/* 2216 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2222 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2223 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2239 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2252 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2257 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.release();
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2260 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2261 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.release();
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/* 2263 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2270 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2273 */     JspWriter out = null;
/* 2274 */     Object page = this;
/* 2275 */     JspWriter _jspx_out = null;
/* 2276 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2280 */       response.setContentType("text/html;charset=UTF-8");
/* 2281 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2283 */       _jspx_page_context = pageContext;
/* 2284 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2285 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2286 */       session = pageContext.getSession();
/* 2287 */       out = pageContext.getOut();
/* 2288 */       _jspx_out = out;
/*      */       
/* 2290 */       out.write("<!--$Id$-->\n");
/* 2291 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2293 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2294 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2296 */       out.write(10);
/* 2297 */       out.write(10);
/* 2298 */       out.write(10);
/* 2299 */       out.write("\n\n\n\n");
/* 2300 */       GetWLSGraph wlsGraph = null;
/* 2301 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2302 */       if (wlsGraph == null) {
/* 2303 */         wlsGraph = new GetWLSGraph();
/* 2304 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2306 */       out.write(10);
/* 2307 */       com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB dataHandler = null;
/* 2308 */       dataHandler = (com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB)_jspx_page_context.getAttribute("dataHandler", 1);
/* 2309 */       if (dataHandler == null) {
/* 2310 */         dataHandler = new com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB();
/* 2311 */         _jspx_page_context.setAttribute("dataHandler", dataHandler, 1);
/*      */       }
/* 2313 */       out.write(10);
/* 2314 */       ManagedApplication mo = null;
/* 2315 */       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2316 */       if (mo == null) {
/* 2317 */         mo = new ManagedApplication();
/* 2318 */         _jspx_page_context.setAttribute("mo", mo, 1);
/*      */       }
/* 2320 */       out.write(10);
/* 2321 */       out.write(10);
/*      */       
/* 2323 */       String resourceid = request.getParameter("resourceid");
/* 2324 */       String collectionTime = request.getParameter("collectiontime");
/* 2325 */       String xaxis_ejb = FormatUtil.getString("am.webclient.jboss.ejbname.text");
/* 2326 */       String yaxis_readycount = FormatUtil.getString("am.webclient.jboss.readycount.text");
/* 2327 */       String yaxis_createcount = FormatUtil.getString("am.webclient.jboss.createcount.text");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2341 */       ArrayList rows = mo.getPropertiesList("SELECT AM_ManagedObject.resourceid,displayname,CreateCount,ReadyCount,RemoveCount,MethodReadyCount,MessageCount,MethodReadyCount,ReadyCount,PooledCount,PassiveCount,AM_EJB.EJBTYPE,AM_EJB.EJBJAR FROM AM_JB_EJBData,AM_ManagedObject,AM_EJB,AM_EAR where AM_JB_EJBData.COLLECTIONTIME =" + collectionTime + " and AM_JB_EJBData.RESOURCEID=AM_EJB.EJBID and AM_EAR.EARID=AM_EJB.PARENTID and  AM_EAR.PARENTID=" + resourceid + " and AM_ManagedObject.resourceid=AM_JB_EJBData.RESOURCEID");
/* 2342 */       if (rows.size() > 0)
/* 2343 */         request.setAttribute("details", rows);
/* 2344 */       initAttributeIds(request);
/*      */       
/*      */ 
/* 2347 */       out.write(10);
/*      */       
/* 2349 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2350 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2351 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/* 2353 */       _jspx_th_c_005fif_005f0.setTest("${empty details}");
/* 2354 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2355 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/* 2357 */           out.write("\n<span class=\"bodytext\">\n<table class=\"lrtbdarkborder\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\">\n<tr>\n<td class=\"tableheadingbborder\">\n");
/* 2358 */           out.print(FormatUtil.getString("am.webclient.weblogic.ejbdetails.text"));
/* 2359 */           out.write("\n</td>\n</tr>\n\n<tr>\n    <td class=\"bodytext\" height=\"22\"> &nbsp;");
/* 2360 */           out.print(FormatUtil.getString("am.webclient.jboss.noejbsdeployed.text"));
/* 2361 */           out.write("\n    </td>\n</tr>\n</table>\n\n</span>\n");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2366 */           out.write(10);
/* 2367 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2368 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2372 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2373 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/* 2376 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2377 */         out.write(10);
/*      */         
/* 2379 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2380 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2381 */         _jspx_th_c_005fif_005f1.setParent(null);
/*      */         
/* 2383 */         _jspx_th_c_005fif_005f1.setTest("${!empty details}");
/* 2384 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2385 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */           for (;;) {
/* 2387 */             out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n  <tr>\n    <td width=\"53%\"><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr class=\"lrtbdarkborder\">\n          <td class=\"tableheading\">");
/* 2388 */             out.print(FormatUtil.getString("am.webclient.jboss.ejbsinstances.text"));
/* 2389 */             out.write("</td>\n        </tr>\n        <tr class=\"lrtbdarkborder\">\n          <td  height=\"230\" align=\"center\">\n            ");
/*      */             
/*      */ 
/* 2392 */             wlsGraph.setParam(resourceid, "JBEJBCOUNT");
/*      */             
/*      */ 
/* 2395 */             out.write("\n            ");
/*      */             
/* 2397 */             BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.get(BarChart.class);
/* 2398 */             _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 2399 */             _jspx_th_awolf_005fbarchart_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */             
/* 2401 */             _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("wlsGraph");
/*      */             
/* 2403 */             _jspx_th_awolf_005fbarchart_005f0.setWidth("300");
/*      */             
/* 2405 */             _jspx_th_awolf_005fbarchart_005f0.setHeight("220");
/*      */             
/* 2407 */             _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */             
/* 2409 */             _jspx_th_awolf_005fbarchart_005f0.setUrl(false);
/*      */             
/* 2411 */             _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel(xaxis_ejb);
/*      */             
/* 2413 */             _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel(yaxis_createcount);
/*      */             
/* 2415 */             _jspx_th_awolf_005fbarchart_005f0.setBaseItemLabel(true);
/* 2416 */             int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 2417 */             if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 2418 */               if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2419 */                 out = _jspx_page_context.pushBody();
/* 2420 */                 _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 2421 */                 _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 2424 */                 out.write("\n            ");
/* 2425 */                 int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 2426 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 2429 */               if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2430 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 2433 */             if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 2434 */               this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*      */             }
/*      */             
/* 2437 */             this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 2438 */             out.write("</td>\n        </tr>\n      </table></td>\n    <td width=\"47%\" valign=\"top\" > <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n          <td width=\"51%\" class=\"tableheading\">");
/* 2439 */             out.print(FormatUtil.getString("am.webclient.jboss.entitybeans.text"));
/* 2440 */             out.write("<a name=\"server\" id=\"server\"></a></td>\n        </tr>\n        <tr>\n          <td height=\"230\" align=\"center\">\n            ");
/*      */             
/*      */ 
/* 2443 */             wlsGraph.setParam(resourceid, "JBEJBREADYCOUNT");
/*      */             
/*      */ 
/* 2446 */             out.write("\n            ");
/*      */             
/* 2448 */             BarChart _jspx_th_awolf_005fbarchart_005f1 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.get(BarChart.class);
/* 2449 */             _jspx_th_awolf_005fbarchart_005f1.setPageContext(_jspx_page_context);
/* 2450 */             _jspx_th_awolf_005fbarchart_005f1.setParent(_jspx_th_c_005fif_005f1);
/*      */             
/* 2452 */             _jspx_th_awolf_005fbarchart_005f1.setDataSetProducer("wlsGraph");
/*      */             
/* 2454 */             _jspx_th_awolf_005fbarchart_005f1.setWidth("300");
/*      */             
/* 2456 */             _jspx_th_awolf_005fbarchart_005f1.setHeight("220");
/*      */             
/* 2458 */             _jspx_th_awolf_005fbarchart_005f1.setLegend("false");
/*      */             
/* 2460 */             _jspx_th_awolf_005fbarchart_005f1.setUrl(false);
/*      */             
/* 2462 */             _jspx_th_awolf_005fbarchart_005f1.setXaxisLabel(xaxis_ejb);
/*      */             
/* 2464 */             _jspx_th_awolf_005fbarchart_005f1.setYaxisLabel(yaxis_readycount);
/*      */             
/* 2466 */             _jspx_th_awolf_005fbarchart_005f1.setBaseItemLabel(true);
/* 2467 */             int _jspx_eval_awolf_005fbarchart_005f1 = _jspx_th_awolf_005fbarchart_005f1.doStartTag();
/* 2468 */             if (_jspx_eval_awolf_005fbarchart_005f1 != 0) {
/* 2469 */               if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 2470 */                 out = _jspx_page_context.pushBody();
/* 2471 */                 _jspx_th_awolf_005fbarchart_005f1.setBodyContent((BodyContent)out);
/* 2472 */                 _jspx_th_awolf_005fbarchart_005f1.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 2475 */                 out.write("\n            ");
/* 2476 */                 int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f1.doAfterBody();
/* 2477 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 2480 */               if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 2481 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 2484 */             if (_jspx_th_awolf_005fbarchart_005f1.doEndTag() == 5) {
/* 2485 */               this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.reuse(_jspx_th_awolf_005fbarchart_005f1); return;
/*      */             }
/*      */             
/* 2488 */             this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.reuse(_jspx_th_awolf_005fbarchart_005f1);
/* 2489 */             out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"31\" class=\"tableheadingtrans\">");
/* 2490 */             out.print(FormatUtil.getString("am.webclient.jboss.ejbstatistics.text"));
/* 2491 */             out.write(" &nbsp;&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n    <td rowspan=\"2\" class=\"columnheading\">");
/* 2492 */             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2493 */             out.write("</td>\n    <td rowspan=\"2\" class=\"columnheading\">");
/* 2494 */             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2495 */             out.write("</td>\n    <td rowspan=\"2\" class=\"columnheadingrightborder\">");
/* 2496 */             out.print(FormatUtil.getString("am.webclient.jboss.module.text"));
/* 2497 */             out.write("</td>\n    <td colspan=\"5\" class=\"columnheadingrightborder\" align=\"center\">");
/* 2498 */             out.print(FormatUtil.getString("am.webclient.jboss.instances.text"));
/* 2499 */             out.write("</td>\n    <td rowspan=\"2\" class=\"columnheadingrightborder\">&nbsp;  </td>\n        </tr>\n\n        <tr>\n          <td class=\"columnheadingrightborder\">");
/* 2500 */             out.print(FormatUtil.getString("am.webclient.jboss.created.text"));
/* 2501 */             out.write("</td>\n          <td class=\"columnheadingrightborder\">");
/* 2502 */             out.print(FormatUtil.getString("am.webclient.jboss.removed.text"));
/* 2503 */             out.write(" </td>\n          <td class=\"columnheadingrightborder\">");
/* 2504 */             out.print(FormatUtil.getString("am.webclient.jboss.availableforprocessing.text"));
/* 2505 */             out.write("</td>\n          <td class=\"columnheadingrightborder\">");
/* 2506 */             out.print(FormatUtil.getString("am.webclient.jboss.ready.text"));
/* 2507 */             out.write("</td>\n          <td class=\"columnheadingrightborder\">");
/* 2508 */             out.print(FormatUtil.getString("am.webclient.jboss.pooled.text"));
/* 2509 */             out.write("</td>\n        </tr>\n\n\n\n\n  ");
/*      */             
/* 2511 */             ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2512 */             _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2513 */             _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */             
/* 2515 */             _jspx_th_c_005fforEach_005f0.setVar("props");
/*      */             
/* 2517 */             _jspx_th_c_005fforEach_005f0.setItems("${details}");
/*      */             
/* 2519 */             _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 2520 */             int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */             try {
/* 2522 */               int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2523 */               if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                 for (;;) {
/* 2525 */                   out.write(10);
/* 2526 */                   out.write(32);
/* 2527 */                   out.write(32);
/* 2528 */                   if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2530 */                   out.write(10);
/* 2531 */                   out.write(32);
/* 2532 */                   out.write(32);
/* 2533 */                   if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2535 */                   out.write("\n       \t<tr  >\n  <td height=\"20\" class=\"");
/* 2536 */                   if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2538 */                   out.write("\" >&nbsp; <a href=\"/showresource.do?haid=");
/* 2539 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2541 */                   out.write("&resID=");
/* 2542 */                   if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2544 */                   out.write("&resourceid=");
/* 2545 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2547 */                   out.write("&resourcename=");
/* 2548 */                   if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2550 */                   out.write("&type=ejb&method=showJ2EEdetails&appservertype=JBOSS-server&ejbtype=");
/* 2551 */                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2553 */                   out.write("&monitorname=");
/* 2554 */                   if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2556 */                   out.write("\"\n    class=\"resourcename\" title=\"");
/* 2557 */                   if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2559 */                   out.write(34);
/* 2560 */                   out.write(62);
/* 2561 */                   out.write(32);
/* 2562 */                   if (_jspx_meth_am_005fTruncate_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2564 */                   out.write(" </a>\n  </td>\n  <td  class=\"");
/* 2565 */                   if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2567 */                   out.write("\" > ");
/* 2568 */                   if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2570 */                   out.write("</td>\n  <td  class=\"");
/* 2571 */                   if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2573 */                   out.write("\" > ");
/* 2574 */                   if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2576 */                   out.write("&nbsp; </td>\n  <td align=\"center\"  class=\"");
/* 2577 */                   if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2579 */                   out.write(34);
/* 2580 */                   out.write(62);
/* 2581 */                   if (_jspx_meth_am_005fFormat_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2583 */                   out.write("&nbsp;\n  </td>\n  <td align=\"center\"  class=\"");
/* 2584 */                   if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2586 */                   out.write(34);
/* 2587 */                   out.write(62);
/* 2588 */                   if (_jspx_meth_am_005fFormat_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2590 */                   out.write("&nbsp;\n  </td>\n  <td align=\"center\"  width=\"25%\" class=\"");
/* 2591 */                   if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2593 */                   out.write(34);
/* 2594 */                   out.write(62);
/* 2595 */                   out.write(32);
/* 2596 */                   if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2598 */                   out.write(32);
/* 2599 */                   if (_jspx_meth_c_005fif_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2601 */                   out.write(" </td>\n  <td align=\"center\"  class=\"");
/* 2602 */                   if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2604 */                   out.write(34);
/* 2605 */                   out.write(62);
/* 2606 */                   out.write(32);
/* 2607 */                   if (_jspx_meth_c_005fif_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2609 */                   out.write(32);
/* 2610 */                   if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2612 */                   out.write(" </td>\n  <td align=\"center\"  class=\"");
/* 2613 */                   if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2615 */                   out.write(34);
/* 2616 */                   out.write(62);
/* 2617 */                   out.write(32);
/* 2618 */                   if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2620 */                   out.write(32);
/* 2621 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2623 */                   out.write(" </td>\n  <td align=\"center\"  class=\"");
/* 2624 */                   if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2626 */                   out.write("\"><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2627 */                   if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2629 */                   out.write("&attributeIDs=");
/* 2630 */                   if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2632 */                   out.write("&attributeToSelect=307&redirectto=");
/* 2633 */                   out.print(java.net.URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid));
/* 2634 */                   out.write("'>\n    <img src=\"/images/icon_associateaction.gif\" alt=\"");
/* 2635 */                   out.print(ALERTCONFIG_TEXT);
/* 2636 */                   out.write("\" border=\"0\" ></a></td>\n  <!--\t<td  class=\"bodytext\" align=\"right\">\n\t");
/* 2637 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2639 */                   out.write(10);
/* 2640 */                   out.write(9);
/* 2641 */                   if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2643 */                   out.write("\n\t</td>\n\t<td  class=\"bodytext\" align=\"right\">\n\t");
/* 2644 */                   if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2646 */                   out.write(10);
/* 2647 */                   out.write(9);
/* 2648 */                   if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                   }
/* 2650 */                   out.write("\n\t</td>\t-->\n  </tr>\n  ");
/* 2651 */                   int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2652 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2656 */               if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */               }
/*      */             }
/*      */             catch (Throwable _jspx_exception)
/*      */             {
/*      */               for (;;)
/*      */               {
/* 2660 */                 int tmp3191_3190 = 0; int[] tmp3191_3188 = _jspx_push_body_count_c_005fforEach_005f0; int tmp3193_3192 = tmp3191_3188[tmp3191_3190];tmp3191_3188[tmp3191_3190] = (tmp3193_3192 - 1); if (tmp3193_3192 <= 0) break;
/* 2661 */                 out = _jspx_page_context.popBody(); }
/* 2662 */               _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */             } finally {
/* 2664 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2665 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */             }
/* 2667 */             out.write("\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"72%\" height=\"31\" class=\"tablebottom\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td width=\"16%\" class=\"arial10\"><img src=\"/images/icon_ejb_entity.gif\" width=\"21\" height=\"13\" align=\"absmiddle\">\n            - Entity bean</td>\n          <td width=\"20%\" class=\"arial10\"><img src=\"/images/icon_ejb_stateless.gif\" align=\"absmiddle\">\n            - Stateless bean</td>\n          <td width=\"17%\" class=\"arial10\"><img src=\"/images/icon_ejb_stateful.gif\" align=\"absmiddle\">\n            - Stateful bean</td>\n          <td width=\"31%\" class=\"arial10\"><img src=\"/images/icon_ejb_mbean.gif\" align=\"absmiddle\">\n            - Message driven bean</td>\n          <td width=\"16%\" align=\"right\" class=\"arial10\"><a href=\"#jbtop\" class=\"staticlinks\">");
/* 2668 */             out.print(FormatUtil.getString("am.webclient.common.top.text"));
/* 2669 */             out.write("</a>&nbsp;&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n\n");
/* 2670 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2671 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2675 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2676 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */         }
/*      */         else {
/* 2679 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2680 */           out.write(10);
/* 2681 */           out.write(10);
/* 2682 */           out.write(10);
/* 2683 */           out.write(10);
/*      */         }
/* 2685 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2686 */         out = _jspx_out;
/* 2687 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2688 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2689 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2692 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2698 */     PageContext pageContext = _jspx_page_context;
/* 2699 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2701 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2702 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2703 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2705 */     _jspx_th_c_005fif_005f2.setTest("${status.count %2 == 1}");
/* 2706 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2707 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 2709 */         out.write("\n   ");
/* 2710 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2711 */           return true;
/* 2712 */         out.write(" \n  ");
/* 2713 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2714 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2718 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2719 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2720 */       return true;
/*      */     }
/* 2722 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2728 */     PageContext pageContext = _jspx_page_context;
/* 2729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2731 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2732 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2733 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 2735 */     _jspx_th_c_005fset_005f0.setVar("class");
/*      */     
/* 2737 */     _jspx_th_c_005fset_005f0.setValue("${'whitegrayborder'}");
/* 2738 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2739 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2740 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2741 */       return true;
/*      */     }
/* 2743 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2749 */     PageContext pageContext = _jspx_page_context;
/* 2750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2752 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2753 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2754 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2756 */     _jspx_th_c_005fif_005f3.setTest("${status.count %2 == 0}");
/* 2757 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2758 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 2760 */         out.write(10);
/* 2761 */         out.write(32);
/* 2762 */         out.write(32);
/* 2763 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2764 */           return true;
/* 2765 */         out.write(" \n\n  ");
/* 2766 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2767 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2771 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2772 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2773 */       return true;
/*      */     }
/* 2775 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2781 */     PageContext pageContext = _jspx_page_context;
/* 2782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2784 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2785 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2786 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 2788 */     _jspx_th_c_005fset_005f1.setVar("class");
/*      */     
/* 2790 */     _jspx_th_c_005fset_005f1.setValue("${'yellowgrayborder'}");
/* 2791 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2792 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2793 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 2794 */       return true;
/*      */     }
/* 2796 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 2797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2802 */     PageContext pageContext = _jspx_page_context;
/* 2803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2805 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2806 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2807 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2809 */     _jspx_th_c_005fout_005f0.setValue("${class}");
/* 2810 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2811 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2812 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2813 */       return true;
/*      */     }
/* 2815 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2821 */     PageContext pageContext = _jspx_page_context;
/* 2822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2824 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2825 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2826 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2828 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 2829 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2830 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2831 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2832 */       return true;
/*      */     }
/* 2834 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2835 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2840 */     PageContext pageContext = _jspx_page_context;
/* 2841 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2843 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2844 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2845 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2847 */     _jspx_th_c_005fout_005f2.setValue("${props.resourceid}");
/* 2848 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2849 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2850 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2851 */       return true;
/*      */     }
/* 2853 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2854 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2859 */     PageContext pageContext = _jspx_page_context;
/* 2860 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2862 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2863 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2864 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2866 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 2867 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2868 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2869 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2870 */       return true;
/*      */     }
/* 2872 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2878 */     PageContext pageContext = _jspx_page_context;
/* 2879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2881 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2882 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2883 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2885 */     _jspx_th_c_005fout_005f4.setValue("${props.displayname}");
/* 2886 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2887 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2888 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2889 */       return true;
/*      */     }
/* 2891 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2897 */     PageContext pageContext = _jspx_page_context;
/* 2898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2900 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2901 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2902 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2904 */     _jspx_th_c_005fout_005f5.setValue("${props.EJBTYPE}");
/* 2905 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2906 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2907 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2908 */       return true;
/*      */     }
/* 2910 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2911 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2916 */     PageContext pageContext = _jspx_page_context;
/* 2917 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2919 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2920 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2921 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2923 */     _jspx_th_c_005fout_005f6.setValue("${monitorname}");
/* 2924 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2925 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2926 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2927 */       return true;
/*      */     }
/* 2929 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2930 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2935 */     PageContext pageContext = _jspx_page_context;
/* 2936 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2938 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2939 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2940 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2942 */     _jspx_th_c_005fout_005f7.setValue("${props.displayname}");
/* 2943 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2944 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2945 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2946 */       return true;
/*      */     }
/* 2948 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2949 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2954 */     PageContext pageContext = _jspx_page_context;
/* 2955 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2957 */     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 2958 */     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 2959 */     _jspx_th_am_005fTruncate_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2961 */     _jspx_th_am_005fTruncate_005f0.setLength(20);
/*      */     
/* 2963 */     _jspx_th_am_005fTruncate_005f0.setTooltip("true");
/* 2964 */     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 2965 */     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 2966 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 2967 */         out = _jspx_page_context.pushBody();
/* 2968 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 2969 */         _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 2970 */         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2973 */         out.write(32);
/* 2974 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_am_005fTruncate_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2975 */           return true;
/* 2976 */         out.write(32);
/* 2977 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 2978 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2981 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 2982 */         out = _jspx_page_context.popBody();
/* 2983 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 2986 */     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 2987 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 2988 */       return true;
/*      */     }
/* 2990 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 2991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_am_005fTruncate_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2996 */     PageContext pageContext = _jspx_page_context;
/* 2997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2999 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3000 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3001 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_am_005fTruncate_005f0);
/*      */     
/* 3003 */     _jspx_th_c_005fout_005f8.setValue("${props.displayname}");
/* 3004 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3005 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3006 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3007 */       return true;
/*      */     }
/* 3009 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3015 */     PageContext pageContext = _jspx_page_context;
/* 3016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3018 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3019 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3020 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3022 */     _jspx_th_c_005fout_005f9.setValue("${class}");
/* 3023 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3024 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3025 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3026 */       return true;
/*      */     }
/* 3028 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3034 */     PageContext pageContext = _jspx_page_context;
/* 3035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3037 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 3038 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3039 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3041 */     _jspx_th_c_005fout_005f10.setValue("${ejbTypes[props.EJBTYPE]}");
/*      */     
/* 3043 */     _jspx_th_c_005fout_005f10.setEscapeXml("false");
/* 3044 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3045 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3046 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3047 */       return true;
/*      */     }
/* 3049 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3055 */     PageContext pageContext = _jspx_page_context;
/* 3056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3058 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3059 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3060 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3062 */     _jspx_th_c_005fout_005f11.setValue("${class}");
/* 3063 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3064 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3065 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3066 */       return true;
/*      */     }
/* 3068 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3074 */     PageContext pageContext = _jspx_page_context;
/* 3075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3077 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3078 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3079 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3081 */     _jspx_th_c_005fout_005f12.setValue("${props.EJBJAR}");
/* 3082 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3083 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3084 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3085 */       return true;
/*      */     }
/* 3087 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3093 */     PageContext pageContext = _jspx_page_context;
/* 3094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3096 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3097 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 3098 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3100 */     _jspx_th_c_005fout_005f13.setValue("${class}");
/* 3101 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 3102 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 3103 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3104 */       return true;
/*      */     }
/* 3106 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3112 */     PageContext pageContext = _jspx_page_context;
/* 3113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3115 */     FormatTag _jspx_th_am_005fFormat_005f0 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 3116 */     _jspx_th_am_005fFormat_005f0.setPageContext(_jspx_page_context);
/* 3117 */     _jspx_th_am_005fFormat_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3119 */     _jspx_th_am_005fFormat_005f0.setType("Number");
/* 3120 */     int _jspx_eval_am_005fFormat_005f0 = _jspx_th_am_005fFormat_005f0.doStartTag();
/* 3121 */     if (_jspx_eval_am_005fFormat_005f0 != 0) {
/* 3122 */       if (_jspx_eval_am_005fFormat_005f0 != 1) {
/* 3123 */         out = _jspx_page_context.pushBody();
/* 3124 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3125 */         _jspx_th_am_005fFormat_005f0.setBodyContent((BodyContent)out);
/* 3126 */         _jspx_th_am_005fFormat_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3129 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_am_005fFormat_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3130 */           return true;
/* 3131 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f0.doAfterBody();
/* 3132 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3135 */       if (_jspx_eval_am_005fFormat_005f0 != 1) {
/* 3136 */         out = _jspx_page_context.popBody();
/* 3137 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3140 */     if (_jspx_th_am_005fFormat_005f0.doEndTag() == 5) {
/* 3141 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f0);
/* 3142 */       return true;
/*      */     }
/* 3144 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f0);
/* 3145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_am_005fFormat_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3150 */     PageContext pageContext = _jspx_page_context;
/* 3151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3153 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3154 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 3155 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_am_005fFormat_005f0);
/*      */     
/* 3157 */     _jspx_th_c_005fout_005f14.setValue("${props.CreateCount}");
/* 3158 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 3159 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 3160 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3161 */       return true;
/*      */     }
/* 3163 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3169 */     PageContext pageContext = _jspx_page_context;
/* 3170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3172 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3173 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 3174 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3176 */     _jspx_th_c_005fout_005f15.setValue("${class}");
/* 3177 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 3178 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 3179 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3180 */       return true;
/*      */     }
/* 3182 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3188 */     PageContext pageContext = _jspx_page_context;
/* 3189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3191 */     FormatTag _jspx_th_am_005fFormat_005f1 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 3192 */     _jspx_th_am_005fFormat_005f1.setPageContext(_jspx_page_context);
/* 3193 */     _jspx_th_am_005fFormat_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3195 */     _jspx_th_am_005fFormat_005f1.setType("Number");
/* 3196 */     int _jspx_eval_am_005fFormat_005f1 = _jspx_th_am_005fFormat_005f1.doStartTag();
/* 3197 */     if (_jspx_eval_am_005fFormat_005f1 != 0) {
/* 3198 */       if (_jspx_eval_am_005fFormat_005f1 != 1) {
/* 3199 */         out = _jspx_page_context.pushBody();
/* 3200 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3201 */         _jspx_th_am_005fFormat_005f1.setBodyContent((BodyContent)out);
/* 3202 */         _jspx_th_am_005fFormat_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3205 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_am_005fFormat_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3206 */           return true;
/* 3207 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f1.doAfterBody();
/* 3208 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3211 */       if (_jspx_eval_am_005fFormat_005f1 != 1) {
/* 3212 */         out = _jspx_page_context.popBody();
/* 3213 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3216 */     if (_jspx_th_am_005fFormat_005f1.doEndTag() == 5) {
/* 3217 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f1);
/* 3218 */       return true;
/*      */     }
/* 3220 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f1);
/* 3221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_am_005fFormat_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3226 */     PageContext pageContext = _jspx_page_context;
/* 3227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3229 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3230 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 3231 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_am_005fFormat_005f1);
/*      */     
/* 3233 */     _jspx_th_c_005fout_005f16.setValue("${props.RemoveCount}");
/* 3234 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 3235 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 3236 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3237 */       return true;
/*      */     }
/* 3239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3245 */     PageContext pageContext = _jspx_page_context;
/* 3246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3248 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3249 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 3250 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3252 */     _jspx_th_c_005fout_005f17.setValue("${class}");
/* 3253 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 3254 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 3255 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 3256 */       return true;
/*      */     }
/* 3258 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 3259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3264 */     PageContext pageContext = _jspx_page_context;
/* 3265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3267 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3268 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3269 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3271 */     _jspx_th_c_005fif_005f4.setTest("${props.MethodReadyCount != -1}");
/* 3272 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3273 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 3275 */         out.write("\n    ");
/* 3276 */         if (_jspx_meth_am_005fFormat_005f2(_jspx_th_c_005fif_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3277 */           return true;
/* 3278 */         out.write("&nbsp;\n    ");
/* 3279 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3280 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3284 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3285 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3286 */       return true;
/*      */     }
/* 3288 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f2(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3294 */     PageContext pageContext = _jspx_page_context;
/* 3295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3297 */     FormatTag _jspx_th_am_005fFormat_005f2 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 3298 */     _jspx_th_am_005fFormat_005f2.setPageContext(_jspx_page_context);
/* 3299 */     _jspx_th_am_005fFormat_005f2.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3301 */     _jspx_th_am_005fFormat_005f2.setType("Number");
/* 3302 */     int _jspx_eval_am_005fFormat_005f2 = _jspx_th_am_005fFormat_005f2.doStartTag();
/* 3303 */     if (_jspx_eval_am_005fFormat_005f2 != 0) {
/* 3304 */       if (_jspx_eval_am_005fFormat_005f2 != 1) {
/* 3305 */         out = _jspx_page_context.pushBody();
/* 3306 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3307 */         _jspx_th_am_005fFormat_005f2.setBodyContent((BodyContent)out);
/* 3308 */         _jspx_th_am_005fFormat_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3311 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_am_005fFormat_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3312 */           return true;
/* 3313 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f2.doAfterBody();
/* 3314 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3317 */       if (_jspx_eval_am_005fFormat_005f2 != 1) {
/* 3318 */         out = _jspx_page_context.popBody();
/* 3319 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3322 */     if (_jspx_th_am_005fFormat_005f2.doEndTag() == 5) {
/* 3323 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f2);
/* 3324 */       return true;
/*      */     }
/* 3326 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f2);
/* 3327 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_am_005fFormat_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3332 */     PageContext pageContext = _jspx_page_context;
/* 3333 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3335 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3336 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 3337 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_am_005fFormat_005f2);
/*      */     
/* 3339 */     _jspx_th_c_005fout_005f18.setValue("${props.MethodReadyCount}");
/* 3340 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 3341 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 3342 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 3343 */       return true;
/*      */     }
/* 3345 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 3346 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3351 */     PageContext pageContext = _jspx_page_context;
/* 3352 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3354 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3355 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3356 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3358 */     _jspx_th_c_005fif_005f5.setTest("${props.MethodReadyCount == -1}");
/* 3359 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3360 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 3362 */         out.write(" NA&nbsp; ");
/* 3363 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3364 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3368 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3369 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3370 */       return true;
/*      */     }
/* 3372 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3373 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3378 */     PageContext pageContext = _jspx_page_context;
/* 3379 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3381 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3382 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 3383 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3385 */     _jspx_th_c_005fout_005f19.setValue("${class}");
/* 3386 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 3387 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 3388 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 3389 */       return true;
/*      */     }
/* 3391 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 3392 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3397 */     PageContext pageContext = _jspx_page_context;
/* 3398 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3400 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3401 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3402 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3404 */     _jspx_th_c_005fif_005f6.setTest("${props.ReadyCount != -1}");
/* 3405 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3406 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 3408 */         out.write("\n    ");
/* 3409 */         if (_jspx_meth_am_005fFormat_005f3(_jspx_th_c_005fif_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3410 */           return true;
/* 3411 */         out.write("&nbsp;\n    ");
/* 3412 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3413 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3417 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3418 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3419 */       return true;
/*      */     }
/* 3421 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3422 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f3(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3427 */     PageContext pageContext = _jspx_page_context;
/* 3428 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3430 */     FormatTag _jspx_th_am_005fFormat_005f3 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 3431 */     _jspx_th_am_005fFormat_005f3.setPageContext(_jspx_page_context);
/* 3432 */     _jspx_th_am_005fFormat_005f3.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 3434 */     _jspx_th_am_005fFormat_005f3.setType("Number");
/* 3435 */     int _jspx_eval_am_005fFormat_005f3 = _jspx_th_am_005fFormat_005f3.doStartTag();
/* 3436 */     if (_jspx_eval_am_005fFormat_005f3 != 0) {
/* 3437 */       if (_jspx_eval_am_005fFormat_005f3 != 1) {
/* 3438 */         out = _jspx_page_context.pushBody();
/* 3439 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3440 */         _jspx_th_am_005fFormat_005f3.setBodyContent((BodyContent)out);
/* 3441 */         _jspx_th_am_005fFormat_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3444 */         if (_jspx_meth_c_005fout_005f20(_jspx_th_am_005fFormat_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3445 */           return true;
/* 3446 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f3.doAfterBody();
/* 3447 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3450 */       if (_jspx_eval_am_005fFormat_005f3 != 1) {
/* 3451 */         out = _jspx_page_context.popBody();
/* 3452 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3455 */     if (_jspx_th_am_005fFormat_005f3.doEndTag() == 5) {
/* 3456 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f3);
/* 3457 */       return true;
/*      */     }
/* 3459 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f3);
/* 3460 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_am_005fFormat_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3465 */     PageContext pageContext = _jspx_page_context;
/* 3466 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3468 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3469 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 3470 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_am_005fFormat_005f3);
/*      */     
/* 3472 */     _jspx_th_c_005fout_005f20.setValue("${props.ReadyCount}");
/* 3473 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 3474 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 3475 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 3476 */       return true;
/*      */     }
/* 3478 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 3479 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3484 */     PageContext pageContext = _jspx_page_context;
/* 3485 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3487 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3488 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3489 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3491 */     _jspx_th_c_005fif_005f7.setTest("${props.ReadyCount == -1}");
/* 3492 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3493 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 3495 */         out.write(" NA&nbsp; ");
/* 3496 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3497 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3501 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3502 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3503 */       return true;
/*      */     }
/* 3505 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3506 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3511 */     PageContext pageContext = _jspx_page_context;
/* 3512 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3514 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3515 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 3516 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3518 */     _jspx_th_c_005fout_005f21.setValue("${class}");
/* 3519 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 3520 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 3521 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 3522 */       return true;
/*      */     }
/* 3524 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 3525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3530 */     PageContext pageContext = _jspx_page_context;
/* 3531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3533 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3534 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3535 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3537 */     _jspx_th_c_005fif_005f8.setTest("${props.PooledCount != -1}");
/* 3538 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3539 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 3541 */         out.write("\n    ");
/* 3542 */         if (_jspx_meth_am_005fFormat_005f4(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3543 */           return true;
/* 3544 */         out.write("&nbsp;\n    ");
/* 3545 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3546 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3550 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3551 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3552 */       return true;
/*      */     }
/* 3554 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3555 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f4(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3560 */     PageContext pageContext = _jspx_page_context;
/* 3561 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3563 */     FormatTag _jspx_th_am_005fFormat_005f4 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 3564 */     _jspx_th_am_005fFormat_005f4.setPageContext(_jspx_page_context);
/* 3565 */     _jspx_th_am_005fFormat_005f4.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 3567 */     _jspx_th_am_005fFormat_005f4.setType("Number");
/* 3568 */     int _jspx_eval_am_005fFormat_005f4 = _jspx_th_am_005fFormat_005f4.doStartTag();
/* 3569 */     if (_jspx_eval_am_005fFormat_005f4 != 0) {
/* 3570 */       if (_jspx_eval_am_005fFormat_005f4 != 1) {
/* 3571 */         out = _jspx_page_context.pushBody();
/* 3572 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3573 */         _jspx_th_am_005fFormat_005f4.setBodyContent((BodyContent)out);
/* 3574 */         _jspx_th_am_005fFormat_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3577 */         if (_jspx_meth_c_005fout_005f22(_jspx_th_am_005fFormat_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3578 */           return true;
/* 3579 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f4.doAfterBody();
/* 3580 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3583 */       if (_jspx_eval_am_005fFormat_005f4 != 1) {
/* 3584 */         out = _jspx_page_context.popBody();
/* 3585 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3588 */     if (_jspx_th_am_005fFormat_005f4.doEndTag() == 5) {
/* 3589 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f4);
/* 3590 */       return true;
/*      */     }
/* 3592 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f4);
/* 3593 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_am_005fFormat_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3598 */     PageContext pageContext = _jspx_page_context;
/* 3599 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3601 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3602 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 3603 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_am_005fFormat_005f4);
/*      */     
/* 3605 */     _jspx_th_c_005fout_005f22.setValue("${props.PooledCount}");
/* 3606 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 3607 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 3608 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 3609 */       return true;
/*      */     }
/* 3611 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 3612 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3617 */     PageContext pageContext = _jspx_page_context;
/* 3618 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3620 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3621 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3622 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3624 */     _jspx_th_c_005fif_005f9.setTest("${props.PooledCount == -1}");
/* 3625 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3626 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 3628 */         out.write(" NA&nbsp; ");
/* 3629 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3630 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3634 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3635 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3636 */       return true;
/*      */     }
/* 3638 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3644 */     PageContext pageContext = _jspx_page_context;
/* 3645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3647 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3648 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 3649 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3651 */     _jspx_th_c_005fout_005f23.setValue("${class}");
/* 3652 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 3653 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 3654 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 3655 */       return true;
/*      */     }
/* 3657 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 3658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3663 */     PageContext pageContext = _jspx_page_context;
/* 3664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3666 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3667 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 3668 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3670 */     _jspx_th_c_005fout_005f24.setValue("${props.resourceid}");
/* 3671 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 3672 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 3673 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 3674 */       return true;
/*      */     }
/* 3676 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 3677 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3682 */     PageContext pageContext = _jspx_page_context;
/* 3683 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3685 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3686 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 3687 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3689 */     _jspx_th_c_005fout_005f25.setValue("${jbossejbids[props.EJBTYPE]}");
/* 3690 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 3691 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 3692 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 3693 */       return true;
/*      */     }
/* 3695 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 3696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3701 */     PageContext pageContext = _jspx_page_context;
/* 3702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3704 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3705 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3706 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3708 */     _jspx_th_c_005fif_005f10.setTest("${props.PassiveCount != -1}");
/* 3709 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3710 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 3712 */         out.write(10);
/* 3713 */         out.write(9);
/* 3714 */         if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fif_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3715 */           return true;
/* 3716 */         out.write("&nbsp;\n\t");
/* 3717 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3718 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3722 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3723 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3724 */       return true;
/*      */     }
/* 3726 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3732 */     PageContext pageContext = _jspx_page_context;
/* 3733 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3735 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3736 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 3737 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 3739 */     _jspx_th_c_005fout_005f26.setValue("${props.PassiveCount}");
/* 3740 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 3741 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 3742 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 3743 */       return true;
/*      */     }
/* 3745 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 3746 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3751 */     PageContext pageContext = _jspx_page_context;
/* 3752 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3754 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3755 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3756 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3758 */     _jspx_th_c_005fif_005f11.setTest("${props.PassiveCount == -1}");
/* 3759 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3760 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 3762 */         out.write("\n\tNA&nbsp;\n\t");
/* 3763 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3764 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3768 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3769 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3770 */       return true;
/*      */     }
/* 3772 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3773 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3778 */     PageContext pageContext = _jspx_page_context;
/* 3779 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3781 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3782 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3783 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3785 */     _jspx_th_c_005fif_005f12.setTest("${props.MessageCount != -1}");
/* 3786 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3787 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 3789 */         out.write(10);
/* 3790 */         out.write(9);
/* 3791 */         if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fif_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3792 */           return true;
/* 3793 */         out.write("&nbsp;\n\t");
/* 3794 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3795 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3799 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3800 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3801 */       return true;
/*      */     }
/* 3803 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3809 */     PageContext pageContext = _jspx_page_context;
/* 3810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3812 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3813 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 3814 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 3816 */     _jspx_th_c_005fout_005f27.setValue("${props.MessageCount}");
/* 3817 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 3818 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 3819 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 3820 */       return true;
/*      */     }
/* 3822 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 3823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3828 */     PageContext pageContext = _jspx_page_context;
/* 3829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3831 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3832 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3833 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3835 */     _jspx_th_c_005fif_005f13.setTest("${props.MessageCount == -1}");
/* 3836 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3837 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 3839 */         out.write("\n\tNA&nbsp;\n\t");
/* 3840 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3841 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3845 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3846 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3847 */       return true;
/*      */     }
/* 3849 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3850 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\JBossEJBDetailsUserArea_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */