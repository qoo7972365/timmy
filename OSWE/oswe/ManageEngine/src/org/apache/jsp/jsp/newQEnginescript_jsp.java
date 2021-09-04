/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
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
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.ResetTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class newQEnginescript_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 1388 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1433 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 2191 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2192 */   static { _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2194 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2229 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2233 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2261 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2265 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2266 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2267 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2268 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2269 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2270 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2271 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.release();
/* 2272 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2273 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2275 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2276 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2277 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2278 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2279 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2280 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2281 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2282 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2283 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2284 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2286 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2287 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.release();
/* 2288 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2289 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.release();
/* 2290 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.release();
/* 2291 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2298 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2301 */     JspWriter out = null;
/* 2302 */     Object page = this;
/* 2303 */     JspWriter _jspx_out = null;
/* 2304 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2308 */       response.setContentType("text/html;charset=UTF-8");
/* 2309 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2311 */       _jspx_page_context = pageContext;
/* 2312 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2313 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2314 */       session = pageContext.getSession();
/* 2315 */       out = pageContext.getOut();
/* 2316 */       _jspx_out = out;
/*      */       
/* 2318 */       out.write("<!--$Id$-->\n");
/*      */       
/* 2320 */       request.setAttribute("HelpKey", "Configuring QEngineScript Monitor");
/*      */       
/* 2322 */       out.write("\n\n\n\n\n\n\n\n");
/* 2323 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2325 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2326 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2327 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2329 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2331 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2333 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2335 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2336 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2337 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2338 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2341 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2342 */         String available = null;
/* 2343 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2344 */         out.write(10);
/*      */         
/* 2346 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2347 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2348 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2350 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2352 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2354 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2356 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2357 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2358 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2359 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2362 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2363 */           String unavailable = null;
/* 2364 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2365 */           out.write(10);
/*      */           
/* 2367 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2368 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2369 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2371 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2373 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2375 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2377 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2378 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2379 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2380 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2383 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2384 */             String unmanaged = null;
/* 2385 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2386 */             out.write(10);
/*      */             
/* 2388 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2389 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2390 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2392 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2394 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2396 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2398 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2399 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2400 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2401 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2404 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2405 */               String scheduled = null;
/* 2406 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2407 */               out.write(10);
/*      */               
/* 2409 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2410 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2411 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2413 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2415 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2417 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2419 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2420 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2421 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2422 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2425 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2426 */                 String critical = null;
/* 2427 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2428 */                 out.write(10);
/*      */                 
/* 2430 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2431 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2432 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2434 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2436 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2438 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2440 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2441 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2442 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2443 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2446 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2447 */                   String clear = null;
/* 2448 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2449 */                   out.write(10);
/*      */                   
/* 2451 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2452 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2453 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2455 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2457 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2459 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2461 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2462 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2463 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2464 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2467 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2468 */                     String warning = null;
/* 2469 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2470 */                     out.write(10);
/* 2471 */                     out.write(10);
/*      */                     
/* 2473 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2474 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2476 */                     out.write(10);
/* 2477 */                     out.write(10);
/* 2478 */                     out.write(10);
/* 2479 */                     out.write(10);
/* 2480 */                     ManagedApplication mo = null;
/* 2481 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2482 */                     if (mo == null) {
/* 2483 */                       mo = new ManagedApplication();
/* 2484 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2486 */                     out.write(10);
/* 2487 */                     Hashtable applications = null;
/* 2488 */                     synchronized (application) {
/* 2489 */                       applications = (Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 2490 */                       if (applications == null) {
/* 2491 */                         applications = new Hashtable();
/* 2492 */                         _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                       }
/*      */                     }
/* 2495 */                     out.write(" \n<SCRIPT LANGAUGE =\"javascript\" SRC=\"../template/appmanager.js\" ></SCRIPT>\n\n");
/*      */                     
/*      */ 
/*      */ 
/* 2499 */                     String resourceid = request.getParameter("resourceid");
/* 2500 */                     String haid = request.getParameter("haid");
/*      */                     
/*      */ 
/* 2503 */                     org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2504 */                     token.saveToken(request);
/*      */                     
/* 2506 */                     out.write("\n<script>\nfunction validateAndSubmit()\n{\n\n");
/* 2507 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2509 */                     out.write(10);
/*      */                     
/* 2511 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2512 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2513 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/* 2515 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2516 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2517 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2519 */                         out.write("\n    var displayname=trimAll(document.AMActionForm.displayname.value);\n    var message=trimAll(document.AMActionForm.message.value);\t\n    //var exp_results=trimAll(document.AMActionForm.description.value);\n    //var delimiter=trimAll(document.AMActionForm.delimiter.value);\n    //var outputfile=trimAll(document.AMActionForm.outputfile.value);\n    var arguments=trimAll(document.AMActionForm.pollInterval.value);\n    var pollinterval=trimAll(document.AMActionForm.pollInterval.value);\n    var timeout=trimAll(document.AMActionForm.timeout.value);\n    var mode=\"\";\n    ");
/*      */                         
/* 2521 */                         if ((!System.getProperty("os.name").startsWith("Windows")) && (!System.getProperty("os.name").startsWith("windows")))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2528 */                           out.write("\n        mode=trimAll(document.AMActionForm.mode.value);\n        if(mode==\"\")\n        {\n            \n        }\n        ");
/*      */                         }
/*      */                         
/*      */ 
/* 2532 */                         out.write("\n    \n    \n    if(displayname == \"\")\n    {\n        alert(\"");
/* 2533 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.qengine.alert.name"));
/* 2534 */                         out.write("\");\n        document.AMActionForm.displayname.focus();\n        return;\n    }\n    if(displayname.indexOf(\"'\") != -1) \n\t{\n\t\talert(\"");
/* 2535 */                         out.print(FormatUtil.getString("am.webclient.common.jsalertforsinglequote.text"));
/* 2536 */                         out.write("\");\n\t\tdocument.AMActionForm.displayname.focus();\n\t\treturn;\n\t}\n\tif(message==\"\")\n\t{\n\t\talert(\"");
/* 2537 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.qengine.alert.scriptpath"));
/* 2538 */                         out.write("\");\n\t\tdocument.AMActionForm.message.focus();\n\t\treturn;\n\t}\n\n    \n  //  var serverpath=trimAll(document.AMActionForm.serverpath.value);\n  //  var exec_dir=trimAll(document.AMActionForm.workingdirectory.value);\n/*    if(serverpath==\"\")\n    {\n        alert(\"Script to be Monitored should not be empty\");\n        document.AMActionForm.serverpath.focus();\n        return;\n    }\n    if(exec_dir==\"\")\n    {\n        alert(\"Working directory(directory from which the script is executed) should not be empty\");\n        document.AMActionForm.workingdirectory.focus();\n        return;\n    }\n    else\n    {\n        if(serverpath.indexOf(exec_dir,0))\n        {\n            alert(\"Specify the proper working directory\");\n            document.AMActionForm.workingdirectory.focus();\n            return;\n        }\n    }*/\n    \n    if(pollinterval ==\"\" ||!(isPositiveInteger(pollinterval)) || pollinterval =='0' )\n    {\n        alert(\"");
/* 2539 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.polling"));
/* 2540 */                         out.write("\");\t\n        document.AMActionForm.pollInterval.focus();\n        return;\n    }\n/*    if(outputfile!=\"\" && exp_results==\"\")\n    {\n        alert(\"Expected output should not be empty if the output file is specified\");\n        document.AMActionForm.description.focus();\n        return;\n    }\n    if(outputfile!=\"\" && delimiter==\"\")\n    {\n        alert(\"Output delimiter should not be empty if the output file is specified\");\n        document.AMActionForm.delimiter.focus();\n        return;\n    }\n    if(outputfile==\"\" && exp_results!=\"\")\n    {\n        alert(\"Output file cannot be empty when the expected output is specified\");\n        document.AMActionForm.outputfile.focus();\n        return;\n    }\n    */\n    if(timeout==\"\")\n    {\n        alert(\"");
/* 2541 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.timeout"));
/* 2542 */                         out.write("\");\n        document.AMActionForm.timeout.focus();\n        return;\n    }\n\n    document.AMActionForm.submit();\n      ");
/* 2543 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2544 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2548 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2549 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     }
/*      */                     else {
/* 2552 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2553 */                       out.write("\n}\n\nfunction formReload()\n{\n    var v=document.AMActionForm.type.value;\n    //var portNo=v.substring(v.indexOf(\":\")+1,v.length);\n    //document.AMActionForm.method=\"post\";\n    document.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 2554 */                       out.print(request.getParameter("hideFieldsForIT360"));
/* 2555 */                       out.write("\";\n    //enableAll();\n    document.AMActionForm.submit();\n}\n\n</script>\n\n    ");
/*      */                       
/* 2557 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2558 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2559 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2561 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 2562 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2563 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2565 */                           out.write("\n    ");
/* 2566 */                           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2568 */                           out.write("\n    ");
/* 2569 */                           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2571 */                           out.write("\n    ");
/* 2572 */                           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2574 */                           out.write("\n    \n    \n    ");
/*      */                           
/* 2576 */                           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2577 */                           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2578 */                           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2580 */                           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                           
/* 2582 */                           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2583 */                           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2584 */                           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2585 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2586 */                               out = _jspx_page_context.pushBody();
/* 2587 */                               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2588 */                               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2591 */                               out.write("     \n\n");
/*      */                               
/* 2593 */                               String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2594 */                               if (hideFieldsForIT360 == null)
/*      */                               {
/* 2596 */                                 hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */                               }
/*      */                               
/* 2599 */                               boolean hideFields = false;
/* 2600 */                               String hideStyle = "";
/* 2601 */                               if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */                               {
/* 2603 */                                 hideFields = true;
/* 2604 */                                 hideStyle = "hideContent";
/*      */                               }
/* 2606 */                               boolean isDiscoveryComplete = false;
/* 2607 */                               boolean isDiscoverySuccess = false;
/*      */                               
/* 2609 */                               out.write(10);
/* 2610 */                               out.write(10);
/* 2611 */                               out.write(9);
/*      */                               
/* 2613 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.get(FormTag.class);
/* 2614 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2615 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 2617 */                               _jspx_th_html_005fform_005f0.setAction("/updateScript");
/*      */                               
/* 2619 */                               _jspx_th_html_005fform_005f0.setFocus("displayname");
/*      */                               
/* 2621 */                               _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/* 2622 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2623 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2625 */                                   out.write(32);
/* 2626 */                                   out.write(10);
/* 2627 */                                   out.write(9);
/*      */                                   
/* 2629 */                                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2630 */                                   _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2631 */                                   _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2633 */                                   _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2634 */                                   int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2635 */                                   if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                     for (;;) {
/* 2637 */                                       out.write(32);
/* 2638 */                                       out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                                       
/* 2640 */                                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2641 */                                       _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2642 */                                       _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                       
/* 2644 */                                       _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 2645 */                                       int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2646 */                                       if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                         for (;;) {
/* 2648 */                                           out.write(10);
/*      */                                           
/*      */ 
/* 2651 */                                           if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                                           {
/*      */ 
/* 2654 */                                             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2655 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2656 */                                             out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2657 */                                             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2658 */                                             out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2659 */                                             out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2660 */                                             out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2661 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2662 */                                             out.write("\n </span></td>\n  <tr>\n  ");
/*      */                                             
/* 2664 */                                             int failedNumber = 1;
/*      */                                             
/* 2666 */                                             out.write(10);
/*      */                                             
/* 2668 */                                             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2669 */                                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2670 */                                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                             
/* 2672 */                                             _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                                             
/* 2674 */                                             _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                             
/* 2676 */                                             _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                                             
/* 2678 */                                             _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2679 */                                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2680 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2681 */                                               ArrayList row = null;
/* 2682 */                                               Integer i = null;
/* 2683 */                                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2684 */                                                 out = _jspx_page_context.pushBody();
/* 2685 */                                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2686 */                                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                               }
/* 2688 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2689 */                                               i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                               for (;;) {
/* 2691 */                                                 out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2692 */                                                 out.print(row.get(0));
/* 2693 */                                                 out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2694 */                                                 out.print(row.get(1));
/* 2695 */                                                 out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                                 
/* 2697 */                                                 if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                                 {
/* 2699 */                                                   request.setAttribute("isDiscoverySuccess", "true");
/*      */                                                   
/* 2701 */                                                   out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2702 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2703 */                                                   out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                                 }
/*      */                                                 else
/*      */                                                 {
/* 2708 */                                                   request.setAttribute("isDiscoverySuccess", "false");
/*      */                                                   
/* 2710 */                                                   out.write("\n      <img alt=\"");
/* 2711 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2712 */                                                   out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 2716 */                                                 out.write("\n      <span class=\"bodytextbold\">");
/* 2717 */                                                 out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2718 */                                                 out.write("</span> </td>\n\n      ");
/*      */                                                 
/* 2720 */                                                 pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                                 
/* 2722 */                                                 out.write("\n                     ");
/*      */                                                 
/* 2724 */                                                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2725 */                                                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2726 */                                                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                                 
/* 2728 */                                                 _jspx_th_c_005fif_005f0.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2729 */                                                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2730 */                                                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                                   for (;;) {
/* 2732 */                                                     out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2733 */                                                     out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2734 */                                                     out.write("\n                                 ");
/* 2735 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2736 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 2740 */                                                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2741 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                                 }
/*      */                                                 
/* 2744 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2745 */                                                 out.write("\n                                       ");
/*      */                                                 
/* 2747 */                                                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2748 */                                                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2749 */                                                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                                 
/* 2751 */                                                 _jspx_th_c_005fif_005f1.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2752 */                                                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2753 */                                                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                                   for (;;) {
/* 2755 */                                                     out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2756 */                                                     out.print(row.get(3));
/* 2757 */                                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                     
/* 2759 */                                                     if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                                     {
/* 2761 */                                                       if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                                       {
/* 2763 */                                                         String fWhr = request.getParameter("hideFieldsForIT360");
/* 2764 */                                                         if (fWhr == null)
/*      */                                                         {
/* 2766 */                                                           fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                                         }
/*      */                                                         
/* 2769 */                                                         if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2770 */                                                           (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                                         {
/* 2772 */                                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2773 */                                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2774 */                                                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                         }
/*      */                                                       } }
/* 2777 */                                                     if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                                     {
/* 2779 */                                                       failedNumber++;
/*      */                                                       
/*      */ 
/* 2782 */                                                       out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2783 */                                                       out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.talkback.mailid"), com.adventnet.appmanager.util.OEMUtil.getOEMString("product.tollfree.number") }));
/* 2784 */                                                       out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                     }
/* 2786 */                                                     out.write("\n                                                   ");
/* 2787 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2788 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 2792 */                                                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2793 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                                 }
/*      */                                                 
/* 2796 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2797 */                                                 out.write(10);
/* 2798 */                                                 out.write(10);
/* 2799 */                                                 out.write(10);
/*      */                                                 
/*      */ 
/* 2802 */                                                 if (row.size() > 4)
/*      */                                                 {
/*      */ 
/* 2805 */                                                   out.write("<br>\n");
/* 2806 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2807 */                                                   out.write(10);
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 2811 */                                                 out.write("\n</td>\n\n</tr>\n");
/* 2812 */                                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2813 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2814 */                                                 i = (Integer)_jspx_page_context.findAttribute("i");
/* 2815 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 2818 */                                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2819 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 2822 */                                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2823 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                             }
/*      */                                             
/* 2826 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2827 */                                             out.write("\n</table>\n");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2832 */                                             ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                                             
/* 2834 */                                             out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2835 */                                             String mtype = (String)request.getAttribute("type");
/* 2836 */                                             out.write(10);
/* 2837 */                                             if (mtype.equals("File System Monitor")) {
/* 2838 */                                               out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2839 */                                               out.print(FormatUtil.getString("File/Directory Name"));
/* 2840 */                                               out.write("</span> </td>\n");
/* 2841 */                                             } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2842 */                                               out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2843 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2844 */                                               out.write("</span> </td>\n");
/*      */                                             } else {
/* 2846 */                                               out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2847 */                                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2848 */                                               out.write("</span> </td>\n");
/*      */                                             }
/* 2850 */                                             out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2851 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2852 */                                             out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2853 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2854 */                                             out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2855 */                                             out.print(al1.get(0));
/* 2856 */                                             out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                                             
/* 2858 */                                             if (al1.get(1).equals("Success"))
/*      */                                             {
/* 2860 */                                               request.setAttribute("isDiscoverySuccess", "true");
/*      */                                               
/* 2862 */                                               out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2863 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2864 */                                               out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 2869 */                                               request.setAttribute("isDiscoverySuccess", "false");
/*      */                                               
/*      */ 
/* 2872 */                                               out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 2876 */                                             out.write("\n<span class=\"bodytextbold\">");
/* 2877 */                                             out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2878 */                                             out.write("</span> </td>\n");
/*      */                                             
/* 2880 */                                             if (al1.get(1).equals("Success"))
/*      */                                             {
/* 2882 */                                               boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2883 */                                               if (isAdminServer) {
/* 2884 */                                                 String masDisplayName = (String)al1.get(3);
/* 2885 */                                                 String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                                 
/* 2887 */                                                 out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2888 */                                                 out.print(format);
/* 2889 */                                                 out.write("</td>\n");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 2893 */                                                 out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2894 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2895 */                                                 out.write("<br> ");
/* 2896 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2897 */                                                 out.write("</td>\n");
/*      */                                               }
/*      */                                               
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 2904 */                                               out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2905 */                                               out.print(al1.get(2));
/* 2906 */                                               out.write("</span></td>\n");
/*      */                                             }
/*      */                                             
/*      */ 
/* 2910 */                                             out.write("\n  </tr>\n</table>\n\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 2914 */                                           out.write(10);
/* 2915 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2916 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2920 */                                       if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2921 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                       }
/*      */                                       
/* 2924 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2925 */                                       out.write(10);
/* 2926 */                                       out.write(10);
/* 2927 */                                       out.write(9);
/*      */                                       
/* 2929 */                                       String discSucc = (String)request.getAttribute("isDiscoverySuccess");
/* 2930 */                                       isDiscoveryComplete = true;
/* 2931 */                                       if ((discSucc != null) && (discSucc.equals("true")))
/*      */                                       {
/* 2933 */                                         isDiscoverySuccess = true;
/*      */                                       }
/*      */                                       
/* 2936 */                                       out.write("\n\t<br>\n");
/* 2937 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2938 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2942 */                                   if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2943 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                   }
/*      */                                   
/* 2946 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2947 */                                   out.write(10);
/* 2948 */                                   out.write(9);
/* 2949 */                                   if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2951 */                                   out.write(10);
/*      */                                   
/* 2953 */                                   if ((!hideFields) || ((!isDiscoveryComplete) && (hideFields)))
/*      */                                   {
/*      */ 
/* 2956 */                                     out.write(10);
/* 2957 */                                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 2958 */                                     String message = (String)request.getAttribute("typemessage");
/*      */                                     
/* 2960 */                                     ManagedApplication mo1 = new ManagedApplication();
/* 2961 */                                     Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 2962 */                                     boolean isConfMonitor = false;
/* 2963 */                                     ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 2964 */                                     if (message != null)
/*      */                                     {
/* 2966 */                                       out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 2967 */                                       out.print(message);
/* 2968 */                                       out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 2972 */                                     out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 2973 */                                     out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 2974 */                                     out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 2975 */                                     if ("UrlSeq".equals(request.getParameter("type"))) {
/* 2976 */                                       DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2977 */                                       if (frm != null) {
/* 2978 */                                         frm.set("type", "UrlSeq");
/*      */                                       }
/*      */                                     }
/*      */                                     
/* 2982 */                                     if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 2983 */                                       DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2984 */                                       if (frm != null) {
/* 2985 */                                         frm.set("type", "UrlMonitor");
/*      */                                       }
/*      */                                     }
/*      */                                     
/* 2989 */                                     if ("RBM".equals(request.getParameter("type"))) {
/* 2990 */                                       DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 2991 */                                       if (frm != null) {
/* 2992 */                                         frm.set("type", "RBM");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 2997 */                                     out.write("\n\n    ");
/*      */                                     
/* 2999 */                                     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3000 */                                     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3001 */                                     _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 3003 */                                     _jspx_th_c_005fif_005f2.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 3004 */                                     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3005 */                                     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                       for (;;) {
/* 3007 */                                         out.write("\n     ");
/*      */                                         
/* 3009 */                                         SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3010 */                                         _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3011 */                                         _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f2);
/*      */                                         
/* 3013 */                                         _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                         
/* 3015 */                                         _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                         
/* 3017 */                                         _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                         
/* 3019 */                                         _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 3020 */                                         int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3021 */                                         if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3022 */                                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3023 */                                             out = _jspx_page_context.pushBody();
/* 3024 */                                             _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3025 */                                             _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3028 */                                             out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                             
/* 3030 */                                             if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                             {
/*      */ 
/*      */ 
/* 3034 */                                               out.write("\n\n\t <optgroup label=\"");
/* 3035 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3036 */                                               out.write("\">\n                                        \n                                        ");
/*      */                                               
/* 3038 */                                               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3039 */                                               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3040 */                                               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3042 */                                               _jspx_th_html_005foption_005f0.setValue("AIX");
/* 3043 */                                               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3044 */                                               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3045 */                                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3046 */                                                   out = _jspx_page_context.pushBody();
/* 3047 */                                                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3048 */                                                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3051 */                                                   out.print(FormatUtil.getString("AIX"));
/* 3052 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3053 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3056 */                                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3057 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3060 */                                               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3061 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                               }
/*      */                                               
/* 3064 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3065 */                                               out.write("\n                                        ");
/*      */                                               
/* 3067 */                                               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3068 */                                               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3069 */                                               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3071 */                                               _jspx_th_html_005foption_005f1.setValue("AS400");
/* 3072 */                                               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3073 */                                               if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3074 */                                                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3075 */                                                   out = _jspx_page_context.pushBody();
/* 3076 */                                                   _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3077 */                                                   _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3080 */                                                   out.print(FormatUtil.getString("AS400/iSeries"));
/* 3081 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3082 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3085 */                                                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3086 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3089 */                                               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3090 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                               }
/*      */                                               
/* 3093 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3094 */                                               out.write("\n                                        ");
/*      */                                               
/* 3096 */                                               OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3097 */                                               _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3098 */                                               _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3100 */                                               _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 3101 */                                               int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3102 */                                               if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3103 */                                                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3104 */                                                   out = _jspx_page_context.pushBody();
/* 3105 */                                                   _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3106 */                                                   _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3109 */                                                   out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 3110 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3111 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3114 */                                                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3115 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3118 */                                               if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3119 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                               }
/*      */                                               
/* 3122 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3123 */                                               out.write("\n                                        ");
/*      */                                               
/* 3125 */                                               OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3126 */                                               _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3127 */                                               _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3129 */                                               _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 3130 */                                               int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3131 */                                               if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3132 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3133 */                                                   out = _jspx_page_context.pushBody();
/* 3134 */                                                   _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3135 */                                                   _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3138 */                                                   out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 3139 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3140 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3143 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3144 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3147 */                                               if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3148 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                               }
/*      */                                               
/* 3151 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3152 */                                               out.write("\n                                        ");
/*      */                                               
/* 3154 */                                               OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3155 */                                               _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3156 */                                               _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3158 */                                               _jspx_th_html_005foption_005f4.setValue("Linux");
/* 3159 */                                               int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3160 */                                               if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3161 */                                                 if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3162 */                                                   out = _jspx_page_context.pushBody();
/* 3163 */                                                   _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3164 */                                                   _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3167 */                                                   out.print(FormatUtil.getString("Linux"));
/* 3168 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3169 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3172 */                                                 if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3173 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3176 */                                               if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3177 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                               }
/*      */                                               
/* 3180 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3181 */                                               out.write("\n                                        ");
/*      */                                               
/* 3183 */                                               OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3184 */                                               _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3185 */                                               _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3187 */                                               _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 3188 */                                               int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3189 */                                               if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3190 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3191 */                                                   out = _jspx_page_context.pushBody();
/* 3192 */                                                   _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3193 */                                                   _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3196 */                                                   out.print(FormatUtil.getString("Mac OS"));
/* 3197 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3198 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3201 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3202 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3205 */                                               if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3206 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                               }
/*      */                                               
/* 3209 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3210 */                                               out.write("\n                                        ");
/*      */                                               
/* 3212 */                                               OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3213 */                                               _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3214 */                                               _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3216 */                                               _jspx_th_html_005foption_005f6.setValue("Novell");
/* 3217 */                                               int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3218 */                                               if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3219 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3220 */                                                   out = _jspx_page_context.pushBody();
/* 3221 */                                                   _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3222 */                                                   _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3225 */                                                   out.print(FormatUtil.getString("Novell"));
/* 3226 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3227 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3230 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3231 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3234 */                                               if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3235 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                               }
/*      */                                               
/* 3238 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3239 */                                               out.write("\n                                        ");
/*      */                                               
/* 3241 */                                               OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3242 */                                               _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3243 */                                               _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3245 */                                               _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 3246 */                                               int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3247 */                                               if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3248 */                                                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3249 */                                                   out = _jspx_page_context.pushBody();
/* 3250 */                                                   _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3251 */                                                   _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3254 */                                                   out.print(FormatUtil.getString("Sun Solaris"));
/* 3255 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3256 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3259 */                                                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3260 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3263 */                                               if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3264 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                               }
/*      */                                               
/* 3267 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3268 */                                               out.write("\n                                        ");
/*      */                                               
/* 3270 */                                               OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3271 */                                               _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3272 */                                               _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3274 */                                               _jspx_th_html_005foption_005f8.setValue("Windows");
/* 3275 */                                               int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3276 */                                               if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3277 */                                                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3278 */                                                   out = _jspx_page_context.pushBody();
/* 3279 */                                                   _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3280 */                                                   _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3283 */                                                   out.print(FormatUtil.getString("Windows"));
/* 3284 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3285 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3288 */                                                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3289 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3292 */                                               if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3293 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                               }
/*      */                                               
/* 3296 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3297 */                                               out.write("\n                                        ");
/*      */                                               
/* 3299 */                                               OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3300 */                                               _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3301 */                                               _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3303 */                                               _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 3304 */                                               int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3305 */                                               if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3306 */                                                 if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3307 */                                                   out = _jspx_page_context.pushBody();
/* 3308 */                                                   _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3309 */                                                   _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3312 */                                                   out.print(FormatUtil.getString("Windows Cluster"));
/* 3313 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3314 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3317 */                                                 if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3318 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3321 */                                               if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3322 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                               }
/*      */                                               
/* 3325 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3326 */                                               out.write("\n                                        \n\n  ");
/*      */                                               
/* 3328 */                                               ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 3329 */                                               if ((rows1 != null) && (rows1.size() > 0))
/*      */                                               {
/* 3331 */                                                 for (int i = 0; i < rows1.size(); i++)
/*      */                                                 {
/* 3333 */                                                   ArrayList row = (ArrayList)rows1.get(i);
/* 3334 */                                                   String res = (String)row.get(0);
/* 3335 */                                                   String dname = (String)row.get(1);
/* 3336 */                                                   String values = props.getProperty(res);
/* 3337 */                                                   if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                   {
/*      */ 
/* 3340 */                                                     out.write("\n\t\t\t\t");
/*      */                                                     
/* 3342 */                                                     OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3343 */                                                     _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 3344 */                                                     _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                     
/* 3346 */                                                     _jspx_th_html_005foption_005f10.setValue(values);
/* 3347 */                                                     int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 3348 */                                                     if (_jspx_eval_html_005foption_005f10 != 0) {
/* 3349 */                                                       if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3350 */                                                         out = _jspx_page_context.pushBody();
/* 3351 */                                                         _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 3352 */                                                         _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                       }
/*      */                                                       for (;;) {
/* 3355 */                                                         out.write(32);
/* 3356 */                                                         out.print(FormatUtil.getString(dname));
/* 3357 */                                                         int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 3358 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/* 3361 */                                                       if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3362 */                                                         out = _jspx_page_context.popBody();
/*      */                                                       }
/*      */                                                     }
/* 3365 */                                                     if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3366 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                     }
/*      */                                                     
/* 3369 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3370 */                                                     out.write("\n\t\t\t");
/*      */                                                   }
/*      */                                                 }
/*      */                                               }
/*      */                                               
/*      */ 
/* 3376 */                                               String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                               
/* 3378 */                                               String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                               
/*      */ 
/* 3381 */                                               if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                               {
/*      */ 
/* 3384 */                                                 categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3385 */                                                 categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                               }
/* 3387 */                                               for (int c = 0; c < categoryLink.length; c++)
/*      */                                               {
/* 3389 */                                                 ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 3390 */                                                 if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                                 {
/*      */ 
/*      */ 
/* 3394 */                                                   StringBuffer queryBuf = new StringBuffer();
/* 3395 */                                                   queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 3396 */                                                   queryBuf.append(categoryLink[c] + "'");
/* 3397 */                                                   queryBuf.append(" ");
/* 3398 */                                                   queryBuf.append("and RESOURCETYPE in");
/* 3399 */                                                   queryBuf.append(" ");
/* 3400 */                                                   queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 3401 */                                                   if (categoryLink[c].equals("APP"))
/*      */                                                   {
/* 3403 */                                                     queryBuf.append(" ");
/* 3404 */                                                     queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 3405 */                                                     queryBuf.append(" ");
/*      */                                                   }
/* 3407 */                                                   else if (categoryLink[c].equals("SER"))
/*      */                                                   {
/* 3409 */                                                     queryBuf.append(" ");
/* 3410 */                                                     queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 3411 */                                                     queryBuf.append(" ");
/*      */                                                   }
/* 3413 */                                                   else if (categoryLink[c].equals("CAM"))
/*      */                                                   {
/* 3415 */                                                     queryBuf.append(" ");
/* 3416 */                                                     queryBuf.append("and RESOURCETYPE != 'directory'");
/* 3417 */                                                     queryBuf.append(" ");
/*      */                                                   }
/* 3419 */                                                   queryBuf.append(" ");
/* 3420 */                                                   queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 3421 */                                                   ArrayList rows = mo1.getRows(queryBuf.toString());
/* 3422 */                                                   if ((rows != null) && (rows.size() != 0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/* 3427 */                                                     out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 3428 */                                                     out.print(FormatUtil.getString(categoryTitle[c]));
/* 3429 */                                                     out.write(34);
/* 3430 */                                                     out.write(62);
/* 3431 */                                                     out.write(10);
/*      */                                                     
/*      */ 
/* 3434 */                                                     for (int i = 0; i < rows.size(); i++)
/*      */                                                     {
/* 3436 */                                                       ArrayList row = (ArrayList)rows.get(i);
/* 3437 */                                                       String res = (String)row.get(0);
/* 3438 */                                                       if (res.equals("file"))
/*      */                                                       {
/* 3440 */                                                         res = "File / Directory Monitor";
/*      */                                                       }
/* 3442 */                                                       String dname = (String)row.get(1);
/* 3443 */                                                       String values = props.getProperty(res);
/* 3444 */                                                       if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                       {
/*      */ 
/* 3447 */                                                         if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                         {
/*      */ 
/* 3450 */                                                           out.write("\n\t\t\t\t \t");
/*      */                                                           
/* 3452 */                                                           OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3453 */                                                           _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 3454 */                                                           _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                           
/* 3456 */                                                           _jspx_th_html_005foption_005f11.setValue(values);
/* 3457 */                                                           int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 3458 */                                                           if (_jspx_eval_html_005foption_005f11 != 0) {
/* 3459 */                                                             if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3460 */                                                               out = _jspx_page_context.pushBody();
/* 3461 */                                                               _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 3462 */                                                               _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                             }
/*      */                                                             for (;;) {
/* 3465 */                                                               out.write(32);
/* 3466 */                                                               out.print(FormatUtil.getString(dname));
/* 3467 */                                                               int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 3468 */                                                               if (evalDoAfterBody != 2)
/*      */                                                                 break;
/*      */                                                             }
/* 3471 */                                                             if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3472 */                                                               out = _jspx_page_context.popBody();
/*      */                                                             }
/*      */                                                           }
/* 3475 */                                                           if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 3476 */                                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                           }
/*      */                                                           
/* 3479 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 3480 */                                                           out.write("\n\t\t\t\t");
/*      */                                                         }
/*      */                                                       }
/*      */                                                     }
/*      */                                                     
/* 3485 */                                                     if (categoryLink[c].equals("VIR"))
/*      */                                                     {
/*      */ 
/* 3488 */                                                       out.write("\n\t\t\t\t\t");
/*      */                                                       
/* 3490 */                                                       OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3491 */                                                       _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 3492 */                                                       _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3494 */                                                       _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 3495 */                                                       int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 3496 */                                                       if (_jspx_eval_html_005foption_005f12 != 0) {
/* 3497 */                                                         if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3498 */                                                           out = _jspx_page_context.pushBody();
/* 3499 */                                                           _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 3500 */                                                           _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3503 */                                                           out.write(32);
/* 3504 */                                                           out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 3505 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 3506 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3509 */                                                         if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3510 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3513 */                                                       if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 3514 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                       }
/*      */                                                       
/* 3517 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 3518 */                                                       out.write("\n\t\t\t\t");
/*      */                                                     }
/*      */                                                   }
/*      */                                                 } }
/* 3522 */                                               String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 3523 */                                               if (!usertype.equals("F"))
/*      */                                               {
/* 3525 */                                                 if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                                 {
/* 3527 */                                                   out.write("\n    </optgroup> <optgroup label=\"");
/* 3528 */                                                   out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3529 */                                                   out.write("\">\n     ");
/*      */                                                   
/* 3531 */                                                   OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3532 */                                                   _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 3533 */                                                   _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3535 */                                                   _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 3536 */                                                   int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 3537 */                                                   if (_jspx_eval_html_005foption_005f13 != 0) {
/* 3538 */                                                     if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3539 */                                                       out = _jspx_page_context.pushBody();
/* 3540 */                                                       _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 3541 */                                                       _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3544 */                                                       out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3545 */                                                       out.write(32);
/* 3546 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 3547 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3550 */                                                     if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3551 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3554 */                                                   if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 3555 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                   }
/*      */                                                   
/* 3558 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 3559 */                                                   out.write("\n\n     ");
/*      */                                                 }
/*      */                                                 
/*      */                                               }
/*      */                                               
/*      */                                             }
/* 3565 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                             {
/*      */ 
/* 3568 */                                               out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 3569 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3570 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3572 */                                               OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3573 */                                               _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 3574 */                                               _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3576 */                                               _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 3577 */                                               int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 3578 */                                               if (_jspx_eval_html_005foption_005f14 != 0) {
/* 3579 */                                                 if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3580 */                                                   out = _jspx_page_context.pushBody();
/* 3581 */                                                   _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 3582 */                                                   _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3585 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3586 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 3587 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3590 */                                                 if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3591 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3594 */                                               if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 3595 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                               }
/*      */                                               
/* 3598 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 3599 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3600 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3601 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3603 */                                               OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3604 */                                               _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 3605 */                                               _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3607 */                                               _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 3608 */                                               int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 3609 */                                               if (_jspx_eval_html_005foption_005f15 != 0) {
/* 3610 */                                                 if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3611 */                                                   out = _jspx_page_context.pushBody();
/* 3612 */                                                   _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 3613 */                                                   _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3616 */                                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 3617 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 3618 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3621 */                                                 if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3622 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3625 */                                               if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 3626 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                               }
/*      */                                               
/* 3629 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 3630 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3631 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3632 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3634 */                                               OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3635 */                                               _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 3636 */                                               _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3638 */                                               _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 3639 */                                               int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 3640 */                                               if (_jspx_eval_html_005foption_005f16 != 0) {
/* 3641 */                                                 if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3642 */                                                   out = _jspx_page_context.pushBody();
/* 3643 */                                                   _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 3644 */                                                   _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3647 */                                                   out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3648 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 3649 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3652 */                                                 if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3653 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3656 */                                               if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 3657 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                               }
/*      */                                               
/* 3660 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 3661 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3663 */                                               OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3664 */                                               _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 3665 */                                               _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3667 */                                               _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 3668 */                                               int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 3669 */                                               if (_jspx_eval_html_005foption_005f17 != 0) {
/* 3670 */                                                 if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3671 */                                                   out = _jspx_page_context.pushBody();
/* 3672 */                                                   _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 3673 */                                                   _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3676 */                                                   out.write(32);
/* 3677 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 3678 */                                                   out.write(32);
/* 3679 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 3680 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3683 */                                                 if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3684 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3687 */                                               if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3688 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                               }
/*      */                                               
/* 3691 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3692 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3694 */                                               OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3695 */                                               _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3696 */                                               _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3698 */                                               _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 3699 */                                               int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3700 */                                               if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3701 */                                                 if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3702 */                                                   out = _jspx_page_context.pushBody();
/* 3703 */                                                   _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3704 */                                                   _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3707 */                                                   out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3708 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3709 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3712 */                                                 if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3713 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3716 */                                               if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3717 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                               }
/*      */                                               
/* 3720 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3721 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3723 */                                               OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3724 */                                               _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3725 */                                               _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3727 */                                               _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 3728 */                                               int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3729 */                                               if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3730 */                                                 if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3731 */                                                   out = _jspx_page_context.pushBody();
/* 3732 */                                                   _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3733 */                                                   _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3736 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 3737 */                                                   out.write(" (V1 or V2c)");
/* 3738 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3739 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3742 */                                                 if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3743 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3746 */                                               if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3747 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                               }
/*      */                                               
/* 3750 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3751 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3753 */                                               OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3754 */                                               _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3755 */                                               _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3757 */                                               _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 3758 */                                               int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3759 */                                               if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3760 */                                                 if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3761 */                                                   out = _jspx_page_context.pushBody();
/* 3762 */                                                   _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3763 */                                                   _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3766 */                                                   out.print(FormatUtil.getString("Telnet"));
/* 3767 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3768 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3771 */                                                 if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3772 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3775 */                                               if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3776 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                               }
/*      */                                               
/* 3779 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3780 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3781 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3782 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3784 */                                               OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3785 */                                               _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3786 */                                               _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3788 */                                               _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 3789 */                                               int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3790 */                                               if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3791 */                                                 if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3792 */                                                   out = _jspx_page_context.pushBody();
/* 3793 */                                                   _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3794 */                                                   _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3797 */                                                   out.write(32);
/* 3798 */                                                   out.print(FormatUtil.getString("Apache Server"));
/* 3799 */                                                   out.write(32);
/* 3800 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3801 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3804 */                                                 if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3805 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3808 */                                               if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3809 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                               }
/*      */                                               
/* 3812 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3813 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3815 */                                               OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3816 */                                               _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3817 */                                               _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3819 */                                               _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 3820 */                                               int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3821 */                                               if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3822 */                                                 if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3823 */                                                   out = _jspx_page_context.pushBody();
/* 3824 */                                                   _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3825 */                                                   _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3828 */                                                   out.print(FormatUtil.getString("PHP"));
/* 3829 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3830 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3833 */                                                 if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3834 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3837 */                                               if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3838 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                               }
/*      */                                               
/* 3841 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3842 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3844 */                                               OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3845 */                                               _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3846 */                                               _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3848 */                                               _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 3849 */                                               int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3850 */                                               if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3851 */                                                 if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3852 */                                                   out = _jspx_page_context.pushBody();
/* 3853 */                                                   _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3854 */                                                   _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3857 */                                                   out.print(FormatUtil.getString("HTTP-URLs"));
/* 3858 */                                                   out.write(32);
/* 3859 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3860 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3863 */                                                 if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3864 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3867 */                                               if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3868 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                               }
/*      */                                               
/* 3871 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3872 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3874 */                                               OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3875 */                                               _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3876 */                                               _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3878 */                                               _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 3879 */                                               int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3880 */                                               if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3881 */                                                 if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3882 */                                                   out = _jspx_page_context.pushBody();
/* 3883 */                                                   _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3884 */                                                   _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3887 */                                                   out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 3888 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3889 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3892 */                                                 if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3893 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3896 */                                               if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3897 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                               }
/*      */                                               
/* 3900 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3901 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3903 */                                               OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3904 */                                               _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3905 */                                               _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3907 */                                               _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 3908 */                                               int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3909 */                                               if (_jspx_eval_html_005foption_005f25 != 0) {
/* 3910 */                                                 if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3911 */                                                   out = _jspx_page_context.pushBody();
/* 3912 */                                                   _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 3913 */                                                   _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3916 */                                                   out.write(32);
/* 3917 */                                                   out.print(FormatUtil.getString("Web Server"));
/* 3918 */                                                   out.write(32);
/* 3919 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 3920 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3923 */                                                 if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3924 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3927 */                                               if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 3928 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                               }
/*      */                                               
/* 3931 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 3932 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3934 */                                               OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3935 */                                               _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 3936 */                                               _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3938 */                                               _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 3939 */                                               int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 3940 */                                               if (_jspx_eval_html_005foption_005f26 != 0) {
/* 3941 */                                                 if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3942 */                                                   out = _jspx_page_context.pushBody();
/* 3943 */                                                   _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 3944 */                                                   _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3947 */                                                   out.write(32);
/* 3948 */                                                   out.print(FormatUtil.getString("Web Service"));
/* 3949 */                                                   out.write(32);
/* 3950 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 3951 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3954 */                                                 if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3955 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3958 */                                               if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 3959 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                               }
/*      */                                               
/* 3962 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 3963 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3964 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 3965 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3967 */                                               OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3968 */                                               _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 3969 */                                               _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3971 */                                               _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 3972 */                                               int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 3973 */                                               if (_jspx_eval_html_005foption_005f27 != 0) {
/* 3974 */                                                 if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3975 */                                                   out = _jspx_page_context.pushBody();
/* 3976 */                                                   _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 3977 */                                                   _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3980 */                                                   out.print(FormatUtil.getString("Mail Server"));
/* 3981 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 3982 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3985 */                                                 if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3986 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3989 */                                               if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 3990 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                               }
/*      */                                               
/* 3993 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 3994 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3995 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 3996 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3998 */                                               OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3999 */                                               _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 4000 */                                               _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4002 */                                               _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 4003 */                                               int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 4004 */                                               if (_jspx_eval_html_005foption_005f28 != 0) {
/* 4005 */                                                 if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4006 */                                                   out = _jspx_page_context.pushBody();
/* 4007 */                                                   _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 4008 */                                                   _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4011 */                                                   out.write(32);
/* 4012 */                                                   out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4013 */                                                   out.write(32);
/* 4014 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 4015 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4018 */                                                 if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4019 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4022 */                                               if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 4023 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                               }
/*      */                                               
/* 4026 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 4027 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4029 */                                               OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4030 */                                               _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 4031 */                                               _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4033 */                                               _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 4034 */                                               int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 4035 */                                               if (_jspx_eval_html_005foption_005f29 != 0) {
/* 4036 */                                                 if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4037 */                                                   out = _jspx_page_context.pushBody();
/* 4038 */                                                   _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 4039 */                                                   _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4042 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 4043 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 4044 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4047 */                                                 if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4048 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4051 */                                               if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 4052 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                               }
/*      */                                               
/* 4055 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 4056 */                                               out.write("\n\n    ");
/*      */ 
/*      */                                             }
/* 4059 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                             {
/*      */ 
/* 4062 */                                               out.write("\n        ");
/*      */                                               
/* 4064 */                                               OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4065 */                                               _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 4066 */                                               _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4068 */                                               _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 4069 */                                               int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 4070 */                                               if (_jspx_eval_html_005foption_005f30 != 0) {
/* 4071 */                                                 if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4072 */                                                   out = _jspx_page_context.pushBody();
/* 4073 */                                                   _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 4074 */                                                   _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4077 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4078 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 4079 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4082 */                                                 if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4083 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4086 */                                               if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 4087 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                               }
/*      */                                               
/* 4090 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 4091 */                                               out.write("\n       ");
/*      */                                               
/* 4093 */                                               OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4094 */                                               _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 4095 */                                               _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4097 */                                               _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 4098 */                                               int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 4099 */                                               if (_jspx_eval_html_005foption_005f31 != 0) {
/* 4100 */                                                 if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4101 */                                                   out = _jspx_page_context.pushBody();
/* 4102 */                                                   _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 4103 */                                                   _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4106 */                                                   out.write(32);
/* 4107 */                                                   out.print(FormatUtil.getString("JBoss Server"));
/* 4108 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 4109 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4112 */                                                 if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4113 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4116 */                                               if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 4117 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                               }
/*      */                                               
/* 4120 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 4121 */                                               out.write("\n      ");
/*      */                                               
/* 4123 */                                               OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4124 */                                               _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 4125 */                                               _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4127 */                                               _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 4128 */                                               int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 4129 */                                               if (_jspx_eval_html_005foption_005f32 != 0) {
/* 4130 */                                                 if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4131 */                                                   out = _jspx_page_context.pushBody();
/* 4132 */                                                   _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 4133 */                                                   _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4136 */                                                   out.print(FormatUtil.getString("Tomcat Server"));
/* 4137 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 4138 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4141 */                                                 if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4142 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4145 */                                               if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 4146 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                               }
/*      */                                               
/* 4149 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 4150 */                                               out.write("\n       ");
/*      */                                               
/* 4152 */                                               OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4153 */                                               _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 4154 */                                               _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4156 */                                               _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 4157 */                                               int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 4158 */                                               if (_jspx_eval_html_005foption_005f33 != 0) {
/* 4159 */                                                 if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4160 */                                                   out = _jspx_page_context.pushBody();
/* 4161 */                                                   _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 4162 */                                                   _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4165 */                                                   out.write(32);
/* 4166 */                                                   out.print(FormatUtil.getString("WebLogic Server"));
/* 4167 */                                                   out.write(32);
/* 4168 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 4169 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4172 */                                                 if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4173 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4176 */                                               if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 4177 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                               }
/*      */                                               
/* 4180 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 4181 */                                               out.write("\n      ");
/*      */                                               
/* 4183 */                                               OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4184 */                                               _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 4185 */                                               _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4187 */                                               _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 4188 */                                               int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 4189 */                                               if (_jspx_eval_html_005foption_005f34 != 0) {
/* 4190 */                                                 if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4191 */                                                   out = _jspx_page_context.pushBody();
/* 4192 */                                                   _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 4193 */                                                   _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4196 */                                                   out.write(32);
/* 4197 */                                                   out.print(FormatUtil.getString("WebSphere Server"));
/* 4198 */                                                   out.write(32);
/* 4199 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 4200 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4203 */                                                 if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4204 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4207 */                                               if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 4208 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                               }
/*      */                                               
/* 4211 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 4212 */                                               out.write("\n      ");
/*      */                                               
/* 4214 */                                               OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4215 */                                               _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 4216 */                                               _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4218 */                                               _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 4219 */                                               int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 4220 */                                               if (_jspx_eval_html_005foption_005f35 != 0) {
/* 4221 */                                                 if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4222 */                                                   out = _jspx_page_context.pushBody();
/* 4223 */                                                   _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 4224 */                                                   _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4227 */                                                   out.print(FormatUtil.getString("Web Transactions"));
/* 4228 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 4229 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4232 */                                                 if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4233 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4236 */                                               if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 4237 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                               }
/*      */                                               
/* 4240 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 4241 */                                               out.write("\n      ");
/*      */                                               
/* 4243 */                                               OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4244 */                                               _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 4245 */                                               _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4247 */                                               _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 4248 */                                               int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 4249 */                                               if (_jspx_eval_html_005foption_005f36 != 0) {
/* 4250 */                                                 if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4251 */                                                   out = _jspx_page_context.pushBody();
/* 4252 */                                                   _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 4253 */                                                   _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4256 */                                                   out.print(FormatUtil.getString("Mail Server"));
/* 4257 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 4258 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4261 */                                                 if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4262 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4265 */                                               if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 4266 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                               }
/*      */                                               
/* 4269 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 4270 */                                               out.write("\n      ");
/*      */                                               
/* 4272 */                                               OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4273 */                                               _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 4274 */                                               _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4276 */                                               _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 4277 */                                               int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 4278 */                                               if (_jspx_eval_html_005foption_005f37 != 0) {
/* 4279 */                                                 if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4280 */                                                   out = _jspx_page_context.pushBody();
/* 4281 */                                                   _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 4282 */                                                   _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4285 */                                                   out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4286 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 4287 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4290 */                                                 if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4291 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4294 */                                               if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 4295 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                               }
/*      */                                               
/* 4298 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 4299 */                                               out.write("\n      ");
/*      */                                               
/* 4301 */                                               OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4302 */                                               _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 4303 */                                               _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4305 */                                               _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 4306 */                                               int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 4307 */                                               if (_jspx_eval_html_005foption_005f38 != 0) {
/* 4308 */                                                 if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4309 */                                                   out = _jspx_page_context.pushBody();
/* 4310 */                                                   _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 4311 */                                                   _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4314 */                                                   out.write(32);
/* 4315 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 4316 */                                                   out.write(32);
/* 4317 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 4318 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4321 */                                                 if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4322 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4325 */                                               if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 4326 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                               }
/*      */                                               
/* 4329 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 4330 */                                               out.write("\n      ");
/*      */                                               
/* 4332 */                                               OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4333 */                                               _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 4334 */                                               _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4336 */                                               _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 4337 */                                               int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 4338 */                                               if (_jspx_eval_html_005foption_005f39 != 0) {
/* 4339 */                                                 if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4340 */                                                   out = _jspx_page_context.pushBody();
/* 4341 */                                                   _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 4342 */                                                   _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4345 */                                                   out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4346 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 4347 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4350 */                                                 if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4351 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4354 */                                               if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 4355 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                               }
/*      */                                               
/* 4358 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 4359 */                                               out.write("\n      ");
/*      */                                               
/* 4361 */                                               OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4362 */                                               _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 4363 */                                               _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4365 */                                               _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 4366 */                                               int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 4367 */                                               if (_jspx_eval_html_005foption_005f40 != 0) {
/* 4368 */                                                 if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4369 */                                                   out = _jspx_page_context.pushBody();
/* 4370 */                                                   _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 4371 */                                                   _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4374 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 4375 */                                                   out.write(" (V1 or V2c)");
/* 4376 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 4377 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4380 */                                                 if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4381 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4384 */                                               if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 4385 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                               }
/*      */                                               
/* 4388 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 4389 */                                               out.write("\n      ");
/*      */                                               
/* 4391 */                                               OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4392 */                                               _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 4393 */                                               _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4395 */                                               _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 4396 */                                               int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 4397 */                                               if (_jspx_eval_html_005foption_005f41 != 0) {
/* 4398 */                                                 if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4399 */                                                   out = _jspx_page_context.pushBody();
/* 4400 */                                                   _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 4401 */                                                   _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4404 */                                                   out.write(32);
/* 4405 */                                                   out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4406 */                                                   out.write(32);
/* 4407 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 4408 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4411 */                                                 if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4412 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4415 */                                               if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 4416 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                               }
/*      */                                               
/* 4419 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 4420 */                                               out.write("\n      ");
/*      */                                               
/* 4422 */                                               OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4423 */                                               _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 4424 */                                               _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4426 */                                               _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 4427 */                                               int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 4428 */                                               if (_jspx_eval_html_005foption_005f42 != 0) {
/* 4429 */                                                 if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4430 */                                                   out = _jspx_page_context.pushBody();
/* 4431 */                                                   _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 4432 */                                                   _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4435 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 4436 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 4437 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4440 */                                                 if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4441 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4444 */                                               if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 4445 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                               }
/*      */                                               
/* 4448 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 4449 */                                               out.write("\n       ");
/*      */ 
/*      */                                             }
/* 4452 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                             {
/*      */ 
/* 4455 */                                               out.write("\n        ");
/*      */                                               
/* 4457 */                                               OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4458 */                                               _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 4459 */                                               _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4461 */                                               _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 4462 */                                               int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 4463 */                                               if (_jspx_eval_html_005foption_005f43 != 0) {
/* 4464 */                                                 if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4465 */                                                   out = _jspx_page_context.pushBody();
/* 4466 */                                                   _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 4467 */                                                   _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4470 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4471 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 4472 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4475 */                                                 if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4476 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4479 */                                               if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 4480 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                               }
/*      */                                               
/* 4483 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 4484 */                                               out.write("\n       ");
/*      */                                               
/* 4486 */                                               OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4487 */                                               _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 4488 */                                               _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4490 */                                               _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 4491 */                                               int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 4492 */                                               if (_jspx_eval_html_005foption_005f44 != 0) {
/* 4493 */                                                 if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4494 */                                                   out = _jspx_page_context.pushBody();
/* 4495 */                                                   _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 4496 */                                                   _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4499 */                                                   out.print(FormatUtil.getString("Microsoft .NET"));
/* 4500 */                                                   out.write(32);
/* 4501 */                                                   out.write(32);
/* 4502 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 4503 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4506 */                                                 if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4507 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4510 */                                               if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 4511 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                               }
/*      */                                               
/* 4514 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 4515 */                                               out.write("\n      ");
/*      */                                               
/* 4517 */                                               OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4518 */                                               _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 4519 */                                               _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4521 */                                               _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 4522 */                                               int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 4523 */                                               if (_jspx_eval_html_005foption_005f45 != 0) {
/* 4524 */                                                 if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4525 */                                                   out = _jspx_page_context.pushBody();
/* 4526 */                                                   _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 4527 */                                                   _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4530 */                                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4531 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 4532 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4535 */                                                 if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4536 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4539 */                                               if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 4540 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                               }
/*      */                                               
/* 4543 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 4544 */                                               out.write("\n      ");
/*      */                                               
/* 4546 */                                               OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4547 */                                               _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 4548 */                                               _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4550 */                                               _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 4551 */                                               int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 4552 */                                               if (_jspx_eval_html_005foption_005f46 != 0) {
/* 4553 */                                                 if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4554 */                                                   out = _jspx_page_context.pushBody();
/* 4555 */                                                   _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 4556 */                                                   _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4559 */                                                   out.print(FormatUtil.getString("Exchange Server"));
/* 4560 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 4561 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4564 */                                                 if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4565 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4568 */                                               if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 4569 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                               }
/*      */                                               
/* 4572 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 4573 */                                               out.write("\n\t  ");
/*      */                                               
/* 4575 */                                               OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4576 */                                               _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 4577 */                                               _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4579 */                                               _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 4580 */                                               int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 4581 */                                               if (_jspx_eval_html_005foption_005f47 != 0) {
/* 4582 */                                                 if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4583 */                                                   out = _jspx_page_context.pushBody();
/* 4584 */                                                   _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 4585 */                                                   _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4588 */                                                   out.write(32);
/* 4589 */                                                   out.print(FormatUtil.getString("IIS Server"));
/* 4590 */                                                   out.write(32);
/* 4591 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 4592 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4595 */                                                 if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4596 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4599 */                                               if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 4600 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                               }
/*      */                                               
/* 4603 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 4604 */                                               out.write("\n      ");
/*      */                                               
/* 4606 */                                               OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4607 */                                               _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 4608 */                                               _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4610 */                                               _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 4611 */                                               int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 4612 */                                               if (_jspx_eval_html_005foption_005f48 != 0) {
/* 4613 */                                                 if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4614 */                                                   out = _jspx_page_context.pushBody();
/* 4615 */                                                   _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 4616 */                                                   _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4619 */                                                   out.write(32);
/* 4620 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 4621 */                                                   out.write(32);
/* 4622 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 4623 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4626 */                                                 if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4627 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4630 */                                               if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 4631 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                               }
/*      */                                               
/* 4634 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 4635 */                                               out.write("\n\t  ");
/*      */                                               
/* 4637 */                                               OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4638 */                                               _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 4639 */                                               _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4641 */                                               _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 4642 */                                               int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 4643 */                                               if (_jspx_eval_html_005foption_005f49 != 0) {
/* 4644 */                                                 if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4645 */                                                   out = _jspx_page_context.pushBody();
/* 4646 */                                                   _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 4647 */                                                   _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4650 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 4651 */                                                   out.write(" (V1 or V2c)");
/* 4652 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 4653 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4656 */                                                 if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4657 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4660 */                                               if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 4661 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                               }
/*      */                                               
/* 4664 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 4665 */                                               out.write("\n      ");
/*      */                                               
/* 4667 */                                               OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4668 */                                               _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 4669 */                                               _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4671 */                                               _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 4672 */                                               int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 4673 */                                               if (_jspx_eval_html_005foption_005f50 != 0) {
/* 4674 */                                                 if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4675 */                                                   out = _jspx_page_context.pushBody();
/* 4676 */                                                   _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 4677 */                                                   _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4680 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 4681 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 4682 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4685 */                                                 if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4686 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4689 */                                               if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 4690 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                               }
/*      */                                               
/* 4693 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 4694 */                                               out.write(10);
/*      */ 
/*      */                                             }
/* 4697 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                             {
/*      */ 
/* 4700 */                                               out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 4701 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4702 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 4704 */                                               OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4705 */                                               _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 4706 */                                               _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4708 */                                               _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 4709 */                                               int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 4710 */                                               if (_jspx_eval_html_005foption_005f51 != 0) {
/* 4711 */                                                 if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4712 */                                                   out = _jspx_page_context.pushBody();
/* 4713 */                                                   _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 4714 */                                                   _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4717 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4718 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 4719 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4722 */                                                 if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4723 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4726 */                                               if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 4727 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                               }
/*      */                                               
/* 4730 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 4731 */                                               out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4732 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4733 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 4735 */                                               OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4736 */                                               _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 4737 */                                               _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4739 */                                               _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 4740 */                                               int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 4741 */                                               if (_jspx_eval_html_005foption_005f52 != 0) {
/* 4742 */                                                 if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4743 */                                                   out = _jspx_page_context.pushBody();
/* 4744 */                                                   _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 4745 */                                                   _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4748 */                                                   out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 4749 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 4750 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4753 */                                                 if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4754 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4757 */                                               if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 4758 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                               }
/*      */                                               
/* 4761 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 4762 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4764 */                                               OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4765 */                                               _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 4766 */                                               _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4768 */                                               _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 4769 */                                               int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 4770 */                                               if (_jspx_eval_html_005foption_005f53 != 0) {
/* 4771 */                                                 if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4772 */                                                   out = _jspx_page_context.pushBody();
/* 4773 */                                                   _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 4774 */                                                   _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4777 */                                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4778 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 4779 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4782 */                                                 if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4783 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4786 */                                               if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 4787 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                               }
/*      */                                               
/* 4790 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 4791 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4793 */                                               OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4794 */                                               _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 4795 */                                               _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4797 */                                               _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 4798 */                                               int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 4799 */                                               if (_jspx_eval_html_005foption_005f54 != 0) {
/* 4800 */                                                 if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4801 */                                                   out = _jspx_page_context.pushBody();
/* 4802 */                                                   _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 4803 */                                                   _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4806 */                                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4807 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 4808 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4811 */                                                 if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4812 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4815 */                                               if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 4816 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                               }
/*      */                                               
/* 4819 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 4820 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4822 */                                               OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4823 */                                               _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 4824 */                                               _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4826 */                                               _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 4827 */                                               int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 4828 */                                               if (_jspx_eval_html_005foption_005f55 != 0) {
/* 4829 */                                                 if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4830 */                                                   out = _jspx_page_context.pushBody();
/* 4831 */                                                   _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 4832 */                                                   _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4835 */                                                   out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 4836 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 4837 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4840 */                                                 if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4841 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4844 */                                               if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 4845 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                               }
/*      */                                               
/* 4848 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 4849 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4851 */                                               OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4852 */                                               _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 4853 */                                               _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4855 */                                               _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 4856 */                                               int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 4857 */                                               if (_jspx_eval_html_005foption_005f56 != 0) {
/* 4858 */                                                 if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4859 */                                                   out = _jspx_page_context.pushBody();
/* 4860 */                                                   _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 4861 */                                                   _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4864 */                                                   out.print(FormatUtil.getString("Sybase"));
/* 4865 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 4866 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4869 */                                                 if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4870 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4873 */                                               if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 4874 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                               }
/*      */                                               
/* 4877 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 4878 */                                               out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4879 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4880 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 4882 */                                               OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4883 */                                               _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 4884 */                                               _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4886 */                                               _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 4887 */                                               int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 4888 */                                               if (_jspx_eval_html_005foption_005f57 != 0) {
/* 4889 */                                                 if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4890 */                                                   out = _jspx_page_context.pushBody();
/* 4891 */                                                   _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 4892 */                                                   _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4895 */                                                   out.write(32);
/* 4896 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 4897 */                                                   out.write(32);
/* 4898 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 4899 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4902 */                                                 if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4903 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4906 */                                               if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 4907 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                               }
/*      */                                               
/* 4910 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 4911 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4913 */                                               OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4914 */                                               _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 4915 */                                               _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4917 */                                               _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 4918 */                                               int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 4919 */                                               if (_jspx_eval_html_005foption_005f58 != 0) {
/* 4920 */                                                 if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4921 */                                                   out = _jspx_page_context.pushBody();
/* 4922 */                                                   _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 4923 */                                                   _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4926 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 4927 */                                                   out.write(" (V1 or V2c)");
/* 4928 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 4929 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4932 */                                                 if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4933 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4936 */                                               if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 4937 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                               }
/*      */                                               
/* 4940 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 4941 */                                               out.write("</optgroup>");
/* 4942 */                                               out.write("\n\t\t\t<optgroup label=\"");
/* 4943 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4944 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 4946 */                                               OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4947 */                                               _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 4948 */                                               _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4950 */                                               _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 4951 */                                               int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 4952 */                                               if (_jspx_eval_html_005foption_005f59 != 0) {
/* 4953 */                                                 if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4954 */                                                   out = _jspx_page_context.pushBody();
/* 4955 */                                                   _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 4956 */                                                   _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4959 */                                                   out.print(FormatUtil.getString("HTTP-URLs"));
/* 4960 */                                                   out.write(32);
/* 4961 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 4962 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4965 */                                                 if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4966 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4969 */                                               if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 4970 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                               }
/*      */                                               
/* 4973 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 4974 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4976 */                                               OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4977 */                                               _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 4978 */                                               _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4980 */                                               _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 4981 */                                               int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 4982 */                                               if (_jspx_eval_html_005foption_005f60 != 0) {
/* 4983 */                                                 if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4984 */                                                   out = _jspx_page_context.pushBody();
/* 4985 */                                                   _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 4986 */                                                   _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4989 */                                                   out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 4990 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 4991 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4994 */                                                 if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4995 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4998 */                                               if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 4999 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                               }
/*      */                                               
/* 5002 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 5003 */                                               out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5004 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 5005 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 5007 */                                               OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5008 */                                               _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 5009 */                                               _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5011 */                                               _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 5012 */                                               int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 5013 */                                               if (_jspx_eval_html_005foption_005f61 != 0) {
/* 5014 */                                                 if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5015 */                                                   out = _jspx_page_context.pushBody();
/* 5016 */                                                   _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 5017 */                                                   _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5020 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 5021 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 5022 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5025 */                                                 if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5026 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5029 */                                               if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 5030 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                               }
/*      */                                               
/* 5033 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 5034 */                                               out.write(10);
/* 5035 */                                               out.write(10);
/*      */                                             }
/*      */                                             
/*      */ 
/* 5039 */                                             out.write("\n\n\n\n      ");
/* 5040 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 5041 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5044 */                                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5045 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5048 */                                         if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 5049 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                         }
/*      */                                         
/* 5052 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 5053 */                                         out.write("\n                      \n      ");
/* 5054 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5055 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5059 */                                     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5060 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                     }
/*      */                                     
/* 5063 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5064 */                                     out.write("\n      ");
/* 5065 */                                     if (_jspx_meth_c_005fif_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5067 */                                     out.write("\n      </td>\n      \n      ");
/* 5068 */                                     if (request.getParameter("type") != null) {
/* 5069 */                                       isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 5070 */                                       String restype = request.getParameter("type");
/* 5071 */                                       if (restype.indexOf(":") != -1) {
/* 5072 */                                         restype = restype.substring(0, restype.indexOf(":"));
/*      */                                       }
/* 5074 */                                       if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 5075 */                                         out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 5076 */                                         out.print(restype);
/* 5077 */                                         out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 5078 */                                         out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 5079 */                                         out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                       }
/*      */                                     }
/* 5082 */                                     out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 5083 */                                     out.write("\n\n\n <input type=\"hidden\" name=\"method\" value=\"createscript\"/>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"1\" class=\"lrborder\">\n  <tr> \n    <td height=\"20\" class=\"bodytext\">");
/* 5084 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.qengine.disname"));
/* 5085 */                                     out.write("<span class=\"mandatory\">*</span></td>\n    <td height=\"20\" colspan=\"2\"> ");
/* 5086 */                                     if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5088 */                                     out.write(" \n\t<span><a target=\"_blank\" href=\"/help/monitors/qengine-script-monitoring.html\" class=\"footer\">");
/* 5089 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/* 5090 */                                     out.write("</a></span>\n    </td>\n  </tr>\n");
/* 5091 */                                     out.write("\n  <tr> \n    <td width=\"20%\" height=\"35\" class=\"bodytext\" ><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 5092 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.qengine.scriptpath.message"));
/* 5093 */                                     out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 5094 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.qengine.scriptpath"));
/* 5095 */                                     out.write("</a><span class=\"mandatory\">*</span></td>\n    <td height=\"35\" colspan=\"2\" class=\"bodytext\"> ");
/* 5096 */                                     if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5098 */                                     out.write(" \n</td>\n   \n  </tr>    \n");
/* 5099 */                                     out.write("\n      ");
/* 5100 */                                     if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5102 */                                     out.write(10);
/* 5103 */                                     out.write(32);
/* 5104 */                                     out.write(32);
/* 5105 */                                     out.write("\n  <tr>\n    <td width=\"24%\" height=\"26\" class=\"bodytext\">");
/* 5106 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.polling"));
/* 5107 */                                     out.write("<span class=\"mandatory\"> <span class=\"mandatory\">*</span></td>\n    <td height=\"26\" colspan=\"2\" class=\"bodytext\"> ");
/* 5108 */                                     if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5110 */                                     out.write(" \n      <span class=\"footer\">");
/* 5111 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 5112 */                                     out.write("</span></td>\n  </tr>\n  \n  <tr> \n    <td height=\"20\" class=\"bodytext\"><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 5113 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.qengine.timeout.message"));
/* 5114 */                                     out.write("',false,true,'#000000',400,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 5115 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.timeout"));
/* 5116 */                                     out.write("</a><span class=\"mandatory\">*</span></td>\n    <td height=\"20\" colspan=\"2\"> ");
/* 5117 */                                     if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5119 */                                     out.write("\n</td>\n  </tr>\n\n  ");
/*      */                                     
/* 5121 */                                     if ((!System.getProperty("os.name").startsWith("windows")) && (!System.getProperty("os.name").startsWith("Windows")))
/*      */                                     {
/*      */ 
/* 5124 */                                       out.write("\n  <tr> \n    <td height=\"20\" class=\"bodytext\">");
/* 5125 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.qengine.mode"));
/* 5126 */                                       out.write("<span class=\"mandatory\"></span></td>\n    <td height=\"20\" colspan=\"2\"> ");
/* 5127 */                                       if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 5129 */                                       out.write("\n    </td>\n  </tr>\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 5133 */                                     out.write("\n  \n \n</table>\n  <table class=\"lrbborder\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" width=\"99%\">\n   <tbody>\n    <tr> \n      <td width=\"24%\" height=\"22\" class=\"tablebottom\">&nbsp;</td>\n      <td width=\"76%\" height=\"26\" class=\"tablebottom\"> ");
/*      */                                     
/* 5135 */                                     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5136 */                                     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5137 */                                     _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5139 */                                     _jspx_th_c_005fif_005f4.setTest("${empty method}");
/* 5140 */                                     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5141 */                                     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                       for (;;) {
/* 5143 */                                         out.write(32);
/*      */                                         
/* 5145 */                                         ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5146 */                                         _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 5147 */                                         _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_c_005fif_005f4);
/*      */                                         
/* 5149 */                                         _jspx_th_html_005fbutton_005f0.setStyleClass("buttons");
/*      */                                         
/* 5151 */                                         _jspx_th_html_005fbutton_005f0.setValue(FormatUtil.getString("am.webclient.hostdiscovery.qengine.button.addscript"));
/*      */                                         
/* 5153 */                                         _jspx_th_html_005fbutton_005f0.setOnclick("validateAndSubmit();");
/*      */                                         
/* 5155 */                                         _jspx_th_html_005fbutton_005f0.setProperty("submitbutton1");
/* 5156 */                                         int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 5157 */                                         if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 5158 */                                           this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                                         }
/*      */                                         
/* 5161 */                                         this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 5162 */                                         out.write(" \n        ");
/* 5163 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5164 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5168 */                                     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5169 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                     }
/*      */                                     
/* 5172 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5173 */                                     out.write(32);
/*      */                                     
/* 5175 */                                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5176 */                                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 5177 */                                     _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5179 */                                     _jspx_th_c_005fif_005f5.setTest("${!empty method}");
/* 5180 */                                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 5181 */                                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                       for (;;) {
/* 5183 */                                         out.write(" \n        ");
/*      */                                         
/* 5185 */                                         ButtonTag _jspx_th_html_005fbutton_005f1 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5186 */                                         _jspx_th_html_005fbutton_005f1.setPageContext(_jspx_page_context);
/* 5187 */                                         _jspx_th_html_005fbutton_005f1.setParent(_jspx_th_c_005fif_005f5);
/*      */                                         
/* 5189 */                                         _jspx_th_html_005fbutton_005f1.setOnclick("return validateAndSubmit();");
/*      */                                         
/* 5191 */                                         _jspx_th_html_005fbutton_005f1.setStyleClass("buttons");
/*      */                                         
/* 5193 */                                         _jspx_th_html_005fbutton_005f1.setProperty("submitbutton1");
/*      */                                         
/* 5195 */                                         _jspx_th_html_005fbutton_005f1.setValue(FormatUtil.getString("am.webclient.hostdiscovery.qengine.button.update"));
/* 5196 */                                         int _jspx_eval_html_005fbutton_005f1 = _jspx_th_html_005fbutton_005f1.doStartTag();
/* 5197 */                                         if (_jspx_th_html_005fbutton_005f1.doEndTag() == 5) {
/* 5198 */                                           this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1); return;
/*      */                                         }
/*      */                                         
/* 5201 */                                         this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 5202 */                                         out.write("\n        ");
/* 5203 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5204 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5208 */                                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5209 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                     }
/*      */                                     
/* 5212 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5213 */                                     out.write(" &nbsp;\n\t\t");
/*      */                                     
/* 5215 */                                     ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.get(ResetTag.class);
/* 5216 */                                     _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 5217 */                                     _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5219 */                                     _jspx_th_html_005freset_005f0.setStyleClass("buttons");
/*      */                                     
/* 5221 */                                     _jspx_th_html_005freset_005f0.setValue(FormatUtil.getString("am.webclient.hostdiscovery.qengine.button.restore"));
/* 5222 */                                     int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 5223 */                                     if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 5224 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                                     }
/*      */                                     
/* 5227 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 5228 */                                     out.write(" &nbsp;\n\t\t");
/*      */                                     
/* 5230 */                                     ResetTag _jspx_th_html_005freset_005f1 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 5231 */                                     _jspx_th_html_005freset_005f1.setPageContext(_jspx_page_context);
/* 5232 */                                     _jspx_th_html_005freset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5234 */                                     _jspx_th_html_005freset_005f1.setStyleClass("buttons");
/*      */                                     
/* 5236 */                                     _jspx_th_html_005freset_005f1.setValue(FormatUtil.getString("webclient.fault.alarm.customview.button.cancel"));
/*      */                                     
/* 5238 */                                     _jspx_th_html_005freset_005f1.setOnclick("javascript:history.back();");
/*      */                                     
/* 5240 */                                     _jspx_th_html_005freset_005f1.setStyleId("cancelButtonMod");
/* 5241 */                                     int _jspx_eval_html_005freset_005f1 = _jspx_th_html_005freset_005f1.doStartTag();
/* 5242 */                                     if (_jspx_th_html_005freset_005f1.doEndTag() == 5) {
/* 5243 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1); return;
/*      */                                     }
/*      */                                     
/* 5246 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1);
/* 5247 */                                     out.write("</td>\n    </tr>\n\t</tbody>\n</table>\n\n    \n");
/* 5248 */                                     if (_jspx_meth_c_005fif_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5250 */                                     out.write("\n        ");
/* 5251 */                                     if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5253 */                                     out.write(10);
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 5259 */                                     out.write("\n<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">  \n      <input name=\"closeButton\" type=\"button\" class=\"buttons\" value=\"");
/* 5260 */                                     out.print(FormatUtil.getString("Close Window"));
/* 5261 */                                     out.write("\" onClick=\"closePopUpWindow();\">\n      ");
/* 5262 */                                     if (!isDiscoverySuccess) {
/* 5263 */                                       out.write("\n              ");
/*      */                                       
/* 5265 */                                       ResetTag _jspx_th_html_005freset_005f2 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 5266 */                                       _jspx_th_html_005freset_005f2.setPageContext(_jspx_page_context);
/* 5267 */                                       _jspx_th_html_005freset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5269 */                                       _jspx_th_html_005freset_005f2.setStyleClass("buttons");
/*      */                                       
/* 5271 */                                       _jspx_th_html_005freset_005f2.setValue(FormatUtil.getString("am.webclient.goback.readd.txt"));
/*      */                                       
/* 5273 */                                       _jspx_th_html_005freset_005f2.setOnclick("javascript:history.back();");
/* 5274 */                                       int _jspx_eval_html_005freset_005f2 = _jspx_th_html_005freset_005f2.doStartTag();
/* 5275 */                                       if (_jspx_th_html_005freset_005f2.doEndTag() == 5) {
/* 5276 */                                         this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f2); return;
/*      */                                       }
/*      */                                       
/* 5279 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f2);
/* 5280 */                                       out.write("\n      ");
/*      */                                     }
/* 5282 */                                     out.write("\n      </td>\n      </tr>\n      </table>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5286 */                                   if (hideFields)
/*      */                                   {
/*      */ 
/* 5289 */                                     out.write("\n\t<script>\n\t\t$(document.body).ready(function(){\n\t\tdocument.getElementById(\"cancelButtonMod\").onclick = null;\n\t\t$(\"#cancelButtonMod\").click(function(){ //No I18N\n\t\t\tclosePopUpWindow();\n\t\t});\n\t\t});\n\t</script>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5293 */                                   out.write(10);
/* 5294 */                                   out.write(10);
/* 5295 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5296 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5300 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5301 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 5304 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5305 */                               out.write(32);
/* 5306 */                               out.write(10);
/* 5307 */                               out.write(10);
/* 5308 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 5309 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 5312 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 5313 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 5316 */                           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 5317 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                           }
/*      */                           
/* 5320 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 5321 */                           out.write(10);
/* 5322 */                           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 5324 */                           out.write(10);
/* 5325 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5326 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5330 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5331 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                       }
/*      */                       else {
/* 5334 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5335 */                         out.write(10);
/*      */                       }
/* 5337 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5338 */         out = _jspx_out;
/* 5339 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5340 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5341 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5344 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5350 */     PageContext pageContext = _jspx_page_context;
/* 5351 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5353 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5354 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 5355 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 5357 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 5358 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 5359 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 5361 */         out.write("\nalertUser();\n");
/* 5362 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 5363 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5367 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 5368 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5369 */       return true;
/*      */     }
/* 5371 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5377 */     PageContext pageContext = _jspx_page_context;
/* 5378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5380 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5381 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 5382 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5384 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 5386 */     _jspx_th_tiles_005fput_005f0.setValue("Script Monitoring");
/* 5387 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 5388 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 5389 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5390 */       return true;
/*      */     }
/* 5392 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5393 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5398 */     PageContext pageContext = _jspx_page_context;
/* 5399 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5401 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5402 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5403 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5405 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5407 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 5408 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5409 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5410 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5411 */       return true;
/*      */     }
/* 5413 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5419 */     PageContext pageContext = _jspx_page_context;
/* 5420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5422 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5423 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 5424 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5426 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */     
/* 5428 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/DiscoveryLeftLinks.jsp");
/* 5429 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 5430 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5431 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5432 */       return true;
/*      */     }
/* 5434 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5440 */     PageContext pageContext = _jspx_page_context;
/* 5441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5443 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 5444 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 5445 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5447 */     _jspx_th_am_005fhiddenparam_005f0.setName("wiz");
/* 5448 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 5449 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 5450 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 5451 */       return true;
/*      */     }
/* 5453 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 5454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5459 */     PageContext pageContext = _jspx_page_context;
/* 5460 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5462 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5463 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5464 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5466 */     _jspx_th_c_005fif_005f3.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 5467 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5468 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 5470 */         out.write("\n      ");
/* 5471 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5472 */           return true;
/* 5473 */         out.write("\n      ");
/* 5474 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5475 */           return true;
/* 5476 */         out.write("\n      ");
/* 5477 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5478 */           return true;
/* 5479 */         out.write("\n      ");
/* 5480 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5481 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5485 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5486 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5487 */       return true;
/*      */     }
/* 5489 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5495 */     PageContext pageContext = _jspx_page_context;
/* 5496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5498 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5499 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 5500 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/* 5501 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 5502 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 5504 */         out.write("\n        ");
/* 5505 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5506 */           return true;
/* 5507 */         out.write("\n        ");
/* 5508 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5509 */           return true;
/* 5510 */         out.write("\n\n        ");
/* 5511 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5512 */           return true;
/* 5513 */         out.write("\n      ");
/* 5514 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 5515 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5519 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 5520 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5521 */       return true;
/*      */     }
/* 5523 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5524 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5529 */     PageContext pageContext = _jspx_page_context;
/* 5530 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5532 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5533 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 5534 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5536 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 5537 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 5538 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 5540 */         out.write("\n          ");
/* 5541 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 5542 */           return true;
/* 5543 */         out.write("\n        ");
/* 5544 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 5545 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5549 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 5550 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5551 */       return true;
/*      */     }
/* 5553 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5554 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5559 */     PageContext pageContext = _jspx_page_context;
/* 5560 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5562 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5563 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5564 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5566 */     _jspx_th_c_005fout_005f0.setValue("Web Transaction Monitor");
/* 5567 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5568 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5569 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5570 */       return true;
/*      */     }
/* 5572 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5573 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5578 */     PageContext pageContext = _jspx_page_context;
/* 5579 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5581 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5582 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 5583 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5585 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 5586 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 5587 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 5589 */         out.write("\n          ");
/* 5590 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 5591 */           return true;
/* 5592 */         out.write("\n        ");
/* 5593 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 5594 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5598 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 5599 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5600 */       return true;
/*      */     }
/* 5602 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5608 */     PageContext pageContext = _jspx_page_context;
/* 5609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5611 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5612 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5613 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 5615 */     _jspx_th_c_005fout_005f1.setValue("Tomcat Server");
/* 5616 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5617 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5618 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5619 */       return true;
/*      */     }
/* 5621 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5627 */     PageContext pageContext = _jspx_page_context;
/* 5628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5630 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5631 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 5632 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 5633 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 5634 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 5636 */         out.write("\n         ");
/* 5637 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 5638 */           return true;
/* 5639 */         out.write("\n        ");
/* 5640 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 5641 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5645 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 5646 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5647 */       return true;
/*      */     }
/* 5649 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5655 */     PageContext pageContext = _jspx_page_context;
/* 5656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5658 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 5659 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5660 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5662 */     _jspx_th_c_005fout_005f2.setValue("${param.type}");
/* 5663 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5664 */     if (_jspx_eval_c_005fout_005f2 != 0) {
/* 5665 */       if (_jspx_eval_c_005fout_005f2 != 1) {
/* 5666 */         out = _jspx_page_context.pushBody();
/* 5667 */         _jspx_th_c_005fout_005f2.setBodyContent((BodyContent)out);
/* 5668 */         _jspx_th_c_005fout_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5671 */         out.write(45);
/* 5672 */         int evalDoAfterBody = _jspx_th_c_005fout_005f2.doAfterBody();
/* 5673 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5676 */       if (_jspx_eval_c_005fout_005f2 != 1) {
/* 5677 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5680 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5681 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f2);
/* 5682 */       return true;
/*      */     }
/* 5684 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f2);
/* 5685 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5690 */     PageContext pageContext = _jspx_page_context;
/* 5691 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5693 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5694 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 5695 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5697 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 5698 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 5699 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 5700 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5701 */       return true;
/*      */     }
/* 5703 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5704 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5709 */     PageContext pageContext = _jspx_page_context;
/* 5710 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5712 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5713 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 5714 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5716 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 5717 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 5718 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 5719 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5720 */       return true;
/*      */     }
/* 5722 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5728 */     PageContext pageContext = _jspx_page_context;
/* 5729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5731 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 5732 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 5733 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5735 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 5737 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 5738 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 5739 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 5740 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5741 */       return true;
/*      */     }
/* 5743 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5749 */     PageContext pageContext = _jspx_page_context;
/* 5750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5752 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5753 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 5754 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5756 */     _jspx_th_html_005ftext_005f1.setSize("100");
/*      */     
/* 5758 */     _jspx_th_html_005ftext_005f1.setProperty("message");
/*      */     
/* 5760 */     _jspx_th_html_005ftext_005f1.setValue("");
/*      */     
/* 5762 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 5763 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 5764 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 5765 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5766 */       return true;
/*      */     }
/* 5768 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5769 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5774 */     PageContext pageContext = _jspx_page_context;
/* 5775 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5777 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5778 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 5779 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5781 */     _jspx_th_html_005fhidden_005f2.setProperty("delimiter");
/* 5782 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 5783 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 5784 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 5785 */       return true;
/*      */     }
/* 5787 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 5788 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5793 */     PageContext pageContext = _jspx_page_context;
/* 5794 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5796 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5797 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 5798 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5800 */     _jspx_th_html_005ftext_005f2.setProperty("pollInterval");
/*      */     
/* 5802 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 5804 */     _jspx_th_html_005ftext_005f2.setSize("5");
/* 5805 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 5806 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 5807 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5808 */       return true;
/*      */     }
/* 5810 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5816 */     PageContext pageContext = _jspx_page_context;
/* 5817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5819 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 5820 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 5821 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5823 */     _jspx_th_html_005ftext_005f3.setProperty("timeout");
/*      */     
/* 5825 */     _jspx_th_html_005ftext_005f3.setValue("60");
/*      */     
/* 5827 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/* 5828 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 5829 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 5830 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5831 */       return true;
/*      */     }
/* 5833 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5834 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5839 */     PageContext pageContext = _jspx_page_context;
/* 5840 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5842 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 5843 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 5844 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5846 */     _jspx_th_html_005ftext_005f4.setProperty("mode");
/*      */     
/* 5848 */     _jspx_th_html_005ftext_005f4.setValue("sh");
/*      */     
/* 5850 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/* 5851 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 5852 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 5853 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 5854 */       return true;
/*      */     }
/* 5856 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 5857 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5862 */     PageContext pageContext = _jspx_page_context;
/* 5863 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5865 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5866 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 5867 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5869 */     _jspx_th_c_005fif_005f6.setTest("${!empty param.haid && empty invalidhaid}");
/* 5870 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 5871 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 5873 */         out.write("\n\t<input type=\"hidden\" name=\"haid\"  value='");
/* 5874 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 5875 */           return true;
/* 5876 */         out.write("'>\n\t");
/* 5877 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5878 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5882 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5883 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5884 */       return true;
/*      */     }
/* 5886 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5887 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5892 */     PageContext pageContext = _jspx_page_context;
/* 5893 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5895 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5896 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5897 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5899 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 5900 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5901 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5902 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5903 */       return true;
/*      */     }
/* 5905 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5906 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5911 */     PageContext pageContext = _jspx_page_context;
/* 5912 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5914 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5915 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5916 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5918 */     _jspx_th_c_005fif_005f7.setTest("${!empty param.name}");
/* 5919 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5920 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5922 */         out.write("\n        <input type=\"hidden\" name=\"name\"  value='");
/* 5923 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 5924 */           return true;
/* 5925 */         out.write("'>\n        ");
/* 5926 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5927 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5931 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5932 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5933 */       return true;
/*      */     }
/* 5935 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5936 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5941 */     PageContext pageContext = _jspx_page_context;
/* 5942 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5944 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5945 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5946 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5948 */     _jspx_th_c_005fout_005f4.setValue("${param.name}");
/* 5949 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5950 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5951 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5952 */       return true;
/*      */     }
/* 5954 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5955 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5960 */     PageContext pageContext = _jspx_page_context;
/* 5961 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5963 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5964 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 5965 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5967 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 5969 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 5970 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 5971 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5972 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5973 */       return true;
/*      */     }
/* 5975 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5976 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\newQEnginescript_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */