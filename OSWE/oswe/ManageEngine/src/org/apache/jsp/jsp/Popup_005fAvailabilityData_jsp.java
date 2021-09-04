/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
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
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*      */ 
/*      */ public final class Popup_005fAvailabilityData_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session, HttpServletRequest request)
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
/* 2178 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2179 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2180 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2211 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2239 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2244 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/* 2256 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/* 2257 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2258 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2259 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2261 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2262 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2272 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/* 2275 */     JspWriter out = null;
/* 2276 */     Object page = this;
/* 2277 */     JspWriter _jspx_out = null;
/* 2278 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2282 */       response.setContentType("text/html;charset=UTF-8");
/* 2283 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2285 */       _jspx_page_context = pageContext;
/* 2286 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2287 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2288 */       session = pageContext.getSession();
/* 2289 */       out = pageContext.getOut();
/* 2290 */       _jspx_out = out;
/*      */       
/* 2292 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2293 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/* 2295 */       out.write(10);
/* 2296 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2297 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2298 */       if (wlsGraph == null) {
/* 2299 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2300 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2302 */       out.write(10);
/* 2303 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2305 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2306 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2307 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2309 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2311 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2313 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2315 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2316 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2317 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2318 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2321 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2322 */         String available = null;
/* 2323 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2324 */         out.write(10);
/*      */         
/* 2326 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2327 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2328 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2330 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2332 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2334 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2336 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2337 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2338 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2339 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2342 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2343 */           String unavailable = null;
/* 2344 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2345 */           out.write(10);
/*      */           
/* 2347 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2348 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2349 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2351 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2353 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2355 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2357 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2358 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2359 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2360 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2363 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2364 */             String unmanaged = null;
/* 2365 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2366 */             out.write(10);
/*      */             
/* 2368 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2369 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2370 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2372 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2374 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2376 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2378 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2379 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2380 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2381 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2384 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2385 */               String scheduled = null;
/* 2386 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2387 */               out.write(10);
/*      */               
/* 2389 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2390 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2391 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2393 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2395 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2397 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2399 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2400 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2401 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2402 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2405 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2406 */                 String critical = null;
/* 2407 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2408 */                 out.write(10);
/*      */                 
/* 2410 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2411 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2412 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2414 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2416 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2418 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2420 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2421 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2422 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2423 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2426 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2427 */                   String clear = null;
/* 2428 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2429 */                   out.write(10);
/*      */                   
/* 2431 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2432 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2433 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2435 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2437 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2439 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2441 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2442 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2443 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2444 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2447 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2448 */                     String warning = null;
/* 2449 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2450 */                     out.write(10);
/* 2451 */                     out.write(10);
/*      */                     
/* 2453 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2454 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2456 */                     out.write(10);
/* 2457 */                     out.write(10);
/* 2458 */                     out.write(10);
/* 2459 */                     out.write("\n<html>\n<head>\n<title>");
/* 2460 */                     out.print(FormatUtil.getString("am.webclient.availablitydata.text"));
/* 2461 */                     out.write("</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
/* 2462 */                     out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2463 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2465 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2466 */                     out.write(10);
/* 2467 */                     out.write(10);
/*      */                     
/* 2469 */                     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2470 */                     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2471 */                     _jspx_th_c_005fif_005f1.setParent(null);
/*      */                     
/* 2473 */                     _jspx_th_c_005fif_005f1.setTest("${not param.Report}");
/* 2474 */                     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2475 */                     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                       for (;;) {
/* 2477 */                         out.write(" \n<table width=\"100%\"  height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/* 2478 */                         out.print(FormatUtil.getString("am.webclient.availablitydata.text"));
/* 2479 */                         out.write("</span><span class=\"headingwhite\"> </span>\n</td>\n</tr>\n</table>\n<br>\n");
/* 2480 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2481 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2485 */                     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2486 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                     }
/*      */                     else {
/* 2489 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2490 */                       out.write("\n<script>\n\n function fndeleteDowntimeData(del)\n {\n\t var typeID = del.charAt(del.indexOf(\"typeid\")+7); ");
/* 2491 */                       out.write(10);
/* 2492 */                       out.write(32);
/* 2493 */                       out.write(32);
/*      */                       
/* 2495 */                       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2496 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2497 */                       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                       
/* 2499 */                       _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2500 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2501 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2503 */                           out.write("\n    alert(\"");
/* 2504 */                           out.print(FormatUtil.getString("am.webclient.historydata.jsalertfordemo.text"));
/* 2505 */                           out.write("\")\n    return\n  ");
/* 2506 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2507 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2511 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2512 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                       }
/*      */                       else {
/* 2515 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2516 */                         out.write(10);
/* 2517 */                         out.write(32);
/* 2518 */                         out.write(32);
/*      */                         
/* 2520 */                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2521 */                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2522 */                         _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                         
/* 2524 */                         _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2525 */                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2526 */                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                           for (;;) {
/* 2528 */                             out.write("\n  if(typeID == 1)\n  {\n  \tif(confirm(\"");
/* 2529 */                             out.print(FormatUtil.getString("am.webclient.historydata.jsalertforadmin.text"));
/* 2530 */                             out.write("\"))\n  \t {\n\t   location.href=del\n     }\n\telse\n\t {\n\n\t }\n  }\n  else if(typeID == 2)\n  {\n\tif(confirm(\"");
/* 2531 */                             out.print(FormatUtil.getString("am.webclient.historydata.jsalertforadmin.unmanaged.text"));
/* 2532 */                             out.write("\"))\n\t  {\n\t\t location.href=del\n\t  }\n\telse\n\t{\n\n\t}\n  }\n  else\n  {\n  \tif(confirm(\"");
/* 2533 */                             out.print(FormatUtil.getString("am.webclient.historydata.jsalertforadmin.scheduled.text"));
/* 2534 */                             out.write("\"))\n  \t  {\n  \t\t location.href=del\n  \t  }\n  \telse\n  \t{\n\n  \t}\n  }\n ");
/* 2535 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2536 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2540 */                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2541 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                         }
/*      */                         else {
/* 2544 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2545 */                           out.write("\n }\n\n\nfunction insertDowntimeReason(downtime,textName,divName,textReason)\n{\n  var reasonID=document.getElementById(textReason).value;\n  if(document.getElementById(textName).value=='')\n   {\n     alert(\"");
/* 2546 */                           out.print(FormatUtil.getString("am.webclient.historydata.downtimereason.text"));
/* 2547 */                           out.write("\"); ");
/* 2548 */                           out.write("\n     document.getElementById(textName).focus();\n     showDiv(divName);\n   }\n  else\n   {\n    var reason=document.getElementById(textName).value;\n    url=\"/showHistoryData.do?method=insertDowntimeNote&downtime=\"+downtime+\"&reasonid=\"+reasonID+\"&textValue=\"+encodeURIComponent(reason)+\"&resourceid=");
/* 2549 */                           out.print(request.getParameter("resourceid"));
/* 2550 */                           out.write(34);
/* 2551 */                           out.write(59);
/* 2552 */                           out.write(32);
/* 2553 */                           out.write("\n    http.open(\"GET\",url,true); ");
/* 2554 */                           out.write("\n    http.onreadystatechange = new Function('if(http.readyState == 4 && http.status == 200) {document.getElementById(\"'+textReason+'\").value = http.responseText,document.getElementById(\"'+divName+'\").style.display=\"none\";}'); ");
/* 2555 */                           out.write("\n    http.send(null);\n    document.getElementById(textName).style.border=\"0px\";  ");
/* 2556 */                           out.write("    \n  }\n}\n\n\nfunction cancelDowntimeReason(downtime,textName,divNote,divName,textReason,typeID)\n{\n\tvar reasonID=document.getElementById(textReason).value;\n\tif(reasonID == -1) {\n\t\tif(typeID == 2) {\n\t\t\tdocument.getElementById(textName).value = \"Monitor is Unmanaged\"; ");
/* 2557 */                           out.write("\n\t\t\tdocument.getElementById(textName).style.border = \"0px\"; ");
/* 2558 */                           out.write("\n    \t\n    \t\thideDiv(divName);\n\t\t\tshowDiv(divNote);\n\t\t}\n\t\telse if(typeID == 3) {\n\t\t\tdocument.getElementById(textName).value = \"Scheduled Downtime\"; ");
/* 2559 */                           out.write("\n\t\t\tdocument.getElementById(textName).style.border = \"0px\"; ");
/* 2560 */                           out.write("\n\t\t\n\t\t\thideDiv(divName);\n\t\t\tshowDiv(divNote);\n\t\t}\n\t\telse {\n\t\t\thideDiv(divName);\n\t\t\thideDiv(divNote);  ");
/* 2561 */                           out.write("\n\t\t}\n    }\n    else {\n\t\tvar reason=document.getElementById(textName).value;  ");
/* 2562 */                           out.write("\n        url=\"/showHistoryData.do?method=cancelDowntimeNote&downtime=\"+downtime+\"&reasonid=\"+reasonID+\"&textValue=\"+encodeURIComponent(reason)+\"&resourceid=");
/* 2563 */                           out.print(request.getParameter("resourceid"));
/* 2564 */                           out.write(34);
/* 2565 */                           out.write(59);
/* 2566 */                           out.write(32);
/* 2567 */                           out.write("\n\t\thttp.open(\"GET\",url,true); ");
/* 2568 */                           out.write("\n\t\thttp.onreadystatechange = new Function('if(http.readyState == 4 && http.status == 200){var respStr = http.responseText;document.getElementById(\"'+textName+'\").value=respStr.substring(respStr.indexOf(\\'~\\')+1,respStr.length);document.getElementById(\"'+textReason+'\").value=respStr.substring(0,respStr.indexOf(\\'~\\'));}'); ");
/* 2569 */                           out.write("\n\t\thttp.send(null);\n\t\tshowDiv(divNote);\n\t\thideDiv(divName);\n\t\tdocument.getElementById(textName).style.border=\"0px\"; ");
/* 2570 */                           out.write("\n\t\n    }\n}\n\n\nfunction addNote(textName,divName,divNote)\n{\n\tshowDiv(divName);\n\tshowDiv(divNote);\n\tdocument.getElementById(textName).focus();\n\t\n\n}\n\n\n function fnCheckCustomTime(f)\n {\n if(f.startDate.value=='')\n {\n alert(\"");
/* 2571 */                           out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/* 2572 */                           out.write("\");\n return false\n }\n else if(f.endDate.value=='')\n {\n alert(\"");
/* 2573 */                           out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/* 2574 */                           out.write("\")\n return false\n }\n var s=f.startDate.value;\n var e=f.endDate.value;\n    if(s>e)\n    {\n     alert(\"");
/* 2575 */                           out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/* 2576 */                           out.write("\" );\n     return false;\n    }\n\n return true\n }\nonload = function()\n{\n\tvar p= ");
/* 2577 */                           out.print(request.getParameter("period"));
/* 2578 */                           out.write("\n\tif(p == 4)\n\t{\n\t\tdocument.getElementById(\"custPeriod\").style.display='block';\n\t}\n\n}\n");
/*      */                           
/* 2580 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2581 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2582 */                           _jspx_th_c_005fif_005f2.setParent(null);
/*      */                           
/* 2584 */                           _jspx_th_c_005fif_005f2.setTest("${param.Report ==null}");
/* 2585 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2586 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/* 2588 */                               out.write("\n function fnSetEndTime(a)\n{\n\tdocument.forms[1].endDate.value=a;\n}\nfunction fnSetStartTime(a)\n{\n\tdocument.forms[1].startDate.value=a;\n}\n function fnPeriod(periodcombo)\n{\n\tvar p = periodcombo.value;\n\tif(p==4) //period  4  is meant for the custom period.\n\t{\n\t\tdocument.getElementById(\"custPeriod\").style.display='block';\n\t\talert(\"");
/* 2589 */                               out.print(FormatUtil.getString("am.webclient.historydata.jsalertforcustomtime"));
/* 2590 */                               out.write("\")\n \t\treturn false\n\t}\n\telse\n\t{\n\t\tdocument.getElementById(\"custPeriod\").style.display='none';\n\t}\n       document.forms[0].reporttype.value = \"html\"; // can only be html as first generate html and then save as pdf\n     \tdocument.forms[0].submit();\n\n}\n\n\nfunction generateReport(type)\n{\n\n\n");
/* 2591 */                               if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                 return;
/* 2593 */                               out.write(10);
/* 2594 */                               out.write(10);
/* 2595 */                               if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                 return;
/* 2597 */                               out.write(10);
/* 2598 */                               out.write(125);
/* 2599 */                               out.write(10);
/* 2600 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2601 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2605 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2606 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */                           }
/*      */                           else {
/* 2609 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2610 */                             out.write("\n\n</script>\n</head>\n\n<body>\n ");
/*      */                             
/* 2612 */                             Properties downtimeSummarGraphData = new Properties();
/*      */                             try {
/* 2614 */                               downtimeSummarGraphData = (Properties)request.getAttribute("summary");
/*      */                             }
/*      */                             catch (Exception ep) {
/* 2617 */                               ep.printStackTrace();
/*      */                             }
/*      */                             
/*      */ 
/* 2621 */                             com.adventnet.appmanager.reporting.form.ReportForm frm = (com.adventnet.appmanager.reporting.form.ReportForm)request.getAttribute("ReportForm");
/* 2622 */                             ArrayList list = frm.getMonitors();
/* 2623 */                             Hashtable monitorDisplayNames = null;
/* 2624 */                             if ((request.getParameter("actionMethod") != null) && (frm != null) && (frm.getMonitorDisplayNames() != null)) {
/* 2625 */                               monitorDisplayNames = frm.getMonitorDisplayNames();
/*      */                             }
/* 2627 */                             String forward = null;
/* 2628 */                             if ((request.getParameter("Report") != null) && (request.getParameter("Report").equalsIgnoreCase("true")))
/*      */                             {
/* 2630 */                               forward = "/showReports.do?actionMethod=generateMttrAvailablityReport&Report=true&resourceType=monitors";
/*      */                               
/* 2632 */                               out.write("\n                    <table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n  <tr>\n    <td width='100%' >\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n        ");
/*      */                               
/* 2634 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2635 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2636 */                               _jspx_th_html_005fform_005f0.setParent(null);
/*      */                               
/* 2638 */                               _jspx_th_html_005fform_005f0.setAction("/showReports.do");
/*      */                               
/* 2640 */                               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2641 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2642 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2644 */                                   out.write(32);
/* 2645 */                                   if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2647 */                                   out.write("\n        ");
/* 2648 */                                   if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2650 */                                   out.write(32);
/* 2651 */                                   if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2653 */                                   out.write(32);
/* 2654 */                                   if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2656 */                                   out.write("\n        ");
/* 2657 */                                   if (_jspx_meth_html_005fhidden_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2659 */                                   out.write(32);
/* 2660 */                                   if (_jspx_meth_html_005fhidden_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2662 */                                   out.write("\n        ");
/* 2663 */                                   if (_jspx_meth_html_005fhidden_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2665 */                                   out.write(32);
/* 2666 */                                   if (_jspx_meth_html_005fhidden_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2668 */                                   out.write("\n        ");
/* 2669 */                                   if (_jspx_meth_html_005fhidden_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2671 */                                   out.write(32);
/* 2672 */                                   if (_jspx_meth_html_005fhidden_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2674 */                                   out.write("\n        ");
/* 2675 */                                   if (_jspx_meth_html_005fhidden_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2677 */                                   out.write(32);
/* 2678 */                                   if (_jspx_meth_html_005fhidden_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2680 */                                   out.write("\n        ");
/* 2681 */                                   if ((request.getAttribute("PRINTER_FRIENDLY") == null) || (request.getAttribute("PRINTER_FRIENDLY").equals("false")))
/*      */                                   {
/* 2683 */                                     out.write(32);
/* 2684 */                                     int listsize = list.size();
/*      */                                     
/* 2686 */                                     int divlength = 80;
/*      */                                     
/* 2688 */                                     if ((listsize > 10) && (listsize < 20))
/*      */                                     {
/* 2690 */                                       divlength = 130;
/*      */                                     }
/* 2692 */                                     else if (listsize > 25)
/*      */                                     {
/* 2694 */                                       divlength = 200;
/*      */                                     }
/*      */                                     
/* 2697 */                                     String selectedResName = null;
/*      */                                     
/* 2699 */                                     if ((monitorDisplayNames != null) && (monitorDisplayNames.get(frm.getResid()) != null)) {
/* 2700 */                                       selectedResName = (String)monitorDisplayNames.get(frm.getResid());
/*      */                                       
/* 2702 */                                       if (selectedResName.length() >= 30) {
/* 2703 */                                         selectedResName = selectedResName.substring(0, 22);
/*      */                                       }
/*      */                                     }
/*      */                                     
/* 2707 */                                     if (selectedResName == null) {
/* 2708 */                                       selectedResName = "<c:out value=\"${ReportForm.monitorDisplayNames[ReportForm.resid]}\"/>";
/*      */                                     }
/*      */                                     
/* 2711 */                                     out.write("\n        <tr>\n          <td width=\"12%\" nowrap class=\"bodytext\">");
/* 2712 */                                     out.print(FormatUtil.getString("am.webclient.availablitydata.summaryselectmonitor.text"));
/* 2713 */                                     out.write("\n          </td>\n\t      <td width=\"88%\"> ");
/* 2714 */                                     if (_jspx_meth_html_005fhidden_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 2716 */                                     out.write("\n            <input type=\"text\" class=\"formtext input-down-arrow\" size=\"35\" style=\"height:20px;\" onMouseDown=\"DisplayServiceList('service_list_popup','centerimage')\" id=\"saturday\" name=\"saturday\" value='");
/* 2717 */                                     out.print(selectedResName);
/* 2718 */                                     out.write("'><img src=\"../../images/icon_downarrow.gif\" style=\"display:none\" class=\"drop-downimg1\" align=\"absmiddle\" name=\"centerimage\" onClick=\"DisplayServiceList('service_list_popup','centerimage')\">     <div id=\"dummyid\" style=\"width:0px; height:0px; overflow:none;\">\n\t  <div id=\"service_list_popup\" class=\"formtext\" style=\"overflow:auto; width:400; height:");
/* 2719 */                                     out.print(divlength);
/* 2720 */                                     out.write("; display:none; position:absolute; background:#FFFFFF;\">\n\t  \t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t  ");
/* 2721 */                                     for (int i = 0; i < list.size(); i++) {
/* 2722 */                                       out.write("\n  <tr>\n    <td id=\"");
/* 2723 */                                       out.print(((Properties)list.get(i)).getProperty("value"));
/* 2724 */                                       out.write("_Availlist\" class=\"bodytext dropDownText\" onmouseover='SetSelected(this)' onMouseOut=\"changeStyle(this);\" onClick=\"SelectService('service_list_popup','");
/* 2725 */                                       out.print(((Properties)list.get(i)).getProperty("label"));
/* 2726 */                                       out.write(39);
/* 2727 */                                       out.write(44);
/* 2728 */                                       out.write(39);
/* 2729 */                                       out.print(((Properties)list.get(i)).getProperty("value"));
/* 2730 */                                       out.write("','centerimage')\">");
/* 2731 */                                       out.print(((Properties)list.get(i)).getProperty("label"));
/* 2732 */                                       out.write("\n\n\n\t</td>\n  </tr>\n  ");
/*      */                                     }
/* 2734 */                                     out.write("\n</table>\n </div></div>\n\t</td></tr>\n    ");
/*      */                                   }
/* 2736 */                                   out.write(10);
/* 2737 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2738 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2742 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2743 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 2746 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2747 */                               out.write("\n</table>\n</td>\n  </tr>\n</table>\n\n");
/*      */                             }
/*      */                             else
/*      */                             {
/* 2751 */                               forward = "../showHistoryData.do?method=getAvailabilityData";
/*      */                               
/* 2753 */                               out.write(10);
/*      */                               
/* 2755 */                               if ((request.getParameter("Report") == null) || (!request.getParameter("Report").equalsIgnoreCase("true")))
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2762 */                                 out.write("\n\n      ");
/*      */                               }
/* 2764 */                               out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tblbordergray-report\" align=\"center\">\n  <tr>\n    <td width=\"10%\" align=\"left\" style=\"padding-left:15px;\"> ");
/*      */                               
/* 2766 */                               FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2767 */                               _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 2768 */                               _jspx_th_html_005fform_005f1.setParent(null);
/*      */                               
/* 2770 */                               _jspx_th_html_005fform_005f1.setAction("/showHistoryData.do?method=getAvailabilityData");
/*      */                               
/* 2772 */                               _jspx_th_html_005fform_005f1.setStyle("Display:inline");
/* 2773 */                               int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 2774 */                               if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                                 for (;;) {
/* 2776 */                                   out.write("\n       ");
/*      */                                   
/* 2778 */                                   SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2779 */                                   _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2780 */                                   _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f1);
/*      */                                   
/* 2782 */                                   _jspx_th_html_005fselect_005f0.setProperty("period");
/*      */                                   
/* 2784 */                                   _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnPeriod(this)");
/*      */                                   
/* 2786 */                                   _jspx_th_html_005fselect_005f0.setStyleClass("formtextselected");
/* 2787 */                                   int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2788 */                                   if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2789 */                                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2790 */                                       out = _jspx_page_context.pushBody();
/* 2791 */                                       _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2792 */                                       _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2795 */                                       out.write("\n\t    ");
/*      */                                       
/* 2797 */                                       OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2798 */                                       _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2799 */                                       _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2801 */                                       _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.reports.period.dropdown.customtime.text"));
/*      */                                       
/* 2803 */                                       _jspx_th_html_005foption_005f0.setValue("4");
/* 2804 */                                       int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2805 */                                       if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2806 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                       }
/*      */                                       
/* 2809 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/* 2810 */                                       out.write("\n            ");
/*      */                                       
/* 2812 */                                       OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2813 */                                       _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2814 */                                       _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2816 */                                       _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */                                       
/* 2818 */                                       _jspx_th_html_005foption_005f1.setValue("0");
/* 2819 */                                       int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2820 */                                       if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2821 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                       }
/*      */                                       
/* 2824 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/* 2825 */                                       out.write("\n            ");
/*      */                                       
/* 2827 */                                       OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2828 */                                       _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2829 */                                       _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2831 */                                       _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */                                       
/* 2833 */                                       _jspx_th_html_005foption_005f2.setValue("3");
/* 2834 */                                       int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2835 */                                       if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2836 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                       }
/*      */                                       
/* 2839 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/* 2840 */                                       out.write("\n            ");
/*      */                                       
/* 2842 */                                       OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2843 */                                       _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2844 */                                       _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2846 */                                       _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */                                       
/* 2848 */                                       _jspx_th_html_005foption_005f3.setValue("6");
/* 2849 */                                       int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2850 */                                       if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2851 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                       }
/*      */                                       
/* 2854 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/*      */                                       
/* 2856 */                                       OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2857 */                                       _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2858 */                                       _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2860 */                                       _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                                       
/* 2862 */                                       _jspx_th_html_005foption_005f4.setValue("1");
/* 2863 */                                       int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2864 */                                       if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2865 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                       }
/*      */                                       
/* 2868 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/* 2869 */                                       out.write("\n            ");
/*      */                                       
/* 2871 */                                       OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2872 */                                       _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2873 */                                       _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2875 */                                       _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */                                       
/* 2877 */                                       _jspx_th_html_005foption_005f5.setValue("12");
/* 2878 */                                       int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2879 */                                       if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2880 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                       }
/*      */                                       
/* 2883 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/* 2884 */                                       out.write("\n            ");
/*      */                                       
/* 2886 */                                       OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2887 */                                       _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2888 */                                       _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2890 */                                       _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */                                       
/* 2892 */                                       _jspx_th_html_005foption_005f6.setValue("7");
/* 2893 */                                       int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2894 */                                       if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2895 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                       }
/*      */                                       
/* 2898 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*      */                                       
/* 2900 */                                       OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2901 */                                       _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 2902 */                                       _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2904 */                                       _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                                       
/* 2906 */                                       _jspx_th_html_005foption_005f7.setValue("2");
/* 2907 */                                       int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 2908 */                                       if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 2909 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                       }
/*      */                                       
/* 2912 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/* 2913 */                                       out.write("\n            ");
/*      */                                       
/* 2915 */                                       OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2916 */                                       _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 2917 */                                       _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2919 */                                       _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */                                       
/* 2921 */                                       _jspx_th_html_005foption_005f8.setValue("11");
/* 2922 */                                       int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 2923 */                                       if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 2924 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                       }
/*      */                                       
/* 2927 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/*      */                                       
/* 2929 */                                       OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2930 */                                       _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 2931 */                                       _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2933 */                                       _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */                                       
/* 2935 */                                       _jspx_th_html_005foption_005f9.setValue("9");
/* 2936 */                                       int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 2937 */                                       if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 2938 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                       }
/*      */                                       
/* 2941 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/* 2942 */                                       out.write("\n            ");
/*      */                                       
/* 2944 */                                       OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2945 */                                       _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 2946 */                                       _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2948 */                                       _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */                                       
/* 2950 */                                       _jspx_th_html_005foption_005f10.setValue("8");
/* 2951 */                                       int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 2952 */                                       if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 2953 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                       }
/*      */                                       
/* 2956 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/*      */                                       
/* 2958 */                                       OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2959 */                                       _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 2960 */                                       _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2962 */                                       _jspx_th_html_005foption_005f11.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                                       
/* 2964 */                                       _jspx_th_html_005foption_005f11.setValue("5");
/* 2965 */                                       int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 2966 */                                       if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 2967 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                       }
/*      */                                       
/* 2970 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11);
/* 2971 */                                       out.write("\n            ");
/* 2972 */                                       int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 2973 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2976 */                                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2977 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2980 */                                   if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 2981 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                   }
/*      */                                   
/* 2984 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 2985 */                                   out.write(" <input type=hidden name=\"resourcename\" value=\"");
/* 2986 */                                   out.print(request.getParameter("resourcename"));
/* 2987 */                                   out.write("\" >\n            <input type=hidden name=\"resourceid\" value=\"");
/* 2988 */                                   out.print(request.getParameter("resourceid"));
/* 2989 */                                   out.write("\" >\n\n      ");
/* 2990 */                                   if (_jspx_meth_html_005fhidden_005f13(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2992 */                                   out.write(32);
/* 2993 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 2994 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2998 */                               if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 2999 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                               }
/*      */                               
/* 3002 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3003 */                               out.write("</td>\n  \t  <td width=\"55%\" align=\"left\" class=\"bodytext\"> ");
/*      */                               
/* 3005 */                               FormTag _jspx_th_html_005fform_005f2 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 3006 */                               _jspx_th_html_005fform_005f2.setPageContext(_jspx_page_context);
/* 3007 */                               _jspx_th_html_005fform_005f2.setParent(null);
/*      */                               
/* 3009 */                               _jspx_th_html_005fform_005f2.setAction("/showHistoryData.do?method=getAvailabilityData&period=4");
/*      */                               
/* 3011 */                               _jspx_th_html_005fform_005f2.setStyle("Display:inline");
/* 3012 */                               int _jspx_eval_html_005fform_005f2 = _jspx_th_html_005fform_005f2.doStartTag();
/* 3013 */                               if (_jspx_eval_html_005fform_005f2 != 0) {
/*      */                                 for (;;) {
/* 3015 */                                   out.write("\n            <div id='custPeriod' style=\"Display:none\" width=\"100%\">\n    \t\t \n\t\t\t   <span style=\"padding-left:10px;\">");
/* 3016 */                                   out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 3017 */                                   out.write("</span>\n\t\t\t  ");
/* 3018 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                                     return;
/* 3020 */                                   out.write("\n\t\t            <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=startTrigger title=\"");
/* 3021 */                                   out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 3022 */                                   out.write("\"></a>\n\t\t            <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT>\n                 <span style=\"padding-left:3px;\">");
/* 3023 */                                   out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 3024 */                                   out.write("</span>\n                 ");
/* 3025 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                                     return;
/* 3027 */                                   out.write("\n\t\t\t            <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=endTrigger title=\"");
/* 3028 */                                   out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 3029 */                                   out.write("\"></a>\n\t\t\t            <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"end\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT>\n                   \n                   <input type=hidden name=\"resourcename\" value=\"");
/* 3030 */                                   out.print(request.getParameter("resourcename"));
/* 3031 */                                   out.write("\" >\n                  <input type=hidden name=\"resourceid\" value=\"");
/* 3032 */                                   out.print(request.getParameter("resourceid"));
/* 3033 */                                   out.write("\" >\n                <span style=\"padding-left:3px;\"><input type=\"submit\" name=\"show\" value=\"");
/* 3034 */                                   out.print(FormatUtil.getString("Go"));
/* 3035 */                                   out.write("\" class=\"buttons btn_highlt\" onClick=\"return fnCheckCustomTime(this.form)\"></span>\n            \n             \n\t</div>\n            ");
/* 3036 */                                   if (_jspx_meth_html_005fhidden_005f14(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                                     return;
/* 3038 */                                   out.write("\n            ");
/* 3039 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f2.doAfterBody();
/* 3040 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3044 */                               if (_jspx_th_html_005fform_005f2.doEndTag() == 5) {
/* 3045 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f2); return;
/*      */                               }
/*      */                               
/* 3048 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f2);
/* 3049 */                               out.write(" </td>\n    <td width=\"30%\" align=\"right\"><a class=\"staticlinks\" href=\"javascript:generateReport('pdf')\"><img  align=\"absmiddle\"  src=\"images/icon_pdf.gif\" border=\"0\" alt=\"PDF Report\" hspace=\"4\" title=\"");
/* 3050 */                               out.print(FormatUtil.getString("am.reporttab.pdftitle.text"));
/* 3051 */                               out.write("\"></a>\n    </td>\n  </tr>\n</table>\n");
/*      */                             }
/* 3053 */                             out.write(10);
/* 3054 */                             out.write(10);
/*      */                             
/* 3056 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3057 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3058 */                             _jspx_th_c_005fif_005f5.setParent(null);
/*      */                             
/* 3060 */                             _jspx_th_c_005fif_005f5.setTest("${STATUS!='SUCCESS'}");
/* 3061 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3062 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/* 3064 */                                 out.write("\n<br>\n\t<table align=\"center\" cellspacing=\"5\" width=\"98%\" align=\"center\">\n  \t<tr>\n   \t <td valign=\"top\" width=\"80%\"> <table cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder\" align=\"left\" width=\"100%\">\n        <tr>\n          <td class=\"lightbg\"> <span class=\"bodytextbold\">");
/* 3065 */                                 if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                   return;
/* 3067 */                                 out.write("</span></td>\n        </tr>\n \t ");
/*      */                                 
/* 3069 */                                 if (!com.adventnet.appmanager.util.OEMUtil.isOEM())
/*      */                                 {
/*      */ 
/* 3072 */                                   out.write("\n        <tr>\n          <td align=\"right\" class=\"lightbg\"> <a href=\"http://");
/* 3073 */                                   out.print(com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link"));
/* 3074 */                                   out.write("#m4\" class=\"staticlinks\">\n            ");
/* 3075 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/* 3076 */                                   out.write("\n            >></a></td>\n        </tr>\n");
/*      */                                 }
/* 3078 */                                 out.write("\n      </table></td>\n  \t</tr>\n\t</table>\n\t");
/* 3079 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3080 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3084 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3085 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*      */                             }
/*      */                             else {
/* 3088 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3089 */                               out.write(10);
/*      */                               
/* 3091 */                               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3092 */                               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3093 */                               _jspx_th_c_005fif_005f6.setParent(null);
/*      */                               
/* 3095 */                               _jspx_th_c_005fif_005f6.setTest("${STATUS=='SUCCESS'}");
/* 3096 */                               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3097 */                               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                 for (;;) {
/* 3099 */                                   out.write("\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n  <tr>\n    <td colspan=\"2\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n        <tr>\n          ");
/* 3100 */                                   if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3102 */                                   out.write("\n          ");
/* 3103 */                                   if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3105 */                                   out.write("\n        ");
/* 3106 */                                   String nameofresource = (String)pageContext.getAttribute("rname");
/* 3107 */                                   String resourceperiod = FormatUtil.getString((String)pageContext.getAttribute("rperiod"));
/*      */                                   
/*      */ 
/* 3110 */                                   out.write("\n         ");
/*      */                                   
/*      */ 
/* 3113 */                                   if (request.getParameter("period").equals("4"))
/*      */                                   {
/* 3115 */                                     out.write("\n\n          <td class=\"tableheadingbborder\"> ");
/* 3116 */                                     out.print(FormatUtil.getString("am.webclient.availablityreport.customtimeheading.text", new String[] { nameofresource, resourceperiod }));
/* 3117 */                                     out.write(" </td>\n         ");
/*      */                                   }
/*      */                                   else {
/* 3120 */                                     out.write("\n          <td class=\"tableheadingbborder\">\n    ");
/* 3121 */                                     out.print(FormatUtil.getString("am.webclient.availablityreport.timeperiodheading.text", new String[] { resourceperiod, nameofresource }));
/* 3122 */                                     out.write("\n    </td>");
/*      */                                   }
/* 3124 */                                   out.write("\n\n          <td class=\"tableheadingbborder\" align=\"right\" valign=\"middle\">\n           ");
/* 3125 */                                   if ((request.getParameter("Report") == null) || (!request.getParameter("Report").equalsIgnoreCase("true")))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/* 3130 */                                     out.write("\n          <form name=\"closeform\" style=\"display: inline\">\n              <input name=\"button\" value=\"");
/* 3131 */                                     out.print(FormatUtil.getString("am.webclient.prolic.close.text"));
/* 3132 */                                     out.write("\" type=\"button\" class=\"buttons btn_link\"  onClick=\"parent.window.close()\">\n            </form>");
/*      */                                   }
/* 3134 */                                   out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n  ");
/*      */                                   
/* 3136 */                                   MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 3137 */                                   _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 3138 */                                   _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 3140 */                                   _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                                   
/* 3142 */                                   _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 3143 */                                   int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 3144 */                                   if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 3145 */                                     String msg = null;
/* 3146 */                                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 3147 */                                       out = _jspx_page_context.pushBody();
/* 3148 */                                       _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 3149 */                                       _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                                     }
/* 3151 */                                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                                     for (;;) {
/* 3153 */                                       out.write("\n  <tr>\n    <td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n        <tr>\n          <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\n\"></td>\n          <td width=\"95%\" height=\"28\" class=\"message\">");
/* 3154 */                                       if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                                         return;
/* 3156 */                                       out.write("</td>\n        </tr>\n      </table>\n      <br></td>\n  </tr>\n  ");
/* 3157 */                                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 3158 */                                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 3159 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3162 */                                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 3163 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3166 */                                   if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 3167 */                                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                                   }
/*      */                                   
/* 3170 */                                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 3171 */                                   out.write("\n\n  <tr>\n    <td height=\"30\" align=\"center\" class=\"bodytextbold\">");
/* 3172 */                                   out.print(FormatUtil.getString("am.webclient.availablityreport.uptimedowntime.text"));
/* 3173 */                                   out.write("</td>\n    <td align=\"center\" class=\"bodytextbold\">&nbsp;</td>\n  </tr>\n  <tr>\n  ");
/* 3174 */                                   if ((request.getParameter("Report") != null) && (request.getParameter("Report").equalsIgnoreCase("true")))
/*      */                                   {
/* 3176 */                                     out.write("<td width=\"95%\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"grayfullborder\"> ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3181 */                                     out.write("\n\n\n        <td align=\"left\" valign=\"top\" colspan=\"2\"  class=\"tdindent\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n            <tr>\n          <td> <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n              <tr> ");
/* 3182 */                                     if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3184 */                                     out.write(32);
/*      */                                     
/* 3186 */                                     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3187 */                                     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3188 */                                     _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f6);
/*      */                                     
/* 3190 */                                     _jspx_th_c_005fif_005f8.setTest("${period !='1'}");
/* 3191 */                                     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3192 */                                     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                       for (;;) {
/* 3194 */                                         out.write("\n                <td width=\"90%\" align=\"right\"><a href='../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3195 */                                         out.print(request.getParameter("resourceid"));
/* 3196 */                                         out.write("&period=1&resourcename=");
/* 3197 */                                         out.print(request.getParameter("resourcename"));
/* 3198 */                                         out.write("'><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3199 */                                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3200 */                                         out.write("\"></a>\n                </td>\n                ");
/* 3201 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3202 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3206 */                                     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3207 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                     }
/*      */                                     
/* 3210 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3211 */                                     out.write(32);
/* 3212 */                                     if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3214 */                                     out.write(32);
/*      */                                     
/* 3216 */                                     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3217 */                                     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3218 */                                     _jspx_th_c_005fif_005f10.setParent(_jspx_th_c_005fif_005f6);
/*      */                                     
/* 3220 */                                     _jspx_th_c_005fif_005f10.setTest("${period !='2'}");
/* 3221 */                                     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3222 */                                     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                       for (;;) {
/* 3224 */                                         out.write("\n                <td width=\"10%\"> <a href='../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3225 */                                         out.print(request.getParameter("resourceid"));
/* 3226 */                                         out.write("&period=2&resourcename=");
/* 3227 */                                         out.print(request.getParameter("resourcename"));
/* 3228 */                                         out.write("'><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3229 */                                         out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3230 */                                         out.write("\"></a>\n                </td>\n                ");
/* 3231 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3232 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3236 */                                     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3237 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                     }
/*      */                                     
/* 3240 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3241 */                                     out.write(" </tr>\n            </table></td>\n        </tr>");
/*      */                                   }
/* 3243 */                                   out.write("\n        ");
/*      */                                   
/* 3245 */                                   wlsGraph.setParam(request.getParameter("resourceid"), "AVAILABILITY", request.getAttribute("period").toString(), true, downtimeSummarGraphData);
/*      */                                   
/* 3247 */                                   out.write("\n        <tr>\n          <td height=\"170\" align=\"center\" class=\"bodytext\"> ");
/*      */                                   
/* 3249 */                                   AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3250 */                                   _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3251 */                                   _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 3253 */                                   _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                                   
/* 3255 */                                   _jspx_th_awolf_005fpiechart_005f0.setWidth("300");
/*      */                                   
/* 3257 */                                   _jspx_th_awolf_005fpiechart_005f0.setHeight("185");
/*      */                                   
/* 3259 */                                   _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                                   
/* 3261 */                                   _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                                   
/* 3263 */                                   _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3264 */                                   int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3265 */                                   if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3266 */                                     if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3267 */                                       out = _jspx_page_context.pushBody();
/* 3268 */                                       _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3269 */                                       _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3272 */                                       out.write("\n            ");
/*      */                                       
/* 3274 */                                       Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3275 */                                       _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3276 */                                       _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                       
/* 3278 */                                       _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3279 */                                       int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3280 */                                       if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3281 */                                         if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3282 */                                           out = _jspx_page_context.pushBody();
/* 3283 */                                           _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3284 */                                           _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3287 */                                           out.write(32);
/*      */                                           
/* 3289 */                                           AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3290 */                                           _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3291 */                                           _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                           
/* 3293 */                                           _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                           
/* 3295 */                                           _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3296 */                                           int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3297 */                                           if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3298 */                                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                           }
/*      */                                           
/* 3301 */                                           this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3302 */                                           out.write("\n            ");
/*      */                                           
/* 3304 */                                           AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3305 */                                           _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3306 */                                           _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                           
/* 3308 */                                           _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                           
/* 3310 */                                           _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3311 */                                           int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3312 */                                           if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3313 */                                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                           }
/*      */                                           
/* 3316 */                                           this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3317 */                                           out.write("\n            ");
/*      */                                           
/* 3319 */                                           AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3320 */                                           _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 3321 */                                           _jspx_th_awolf_005fparam_005f2.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                           
/* 3323 */                                           _jspx_th_awolf_005fparam_005f2.setName("2");
/*      */                                           
/* 3325 */                                           _jspx_th_awolf_005fparam_005f2.setValue(unmanaged);
/* 3326 */                                           int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 3327 */                                           if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 3328 */                                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2); return;
/*      */                                           }
/*      */                                           
/* 3331 */                                           this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 3332 */                                           out.write("\n            ");
/*      */                                           
/* 3334 */                                           AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3335 */                                           _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 3336 */                                           _jspx_th_awolf_005fparam_005f3.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                           
/* 3338 */                                           _jspx_th_awolf_005fparam_005f3.setName("3");
/*      */                                           
/* 3340 */                                           _jspx_th_awolf_005fparam_005f3.setValue(scheduled);
/* 3341 */                                           int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 3342 */                                           if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 3343 */                                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3); return;
/*      */                                           }
/*      */                                           
/* 3346 */                                           this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 3347 */                                           out.write("\n            ");
/* 3348 */                                           int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3349 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3352 */                                         if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3353 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3356 */                                       if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3357 */                                         this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                       }
/*      */                                       
/* 3360 */                                       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3361 */                                       out.write(32);
/* 3362 */                                       int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3363 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3366 */                                     if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3367 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3370 */                                   if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3371 */                                     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                                   }
/*      */                                   
/* 3374 */                                   this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3375 */                                   out.write("\n          </td>\n        </tr>\n      </table>\n        </td>\n\n\n  </tr>\n  <tr>\n    <td colspan=\"2\" valign=\"top\">&nbsp;</td>\n  </tr>\n\n\n    <tr>\n      <td colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\n\n      <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\n  \t<tr>\n\t");
/*      */                                   
/* 3377 */                                   int widthperc = 46;
/* 3378 */                                   if ("true".equals(com.adventnet.appmanager.util.Constants.addMaintenanceToAvailablity))
/*      */                                   {
/* 3380 */                                     widthperc = 100;
/*      */                                   }
/*      */                                   
/* 3383 */                                   out.write("\n  \t<td width=\"");
/* 3384 */                                   out.print(widthperc);
/* 3385 */                                   out.write("%\">\n  \t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n  \t\t        <tr>\n  \t\t          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 3386 */                                   out.print(FormatUtil.getString("am.webclient.historydata.mondown.text"));
/* 3387 */                                   out.write("</td>\n  \t\t        </tr>\n  \t\t        <tr>\n  \t\t          <td width=\"56%\" class=\"whitegrayborderbr\">");
/* 3388 */                                   out.print(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/* 3389 */                                   out.write(" </td>\n  \t\t          <td width=\"44%\" class=\"whitegrayborder\">");
/* 3390 */                                   if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3392 */                                   out.write("</td>\n  \t\t        </tr>\n  \t\t        <tr>\n\t\t\t\t  <td width=\"56%\" class=\"whitegrayborderbr\">");
/* 3393 */                                   out.print(FormatUtil.getString("am.webclient.historydata.totaldownpercentage.text"));
/* 3394 */                                   out.write(" </td>\n\t\t\t\t  <td width=\"44%\" class=\"whitegrayborder\">");
/* 3395 */                                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3397 */                                   out.write("%</td>\n  \t\t        </tr>\n  \t\t        <tr>\n  \t\t          <td class=\"yellowgrayborderbr\">");
/* 3398 */                                   out.print(FormatUtil.getString("am.webclient.historydata.totaluppercentage.text"));
/* 3399 */                                   out.write("</td>\n  \t\t          <td class=\"yellowgrayborder\">\n\t\t\t\t  ");
/* 3400 */                                   if (!"true".equals(com.adventnet.appmanager.util.Constants.addMaintenanceToAvailablity)) {
/* 3401 */                                     out.write("\n\t\t\t\t  ");
/* 3402 */                                     if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3404 */                                     out.write("\n\t\t\t\t  ");
/*      */                                   } else {
/* 3406 */                                     out.write("\n\t\t\t\t  ");
/* 3407 */                                     if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3409 */                                     out.write("\n\t\t\t\t  ");
/*      */                                   }
/* 3411 */                                   out.write("\n\t\t\t\t  %</td>\n  \t\t        </tr>\n  \t\t        <tr>\n  \t\t          <td class=\"whitegrayborderbr\">");
/* 3412 */                                   out.print(FormatUtil.getString("am.webclient.historydata.mttr.text"));
/* 3413 */                                   out.write(" </td>\n  \t\t          <td class=\"whitegrayborder\">");
/* 3414 */                                   if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3416 */                                   out.write("</td>\n  \t\t        </tr>\n  \t\t        <tr>\n  \t\t          <td class=\"yellowgrayborderbr\">");
/* 3417 */                                   out.print(FormatUtil.getString("am.webclient.historydata.mtbf.text"));
/* 3418 */                                   out.write("</td>\n  \t\t          <td class=\"yellowgrayborder\">");
/* 3419 */                                   if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3421 */                                   out.write("</td>\n                  </tr>\n  \t\t</table>\n  \t</td>\n\t");
/* 3422 */                                   if (!"true".equals(com.adventnet.appmanager.util.Constants.addMaintenanceToAvailablity)) {
/* 3423 */                                     out.write("\n  \t<td width=\"46%\" valign=\"top\">\n  \t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n  \t\t\t\t\t <tr>\n  \t\t\t\t\t   <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 3424 */                                     out.print(FormatUtil.getString("am.webclient.historydata.unmge&scheduled.text"));
/* 3425 */                                     out.write("</td>\n  \t\t\t\t\t </tr>\n  \t\t\t\t\t <tr>\n  \t\t\t\t\t   <td width=\"56%\" class=\"whitegrayborderbr\">");
/* 3426 */                                     out.print(FormatUtil.getString("am.reporttab.availablityreport.totunmanaged.text"));
/* 3427 */                                     out.write(" </td>\n  \t\t\t\t\t   <td width=\"44%\" class=\"whitegrayborder\">");
/* 3428 */                                     if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3430 */                                     out.write("</td>\n  \t\t\t\t\t </tr>\n  \t\t\t\t\t <tr>\n  \t\t\t\t\t   <td class=\"yellowgrayborderbr\">");
/* 3431 */                                     out.print(FormatUtil.getString("am.reporttab.availablityreport.totunmanagepercentage.text"));
/* 3432 */                                     out.write("</td>\n  \t\t\t\t\t   <td class=\"yellowgrayborder\">");
/* 3433 */                                     if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3435 */                                     out.write("%</td>\n  \t\t\t\t\t </tr>\n  \t\t\t\t\t <tr>\n  \t\t\t\t\t   <td class=\"whitegrayborderbr\">");
/* 3436 */                                     out.print(FormatUtil.getString("am.reporttab.availablityreport.totscheduled.text"));
/* 3437 */                                     out.write(" </td>\n  \t\t\t\t\t   <td class=\"whitegrayborder\">");
/* 3438 */                                     if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3440 */                                     out.write("</td>\n  \t\t\t\t\t </tr>\n  \t\t\t\t\t <tr>\n  \t\t\t\t\t   <td class=\"yellowgrayborderbr\">");
/* 3441 */                                     out.print(FormatUtil.getString("am.reporttab.availablityreport.totscheduledpercentage.text"));
/* 3442 */                                     out.write("</td>\n  \t\t\t\t\t   <td class=\"yellowgrayborder\">");
/* 3443 */                                     if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3445 */                                     out.write("%</td>\n          \t\t\t</tr>\n  \t</table>\n  \t</td>\n\t");
/*      */                                   }
/* 3447 */                                   out.write("\n  \t</tr>\n\n      </table>\n\n\n      <br>\n      ");
/*      */                                   
/* 3449 */                                   IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3450 */                                   _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3451 */                                   _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 3453 */                                   _jspx_th_c_005fif_005f11.setTest("${size >'0'}");
/* 3454 */                                   int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3455 */                                   if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                     for (;;) {
/* 3457 */                                       out.write("\n      <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr align=\"center\">\n          <td colspan=\"8\" class=\"tableheadingbborder\">");
/* 3458 */                                       out.print(FormatUtil.getString("am.webclient.historydata.downtimedetail.text"));
/* 3459 */                                       out.write(" *</td>\n        </tr>\n        ");
/*      */                                       
/* 3461 */                                       org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/* 3462 */                                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3463 */                                       _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f11);
/*      */                                       
/* 3465 */                                       _jspx_th_logic_005fnotPresent_005f0.setRole("ADMIN,DEMO");
/* 3466 */                                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3467 */                                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                         for (;;) {
/* 3469 */                                           out.write("\n        <tr>\n          <td width=\"20%\" class=\"columnheading\">");
/* 3470 */                                           out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 3471 */                                           out.write("</td>\n          <td width=\"20%\" class=\"columnheading\">");
/* 3472 */                                           out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 3473 */                                           out.write("</td>\n          <td width=\"20%\" class=\"columnheading\">");
/* 3474 */                                           out.print(FormatUtil.getString("am.webclient.historydata.duration.text"));
/* 3475 */                                           out.write("</td>\n\n          <td width=\"40%\" class=\"columnheading\">");
/* 3476 */                                           out.print(FormatUtil.getString("am.webclient.historydata.downtime.reason.text"));
/* 3477 */                                           out.write(" </td>\n\n        </tr>\n        ");
/* 3478 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3479 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3483 */                                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3484 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                       }
/*      */                                       
/* 3487 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3488 */                                       out.write(32);
/*      */                                       
/* 3490 */                                       PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3491 */                                       _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3492 */                                       _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f11);
/*      */                                       
/* 3494 */                                       _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,DEMO");
/* 3495 */                                       int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3496 */                                       if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                         for (;;) {
/* 3498 */                                           out.write("\n        <tr>\n          <td width=\"21%\" class=\"columnheading\">");
/* 3499 */                                           out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 3500 */                                           out.write("</td>\n          <td width=\"21%\" class=\"columnheading\">");
/* 3501 */                                           out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 3502 */                                           out.write("</td>\n          <td width=\"22%\" class=\"columnheading\">");
/* 3503 */                                           out.print(FormatUtil.getString("am.webclient.historydata.duration.text"));
/* 3504 */                                           out.write("</td>\n\n\t  <td width=\"28%\" class=\"columnheading\">");
/* 3505 */                                           out.print(FormatUtil.getString("am.webclient.historydata.downtime.reason.text"));
/* 3506 */                                           out.write(" </td>\n\n          <td width=\"4%\"  class=\"columnheading\">");
/* 3507 */                                           out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3508 */                                           out.write("</td>\n        </tr>\n        ");
/* 3509 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3510 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3514 */                                       if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3515 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                       }
/*      */                                       
/* 3518 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3519 */                                       out.write("\n\n        ");
/* 3520 */                                       int i = 0;
/* 3521 */                                       out.write("\n\n        ");
/*      */                                       
/* 3523 */                                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3524 */                                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3525 */                                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f11);
/*      */                                       
/* 3527 */                                       _jspx_th_c_005fforEach_005f0.setItems("${downtimesummary}");
/*      */                                       
/* 3529 */                                       _jspx_th_c_005fforEach_005f0.setVar("row");
/*      */                                       
/* 3531 */                                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3532 */                                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                       try {
/* 3534 */                                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3535 */                                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                           for (;;) {
/* 3537 */                                             out.write("\n\n       ");
/* 3538 */                                             i++;
/* 3539 */                                             out.write("\n        <tr>\n      ");
/*      */                                             
/*      */ 
/* 3542 */                                             String color = "class=\"whitegrayborder\"";
/*      */                                             
/* 3544 */                                             out.write("\n          ");
/*      */                                             
/* 3546 */                                             IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3547 */                                             _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3548 */                                             _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                             
/* 3550 */                                             _jspx_th_c_005fif_005f12.setTest("${(status.count % 2) == 0}");
/* 3551 */                                             int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3552 */                                             if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                               for (;;) {
/* 3554 */                                                 out.write("\n          ");
/*      */                                                 
/* 3556 */                                                 color = "class=\"yellowgrayborder\"";
/*      */                                                 
/* 3558 */                                                 out.write("\n          ");
/* 3559 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3560 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3564 */                                             if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3565 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*      */                                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3568 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3569 */                                             out.write("\n\n          <td ");
/* 3570 */                                             out.print(color);
/* 3571 */                                             out.write(32);
/* 3572 */                                             out.write(62);
/* 3573 */                                             if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3575 */                                             out.write("</td>\n          <td ");
/* 3576 */                                             out.print(color);
/* 3577 */                                             out.write(32);
/* 3578 */                                             out.write(62);
/* 3579 */                                             if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3581 */                                             out.write("</td>\n          <td ");
/* 3582 */                                             out.print(color);
/* 3583 */                                             out.write(32);
/* 3584 */                                             out.write(62);
/* 3585 */                                             if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3587 */                                             out.write("</td>\n\n\n   <td ");
/* 3588 */                                             out.print(color);
/* 3589 */                                             out.write(" >\n     <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n          <tr>\n\n          <td>\n\n          <a style=\"cursor:pointer;\">\n          <img src=\"../images/editWidget.gif\" width=\"12\" height=\"12\" hspace=\"5\" border=\"0\" title=\"");
/* 3590 */                                             out.print(FormatUtil.getString("Edit"));
/* 3591 */                                             out.write("\" onClick=\"javascript:addNote('textNote");
/* 3592 */                                             out.print(i);
/* 3593 */                                             out.write("','divNote");
/* 3594 */                                             out.print(i);
/* 3595 */                                             out.write("','divImage");
/* 3596 */                                             out.print(i);
/* 3597 */                                             out.write("')\">\n          </a>\n\n          </td>\n          <td>\n          <div id=\"divNote");
/* 3598 */                                             out.print(i);
/* 3599 */                                             out.write("\" style=\"display:none; padding: 5px;\">\n   \t  \t<input id=\"textNote");
/* 3600 */                                             out.print(i);
/* 3601 */                                             out.write("\" type=\"text\"  class=\"formtext medium\" value=\"");
/* 3602 */                                             if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3604 */                                             out.write("\">\n   \t  \t<input type=\"hidden\" id=\"textReason");
/* 3605 */                                             out.print(i);
/* 3606 */                                             out.write("\" value=\"");
/* 3607 */                                             if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3609 */                                             out.write("\">\n\t  </div>\n          </td>\n\n          <td>\n\n          <div id=\"divImage");
/* 3610 */                                             out.print(i);
/* 3611 */                                             out.write("\" style=\"display:none; padding: 5px;\">\n    \t  <a style=\"cursor:pointer;\">\n    \t  \t<img src=\"../images/save.gif\" width=\"12\" height=\"12\" hspace=\"0\" border=\"0\" title=\"");
/* 3612 */                                             out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 3613 */                                             out.write("\" onClick=\"javascript:insertDowntimeReason('");
/* 3614 */                                             if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3616 */                                             out.write("','textNote");
/* 3617 */                                             out.print(i);
/* 3618 */                                             out.write("','divImage");
/* 3619 */                                             out.print(i);
/* 3620 */                                             out.write("','textReason");
/* 3621 */                                             out.print(i);
/* 3622 */                                             out.write("')\">\n    \t  </a>\n\n    \t  <a style=\"cursor:pointer;\">\n    \t  \t<img src=\"../images/cross.gif\" width=\"12\" height=\"12\" hspace=\"0\" border=\"0\" title=\"");
/* 3623 */                                             out.print(FormatUtil.getString("Cancel"));
/* 3624 */                                             out.write("\" onClick=\"javascript:cancelDowntimeReason('");
/* 3625 */                                             if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3627 */                                             out.write("','textNote");
/* 3628 */                                             out.print(i);
/* 3629 */                                             out.write("','divNote");
/* 3630 */                                             out.print(i);
/* 3631 */                                             out.write("','divImage");
/* 3632 */                                             out.print(i);
/* 3633 */                                             out.write("','textReason");
/* 3634 */                                             out.print(i);
/* 3635 */                                             out.write(39);
/* 3636 */                                             out.write(44);
/* 3637 */                                             out.write(39);
/* 3638 */                                             if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3640 */                                             out.write("')\">\n    \t  </a>\n\t  </div>\n          </td>\n          </tr>\n\n     </table>\n     ");
/*      */                                             
/* 3642 */                                             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3643 */                                             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3644 */                                             _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                             
/* 3646 */                                             _jspx_th_c_005fif_005f13.setTest("${!empty row.Downtime_Reason}");
/* 3647 */                                             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3648 */                                             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                               for (;;) {
/* 3650 */                                                 out.write("\n     \t<script>\n     \t\tshowDiv('divNote");
/* 3651 */                                                 out.print(i);
/* 3652 */                                                 out.write("'); ");
/* 3653 */                                                 out.write("\n     \t\tdocument.getElementById(\"textNote");
/* 3654 */                                                 out.print(i);
/* 3655 */                                                 out.write("\").style.border=\"0px\"; ");
/* 3656 */                                                 out.write("\n     \t\tdocument.getElementById(\"textNote");
/* 3657 */                                                 out.print(i);
/* 3658 */                                                 out.write("\").value =\"");
/* 3659 */                                                 if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                 }
/* 3661 */                                                 out.write(34);
/* 3662 */                                                 out.write(59);
/* 3663 */                                                 out.write(32);
/* 3664 */                                                 out.write("\n      \t</script>\n     ");
/* 3665 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3666 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3670 */                                             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3671 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*      */                                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3674 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3675 */                                             out.write("\n   </td>\n\n\n\n          ");
/*      */                                             
/* 3677 */                                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3678 */                                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3679 */                                             _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                             
/* 3681 */                                             _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,DEMO");
/* 3682 */                                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3683 */                                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                               for (;;) {
/* 3685 */                                                 out.write("\n          <td ");
/* 3686 */                                                 out.print(color);
/* 3687 */                                                 out.write(32);
/* 3688 */                                                 out.write(62);
/* 3689 */                                                 out.write(32);
/*      */                                                 
/* 3691 */                                                 org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
/* 3692 */                                                 _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3693 */                                                 _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fpresent_005f3);
/* 3694 */                                                 int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3695 */                                                 if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                                   for (;;) {
/* 3697 */                                                     out.write(32);
/*      */                                                     
/* 3699 */                                                     org.apache.taglibs.standard.tag.el.core.WhenTag _jspx_th_c_005fwhen_005f0 = (org.apache.taglibs.standard.tag.el.core.WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(org.apache.taglibs.standard.tag.el.core.WhenTag.class);
/* 3700 */                                                     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3701 */                                                     _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                                     
/* 3703 */                                                     _jspx_th_c_005fwhen_005f0.setTest("${row.dontdelete  == 'true'}");
/* 3704 */                                                     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3705 */                                                     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                                       for (;;) {
/* 3707 */                                                         out.write("\n            <img src=\"../images/icon_delete_disabled.gif\" width=\"12\" height=\"12\" hspace=\"5\" border=\"0\" title=\"");
/* 3708 */                                                         out.print(FormatUtil.getString("am.webclient.historydata.titlefordelete.text"));
/* 3709 */                                                         out.write("\">\n            ");
/* 3710 */                                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3711 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/*      */                                                     }
/* 3715 */                                                     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3716 */                                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*      */                                                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                     }
/* 3719 */                                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3720 */                                                     out.write(32);
/*      */                                                     
/* 3722 */                                                     org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
/* 3723 */                                                     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3724 */                                                     _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 3725 */                                                     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3726 */                                                     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                                       for (;;) {
/* 3728 */                                                         out.write(10);
/* 3729 */                                                         out.write(32);
/* 3730 */                                                         out.write(32);
/* 3731 */                                                         if ((request.getParameter("Report") != null) && (request.getParameter("Report").equalsIgnoreCase("true")))
/*      */                                                         {
/*      */ 
/* 3734 */                                                           out.write("\n                    <a href=\"javascript:fndeleteDowntimeData('/showHistoryData.do?method=deleteDowntimeData&resourceid=");
/* 3735 */                                                           out.print(request.getParameter("resourceid"));
/* 3736 */                                                           out.write("&typeid=");
/* 3737 */                                                           if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                           }
/* 3739 */                                                           out.write("&downtime=");
/* 3740 */                                                           if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                           }
/* 3742 */                                                           out.write("&reasonid='+document.getElementById('textReason");
/* 3743 */                                                           out.print(i);
/* 3744 */                                                           out.write("').value+'&period=");
/* 3745 */                                                           if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                           }
/* 3747 */                                                           out.write("&redirectto=/showReports.do?actionMethod=generateMttrAvailablityReport')\"><img src=\"../images/icon_removefromgroup.gif\" title=\"");
/* 3748 */                                                           out.print(FormatUtil.getString("Delete"));
/* 3749 */                                                           out.write("\" width=\"12\" height=\"12\" hspace=\"5\" border=\"0\"></a>\n                    ");
/*      */                                                         }
/*      */                                                         else
/*      */                                                         {
/* 3753 */                                                           out.write("\n<a href=\"javascript:fndeleteDowntimeData('/showHistoryData.do?method=deleteDowntimeData&resourceid=");
/* 3754 */                                                           out.print(request.getParameter("resourceid"));
/* 3755 */                                                           out.write("&typeid=");
/* 3756 */                                                           if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                           }
/* 3758 */                                                           out.write("&downtime=");
/* 3759 */                                                           if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                           }
/* 3761 */                                                           out.write("&reasonid='+document.getElementById('textReason");
/* 3762 */                                                           out.print(i);
/* 3763 */                                                           out.write("').value+'&period=");
/* 3764 */                                                           if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                           }
/* 3766 */                                                           out.write("')\"><img src=\"../images/icon_removefromgroup.gif\" title=\"");
/* 3767 */                                                           out.print(FormatUtil.getString("Delete"));
/* 3768 */                                                           out.write("\" width=\"12\" height=\"12\" hspace=\"5\" border=\"0\"></a>\n            ");
/*      */                                                         }
/* 3770 */                                                         out.write("\n            ");
/* 3771 */                                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3772 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/*      */                                                     }
/* 3776 */                                                     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3777 */                                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*      */                                                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                     }
/* 3780 */                                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3781 */                                                     out.write(32);
/* 3782 */                                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3783 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 3787 */                                                 if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3788 */                                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */                                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                 }
/* 3791 */                                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3792 */                                                 out.write(" </td>\n          ");
/* 3793 */                                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3794 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3798 */                                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3799 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */                                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 3802 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3803 */                                             out.write(" </tr>\n        ");
/* 3804 */                                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3805 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3809 */                                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3817 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/*      */                                       }
/*      */                                       catch (Throwable _jspx_exception)
/*      */                                       {
/*      */                                         for (;;)
/*      */                                         {
/* 3813 */                                           int tmp10575_10574 = 0; int[] tmp10575_10572 = _jspx_push_body_count_c_005fforEach_005f0; int tmp10577_10576 = tmp10575_10572[tmp10575_10574];tmp10575_10572[tmp10575_10574] = (tmp10577_10576 - 1); if (tmp10577_10576 <= 0) break;
/* 3814 */                                           out = _jspx_page_context.popBody(); }
/* 3815 */                                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                       } finally {
/* 3817 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3818 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                       }
/* 3820 */                                       out.write(" </table>\n        ");
/* 3821 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3822 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3826 */                                   if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3827 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                   }
/*      */                                   
/* 3830 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3831 */                                   out.write("</td>\n  </tr>\n<br>\n  <tr>\n    <td colspan=\"2\" valign=\"top\" class=\"tdindent\"><br><table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr align=\"center\">\n          <td colspan=\"5\" class=\"tableheadingbborder\">");
/* 3832 */                                   out.print(FormatUtil.getString("am.webclient.historydata.monitordowntimehistory.text"));
/* 3833 */                                   out.write("</td>\n        </tr>\n        <tr>\n          <td width=\"15%\" class=\"columnheading\">");
/* 3834 */                                   out.print(FormatUtil.getString("am.webclient.historydata.options.text"));
/* 3835 */                                   out.write("</td>\n          <td width=\"15%\" class=\"columnheading\">");
/* 3836 */                                   out.print(FormatUtil.getString("am.webclient.historydata.uptime.text"));
/* 3837 */                                   out.write(" %</td>\n          <td width=\"20%\" class=\"columnheading\">");
/* 3838 */                                   out.print(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/* 3839 */                                   out.write("</td>\n          <td width=\"20%\" class=\"columnheading\">");
/* 3840 */                                   out.print(FormatUtil.getString("am.webclient.historydata.capmttr.text"));
/* 3841 */                                   out.write("</td>\n          <td width=\"20%\" class=\"columnheading\">");
/* 3842 */                                   out.print(FormatUtil.getString("am.webclient.historydata.capmtbf.text"));
/* 3843 */                                   out.write("</td>\n        </tr>\n        <tr>\n        ");
/* 3844 */                                   if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3846 */                                   out.write("\n        ");
/* 3847 */                                   String periodvalue = (String)pageContext.getAttribute("today");
/*      */                                   
/* 3849 */                                   out.write("\n          <td class=\"whitegrayborder\"><a href='");
/* 3850 */                                   out.print(forward);
/* 3851 */                                   out.write("&resourceid=");
/* 3852 */                                   out.print(request.getParameter("resourceid"));
/* 3853 */                                   out.write("&period=0&resourcename=");
/* 3854 */                                   out.print(request.getParameter("resourcename"));
/* 3855 */                                   out.write("' class=\"staticlinks\">");
/* 3856 */                                   out.print(FormatUtil.getString(periodvalue));
/* 3857 */                                   out.write("</a></td>\n          <td class=\"whitegrayborder\">");
/* 3858 */                                   if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3860 */                                   out.write("%</td>\n          <td class=\"whitegrayborder\">");
/* 3861 */                                   if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3863 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3864 */                                   if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3866 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3867 */                                   if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3869 */                                   out.write("</td>\n        </tr>\n         ");
/* 3870 */                                   if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3872 */                                   out.write("\n        ");
/* 3873 */                                   String yesterdayperiod = (String)pageContext.getAttribute("yesterday");
/*      */                                   
/* 3875 */                                   out.write("\n        ");
/*      */                                   
/* 3877 */                                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3878 */                                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3879 */                                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 3881 */                                   _jspx_th_c_005fif_005f14.setTest("${downtimehistory[1].yesterdaydata == 'Not Applicable'}");
/* 3882 */                                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3883 */                                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                     for (;;) {
/* 3885 */                                       out.write("\n        <tr title=\"");
/* 3886 */                                       out.print(FormatUtil.getString("am.webclient.historydata.discoverymessage.text"));
/* 3887 */                                       out.write("\">\n\n          <td class=\"yellowgrayborder\">");
/* 3888 */                                       out.print(FormatUtil.getString(yesterdayperiod));
/* 3889 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3890 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 3891 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3892 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 3893 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3894 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 3895 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3896 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 3897 */                                       out.write("</td>\n        </tr>\n        ");
/* 3898 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3899 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3903 */                                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3904 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                   }
/*      */                                   
/* 3907 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3908 */                                   out.write(32);
/*      */                                   
/* 3910 */                                   IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3911 */                                   _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3912 */                                   _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 3914 */                                   _jspx_th_c_005fif_005f15.setTest("${downtimehistory[1].yesterdaydata == 'Applicable'}");
/* 3915 */                                   int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3916 */                                   if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                     for (;;) {
/* 3918 */                                       out.write("\n        <tr>\n          <td class=\"yellowgrayborder\"><a href='");
/* 3919 */                                       out.print(forward);
/* 3920 */                                       out.write("&resourceid=");
/* 3921 */                                       out.print(request.getParameter("resourceid"));
/* 3922 */                                       out.write("&period=3&resourcename=");
/* 3923 */                                       out.print(request.getParameter("resourcename"));
/* 3924 */                                       out.write("'class=\"staticlinks\">");
/* 3925 */                                       out.print(FormatUtil.getString(yesterdayperiod));
/* 3926 */                                       out.write("</a></td>\n          <td class=\"yellowgrayborder\">");
/* 3927 */                                       if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                         return;
/* 3929 */                                       out.write("%</td>\n          <td class=\"yellowgrayborder\">");
/* 3930 */                                       if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                         return;
/* 3932 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3933 */                                       if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                         return;
/* 3935 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3936 */                                       if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                         return;
/* 3938 */                                       out.write("</td>\n        </tr>\n        ");
/* 3939 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3940 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3944 */                                   if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3945 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                   }
/*      */                                   
/* 3948 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3949 */                                   out.write("\n        ");
/* 3950 */                                   if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 3952 */                                   out.write("\n        ");
/* 3953 */                                   String thisweekperiod = (String)pageContext.getAttribute("thisweek");
/*      */                                   
/* 3955 */                                   out.write("\n             ");
/*      */                                   
/* 3957 */                                   IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3958 */                                   _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3959 */                                   _jspx_th_c_005fif_005f16.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 3961 */                                   _jspx_th_c_005fif_005f16.setTest("${downtimehistory[2].thisweekdata == 'Not Applicable'}");
/* 3962 */                                   int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3963 */                                   if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                                     for (;;) {
/* 3965 */                                       out.write("\n        <tr title=\"");
/* 3966 */                                       out.print(FormatUtil.getString("am.webclient.historydata.discoverymessage.text"));
/* 3967 */                                       out.write("\">\n\n          <td class=\"whitegrayborder\">");
/* 3968 */                                       out.print(FormatUtil.getString(thisweekperiod));
/* 3969 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3970 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 3971 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3972 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 3973 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3974 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 3975 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3976 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 3977 */                                       out.write("</td>\n        </tr>\n        ");
/* 3978 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3979 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3983 */                                   if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3984 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                                   }
/*      */                                   
/* 3987 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3988 */                                   out.write(32);
/*      */                                   
/* 3990 */                                   IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3991 */                                   _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3992 */                                   _jspx_th_c_005fif_005f17.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 3994 */                                   _jspx_th_c_005fif_005f17.setTest("${downtimehistory[2].thisweekdata == 'Applicable'}");
/* 3995 */                                   int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3996 */                                   if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                     for (;;) {
/* 3998 */                                       out.write("\n        <tr>\n          <td class=\"whitegrayborder\"><a href='");
/* 3999 */                                       out.print(forward);
/* 4000 */                                       out.write("&resourceid=");
/* 4001 */                                       out.print(request.getParameter("resourceid"));
/* 4002 */                                       out.write("&period=6&resourcename=");
/* 4003 */                                       out.print(request.getParameter("resourcename"));
/* 4004 */                                       out.write("' class=\"staticlinks\">");
/* 4005 */                                       out.print(FormatUtil.getString(thisweekperiod));
/* 4006 */                                       out.write("</a></td>\n          <td class=\"whitegrayborder\">");
/* 4007 */                                       if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                         return;
/* 4009 */                                       out.write("%</td>\n          <td class=\"whitegrayborder\">");
/* 4010 */                                       if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                         return;
/* 4012 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4013 */                                       if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                         return;
/* 4015 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4016 */                                       if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                         return;
/* 4018 */                                       out.write("</td>\n        </tr>\n          ");
/* 4019 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 4020 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4024 */                                   if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 4025 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                                   }
/*      */                                   
/* 4028 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4029 */                                   out.write("\n        ");
/* 4030 */                                   if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4032 */                                   out.write("\n        ");
/* 4033 */                                   String sevendaysperiod = (String)pageContext.getAttribute("7days");
/*      */                                   
/* 4035 */                                   out.write("\n        <tr>\n          <td class=\"yellowgrayborder\"><a href='");
/* 4036 */                                   out.print(forward);
/* 4037 */                                   out.write("&resourceid=");
/* 4038 */                                   out.print(request.getParameter("resourceid"));
/* 4039 */                                   out.write("&period=1&resourcename=");
/* 4040 */                                   out.print(request.getParameter("resourcename"));
/* 4041 */                                   out.write("' class=\"staticlinks\" >");
/* 4042 */                                   out.print(FormatUtil.getString(sevendaysperiod));
/* 4043 */                                   out.write("</a></td>\n          <td class=\"yellowgrayborder\">");
/* 4044 */                                   if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4046 */                                   out.write("%</td>\n          <td class=\"yellowgrayborder\">");
/* 4047 */                                   if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4049 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4050 */                                   if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4052 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4053 */                                   if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4055 */                                   out.write("</td>\n        </tr>\n         ");
/* 4056 */                                   if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4058 */                                   out.write("\n        ");
/* 4059 */                                   String lastweekperiod = (String)pageContext.getAttribute("lastweek");
/*      */                                   
/* 4061 */                                   out.write("\n         ");
/*      */                                   
/* 4063 */                                   IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4064 */                                   _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 4065 */                                   _jspx_th_c_005fif_005f18.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 4067 */                                   _jspx_th_c_005fif_005f18.setTest("${downtimehistory[4].LastWeekdata == 'Not Applicable'}");
/* 4068 */                                   int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 4069 */                                   if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                                     for (;;) {
/* 4071 */                                       out.write("\n\n        <tr title=\"");
/* 4072 */                                       out.print(FormatUtil.getString("am.webclient.historydata.discoverymessagelastweek.text"));
/* 4073 */                                       out.write("\">\n           <td class=\"whitegrayborder\">");
/* 4074 */                                       out.print(FormatUtil.getString(lastweekperiod));
/* 4075 */                                       out.write("</td>\n           <td class=\"whitegrayborder\">");
/* 4076 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4077 */                                       out.write("</td>\n           <td class=\"whitegrayborder\">");
/* 4078 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4079 */                                       out.write("</td>\n           <td class=\"whitegrayborder\">");
/* 4080 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4081 */                                       out.write("</td>\n           <td class=\"whitegrayborder\">");
/* 4082 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4083 */                                       out.write("</td>\n        </tr>");
/* 4084 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 4085 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4089 */                                   if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 4090 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                                   }
/*      */                                   
/* 4093 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 4094 */                                   out.write(32);
/*      */                                   
/* 4096 */                                   IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4097 */                                   _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 4098 */                                   _jspx_th_c_005fif_005f19.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 4100 */                                   _jspx_th_c_005fif_005f19.setTest("${downtimehistory[4].LastWeekdata == 'Applicable'}");
/* 4101 */                                   int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 4102 */                                   if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                                     for (;;) {
/* 4104 */                                       out.write("\n        <tr>\n          <td class=\"whitegrayborder\"><a href='");
/* 4105 */                                       out.print(forward);
/* 4106 */                                       out.write("&resourceid=");
/* 4107 */                                       out.print(request.getParameter("resourceid"));
/* 4108 */                                       out.write("&period=12&resourcename=");
/* 4109 */                                       out.print(request.getParameter("resourcename"));
/* 4110 */                                       out.write("' class=\"staticlinks\">");
/* 4111 */                                       out.print(FormatUtil.getString(lastweekperiod));
/* 4112 */                                       out.write("</a></td>\n          <td class=\"whitegrayborder\">");
/* 4113 */                                       if (_jspx_meth_c_005fout_005f48(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                                         return;
/* 4115 */                                       out.write("%</td>\n          <td class=\"whitegrayborder\">");
/* 4116 */                                       if (_jspx_meth_c_005fout_005f49(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                                         return;
/* 4118 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4119 */                                       if (_jspx_meth_c_005fout_005f50(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                                         return;
/* 4121 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4122 */                                       if (_jspx_meth_c_005fout_005f51(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                                         return;
/* 4124 */                                       out.write("</td>\n        </tr>\n        ");
/* 4125 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 4126 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4130 */                                   if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 4131 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                                   }
/*      */                                   
/* 4134 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 4135 */                                   out.write("\n        ");
/* 4136 */                                   if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4138 */                                   out.write("\n        ");
/* 4139 */                                   String thismonthperiod = (String)pageContext.getAttribute("thismonth");
/*      */                                   
/* 4141 */                                   out.write("\n\n        <tr>\n          <td class=\"yellowgrayborder\"><a href='");
/* 4142 */                                   out.print(forward);
/* 4143 */                                   out.write("&resourceid=");
/* 4144 */                                   out.print(request.getParameter("resourceid"));
/* 4145 */                                   out.write("&period=7&resourcename=");
/* 4146 */                                   out.print(request.getParameter("resourcename"));
/* 4147 */                                   out.write("' class=\"staticlinks\">");
/* 4148 */                                   out.print(FormatUtil.getString(thismonthperiod));
/* 4149 */                                   out.write("</a></td>\n          <td class=\"yellowgrayborder\">");
/* 4150 */                                   if (_jspx_meth_c_005fout_005f53(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4152 */                                   out.write("%</td>\n          <td class=\"yellowgrayborder\">");
/* 4153 */                                   if (_jspx_meth_c_005fout_005f54(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4155 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4156 */                                   if (_jspx_meth_c_005fout_005f55(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4158 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4159 */                                   if (_jspx_meth_c_005fout_005f56(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4161 */                                   out.write("</td>\n        </tr>\n        ");
/* 4162 */                                   if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4164 */                                   out.write("\n        ");
/* 4165 */                                   String thirtydaysperiod = (String)pageContext.getAttribute("30days");
/*      */                                   
/* 4167 */                                   out.write("\n        <tr>\n          <td class=\"whitegrayborder\"><a href='");
/* 4168 */                                   out.print(forward);
/* 4169 */                                   out.write("&resourceid=");
/* 4170 */                                   out.print(request.getParameter("resourceid"));
/* 4171 */                                   out.write("&period=2&resourcename=");
/* 4172 */                                   out.print(request.getParameter("resourcename"));
/* 4173 */                                   out.write("' class=\"staticlinks\">");
/* 4174 */                                   out.print(FormatUtil.getString(thirtydaysperiod));
/* 4175 */                                   out.write("</a></td>\n          <td class=\"whitegrayborder\">");
/* 4176 */                                   if (_jspx_meth_c_005fout_005f58(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4178 */                                   out.write("%</td>\n          <td class=\"whitegrayborder\">");
/* 4179 */                                   if (_jspx_meth_c_005fout_005f59(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4181 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4182 */                                   if (_jspx_meth_c_005fout_005f60(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4184 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4185 */                                   if (_jspx_meth_c_005fout_005f61(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4187 */                                   out.write("</td>\n        </tr>\n        ");
/* 4188 */                                   if (_jspx_meth_c_005fset_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4190 */                                   out.write("\n        ");
/* 4191 */                                   String lastmonthperiod = (String)pageContext.getAttribute("lastmonth");
/*      */                                   
/* 4193 */                                   out.write("\n         ");
/*      */                                   
/* 4195 */                                   IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4196 */                                   _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 4197 */                                   _jspx_th_c_005fif_005f20.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 4199 */                                   _jspx_th_c_005fif_005f20.setTest("${downtimehistory[7].LastMonthdata == 'Not Applicable'}");
/* 4200 */                                   int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 4201 */                                   if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                                     for (;;) {
/* 4203 */                                       out.write("\n\n        <tr title=\"");
/* 4204 */                                       out.print(FormatUtil.getString("am.webclient.historydata.discoverymessagelastmonth.text"));
/* 4205 */                                       out.write("\">\n          <td class=\"yellowgrayborder\">");
/* 4206 */                                       out.print(FormatUtil.getString(lastmonthperiod));
/* 4207 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4208 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4209 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4210 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4211 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4212 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4213 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4214 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4215 */                                       out.write("</td>\n        </tr>\n        ");
/* 4216 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 4217 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4221 */                                   if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 4222 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                                   }
/*      */                                   
/* 4225 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4226 */                                   out.write(32);
/*      */                                   
/* 4228 */                                   IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4229 */                                   _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 4230 */                                   _jspx_th_c_005fif_005f21.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 4232 */                                   _jspx_th_c_005fif_005f21.setTest("${downtimehistory[7].LastMonthdata == 'Applicable'}");
/* 4233 */                                   int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 4234 */                                   if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                     for (;;) {
/* 4236 */                                       out.write("\n        <tr>\n          <td class=\"yellowgrayborder\"><a href='");
/* 4237 */                                       out.print(forward);
/* 4238 */                                       out.write("&resourceid=");
/* 4239 */                                       out.print(request.getParameter("resourceid"));
/* 4240 */                                       out.write("&period=11&resourcename=");
/* 4241 */                                       out.print(request.getParameter("resourcename"));
/* 4242 */                                       out.write("' class=\"staticlinks\">");
/* 4243 */                                       out.print(FormatUtil.getString(lastmonthperiod));
/* 4244 */                                       out.write("</a></td>\n          <td class=\"yellowgrayborder\">");
/* 4245 */                                       if (_jspx_meth_c_005fout_005f63(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                                         return;
/* 4247 */                                       out.write("%</td>\n          <td class=\"yellowgrayborder\">");
/* 4248 */                                       if (_jspx_meth_c_005fout_005f64(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                                         return;
/* 4250 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4251 */                                       if (_jspx_meth_c_005fout_005f65(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                                         return;
/* 4253 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4254 */                                       if (_jspx_meth_c_005fout_005f66(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                                         return;
/* 4256 */                                       out.write("</td>\n        </tr>\n        ");
/* 4257 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 4258 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4262 */                                   if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 4263 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                                   }
/*      */                                   
/* 4266 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 4267 */                                   out.write("\n        ");
/* 4268 */                                   if (_jspx_meth_c_005fset_005f10(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4270 */                                   out.write("\n        ");
/* 4271 */                                   String thisquarterperiod = (String)pageContext.getAttribute("thisquarter");
/*      */                                   
/* 4273 */                                   out.write("\n        <tr>\n          <td class=\"whitegrayborder\"><a href='");
/* 4274 */                                   out.print(forward);
/* 4275 */                                   out.write("&resourceid=");
/* 4276 */                                   out.print(request.getParameter("resourceid"));
/* 4277 */                                   out.write("&period=9&resourcename=");
/* 4278 */                                   out.print(request.getParameter("resourcename"));
/* 4279 */                                   out.write("' class=\"staticlinks\">");
/* 4280 */                                   out.print(FormatUtil.getString(thisquarterperiod));
/* 4281 */                                   out.write("</a></td>\n          <td class=\"whitegrayborder\">");
/* 4282 */                                   if (_jspx_meth_c_005fout_005f68(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4284 */                                   out.write("%</td>\n          <td class=\"whitegrayborder\">");
/* 4285 */                                   if (_jspx_meth_c_005fout_005f69(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4287 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4288 */                                   if (_jspx_meth_c_005fout_005f70(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4290 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4291 */                                   if (_jspx_meth_c_005fout_005f71(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4293 */                                   out.write("</td>\n        </tr>\n        ");
/* 4294 */                                   if (_jspx_meth_c_005fset_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4296 */                                   out.write("\n        ");
/* 4297 */                                   String thisyearperiod = (String)pageContext.getAttribute("thisyear");
/*      */                                   
/* 4299 */                                   out.write(10);
/* 4300 */                                   out.write(9);
/* 4301 */                                   out.write(32);
/*      */                                   
/* 4303 */                                   IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4304 */                                   _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 4305 */                                   _jspx_th_c_005fif_005f22.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 4307 */                                   _jspx_th_c_005fif_005f22.setTest("${downtimehistory[9].LastMonthdata == 'Not Applicable'}");
/* 4308 */                                   int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 4309 */                                   if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                     for (;;) {
/* 4311 */                                       out.write("\n\n        <tr title=\"");
/* 4312 */                                       out.print(FormatUtil.getString("am.webclient.historydata.discoverymessagelastmonth.text"));
/* 4313 */                                       out.write("\">\n          <td class=\"yellowgrayborder\">");
/* 4314 */                                       out.print(FormatUtil.getString(thisyearperiod));
/* 4315 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4316 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4317 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4318 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4319 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4320 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4321 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4322 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4323 */                                       out.write("</td>\n        </tr>\n        ");
/* 4324 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 4325 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4329 */                                   if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 4330 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                                   }
/*      */                                   
/* 4333 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 4334 */                                   out.write(32);
/*      */                                   
/* 4336 */                                   IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4337 */                                   _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 4338 */                                   _jspx_th_c_005fif_005f23.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 4340 */                                   _jspx_th_c_005fif_005f23.setTest("${downtimehistory[9].LastMonthdata == 'Applicable'}");
/* 4341 */                                   int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 4342 */                                   if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                     for (;;) {
/* 4344 */                                       out.write("\n        <tr>\n          <td class=\"yellowgrayborder\"><a href='");
/* 4345 */                                       out.print(forward);
/* 4346 */                                       out.write("&resourceid=");
/* 4347 */                                       out.print(request.getParameter("resourceid"));
/* 4348 */                                       out.write("&period=8&resourcename=");
/* 4349 */                                       out.print(request.getParameter("resourcename"));
/* 4350 */                                       out.write("' class=\"staticlinks\">");
/* 4351 */                                       out.print(FormatUtil.getString(thisyearperiod));
/* 4352 */                                       out.write("</a></td>\n          <td class=\"yellowgrayborder\">");
/* 4353 */                                       if (_jspx_meth_c_005fout_005f73(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                         return;
/* 4355 */                                       out.write("%</td>\n          <td class=\"yellowgrayborder\">");
/* 4356 */                                       if (_jspx_meth_c_005fout_005f74(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                         return;
/* 4358 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4359 */                                       if (_jspx_meth_c_005fout_005f75(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                         return;
/* 4361 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4362 */                                       if (_jspx_meth_c_005fout_005f76(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                         return;
/* 4364 */                                       out.write("</td>\n        </tr>\n\t");
/* 4365 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 4366 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4370 */                                   if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 4371 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                                   }
/*      */                                   
/* 4374 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 4375 */                                   out.write("\n        ");
/* 4376 */                                   if (_jspx_meth_c_005fset_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                     return;
/* 4378 */                                   out.write("\n        ");
/* 4379 */                                   String lastoneyearperiod = (String)pageContext.getAttribute("lastoneyear");
/*      */                                   
/* 4381 */                                   out.write(10);
/* 4382 */                                   out.write(9);
/*      */                                   
/* 4384 */                                   IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4385 */                                   _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 4386 */                                   _jspx_th_c_005fif_005f24.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 4388 */                                   _jspx_th_c_005fif_005f24.setTest("${downtimehistory[10].LastWeekdata == 'Not Applicable'}");
/* 4389 */                                   int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 4390 */                                   if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */                                     for (;;) {
/* 4392 */                                       out.write("\n\n        <tr title=\"");
/* 4393 */                                       out.print(FormatUtil.getString("am.webclient.historydata.discoverymessagelastmonth.text"));
/* 4394 */                                       out.write("\">\n          <td class=\"yellowgrayborder\">");
/* 4395 */                                       out.print(FormatUtil.getString(lastoneyearperiod));
/* 4396 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4397 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4398 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4399 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4400 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4401 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4402 */                                       out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4403 */                                       out.print(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/* 4404 */                                       out.write("</td>\n        </tr>\n        ");
/* 4405 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 4406 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4410 */                                   if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 4411 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24); return;
/*      */                                   }
/*      */                                   
/* 4414 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 4415 */                                   out.write(32);
/*      */                                   
/* 4417 */                                   IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4418 */                                   _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 4419 */                                   _jspx_th_c_005fif_005f25.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 4421 */                                   _jspx_th_c_005fif_005f25.setTest("${downtimehistory[10].LastWeekdata == 'Applicable'}");
/* 4422 */                                   int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 4423 */                                   if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                                     for (;;) {
/* 4425 */                                       out.write("\n        <tr>\n          <td class=\"whitegrayborder\"><a href='");
/* 4426 */                                       out.print(forward);
/* 4427 */                                       out.write("&resourceid=");
/* 4428 */                                       out.print(request.getParameter("resourceid"));
/* 4429 */                                       out.write("&period=5&resourcename=");
/* 4430 */                                       out.print(request.getParameter("resourcename"));
/* 4431 */                                       out.write("' class=\"staticlinks\">");
/* 4432 */                                       out.print(FormatUtil.getString(lastoneyearperiod));
/* 4433 */                                       out.write("</a></td>\n          <td class=\"whitegrayborder\">");
/* 4434 */                                       if (_jspx_meth_c_005fout_005f78(_jspx_th_c_005fif_005f25, _jspx_page_context))
/*      */                                         return;
/* 4436 */                                       out.write("%</td>\n          <td class=\"whitegrayborder\">");
/* 4437 */                                       if (_jspx_meth_c_005fout_005f79(_jspx_th_c_005fif_005f25, _jspx_page_context))
/*      */                                         return;
/* 4439 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4440 */                                       if (_jspx_meth_c_005fout_005f80(_jspx_th_c_005fif_005f25, _jspx_page_context))
/*      */                                         return;
/* 4442 */                                       out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4443 */                                       if (_jspx_meth_c_005fout_005f81(_jspx_th_c_005fif_005f25, _jspx_page_context))
/*      */                                         return;
/* 4445 */                                       out.write("</td>\n        </tr>\n\t");
/* 4446 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 4447 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4451 */                                   if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 4452 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                                   }
/*      */                                   
/* 4455 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 4456 */                                   out.write("\n      </table></td>\n  </tr>\n  <tr>\n    <td colspan=2 class=\"bodytext\"> <blockquote style=\"color:#686868;\"> <b><br>\n        ");
/* 4457 */                                   out.print(FormatUtil.getString("am.webclient.historydata.mttr.text"));
/* 4458 */                                   out.write("</b>\n        <blockquote style=\"color:#686868;\"> ");
/* 4459 */                                   out.print(FormatUtil.getString("am.webclient.availablityreport.mttrnote.text"));
/* 4460 */                                   out.write(". <br>\n          <br>\n          ");
/* 4461 */                                   out.print(FormatUtil.getString("am.webclient.availablityreport.mttrsmallnote.text"));
/* 4462 */                                   out.write(". <br>\n          <br>\n        </blockquote style=\"color:#686868;\">\n        <b>");
/* 4463 */                                   out.print(FormatUtil.getString("am.webclient.historydata.mtbf.text"));
/* 4464 */                                   out.write("</b>\n        <blockquote style=\"color:#686868;\"> ");
/* 4465 */                                   out.print(FormatUtil.getString("am.webclient.availablityreport.mtbfnote.text"));
/* 4466 */                                   out.write(".\n          <br>\n          <br>\n          ");
/* 4467 */                                   out.print(FormatUtil.getString("am.webclient.availablityreport.mtbfsmallnote.text"));
/* 4468 */                                   out.write(". </blockquote>\n        <b> *</b> ");
/* 4469 */                                   out.print(FormatUtil.getString("am.webclient.availablityreport.downtimenote.text"));
/* 4470 */                                   out.write(". <br>\n        <br>\n      </blockquote></td>\n  </tr>\n</table>\n\n\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\" align=\"center\">\n  <tr>\n<td class=\"tablebottom align-center\">\n");
/* 4471 */                                   if ((request.getParameter("Report") == null) || (!request.getParameter("Report").equalsIgnoreCase("true")))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/* 4476 */                                     out.write("\n<form name=\"closeform1\" style=\"display: inline\">\n <input name=\"button\" value=\"");
/* 4477 */                                     out.print(FormatUtil.getString("am.webclient.prolic.close.text"));
/* 4478 */                                     out.write("\" type=\"button\" class=\"buttons btn_link\"  onClick=\"parent.window.close()\">\n</form>\n");
/*      */                                   }
/* 4480 */                                   out.write("\n</td>\n  </tr>\n</table>\n");
/* 4481 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4482 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4486 */                               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4487 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*      */                               }
/*      */                               else {
/* 4490 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4491 */                                 out.write("\n<br>\n");
/* 4492 */                                 response.setContentType("text/html;charset=UTF-8");
/* 4493 */                                 out.write(10);
/*      */                                 
/* 4495 */                                 if ("true".equalsIgnoreCase(System.getProperty("DEMOUSER")))
/*      */                                 {
/*      */ 
/* 4498 */                                   out.write("\n<script type=\"text/javascript\">\n\tvar _gaq = _gaq || [];\t\t\t\t\t\t\t//NO I18N\n\t_gaq.push(['_setAccount', 'UA-202658-68']);\t\t//NO I18N\n\t_gaq.push(['_trackPageview']);\t\t\t\t\t//NO I18N\n\n\t(function() {\n\tvar ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\t//NO I18N\n\tga.src = '//www.google-analytics.com/ga.js';\t//NO I18N\n\t\n\tvar s = document.getElementsByTagName('script')[0]; \t//NO I18N\n\ts.parentNode.insertBefore(ga, s);\t\t\t\t\t\t//NO I18N\n\t})();\n</script>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4502 */                                 out.write("\n</body>\n</html>\n");
/*      */                               }
/* 4504 */                             } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4505 */         out = _jspx_out;
/* 4506 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4507 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4508 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4511 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4517 */     PageContext pageContext = _jspx_page_context;
/* 4518 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4520 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4521 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4522 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 4524 */     _jspx_th_c_005fif_005f0.setTest("${param.Report ==null}");
/* 4525 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4526 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4528 */         out.write("\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/appmanager.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 4529 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4530 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4534 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4535 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4536 */       return true;
/*      */     }
/* 4538 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4544 */     PageContext pageContext = _jspx_page_context;
/* 4545 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4547 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4548 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4549 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 4551 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 4553 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 4554 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4555 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4556 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4557 */       return true;
/*      */     }
/* 4559 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4565 */     PageContext pageContext = _jspx_page_context;
/* 4566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4568 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4569 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4570 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4572 */     _jspx_th_c_005fif_005f3.setTest("${param.period !='4'}");
/* 4573 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4574 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 4576 */         out.write("\n\n    document.forms[0].reporttype.value=type;\n    document.forms[0].submit();\n    document.forms[0].reporttype.value=\"html\";\n");
/* 4577 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4578 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4582 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4583 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4584 */       return true;
/*      */     }
/* 4586 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4587 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4592 */     PageContext pageContext = _jspx_page_context;
/* 4593 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4595 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4596 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 4597 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4599 */     _jspx_th_c_005fif_005f4.setTest("${param.period =='4'}");
/* 4600 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 4601 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 4603 */         out.write("\n    document.forms[1].reporttype.value=type;\n    document.forms[1].submit();\n    document.forms[1].reporttype.value=\"html\";\n");
/* 4604 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4605 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4609 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 4610 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4611 */       return true;
/*      */     }
/* 4613 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4614 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4619 */     PageContext pageContext = _jspx_page_context;
/* 4620 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4622 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4623 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 4624 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4626 */     _jspx_th_html_005fhidden_005f0.setProperty("actionMethod");
/* 4627 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 4628 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 4629 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4630 */       return true;
/*      */     }
/* 4632 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4633 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4638 */     PageContext pageContext = _jspx_page_context;
/* 4639 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4641 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4642 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 4643 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4645 */     _jspx_th_html_005fhidden_005f1.setProperty("attribute");
/* 4646 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 4647 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 4648 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 4649 */       return true;
/*      */     }
/* 4651 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 4652 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4657 */     PageContext pageContext = _jspx_page_context;
/* 4658 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4660 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4661 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 4662 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4664 */     _jspx_th_html_005fhidden_005f2.setProperty("unit");
/* 4665 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 4666 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 4667 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 4668 */       return true;
/*      */     }
/* 4670 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 4671 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4676 */     PageContext pageContext = _jspx_page_context;
/* 4677 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4679 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4680 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 4681 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4683 */     _jspx_th_html_005fhidden_005f3.setProperty("attributeName");
/* 4684 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 4685 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 4686 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 4687 */       return true;
/*      */     }
/* 4689 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 4690 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4695 */     PageContext pageContext = _jspx_page_context;
/* 4696 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4698 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4699 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/* 4700 */     _jspx_th_html_005fhidden_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4702 */     _jspx_th_html_005fhidden_005f4.setProperty("resourceid");
/* 4703 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/* 4704 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/* 4705 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 4706 */       return true;
/*      */     }
/* 4708 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 4709 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4714 */     PageContext pageContext = _jspx_page_context;
/* 4715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4717 */     HiddenTag _jspx_th_html_005fhidden_005f5 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4718 */     _jspx_th_html_005fhidden_005f5.setPageContext(_jspx_page_context);
/* 4719 */     _jspx_th_html_005fhidden_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4721 */     _jspx_th_html_005fhidden_005f5.setProperty("resourceType");
/* 4722 */     int _jspx_eval_html_005fhidden_005f5 = _jspx_th_html_005fhidden_005f5.doStartTag();
/* 4723 */     if (_jspx_th_html_005fhidden_005f5.doEndTag() == 5) {
/* 4724 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 4725 */       return true;
/*      */     }
/* 4727 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 4728 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4733 */     PageContext pageContext = _jspx_page_context;
/* 4734 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4736 */     HiddenTag _jspx_th_html_005fhidden_005f6 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4737 */     _jspx_th_html_005fhidden_005f6.setPageContext(_jspx_page_context);
/* 4738 */     _jspx_th_html_005fhidden_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4740 */     _jspx_th_html_005fhidden_005f6.setProperty("period");
/* 4741 */     int _jspx_eval_html_005fhidden_005f6 = _jspx_th_html_005fhidden_005f6.doStartTag();
/* 4742 */     if (_jspx_th_html_005fhidden_005f6.doEndTag() == 5) {
/* 4743 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/* 4744 */       return true;
/*      */     }
/* 4746 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/* 4747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4752 */     PageContext pageContext = _jspx_page_context;
/* 4753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4755 */     HiddenTag _jspx_th_html_005fhidden_005f7 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4756 */     _jspx_th_html_005fhidden_005f7.setPageContext(_jspx_page_context);
/* 4757 */     _jspx_th_html_005fhidden_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4759 */     _jspx_th_html_005fhidden_005f7.setProperty("numberOfRows");
/* 4760 */     int _jspx_eval_html_005fhidden_005f7 = _jspx_th_html_005fhidden_005f7.doStartTag();
/* 4761 */     if (_jspx_th_html_005fhidden_005f7.doEndTag() == 5) {
/* 4762 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/* 4763 */       return true;
/*      */     }
/* 4765 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/* 4766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4771 */     PageContext pageContext = _jspx_page_context;
/* 4772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4774 */     HiddenTag _jspx_th_html_005fhidden_005f8 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4775 */     _jspx_th_html_005fhidden_005f8.setPageContext(_jspx_page_context);
/* 4776 */     _jspx_th_html_005fhidden_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4778 */     _jspx_th_html_005fhidden_005f8.setProperty("startDate");
/* 4779 */     int _jspx_eval_html_005fhidden_005f8 = _jspx_th_html_005fhidden_005f8.doStartTag();
/* 4780 */     if (_jspx_th_html_005fhidden_005f8.doEndTag() == 5) {
/* 4781 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f8);
/* 4782 */       return true;
/*      */     }
/* 4784 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f8);
/* 4785 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4790 */     PageContext pageContext = _jspx_page_context;
/* 4791 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4793 */     HiddenTag _jspx_th_html_005fhidden_005f9 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4794 */     _jspx_th_html_005fhidden_005f9.setPageContext(_jspx_page_context);
/* 4795 */     _jspx_th_html_005fhidden_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4797 */     _jspx_th_html_005fhidden_005f9.setProperty("endDate");
/* 4798 */     int _jspx_eval_html_005fhidden_005f9 = _jspx_th_html_005fhidden_005f9.doStartTag();
/* 4799 */     if (_jspx_th_html_005fhidden_005f9.doEndTag() == 5) {
/* 4800 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f9);
/* 4801 */       return true;
/*      */     }
/* 4803 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f9);
/* 4804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4809 */     PageContext pageContext = _jspx_page_context;
/* 4810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4812 */     HiddenTag _jspx_th_html_005fhidden_005f10 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 4813 */     _jspx_th_html_005fhidden_005f10.setPageContext(_jspx_page_context);
/* 4814 */     _jspx_th_html_005fhidden_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4816 */     _jspx_th_html_005fhidden_005f10.setProperty("Report");
/*      */     
/* 4818 */     _jspx_th_html_005fhidden_005f10.setValue("true");
/* 4819 */     int _jspx_eval_html_005fhidden_005f10 = _jspx_th_html_005fhidden_005f10.doStartTag();
/* 4820 */     if (_jspx_th_html_005fhidden_005f10.doEndTag() == 5) {
/* 4821 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f10);
/* 4822 */       return true;
/*      */     }
/* 4824 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f10);
/* 4825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4830 */     PageContext pageContext = _jspx_page_context;
/* 4831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4833 */     HiddenTag _jspx_th_html_005fhidden_005f11 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 4834 */     _jspx_th_html_005fhidden_005f11.setPageContext(_jspx_page_context);
/* 4835 */     _jspx_th_html_005fhidden_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4837 */     _jspx_th_html_005fhidden_005f11.setProperty("reporttype");
/*      */     
/* 4839 */     _jspx_th_html_005fhidden_005f11.setValue("html");
/* 4840 */     int _jspx_eval_html_005fhidden_005f11 = _jspx_th_html_005fhidden_005f11.doStartTag();
/* 4841 */     if (_jspx_th_html_005fhidden_005f11.doEndTag() == 5) {
/* 4842 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f11);
/* 4843 */       return true;
/*      */     }
/* 4845 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f11);
/* 4846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4851 */     PageContext pageContext = _jspx_page_context;
/* 4852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4854 */     HiddenTag _jspx_th_html_005fhidden_005f12 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4855 */     _jspx_th_html_005fhidden_005f12.setPageContext(_jspx_page_context);
/* 4856 */     _jspx_th_html_005fhidden_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4858 */     _jspx_th_html_005fhidden_005f12.setProperty("resid");
/* 4859 */     int _jspx_eval_html_005fhidden_005f12 = _jspx_th_html_005fhidden_005f12.doStartTag();
/* 4860 */     if (_jspx_th_html_005fhidden_005f12.doEndTag() == 5) {
/* 4861 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f12);
/* 4862 */       return true;
/*      */     }
/* 4864 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f12);
/* 4865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f13(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4870 */     PageContext pageContext = _jspx_page_context;
/* 4871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4873 */     HiddenTag _jspx_th_html_005fhidden_005f13 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 4874 */     _jspx_th_html_005fhidden_005f13.setPageContext(_jspx_page_context);
/* 4875 */     _jspx_th_html_005fhidden_005f13.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4877 */     _jspx_th_html_005fhidden_005f13.setProperty("reporttype");
/*      */     
/* 4879 */     _jspx_th_html_005fhidden_005f13.setValue("html");
/* 4880 */     int _jspx_eval_html_005fhidden_005f13 = _jspx_th_html_005fhidden_005f13.doStartTag();
/* 4881 */     if (_jspx_th_html_005fhidden_005f13.doEndTag() == 5) {
/* 4882 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f13);
/* 4883 */       return true;
/*      */     }
/* 4885 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f13);
/* 4886 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4891 */     PageContext pageContext = _jspx_page_context;
/* 4892 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4894 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 4895 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4896 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 4898 */     _jspx_th_html_005ftext_005f0.setSize("16");
/*      */     
/* 4900 */     _jspx_th_html_005ftext_005f0.setProperty("startDate");
/*      */     
/* 4902 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/* 4904 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/* 4906 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 4908 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 4909 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4910 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4911 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4912 */       return true;
/*      */     }
/* 4914 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4920 */     PageContext pageContext = _jspx_page_context;
/* 4921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4923 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 4924 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4925 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 4927 */     _jspx_th_html_005ftext_005f1.setProperty("endDate");
/*      */     
/* 4929 */     _jspx_th_html_005ftext_005f1.setSize("16");
/*      */     
/* 4931 */     _jspx_th_html_005ftext_005f1.setStyleId("end");
/*      */     
/* 4933 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 4935 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 4937 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 4938 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4939 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4940 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4941 */       return true;
/*      */     }
/* 4943 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4944 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f14(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4949 */     PageContext pageContext = _jspx_page_context;
/* 4950 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4952 */     HiddenTag _jspx_th_html_005fhidden_005f14 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 4953 */     _jspx_th_html_005fhidden_005f14.setPageContext(_jspx_page_context);
/* 4954 */     _jspx_th_html_005fhidden_005f14.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 4956 */     _jspx_th_html_005fhidden_005f14.setProperty("reporttype");
/*      */     
/* 4958 */     _jspx_th_html_005fhidden_005f14.setValue("html");
/* 4959 */     int _jspx_eval_html_005fhidden_005f14 = _jspx_th_html_005fhidden_005f14.doStartTag();
/* 4960 */     if (_jspx_th_html_005fhidden_005f14.doEndTag() == 5) {
/* 4961 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f14);
/* 4962 */       return true;
/*      */     }
/* 4964 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f14);
/* 4965 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4970 */     PageContext pageContext = _jspx_page_context;
/* 4971 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4973 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4974 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4975 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 4977 */     _jspx_th_c_005fout_005f1.setValue("${STATUS}");
/* 4978 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4979 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4980 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4981 */       return true;
/*      */     }
/* 4983 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4989 */     PageContext pageContext = _jspx_page_context;
/* 4990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4992 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4993 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4994 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4996 */     _jspx_th_c_005fset_005f0.setVar("rname");
/* 4997 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4998 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4999 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5000 */         out = _jspx_page_context.pushBody();
/* 5001 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5002 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5005 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5006 */           return true;
/* 5007 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5008 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5011 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5012 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5015 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5016 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5017 */       return true;
/*      */     }
/* 5019 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5020 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5025 */     PageContext pageContext = _jspx_page_context;
/* 5026 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5028 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5029 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5030 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5032 */     _jspx_th_c_005fout_005f2.setValue("${resourcename}");
/* 5033 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5034 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5035 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5036 */       return true;
/*      */     }
/* 5038 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5039 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5044 */     PageContext pageContext = _jspx_page_context;
/* 5045 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5047 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5048 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5049 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5051 */     _jspx_th_c_005fset_005f1.setVar("rperiod");
/* 5052 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5053 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 5054 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5055 */         out = _jspx_page_context.pushBody();
/* 5056 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 5057 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5060 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 5061 */           return true;
/* 5062 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 5063 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5066 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5067 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5070 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5071 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 5072 */       return true;
/*      */     }
/* 5074 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 5075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5080 */     PageContext pageContext = _jspx_page_context;
/* 5081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5083 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5084 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5085 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 5087 */     _jspx_th_c_005fout_005f3.setValue("${timeperiod}");
/* 5088 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5089 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5090 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5091 */       return true;
/*      */     }
/* 5093 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5099 */     PageContext pageContext = _jspx_page_context;
/* 5100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5102 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 5103 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 5104 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 5106 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 5108 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 5109 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 5110 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 5111 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 5112 */       return true;
/*      */     }
/* 5114 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 5115 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5120 */     PageContext pageContext = _jspx_page_context;
/* 5121 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5123 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5124 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5125 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5127 */     _jspx_th_c_005fif_005f7.setTest("${period =='1'}");
/* 5128 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5129 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5131 */         out.write("\n                <td width=\"90%\" align=\"right\"><img src=\"../images/icon_7days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\">\n                ");
/* 5132 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5133 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5137 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5138 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5139 */       return true;
/*      */     }
/* 5141 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5142 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5147 */     PageContext pageContext = _jspx_page_context;
/* 5148 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5150 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5151 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 5152 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5154 */     _jspx_th_c_005fif_005f9.setTest("${period =='2'}");
/* 5155 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 5156 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 5158 */         out.write("\n                <td width=\"10%\"><img src=\"../images/icon_30days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"></a>\n                ");
/* 5159 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 5160 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5164 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 5165 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5166 */       return true;
/*      */     }
/* 5168 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5169 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5174 */     PageContext pageContext = _jspx_page_context;
/* 5175 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5177 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5178 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5179 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5181 */     _jspx_th_c_005fout_005f4.setValue("${summary.totaldowntime}");
/* 5182 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5183 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5184 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5185 */       return true;
/*      */     }
/* 5187 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5188 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5193 */     PageContext pageContext = _jspx_page_context;
/* 5194 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5196 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5197 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5198 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5200 */     _jspx_th_c_005fout_005f5.setValue("${summary.DowntimePercentage}");
/* 5201 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5202 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5203 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5204 */       return true;
/*      */     }
/* 5206 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5207 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5212 */     PageContext pageContext = _jspx_page_context;
/* 5213 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5215 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5216 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5217 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5219 */     _jspx_th_c_005fout_005f6.setValue("${summary.uptimepercentage}");
/* 5220 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5221 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5222 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5223 */       return true;
/*      */     }
/* 5225 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5231 */     PageContext pageContext = _jspx_page_context;
/* 5232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5234 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5235 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5236 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5238 */     _jspx_th_c_005fout_005f7.setValue("${summary.uptimepercentage+summary.UnManagedPercentage+summary.ScheduledPercentage}");
/* 5239 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5240 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5241 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5242 */       return true;
/*      */     }
/* 5244 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5245 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5250 */     PageContext pageContext = _jspx_page_context;
/* 5251 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5253 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5254 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5255 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5257 */     _jspx_th_c_005fout_005f8.setValue("${summary.mttr}");
/* 5258 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5259 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5260 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5261 */       return true;
/*      */     }
/* 5263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5269 */     PageContext pageContext = _jspx_page_context;
/* 5270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5272 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5273 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5274 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5276 */     _jspx_th_c_005fout_005f9.setValue("${summary.mtbf}");
/* 5277 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5278 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5279 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5280 */       return true;
/*      */     }
/* 5282 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5288 */     PageContext pageContext = _jspx_page_context;
/* 5289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5291 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5292 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5293 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5295 */     _jspx_th_c_005fout_005f10.setValue("${summary.UnManagedTime}");
/* 5296 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5297 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5298 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5299 */       return true;
/*      */     }
/* 5301 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5307 */     PageContext pageContext = _jspx_page_context;
/* 5308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5310 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5311 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5312 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5314 */     _jspx_th_c_005fout_005f11.setValue("${summary.UnManagedPercentage}");
/* 5315 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5316 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5317 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5318 */       return true;
/*      */     }
/* 5320 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5326 */     PageContext pageContext = _jspx_page_context;
/* 5327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5329 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5330 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5331 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5333 */     _jspx_th_c_005fout_005f12.setValue("${summary.ScheduledTime}");
/* 5334 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5335 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5336 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5337 */       return true;
/*      */     }
/* 5339 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5345 */     PageContext pageContext = _jspx_page_context;
/* 5346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5348 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5349 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5350 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5352 */     _jspx_th_c_005fout_005f13.setValue("${summary.ScheduledPercentage}");
/* 5353 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5354 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5355 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5356 */       return true;
/*      */     }
/* 5358 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5364 */     PageContext pageContext = _jspx_page_context;
/* 5365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5367 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 5368 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 5369 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5371 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${row.downtime}");
/*      */     
/* 5373 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 5374 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 5375 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 5376 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 5377 */       return true;
/*      */     }
/* 5379 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 5380 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5385 */     PageContext pageContext = _jspx_page_context;
/* 5386 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5388 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 5389 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 5390 */     _jspx_th_fmt_005fformatDate_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5392 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${row.uptime}");
/*      */     
/* 5394 */     _jspx_th_fmt_005fformatDate_005f1.setType("both");
/* 5395 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 5396 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 5397 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 5398 */       return true;
/*      */     }
/* 5400 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 5401 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5406 */     PageContext pageContext = _jspx_page_context;
/* 5407 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5409 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5410 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5411 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5413 */     _jspx_th_c_005fout_005f14.setValue("${row.interval}");
/* 5414 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5415 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5416 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5417 */       return true;
/*      */     }
/* 5419 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5420 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5425 */     PageContext pageContext = _jspx_page_context;
/* 5426 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5428 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5429 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5430 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5432 */     _jspx_th_c_005fout_005f15.setValue("${row.Downtime_Reason}");
/* 5433 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5434 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5435 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5436 */       return true;
/*      */     }
/* 5438 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5439 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5444 */     PageContext pageContext = _jspx_page_context;
/* 5445 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5447 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5448 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5449 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5451 */     _jspx_th_c_005fout_005f16.setValue("${row.ReasonID}");
/* 5452 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5453 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5454 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5455 */       return true;
/*      */     }
/* 5457 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5458 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5463 */     PageContext pageContext = _jspx_page_context;
/* 5464 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5466 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5467 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5468 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5470 */     _jspx_th_c_005fout_005f17.setValue("${row.downtimeinmillis}");
/* 5471 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5472 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5473 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5474 */       return true;
/*      */     }
/* 5476 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5477 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5482 */     PageContext pageContext = _jspx_page_context;
/* 5483 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5485 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5486 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5487 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5489 */     _jspx_th_c_005fout_005f18.setValue("${row.downtimeinmillis}");
/* 5490 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5491 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5492 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5493 */       return true;
/*      */     }
/* 5495 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5496 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5501 */     PageContext pageContext = _jspx_page_context;
/* 5502 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5504 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5505 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5506 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5508 */     _jspx_th_c_005fout_005f19.setValue("${row.typeID}");
/* 5509 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5510 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5511 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5512 */       return true;
/*      */     }
/* 5514 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5515 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5520 */     PageContext pageContext = _jspx_page_context;
/* 5521 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5523 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5524 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5525 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5527 */     _jspx_th_c_005fout_005f20.setValue("${row.Downtime_Reason}");
/* 5528 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5529 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5530 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5531 */       return true;
/*      */     }
/* 5533 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5534 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5539 */     PageContext pageContext = _jspx_page_context;
/* 5540 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5542 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5543 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5544 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5546 */     _jspx_th_c_005fout_005f21.setValue("${row.typeID}");
/* 5547 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5548 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5549 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5550 */       return true;
/*      */     }
/* 5552 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5553 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5558 */     PageContext pageContext = _jspx_page_context;
/* 5559 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5561 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5562 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5563 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5565 */     _jspx_th_c_005fout_005f22.setValue("${row.downtimeinmillis}");
/* 5566 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5567 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5568 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5569 */       return true;
/*      */     }
/* 5571 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5572 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5577 */     PageContext pageContext = _jspx_page_context;
/* 5578 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5580 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5581 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5582 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5584 */     _jspx_th_c_005fout_005f23.setValue("${param.period}");
/* 5585 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5586 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5587 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5588 */       return true;
/*      */     }
/* 5590 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5591 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5596 */     PageContext pageContext = _jspx_page_context;
/* 5597 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5599 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5600 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5601 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5603 */     _jspx_th_c_005fout_005f24.setValue("${row.typeID}");
/* 5604 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5605 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5606 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5607 */       return true;
/*      */     }
/* 5609 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5610 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5615 */     PageContext pageContext = _jspx_page_context;
/* 5616 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5618 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5619 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5620 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5622 */     _jspx_th_c_005fout_005f25.setValue("${row.downtimeinmillis}");
/* 5623 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5624 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5625 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5626 */       return true;
/*      */     }
/* 5628 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5629 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5634 */     PageContext pageContext = _jspx_page_context;
/* 5635 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5637 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5638 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5639 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5641 */     _jspx_th_c_005fout_005f26.setValue("${param.period}");
/* 5642 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5643 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5644 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5645 */       return true;
/*      */     }
/* 5647 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5648 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5653 */     PageContext pageContext = _jspx_page_context;
/* 5654 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5656 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5657 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5658 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5660 */     _jspx_th_c_005fset_005f2.setVar("today");
/* 5661 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5662 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5663 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5664 */         out = _jspx_page_context.pushBody();
/* 5665 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5666 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5669 */         if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5670 */           return true;
/* 5671 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5672 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5675 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5676 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5679 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5680 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5681 */       return true;
/*      */     }
/* 5683 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 5684 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5689 */     PageContext pageContext = _jspx_page_context;
/* 5690 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5692 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5693 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5694 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5696 */     _jspx_th_c_005fout_005f27.setValue("${downtimehistory[0].period}");
/* 5697 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5698 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5699 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5700 */       return true;
/*      */     }
/* 5702 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5703 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5708 */     PageContext pageContext = _jspx_page_context;
/* 5709 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5711 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5712 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5713 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5715 */     _jspx_th_c_005fout_005f28.setValue("${downtimehistory[0].uptimepercentage}");
/* 5716 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5717 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5718 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5719 */       return true;
/*      */     }
/* 5721 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5727 */     PageContext pageContext = _jspx_page_context;
/* 5728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5730 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5731 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5732 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5734 */     _jspx_th_c_005fout_005f29.setValue("${downtimehistory[0].totaldowntime}");
/* 5735 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5736 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5737 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5738 */       return true;
/*      */     }
/* 5740 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5746 */     PageContext pageContext = _jspx_page_context;
/* 5747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5749 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5750 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 5751 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5753 */     _jspx_th_c_005fout_005f30.setValue("${downtimehistory[0].mttr}");
/* 5754 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 5755 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 5756 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5757 */       return true;
/*      */     }
/* 5759 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5760 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5765 */     PageContext pageContext = _jspx_page_context;
/* 5766 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5768 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5769 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 5770 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5772 */     _jspx_th_c_005fout_005f31.setValue("${downtimehistory[0].mtbf}");
/* 5773 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 5774 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 5775 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5776 */       return true;
/*      */     }
/* 5778 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5779 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5784 */     PageContext pageContext = _jspx_page_context;
/* 5785 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5787 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5788 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5789 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5791 */     _jspx_th_c_005fset_005f3.setVar("yesterday");
/* 5792 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5793 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 5794 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5795 */         out = _jspx_page_context.pushBody();
/* 5796 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 5797 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5800 */         if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 5801 */           return true;
/* 5802 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 5803 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5806 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5807 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5810 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5811 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 5812 */       return true;
/*      */     }
/* 5814 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 5815 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5820 */     PageContext pageContext = _jspx_page_context;
/* 5821 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5823 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5824 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 5825 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 5827 */     _jspx_th_c_005fout_005f32.setValue("${downtimehistory[1].period}");
/* 5828 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 5829 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 5830 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5831 */       return true;
/*      */     }
/* 5833 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5834 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5839 */     PageContext pageContext = _jspx_page_context;
/* 5840 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5842 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5843 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 5844 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 5846 */     _jspx_th_c_005fout_005f33.setValue("${downtimehistory[1].uptimepercentage}");
/* 5847 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 5848 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 5849 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5850 */       return true;
/*      */     }
/* 5852 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5853 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5858 */     PageContext pageContext = _jspx_page_context;
/* 5859 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5861 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5862 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 5863 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 5865 */     _jspx_th_c_005fout_005f34.setValue("${downtimehistory[1].totaldowntime}");
/* 5866 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 5867 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 5868 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5869 */       return true;
/*      */     }
/* 5871 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5872 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5877 */     PageContext pageContext = _jspx_page_context;
/* 5878 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5880 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5881 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 5882 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 5884 */     _jspx_th_c_005fout_005f35.setValue("${downtimehistory[1].mttr}");
/* 5885 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 5886 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 5887 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5888 */       return true;
/*      */     }
/* 5890 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5891 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5896 */     PageContext pageContext = _jspx_page_context;
/* 5897 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5899 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5900 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 5901 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 5903 */     _jspx_th_c_005fout_005f36.setValue("${downtimehistory[1].mtbf}");
/* 5904 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 5905 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 5906 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5907 */       return true;
/*      */     }
/* 5909 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5910 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5915 */     PageContext pageContext = _jspx_page_context;
/* 5916 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5918 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5919 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 5920 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5922 */     _jspx_th_c_005fset_005f4.setVar("thisweek");
/* 5923 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 5924 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 5925 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5926 */         out = _jspx_page_context.pushBody();
/* 5927 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 5928 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5931 */         if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 5932 */           return true;
/* 5933 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 5934 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5937 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5938 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5941 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 5942 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 5943 */       return true;
/*      */     }
/* 5945 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 5946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5951 */     PageContext pageContext = _jspx_page_context;
/* 5952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5954 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5955 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 5956 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 5958 */     _jspx_th_c_005fout_005f37.setValue("${downtimehistory[2].period}");
/* 5959 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 5960 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 5961 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5962 */       return true;
/*      */     }
/* 5964 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5965 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5970 */     PageContext pageContext = _jspx_page_context;
/* 5971 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5973 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5974 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 5975 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5977 */     _jspx_th_c_005fout_005f38.setValue("${downtimehistory[2].uptimepercentage}");
/* 5978 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 5979 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 5980 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 5981 */       return true;
/*      */     }
/* 5983 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 5984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5989 */     PageContext pageContext = _jspx_page_context;
/* 5990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5992 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5993 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 5994 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5996 */     _jspx_th_c_005fout_005f39.setValue("${downtimehistory[2].totaldowntime}");
/* 5997 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 5998 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 5999 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6000 */       return true;
/*      */     }
/* 6002 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6008 */     PageContext pageContext = _jspx_page_context;
/* 6009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6011 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6012 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 6013 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 6015 */     _jspx_th_c_005fout_005f40.setValue("${downtimehistory[2].mttr}");
/* 6016 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 6017 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 6018 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6019 */       return true;
/*      */     }
/* 6021 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6027 */     PageContext pageContext = _jspx_page_context;
/* 6028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6030 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6031 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 6032 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 6034 */     _jspx_th_c_005fout_005f41.setValue("${downtimehistory[2].mtbf}");
/* 6035 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 6036 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 6037 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6038 */       return true;
/*      */     }
/* 6040 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6041 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6046 */     PageContext pageContext = _jspx_page_context;
/* 6047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6049 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6050 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 6051 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6053 */     _jspx_th_c_005fset_005f5.setVar("7days");
/* 6054 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 6055 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 6056 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6057 */         out = _jspx_page_context.pushBody();
/* 6058 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 6059 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6062 */         if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 6063 */           return true;
/* 6064 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 6065 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6068 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6069 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6072 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 6073 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 6074 */       return true;
/*      */     }
/* 6076 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 6077 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6082 */     PageContext pageContext = _jspx_page_context;
/* 6083 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6085 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6086 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 6087 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 6089 */     _jspx_th_c_005fout_005f42.setValue("${downtimehistory[3].period}");
/* 6090 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 6091 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 6092 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6093 */       return true;
/*      */     }
/* 6095 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6096 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6101 */     PageContext pageContext = _jspx_page_context;
/* 6102 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6104 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6105 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 6106 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6108 */     _jspx_th_c_005fout_005f43.setValue("${downtimehistory[3].uptimepercentage}");
/* 6109 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 6110 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 6111 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6112 */       return true;
/*      */     }
/* 6114 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6115 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6120 */     PageContext pageContext = _jspx_page_context;
/* 6121 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6123 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6124 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 6125 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6127 */     _jspx_th_c_005fout_005f44.setValue("${downtimehistory[3].totaldowntime}");
/* 6128 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 6129 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 6130 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 6131 */       return true;
/*      */     }
/* 6133 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 6134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6139 */     PageContext pageContext = _jspx_page_context;
/* 6140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6142 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6143 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 6144 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6146 */     _jspx_th_c_005fout_005f45.setValue("${downtimehistory[3].mttr}");
/* 6147 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 6148 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 6149 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 6150 */       return true;
/*      */     }
/* 6152 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 6153 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6158 */     PageContext pageContext = _jspx_page_context;
/* 6159 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6161 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6162 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 6163 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6165 */     _jspx_th_c_005fout_005f46.setValue("${downtimehistory[3].mtbf}");
/* 6166 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 6167 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 6168 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 6169 */       return true;
/*      */     }
/* 6171 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 6172 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6177 */     PageContext pageContext = _jspx_page_context;
/* 6178 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6180 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6181 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 6182 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6184 */     _jspx_th_c_005fset_005f6.setVar("lastweek");
/* 6185 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 6186 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 6187 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6188 */         out = _jspx_page_context.pushBody();
/* 6189 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 6190 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6193 */         if (_jspx_meth_c_005fout_005f47(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 6194 */           return true;
/* 6195 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 6196 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6199 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6200 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6203 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 6204 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 6205 */       return true;
/*      */     }
/* 6207 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 6208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6213 */     PageContext pageContext = _jspx_page_context;
/* 6214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6216 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6217 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 6218 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 6220 */     _jspx_th_c_005fout_005f47.setValue("${downtimehistory[4].period}");
/* 6221 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 6222 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 6223 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 6224 */       return true;
/*      */     }
/* 6226 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 6227 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6232 */     PageContext pageContext = _jspx_page_context;
/* 6233 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6235 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6236 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 6237 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 6239 */     _jspx_th_c_005fout_005f48.setValue("${downtimehistory[4].uptimepercentage}");
/* 6240 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 6241 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 6242 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 6243 */       return true;
/*      */     }
/* 6245 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 6246 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6251 */     PageContext pageContext = _jspx_page_context;
/* 6252 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6254 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6255 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 6256 */     _jspx_th_c_005fout_005f49.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 6258 */     _jspx_th_c_005fout_005f49.setValue("${downtimehistory[4].totaldowntime}");
/* 6259 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 6260 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 6261 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 6262 */       return true;
/*      */     }
/* 6264 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 6265 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6270 */     PageContext pageContext = _jspx_page_context;
/* 6271 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6273 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6274 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 6275 */     _jspx_th_c_005fout_005f50.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 6277 */     _jspx_th_c_005fout_005f50.setValue("${downtimehistory[4].mttr}");
/* 6278 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 6279 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 6280 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 6281 */       return true;
/*      */     }
/* 6283 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 6284 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6289 */     PageContext pageContext = _jspx_page_context;
/* 6290 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6292 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6293 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 6294 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 6296 */     _jspx_th_c_005fout_005f51.setValue("${downtimehistory[4].mtbf}");
/* 6297 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 6298 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 6299 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 6300 */       return true;
/*      */     }
/* 6302 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 6303 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6308 */     PageContext pageContext = _jspx_page_context;
/* 6309 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6311 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6312 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 6313 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6315 */     _jspx_th_c_005fset_005f7.setVar("thismonth");
/* 6316 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 6317 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 6318 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6319 */         out = _jspx_page_context.pushBody();
/* 6320 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 6321 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6324 */         if (_jspx_meth_c_005fout_005f52(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 6325 */           return true;
/* 6326 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 6327 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6330 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6331 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6334 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 6335 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 6336 */       return true;
/*      */     }
/* 6338 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 6339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6344 */     PageContext pageContext = _jspx_page_context;
/* 6345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6347 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6348 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 6349 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 6351 */     _jspx_th_c_005fout_005f52.setValue("${downtimehistory[5].period}");
/* 6352 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 6353 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 6354 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 6355 */       return true;
/*      */     }
/* 6357 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 6358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6363 */     PageContext pageContext = _jspx_page_context;
/* 6364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6366 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6367 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 6368 */     _jspx_th_c_005fout_005f53.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6370 */     _jspx_th_c_005fout_005f53.setValue("${downtimehistory[5].uptimepercentage}");
/* 6371 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 6372 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 6373 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 6374 */       return true;
/*      */     }
/* 6376 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 6377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f54(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6382 */     PageContext pageContext = _jspx_page_context;
/* 6383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6385 */     OutTag _jspx_th_c_005fout_005f54 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6386 */     _jspx_th_c_005fout_005f54.setPageContext(_jspx_page_context);
/* 6387 */     _jspx_th_c_005fout_005f54.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6389 */     _jspx_th_c_005fout_005f54.setValue("${downtimehistory[5].totaldowntime}");
/* 6390 */     int _jspx_eval_c_005fout_005f54 = _jspx_th_c_005fout_005f54.doStartTag();
/* 6391 */     if (_jspx_th_c_005fout_005f54.doEndTag() == 5) {
/* 6392 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 6393 */       return true;
/*      */     }
/* 6395 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 6396 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f55(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6401 */     PageContext pageContext = _jspx_page_context;
/* 6402 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6404 */     OutTag _jspx_th_c_005fout_005f55 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6405 */     _jspx_th_c_005fout_005f55.setPageContext(_jspx_page_context);
/* 6406 */     _jspx_th_c_005fout_005f55.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6408 */     _jspx_th_c_005fout_005f55.setValue("${downtimehistory[5].mttr}");
/* 6409 */     int _jspx_eval_c_005fout_005f55 = _jspx_th_c_005fout_005f55.doStartTag();
/* 6410 */     if (_jspx_th_c_005fout_005f55.doEndTag() == 5) {
/* 6411 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 6412 */       return true;
/*      */     }
/* 6414 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 6415 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f56(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6420 */     PageContext pageContext = _jspx_page_context;
/* 6421 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6423 */     OutTag _jspx_th_c_005fout_005f56 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6424 */     _jspx_th_c_005fout_005f56.setPageContext(_jspx_page_context);
/* 6425 */     _jspx_th_c_005fout_005f56.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6427 */     _jspx_th_c_005fout_005f56.setValue("${downtimehistory[5].mtbf}");
/* 6428 */     int _jspx_eval_c_005fout_005f56 = _jspx_th_c_005fout_005f56.doStartTag();
/* 6429 */     if (_jspx_th_c_005fout_005f56.doEndTag() == 5) {
/* 6430 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 6431 */       return true;
/*      */     }
/* 6433 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 6434 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6439 */     PageContext pageContext = _jspx_page_context;
/* 6440 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6442 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6443 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 6444 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6446 */     _jspx_th_c_005fset_005f8.setVar("30days");
/* 6447 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 6448 */     if (_jspx_eval_c_005fset_005f8 != 0) {
/* 6449 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6450 */         out = _jspx_page_context.pushBody();
/* 6451 */         _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 6452 */         _jspx_th_c_005fset_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6455 */         if (_jspx_meth_c_005fout_005f57(_jspx_th_c_005fset_005f8, _jspx_page_context))
/* 6456 */           return true;
/* 6457 */         int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 6458 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6461 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6462 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6465 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 6466 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 6467 */       return true;
/*      */     }
/* 6469 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 6470 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f57(JspTag _jspx_th_c_005fset_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6475 */     PageContext pageContext = _jspx_page_context;
/* 6476 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6478 */     OutTag _jspx_th_c_005fout_005f57 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6479 */     _jspx_th_c_005fout_005f57.setPageContext(_jspx_page_context);
/* 6480 */     _jspx_th_c_005fout_005f57.setParent((Tag)_jspx_th_c_005fset_005f8);
/*      */     
/* 6482 */     _jspx_th_c_005fout_005f57.setValue("${downtimehistory[6].period}");
/* 6483 */     int _jspx_eval_c_005fout_005f57 = _jspx_th_c_005fout_005f57.doStartTag();
/* 6484 */     if (_jspx_th_c_005fout_005f57.doEndTag() == 5) {
/* 6485 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 6486 */       return true;
/*      */     }
/* 6488 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 6489 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f58(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6494 */     PageContext pageContext = _jspx_page_context;
/* 6495 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6497 */     OutTag _jspx_th_c_005fout_005f58 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6498 */     _jspx_th_c_005fout_005f58.setPageContext(_jspx_page_context);
/* 6499 */     _jspx_th_c_005fout_005f58.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6501 */     _jspx_th_c_005fout_005f58.setValue("${downtimehistory[6].uptimepercentage}");
/* 6502 */     int _jspx_eval_c_005fout_005f58 = _jspx_th_c_005fout_005f58.doStartTag();
/* 6503 */     if (_jspx_th_c_005fout_005f58.doEndTag() == 5) {
/* 6504 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 6505 */       return true;
/*      */     }
/* 6507 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 6508 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f59(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6513 */     PageContext pageContext = _jspx_page_context;
/* 6514 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6516 */     OutTag _jspx_th_c_005fout_005f59 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6517 */     _jspx_th_c_005fout_005f59.setPageContext(_jspx_page_context);
/* 6518 */     _jspx_th_c_005fout_005f59.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6520 */     _jspx_th_c_005fout_005f59.setValue("${downtimehistory[6].totaldowntime}");
/* 6521 */     int _jspx_eval_c_005fout_005f59 = _jspx_th_c_005fout_005f59.doStartTag();
/* 6522 */     if (_jspx_th_c_005fout_005f59.doEndTag() == 5) {
/* 6523 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f59);
/* 6524 */       return true;
/*      */     }
/* 6526 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f59);
/* 6527 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f60(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6532 */     PageContext pageContext = _jspx_page_context;
/* 6533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6535 */     OutTag _jspx_th_c_005fout_005f60 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6536 */     _jspx_th_c_005fout_005f60.setPageContext(_jspx_page_context);
/* 6537 */     _jspx_th_c_005fout_005f60.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6539 */     _jspx_th_c_005fout_005f60.setValue("${downtimehistory[6].mttr}");
/* 6540 */     int _jspx_eval_c_005fout_005f60 = _jspx_th_c_005fout_005f60.doStartTag();
/* 6541 */     if (_jspx_th_c_005fout_005f60.doEndTag() == 5) {
/* 6542 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f60);
/* 6543 */       return true;
/*      */     }
/* 6545 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f60);
/* 6546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f61(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6551 */     PageContext pageContext = _jspx_page_context;
/* 6552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6554 */     OutTag _jspx_th_c_005fout_005f61 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6555 */     _jspx_th_c_005fout_005f61.setPageContext(_jspx_page_context);
/* 6556 */     _jspx_th_c_005fout_005f61.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6558 */     _jspx_th_c_005fout_005f61.setValue("${downtimehistory[6].mtbf}");
/* 6559 */     int _jspx_eval_c_005fout_005f61 = _jspx_th_c_005fout_005f61.doStartTag();
/* 6560 */     if (_jspx_th_c_005fout_005f61.doEndTag() == 5) {
/* 6561 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f61);
/* 6562 */       return true;
/*      */     }
/* 6564 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f61);
/* 6565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6570 */     PageContext pageContext = _jspx_page_context;
/* 6571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6573 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6574 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 6575 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6577 */     _jspx_th_c_005fset_005f9.setVar("lastmonth");
/* 6578 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 6579 */     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 6580 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6581 */         out = _jspx_page_context.pushBody();
/* 6582 */         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 6583 */         _jspx_th_c_005fset_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6586 */         if (_jspx_meth_c_005fout_005f62(_jspx_th_c_005fset_005f9, _jspx_page_context))
/* 6587 */           return true;
/* 6588 */         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 6589 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6592 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6593 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6596 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 6597 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 6598 */       return true;
/*      */     }
/* 6600 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 6601 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f62(JspTag _jspx_th_c_005fset_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6606 */     PageContext pageContext = _jspx_page_context;
/* 6607 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6609 */     OutTag _jspx_th_c_005fout_005f62 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6610 */     _jspx_th_c_005fout_005f62.setPageContext(_jspx_page_context);
/* 6611 */     _jspx_th_c_005fout_005f62.setParent((Tag)_jspx_th_c_005fset_005f9);
/*      */     
/* 6613 */     _jspx_th_c_005fout_005f62.setValue("${downtimehistory[7].period}");
/* 6614 */     int _jspx_eval_c_005fout_005f62 = _jspx_th_c_005fout_005f62.doStartTag();
/* 6615 */     if (_jspx_th_c_005fout_005f62.doEndTag() == 5) {
/* 6616 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f62);
/* 6617 */       return true;
/*      */     }
/* 6619 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f62);
/* 6620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f63(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6625 */     PageContext pageContext = _jspx_page_context;
/* 6626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6628 */     OutTag _jspx_th_c_005fout_005f63 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6629 */     _jspx_th_c_005fout_005f63.setPageContext(_jspx_page_context);
/* 6630 */     _jspx_th_c_005fout_005f63.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 6632 */     _jspx_th_c_005fout_005f63.setValue("${downtimehistory[7].uptimepercentage}");
/* 6633 */     int _jspx_eval_c_005fout_005f63 = _jspx_th_c_005fout_005f63.doStartTag();
/* 6634 */     if (_jspx_th_c_005fout_005f63.doEndTag() == 5) {
/* 6635 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f63);
/* 6636 */       return true;
/*      */     }
/* 6638 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f63);
/* 6639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f64(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6644 */     PageContext pageContext = _jspx_page_context;
/* 6645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6647 */     OutTag _jspx_th_c_005fout_005f64 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6648 */     _jspx_th_c_005fout_005f64.setPageContext(_jspx_page_context);
/* 6649 */     _jspx_th_c_005fout_005f64.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 6651 */     _jspx_th_c_005fout_005f64.setValue("${downtimehistory[7].totaldowntime}");
/* 6652 */     int _jspx_eval_c_005fout_005f64 = _jspx_th_c_005fout_005f64.doStartTag();
/* 6653 */     if (_jspx_th_c_005fout_005f64.doEndTag() == 5) {
/* 6654 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f64);
/* 6655 */       return true;
/*      */     }
/* 6657 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f64);
/* 6658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f65(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6663 */     PageContext pageContext = _jspx_page_context;
/* 6664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6666 */     OutTag _jspx_th_c_005fout_005f65 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6667 */     _jspx_th_c_005fout_005f65.setPageContext(_jspx_page_context);
/* 6668 */     _jspx_th_c_005fout_005f65.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 6670 */     _jspx_th_c_005fout_005f65.setValue("${downtimehistory[7].mttr}");
/* 6671 */     int _jspx_eval_c_005fout_005f65 = _jspx_th_c_005fout_005f65.doStartTag();
/* 6672 */     if (_jspx_th_c_005fout_005f65.doEndTag() == 5) {
/* 6673 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f65);
/* 6674 */       return true;
/*      */     }
/* 6676 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f65);
/* 6677 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f66(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6682 */     PageContext pageContext = _jspx_page_context;
/* 6683 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6685 */     OutTag _jspx_th_c_005fout_005f66 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6686 */     _jspx_th_c_005fout_005f66.setPageContext(_jspx_page_context);
/* 6687 */     _jspx_th_c_005fout_005f66.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 6689 */     _jspx_th_c_005fout_005f66.setValue("${downtimehistory[7].mtbf}");
/* 6690 */     int _jspx_eval_c_005fout_005f66 = _jspx_th_c_005fout_005f66.doStartTag();
/* 6691 */     if (_jspx_th_c_005fout_005f66.doEndTag() == 5) {
/* 6692 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f66);
/* 6693 */       return true;
/*      */     }
/* 6695 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f66);
/* 6696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f10(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6701 */     PageContext pageContext = _jspx_page_context;
/* 6702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6704 */     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6705 */     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 6706 */     _jspx_th_c_005fset_005f10.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6708 */     _jspx_th_c_005fset_005f10.setVar("thisquarter");
/* 6709 */     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 6710 */     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 6711 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6712 */         out = _jspx_page_context.pushBody();
/* 6713 */         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 6714 */         _jspx_th_c_005fset_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6717 */         if (_jspx_meth_c_005fout_005f67(_jspx_th_c_005fset_005f10, _jspx_page_context))
/* 6718 */           return true;
/* 6719 */         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 6720 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6723 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6724 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6727 */     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 6728 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 6729 */       return true;
/*      */     }
/* 6731 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 6732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f67(JspTag _jspx_th_c_005fset_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6737 */     PageContext pageContext = _jspx_page_context;
/* 6738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6740 */     OutTag _jspx_th_c_005fout_005f67 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6741 */     _jspx_th_c_005fout_005f67.setPageContext(_jspx_page_context);
/* 6742 */     _jspx_th_c_005fout_005f67.setParent((Tag)_jspx_th_c_005fset_005f10);
/*      */     
/* 6744 */     _jspx_th_c_005fout_005f67.setValue("${downtimehistory[8].period}");
/* 6745 */     int _jspx_eval_c_005fout_005f67 = _jspx_th_c_005fout_005f67.doStartTag();
/* 6746 */     if (_jspx_th_c_005fout_005f67.doEndTag() == 5) {
/* 6747 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f67);
/* 6748 */       return true;
/*      */     }
/* 6750 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f67);
/* 6751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f68(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6756 */     PageContext pageContext = _jspx_page_context;
/* 6757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6759 */     OutTag _jspx_th_c_005fout_005f68 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6760 */     _jspx_th_c_005fout_005f68.setPageContext(_jspx_page_context);
/* 6761 */     _jspx_th_c_005fout_005f68.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6763 */     _jspx_th_c_005fout_005f68.setValue("${downtimehistory[8].uptimepercentage}");
/* 6764 */     int _jspx_eval_c_005fout_005f68 = _jspx_th_c_005fout_005f68.doStartTag();
/* 6765 */     if (_jspx_th_c_005fout_005f68.doEndTag() == 5) {
/* 6766 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f68);
/* 6767 */       return true;
/*      */     }
/* 6769 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f68);
/* 6770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f69(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6775 */     PageContext pageContext = _jspx_page_context;
/* 6776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6778 */     OutTag _jspx_th_c_005fout_005f69 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6779 */     _jspx_th_c_005fout_005f69.setPageContext(_jspx_page_context);
/* 6780 */     _jspx_th_c_005fout_005f69.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6782 */     _jspx_th_c_005fout_005f69.setValue("${downtimehistory[8].totaldowntime}");
/* 6783 */     int _jspx_eval_c_005fout_005f69 = _jspx_th_c_005fout_005f69.doStartTag();
/* 6784 */     if (_jspx_th_c_005fout_005f69.doEndTag() == 5) {
/* 6785 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f69);
/* 6786 */       return true;
/*      */     }
/* 6788 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f69);
/* 6789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f70(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6794 */     PageContext pageContext = _jspx_page_context;
/* 6795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6797 */     OutTag _jspx_th_c_005fout_005f70 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6798 */     _jspx_th_c_005fout_005f70.setPageContext(_jspx_page_context);
/* 6799 */     _jspx_th_c_005fout_005f70.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6801 */     _jspx_th_c_005fout_005f70.setValue("${downtimehistory[8].mttr}");
/* 6802 */     int _jspx_eval_c_005fout_005f70 = _jspx_th_c_005fout_005f70.doStartTag();
/* 6803 */     if (_jspx_th_c_005fout_005f70.doEndTag() == 5) {
/* 6804 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f70);
/* 6805 */       return true;
/*      */     }
/* 6807 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f70);
/* 6808 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f71(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6813 */     PageContext pageContext = _jspx_page_context;
/* 6814 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6816 */     OutTag _jspx_th_c_005fout_005f71 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6817 */     _jspx_th_c_005fout_005f71.setPageContext(_jspx_page_context);
/* 6818 */     _jspx_th_c_005fout_005f71.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6820 */     _jspx_th_c_005fout_005f71.setValue("${downtimehistory[8].mtbf}");
/* 6821 */     int _jspx_eval_c_005fout_005f71 = _jspx_th_c_005fout_005f71.doStartTag();
/* 6822 */     if (_jspx_th_c_005fout_005f71.doEndTag() == 5) {
/* 6823 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f71);
/* 6824 */       return true;
/*      */     }
/* 6826 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f71);
/* 6827 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6832 */     PageContext pageContext = _jspx_page_context;
/* 6833 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6835 */     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6836 */     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 6837 */     _jspx_th_c_005fset_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6839 */     _jspx_th_c_005fset_005f11.setVar("thisyear");
/* 6840 */     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 6841 */     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 6842 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 6843 */         out = _jspx_page_context.pushBody();
/* 6844 */         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 6845 */         _jspx_th_c_005fset_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6848 */         if (_jspx_meth_c_005fout_005f72(_jspx_th_c_005fset_005f11, _jspx_page_context))
/* 6849 */           return true;
/* 6850 */         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 6851 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6854 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 6855 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6858 */     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 6859 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 6860 */       return true;
/*      */     }
/* 6862 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 6863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f72(JspTag _jspx_th_c_005fset_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6868 */     PageContext pageContext = _jspx_page_context;
/* 6869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6871 */     OutTag _jspx_th_c_005fout_005f72 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6872 */     _jspx_th_c_005fout_005f72.setPageContext(_jspx_page_context);
/* 6873 */     _jspx_th_c_005fout_005f72.setParent((Tag)_jspx_th_c_005fset_005f11);
/*      */     
/* 6875 */     _jspx_th_c_005fout_005f72.setValue("${downtimehistory[9].period}");
/* 6876 */     int _jspx_eval_c_005fout_005f72 = _jspx_th_c_005fout_005f72.doStartTag();
/* 6877 */     if (_jspx_th_c_005fout_005f72.doEndTag() == 5) {
/* 6878 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f72);
/* 6879 */       return true;
/*      */     }
/* 6881 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f72);
/* 6882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f73(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6887 */     PageContext pageContext = _jspx_page_context;
/* 6888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6890 */     OutTag _jspx_th_c_005fout_005f73 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6891 */     _jspx_th_c_005fout_005f73.setPageContext(_jspx_page_context);
/* 6892 */     _jspx_th_c_005fout_005f73.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 6894 */     _jspx_th_c_005fout_005f73.setValue("${downtimehistory[9].uptimepercentage}");
/* 6895 */     int _jspx_eval_c_005fout_005f73 = _jspx_th_c_005fout_005f73.doStartTag();
/* 6896 */     if (_jspx_th_c_005fout_005f73.doEndTag() == 5) {
/* 6897 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f73);
/* 6898 */       return true;
/*      */     }
/* 6900 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f73);
/* 6901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f74(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6906 */     PageContext pageContext = _jspx_page_context;
/* 6907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6909 */     OutTag _jspx_th_c_005fout_005f74 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6910 */     _jspx_th_c_005fout_005f74.setPageContext(_jspx_page_context);
/* 6911 */     _jspx_th_c_005fout_005f74.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 6913 */     _jspx_th_c_005fout_005f74.setValue("${downtimehistory[9].totaldowntime}");
/* 6914 */     int _jspx_eval_c_005fout_005f74 = _jspx_th_c_005fout_005f74.doStartTag();
/* 6915 */     if (_jspx_th_c_005fout_005f74.doEndTag() == 5) {
/* 6916 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f74);
/* 6917 */       return true;
/*      */     }
/* 6919 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f74);
/* 6920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f75(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6925 */     PageContext pageContext = _jspx_page_context;
/* 6926 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6928 */     OutTag _jspx_th_c_005fout_005f75 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6929 */     _jspx_th_c_005fout_005f75.setPageContext(_jspx_page_context);
/* 6930 */     _jspx_th_c_005fout_005f75.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 6932 */     _jspx_th_c_005fout_005f75.setValue("${downtimehistory[9].mttr}");
/* 6933 */     int _jspx_eval_c_005fout_005f75 = _jspx_th_c_005fout_005f75.doStartTag();
/* 6934 */     if (_jspx_th_c_005fout_005f75.doEndTag() == 5) {
/* 6935 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f75);
/* 6936 */       return true;
/*      */     }
/* 6938 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f75);
/* 6939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f76(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6944 */     PageContext pageContext = _jspx_page_context;
/* 6945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6947 */     OutTag _jspx_th_c_005fout_005f76 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6948 */     _jspx_th_c_005fout_005f76.setPageContext(_jspx_page_context);
/* 6949 */     _jspx_th_c_005fout_005f76.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 6951 */     _jspx_th_c_005fout_005f76.setValue("${downtimehistory[9].mtbf}");
/* 6952 */     int _jspx_eval_c_005fout_005f76 = _jspx_th_c_005fout_005f76.doStartTag();
/* 6953 */     if (_jspx_th_c_005fout_005f76.doEndTag() == 5) {
/* 6954 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f76);
/* 6955 */       return true;
/*      */     }
/* 6957 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f76);
/* 6958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6963 */     PageContext pageContext = _jspx_page_context;
/* 6964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6966 */     SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6967 */     _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 6968 */     _jspx_th_c_005fset_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6970 */     _jspx_th_c_005fset_005f12.setVar("lastoneyear");
/* 6971 */     int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 6972 */     if (_jspx_eval_c_005fset_005f12 != 0) {
/* 6973 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 6974 */         out = _jspx_page_context.pushBody();
/* 6975 */         _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 6976 */         _jspx_th_c_005fset_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6979 */         if (_jspx_meth_c_005fout_005f77(_jspx_th_c_005fset_005f12, _jspx_page_context))
/* 6980 */           return true;
/* 6981 */         int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 6982 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6985 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 6986 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6989 */     if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 6990 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 6991 */       return true;
/*      */     }
/* 6993 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 6994 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f77(JspTag _jspx_th_c_005fset_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6999 */     PageContext pageContext = _jspx_page_context;
/* 7000 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7002 */     OutTag _jspx_th_c_005fout_005f77 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7003 */     _jspx_th_c_005fout_005f77.setPageContext(_jspx_page_context);
/* 7004 */     _jspx_th_c_005fout_005f77.setParent((Tag)_jspx_th_c_005fset_005f12);
/*      */     
/* 7006 */     _jspx_th_c_005fout_005f77.setValue("${downtimehistory[10].period}");
/* 7007 */     int _jspx_eval_c_005fout_005f77 = _jspx_th_c_005fout_005f77.doStartTag();
/* 7008 */     if (_jspx_th_c_005fout_005f77.doEndTag() == 5) {
/* 7009 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f77);
/* 7010 */       return true;
/*      */     }
/* 7012 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f77);
/* 7013 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f78(JspTag _jspx_th_c_005fif_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7018 */     PageContext pageContext = _jspx_page_context;
/* 7019 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7021 */     OutTag _jspx_th_c_005fout_005f78 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7022 */     _jspx_th_c_005fout_005f78.setPageContext(_jspx_page_context);
/* 7023 */     _jspx_th_c_005fout_005f78.setParent((Tag)_jspx_th_c_005fif_005f25);
/*      */     
/* 7025 */     _jspx_th_c_005fout_005f78.setValue("${downtimehistory[10].uptimepercentage}");
/* 7026 */     int _jspx_eval_c_005fout_005f78 = _jspx_th_c_005fout_005f78.doStartTag();
/* 7027 */     if (_jspx_th_c_005fout_005f78.doEndTag() == 5) {
/* 7028 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f78);
/* 7029 */       return true;
/*      */     }
/* 7031 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f78);
/* 7032 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f79(JspTag _jspx_th_c_005fif_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7037 */     PageContext pageContext = _jspx_page_context;
/* 7038 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7040 */     OutTag _jspx_th_c_005fout_005f79 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7041 */     _jspx_th_c_005fout_005f79.setPageContext(_jspx_page_context);
/* 7042 */     _jspx_th_c_005fout_005f79.setParent((Tag)_jspx_th_c_005fif_005f25);
/*      */     
/* 7044 */     _jspx_th_c_005fout_005f79.setValue("${downtimehistory[10].totaldowntime}");
/* 7045 */     int _jspx_eval_c_005fout_005f79 = _jspx_th_c_005fout_005f79.doStartTag();
/* 7046 */     if (_jspx_th_c_005fout_005f79.doEndTag() == 5) {
/* 7047 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f79);
/* 7048 */       return true;
/*      */     }
/* 7050 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f79);
/* 7051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f80(JspTag _jspx_th_c_005fif_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7056 */     PageContext pageContext = _jspx_page_context;
/* 7057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7059 */     OutTag _jspx_th_c_005fout_005f80 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7060 */     _jspx_th_c_005fout_005f80.setPageContext(_jspx_page_context);
/* 7061 */     _jspx_th_c_005fout_005f80.setParent((Tag)_jspx_th_c_005fif_005f25);
/*      */     
/* 7063 */     _jspx_th_c_005fout_005f80.setValue("${downtimehistory[10].mttr}");
/* 7064 */     int _jspx_eval_c_005fout_005f80 = _jspx_th_c_005fout_005f80.doStartTag();
/* 7065 */     if (_jspx_th_c_005fout_005f80.doEndTag() == 5) {
/* 7066 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f80);
/* 7067 */       return true;
/*      */     }
/* 7069 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f80);
/* 7070 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f81(JspTag _jspx_th_c_005fif_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7075 */     PageContext pageContext = _jspx_page_context;
/* 7076 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7078 */     OutTag _jspx_th_c_005fout_005f81 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7079 */     _jspx_th_c_005fout_005f81.setPageContext(_jspx_page_context);
/* 7080 */     _jspx_th_c_005fout_005f81.setParent((Tag)_jspx_th_c_005fif_005f25);
/*      */     
/* 7082 */     _jspx_th_c_005fout_005f81.setValue("${downtimehistory[10].mtbf}");
/* 7083 */     int _jspx_eval_c_005fout_005f81 = _jspx_th_c_005fout_005f81.doStartTag();
/* 7084 */     if (_jspx_th_c_005fout_005f81.doEndTag() == 5) {
/* 7085 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f81);
/* 7086 */       return true;
/*      */     }
/* 7088 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f81);
/* 7089 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fAvailabilityData_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */