/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
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
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class cam_005fnewcam_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
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
/*  862 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  863 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  866 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  867 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  868 */       set = AMConnectionPool.executeQueryStmt(query);
/*  869 */       if (set.next())
/*      */       {
/*  871 */         String helpLink = set.getString("LINK");
/*  872 */         DBUtil.searchLinks.put(key, helpLink);
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
/*  933 */     AMLog.debug("JSP : " + debugMessage);
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
/* 1481 */       message = EnterpriseUtil.decodeString(message);
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
/*      */           catch (SQLException e) {
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
/* 1714 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
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
/* 1831 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1833 */               if (maxCol != null)
/* 1834 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1836 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1831 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 1992 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
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
/* 2155 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2156 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
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
/* 2185 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2191 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2192 */   static { _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2220 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2224 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2244 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2248 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2250 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2251 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2254 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2259 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2260 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2261 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2263 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2265 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2266 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2273 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2276 */     JspWriter out = null;
/* 2277 */     Object page = this;
/* 2278 */     JspWriter _jspx_out = null;
/* 2279 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2283 */       response.setContentType("text/html;charset=UTF-8");
/* 2284 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2286 */       _jspx_page_context = pageContext;
/* 2287 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2288 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2289 */       session = pageContext.getSession();
/* 2290 */       out = pageContext.getOut();
/* 2291 */       _jspx_out = out;
/*      */       
/* 2293 */       out.write("<!DOCTYPE html>\n");
/* 2294 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n");
/*      */       
/* 2296 */       request.setAttribute("HelpKey", "Create New CAM");
/*      */       
/* 2298 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2299 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2301 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2302 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2303 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2305 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2307 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2309 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2311 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2312 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2313 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2314 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2317 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2318 */         String available = null;
/* 2319 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2320 */         out.write(10);
/*      */         
/* 2322 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2323 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2324 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2326 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2328 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2330 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2332 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2333 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2334 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2335 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2338 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2339 */           String unavailable = null;
/* 2340 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2341 */           out.write(10);
/*      */           
/* 2343 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2344 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2345 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2347 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2349 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2351 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2353 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2354 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2355 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2356 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2359 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2360 */             String unmanaged = null;
/* 2361 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2362 */             out.write(10);
/*      */             
/* 2364 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2365 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2366 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2368 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2370 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2372 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2374 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2375 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2376 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2377 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2380 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2381 */               String scheduled = null;
/* 2382 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2383 */               out.write(10);
/*      */               
/* 2385 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2386 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2387 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2389 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2391 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2393 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2395 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2396 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2397 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2398 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2401 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2402 */                 String critical = null;
/* 2403 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2404 */                 out.write(10);
/*      */                 
/* 2406 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2407 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2408 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2410 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2412 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2414 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2416 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2417 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2418 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2419 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2422 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2423 */                   String clear = null;
/* 2424 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2425 */                   out.write(10);
/*      */                   
/* 2427 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2428 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2429 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2431 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2433 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2435 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2437 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2438 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2439 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2440 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2443 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2444 */                     String warning = null;
/* 2445 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2446 */                     out.write(10);
/* 2447 */                     out.write(10);
/*      */                     
/* 2449 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2450 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2452 */                     out.write(10);
/* 2453 */                     out.write(10);
/* 2454 */                     out.write(10);
/* 2455 */                     out.write(10);
/* 2456 */                     out.write(10);
/*      */                     
/* 2458 */                     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2459 */                     if (hideFieldsForIT360 == null)
/*      */                     {
/* 2461 */                       hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */                     }
/*      */                     
/* 2464 */                     boolean hideFields = false;
/* 2465 */                     if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */                     {
/* 2467 */                       hideFields = true;
/*      */                     }
/* 2469 */                     String haid = request.getParameter("haid");
/*      */                     
/* 2471 */                     out.write(10);
/*      */                     
/* 2473 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2474 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2475 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2477 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2478 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2479 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2481 */                         out.write(32);
/* 2482 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2484 */                         out.write(32);
/* 2485 */                         out.write(10);
/* 2486 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2488 */                         out.write(" \n\n\n");
/*      */                         
/* 2490 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2491 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2492 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2494 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2496 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2497 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2498 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2499 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2500 */                             out = _jspx_page_context.pushBody();
/* 2501 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2502 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2505 */                             out.write(32);
/*      */                             
/* 2507 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 2508 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2509 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 2511 */                             _jspx_th_html_005fform_005f0.setAction("/CreateCustomApplicationForm");
/* 2512 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2513 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 2515 */                                 out.write(10);
/* 2516 */                                 out.write(10);
/*      */                                 
/* 2518 */                                 Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2519 */                                 String aid = request.getParameter("haid");
/* 2520 */                                 String haName = null;
/* 2521 */                                 if (aid != null)
/*      */                                 {
/* 2523 */                                   haName = (String)ht.get(aid);
/*      */                                 }
/*      */                                 
/* 2526 */                                 out.write(10);
/* 2527 */                                 out.write(10);
/* 2528 */                                 if (_jspx_meth_c_005fcatch_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2530 */                                 out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"itadmin-hide\">\n\t<tr>\n\t\t<td width=\"65%\" valign=\"top\">\n");
/*      */                                 
/* 2532 */                                 ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2533 */                                 _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2534 */                                 _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/* 2535 */                                 int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2536 */                                 if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                   for (;;) {
/* 2538 */                                     out.write(10);
/*      */                                     
/* 2540 */                                     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2541 */                                     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2542 */                                     _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                     
/* 2544 */                                     _jspx_th_c_005fwhen_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2545 */                                     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2546 */                                     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                       for (;;) {
/* 2548 */                                         out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/*      */                                         
/* 2550 */                                         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2551 */                                         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2552 */                                         _jspx_th_c_005fif_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                         
/* 2554 */                                         _jspx_th_c_005fif_005f0.setTest("${!empty wiz}");
/* 2555 */                                         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2556 */                                         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                           for (;;) {
/* 2558 */                                             out.write("\n    <tr>\n\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2559 */                                             out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2560 */                                             out.write(" &gt; ");
/* 2561 */                                             out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2562 */                                             out.write(" &gt; <span class=\"bcactive\"> ");
/* 2563 */                                             out.print(FormatUtil.getString("am.webclient.cam.createjmxsnmpdashboard"));
/* 2564 */                                             out.write(" </span></td>\n\n    </tr>\n");
/* 2565 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2566 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2570 */                                         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2571 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                         }
/*      */                                         
/* 2574 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2575 */                                         out.write("\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr> \n    <td height=\"19\" align=\"left\"  class=\"tableheading\"> &nbsp;");
/* 2576 */                                         out.print(FormatUtil.getString("am.webclient.cam.createjmxsnmpdashboard"));
/* 2577 */                                         out.write(" </td>\n  </tr>\n</table>\n");
/* 2578 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2579 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2583 */                                     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2584 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                     }
/*      */                                     
/* 2587 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2588 */                                     out.write(10);
/*      */                                     
/* 2590 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2591 */                                     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2592 */                                     _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2593 */                                     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2594 */                                     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                       for (;;) {
/* 2596 */                                         out.write(10);
/* 2597 */                                         out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 2598 */                                         String message = (String)request.getAttribute("typemessage");
/*      */                                         
/* 2600 */                                         ManagedApplication mo1 = new ManagedApplication();
/* 2601 */                                         Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 2602 */                                         boolean isConfMonitor = false;
/* 2603 */                                         ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 2604 */                                         if (message != null)
/*      */                                         {
/* 2606 */                                           out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 2607 */                                           out.print(message);
/* 2608 */                                           out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 2612 */                                         out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 2613 */                                         out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 2614 */                                         out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 2615 */                                         if ("UrlSeq".equals(request.getParameter("type"))) {
/* 2616 */                                           DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2617 */                                           if (frm != null) {
/* 2618 */                                             frm.set("type", "UrlSeq");
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 2622 */                                         if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 2623 */                                           DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2624 */                                           if (frm != null) {
/* 2625 */                                             frm.set("type", "UrlMonitor");
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 2629 */                                         if ("RBM".equals(request.getParameter("type"))) {
/* 2630 */                                           DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 2631 */                                           if (frm != null) {
/* 2632 */                                             frm.set("type", "RBM");
/*      */                                           }
/*      */                                         }
/*      */                                         
/*      */ 
/* 2637 */                                         out.write("\n\n    ");
/*      */                                         
/* 2639 */                                         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2640 */                                         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2641 */                                         _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                                         
/* 2643 */                                         _jspx_th_c_005fif_005f1.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 2644 */                                         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2645 */                                         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                           for (;;) {
/* 2647 */                                             out.write("\n     ");
/*      */                                             
/* 2649 */                                             SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 2650 */                                             _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2651 */                                             _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */                                             
/* 2653 */                                             _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                             
/* 2655 */                                             _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                             
/* 2657 */                                             _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                             
/* 2659 */                                             _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 2660 */                                             int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2661 */                                             if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2662 */                                               if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2663 */                                                 out = _jspx_page_context.pushBody();
/* 2664 */                                                 _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2665 */                                                 _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 2668 */                                                 out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                                 
/* 2670 */                                                 if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                                 {
/*      */ 
/*      */ 
/* 2674 */                                                   out.write("\n\n\t <optgroup label=\"");
/* 2675 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 2676 */                                                   out.write("\">\n                                        \n                                        ");
/*      */                                                   
/* 2678 */                                                   OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2679 */                                                   _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2680 */                                                   _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2682 */                                                   _jspx_th_html_005foption_005f0.setValue("AIX");
/* 2683 */                                                   int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2684 */                                                   if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2685 */                                                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2686 */                                                       out = _jspx_page_context.pushBody();
/* 2687 */                                                       _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2688 */                                                       _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2691 */                                                       out.print(FormatUtil.getString("AIX"));
/* 2692 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2693 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2696 */                                                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2697 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2700 */                                                   if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2701 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                                   }
/*      */                                                   
/* 2704 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2705 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 2707 */                                                   OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2708 */                                                   _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2709 */                                                   _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2711 */                                                   _jspx_th_html_005foption_005f1.setValue("AS400");
/* 2712 */                                                   int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2713 */                                                   if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2714 */                                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2715 */                                                       out = _jspx_page_context.pushBody();
/* 2716 */                                                       _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2717 */                                                       _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2720 */                                                       out.print(FormatUtil.getString("AS400/iSeries"));
/* 2721 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2722 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2725 */                                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2726 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2729 */                                                   if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2730 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                                   }
/*      */                                                   
/* 2733 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2734 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 2736 */                                                   OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2737 */                                                   _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2738 */                                                   _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2740 */                                                   _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 2741 */                                                   int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2742 */                                                   if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2743 */                                                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2744 */                                                       out = _jspx_page_context.pushBody();
/* 2745 */                                                       _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 2746 */                                                       _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2749 */                                                       out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 2750 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2751 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2754 */                                                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2755 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2758 */                                                   if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2759 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                                   }
/*      */                                                   
/* 2762 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2763 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 2765 */                                                   OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2766 */                                                   _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2767 */                                                   _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2769 */                                                   _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 2770 */                                                   int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2771 */                                                   if (_jspx_eval_html_005foption_005f3 != 0) {
/* 2772 */                                                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2773 */                                                       out = _jspx_page_context.pushBody();
/* 2774 */                                                       _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 2775 */                                                       _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2778 */                                                       out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 2779 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 2780 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2783 */                                                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2784 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2787 */                                                   if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2788 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                                   }
/*      */                                                   
/* 2791 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2792 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 2794 */                                                   OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2795 */                                                   _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2796 */                                                   _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2798 */                                                   _jspx_th_html_005foption_005f4.setValue("Linux");
/* 2799 */                                                   int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2800 */                                                   if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2801 */                                                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2802 */                                                       out = _jspx_page_context.pushBody();
/* 2803 */                                                       _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 2804 */                                                       _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2807 */                                                       out.print(FormatUtil.getString("Linux"));
/* 2808 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2809 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2812 */                                                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2813 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2816 */                                                   if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2817 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                                   }
/*      */                                                   
/* 2820 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2821 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 2823 */                                                   OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2824 */                                                   _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2825 */                                                   _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2827 */                                                   _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 2828 */                                                   int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2829 */                                                   if (_jspx_eval_html_005foption_005f5 != 0) {
/* 2830 */                                                     if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2831 */                                                       out = _jspx_page_context.pushBody();
/* 2832 */                                                       _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 2833 */                                                       _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2836 */                                                       out.print(FormatUtil.getString("Mac OS"));
/* 2837 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 2838 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2841 */                                                     if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2842 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2845 */                                                   if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2846 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                                   }
/*      */                                                   
/* 2849 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 2850 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 2852 */                                                   OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2853 */                                                   _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2854 */                                                   _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2856 */                                                   _jspx_th_html_005foption_005f6.setValue("Novell");
/* 2857 */                                                   int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2858 */                                                   if (_jspx_eval_html_005foption_005f6 != 0) {
/* 2859 */                                                     if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2860 */                                                       out = _jspx_page_context.pushBody();
/* 2861 */                                                       _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 2862 */                                                       _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2865 */                                                       out.print(FormatUtil.getString("Novell"));
/* 2866 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 2867 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2870 */                                                     if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2871 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2874 */                                                   if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2875 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                                   }
/*      */                                                   
/* 2878 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 2879 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 2881 */                                                   OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2882 */                                                   _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 2883 */                                                   _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2885 */                                                   _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 2886 */                                                   int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 2887 */                                                   if (_jspx_eval_html_005foption_005f7 != 0) {
/* 2888 */                                                     if (_jspx_eval_html_005foption_005f7 != 1) {
/* 2889 */                                                       out = _jspx_page_context.pushBody();
/* 2890 */                                                       _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 2891 */                                                       _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2894 */                                                       out.print(FormatUtil.getString("Sun Solaris"));
/* 2895 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 2896 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2899 */                                                     if (_jspx_eval_html_005foption_005f7 != 1) {
/* 2900 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2903 */                                                   if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 2904 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                                   }
/*      */                                                   
/* 2907 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 2908 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 2910 */                                                   OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2911 */                                                   _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 2912 */                                                   _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2914 */                                                   _jspx_th_html_005foption_005f8.setValue("Windows");
/* 2915 */                                                   int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 2916 */                                                   if (_jspx_eval_html_005foption_005f8 != 0) {
/* 2917 */                                                     if (_jspx_eval_html_005foption_005f8 != 1) {
/* 2918 */                                                       out = _jspx_page_context.pushBody();
/* 2919 */                                                       _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 2920 */                                                       _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2923 */                                                       out.print(FormatUtil.getString("Windows"));
/* 2924 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 2925 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2928 */                                                     if (_jspx_eval_html_005foption_005f8 != 1) {
/* 2929 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2932 */                                                   if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 2933 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                                   }
/*      */                                                   
/* 2936 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 2937 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 2939 */                                                   OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2940 */                                                   _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 2941 */                                                   _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 2943 */                                                   _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 2944 */                                                   int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 2945 */                                                   if (_jspx_eval_html_005foption_005f9 != 0) {
/* 2946 */                                                     if (_jspx_eval_html_005foption_005f9 != 1) {
/* 2947 */                                                       out = _jspx_page_context.pushBody();
/* 2948 */                                                       _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 2949 */                                                       _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 2952 */                                                       out.print(FormatUtil.getString("Windows Cluster"));
/* 2953 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 2954 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 2957 */                                                     if (_jspx_eval_html_005foption_005f9 != 1) {
/* 2958 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 2961 */                                                   if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 2962 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                                   }
/*      */                                                   
/* 2965 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 2966 */                                                   out.write("\n                                        \n\n  ");
/*      */                                                   
/* 2968 */                                                   ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 2969 */                                                   if ((rows1 != null) && (rows1.size() > 0))
/*      */                                                   {
/* 2971 */                                                     for (int i = 0; i < rows1.size(); i++)
/*      */                                                     {
/* 2973 */                                                       ArrayList row = (ArrayList)rows1.get(i);
/* 2974 */                                                       String res = (String)row.get(0);
/* 2975 */                                                       String dname = (String)row.get(1);
/* 2976 */                                                       String values = props.getProperty(res);
/* 2977 */                                                       if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                       {
/*      */ 
/* 2980 */                                                         out.write("\n\t\t\t\t");
/*      */                                                         
/* 2982 */                                                         OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2983 */                                                         _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 2984 */                                                         _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                         
/* 2986 */                                                         _jspx_th_html_005foption_005f10.setValue(values);
/* 2987 */                                                         int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 2988 */                                                         if (_jspx_eval_html_005foption_005f10 != 0) {
/* 2989 */                                                           if (_jspx_eval_html_005foption_005f10 != 1) {
/* 2990 */                                                             out = _jspx_page_context.pushBody();
/* 2991 */                                                             _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 2992 */                                                             _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                           }
/*      */                                                           for (;;) {
/* 2995 */                                                             out.write(32);
/* 2996 */                                                             out.print(FormatUtil.getString(dname));
/* 2997 */                                                             int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 2998 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/* 3001 */                                                           if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3002 */                                                             out = _jspx_page_context.popBody();
/*      */                                                           }
/*      */                                                         }
/* 3005 */                                                         if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3006 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                         }
/*      */                                                         
/* 3009 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3010 */                                                         out.write("\n\t\t\t");
/*      */                                                       }
/*      */                                                     }
/*      */                                                   }
/*      */                                                   
/*      */ 
/* 3016 */                                                   String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                                   
/* 3018 */                                                   String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                                   
/*      */ 
/* 3021 */                                                   if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                                   {
/*      */ 
/* 3024 */                                                     categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3025 */                                                     categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                                   }
/* 3027 */                                                   for (int c = 0; c < categoryLink.length; c++)
/*      */                                                   {
/* 3029 */                                                     ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 3030 */                                                     if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                                     {
/*      */ 
/*      */ 
/* 3034 */                                                       StringBuffer queryBuf = new StringBuffer();
/* 3035 */                                                       queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 3036 */                                                       queryBuf.append(categoryLink[c] + "'");
/* 3037 */                                                       queryBuf.append(" ");
/* 3038 */                                                       queryBuf.append("and RESOURCETYPE in");
/* 3039 */                                                       queryBuf.append(" ");
/* 3040 */                                                       queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 3041 */                                                       if (categoryLink[c].equals("APP"))
/*      */                                                       {
/* 3043 */                                                         queryBuf.append(" ");
/* 3044 */                                                         queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 3045 */                                                         queryBuf.append(" ");
/*      */                                                       }
/* 3047 */                                                       else if (categoryLink[c].equals("SER"))
/*      */                                                       {
/* 3049 */                                                         queryBuf.append(" ");
/* 3050 */                                                         queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 3051 */                                                         queryBuf.append(" ");
/*      */                                                       }
/* 3053 */                                                       else if (categoryLink[c].equals("CAM"))
/*      */                                                       {
/* 3055 */                                                         queryBuf.append(" ");
/* 3056 */                                                         queryBuf.append("and RESOURCETYPE != 'directory'");
/* 3057 */                                                         queryBuf.append(" ");
/*      */                                                       }
/* 3059 */                                                       queryBuf.append(" ");
/* 3060 */                                                       queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 3061 */                                                       ArrayList rows = mo1.getRows(queryBuf.toString());
/* 3062 */                                                       if ((rows != null) && (rows.size() != 0))
/*      */                                                       {
/*      */ 
/*      */ 
/*      */ 
/* 3067 */                                                         out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 3068 */                                                         out.print(FormatUtil.getString(categoryTitle[c]));
/* 3069 */                                                         out.write(34);
/* 3070 */                                                         out.write(62);
/* 3071 */                                                         out.write(10);
/*      */                                                         
/*      */ 
/* 3074 */                                                         for (int i = 0; i < rows.size(); i++)
/*      */                                                         {
/* 3076 */                                                           ArrayList row = (ArrayList)rows.get(i);
/* 3077 */                                                           String res = (String)row.get(0);
/* 3078 */                                                           if (res.equals("file"))
/*      */                                                           {
/* 3080 */                                                             res = "File / Directory Monitor";
/*      */                                                           }
/* 3082 */                                                           String dname = (String)row.get(1);
/* 3083 */                                                           String values = props.getProperty(res);
/* 3084 */                                                           if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                           {
/*      */ 
/* 3087 */                                                             if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                             {
/*      */ 
/* 3090 */                                                               out.write("\n\t\t\t\t \t");
/*      */                                                               
/* 3092 */                                                               OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3093 */                                                               _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 3094 */                                                               _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                               
/* 3096 */                                                               _jspx_th_html_005foption_005f11.setValue(values);
/* 3097 */                                                               int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 3098 */                                                               if (_jspx_eval_html_005foption_005f11 != 0) {
/* 3099 */                                                                 if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3100 */                                                                   out = _jspx_page_context.pushBody();
/* 3101 */                                                                   _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 3102 */                                                                   _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                                 }
/*      */                                                                 for (;;) {
/* 3105 */                                                                   out.write(32);
/* 3106 */                                                                   out.print(FormatUtil.getString(dname));
/* 3107 */                                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 3108 */                                                                   if (evalDoAfterBody != 2)
/*      */                                                                     break;
/*      */                                                                 }
/* 3111 */                                                                 if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3112 */                                                                   out = _jspx_page_context.popBody();
/*      */                                                                 }
/*      */                                                               }
/* 3115 */                                                               if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 3116 */                                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                               }
/*      */                                                               
/* 3119 */                                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 3120 */                                                               out.write("\n\t\t\t\t");
/*      */                                                             }
/*      */                                                           }
/*      */                                                         }
/*      */                                                         
/* 3125 */                                                         if (categoryLink[c].equals("VIR"))
/*      */                                                         {
/*      */ 
/* 3128 */                                                           out.write("\n\t\t\t\t\t");
/*      */                                                           
/* 3130 */                                                           OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3131 */                                                           _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 3132 */                                                           _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                           
/* 3134 */                                                           _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 3135 */                                                           int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 3136 */                                                           if (_jspx_eval_html_005foption_005f12 != 0) {
/* 3137 */                                                             if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3138 */                                                               out = _jspx_page_context.pushBody();
/* 3139 */                                                               _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 3140 */                                                               _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                             }
/*      */                                                             for (;;) {
/* 3143 */                                                               out.write(32);
/* 3144 */                                                               out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 3145 */                                                               int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 3146 */                                                               if (evalDoAfterBody != 2)
/*      */                                                                 break;
/*      */                                                             }
/* 3149 */                                                             if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3150 */                                                               out = _jspx_page_context.popBody();
/*      */                                                             }
/*      */                                                           }
/* 3153 */                                                           if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 3154 */                                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                           }
/*      */                                                           
/* 3157 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 3158 */                                                           out.write("\n\t\t\t\t");
/*      */                                                         }
/*      */                                                       }
/*      */                                                     } }
/* 3162 */                                                   String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 3163 */                                                   if (!usertype.equals("F"))
/*      */                                                   {
/* 3165 */                                                     if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                                     {
/* 3167 */                                                       out.write("\n    </optgroup> <optgroup label=\"");
/* 3168 */                                                       out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3169 */                                                       out.write("\">\n     ");
/*      */                                                       
/* 3171 */                                                       OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3172 */                                                       _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 3173 */                                                       _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3175 */                                                       _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 3176 */                                                       int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 3177 */                                                       if (_jspx_eval_html_005foption_005f13 != 0) {
/* 3178 */                                                         if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3179 */                                                           out = _jspx_page_context.pushBody();
/* 3180 */                                                           _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 3181 */                                                           _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3184 */                                                           out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3185 */                                                           out.write(32);
/* 3186 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 3187 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3190 */                                                         if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3191 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3194 */                                                       if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 3195 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                       }
/*      */                                                       
/* 3198 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 3199 */                                                       out.write("\n\n     ");
/*      */                                                     }
/*      */                                                     
/*      */                                                   }
/*      */                                                   
/*      */                                                 }
/* 3205 */                                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                                 {
/*      */ 
/* 3208 */                                                   out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 3209 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3210 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 3212 */                                                   OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3213 */                                                   _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 3214 */                                                   _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3216 */                                                   _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 3217 */                                                   int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 3218 */                                                   if (_jspx_eval_html_005foption_005f14 != 0) {
/* 3219 */                                                     if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3220 */                                                       out = _jspx_page_context.pushBody();
/* 3221 */                                                       _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 3222 */                                                       _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3225 */                                                       out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3226 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 3227 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3230 */                                                     if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3231 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3234 */                                                   if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 3235 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                                   }
/*      */                                                   
/* 3238 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 3239 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3240 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3241 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 3243 */                                                   OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3244 */                                                   _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 3245 */                                                   _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3247 */                                                   _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 3248 */                                                   int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 3249 */                                                   if (_jspx_eval_html_005foption_005f15 != 0) {
/* 3250 */                                                     if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3251 */                                                       out = _jspx_page_context.pushBody();
/* 3252 */                                                       _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 3253 */                                                       _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3256 */                                                       out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 3257 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 3258 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3261 */                                                     if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3262 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3265 */                                                   if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 3266 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                                   }
/*      */                                                   
/* 3269 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 3270 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3271 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3272 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 3274 */                                                   OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3275 */                                                   _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 3276 */                                                   _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3278 */                                                   _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 3279 */                                                   int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 3280 */                                                   if (_jspx_eval_html_005foption_005f16 != 0) {
/* 3281 */                                                     if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3282 */                                                       out = _jspx_page_context.pushBody();
/* 3283 */                                                       _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 3284 */                                                       _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3287 */                                                       out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3288 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 3289 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3292 */                                                     if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3293 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3296 */                                                   if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 3297 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                                   }
/*      */                                                   
/* 3300 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 3301 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3303 */                                                   OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3304 */                                                   _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 3305 */                                                   _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3307 */                                                   _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 3308 */                                                   int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 3309 */                                                   if (_jspx_eval_html_005foption_005f17 != 0) {
/* 3310 */                                                     if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3311 */                                                       out = _jspx_page_context.pushBody();
/* 3312 */                                                       _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 3313 */                                                       _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3316 */                                                       out.write(32);
/* 3317 */                                                       out.print(FormatUtil.getString("Service Monitoring"));
/* 3318 */                                                       out.write(32);
/* 3319 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 3320 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3323 */                                                     if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3324 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3327 */                                                   if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3328 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                                   }
/*      */                                                   
/* 3331 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3332 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3334 */                                                   OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3335 */                                                   _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3336 */                                                   _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3338 */                                                   _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 3339 */                                                   int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3340 */                                                   if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3341 */                                                     if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3342 */                                                       out = _jspx_page_context.pushBody();
/* 3343 */                                                       _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3344 */                                                       _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3347 */                                                       out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3348 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3349 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3352 */                                                     if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3353 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3356 */                                                   if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3357 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                                   }
/*      */                                                   
/* 3360 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3361 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3363 */                                                   OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3364 */                                                   _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3365 */                                                   _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3367 */                                                   _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 3368 */                                                   int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3369 */                                                   if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3370 */                                                     if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3371 */                                                       out = _jspx_page_context.pushBody();
/* 3372 */                                                       _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3373 */                                                       _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3376 */                                                       out.print(FormatUtil.getString("SNMP"));
/* 3377 */                                                       out.write(" (V1 or V2c)");
/* 3378 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3379 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3382 */                                                     if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3383 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3386 */                                                   if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3387 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                                   }
/*      */                                                   
/* 3390 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3391 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3393 */                                                   OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3394 */                                                   _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3395 */                                                   _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3397 */                                                   _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 3398 */                                                   int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3399 */                                                   if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3400 */                                                     if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3401 */                                                       out = _jspx_page_context.pushBody();
/* 3402 */                                                       _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3403 */                                                       _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3406 */                                                       out.print(FormatUtil.getString("Telnet"));
/* 3407 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3408 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3411 */                                                     if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3412 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3415 */                                                   if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3416 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                                   }
/*      */                                                   
/* 3419 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3420 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3421 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3422 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 3424 */                                                   OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3425 */                                                   _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3426 */                                                   _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3428 */                                                   _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 3429 */                                                   int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3430 */                                                   if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3431 */                                                     if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3432 */                                                       out = _jspx_page_context.pushBody();
/* 3433 */                                                       _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3434 */                                                       _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3437 */                                                       out.write(32);
/* 3438 */                                                       out.print(FormatUtil.getString("Apache Server"));
/* 3439 */                                                       out.write(32);
/* 3440 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3441 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3444 */                                                     if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3445 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3448 */                                                   if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3449 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                                   }
/*      */                                                   
/* 3452 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3453 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3455 */                                                   OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3456 */                                                   _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3457 */                                                   _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3459 */                                                   _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 3460 */                                                   int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3461 */                                                   if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3462 */                                                     if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3463 */                                                       out = _jspx_page_context.pushBody();
/* 3464 */                                                       _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3465 */                                                       _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3468 */                                                       out.print(FormatUtil.getString("PHP"));
/* 3469 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3470 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3473 */                                                     if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3474 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3477 */                                                   if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3478 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                                   }
/*      */                                                   
/* 3481 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3482 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3484 */                                                   OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3485 */                                                   _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3486 */                                                   _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3488 */                                                   _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 3489 */                                                   int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3490 */                                                   if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3491 */                                                     if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3492 */                                                       out = _jspx_page_context.pushBody();
/* 3493 */                                                       _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3494 */                                                       _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3497 */                                                       out.print(FormatUtil.getString("HTTP-URLs"));
/* 3498 */                                                       out.write(32);
/* 3499 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3500 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3503 */                                                     if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3504 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3507 */                                                   if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3508 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                                   }
/*      */                                                   
/* 3511 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3512 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3514 */                                                   OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3515 */                                                   _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3516 */                                                   _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3518 */                                                   _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 3519 */                                                   int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3520 */                                                   if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3521 */                                                     if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3522 */                                                       out = _jspx_page_context.pushBody();
/* 3523 */                                                       _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3524 */                                                       _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3527 */                                                       out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 3528 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3529 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3532 */                                                     if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3533 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3536 */                                                   if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3537 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                                   }
/*      */                                                   
/* 3540 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3541 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3543 */                                                   OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3544 */                                                   _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3545 */                                                   _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3547 */                                                   _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 3548 */                                                   int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3549 */                                                   if (_jspx_eval_html_005foption_005f25 != 0) {
/* 3550 */                                                     if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3551 */                                                       out = _jspx_page_context.pushBody();
/* 3552 */                                                       _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 3553 */                                                       _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3556 */                                                       out.write(32);
/* 3557 */                                                       out.print(FormatUtil.getString("Web Server"));
/* 3558 */                                                       out.write(32);
/* 3559 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 3560 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3563 */                                                     if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3564 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3567 */                                                   if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 3568 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                                   }
/*      */                                                   
/* 3571 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 3572 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3574 */                                                   OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3575 */                                                   _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 3576 */                                                   _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3578 */                                                   _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 3579 */                                                   int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 3580 */                                                   if (_jspx_eval_html_005foption_005f26 != 0) {
/* 3581 */                                                     if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3582 */                                                       out = _jspx_page_context.pushBody();
/* 3583 */                                                       _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 3584 */                                                       _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3587 */                                                       out.write(32);
/* 3588 */                                                       out.print(FormatUtil.getString("Web Service"));
/* 3589 */                                                       out.write(32);
/* 3590 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 3591 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3594 */                                                     if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3595 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3598 */                                                   if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 3599 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                                   }
/*      */                                                   
/* 3602 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 3603 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3604 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 3605 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 3607 */                                                   OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3608 */                                                   _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 3609 */                                                   _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3611 */                                                   _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 3612 */                                                   int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 3613 */                                                   if (_jspx_eval_html_005foption_005f27 != 0) {
/* 3614 */                                                     if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3615 */                                                       out = _jspx_page_context.pushBody();
/* 3616 */                                                       _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 3617 */                                                       _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3620 */                                                       out.print(FormatUtil.getString("Mail Server"));
/* 3621 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 3622 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3625 */                                                     if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3626 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3629 */                                                   if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 3630 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                                   }
/*      */                                                   
/* 3633 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 3634 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3635 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 3636 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 3638 */                                                   OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3639 */                                                   _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 3640 */                                                   _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3642 */                                                   _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 3643 */                                                   int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 3644 */                                                   if (_jspx_eval_html_005foption_005f28 != 0) {
/* 3645 */                                                     if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3646 */                                                       out = _jspx_page_context.pushBody();
/* 3647 */                                                       _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 3648 */                                                       _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3651 */                                                       out.write(32);
/* 3652 */                                                       out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 3653 */                                                       out.write(32);
/* 3654 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 3655 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3658 */                                                     if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3659 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3662 */                                                   if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 3663 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                                   }
/*      */                                                   
/* 3666 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 3667 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 3669 */                                                   OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3670 */                                                   _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 3671 */                                                   _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3673 */                                                   _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 3674 */                                                   int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 3675 */                                                   if (_jspx_eval_html_005foption_005f29 != 0) {
/* 3676 */                                                     if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3677 */                                                       out = _jspx_page_context.pushBody();
/* 3678 */                                                       _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 3679 */                                                       _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3682 */                                                       out.print(FormatUtil.getString("Script Monitor"));
/* 3683 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 3684 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3687 */                                                     if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3688 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3691 */                                                   if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 3692 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                                   }
/*      */                                                   
/* 3695 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 3696 */                                                   out.write("\n\n    ");
/*      */ 
/*      */                                                 }
/* 3699 */                                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                                 {
/*      */ 
/* 3702 */                                                   out.write("\n        ");
/*      */                                                   
/* 3704 */                                                   OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3705 */                                                   _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 3706 */                                                   _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3708 */                                                   _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 3709 */                                                   int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 3710 */                                                   if (_jspx_eval_html_005foption_005f30 != 0) {
/* 3711 */                                                     if (_jspx_eval_html_005foption_005f30 != 1) {
/* 3712 */                                                       out = _jspx_page_context.pushBody();
/* 3713 */                                                       _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 3714 */                                                       _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3717 */                                                       out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3718 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 3719 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3722 */                                                     if (_jspx_eval_html_005foption_005f30 != 1) {
/* 3723 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3726 */                                                   if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 3727 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                                   }
/*      */                                                   
/* 3730 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 3731 */                                                   out.write("\n       ");
/*      */                                                   
/* 3733 */                                                   OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3734 */                                                   _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 3735 */                                                   _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3737 */                                                   _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 3738 */                                                   int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 3739 */                                                   if (_jspx_eval_html_005foption_005f31 != 0) {
/* 3740 */                                                     if (_jspx_eval_html_005foption_005f31 != 1) {
/* 3741 */                                                       out = _jspx_page_context.pushBody();
/* 3742 */                                                       _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 3743 */                                                       _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3746 */                                                       out.write(32);
/* 3747 */                                                       out.print(FormatUtil.getString("JBoss Server"));
/* 3748 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 3749 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3752 */                                                     if (_jspx_eval_html_005foption_005f31 != 1) {
/* 3753 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3756 */                                                   if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 3757 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                                   }
/*      */                                                   
/* 3760 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 3761 */                                                   out.write("\n      ");
/*      */                                                   
/* 3763 */                                                   OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3764 */                                                   _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 3765 */                                                   _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3767 */                                                   _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 3768 */                                                   int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 3769 */                                                   if (_jspx_eval_html_005foption_005f32 != 0) {
/* 3770 */                                                     if (_jspx_eval_html_005foption_005f32 != 1) {
/* 3771 */                                                       out = _jspx_page_context.pushBody();
/* 3772 */                                                       _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 3773 */                                                       _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3776 */                                                       out.print(FormatUtil.getString("Tomcat Server"));
/* 3777 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 3778 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3781 */                                                     if (_jspx_eval_html_005foption_005f32 != 1) {
/* 3782 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3785 */                                                   if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 3786 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                                   }
/*      */                                                   
/* 3789 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 3790 */                                                   out.write("\n       ");
/*      */                                                   
/* 3792 */                                                   OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3793 */                                                   _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 3794 */                                                   _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3796 */                                                   _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 3797 */                                                   int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 3798 */                                                   if (_jspx_eval_html_005foption_005f33 != 0) {
/* 3799 */                                                     if (_jspx_eval_html_005foption_005f33 != 1) {
/* 3800 */                                                       out = _jspx_page_context.pushBody();
/* 3801 */                                                       _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 3802 */                                                       _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3805 */                                                       out.write(32);
/* 3806 */                                                       out.print(FormatUtil.getString("WebLogic Server"));
/* 3807 */                                                       out.write(32);
/* 3808 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 3809 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3812 */                                                     if (_jspx_eval_html_005foption_005f33 != 1) {
/* 3813 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3816 */                                                   if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 3817 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                                   }
/*      */                                                   
/* 3820 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 3821 */                                                   out.write("\n      ");
/*      */                                                   
/* 3823 */                                                   OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3824 */                                                   _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 3825 */                                                   _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3827 */                                                   _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 3828 */                                                   int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 3829 */                                                   if (_jspx_eval_html_005foption_005f34 != 0) {
/* 3830 */                                                     if (_jspx_eval_html_005foption_005f34 != 1) {
/* 3831 */                                                       out = _jspx_page_context.pushBody();
/* 3832 */                                                       _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 3833 */                                                       _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3836 */                                                       out.write(32);
/* 3837 */                                                       out.print(FormatUtil.getString("WebSphere Server"));
/* 3838 */                                                       out.write(32);
/* 3839 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 3840 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3843 */                                                     if (_jspx_eval_html_005foption_005f34 != 1) {
/* 3844 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3847 */                                                   if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 3848 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                                   }
/*      */                                                   
/* 3851 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 3852 */                                                   out.write("\n      ");
/*      */                                                   
/* 3854 */                                                   OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3855 */                                                   _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 3856 */                                                   _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3858 */                                                   _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 3859 */                                                   int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 3860 */                                                   if (_jspx_eval_html_005foption_005f35 != 0) {
/* 3861 */                                                     if (_jspx_eval_html_005foption_005f35 != 1) {
/* 3862 */                                                       out = _jspx_page_context.pushBody();
/* 3863 */                                                       _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 3864 */                                                       _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3867 */                                                       out.print(FormatUtil.getString("Web Transactions"));
/* 3868 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 3869 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3872 */                                                     if (_jspx_eval_html_005foption_005f35 != 1) {
/* 3873 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3876 */                                                   if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 3877 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                                   }
/*      */                                                   
/* 3880 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 3881 */                                                   out.write("\n      ");
/*      */                                                   
/* 3883 */                                                   OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3884 */                                                   _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 3885 */                                                   _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3887 */                                                   _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 3888 */                                                   int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 3889 */                                                   if (_jspx_eval_html_005foption_005f36 != 0) {
/* 3890 */                                                     if (_jspx_eval_html_005foption_005f36 != 1) {
/* 3891 */                                                       out = _jspx_page_context.pushBody();
/* 3892 */                                                       _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 3893 */                                                       _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3896 */                                                       out.print(FormatUtil.getString("Mail Server"));
/* 3897 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 3898 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3901 */                                                     if (_jspx_eval_html_005foption_005f36 != 1) {
/* 3902 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3905 */                                                   if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 3906 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                                   }
/*      */                                                   
/* 3909 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 3910 */                                                   out.write("\n      ");
/*      */                                                   
/* 3912 */                                                   OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3913 */                                                   _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 3914 */                                                   _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3916 */                                                   _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 3917 */                                                   int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 3918 */                                                   if (_jspx_eval_html_005foption_005f37 != 0) {
/* 3919 */                                                     if (_jspx_eval_html_005foption_005f37 != 1) {
/* 3920 */                                                       out = _jspx_page_context.pushBody();
/* 3921 */                                                       _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 3922 */                                                       _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3925 */                                                       out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3926 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 3927 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3930 */                                                     if (_jspx_eval_html_005foption_005f37 != 1) {
/* 3931 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3934 */                                                   if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 3935 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                                   }
/*      */                                                   
/* 3938 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 3939 */                                                   out.write("\n      ");
/*      */                                                   
/* 3941 */                                                   OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3942 */                                                   _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 3943 */                                                   _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3945 */                                                   _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 3946 */                                                   int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 3947 */                                                   if (_jspx_eval_html_005foption_005f38 != 0) {
/* 3948 */                                                     if (_jspx_eval_html_005foption_005f38 != 1) {
/* 3949 */                                                       out = _jspx_page_context.pushBody();
/* 3950 */                                                       _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 3951 */                                                       _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3954 */                                                       out.write(32);
/* 3955 */                                                       out.print(FormatUtil.getString("Service Monitoring"));
/* 3956 */                                                       out.write(32);
/* 3957 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 3958 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3961 */                                                     if (_jspx_eval_html_005foption_005f38 != 1) {
/* 3962 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3965 */                                                   if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 3966 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                                   }
/*      */                                                   
/* 3969 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 3970 */                                                   out.write("\n      ");
/*      */                                                   
/* 3972 */                                                   OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3973 */                                                   _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 3974 */                                                   _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3976 */                                                   _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 3977 */                                                   int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 3978 */                                                   if (_jspx_eval_html_005foption_005f39 != 0) {
/* 3979 */                                                     if (_jspx_eval_html_005foption_005f39 != 1) {
/* 3980 */                                                       out = _jspx_page_context.pushBody();
/* 3981 */                                                       _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 3982 */                                                       _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3985 */                                                       out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3986 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 3987 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3990 */                                                     if (_jspx_eval_html_005foption_005f39 != 1) {
/* 3991 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3994 */                                                   if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 3995 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                                   }
/*      */                                                   
/* 3998 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 3999 */                                                   out.write("\n      ");
/*      */                                                   
/* 4001 */                                                   OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4002 */                                                   _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 4003 */                                                   _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4005 */                                                   _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 4006 */                                                   int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 4007 */                                                   if (_jspx_eval_html_005foption_005f40 != 0) {
/* 4008 */                                                     if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4009 */                                                       out = _jspx_page_context.pushBody();
/* 4010 */                                                       _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 4011 */                                                       _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4014 */                                                       out.print(FormatUtil.getString("SNMP"));
/* 4015 */                                                       out.write(" (V1 or V2c)");
/* 4016 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 4017 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4020 */                                                     if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4021 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4024 */                                                   if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 4025 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                                   }
/*      */                                                   
/* 4028 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 4029 */                                                   out.write("\n      ");
/*      */                                                   
/* 4031 */                                                   OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4032 */                                                   _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 4033 */                                                   _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4035 */                                                   _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 4036 */                                                   int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 4037 */                                                   if (_jspx_eval_html_005foption_005f41 != 0) {
/* 4038 */                                                     if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4039 */                                                       out = _jspx_page_context.pushBody();
/* 4040 */                                                       _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 4041 */                                                       _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4044 */                                                       out.write(32);
/* 4045 */                                                       out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4046 */                                                       out.write(32);
/* 4047 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 4048 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4051 */                                                     if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4052 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4055 */                                                   if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 4056 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                                   }
/*      */                                                   
/* 4059 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 4060 */                                                   out.write("\n      ");
/*      */                                                   
/* 4062 */                                                   OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4063 */                                                   _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 4064 */                                                   _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4066 */                                                   _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 4067 */                                                   int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 4068 */                                                   if (_jspx_eval_html_005foption_005f42 != 0) {
/* 4069 */                                                     if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4070 */                                                       out = _jspx_page_context.pushBody();
/* 4071 */                                                       _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 4072 */                                                       _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4075 */                                                       out.print(FormatUtil.getString("Script Monitor"));
/* 4076 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 4077 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4080 */                                                     if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4081 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4084 */                                                   if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 4085 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                                   }
/*      */                                                   
/* 4088 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 4089 */                                                   out.write("\n       ");
/*      */ 
/*      */                                                 }
/* 4092 */                                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                                 {
/*      */ 
/* 4095 */                                                   out.write("\n        ");
/*      */                                                   
/* 4097 */                                                   OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4098 */                                                   _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 4099 */                                                   _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4101 */                                                   _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 4102 */                                                   int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 4103 */                                                   if (_jspx_eval_html_005foption_005f43 != 0) {
/* 4104 */                                                     if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4105 */                                                       out = _jspx_page_context.pushBody();
/* 4106 */                                                       _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 4107 */                                                       _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4110 */                                                       out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4111 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 4112 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4115 */                                                     if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4116 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4119 */                                                   if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 4120 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                                   }
/*      */                                                   
/* 4123 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 4124 */                                                   out.write("\n       ");
/*      */                                                   
/* 4126 */                                                   OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4127 */                                                   _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 4128 */                                                   _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4130 */                                                   _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 4131 */                                                   int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 4132 */                                                   if (_jspx_eval_html_005foption_005f44 != 0) {
/* 4133 */                                                     if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4134 */                                                       out = _jspx_page_context.pushBody();
/* 4135 */                                                       _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 4136 */                                                       _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4139 */                                                       out.print(FormatUtil.getString("Microsoft .NET"));
/* 4140 */                                                       out.write(32);
/* 4141 */                                                       out.write(32);
/* 4142 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 4143 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4146 */                                                     if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4147 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4150 */                                                   if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 4151 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                                   }
/*      */                                                   
/* 4154 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 4155 */                                                   out.write("\n      ");
/*      */                                                   
/* 4157 */                                                   OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4158 */                                                   _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 4159 */                                                   _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4161 */                                                   _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 4162 */                                                   int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 4163 */                                                   if (_jspx_eval_html_005foption_005f45 != 0) {
/* 4164 */                                                     if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4165 */                                                       out = _jspx_page_context.pushBody();
/* 4166 */                                                       _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 4167 */                                                       _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4170 */                                                       out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4171 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 4172 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4175 */                                                     if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4176 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4179 */                                                   if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 4180 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                                   }
/*      */                                                   
/* 4183 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 4184 */                                                   out.write("\n      ");
/*      */                                                   
/* 4186 */                                                   OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4187 */                                                   _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 4188 */                                                   _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4190 */                                                   _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 4191 */                                                   int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 4192 */                                                   if (_jspx_eval_html_005foption_005f46 != 0) {
/* 4193 */                                                     if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4194 */                                                       out = _jspx_page_context.pushBody();
/* 4195 */                                                       _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 4196 */                                                       _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4199 */                                                       out.print(FormatUtil.getString("Exchange Server"));
/* 4200 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 4201 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4204 */                                                     if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4205 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4208 */                                                   if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 4209 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                                   }
/*      */                                                   
/* 4212 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 4213 */                                                   out.write("\n\t  ");
/*      */                                                   
/* 4215 */                                                   OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4216 */                                                   _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 4217 */                                                   _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4219 */                                                   _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 4220 */                                                   int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 4221 */                                                   if (_jspx_eval_html_005foption_005f47 != 0) {
/* 4222 */                                                     if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4223 */                                                       out = _jspx_page_context.pushBody();
/* 4224 */                                                       _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 4225 */                                                       _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4228 */                                                       out.write(32);
/* 4229 */                                                       out.print(FormatUtil.getString("IIS Server"));
/* 4230 */                                                       out.write(32);
/* 4231 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 4232 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4235 */                                                     if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4236 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4239 */                                                   if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 4240 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                                   }
/*      */                                                   
/* 4243 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 4244 */                                                   out.write("\n      ");
/*      */                                                   
/* 4246 */                                                   OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4247 */                                                   _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 4248 */                                                   _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4250 */                                                   _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 4251 */                                                   int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 4252 */                                                   if (_jspx_eval_html_005foption_005f48 != 0) {
/* 4253 */                                                     if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4254 */                                                       out = _jspx_page_context.pushBody();
/* 4255 */                                                       _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 4256 */                                                       _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4259 */                                                       out.write(32);
/* 4260 */                                                       out.print(FormatUtil.getString("Service Monitoring"));
/* 4261 */                                                       out.write(32);
/* 4262 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 4263 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4266 */                                                     if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4267 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4270 */                                                   if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 4271 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                                   }
/*      */                                                   
/* 4274 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 4275 */                                                   out.write("\n\t  ");
/*      */                                                   
/* 4277 */                                                   OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4278 */                                                   _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 4279 */                                                   _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4281 */                                                   _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 4282 */                                                   int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 4283 */                                                   if (_jspx_eval_html_005foption_005f49 != 0) {
/* 4284 */                                                     if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4285 */                                                       out = _jspx_page_context.pushBody();
/* 4286 */                                                       _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 4287 */                                                       _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4290 */                                                       out.print(FormatUtil.getString("SNMP"));
/* 4291 */                                                       out.write(" (V1 or V2c)");
/* 4292 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 4293 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4296 */                                                     if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4297 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4300 */                                                   if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 4301 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                                   }
/*      */                                                   
/* 4304 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 4305 */                                                   out.write("\n      ");
/*      */                                                   
/* 4307 */                                                   OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4308 */                                                   _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 4309 */                                                   _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4311 */                                                   _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 4312 */                                                   int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 4313 */                                                   if (_jspx_eval_html_005foption_005f50 != 0) {
/* 4314 */                                                     if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4315 */                                                       out = _jspx_page_context.pushBody();
/* 4316 */                                                       _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 4317 */                                                       _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4320 */                                                       out.print(FormatUtil.getString("Script Monitor"));
/* 4321 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 4322 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4325 */                                                     if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4326 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4329 */                                                   if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 4330 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                                   }
/*      */                                                   
/* 4333 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 4334 */                                                   out.write(10);
/*      */ 
/*      */                                                 }
/* 4337 */                                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                                 {
/*      */ 
/* 4340 */                                                   out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 4341 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4342 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 4344 */                                                   OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4345 */                                                   _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 4346 */                                                   _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4348 */                                                   _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 4349 */                                                   int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 4350 */                                                   if (_jspx_eval_html_005foption_005f51 != 0) {
/* 4351 */                                                     if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4352 */                                                       out = _jspx_page_context.pushBody();
/* 4353 */                                                       _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 4354 */                                                       _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4357 */                                                       out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4358 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 4359 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4362 */                                                     if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4363 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4366 */                                                   if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 4367 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                                   }
/*      */                                                   
/* 4370 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 4371 */                                                   out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4372 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4373 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 4375 */                                                   OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4376 */                                                   _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 4377 */                                                   _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4379 */                                                   _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 4380 */                                                   int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 4381 */                                                   if (_jspx_eval_html_005foption_005f52 != 0) {
/* 4382 */                                                     if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4383 */                                                       out = _jspx_page_context.pushBody();
/* 4384 */                                                       _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 4385 */                                                       _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4388 */                                                       out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 4389 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 4390 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4393 */                                                     if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4394 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4397 */                                                   if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 4398 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                                   }
/*      */                                                   
/* 4401 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 4402 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 4404 */                                                   OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4405 */                                                   _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 4406 */                                                   _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4408 */                                                   _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 4409 */                                                   int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 4410 */                                                   if (_jspx_eval_html_005foption_005f53 != 0) {
/* 4411 */                                                     if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4412 */                                                       out = _jspx_page_context.pushBody();
/* 4413 */                                                       _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 4414 */                                                       _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4417 */                                                       out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4418 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 4419 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4422 */                                                     if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4423 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4426 */                                                   if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 4427 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                                   }
/*      */                                                   
/* 4430 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 4431 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 4433 */                                                   OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4434 */                                                   _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 4435 */                                                   _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4437 */                                                   _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 4438 */                                                   int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 4439 */                                                   if (_jspx_eval_html_005foption_005f54 != 0) {
/* 4440 */                                                     if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4441 */                                                       out = _jspx_page_context.pushBody();
/* 4442 */                                                       _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 4443 */                                                       _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4446 */                                                       out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4447 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 4448 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4451 */                                                     if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4452 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4455 */                                                   if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 4456 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                                   }
/*      */                                                   
/* 4459 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 4460 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 4462 */                                                   OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4463 */                                                   _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 4464 */                                                   _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4466 */                                                   _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 4467 */                                                   int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 4468 */                                                   if (_jspx_eval_html_005foption_005f55 != 0) {
/* 4469 */                                                     if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4470 */                                                       out = _jspx_page_context.pushBody();
/* 4471 */                                                       _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 4472 */                                                       _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4475 */                                                       out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 4476 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 4477 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4480 */                                                     if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4481 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4484 */                                                   if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 4485 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                                   }
/*      */                                                   
/* 4488 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 4489 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 4491 */                                                   OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4492 */                                                   _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 4493 */                                                   _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4495 */                                                   _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 4496 */                                                   int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 4497 */                                                   if (_jspx_eval_html_005foption_005f56 != 0) {
/* 4498 */                                                     if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4499 */                                                       out = _jspx_page_context.pushBody();
/* 4500 */                                                       _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 4501 */                                                       _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4504 */                                                       out.print(FormatUtil.getString("Sybase"));
/* 4505 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 4506 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4509 */                                                     if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4510 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4513 */                                                   if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 4514 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                                   }
/*      */                                                   
/* 4517 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 4518 */                                                   out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4519 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4520 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 4522 */                                                   OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4523 */                                                   _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 4524 */                                                   _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4526 */                                                   _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 4527 */                                                   int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 4528 */                                                   if (_jspx_eval_html_005foption_005f57 != 0) {
/* 4529 */                                                     if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4530 */                                                       out = _jspx_page_context.pushBody();
/* 4531 */                                                       _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 4532 */                                                       _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4535 */                                                       out.write(32);
/* 4536 */                                                       out.print(FormatUtil.getString("Service Monitoring"));
/* 4537 */                                                       out.write(32);
/* 4538 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 4539 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4542 */                                                     if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4543 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4546 */                                                   if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 4547 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                                   }
/*      */                                                   
/* 4550 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 4551 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 4553 */                                                   OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4554 */                                                   _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 4555 */                                                   _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4557 */                                                   _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 4558 */                                                   int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 4559 */                                                   if (_jspx_eval_html_005foption_005f58 != 0) {
/* 4560 */                                                     if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4561 */                                                       out = _jspx_page_context.pushBody();
/* 4562 */                                                       _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 4563 */                                                       _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4566 */                                                       out.print(FormatUtil.getString("SNMP"));
/* 4567 */                                                       out.write(" (V1 or V2c)");
/* 4568 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 4569 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4572 */                                                     if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4573 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4576 */                                                   if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 4577 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                                   }
/*      */                                                   
/* 4580 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 4581 */                                                   out.write("</optgroup>");
/* 4582 */                                                   out.write("\n\t\t\t<optgroup label=\"");
/* 4583 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4584 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 4586 */                                                   OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4587 */                                                   _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 4588 */                                                   _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4590 */                                                   _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 4591 */                                                   int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 4592 */                                                   if (_jspx_eval_html_005foption_005f59 != 0) {
/* 4593 */                                                     if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4594 */                                                       out = _jspx_page_context.pushBody();
/* 4595 */                                                       _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 4596 */                                                       _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4599 */                                                       out.print(FormatUtil.getString("HTTP-URLs"));
/* 4600 */                                                       out.write(32);
/* 4601 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 4602 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4605 */                                                     if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4606 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4609 */                                                   if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 4610 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                                   }
/*      */                                                   
/* 4613 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 4614 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 4616 */                                                   OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4617 */                                                   _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 4618 */                                                   _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4620 */                                                   _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 4621 */                                                   int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 4622 */                                                   if (_jspx_eval_html_005foption_005f60 != 0) {
/* 4623 */                                                     if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4624 */                                                       out = _jspx_page_context.pushBody();
/* 4625 */                                                       _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 4626 */                                                       _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4629 */                                                       out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 4630 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 4631 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4634 */                                                     if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4635 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4638 */                                                   if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 4639 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                                   }
/*      */                                                   
/* 4642 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 4643 */                                                   out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4644 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4645 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 4647 */                                                   OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4648 */                                                   _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 4649 */                                                   _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4651 */                                                   _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 4652 */                                                   int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 4653 */                                                   if (_jspx_eval_html_005foption_005f61 != 0) {
/* 4654 */                                                     if (_jspx_eval_html_005foption_005f61 != 1) {
/* 4655 */                                                       out = _jspx_page_context.pushBody();
/* 4656 */                                                       _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 4657 */                                                       _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4660 */                                                       out.print(FormatUtil.getString("Script Monitor"));
/* 4661 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 4662 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4665 */                                                     if (_jspx_eval_html_005foption_005f61 != 1) {
/* 4666 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4669 */                                                   if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 4670 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                                   }
/*      */                                                   
/* 4673 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 4674 */                                                   out.write(10);
/* 4675 */                                                   out.write(10);
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 4679 */                                                 out.write("\n\n\n\n      ");
/* 4680 */                                                 int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4681 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4684 */                                               if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4685 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4688 */                                             if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4689 */                                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                             }
/*      */                                             
/* 4692 */                                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 4693 */                                             out.write("\n                      \n      ");
/* 4694 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4695 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4699 */                                         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4700 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                         }
/*      */                                         
/* 4703 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4704 */                                         out.write("\n      ");
/* 4705 */                                         if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                           return;
/* 4707 */                                         out.write("\n      </td>\n      \n      ");
/* 4708 */                                         if (request.getParameter("type") != null) {
/* 4709 */                                           isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 4710 */                                           String restype = request.getParameter("type");
/* 4711 */                                           if (restype.indexOf(":") != -1) {
/* 4712 */                                             restype = restype.substring(0, restype.indexOf(":"));
/*      */                                           }
/* 4714 */                                           if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 4715 */                                             out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 4716 */                                             out.print(restype);
/* 4717 */                                             out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 4718 */                                             out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 4719 */                                             out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                           }
/*      */                                         }
/* 4722 */                                         out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 4723 */                                         out.write(10);
/* 4724 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4725 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4729 */                                     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4730 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                     }
/*      */                                     
/* 4733 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4734 */                                     out.write(10);
/* 4735 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4736 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4740 */                                 if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4741 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                 }
/*      */                                 
/* 4744 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4745 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" bgcolor=\"FFFFFF\" class=\"lrborder\">\n  <tr> \n    <td width=\"25%\" height=\"32\" valign=='top' class=\"bodytext label-align addmonitor-label\"><label>");
/* 4746 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4747 */                                 out.write("</label></td>\n    <td width=\"75%\" class=\"bodytext\"> <input type=text name=\"customapplicationname\" size=40 maxlength=25 class=\"formtext default\" value=\"");
/* 4748 */                                 out.print(request.getAttribute("customapplicationname") != null ? request.getAttribute("customapplicationname") : "vtiger CRM");
/* 4749 */                                 out.write("\" /> \n    ");
/* 4750 */                                 if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 4752 */                                 out.write("\n    ");
/* 4753 */                                 if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 4755 */                                 out.write("\n    <!-- Below hidden varialble  is used only in the wizard -->\n    <input type=\"hidden\" name=\"configurealerts\" value=\"false\">\n    <input type=\"hidden\" name=\"hideFieldsForIT360\" value=\"");
/* 4756 */                                 out.print(request.getParameter("hideFieldsForIT360"));
/* 4757 */                                 out.write("\">\n    </td>\n  </tr>\n  <tr> \n    <td valign='top'  class=\"bodytext label-align addmonitor-label\"><label>");
/* 4758 */                                 out.print(FormatUtil.getString("Description"));
/* 4759 */                                 out.write("</label></td>\n    <td  class=\"bodytext\"> <textarea name=\"customapplicationdescription\" cols=\"38\" rows=\"3\" class=\"formtextarea xlarge\" >");
/* 4760 */                                 out.print(request.getAttribute("customapplicationdescription") != null ? request.getAttribute("customapplicationdescription") : "CRM Application");
/* 4761 */                                 out.write("</textarea> \n    </td>\n  </tr>\n  <!-- new line -->\n</table>\n");
/*      */                                 
/* 4763 */                                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4764 */                                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4765 */                                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 4767 */                                 _jspx_th_c_005fif_005f3.setTest("${empty param.wiz}");
/* 4768 */                                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4769 */                                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                   for (;;) {
/* 4771 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"25%\" align=\"left\"  class=\"tablebottom\">&nbsp;</td>\n    <td width=\"75%\" height=\"31\" align=\"left\"  class=\"tablebottom\"><input type=hidden name=\"haid\" value=\"");
/* 4772 */                                     out.print(haid);
/* 4773 */                                     out.write("\" /> \n      <input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 4774 */                                     out.print(FormatUtil.getString("am.webclient.cam.addjmxsnmpdashboard"));
/* 4775 */                                     out.write("\" onClick=\"validateAndSubmit()\"/> \n      &nbsp;&nbsp;\n      <input type=\"button\" value=\"");
/* 4776 */                                     out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 4777 */                                     out.write("\" class='buttons btn_link' onClick=\"cancel();\" id=\"cancelButtonMod\"/>\n    </td>\n  </tr>\n</table>\n");
/* 4778 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4779 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4783 */                                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4784 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                 }
/*      */                                 
/* 4787 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4788 */                                 out.write(10);
/*      */                                 
/* 4790 */                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4791 */                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 4792 */                                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 4794 */                                 _jspx_th_c_005fif_005f4.setTest("${!empty param.wiz}");
/* 4795 */                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 4796 */                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                   for (;;) {
/* 4798 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"25%\" align=\"left\"  class=\"tablebottom\">&nbsp;</td>\n    <td width=\"75%\" height=\"31\" align=\"left\"  class=\"tablebottom\">\n    <input type=hidden name=\"haid\" value=\"");
/* 4799 */                                     out.print(haid);
/* 4800 */                                     out.write("\" /> \n      <input type=\"button\" name=\"xx2\" value=\"");
/* 4801 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.back"));
/* 4802 */                                     out.write("\" class=\"buttons btn_back\"  onClick=\"javascript:location.href='/showresource.do?method=associateMonitors&haid=");
/* 4803 */                                     if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                       return;
/* 4805 */                                     out.write("&wiz=true'\">\n      <input name=\"addcustomapp1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 4806 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/* 4807 */                                     out.write("\" onClick=\"validateAndShowAlerts()\"/>\n      <input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 4808 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createandaddmore"));
/* 4809 */                                     out.write("\" onClick=\"validateAndSubmit()\"/>\n           ");
/*      */                                     
/* 4811 */                                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4812 */                                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 4813 */                                     _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                                     
/* 4815 */                                     _jspx_th_c_005fif_005f5.setTest("${!empty associatedmonitors}");
/* 4816 */                                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 4817 */                                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                       for (;;) {
/* 4819 */                                         out.write("\n           <input type=\"button\" name=\"xx\" value=\"");
/* 4820 */                                         out.print(FormatUtil.getString("am.webclient.common.configurealerts.text"));
/* 4821 */                                         out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showActionProfiles.do?method=getHAProfiles&haid=");
/* 4822 */                                         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                           return;
/* 4824 */                                         out.write("&wiz=true'\">\n           ");
/* 4825 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 4826 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4830 */                                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 4831 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                     }
/*      */                                     
/* 4834 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4835 */                                     out.write("\n      <input type=\"button\" name=\"xx1\" value=\"");
/* 4836 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.finish"));
/* 4837 */                                     out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showapplication.do?method=showApplication&haid=");
/* 4838 */                                     if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                       return;
/* 4840 */                                     out.write("'\">      \n    </td>\n  </tr>\n  </table>\n  <table class=\"lrbborder\" width=\"99%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">\n  <!--<td width=\"28\" class=\"bodytext\"><b>Back : </b>It takes you to the second step to select monitor type.</td></tr>-->\n  <tr><td class=\"bodytext\">");
/* 4841 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createdesc"));
/* 4842 */                                     out.write("</td></tr>\n  <tr><td class=\"bodytext\">");
/* 4843 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createandaddmoredesc"));
/* 4844 */                                     out.write("</td></tr>\n  ");
/*      */                                     
/* 4846 */                                     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4847 */                                     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 4848 */                                     _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f4);
/*      */                                     
/* 4850 */                                     _jspx_th_c_005fif_005f6.setTest("${!empty associatedmonitors}");
/* 4851 */                                     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 4852 */                                     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                       for (;;) {
/* 4854 */                                         out.write("\n  <tr><td class=\"bodytext\">");
/* 4855 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealertdesc"));
/* 4856 */                                         out.write("</td></tr>\n  ");
/* 4857 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4858 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4862 */                                     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4863 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                     }
/*      */                                     
/* 4866 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4867 */                                     out.write("\n  <tr><td class=\"bodytext\">");
/* 4868 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.finishdesc"));
/* 4869 */                                     out.write("</td></tr>\n  </tr>\n  </table>\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"2%\" background=\"/images/wiz_bg_graylind.gif\"><img src=\"../images/wiz_startimage_bottom.gif\" width=\"20\" height=\"19\"></td>\n    <td width=\"94%\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"19\"></td>\n    <td width=\"4%\" align=\"right\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"../images/wiz_endicon_bottom.gif\" width=\"32\" height=\"19\"></td>\n  </tr>\n</table>\n");
/* 4870 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4871 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4875 */                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 4876 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                 }
/*      */                                 
/* 4879 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4880 */                                 out.write("\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/*      */                                 
/* 4882 */                                 String title = request.getParameter("type");
/* 4883 */                                 StringBuffer helpcardKey = new StringBuffer();
/*      */                                 
/* 4885 */                                 if (title.equals("Custom-Application")) {
/* 4886 */                                   helpcardKey.append(FormatUtil.getString("am.jmx.helpcard.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/*      */                                   
/* 4888 */                                   out.write("\t\t\t  \n");
/* 4889 */                                   org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()), out, false);
/* 4890 */                                   out.write("\t  \n\n");
/*      */                                 }
/* 4892 */                                 out.write(10);
/* 4893 */                                 out.write(10);
/* 4894 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 4895 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4899 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 4900 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 4903 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 4904 */                             out.write(" \n</td>\n        </tr>\n        </table>\n\n\n<script>\n\n    \nfunction cancel() {\n\n\n");
/* 4905 */                             if (_jspx_meth_c_005fif_005f7(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 4907 */                             out.write("\n    \n}\n\nfunction validateAndShowAlerts()\n{\n\tdocument.CreateCustomApplicationForm.configurealerts.value='true';\t\n\tvalidateAndSubmit();\n}\n\nfunction validateAndSubmit()\n{\n");
/* 4908 */                             if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 4910 */                             out.write(10);
/*      */                             
/* 4912 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4913 */                             _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 4914 */                             _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 4916 */                             _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 4917 */                             int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 4918 */                             if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                               for (;;) {
/* 4920 */                                 out.write("\n\n   if(trimAll(document.CreateCustomApplicationForm.customapplicationname.value)==\"\")\n   {\n   \talert('");
/* 4921 */                                 out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 4922 */                                 out.write("');\n   \treturn;\n   }\n   document.CreateCustomApplicationForm.submit();\n   ");
/* 4923 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 4924 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4928 */                             if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 4929 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                             }
/*      */                             
/* 4932 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4933 */                             out.write("\n}\n\nfunction formReload()\n{\n    var v=document.CreateCustomApplicationForm.type.value;\n    //var portNo=v.substring(v.indexOf(\":\")+1,v.length);\n    //document.AMActionForm.method=\"post\";\n    document.CreateCustomApplicationForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 4934 */                             out.print(request.getParameter("hideFieldsForIT360"));
/* 4935 */                             out.write("\";\n    //enableAll();\n    document.CreateCustomApplicationForm.submit();\n}\n</script>\n\n<!--  Your area ends here -->\n");
/* 4936 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 4937 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4940 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 4941 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4944 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4945 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 4948 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 4949 */                         out.write(10);
/* 4950 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 4952 */                         out.write(32);
/* 4953 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4954 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 4958 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4959 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 4962 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4963 */                       out.write(32);
/* 4964 */                       out.write(10);
/* 4965 */                       out.write(10);
/*      */                       
/* 4967 */                       if (hideFields)
/*      */                       {
/*      */ 
/* 4970 */                         out.write("\n\t<script>\n\t\t$(document.body).ready(function(){\n\t\tdocument.getElementById(\"cancelButtonMod\").onclick = null;\n\t\t$(\"#cancelButtonMod\").click(function(){ //No I18N\n\t\t\tclosePopUpWindow();\n\t\t});\n\t\t});\n\t</script>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 4974 */                       out.write(10);
/*      */                     }
/* 4976 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4977 */         out = _jspx_out;
/* 4978 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4979 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4980 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4983 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4989 */     PageContext pageContext = _jspx_page_context;
/* 4990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4992 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4993 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4994 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4996 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4998 */     _jspx_th_tiles_005fput_005f0.setValue("Create New Custom Monitor");
/* 4999 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 5000 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 5001 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5002 */       return true;
/*      */     }
/* 5004 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5010 */     PageContext pageContext = _jspx_page_context;
/* 5011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5013 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5014 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5015 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5017 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5019 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 5020 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5021 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5022 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5023 */       return true;
/*      */     }
/* 5025 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5026 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5031 */     PageContext pageContext = _jspx_page_context;
/* 5032 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5034 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 5035 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 5036 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5038 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 5039 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 5041 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 5042 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 5044 */           out.write("\n    ");
/* 5045 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 5046 */             return true;
/* 5047 */           out.write(10);
/* 5048 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 5049 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5053 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 5054 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5057 */         int tmp184_183 = 0; int[] tmp184_181 = _jspx_push_body_count_c_005fcatch_005f0; int tmp186_185 = tmp184_181[tmp184_183];tmp184_181[tmp184_183] = (tmp186_185 - 1); if (tmp186_185 <= 0) break;
/* 5058 */         out = _jspx_page_context.popBody(); }
/* 5059 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5061 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 5062 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 5064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 5069 */     PageContext pageContext = _jspx_page_context;
/* 5070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5072 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 5073 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 5074 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 5076 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 5078 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 5079 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 5080 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 5081 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5082 */       return true;
/*      */     }
/* 5084 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5085 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5090 */     PageContext pageContext = _jspx_page_context;
/* 5091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5093 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5094 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5095 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5097 */     _jspx_th_c_005fif_005f2.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 5098 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5099 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5101 */         out.write("\n      ");
/* 5102 */         if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5103 */           return true;
/* 5104 */         out.write("\n      ");
/* 5105 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5106 */           return true;
/* 5107 */         out.write("\n      ");
/* 5108 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5109 */           return true;
/* 5110 */         out.write("\n      ");
/* 5111 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5112 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5116 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5117 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5118 */       return true;
/*      */     }
/* 5120 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5121 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5126 */     PageContext pageContext = _jspx_page_context;
/* 5127 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5129 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5130 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 5131 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 5132 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 5133 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 5135 */         out.write("\n        ");
/* 5136 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 5137 */           return true;
/* 5138 */         out.write("\n        ");
/* 5139 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 5140 */           return true;
/* 5141 */         out.write("\n\n        ");
/* 5142 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 5143 */           return true;
/* 5144 */         out.write("\n      ");
/* 5145 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 5146 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5150 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 5151 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 5152 */       return true;
/*      */     }
/* 5154 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 5155 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5160 */     PageContext pageContext = _jspx_page_context;
/* 5161 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5163 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5164 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 5165 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 5167 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='WTA'}");
/* 5168 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 5169 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 5171 */         out.write("\n          ");
/* 5172 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 5173 */           return true;
/* 5174 */         out.write("\n        ");
/* 5175 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 5176 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5180 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 5181 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5182 */       return true;
/*      */     }
/* 5184 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5185 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5190 */     PageContext pageContext = _jspx_page_context;
/* 5191 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5193 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5194 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5195 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 5197 */     _jspx_th_c_005fout_005f0.setValue("Web Transaction Monitor");
/* 5198 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5199 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5200 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5201 */       return true;
/*      */     }
/* 5203 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5204 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5209 */     PageContext pageContext = _jspx_page_context;
/* 5210 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5212 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5213 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 5214 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 5216 */     _jspx_th_c_005fwhen_005f2.setTest("${param.type=='.Net'}");
/* 5217 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 5218 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 5220 */         out.write("\n          ");
/* 5221 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 5222 */           return true;
/* 5223 */         out.write("\n        ");
/* 5224 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 5225 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5229 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 5230 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 5231 */       return true;
/*      */     }
/* 5233 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 5234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5239 */     PageContext pageContext = _jspx_page_context;
/* 5240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5242 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5243 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5244 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 5246 */     _jspx_th_c_005fout_005f1.setValue("Tomcat Server");
/* 5247 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5248 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5249 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5250 */       return true;
/*      */     }
/* 5252 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5258 */     PageContext pageContext = _jspx_page_context;
/* 5259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5261 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5262 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 5263 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 5264 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 5265 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 5267 */         out.write("\n         ");
/* 5268 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 5269 */           return true;
/* 5270 */         out.write("\n        ");
/* 5271 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 5272 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5276 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 5277 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 5278 */       return true;
/*      */     }
/* 5280 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 5281 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5286 */     PageContext pageContext = _jspx_page_context;
/* 5287 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5289 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 5290 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5291 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 5293 */     _jspx_th_c_005fout_005f2.setValue("${param.type}");
/* 5294 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5295 */     if (_jspx_eval_c_005fout_005f2 != 0) {
/* 5296 */       if (_jspx_eval_c_005fout_005f2 != 1) {
/* 5297 */         out = _jspx_page_context.pushBody();
/* 5298 */         _jspx_th_c_005fout_005f2.setBodyContent((BodyContent)out);
/* 5299 */         _jspx_th_c_005fout_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5302 */         out.write(45);
/* 5303 */         int evalDoAfterBody = _jspx_th_c_005fout_005f2.doAfterBody();
/* 5304 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5307 */       if (_jspx_eval_c_005fout_005f2 != 1) {
/* 5308 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5311 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5312 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f2);
/* 5313 */       return true;
/*      */     }
/* 5315 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f2);
/* 5316 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5321 */     PageContext pageContext = _jspx_page_context;
/* 5322 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5324 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5325 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 5326 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5328 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 5329 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 5330 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 5331 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5332 */       return true;
/*      */     }
/* 5334 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5335 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5340 */     PageContext pageContext = _jspx_page_context;
/* 5341 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5343 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5344 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 5345 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5347 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 5348 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 5349 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 5350 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5351 */       return true;
/*      */     }
/* 5353 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5354 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5359 */     PageContext pageContext = _jspx_page_context;
/* 5360 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5362 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 5363 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 5364 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5366 */     _jspx_th_am_005fhiddenparam_005f0.setName("wiz");
/* 5367 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 5368 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 5369 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 5370 */       return true;
/*      */     }
/* 5372 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 5373 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5378 */     PageContext pageContext = _jspx_page_context;
/* 5379 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5381 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 5382 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 5383 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5385 */     _jspx_th_am_005fhiddenparam_005f1.setName("haid");
/* 5386 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 5387 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 5388 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 5389 */       return true;
/*      */     }
/* 5391 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 5392 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5397 */     PageContext pageContext = _jspx_page_context;
/* 5398 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5400 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5401 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5402 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5404 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 5405 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5406 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5407 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5408 */       return true;
/*      */     }
/* 5410 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5411 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5416 */     PageContext pageContext = _jspx_page_context;
/* 5417 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5419 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5420 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5421 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5423 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 5424 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5425 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5426 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5427 */       return true;
/*      */     }
/* 5429 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5430 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5435 */     PageContext pageContext = _jspx_page_context;
/* 5436 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5438 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5439 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5440 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5442 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 5443 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5444 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5445 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5446 */       return true;
/*      */     }
/* 5448 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5449 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5454 */     PageContext pageContext = _jspx_page_context;
/* 5455 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5457 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5458 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5459 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5461 */     _jspx_th_c_005fif_005f7.setTest("${empty haid}");
/* 5462 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5463 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5465 */         out.write(" \n      history.back();\n");
/* 5466 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5467 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5471 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5472 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5473 */       return true;
/*      */     }
/* 5475 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5476 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5481 */     PageContext pageContext = _jspx_page_context;
/* 5482 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5484 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5485 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 5486 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5488 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 5489 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 5490 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 5492 */         out.write("\nalertUser();\n");
/* 5493 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 5494 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5498 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 5499 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5500 */       return true;
/*      */     }
/* 5502 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5503 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5508 */     PageContext pageContext = _jspx_page_context;
/* 5509 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5511 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5512 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 5513 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5515 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 5517 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 5518 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 5519 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 5520 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 5521 */       return true;
/*      */     }
/* 5523 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 5524 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\cam_005fnewcam_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */