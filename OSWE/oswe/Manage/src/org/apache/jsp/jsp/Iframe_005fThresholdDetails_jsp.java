/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.ErrorsTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class Iframe_005fThresholdDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   63 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/*  713 */     java.net.InetAddress add = null;
/*  714 */     if (ip.equals("127.0.0.1")) {
/*  715 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  719 */       add = java.net.InetAddress.getByName(ip);
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
/*  920 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
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
/* 1468 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/*      */           catch (java.sql.SQLException e) {
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
/* 1701 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
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
/* 1818 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1820 */               if (maxCol != null)
/* 1821 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1823 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1818 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 1979 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/*      */   public java.util.Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
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
/* 2142 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2143 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
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
/* 2172 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2178 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2179 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2205 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2209 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2228 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2233 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2237 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2239 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/* 2241 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/* 2245 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2256 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2259 */     JspWriter out = null;
/* 2260 */     Object page = this;
/* 2261 */     JspWriter _jspx_out = null;
/* 2262 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2266 */       response.setContentType("text/html;charset=UTF-8");
/* 2267 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2269 */       _jspx_page_context = pageContext;
/* 2270 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2271 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2272 */       session = pageContext.getSession();
/* 2273 */       out = pageContext.getOut();
/* 2274 */       _jspx_out = out;
/*      */       
/* 2276 */       out.write("<!--$Id$-->\n");
/* 2277 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2279 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2280 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2282 */       out.write(10);
/* 2283 */       out.write(10);
/* 2284 */       out.write(10);
/* 2285 */       out.write("\n\n\n\n\n\n<html>\n<head>\n<title>");
/* 2286 */       out.print(FormatUtil.getString("am.webclient.configurealert.thresholdprofile"));
/* 2287 */       out.write("</title>\n\n<link href=\"/images/");
/* 2288 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2290 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\"> \n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script>\nfunction validateAndSubmit()\n{\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)\n\t{\n\t\tif(document.AMActionForm.elements[i].type==\"text\")\n                {\n                        var name = document.AMActionForm.elements[i].name;\n                        var value = document.AMActionForm.elements[i].value;\n\t\t\tif((name==\"displayname\") && !checkForDisplayName(trimAll(value))) {\n\t\t\t\treturn false\n\t\t\t}\n\t\t\telse if(name==\"criticalthresholdvalue\")\n\t\t\t{\n\t\t\t\tif((trimAll(value)==\"\") || (isPositiveInteger(value)==false))\n\t\t\t\t\t{\n\t\t\talert('");
/* 2291 */       out.print(FormatUtil.getString("am.webclient.threshold.alertpositivecritical"));
/* 2292 */       out.write("');                            \n\t\t\treturn false;\n\t\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"warningthresholdvalue\")\n\t\t\t{\n\t\t\t\tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n\t\t\t\t\t{\n\t\t\talert('");
/* 2293 */       out.print(FormatUtil.getString("am.webclient.threshold.alertpositivewarning"));
/* 2294 */       out.write("');                            \n\t\t\treturn false;\n\t\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"infothresholdvalue\")\n\t\t\t{\n\n\t\t\t\tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n\t\t\t\t\t{\n\t\t\talert('");
/* 2295 */       out.print(FormatUtil.getString("am.webclient.threshold.alertpositiveclear"));
/* 2296 */       out.write("');                            \n\t\t\treturn false;\n\t\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"consecutive_criticalpolls\")\n\t\t\t{\n\t\t\t   if(trimAll(value)!=\"\")\n\t\t\t   {\n\t\t\t  \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('");
/* 2297 */       out.print(FormatUtil.getString("am.webclient.threshold.alertpositivecriticalconsecutive"));
/* 2298 */       out.write("');\n\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t         else if((value>10)||(value<1))\n\t\t\t         {\n\t\t\t              \twindow.alert('");
/* 2299 */       out.print(FormatUtil.getString("am.webclient.threshold.alertpositivecriticalvalue"));
/* 2300 */       out.write("');\n\t\t\t\t        return false;\n\t\t\t         }\n\t\t\t    }\n\t\t\t }\n\t\t\t else if(name==\"consecutive_warningpolls\")\n\t\t\t {\n\t\t\t   if(trimAll(value)!=\"\")\n\t\t\t   {\n\t\t\t  \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('");
/* 2301 */       out.print(FormatUtil.getString("am.webclient.threshold.alertpositivewarningconsecutive"));
/* 2302 */       out.write("');                            \n\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t         else if((value>10)||(value<1))\n\t\t\t\t {\n\t\t\t\t\twindow.alert('");
/* 2303 */       out.print(FormatUtil.getString("am.webclient.threshold.alertpositivewarningvalue"));
/* 2304 */       out.write("');\n\t\t\t\t\treturn false;\n\t\t\t         }\n\t\t\t     }\n\t\t\t }\n\t\t\t else if(name==\"consecutive_clearpolls\")\n\t\t\t {\n\t\t\t  \t\n\t\t\t    if(trimAll(value)!=\"\")\n\t\t\t    {\n\t\t\t   \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('");
/* 2305 */       out.print(FormatUtil.getString("am.webclient.threshold.alertpositiveclearconsecutive"));
/* 2306 */       out.write("');                            \n\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t         else if((value>10)||(value<1))\n\t\t\t\t {\n\t\t\t\t        window.alert('");
/* 2307 */       out.print(FormatUtil.getString("am.webclient.threshold.alertpositiveclearvalue"));
/* 2308 */       out.write("');\n\t\t\t\t        return false;\n\t\t\t         }\n\t\t\t     }\n                        }\n        \t}\n\t}\n\tdocument.AMActionForm.submit();\n}\nfunction closeWindow(redirect)\n{\n\twindow.parent.opener.fnReload(redirect);\n\twindow.parent.close();\n}\nfunction onDelete(path)\n{\n\tif(confirm('");
/* 2309 */       out.print(FormatUtil.getString("am.webclient.threshold.alertdelete"));
/* 2310 */       out.write("'))\n\t{\n\t\twindow.parent.opener.location.href=path;\n\t\twindow.parent.close();\n\t}\n}\n</script>\n</head>\n\n<body leftmargin=\"5\" topmargin=\"5\" marginwidth=\"5\" marginheight=\"5\">\n");
/*      */       
/* 2312 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2313 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2314 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */       
/* 2316 */       _jspx_th_logic_005fnotPresent_005f0.setRole("ADMIN");
/* 2317 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2318 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */         for (;;) {
/* 2320 */           out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr > \n    <td height=\"23\" colspan=\"2\"  class=\"tableheadingbborder\">sadasd");
/* 2321 */           out.print(FormatUtil.getString("am.webclient.configurealert.thresholdprofile"));
/* 2322 */           out.write("</td>\n  </tr>\n  ");
/* 2323 */           out.write(10);
/* 2324 */           out.write(32);
/* 2325 */           out.write(32);
/*      */           
/* 2327 */           ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 2328 */           _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2329 */           _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */           
/* 2331 */           _jspx_th_c_005fforEach_005f0.setVar("row");
/*      */           
/* 2333 */           _jspx_th_c_005fforEach_005f0.setItems("${thresholddetails}");
/* 2334 */           int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */           try {
/* 2336 */             int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2337 */             if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */               for (;;) {
/* 2339 */                 out.write(" \n  <tr > \n    <td width=\"33%\" height=\"19\"  class=\"whitegrayborderbr\">");
/* 2340 */                 out.print(FormatUtil.getString("am.webclient.viewaction.name"));
/* 2341 */                 out.write("</td>\n    <td width=\"67%\"  class=\"whitegrayborder\">");
/* 2342 */                 if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2387 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/* 2344 */                 out.write("</td>\n  </tr>\n  <tr > \n    <td height=\"19\"  class=\"yellowgrayborderbr\">");
/* 2345 */                 out.print(FormatUtil.getString("am.webclient.threshold.description"));
/* 2346 */                 out.write(" </td>\n    <td height=\"19\"  class=\"yellowgrayborder\"> ");
/* 2347 */                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2387 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/* 2349 */                 out.write("</td>\n  </tr>\n  <tr > \n    <td height=\"19\"  class=\"whitegrayborderbr\"><img src=\"/images/icon_legend_critical.gif\" width=\"12\" height=\"11\" hspace=\"2\" vspace=\"0\">");
/* 2350 */                 out.print(FormatUtil.getString("am.webclient.threshold.criticalalert"));
/* 2351 */                 out.write("</td>\n    <td  class=\"whitegrayborder\">Value is ");
/* 2352 */                 if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2387 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/* 2354 */                 out.write(" \n      ");
/* 2355 */                 if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2387 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/* 2357 */                 out.write("</td>\n  </tr>\n  <tr > \n    <td height=\"19\"  class=\"yellowgrayborderbr\"><img src=\"/images/icon_legend_warning.gif\" width=\"12\" height=\"11\" hspace=\"2\">");
/* 2358 */                 out.print(FormatUtil.getString("am.webclient.threshold.warningalert"));
/* 2359 */                 out.write("</td>\n    <td height=\"19\"  class=\"yellowgrayborder\">Value is ");
/* 2360 */                 if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2387 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/* 2362 */                 out.write(" \n      ");
/* 2363 */                 if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2387 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/* 2365 */                 out.write("</td>\n  </tr>\n  <tr > \n    <td height=\"19\"  class=\"whitegrayborderbr\"><img src=\"/images/icon_legend_clear.gif\" width=\"12\" height=\"11\" hspace=\"2\">");
/* 2366 */                 out.print(FormatUtil.getString("am.webclient.threshold.clearalert"));
/* 2367 */                 out.write("</td>\n    <td height=\"19\"  class=\"whitegrayborder\">value is ");
/* 2368 */                 if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2387 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/* 2370 */                 out.write(" \n      ");
/* 2371 */                 if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2387 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/* 2373 */                 out.write("</td>\n  </tr>\n  ");
/* 2374 */                 int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2375 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2379 */             if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2387 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */             }
/*      */           }
/*      */           catch (Throwable _jspx_exception)
/*      */           {
/*      */             for (;;)
/*      */             {
/* 2383 */               int tmp1087_1086 = 0; int[] tmp1087_1084 = _jspx_push_body_count_c_005fforEach_005f0; int tmp1089_1088 = tmp1087_1084[tmp1087_1086];tmp1087_1084[tmp1087_1086] = (tmp1089_1088 - 1); if (tmp1089_1088 <= 0) break;
/* 2384 */               out = _jspx_page_context.popBody(); }
/* 2385 */             _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */           } finally {
/* 2387 */             _jspx_th_c_005fforEach_005f0.doFinally();
/* 2388 */             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */           }
/* 2390 */           out.write(" \n  ");
/* 2391 */           out.write("\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr> \n    <td width=\"44%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"56%\" class=\"tablebottom\"><input name=\"Button\" type=\"button\" class=\"buttons\" value=\"Close\" onClick=\"javascript:window.parent.close();\"></td>\n  </tr>\n</table>\n");
/* 2392 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2393 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2397 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2398 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */       }
/*      */       else {
/* 2401 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2402 */         out.write(10);
/*      */         
/* 2404 */         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2405 */         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2406 */         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */         
/* 2408 */         _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2409 */         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2410 */         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */           for (;;) {
/* 2412 */             out.write(10);
/*      */             
/* 2414 */             String resID = request.getParameter("resourceid");
/* 2415 */             String attrID = request.getParameter("attributeid");
/* 2416 */             String haID = request.getParameter("haid");
/* 2417 */             String thresholdID = request.getParameter("thresholdid");
/* 2418 */             String redirectTo = request.getParameter("redirectTo");
/* 2419 */             String delete = request.getParameter("delete");
/*      */             
/* 2421 */             out.write(10);
/* 2422 */             out.write(9);
/* 2423 */             if (_jspx_meth_html_005ferrors_005f0(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */               return;
/* 2425 */             out.write(10);
/* 2426 */             out.write(9);
/*      */             
/* 2428 */             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 2429 */             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2430 */             _jspx_th_html_005fform_005f0.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */             
/* 2432 */             _jspx_th_html_005fform_005f0.setAction("/adminAction");
/* 2433 */             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2434 */             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */               for (;;) {
/* 2436 */                 out.write(10);
/* 2437 */                 out.write(9);
/* 2438 */                 if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/* 2440 */                 out.write(10);
/* 2441 */                 out.write(9);
/* 2442 */                 if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/* 2444 */                 out.write(10);
/* 2445 */                 out.write(9);
/* 2446 */                 if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/* 2448 */                 out.write("\n\t<input name=\"resourceid\" type=\"hidden\" value=\"");
/* 2449 */                 out.print(resID);
/* 2450 */                 out.write("\">\n\t<input name=\"attributeid\" type=\"hidden\" value=\"");
/* 2451 */                 out.print(attrID);
/* 2452 */                 out.write("\">\n\t<input name=\"haid\" type=\"hidden\" value=\"");
/* 2453 */                 out.print(haID);
/* 2454 */                 out.write("\">\n\t<input name=\"redirectTo\" type=\"hidden\" value=\"");
/* 2455 */                 out.print(redirectTo);
/* 2456 */                 out.write("\">\n\t<input name=\"delete\" type=\"hidden\" value=\"");
/* 2457 */                 out.print(delete);
/* 2458 */                 out.write("\">\n\t<input name=\"method\" type=\"hidden\" value=\"editThresholdAction\">\n\t<input name=\"update\" type=\"hidden\" value=\"true\">\n\t<input name=\"id\" type=\"hidden\" value=\"");
/* 2459 */                 out.print(thresholdID);
/* 2460 */                 out.write("\">\n\t");
/*      */                 
/* 2462 */                 MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 2463 */                 _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 2464 */                 _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                 
/* 2466 */                 _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 2467 */                 int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 2468 */                 if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                   for (;;) {
/* 2470 */                     out.write(" \n\t   ");
/*      */                     
/* 2472 */                     MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 2473 */                     _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 2474 */                     _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                     
/* 2476 */                     _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                     
/* 2478 */                     _jspx_th_html_005fmessages_005f0.setMessage("true");
/* 2479 */                     int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 2480 */                     if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 2481 */                       String msg = null;
/* 2482 */                       if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 2483 */                         out = _jspx_page_context.pushBody();
/* 2484 */                         _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2485 */                         _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                       }
/* 2487 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/* 2489 */                         out.write("\n\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"messagebox\">\n\t\t\t\t  <tr> \n\t\t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\"></td>\n\t\t\t\t\t<td width=\"95%\" class=\"message\"> <bean:write name=\"msg\"/></td>\n\t\t\t\t  </tr>\n\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t");
/* 2490 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 2491 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/* 2492 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2495 */                       if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 2496 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2499 */                     if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 2500 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                     }
/*      */                     
/* 2503 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 2504 */                     out.write(10);
/* 2505 */                     out.write(9);
/* 2506 */                     int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 2507 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2511 */                 if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 2512 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                 }
/*      */                 
/* 2515 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 2516 */                 out.write(" \n\t<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t  <tr > \n\t\t\n    <td height=\"23\"  class=\"tableheading\">");
/* 2517 */                 out.print(FormatUtil.getString("am.webclient.configurealert.thresholdprofile"));
/* 2518 */                 out.write(" </td>\n\t  </tr>\n\t  </table>\n\t  ");
/* 2519 */                 out.write("\n\t  \t<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrbborder\">\n\t  ");
/*      */                 
/* 2521 */                 ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 2522 */                 _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 2523 */                 _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                 
/* 2525 */                 _jspx_th_c_005fforEach_005f1.setVar("row");
/*      */                 
/* 2527 */                 _jspx_th_c_005fforEach_005f1.setItems("${thresholddetails}");
/* 2528 */                 int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                 try {
/* 2530 */                   int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 2531 */                   if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                     for (;;) {
/* 2533 */                       out.write(" \n\t\t<tr> \n\t\t\t <td class=\"whitegrayborderbr\" width=\"20%\">&nbsp;");
/* 2534 */                       out.print(FormatUtil.getString("am.webclient.viewaction.name"));
/* 2535 */                       out.write(":</td>\n\t\t\t <td class=\"whitegrayborder\" colspan=\"2\" width=\"80%\">\n\t\t\t\t");
/* 2536 */                       if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2538 */                       out.write("\n\t\t\t\t");
/* 2539 */                       if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2541 */                       out.write("\n\t\t\t </td>\n\t\t</tr>  \n\t\t<tr> \n\t\t\t <td class=\"yellowgrayborderbr\">&nbsp;");
/* 2542 */                       out.print(FormatUtil.getString("am.webclient.threshold.description"));
/* 2543 */                       out.write(":</td>\n\t\t\t <td class=\"yellowgrayborder\" colspan=\"2\">\n\t\t\t\t");
/* 2544 */                       if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2546 */                       out.write("\n\t\t\t\t");
/* 2547 */                       if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2549 */                       out.write("\n\t\t\t </td>\n\t\t</tr>  \n\t\t<tr> \n\t\t\t  <td class=\"whitegrayborderbr\" valign=\"top\"><img src=\"/images/icon_legend_critical.gif\" width=\"12\" height=\"11\" hspace=\"2\" vspace=\"0\">");
/* 2550 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalalert"));
/* 2551 */                       out.write("</td>\n\t\t\t  <td valign=\"top\" class=\"whitegrayborderbr\"> \n\t\t\t\t   ");
/* 2552 */                       if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2554 */                       out.write("\n\t\t\t\t   ");
/* 2555 */                       if (_jspx_meth_html_005fselect_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2557 */                       out.write(" \n\t\t\t  </td>\n\t\t\t <td class=\"whitegrayborder\" valign=\"top\">\n\t\t\t\t ");
/* 2558 */                       if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2560 */                       out.write("\n\t\t\t\t ");
/* 2561 */                       if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2563 */                       out.write(32);
/* 2564 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.chooseresourcefor.text"));
/* 2565 */                       out.write("\n\t\t\t\t  ");
/* 2566 */                       if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2568 */                       out.write("\t\t\t\t \n\t\t\t\t ");
/* 2569 */                       if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2571 */                       out.write(32);
/* 2572 */                       out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2573 */                       out.write("\n\t\t\t </td>\n\t\t</tr>\n\t\t<tr>\n\t\t   <td valign=\"top\" class=\"yellowgrayborderbr\"><img src=\"/images/icon_legend_warning.gif\" width=\"12\" height=\"11\" hspace=\"2\">");
/* 2574 */                       out.print(FormatUtil.getString("am.webclient.threshold.warningalert"));
/* 2575 */                       out.write("</td>\n\t\t   <td class=\"yellowgrayborderbr\"valign=\"top\" >\n\t\t\t  ");
/* 2576 */                       if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2578 */                       out.write("\n\t\t\t  ");
/* 2579 */                       if (_jspx_meth_html_005fselect_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2581 */                       out.write("\n\t\t   </td>\n\t\t   <td class=\"yellowgrayborder\" valign=\"top\" >\n\t\t\t  ");
/* 2582 */                       if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2584 */                       out.write("\n\t\t\t  ");
/* 2585 */                       if (_jspx_meth_html_005ftext_005f4(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2587 */                       out.write(32);
/* 2588 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.chooseresourcefor.text"));
/* 2589 */                       out.write("\n\t\t\t   ");
/* 2590 */                       if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2592 */                       out.write("\t\t\t\t \n\t\t\t  ");
/* 2593 */                       if (_jspx_meth_html_005ftext_005f5(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2595 */                       out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2596 */                       out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td valign=\"top\" class=\"whitegrayborderbr\"><img src=\"/images/icon_legend_clear.gif\" width=\"12\" height=\"11\" hspace=\"2\">");
/* 2597 */                       out.print(FormatUtil.getString("am.webclient.threshold.clearalert"));
/* 2598 */                       out.write("</td>\n\t\t\t<td class=\"whitegrayborderbr\" valign=\"top\">\n\t\t\t   ");
/* 2599 */                       if (_jspx_meth_c_005fset_005f10(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2601 */                       out.write("\n\t\t\t   ");
/* 2602 */                       if (_jspx_meth_html_005fselect_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2604 */                       out.write(" \n\t\t\t</td>\n\t\t\t<td class=\"whitegrayborder\" valign=\"top\"> \n\t\t\t\t ");
/* 2605 */                       if (_jspx_meth_c_005fset_005f11(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2607 */                       out.write("\n\t\t\t\t ");
/* 2608 */                       if (_jspx_meth_html_005ftext_005f6(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2610 */                       out.write(32);
/* 2611 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.chooseresourcefor.text"));
/* 2612 */                       out.write("\n\t\t\t\t ");
/* 2613 */                       if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2615 */                       out.write("\t\n\t\t\t\t");
/* 2616 */                       if (_jspx_meth_html_005ftext_005f7(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                       }
/* 2618 */                       out.write(32);
/* 2619 */                       out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2620 */                       out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t");
/* 2621 */                       int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 2622 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2626 */                   if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2634 */                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                   }
/*      */                 }
/*      */                 catch (Throwable _jspx_exception)
/*      */                 {
/*      */                   for (;;)
/*      */                   {
/* 2630 */                     int tmp3306_3305 = 0; int[] tmp3306_3303 = _jspx_push_body_count_c_005fforEach_005f1; int tmp3308_3307 = tmp3306_3303[tmp3306_3305];tmp3306_3303[tmp3306_3305] = (tmp3308_3307 - 1); if (tmp3308_3307 <= 0) break;
/* 2631 */                     out = _jspx_page_context.popBody(); }
/* 2632 */                   _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                 } finally {
/* 2634 */                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 2635 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                 }
/* 2637 */                 out.write("\n\t</table>\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t  <tr> \n\t\t<td align=\"center\" class=\"tablebottom\">\n\t\t");
/* 2638 */                 if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/* 2640 */                 out.write("\t\t\n\t\t<input name=\"submitbutton\" value=\"");
/* 2641 */                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.qengine.button.update"));
/* 2642 */                 out.write("\" type=\"button\" class=\"buttons\" onClick=\"javascript:validateAndSubmit()\">\n\t\t");
/*      */                 
/* 2644 */                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2645 */                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2646 */                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                 
/* 2648 */                 _jspx_th_c_005fif_005f1.setTest("${param.delete=='true'}");
/* 2649 */                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2650 */                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                   for (;;) {
/* 2652 */                     out.write(10);
/* 2653 */                     out.write(9);
/* 2654 */                     out.write(9);
/*      */                     
/* 2656 */                     String path = "/adminAction.do?method=deleteAssociatedThreshold&resourceid=" + resID + "&attributeid=" + attrID + "&redirectto=" + java.net.URLEncoder.encode(new StringBuilder().append(redirectTo).append("").toString());
/*      */                     
/* 2658 */                     out.write("\n\t\t<input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/* 2659 */                     out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 2660 */                     out.write("\" onClick=\"javascript:onDelete('");
/* 2661 */                     out.print(path);
/* 2662 */                     out.write("');\">\n\t\t");
/* 2663 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2664 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2668 */                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2669 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                 }
/*      */                 
/* 2672 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2673 */                 out.write("\t\t\n\t\t");
/* 2674 */                 if ((redirectTo == null) || (redirectTo.equals("null"))) {
/* 2675 */                   out.write("\n\t\t<input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/* 2676 */                   out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 2677 */                   out.write("\" onClick=\"javascript:window.parent.close();\">\t\t\n\t\t");
/*      */                 } else {
/* 2679 */                   out.write("\n\t\t<input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/* 2680 */                   out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 2681 */                   out.write("\" onClick=\"javascript:closeWindow('");
/* 2682 */                   out.print(redirectTo);
/* 2683 */                   out.write("')\">\n\t\t");
/*      */                 }
/* 2685 */                 out.write("\n\t\t\n\t\t</td>\n\t  </tr>\n\t</table>\n\t");
/* 2686 */                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2687 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2691 */             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2692 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */             }
/*      */             
/* 2695 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2696 */             out.write(10);
/* 2697 */             out.write(9);
/* 2698 */             out.write(10);
/* 2699 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2700 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2704 */         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2705 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */         }
/*      */         else {
/* 2708 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2709 */           out.write("\n\n</body>\n</html>\n");
/*      */         }
/* 2711 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2712 */         out = _jspx_out;
/* 2713 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2714 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2715 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2718 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2724 */     PageContext pageContext = _jspx_page_context;
/* 2725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2727 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2728 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2729 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2731 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2733 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2734 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2735 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2736 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2737 */       return true;
/*      */     }
/* 2739 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2740 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2745 */     PageContext pageContext = _jspx_page_context;
/* 2746 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2748 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2749 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2750 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2752 */     _jspx_th_c_005fout_005f1.setValue("${row[0]}");
/* 2753 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2754 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2755 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2756 */       return true;
/*      */     }
/* 2758 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2759 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2764 */     PageContext pageContext = _jspx_page_context;
/* 2765 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2767 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2768 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2769 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2771 */     _jspx_th_c_005fout_005f2.setValue("${row[1]}");
/* 2772 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2773 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2774 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2775 */       return true;
/*      */     }
/* 2777 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2783 */     PageContext pageContext = _jspx_page_context;
/* 2784 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2786 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2787 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2788 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2790 */     _jspx_th_c_005fout_005f3.setValue("${applicationScope.thresholdconditions[row[2]]}");
/* 2791 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2792 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2793 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2794 */       return true;
/*      */     }
/* 2796 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2802 */     PageContext pageContext = _jspx_page_context;
/* 2803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2805 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2806 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2807 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2809 */     _jspx_th_c_005fout_005f4.setValue("${row[3]}");
/* 2810 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2811 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2812 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2813 */       return true;
/*      */     }
/* 2815 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2821 */     PageContext pageContext = _jspx_page_context;
/* 2822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2824 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2825 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2826 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2828 */     _jspx_th_c_005fout_005f5.setValue("${applicationScope.thresholdconditions[row[4]]}");
/* 2829 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2830 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2831 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2832 */       return true;
/*      */     }
/* 2834 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2835 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2840 */     PageContext pageContext = _jspx_page_context;
/* 2841 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2843 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2844 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2845 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2847 */     _jspx_th_c_005fout_005f6.setValue("${row[5]}");
/* 2848 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2849 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2850 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2851 */       return true;
/*      */     }
/* 2853 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2854 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2859 */     PageContext pageContext = _jspx_page_context;
/* 2860 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2862 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2863 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2864 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2866 */     _jspx_th_c_005fout_005f7.setValue("${applicationScope.thresholdconditions[row[6]]}");
/* 2867 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2868 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2869 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2870 */       return true;
/*      */     }
/* 2872 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2878 */     PageContext pageContext = _jspx_page_context;
/* 2879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2881 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2882 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2883 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2885 */     _jspx_th_c_005fout_005f8.setValue("${row[7]}");
/* 2886 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2887 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2888 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2889 */       return true;
/*      */     }
/* 2891 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ferrors_005f0(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2897 */     PageContext pageContext = _jspx_page_context;
/* 2898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2900 */     ErrorsTag _jspx_th_html_005ferrors_005f0 = (ErrorsTag)this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.get(ErrorsTag.class);
/* 2901 */     _jspx_th_html_005ferrors_005f0.setPageContext(_jspx_page_context);
/* 2902 */     _jspx_th_html_005ferrors_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/* 2903 */     int _jspx_eval_html_005ferrors_005f0 = _jspx_th_html_005ferrors_005f0.doStartTag();
/* 2904 */     if (_jspx_th_html_005ferrors_005f0.doEndTag() == 5) {
/* 2905 */       this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 2906 */       return true;
/*      */     }
/* 2908 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 2909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2914 */     PageContext pageContext = _jspx_page_context;
/* 2915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2917 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2918 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 2919 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2921 */     _jspx_th_html_005fhidden_005f0.setProperty("criticalthresholdmessage");
/* 2922 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 2923 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 2924 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2925 */       return true;
/*      */     }
/* 2927 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2933 */     PageContext pageContext = _jspx_page_context;
/* 2934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2936 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2937 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 2938 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2940 */     _jspx_th_html_005fhidden_005f1.setProperty("warningthresholdmessage");
/* 2941 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 2942 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 2943 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2944 */       return true;
/*      */     }
/* 2946 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2952 */     PageContext pageContext = _jspx_page_context;
/* 2953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2955 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2956 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 2957 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2959 */     _jspx_th_html_005fhidden_005f2.setProperty("infothresholdmessage");
/* 2960 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 2961 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 2962 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 2963 */       return true;
/*      */     }
/* 2965 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 2966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2971 */     PageContext pageContext = _jspx_page_context;
/* 2972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2974 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 2975 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2976 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 2978 */     _jspx_th_c_005fset_005f0.setTarget("${AMActionForm}");
/*      */     
/* 2980 */     _jspx_th_c_005fset_005f0.setProperty("displayname");
/*      */     
/* 2982 */     _jspx_th_c_005fset_005f0.setValue("${row[0]}");
/* 2983 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2984 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2985 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2986 */       return true;
/*      */     }
/* 2988 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2989 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2994 */     PageContext pageContext = _jspx_page_context;
/* 2995 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2997 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2998 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 2999 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3001 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 3003 */     _jspx_th_html_005ftext_005f0.setSize("30");
/*      */     
/* 3005 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/* 3007 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 3008 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3009 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3010 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3011 */       return true;
/*      */     }
/* 3013 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3014 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3019 */     PageContext pageContext = _jspx_page_context;
/* 3020 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3022 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3023 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3024 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3026 */     _jspx_th_c_005fset_005f1.setTarget("${AMActionForm}");
/*      */     
/* 3028 */     _jspx_th_c_005fset_005f1.setProperty("description");
/*      */     
/* 3030 */     _jspx_th_c_005fset_005f1.setValue("${row[1]}");
/* 3031 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3032 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3033 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 3034 */       return true;
/*      */     }
/* 3036 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 3037 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3042 */     PageContext pageContext = _jspx_page_context;
/* 3043 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3045 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3046 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3047 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3049 */     _jspx_th_html_005ftext_005f1.setProperty("description");
/*      */     
/* 3051 */     _jspx_th_html_005ftext_005f1.setSize("50");
/*      */     
/* 3053 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 3055 */     _jspx_th_html_005ftext_005f1.setMaxlength("50");
/* 3056 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3057 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3058 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3059 */       return true;
/*      */     }
/* 3061 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3062 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3067 */     PageContext pageContext = _jspx_page_context;
/* 3068 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3070 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3071 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3072 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3074 */     _jspx_th_c_005fset_005f2.setTarget("${AMActionForm}");
/*      */     
/* 3076 */     _jspx_th_c_005fset_005f2.setProperty("criticalthresholdcondition");
/*      */     
/* 3078 */     _jspx_th_c_005fset_005f2.setValue("${row[2]}");
/* 3079 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3080 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3081 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 3082 */       return true;
/*      */     }
/* 3084 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 3085 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3090 */     PageContext pageContext = _jspx_page_context;
/* 3091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3093 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3094 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3095 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3097 */     _jspx_th_html_005fselect_005f0.setProperty("criticalthresholdcondition");
/*      */     
/* 3099 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/* 3100 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3101 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3102 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3103 */         out = _jspx_page_context.pushBody();
/* 3104 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 3105 */         _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3106 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3109 */         out.write(" \n\t\t\t\t\t   ");
/* 3110 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3111 */           return true;
/* 3112 */         out.write("\n\t\t\t\t   ");
/* 3113 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3114 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3117 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3118 */         out = _jspx_page_context.popBody();
/* 3119 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 3122 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3123 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 3124 */       return true;
/*      */     }
/* 3126 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 3127 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3132 */     PageContext pageContext = _jspx_page_context;
/* 3133 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3135 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3136 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 3137 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 3139 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("conditions");
/* 3140 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 3141 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 3142 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3143 */       return true;
/*      */     }
/* 3145 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3146 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3151 */     PageContext pageContext = _jspx_page_context;
/* 3152 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3154 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3155 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3156 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3158 */     _jspx_th_c_005fset_005f3.setTarget("${AMActionForm}");
/*      */     
/* 3160 */     _jspx_th_c_005fset_005f3.setProperty("criticalthresholdvalue");
/*      */     
/* 3162 */     _jspx_th_c_005fset_005f3.setValue("${row[3]}");
/* 3163 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3164 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3165 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 3166 */       return true;
/*      */     }
/* 3168 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 3169 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3174 */     PageContext pageContext = _jspx_page_context;
/* 3175 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3177 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3178 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3179 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3181 */     _jspx_th_html_005ftext_005f2.setProperty("criticalthresholdvalue");
/*      */     
/* 3183 */     _jspx_th_html_005ftext_005f2.setSize("5");
/*      */     
/* 3185 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 3187 */     _jspx_th_html_005ftext_005f2.setMaxlength("100");
/* 3188 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3189 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3190 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3191 */       return true;
/*      */     }
/* 3193 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3194 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3199 */     PageContext pageContext = _jspx_page_context;
/* 3200 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3202 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3203 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3204 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 3205 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3206 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 3208 */         out.write("\n\t\t\t\t \t");
/* 3209 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3210 */           return true;
/* 3211 */         out.write("\n\t\t\t\t \t");
/* 3212 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3213 */           return true;
/* 3214 */         out.write("\n\t\t\t\t ");
/* 3215 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3216 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3220 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3221 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3222 */       return true;
/*      */     }
/* 3224 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3230 */     PageContext pageContext = _jspx_page_context;
/* 3231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3233 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3234 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3235 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3237 */     _jspx_th_c_005fwhen_005f0.setTest("${row[8]== \"-1\"}");
/* 3238 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3239 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 3241 */         out.write("\n\t\t\t\t \t\t ");
/* 3242 */         if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3243 */           return true;
/* 3244 */         out.write("\n\t\t\t\t \t");
/* 3245 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3246 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3250 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3251 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3252 */       return true;
/*      */     }
/* 3254 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3260 */     PageContext pageContext = _jspx_page_context;
/* 3261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3263 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3264 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 3265 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3267 */     _jspx_th_c_005fset_005f4.setTarget("${AMActionForm}");
/*      */     
/* 3269 */     _jspx_th_c_005fset_005f4.setProperty("consecutive_criticalpolls");
/*      */     
/* 3271 */     _jspx_th_c_005fset_005f4.setValue(" ");
/* 3272 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 3273 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 3274 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 3275 */       return true;
/*      */     }
/* 3277 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 3278 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3283 */     PageContext pageContext = _jspx_page_context;
/* 3284 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3286 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3287 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3288 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 3289 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3290 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3292 */         out.write("\n\t\t\t\t \t\t");
/* 3293 */         if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3294 */           return true;
/* 3295 */         out.write("\n\t\t\t\t \t");
/* 3296 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3297 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3301 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3302 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3303 */       return true;
/*      */     }
/* 3305 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3311 */     PageContext pageContext = _jspx_page_context;
/* 3312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3314 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3315 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 3316 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3318 */     _jspx_th_c_005fset_005f5.setTarget("${AMActionForm}");
/*      */     
/* 3320 */     _jspx_th_c_005fset_005f5.setProperty("consecutive_criticalpolls");
/*      */     
/* 3322 */     _jspx_th_c_005fset_005f5.setValue("${row[8]}");
/* 3323 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 3324 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 3325 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 3326 */       return true;
/*      */     }
/* 3328 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 3329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3334 */     PageContext pageContext = _jspx_page_context;
/* 3335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3337 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3338 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 3339 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3341 */     _jspx_th_html_005ftext_005f3.setProperty("consecutive_criticalpolls");
/*      */     
/* 3343 */     _jspx_th_html_005ftext_005f3.setSize("2");
/*      */     
/* 3345 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 3347 */     _jspx_th_html_005ftext_005f3.setMaxlength("2");
/* 3348 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 3349 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 3350 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3351 */       return true;
/*      */     }
/* 3353 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3354 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3359 */     PageContext pageContext = _jspx_page_context;
/* 3360 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3362 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3363 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 3364 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3366 */     _jspx_th_c_005fset_005f6.setTarget("${AMActionForm}");
/*      */     
/* 3368 */     _jspx_th_c_005fset_005f6.setProperty("warningthresholdcondition");
/*      */     
/* 3370 */     _jspx_th_c_005fset_005f6.setValue("${row[4]}");
/* 3371 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 3372 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 3373 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 3374 */       return true;
/*      */     }
/* 3376 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 3377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3382 */     PageContext pageContext = _jspx_page_context;
/* 3383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3385 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3386 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3387 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3389 */     _jspx_th_html_005fselect_005f1.setProperty("warningthresholdcondition");
/*      */     
/* 3391 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/* 3392 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3393 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3394 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3395 */         out = _jspx_page_context.pushBody();
/* 3396 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 3397 */         _jspx_th_html_005fselect_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3398 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3401 */         out.write(" \n\t\t\t\t  ");
/* 3402 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3403 */           return true;
/* 3404 */         out.write("\n\t\t\t   ");
/* 3405 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3406 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3409 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3410 */         out = _jspx_page_context.popBody();
/* 3411 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 3414 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3415 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 3416 */       return true;
/*      */     }
/* 3418 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 3419 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3424 */     PageContext pageContext = _jspx_page_context;
/* 3425 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3427 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3428 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 3429 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 3431 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("conditions");
/* 3432 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 3433 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 3434 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3435 */       return true;
/*      */     }
/* 3437 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3443 */     PageContext pageContext = _jspx_page_context;
/* 3444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3446 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3447 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 3448 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3450 */     _jspx_th_c_005fset_005f7.setTarget("${AMActionForm}");
/*      */     
/* 3452 */     _jspx_th_c_005fset_005f7.setProperty("warningthresholdvalue");
/*      */     
/* 3454 */     _jspx_th_c_005fset_005f7.setValue("${row[5]}");
/* 3455 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 3456 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 3457 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f7);
/* 3458 */       return true;
/*      */     }
/* 3460 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f7);
/* 3461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3466 */     PageContext pageContext = _jspx_page_context;
/* 3467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3469 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3470 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 3471 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3473 */     _jspx_th_html_005ftext_005f4.setProperty("warningthresholdvalue");
/*      */     
/* 3475 */     _jspx_th_html_005ftext_005f4.setSize("5");
/*      */     
/* 3477 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */     
/* 3479 */     _jspx_th_html_005ftext_005f4.setMaxlength("100");
/* 3480 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 3481 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 3482 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3483 */       return true;
/*      */     }
/* 3485 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3486 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3491 */     PageContext pageContext = _jspx_page_context;
/* 3492 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3494 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3495 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3496 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 3497 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3498 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 3500 */         out.write("\n\t\t\t\t");
/* 3501 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3502 */           return true;
/* 3503 */         out.write("\n\t\t\t\t");
/* 3504 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3505 */           return true;
/* 3506 */         out.write("\n\t\t\t  ");
/* 3507 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3508 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3512 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3513 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3514 */       return true;
/*      */     }
/* 3516 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3522 */     PageContext pageContext = _jspx_page_context;
/* 3523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3525 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3526 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3527 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 3529 */     _jspx_th_c_005fwhen_005f1.setTest("${row[9]== \"-1\"}");
/* 3530 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3531 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 3533 */         out.write("\n\t\t\t\t\t ");
/* 3534 */         if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3535 */           return true;
/* 3536 */         out.write("\n\t\t\t\t");
/* 3537 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3538 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3542 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3543 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3544 */       return true;
/*      */     }
/* 3546 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3547 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3552 */     PageContext pageContext = _jspx_page_context;
/* 3553 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3555 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3556 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 3557 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3559 */     _jspx_th_c_005fset_005f8.setTarget("${AMActionForm}");
/*      */     
/* 3561 */     _jspx_th_c_005fset_005f8.setProperty("consecutive_warningpolls");
/*      */     
/* 3563 */     _jspx_th_c_005fset_005f8.setValue(" ");
/* 3564 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 3565 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 3566 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f8);
/* 3567 */       return true;
/*      */     }
/* 3569 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f8);
/* 3570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3575 */     PageContext pageContext = _jspx_page_context;
/* 3576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3578 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3579 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3580 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 3581 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3582 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 3584 */         out.write("\n\t\t\t\t\t");
/* 3585 */         if (_jspx_meth_c_005fset_005f9(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3586 */           return true;
/* 3587 */         out.write("\n\t\t\t\t");
/* 3588 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3589 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3593 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3594 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3595 */       return true;
/*      */     }
/* 3597 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3603 */     PageContext pageContext = _jspx_page_context;
/* 3604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3606 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3607 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 3608 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3610 */     _jspx_th_c_005fset_005f9.setTarget("${AMActionForm}");
/*      */     
/* 3612 */     _jspx_th_c_005fset_005f9.setProperty("consecutive_warningpolls");
/*      */     
/* 3614 */     _jspx_th_c_005fset_005f9.setValue("${row[9]}");
/* 3615 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 3616 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 3617 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f9);
/* 3618 */       return true;
/*      */     }
/* 3620 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f9);
/* 3621 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3626 */     PageContext pageContext = _jspx_page_context;
/* 3627 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3629 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3630 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 3631 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3633 */     _jspx_th_html_005ftext_005f5.setProperty("consecutive_warningpolls");
/*      */     
/* 3635 */     _jspx_th_html_005ftext_005f5.setSize("2");
/*      */     
/* 3637 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/*      */     
/* 3639 */     _jspx_th_html_005ftext_005f5.setMaxlength("2");
/* 3640 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 3641 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 3642 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3643 */       return true;
/*      */     }
/* 3645 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3646 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f10(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3651 */     PageContext pageContext = _jspx_page_context;
/* 3652 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3654 */     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3655 */     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 3656 */     _jspx_th_c_005fset_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3658 */     _jspx_th_c_005fset_005f10.setTarget("${AMActionForm}");
/*      */     
/* 3660 */     _jspx_th_c_005fset_005f10.setProperty("infothresholdcondition");
/*      */     
/* 3662 */     _jspx_th_c_005fset_005f10.setValue("${row[6]}");
/* 3663 */     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 3664 */     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 3665 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f10);
/* 3666 */       return true;
/*      */     }
/* 3668 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f10);
/* 3669 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3674 */     PageContext pageContext = _jspx_page_context;
/* 3675 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3677 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3678 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 3679 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3681 */     _jspx_th_html_005fselect_005f2.setProperty("infothresholdcondition");
/*      */     
/* 3683 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/* 3684 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 3685 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 3686 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3687 */         out = _jspx_page_context.pushBody();
/* 3688 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 3689 */         _jspx_th_html_005fselect_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3690 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3693 */         out.write(" \n\t\t\t\t\t");
/* 3694 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3695 */           return true;
/* 3696 */         out.write(" \n\t\t\t   ");
/* 3697 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 3698 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3701 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3702 */         out = _jspx_page_context.popBody();
/* 3703 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 3706 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 3707 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 3708 */       return true;
/*      */     }
/* 3710 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 3711 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3716 */     PageContext pageContext = _jspx_page_context;
/* 3717 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3719 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3720 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 3721 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 3723 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("conditions");
/* 3724 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 3725 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 3726 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3727 */       return true;
/*      */     }
/* 3729 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3730 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f11(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3735 */     PageContext pageContext = _jspx_page_context;
/* 3736 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3738 */     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3739 */     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 3740 */     _jspx_th_c_005fset_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3742 */     _jspx_th_c_005fset_005f11.setTarget("${AMActionForm}");
/*      */     
/* 3744 */     _jspx_th_c_005fset_005f11.setProperty("infothresholdvalue");
/*      */     
/* 3746 */     _jspx_th_c_005fset_005f11.setValue("${row[7]}");
/* 3747 */     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 3748 */     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 3749 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f11);
/* 3750 */       return true;
/*      */     }
/* 3752 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f11);
/* 3753 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3758 */     PageContext pageContext = _jspx_page_context;
/* 3759 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3761 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3762 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 3763 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3765 */     _jspx_th_html_005ftext_005f6.setProperty("infothresholdvalue");
/*      */     
/* 3767 */     _jspx_th_html_005ftext_005f6.setSize("5");
/*      */     
/* 3769 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext");
/*      */     
/* 3771 */     _jspx_th_html_005ftext_005f6.setMaxlength("100");
/* 3772 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 3773 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 3774 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3775 */       return true;
/*      */     }
/* 3777 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3783 */     PageContext pageContext = _jspx_page_context;
/* 3784 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3786 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3787 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3788 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 3789 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3790 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 3792 */         out.write("\n\t\t\t\t\t");
/* 3793 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3794 */           return true;
/* 3795 */         out.write("\n\t\t\t\t\t");
/* 3796 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3797 */           return true;
/* 3798 */         out.write("\n\t\t\t\t");
/* 3799 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3800 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3804 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3805 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3806 */       return true;
/*      */     }
/* 3808 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3809 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3814 */     PageContext pageContext = _jspx_page_context;
/* 3815 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3817 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3818 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3819 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 3821 */     _jspx_th_c_005fwhen_005f2.setTest("${row[10]== \"-1\"}");
/* 3822 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3823 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 3825 */         out.write("\n\t\t\t\t\t\t ");
/* 3826 */         if (_jspx_meth_c_005fset_005f12(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3827 */           return true;
/* 3828 */         out.write("\n\t\t\t\t\t");
/* 3829 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3830 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3834 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3835 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3836 */       return true;
/*      */     }
/* 3838 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3839 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f12(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3844 */     PageContext pageContext = _jspx_page_context;
/* 3845 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3847 */     SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3848 */     _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 3849 */     _jspx_th_c_005fset_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 3851 */     _jspx_th_c_005fset_005f12.setTarget("${AMActionForm}");
/*      */     
/* 3853 */     _jspx_th_c_005fset_005f12.setProperty("consecutive_clearpolls");
/*      */     
/* 3855 */     _jspx_th_c_005fset_005f12.setValue(" ");
/* 3856 */     int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 3857 */     if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 3858 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f12);
/* 3859 */       return true;
/*      */     }
/* 3861 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f12);
/* 3862 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3867 */     PageContext pageContext = _jspx_page_context;
/* 3868 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3870 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3871 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3872 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 3873 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3874 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 3876 */         out.write("\n\t\t\t\t\t\t");
/* 3877 */         if (_jspx_meth_c_005fset_005f13(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3878 */           return true;
/* 3879 */         out.write("\n\t\t\t\t\t");
/* 3880 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3881 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3885 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3886 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3887 */       return true;
/*      */     }
/* 3889 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f13(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3895 */     PageContext pageContext = _jspx_page_context;
/* 3896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3898 */     SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3899 */     _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 3900 */     _jspx_th_c_005fset_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 3902 */     _jspx_th_c_005fset_005f13.setTarget("${AMActionForm}");
/*      */     
/* 3904 */     _jspx_th_c_005fset_005f13.setProperty("consecutive_clearpolls");
/*      */     
/* 3906 */     _jspx_th_c_005fset_005f13.setValue("${row[10]}");
/* 3907 */     int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 3908 */     if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 3909 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f13);
/* 3910 */       return true;
/*      */     }
/* 3912 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f13);
/* 3913 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3918 */     PageContext pageContext = _jspx_page_context;
/* 3919 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3921 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3922 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 3923 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3925 */     _jspx_th_html_005ftext_005f7.setProperty("consecutive_clearpolls");
/*      */     
/* 3927 */     _jspx_th_html_005ftext_005f7.setSize("2");
/*      */     
/* 3929 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext");
/*      */     
/* 3931 */     _jspx_th_html_005ftext_005f7.setMaxlength("2");
/* 3932 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 3933 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 3934 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3935 */       return true;
/*      */     }
/* 3937 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3943 */     PageContext pageContext = _jspx_page_context;
/* 3944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3946 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3947 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3948 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3950 */     _jspx_th_c_005fif_005f0.setTest("${param.frompopupNewThreshold=='true'}");
/* 3951 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3952 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3954 */         out.write("\n\t\t<input name=\"popupNewThreshold\" type=\"hidden\" value=\"true\">\n\t\t");
/* 3955 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3956 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3960 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3961 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3962 */       return true;
/*      */     }
/* 3964 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3965 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Iframe_005fThresholdDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */