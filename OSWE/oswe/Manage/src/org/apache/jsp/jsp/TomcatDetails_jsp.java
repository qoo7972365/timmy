/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.server.tomcat.bean.TomcatMemoryBean;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.StackBarChart;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.net.URLEncoder;
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
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class TomcatDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   71 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session, HttpServletRequest request)
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
/*  721 */     java.net.InetAddress add = null;
/*  722 */     if (ip.equals("127.0.0.1")) {
/*  723 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  727 */       add = java.net.InetAddress.getByName(ip);
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
/*  928 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
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
/* 1476 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/*      */           catch (java.sql.SQLException e) {
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
/* 1709 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
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
/* 1826 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1828 */               if (maxCol != null)
/* 1829 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1831 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1826 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 1987 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/* 2150 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2151 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
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
/* 2180 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2186 */   private static Map<String, Long> _jspx_dependants = new HashMap(7);
/* 2187 */   static { _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2189 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2190 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2191 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2229 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2233 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2262 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2266 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2270 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2271 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2272 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2274 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2275 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.release();
/* 2276 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2277 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2278 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2279 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2280 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2281 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2282 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2283 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2284 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2286 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2287 */     this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.release();
/* 2288 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2289 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2290 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdataSetProducer.release();
/* 2291 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2292 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2293 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2300 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/* 2303 */     JspWriter out = null;
/* 2304 */     Object page = this;
/* 2305 */     JspWriter _jspx_out = null;
/* 2306 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2310 */       response.setContentType("text/html;charset=UTF-8");
/* 2311 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2313 */       _jspx_page_context = pageContext;
/* 2314 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2315 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2316 */       session = pageContext.getSession();
/* 2317 */       out = pageContext.getOut();
/* 2318 */       _jspx_out = out;
/*      */       
/* 2320 */       out.write("<!DOCTYPE html>\n");
/* 2321 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n");
/*      */       
/* 2323 */       request.setAttribute("HelpKey", "Monitors Tomcat Details");
/*      */       
/* 2325 */       out.write(10);
/* 2326 */       TomcatMemoryBean membean = null;
/* 2327 */       membean = (TomcatMemoryBean)_jspx_page_context.getAttribute("membean", 2);
/* 2328 */       if (membean == null) {
/* 2329 */         membean = new TomcatMemoryBean();
/* 2330 */         _jspx_page_context.setAttribute("membean", membean, 2);
/*      */       }
/* 2332 */       out.write(10);
/* 2333 */       com.adventnet.appmanager.server.tomcat.bean.TomcatPerformanceBean perfbean = null;
/* 2334 */       perfbean = (com.adventnet.appmanager.server.tomcat.bean.TomcatPerformanceBean)_jspx_page_context.getAttribute("perfbean", 2);
/* 2335 */       if (perfbean == null) {
/* 2336 */         perfbean = new com.adventnet.appmanager.server.tomcat.bean.TomcatPerformanceBean();
/* 2337 */         _jspx_page_context.setAttribute("perfbean", perfbean, 2);
/*      */       }
/* 2339 */       out.write(10);
/* 2340 */       com.adventnet.appmanager.server.tomcat.bean.TomcatResponseBean resbean = null;
/* 2341 */       resbean = (com.adventnet.appmanager.server.tomcat.bean.TomcatResponseBean)_jspx_page_context.getAttribute("resbean", 2);
/* 2342 */       if (resbean == null) {
/* 2343 */         resbean = new com.adventnet.appmanager.server.tomcat.bean.TomcatResponseBean();
/* 2344 */         _jspx_page_context.setAttribute("resbean", resbean, 2);
/*      */       }
/* 2346 */       out.write(10);
/* 2347 */       com.adventnet.appmanager.server.tomcat.bean.TomcatThreadBean threadbean = null;
/* 2348 */       threadbean = (com.adventnet.appmanager.server.tomcat.bean.TomcatThreadBean)_jspx_page_context.getAttribute("threadbean", 2);
/* 2349 */       if (threadbean == null) {
/* 2350 */         threadbean = new com.adventnet.appmanager.server.tomcat.bean.TomcatThreadBean();
/* 2351 */         _jspx_page_context.setAttribute("threadbean", threadbean, 2);
/*      */       }
/* 2353 */       out.write(10);
/* 2354 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2355 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2356 */       if (wlsGraph == null) {
/* 2357 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2358 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2360 */       out.write(10);
/* 2361 */       com.adventnet.appmanager.bean.PerformanceBean perfgraph = null;
/* 2362 */       perfgraph = (com.adventnet.appmanager.bean.PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2363 */       if (perfgraph == null) {
/* 2364 */         perfgraph = new com.adventnet.appmanager.bean.PerformanceBean();
/* 2365 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2367 */       out.write("\n\n\n\n\n\n\n\n");
/* 2368 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2370 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2371 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2372 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2374 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2376 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2378 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2380 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2381 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2382 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2383 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2386 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2387 */         String available = null;
/* 2388 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2389 */         out.write(10);
/*      */         
/* 2391 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2392 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2393 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2395 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2397 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2399 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2401 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2402 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2403 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2404 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2407 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2408 */           String unavailable = null;
/* 2409 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2410 */           out.write(10);
/*      */           
/* 2412 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2413 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2414 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2416 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2418 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2420 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2422 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2423 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2424 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2425 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2428 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2429 */             String unmanaged = null;
/* 2430 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2431 */             out.write(10);
/*      */             
/* 2433 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2434 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2435 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2437 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2439 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2441 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2443 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2444 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2445 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2446 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2449 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2450 */               String scheduled = null;
/* 2451 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2452 */               out.write(10);
/*      */               
/* 2454 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2455 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2456 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2458 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2460 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2462 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2464 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2465 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2466 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2467 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2470 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2471 */                 String critical = null;
/* 2472 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2473 */                 out.write(10);
/*      */                 
/* 2475 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2476 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2477 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2479 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2481 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2483 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2485 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2486 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2487 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2488 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2491 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2492 */                   String clear = null;
/* 2493 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2494 */                   out.write(10);
/*      */                   
/* 2496 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2497 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2498 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2500 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2502 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2504 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2506 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2507 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2508 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2509 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2512 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2513 */                     String warning = null;
/* 2514 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2515 */                     out.write(10);
/* 2516 */                     out.write(10);
/*      */                     
/* 2518 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2519 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2521 */                     out.write(10);
/* 2522 */                     out.write(10);
/* 2523 */                     out.write(10);
/* 2524 */                     out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2525 */                     if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */                       return;
/* 2527 */                     out.write(10);
/*      */                     try {
/* 2529 */                       String tipDB = FormatUtil.getString("am.webclient.tomcat.tooltip");
/* 2530 */                       String tipAvgRespTime = FormatUtil.getString("am.webclient.tomcat.restime.tooltip");
/*      */                       
/* 2532 */                       out.write(10);
/* 2533 */                       out.write(10);
/*      */                       
/* 2535 */                       String name = null;
/* 2536 */                       float downtime = 0.0F;
/*      */                       
/*      */ 
/* 2539 */                       java.util.Vector list = new java.util.Vector();
/* 2540 */                       name = (String)request.getAttribute("name");
/* 2541 */                       String haid = null;
/* 2542 */                       String appname = null;
/* 2543 */                       String search = null;
/* 2544 */                       String tab = "1";
/* 2545 */                       String resourceid = request.getParameter("resourceid");
/* 2546 */                       haid = (String)request.getAttribute("haid");
/* 2547 */                       String version = (String)request.getAttribute("version");
/* 2548 */                       request.setAttribute("version", version);
/* 2549 */                       appname = (String)request.getAttribute("appName");
/* 2550 */                       String displayname = null;
/*      */                       
/* 2552 */                       if (request.getParameter("configure") == null)
/*      */                       {
/* 2554 */                         displayname = request.getParameter("resourcename");
/*      */                       }
/* 2556 */                       else if ((request.getParameter("configure") != null) && (request.getParameter("configure").equals("true")))
/*      */                       {
/* 2558 */                         displayname = (String)request.getAttribute("displayname");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 2563 */                         displayname = request.getParameter("resourcename");
/*      */                       }
/*      */                       
/* 2566 */                       String redirect = (String)request.getAttribute("redirect");
/*      */                       
/* 2568 */                       String encodeurl = null;
/* 2569 */                       if (haid == null)
/*      */                       {
/* 2571 */                         search = "<a href='network_content.jsp' class='arial10'>Monitors</a> &nbsp;<img src='../images/icon_arrow.gif'> &nbsp; <a href='networkdetails.jsp?network=Tomcat-server&appname=null&haid=null' class='arial10'>Tomcat-server</a> &nbsp;<img src='../images/icon_arrow.gif'> &nbsp;" + name + "";
/*      */                       }
/*      */                       else
/*      */                       {
/* 2575 */                         search = "<a href='applications.jsp' class='arial10'>Applications</a> &nbsp;<img src='../images/icon_arrow.gif'> &nbsp;<a href='showapplication.jsp?haid=" + haid + "' class='arial10'>" + appname + "</a> &nbsp;<img src='../images/icon_arrow.gif'>&nbsp;" + name + "";
/* 2576 */                         tab = "0";
/*      */                       }
/*      */                       
/* 2579 */                       request.setAttribute("breadcumps", search);
/* 2580 */                       request.setAttribute("tabtoselect", tab);
/* 2581 */                       request.setAttribute("configured", "true");
/* 2582 */                       Hashtable contextdetails = (Hashtable)request.getAttribute("contextdetails");
/* 2583 */                       Hashtable sessiondet = (Hashtable)request.getAttribute("tomcatsessiondet");
/* 2584 */                       ArrayList attribIDs = new ArrayList();
/* 2585 */                       ArrayList resIDs = new ArrayList();
/* 2586 */                       for (int i = 1; i < 17; i++)
/*      */                       {
/* 2588 */                         attribIDs.add("" + i);
/*      */                       }
/* 2590 */                       attribIDs.add("22");
/* 2591 */                       attribIDs.add("23");
/* 2592 */                       attribIDs.add("30");
/* 2593 */                       attribIDs.add("31");
/* 2594 */                       attribIDs.add("33");
/* 2595 */                       attribIDs.add("34");
/* 2596 */                       attribIDs.add("35");
/* 2597 */                       resIDs.add(resourceid);
/* 2598 */                       for (Enumeration e = contextdetails.keys(); e.hasMoreElements();)
/*      */                       {
/* 2600 */                         String key = (String)e.nextElement();
/* 2601 */                         Properties idprops = (Properties)contextdetails.get(key);
/* 2602 */                         resIDs.add(idprops.getProperty("ID"));
/*      */                       }
/* 2604 */                       Properties alert = getStatus(resIDs, attribIDs);
/* 2605 */                       wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2606 */                       HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */                       
/* 2608 */                       Properties tomcatdetails = (Properties)request.getAttribute("tomcatdetails");
/*      */                       
/*      */ 
/*      */ 
/* 2612 */                       out.write(10);
/* 2613 */                       out.write(9);
/* 2614 */                       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */                         return;
/* 2616 */                       out.write(10);
/* 2617 */                       out.write(9);
/*      */                       
/* 2619 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2620 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2621 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2623 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2624 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2625 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2627 */                           out.write(32);
/* 2628 */                           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2630 */                           out.write(10);
/* 2631 */                           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2633 */                           out.write(10);
/* 2634 */                           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2636 */                           out.write(10);
/* 2637 */                           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2639 */                           out.write(10);
/*      */                           
/* 2641 */                           PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2642 */                           _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2643 */                           _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2645 */                           _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                           
/* 2647 */                           _jspx_th_tiles_005fput_005f4.setType("string");
/* 2648 */                           int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2649 */                           if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2650 */                             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2651 */                               out = _jspx_page_context.pushBody();
/* 2652 */                               _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 2653 */                               _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2656 */                               out.write(" <!--span class=\"message\">");
/* 2657 */                               if (_jspx_meth_html_005ferrors_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 2659 */                               out.write("</span-->\n");
/*      */                               
/* 2661 */                               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2662 */                               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2663 */                               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2665 */                               _jspx_th_c_005fif_005f2.setTest("${ !empty error}");
/* 2666 */                               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2667 */                               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                 for (;;) {
/* 2669 */                                   out.write(" <span class=\"message\" >");
/* 2670 */                                   out.print((String)request.getAttribute("error"));
/* 2671 */                                   out.write("</span>\n");
/* 2672 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2673 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2677 */                               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2678 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                               }
/*      */                               
/* 2681 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2682 */                               out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */                               
/* 2684 */                               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2685 */                               String aid = request.getParameter("haid");
/* 2686 */                               String haName = null;
/* 2687 */                               if (aid != null)
/*      */                               {
/* 2689 */                                 haName = (String)ht.get(aid);
/*      */                               }
/*      */                               
/* 2692 */                               out.write(10);
/* 2693 */                               out.write(9);
/*      */                               
/* 2695 */                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2696 */                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2697 */                               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2699 */                               _jspx_th_c_005fif_005f3.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2700 */                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2701 */                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                 for (;;) {
/* 2703 */                                   out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2704 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2705 */                                   out.write(" &gt; ");
/* 2706 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(aid, haName));
/* 2707 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 2708 */                                   out.print(displayname);
/* 2709 */                                   out.write(" </span></td>\n\t");
/* 2710 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2711 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2715 */                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2716 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                               }
/*      */                               
/* 2719 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2720 */                               out.write(10);
/* 2721 */                               out.write(9);
/*      */                               
/* 2723 */                               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2724 */                               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2725 */                               _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2727 */                               _jspx_th_c_005fif_005f4.setTest("${!empty param.haid && (!empty invalidhaid)}");
/* 2728 */                               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2729 */                               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                 for (;;) {
/* 2731 */                                   out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2732 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2733 */                                   out.write(" &gt; ");
/* 2734 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("Tomcat-server"));
/* 2735 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 2736 */                                   out.print(displayname);
/* 2737 */                                   out.write(" </span></td>\n\t");
/* 2738 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2739 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2743 */                               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2744 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                               }
/*      */                               
/* 2747 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2748 */                               out.write("\n    </tr>\n</table>\n\n\n");
/*      */                               
/*      */ 
/* 2751 */                               encodeurl = URLEncoder.encode(redirect);
/*      */                               
/* 2753 */                               out.write(10);
/* 2754 */                               out.write(10);
/* 2755 */                               if (_jspx_meth_c_005fif_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 2757 */                               out.write(10);
/* 2758 */                               if (_jspx_meth_c_005fif_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 2760 */                               out.write("\n\n\n\n");
/*      */                               
/* 2762 */                               org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
/* 2763 */                               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2764 */                               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/* 2765 */                               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2766 */                               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                 for (;;) {
/* 2768 */                                   out.write(10);
/*      */                                   
/* 2770 */                                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2771 */                                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2772 */                                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                   
/* 2774 */                                   _jspx_th_c_005fwhen_005f0.setTest("${ param.alert!='true' && param.all!='true' }");
/* 2775 */                                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2776 */                                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                     for (;;) {
/* 2778 */                                       out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"60%\" valign=\"top\">\n\t<table width=\"96%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n          <td colspan=\"2\" align=\"left\" class=\"tableheadingbborder\">");
/* 2779 */                                       out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2780 */                                       out.write("</td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t  <td width=\"30%\" class=\"monitorinfoodd\">");
/* 2781 */                                       out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2782 */                                       out.write("</td>\n\t\t  <td width=\"70%\" class=\"monitorinfoodd\" title=\"");
/* 2783 */                                       out.print(displayname);
/* 2784 */                                       out.write(34);
/* 2785 */                                       out.write(62);
/* 2786 */                                       out.print(getTrimmedText(displayname, 40));
/* 2787 */                                       out.write("</td>\n\t\t  </tr>\n\t\t  ");
/* 2788 */                                       out.write("<!--$Id$-->\n");
/*      */                                       
/* 2790 */                                       String hostName = "localhost";
/*      */                                       try {
/* 2792 */                                         hostName = java.net.InetAddress.getLocalHost().getHostName();
/*      */                                       } catch (Exception ex) {
/* 2794 */                                         ex.printStackTrace();
/*      */                                       }
/* 2796 */                                       String portNumber = System.getProperty("webserver.port");
/* 2797 */                                       String styleClass = "monitorinfoodd";
/* 2798 */                                       if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2799 */                                         styleClass = "whitegrayborder-conf-mon";
/*      */                                       }
/*      */                                       
/* 2802 */                                       out.write(10);
/*      */                                       
/* 2804 */                                       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2805 */                                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2806 */                                       _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                       
/* 2808 */                                       _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2809 */                                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2810 */                                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                         for (;;) {
/* 2812 */                                           out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2813 */                                           out.print(styleClass);
/* 2814 */                                           out.write(34);
/* 2815 */                                           out.write(62);
/* 2816 */                                           out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2817 */                                           out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2818 */                                           out.print(styleClass);
/* 2819 */                                           out.write(34);
/* 2820 */                                           out.write(62);
/* 2821 */                                           out.print(hostName);
/* 2822 */                                           out.write(95);
/* 2823 */                                           out.print(portNumber);
/* 2824 */                                           out.write("</td>\n</tr>\n");
/* 2825 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2826 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2830 */                                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2831 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                       }
/*      */                                       
/* 2834 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2835 */                                       out.write(10);
/* 2836 */                                       out.write("\n\t\t  <tr>\n\t\t  <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2837 */                                       out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2838 */                                       out.write("</td>\n\t\t  <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2839 */                                       out.print(resourceid);
/* 2840 */                                       out.write("&attributeid=16')\">");
/* 2841 */                                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "16")));
/* 2842 */                                       out.write("</a>\n\t\t   ");
/* 2843 */                                       out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "16" + "#" + "MESSAGE"), "16", alert.getProperty(resourceid + "#" + "16"), resourceid));
/* 2844 */                                       out.write("\n\t\t   ");
/* 2845 */                                       if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "16") != 0) {
/* 2846 */                                         out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2847 */                                         out.print(resourceid + "_16");
/* 2848 */                                         out.write("&monitortype=Tomcat-server')\">");
/* 2849 */                                         out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2850 */                                         out.write("</a></span>\n           ");
/*      */                                       }
/* 2852 */                                       out.write("\n\t\t  </td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2853 */                                       out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2854 */                                       out.write(" </td>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2855 */                                       out.print(FormatUtil.getString("am.webclient.tomacatdetail.server"));
/* 2856 */                                       out.write("</td>\n\t\t  </tr>\n        ");
/*      */                                       
/*      */ 
/*      */ 
/* 2860 */                                       if (tomcatdetails.size() != 0)
/*      */                                       {
/*      */ 
/* 2863 */                                         out.write("\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 2864 */                                         out.print(FormatUtil.getString("am.webclient.tomacatdetail.version"));
/* 2865 */                                         out.write("</td>\n          <td  class=\"monitorinfoodd\">");
/* 2866 */                                         out.print(tomcatdetails.getProperty("TOMCATVERSION"));
/* 2867 */                                         out.write("</td>\n        </tr>\n        <tr>\n          <td  height=\"21\" class=\"monitorinfoeven\">");
/* 2868 */                                         out.print(FormatUtil.getString("am.webclient.tomacatdetail.jvmversion"));
/* 2869 */                                         out.write("</td>\n          <td  height=\"21\" class=\"monitorinfoeven\">");
/* 2870 */                                         out.print(tomcatdetails.getProperty("JAVAVERSION"));
/* 2871 */                                         out.write("</td>\n        </tr>\n        <tr>\n          <td  height=\"21\" class=\"monitorinfoodd\">");
/* 2872 */                                         out.print(FormatUtil.getString("am.webclient.tomacatdetail.jvmvendor"));
/* 2873 */                                         out.write("</td>\n          <td  height=\"21\" class=\"monitorinfoodd\">");
/* 2874 */                                         out.print(tomcatdetails.getProperty("JVMVENDOR"));
/* 2875 */                                         out.write("</td>\n        </tr>\n        ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 2879 */                                       out.write(10);
/* 2880 */                                       out.write(9);
/* 2881 */                                       out.write(9);
/*      */                                       
/* 2883 */                                       EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2884 */                                       _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2885 */                                       _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                       
/* 2887 */                                       _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2888 */                                       int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2889 */                                       if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                                         for (;;) {
/* 2891 */                                           out.write("\n\t\t<tr>\n\t\t<td class=\"monitorinfoeven\">");
/* 2892 */                                           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2893 */                                           out.write("</td>\n\t\t<td class=\"monitorinfoeven\">-&nbsp;</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td class=\"monitorinfoodd\">");
/* 2894 */                                           out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2895 */                                           out.write("</td>\n\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td class=\"monitorinfoeven\">");
/* 2896 */                                           out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2897 */                                           out.write("</td>\n\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td class=\"monitorinfoodd\">");
/* 2898 */                                           out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2899 */                                           out.write("</td>\n\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t</tr>\n\t\t");
/* 2900 */                                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2901 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2905 */                                       if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2906 */                                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                                       }
/*      */                                       
/* 2909 */                                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2910 */                                       out.write(10);
/* 2911 */                                       out.write(9);
/* 2912 */                                       out.write(9);
/*      */                                       
/* 2914 */                                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2915 */                                       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2916 */                                       _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                       
/* 2918 */                                       _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 2919 */                                       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2920 */                                       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                         for (;;) {
/* 2922 */                                           out.write("\n\t\t<tr>\n\t\t<td class=\"monitorinfoeven\">");
/* 2923 */                                           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2924 */                                           out.write("</td>\n\t\t");
/*      */                                           
/* 2926 */                                           if (systeminfo.get("host_resid") != null)
/*      */                                           {
/* 2928 */                                             out.write("\n\t\t    <td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 2929 */                                             out.print(systeminfo.get("host_resid"));
/* 2930 */                                             out.write("&method=showResourceForResourceID\" class=\"staticlinks\" title=\"");
/* 2931 */                                             out.print(systeminfo.get("HOSTNAME"));
/* 2932 */                                             out.write(34);
/* 2933 */                                             out.write(32);
/* 2934 */                                             out.write(62);
/* 2935 */                                             out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2936 */                                             out.write("&nbsp;(");
/* 2937 */                                             out.print(systeminfo.get("HOSTIP"));
/* 2938 */                                             out.write(")</a></td>\n\t\t\t");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2943 */                                             out.write("\n             <td class=\"monitorinfoeven\" title=\"");
/* 2944 */                                             out.print(systeminfo.get("HOSTNAME"));
/* 2945 */                                             out.write(34);
/* 2946 */                                             out.write(32);
/* 2947 */                                             out.write(62);
/* 2948 */                                             out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2949 */                                             out.write("&nbsp;(");
/* 2950 */                                             out.print(systeminfo.get("HOSTIP"));
/* 2951 */                                             out.write(")</td>\n\t\t\t");
/*      */                                           }
/* 2953 */                                           out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t<td class=\"monitorinfoodd\">");
/* 2954 */                                           out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2955 */                                           out.write("</td>\n\n\t\t");
/* 2956 */                                           if ((tomcatdetails.getProperty("OSNAME") != null) && (!tomcatdetails.getProperty("OSNAME").equalsIgnoreCase("null"))) {
/* 2957 */                                             out.write("\n\n\n\t\t\t<td class=\"monitorinfoodd\">");
/* 2958 */                                             out.print(FormatUtil.getString(tomcatdetails.getProperty("OSNAME")));
/* 2959 */                                             out.write("</td>\n\n\t\t\t\t");
/*      */                                           } else {
/* 2961 */                                             out.write("\n\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2962 */                                             out.print(FormatUtil.getString("Unknown"));
/* 2963 */                                             out.write("</td>\n\n\n\t\t\t\t\t\t");
/*      */                                           }
/* 2965 */                                           out.write("\n\t\t</tr>\n\t\t");
/*      */                                           
/* 2967 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2968 */                                           _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2969 */                                           _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                           
/* 2971 */                                           _jspx_th_logic_005fnotEmpty_005f1.setName("recent5Alarms");
/* 2972 */                                           int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2973 */                                           if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                             for (;;) {
/* 2975 */                                               out.write(10);
/* 2976 */                                               out.write(9);
/* 2977 */                                               out.write(9);
/*      */                                               
/* 2979 */                                               ArrayList recent = (ArrayList)((ArrayList)request.getAttribute("recent5Alarms")).get(0);
/*      */                                               
/* 2981 */                                               out.write("\n\t\t<tr>\n\t\t<td class=\"monitorinfoeven\">");
/* 2982 */                                               out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 2983 */                                               out.write("</td>\n\t\t<td class=\"monitorinfoeven\"><a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2984 */                                               out.print(recent.get(2));
/* 2985 */                                               out.write("&source=");
/* 2986 */                                               out.print(recent.get(4));
/* 2987 */                                               out.write("&category=");
/* 2988 */                                               out.print(recent.get(0));
/* 2989 */                                               out.write("&redirectto=");
/* 2990 */                                               out.print(encodeurl);
/* 2991 */                                               out.write("\" class=\"resourcename\">");
/* 2992 */                                               out.print(getTruncatedAlertMessage((String)recent.get(3)));
/* 2993 */                                               out.write("</a></td>\n\t\t</tr>\n\t\t");
/* 2994 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2995 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2999 */                                           if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3000 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                           }
/*      */                                           
/* 3003 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3004 */                                           out.write(10);
/* 3005 */                                           out.write(9);
/* 3006 */                                           out.write(9);
/*      */                                           
/* 3008 */                                           EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3009 */                                           _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3010 */                                           _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                           
/* 3012 */                                           _jspx_th_logic_005fempty_005f1.setName("recent5Alarms");
/* 3013 */                                           int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3014 */                                           if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                                             for (;;) {
/* 3016 */                                               out.write("\n\t\t<tr>\n\t\t<td class=\"monitorinfoeven\">");
/* 3017 */                                               out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 3018 */                                               out.write("</td>\n\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t</tr>\n\t\t");
/* 3019 */                                               int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3020 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3024 */                                           if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3025 */                                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                                           }
/*      */                                           
/* 3028 */                                           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3029 */                                           out.write("\n\t\t<tr>\n\t\t<td class=\"monitorinfoodd\">");
/* 3030 */                                           out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3031 */                                           out.write("</td>\n\t\t<td class=\"monitorinfoodd\">");
/* 3032 */                                           out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3033 */                                           out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td class=\"monitorinfoeven\">");
/* 3034 */                                           out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3035 */                                           out.write("</td>\n\t\t<td class=\"monitorinfoeven\">");
/* 3036 */                                           out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3037 */                                           out.write("</td>\n\t\t</tr>\n\t\t");
/* 3038 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3039 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3043 */                                       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3044 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                       }
/*      */                                       
/* 3047 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3048 */                                       out.write(10);
/* 3049 */                                       out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 3050 */                                       out.write("\n\t{\n\t\t");
/* 3051 */                                       if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3053 */                                       out.write(10);
/* 3054 */                                       out.write(9);
/* 3055 */                                       out.write(9);
/* 3056 */                                       if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3058 */                                       out.write("\n\t\tgetCustomFields('");
/* 3059 */                                       if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3061 */                                       out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 3062 */                                       out.write("\n\t}\n\n});\n</script>\n");
/* 3063 */                                       if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3065 */                                       out.write(10);
/* 3066 */                                       out.write(10);
/* 3067 */                                       if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3069 */                                       out.write(10);
/* 3070 */                                       out.write(10);
/* 3071 */                                       if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3073 */                                       out.write(10);
/* 3074 */                                       if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3076 */                                       out.write(10);
/* 3077 */                                       out.write(10);
/* 3078 */                                       out.write(10);
/* 3079 */                                       if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3081 */                                       out.write(10);
/* 3082 */                                       out.write(10);
/* 3083 */                                       out.write(10);
/* 3084 */                                       if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3086 */                                       out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 3087 */                                       if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3089 */                                       out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 3090 */                                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3092 */                                       out.write("\" onclick=\"getCustomFields('");
/* 3093 */                                       if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3095 */                                       out.write(39);
/* 3096 */                                       out.write(44);
/* 3097 */                                       out.write(39);
/* 3098 */                                       if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3100 */                                       out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 3101 */                                       out.write("\n</td>\n</tr>\n\n\n");
/* 3102 */                                       out.write("\n      </table>\n                ");
/*      */                                       
/* 3104 */                                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3105 */                                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3106 */                                       _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                       
/* 3108 */                                       _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3109 */                                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3110 */                                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                         for (;;) {
/* 3112 */                                           out.write("\n\t  \t");
/*      */                                           
/* 3114 */                                           IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3115 */                                           _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3116 */                                           _jspx_th_c_005fif_005f13.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                                           
/* 3118 */                                           _jspx_th_c_005fif_005f13.setTest("${showdata=='1'}");
/* 3119 */                                           int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3120 */                                           if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                             for (;;) {
/* 3122 */                                               out.write("\n\n\t\t<div align=\"center\"><a style=cursor:pointer;><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"javascript:toggleDiv('edit')\">\n\n            <tr>\n              <td>&nbsp;</td>\n            </tr>\n            <tr>\n              <td><table width=\"75%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"getmoredatatable\">\n                  <tr>\n                    <td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img src=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\" border=\"0\" vspace=\"2\" hspace=\"5\"></td>\n                    <td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 3123 */                                               out.print(FormatUtil.getString("am.webclient.configureimage.appservers.text"));
/* 3124 */                                               out.write("</td>\n                  </tr>\n                </table></td>\n            </tr>\n      </table></a></div>\n\n\t\t");
/* 3125 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3126 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3130 */                                           if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3131 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                           }
/*      */                                           
/* 3134 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3135 */                                           out.write(10);
/* 3136 */                                           out.write(9);
/* 3137 */                                           out.write(9);
/* 3138 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3139 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3143 */                                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3144 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                       }
/*      */                                       
/* 3147 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3148 */                                       out.write("\n    </td>\n    <td valign=\"top\">\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n          <td class=\"tableheadingbborder\">");
/* 3149 */                                       out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3150 */                                       out.write(" <a name=\"Availability\" id=\"Availability\"></a>&nbsp;&nbsp;</td>\n        </tr>\n        <tr>\n          <td align=\"right\" >\n<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3151 */                                       if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3153 */                                       out.write("&period=1&resourcename=");
/* 3154 */                                       if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3156 */                                       out.write("')\">\n                  <img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3157 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3158 */                                       out.write("\"></td>\n                <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3159 */                                       if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3161 */                                       out.write("&period=2&resourcename=");
/* 3162 */                                       if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 3164 */                                       out.write("')\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3165 */                                       out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3166 */                                       out.write("\"></td>\n              </tr>\n            </table>\n          </td>\n        </tr>\n        <tr>\n          <td height=\"21\" align=\"center\">");
/*      */                                       
/* 3168 */                                       AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3169 */                                       _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3170 */                                       _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                       
/* 3172 */                                       _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                                       
/* 3174 */                                       _jspx_th_awolf_005fpiechart_005f0.setWidth("300");
/*      */                                       
/* 3176 */                                       _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */                                       
/* 3178 */                                       _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                                       
/* 3180 */                                       _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                                       
/* 3182 */                                       _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                                       
/* 3184 */                                       _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3185 */                                       int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3186 */                                       if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3187 */                                         if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3188 */                                           out = _jspx_page_context.pushBody();
/* 3189 */                                           _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3190 */                                           _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3193 */                                           out.write("\n            ");
/*      */                                           
/* 3195 */                                           Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3196 */                                           _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3197 */                                           _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                           
/* 3199 */                                           _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3200 */                                           int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3201 */                                           if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3202 */                                             if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3203 */                                               out = _jspx_page_context.pushBody();
/* 3204 */                                               _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3205 */                                               _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3208 */                                               out.write(32);
/*      */                                               
/* 3210 */                                               AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3211 */                                               _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3212 */                                               _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                               
/* 3214 */                                               _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                               
/* 3216 */                                               _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3217 */                                               int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3218 */                                               if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3219 */                                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                               }
/*      */                                               
/* 3222 */                                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3223 */                                               out.write("\n            ");
/*      */                                               
/* 3225 */                                               AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3226 */                                               _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3227 */                                               _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                               
/* 3229 */                                               _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                               
/* 3231 */                                               _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3232 */                                               int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3233 */                                               if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3234 */                                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                               }
/*      */                                               
/* 3237 */                                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3238 */                                               out.write(32);
/* 3239 */                                               int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3240 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3243 */                                             if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3244 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3247 */                                           if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3248 */                                             this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                           }
/*      */                                           
/* 3251 */                                           this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3252 */                                           out.write(32);
/* 3253 */                                           int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3254 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3257 */                                         if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3258 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3261 */                                       if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3262 */                                         this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                                       }
/*      */                                       
/* 3265 */                                       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3266 */                                       out.write("\n          </td>\n        </tr>\n        <tr>\n          <td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tbody>\n                <tr>\n\t\t\t  <td width=\"38%\" height=\"25\" class=\"bodytext\" colspan=\"2\">&nbsp;");
/* 3267 */                                       out.print(FormatUtil.getString("am.webclient.tomacatdetail.currentstatus"));
/* 3268 */                                       out.write(" <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3269 */                                       out.print((String)request.getAttribute("resourceid"));
/* 3270 */                                       out.write("&attributeid=15')\">");
/* 3271 */                                       out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "15")));
/* 3272 */                                       out.write("</a> </td>\n\n\t\t\t  <td width=\"50%\" class=\"bodytext\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3273 */                                       out.print(resourceid);
/* 3274 */                                       out.write("&attributeIDs=15,16&attributeToSelect=15&redirectto=");
/* 3275 */                                       out.print(encodeurl);
/* 3276 */                                       out.write("#Availability\" class=\"staticlinks\">");
/* 3277 */                                       out.print(ALERTCONFIG_TEXT);
/* 3278 */                                       out.write("</a>&nbsp;</td>\n                </tr>\n              </tbody>\n            </table></td>\n        </tr>\n      </table>\n    </td>\n  </tr>\n</table>\n <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3279 */                                       out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 3280 */                                       out.write("</td></tr></table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n  </table>\n  ");
/*      */                                       
/* 3282 */                                       IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3283 */                                       _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3284 */                                       _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                       
/* 3286 */                                       _jspx_th_c_005fif_005f14.setTest("${showdata=='1' || version=='3'}");
/* 3287 */                                       int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3288 */                                       if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                         for (;;) {
/* 3290 */                                           out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3291 */                                           out.print(FormatUtil.getString("am.webclient.hostResource.servers.response"));
/* 3292 */                                           out.write("&nbsp;</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n  <td width=\"405\" height=\"127\" valign=\"top\">\n  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n  <tr>\n  <td width=\"90%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3293 */                                           if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                             return;
/* 3295 */                                           out.write("&attributeid=13&period=-7&resourcename=");
/* 3296 */                                           if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                             return;
/* 3298 */                                           out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"0\" vspace=\"5\" border=\"0\"  title=\"");
/* 3299 */                                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3300 */                                           out.write("\"></a></td>\n  <td width=\"10%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3301 */                                           if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                             return;
/* 3303 */                                           out.write("&attributeid=13&period=-30&resourcename=");
/* 3304 */                                           if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                             return;
/* 3306 */                                           out.write("',740,550)\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"15\" vspace=\"5\" border=\"0\" title=\"");
/* 3307 */                                           out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3308 */                                           out.write("\"></a></td>\n  </tr>\n  <tr>\n  <td colspan=\"2\">\n  ");
/*      */                                           
/* 3310 */                                           perfgraph.setresourceid(Integer.parseInt(resourceid));
/* 3311 */                                           perfgraph.setEntity("Response Time");
/*      */                                           
/* 3313 */                                           out.write(10);
/* 3314 */                                           out.write(32);
/* 3315 */                                           out.write(32);
/*      */                                           
/* 3317 */                                           TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3318 */                                           _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3319 */                                           _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f14);
/*      */                                           
/* 3321 */                                           _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("perfgraph");
/*      */                                           
/* 3323 */                                           _jspx_th_awolf_005ftimechart_005f0.setWidth("300");
/*      */                                           
/* 3325 */                                           _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                                           
/* 3327 */                                           _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */                                           
/* 3329 */                                           _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                                           
/* 3331 */                                           _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.responsetimeinms"));
/*      */                                           
/* 3333 */                                           _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 3334 */                                           int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3335 */                                           if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3336 */                                             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3337 */                                               out = _jspx_page_context.pushBody();
/* 3338 */                                               _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3339 */                                               _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3342 */                                               out.write(10);
/* 3343 */                                               out.write(32);
/* 3344 */                                               out.write(32);
/* 3345 */                                               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3346 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3349 */                                             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3350 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3353 */                                           if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3354 */                                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                           }
/*      */                                           
/* 3357 */                                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3358 */                                           out.write("\n  </tr>\n  </table></td>\n  <td width=\"562\" valign=\"top\"> <br> <br>\n  <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n  <tr>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3359 */                                           if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                             return;
/* 3361 */                                           out.write("</span></td>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3362 */                                           if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                             return;
/* 3364 */                                           out.write("</span></td>\n  <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3365 */                                           if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                             return;
/* 3367 */                                           out.write("</span></td>\n  </tr>\n  <td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3368 */                                           out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3369 */                                           out.write(" </td>\n  <td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n\t");
/*      */                                           
/* 3371 */                                           if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) != -1L)
/*      */                                           {
/*      */ 
/* 3374 */                                             out.write(10);
/* 3375 */                                             out.write(9);
/* 3376 */                                             out.write(9);
/* 3377 */                                             out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 3378 */                                             out.write("&nbsp;");
/* 3379 */                                             out.print(FormatUtil.getString("ms"));
/* 3380 */                                             out.write(10);
/* 3381 */                                             out.write(9);
/* 3382 */                                             out.write(9);
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/*      */ 
/* 3388 */                                             out.write("\n\t\t-\n\t\t");
/*      */                                           }
/*      */                                           
/*      */ 
/* 3392 */                                           out.write("\n</td>\n<td class=\"whitegrayborder\">\n<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3393 */                                           out.print((String)request.getAttribute("resourceid"));
/* 3394 */                                           out.write("&attributeid=13')\">");
/* 3395 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "13")));
/* 3396 */                                           out.write("</a></td>\n</tr>\n<tr >\n<td  colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\">\n<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\"\n>&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3397 */                                           out.print(resourceid);
/* 3398 */                                           out.write("&attributeIDs=13,14,12&attributeToSelect=13&redirectto=");
/* 3399 */                                           out.print(encodeurl);
/* 3400 */                                           out.write("\" class=\"staticlinks\">");
/* 3401 */                                           out.print(FormatUtil.getString("am.webclient.tomacatdetail.alertconfigure"));
/* 3402 */                                           out.write("</a>&nbsp;&nbsp;</td>\n</tr>\n</table></td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n<tr>\n<td height=\"26\" class=\"tablebottom1\">&nbsp;</td>\n</tr>\n</table>\n");
/* 3403 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3404 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3408 */                                       if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3409 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                       }
/*      */                                       
/* 3412 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3413 */                                       out.write(10);
/*      */                                       
/* 3415 */                                       IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3416 */                                       _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3417 */                                       _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                       
/* 3419 */                                       _jspx_th_c_005fif_005f15.setTest("${showdata!='1'}");
/* 3420 */                                       int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3421 */                                       if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                         for (;;) {
/* 3423 */                                           out.write(10);
/* 3424 */                                           out.write(10);
/*      */                                           
/* 3426 */                                           if (!version.equals("3"))
/*      */                                           {
/* 3428 */                                             out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"51%\" height=\"31\" class=\"tableheadingtrans\"><a name=\"Server Performance\" id=\"Server Performance\"></a>");
/* 3429 */                                             out.print(FormatUtil.getString("am.webclient.tomacatdetail.perflastonehour"));
/* 3430 */                                             out.write("</td>\n    <td width=\"49%\" height=\"31\" class=\"tableheadingtrans\">");
/* 3431 */                                             out.print(FormatUtil.getString("am.webclient.tomacatdetail.memlastonehour"));
/* 3432 */                                             out.write("<a name=\"MemoryUsage\" id=\"Memory Usage\"></a></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrborder\">\n  <tr>\n    <td width=\"51%\" class=\"rbborder\"><table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td width=\"90%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3433 */                                             if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3435 */                                             out.write("&attributeid=13&period=-7&resourcename=");
/* 3436 */                                             if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3438 */                                             out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"0\" vspace=\"5\" border=\"0\"  title=\"");
/* 3439 */                                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3440 */                                             out.write("\"></a></td>\n                <td width=\"10%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3441 */                                             if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3443 */                                             out.write("&attributeid=13&period=-30&resourcename=");
/* 3444 */                                             if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3446 */                                             out.write("',740,550)\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"15\" vspace=\"5\" border=\"0\" title=\"");
/* 3447 */                                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3448 */                                             out.write("\"></a></td>\n              </tr>\n            </table></td>\n        </tr>\n        <tr>\n          <td>\n            ");
/*      */                                             
/*      */ 
/* 3451 */                                             encodeurl = URLEncoder.encode(redirect + "");
/* 3452 */                                             perfbean.setresourceID(Integer.parseInt(resourceid));
/* 3453 */                                             String xaxis_time = FormatUtil.getString("webclient.performance.reports.index.transmittraffic.xaxisname");
/* 3454 */                                             String yaxis_value_ms = FormatUtil.getString("am.webclient.mssqldetails.valueinms");
/*      */                                             
/* 3456 */                                             out.write(32);
/*      */                                             
/* 3458 */                                             TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3459 */                                             _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3460 */                                             _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fif_005f15);
/*      */                                             
/* 3462 */                                             _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("perfbean");
/*      */                                             
/* 3464 */                                             _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                                             
/* 3466 */                                             _jspx_th_awolf_005ftimechart_005f1.setHeight("130");
/*      */                                             
/* 3468 */                                             _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                                             
/* 3470 */                                             _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                                             
/* 3472 */                                             _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(yaxis_value_ms);
/* 3473 */                                             int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3474 */                                             if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3475 */                                               if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3476 */                                                 out = _jspx_page_context.pushBody();
/* 3477 */                                                 _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3478 */                                                 _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3481 */                                                 out.write("\n      ");
/* 3482 */                                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3483 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3486 */                                               if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3487 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3490 */                                             if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3491 */                                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                             }
/*      */                                             
/* 3494 */                                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3495 */                                             out.write("\n          </td>\n        </tr>\n      </table></td>\n    <td width=\"49%\" class=\"bottomborder\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td width=\"90%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3496 */                                             if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3498 */                                             out.write("&attributeid=11&period=-7&resourcename=");
/* 3499 */                                             if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3501 */                                             out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"0\" vspace=\"5\" border=\"0\"  title=\"");
/* 3502 */                                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3503 */                                             out.write("\"></td>\n                <td width=\"10%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3504 */                                             if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3506 */                                             out.write("&attributeid=11&period=-30&resourcename=");
/* 3507 */                                             if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3509 */                                             out.write("',740,550)\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"15\" vspace=\"5\" border=\"0\" title=\"");
/* 3510 */                                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3511 */                                             out.write("\"></td>\n              </tr>\n            </table></td>\n        </tr>\n        <tr>\n          <td>    ");
/*      */                                             
/* 3513 */                                             encodeurl = URLEncoder.encode(redirect);
/* 3514 */                                             membean.setresourceID(Integer.parseInt(resourceid));
/*      */                                             
/* 3516 */                                             out.write("\n      ");
/*      */                                             
/* 3518 */                                             TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3519 */                                             _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3520 */                                             _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                             
/* 3522 */                                             _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("membean");
/*      */                                             
/* 3524 */                                             _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */                                             
/* 3526 */                                             _jspx_th_awolf_005ftimechart_005f2.setHeight("120");
/*      */                                             
/* 3528 */                                             _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                                             
/* 3530 */                                             _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                             
/* 3532 */                                             _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.valueinkb"));
/* 3533 */                                             int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3534 */                                             if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3535 */                                               if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3536 */                                                 out = _jspx_page_context.pushBody();
/* 3537 */                                                 _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3538 */                                                 _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3541 */                                                 out.write("\n      ");
/* 3542 */                                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3543 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3546 */                                               if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3547 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3550 */                                             if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3551 */                                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                                             }
/*      */                                             
/* 3554 */                                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3555 */                                             out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"51%\" height=\"230\" valign=\"top\" class=\"rborder\">\n      ");
/*      */                                             
/* 3557 */                                             Properties p = perfbean.getProperties(Integer.parseInt(resourceid));
/*      */                                             
/* 3559 */                                             out.write("\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n        <tbody>\n          <tr>\n            <td height=\"25\" class=\"columnheadingb\" title=\"");
/* 3560 */                                             out.print(tipAvgRespTime);
/* 3561 */                                             out.write("\"><span class=\"bodytextbold\">");
/* 3562 */                                             if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3564 */                                             out.write("</span></td>");
/* 3565 */                                             out.write("\n            <td height=\"25\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3566 */                                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3568 */                                             out.write("</span></td>");
/* 3569 */                                             out.write("\n            <td height=\"25\" class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 3570 */                                             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3572 */                                             out.write("</span></td>");
/* 3573 */                                             out.write("\n          </tr>\n          <tr>\n            <td width=\"57%\" height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3574 */                                             out.print(tipAvgRespTime);
/* 3575 */                                             out.write(34);
/* 3576 */                                             out.write(62);
/* 3577 */                                             out.print(FormatUtil.getString("am.webclient.tomacatdetail.avgresponse"));
/* 3578 */                                             out.write("</td>\n            <td width=\"20%\" height=\"25\" class=\"whitegrayborder\">\n              ");
/*      */                                             
/* 3580 */                                             if (p.containsKey("AVGRESPONSETIME"))
/*      */                                             {
/*      */ 
/* 3583 */                                               out.write("\n              ");
/* 3584 */                                               out.print(formatNumber(p.getProperty("AVGRESPONSETIME")));
/* 3585 */                                               out.write("&nbsp;");
/* 3586 */                                               out.print(FormatUtil.getString("ms"));
/* 3587 */                                               out.write("\n              ");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/*      */ 
/* 3593 */                                               out.write("\n              -\n              ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 3597 */                                             out.write("\n            </td>\n            <td width=\"23%\" height=\"25\" class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3598 */                                             out.print((String)request.getAttribute("resourceid"));
/* 3599 */                                             out.write("&attributeid=13')\">");
/* 3600 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "13")));
/* 3601 */                                             out.write("</a></td>\n          </tr>\n          <tr>\n            <td height=\"27\" width=\"57%\"  class=\"yellowgrayborder\">");
/* 3602 */                                             out.print(FormatUtil.getString("Request per Minute"));
/* 3603 */                                             out.write("<br> </td>\n            <td height=\"27\" width=\"20%\" class=\"yellowgrayborder\" >\n              ");
/*      */                                             
/* 3605 */                                             if (p.containsKey("REQUESTPERSECOND"))
/*      */                                             {
/*      */ 
/* 3608 */                                               out.write("\n              ");
/* 3609 */                                               out.print(formatNumber(p.getProperty("REQUESTPERSECOND")));
/* 3610 */                                               out.write("\n              ");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/*      */ 
/* 3616 */                                               out.write("\n              -\n              ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 3620 */                                             out.write("\n              <br> </td>\n            <td width=\"23%\" height=\"37\" class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3621 */                                             out.print((String)request.getAttribute("resourceid"));
/* 3622 */                                             out.write("&attributeid=14')\">");
/* 3623 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "14")));
/* 3624 */                                             out.write("</a>&nbsp;</td>\n          </tr>\n          <tr>\n            <td width=\"57%\" height=\"20\"  class=\"whitegrayborder\">");
/* 3625 */                                             out.print(FormatUtil.getString("Average Bytes Per Minute"));
/* 3626 */                                             out.write("</td>\n            <td width=\"20%\" height=\"20\" class=\"whitegrayborder\" >\n              ");
/*      */                                             
/* 3628 */                                             if (p.containsKey("AVGBYTESPERSECOND"))
/*      */                                             {
/*      */ 
/* 3631 */                                               out.write("\n              ");
/* 3632 */                                               out.print(formatNumber(p.getProperty("AVGBYTESPERSECOND")));
/* 3633 */                                               out.write("<br>\n              ");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/*      */ 
/* 3639 */                                               out.write("\n              -\n              ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 3643 */                                             out.write("\n            </td>\n            <td width=\"23%\" height=\"20\" class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3644 */                                             out.print((String)request.getAttribute("resourceid"));
/* 3645 */                                             out.write("&attributeid=12')\">");
/* 3646 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "12")));
/* 3647 */                                             out.write("</a>&nbsp;</td>\n          </tr>\n          <tr>\n            <td height=\"20\" colspan=\"3\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3648 */                                             out.print(resourceid);
/* 3649 */                                             out.write("&attributeIDs=13,14,12&attributeToSelect=13&redirectto=");
/* 3650 */                                             out.print(encodeurl);
/* 3651 */                                             out.write("\" class=\"staticlinks\">");
/* 3652 */                                             out.print(ALERTCONFIG_TEXT);
/* 3653 */                                             out.write("</a>&nbsp;&nbsp;</td>\n          </tr>\n        </tbody>\n      </table>\n       </td>\n\n    <td width=\"49%\" align=\"center\" valign=\"top\">\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tbody>\n          <tr>\n            <td height=\"25\" class=\"columnheadingb\" title=\"");
/* 3654 */                                             out.print(tipAvgRespTime);
/* 3655 */                                             out.write("\"><span class=\"bodytextbold\">");
/* 3656 */                                             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3658 */                                             out.write("</span></td>");
/* 3659 */                                             out.write("\n            <td height=\"25\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3660 */                                             if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3662 */                                             out.write("</span></td>");
/* 3663 */                                             out.write("\n            <td height=\"25\" class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 3664 */                                             if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3666 */                                             out.write("</span></td>");
/* 3667 */                                             out.write("\n          </tr>\n          <tr>\n            <td width=\"40%\" height=\"20\" class=\"whitegrayborder\">");
/* 3668 */                                             out.print(FormatUtil.getString("Total Memory"));
/* 3669 */                                             out.write("\n            </td>\n            <td width=\"40%\" height=\"20\" class=\"whitegrayborder\">&nbsp;");
/* 3670 */                                             out.print(formatNumber(membean.gettotal() / 1024L));
/* 3671 */                                             out.write("\n              ");
/* 3672 */                                             out.print(FormatUtil.getString("KB"));
/* 3673 */                                             out.write("<br> </td>\n            <td width=\"20%\" height=\"20\" class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3674 */                                             out.print((String)request.getAttribute("resourceid"));
/* 3675 */                                             out.write("&attributeid=9')\">");
/* 3676 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "9")));
/* 3677 */                                             out.write("</a></td>\n          </tr>\n          <tr>\n            <td width=\"40%\" class=\"yellowgrayborder\">");
/* 3678 */                                             out.print(FormatUtil.getString("Used Memory"));
/* 3679 */                                             out.write("</td>\n            <td  width=\"40%\" class=\"yellowgrayborder\" >&nbsp;");
/* 3680 */                                             out.print(formatNumber(membean.getused() / 1024L));
/* 3681 */                                             out.write("\n              ");
/* 3682 */                                             out.print(FormatUtil.getString("KB"));
/* 3683 */                                             out.write("<br> </td>\n            <td width=\"20%\" class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3684 */                                             out.print((String)request.getAttribute("resourceid"));
/* 3685 */                                             out.write("&attributeid=11')\">");
/* 3686 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "11")));
/* 3687 */                                             out.write("</a></td>\n          </tr>\n          <tr>\n            <td width=\"40%\" height=\"20\" class=\"whitegrayborder\">");
/* 3688 */                                             out.print(FormatUtil.getString("Free Memory"));
/* 3689 */                                             out.write("\n            </td>\n            <td width=\"40%\" height=\"20\" class=\"whitegrayborder\">&nbsp;");
/* 3690 */                                             out.print(formatNumber(membean.getfree() / 1024L));
/* 3691 */                                             out.write("\n              ");
/* 3692 */                                             out.print(FormatUtil.getString("KB"));
/* 3693 */                                             out.write("<br> </td>\n            <td width=\"20%\" height=\"20\" class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3694 */                                             out.print((String)request.getAttribute("resourceid"));
/* 3695 */                                             out.write("&attributeid=10')\">");
/* 3696 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "10")));
/* 3697 */                                             out.write("</a></td>\n          </tr>\n          <tr>\n\t                 <td height=\"20\" colspan=\"3\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3698 */                                             out.print(resourceid);
/* 3699 */                                             out.write("&attributeIDs=9,11,10&attributeToSelect=9&redirectto=");
/* 3700 */                                             out.print(encodeurl);
/* 3701 */                                             out.write("\" class=\"staticlinks\">");
/* 3702 */                                             out.print(ALERTCONFIG_TEXT);
/* 3703 */                                             out.write("</a>&nbsp;&nbsp;</td>\n\t                 </tr>\n\n        </tbody>\n      </table> </td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 3707 */                                           out.write(10);
/*      */                                           
/* 3709 */                                           if (version.equals("3"))
/*      */                                           {
/*      */ 
/* 3712 */                                             out.write("\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\n  <tr>\n    <td width=\"72%\" height=\"31\" class=\"tableheadingtrans\">");
/* 3713 */                                             out.print(FormatUtil.getString("am.webclient.tomacatdetail.memlastonehour"));
/* 3714 */                                             out.write("<a name=\"MemoryUsage\" id=\"Memory Usage\"></a></td>\n    <td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3715 */                                             out.print(resourceid);
/* 3716 */                                             out.write("&attributeIDs=9,11,10&attributeToSelect=9&redirectto=");
/* 3717 */                                             out.print(encodeurl);
/* 3718 */                                             out.write("#Memory Usage\" class=\"bodytextboldwhiteun\">");
/* 3719 */                                             out.print(ALERTCONFIG_TEXT);
/* 3720 */                                             out.write("</a>&nbsp;</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n<tr>\n<td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n<td width=\"90%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3721 */                                             if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3723 */                                             out.write("&attributeid=11&period=-7&resourcename=");
/* 3724 */                                             if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3726 */                                             out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"0\" vspace=\"5\" border=\"0\" title=\"");
/* 3727 */                                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3728 */                                             out.write("\"></a></td>\n<td width=\"10%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3729 */                                             if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3731 */                                             out.write("&attributeid=11&period=-30&resourcename=");
/* 3732 */                                             if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3734 */                                             out.write("',740,550)\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"15\" vspace=\"5\" border=\"0\" title=\"");
/* 3735 */                                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3736 */                                             out.write("\"></a></td>\n</tr>\n</table></td>\n</tr>\n  <tr>\n    <td width=\"57%\" height=\"195\" valign=\"top\"> <br>\n\n      ");
/*      */                                             
/* 3738 */                                             encodeurl = URLEncoder.encode(redirect);
/* 3739 */                                             membean.setresourceID(Integer.parseInt(resourceid));
/*      */                                             
/* 3741 */                                             out.write("\n      ");
/* 3742 */                                             if (_jspx_meth_awolf_005ftimechart_005f3(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                               return;
/* 3744 */                                             out.write(" </td>\n    <td width=\"43%\" align=\"center\" valign=\"top\"><br>\n      <table width=\"82%\" border=\"0\"\n\ncellpadding=\"0\" cellspacing=\"0\" class=\"grayfullborder\"\n  >\n        <tbody>\n          <tr>\n            <td height=\"25\" class=\"columnheadingb\" >");
/* 3745 */                                             out.print(FormatUtil.getString("am.webclient.rca.attribute"));
/* 3746 */                                             out.write("</td>\n            <td height=\"45\" class=\"columnheadingb\">");
/* 3747 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.value"));
/* 3748 */                                             out.write("</td>\n            <td height=\"20\" class=\"columnheadingb\" >");
/* 3749 */                                             out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 3750 */                                             out.write("</td>\n          </tr>\n          <tr>\n            <td width=\"40%\" height=\"20\" class=\"whitegrayborderbr\">");
/* 3751 */                                             out.print(FormatUtil.getString("Total Memory"));
/* 3752 */                                             out.write("</td>\n            <td width=\"40%\" height=\"20\" class=\"whitegrayborderbr\">&nbsp;");
/* 3753 */                                             out.print(formatNumber(membean.gettotal() / 1024L));
/* 3754 */                                             out.write("\n              KB<br> </td>\n            <td width=\"20%\" height=\"20\" class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3755 */                                             out.print((String)request.getAttribute("resourceid"));
/* 3756 */                                             out.write("&attributeid=9')\">");
/* 3757 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "9")));
/* 3758 */                                             out.write("</a></td>\n          </tr>\n          <tr>\n            <td width=\"40%\" class=\"yellowgrayborderbr\">");
/* 3759 */                                             out.print(FormatUtil.getString("Used Memory"));
/* 3760 */                                             out.write("</td>\n            <td  width=\"40%\" class=\"yellowgrayborderbr\" >");
/* 3761 */                                             out.print(formatNumber(membean.getused() / 1024L));
/* 3762 */                                             out.write("\n              KB<br> </td>\n            <td width=\"20%\" class=\"yellowgrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3763 */                                             out.print((String)request.getAttribute("resourceid"));
/* 3764 */                                             out.write("&attributeid=11')\">");
/* 3765 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "11")));
/* 3766 */                                             out.write("</a></td>\n          </tr>\n          <tr>\n            <td width=\"40%\" height=\"20\" class=\"whitegrayborderbr\">");
/* 3767 */                                             out.print(FormatUtil.getString("Free Memory"));
/* 3768 */                                             out.write(" </td>\n            <td width=\"40%\" height=\"20\" class=\"whitegrayborderbr\">&nbsp;");
/* 3769 */                                             out.print(formatNumber(membean.getfree() / 1024L));
/* 3770 */                                             out.write("\n              KB<br> </td>\n            <td width=\"20%\" height=\"20\" class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3771 */                                             out.print((String)request.getAttribute("resourceid"));
/* 3772 */                                             out.write("&attributeid=10')\">");
/* 3773 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "10")));
/* 3774 */                                             out.write("</a></td>\n          </tr>\n\t<tr>\n\t     <td height=\"20\" colspan=\"3\" align=\"right\"  class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3775 */                                             out.print(resourceid);
/* 3776 */                                             out.write("&attributeIDs=9,11,10&attributeToSelect=9&redirectto=");
/* 3777 */                                             out.print(encodeurl);
/* 3778 */                                             out.write("\" class=\"staticlinks\">");
/* 3779 */                                             out.print(ALERTCONFIG_TEXT);
/* 3780 */                                             out.write("</a>&nbsp;&nbsp;</td>\n\t</tr>\n\n        </tbody>\n      </table></td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n   <td width=\"72%\" height=\"31\" class=\"tableheadingtrans\">");
/* 3781 */                                             out.print(FormatUtil.getString("am.webclient.tomacatdetail.applicationsummary"));
/* 3782 */                                             out.write(" <a name=\"Application Summary\" id=\"Application Summary\"></a></td>\n    <td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\nclass=\"lrborder\">\n  <tr>\n    <td width=\"62%\" height=\"28\"  class=\"columnheading\">");
/* 3783 */                                             out.print(FormatUtil.getString("am.webclient.tomacatdetail.applicationname"));
/*      */                                             
/* 3785 */                                             out.write("</td>\n\n    <td width=\"10%\" height=\"28\"  class=\"columnheading\">");
/* 3786 */                                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3787 */                                             out.write("<br></td>\n\n    <td width=\"18%\" height=\"28\"  class=\"columnheading\">");
/* 3788 */                                             out.print(ALERTCONFIG_TEXT);
/* 3789 */                                             out.write("</td>\n  </tr>\n  ");
/*      */                                             
/*      */ 
/* 3792 */                                             int l = 0;
/*      */                                             
/* 3794 */                                             for (Enumeration e = contextdetails.keys(); e.hasMoreElements();) { String bgcolor;
/* 3795 */                                               String bgcolor; if (l % 2 == 0)
/*      */                                               {
/* 3797 */                                                 bgcolor = "whitegrayborder";
/*      */                                               }
/*      */                                               else {
/* 3800 */                                                 bgcolor = "yellowgrayborder";
/*      */                                               }
/*      */                                               
/*      */ 
/*      */ 
/* 3805 */                                               String key = (String)e.nextElement();
/* 3806 */                                               Properties idprops = (Properties)contextdetails.get(key);
/*      */                                               
/* 3808 */                                               encodeurl = URLEncoder.encode(redirect);
/*      */                                               
/* 3810 */                                               out.write("\n  <tr>\n  <td class=\"");
/* 3811 */                                               out.print(bgcolor);
/* 3812 */                                               out.write("\" ><a href=\"/Tomcat.do?name=");
/* 3813 */                                               out.print(name);
/* 3814 */                                               out.write("&context=");
/* 3815 */                                               out.print(key);
/* 3816 */                                               out.write("&haid=");
/* 3817 */                                               out.print(haid);
/* 3818 */                                               out.write("&appName=");
/* 3819 */                                               out.print(appname);
/* 3820 */                                               out.write("&resourcename=");
/* 3821 */                                               out.print(displayname);
/* 3822 */                                               out.write("&contextid=");
/* 3823 */                                               out.print(idprops.getProperty("ID"));
/* 3824 */                                               out.write("&resourceid=");
/* 3825 */                                               out.print(resourceid);
/* 3826 */                                               out.write("\" class=\"resourcename\">\n");
/* 3827 */                                               out.print(key);
/* 3828 */                                               out.write(" </a></td>\n\n\n    <td align=\"center\" class=\"");
/* 3829 */                                               out.print(bgcolor);
/* 3830 */                                               out.write("\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3831 */                                               out.print(idprops.getProperty("ID"));
/* 3832 */                                               out.write("&attributeid=23')\">");
/* 3833 */                                               out.print(getSeverityImageForHealth(alert.getProperty(idprops.getProperty("ID") + "#" + "23")));
/* 3834 */                                               out.write("\n      </a> </td>\n\n    <td width=\"16%\" align=\"center\" class=\"");
/* 3835 */                                               out.print(bgcolor);
/* 3836 */                                               out.write("\" > <a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3837 */                                               out.print(idprops.getProperty("ID"));
/* 3838 */                                               out.write("&attributeIDs=23&attributeToSelect=23&redirectto=");
/* 3839 */                                               out.print(encodeurl);
/* 3840 */                                               out.write("#Application Summary\" class=\"bodytextboldwhiteun\"><img src=\"/images/icon_associateaction.gif\"  align=\"absmiddle\" border=\"0\"></a></td>\n  </tr>\n  ");
/*      */                                               
/* 3842 */                                               l++;
/*      */                                             }
/* 3844 */                                             if (contextdetails.size() == 0)
/*      */                                             {
/* 3846 */                                               out.write("\n  <tr>\n    <td colspan=\"6\" class=\"bodytextbold\"  height=\"28\">");
/* 3847 */                                               out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3848 */                                               out.write(" </td>\n    ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 3852 */                                             out.write("\n</table>\n\n\n\n\n\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 3856 */                                           out.write(10);
/* 3857 */                                           out.write(10);
/*      */                                           
/* 3859 */                                           if (!version.equals("3"))
/*      */                                           {
/* 3861 */                                             resbean.setVersion(version);
/*      */                                           }
/* 3863 */                                           if ((version.equals("5")) || (version.equals("6")) || (version.equals("7")))
/*      */                                           {
/*      */ 
/*      */ 
/* 3867 */                                             out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"100%\" height=\"31\" class=\"tableheadingtrans\">");
/* 3868 */                                             out.print(FormatUtil.getString("am.webclient.tomacatdetail.threadavailability"));
/* 3869 */                                             out.write(" <a name=\"ThreadsAvailability\"  id=\"Threads Availability\"></a></td>\n  </tr>\n</table>\n    <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n      <tr>\n        <td width=\"100%\" height=\"235\" class=\"bborder\" align=\"center\">\n          ");
/*      */                                             
/* 3871 */                                             threadbean.setresourceID(Integer.parseInt(resourceid));
/* 3872 */                                             encodeurl = URLEncoder.encode(redirect);
/* 3873 */                                             threadbean.update(Integer.parseInt(resourceid));
/* 3874 */                                             request.setAttribute("threadinfo", threadbean.getData());
/*      */                                             
/* 3876 */                                             out.write("\n \t\t");
/*      */                                             
/* 3878 */                                             StackBarChart _jspx_th_awolf_005fstackbarchart_005f0 = (StackBarChart)this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.get(StackBarChart.class);
/* 3879 */                                             _jspx_th_awolf_005fstackbarchart_005f0.setPageContext(_jspx_page_context);
/* 3880 */                                             _jspx_th_awolf_005fstackbarchart_005f0.setParent(_jspx_th_c_005fif_005f15);
/*      */                                             
/* 3882 */                                             _jspx_th_awolf_005fstackbarchart_005f0.setDataSetProducer("threadbean");
/*      */                                             
/* 3884 */                                             _jspx_th_awolf_005fstackbarchart_005f0.setWidth("400");
/*      */                                             
/* 3886 */                                             _jspx_th_awolf_005fstackbarchart_005f0.setHeight("200");
/*      */                                             
/* 3888 */                                             _jspx_th_awolf_005fstackbarchart_005f0.setLegend("true");
/*      */                                             
/* 3890 */                                             _jspx_th_awolf_005fstackbarchart_005f0.setUrl(false);
/*      */                                             
/* 3892 */                                             _jspx_th_awolf_005fstackbarchart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.connectorname"));
/*      */                                             
/* 3894 */                                             _jspx_th_awolf_005fstackbarchart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.numberofthread"));
/* 3895 */                                             int _jspx_eval_awolf_005fstackbarchart_005f0 = _jspx_th_awolf_005fstackbarchart_005f0.doStartTag();
/* 3896 */                                             if (_jspx_eval_awolf_005fstackbarchart_005f0 != 0) {
/* 3897 */                                               if (_jspx_eval_awolf_005fstackbarchart_005f0 != 1) {
/* 3898 */                                                 out = _jspx_page_context.pushBody();
/* 3899 */                                                 _jspx_th_awolf_005fstackbarchart_005f0.setBodyContent((BodyContent)out);
/* 3900 */                                                 _jspx_th_awolf_005fstackbarchart_005f0.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3903 */                                                 out.write("\n\t    ");
/* 3904 */                                                 int evalDoAfterBody = _jspx_th_awolf_005fstackbarchart_005f0.doAfterBody();
/* 3905 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3908 */                                               if (_jspx_eval_awolf_005fstackbarchart_005f0 != 1) {
/* 3909 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3912 */                                             if (_jspx_th_awolf_005fstackbarchart_005f0.doEndTag() == 5) {
/* 3913 */                                               this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fstackbarchart_005f0); return;
/*      */                                             }
/*      */                                             
/* 3916 */                                             this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fstackbarchart_005f0);
/* 3917 */                                             out.write("\n          </td>\n      </tr>\n      <tr>\n        <td width=\"100%\" height=\"175\" valign=\"top\">\n          <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n            <tbody>\n              <tr>\n                <td width=\"20%\" height=\"25\" class=\"columnheadingb\" >");
/* 3918 */                                             out.print(FormatUtil.getString("am.webclient.tomacatdetail.connectorname"));
/* 3919 */                                             out.write("</td>\n                <td width=\"25%\"  height=\"25\" class=\"columnheadingb\" >");
/* 3920 */                                             out.print(FormatUtil.getString("Busy Threads"));
/* 3921 */                                             out.write("</td>\n                <td width=\"25%\" height=\"25\" class=\"columnheadingb\"> ");
/* 3922 */                                             out.print(FormatUtil.getString("Current Threads"));
/* 3923 */                                             out.write("</td>\n                <td width=\"25%\" height=\"25\" class=\"columnheadingb\" >");
/* 3924 */                                             out.print(FormatUtil.getString("am.webclient.tomacatdetail.maxsparethread"));
/* 3925 */                                             out.write("</td>\n                <td width=\"5%\" height=\"25\" class=\"columnheadingb\" >&nbsp;</td>\n              </tr>\n\t\t\t  ");
/*      */                                             
/* 3927 */                                             EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3928 */                                             _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 3929 */                                             _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                             
/* 3931 */                                             _jspx_th_logic_005fempty_005f2.setName("threadinfo");
/* 3932 */                                             int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 3933 */                                             if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */                                               for (;;) {
/* 3935 */                                                 out.write("\n\t\t\t  <tr>\n\t\t\t  <td  height=\"35\" width=\"100%\" colspan=\"5\" class=\"bodytext\">\n\t\t\t  ");
/* 3936 */                                                 out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3937 */                                                 out.write("\n\t\t\t  </td>\n\t\t\t  </tr>\n\t\t\t  </tbody>\n\t\t\t  </table>\n\t\t\t  ");
/* 3938 */                                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 3939 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3943 */                                             if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 3944 */                                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */                                             }
/*      */                                             
/* 3947 */                                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 3948 */                                             out.write("\n\n\t\t\t  ");
/*      */                                             
/* 3950 */                                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3951 */                                             _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3952 */                                             _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                             
/* 3954 */                                             _jspx_th_logic_005fnotEmpty_005f2.setName("threadinfo");
/* 3955 */                                             int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3956 */                                             if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                               for (;;) {
/* 3958 */                                                 out.write("\n\t\t\t  \t");
/*      */                                                 
/* 3960 */                                                 ArrayList attribIDs1 = new ArrayList();
/* 3961 */                                                 attribIDs1.add("33");
/* 3962 */                                                 attribIDs1.add("34");
/* 3963 */                                                 attribIDs1.add("35");
/* 3964 */                                                 ArrayList resIDs1 = new ArrayList();
/*      */                                                 
/* 3966 */                                                 out.write("\n\t\t\t  ");
/*      */                                                 
/* 3968 */                                                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3969 */                                                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3970 */                                                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                                 
/* 3972 */                                                 _jspx_th_logic_005fiterate_005f0.setName("threadinfo");
/*      */                                                 
/* 3974 */                                                 _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                                 
/* 3976 */                                                 _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                                 
/* 3978 */                                                 _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 3979 */                                                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3980 */                                                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3981 */                                                   ArrayList row = null;
/* 3982 */                                                   Integer j = null;
/* 3983 */                                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3984 */                                                     out = _jspx_page_context.pushBody();
/* 3985 */                                                     _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3986 */                                                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                                   }
/* 3988 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3989 */                                                   j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                   for (;;) {
/* 3991 */                                                     out.write("\n\t\t\t  \t");
/*      */                                                     
/* 3993 */                                                     resIDs1.add((String)row.get(4));
/*      */                                                     
/* 3995 */                                                     out.write("\n\t\t\t  ");
/* 3996 */                                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3997 */                                                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3998 */                                                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 3999 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 4002 */                                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4003 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 4006 */                                                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 4007 */                                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                                 }
/*      */                                                 
/* 4010 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 4011 */                                                 out.write("\n\t\t\t  ");
/*      */                                                 
/* 4013 */                                                 Properties alert1 = getStatus(resIDs1, attribIDs1);
/*      */                                                 
/* 4015 */                                                 out.write("\n\t\t\t  ");
/*      */                                                 
/* 4017 */                                                 IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4018 */                                                 _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 4019 */                                                 _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                                 
/* 4021 */                                                 _jspx_th_logic_005fiterate_005f1.setName("threadinfo");
/*      */                                                 
/* 4023 */                                                 _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                                 
/* 4025 */                                                 _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                                                 
/* 4027 */                                                 _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 4028 */                                                 int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 4029 */                                                 if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 4030 */                                                   ArrayList row = null;
/* 4031 */                                                   Integer j = null;
/* 4032 */                                                   if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4033 */                                                     out = _jspx_page_context.pushBody();
/* 4034 */                                                     _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 4035 */                                                     _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                                   }
/* 4037 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4038 */                                                   j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                   for (;;) {
/* 4040 */                                                     out.write("\n\t\t\t  ");
/*      */                                                     
/* 4042 */                                                     String bgcolour = "class=\"whitegrayborder\"";
/* 4043 */                                                     String threadid = (String)row.get(4);
/* 4044 */                                                     if (j.intValue() % 2 == 0)
/*      */                                                     {
/* 4046 */                                                       bgcolour = "class=\"whitegrayborder\"";
/*      */                                                     }
/*      */                                                     else
/*      */                                                     {
/* 4050 */                                                       bgcolour = "class=\"yellowgrayborder\"";
/*      */                                                     }
/*      */                                                     
/* 4053 */                                                     out.write("\n\t\t\t  <tr>\n\t\t\t  <td ");
/* 4054 */                                                     out.print(bgcolour);
/* 4055 */                                                     out.write(62);
/* 4056 */                                                     out.write(32);
/* 4057 */                                                     out.print((String)row.get(0));
/* 4058 */                                                     out.write("</td>\n\t\t\t  <td ");
/* 4059 */                                                     out.print(bgcolour);
/* 4060 */                                                     out.write(62);
/* 4061 */                                                     out.write(32);
/* 4062 */                                                     out.print((String)row.get(1));
/* 4063 */                                                     out.write("&nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4064 */                                                     out.print(threadid);
/* 4065 */                                                     out.write("&attributeid=35')\">");
/* 4066 */                                                     out.print(getSeverityImage(alert1.getProperty(threadid + "#" + "35")));
/* 4067 */                                                     out.write("</a></td>\n\t\t\t  <td ");
/* 4068 */                                                     out.print(bgcolour);
/* 4069 */                                                     out.write(62);
/* 4070 */                                                     out.write(32);
/* 4071 */                                                     out.print((String)row.get(2));
/* 4072 */                                                     out.write("&nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4073 */                                                     out.print(threadid);
/* 4074 */                                                     out.write("&attributeid=33')\">");
/* 4075 */                                                     out.print(getSeverityImage(alert1.getProperty(threadid + "#" + "33")));
/* 4076 */                                                     out.write("</a></td>\n\t\t\t  <td ");
/* 4077 */                                                     out.print(bgcolour);
/* 4078 */                                                     out.write(62);
/* 4079 */                                                     out.write(32);
/* 4080 */                                                     out.print((String)row.get(3));
/* 4081 */                                                     out.write(" &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4082 */                                                     out.print(threadid);
/* 4083 */                                                     out.write("&attributeid=34')\">");
/* 4084 */                                                     out.print(getSeverityImage(alert1.getProperty(threadid + "#" + "34")));
/* 4085 */                                                     out.write("</a></td>\n\t\t\t  <td ");
/* 4086 */                                                     out.print(bgcolour);
/* 4087 */                                                     out.write(">  <a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4088 */                                                     out.print(threadid);
/* 4089 */                                                     out.write("&attributeIDs=33,34,35&attributeToSelect=35&redirectto=");
/* 4090 */                                                     out.print(encodeurl);
/* 4091 */                                                     out.write("#Response Summary\"><img src=\"/images/icon_associateaction.gif\" border=\"0\" align=\"absmiddle\">&nbsp;</a></td>\n\t\t\t  </tr>\n\t\t\t  ");
/* 4092 */                                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 4093 */                                                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4094 */                                                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 4095 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 4098 */                                                   if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4099 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 4102 */                                                 if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 4103 */                                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                                 }
/*      */                                                 
/* 4106 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 4107 */                                                 out.write("\n\t\t\t  </tbody>\n\t\t\t  </table>\n\t\t\t  ");
/* 4108 */                                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 4109 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 4113 */                                             if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 4114 */                                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                                             }
/*      */                                             
/* 4117 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 4118 */                                             out.write("\n\t\t</td>\n\t\t</tr>\n    </table>\n\t<br>\n    ");
/*      */                                           }
/*      */                                           
/* 4121 */                                           if (!version.equals("3")) {
/* 4122 */                                             encodeurl = URLEncoder.encode(redirect);
/*      */                                             
/* 4124 */                                             if (version.equals("4"))
/*      */                                             {
/*      */ 
/* 4127 */                                               out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"31\" class=\"tableheadingtrans\">");
/* 4128 */                                               out.print(FormatUtil.getString("am.webclient.tomacatdetail.responsesummery"));
/* 4129 */                                               out.write(" <a name=\"ResponseSummary\" id=\"Response Summary\"></a></td>\n    <td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\"></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" height=\"160\">\n<tr>\n  <td width=\"49%\"  align=\"center\" valign=\"top\" style=\"padding-top:15px;\">\n    ");
/*      */                                               
/* 4131 */                                               resbean.setresourceID(Integer.parseInt(resourceid));
/*      */                                               
/* 4133 */                                               out.write("\n    ");
/*      */                                               
/* 4135 */                                               AMWolf _jspx_th_awolf_005fpiechart_005f1 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005flegend_005fheight_005fdataSetProducer.get(AMWolf.class);
/* 4136 */                                               _jspx_th_awolf_005fpiechart_005f1.setPageContext(_jspx_page_context);
/* 4137 */                                               _jspx_th_awolf_005fpiechart_005f1.setParent(_jspx_th_c_005fif_005f15);
/*      */                                               
/* 4139 */                                               _jspx_th_awolf_005fpiechart_005f1.setDataSetProducer("resbean");
/*      */                                               
/* 4141 */                                               _jspx_th_awolf_005fpiechart_005f1.setWidth("300");
/*      */                                               
/* 4143 */                                               _jspx_th_awolf_005fpiechart_005f1.setHeight("200");
/*      */                                               
/* 4145 */                                               _jspx_th_awolf_005fpiechart_005f1.setLegend("true");
/* 4146 */                                               int _jspx_eval_awolf_005fpiechart_005f1 = _jspx_th_awolf_005fpiechart_005f1.doStartTag();
/* 4147 */                                               if (_jspx_eval_awolf_005fpiechart_005f1 != 0) {
/* 4148 */                                                 if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 4149 */                                                   out = _jspx_page_context.pushBody();
/* 4150 */                                                   _jspx_th_awolf_005fpiechart_005f1.setBodyContent((BodyContent)out);
/* 4151 */                                                   _jspx_th_awolf_005fpiechart_005f1.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4154 */                                                   out.write("\n    ");
/*      */                                                   
/* 4156 */                                                   Property _jspx_th_awolf_005fmap_005f1 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4157 */                                                   _jspx_th_awolf_005fmap_005f1.setPageContext(_jspx_page_context);
/* 4158 */                                                   _jspx_th_awolf_005fmap_005f1.setParent(_jspx_th_awolf_005fpiechart_005f1);
/*      */                                                   
/* 4160 */                                                   _jspx_th_awolf_005fmap_005f1.setId("color");
/* 4161 */                                                   int _jspx_eval_awolf_005fmap_005f1 = _jspx_th_awolf_005fmap_005f1.doStartTag();
/* 4162 */                                                   if (_jspx_eval_awolf_005fmap_005f1 != 0) {
/* 4163 */                                                     if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 4164 */                                                       out = _jspx_page_context.pushBody();
/* 4165 */                                                       _jspx_th_awolf_005fmap_005f1.setBodyContent((BodyContent)out);
/* 4166 */                                                       _jspx_th_awolf_005fmap_005f1.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4169 */                                                       out.write(32);
/*      */                                                       
/* 4171 */                                                       AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4172 */                                                       _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 4173 */                                                       _jspx_th_awolf_005fparam_005f2.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                       
/* 4175 */                                                       _jspx_th_awolf_005fparam_005f2.setName("1");
/*      */                                                       
/* 4177 */                                                       _jspx_th_awolf_005fparam_005f2.setValue("#FF9900");
/* 4178 */                                                       int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 4179 */                                                       if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 4180 */                                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2); return;
/*      */                                                       }
/*      */                                                       
/* 4183 */                                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 4184 */                                                       out.write(32);
/*      */                                                       
/* 4186 */                                                       AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4187 */                                                       _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 4188 */                                                       _jspx_th_awolf_005fparam_005f3.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                       
/* 4190 */                                                       _jspx_th_awolf_005fparam_005f3.setName("0");
/*      */                                                       
/* 4192 */                                                       _jspx_th_awolf_005fparam_005f3.setValue("#00CCFF");
/* 4193 */                                                       int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 4194 */                                                       if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 4195 */                                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3); return;
/*      */                                                       }
/*      */                                                       
/* 4198 */                                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 4199 */                                                       out.write("\n    ");
/* 4200 */                                                       int evalDoAfterBody = _jspx_th_awolf_005fmap_005f1.doAfterBody();
/* 4201 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4204 */                                                     if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 4205 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4208 */                                                   if (_jspx_th_awolf_005fmap_005f1.doEndTag() == 5) {
/* 4209 */                                                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1); return;
/*      */                                                   }
/*      */                                                   
/* 4212 */                                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 4213 */                                                   out.write(32);
/* 4214 */                                                   int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f1.doAfterBody();
/* 4215 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4218 */                                                 if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 4219 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4222 */                                               if (_jspx_th_awolf_005fpiechart_005f1.doEndTag() == 5) {
/* 4223 */                                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1); return;
/*      */                                               }
/*      */                                               
/* 4226 */                                               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1);
/* 4227 */                                               out.write(10);
/* 4228 */                                               out.write(32);
/* 4229 */                                               out.write(32);
/*      */                                               
/* 4231 */                                               Properties p = resbean.getProperties();
/*      */                                               
/* 4233 */                                               out.write("\n</td><td width=\"51%\" align=\"center\" valign=\"top\" style=\"padding-bottom:15px;\">\n  <br>\n  <table width=\"82%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"grayfullborder\">\n    <tbody>\n\t<tr>\n\n\t    \t<td height=\"25\" class=\"columnheadingb\" >");
/* 4234 */                                               out.print(FormatUtil.getString("table.heading.attribute"));
/* 4235 */                                               out.write("</td>\n            <td height=\"25\" class=\"columnheadingb\">");
/* 4236 */                                               out.print(FormatUtil.getString("table.heading.value"));
/* 4237 */                                               out.write("</td>\n            <td height=\"25\" class=\"columnheadingb\" >");
/* 4238 */                                               out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 4239 */                                               out.write("</td>\n\n\n\t</tr>\n      <tr>\n        <td width=\"50%\" height=\"35\" class=\"whitegrayborderbr\">");
/* 4240 */                                               out.print(FormatUtil.getString("1XX Responses"));
/* 4241 */                                               out.write("\n        </td>\n        <td width=\"20%\" height=\"35\" class=\"whitegrayborderbr\">\n          ");
/*      */                                               
/* 4243 */                                               if (p.containsKey("1XX"))
/*      */                                               {
/*      */ 
/* 4246 */                                                 out.write("\n          ");
/* 4247 */                                                 out.print(formatNumber(p.get("1XX")));
/* 4248 */                                                 out.write("\n          ");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/*      */ 
/* 4254 */                                                 out.write("\n          -\n          ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 4258 */                                               out.write("\n        </td>\n        <td width=\"30%\" height=\"35\" class=\"whitegrayborder\" align=center><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4259 */                                               out.print((String)request.getAttribute("resourceid"));
/* 4260 */                                               out.write("&attributeid=5')\">");
/* 4261 */                                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "5")));
/* 4262 */                                               out.write("</a></td>\n      </tr>\n      <tr>\n        <td width=\"50%\" height=\"35\" class=\"yellowgrayborderbr\">");
/* 4263 */                                               out.print(FormatUtil.getString("2XX Responses"));
/* 4264 */                                               out.write("</td>\n        <td width=\"20%\" height=\"35\" class=\"yellowgrayborderbr\">\n          ");
/*      */                                               
/* 4266 */                                               if (p.containsKey("2XX"))
/*      */                                               {
/*      */ 
/* 4269 */                                                 out.write("\n          ");
/* 4270 */                                                 out.print(formatNumber(p.get("2XX")));
/* 4271 */                                                 out.write("\n          ");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/*      */ 
/* 4277 */                                                 out.write("\n          -\n          ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 4281 */                                               out.write("\n        </td>\n        <td width=\"30%\" height=\"35\" class=\"yellowgrayborder\" align=center ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4282 */                                               out.print((String)request.getAttribute("resourceid"));
/* 4283 */                                               out.write("&attributeid=4')\">");
/* 4284 */                                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "4")));
/* 4285 */                                               out.write("</a></td>\n      </tr>\n      <tr>\n        <td width=\"50%\" height=\"35\" class=\"whitegrayborderbr\">");
/* 4286 */                                               out.print(FormatUtil.getString("3XX Responses"));
/* 4287 */                                               out.write(" <br> </td>\n        <td width=\"20%\" height=\"35\" class=\"whitegrayborderbr\">\n          ");
/*      */                                               
/* 4289 */                                               if (p.containsKey("3XX"))
/*      */                                               {
/*      */ 
/* 4292 */                                                 out.write("\n          ");
/* 4293 */                                                 out.print(formatNumber(p.get("3XX")));
/* 4294 */                                                 out.write("\n          ");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/*      */ 
/* 4300 */                                                 out.write("\n          -\n          ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 4304 */                                               out.write("\n          <br> </td>\n        <td width=\"30%\" height=\"35\" class=\"whitegrayborder\" align=center ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4305 */                                               out.print((String)request.getAttribute("resourceid"));
/* 4306 */                                               out.write("&attributeid=3')\">");
/* 4307 */                                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3")));
/* 4308 */                                               out.write("</a></td>\n      </tr>\n      <tr>\n        <td width=\"50%\" height=\"35\" class=\"yellowgrayborderbr\">");
/* 4309 */                                               out.print(FormatUtil.getString("4XX Responses"));
/* 4310 */                                               out.write("\n        </td>\n        <td width=\"20%\" height=\"35\" class=\"yellowgrayborderbr\">\n          ");
/*      */                                               
/* 4312 */                                               if (p.containsKey("4XX"))
/*      */                                               {
/*      */ 
/* 4315 */                                                 out.write("\n          ");
/* 4316 */                                                 out.print(formatNumber(p.get("4XX")));
/* 4317 */                                                 out.write("\n          ");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/*      */ 
/* 4323 */                                                 out.write("\n          -\n          ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 4327 */                                               out.write("\n          <br> </td>\n        <td width=\"30%\" height=\"35\" class=\"yellowgrayborder\" align=center ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4328 */                                               out.print((String)request.getAttribute("resourceid"));
/* 4329 */                                               out.write("&attributeid=2')\">");
/* 4330 */                                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "2")));
/* 4331 */                                               out.write("</a></td>\n      </tr>\n      <tr>\n        <td width=\"50%\" height=\"20\" class=\"whitegrayborderbr\" title=\"");
/* 4332 */                                               out.print(tipDB);
/* 4333 */                                               out.write(34);
/* 4334 */                                               out.write(62);
/* 4335 */                                               out.print(FormatUtil.getString("5XX Responses"));
/* 4336 */                                               out.write("</td>\n        <td width=\"20%\" height=\"20\" class=\"whitegrayborderbr\">\n          ");
/*      */                                               
/* 4338 */                                               if (p.containsKey("5XX"))
/*      */                                               {
/*      */ 
/* 4341 */                                                 out.write("\n          ");
/* 4342 */                                                 out.print(formatNumber(p.get("5XX")));
/* 4343 */                                                 out.write("\n          ");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/*      */ 
/* 4349 */                                                 out.write("\n          -\n          ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 4353 */                                               out.write("\n         <br>\n       <td width=\"30%\" height=\"20\" class=\"whitegrayborder\" align=\"center\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4354 */                                               out.print((String)request.getAttribute("resourceid"));
/* 4355 */                                               out.write("&attributeid=1')\">");
/* 4356 */                                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "1")));
/* 4357 */                                               out.write("</a></td>\n</tr>\n<tr class=\"yellowgrayborder\">\n<td height=\"20\" colspan=\"3\" align=\"right\"> <img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4358 */                                               out.print(resourceid);
/* 4359 */                                               out.write("&attributeIDs=5,4,3,2,1&attributeToSelect=5&redirectto=");
/* 4360 */                                               out.print(encodeurl);
/* 4361 */                                               out.write("#Response Summary\" class=\"staticlinks\">");
/* 4362 */                                               out.print(ALERTCONFIG_TEXT);
/* 4363 */                                               out.write("</a>&nbsp;\n      </td>\n</tr>\n</tbody>\n  </table></td></tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n   <td width=\"72%\" height=\"31\" class=\"tableheadingtrans\">");
/* 4364 */                                               out.print(FormatUtil.getString("am.webclient.tomacatdetail.applicationsummary"));
/* 4365 */                                               out.write(" <a name=\"Application Summary\" id=\"Application Summary\"></a></td>\n    <td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\nclass=\"lrborder\">\n  <tr>\n    <td width=\"62%\" height=\"28\"  class=\"columnheading\">");
/* 4366 */                                               out.print(FormatUtil.getString("am.webclient.tomacatdetail.applicationname"));
/*      */                                               
/* 4368 */                                               out.write("</td>\n\n    <td width=\"10%\" height=\"28\"  class=\"columnheading\" align=\"center\">");
/* 4369 */                                               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4370 */                                               out.write("<br></td>\n\n    <td width=\"18%\" height=\"28\"  class=\"columnheading\">");
/* 4371 */                                               out.print(ALERTCONFIG_TEXT);
/* 4372 */                                               out.write("</td>\n  </tr>\n  ");
/*      */                                               
/*      */ 
/* 4375 */                                               int l = 0;
/*      */                                               
/* 4377 */                                               for (Enumeration e = contextdetails.keys(); e.hasMoreElements();) { String bgcolor;
/* 4378 */                                                 String bgcolor; if (l % 2 == 0)
/*      */                                                 {
/* 4380 */                                                   bgcolor = "whitegrayborder";
/*      */                                                 }
/*      */                                                 else {
/* 4383 */                                                   bgcolor = "yellowgrayborder";
/*      */                                                 }
/*      */                                                 
/*      */ 
/*      */ 
/* 4388 */                                                 String key = (String)e.nextElement();
/* 4389 */                                                 Properties idprops = (Properties)contextdetails.get(key);
/*      */                                                 
/* 4391 */                                                 encodeurl = URLEncoder.encode(redirect);
/*      */                                                 
/* 4393 */                                                 out.write("\n  <tr>\n  <td class=\"");
/* 4394 */                                                 out.print(bgcolor);
/* 4395 */                                                 out.write("\" ><a href=\"/Tomcat.do?name=");
/* 4396 */                                                 out.print(name);
/* 4397 */                                                 out.write("&context=");
/* 4398 */                                                 out.print(key);
/* 4399 */                                                 out.write("&haid=");
/* 4400 */                                                 out.print(haid);
/* 4401 */                                                 out.write("&appName=");
/* 4402 */                                                 out.print(appname);
/* 4403 */                                                 out.write("&resourcename=");
/* 4404 */                                                 out.print(displayname);
/* 4405 */                                                 out.write("&contextid=");
/* 4406 */                                                 out.print(idprops.getProperty("ID"));
/* 4407 */                                                 out.write("&resourceid=");
/* 4408 */                                                 out.print(resourceid);
/* 4409 */                                                 out.write("\" class=\"resourcename\">\n");
/* 4410 */                                                 out.print(key);
/* 4411 */                                                 out.write(" </a></td>\n\n\n    <td align=\"center\" class=\"");
/* 4412 */                                                 out.print(bgcolor);
/* 4413 */                                                 out.write("\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4414 */                                                 out.print(idprops.getProperty("ID"));
/* 4415 */                                                 out.write("&attributeid=23')\">");
/* 4416 */                                                 out.print(getSeverityImageForHealth(alert.getProperty(idprops.getProperty("ID") + "#" + "23")));
/* 4417 */                                                 out.write("\n      </a> </td>\n\n    <td width=\"16%\" align=\"center\" class=\"");
/* 4418 */                                                 out.print(bgcolor);
/* 4419 */                                                 out.write("\" > <a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4420 */                                                 out.print(idprops.getProperty("ID"));
/* 4421 */                                                 out.write("&attributeIDs=23&attributeToSelect=23&redirectto=");
/* 4422 */                                                 out.print(encodeurl);
/* 4423 */                                                 out.write("#Application Summary\" class=\"bodytextboldwhiteun\"><img src=\"/images/icon_associateaction.gif\"  align=\"absmiddle\" border=\"0\"></a></td>\n  </tr>\n  ");
/*      */                                                 
/* 4425 */                                                 l++;
/*      */                                               }
/* 4427 */                                               if (contextdetails.size() == 0)
/*      */                                               {
/* 4429 */                                                 out.write("\n  <tr>\n    <td colspan=\"6\" class=\"bodytextbold\"  height=\"28\">");
/* 4430 */                                                 out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 4431 */                                                 out.write(" </td>\n    ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 4435 */                                               out.write("\n</table>\n\n\n");
/*      */                                             }
/*      */                                             
/* 4438 */                                             if ((version.equals("5")) || (version.equals("6")) || (version.equals("7")))
/*      */                                             {
/*      */ 
/* 4441 */                                               out.write("\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t<tr>\n\t\t<td width=\"72%\" height=\"31\" class=\"tableheadingtrans\">");
/* 4442 */                                               out.print(FormatUtil.getString("am.webclient.tomacatdetail.responsesummery"));
/* 4443 */                                               out.write(" <a name=\"ResponseSummary\" id=\"Response Summary\"></a></td>\n\t\t</tr>\n\t\t</table>\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" height=\"160\">\n\t\t<tr>\n\t\t<td width=\"49%\"  align=\"center\" valign=\"top\">\n\t\t");
/*      */                                               
/* 4445 */                                               resbean.setresourceID(Integer.parseInt(resourceid));
/*      */                                               
/* 4447 */                                               out.write(10);
/* 4448 */                                               out.write(9);
/* 4449 */                                               out.write(9);
/*      */                                               
/* 4451 */                                               AMWolf _jspx_th_awolf_005fpiechart_005f2 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdataSetProducer.get(AMWolf.class);
/* 4452 */                                               _jspx_th_awolf_005fpiechart_005f2.setPageContext(_jspx_page_context);
/* 4453 */                                               _jspx_th_awolf_005fpiechart_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                               
/* 4455 */                                               _jspx_th_awolf_005fpiechart_005f2.setDataSetProducer("resbean");
/*      */                                               
/* 4457 */                                               _jspx_th_awolf_005fpiechart_005f2.setWidth("300");
/*      */                                               
/* 4459 */                                               _jspx_th_awolf_005fpiechart_005f2.setHeight("200");
/*      */                                               
/* 4461 */                                               _jspx_th_awolf_005fpiechart_005f2.setUnits(" ");
/*      */                                               
/* 4463 */                                               _jspx_th_awolf_005fpiechart_005f2.setLegend("true");
/* 4464 */                                               int _jspx_eval_awolf_005fpiechart_005f2 = _jspx_th_awolf_005fpiechart_005f2.doStartTag();
/* 4465 */                                               if (_jspx_eval_awolf_005fpiechart_005f2 != 0) {
/* 4466 */                                                 if (_jspx_eval_awolf_005fpiechart_005f2 != 1) {
/* 4467 */                                                   out = _jspx_page_context.pushBody();
/* 4468 */                                                   _jspx_th_awolf_005fpiechart_005f2.setBodyContent((BodyContent)out);
/* 4469 */                                                   _jspx_th_awolf_005fpiechart_005f2.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4472 */                                                   out.write(10);
/* 4473 */                                                   out.write(9);
/* 4474 */                                                   out.write(9);
/*      */                                                   
/* 4476 */                                                   Property _jspx_th_awolf_005fmap_005f2 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4477 */                                                   _jspx_th_awolf_005fmap_005f2.setPageContext(_jspx_page_context);
/* 4478 */                                                   _jspx_th_awolf_005fmap_005f2.setParent(_jspx_th_awolf_005fpiechart_005f2);
/*      */                                                   
/* 4480 */                                                   _jspx_th_awolf_005fmap_005f2.setId("color");
/* 4481 */                                                   int _jspx_eval_awolf_005fmap_005f2 = _jspx_th_awolf_005fmap_005f2.doStartTag();
/* 4482 */                                                   if (_jspx_eval_awolf_005fmap_005f2 != 0) {
/* 4483 */                                                     if (_jspx_eval_awolf_005fmap_005f2 != 1) {
/* 4484 */                                                       out = _jspx_page_context.pushBody();
/* 4485 */                                                       _jspx_th_awolf_005fmap_005f2.setBodyContent((BodyContent)out);
/* 4486 */                                                       _jspx_th_awolf_005fmap_005f2.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4489 */                                                       out.write(32);
/*      */                                                       
/* 4491 */                                                       AMParam _jspx_th_awolf_005fparam_005f4 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4492 */                                                       _jspx_th_awolf_005fparam_005f4.setPageContext(_jspx_page_context);
/* 4493 */                                                       _jspx_th_awolf_005fparam_005f4.setParent(_jspx_th_awolf_005fmap_005f2);
/*      */                                                       
/* 4495 */                                                       _jspx_th_awolf_005fparam_005f4.setName("1");
/*      */                                                       
/* 4497 */                                                       _jspx_th_awolf_005fparam_005f4.setValue("#FF9900");
/* 4498 */                                                       int _jspx_eval_awolf_005fparam_005f4 = _jspx_th_awolf_005fparam_005f4.doStartTag();
/* 4499 */                                                       if (_jspx_th_awolf_005fparam_005f4.doEndTag() == 5) {
/* 4500 */                                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4); return;
/*      */                                                       }
/*      */                                                       
/* 4503 */                                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 4504 */                                                       out.write(32);
/*      */                                                       
/* 4506 */                                                       AMParam _jspx_th_awolf_005fparam_005f5 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4507 */                                                       _jspx_th_awolf_005fparam_005f5.setPageContext(_jspx_page_context);
/* 4508 */                                                       _jspx_th_awolf_005fparam_005f5.setParent(_jspx_th_awolf_005fmap_005f2);
/*      */                                                       
/* 4510 */                                                       _jspx_th_awolf_005fparam_005f5.setName("0");
/*      */                                                       
/* 4512 */                                                       _jspx_th_awolf_005fparam_005f5.setValue("#00CCFF");
/* 4513 */                                                       int _jspx_eval_awolf_005fparam_005f5 = _jspx_th_awolf_005fparam_005f5.doStartTag();
/* 4514 */                                                       if (_jspx_th_awolf_005fparam_005f5.doEndTag() == 5) {
/* 4515 */                                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5); return;
/*      */                                                       }
/*      */                                                       
/* 4518 */                                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5);
/* 4519 */                                                       out.write(10);
/* 4520 */                                                       out.write(9);
/* 4521 */                                                       out.write(9);
/* 4522 */                                                       int evalDoAfterBody = _jspx_th_awolf_005fmap_005f2.doAfterBody();
/* 4523 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4526 */                                                     if (_jspx_eval_awolf_005fmap_005f2 != 1) {
/* 4527 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4530 */                                                   if (_jspx_th_awolf_005fmap_005f2.doEndTag() == 5) {
/* 4531 */                                                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f2); return;
/*      */                                                   }
/*      */                                                   
/* 4534 */                                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f2);
/* 4535 */                                                   out.write(32);
/* 4536 */                                                   int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f2.doAfterBody();
/* 4537 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4540 */                                                 if (_jspx_eval_awolf_005fpiechart_005f2 != 1) {
/* 4541 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4544 */                                               if (_jspx_th_awolf_005fpiechart_005f2.doEndTag() == 5) {
/* 4545 */                                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f2); return;
/*      */                                               }
/*      */                                               
/* 4548 */                                               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f2);
/* 4549 */                                               out.write(10);
/* 4550 */                                               out.write(9);
/* 4551 */                                               out.write(9);
/*      */                                               
/* 4553 */                                               Properties p = resbean.getProperties();
/*      */                                               
/* 4555 */                                               out.write("\n\t\t</td><td width=\"51%\" align=\"center\" valign=\"top\">\n\t\t<br>\n\t\t<table width=\"82%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"grayfullborder\">\n\t\t<tbody>\n\t\t<tr>\n\n\t\t<td height=\"35\" class=\"columnheadingb\" >");
/* 4556 */                                               out.print(FormatUtil.getString("table.heading.attribute"));
/* 4557 */                                               out.write("</td>\n\t\t<td height=\"35\" class=\"columnheadingb\">");
/* 4558 */                                               out.print(FormatUtil.getString("table.heading.value"));
/* 4559 */                                               out.write("</td>\n\t\t<td height=\"35\" class=\"columnheadingb\" >");
/* 4560 */                                               out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 4561 */                                               out.write("</td>\n\n\n\t\t</tr>\n\t\t<tr>\n\t\t<td width=\"40%\" height=\"35\" class=\"whitegrayborderbr\">");
/* 4562 */                                               out.print(FormatUtil.getString("Total Requests"));
/* 4563 */                                               out.write("\n\t\t</td>\n\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborderbr\">\n\t\t");
/*      */                                               
/* 4565 */                                               if (p.containsKey("1XX"))
/*      */                                               {
/*      */ 
/* 4568 */                                                 out.write("\n\t\t\t\t");
/* 4569 */                                                 out.print(formatNumber(p.get("1XX")));
/* 4570 */                                                 out.write("\n\t\t\t\t");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/*      */ 
/* 4576 */                                                 out.write("\n\t\t\t\t-\n\t\t\t\t");
/*      */                                               }
/*      */                                               
/*      */ 
/* 4580 */                                               out.write("\n\t\t</td>\n\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborder\" align=center>-</td>\n\t\t</tr>\n\t\t<tr>\n\t<td width=\"40%\" height=\"35\" class=\"whitegrayborderbr\">");
/* 4581 */                                               out.print(FormatUtil.getString("am.webclient.tomacatdetail.sucessfulrequest"));
/* 4582 */                                               out.write("\n\t\t</td>\n\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborderbr\">\n\t\t");
/*      */                                               
/* 4584 */                                               if (p.containsKey("2XX"))
/*      */                                               {
/*      */ 
/* 4587 */                                                 out.write("\n\t\t\t\t");
/* 4588 */                                                 out.print(formatNumber(p.get("2XX")));
/* 4589 */                                                 out.write("\n\t\t\t\t");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/*      */ 
/* 4595 */                                                 out.write("\n\t\t\t\t-\n\t\t\t\t");
/*      */                                               }
/*      */                                               
/*      */ 
/* 4599 */                                               out.write("\n\t\t</td>\n\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborder\" align=center><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4600 */                                               out.print((String)request.getAttribute("resourceid"));
/* 4601 */                                               out.write("&attributeid=30')\">");
/* 4602 */                                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "30")));
/* 4603 */                                               out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td width=\"40%\" height=\"35\" class=\"whitegrayborderbr\">");
/* 4604 */                                               out.print(FormatUtil.getString("Errors"));
/* 4605 */                                               out.write("\n\t\t</td>\n\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborderbr\">\n\t\t");
/*      */                                               
/* 4607 */                                               if (p.containsKey("4XX"))
/*      */                                               {
/* 4609 */                                                 if (p.containsKey("Error%"))
/*      */                                                 {
/*      */ 
/*      */ 
/* 4613 */                                                   out.write("\n\t\t\t\t");
/* 4614 */                                                   out.print(formatNumber(p.get("4XX")));
/* 4615 */                                                   out.write(" &nbsp;&nbsp;&nbsp;(");
/* 4616 */                                                   out.print(formatNumber(p.get("Error%")));
/* 4617 */                                                   out.write("%)\n\t\t\t\t");
/*      */ 
/*      */                                                 }
/*      */                                                 else
/*      */                                                 {
/*      */ 
/* 4623 */                                                   out.write("\n\t\t\t\t");
/* 4624 */                                                   out.print(formatNumber(p.get("4XX")));
/* 4625 */                                                   out.write("\n\n\t\t\t");
/*      */                                                 }
/*      */                                                 
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 4632 */                                                 out.write("\n\t\t\t\t-\n\t\t\t\t");
/*      */                                               }
/*      */                                               
/*      */ 
/* 4636 */                                               out.write("\n\t\t</td>\n\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborder\" align=center><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4637 */                                               out.print((String)request.getAttribute("resourceid"));
/* 4638 */                                               out.write("&attributeid=31')\">");
/* 4639 */                                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "31")));
/* 4640 */                                               out.write("</a></td>\n\t\t</tr>\n<!--Alert configuration is added at end of the attribute table-->\n\t\t<tr> <td height=\"23\" colspan=\"3\" align=\"right\"  class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4641 */                                               out.print(resourceid);
/* 4642 */                                               out.write("&attributeIDs=30,31&attributeToSelect=30&redirectto=");
/* 4643 */                                               out.print(encodeurl);
/* 4644 */                                               out.write("\" class=\"staticlinks\">");
/* 4645 */                                               out.print(ALERTCONFIG_TEXT);
/* 4646 */                                               out.write("</a>&nbsp;&nbsp;</td>\n        </tr>\n\n\t\t</tbody>\n\t\t</table></td></tr>\n\t\t</table>\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n\n\n<table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"31\" class=\"tableheadingtrans\">");
/* 4647 */                                               out.print(FormatUtil.getString("am.webclient.tomacatdetail.applicationsummary"));
/* 4648 */                                               out.write(" <a name=\"Application Summary\" id=\"Application Summary\"></a></td>\n    <td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\nclass=\"lrborder\">\n  <tr>\n    <td width=\"62%\" height=\"28\"  class=\"columnheading\">");
/* 4649 */                                               out.print(FormatUtil.getString("am.webclient.tomacatdetail.applicationname"));
/* 4650 */                                               out.write("</td>\n\n    <td width=\"10%\" height=\"28\" class=\"columnheading\">");
/* 4651 */                                               out.print(FormatUtil.getString("am.webclient.dotnet.sessions"));
/* 4652 */                                               out.write("\n    <td width=\"10%\" height=\"28\"  class=\"columnheading\" align=\"center\">");
/* 4653 */                                               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4654 */                                               out.write("<br></td>\n\n    <td width=\"18%\" height=\"28\"  class=\"columnheading\">");
/* 4655 */                                               out.print(ALERTCONFIG_TEXT);
/* 4656 */                                               out.write("</td>\n  </tr>\n  ");
/*      */                                               
/*      */ 
/*      */                                               try
/*      */                                               {
/* 4661 */                                                 if (contextdetails.size() == 0)
/*      */                                                 {
/* 4663 */                                                   out.write("\n\t\t\t  <tr>\n\t\t\t  <td colspan=\"6\" class=\"bodytextbold\" align=\"center\"  height=\"28\">");
/* 4664 */                                                   out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 4665 */                                                   out.write(" </td>\n\t\t\t  ");
/*      */ 
/*      */                                                 }
/*      */                                                 else
/*      */                                                 {
/* 4670 */                                                   svalue = null;
/* 4671 */                                                   l = 0;
/* 4672 */                                                   for (e = contextdetails.keys(); e.hasMoreElements();)
/*      */                                                   {
/* 4674 */                                                     String aplname = (String)e.nextElement();
/* 4675 */                                                     Properties appdet = (Properties)contextdetails.get(aplname);
/* 4676 */                                                     if (appdet.size() != 2)
/*      */                                                     {
/* 4678 */                                                       svalue = "-";
/*      */                                                     }
/*      */                                                     else {
/* 4681 */                                                       svalue = appdet.getProperty("session");
/*      */                                                     }
/* 4683 */                                                     String resid = appdet.getProperty("ID");
/*      */                                                     String bgcolor;
/* 4685 */                                                     String bgcolor; if (l % 2 == 0) {
/* 4686 */                                                       bgcolor = "whitegrayborder";
/*      */                                                     }
/*      */                                                     else {
/* 4689 */                                                       bgcolor = "yellowgrayborder";
/*      */                                                     }
/*      */                                                     
/*      */ 
/* 4693 */                                                     encodeurl = URLEncoder.encode(redirect);
/*      */                                                     
/* 4695 */                                                     out.write("\n\t\t\t\t\t  <tr>\n\t\t\t\t\t  <td class=\"");
/* 4696 */                                                     out.print(bgcolor);
/* 4697 */                                                     out.write("\" ><a href=\"/Tomcat.do?name=");
/* 4698 */                                                     out.print(name);
/* 4699 */                                                     out.write("&context=");
/* 4700 */                                                     out.print(aplname);
/* 4701 */                                                     out.write("&haid=");
/* 4702 */                                                     out.print(haid);
/* 4703 */                                                     out.write("&appName=");
/* 4704 */                                                     out.print(appname);
/* 4705 */                                                     out.write("&resourcename=");
/* 4706 */                                                     out.print(displayname);
/* 4707 */                                                     out.write("&contextid=");
/* 4708 */                                                     out.print(resid);
/* 4709 */                                                     out.write("&resourceid=");
/* 4710 */                                                     out.print(resourceid);
/* 4711 */                                                     out.write("\" class=\"resourcename\">\n\t\t\t\t\t  ");
/* 4712 */                                                     out.print(aplname);
/* 4713 */                                                     out.write(" </a></td>\n\n\t\t\t\t\t  <td class=\"");
/* 4714 */                                                     out.print(bgcolor);
/* 4715 */                                                     out.write("\" align=\"center\"> ");
/* 4716 */                                                     out.print(svalue);
/* 4717 */                                                     out.write("</td>\n\n\t\t\t\t\t  <td align=\"center\" class=\"");
/* 4718 */                                                     out.print(bgcolor);
/* 4719 */                                                     out.write("\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4720 */                                                     out.print(resid);
/* 4721 */                                                     out.write("&attributeid=23')\">");
/* 4722 */                                                     out.print(getSeverityImageForHealth(alert.getProperty(resid + "#" + "23")));
/* 4723 */                                                     out.write("\n\t\t\t\t\t  </a> </td>\n\n\t\t\t\t\t  <td width=\"16%\" align=\"center\" class=\"");
/* 4724 */                                                     out.print(bgcolor);
/* 4725 */                                                     out.write("\" > <a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4726 */                                                     out.print(resid);
/* 4727 */                                                     out.write("&attributeIDs=23&attributeToSelect=23&redirectto=");
/* 4728 */                                                     out.print(encodeurl);
/* 4729 */                                                     out.write("#Application Summary\" class=\"bodytextboldwhiteun\"><img src=\"/images/icon_associateaction.gif\"  align=\"absmiddle\" border=\"0\"></a></td>\n\t\t\t\t\t  </tr>\n\t\t\t\t\t  ");
/*      */                                                     
/* 4731 */                                                     l++;
/*      */                                                   } } } catch (Exception e) { String svalue;
/*      */                                                 int l;
/*      */                                                 Enumeration e;
/* 4735 */                                                 e.printStackTrace();
/*      */                                               }
/* 4737 */                                               out.write("</table>");
/*      */                                             }
/*      */                                           }
/*      */                                           
/* 4741 */                                           if (!version.equals("3"))
/*      */                                           {
/*      */ 
/* 4744 */                                             out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"72%\" height=\"31\" align=\"right\" class=\"tablebottom1\"><a href=\"#top\" class=\"staticlinks\">");
/* 4745 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.top"));
/* 4746 */                                             out.write("</a>&nbsp;&nbsp;</td>\n  </tr>\n</table>\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 4750 */                                           out.write(10);
/* 4751 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4752 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4756 */                                       if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4757 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                       }
/*      */                                       
/* 4760 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4761 */                                       out.write(10);
/* 4762 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4763 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4767 */                                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4768 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                   }
/*      */                                   
/* 4771 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4772 */                                   out.write(10);
/*      */                                   
/* 4774 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4775 */                                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 4776 */                                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 4777 */                                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 4778 */                                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                     for (;;) {
/* 4780 */                                       out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td align=\"center\" class=\"bodytextbold11\"><a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 4781 */                                       if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                         return;
/* 4783 */                                       if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                         return;
/* 4785 */                                       out.write("\" class=\"staticlinks\">");
/* 4786 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.gotosnapshot"));
/* 4787 */                                       out.write("</a></td>\n  </tr>\n</table>\n");
/* 4788 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4789 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4793 */                                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4794 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                   }
/*      */                                   
/* 4797 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4798 */                                   out.write(10);
/* 4799 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4800 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4804 */                               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4805 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                               }
/*      */                               
/* 4808 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4809 */                               out.write("\n<br>\n\t");
/* 4810 */                               out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 4811 */                               DialChartSupport dialGraph = null;
/* 4812 */                               dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 4813 */                               if (dialGraph == null) {
/* 4814 */                                 dialGraph = new DialChartSupport();
/* 4815 */                                 _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                               }
/* 4817 */                               out.write(10);
/*      */                               
/*      */                               try
/*      */                               {
/* 4821 */                                 String hostos = (String)systeminfo.get("HOSTOS");
/* 4822 */                                 String hostname = (String)systeminfo.get("HOSTNAME");
/* 4823 */                                 String hostid = (String)systeminfo.get("host_resid");
/* 4824 */                                 boolean isConf = false;
/* 4825 */                                 if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 4826 */                                   isConf = true;
/*      */                                 }
/* 4828 */                                 com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 4829 */                                 Properties property = new Properties();
/* 4830 */                                 if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                                 {
/* 4832 */                                   property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 4833 */                                   if ((property != null) && (property.size() > 0))
/*      */                                   {
/* 4835 */                                     String cpuid = property.getProperty("cpuid");
/* 4836 */                                     String memid = property.getProperty("memid");
/* 4837 */                                     String diskid = property.getProperty("diskid");
/* 4838 */                                     String cpuvalue = property.getProperty("CPU Utilization");
/* 4839 */                                     String memvalue = property.getProperty("Memory Utilization");
/* 4840 */                                     String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 4841 */                                     String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 4842 */                                     String diskvalue = property.getProperty("Disk Utilization");
/* 4843 */                                     String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                     
/* 4845 */                                     if (!isConf) {
/* 4846 */                                       out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 4847 */                                       out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 4848 */                                       out.write(45);
/* 4849 */                                       if (systeminfo.get("host_resid") != null) {
/* 4850 */                                         out.write("<a href=\"showresource.do?resourceid=");
/* 4851 */                                         out.print(hostid);
/* 4852 */                                         out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 4853 */                                         out.print(hostname);
/* 4854 */                                         out.write("</a>");
/* 4855 */                                       } else { out.println(hostname); }
/* 4856 */                                       out.write("</td>\t");
/* 4857 */                                       out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 4858 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4859 */                                       out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                       
/*      */ 
/* 4862 */                                       if (cpuvalue != null)
/*      */                                       {
/*      */ 
/* 4865 */                                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4866 */                                         out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4867 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4868 */                                         out.write(45);
/* 4869 */                                         out.print(cpuvalue);
/* 4870 */                                         out.write(" %'>\n\n");
/*      */                                         
/* 4872 */                                         DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4873 */                                         _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 4874 */                                         _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4876 */                                         _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                         
/* 4878 */                                         _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                         
/* 4880 */                                         _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                         
/* 4882 */                                         _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                         
/* 4884 */                                         _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                         
/* 4886 */                                         _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                         
/* 4888 */                                         _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                         
/* 4890 */                                         _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                         
/* 4892 */                                         _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                         
/* 4894 */                                         _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 4895 */                                         int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 4896 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 4897 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4898 */                                             out = _jspx_page_context.pushBody();
/* 4899 */                                             _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 4900 */                                             _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4903 */                                             out.write(10);
/* 4904 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 4905 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4908 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4909 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4912 */                                         if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 4913 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                         }
/*      */                                         
/* 4916 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 4917 */                                         out.write("\n         </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4921 */                                         out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4922 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4923 */                                         out.write(32);
/* 4924 */                                         out.write(62);
/* 4925 */                                         out.write(10);
/* 4926 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4927 */                                         out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                       }
/* 4929 */                                       out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4930 */                                       if (cpuvalue != null)
/*      */                                       {
/* 4932 */                                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4933 */                                         out.print(hostid);
/* 4934 */                                         out.write("&attributeid=");
/* 4935 */                                         out.print(cpuid);
/* 4936 */                                         out.write("&period=-7')\" class='bodytextbold'>");
/* 4937 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4938 */                                         out.write(32);
/* 4939 */                                         out.write(45);
/* 4940 */                                         out.write(32);
/* 4941 */                                         out.print(cpuvalue);
/* 4942 */                                         out.write("</a> %\n");
/*      */                                       }
/* 4944 */                                       out.write("\n  </td>\n       </tr>\n       </table>");
/* 4945 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4946 */                                       out.write("</td>\n      <td width=\"30%\"> ");
/* 4947 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4948 */                                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                       
/* 4950 */                                       if (memvalue != null)
/*      */                                       {
/*      */ 
/* 4953 */                                         dialGraph.setValue(Long.parseLong(memvalue));
/* 4954 */                                         out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4955 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4956 */                                         out.write(45);
/* 4957 */                                         out.print(memvalue);
/* 4958 */                                         out.write(" %' >\n\n");
/*      */                                         
/* 4960 */                                         DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4961 */                                         _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 4962 */                                         _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4964 */                                         _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                         
/* 4966 */                                         _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                         
/* 4968 */                                         _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                         
/* 4970 */                                         _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                         
/* 4972 */                                         _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                         
/* 4974 */                                         _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                         
/* 4976 */                                         _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                         
/* 4978 */                                         _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                         
/* 4980 */                                         _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                         
/* 4982 */                                         _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 4983 */                                         int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 4984 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 4985 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4986 */                                             out = _jspx_page_context.pushBody();
/* 4987 */                                             _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 4988 */                                             _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4991 */                                             out.write(32);
/* 4992 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 4993 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4996 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4997 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5000 */                                         if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 5001 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                         }
/*      */                                         
/* 5004 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 5005 */                                         out.write(32);
/* 5006 */                                         out.write("\n            </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 5010 */                                         out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5011 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5012 */                                         out.write(" >\n\n");
/* 5013 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5014 */                                         out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                       }
/* 5016 */                                       out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5017 */                                       if (memvalue != null)
/*      */                                       {
/* 5019 */                                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5020 */                                         out.print(hostid);
/* 5021 */                                         out.write("&attributeid=");
/* 5022 */                                         out.print(memid);
/* 5023 */                                         out.write("&period=-7')\" class='bodytextbold'>");
/* 5024 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5025 */                                         out.write(45);
/* 5026 */                                         out.print(memvalue);
/* 5027 */                                         out.write("</a> %\n  ");
/*      */                                       }
/* 5029 */                                       out.write("\n  </td>\n       </tr>\n    </table>");
/* 5030 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5031 */                                       out.write("</td>\n      <td width=\"30%\">");
/* 5032 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5033 */                                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                       
/*      */ 
/* 5036 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                       {
/*      */ 
/*      */ 
/* 5040 */                                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 5041 */                                         out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5042 */                                         out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 5043 */                                         out.write(45);
/* 5044 */                                         out.print(diskvalue);
/* 5045 */                                         out.write("%' >\n");
/*      */                                         
/* 5047 */                                         DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5048 */                                         _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 5049 */                                         _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 5051 */                                         _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                         
/* 5053 */                                         _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                         
/* 5055 */                                         _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                         
/* 5057 */                                         _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                         
/* 5059 */                                         _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                         
/* 5061 */                                         _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                         
/* 5063 */                                         _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                         
/* 5065 */                                         _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                         
/* 5067 */                                         _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                         
/* 5069 */                                         _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 5070 */                                         int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 5071 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 5072 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5073 */                                             out = _jspx_page_context.pushBody();
/* 5074 */                                             _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 5075 */                                             _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5078 */                                             out.write(32);
/* 5079 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 5080 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5083 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5084 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5087 */                                         if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 5088 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                         }
/*      */                                         
/* 5091 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 5092 */                                         out.write(32);
/* 5093 */                                         out.write(32);
/* 5094 */                                         out.write("\n    </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 5098 */                                         out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5099 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5100 */                                         out.write(32);
/* 5101 */                                         out.write(62);
/* 5102 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5103 */                                         out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                       }
/* 5105 */                                       out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 5106 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                       {
/* 5108 */                                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5109 */                                         out.print(hostid);
/* 5110 */                                         out.write("&attributeid=");
/* 5111 */                                         out.print(diskid);
/* 5112 */                                         out.write("&period=-7')\" class='bodytextbold'>");
/* 5113 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5114 */                                         out.write(45);
/* 5115 */                                         out.print(diskvalue);
/* 5116 */                                         out.write("</a> %\n     ");
/*      */                                       }
/* 5118 */                                       out.write("\n  </td>\n  </tr>\n</table>");
/* 5119 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5120 */                                       out.write("</td></tr></table>\n\n");
/*      */                                     } else {
/* 5122 */                                       out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 5123 */                                       out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 5124 */                                       out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 5125 */                                       out.print(systeminfo.get("host_resid"));
/* 5126 */                                       out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5127 */                                       out.print(hostname);
/* 5128 */                                       out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 5129 */                                       if (cpuvalue != null)
/*      */                                       {
/*      */ 
/* 5132 */                                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5133 */                                         out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                         
/* 5135 */                                         DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5136 */                                         _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 5137 */                                         _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 5139 */                                         _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                         
/* 5141 */                                         _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                         
/* 5143 */                                         _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                         
/* 5145 */                                         _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                         
/* 5147 */                                         _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                         
/* 5149 */                                         _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                         
/* 5151 */                                         _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                         
/* 5153 */                                         _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                         
/* 5155 */                                         _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                         
/* 5157 */                                         _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 5158 */                                         int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 5159 */                                         if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 5160 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                         }
/*      */                                         
/* 5163 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 5164 */                                         out.write("\n         </td>\n     ");
/*      */                                       }
/*      */                                       else {
/* 5167 */                                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5168 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5169 */                                         out.write(39);
/* 5170 */                                         out.write(32);
/* 5171 */                                         out.write(62);
/* 5172 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5173 */                                         out.write("\n \t\t</td>\n\t\t");
/*      */                                       }
/* 5175 */                                       if (memvalue != null) {
/* 5176 */                                         dialGraph.setValue(Long.parseLong(memvalue));
/* 5177 */                                         out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                         
/* 5179 */                                         DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5180 */                                         _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 5181 */                                         _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 5183 */                                         _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                         
/* 5185 */                                         _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                         
/* 5187 */                                         _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                         
/* 5189 */                                         _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                         
/* 5191 */                                         _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                         
/* 5193 */                                         _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                         
/* 5195 */                                         _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                         
/* 5197 */                                         _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                         
/* 5199 */                                         _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                         
/* 5201 */                                         _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 5202 */                                         int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 5203 */                                         if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 5204 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                         }
/*      */                                         
/* 5207 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 5208 */                                         out.write("\n            </td>\n         ");
/*      */                                       }
/*      */                                       else {
/* 5211 */                                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5212 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5213 */                                         out.write(39);
/* 5214 */                                         out.write(32);
/* 5215 */                                         out.write(62);
/* 5216 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5217 */                                         out.write("\n \t\t</td>\n\t\t");
/*      */                                       }
/* 5219 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5220 */                                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 5221 */                                         out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                         
/* 5223 */                                         DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5224 */                                         _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 5225 */                                         _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 5227 */                                         _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                         
/* 5229 */                                         _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                         
/* 5231 */                                         _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                         
/* 5233 */                                         _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                         
/* 5235 */                                         _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                         
/* 5237 */                                         _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                         
/* 5239 */                                         _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                         
/* 5241 */                                         _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                         
/* 5243 */                                         _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                         
/* 5245 */                                         _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 5246 */                                         int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 5247 */                                         if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 5248 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                         }
/*      */                                         
/* 5251 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 5252 */                                         out.write(32);
/* 5253 */                                         out.write("\n\t          </td>\n\t  ");
/*      */                                       }
/*      */                                       else {
/* 5256 */                                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5257 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5258 */                                         out.write(39);
/* 5259 */                                         out.write(32);
/* 5260 */                                         out.write(62);
/* 5261 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5262 */                                         out.write("\n \t\t</td>\n\t\t");
/*      */                                       }
/* 5264 */                                       out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5265 */                                       out.print(hostid);
/* 5266 */                                       out.write("&attributeid=");
/* 5267 */                                       out.print(cpuid);
/* 5268 */                                       out.write("&period=-7')\" class='tooltip'>");
/* 5269 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5270 */                                       out.write(32);
/* 5271 */                                       out.write(45);
/* 5272 */                                       out.write(32);
/* 5273 */                                       if (cpuvalue != null) {
/* 5274 */                                         out.print(cpuvalue);
/*      */                                       }
/* 5276 */                                       out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5277 */                                       out.print(hostid);
/* 5278 */                                       out.write("&attributeid=");
/* 5279 */                                       out.print(memid);
/* 5280 */                                       out.write("&period=-7')\" class='tooltip'>");
/* 5281 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5282 */                                       out.write(45);
/* 5283 */                                       if (memvalue != null) {
/* 5284 */                                         out.print(memvalue);
/*      */                                       }
/* 5286 */                                       out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5287 */                                       out.print(hostid);
/* 5288 */                                       out.write("&attributeid=");
/* 5289 */                                       out.print(diskid);
/* 5290 */                                       out.write("&period=-7')\" class='tooltip'>");
/* 5291 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5292 */                                       out.write(45);
/* 5293 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5294 */                                         out.print(diskvalue);
/*      */                                       }
/* 5296 */                                       out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                     }
/* 5298 */                                     out.write(10);
/* 5299 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/*      */                                 }
/*      */                               }
/*      */                               catch (Exception e)
/*      */                               {
/* 5306 */                                 e.printStackTrace();
/*      */                               }
/* 5308 */                               out.write(10);
/* 5309 */                               out.write(10);
/* 5310 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 5311 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 5314 */                             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 5315 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 5318 */                           if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5319 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                           }
/*      */                           
/* 5322 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 5323 */                           out.write(32);
/* 5324 */                           if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 5326 */                           out.write(32);
/* 5327 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5328 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5332 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5333 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                       }
/*      */                       
/* 5336 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5337 */                       out.write("\n</body>\n</html>\n");
/*      */                     } catch (Exception e) {
/* 5339 */                       e.printStackTrace();
/*      */                     }
/* 5341 */                     out.write(10);
/*      */                   }
/* 5343 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5344 */         out = _jspx_out;
/* 5345 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5346 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5347 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5350 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5356 */     PageContext pageContext = _jspx_page_context;
/* 5357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5359 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5360 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5361 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 5363 */     _jspx_th_c_005fset_005f0.setVar("redirect");
/*      */     
/* 5365 */     _jspx_th_c_005fset_005f0.setScope("request");
/* 5366 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5367 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5368 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5369 */         out = _jspx_page_context.pushBody();
/* 5370 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5371 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5374 */         out.write("/showresource.do?method=showResourceForResourceID&resourceid=");
/* 5375 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5376 */           return true;
/* 5377 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5378 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5381 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5382 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5385 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5386 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5387 */       return true;
/*      */     }
/* 5389 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5390 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5395 */     PageContext pageContext = _jspx_page_context;
/* 5396 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5398 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5399 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5400 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5402 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 5403 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5404 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5405 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5406 */       return true;
/*      */     }
/* 5408 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5414 */     PageContext pageContext = _jspx_page_context;
/* 5415 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5417 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 5418 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 5419 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 5421 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 5422 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 5424 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 5425 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 5427 */           out.write("\n\t    ");
/* 5428 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 5429 */             return true;
/* 5430 */           out.write(10);
/* 5431 */           out.write(9);
/* 5432 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 5433 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5437 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 5438 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5441 */         int tmp184_183 = 0; int[] tmp184_181 = _jspx_push_body_count_c_005fcatch_005f0; int tmp186_185 = tmp184_181[tmp184_183];tmp184_181[tmp184_183] = (tmp186_185 - 1); if (tmp186_185 <= 0) break;
/* 5442 */         out = _jspx_page_context.popBody(); }
/* 5443 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5445 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 5446 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 5448 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 5453 */     PageContext pageContext = _jspx_page_context;
/* 5454 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5456 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 5457 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 5458 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 5460 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 5462 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 5463 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 5464 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 5465 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5466 */       return true;
/*      */     }
/* 5468 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5469 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5474 */     PageContext pageContext = _jspx_page_context;
/* 5475 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5477 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5478 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 5479 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5481 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 5483 */     _jspx_th_tiles_005fput_005f0.setValue("Tomcat Server - Snapshot");
/* 5484 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 5485 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 5486 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5487 */       return true;
/*      */     }
/* 5489 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5495 */     PageContext pageContext = _jspx_page_context;
/* 5496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5498 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5499 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5500 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5502 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 5503 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5504 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5506 */         out.write(10);
/* 5507 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 5508 */           return true;
/* 5509 */         out.write(10);
/* 5510 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5511 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5515 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5516 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5517 */       return true;
/*      */     }
/* 5519 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5520 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5525 */     PageContext pageContext = _jspx_page_context;
/* 5526 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5528 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5529 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5530 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5532 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5534 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 5535 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5536 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5537 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5538 */       return true;
/*      */     }
/* 5540 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5546 */     PageContext pageContext = _jspx_page_context;
/* 5547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5549 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5550 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5551 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5553 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 5554 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5555 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5557 */         out.write(10);
/* 5558 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 5559 */           return true;
/* 5560 */         out.write(10);
/* 5561 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5562 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5566 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5567 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5568 */       return true;
/*      */     }
/* 5570 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5571 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5576 */     PageContext pageContext = _jspx_page_context;
/* 5577 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5579 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5580 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 5581 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5583 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 5585 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 5586 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 5587 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5588 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5589 */       return true;
/*      */     }
/* 5591 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5597 */     PageContext pageContext = _jspx_page_context;
/* 5598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5600 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5601 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 5602 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5604 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 5606 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/TomcatLeftPage.jsp");
/* 5607 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 5608 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 5609 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 5610 */       return true;
/*      */     }
/* 5612 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 5613 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ferrors_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5618 */     PageContext pageContext = _jspx_page_context;
/* 5619 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5621 */     org.apache.struts.taglib.html.ErrorsTag _jspx_th_html_005ferrors_005f0 = (org.apache.struts.taglib.html.ErrorsTag)this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.get(org.apache.struts.taglib.html.ErrorsTag.class);
/* 5622 */     _jspx_th_html_005ferrors_005f0.setPageContext(_jspx_page_context);
/* 5623 */     _jspx_th_html_005ferrors_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5624 */     int _jspx_eval_html_005ferrors_005f0 = _jspx_th_html_005ferrors_005f0.doStartTag();
/* 5625 */     if (_jspx_th_html_005ferrors_005f0.doEndTag() == 5) {
/* 5626 */       this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 5627 */       return true;
/*      */     }
/* 5629 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 5630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5635 */     PageContext pageContext = _jspx_page_context;
/* 5636 */     JspWriter out = _jspx_page_context.getOut();
/* 5637 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5638 */     javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5640 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5641 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 5642 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5644 */     _jspx_th_c_005fif_005f5.setTest("${!empty param.showconfigdiv && param.showconfigdiv=='true'}");
/* 5645 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 5646 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 5648 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: block\">\n\n\n");
/* 5649 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/TomcatConfig.jsp?reconfigure=true", out, false);
/* 5650 */         out.write("\n\n<br>\n</div>\n");
/* 5651 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5652 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5656 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5657 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5658 */       return true;
/*      */     }
/* 5660 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5666 */     PageContext pageContext = _jspx_page_context;
/* 5667 */     JspWriter out = _jspx_page_context.getOut();
/* 5668 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5669 */     javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5671 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5672 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 5673 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5675 */     _jspx_th_c_005fif_005f6.setTest("${empty param.showconfigdiv && param.showconfigdiv!='true'}");
/* 5676 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 5677 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 5679 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: none\">\n\n\n");
/* 5680 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/TomcatConfig.jsp?reconfigure=true", out, false);
/* 5681 */         out.write("\n\n<br>\n</div>\n");
/* 5682 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5683 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5687 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5688 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5689 */       return true;
/*      */     }
/* 5691 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5692 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5697 */     PageContext pageContext = _jspx_page_context;
/* 5698 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5700 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5701 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5702 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5704 */     _jspx_th_c_005fif_005f7.setTest("${not empty param.haid}");
/* 5705 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5706 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5708 */         out.write(10);
/* 5709 */         out.write(9);
/* 5710 */         out.write(9);
/* 5711 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 5712 */           return true;
/* 5713 */         out.write(10);
/* 5714 */         out.write(9);
/* 5715 */         out.write(9);
/* 5716 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5717 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5721 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5722 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5723 */       return true;
/*      */     }
/* 5725 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5726 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5731 */     PageContext pageContext = _jspx_page_context;
/* 5732 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5734 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5735 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5736 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5738 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 5740 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 5741 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5742 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 5743 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5744 */         out = _jspx_page_context.pushBody();
/* 5745 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 5746 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5749 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 5750 */           return true;
/* 5751 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 5752 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5755 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5756 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5759 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5760 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5761 */       return true;
/*      */     }
/* 5763 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5769 */     PageContext pageContext = _jspx_page_context;
/* 5770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5772 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5773 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5774 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 5776 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 5777 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5778 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5779 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5780 */       return true;
/*      */     }
/* 5782 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5783 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5788 */     PageContext pageContext = _jspx_page_context;
/* 5789 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5791 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5792 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 5793 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5795 */     _jspx_th_c_005fif_005f8.setTest("${not empty param.resourceid}");
/* 5796 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 5797 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 5799 */         out.write(10);
/* 5800 */         out.write(9);
/* 5801 */         out.write(9);
/* 5802 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 5803 */           return true;
/* 5804 */         out.write(10);
/* 5805 */         out.write(9);
/* 5806 */         out.write(9);
/* 5807 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 5808 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5812 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 5813 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 5814 */       return true;
/*      */     }
/* 5816 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 5817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5822 */     PageContext pageContext = _jspx_page_context;
/* 5823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5825 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5826 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5827 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 5829 */     _jspx_th_c_005fset_005f2.setVar("myfield_paramresid");
/*      */     
/* 5831 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 5832 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5833 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5834 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5835 */         out = _jspx_page_context.pushBody();
/* 5836 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5837 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5840 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5841 */           return true;
/* 5842 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5843 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5846 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5847 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5850 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5851 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5852 */       return true;
/*      */     }
/* 5854 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5855 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5860 */     PageContext pageContext = _jspx_page_context;
/* 5861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5863 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5864 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5865 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5867 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5868 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5869 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5870 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5871 */       return true;
/*      */     }
/* 5873 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5874 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5879 */     PageContext pageContext = _jspx_page_context;
/* 5880 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5882 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5883 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5884 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5886 */     _jspx_th_c_005fout_005f3.setValue("${myfield_paramresid}");
/* 5887 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5888 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5889 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5890 */       return true;
/*      */     }
/* 5892 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5893 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5898 */     PageContext pageContext = _jspx_page_context;
/* 5899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5901 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5902 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 5903 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5905 */     _jspx_th_c_005fif_005f9.setTest("${not empty param.haid}");
/* 5906 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 5907 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 5909 */         out.write(10);
/* 5910 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 5911 */           return true;
/* 5912 */         out.write(10);
/* 5913 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 5914 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5918 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 5919 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5920 */       return true;
/*      */     }
/* 5922 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5928 */     PageContext pageContext = _jspx_page_context;
/* 5929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5931 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5932 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5933 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 5935 */     _jspx_th_c_005fset_005f3.setVar("myfield_resid");
/*      */     
/* 5937 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 5938 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5939 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 5940 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5941 */         out = _jspx_page_context.pushBody();
/* 5942 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 5943 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5946 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 5947 */           return true;
/* 5948 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 5949 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5952 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5953 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5956 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5957 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5958 */       return true;
/*      */     }
/* 5960 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5961 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5966 */     PageContext pageContext = _jspx_page_context;
/* 5967 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5969 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5970 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5971 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 5973 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 5974 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5975 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5976 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5977 */       return true;
/*      */     }
/* 5979 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5980 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5985 */     PageContext pageContext = _jspx_page_context;
/* 5986 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5988 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5989 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 5990 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5992 */     _jspx_th_c_005fif_005f10.setTest("${not empty param.resourceid}");
/* 5993 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 5994 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 5996 */         out.write(10);
/* 5997 */         if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 5998 */           return true;
/* 5999 */         out.write(10);
/* 6000 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 6001 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6005 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 6006 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6007 */       return true;
/*      */     }
/* 6009 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6015 */     PageContext pageContext = _jspx_page_context;
/* 6016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6018 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6019 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 6020 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 6022 */     _jspx_th_c_005fset_005f4.setVar("myfield_resid");
/*      */     
/* 6024 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 6025 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 6026 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 6027 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6028 */         out = _jspx_page_context.pushBody();
/* 6029 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 6030 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6033 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 6034 */           return true;
/* 6035 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 6036 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6039 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6040 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6043 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 6044 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 6045 */       return true;
/*      */     }
/* 6047 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 6048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6053 */     PageContext pageContext = _jspx_page_context;
/* 6054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6056 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6057 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6058 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 6060 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 6061 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6062 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6063 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6064 */       return true;
/*      */     }
/* 6066 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6072 */     PageContext pageContext = _jspx_page_context;
/* 6073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6075 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6076 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 6077 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6079 */     _jspx_th_c_005fset_005f5.setVar("trstripclass");
/*      */     
/* 6081 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 6082 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 6083 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 6084 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6085 */         out = _jspx_page_context.pushBody();
/* 6086 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 6087 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6090 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 6091 */           return true;
/* 6092 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 6093 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6096 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6097 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6100 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 6101 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 6102 */       return true;
/*      */     }
/* 6104 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 6105 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6110 */     PageContext pageContext = _jspx_page_context;
/* 6111 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6113 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6114 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6115 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 6117 */     _jspx_th_c_005fout_005f6.setValue("");
/* 6118 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6119 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6120 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6121 */       return true;
/*      */     }
/* 6123 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6124 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6129 */     PageContext pageContext = _jspx_page_context;
/* 6130 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6132 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6133 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 6134 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6136 */     _jspx_th_c_005fset_005f6.setVar("myfield_entity");
/*      */     
/* 6138 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 6139 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 6140 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 6141 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6142 */         out = _jspx_page_context.pushBody();
/* 6143 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 6144 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6147 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 6148 */           return true;
/* 6149 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 6150 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6153 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6154 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6157 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 6158 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 6159 */       return true;
/*      */     }
/* 6161 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 6162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6167 */     PageContext pageContext = _jspx_page_context;
/* 6168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6170 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6171 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 6172 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 6174 */     _jspx_th_c_005fout_005f7.setValue("noalarms");
/* 6175 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 6176 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 6177 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6178 */       return true;
/*      */     }
/* 6180 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6186 */     PageContext pageContext = _jspx_page_context;
/* 6187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6189 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6190 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 6191 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6193 */     _jspx_th_c_005fif_005f11.setTest("${not empty param.entity}");
/* 6194 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 6195 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 6197 */         out.write(10);
/* 6198 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 6199 */           return true;
/* 6200 */         out.write(10);
/* 6201 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 6202 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6206 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 6207 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 6208 */       return true;
/*      */     }
/* 6210 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 6211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6216 */     PageContext pageContext = _jspx_page_context;
/* 6217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6219 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6220 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 6221 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 6223 */     _jspx_th_c_005fset_005f7.setVar("myfield_entity");
/*      */     
/* 6225 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 6226 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 6227 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 6228 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6229 */         out = _jspx_page_context.pushBody();
/* 6230 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 6231 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6234 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 6235 */           return true;
/* 6236 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 6237 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6240 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6241 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6244 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 6245 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6246 */       return true;
/*      */     }
/* 6248 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6249 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6254 */     PageContext pageContext = _jspx_page_context;
/* 6255 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6257 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6258 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6259 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 6261 */     _jspx_th_c_005fout_005f8.setValue("${param.entity}");
/* 6262 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6263 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6264 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6265 */       return true;
/*      */     }
/* 6267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6273 */     PageContext pageContext = _jspx_page_context;
/* 6274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6276 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6277 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 6278 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6280 */     _jspx_th_c_005fif_005f12.setTest("${not empty param.includeClass}");
/* 6281 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 6282 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 6284 */         out.write(10);
/* 6285 */         if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 6286 */           return true;
/* 6287 */         out.write(10);
/* 6288 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 6289 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6293 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 6294 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 6295 */       return true;
/*      */     }
/* 6297 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 6298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6303 */     PageContext pageContext = _jspx_page_context;
/* 6304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6306 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6307 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 6308 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 6310 */     _jspx_th_c_005fset_005f8.setVar("trstripclass");
/*      */     
/* 6312 */     _jspx_th_c_005fset_005f8.setScope("page");
/* 6313 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 6314 */     if (_jspx_eval_c_005fset_005f8 != 0) {
/* 6315 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6316 */         out = _jspx_page_context.pushBody();
/* 6317 */         _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 6318 */         _jspx_th_c_005fset_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6321 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fset_005f8, _jspx_page_context))
/* 6322 */           return true;
/* 6323 */         int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 6324 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6327 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6328 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6331 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 6332 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 6333 */       return true;
/*      */     }
/* 6335 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 6336 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fset_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6341 */     PageContext pageContext = _jspx_page_context;
/* 6342 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6344 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6345 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6346 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fset_005f8);
/*      */     
/* 6348 */     _jspx_th_c_005fout_005f9.setValue("${param.includeClass}");
/* 6349 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6350 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6351 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6352 */       return true;
/*      */     }
/* 6354 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6355 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6360 */     PageContext pageContext = _jspx_page_context;
/* 6361 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6363 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6364 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6365 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6367 */     _jspx_th_c_005fout_005f10.setValue("${trstripclass}");
/* 6368 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6369 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6370 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6371 */       return true;
/*      */     }
/* 6373 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6379 */     PageContext pageContext = _jspx_page_context;
/* 6380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6382 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6383 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6384 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 6385 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6386 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 6387 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6388 */         out = _jspx_page_context.pushBody();
/* 6389 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 6390 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6393 */         out.write("am.myfield.customfield.text");
/* 6394 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 6395 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6398 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6399 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6402 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6403 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6404 */       return true;
/*      */     }
/* 6406 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6412 */     PageContext pageContext = _jspx_page_context;
/* 6413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6415 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6416 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6417 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6419 */     _jspx_th_c_005fout_005f11.setValue("${myfield_resid}");
/* 6420 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6421 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6422 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6423 */       return true;
/*      */     }
/* 6425 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6431 */     PageContext pageContext = _jspx_page_context;
/* 6432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6434 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6435 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6436 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6438 */     _jspx_th_c_005fout_005f12.setValue("${myfield_entity}");
/* 6439 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6440 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6441 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6442 */       return true;
/*      */     }
/* 6444 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6450 */     PageContext pageContext = _jspx_page_context;
/* 6451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6453 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6454 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6455 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6457 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 6458 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6459 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6460 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6461 */       return true;
/*      */     }
/* 6463 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6464 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6469 */     PageContext pageContext = _jspx_page_context;
/* 6470 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6472 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6473 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6474 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6476 */     _jspx_th_c_005fout_005f14.setValue("${param.resourcename}");
/* 6477 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6478 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6479 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6480 */       return true;
/*      */     }
/* 6482 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6488 */     PageContext pageContext = _jspx_page_context;
/* 6489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6491 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6492 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6493 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6495 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 6496 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6497 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6498 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6499 */       return true;
/*      */     }
/* 6501 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6502 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6507 */     PageContext pageContext = _jspx_page_context;
/* 6508 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6510 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6511 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6512 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6514 */     _jspx_th_c_005fout_005f16.setValue("${param.resourcename}");
/* 6515 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6516 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6517 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6518 */       return true;
/*      */     }
/* 6520 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6526 */     PageContext pageContext = _jspx_page_context;
/* 6527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6529 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6530 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6531 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 6533 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 6534 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6535 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6536 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6537 */       return true;
/*      */     }
/* 6539 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6540 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6545 */     PageContext pageContext = _jspx_page_context;
/* 6546 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6548 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6549 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6550 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 6552 */     _jspx_th_c_005fout_005f18.setValue("${param.resourcename}");
/* 6553 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6554 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6555 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6556 */       return true;
/*      */     }
/* 6558 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6564 */     PageContext pageContext = _jspx_page_context;
/* 6565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6567 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6568 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6569 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 6571 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 6572 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6573 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6574 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6575 */       return true;
/*      */     }
/* 6577 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6578 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6583 */     PageContext pageContext = _jspx_page_context;
/* 6584 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6586 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6587 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6588 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 6590 */     _jspx_th_c_005fout_005f20.setValue("${param.resourcename}");
/* 6591 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6592 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6593 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6594 */       return true;
/*      */     }
/* 6596 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6602 */     PageContext pageContext = _jspx_page_context;
/* 6603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6605 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6606 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 6607 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6608 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 6609 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 6610 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 6611 */         out = _jspx_page_context.pushBody();
/* 6612 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 6613 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6616 */         out.write("table.heading.attribute");
/* 6617 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 6618 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6621 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 6622 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6625 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 6626 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6627 */       return true;
/*      */     }
/* 6629 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6635 */     PageContext pageContext = _jspx_page_context;
/* 6636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6638 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6639 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 6640 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6641 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 6642 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 6643 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 6644 */         out = _jspx_page_context.pushBody();
/* 6645 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 6646 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6649 */         out.write("table.heading.value");
/* 6650 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 6651 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6654 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 6655 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6658 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 6659 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6660 */       return true;
/*      */     }
/* 6662 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6663 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6668 */     PageContext pageContext = _jspx_page_context;
/* 6669 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6671 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6672 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 6673 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f14);
/* 6674 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 6675 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 6676 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 6677 */         out = _jspx_page_context.pushBody();
/* 6678 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 6679 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6682 */         out.write("table.heading.status");
/* 6683 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 6684 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6687 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 6688 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6691 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 6692 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6693 */       return true;
/*      */     }
/* 6695 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6701 */     PageContext pageContext = _jspx_page_context;
/* 6702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6704 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6705 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6706 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6708 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 6709 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6710 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6711 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6712 */       return true;
/*      */     }
/* 6714 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6715 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6720 */     PageContext pageContext = _jspx_page_context;
/* 6721 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6723 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6724 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6725 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6727 */     _jspx_th_c_005fout_005f22.setValue("${param.resourcename}");
/* 6728 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6729 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6730 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6731 */       return true;
/*      */     }
/* 6733 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6734 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6739 */     PageContext pageContext = _jspx_page_context;
/* 6740 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6742 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6743 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 6744 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6746 */     _jspx_th_c_005fout_005f23.setValue("${param.resourceid}");
/* 6747 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 6748 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 6749 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6750 */       return true;
/*      */     }
/* 6752 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6753 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6758 */     PageContext pageContext = _jspx_page_context;
/* 6759 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6761 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6762 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6763 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6765 */     _jspx_th_c_005fout_005f24.setValue("${param.resourcename}");
/* 6766 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6767 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6768 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6769 */       return true;
/*      */     }
/* 6771 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6772 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6777 */     PageContext pageContext = _jspx_page_context;
/* 6778 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6780 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6781 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 6782 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6784 */     _jspx_th_c_005fout_005f25.setValue("${param.resourceid}");
/* 6785 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 6786 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 6787 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6788 */       return true;
/*      */     }
/* 6790 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6791 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6796 */     PageContext pageContext = _jspx_page_context;
/* 6797 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6799 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6800 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 6801 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6803 */     _jspx_th_c_005fout_005f26.setValue("${param.resourcename}");
/* 6804 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 6805 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 6806 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6807 */       return true;
/*      */     }
/* 6809 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6810 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6815 */     PageContext pageContext = _jspx_page_context;
/* 6816 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6818 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6819 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 6820 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6822 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 6823 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 6824 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 6825 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6826 */       return true;
/*      */     }
/* 6828 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6829 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6834 */     PageContext pageContext = _jspx_page_context;
/* 6835 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6837 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6838 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 6839 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6841 */     _jspx_th_c_005fout_005f28.setValue("${param.resourcename}");
/* 6842 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 6843 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 6844 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6845 */       return true;
/*      */     }
/* 6847 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6853 */     PageContext pageContext = _jspx_page_context;
/* 6854 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6856 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6857 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 6858 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f15);
/* 6859 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 6860 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 6861 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 6862 */         out = _jspx_page_context.pushBody();
/* 6863 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 6864 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6867 */         out.write("table.heading.attribute");
/* 6868 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 6869 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6872 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 6873 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6876 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6877 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6878 */       return true;
/*      */     }
/* 6880 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6881 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6886 */     PageContext pageContext = _jspx_page_context;
/* 6887 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6889 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6890 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 6891 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f15);
/* 6892 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 6893 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 6894 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6895 */         out = _jspx_page_context.pushBody();
/* 6896 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 6897 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6900 */         out.write("table.heading.value");
/* 6901 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 6902 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6905 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6906 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6909 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 6910 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6911 */       return true;
/*      */     }
/* 6913 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6914 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6919 */     PageContext pageContext = _jspx_page_context;
/* 6920 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6922 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6923 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 6924 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f15);
/* 6925 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 6926 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 6927 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6928 */         out = _jspx_page_context.pushBody();
/* 6929 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 6930 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6933 */         out.write("table.heading.status");
/* 6934 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 6935 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6938 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6939 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6942 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 6943 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6944 */       return true;
/*      */     }
/* 6946 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6952 */     PageContext pageContext = _jspx_page_context;
/* 6953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6955 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6956 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 6957 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f15);
/* 6958 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 6959 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 6960 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6961 */         out = _jspx_page_context.pushBody();
/* 6962 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 6963 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6966 */         out.write("table.heading.attribute");
/* 6967 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 6968 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6971 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6972 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6975 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 6976 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6977 */       return true;
/*      */     }
/* 6979 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6980 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6985 */     PageContext pageContext = _jspx_page_context;
/* 6986 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6988 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6989 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 6990 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fif_005f15);
/* 6991 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 6992 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 6993 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6994 */         out = _jspx_page_context.pushBody();
/* 6995 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 6996 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6999 */         out.write("table.heading.value");
/* 7000 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 7001 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7004 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 7005 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7008 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 7009 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 7010 */       return true;
/*      */     }
/* 7012 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 7013 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7018 */     PageContext pageContext = _jspx_page_context;
/* 7019 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7021 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7022 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 7023 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fif_005f15);
/* 7024 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 7025 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 7026 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 7027 */         out = _jspx_page_context.pushBody();
/* 7028 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 7029 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7032 */         out.write("table.heading.status");
/* 7033 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 7034 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7037 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 7038 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7041 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 7042 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 7043 */       return true;
/*      */     }
/* 7045 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 7046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7051 */     PageContext pageContext = _jspx_page_context;
/* 7052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7054 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7055 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 7056 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 7058 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 7059 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 7060 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 7061 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7062 */       return true;
/*      */     }
/* 7064 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7065 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7070 */     PageContext pageContext = _jspx_page_context;
/* 7071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7073 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7074 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 7075 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 7077 */     _jspx_th_c_005fout_005f30.setValue("${param.resourcename}");
/* 7078 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 7079 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 7080 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7081 */       return true;
/*      */     }
/* 7083 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7084 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7089 */     PageContext pageContext = _jspx_page_context;
/* 7090 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7092 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7093 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 7094 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 7096 */     _jspx_th_c_005fout_005f31.setValue("${param.resourceid}");
/* 7097 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 7098 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 7099 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7100 */       return true;
/*      */     }
/* 7102 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7103 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7108 */     PageContext pageContext = _jspx_page_context;
/* 7109 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7111 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7112 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 7113 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 7115 */     _jspx_th_c_005fout_005f32.setValue("${param.resourcename}");
/* 7116 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 7117 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 7118 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7119 */       return true;
/*      */     }
/* 7121 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005ftimechart_005f3(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7127 */     PageContext pageContext = _jspx_page_context;
/* 7128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7130 */     TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 7131 */     _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 7132 */     _jspx_th_awolf_005ftimechart_005f3.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 7134 */     _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("membean");
/*      */     
/* 7136 */     _jspx_th_awolf_005ftimechart_005f3.setWidth("300");
/*      */     
/* 7138 */     _jspx_th_awolf_005ftimechart_005f3.setHeight("120");
/*      */     
/* 7140 */     _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */     
/* 7142 */     _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel("Time");
/*      */     
/* 7144 */     _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel("Value in KB");
/* 7145 */     int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 7146 */     if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 7147 */       if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 7148 */         out = _jspx_page_context.pushBody();
/* 7149 */         _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 7150 */         _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7153 */         out.write("\n      ");
/* 7154 */         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 7155 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7158 */       if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 7159 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7162 */     if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 7163 */       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 7164 */       return true;
/*      */     }
/* 7166 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 7167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7172 */     PageContext pageContext = _jspx_page_context;
/* 7173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7175 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7176 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 7177 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 7179 */     _jspx_th_c_005fout_005f33.setValue("${param.resourceid}");
/* 7180 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 7181 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 7182 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7183 */       return true;
/*      */     }
/* 7185 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7191 */     PageContext pageContext = _jspx_page_context;
/* 7192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7194 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7195 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 7196 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 7198 */     _jspx_th_c_005fif_005f16.setTest("${ !empty param.haid && param.haid!='null' }");
/* 7199 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 7200 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 7202 */         out.write("&haid=");
/* 7203 */         if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 7204 */           return true;
/* 7205 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 7206 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7210 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 7211 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 7212 */       return true;
/*      */     }
/* 7214 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 7215 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7220 */     PageContext pageContext = _jspx_page_context;
/* 7221 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7223 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7224 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 7225 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7227 */     _jspx_th_c_005fout_005f34.setValue("${param.haid}");
/* 7228 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 7229 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 7230 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7231 */       return true;
/*      */     }
/* 7233 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7239 */     PageContext pageContext = _jspx_page_context;
/* 7240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7242 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 7243 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 7244 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 7246 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 7248 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 7249 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 7250 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 7251 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 7252 */       return true;
/*      */     }
/* 7254 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 7255 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\TomcatDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */