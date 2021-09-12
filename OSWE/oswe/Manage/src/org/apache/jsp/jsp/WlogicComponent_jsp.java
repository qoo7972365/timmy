/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.FormatTag;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
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
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class WlogicComponent_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   55 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   58 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   59 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   60 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   67 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   72 */     ArrayList list = null;
/*   73 */     StringBuffer sbf = new StringBuffer();
/*   74 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/*   75 */     if (distinct)
/*      */     {
/*   77 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   81 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   84 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   86 */       ArrayList row = (ArrayList)list.get(i);
/*   87 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   88 */       if (distinct) {
/*   89 */         sbf.append(row.get(0));
/*      */       } else
/*   91 */         sbf.append(row.get(1));
/*   92 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   95 */     return sbf.toString(); }
/*      */   
/*   97 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  100 */     if (severity == null)
/*      */     {
/*  102 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  104 */     if (severity.equals("5"))
/*      */     {
/*  106 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  108 */     if (severity.equals("1"))
/*      */     {
/*  110 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  115 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  122 */     if (severity == null)
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  126 */     if (severity.equals("1"))
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  130 */     if (severity.equals("4"))
/*      */     {
/*  132 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  134 */     if (severity.equals("5"))
/*      */     {
/*  136 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  141 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  147 */     if (severity == null)
/*      */     {
/*  149 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  151 */     if (severity.equals("5"))
/*      */     {
/*  153 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  155 */     if (severity.equals("1"))
/*      */     {
/*  157 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  161 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  167 */     if (severity == null)
/*      */     {
/*  169 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  171 */     if (severity.equals("1"))
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  175 */     if (severity.equals("4"))
/*      */     {
/*  177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  179 */     if (severity.equals("5"))
/*      */     {
/*  181 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  185 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  191 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  197 */     if (severity == 5)
/*      */     {
/*  199 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  201 */     if (severity == 1)
/*      */     {
/*  203 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  208 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  214 */     if (severity == null)
/*      */     {
/*  216 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  218 */     if (severity.equals("5"))
/*      */     {
/*  220 */       if (isAvailability) {
/*  221 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  224 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  227 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  229 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  231 */     if (severity.equals("1"))
/*      */     {
/*  233 */       if (isAvailability) {
/*  234 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  237 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  244 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  251 */     if (severity == null)
/*      */     {
/*  253 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  255 */     if (severity.equals("5"))
/*      */     {
/*  257 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  259 */     if (severity.equals("4"))
/*      */     {
/*  261 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  263 */     if (severity.equals("1"))
/*      */     {
/*  265 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  270 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  276 */     if (severity == null)
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  280 */     if (severity.equals("5"))
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  284 */     if (severity.equals("4"))
/*      */     {
/*  286 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  288 */     if (severity.equals("1"))
/*      */     {
/*  290 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  295 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  302 */     if (severity == null)
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  306 */     if (severity.equals("5"))
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  310 */     if (severity.equals("4"))
/*      */     {
/*  312 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  314 */     if (severity.equals("1"))
/*      */     {
/*  316 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  321 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  329 */     StringBuffer out = new StringBuffer();
/*  330 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  331 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  332 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  333 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  334 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  335 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  336 */     out.append("</tr>");
/*  337 */     out.append("</form></table>");
/*  338 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  345 */     if (val == null)
/*      */     {
/*  347 */       return "-";
/*      */     }
/*      */     
/*  350 */     String ret = FormatUtil.formatNumber(val);
/*  351 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  352 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  355 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  359 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  367 */     StringBuffer out = new StringBuffer();
/*  368 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  369 */     out.append("<tr>");
/*  370 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  372 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  374 */     out.append("</tr>");
/*  375 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  379 */       if (j % 2 == 0)
/*      */       {
/*  381 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  385 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  388 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  390 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  393 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  397 */       out.append("</tr>");
/*      */     }
/*  399 */     out.append("</table>");
/*  400 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  401 */     out.append("<tr>");
/*  402 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  403 */     out.append("</tr>");
/*  404 */     out.append("</table>");
/*  405 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
/*      */   {
/*  411 */     StringBuffer out = new StringBuffer();
/*  412 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  413 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  414 */     out.append("<tr>");
/*  415 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  416 */     out.append("<tr>");
/*  417 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  418 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  419 */     out.append("</tr>");
/*  420 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  423 */       out.append("<tr>");
/*  424 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  425 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  426 */       out.append("</tr>");
/*      */     }
/*      */     
/*  429 */     out.append("</table>");
/*  430 */     out.append("</table>");
/*  431 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  436 */     if (severity.equals("0"))
/*      */     {
/*  438 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  442 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, javax.servlet.http.HttpSession session)
/*      */   {
/*  449 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  462 */     StringBuffer out = new StringBuffer();
/*  463 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  464 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  466 */       out.append("<tr>");
/*  467 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  468 */       out.append("</tr>");
/*      */       
/*      */ 
/*  471 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  473 */         String borderclass = "";
/*      */         
/*      */ 
/*  476 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  478 */         out.append("<tr>");
/*      */         
/*  480 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  481 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  482 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  488 */     out.append("</table><br>");
/*  489 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  490 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  492 */       List sLinks = secondLevelOfLinks[0];
/*  493 */       List sText = secondLevelOfLinks[1];
/*  494 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  497 */         out.append("<tr>");
/*  498 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  499 */         out.append("</tr>");
/*  500 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  502 */           String borderclass = "";
/*      */           
/*      */ 
/*  505 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  507 */           out.append("<tr>");
/*      */           
/*  509 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  510 */           if (sLinks.get(i).toString().length() == 0) {
/*  511 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  514 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  516 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  520 */     out.append("</table>");
/*  521 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session, HttpServletRequest request)
/*      */   {
/*  528 */     StringBuffer out = new StringBuffer();
/*  529 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  530 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  532 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  534 */         out.append("<tr>");
/*  535 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  536 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  540 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  542 */           String borderclass = "";
/*      */           
/*      */ 
/*  545 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  547 */           out.append("<tr>");
/*      */           
/*  549 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  550 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  551 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  554 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  557 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  562 */     out.append("</table><br>");
/*  563 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  564 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  566 */       List sLinks = secondLevelOfLinks[0];
/*  567 */       List sText = secondLevelOfLinks[1];
/*  568 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  571 */         out.append("<tr>");
/*  572 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  573 */         out.append("</tr>");
/*  574 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  576 */           String borderclass = "";
/*      */           
/*      */ 
/*  579 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  581 */           out.append("<tr>");
/*      */           
/*  583 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  584 */           if (sLinks.get(i).toString().length() == 0) {
/*  585 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  588 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  590 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  594 */     out.append("</table>");
/*  595 */     return out.toString();
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
/*  608 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  611 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  614 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  617 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  620 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  623 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  626 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  629 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  637 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  642 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  647 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  652 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  657 */     if (val != null)
/*      */     {
/*  659 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  663 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  668 */     if (val == null) {
/*  669 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  673 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  678 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  684 */     if (val != null)
/*      */     {
/*  686 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  690 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  696 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  701 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  705 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  710 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  715 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  720 */     String hostaddress = "";
/*  721 */     String ip = request.getHeader("x-forwarded-for");
/*  722 */     if (ip == null)
/*  723 */       ip = request.getRemoteAddr();
/*  724 */     java.net.InetAddress add = null;
/*  725 */     if (ip.equals("127.0.0.1")) {
/*  726 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  730 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  732 */     hostaddress = add.getHostName();
/*  733 */     if (hostaddress.indexOf('.') != -1) {
/*  734 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  735 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  739 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  744 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  750 */     if (severity == null)
/*      */     {
/*  752 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  754 */     if (severity.equals("5"))
/*      */     {
/*  756 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  758 */     if (severity.equals("1"))
/*      */     {
/*  760 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  765 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  770 */     ResultSet set = null;
/*  771 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  772 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  774 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  775 */       if (set.next()) { String str1;
/*  776 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  777 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  780 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  785 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  788 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  790 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  794 */     StringBuffer rca = new StringBuffer();
/*  795 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  796 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  799 */     int rcalength = key.length();
/*  800 */     String split = "6. ";
/*  801 */     int splitPresent = key.indexOf(split);
/*  802 */     String div1 = "";String div2 = "";
/*  803 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  805 */       if (rcalength > 180) {
/*  806 */         rca.append("<span class=\"rca-critical-text\">");
/*  807 */         getRCATrimmedText(key, rca);
/*  808 */         rca.append("</span>");
/*      */       } else {
/*  810 */         rca.append("<span class=\"rca-critical-text\">");
/*  811 */         rca.append(key);
/*  812 */         rca.append("</span>");
/*      */       }
/*  814 */       return rca.toString();
/*      */     }
/*  816 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  817 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  818 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  819 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  820 */     getRCATrimmedText(div1, rca);
/*  821 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  824 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  825 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  826 */     getRCATrimmedText(div2, rca);
/*  827 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  829 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  834 */     String[] st = msg.split("<br>");
/*  835 */     for (int i = 0; i < st.length; i++) {
/*  836 */       String s = st[i];
/*  837 */       if (s.length() > 180) {
/*  838 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  840 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  844 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  845 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  847 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  851 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  852 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  853 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  856 */       if (key == null) {
/*  857 */         return ret;
/*      */       }
/*      */       
/*  860 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  861 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  864 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  865 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  866 */       set = AMConnectionPool.executeQueryStmt(query);
/*  867 */       if (set.next())
/*      */       {
/*  869 */         String helpLink = set.getString("LINK");
/*  870 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  873 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  879 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  898 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  889 */         if (set != null) {
/*  890 */           AMConnectionPool.closeStatement(set);
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
/*  904 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  905 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  907 */       String entityStr = (String)keys.nextElement();
/*  908 */       String mmessage = temp.getProperty(entityStr);
/*  909 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  910 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  912 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  918 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  919 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  921 */       String entityStr = (String)keys.nextElement();
/*  922 */       String mmessage = temp.getProperty(entityStr);
/*  923 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  924 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  926 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  931 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  941 */     String des = new String();
/*  942 */     while (str.indexOf(find) != -1) {
/*  943 */       des = des + str.substring(0, str.indexOf(find));
/*  944 */       des = des + replace;
/*  945 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  947 */     des = des + str;
/*  948 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  955 */       if (alert == null)
/*      */       {
/*  957 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  959 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  961 */         return "&nbsp;";
/*      */       }
/*      */       
/*  964 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  966 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  969 */       int rcalength = test.length();
/*  970 */       if (rcalength < 300)
/*      */       {
/*  972 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  976 */       StringBuffer out = new StringBuffer();
/*  977 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  978 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  979 */       out.append("</div>");
/*  980 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  981 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  982 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  987 */       ex.printStackTrace();
/*      */     }
/*  989 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  995 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1000 */     ArrayList attribIDs = new ArrayList();
/* 1001 */     ArrayList resIDs = new ArrayList();
/* 1002 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1004 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1006 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1008 */       String resourceid = "";
/* 1009 */       String resourceType = "";
/* 1010 */       if (type == 2) {
/* 1011 */         resourceid = (String)row.get(0);
/* 1012 */         resourceType = (String)row.get(3);
/*      */       }
/* 1014 */       else if (type == 3) {
/* 1015 */         resourceid = (String)row.get(0);
/* 1016 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1019 */         resourceid = (String)row.get(6);
/* 1020 */         resourceType = (String)row.get(7);
/*      */       }
/* 1022 */       resIDs.add(resourceid);
/* 1023 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1024 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1026 */       String healthentity = null;
/* 1027 */       String availentity = null;
/* 1028 */       if (healthid != null) {
/* 1029 */         healthentity = resourceid + "_" + healthid;
/* 1030 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1033 */       if (availid != null) {
/* 1034 */         availentity = resourceid + "_" + availid;
/* 1035 */         entitylist.add(availentity);
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
/* 1049 */     Properties alert = getStatus(entitylist);
/* 1050 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1055 */     int size = monitorList.size();
/*      */     
/* 1057 */     String[] severity = new String[size];
/*      */     
/* 1059 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1061 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1062 */       String resourceName1 = (String)row1.get(7);
/* 1063 */       String resourceid1 = (String)row1.get(6);
/* 1064 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1065 */       if (severity[j] == null)
/*      */       {
/* 1067 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1071 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1073 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1075 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1078 */         if (sev > 0) {
/* 1079 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1080 */           monitorList.set(k, monitorList.get(j));
/* 1081 */           monitorList.set(j, t);
/* 1082 */           String temp = severity[k];
/* 1083 */           severity[k] = severity[j];
/* 1084 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1090 */     int z = 0;
/* 1091 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1094 */       int i = 0;
/* 1095 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1098 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1102 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1106 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1108 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1111 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1115 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1118 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1119 */       String resourceName1 = (String)row1.get(7);
/* 1120 */       String resourceid1 = (String)row1.get(6);
/* 1121 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1122 */       if (hseverity[j] == null)
/*      */       {
/* 1124 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1129 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1131 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1134 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1137 */         if (hsev > 0) {
/* 1138 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1139 */           monitorList.set(k, monitorList.get(j));
/* 1140 */           monitorList.set(j, t);
/* 1141 */           String temp1 = hseverity[k];
/* 1142 */           hseverity[k] = hseverity[j];
/* 1143 */           hseverity[j] = temp1;
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
/* 1155 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1156 */     boolean forInventory = false;
/* 1157 */     String trdisplay = "none";
/* 1158 */     String plusstyle = "inline";
/* 1159 */     String minusstyle = "none";
/* 1160 */     String haidTopLevel = "";
/* 1161 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1163 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1165 */         haidTopLevel = request.getParameter("haid");
/* 1166 */         forInventory = true;
/* 1167 */         trdisplay = "table-row;";
/* 1168 */         plusstyle = "none";
/* 1169 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1176 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1179 */     ArrayList listtoreturn = new ArrayList();
/* 1180 */     StringBuffer toreturn = new StringBuffer();
/* 1181 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1182 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1183 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1185 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1187 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1188 */       String childresid = (String)singlerow.get(0);
/* 1189 */       String childresname = (String)singlerow.get(1);
/* 1190 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1191 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1192 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1193 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1194 */       String unmanagestatus = (String)singlerow.get(5);
/* 1195 */       String actionstatus = (String)singlerow.get(6);
/* 1196 */       String linkclass = "monitorgp-links";
/* 1197 */       String titleforres = childresname;
/* 1198 */       String titilechildresname = childresname;
/* 1199 */       String childimg = "/images/trcont.png";
/* 1200 */       String flag = "enable";
/* 1201 */       String dcstarted = (String)singlerow.get(8);
/* 1202 */       String configMonitor = "";
/* 1203 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1204 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1206 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1208 */       if (singlerow.get(7) != null)
/*      */       {
/* 1210 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1212 */       String haiGroupType = "0";
/* 1213 */       if ("HAI".equals(childtype))
/*      */       {
/* 1215 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1217 */       childimg = "/images/trend.png";
/* 1218 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1219 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1220 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1222 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1224 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1226 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1227 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1230 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1232 */         linkclass = "disabledtext";
/* 1233 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1235 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1236 */       String availmouseover = "";
/* 1237 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1239 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1241 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1242 */       String healthmouseover = "";
/* 1243 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1245 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1248 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1249 */       int spacing = 0;
/* 1250 */       if (level >= 1)
/*      */       {
/* 1252 */         spacing = 40 * level;
/*      */       }
/* 1254 */       if (childtype.equals("HAI"))
/*      */       {
/* 1256 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1257 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1258 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1260 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1261 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1262 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1263 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1264 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1265 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1266 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1267 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1268 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1269 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1270 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1272 */         if (!forInventory)
/*      */         {
/* 1274 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1277 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1279 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1281 */           actions = editlink + actions;
/*      */         }
/* 1283 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1285 */           actions = actions + associatelink;
/*      */         }
/* 1287 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1288 */         String arrowimg = "";
/* 1289 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1291 */           actions = "";
/* 1292 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1293 */           checkbox = "";
/* 1294 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1296 */         if (isIt360)
/*      */         {
/* 1298 */           actionimg = "";
/* 1299 */           actions = "";
/* 1300 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1301 */           checkbox = "";
/*      */         }
/*      */         
/* 1304 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1306 */           actions = "";
/*      */         }
/* 1308 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1310 */           checkbox = "";
/*      */         }
/*      */         
/* 1313 */         String resourcelink = "";
/*      */         
/* 1315 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1317 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1321 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1324 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1330 */         if (!isIt360)
/*      */         {
/* 1332 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1336 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1339 */         toreturn.append("</tr>");
/* 1340 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1342 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1343 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1347 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1348 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1351 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1355 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1357 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1358 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1359 */             toreturn.append(assocMessage);
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1363 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1369 */         String resourcelink = null;
/* 1370 */         boolean hideEditLink = false;
/* 1371 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1373 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1374 */           hideEditLink = true;
/* 1375 */           if (isIt360)
/*      */           {
/* 1377 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1381 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1383 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1385 */           hideEditLink = true;
/* 1386 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1387 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1392 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1395 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1396 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1397 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1398 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1399 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1400 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1401 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1402 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1403 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1404 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1405 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1406 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1407 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1409 */         if (hideEditLink)
/*      */         {
/* 1411 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1413 */         if (!forInventory)
/*      */         {
/* 1415 */           removefromgroup = "";
/*      */         }
/* 1417 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1418 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1419 */           actions = actions + configcustomfields;
/*      */         }
/* 1421 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1423 */           actions = editlink + actions;
/*      */         }
/* 1425 */         String managedLink = "";
/* 1426 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1428 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1429 */           actions = "";
/* 1430 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1431 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1434 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1436 */           checkbox = "";
/*      */         }
/*      */         
/* 1439 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1441 */           actions = "";
/*      */         }
/* 1443 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1446 */         if (isIt360)
/*      */         {
/* 1448 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1452 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1455 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1456 */         if (!isIt360)
/*      */         {
/* 1458 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1462 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1464 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1467 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1474 */       StringBuilder toreturn = new StringBuilder();
/* 1475 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1476 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1477 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1478 */       String title = "";
/* 1479 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1480 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1481 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1482 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1484 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1486 */       else if ("5".equals(severity))
/*      */       {
/* 1488 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1492 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1494 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1495 */       toreturn.append(v);
/*      */       
/* 1497 */       toreturn.append(link);
/* 1498 */       if (severity == null)
/*      */       {
/* 1500 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1502 */       else if (severity.equals("5"))
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1506 */       else if (severity.equals("4"))
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1510 */       else if (severity.equals("1"))
/*      */       {
/* 1512 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1517 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1519 */       toreturn.append("</a>");
/* 1520 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1524 */       ex.printStackTrace();
/*      */     }
/* 1526 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1533 */       StringBuilder toreturn = new StringBuilder();
/* 1534 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1535 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1536 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1537 */       if (message == null)
/*      */       {
/* 1539 */         message = "";
/*      */       }
/*      */       
/* 1542 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1543 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1545 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1546 */       toreturn.append(v);
/*      */       
/* 1548 */       toreturn.append(link);
/*      */       
/* 1550 */       if (severity == null)
/*      */       {
/* 1552 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1554 */       else if (severity.equals("5"))
/*      */       {
/* 1556 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1558 */       else if (severity.equals("1"))
/*      */       {
/* 1560 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1565 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1567 */       toreturn.append("</a>");
/* 1568 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1574 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1577 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1578 */     if (invokeActions != null) {
/* 1579 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1580 */       while (iterator.hasNext()) {
/* 1581 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1582 */         if (actionmap.containsKey(actionid)) {
/* 1583 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1588 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1592 */     String actionLink = "";
/* 1593 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1594 */     String query = "";
/* 1595 */     ResultSet rs = null;
/* 1596 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1597 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1598 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1599 */       actionLink = "method=" + methodName;
/*      */     }
/* 1601 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1602 */       actionLink = methodName;
/*      */     }
/* 1604 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1605 */     Iterator itr = methodarglist.iterator();
/* 1606 */     boolean isfirstparam = true;
/* 1607 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1608 */     while (itr.hasNext()) {
/* 1609 */       HashMap argmap = (HashMap)itr.next();
/* 1610 */       String argtype = (String)argmap.get("TYPE");
/* 1611 */       String argname = (String)argmap.get("IDENTITY");
/* 1612 */       String paramname = (String)argmap.get("PARAMETER");
/* 1613 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1614 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1615 */         isfirstparam = false;
/* 1616 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1618 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1622 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1626 */         actionLink = actionLink + "&";
/*      */       }
/* 1628 */       String paramValue = null;
/* 1629 */       String tempargname = argname;
/* 1630 */       if (commonValues.getProperty(tempargname) != null) {
/* 1631 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1634 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1635 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1636 */           if (dbType.equals("mysql")) {
/* 1637 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1640 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1642 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1644 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1645 */             if (rs.next()) {
/* 1646 */               paramValue = rs.getString("VALUE");
/* 1647 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1651 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1655 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1658 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1663 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1664 */           paramValue = rowId;
/*      */         }
/* 1666 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1667 */           paramValue = managedObjectName;
/*      */         }
/* 1669 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1670 */           paramValue = resID;
/*      */         }
/* 1672 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1673 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1676 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1678 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1679 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1680 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1682 */     return actionLink;
/*      */   }
/*      */   
/* 1685 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1686 */     String dependentAttribute = null;
/* 1687 */     String align = "left";
/*      */     
/* 1689 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1690 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1691 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1692 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1693 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1694 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1695 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1696 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1697 */       align = "center";
/*      */     }
/*      */     
/* 1700 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1701 */     String actualdata = "";
/*      */     
/* 1703 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1704 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1705 */         actualdata = availValue;
/*      */       }
/* 1707 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1708 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1712 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1713 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1716 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1722 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1723 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1724 */       toreturn.append("<table>");
/* 1725 */       toreturn.append("<tr>");
/* 1726 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1727 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1728 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1729 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1730 */         String toolTip = "";
/* 1731 */         String hideClass = "";
/* 1732 */         String textStyle = "";
/* 1733 */         boolean isreferenced = true;
/* 1734 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1735 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1736 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1737 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1739 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1740 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1741 */           while (valueList.hasMoreTokens()) {
/* 1742 */             String dependentVal = valueList.nextToken();
/* 1743 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1744 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1745 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1747 */               toolTip = "";
/* 1748 */               hideClass = "";
/* 1749 */               isreferenced = false;
/* 1750 */               textStyle = "disabledtext";
/* 1751 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1755 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1756 */           toolTip = "";
/* 1757 */           hideClass = "";
/* 1758 */           isreferenced = false;
/* 1759 */           textStyle = "disabledtext";
/* 1760 */           if (dependentImageMap != null) {
/* 1761 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1762 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1765 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1769 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1770 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1771 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1772 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1773 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1774 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1776 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1777 */           if (isreferenced) {
/* 1778 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1782 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1783 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1784 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1785 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1786 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1787 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1789 */           toreturn.append("</span>");
/* 1790 */           toreturn.append("</a>");
/* 1791 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1794 */       toreturn.append("</tr>");
/* 1795 */       toreturn.append("</table>");
/* 1796 */       toreturn.append("</td>");
/*      */     } else {
/* 1798 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1801 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1805 */     String colTime = null;
/* 1806 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1807 */     if ((rows != null) && (rows.size() > 0)) {
/* 1808 */       Iterator<String> itr = rows.iterator();
/* 1809 */       String maxColQuery = "";
/* 1810 */       for (;;) { if (itr.hasNext()) {
/* 1811 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1812 */           ResultSet maxCol = null;
/*      */           try {
/* 1814 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1815 */             while (maxCol.next()) {
/* 1816 */               if (colTime == null) {
/* 1817 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1820 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1829 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1831 */               if (maxCol != null)
/* 1832 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1834 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1829 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1831 */               if (maxCol != null)
/* 1832 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1834 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1839 */     return colTime;
/*      */   }
/*      */   
/* 1842 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1843 */     tablename = null;
/* 1844 */     ResultSet rsTable = null;
/* 1845 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1847 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1848 */       while (rsTable.next()) {
/* 1849 */         tablename = rsTable.getString("DATATABLE");
/* 1850 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1851 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1864 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1855 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1858 */         if (rsTable != null)
/* 1859 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1861 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1867 */     String argsList = "";
/* 1868 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1870 */       if (showArgsMap.get(row) != null) {
/* 1871 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1872 */         if (showArgslist != null) {
/* 1873 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1874 */             if (argsList.trim().equals("")) {
/* 1875 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1878 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1885 */       e.printStackTrace();
/* 1886 */       return "";
/*      */     }
/* 1888 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1893 */     String argsList = "";
/* 1894 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1897 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1899 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1900 */         if (hideArgsList != null)
/*      */         {
/* 1902 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1904 */             if (argsList.trim().equals(""))
/*      */             {
/* 1906 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1910 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1918 */       ex.printStackTrace();
/*      */     }
/* 1920 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1924 */     StringBuilder toreturn = new StringBuilder();
/* 1925 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1932 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1933 */       Iterator itr = tActionList.iterator();
/* 1934 */       while (itr.hasNext()) {
/* 1935 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1936 */         String confirmmsg = "";
/* 1937 */         String link = "";
/* 1938 */         String isJSP = "NO";
/* 1939 */         HashMap tactionMap = (HashMap)itr.next();
/* 1940 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1941 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1942 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1943 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1944 */           (actionmap.containsKey(actionId))) {
/* 1945 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1946 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1947 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1948 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1949 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1951 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1957 */           if (isTableAction) {
/* 1958 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1961 */             tableName = "Link";
/* 1962 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1963 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1964 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1965 */             toreturn.append("</a></td>");
/*      */           }
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1976 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1982 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1984 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1985 */       Properties prop = (Properties)node.getUserObject();
/* 1986 */       String mgID = prop.getProperty("label");
/* 1987 */       String mgName = prop.getProperty("value");
/* 1988 */       String isParent = prop.getProperty("isParent");
/* 1989 */       int mgIDint = Integer.parseInt(mgID);
/* 1990 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1992 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1994 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1995 */       if (node.getChildCount() > 0)
/*      */       {
/* 1997 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1999 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2001 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2003 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2007 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2012 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2014 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2016 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2018 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2022 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2025 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2026 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2028 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2032 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2034 */       if (node.getChildCount() > 0)
/*      */       {
/* 2036 */         builder.append("<UL>");
/* 2037 */         printMGTree(node, builder);
/* 2038 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2043 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2044 */     StringBuffer toReturn = new StringBuffer();
/* 2045 */     String table = "-";
/*      */     try {
/* 2047 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2048 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2049 */       float total = 0.0F;
/* 2050 */       while (it.hasNext()) {
/* 2051 */         String attName = (String)it.next();
/* 2052 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2053 */         boolean roundOffData = false;
/* 2054 */         if ((data != null) && (!data.equals(""))) {
/* 2055 */           if (data.indexOf(",") != -1) {
/* 2056 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2059 */             float value = Float.parseFloat(data);
/* 2060 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2063 */             total += value;
/* 2064 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2067 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2072 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2073 */       while (attVsWidthList.hasNext()) {
/* 2074 */         String attName = (String)attVsWidthList.next();
/* 2075 */         String data = (String)attVsWidthProps.get(attName);
/* 2076 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2077 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2078 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2079 */         String className = (String)graphDetails.get("ClassName");
/* 2080 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2081 */         if (percentage < 1.0F)
/*      */         {
/* 2083 */           data = percentage + "";
/*      */         }
/* 2085 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2087 */       if (toReturn.length() > 0) {
/* 2088 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2092 */       e.printStackTrace();
/*      */     }
/* 2094 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2100 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2101 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2102 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2103 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2104 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2105 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2106 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2107 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2108 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2111 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2112 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2113 */       splitvalues[0] = multiplecondition.toString();
/* 2114 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2117 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2122 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2123 */     if (thresholdType != 3) {
/* 2124 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2125 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2126 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2127 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2128 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2129 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2131 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2132 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2133 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2134 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2135 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2136 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2138 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2139 */     if (updateSelected != null) {
/* 2140 */       updateSelected[0] = "selected";
/*      */     }
/* 2142 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2147 */       StringBuffer toreturn = new StringBuffer("");
/* 2148 */       if (commaSeparatedMsgId != null) {
/* 2149 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2150 */         int count = 0;
/* 2151 */         while (msgids.hasMoreTokens()) {
/* 2152 */           String id = msgids.nextToken();
/* 2153 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2154 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2155 */           count++;
/* 2156 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2157 */             if (toreturn.length() == 0) {
/* 2158 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2160 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2161 */             if (!image.trim().equals("")) {
/* 2162 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2164 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2165 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2168 */         if (toreturn.length() > 0) {
/* 2169 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2173 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2176 */       e.printStackTrace(); }
/* 2177 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String addBreakAt(String str, int len)
/*      */   {
/* 2186 */     if (len == 0) return str;
/* 2187 */     String temp = str;
/* 2188 */     StringBuffer ret = new StringBuffer("");
/* 2189 */     while (temp.length() > len)
/*      */     {
/* 2191 */       ret.append(temp.substring(0, len));
/* 2192 */       ret.append("<br>");
/* 2193 */       temp = temp.substring(len);
/*      */     }
/* 2195 */     ret.append(temp);
/* 2196 */     return ret.toString();
/*      */   }
/*      */   
/* 2199 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2205 */   private static Map<String, Long> _jspx_dependants = new HashMap(7);
/* 2206 */   static { _jspx_dependants.put("/jsp/includes/mop_actions.jspf", Long.valueOf(1473429417000L));
/* 2207 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2208 */     _jspx_dependants.put("/jsp/includes/cam_screen.jspf", Long.valueOf(1473429417000L));
/* 2209 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2210 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2211 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2212 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2253 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2279 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2280 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2281 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2282 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2283 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2284 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2285 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2286 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2288 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2289 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2290 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2291 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2295 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2296 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2297 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2298 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2299 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2300 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2301 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2302 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2303 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2304 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2305 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2306 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2307 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2308 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2309 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2310 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2311 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2312 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2313 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2314 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.release();
/* 2315 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2316 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.release();
/* 2317 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2318 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2319 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.release();
/* 2320 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.release();
/* 2321 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.release();
/* 2322 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2323 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2324 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.release();
/* 2325 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2326 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2327 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException {
/*      */     ;
/*      */     ;
/*      */     ;
/* 2334 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/* 2337 */     JspWriter out = null;
/* 2338 */     Object page = this;
/* 2339 */     JspWriter _jspx_out = null;
/* 2340 */     PageContext _jspx_page_context = null;
/*      */     
/* 2342 */     Object _jspx_acolumn_1 = null;
/* 2343 */     Integer _jspx_i_1 = null;
/*      */     try
/*      */     {
/* 2346 */       response.setContentType("text/html;charset=UTF-8");
/* 2347 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2349 */       _jspx_page_context = pageContext;
/* 2350 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2351 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2352 */       session = pageContext.getSession();
/* 2353 */       out = pageContext.getOut();
/* 2354 */       _jspx_out = out;
/*      */       
/* 2356 */       out.write("<!DOCTYPE html>\n");
/* 2357 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2359 */       String resourceType = request.getParameter("type");
/* 2360 */       if (resourceType.equals("WEBLOGIC-Integration"))
/*      */       {
/* 2362 */         request.setAttribute("HelpKey", "Monitors WLI Details");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2367 */         request.setAttribute("HelpKey", "Monitors WebLogic Details");
/*      */       }
/*      */       
/* 2370 */       out.write(10);
/* 2371 */       out.write(10);
/* 2372 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2373 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2374 */       if (wlsGraph == null) {
/* 2375 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2376 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2378 */       out.write(10);
/* 2379 */       com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB dataHandler = null;
/* 2380 */       dataHandler = (com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB)_jspx_page_context.getAttribute("dataHandler", 1);
/* 2381 */       if (dataHandler == null) {
/* 2382 */         dataHandler = new com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB();
/* 2383 */         _jspx_page_context.setAttribute("dataHandler", dataHandler, 1);
/*      */       }
/* 2385 */       out.write("\n\n\n\n\n");
/*      */       
/* 2387 */       String resourceName = request.getParameter("resourcename");
/* 2388 */       String resID = request.getParameter("resourceid");
/*      */       
/* 2390 */       ArrayList attribIDs = new ArrayList();
/* 2391 */       ArrayList resIDs = new ArrayList();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2396 */       resIDs.add(resID);
/* 2397 */       String type = FormatUtil.getString("am.webclient.webappdetails.type1.text");
/* 2398 */       if (resourceType.equals("WEBLOGIC-Integration"))
/*      */       {
/* 2400 */         attribIDs.add("6101");
/* 2401 */         attribIDs.add("6102");
/* 2402 */         attribIDs.add("6103");
/* 2403 */         attribIDs.add("6104");
/* 2404 */         type = FormatUtil.getString("am.webclient.webappdetails.wli.text");
/*      */       }
/*      */       else
/*      */       {
/* 2408 */         attribIDs.add("216");
/* 2409 */         attribIDs.add("217");
/* 2410 */         attribIDs.add("218");
/* 2411 */         attribIDs.add("224");
/*      */       }
/* 2413 */       Properties alert = getStatus(resIDs, attribIDs);
/* 2414 */       String applicationName = request.getParameter("name");
/* 2415 */       String appID = request.getParameter("haid");
/* 2416 */       request.setAttribute("isfromresourcepage", "true");
/* 2417 */       String encodeurl = URLEncoder.encode("/showresource.do?" + request.getQueryString());
/* 2418 */       HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2419 */       Properties data = dataHandler.getServerStaticData(resID);
/* 2420 */       Properties status = (Properties)request.getAttribute("currentstatus");
/* 2421 */       String monitor_name = String.valueOf(request.getAttribute("monitorname"));
/*      */       
/* 2423 */       String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 2424 */       String yaxis_restime = FormatUtil.getString("am.webclient.db2.graph.responsetimeinms");
/* 2425 */       String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2426 */       String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/*      */       
/*      */ 
/* 2429 */       out.write("\n\n\n\n\n\n\n\n");
/* 2430 */       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */         return;
/* 2432 */       out.write(10);
/*      */       
/* 2434 */       org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(org.apache.struts.taglib.tiles.InsertTag.class);
/* 2435 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2436 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2438 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2439 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2440 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2442 */           out.write(10);
/* 2443 */           if (resourceType.equals("WEBLOGIC-Integration")) {
/* 2444 */             out.write(10);
/* 2445 */             out.write(10);
/*      */             
/* 2447 */             PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2448 */             _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2449 */             _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/* 2451 */             _jspx_th_tiles_005fput_005f0.setName("title");
/*      */             
/* 2453 */             _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("WebLogic Integration"));
/* 2454 */             int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2455 */             if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2456 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */             }
/*      */             
/* 2459 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2460 */             out.write(10);
/*      */           } else {
/* 2462 */             out.write(10);
/*      */             
/* 2464 */             PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2465 */             _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2466 */             _jspx_th_tiles_005fput_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/* 2468 */             _jspx_th_tiles_005fput_005f1.setName("title");
/*      */             
/* 2470 */             _jspx_th_tiles_005fput_005f1.setValue(FormatUtil.getString("WebLogic Server"));
/* 2471 */             int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2472 */             if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2473 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1); return;
/*      */             }
/*      */             
/* 2476 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2477 */             out.write(10);
/* 2478 */             out.write(9);
/*      */           }
/* 2480 */           out.write(10);
/* 2481 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2483 */           out.write(10);
/* 2484 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2486 */           out.write(10);
/* 2487 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2489 */           out.write(10);
/*      */           
/* 2491 */           PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2492 */           _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 2493 */           _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2495 */           _jspx_th_tiles_005fput_005f5.setName("UserArea");
/*      */           
/* 2497 */           _jspx_th_tiles_005fput_005f5.setType("string");
/* 2498 */           int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 2499 */           if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 2500 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 2501 */               out = _jspx_page_context.pushBody();
/* 2502 */               _jspx_th_tiles_005fput_005f5.setBodyContent((BodyContent)out);
/* 2503 */               _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2506 */               out.write(10);
/* 2507 */               if (_jspx_meth_c_005fset_005f0(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 2509 */               out.write(10);
/* 2510 */               out.write(10);
/* 2511 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2513 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2514 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2515 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2517 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2519 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2521 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2523 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2524 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2525 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2526 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2529 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2530 */               String available = null;
/* 2531 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2532 */               out.write(10);
/*      */               
/* 2534 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2535 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2536 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2538 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2540 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2542 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2544 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2545 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2546 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2547 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2550 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2551 */               String unavailable = null;
/* 2552 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2553 */               out.write(10);
/*      */               
/* 2555 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2556 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2557 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2559 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2561 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2563 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2565 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2566 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2567 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2568 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2571 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2572 */               String unmanaged = null;
/* 2573 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2574 */               out.write(10);
/*      */               
/* 2576 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2577 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2578 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2580 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2582 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2584 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2586 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2587 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2588 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2589 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2592 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2593 */               String scheduled = null;
/* 2594 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2595 */               out.write(10);
/*      */               
/* 2597 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2598 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2599 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2601 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2603 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2605 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2607 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2608 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2609 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2610 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2613 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2614 */               String critical = null;
/* 2615 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2616 */               out.write(10);
/*      */               
/* 2618 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2619 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2620 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2622 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2624 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2626 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2628 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2629 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2630 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2631 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2634 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2635 */               String clear = null;
/* 2636 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2637 */               out.write(10);
/*      */               
/* 2639 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2640 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2641 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2643 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2645 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2647 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2649 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2650 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2651 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2652 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2655 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2656 */               String warning = null;
/* 2657 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2658 */               out.write(10);
/* 2659 */               out.write(10);
/*      */               
/* 2661 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2662 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2664 */               out.write(10);
/* 2665 */               out.write(10);
/* 2666 */               out.write(10);
/* 2667 */               out.write("\n\n\n\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */               
/* 2669 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2670 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2671 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2673 */               _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2674 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2675 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 2677 */                   out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2678 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2679 */                   out.write(" &gt; ");
/* 2680 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request));
/* 2681 */                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 2682 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                     return;
/* 2684 */                   out.write(" </span></td>\n\t");
/* 2685 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2686 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2690 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2691 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 2694 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2695 */               out.write(10);
/* 2696 */               out.write(9);
/*      */               
/* 2698 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2699 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2700 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2702 */               _jspx_th_c_005fif_005f3.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2703 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2704 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 2706 */                   out.write(10);
/* 2707 */                   out.write(9);
/* 2708 */                   if (resourceType.equals("WEBLOGIC-Integration")) {
/* 2709 */                     out.write("\n\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2710 */                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2711 */                     out.write(" &gt; ");
/* 2712 */                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("WEBLOGIC-Integration"));
/* 2713 */                     out.write(" &gt; <span class=\"bcactive\"> ");
/* 2714 */                     if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                       return;
/* 2716 */                     out.write(" </span></td>\n\t");
/*      */                   } else {
/* 2718 */                     out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2719 */                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2720 */                     out.write(" &gt; ");
/* 2721 */                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("WEBLOGIC-server"));
/* 2722 */                     out.write(" &gt; <span class=\"bcactive\"> ");
/* 2723 */                     if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                       return;
/* 2725 */                     out.write(" </span></td>\n\t");
/*      */                   }
/* 2727 */                   out.write(10);
/* 2728 */                   out.write(9);
/* 2729 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2730 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2734 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2735 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 2738 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2739 */               out.write("\n    </tr>\n</table>\n\n");
/* 2740 */               if (_jspx_meth_c_005fif_005f4(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 2742 */               out.write(10);
/* 2743 */               if (_jspx_meth_c_005fif_005f5(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 2745 */               out.write(10);
/* 2746 */               JspRuntimeLibrary.include(request, response, "/reconfigure.do?method=getWlogicConfiguration&reconfig=true&include=true", out, false);
/* 2747 */               out.write(10);
/* 2748 */               out.write(32);
/* 2749 */               out.write(32);
/* 2750 */               JspRuntimeLibrary.include(request, response, "/jsp/ConfigureWlogic.jsp?reconfig=true", out, false);
/* 2751 */               out.write("\n </div>\n");
/*      */               
/* 2753 */               org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
/* 2754 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2755 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/* 2756 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2757 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 2759 */                   out.write(10);
/*      */                   
/* 2761 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2762 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2763 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/* 2765 */                   _jspx_th_c_005fwhen_005f0.setTest("${ param.alert!='true' && param.all!='true' }");
/* 2766 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2767 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/* 2769 */                       out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"60%\" valign=\"top\">\n      ");
/*      */                       
/*      */ 
/*      */ 
/* 2773 */                       String version = data.getProperty("version");
/* 2774 */                       String state = data.getProperty("state");
/* 2775 */                       String port = data.getProperty("port");
/* 2776 */                       String activation = data.getProperty("activationTime");
/* 2777 */                       long obj = 0L;
/* 2778 */                       if (activation != null)
/*      */                       {
/* 2780 */                         obj = Long.parseLong(activation);
/*      */                       }
/*      */                       
/*      */ 
/* 2784 */                       out.write("\n      <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n        <tr>\n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2785 */                       out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2786 */                       out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 2787 */                       out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2788 */                       out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 2789 */                       out.print(getTrimmedText(monitor_name, 40));
/* 2790 */                       out.write("</td>\n        </tr>\n\t\t");
/* 2791 */                       out.write("<!--$Id$-->\n");
/*      */                       
/* 2793 */                       String hostName = "localhost";
/*      */                       try {
/* 2795 */                         hostName = java.net.InetAddress.getLocalHost().getHostName();
/*      */                       } catch (Exception ex) {
/* 2797 */                         ex.printStackTrace();
/*      */                       }
/* 2799 */                       String portNumber = System.getProperty("webserver.port");
/* 2800 */                       String styleClass = "monitorinfoodd";
/* 2801 */                       if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2802 */                         styleClass = "whitegrayborder-conf-mon";
/*      */                       }
/*      */                       
/* 2805 */                       out.write(10);
/*      */                       
/* 2807 */                       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2808 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2809 */                       _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 2811 */                       _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2812 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2813 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2815 */                           out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2816 */                           out.print(styleClass);
/* 2817 */                           out.write(34);
/* 2818 */                           out.write(62);
/* 2819 */                           out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2820 */                           out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2821 */                           out.print(styleClass);
/* 2822 */                           out.write(34);
/* 2823 */                           out.write(62);
/* 2824 */                           out.print(hostName);
/* 2825 */                           out.write(95);
/* 2826 */                           out.print(portNumber);
/* 2827 */                           out.write("</td>\n</tr>\n");
/* 2828 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2829 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2833 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2834 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                       }
/*      */                       
/* 2837 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2838 */                       out.write(10);
/* 2839 */                       out.write("\n        <tr>\n          <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2840 */                       out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2841 */                       out.write("</td>\n\t");
/* 2842 */                       if (resourceType.equals("WEBLOGIC-Integration")) {
/* 2843 */                         out.write("\n          <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2844 */                         out.print(resID);
/* 2845 */                         out.write("&attributeid=6102')\">");
/* 2846 */                         out.print(getSeverityImageForHealth(alert.getProperty(resID + "#" + "6102")));
/* 2847 */                         out.write("</a>\n\t\t   ");
/* 2848 */                         out.print(getHideAndShowRCAMessage(alert.getProperty(resID + "#" + "6102" + "#" + "MESSAGE"), "6102", alert.getProperty(resID + "#" + "6102"), resID));
/* 2849 */                         out.write("\n\t\t   ");
/* 2850 */                         if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resID, "6102") != 0) {
/* 2851 */                           out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2852 */                           out.print(resID + "_6102");
/* 2853 */                           out.write("&monitortype=WEBLOGIC-Integration')\">");
/* 2854 */                           out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2855 */                           out.write("</a></span>\n           ");
/*      */                         }
/* 2857 */                         out.write("\n\t\t  </td>\n\t");
/*      */                       } else {
/* 2859 */                         out.write("\n          <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2860 */                         out.print(resID);
/* 2861 */                         out.write("&attributeid=218')\">");
/* 2862 */                         out.print(getSeverityImageForHealth(alert.getProperty(resID + "#" + "218")));
/* 2863 */                         out.write("</a>\n\t\t   ");
/* 2864 */                         out.print(getHideAndShowRCAMessage(alert.getProperty(resID + "#" + "218" + "#" + "MESSAGE"), "218", alert.getProperty(resID + "#" + "218"), resID));
/* 2865 */                         out.write("\n\t\t   ");
/* 2866 */                         if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resID, "218") != 0) {
/* 2867 */                           out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2868 */                           out.print(resID + "_218");
/* 2869 */                           out.write("&monitortype=WEBLOGIC-server')\">");
/* 2870 */                           out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2871 */                           out.write("</a></span>\n           ");
/*      */                         }
/* 2873 */                         out.write(10);
/* 2874 */                         out.write(9);
/*      */                       }
/* 2876 */                       out.write("\n\t\t  </td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 2877 */                       out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2878 */                       out.write("</td>\n\t");
/* 2879 */                       if (resourceType.equals("WEBLOGIC-Integration")) {
/* 2880 */                         out.write("\n           <td class=\"monitorinfoodd\">");
/* 2881 */                         out.print(FormatUtil.getString("am.webclient.wli.name.text"));
/* 2882 */                         out.write("</td>\n\t");
/*      */                       } else {
/* 2884 */                         out.write("\n           <td class=\"monitorinfoodd\">");
/* 2885 */                         out.print(FormatUtil.getString("WebLogic Server"));
/* 2886 */                         out.write("</td>\n\t");
/*      */                       }
/* 2888 */                       out.write("\n        </tr>\n\t\t");
/*      */                       
/* 2890 */                       if ((version != null) && (state != null) && (port != null) && (activation != null))
/*      */                       {
/*      */ 
/* 2893 */                         out.write("\n        <tr>\n\t");
/* 2894 */                         if (resourceType.equals("WEBLOGIC-Integration")) {
/* 2895 */                           out.write("\n           <td class=\"monitorinfoodd\">");
/* 2896 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.wli.version"));
/* 2897 */                           out.write("</td>\n\t");
/*      */                         } else {
/* 2899 */                           out.write("\n          <td class=\"monitorinfoeven\">");
/* 2900 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.weblogicversion"));
/* 2901 */                           out.write("</td>\n\t");
/*      */                         }
/* 2903 */                         out.write("\n          <td class=\"monitorinfoeven\"><a class=\"tooltip\" title=\"");
/* 2904 */                         out.print(version);
/* 2905 */                         out.write("\">\n\t\t  ");
/* 2906 */                         out.print(getTrimmedText(version, 40));
/* 2907 */                         out.write("</a></td>\n        </tr>\n        <!--\n        <tr>\n          <td class=\"monitorinfoodd\">State</td>\n          <td class=\"monitorinfoodd\">");
/* 2908 */                         out.print(state);
/* 2909 */                         out.write("</td>\n        </tr>-->\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 2910 */                         out.print(FormatUtil.getString("am.webclient.common.listenport.text"));
/* 2911 */                         out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 2912 */                         out.print(port);
/* 2913 */                         out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 2914 */                         out.print(FormatUtil.getString("am.webclient.common.activationtime.text"));
/* 2915 */                         out.write(" </td>\n          <td class=\"monitorinfoeven\">");
/* 2916 */                         out.print(formatDT("" + obj));
/* 2917 */                         out.write("</td>\n        </tr>\n\t\t");
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 2922 */                       out.write("\n        ");
/*      */                       
/* 2924 */                       EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2925 */                       _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2926 */                       _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 2928 */                       _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2929 */                       int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2930 */                       if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                         for (;;) {
/* 2932 */                           out.write("\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 2933 */                           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2934 */                           out.write("</td>\n          <td class=\"monitorinfoodd\">-&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 2935 */                           out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2936 */                           out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        ");
/* 2937 */                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2938 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2942 */                       if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2943 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                       }
/*      */                       
/* 2946 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2947 */                       out.write(32);
/*      */                       
/* 2949 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2950 */                       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2951 */                       _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 2953 */                       _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 2954 */                       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2955 */                       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                         for (;;) {
/* 2957 */                           out.write("\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 2958 */                           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2959 */                           out.write("</td>\n          ");
/*      */                           
/* 2961 */                           if (systeminfo.get("host_resid") != null)
/*      */                           {
/* 2963 */                             out.write("\n\t\t    <td class=\"monitorinfoodd\"><a href=\"showresource.do?resourceid=");
/* 2964 */                             out.print(systeminfo.get("host_resid"));
/* 2965 */                             out.write("&method=showResourceForResourceID\" class=\"staticlinks\" title=\"");
/* 2966 */                             out.print(systeminfo.get("HOSTNAME"));
/* 2967 */                             out.write(34);
/* 2968 */                             out.write(32);
/* 2969 */                             out.write(62);
/* 2970 */                             out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2971 */                             out.write("&nbsp;(");
/* 2972 */                             out.print(systeminfo.get("HOSTIP"));
/* 2973 */                             out.write(")</a></td>\n\t\t\t");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 2978 */                             out.write("\n             <td class=\"monitorinfoodd\" title=\"");
/* 2979 */                             out.print(systeminfo.get("HOSTNAME"));
/* 2980 */                             out.write(34);
/* 2981 */                             out.write(32);
/* 2982 */                             out.write(62);
/* 2983 */                             out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2984 */                             out.write("&nbsp;(");
/* 2985 */                             out.print(systeminfo.get("HOSTIP"));
/* 2986 */                             out.write(")</td>\n\t\t\t");
/*      */                           }
/* 2988 */                           out.write("\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 2989 */                           out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2990 */                           out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 2991 */                           out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 2992 */                           out.write("</td>\n        </tr>\n        ");
/* 2993 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2994 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2998 */                       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2999 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                       }
/*      */                       
/* 3002 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3003 */                       out.write("\n        ");
/*      */                       
/* 3005 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3006 */                       _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3007 */                       _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3009 */                       _jspx_th_logic_005fnotEmpty_005f1.setName("systeminfo");
/* 3010 */                       int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3011 */                       if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                         for (;;) {
/* 3013 */                           out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3014 */                           out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3015 */                           out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 3016 */                           out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3017 */                           out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3018 */                           out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3019 */                           out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3020 */                           out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3021 */                           out.write("</td>\n        </tr>\n        ");
/* 3022 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3023 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3027 */                       if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3028 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                       }
/*      */                       
/* 3031 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3032 */                       out.write("\n        ");
/*      */                       
/* 3034 */                       EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3035 */                       _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3036 */                       _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3038 */                       _jspx_th_logic_005fempty_005f1.setName("systeminfo");
/* 3039 */                       int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3040 */                       if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                         for (;;) {
/* 3042 */                           out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3043 */                           out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3044 */                           out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3045 */                           out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3046 */                           out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        ");
/* 3047 */                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3048 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3052 */                       if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3053 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                       }
/*      */                       
/* 3056 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3057 */                       out.write(10);
/* 3058 */                       out.write(9);
/* 3059 */                       out.write(9);
/* 3060 */                       JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 3061 */                       out.write("\n        </table>\n                 ");
/*      */                       
/* 3063 */                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3064 */                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3065 */                       _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3067 */                       _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3068 */                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3069 */                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                         for (;;) {
/* 3071 */                           out.write(10);
/* 3072 */                           out.write(9);
/* 3073 */                           out.write(9);
/*      */                           
/* 3075 */                           IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3076 */                           _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3077 */                           _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                           
/* 3079 */                           _jspx_th_c_005fif_005f6.setTest("${showdata=='1'}");
/* 3080 */                           int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3081 */                           if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                             for (;;) {
/* 3083 */                               out.write("\n\n\t\t<div align=\"center\"><a style=cursor:pointer;><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"javascript:toggleDiv('Reconfigure')\">\n\n            <tr>\n              <td>&nbsp;</td>\n            </tr>\n            <tr>\n              <td><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"getmoredatatable\">\n                  <tr>\n                    <td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img src=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\" border=\"0\" vspace=\"2\" hspace=\"5\"></td>\n                    <td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 3084 */                               out.print(FormatUtil.getString("am.webclient.dcprogress.appservers.text"));
/* 3085 */                               out.write("</td>\n                  </tr>\n                </table></td>\n            </tr>\n      </table></a></div>\n\n\t\t");
/* 3086 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3087 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3091 */                           if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3092 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                           }
/*      */                           
/* 3095 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3096 */                           out.write(10);
/* 3097 */                           out.write(9);
/* 3098 */                           out.write(9);
/* 3099 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3100 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3104 */                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3105 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                       }
/*      */                       
/* 3108 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3109 */                       out.write("\n    </td>\n    <td width=\"40%\" valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n          <td colspan=\"6\" class=\"tableheadingbborder\">");
/* 3110 */                       out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3111 */                       out.write("</td>\n        </tr>\n        <tr>\n          <td colspan=\"4\" align=\"right\"><table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3112 */                       if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3114 */                       out.write("&period=1&resourcename=");
/* 3115 */                       if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3117 */                       out.write("')\">\n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3118 */                       out.print(seven_days_text);
/* 3119 */                       out.write("\"></a></td>\n                <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3120 */                       if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3122 */                       out.write("&period=2&resourcename=");
/* 3123 */                       if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3125 */                       out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3126 */                       out.print(thiry_days_text);
/* 3127 */                       out.write("\"></a></td>\n              </tr>\n            </table></td>\n        </tr>\n        <tr>\n          <td colspan=\"4\" align=\"center\">\n            ");
/*      */                       
/* 3129 */                       wlsGraph.setParam(resID, "AVAILABILITY");
/*      */                       
/* 3131 */                       out.write("\n            ");
/*      */                       
/* 3133 */                       AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3134 */                       _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3135 */                       _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3137 */                       _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                       
/* 3139 */                       _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */                       
/* 3141 */                       _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                       
/* 3143 */                       _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                       
/* 3145 */                       _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                       
/* 3147 */                       _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                       
/* 3149 */                       _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3150 */                       int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3151 */                       if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3152 */                         if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3153 */                           out = _jspx_page_context.pushBody();
/* 3154 */                           _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3155 */                           _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3158 */                           out.write("\n            ");
/*      */                           
/* 3160 */                           Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3161 */                           _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3162 */                           _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                           
/* 3164 */                           _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3165 */                           int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3166 */                           if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3167 */                             if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3168 */                               out = _jspx_page_context.pushBody();
/* 3169 */                               _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3170 */                               _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3173 */                               out.write(32);
/*      */                               
/* 3175 */                               AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3176 */                               _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3177 */                               _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                               
/* 3179 */                               _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                               
/* 3181 */                               _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3182 */                               int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3183 */                               if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3184 */                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                               }
/*      */                               
/* 3187 */                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3188 */                               out.write("\n            ");
/*      */                               
/* 3190 */                               AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3191 */                               _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3192 */                               _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                               
/* 3194 */                               _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                               
/* 3196 */                               _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3197 */                               int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3198 */                               if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3199 */                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                               }
/*      */                               
/* 3202 */                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3203 */                               out.write(32);
/* 3204 */                               int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3205 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3208 */                             if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3209 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3212 */                           if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3213 */                             this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                           }
/*      */                           
/* 3216 */                           this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3217 */                           out.write(32);
/* 3218 */                           int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3219 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3222 */                         if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3223 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3226 */                       if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3227 */                         this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                       }
/*      */                       
/* 3230 */                       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3231 */                       out.write("\n          </td>\n        </tr>\n\n\t<tr>\n\t\t<td width=\"45%\" colspan=\"2\"  class=\"yellowgrayborder\">");
/* 3232 */                       out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3233 */                       out.write(" :\n\t \t");
/* 3234 */                       if (resourceType.equals("WEBLOGIC-Integration")) {
/* 3235 */                         out.write("\n\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3236 */                         out.print(resID);
/* 3237 */                         out.write("&attributeid=6101')\">");
/* 3238 */                         out.print(getSeverityImageForAvailability(alert.getProperty(resID + "#" + "6101")));
/* 3239 */                         out.write("</a>\n\t\t</td>\n\t \t<td width=\"54%\" colspan=\"2\" class=\"yellowgrayborder\" align=\"right\">\n\t \t\t<img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;\n\t \t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3240 */                         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                           return;
/* 3242 */                         out.write("&attributeIDs=6101,6102&attributeToSelect=6101&redirectto=");
/* 3243 */                         out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 3244 */                         out.write("'class=\"links\">");
/* 3245 */                         out.print(ALERTCONFIG_TEXT);
/* 3246 */                         out.write("</a>&nbsp;\n\t \t</td>\n\t \t");
/*      */                       } else {
/* 3248 */                         out.write("\n\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3249 */                         out.print(resID);
/* 3250 */                         out.write("&attributeid=217')\">");
/* 3251 */                         out.print(getSeverityImageForAvailability(alert.getProperty(resID + "#" + "217")));
/* 3252 */                         out.write("</a>\n\t\t</td>\n\t \t<td width=\"54%\" colspan=\"2\" class=\"yellowgrayborder\" align=\"right\">\n\t \t\t<img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;\n\t \t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3253 */                         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                           return;
/* 3255 */                         out.write("&attributeIDs=217,218&attributeToSelect=217&redirectto=");
/* 3256 */                         out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 3257 */                         out.write("'class=\"links\">");
/* 3258 */                         out.print(ALERTCONFIG_TEXT);
/* 3259 */                         out.write("</a>&nbsp;\n\t \t</td>\n\t \t  ");
/*      */                       }
/* 3261 */                       out.write("\n\n        </tr>\n\n      </table>\n    </td>\n  </tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3262 */                       JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 3263 */                       out.write("</td></tr></table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n  </table>\n  ");
/*      */                       
/* 3265 */                       IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3266 */                       _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3267 */                       _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3269 */                       _jspx_th_c_005fif_005f7.setTest("${showdata=='1' }");
/* 3270 */                       int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3271 */                       if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                         for (;;) {
/* 3273 */                           out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3274 */                           out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3275 */                           out.write(32);
/* 3276 */                           out.write(45);
/* 3277 */                           out.write(32);
/* 3278 */                           out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3279 */                           out.write("&nbsp;</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n  <td width=\"405\" height=\"127\" valign=\"top\">\n  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n  <tr>\n  ");
/* 3280 */                           if (resourceType.equals("WEBLOGIC-Integration")) {
/* 3281 */                             out.write("\n  <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3282 */                             if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3284 */                             out.write("&attributeid=6103&period=-7&resourcename=");
/* 3285 */                             if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3287 */                             out.write("')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3288 */                             out.print(seven_days_text);
/* 3289 */                             out.write("\"></a></td>\n  <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3290 */                             if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3292 */                             out.write("&attributeid=6103&period=-30&resourcename=");
/* 3293 */                             if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3295 */                             out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3296 */                             out.print(thiry_days_text);
/* 3297 */                             out.write("\"></a></td>\n  ");
/*      */                           } else {
/* 3299 */                             out.write("\n  <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3300 */                             if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3302 */                             out.write("&attributeid=216&period=-7&resourcename=");
/* 3303 */                             if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3305 */                             out.write("')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3306 */                             out.print(seven_days_text);
/* 3307 */                             out.write("\"></a></td>\n  <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3308 */                             if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3310 */                             out.write("&attributeid=216&period=-30&resourcename=");
/* 3311 */                             if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3313 */                             out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3314 */                             out.print(thiry_days_text);
/* 3315 */                             out.write("\"></a></td>\n  ");
/*      */                           }
/* 3317 */                           out.write("\n  </tr>\n  ");
/*      */                           
/* 3319 */                           data = dataHandler.getResponseTimePerformanceData(resID);
/* 3320 */                           String min = data.getProperty("min");
/* 3321 */                           String max = data.getProperty("max");
/* 3322 */                           String avg = data.getProperty("avg");
/* 3323 */                           boolean resTimeDataPresent = (min != null) && (max != null) && (avg != null);
/* 3324 */                           pageContext.setAttribute("restime", data);
/* 3325 */                           wlsGraph.setParam(resID, "RESPONSETIME");
/*      */                           
/* 3327 */                           out.write("\n  <tr>\n  <td colspan=\"2\">\n  ");
/*      */                           
/* 3329 */                           TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3330 */                           _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3331 */                           _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f7);
/*      */                           
/* 3333 */                           _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wlsGraph");
/*      */                           
/* 3335 */                           _jspx_th_awolf_005ftimechart_005f0.setWidth("300");
/*      */                           
/* 3337 */                           _jspx_th_awolf_005ftimechart_005f0.setHeight("150");
/*      */                           
/* 3339 */                           _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                           
/* 3341 */                           _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                           
/* 3343 */                           _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_restime);
/*      */                           
/* 3345 */                           _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 3346 */                           int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3347 */                           if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3348 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3349 */                               out = _jspx_page_context.pushBody();
/* 3350 */                               _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3351 */                               _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3354 */                               out.write(10);
/* 3355 */                               out.write(32);
/* 3356 */                               out.write(32);
/* 3357 */                               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3358 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3361 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3362 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3365 */                           if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3366 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                           }
/*      */                           
/* 3369 */                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3370 */                           out.write("\n  </tr>\n  </table></td>\n  <td width=\"562\" valign=\"top\"> <br> <br>\n  <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n  <tr>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3371 */                           if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                             return;
/* 3373 */                           out.write("</span></td>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3374 */                           if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                             return;
/* 3376 */                           out.write("</span></td>\n  <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3377 */                           if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                             return;
/* 3379 */                           out.write("</span></td>\n  </tr>\n  <tr>\n  <td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3380 */                           out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 3381 */                           out.write(32);
/* 3382 */                           out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3383 */                           out.write("\n  </td>\n  <td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n  ");
/* 3384 */                           if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                             return;
/* 3386 */                           out.write(10);
/* 3387 */                           out.write(32);
/* 3388 */                           out.write(32);
/*      */                           
/* 3390 */                           IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3391 */                           _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3392 */                           _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f7);
/*      */                           
/* 3394 */                           _jspx_th_c_005fif_005f9.setTest("${responsetime !='-1'}");
/* 3395 */                           int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3396 */                           if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                             for (;;) {
/* 3398 */                               out.write(10);
/* 3399 */                               if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                                 return;
/* 3401 */                               out.print(FormatUtil.getString("ms"));
/* 3402 */                               out.write(10);
/* 3403 */                               out.write(32);
/* 3404 */                               out.write(32);
/* 3405 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3406 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3410 */                           if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3411 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                           }
/*      */                           
/* 3414 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3415 */                           out.write("\n  </td>\n  <td class=\"whitegrayborder\" width=\"29%\" >\n  ");
/* 3416 */                           if (resourceType.equals("WEBLOGIC-Integration")) {
/* 3417 */                             out.write("\n  <a href=\"javascript:void(0)\" class=\"yellowgrayborder\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3418 */                             if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3420 */                             out.write("&attributeid=6103')\">");
/* 3421 */                             out.print(getSeverityImage(alert.getProperty(resID + "#" + "6103")));
/* 3422 */                             out.write("</a>\n ");
/*      */                           } else {
/* 3424 */                             out.write("\n  <a href=\"javascript:void(0)\" class=\"yellowgrayborder\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3425 */                             if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3427 */                             out.write("&attributeid=216')\">");
/* 3428 */                             out.print(getSeverityImage(alert.getProperty(resID + "#" + "216")));
/* 3429 */                             out.write("</a>\n  ");
/*      */                           }
/* 3431 */                           out.write("\n  </td>\n  </tr>\n  <tr>\n  ");
/* 3432 */                           if (resourceType.equals("WEBLOGIC-Integration")) {
/* 3433 */                             out.write("\n  <td  colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3434 */                             if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3436 */                             out.write("&attributeIDs=6103&attributeToSelect=6103&redirectto=");
/* 3437 */                             out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 3438 */                             out.write("'  class=\"staticlinks\">");
/* 3439 */                             out.print(ALERTCONFIG_TEXT);
/* 3440 */                             out.write("</a></td>\n ");
/*      */                           } else {
/* 3442 */                             out.write("\n  <td  colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3443 */                             if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 3445 */                             out.write("&attributeIDs=216&attributeToSelect=216&redirectto=");
/* 3446 */                             out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 3447 */                             out.write("'  class=\"staticlinks\">");
/* 3448 */                             out.print(ALERTCONFIG_TEXT);
/* 3449 */                             out.write("</a></td>\n  ");
/*      */                           }
/* 3451 */                           out.write("\n  </tr>\n  </table>\n  </td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n  <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n  </tr>\n  </table>\n  ");
/* 3452 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3453 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3457 */                       if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3458 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                       }
/*      */                       
/* 3461 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3462 */                       out.write(10);
/* 3463 */                       out.write(32);
/* 3464 */                       out.write(32);
/*      */                       
/* 3466 */                       IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3467 */                       _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3468 */                       _jspx_th_c_005fif_005f10.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3470 */                       _jspx_th_c_005fif_005f10.setTest("${showdata=='2'}");
/* 3471 */                       int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3472 */                       if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                         for (;;) {
/* 3474 */                           out.write(10);
/*      */                           
/* 3476 */                           data = dataHandler.getJVMPerformanceData(resID);
/* 3477 */                           String jvmmin = data.getProperty("min");
/* 3478 */                           String jvmmax = data.getProperty("max");
/* 3479 */                           String jvmavg = data.getProperty("avg");
/* 3480 */                           String total = data.getProperty("total");
/* 3481 */                           boolean jvmDataPresent = (jvmmin != null) && (jvmmax != null) && (jvmavg != null) && (total != null);
/*      */                           
/* 3483 */                           data = dataHandler.getResponseTimePerformanceData(resID);
/* 3484 */                           String min = data.getProperty("min");
/* 3485 */                           String max = data.getProperty("max");
/* 3486 */                           String avg = data.getProperty("avg");
/* 3487 */                           boolean resTimeDataPresent = (min != null) && (max != null) && (avg != null);
/* 3488 */                           pageContext.setAttribute("restime", data);
/*      */                           
/* 3490 */                           if (!jvmDataPresent)
/*      */                           {
/* 3492 */                             out.write("\n        <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3493 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3494 */                             out.write(32);
/* 3495 */                             out.write(45);
/* 3496 */                             out.write(32);
/* 3497 */                             out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3498 */                             out.write("&nbsp;</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n  <td width=\"405\" height=\"127\" valign=\"top\">\n  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n  <tr>\n  ");
/* 3499 */                             if (resourceType.equals("WEBLOGIC-Integration")) {
/* 3500 */                               out.write("\n  <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3501 */                               if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3503 */                               out.write("&attributeid=6103&period=-7&resourcename=");
/* 3504 */                               if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3506 */                               out.write("')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3507 */                               out.print(seven_days_text);
/* 3508 */                               out.write("\"></a></td>\n  <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3509 */                               if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3511 */                               out.write("&attributeid=6103&period=-30&resourcename=");
/* 3512 */                               if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3514 */                               out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3515 */                               out.print(thiry_days_text);
/* 3516 */                               out.write("\"></a></td>\n\t  ");
/*      */                             } else {
/* 3518 */                               out.write("\n  <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3519 */                               if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3521 */                               out.write("&attributeid=216&period=-7&resourcename=");
/* 3522 */                               if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3524 */                               out.write("')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3525 */                               out.print(seven_days_text);
/* 3526 */                               out.write("\"></a></td>\n  <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3527 */                               if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3529 */                               out.write("&attributeid=216&period=-30&resourcename=");
/* 3530 */                               if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3532 */                               out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3533 */                               out.print(thiry_days_text);
/* 3534 */                               out.write("\"></a></td>\n\t  ");
/*      */                             }
/* 3536 */                             out.write("\n  </tr>\n  ");
/*      */                             
/* 3538 */                             wlsGraph.setParam(resID, "RESPONSETIME");
/*      */                             
/* 3540 */                             out.write("\n  <tr>\n  <td colspan=\"2\">\n  ");
/*      */                             
/* 3542 */                             TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3543 */                             _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3544 */                             _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 3546 */                             _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("wlsGraph");
/*      */                             
/* 3548 */                             _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                             
/* 3550 */                             _jspx_th_awolf_005ftimechart_005f1.setHeight("150");
/*      */                             
/* 3552 */                             _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                             
/* 3554 */                             _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                             
/* 3556 */                             _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(yaxis_restime);
/*      */                             
/* 3558 */                             _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 3559 */                             int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3560 */                             if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3561 */                               if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3562 */                                 out = _jspx_page_context.pushBody();
/* 3563 */                                 _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3564 */                                 _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3567 */                                 out.write(10);
/* 3568 */                                 out.write(32);
/* 3569 */                                 out.write(32);
/* 3570 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3571 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3574 */                               if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3575 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3578 */                             if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3579 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                             }
/*      */                             
/* 3582 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3583 */                             out.write("\n  </tr>\n  </table></td>\n  <td width=\"562\" valign=\"top\"> <br> <br>\n  <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n  <tr>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3584 */                             if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 3586 */                             out.write("</span></td>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3587 */                             if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 3589 */                             out.write("</span></td>\n  <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3590 */                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 3592 */                             out.write("</span></td>\n  </tr>\n  <tr>\n  <td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3593 */                             out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 3594 */                             out.write(32);
/* 3595 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3596 */                             out.write("\n  </td>\n  <td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n  ");
/* 3597 */                             if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 3599 */                             out.write(10);
/* 3600 */                             out.write(32);
/* 3601 */                             out.write(32);
/*      */                             
/* 3603 */                             IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3604 */                             _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3605 */                             _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 3607 */                             _jspx_th_c_005fif_005f12.setTest("${responsetime !='-1'}");
/* 3608 */                             int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3609 */                             if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                               for (;;) {
/* 3611 */                                 out.write(10);
/* 3612 */                                 out.write(32);
/* 3613 */                                 out.write(32);
/* 3614 */                                 if (_jspx_meth_fmt_005fformatNumber_005f1(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*      */                                   return;
/* 3616 */                                 out.write(32);
/* 3617 */                                 out.print(FormatUtil.getString("ms"));
/* 3618 */                                 out.write(10);
/* 3619 */                                 out.write(32);
/* 3620 */                                 out.write(32);
/* 3621 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3622 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3626 */                             if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3627 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                             }
/*      */                             
/* 3630 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3631 */                             out.write("\n  </td>\n  <td class=\"whitegrayborder\" width=\"29%\" >\n  ");
/* 3632 */                             if (resourceType.equals("WEBLOGIC-Integration")) {
/* 3633 */                               out.write("\n  <a href=\"javascript:void(0)\" class=\"yellowgrayborder\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3634 */                               if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3636 */                               out.write("&attributeid=6103')\">");
/* 3637 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + "6103")));
/* 3638 */                               out.write("</a>\n\t  ");
/*      */                             } else {
/* 3640 */                               out.write("\n  <a href=\"javascript:void(0)\" class=\"yellowgrayborder\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3641 */                               if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3643 */                               out.write("&attributeid=216')\">");
/* 3644 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + "216")));
/* 3645 */                               out.write("</a>\n\t  ");
/*      */                             }
/* 3647 */                             out.write("\n  </td>\n  </tr>\n  <tr>\n  ");
/* 3648 */                             if (resourceType.equals("WEBLOGIC-Integration")) {
/* 3649 */                               out.write("\n  <td  colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3650 */                               if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3652 */                               out.write("&attributeIDs=6103&attributeToSelect=6103&redirectto=");
/* 3653 */                               out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 3654 */                               out.write("'  class=\"staticlinks\">");
/* 3655 */                               out.print(ALERTCONFIG_TEXT);
/* 3656 */                               out.write("</a></td>\n\t  ");
/*      */                             } else {
/* 3658 */                               out.write("\n  <td  colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3659 */                               if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3661 */                               out.write("&attributeIDs=216&attributeToSelect=216&redirectto=");
/* 3662 */                               out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 3663 */                               out.write("'  class=\"staticlinks\">");
/* 3664 */                               out.print(ALERTCONFIG_TEXT);
/* 3665 */                               out.write("</a></td>\n\t  ");
/*      */                             }
/* 3667 */                             out.write("\n  </tr>\n  </table>\n  </td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n  <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n  </tr>\n  </table>\n\n        ");
/*      */                           }
/* 3669 */                           else if ((jvmDataPresent) && (resTimeDataPresent))
/*      */                           {
/*      */ 
/* 3672 */                             out.write("\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n          <tr>\n\n    <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 3673 */                             out.print(FormatUtil.getString("am.webclient.weblogic.jvmlastonehr.text"));
/* 3674 */                             out.write("</td>\n\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 3675 */                             out.print(FormatUtil.getString("am.webclient.weblogic.responsetimelastonehr.text"));
/* 3676 */                             out.write("</td>\n          </tr>\n        </table>\n\n");
/*      */                             
/* 3678 */                             String yaxis_heap_usage = FormatUtil.getString("am.webclient.weblogic.heapusagekb.text");
/*      */                             
/*      */ 
/* 3681 */                             out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n");
/*      */                             
/* 3683 */                             wlsGraph.setParam(resID, "JVM");
/*      */                             
/* 3685 */                             out.write("\n    <td width=\"50%\" height=\"38\" class=\"rbborder\">\n\t<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n  ");
/* 3686 */                             if (resourceType.equals("WEBLOGIC-Integration")) {
/* 3687 */                               out.write("\n       <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3688 */                               if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3690 */                               out.write("&attributeid=6104&period=-7&resourcename=");
/* 3691 */                               if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3693 */                               out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3694 */                               out.print(seven_days_text);
/* 3695 */                               out.write("\"></a></td>\n\t   <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3696 */                               if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3698 */                               out.write("&attributeid=6104&period=-30&resourcename=");
/* 3699 */                               if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3701 */                               out.write(",740,550')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3702 */                               out.print(thiry_days_text);
/* 3703 */                               out.write("\"></a></td>\n\t  ");
/*      */                             } else {
/* 3705 */                               out.write("\n       <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3706 */                               if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3708 */                               out.write("&attributeid=224&period=-7&resourcename=");
/* 3709 */                               if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3711 */                               out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3712 */                               out.print(seven_days_text);
/* 3713 */                               out.write("\"></a></td>\n\t   <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3714 */                               if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3716 */                               out.write("&attributeid=224&period=-30&resourcename=");
/* 3717 */                               if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3719 */                               out.write(",740,550')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3720 */                               out.print(thiry_days_text);
/* 3721 */                               out.write("\"></a></td>\n\t       ");
/*      */                             }
/* 3723 */                             out.write("\n\t</tr>\n\t<tr>\n\t<td>\n          ");
/*      */                             
/* 3725 */                             TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3726 */                             _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3727 */                             _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 3729 */                             _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("wlsGraph");
/*      */                             
/* 3731 */                             _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */                             
/* 3733 */                             _jspx_th_awolf_005ftimechart_005f2.setHeight("150");
/*      */                             
/* 3735 */                             _jspx_th_awolf_005ftimechart_005f2.setLegend("false");
/*      */                             
/* 3737 */                             _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(xaxis_time);
/*      */                             
/* 3739 */                             _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(yaxis_heap_usage);
/* 3740 */                             int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3741 */                             if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3742 */                               if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3743 */                                 out = _jspx_page_context.pushBody();
/* 3744 */                                 _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3745 */                                 _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3748 */                                 out.write("\n          ");
/* 3749 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3750 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3753 */                               if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3754 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3757 */                             if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3758 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                             }
/*      */                             
/* 3761 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3762 */                             out.write("\n\n    </td>\n\t</tr>\n\t</table>\n");
/*      */                             
/* 3764 */                             wlsGraph.setParam(resID, "RESPONSETIME");
/*      */                             
/* 3766 */                             out.write("\n    <td width=\"50%\" height=\"38\" class=\"bottomborder\">\n\t<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t");
/* 3767 */                             if (resourceType.equals("WEBLOGIC-Integration")) {
/* 3768 */                               out.write("\n       <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3769 */                               if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3771 */                               out.write("&attributeid=6103&period=-7&resourcename=");
/* 3772 */                               if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3774 */                               out.write("')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3775 */                               out.print(seven_days_text);
/* 3776 */                               out.write("\"></a></td>\n\t   <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3777 */                               if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3779 */                               out.write("&attributeid=6103&period=-30&resourcename=");
/* 3780 */                               if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3782 */                               out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3783 */                               out.print(thiry_days_text);
/* 3784 */                               out.write("\"></a></td>\n\n\t");
/*      */                             } else {
/* 3786 */                               out.write("\n       <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3787 */                               if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3789 */                               out.write("&attributeid=216&period=-7&resourcename=");
/* 3790 */                               if (_jspx_meth_c_005fout_005f47(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3792 */                               out.write("')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3793 */                               out.print(seven_days_text);
/* 3794 */                               out.write("\"></a></td>\n\t   <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3795 */                               if (_jspx_meth_c_005fout_005f48(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3797 */                               out.write("&attributeid=216&period=-30&resourcename=");
/* 3798 */                               if (_jspx_meth_c_005fout_005f49(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3800 */                               out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3801 */                               out.print(thiry_days_text);
/* 3802 */                               out.write("\"></a></td>\n   ");
/*      */                             }
/* 3804 */                             out.write("\n\t</tr>\n\t<tr>\n\t<td>\n    ");
/*      */                             
/* 3806 */                             TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3807 */                             _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3808 */                             _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 3810 */                             _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("wlsGraph");
/*      */                             
/* 3812 */                             _jspx_th_awolf_005ftimechart_005f3.setWidth("300");
/*      */                             
/* 3814 */                             _jspx_th_awolf_005ftimechart_005f3.setHeight("150");
/*      */                             
/* 3816 */                             _jspx_th_awolf_005ftimechart_005f3.setLegend("false");
/*      */                             
/* 3818 */                             _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(xaxis_time);
/*      */                             
/* 3820 */                             _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(yaxis_restime);
/* 3821 */                             int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3822 */                             if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 3823 */                               if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3824 */                                 out = _jspx_page_context.pushBody();
/* 3825 */                                 _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 3826 */                                 _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3829 */                                 out.write("\n    ");
/* 3830 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 3831 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3834 */                               if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3835 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3838 */                             if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3839 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                             }
/*      */                             
/* 3842 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3843 */                             out.write("\n\t</td>\n  </tr>\n  </table>\n  </td>\n  <tr>\n    <td valign=\"top\" class=\"rborder\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\n\t<tr>\n          <td width=\"50%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3844 */                             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 3846 */                             out.write("</span></td>\n\t  <td width=\"30%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3847 */                             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 3849 */                             out.write("</span></td>\n\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3850 */                             if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 3852 */                             out.write("</span></td>\n\t</tr>\n \t<tr>\n          <td class=\"whitegrayborder\">");
/* 3853 */                             out.print(FormatUtil.getString("am.webclient.common.minimum.text"));
/* 3854 */                             out.write(32);
/* 3855 */                             out.print(FormatUtil.getString("Heap Size"));
/* 3856 */                             out.write("</td>\n          <td class=\"whitegrayborder\">");
/*      */                             
/* 3858 */                             FormatTag _jspx_th_am_005fFormat_005f0 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 3859 */                             _jspx_th_am_005fFormat_005f0.setPageContext(_jspx_page_context);
/* 3860 */                             _jspx_th_am_005fFormat_005f0.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 3862 */                             _jspx_th_am_005fFormat_005f0.setType("Number");
/* 3863 */                             int _jspx_eval_am_005fFormat_005f0 = _jspx_th_am_005fFormat_005f0.doStartTag();
/* 3864 */                             if (_jspx_eval_am_005fFormat_005f0 != 0) {
/* 3865 */                               if (_jspx_eval_am_005fFormat_005f0 != 1) {
/* 3866 */                                 out = _jspx_page_context.pushBody();
/* 3867 */                                 _jspx_th_am_005fFormat_005f0.setBodyContent((BodyContent)out);
/* 3868 */                                 _jspx_th_am_005fFormat_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3871 */                                 out.print(jvmmin);
/* 3872 */                                 int evalDoAfterBody = _jspx_th_am_005fFormat_005f0.doAfterBody();
/* 3873 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3876 */                               if (_jspx_eval_am_005fFormat_005f0 != 1) {
/* 3877 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3880 */                             if (_jspx_th_am_005fFormat_005f0.doEndTag() == 5) {
/* 3881 */                               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f0); return;
/*      */                             }
/*      */                             
/* 3884 */                             this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f0);
/* 3885 */                             out.write(32);
/* 3886 */                             out.print(FormatUtil.getString("KB"));
/* 3887 */                             out.write("</td>\n\t  <td class=\"whitegrayborder\">&nbsp; </td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 3888 */                             out.print(FormatUtil.getString("am.webclient.common.maximum.text"));
/* 3889 */                             out.write(32);
/* 3890 */                             out.print(FormatUtil.getString("Heap Size"));
/* 3891 */                             out.write("</td>\n          <td class=\"yellowgrayborder\">");
/*      */                             
/* 3893 */                             FormatTag _jspx_th_am_005fFormat_005f1 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 3894 */                             _jspx_th_am_005fFormat_005f1.setPageContext(_jspx_page_context);
/* 3895 */                             _jspx_th_am_005fFormat_005f1.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 3897 */                             _jspx_th_am_005fFormat_005f1.setType("Number");
/* 3898 */                             int _jspx_eval_am_005fFormat_005f1 = _jspx_th_am_005fFormat_005f1.doStartTag();
/* 3899 */                             if (_jspx_eval_am_005fFormat_005f1 != 0) {
/* 3900 */                               if (_jspx_eval_am_005fFormat_005f1 != 1) {
/* 3901 */                                 out = _jspx_page_context.pushBody();
/* 3902 */                                 _jspx_th_am_005fFormat_005f1.setBodyContent((BodyContent)out);
/* 3903 */                                 _jspx_th_am_005fFormat_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3906 */                                 out.print(jvmmax);
/* 3907 */                                 int evalDoAfterBody = _jspx_th_am_005fFormat_005f1.doAfterBody();
/* 3908 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3911 */                               if (_jspx_eval_am_005fFormat_005f1 != 1) {
/* 3912 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3915 */                             if (_jspx_th_am_005fFormat_005f1.doEndTag() == 5) {
/* 3916 */                               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f1); return;
/*      */                             }
/*      */                             
/* 3919 */                             this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f1);
/* 3920 */                             out.write(32);
/* 3921 */                             out.print(FormatUtil.getString("KB"));
/* 3922 */                             out.write("</td>\n\t  <td class=\"yellowgrayborder\">&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3923 */                             out.print(FormatUtil.getString("am.webclient.common.average.text"));
/* 3924 */                             out.write(32);
/* 3925 */                             out.print(FormatUtil.getString("Heap Size"));
/* 3926 */                             out.write("</td>\n          <td class=\"whitegrayborder\">");
/*      */                             
/* 3928 */                             FormatTag _jspx_th_am_005fFormat_005f2 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 3929 */                             _jspx_th_am_005fFormat_005f2.setPageContext(_jspx_page_context);
/* 3930 */                             _jspx_th_am_005fFormat_005f2.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 3932 */                             _jspx_th_am_005fFormat_005f2.setType("Number");
/* 3933 */                             int _jspx_eval_am_005fFormat_005f2 = _jspx_th_am_005fFormat_005f2.doStartTag();
/* 3934 */                             if (_jspx_eval_am_005fFormat_005f2 != 0) {
/* 3935 */                               if (_jspx_eval_am_005fFormat_005f2 != 1) {
/* 3936 */                                 out = _jspx_page_context.pushBody();
/* 3937 */                                 _jspx_th_am_005fFormat_005f2.setBodyContent((BodyContent)out);
/* 3938 */                                 _jspx_th_am_005fFormat_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3941 */                                 out.print(jvmavg);
/* 3942 */                                 int evalDoAfterBody = _jspx_th_am_005fFormat_005f2.doAfterBody();
/* 3943 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3946 */                               if (_jspx_eval_am_005fFormat_005f2 != 1) {
/* 3947 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3950 */                             if (_jspx_th_am_005fFormat_005f2.doEndTag() == 5) {
/* 3951 */                               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f2); return;
/*      */                             }
/*      */                             
/* 3954 */                             this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f2);
/* 3955 */                             out.write(32);
/* 3956 */                             out.print(FormatUtil.getString("KB"));
/* 3957 */                             out.write("</td>\n\t  <td class=\"whitegrayborder\">&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 3958 */                             out.print(FormatUtil.getString("am.webclient.common.total.text"));
/* 3959 */                             out.write(32);
/* 3960 */                             out.print(FormatUtil.getString("Heap Size"));
/* 3961 */                             out.write(" </td>\n          <td class=\"yellowgrayborder\">");
/*      */                             
/* 3963 */                             FormatTag _jspx_th_am_005fFormat_005f3 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 3964 */                             _jspx_th_am_005fFormat_005f3.setPageContext(_jspx_page_context);
/* 3965 */                             _jspx_th_am_005fFormat_005f3.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 3967 */                             _jspx_th_am_005fFormat_005f3.setType("Number");
/* 3968 */                             int _jspx_eval_am_005fFormat_005f3 = _jspx_th_am_005fFormat_005f3.doStartTag();
/* 3969 */                             if (_jspx_eval_am_005fFormat_005f3 != 0) {
/* 3970 */                               if (_jspx_eval_am_005fFormat_005f3 != 1) {
/* 3971 */                                 out = _jspx_page_context.pushBody();
/* 3972 */                                 _jspx_th_am_005fFormat_005f3.setBodyContent((BodyContent)out);
/* 3973 */                                 _jspx_th_am_005fFormat_005f3.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3976 */                                 out.print(total);
/* 3977 */                                 int evalDoAfterBody = _jspx_th_am_005fFormat_005f3.doAfterBody();
/* 3978 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3981 */                               if (_jspx_eval_am_005fFormat_005f3 != 1) {
/* 3982 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3985 */                             if (_jspx_th_am_005fFormat_005f3.doEndTag() == 5) {
/* 3986 */                               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f3); return;
/*      */                             }
/*      */                             
/* 3989 */                             this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f3);
/* 3990 */                             out.write(32);
/* 3991 */                             out.print(FormatUtil.getString("KB"));
/* 3992 */                             out.write("</td>\n\t  <td class=\"yellowgrayborder\">&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3993 */                             out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 3994 */                             out.write(32);
/* 3995 */                             out.print(FormatUtil.getString("Heap Size"));
/* 3996 */                             out.write(" </td>\n          <td class=\"whitegrayborder\">");
/* 3997 */                             if (_jspx_meth_c_005fout_005f50(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 3999 */                             out.write("</td>\n\t  <td  class=\"whitegrayborder\"> &nbsp;\n\t  ");
/* 4000 */                             if (resourceType.equals("WEBLOGIC-Integration")) {
/* 4001 */                               out.write("\n          <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4002 */                               if (_jspx_meth_c_005fout_005f51(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 4004 */                               out.write("&attributeid=6104')\">\n          ");
/* 4005 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + "6104")));
/* 4006 */                               out.write("</td>\n\t\t  ");
/*      */                             } else {
/* 4008 */                               out.write("\n          <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4009 */                               if (_jspx_meth_c_005fout_005f52(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 4011 */                               out.write("&attributeid=224')\">\n          ");
/* 4012 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + "224")));
/* 4013 */                               out.write("</td>\n\t  ");
/*      */                             }
/* 4015 */                             out.write("\n        </tr>\n        <tr align=\"right\">\n\t  ");
/* 4016 */                             if (resourceType.equals("WEBLOGIC-Integration")) {
/* 4017 */                               out.write("\n          <td colspan=\"3\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4018 */                               if (_jspx_meth_c_005fout_005f53(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 4020 */                               out.write("&attributeIDs=6104&attributeToSelect=6104&redirectto=");
/* 4021 */                               out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 4022 */                               out.write("'class=\"staticlinks\">");
/* 4023 */                               out.print(ALERTCONFIG_TEXT);
/* 4024 */                               out.write("</td>\n\t\t  ");
/*      */                             } else {
/* 4026 */                               out.write("\n          <td colspan=\"3\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4027 */                               if (_jspx_meth_c_005fout_005f54(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 4029 */                               out.write("&attributeIDs=224&attributeToSelect=224&redirectto=");
/* 4030 */                               out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 4031 */                               out.write("'class=\"staticlinks\">");
/* 4032 */                               out.print(ALERTCONFIG_TEXT);
/* 4033 */                               out.write("</td>\n\t  ");
/*      */                             }
/* 4035 */                             out.write("\n        </tr>\n      </table></td>\n    <td valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\n\t<tr>\n          <td width=\"50%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 4036 */                             if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4038 */                             out.write("</span></td>\n          <td width=\"30%\"class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 4039 */                             if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4041 */                             out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 4042 */                             if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4044 */                             out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 4045 */                             out.print(FormatUtil.getString("am.webclient.common.minimum.text"));
/* 4046 */                             out.write(32);
/* 4047 */                             out.print(FormatUtil.getString("Response Time"));
/* 4048 */                             out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4049 */                             if (_jspx_meth_am_005fFormat_005f4(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4051 */                             out.write(32);
/* 4052 */                             out.print(FormatUtil.getString("ms"));
/* 4053 */                             out.write("</td>\n\t  <td class=\"whitegrayborder\">&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 4054 */                             out.print(FormatUtil.getString("am.webclient.common.maximum.text"));
/* 4055 */                             out.write(32);
/* 4056 */                             out.print(FormatUtil.getString("Response Time"));
/* 4057 */                             out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4058 */                             if (_jspx_meth_am_005fFormat_005f5(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4060 */                             out.write(32);
/* 4061 */                             out.print(FormatUtil.getString("ms"));
/* 4062 */                             out.write("</td>\n\t  <td class=\"yellowgrayborder\">&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 4063 */                             out.print(FormatUtil.getString("Average Response Time"));
/* 4064 */                             out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4065 */                             if (_jspx_meth_am_005fFormat_005f6(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4067 */                             out.write(32);
/* 4068 */                             out.print(FormatUtil.getString("ms"));
/* 4069 */                             out.write("</td>\n\t  <td class=\"whitegrayborder\">&nbsp;</td>\n        </tr>\n        <tr>\n        <td  class=\"yellowgrayborder\">&nbsp;");
/* 4070 */                             out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 4071 */                             out.write(32);
/* 4072 */                             out.print(FormatUtil.getString("Response Time"));
/* 4073 */                             out.write("</td>\n\t   ");
/*      */                             
/* 4075 */                             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4076 */                             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4077 */                             _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 4079 */                             _jspx_th_c_005fif_005f13.setTest("${responsetime !='-1'}");
/* 4080 */                             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4081 */                             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                               for (;;) {
/* 4083 */                                 out.write("\n           <td class=\"yellowgrayborder\">");
/* 4084 */                                 if (_jspx_meth_am_005fFormat_005f7(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                   return;
/* 4086 */                                 out.write(32);
/* 4087 */                                 out.print(FormatUtil.getString("ms"));
/* 4088 */                                 out.write("</td>\n\t");
/* 4089 */                                 if (resourceType.equals("WEBLOGIC-Integration")) {
/* 4090 */                                   out.write("\n          <td class=\"yellowgrayborder\"> <a href=\"javascript:void(0)\" class=\"yellowgrayborder\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4091 */                                   if (_jspx_meth_c_005fout_005f59(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                     return;
/* 4093 */                                   out.write("&attributeid=6103')\"> ");
/*      */                                   
/* 4095 */                                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4096 */                                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 4097 */                                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f13);
/*      */                                   
/* 4099 */                                   _jspx_th_c_005fif_005f14.setTest("${!empty responsetime}");
/* 4100 */                                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 4101 */                                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                     for (;;) {
/* 4103 */                                       out.print(getSeverityImage(alert.getProperty(resID + "#" + "6103")));
/* 4104 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 4105 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4109 */                                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 4110 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                   }
/*      */                                   
/* 4113 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4114 */                                   out.write("</a></td>\n\t\t");
/*      */                                 } else {
/* 4116 */                                   out.write("\n          <td class=\"yellowgrayborder\"> <a href=\"javascript:void(0)\" class=\"yellowgrayborder\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4117 */                                   if (_jspx_meth_c_005fout_005f60(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                     return;
/* 4119 */                                   out.write("&attributeid=216')\"> ");
/*      */                                   
/* 4121 */                                   IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4122 */                                   _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4123 */                                   _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f13);
/*      */                                   
/* 4125 */                                   _jspx_th_c_005fif_005f15.setTest("${!empty responsetime}");
/* 4126 */                                   int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4127 */                                   if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                     for (;;) {
/* 4129 */                                       out.print(getSeverityImage(alert.getProperty(resID + "#" + "216")));
/* 4130 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4131 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4135 */                                   if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4136 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                   }
/*      */                                   
/* 4139 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4140 */                                   out.write("</a></td>\n\t   ");
/*      */                                 }
/* 4142 */                                 out.write(10);
/* 4143 */                                 out.write(9);
/* 4144 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4145 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4149 */                             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4150 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                             }
/*      */                             
/* 4153 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4154 */                             out.write(10);
/* 4155 */                             out.write(9);
/*      */                             
/* 4157 */                             IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4158 */                             _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 4159 */                             _jspx_th_c_005fif_005f16.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 4161 */                             _jspx_th_c_005fif_005f16.setTest("${responsetime =='-1'}");
/* 4162 */                             int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 4163 */                             if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                               for (;;) {
/* 4165 */                                 out.write("\n                   <td colspan=\"2\" width=\"23%\" class=\"yellowgrayborder\">");
/* 4166 */                                 out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 4167 */                                 out.write("</td>\n           ");
/* 4168 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 4169 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4173 */                             if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 4174 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                             }
/*      */                             
/* 4177 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4178 */                             out.write("\n         </tr>\n        <tr align=\"right\">\n\t<td class=\"whitegrayborder\">&nbsp;</td>\n\t");
/* 4179 */                             if (resourceType.equals("WEBLOGIC-Integration")) {
/* 4180 */                               out.write("\n          <td colspan=\"3\"  class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4181 */                               if (_jspx_meth_c_005fout_005f61(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 4183 */                               out.write("&attributeIDs=6103&attributeToSelect=6103&redirectto=");
/* 4184 */                               out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 4185 */                               out.write("'  class=\"staticlinks\">");
/* 4186 */                               out.print(ALERTCONFIG_TEXT);
/* 4187 */                               out.write("</a></td>\n\t\t");
/*      */                             } else {
/* 4189 */                               out.write("\n          <td colspan=\"3\"  class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4190 */                               if (_jspx_meth_c_005fout_005f62(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 4192 */                               out.write("&attributeIDs=216&attributeToSelect=216&redirectto=");
/* 4193 */                               out.print(URLEncoder.encode("/showresource.do?" + request.getQueryString()));
/* 4194 */                               out.write("'  class=\"staticlinks\">");
/* 4195 */                               out.print(ALERTCONFIG_TEXT);
/* 4196 */                               out.write("</a></td>\n\t   ");
/*      */                             }
/* 4198 */                             out.write("\n        </tr>\n      </table></td>\n\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n<tr>\n<td width=\"100%\" height=\"31\" align=\"right\" class=\"tablebottom\"><a href=\"#top\" class=\"staticlinks\">");
/* 4199 */                             out.print(FormatUtil.getString("am.webclient.common.top.text"));
/* 4200 */                             out.write("</a>&nbsp;&nbsp;</td>\n</tr>\n</table>\n");
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/* 4205 */                           out.write("\n<br>\n\n");
/* 4206 */                           if (resourceType.equals("WEBLOGIC-Integration")) {
/* 4207 */                             out.write("\n<a name=\"wlibpm\" id=\"wlibpm\"></a>\n");
/* 4208 */                             JspRuntimeLibrary.include(request, response, "/showWLIBpm.do?method=getBusinessProcessData&include=true", out, false);
/* 4209 */                             out.write(10);
/* 4210 */                             if (_jspx_meth_logic_005fnotEmpty_005f2(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4212 */                             out.write(10);
/*      */                             
/* 4214 */                             EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4215 */                             _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 4216 */                             _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 4218 */                             _jspx_th_logic_005fempty_005f2.setName("data");
/* 4219 */                             int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 4220 */                             if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */                               for (;;) {
/* 4222 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n      <td width=\"72%\" height=\"26\" class=\"tableheadingtrans\">");
/* 4223 */                                 out.print(FormatUtil.getString("am.webclient.wli.bpm.text"));
/* 4224 */                                 out.write("</td>\n</tr>\n<tr>\n<td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4225 */                                 out.print(FormatUtil.getString("am.wli.nobpm.text"));
/* 4226 */                                 out.write("\n</td>\n</tr>\n</table>\n");
/* 4227 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 4228 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4232 */                             if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 4233 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */                             }
/*      */                             
/* 4236 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 4237 */                             out.write("\n<br>\n\n<a name=\"wliai\" id=\"wliai\"></a>\n");
/* 4238 */                             JspRuntimeLibrary.include(request, response, "/showWLIAI.do?method=getApplnIntegrationData&include=true", out, false);
/* 4239 */                             out.write(10);
/* 4240 */                             if (_jspx_meth_logic_005fnotEmpty_005f3(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4242 */                             out.write(10);
/*      */                             
/* 4244 */                             EmptyTag _jspx_th_logic_005fempty_005f3 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4245 */                             _jspx_th_logic_005fempty_005f3.setPageContext(_jspx_page_context);
/* 4246 */                             _jspx_th_logic_005fempty_005f3.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 4248 */                             _jspx_th_logic_005fempty_005f3.setName("data");
/* 4249 */                             int _jspx_eval_logic_005fempty_005f3 = _jspx_th_logic_005fempty_005f3.doStartTag();
/* 4250 */                             if (_jspx_eval_logic_005fempty_005f3 != 0) {
/*      */                               for (;;) {
/* 4252 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n      <td width=\"72%\" height=\"26\" class=\"tableheadingtrans\">");
/* 4253 */                                 out.print(FormatUtil.getString("am.webclient.wli.aidetails.text"));
/* 4254 */                                 out.write("</td>\n</tr>\n<tr>\n<td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4255 */                                 out.print(FormatUtil.getString("am.wli.noai.text"));
/* 4256 */                                 out.write("\n</td>\n</tr>\n</table>\n");
/* 4257 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f3.doAfterBody();
/* 4258 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4262 */                             if (_jspx_th_logic_005fempty_005f3.doEndTag() == 5) {
/* 4263 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f3); return;
/*      */                             }
/*      */                             
/* 4266 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f3);
/* 4267 */                             out.write("\n<br>\n\n<a name=\"wlimb\" id=\"wlimb\"></a>\n");
/* 4268 */                             JspRuntimeLibrary.include(request, response, "/showWLIMB.do?method=getMessageBrokerData&include=true", out, false);
/* 4269 */                             out.write(10);
/* 4270 */                             if (_jspx_meth_logic_005fnotEmpty_005f4(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4272 */                             out.write(10);
/*      */                             
/* 4274 */                             EmptyTag _jspx_th_logic_005fempty_005f4 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4275 */                             _jspx_th_logic_005fempty_005f4.setPageContext(_jspx_page_context);
/* 4276 */                             _jspx_th_logic_005fempty_005f4.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 4278 */                             _jspx_th_logic_005fempty_005f4.setName("data");
/* 4279 */                             int _jspx_eval_logic_005fempty_005f4 = _jspx_th_logic_005fempty_005f4.doStartTag();
/* 4280 */                             if (_jspx_eval_logic_005fempty_005f4 != 0) {
/*      */                               for (;;) {
/* 4282 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n      <td width=\"72%\" height=\"26\" class=\"tableheadingtrans\">");
/* 4283 */                                 out.print(FormatUtil.getString("am.webclient.wli.mbdetails.text"));
/* 4284 */                                 out.write("</td>\n</tr>\n<tr>\n<td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4285 */                                 out.print(FormatUtil.getString("am.wli.nomb.text"));
/* 4286 */                                 out.write("\n</td>\n</tr>\n</table>\n");
/* 4287 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f4.doAfterBody();
/* 4288 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4292 */                             if (_jspx_th_logic_005fempty_005f4.doEndTag() == 5) {
/* 4293 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4); return;
/*      */                             }
/*      */                             
/* 4296 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4);
/* 4297 */                             out.write(10);
/* 4298 */                             out.write(10);
/*      */                           }
/* 4300 */                           out.write("\n\n<br>\n");
/* 4301 */                           if (!com.adventnet.appmanager.server.framework.datacollection.AMDataCollector.disablewebapplist.contains(resID)) {
/* 4302 */                             out.write("\n<a name=\"webapp\" id=\"webapp\"></a>\n");
/* 4303 */                             JspRuntimeLibrary.include(request, response, "/showWebApp.do?method=getWebAppData&include=true", out, false);
/* 4304 */                             out.write(10);
/* 4305 */                             if (_jspx_meth_logic_005fnotEmpty_005f5(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4307 */                             out.write(10);
/*      */                             
/* 4309 */                             EmptyTag _jspx_th_logic_005fempty_005f5 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4310 */                             _jspx_th_logic_005fempty_005f5.setPageContext(_jspx_page_context);
/* 4311 */                             _jspx_th_logic_005fempty_005f5.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 4313 */                             _jspx_th_logic_005fempty_005f5.setName("data");
/* 4314 */                             int _jspx_eval_logic_005fempty_005f5 = _jspx_th_logic_005fempty_005f5.doStartTag();
/* 4315 */                             if (_jspx_eval_logic_005fempty_005f5 != 0) {
/*      */                               for (;;) {
/* 4317 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"26\" class=\"tableheadingbborder\">");
/* 4318 */                                 out.print(FormatUtil.getString("am.webclient.jboss.webapplications.text"));
/* 4319 */                                 out.write("</td>\n  </tr>\n  <tr>\n  <td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4320 */                                 out.print(FormatUtil.getString("weblogic.nowebapps.text"));
/* 4321 */                                 out.write("\n  </td>\n  </tr>\n</table>\n");
/* 4322 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f5.doAfterBody();
/* 4323 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4327 */                             if (_jspx_th_logic_005fempty_005f5.doEndTag() == 5) {
/* 4328 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5); return;
/*      */                             }
/*      */                             
/* 4331 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5);
/* 4332 */                             out.write(10);
/*      */                           }
/* 4334 */                           out.write("\n<br>\n<a name=\"thread\" id=\"thread\"></a>\n");
/* 4335 */                           JspRuntimeLibrary.include(request, response, "/showThread.do?method=getThreadPoolData&include=true", out, false);
/* 4336 */                           out.write(10);
/* 4337 */                           if (_jspx_meth_logic_005fnotEmpty_005f6(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                             return;
/* 4339 */                           out.write(10);
/*      */                           
/* 4341 */                           EmptyTag _jspx_th_logic_005fempty_005f6 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4342 */                           _jspx_th_logic_005fempty_005f6.setPageContext(_jspx_page_context);
/* 4343 */                           _jspx_th_logic_005fempty_005f6.setParent(_jspx_th_c_005fif_005f10);
/*      */                           
/* 4345 */                           _jspx_th_logic_005fempty_005f6.setName("data");
/* 4346 */                           int _jspx_eval_logic_005fempty_005f6 = _jspx_th_logic_005fempty_005f6.doStartTag();
/* 4347 */                           if (_jspx_eval_logic_005fempty_005f6 != 0) {
/*      */                             for (;;) {
/* 4349 */                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"26\" class=\"tableheadingbborder\">");
/* 4350 */                               out.print(FormatUtil.getString("am.webclient.weblogic.threadpool.text"));
/* 4351 */                               out.write("</td>\n  </tr>\n  <tr>\n  <td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4352 */                               out.print(FormatUtil.getString("weblogic.nothreads.text"));
/* 4353 */                               out.write("\n  </td>\n  </tr>\n</table>\n");
/* 4354 */                               int evalDoAfterBody = _jspx_th_logic_005fempty_005f6.doAfterBody();
/* 4355 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4359 */                           if (_jspx_th_logic_005fempty_005f6.doEndTag() == 5) {
/* 4360 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f6); return;
/*      */                           }
/*      */                           
/* 4363 */                           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f6);
/* 4364 */                           out.write("\n<br>\n<a name=\"jdbc\"  id=\"jdbc\"></a>\n");
/* 4365 */                           JspRuntimeLibrary.include(request, response, "/showJDBC.do?method=getJDBCPoolData&include=true", out, false);
/* 4366 */                           out.write(10);
/* 4367 */                           if (_jspx_meth_logic_005fnotEmpty_005f7(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                             return;
/* 4369 */                           out.write(10);
/*      */                           
/* 4371 */                           EmptyTag _jspx_th_logic_005fempty_005f7 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4372 */                           _jspx_th_logic_005fempty_005f7.setPageContext(_jspx_page_context);
/* 4373 */                           _jspx_th_logic_005fempty_005f7.setParent(_jspx_th_c_005fif_005f10);
/*      */                           
/* 4375 */                           _jspx_th_logic_005fempty_005f7.setName("data");
/* 4376 */                           int _jspx_eval_logic_005fempty_005f7 = _jspx_th_logic_005fempty_005f7.doStartTag();
/* 4377 */                           if (_jspx_eval_logic_005fempty_005f7 != 0) {
/*      */                             for (;;) {
/* 4379 */                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"26\" class=\"tableheadingbborder\">");
/* 4380 */                               out.print(FormatUtil.getString("am.webclient.weblogic.connectionpool.text"));
/* 4381 */                               out.write("</td>\n  </tr>\n  <tr>\n  <td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4382 */                               out.print(FormatUtil.getString("weblogic.nojdbcs.text"));
/* 4383 */                               out.write("\n  </td>\n  </tr>\n</table>\n");
/* 4384 */                               int evalDoAfterBody = _jspx_th_logic_005fempty_005f7.doAfterBody();
/* 4385 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4389 */                           if (_jspx_th_logic_005fempty_005f7.doEndTag() == 5) {
/* 4390 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f7); return;
/*      */                           }
/*      */                           
/* 4393 */                           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f7);
/* 4394 */                           out.write("\n\n<br>\n\n<a name=\"jms\"  id=\"jms\"></a>\n");
/* 4395 */                           JspRuntimeLibrary.include(request, response, "/showJMS.do?method=getJMSData&include=true", out, false);
/* 4396 */                           out.write(10);
/* 4397 */                           if (_jspx_meth_logic_005fnotEmpty_005f8(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                             return;
/* 4399 */                           out.write(10);
/*      */                           
/* 4401 */                           EmptyTag _jspx_th_logic_005fempty_005f8 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4402 */                           _jspx_th_logic_005fempty_005f8.setPageContext(_jspx_page_context);
/* 4403 */                           _jspx_th_logic_005fempty_005f8.setParent(_jspx_th_c_005fif_005f10);
/*      */                           
/* 4405 */                           _jspx_th_logic_005fempty_005f8.setName("jmsdata");
/* 4406 */                           int _jspx_eval_logic_005fempty_005f8 = _jspx_th_logic_005fempty_005f8.doStartTag();
/* 4407 */                           if (_jspx_eval_logic_005fempty_005f8 != 0) {
/*      */                             for (;;) {
/* 4409 */                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"26\" class=\"tableheadingbborder\">");
/* 4410 */                               out.print(FormatUtil.getString("am.webclient.weblogic.jmsdetails.text"));
/* 4411 */                               out.write("</td>\n  </tr>\n  <tr>\n  ");
/* 4412 */                               String jmsError = (String)request.getAttribute("jmserror");
/* 4413 */                               if ((jmsError != null) && (jmsError.equals("jms.notdeployed.error")))
/*      */                               {
/*      */ 
/* 4416 */                                 out.write("\t<td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4417 */                                 out.print(FormatUtil.getString("weblogic.nojms.troubleshooting.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link") }));
/* 4418 */                                 out.write("\n\t   </td>\n\t");
/*      */                               } else {
/* 4420 */                                 out.write("\n\t\t<td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4421 */                                 out.print(FormatUtil.getString("weblogic.nojms.text"));
/* 4422 */                                 out.write("\n\t\t\t  </td>\n\t\t");
/*      */                               }
/* 4424 */                               out.write("\n  </tr>\n</table>\n");
/* 4425 */                               int evalDoAfterBody = _jspx_th_logic_005fempty_005f8.doAfterBody();
/* 4426 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4430 */                           if (_jspx_th_logic_005fempty_005f8.doEndTag() == 5) {
/* 4431 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f8); return;
/*      */                           }
/*      */                           
/* 4434 */                           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f8);
/* 4435 */                           out.write("\n\n<br>\n\n<a name=\"saf\"  id=\"saf\"></a>\n");
/* 4436 */                           JspRuntimeLibrary.include(request, response, "/showSAF.do?method=getSAFData&include=true", out, false);
/* 4437 */                           out.write(10);
/* 4438 */                           if (_jspx_meth_logic_005fnotEmpty_005f9(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                             return;
/* 4440 */                           out.write(10);
/*      */                           
/* 4442 */                           EmptyTag _jspx_th_logic_005fempty_005f9 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4443 */                           _jspx_th_logic_005fempty_005f9.setPageContext(_jspx_page_context);
/* 4444 */                           _jspx_th_logic_005fempty_005f9.setParent(_jspx_th_c_005fif_005f10);
/*      */                           
/* 4446 */                           _jspx_th_logic_005fempty_005f9.setName("safdata");
/* 4447 */                           int _jspx_eval_logic_005fempty_005f9 = _jspx_th_logic_005fempty_005f9.doStartTag();
/* 4448 */                           if (_jspx_eval_logic_005fempty_005f9 != 0) {
/*      */                             for (;;) {
/* 4450 */                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"26\" class=\"tableheadingbborder\">");
/* 4451 */                               out.print(FormatUtil.getString("am.webclient.weblogic.safdetails.text"));
/* 4452 */                               out.write("</td>\n  </tr>\n  <tr>\n  ");
/* 4453 */                               String jmsError = (String)request.getAttribute("jmserror");
/* 4454 */                               if ((jmsError != null) && (jmsError.equals("jms.notdeployed.error")))
/*      */                               {
/*      */ 
/* 4457 */                                 out.write("\t<td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4458 */                                 out.print(FormatUtil.getString("weblogic.nosafs.troubleshooting.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link") }));
/* 4459 */                                 out.write("\n\t   </td>\n\t");
/*      */                               } else {
/* 4461 */                                 out.write("\n\t\t<td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4462 */                                 out.print(FormatUtil.getString("weblogic.nosafs.text"));
/* 4463 */                                 out.write("\n\t\t\t  </td>\n\t\t");
/*      */                               }
/* 4465 */                               out.write("\n  </tr>\n</table>\n");
/* 4466 */                               int evalDoAfterBody = _jspx_th_logic_005fempty_005f9.doAfterBody();
/* 4467 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4471 */                           if (_jspx_th_logic_005fempty_005f9.doEndTag() == 5) {
/* 4472 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f9); return;
/*      */                           }
/*      */                           
/* 4475 */                           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f9);
/* 4476 */                           out.write("\n\n<br>\n<a name=\"ejb\" id=\"ejb\"></a>\n");
/* 4477 */                           if (!com.adventnet.appmanager.server.framework.datacollection.AMDataCollector.disableEjblist.contains(resID)) {
/* 4478 */                             out.write(10);
/* 4479 */                             JspRuntimeLibrary.include(request, response, "/showEJB.do?method=getEJBData&include=true", out, false);
/* 4480 */                             out.write(10);
/* 4481 */                             if (_jspx_meth_logic_005fnotEmpty_005f10(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 4483 */                             out.write(10);
/*      */                             
/* 4485 */                             EmptyTag _jspx_th_logic_005fempty_005f10 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4486 */                             _jspx_th_logic_005fempty_005f10.setPageContext(_jspx_page_context);
/* 4487 */                             _jspx_th_logic_005fempty_005f10.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 4489 */                             _jspx_th_logic_005fempty_005f10.setName("data");
/* 4490 */                             int _jspx_eval_logic_005fempty_005f10 = _jspx_th_logic_005fempty_005f10.doStartTag();
/* 4491 */                             if (_jspx_eval_logic_005fempty_005f10 != 0) {
/*      */                               for (;;) {
/* 4493 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"26\" class=\"tableheadingbborder\">");
/* 4494 */                                 out.print(FormatUtil.getString("am.webclient.weblogic.ejbdetails.text"));
/* 4495 */                                 out.write("</td>\n  </tr>\n  <tr>\n  <td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4496 */                                 out.print(FormatUtil.getString("weblogic.noejbs.text"));
/* 4497 */                                 out.write("\n  </td>\n  </tr>\n</table>\n");
/* 4498 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f10.doAfterBody();
/* 4499 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4503 */                             if (_jspx_th_logic_005fempty_005f10.doEndTag() == 5) {
/* 4504 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f10); return;
/*      */                             }
/*      */                             
/* 4507 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f10);
/* 4508 */                             out.write(10);
/*      */                           }
/* 4510 */                           out.write("\n\n<br>\n");
/*      */                           
/* 4512 */                           String mopRedirectString = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resID);
/* 4513 */                           String linkForMopAction = "/MBeanOperationAction.do?method=showInitialScreen&resourceid=" + resID + "&redirectto=" + mopRedirectString;
/* 4514 */                           ArrayList mopActions = com.adventnet.appmanager.fault.FaultUtil.getMBeanOperationActionsForResourceID(resID, request.getRemoteUser(), request.isUserInRole("OPERATOR"));
/* 4515 */                           if (mopActions.size() > 0)
/*      */                           {
/* 4517 */                             request.setAttribute("executeMopActions", mopActions);
/*      */                           }
/* 4519 */                           request.setAttribute("showrefreshnowoption", "true");
/*      */                           
/* 4521 */                           out.write(10);
/* 4522 */                           if (!resourceType.equals("WEBLOGIC-Integration")) {
/* 4523 */                             out.write(10);
/* 4524 */                             out.write("<!--$Id$-->\n");
/* 4525 */                             com.adventnet.appmanager.cam.beans.CAMGraphs camGraph = null;
/* 4526 */                             camGraph = (com.adventnet.appmanager.cam.beans.CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 4527 */                             if (camGraph == null) {
/* 4528 */                               camGraph = new com.adventnet.appmanager.cam.beans.CAMGraphs();
/* 4529 */                               _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                             }
/* 4531 */                             out.write("\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n\n");
/*      */                             
/* 4533 */                             long camStartTime = System.currentTimeMillis();
/*      */                             
/* 4535 */                             String camIDI = (String)request.getAttribute("camid");
/* 4536 */                             String screenIDI = (String)request.getAttribute("screenid");
/* 4537 */                             List screenInfoI = (List)request.getAttribute("screeninfo");
/* 4538 */                             boolean perfAvailResourceScreen = false;
/* 4539 */                             String resourceID = "";
/* 4540 */                             String fromConfPage1 = request.getAttribute("fromConfPage") + "";
/* 4541 */                             String haidI = request.getParameter("haid");
/* 4542 */                             if ((haidI == null) || (haidI.trim().length() == 0)) {
/* 4543 */                               haidI = request.getParameter("haid");
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/* 4548 */                             String isFromResourcePage = (String)request.getAttribute("isfromresourcepage");
/* 4549 */                             if (isFromResourcePage == null) {
/* 4550 */                               isFromResourcePage = "NA";
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/* 4555 */                             if ("true".equals(isFromResourcePage))
/*      */                             {
/*      */ 
/*      */ 
/* 4559 */                               resourceID = (String)request.getAttribute("resourceid");
/* 4560 */                               if ((resourceID == null) || (resourceID.trim().length() == 0)) {
/* 4561 */                                 resourceID = request.getParameter("resourceid");
/*      */                               }
/*      */                               
/* 4564 */                               camIDI = resourceID;
/* 4565 */                               perfAvailResourceScreen = true;
/*      */                               
/*      */ 
/* 4568 */                               request.setAttribute("screenInfo", screenInfoI);
/* 4569 */                               List tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 4570 */                               if (tmpList.size() == 0)
/*      */                               {
/* 4572 */                                 CAMDBUtil.createDefaultScreenForResource(Integer.parseInt(camIDI));
/* 4573 */                                 tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 4574 */                                 screenInfoI = (List)tmpList.get(0);
/*      */                               }
/*      */                               else {
/* 4577 */                                 screenInfoI = (List)tmpList.get(0);
/*      */                               }
/*      */                               
/* 4580 */                               screenIDI = (String)screenInfoI.get(0);
/* 4581 */                               request.setAttribute("screenid", screenIDI);
/*      */                             }
/*      */                             
/*      */ 
/* 4585 */                             Map fromDB = CAMDBUtil.getCustomizedDataForScreenAdminActivity(Long.parseLong(screenIDI));
/* 4586 */                             List screenConfigList = (List)fromDB.get("completedata");
/*      */                             
/*      */ 
/* 4589 */                             List reportsData = (List)fromDB.get("reportsdata");
/*      */                             
/*      */ 
/* 4592 */                             Map dcTimeMap = CAMDBUtil.getLatestCollectionTimes(Long.parseLong(screenIDI));
/* 4593 */                             Map attribResourceMap = CAMDBUtil.getResourceNamesForAttributesInScreen(Integer.parseInt(screenIDI));
/*      */                             
/* 4595 */                             List screenAttribInfo = CAMDBUtil.getScreenAttributeInfo(Long.parseLong(screenIDI), Integer.parseInt((String)screenInfoI.get(3)), dcTimeMap);
/* 4596 */                             boolean scalarAttribsPresent = screenAttribInfo.size() > 0;
/* 4597 */                             List colScreenAttribInfo = CAMDBUtil.getScreenInfoForColumnarData(Long.parseLong(screenIDI));
/* 4598 */                             boolean columnarAttribsPresent = colScreenAttribInfo.size() > 0;
/* 4599 */                             boolean attribsPresent = (scalarAttribsPresent == true) || (columnarAttribsPresent == true);
/* 4600 */                             String quickNote = "This page displays the attributes monitored from various resources as configured during design time. Placing the mouse over the value for table data will display the time when the data was collected. Time intervals will be different if the attributes are from different resources.";
/*      */                             
/*      */ 
/*      */ 
/* 4604 */                             if (request.getAttribute("quicknote") == null) {
/* 4605 */                               request.setAttribute("quicknote", quickNote);
/*      */                             }
/* 4607 */                             String configureLink = "/ShowCAM.do?method=configureScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "&isfromresourcepage=" + isFromResourcePage;
/* 4608 */                             if ((request.isUserInRole("ENTERPRISEADMIN")) && (com.adventnet.appmanager.util.Constants.isSsoEnabled()))
/*      */                             {
/* 4610 */                               StringBuilder builder = new StringBuilder();
/* 4611 */                               builder.append("https:").append("//");
/* 4612 */                               builder.append(com.adventnet.appmanager.util.Constants.getAppHostName()).append(":");
/* 4613 */                               builder.append(com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getsslport()).append(configureLink);
/* 4614 */                               configureLink = builder.toString();
/*      */                             }
/* 4616 */                             request.setAttribute("configurelink", configureLink);
/* 4617 */                             String secondLevelLinkTitle; if (!perfAvailResourceScreen)
/*      */                             {
/*      */ 
/* 4620 */                               List sLinks = new ArrayList();
/* 4621 */                               List sText = new ArrayList();
/*      */                               
/* 4623 */                               sLinks.add("Add ScreenXXXX");
/* 4624 */                               sText.add("/ShowCAM.do?method=addScreen&camid=" + camIDI + "&haid=" + haidI);
/*      */                               
/*      */ 
/*      */ 
/* 4628 */                               sLinks.add("Customize");
/* 4629 */                               sText.add(configureLink);
/*      */                               
/*      */ 
/* 4632 */                               sLinks.add("<a href=\"/CAMDeleteScreen.do?method=deleteScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "\" onclick=\"return deleteScreen();\" class='staticlinks'>Delete Screen</a>");
/* 4633 */                               sText.add("");
/*      */                               
/*      */ 
/*      */ 
/* 4637 */                               List[] secondLevelOfLinks = { sText, sLinks };
/* 4638 */                               secondLevelLinkTitle = "Admin Activity";
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4647 */                             String configureThresholdRedirectLink = "/ShowCAM.do?method=showScreen&haid=" + haidI + "&camid=" + camIDI + "&screenid=" + screenIDI;
/*      */                             
/* 4649 */                             if ("true".equals(isFromResourcePage)) {
/* 4650 */                               configureThresholdRedirectLink = "/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID";
/*      */                             }
/*      */                             
/* 4653 */                             configureThresholdRedirectLink = URLEncoder.encode(configureThresholdRedirectLink);
/*      */                             
/*      */ 
/*      */ 
/* 4657 */                             out.write("\n\n\n\n<script language=\"JavaScript1.2\">\n\nfunction showAttribGraph(attribID,mbean) {\n       var featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes\";\n       var url = \"/ShowCAM.do?method=showSingleGraphScreen&attributeid=\" + attribID+\"&mbean=\" +mbean;\n       popUp = window.open(url,'popUp',featurelist);\n       return false;\n}\n\n</SCRIPT>\n<!--This script is used to show horizontal bar if customer attributes tables have more number of attributes in SNMP Devices.--> \n<script>\n    jQuery(document).ready(function(){\n        var windowWidth = jQuery(window).width();\n        windowWidth = windowWidth*(81/100);\n        jQuery('.horTableScroll').css({'width':windowWidth});//No I18N\n\n    });\n</script>\n\n<style>\n    .horTableScroll {\n        overflow-x:auto;\n    }    \n</style>\n<!--");
/*      */                             
/* 4659 */                             String camid = request.getParameter("camid");
/*      */                             
/*      */ 
/* 4662 */                             out.write("-->\n\n\n<script>\nfunction validateAndSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 4663 */                             out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 4664 */                             out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n\n");
/*      */                             
/* 4666 */                             List list = CAMDBUtil.getCAMDetails(camIDI);
/* 4667 */                             String camName = (String)list.get(0);
/* 4668 */                             String camDescription = (String)list.get(2);
/*      */                             
/* 4670 */                             out.write("\n\n\n\n");
/*      */                             
/* 4672 */                             if ("true".equals(request.getParameter("editPage")))
/*      */                             {
/* 4674 */                               out.write("\n<div id=\"edit\" style=\"display:block\">\n");
/*      */                             } else {
/* 4676 */                               out.write("\n<div id=\"edit\" style=\"display:none\">\n");
/*      */                             }
/* 4678 */                             out.write(10);
/* 4679 */                             out.write(10);
/*      */                             
/* 4681 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 4682 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 4683 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 4685 */                             _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                             
/* 4687 */                             _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                             
/* 4689 */                             _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 4690 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 4691 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 4693 */                                 out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 4694 */                                 out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 4695 */                                 out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 4696 */                                 out.print(request.getParameter("name"));
/* 4697 */                                 out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 4698 */                                 out.print(request.getParameter("haid"));
/* 4699 */                                 out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 4700 */                                 out.print(request.getParameter("type"));
/* 4701 */                                 out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 4702 */                                 out.print(request.getParameter("type"));
/* 4703 */                                 out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 4704 */                                 out.print(request.getParameter("resourceid"));
/* 4705 */                                 out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 4706 */                                 out.print(request.getParameter("resourcename"));
/* 4707 */                                 out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 4708 */                                 out.print(request.getParameter("moname"));
/* 4709 */                                 out.write("\">\n\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 4710 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 4711 */                                 out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 4712 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4713 */                                 out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 4714 */                                 out.print(camName);
/* 4715 */                                 out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 4716 */                                 out.print(FormatUtil.getString("Description"));
/* 4717 */                                 out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 4718 */                                 out.print(camDescription);
/* 4719 */                                 out.write("</textarea>\n    </td>\n  </tr>\n</table>\n");
/*      */                                 
/* 4721 */                                 String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                 
/* 4723 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" \" value=\"");
/* 4724 */                                 out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 4725 */                                 out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 4726 */                                 out.print(cancel);
/* 4727 */                                 out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table>\n");
/* 4728 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 4729 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4733 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 4734 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 4737 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 4738 */                             out.write("\n</div>\n");
/*      */                             int i;
/* 4740 */                             if (!attribsPresent)
/*      */                             {
/*      */ 
/* 4743 */                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 4744 */                               out.print(camName);
/* 4745 */                               out.write("\n      ");
/* 4746 */                               if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4747 */                                 out.write(": <span class=\"topdesc\">");
/* 4748 */                                 out.print(FormatUtil.getString(camDescription));
/* 4749 */                                 out.write(" </span>");
/*      */                               }
/* 4751 */                               out.write("</td>\n     <td  height=\"19\" width=\"20%\" class=\"tableheadingbborder\">\n     ");
/*      */                               
/* 4753 */                               if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null))
/*      */                               {
/*      */ 
/* 4756 */                                 out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 4757 */                                 out.print(camIDI);
/* 4758 */                                 out.write("&redirectto=");
/* 4759 */                                 out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 4760 */                                 out.write(34);
/* 4761 */                                 out.write(62);
/* 4762 */                                 out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 4763 */                                 out.write("</a>\n     ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 4769 */                                 out.write("\n     &nbsp;\n     ");
/*      */                               }
/*      */                               
/*      */ 
/* 4773 */                               out.write("\n\n     </td>\n</tr>\n\n<tr>\n    <td colspan=4  height=\"29\" ><span class=\"bodytext\">&nbsp;");
/* 4774 */                               out.print(FormatUtil.getString("am.webclient.cam.addcustomattributes.message"));
/*      */                               
/* 4776 */                               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4777 */                               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4778 */                               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f10);
/*      */                               
/* 4780 */                               _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 4781 */                               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4782 */                               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                 for (;;) {
/* 4784 */                                   out.write(" <a class='staticlinks' href=\"");
/* 4785 */                                   out.print(configureLink);
/* 4786 */                                   out.write("\">\n      ");
/* 4787 */                                   out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 4788 */                                   out.write("</a>.");
/* 4789 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4790 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4794 */                               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4795 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                               }
/*      */                               
/* 4798 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4799 */                               out.write("</span></td>\n</tr>\n</table>\n");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 4804 */                               if (!scalarAttribsPresent) {
/* 4805 */                                 out.write(10);
/* 4806 */                                 out.write(10);
/*      */ 
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/*      */ 
/* 4814 */                                 List row = null;
/* 4815 */                                 int posOfAttribName = 2;
/* 4816 */                                 int posOfDispType = 4;
/* 4817 */                                 int posOfAttribValue = 7;
/* 4818 */                                 int posOfResourceID = 6;
/* 4819 */                                 int posOfAttribID = 0;
/* 4820 */                                 int posOfAttribType = 3;
/* 4821 */                                 String className = "whitegrayborder";
/* 4822 */                                 String currentResourceName = null;
/* 4823 */                                 String currentMBeanName = null;
/* 4824 */                                 Map infoMap = CAMDBUtil.getMBeanBasedScreenData(Long.parseLong(screenIDI));
/* 4825 */                                 Map idVsMBeanAndRes = (HashMap)infoMap.get("attrIdVsMBeanName");
/* 4826 */                                 List ordListFromDB = (ArrayList)infoMap.get("attributeidsorderedlist");
/* 4827 */                                 List orderedList = new ArrayList(screenAttribInfo.size());
/*      */                                 
/*      */ 
/* 4830 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 4831 */                                 out.print(camName);
/* 4832 */                                 out.write("\n    ");
/* 4833 */                                 if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4834 */                                   out.write("  : <span class=\"topdesc\">");
/* 4835 */                                   out.print(FormatUtil.getString(camDescription));
/* 4836 */                                   out.write(" </span> ");
/*      */                                 }
/* 4838 */                                 out.write("</td>\n\t<td width=\"30%\" nowrap class=\"tableheadingbborder\">\n\t");
/* 4839 */                                 if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null)) {
/* 4840 */                                   out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 4841 */                                   out.print(camIDI);
/* 4842 */                                   out.write("&redirectto=");
/* 4843 */                                   out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 4844 */                                   out.write(34);
/* 4845 */                                   out.write(62);
/* 4846 */                                   out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 4847 */                                   out.write("</a>\n       ");
/*      */                                 } else {
/* 4849 */                                   out.write("\n       <a class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"getCustomFields('");
/* 4850 */                                   out.print(camIDI);
/* 4851 */                                   out.write("','noalarms',false,'CustomFieldValues',false);\">");
/* 4852 */                                   out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 4853 */                                   out.write("</a>&nbsp;");
/* 4854 */                                   out.write("\n       ");
/*      */                                 }
/* 4856 */                                 out.write("\n       </td>\n\n<tr>\n                <td width=\"36%\" class=\"columnheading\" > ");
/* 4857 */                                 out.print(FormatUtil.getString("am.webclient.camscreen.attributename"));
/* 4858 */                                 out.write("</td>\n            <td width=\"30%\" class=\"columnheading\" > ");
/* 4859 */                                 out.print(FormatUtil.getString("am.webclient.camscreen.value"));
/* 4860 */                                 out.write("</td>\n        <td width=\"20%\" class=\"columnheading\" > ");
/* 4861 */                                 if ((request.getParameter("type") != null) && (request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4862 */                                   out.write(" &nbsp; ");
/*      */                                 } else {
/* 4864 */                                   out.write(32);
/* 4865 */                                   out.print(FormatUtil.getString("am.webclient.camscreen.datacollectiontime"));
/* 4866 */                                   out.write("</td> ");
/*      */                                 }
/* 4868 */                                 out.write("\n    <td width=\"9%\" class=\"columnheading\" >");
/* 4869 */                                 out.print(FormatUtil.getString("am.webclient.camscreen.actions.text"));
/* 4870 */                                 out.write("</td>\n</tr>\n");
/*      */                                 
/* 4872 */                                 Hashtable token = new Hashtable();
/*      */                                 int n;
/* 4874 */                                 for (int i = 0; i < screenAttribInfo.size(); n++)
/*      */                                 {
/* 4876 */                                   row = (List)screenAttribInfo.get(i);
/* 4877 */                                   if (i % 2 == 0) {
/* 4878 */                                     className = "whitegrayborder";
/*      */                                   } else {
/* 4880 */                                     className = "yellowgrayborder";
/*      */                                   }
/*      */                                   
/* 4883 */                                   boolean newResource = false;
/* 4884 */                                   boolean newMBean = false;
/* 4885 */                                   boolean addMBeanRow = false;
/* 4886 */                                   String date = "Could not be obtained";
/* 4887 */                                   String shortDate = "Could not be obtained";
/* 4888 */                                   String longFormatDate = "Could not be obtained";
/* 4889 */                                   String resourceName4Attrib = "";
/*      */                                   try
/*      */                                   {
/* 4892 */                                     resourceName4Attrib = (String)attribResourceMap.get(row.get(posOfAttribID));
/* 4893 */                                     String attribID = (String)row.get(posOfAttribID);
/* 4894 */                                     String mBeanName = (String)idVsMBeanAndRes.get(attribID);
/* 4895 */                                     if (currentMBeanName == null)
/*      */                                     {
/* 4897 */                                       currentMBeanName = mBeanName;
/* 4898 */                                       newMBean = true;
/*      */                                     }
/* 4900 */                                     else if (!currentMBeanName.equals(mBeanName))
/*      */                                     {
/* 4902 */                                       currentMBeanName = mBeanName;
/* 4903 */                                       newMBean = true;
/*      */                                     }
/* 4905 */                                     if (currentResourceName == null)
/*      */                                     {
/* 4907 */                                       currentResourceName = resourceName4Attrib;
/* 4908 */                                       newResource = true;
/*      */                                     }
/* 4910 */                                     else if (!currentResourceName.equals(resourceName4Attrib))
/*      */                                     {
/* 4912 */                                       currentResourceName = resourceName4Attrib;
/* 4913 */                                       newResource = true;
/*      */                                     }
/* 4915 */                                     addMBeanRow = (newMBean) || (newResource);
/* 4916 */                                     date = String.valueOf(Long.parseLong((String)dcTimeMap.get(row.get(posOfAttribID))));
/* 4917 */                                     shortDate = formatDT(date);
/* 4918 */                                     longFormatDate = new java.util.Date(Long.parseLong(date)).toString();
/*      */                                   }
/*      */                                   catch (Exception e) {}
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4927 */                                   String value = (String)row.get(posOfAttribValue);
/* 4928 */                                   if (row.get(posOfAttribType).equals("0"))
/*      */                                   {
/* 4930 */                                     if (value.equals("-1"))
/*      */                                     {
/* 4932 */                                       value = FormatUtil.getString("am.webclient.cam.nodata");
/*      */                                     }
/*      */                                   }
/*      */                                   
/*      */ 
/* 4937 */                                   out.write(10);
/*      */                                   
/* 4939 */                                   if (addMBeanRow)
/*      */                                   {
/* 4941 */                                     if (((String)attribResourceMap.get(row.get(posOfAttribID) + "_RESTYPE")).equals("SNMP"))
/*      */                                     {
/*      */ 
/* 4944 */                                       out.write("\n<tr>\n       <td height=\"20\" class=\"secondchildnode\" colspan=\"4\"><span class=\"bodytextbold\"><span class=\"bodytext\">(");
/* 4945 */                                       out.print(currentResourceName);
/* 4946 */                                       out.write(")</span></span></td>\n</tr>\n\n");
/*      */ 
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 4953 */                                       out.write("\n\n<tr>\n<td height=\"20\"   class=\"secondchildnode\"  colspan=\"4\" onmouseover=\"ddrivetip(this,event,'");
/* 4954 */                                       out.print(addBreakAt((currentMBeanName + " - " + currentResourceName).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"), 80));
/* 4955 */                                       out.write("',null,true,'#000000',300)\" onmouseout=\"hideddrivetip()\" <span class=\"bodytextbold\">");
/* 4956 */                                       out.print(addBreakAt(currentMBeanName, 80));
/* 4957 */                                       out.write(" <span class=\"availablity-arrow\">&raquo;&nbsp;</span> ");
/* 4958 */                                       out.print(getTrimmedText(currentResourceName, 25));
/* 4959 */                                       out.write("</span> </td> ");
/* 4960 */                                       out.write("\n</tr>\n");
/*      */                                     }
/*      */                                   }
/*      */                                   try
/*      */                                   {
/* 4965 */                                     StringTokenizer mbean = new StringTokenizer(currentResourceName, "_");
/* 4966 */                                     int j = 0;
/* 4967 */                                     int i1; while (mbean.hasMoreTokens()) {
/* 4968 */                                       String t = mbean.nextToken();
/* 4969 */                                       token.put(Integer.valueOf(j), t);
/* 4970 */                                       i1++;
/*      */                                     }
/*      */                                     
/*      */ 
/* 4974 */                                     String attrbId = (String)row.get(posOfAttribID);
/* 4975 */                                     String resType = (String)attribResourceMap.get(attrbId + "_RESTYPE");
/* 4976 */                                     if ((resType != null) && (resType.equalsIgnoreCase("snmp"))) {
/* 4977 */                                       String resourceId = (String)row.get(row.size() - 2);
/* 4978 */                                       if ((resourceId != null) && (resourceId.length() > 0)) {
/* 4979 */                                         List l = com.adventnet.appmanager.util.DBUtil.getRows("SELECT RESOURCENAME FROM AM_ManagedObject where RESOURCEID=" + resourceId);
/* 4980 */                                         if ((l != null) && (l.size() == 1)) {
/* 4981 */                                           j = 0;
/* 4982 */                                           String actualResourceName = (String)((ArrayList)l.get(0)).get(0);
/* 4983 */                                           mbean = new StringTokenizer(actualResourceName, "_");
/* 4984 */                                           while (mbean.hasMoreTokens()) {
/* 4985 */                                             String t = mbean.nextToken();
/* 4986 */                                             token.put(Integer.valueOf(j), t);
/* 4987 */                                             i1++;
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                     }
/*      */                                   } catch (Exception e) {}
/* 4993 */                                   String toshow = getTrimmedText(value, 30);
/* 4994 */                                   request.setAttribute("toshow", toshow);
/* 4995 */                                   request.setAttribute("fullvalue", value);
/* 4996 */                                   int len = value.length();
/* 4997 */                                   String tooltiptype = (String)row.get(posOfDispType);
/*      */                                   
/* 4999 */                                   if (tooltiptype.equals("1")) {
/* 5000 */                                     tooltiptype = "Counter";
/* 5001 */                                     if ((toshow.equals(" ")) || (value.equals(" ")))
/*      */                                     {
/* 5003 */                                       Map fromMap = new HashMap();
/* 5004 */                                       fromMap = (HashMap)com.adventnet.appmanager.cam.CAMServerUtil.collectFirstData;
/* 5005 */                                       if (fromMap != null) {
/* 5006 */                                         List lst = new ArrayList();
/* 5007 */                                         lst = (ArrayList)fromMap.get((String)row.get(posOfAttribID));
/* 5008 */                                         if (lst != null) {
/* 5009 */                                           request.setAttribute("toshow", lst.get(2));
/* 5010 */                                           request.setAttribute("fullvalue", lst.get(3));
/*      */                                         }
/*      */                                       }
/*      */                                     }
/*      */                                   } else {
/* 5015 */                                     tooltiptype = "Non-Counter";
/*      */                                   }
/*      */                                   
/* 5018 */                                   out.write("\n\n<tr>\n\t<td class=\"");
/* 5019 */                                   out.print(className);
/* 5020 */                                   out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 5021 */                                   out.print(FormatUtil.getString("am.webclient.snmp.tootipMsg", new String[] { (String)row.get(posOfAttribName), resourceName4Attrib, tooltiptype }));
/* 5022 */                                   out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\"> <span class=\"bodytext\">");
/* 5023 */                                   out.print(getTrimmedText((String)row.get(posOfAttribName), 25));
/* 5024 */                                   out.write(" </span></td>\n\n");
/*      */                                   
/* 5026 */                                   if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 5027 */                                     if (len >= 30)
/*      */                                     {
/* 5029 */                                       out.write(10);
/* 5030 */                                       out.write(10);
/* 5031 */                                       String breaktext = addBreakAt(value, 50);
/* 5032 */                                       out.write("\n     <td class=\"");
/* 5033 */                                       out.print(className);
/* 5034 */                                       out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 5035 */                                       out.print(value.replaceAll("\\n", " "));
/* 5036 */                                       out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\" ><span class=\"bodytext\"> ");
/* 5037 */                                       if (_jspx_meth_c_005fout_005f63(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                         return;
/* 5039 */                                       out.write(" </span></td>\n\n");
/*      */                                     }
/*      */                                     else {
/* 5042 */                                       out.write("\n\n<td class=\"");
/* 5043 */                                       out.print(className);
/* 5044 */                                       out.write("\" ><span class=\"bodytext\"> ");
/* 5045 */                                       if (_jspx_meth_c_005fout_005f64(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                         return;
/* 5047 */                                       out.write(" </span></td>\n\n");
/*      */                                     }
/*      */                                     
/* 5050 */                                     out.write("\n\n        <td class=\"");
/* 5051 */                                     out.print(className);
/* 5052 */                                     out.write("\" title=\"Time : ");
/* 5053 */                                     out.print(longFormatDate);
/* 5054 */                                     out.write(" Resource : ");
/* 5055 */                                     out.print(resourceName4Attrib);
/* 5056 */                                     out.write("\"> <span class=\"bodytext\">");
/* 5057 */                                     out.print(shortDate);
/* 5058 */                                     out.write("</span></td>\n");
/*      */                                   } else {
/* 5060 */                                     out.write("\n<td colspan=2 class=\"");
/* 5061 */                                     out.print(className);
/* 5062 */                                     out.write("\"><span class=\"bodytext\">");
/* 5063 */                                     out.print(addBreakAt(value, 55));
/* 5064 */                                     out.write("</span></td>\n");
/*      */                                   }
/* 5066 */                                   out.write("\n        <td class=\"");
/* 5067 */                                   out.print(className);
/* 5068 */                                   out.write("\" >\n        ");
/* 5069 */                                   if ((row.get(posOfAttribType).equals("0")) || (row.get(posOfAttribType).equals("1"))) {
/* 5070 */                                     if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS")))
/*      */                                     {
/* 5072 */                                       out.write("\n\n<a  style=\"cursor: pointer;\" onClick=\"javascript:MM_openBrWindow('/jsp/attribute_edit.jsp?resourceid=");
/* 5073 */                                       out.print(row.get(posOfResourceID));
/* 5074 */                                       out.write("&attributeid=");
/* 5075 */                                       out.print(row.get(posOfAttribID));
/* 5076 */                                       out.write("&camid=");
/* 5077 */                                       out.print(camIDI);
/* 5078 */                                       out.write("&haid=");
/* 5079 */                                       out.print(haidI);
/* 5080 */                                       out.write("&screenid=");
/* 5081 */                                       out.print(screenIDI);
/* 5082 */                                       out.write("&resourcename=");
/* 5083 */                                       out.print(currentResourceName);
/* 5084 */                                       out.write("&hostname=");
/* 5085 */                                       out.print(token.get(Integer.valueOf(0)));
/* 5086 */                                       out.write("&resourcetype=");
/* 5087 */                                       out.print(token.get(Integer.valueOf(1)));
/* 5088 */                                       out.write("&portno=");
/* 5089 */                                       out.print(token.get(Integer.valueOf(2)));
/* 5090 */                                       out.write("&attributes=");
/* 5091 */                                       out.print(URLEncoder.encode(currentMBeanName + "|" + (String)row.get(1) + "|" + row.get(posOfAttribType)));
/* 5092 */                                       out.write("&displayname=");
/* 5093 */                                       out.print((String)row.get(posOfAttribName));
/* 5094 */                                       out.write("&isfromeditpage=");
/* 5095 */                                       out.print("true");
/* 5096 */                                       out.write("&resourceid=");
/* 5097 */                                       out.print(row.get(posOfResourceID));
/* 5098 */                                       out.write("&dispType=");
/* 5099 */                                       out.print(row.get(posOfDispType));
/* 5100 */                                       out.write("','Personalize','width=390,height=200,screenX=100,screenY=300,scrollbars=yes')\"><img src=\"/images/icon_edit.gif\"  border=\"0\" title='");
/* 5101 */                                       out.print(FormatUtil.getString("jmxnotification.edit"));
/* 5102 */                                       out.write("'></a>\n");
/*      */                                     }
/* 5104 */                                     out.write("\n\n    <A class='staticlinks' href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5105 */                                     out.print(row.get(posOfResourceID));
/* 5106 */                                     out.write("&attributeIDs=");
/* 5107 */                                     out.print(row.get(posOfAttribID));
/* 5108 */                                     out.write("&attributeToSelect=");
/* 5109 */                                     out.print(row.get(posOfAttribID));
/* 5110 */                                     out.write("&redirectto=");
/* 5111 */                                     out.print(configureThresholdRedirectLink);
/* 5112 */                                     out.write("'>\n    <img src=\"/images/icon_associateaction.gif\" title='");
/* 5113 */                                     out.print(ALERTCONFIG_TEXT);
/* 5114 */                                     out.write("' border=\"0\" ></A>\n\n    ");
/*      */                                     
/* 5116 */                                     if (row.get(posOfAttribType).equals("0"))
/*      */                                     {
/*      */ 
/* 5119 */                                       out.write("\n    <a style=\"cursor: pointer;\" onclick=\"showAttribGraph(");
/* 5120 */                                       out.print(row.get(posOfAttribID));
/* 5121 */                                       out.write(44);
/* 5122 */                                       out.write(39);
/* 5123 */                                       out.print(getTrimmedText(currentMBeanName, 50).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 5124 */                                       out.write("')\" ><img src='/images/icon_linegraph.gif' title='");
/* 5125 */                                       out.print(FormatUtil.getString("jmxnotification.showgraph"));
/* 5126 */                                       out.write("' border='0' ></a>\n\n\n        ");
/*      */                                     }
/*      */                                     
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 5133 */                                     out.write("\n\t\t&nbsp;\n\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5137 */                                   out.write("\n</td>\n\n</tr>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 5141 */                                 out.write("\n</tr>\n\n<tr>\n\t<td colspan=4  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                                 
/* 5143 */                                 PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5144 */                                 _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 5145 */                                 _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f10);
/*      */                                 
/* 5147 */                                 _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 5148 */                                 int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 5149 */                                 if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                   for (;;) {
/* 5151 */                                     out.write("\n       <a href=\"");
/* 5152 */                                     out.print(configureLink);
/* 5153 */                                     out.write("\" class='staticlinks'>");
/* 5154 */                                     out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 5155 */                                     out.write("</a> ");
/* 5156 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 5157 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5161 */                                 if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 5162 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                 }
/*      */                                 
/* 5165 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 5166 */                                 out.write("</span></td>\n</tr>\n</table>\n");
/*      */                               }
/*      */                               
/*      */ 
/* 5170 */                               out.write("\n<br>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 5171 */                               JspRuntimeLibrary.include(request, response, "/jsp/MyField_div.jsp", out, false);
/* 5172 */                               out.write("</td></tr></table>\n");
/*      */                               
/* 5174 */                               if (columnarAttribsPresent)
/*      */                               {
/* 5176 */                                 int k = 0;
/* 5177 */                                 String titlePrefix = FormatUtil.getString("am.webclient.common.name.text");
/* 5178 */                                 for (int i = 0; i < colScreenAttribInfo.size(); ???++)
/*      */                                 {
/* 5180 */                                   out.write("\n\t<div class=\"horTableScroll\">\n\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\" class=\"lrtbdarkborder\">\n\t");
/*      */                                   
/* 5182 */                                   List temp1 = (List)colScreenAttribInfo.get(i);
/* 5183 */                                   if (temp1.size() > 0)
/*      */                                   {
/* 5185 */                                     Properties tmpProp = (Properties)((List)temp1.get(0)).get(0);
/* 5186 */                                     String mbeanName = tmpProp.getProperty("mbeanname");
/* 5187 */                                     List h = (List)tmpProp.get("columnnames");
/*      */                                     
/* 5189 */                                     out.write("\n\t\t<tr>\n\t\t<td height=\"24\" width=\"75%\" class=\"tableheadingbborder\" colspan=\"");
/* 5190 */                                     out.print(h.size() + 1);
/* 5191 */                                     out.write("\">\n\t\t");
/* 5192 */                                     out.print(titlePrefix);
/* 5193 */                                     out.write(32);
/* 5194 */                                     out.write(58);
/* 5195 */                                     out.write(32);
/* 5196 */                                     out.print(getTrimmedText(mbeanName, 50));
/* 5197 */                                     out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                                   }
/*      */                                   
/* 5200 */                                   int cnt = 0; for (int size = temp1.size(); cnt < size; ???++)
/*      */                                   {
/*      */ 
/* 5203 */                                     out.write("\n\t\t<tr><td width=\"100%\" style=\"vertical-align: top;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t");
/*      */                                     
/*      */ 
/* 5206 */                                     List oneTableList = (List)temp1.get(cnt);
/* 5207 */                                     Properties camprops = (Properties)oneTableList.get(0);
/* 5208 */                                     List headers = (List)camprops.get("columnnames");
/* 5209 */                                     List thresholdPossibleIDs = (List)camprops.get("thresholdpossibleattrids");
/* 5210 */                                     if ("snmp table".equals(camprops.get("TableType"))) {
/* 5211 */                                       titlePrefix = "SNMP Table Name";
/*      */                                     } else {
/* 5213 */                                       titlePrefix = FormatUtil.getString("am.webclient.camscreen.titleprefix");
/*      */                                     }
/*      */                                     
/*      */ 
/* 5217 */                                     out.write("\n\t\t\t<tr >\n\t\t     ");
/*      */                                     
/* 5219 */                                     String attrs = "";
/* 5220 */                                     for (int a = 0; a < thresholdPossibleIDs.size(); i++)
/*      */                                     {
/* 5222 */                                       attrs = attrs + (String)thresholdPossibleIDs.get(a) + ",";
/*      */                                     }
/*      */                                     
/*      */ 
/* 5226 */                                     out.write("\n\t\t<td height=\"24\" width=\"75%\" class=\"secondchildnode\" colspan=\"");
/* 5227 */                                     out.print(headers.size());
/* 5228 */                                     out.write("\">\n\t\t");
/* 5229 */                                     String temp = (String)camprops.get("attrName");
/* 5230 */                                     out.write("\n\t\t<span class=\"bodytextbold\">");
/* 5231 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.attributegraphs.attribute.text"));
/* 5232 */                                     out.write(32);
/* 5233 */                                     out.write(58);
/* 5234 */                                     out.write(32);
/* 5235 */                                     out.print(getTrimmedText(temp, 50));
/* 5236 */                                     out.write("</span></td>\n\n\t<td class=\"secondchildnode\" width=\"25%\">\n\n\t");
/*      */                                     
/* 5238 */                                     if (thresholdPossibleIDs.size() > 0)
/*      */                                     {
/*      */ 
/*      */ 
/* 5242 */                                       out.write("\n\n\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5243 */                                       out.print(camprops.get("resourceid"));
/* 5244 */                                       out.write("&attributeIDs=");
/* 5245 */                                       out.print(attrs.substring(0, attrs.length() - 1));
/* 5246 */                                       out.write("&attributeToSelect=");
/* 5247 */                                       out.print(thresholdPossibleIDs.get(0));
/* 5248 */                                       out.write("&redirectto=");
/* 5249 */                                       out.print(configureThresholdRedirectLink);
/* 5250 */                                       out.write("' class=\"staticlinks\">\n        <img src=\"/images/icon_associateaction.gif\" alt=\"Associate Action\" border=\"0\" align=\"absmiddle\" hspace=\"5\" >");
/* 5251 */                                       out.print(ALERTCONFIG_TEXT);
/* 5252 */                                       out.write("</a>\n        ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 5256 */                                     out.write("\n\t\t</td>\n\t\t</tr>\n\t        <tr>\n\t\t");
/*      */                                     
/* 5258 */                                     for (k = 0; k < headers.size(); ???++)
/*      */                                     {
/*      */ 
/* 5261 */                                       out.write("\n\t\t\t\t<td class=\"columnheading\" align=left>\n\t\t\t\t");
/* 5262 */                                       out.print(headers.get(k));
/* 5263 */                                       out.write("\n\t\t\t\t</td>\n\t\t\t");
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/* 5268 */                                     out.write("\n\t\t<td class=\"columnheading\" width=\"19%\">&nbsp;</td>\n\t        </tr>\n\t        ");
/*      */                                     int k;
/* 5270 */                                     for (int j = 1; j < oneTableList.size(); k++)
/*      */                                     {
/* 5272 */                                       String bgclass = "class=\"whitegrayrightalign-reports\"";
/* 5273 */                                       if (j % 2 != 0)
/*      */                                       {
/* 5275 */                                         bgclass = "class=\"whitegrayrightalign-reports\"";
/*      */                                       }
/*      */                                       
/* 5278 */                                       out.write("\n\t        \t\t<tr>\n\t        \t\t");
/*      */                                       int m;
/* 5280 */                                       for (int l = 0; l < headers.size(); m++)
/*      */                                       {
/*      */ 
/* 5283 */                                         out.write("\n\t\t\t\t\t<td height=\"28\"  ");
/* 5284 */                                         out.print(bgclass);
/* 5285 */                                         out.write(" align=\"left\" title=\"");
/* 5286 */                                         out.print(formatDT((String)camprops.get("dctime")));
/* 5287 */                                         out.write("\">\n\t\t\t\t\t\t<div  style=\"WORD-BREAK:BREAK-ALL; word-wrap: break-word; width:100px; white-space:-moz-pre-wrap; white-space: normal;\">");
/* 5288 */                                         out.print(((List)oneTableList.get(j)).get(l));
/* 5289 */                                         out.write("</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/* 5293 */                                       out.write("\n\n\t\t\t<td ");
/* 5294 */                                       out.print(bgclass);
/* 5295 */                                       out.write(" width=\"19%\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t");
/*      */                                     }
/*      */                                     
/*      */ 
/* 5299 */                                     out.write("\n\t</table></td></tr>\n\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5303 */                                   out.write("\n<tr>\n        <td colspan=");
/* 5304 */                                   out.print(k + 1);
/* 5305 */                                   out.write("  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                                   
/* 5307 */                                   PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5308 */                                   _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 5309 */                                   _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f10);
/*      */                                   
/* 5311 */                                   _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 5312 */                                   int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 5313 */                                   if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                     for (;;) {
/* 5315 */                                       out.write("\n       <a href=\"");
/* 5316 */                                       out.print(configureLink);
/* 5317 */                                       out.write("\" class='staticlinks'>");
/* 5318 */                                       out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 5319 */                                       out.write("</a> ");
/* 5320 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 5321 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5325 */                                   if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 5326 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                   }
/*      */                                   
/* 5329 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 5330 */                                   out.write("</span></td>\n</tr>\n\n\n</table><br></div>\n");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 5336 */                             out.write("\n<br><br>\n\n<!-- Added graphs to the Normal Screen -->\n<div id=\"status\" style='Display:none'>");
/* 5337 */                             out.print(FormatUtil.getString("am.webclient.gengraph.text"));
/* 5338 */                             out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"attributegraphs\"></div>\n\n\n\n<script>\nmyOnLoad();\nfunction myOnLoad()\n{\n/**\t");
/* 5339 */                             if ((request.getParameter("type") != null) && (request.getParameter("type").equals("JBOSS-server"))) {
/* 5340 */                               out.write("\n\tmyOnJbossLoad();\n\t");
/*      */                             }
/* 5342 */                             out.write("\n\t**/\n\tattributegraphs();\n}\nvar http1;\nfunction attributegraphs()\n{\n        document.getElementById(\"status\").style.display='block';\n        URL='/jsp/cam_graphs.jsp?camIDI=");
/* 5343 */                             out.print(camIDI);
/* 5344 */                             out.write("&haidI=");
/* 5345 */                             out.print(haidI);
/* 5346 */                             out.write("&screenIDI=");
/* 5347 */                             out.print(screenIDI);
/* 5348 */                             out.write("&isfromresourcepage=");
/* 5349 */                             out.print(isFromResourcePage);
/* 5350 */                             out.write("';\n        http1=getHTTPObject();\n        http1.open(\"GET\", URL, true);\n        //http1.onreadystatechange = new Function('if(http1.readyState == 4 && http1.status == 200){document.getElementById(\"status\").innerHTML =\"&nbsp;\",document.getElementById(\"attributegraphs\").innerHTML = http1.responseText;}' );\n\thttp1.onreadystatechange =handleResponse1;\n        http1.send(null);\n}\n\nfunction handleResponse1()\n{\n        if(http1.readyState == 4)\n        {\n                var result = http1.responseText;\n\t\tdocument.getElementById(\"status\").innerHTML =\"&nbsp;\"\n                document.getElementById(\"attributegraphs\").innerHTML = result;\n                document.getElementById(\"attributegraphs\").style.display='block';\n        //      alert('Div similarmonitor display' + document.getElementById(\"multimonitors\").checked);\n        }\n}\n\n\nfunction subAddCustom(linkS) {\n\tlinkS.href = \"");
/* 5351 */                             out.print(configureLink);
/* 5352 */                             out.write("\";\n\treturn true;\n}\n\n$(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 5353 */                             out.write("\n\t{\n\t\t");
/* 5354 */                             if (_jspx_meth_c_005fif_005f17(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 5356 */                             out.write(10);
/* 5357 */                             out.write(9);
/* 5358 */                             out.write(9);
/* 5359 */                             if (_jspx_meth_c_005fif_005f18(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 5361 */                             out.write("\n\t\tgetCustomFields('");
/* 5362 */                             if (_jspx_meth_c_005fout_005f67(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                               return;
/* 5364 */                             out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 5365 */                             out.write("\n\t}\n\n});\n</script>\n\n\n");
/* 5366 */                             out.write(10);
/* 5367 */                             out.write(10);
/* 5368 */                             out.write(10);
/* 5369 */                             out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\n\n\n\n<script>\nfunction deleteMopSelections()\n{\n\tvar sel = false;\n \tfor(i=0;i<document.formab.elements.length;i++)\n \t{\n \t\tif(document.formab.elements[i].type==\"checkbox\")\n \t               {\n \t                        var name = document.formab.elements[i].name;\n \t                        if(name==\"execmopcheckbox\")\n \t                        {\n \t                        \tvar value = document.formab.elements[i].value;\n \t                        \tsel=document.formab.elements[i].checked;\n \t                        \tif(sel)\n \t                        \t{\n \t                        \t\tbreak;\n \t                        \t}\n \t                        }\n \t                 }\n         }\n \tif(!sel)\n \t{\n \t     alert('");
/* 5370 */                             out.print(FormatUtil.getString("am.webclient.viewaction.alertmbeandelete"));
/* 5371 */                             out.write("');\n \t}\n \telse if(confirm('");
/* 5372 */                             out.print(FormatUtil.getString("am.webclient.viewaction.alertdeleteconfirm"));
/* 5373 */                             out.write("'))\n\t{\n\t    document.formab.action=\"/adminAction.do?method=deleteMBeanOperationAction\";\n\t    document.formab.method=\"Post\"\n\t    document.formab.submit();\n\t}\n}\nfunction deleteThreadDump(url,id)\n{\n      \tif(!confirm(\"");
/* 5374 */                             out.print(FormatUtil.getString("am.javaruntime.confirm.delete.text"));
/* 5375 */                             out.write("\"))\n      \t{\n       \t\treturn;//No I18N\n      \t}\n\tvar url =\"/CAMUpdateScreenAttributes.do?method=deletethreadURL&url=\"+url; //NO I18N\n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse3;//NO I18N\n\thttp.open(\"GET\",url,true);\n\thttp.send(null); //NO I18N\n}\nfunction getThreadDumpData(rid,tabval)\n{\n\tif(document.getElementById('exturl').style.display=='block')\n\t{\n\t    var showall = document.getElementById('more'); //NO I18N\n\t    showall.innerHTML=\"More...\"; //NO I18N\n\t    toggleDiv('exturl'); //NO I18N\n\t    return;\n\t}\n\tvar date = new Date(); //NO I18N\n\tvar url ='/CAMUpdateScreenAttributes.do?resourceid='+rid+'&method=getThreadDump'; //NO I18N\n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse2 //NO I18N\n\thttp.open(\"POST\", url, true); //NO I18N\n\thttp.send(null); //NO I18N\n\n}\nfunction handleResponse2() \n{\n\t if(http.readyState == 4 && http.status == 200)\n\t {\n\t\tvar result = http.responseText;\n\t\tdocument.getElementById('exturl').innerHTML = result; //NO I18N\n\t\tvar showall = document.getElementById('more'); //NO I18N\n");
/* 5376 */                             out.write("\t\tshowall.innerHTML=\"Hide...\"; //NO I18N\n\t\ttoggleDiv('exturl'); //NO I18N\n\t }\n}\nfunction handleResponse3() {\n\tif (http.readyState == 4) {\n\t\tvar result = http.responseText;\n\t\tvar ele = document.getElementById('groupContent');\n\t\tif(ele)\n\t\t{\n\t\t\tele.innerHTML = result;\n\t\t\tconfBodyLoad();\n\t\t}\n\t}\n\t\n}\n</script>\n<form name=\"formab\">\n<input type=\"hidden\" name=\"redirectto\" value=\"");
/* 5377 */                             out.print(java.net.URLDecoder.decode(mopRedirectString));
/* 5378 */                             out.write("\">\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t    <tr>\n\t\t\t\t          <td width=\"100%\" height=\"31\" class=\"tableheading\" >");
/* 5379 */                             out.print(FormatUtil.getString("am.webclient.common.mbeanoperations.text"));
/* 5380 */                             out.write(" :</td>\n\t\t\t\t        </tr>\n\t\t\t\t      </table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n\n            ");
/*      */                             
/* 5382 */                             PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/* 5383 */                             _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 5384 */                             _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 5386 */                             _jspx_th_logic_005fpresent_005f5.setName("executeMopActions");
/* 5387 */                             int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 5388 */                             if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                               for (;;) {
/* 5390 */                                 out.write("\n            <tr>\n              <td> <SCRIPT LANGUAGE=\"javascript\" SRC=\"../webclient/common/js/windowFunctions.js\"></SCRIPT>\n              </td>\n            </tr>\n            <tr>\n              <td>\n\n\n \t\t<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">\n\t\t<tr>\n\t\t<td width=\"2%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">\n\t\t<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:ToggleAll(this,document.formab,'execmopcheckbox')\">\n\t\t</td>\n\n\t\t<td width=\"12%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 5391 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 5392 */                                 out.write("</td>\n\t\t<td width=\"21%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 5393 */                                 out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 5394 */                                 out.write("</td>\n\t\t<td width=\"27%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 5395 */                                 out.print(FormatUtil.getString("am.reporttab.mbeanname.text"));
/* 5396 */                                 out.write("</td>\n\t\t<td width=\"23%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 5397 */                                 out.print(FormatUtil.getString("am.webclient.viewaction.operation"));
/* 5398 */                                 out.write(" </td>\n\t\t<!--td width=\"23%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 5399 */                                 out.print(FormatUtil.getString("am.webclient.viewaction.argscount"));
/* 5400 */                                 out.write(" </td-->\n\t\t<td width=\"23%\" height=\"28\" valign=\"center\" class=\"columnheadingnotop\">");
/* 5401 */                                 out.print(FormatUtil.getString("am.webclient.viewaction.targetemail.text"));
/* 5402 */                                 out.write("</td>\n\t\t<td width=\"11%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 5403 */                                 out.print(FormatUtil.getString("am.webclient.threshold.editview"));
/* 5404 */                                 out.write("</td>\n\t\t<td width=\"6%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"> ");
/* 5405 */                                 out.print(FormatUtil.getString("am.webclient.viewaction.execute"));
/* 5406 */                                 out.write("</td>\n\t\t<td width=\"6%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"> ");
/* 5407 */                                 out.print(FormatUtil.getString("am.webclient.viewaction.manualexecution"));
/* 5408 */                                 out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                                 
/* 5410 */                                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 5411 */                                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 5412 */                                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fpresent_005f5);
/*      */                                 
/* 5414 */                                 _jspx_th_logic_005fiterate_005f0.setName("executeMopActions");
/*      */                                 
/* 5416 */                                 _jspx_th_logic_005fiterate_005f0.setScope("request");
/*      */                                 
/* 5418 */                                 _jspx_th_logic_005fiterate_005f0.setId("moprow");
/*      */                                 
/* 5420 */                                 _jspx_th_logic_005fiterate_005f0.setIndexId("mopm");
/* 5421 */                                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 5422 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 5423 */                                   Object moprow = null;
/* 5424 */                                   Integer mopm = null;
/* 5425 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5426 */                                     out = _jspx_page_context.pushBody();
/* 5427 */                                     _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 5428 */                                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                   }
/* 5430 */                                   moprow = _jspx_page_context.findAttribute("moprow");
/* 5431 */                                   mopm = (Integer)_jspx_page_context.findAttribute("mopm");
/*      */                                   for (;;) {
/* 5433 */                                     out.write(10);
/* 5434 */                                     out.write(9);
/* 5435 */                                     out.write(9);
/*      */                                     
/* 5437 */                                     String bgclass = "whitegrayborder";
/* 5438 */                                     if (mopm.intValue() % 2 != 0)
/*      */                                     {
/* 5440 */                                       bgclass = "yellowgrayborder";
/*      */                                     }
/*      */                                     
/* 5443 */                                     out.write("\n\t\t<tr>\n\n\t\t<td class=\"");
/* 5444 */                                     out.print(bgclass);
/* 5445 */                                     out.write("\">\n\t\t<input type=\"checkbox\" name=\"execmopcheckbox\" value=\"");
/* 5446 */                                     out.print(((ArrayList)moprow).get(0));
/* 5447 */                                     out.write("\">\n\t\t</td>\n\t\t<td height=\"22\" class=\"");
/* 5448 */                                     out.print(bgclass);
/* 5449 */                                     out.write("\"><!--a href=\"#\" class=\"resourcename\" onClick=\"MM_openBrWindow('/showActionProfiles.do?method=getActionDetails&actionid=");
/* 5450 */                                     out.print(((ArrayList)moprow).get(0));
/* 5451 */                                     out.write("','','resizable=yes,width=450,height=185')\"-->\n\t\t");
/* 5452 */                                     out.print(getTrimmedText((String)((ArrayList)moprow).get(1), 25));
/* 5453 */                                     out.write("</a>\n\t\t</td>\n\t\t");
/*      */                                     
/* 5455 */                                     boolean hasArgs = false;
/*      */                                     
/* 5457 */                                     out.write("\n\t\t\t");
/*      */                                     
/* 5459 */                                     IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.get(IterateTag.class);
/* 5460 */                                     _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 5461 */                                     _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                     
/* 5463 */                                     _jspx_th_logic_005fiterate_005f1.setName("moprow");
/*      */                                     
/* 5465 */                                     _jspx_th_logic_005fiterate_005f1.setId("acolumn");
/*      */                                     
/* 5467 */                                     _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/*      */                                     
/* 5469 */                                     _jspx_th_logic_005fiterate_005f1.setOffset("2");
/* 5470 */                                     int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 5471 */                                     if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 5472 */                                       Object acolumn = null;
/* 5473 */                                       Integer i = null;
/* 5474 */                                       if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5475 */                                         out = _jspx_page_context.pushBody();
/* 5476 */                                         _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 5477 */                                         _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                       }
/* 5479 */                                       acolumn = _jspx_page_context.findAttribute("acolumn");
/* 5480 */                                       i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                       for (;;) {
/* 5482 */                                         out.write("\n\t\t\t");
/*      */                                         
/* 5484 */                                         if (i.intValue() == 6)
/*      */                                         {
/* 5486 */                                           if (Integer.parseInt((String)acolumn) > 0)
/*      */                                           {
/* 5488 */                                             hasArgs = true;
/*      */                                           }
/*      */                                           
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 5494 */                                           out.write("\n\t\t\t<td height='22' class='");
/* 5495 */                                           out.print(bgclass);
/* 5496 */                                           out.write("' title='");
/* 5497 */                                           out.print((String)acolumn);
/* 5498 */                                           out.write("'>\n\n\t\t\t");
/* 5499 */                                           out.print(getTrimmedText((String)acolumn, 25));
/* 5500 */                                           out.write("\n\t\t\t</td>\n\t\t\t");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5504 */                                         out.write("\n\t\t\t");
/* 5505 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 5506 */                                         acolumn = _jspx_page_context.findAttribute("acolumn");
/* 5507 */                                         i = (Integer)_jspx_page_context.findAttribute("i");
/* 5508 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5511 */                                       if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5512 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5515 */                                     if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 5516 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                     }
/*      */                                     
/* 5519 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 5520 */                                     out.write("\n\t\t<td class=\"");
/* 5521 */                                     out.print(bgclass);
/* 5522 */                                     out.write("\"><a href=\"/adminAction.do?method=showMBeanOperationAction&actionID=");
/* 5523 */                                     out.print(((ArrayList)moprow).get(0));
/* 5524 */                                     out.write("&haid=");
/* 5525 */                                     out.print(request.getParameter("haid"));
/* 5526 */                                     out.write("&redirectto=");
/* 5527 */                                     out.print(mopRedirectString);
/* 5528 */                                     out.write("\"><img src=\"/images/icon_edit.gif\"  border=\"0\"></a></td>\n\t\t<td width=\"4%\"height=\"28\" align=\"center\"  class=\"");
/* 5529 */                                     out.print(bgclass);
/* 5530 */                                     out.write("\">\n\t\t");
/* 5531 */                                     if (_jspx_meth_logic_005fpresent_005f6(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                                       return;
/* 5533 */                                     out.write(10);
/* 5534 */                                     out.write(9);
/* 5535 */                                     out.write(9);
/*      */                                     
/* 5537 */                                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 5538 */                                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 5539 */                                     _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                     
/* 5541 */                                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO,ENTERPRISEADMIN");
/* 5542 */                                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 5543 */                                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                       for (;;) {
/* 5545 */                                         out.write("\n \t\t\t<a href=\"/GlobalActions.do?method=testAction&actionID=");
/* 5546 */                                         out.print(((ArrayList)moprow).get(0));
/* 5547 */                                         out.write("&haid=");
/* 5548 */                                         out.print(request.getParameter("haid") + "&redirectto=" + mopRedirectString);
/* 5549 */                                         out.write("\"><img src=\"/images/icon_executeaction.gif\"  border=\"0\"></a></td>\n \t\t\t<td class=\"");
/* 5550 */                                         out.print(bgclass);
/* 5551 */                                         out.write("\">\n \t\t\t");
/*      */                                         
/* 5553 */                                         if (hasArgs)
/*      */                                         {
/*      */ 
/* 5556 */                                           out.write("\n \t\t\t<a href=\"javascript:void(0)\" onclick=\"javascript:MM_openBrWindow('/MBeanOperationAction.do?method=executeMBeanOperationActionWithUserIntervention&actionID=");
/* 5557 */                                           out.print(((ArrayList)moprow).get(0));
/* 5558 */                                           out.write("&haid=");
/* 5559 */                                           out.print(request.getParameter("haid"));
/* 5560 */                                           out.write("','','width=580,height=385')\"><img src=\"/images/icon_execute_user.gif\"  border=\"0\" title='");
/* 5561 */                                           out.print(FormatUtil.getString("am.webclient.viewaction.mbeantitle"));
/* 5562 */                                           out.write("'></a>\n \t\t\t");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/*      */ 
/* 5568 */                                           out.write("\n \t\t\t");
/* 5569 */                                           out.print(FormatUtil.getString("am.webclient.viewaction.na"));
/* 5570 */                                           out.write("\n \t\t\t");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5574 */                                         out.write("\n \t\t\t</td>\n\t\t");
/* 5575 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 5576 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5580 */                                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 5581 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                     }
/*      */                                     
/* 5584 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 5585 */                                     out.write("\n\t\t</tr>\n\t\t");
/* 5586 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 5587 */                                     moprow = _jspx_page_context.findAttribute("moprow");
/* 5588 */                                     mopm = (Integer)_jspx_page_context.findAttribute("mopm");
/* 5589 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 5592 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5593 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 5596 */                                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 5597 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                 }
/*      */                                 
/* 5600 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 5601 */                                 out.write("\n\t\t</table>\n\t  \t\t\t\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"Tablebottom\">\n\t  \t\t\t\t<tr>\n\t  \t\t\t\t<td width=\"72%\" height=\"26\" class=\"bodytext\">\n\t  \t\t\t\t");
/*      */                                 
/* 5603 */                                 AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 5604 */                                 _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 5605 */                                 _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fpresent_005f5);
/*      */                                 
/* 5607 */                                 _jspx_th_am_005fadminlink_005f0.setHref("javascript:deleteMopSelections(this.form);");
/*      */                                 
/* 5609 */                                 _jspx_th_am_005fadminlink_005f0.setEnableClass("staticlinks");
/* 5610 */                                 int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 5611 */                                 if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 5612 */                                   if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 5613 */                                     out = _jspx_page_context.pushBody();
/* 5614 */                                     _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 5615 */                                     _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 5618 */                                     out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 5619 */                                     int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 5620 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 5623 */                                   if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 5624 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 5627 */                                 if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 5628 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                 }
/*      */                                 
/* 5631 */                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 5632 */                                 out.write("\n\t  \t\t\t\t|\n\t  \t\t\t\t");
/*      */                                 
/* 5634 */                                 AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 5635 */                                 _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 5636 */                                 _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_logic_005fpresent_005f5);
/*      */                                 
/* 5638 */                                 _jspx_th_am_005fadminlink_005f1.setHref(linkForMopAction);
/*      */                                 
/* 5640 */                                 _jspx_th_am_005fadminlink_005f1.setEnableClass("staticlinks");
/* 5641 */                                 int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 5642 */                                 if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 5643 */                                   if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 5644 */                                     out = _jspx_page_context.pushBody();
/* 5645 */                                     _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 5646 */                                     _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 5649 */                                     out.write("\n\t  \t\t\t\t");
/* 5650 */                                     out.print(FormatUtil.getString("am.webclient.threshold.addnew"));
/* 5651 */                                     int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 5652 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 5655 */                                   if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 5656 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 5659 */                                 if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 5660 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                 }
/*      */                                 
/* 5663 */                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 5664 */                                 out.write("</td>\n\t  \t\t\t\t</tr>\n\t  \t\t\t\t</table>\n\n              </td>\n            </tr>\n            ");
/* 5665 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 5666 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5670 */                             if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 5671 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                             }
/*      */                             
/* 5674 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f5);
/* 5675 */                             out.write("\n            ");
/*      */                             
/* 5677 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/* 5678 */                             _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 5679 */                             _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f10);
/*      */                             
/* 5681 */                             _jspx_th_logic_005fnotPresent_005f1.setName("executeMopActions");
/* 5682 */                             int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 5683 */                             if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                               for (;;) {
/* 5685 */                                 out.write("\n            <tr>\n            <td>\n           \t\t<table>\n           \t\t<tr>\n\n<td class=\"bodytext\" height=\"29\" valign=\"center\">&nbsp;");
/* 5686 */                                 out.print(FormatUtil.getString("am.webclient.viewaction.noactionscreated"));
/* 5687 */                                 out.write(32);
/*      */                                 
/* 5689 */                                 IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5690 */                                 _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 5691 */                                 _jspx_th_c_005fif_005f19.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                 
/* 5693 */                                 _jspx_th_c_005fif_005f19.setTest("${!empty ADMIN}");
/* 5694 */                                 int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 5695 */                                 if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                                   for (;;) {
/* 5697 */                                     out.write(10);
/* 5698 */                                     out.write(32);
/* 5699 */                                     out.write(32);
/* 5700 */                                     out.print(FormatUtil.getString("am.webclient.viewaction.clickto"));
/* 5701 */                                     out.write(" <a href=\"");
/* 5702 */                                     out.print(linkForMopAction);
/* 5703 */                                     out.write("\" class=\"resourcename\">\n  ");
/* 5704 */                                     out.print(FormatUtil.getString("am.webclient.threshold.creatembean"));
/* 5705 */                                     out.write("</a>");
/* 5706 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 5707 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5711 */                                 if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 5712 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                                 }
/*      */                                 
/* 5715 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 5716 */                                 out.write("</td>\n           \t\t</tr>\n               \t</table>\n           </td>\n           </tr>\n           ");
/* 5717 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 5718 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5722 */                             if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 5723 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                             }
/*      */                             
/* 5726 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 5727 */                             out.write("\n          \t</table>\n\n");
/*      */                             
/* 5729 */                             HashMap threadProps11 = (HashMap)request.getAttribute("threadProps");
/*      */                             try {
/* 5731 */                               if (threadProps11 != null) {
/* 5732 */                                 ArrayList threaddumphistory = (ArrayList)threadProps11.get("threadurls");
/* 5733 */                                 int rowCount = ((Integer)threadProps11.get("ROW_COUNT")).intValue();
/* 5734 */                                 String resourceid11 = "" + request.getParameter("resourceid");
/*      */                                 
/* 5736 */                                 out.write(10);
/*      */                                 
/* 5738 */                                 PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5739 */                                 _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 5740 */                                 _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f10);
/*      */                                 
/* 5742 */                                 _jspx_th_logic_005fpresent_005f7.setRole("ADMIN");
/* 5743 */                                 int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 5744 */                                 if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                   for (;;) {
/* 5746 */                                     out.write("\n <br>\n     <input class=\"buttons\" value=\"");
/* 5747 */                                     out.print(FormatUtil.getString("am.webclient.jdk15.threadinfo.text"));
/* 5748 */                                     out.write("\" type=\"button\" onClick=\"javascript:MM_openBrWindow('/JavaRuntime.do?method=getThreadInfo&resourceid=");
/* 5749 */                                     out.print(resourceid11);
/* 5750 */                                     out.write("','ThreadInfo','scrollbars=yes,resizable=yes')\"> \n <br>\n");
/* 5751 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 5752 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5756 */                                 if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 5757 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                 }
/*      */                                 
/* 5760 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 5761 */                                 out.write("\n\n<br>\n<table width=\"99%\"   border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" height=\"31\" class=\"tableheading\">");
/* 5762 */                                 out.print(FormatUtil.getString("am.javaruntime.threaddumphistory"));
/* 5763 */                                 out.write("</td>\n</tr> \n");
/*      */                                 
/* 5765 */                                 if (threaddumphistory.size() > 0)
/*      */                                 {
/*      */ 
/* 5768 */                                   out.write("\n\t<tr>\n\t<td width=\"80%\" class=\"columnheadingb\">");
/* 5769 */                                   out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 5770 */                                   out.write(32);
/* 5771 */                                   out.write(38);
/* 5772 */                                   out.write(32);
/* 5773 */                                   out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 5774 */                                   out.write("</td>\n");
/* 5775 */                                   if ((request.isUserInRole("ADMIN")) && (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */                                   {
/*      */ 
/* 5778 */                                     out.write("\n\t<td width=\"20%%\" class=\"columnheadingb\">");
/* 5779 */                                     out.print(FormatUtil.getString("am.webclient.rbm.delete.text"));
/* 5780 */                                     out.write("</td>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5784 */                                   out.write("\n</tr>\n\t\n");
/*      */                                   
/* 5786 */                                   boolean extra = false;
/* 5787 */                                   for (int k = 0; k < threaddumphistory.size(); i++)
/*      */                                   {
/* 5789 */                                     Properties url = (Properties)threaddumphistory.get(k);
/*      */                                     
/* 5791 */                                     out.write("\n\t<tr>\n\t<td style=\"padding-left:26px\" class=\"whitegrayborderbr\" title=\"");
/* 5792 */                                     out.print(url.getProperty("URL"));
/* 5793 */                                     out.write("\">\n\t<a class=\"staticlinks-blue\" href=\"javascript:void(0);\" onclick=\"javascript:MM_openBrWindow('");
/* 5794 */                                     out.print(url.getProperty("URL"));
/* 5795 */                                     out.write("','ThreadInfo','scrollbars=yes,resizable=yes')\">");
/* 5796 */                                     out.print(url.getProperty("DSPNAME"));
/* 5797 */                                     out.write("</a>\n\t</td>\n");
/* 5798 */                                     if ((request.isUserInRole("ADMIN")) && (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */                                     {
/*      */ 
/* 5801 */                                       out.write("\t\n\t<td class=\"whitegrayborderbr\">\n\t<a title=\"Delete Thread Dump File\"  class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"javascript:deleteThreadDump('");
/* 5802 */                                       out.print(url.getProperty("ABSURL"));
/* 5803 */                                       out.write(39);
/* 5804 */                                       out.write(44);
/* 5805 */                                       out.write(39);
/* 5806 */                                       out.print(resourceid11);
/* 5807 */                                       out.write("');return false;\">\n\t<img hspace=\"5\" border=\"0\" src=\"/images/deleteWidget.gif\"/>\n\t</a>\n\t</td>\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 5811 */                                     out.write("\n\t</tr>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5815 */                                   out.write("\t \n\t<tr>\n\t<td colspan=\"2\">\n\t<div id=\"exturl\" style=\"display:none\">\n\n\t</div>\n\t</td>\n\t</tr>\n");
/*      */                                   
/* 5817 */                                   if (rowCount > 5)
/*      */                                   {
/*      */ 
/* 5820 */                                     out.write("\n\t<tr>\n\t<td class=\"columnheadingb\" colspan=\"2\" align=\"right\"><a class=\"bodytext-nounderline\" href=\"javascript:void(0)\" id=\"more\" onclick=\"javascript:getThreadDumpData('");
/* 5821 */                                     out.print(resourceid11);
/* 5822 */                                     out.write("');\" >More...</a></td>");
/* 5823 */                                     out.write("\n\t</tr>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 5830 */                                   out.write("\n\t<tr>\n\t<td  colspan=\"2\" class=\"whitegrayborderbr\" align=\"center\">");
/* 5831 */                                   out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 5832 */                                   out.write("</td>\n\t</tr>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 5836 */                                 out.write("\n</table>\n");
/*      */                               }
/*      */                             } catch (Exception ex) {
/* 5839 */                               ex.printStackTrace();
/*      */                             }
/* 5841 */                             out.write("\n</form>\n");
/* 5842 */                             out.write(10);
/*      */                           }
/* 5844 */                           out.write(10);
/* 5845 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 5846 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5850 */                       if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 5851 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                       }
/*      */                       
/* 5854 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 5855 */                       out.write(10);
/* 5856 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 5857 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5861 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 5862 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/* 5865 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5866 */                   out.write(10);
/* 5867 */                   if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                     return;
/* 5869 */                   out.write(10);
/* 5870 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 5871 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 5875 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 5876 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 5879 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5880 */               out.write("\n<br>\n\t");
/* 5881 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 5882 */               com.adventnet.awolf.data.support.DialChartSupport dialGraph = null;
/* 5883 */               dialGraph = (com.adventnet.awolf.data.support.DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 5884 */               if (dialGraph == null) {
/* 5885 */                 dialGraph = new com.adventnet.awolf.data.support.DialChartSupport();
/* 5886 */                 _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */               }
/* 5888 */               out.write(10);
/*      */               
/*      */               try
/*      */               {
/* 5892 */                 String hostos = (String)systeminfo.get("HOSTOS");
/* 5893 */                 String hostname = (String)systeminfo.get("HOSTNAME");
/* 5894 */                 String hostid = (String)systeminfo.get("host_resid");
/* 5895 */                 boolean isConf = false;
/* 5896 */                 if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 5897 */                   isConf = true;
/*      */                 }
/* 5899 */                 com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 5900 */                 Properties property = new Properties();
/* 5901 */                 if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                 {
/* 5903 */                   property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 5904 */                   if ((property != null) && (property.size() > 0))
/*      */                   {
/* 5906 */                     String cpuid = property.getProperty("cpuid");
/* 5907 */                     String memid = property.getProperty("memid");
/* 5908 */                     String diskid = property.getProperty("diskid");
/* 5909 */                     String cpuvalue = property.getProperty("CPU Utilization");
/* 5910 */                     String memvalue = property.getProperty("Memory Utilization");
/* 5911 */                     String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 5912 */                     String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 5913 */                     String diskvalue = property.getProperty("Disk Utilization");
/* 5914 */                     String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                     
/* 5916 */                     if (!isConf) {
/* 5917 */                       out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 5918 */                       out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 5919 */                       out.write(45);
/* 5920 */                       if (systeminfo.get("host_resid") != null) {
/* 5921 */                         out.write("<a href=\"showresource.do?resourceid=");
/* 5922 */                         out.print(hostid);
/* 5923 */                         out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5924 */                         out.print(hostname);
/* 5925 */                         out.write("</a>");
/* 5926 */                       } else { out.println(hostname); }
/* 5927 */                       out.write("</td>\t");
/* 5928 */                       out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 5929 */                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5930 */                       out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                       
/*      */ 
/* 5933 */                       if (cpuvalue != null)
/*      */                       {
/*      */ 
/* 5936 */                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5937 */                         out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5938 */                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5939 */                         out.write(45);
/* 5940 */                         out.print(cpuvalue);
/* 5941 */                         out.write(" %'>\n\n");
/*      */                         
/* 5943 */                         DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5944 */                         _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 5945 */                         _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                         
/* 5947 */                         _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                         
/* 5949 */                         _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                         
/* 5951 */                         _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                         
/* 5953 */                         _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                         
/* 5955 */                         _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                         
/* 5957 */                         _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                         
/* 5959 */                         _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                         
/* 5961 */                         _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                         
/* 5963 */                         _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                         
/* 5965 */                         _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 5966 */                         int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 5967 */                         if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 5968 */                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5969 */                             out = _jspx_page_context.pushBody();
/* 5970 */                             _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 5971 */                             _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 5974 */                             out.write(10);
/* 5975 */                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 5976 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5979 */                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5980 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5983 */                         if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 5984 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                         }
/*      */                         
/* 5987 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 5988 */                         out.write("\n         </td>\n            ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 5992 */                         out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5993 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5994 */                         out.write(32);
/* 5995 */                         out.write(62);
/* 5996 */                         out.write(10);
/* 5997 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5998 */                         out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                       }
/* 6000 */                       out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 6001 */                       if (cpuvalue != null)
/*      */                       {
/* 6003 */                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6004 */                         out.print(hostid);
/* 6005 */                         out.write("&attributeid=");
/* 6006 */                         out.print(cpuid);
/* 6007 */                         out.write("&period=-7')\" class='bodytextbold'>");
/* 6008 */                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 6009 */                         out.write(32);
/* 6010 */                         out.write(45);
/* 6011 */                         out.write(32);
/* 6012 */                         out.print(cpuvalue);
/* 6013 */                         out.write("</a> %\n");
/*      */                       }
/* 6015 */                       out.write("\n  </td>\n       </tr>\n       </table>");
/* 6016 */                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 6017 */                       out.write("</td>\n      <td width=\"30%\"> ");
/* 6018 */                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 6019 */                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                       
/* 6021 */                       if (memvalue != null)
/*      */                       {
/*      */ 
/* 6024 */                         dialGraph.setValue(Long.parseLong(memvalue));
/* 6025 */                         out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 6026 */                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 6027 */                         out.write(45);
/* 6028 */                         out.print(memvalue);
/* 6029 */                         out.write(" %' >\n\n");
/*      */                         
/* 6031 */                         DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 6032 */                         _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 6033 */                         _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                         
/* 6035 */                         _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                         
/* 6037 */                         _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                         
/* 6039 */                         _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                         
/* 6041 */                         _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                         
/* 6043 */                         _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                         
/* 6045 */                         _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                         
/* 6047 */                         _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                         
/* 6049 */                         _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                         
/* 6051 */                         _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                         
/* 6053 */                         _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 6054 */                         int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 6055 */                         if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 6056 */                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 6057 */                             out = _jspx_page_context.pushBody();
/* 6058 */                             _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 6059 */                             _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 6062 */                             out.write(32);
/* 6063 */                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 6064 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 6067 */                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 6068 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 6071 */                         if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 6072 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                         }
/*      */                         
/* 6075 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 6076 */                         out.write(32);
/* 6077 */                         out.write("\n            </td>\n            ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 6081 */                         out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 6082 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6083 */                         out.write(" >\n\n");
/* 6084 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6085 */                         out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                       }
/* 6087 */                       out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 6088 */                       if (memvalue != null)
/*      */                       {
/* 6090 */                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6091 */                         out.print(hostid);
/* 6092 */                         out.write("&attributeid=");
/* 6093 */                         out.print(memid);
/* 6094 */                         out.write("&period=-7')\" class='bodytextbold'>");
/* 6095 */                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 6096 */                         out.write(45);
/* 6097 */                         out.print(memvalue);
/* 6098 */                         out.write("</a> %\n  ");
/*      */                       }
/* 6100 */                       out.write("\n  </td>\n       </tr>\n    </table>");
/* 6101 */                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 6102 */                       out.write("</td>\n      <td width=\"30%\">");
/* 6103 */                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 6104 */                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                       
/*      */ 
/* 6107 */                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                       {
/*      */ 
/*      */ 
/* 6111 */                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 6112 */                         out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 6113 */                         out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 6114 */                         out.write(45);
/* 6115 */                         out.print(diskvalue);
/* 6116 */                         out.write("%' >\n");
/*      */                         
/* 6118 */                         DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 6119 */                         _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 6120 */                         _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                         
/* 6122 */                         _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                         
/* 6124 */                         _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                         
/* 6126 */                         _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                         
/* 6128 */                         _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                         
/* 6130 */                         _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                         
/* 6132 */                         _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                         
/* 6134 */                         _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                         
/* 6136 */                         _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                         
/* 6138 */                         _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                         
/* 6140 */                         _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 6141 */                         int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 6142 */                         if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 6143 */                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 6144 */                             out = _jspx_page_context.pushBody();
/* 6145 */                             _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 6146 */                             _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 6149 */                             out.write(32);
/* 6150 */                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 6151 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 6154 */                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 6155 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 6158 */                         if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 6159 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                         }
/*      */                         
/* 6162 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 6163 */                         out.write(32);
/* 6164 */                         out.write(32);
/* 6165 */                         out.write("\n    </td>\n            ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 6169 */                         out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 6170 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6171 */                         out.write(32);
/* 6172 */                         out.write(62);
/* 6173 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6174 */                         out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                       }
/* 6176 */                       out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 6177 */                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                       {
/* 6179 */                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6180 */                         out.print(hostid);
/* 6181 */                         out.write("&attributeid=");
/* 6182 */                         out.print(diskid);
/* 6183 */                         out.write("&period=-7')\" class='bodytextbold'>");
/* 6184 */                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 6185 */                         out.write(45);
/* 6186 */                         out.print(diskvalue);
/* 6187 */                         out.write("</a> %\n     ");
/*      */                       }
/* 6189 */                       out.write("\n  </td>\n  </tr>\n</table>");
/* 6190 */                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 6191 */                       out.write("</td></tr></table>\n\n");
/*      */                     } else {
/* 6193 */                       out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 6194 */                       out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 6195 */                       out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 6196 */                       out.print(systeminfo.get("host_resid"));
/* 6197 */                       out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 6198 */                       out.print(hostname);
/* 6199 */                       out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 6200 */                       if (cpuvalue != null)
/*      */                       {
/*      */ 
/* 6203 */                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 6204 */                         out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                         
/* 6206 */                         DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 6207 */                         _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 6208 */                         _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                         
/* 6210 */                         _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                         
/* 6212 */                         _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                         
/* 6214 */                         _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                         
/* 6216 */                         _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                         
/* 6218 */                         _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                         
/* 6220 */                         _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                         
/* 6222 */                         _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                         
/* 6224 */                         _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                         
/* 6226 */                         _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                         
/* 6228 */                         _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 6229 */                         int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 6230 */                         if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 6231 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                         }
/*      */                         
/* 6234 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 6235 */                         out.write("\n         </td>\n     ");
/*      */                       }
/*      */                       else {
/* 6238 */                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 6239 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6240 */                         out.write(39);
/* 6241 */                         out.write(32);
/* 6242 */                         out.write(62);
/* 6243 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6244 */                         out.write("\n \t\t</td>\n\t\t");
/*      */                       }
/* 6246 */                       if (memvalue != null) {
/* 6247 */                         dialGraph.setValue(Long.parseLong(memvalue));
/* 6248 */                         out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                         
/* 6250 */                         DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 6251 */                         _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 6252 */                         _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                         
/* 6254 */                         _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                         
/* 6256 */                         _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                         
/* 6258 */                         _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                         
/* 6260 */                         _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                         
/* 6262 */                         _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                         
/* 6264 */                         _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                         
/* 6266 */                         _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                         
/* 6268 */                         _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                         
/* 6270 */                         _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                         
/* 6272 */                         _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 6273 */                         int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 6274 */                         if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 6275 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                         }
/*      */                         
/* 6278 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 6279 */                         out.write("\n            </td>\n         ");
/*      */                       }
/*      */                       else {
/* 6282 */                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 6283 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6284 */                         out.write(39);
/* 6285 */                         out.write(32);
/* 6286 */                         out.write(62);
/* 6287 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6288 */                         out.write("\n \t\t</td>\n\t\t");
/*      */                       }
/* 6290 */                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 6291 */                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 6292 */                         out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                         
/* 6294 */                         DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 6295 */                         _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 6296 */                         _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                         
/* 6298 */                         _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                         
/* 6300 */                         _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                         
/* 6302 */                         _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                         
/* 6304 */                         _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                         
/* 6306 */                         _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                         
/* 6308 */                         _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                         
/* 6310 */                         _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                         
/* 6312 */                         _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                         
/* 6314 */                         _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                         
/* 6316 */                         _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 6317 */                         int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 6318 */                         if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 6319 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                         }
/*      */                         
/* 6322 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 6323 */                         out.write(32);
/* 6324 */                         out.write("\n\t          </td>\n\t  ");
/*      */                       }
/*      */                       else {
/* 6327 */                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 6328 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6329 */                         out.write(39);
/* 6330 */                         out.write(32);
/* 6331 */                         out.write(62);
/* 6332 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6333 */                         out.write("\n \t\t</td>\n\t\t");
/*      */                       }
/* 6335 */                       out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6336 */                       out.print(hostid);
/* 6337 */                       out.write("&attributeid=");
/* 6338 */                       out.print(cpuid);
/* 6339 */                       out.write("&period=-7')\" class='tooltip'>");
/* 6340 */                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 6341 */                       out.write(32);
/* 6342 */                       out.write(45);
/* 6343 */                       out.write(32);
/* 6344 */                       if (cpuvalue != null) {
/* 6345 */                         out.print(cpuvalue);
/*      */                       }
/* 6347 */                       out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6348 */                       out.print(hostid);
/* 6349 */                       out.write("&attributeid=");
/* 6350 */                       out.print(memid);
/* 6351 */                       out.write("&period=-7')\" class='tooltip'>");
/* 6352 */                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 6353 */                       out.write(45);
/* 6354 */                       if (memvalue != null) {
/* 6355 */                         out.print(memvalue);
/*      */                       }
/* 6357 */                       out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6358 */                       out.print(hostid);
/* 6359 */                       out.write("&attributeid=");
/* 6360 */                       out.print(diskid);
/* 6361 */                       out.write("&period=-7')\" class='tooltip'>");
/* 6362 */                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 6363 */                       out.write(45);
/* 6364 */                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 6365 */                         out.print(diskvalue);
/*      */                       }
/* 6367 */                       out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                     }
/* 6369 */                     out.write(10);
/* 6370 */                     out.write(10);
/*      */                   }
/*      */                   
/*      */                 }
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/* 6377 */                 e.printStackTrace();
/*      */               }
/* 6379 */               out.write(10);
/* 6380 */               out.write(10);
/* 6381 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 6382 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 6385 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 6386 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 6389 */           if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 6390 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */           }
/*      */           
/* 6393 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 6394 */           out.write(10);
/* 6395 */           out.write(32);
/* 6396 */           if (_jspx_meth_tiles_005fput_005f6(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 6398 */           out.write(10);
/* 6399 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 6400 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6404 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 6405 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 6408 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 6409 */         out.write("\n\n\n\n");
/*      */       }
/* 6411 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 6412 */         out = _jspx_out;
/* 6413 */         if ((out != null) && (out.getBufferSize() != 0))
/* 6414 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 6415 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 6418 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6424 */     PageContext pageContext = _jspx_page_context;
/* 6425 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6427 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 6428 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 6429 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 6431 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 6432 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 6434 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 6435 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 6437 */           out.write(10);
/* 6438 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 6439 */             return true;
/* 6440 */           out.write(10);
/* 6441 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 6442 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6446 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 6447 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6450 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 6451 */         out = _jspx_page_context.popBody(); }
/* 6452 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6454 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 6455 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 6457 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 6462 */     PageContext pageContext = _jspx_page_context;
/* 6463 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6465 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 6466 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 6467 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 6469 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 6471 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 6472 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 6473 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 6474 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6475 */       return true;
/*      */     }
/* 6477 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6478 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6483 */     PageContext pageContext = _jspx_page_context;
/* 6484 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6486 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6487 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 6488 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6490 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 6491 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 6492 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 6494 */         out.write(10);
/* 6495 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 6496 */           return true;
/* 6497 */         out.write(10);
/* 6498 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 6499 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6503 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 6504 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6505 */       return true;
/*      */     }
/* 6507 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6508 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6513 */     PageContext pageContext = _jspx_page_context;
/* 6514 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6516 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6517 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 6518 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6520 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 6522 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=0");
/* 6523 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 6524 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 6525 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6526 */       return true;
/*      */     }
/* 6528 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6529 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6534 */     PageContext pageContext = _jspx_page_context;
/* 6535 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6537 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6538 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 6539 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6541 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 6542 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 6543 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 6545 */         out.write(10);
/* 6546 */         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 6547 */           return true;
/* 6548 */         out.write(10);
/* 6549 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 6550 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6554 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 6555 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6556 */       return true;
/*      */     }
/* 6558 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6564 */     PageContext pageContext = _jspx_page_context;
/* 6565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6567 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6568 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 6569 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6571 */     _jspx_th_tiles_005fput_005f3.setName("Header");
/*      */     
/* 6573 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/header.jsp?tabtoselect=1");
/* 6574 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 6575 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 6576 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6577 */       return true;
/*      */     }
/* 6579 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6585 */     PageContext pageContext = _jspx_page_context;
/* 6586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6588 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6589 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 6590 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6592 */     _jspx_th_tiles_005fput_005f4.setName("ServerLeftArea");
/*      */     
/* 6594 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/WlogicLeftArea.jsp");
/* 6595 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 6596 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 6597 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6598 */       return true;
/*      */     }
/* 6600 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6601 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6606 */     PageContext pageContext = _jspx_page_context;
/* 6607 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6609 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6610 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 6611 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6613 */     _jspx_th_c_005fset_005f0.setVar("redirect");
/*      */     
/* 6615 */     _jspx_th_c_005fset_005f0.setScope("request");
/* 6616 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 6617 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 6618 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 6619 */         out = _jspx_page_context.pushBody();
/* 6620 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 6621 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6624 */         out.write("/showresource.do?method=showResourceForResourceID&resourceid=");
/* 6625 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 6626 */           return true;
/* 6627 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 6628 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6631 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 6632 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6635 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 6636 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 6637 */       return true;
/*      */     }
/* 6639 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 6640 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6645 */     PageContext pageContext = _jspx_page_context;
/* 6646 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6648 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6649 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 6650 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 6652 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 6653 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 6654 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 6655 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6656 */       return true;
/*      */     }
/* 6658 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6659 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6664 */     PageContext pageContext = _jspx_page_context;
/* 6665 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6667 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6668 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6669 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6671 */     _jspx_th_c_005fout_005f1.setValue("${monitorname}");
/* 6672 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6673 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6674 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6675 */       return true;
/*      */     }
/* 6677 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6678 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6683 */     PageContext pageContext = _jspx_page_context;
/* 6684 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6686 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6687 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6688 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6690 */     _jspx_th_c_005fout_005f2.setValue("${monitorname}");
/* 6691 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6692 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6693 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6694 */       return true;
/*      */     }
/* 6696 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6697 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6702 */     PageContext pageContext = _jspx_page_context;
/* 6703 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6705 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6706 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6707 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6709 */     _jspx_th_c_005fout_005f3.setValue("${monitorname}");
/* 6710 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6711 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6712 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6713 */       return true;
/*      */     }
/* 6715 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6716 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6721 */     PageContext pageContext = _jspx_page_context;
/* 6722 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6724 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6725 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 6726 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6728 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.editreconfig}");
/* 6729 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 6730 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 6732 */         out.write("\n<div id=\"Reconfigure\"  style=\"DISPLAY: block\">\n");
/* 6733 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 6734 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6738 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 6739 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6740 */       return true;
/*      */     }
/* 6742 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6743 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6748 */     PageContext pageContext = _jspx_page_context;
/* 6749 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6751 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6752 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 6753 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6755 */     _jspx_th_c_005fif_005f5.setTest("${empty param.editreconfig}");
/* 6756 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 6757 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 6759 */         out.write("\n<div id=\"Reconfigure\"  style=\"DISPLAY: none\">\n");
/* 6760 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 6761 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6765 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 6766 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6767 */       return true;
/*      */     }
/* 6769 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6775 */     PageContext pageContext = _jspx_page_context;
/* 6776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6778 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6779 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6780 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6782 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 6783 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6784 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6785 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6786 */       return true;
/*      */     }
/* 6788 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6794 */     PageContext pageContext = _jspx_page_context;
/* 6795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6797 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6798 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6799 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6801 */     _jspx_th_c_005fout_005f5.setValue("${param.resourcename}");
/* 6802 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6803 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6804 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6805 */       return true;
/*      */     }
/* 6807 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6808 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6813 */     PageContext pageContext = _jspx_page_context;
/* 6814 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6816 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6817 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6818 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6820 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 6821 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6822 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6823 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6824 */       return true;
/*      */     }
/* 6826 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6827 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6832 */     PageContext pageContext = _jspx_page_context;
/* 6833 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6835 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6836 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 6837 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6839 */     _jspx_th_c_005fout_005f7.setValue("${param.resourcename}");
/* 6840 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 6841 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 6842 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6843 */       return true;
/*      */     }
/* 6845 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6851 */     PageContext pageContext = _jspx_page_context;
/* 6852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6854 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6855 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6856 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6858 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 6859 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6860 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6861 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6862 */       return true;
/*      */     }
/* 6864 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6870 */     PageContext pageContext = _jspx_page_context;
/* 6871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6873 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6874 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6875 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6877 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 6878 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6879 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6880 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6881 */       return true;
/*      */     }
/* 6883 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6884 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6889 */     PageContext pageContext = _jspx_page_context;
/* 6890 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6892 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6893 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6894 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6896 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 6897 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6898 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6899 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6900 */       return true;
/*      */     }
/* 6902 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6903 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6908 */     PageContext pageContext = _jspx_page_context;
/* 6909 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6911 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6912 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6913 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6915 */     _jspx_th_c_005fout_005f11.setValue("${param.resourcename}");
/* 6916 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6917 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6918 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6919 */       return true;
/*      */     }
/* 6921 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6922 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6927 */     PageContext pageContext = _jspx_page_context;
/* 6928 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6930 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6931 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6932 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6934 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 6935 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6936 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6937 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6938 */       return true;
/*      */     }
/* 6940 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6941 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6946 */     PageContext pageContext = _jspx_page_context;
/* 6947 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6949 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6950 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6951 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6953 */     _jspx_th_c_005fout_005f13.setValue("${param.resourcename}");
/* 6954 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6955 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6956 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6957 */       return true;
/*      */     }
/* 6959 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6960 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6965 */     PageContext pageContext = _jspx_page_context;
/* 6966 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6968 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6969 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6970 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6972 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 6973 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6974 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6975 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6976 */       return true;
/*      */     }
/* 6978 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6979 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6984 */     PageContext pageContext = _jspx_page_context;
/* 6985 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6987 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6988 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6989 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6991 */     _jspx_th_c_005fout_005f15.setValue("${param.resourcename}");
/* 6992 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6993 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6994 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6995 */       return true;
/*      */     }
/* 6997 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7003 */     PageContext pageContext = _jspx_page_context;
/* 7004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7006 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7007 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 7008 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7010 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 7011 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 7012 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 7013 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 7014 */       return true;
/*      */     }
/* 7016 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 7017 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7022 */     PageContext pageContext = _jspx_page_context;
/* 7023 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7025 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7026 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 7027 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7029 */     _jspx_th_c_005fout_005f17.setValue("${param.resourcename}");
/* 7030 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 7031 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 7032 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 7033 */       return true;
/*      */     }
/* 7035 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 7036 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7041 */     PageContext pageContext = _jspx_page_context;
/* 7042 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7044 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7045 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 7046 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 7047 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 7048 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 7049 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 7050 */         out = _jspx_page_context.pushBody();
/* 7051 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 7052 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7055 */         out.write("table.heading.attribute");
/* 7056 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 7057 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7060 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 7061 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7064 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 7065 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7066 */       return true;
/*      */     }
/* 7068 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7074 */     PageContext pageContext = _jspx_page_context;
/* 7075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7077 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7078 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 7079 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 7080 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 7081 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 7082 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 7083 */         out = _jspx_page_context.pushBody();
/* 7084 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 7085 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7088 */         out.write("table.heading.value");
/* 7089 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 7090 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7093 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 7094 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7097 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 7098 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 7099 */       return true;
/*      */     }
/* 7101 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 7102 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7107 */     PageContext pageContext = _jspx_page_context;
/* 7108 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7110 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7111 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 7112 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 7113 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 7114 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 7115 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 7116 */         out = _jspx_page_context.pushBody();
/* 7117 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 7118 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7121 */         out.write("table.heading.status");
/* 7122 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 7123 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7126 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 7127 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7130 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 7131 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7132 */       return true;
/*      */     }
/* 7134 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7135 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7140 */     PageContext pageContext = _jspx_page_context;
/* 7141 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7143 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7144 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 7145 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7147 */     _jspx_th_c_005fif_005f8.setTest("${responsetime =='-1'}");
/* 7148 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 7149 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 7151 */         out.write("  0\n  ");
/* 7152 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 7153 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7157 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 7158 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 7159 */       return true;
/*      */     }
/* 7161 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 7162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7167 */     PageContext pageContext = _jspx_page_context;
/* 7168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7170 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 7171 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 7172 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 7174 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${responsetime}");
/* 7175 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 7176 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 7177 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 7178 */       return true;
/*      */     }
/* 7180 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 7181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7186 */     PageContext pageContext = _jspx_page_context;
/* 7187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7189 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7190 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 7191 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7193 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 7194 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 7195 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 7196 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 7197 */       return true;
/*      */     }
/* 7199 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 7200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7205 */     PageContext pageContext = _jspx_page_context;
/* 7206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7208 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7209 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 7210 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7212 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 7213 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 7214 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 7215 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 7216 */       return true;
/*      */     }
/* 7218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 7219 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7224 */     PageContext pageContext = _jspx_page_context;
/* 7225 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7227 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7228 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 7229 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7231 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 7232 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 7233 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 7234 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 7235 */       return true;
/*      */     }
/* 7237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 7238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7243 */     PageContext pageContext = _jspx_page_context;
/* 7244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7246 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7247 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 7248 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7250 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 7251 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 7252 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 7253 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 7254 */       return true;
/*      */     }
/* 7256 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 7257 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7262 */     PageContext pageContext = _jspx_page_context;
/* 7263 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7265 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7266 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 7267 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7269 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 7270 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 7271 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 7272 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 7273 */       return true;
/*      */     }
/* 7275 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 7276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7281 */     PageContext pageContext = _jspx_page_context;
/* 7282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7284 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7285 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 7286 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7288 */     _jspx_th_c_005fout_005f23.setValue("${param.resourcename}");
/* 7289 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 7290 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 7291 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 7292 */       return true;
/*      */     }
/* 7294 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 7295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7300 */     PageContext pageContext = _jspx_page_context;
/* 7301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7303 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7304 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 7305 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7307 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 7308 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 7309 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 7310 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 7311 */       return true;
/*      */     }
/* 7313 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 7314 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7319 */     PageContext pageContext = _jspx_page_context;
/* 7320 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7322 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7323 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 7324 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7326 */     _jspx_th_c_005fout_005f25.setValue("${param.resourcename}");
/* 7327 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 7328 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 7329 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 7330 */       return true;
/*      */     }
/* 7332 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 7333 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7338 */     PageContext pageContext = _jspx_page_context;
/* 7339 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7341 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7342 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 7343 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7345 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 7346 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 7347 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 7348 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7349 */       return true;
/*      */     }
/* 7351 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7352 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7357 */     PageContext pageContext = _jspx_page_context;
/* 7358 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7360 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7361 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 7362 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7364 */     _jspx_th_c_005fout_005f27.setValue("${param.resourcename}");
/* 7365 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 7366 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 7367 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7368 */       return true;
/*      */     }
/* 7370 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7371 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7376 */     PageContext pageContext = _jspx_page_context;
/* 7377 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7379 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7380 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 7381 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7383 */     _jspx_th_c_005fout_005f28.setValue("${param.resourceid}");
/* 7384 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 7385 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 7386 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7387 */       return true;
/*      */     }
/* 7389 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7390 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7395 */     PageContext pageContext = _jspx_page_context;
/* 7396 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7398 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7399 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 7400 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7402 */     _jspx_th_c_005fout_005f29.setValue("${param.resourcename}");
/* 7403 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 7404 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 7405 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7406 */       return true;
/*      */     }
/* 7408 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7414 */     PageContext pageContext = _jspx_page_context;
/* 7415 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7417 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7418 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 7419 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 7420 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 7421 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 7422 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 7423 */         out = _jspx_page_context.pushBody();
/* 7424 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 7425 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7428 */         out.write("table.heading.attribute");
/* 7429 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 7430 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7433 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 7434 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7437 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 7438 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7439 */       return true;
/*      */     }
/* 7441 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7447 */     PageContext pageContext = _jspx_page_context;
/* 7448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7450 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7451 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 7452 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 7453 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 7454 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 7455 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 7456 */         out = _jspx_page_context.pushBody();
/* 7457 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 7458 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7461 */         out.write("table.heading.value");
/* 7462 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 7463 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7466 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 7467 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7470 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 7471 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7472 */       return true;
/*      */     }
/* 7474 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7475 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7480 */     PageContext pageContext = _jspx_page_context;
/* 7481 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7483 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7484 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 7485 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 7486 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 7487 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 7488 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 7489 */         out = _jspx_page_context.pushBody();
/* 7490 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 7491 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7494 */         out.write("table.heading.status");
/* 7495 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 7496 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7499 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 7500 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7503 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 7504 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7505 */       return true;
/*      */     }
/* 7507 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7508 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7513 */     PageContext pageContext = _jspx_page_context;
/* 7514 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7516 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7517 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 7518 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7520 */     _jspx_th_c_005fif_005f11.setTest("${responsetime =='-1'}");
/* 7521 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 7522 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 7524 */         out.write("  0\n  ");
/* 7525 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 7526 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7530 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 7531 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 7532 */       return true;
/*      */     }
/* 7534 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 7535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f1(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7540 */     PageContext pageContext = _jspx_page_context;
/* 7541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7543 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f1 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 7544 */     _jspx_th_fmt_005fformatNumber_005f1.setPageContext(_jspx_page_context);
/* 7545 */     _jspx_th_fmt_005fformatNumber_005f1.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 7547 */     _jspx_th_fmt_005fformatNumber_005f1.setValue("${responsetime}");
/* 7548 */     int _jspx_eval_fmt_005fformatNumber_005f1 = _jspx_th_fmt_005fformatNumber_005f1.doStartTag();
/* 7549 */     if (_jspx_th_fmt_005fformatNumber_005f1.doEndTag() == 5) {
/* 7550 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 7551 */       return true;
/*      */     }
/* 7553 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 7554 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7559 */     PageContext pageContext = _jspx_page_context;
/* 7560 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7562 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7563 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 7564 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7566 */     _jspx_th_c_005fout_005f30.setValue("${param.resourceid}");
/* 7567 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 7568 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 7569 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7570 */       return true;
/*      */     }
/* 7572 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7573 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7578 */     PageContext pageContext = _jspx_page_context;
/* 7579 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7581 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7582 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 7583 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7585 */     _jspx_th_c_005fout_005f31.setValue("${param.resourceid}");
/* 7586 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 7587 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 7588 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7589 */       return true;
/*      */     }
/* 7591 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7597 */     PageContext pageContext = _jspx_page_context;
/* 7598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7600 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7601 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 7602 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7604 */     _jspx_th_c_005fout_005f32.setValue("${param.resourceid}");
/* 7605 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 7606 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 7607 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7608 */       return true;
/*      */     }
/* 7610 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7611 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7616 */     PageContext pageContext = _jspx_page_context;
/* 7617 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7619 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7620 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 7621 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7623 */     _jspx_th_c_005fout_005f33.setValue("${param.resourceid}");
/* 7624 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 7625 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 7626 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7627 */       return true;
/*      */     }
/* 7629 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7635 */     PageContext pageContext = _jspx_page_context;
/* 7636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7638 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7639 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 7640 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7642 */     _jspx_th_c_005fout_005f34.setValue("${param.resourceid}");
/* 7643 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 7644 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 7645 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7646 */       return true;
/*      */     }
/* 7648 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7654 */     PageContext pageContext = _jspx_page_context;
/* 7655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7657 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7658 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 7659 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7661 */     _jspx_th_c_005fout_005f35.setValue("${param.resourcename}");
/* 7662 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 7663 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 7664 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7665 */       return true;
/*      */     }
/* 7667 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7668 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7673 */     PageContext pageContext = _jspx_page_context;
/* 7674 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7676 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7677 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 7678 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7680 */     _jspx_th_c_005fout_005f36.setValue("${param.resourceid}");
/* 7681 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 7682 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 7683 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 7684 */       return true;
/*      */     }
/* 7686 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 7687 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7692 */     PageContext pageContext = _jspx_page_context;
/* 7693 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7695 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7696 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 7697 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7699 */     _jspx_th_c_005fout_005f37.setValue("${param.resourcename}");
/* 7700 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 7701 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 7702 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7703 */       return true;
/*      */     }
/* 7705 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7706 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7711 */     PageContext pageContext = _jspx_page_context;
/* 7712 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7714 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7715 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 7716 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7718 */     _jspx_th_c_005fout_005f38.setValue("${param.resourceid}");
/* 7719 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 7720 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 7721 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7722 */       return true;
/*      */     }
/* 7724 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7730 */     PageContext pageContext = _jspx_page_context;
/* 7731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7733 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7734 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 7735 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7737 */     _jspx_th_c_005fout_005f39.setValue("${param.resourcename}");
/* 7738 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 7739 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 7740 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7741 */       return true;
/*      */     }
/* 7743 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7749 */     PageContext pageContext = _jspx_page_context;
/* 7750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7752 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7753 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 7754 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7756 */     _jspx_th_c_005fout_005f40.setValue("${param.resourceid}");
/* 7757 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 7758 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 7759 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7760 */       return true;
/*      */     }
/* 7762 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7763 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7768 */     PageContext pageContext = _jspx_page_context;
/* 7769 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7771 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7772 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 7773 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7775 */     _jspx_th_c_005fout_005f41.setValue("${param.resourcename}");
/* 7776 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 7777 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 7778 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 7779 */       return true;
/*      */     }
/* 7781 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 7782 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7787 */     PageContext pageContext = _jspx_page_context;
/* 7788 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7790 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7791 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 7792 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7794 */     _jspx_th_c_005fout_005f42.setValue("${param.resourceid}");
/* 7795 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 7796 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 7797 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 7798 */       return true;
/*      */     }
/* 7800 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 7801 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7806 */     PageContext pageContext = _jspx_page_context;
/* 7807 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7809 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7810 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 7811 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7813 */     _jspx_th_c_005fout_005f43.setValue("${param.resourcename}");
/* 7814 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 7815 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 7816 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 7817 */       return true;
/*      */     }
/* 7819 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 7820 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7825 */     PageContext pageContext = _jspx_page_context;
/* 7826 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7828 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7829 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 7830 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7832 */     _jspx_th_c_005fout_005f44.setValue("${param.resourceid}");
/* 7833 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 7834 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 7835 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 7836 */       return true;
/*      */     }
/* 7838 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 7839 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7844 */     PageContext pageContext = _jspx_page_context;
/* 7845 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7847 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7848 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 7849 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7851 */     _jspx_th_c_005fout_005f45.setValue("${param.resourcename}");
/* 7852 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 7853 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 7854 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 7855 */       return true;
/*      */     }
/* 7857 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 7858 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7863 */     PageContext pageContext = _jspx_page_context;
/* 7864 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7866 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7867 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 7868 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7870 */     _jspx_th_c_005fout_005f46.setValue("${param.resourceid}");
/* 7871 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 7872 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 7873 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 7874 */       return true;
/*      */     }
/* 7876 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 7877 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7882 */     PageContext pageContext = _jspx_page_context;
/* 7883 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7885 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7886 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 7887 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7889 */     _jspx_th_c_005fout_005f47.setValue("${param.resourcename}");
/* 7890 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 7891 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 7892 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 7893 */       return true;
/*      */     }
/* 7895 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 7896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7901 */     PageContext pageContext = _jspx_page_context;
/* 7902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7904 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7905 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 7906 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7908 */     _jspx_th_c_005fout_005f48.setValue("${param.resourceid}");
/* 7909 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 7910 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 7911 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 7912 */       return true;
/*      */     }
/* 7914 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 7915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7920 */     PageContext pageContext = _jspx_page_context;
/* 7921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7923 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7924 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 7925 */     _jspx_th_c_005fout_005f49.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7927 */     _jspx_th_c_005fout_005f49.setValue("${param.resourcename}");
/* 7928 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 7929 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 7930 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 7931 */       return true;
/*      */     }
/* 7933 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 7934 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7939 */     PageContext pageContext = _jspx_page_context;
/* 7940 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7942 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7943 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 7944 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 7945 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 7946 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 7947 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 7948 */         out = _jspx_page_context.pushBody();
/* 7949 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 7950 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7953 */         out.write("table.heading.attribute");
/* 7954 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 7955 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7958 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 7959 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7962 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 7963 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7964 */       return true;
/*      */     }
/* 7966 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7972 */     PageContext pageContext = _jspx_page_context;
/* 7973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7975 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7976 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 7977 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 7978 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 7979 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 7980 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 7981 */         out = _jspx_page_context.pushBody();
/* 7982 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 7983 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7986 */         out.write("table.heading.value");
/* 7987 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 7988 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7991 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 7992 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7995 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 7996 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 7997 */       return true;
/*      */     }
/* 7999 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 8000 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8005 */     PageContext pageContext = _jspx_page_context;
/* 8006 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8008 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8009 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 8010 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 8011 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 8012 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 8013 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 8014 */         out = _jspx_page_context.pushBody();
/* 8015 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 8016 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8019 */         out.write("table.heading.status");
/* 8020 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 8021 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8024 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 8025 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8028 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 8029 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 8030 */       return true;
/*      */     }
/* 8032 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 8033 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8038 */     PageContext pageContext = _jspx_page_context;
/* 8039 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8041 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 8042 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 8043 */     _jspx_th_c_005fout_005f50.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8045 */     _jspx_th_c_005fout_005f50.setValue("${heapsize}");
/* 8046 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 8047 */     if (_jspx_eval_c_005fout_005f50 != 0) {
/* 8048 */       if (_jspx_eval_c_005fout_005f50 != 1) {
/* 8049 */         out = _jspx_page_context.pushBody();
/* 8050 */         _jspx_th_c_005fout_005f50.setBodyContent((BodyContent)out);
/* 8051 */         _jspx_th_c_005fout_005f50.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8054 */         out.write(45);
/* 8055 */         int evalDoAfterBody = _jspx_th_c_005fout_005f50.doAfterBody();
/* 8056 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8059 */       if (_jspx_eval_c_005fout_005f50 != 1) {
/* 8060 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8063 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 8064 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f50);
/* 8065 */       return true;
/*      */     }
/* 8067 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f50);
/* 8068 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8073 */     PageContext pageContext = _jspx_page_context;
/* 8074 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8076 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8077 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 8078 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8080 */     _jspx_th_c_005fout_005f51.setValue("${param.resourceid}");
/* 8081 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 8082 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 8083 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 8084 */       return true;
/*      */     }
/* 8086 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 8087 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8092 */     PageContext pageContext = _jspx_page_context;
/* 8093 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8095 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8096 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 8097 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8099 */     _jspx_th_c_005fout_005f52.setValue("${param.resourceid}");
/* 8100 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 8101 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 8102 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 8103 */       return true;
/*      */     }
/* 8105 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 8106 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8111 */     PageContext pageContext = _jspx_page_context;
/* 8112 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8114 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8115 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 8116 */     _jspx_th_c_005fout_005f53.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8118 */     _jspx_th_c_005fout_005f53.setValue("${param.resourceid}");
/* 8119 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 8120 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 8121 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 8122 */       return true;
/*      */     }
/* 8124 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 8125 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f54(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8130 */     PageContext pageContext = _jspx_page_context;
/* 8131 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8133 */     OutTag _jspx_th_c_005fout_005f54 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8134 */     _jspx_th_c_005fout_005f54.setPageContext(_jspx_page_context);
/* 8135 */     _jspx_th_c_005fout_005f54.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8137 */     _jspx_th_c_005fout_005f54.setValue("${param.resourceid}");
/* 8138 */     int _jspx_eval_c_005fout_005f54 = _jspx_th_c_005fout_005f54.doStartTag();
/* 8139 */     if (_jspx_th_c_005fout_005f54.doEndTag() == 5) {
/* 8140 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 8141 */       return true;
/*      */     }
/* 8143 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 8144 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8149 */     PageContext pageContext = _jspx_page_context;
/* 8150 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8152 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8153 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 8154 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 8155 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 8156 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 8157 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 8158 */         out = _jspx_page_context.pushBody();
/* 8159 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 8160 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8163 */         out.write("table.heading.attribute");
/* 8164 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 8165 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8168 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 8169 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8172 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 8173 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 8174 */       return true;
/*      */     }
/* 8176 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 8177 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8182 */     PageContext pageContext = _jspx_page_context;
/* 8183 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8185 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8186 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 8187 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 8188 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 8189 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 8190 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 8191 */         out = _jspx_page_context.pushBody();
/* 8192 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 8193 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8196 */         out.write("table.heading.value");
/* 8197 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 8198 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8201 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 8202 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8205 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 8206 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 8207 */       return true;
/*      */     }
/* 8209 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 8210 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8215 */     PageContext pageContext = _jspx_page_context;
/* 8216 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8218 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8219 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 8220 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 8221 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 8222 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 8223 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 8224 */         out = _jspx_page_context.pushBody();
/* 8225 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 8226 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8229 */         out.write("table.heading.status");
/* 8230 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 8231 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8234 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 8235 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8238 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 8239 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 8240 */       return true;
/*      */     }
/* 8242 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 8243 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f4(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8248 */     PageContext pageContext = _jspx_page_context;
/* 8249 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8251 */     FormatTag _jspx_th_am_005fFormat_005f4 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 8252 */     _jspx_th_am_005fFormat_005f4.setPageContext(_jspx_page_context);
/* 8253 */     _jspx_th_am_005fFormat_005f4.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8255 */     _jspx_th_am_005fFormat_005f4.setType("Number");
/* 8256 */     int _jspx_eval_am_005fFormat_005f4 = _jspx_th_am_005fFormat_005f4.doStartTag();
/* 8257 */     if (_jspx_eval_am_005fFormat_005f4 != 0) {
/* 8258 */       if (_jspx_eval_am_005fFormat_005f4 != 1) {
/* 8259 */         out = _jspx_page_context.pushBody();
/* 8260 */         _jspx_th_am_005fFormat_005f4.setBodyContent((BodyContent)out);
/* 8261 */         _jspx_th_am_005fFormat_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8264 */         if (_jspx_meth_c_005fout_005f55(_jspx_th_am_005fFormat_005f4, _jspx_page_context))
/* 8265 */           return true;
/* 8266 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f4.doAfterBody();
/* 8267 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8270 */       if (_jspx_eval_am_005fFormat_005f4 != 1) {
/* 8271 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8274 */     if (_jspx_th_am_005fFormat_005f4.doEndTag() == 5) {
/* 8275 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f4);
/* 8276 */       return true;
/*      */     }
/* 8278 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f4);
/* 8279 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f55(JspTag _jspx_th_am_005fFormat_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8284 */     PageContext pageContext = _jspx_page_context;
/* 8285 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8287 */     OutTag _jspx_th_c_005fout_005f55 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 8288 */     _jspx_th_c_005fout_005f55.setPageContext(_jspx_page_context);
/* 8289 */     _jspx_th_c_005fout_005f55.setParent((Tag)_jspx_th_am_005fFormat_005f4);
/*      */     
/* 8291 */     _jspx_th_c_005fout_005f55.setValue("${restime.min}");
/* 8292 */     int _jspx_eval_c_005fout_005f55 = _jspx_th_c_005fout_005f55.doStartTag();
/* 8293 */     if (_jspx_eval_c_005fout_005f55 != 0) {
/* 8294 */       if (_jspx_eval_c_005fout_005f55 != 1) {
/* 8295 */         out = _jspx_page_context.pushBody();
/* 8296 */         _jspx_th_c_005fout_005f55.setBodyContent((BodyContent)out);
/* 8297 */         _jspx_th_c_005fout_005f55.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8300 */         out.write(45);
/* 8301 */         int evalDoAfterBody = _jspx_th_c_005fout_005f55.doAfterBody();
/* 8302 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8305 */       if (_jspx_eval_c_005fout_005f55 != 1) {
/* 8306 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8309 */     if (_jspx_th_c_005fout_005f55.doEndTag() == 5) {
/* 8310 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f55);
/* 8311 */       return true;
/*      */     }
/* 8313 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f55);
/* 8314 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f5(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8319 */     PageContext pageContext = _jspx_page_context;
/* 8320 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8322 */     FormatTag _jspx_th_am_005fFormat_005f5 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 8323 */     _jspx_th_am_005fFormat_005f5.setPageContext(_jspx_page_context);
/* 8324 */     _jspx_th_am_005fFormat_005f5.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8326 */     _jspx_th_am_005fFormat_005f5.setType("Number");
/* 8327 */     int _jspx_eval_am_005fFormat_005f5 = _jspx_th_am_005fFormat_005f5.doStartTag();
/* 8328 */     if (_jspx_eval_am_005fFormat_005f5 != 0) {
/* 8329 */       if (_jspx_eval_am_005fFormat_005f5 != 1) {
/* 8330 */         out = _jspx_page_context.pushBody();
/* 8331 */         _jspx_th_am_005fFormat_005f5.setBodyContent((BodyContent)out);
/* 8332 */         _jspx_th_am_005fFormat_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8335 */         if (_jspx_meth_c_005fout_005f56(_jspx_th_am_005fFormat_005f5, _jspx_page_context))
/* 8336 */           return true;
/* 8337 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f5.doAfterBody();
/* 8338 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8341 */       if (_jspx_eval_am_005fFormat_005f5 != 1) {
/* 8342 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8345 */     if (_jspx_th_am_005fFormat_005f5.doEndTag() == 5) {
/* 8346 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f5);
/* 8347 */       return true;
/*      */     }
/* 8349 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f5);
/* 8350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f56(JspTag _jspx_th_am_005fFormat_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8355 */     PageContext pageContext = _jspx_page_context;
/* 8356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8358 */     OutTag _jspx_th_c_005fout_005f56 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 8359 */     _jspx_th_c_005fout_005f56.setPageContext(_jspx_page_context);
/* 8360 */     _jspx_th_c_005fout_005f56.setParent((Tag)_jspx_th_am_005fFormat_005f5);
/*      */     
/* 8362 */     _jspx_th_c_005fout_005f56.setValue("${restime.max}");
/* 8363 */     int _jspx_eval_c_005fout_005f56 = _jspx_th_c_005fout_005f56.doStartTag();
/* 8364 */     if (_jspx_eval_c_005fout_005f56 != 0) {
/* 8365 */       if (_jspx_eval_c_005fout_005f56 != 1) {
/* 8366 */         out = _jspx_page_context.pushBody();
/* 8367 */         _jspx_th_c_005fout_005f56.setBodyContent((BodyContent)out);
/* 8368 */         _jspx_th_c_005fout_005f56.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8371 */         out.write(45);
/* 8372 */         int evalDoAfterBody = _jspx_th_c_005fout_005f56.doAfterBody();
/* 8373 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8376 */       if (_jspx_eval_c_005fout_005f56 != 1) {
/* 8377 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8380 */     if (_jspx_th_c_005fout_005f56.doEndTag() == 5) {
/* 8381 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f56);
/* 8382 */       return true;
/*      */     }
/* 8384 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f56);
/* 8385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f6(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8390 */     PageContext pageContext = _jspx_page_context;
/* 8391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8393 */     FormatTag _jspx_th_am_005fFormat_005f6 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 8394 */     _jspx_th_am_005fFormat_005f6.setPageContext(_jspx_page_context);
/* 8395 */     _jspx_th_am_005fFormat_005f6.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8397 */     _jspx_th_am_005fFormat_005f6.setType("Number");
/* 8398 */     int _jspx_eval_am_005fFormat_005f6 = _jspx_th_am_005fFormat_005f6.doStartTag();
/* 8399 */     if (_jspx_eval_am_005fFormat_005f6 != 0) {
/* 8400 */       if (_jspx_eval_am_005fFormat_005f6 != 1) {
/* 8401 */         out = _jspx_page_context.pushBody();
/* 8402 */         _jspx_th_am_005fFormat_005f6.setBodyContent((BodyContent)out);
/* 8403 */         _jspx_th_am_005fFormat_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8406 */         if (_jspx_meth_c_005fout_005f57(_jspx_th_am_005fFormat_005f6, _jspx_page_context))
/* 8407 */           return true;
/* 8408 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f6.doAfterBody();
/* 8409 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8412 */       if (_jspx_eval_am_005fFormat_005f6 != 1) {
/* 8413 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8416 */     if (_jspx_th_am_005fFormat_005f6.doEndTag() == 5) {
/* 8417 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f6);
/* 8418 */       return true;
/*      */     }
/* 8420 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f6);
/* 8421 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f57(JspTag _jspx_th_am_005fFormat_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8426 */     PageContext pageContext = _jspx_page_context;
/* 8427 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8429 */     OutTag _jspx_th_c_005fout_005f57 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 8430 */     _jspx_th_c_005fout_005f57.setPageContext(_jspx_page_context);
/* 8431 */     _jspx_th_c_005fout_005f57.setParent((Tag)_jspx_th_am_005fFormat_005f6);
/*      */     
/* 8433 */     _jspx_th_c_005fout_005f57.setValue("${restime.avg}");
/* 8434 */     int _jspx_eval_c_005fout_005f57 = _jspx_th_c_005fout_005f57.doStartTag();
/* 8435 */     if (_jspx_eval_c_005fout_005f57 != 0) {
/* 8436 */       if (_jspx_eval_c_005fout_005f57 != 1) {
/* 8437 */         out = _jspx_page_context.pushBody();
/* 8438 */         _jspx_th_c_005fout_005f57.setBodyContent((BodyContent)out);
/* 8439 */         _jspx_th_c_005fout_005f57.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8442 */         out.write(45);
/* 8443 */         int evalDoAfterBody = _jspx_th_c_005fout_005f57.doAfterBody();
/* 8444 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8447 */       if (_jspx_eval_c_005fout_005f57 != 1) {
/* 8448 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8451 */     if (_jspx_th_c_005fout_005f57.doEndTag() == 5) {
/* 8452 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f57);
/* 8453 */       return true;
/*      */     }
/* 8455 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f57);
/* 8456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f7(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8461 */     PageContext pageContext = _jspx_page_context;
/* 8462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8464 */     FormatTag _jspx_th_am_005fFormat_005f7 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 8465 */     _jspx_th_am_005fFormat_005f7.setPageContext(_jspx_page_context);
/* 8466 */     _jspx_th_am_005fFormat_005f7.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 8468 */     _jspx_th_am_005fFormat_005f7.setType("Number");
/* 8469 */     int _jspx_eval_am_005fFormat_005f7 = _jspx_th_am_005fFormat_005f7.doStartTag();
/* 8470 */     if (_jspx_eval_am_005fFormat_005f7 != 0) {
/* 8471 */       if (_jspx_eval_am_005fFormat_005f7 != 1) {
/* 8472 */         out = _jspx_page_context.pushBody();
/* 8473 */         _jspx_th_am_005fFormat_005f7.setBodyContent((BodyContent)out);
/* 8474 */         _jspx_th_am_005fFormat_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8477 */         if (_jspx_meth_c_005fout_005f58(_jspx_th_am_005fFormat_005f7, _jspx_page_context))
/* 8478 */           return true;
/* 8479 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f7.doAfterBody();
/* 8480 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8483 */       if (_jspx_eval_am_005fFormat_005f7 != 1) {
/* 8484 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8487 */     if (_jspx_th_am_005fFormat_005f7.doEndTag() == 5) {
/* 8488 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f7);
/* 8489 */       return true;
/*      */     }
/* 8491 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f7);
/* 8492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f58(JspTag _jspx_th_am_005fFormat_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8497 */     PageContext pageContext = _jspx_page_context;
/* 8498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8500 */     OutTag _jspx_th_c_005fout_005f58 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 8501 */     _jspx_th_c_005fout_005f58.setPageContext(_jspx_page_context);
/* 8502 */     _jspx_th_c_005fout_005f58.setParent((Tag)_jspx_th_am_005fFormat_005f7);
/*      */     
/* 8504 */     _jspx_th_c_005fout_005f58.setValue("${responsetime}");
/* 8505 */     int _jspx_eval_c_005fout_005f58 = _jspx_th_c_005fout_005f58.doStartTag();
/* 8506 */     if (_jspx_eval_c_005fout_005f58 != 0) {
/* 8507 */       if (_jspx_eval_c_005fout_005f58 != 1) {
/* 8508 */         out = _jspx_page_context.pushBody();
/* 8509 */         _jspx_th_c_005fout_005f58.setBodyContent((BodyContent)out);
/* 8510 */         _jspx_th_c_005fout_005f58.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8513 */         out.write(45);
/* 8514 */         int evalDoAfterBody = _jspx_th_c_005fout_005f58.doAfterBody();
/* 8515 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8518 */       if (_jspx_eval_c_005fout_005f58 != 1) {
/* 8519 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8522 */     if (_jspx_th_c_005fout_005f58.doEndTag() == 5) {
/* 8523 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f58);
/* 8524 */       return true;
/*      */     }
/* 8526 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f58);
/* 8527 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f59(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8532 */     PageContext pageContext = _jspx_page_context;
/* 8533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8535 */     OutTag _jspx_th_c_005fout_005f59 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8536 */     _jspx_th_c_005fout_005f59.setPageContext(_jspx_page_context);
/* 8537 */     _jspx_th_c_005fout_005f59.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 8539 */     _jspx_th_c_005fout_005f59.setValue("${param.resourceid}");
/* 8540 */     int _jspx_eval_c_005fout_005f59 = _jspx_th_c_005fout_005f59.doStartTag();
/* 8541 */     if (_jspx_th_c_005fout_005f59.doEndTag() == 5) {
/* 8542 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f59);
/* 8543 */       return true;
/*      */     }
/* 8545 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f59);
/* 8546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f60(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8551 */     PageContext pageContext = _jspx_page_context;
/* 8552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8554 */     OutTag _jspx_th_c_005fout_005f60 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8555 */     _jspx_th_c_005fout_005f60.setPageContext(_jspx_page_context);
/* 8556 */     _jspx_th_c_005fout_005f60.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 8558 */     _jspx_th_c_005fout_005f60.setValue("${param.resourceid}");
/* 8559 */     int _jspx_eval_c_005fout_005f60 = _jspx_th_c_005fout_005f60.doStartTag();
/* 8560 */     if (_jspx_th_c_005fout_005f60.doEndTag() == 5) {
/* 8561 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f60);
/* 8562 */       return true;
/*      */     }
/* 8564 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f60);
/* 8565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f61(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8570 */     PageContext pageContext = _jspx_page_context;
/* 8571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8573 */     OutTag _jspx_th_c_005fout_005f61 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8574 */     _jspx_th_c_005fout_005f61.setPageContext(_jspx_page_context);
/* 8575 */     _jspx_th_c_005fout_005f61.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8577 */     _jspx_th_c_005fout_005f61.setValue("${param.resourceid}");
/* 8578 */     int _jspx_eval_c_005fout_005f61 = _jspx_th_c_005fout_005f61.doStartTag();
/* 8579 */     if (_jspx_th_c_005fout_005f61.doEndTag() == 5) {
/* 8580 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f61);
/* 8581 */       return true;
/*      */     }
/* 8583 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f61);
/* 8584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f62(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8589 */     PageContext pageContext = _jspx_page_context;
/* 8590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8592 */     OutTag _jspx_th_c_005fout_005f62 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8593 */     _jspx_th_c_005fout_005f62.setPageContext(_jspx_page_context);
/* 8594 */     _jspx_th_c_005fout_005f62.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8596 */     _jspx_th_c_005fout_005f62.setValue("${param.resourceid}");
/* 8597 */     int _jspx_eval_c_005fout_005f62 = _jspx_th_c_005fout_005f62.doStartTag();
/* 8598 */     if (_jspx_th_c_005fout_005f62.doEndTag() == 5) {
/* 8599 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f62);
/* 8600 */       return true;
/*      */     }
/* 8602 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f62);
/* 8603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f2(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8608 */     PageContext pageContext = _jspx_page_context;
/* 8609 */     JspWriter out = _jspx_page_context.getOut();
/* 8610 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8611 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8613 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8614 */     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 8615 */     _jspx_th_logic_005fnotEmpty_005f2.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8617 */     _jspx_th_logic_005fnotEmpty_005f2.setName("data");
/* 8618 */     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 8619 */     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */       for (;;) {
/* 8621 */         out.write(10);
/* 8622 */         JspRuntimeLibrary.include(request, response, "/jsp/ShowWLIBpmDetails.jsp", out, false);
/* 8623 */         out.write(10);
/* 8624 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 8625 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8629 */     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 8630 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 8631 */       return true;
/*      */     }
/* 8633 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 8634 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f3(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8639 */     PageContext pageContext = _jspx_page_context;
/* 8640 */     JspWriter out = _jspx_page_context.getOut();
/* 8641 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8642 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8644 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8645 */     _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 8646 */     _jspx_th_logic_005fnotEmpty_005f3.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8648 */     _jspx_th_logic_005fnotEmpty_005f3.setName("data");
/* 8649 */     int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 8650 */     if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */       for (;;) {
/* 8652 */         out.write(10);
/* 8653 */         JspRuntimeLibrary.include(request, response, "/jsp/ShowWLIAIDetails.jsp", out, false);
/* 8654 */         out.write(10);
/* 8655 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 8656 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8660 */     if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 8661 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 8662 */       return true;
/*      */     }
/* 8664 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 8665 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f4(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8670 */     PageContext pageContext = _jspx_page_context;
/* 8671 */     JspWriter out = _jspx_page_context.getOut();
/* 8672 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8673 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8675 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8676 */     _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 8677 */     _jspx_th_logic_005fnotEmpty_005f4.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8679 */     _jspx_th_logic_005fnotEmpty_005f4.setName("data");
/* 8680 */     int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 8681 */     if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */       for (;;) {
/* 8683 */         out.write(10);
/* 8684 */         JspRuntimeLibrary.include(request, response, "/jsp/ShowWLIMBDetails.jsp", out, false);
/* 8685 */         out.write(10);
/* 8686 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 8687 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8691 */     if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 8692 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 8693 */       return true;
/*      */     }
/* 8695 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 8696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f5(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8701 */     PageContext pageContext = _jspx_page_context;
/* 8702 */     JspWriter out = _jspx_page_context.getOut();
/* 8703 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8704 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8706 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f5 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8707 */     _jspx_th_logic_005fnotEmpty_005f5.setPageContext(_jspx_page_context);
/* 8708 */     _jspx_th_logic_005fnotEmpty_005f5.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8710 */     _jspx_th_logic_005fnotEmpty_005f5.setName("data");
/* 8711 */     int _jspx_eval_logic_005fnotEmpty_005f5 = _jspx_th_logic_005fnotEmpty_005f5.doStartTag();
/* 8712 */     if (_jspx_eval_logic_005fnotEmpty_005f5 != 0) {
/*      */       for (;;) {
/* 8714 */         out.write(10);
/* 8715 */         JspRuntimeLibrary.include(request, response, "/jsp/ShowWebApplications.jsp", out, false);
/* 8716 */         out.write(10);
/* 8717 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f5.doAfterBody();
/* 8718 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8722 */     if (_jspx_th_logic_005fnotEmpty_005f5.doEndTag() == 5) {
/* 8723 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5);
/* 8724 */       return true;
/*      */     }
/* 8726 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5);
/* 8727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f6(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8732 */     PageContext pageContext = _jspx_page_context;
/* 8733 */     JspWriter out = _jspx_page_context.getOut();
/* 8734 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8735 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8737 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f6 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8738 */     _jspx_th_logic_005fnotEmpty_005f6.setPageContext(_jspx_page_context);
/* 8739 */     _jspx_th_logic_005fnotEmpty_005f6.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8741 */     _jspx_th_logic_005fnotEmpty_005f6.setName("data");
/* 8742 */     int _jspx_eval_logic_005fnotEmpty_005f6 = _jspx_th_logic_005fnotEmpty_005f6.doStartTag();
/* 8743 */     if (_jspx_eval_logic_005fnotEmpty_005f6 != 0) {
/*      */       for (;;) {
/* 8745 */         out.write(10);
/* 8746 */         JspRuntimeLibrary.include(request, response, "/jsp/ShowThreadData.jsp", out, false);
/* 8747 */         out.write(10);
/* 8748 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f6.doAfterBody();
/* 8749 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8753 */     if (_jspx_th_logic_005fnotEmpty_005f6.doEndTag() == 5) {
/* 8754 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6);
/* 8755 */       return true;
/*      */     }
/* 8757 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6);
/* 8758 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f7(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8763 */     PageContext pageContext = _jspx_page_context;
/* 8764 */     JspWriter out = _jspx_page_context.getOut();
/* 8765 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8766 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8768 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f7 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8769 */     _jspx_th_logic_005fnotEmpty_005f7.setPageContext(_jspx_page_context);
/* 8770 */     _jspx_th_logic_005fnotEmpty_005f7.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8772 */     _jspx_th_logic_005fnotEmpty_005f7.setName("data");
/* 8773 */     int _jspx_eval_logic_005fnotEmpty_005f7 = _jspx_th_logic_005fnotEmpty_005f7.doStartTag();
/* 8774 */     if (_jspx_eval_logic_005fnotEmpty_005f7 != 0) {
/*      */       for (;;) {
/* 8776 */         out.write(10);
/* 8777 */         JspRuntimeLibrary.include(request, response, "/jsp/ShowJDBCDetails.jsp", out, false);
/* 8778 */         out.write(10);
/* 8779 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f7.doAfterBody();
/* 8780 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8784 */     if (_jspx_th_logic_005fnotEmpty_005f7.doEndTag() == 5) {
/* 8785 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f7);
/* 8786 */       return true;
/*      */     }
/* 8788 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f7);
/* 8789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f8(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8794 */     PageContext pageContext = _jspx_page_context;
/* 8795 */     JspWriter out = _jspx_page_context.getOut();
/* 8796 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8797 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8799 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f8 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8800 */     _jspx_th_logic_005fnotEmpty_005f8.setPageContext(_jspx_page_context);
/* 8801 */     _jspx_th_logic_005fnotEmpty_005f8.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8803 */     _jspx_th_logic_005fnotEmpty_005f8.setName("jmsdata");
/* 8804 */     int _jspx_eval_logic_005fnotEmpty_005f8 = _jspx_th_logic_005fnotEmpty_005f8.doStartTag();
/* 8805 */     if (_jspx_eval_logic_005fnotEmpty_005f8 != 0) {
/*      */       for (;;) {
/* 8807 */         out.write(10);
/* 8808 */         JspRuntimeLibrary.include(request, response, "/jsp/ShowJMSDetails.jsp", out, false);
/* 8809 */         out.write(10);
/* 8810 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f8.doAfterBody();
/* 8811 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8815 */     if (_jspx_th_logic_005fnotEmpty_005f8.doEndTag() == 5) {
/* 8816 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f8);
/* 8817 */       return true;
/*      */     }
/* 8819 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f8);
/* 8820 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f9(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8825 */     PageContext pageContext = _jspx_page_context;
/* 8826 */     JspWriter out = _jspx_page_context.getOut();
/* 8827 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8828 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8830 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f9 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8831 */     _jspx_th_logic_005fnotEmpty_005f9.setPageContext(_jspx_page_context);
/* 8832 */     _jspx_th_logic_005fnotEmpty_005f9.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8834 */     _jspx_th_logic_005fnotEmpty_005f9.setName("safdata");
/* 8835 */     int _jspx_eval_logic_005fnotEmpty_005f9 = _jspx_th_logic_005fnotEmpty_005f9.doStartTag();
/* 8836 */     if (_jspx_eval_logic_005fnotEmpty_005f9 != 0) {
/*      */       for (;;) {
/* 8838 */         out.write(10);
/* 8839 */         JspRuntimeLibrary.include(request, response, "/jsp/ShowSAFDetails.jsp", out, false);
/* 8840 */         out.write(10);
/* 8841 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f9.doAfterBody();
/* 8842 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8846 */     if (_jspx_th_logic_005fnotEmpty_005f9.doEndTag() == 5) {
/* 8847 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f9);
/* 8848 */       return true;
/*      */     }
/* 8850 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f9);
/* 8851 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f10(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8856 */     PageContext pageContext = _jspx_page_context;
/* 8857 */     JspWriter out = _jspx_page_context.getOut();
/* 8858 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8859 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8861 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f10 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8862 */     _jspx_th_logic_005fnotEmpty_005f10.setPageContext(_jspx_page_context);
/* 8863 */     _jspx_th_logic_005fnotEmpty_005f10.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8865 */     _jspx_th_logic_005fnotEmpty_005f10.setName("data");
/* 8866 */     int _jspx_eval_logic_005fnotEmpty_005f10 = _jspx_th_logic_005fnotEmpty_005f10.doStartTag();
/* 8867 */     if (_jspx_eval_logic_005fnotEmpty_005f10 != 0) {
/*      */       for (;;) {
/* 8869 */         out.write(10);
/* 8870 */         JspRuntimeLibrary.include(request, response, "/jsp/ShowEJBDetails.jsp", out, false);
/* 8871 */         out.write(10);
/* 8872 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f10.doAfterBody();
/* 8873 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8877 */     if (_jspx_th_logic_005fnotEmpty_005f10.doEndTag() == 5) {
/* 8878 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f10);
/* 8879 */       return true;
/*      */     }
/* 8881 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f10);
/* 8882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f63(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8887 */     PageContext pageContext = _jspx_page_context;
/* 8888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8890 */     OutTag _jspx_th_c_005fout_005f63 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8891 */     _jspx_th_c_005fout_005f63.setPageContext(_jspx_page_context);
/* 8892 */     _jspx_th_c_005fout_005f63.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8894 */     _jspx_th_c_005fout_005f63.setValue("${fullvalue}");
/* 8895 */     int _jspx_eval_c_005fout_005f63 = _jspx_th_c_005fout_005f63.doStartTag();
/* 8896 */     if (_jspx_th_c_005fout_005f63.doEndTag() == 5) {
/* 8897 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f63);
/* 8898 */       return true;
/*      */     }
/* 8900 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f63);
/* 8901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f64(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8906 */     PageContext pageContext = _jspx_page_context;
/* 8907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8909 */     OutTag _jspx_th_c_005fout_005f64 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8910 */     _jspx_th_c_005fout_005f64.setPageContext(_jspx_page_context);
/* 8911 */     _jspx_th_c_005fout_005f64.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8913 */     _jspx_th_c_005fout_005f64.setValue("${fullvalue}");
/* 8914 */     int _jspx_eval_c_005fout_005f64 = _jspx_th_c_005fout_005f64.doStartTag();
/* 8915 */     if (_jspx_th_c_005fout_005f64.doEndTag() == 5) {
/* 8916 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f64);
/* 8917 */       return true;
/*      */     }
/* 8919 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f64);
/* 8920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8925 */     PageContext pageContext = _jspx_page_context;
/* 8926 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8928 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8929 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 8930 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 8932 */     _jspx_th_c_005fif_005f17.setTest("${not empty param.haid}");
/* 8933 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 8934 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 8936 */         out.write(10);
/* 8937 */         out.write(9);
/* 8938 */         out.write(9);
/* 8939 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f17, _jspx_page_context))
/* 8940 */           return true;
/* 8941 */         out.write(10);
/* 8942 */         out.write(9);
/* 8943 */         out.write(9);
/* 8944 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 8945 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8949 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 8950 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 8951 */       return true;
/*      */     }
/* 8953 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 8954 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8959 */     PageContext pageContext = _jspx_page_context;
/* 8960 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8962 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 8963 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 8964 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 8966 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 8968 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 8969 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 8970 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 8971 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 8972 */         out = _jspx_page_context.pushBody();
/* 8973 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 8974 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8977 */         if (_jspx_meth_c_005fout_005f65(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 8978 */           return true;
/* 8979 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 8980 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8983 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 8984 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8987 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 8988 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 8989 */       return true;
/*      */     }
/* 8991 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 8992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f65(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8997 */     PageContext pageContext = _jspx_page_context;
/* 8998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9000 */     OutTag _jspx_th_c_005fout_005f65 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9001 */     _jspx_th_c_005fout_005f65.setPageContext(_jspx_page_context);
/* 9002 */     _jspx_th_c_005fout_005f65.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 9004 */     _jspx_th_c_005fout_005f65.setValue("${param.haid}");
/* 9005 */     int _jspx_eval_c_005fout_005f65 = _jspx_th_c_005fout_005f65.doStartTag();
/* 9006 */     if (_jspx_th_c_005fout_005f65.doEndTag() == 5) {
/* 9007 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f65);
/* 9008 */       return true;
/*      */     }
/* 9010 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f65);
/* 9011 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9016 */     PageContext pageContext = _jspx_page_context;
/* 9017 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9019 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 9020 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 9021 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 9023 */     _jspx_th_c_005fif_005f18.setTest("${not empty param.resourceid}");
/* 9024 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 9025 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 9027 */         out.write(10);
/* 9028 */         out.write(9);
/* 9029 */         out.write(9);
/* 9030 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f18, _jspx_page_context))
/* 9031 */           return true;
/* 9032 */         out.write(10);
/* 9033 */         out.write(9);
/* 9034 */         out.write(9);
/* 9035 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 9036 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 9040 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 9041 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 9042 */       return true;
/*      */     }
/* 9044 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 9045 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9050 */     PageContext pageContext = _jspx_page_context;
/* 9051 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9053 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 9054 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 9055 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 9057 */     _jspx_th_c_005fset_005f2.setVar("myfield_paramresid");
/*      */     
/* 9059 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 9060 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 9061 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 9062 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 9063 */         out = _jspx_page_context.pushBody();
/* 9064 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 9065 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 9068 */         if (_jspx_meth_c_005fout_005f66(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 9069 */           return true;
/* 9070 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 9071 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 9074 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 9075 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 9078 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 9079 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 9080 */       return true;
/*      */     }
/* 9082 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 9083 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f66(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9088 */     PageContext pageContext = _jspx_page_context;
/* 9089 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9091 */     OutTag _jspx_th_c_005fout_005f66 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9092 */     _jspx_th_c_005fout_005f66.setPageContext(_jspx_page_context);
/* 9093 */     _jspx_th_c_005fout_005f66.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 9095 */     _jspx_th_c_005fout_005f66.setValue("${param.resourceid}");
/* 9096 */     int _jspx_eval_c_005fout_005f66 = _jspx_th_c_005fout_005f66.doStartTag();
/* 9097 */     if (_jspx_th_c_005fout_005f66.doEndTag() == 5) {
/* 9098 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f66);
/* 9099 */       return true;
/*      */     }
/* 9101 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f66);
/* 9102 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f67(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9107 */     PageContext pageContext = _jspx_page_context;
/* 9108 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9110 */     OutTag _jspx_th_c_005fout_005f67 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9111 */     _jspx_th_c_005fout_005f67.setPageContext(_jspx_page_context);
/* 9112 */     _jspx_th_c_005fout_005f67.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 9114 */     _jspx_th_c_005fout_005f67.setValue("${myfield_paramresid}");
/* 9115 */     int _jspx_eval_c_005fout_005f67 = _jspx_th_c_005fout_005f67.doStartTag();
/* 9116 */     if (_jspx_th_c_005fout_005f67.doEndTag() == 5) {
/* 9117 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f67);
/* 9118 */       return true;
/*      */     }
/* 9120 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f67);
/* 9121 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9126 */     PageContext pageContext = _jspx_page_context;
/* 9127 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9129 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 9130 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 9131 */     _jspx_th_logic_005fpresent_005f6.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 9133 */     _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 9134 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 9135 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 9137 */         out.write("\n\t\t<a href=\"javascript:alertUser()\"><img src=\"/images/icon_executeaction.gif\"  border=\"0\"></a></td>\n\t\t");
/* 9138 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 9139 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 9143 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 9144 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 9145 */       return true;
/*      */     }
/* 9147 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 9148 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9153 */     PageContext pageContext = _jspx_page_context;
/* 9154 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9156 */     org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
/* 9157 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 9158 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 9159 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 9160 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 9162 */         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td align=\"center\" class=\"bodytextbold11\"><a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 9163 */         if (_jspx_meth_c_005fout_005f68(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 9164 */           return true;
/* 9165 */         if (_jspx_meth_c_005fif_005f20(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 9166 */           return true;
/* 9167 */         out.write("\" class=\"staticlinks\">Goto Snapshot View</a></td>\n  </tr>\n</table>\n");
/* 9168 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 9169 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 9173 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 9174 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 9175 */       return true;
/*      */     }
/* 9177 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 9178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f68(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9183 */     PageContext pageContext = _jspx_page_context;
/* 9184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9186 */     OutTag _jspx_th_c_005fout_005f68 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9187 */     _jspx_th_c_005fout_005f68.setPageContext(_jspx_page_context);
/* 9188 */     _jspx_th_c_005fout_005f68.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 9190 */     _jspx_th_c_005fout_005f68.setValue("${param.resourceid}");
/* 9191 */     int _jspx_eval_c_005fout_005f68 = _jspx_th_c_005fout_005f68.doStartTag();
/* 9192 */     if (_jspx_th_c_005fout_005f68.doEndTag() == 5) {
/* 9193 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f68);
/* 9194 */       return true;
/*      */     }
/* 9196 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f68);
/* 9197 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9202 */     PageContext pageContext = _jspx_page_context;
/* 9203 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9205 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 9206 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 9207 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 9209 */     _jspx_th_c_005fif_005f20.setTest("${ !empty param.haid && param.haid!='null' }");
/* 9210 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 9211 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 9213 */         out.write("&haid=");
/* 9214 */         if (_jspx_meth_c_005fout_005f69(_jspx_th_c_005fif_005f20, _jspx_page_context))
/* 9215 */           return true;
/* 9216 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 9217 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 9221 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 9222 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 9223 */       return true;
/*      */     }
/* 9225 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 9226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f69(JspTag _jspx_th_c_005fif_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9231 */     PageContext pageContext = _jspx_page_context;
/* 9232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9234 */     OutTag _jspx_th_c_005fout_005f69 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9235 */     _jspx_th_c_005fout_005f69.setPageContext(_jspx_page_context);
/* 9236 */     _jspx_th_c_005fout_005f69.setParent((Tag)_jspx_th_c_005fif_005f20);
/*      */     
/* 9238 */     _jspx_th_c_005fout_005f69.setValue("${param.haid}");
/* 9239 */     int _jspx_eval_c_005fout_005f69 = _jspx_th_c_005fout_005f69.doStartTag();
/* 9240 */     if (_jspx_th_c_005fout_005f69.doEndTag() == 5) {
/* 9241 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f69);
/* 9242 */       return true;
/*      */     }
/* 9244 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f69);
/* 9245 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f6(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9250 */     PageContext pageContext = _jspx_page_context;
/* 9251 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9253 */     PutTag _jspx_th_tiles_005fput_005f6 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 9254 */     _jspx_th_tiles_005fput_005f6.setPageContext(_jspx_page_context);
/* 9255 */     _jspx_th_tiles_005fput_005f6.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 9257 */     _jspx_th_tiles_005fput_005f6.setName("footer");
/*      */     
/* 9259 */     _jspx_th_tiles_005fput_005f6.setValue("/jsp/footer.jsp");
/* 9260 */     int _jspx_eval_tiles_005fput_005f6 = _jspx_th_tiles_005fput_005f6.doStartTag();
/* 9261 */     if (_jspx_th_tiles_005fput_005f6.doEndTag() == 5) {
/* 9262 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 9263 */       return true;
/*      */     }
/* 9265 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 9266 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\WlogicComponent_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */