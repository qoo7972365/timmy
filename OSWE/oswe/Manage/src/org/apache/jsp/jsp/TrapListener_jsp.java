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
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class TrapListener_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2206 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2210 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2230 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2234 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2235 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2237 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2243 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2259 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2262 */     JspWriter out = null;
/* 2263 */     Object page = this;
/* 2264 */     JspWriter _jspx_out = null;
/* 2265 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2269 */       response.setContentType("text/html;charset=UTF-8");
/* 2270 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2272 */       _jspx_page_context = pageContext;
/* 2273 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2274 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2275 */       session = pageContext.getSession();
/* 2276 */       out = pageContext.getOut();
/* 2277 */       _jspx_out = out;
/*      */       
/* 2279 */       out.write("<!DOCTYPE html>\n");
/* 2280 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2281 */       out.write(10);
/*      */       
/* 2283 */       request.setAttribute("HelpKey", "SNMP Trap Listener");
/*      */       
/* 2285 */       out.write("\n\n\n\n\n\n\n\n");
/* 2286 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2288 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2289 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2291 */       out.write(10);
/* 2292 */       out.write(10);
/* 2293 */       out.write(10);
/* 2294 */       out.write(10);
/* 2295 */       out.write(10);
/*      */       
/* 2297 */       String path = (String)session.getAttribute("mibNodesToOpen");
/* 2298 */       String oid = (String)session.getAttribute("mibNodeToSelect");
/*      */       
/* 2300 */       out.write(10);
/*      */       
/* 2302 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2303 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2304 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2306 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/NewAdminLayout.jsp");
/* 2307 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2308 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2310 */           out.write("\n\n<script>\n\nfunction showTrapThresholdDetail(id)\n{\n\n\tif(id=='Reset')\n\t{\n\t\tdocument.getElementById(\"thresholddetail\").style.display='none';\n\t\treturn;\n\t}\n\n\telse if(id == '1' || id == '4' || id == '5'){\n\n\t\t document.getElementById(\"thresholddetail\").style.display='none';\n\t\t\tif(document.getElementById(\"singleaction\") != null){\n\t\t\tdocument.getElementById(\"singleaction\").style.display='block';\n\t\t\t}\n\t\t\tif( document.getElementById(\"multipleaction\") != null){\n\t\t\tdocument.getElementById(\"multipleaction\").style.display='none';\n\t\t\t}\n\n                return;\n\n\n\t}else{\n        var url = '/jsp/ThresholdProfile.jsp?thresholdid='+id; //No I18N\n        http.open(\"GET\",url,true);\n        http.onreadystatechange = handleTrapThresholdDetail;\n        http.send(null);\n\t\t\tif(document.getElementById(\"singleaction\") != null){\n\t\t document.getElementById(\"singleaction\").style.display='none';\n\t\t}\n\t\tif( document.getElementById(\"multipleaction\") != null){\n\n        document.getElementById(\"multipleaction\").style.display='block';\n\n\t\t}\n\n\t\t}\n\n\n");
/* 2311 */           out.write("}\n\nfunction handleTrapThresholdDetail()\n{\n        if(http.readyState == 4)\n        {\n                var result = http.responseText;\n                document.getElementById(\"thresholddetail\").innerHTML = result;\n                document.getElementById(\"thresholddetail\").style.display='block';\n        }\n}\n\nfunction setVal(val)\n{\n\tdocument.AMActionForm.enterpriseOID.value=val;\n\tdocument.AMActionForm.trapOID.value=val;\n\n}\nfunction openMibBrowserWin(url)\n{\n\tvar win=window.open(url,'','resizable=yes,scrollbars=yes,width=1000,height=550');\n\twin.focus();\n}\nfunction validateandsubmit()\n{\n");
/* 2312 */           if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2314 */           out.write(10);
/* 2315 */           if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2317 */           out.write("\n}\n\nfunction showVersion(version)\n{\n    if(version == 'v1')\n    {\n        document.getElementById(\"v1\").style.display='block';\n        document.getElementById(\"v2c\").style.display='none';\n        //document.getElementById(\"v3\").style.display='none';\n        document.getElementById(\"v3UserNameDiv\").style.display='none';\n\n\n\n    }\n    if(version == 'v2c')\n    {\n        document.getElementById(\"v1\").style.display='none';\n        document.getElementById(\"v2c\").style.display='block';\n        //document.getElementById(\"v3\").style.display='none';\n        document.getElementById(\"v3UserNameDiv\").style.display='none';\n\n    }\n    if(version == 'v3')\n    {\n        document.getElementById(\"v1\").style.display='none';\n        document.getElementById(\"v2c\").style.display='block';\n        //document.getElementById(\"v3\").style.display='block';\n        document.getElementById(\"v3UserNameDiv\").style.display='block';\n    }\n\n}\nfunction onComboChange(obj)\n{\n\tif(obj.value=='6')\n\t{\n\t\tjavascript:showDiv('specifictype');\n\t}\n\telse\n\t{\n\t\tjavascript:hideDiv('specifictype');\n");
/* 2318 */           out.write("\t}\n}\n\nfunction onCustomizeMsgCheckBoxChange(obj)\n{\n\tif(obj.checked)\n\t{\n\t\tdocument.getElementById(\"custMessageVarbindTr\").style.display='';\n\t}\n\telse\n\t{\n\t\tdocument.getElementById(\"custMessageVarbindTr\").style.display='none';\n\t}\n}\n\nfunction onCheckBoxChange(obj)\n{\n\tif(obj.checked)\n\t{\n\t\tjavascript:hideDiv('hostname');\n\t}\n\telse\n\t{\n\t\tjavascript:showDiv('hostname');\n\t}\n}\nfunction selectAllList(checkbox,optionbox)\n{\n    var flag = checkbox.checked ;\n    selectDeselect( optionbox, flag );\n}\nfunction selectDeselect( component, flag )\n{\n    for(var i=0; i<component.options.length; i++)\n    {\n        component.options[i].selected = flag;\n    }\n}\n\n\n\nfunction myOnLoad()\n{\n\t//document.getElementById(\"thresholddetail\").style.display='none';\n\tvar sv = '");
/* 2319 */           out.print(request.getAttribute("thresholdId"));
/* 2320 */           out.write("';\n\tif (sv == \"null\") {\n\t\tsv = 1;\n\t} \n\tdocument.getElementById(\"thresholdlistid\").value = sv;\n\tif(document.AMActionForm.trapVersion[0].checked)\n\t{\n\t\tjavascript:showDiv('v1'),hideDiv('v2c');\n\t}\n\telse if (document.AMActionForm.trapVersion[1].checked)\n\t{\n\t\tjavascript:showDiv('v2c'),hideDiv('v1');\n\t}\n\telse if (document.AMActionForm.trapVersion[2].checked)\n\t{\n\t\tjavascript:showDiv('v2c'),showDiv('v3UserNameDiv'),hideDiv('v1');\n\t}\n\tif(document.AMActionForm.trapType.value=='6')\n\t{\n\t\tjavascript:showDiv('specifictype');\n\t}\n\tif(document.AMActionForm.trapType.value=='6')\n\t{\n\t\tjavascript:showDiv('specifictype');\n\t}\n\tif(!document.AMActionForm.chkTrapHost.checked)\n\t{\n\t\tjavascript:showDiv('hostname');\n\t}\n\tonCustomizeMsgCheckBoxChange(document.AMActionForm.chkCustomizeVarbinds);\n\t\n\t\tif(document.AMActionForm.thresholdList.value == '1' || document.AMActionForm.thresholdList.value == '4' || document.AMActionForm.thresholdList.value == '5'){\n\tdocument.getElementById(\"singleaction\").style.display='block';\n\tdocument.getElementById(\"multipleaction\").style.display='none';\n");
/* 2321 */           out.write("\t}else{\n\n\tdocument.getElementById(\"singleaction\").style.display='none';\n\tdocument.getElementById(\"multipleaction\").style.display='block';\n\n\t}\n\n}\n\nfunction submit()\n{\n\tdocument.AMActionForm.submit;\n}\n</script>\n\n");
/*      */           
/* 2323 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2324 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2325 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2327 */           _jspx_th_tiles_005fput_005f0.setName("UserArea");
/*      */           
/* 2329 */           _jspx_th_tiles_005fput_005f0.setType("string");
/* 2330 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2331 */           if (_jspx_eval_tiles_005fput_005f0 != 0) {
/* 2332 */             if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 2333 */               out = _jspx_page_context.pushBody();
/* 2334 */               _jspx_th_tiles_005fput_005f0.setBodyContent((BodyContent)out);
/* 2335 */               _jspx_th_tiles_005fput_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2338 */               out.write("\n\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\">\n\t<tr>\n\t");
/*      */               
/* 2340 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2341 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2342 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f0);
/* 2343 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2344 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 2346 */                   out.write(10);
/* 2347 */                   out.write(9);
/* 2348 */                   out.write(9);
/*      */                   
/* 2350 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2351 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2352 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/* 2354 */                   _jspx_th_c_005fwhen_005f0.setTest("${(! empty param.edit && param.edit=='true') || (! empty param.resourceid)}");
/* 2355 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2356 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/* 2358 */                       out.write("\n\t\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2359 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2360 */                       out.write(" &gt;<a href=\"/adminAction.do?method=listTrapListener\" class=\"bcinactive\"> ");
/* 2361 */                       out.print(FormatUtil.getString("am.webclient.traplistener.viewtrap"));
/* 2362 */                       out.write("</a> &gt;<span class=\"bcactive\"> ");
/* 2363 */                       out.print(FormatUtil.getString("am.webclient.traplistener.editbreadcrumb"));
/* 2364 */                       out.write("</span></td>\n\t\t");
/* 2365 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2366 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2370 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2371 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/* 2374 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2375 */                   out.write(10);
/* 2376 */                   out.write(9);
/* 2377 */                   out.write(9);
/*      */                   
/* 2379 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2380 */                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2381 */                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2382 */                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2383 */                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                     for (;;) {
/* 2385 */                       out.write("\n\t\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2386 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2387 */                       out.write(" &gt;<span class=\"bcactive\"> ");
/* 2388 */                       out.print(FormatUtil.getString("am.webclient.traplistener.addbreadcrumb"));
/* 2389 */                       out.write("</span></td>\n\t\t");
/* 2390 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2391 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2395 */                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2396 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                   }
/*      */                   
/* 2399 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2400 */                   out.write(10);
/* 2401 */                   out.write(9);
/* 2402 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2403 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2407 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2408 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 2411 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2412 */               out.write("\n\t</tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n");
/*      */               
/* 2414 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2415 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2416 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f0);
/*      */               
/* 2418 */               _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */               
/* 2420 */               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2421 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2422 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 2424 */                   out.write(10);
/*      */                   
/* 2426 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2427 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2428 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_html_005fform_005f0);
/* 2429 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2430 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/* 2432 */                       out.write(10);
/* 2433 */                       out.write(9);
/*      */                       
/* 2435 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2436 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2437 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/* 2439 */                       _jspx_th_c_005fwhen_005f1.setTest("${! empty param.edit && param.edit=='true' }");
/* 2440 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2441 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/* 2443 */                           out.write("\n\t\t<input name=\"method\" type=\"hidden\" value=\"updateTrapListener\">\n\t\t<input name=\"trapid\" type=\"hidden\" value=\"");
/* 2444 */                           out.print(request.getParameter("trapid"));
/* 2445 */                           out.write("\">\n\t");
/* 2446 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2447 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2451 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2452 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/* 2455 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2456 */                       out.write(10);
/* 2457 */                       out.write(9);
/*      */                       
/* 2459 */                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2460 */                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2461 */                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/* 2463 */                       _jspx_th_c_005fwhen_005f2.setTest("${! empty param.resourceid }");
/* 2464 */                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2465 */                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                         for (;;) {
/* 2467 */                           out.write("\n\t\t<input name=\"method\" type=\"hidden\" value=\"updateTrapListener\">\n\t\t<input name=\"trapid\" type=\"hidden\" value=\"");
/* 2468 */                           out.print(request.getParameter("resourceid"));
/* 2469 */                           out.write("\">\n\t");
/* 2470 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2471 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2475 */                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2476 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                       }
/*      */                       
/* 2479 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2480 */                       out.write(10);
/* 2481 */                       out.write(9);
/* 2482 */                       if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/*      */                         return;
/* 2484 */                       out.write(10);
/* 2485 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2486 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2490 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2491 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/* 2494 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2495 */                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n    <tr>\n      <td height=\"31\" class=\"tableheading\"> ");
/* 2496 */                   out.print(FormatUtil.getString("am.webclient.traplistener.traplistenerdetails"));
/* 2497 */                   out.write("</td>\n    </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n\t  <td class=\"bodytext label-align\" width=\"25%\"> ");
/* 2498 */                   out.print(FormatUtil.getString("am.webclient.traplistener.traplistenername"));
/* 2499 */                   out.write("<span class=\"mandatory\">*</span></td>\n\t  <td width=\"75%\">");
/* 2500 */                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2502 */                   out.write("&nbsp;<span class=\"bodytext\">* ");
/* 2503 */                   out.print(FormatUtil.getString("am.webclient.traplistener.trapporthelpnote", new String[] { System.getProperty("traplistener.port") }));
/* 2504 */                   out.write("&nbsp;</td>\n\t</tr>\n\t<tr>\n\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 2505 */                   out.print(FormatUtil.getString("am.webclient.traplistener.status"));
/* 2506 */                   out.write("</td>\n\t  <td class=\"bodytext\" width=\"75%\" valign=\"middle\">");
/* 2507 */                   if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2509 */                   out.write("&nbsp;");
/* 2510 */                   out.print(FormatUtil.getString("am.webclient.traplistener.enable"));
/* 2511 */                   out.write(" &nbsp;&nbsp;");
/* 2512 */                   if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2514 */                   out.write("&nbsp;");
/* 2515 */                   out.print(FormatUtil.getString("am.webclient.traplistener.disable"));
/* 2516 */                   out.write(" </td>\n\t</tr>\n\t<tr>\n\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 2517 */                   out.print(FormatUtil.getString("am.webclient.traplistener.snmpversion"));
/* 2518 */                   out.write("</td>\n\t  <td class=\"bodytext\" width=\"75%\" valign=\"middle\">");
/* 2519 */                   if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2521 */                   out.write("&nbsp;");
/* 2522 */                   out.print(FormatUtil.getString("am.webclient.traplistener.v1"));
/* 2523 */                   out.write(" &nbsp;&nbsp;");
/* 2524 */                   if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2526 */                   out.write("&nbsp;");
/* 2527 */                   out.print(FormatUtil.getString("am.webclient.traplistener.v2c"));
/* 2528 */                   out.write("&nbsp;&nbsp;");
/* 2529 */                   if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2531 */                   out.write("&nbsp;");
/* 2532 */                   out.print(FormatUtil.getString("v3"));
/* 2533 */                   out.write("  </td>\n\t</tr>\n\t<tr>\n\t  <td colspan=\"2\" width=\"100%\">\n\t  \t<div id='v1' style='display:inline'>\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n          <tr>\n\n            <td width=\"25%\" height=\"24\" class=\"bodytext label-align\">");
/* 2534 */                   out.print(FormatUtil.getString("am.webclient.traplistener.generictype"));
/* 2535 */                   out.write("</td>\n\t\t\t  <td class=\"bodytext\" width=\"75%\">\n\t\t\t\t");
/* 2536 */                   if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2538 */                   out.write("\n\t\t\t  </td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t  <td width=\"100%\" colspan=\"2\">\n\t\t\t  <div id=\"specifictype\" style=\"display:none\">\n\t\t\t  <table width=\"100%\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t  <tr>\n\t\t\t        <td width=\"25%\" height=\"24\" class=\"bodytext label-align\">");
/* 2539 */                   out.print(FormatUtil.getString("am.webclient.traplistener.specifictype"));
/* 2540 */                   out.write("</td>\n\t\t\t  <td class=\"bodytext\" width=\"75%\" valign=\"middle\">");
/* 2541 */                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2543 */                   out.write("&nbsp;&nbsp;");
/* 2544 */                   out.print(FormatUtil.getString("am.webclient.traplistener.specifictype.note"));
/* 2545 */                   out.write("</td>\n\n\t\t\t  </tr>\n\t\t\t  </table>\n\t\t\t  </div>\n\t\t\t  </td>\n\t\t\t</tr>\n\t\t\t");
/*      */                   
/* 2547 */                   String querString = "";
/* 2548 */                   if ((path != null) && (oid != null))
/*      */                   {
/* 2550 */                     querString = "?pressName=" + oid + "&pressType=Treelink&type=" + oid + "&nodesToOpen=" + java.net.URLEncoder.encode(path);
/*      */                   }
/*      */                   
/* 2553 */                   out.write("\n\t\t\t<tr>\n\n            <td width=\"25%\" height=\"24\" class=\"bodytext label-align\">");
/* 2554 */                   out.print(FormatUtil.getString("am.webclient.traplistener.enterpriseoid"));
/* 2555 */                   out.write("</td>\n\t\t\t  <td class=\"bodytext\" width=\"75%\" valign=\"middle\">");
/* 2556 */                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2558 */                   out.write("&nbsp;&nbsp;");
/* 2559 */                   out.print(FormatUtil.getString("am.webclient.traplistener.mibhelpnote"));
/* 2560 */                   out.write(" <a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"javascript:openMibBrowserWin('/jsp/MibBrowser.jsp");
/* 2561 */                   out.print(querString);
/* 2562 */                   out.write("')\">");
/* 2563 */                   out.print(FormatUtil.getString("am.webclient.newaction.trapmibnote2"));
/* 2564 */                   out.write("</a></td>\n\t\t\t</tr>\n\t\t</table>\n\t\t</div>\n\t\t<div id='v2c' style='display:none'>\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n          <tr>\n\n            <td width=\"25%\" height=\"26\" class=\"bodytext label-align\">");
/* 2565 */                   out.print(FormatUtil.getString("am.webclient.traplistener.trapoid"));
/* 2566 */                   out.write("<span class=\"mandatory\">*</span></td>\t\t\t  \n\t\t\t  <td class=\"bodytext\" width=\"75%\" valign=\"middle\">&nbsp;");
/* 2567 */                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2569 */                   out.write("&nbsp;&nbsp;");
/* 2570 */                   out.print(FormatUtil.getString("am.webclient.traplistener.mibhelpnote"));
/* 2571 */                   out.write(" <a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"javascript:openMibBrowserWin('/jsp/MibBrowser.jsp");
/* 2572 */                   out.print(querString);
/* 2573 */                   out.write("')\">");
/* 2574 */                   out.print(FormatUtil.getString("am.webclient.newaction.trapmibnote2"));
/* 2575 */                   out.write("</a></td>\n\t\t\t</tr>\n\t\t</table>\n\t\t</div>\n\t  </td>\n\t</tr>\n\n\t<!-- Customize Message (Varbinds)  section starts -->\n\t<tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\">&nbsp;</td>\n\t\t<td class=\"bodytext\" width=\"84%\">\n\t \t\t");
/* 2576 */                   if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2578 */                   out.print(FormatUtil.getString("am.webclient.traplistener.customize.trapmessage"));
/* 2579 */                   out.write("\n\t  \t</td>\n\t<tr>\n\n\t<tr id=\"custMessageVarbindTr\">\n\t\t<td class=\"bodytext label-align\">");
/* 2580 */                   out.print(FormatUtil.getString("am.webclient.traplistener.trapmessage"));
/* 2581 */                   out.write("</td>    \n\t\t<td class=\"bodytext\">\n\t\t\t<table>\n\t\t\t\t<tr>\n\t\t\t\t\t<td>");
/* 2582 */                   if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2584 */                   out.write("</td>\n\t\t\t\t\t<td class=\"align-top padd-lt5\">");
/* 2585 */                   out.print(FormatUtil.getString("am.webclient.traplistener.trapmessage.note"));
/* 2586 */                   out.write("<br/>");
/* 2587 */                   out.print(FormatUtil.getString("am.webclient.traplistener.trapmessage.format1"));
/* 2588 */                   out.write("<br/>");
/* 2589 */                   out.print(FormatUtil.getString("am.webclient.traplistener.trapmessage.format2"));
/* 2590 */                   out.write("<br/>");
/* 2591 */                   out.print(FormatUtil.getString("am.webclient.traplistener.trapmessage.format3"));
/* 2592 */                   out.write("<br/>");
/* 2593 */                   out.print(FormatUtil.getString("am.webclient.traplistener.trapmessage.format3.example"));
/* 2594 */                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>      \n\t</tr>\n\t<!-- Customize Message (Varbinds)  section ends -->\t  \n\n\n\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 2595 */                   out.print(FormatUtil.getString("am.webclient.traplistener.severity"));
/* 2596 */                   out.write("</td>\n\t  <!--td class=\"bodytext\" width=\"84%\">\n\t  \t");
/* 2597 */                   if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2599 */                   out.write("\n\t  </td-->\n\n\n<td width=\"75%\">\n");
/*      */                   
/* 2601 */                   boolean isStringAttribute = true;
/* 2602 */                   out.write("\n<select id=\"thresholdlistid\" name=\"thresholdList\" class=\"formtext normal\" onchange=\"showTrapThresholdDetail(this.value);\">\n<option value=\"1\">");
/* 2603 */                   out.print(FormatUtil.getString("Critical"));
/* 2604 */                   out.write("</option>\n<option value=\"4\">");
/* 2605 */                   out.print(FormatUtil.getString("Warning"));
/* 2606 */                   out.write("</option>\n<option value=\"5\">");
/* 2607 */                   out.print(FormatUtil.getString("Clear"));
/* 2608 */                   out.write("</option>\n <optgroup label=\"");
/* 2609 */                   out.print(FormatUtil.getString("am.webclient.configurealert.associatethreshold"));
/* 2610 */                   out.write("\"></optgroup>\n<option value=\"New");
/* 2611 */                   out.print(isStringAttribute);
/* 2612 */                   out.write("\" style=\"font-style:italic;\">-- ");
/* 2613 */                   out.print(FormatUtil.getString("am.webclient.toolbar.newthreshold.text"));
/* 2614 */                   out.write(" --</option>\n  ");
/*      */                   
/* 2616 */                   int thresholdID = -1;
/*      */                   try {
/* 2618 */                     if (request.getAttribute("thresholdId") == null)
/*      */                     {
/* 2620 */                       thresholdID = -1;
/*      */                     }
/*      */                     else
/*      */                     {
/* 2624 */                       thresholdID = Integer.parseInt((String)request.getAttribute("thresholdId"));
/*      */                     }
/*      */                   }
/*      */                   catch (Exception ex)
/*      */                   {
/* 2629 */                     ex.printStackTrace();
/*      */                   }
/*      */                   
/*      */                   try
/*      */                   {
/* 2634 */                     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 2635 */                     String tmpQ = "select ID,NAME from AM_THRESHOLDCONFIG where ID <> 1 AND ID <> 2 AND (DESCRIPTION <>'##Threshod for URL##') AND NAME NOT IN ('Marker THRESHOLD','Availability Threshold','Health Threshold')";
/* 2636 */                     if (isStringAttribute)
/*      */                     {
/* 2638 */                       tmpQ = tmpQ + " and TYPE=3";
/*      */                     }
/*      */                     else
/*      */                     {
/* 2642 */                       tmpQ = tmpQ + " and TYPE!=3";
/*      */                     }
/* 2644 */                     tmpQ = tmpQ + " order by NAME";
/* 2645 */                     ResultSet result = AMConnectionPool.executeQueryStmt(tmpQ);
/* 2646 */                     while (result.next())
/*      */                     {
/* 2648 */                       Vector v = new Vector();
/* 2649 */                       int id = result.getInt(1);
/* 2650 */                       String name = result.getString(2);
/*      */                       
/* 2652 */                       String selected = "";
/* 2653 */                       if (thresholdID == id)
/*      */                       {
/* 2655 */                         selected = "selected";
/*      */                       }
/* 2657 */                       String thresNameToBeSel = null;
/* 2658 */                       thresNameToBeSel = (String)request.getAttribute("displayName1");
/* 2659 */                       if ((selected.equals("")) && (thresNameToBeSel != null))
/*      */                       {
/* 2661 */                         if (name.equals(thresNameToBeSel))
/*      */                         {
/* 2663 */                           selected = "selected";
/*      */                         }
/*      */                       }
/*      */                       
/* 2667 */                       out.write("\n          <option value=\"");
/* 2668 */                       out.print(id);
/* 2669 */                       out.write(34);
/* 2670 */                       out.write(32);
/* 2671 */                       out.print(selected);
/* 2672 */                       out.write(62);
/* 2673 */                       out.print(FormatUtil.getString(name));
/* 2674 */                       out.write("</option>\n          ");
/*      */                     }
/*      */                     
/*      */ 
/* 2678 */                     result.close();
/*      */                   }
/*      */                   catch (Exception ex)
/*      */                   {
/* 2682 */                     ex.printStackTrace();
/*      */                   }
/*      */                   
/* 2685 */                   out.write("\n</select>\n</td>\n\n<tr>\n<td colspan=\"2\" width=\"25%\">\n<table border=\"0\" width=\"75%\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td >\n<div id=\"thresholddetail\">\n\t");
/*      */                   
/* 2687 */                   if (thresholdID != -1)
/*      */                   {
/*      */ 
/* 2690 */                     out.write("\n\t\t<script>\n\t\tshowTrapThresholdDetail(");
/* 2691 */                     out.print(thresholdID);
/* 2692 */                     out.write(");\n\t</script>\n\t\t");
/*      */                   }
/*      */                   
/*      */ 
/* 2696 */                   out.write("\n</div>\n</td>\n</tr>\n</table>\n</td>\n</tr>\n\n\n\t\t<tr>\n\t<tr>\n\t  <td width=\"25%\" class=\"bodytext label-align\">&nbsp;</td>\n\t  <td width=\"75%\" class=\"bodytext\" valign=\"middle\">");
/* 2697 */                   if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2699 */                   out.write("\n      ");
/* 2700 */                   out.print(FormatUtil.getString("am.webclient.traplistener.associatetrapseverity"));
/* 2701 */                   out.write("\n      </td>\n\t</tr>\n\n\n\t<tr>\n\t  <td width=\"25%\" class=\"bodytext label-align\">&nbsp;</td>\n\t  <td width=\"75%\" class=\"bodytext\" valign=\"middle\">");
/* 2702 */                   if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2704 */                   out.write("\n      ");
/* 2705 */                   out.print(FormatUtil.getString("am.webclient.traplistener.traphost"));
/* 2706 */                   out.write("\n      </td>\n\t</tr>\n\t<tr>\n\t  <td width=\"100%\" colspan=\"2\" class=\"cellpadd-none\">\n\t  <div id=\"hostname\" style=\"display:none\">\n\t  <table width=\"100%\" border=\"0\" cellpadding=\"5\">\n\t  <tr>\n\t\t  <td width=\"25%\" class=\"bodytext label-align\">");
/* 2707 */                   out.print(FormatUtil.getString("am.webclient.traplistener.hostname"));
/* 2708 */                   out.write("<span class=\"mandatory\">*</span></td>\n\t\t  <td width=\"75%\" class=\"bodytext\" valign=\"middle\">");
/* 2709 */                   if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2711 */                   out.write("</td>\n\t  </tr>\n\t  </table>\n\t  </div>\n          <div id=\"v3UserNameDiv\" style=\"display:none\">\n\t  <table width=\"100%\" border=\"0\" cellpadding=\"5\">\n\t  <tr>\n\t\t  <td width=\"25%\" class=\"bodytext label-align\">");
/* 2712 */                   out.print(FormatUtil.getString("UserName"));
/* 2713 */                   out.write("<span class=\"mandatory\">*</span></td>\n\t\t  <td width=\"75%\" class=\"bodytext\" valign=\"middle\">");
/* 2714 */                   if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2716 */                   out.write("</td>\n\t  </tr>\n\t  </table>\n          </div>\n\t  </td>\n\t</tr>\n\n<tr>\n\n</tr>\n\n\t<tr>\n<td colspan=\"5\">\n<div id=\"multipleaction\">\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n  <tr>\n      <td align=\"left\" valign=\"top\" class=\"bodytextbold dotteduline\" colspan=\"5\">");
/* 2717 */                   out.print(FormatUtil.getString("am.webclient.configurealert.associateactions"));
/* 2718 */                   out.write("</td>\n    </tr>\n    <tr>\n      <td align=\"center\" width=\"30%\" class=\"bodytext\">&nbsp;</td>\n      <td align=\"center\" width=\"20%\" class=\"bodytextbold\">");
/* 2719 */                   out.print(FormatUtil.getString("am.webclient.configurealert.availableactions"));
/* 2720 */                   out.write("</td>\n      <td align=\"center\" width=\"5%\" class=\"bodytext\">&nbsp;</td>\n      <td align=\"center\" width=\"20%\" class=\"bodytextbold\">");
/* 2721 */                   out.print(FormatUtil.getString("am.webclient.configurealert.associatedactions"));
/* 2722 */                   out.write("</td>\n      <td width=\"25%\" class=\"bodytext\">&nbsp;</td>\n    </tr>\n    <tr>\n      <td align=\"left\" valign=\"top\" width=\"30%\" class=\"bodytext\">\n        ");
/*      */                   
/* 2724 */                   out.println("&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.configurealert.thresholdcritical"));
/*      */                   
/*      */ 
/* 2727 */                   out.write("\n      </td>\n      <td width=\"20%\" class=\"bodytext\" align=\"left\"> <select STYLE=\"width:200px\" name=\"availableactions_critical\" size=\"8\" multiple class=\"formtextarea\" id=\"select2\">\n\t\t\t");
/* 2728 */                   out.print(getOptions("AM_ACTIONPROFILE.ID", "AM_ACTIONPROFILE.NAME,AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE", false, "left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID and AM_ATTRIBUTEACTIONMAPPER.ID =" + request.getParameter("trapid") + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =3200 and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=1 where  AM_ACTIONPROFILE.NAME !='Marker'and ACTIONID is null and AM_ACTIONPROFILE.NAME !='Restart The Service' and AM_ACTIONPROFILE.TYPE not in (18) order by AM_ACTIONPROFILE.NAME"));
/* 2729 */                   out.write(" </select></td>\n\n      <td align=\"center\" width=\"5%\" class=\"bodytext\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n          <tr>\n            <td align=\"left\"><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.availableactions_critical,document.AMActionForm.selectedactions_critical),fnRemoveFromRightCombo(document.AMActionForm.availableactions_critical);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n          </tr>\n          <tr>\n            <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n          </tr>\n          <tr>\n                                     <td align=\"left\"><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddAllFromCombo(document.AMActionForm.availableactions_critical,document.AMActionForm.selectedactions_critical),fnRemoveAllFromCombo(document.AMActionForm.availableactions_critical);\" value=\"&gt;&gt;\"></td>\n          </tr>\n          <tr>\n                <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n");
/* 2730 */                   out.write("          </tr>\n          <tr>\n            <td align=\"left\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.selectedactions_critical,document.AMActionForm.availableactions_critical),fnRemoveFromRightCombo(document.AMActionForm.selectedactions_critical);\" value=\"&nbsp;&lt;&nbsp;\"></td>\n          </tr>\n\t  <tr>\n               <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n          </tr>\n          <tr>\n                 <td align=\"left\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddAllFromCombo(document.AMActionForm.selectedactions_critical,document.AMActionForm.availableactions_critical),fnRemoveAllFromCombo(document.AMActionForm.selectedactions_critical);\" value=\"&lt;&lt;\"></td>\n          </tr>\n        </table></td>\n\n\n    <td width=\"20%\" class=\"bodytext\" align=\"left\"> <select STYLE=\"width:200px\" name=\"selectedactions_critical\" size=\"8\" multiple class=\"formtextarea\">\n  ");
/* 2731 */                   out.print(getOptions("AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE.NAME", "AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER", false, "where  AM_ATTRIBUTEACTIONMAPPER.ID=" + request.getParameter("trapid") + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =3200 and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=1 and AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID order by AM_ACTIONPROFILE.NAME"));
/* 2732 */                   out.write("\n\n</select></td>\n\n        <td width=\"25%\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n\t\t<tr>\n\t\t<td colspan=\"5\" class=\"advancedoption\">\n\t\t<table width=\"100%\" border=\"0\">\n\t\t<tr>\n\t\t</tr>\n\t\t</table>\n  <tr>\n      <td align=\"center\" width=\"30%\" class=\"bodytext\">&nbsp;</td>\n      <td align=\"center\" width=\"20%\" class=\"bodytextbold\">");
/* 2733 */                   out.print(FormatUtil.getString("am.webclient.configurealert.availableactions"));
/* 2734 */                   out.write("</td>\n      <td align=\"center\" width=\"5%\" class=\"bodytext\">&nbsp;</td>\n      <td align=\"center\" width=\"20%\" class=\"bodytextbold\">");
/* 2735 */                   out.print(FormatUtil.getString("am.webclient.configurealert.associatedactions"));
/* 2736 */                   out.write("</td>\n      <td width=\"25%\" class=\"bodytext\">&nbsp;</td>\n    </tr>\n    <tr>\n      <td align=\"left\" width=\"30%\" valign=\"top\" class=\"bodytext\">\n        ");
/*      */                   
/* 2738 */                   out.println("&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.configurealert.thresholdwarning"));
/*      */                   
/*      */ 
/* 2741 */                   out.write("\n      </td>\n      <td class=\"20%\" class=\"bodytext\" align=\"left\"> <select STYLE=\"width:200px\" name=\"availableactions_warning\" size=\"8\" multiple class=\"formtextarea\" id=\"select2\">\n\n   ");
/* 2742 */                   out.print(getOptions("AM_ACTIONPROFILE.ID", "AM_ACTIONPROFILE.NAME,AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE", false, "left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID and AM_ATTRIBUTEACTIONMAPPER.ID =" + request.getParameter("trapid") + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =3200 and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=4 where  AM_ACTIONPROFILE.NAME !='Marker'and ACTIONID is null and AM_ACTIONPROFILE.NAME !='Restart The Service' and AM_ACTIONPROFILE.TYPE not in (18) order by AM_ACTIONPROFILE.NAME"));
/* 2743 */                   out.write(" </select></td>\n      <td align=\"center\" width=\"5%\" class=\"bodytext\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n          <tr>\n            <td align=\"left\"><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.availableactions_warning,document.AMActionForm.selectedactions_warning),fnRemoveFromRightCombo(document.AMActionForm.availableactions_warning);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n          </tr>\n          <tr>\n            <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n          </tr>\n          <tr>\n                                     <td align=\"left\"><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddAllFromCombo(document.AMActionForm.availableactions_warning,document.AMActionForm.selectedactions_warning),fnRemoveAllFromCombo(document.AMActionForm.availableactions_warning);\" value=\"&gt;&gt;\"></td>\n          </tr>\n          <tr>\n                <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n");
/* 2744 */                   out.write("          </tr>\n          <tr>\n            <td align=\"left\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.selectedactions_warning,document.AMActionForm.availableactions_warning),fnRemoveFromRightCombo(document.AMActionForm.selectedactions_warning);\" value=\"&nbsp;&lt;&nbsp;\"></td>\n          </tr>\n\t  <tr>\n               <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n          </tr>\n          <tr>\n                 <td align=\"left\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddAllFromCombo(document.AMActionForm.selectedactions_warning,document.AMActionForm.availableactions_warning),fnRemoveAllFromCombo(document.AMActionForm.selectedactions_warning);\" value=\"&lt;&lt;\"></td>\n          </tr>\n        </table></td>\n\n\n    <td width=\"20%\" class=\"bodytext\" align=\"left\"> <select STYLE=\"width:200px\" name=\"selectedactions_warning\" size=\"8\" multiple class=\"formtextarea\">\n\n  ");
/* 2745 */                   out.print(getOptions("AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE.NAME", "AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER", false, "where  AM_ATTRIBUTEACTIONMAPPER.ID=" + request.getParameter("trapid") + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =3200 and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=4 and AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID order by AM_ACTIONPROFILE.NAME"));
/* 2746 */                   out.write("\n\n\n</select></td>\n        <td width=\"25%\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n\t\t<tr>\n\t\t<td colspan=\"5\" class=\"advancedoption\">\n\t\t<table width=\"100%\" border=\"0\">\n\t\t<tr>\n\t\t</tr>\n\t\t</table>\n  <tr>\n      <td align=\"center\" width=\"30%\" class=\"bodytext\">&nbsp;</td>\n      <td align=\"center\" width=\"20%\" class=\"bodytextbold\">");
/* 2747 */                   out.print(FormatUtil.getString("am.webclient.configurealert.availableactions"));
/* 2748 */                   out.write("</td>\n      <td align=\"center\" width=\"5%\" class=\"bodytext\">&nbsp;</td>\n      <td align=\"center\" width=\"20%\" class=\"bodytextbold\">");
/* 2749 */                   out.print(FormatUtil.getString("am.webclient.configurealert.associatedactions"));
/* 2750 */                   out.write("</td>\n      <td width=\"25%\" class=\"bodytext\">&nbsp;</td>\n    </tr>\n    <tr>\n      <td align=\"left\" width=\"30%\" valign=\"top\" class=\"bodytext\">\n        ");
/*      */                   
/* 2752 */                   out.println("&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.configurealert.thresholdclear"));
/*      */                   
/*      */ 
/* 2755 */                   out.write("\n      </td>\n      <td width=\"20%\" class=\"bodytext\" align=\"left\"> <select STYLE=\"width:200px\" name=\"availableactions_clear\" size=\"8\" multiple class=\"formtextarea\" id=\"select2\">\n\t   ");
/* 2756 */                   out.print(getOptions("AM_ACTIONPROFILE.ID", "AM_ACTIONPROFILE.NAME,AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE", false, "left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID and AM_ATTRIBUTEACTIONMAPPER.ID =" + request.getParameter("trapid") + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =3200 and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=5 where  AM_ACTIONPROFILE.NAME !='Marker'and ACTIONID is null and AM_ACTIONPROFILE.NAME !='Restart The Service' and  AM_ACTIONPROFILE.TYPE not in (18) order by AM_ACTIONPROFILE.NAME"));
/* 2757 */                   out.write(" </select></td>\n\n      <td align=\"center\" width=\"5%\" class=\"bodytext\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n          <tr>\n            <td align=\"left\"><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.availableactions_clear,document.AMActionForm.selectedactions_clear),fnRemoveFromRightCombo(document.AMActionForm.availableactions_clear);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n          </tr>\n          <tr>\n            <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n          </tr>\n          <tr>\n                                     <td align=\"left\"><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddAllFromCombo(document.AMActionForm.availableactions_clear,document.AMActionForm.selectedactions_clear),fnRemoveAllFromCombo(document.AMActionForm.availableactions_clear);\" value=\"&gt;&gt;\"></td>\n          </tr>\n          <tr>\n                <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n");
/* 2758 */                   out.write("          </tr>\n          <tr>\n            <td align=\"left\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.selectedactions_clear,document.AMActionForm.availableactions_clear),fnRemoveFromRightCombo(document.AMActionForm.selectedactions_clear);\" value=\"&nbsp;&lt;&nbsp;\"></td>\n          </tr>\n\t  <tr>\n               <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n          </tr>\n          <tr>\n                 <td align=\"left\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddAllFromCombo(document.AMActionForm.selectedactions_clear,document.AMActionForm.availableactions_clear),fnRemoveAllFromCombo(document.AMActionForm.selectedactions_clear);\" value=\"&lt;&lt;\"></td>\n          </tr>\n        </table></td>\n\n\n    <td width=\"20%\" class=\"bodytext\" align=\"left\"> <select STYLE=\"width:200px\" name=\"selectedactions_clear\" size=\"8\" multiple class=\"formtextarea\">\n\t  ");
/* 2759 */                   out.print(getOptions("AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE.NAME", "AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER", false, "where  AM_ATTRIBUTEACTIONMAPPER.ID=" + request.getParameter("trapid") + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =3200 and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=5 and AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID order by AM_ACTIONPROFILE.NAME"));
/* 2760 */                   out.write("\n\n        </select></td>\n        <td width=\"25%\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n\t\t<tr>\n\t\t<td colspan=\"5\" class=\"advancedoption\">\n\t\t<table width=\"100%\" border=\"0\">\n\t\t<tr>\n\t\t</tr>\n\t\t</table>\n\n</table>\n</div>\n</td>\n</tr>\n\n\t<tr>\n\t<td width=\"100%\" align=\"center\" colspan=\"2\">\n\t<div id=\"singleaction\">\n\t\t<table width=\"85%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t<td align=\"center\" class=\"bodytextbold\">");
/* 2761 */                   out.print(FormatUtil.getString("am.webclient.traplistener.availableactions"));
/* 2762 */                   out.write("</td>\n\t\t<td></td>\n\t\t<td align=\"center\" class=\"bodytextbold\">");
/* 2763 */                   out.print(FormatUtil.getString("am.webclient.traplistener.associatedactions"));
/* 2764 */                   out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\n\t\t <td width=\"46%\" align=\"left\"> <select STYLE=\"width:100%\" name=\"trapActionsCombo1\" size=\"8\" multiple >\n                        ");
/* 2765 */                   out.print(getOptions("AM_ACTIONPROFILE.ID", "AM_ACTIONPROFILE.NAME,AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE", false, "left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID and AM_ATTRIBUTEACTIONMAPPER.ID =" + request.getParameter("trapid") + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =3200  where  AM_ACTIONPROFILE.NAME !='Marker'and ACTIONID is null and AM_ACTIONPROFILE.NAME !='Restart The Service' and AM_ACTIONPROFILE.TYPE not in (18) order by AM_ACTIONPROFILE.NAME"));
/* 2766 */                   out.write(" </select></td>\n\n\n\t\t<td width=\"8%\" align=\"center\" class=\"bodytext\">\n\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"cenAlign\">\n\t\t\t  <tr>\n\t\t\t\t<td><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.trapActionsCombo1,document.AMActionForm.trapActionsCombo2),fnRemoveFromRightCombo(document.AMActionForm.trapActionsCombo1);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n\t\t\t  </tr>\n\t\t\t  <tr>\n\t\t\t\t<td><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.trapActionsCombo1),frmSelectAllIncludingEmpty(document.AMActionForm.trapActionsCombo1),fnAddToRightCombo(document.AMActionForm.trapActionsCombo1,document.AMActionForm.trapActionsCombo2),fnRemoveFromRightCombo(document.AMActionForm.trapActionsCombo1);\" value=\"&gt;&gt;\"></td>\n\t\t\t  </tr>\n\t\t\t  <tr>\n\t\t\t\t<td><img src=\"../images/spacer.gif\" height=\"6\" width=\"5\"></td>\n\t\t\t  </tr>\n\t\t\t  <tr>\n\t\t\t\t<td><input name=\"RemoveButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.trapActionsCombo2),fnAddToRightCombo(document.AMActionForm.trapActionsCombo2,document.AMActionForm.trapActionsCombo1),fnRemoveFromRightCombo(document.AMActionForm.trapActionsCombo2);\" value=\"&lt;&lt;\"></td>\n");
/* 2767 */                   out.write("\t\t\t  </tr>\n\t\t\t  <tr>\n\t\t\t\t<td><input name=\"RemoveButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.trapActionsCombo2,document.AMActionForm.trapActionsCombo1),fnRemoveFromRightCombo(document.AMActionForm.trapActionsCombo2);\" value=\"&nbsp;&lt;&nbsp;\"></td>\n\t\t\t  </tr>\n\t\t\t</table>\n\t\t</td>\n\n\t\t <td width=\"46%\" align=\"left\"> <select STYLE=\"width:100%\" name=\"trapActionsCombo2\" size=\"8\" multiple >\n");
/* 2768 */                   out.print(getOptions("AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE.NAME", "AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER", false, "where  AM_ATTRIBUTEACTIONMAPPER.ID=" + request.getParameter("trapid") + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =3200 and  AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID order by AM_ACTIONPROFILE.NAME"));
/* 2769 */                   out.write("\n\n\t\t</tr>\n\t\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" style=\"height:32px;\">\n\t<tr class=\"tablebottom\">\n\t<td align=\"center\" width=\"47%\">\n\t\t<input name=\"save\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:validateandsubmit();\" value=\"");
/* 2770 */                   out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 2771 */                   out.write("\">&nbsp;&nbsp;\n\n\n\t\t<input name=\"reset\" type=\"reset\" class=\"buttons btn_reset\" value=\"");
/* 2772 */                   out.print(FormatUtil.getString("am.webclient.common.reset.text"));
/* 2773 */                   out.write("\" onclick=\"javascript:showDiv('v1'),hideDiv('v2c'),hideDiv('specifictype');\">&nbsp;&nbsp;\n\n\n\t\t<input name=\"cancel\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2774 */                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2775 */                   out.write("\" onclick='javascript:window.location.href=\"/adminAction.do?method=listTrapListener\"'>\n\t</td>\n\t</tr>\n\t</table>\n");
/* 2776 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2777 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2781 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2782 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 2785 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2786 */               out.write("\n</td>\n<td width=\"30%\" valign=\"top\">\n\t\n\t");
/* 2787 */               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.snmptraplistener.helpcard")), request.getCharacterEncoding()), out, false);
/* 2788 */               out.write("\n\t \n\t</td>\n</tr>\n</table>\n");
/* 2789 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f0.doAfterBody();
/* 2790 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2793 */             if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 2794 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2797 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2798 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2801 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0);
/* 2802 */           out.write(10);
/* 2803 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2805 */           out.write("\n    ");
/* 2806 */           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2808 */           out.write(10);
/* 2809 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2810 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2814 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2815 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 2818 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2819 */         out.write(10);
/*      */       }
/* 2821 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2822 */         out = _jspx_out;
/* 2823 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2824 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2825 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2828 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2834 */     PageContext pageContext = _jspx_page_context;
/* 2835 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2837 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2838 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2839 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2841 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2842 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2843 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2845 */         out.write("\nalertUser();\n");
/* 2846 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2847 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2851 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2852 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2853 */       return true;
/*      */     }
/* 2855 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2861 */     PageContext pageContext = _jspx_page_context;
/* 2862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2864 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2865 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2866 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2868 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2869 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2870 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 2872 */         out.write("\n\n\tif((trimAll(document.AMActionForm.trapName.value)==''))\n\t{\n\t\tif(trimAll(document.AMActionForm.trapName.value)=='')\n\t\t{\n\t\t\talert(\"");
/* 2873 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2874 */           return true;
/* 2875 */         out.write("\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\talert(\"");
/* 2876 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2877 */           return true;
/* 2878 */         out.write("\");\n\t\t}\n\t\tdocument.AMActionForm.trapName.select();\n\t\treturn;\n\t}\n\tif(document.AMActionForm.trapVersion[1].checked)\n\t{\n\t\tif(trimAll(document.AMActionForm.trapOID.value)=='')\n\t\t{\n\t\t\talert(\"");
/* 2879 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2880 */           return true;
/* 2881 */         out.write("\");\n\t\t\tdocument.AMActionForm.trapOID.select();\n\t\t\treturn;\n\t\t}\n\t}\n\tif(!document.AMActionForm.chkTrapHost.checked)\n\t{\n\t\tif((trimAll(document.AMActionForm.trapHost.value)=='')||(!checkBA(document.AMActionForm.trapHost.value)))\n\t\t{\n\t\t\tif(trimAll(document.AMActionForm.trapHost.value)=='')\n\t\t\t{\n\t\t\t\talert(\"");
/* 2882 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2883 */           return true;
/* 2884 */         out.write("\");\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\talert(\"");
/* 2885 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2886 */           return true;
/* 2887 */         out.write("\");\n\t\t\t}\n\t\t\tdocument.AMActionForm.trapHost.select();\n\t\t\treturn;\n\t\t}\n \t}\n\tif(document.AMActionForm.chkCustomizeVarbinds.checked)\n\t{\n\t\tvar myValue = document.AMActionForm.trapCustomVarbinds.value;\n\t\tif(trimAll(myValue)=='')\n\t\t{\n\t\t\talert(\"");
/* 2888 */         if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2889 */           return true;
/* 2890 */         out.write("\");//No I18N\n\t\t\tdocument.AMActionForm.trapCustomVarbinds.select();\n\t\t\treturn;\n\t\t}\n \t}\n\tif(document.AMActionForm.trapVersion[2].checked)\n        {\n\t\tif(trimAll(document.AMActionForm.trapOID.value)=='')\n\t\t{\n\t\t\talert(\"");
/* 2891 */         if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2892 */           return true;
/* 2893 */         out.write("\");\t//No I18N\n\t\t\tdocument.AMActionForm.trapOID.select();\n\t\t\treturn;\n\t\t}\n            if(trimAll(document.AMActionForm.v3UserName.value) == '')\n            {\n                //alert(\"");
/* 2894 */         if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2895 */           return true;
/* 2896 */         out.write("\");\n                alert(\"");
/* 2897 */         if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2898 */           return true;
/* 2899 */         out.write("\"); //No I18N\n                document.AMActionForm.v3UserName.select();\n                return;\n            }\n\n        }\n\tif(document.AMActionForm.trapType.value=='6')\n\t{\n\t\tif(!isInteger(document.AMActionForm.specificType.value) && (trimAll(document.AMActionForm.specificType.value)!='*'))\n\t\t{\n\n\t\t\talert(\"");
/* 2900 */         if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2901 */           return true;
/* 2902 */         out.write("\");\n\t\t\treturn;\n\t\t}\n\t\telse if((isInteger(document.AMActionForm.specificType.value))&&(!isPositiveInteger(document.AMActionForm.specificType.value)))\n\t\t{\n\t\t\talert(\"");
/* 2903 */         if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2904 */           return true;
/* 2905 */         out.write("\");\n\t\t\treturn;\n\t\t}\n \t}\n\n\tif(document.AMActionForm.thresholdList.value == 1 || document.AMActionForm.thresholdList.value == 4 || document.AMActionForm.thresholdList.value == 5){\n\n\tfrmSelectAllIncludingEmpty(document.AMActionForm.trapActionsCombo1);\n\tfrmSelectAllIncludingEmpty(document.AMActionForm.trapActionsCombo2);\n\n}else{\n\tfrmSelectAllIncludingEmpty(document.AMActionForm.selectedactions_critical);\n\tfrmSelectAllIncludingEmpty(document.AMActionForm.selectedactions_warning);\n\tfrmSelectAllIncludingEmpty(document.AMActionForm.selectedactions_clear);\n}\n\n\tdocument.AMActionForm.submit();\n");
/* 2906 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2907 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2911 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2912 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2913 */       return true;
/*      */     }
/* 2915 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2916 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2921 */     PageContext pageContext = _jspx_page_context;
/* 2922 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2924 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2925 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2926 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 2927 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2928 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 2929 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2930 */         out = _jspx_page_context.pushBody();
/* 2931 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 2932 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2935 */         out.write("am.webclient.traplistener.emptyname");
/* 2936 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 2937 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2940 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2941 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2944 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2945 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2946 */       return true;
/*      */     }
/* 2948 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2949 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2954 */     PageContext pageContext = _jspx_page_context;
/* 2955 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2957 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2958 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2959 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 2960 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2961 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 2962 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2963 */         out = _jspx_page_context.pushBody();
/* 2964 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 2965 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2968 */         out.write("am.webclient.traplistener.validname");
/* 2969 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 2970 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2973 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2974 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2977 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2978 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2979 */       return true;
/*      */     }
/* 2981 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2987 */     PageContext pageContext = _jspx_page_context;
/* 2988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2990 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2991 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 2992 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 2993 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 2994 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 2995 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 2996 */         out = _jspx_page_context.pushBody();
/* 2997 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 2998 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3001 */         out.write("am.webclient.traplistener.emptyoid");
/* 3002 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3003 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3006 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3007 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3010 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3011 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3012 */       return true;
/*      */     }
/* 3014 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3020 */     PageContext pageContext = _jspx_page_context;
/* 3021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3023 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3024 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3025 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3026 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3027 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 3028 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3029 */         out = _jspx_page_context.pushBody();
/* 3030 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 3031 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3034 */         out.write("am.webclient.traplistener.emptyhostname");
/* 3035 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3036 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3039 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3040 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3043 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3044 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3045 */       return true;
/*      */     }
/* 3047 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3053 */     PageContext pageContext = _jspx_page_context;
/* 3054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3056 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3057 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3058 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3059 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3060 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3061 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3062 */         out = _jspx_page_context.pushBody();
/* 3063 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3064 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3067 */         out.write("am.webclient.traplistener.validname");
/* 3068 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3069 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3072 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3073 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3076 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3077 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3078 */       return true;
/*      */     }
/* 3080 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3081 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3086 */     PageContext pageContext = _jspx_page_context;
/* 3087 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3089 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3090 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3091 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3092 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3093 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 3094 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3095 */         out = _jspx_page_context.pushBody();
/* 3096 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 3097 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3100 */         out.write("am.webclient.traplistener.emptyvarbinds");
/* 3101 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 3102 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3105 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3106 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3109 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3110 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3111 */       return true;
/*      */     }
/* 3113 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3114 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3119 */     PageContext pageContext = _jspx_page_context;
/* 3120 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3122 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3123 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3124 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3125 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3126 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3127 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3128 */         out = _jspx_page_context.pushBody();
/* 3129 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 3130 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3133 */         out.write("am.webclient.traplistener.emptyoid");
/* 3134 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 3135 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3138 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3139 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3142 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3143 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3144 */       return true;
/*      */     }
/* 3146 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3147 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3152 */     PageContext pageContext = _jspx_page_context;
/* 3153 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3155 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3156 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3157 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3158 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3159 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 3160 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3161 */         out = _jspx_page_context.pushBody();
/* 3162 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 3163 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3166 */         out.write("Enter Username to filter the trap.");
/* 3167 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 3168 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3171 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3172 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3175 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3176 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3177 */       return true;
/*      */     }
/* 3179 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3185 */     PageContext pageContext = _jspx_page_context;
/* 3186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3188 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3189 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3190 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3191 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3192 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 3193 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3194 */         out = _jspx_page_context.pushBody();
/* 3195 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 3196 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3199 */         out.write("am.webclient.trapListener.validUserName");
/* 3200 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 3201 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3204 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3205 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3208 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3209 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3210 */       return true;
/*      */     }
/* 3212 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3213 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3218 */     PageContext pageContext = _jspx_page_context;
/* 3219 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3221 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3222 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3223 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3224 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3225 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 3226 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3227 */         out = _jspx_page_context.pushBody();
/* 3228 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 3229 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3232 */         out.write("am.webclient.traplistener.specifictype.integer.alert");
/* 3233 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 3234 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3237 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3238 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3241 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3242 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3243 */       return true;
/*      */     }
/* 3245 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3246 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3251 */     PageContext pageContext = _jspx_page_context;
/* 3252 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3254 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3255 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3256 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3257 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 3258 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 3259 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3260 */         out = _jspx_page_context.pushBody();
/* 3261 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 3262 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3265 */         out.write("am.webclient.traplistener.specifictype.positiveinteger.alert");
/* 3266 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 3267 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3270 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3271 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3274 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 3275 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3276 */       return true;
/*      */     }
/* 3278 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3279 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3284 */     PageContext pageContext = _jspx_page_context;
/* 3285 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3287 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3288 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3289 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 3290 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3291 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 3293 */         out.write("\n\t\t<input name=\"method\" type=\"hidden\" value=\"createTrapListener\">\n\t");
/* 3294 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3295 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3299 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3300 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3301 */       return true;
/*      */     }
/* 3303 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3304 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3309 */     PageContext pageContext = _jspx_page_context;
/* 3310 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3312 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3313 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3314 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3316 */     _jspx_th_html_005ftext_005f0.setProperty("trapName");
/*      */     
/* 3318 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/* 3320 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext large");
/*      */     
/* 3322 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 3323 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3324 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3325 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3326 */       return true;
/*      */     }
/* 3328 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3334 */     PageContext pageContext = _jspx_page_context;
/* 3335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3337 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3338 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 3339 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3341 */     _jspx_th_html_005fradio_005f0.setProperty("trapStatus");
/*      */     
/* 3343 */     _jspx_th_html_005fradio_005f0.setValue("enable");
/* 3344 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 3345 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 3346 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3347 */       return true;
/*      */     }
/* 3349 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3355 */     PageContext pageContext = _jspx_page_context;
/* 3356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3358 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3359 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 3360 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3362 */     _jspx_th_html_005fradio_005f1.setProperty("trapStatus");
/*      */     
/* 3364 */     _jspx_th_html_005fradio_005f1.setValue("disable");
/* 3365 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 3366 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 3367 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3368 */       return true;
/*      */     }
/* 3370 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3371 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3376 */     PageContext pageContext = _jspx_page_context;
/* 3377 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3379 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3380 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 3381 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3383 */     _jspx_th_html_005fradio_005f2.setProperty("trapVersion");
/*      */     
/* 3385 */     _jspx_th_html_005fradio_005f2.setValue("v1");
/*      */     
/* 3387 */     _jspx_th_html_005fradio_005f2.setOnclick("javascript:showVersion('v1');");
/* 3388 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 3389 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 3390 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 3391 */       return true;
/*      */     }
/* 3393 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 3394 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3399 */     PageContext pageContext = _jspx_page_context;
/* 3400 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3402 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3403 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 3404 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3406 */     _jspx_th_html_005fradio_005f3.setProperty("trapVersion");
/*      */     
/* 3408 */     _jspx_th_html_005fradio_005f3.setValue("v2c");
/*      */     
/* 3410 */     _jspx_th_html_005fradio_005f3.setOnclick("javascript:showVersion('v2c');");
/* 3411 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 3412 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 3413 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 3414 */       return true;
/*      */     }
/* 3416 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 3417 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3422 */     PageContext pageContext = _jspx_page_context;
/* 3423 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3425 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3426 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 3427 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3429 */     _jspx_th_html_005fradio_005f4.setProperty("trapVersion");
/*      */     
/* 3431 */     _jspx_th_html_005fradio_005f4.setValue("v3");
/*      */     
/* 3433 */     _jspx_th_html_005fradio_005f4.setOnclick("javascript:showVersion('v3');");
/* 3434 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 3435 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 3436 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 3437 */       return true;
/*      */     }
/* 3439 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 3440 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3445 */     PageContext pageContext = _jspx_page_context;
/* 3446 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3448 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 3449 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3450 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3452 */     _jspx_th_html_005fselect_005f0.setProperty("trapType");
/*      */     
/* 3454 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext normal");
/*      */     
/* 3456 */     _jspx_th_html_005fselect_005f0.setOnchange("javascript:onComboChange(this);");
/* 3457 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3458 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3459 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3460 */         out = _jspx_page_context.pushBody();
/* 3461 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3462 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3465 */         out.write("\n\t\t\t\t\t");
/* 3466 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 3467 */           return true;
/* 3468 */         out.write("\n\t\t\t\t");
/* 3469 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3470 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3473 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3474 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3477 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3478 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3479 */       return true;
/*      */     }
/* 3481 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3482 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3487 */     PageContext pageContext = _jspx_page_context;
/* 3488 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3490 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3491 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 3492 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 3494 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("genericType");
/* 3495 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 3496 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 3497 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3498 */       return true;
/*      */     }
/* 3500 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3501 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3506 */     PageContext pageContext = _jspx_page_context;
/* 3507 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3509 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3510 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3511 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3513 */     _jspx_th_html_005ftext_005f1.setProperty("specificType");
/*      */     
/* 3515 */     _jspx_th_html_005ftext_005f1.setSize("40");
/*      */     
/* 3517 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext large");
/*      */     
/* 3519 */     _jspx_th_html_005ftext_005f1.setMaxlength("50");
/* 3520 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3521 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3522 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3523 */       return true;
/*      */     }
/* 3525 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3531 */     PageContext pageContext = _jspx_page_context;
/* 3532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3534 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3535 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3536 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3538 */     _jspx_th_html_005ftext_005f2.setProperty("enterpriseOID");
/*      */     
/* 3540 */     _jspx_th_html_005ftext_005f2.setSize("40");
/*      */     
/* 3542 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext large");
/*      */     
/* 3544 */     _jspx_th_html_005ftext_005f2.setMaxlength("50");
/* 3545 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3546 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3547 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3548 */       return true;
/*      */     }
/* 3550 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3551 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3556 */     PageContext pageContext = _jspx_page_context;
/* 3557 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3559 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3560 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 3561 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3563 */     _jspx_th_html_005ftext_005f3.setProperty("trapOID");
/*      */     
/* 3565 */     _jspx_th_html_005ftext_005f3.setSize("40");
/*      */     
/* 3567 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext large");
/*      */     
/* 3569 */     _jspx_th_html_005ftext_005f3.setMaxlength("50");
/* 3570 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 3571 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 3572 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3573 */       return true;
/*      */     }
/* 3575 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3581 */     PageContext pageContext = _jspx_page_context;
/* 3582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3584 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 3585 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 3586 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3588 */     _jspx_th_html_005fcheckbox_005f0.setProperty("chkCustomizeVarbinds");
/*      */     
/* 3590 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("javascript:onCustomizeMsgCheckBoxChange(this);");
/* 3591 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 3592 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 3593 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3594 */       return true;
/*      */     }
/* 3596 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3602 */     PageContext pageContext = _jspx_page_context;
/* 3603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3605 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody.get(TextareaTag.class);
/* 3606 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 3607 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3609 */     _jspx_th_html_005ftextarea_005f0.setProperty("trapCustomVarbinds");
/*      */     
/* 3611 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea large");
/*      */     
/* 3613 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/* 3614 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 3615 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 3616 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 3617 */       return true;
/*      */     }
/* 3619 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 3620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3625 */     PageContext pageContext = _jspx_page_context;
/* 3626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3628 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3629 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3630 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3632 */     _jspx_th_html_005fselect_005f1.setProperty("severity");
/*      */     
/* 3634 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/* 3635 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3636 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3637 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3638 */         out = _jspx_page_context.pushBody();
/* 3639 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 3640 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3643 */         out.write("\n\t\t\t");
/* 3644 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 3645 */           return true;
/* 3646 */         out.write("\n\t  \t");
/* 3647 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3648 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3651 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3652 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3655 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3656 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 3657 */       return true;
/*      */     }
/* 3659 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 3660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3665 */     PageContext pageContext = _jspx_page_context;
/* 3666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3668 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3669 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 3670 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 3672 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("alertSeverity");
/* 3673 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 3674 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 3675 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3676 */       return true;
/*      */     }
/* 3678 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3684 */     PageContext pageContext = _jspx_page_context;
/* 3685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3687 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 3688 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 3689 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3691 */     _jspx_th_html_005fcheckbox_005f1.setProperty("chkAssociateTrapSeverity");
/* 3692 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 3693 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 3694 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 3695 */       return true;
/*      */     }
/* 3697 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 3698 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3703 */     PageContext pageContext = _jspx_page_context;
/* 3704 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3706 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 3707 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 3708 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3710 */     _jspx_th_html_005fcheckbox_005f2.setProperty("chkTrapHost");
/*      */     
/* 3712 */     _jspx_th_html_005fcheckbox_005f2.setOnclick("javascript:onCheckBoxChange(this);");
/* 3713 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 3714 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 3715 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 3716 */       return true;
/*      */     }
/* 3718 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 3719 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3724 */     PageContext pageContext = _jspx_page_context;
/* 3725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3727 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3728 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 3729 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3731 */     _jspx_th_html_005ftext_005f4.setProperty("trapHost");
/*      */     
/* 3733 */     _jspx_th_html_005ftext_005f4.setSize("40");
/*      */     
/* 3735 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext large");
/*      */     
/* 3737 */     _jspx_th_html_005ftext_005f4.setMaxlength("100");
/* 3738 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 3739 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 3740 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3741 */       return true;
/*      */     }
/* 3743 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3749 */     PageContext pageContext = _jspx_page_context;
/* 3750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3752 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3753 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 3754 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3756 */     _jspx_th_html_005ftext_005f5.setProperty("v3UserName");
/*      */     
/* 3758 */     _jspx_th_html_005ftext_005f5.setSize("40");
/*      */     
/* 3760 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext large");
/*      */     
/* 3762 */     _jspx_th_html_005ftext_005f5.setMaxlength("100");
/* 3763 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 3764 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 3765 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3766 */       return true;
/*      */     }
/* 3768 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3769 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3774 */     PageContext pageContext = _jspx_page_context;
/* 3775 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3777 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3778 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3779 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3781 */     _jspx_th_tiles_005fput_005f1.setName("HelpContent");
/*      */     
/* 3783 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/test.jsp");
/* 3784 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3785 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3786 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3787 */       return true;
/*      */     }
/* 3789 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3795 */     PageContext pageContext = _jspx_page_context;
/* 3796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3798 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3799 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3800 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3802 */     _jspx_th_tiles_005fput_005f2.setName("Footer");
/*      */     
/* 3804 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/footer.jsp");
/* 3805 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3806 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3807 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3808 */       return true;
/*      */     }
/* 3810 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3811 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\TrapListener_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */