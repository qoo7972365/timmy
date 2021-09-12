/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class DB2Details_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   53 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   56 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   57 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   58 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   65 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   70 */     ArrayList list = null;
/*   71 */     StringBuffer sbf = new StringBuffer();
/*   72 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/*   73 */     if (distinct)
/*      */     {
/*   75 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   79 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   82 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   84 */       ArrayList row = (ArrayList)list.get(i);
/*   85 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   86 */       if (distinct) {
/*   87 */         sbf.append(row.get(0));
/*      */       } else
/*   89 */         sbf.append(row.get(1));
/*   90 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   93 */     return sbf.toString(); }
/*      */   
/*   95 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   98 */     if (severity == null)
/*      */     {
/*  100 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  102 */     if (severity.equals("5"))
/*      */     {
/*  104 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  106 */     if (severity.equals("1"))
/*      */     {
/*  108 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  113 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  120 */     if (severity == null)
/*      */     {
/*  122 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  124 */     if (severity.equals("1"))
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  128 */     if (severity.equals("4"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  132 */     if (severity.equals("5"))
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  139 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  145 */     if (severity == null)
/*      */     {
/*  147 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  149 */     if (severity.equals("5"))
/*      */     {
/*  151 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  153 */     if (severity.equals("1"))
/*      */     {
/*  155 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  159 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  165 */     if (severity == null)
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  169 */     if (severity.equals("1"))
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  173 */     if (severity.equals("4"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  177 */     if (severity.equals("5"))
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  183 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  189 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  195 */     if (severity == 5)
/*      */     {
/*  197 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  199 */     if (severity == 1)
/*      */     {
/*  201 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  206 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  212 */     if (severity == null)
/*      */     {
/*  214 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  216 */     if (severity.equals("5"))
/*      */     {
/*  218 */       if (isAvailability) {
/*  219 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  222 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  225 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  227 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  229 */     if (severity.equals("1"))
/*      */     {
/*  231 */       if (isAvailability) {
/*  232 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  235 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  242 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  249 */     if (severity == null)
/*      */     {
/*  251 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  253 */     if (severity.equals("5"))
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  257 */     if (severity.equals("4"))
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  261 */     if (severity.equals("1"))
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  268 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  274 */     if (severity == null)
/*      */     {
/*  276 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  278 */     if (severity.equals("5"))
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("4"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  286 */     if (severity.equals("1"))
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  293 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  300 */     if (severity == null)
/*      */     {
/*  302 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  304 */     if (severity.equals("5"))
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  308 */     if (severity.equals("4"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  312 */     if (severity.equals("1"))
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  319 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  327 */     StringBuffer out = new StringBuffer();
/*  328 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  329 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  330 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  331 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  332 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  333 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  334 */     out.append("</tr>");
/*  335 */     out.append("</form></table>");
/*  336 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  343 */     if (val == null)
/*      */     {
/*  345 */       return "-";
/*      */     }
/*      */     
/*  348 */     String ret = FormatUtil.formatNumber(val);
/*  349 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  350 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  353 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  357 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  365 */     StringBuffer out = new StringBuffer();
/*  366 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  367 */     out.append("<tr>");
/*  368 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  370 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  372 */     out.append("</tr>");
/*  373 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  377 */       if (j % 2 == 0)
/*      */       {
/*  379 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  383 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  386 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  388 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  391 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  395 */       out.append("</tr>");
/*      */     }
/*  397 */     out.append("</table>");
/*  398 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  399 */     out.append("<tr>");
/*  400 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  401 */     out.append("</tr>");
/*  402 */     out.append("</table>");
/*  403 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
/*      */   {
/*  409 */     StringBuffer out = new StringBuffer();
/*  410 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  411 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  412 */     out.append("<tr>");
/*  413 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  414 */     out.append("<tr>");
/*  415 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  416 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  417 */     out.append("</tr>");
/*  418 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  421 */       out.append("<tr>");
/*  422 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  423 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  424 */       out.append("</tr>");
/*      */     }
/*      */     
/*  427 */     out.append("</table>");
/*  428 */     out.append("</table>");
/*  429 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  434 */     if (severity.equals("0"))
/*      */     {
/*  436 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  440 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, javax.servlet.http.HttpSession session)
/*      */   {
/*  447 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  460 */     StringBuffer out = new StringBuffer();
/*  461 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  462 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  464 */       out.append("<tr>");
/*  465 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  466 */       out.append("</tr>");
/*      */       
/*      */ 
/*  469 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  471 */         String borderclass = "";
/*      */         
/*      */ 
/*  474 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  476 */         out.append("<tr>");
/*      */         
/*  478 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  479 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  480 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  486 */     out.append("</table><br>");
/*  487 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  488 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  490 */       List sLinks = secondLevelOfLinks[0];
/*  491 */       List sText = secondLevelOfLinks[1];
/*  492 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  495 */         out.append("<tr>");
/*  496 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  497 */         out.append("</tr>");
/*  498 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  500 */           String borderclass = "";
/*      */           
/*      */ 
/*  503 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  505 */           out.append("<tr>");
/*      */           
/*  507 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  508 */           if (sLinks.get(i).toString().length() == 0) {
/*  509 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  512 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  514 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  518 */     out.append("</table>");
/*  519 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session, HttpServletRequest request)
/*      */   {
/*  526 */     StringBuffer out = new StringBuffer();
/*  527 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  528 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  530 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  532 */         out.append("<tr>");
/*  533 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  534 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  538 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  540 */           String borderclass = "";
/*      */           
/*      */ 
/*  543 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  545 */           out.append("<tr>");
/*      */           
/*  547 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  548 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  549 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  552 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  555 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  560 */     out.append("</table><br>");
/*  561 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  562 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  564 */       List sLinks = secondLevelOfLinks[0];
/*  565 */       List sText = secondLevelOfLinks[1];
/*  566 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  569 */         out.append("<tr>");
/*  570 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  571 */         out.append("</tr>");
/*  572 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  574 */           String borderclass = "";
/*      */           
/*      */ 
/*  577 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  579 */           out.append("<tr>");
/*      */           
/*  581 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  582 */           if (sLinks.get(i).toString().length() == 0) {
/*  583 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  586 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  588 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  592 */     out.append("</table>");
/*  593 */     return out.toString();
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
/*  606 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  618 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  621 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  624 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  627 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  635 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  640 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  645 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  650 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  655 */     if (val != null)
/*      */     {
/*  657 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  661 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  666 */     if (val == null) {
/*  667 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  671 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  676 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  682 */     if (val != null)
/*      */     {
/*  684 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  688 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  694 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  699 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  703 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  708 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  713 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  718 */     String hostaddress = "";
/*  719 */     String ip = request.getHeader("x-forwarded-for");
/*  720 */     if (ip == null)
/*  721 */       ip = request.getRemoteAddr();
/*  722 */     java.net.InetAddress add = null;
/*  723 */     if (ip.equals("127.0.0.1")) {
/*  724 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  728 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  730 */     hostaddress = add.getHostName();
/*  731 */     if (hostaddress.indexOf('.') != -1) {
/*  732 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  733 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  737 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  742 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  748 */     if (severity == null)
/*      */     {
/*  750 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  752 */     if (severity.equals("5"))
/*      */     {
/*  754 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  756 */     if (severity.equals("1"))
/*      */     {
/*  758 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  763 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  768 */     ResultSet set = null;
/*  769 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  770 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  772 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  773 */       if (set.next()) { String str1;
/*  774 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  775 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  778 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  783 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  786 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  788 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  792 */     StringBuffer rca = new StringBuffer();
/*  793 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  794 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  797 */     int rcalength = key.length();
/*  798 */     String split = "6. ";
/*  799 */     int splitPresent = key.indexOf(split);
/*  800 */     String div1 = "";String div2 = "";
/*  801 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  803 */       if (rcalength > 180) {
/*  804 */         rca.append("<span class=\"rca-critical-text\">");
/*  805 */         getRCATrimmedText(key, rca);
/*  806 */         rca.append("</span>");
/*      */       } else {
/*  808 */         rca.append("<span class=\"rca-critical-text\">");
/*  809 */         rca.append(key);
/*  810 */         rca.append("</span>");
/*      */       }
/*  812 */       return rca.toString();
/*      */     }
/*  814 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  815 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  816 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  817 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  818 */     getRCATrimmedText(div1, rca);
/*  819 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  822 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  823 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  824 */     getRCATrimmedText(div2, rca);
/*  825 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  827 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  832 */     String[] st = msg.split("<br>");
/*  833 */     for (int i = 0; i < st.length; i++) {
/*  834 */       String s = st[i];
/*  835 */       if (s.length() > 180) {
/*  836 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  838 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  842 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  843 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  845 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  849 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  850 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  851 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  854 */       if (key == null) {
/*  855 */         return ret;
/*      */       }
/*      */       
/*  858 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  859 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  862 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  863 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  864 */       set = AMConnectionPool.executeQueryStmt(query);
/*  865 */       if (set.next())
/*      */       {
/*  867 */         String helpLink = set.getString("LINK");
/*  868 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  871 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  877 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  896 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  887 */         if (set != null) {
/*  888 */           AMConnectionPool.closeStatement(set);
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
/*  902 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  903 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  905 */       String entityStr = (String)keys.nextElement();
/*  906 */       String mmessage = temp.getProperty(entityStr);
/*  907 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  908 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  910 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  916 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  917 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  919 */       String entityStr = (String)keys.nextElement();
/*  920 */       String mmessage = temp.getProperty(entityStr);
/*  921 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  922 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  924 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  929 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  939 */     String des = new String();
/*  940 */     while (str.indexOf(find) != -1) {
/*  941 */       des = des + str.substring(0, str.indexOf(find));
/*  942 */       des = des + replace;
/*  943 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  945 */     des = des + str;
/*  946 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  953 */       if (alert == null)
/*      */       {
/*  955 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  957 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  959 */         return "&nbsp;";
/*      */       }
/*      */       
/*  962 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  964 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  967 */       int rcalength = test.length();
/*  968 */       if (rcalength < 300)
/*      */       {
/*  970 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  974 */       StringBuffer out = new StringBuffer();
/*  975 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  976 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  977 */       out.append("</div>");
/*  978 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  979 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  980 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  985 */       ex.printStackTrace();
/*      */     }
/*  987 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  993 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  998 */     ArrayList attribIDs = new ArrayList();
/*  999 */     ArrayList resIDs = new ArrayList();
/* 1000 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1002 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1004 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1006 */       String resourceid = "";
/* 1007 */       String resourceType = "";
/* 1008 */       if (type == 2) {
/* 1009 */         resourceid = (String)row.get(0);
/* 1010 */         resourceType = (String)row.get(3);
/*      */       }
/* 1012 */       else if (type == 3) {
/* 1013 */         resourceid = (String)row.get(0);
/* 1014 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1017 */         resourceid = (String)row.get(6);
/* 1018 */         resourceType = (String)row.get(7);
/*      */       }
/* 1020 */       resIDs.add(resourceid);
/* 1021 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1022 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1024 */       String healthentity = null;
/* 1025 */       String availentity = null;
/* 1026 */       if (healthid != null) {
/* 1027 */         healthentity = resourceid + "_" + healthid;
/* 1028 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1031 */       if (availid != null) {
/* 1032 */         availentity = resourceid + "_" + availid;
/* 1033 */         entitylist.add(availentity);
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
/* 1047 */     Properties alert = getStatus(entitylist);
/* 1048 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1053 */     int size = monitorList.size();
/*      */     
/* 1055 */     String[] severity = new String[size];
/*      */     
/* 1057 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1059 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1060 */       String resourceName1 = (String)row1.get(7);
/* 1061 */       String resourceid1 = (String)row1.get(6);
/* 1062 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1063 */       if (severity[j] == null)
/*      */       {
/* 1065 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1069 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1071 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1073 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1076 */         if (sev > 0) {
/* 1077 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1078 */           monitorList.set(k, monitorList.get(j));
/* 1079 */           monitorList.set(j, t);
/* 1080 */           String temp = severity[k];
/* 1081 */           severity[k] = severity[j];
/* 1082 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1088 */     int z = 0;
/* 1089 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1092 */       int i = 0;
/* 1093 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1096 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1100 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1104 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1106 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1109 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1113 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1116 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1117 */       String resourceName1 = (String)row1.get(7);
/* 1118 */       String resourceid1 = (String)row1.get(6);
/* 1119 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1120 */       if (hseverity[j] == null)
/*      */       {
/* 1122 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1127 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1129 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1132 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1135 */         if (hsev > 0) {
/* 1136 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1137 */           monitorList.set(k, monitorList.get(j));
/* 1138 */           monitorList.set(j, t);
/* 1139 */           String temp1 = hseverity[k];
/* 1140 */           hseverity[k] = hseverity[j];
/* 1141 */           hseverity[j] = temp1;
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
/* 1153 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1154 */     boolean forInventory = false;
/* 1155 */     String trdisplay = "none";
/* 1156 */     String plusstyle = "inline";
/* 1157 */     String minusstyle = "none";
/* 1158 */     String haidTopLevel = "";
/* 1159 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1161 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1163 */         haidTopLevel = request.getParameter("haid");
/* 1164 */         forInventory = true;
/* 1165 */         trdisplay = "table-row;";
/* 1166 */         plusstyle = "none";
/* 1167 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1174 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1177 */     ArrayList listtoreturn = new ArrayList();
/* 1178 */     StringBuffer toreturn = new StringBuffer();
/* 1179 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1180 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1181 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1183 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1185 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1186 */       String childresid = (String)singlerow.get(0);
/* 1187 */       String childresname = (String)singlerow.get(1);
/* 1188 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1189 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1190 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1191 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1192 */       String unmanagestatus = (String)singlerow.get(5);
/* 1193 */       String actionstatus = (String)singlerow.get(6);
/* 1194 */       String linkclass = "monitorgp-links";
/* 1195 */       String titleforres = childresname;
/* 1196 */       String titilechildresname = childresname;
/* 1197 */       String childimg = "/images/trcont.png";
/* 1198 */       String flag = "enable";
/* 1199 */       String dcstarted = (String)singlerow.get(8);
/* 1200 */       String configMonitor = "";
/* 1201 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1202 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1204 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1206 */       if (singlerow.get(7) != null)
/*      */       {
/* 1208 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1210 */       String haiGroupType = "0";
/* 1211 */       if ("HAI".equals(childtype))
/*      */       {
/* 1213 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1215 */       childimg = "/images/trend.png";
/* 1216 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1217 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1218 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1220 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1222 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1224 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1225 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1228 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1230 */         linkclass = "disabledtext";
/* 1231 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1233 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1234 */       String availmouseover = "";
/* 1235 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1237 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1239 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1240 */       String healthmouseover = "";
/* 1241 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1243 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1246 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1247 */       int spacing = 0;
/* 1248 */       if (level >= 1)
/*      */       {
/* 1250 */         spacing = 40 * level;
/*      */       }
/* 1252 */       if (childtype.equals("HAI"))
/*      */       {
/* 1254 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1255 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1256 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1258 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1259 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1260 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1261 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1262 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1263 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1264 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1265 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1266 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1267 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1268 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1270 */         if (!forInventory)
/*      */         {
/* 1272 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1275 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1277 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1279 */           actions = editlink + actions;
/*      */         }
/* 1281 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1283 */           actions = actions + associatelink;
/*      */         }
/* 1285 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1286 */         String arrowimg = "";
/* 1287 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1289 */           actions = "";
/* 1290 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1291 */           checkbox = "";
/* 1292 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1294 */         if (isIt360)
/*      */         {
/* 1296 */           actionimg = "";
/* 1297 */           actions = "";
/* 1298 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1299 */           checkbox = "";
/*      */         }
/*      */         
/* 1302 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1304 */           actions = "";
/*      */         }
/* 1306 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1308 */           checkbox = "";
/*      */         }
/*      */         
/* 1311 */         String resourcelink = "";
/*      */         
/* 1313 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1315 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1319 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1322 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1328 */         if (!isIt360)
/*      */         {
/* 1330 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1334 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1337 */         toreturn.append("</tr>");
/* 1338 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1340 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1341 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1345 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1346 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1349 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1353 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1355 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1356 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1357 */             toreturn.append(assocMessage);
/* 1358 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1361 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1367 */         String resourcelink = null;
/* 1368 */         boolean hideEditLink = false;
/* 1369 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1371 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1372 */           hideEditLink = true;
/* 1373 */           if (isIt360)
/*      */           {
/* 1375 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1379 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1381 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1383 */           hideEditLink = true;
/* 1384 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1385 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1390 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1393 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1394 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1395 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1396 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1397 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1398 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1399 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1400 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1401 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1402 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1403 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1404 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1405 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1407 */         if (hideEditLink)
/*      */         {
/* 1409 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1411 */         if (!forInventory)
/*      */         {
/* 1413 */           removefromgroup = "";
/*      */         }
/* 1415 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1416 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1417 */           actions = actions + configcustomfields;
/*      */         }
/* 1419 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1421 */           actions = editlink + actions;
/*      */         }
/* 1423 */         String managedLink = "";
/* 1424 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1426 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1427 */           actions = "";
/* 1428 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1429 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1432 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1434 */           checkbox = "";
/*      */         }
/*      */         
/* 1437 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1439 */           actions = "";
/*      */         }
/* 1441 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1442 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1444 */         if (isIt360)
/*      */         {
/* 1446 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1452 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1454 */         if (!isIt360)
/*      */         {
/* 1456 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1460 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1462 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1465 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1472 */       StringBuilder toreturn = new StringBuilder();
/* 1473 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1474 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1475 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1476 */       String title = "";
/* 1477 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1478 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1479 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1480 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1482 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1484 */       else if ("5".equals(severity))
/*      */       {
/* 1486 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1490 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1492 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1493 */       toreturn.append(v);
/*      */       
/* 1495 */       toreturn.append(link);
/* 1496 */       if (severity == null)
/*      */       {
/* 1498 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1500 */       else if (severity.equals("5"))
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1504 */       else if (severity.equals("4"))
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       else if (severity.equals("1"))
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1515 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1517 */       toreturn.append("</a>");
/* 1518 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1522 */       ex.printStackTrace();
/*      */     }
/* 1524 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1531 */       StringBuilder toreturn = new StringBuilder();
/* 1532 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1533 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1534 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1535 */       if (message == null)
/*      */       {
/* 1537 */         message = "";
/*      */       }
/*      */       
/* 1540 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1541 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1543 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1544 */       toreturn.append(v);
/*      */       
/* 1546 */       toreturn.append(link);
/*      */       
/* 1548 */       if (severity == null)
/*      */       {
/* 1550 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1552 */       else if (severity.equals("5"))
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1556 */       else if (severity.equals("1"))
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1563 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1565 */       toreturn.append("</a>");
/* 1566 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1572 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1575 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1576 */     if (invokeActions != null) {
/* 1577 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1578 */       while (iterator.hasNext()) {
/* 1579 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1580 */         if (actionmap.containsKey(actionid)) {
/* 1581 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1586 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1590 */     String actionLink = "";
/* 1591 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1592 */     String query = "";
/* 1593 */     ResultSet rs = null;
/* 1594 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1595 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1596 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1597 */       actionLink = "method=" + methodName;
/*      */     }
/* 1599 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1600 */       actionLink = methodName;
/*      */     }
/* 1602 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1603 */     Iterator itr = methodarglist.iterator();
/* 1604 */     boolean isfirstparam = true;
/* 1605 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1606 */     while (itr.hasNext()) {
/* 1607 */       HashMap argmap = (HashMap)itr.next();
/* 1608 */       String argtype = (String)argmap.get("TYPE");
/* 1609 */       String argname = (String)argmap.get("IDENTITY");
/* 1610 */       String paramname = (String)argmap.get("PARAMETER");
/* 1611 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1612 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1613 */         isfirstparam = false;
/* 1614 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1616 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1620 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1624 */         actionLink = actionLink + "&";
/*      */       }
/* 1626 */       String paramValue = null;
/* 1627 */       String tempargname = argname;
/* 1628 */       if (commonValues.getProperty(tempargname) != null) {
/* 1629 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1632 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1633 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1634 */           if (dbType.equals("mysql")) {
/* 1635 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1638 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1640 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1642 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1643 */             if (rs.next()) {
/* 1644 */               paramValue = rs.getString("VALUE");
/* 1645 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1649 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1653 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1656 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1661 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1662 */           paramValue = rowId;
/*      */         }
/* 1664 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1665 */           paramValue = managedObjectName;
/*      */         }
/* 1667 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1668 */           paramValue = resID;
/*      */         }
/* 1670 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1671 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1674 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1676 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1677 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1678 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1680 */     return actionLink;
/*      */   }
/*      */   
/* 1683 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1684 */     String dependentAttribute = null;
/* 1685 */     String align = "left";
/*      */     
/* 1687 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1688 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1689 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1690 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1691 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1692 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1693 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1694 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1695 */       align = "center";
/*      */     }
/*      */     
/* 1698 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1699 */     String actualdata = "";
/*      */     
/* 1701 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1702 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1703 */         actualdata = availValue;
/*      */       }
/* 1705 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1706 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1710 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1711 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1714 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1720 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1721 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1722 */       toreturn.append("<table>");
/* 1723 */       toreturn.append("<tr>");
/* 1724 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1725 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1726 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1727 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1728 */         String toolTip = "";
/* 1729 */         String hideClass = "";
/* 1730 */         String textStyle = "";
/* 1731 */         boolean isreferenced = true;
/* 1732 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1733 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1734 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1735 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1737 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1738 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1739 */           while (valueList.hasMoreTokens()) {
/* 1740 */             String dependentVal = valueList.nextToken();
/* 1741 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1742 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1743 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1745 */               toolTip = "";
/* 1746 */               hideClass = "";
/* 1747 */               isreferenced = false;
/* 1748 */               textStyle = "disabledtext";
/* 1749 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1753 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1754 */           toolTip = "";
/* 1755 */           hideClass = "";
/* 1756 */           isreferenced = false;
/* 1757 */           textStyle = "disabledtext";
/* 1758 */           if (dependentImageMap != null) {
/* 1759 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1760 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1763 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1767 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1768 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1769 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1770 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1771 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1772 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1774 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1775 */           if (isreferenced) {
/* 1776 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1780 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1781 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1782 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1783 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1784 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1785 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1787 */           toreturn.append("</span>");
/* 1788 */           toreturn.append("</a>");
/* 1789 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1792 */       toreturn.append("</tr>");
/* 1793 */       toreturn.append("</table>");
/* 1794 */       toreturn.append("</td>");
/*      */     } else {
/* 1796 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1799 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1803 */     String colTime = null;
/* 1804 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1805 */     if ((rows != null) && (rows.size() > 0)) {
/* 1806 */       Iterator<String> itr = rows.iterator();
/* 1807 */       String maxColQuery = "";
/* 1808 */       for (;;) { if (itr.hasNext()) {
/* 1809 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1810 */           ResultSet maxCol = null;
/*      */           try {
/* 1812 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1813 */             while (maxCol.next()) {
/* 1814 */               if (colTime == null) {
/* 1815 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1818 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1827 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1829 */               if (maxCol != null)
/* 1830 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1832 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1827 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1829 */               if (maxCol != null)
/* 1830 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1832 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1837 */     return colTime;
/*      */   }
/*      */   
/* 1840 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1841 */     tablename = null;
/* 1842 */     ResultSet rsTable = null;
/* 1843 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1845 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1846 */       while (rsTable.next()) {
/* 1847 */         tablename = rsTable.getString("DATATABLE");
/* 1848 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1849 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1862 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1853 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1856 */         if (rsTable != null)
/* 1857 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1859 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1865 */     String argsList = "";
/* 1866 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1868 */       if (showArgsMap.get(row) != null) {
/* 1869 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1870 */         if (showArgslist != null) {
/* 1871 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1872 */             if (argsList.trim().equals("")) {
/* 1873 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1876 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1883 */       e.printStackTrace();
/* 1884 */       return "";
/*      */     }
/* 1886 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1891 */     String argsList = "";
/* 1892 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1895 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1897 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1898 */         if (hideArgsList != null)
/*      */         {
/* 1900 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1902 */             if (argsList.trim().equals(""))
/*      */             {
/* 1904 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1908 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1916 */       ex.printStackTrace();
/*      */     }
/* 1918 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1922 */     StringBuilder toreturn = new StringBuilder();
/* 1923 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1930 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1931 */       Iterator itr = tActionList.iterator();
/* 1932 */       while (itr.hasNext()) {
/* 1933 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1934 */         String confirmmsg = "";
/* 1935 */         String link = "";
/* 1936 */         String isJSP = "NO";
/* 1937 */         HashMap tactionMap = (HashMap)itr.next();
/* 1938 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1939 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1940 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1941 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1942 */           (actionmap.containsKey(actionId))) {
/* 1943 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1944 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1945 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1946 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1947 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1949 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1955 */           if (isTableAction) {
/* 1956 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1959 */             tableName = "Link";
/* 1960 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1961 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1962 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1963 */             toreturn.append("</a></td>");
/*      */           }
/* 1965 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1974 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1980 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1982 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1983 */       Properties prop = (Properties)node.getUserObject();
/* 1984 */       String mgID = prop.getProperty("label");
/* 1985 */       String mgName = prop.getProperty("value");
/* 1986 */       String isParent = prop.getProperty("isParent");
/* 1987 */       int mgIDint = Integer.parseInt(mgID);
/* 1988 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1990 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1992 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1993 */       if (node.getChildCount() > 0)
/*      */       {
/* 1995 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1997 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1999 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2001 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2005 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2010 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2012 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2014 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2016 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2020 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2023 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2024 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2026 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2030 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2032 */       if (node.getChildCount() > 0)
/*      */       {
/* 2034 */         builder.append("<UL>");
/* 2035 */         printMGTree(node, builder);
/* 2036 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2041 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2042 */     StringBuffer toReturn = new StringBuffer();
/* 2043 */     String table = "-";
/*      */     try {
/* 2045 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2046 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2047 */       float total = 0.0F;
/* 2048 */       while (it.hasNext()) {
/* 2049 */         String attName = (String)it.next();
/* 2050 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2051 */         boolean roundOffData = false;
/* 2052 */         if ((data != null) && (!data.equals(""))) {
/* 2053 */           if (data.indexOf(",") != -1) {
/* 2054 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2057 */             float value = Float.parseFloat(data);
/* 2058 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2061 */             total += value;
/* 2062 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2065 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2070 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2071 */       while (attVsWidthList.hasNext()) {
/* 2072 */         String attName = (String)attVsWidthList.next();
/* 2073 */         String data = (String)attVsWidthProps.get(attName);
/* 2074 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2075 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2076 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2077 */         String className = (String)graphDetails.get("ClassName");
/* 2078 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2079 */         if (percentage < 1.0F)
/*      */         {
/* 2081 */           data = percentage + "";
/*      */         }
/* 2083 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2085 */       if (toReturn.length() > 0) {
/* 2086 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2090 */       e.printStackTrace();
/*      */     }
/* 2092 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2098 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2099 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2100 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2101 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2102 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2103 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2104 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2105 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2106 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2109 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2110 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2111 */       splitvalues[0] = multiplecondition.toString();
/* 2112 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2115 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2120 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2121 */     if (thresholdType != 3) {
/* 2122 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2123 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2124 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2125 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2126 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2127 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2129 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2130 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2131 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2132 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2133 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2134 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2136 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2137 */     if (updateSelected != null) {
/* 2138 */       updateSelected[0] = "selected";
/*      */     }
/* 2140 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2145 */       StringBuffer toreturn = new StringBuffer("");
/* 2146 */       if (commaSeparatedMsgId != null) {
/* 2147 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2148 */         int count = 0;
/* 2149 */         while (msgids.hasMoreTokens()) {
/* 2150 */           String id = msgids.nextToken();
/* 2151 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2152 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2153 */           count++;
/* 2154 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2155 */             if (toreturn.length() == 0) {
/* 2156 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2158 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2159 */             if (!image.trim().equals("")) {
/* 2160 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2162 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2163 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2166 */         if (toreturn.length() > 0) {
/* 2167 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2171 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2174 */       e.printStackTrace(); }
/* 2175 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2181 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2187 */   private static Map<String, Long> _jspx_dependants = new HashMap(7);
/* 2188 */   static { _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2189 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2190 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2191 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2194 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2231 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2235 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2265 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2269 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2270 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2271 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2272 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2273 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2274 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2275 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2276 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2277 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2278 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2279 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2280 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2281 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2282 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2283 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2284 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2286 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2287 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2288 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2289 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2290 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2291 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2292 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2293 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2294 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.release();
/* 2295 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2296 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2297 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2304 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/* 2307 */     JspWriter out = null;
/* 2308 */     Object page = this;
/* 2309 */     JspWriter _jspx_out = null;
/* 2310 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2314 */       response.setContentType("text/html;charset=UTF-8");
/* 2315 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2317 */       _jspx_page_context = pageContext;
/* 2318 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2319 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2320 */       session = pageContext.getSession();
/* 2321 */       out = pageContext.getOut();
/* 2322 */       _jspx_out = out;
/*      */       
/* 2324 */       out.write("<!DOCTYPE html>\n");
/* 2325 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2327 */       request.setAttribute("HelpKey", "Monitors DB2 Details");
/*      */       
/* 2329 */       out.write(10);
/* 2330 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2331 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2332 */       if (wlsGraph == null) {
/* 2333 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2334 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2336 */       out.write(10);
/* 2337 */       com.adventnet.appmanager.server.db2.bean.DB2Graphs db2graph = null;
/* 2338 */       db2graph = (com.adventnet.appmanager.server.db2.bean.DB2Graphs)_jspx_page_context.getAttribute("db2graph", 1);
/* 2339 */       if (db2graph == null) {
/* 2340 */         db2graph = new com.adventnet.appmanager.server.db2.bean.DB2Graphs();
/* 2341 */         _jspx_page_context.setAttribute("db2graph", db2graph, 1);
/*      */       }
/* 2343 */       out.write(10);
/* 2344 */       com.adventnet.appmanager.bean.PerformanceBean perfgraph = null;
/* 2345 */       perfgraph = (com.adventnet.appmanager.bean.PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2346 */       if (perfgraph == null) {
/* 2347 */         perfgraph = new com.adventnet.appmanager.bean.PerformanceBean();
/* 2348 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2350 */       out.write("\n\n\n\n\n\n\n\n\n");
/* 2351 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2353 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2354 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2355 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2357 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2359 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2361 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2363 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2364 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2365 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2366 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2369 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2370 */         String available = null;
/* 2371 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2372 */         out.write(10);
/*      */         
/* 2374 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2375 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2376 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2378 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2380 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2382 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2384 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2385 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2386 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2387 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2390 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2391 */           String unavailable = null;
/* 2392 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2393 */           out.write(10);
/*      */           
/* 2395 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2396 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2397 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2399 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2401 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2403 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2405 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2406 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2407 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2408 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2411 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2412 */             String unmanaged = null;
/* 2413 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2414 */             out.write(10);
/*      */             
/* 2416 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2417 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2418 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2420 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2422 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2424 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2426 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2427 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2428 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2429 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2432 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2433 */               String scheduled = null;
/* 2434 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2435 */               out.write(10);
/*      */               
/* 2437 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2438 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2439 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2441 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2443 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2445 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2447 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2448 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2449 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2450 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2453 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2454 */                 String critical = null;
/* 2455 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2456 */                 out.write(10);
/*      */                 
/* 2458 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2459 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2460 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2462 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2464 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2466 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2468 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2469 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2470 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2471 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2474 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2475 */                   String clear = null;
/* 2476 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2477 */                   out.write(10);
/*      */                   
/* 2479 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2480 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2481 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2483 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2485 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2487 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2489 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2490 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2491 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2492 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2495 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2496 */                     String warning = null;
/* 2497 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2498 */                     out.write(10);
/* 2499 */                     out.write(10);
/*      */                     
/* 2501 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2502 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2504 */                     out.write(10);
/* 2505 */                     out.write(10);
/* 2506 */                     out.write(10);
/* 2507 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n<script>\nfunction validateAndSubmit()\n{\n\t");
/* 2508 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2510 */                     out.write(10);
/* 2511 */                     out.write(9);
/* 2512 */                     if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2514 */                     out.write("\n}\n</script>\n\n");
/*      */                     
/*      */ 
/* 2517 */                     String tooltip_version = FormatUtil.getString("am.webclient.db2details.version.tooltip");
/* 2518 */                     String tooltip_port = FormatUtil.getString("am.webclient.db2details.port.tooltip");
/* 2519 */                     String tooltip_instance = FormatUtil.getString("am.webclient.db2details.instance.tooltip");
/* 2520 */                     String tooltip_status = FormatUtil.getString("am.webclient.db2details.status.tooltip");
/* 2521 */                     String tooltip_starttime = FormatUtil.getString("am.webclient.db2details.starttime.tooltip");
/* 2522 */                     String tooltip_connectiontime = FormatUtil.getString("am.webclient.db2details.totalcon.tooltip");
/* 2523 */                     String tooltip_totalconnections = FormatUtil.getString("am.webclient.db2details.connections.tooltip");
/* 2524 */                     String tooltip_localconnections = FormatUtil.getString("am.webclient.db2details.localcon.tooltip");
/* 2525 */                     String tooltip_remoteconnections = FormatUtil.getString("am.webclient.db2details.remotecon.tooltip");
/* 2526 */                     String tooltip_activeagents = FormatUtil.getString("am.webclient.db2details.activeagents.tooltip");
/* 2527 */                     String tooltip_idleagents = FormatUtil.getString("am.webclient.db2details.idleagent.tooltip");
/* 2528 */                     String tooltip_numberofagents = FormatUtil.getString("am.webclient.db2details.numofagents.tooltip");
/* 2529 */                     String tooltip_agentswaiting = FormatUtil.getString("am.webclient.db2details.agentswaiting.tooltip");
/*      */                     
/* 2531 */                     String resourceid = request.getParameter("resourceid");
/* 2532 */                     String haid = request.getParameter("haid");
/* 2533 */                     String showdata = (String)request.getAttribute("showdata");
/* 2534 */                     String redirect = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "";
/* 2535 */                     request.setAttribute("redirect", redirect);
/* 2536 */                     String encodeurl = java.net.URLEncoder.encode(redirect);
/* 2537 */                     String displayname = null;
/* 2538 */                     if (request.getAttribute("displayname") == null)
/*      */                     {
/* 2540 */                       displayname = request.getParameter("resourcename");
/*      */                     }
/*      */                     else
/*      */                     {
/* 2544 */                       displayname = (String)request.getAttribute("displayname");
/*      */                     }
/* 2546 */                     ArrayList attribIDs = new ArrayList();
/* 2547 */                     ArrayList resIDs = new ArrayList();
/* 2548 */                     for (int i = 2600; i <= 2631; i++)
/*      */                     {
/* 2550 */                       attribIDs.add("" + i);
/*      */                     }
/* 2552 */                     resIDs.add(resourceid);
/* 2553 */                     Properties db2details = (Properties)request.getAttribute("db2details");
/* 2554 */                     Properties dbmdetails = (Properties)db2details.get("dbmdetails");
/* 2555 */                     Properties dbprops = (Properties)db2details.get("db");
/* 2556 */                     ArrayList tablespaces = (dbprops != null) && (dbprops.get("TABLESTATS") != null) ? (ArrayList)dbprops.get("TABLESTATS") : null;
/* 2557 */                     ArrayList dbinfo = (ArrayList)db2details.get("rows");
/*      */                     
/* 2559 */                     for (int i = 0; i < dbinfo.size(); i++)
/*      */                     {
/* 2561 */                       Properties dbprop = (Properties)dbinfo.get(i);
/* 2562 */                       resIDs.add(dbprop.getProperty("value"));
/*      */                     }
/* 2564 */                     for (int k = 0; (tablespaces != null) && (k < tablespaces.size()); k++)
/*      */                     {
/* 2566 */                       ArrayList tabspaceidrow = (ArrayList)tablespaces.get(k);
/* 2567 */                       resIDs.add((String)tabspaceidrow.get(0));
/*      */                     }
/* 2569 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2570 */                     request.setAttribute("alert", alert);
/* 2571 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */                     
/* 2573 */                     wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2574 */                     perfgraph.setresourceid(Integer.parseInt(resourceid));
/* 2575 */                     perfgraph.setEntity("Response Time");
/* 2576 */                     db2graph.setresid(Integer.parseInt(resourceid));
/*      */                     
/*      */ 
/* 2579 */                     out.write(10);
/*      */                     
/* 2581 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2582 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2583 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2585 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2586 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2587 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2589 */                         out.write(10);
/* 2590 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2592 */                         out.write(10);
/* 2593 */                         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2595 */                         out.write(10);
/* 2596 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2598 */                         out.write(10);
/* 2599 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2601 */                         out.write(10);
/* 2602 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2604 */                         out.write(32);
/*      */                         
/* 2606 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2607 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2608 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2610 */                         _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                         
/* 2612 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 2613 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2614 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2615 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2616 */                             out = _jspx_page_context.pushBody();
/* 2617 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 2618 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2621 */                             out.write(10);
/*      */                             
/* 2623 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2624 */                             String aid = request.getParameter("haid");
/* 2625 */                             String haName = null;
/* 2626 */                             if (aid != null)
/*      */                             {
/* 2628 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 2631 */                             out.write("\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */                             
/* 2633 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2634 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2635 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2637 */                             _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2638 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2639 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 2641 */                                 out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2642 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2643 */                                 out.write(" &gt; ");
/* 2644 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2645 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 2646 */                                 out.print(displayname);
/* 2647 */                                 out.write("</span></td>\n\t");
/* 2648 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2649 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2653 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2654 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                             }
/*      */                             
/* 2657 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2658 */                             out.write(10);
/* 2659 */                             out.write(9);
/*      */                             
/* 2661 */                             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2662 */                             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2663 */                             _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2665 */                             _jspx_th_c_005fif_005f3.setTest("${!empty param.haid && (!empty invalidhaid)}");
/* 2666 */                             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2667 */                             if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                               for (;;) {
/* 2669 */                                 out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2670 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2671 */                                 out.write(" &gt; ");
/* 2672 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("DB2-server"));
/* 2673 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 2674 */                                 out.print(displayname);
/* 2675 */                                 out.write("</span></td>\n\t");
/* 2676 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2677 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2681 */                             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2682 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                             }
/*      */                             
/* 2685 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2686 */                             out.write("\n    </tr>\n</table>\n\n");
/* 2687 */                             if (_jspx_meth_c_005fchoose_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 2689 */                             out.write(10);
/*      */                             
/* 2691 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2692 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2693 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2695 */                             _jspx_th_html_005fform_005f0.setAction("/DB2.do");
/*      */                             
/* 2697 */                             _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 2698 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2699 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 2701 */                                 out.write("\n<input type=\"hidden\" name=\"method\" value=\"update\"/>\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2702 */                                 out.print(haid);
/* 2703 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2704 */                                 out.print(request.getParameter("resourcename"));
/* 2705 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2706 */                                 out.print(request.getParameter("resourceid"));
/* 2707 */                                 out.write("\"/>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t<tr>\n      <td height=\"31\" class=\"tableheading\">");
/* 2708 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2709 */                                 out.write(" </td>\n      <td height=\"31\" class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\"><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\"><span class=\"bodytextboldwhiteun\" ><a href=\"javascript:hideDiv('edit')\" class=\"staticlinks\">");
/* 2710 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2711 */                                 out.write("</a></span>\n\t  </td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=2 cellspacing=2 class=\"lrborder\">\n\t<tr>\n\t  <td width=\"15%\" height=\"35\" class=\"bodytext\">");
/* 2712 */                                 out.print(FormatUtil.getString("am.webclient.common.username.text"));
/* 2713 */                                 out.write("<span class=mandatory>*</span></td>\n\t  <td width=\"72%\">");
/* 2714 */                                 if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2716 */                                 out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td height=\"35\" class=\"bodytext\">");
/* 2717 */                                 out.print(FormatUtil.getString("am.webclient.common.password.text"));
/* 2718 */                                 out.write("<span class=mandatory>*</span></td>\n      <td>");
/* 2719 */                                 if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2721 */                                 out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td height=\"35\" class=\"bodytext\">");
/* 2722 */                                 out.print(FormatUtil.getString("am.webclient.common.databasename"));
/* 2723 */                                 out.write("<span class=mandatory>*</span></td>\n\t  <td>");
/* 2724 */                                 if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2726 */                                 out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td height=\"35\" class=\"bodytext\">");
/* 2727 */                                 out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2728 */                                 out.write("<span class=mandatory>*</span></td>\n\t  <td>");
/* 2729 */                                 if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2731 */                                 out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td height=\"35\" class=\"bodytext\">");
/* 2732 */                                 out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2733 */                                 out.write("<span class=mandatory>*</span></td>\n      <td class=\"bodytext\">");
/* 2734 */                                 if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2736 */                                 out.write(32);
/* 2737 */                                 out.print(FormatUtil.getString("am.webclient.mysql.inminutes"));
/* 2738 */                                 out.write("</td>\n     </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t  <td width=\"17%\" height=\"31\" class=\"tablebottom\">&nbsp;</td>\n\t  <td width=\"83%\" class=\"tablebottom\">");
/*      */                                 
/* 2740 */                                 ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 2741 */                                 _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 2742 */                                 _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2744 */                                 _jspx_th_html_005fbutton_005f0.setOnclick("return validateAndSubmit();");
/*      */                                 
/* 2746 */                                 _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_highlt");
/*      */                                 
/* 2748 */                                 _jspx_th_html_005fbutton_005f0.setProperty("submitbutton3");
/*      */                                 
/* 2750 */                                 _jspx_th_html_005fbutton_005f0.setValue(FormatUtil.getString("am.webclient.common.update.text"));
/* 2751 */                                 int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 2752 */                                 if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 2753 */                                   this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                                 }
/*      */                                 
/* 2756 */                                 this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 2757 */                                 out.write("\n\t  &nbsp;<input type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 2758 */                                 out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2759 */                                 out.write("\" onClick=\"javascript:toggleDiv('edit');\"></td>\n\t</tr>\n</table>\n<br>\n");
/* 2760 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2761 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2765 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2766 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 2769 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2770 */                             out.write("\n</div>\n\n<table width=\"99%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n      <td valign=\"top\">\n\t\t<table width=\"96%\"  border=\"0\" cellpadding=\"1\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n      \t<tr>\n      \t  <td  colspan=\"2\" class=\"tableheadingbborder\">");
/* 2771 */                             out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2772 */                             out.write("</td>\n\t\t</tr>\n        <tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2773 */                             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2774 */                             out.write(" </td>\n\t\t  <td class=\"monitorinfoodd\" title=\"");
/* 2775 */                             out.print(displayname);
/* 2776 */                             out.write(34);
/* 2777 */                             out.write(62);
/* 2778 */                             out.print(getTrimmedText(displayname, 30));
/* 2779 */                             out.write("</td>\n\t\t</tr>\n\t\t");
/* 2780 */                             out.write("<!--$Id$-->\n");
/*      */                             
/* 2782 */                             String hostName = "localhost";
/*      */                             try {
/* 2784 */                               hostName = java.net.InetAddress.getLocalHost().getHostName();
/*      */                             } catch (Exception ex) {
/* 2786 */                               ex.printStackTrace();
/*      */                             }
/* 2788 */                             String portNumber = System.getProperty("webserver.port");
/* 2789 */                             String styleClass = "monitorinfoodd";
/* 2790 */                             if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2791 */                               styleClass = "whitegrayborder-conf-mon";
/*      */                             }
/*      */                             
/* 2794 */                             out.write(10);
/*      */                             
/* 2796 */                             PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2797 */                             _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2798 */                             _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2800 */                             _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 2801 */                             int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2802 */                             if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                               for (;;) {
/* 2804 */                                 out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2805 */                                 out.print(styleClass);
/* 2806 */                                 out.write(34);
/* 2807 */                                 out.write(62);
/* 2808 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2809 */                                 out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2810 */                                 out.print(styleClass);
/* 2811 */                                 out.write(34);
/* 2812 */                                 out.write(62);
/* 2813 */                                 out.print(hostName);
/* 2814 */                                 out.write(95);
/* 2815 */                                 out.print(portNumber);
/* 2816 */                                 out.write("</td>\n</tr>\n");
/* 2817 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2818 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2822 */                             if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2823 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                             }
/*      */                             
/* 2826 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2827 */                             out.write(10);
/* 2828 */                             out.write(10);
/* 2829 */                             out.write(9);
/* 2830 */                             out.write(9);
/*      */                             
/* 2832 */                             String healthStatus = alert.getProperty(resourceid + "#" + "2601");
/*      */                             
/* 2834 */                             out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2835 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2836 */                             out.write("</td>\n\t\t  <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2837 */                             out.print(resourceid);
/* 2838 */                             out.write("&attributeid=2601')\">");
/* 2839 */                             out.print(getSeverityImageForHealth(healthStatus));
/* 2840 */                             out.write("</a>\n\t\t   ");
/* 2841 */                             out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "2601" + "#" + "MESSAGE"), "2601", alert.getProperty(resourceid + "#" + "2601"), resourceid));
/* 2842 */                             out.write("\n\t\t   ");
/* 2843 */                             if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "2601") != 0) {
/* 2844 */                               out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2845 */                               out.print(resourceid + "_2601");
/* 2846 */                               out.write("&monitortype=DB2-server')\">");
/* 2847 */                               out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2848 */                               out.write("</a></span>\n                 ");
/*      */                             }
/* 2850 */                             out.write("\n\t\t  </td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2851 */                             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2852 */                             out.write(" </td>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2853 */                             out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 2854 */                             out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                             
/* 2856 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2857 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2858 */                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2860 */                             _jspx_th_c_005fif_005f4.setTest("${showdata!='1'}");
/* 2861 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2862 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/* 2864 */                                 out.write("\n        ");
/*      */                                 
/* 2866 */                                 if (!dbmdetails.isEmpty())
/*      */                                 {
/* 2868 */                                   String temp = dbmdetails.getProperty("DB2STATUS");
/* 2869 */                                   String status = null;
/* 2870 */                                   String mouseOver = null;
/* 2871 */                                   int db2status = 0;
/*      */                                   try
/*      */                                   {
/* 2874 */                                     db2status = Integer.parseInt(temp);
/*      */                                   }
/*      */                                   catch (Exception e)
/*      */                                   {
/* 2878 */                                     db2status = 4;
/*      */                                   }
/* 2880 */                                   if (db2status == 0)
/*      */                                   {
/* 2882 */                                     status = FormatUtil.getString("am.webclient.db2details.status");
/* 2883 */                                     mouseOver = FormatUtil.getString("am.webclient.db2details.mouseover");
/*      */                                   }
/* 2885 */                                   else if (db2status == 1)
/*      */                                   {
/* 2887 */                                     status = FormatUtil.getString("am.webclient.db2details.status1");
/* 2888 */                                     mouseOver = FormatUtil.getString("am.webclient.db2details.mouseover1");
/*      */                                   }
/* 2890 */                                   else if (db2status == 2)
/*      */                                   {
/* 2892 */                                     status = FormatUtil.getString("am.webclient.db2details.status2");
/* 2893 */                                     mouseOver = FormatUtil.getString("am.webclient.db2details.mouseover2");
/*      */ 
/*      */                                   }
/* 2896 */                                   else if (db2status == 3)
/*      */                                   {
/* 2898 */                                     status = FormatUtil.getString("am.webclient.db2details.status3");
/* 2899 */                                     mouseOver = FormatUtil.getString("am.webclient.db2details.mouseover3");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2903 */                                     status = "-";
/* 2904 */                                     mouseOver = FormatUtil.getString("am.webclient.db2details.mouseover4");
/*      */                                   }
/*      */                                   
/* 2907 */                                   out.write("\n\t\t\t<tr title=\"");
/* 2908 */                                   out.print(tooltip_version);
/* 2909 */                                   out.write("\">\n\t\t\t <td class=\"monitorinfoeven\" >");
/* 2910 */                                   out.print(FormatUtil.getString("am.webclient.db2.version"));
/* 2911 */                                   out.write("</td>\n\t\t\t <td class=\"monitorinfoeven\" height=\"21\"  title=\"");
/* 2912 */                                   out.print(dbmdetails.getProperty("VERSION"));
/* 2913 */                                   out.write(34);
/* 2914 */                                   out.write(62);
/* 2915 */                                   out.print(getTrimmedText(dbmdetails.getProperty("VERSION"), 40));
/* 2916 */                                   out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr title=\"");
/* 2917 */                                   out.print(tooltip_port);
/* 2918 */                                   out.write("\">\n\t\t\t  <td class=\"monitorinfoodd\">");
/* 2919 */                                   out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 2920 */                                   out.write("</td>\n\t\t\t  <td  height=\"21\"  class=\"monitorinfoodd\">");
/* 2921 */                                   out.print(dbmdetails.getProperty("PORT"));
/* 2922 */                                   out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr title=\"");
/* 2923 */                                   out.print(tooltip_instance);
/* 2924 */                                   out.write("\">\n\t\t\t  <td  class=\"monitorinfoeven\">");
/* 2925 */                                   out.print(FormatUtil.getString("am.webclient.db2.instancename"));
/* 2926 */                                   out.write("</td>\n\t\t\t  <td  height=\"21\"  class=\"monitorinfoeven\">");
/* 2927 */                                   out.print(dbmdetails.getProperty("INSTANCENAME"));
/* 2928 */                                   out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t  <td  class=\"monitorinfoeven\" title=\"");
/* 2929 */                                   out.print(tooltip_status);
/* 2930 */                                   out.write(34);
/* 2931 */                                   out.write(62);
/* 2932 */                                   out.print(FormatUtil.getString("am.webclient.db2.serverstatus"));
/* 2933 */                                   out.write("</td>\n\t\t\t  <td  height=\"21\"  class=\"monitorinfoeven\" title=\"");
/* 2934 */                                   out.print(mouseOver);
/* 2935 */                                   out.write(34);
/* 2936 */                                   out.write(62);
/* 2937 */                                   out.print(status);
/* 2938 */                                   out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr title=\"");
/* 2939 */                                   out.print(tooltip_starttime);
/* 2940 */                                   out.write("\">\n\t\t\t  <td  class=\"monitorinfoeven\">");
/* 2941 */                                   out.print(FormatUtil.getString("am.webclient.db2.startedtime"));
/* 2942 */                                   out.write("</td>\n\t\t\t  <td  height=\"21\"  class=\"monitorinfoeven\">\n\t\t\t  ");
/*      */                                   
/* 2944 */                                   if ((((Long)dbmdetails.get("SERVERSTARTTIME")).longValue() != 1L) && (((Long)dbmdetails.get("SERVERSTARTTIME")).longValue() != 0L))
/*      */                                   {
/* 2946 */                                     out.println(formatDT(dbmdetails.get("SERVERSTARTTIME") + ""));
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2950 */                                     out.println("-");
/*      */                                   }
/*      */                                   
/* 2953 */                                   out.write("</td>\n\t\t\t</tr>\n\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2957 */                                 out.write(10);
/* 2958 */                                 out.write(9);
/* 2959 */                                 out.write(9);
/* 2960 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2961 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2965 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2966 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                             }
/*      */                             
/* 2969 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2970 */                             out.write(10);
/* 2971 */                             out.write(9);
/* 2972 */                             out.write(9);
/*      */                             
/* 2974 */                             PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2975 */                             _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2976 */                             _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2978 */                             _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2979 */                             int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2980 */                             if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                               for (;;) {
/* 2982 */                                 out.write(10);
/* 2983 */                                 out.write(9);
/* 2984 */                                 out.write(9);
/*      */                                 
/* 2986 */                                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2987 */                                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2988 */                                 _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                                 
/* 2990 */                                 _jspx_th_c_005fif_005f5.setTest("${showdata=='1'}");
/* 2991 */                                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2992 */                                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                   for (;;) {
/* 2994 */                                     out.write("\n\t\t\t<tr title=\"");
/* 2995 */                                     out.print(tooltip_port);
/* 2996 */                                     out.write("\">\n\t\t\t  <td class=\"monitorinfoeven\">Port</td>\n\t\t\t  <td  height=\"21\"  class=\"monitorinfoeven\">");
/* 2997 */                                     out.print(dbmdetails.getProperty("PORT"));
/* 2998 */                                     out.write("</td>\n\t\t\t</tr>\n\t\t");
/* 2999 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3000 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3004 */                                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3005 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                 }
/*      */                                 
/* 3008 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3009 */                                 out.write(10);
/* 3010 */                                 out.write(9);
/* 3011 */                                 out.write(9);
/* 3012 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3013 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3017 */                             if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3018 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                             }
/*      */                             
/* 3021 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3022 */                             out.write(10);
/* 3023 */                             out.write(9);
/* 3024 */                             out.write(9);
/*      */                             
/* 3026 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3027 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 3028 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3030 */                             _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 3031 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 3032 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 3034 */                                 out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3035 */                                 out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3036 */                                 out.write("</td>\n\t\t  <td class=\"monitorinfoodd\">-&nbsp;</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3037 */                                 out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3038 */                                 out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">-</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3039 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3040 */                                 out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">-</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3041 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3042 */                                 out.write("</td>\n\t\t  <td class=\"monitorinfoodd\">-</td>\n\t\t</tr>\n\t\t");
/* 3043 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3044 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3048 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3049 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 3052 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3053 */                             out.write(10);
/* 3054 */                             out.write(9);
/* 3055 */                             out.write(9);
/*      */                             
/* 3057 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3058 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3059 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3061 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 3062 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3063 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 3065 */                                 out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3066 */                                 out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3067 */                                 out.write("</td>\n\t\t  ");
/*      */                                 
/* 3069 */                                 if (systeminfo.get("host_resid") != null)
/*      */                                 {
/* 3071 */                                   out.write("\n\t\t    <td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 3072 */                                   out.print(systeminfo.get("host_resid"));
/* 3073 */                                   out.write("&method=showResourceForResourceID\" class=\"staticlinks\" title=\"");
/* 3074 */                                   out.print(systeminfo.get("HOSTNAME"));
/* 3075 */                                   out.write(34);
/* 3076 */                                   out.write(32);
/* 3077 */                                   out.write(62);
/* 3078 */                                   out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3079 */                                   out.write("&nbsp;(");
/* 3080 */                                   out.print(systeminfo.get("HOSTIP"));
/* 3081 */                                   out.write(")</a></td>\n\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3086 */                                   out.write("\n             <td class=\"monitorinfoeven\" title=\"");
/* 3087 */                                   out.print(systeminfo.get("HOSTNAME"));
/* 3088 */                                   out.write(34);
/* 3089 */                                   out.write(32);
/* 3090 */                                   out.write(62);
/* 3091 */                                   out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3092 */                                   out.write("&nbsp;(");
/* 3093 */                                   out.print(systeminfo.get("HOSTIP"));
/* 3094 */                                   out.write(")</td>\n\t\t\t");
/*      */                                 }
/* 3096 */                                 out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3097 */                                 out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3098 */                                 out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3099 */                                 out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 3100 */                                 out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                                 
/* 3102 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3103 */                                 _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3104 */                                 _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                 
/* 3106 */                                 _jspx_th_logic_005fnotEmpty_005f1.setName("recent5Alarms");
/* 3107 */                                 int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3108 */                                 if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                   for (;;) {
/* 3110 */                                     out.write(10);
/* 3111 */                                     out.write(9);
/* 3112 */                                     out.write(9);
/*      */                                     
/* 3114 */                                     ArrayList recent = (ArrayList)((ArrayList)request.getAttribute("recent5Alarms")).get(0);
/*      */                                     
/* 3116 */                                     out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3117 */                                     out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 3118 */                                     out.write("</td>\n\t\t  <td class=\"monitorinfoodd\"><a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 3119 */                                     out.print(recent.get(2));
/* 3120 */                                     out.write("&source=");
/* 3121 */                                     out.print(recent.get(4));
/* 3122 */                                     out.write("&category=");
/* 3123 */                                     out.print(recent.get(0));
/* 3124 */                                     out.write("&redirectto=");
/* 3125 */                                     out.print(encodeurl);
/* 3126 */                                     out.write("\"  class=\"resourcename\">");
/* 3127 */                                     out.print(getTruncatedAlertMessage((String)recent.get(3)));
/* 3128 */                                     out.write("</a></td>\n\t\t</tr>\n\t\t");
/* 3129 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3130 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3134 */                                 if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3135 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                 }
/*      */                                 
/* 3138 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3139 */                                 out.write(10);
/* 3140 */                                 out.write(9);
/* 3141 */                                 out.write(9);
/*      */                                 
/* 3143 */                                 EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3144 */                                 _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3145 */                                 _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                 
/* 3147 */                                 _jspx_th_logic_005fempty_005f1.setName("recent5Alarms");
/* 3148 */                                 int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3149 */                                 if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                                   for (;;) {
/* 3151 */                                     out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3152 */                                     out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 3153 */                                     out.write("</td>\n\t\t  <td class=\"monitorinfoodd\">-</td>\n\t\t</tr>\n\t\t");
/* 3154 */                                     int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3155 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3159 */                                 if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3160 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                                 }
/*      */                                 
/* 3163 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3164 */                                 out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3165 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3166 */                                 out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3167 */                                 out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3168 */                                 out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3169 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3170 */                                 out.write("</td>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3171 */                                 out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3172 */                                 out.write("</td>\n\t\t</tr>\n\t\t");
/* 3173 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3174 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3178 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3179 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 3182 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3183 */                             out.write(10);
/* 3184 */                             out.write(9);
/* 3185 */                             out.write(9);
/* 3186 */                             out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 3187 */                             out.write("\n\t{\n\t\t");
/* 3188 */                             if (_jspx_meth_c_005fif_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3190 */                             out.write(10);
/* 3191 */                             out.write(9);
/* 3192 */                             out.write(9);
/* 3193 */                             if (_jspx_meth_c_005fif_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3195 */                             out.write("\n\t\tgetCustomFields('");
/* 3196 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3198 */                             out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 3199 */                             out.write("\n\t}\n\n});\n</script>\n");
/* 3200 */                             if (_jspx_meth_c_005fif_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3202 */                             out.write(10);
/* 3203 */                             out.write(10);
/* 3204 */                             if (_jspx_meth_c_005fif_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3206 */                             out.write(10);
/* 3207 */                             out.write(10);
/* 3208 */                             if (_jspx_meth_c_005fset_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3210 */                             out.write(10);
/* 3211 */                             if (_jspx_meth_c_005fset_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3213 */                             out.write(10);
/* 3214 */                             out.write(10);
/* 3215 */                             out.write(10);
/* 3216 */                             if (_jspx_meth_c_005fif_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3218 */                             out.write(10);
/* 3219 */                             out.write(10);
/* 3220 */                             out.write(10);
/* 3221 */                             if (_jspx_meth_c_005fif_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3223 */                             out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 3224 */                             if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3226 */                             out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 3227 */                             if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3229 */                             out.write("\" onclick=\"getCustomFields('");
/* 3230 */                             if (_jspx_meth_c_005fout_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3232 */                             out.write(39);
/* 3233 */                             out.write(44);
/* 3234 */                             out.write(39);
/* 3235 */                             if (_jspx_meth_c_005fout_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3237 */                             out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 3238 */                             out.write("\n</td>\n</tr>\n\n\n");
/* 3239 */                             out.write("\n        </table>\n\t\t");
/*      */                             
/* 3241 */                             IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3242 */                             _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3243 */                             _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3245 */                             _jspx_th_c_005fif_005f12.setTest("${showdata=='1'}");
/* 3246 */                             int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3247 */                             if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                               for (;;) {
/* 3249 */                                 out.write("\n\t\t\t<div align=\"center\"><a style=cursor:pointer; ><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"javascript:toggleDiv('edit')\">\n            <tr>\n              <td>&nbsp;</td>\n            </tr>\n            <tr>\n              <td><table width=\"75%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"getmoredatatable\">\n                  <tr>\n                    <td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img src=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\" border=\"0\" vspace=\"2\" hspace=\"5\"></td>\n                    <td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 3250 */                                 out.print(FormatUtil.getString("am.webclient.configureimage.mssql.text"));
/* 3251 */                                 out.write("</td>\n                  </tr>\n                </table></td>\n            </tr>\n      </table></a></div>\n\t\t");
/* 3252 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3253 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3257 */                             if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3258 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                             }
/*      */                             
/* 3261 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3262 */                             out.write("\n      </td>\n      <td width=\"40%\" height=\"31\" class=\"bodytextbold\" valign=\"top\" >\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n        <tbody>\n        <tr>\n          <td colspan=\"4\" height=\"31\" class=\"tableheadingbborder\"> ");
/* 3263 */                             out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3264 */                             out.write(" <a name=\"Availability\" id=\"Availability\"></a></td>\n        </tr>\n\t\t<tr>\n\t\t  <td colspan=\"4\">\n\t\t  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"right\">\n          <tr>\n            <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3265 */                             if (_jspx_meth_c_005fout_005f12(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3267 */                             out.write("&period=1&resourcename=");
/* 3268 */                             out.print(displayname);
/* 3269 */                             out.write("')\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3270 */                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3271 */                             out.write("'></a></td>\n            <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3272 */                             if (_jspx_meth_c_005fout_005f13(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3274 */                             out.write("&period=2&resourcename=");
/* 3275 */                             out.print(displayname);
/* 3276 */                             out.write("')\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3277 */                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3278 */                             out.write("'></a></td>\n      \t  </tr>\n          </table>\n          </td>\n        </tr>\n        <tr>\n          <td colspan=\"4\" align=\"center\">\n\t\t\t ");
/*      */                             
/* 3280 */                             AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3281 */                             _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3282 */                             _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3284 */                             _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                             
/* 3286 */                             _jspx_th_awolf_005fpiechart_005f0.setWidth("280");
/*      */                             
/* 3288 */                             _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                             
/* 3290 */                             _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                             
/* 3292 */                             _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                             
/* 3294 */                             _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                             
/* 3296 */                             _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3297 */                             int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3298 */                             if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3299 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3300 */                                 out = _jspx_page_context.pushBody();
/* 3301 */                                 _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3302 */                                 _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3305 */                                 out.write("\n\t\t\t\t");
/*      */                                 
/* 3307 */                                 Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3308 */                                 _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3309 */                                 _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                 
/* 3311 */                                 _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3312 */                                 int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3313 */                                 if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3314 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3315 */                                     out = _jspx_page_context.pushBody();
/* 3316 */                                     _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3317 */                                     _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3320 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3322 */                                     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3323 */                                     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3324 */                                     _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                     
/* 3326 */                                     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                     
/* 3328 */                                     _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3329 */                                     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3330 */                                     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3331 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                     }
/*      */                                     
/* 3334 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3335 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3337 */                                     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3338 */                                     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3339 */                                     _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                     
/* 3341 */                                     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                     
/* 3343 */                                     _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3344 */                                     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3345 */                                     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3346 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                     }
/*      */                                     
/* 3349 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3350 */                                     out.write("\n\t\t\t\t");
/* 3351 */                                     int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3352 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3355 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3356 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3359 */                                 if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3360 */                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                 }
/*      */                                 
/* 3363 */                                 this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3364 */                                 out.write("\n\t\t\t  ");
/* 3365 */                                 int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3366 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3369 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3370 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3373 */                             if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3374 */                               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                             }
/*      */                             
/* 3377 */                             this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3378 */                             out.write("\n          </td>\n        </tr>\n\t\t<tr>\n\t\t\t\t  <td  width=\"49%\" class=\"yellowgrayborder\" colspan=\"2\" align=\"left\">");
/* 3379 */                             out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3380 */                             out.write("\n\t\t\t\t  ");
/*      */                             
/* 3382 */                             String avastatus = alert.getProperty(resourceid + "#" + "2600");
/*      */                             
/* 3384 */                             out.write("\n\t\t\t\t  <a style=\"position:relative; top:2px;\"href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3385 */                             out.print(resourceid);
/* 3386 */                             out.write("&attributeid=2600')\">");
/* 3387 */                             out.print(getSeverityImageForAvailability(avastatus));
/* 3388 */                             out.write("</a></td>\n\t\t\t\t  <td width=\"50%\"  class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3389 */                             out.print(resourceid);
/* 3390 */                             out.write("&attributeIDs=2600,2601&attributeToSelect=2600&redirectto=");
/* 3391 */                             out.print(encodeurl);
/* 3392 */                             out.write("\" class=\"links\">");
/* 3393 */                             out.print(ALERTCONFIG_TEXT);
/* 3394 */                             out.write("</a>&nbsp;</td>\n\t\t</tr>\n        </tbody>\n        </table>\n      </td>\n\t</tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3395 */                             out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 3396 */                             out.write("</td></tr></table>\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t  <td>&nbsp;</td>\n\t</tr>\n</table>\n\n");
/*      */                             
/* 3398 */                             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3399 */                             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3400 */                             _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3402 */                             _jspx_th_c_005fif_005f13.setTest("${showdata=='1'}");
/* 3403 */                             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3404 */                             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                               for (;;) {
/* 3406 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3407 */                                 out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3408 */                                 out.write(32);
/* 3409 */                                 out.write(45);
/* 3410 */                                 out.write(32);
/* 3411 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3412 */                                 out.write("&nbsp;</td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n\t<tr>\n\t  <td width=\"405\" height=\"127\" valign=\"top\">\n\t  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n\t    <tr>\n\t\t  <td width=\"96%\" align=\"right\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3413 */                                 if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                   return;
/* 3415 */                                 out.write("&attributeid=2602&period=-7',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3416 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3417 */                                 out.write("'></td>\n\t\t  <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3418 */                                 if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                   return;
/* 3420 */                                 out.write("&attributeid=2602&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3421 */                                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3422 */                                 out.write("'></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"2\">\n\t\t  ");
/*      */                                 
/* 3424 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3425 */                                 _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3426 */                                 _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f13);
/*      */                                 
/* 3428 */                                 _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("perfgraph");
/*      */                                 
/* 3430 */                                 _jspx_th_awolf_005ftimechart_005f0.setWidth("300");
/*      */                                 
/* 3432 */                                 _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                                 
/* 3434 */                                 _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                                 
/* 3436 */                                 _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                 
/* 3438 */                                 _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.db2.graph.responsetimeinms"));
/* 3439 */                                 int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3440 */                                 if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3441 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3442 */                                     out = _jspx_page_context.pushBody();
/* 3443 */                                     _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3444 */                                     _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3447 */                                     out.write("\n\t\t  ");
/* 3448 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3449 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3452 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3453 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3456 */                                 if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3457 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                 }
/*      */                                 
/* 3460 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3461 */                                 out.write("\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t  <td width=\"562\" valign=\"top\"> <br> <br>\n\t  <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n\t\t<tr>\n\t\t  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3462 */                                 if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                   return;
/* 3464 */                                 out.write("</span></td>\n\t\t  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3465 */                                 if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                   return;
/* 3467 */                                 out.write("</span></td>\n\t\t  <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3468 */                                 if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                   return;
/* 3470 */                                 out.write("</span></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3471 */                                 out.print(tooltip_connectiontime);
/* 3472 */                                 out.write("\">\n\t\t  <td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3473 */                                 out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3474 */                                 out.write(" </td>\n\t\t  <td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n\t\t  ");
/* 3475 */                                 if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) == -1L)
/*      */                                 {
/* 3477 */                                   out.write(32);
/* 3478 */                                   out.write(45);
/* 3479 */                                   out.write(32);
/*      */                                 } else {
/* 3481 */                                   out.write("\n\t\t  ");
/* 3482 */                                   out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 3483 */                                   out.write(" ms\n\t\t  ");
/*      */                                 }
/* 3485 */                                 out.write("\n\t\t  </td>\n\t\t  ");
/*      */                                 
/* 3487 */                                 String status2602 = alert.getProperty(resourceid + "#" + "2602");
/*      */                                 
/* 3489 */                                 out.write("\n\t\t  <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3490 */                                 out.print(resourceid);
/* 3491 */                                 out.write("&attributeid=2602')\">");
/* 3492 */                                 out.print(getSeverityImage(status2602));
/* 3493 */                                 out.write("&nbsp;</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" height=\"21\"  align=\"right\"><img src=\"/images/icon_associateaction.gif\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3494 */                                 out.print(resourceid);
/* 3495 */                                 out.write("&attributeIDs=2602&attributeToSelect=2602&redirectto=");
/* 3496 */                                 out.print(encodeurl);
/* 3497 */                                 out.write("\" class=\"links\">");
/* 3498 */                                 out.print(ALERTCONFIG_TEXT);
/* 3499 */                                 out.write("</a>&nbsp;</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t  <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t</tr>\n</table>\n");
/* 3500 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3501 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3505 */                             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3506 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                             }
/*      */                             
/* 3509 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3510 */                             out.write(10);
/* 3511 */                             out.write(10);
/*      */                             
/* 3513 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3514 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3515 */                             _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3517 */                             _jspx_th_c_005fif_005f14.setTest("${showdata!='1'}");
/* 3518 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3519 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/* 3521 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbtborder\">\n\t<tr>\n\t  <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 3522 */                                 out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3523 */                                 out.write(32);
/* 3524 */                                 out.write(45);
/* 3525 */                                 out.write(32);
/* 3526 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3527 */                                 out.write("</td>\n\t  <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 3528 */                                 out.print(FormatUtil.getString("am.webclient.db2.connectionstatistics"));
/* 3529 */                                 out.write(32);
/* 3530 */                                 out.write(45);
/* 3531 */                                 out.write(32);
/* 3532 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3533 */                                 out.write("</td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t<tr>\n\t  <td width=\"49%\" height=\"38\" >\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  class=\"rbborder\">\n\t\t<tr>\n\t\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3534 */                                 out.print(resourceid);
/* 3535 */                                 out.write("&attributeid=2602&period=-7&resourcename=");
/* 3536 */                                 out.print(displayname);
/* 3537 */                                 out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3538 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3539 */                                 out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3540 */                                 out.print(resourceid);
/* 3541 */                                 out.write("&attributeid=2602&period=-30&resourcename=");
/* 3542 */                                 out.print(displayname);
/* 3543 */                                 out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3544 */                                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3545 */                                 out.write("'></a></td>\n\t\t</tr>\n\t\t");
/* 3546 */                                 db2graph.settype("CONNECTION");
/* 3547 */                                 out.write("\n\t\t<tr>\n\t\t  <td>");
/*      */                                 
/* 3549 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3550 */                                 _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3551 */                                 _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fif_005f14);
/*      */                                 
/* 3553 */                                 _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("db2graph");
/*      */                                 
/* 3555 */                                 _jspx_th_awolf_005ftimechart_005f1.setWidth("330");
/*      */                                 
/* 3557 */                                 _jspx_th_awolf_005ftimechart_005f1.setHeight("185");
/*      */                                 
/* 3559 */                                 _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                                 
/* 3561 */                                 _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                 
/* 3563 */                                 _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.db2.graph.connectiontime"));
/* 3564 */                                 int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3565 */                                 if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3566 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                 }
/*      */                                 
/* 3569 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3570 */                                 out.write("</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t  <td width=\"50%\" height=\"38\" class=\"bottomborder\">\n\t  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3571 */                                 out.print(resourceid);
/* 3572 */                                 out.write("&attributeid=2605&period=-7&resourcename=");
/* 3573 */                                 out.print(displayname);
/* 3574 */                                 out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3575 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3576 */                                 out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3577 */                                 out.print(resourceid);
/* 3578 */                                 out.write("&attributeid=2605&period=-30&resourcename=");
/* 3579 */                                 out.print(displayname);
/* 3580 */                                 out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3581 */                                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3582 */                                 out.write("'></a></td>\n\t\t</tr>\n\t\t");
/* 3583 */                                 db2graph.settype("CONNECTIONSTATICS");
/* 3584 */                                 out.write("\n\t\t<tr>\n\t\t  <td>");
/*      */                                 
/* 3586 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3587 */                                 _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3588 */                                 _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_c_005fif_005f14);
/*      */                                 
/* 3590 */                                 _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("db2graph");
/*      */                                 
/* 3592 */                                 _jspx_th_awolf_005ftimechart_005f2.setWidth("330");
/*      */                                 
/* 3594 */                                 _jspx_th_awolf_005ftimechart_005f2.setHeight("185");
/*      */                                 
/* 3596 */                                 _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                                 
/* 3598 */                                 _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                 
/* 3600 */                                 _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.db2.graph.noofconnections"));
/* 3601 */                                 int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3602 */                                 if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3603 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                                 }
/*      */                                 
/* 3606 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3607 */                                 out.write("</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t</tr>\n\t<tr>\n\t  <td valign=\"top\" class=\"rborder\">\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3608 */                                 if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3610 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3611 */                                 if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3613 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3614 */                                 if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3616 */                                 out.write("</span></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3617 */                                 out.print(tooltip_connectiontime);
/* 3618 */                                 out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3619 */                                 out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3620 */                                 out.write("</td>\n\t\t  <td width=\"27%\" class=\"whitegrayborder\">");
/* 3621 */                                 out.print(formatNumber(dbmdetails.get("RESPONSETIME")));
/* 3622 */                                 out.write("&nbsp;ms</td>\n\t  \t  <td width=\"36%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3623 */                                 out.print(resourceid);
/* 3624 */                                 out.write("&attributeid=2602')\">");
/* 3625 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "2602")));
/* 3626 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\" ><img style=\"position:relative; top:3px;\" src=\"../images/icon_associateaction.gif\">&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3627 */                                 out.print(resourceid);
/* 3628 */                                 out.write("&attributeIDs=2602&attributeToSelect=2602&redirectto=");
/* 3629 */                                 out.print(encodeurl);
/* 3630 */                                 out.write("'class=\"staticlinks\">");
/* 3631 */                                 out.print(ALERTCONFIG_TEXT);
/* 3632 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" class=\"whitegrayborder\"></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" class=\"yellowgrayborder\"></td>\n\t\t</tr>\n  \t  </table>\n  \t  </td>\n\t  <td valign=\"top\">\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3633 */                                 if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3635 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3636 */                                 if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3638 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3639 */                                 if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3641 */                                 out.write("</span></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3642 */                                 out.print(tooltip_totalconnections);
/* 3643 */                                 out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3644 */                                 out.print(FormatUtil.getString("am.webclient.db2.totalconnections"));
/* 3645 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">");
/* 3646 */                                 out.print(dbmdetails.getProperty("TOTALCONN"));
/* 3647 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3648 */                                 out.print(resourceid);
/* 3649 */                                 out.write("&attributeid=2605')\">");
/* 3650 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "2605")));
/* 3651 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3652 */                                 out.print(tooltip_localconnections);
/* 3653 */                                 out.write("\">\n\t\t  <td class=\"yellowgrayborder\">");
/* 3654 */                                 out.print(FormatUtil.getString("am.webclient.db2.localconnections"));
/* 3655 */                                 out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">");
/* 3656 */                                 out.print(dbmdetails.getProperty("LOCALCONN"));
/* 3657 */                                 out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3658 */                                 out.print(resourceid);
/* 3659 */                                 out.write("&attributeid=2604')\">");
/* 3660 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "2604")));
/* 3661 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3662 */                                 out.print(tooltip_remoteconnections);
/* 3663 */                                 out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3664 */                                 out.print(FormatUtil.getString("am.webclient.db2.remoteconnections"));
/* 3665 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">");
/* 3666 */                                 out.print(dbmdetails.getProperty("REMOTECONN"));
/* 3667 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3668 */                                 out.print(resourceid);
/* 3669 */                                 out.write("&attributeid=2603')\">");
/* 3670 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "2603")));
/* 3671 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\"><img style=\"position:relative; top:3px;\" src=\"../images/icon_associateaction.gif\">&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3672 */                                 out.print(resourceid);
/* 3673 */                                 out.write("&attributeIDs=2604,2603,2605&attributeToSelect=2604&redirectto=");
/* 3674 */                                 out.print(encodeurl);
/* 3675 */                                 out.write("'class=\"staticlinks\">");
/* 3676 */                                 out.print(ALERTCONFIG_TEXT);
/* 3677 */                                 out.write("</a></td>\n\t\t</tr>\n  \t  </table>\n  \t  </td>\n\t</tr>\n</table>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t  <td width=\"50%\" height=\"31\" class=\"tableheadingtrans\">");
/* 3678 */                                 out.print(FormatUtil.getString("am.webclient.db2.agentsstatistics"));
/* 3679 */                                 out.write(32);
/* 3680 */                                 out.write(45);
/* 3681 */                                 out.write(32);
/* 3682 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3683 */                                 out.write("</td>\n\t  <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 3684 */                                 out.print(FormatUtil.getString("am.webclient.db2.agentswaiting"));
/* 3685 */                                 out.write(32);
/* 3686 */                                 out.write(45);
/* 3687 */                                 out.write(32);
/* 3688 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3689 */                                 out.write("</td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t<tr>\n\t  <td width=\"49%\" height=\"38\"  class=\"rbborder\">\n\t  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3690 */                                 out.print(resourceid);
/* 3691 */                                 out.write("&attributeid=2609&period=-7&resourcename=");
/* 3692 */                                 out.print(displayname);
/* 3693 */                                 out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3694 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3695 */                                 out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3696 */                                 out.print(resourceid);
/* 3697 */                                 out.write("&attributeid=2609&period=-30&resourcename=");
/* 3698 */                                 out.print(displayname);
/* 3699 */                                 out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3700 */                                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3701 */                                 out.write("'></a></td>\n\t\t</tr>\n\t\t");
/* 3702 */                                 db2graph.settype("ACTIVEAGENTSTATICS");
/* 3703 */                                 out.write("\n\t\t<tr>\n\t\t  <td>");
/*      */                                 
/* 3705 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3706 */                                 _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3707 */                                 _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_c_005fif_005f14);
/*      */                                 
/* 3709 */                                 _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("db2graph");
/*      */                                 
/* 3711 */                                 _jspx_th_awolf_005ftimechart_005f3.setWidth("330");
/*      */                                 
/* 3713 */                                 _jspx_th_awolf_005ftimechart_005f3.setHeight("185");
/*      */                                 
/* 3715 */                                 _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */                                 
/* 3717 */                                 _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                 
/* 3719 */                                 _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.db2.graph.noofactiveagents"));
/* 3720 */                                 int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3721 */                                 if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3722 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                                 }
/*      */                                 
/* 3725 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3726 */                                 out.write("</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t  <td width=\"50%\" height=\"38\" class=\"bottomborder\">\n\t  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3727 */                                 out.print(resourceid);
/* 3728 */                                 out.write("&attributeid=2607&period=-7&resourcename=");
/* 3729 */                                 out.print(displayname);
/* 3730 */                                 out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3731 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3732 */                                 out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3733 */                                 out.print(resourceid);
/* 3734 */                                 out.write("&attributeid=2607&period=-30&resourcename=");
/* 3735 */                                 out.print(displayname);
/* 3736 */                                 out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3737 */                                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3738 */                                 out.write("'></a></td>\n\t\t</tr>\n\t\t");
/* 3739 */                                 db2graph.settype("WAITINGAGENTSTATICS");
/* 3740 */                                 out.write("\n\t\t<tr>\n\t\t  <td>");
/*      */                                 
/* 3742 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3743 */                                 _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 3744 */                                 _jspx_th_awolf_005ftimechart_005f4.setParent(_jspx_th_c_005fif_005f14);
/*      */                                 
/* 3746 */                                 _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("db2graph");
/*      */                                 
/* 3748 */                                 _jspx_th_awolf_005ftimechart_005f4.setWidth("330");
/*      */                                 
/* 3750 */                                 _jspx_th_awolf_005ftimechart_005f4.setHeight("185");
/*      */                                 
/* 3752 */                                 _jspx_th_awolf_005ftimechart_005f4.setLegend("true");
/*      */                                 
/* 3754 */                                 _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                 
/* 3756 */                                 _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel(FormatUtil.getString("am.webclient.db2.graph.noofagentswaiting"));
/* 3757 */                                 int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 3758 */                                 if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 3759 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f4); return;
/*      */                                 }
/*      */                                 
/* 3762 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 3763 */                                 out.write("</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t</tr>\n\t<tr>\n\t  <td valign=\"top\" class=\"rborder\">\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3764 */                                 if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3766 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3767 */                                 if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3769 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3770 */                                 if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3772 */                                 out.write("</span></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3773 */                                 out.print(tooltip_activeagents);
/* 3774 */                                 out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3775 */                                 out.print(FormatUtil.getString("am.webclient.db2.activeagents"));
/* 3776 */                                 out.write("</td>\n\t\t  <td width=\"27%\" class=\"whitegrayborder\">");
/* 3777 */                                 out.print(dbmdetails.get("AGENTSACTIVE"));
/* 3778 */                                 out.write("</td>\n\t  \t  <td width=\"36%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3779 */                                 out.print(resourceid);
/* 3780 */                                 out.write("&attributeid=2609')\">");
/* 3781 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "2609")));
/* 3782 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3783 */                                 out.print(tooltip_idleagents);
/* 3784 */                                 out.write("\">\n\t\t  <td class=\"yellowgrayborder\">");
/* 3785 */                                 out.print(FormatUtil.getString("am.webclient.db2.idleagents"));
/* 3786 */                                 out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">");
/* 3787 */                                 out.print(dbmdetails.getProperty("AGENTSIDLE"));
/* 3788 */                                 out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3789 */                                 out.print(resourceid);
/* 3790 */                                 out.write("&attributeid=2608')\">");
/* 3791 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "2608")));
/* 3792 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3793 */                                 out.print(tooltip_numberofagents);
/* 3794 */                                 out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3795 */                                 out.print(FormatUtil.getString("am.webclient.db2.numberofagents"));
/* 3796 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">");
/* 3797 */                                 out.print(dbmdetails.getProperty("AGENTSREGISTERED"));
/* 3798 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3799 */                                 out.print(resourceid);
/* 3800 */                                 out.write("&attributeid=2606')\">");
/* 3801 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "2606")));
/* 3802 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\" ><img  style=\"position:relative; top:3px;\" src=\"../images/icon_associateaction.gif\">&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3803 */                                 out.print(resourceid);
/* 3804 */                                 out.write("&attributeIDs=2609,2608,2606&attributeToSelect=2609&redirectto=");
/* 3805 */                                 out.print(encodeurl);
/* 3806 */                                 out.write("'class=\"staticlinks\">");
/* 3807 */                                 out.print(ALERTCONFIG_TEXT);
/* 3808 */                                 out.write("</a></td>\n\t\t</tr>\n  \t  </table>\n  \t  </td>\n\t  <td valign=\"top\">\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3809 */                                 if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3811 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3812 */                                 if (_jspx_meth_fmt_005fmessage_005f18(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3814 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3815 */                                 if (_jspx_meth_fmt_005fmessage_005f19(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                   return;
/* 3817 */                                 out.write("</span></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3818 */                                 out.print(tooltip_agentswaiting);
/* 3819 */                                 out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3820 */                                 out.print(FormatUtil.getString("am.webclient.db2.agentswaiting"));
/* 3821 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">");
/* 3822 */                                 out.print(dbmdetails.getProperty("AGENTSWAITING"));
/* 3823 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3824 */                                 out.print(resourceid);
/* 3825 */                                 out.write("&attributeid=2607')\">");
/* 3826 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "2607")));
/* 3827 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\" ><img style=\"position:relative; top:3px;\" src=\"../images/icon_associateaction.gif\" style=\"position:relative; top:3px;\">&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3828 */                                 out.print(resourceid);
/* 3829 */                                 out.write("&attributeIDs=2607&attributeToSelect=2607&redirectto=");
/* 3830 */                                 out.print(encodeurl);
/* 3831 */                                 out.write("'class=\"staticlinks\">");
/* 3832 */                                 out.print(ALERTCONFIG_TEXT);
/* 3833 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" class=\"whitegrayborder\"></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" class=\"yellowgrayborder\"></td>\n\t\t</tr>\n  \t  </table>\n  \t  </td>\n\t</tr>\n</table>\n<br>\n\n\n");
/* 3834 */                                 if (!db2details.get("selectedDatabaseId").equals("-1")) {
/* 3835 */                                   out.write(10);
/* 3836 */                                   org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "DB2DatabaseDetails.jsp", out, false);
/* 3837 */                                   out.write(10);
/*      */                                 }
/* 3839 */                                 out.write(10);
/* 3840 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3841 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3845 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3846 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                             }
/*      */                             
/* 3849 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3850 */                             out.write("\n<br>\n");
/*      */                             
/* 3852 */                             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3853 */                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3854 */                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3856 */                             _jspx_th_logic_005fiterate_005f0.setName("script_ids");
/*      */                             
/* 3858 */                             _jspx_th_logic_005fiterate_005f0.setId("attribute");
/*      */                             
/* 3860 */                             _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                             
/* 3862 */                             _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 3863 */                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3864 */                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3865 */                               String attribute = null;
/* 3866 */                               Integer j = null;
/* 3867 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3868 */                                 out = _jspx_page_context.pushBody();
/* 3869 */                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3870 */                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                               }
/* 3872 */                               attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3873 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                               for (;;) {
/* 3875 */                                 out.write(10);
/* 3876 */                                 out.write(9);
/* 3877 */                                 out.write(32);
/*      */                                 
/* 3879 */                                 String query = "select scriptname,displayname from AM_ScriptArgs where resourceid=" + attribute;
/* 3880 */                                 String monitorname1 = null;
/* 3881 */                                 String displayname1 = null;
/*      */                                 try
/*      */                                 {
/* 3884 */                                   AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3885 */                                   ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 3886 */                                   if (rs.next())
/*      */                                   {
/* 3888 */                                     monitorname1 = rs.getString("scriptname");
/* 3889 */                                     displayname1 = rs.getString("displayname");
/*      */                                   }
/* 3891 */                                   rs.close();
/*      */                                 }
/*      */                                 catch (Exception exc) {}
/*      */                                 
/*      */ 
/* 3896 */                                 String url2 = "/showresource.do?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true";
/* 3897 */                                 String url3 = "/jsp/HostScript.jsp?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true&hostid=" + resourceid;
/*      */                                 
/* 3899 */                                 out.write("\n         <table  width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n\t ");
/* 3900 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, url2, out, false);
/* 3901 */                                 out.write("\n         <tr>\n         <td width=\"99%\"   class=\"tableheadingtrans\" >\n         <a href=\"showresource.do?method=showResourceForResourceID&resourceid=");
/* 3902 */                                 out.print(attribute);
/* 3903 */                                 out.write("\" class=\"staticlinks\">");
/* 3904 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.scriptmonitor"));
/* 3905 */                                 out.write(32);
/* 3906 */                                 out.write(45);
/* 3907 */                                 out.write(32);
/* 3908 */                                 out.print(displayname1);
/* 3909 */                                 out.write("</a>\n         </td>\n         </tr>\n         <tr>\n         <td>\n         ");
/* 3910 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, url3, out, false);
/* 3911 */                                 out.write("\n         </td>\n         </tr>\n         <br>\n         </table>\n         <br>\n         ");
/* 3912 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3913 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3914 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 3915 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3918 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3919 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3922 */                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3923 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                             }
/*      */                             
/* 3926 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3927 */                             out.write("\n\t <br>\n\t");
/* 3928 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 3929 */                             DialChartSupport dialGraph = null;
/* 3930 */                             dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 3931 */                             if (dialGraph == null) {
/* 3932 */                               dialGraph = new DialChartSupport();
/* 3933 */                               _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                             }
/* 3935 */                             out.write(10);
/*      */                             
/*      */                             try
/*      */                             {
/* 3939 */                               String hostos = (String)systeminfo.get("HOSTOS");
/* 3940 */                               String hostname = (String)systeminfo.get("HOSTNAME");
/* 3941 */                               String hostid = (String)systeminfo.get("host_resid");
/* 3942 */                               boolean isConf = false;
/* 3943 */                               if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 3944 */                                 isConf = true;
/*      */                               }
/* 3946 */                               com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 3947 */                               Properties property = new Properties();
/* 3948 */                               if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                               {
/* 3950 */                                 property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 3951 */                                 if ((property != null) && (property.size() > 0))
/*      */                                 {
/* 3953 */                                   String cpuid = property.getProperty("cpuid");
/* 3954 */                                   String memid = property.getProperty("memid");
/* 3955 */                                   String diskid = property.getProperty("diskid");
/* 3956 */                                   String cpuvalue = property.getProperty("CPU Utilization");
/* 3957 */                                   String memvalue = property.getProperty("Memory Utilization");
/* 3958 */                                   String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 3959 */                                   String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 3960 */                                   String diskvalue = property.getProperty("Disk Utilization");
/* 3961 */                                   String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                   
/* 3963 */                                   if (!isConf) {
/* 3964 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 3965 */                                     out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 3966 */                                     out.write(45);
/* 3967 */                                     if (systeminfo.get("host_resid") != null) {
/* 3968 */                                       out.write("<a href=\"showresource.do?resourceid=");
/* 3969 */                                       out.print(hostid);
/* 3970 */                                       out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 3971 */                                       out.print(hostname);
/* 3972 */                                       out.write("</a>");
/* 3973 */                                     } else { out.println(hostname); }
/* 3974 */                                     out.write("</td>\t");
/* 3975 */                                     out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 3976 */                                     out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3977 */                                     out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                     
/*      */ 
/* 3980 */                                     if (cpuvalue != null)
/*      */                                     {
/*      */ 
/* 3983 */                                       dialGraph.setValue(Long.parseLong(cpuvalue));
/* 3984 */                                       out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3985 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 3986 */                                       out.write(45);
/* 3987 */                                       out.print(cpuvalue);
/* 3988 */                                       out.write(" %'>\n\n");
/*      */                                       
/* 3990 */                                       DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 3991 */                                       _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 3992 */                                       _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 3994 */                                       _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                       
/* 3996 */                                       _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                       
/* 3998 */                                       _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                       
/* 4000 */                                       _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                       
/* 4002 */                                       _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                       
/* 4004 */                                       _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                       
/* 4006 */                                       _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                       
/* 4008 */                                       _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                       
/* 4010 */                                       _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                       
/* 4012 */                                       _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 4013 */                                       int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 4014 */                                       if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 4015 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4016 */                                           out = _jspx_page_context.pushBody();
/* 4017 */                                           _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 4018 */                                           _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4021 */                                           out.write(10);
/* 4022 */                                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 4023 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4026 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4027 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4030 */                                       if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 4031 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                       }
/*      */                                       
/* 4034 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 4035 */                                       out.write("\n         </td>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4039 */                                       out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4040 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4041 */                                       out.write(32);
/* 4042 */                                       out.write(62);
/* 4043 */                                       out.write(10);
/* 4044 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4045 */                                       out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                     }
/* 4047 */                                     out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4048 */                                     if (cpuvalue != null)
/*      */                                     {
/* 4050 */                                       out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4051 */                                       out.print(hostid);
/* 4052 */                                       out.write("&attributeid=");
/* 4053 */                                       out.print(cpuid);
/* 4054 */                                       out.write("&period=-7')\" class='bodytextbold'>");
/* 4055 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4056 */                                       out.write(32);
/* 4057 */                                       out.write(45);
/* 4058 */                                       out.write(32);
/* 4059 */                                       out.print(cpuvalue);
/* 4060 */                                       out.write("</a> %\n");
/*      */                                     }
/* 4062 */                                     out.write("\n  </td>\n       </tr>\n       </table>");
/* 4063 */                                     out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4064 */                                     out.write("</td>\n      <td width=\"30%\"> ");
/* 4065 */                                     out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4066 */                                     out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                     
/* 4068 */                                     if (memvalue != null)
/*      */                                     {
/*      */ 
/* 4071 */                                       dialGraph.setValue(Long.parseLong(memvalue));
/* 4072 */                                       out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4073 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4074 */                                       out.write(45);
/* 4075 */                                       out.print(memvalue);
/* 4076 */                                       out.write(" %' >\n\n");
/*      */                                       
/* 4078 */                                       DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4079 */                                       _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 4080 */                                       _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4082 */                                       _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                       
/* 4084 */                                       _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                       
/* 4086 */                                       _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                       
/* 4088 */                                       _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                       
/* 4090 */                                       _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                       
/* 4092 */                                       _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                       
/* 4094 */                                       _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                       
/* 4096 */                                       _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                       
/* 4098 */                                       _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                       
/* 4100 */                                       _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 4101 */                                       int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 4102 */                                       if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 4103 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4104 */                                           out = _jspx_page_context.pushBody();
/* 4105 */                                           _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 4106 */                                           _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4109 */                                           out.write(32);
/* 4110 */                                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 4111 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4114 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4115 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4118 */                                       if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 4119 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                       }
/*      */                                       
/* 4122 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 4123 */                                       out.write(32);
/* 4124 */                                       out.write("\n            </td>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4128 */                                       out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4129 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4130 */                                       out.write(" >\n\n");
/* 4131 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4132 */                                       out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                     }
/* 4134 */                                     out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4135 */                                     if (memvalue != null)
/*      */                                     {
/* 4137 */                                       out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4138 */                                       out.print(hostid);
/* 4139 */                                       out.write("&attributeid=");
/* 4140 */                                       out.print(memid);
/* 4141 */                                       out.write("&period=-7')\" class='bodytextbold'>");
/* 4142 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4143 */                                       out.write(45);
/* 4144 */                                       out.print(memvalue);
/* 4145 */                                       out.write("</a> %\n  ");
/*      */                                     }
/* 4147 */                                     out.write("\n  </td>\n       </tr>\n    </table>");
/* 4148 */                                     out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4149 */                                     out.write("</td>\n      <td width=\"30%\">");
/* 4150 */                                     out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4151 */                                     out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                     
/*      */ 
/* 4154 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                     {
/*      */ 
/*      */ 
/* 4158 */                                       dialGraph.setValue(Long.parseLong(diskvalue));
/* 4159 */                                       out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4160 */                                       out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 4161 */                                       out.write(45);
/* 4162 */                                       out.print(diskvalue);
/* 4163 */                                       out.write("%' >\n");
/*      */                                       
/* 4165 */                                       DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4166 */                                       _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 4167 */                                       _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4169 */                                       _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                       
/* 4171 */                                       _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                       
/* 4173 */                                       _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                       
/* 4175 */                                       _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                       
/* 4177 */                                       _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                       
/* 4179 */                                       _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                       
/* 4181 */                                       _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                       
/* 4183 */                                       _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                       
/* 4185 */                                       _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                       
/* 4187 */                                       _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 4188 */                                       int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 4189 */                                       if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 4190 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 4191 */                                           out = _jspx_page_context.pushBody();
/* 4192 */                                           _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 4193 */                                           _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4196 */                                           out.write(32);
/* 4197 */                                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 4198 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4201 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 4202 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4205 */                                       if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 4206 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                       }
/*      */                                       
/* 4209 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 4210 */                                       out.write(32);
/* 4211 */                                       out.write(32);
/* 4212 */                                       out.write("\n    </td>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4216 */                                       out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4217 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4218 */                                       out.write(32);
/* 4219 */                                       out.write(62);
/* 4220 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4221 */                                       out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                     }
/* 4223 */                                     out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 4224 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                     {
/* 4226 */                                       out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4227 */                                       out.print(hostid);
/* 4228 */                                       out.write("&attributeid=");
/* 4229 */                                       out.print(diskid);
/* 4230 */                                       out.write("&period=-7')\" class='bodytextbold'>");
/* 4231 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 4232 */                                       out.write(45);
/* 4233 */                                       out.print(diskvalue);
/* 4234 */                                       out.write("</a> %\n     ");
/*      */                                     }
/* 4236 */                                     out.write("\n  </td>\n  </tr>\n</table>");
/* 4237 */                                     out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4238 */                                     out.write("</td></tr></table>\n\n");
/*      */                                   } else {
/* 4240 */                                     out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 4241 */                                     out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 4242 */                                     out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 4243 */                                     out.print(systeminfo.get("host_resid"));
/* 4244 */                                     out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 4245 */                                     out.print(hostname);
/* 4246 */                                     out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 4247 */                                     if (cpuvalue != null)
/*      */                                     {
/*      */ 
/* 4250 */                                       dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4251 */                                       out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                       
/* 4253 */                                       DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4254 */                                       _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 4255 */                                       _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4257 */                                       _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                       
/* 4259 */                                       _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                       
/* 4261 */                                       _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                       
/* 4263 */                                       _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                       
/* 4265 */                                       _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                       
/* 4267 */                                       _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                       
/* 4269 */                                       _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                       
/* 4271 */                                       _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                       
/* 4273 */                                       _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                       
/* 4275 */                                       _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 4276 */                                       int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 4277 */                                       if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 4278 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                       }
/*      */                                       
/* 4281 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 4282 */                                       out.write("\n         </td>\n     ");
/*      */                                     }
/*      */                                     else {
/* 4285 */                                       out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4286 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4287 */                                       out.write(39);
/* 4288 */                                       out.write(32);
/* 4289 */                                       out.write(62);
/* 4290 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4291 */                                       out.write("\n \t\t</td>\n\t\t");
/*      */                                     }
/* 4293 */                                     if (memvalue != null) {
/* 4294 */                                       dialGraph.setValue(Long.parseLong(memvalue));
/* 4295 */                                       out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                       
/* 4297 */                                       DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4298 */                                       _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 4299 */                                       _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4301 */                                       _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                       
/* 4303 */                                       _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                       
/* 4305 */                                       _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                       
/* 4307 */                                       _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                       
/* 4309 */                                       _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                       
/* 4311 */                                       _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                       
/* 4313 */                                       _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                       
/* 4315 */                                       _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                       
/* 4317 */                                       _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                       
/* 4319 */                                       _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 4320 */                                       int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 4321 */                                       if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 4322 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                       }
/*      */                                       
/* 4325 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 4326 */                                       out.write("\n            </td>\n         ");
/*      */                                     }
/*      */                                     else {
/* 4329 */                                       out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4330 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4331 */                                       out.write(39);
/* 4332 */                                       out.write(32);
/* 4333 */                                       out.write(62);
/* 4334 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4335 */                                       out.write("\n \t\t</td>\n\t\t");
/*      */                                     }
/* 4337 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 4338 */                                       dialGraph.setValue(Long.parseLong(diskvalue));
/* 4339 */                                       out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                       
/* 4341 */                                       DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4342 */                                       _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 4343 */                                       _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4345 */                                       _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                       
/* 4347 */                                       _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                       
/* 4349 */                                       _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                       
/* 4351 */                                       _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                       
/* 4353 */                                       _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                       
/* 4355 */                                       _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                       
/* 4357 */                                       _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                       
/* 4359 */                                       _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                       
/* 4361 */                                       _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                       
/* 4363 */                                       _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 4364 */                                       int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 4365 */                                       if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 4366 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                       }
/*      */                                       
/* 4369 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 4370 */                                       out.write(32);
/* 4371 */                                       out.write("\n\t          </td>\n\t  ");
/*      */                                     }
/*      */                                     else {
/* 4374 */                                       out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4375 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4376 */                                       out.write(39);
/* 4377 */                                       out.write(32);
/* 4378 */                                       out.write(62);
/* 4379 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4380 */                                       out.write("\n \t\t</td>\n\t\t");
/*      */                                     }
/* 4382 */                                     out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4383 */                                     out.print(hostid);
/* 4384 */                                     out.write("&attributeid=");
/* 4385 */                                     out.print(cpuid);
/* 4386 */                                     out.write("&period=-7')\" class='tooltip'>");
/* 4387 */                                     out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4388 */                                     out.write(32);
/* 4389 */                                     out.write(45);
/* 4390 */                                     out.write(32);
/* 4391 */                                     if (cpuvalue != null) {
/* 4392 */                                       out.print(cpuvalue);
/*      */                                     }
/* 4394 */                                     out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4395 */                                     out.print(hostid);
/* 4396 */                                     out.write("&attributeid=");
/* 4397 */                                     out.print(memid);
/* 4398 */                                     out.write("&period=-7')\" class='tooltip'>");
/* 4399 */                                     out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4400 */                                     out.write(45);
/* 4401 */                                     if (memvalue != null) {
/* 4402 */                                       out.print(memvalue);
/*      */                                     }
/* 4404 */                                     out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4405 */                                     out.print(hostid);
/* 4406 */                                     out.write("&attributeid=");
/* 4407 */                                     out.print(diskid);
/* 4408 */                                     out.write("&period=-7')\" class='tooltip'>");
/* 4409 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 4410 */                                     out.write(45);
/* 4411 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 4412 */                                       out.print(diskvalue);
/*      */                                     }
/* 4414 */                                     out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                   }
/* 4416 */                                   out.write(10);
/* 4417 */                                   out.write(10);
/*      */                                 }
/*      */                                 
/*      */                               }
/*      */                             }
/*      */                             catch (Exception e)
/*      */                             {
/* 4424 */                               e.printStackTrace();
/*      */                             }
/* 4426 */                             out.write(10);
/* 4427 */                             out.write(10);
/* 4428 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 4429 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4432 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4433 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4436 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4437 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 4440 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 4441 */                         out.write(10);
/* 4442 */                         if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 4444 */                         out.write(10);
/* 4445 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4446 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 4450 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4451 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 4454 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4455 */                       out.write("\n\n\n\n");
/*      */                     }
/* 4457 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4458 */         out = _jspx_out;
/* 4459 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4460 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4461 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4464 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4470 */     PageContext pageContext = _jspx_page_context;
/* 4471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4473 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4474 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4475 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 4477 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 4478 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4479 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4481 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 4482 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4483 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4487 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4488 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4489 */       return true;
/*      */     }
/* 4491 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4497 */     PageContext pageContext = _jspx_page_context;
/* 4498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4500 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4501 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 4502 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/* 4504 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 4505 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 4506 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 4508 */         out.write("\n\tvar poll=trimAll(document.HostResourceForm.pollinterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t    {\n\t\talert(\"");
/* 4509 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 4510 */           return true;
/* 4511 */         out.write("\");\n\t\treturn;\n\t}\n\tvar user=trimAll(document.HostResourceForm.username.value);\n\tif(user == '')\n\t\t{\n\t\t\talert(\"");
/* 4512 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 4513 */           return true;
/* 4514 */         out.write("\");\n\t\t\treturn;\n\t\t}\n\tif(trimAll(document.HostResourceForm.password.value) == '')\n\t{\n\t\talert(\"");
/* 4515 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 4516 */           return true;
/* 4517 */         out.write("\");\n\t\treturn;\n\t}\n\tvar db=trimAll(document.HostResourceForm.instance.value);\n\tif(db == '')\n\t\t{\n\t\t\talert(\"");
/* 4518 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 4519 */           return true;
/* 4520 */         out.write("\");\n\t\t\treturn;\n\t\t}\n\n        if(!checkForDisplayName(trimAll(document.HostResourceForm.displayname.value))) {\n            document.HostResourceForm.displayname.select();\n            return;\n        }\n\tdocument.HostResourceForm.submit();\n\t");
/* 4521 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 4522 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4526 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 4527 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4528 */       return true;
/*      */     }
/* 4530 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4531 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4536 */     PageContext pageContext = _jspx_page_context;
/* 4537 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4539 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4540 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4541 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 4542 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4543 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 4544 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4545 */         out = _jspx_page_context.pushBody();
/* 4546 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 4547 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4550 */         out.write("am.webclient.common.validpollinginterval.text");
/* 4551 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 4552 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4555 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4556 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4559 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4560 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4561 */       return true;
/*      */     }
/* 4563 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4564 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4569 */     PageContext pageContext = _jspx_page_context;
/* 4570 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4572 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4573 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4574 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 4575 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4576 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 4577 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4578 */         out = _jspx_page_context.pushBody();
/* 4579 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 4580 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4583 */         out.write("am.webclient.common.validusername.text");
/* 4584 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 4585 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4588 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4589 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4592 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4593 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4594 */       return true;
/*      */     }
/* 4596 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4602 */     PageContext pageContext = _jspx_page_context;
/* 4603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4605 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4606 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4607 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 4608 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4609 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 4610 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4611 */         out = _jspx_page_context.pushBody();
/* 4612 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 4613 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4616 */         out.write("am.webclient.db2.emptypassword");
/* 4617 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 4618 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4621 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4622 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4625 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4626 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4627 */       return true;
/*      */     }
/* 4629 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4635 */     PageContext pageContext = _jspx_page_context;
/* 4636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4638 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4639 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4640 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 4641 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4642 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 4643 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4644 */         out = _jspx_page_context.pushBody();
/* 4645 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 4646 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4649 */         out.write("am.webclient.db2.emptydatabase");
/* 4650 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 4651 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4654 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4655 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4658 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4659 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4660 */       return true;
/*      */     }
/* 4662 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4663 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4668 */     PageContext pageContext = _jspx_page_context;
/* 4669 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4671 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4672 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4673 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4675 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4677 */     _jspx_th_tiles_005fput_005f0.setValue("DB2 Server Details");
/* 4678 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4679 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4680 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4681 */       return true;
/*      */     }
/* 4683 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4684 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4689 */     PageContext pageContext = _jspx_page_context;
/* 4690 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4692 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 4693 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 4694 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4696 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 4697 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 4699 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 4700 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 4702 */           out.write(10);
/* 4703 */           out.write(9);
/* 4704 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 4705 */             return true;
/* 4706 */           out.write(10);
/* 4707 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 4708 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4712 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 4713 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4716 */         int tmp190_189 = 0; int[] tmp190_187 = _jspx_push_body_count_c_005fcatch_005f0; int tmp192_191 = tmp190_187[tmp190_189];tmp190_187[tmp190_189] = (tmp192_191 - 1); if (tmp192_191 <= 0) break;
/* 4717 */         out = _jspx_page_context.popBody(); }
/* 4718 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4720 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 4721 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 4723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 4728 */     PageContext pageContext = _jspx_page_context;
/* 4729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4731 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 4732 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 4733 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 4735 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 4737 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 4738 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 4739 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 4740 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4741 */       return true;
/*      */     }
/* 4743 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4749 */     PageContext pageContext = _jspx_page_context;
/* 4750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4752 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4753 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4754 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4756 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4757 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4758 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4760 */         out.write(10);
/* 4761 */         out.write(9);
/* 4762 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 4763 */           return true;
/* 4764 */         out.write(10);
/* 4765 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4766 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4770 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4771 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4772 */       return true;
/*      */     }
/* 4774 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4775 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4780 */     PageContext pageContext = _jspx_page_context;
/* 4781 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4783 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4784 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4785 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4787 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4789 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 4790 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4791 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4792 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4793 */       return true;
/*      */     }
/* 4795 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4796 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4801 */     PageContext pageContext = _jspx_page_context;
/* 4802 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4804 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4805 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4806 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4808 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4809 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4810 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4812 */         out.write(10);
/* 4813 */         out.write(9);
/* 4814 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4815 */           return true;
/* 4816 */         out.write(10);
/* 4817 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4818 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4822 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4823 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4824 */       return true;
/*      */     }
/* 4826 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4827 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4832 */     PageContext pageContext = _jspx_page_context;
/* 4833 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4835 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4836 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4837 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4839 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 4841 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 4842 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4843 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4844 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4845 */       return true;
/*      */     }
/* 4847 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4853 */     PageContext pageContext = _jspx_page_context;
/* 4854 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4856 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4857 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4858 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4860 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 4862 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/DB2LeftPage.jsp");
/* 4863 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4864 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4865 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4866 */       return true;
/*      */     }
/* 4868 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4874 */     PageContext pageContext = _jspx_page_context;
/* 4875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4877 */     org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
/* 4878 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 4879 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 4880 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 4881 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 4883 */         out.write(10);
/* 4884 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4885 */           return true;
/* 4886 */         out.write(10);
/* 4887 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4888 */           return true;
/* 4889 */         out.write(10);
/* 4890 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4891 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4895 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4896 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4897 */       return true;
/*      */     }
/* 4899 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4900 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4905 */     PageContext pageContext = _jspx_page_context;
/* 4906 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4908 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4909 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 4910 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 4912 */     _jspx_th_c_005fwhen_005f0.setTest("${!empty showconfigdiv && (showconfigdiv == 'true')}");
/* 4913 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 4914 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 4916 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: block\">\n");
/* 4917 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4918 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4922 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4923 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4924 */       return true;
/*      */     }
/* 4926 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4927 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4932 */     PageContext pageContext = _jspx_page_context;
/* 4933 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4935 */     org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
/* 4936 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 4937 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 4938 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 4939 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 4941 */         out.write("\n<div id=\"edit\" style=\"DISPLAY:none\">\n");
/* 4942 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4943 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4947 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4948 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4949 */       return true;
/*      */     }
/* 4951 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4957 */     PageContext pageContext = _jspx_page_context;
/* 4958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4960 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4961 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4962 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4964 */     _jspx_th_html_005ftext_005f0.setProperty("username");
/*      */     
/* 4966 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/* 4968 */     _jspx_th_html_005ftext_005f0.setSize("45");
/* 4969 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4970 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4971 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4972 */       return true;
/*      */     }
/* 4974 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4975 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4980 */     PageContext pageContext = _jspx_page_context;
/* 4981 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4983 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4984 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 4985 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4987 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 4989 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 4991 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/*      */     
/* 4993 */     _jspx_th_html_005fpassword_005f0.setValue("");
/* 4994 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 4995 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 4996 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4997 */       return true;
/*      */     }
/* 4999 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 5000 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5005 */     PageContext pageContext = _jspx_page_context;
/* 5006 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5008 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5009 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 5010 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5012 */     _jspx_th_html_005ftext_005f1.setProperty("instance");
/*      */     
/* 5014 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 5016 */     _jspx_th_html_005ftext_005f1.setSize("15");
/* 5017 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 5018 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 5019 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5020 */       return true;
/*      */     }
/* 5022 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5023 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5028 */     PageContext pageContext = _jspx_page_context;
/* 5029 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5031 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5032 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 5033 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5035 */     _jspx_th_html_005ftext_005f2.setProperty("displayname");
/*      */     
/* 5037 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 5039 */     _jspx_th_html_005ftext_005f2.setSize("45");
/* 5040 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 5041 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 5042 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5043 */       return true;
/*      */     }
/* 5045 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5051 */     PageContext pageContext = _jspx_page_context;
/* 5052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5054 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5055 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 5056 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5058 */     _jspx_th_html_005ftext_005f3.setProperty("pollinterval");
/*      */     
/* 5060 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 5062 */     _jspx_th_html_005ftext_005f3.setSize("5");
/* 5063 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 5064 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 5065 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5066 */       return true;
/*      */     }
/* 5068 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5074 */     PageContext pageContext = _jspx_page_context;
/* 5075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5077 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5078 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 5079 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5081 */     _jspx_th_c_005fif_005f6.setTest("${not empty param.haid}");
/* 5082 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 5083 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 5085 */         out.write(10);
/* 5086 */         out.write(9);
/* 5087 */         out.write(9);
/* 5088 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 5089 */           return true;
/* 5090 */         out.write(10);
/* 5091 */         out.write(9);
/* 5092 */         out.write(9);
/* 5093 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5094 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5098 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5099 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5100 */       return true;
/*      */     }
/* 5102 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5103 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5108 */     PageContext pageContext = _jspx_page_context;
/* 5109 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5111 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5112 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5113 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5115 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 5117 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 5118 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5119 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5120 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5121 */         out = _jspx_page_context.pushBody();
/* 5122 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5123 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5126 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5127 */           return true;
/* 5128 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5129 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5132 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5133 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5136 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5137 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5138 */       return true;
/*      */     }
/* 5140 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5146 */     PageContext pageContext = _jspx_page_context;
/* 5147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5149 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5150 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5151 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5153 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 5154 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5155 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5156 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5157 */       return true;
/*      */     }
/* 5159 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5165 */     PageContext pageContext = _jspx_page_context;
/* 5166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5168 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5169 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5170 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5172 */     _jspx_th_c_005fif_005f7.setTest("${not empty param.resourceid}");
/* 5173 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5174 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5176 */         out.write(10);
/* 5177 */         out.write(9);
/* 5178 */         out.write(9);
/* 5179 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 5180 */           return true;
/* 5181 */         out.write(10);
/* 5182 */         out.write(9);
/* 5183 */         out.write(9);
/* 5184 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5185 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5189 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5190 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5191 */       return true;
/*      */     }
/* 5193 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5194 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5199 */     PageContext pageContext = _jspx_page_context;
/* 5200 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5202 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5203 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5204 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5206 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 5208 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 5209 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5210 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 5211 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5212 */         out = _jspx_page_context.pushBody();
/* 5213 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 5214 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5217 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 5218 */           return true;
/* 5219 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 5220 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5223 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5224 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5227 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5228 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5229 */       return true;
/*      */     }
/* 5231 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5237 */     PageContext pageContext = _jspx_page_context;
/* 5238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5240 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5241 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5242 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 5244 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 5245 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5246 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5247 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5248 */       return true;
/*      */     }
/* 5250 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5256 */     PageContext pageContext = _jspx_page_context;
/* 5257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5259 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5260 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5261 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5263 */     _jspx_th_c_005fout_005f2.setValue("${myfield_paramresid}");
/* 5264 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5265 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5266 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5267 */       return true;
/*      */     }
/* 5269 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5270 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5275 */     PageContext pageContext = _jspx_page_context;
/* 5276 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5278 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5279 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 5280 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5282 */     _jspx_th_c_005fif_005f8.setTest("${not empty param.haid}");
/* 5283 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 5284 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 5286 */         out.write(10);
/* 5287 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 5288 */           return true;
/* 5289 */         out.write(10);
/* 5290 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 5291 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5295 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 5296 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 5297 */       return true;
/*      */     }
/* 5299 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 5300 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5305 */     PageContext pageContext = _jspx_page_context;
/* 5306 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5308 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5309 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5310 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 5312 */     _jspx_th_c_005fset_005f2.setVar("myfield_resid");
/*      */     
/* 5314 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 5315 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5316 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5317 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5318 */         out = _jspx_page_context.pushBody();
/* 5319 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5320 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5323 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5324 */           return true;
/* 5325 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5326 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5329 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5330 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5333 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5334 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5335 */       return true;
/*      */     }
/* 5337 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5343 */     PageContext pageContext = _jspx_page_context;
/* 5344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5346 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5347 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5348 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5350 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 5351 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5352 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5353 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5354 */       return true;
/*      */     }
/* 5356 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5357 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5362 */     PageContext pageContext = _jspx_page_context;
/* 5363 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5365 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5366 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 5367 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5369 */     _jspx_th_c_005fif_005f9.setTest("${not empty param.resourceid}");
/* 5370 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 5371 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 5373 */         out.write(10);
/* 5374 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 5375 */           return true;
/* 5376 */         out.write(10);
/* 5377 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 5378 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5382 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 5383 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5384 */       return true;
/*      */     }
/* 5386 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5387 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5392 */     PageContext pageContext = _jspx_page_context;
/* 5393 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5395 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5396 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5397 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 5399 */     _jspx_th_c_005fset_005f3.setVar("myfield_resid");
/*      */     
/* 5401 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 5402 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5403 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 5404 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5405 */         out = _jspx_page_context.pushBody();
/* 5406 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 5407 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5410 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 5411 */           return true;
/* 5412 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 5413 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5416 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5417 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5420 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5421 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5422 */       return true;
/*      */     }
/* 5424 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5425 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5430 */     PageContext pageContext = _jspx_page_context;
/* 5431 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5433 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5434 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5435 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 5437 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 5438 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5439 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5440 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5441 */       return true;
/*      */     }
/* 5443 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5444 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5449 */     PageContext pageContext = _jspx_page_context;
/* 5450 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5452 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5453 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 5454 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5456 */     _jspx_th_c_005fset_005f4.setVar("trstripclass");
/*      */     
/* 5458 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 5459 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 5460 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 5461 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5462 */         out = _jspx_page_context.pushBody();
/* 5463 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 5464 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5467 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 5468 */           return true;
/* 5469 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 5470 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5473 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5474 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5477 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 5478 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 5479 */       return true;
/*      */     }
/* 5481 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 5482 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5487 */     PageContext pageContext = _jspx_page_context;
/* 5488 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5490 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5491 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5492 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 5494 */     _jspx_th_c_005fout_005f5.setValue("");
/* 5495 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5496 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5497 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5498 */       return true;
/*      */     }
/* 5500 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5501 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5506 */     PageContext pageContext = _jspx_page_context;
/* 5507 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5509 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5510 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 5511 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5513 */     _jspx_th_c_005fset_005f5.setVar("myfield_entity");
/*      */     
/* 5515 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 5516 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 5517 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 5518 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 5519 */         out = _jspx_page_context.pushBody();
/* 5520 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 5521 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5524 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 5525 */           return true;
/* 5526 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 5527 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5530 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 5531 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5534 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 5535 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 5536 */       return true;
/*      */     }
/* 5538 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 5539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5544 */     PageContext pageContext = _jspx_page_context;
/* 5545 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5547 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5548 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5549 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 5551 */     _jspx_th_c_005fout_005f6.setValue("noalarms");
/* 5552 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5553 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5554 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5555 */       return true;
/*      */     }
/* 5557 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5558 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5563 */     PageContext pageContext = _jspx_page_context;
/* 5564 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5566 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5567 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 5568 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5570 */     _jspx_th_c_005fif_005f10.setTest("${not empty param.entity}");
/* 5571 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 5572 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 5574 */         out.write(10);
/* 5575 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 5576 */           return true;
/* 5577 */         out.write(10);
/* 5578 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 5579 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5583 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 5584 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 5585 */       return true;
/*      */     }
/* 5587 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 5588 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5593 */     PageContext pageContext = _jspx_page_context;
/* 5594 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5596 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5597 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 5598 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5600 */     _jspx_th_c_005fset_005f6.setVar("myfield_entity");
/*      */     
/* 5602 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 5603 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 5604 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 5605 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5606 */         out = _jspx_page_context.pushBody();
/* 5607 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 5608 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5611 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 5612 */           return true;
/* 5613 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 5614 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5617 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5618 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5621 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 5622 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 5623 */       return true;
/*      */     }
/* 5625 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 5626 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5631 */     PageContext pageContext = _jspx_page_context;
/* 5632 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5634 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5635 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5636 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 5638 */     _jspx_th_c_005fout_005f7.setValue("${param.entity}");
/* 5639 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5640 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5641 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5642 */       return true;
/*      */     }
/* 5644 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5650 */     PageContext pageContext = _jspx_page_context;
/* 5651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5653 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5654 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 5655 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5657 */     _jspx_th_c_005fif_005f11.setTest("${not empty param.includeClass}");
/* 5658 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 5659 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 5661 */         out.write(10);
/* 5662 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 5663 */           return true;
/* 5664 */         out.write(10);
/* 5665 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 5666 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5670 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 5671 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 5672 */       return true;
/*      */     }
/* 5674 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 5675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5680 */     PageContext pageContext = _jspx_page_context;
/* 5681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5683 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5684 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 5685 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 5687 */     _jspx_th_c_005fset_005f7.setVar("trstripclass");
/*      */     
/* 5689 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 5690 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 5691 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 5692 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 5693 */         out = _jspx_page_context.pushBody();
/* 5694 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 5695 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5698 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 5699 */           return true;
/* 5700 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 5701 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5704 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 5705 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5708 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 5709 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 5710 */       return true;
/*      */     }
/* 5712 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 5713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5718 */     PageContext pageContext = _jspx_page_context;
/* 5719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5721 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5722 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5723 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 5725 */     _jspx_th_c_005fout_005f8.setValue("${param.includeClass}");
/* 5726 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5727 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5728 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5729 */       return true;
/*      */     }
/* 5731 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5737 */     PageContext pageContext = _jspx_page_context;
/* 5738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5740 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5741 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5742 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5744 */     _jspx_th_c_005fout_005f9.setValue("${trstripclass}");
/* 5745 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5746 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5747 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5748 */       return true;
/*      */     }
/* 5750 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5756 */     PageContext pageContext = _jspx_page_context;
/* 5757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5759 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5760 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5761 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5762 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5763 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 5764 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 5765 */         out = _jspx_page_context.pushBody();
/* 5766 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 5767 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5770 */         out.write("am.myfield.customfield.text");
/* 5771 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 5772 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5775 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 5776 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5779 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 5780 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5781 */       return true;
/*      */     }
/* 5783 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5784 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5789 */     PageContext pageContext = _jspx_page_context;
/* 5790 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5792 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5793 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5794 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5796 */     _jspx_th_c_005fout_005f10.setValue("${myfield_resid}");
/* 5797 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5798 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5799 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5800 */       return true;
/*      */     }
/* 5802 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5803 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5808 */     PageContext pageContext = _jspx_page_context;
/* 5809 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5811 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5812 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5813 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5815 */     _jspx_th_c_005fout_005f11.setValue("${myfield_entity}");
/* 5816 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5817 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5818 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5819 */       return true;
/*      */     }
/* 5821 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5822 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5827 */     PageContext pageContext = _jspx_page_context;
/* 5828 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5830 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5831 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5832 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5834 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 5835 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5836 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5837 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5838 */       return true;
/*      */     }
/* 5840 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5841 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5846 */     PageContext pageContext = _jspx_page_context;
/* 5847 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5849 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5850 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5851 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5853 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 5854 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5855 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5856 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5857 */       return true;
/*      */     }
/* 5859 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5860 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5865 */     PageContext pageContext = _jspx_page_context;
/* 5866 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5868 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5869 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5870 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5872 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 5873 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5874 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5875 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5876 */       return true;
/*      */     }
/* 5878 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5879 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5884 */     PageContext pageContext = _jspx_page_context;
/* 5885 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5887 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5888 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5889 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5891 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 5892 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5893 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5894 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5895 */       return true;
/*      */     }
/* 5897 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5898 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5903 */     PageContext pageContext = _jspx_page_context;
/* 5904 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5906 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5907 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 5908 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f13);
/* 5909 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 5910 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 5911 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5912 */         out = _jspx_page_context.pushBody();
/* 5913 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 5914 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5917 */         out.write("table.heading.attribute");
/* 5918 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 5919 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5922 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5923 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5926 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 5927 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5928 */       return true;
/*      */     }
/* 5930 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5931 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5936 */     PageContext pageContext = _jspx_page_context;
/* 5937 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5939 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5940 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 5941 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f13);
/* 5942 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 5943 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 5944 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5945 */         out = _jspx_page_context.pushBody();
/* 5946 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 5947 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5950 */         out.write("table.heading.value");
/* 5951 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 5952 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5955 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5956 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5959 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 5960 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5961 */       return true;
/*      */     }
/* 5963 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5969 */     PageContext pageContext = _jspx_page_context;
/* 5970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5972 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5973 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 5974 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f13);
/* 5975 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 5976 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 5977 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5978 */         out = _jspx_page_context.pushBody();
/* 5979 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 5980 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5983 */         out.write("table.heading.status");
/* 5984 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 5985 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5988 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5989 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5992 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 5993 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5994 */       return true;
/*      */     }
/* 5996 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6002 */     PageContext pageContext = _jspx_page_context;
/* 6003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6005 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6006 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 6007 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6008 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 6009 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 6010 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6011 */         out = _jspx_page_context.pushBody();
/* 6012 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 6013 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6016 */         out.write("table.heading.attribute");
/* 6017 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 6018 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6021 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6022 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6025 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 6026 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6027 */       return true;
/*      */     }
/* 6029 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6030 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6035 */     PageContext pageContext = _jspx_page_context;
/* 6036 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6038 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6039 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 6040 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6041 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 6042 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 6043 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6044 */         out = _jspx_page_context.pushBody();
/* 6045 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 6046 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6049 */         out.write("table.heading.value");
/* 6050 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 6051 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6054 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6055 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6058 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 6059 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6060 */       return true;
/*      */     }
/* 6062 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6063 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6068 */     PageContext pageContext = _jspx_page_context;
/* 6069 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6071 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6072 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 6073 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6074 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 6075 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 6076 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6077 */         out = _jspx_page_context.pushBody();
/* 6078 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 6079 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6082 */         out.write("table.heading.status");
/* 6083 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 6084 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6087 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6088 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6091 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 6092 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6093 */       return true;
/*      */     }
/* 6095 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6096 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6101 */     PageContext pageContext = _jspx_page_context;
/* 6102 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6104 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6105 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 6106 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6107 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 6108 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 6109 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6110 */         out = _jspx_page_context.pushBody();
/* 6111 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 6112 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6115 */         out.write("table.heading.attribute");
/* 6116 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 6117 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6120 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6121 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6124 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 6125 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6126 */       return true;
/*      */     }
/* 6128 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6129 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6134 */     PageContext pageContext = _jspx_page_context;
/* 6135 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6137 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6138 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 6139 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6140 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 6141 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 6142 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6143 */         out = _jspx_page_context.pushBody();
/* 6144 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 6145 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6148 */         out.write("table.heading.value");
/* 6149 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 6150 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6153 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6154 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6157 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 6158 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6159 */       return true;
/*      */     }
/* 6161 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6167 */     PageContext pageContext = _jspx_page_context;
/* 6168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6170 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6171 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 6172 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6173 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 6174 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 6175 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6176 */         out = _jspx_page_context.pushBody();
/* 6177 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 6178 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6181 */         out.write("table.heading.status");
/* 6182 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 6183 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6186 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6187 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6190 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 6191 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6192 */       return true;
/*      */     }
/* 6194 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6195 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6200 */     PageContext pageContext = _jspx_page_context;
/* 6201 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6203 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6204 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 6205 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6206 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 6207 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 6208 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 6209 */         out = _jspx_page_context.pushBody();
/* 6210 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 6211 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6214 */         out.write("table.heading.attribute");
/* 6215 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 6216 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6219 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 6220 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6223 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 6224 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 6225 */       return true;
/*      */     }
/* 6227 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 6228 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6233 */     PageContext pageContext = _jspx_page_context;
/* 6234 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6236 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6237 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 6238 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6239 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 6240 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 6241 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 6242 */         out = _jspx_page_context.pushBody();
/* 6243 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 6244 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6247 */         out.write("table.heading.value");
/* 6248 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 6249 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6252 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 6253 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6256 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 6257 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 6258 */       return true;
/*      */     }
/* 6260 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 6261 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6266 */     PageContext pageContext = _jspx_page_context;
/* 6267 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6269 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6270 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 6271 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6272 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 6273 */     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 6274 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 6275 */         out = _jspx_page_context.pushBody();
/* 6276 */         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 6277 */         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6280 */         out.write("table.heading.status");
/* 6281 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 6282 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6285 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 6286 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6289 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 6290 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 6291 */       return true;
/*      */     }
/* 6293 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 6294 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6299 */     PageContext pageContext = _jspx_page_context;
/* 6300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6302 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6303 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 6304 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6305 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 6306 */     if (_jspx_eval_fmt_005fmessage_005f17 != 0) {
/* 6307 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 6308 */         out = _jspx_page_context.pushBody();
/* 6309 */         _jspx_th_fmt_005fmessage_005f17.setBodyContent((BodyContent)out);
/* 6310 */         _jspx_th_fmt_005fmessage_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6313 */         out.write("table.heading.attribute");
/* 6314 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f17.doAfterBody();
/* 6315 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6318 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 6319 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6322 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 6323 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 6324 */       return true;
/*      */     }
/* 6326 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 6327 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6332 */     PageContext pageContext = _jspx_page_context;
/* 6333 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6335 */     MessageTag _jspx_th_fmt_005fmessage_005f18 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6336 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 6337 */     _jspx_th_fmt_005fmessage_005f18.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6338 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 6339 */     if (_jspx_eval_fmt_005fmessage_005f18 != 0) {
/* 6340 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 6341 */         out = _jspx_page_context.pushBody();
/* 6342 */         _jspx_th_fmt_005fmessage_005f18.setBodyContent((BodyContent)out);
/* 6343 */         _jspx_th_fmt_005fmessage_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6346 */         out.write("table.heading.value");
/* 6347 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f18.doAfterBody();
/* 6348 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6351 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 6352 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6355 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 6356 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 6357 */       return true;
/*      */     }
/* 6359 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 6360 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6365 */     PageContext pageContext = _jspx_page_context;
/* 6366 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6368 */     MessageTag _jspx_th_fmt_005fmessage_005f19 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6369 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 6370 */     _jspx_th_fmt_005fmessage_005f19.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6371 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 6372 */     if (_jspx_eval_fmt_005fmessage_005f19 != 0) {
/* 6373 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 6374 */         out = _jspx_page_context.pushBody();
/* 6375 */         _jspx_th_fmt_005fmessage_005f19.setBodyContent((BodyContent)out);
/* 6376 */         _jspx_th_fmt_005fmessage_005f19.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6379 */         out.write("table.heading.status");
/* 6380 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f19.doAfterBody();
/* 6381 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6384 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 6385 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6388 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 6389 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 6390 */       return true;
/*      */     }
/* 6392 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 6393 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6398 */     PageContext pageContext = _jspx_page_context;
/* 6399 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6401 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6402 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 6403 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6405 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 6407 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 6408 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 6409 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 6410 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6411 */       return true;
/*      */     }
/* 6413 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6414 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DB2Details_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */