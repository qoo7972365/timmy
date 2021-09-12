/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.server.sybase.bean.SybaseGraphs;
/*      */ import com.adventnet.appmanager.tags.Truncate;
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
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class SybaseDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   54 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   57 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   58 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   59 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   66 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   71 */     ArrayList list = null;
/*   72 */     StringBuffer sbf = new StringBuffer();
/*   73 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/*   74 */     if (distinct)
/*      */     {
/*   76 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   80 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   83 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   85 */       ArrayList row = (ArrayList)list.get(i);
/*   86 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   87 */       if (distinct) {
/*   88 */         sbf.append(row.get(0));
/*      */       } else
/*   90 */         sbf.append(row.get(1));
/*   91 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   94 */     return sbf.toString(); }
/*      */   
/*   96 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   99 */     if (severity == null)
/*      */     {
/*  101 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  103 */     if (severity.equals("5"))
/*      */     {
/*  105 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  107 */     if (severity.equals("1"))
/*      */     {
/*  109 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  114 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  121 */     if (severity == null)
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  125 */     if (severity.equals("1"))
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  129 */     if (severity.equals("4"))
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  133 */     if (severity.equals("5"))
/*      */     {
/*  135 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  140 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  146 */     if (severity == null)
/*      */     {
/*  148 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  150 */     if (severity.equals("5"))
/*      */     {
/*  152 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  154 */     if (severity.equals("1"))
/*      */     {
/*  156 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  160 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  166 */     if (severity == null)
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  170 */     if (severity.equals("1"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  174 */     if (severity.equals("4"))
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  178 */     if (severity.equals("5"))
/*      */     {
/*  180 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  184 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  190 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  196 */     if (severity == 5)
/*      */     {
/*  198 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  200 */     if (severity == 1)
/*      */     {
/*  202 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  207 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  213 */     if (severity == null)
/*      */     {
/*  215 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  217 */     if (severity.equals("5"))
/*      */     {
/*  219 */       if (isAvailability) {
/*  220 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  223 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  226 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  228 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  230 */     if (severity.equals("1"))
/*      */     {
/*  232 */       if (isAvailability) {
/*  233 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  236 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  243 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  250 */     if (severity == null)
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  254 */     if (severity.equals("5"))
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  258 */     if (severity.equals("4"))
/*      */     {
/*  260 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  262 */     if (severity.equals("1"))
/*      */     {
/*  264 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  269 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  275 */     if (severity == null)
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("5"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("4"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  287 */     if (severity.equals("1"))
/*      */     {
/*  289 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  294 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  301 */     if (severity == null)
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  305 */     if (severity.equals("5"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  309 */     if (severity.equals("4"))
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  313 */     if (severity.equals("1"))
/*      */     {
/*  315 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  320 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  328 */     StringBuffer out = new StringBuffer();
/*  329 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  330 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  331 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  332 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  333 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  334 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  335 */     out.append("</tr>");
/*  336 */     out.append("</form></table>");
/*  337 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  344 */     if (val == null)
/*      */     {
/*  346 */       return "-";
/*      */     }
/*      */     
/*  349 */     String ret = FormatUtil.formatNumber(val);
/*  350 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  351 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  354 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  358 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  366 */     StringBuffer out = new StringBuffer();
/*  367 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  368 */     out.append("<tr>");
/*  369 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  371 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  373 */     out.append("</tr>");
/*  374 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  378 */       if (j % 2 == 0)
/*      */       {
/*  380 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  384 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  387 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  389 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  392 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  396 */       out.append("</tr>");
/*      */     }
/*  398 */     out.append("</table>");
/*  399 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  400 */     out.append("<tr>");
/*  401 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  402 */     out.append("</tr>");
/*  403 */     out.append("</table>");
/*  404 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
/*      */   {
/*  410 */     StringBuffer out = new StringBuffer();
/*  411 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  412 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  413 */     out.append("<tr>");
/*  414 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  415 */     out.append("<tr>");
/*  416 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  417 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  418 */     out.append("</tr>");
/*  419 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  422 */       out.append("<tr>");
/*  423 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  424 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  425 */       out.append("</tr>");
/*      */     }
/*      */     
/*  428 */     out.append("</table>");
/*  429 */     out.append("</table>");
/*  430 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  435 */     if (severity.equals("0"))
/*      */     {
/*  437 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  441 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, javax.servlet.http.HttpSession session)
/*      */   {
/*  448 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  461 */     StringBuffer out = new StringBuffer();
/*  462 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  463 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  465 */       out.append("<tr>");
/*  466 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  467 */       out.append("</tr>");
/*      */       
/*      */ 
/*  470 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  472 */         String borderclass = "";
/*      */         
/*      */ 
/*  475 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  477 */         out.append("<tr>");
/*      */         
/*  479 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  480 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  481 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  487 */     out.append("</table><br>");
/*  488 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  489 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  491 */       List sLinks = secondLevelOfLinks[0];
/*  492 */       List sText = secondLevelOfLinks[1];
/*  493 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  496 */         out.append("<tr>");
/*  497 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  498 */         out.append("</tr>");
/*  499 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  501 */           String borderclass = "";
/*      */           
/*      */ 
/*  504 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  506 */           out.append("<tr>");
/*      */           
/*  508 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  509 */           if (sLinks.get(i).toString().length() == 0) {
/*  510 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  513 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  515 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  519 */     out.append("</table>");
/*  520 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session, HttpServletRequest request)
/*      */   {
/*  527 */     StringBuffer out = new StringBuffer();
/*  528 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  529 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  531 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  533 */         out.append("<tr>");
/*  534 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  535 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  539 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  541 */           String borderclass = "";
/*      */           
/*      */ 
/*  544 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  546 */           out.append("<tr>");
/*      */           
/*  548 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  549 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  550 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  553 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  556 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  561 */     out.append("</table><br>");
/*  562 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  563 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  565 */       List sLinks = secondLevelOfLinks[0];
/*  566 */       List sText = secondLevelOfLinks[1];
/*  567 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  570 */         out.append("<tr>");
/*  571 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  572 */         out.append("</tr>");
/*  573 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  575 */           String borderclass = "";
/*      */           
/*      */ 
/*  578 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  580 */           out.append("<tr>");
/*      */           
/*  582 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  583 */           if (sLinks.get(i).toString().length() == 0) {
/*  584 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  587 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  589 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  593 */     out.append("</table>");
/*  594 */     return out.toString();
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
/*  607 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  622 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  625 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  628 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  636 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  641 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  646 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  651 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  656 */     if (val != null)
/*      */     {
/*  658 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  662 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  667 */     if (val == null) {
/*  668 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  672 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  677 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  683 */     if (val != null)
/*      */     {
/*  685 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  689 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  695 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  700 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  704 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  709 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  714 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  719 */     String hostaddress = "";
/*  720 */     String ip = request.getHeader("x-forwarded-for");
/*  721 */     if (ip == null)
/*  722 */       ip = request.getRemoteAddr();
/*  723 */     java.net.InetAddress add = null;
/*  724 */     if (ip.equals("127.0.0.1")) {
/*  725 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  729 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  731 */     hostaddress = add.getHostName();
/*  732 */     if (hostaddress.indexOf('.') != -1) {
/*  733 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  734 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  738 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  743 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  749 */     if (severity == null)
/*      */     {
/*  751 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  753 */     if (severity.equals("5"))
/*      */     {
/*  755 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  757 */     if (severity.equals("1"))
/*      */     {
/*  759 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  764 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  769 */     ResultSet set = null;
/*  770 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  771 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  773 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  774 */       if (set.next()) { String str1;
/*  775 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  776 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  779 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  784 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  787 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  789 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  793 */     StringBuffer rca = new StringBuffer();
/*  794 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  795 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  798 */     int rcalength = key.length();
/*  799 */     String split = "6. ";
/*  800 */     int splitPresent = key.indexOf(split);
/*  801 */     String div1 = "";String div2 = "";
/*  802 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  804 */       if (rcalength > 180) {
/*  805 */         rca.append("<span class=\"rca-critical-text\">");
/*  806 */         getRCATrimmedText(key, rca);
/*  807 */         rca.append("</span>");
/*      */       } else {
/*  809 */         rca.append("<span class=\"rca-critical-text\">");
/*  810 */         rca.append(key);
/*  811 */         rca.append("</span>");
/*      */       }
/*  813 */       return rca.toString();
/*      */     }
/*  815 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  816 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  817 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  818 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  819 */     getRCATrimmedText(div1, rca);
/*  820 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  823 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  824 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  825 */     getRCATrimmedText(div2, rca);
/*  826 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  828 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  833 */     String[] st = msg.split("<br>");
/*  834 */     for (int i = 0; i < st.length; i++) {
/*  835 */       String s = st[i];
/*  836 */       if (s.length() > 180) {
/*  837 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  839 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  843 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  844 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  846 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  850 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  851 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  852 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  855 */       if (key == null) {
/*  856 */         return ret;
/*      */       }
/*      */       
/*  859 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  860 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  863 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  864 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  865 */       set = AMConnectionPool.executeQueryStmt(query);
/*  866 */       if (set.next())
/*      */       {
/*  868 */         String helpLink = set.getString("LINK");
/*  869 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  872 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  878 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  897 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  888 */         if (set != null) {
/*  889 */           AMConnectionPool.closeStatement(set);
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
/*  903 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  904 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  906 */       String entityStr = (String)keys.nextElement();
/*  907 */       String mmessage = temp.getProperty(entityStr);
/*  908 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  909 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  911 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  917 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  918 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  920 */       String entityStr = (String)keys.nextElement();
/*  921 */       String mmessage = temp.getProperty(entityStr);
/*  922 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  923 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  925 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  930 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  940 */     String des = new String();
/*  941 */     while (str.indexOf(find) != -1) {
/*  942 */       des = des + str.substring(0, str.indexOf(find));
/*  943 */       des = des + replace;
/*  944 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  946 */     des = des + str;
/*  947 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  954 */       if (alert == null)
/*      */       {
/*  956 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  958 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  960 */         return "&nbsp;";
/*      */       }
/*      */       
/*  963 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  965 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  968 */       int rcalength = test.length();
/*  969 */       if (rcalength < 300)
/*      */       {
/*  971 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  975 */       StringBuffer out = new StringBuffer();
/*  976 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  977 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  978 */       out.append("</div>");
/*  979 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  980 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  981 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  986 */       ex.printStackTrace();
/*      */     }
/*  988 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  994 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  999 */     ArrayList attribIDs = new ArrayList();
/* 1000 */     ArrayList resIDs = new ArrayList();
/* 1001 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1003 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1005 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1007 */       String resourceid = "";
/* 1008 */       String resourceType = "";
/* 1009 */       if (type == 2) {
/* 1010 */         resourceid = (String)row.get(0);
/* 1011 */         resourceType = (String)row.get(3);
/*      */       }
/* 1013 */       else if (type == 3) {
/* 1014 */         resourceid = (String)row.get(0);
/* 1015 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1018 */         resourceid = (String)row.get(6);
/* 1019 */         resourceType = (String)row.get(7);
/*      */       }
/* 1021 */       resIDs.add(resourceid);
/* 1022 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1023 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1025 */       String healthentity = null;
/* 1026 */       String availentity = null;
/* 1027 */       if (healthid != null) {
/* 1028 */         healthentity = resourceid + "_" + healthid;
/* 1029 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1032 */       if (availid != null) {
/* 1033 */         availentity = resourceid + "_" + availid;
/* 1034 */         entitylist.add(availentity);
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
/* 1048 */     Properties alert = getStatus(entitylist);
/* 1049 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1054 */     int size = monitorList.size();
/*      */     
/* 1056 */     String[] severity = new String[size];
/*      */     
/* 1058 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1060 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1061 */       String resourceName1 = (String)row1.get(7);
/* 1062 */       String resourceid1 = (String)row1.get(6);
/* 1063 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1064 */       if (severity[j] == null)
/*      */       {
/* 1066 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1070 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1072 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1074 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1077 */         if (sev > 0) {
/* 1078 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1079 */           monitorList.set(k, monitorList.get(j));
/* 1080 */           monitorList.set(j, t);
/* 1081 */           String temp = severity[k];
/* 1082 */           severity[k] = severity[j];
/* 1083 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1089 */     int z = 0;
/* 1090 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1093 */       int i = 0;
/* 1094 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1097 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1101 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1105 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1107 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1110 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1114 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1117 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1118 */       String resourceName1 = (String)row1.get(7);
/* 1119 */       String resourceid1 = (String)row1.get(6);
/* 1120 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1121 */       if (hseverity[j] == null)
/*      */       {
/* 1123 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1128 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1130 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1133 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1136 */         if (hsev > 0) {
/* 1137 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1138 */           monitorList.set(k, monitorList.get(j));
/* 1139 */           monitorList.set(j, t);
/* 1140 */           String temp1 = hseverity[k];
/* 1141 */           hseverity[k] = hseverity[j];
/* 1142 */           hseverity[j] = temp1;
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
/* 1154 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1155 */     boolean forInventory = false;
/* 1156 */     String trdisplay = "none";
/* 1157 */     String plusstyle = "inline";
/* 1158 */     String minusstyle = "none";
/* 1159 */     String haidTopLevel = "";
/* 1160 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1162 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1164 */         haidTopLevel = request.getParameter("haid");
/* 1165 */         forInventory = true;
/* 1166 */         trdisplay = "table-row;";
/* 1167 */         plusstyle = "none";
/* 1168 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1175 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1178 */     ArrayList listtoreturn = new ArrayList();
/* 1179 */     StringBuffer toreturn = new StringBuffer();
/* 1180 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1181 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1182 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1184 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1186 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1187 */       String childresid = (String)singlerow.get(0);
/* 1188 */       String childresname = (String)singlerow.get(1);
/* 1189 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1190 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1191 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1192 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1193 */       String unmanagestatus = (String)singlerow.get(5);
/* 1194 */       String actionstatus = (String)singlerow.get(6);
/* 1195 */       String linkclass = "monitorgp-links";
/* 1196 */       String titleforres = childresname;
/* 1197 */       String titilechildresname = childresname;
/* 1198 */       String childimg = "/images/trcont.png";
/* 1199 */       String flag = "enable";
/* 1200 */       String dcstarted = (String)singlerow.get(8);
/* 1201 */       String configMonitor = "";
/* 1202 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1203 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1205 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1207 */       if (singlerow.get(7) != null)
/*      */       {
/* 1209 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1211 */       String haiGroupType = "0";
/* 1212 */       if ("HAI".equals(childtype))
/*      */       {
/* 1214 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1216 */       childimg = "/images/trend.png";
/* 1217 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1218 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1219 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1221 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1223 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1225 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1226 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1229 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1231 */         linkclass = "disabledtext";
/* 1232 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1234 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1235 */       String availmouseover = "";
/* 1236 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1238 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1240 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1241 */       String healthmouseover = "";
/* 1242 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1244 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1247 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1248 */       int spacing = 0;
/* 1249 */       if (level >= 1)
/*      */       {
/* 1251 */         spacing = 40 * level;
/*      */       }
/* 1253 */       if (childtype.equals("HAI"))
/*      */       {
/* 1255 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1256 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1257 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1259 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1260 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1261 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1262 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1263 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1264 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1265 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1266 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1267 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1268 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1269 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1271 */         if (!forInventory)
/*      */         {
/* 1273 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1276 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1278 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1280 */           actions = editlink + actions;
/*      */         }
/* 1282 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1284 */           actions = actions + associatelink;
/*      */         }
/* 1286 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1287 */         String arrowimg = "";
/* 1288 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1290 */           actions = "";
/* 1291 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1292 */           checkbox = "";
/* 1293 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1295 */         if (isIt360)
/*      */         {
/* 1297 */           actionimg = "";
/* 1298 */           actions = "";
/* 1299 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1300 */           checkbox = "";
/*      */         }
/*      */         
/* 1303 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1305 */           actions = "";
/*      */         }
/* 1307 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1309 */           checkbox = "";
/*      */         }
/*      */         
/* 1312 */         String resourcelink = "";
/*      */         
/* 1314 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1316 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1320 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1323 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1329 */         if (!isIt360)
/*      */         {
/* 1331 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1335 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1338 */         toreturn.append("</tr>");
/* 1339 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1341 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1342 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1346 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1347 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1350 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1354 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1356 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1358 */             toreturn.append(assocMessage);
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1362 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1368 */         String resourcelink = null;
/* 1369 */         boolean hideEditLink = false;
/* 1370 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1372 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1373 */           hideEditLink = true;
/* 1374 */           if (isIt360)
/*      */           {
/* 1376 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1380 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1382 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1384 */           hideEditLink = true;
/* 1385 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1386 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1391 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1394 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1395 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1396 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1397 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1398 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1399 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1400 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1401 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1402 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1403 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1404 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1405 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1406 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1408 */         if (hideEditLink)
/*      */         {
/* 1410 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1412 */         if (!forInventory)
/*      */         {
/* 1414 */           removefromgroup = "";
/*      */         }
/* 1416 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1417 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1418 */           actions = actions + configcustomfields;
/*      */         }
/* 1420 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1422 */           actions = editlink + actions;
/*      */         }
/* 1424 */         String managedLink = "";
/* 1425 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1427 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1428 */           actions = "";
/* 1429 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1430 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1433 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1435 */           checkbox = "";
/*      */         }
/*      */         
/* 1438 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1440 */           actions = "";
/*      */         }
/* 1442 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1445 */         if (isIt360)
/*      */         {
/* 1447 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1455 */         if (!isIt360)
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1463 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1466 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1473 */       StringBuilder toreturn = new StringBuilder();
/* 1474 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1475 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1476 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1477 */       String title = "";
/* 1478 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1479 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1480 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1481 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1483 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1485 */       else if ("5".equals(severity))
/*      */       {
/* 1487 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1491 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1493 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1494 */       toreturn.append(v);
/*      */       
/* 1496 */       toreturn.append(link);
/* 1497 */       if (severity == null)
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1501 */       else if (severity.equals("5"))
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1505 */       else if (severity.equals("4"))
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1509 */       else if (severity.equals("1"))
/*      */       {
/* 1511 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1516 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1518 */       toreturn.append("</a>");
/* 1519 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1523 */       ex.printStackTrace();
/*      */     }
/* 1525 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1532 */       StringBuilder toreturn = new StringBuilder();
/* 1533 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1534 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1535 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1536 */       if (message == null)
/*      */       {
/* 1538 */         message = "";
/*      */       }
/*      */       
/* 1541 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1542 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1544 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1545 */       toreturn.append(v);
/*      */       
/* 1547 */       toreturn.append(link);
/*      */       
/* 1549 */       if (severity == null)
/*      */       {
/* 1551 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1553 */       else if (severity.equals("5"))
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1557 */       else if (severity.equals("1"))
/*      */       {
/* 1559 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1564 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1566 */       toreturn.append("</a>");
/* 1567 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1573 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1576 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1577 */     if (invokeActions != null) {
/* 1578 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1579 */       while (iterator.hasNext()) {
/* 1580 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1581 */         if (actionmap.containsKey(actionid)) {
/* 1582 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1587 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1591 */     String actionLink = "";
/* 1592 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1593 */     String query = "";
/* 1594 */     ResultSet rs = null;
/* 1595 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1596 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1597 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1598 */       actionLink = "method=" + methodName;
/*      */     }
/* 1600 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1601 */       actionLink = methodName;
/*      */     }
/* 1603 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1604 */     Iterator itr = methodarglist.iterator();
/* 1605 */     boolean isfirstparam = true;
/* 1606 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1607 */     while (itr.hasNext()) {
/* 1608 */       HashMap argmap = (HashMap)itr.next();
/* 1609 */       String argtype = (String)argmap.get("TYPE");
/* 1610 */       String argname = (String)argmap.get("IDENTITY");
/* 1611 */       String paramname = (String)argmap.get("PARAMETER");
/* 1612 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1613 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1614 */         isfirstparam = false;
/* 1615 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1617 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1621 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1625 */         actionLink = actionLink + "&";
/*      */       }
/* 1627 */       String paramValue = null;
/* 1628 */       String tempargname = argname;
/* 1629 */       if (commonValues.getProperty(tempargname) != null) {
/* 1630 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1633 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1634 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1635 */           if (dbType.equals("mysql")) {
/* 1636 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1639 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1641 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1643 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1644 */             if (rs.next()) {
/* 1645 */               paramValue = rs.getString("VALUE");
/* 1646 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1650 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1654 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1657 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1662 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1663 */           paramValue = rowId;
/*      */         }
/* 1665 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1666 */           paramValue = managedObjectName;
/*      */         }
/* 1668 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1669 */           paramValue = resID;
/*      */         }
/* 1671 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1672 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1675 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1677 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1678 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1679 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1681 */     return actionLink;
/*      */   }
/*      */   
/* 1684 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1685 */     String dependentAttribute = null;
/* 1686 */     String align = "left";
/*      */     
/* 1688 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1689 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1690 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1691 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1692 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1693 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1694 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1695 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1696 */       align = "center";
/*      */     }
/*      */     
/* 1699 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1700 */     String actualdata = "";
/*      */     
/* 1702 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1703 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1704 */         actualdata = availValue;
/*      */       }
/* 1706 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1707 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1711 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1712 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1715 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1721 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1722 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1723 */       toreturn.append("<table>");
/* 1724 */       toreturn.append("<tr>");
/* 1725 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1726 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1727 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1728 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1729 */         String toolTip = "";
/* 1730 */         String hideClass = "";
/* 1731 */         String textStyle = "";
/* 1732 */         boolean isreferenced = true;
/* 1733 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1734 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1735 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1736 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1738 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1739 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1740 */           while (valueList.hasMoreTokens()) {
/* 1741 */             String dependentVal = valueList.nextToken();
/* 1742 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1743 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1744 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1746 */               toolTip = "";
/* 1747 */               hideClass = "";
/* 1748 */               isreferenced = false;
/* 1749 */               textStyle = "disabledtext";
/* 1750 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1754 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1755 */           toolTip = "";
/* 1756 */           hideClass = "";
/* 1757 */           isreferenced = false;
/* 1758 */           textStyle = "disabledtext";
/* 1759 */           if (dependentImageMap != null) {
/* 1760 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1761 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1764 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1768 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1769 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1770 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1771 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1772 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1773 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1775 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1776 */           if (isreferenced) {
/* 1777 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1781 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1782 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1783 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1784 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1785 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1786 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1788 */           toreturn.append("</span>");
/* 1789 */           toreturn.append("</a>");
/* 1790 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1793 */       toreturn.append("</tr>");
/* 1794 */       toreturn.append("</table>");
/* 1795 */       toreturn.append("</td>");
/*      */     } else {
/* 1797 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1800 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1804 */     String colTime = null;
/* 1805 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1806 */     if ((rows != null) && (rows.size() > 0)) {
/* 1807 */       Iterator<String> itr = rows.iterator();
/* 1808 */       String maxColQuery = "";
/* 1809 */       for (;;) { if (itr.hasNext()) {
/* 1810 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1811 */           ResultSet maxCol = null;
/*      */           try {
/* 1813 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1814 */             while (maxCol.next()) {
/* 1815 */               if (colTime == null) {
/* 1816 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1819 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1828 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1830 */               if (maxCol != null)
/* 1831 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1833 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1828 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1830 */               if (maxCol != null)
/* 1831 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1833 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1838 */     return colTime;
/*      */   }
/*      */   
/* 1841 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1842 */     tablename = null;
/* 1843 */     ResultSet rsTable = null;
/* 1844 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1846 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1847 */       while (rsTable.next()) {
/* 1848 */         tablename = rsTable.getString("DATATABLE");
/* 1849 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1850 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1863 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1854 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1857 */         if (rsTable != null)
/* 1858 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1860 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1866 */     String argsList = "";
/* 1867 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1869 */       if (showArgsMap.get(row) != null) {
/* 1870 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1871 */         if (showArgslist != null) {
/* 1872 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1873 */             if (argsList.trim().equals("")) {
/* 1874 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1877 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1884 */       e.printStackTrace();
/* 1885 */       return "";
/*      */     }
/* 1887 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1892 */     String argsList = "";
/* 1893 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1896 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1898 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1899 */         if (hideArgsList != null)
/*      */         {
/* 1901 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1903 */             if (argsList.trim().equals(""))
/*      */             {
/* 1905 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1909 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1917 */       ex.printStackTrace();
/*      */     }
/* 1919 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1923 */     StringBuilder toreturn = new StringBuilder();
/* 1924 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1931 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1932 */       Iterator itr = tActionList.iterator();
/* 1933 */       while (itr.hasNext()) {
/* 1934 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1935 */         String confirmmsg = "";
/* 1936 */         String link = "";
/* 1937 */         String isJSP = "NO";
/* 1938 */         HashMap tactionMap = (HashMap)itr.next();
/* 1939 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1940 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1941 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1942 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1943 */           (actionmap.containsKey(actionId))) {
/* 1944 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1945 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1946 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1947 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1948 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1950 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1956 */           if (isTableAction) {
/* 1957 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1960 */             tableName = "Link";
/* 1961 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1962 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1963 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1964 */             toreturn.append("</a></td>");
/*      */           }
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1975 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1981 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1983 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1984 */       Properties prop = (Properties)node.getUserObject();
/* 1985 */       String mgID = prop.getProperty("label");
/* 1986 */       String mgName = prop.getProperty("value");
/* 1987 */       String isParent = prop.getProperty("isParent");
/* 1988 */       int mgIDint = Integer.parseInt(mgID);
/* 1989 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1991 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1993 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1994 */       if (node.getChildCount() > 0)
/*      */       {
/* 1996 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1998 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2000 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2002 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2006 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2011 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2013 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2015 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2017 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2021 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2024 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2025 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2027 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2031 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2033 */       if (node.getChildCount() > 0)
/*      */       {
/* 2035 */         builder.append("<UL>");
/* 2036 */         printMGTree(node, builder);
/* 2037 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2042 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2043 */     StringBuffer toReturn = new StringBuffer();
/* 2044 */     String table = "-";
/*      */     try {
/* 2046 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2047 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2048 */       float total = 0.0F;
/* 2049 */       while (it.hasNext()) {
/* 2050 */         String attName = (String)it.next();
/* 2051 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2052 */         boolean roundOffData = false;
/* 2053 */         if ((data != null) && (!data.equals(""))) {
/* 2054 */           if (data.indexOf(",") != -1) {
/* 2055 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2058 */             float value = Float.parseFloat(data);
/* 2059 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2062 */             total += value;
/* 2063 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2066 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2071 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2072 */       while (attVsWidthList.hasNext()) {
/* 2073 */         String attName = (String)attVsWidthList.next();
/* 2074 */         String data = (String)attVsWidthProps.get(attName);
/* 2075 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2076 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2077 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2078 */         String className = (String)graphDetails.get("ClassName");
/* 2079 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2080 */         if (percentage < 1.0F)
/*      */         {
/* 2082 */           data = percentage + "";
/*      */         }
/* 2084 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2086 */       if (toReturn.length() > 0) {
/* 2087 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2091 */       e.printStackTrace();
/*      */     }
/* 2093 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2099 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2100 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2101 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2102 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2103 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2104 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2105 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2106 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2107 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2110 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2111 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2112 */       splitvalues[0] = multiplecondition.toString();
/* 2113 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2116 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2121 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2122 */     if (thresholdType != 3) {
/* 2123 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2124 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2125 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2126 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2127 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2128 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2130 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2131 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2132 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2133 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2134 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2135 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2137 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2138 */     if (updateSelected != null) {
/* 2139 */       updateSelected[0] = "selected";
/*      */     }
/* 2141 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2146 */       StringBuffer toreturn = new StringBuffer("");
/* 2147 */       if (commaSeparatedMsgId != null) {
/* 2148 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2149 */         int count = 0;
/* 2150 */         while (msgids.hasMoreTokens()) {
/* 2151 */           String id = msgids.nextToken();
/* 2152 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2153 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2154 */           count++;
/* 2155 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2156 */             if (toreturn.length() == 0) {
/* 2157 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2159 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2160 */             if (!image.trim().equals("")) {
/* 2161 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2163 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2164 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2167 */         if (toreturn.length() > 0) {
/* 2168 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2172 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2175 */       e.printStackTrace(); }
/* 2176 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2182 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2188 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2189 */   static { _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2190 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2191 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2230 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2234 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2264 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2268 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2270 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2271 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2272 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2273 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2275 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2276 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2277 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2278 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2279 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2280 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2281 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2282 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2283 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2284 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2286 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2288 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2289 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2290 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2291 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.release();
/* 2292 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2293 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.release();
/* 2294 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2295 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2296 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2303 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/* 2306 */     JspWriter out = null;
/* 2307 */     Object page = this;
/* 2308 */     JspWriter _jspx_out = null;
/* 2309 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2313 */       response.setContentType("text/html;charset=UTF-8");
/* 2314 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2316 */       _jspx_page_context = pageContext;
/* 2317 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2318 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2319 */       session = pageContext.getSession();
/* 2320 */       out = pageContext.getOut();
/* 2321 */       _jspx_out = out;
/*      */       
/* 2323 */       out.write("<!DOCTYPE html>\n");
/* 2324 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2326 */       request.setAttribute("HelpKey", "Monitors Sybase Details");
/*      */       
/* 2328 */       out.write(10);
/* 2329 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2330 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2331 */       if (wlsGraph == null) {
/* 2332 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2333 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2335 */       out.write(10);
/* 2336 */       SybaseGraphs sybasegraph = null;
/* 2337 */       sybasegraph = (SybaseGraphs)_jspx_page_context.getAttribute("sybasegraph", 1);
/* 2338 */       if (sybasegraph == null) {
/* 2339 */         sybasegraph = new SybaseGraphs();
/* 2340 */         _jspx_page_context.setAttribute("sybasegraph", sybasegraph, 1);
/*      */       }
/* 2342 */       out.write(10);
/* 2343 */       com.adventnet.appmanager.bean.PerformanceBean perfgraph = null;
/* 2344 */       perfgraph = (com.adventnet.appmanager.bean.PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2345 */       if (perfgraph == null) {
/* 2346 */         perfgraph = new com.adventnet.appmanager.bean.PerformanceBean();
/* 2347 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2349 */       out.write("\n\n\n\n\n\n\n\n\n");
/* 2350 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2352 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2353 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2354 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2356 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2358 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2360 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2362 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2363 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2364 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2365 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2368 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2369 */         String available = null;
/* 2370 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2371 */         out.write(10);
/*      */         
/* 2373 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2374 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2375 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2377 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2379 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2381 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2383 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2384 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2385 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2386 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2389 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2390 */           String unavailable = null;
/* 2391 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2392 */           out.write(10);
/*      */           
/* 2394 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2395 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2396 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2398 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2400 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2402 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2404 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2405 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2406 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2407 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2410 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2411 */             String unmanaged = null;
/* 2412 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2413 */             out.write(10);
/*      */             
/* 2415 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2416 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2417 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2419 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2421 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2423 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2425 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2426 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2427 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2428 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2431 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2432 */               String scheduled = null;
/* 2433 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2434 */               out.write(10);
/*      */               
/* 2436 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2437 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2438 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2440 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2442 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2444 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2446 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2447 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2448 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2449 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2452 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2453 */                 String critical = null;
/* 2454 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2455 */                 out.write(10);
/*      */                 
/* 2457 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2458 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2459 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2461 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2463 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2465 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2467 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2468 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2469 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2470 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2473 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2474 */                   String clear = null;
/* 2475 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2476 */                   out.write(10);
/*      */                   
/* 2478 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2479 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2480 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2482 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2484 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2486 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2488 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2489 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2490 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2491 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2494 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2495 */                     String warning = null;
/* 2496 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2497 */                     out.write(10);
/* 2498 */                     out.write(10);
/*      */                     
/* 2500 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2501 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2503 */                     out.write(10);
/* 2504 */                     out.write(10);
/* 2505 */                     out.write(10);
/* 2506 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n<script>\nfunction myOnLoad()\n{\n   SORTTABLENAME = 'dbtable';\n   var numberOfColumnsToBeSorted =2;\n   var ignoreCheckBox =false;\n   var ignoreDefaultSorting=false;\n   sortables_init(numberOfColumnsToBeSorted,ignoreCheckBox,ignoreDefaultSorting);\n\n}\nfunction validateAndSubmit()\n{\n\t");
/* 2507 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2509 */                     out.write(10);
/* 2510 */                     out.write(9);
/* 2511 */                     if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2513 */                     out.write("\n}\n</script>\n\n");
/*      */                     try {
/* 2515 */                       String resourceid = request.getParameter("resourceid");
/* 2516 */                       String haid = request.getParameter("haid");
/* 2517 */                       String showdata = (String)request.getAttribute("showdata");
/* 2518 */                       String redirect = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "";
/* 2519 */                       request.setAttribute("redirect", redirect);
/* 2520 */                       String encodeurl = java.net.URLEncoder.encode(redirect);
/* 2521 */                       String displayname = null;
/* 2522 */                       if (request.getAttribute("displayname") == null)
/*      */                       {
/* 2524 */                         displayname = request.getParameter("resourcename");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2528 */                         displayname = (String)request.getAttribute("displayname");
/*      */                       }
/* 2530 */                       ArrayList attribIDs = new ArrayList();
/* 2531 */                       ArrayList resIDs = new ArrayList();
/* 2532 */                       for (int i = 8500; i <= 8510; i++)
/*      */                       {
/* 2534 */                         attribIDs.add("" + i);
/*      */                       }
/*      */                       
/* 2537 */                       resIDs.add(resourceid);
/* 2538 */                       Properties sybasedetails = (Properties)request.getAttribute("sybasedetails");
/* 2539 */                       Properties dbmdetails = (Properties)sybasedetails.get("dbmdetails");
/* 2540 */                       ArrayList dbrows = (ArrayList)sybasedetails.get("rows");
/* 2541 */                       for (int i = 0; i < dbrows.size(); i++) {
/* 2542 */                         Properties pd = (Properties)dbrows.get(i);
/*      */                         
/* 2544 */                         resIDs.add(pd.getProperty("RESID"));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 2550 */                       Properties alert = getStatus(resIDs, attribIDs);
/*      */                       
/* 2552 */                       request.setAttribute("alert", alert);
/* 2553 */                       HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */                       
/* 2555 */                       wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2556 */                       perfgraph.setresourceid(Integer.parseInt(resourceid));
/* 2557 */                       perfgraph.setEntity("Response Time");
/* 2558 */                       sybasegraph.setresid(Integer.parseInt(resourceid));
/* 2559 */                       String totalmemory = dbmdetails.get("TotalMemory").toString();
/* 2560 */                       String usedmemory = dbmdetails.get("UsedMemory").toString();
/* 2561 */                       String memper = dbmdetails.get("MemoryPercentage").toString();
/* 2562 */                       String freememory = dbmdetails.get("FreeMemory").toString();
/*      */                       
/* 2564 */                       ArrayList commands = (ArrayList)sybasedetails.get("allCommands");
/* 2565 */                       ArrayList transactions = (ArrayList)sybasedetails.get("allTransactions");
/*      */                       
/*      */ 
/*      */ 
/* 2569 */                       out.write(10);
/*      */                       
/* 2571 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2572 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2573 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2575 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2576 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2577 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2579 */                           out.write(10);
/* 2580 */                           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2582 */                           out.write(10);
/* 2583 */                           if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2585 */                           out.write(10);
/* 2586 */                           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2588 */                           out.write(10);
/* 2589 */                           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2591 */                           out.write(10);
/* 2592 */                           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2594 */                           out.write(32);
/*      */                           
/* 2596 */                           PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2597 */                           _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2598 */                           _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2600 */                           _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                           
/* 2602 */                           _jspx_th_tiles_005fput_005f4.setType("string");
/* 2603 */                           int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2604 */                           if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2605 */                             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2606 */                               out = _jspx_page_context.pushBody();
/* 2607 */                               _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 2608 */                               _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2611 */                               out.write(10);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 2616 */                               out.write(10);
/*      */                               
/* 2618 */                               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2619 */                               String aid = request.getParameter("haid");
/* 2620 */                               String haName = null;
/* 2621 */                               if (aid != null)
/*      */                               {
/* 2623 */                                 haName = (String)ht.get(aid);
/*      */                               }
/*      */                               
/* 2626 */                               out.write("\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */                               
/* 2628 */                               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2629 */                               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2630 */                               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2632 */                               _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2633 */                               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2634 */                               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                 for (;;) {
/* 2636 */                                   out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2637 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2638 */                                   out.write(" &gt; ");
/* 2639 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2640 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 2641 */                                   out.print(displayname);
/* 2642 */                                   out.write("</span></td>\n\t");
/* 2643 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2644 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2648 */                               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2649 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                               }
/*      */                               
/* 2652 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2653 */                               out.write(10);
/* 2654 */                               out.write(9);
/*      */                               
/* 2656 */                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2657 */                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2658 */                               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2660 */                               _jspx_th_c_005fif_005f3.setTest("${!empty param.haid && (!empty invalidhaid)}");
/* 2661 */                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2662 */                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                 for (;;) {
/* 2664 */                                   out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2665 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2666 */                                   out.write(" &gt; ");
/* 2667 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("SYBASE-DB-server"));
/* 2668 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 2669 */                                   out.print(displayname);
/* 2670 */                                   out.write("</span></td>\n\t");
/* 2671 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2672 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2676 */                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2677 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                               }
/*      */                               
/* 2680 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2681 */                               out.write("\n    </tr>\n</table>\n\n\n");
/* 2682 */                               if (_jspx_meth_c_005fchoose_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 2684 */                               out.write(10);
/*      */                               
/* 2686 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2687 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2688 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2690 */                               _jspx_th_html_005fform_005f0.setAction("/Sybase.do");
/*      */                               
/* 2692 */                               _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 2693 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2694 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2696 */                                   out.write("\n<input type=\"hidden\" name=\"method\" value=\"update\"/>\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2697 */                                   out.print(haid);
/* 2698 */                                   out.write("\"/>\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2699 */                                   out.print(request.getParameter("resourcename"));
/* 2700 */                                   out.write("\"/>\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2701 */                                   out.print(request.getParameter("resourceid"));
/* 2702 */                                   out.write("\"/>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t<tr>\n      <td height=\"31\" class=\"tableheading\">");
/* 2703 */                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2704 */                                   out.write(" </td>\n      <td height=\"31\" class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\"><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\"><span class=\"bodytextboldwhiteun\" ><a href=\"javascript:hideDiv('edit')\" class=\"staticlinks\">");
/* 2705 */                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2706 */                                   out.write("</a></span>\n\t  </td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=2 cellspacing=2 class=\"lrborder\">\n\t<tr>\n\t  <td width=\"15%\" height=\"35\" class=\"bodytext\">");
/* 2707 */                                   out.print(FormatUtil.getString("am.webclient.common.username.text"));
/* 2708 */                                   out.write("<span class=mandatory>*</span></td>\n\t  <td width=\"72%\">");
/* 2709 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2711 */                                   out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td height=\"35\" class=\"bodytext\">");
/* 2712 */                                   out.print(FormatUtil.getString("am.webclient.common.password.text"));
/* 2713 */                                   out.write("<span class=mandatory>*</span></td>\n      <td>");
/* 2714 */                                   if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2716 */                                   out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td height=\"35\" class=\"bodytext\">");
/* 2717 */                                   out.print(FormatUtil.getString("am.webclient.common.databasename"));
/* 2718 */                                   out.write("<span class=mandatory>*</span></td>\n\t  <td>");
/* 2719 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2721 */                                   out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td height=\"35\" class=\"bodytext\">");
/* 2722 */                                   out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2723 */                                   out.write("<span class=mandatory>*</span></td>\n\t  <td>");
/* 2724 */                                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2726 */                                   out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td height=\"35\" class=\"bodytext\">");
/* 2727 */                                   out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2728 */                                   out.write("<span class=mandatory>*</span></td>\n      <td class=\"bodytext\">");
/* 2729 */                                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2731 */                                   out.write(32);
/* 2732 */                                   out.print(FormatUtil.getString("am.webclient.mysql.inminutes"));
/* 2733 */                                   out.write("</td>\n     </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t  <td width=\"17%\" height=\"31\" class=\"tablebottom\">&nbsp;</td>\n\t  <td width=\"83%\" class=\"tablebottom\">");
/*      */                                   
/* 2735 */                                   ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 2736 */                                   _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 2737 */                                   _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2739 */                                   _jspx_th_html_005fbutton_005f0.setOnclick("return validateAndSubmit();");
/*      */                                   
/* 2741 */                                   _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_highlt");
/*      */                                   
/* 2743 */                                   _jspx_th_html_005fbutton_005f0.setProperty("submitbutton3");
/*      */                                   
/* 2745 */                                   _jspx_th_html_005fbutton_005f0.setValue(FormatUtil.getString("am.webclient.common.update.text"));
/* 2746 */                                   int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 2747 */                                   if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 2748 */                                     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                                   }
/*      */                                   
/* 2751 */                                   this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 2752 */                                   out.write("\n\t  &nbsp;<input type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 2753 */                                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2754 */                                   out.write("\" onClick=\"javascript:toggleDiv('edit');\"></td>\n\t</tr>\n</table>\n<br>\n");
/* 2755 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2756 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2760 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2761 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 2764 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2765 */                               out.write("\n</div>\n\n\n<table width=\"99%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n      <td valign=\"top\">\n\t\t<table width=\"96%\"  border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n      \t<tr>\n      \t  <td  colspan=\"2\" class=\"tableheadingbborder\">");
/* 2766 */                               out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2767 */                               out.write("</td>\n\t\t</tr>\n        <tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2768 */                               out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2769 */                               out.write(" </td>\n\t\t  <td class=\"monitorinfoodd\" title=\"");
/* 2770 */                               out.print(displayname);
/* 2771 */                               out.write(34);
/* 2772 */                               out.write(62);
/* 2773 */                               out.print(getTrimmedText(displayname, 30));
/* 2774 */                               out.write("</td>\n\t\t</tr>\n\t\t");
/* 2775 */                               out.write("<!--$Id$-->\n");
/*      */                               
/* 2777 */                               String hostName = "localhost";
/*      */                               try {
/* 2779 */                                 hostName = java.net.InetAddress.getLocalHost().getHostName();
/*      */                               } catch (Exception ex) {
/* 2781 */                                 ex.printStackTrace();
/*      */                               }
/* 2783 */                               String portNumber = System.getProperty("webserver.port");
/* 2784 */                               String styleClass = "monitorinfoodd";
/* 2785 */                               if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2786 */                                 styleClass = "whitegrayborder-conf-mon";
/*      */                               }
/*      */                               
/* 2789 */                               out.write(10);
/*      */                               
/* 2791 */                               PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2792 */                               _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2793 */                               _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2795 */                               _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 2796 */                               int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2797 */                               if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                 for (;;) {
/* 2799 */                                   out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2800 */                                   out.print(styleClass);
/* 2801 */                                   out.write(34);
/* 2802 */                                   out.write(62);
/* 2803 */                                   out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2804 */                                   out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2805 */                                   out.print(styleClass);
/* 2806 */                                   out.write(34);
/* 2807 */                                   out.write(62);
/* 2808 */                                   out.print(hostName);
/* 2809 */                                   out.write(95);
/* 2810 */                                   out.print(portNumber);
/* 2811 */                                   out.write("</td>\n</tr>\n");
/* 2812 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2813 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2817 */                               if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2818 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                               }
/*      */                               
/* 2821 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2822 */                               out.write(10);
/* 2823 */                               out.write(10);
/* 2824 */                               out.write(9);
/* 2825 */                               out.write(9);
/*      */                               
/* 2827 */                               String healthStatus = alert.getProperty(resourceid + "#" + "8501");
/*      */                               
/* 2829 */                               out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2830 */                               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2831 */                               out.write("</td>\n\t\t  <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2832 */                               out.print(resourceid);
/* 2833 */                               out.write("&attributeid=8501')\">");
/* 2834 */                               out.print(getSeverityImageForHealth(healthStatus));
/* 2835 */                               out.write("</a>\n\t\t   ");
/* 2836 */                               out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "8501" + "#" + "MESSAGE"), "8501", alert.getProperty(resourceid + "#" + "8501"), resourceid));
/* 2837 */                               out.write("\n\t\t   ");
/* 2838 */                               if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "8501") != 0) {
/* 2839 */                                 out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2840 */                                 out.print(resourceid + "_8501");
/* 2841 */                                 out.write("&monitortype=SYBASE-DB-server')\">");
/* 2842 */                                 out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2843 */                                 out.write("</a></span>\n           ");
/*      */                               }
/* 2845 */                               out.write("\n\t\t  </td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2846 */                               out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2847 */                               out.write(" </td>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2848 */                               out.print(FormatUtil.getString("Sybase Server"));
/* 2849 */                               out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                               
/* 2851 */                               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2852 */                               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2853 */                               _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2855 */                               _jspx_th_c_005fif_005f4.setTest("${showdata!='1'}");
/* 2856 */                               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2857 */                               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                 for (;;) {
/* 2859 */                                   out.write("\n        ");
/*      */                                   
/* 2861 */                                   if (!dbmdetails.isEmpty())
/*      */                                   {
/*      */ 
/* 2864 */                                     String status = null;
/* 2865 */                                     String mouseOver = null;
/*      */                                     
/*      */ 
/* 2868 */                                     out.write("\n\n\t\t\t<tr >\n\t\t\t <td class=\"monitorinfoeven\" >");
/* 2869 */                                     out.print(FormatUtil.getString("am.webclient.sybase.version.text"));
/* 2870 */                                     out.write("</td>\n\t\t\t <td class=\"monitorinfoeven\" height=\"21\"  title=\"");
/* 2871 */                                     out.print(dbmdetails.getProperty("VERSION"));
/* 2872 */                                     out.write(34);
/* 2873 */                                     out.write(62);
/* 2874 */                                     out.print(getTrimmedText(dbmdetails.getProperty("VERSION"), 40));
/* 2875 */                                     out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr >\n\t\t\t  <td class=\"monitorinfoodd\">");
/* 2876 */                                     out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 2877 */                                     out.write("</td>\n\t\t\t  <td  height=\"21\"  class=\"monitorinfoodd\">");
/* 2878 */                                     out.print(dbmdetails.getProperty("PORT"));
/* 2879 */                                     out.write("</td>\n\t\t\t</tr>\n\n\n\n\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 2883 */                                   out.write(10);
/* 2884 */                                   out.write(9);
/* 2885 */                                   out.write(9);
/* 2886 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2887 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2891 */                               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2892 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                               }
/*      */                               
/* 2895 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2896 */                               out.write(10);
/* 2897 */                               out.write(9);
/* 2898 */                               out.write(9);
/*      */                               
/* 2900 */                               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2901 */                               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2902 */                               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2904 */                               _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2905 */                               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2906 */                               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                 for (;;) {
/* 2908 */                                   out.write(10);
/* 2909 */                                   out.write(9);
/* 2910 */                                   out.write(9);
/*      */                                   
/* 2912 */                                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2913 */                                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2914 */                                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                                   
/* 2916 */                                   _jspx_th_c_005fif_005f5.setTest("${showdata=='1'}");
/* 2917 */                                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2918 */                                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                     for (;;) {
/* 2920 */                                       out.write("\n\t\t\t<tr >\n\t\t\t  <td class=\"monitorinfoeven\">Port</td>\n\t\t\t  <td  height=\"21\"  class=\"monitorinfoeven\">");
/* 2921 */                                       out.print(dbmdetails.getProperty("PORT"));
/* 2922 */                                       out.write("</td>\n\t\t\t</tr>\n\t\t");
/* 2923 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2924 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2928 */                                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2929 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                   }
/*      */                                   
/* 2932 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2933 */                                   out.write(10);
/* 2934 */                                   out.write(9);
/* 2935 */                                   out.write(9);
/* 2936 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2937 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2941 */                               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2942 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                               }
/*      */                               
/* 2945 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2946 */                               out.write(10);
/* 2947 */                               out.write(9);
/* 2948 */                               out.write(9);
/*      */                               
/* 2950 */                               EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2951 */                               _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2952 */                               _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2954 */                               _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2955 */                               int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2956 */                               if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                                 for (;;) {
/* 2958 */                                   out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2959 */                                   out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2960 */                                   out.write("</td>\n\t\t  <td class=\"monitorinfoodd\">-&nbsp;</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 2961 */                                   out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2962 */                                   out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">-</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 2963 */                                   out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2964 */                                   out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">-</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2965 */                                   out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2966 */                                   out.write("</td>\n\t\t  <td class=\"monitorinfoodd\">-</td>\n\t\t</tr>\n\t\t");
/* 2967 */                                   int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2968 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2972 */                               if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2973 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                               }
/*      */                               
/* 2976 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2977 */                               out.write(10);
/* 2978 */                               out.write(9);
/* 2979 */                               out.write(9);
/*      */                               
/* 2981 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2982 */                               _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2983 */                               _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2985 */                               _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 2986 */                               int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2987 */                               if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                 for (;;) {
/* 2989 */                                   out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 2990 */                                   out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2991 */                                   out.write("</td>\n\t\t  ");
/* 2992 */                                   if (systeminfo.get("host_resid") != null)
/*      */                                   {
/* 2994 */                                     out.write("\n\t\t  <td class=\"monitorinfoeven\" title='");
/* 2995 */                                     out.print(systeminfo.get("HOSTNAME"));
/* 2996 */                                     out.write(32);
/* 2997 */                                     out.write(40);
/* 2998 */                                     out.print(systeminfo.get("HOSTIP"));
/* 2999 */                                     out.write(")'><a href=\"showresource.do?resourceid=");
/* 3000 */                                     out.print(systeminfo.get("host_resid"));
/* 3001 */                                     out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 3002 */                                     out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 30));
/* 3003 */                                     out.write("&nbsp;(");
/* 3004 */                                     out.print(systeminfo.get("HOSTIP"));
/* 3005 */                                     out.write(")</a></td>\n\t\t  ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3010 */                                     out.write("\n  \t              <td class=\"monitorinfoeven\" title=\"");
/* 3011 */                                     out.print(systeminfo.get("HOSTNAME"));
/* 3012 */                                     out.write(34);
/* 3013 */                                     out.write(32);
/* 3014 */                                     out.write(62);
/* 3015 */                                     out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3016 */                                     out.write("&nbsp;(");
/* 3017 */                                     out.print(systeminfo.get("HOSTIP"));
/* 3018 */                                     out.write(")</td>\n  \t                         ");
/*      */                                   }
/* 3020 */                                   out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3021 */                                   out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3022 */                                   out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3023 */                                   out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 3024 */                                   out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                                   
/* 3026 */                                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3027 */                                   _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3028 */                                   _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                   
/* 3030 */                                   _jspx_th_logic_005fnotEmpty_005f1.setName("recent5Alarms");
/* 3031 */                                   int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3032 */                                   if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                     for (;;) {
/* 3034 */                                       out.write(10);
/* 3035 */                                       out.write(9);
/* 3036 */                                       out.write(9);
/*      */                                       
/* 3038 */                                       ArrayList recent = (ArrayList)((ArrayList)request.getAttribute("recent5Alarms")).get(0);
/*      */                                       
/* 3040 */                                       out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3041 */                                       out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 3042 */                                       out.write("</td>\n\t\t  <td class=\"monitorinfoodd\"><a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 3043 */                                       out.print(recent.get(2));
/* 3044 */                                       out.write("&source=");
/* 3045 */                                       out.print(recent.get(4));
/* 3046 */                                       out.write("&category=");
/* 3047 */                                       out.print(recent.get(0));
/* 3048 */                                       out.write("&redirectto=");
/* 3049 */                                       out.print(encodeurl);
/* 3050 */                                       out.write("\"  class=\"resourcename\">");
/* 3051 */                                       out.print(getTruncatedAlertMessage((String)recent.get(3)));
/* 3052 */                                       out.write("</a></td>\n\t\t</tr>\n\t\t");
/* 3053 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3054 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3058 */                                   if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3059 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                   }
/*      */                                   
/* 3062 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3063 */                                   out.write(10);
/* 3064 */                                   out.write(9);
/* 3065 */                                   out.write(9);
/*      */                                   
/* 3067 */                                   EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3068 */                                   _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3069 */                                   _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                   
/* 3071 */                                   _jspx_th_logic_005fempty_005f1.setName("recent5Alarms");
/* 3072 */                                   int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3073 */                                   if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                                     for (;;) {
/* 3075 */                                       out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3076 */                                       out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 3077 */                                       out.write("</td>\n\t\t  <td class=\"monitorinfoodd\">-</td>\n\t\t</tr>\n\t\t");
/* 3078 */                                       int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3079 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3083 */                                   if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3084 */                                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                                   }
/*      */                                   
/* 3087 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3088 */                                   out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3089 */                                   out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3090 */                                   out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3091 */                                   out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3092 */                                   out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3093 */                                   out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3094 */                                   out.write("</td>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3095 */                                   out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3096 */                                   out.write("</td>\n\t\t</tr>\n\t\t");
/* 3097 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3098 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3102 */                               if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3103 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                               }
/*      */                               
/* 3106 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3107 */                               out.write(10);
/* 3108 */                               out.write(9);
/* 3109 */                               out.write(9);
/* 3110 */                               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 3111 */                               out.write("\n        </table>\n\t\t");
/*      */                               
/* 3113 */                               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3114 */                               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3115 */                               _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3117 */                               _jspx_th_c_005fif_005f6.setTest("${showdata=='1'}");
/* 3118 */                               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3119 */                               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                 for (;;) {
/* 3121 */                                   out.write("\n\t\t\t<div align=\"center\"><a style=cursor:pointer; ><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"javascript:toggleDiv('edit')\">\n            <tr>\n              <td>&nbsp;</td>\n            </tr>\n            <tr>\n              <td><table width=\"75%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"getmoredatatable\">\n                  <tr>\n                    <td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img src=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\" border=\"0\" vspace=\"2\" hspace=\"5\"></td>\n                    <td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 3122 */                                   out.print(FormatUtil.getString("am.webclient.configureimage.mssql.text"));
/* 3123 */                                   out.write("</td>\n                  </tr>\n                </table></td>\n            </tr>\n      </table></a></div>\n\t\t");
/* 3124 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3125 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3129 */                               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3130 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                               }
/*      */                               
/* 3133 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3134 */                               out.write("\n      </td>\n      <td width=\"40%\" height=\"31\" class=\"bodytextbold\" valign=\"top\" >\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n        <tbody>\n        <tr>\n          <td colspan=\"4\" height=\"31\" class=\"tableheadingbborder\"> ");
/* 3135 */                               out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3136 */                               out.write(" <a name=\"Availability\" id=\"Availability\"></a></td>\n        </tr>\n\t\t<tr>\n\t\t  <td colspan=\"4\">\n\t\t  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n          <tr>\n            <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3137 */                               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3139 */                               out.write("&period=1&resourcename=");
/* 3140 */                               out.print(displayname);
/* 3141 */                               out.write("')\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3142 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3143 */                               out.write("'></a></td>\n            <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3144 */                               if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3146 */                               out.write("&period=2&resourcename=");
/* 3147 */                               out.print(displayname);
/* 3148 */                               out.write("')\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3149 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3150 */                               out.write("'></a></td>\n      \t  </tr>\n          </table>\n          </td>\n        </tr>\n        <tr>\n          <td colspan=\"4\" align=\"center\">\n\t\t\t ");
/*      */                               
/* 3152 */                               AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3153 */                               _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3154 */                               _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3156 */                               _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                               
/* 3158 */                               _jspx_th_awolf_005fpiechart_005f0.setWidth("280");
/*      */                               
/* 3160 */                               _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                               
/* 3162 */                               _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                               
/* 3164 */                               _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                               
/* 3166 */                               _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                               
/* 3168 */                               _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3169 */                               int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3170 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3171 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3172 */                                   out = _jspx_page_context.pushBody();
/* 3173 */                                   _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3174 */                                   _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3177 */                                   out.write("\n\t\t\t\t");
/*      */                                   
/* 3179 */                                   Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3180 */                                   _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3181 */                                   _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                   
/* 3183 */                                   _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3184 */                                   int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3185 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3186 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3187 */                                       out = _jspx_page_context.pushBody();
/* 3188 */                                       _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3189 */                                       _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3192 */                                       out.write("\n\t\t\t\t");
/*      */                                       
/* 3194 */                                       AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3195 */                                       _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3196 */                                       _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 3198 */                                       _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                       
/* 3200 */                                       _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3201 */                                       int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3202 */                                       if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3203 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                       }
/*      */                                       
/* 3206 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3207 */                                       out.write("\n\t\t\t\t");
/*      */                                       
/* 3209 */                                       AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3210 */                                       _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3211 */                                       _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 3213 */                                       _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                       
/* 3215 */                                       _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3216 */                                       int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3217 */                                       if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3218 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                       }
/*      */                                       
/* 3221 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3222 */                                       out.write("\n\t\t\t\t");
/* 3223 */                                       int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3224 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3227 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3228 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3231 */                                   if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3232 */                                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                   }
/*      */                                   
/* 3235 */                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3236 */                                   out.write("\n\t\t\t  ");
/* 3237 */                                   int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3238 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3241 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3242 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3245 */                               if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3246 */                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                               }
/*      */                               
/* 3249 */                               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3250 */                               out.write("\n          </td>\n        </tr>\n\t\t<tr>\n\t\t<td  width=\"49%\" class=\"yellowgrayborder\">");
/* 3251 */                               out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3252 */                               out.write(10);
/* 3253 */                               out.write(9);
/* 3254 */                               out.write(9);
/*      */                               
/* 3256 */                               String avastatus = alert.getProperty(resourceid + "#" + "8500");
/*      */                               
/* 3258 */                               out.write("\n\t\t<a style=\"position:relative; top:2px;\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3259 */                               out.print(resourceid);
/* 3260 */                               out.write("&attributeid=8500')\">");
/* 3261 */                               out.print(getSeverityImageForAvailability(avastatus));
/* 3262 */                               out.write("</a></td>\n\t\t<td width=\"50%\" align=\"right\" class=\"yellowgrayborder\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3263 */                               out.print(resourceid);
/* 3264 */                               out.write("&attributeIDs=8500,8501&attributeToSelect=8500&redirectto=");
/* 3265 */                               out.print(encodeurl);
/* 3266 */                               out.write("\" class=\"links\">");
/* 3267 */                               out.print(ALERTCONFIG_TEXT);
/* 3268 */                               out.write("</a>&nbsp;</td>\n\t\t</tr>\n        </tbody>\n        </table>\n      </td>\n\t</tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3269 */                               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 3270 */                               out.write("</td></tr></table>\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t  <td>&nbsp;</td>\n\t</tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3271 */                               out.print(FormatUtil.getString("am.reporttab.memoryusage.text"));
/* 3272 */                               out.write("&nbsp;</td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n\t<tr>\n\t  <td width=\"405\" height=\"127\" valign=\"top\">\n\t  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n\t    <tr>\n\t\t  <td width=\"96%\" align=\"right\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3273 */                               if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3275 */                               out.write("&attributeid=8504&period=-7',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3276 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3277 */                               out.write("'></td>\n\t\t  <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3278 */                               if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3280 */                               out.write("&attributeid=8504&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3281 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3282 */                               out.write("'></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"2\">\n\t\t  ");
/* 3283 */                               sybasegraph.settype("MEMORYDETAILS");
/* 3284 */                               out.write("\n\t\t  ");
/*      */                               
/* 3286 */                               TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3287 */                               _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3288 */                               _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3290 */                               _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("sybasegraph");
/*      */                               
/* 3292 */                               _jspx_th_awolf_005ftimechart_005f0.setWidth("330");
/*      */                               
/* 3294 */                               _jspx_th_awolf_005ftimechart_005f0.setHeight("185");
/*      */                               
/* 3296 */                               _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */                               
/* 3298 */                               _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                               
/* 3300 */                               _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("Used Memory (KB)"));
/* 3301 */                               int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3302 */                               if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3303 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                               }
/*      */                               
/* 3306 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3307 */                               out.write("\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t  <td width=\"562\" valign=\"top\"> <br> <br>\n\t  <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n\t\t<tr>\n\t\t  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3308 */                               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3310 */                               out.write("</span></td>\n\t\t  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3311 */                               if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3313 */                               out.write("</span></td>\n\t\t  <td class=\"columnheadingnotop\" ><span class=\"bodytextbold\">");
/* 3314 */                               if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3316 */                               out.write("</span></td>\n\t\t</tr>\n\t\t<tr >\n\t\t<td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3317 */                               out.print(FormatUtil.getString("Total Memory (KB)"));
/* 3318 */                               out.write("</td>\n\t\t<td width=\"26%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3319 */                               out.print(totalmemory);
/* 3320 */                               out.write("  </td>\n\t\t ");
/*      */                               
/* 3322 */                               String status8503 = alert.getProperty(resourceid + "#" + "8503");
/*      */                               
/* 3324 */                               out.write("\n\t\t  <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3325 */                               out.print(resourceid);
/* 3326 */                               out.write("&attributeid=8503')\">");
/* 3327 */                               out.print(getSeverityImage(status8503));
/* 3328 */                               out.write("&nbsp;</a></td>\n\t\t</tr>\n\t\t  <tr >\n\t\t<td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3329 */                               out.print(FormatUtil.getString("Used Memory (KB)"));
/* 3330 */                               out.write("</td>\n\t\t<td width=\"26%\" height=\"19\" class=\"whitegrayborder\">");
/* 3331 */                               out.print(usedmemory);
/* 3332 */                               out.write("  </td>\n\t\t  ");
/*      */                               
/* 3334 */                               String status8504 = alert.getProperty(resourceid + "#" + "8504");
/*      */                               
/* 3336 */                               out.write("\n\t\t  <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3337 */                               out.print(resourceid);
/* 3338 */                               out.write("&attributeid=8504')\">");
/* 3339 */                               out.print(getSeverityImage(status8504));
/* 3340 */                               out.write("&nbsp;</a></td>\n\t\t</tr>\n\t\t<tr >\n\t\t<td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3341 */                               out.print(FormatUtil.getString("Free Memory (KB)"));
/* 3342 */                               out.write(" </td>\n\t\t<td width=\"26%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3343 */                               out.print(freememory);
/* 3344 */                               out.write(" </td>\n\t\t ");
/*      */                               
/* 3346 */                               String status8505 = alert.getProperty(resourceid + "#" + "8505");
/*      */                               
/* 3348 */                               out.write("\n\t\t  <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3349 */                               out.print(resourceid);
/* 3350 */                               out.write("&attributeid=8505')\">");
/* 3351 */                               out.print(getSeverityImage(status8505));
/* 3352 */                               out.write("&nbsp;</a></td>\n\t\t</tr>\n\t\t<tr >\n\t\t<td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3353 */                               out.print(FormatUtil.getString("am.webclient.sybase.umpercentage.text"));
/* 3354 */                               out.write("</td>\n\t\t<td width=\"26%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3355 */                               out.print(memper);
/* 3356 */                               out.write("  %</td>\n\t\t ");
/*      */                               
/* 3358 */                               String status8506 = alert.getProperty(resourceid + "#" + "8506");
/*      */                               
/* 3360 */                               out.write("\n\t\t  <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3361 */                               out.print(resourceid);
/* 3362 */                               out.write("&attributeid=8506')\">");
/* 3363 */                               out.print(getSeverityImage(status8506));
/* 3364 */                               out.write("&nbsp;</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3365 */                               out.print(resourceid);
/* 3366 */                               out.write("&attributeIDs=8503,8504,8505,8506&attributeToSelect=8504&redirectto=");
/* 3367 */                               out.print(encodeurl);
/* 3368 */                               out.write("\" class=\"links\">");
/* 3369 */                               out.print(ALERTCONFIG_TEXT);
/* 3370 */                               out.write("</a>&nbsp;</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t</tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t  <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t</tr>\n</table>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3371 */                               out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3372 */                               out.write(" &nbsp;</td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n\t<tr>\n\t  <td width=\"405\" height=\"127\" valign=\"top\">\n\t  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n\t    <tr>\n\t\t  <td width=\"96%\" align=\"right\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3373 */                               if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3375 */                               out.write("&attributeid=8502&period=-7',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3376 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3377 */                               out.write("'></td>\n\t\t  <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3378 */                               if (_jspx_meth_c_005fout_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3380 */                               out.write("&attributeid=8502&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3381 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3382 */                               out.write("'></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"2\">");
/* 3383 */                               sybasegraph.settype("CONNECTION");
/* 3384 */                               out.write("\n\t\t  ");
/*      */                               
/* 3386 */                               TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3387 */                               _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3388 */                               _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3390 */                               _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("sybasegraph");
/*      */                               
/* 3392 */                               _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                               
/* 3394 */                               _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                               
/* 3396 */                               _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                               
/* 3398 */                               _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                               
/* 3400 */                               _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.oracle.graph.connectiontime"));
/* 3401 */                               int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3402 */                               if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3403 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3404 */                                   out = _jspx_page_context.pushBody();
/* 3405 */                                   _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3406 */                                   _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3409 */                                   out.write("\n\t\t  ");
/* 3410 */                                   int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3411 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3414 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3415 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3418 */                               if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3419 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                               }
/*      */                               
/* 3422 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3423 */                               out.write("\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t  <td width=\"562\" valign=\"top\"> <br> <br>\n\t  <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n\t\t<tr>\n\t\t  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3424 */                               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3426 */                               out.write("</span></td>\n\t\t  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3427 */                               if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3429 */                               out.write("</span></td>\n\t\t  <td class=\"columnheadingnotop\" ><span class=\"bodytextbold\">");
/* 3430 */                               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3432 */                               out.write("</span></td>\n\t\t</tr>\n\t\t<tr >\n\t\t  <td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3433 */                               out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3434 */                               out.write(" </td>\n\t\t  <td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n\t\t  ");
/* 3435 */                               if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) == -1L)
/*      */                               {
/* 3437 */                                 out.write(32);
/* 3438 */                                 out.write(45);
/* 3439 */                                 out.write(32);
/*      */                               } else {
/* 3441 */                                 out.write("\n\t\t  ");
/* 3442 */                                 out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 3443 */                                 out.write(" ms\n\t\t  ");
/*      */                               }
/* 3445 */                               out.write("\n\t\t  </td>\n\t\t  ");
/*      */                               
/* 3447 */                               String status8502 = alert.getProperty(resourceid + "#" + "8502");
/*      */                               
/* 3449 */                               out.write("\n\t\t  <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3450 */                               out.print(resourceid);
/* 3451 */                               out.write("&attributeid=8502')\">");
/* 3452 */                               out.print(getSeverityImage(status8502));
/* 3453 */                               out.write("&nbsp;</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3454 */                               out.print(resourceid);
/* 3455 */                               out.write("&attributeIDs=8502&attributeToSelect=8502&redirectto=");
/* 3456 */                               out.print(encodeurl);
/* 3457 */                               out.write("\" class=\"links\">");
/* 3458 */                               out.print(ALERTCONFIG_TEXT);
/* 3459 */                               out.write("</a>&nbsp;</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t  <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t</tr>\n</table>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3460 */                               out.print(FormatUtil.getString("am.webclient.db2.connectionstatistics"));
/* 3461 */                               out.write(" &nbsp;</td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t<tr>\n\t  <td width=\"50%\" height=\"38\"  class=\"rbborder\">\n\t  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3462 */                               if (_jspx_meth_c_005fout_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3464 */                               out.write("&attributeid=8508&period=-7',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3465 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3466 */                               out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3467 */                               if (_jspx_meth_c_005fout_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3469 */                               out.write("&attributeid=8508&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3470 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3471 */                               out.write("'></a></td>\n\t\t</tr>\n\t\t");
/* 3472 */                               sybasegraph.settype("REMOTECONNECTION");
/* 3473 */                               out.write("\n\t\t<tr>\n\t\t  <td>");
/*      */                               
/* 3475 */                               TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3476 */                               _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3477 */                               _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3479 */                               _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("sybasegraph");
/*      */                               
/* 3481 */                               _jspx_th_awolf_005ftimechart_005f2.setWidth("330");
/*      */                               
/* 3483 */                               _jspx_th_awolf_005ftimechart_005f2.setHeight("205");
/*      */                               
/* 3485 */                               _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                               
/* 3487 */                               _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                               
/* 3489 */                               _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.sybase.activeremoteconnections.text"));
/* 3490 */                               int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3491 */                               if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3492 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                               }
/*      */                               
/* 3495 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3496 */                               out.write("</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t  <td width=\"50%\" height=\"38\" class=\"bottomborder\">\n\t  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3497 */                               if (_jspx_meth_c_005fout_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3499 */                               out.write("&attributeid=8509&period=-7',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3500 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3501 */                               out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3502 */                               if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3504 */                               out.write("&attributeid=8509&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3505 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3506 */                               out.write("'></a></td>\n\t\t</tr>\n\t\t");
/* 3507 */                               sybasegraph.settype("USERCONNECTION");
/* 3508 */                               out.write("\n\t\t<tr>\n\t\t  <td>");
/*      */                               
/* 3510 */                               TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3511 */                               _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3512 */                               _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3514 */                               _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("sybasegraph");
/*      */                               
/* 3516 */                               _jspx_th_awolf_005ftimechart_005f3.setWidth("330");
/*      */                               
/* 3518 */                               _jspx_th_awolf_005ftimechart_005f3.setHeight("205");
/*      */                               
/* 3520 */                               _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */                               
/* 3522 */                               _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                               
/* 3524 */                               _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.sybase.graph.noofconnections"));
/* 3525 */                               int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3526 */                               if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3527 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                               }
/*      */                               
/* 3530 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3531 */                               out.write("</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t</tr>\n\t<tr>\n\t  <td valign=\"top\" class=\"rborder\">\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3532 */                               if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3534 */                               out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3535 */                               if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3537 */                               out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3538 */                               if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3540 */                               out.write("</span></td>\n\t\t</tr>\n\t\t<tr >\n\t\t  <td class=\"whitegrayborder\">");
/* 3541 */                               out.print(FormatUtil.getString("am.webclient.sybase.maxremoteconnections.text"));
/* 3542 */                               out.write("</td>\n\t\t  <td width=\"27%\" class=\"whitegrayborder\" colspan='2'>");
/* 3543 */                               out.print(formatNumber(dbmdetails.get("TOTALREMOTECONNECTION")));
/* 3544 */                               out.write("&nbsp;</td>\n\n\t\t</tr>\n\t\t<tr >\n\t\t  <td class=\"whitegrayborder\">");
/* 3545 */                               out.print(FormatUtil.getString("am.webclient.sybase.activeremoteconnections.text"));
/* 3546 */                               out.write("</td>\n\t\t  <td width=\"27%\" class=\"whitegrayborder\" >");
/* 3547 */                               out.print(formatNumber(dbmdetails.get("ACTIVEREMOTECONNECTIOn")));
/* 3548 */                               out.write("&nbsp;</td>\n\t  \t  ");
/*      */                               
/* 3550 */                               String status8508 = alert.getProperty(resourceid + "#" + "8508");
/*      */                               
/* 3552 */                               out.write("\n\t\t  <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3553 */                               out.print(resourceid);
/* 3554 */                               out.write("&attributeid=8508')\">");
/* 3555 */                               out.print(getSeverityImage(status8508));
/* 3556 */                               out.write("&nbsp;</a></td>\n\t\t</tr>\n\n\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3557 */                               out.print(resourceid);
/* 3558 */                               out.write("&attributeIDs=8508&attributeToSelect=8508&redirectto=");
/* 3559 */                               out.print(encodeurl);
/* 3560 */                               out.write("'class=\"staticlinks\">");
/* 3561 */                               out.print(ALERTCONFIG_TEXT);
/* 3562 */                               out.write("</a></td>\n\t\t</tr>\n\n  \t  </table>\n  \t  </td>\n\t  <td valign=\"top\">\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3563 */                               if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3565 */                               out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3566 */                               if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3568 */                               out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3569 */                               if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3571 */                               out.write("</span></td>\n\t\t</tr>\n\t\t<tr >\n\t\t  <td class=\"whitegrayborder\">");
/* 3572 */                               out.print(FormatUtil.getString("am.webclient.sybase.maxuserconnections.text"));
/* 3573 */                               out.write("</td>\n\t\t  <td width=\"27%\" class=\"whitegrayborder\" colspan='2'>");
/* 3574 */                               out.print(formatNumber(dbmdetails.get("TOTALUSERCONNECTION")));
/* 3575 */                               out.write("&nbsp;</td>\n\n\t\t</tr>\n\t\t<tr >\n\t\t  <td class=\"whitegrayborder\">");
/* 3576 */                               out.print(FormatUtil.getString("am.webclient.sybase.activeuserconnections.text"));
/* 3577 */                               out.write("</td>\n\t\t  <td width=\"27%\" class=\"whitegrayborder\"  >");
/* 3578 */                               out.print(formatNumber(dbmdetails.get("ACTIVEUSERCONNECTION")));
/* 3579 */                               out.write("&nbsp;</td>\n\t\t  ");
/*      */                               
/* 3581 */                               String status8509 = alert.getProperty(resourceid + "#" + "8509");
/*      */                               
/* 3583 */                               out.write("\n\t\t  <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3584 */                               out.print(resourceid);
/* 3585 */                               out.write("&attributeid=8509')\">");
/* 3586 */                               out.print(getSeverityImage(status8509));
/* 3587 */                               out.write("&nbsp;</a></td>\n\n\t\t</tr>\n\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3588 */                               out.print(resourceid);
/* 3589 */                               out.write("&attributeIDs=8509&attributeToSelect=8509&redirectto=");
/* 3590 */                               out.print(encodeurl);
/* 3591 */                               out.write("'class=\"staticlinks\">");
/* 3592 */                               out.print(ALERTCONFIG_TEXT);
/* 3593 */                               out.write("</a></td>\n\t\t</tr>\n  \t  </table>\n  \t  </td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t  <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t</tr>\n</table>\n<br>\n\n\n");
/*      */                               
/* 3595 */                               String ChkToCollect = (String)com.adventnet.appmanager.util.EnterpriseUtil.getDisableTable().get("SYBASE-DB-server#DATABASE DETAILS");
/* 3596 */                               if (("every".equalsIgnoreCase(ChkToCollect)) || (("onceaday".equalsIgnoreCase(ChkToCollect)) && (dbrows.size() > 0)) || ((!"never".equalsIgnoreCase(ChkToCollect)) && (dbrows.size() > 0)))
/*      */                               {
/* 3598 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3599 */                                 out.print(FormatUtil.getString("am.webclient.mysql.databasedetails"));
/* 3600 */                                 out.write(" &nbsp;</td>\n\t</tr>\n</table>\n<table width=\"99%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n<tr>\n\t<td>\n\t<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t<tr>\n ");
/*      */                                 
/* 3602 */                                 if (dbrows.size() > 0) {
/* 3603 */                                   for (int j = 0; j < 5; j++) {
/* 3604 */                                     Properties p1 = (Properties)dbrows.get(j);
/* 3605 */                                     String name = p1.getProperty("NAME");
/* 3606 */                                     String size = p1.getProperty("SIZE");
/* 3607 */                                     String usedpercent = p1.getProperty("USEDPERCENTAGE");
/* 3608 */                                     int usedpercentint = Float.valueOf(p1.getProperty("USEDPERCENTAGE")).intValue();
/*      */                                     
/*      */                                     try
/*      */                                     {
/* 3612 */                                       out.write("\n\t\t\t<td width=\"20%\" align=\"center\" style=\"padding:10px 5px 10px 5px; \" title=\"");
/* 3613 */                                       out.print(FormatUtil.getString("am.webclient.sybase.details.usedsize.text"));
/* 3614 */                                       out.write(32);
/* 3615 */                                       out.write(45);
/* 3616 */                                       out.write(32);
/* 3617 */                                       out.print(size);
/* 3618 */                                       out.write(" MB\">\n\t\t\t<fieldset>\n\t\t\t<legend style=\"width:100%;background-color:#fff; line-height:25px;\"><span class=\"bodytext\">");
/* 3619 */                                       out.print(name);
/* 3620 */                                       out.write("</span></legend>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t<tr>\n\t\t\t<td align=\"center\"> ");
/* 3621 */                                       org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp?tooltip=false" + ("/jsp/helicalgraph.jsp?tooltip=false".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(usedpercentint), request.getCharacterEncoding()), out, true);
/* 3622 */                                       out.write(" </td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</fieldset>\n\t\t\t</td>\n\t      ");
/*      */ 
/*      */                                     }
/*      */                                     catch (Exception exc)
/*      */                                     {
/* 3627 */                                       exc.printStackTrace();
/*      */                                     }
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 3632 */                                 out.write("\n  </tr>\n  \t</table>\n  \t</td>\n  </tr>\n  <tr>\n    <td >\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id='dbtable'>\n          <tr>\n            <td width=\"16%\" height=\"35\" class=\"tableheading\"  ><span class=\"bodytextbold\">");
/* 3633 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3634 */                                 out.write("</span></td>\n            <td width=\"18%\" height=\"35\" class=\"tableheading\" ><span class=\"bodytextbold\">");
/* 3635 */                                 out.print(FormatUtil.getString("am.webclient.sybase.details.totalsize.text"));
/* 3636 */                                 out.write(32);
/* 3637 */                                 out.print(FormatUtil.getString("MB"));
/* 3638 */                                 out.write("</span></td>\n            <td width=\"17%\" height=\"35\" class=\"tableheading\" ><span class=\"bodytextbold\">");
/* 3639 */                                 out.print(FormatUtil.getString("am.webclient.sybase.details.usedsize.text"));
/* 3640 */                                 out.write(32);
/* 3641 */                                 out.print(FormatUtil.getString("MB"));
/* 3642 */                                 out.write("</span></td>\n            <td width=\"17%\" height=\"35\" class=\"tableheading\" ><span class=\"bodytextbold\">");
/* 3643 */                                 out.print(FormatUtil.getString("am.webclient.sybase.details.usedsize.text"));
/* 3644 */                                 out.write(" %</span></td>\n            <td width=\"15%\" height=\"35\" class=\"tableheading\" ><span class=\"bodytextbold\">");
/* 3645 */                                 out.print(FormatUtil.getString("am.webclient.sybase.creator.text"));
/* 3646 */                                 out.write("</span></td>\n           <td width=\"15%\" height=\"35\" class=\"tableheading\"  ><span class=\"bodytextbold\">");
/* 3647 */                                 out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3648 */                                 out.write("</span></td>\n\t\t\t");
/*      */                                 
/* 3650 */                                 if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                                 {
/*      */ 
/* 3653 */                                   out.write("\n            <td width=\"22%\" height=\"35\" class=\"tableheading\"  ><span class=\"bodytextbold\">");
/* 3654 */                                   out.print(ALERTCONFIG_TEXT);
/* 3655 */                                   out.write("</span></td>\n\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3660 */                                   out.write("\n\t\t\t<td width=\"22%\" height=\"35\" class=\"tableheading\"></td>\n\t\t\t");
/*      */                                 }
/* 3662 */                                 out.write("\n          </tr>\n\n          ");
/*      */                                 
/* 3664 */                                 for (int j = 0; j < dbrows.size(); j++) {
/* 3665 */                                   Properties p1 = (Properties)dbrows.get(j);
/* 3666 */                                   String name = p1.getProperty("NAME");
/* 3667 */                                   String size = p1.getProperty("SIZE");
/* 3668 */                                   String creator = p1.getProperty("CREATOR");
/* 3669 */                                   String resid = p1.getProperty("RESID");
/* 3670 */                                   String totalsize = p1.getProperty("TOTALDBSIZE");
/* 3671 */                                   String usedpercent = p1.getProperty("USEDPERCENTAGE");
/* 3672 */                                   String bgcolour = "class=\"whitegrayborder\"";
/* 3673 */                                   if (j % 2 == 0)
/*      */                                   {
/* 3675 */                                     bgcolour = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3679 */                                     bgcolour = "yellowgrayborder";
/*      */                                   }
/*      */                                   
/* 3682 */                                   out.write("\n          <tr>\n            <td height=\"35\" class=\"");
/* 3683 */                                   out.print(bgcolour);
/* 3684 */                                   out.write("\" style=\"background:#FFFFFF; border-bottom:1px solid #f6f6f6;\" > ");
/*      */                                   
/* 3686 */                                   Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 3687 */                                   _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 3688 */                                   _jspx_th_am_005fTruncate_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                   
/* 3690 */                                   _jspx_th_am_005fTruncate_005f0.setLength(25);
/* 3691 */                                   int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 3692 */                                   if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 3693 */                                     if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3694 */                                       out = _jspx_page_context.pushBody();
/* 3695 */                                       _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 3696 */                                       _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3699 */                                       out.write(32);
/* 3700 */                                       out.print(name);
/* 3701 */                                       out.write(32);
/* 3702 */                                       int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 3703 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3706 */                                     if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3707 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3710 */                                   if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 3711 */                                     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0); return;
/*      */                                   }
/*      */                                   
/* 3714 */                                   this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 3715 */                                   out.write("</td>\n            <td height=\"35\" class=\"");
/* 3716 */                                   out.print(bgcolour);
/* 3717 */                                   out.write("\" style=\"background:#FFFFFF; border-bottom:1px solid #f6f6f6;\" >\n              ");
/* 3718 */                                   out.print(totalsize);
/* 3719 */                                   out.write("</td>\n              <td height=\"35\" class=\"");
/* 3720 */                                   out.print(bgcolour);
/* 3721 */                                   out.write("\" style=\"background:#FFFFFF; border-bottom:1px solid #f6f6f6;\" >\n              ");
/* 3722 */                                   out.print(size);
/* 3723 */                                   out.write("</td>\n              <td height=\"35\" class=\"");
/* 3724 */                                   out.print(bgcolour);
/* 3725 */                                   out.write("\" style=\"background:#FFFFFF; border-bottom:1px solid #f6f6f6;\" >\n              ");
/* 3726 */                                   out.print(usedpercent);
/* 3727 */                                   out.write("</td>\n            <td height=\"35\" class=\"");
/* 3728 */                                   out.print(bgcolour);
/* 3729 */                                   out.write("\" style=\"background:#FFFFFF; border-bottom:1px solid #f6f6f6;\" > ");
/* 3730 */                                   out.print(creator);
/* 3731 */                                   out.write("</td>\n            ");
/*      */                                   
/* 3733 */                                   String databaseHealthStatus = alert.getProperty(resid + "#" + "8510");
/*      */                                   
/* 3735 */                                   out.write("\n\n\t\t  <td height=\"35\" class=\"");
/* 3736 */                                   out.print(bgcolour);
/* 3737 */                                   out.write("\" style=\"background:#FFFFFF; border-bottom:1px solid #f6f6f6;\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3738 */                                   out.print(resid);
/* 3739 */                                   out.write("&attributeid=8510')\">");
/* 3740 */                                   out.print(getSeverityImageForHealth(databaseHealthStatus));
/* 3741 */                                   out.write("</a>\n\n\t\t  </td>\n\n\t");
/*      */                                   
/* 3743 */                                   if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                                   {
/*      */ 
/* 3746 */                                     out.write("\n            <td height=\"35\" align=\"center\"  class=\"");
/* 3747 */                                     out.print(bgcolour);
/* 3748 */                                     out.write("\" style=\"background:#FFFFFF; border-bottom:1px solid #f6f6f6;\" > <a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3749 */                                     out.print(Integer.parseInt(resid));
/* 3750 */                                     out.write("&attributeIDs=8507,8510,8511,8513&attributeToSelect=8507&redirectto=");
/* 3751 */                                     out.print(encodeurl);
/* 3752 */                                     out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\"  border=\"0\"></a>\n            </td>\n\t\t");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3756 */                                     out.write("\n\t\t<td height=\"35\" align=\"center\"  class=\"");
/* 3757 */                                     out.print(bgcolour);
/* 3758 */                                     out.write("\"  ></td>\n\t\t");
/*      */                                   }
/* 3760 */                                   out.write("\n          </tr>\n          ");
/*      */                                 }
/* 3762 */                                 out.write(" </table>\n    </td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t  <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t</tr>\n</table>\n");
/*      */                               }
/*      */                               
/*      */ 
/* 3766 */                               out.write("\n<br>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n<tr>\n<td>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n\t  <td width=\"100%\" height=\"31\" class=\"tableheadingtrans\" colspan=\"7\">");
/* 3767 */                               out.print(FormatUtil.getString("am.webclient.sybase.currentprocess.text"));
/* 3768 */                               out.write(" </td>\n\t  <td colspan=\"2\" height=\"31\" class=\"tableheadingtrans\" align=\"right\" nowrap><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3769 */                               out.print(resourceid);
/* 3770 */                               out.write("&attributeIDs=8512&attributeToSelect=8512&redirectto=");
/* 3771 */                               out.print(encodeurl);
/* 3772 */                               out.write("\" class=\"bodytextboldwhiteun\">");
/* 3773 */                               out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 3774 */                               out.write("</a>&nbsp;</td>\n</tr>\n\n<tr  height=\"22\">\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"25%\">");
/* 3775 */                               out.print(FormatUtil.getString("am.webclient.hostresourceconfig.processname"));
/* 3776 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"25%\">");
/* 3777 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 3778 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"15%\">");
/* 3779 */                               out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverusername"));
/* 3780 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"10%\">");
/* 3781 */                               out.print(FormatUtil.getString("am.webclient.sybase.DBname.text"));
/* 3782 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"25%\">");
/* 3783 */                               out.print(FormatUtil.getString("am.webclient.hostResource.servers.command"));
/* 3784 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"25%\">");
/* 3785 */                               out.print(FormatUtil.getString("am.webclient.traplistener.status"));
/* 3786 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"20%\">");
/* 3787 */                               out.print(FormatUtil.getString("am.webclient.sybase.physicalio.text"));
/* 3788 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"10%\">");
/* 3789 */                               out.print(FormatUtil.getString("am.webclient.sybase.memusage.text"));
/* 3790 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"10%\">");
/* 3791 */                               out.print(FormatUtil.getString("am.webclient.sybase.timeblocked.text"));
/* 3792 */                               out.write(" &nbsp; &nbsp; &nbsp; ");
/* 3793 */                               out.print(FormatUtil.getString("am.webclient.report.secs"));
/* 3794 */                               out.write(" </td>\n\n</tr>\n");
/* 3795 */                               if (commands.size() > 0) {
/* 3796 */                                 out.write(10);
/* 3797 */                                 for (int i = 0; i < commands.size(); i++) {
/* 3798 */                                   Properties per = (Properties)commands.get(i);
/* 3799 */                                   out.write("\n<tr>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\"  height=\"28\"><span class='bodytext'>");
/* 3800 */                                   out.print(per.getProperty("PROCESSNAME"));
/* 3801 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\"  height=\"28\"><span class='bodytext'>");
/* 3802 */                                   out.print(per.getProperty("HOSTNAME"));
/* 3803 */                                   out.write(47);
/* 3804 */                                   out.print(per.getProperty("IPADDRESS"));
/* 3805 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>&nbsp;");
/* 3806 */                                   out.print(per.getProperty("USERNAME"));
/* 3807 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3808 */                                   out.print(per.getProperty("DBNAME"));
/* 3809 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3810 */                                   out.print(per.getProperty("COMMAND"));
/* 3811 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3812 */                                   out.print(per.getProperty("STATUS"));
/* 3813 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>&nbsp;&nbsp;");
/* 3814 */                                   out.print(per.getProperty("PHYSICALIO"));
/* 3815 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3816 */                                   out.print(per.getProperty("MEMUSAGE"));
/* 3817 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3818 */                                   out.print(per.getProperty("TIME_BLOCKED"));
/* 3819 */                                   out.write("</span></td>\n\n</tr>\n");
/*      */                                 }
/* 3821 */                                 out.write(10);
/*      */                               } else {
/* 3823 */                                 out.write("\n<tr>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\"  height=\"28\"><span class='bodytext'>-</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\"  height=\"28\"><span class='bodytext'>-</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>&nbsp;-</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>-</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>-</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>-</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>&nbsp;&nbsp;-</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>-</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>-</span></td>\n\n</tr>\n\n");
/*      */                               }
/* 3825 */                               out.write("\n</table>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t  <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t</tr>\n</table>\n<br>\n\n<br>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n<tr>\n<td>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n\t  <td width=\"100%\" height=\"31\" class=\"tableheadingtrans\" colspan=\"8\">");
/* 3826 */                               out.print(FormatUtil.getString("am.webclient.sybase.currenttransactions.text"));
/* 3827 */                               out.write(" </td>\n</tr>\n\n<tr  height=\"22\">\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"10%\">");
/* 3828 */                               out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.columnheader.type"));
/* 3829 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"10%\">");
/* 3830 */                               out.print(FormatUtil.getString("am.webclient.sybase.Coordinator.text"));
/* 3831 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"10%\">");
/* 3832 */                               out.print(FormatUtil.getString("am.webclient.oracle.state"));
/* 3833 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"10%\">");
/* 3834 */                               out.print(FormatUtil.getString("am.webclient.dotnet.connections"));
/* 3835 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"10%\">");
/* 3836 */                               out.print(FormatUtil.getString("am.webclient.sybase.DBname.text"));
/* 3837 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"10%\">");
/* 3838 */                               out.print(FormatUtil.getString("am.webclient.hostresourceconfig.processname"));
/* 3839 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"25%\">");
/* 3840 */                               out.print(FormatUtil.getString("am.webclient.sybase.transactionname.text"));
/* 3841 */                               out.write("</td>\n<td class=\"columnheadingtb\" align=\"left\" height=\"28\" width=\"35%\">");
/* 3842 */                               out.print(FormatUtil.getString("am.webclient.support.systeminformation.time"));
/* 3843 */                               out.write("</td>\n\n\n</tr>\n");
/* 3844 */                               if (transactions.size() > 0) {
/* 3845 */                                 out.write(10);
/* 3846 */                                 for (int i = 0; i < transactions.size(); i++) {
/* 3847 */                                   Properties per = (Properties)transactions.get(i);
/* 3848 */                                   out.write("\n<tr>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\"  height=\"28\"><span class='bodytext'>");
/* 3849 */                                   out.print(per.getProperty("TYPE"));
/* 3850 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3851 */                                   out.print(per.getProperty("COORDINATOR"));
/* 3852 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>&nbsp;");
/* 3853 */                                   out.print(per.getProperty("STATE"));
/* 3854 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3855 */                                   out.print(per.getProperty("CONNECTION"));
/* 3856 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3857 */                                   out.print(per.getProperty("DBNAME"));
/* 3858 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3859 */                                   out.print(per.getProperty("PROGRAM_NAME"));
/* 3860 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>&nbsp;&nbsp;");
/* 3861 */                                   out.print(per.getProperty("TRANSACTION_NAME"));
/* 3862 */                                   out.write("</span></td>\n<td align=\"left\" class=\"whitegrayrightalign-reports-normal\" height=\"28\"><span class='bodytext'>");
/* 3863 */                                   out.print(per.getProperty("STARTTIME"));
/* 3864 */                                   out.write("</span></td>\n\n\n</tr>\n\n");
/*      */                                 }
/* 3866 */                                 out.write(10);
/*      */                               } else {
/* 3868 */                                 out.write("\n\n<tr>\n<td align=\"left\"  height=\"28\" colspan='8' class='bodytext'>");
/* 3869 */                                 out.print(FormatUtil.getString("am.webclient.sybase.notransaction.text"));
/* 3870 */                                 out.write(" </td>\n\n\n</tr>\n\n");
/*      */                               }
/* 3872 */                               out.write("\n</table>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t  <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t</tr>\n</table>\n<br>\n\n");
/*      */                               
/* 3874 */                               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3875 */                               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3876 */                               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3878 */                               _jspx_th_logic_005fiterate_005f0.setName("script_ids");
/*      */                               
/* 3880 */                               _jspx_th_logic_005fiterate_005f0.setId("attribute");
/*      */                               
/* 3882 */                               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                               
/* 3884 */                               _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 3885 */                               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3886 */                               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3887 */                                 String attribute = null;
/* 3888 */                                 Integer j = null;
/* 3889 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3890 */                                   out = _jspx_page_context.pushBody();
/* 3891 */                                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3892 */                                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                 }
/* 3894 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3895 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                 for (;;) {
/* 3897 */                                   out.write(10);
/* 3898 */                                   out.write(9);
/* 3899 */                                   out.write(32);
/*      */                                   
/* 3901 */                                   String query = "select scriptname,displayname from AM_ScriptArgs where resourceid=" + attribute;
/* 3902 */                                   String monitorname1 = null;
/* 3903 */                                   String displayname1 = null;
/*      */                                   try
/*      */                                   {
/* 3906 */                                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3907 */                                     ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 3908 */                                     if (rs.next())
/*      */                                     {
/* 3910 */                                       monitorname1 = rs.getString("scriptname");
/* 3911 */                                       displayname1 = rs.getString("displayname");
/*      */                                     }
/* 3913 */                                     rs.close();
/*      */                                   }
/*      */                                   catch (Exception exc) {}
/*      */                                   
/*      */ 
/* 3918 */                                   String url2 = "/showresource.do?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true";
/* 3919 */                                   String url3 = "/jsp/HostScript.jsp?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true&hostid=" + resourceid;
/*      */                                   
/* 3921 */                                   out.write("\n         <table  width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n\t ");
/* 3922 */                                   org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, url2, out, false);
/* 3923 */                                   out.write("\n         <tr>\n         <td width=\"99%\"   class=\"tableheadingtrans\" >\n         <a href=\"showresource.do?method=showResourceForResourceID&resourceid=");
/* 3924 */                                   out.print(attribute);
/* 3925 */                                   out.write("\" class=\"staticlinks\">");
/* 3926 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.scriptmonitor"));
/* 3927 */                                   out.write(32);
/* 3928 */                                   out.write(45);
/* 3929 */                                   out.write(32);
/* 3930 */                                   out.print(displayname1);
/* 3931 */                                   out.write("</a>\n         </td>\n         </tr>\n         <tr>\n         <td>\n         ");
/* 3932 */                                   org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, url3, out, false);
/* 3933 */                                   out.write("\n         </td>\n         </tr>\n         <br>\n         </table>\n         <br>\n         ");
/* 3934 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3935 */                                   attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 3936 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 3937 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3940 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3941 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3944 */                               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3945 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                               }
/*      */                               
/* 3948 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3949 */                               out.write("\n\t <br>\n\t");
/* 3950 */                               out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 3951 */                               DialChartSupport dialGraph = null;
/* 3952 */                               dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 3953 */                               if (dialGraph == null) {
/* 3954 */                                 dialGraph = new DialChartSupport();
/* 3955 */                                 _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                               }
/* 3957 */                               out.write(10);
/*      */                               
/*      */                               try
/*      */                               {
/* 3961 */                                 String hostos = (String)systeminfo.get("HOSTOS");
/* 3962 */                                 String hostname = (String)systeminfo.get("HOSTNAME");
/* 3963 */                                 String hostid = (String)systeminfo.get("host_resid");
/* 3964 */                                 boolean isConf = false;
/* 3965 */                                 if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 3966 */                                   isConf = true;
/*      */                                 }
/* 3968 */                                 com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 3969 */                                 Properties property = new Properties();
/* 3970 */                                 if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                                 {
/* 3972 */                                   property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 3973 */                                   if ((property != null) && (property.size() > 0))
/*      */                                   {
/* 3975 */                                     String cpuid = property.getProperty("cpuid");
/* 3976 */                                     String memid = property.getProperty("memid");
/* 3977 */                                     String diskid = property.getProperty("diskid");
/* 3978 */                                     String cpuvalue = property.getProperty("CPU Utilization");
/* 3979 */                                     String memvalue = property.getProperty("Memory Utilization");
/* 3980 */                                     String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 3981 */                                     String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 3982 */                                     String diskvalue = property.getProperty("Disk Utilization");
/* 3983 */                                     String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                     
/* 3985 */                                     if (!isConf) {
/* 3986 */                                       out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 3987 */                                       out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 3988 */                                       out.write(45);
/* 3989 */                                       if (systeminfo.get("host_resid") != null) {
/* 3990 */                                         out.write("<a href=\"showresource.do?resourceid=");
/* 3991 */                                         out.print(hostid);
/* 3992 */                                         out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 3993 */                                         out.print(hostname);
/* 3994 */                                         out.write("</a>");
/* 3995 */                                       } else { out.println(hostname); }
/* 3996 */                                       out.write("</td>\t");
/* 3997 */                                       out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 3998 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3999 */                                       out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                       
/*      */ 
/* 4002 */                                       if (cpuvalue != null)
/*      */                                       {
/*      */ 
/* 4005 */                                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4006 */                                         out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4007 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4008 */                                         out.write(45);
/* 4009 */                                         out.print(cpuvalue);
/* 4010 */                                         out.write(" %'>\n\n");
/*      */                                         
/* 4012 */                                         DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4013 */                                         _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 4014 */                                         _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4016 */                                         _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                         
/* 4018 */                                         _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                         
/* 4020 */                                         _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                         
/* 4022 */                                         _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                         
/* 4024 */                                         _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                         
/* 4026 */                                         _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                         
/* 4028 */                                         _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                         
/* 4030 */                                         _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                         
/* 4032 */                                         _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                         
/* 4034 */                                         _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 4035 */                                         int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 4036 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 4037 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4038 */                                             out = _jspx_page_context.pushBody();
/* 4039 */                                             _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 4040 */                                             _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4043 */                                             out.write(10);
/* 4044 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 4045 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4048 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4049 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4052 */                                         if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 4053 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                         }
/*      */                                         
/* 4056 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 4057 */                                         out.write("\n         </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4061 */                                         out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4062 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4063 */                                         out.write(32);
/* 4064 */                                         out.write(62);
/* 4065 */                                         out.write(10);
/* 4066 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4067 */                                         out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                       }
/* 4069 */                                       out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4070 */                                       if (cpuvalue != null)
/*      */                                       {
/* 4072 */                                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4073 */                                         out.print(hostid);
/* 4074 */                                         out.write("&attributeid=");
/* 4075 */                                         out.print(cpuid);
/* 4076 */                                         out.write("&period=-7')\" class='bodytextbold'>");
/* 4077 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4078 */                                         out.write(32);
/* 4079 */                                         out.write(45);
/* 4080 */                                         out.write(32);
/* 4081 */                                         out.print(cpuvalue);
/* 4082 */                                         out.write("</a> %\n");
/*      */                                       }
/* 4084 */                                       out.write("\n  </td>\n       </tr>\n       </table>");
/* 4085 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4086 */                                       out.write("</td>\n      <td width=\"30%\"> ");
/* 4087 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4088 */                                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                       
/* 4090 */                                       if (memvalue != null)
/*      */                                       {
/*      */ 
/* 4093 */                                         dialGraph.setValue(Long.parseLong(memvalue));
/* 4094 */                                         out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4095 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4096 */                                         out.write(45);
/* 4097 */                                         out.print(memvalue);
/* 4098 */                                         out.write(" %' >\n\n");
/*      */                                         
/* 4100 */                                         DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4101 */                                         _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 4102 */                                         _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4104 */                                         _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                         
/* 4106 */                                         _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                         
/* 4108 */                                         _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                         
/* 4110 */                                         _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                         
/* 4112 */                                         _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                         
/* 4114 */                                         _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                         
/* 4116 */                                         _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                         
/* 4118 */                                         _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                         
/* 4120 */                                         _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                         
/* 4122 */                                         _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 4123 */                                         int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 4124 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 4125 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4126 */                                             out = _jspx_page_context.pushBody();
/* 4127 */                                             _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 4128 */                                             _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4131 */                                             out.write(32);
/* 4132 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 4133 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4136 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4137 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4140 */                                         if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 4141 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                         }
/*      */                                         
/* 4144 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 4145 */                                         out.write(32);
/* 4146 */                                         out.write("\n            </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4150 */                                         out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4151 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4152 */                                         out.write(" >\n\n");
/* 4153 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4154 */                                         out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                       }
/* 4156 */                                       out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4157 */                                       if (memvalue != null)
/*      */                                       {
/* 4159 */                                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4160 */                                         out.print(hostid);
/* 4161 */                                         out.write("&attributeid=");
/* 4162 */                                         out.print(memid);
/* 4163 */                                         out.write("&period=-7')\" class='bodytextbold'>");
/* 4164 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4165 */                                         out.write(45);
/* 4166 */                                         out.print(memvalue);
/* 4167 */                                         out.write("</a> %\n  ");
/*      */                                       }
/* 4169 */                                       out.write("\n  </td>\n       </tr>\n    </table>");
/* 4170 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4171 */                                       out.write("</td>\n      <td width=\"30%\">");
/* 4172 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4173 */                                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                       
/*      */ 
/* 4176 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                       {
/*      */ 
/*      */ 
/* 4180 */                                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 4181 */                                         out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4182 */                                         out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 4183 */                                         out.write(45);
/* 4184 */                                         out.print(diskvalue);
/* 4185 */                                         out.write("%' >\n");
/*      */                                         
/* 4187 */                                         DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4188 */                                         _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 4189 */                                         _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4191 */                                         _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                         
/* 4193 */                                         _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                         
/* 4195 */                                         _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                         
/* 4197 */                                         _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                         
/* 4199 */                                         _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                         
/* 4201 */                                         _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                         
/* 4203 */                                         _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                         
/* 4205 */                                         _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                         
/* 4207 */                                         _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                         
/* 4209 */                                         _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 4210 */                                         int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 4211 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 4212 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 4213 */                                             out = _jspx_page_context.pushBody();
/* 4214 */                                             _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 4215 */                                             _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4218 */                                             out.write(32);
/* 4219 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 4220 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4223 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 4224 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4227 */                                         if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 4228 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                         }
/*      */                                         
/* 4231 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 4232 */                                         out.write(32);
/* 4233 */                                         out.write(32);
/* 4234 */                                         out.write("\n    </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4238 */                                         out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4239 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4240 */                                         out.write(32);
/* 4241 */                                         out.write(62);
/* 4242 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4243 */                                         out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                       }
/* 4245 */                                       out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 4246 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                       {
/* 4248 */                                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4249 */                                         out.print(hostid);
/* 4250 */                                         out.write("&attributeid=");
/* 4251 */                                         out.print(diskid);
/* 4252 */                                         out.write("&period=-7')\" class='bodytextbold'>");
/* 4253 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 4254 */                                         out.write(45);
/* 4255 */                                         out.print(diskvalue);
/* 4256 */                                         out.write("</a> %\n     ");
/*      */                                       }
/* 4258 */                                       out.write("\n  </td>\n  </tr>\n</table>");
/* 4259 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4260 */                                       out.write("</td></tr></table>\n\n");
/*      */                                     } else {
/* 4262 */                                       out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 4263 */                                       out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 4264 */                                       out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 4265 */                                       out.print(systeminfo.get("host_resid"));
/* 4266 */                                       out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 4267 */                                       out.print(hostname);
/* 4268 */                                       out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 4269 */                                       if (cpuvalue != null)
/*      */                                       {
/*      */ 
/* 4272 */                                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4273 */                                         out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                         
/* 4275 */                                         DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4276 */                                         _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 4277 */                                         _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4279 */                                         _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                         
/* 4281 */                                         _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                         
/* 4283 */                                         _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                         
/* 4285 */                                         _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                         
/* 4287 */                                         _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                         
/* 4289 */                                         _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                         
/* 4291 */                                         _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                         
/* 4293 */                                         _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                         
/* 4295 */                                         _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                         
/* 4297 */                                         _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 4298 */                                         int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 4299 */                                         if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 4300 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                         }
/*      */                                         
/* 4303 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 4304 */                                         out.write("\n         </td>\n     ");
/*      */                                       }
/*      */                                       else {
/* 4307 */                                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4308 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4309 */                                         out.write(39);
/* 4310 */                                         out.write(32);
/* 4311 */                                         out.write(62);
/* 4312 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4313 */                                         out.write("\n \t\t</td>\n\t\t");
/*      */                                       }
/* 4315 */                                       if (memvalue != null) {
/* 4316 */                                         dialGraph.setValue(Long.parseLong(memvalue));
/* 4317 */                                         out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                         
/* 4319 */                                         DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4320 */                                         _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 4321 */                                         _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4323 */                                         _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                         
/* 4325 */                                         _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                         
/* 4327 */                                         _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                         
/* 4329 */                                         _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                         
/* 4331 */                                         _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                         
/* 4333 */                                         _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                         
/* 4335 */                                         _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                         
/* 4337 */                                         _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                         
/* 4339 */                                         _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                         
/* 4341 */                                         _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 4342 */                                         int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 4343 */                                         if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 4344 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                         }
/*      */                                         
/* 4347 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 4348 */                                         out.write("\n            </td>\n         ");
/*      */                                       }
/*      */                                       else {
/* 4351 */                                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4352 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4353 */                                         out.write(39);
/* 4354 */                                         out.write(32);
/* 4355 */                                         out.write(62);
/* 4356 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4357 */                                         out.write("\n \t\t</td>\n\t\t");
/*      */                                       }
/* 4359 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 4360 */                                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 4361 */                                         out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                         
/* 4363 */                                         DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4364 */                                         _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 4365 */                                         _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4367 */                                         _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                         
/* 4369 */                                         _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                         
/* 4371 */                                         _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                         
/* 4373 */                                         _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                         
/* 4375 */                                         _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                         
/* 4377 */                                         _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                         
/* 4379 */                                         _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                         
/* 4381 */                                         _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                         
/* 4383 */                                         _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                         
/* 4385 */                                         _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 4386 */                                         int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 4387 */                                         if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 4388 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                         }
/*      */                                         
/* 4391 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 4392 */                                         out.write(32);
/* 4393 */                                         out.write("\n\t          </td>\n\t  ");
/*      */                                       }
/*      */                                       else {
/* 4396 */                                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4397 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4398 */                                         out.write(39);
/* 4399 */                                         out.write(32);
/* 4400 */                                         out.write(62);
/* 4401 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4402 */                                         out.write("\n \t\t</td>\n\t\t");
/*      */                                       }
/* 4404 */                                       out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4405 */                                       out.print(hostid);
/* 4406 */                                       out.write("&attributeid=");
/* 4407 */                                       out.print(cpuid);
/* 4408 */                                       out.write("&period=-7')\" class='tooltip'>");
/* 4409 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4410 */                                       out.write(32);
/* 4411 */                                       out.write(45);
/* 4412 */                                       out.write(32);
/* 4413 */                                       if (cpuvalue != null) {
/* 4414 */                                         out.print(cpuvalue);
/*      */                                       }
/* 4416 */                                       out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4417 */                                       out.print(hostid);
/* 4418 */                                       out.write("&attributeid=");
/* 4419 */                                       out.print(memid);
/* 4420 */                                       out.write("&period=-7')\" class='tooltip'>");
/* 4421 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4422 */                                       out.write(45);
/* 4423 */                                       if (memvalue != null) {
/* 4424 */                                         out.print(memvalue);
/*      */                                       }
/* 4426 */                                       out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4427 */                                       out.print(hostid);
/* 4428 */                                       out.write("&attributeid=");
/* 4429 */                                       out.print(diskid);
/* 4430 */                                       out.write("&period=-7')\" class='tooltip'>");
/* 4431 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 4432 */                                       out.write(45);
/* 4433 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 4434 */                                         out.print(diskvalue);
/*      */                                       }
/* 4436 */                                       out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                     }
/* 4438 */                                     out.write(10);
/* 4439 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/*      */                                 }
/*      */                               }
/*      */                               catch (Exception e)
/*      */                               {
/* 4446 */                                 e.printStackTrace();
/*      */                               }
/* 4448 */                               out.write(10);
/* 4449 */                               out.write(10);
/* 4450 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 4451 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 4454 */                             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4455 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 4458 */                           if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4459 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                           }
/*      */                           
/* 4462 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 4463 */                           out.write(10);
/* 4464 */                           if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 4466 */                           out.write(10);
/* 4467 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4468 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4472 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4473 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                       }
/*      */                       
/* 4476 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4477 */                       out.write(10);
/*      */                     } catch (NullPointerException npe) {
/* 4479 */                       npe.printStackTrace();
/*      */                     } catch (Exception ex) {
/* 4481 */                       ex.printStackTrace();
/*      */                     }
/* 4483 */                     out.write(10);
/*      */                   }
/* 4485 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4486 */         out = _jspx_out;
/* 4487 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4488 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4489 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4492 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4498 */     PageContext pageContext = _jspx_page_context;
/* 4499 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4501 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4502 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4503 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 4505 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 4506 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4507 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4509 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 4510 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4511 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4515 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4516 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4517 */       return true;
/*      */     }
/* 4519 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4520 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4525 */     PageContext pageContext = _jspx_page_context;
/* 4526 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4528 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4529 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 4530 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/* 4532 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 4533 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 4534 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 4536 */         out.write("\n\tvar poll=trimAll(document.HostResourceForm.pollinterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t    {\n\t\talert(\"");
/* 4537 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 4538 */           return true;
/* 4539 */         out.write("\");\n\t\treturn;\n\t}\n\tvar user=trimAll(document.HostResourceForm.username.value);\n\tif(user == '')\n\t\t{\n\t\t\talert(\"");
/* 4540 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 4541 */           return true;
/* 4542 */         out.write("\");\n\t\t\treturn;\n\t\t}\n\tif(trimAll(document.HostResourceForm.password.value) == '')\n\t{\n\n\t\tif(!confirm('");
/* 4543 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 4544 */           return true;
/* 4545 */         out.write("'))\n                        {\n                            return;\n                        }\n\t}\n\tvar db=trimAll(document.HostResourceForm.instance.value);\n\tif(db == '')\n\t\t{\n\t\t\talert(\"");
/* 4546 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 4547 */           return true;
/* 4548 */         out.write("\");\n\t\t\treturn;\n\t\t}\n\n        if(!checkForDisplayName(trimAll(document.HostResourceForm.displayname.value))) {\n            document.HostResourceForm.displayname.select();\n            return;\n        }\n\tdocument.HostResourceForm.submit();\n\t");
/* 4549 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 4550 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4554 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 4555 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4556 */       return true;
/*      */     }
/* 4558 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4564 */     PageContext pageContext = _jspx_page_context;
/* 4565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4567 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4568 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4569 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 4570 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4571 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 4572 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4573 */         out = _jspx_page_context.pushBody();
/* 4574 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 4575 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4578 */         out.write("am.webclient.common.validpollinginterval.text");
/* 4579 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 4580 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4583 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4584 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4587 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4588 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4589 */       return true;
/*      */     }
/* 4591 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4597 */     PageContext pageContext = _jspx_page_context;
/* 4598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4600 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4601 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4602 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 4603 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4604 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 4605 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4606 */         out = _jspx_page_context.pushBody();
/* 4607 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 4608 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4611 */         out.write("am.webclient.common.validusername.text");
/* 4612 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 4613 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4616 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4617 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4620 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4621 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4622 */       return true;
/*      */     }
/* 4624 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4625 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4630 */     PageContext pageContext = _jspx_page_context;
/* 4631 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4633 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4634 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4635 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 4636 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4637 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 4638 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4639 */         out = _jspx_page_context.pushBody();
/* 4640 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 4641 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4644 */         out.write("password.empty.message");
/* 4645 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 4646 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4649 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4650 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4653 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4654 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4655 */       return true;
/*      */     }
/* 4657 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4663 */     PageContext pageContext = _jspx_page_context;
/* 4664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4666 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4667 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4668 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 4669 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4670 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 4671 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4672 */         out = _jspx_page_context.pushBody();
/* 4673 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 4674 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4677 */         out.write("am.webclient.db2.emptydatabase");
/* 4678 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 4679 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4682 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4683 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4686 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4687 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4688 */       return true;
/*      */     }
/* 4690 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4691 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4696 */     PageContext pageContext = _jspx_page_context;
/* 4697 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4699 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4700 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4701 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4703 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4705 */     _jspx_th_tiles_005fput_005f0.setValue("Sybase Server Details");
/* 4706 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4707 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4708 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4709 */       return true;
/*      */     }
/* 4711 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4712 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4717 */     PageContext pageContext = _jspx_page_context;
/* 4718 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4720 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 4721 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 4722 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4724 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 4725 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 4727 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 4728 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 4730 */           out.write(10);
/* 4731 */           out.write(9);
/* 4732 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 4733 */             return true;
/* 4734 */           out.write(10);
/* 4735 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 4736 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4740 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 4741 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4744 */         int tmp190_189 = 0; int[] tmp190_187 = _jspx_push_body_count_c_005fcatch_005f0; int tmp192_191 = tmp190_187[tmp190_189];tmp190_187[tmp190_189] = (tmp192_191 - 1); if (tmp192_191 <= 0) break;
/* 4745 */         out = _jspx_page_context.popBody(); }
/* 4746 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4748 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 4749 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 4751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 4756 */     PageContext pageContext = _jspx_page_context;
/* 4757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4759 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 4760 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 4761 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 4763 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 4765 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 4766 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 4767 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 4768 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4769 */       return true;
/*      */     }
/* 4771 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4772 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4777 */     PageContext pageContext = _jspx_page_context;
/* 4778 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4780 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4781 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4782 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4784 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4785 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4786 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4788 */         out.write(10);
/* 4789 */         out.write(9);
/* 4790 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 4791 */           return true;
/* 4792 */         out.write(10);
/* 4793 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4794 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4798 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4799 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4800 */       return true;
/*      */     }
/* 4802 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4803 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4808 */     PageContext pageContext = _jspx_page_context;
/* 4809 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4811 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4812 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4813 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4815 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4817 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 4818 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4819 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4820 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4821 */       return true;
/*      */     }
/* 4823 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4824 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4829 */     PageContext pageContext = _jspx_page_context;
/* 4830 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4832 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4833 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4834 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4836 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4837 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4838 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4840 */         out.write(10);
/* 4841 */         out.write(9);
/* 4842 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4843 */           return true;
/* 4844 */         out.write(10);
/* 4845 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4846 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4850 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4851 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4852 */       return true;
/*      */     }
/* 4854 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4855 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4860 */     PageContext pageContext = _jspx_page_context;
/* 4861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4863 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4864 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4865 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4867 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 4869 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 4870 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4871 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4872 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4873 */       return true;
/*      */     }
/* 4875 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4876 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4881 */     PageContext pageContext = _jspx_page_context;
/* 4882 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4884 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4885 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4886 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4888 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 4890 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/SybaseLeftPage.jsp");
/* 4891 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4892 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4893 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4894 */       return true;
/*      */     }
/* 4896 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4897 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4902 */     PageContext pageContext = _jspx_page_context;
/* 4903 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4905 */     org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
/* 4906 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 4907 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 4908 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 4909 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 4911 */         out.write(10);
/* 4912 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4913 */           return true;
/* 4914 */         out.write(10);
/* 4915 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4916 */           return true;
/* 4917 */         out.write(10);
/* 4918 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4919 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4923 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4924 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4925 */       return true;
/*      */     }
/* 4927 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4933 */     PageContext pageContext = _jspx_page_context;
/* 4934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4936 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4937 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 4938 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 4940 */     _jspx_th_c_005fwhen_005f0.setTest("${!empty showconfigdiv && (showconfigdiv == 'true')}");
/* 4941 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 4942 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 4944 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: block\">\n");
/* 4945 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4946 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4950 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4951 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4952 */       return true;
/*      */     }
/* 4954 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4955 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4960 */     PageContext pageContext = _jspx_page_context;
/* 4961 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4963 */     org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
/* 4964 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 4965 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 4966 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 4967 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 4969 */         out.write("\n<div id=\"edit\" style=\"DISPLAY:none\">\n");
/* 4970 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4971 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4975 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4976 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4977 */       return true;
/*      */     }
/* 4979 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4980 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4985 */     PageContext pageContext = _jspx_page_context;
/* 4986 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4988 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4989 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4990 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4992 */     _jspx_th_html_005ftext_005f0.setProperty("username");
/*      */     
/* 4994 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/* 4996 */     _jspx_th_html_005ftext_005f0.setSize("45");
/* 4997 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4998 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4999 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5000 */       return true;
/*      */     }
/* 5002 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5008 */     PageContext pageContext = _jspx_page_context;
/* 5009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5011 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 5012 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 5013 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5015 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 5017 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 5019 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/*      */     
/* 5021 */     _jspx_th_html_005fpassword_005f0.setValue("");
/* 5022 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 5023 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 5024 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 5025 */       return true;
/*      */     }
/* 5027 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 5028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5033 */     PageContext pageContext = _jspx_page_context;
/* 5034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5036 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5037 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 5038 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5040 */     _jspx_th_html_005ftext_005f1.setProperty("instance");
/*      */     
/* 5042 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 5044 */     _jspx_th_html_005ftext_005f1.setSize("15");
/* 5045 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 5046 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 5047 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5048 */       return true;
/*      */     }
/* 5050 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5056 */     PageContext pageContext = _jspx_page_context;
/* 5057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5059 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5060 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 5061 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5063 */     _jspx_th_html_005ftext_005f2.setProperty("displayname");
/*      */     
/* 5065 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 5067 */     _jspx_th_html_005ftext_005f2.setSize("45");
/* 5068 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 5069 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 5070 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5071 */       return true;
/*      */     }
/* 5073 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5079 */     PageContext pageContext = _jspx_page_context;
/* 5080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5082 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5083 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 5084 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5086 */     _jspx_th_html_005ftext_005f3.setProperty("pollinterval");
/*      */     
/* 5088 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 5090 */     _jspx_th_html_005ftext_005f3.setSize("5");
/* 5091 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 5092 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 5093 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5094 */       return true;
/*      */     }
/* 5096 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5102 */     PageContext pageContext = _jspx_page_context;
/* 5103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5105 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5106 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5107 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5109 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 5110 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5111 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5112 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5113 */       return true;
/*      */     }
/* 5115 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5116 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5121 */     PageContext pageContext = _jspx_page_context;
/* 5122 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5124 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5125 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5126 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5128 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 5129 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5130 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5131 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5132 */       return true;
/*      */     }
/* 5134 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5135 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5140 */     PageContext pageContext = _jspx_page_context;
/* 5141 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5143 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5144 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5145 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5147 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5148 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5149 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5150 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5151 */       return true;
/*      */     }
/* 5153 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5154 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5159 */     PageContext pageContext = _jspx_page_context;
/* 5160 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5162 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5163 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5164 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5166 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 5167 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5168 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5169 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5170 */       return true;
/*      */     }
/* 5172 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5178 */     PageContext pageContext = _jspx_page_context;
/* 5179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5181 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5182 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5183 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5184 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5185 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 5186 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 5187 */         out = _jspx_page_context.pushBody();
/* 5188 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 5189 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5192 */         out.write("table.heading.attribute");
/* 5193 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 5194 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5197 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 5198 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5201 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 5202 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5203 */       return true;
/*      */     }
/* 5205 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5206 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5211 */     PageContext pageContext = _jspx_page_context;
/* 5212 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5214 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5215 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 5216 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5217 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 5218 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 5219 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5220 */         out = _jspx_page_context.pushBody();
/* 5221 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 5222 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5225 */         out.write("table.heading.value");
/* 5226 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 5227 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5230 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5231 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5234 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 5235 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5236 */       return true;
/*      */     }
/* 5238 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5239 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5244 */     PageContext pageContext = _jspx_page_context;
/* 5245 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5247 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5248 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 5249 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5250 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 5251 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 5252 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5253 */         out = _jspx_page_context.pushBody();
/* 5254 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 5255 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5258 */         out.write("table.heading.status");
/* 5259 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 5260 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5263 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5264 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5267 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 5268 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5269 */       return true;
/*      */     }
/* 5271 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5272 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5277 */     PageContext pageContext = _jspx_page_context;
/* 5278 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5280 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5281 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5282 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5284 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 5285 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5286 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5287 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5288 */       return true;
/*      */     }
/* 5290 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5291 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5296 */     PageContext pageContext = _jspx_page_context;
/* 5297 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5299 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5300 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5301 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5303 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 5304 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5305 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5306 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5307 */       return true;
/*      */     }
/* 5309 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5310 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5315 */     PageContext pageContext = _jspx_page_context;
/* 5316 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5318 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5319 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 5320 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5321 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 5322 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 5323 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5324 */         out = _jspx_page_context.pushBody();
/* 5325 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 5326 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5329 */         out.write("table.heading.attribute");
/* 5330 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 5331 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5334 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5335 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5338 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 5339 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5340 */       return true;
/*      */     }
/* 5342 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5343 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5348 */     PageContext pageContext = _jspx_page_context;
/* 5349 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5351 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5352 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 5353 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5354 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 5355 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 5356 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 5357 */         out = _jspx_page_context.pushBody();
/* 5358 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 5359 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5362 */         out.write("table.heading.value");
/* 5363 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 5364 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5367 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 5368 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5371 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 5372 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 5373 */       return true;
/*      */     }
/* 5375 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 5376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5381 */     PageContext pageContext = _jspx_page_context;
/* 5382 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5384 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5385 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 5386 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5387 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 5388 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 5389 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 5390 */         out = _jspx_page_context.pushBody();
/* 5391 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 5392 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5395 */         out.write("table.heading.status");
/* 5396 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 5397 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5400 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 5401 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5404 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 5405 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 5406 */       return true;
/*      */     }
/* 5408 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 5409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5414 */     PageContext pageContext = _jspx_page_context;
/* 5415 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5417 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5418 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5419 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5421 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 5422 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5423 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5424 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5425 */       return true;
/*      */     }
/* 5427 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5428 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5433 */     PageContext pageContext = _jspx_page_context;
/* 5434 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5436 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5437 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5438 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5440 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 5441 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5442 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5443 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5444 */       return true;
/*      */     }
/* 5446 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5447 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5452 */     PageContext pageContext = _jspx_page_context;
/* 5453 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5455 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5456 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5457 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5459 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 5460 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5461 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5462 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5463 */       return true;
/*      */     }
/* 5465 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5466 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5471 */     PageContext pageContext = _jspx_page_context;
/* 5472 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5474 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5475 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5476 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5478 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 5479 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5480 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5481 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5482 */       return true;
/*      */     }
/* 5484 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5485 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5490 */     PageContext pageContext = _jspx_page_context;
/* 5491 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5493 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5494 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 5495 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5496 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 5497 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 5498 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 5499 */         out = _jspx_page_context.pushBody();
/* 5500 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 5501 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5504 */         out.write("table.heading.attribute");
/* 5505 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 5506 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5509 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 5510 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5513 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 5514 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 5515 */       return true;
/*      */     }
/* 5517 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 5518 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5523 */     PageContext pageContext = _jspx_page_context;
/* 5524 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5526 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5527 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 5528 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5529 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 5530 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 5531 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 5532 */         out = _jspx_page_context.pushBody();
/* 5533 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 5534 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5537 */         out.write("table.heading.value");
/* 5538 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 5539 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5542 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 5543 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5546 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 5547 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 5548 */       return true;
/*      */     }
/* 5550 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 5551 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5556 */     PageContext pageContext = _jspx_page_context;
/* 5557 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5559 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5560 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 5561 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5562 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 5563 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 5564 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 5565 */         out = _jspx_page_context.pushBody();
/* 5566 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 5567 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5570 */         out.write("table.heading.status");
/* 5571 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 5572 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5575 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 5576 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5579 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 5580 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 5581 */       return true;
/*      */     }
/* 5583 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 5584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5589 */     PageContext pageContext = _jspx_page_context;
/* 5590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5592 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5593 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 5594 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5595 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 5596 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 5597 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 5598 */         out = _jspx_page_context.pushBody();
/* 5599 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 5600 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5603 */         out.write("table.heading.attribute");
/* 5604 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 5605 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5608 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 5609 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5612 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 5613 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 5614 */       return true;
/*      */     }
/* 5616 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 5617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5622 */     PageContext pageContext = _jspx_page_context;
/* 5623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5625 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5626 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 5627 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5628 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 5629 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 5630 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 5631 */         out = _jspx_page_context.pushBody();
/* 5632 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 5633 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5636 */         out.write("table.heading.value");
/* 5637 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 5638 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5641 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 5642 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5645 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 5646 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 5647 */       return true;
/*      */     }
/* 5649 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 5650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5655 */     PageContext pageContext = _jspx_page_context;
/* 5656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5658 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5659 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 5660 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5661 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 5662 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 5663 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 5664 */         out = _jspx_page_context.pushBody();
/* 5665 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 5666 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5669 */         out.write("table.heading.status");
/* 5670 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 5671 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5674 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 5675 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5678 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 5679 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 5680 */       return true;
/*      */     }
/* 5682 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 5683 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5688 */     PageContext pageContext = _jspx_page_context;
/* 5689 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5691 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5692 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 5693 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5695 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 5697 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 5698 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 5699 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 5700 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 5701 */       return true;
/*      */     }
/* 5703 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 5704 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SybaseDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */