/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.AlarmGraph;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
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
/*      */ import org.apache.taglibs.standard.tag.el.core.ImportTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class Alarm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   44 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   47 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   48 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   49 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   56 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   61 */     ArrayList list = null;
/*   62 */     StringBuffer sbf = new StringBuffer();
/*   63 */     ManagedApplication mo = new ManagedApplication();
/*   64 */     if (distinct)
/*      */     {
/*   66 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   70 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   73 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   75 */       ArrayList row = (ArrayList)list.get(i);
/*   76 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   77 */       if (distinct) {
/*   78 */         sbf.append(row.get(0));
/*      */       } else
/*   80 */         sbf.append(row.get(1));
/*   81 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   84 */     return sbf.toString(); }
/*      */   
/*   86 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   89 */     if (severity == null)
/*      */     {
/*   91 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   93 */     if (severity.equals("5"))
/*      */     {
/*   95 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   97 */     if (severity.equals("1"))
/*      */     {
/*   99 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  104 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  111 */     if (severity == null)
/*      */     {
/*  113 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  115 */     if (severity.equals("1"))
/*      */     {
/*  117 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  119 */     if (severity.equals("4"))
/*      */     {
/*  121 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  123 */     if (severity.equals("5"))
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  130 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  136 */     if (severity == null)
/*      */     {
/*  138 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  140 */     if (severity.equals("5"))
/*      */     {
/*  142 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  144 */     if (severity.equals("1"))
/*      */     {
/*  146 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  150 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  156 */     if (severity == null)
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  160 */     if (severity.equals("1"))
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  164 */     if (severity.equals("4"))
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  168 */     if (severity.equals("5"))
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  174 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  180 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  186 */     if (severity == 5)
/*      */     {
/*  188 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  190 */     if (severity == 1)
/*      */     {
/*  192 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  197 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  203 */     if (severity == null)
/*      */     {
/*  205 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  207 */     if (severity.equals("5"))
/*      */     {
/*  209 */       if (isAvailability) {
/*  210 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  213 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  216 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  218 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  220 */     if (severity.equals("1"))
/*      */     {
/*  222 */       if (isAvailability) {
/*  223 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  226 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  233 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  240 */     if (severity == null)
/*      */     {
/*  242 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  244 */     if (severity.equals("5"))
/*      */     {
/*  246 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  248 */     if (severity.equals("4"))
/*      */     {
/*  250 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  252 */     if (severity.equals("1"))
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  259 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  265 */     if (severity == null)
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  269 */     if (severity.equals("5"))
/*      */     {
/*  271 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  273 */     if (severity.equals("4"))
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  277 */     if (severity.equals("1"))
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  284 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  291 */     if (severity == null)
/*      */     {
/*  293 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  295 */     if (severity.equals("5"))
/*      */     {
/*  297 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  299 */     if (severity.equals("4"))
/*      */     {
/*  301 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  303 */     if (severity.equals("1"))
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  310 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  318 */     StringBuffer out = new StringBuffer();
/*  319 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  320 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  321 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  322 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  323 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  324 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  325 */     out.append("</tr>");
/*  326 */     out.append("</form></table>");
/*  327 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  334 */     if (val == null)
/*      */     {
/*  336 */       return "-";
/*      */     }
/*      */     
/*  339 */     String ret = FormatUtil.formatNumber(val);
/*  340 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  341 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  344 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  348 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  356 */     StringBuffer out = new StringBuffer();
/*  357 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  358 */     out.append("<tr>");
/*  359 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  361 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  363 */     out.append("</tr>");
/*  364 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  368 */       if (j % 2 == 0)
/*      */       {
/*  370 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  374 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  377 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  379 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  382 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  386 */       out.append("</tr>");
/*      */     }
/*  388 */     out.append("</table>");
/*  389 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  390 */     out.append("<tr>");
/*  391 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  392 */     out.append("</tr>");
/*  393 */     out.append("</table>");
/*  394 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  400 */     StringBuffer out = new StringBuffer();
/*  401 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  402 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  403 */     out.append("<tr>");
/*  404 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  405 */     out.append("<tr>");
/*  406 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  407 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  408 */     out.append("</tr>");
/*  409 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  412 */       out.append("<tr>");
/*  413 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  414 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  415 */       out.append("</tr>");
/*      */     }
/*      */     
/*  418 */     out.append("</table>");
/*  419 */     out.append("</table>");
/*  420 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  425 */     if (severity.equals("0"))
/*      */     {
/*  427 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  431 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  438 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  451 */     StringBuffer out = new StringBuffer();
/*  452 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  453 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  455 */       out.append("<tr>");
/*  456 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  457 */       out.append("</tr>");
/*      */       
/*      */ 
/*  460 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  462 */         String borderclass = "";
/*      */         
/*      */ 
/*  465 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  467 */         out.append("<tr>");
/*      */         
/*  469 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  470 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  471 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  477 */     out.append("</table><br>");
/*  478 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  479 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  481 */       List sLinks = secondLevelOfLinks[0];
/*  482 */       List sText = secondLevelOfLinks[1];
/*  483 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  486 */         out.append("<tr>");
/*  487 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  488 */         out.append("</tr>");
/*  489 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  491 */           String borderclass = "";
/*      */           
/*      */ 
/*  494 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  496 */           out.append("<tr>");
/*      */           
/*  498 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  499 */           if (sLinks.get(i).toString().length() == 0) {
/*  500 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  503 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  505 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  509 */     out.append("</table>");
/*  510 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  517 */     StringBuffer out = new StringBuffer();
/*  518 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  519 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  521 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  523 */         out.append("<tr>");
/*  524 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  525 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  529 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  531 */           String borderclass = "";
/*      */           
/*      */ 
/*  534 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  536 */           out.append("<tr>");
/*      */           
/*  538 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  539 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  540 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  543 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  546 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  551 */     out.append("</table><br>");
/*  552 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  553 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  555 */       List sLinks = secondLevelOfLinks[0];
/*  556 */       List sText = secondLevelOfLinks[1];
/*  557 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  560 */         out.append("<tr>");
/*  561 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  562 */         out.append("</tr>");
/*  563 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  565 */           String borderclass = "";
/*      */           
/*      */ 
/*  568 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  570 */           out.append("<tr>");
/*      */           
/*  572 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  573 */           if (sLinks.get(i).toString().length() == 0) {
/*  574 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  577 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  579 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  583 */     out.append("</table>");
/*  584 */     return out.toString();
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
/*  597 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  600 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  603 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  606 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  612 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  615 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  618 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  626 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  631 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  636 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  641 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  646 */     if (val != null)
/*      */     {
/*  648 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  652 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  657 */     if (val == null) {
/*  658 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  662 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  667 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  673 */     if (val != null)
/*      */     {
/*  675 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  679 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  685 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  690 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  694 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  699 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  704 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  709 */     String hostaddress = "";
/*  710 */     String ip = request.getHeader("x-forwarded-for");
/*  711 */     if (ip == null)
/*  712 */       ip = request.getRemoteAddr();
/*  713 */     InetAddress add = null;
/*  714 */     if (ip.equals("127.0.0.1")) {
/*  715 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  719 */       add = InetAddress.getByName(ip);
/*      */     }
/*  721 */     hostaddress = add.getHostName();
/*  722 */     if (hostaddress.indexOf('.') != -1) {
/*  723 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  724 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  728 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  733 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  739 */     if (severity == null)
/*      */     {
/*  741 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  743 */     if (severity.equals("5"))
/*      */     {
/*  745 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  747 */     if (severity.equals("1"))
/*      */     {
/*  749 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  754 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  759 */     ResultSet set = null;
/*  760 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  761 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  763 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  764 */       if (set.next()) { String str1;
/*  765 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  766 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  769 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  774 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  777 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  779 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  783 */     StringBuffer rca = new StringBuffer();
/*  784 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  785 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  788 */     int rcalength = key.length();
/*  789 */     String split = "6. ";
/*  790 */     int splitPresent = key.indexOf(split);
/*  791 */     String div1 = "";String div2 = "";
/*  792 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  794 */       if (rcalength > 180) {
/*  795 */         rca.append("<span class=\"rca-critical-text\">");
/*  796 */         getRCATrimmedText(key, rca);
/*  797 */         rca.append("</span>");
/*      */       } else {
/*  799 */         rca.append("<span class=\"rca-critical-text\">");
/*  800 */         rca.append(key);
/*  801 */         rca.append("</span>");
/*      */       }
/*  803 */       return rca.toString();
/*      */     }
/*  805 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  806 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  807 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  808 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  809 */     getRCATrimmedText(div1, rca);
/*  810 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  813 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  814 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  815 */     getRCATrimmedText(div2, rca);
/*  816 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  818 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  823 */     String[] st = msg.split("<br>");
/*  824 */     for (int i = 0; i < st.length; i++) {
/*  825 */       String s = st[i];
/*  826 */       if (s.length() > 180) {
/*  827 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  829 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  833 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  834 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  836 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  840 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  841 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  842 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  845 */       if (key == null) {
/*  846 */         return ret;
/*      */       }
/*      */       
/*  849 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  850 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  853 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  854 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  855 */       set = AMConnectionPool.executeQueryStmt(query);
/*  856 */       if (set.next())
/*      */       {
/*  858 */         String helpLink = set.getString("LINK");
/*  859 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  862 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  868 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  887 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  878 */         if (set != null) {
/*  879 */           AMConnectionPool.closeStatement(set);
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
/*  893 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  894 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  896 */       String entityStr = (String)keys.nextElement();
/*  897 */       String mmessage = temp.getProperty(entityStr);
/*  898 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  899 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  901 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  907 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  908 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  910 */       String entityStr = (String)keys.nextElement();
/*  911 */       String mmessage = temp.getProperty(entityStr);
/*  912 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  913 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  915 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  920 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  930 */     String des = new String();
/*  931 */     while (str.indexOf(find) != -1) {
/*  932 */       des = des + str.substring(0, str.indexOf(find));
/*  933 */       des = des + replace;
/*  934 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  936 */     des = des + str;
/*  937 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  944 */       if (alert == null)
/*      */       {
/*  946 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  948 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  950 */         return "&nbsp;";
/*      */       }
/*      */       
/*  953 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  955 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  958 */       int rcalength = test.length();
/*  959 */       if (rcalength < 300)
/*      */       {
/*  961 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  965 */       StringBuffer out = new StringBuffer();
/*  966 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  967 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  968 */       out.append("</div>");
/*  969 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  970 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  971 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  976 */       ex.printStackTrace();
/*      */     }
/*  978 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  984 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  989 */     ArrayList attribIDs = new ArrayList();
/*  990 */     ArrayList resIDs = new ArrayList();
/*  991 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  993 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  995 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  997 */       String resourceid = "";
/*  998 */       String resourceType = "";
/*  999 */       if (type == 2) {
/* 1000 */         resourceid = (String)row.get(0);
/* 1001 */         resourceType = (String)row.get(3);
/*      */       }
/* 1003 */       else if (type == 3) {
/* 1004 */         resourceid = (String)row.get(0);
/* 1005 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1008 */         resourceid = (String)row.get(6);
/* 1009 */         resourceType = (String)row.get(7);
/*      */       }
/* 1011 */       resIDs.add(resourceid);
/* 1012 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1013 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1015 */       String healthentity = null;
/* 1016 */       String availentity = null;
/* 1017 */       if (healthid != null) {
/* 1018 */         healthentity = resourceid + "_" + healthid;
/* 1019 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1022 */       if (availid != null) {
/* 1023 */         availentity = resourceid + "_" + availid;
/* 1024 */         entitylist.add(availentity);
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
/* 1038 */     Properties alert = getStatus(entitylist);
/* 1039 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1044 */     int size = monitorList.size();
/*      */     
/* 1046 */     String[] severity = new String[size];
/*      */     
/* 1048 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1050 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1051 */       String resourceName1 = (String)row1.get(7);
/* 1052 */       String resourceid1 = (String)row1.get(6);
/* 1053 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1054 */       if (severity[j] == null)
/*      */       {
/* 1056 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1060 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1062 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1064 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1067 */         if (sev > 0) {
/* 1068 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1069 */           monitorList.set(k, monitorList.get(j));
/* 1070 */           monitorList.set(j, t);
/* 1071 */           String temp = severity[k];
/* 1072 */           severity[k] = severity[j];
/* 1073 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1079 */     int z = 0;
/* 1080 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1083 */       int i = 0;
/* 1084 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1087 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1091 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1095 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1097 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1100 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1104 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1107 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1108 */       String resourceName1 = (String)row1.get(7);
/* 1109 */       String resourceid1 = (String)row1.get(6);
/* 1110 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1111 */       if (hseverity[j] == null)
/*      */       {
/* 1113 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1118 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1120 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1123 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1126 */         if (hsev > 0) {
/* 1127 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1128 */           monitorList.set(k, monitorList.get(j));
/* 1129 */           monitorList.set(j, t);
/* 1130 */           String temp1 = hseverity[k];
/* 1131 */           hseverity[k] = hseverity[j];
/* 1132 */           hseverity[j] = temp1;
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
/* 1144 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1145 */     boolean forInventory = false;
/* 1146 */     String trdisplay = "none";
/* 1147 */     String plusstyle = "inline";
/* 1148 */     String minusstyle = "none";
/* 1149 */     String haidTopLevel = "";
/* 1150 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1152 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1154 */         haidTopLevel = request.getParameter("haid");
/* 1155 */         forInventory = true;
/* 1156 */         trdisplay = "table-row;";
/* 1157 */         plusstyle = "none";
/* 1158 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1165 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1168 */     ArrayList listtoreturn = new ArrayList();
/* 1169 */     StringBuffer toreturn = new StringBuffer();
/* 1170 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1171 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1172 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1174 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1176 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1177 */       String childresid = (String)singlerow.get(0);
/* 1178 */       String childresname = (String)singlerow.get(1);
/* 1179 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1180 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1181 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1182 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1183 */       String unmanagestatus = (String)singlerow.get(5);
/* 1184 */       String actionstatus = (String)singlerow.get(6);
/* 1185 */       String linkclass = "monitorgp-links";
/* 1186 */       String titleforres = childresname;
/* 1187 */       String titilechildresname = childresname;
/* 1188 */       String childimg = "/images/trcont.png";
/* 1189 */       String flag = "enable";
/* 1190 */       String dcstarted = (String)singlerow.get(8);
/* 1191 */       String configMonitor = "";
/* 1192 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1193 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1195 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1197 */       if (singlerow.get(7) != null)
/*      */       {
/* 1199 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1201 */       String haiGroupType = "0";
/* 1202 */       if ("HAI".equals(childtype))
/*      */       {
/* 1204 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1206 */       childimg = "/images/trend.png";
/* 1207 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1208 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1209 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1211 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1213 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1215 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1216 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1219 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1221 */         linkclass = "disabledtext";
/* 1222 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1224 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1225 */       String availmouseover = "";
/* 1226 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1228 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1230 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1231 */       String healthmouseover = "";
/* 1232 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1234 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1237 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1238 */       int spacing = 0;
/* 1239 */       if (level >= 1)
/*      */       {
/* 1241 */         spacing = 40 * level;
/*      */       }
/* 1243 */       if (childtype.equals("HAI"))
/*      */       {
/* 1245 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1246 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1247 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1249 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1250 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1251 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1252 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1253 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1254 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1255 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1256 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1257 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1258 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1259 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1261 */         if (!forInventory)
/*      */         {
/* 1263 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1266 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1268 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1270 */           actions = editlink + actions;
/*      */         }
/* 1272 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1274 */           actions = actions + associatelink;
/*      */         }
/* 1276 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1277 */         String arrowimg = "";
/* 1278 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1280 */           actions = "";
/* 1281 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1282 */           checkbox = "";
/* 1283 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1285 */         if (isIt360)
/*      */         {
/* 1287 */           actionimg = "";
/* 1288 */           actions = "";
/* 1289 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1290 */           checkbox = "";
/*      */         }
/*      */         
/* 1293 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1295 */           actions = "";
/*      */         }
/* 1297 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1299 */           checkbox = "";
/*      */         }
/*      */         
/* 1302 */         String resourcelink = "";
/*      */         
/* 1304 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1306 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1310 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1313 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1314 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1315 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1319 */         if (!isIt360)
/*      */         {
/* 1321 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1325 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1328 */         toreturn.append("</tr>");
/* 1329 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1331 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1332 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1336 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1337 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1340 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1344 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1346 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1347 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1348 */             toreturn.append(assocMessage);
/* 1349 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1350 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1351 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1352 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1358 */         String resourcelink = null;
/* 1359 */         boolean hideEditLink = false;
/* 1360 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1362 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1363 */           hideEditLink = true;
/* 1364 */           if (isIt360)
/*      */           {
/* 1366 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1370 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1372 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1374 */           hideEditLink = true;
/* 1375 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1376 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1381 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1384 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1385 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1386 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1387 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1388 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1389 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1390 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1391 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1392 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1393 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1394 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1395 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1396 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1398 */         if (hideEditLink)
/*      */         {
/* 1400 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1402 */         if (!forInventory)
/*      */         {
/* 1404 */           removefromgroup = "";
/*      */         }
/* 1406 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1407 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1408 */           actions = actions + configcustomfields;
/*      */         }
/* 1410 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1412 */           actions = editlink + actions;
/*      */         }
/* 1414 */         String managedLink = "";
/* 1415 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1417 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1418 */           actions = "";
/* 1419 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1420 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1423 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1425 */           checkbox = "";
/*      */         }
/*      */         
/* 1428 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1430 */           actions = "";
/*      */         }
/* 1432 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1433 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1434 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1435 */         if (isIt360)
/*      */         {
/* 1437 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1441 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1445 */         if (!isIt360)
/*      */         {
/* 1447 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1453 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1456 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1463 */       StringBuilder toreturn = new StringBuilder();
/* 1464 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1465 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1466 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1467 */       String title = "";
/* 1468 */       message = EnterpriseUtil.decodeString(message);
/* 1469 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1470 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1471 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1473 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1475 */       else if ("5".equals(severity))
/*      */       {
/* 1477 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1481 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1483 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1484 */       toreturn.append(v);
/*      */       
/* 1486 */       toreturn.append(link);
/* 1487 */       if (severity == null)
/*      */       {
/* 1489 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1491 */       else if (severity.equals("5"))
/*      */       {
/* 1493 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1495 */       else if (severity.equals("4"))
/*      */       {
/* 1497 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1499 */       else if (severity.equals("1"))
/*      */       {
/* 1501 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       toreturn.append("</a>");
/* 1509 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1513 */       ex.printStackTrace();
/*      */     }
/* 1515 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1522 */       StringBuilder toreturn = new StringBuilder();
/* 1523 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1524 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1525 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1526 */       if (message == null)
/*      */       {
/* 1528 */         message = "";
/*      */       }
/*      */       
/* 1531 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1532 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1534 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1535 */       toreturn.append(v);
/*      */       
/* 1537 */       toreturn.append(link);
/*      */       
/* 1539 */       if (severity == null)
/*      */       {
/* 1541 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1543 */       else if (severity.equals("5"))
/*      */       {
/* 1545 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1547 */       else if (severity.equals("1"))
/*      */       {
/* 1549 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1556 */       toreturn.append("</a>");
/* 1557 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1563 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1566 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1567 */     if (invokeActions != null) {
/* 1568 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1569 */       while (iterator.hasNext()) {
/* 1570 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1571 */         if (actionmap.containsKey(actionid)) {
/* 1572 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1577 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1581 */     String actionLink = "";
/* 1582 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1583 */     String query = "";
/* 1584 */     ResultSet rs = null;
/* 1585 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1586 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1587 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1588 */       actionLink = "method=" + methodName;
/*      */     }
/* 1590 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1591 */       actionLink = methodName;
/*      */     }
/* 1593 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1594 */     Iterator itr = methodarglist.iterator();
/* 1595 */     boolean isfirstparam = true;
/* 1596 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1597 */     while (itr.hasNext()) {
/* 1598 */       HashMap argmap = (HashMap)itr.next();
/* 1599 */       String argtype = (String)argmap.get("TYPE");
/* 1600 */       String argname = (String)argmap.get("IDENTITY");
/* 1601 */       String paramname = (String)argmap.get("PARAMETER");
/* 1602 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1603 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1604 */         isfirstparam = false;
/* 1605 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1607 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1611 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1615 */         actionLink = actionLink + "&";
/*      */       }
/* 1617 */       String paramValue = null;
/* 1618 */       String tempargname = argname;
/* 1619 */       if (commonValues.getProperty(tempargname) != null) {
/* 1620 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1623 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1624 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1625 */           if (dbType.equals("mysql")) {
/* 1626 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1629 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1631 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1633 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1634 */             if (rs.next()) {
/* 1635 */               paramValue = rs.getString("VALUE");
/* 1636 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1640 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1644 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1647 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1652 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1653 */           paramValue = rowId;
/*      */         }
/* 1655 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1656 */           paramValue = managedObjectName;
/*      */         }
/* 1658 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1659 */           paramValue = resID;
/*      */         }
/* 1661 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1662 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1665 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1667 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1668 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1669 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1671 */     return actionLink;
/*      */   }
/*      */   
/* 1674 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1675 */     String dependentAttribute = null;
/* 1676 */     String align = "left";
/*      */     
/* 1678 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1679 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1680 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1681 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1682 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1683 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1684 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1685 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1686 */       align = "center";
/*      */     }
/*      */     
/* 1689 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1690 */     String actualdata = "";
/*      */     
/* 1692 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1693 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1694 */         actualdata = availValue;
/*      */       }
/* 1696 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1697 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1701 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1702 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1705 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1711 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1712 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1713 */       toreturn.append("<table>");
/* 1714 */       toreturn.append("<tr>");
/* 1715 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1716 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1717 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1718 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1719 */         String toolTip = "";
/* 1720 */         String hideClass = "";
/* 1721 */         String textStyle = "";
/* 1722 */         boolean isreferenced = true;
/* 1723 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1724 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1725 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1726 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1728 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1729 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1730 */           while (valueList.hasMoreTokens()) {
/* 1731 */             String dependentVal = valueList.nextToken();
/* 1732 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1733 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1734 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1736 */               toolTip = "";
/* 1737 */               hideClass = "";
/* 1738 */               isreferenced = false;
/* 1739 */               textStyle = "disabledtext";
/* 1740 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1744 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1745 */           toolTip = "";
/* 1746 */           hideClass = "";
/* 1747 */           isreferenced = false;
/* 1748 */           textStyle = "disabledtext";
/* 1749 */           if (dependentImageMap != null) {
/* 1750 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1751 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1754 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1758 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1759 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1760 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1761 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1762 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1763 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1765 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1766 */           if (isreferenced) {
/* 1767 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1771 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1772 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1773 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1774 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1775 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1776 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1778 */           toreturn.append("</span>");
/* 1779 */           toreturn.append("</a>");
/* 1780 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1783 */       toreturn.append("</tr>");
/* 1784 */       toreturn.append("</table>");
/* 1785 */       toreturn.append("</td>");
/*      */     } else {
/* 1787 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1790 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1794 */     String colTime = null;
/* 1795 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1796 */     if ((rows != null) && (rows.size() > 0)) {
/* 1797 */       Iterator<String> itr = rows.iterator();
/* 1798 */       String maxColQuery = "";
/* 1799 */       for (;;) { if (itr.hasNext()) {
/* 1800 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1801 */           ResultSet maxCol = null;
/*      */           try {
/* 1803 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1804 */             while (maxCol.next()) {
/* 1805 */               if (colTime == null) {
/* 1806 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1809 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1818 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1820 */               if (maxCol != null)
/* 1821 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1823 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1818 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1820 */               if (maxCol != null)
/* 1821 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1823 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1828 */     return colTime;
/*      */   }
/*      */   
/* 1831 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1832 */     tablename = null;
/* 1833 */     ResultSet rsTable = null;
/* 1834 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1836 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1837 */       while (rsTable.next()) {
/* 1838 */         tablename = rsTable.getString("DATATABLE");
/* 1839 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1840 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1853 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1844 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1847 */         if (rsTable != null)
/* 1848 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1850 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1856 */     String argsList = "";
/* 1857 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1859 */       if (showArgsMap.get(row) != null) {
/* 1860 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1861 */         if (showArgslist != null) {
/* 1862 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1863 */             if (argsList.trim().equals("")) {
/* 1864 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1867 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1874 */       e.printStackTrace();
/* 1875 */       return "";
/*      */     }
/* 1877 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1882 */     String argsList = "";
/* 1883 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1886 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1888 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1889 */         if (hideArgsList != null)
/*      */         {
/* 1891 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1893 */             if (argsList.trim().equals(""))
/*      */             {
/* 1895 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1899 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1907 */       ex.printStackTrace();
/*      */     }
/* 1909 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1913 */     StringBuilder toreturn = new StringBuilder();
/* 1914 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1921 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1922 */       Iterator itr = tActionList.iterator();
/* 1923 */       while (itr.hasNext()) {
/* 1924 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1925 */         String confirmmsg = "";
/* 1926 */         String link = "";
/* 1927 */         String isJSP = "NO";
/* 1928 */         HashMap tactionMap = (HashMap)itr.next();
/* 1929 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1930 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1931 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1932 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1933 */           (actionmap.containsKey(actionId))) {
/* 1934 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1935 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1936 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1937 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1938 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1940 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1946 */           if (isTableAction) {
/* 1947 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1950 */             tableName = "Link";
/* 1951 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1952 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1953 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1954 */             toreturn.append("</a></td>");
/*      */           }
/* 1956 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1957 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1958 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1965 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1971 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1973 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1974 */       Properties prop = (Properties)node.getUserObject();
/* 1975 */       String mgID = prop.getProperty("label");
/* 1976 */       String mgName = prop.getProperty("value");
/* 1977 */       String isParent = prop.getProperty("isParent");
/* 1978 */       int mgIDint = Integer.parseInt(mgID);
/* 1979 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1981 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1983 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1984 */       if (node.getChildCount() > 0)
/*      */       {
/* 1986 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1988 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1990 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1992 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1996 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2001 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2003 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2005 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2007 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2011 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2014 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2015 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2017 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2021 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2023 */       if (node.getChildCount() > 0)
/*      */       {
/* 2025 */         builder.append("<UL>");
/* 2026 */         printMGTree(node, builder);
/* 2027 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2032 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2033 */     StringBuffer toReturn = new StringBuffer();
/* 2034 */     String table = "-";
/*      */     try {
/* 2036 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2037 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2038 */       float total = 0.0F;
/* 2039 */       while (it.hasNext()) {
/* 2040 */         String attName = (String)it.next();
/* 2041 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2042 */         boolean roundOffData = false;
/* 2043 */         if ((data != null) && (!data.equals(""))) {
/* 2044 */           if (data.indexOf(",") != -1) {
/* 2045 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2048 */             float value = Float.parseFloat(data);
/* 2049 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2052 */             total += value;
/* 2053 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2056 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2061 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2062 */       while (attVsWidthList.hasNext()) {
/* 2063 */         String attName = (String)attVsWidthList.next();
/* 2064 */         String data = (String)attVsWidthProps.get(attName);
/* 2065 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2066 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2067 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2068 */         String className = (String)graphDetails.get("ClassName");
/* 2069 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2070 */         if (percentage < 1.0F)
/*      */         {
/* 2072 */           data = percentage + "";
/*      */         }
/* 2074 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2076 */       if (toReturn.length() > 0) {
/* 2077 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2081 */       e.printStackTrace();
/*      */     }
/* 2083 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2089 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2090 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2091 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2092 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2093 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2094 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2095 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2096 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2097 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2100 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2101 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2102 */       splitvalues[0] = multiplecondition.toString();
/* 2103 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2106 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2111 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2112 */     if (thresholdType != 3) {
/* 2113 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2114 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2115 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2116 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2117 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2118 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2120 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2121 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2122 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2123 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2124 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2125 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2127 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2128 */     if (updateSelected != null) {
/* 2129 */       updateSelected[0] = "selected";
/*      */     }
/* 2131 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2136 */       StringBuffer toreturn = new StringBuffer("");
/* 2137 */       if (commaSeparatedMsgId != null) {
/* 2138 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2139 */         int count = 0;
/* 2140 */         while (msgids.hasMoreTokens()) {
/* 2141 */           String id = msgids.nextToken();
/* 2142 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2143 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2144 */           count++;
/* 2145 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2146 */             if (toreturn.length() == 0) {
/* 2147 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2149 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2150 */             if (!image.trim().equals("")) {
/* 2151 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2153 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2154 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2157 */         if (toreturn.length() > 0) {
/* 2158 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2162 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2165 */       e.printStackTrace(); }
/* 2166 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2172 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2178 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2179 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2180 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2194 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2198 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2199 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2200 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2201 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2202 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2203 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2204 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2205 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2209 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2211 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005fheight_005fdataSetProducer.release();
/* 2212 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2213 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2221 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2224 */     JspWriter out = null;
/* 2225 */     Object page = this;
/* 2226 */     JspWriter _jspx_out = null;
/* 2227 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2231 */       response.setContentType("text/html;charset=UTF-8");
/* 2232 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2234 */       _jspx_page_context = pageContext;
/* 2235 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2236 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2237 */       session = pageContext.getSession();
/* 2238 */       out = pageContext.getOut();
/* 2239 */       _jspx_out = out;
/*      */       
/* 2241 */       out.write(10);
/* 2242 */       out.write(10);
/* 2243 */       out.write(10);
/* 2244 */       AlarmGraph alarmgraph = null;
/* 2245 */       alarmgraph = (AlarmGraph)_jspx_page_context.getAttribute("alarmgraph", 2);
/* 2246 */       if (alarmgraph == null) {
/* 2247 */         alarmgraph = new AlarmGraph();
/* 2248 */         _jspx_page_context.setAttribute("alarmgraph", alarmgraph, 2);
/*      */       }
/* 2250 */       out.write(10);
/* 2251 */       out.write(10);
/* 2252 */       out.write(10);
/* 2253 */       ManagedApplication mo = null;
/* 2254 */       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2255 */       if (mo == null) {
/* 2256 */         mo = new ManagedApplication();
/* 2257 */         _jspx_page_context.setAttribute("mo", mo, 1);
/*      */       }
/* 2259 */       out.write("\n\n\n\n\n\n\n<head>\n\n\n");
/* 2260 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2262 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2263 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2264 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2266 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2268 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2270 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2272 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2273 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2274 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2275 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2278 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2279 */         String available = null;
/* 2280 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2281 */         out.write(10);
/*      */         
/* 2283 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2284 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2285 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2287 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2289 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2291 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2293 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2294 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2295 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2296 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2299 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2300 */           String unavailable = null;
/* 2301 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2302 */           out.write(10);
/*      */           
/* 2304 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2305 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2306 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2308 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2310 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2312 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2314 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2315 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2316 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2317 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2320 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2321 */             String unmanaged = null;
/* 2322 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2323 */             out.write(10);
/*      */             
/* 2325 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2326 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2327 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2329 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2331 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2333 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2335 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2336 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2337 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2338 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2341 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2342 */               String scheduled = null;
/* 2343 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2344 */               out.write(10);
/*      */               
/* 2346 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2347 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2348 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2350 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2352 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2354 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2356 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2357 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2358 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2359 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2362 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2363 */                 String critical = null;
/* 2364 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2365 */                 out.write(10);
/*      */                 
/* 2367 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2368 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2369 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2371 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2373 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2375 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2377 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2378 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2379 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2380 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2383 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2384 */                   String clear = null;
/* 2385 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2386 */                   out.write(10);
/*      */                   
/* 2388 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2389 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2390 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2392 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2394 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2396 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2398 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2399 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2400 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2401 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2404 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2405 */                     String warning = null;
/* 2406 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2407 */                     out.write(10);
/* 2408 */                     out.write(10);
/*      */                     
/* 2410 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2411 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2413 */                     out.write(10);
/* 2414 */                     out.write(10);
/* 2415 */                     out.write(10);
/* 2416 */                     out.write(10);
/* 2417 */                     out.write(10);
/* 2418 */                     out.write(10);
/* 2419 */                     out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2420 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2422 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2423 */                     out.write(10);
/* 2424 */                     out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/validation.js\"></SCRIPT>\n<script>\n\nfunction open(link){\n\t\twindow.opener.location.href=link;\n\t}\n</script>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"><title>");
/* 2425 */                     out.print(FormatUtil.getString("am.webclient.toolbar.alertsummarylink.text"));
/* 2426 */                     out.write("</title></head>\n\n\n\n\n\n\n\n\n\n\n<body >\n\n");
/*      */                     
/* 2428 */                     ArrayList list = mo.getRows("select count(*) from Alert");
/* 2429 */                     if (list.size() > 0)
/*      */                     {
/* 2431 */                       list = (ArrayList)list.get(0);
/* 2432 */                       String count = (String)list.get(0);
/* 2433 */                       if (count.equals("0"))
/*      */                       {
/*      */ 
/* 2436 */                         out.write("\n\n\n\n\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td class=\"tableheadingbborder\">\n");
/* 2437 */                         out.print(FormatUtil.getString("am.webclient.toolbar.alertsummarylink.text"));
/* 2438 */                         out.write("\n</td>\n</tr>\n  <tr>\n    <td height=\"63\" align=\"center\" class=\"bodytext\">&nbsp;&nbsp;");
/* 2439 */                         out.print(FormatUtil.getString("am.webclient.alerttab.nodatamessage.text"));
/* 2440 */                         out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"tablebottom\" align=\"center\"><input type=\"submit\" name=\"Close\" value=\"");
/* 2441 */                         out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 2442 */                         out.write("\" onClick=\"window.close()\" class=\"buttons btn_link\" ></td>\n  </tr>\n</table>\n</body>\n</html>\n\n"); return;
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 2447 */                     if (request.isUserInRole("OPERATOR"))
/*      */                     {
/* 2449 */                       alarmgraph.setUsername(request.getRemoteUser());
/*      */                     }
/*      */                     
/* 2452 */                     out.write("\n\n\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n<tbody><tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/* 2453 */                     out.print(FormatUtil.getString("am.webclient.toolbar.alertsummarylink.text"));
/* 2454 */                     out.write("\n</span>\n</td>\n</tr>\n</tbody></table>\n\n\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\" align=\"center\" style=\"margin-top:10px;\">\n<tr>\n    <td width=\"72%\" height=\"26\" class=\"tableheading\">");
/* 2455 */                     out.print(FormatUtil.getString("am.webclient.toolbar.alertsummarylink.text"));
/* 2456 */                     out.write("</td>\n  </tr>\n<tr>\n<td width=\"60%\"  align=\"center\" height=\"150\">\n\n            ");
/*      */                     
/* 2458 */                     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005fheight_005fdataSetProducer.get(AMWolf.class);
/* 2459 */                     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 2460 */                     _jspx_th_awolf_005fpiechart_005f0.setParent(null);
/*      */                     
/* 2462 */                     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("alarmgraph");
/*      */                     
/* 2464 */                     _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */                     
/* 2466 */                     _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */                     
/* 2468 */                     _jspx_th_awolf_005fpiechart_005f0.setUnits(" ");
/*      */                     
/* 2470 */                     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/* 2471 */                     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 2472 */                     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 2473 */                       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2474 */                         out = _jspx_page_context.pushBody();
/* 2475 */                         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 2476 */                         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/* 2479 */                         out.write("\n\t\t\t");
/*      */                         
/* 2481 */                         Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 2482 */                         _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 2483 */                         _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                         
/* 2485 */                         _jspx_th_awolf_005fmap_005f0.setId("color");
/* 2486 */                         int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 2487 */                         if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 2488 */                           if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2489 */                             out = _jspx_page_context.pushBody();
/* 2490 */                             _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 2491 */                             _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2494 */                             out.write("\n\t\t\t\t");
/*      */                             
/* 2496 */                             AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2497 */                             _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 2498 */                             _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                             
/* 2500 */                             _jspx_th_awolf_005fparam_005f0.setName("0");
/*      */                             
/* 2502 */                             _jspx_th_awolf_005fparam_005f0.setValue(critical);
/* 2503 */                             int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 2504 */                             if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 2505 */                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                             }
/*      */                             
/* 2508 */                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 2509 */                             out.write("\n\t\t\t\t");
/*      */                             
/* 2511 */                             AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2512 */                             _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 2513 */                             _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                             
/* 2515 */                             _jspx_th_awolf_005fparam_005f1.setName("1");
/*      */                             
/* 2517 */                             _jspx_th_awolf_005fparam_005f1.setValue(warning);
/* 2518 */                             int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 2519 */                             if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 2520 */                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                             }
/*      */                             
/* 2523 */                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 2524 */                             out.write("\n\t\t\t\t");
/*      */                             
/* 2526 */                             AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2527 */                             _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 2528 */                             _jspx_th_awolf_005fparam_005f2.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                             
/* 2530 */                             _jspx_th_awolf_005fparam_005f2.setName("2");
/*      */                             
/* 2532 */                             _jspx_th_awolf_005fparam_005f2.setValue(clear);
/* 2533 */                             int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 2534 */                             if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 2535 */                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2); return;
/*      */                             }
/*      */                             
/* 2538 */                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 2539 */                             out.write("\n\t\t\t");
/* 2540 */                             int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 2541 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2544 */                           if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2545 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2548 */                         if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 2549 */                           this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                         }
/*      */                         
/* 2552 */                         this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 2553 */                         out.write("\n\t\t\t");
/* 2554 */                         if (_jspx_meth_awolf_005fmap_005f1(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/*      */                           return;
/* 2556 */                         out.write(10);
/* 2557 */                         out.write(9);
/* 2558 */                         out.write(9);
/* 2559 */                         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 2560 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2563 */                       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2564 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2567 */                     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 2568 */                       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/*      */                     }
/*      */                     else {
/* 2571 */                       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 2572 */                       out.write("\n\n</td>\n    <!--td width=\"100%\" > <img src=\"../images/img_alertsbottom.png\">\n    </td-->\n</tr>\n\n\n\n</table>\n<br>\n\n\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"center\">\n<tr>\n<td>\n\n\n\n");
/* 2573 */                       if (_jspx_meth_c_005fimport_005f0(_jspx_page_context))
/*      */                         return;
/* 2575 */                       out.write("\n\n</td>\n</tr>\n\n</table>\n\n\n</body>\n</html>\n");
/*      */                     }
/* 2577 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2578 */         out = _jspx_out;
/* 2579 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2580 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2581 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2584 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2590 */     PageContext pageContext = _jspx_page_context;
/* 2591 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2593 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2594 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2595 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2597 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2599 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2600 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2601 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2602 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2603 */       return true;
/*      */     }
/* 2605 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2606 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f1(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2611 */     PageContext pageContext = _jspx_page_context;
/* 2612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2614 */     Property _jspx_th_awolf_005fmap_005f1 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 2615 */     _jspx_th_awolf_005fmap_005f1.setPageContext(_jspx_page_context);
/* 2616 */     _jspx_th_awolf_005fmap_005f1.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 2618 */     _jspx_th_awolf_005fmap_005f1.setId("url");
/* 2619 */     int _jspx_eval_awolf_005fmap_005f1 = _jspx_th_awolf_005fmap_005f1.doStartTag();
/* 2620 */     if (_jspx_eval_awolf_005fmap_005f1 != 0) {
/* 2621 */       if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 2622 */         out = _jspx_page_context.pushBody();
/* 2623 */         _jspx_th_awolf_005fmap_005f1.setBodyContent((BodyContent)out);
/* 2624 */         _jspx_th_awolf_005fmap_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2627 */         out.write("\n\t\t\t\t");
/* 2628 */         if (_jspx_meth_awolf_005fparam_005f3(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 2629 */           return true;
/* 2630 */         out.write("\n\t\t\t\t");
/* 2631 */         if (_jspx_meth_awolf_005fparam_005f4(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 2632 */           return true;
/* 2633 */         out.write("\n\t\t\t\t");
/* 2634 */         if (_jspx_meth_awolf_005fparam_005f5(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 2635 */           return true;
/* 2636 */         out.write("\n\t\t\t");
/* 2637 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f1.doAfterBody();
/* 2638 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2641 */       if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 2642 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2645 */     if (_jspx_th_awolf_005fmap_005f1.doEndTag() == 5) {
/* 2646 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 2647 */       return true;
/*      */     }
/* 2649 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 2650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f3(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2655 */     PageContext pageContext = _jspx_page_context;
/* 2656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2658 */     AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2659 */     _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 2660 */     _jspx_th_awolf_005fparam_005f3.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 2662 */     _jspx_th_awolf_005fparam_005f3.setName("Critical");
/*      */     
/* 2664 */     _jspx_th_awolf_005fparam_005f3.setValue("javascript:open('/fault/AlarmView.do?displayName=All Alerts&header=All Alerts - Critical&viewId=Alerts.1')");
/* 2665 */     int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 2666 */     if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 2667 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 2668 */       return true;
/*      */     }
/* 2670 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 2671 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f4(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2676 */     PageContext pageContext = _jspx_page_context;
/* 2677 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2679 */     AMParam _jspx_th_awolf_005fparam_005f4 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2680 */     _jspx_th_awolf_005fparam_005f4.setPageContext(_jspx_page_context);
/* 2681 */     _jspx_th_awolf_005fparam_005f4.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 2683 */     _jspx_th_awolf_005fparam_005f4.setName("Warning");
/*      */     
/* 2685 */     _jspx_th_awolf_005fparam_005f4.setValue("javascript:open('/fault/AlarmView.do?displayName=All Alerts&header=All Alerts - Warning&viewId=Alerts.14')");
/* 2686 */     int _jspx_eval_awolf_005fparam_005f4 = _jspx_th_awolf_005fparam_005f4.doStartTag();
/* 2687 */     if (_jspx_th_awolf_005fparam_005f4.doEndTag() == 5) {
/* 2688 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 2689 */       return true;
/*      */     }
/* 2691 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 2692 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f5(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2697 */     PageContext pageContext = _jspx_page_context;
/* 2698 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2700 */     AMParam _jspx_th_awolf_005fparam_005f5 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2701 */     _jspx_th_awolf_005fparam_005f5.setPageContext(_jspx_page_context);
/* 2702 */     _jspx_th_awolf_005fparam_005f5.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 2704 */     _jspx_th_awolf_005fparam_005f5.setName("Clear");
/*      */     
/* 2706 */     _jspx_th_awolf_005fparam_005f5.setValue("javascript:open('/fault/AlarmView.do?displayName=All Alerts&header=All Alerts - Clear&viewId=Alerts.15')");
/* 2707 */     int _jspx_eval_awolf_005fparam_005f5 = _jspx_th_awolf_005fparam_005f5.doStartTag();
/* 2708 */     if (_jspx_th_awolf_005fparam_005f5.doEndTag() == 5) {
/* 2709 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5);
/* 2710 */       return true;
/*      */     }
/* 2712 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5);
/* 2713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fimport_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2718 */     PageContext pageContext = _jspx_page_context;
/* 2719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2721 */     ImportTag _jspx_th_c_005fimport_005f0 = (ImportTag)this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.get(ImportTag.class);
/* 2722 */     _jspx_th_c_005fimport_005f0.setPageContext(_jspx_page_context);
/* 2723 */     _jspx_th_c_005fimport_005f0.setParent(null);
/*      */     
/* 2725 */     _jspx_th_c_005fimport_005f0.setUrl("/RecentAlarms.do?method=getRecentAlarms&severity=1");
/* 2726 */     int[] _jspx_push_body_count_c_005fimport_005f0 = { 0 };
/*      */     try {
/* 2728 */       int _jspx_eval_c_005fimport_005f0 = _jspx_th_c_005fimport_005f0.doStartTag();
/* 2729 */       if (_jspx_th_c_005fimport_005f0.doEndTag() == 5)
/* 2730 */         return true;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2733 */         int tmp109_108 = 0; int[] tmp109_106 = _jspx_push_body_count_c_005fimport_005f0; int tmp111_110 = tmp109_106[tmp109_108];tmp109_106[tmp109_108] = (tmp111_110 - 1); if (tmp111_110 <= 0) break;
/* 2734 */         out = _jspx_page_context.popBody(); }
/* 2735 */       _jspx_th_c_005fimport_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2737 */       _jspx_th_c_005fimport_005f0.doFinally();
/* 2738 */       this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.reuse(_jspx_th_c_005fimport_005f0);
/*      */     }
/* 2740 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Alarm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */