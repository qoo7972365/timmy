/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.text.NumberFormat;
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
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class ApacheDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   57 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   60 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   61 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   62 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   69 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   74 */     ArrayList list = null;
/*   75 */     StringBuffer sbf = new StringBuffer();
/*   76 */     ManagedApplication mo = new ManagedApplication();
/*   77 */     if (distinct)
/*      */     {
/*   79 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   83 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   86 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   88 */       ArrayList row = (ArrayList)list.get(i);
/*   89 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   90 */       if (distinct) {
/*   91 */         sbf.append(row.get(0));
/*      */       } else
/*   93 */         sbf.append(row.get(1));
/*   94 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   97 */     return sbf.toString(); }
/*      */   
/*   99 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  102 */     if (severity == null)
/*      */     {
/*  104 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  106 */     if (severity.equals("5"))
/*      */     {
/*  108 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  110 */     if (severity.equals("1"))
/*      */     {
/*  112 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  117 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  124 */     if (severity == null)
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  128 */     if (severity.equals("1"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  132 */     if (severity.equals("4"))
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  136 */     if (severity.equals("5"))
/*      */     {
/*  138 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  143 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  149 */     if (severity == null)
/*      */     {
/*  151 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  153 */     if (severity.equals("5"))
/*      */     {
/*  155 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  157 */     if (severity.equals("1"))
/*      */     {
/*  159 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  163 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  169 */     if (severity == null)
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  173 */     if (severity.equals("1"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  177 */     if (severity.equals("4"))
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  181 */     if (severity.equals("5"))
/*      */     {
/*  183 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  187 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  193 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  199 */     if (severity == 5)
/*      */     {
/*  201 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  203 */     if (severity == 1)
/*      */     {
/*  205 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  210 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  216 */     if (severity == null)
/*      */     {
/*  218 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  220 */     if (severity.equals("5"))
/*      */     {
/*  222 */       if (isAvailability) {
/*  223 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  226 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  229 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  231 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  233 */     if (severity.equals("1"))
/*      */     {
/*  235 */       if (isAvailability) {
/*  236 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  239 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  246 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  253 */     if (severity == null)
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  257 */     if (severity.equals("5"))
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  261 */     if (severity.equals("4"))
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  265 */     if (severity.equals("1"))
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  272 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  278 */     if (severity == null)
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("5"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  286 */     if (severity.equals("4"))
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  290 */     if (severity.equals("1"))
/*      */     {
/*  292 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  297 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  304 */     if (severity == null)
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  308 */     if (severity.equals("5"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  312 */     if (severity.equals("4"))
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  316 */     if (severity.equals("1"))
/*      */     {
/*  318 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  323 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  331 */     StringBuffer out = new StringBuffer();
/*  332 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  333 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  334 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  335 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  336 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  337 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  338 */     out.append("</tr>");
/*  339 */     out.append("</form></table>");
/*  340 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  347 */     if (val == null)
/*      */     {
/*  349 */       return "-";
/*      */     }
/*      */     
/*  352 */     String ret = FormatUtil.formatNumber(val);
/*  353 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  354 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  357 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  361 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  369 */     StringBuffer out = new StringBuffer();
/*  370 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  371 */     out.append("<tr>");
/*  372 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  374 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  376 */     out.append("</tr>");
/*  377 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  381 */       if (j % 2 == 0)
/*      */       {
/*  383 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  387 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  390 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  392 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  395 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  399 */       out.append("</tr>");
/*      */     }
/*  401 */     out.append("</table>");
/*  402 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  403 */     out.append("<tr>");
/*  404 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  405 */     out.append("</tr>");
/*  406 */     out.append("</table>");
/*  407 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
/*      */   {
/*  413 */     StringBuffer out = new StringBuffer();
/*  414 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  415 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  416 */     out.append("<tr>");
/*  417 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  418 */     out.append("<tr>");
/*  419 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  420 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  421 */     out.append("</tr>");
/*  422 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  425 */       out.append("<tr>");
/*  426 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  427 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  428 */       out.append("</tr>");
/*      */     }
/*      */     
/*  431 */     out.append("</table>");
/*  432 */     out.append("</table>");
/*  433 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  438 */     if (severity.equals("0"))
/*      */     {
/*  440 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  444 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  451 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  464 */     StringBuffer out = new StringBuffer();
/*  465 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  466 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  468 */       out.append("<tr>");
/*  469 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  470 */       out.append("</tr>");
/*      */       
/*      */ 
/*  473 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  475 */         String borderclass = "";
/*      */         
/*      */ 
/*  478 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  480 */         out.append("<tr>");
/*      */         
/*  482 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  483 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  484 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  490 */     out.append("</table><br>");
/*  491 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  492 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  494 */       List sLinks = secondLevelOfLinks[0];
/*  495 */       List sText = secondLevelOfLinks[1];
/*  496 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  499 */         out.append("<tr>");
/*  500 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  501 */         out.append("</tr>");
/*  502 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  504 */           String borderclass = "";
/*      */           
/*      */ 
/*  507 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  509 */           out.append("<tr>");
/*      */           
/*  511 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  512 */           if (sLinks.get(i).toString().length() == 0) {
/*  513 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  516 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  518 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  522 */     out.append("</table>");
/*  523 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  530 */     StringBuffer out = new StringBuffer();
/*  531 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  532 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  534 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  536 */         out.append("<tr>");
/*  537 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  538 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  542 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  544 */           String borderclass = "";
/*      */           
/*      */ 
/*  547 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  549 */           out.append("<tr>");
/*      */           
/*  551 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  552 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  553 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  556 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  559 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  564 */     out.append("</table><br>");
/*  565 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  566 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  568 */       List sLinks = secondLevelOfLinks[0];
/*  569 */       List sText = secondLevelOfLinks[1];
/*  570 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  573 */         out.append("<tr>");
/*  574 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  575 */         out.append("</tr>");
/*  576 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  578 */           String borderclass = "";
/*      */           
/*      */ 
/*  581 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  583 */           out.append("<tr>");
/*      */           
/*  585 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  586 */           if (sLinks.get(i).toString().length() == 0) {
/*  587 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  590 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  592 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  596 */     out.append("</table>");
/*  597 */     return out.toString();
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
/*  610 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  622 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  625 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  628 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  631 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  639 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  644 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  649 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  654 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  659 */     if (val != null)
/*      */     {
/*  661 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  665 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  670 */     if (val == null) {
/*  671 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  675 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  680 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  686 */     if (val != null)
/*      */     {
/*  688 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  692 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  698 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  703 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  707 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  712 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  717 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  722 */     String hostaddress = "";
/*  723 */     String ip = request.getHeader("x-forwarded-for");
/*  724 */     if (ip == null)
/*  725 */       ip = request.getRemoteAddr();
/*  726 */     InetAddress add = null;
/*  727 */     if (ip.equals("127.0.0.1")) {
/*  728 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  732 */       add = InetAddress.getByName(ip);
/*      */     }
/*  734 */     hostaddress = add.getHostName();
/*  735 */     if (hostaddress.indexOf('.') != -1) {
/*  736 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  737 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  741 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  746 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  752 */     if (severity == null)
/*      */     {
/*  754 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  756 */     if (severity.equals("5"))
/*      */     {
/*  758 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  760 */     if (severity.equals("1"))
/*      */     {
/*  762 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  767 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  772 */     ResultSet set = null;
/*  773 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  774 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  776 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  777 */       if (set.next()) { String str1;
/*  778 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  779 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  782 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  787 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  790 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  792 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  796 */     StringBuffer rca = new StringBuffer();
/*  797 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  798 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  801 */     int rcalength = key.length();
/*  802 */     String split = "6. ";
/*  803 */     int splitPresent = key.indexOf(split);
/*  804 */     String div1 = "";String div2 = "";
/*  805 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  807 */       if (rcalength > 180) {
/*  808 */         rca.append("<span class=\"rca-critical-text\">");
/*  809 */         getRCATrimmedText(key, rca);
/*  810 */         rca.append("</span>");
/*      */       } else {
/*  812 */         rca.append("<span class=\"rca-critical-text\">");
/*  813 */         rca.append(key);
/*  814 */         rca.append("</span>");
/*      */       }
/*  816 */       return rca.toString();
/*      */     }
/*  818 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  819 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  820 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  821 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  822 */     getRCATrimmedText(div1, rca);
/*  823 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  826 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  827 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  828 */     getRCATrimmedText(div2, rca);
/*  829 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  831 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  836 */     String[] st = msg.split("<br>");
/*  837 */     for (int i = 0; i < st.length; i++) {
/*  838 */       String s = st[i];
/*  839 */       if (s.length() > 180) {
/*  840 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  842 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  846 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  847 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  849 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  853 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  854 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  855 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  858 */       if (key == null) {
/*  859 */         return ret;
/*      */       }
/*      */       
/*  862 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  863 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  866 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  867 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  868 */       set = AMConnectionPool.executeQueryStmt(query);
/*  869 */       if (set.next())
/*      */       {
/*  871 */         String helpLink = set.getString("LINK");
/*  872 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  875 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  881 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  900 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  891 */         if (set != null) {
/*  892 */           AMConnectionPool.closeStatement(set);
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
/*  906 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  907 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  909 */       String entityStr = (String)keys.nextElement();
/*  910 */       String mmessage = temp.getProperty(entityStr);
/*  911 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  912 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  914 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  920 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  921 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  923 */       String entityStr = (String)keys.nextElement();
/*  924 */       String mmessage = temp.getProperty(entityStr);
/*  925 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  926 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  928 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  933 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  943 */     String des = new String();
/*  944 */     while (str.indexOf(find) != -1) {
/*  945 */       des = des + str.substring(0, str.indexOf(find));
/*  946 */       des = des + replace;
/*  947 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  949 */     des = des + str;
/*  950 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  957 */       if (alert == null)
/*      */       {
/*  959 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  961 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  963 */         return "&nbsp;";
/*      */       }
/*      */       
/*  966 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  968 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  971 */       int rcalength = test.length();
/*  972 */       if (rcalength < 300)
/*      */       {
/*  974 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  978 */       StringBuffer out = new StringBuffer();
/*  979 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  980 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  981 */       out.append("</div>");
/*  982 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  983 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  984 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  989 */       ex.printStackTrace();
/*      */     }
/*  991 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  997 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1002 */     ArrayList attribIDs = new ArrayList();
/* 1003 */     ArrayList resIDs = new ArrayList();
/* 1004 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1006 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1008 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1010 */       String resourceid = "";
/* 1011 */       String resourceType = "";
/* 1012 */       if (type == 2) {
/* 1013 */         resourceid = (String)row.get(0);
/* 1014 */         resourceType = (String)row.get(3);
/*      */       }
/* 1016 */       else if (type == 3) {
/* 1017 */         resourceid = (String)row.get(0);
/* 1018 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1021 */         resourceid = (String)row.get(6);
/* 1022 */         resourceType = (String)row.get(7);
/*      */       }
/* 1024 */       resIDs.add(resourceid);
/* 1025 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1026 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1028 */       String healthentity = null;
/* 1029 */       String availentity = null;
/* 1030 */       if (healthid != null) {
/* 1031 */         healthentity = resourceid + "_" + healthid;
/* 1032 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1035 */       if (availid != null) {
/* 1036 */         availentity = resourceid + "_" + availid;
/* 1037 */         entitylist.add(availentity);
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
/* 1051 */     Properties alert = getStatus(entitylist);
/* 1052 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1057 */     int size = monitorList.size();
/*      */     
/* 1059 */     String[] severity = new String[size];
/*      */     
/* 1061 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1063 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1064 */       String resourceName1 = (String)row1.get(7);
/* 1065 */       String resourceid1 = (String)row1.get(6);
/* 1066 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1067 */       if (severity[j] == null)
/*      */       {
/* 1069 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1073 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1075 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1077 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1080 */         if (sev > 0) {
/* 1081 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1082 */           monitorList.set(k, monitorList.get(j));
/* 1083 */           monitorList.set(j, t);
/* 1084 */           String temp = severity[k];
/* 1085 */           severity[k] = severity[j];
/* 1086 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1092 */     int z = 0;
/* 1093 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1096 */       int i = 0;
/* 1097 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1100 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1104 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1108 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1110 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1113 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1117 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1120 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1121 */       String resourceName1 = (String)row1.get(7);
/* 1122 */       String resourceid1 = (String)row1.get(6);
/* 1123 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1124 */       if (hseverity[j] == null)
/*      */       {
/* 1126 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1131 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1133 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1136 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1139 */         if (hsev > 0) {
/* 1140 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1141 */           monitorList.set(k, monitorList.get(j));
/* 1142 */           monitorList.set(j, t);
/* 1143 */           String temp1 = hseverity[k];
/* 1144 */           hseverity[k] = hseverity[j];
/* 1145 */           hseverity[j] = temp1;
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
/* 1157 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1158 */     boolean forInventory = false;
/* 1159 */     String trdisplay = "none";
/* 1160 */     String plusstyle = "inline";
/* 1161 */     String minusstyle = "none";
/* 1162 */     String haidTopLevel = "";
/* 1163 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1165 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1167 */         haidTopLevel = request.getParameter("haid");
/* 1168 */         forInventory = true;
/* 1169 */         trdisplay = "table-row;";
/* 1170 */         plusstyle = "none";
/* 1171 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1178 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1181 */     ArrayList listtoreturn = new ArrayList();
/* 1182 */     StringBuffer toreturn = new StringBuffer();
/* 1183 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1184 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1185 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1187 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1189 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1190 */       String childresid = (String)singlerow.get(0);
/* 1191 */       String childresname = (String)singlerow.get(1);
/* 1192 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1193 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1194 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1195 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1196 */       String unmanagestatus = (String)singlerow.get(5);
/* 1197 */       String actionstatus = (String)singlerow.get(6);
/* 1198 */       String linkclass = "monitorgp-links";
/* 1199 */       String titleforres = childresname;
/* 1200 */       String titilechildresname = childresname;
/* 1201 */       String childimg = "/images/trcont.png";
/* 1202 */       String flag = "enable";
/* 1203 */       String dcstarted = (String)singlerow.get(8);
/* 1204 */       String configMonitor = "";
/* 1205 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1206 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1208 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1210 */       if (singlerow.get(7) != null)
/*      */       {
/* 1212 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1214 */       String haiGroupType = "0";
/* 1215 */       if ("HAI".equals(childtype))
/*      */       {
/* 1217 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1219 */       childimg = "/images/trend.png";
/* 1220 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1221 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1222 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1224 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1226 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1228 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1229 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1232 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1234 */         linkclass = "disabledtext";
/* 1235 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1237 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1238 */       String availmouseover = "";
/* 1239 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1241 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1243 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1244 */       String healthmouseover = "";
/* 1245 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1247 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1250 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1251 */       int spacing = 0;
/* 1252 */       if (level >= 1)
/*      */       {
/* 1254 */         spacing = 40 * level;
/*      */       }
/* 1256 */       if (childtype.equals("HAI"))
/*      */       {
/* 1258 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1259 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1260 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1262 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1263 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1264 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1265 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1266 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1267 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1268 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1269 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1270 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1271 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1272 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1274 */         if (!forInventory)
/*      */         {
/* 1276 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1279 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1281 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1283 */           actions = editlink + actions;
/*      */         }
/* 1285 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1287 */           actions = actions + associatelink;
/*      */         }
/* 1289 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1290 */         String arrowimg = "";
/* 1291 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1293 */           actions = "";
/* 1294 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1295 */           checkbox = "";
/* 1296 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1298 */         if (isIt360)
/*      */         {
/* 1300 */           actionimg = "";
/* 1301 */           actions = "";
/* 1302 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1303 */           checkbox = "";
/*      */         }
/*      */         
/* 1306 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1308 */           actions = "";
/*      */         }
/* 1310 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1312 */           checkbox = "";
/*      */         }
/*      */         
/* 1315 */         String resourcelink = "";
/*      */         
/* 1317 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1319 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1323 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1326 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1332 */         if (!isIt360)
/*      */         {
/* 1334 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1338 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1341 */         toreturn.append("</tr>");
/* 1342 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1344 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1345 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1349 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1350 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1353 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1357 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1359 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1361 */             toreturn.append(assocMessage);
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1364 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1365 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1371 */         String resourcelink = null;
/* 1372 */         boolean hideEditLink = false;
/* 1373 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1375 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1376 */           hideEditLink = true;
/* 1377 */           if (isIt360)
/*      */           {
/* 1379 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1383 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1385 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1387 */           hideEditLink = true;
/* 1388 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1389 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1394 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1397 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1398 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1399 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1400 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1401 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1402 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1403 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1404 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1405 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1406 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1407 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1408 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1409 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1411 */         if (hideEditLink)
/*      */         {
/* 1413 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1415 */         if (!forInventory)
/*      */         {
/* 1417 */           removefromgroup = "";
/*      */         }
/* 1419 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1420 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1421 */           actions = actions + configcustomfields;
/*      */         }
/* 1423 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1425 */           actions = editlink + actions;
/*      */         }
/* 1427 */         String managedLink = "";
/* 1428 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1430 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1431 */           actions = "";
/* 1432 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1433 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1436 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1438 */           checkbox = "";
/*      */         }
/*      */         
/* 1441 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1443 */           actions = "";
/*      */         }
/* 1445 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1448 */         if (isIt360)
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1456 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1457 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1458 */         if (!isIt360)
/*      */         {
/* 1460 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1464 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1466 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1469 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1476 */       StringBuilder toreturn = new StringBuilder();
/* 1477 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1478 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1479 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1480 */       String title = "";
/* 1481 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1482 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1483 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1484 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1486 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1488 */       else if ("5".equals(severity))
/*      */       {
/* 1490 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1494 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1496 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1497 */       toreturn.append(v);
/*      */       
/* 1499 */       toreturn.append(link);
/* 1500 */       if (severity == null)
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1504 */       else if (severity.equals("5"))
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       else if (severity.equals("4"))
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1512 */       else if (severity.equals("1"))
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1519 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1521 */       toreturn.append("</a>");
/* 1522 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1526 */       ex.printStackTrace();
/*      */     }
/* 1528 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1535 */       StringBuilder toreturn = new StringBuilder();
/* 1536 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1537 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1538 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1539 */       if (message == null)
/*      */       {
/* 1541 */         message = "";
/*      */       }
/*      */       
/* 1544 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1545 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1547 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1548 */       toreturn.append(v);
/*      */       
/* 1550 */       toreturn.append(link);
/*      */       
/* 1552 */       if (severity == null)
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1556 */       else if (severity.equals("5"))
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1560 */       else if (severity.equals("1"))
/*      */       {
/* 1562 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1567 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1569 */       toreturn.append("</a>");
/* 1570 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1576 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1579 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1580 */     if (invokeActions != null) {
/* 1581 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1582 */       while (iterator.hasNext()) {
/* 1583 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1584 */         if (actionmap.containsKey(actionid)) {
/* 1585 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1590 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1594 */     String actionLink = "";
/* 1595 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1596 */     String query = "";
/* 1597 */     ResultSet rs = null;
/* 1598 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1599 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1600 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1601 */       actionLink = "method=" + methodName;
/*      */     }
/* 1603 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1604 */       actionLink = methodName;
/*      */     }
/* 1606 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1607 */     Iterator itr = methodarglist.iterator();
/* 1608 */     boolean isfirstparam = true;
/* 1609 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1610 */     while (itr.hasNext()) {
/* 1611 */       HashMap argmap = (HashMap)itr.next();
/* 1612 */       String argtype = (String)argmap.get("TYPE");
/* 1613 */       String argname = (String)argmap.get("IDENTITY");
/* 1614 */       String paramname = (String)argmap.get("PARAMETER");
/* 1615 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1616 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1617 */         isfirstparam = false;
/* 1618 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1620 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1624 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1628 */         actionLink = actionLink + "&";
/*      */       }
/* 1630 */       String paramValue = null;
/* 1631 */       String tempargname = argname;
/* 1632 */       if (commonValues.getProperty(tempargname) != null) {
/* 1633 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1636 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1637 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1638 */           if (dbType.equals("mysql")) {
/* 1639 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1642 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1644 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1646 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1647 */             if (rs.next()) {
/* 1648 */               paramValue = rs.getString("VALUE");
/* 1649 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1653 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1657 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1660 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1665 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1666 */           paramValue = rowId;
/*      */         }
/* 1668 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1669 */           paramValue = managedObjectName;
/*      */         }
/* 1671 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1672 */           paramValue = resID;
/*      */         }
/* 1674 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1675 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1678 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1680 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1681 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1682 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1684 */     return actionLink;
/*      */   }
/*      */   
/* 1687 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1688 */     String dependentAttribute = null;
/* 1689 */     String align = "left";
/*      */     
/* 1691 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1692 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1693 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1694 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1695 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1696 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1697 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1698 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1699 */       align = "center";
/*      */     }
/*      */     
/* 1702 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1703 */     String actualdata = "";
/*      */     
/* 1705 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1706 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1707 */         actualdata = availValue;
/*      */       }
/* 1709 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1710 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1714 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1715 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1718 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1724 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1725 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1726 */       toreturn.append("<table>");
/* 1727 */       toreturn.append("<tr>");
/* 1728 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1729 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1730 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1731 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1732 */         String toolTip = "";
/* 1733 */         String hideClass = "";
/* 1734 */         String textStyle = "";
/* 1735 */         boolean isreferenced = true;
/* 1736 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1737 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1738 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1739 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1741 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1742 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1743 */           while (valueList.hasMoreTokens()) {
/* 1744 */             String dependentVal = valueList.nextToken();
/* 1745 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1746 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1747 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1749 */               toolTip = "";
/* 1750 */               hideClass = "";
/* 1751 */               isreferenced = false;
/* 1752 */               textStyle = "disabledtext";
/* 1753 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1757 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1758 */           toolTip = "";
/* 1759 */           hideClass = "";
/* 1760 */           isreferenced = false;
/* 1761 */           textStyle = "disabledtext";
/* 1762 */           if (dependentImageMap != null) {
/* 1763 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1764 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1767 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1771 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1772 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1773 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1774 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1775 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1776 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1778 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1779 */           if (isreferenced) {
/* 1780 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1784 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1785 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1786 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1787 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1788 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1789 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1791 */           toreturn.append("</span>");
/* 1792 */           toreturn.append("</a>");
/* 1793 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1796 */       toreturn.append("</tr>");
/* 1797 */       toreturn.append("</table>");
/* 1798 */       toreturn.append("</td>");
/*      */     } else {
/* 1800 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1803 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1807 */     String colTime = null;
/* 1808 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1809 */     if ((rows != null) && (rows.size() > 0)) {
/* 1810 */       Iterator<String> itr = rows.iterator();
/* 1811 */       String maxColQuery = "";
/* 1812 */       for (;;) { if (itr.hasNext()) {
/* 1813 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1814 */           ResultSet maxCol = null;
/*      */           try {
/* 1816 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1817 */             while (maxCol.next()) {
/* 1818 */               if (colTime == null) {
/* 1819 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1822 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1831 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1833 */               if (maxCol != null)
/* 1834 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1836 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1831 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1833 */               if (maxCol != null)
/* 1834 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1836 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1841 */     return colTime;
/*      */   }
/*      */   
/* 1844 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1845 */     tablename = null;
/* 1846 */     ResultSet rsTable = null;
/* 1847 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1849 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1850 */       while (rsTable.next()) {
/* 1851 */         tablename = rsTable.getString("DATATABLE");
/* 1852 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1853 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1866 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1857 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1860 */         if (rsTable != null)
/* 1861 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1863 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1869 */     String argsList = "";
/* 1870 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1872 */       if (showArgsMap.get(row) != null) {
/* 1873 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1874 */         if (showArgslist != null) {
/* 1875 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1876 */             if (argsList.trim().equals("")) {
/* 1877 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1880 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1887 */       e.printStackTrace();
/* 1888 */       return "";
/*      */     }
/* 1890 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1895 */     String argsList = "";
/* 1896 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1899 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1901 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1902 */         if (hideArgsList != null)
/*      */         {
/* 1904 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1906 */             if (argsList.trim().equals(""))
/*      */             {
/* 1908 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1912 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1920 */       ex.printStackTrace();
/*      */     }
/* 1922 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1926 */     StringBuilder toreturn = new StringBuilder();
/* 1927 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1934 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1935 */       Iterator itr = tActionList.iterator();
/* 1936 */       while (itr.hasNext()) {
/* 1937 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1938 */         String confirmmsg = "";
/* 1939 */         String link = "";
/* 1940 */         String isJSP = "NO";
/* 1941 */         HashMap tactionMap = (HashMap)itr.next();
/* 1942 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1943 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1944 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1945 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1946 */           (actionmap.containsKey(actionId))) {
/* 1947 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1948 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1949 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1950 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1951 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1953 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1959 */           if (isTableAction) {
/* 1960 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1963 */             tableName = "Link";
/* 1964 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1965 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1966 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1967 */             toreturn.append("</a></td>");
/*      */           }
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1978 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1984 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1986 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1987 */       Properties prop = (Properties)node.getUserObject();
/* 1988 */       String mgID = prop.getProperty("label");
/* 1989 */       String mgName = prop.getProperty("value");
/* 1990 */       String isParent = prop.getProperty("isParent");
/* 1991 */       int mgIDint = Integer.parseInt(mgID);
/* 1992 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1994 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1996 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1997 */       if (node.getChildCount() > 0)
/*      */       {
/* 1999 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2001 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2003 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2005 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2009 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2014 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2016 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2018 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2020 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2024 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2027 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2028 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2030 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2034 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2036 */       if (node.getChildCount() > 0)
/*      */       {
/* 2038 */         builder.append("<UL>");
/* 2039 */         printMGTree(node, builder);
/* 2040 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2045 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2046 */     StringBuffer toReturn = new StringBuffer();
/* 2047 */     String table = "-";
/*      */     try {
/* 2049 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2050 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2051 */       float total = 0.0F;
/* 2052 */       while (it.hasNext()) {
/* 2053 */         String attName = (String)it.next();
/* 2054 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2055 */         boolean roundOffData = false;
/* 2056 */         if ((data != null) && (!data.equals(""))) {
/* 2057 */           if (data.indexOf(",") != -1) {
/* 2058 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2061 */             float value = Float.parseFloat(data);
/* 2062 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2065 */             total += value;
/* 2066 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2069 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2074 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2075 */       while (attVsWidthList.hasNext()) {
/* 2076 */         String attName = (String)attVsWidthList.next();
/* 2077 */         String data = (String)attVsWidthProps.get(attName);
/* 2078 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2079 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2080 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2081 */         String className = (String)graphDetails.get("ClassName");
/* 2082 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2083 */         if (percentage < 1.0F)
/*      */         {
/* 2085 */           data = percentage + "";
/*      */         }
/* 2087 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2089 */       if (toReturn.length() > 0) {
/* 2090 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2094 */       e.printStackTrace();
/*      */     }
/* 2096 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2102 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2103 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2104 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2105 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2106 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2107 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2108 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2109 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2110 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2113 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2114 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2115 */       splitvalues[0] = multiplecondition.toString();
/* 2116 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2119 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2124 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2125 */     if (thresholdType != 3) {
/* 2126 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2127 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2128 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2129 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2130 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2131 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2133 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2134 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2135 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2136 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2137 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2138 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2140 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2141 */     if (updateSelected != null) {
/* 2142 */       updateSelected[0] = "selected";
/*      */     }
/* 2144 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2149 */       StringBuffer toreturn = new StringBuffer("");
/* 2150 */       if (commaSeparatedMsgId != null) {
/* 2151 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2152 */         int count = 0;
/* 2153 */         while (msgids.hasMoreTokens()) {
/* 2154 */           String id = msgids.nextToken();
/* 2155 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2156 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2157 */           count++;
/* 2158 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2159 */             if (toreturn.length() == 0) {
/* 2160 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2162 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2163 */             if (!image.trim().equals("")) {
/* 2164 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2166 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2167 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2170 */         if (toreturn.length() > 0) {
/* 2171 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2175 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2178 */       e.printStackTrace(); }
/* 2179 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2185 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2191 */   private static Map<String, Long> _jspx_dependants = new HashMap(8);
/* 2192 */   static { _jspx_dependants.put("/jsp/includes/ApacheStats.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2194 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2195 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2196 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2197 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/* 2198 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2199 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2234 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2238 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2266 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2270 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2271 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2272 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2274 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2275 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2276 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2277 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2278 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2279 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2280 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2281 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.release();
/* 2282 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
/* 2283 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2284 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2286 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2287 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2288 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2289 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2290 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2291 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2292 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2293 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2294 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2295 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2296 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2303 */     HttpSession session = null;
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
/* 2324 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2325 */       out.write(10);
/*      */       
/* 2327 */       request.setAttribute("HelpKey", "Monitors Service Details");
/*      */       
/* 2329 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2330 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2332 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2333 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2334 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2336 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2338 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2340 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2342 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2343 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2344 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2345 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2348 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2349 */         String available = null;
/* 2350 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2351 */         out.write(10);
/*      */         
/* 2353 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2354 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2355 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2357 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2359 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2361 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2363 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2364 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2365 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2366 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2369 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2370 */           String unavailable = null;
/* 2371 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2372 */           out.write(10);
/*      */           
/* 2374 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2375 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2376 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2378 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2380 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2382 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2384 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2385 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2386 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2387 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2390 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2391 */             String unmanaged = null;
/* 2392 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2393 */             out.write(10);
/*      */             
/* 2395 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2396 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2397 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2399 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2401 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2403 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2405 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2406 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2407 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2408 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2411 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2412 */               String scheduled = null;
/* 2413 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2414 */               out.write(10);
/*      */               
/* 2416 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2417 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2418 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2420 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2422 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2424 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2426 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2427 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2428 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2429 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2432 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2433 */                 String critical = null;
/* 2434 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2435 */                 out.write(10);
/*      */                 
/* 2437 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2438 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2439 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2441 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2443 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2445 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2447 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2448 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2449 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2450 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2453 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2454 */                   String clear = null;
/* 2455 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2456 */                   out.write(10);
/*      */                   
/* 2458 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2459 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2460 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2462 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2464 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2466 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2468 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2469 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2470 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2471 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2474 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2475 */                     String warning = null;
/* 2476 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2477 */                     out.write(10);
/* 2478 */                     out.write(10);
/*      */                     
/* 2480 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2481 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2483 */                     out.write(10);
/* 2484 */                     out.write(10);
/* 2485 */                     out.write(10);
/* 2486 */                     out.write("\n\n\n\n\n\n");
/* 2487 */                     GetWLSGraph wlsGraph = null;
/* 2488 */                     wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2489 */                     if (wlsGraph == null) {
/* 2490 */                       wlsGraph = new GetWLSGraph();
/* 2491 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2493 */                     out.write(10);
/* 2494 */                     Hashtable motypedisplaynames = null;
/* 2495 */                     synchronized (application) {
/* 2496 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2497 */                       if (motypedisplaynames == null) {
/* 2498 */                         motypedisplaynames = new Hashtable();
/* 2499 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2502 */                     out.write(10);
/* 2503 */                     Hashtable availabilitykeys = null;
/* 2504 */                     synchronized (application) {
/* 2505 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2506 */                       if (availabilitykeys == null) {
/* 2507 */                         availabilitykeys = new Hashtable();
/* 2508 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2511 */                     out.write(10);
/* 2512 */                     Hashtable healthkeys = null;
/* 2513 */                     synchronized (application) {
/* 2514 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2515 */                       if (healthkeys == null) {
/* 2516 */                         healthkeys = new Hashtable();
/* 2517 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2520 */                     out.write("\n\n<script>\nfunction submitForm()\n{\n\t");
/* 2521 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2523 */                     out.write(10);
/* 2524 */                     out.write(9);
/*      */                     
/* 2526 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2527 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2528 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/* 2530 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2531 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2532 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2534 */                         out.write("\n\n\tvar poll=trimAll(document.AMActionForm.pollInterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n                alert(\"");
/* 2535 */                         out.print(FormatUtil.getString("am.webclient.common.validpollinginterval.text"));
/* 2536 */                         out.write("\");\n\t\treturn;\n\t}\n\tif(document.AMActionForm.serverstatusurl.checked==true)\n\t{\n\t\tif(trimAll(document.AMActionForm.apacheurl.value)=='')\n\t\t{\n\t\t\talert(\"");
/* 2537 */                         out.print(FormatUtil.getString("am.webclient.apache.enterstatusurl"));
/* 2538 */                         out.write("\");\n\t\t\treturn;\n\t\t}\n\t}\n\n\tdocument.AMActionForm.submit();\n\t");
/* 2539 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2540 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2544 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2545 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     }
/*      */                     else {
/* 2548 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2549 */                       out.write("\n}\nfunction showApacheAuth()\n        {\n\t\tif(document.AMActionForm.apacheauth.checked)\n\t\t{\n\t\t\tdocument.getElementById(\"apacheauthinfo\").style.display=\"block\";\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"apacheauthinfo\").style.display=\"none\";\n\t\t}\n\n        }\n\n        function showApacheStatus()\n        {\n\t\tif(document.AMActionForm.serverstatusurl.checked)\n\t\t{\n\t\t\tdocument.getElementById(\"serverstatus\").style.display=\"block\";\n\t\t\t");
/*      */                       
/* 2551 */                       if ((request.getAttribute("serverstatusurl") != null) && (String.valueOf(request.getAttribute("serverstatusurl")).indexOf("http") != -1))
/*      */                       {
/*      */ 
/* 2554 */                         out.write("\n\t\t\t\tdocument.AMActionForm.apacheurl.value='");
/* 2555 */                         out.print(request.getAttribute("serverstatusurl"));
/* 2556 */                         out.write("';\n\t\t\t\t");
/*      */                       }
/*      */                       
/*      */ 
/* 2560 */                       out.write("\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"serverstatus\").style.display=\"none\";\n\t\t}\n\n        }\n</script>\n\n");
/*      */                       
/*      */ 
/* 2563 */                       String resourceName = request.getParameter("resourcename");
/* 2564 */                       String resID = request.getParameter("resourceid");
/* 2565 */                       String apache_id = resID;
/* 2566 */                       request.setAttribute("resourceid", resID);
/* 2567 */                       ArrayList attribIDs = new ArrayList();
/* 2568 */                       ArrayList resIDs = new ArrayList();
/* 2569 */                       resIDs.add(resID);
/* 2570 */                       String applicationName = request.getParameter("name");
/* 2571 */                       String haid = request.getParameter("haid");
/* 2572 */                       String moname = request.getParameter("moname");
/* 2573 */                       ManagedApplication mo = new ManagedApplication();
/* 2574 */                       String port = null;
/* 2575 */                       ArrayList rows1 = mo.getRows("select resourcename,applndiscport  from CollectData where resourcename='" + moname + "'");
/* 2576 */                       if (rows1.size() > 0)
/*      */                       {
/* 2578 */                         rows1 = (ArrayList)rows1.get(0);
/* 2579 */                         port = (String)rows1.get(1);
/*      */                       }
/* 2581 */                       if ((port == null) || (port.equalsIgnoreCase("null")))
/*      */                       {
/* 2583 */                         port = "-";
/*      */                       }
/* 2585 */                       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 2586 */                       ResultSet rs = null;
/* 2587 */                       String resourcetype = request.getParameter("type");
/* 2588 */                       String responsetimeid = "2102";
/* 2589 */                       NumberFormat nf = NumberFormat.getInstance();
/* 2590 */                       nf.setMaximumFractionDigits(3);
/* 2591 */                       String busyserver_id = "2106";
/* 2592 */                       String idleserver_id = "2107";
/* 2593 */                       String bytespersec_id = "2108";
/* 2594 */                       attribIDs.add("2100");
/* 2595 */                       attribIDs.add("2101");
/* 2596 */                       attribIDs.add("2102");
/* 2597 */                       attribIDs.add("2103");
/* 2598 */                       attribIDs.add("2104");
/* 2599 */                       attribIDs.add("2105");
/* 2600 */                       Properties alert = getStatus(resIDs, attribIDs);
/* 2601 */                       HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2602 */                       String encodeurl = URLEncoder.encode("/showresource.do?type=" + resourcetype + "&moname=" + moname + "&method=showdetails&resourcename=" + resourceName + "&resourceid=" + resID + "&haid=" + haid);
/*      */                       
/* 2604 */                       String dispname = (String)motypedisplaynames.get(request.getParameter("type")) + " Details";
/*      */                       
/*      */ 
/* 2607 */                       HashMap apachedetails = (HashMap)request.getAttribute("apachedetails");
/*      */                       
/*      */ 
/* 2610 */                       String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 2611 */                       String yaxis_restime = FormatUtil.getString("am.webclient.common.responsetime.text") + " " + FormatUtil.getString("am.webclient.common.units.ms.text");
/* 2612 */                       String yaxis_reqmin = FormatUtil.getString("am.webclient.apache.RequestperMinute");
/* 2613 */                       String yaxis_busyser = FormatUtil.getString("am.webclient.apache.busyservers");
/* 2614 */                       String yaxis_bytessec = FormatUtil.getString("am.webclient.apache.bytespersecond");
/* 2615 */                       String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2616 */                       String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/*      */                       
/*      */ 
/* 2619 */                       String connectionstatus = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2620 */                       String totalaccesses = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2621 */                       String totalkb = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2622 */                       String cpuload = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2623 */                       String uptime = FormatUtil.getString("am.webclient.common.nodata.text");
/*      */                       
/* 2625 */                       String bytespersec = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2626 */                       String bytesperreq = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2627 */                       String busyservers = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2628 */                       String idleservers = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2629 */                       String responsecode = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2630 */                       String enablestatus = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2631 */                       String collectiontime = "";
/* 2632 */                       String reqpermin = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2633 */                       String totalmb = FormatUtil.getString("am.webclient.common.nodata.text");
/* 2634 */                       connectionstatus = (String)apachedetails.get("connectionstatus");
/* 2635 */                       enablestatus = (String)apachedetails.get("enablestatus");
/*      */                       
/* 2637 */                       if (connectionstatus.equals("0"))
/*      */                       {
/* 2639 */                         if (!enablestatus.equals("2"))
/*      */                         {
/* 2641 */                           if (enablestatus.equals("1"))
/*      */                           {
/* 2643 */                             totalaccesses = (String)apachedetails.get("totalaccesses");
/* 2644 */                             totalkb = (String)apachedetails.get("totalkb") + " " + FormatUtil.getString("KB");
/* 2645 */                             if ((!String.valueOf(apachedetails.get("cpuload")).equals("")) && (!String.valueOf(apachedetails.get("cpuload")).equals("-1.0")))
/*      */                             {
/* 2647 */                               cpuload = (String)apachedetails.get("cpuload");
/*      */                             }
/* 2649 */                             uptime = (String)apachedetails.get("uptime");
/* 2650 */                             reqpermin = (String)apachedetails.get("reqpermin");
/* 2651 */                             bytespersec = (String)apachedetails.get("bytespersec");
/* 2652 */                             bytesperreq = (String)apachedetails.get("bytesperreq");
/*      */                           }
/* 2654 */                           busyservers = (String)apachedetails.get("busyservers");
/* 2655 */                           idleservers = (String)apachedetails.get("idleservers");
/* 2656 */                           responsecode = (String)apachedetails.get("responsecode");
/* 2657 */                           collectiontime = (String)apachedetails.get("collectiontime");
/* 2658 */                           if (!String.valueOf(apachedetails.get("reqpermin")).equals("-1"))
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/* 2663 */                             reqpermin = (String)apachedetails.get("reqpermin");
/*      */                           }
/*      */                           
/*      */ 
/*      */                           try
/*      */                           {
/* 2669 */                             totalmb = String.valueOf(Integer.parseInt(totalkb) / 1024);
/*      */                           }
/*      */                           catch (Exception e) {}
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 2678 */                       if ((bytespersec != null) && (!bytespersec.equals(FormatUtil.getString("am.webclient.common.nodata.text"))))
/*      */                       {
/*      */                         try
/*      */                         {
/* 2682 */                           bytespersec = nf.format(Double.parseDouble(bytespersec));
/*      */                         }
/*      */                         catch (Exception exc1) {}
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 2689 */                       if ((cpuload != null) && (!cpuload.equals(FormatUtil.getString("am.webclient.common.nodata.text"))))
/*      */                       {
/*      */                         try
/*      */                         {
/* 2693 */                           cpuload = nf.format(Double.parseDouble(cpuload));
/*      */                         }
/*      */                         catch (Exception exc2) {}
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 2700 */                       if ((bytesperreq != null) && (!bytesperreq.equals(FormatUtil.getString("am.webclient.common.nodata.text"))))
/*      */                       {
/*      */                         try
/*      */                         {
/* 2704 */                           bytesperreq = nf.format(Double.parseDouble(bytesperreq));
/*      */                         }
/*      */                         catch (Exception exc3) {}
/*      */                       }
/*      */                       
/*      */ 
/* 2710 */                       if ((reqpermin != null) && (!reqpermin.equals(FormatUtil.getString("am.webclient.common.nodata.text"))))
/*      */                       {
/*      */                         try
/*      */                         {
/* 2714 */                           reqpermin = nf.format(Double.parseDouble(reqpermin));
/*      */                         }
/*      */                         catch (Exception exc4) {}
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 2721 */                       out.write(10);
/* 2722 */                       out.write(10);
/* 2723 */                       out.write(9);
/* 2724 */                       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */                         return;
/* 2726 */                       out.write(10);
/*      */                       
/* 2728 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2729 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2730 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2732 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2733 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2734 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2736 */                           out.write(32);
/*      */                           
/* 2738 */                           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2739 */                           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2740 */                           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2742 */                           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                           
/* 2744 */                           _jspx_th_tiles_005fput_005f0.setValue(dispname);
/* 2745 */                           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2746 */                           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2747 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                           }
/*      */                           
/* 2750 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2751 */                           out.write(10);
/* 2752 */                           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2754 */                           out.write(10);
/* 2755 */                           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2757 */                           out.write(10);
/* 2758 */                           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2760 */                           out.write(10);
/*      */                           
/* 2762 */                           PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2763 */                           _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2764 */                           _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2766 */                           _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                           
/* 2768 */                           _jspx_th_tiles_005fput_005f4.setType("string");
/* 2769 */                           int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2770 */                           if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2771 */                             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2772 */                               out = _jspx_page_context.pushBody();
/* 2773 */                               _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 2774 */                               _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2777 */                               out.write(10);
/*      */                               
/*      */ 
/* 2780 */                               String tipDB = FormatUtil.getString("am.webclient.availabilityperf.tooltip");
/* 2781 */                               String tipCurStatus = FormatUtil.getString("am.webclient.availabilityperf.curstatus.tooltip");
/* 2782 */                               String tipCurValue = FormatUtil.getString("am.webclient.availabilityperf.curvalue.tooltip");
/* 2783 */                               String tipCurThr = FormatUtil.getString("am.webclient.availabilityperf.curthres.tooltip");
/*      */                               
/*      */ 
/* 2786 */                               out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n\t");
/*      */                               
/* 2788 */                               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2789 */                               String aid = request.getParameter("haid");
/* 2790 */                               String haName = null;
/* 2791 */                               if (aid != null)
/*      */                               {
/* 2793 */                                 haName = (String)ht.get(aid);
/*      */                               }
/*      */                               
/* 2796 */                               out.write(10);
/* 2797 */                               out.write(9);
/*      */                               
/* 2799 */                               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2800 */                               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2801 */                               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2803 */                               _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2804 */                               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2805 */                               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                 for (;;) {
/* 2807 */                                   out.write("\n        <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2808 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2809 */                                   out.write(" &gt; ");
/* 2810 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2811 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 2812 */                                   out.print(resourceName);
/* 2813 */                                   out.write(" </span></td>\n\t");
/* 2814 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2815 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2819 */                               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2820 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                               }
/*      */                               
/* 2823 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2824 */                               out.write(10);
/* 2825 */                               out.write(9);
/*      */                               
/* 2827 */                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2828 */                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2829 */                               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2831 */                               _jspx_th_c_005fif_005f3.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2832 */                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2833 */                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                 for (;;) {
/* 2835 */                                   out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2836 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2837 */                                   out.write(" &gt; ");
/* 2838 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes(request.getParameter("type")));
/* 2839 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 2840 */                                   out.print(resourceName);
/* 2841 */                                   out.write("</span></td>\n\t");
/* 2842 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2843 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2847 */                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2848 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                               }
/*      */                               
/* 2851 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2852 */                               out.write("\n    </tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n<div id=\"edit\" style=\"DISPLAY: none\">\n\n\n");
/*      */                               
/* 2854 */                               String type = request.getParameter("type");
/*      */                               
/*      */ 
/* 2857 */                               String smtpPort = request.getParameter("smtpPort");
/*      */                               
/* 2859 */                               if (smtpPort == null)
/* 2860 */                                 smtpPort = "";
/* 2861 */                               String pollInterval = request.getParameter("pollInterval");
/* 2862 */                               if (pollInterval == null) {
/* 2863 */                                 pollInterval = "5";
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 2868 */                               out.write(10);
/* 2869 */                               out.write(10);
/*      */                               
/* 2871 */                               org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2872 */                               token.saveToken(request);
/*      */                               
/*      */ 
/* 2875 */                               out.write("\n\n\n\n\n  ");
/*      */                               
/* 2877 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2878 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2879 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 2881 */                               _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                               
/* 2883 */                               _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                               
/* 2885 */                               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2886 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2887 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2889 */                                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n    <td height=\"28\"   class=\"tableheading\">");
/* 2890 */                                   out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 2891 */                                   out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2892 */                                   out.print(request.getParameter("name"));
/* 2893 */                                   out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2894 */                                   out.print(request.getParameter("haid"));
/* 2895 */                                   out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2896 */                                   out.print(type);
/* 2897 */                                   out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 2898 */                                   out.print(type);
/* 2899 */                                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureApache\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2900 */                                   out.print(request.getParameter("resourceid"));
/* 2901 */                                   out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2902 */                                   out.print(request.getParameter("resourcename"));
/* 2903 */                                   out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 2904 */                                   out.print(request.getParameter("moname"));
/* 2905 */                                   out.write("\">\n\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n<span class=\"bodytextboldwhiteun\" ><a href=\"javascript:hideDiv('edit')\" class=\"staticlinks\">");
/* 2906 */                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2907 */                                   out.write("</a></span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n\t<TR>\n        <TD height=\"28\" width=\"31%\" class=\"bodytext\">");
/* 2908 */                                   out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2909 */                                   out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\">\n\t");
/* 2910 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2912 */                                   out.write("\n\t</TD>\n\t</TR>\n        <TR>\n\t           <TD height=\"28\" class=\"bodytext\" >&nbsp;</TD>\n\t           ");
/*      */                                   
/* 2914 */                                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2915 */                                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2916 */                                   _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2918 */                                   _jspx_th_c_005fif_005f4.setTest("${sslenabled_val}");
/* 2919 */                                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2920 */                                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                     for (;;) {
/* 2922 */                                       out.write("\n                    <TD height=\"28\" > ");
/* 2923 */                                       if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                         return;
/* 2925 */                                       out.write("<span class=\"bodytext\"> ");
/* 2926 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssl"));
/* 2927 */                                       out.write(" </span>\n\t           ");
/* 2928 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2929 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2933 */                                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2934 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                   }
/*      */                                   
/* 2937 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2938 */                                   out.write("\n\t           ");
/*      */                                   
/* 2940 */                                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2941 */                                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2942 */                                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2944 */                                   _jspx_th_c_005fif_005f5.setTest("${!sslenabled_val}");
/* 2945 */                                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2946 */                                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                     for (;;) {
/* 2948 */                                       out.write("\n                    <TD height=\"28\" colspan=\"2\" > ");
/* 2949 */                                       if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                         return;
/* 2951 */                                       out.write("<span class=\"bodytext\">");
/* 2952 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssl"));
/* 2953 */                                       out.write(" </span>\n\t           ");
/* 2954 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2955 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2959 */                                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2960 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                   }
/*      */                                   
/* 2963 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2964 */                                   out.write("\n\t           </TD>\n\t</TR>\n\t<TR>\n        <TD height=\"28\" class=\"bodytext\">");
/* 2965 */                                   out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2966 */                                   out.write("<span class=\"mandatory\">*</span></TD>\n        <TD height=\"28\"> <INPUT NAME=\"pollInterval\" TYPE=\"text\" class=\"formtext\" VALUE=\"");
/* 2967 */                                   out.print(pollInterval);
/* 2968 */                                   out.write("\" SIZE=\"15\"><span class=\"bodytext\">&nbsp;");
/* 2969 */                                   out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 2970 */                                   out.write("</span>\n\t</TD>\n\t</TR>\n\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrborder\">\n\t\t<TR>\n\t\t<TD height=\"28\" class=\"bodytext\" colspan=\"3\">");
/* 2971 */                                   if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2973 */                                   out.write(32);
/* 2974 */                                   out.print(FormatUtil.getString("am.webclient.apache.authentication.required"));
/* 2975 */                                   out.write("\n\t\t<div id=\"apacheauthinfo\"   ");
/* 2976 */                                   if (_jspx_meth_c_005fif_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2978 */                                   if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2980 */                                   out.write(">\n\t\t<table border=\"0\" width=\"100%\">\n\t\t<tr>\n\t\t<td  colspan=\"3\" align=\"left\" width=\"100%\" height=\"1\"><br></td>\n\t\t</tr>\n\t\t</table>\n\n      <TABLE  BORDER=\"0\" CELLSPACING=2 CELLPADDING=3 WIDTH=\"100%\" align=\"left\">\n        <TR>\n          <TD class=\"bodytext\" height=\"28\" width=\"25%\">");
/* 2981 */                                   out.print(FormatUtil.getString("am.webclient.apache.username.text"));
/* 2982 */                                   out.write("\n          </TD>\n          <TD width=\"57%\"> ");
/* 2983 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2985 */                                   out.write("\n          </TD>\n        </tr>\n        <TR>\n          <TD class=\"bodytext\" width=\"25%\">");
/* 2986 */                                   out.print(FormatUtil.getString("am.webclient.apache.password.text"));
/* 2987 */                                   out.write("</TD>\n          <TD> ");
/* 2988 */                                   if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2990 */                                   out.write("\n          </TD>\n        </tr>\n      </TABLE>\n\n\t\t</div>\n\n\t\t</TR>\n                <tr>\n\t\t<td colspan=\"3\" height=\"2\"></td>\n\t\t</tr>\n                <TR>\n\t\t<TD height=\"28\" class=\"bodytext\" colspan=\"3\">");
/* 2991 */                                   if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2993 */                                   out.write(32);
/* 2994 */                                   out.print(FormatUtil.getString("am.webclient.apache.specifyserverstatusurl"));
/* 2995 */                                   out.write("\n\t\t<div id=\"serverstatus\"   ");
/* 2996 */                                   if (_jspx_meth_c_005fif_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2998 */                                   out.write(">\n                <table border=\"0\" width=\"100%\">\n\t\t<tr>\n\t\t<td colspan=\"3\" align=\"left\" width=\"100%\" height=\"1\">&nbsp;</td>\n\t\t</tr>\n\t\t</table>\n\n      <TABLE  BORDER=\"0\" CELLSPACING=2 CELLPADDING=3 WIDTH=\"100%\" align=\"left\">\n        <TR>\n\n\t\t  <TD class=\"bodytext\" height=\"28\" width=\"25%\">");
/* 2999 */                                   out.print(FormatUtil.getString("am.webclient.apache.statusurl"));
/* 3000 */                                   out.write("\n          </TD>\n\t\t  <TD width=\"57%\"> ");
/* 3001 */                                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 3003 */                                   out.write("\n          </TD>\n\t\t</tr>\n                </table>\n\t\t</div>\n\n\t\t</TR>\n\t\t</table>\n");
/*      */                                   
/* 3005 */                                   String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                   
/* 3007 */                                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"13%\" class=\"tablebottom1\">&nbsp;</td>\n    <td width=\"87%\" class=\"tablebottom1\">\n\n    <input name=\"but1\" type=\"button\"  class=\"buttons btn_highlt\" value=\"");
/*      */                                   
/* 3009 */                                   IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3010 */                                   _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3011 */                                   _jspx_th_c_005fif_005f9.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 3013 */                                   _jspx_th_c_005fif_005f9.setTest("${empty enabled}");
/* 3014 */                                   int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3015 */                                   if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                     for (;;) {
/* 3017 */                                       out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 3018 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3019 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3023 */                                   if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3024 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                   }
/*      */                                   
/* 3027 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*      */                                   
/* 3029 */                                   IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3030 */                                   _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3031 */                                   _jspx_th_c_005fif_005f10.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 3033 */                                   _jspx_th_c_005fif_005f10.setTest("${!empty enabled}");
/* 3034 */                                   int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3035 */                                   if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                     for (;;) {
/* 3037 */                                       out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 3038 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3039 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3043 */                                   if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3044 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                   }
/*      */                                   
/* 3047 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3048 */                                   out.write("\" onClick=\"javascript:submitForm()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_reset\" value=\"");
/* 3049 */                                   out.print(cancel);
/* 3050 */                                   out.write("\" onClick=\"javascript:toggleDiv('edit')\" />\n     </td>\n  </tr>\n</table>\n");
/* 3051 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3052 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3056 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3057 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 3060 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3061 */                               out.write("\n\n<br>\n</div>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n    <td width=\"573\" height=\"123\" valign=\"top\"> <table width=\"96%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n        <tr>\n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 3062 */                               out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 3063 */                               out.write(" </td>\n        </tr>\n        <tr>\n          <td width=\"32%\" class=\"monitorinfoodd\">");
/* 3064 */                               out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3065 */                               out.write(" </td>\n          <td width=\"68%\" class=\"monitorinfoodd\"> ");
/* 3066 */                               out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 3067 */                               out.write("\n          </td>\n        </tr>\n\t\t");
/* 3068 */                               out.write("<!--$Id$-->\n");
/*      */                               
/* 3070 */                               String hostName = "localhost";
/*      */                               try {
/* 3072 */                                 hostName = InetAddress.getLocalHost().getHostName();
/*      */                               } catch (Exception ex) {
/* 3074 */                                 ex.printStackTrace();
/*      */                               }
/* 3076 */                               String portNumber = System.getProperty("webserver.port");
/* 3077 */                               String styleClass = "monitorinfoodd";
/* 3078 */                               if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 3079 */                                 styleClass = "whitegrayborder-conf-mon";
/*      */                               }
/*      */                               
/* 3082 */                               out.write(10);
/*      */                               
/* 3084 */                               PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3085 */                               _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3086 */                               _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3088 */                               _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 3089 */                               int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3090 */                               if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                 for (;;) {
/* 3092 */                                   out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 3093 */                                   out.print(styleClass);
/* 3094 */                                   out.write(34);
/* 3095 */                                   out.write(62);
/* 3096 */                                   out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 3097 */                                   out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 3098 */                                   out.print(styleClass);
/* 3099 */                                   out.write(34);
/* 3100 */                                   out.write(62);
/* 3101 */                                   out.print(hostName);
/* 3102 */                                   out.write(95);
/* 3103 */                                   out.print(portNumber);
/* 3104 */                                   out.write("</td>\n</tr>\n");
/* 3105 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3106 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3110 */                               if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3111 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                               }
/*      */                               
/* 3114 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3115 */                               out.write(10);
/* 3116 */                               out.write("\n        <tr>\n          <td class=\"monitorinfoeven\" valign=\"top\">");
/* 3117 */                               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3118 */                               out.write("</td>\n\n          <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3119 */                               out.print(resID);
/* 3120 */                               out.write("&attributeid=");
/* 3121 */                               out.print((String)systeminfo.get("HEALTHID"));
/* 3122 */                               out.write("')\">");
/* 3123 */                               out.print(getSeverityImageForHealth(alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"))));
/* 3124 */                               out.write("</a>\n\t\t  ");
/* 3125 */                               out.print(getHideAndShowRCAMessage(alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID") + "#" + "MESSAGE"), (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"), alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID")), resID));
/* 3126 */                               out.write("\n\t\t  ");
/* 3127 */                               if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resID, (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID")) != 0) {
/* 3128 */                                 out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 3129 */                                 out.print(resID + "_" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"));
/* 3130 */                                 out.write("&monitortype=");
/* 3131 */                                 out.print(resourcetype);
/* 3132 */                                 out.write("')\">");
/* 3133 */                                 out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 3134 */                                 out.write("</a></span>\n           ");
/*      */                               }
/* 3136 */                               out.write("\n\t\t  </td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3137 */                               out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 3138 */                               out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3139 */                               out.print(FormatUtil.getString("Apache Server"));
/* 3140 */                               out.write("</td>\n        </tr>\n\n\t<tr>\n\t<td class=\"monitorinfoodd\">");
/* 3141 */                               out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 3142 */                               out.write("</td>\n\t<td class=\"monitorinfoodd\">");
/* 3143 */                               out.print(port);
/* 3144 */                               out.write("\n        </td>\n\t</tr>\n\n        ");
/*      */                               
/* 3146 */                               EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3147 */                               _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 3148 */                               _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3150 */                               _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 3151 */                               int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 3152 */                               if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                                 for (;;) {
/* 3154 */                                   out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3155 */                                   out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3156 */                                   out.write("</td>\n          <td class=\"monitorinfoeven\">-&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3157 */                                   out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3158 */                                   out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3159 */                                   out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3160 */                                   out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3161 */                                   out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3162 */                                   out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        ");
/* 3163 */                                   int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3164 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3168 */                               if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3169 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                               }
/*      */                               
/* 3172 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3173 */                               out.write(32);
/*      */                               
/* 3175 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3176 */                               _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3177 */                               _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3179 */                               _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 3180 */                               int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3181 */                               if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                 for (;;) {
/* 3183 */                                   out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3184 */                                   out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3185 */                                   out.write("</td>\n          ");
/*      */                                   
/* 3187 */                                   if (systeminfo.get("host_resid") != null)
/*      */                                   {
/* 3189 */                                     out.write("\n\t\t    <td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 3190 */                                     out.print(systeminfo.get("host_resid"));
/* 3191 */                                     out.write("&method=showResourceForResourceID\" class=\"staticlinks\" title=\"");
/* 3192 */                                     out.print(systeminfo.get("HOSTNAME"));
/* 3193 */                                     out.write(34);
/* 3194 */                                     out.write(32);
/* 3195 */                                     out.write(62);
/* 3196 */                                     out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3197 */                                     out.write("&nbsp;(");
/* 3198 */                                     out.print(systeminfo.get("HOSTIP"));
/* 3199 */                                     out.write(")</a></td>\n\t\t\t");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3204 */                                     out.write("\n             <td class=\"monitorinfoeven\" title=\"");
/* 3205 */                                     out.print(systeminfo.get("HOSTNAME"));
/* 3206 */                                     out.write(34);
/* 3207 */                                     out.write(32);
/* 3208 */                                     out.write(62);
/* 3209 */                                     out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3210 */                                     out.write("&nbsp;(");
/* 3211 */                                     out.print(systeminfo.get("HOSTIP"));
/* 3212 */                                     out.write(")</td>\n\t\t\t");
/*      */                                   }
/* 3214 */                                   out.write("\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3215 */                                   out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3216 */                                   out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3217 */                                   out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 3218 */                                   out.write("</td>\n        </tr>\n\t<tr>\n\t<td class=\"monitorinfoodd\">");
/* 3219 */                                   out.print(FormatUtil.getString("am.webclient.apache.totalaccess"));
/* 3220 */                                   out.write("</td>\n\t<td class=\"monitorinfoodd\">");
/* 3221 */                                   out.print(totalaccesses);
/* 3222 */                                   out.write("\n        </td>\n\t</tr>\n\t<tr>\n\t<td class=\"monitorinfoeven\">");
/* 3223 */                                   out.print(FormatUtil.getString("am.webclient.apache.totalkb"));
/* 3224 */                                   out.write("</td>\n\t<td class=\"monitorinfoeven\">");
/* 3225 */                                   out.print(totalkb);
/* 3226 */                                   out.write("&nbsp;\n\t</td>\n\t</tr>\n\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3227 */                                   out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3228 */                                   out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3229 */                                   out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3230 */                                   out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3231 */                                   out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3232 */                                   out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 3233 */                                   out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3234 */                                   out.write("</td>\n        </tr>\n\t");
/* 3235 */                                   out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 3236 */                                   out.write("\n\t{\n\t\t");
/* 3237 */                                   if (_jspx_meth_c_005fif_005f11(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3239 */                                   out.write(10);
/* 3240 */                                   out.write(9);
/* 3241 */                                   out.write(9);
/* 3242 */                                   if (_jspx_meth_c_005fif_005f12(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3244 */                                   out.write("\n\t\tgetCustomFields('");
/* 3245 */                                   if (_jspx_meth_c_005fout_005f2(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3247 */                                   out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 3248 */                                   out.write("\n\t}\n\n});\n</script>\n");
/* 3249 */                                   if (_jspx_meth_c_005fif_005f13(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3251 */                                   out.write(10);
/* 3252 */                                   out.write(10);
/* 3253 */                                   if (_jspx_meth_c_005fif_005f14(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3255 */                                   out.write(10);
/* 3256 */                                   out.write(10);
/* 3257 */                                   if (_jspx_meth_c_005fset_005f4(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3259 */                                   out.write(10);
/* 3260 */                                   if (_jspx_meth_c_005fset_005f5(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3262 */                                   out.write(10);
/* 3263 */                                   out.write(10);
/* 3264 */                                   out.write(10);
/* 3265 */                                   if (_jspx_meth_c_005fif_005f15(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3267 */                                   out.write(10);
/* 3268 */                                   out.write(10);
/* 3269 */                                   out.write(10);
/* 3270 */                                   if (_jspx_meth_c_005fif_005f16(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3272 */                                   out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 3273 */                                   if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3275 */                                   out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 3276 */                                   if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3278 */                                   out.write("\" onclick=\"getCustomFields('");
/* 3279 */                                   if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3281 */                                   out.write(39);
/* 3282 */                                   out.write(44);
/* 3283 */                                   out.write(39);
/* 3284 */                                   if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                     return;
/* 3286 */                                   out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 3287 */                                   out.write("\n</td>\n</tr>\n\n\n");
/* 3288 */                                   out.write("\n        ");
/* 3289 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3290 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3294 */                               if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3295 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                               }
/*      */                               
/* 3298 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3299 */                               out.write(" </table></td>\n    <td width=\"355\" valign=\"top\"><table align=left width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n          <td height=\"28\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 3300 */                               out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3301 */                               out.write("</td>\n        </tr>\n        <tr>\n          <td colspan=\"3\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"right\">\n              <tr>\n                <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3302 */                               if (_jspx_meth_c_005fout_005f12(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3304 */                               out.write("&period=1&resourcename=");
/* 3305 */                               if (_jspx_meth_c_005fout_005f13(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3307 */                               out.write("')\">\n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3308 */                               out.print(seven_days_text);
/* 3309 */                               out.write("\"></a>\n               <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3310 */                               if (_jspx_meth_c_005fout_005f14(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3312 */                               out.write("&period=2&resourcename=");
/* 3313 */                               if (_jspx_meth_c_005fout_005f15(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3315 */                               out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3316 */                               out.print(thiry_days_text);
/* 3317 */                               out.write("\"></a></td>\n              </tr>\n            </table></td>\n          ");
/*      */                               
/* 3319 */                               wlsGraph.setParam(resID, "AVAILABILITY");
/*      */                               
/* 3321 */                               out.write("\n        </tr>\n        <tr align=\"center\">\n          <td height=\"28\" colspan=\"3\" > ");
/* 3322 */                               if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3324 */                               out.write("</td>\n        </tr>\n\t\t\t<tr>\n\t\t\t<td width=\"49%\" height=\"25\" colspan=\"2\" class=\"bodytext\"  title=\"");
/* 3325 */                               out.print(tipCurStatus);
/* 3326 */                               out.write("\">&nbsp; ");
/* 3327 */                               out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3328 */                               out.write(" : <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3329 */                               out.print(resID);
/* 3330 */                               out.write("&attributeid=");
/* 3331 */                               out.print(availabilitykeys.get(resourcetype));
/* 3332 */                               out.write("&alertconfigurl=");
/* 3333 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "&attributeIDs=" + availabilitykeys.get(resourcetype) + "&attributeToSelect=" + availabilitykeys.get(resourcetype) + "&redirectto=" + encodeurl));
/* 3334 */                               out.write("')\">\n\t\t\t");
/* 3335 */                               out.print(getSeverityImageForAvailability(alert.getProperty(resID + "#" + "2100")));
/* 3336 */                               out.write("\n\t\t\t</a></td>\n\n\t\t\t<td width=\"51%\" align=\"right\" class=\"bodytext\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3337 */                               out.print(resID);
/* 3338 */                               out.write("&attributeIDs=");
/* 3339 */                               out.print(availabilitykeys.get(resourcetype));
/* 3340 */                               out.write(44);
/* 3341 */                               out.print(healthkeys.get(resourcetype));
/* 3342 */                               out.write("&attributeToSelect=");
/* 3343 */                               out.print(availabilitykeys.get(resourcetype));
/* 3344 */                               out.write("&redirectto=");
/* 3345 */                               out.print(encodeurl);
/* 3346 */                               out.write("\" class=\"links\">");
/* 3347 */                               out.print(ALERTCONFIG_TEXT);
/* 3348 */                               out.write("</a>&nbsp;\n\t\t\t</td>\n\t\t\t</tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3349 */                               out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 3350 */                               out.write("</td></tr></table>\n\n<br>\n ");
/*      */                               
/*      */ 
/* 3353 */                               long maxtime = wlsGraph.getLastDataCollectedTime();
/* 3354 */                               long curvalue = 0L;
/* 3355 */                               int health = -1;
/*      */                               
/*      */ 
/* 3358 */                               String dataquery = com.adventnet.appmanager.db.DBQueryUtil.getTopNValues("select RESPONSETIME,HEALTH from AM_ManagedObjectData where AM_ManagedObjectData.RESID=" + resID + " order by collectiontime desc", "1");
/* 3359 */                               rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 3360 */                               if (rs.next())
/*      */                               {
/* 3362 */                                 curvalue = rs.getLong(1);
/* 3363 */                                 health = rs.getInt(2);
/*      */                               }
/* 3365 */                               rs.close();
/*      */                               
/* 3367 */                               if (enablestatus.equals("2"))
/*      */                               {
/* 3369 */                                 wlsGraph.setParam(resID, "RESPONSETIME");
/*      */                                 
/* 3371 */                                 out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 3372 */                                 out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3373 */                                 out.write(32);
/* 3374 */                                 out.write(45);
/* 3375 */                                 out.write(32);
/* 3376 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3377 */                                 out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n    <td width=\"405\" height=\"127\" valign=\"top\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n        <tr>\n\t\t          <td width=\"100%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3378 */                                 if (_jspx_meth_c_005fout_005f16(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3380 */                                 out.write("&attributeid=");
/* 3381 */                                 out.print(responsetimeid);
/* 3382 */                                 out.write("&period=-7',740,550)\">\n\t\t            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3383 */                                 out.print(seven_days_text);
/* 3384 */                                 out.write("\"></a>\n\t\t         <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3385 */                                 if (_jspx_meth_c_005fout_005f17(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3387 */                                 out.write("&attributeid=");
/* 3388 */                                 out.print(responsetimeid);
/* 3389 */                                 out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3390 */                                 out.print(thiry_days_text);
/* 3391 */                                 out.write("\"></a></td>\n        </tr>\n        <tr>\n          <td colspan=\"2\"> ");
/*      */                                 
/* 3393 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3394 */                                 _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3395 */                                 _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3397 */                                 _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3399 */                                 _jspx_th_awolf_005ftimechart_005f0.setWidth("320");
/*      */                                 
/* 3401 */                                 _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                                 
/* 3403 */                                 _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                                 
/* 3405 */                                 _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                                 
/* 3407 */                                 _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_restime);
/*      */                                 
/* 3409 */                                 _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 3410 */                                 int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3411 */                                 if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3412 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3413 */                                     out = _jspx_page_context.pushBody();
/* 3414 */                                     _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3415 */                                     _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3418 */                                     out.write("\n            ");
/* 3419 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3420 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3423 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3424 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3427 */                                 if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3428 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                 }
/*      */                                 
/* 3431 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3432 */                                 out.write(" </td>\n        </tr>\n      </table></td>\n    <td width=\"562\" valign=\"top\"> <br> <br>\n\n      <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3433 */                                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3435 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3436 */                                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3438 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">\n          ");
/*      */                                 
/* 3440 */                                 if (curvalue != -1L)
/*      */                                 {
/* 3442 */                                   out.write("\n          ");
/* 3443 */                                   if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                     return;
/* 3445 */                                   out.write("</span>\n          ");
/*      */                                 } else {
/* 3447 */                                   out.println("&nbsp;");
/*      */                                 }
/* 3449 */                                 out.write("\n          </td>\n        </tr>\n        <tr>\n\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3450 */                                 out.print(tipCurValue);
/* 3451 */                                 out.write(34);
/* 3452 */                                 out.write(62);
/* 3453 */                                 out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 3454 */                                 out.write(32);
/* 3455 */                                 out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3456 */                                 out.write("</td>\n          ");
/*      */                                 
/* 3458 */                                 if (curvalue != -1L)
/*      */                                 {
/*      */ 
/* 3461 */                                   out.write("\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 3462 */                                   out.print(formatNumber(curvalue));
/* 3463 */                                   out.write(32);
/* 3464 */                                   out.print(FormatUtil.getString("ms"));
/* 3465 */                                   out.write("</td>\n\n            ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3471 */                                   out.write("\n          <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 3472 */                                   out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3473 */                                   out.write(" </td>\n            ");
/*      */                                 }
/*      */                                 
/* 3476 */                                 out.write(10);
/* 3477 */                                 out.write(9);
/* 3478 */                                 out.write(32);
/*      */                                 
/* 3480 */                                 if (curvalue != -1L)
/*      */                                 {
/*      */ 
/* 3483 */                                   out.write("\n          <Td width=\"16%\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3484 */                                   out.print(resID);
/* 3485 */                                   out.write("&attributeid=");
/* 3486 */                                   out.print(responsetimeid);
/* 3487 */                                   out.write("&alertconfigurl=");
/* 3488 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + responsetimeid + "attributeToSelect=" + responsetimeid + "&redirectto=" + encodeurl));
/* 3489 */                                   out.write("')\">");
/* 3490 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + responsetimeid)));
/* 3491 */                                   out.write(" </a>\n          </td>\n          ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3495 */                                 out.write("\n        </tr>\n        <tr>\n          <td  colspan=\"4\" height=\"35\" class=\"yellowgrayborder\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3496 */                                 out.print(resID);
/* 3497 */                                 out.write("&attributeIDs=");
/* 3498 */                                 out.print(responsetimeid);
/* 3499 */                                 out.write("&attributeToSelect=");
/* 3500 */                                 out.print(responsetimeid);
/* 3501 */                                 out.write("&redirectto=");
/* 3502 */                                 out.print(encodeurl);
/* 3503 */                                 out.write("\" class=\"staticlinks\">");
/* 3504 */                                 out.print(ALERTCONFIG_TEXT);
/* 3505 */                                 out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td height=\"26\" class=\"tablebottom1\">&nbsp;</td>\n  </tr>\n</table>\n\n<br>\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\" class=\"lrbtborder\">\n<tr>\n    <td class=\"yellowgrayborder\"> &nbsp;&nbsp;");
/* 3506 */                                 out.print(FormatUtil.getString("am.webclient.apache.clicktoknow.text", new String[] { FormatUtil.getString("am.webclient.contexthelplink.text") }));
/* 3507 */                                 out.write("<a href=\"/help/monitors/webservices-\nmonitoring.html#apache\" target=\"_blank\" class=\"selectedmenu\"><img src=\"/images/icon_quicknote_help.gif\"\nhspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">");
/* 3508 */                                 out.print(FormatUtil.getString("am.webclient.contexthelplink.text"));
/* 3509 */                                 out.write(" >></a> </td>\n</tr>\n</table>\n<br>\n\n\n\n");
/*      */                               }
/*      */                               else
/*      */                               {
/* 3513 */                                 wlsGraph.setParam(resID, "RESPONSETIME");
/*      */                                 
/* 3515 */                                 out.write("\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbtborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 3516 */                                 out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3517 */                                 out.write(32);
/* 3518 */                                 out.write(45);
/* 3519 */                                 out.write(32);
/* 3520 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3521 */                                 out.write("</td>\n    <td height=\"26\" class=\"tableheading\">");
/* 3522 */                                 out.print(FormatUtil.getString("am.webclient.apache.requestdetails"));
/* 3523 */                                 out.write(32);
/* 3524 */                                 out.write(45);
/* 3525 */                                 out.write(32);
/* 3526 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3527 */                                 out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrborder\">\n<tr>\n<!--td width=\"50%\" height=\"25\" class=\"yellowgrayborder\" -->\n<td width=\"49%\" height=\"25\"  valign=\"top\">\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=rborder>\n<tr>\n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3528 */                                 if (_jspx_meth_c_005fout_005f18(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3530 */                                 out.write("&attributeid=");
/* 3531 */                                 out.print(responsetimeid);
/* 3532 */                                 out.write("&period=-7',740,550)\">\n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3533 */                                 out.print(seven_days_text);
/* 3534 */                                 out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3535 */                                 if (_jspx_meth_c_005fout_005f19(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3537 */                                 out.write("&attributeid=");
/* 3538 */                                 out.print(responsetimeid);
/* 3539 */                                 out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3540 */                                 out.print(thiry_days_text);
/* 3541 */                                 out.write("\"></a></td>\n        </tr>\n<tr>\n <td width=\"300\" height=\"127\" valign=\"top\" colspan=2> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"50%\">\n        <tr>\n        ");
/*      */                                 
/*      */ 
/*      */ 
/* 3545 */                                 out.write("\n\n          <td colspan=\"2\"> ");
/*      */                                 
/* 3547 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3548 */                                 _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3549 */                                 _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3551 */                                 _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3553 */                                 _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                                 
/* 3555 */                                 _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                                 
/* 3557 */                                 _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                                 
/* 3559 */                                 _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                                 
/* 3561 */                                 _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(yaxis_restime);
/*      */                                 
/* 3563 */                                 _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 3564 */                                 int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3565 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3566 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3567 */                                     out = _jspx_page_context.pushBody();
/* 3568 */                                     _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3569 */                                     _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3572 */                                     out.write("\n            ");
/* 3573 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3574 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3577 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3578 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3581 */                                 if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3582 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                 }
/*      */                                 
/* 3585 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3586 */                                 out.write(" </td>\n        </tr>\n      </table></td>\n</tr>\n<tr>\n     <td  align=left colspan=2 valign=\"top\">\n      <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3587 */                                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3589 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3590 */                                 if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3592 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">\n          ");
/*      */                                 
/* 3594 */                                 if (curvalue != -1L)
/*      */                                 {
/* 3596 */                                   out.write("\n          ");
/* 3597 */                                   if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                     return;
/* 3599 */                                   out.write("</span>\n          ");
/*      */                                 } else {
/* 3601 */                                   out.println("&nbsp;");
/*      */                                 }
/* 3603 */                                 out.write("\n          </td>\n          </tr>\n        <tr>\n\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3604 */                                 out.print(tipCurValue);
/* 3605 */                                 out.write(34);
/* 3606 */                                 out.write(62);
/* 3607 */                                 out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3608 */                                 out.write("</td>\n\n          ");
/*      */                                 
/* 3610 */                                 if (curvalue != -1L)
/*      */                                 {
/*      */ 
/* 3613 */                                   out.write("\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 3614 */                                   out.print(formatNumber(curvalue));
/* 3615 */                                   out.write(32);
/* 3616 */                                   out.print(FormatUtil.getString("ms"));
/* 3617 */                                   out.write("</td>\n\n            ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3623 */                                   out.write("\n          <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 3624 */                                   out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3625 */                                   out.write("</td>\n            ");
/*      */                                 }
/*      */                                 
/* 3628 */                                 out.write("\n     ");
/*      */                                 
/* 3630 */                                 if (curvalue != -1L)
/*      */                                 {
/*      */ 
/* 3633 */                                   out.write("\n              <td width=\"16%\" align=\"center\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3634 */                                   out.print(resID);
/* 3635 */                                   out.write("&attributeid=");
/* 3636 */                                   out.print(responsetimeid);
/* 3637 */                                   out.write("&alertconfigurl=");
/* 3638 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + responsetimeid + "attributeToSelect=" + responsetimeid + "&redirectto=" + encodeurl));
/* 3639 */                                   out.write("')\">");
/* 3640 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + responsetimeid)));
/* 3641 */                                   out.write(" </a>\n          </td>\n          ");
/*      */                                 }
/*      */                                 
/* 3644 */                                 tipCurValue = FormatUtil.getString("am.webclient.apache.tooltip.cpuload");
/*      */                                 
/* 3646 */                                 out.write("\n        </tr>\n\n       <tr>\n          <td  height=\"25\" class=\"yellowgrayborder\" title=\"");
/* 3647 */                                 out.print(tipCurValue);
/* 3648 */                                 out.write(34);
/* 3649 */                                 out.write(62);
/* 3650 */                                 out.print(FormatUtil.getString("am.webclient.apache.cpuload"));
/* 3651 */                                 out.write("</td>\n          <td  height=\"25\" class=\"yellowgrayborder\">");
/* 3652 */                                 out.print(formatNumber(cpuload));
/* 3653 */                                 out.write(32);
/* 3654 */                                 if ((cpuload != null) && (!cpuload.equals(FormatUtil.getString("am.webclient.common.nodata.text")))) out.println("%");
/* 3655 */                                 out.write("</td>\n                <td width=\"16%\" class=\"yellowgrayborder\" align=\"center\"> &nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3656 */                                 out.print(resID);
/* 3657 */                                 out.write("&attributeid=2103&alertconfigurl=");
/* 3658 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs2103attributeToSelect=2103&redirectto=" + encodeurl));
/* 3659 */                                 out.write("')\">");
/* 3660 */                                 out.print(getSeverityImage(alert.getProperty(resID + "#" + "2103")));
/* 3661 */                                 out.write("\n                  </a></td>\n       </tr>\n       <tr>\n                <td colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\" >\n                  <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3662 */                                 out.print(resID);
/* 3663 */                                 out.write("&attributeIDs=2102,2103&attributeToSelect=2102&redirectto=");
/* 3664 */                                 out.print(encodeurl);
/* 3665 */                                 out.write("\" class=\"staticlinks\">");
/* 3666 */                                 out.print(ALERTCONFIG_TEXT);
/* 3667 */                                 out.write("</a>&nbsp;</td>\n       </tr>\n      </table>\n    </td>\n</tr>\n</table>\n</td>\n\n\n");
/*      */                                 
/* 3669 */                                 wlsGraph.setParam(resID, "REQPERMIN");
/* 3670 */                                 tipCurValue = FormatUtil.getString("am.webclient.apache.tooltip.norperminute");
/*      */                                 
/* 3672 */                                 out.write("\n\n\n<td width=\"50%\" height=\"25\"  >\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3673 */                                 if (_jspx_meth_c_005fout_005f20(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3675 */                                 out.write("&attributeid=2104&period=-7',740,550)\">\n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3676 */                                 out.print(seven_days_text);
/* 3677 */                                 out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3678 */                                 if (_jspx_meth_c_005fout_005f21(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3680 */                                 out.write("&attributeid=2104&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3681 */                                 out.print(thiry_days_text);
/* 3682 */                                 out.write("\"></a></td>\n</tr>\n\n<tr>\n\n <td width=\"300\" height=\"127\" valign=\"top\" colspan=2> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"50%\">\n        <tr>\n        <td colspan=\"2\"> ");
/*      */                                 
/* 3684 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3685 */                                 _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3686 */                                 _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3688 */                                 _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3690 */                                 _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */                                 
/* 3692 */                                 _jspx_th_awolf_005ftimechart_005f2.setHeight("170");
/*      */                                 
/* 3694 */                                 _jspx_th_awolf_005ftimechart_005f2.setLegend("false");
/*      */                                 
/* 3696 */                                 _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(xaxis_time);
/*      */                                 
/* 3698 */                                 _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(yaxis_reqmin);
/*      */                                 
/* 3700 */                                 _jspx_th_awolf_005ftimechart_005f2.setDateFormat("HH:mm");
/* 3701 */                                 int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3702 */                                 if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3703 */                                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3704 */                                     out = _jspx_page_context.pushBody();
/* 3705 */                                     _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3706 */                                     _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3709 */                                     out.write("\n            ");
/* 3710 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3711 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3714 */                                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3715 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3718 */                                 if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3719 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                                 }
/*      */                                 
/* 3722 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3723 */                                 out.write(" </td>\n        </tr>\n      </table></td>\n</tr>\n\n<tr>\n     <!--td width=\"300\" align=left valign=\"top\"-->\n     <td  align=left colspan=2 valign=\"top\">\n      <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3724 */                                 if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3726 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3727 */                                 if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3729 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3730 */                                 if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3732 */                                 out.write("</span></td>\n          <!--td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3733 */                                 if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3735 */                                 out.write("</span></td-->\n        </tr>\n\n        <tr>\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3736 */                                 out.print(tipCurValue);
/* 3737 */                                 out.write(34);
/* 3738 */                                 out.write(62);
/* 3739 */                                 out.print(FormatUtil.getString("am.webclient.apache.requestperminute"));
/* 3740 */                                 out.write("</td>\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 3741 */                                 out.print(formatNumber(reqpermin));
/* 3742 */                                 out.write(" </td>\n          <td width=\"16%\" align=\"center\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3743 */                                 out.print(resID);
/* 3744 */                                 out.write("&attributeid=2104&alertconfigurl=");
/* 3745 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs2104attributeToSelect=2104&redirectto=" + encodeurl));
/* 3746 */                                 out.write("')\">");
/* 3747 */                                 out.print(getSeverityImage(alert.getProperty(resID + "#" + "2104")));
/* 3748 */                                 out.write(" </a></td>\n          <!--td  colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"left\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3749 */                                 out.print(resID);
/* 3750 */                                 out.write("&attributeIDs=2104&attributeToSelect=2104&redirectto=");
/* 3751 */                                 out.print(encodeurl);
/* 3752 */                                 out.write("\" class=\"staticlinks\">");
/* 3753 */                                 out.print(ALERTCONFIG_TEXT);
/* 3754 */                                 out.write("</a>&nbsp;</td-->\n        </tr>\n        ");
/*      */                                 
/* 3756 */                                 tipCurValue = FormatUtil.getString("am.webclient.apache.tooltip.nobtransfered");
/*      */                                 
/* 3758 */                                 out.write("\n        <tr>\n          <td  height=\"25\" class=\"yellowgrayborder\" title=\"");
/* 3759 */                                 out.print(tipCurValue);
/* 3760 */                                 out.write(34);
/* 3761 */                                 out.write(62);
/* 3762 */                                 out.print(FormatUtil.getString("am.webclient.apache.bytesperrequest"));
/* 3763 */                                 out.write("</td>\n          <td  height=\"25\" class=\"yellowgrayborder\">");
/* 3764 */                                 out.print(formatNumber(bytesperreq));
/* 3765 */                                 out.write(32);
/* 3766 */                                 if ((bytesperreq != null) && (!bytesperreq.equals(FormatUtil.getString("am.webclient.common.nodata.text")))) out.println(FormatUtil.getString("Bytes"));
/* 3767 */                                 out.write("</td>\n          <td width=\"16%\" class=\"yellowgrayborder\" align=\"center\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3768 */                                 out.print(resID);
/* 3769 */                                 out.write("&attributeid=2105&alertconfigurl=");
/* 3770 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs2105attributeToSelect=2105&redirectto=" + encodeurl));
/* 3771 */                                 out.write("')\">");
/* 3772 */                                 out.print(getSeverityImage(alert.getProperty(resID + "#" + "2105")));
/* 3773 */                                 out.write(" </a></td>\n\n        </tr>\n        <tr>\n        <td  colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3774 */                                 out.print(resID);
/* 3775 */                                 out.write("&attributeIDs=2104,2105&attributeToSelect=2104&redirectto=");
/* 3776 */                                 out.print(encodeurl);
/* 3777 */                                 out.write("\" class=\"staticlinks\">");
/* 3778 */                                 out.print(ALERTCONFIG_TEXT);
/* 3779 */                                 out.write("</a>&nbsp;</td>\n        </tr>\n\n     </table>\n    </td>\n</tr>\n</table>\n</td>\n</tr>\n\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td height=\"26\" class=\"tablebottom1\">&nbsp; </td>\n  </tr>\n</table>\n\n<br>\n\n");
/* 3780 */                                 out.write("<!--$Id$-->\n");
/*      */                                 
/* 3782 */                                 ArrayList resID1s = new ArrayList();
/* 3783 */                                 resID1s.add(resID);
/* 3784 */                                 attribIDs.add("" + busyserver_id);
/* 3785 */                                 attribIDs.add("" + idleserver_id);
/* 3786 */                                 attribIDs.add("" + bytespersec_id);
/* 3787 */                                 Properties alert1 = getStatus(resID1s, attribIDs);
/* 3788 */                                 wlsGraph.setParam(apache_id, "BUSYSERVERS");
/* 3789 */                                 tipCurValue = FormatUtil.getString("am.webclient.apache.tooltip.busyservers");
/* 3790 */                                 String busyServerSttributeId = "" + busyserver_id;
/* 3791 */                                 String bytedPerSecId = "" + bytespersec_id;
/*      */                                 
/* 3793 */                                 out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" width=\"50%\" align=\"left\" class=\"tableheading\">");
/* 3794 */                                 out.print(FormatUtil.getString("am.webclient.apache.busyservers"));
/* 3795 */                                 out.write(32);
/* 3796 */                                 out.write(45);
/* 3797 */                                 out.write(32);
/* 3798 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3799 */                                 out.write("</td>\n    <td height=\"26\" width=\"50%\" align=\"left\" class=\"tableheading\">");
/* 3800 */                                 out.print(FormatUtil.getString("am.webclient.apache.bytestransferred"));
/* 3801 */                                 out.write(32);
/* 3802 */                                 out.write(45);
/* 3803 */                                 out.write(32);
/* 3804 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3805 */                                 out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n<tr>\n<td width=\"49%\" height=\"25\">\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"rborder\">\n <td width=\"300\" height=\"127\" colspan=\"2\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"50%\">\n\n\n        <tr>\n          <td colspan=\"2\" > ");
/*      */                                 
/* 3807 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3808 */                                 _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3809 */                                 _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3811 */                                 _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3813 */                                 _jspx_th_awolf_005ftimechart_005f3.setWidth("300");
/*      */                                 
/* 3815 */                                 _jspx_th_awolf_005ftimechart_005f3.setHeight("170");
/*      */                                 
/* 3817 */                                 _jspx_th_awolf_005ftimechart_005f3.setLegend("false");
/*      */                                 
/* 3819 */                                 _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(xaxis_time);
/*      */                                 
/* 3821 */                                 _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(yaxis_busyser);
/*      */                                 
/* 3823 */                                 _jspx_th_awolf_005ftimechart_005f3.setDateFormat("HH:mm");
/* 3824 */                                 int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3825 */                                 if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 3826 */                                   if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3827 */                                     out = _jspx_page_context.pushBody();
/* 3828 */                                     _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 3829 */                                     _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3832 */                                     out.write("\n            ");
/* 3833 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 3834 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3837 */                                   if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3838 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3841 */                                 if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3842 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                                 }
/*      */                                 
/* 3845 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3846 */                                 out.write(" </td>\n        </tr>\n      </table></td>\n</tr>\n<tr>\n        <td align=left valign=\"top\">\n\n      <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3847 */                                 if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3849 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3850 */                                 if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3852 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3853 */                                 if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3855 */                                 out.write("</span></td>\n        </tr>\n        <tr>\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3856 */                                 out.print(tipCurValue);
/* 3857 */                                 out.write(34);
/* 3858 */                                 out.write(62);
/* 3859 */                                 out.print(FormatUtil.getString("am.webclient.apache.busyservers"));
/* 3860 */                                 out.write("</td>\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 3861 */                                 out.print(formatNumber(busyservers));
/* 3862 */                                 out.write("</td>\n          <td width=\"16%\" align=\"center\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3863 */                                 out.print(resID);
/* 3864 */                                 out.write("&attributeid=");
/* 3865 */                                 out.print(busyserver_id);
/* 3866 */                                 out.write("&alertconfigurl=");
/* 3867 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + busyserver_id + "attributeToSelect=" + busyserver_id + "&redirectto=" + encodeurl));
/* 3868 */                                 out.write("')\">");
/* 3869 */                                 out.print(getSeverityImage(alert1.getProperty(resID + "#" + busyserver_id)));
/* 3870 */                                 out.write(" </a></td>\n        </tr>\n        ");
/*      */                                 
/* 3872 */                                 tipCurValue = FormatUtil.getString("am.webclient.apache.tooltip.idleserver");
/*      */                                 
/* 3874 */                                 out.write("\n        <tr>\n          <td  height=\"25\" class=\"yellowgrayborder\" title=\"");
/* 3875 */                                 out.print(tipCurValue);
/* 3876 */                                 out.write(34);
/* 3877 */                                 out.write(62);
/* 3878 */                                 out.print(FormatUtil.getString("am.webclient.apache.idleservers"));
/* 3879 */                                 out.write("</td>\n          <td  height=\"25\" class=\"yellowgrayborder\">");
/* 3880 */                                 out.print(formatNumber(idleservers));
/* 3881 */                                 out.write("</td>\n          <td width=\"16%\"  align=\"center\" class=\"yellowgrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3882 */                                 out.print(resID);
/* 3883 */                                 out.write("&attributeid=");
/* 3884 */                                 out.print(idleserver_id);
/* 3885 */                                 out.write("&alertconfigurl=");
/* 3886 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=" + idleserver_id + "attributeToSelect=" + idleserver_id + "&redirectto=" + encodeurl));
/* 3887 */                                 out.write("')\">");
/* 3888 */                                 out.print(getSeverityImage(alert1.getProperty(resID + "#" + idleserver_id)));
/* 3889 */                                 out.write(" </a></td>\n        </tr>\n        <tr>\n        <td  colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3890 */                                 out.print(resID);
/* 3891 */                                 out.write("&attributeIDs=");
/* 3892 */                                 out.print(busyserver_id);
/* 3893 */                                 out.write(44);
/* 3894 */                                 out.print(idleserver_id);
/* 3895 */                                 out.write("&attributeToSelect=");
/* 3896 */                                 out.print(busyserver_id);
/* 3897 */                                 out.write("&redirectto=");
/* 3898 */                                 out.print(encodeurl);
/* 3899 */                                 out.write("\" class=\"staticlinks\">");
/* 3900 */                                 out.print(ALERTCONFIG_TEXT);
/* 3901 */                                 out.write("</a>&nbsp;</td>\n        </tr>\n      </table>\n     </td>\n</tr>\n</table>\n</td>\n\n");
/*      */                                 
/* 3903 */                                 wlsGraph.setParam(apache_id, "BYTESPERSEC");
/* 3904 */                                 tipCurValue = FormatUtil.getString("am.webclient.apache.tooltip.nobtransferedpersecond");
/*      */                                 
/* 3906 */                                 out.write("\n\n<td width=\"50%\" height=\"25\" >\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tr>\n <td width=\"300\" height=\"127\" colspan=\"2\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"50%\">\n\n        <tr>\n          <td colspan=\"2\" > ");
/*      */                                 
/* 3908 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3909 */                                 _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 3910 */                                 _jspx_th_awolf_005ftimechart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3912 */                                 _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3914 */                                 _jspx_th_awolf_005ftimechart_005f4.setWidth("300");
/*      */                                 
/* 3916 */                                 _jspx_th_awolf_005ftimechart_005f4.setHeight("170");
/*      */                                 
/* 3918 */                                 _jspx_th_awolf_005ftimechart_005f4.setLegend("false");
/*      */                                 
/* 3920 */                                 _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel(xaxis_time);
/*      */                                 
/* 3922 */                                 _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel(yaxis_bytessec);
/*      */                                 
/* 3924 */                                 _jspx_th_awolf_005ftimechart_005f4.setDateFormat("HH:mm");
/* 3925 */                                 int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 3926 */                                 if (_jspx_eval_awolf_005ftimechart_005f4 != 0) {
/* 3927 */                                   if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3928 */                                     out = _jspx_page_context.pushBody();
/* 3929 */                                     _jspx_th_awolf_005ftimechart_005f4.setBodyContent((BodyContent)out);
/* 3930 */                                     _jspx_th_awolf_005ftimechart_005f4.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3933 */                                     out.write("\n            ");
/* 3934 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f4.doAfterBody();
/* 3935 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3938 */                                   if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3939 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3942 */                                 if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 3943 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4); return;
/*      */                                 }
/*      */                                 
/* 3946 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 3947 */                                 out.write(" </td>\n        </tr>\n      </table></td>\n</tr>\n<tr>\n        <td  align=left valign=\"top\">\n\n      <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3948 */                                 if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3950 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3951 */                                 if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3953 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3954 */                                 if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3956 */                                 out.write("</span></td>\n\n        </tr>\n        <tr>\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3957 */                                 out.print(tipCurValue);
/* 3958 */                                 out.write(34);
/* 3959 */                                 out.write(62);
/* 3960 */                                 out.print(FormatUtil.getString("am.webclient.apache.bytespersec"));
/* 3961 */                                 out.write("</td>\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 3962 */                                 out.print(formatNumber(bytespersec));
/* 3963 */                                 out.write("</td>\n          <td width=\"16%\" align=\"center\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3964 */                                 out.print(resID);
/* 3965 */                                 out.write("&attributeid=");
/* 3966 */                                 out.print(bytespersec_id);
/* 3967 */                                 out.write("&alertconfigurl=");
/* 3968 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + bytespersec_id + "attributeToSelect=" + bytespersec_id + "&redirectto=" + encodeurl));
/* 3969 */                                 out.write("')\">");
/* 3970 */                                 out.print(getSeverityImage(alert1.getProperty(resID + "#" + bytespersec_id)));
/* 3971 */                                 out.write(" </a></td>\n\n        </tr>\n        <tr>\n          <td  height=\"25\" class=\"yellowgrayborder\"></td>\n          <td  height=\"25\" class=\"yellowgrayborder\"></td>\n          <td width=\"16%\"class=\"yellowgrayborder\"> </td-->\n        </tr>\n        <tr>\n        <td  colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3972 */                                 out.print(resID);
/* 3973 */                                 out.write("&attributeIDs=");
/* 3974 */                                 out.print(bytespersec_id);
/* 3975 */                                 out.write("&attributeToSelect=");
/* 3976 */                                 out.print(bytespersec_id);
/* 3977 */                                 out.write("&redirectto=");
/* 3978 */                                 out.print(encodeurl);
/* 3979 */                                 out.write("\" class=\"staticlinks\">");
/* 3980 */                                 out.print(ALERTCONFIG_TEXT);
/* 3981 */                                 out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n</tr>\n</table>\n</td>\n</tr>\n\n</table>\n\n\n\n");
/*      */                                 
/* 3983 */                                 if (apache_id != resID)
/*      */                                 {
/*      */ 
/* 3986 */                                   out.write("\n<br>\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\" class=\"lrtbdarkborder\">\n<tr>\n    <td class=\"yellowgrayborder\"> &nbsp;&nbsp;");
/* 3987 */                                   out.print(FormatUtil.getString("am.webclient.apache.latest.values"));
/* 3988 */                                   out.write("\n<a href=\"/help/monitors/services-monitoring.html#php\" target=\"_blank\" class=\"selectedmenu\"><img src=\"/images/icon_quicknote_help.gif\"\nhspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">");
/* 3989 */                                   out.print(FormatUtil.getString("am.webclient.contexthelplink.text"));
/* 3990 */                                   out.write(" >></a> </td>\n</tr>\n</table>\n\n<br>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3994 */                                 out.write(10);
/* 3995 */                                 out.write(10);
/* 3996 */                                 out.write(10);
/*      */                               }
/* 3998 */                               out.write("\n\n<br>\n");
/* 3999 */                               out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 4000 */                               DialChartSupport dialGraph = null;
/* 4001 */                               dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 4002 */                               if (dialGraph == null) {
/* 4003 */                                 dialGraph = new DialChartSupport();
/* 4004 */                                 _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                               }
/* 4006 */                               out.write(10);
/*      */                               
/*      */                               try
/*      */                               {
/* 4010 */                                 String hostos = (String)systeminfo.get("HOSTOS");
/* 4011 */                                 String hostname = (String)systeminfo.get("HOSTNAME");
/* 4012 */                                 String hostid = (String)systeminfo.get("host_resid");
/* 4013 */                                 boolean isConf = false;
/* 4014 */                                 if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 4015 */                                   isConf = true;
/*      */                                 }
/* 4017 */                                 com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 4018 */                                 Properties property = new Properties();
/* 4019 */                                 if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                                 {
/* 4021 */                                   property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 4022 */                                   if ((property != null) && (property.size() > 0))
/*      */                                   {
/* 4024 */                                     String cpuid = property.getProperty("cpuid");
/* 4025 */                                     String memid = property.getProperty("memid");
/* 4026 */                                     String diskid = property.getProperty("diskid");
/* 4027 */                                     String cpuvalue = property.getProperty("CPU Utilization");
/* 4028 */                                     String memvalue = property.getProperty("Memory Utilization");
/* 4029 */                                     String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 4030 */                                     String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 4031 */                                     String diskvalue = property.getProperty("Disk Utilization");
/* 4032 */                                     String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                     
/* 4034 */                                     if (!isConf) {
/* 4035 */                                       out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 4036 */                                       out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 4037 */                                       out.write(45);
/* 4038 */                                       if (systeminfo.get("host_resid") != null) {
/* 4039 */                                         out.write("<a href=\"showresource.do?resourceid=");
/* 4040 */                                         out.print(hostid);
/* 4041 */                                         out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 4042 */                                         out.print(hostname);
/* 4043 */                                         out.write("</a>");
/* 4044 */                                       } else { out.println(hostname); }
/* 4045 */                                       out.write("</td>\t");
/* 4046 */                                       out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 4047 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4048 */                                       out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                       
/*      */ 
/* 4051 */                                       if (cpuvalue != null)
/*      */                                       {
/*      */ 
/* 4054 */                                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4055 */                                         out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4056 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4057 */                                         out.write(45);
/* 4058 */                                         out.print(cpuvalue);
/* 4059 */                                         out.write(" %'>\n\n");
/*      */                                         
/* 4061 */                                         DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4062 */                                         _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 4063 */                                         _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4065 */                                         _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                         
/* 4067 */                                         _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                         
/* 4069 */                                         _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                         
/* 4071 */                                         _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                         
/* 4073 */                                         _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                         
/* 4075 */                                         _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                         
/* 4077 */                                         _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                         
/* 4079 */                                         _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                         
/* 4081 */                                         _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                         
/* 4083 */                                         _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 4084 */                                         int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 4085 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 4086 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4087 */                                             out = _jspx_page_context.pushBody();
/* 4088 */                                             _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 4089 */                                             _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4092 */                                             out.write(10);
/* 4093 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 4094 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4097 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4098 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4101 */                                         if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 4102 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                         }
/*      */                                         
/* 4105 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 4106 */                                         out.write("\n         </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4110 */                                         out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4111 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4112 */                                         out.write(32);
/* 4113 */                                         out.write(62);
/* 4114 */                                         out.write(10);
/* 4115 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4116 */                                         out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                       }
/* 4118 */                                       out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4119 */                                       if (cpuvalue != null)
/*      */                                       {
/* 4121 */                                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4122 */                                         out.print(hostid);
/* 4123 */                                         out.write("&attributeid=");
/* 4124 */                                         out.print(cpuid);
/* 4125 */                                         out.write("&period=-7')\" class='bodytextbold'>");
/* 4126 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4127 */                                         out.write(32);
/* 4128 */                                         out.write(45);
/* 4129 */                                         out.write(32);
/* 4130 */                                         out.print(cpuvalue);
/* 4131 */                                         out.write("</a> %\n");
/*      */                                       }
/* 4133 */                                       out.write("\n  </td>\n       </tr>\n       </table>");
/* 4134 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4135 */                                       out.write("</td>\n      <td width=\"30%\"> ");
/* 4136 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4137 */                                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                       
/* 4139 */                                       if (memvalue != null)
/*      */                                       {
/*      */ 
/* 4142 */                                         dialGraph.setValue(Long.parseLong(memvalue));
/* 4143 */                                         out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4144 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4145 */                                         out.write(45);
/* 4146 */                                         out.print(memvalue);
/* 4147 */                                         out.write(" %' >\n\n");
/*      */                                         
/* 4149 */                                         DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4150 */                                         _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 4151 */                                         _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4153 */                                         _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                         
/* 4155 */                                         _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                         
/* 4157 */                                         _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                         
/* 4159 */                                         _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                         
/* 4161 */                                         _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                         
/* 4163 */                                         _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                         
/* 4165 */                                         _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                         
/* 4167 */                                         _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                         
/* 4169 */                                         _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                         
/* 4171 */                                         _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 4172 */                                         int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 4173 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 4174 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4175 */                                             out = _jspx_page_context.pushBody();
/* 4176 */                                             _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 4177 */                                             _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4180 */                                             out.write(32);
/* 4181 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 4182 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4185 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4186 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4189 */                                         if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 4190 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                         }
/*      */                                         
/* 4193 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 4194 */                                         out.write(32);
/* 4195 */                                         out.write("\n            </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4199 */                                         out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4200 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4201 */                                         out.write(" >\n\n");
/* 4202 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4203 */                                         out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                       }
/* 4205 */                                       out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4206 */                                       if (memvalue != null)
/*      */                                       {
/* 4208 */                                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4209 */                                         out.print(hostid);
/* 4210 */                                         out.write("&attributeid=");
/* 4211 */                                         out.print(memid);
/* 4212 */                                         out.write("&period=-7')\" class='bodytextbold'>");
/* 4213 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4214 */                                         out.write(45);
/* 4215 */                                         out.print(memvalue);
/* 4216 */                                         out.write("</a> %\n  ");
/*      */                                       }
/* 4218 */                                       out.write("\n  </td>\n       </tr>\n    </table>");
/* 4219 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4220 */                                       out.write("</td>\n      <td width=\"30%\">");
/* 4221 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4222 */                                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                       
/*      */ 
/* 4225 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                       {
/*      */ 
/*      */ 
/* 4229 */                                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 4230 */                                         out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4231 */                                         out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 4232 */                                         out.write(45);
/* 4233 */                                         out.print(diskvalue);
/* 4234 */                                         out.write("%' >\n");
/*      */                                         
/* 4236 */                                         DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4237 */                                         _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 4238 */                                         _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4240 */                                         _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                         
/* 4242 */                                         _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                         
/* 4244 */                                         _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                         
/* 4246 */                                         _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                         
/* 4248 */                                         _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                         
/* 4250 */                                         _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                         
/* 4252 */                                         _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                         
/* 4254 */                                         _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                         
/* 4256 */                                         _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                         
/* 4258 */                                         _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 4259 */                                         int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 4260 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 4261 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 4262 */                                             out = _jspx_page_context.pushBody();
/* 4263 */                                             _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 4264 */                                             _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4267 */                                             out.write(32);
/* 4268 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 4269 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4272 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 4273 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4276 */                                         if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 4277 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                         }
/*      */                                         
/* 4280 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 4281 */                                         out.write(32);
/* 4282 */                                         out.write(32);
/* 4283 */                                         out.write("\n    </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4287 */                                         out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4288 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4289 */                                         out.write(32);
/* 4290 */                                         out.write(62);
/* 4291 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4292 */                                         out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                       }
/* 4294 */                                       out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 4295 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                       {
/* 4297 */                                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4298 */                                         out.print(hostid);
/* 4299 */                                         out.write("&attributeid=");
/* 4300 */                                         out.print(diskid);
/* 4301 */                                         out.write("&period=-7')\" class='bodytextbold'>");
/* 4302 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 4303 */                                         out.write(45);
/* 4304 */                                         out.print(diskvalue);
/* 4305 */                                         out.write("</a> %\n     ");
/*      */                                       }
/* 4307 */                                       out.write("\n  </td>\n  </tr>\n</table>");
/* 4308 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4309 */                                       out.write("</td></tr></table>\n\n");
/*      */                                     } else {
/* 4311 */                                       out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 4312 */                                       out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 4313 */                                       out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 4314 */                                       out.print(systeminfo.get("host_resid"));
/* 4315 */                                       out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 4316 */                                       out.print(hostname);
/* 4317 */                                       out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 4318 */                                       if (cpuvalue != null)
/*      */                                       {
/*      */ 
/* 4321 */                                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4322 */                                         out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                         
/* 4324 */                                         DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4325 */                                         _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 4326 */                                         _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4328 */                                         _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                         
/* 4330 */                                         _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                         
/* 4332 */                                         _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                         
/* 4334 */                                         _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                         
/* 4336 */                                         _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                         
/* 4338 */                                         _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                         
/* 4340 */                                         _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                         
/* 4342 */                                         _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                         
/* 4344 */                                         _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                         
/* 4346 */                                         _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 4347 */                                         int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 4348 */                                         if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 4349 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                         }
/*      */                                         
/* 4352 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 4353 */                                         out.write("\n         </td>\n     ");
/*      */                                       }
/*      */                                       else {
/* 4356 */                                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4357 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4358 */                                         out.write(39);
/* 4359 */                                         out.write(32);
/* 4360 */                                         out.write(62);
/* 4361 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4362 */                                         out.write("\n \t\t</td>\n\t\t");
/*      */                                       }
/* 4364 */                                       if (memvalue != null) {
/* 4365 */                                         dialGraph.setValue(Long.parseLong(memvalue));
/* 4366 */                                         out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                         
/* 4368 */                                         DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4369 */                                         _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 4370 */                                         _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4372 */                                         _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                         
/* 4374 */                                         _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                         
/* 4376 */                                         _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                         
/* 4378 */                                         _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                         
/* 4380 */                                         _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                         
/* 4382 */                                         _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                         
/* 4384 */                                         _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                         
/* 4386 */                                         _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                         
/* 4388 */                                         _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                         
/* 4390 */                                         _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 4391 */                                         int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 4392 */                                         if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 4393 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                         }
/*      */                                         
/* 4396 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 4397 */                                         out.write("\n            </td>\n         ");
/*      */                                       }
/*      */                                       else {
/* 4400 */                                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4401 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4402 */                                         out.write(39);
/* 4403 */                                         out.write(32);
/* 4404 */                                         out.write(62);
/* 4405 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4406 */                                         out.write("\n \t\t</td>\n\t\t");
/*      */                                       }
/* 4408 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 4409 */                                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 4410 */                                         out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                         
/* 4412 */                                         DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4413 */                                         _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 4414 */                                         _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                         
/* 4416 */                                         _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                         
/* 4418 */                                         _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                         
/* 4420 */                                         _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                         
/* 4422 */                                         _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                         
/* 4424 */                                         _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                         
/* 4426 */                                         _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                         
/* 4428 */                                         _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                         
/* 4430 */                                         _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                         
/* 4432 */                                         _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                         
/* 4434 */                                         _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 4435 */                                         int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 4436 */                                         if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 4437 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                         }
/*      */                                         
/* 4440 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 4441 */                                         out.write(32);
/* 4442 */                                         out.write("\n\t          </td>\n\t  ");
/*      */                                       }
/*      */                                       else {
/* 4445 */                                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4446 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4447 */                                         out.write(39);
/* 4448 */                                         out.write(32);
/* 4449 */                                         out.write(62);
/* 4450 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4451 */                                         out.write("\n \t\t</td>\n\t\t");
/*      */                                       }
/* 4453 */                                       out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4454 */                                       out.print(hostid);
/* 4455 */                                       out.write("&attributeid=");
/* 4456 */                                       out.print(cpuid);
/* 4457 */                                       out.write("&period=-7')\" class='tooltip'>");
/* 4458 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4459 */                                       out.write(32);
/* 4460 */                                       out.write(45);
/* 4461 */                                       out.write(32);
/* 4462 */                                       if (cpuvalue != null) {
/* 4463 */                                         out.print(cpuvalue);
/*      */                                       }
/* 4465 */                                       out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4466 */                                       out.print(hostid);
/* 4467 */                                       out.write("&attributeid=");
/* 4468 */                                       out.print(memid);
/* 4469 */                                       out.write("&period=-7')\" class='tooltip'>");
/* 4470 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4471 */                                       out.write(45);
/* 4472 */                                       if (memvalue != null) {
/* 4473 */                                         out.print(memvalue);
/*      */                                       }
/* 4475 */                                       out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4476 */                                       out.print(hostid);
/* 4477 */                                       out.write("&attributeid=");
/* 4478 */                                       out.print(diskid);
/* 4479 */                                       out.write("&period=-7')\" class='tooltip'>");
/* 4480 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 4481 */                                       out.write(45);
/* 4482 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 4483 */                                         out.print(diskvalue);
/*      */                                       }
/* 4485 */                                       out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                     }
/* 4487 */                                     out.write(10);
/* 4488 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/*      */                                 }
/*      */                               }
/*      */                               catch (Exception e)
/*      */                               {
/* 4495 */                                 e.printStackTrace();
/*      */                               }
/* 4497 */                               out.write(10);
/* 4498 */                               out.write(10);
/* 4499 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 4500 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 4503 */                             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4504 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 4507 */                           if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4508 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                           }
/*      */                           
/* 4511 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 4512 */                           out.write(32);
/* 4513 */                           if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 4515 */                           out.write(32);
/* 4516 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4517 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4521 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4522 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                       }
/*      */                       else {
/* 4525 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4526 */                         out.write("\n</body>\n</html>\n");
/*      */                       }
/* 4528 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4529 */         out = _jspx_out;
/* 4530 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4531 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4532 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4535 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4541 */     PageContext pageContext = _jspx_page_context;
/* 4542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4544 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4545 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4546 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 4548 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 4549 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4550 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4552 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 4553 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4554 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4558 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4559 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4560 */       return true;
/*      */     }
/* 4562 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4563 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4568 */     PageContext pageContext = _jspx_page_context;
/* 4569 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4571 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 4572 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 4573 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 4575 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 4576 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 4578 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 4579 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 4581 */           out.write("\n\t    ");
/* 4582 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 4583 */             return true;
/* 4584 */           out.write(10);
/* 4585 */           out.write(9);
/* 4586 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 4587 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4591 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 4592 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4595 */         int tmp184_183 = 0; int[] tmp184_181 = _jspx_push_body_count_c_005fcatch_005f0; int tmp186_185 = tmp184_181[tmp184_183];tmp184_181[tmp184_183] = (tmp186_185 - 1); if (tmp186_185 <= 0) break;
/* 4596 */         out = _jspx_page_context.popBody(); }
/* 4597 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4599 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 4600 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 4602 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 4607 */     PageContext pageContext = _jspx_page_context;
/* 4608 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4610 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 4611 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 4612 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 4614 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 4616 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 4617 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 4618 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 4619 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4620 */       return true;
/*      */     }
/* 4622 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4623 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4628 */     PageContext pageContext = _jspx_page_context;
/* 4629 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4631 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4632 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4633 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4635 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4636 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4637 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4639 */         out.write(10);
/* 4640 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 4641 */           return true;
/* 4642 */         out.write(10);
/* 4643 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4644 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4648 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4649 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4650 */       return true;
/*      */     }
/* 4652 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4653 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4658 */     PageContext pageContext = _jspx_page_context;
/* 4659 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4661 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4662 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4663 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4665 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4667 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 4668 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4669 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4670 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4671 */       return true;
/*      */     }
/* 4673 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4679 */     PageContext pageContext = _jspx_page_context;
/* 4680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4682 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4683 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4684 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4686 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4687 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4688 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4690 */         out.write(10);
/* 4691 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4692 */           return true;
/* 4693 */         out.write(10);
/* 4694 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4695 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4699 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4700 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4701 */       return true;
/*      */     }
/* 4703 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4704 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4709 */     PageContext pageContext = _jspx_page_context;
/* 4710 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4712 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4713 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4714 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4716 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 4718 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 4719 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4720 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4721 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4722 */       return true;
/*      */     }
/* 4724 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4730 */     PageContext pageContext = _jspx_page_context;
/* 4731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4733 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4734 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4735 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4737 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 4739 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/AvailabilityPerformanceLinks.jsp");
/* 4740 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4741 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4742 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4743 */       return true;
/*      */     }
/* 4745 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4746 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4751 */     PageContext pageContext = _jspx_page_context;
/* 4752 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4754 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4755 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4756 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4758 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 4760 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*      */     
/* 4762 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 4763 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4764 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4765 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4766 */       return true;
/*      */     }
/* 4768 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4769 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4774 */     PageContext pageContext = _jspx_page_context;
/* 4775 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4777 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(CheckboxTag.class);
/* 4778 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 4779 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 4781 */     _jspx_th_html_005fcheckbox_005f0.setProperty("sslenabled");
/*      */     
/* 4783 */     _jspx_th_html_005fcheckbox_005f0.setValue("checked");
/* 4784 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 4785 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 4786 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4787 */       return true;
/*      */     }
/* 4789 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4795 */     PageContext pageContext = _jspx_page_context;
/* 4796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4798 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 4799 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 4800 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 4802 */     _jspx_th_html_005fcheckbox_005f1.setProperty("sslenabled");
/* 4803 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 4804 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 4805 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4806 */       return true;
/*      */     }
/* 4808 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4809 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4814 */     PageContext pageContext = _jspx_page_context;
/* 4815 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4817 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 4818 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 4819 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4821 */     _jspx_th_html_005fcheckbox_005f2.setProperty("apacheauth");
/*      */     
/* 4823 */     _jspx_th_html_005fcheckbox_005f2.setValue("true");
/*      */     
/* 4825 */     _jspx_th_html_005fcheckbox_005f2.setOnclick("javascript:showApacheAuth()");
/* 4826 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 4827 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 4828 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 4829 */       return true;
/*      */     }
/* 4831 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 4832 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4837 */     PageContext pageContext = _jspx_page_context;
/* 4838 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4840 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4841 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 4842 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4844 */     _jspx_th_c_005fif_005f6.setTest("${!AMActionForm.apacheauth}");
/* 4845 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 4846 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 4848 */         out.write("style=\"DISPLAY: none\"");
/* 4849 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4850 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4854 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4855 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4856 */       return true;
/*      */     }
/* 4858 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4859 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4864 */     PageContext pageContext = _jspx_page_context;
/* 4865 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4867 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4868 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4869 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4871 */     _jspx_th_c_005fif_005f7.setTest("${AMActionForm.apacheauth}");
/* 4872 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4873 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 4875 */         out.write("style=\"DISPLAY:block\"");
/* 4876 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4877 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4881 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4882 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4883 */       return true;
/*      */     }
/* 4885 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4886 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4891 */     PageContext pageContext = _jspx_page_context;
/* 4892 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4894 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4895 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4896 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4898 */     _jspx_th_html_005ftext_005f1.setProperty("apacheUserName");
/*      */     
/* 4900 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 4901 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4902 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4903 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4904 */       return true;
/*      */     }
/* 4906 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4907 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4912 */     PageContext pageContext = _jspx_page_context;
/* 4913 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4915 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 4916 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 4917 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4919 */     _jspx_th_html_005fpassword_005f0.setProperty("apachepassword");
/*      */     
/* 4921 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/* 4922 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 4923 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 4924 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4925 */       return true;
/*      */     }
/* 4927 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4933 */     PageContext pageContext = _jspx_page_context;
/* 4934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4936 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 4937 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 4938 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4940 */     _jspx_th_html_005fcheckbox_005f3.setProperty("serverstatusurl");
/*      */     
/* 4942 */     _jspx_th_html_005fcheckbox_005f3.setValue("true");
/*      */     
/* 4944 */     _jspx_th_html_005fcheckbox_005f3.setOnclick("javascript:showApacheStatus()");
/* 4945 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 4946 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 4947 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 4948 */       return true;
/*      */     }
/* 4950 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 4951 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4956 */     PageContext pageContext = _jspx_page_context;
/* 4957 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4959 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4960 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4961 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4963 */     _jspx_th_c_005fif_005f8.setTest("${!AMActionForm.serverstatusurl}");
/* 4964 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4965 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 4967 */         out.write("style=\"DISPLAY: none\"");
/* 4968 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4969 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4973 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4974 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4975 */       return true;
/*      */     }
/* 4977 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4978 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4983 */     PageContext pageContext = _jspx_page_context;
/* 4984 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4986 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4987 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 4988 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4990 */     _jspx_th_html_005ftext_005f2.setProperty("apacheurl");
/*      */     
/* 4992 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 4994 */     _jspx_th_html_005ftext_005f2.setSize("30");
/* 4995 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 4996 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 4997 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4998 */       return true;
/*      */     }
/* 5000 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5006 */     PageContext pageContext = _jspx_page_context;
/* 5007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5009 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5010 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 5011 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5013 */     _jspx_th_c_005fif_005f11.setTest("${not empty param.haid}");
/* 5014 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 5015 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 5017 */         out.write(10);
/* 5018 */         out.write(9);
/* 5019 */         out.write(9);
/* 5020 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 5021 */           return true;
/* 5022 */         out.write(10);
/* 5023 */         out.write(9);
/* 5024 */         out.write(9);
/* 5025 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 5026 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5030 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 5031 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 5032 */       return true;
/*      */     }
/* 5034 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 5035 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5040 */     PageContext pageContext = _jspx_page_context;
/* 5041 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5043 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5044 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5045 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 5047 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 5049 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 5050 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5051 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5052 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5053 */         out = _jspx_page_context.pushBody();
/* 5054 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5055 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5058 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5059 */           return true;
/* 5060 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5061 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5064 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5065 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5068 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5069 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5070 */       return true;
/*      */     }
/* 5072 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5073 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5078 */     PageContext pageContext = _jspx_page_context;
/* 5079 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5081 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5082 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5083 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5085 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 5086 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5087 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5088 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5089 */       return true;
/*      */     }
/* 5091 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5092 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5097 */     PageContext pageContext = _jspx_page_context;
/* 5098 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5100 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5101 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 5102 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5104 */     _jspx_th_c_005fif_005f12.setTest("${not empty param.resourceid}");
/* 5105 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 5106 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 5108 */         out.write(10);
/* 5109 */         out.write(9);
/* 5110 */         out.write(9);
/* 5111 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 5112 */           return true;
/* 5113 */         out.write(10);
/* 5114 */         out.write(9);
/* 5115 */         out.write(9);
/* 5116 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 5117 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5121 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 5122 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5123 */       return true;
/*      */     }
/* 5125 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5131 */     PageContext pageContext = _jspx_page_context;
/* 5132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5134 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5135 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5136 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 5138 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 5140 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 5141 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5142 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 5143 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5144 */         out = _jspx_page_context.pushBody();
/* 5145 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 5146 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5149 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 5150 */           return true;
/* 5151 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 5152 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5155 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5156 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5159 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5160 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5161 */       return true;
/*      */     }
/* 5163 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5169 */     PageContext pageContext = _jspx_page_context;
/* 5170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5172 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5173 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5174 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 5176 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 5177 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5178 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5179 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5180 */       return true;
/*      */     }
/* 5182 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5188 */     PageContext pageContext = _jspx_page_context;
/* 5189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5191 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5192 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5193 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5195 */     _jspx_th_c_005fout_005f2.setValue("${myfield_paramresid}");
/* 5196 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5197 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5198 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5199 */       return true;
/*      */     }
/* 5201 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5207 */     PageContext pageContext = _jspx_page_context;
/* 5208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5210 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5211 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 5212 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5214 */     _jspx_th_c_005fif_005f13.setTest("${not empty param.haid}");
/* 5215 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 5216 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 5218 */         out.write(10);
/* 5219 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f13, _jspx_page_context))
/* 5220 */           return true;
/* 5221 */         out.write(10);
/* 5222 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 5223 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5227 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 5228 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 5229 */       return true;
/*      */     }
/* 5231 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 5232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5237 */     PageContext pageContext = _jspx_page_context;
/* 5238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5240 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5241 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5242 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5244 */     _jspx_th_c_005fset_005f2.setVar("myfield_resid");
/*      */     
/* 5246 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 5247 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5248 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5249 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5250 */         out = _jspx_page_context.pushBody();
/* 5251 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5252 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5255 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5256 */           return true;
/* 5257 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5258 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5261 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5262 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5265 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5266 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5267 */       return true;
/*      */     }
/* 5269 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5270 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5275 */     PageContext pageContext = _jspx_page_context;
/* 5276 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5278 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5279 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5280 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5282 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 5283 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5284 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5285 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5286 */       return true;
/*      */     }
/* 5288 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5294 */     PageContext pageContext = _jspx_page_context;
/* 5295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5297 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5298 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 5299 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5301 */     _jspx_th_c_005fif_005f14.setTest("${not empty param.resourceid}");
/* 5302 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 5303 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 5305 */         out.write(10);
/* 5306 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f14, _jspx_page_context))
/* 5307 */           return true;
/* 5308 */         out.write(10);
/* 5309 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 5310 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5314 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 5315 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 5316 */       return true;
/*      */     }
/* 5318 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 5319 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5324 */     PageContext pageContext = _jspx_page_context;
/* 5325 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5327 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5328 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5329 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 5331 */     _jspx_th_c_005fset_005f3.setVar("myfield_resid");
/*      */     
/* 5333 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 5334 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5335 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 5336 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5337 */         out = _jspx_page_context.pushBody();
/* 5338 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 5339 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5342 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 5343 */           return true;
/* 5344 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 5345 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5348 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5349 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5352 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5353 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5354 */       return true;
/*      */     }
/* 5356 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5357 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5362 */     PageContext pageContext = _jspx_page_context;
/* 5363 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5365 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5366 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5367 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 5369 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 5370 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5371 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5372 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5373 */       return true;
/*      */     }
/* 5375 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5381 */     PageContext pageContext = _jspx_page_context;
/* 5382 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5384 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5385 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 5386 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5388 */     _jspx_th_c_005fset_005f4.setVar("trstripclass");
/*      */     
/* 5390 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 5391 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 5392 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 5393 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5394 */         out = _jspx_page_context.pushBody();
/* 5395 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 5396 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5399 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 5400 */           return true;
/* 5401 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 5402 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5405 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5406 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5409 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 5410 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 5411 */       return true;
/*      */     }
/* 5413 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 5414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5419 */     PageContext pageContext = _jspx_page_context;
/* 5420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5422 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5423 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5424 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 5426 */     _jspx_th_c_005fout_005f5.setValue("");
/* 5427 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5428 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5429 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5430 */       return true;
/*      */     }
/* 5432 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5438 */     PageContext pageContext = _jspx_page_context;
/* 5439 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5441 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5442 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 5443 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5445 */     _jspx_th_c_005fset_005f5.setVar("myfield_entity");
/*      */     
/* 5447 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 5448 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 5449 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 5450 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 5451 */         out = _jspx_page_context.pushBody();
/* 5452 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 5453 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5456 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 5457 */           return true;
/* 5458 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 5459 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5462 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 5463 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5466 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 5467 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 5468 */       return true;
/*      */     }
/* 5470 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 5471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5476 */     PageContext pageContext = _jspx_page_context;
/* 5477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5479 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5480 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5481 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 5483 */     _jspx_th_c_005fout_005f6.setValue("noalarms");
/* 5484 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5485 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5486 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5487 */       return true;
/*      */     }
/* 5489 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5495 */     PageContext pageContext = _jspx_page_context;
/* 5496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5498 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5499 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 5500 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5502 */     _jspx_th_c_005fif_005f15.setTest("${not empty param.entity}");
/* 5503 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 5504 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 5506 */         out.write(10);
/* 5507 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 5508 */           return true;
/* 5509 */         out.write(10);
/* 5510 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 5511 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5515 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 5516 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 5517 */       return true;
/*      */     }
/* 5519 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 5520 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5525 */     PageContext pageContext = _jspx_page_context;
/* 5526 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5528 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5529 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 5530 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 5532 */     _jspx_th_c_005fset_005f6.setVar("myfield_entity");
/*      */     
/* 5534 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 5535 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 5536 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 5537 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5538 */         out = _jspx_page_context.pushBody();
/* 5539 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 5540 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5543 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 5544 */           return true;
/* 5545 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 5546 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5549 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5550 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5553 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 5554 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 5555 */       return true;
/*      */     }
/* 5557 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 5558 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5563 */     PageContext pageContext = _jspx_page_context;
/* 5564 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5566 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5567 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5568 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 5570 */     _jspx_th_c_005fout_005f7.setValue("${param.entity}");
/* 5571 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5572 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5573 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5574 */       return true;
/*      */     }
/* 5576 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5582 */     PageContext pageContext = _jspx_page_context;
/* 5583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5585 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5586 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 5587 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5589 */     _jspx_th_c_005fif_005f16.setTest("${not empty param.includeClass}");
/* 5590 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 5591 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 5593 */         out.write(10);
/* 5594 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 5595 */           return true;
/* 5596 */         out.write(10);
/* 5597 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 5598 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5602 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 5603 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 5604 */       return true;
/*      */     }
/* 5606 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 5607 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5612 */     PageContext pageContext = _jspx_page_context;
/* 5613 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5615 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5616 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 5617 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 5619 */     _jspx_th_c_005fset_005f7.setVar("trstripclass");
/*      */     
/* 5621 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 5622 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 5623 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 5624 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 5625 */         out = _jspx_page_context.pushBody();
/* 5626 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 5627 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5630 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 5631 */           return true;
/* 5632 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 5633 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5636 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 5637 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5640 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 5641 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 5642 */       return true;
/*      */     }
/* 5644 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 5645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5650 */     PageContext pageContext = _jspx_page_context;
/* 5651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5653 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5654 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5655 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 5657 */     _jspx_th_c_005fout_005f8.setValue("${param.includeClass}");
/* 5658 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5659 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5660 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5661 */       return true;
/*      */     }
/* 5663 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5669 */     PageContext pageContext = _jspx_page_context;
/* 5670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5672 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5673 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5674 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5676 */     _jspx_th_c_005fout_005f9.setValue("${trstripclass}");
/* 5677 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5678 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5679 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5680 */       return true;
/*      */     }
/* 5682 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5683 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5688 */     PageContext pageContext = _jspx_page_context;
/* 5689 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5691 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5692 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 5693 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/* 5694 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 5695 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 5696 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5697 */         out = _jspx_page_context.pushBody();
/* 5698 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 5699 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5702 */         out.write("am.myfield.customfield.text");
/* 5703 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 5704 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5707 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5708 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5711 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 5712 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5713 */       return true;
/*      */     }
/* 5715 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5716 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5721 */     PageContext pageContext = _jspx_page_context;
/* 5722 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5724 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5725 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5726 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5728 */     _jspx_th_c_005fout_005f10.setValue("${myfield_resid}");
/* 5729 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5730 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5731 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5732 */       return true;
/*      */     }
/* 5734 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5735 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5740 */     PageContext pageContext = _jspx_page_context;
/* 5741 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5743 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5744 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5745 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5747 */     _jspx_th_c_005fout_005f11.setValue("${myfield_entity}");
/* 5748 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5749 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5750 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5751 */       return true;
/*      */     }
/* 5753 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5759 */     PageContext pageContext = _jspx_page_context;
/* 5760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5762 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5763 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5764 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5766 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 5767 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5768 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5769 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5770 */       return true;
/*      */     }
/* 5772 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5773 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5778 */     PageContext pageContext = _jspx_page_context;
/* 5779 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5781 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5782 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5783 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5785 */     _jspx_th_c_005fout_005f13.setValue("${param.resourcename}");
/* 5786 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5787 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5788 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5789 */       return true;
/*      */     }
/* 5791 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5792 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5797 */     PageContext pageContext = _jspx_page_context;
/* 5798 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5800 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5801 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5802 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5804 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 5805 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5806 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5807 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5808 */       return true;
/*      */     }
/* 5810 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5816 */     PageContext pageContext = _jspx_page_context;
/* 5817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5819 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5820 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5821 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5823 */     _jspx_th_c_005fout_005f15.setValue("${param.resourcename}");
/* 5824 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5825 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5826 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5827 */       return true;
/*      */     }
/* 5829 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5835 */     PageContext pageContext = _jspx_page_context;
/* 5836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5838 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 5839 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 5840 */     _jspx_th_awolf_005fpiechart_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5842 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 5844 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("300");
/*      */     
/* 5846 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */     
/* 5848 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 5850 */     _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */     
/* 5852 */     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */     
/* 5854 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 5855 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 5856 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 5857 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 5858 */         out = _jspx_page_context.pushBody();
/* 5859 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 5860 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5863 */         out.write("\n            ");
/* 5864 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 5865 */           return true;
/* 5866 */         out.write(32);
/* 5867 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 5868 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5871 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 5872 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5875 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 5876 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 5877 */       return true;
/*      */     }
/* 5879 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 5880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5885 */     PageContext pageContext = _jspx_page_context;
/* 5886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5888 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 5889 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 5890 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 5892 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 5893 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 5894 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 5895 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 5896 */         out = _jspx_page_context.pushBody();
/* 5897 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 5898 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5901 */         out.write(32);
/* 5902 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 5903 */           return true;
/* 5904 */         out.write(32);
/* 5905 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 5906 */           return true;
/* 5907 */         out.write("\n            ");
/* 5908 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 5909 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5912 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 5913 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5916 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 5917 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 5918 */       return true;
/*      */     }
/* 5920 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 5921 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5926 */     PageContext pageContext = _jspx_page_context;
/* 5927 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5929 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 5930 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 5931 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 5933 */     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */     
/* 5935 */     _jspx_th_awolf_005fparam_005f0.setValue("#29FF29");
/* 5936 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 5937 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 5938 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 5939 */       return true;
/*      */     }
/* 5941 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 5942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5947 */     PageContext pageContext = _jspx_page_context;
/* 5948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5950 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 5951 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 5952 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 5954 */     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */     
/* 5956 */     _jspx_th_awolf_005fparam_005f1.setValue("#FF0000");
/* 5957 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 5958 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 5959 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 5960 */       return true;
/*      */     }
/* 5962 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 5963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5968 */     PageContext pageContext = _jspx_page_context;
/* 5969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5971 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5972 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5973 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5975 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 5976 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5977 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5978 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5979 */       return true;
/*      */     }
/* 5981 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5987 */     PageContext pageContext = _jspx_page_context;
/* 5988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5990 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5991 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5992 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5994 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 5995 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5996 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5997 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5998 */       return true;
/*      */     }
/* 6000 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6006 */     PageContext pageContext = _jspx_page_context;
/* 6007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6009 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6010 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 6011 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6012 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 6013 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 6014 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 6015 */         out = _jspx_page_context.pushBody();
/* 6016 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 6017 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6020 */         out.write("table.heading.attribute");
/* 6021 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 6022 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6025 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 6026 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6029 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 6030 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6031 */       return true;
/*      */     }
/* 6033 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6034 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6039 */     PageContext pageContext = _jspx_page_context;
/* 6040 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6042 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6043 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 6044 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6045 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 6046 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 6047 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 6048 */         out = _jspx_page_context.pushBody();
/* 6049 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 6050 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6053 */         out.write("table.heading.value");
/* 6054 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 6055 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6058 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 6059 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6062 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 6063 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6064 */       return true;
/*      */     }
/* 6066 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6072 */     PageContext pageContext = _jspx_page_context;
/* 6073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6075 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6076 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 6077 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6078 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 6079 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 6080 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 6081 */         out = _jspx_page_context.pushBody();
/* 6082 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 6083 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6086 */         out.write("table.heading.status");
/* 6087 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 6088 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6091 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 6092 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6095 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 6096 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6097 */       return true;
/*      */     }
/* 6099 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6100 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6105 */     PageContext pageContext = _jspx_page_context;
/* 6106 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6108 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6109 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6110 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6112 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 6113 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6114 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6115 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6116 */       return true;
/*      */     }
/* 6118 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6124 */     PageContext pageContext = _jspx_page_context;
/* 6125 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6127 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6128 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6129 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6131 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 6132 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6133 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6134 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6135 */       return true;
/*      */     }
/* 6137 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6138 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6143 */     PageContext pageContext = _jspx_page_context;
/* 6144 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6146 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6147 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 6148 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6149 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 6150 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 6151 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 6152 */         out = _jspx_page_context.pushBody();
/* 6153 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 6154 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6157 */         out.write("table.heading.attribute");
/* 6158 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 6159 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6162 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 6163 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6166 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6167 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6168 */       return true;
/*      */     }
/* 6170 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6176 */     PageContext pageContext = _jspx_page_context;
/* 6177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6179 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6180 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 6181 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6182 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 6183 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 6184 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6185 */         out = _jspx_page_context.pushBody();
/* 6186 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 6187 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6190 */         out.write("table.heading.value");
/* 6191 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 6192 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6195 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6196 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6199 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 6200 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6201 */       return true;
/*      */     }
/* 6203 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6204 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6209 */     PageContext pageContext = _jspx_page_context;
/* 6210 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6212 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6213 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 6214 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6215 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 6216 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 6217 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6218 */         out = _jspx_page_context.pushBody();
/* 6219 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 6220 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6223 */         out.write("table.heading.status");
/* 6224 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 6225 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6228 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6229 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6232 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 6233 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6234 */       return true;
/*      */     }
/* 6236 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6237 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6242 */     PageContext pageContext = _jspx_page_context;
/* 6243 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6245 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6246 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6247 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6249 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 6250 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6251 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6252 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6253 */       return true;
/*      */     }
/* 6255 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6256 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6261 */     PageContext pageContext = _jspx_page_context;
/* 6262 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6264 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6265 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6266 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6268 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 6269 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6270 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6271 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6272 */       return true;
/*      */     }
/* 6274 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6275 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6280 */     PageContext pageContext = _jspx_page_context;
/* 6281 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6283 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6284 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 6285 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6286 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 6287 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 6288 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6289 */         out = _jspx_page_context.pushBody();
/* 6290 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 6291 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6294 */         out.write("table.heading.attribute");
/* 6295 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 6296 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6299 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6300 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6303 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 6304 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6305 */       return true;
/*      */     }
/* 6307 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6308 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6313 */     PageContext pageContext = _jspx_page_context;
/* 6314 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6316 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6317 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 6318 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6319 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 6320 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 6321 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6322 */         out = _jspx_page_context.pushBody();
/* 6323 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 6324 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6327 */         out.write("table.heading.value");
/* 6328 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 6329 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6332 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6333 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6336 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 6337 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6338 */       return true;
/*      */     }
/* 6340 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6341 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6346 */     PageContext pageContext = _jspx_page_context;
/* 6347 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6349 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6350 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 6351 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6352 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 6353 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 6354 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6355 */         out = _jspx_page_context.pushBody();
/* 6356 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 6357 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6360 */         out.write("table.heading.status");
/* 6361 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 6362 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6365 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6366 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6369 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 6370 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6371 */       return true;
/*      */     }
/* 6373 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6379 */     PageContext pageContext = _jspx_page_context;
/* 6380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6382 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6383 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 6384 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6385 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 6386 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 6387 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6388 */         out = _jspx_page_context.pushBody();
/* 6389 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 6390 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6393 */         out.write("Alert Configuration");
/* 6394 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 6395 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6398 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6399 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6402 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 6403 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6404 */       return true;
/*      */     }
/* 6406 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6412 */     PageContext pageContext = _jspx_page_context;
/* 6413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6415 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6416 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 6417 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6418 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 6419 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 6420 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6421 */         out = _jspx_page_context.pushBody();
/* 6422 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 6423 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6426 */         out.write("table.heading.attribute");
/* 6427 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 6428 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6431 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6432 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6435 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 6436 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6437 */       return true;
/*      */     }
/* 6439 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6440 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6445 */     PageContext pageContext = _jspx_page_context;
/* 6446 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6448 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6449 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 6450 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6451 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 6452 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 6453 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6454 */         out = _jspx_page_context.pushBody();
/* 6455 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 6456 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6459 */         out.write("table.heading.value");
/* 6460 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 6461 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6464 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6465 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6468 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 6469 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6470 */       return true;
/*      */     }
/* 6472 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6473 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6478 */     PageContext pageContext = _jspx_page_context;
/* 6479 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6481 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6482 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 6483 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6484 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 6485 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 6486 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6487 */         out = _jspx_page_context.pushBody();
/* 6488 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 6489 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6492 */         out.write("table.heading.status");
/* 6493 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 6494 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6497 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6498 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6501 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 6502 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6503 */       return true;
/*      */     }
/* 6505 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6506 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6511 */     PageContext pageContext = _jspx_page_context;
/* 6512 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6514 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6515 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 6516 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6517 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 6518 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 6519 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 6520 */         out = _jspx_page_context.pushBody();
/* 6521 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 6522 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6525 */         out.write("table.heading.attribute");
/* 6526 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 6527 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6530 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 6531 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6534 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 6535 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 6536 */       return true;
/*      */     }
/* 6538 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 6539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6544 */     PageContext pageContext = _jspx_page_context;
/* 6545 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6547 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6548 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 6549 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6550 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 6551 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 6552 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 6553 */         out = _jspx_page_context.pushBody();
/* 6554 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 6555 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6558 */         out.write("table.heading.value");
/* 6559 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 6560 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6563 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 6564 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6567 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 6568 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 6569 */       return true;
/*      */     }
/* 6571 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 6572 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6577 */     PageContext pageContext = _jspx_page_context;
/* 6578 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6580 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6581 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 6582 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6583 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 6584 */     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 6585 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 6586 */         out = _jspx_page_context.pushBody();
/* 6587 */         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 6588 */         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6591 */         out.write("table.heading.status");
/* 6592 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 6593 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6596 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 6597 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6600 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 6601 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 6602 */       return true;
/*      */     }
/* 6604 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 6605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6610 */     PageContext pageContext = _jspx_page_context;
/* 6611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6613 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6614 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 6615 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6617 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 6619 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 6620 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 6621 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 6622 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6623 */       return true;
/*      */     }
/* 6625 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6626 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ApacheDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */