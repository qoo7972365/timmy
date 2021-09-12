/*      */ package org.apache.jsp.jsp.as400;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.AS400Graphs;
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class status_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   46 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   49 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   50 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   51 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   58 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   63 */     ArrayList list = null;
/*   64 */     StringBuffer sbf = new StringBuffer();
/*   65 */     ManagedApplication mo = new ManagedApplication();
/*   66 */     if (distinct)
/*      */     {
/*   68 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   72 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   75 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   77 */       ArrayList row = (ArrayList)list.get(i);
/*   78 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   79 */       if (distinct) {
/*   80 */         sbf.append(row.get(0));
/*      */       } else
/*   82 */         sbf.append(row.get(1));
/*   83 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   86 */     return sbf.toString(); }
/*      */   
/*   88 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   91 */     if (severity == null)
/*      */     {
/*   93 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   95 */     if (severity.equals("5"))
/*      */     {
/*   97 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   99 */     if (severity.equals("1"))
/*      */     {
/*  101 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  106 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  113 */     if (severity == null)
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  117 */     if (severity.equals("1"))
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  121 */     if (severity.equals("4"))
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  125 */     if (severity.equals("5"))
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  132 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  138 */     if (severity == null)
/*      */     {
/*  140 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  142 */     if (severity.equals("5"))
/*      */     {
/*  144 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  146 */     if (severity.equals("1"))
/*      */     {
/*  148 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  152 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  158 */     if (severity == null)
/*      */     {
/*  160 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  162 */     if (severity.equals("1"))
/*      */     {
/*  164 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  166 */     if (severity.equals("4"))
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  170 */     if (severity.equals("5"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  176 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  182 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  188 */     if (severity == 5)
/*      */     {
/*  190 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  192 */     if (severity == 1)
/*      */     {
/*  194 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  199 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  205 */     if (severity == null)
/*      */     {
/*  207 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  209 */     if (severity.equals("5"))
/*      */     {
/*  211 */       if (isAvailability) {
/*  212 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  215 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  218 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  220 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  222 */     if (severity.equals("1"))
/*      */     {
/*  224 */       if (isAvailability) {
/*  225 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  228 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  235 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  242 */     if (severity == null)
/*      */     {
/*  244 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  246 */     if (severity.equals("5"))
/*      */     {
/*  248 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  250 */     if (severity.equals("4"))
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  254 */     if (severity.equals("1"))
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  261 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  267 */     if (severity == null)
/*      */     {
/*  269 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  271 */     if (severity.equals("5"))
/*      */     {
/*  273 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  275 */     if (severity.equals("4"))
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("1"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  286 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  293 */     if (severity == null)
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  297 */     if (severity.equals("5"))
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  301 */     if (severity.equals("4"))
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  305 */     if (severity.equals("1"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  312 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  320 */     StringBuffer out = new StringBuffer();
/*  321 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  322 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  323 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  324 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  325 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  326 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  327 */     out.append("</tr>");
/*  328 */     out.append("</form></table>");
/*  329 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  336 */     if (val == null)
/*      */     {
/*  338 */       return "-";
/*      */     }
/*      */     
/*  341 */     String ret = FormatUtil.formatNumber(val);
/*  342 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  343 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  346 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  350 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  358 */     StringBuffer out = new StringBuffer();
/*  359 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  360 */     out.append("<tr>");
/*  361 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  363 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  365 */     out.append("</tr>");
/*  366 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  370 */       if (j % 2 == 0)
/*      */       {
/*  372 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  376 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  379 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  381 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  384 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  388 */       out.append("</tr>");
/*      */     }
/*  390 */     out.append("</table>");
/*  391 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  392 */     out.append("<tr>");
/*  393 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  394 */     out.append("</tr>");
/*  395 */     out.append("</table>");
/*  396 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  402 */     StringBuffer out = new StringBuffer();
/*  403 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  404 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  405 */     out.append("<tr>");
/*  406 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  407 */     out.append("<tr>");
/*  408 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  409 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  410 */     out.append("</tr>");
/*  411 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  414 */       out.append("<tr>");
/*  415 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  416 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  417 */       out.append("</tr>");
/*      */     }
/*      */     
/*  420 */     out.append("</table>");
/*  421 */     out.append("</table>");
/*  422 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  427 */     if (severity.equals("0"))
/*      */     {
/*  429 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  433 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  440 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  453 */     StringBuffer out = new StringBuffer();
/*  454 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  455 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  457 */       out.append("<tr>");
/*  458 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  459 */       out.append("</tr>");
/*      */       
/*      */ 
/*  462 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  464 */         String borderclass = "";
/*      */         
/*      */ 
/*  467 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  469 */         out.append("<tr>");
/*      */         
/*  471 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  472 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  473 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  479 */     out.append("</table><br>");
/*  480 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  481 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  483 */       List sLinks = secondLevelOfLinks[0];
/*  484 */       List sText = secondLevelOfLinks[1];
/*  485 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  488 */         out.append("<tr>");
/*  489 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  490 */         out.append("</tr>");
/*  491 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  493 */           String borderclass = "";
/*      */           
/*      */ 
/*  496 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  498 */           out.append("<tr>");
/*      */           
/*  500 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  501 */           if (sLinks.get(i).toString().length() == 0) {
/*  502 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  505 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  507 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  511 */     out.append("</table>");
/*  512 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  519 */     StringBuffer out = new StringBuffer();
/*  520 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  521 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  523 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  525 */         out.append("<tr>");
/*  526 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  527 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  531 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  533 */           String borderclass = "";
/*      */           
/*      */ 
/*  536 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  538 */           out.append("<tr>");
/*      */           
/*  540 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  541 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  542 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  545 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  548 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  553 */     out.append("</table><br>");
/*  554 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  555 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  557 */       List sLinks = secondLevelOfLinks[0];
/*  558 */       List sText = secondLevelOfLinks[1];
/*  559 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  562 */         out.append("<tr>");
/*  563 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  564 */         out.append("</tr>");
/*  565 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  567 */           String borderclass = "";
/*      */           
/*      */ 
/*  570 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  572 */           out.append("<tr>");
/*      */           
/*  574 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  575 */           if (sLinks.get(i).toString().length() == 0) {
/*  576 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  579 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  581 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  585 */     out.append("</table>");
/*  586 */     return out.toString();
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
/*  599 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  602 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  605 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  608 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  611 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  614 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  617 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  620 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  628 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  633 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  638 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  643 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  648 */     if (val != null)
/*      */     {
/*  650 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  654 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  659 */     if (val == null) {
/*  660 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  664 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  669 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  675 */     if (val != null)
/*      */     {
/*  677 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  681 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  687 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  692 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  696 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  701 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  706 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  711 */     String hostaddress = "";
/*  712 */     String ip = request.getHeader("x-forwarded-for");
/*  713 */     if (ip == null)
/*  714 */       ip = request.getRemoteAddr();
/*  715 */     InetAddress add = null;
/*  716 */     if (ip.equals("127.0.0.1")) {
/*  717 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  721 */       add = InetAddress.getByName(ip);
/*      */     }
/*  723 */     hostaddress = add.getHostName();
/*  724 */     if (hostaddress.indexOf('.') != -1) {
/*  725 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  726 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  730 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  735 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  741 */     if (severity == null)
/*      */     {
/*  743 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  745 */     if (severity.equals("5"))
/*      */     {
/*  747 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  749 */     if (severity.equals("1"))
/*      */     {
/*  751 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  756 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  761 */     ResultSet set = null;
/*  762 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  763 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  765 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  766 */       if (set.next()) { String str1;
/*  767 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  768 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  771 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  776 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  779 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  781 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  785 */     StringBuffer rca = new StringBuffer();
/*  786 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  787 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  790 */     int rcalength = key.length();
/*  791 */     String split = "6. ";
/*  792 */     int splitPresent = key.indexOf(split);
/*  793 */     String div1 = "";String div2 = "";
/*  794 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  796 */       if (rcalength > 180) {
/*  797 */         rca.append("<span class=\"rca-critical-text\">");
/*  798 */         getRCATrimmedText(key, rca);
/*  799 */         rca.append("</span>");
/*      */       } else {
/*  801 */         rca.append("<span class=\"rca-critical-text\">");
/*  802 */         rca.append(key);
/*  803 */         rca.append("</span>");
/*      */       }
/*  805 */       return rca.toString();
/*      */     }
/*  807 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  808 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  809 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  810 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  811 */     getRCATrimmedText(div1, rca);
/*  812 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  815 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  816 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  817 */     getRCATrimmedText(div2, rca);
/*  818 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  820 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  825 */     String[] st = msg.split("<br>");
/*  826 */     for (int i = 0; i < st.length; i++) {
/*  827 */       String s = st[i];
/*  828 */       if (s.length() > 180) {
/*  829 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  831 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  835 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  836 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  838 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  842 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  843 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  844 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  847 */       if (key == null) {
/*  848 */         return ret;
/*      */       }
/*      */       
/*  851 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  852 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  855 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  856 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  857 */       set = AMConnectionPool.executeQueryStmt(query);
/*  858 */       if (set.next())
/*      */       {
/*  860 */         String helpLink = set.getString("LINK");
/*  861 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  864 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  870 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  889 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  880 */         if (set != null) {
/*  881 */           AMConnectionPool.closeStatement(set);
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
/*  895 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  896 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  898 */       String entityStr = (String)keys.nextElement();
/*  899 */       String mmessage = temp.getProperty(entityStr);
/*  900 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  901 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  903 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  909 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  910 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  912 */       String entityStr = (String)keys.nextElement();
/*  913 */       String mmessage = temp.getProperty(entityStr);
/*  914 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  915 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  917 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  922 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  932 */     String des = new String();
/*  933 */     while (str.indexOf(find) != -1) {
/*  934 */       des = des + str.substring(0, str.indexOf(find));
/*  935 */       des = des + replace;
/*  936 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  938 */     des = des + str;
/*  939 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  946 */       if (alert == null)
/*      */       {
/*  948 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  950 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  952 */         return "&nbsp;";
/*      */       }
/*      */       
/*  955 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  957 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  960 */       int rcalength = test.length();
/*  961 */       if (rcalength < 300)
/*      */       {
/*  963 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  967 */       StringBuffer out = new StringBuffer();
/*  968 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  969 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  970 */       out.append("</div>");
/*  971 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  972 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  973 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  978 */       ex.printStackTrace();
/*      */     }
/*  980 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  986 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  991 */     ArrayList attribIDs = new ArrayList();
/*  992 */     ArrayList resIDs = new ArrayList();
/*  993 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  995 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  997 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  999 */       String resourceid = "";
/* 1000 */       String resourceType = "";
/* 1001 */       if (type == 2) {
/* 1002 */         resourceid = (String)row.get(0);
/* 1003 */         resourceType = (String)row.get(3);
/*      */       }
/* 1005 */       else if (type == 3) {
/* 1006 */         resourceid = (String)row.get(0);
/* 1007 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1010 */         resourceid = (String)row.get(6);
/* 1011 */         resourceType = (String)row.get(7);
/*      */       }
/* 1013 */       resIDs.add(resourceid);
/* 1014 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1015 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1017 */       String healthentity = null;
/* 1018 */       String availentity = null;
/* 1019 */       if (healthid != null) {
/* 1020 */         healthentity = resourceid + "_" + healthid;
/* 1021 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1024 */       if (availid != null) {
/* 1025 */         availentity = resourceid + "_" + availid;
/* 1026 */         entitylist.add(availentity);
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
/* 1040 */     Properties alert = getStatus(entitylist);
/* 1041 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1046 */     int size = monitorList.size();
/*      */     
/* 1048 */     String[] severity = new String[size];
/*      */     
/* 1050 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1052 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1053 */       String resourceName1 = (String)row1.get(7);
/* 1054 */       String resourceid1 = (String)row1.get(6);
/* 1055 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1056 */       if (severity[j] == null)
/*      */       {
/* 1058 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1062 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1064 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1066 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1069 */         if (sev > 0) {
/* 1070 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1071 */           monitorList.set(k, monitorList.get(j));
/* 1072 */           monitorList.set(j, t);
/* 1073 */           String temp = severity[k];
/* 1074 */           severity[k] = severity[j];
/* 1075 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1081 */     int z = 0;
/* 1082 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1085 */       int i = 0;
/* 1086 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1089 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1093 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1097 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1099 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1102 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1106 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1109 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1110 */       String resourceName1 = (String)row1.get(7);
/* 1111 */       String resourceid1 = (String)row1.get(6);
/* 1112 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1113 */       if (hseverity[j] == null)
/*      */       {
/* 1115 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1120 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1122 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1125 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1128 */         if (hsev > 0) {
/* 1129 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1130 */           monitorList.set(k, monitorList.get(j));
/* 1131 */           monitorList.set(j, t);
/* 1132 */           String temp1 = hseverity[k];
/* 1133 */           hseverity[k] = hseverity[j];
/* 1134 */           hseverity[j] = temp1;
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
/* 1146 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1147 */     boolean forInventory = false;
/* 1148 */     String trdisplay = "none";
/* 1149 */     String plusstyle = "inline";
/* 1150 */     String minusstyle = "none";
/* 1151 */     String haidTopLevel = "";
/* 1152 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1154 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1156 */         haidTopLevel = request.getParameter("haid");
/* 1157 */         forInventory = true;
/* 1158 */         trdisplay = "table-row;";
/* 1159 */         plusstyle = "none";
/* 1160 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1167 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1170 */     ArrayList listtoreturn = new ArrayList();
/* 1171 */     StringBuffer toreturn = new StringBuffer();
/* 1172 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1173 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1174 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1176 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1178 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1179 */       String childresid = (String)singlerow.get(0);
/* 1180 */       String childresname = (String)singlerow.get(1);
/* 1181 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1182 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1183 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1184 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1185 */       String unmanagestatus = (String)singlerow.get(5);
/* 1186 */       String actionstatus = (String)singlerow.get(6);
/* 1187 */       String linkclass = "monitorgp-links";
/* 1188 */       String titleforres = childresname;
/* 1189 */       String titilechildresname = childresname;
/* 1190 */       String childimg = "/images/trcont.png";
/* 1191 */       String flag = "enable";
/* 1192 */       String dcstarted = (String)singlerow.get(8);
/* 1193 */       String configMonitor = "";
/* 1194 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1195 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1197 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1199 */       if (singlerow.get(7) != null)
/*      */       {
/* 1201 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1203 */       String haiGroupType = "0";
/* 1204 */       if ("HAI".equals(childtype))
/*      */       {
/* 1206 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1208 */       childimg = "/images/trend.png";
/* 1209 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1210 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1211 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1213 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1215 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1217 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1218 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1221 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1223 */         linkclass = "disabledtext";
/* 1224 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1226 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1227 */       String availmouseover = "";
/* 1228 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1230 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1232 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1233 */       String healthmouseover = "";
/* 1234 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1236 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1239 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1240 */       int spacing = 0;
/* 1241 */       if (level >= 1)
/*      */       {
/* 1243 */         spacing = 40 * level;
/*      */       }
/* 1245 */       if (childtype.equals("HAI"))
/*      */       {
/* 1247 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1248 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1249 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1251 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1252 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1253 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1254 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1255 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1256 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1257 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1258 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1259 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1260 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1261 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1263 */         if (!forInventory)
/*      */         {
/* 1265 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1268 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1270 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1272 */           actions = editlink + actions;
/*      */         }
/* 1274 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1276 */           actions = actions + associatelink;
/*      */         }
/* 1278 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1279 */         String arrowimg = "";
/* 1280 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1282 */           actions = "";
/* 1283 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1284 */           checkbox = "";
/* 1285 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1287 */         if (isIt360)
/*      */         {
/* 1289 */           actionimg = "";
/* 1290 */           actions = "";
/* 1291 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1292 */           checkbox = "";
/*      */         }
/*      */         
/* 1295 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1297 */           actions = "";
/*      */         }
/* 1299 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1301 */           checkbox = "";
/*      */         }
/*      */         
/* 1304 */         String resourcelink = "";
/*      */         
/* 1306 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1308 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1312 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1315 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1321 */         if (!isIt360)
/*      */         {
/* 1323 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1327 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1330 */         toreturn.append("</tr>");
/* 1331 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1333 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1334 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1338 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1339 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1342 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1346 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1348 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1349 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1350 */             toreturn.append(assocMessage);
/* 1351 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1354 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1360 */         String resourcelink = null;
/* 1361 */         boolean hideEditLink = false;
/* 1362 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1364 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1365 */           hideEditLink = true;
/* 1366 */           if (isIt360)
/*      */           {
/* 1368 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1372 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1374 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1376 */           hideEditLink = true;
/* 1377 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1378 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1383 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1386 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1387 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1388 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1389 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1390 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1391 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1392 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1393 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1394 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1395 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1396 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1397 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1398 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1400 */         if (hideEditLink)
/*      */         {
/* 1402 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1404 */         if (!forInventory)
/*      */         {
/* 1406 */           removefromgroup = "";
/*      */         }
/* 1408 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1409 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1410 */           actions = actions + configcustomfields;
/*      */         }
/* 1412 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1414 */           actions = editlink + actions;
/*      */         }
/* 1416 */         String managedLink = "";
/* 1417 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1419 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1420 */           actions = "";
/* 1421 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1422 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1425 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1427 */           checkbox = "";
/*      */         }
/*      */         
/* 1430 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1432 */           actions = "";
/*      */         }
/* 1434 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1435 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1436 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1437 */         if (isIt360)
/*      */         {
/* 1439 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1443 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1447 */         if (!isIt360)
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1455 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1458 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1465 */       StringBuilder toreturn = new StringBuilder();
/* 1466 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1467 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1468 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1469 */       String title = "";
/* 1470 */       message = EnterpriseUtil.decodeString(message);
/* 1471 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1472 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1473 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1475 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1477 */       else if ("5".equals(severity))
/*      */       {
/* 1479 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1483 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1485 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1486 */       toreturn.append(v);
/*      */       
/* 1488 */       toreturn.append(link);
/* 1489 */       if (severity == null)
/*      */       {
/* 1491 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1493 */       else if (severity.equals("5"))
/*      */       {
/* 1495 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1497 */       else if (severity.equals("4"))
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1501 */       else if (severity.equals("1"))
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1510 */       toreturn.append("</a>");
/* 1511 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1515 */       ex.printStackTrace();
/*      */     }
/* 1517 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1524 */       StringBuilder toreturn = new StringBuilder();
/* 1525 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1526 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1527 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1528 */       if (message == null)
/*      */       {
/* 1530 */         message = "";
/*      */       }
/*      */       
/* 1533 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1534 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1536 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1537 */       toreturn.append(v);
/*      */       
/* 1539 */       toreturn.append(link);
/*      */       
/* 1541 */       if (severity == null)
/*      */       {
/* 1543 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1545 */       else if (severity.equals("5"))
/*      */       {
/* 1547 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1549 */       else if (severity.equals("1"))
/*      */       {
/* 1551 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1556 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1558 */       toreturn.append("</a>");
/* 1559 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1565 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1568 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1569 */     if (invokeActions != null) {
/* 1570 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1571 */       while (iterator.hasNext()) {
/* 1572 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1573 */         if (actionmap.containsKey(actionid)) {
/* 1574 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1579 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1583 */     String actionLink = "";
/* 1584 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1585 */     String query = "";
/* 1586 */     ResultSet rs = null;
/* 1587 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1588 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1589 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1590 */       actionLink = "method=" + methodName;
/*      */     }
/* 1592 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1593 */       actionLink = methodName;
/*      */     }
/* 1595 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1596 */     Iterator itr = methodarglist.iterator();
/* 1597 */     boolean isfirstparam = true;
/* 1598 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1599 */     while (itr.hasNext()) {
/* 1600 */       HashMap argmap = (HashMap)itr.next();
/* 1601 */       String argtype = (String)argmap.get("TYPE");
/* 1602 */       String argname = (String)argmap.get("IDENTITY");
/* 1603 */       String paramname = (String)argmap.get("PARAMETER");
/* 1604 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1605 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1606 */         isfirstparam = false;
/* 1607 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1609 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1613 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1617 */         actionLink = actionLink + "&";
/*      */       }
/* 1619 */       String paramValue = null;
/* 1620 */       String tempargname = argname;
/* 1621 */       if (commonValues.getProperty(tempargname) != null) {
/* 1622 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1625 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1626 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1627 */           if (dbType.equals("mysql")) {
/* 1628 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1631 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1633 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1635 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1636 */             if (rs.next()) {
/* 1637 */               paramValue = rs.getString("VALUE");
/* 1638 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1642 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1646 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1649 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1654 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1655 */           paramValue = rowId;
/*      */         }
/* 1657 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1658 */           paramValue = managedObjectName;
/*      */         }
/* 1660 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1661 */           paramValue = resID;
/*      */         }
/* 1663 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1664 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1667 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1669 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1670 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1671 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1673 */     return actionLink;
/*      */   }
/*      */   
/* 1676 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1677 */     String dependentAttribute = null;
/* 1678 */     String align = "left";
/*      */     
/* 1680 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1681 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1682 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1683 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1684 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1685 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1686 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1687 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1688 */       align = "center";
/*      */     }
/*      */     
/* 1691 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1692 */     String actualdata = "";
/*      */     
/* 1694 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1695 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1696 */         actualdata = availValue;
/*      */       }
/* 1698 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1699 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1703 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1704 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1707 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1713 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1714 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1715 */       toreturn.append("<table>");
/* 1716 */       toreturn.append("<tr>");
/* 1717 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1718 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1719 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1720 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1721 */         String toolTip = "";
/* 1722 */         String hideClass = "";
/* 1723 */         String textStyle = "";
/* 1724 */         boolean isreferenced = true;
/* 1725 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1726 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1727 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1728 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1730 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1731 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1732 */           while (valueList.hasMoreTokens()) {
/* 1733 */             String dependentVal = valueList.nextToken();
/* 1734 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1735 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1736 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1738 */               toolTip = "";
/* 1739 */               hideClass = "";
/* 1740 */               isreferenced = false;
/* 1741 */               textStyle = "disabledtext";
/* 1742 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1746 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1747 */           toolTip = "";
/* 1748 */           hideClass = "";
/* 1749 */           isreferenced = false;
/* 1750 */           textStyle = "disabledtext";
/* 1751 */           if (dependentImageMap != null) {
/* 1752 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1753 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1756 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1760 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1761 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1762 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1763 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1764 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1765 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1767 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1768 */           if (isreferenced) {
/* 1769 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1773 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1774 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1775 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1776 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1777 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1778 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1780 */           toreturn.append("</span>");
/* 1781 */           toreturn.append("</a>");
/* 1782 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1785 */       toreturn.append("</tr>");
/* 1786 */       toreturn.append("</table>");
/* 1787 */       toreturn.append("</td>");
/*      */     } else {
/* 1789 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1792 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1796 */     String colTime = null;
/* 1797 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1798 */     if ((rows != null) && (rows.size() > 0)) {
/* 1799 */       Iterator<String> itr = rows.iterator();
/* 1800 */       String maxColQuery = "";
/* 1801 */       for (;;) { if (itr.hasNext()) {
/* 1802 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1803 */           ResultSet maxCol = null;
/*      */           try {
/* 1805 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1806 */             while (maxCol.next()) {
/* 1807 */               if (colTime == null) {
/* 1808 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1811 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1820 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1822 */               if (maxCol != null)
/* 1823 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1825 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1820 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1822 */               if (maxCol != null)
/* 1823 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1825 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1830 */     return colTime;
/*      */   }
/*      */   
/* 1833 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1834 */     tablename = null;
/* 1835 */     ResultSet rsTable = null;
/* 1836 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1838 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1839 */       while (rsTable.next()) {
/* 1840 */         tablename = rsTable.getString("DATATABLE");
/* 1841 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1842 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1855 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1846 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1849 */         if (rsTable != null)
/* 1850 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1852 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1858 */     String argsList = "";
/* 1859 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1861 */       if (showArgsMap.get(row) != null) {
/* 1862 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1863 */         if (showArgslist != null) {
/* 1864 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1865 */             if (argsList.trim().equals("")) {
/* 1866 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1869 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1876 */       e.printStackTrace();
/* 1877 */       return "";
/*      */     }
/* 1879 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1884 */     String argsList = "";
/* 1885 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1888 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1890 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1891 */         if (hideArgsList != null)
/*      */         {
/* 1893 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1895 */             if (argsList.trim().equals(""))
/*      */             {
/* 1897 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1901 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1909 */       ex.printStackTrace();
/*      */     }
/* 1911 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1915 */     StringBuilder toreturn = new StringBuilder();
/* 1916 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1923 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1924 */       Iterator itr = tActionList.iterator();
/* 1925 */       while (itr.hasNext()) {
/* 1926 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1927 */         String confirmmsg = "";
/* 1928 */         String link = "";
/* 1929 */         String isJSP = "NO";
/* 1930 */         HashMap tactionMap = (HashMap)itr.next();
/* 1931 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1932 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1933 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1934 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1935 */           (actionmap.containsKey(actionId))) {
/* 1936 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1937 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1938 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1939 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1940 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1942 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1948 */           if (isTableAction) {
/* 1949 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1952 */             tableName = "Link";
/* 1953 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1954 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1955 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1956 */             toreturn.append("</a></td>");
/*      */           }
/* 1958 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1967 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1973 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1975 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1976 */       Properties prop = (Properties)node.getUserObject();
/* 1977 */       String mgID = prop.getProperty("label");
/* 1978 */       String mgName = prop.getProperty("value");
/* 1979 */       String isParent = prop.getProperty("isParent");
/* 1980 */       int mgIDint = Integer.parseInt(mgID);
/* 1981 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1983 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1985 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1986 */       if (node.getChildCount() > 0)
/*      */       {
/* 1988 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1990 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1992 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1994 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1998 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2003 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2005 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2007 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2009 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2013 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2016 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2017 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2019 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2023 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2025 */       if (node.getChildCount() > 0)
/*      */       {
/* 2027 */         builder.append("<UL>");
/* 2028 */         printMGTree(node, builder);
/* 2029 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2034 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2035 */     StringBuffer toReturn = new StringBuffer();
/* 2036 */     String table = "-";
/*      */     try {
/* 2038 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2039 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2040 */       float total = 0.0F;
/* 2041 */       while (it.hasNext()) {
/* 2042 */         String attName = (String)it.next();
/* 2043 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2044 */         boolean roundOffData = false;
/* 2045 */         if ((data != null) && (!data.equals(""))) {
/* 2046 */           if (data.indexOf(",") != -1) {
/* 2047 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2050 */             float value = Float.parseFloat(data);
/* 2051 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2054 */             total += value;
/* 2055 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2058 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2063 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2064 */       while (attVsWidthList.hasNext()) {
/* 2065 */         String attName = (String)attVsWidthList.next();
/* 2066 */         String data = (String)attVsWidthProps.get(attName);
/* 2067 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2068 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2069 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2070 */         String className = (String)graphDetails.get("ClassName");
/* 2071 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2072 */         if (percentage < 1.0F)
/*      */         {
/* 2074 */           data = percentage + "";
/*      */         }
/* 2076 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2078 */       if (toReturn.length() > 0) {
/* 2079 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2083 */       e.printStackTrace();
/*      */     }
/* 2085 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2091 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2092 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2093 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2094 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2095 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2096 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2097 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2098 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2099 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2102 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2103 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2104 */       splitvalues[0] = multiplecondition.toString();
/* 2105 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2108 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2113 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2114 */     if (thresholdType != 3) {
/* 2115 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2116 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2117 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2118 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2119 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2120 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2122 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2123 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2124 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2125 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2126 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2127 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2129 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2130 */     if (updateSelected != null) {
/* 2131 */       updateSelected[0] = "selected";
/*      */     }
/* 2133 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2138 */       StringBuffer toreturn = new StringBuffer("");
/* 2139 */       if (commaSeparatedMsgId != null) {
/* 2140 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2141 */         int count = 0;
/* 2142 */         while (msgids.hasMoreTokens()) {
/* 2143 */           String id = msgids.nextToken();
/* 2144 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2145 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2146 */           count++;
/* 2147 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2148 */             if (toreturn.length() == 0) {
/* 2149 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2151 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2152 */             if (!image.trim().equals("")) {
/* 2153 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2155 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2156 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2159 */         if (toreturn.length() > 0) {
/* 2160 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2164 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2167 */       e.printStackTrace(); }
/* 2168 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2174 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2180 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2181 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2194 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2198 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2199 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2200 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2201 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2202 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2203 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2204 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2208 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2209 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2210 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2211 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2219 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2222 */     JspWriter out = null;
/* 2223 */     Object page = this;
/* 2224 */     JspWriter _jspx_out = null;
/* 2225 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2229 */       response.setContentType("text/html;charset=UTF-8");
/* 2230 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2232 */       _jspx_page_context = pageContext;
/* 2233 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2234 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2235 */       session = pageContext.getSession();
/* 2236 */       out = pageContext.getOut();
/* 2237 */       _jspx_out = out;
/*      */       
/* 2239 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2240 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2241 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2242 */       if (wlsGraph == null) {
/* 2243 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2244 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2246 */       out.write(10);
/* 2247 */       AS400Graphs as400graph = null;
/* 2248 */       as400graph = (AS400Graphs)_jspx_page_context.getAttribute("as400graph", 1);
/* 2249 */       if (as400graph == null) {
/* 2250 */         as400graph = new AS400Graphs();
/* 2251 */         _jspx_page_context.setAttribute("as400graph", as400graph, 1);
/*      */       }
/* 2253 */       out.write(10);
/* 2254 */       PerformanceBean perfgraph = null;
/* 2255 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2256 */       if (perfgraph == null) {
/* 2257 */         perfgraph = new PerformanceBean();
/* 2258 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2260 */       out.write(10);
/* 2261 */       out.write(10);
/* 2262 */       out.write(10);
/* 2263 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2265 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2266 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2267 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2269 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2271 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2273 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2276 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2277 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2278 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2281 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2282 */         String available = null;
/* 2283 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2284 */         out.write(10);
/*      */         
/* 2286 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2287 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2288 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2290 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2292 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2294 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2297 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2298 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2299 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2302 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2303 */           String unavailable = null;
/* 2304 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2305 */           out.write(10);
/*      */           
/* 2307 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2308 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2309 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2311 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2313 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2315 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2318 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2319 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2320 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2323 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2324 */             String unmanaged = null;
/* 2325 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2326 */             out.write(10);
/*      */             
/* 2328 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2329 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2330 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2332 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2334 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2336 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2339 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2340 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2341 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2344 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2345 */               String scheduled = null;
/* 2346 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2347 */               out.write(10);
/*      */               
/* 2349 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2350 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2351 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2353 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2355 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2357 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2360 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2361 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2362 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2365 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2366 */                 String critical = null;
/* 2367 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2368 */                 out.write(10);
/*      */                 
/* 2370 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2371 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2372 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2374 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2376 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2378 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2381 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2382 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2383 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2386 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2387 */                   String clear = null;
/* 2388 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2389 */                   out.write(10);
/*      */                   
/* 2391 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2392 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2393 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2395 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2397 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2399 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2402 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2403 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2404 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2407 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2408 */                     String warning = null;
/* 2409 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2410 */                     out.write(10);
/* 2411 */                     out.write(10);
/*      */                     
/* 2413 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2414 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2416 */                     out.write(10);
/* 2417 */                     out.write(10);
/* 2418 */                     out.write(10);
/* 2419 */                     out.write(10);
/* 2420 */                     out.write(10);
/*      */                     
/*      */ 
/* 2423 */                     int aspr = 0;int dbr = 0;int prur = 0;int tmpar = 0;int perar = 0;int curpc = 0;int curip = 0;int shrpp = 0;int ucpup = 0;
/* 2424 */                     String resourceid = request.getParameter("resourceid");
/* 2425 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "&datatype=2");
/* 2426 */                     as400graph.setresid(Integer.parseInt(resourceid));
/* 2427 */                     String displayname = (String)request.getAttribute("displayname");
/*      */                     
/* 2429 */                     String ASP_PERCENTAGE = request.getAttribute("ASP_PERCENTAGE").toString();
/* 2430 */                     String ASP_TOTAL = (String)request.getAttribute("ASP_TOTAL");
/* 2431 */                     String DB_PERCENTAGE = request.getAttribute("DB_PERCENTAGE").toString();
/* 2432 */                     String PROCESSINGUNIT_PERCENTAGE = request.getAttribute("PROCESSINGUNIT_PERCENTAGE").toString();
/* 2433 */                     String PERMANENT_ADDRESS_PERCENTAGE = request.getAttribute("PERMANENT_ADDRESS_PERCENTAGE").toString();
/* 2434 */                     String TEMPORARY_ADDRESS_PERCENTAGE = request.getAttribute("TEMPORARY_ADDRESS_PERCENTAGE").toString();
/* 2435 */                     String CURRENT_UNPROTECTED_USED = (String)request.getAttribute("CURRENT_UNPROTECTED_USED");
/* 2436 */                     String MAXIMUM_UNPROTECTED = (String)request.getAttribute("MAXIMUM_UNPROTECTED");
/* 2437 */                     String MAIN_STORAGE = (String)request.getAttribute("MAIN_STORAGE");
/* 2438 */                     String NUMBER_OF_PROCESSORS = (String)request.getAttribute("NUMBER_OF_PROCESSORS");
/* 2439 */                     String NUMBER_OF_POOLS = (String)request.getAttribute("NUMBER_OF_POOLS");
/* 2440 */                     String USERS_SIGNED_ON = (String)request.getAttribute("USERS_SIGNED_ON");
/* 2441 */                     String TOTAL_JOBS = (String)request.getAttribute("TOTAL_JOBS");
/* 2442 */                     String NO_OF_ACTIVE_JOBS = (String)request.getAttribute("NO_OF_ACTIVE_JOBS");
/* 2443 */                     String NO_OF_BATCH_JOBS = (String)request.getAttribute("NO_OF_BATCH_JOBS");
/* 2444 */                     String JOBS_WAITING_FOR_MSG = (String)request.getAttribute("JOBS_WAITING_FOR_MSG");
/*      */                     
/*      */ 
/*      */ 
/* 2448 */                     String ACTIVE_THREADS = (String)request.getAttribute("ACTIVE_THREADS");
/* 2449 */                     String BATCHJOBENDENDWITHPRINTEROUTPUT = (String)request.getAttribute("BATCHJOBENDENDWITHPRINTEROUTPUT");
/* 2450 */                     String BATCHJOBSENDING = (String)request.getAttribute("BATCHJOBSENDING");
/* 2451 */                     String BATCHJOBHELDONJOBQUEUE = (String)request.getAttribute("BATCHJOBHELDONJOBQUEUE");
/* 2452 */                     String BATCHJOBSHELDWHILERUNNING = (String)request.getAttribute("BATCHJOBSHELDWHILERUNNING");
/*      */                     
/* 2454 */                     String BATCHJOBSONHELDJOBQUEUE = (String)request.getAttribute("BATCHJOBSONHELDJOBQUEUE");
/* 2455 */                     String BATCHJOBSONUNASSIGNEDJOBQUEUE = (String)request.getAttribute("BATCHJOBSONUNASSIGNEDJOBQUEUE");
/* 2456 */                     String BATCHJOBSWAITINGTORUN = (String)request.getAttribute("BATCHJOBSWAITINGTORUN");
/* 2457 */                     String NOOFPARTITIONS = (String)request.getAttribute("NOOFPARTITIONS");
/* 2458 */                     String TOTALAUXILZRYSTORAGE = (String)request.getAttribute("TOTALAUXILZRYSTORAGE");
/*      */                     
/*      */ 
/* 2461 */                     String USERSIGNOFFPRINTEROUTPUTWTOPRINT = (String)request.getAttribute("USERSIGNOFFPRINTEROUTPUTWTOPRINT");
/* 2462 */                     String USERSUSPENDEDBYGROUPJOB = (String)request.getAttribute("USERSUSPENDEDBYGROUPJOB");
/* 2463 */                     String USERSUSPENDEDBYSYSTEMREQUEST = (String)request.getAttribute("USERSUSPENDEDBYSYSTEMREQUEST");
/* 2464 */                     String USERSTEMPORARILYSOFF = (String)request.getAttribute("USERSTEMPORARILYSOFF");
/* 2465 */                     String CURRENTPROCESSINGCAPACITY = request.getAttribute("CURRENTPROCESSINGCAPACITY").toString();
/* 2466 */                     String CURRENTINTRACTIVEPERFORMANCEPER = request.getAttribute("CURRENTINTRACTIVEPERFORMANCEPER").toString();
/* 2467 */                     String SHAREDPROCESSINGPOOLPER = request.getAttribute("SHAREDPROCESSINGPOOLPER").toString();
/* 2468 */                     String UNCAPPEDCPUCAPACITYPER = request.getAttribute("UNCAPPEDCPUCAPACITYPER").toString();
/* 2469 */                     String MAXIMUMJOBSINSYSTEM = (String)request.getAttribute("MAXIMUMJOBSINSYSTEM");
/*      */                     
/* 2471 */                     aspr = ((Integer)request.getAttribute("aspr")).intValue();
/* 2472 */                     dbr = ((Integer)request.getAttribute("dbr")).intValue();
/* 2473 */                     prur = ((Integer)request.getAttribute("prur")).intValue();
/* 2474 */                     tmpar = ((Integer)request.getAttribute("tmpar")).intValue();
/* 2475 */                     perar = ((Integer)request.getAttribute("perar")).intValue();
/*      */                     
/*      */ 
/*      */ 
/* 2479 */                     curpc = ((Integer)request.getAttribute("curpc")).intValue();
/* 2480 */                     curip = ((Integer)request.getAttribute("curip")).intValue();
/* 2481 */                     shrpp = ((Integer)request.getAttribute("shrpp")).intValue();
/* 2482 */                     ucpup = ((Integer)request.getAttribute("ucpup")).intValue();
/*      */                     
/* 2484 */                     ArrayList resIDs = new ArrayList();
/* 2485 */                     resIDs.add(resourceid);
/*      */                     
/* 2487 */                     ArrayList<String> attribIDs = new ArrayList();
/* 2488 */                     for (int i = 2717; i < 2733; i++)
/*      */                     {
/* 2490 */                       attribIDs.add("" + i);
/*      */                     }
/*      */                     
/* 2493 */                     for (int i = 2801; i < 2819; i++)
/*      */                     {
/* 2495 */                       attribIDs.add("" + i);
/*      */                     }
/*      */                     
/* 2498 */                     attribIDs.add("711");
/*      */                     
/* 2500 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2505 */                     out.write("\n\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td width=\"49%\" valign=\"top\">\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\" onMouseOver=\"toggledivmo('div1',1)\" onMouseOut=\"toggledivmo('div1',0)\">\n        <tr>\n           <td colspan=\"2\" class=\"conf-mon-heading\">");
/* 2506 */                     out.print(FormatUtil.getString("am.webclient.as400.system"));
/* 2507 */                     out.write("</td>\n           <td  colspan=\"1\" align=\"right\" class=\"conf-mon-heading\">");
/*      */                     
/* 2509 */                     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2510 */                     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2511 */                     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                     
/* 2513 */                     _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2514 */                     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2515 */                     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2517 */                         out.write("<div style=\"visibility: hidden;\" id=\"div1\" ><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2518 */                         out.print(resourceid);
/* 2519 */                         out.write("&attributeIDs=2725,2726,2727,2809,2801&attributeToSelect=2725&redirectto=");
/* 2520 */                         out.print(encodeurl);
/* 2521 */                         out.write("\" > <img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" title=\"");
/* 2522 */                         out.print(ALERTCONFIG_TEXT);
/* 2523 */                         out.write("\"></a>&nbsp;</div>");
/* 2524 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2525 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2529 */                     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2530 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                     }
/*      */                     else {
/* 2533 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2534 */                       out.write("</td>\n        </tr>\n\n\t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t    <td width=\"60%\" class=\"monitorinfoodd\" align=\"left\">");
/* 2535 */                       out.print(FormatUtil.getString("am.webclient.as400.mainstorage"));
/* 2536 */                       out.write("</td>\n           ");
/* 2537 */                       if (MAIN_STORAGE != null) {
/* 2538 */                         out.write("\n\t    <td width=\"25%\" class=\"monitorinfoodd\">");
/* 2539 */                         out.print(FormatUtil.formatNumber(MAIN_STORAGE));
/* 2540 */                         out.write("</td>\n            ");
/*      */                       } else {
/* 2542 */                         out.write("\n\t    <td width=\"25%\" class=\"monitorinfoodd\">-</td>\n            ");
/*      */                       }
/* 2544 */                       out.write("\n\t    <td width=\"15%\" class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2545 */                       out.print(resourceid);
/* 2546 */                       out.write("&attributeid=2725')\">");
/* 2547 */                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2725")));
/* 2548 */                       out.write("</a></td>\n\t  </tr>\n\t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 2549 */                       out.print(FormatUtil.getString("am.webclient.as400.numberofprocessors"));
/* 2550 */                       out.write("</td>\n           ");
/* 2551 */                       if (NUMBER_OF_PROCESSORS != null) {
/* 2552 */                         out.write("\n\t    <td class=\"monitorinfoodd\">");
/* 2553 */                         out.print(FormatUtil.formatNumber(NUMBER_OF_PROCESSORS));
/* 2554 */                         out.write("</td>\n            ");
/*      */                       } else {
/* 2556 */                         out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n            ");
/*      */                       }
/* 2558 */                       out.write("\n\t    <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2559 */                       out.print(resourceid);
/* 2560 */                       out.write("&attributeid=2726')\">");
/* 2561 */                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2726")));
/* 2562 */                       out.write("</a></td>\n\t  </tr>\n\t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 2563 */                       out.print(FormatUtil.getString("am.webclient.as400.numberofpools"));
/* 2564 */                       out.write("</td>\n           ");
/* 2565 */                       if (NUMBER_OF_POOLS != null) {
/* 2566 */                         out.write("\n\t   <td class=\"monitorinfoodd\">");
/* 2567 */                         out.print(FormatUtil.formatNumber(NUMBER_OF_POOLS));
/* 2568 */                         out.write("</td>\n            ");
/*      */                       } else {
/* 2570 */                         out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n            ");
/*      */                       }
/* 2572 */                       out.write("\n\t    <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2573 */                       out.print(resourceid);
/* 2574 */                       out.write("&attributeid=2727')\">");
/* 2575 */                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2727")));
/* 2576 */                       out.write("</a></td>\n\t  </tr>\n\t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 2577 */                       out.print(FormatUtil.getString("am.webclient.as400.NumberOfPartitions"));
/* 2578 */                       out.write("</td>\n           ");
/* 2579 */                       if (NOOFPARTITIONS != null) {
/* 2580 */                         out.write("\n\t    <td class=\"monitorinfoodd\">");
/* 2581 */                         out.print(FormatUtil.formatNumber(NOOFPARTITIONS));
/* 2582 */                         out.write("</td>\n            ");
/*      */                       } else {
/* 2584 */                         out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n            ");
/*      */                       }
/* 2586 */                       out.write("\n\t    <td class=\"monitorinfoodd \" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2587 */                       out.print(resourceid);
/* 2588 */                       out.write("&attributeid=2809')\">");
/* 2589 */                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2809")));
/* 2590 */                       out.write("</a></td>\n\t  </tr>\n\t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t    <td class=\"monitorinfoodd-noborder\" align=\"left\">");
/* 2591 */                       out.print(FormatUtil.getString("am.webclient.as400.ActiveThreadsInSystem"));
/* 2592 */                       out.write("</td>\n           ");
/* 2593 */                       if (ACTIVE_THREADS != null) {
/* 2594 */                         out.write("\n\t    <td class=\"monitorinfoodd-noborder\">");
/* 2595 */                         out.print(FormatUtil.formatNumber(ACTIVE_THREADS));
/* 2596 */                         out.write("</td>\n            ");
/*      */                       } else {
/* 2598 */                         out.write("\n\t    <td class=\"monitorinfoodd-noborder\">-</td>\n            ");
/*      */                       }
/* 2600 */                       out.write("\n            <td class=\"monitorinfoodd-noborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2601 */                       out.print(resourceid);
/* 2602 */                       out.write("&attributeid=2801')\">");
/* 2603 */                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2801")));
/* 2604 */                       out.write("</a></td>\n\t  </tr>\n\n      </table>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\" onMouseOver=\"toggledivmo('div2',1)\" onMouseOut=\"toggledivmo('div2',0)\">\n        <tr>\n           <td  colspan=\"2\" class=\"conf-mon-heading\">");
/* 2605 */                       out.print(FormatUtil.getString("am.webclient.as400.auxiliarystorage"));
/* 2606 */                       out.write("</td>\n           <td  colspan=\"1\" align=\"right\" class=\"conf-mon-heading\"><div style=\"visibility: hidden;\" id=\"div2\" >");
/*      */                       
/* 2608 */                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2609 */                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2610 */                       _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                       
/* 2612 */                       _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2613 */                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2614 */                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                         for (;;) {
/* 2616 */                           out.write("<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2617 */                           out.print(resourceid);
/* 2618 */                           out.write("&attributeIDs=2718,2723,2724&attributeToSelect=2718&redirectto=");
/* 2619 */                           out.print(encodeurl);
/* 2620 */                           out.write("\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" title=\"");
/* 2621 */                           out.print(ALERTCONFIG_TEXT);
/* 2622 */                           out.write("\"></a>&nbsp; &nbsp;</div>");
/* 2623 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2624 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2628 */                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2629 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                       }
/*      */                       else {
/* 2632 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2633 */                         out.write("</td>\n        </tr>\n\n\t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t    <td width=\"60%\" class=\"monitorinfoodd\">");
/* 2634 */                         out.print(FormatUtil.getString("am.webclient.as400.asptotal"));
/* 2635 */                         out.write("</td>\n            ");
/* 2636 */                         if (ASP_TOTAL != null) {
/* 2637 */                           out.write("\n\t    <td width=\"25%\" class=\"monitorinfoodd\">");
/* 2638 */                           out.print(FormatUtil.formatNumber(ASP_TOTAL));
/* 2639 */                           out.write("</td>\n\t    ");
/*      */                         } else {
/* 2641 */                           out.write("\n\t    <td width=\"25%\" class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                         }
/* 2643 */                         out.write("\n\t    <td width=\"15%\" class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2644 */                         out.print(resourceid);
/* 2645 */                         out.write("&attributeid=2718')\">");
/* 2646 */                         out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2718")));
/* 2647 */                         out.write("</a></td>\n\t  </tr>\n\t   <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t    <td class=\"monitorinfoodd\">");
/* 2648 */                         out.print(FormatUtil.getString("am.webclient.as400.currentunprotectedused"));
/* 2649 */                         out.write("</td>\n            ");
/* 2650 */                         if (CURRENT_UNPROTECTED_USED != null) {
/* 2651 */                           out.write("\n\t    <td class=\"monitorinfoodd\">");
/* 2652 */                           out.print(FormatUtil.formatNumber(CURRENT_UNPROTECTED_USED));
/* 2653 */                           out.write("</td>\n\t    ");
/*      */                         } else {
/* 2655 */                           out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                         }
/* 2657 */                         out.write("\n\t    <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2658 */                         out.print(resourceid);
/* 2659 */                         out.write("&attributeid=2723')\">");
/* 2660 */                         out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2723")));
/* 2661 */                         out.write("</a></td>\n\t  </tr>\n\t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t    <td class=\"monitorinfoodd-noborder\">");
/* 2662 */                         out.print(FormatUtil.getString("am.webclient.as400.maximumunprotected"));
/* 2663 */                         out.write("</td>\n            ");
/* 2664 */                         if (MAXIMUM_UNPROTECTED != null) {
/* 2665 */                           out.write("\n\t    <td class=\"monitorinfoodd-noborder\">");
/* 2666 */                           out.print(FormatUtil.formatNumber(MAXIMUM_UNPROTECTED));
/* 2667 */                           out.write("</td>\n\t    ");
/*      */                         } else {
/* 2669 */                           out.write("\n\t    <td class=\"monitorinfoodd-noborder\">-</td>\n\t    ");
/*      */                         }
/* 2671 */                         out.write("\n\t   <td class=\"monitorinfoodd-noborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2672 */                         out.print(resourceid);
/* 2673 */                         out.write("&attributeid=2724')\">");
/* 2674 */                         out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2724")));
/* 2675 */                         out.write("</a></td>\n\t  </tr>\n\n        </table>\n\n        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"conf-mon-table\" onMouseOver=\"toggledivmo('div3',1)\" onMouseOut=\"toggledivmo('div3',0)\">\n            <tr>\n                 <td colspan=\"2\" class=\"conf-mon-heading\">");
/* 2676 */                         out.print(FormatUtil.getString("am.webclient.as400.jobs"));
/* 2677 */                         out.write("</td>\n                 <td  colspan=\"1\" align=\"right\" class=\"conf-mon-heading\"><div style=\"visibility: hidden;\" id=\"div3\" >");
/*      */                         
/* 2679 */                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2680 */                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2681 */                         _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */                         
/* 2683 */                         _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2684 */                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2685 */                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                           for (;;) {
/* 2687 */                             out.write("<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2688 */                             out.print(resourceid);
/* 2689 */                             out.write("&attributeIDs=2729,2730,2731,2732,2818&attributeToSelect=2729&redirectto=");
/* 2690 */                             out.print(encodeurl);
/* 2691 */                             out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" title=\"");
/* 2692 */                             out.print(ALERTCONFIG_TEXT);
/* 2693 */                             out.write("\"></a> &nbsp; &nbsp;</div>");
/* 2694 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2695 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2699 */                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2700 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*      */                         }
/*      */                         else {
/* 2703 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2704 */                           out.write("</td>\n           </tr>\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t\t            \t<td width=\"60%\" class=\"monitorinfoodd\" align=\"left\">");
/* 2705 */                           out.print(FormatUtil.getString("am.webclient.as400.totaljobs"));
/* 2706 */                           out.write("</td>\n           \t\t\t");
/* 2707 */                           if (TOTAL_JOBS != null) {
/* 2708 */                             out.write("\n\t\t            \t<td width=\"25%\" class=\"monitorinfoodd\">");
/* 2709 */                             out.print(FormatUtil.formatNumber(TOTAL_JOBS));
/* 2710 */                             out.write("</td>\n\t\t\t        ");
/*      */                           } else {
/* 2712 */                             out.write("\n\t\t\t        <td width=\"25%\" class=\"monitorinfoodd\">-</td>\n\t\t\t        ");
/*      */                           }
/* 2714 */                           out.write("\n\t\t            \t<td width=\"15%\" class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2715 */                           out.print(resourceid);
/* 2716 */                           out.write("&attributeid=2729')\">");
/* 2717 */                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2729")));
/* 2718 */                           out.write("</a></td>\n\t\t            \t</tr>\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t\t            \t<td class=\"monitorinfoodd\" align=\"left\">");
/* 2719 */                           out.print(FormatUtil.getString("am.webclient.as400.noofactivejobs"));
/* 2720 */                           out.write("</td>\n           \t\t\t");
/* 2721 */                           if (NO_OF_ACTIVE_JOBS != null) {
/* 2722 */                             out.write("\n\t\t            \t<td class=\"monitorinfoodd\">");
/* 2723 */                             out.print(FormatUtil.formatNumber(NO_OF_ACTIVE_JOBS));
/* 2724 */                             out.write("</td>\n\t\t\t        ");
/*      */                           } else {
/* 2726 */                             out.write("\n\t\t\t        <td class=\"monitorinfoodd\">-</td>\n\t\t\t        ");
/*      */                           }
/* 2728 */                           out.write("\n\t\t            \t<td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2729 */                           out.print(resourceid);
/* 2730 */                           out.write("&attributeid=2730')\">");
/* 2731 */                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2730")));
/* 2732 */                           out.write("</a></td>\n\t\t            \t</tr>\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t\t\t        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2733 */                           out.print(FormatUtil.getString("am.webclient.as400.noofbatchjobs"));
/* 2734 */                           out.write("</td>\n           \t\t\t");
/* 2735 */                           if (NO_OF_BATCH_JOBS != null) {
/* 2736 */                             out.write("\n\t\t            \t<td class=\"monitorinfoodd\">");
/* 2737 */                             out.print(FormatUtil.formatNumber(NO_OF_BATCH_JOBS));
/* 2738 */                             out.write("</td>\n\t\t\t        ");
/*      */                           } else {
/* 2740 */                             out.write("\n\t\t\t        <td class=\"monitorinfoodd\">-</td>\n\t\t\t        ");
/*      */                           }
/* 2742 */                           out.write("\n\t\t            \t<td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2743 */                           out.print(resourceid);
/* 2744 */                           out.write("&attributeid=2731')\">");
/* 2745 */                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2731")));
/* 2746 */                           out.write("</a></td>\n\t\t            \t</tr>\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t\t            \t<td class=\"monitorinfoodd\" align=\"left\">");
/* 2747 */                           out.print(FormatUtil.getString("am.webclient.as400.jobswaitingformessage"));
/* 2748 */                           out.write("</td>\n           \t\t\t");
/* 2749 */                           if (JOBS_WAITING_FOR_MSG != null) {
/* 2750 */                             out.write("\n\t\t            \t<td class=\"monitorinfoodd\">");
/* 2751 */                             out.print(FormatUtil.formatNumber(JOBS_WAITING_FOR_MSG));
/* 2752 */                             out.write("</td>\n\t\t\t        ");
/*      */                           } else {
/* 2754 */                             out.write("\n\t\t\t        <td class=\"monitorinfoodd\">-</td>\n\t\t\t        ");
/*      */                           }
/* 2756 */                           out.write("\n\t\t            \t<td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2757 */                           out.print(resourceid);
/* 2758 */                           out.write("&attributeid=2732')\">");
/* 2759 */                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2732")));
/* 2760 */                           out.write("</a></td>\n\t\t            \t</tr>\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t\t            \t<td class=\"monitorinfoodd-noborder\" align=\"left\">");
/* 2761 */                           out.print(FormatUtil.getString("am.webclient.as400.MaximumJobsInSystem"));
/* 2762 */                           out.write("</td>\n           \t\t\t");
/* 2763 */                           if (MAXIMUMJOBSINSYSTEM != null) {
/* 2764 */                             out.write("\n\t\t            \t<td class=\"monitorinfoodd-noborder\">");
/* 2765 */                             out.print(FormatUtil.formatNumber(MAXIMUMJOBSINSYSTEM));
/* 2766 */                             out.write("</td>\n\t\t\t        ");
/*      */                           } else {
/* 2768 */                             out.write("\n\t\t\t        <td class=\"monitorinfoodd-noborder\">-</td>\n\t\t\t        ");
/*      */                           }
/* 2770 */                           out.write("\n\t\t            \t<td class=\"monitorinfoodd-noborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2771 */                           out.print(resourceid);
/* 2772 */                           out.write("&attributeid=2818')\">");
/* 2773 */                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2818")));
/* 2774 */                           out.write("</a></td>\n                                </tr>\n\n\n       </table>\n\n           <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"conf-mon-table\" onMouseOver=\"toggledivmo('div4',1)\" onMouseOut=\"toggledivmo('div4',0)\">\n\n               <tr>\n                <td colspan=\"2\" class=\"conf-mon-heading\">");
/* 2775 */                           out.print(FormatUtil.getString("am.webclient.as400.batchjobs"));
/* 2776 */                           out.write("</td>\n                <td  colspan=\"1\" align=\"right\" class=\"conf-mon-heading\"><div style=\"visibility:hidden;\" id=\"div4\"> ");
/*      */                           
/* 2778 */                           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2779 */                           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2780 */                           _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */                           
/* 2782 */                           _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 2783 */                           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2784 */                           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                             for (;;) {
/* 2786 */                               out.write("<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2787 */                               out.print(resourceid);
/* 2788 */                               out.write("&attributeIDs=2802,2803,2804,2805,2806,2807,2808&attributeToSelect=2802&redirectto=");
/* 2789 */                               out.print(encodeurl);
/* 2790 */                               out.write("\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" title=\"");
/* 2791 */                               out.print(ALERTCONFIG_TEXT);
/* 2792 */                               out.write("\"></a>&nbsp;&nbsp;</div>");
/* 2793 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2794 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2798 */                           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2799 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */                           }
/*      */                           else {
/* 2802 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2803 */                             out.write("</td>\n                </tr>\n\n\n\t\t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t    <td width=\"60%\" class=\"monitorinfoodd\" align=\"left\">");
/* 2804 */                             out.print(FormatUtil.getString("am.webclient.as400.BatchJobsEndedWithPrinterOutputWaitingToPrint"));
/* 2805 */                             out.write("</td>\n            ");
/* 2806 */                             if (BATCHJOBENDENDWITHPRINTEROUTPUT != null) {
/* 2807 */                               out.write("\n\t    <td width=\"25%\" class=\"monitorinfoodd\">");
/* 2808 */                               out.print(FormatUtil.formatNumber(BATCHJOBENDENDWITHPRINTEROUTPUT));
/* 2809 */                               out.write("</td>\n\t    ");
/*      */                             } else {
/* 2811 */                               out.write("\n\t    <td width=\"25%\" class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                             }
/* 2813 */                             out.write("\n\n\t    <td width=\"15%\" class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2814 */                             out.print(resourceid);
/* 2815 */                             out.write("&attributeid=2802')\">");
/* 2816 */                             out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2802")));
/* 2817 */                             out.write("</a></td>\n\n\t\t            \t</tr>\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n\t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 2818 */                             out.print(FormatUtil.getString("am.webclient.as400.BatchJobsEnding"));
/* 2819 */                             out.write("</td>\n            ");
/* 2820 */                             if (BATCHJOBSENDING != null) {
/* 2821 */                               out.write("\n\t    <td class=\"monitorinfoodd\">");
/* 2822 */                               out.print(FormatUtil.formatNumber(BATCHJOBSENDING));
/* 2823 */                               out.write("</td>\n\t    ");
/*      */                             } else {
/* 2825 */                               out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                             }
/* 2827 */                             out.write("\n\n\t    <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2828 */                             out.print(resourceid);
/* 2829 */                             out.write("&attributeid=2803')\">");
/* 2830 */                             out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2803")));
/* 2831 */                             out.write("</a></td>\n\n\t\t            \t</tr>\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n\t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 2832 */                             out.print(FormatUtil.getString("am.webclient.as400.BatchJobsHeldOnJobQueue"));
/* 2833 */                             out.write("</td>\n            ");
/* 2834 */                             if (BATCHJOBHELDONJOBQUEUE != null) {
/* 2835 */                               out.write("\n\t    <td class=\"monitorinfoodd\">");
/* 2836 */                               out.print(FormatUtil.formatNumber(BATCHJOBHELDONJOBQUEUE));
/* 2837 */                               out.write("</td>\n\t    ");
/*      */                             } else {
/* 2839 */                               out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                             }
/* 2841 */                             out.write("\n\t    <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2842 */                             out.print(resourceid);
/* 2843 */                             out.write("&attributeid=2804')\">");
/* 2844 */                             out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2804")));
/* 2845 */                             out.write("</a></td>\n\n\t\t            \t</tr>\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n\t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 2846 */                             out.print(FormatUtil.getString("am.webclient.as400.BatchJobsHeldWhileRunning"));
/* 2847 */                             out.write("</td>\n            ");
/* 2848 */                             if (BATCHJOBSHELDWHILERUNNING != null) {
/* 2849 */                               out.write("\n\t    <td class=\"monitorinfoodd\">");
/* 2850 */                               out.print(FormatUtil.formatNumber(BATCHJOBSHELDWHILERUNNING));
/* 2851 */                               out.write("</td>\n\t    ");
/*      */                             } else {
/* 2853 */                               out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                             }
/* 2855 */                             out.write("\n\t    <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2856 */                             out.print(resourceid);
/* 2857 */                             out.write("&attributeid=2805')\">");
/* 2858 */                             out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2805")));
/* 2859 */                             out.write("</a></td>\n\n\t\t            \t</tr>\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n\t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 2860 */                             out.print(FormatUtil.getString("am.webclient.as400.BatchJobsOnAHeldJobQueue"));
/* 2861 */                             out.write("</td>\n            ");
/* 2862 */                             if (BATCHJOBSONHELDJOBQUEUE != null) {
/* 2863 */                               out.write("\n\t    <td class=\"monitorinfoodd\">");
/* 2864 */                               out.print(FormatUtil.formatNumber(BATCHJOBSONHELDJOBQUEUE));
/* 2865 */                               out.write("</td>\n\t    ");
/*      */                             } else {
/* 2867 */                               out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                             }
/* 2869 */                             out.write("\n\t    <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2870 */                             out.print(resourceid);
/* 2871 */                             out.write("&attributeid=2806')\">");
/* 2872 */                             out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2806")));
/* 2873 */                             out.write("</a></td>\n\n\t\t            \t</tr>\n\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n\t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 2874 */                             out.print(FormatUtil.getString("am.webclient.as400.BatchJobsOnUnassignedJobQueue"));
/* 2875 */                             out.write("</td>\n            ");
/* 2876 */                             if (BATCHJOBSONUNASSIGNEDJOBQUEUE != null) {
/* 2877 */                               out.write("\n\t    <td class=\"monitorinfoodd\">");
/* 2878 */                               out.print(FormatUtil.formatNumber(BATCHJOBSONUNASSIGNEDJOBQUEUE));
/* 2879 */                               out.write("</td>\n\t    ");
/*      */                             } else {
/* 2881 */                               out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                             }
/* 2883 */                             out.write("\n\t    <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2884 */                             out.print(resourceid);
/* 2885 */                             out.write("&attributeid=2807')\">");
/* 2886 */                             out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2807")));
/* 2887 */                             out.write("</a></td>\n\n\t\t            \t</tr>\n\n\t\t            \t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n\t    <td class=\"monitorinfoodd-noborder\" align=\"left\">");
/* 2888 */                             out.print(FormatUtil.getString("am.webclient.as400.BatchJobsWaitingToRunOrAlreadyScheduled"));
/* 2889 */                             out.write("</td>\n            ");
/* 2890 */                             if (BATCHJOBSWAITINGTORUN != null) {
/* 2891 */                               out.write("\n\t    <td class=\"monitorinfoodd-noborder\">");
/* 2892 */                               out.print(FormatUtil.formatNumber(BATCHJOBSWAITINGTORUN));
/* 2893 */                               out.write("</td>\n\t    ");
/*      */                             } else {
/* 2895 */                               out.write("\n\t    <td class=\"monitorinfoodd-noborder\">-</td>\n\t    ");
/*      */                             }
/* 2897 */                             out.write("\n\t    <td class=\"monitorinfoodd-noborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2898 */                             out.print(resourceid);
/* 2899 */                             out.write("&attributeid=2808')\">");
/* 2900 */                             out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2808")));
/* 2901 */                             out.write("</a></td>\n\n\t\t            \t</tr>\n\n\n       </table>\n\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"conf-mon-table\" onMouseOver=\"toggledivmo('div5',1)\" onMouseOut=\"toggledivmo('div5',0)\">\n\n                <tr>\n             <td colspan=\"2\" class=\"conf-mon-heading\">");
/* 2902 */                             out.print(FormatUtil.getString("am.webclient.as400.users"));
/* 2903 */                             out.write("</td>\n             <td  colspan=\"1\" align=\"right\" class=\"conf-mon-heading\"><div style=\"visibility:hidden;\" id=\"div5\">");
/*      */                             
/* 2905 */                             PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2906 */                             _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2907 */                             _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */                             
/* 2909 */                             _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 2910 */                             int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2911 */                             if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                               for (;;) {
/* 2913 */                                 out.write(" <a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2914 */                                 out.print(resourceid);
/* 2915 */                                 out.write("&attributeIDs=2728,2810,2811,2812,2813&attributeToSelect=2728&redirectto=");
/* 2916 */                                 out.print(encodeurl);
/* 2917 */                                 out.write("\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" title=\"");
/* 2918 */                                 out.print(ALERTCONFIG_TEXT);
/* 2919 */                                 out.write("\"></a>&nbsp;&nbsp;</div>");
/* 2920 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2921 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2925 */                             if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2926 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                             }
/*      */                             else {
/* 2929 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2930 */                               out.write("</td>\n            </tr>\n\n\t\t <tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n    \t    \t <td width=\"60%\" class=\"monitorinfoodd\" align=\"left\">");
/* 2931 */                               out.print(FormatUtil.getString("am.webclient.as400.userssignedon"));
/* 2932 */                               out.write("</td>\n                 ");
/* 2933 */                               if (USERS_SIGNED_ON != null) {
/* 2934 */                                 out.write("\n    \t    \t <td width=\"25%\" class=\"monitorinfoodd\">");
/* 2935 */                                 out.print(FormatUtil.formatNumber(USERS_SIGNED_ON));
/* 2936 */                                 out.write("</td>\n\t         ");
/*      */                               } else {
/* 2938 */                                 out.write("\n\t         <td width=\"25%\" class=\"monitorinfoodd\">-</td>\n\t         ");
/*      */                               }
/* 2940 */                               out.write("\n    \t    \t <td width=\"15%\" class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2941 */                               out.print(resourceid);
/* 2942 */                               out.write("&attributeid=2728')\">");
/* 2943 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2728")));
/* 2944 */                               out.write("</a></td>\n\n\n\t\t  </tr>\n\t\t <tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n    \t   \t <td class=\"monitorinfoodd\" align=\"left\">");
/* 2945 */                               out.print(FormatUtil.getString("am.webclient.as400.UsersSignedOffWithPrinterOutputWaitingToPrint"));
/* 2946 */                               out.write("</td>\n                 ");
/* 2947 */                               if (USERSIGNOFFPRINTEROUTPUTWTOPRINT != null) {
/* 2948 */                                 out.write("\n    \t   \t <td class=\"monitorinfoodd\">");
/* 2949 */                                 out.print(FormatUtil.formatNumber(USERSIGNOFFPRINTEROUTPUTWTOPRINT));
/* 2950 */                                 out.write("</td>\n\t         ");
/*      */                               } else {
/* 2952 */                                 out.write("\n\t         <td class=\"monitorinfoodd\">-</td>\n\t         ");
/*      */                               }
/* 2954 */                               out.write("\n    \t   \t <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2955 */                               out.print(resourceid);
/* 2956 */                               out.write("&attributeid=2810')\">");
/* 2957 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2810")));
/* 2958 */                               out.write("</a></td>\n\n\t\t </tr>\n\t\t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n    \t    \t<td class=\"monitorinfoodd\" align=\"left\">");
/* 2959 */                               out.print(FormatUtil.getString("am.webclient.as400.UsersSuspendedByGroupJobs"));
/* 2960 */                               out.write("</td>\n                 ");
/* 2961 */                               if (USERSUSPENDEDBYGROUPJOB != null) {
/* 2962 */                                 out.write("\n    \t    \t<td class=\"monitorinfoodd\">");
/* 2963 */                                 out.print(FormatUtil.formatNumber(USERSUSPENDEDBYGROUPJOB));
/* 2964 */                                 out.write("</td>\n\t         ");
/*      */                               } else {
/* 2966 */                                 out.write("\n\t         <td class=\"monitorinfoodd\">-</td>\n\t         ");
/*      */                               }
/* 2968 */                               out.write("\n    \t   \t <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2969 */                               out.print(resourceid);
/* 2970 */                               out.write("&attributeid=2811')\">");
/* 2971 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2811")));
/* 2972 */                               out.write("</a></td>\n\n\t\t</tr>\n\t\t<tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n    \t   \t<td class=\"monitorinfoodd\" align=\"left\">");
/* 2973 */                               out.print(FormatUtil.getString("am.webclient.as400.UsersSuspendedBySystemRequest"));
/* 2974 */                               out.write("</td>\n                 ");
/* 2975 */                               if (USERSUSPENDEDBYSYSTEMREQUEST != null) {
/* 2976 */                                 out.write("\n    \t   \t<td class=\"monitorinfoodd\">");
/* 2977 */                                 out.print(FormatUtil.formatNumber(USERSUSPENDEDBYSYSTEMREQUEST));
/* 2978 */                                 out.write("</td>\n\t         ");
/*      */                               } else {
/* 2980 */                                 out.write("\n\t         <td class=\"monitorinfoodd\">-</td>\n\t         ");
/*      */                               }
/* 2982 */                               out.write("\n    \t    \t<td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2983 */                               out.print(resourceid);
/* 2984 */                               out.write("&attributeid=2812')\">");
/* 2985 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2812")));
/* 2986 */                               out.write("</a></td>\n\n\t\t</tr>\n\t\t <tr height=\"33\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\n    \t   \t<td class=\"monitorinfoodd-noborder\" align=\"left\">");
/* 2987 */                               out.print(FormatUtil.getString("am.webclient.as400.UsersTemporarilySignedOff"));
/* 2988 */                               out.write("</td>\n                 ");
/* 2989 */                               if (USERSTEMPORARILYSOFF != null) {
/* 2990 */                                 out.write("\n    \t    \t<td class=\"monitorinfoodd-noborder\">");
/* 2991 */                                 out.print(FormatUtil.formatNumber(USERSTEMPORARILYSOFF));
/* 2992 */                                 out.write("</td>\n\t         ");
/*      */                               } else {
/* 2994 */                                 out.write("\n\t         <td class=\"monitorinfoodd-noborder\">-</td>\n\t         ");
/*      */                               }
/* 2996 */                               out.write("\n    \t    \t<td class=\"monitorinfoodd-noborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2997 */                               out.print(resourceid);
/* 2998 */                               out.write("&attributeid=2813')\">");
/* 2999 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2813")));
/* 3000 */                               out.write("</a></td>\n\n\t\t</tr>\n\n       </table>\n\n</td>\n\n\n\n<td width=\"1%\">&nbsp;</td>\n\n\n\n<td width=\"50%\" valign=\"top\">\n\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\" onMouseOver=\"toggledivmo('div6',1)\" onMouseOut=\"toggledivmo('div6',0)\">\n        <tr>\n           <td  colspan=\"3\" class=\"conf-mon-heading\">");
/* 3001 */                               out.print(FormatUtil.getString("am.webclient.as400.systemstatus"));
/* 3002 */                               out.write("</td>\n           <td  colspan=\"1\" align=\"right\" class=\"conf-mon-heading\"><div style=\"visibility:hidden;\" id=\"div6\"> ");
/*      */                               
/* 3004 */                               PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3005 */                               _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3006 */                               _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */                               
/* 3008 */                               _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3009 */                               int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3010 */                               if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                 for (;;) {
/* 3012 */                                   out.write("<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3013 */                                   out.print(resourceid);
/* 3014 */                                   out.write("&attributeIDs=711,2720,2721,2722,2717&attributeToSelect=2720&redirectto=");
/* 3015 */                                   out.print(encodeurl);
/* 3016 */                                   out.write("\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" title=\"");
/* 3017 */                                   out.print(ALERTCONFIG_TEXT);
/* 3018 */                                   out.write("\"></a>&nbsp;&nbsp;</div>");
/* 3019 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3020 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3024 */                               if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3025 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*      */                               }
/*      */                               else {
/* 3028 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3029 */                                 out.write("</td>\n        </tr>\n\n\t<tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t   <td width=\"50%\" class=\"monitorinfoodd\" align=\"left\">");
/* 3030 */                                 out.print(FormatUtil.getString("am.webclient.as400.asppercentage"));
/* 3031 */                                 out.write("</td>\n           ");
/* 3032 */                                 if (ASP_PERCENTAGE != null) {
/* 3033 */                                   out.write("\n\t   <td width=\"15%\" class=\"monitorinfoodd\">");
/* 3034 */                                   out.print(ASP_PERCENTAGE);
/* 3035 */                                   out.write("</td>\n            ");
/*      */                                 } else {
/* 3037 */                                   out.write("\n\t    <td width=\"15%\" class=\"monitorinfoodd\">-</td>\n            ");
/*      */                                 }
/* 3039 */                                 out.write("\n\t   <td width=\"15%\" class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3040 */                                 out.print(resourceid);
/* 3041 */                                 out.write("&attributeid=2717')\">");
/* 3042 */                                 out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2717")));
/* 3043 */                                 out.write("</a></td>\n           <td width=\"20%\" class=\"monitorinfoodd\" align=\"center\">\n            <table align=\"left\" width =\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #a8a8a8;\">\n\t    <tr>\n\n\t      <td class=\"");
/* 3044 */                                 if (!ASP_PERCENTAGE.equals("0")) {
/* 3045 */                                   out.write("availabilitybar ");
/*      */                                 }
/* 3047 */                                 out.write("\" width=\"");
/* 3048 */                                 out.print(ASP_PERCENTAGE);
/* 3049 */                                 out.write("%\" title=\"");
/* 3050 */                                 out.print(ASP_PERCENTAGE);
/* 3051 */                                 out.write("%\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n\t     <td  class=\"\" width=\"");
/* 3052 */                                 out.print(aspr);
/* 3053 */                                 out.write("%\" title=\"");
/* 3054 */                                 out.print(aspr);
/* 3055 */                                 out.write("%\"> <img src=\"../images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\n\t    </tr>\n\t    </table>\n           </td>\n\n\t</tr>\n        <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n         <td class=\"monitorinfoodd\" align=\"left\">");
/* 3056 */                                 out.print(FormatUtil.getString("am.webclient.as400.diskutil"));
/* 3057 */                                 out.write("</td>\n           ");
/* 3058 */                                 if (DB_PERCENTAGE != null) {
/* 3059 */                                   out.write("\n         <td class=\"monitorinfoodd\">");
/* 3060 */                                   out.print(DB_PERCENTAGE);
/* 3061 */                                   out.write("</td>\n            ");
/*      */                                 } else {
/* 3063 */                                   out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n            ");
/*      */                                 }
/* 3065 */                                 out.write("\n         <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3066 */                                 out.print(resourceid);
/* 3067 */                                 out.write("&attributeid=711')\">");
/* 3068 */                                 out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "711")));
/* 3069 */                                 out.write("</a></td>\n\t <td class=\"monitorinfoodd\" align=\"center\">\n\t    <table align=left width =\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #a8a8a8;\">\n\t     <tr>\n\t       <td class=\"");
/* 3070 */                                 if (!DB_PERCENTAGE.equals("0")) {
/* 3071 */                                   out.write("availabilitybar ");
/*      */                                 }
/* 3073 */                                 out.write("\" width=\"");
/* 3074 */                                 out.print(DB_PERCENTAGE);
/* 3075 */                                 out.write("%\" title=\"");
/* 3076 */                                 out.print(DB_PERCENTAGE);
/* 3077 */                                 out.write("%\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n\t       <td  class=\"\" width=\"");
/* 3078 */                                 out.print(dbr);
/* 3079 */                                 out.write("%\" title=\"");
/* 3080 */                                 out.print(dbr);
/* 3081 */                                 out.write("%\"> <img src=\"../images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t     </tr>\n\t   </table>\n\t<td>\n        </tr>\n        <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t  <td class=\"monitorinfoodd\" align=\"left\">");
/* 3082 */                                 out.print(FormatUtil.getString("am.webclient.as400.processingunitpercentage"));
/* 3083 */                                 out.write("</td>\n\n           ");
/* 3084 */                                 if (PROCESSINGUNIT_PERCENTAGE != null) {
/* 3085 */                                   out.write("\n\t  <td class=\"monitorinfoodd\">");
/* 3086 */                                   out.print(PROCESSINGUNIT_PERCENTAGE);
/* 3087 */                                   out.write("</td>\n            ");
/*      */                                 } else {
/* 3089 */                                   out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n            ");
/*      */                                 }
/* 3091 */                                 out.write("\n\t<td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3092 */                                 out.print(resourceid);
/* 3093 */                                 out.write("&attributeid=2720')\">");
/* 3094 */                                 out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2720")));
/* 3095 */                                 out.write("</a></td>\n\t  <td class=\"monitorinfoodd\" align=\"center\">\n\t     <table align=left width =\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #a8a8a8;\">\n\t      <tr>\n\t        <td class=\"");
/* 3096 */                                 if (!PROCESSINGUNIT_PERCENTAGE.equals("0")) {
/* 3097 */                                   out.write("availabilitybar ");
/*      */                                 }
/* 3099 */                                 out.write("\" width=\"");
/* 3100 */                                 out.print(PROCESSINGUNIT_PERCENTAGE);
/* 3101 */                                 out.write("%\" title=\"");
/* 3102 */                                 out.print(PROCESSINGUNIT_PERCENTAGE);
/* 3103 */                                 out.write("%\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n\t        <td  class=\"\" width=\"");
/* 3104 */                                 out.print(prur);
/* 3105 */                                 out.write("%\" title=\"");
/* 3106 */                                 out.print(prur);
/* 3107 */                                 out.write("%\"> <img src=\"../images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t      </tr>\n\t    </table>\n\t<td>\n\t</tr>\n\t<tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t<td class=\"monitorinfoodd\" align=\"left\">");
/* 3108 */                                 out.print(FormatUtil.getString("am.webclient.as400.permanentaddresspercentage"));
/* 3109 */                                 out.write("</td>\n        ");
/* 3110 */                                 if (PERMANENT_ADDRESS_PERCENTAGE != null) {
/* 3111 */                                   out.write("\n\t<td class=\"monitorinfoodd\">");
/* 3112 */                                   out.print(PERMANENT_ADDRESS_PERCENTAGE);
/* 3113 */                                   out.write("</td>\n            ");
/*      */                                 } else {
/* 3115 */                                   out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n            ");
/*      */                                 }
/* 3117 */                                 out.write("\n\t<td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3118 */                                 out.print(resourceid);
/* 3119 */                                 out.write("&attributeid=2721')\">");
/* 3120 */                                 out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2721")));
/* 3121 */                                 out.write("</a></td>\n\t<td class=\"monitorinfoodd\" align=\"center\">\n\t    <table align=left width =\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #a8a8a8;\">\n\t    <tr>\n\n\t      <td class=\"");
/* 3122 */                                 if (!PERMANENT_ADDRESS_PERCENTAGE.equals("0")) {
/* 3123 */                                   out.write("availabilitybar ");
/*      */                                 }
/* 3125 */                                 out.write("\" width=\"");
/* 3126 */                                 out.print(PERMANENT_ADDRESS_PERCENTAGE);
/* 3127 */                                 out.write("%\"  title=\"");
/* 3128 */                                 out.print(PERMANENT_ADDRESS_PERCENTAGE);
/* 3129 */                                 out.write("%\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n\t      <td  class=\"\" width=\"");
/* 3130 */                                 out.print(perar);
/* 3131 */                                 out.write("%\" title=\"");
/* 3132 */                                 out.print(perar);
/* 3133 */                                 out.write("%\"> <img src=\"../images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t    </tr>\n\t  </table>\n\t<td>\n\t</tr>\n\t<tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n\t<td class=\"monitorinfoodd-noborder\" align=\"left\">");
/* 3134 */                                 out.print(FormatUtil.getString("am.webclient.as400.tempraryaddresspercentage"));
/* 3135 */                                 out.write("</td>\n        ");
/* 3136 */                                 if (TEMPORARY_ADDRESS_PERCENTAGE != null) {
/* 3137 */                                   out.write("\n\t<td class=\"monitorinfoodd-noborder\">");
/* 3138 */                                   out.print(TEMPORARY_ADDRESS_PERCENTAGE);
/* 3139 */                                   out.write("</td>\n\t    ");
/*      */                                 } else {
/* 3141 */                                   out.write("\n\t    <td class=\"monitorinfoodd-noborder\">-</td>\n\t    ");
/*      */                                 }
/* 3143 */                                 out.write("\n\t<td class=\"monitorinfoodd-noborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3144 */                                 out.print(resourceid);
/* 3145 */                                 out.write("&attributeid=2722')\">");
/* 3146 */                                 out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2722")));
/* 3147 */                                 out.write("</a></td>\n\t<td class=\"monitorinfoodd-noborder\" align=\"center\">\n\t   <table align=left width =\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #a8a8a8;\">\n\t    <tr>\n\n\t      <td class=\"");
/* 3148 */                                 if (!TEMPORARY_ADDRESS_PERCENTAGE.equals("0")) {
/* 3149 */                                   out.write("availabilitybar ");
/*      */                                 }
/* 3151 */                                 out.write("\" width=\"");
/* 3152 */                                 out.print(TEMPORARY_ADDRESS_PERCENTAGE);
/* 3153 */                                 out.write("%\" title=\"");
/* 3154 */                                 out.print(TEMPORARY_ADDRESS_PERCENTAGE);
/* 3155 */                                 out.write("%\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n\t      <td  class=\"\" width=\"");
/* 3156 */                                 out.print(tmpar);
/* 3157 */                                 out.write("%\" title=\"");
/* 3158 */                                 out.print(tmpar);
/* 3159 */                                 out.write("%\"> <img src=\"../images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t    </tr>\n\t  </table>\n\t<td>\n\t</tr>\n\n      </table>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\" onMouseOver=\"toggledivmo('div7',1)\" onMouseOut=\"toggledivmo('div7',0)\">\n            <tr>\n               <td  colspan=\"3\" class=\"conf-mon-heading\">");
/* 3160 */                                 out.print(FormatUtil.getString("am.webclient.as400.Info"));
/* 3161 */                                 out.write("</td>\n               <td  colspan=\"1\" align=\"right\" class=\"conf-mon-heading\"><div style=\"visibility:hidden;\" id=\"div7\"> ");
/*      */                                 
/* 3163 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3164 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3165 */                                 _jspx_th_logic_005fpresent_005f6.setParent(null);
/*      */                                 
/* 3167 */                                 _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 3168 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3169 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;) {
/* 3171 */                                     out.write("<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3172 */                                     out.print(resourceid);
/* 3173 */                                     out.write("&attributeIDs=2814,2815,2816,2817&attributeToSelect=2814&redirectto=");
/* 3174 */                                     out.print(encodeurl);
/* 3175 */                                     out.write("\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" title=\"");
/* 3176 */                                     out.print(ALERTCONFIG_TEXT);
/* 3177 */                                     out.write("\"></a>&nbsp;&nbsp;</div>");
/* 3178 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3179 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3183 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3184 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*      */                                 }
/*      */                                 else {
/* 3187 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3188 */                                   out.write("</td>\n            </tr>\n\n    \t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n    \t    <td width=\"50%\" class=\"monitorinfoodd\" align=\"left\">");
/* 3189 */                                   out.print(FormatUtil.getString("am.webclient.as400.CurrentProcessingCapacity"));
/* 3190 */                                   out.write("</td>\n            ");
/* 3191 */                                   if (CURRENTPROCESSINGCAPACITY != null) {
/* 3192 */                                     out.write("\n    \t    <td width=\"15%\" class=\"monitorinfoodd\">");
/* 3193 */                                     out.print(CURRENTPROCESSINGCAPACITY);
/* 3194 */                                     out.write("</td>\n\t    ");
/*      */                                   } else {
/* 3196 */                                     out.write("\n\t    <td width=\"15%\" class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                                   }
/* 3198 */                                   out.write("\n    \t   <td width=\"15%\" class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3199 */                                   out.print(resourceid);
/* 3200 */                                   out.write("&attributeid=2814')\">");
/* 3201 */                                   out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2814")));
/* 3202 */                                   out.write("</a></td>\n    \t    \t<td width=\"20%\" class=\"monitorinfoodd\" align=\"center\">\n\t    \t    <table align=left width =\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #a8a8a8;\">\n\t    \t    <tr>\n\n\t    \t      <td class=\"");
/* 3203 */                                   if (!CURRENTPROCESSINGCAPACITY.equals("0")) {
/* 3204 */                                     out.write("availabilitybar ");
/*      */                                   }
/* 3206 */                                   out.write("\" width=\"");
/* 3207 */                                   out.print(CURRENTPROCESSINGCAPACITY);
/* 3208 */                                   out.write("%\" title=\"");
/* 3209 */                                   out.print(CURRENTPROCESSINGCAPACITY);
/* 3210 */                                   out.write("%\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n\t    \t      <td  class=\"\" width=\"");
/* 3211 */                                   out.print(curpc);
/* 3212 */                                   out.write("%\" title=\"");
/* 3213 */                                   out.print(curpc);
/* 3214 */                                   out.write("%\"> <img src=\"../images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t    \t    </tr>\n\t    \t  </table>\n\t    \t<td>\n    \t  </tr>\n    \t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n    \t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 3215 */                                   out.print(FormatUtil.getString("am.webclient.as400.PercentCurrentInteractivePerformance"));
/* 3216 */                                   out.write("</td>\n            ");
/* 3217 */                                   if (CURRENTINTRACTIVEPERFORMANCEPER != null) {
/* 3218 */                                     out.write("\n    \t    <td class=\"monitorinfoodd\">");
/* 3219 */                                     out.print(CURRENTINTRACTIVEPERFORMANCEPER);
/* 3220 */                                     out.write("</td>\n\t    ");
/*      */                                   } else {
/* 3222 */                                     out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                                   }
/* 3224 */                                   out.write("\n    \t   <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3225 */                                   out.print(resourceid);
/* 3226 */                                   out.write("&attributeid=2815')\">");
/* 3227 */                                   out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2815")));
/* 3228 */                                   out.write("</a></td>\n    \t    \t<td class=\"monitorinfoodd\" align=\"center\">\n\t    \t    <table align=left width =\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #a8a8a8;\">\n\t    \t    <tr>\n\n\t    \t      <td class=\"");
/* 3229 */                                   if (!CURRENTINTRACTIVEPERFORMANCEPER.equals("0")) {
/* 3230 */                                     out.write("availabilitybar ");
/*      */                                   }
/* 3232 */                                   out.write("\" width=\"");
/* 3233 */                                   out.print(CURRENTINTRACTIVEPERFORMANCEPER);
/* 3234 */                                   out.write("%\" title=\"");
/* 3235 */                                   out.print(CURRENTINTRACTIVEPERFORMANCEPER);
/* 3236 */                                   out.write("%\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n\t    \t      <td  class=\"\" width=\"");
/* 3237 */                                   out.print(curip);
/* 3238 */                                   out.write("%\" title=\"");
/* 3239 */                                   out.print(curip);
/* 3240 */                                   out.write("%\"> <img src=\"../images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t    \t    </tr>\n\t    \t  </table>\n\t    \t<td>\n\n    \t  </tr>\n    \t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n    \t    <td class=\"monitorinfoodd\" align=\"left\">");
/* 3241 */                                   out.print(FormatUtil.getString("am.webclient.as400.PercentSharedProcessorPoolUsed"));
/* 3242 */                                   out.write("</td>\n            ");
/* 3243 */                                   if (SHAREDPROCESSINGPOOLPER != null) {
/* 3244 */                                     out.write("\n    \t    <td class=\"monitorinfoodd\">");
/* 3245 */                                     out.print(SHAREDPROCESSINGPOOLPER);
/* 3246 */                                     out.write("</td>\n\t    ");
/*      */                                   } else {
/* 3248 */                                     out.write("\n\t    <td class=\"monitorinfoodd\">-</td>\n\t    ");
/*      */                                   }
/* 3250 */                                   out.write("\n    \t   <td class=\"monitorinfoodd\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3251 */                                   out.print(resourceid);
/* 3252 */                                   out.write("&attributeid=2816')\">");
/* 3253 */                                   out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2816")));
/* 3254 */                                   out.write("</a></td>\n    \t    \t<td class=\"monitorinfoodd\" align=\"center\">\n\t    \t    <table align=left width =\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #a8a8a8;\">\n\t    \t    <tr>\n\n\t    \t      <td class=\"");
/* 3255 */                                   if (!SHAREDPROCESSINGPOOLPER.equals("0")) {
/* 3256 */                                     out.write("availabilitybar ");
/*      */                                   }
/* 3258 */                                   out.write("\" width=\"");
/* 3259 */                                   out.print(SHAREDPROCESSINGPOOLPER);
/* 3260 */                                   out.write("%\" title=\"");
/* 3261 */                                   out.print(SHAREDPROCESSINGPOOLPER);
/* 3262 */                                   out.write("%\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n\t    \t      <td  class=\"\" width=\"");
/* 3263 */                                   out.print(shrpp);
/* 3264 */                                   out.write("%\" title=\"");
/* 3265 */                                   out.print(shrpp);
/* 3266 */                                   out.write("%\"> <img src=\"../images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t    \t    </tr>\n\t    \t  </table>\n\t    \t<td>\n\n    \t  </tr>\n    \t  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n    \t    <td class=\"monitorinfoodd-noborder\" align=\"left\">");
/* 3267 */                                   out.print(FormatUtil.getString("am.webclient.as400.PercentUncappedCPUCapacityUsed"));
/* 3268 */                                   out.write("</td>\n            ");
/* 3269 */                                   if (UNCAPPEDCPUCAPACITYPER != null) {
/* 3270 */                                     out.write("\n    \t    <td class=\"monitorinfoodd-noborder\">");
/* 3271 */                                     out.print(UNCAPPEDCPUCAPACITYPER);
/* 3272 */                                     out.write("</td>\n\t    ");
/*      */                                   } else {
/* 3274 */                                     out.write("\n\t    <td class=\"monitorinfoodd-noborder\">-</td>\n\t    ");
/*      */                                   }
/* 3276 */                                   out.write("\n    \t    <td class=\"monitorinfoodd-noborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3277 */                                   out.print(resourceid);
/* 3278 */                                   out.write("&attributeid=2817')\">");
/* 3279 */                                   out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2817")));
/* 3280 */                                   out.write("</a></td>\n    \t    \t<td class=\"monitorinfoodd-noborder\" align=\"center\">\n\t    \t    <table align=left width =\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #a8a8a8;\">\n\t    \t    <tr>\n\n\t    \t      <td class=\"");
/* 3281 */                                   if (!UNCAPPEDCPUCAPACITYPER.equals("0")) {
/* 3282 */                                     out.write("availabilitybar ");
/*      */                                   }
/* 3284 */                                   out.write("\" width=\"");
/* 3285 */                                   out.print(UNCAPPEDCPUCAPACITYPER);
/* 3286 */                                   out.write("%\" title=\"");
/* 3287 */                                   out.print(UNCAPPEDCPUCAPACITYPER);
/* 3288 */                                   out.write("%\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n\t    \t      <td  class=\"\" width=\"");
/* 3289 */                                   out.print(ucpup);
/* 3290 */                                   out.write("%\" title=\"");
/* 3291 */                                   out.print(ucpup);
/* 3292 */                                   out.write("%\"> <img src=\"../images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t    \t    </tr>\n\t    \t  </table>\n\t    \t<td>\n\n    \t  </tr>\n\n          </table>\n\n         <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"conf-mon-table\">\n\t<tr>\n\t\t <td  class=\"conf-mon-heading\" > ");
/* 3293 */                                   out.print(FormatUtil.getString("am.webclient.as400.noofactivejobs"));
/* 3294 */                                   out.write("</td>\n\t</tr>\n<tr>\n\n\t<td align=\"center\" onMouseOver=\"toggledivmo('div8',1)\" onMouseOut=\"toggledivmo('div8',0)\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"table-style\">\n\t\t\t   <tr>\n                               <td align=\"right\" ><div style=\"visibility: hidden;\" id=\"div8\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3295 */                                   out.print(resourceid);
/* 3296 */                                   out.write("&attributeid=2730&period=-7&resourcename=");
/* 3297 */                                   out.print(displayname);
/* 3298 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3299 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3300 */                                   out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3301 */                                   out.print(resourceid);
/* 3302 */                                   out.write("&attributeid=2730&period=-30&resourcename=");
/* 3303 */                                   out.print(displayname);
/* 3304 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3305 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3306 */                                   out.write("'></a>\n                                   </div></td>\n\t\t\t   </tr>\n\t\t\t   <tr>\n\t\t\t   ");
/* 3307 */                                   as400graph.settype("ACTIVEJOBS");
/* 3308 */                                   out.write("\n\t\t\t   <td>");
/*      */                                   
/* 3310 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3311 */                                   _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3312 */                                   _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */                                   
/* 3314 */                                   _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("as400graph");
/*      */                                   
/* 3316 */                                   _jspx_th_awolf_005ftimechart_005f0.setWidth("600");
/*      */                                   
/* 3318 */                                   _jspx_th_awolf_005ftimechart_005f0.setHeight("300");
/*      */                                   
/* 3320 */                                   _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */                                   
/* 3322 */                                   _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                   
/* 3324 */                                   _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel("count");
/* 3325 */                                   int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3326 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3327 */                                     if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3328 */                                       out = _jspx_page_context.pushBody();
/* 3329 */                                       _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3330 */                                       _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3333 */                                       out.write("\n\t\t\t\t ");
/* 3334 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3335 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3338 */                                     if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3339 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3342 */                                   if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3343 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*      */                                   }
/*      */                                   else {
/* 3346 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3347 */                                     out.write("</td>\n\t\t\t   </tr>\n\t\t\t   <tr>\n\t\t\t     <td style=\"height:11px;\"></td>\n\t\t\t   </tr>\n\t\t</table>\n\t</td>\n\n</tr>\n\n</table>\n\n      <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"conf-mon-table\">\n\t<tr>\n\t\t <td class=\"conf-mon-heading\"> ");
/* 3348 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                                       return;
/* 3350 */                                     out.write("</td>\n\t</tr>\n<tr>\n\n\t<td align=\"center\" onMouseOver=\"toggledivmo('div9',1)\" onMouseOut=\"toggledivmo('div9',0)\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"table-style\">\n\t\t\t   <tr>\n\t\t\t   <td align=\"right\"><div style=\"visibility: hidden;\" id=\"div9\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3351 */                                     out.print(resourceid);
/* 3352 */                                     out.write("&attributeid=2804&period=-7&resourcename=");
/* 3353 */                                     out.print(displayname);
/* 3354 */                                     out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3355 */                                     out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3356 */                                     out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3357 */                                     out.print(resourceid);
/* 3358 */                                     out.write("&attributeid=2804&period=-30&resourcename=");
/* 3359 */                                     out.print(displayname);
/* 3360 */                                     out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3361 */                                     out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3362 */                                     out.write("'></a>\n                               </div></td>\n\t\t\t   </tr>\n\t\t\t   <tr>\n\t\t\t   ");
/* 3363 */                                     as400graph.settype("BATCHJOBSONQ");
/* 3364 */                                     out.write("\n\t\t\t   <td>");
/*      */                                     
/* 3366 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3367 */                                     _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3368 */                                     _jspx_th_awolf_005ftimechart_005f1.setParent(null);
/*      */                                     
/* 3370 */                                     _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("as400graph");
/*      */                                     
/* 3372 */                                     _jspx_th_awolf_005ftimechart_005f1.setWidth("600");
/*      */                                     
/* 3374 */                                     _jspx_th_awolf_005ftimechart_005f1.setHeight("300");
/*      */                                     
/* 3376 */                                     _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                                     
/* 3378 */                                     _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 3380 */                                     _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel("count");
/* 3381 */                                     int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3382 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3383 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3384 */                                         out = _jspx_page_context.pushBody();
/* 3385 */                                         _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3386 */                                         _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3389 */                                         out.write("\n\t\t\t\t ");
/* 3390 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3391 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3394 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3395 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3398 */                                     if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3399 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/*      */                                     }
/*      */                                     else {
/* 3402 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3403 */                                       out.write("</td>  ");
/* 3404 */                                       out.write(" \n\t\t\t   </tr>\n\t\t\t   <tr>\n\t\t\t     <td style=\"height:11px;\"></td>\n\t\t\t   </tr>\n\t\t</table>\n\t</td>\n\n</tr>\n\n</table>\n\n     <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"conf-mon-table\">\n\t<tr>\n\t\t <td  class=\"conf-mon-heading\" >");
/* 3405 */                                       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */                                         return;
/* 3407 */                                       out.write("</td>\n\t</tr>\n<tr>\n\n\t<td align=\"center\" onMouseOver=\"toggledivmo('div10',1)\" onMouseOut=\"toggledivmo('div10',0)\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"table-style\">\n\t\t\t   <tr>\n\t\t\t   <td align=\"right\"><div style=\"visibility: hidden;\" id=\"div10\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3408 */                                       out.print(resourceid);
/* 3409 */                                       out.write("&attributeid=2728&period=-7&resourcename=");
/* 3410 */                                       out.print(displayname);
/* 3411 */                                       out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3412 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3413 */                                       out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3414 */                                       out.print(resourceid);
/* 3415 */                                       out.write("&attributeid=2728&period=-30&resourcename=");
/* 3416 */                                       out.print(displayname);
/* 3417 */                                       out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3418 */                                       out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3419 */                                       out.write("'></a>\n                               </div></td>\n\t\t\t   </tr>\n\t\t\t   <tr>\n\t\t\t   ");
/* 3420 */                                       as400graph.settype("USERSSIGNEDON");
/* 3421 */                                       out.write("\n\t\t\t   <td>");
/*      */                                       
/* 3423 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3424 */                                       _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3425 */                                       _jspx_th_awolf_005ftimechart_005f2.setParent(null);
/*      */                                       
/* 3427 */                                       _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("as400graph");
/*      */                                       
/* 3429 */                                       _jspx_th_awolf_005ftimechart_005f2.setWidth("600");
/*      */                                       
/* 3431 */                                       _jspx_th_awolf_005ftimechart_005f2.setHeight("300");
/*      */                                       
/* 3433 */                                       _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                                       
/* 3435 */                                       _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                       
/* 3437 */                                       _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel("count");
/* 3438 */                                       int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3439 */                                       if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3440 */                                         if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3441 */                                           out = _jspx_page_context.pushBody();
/* 3442 */                                           _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3443 */                                           _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3446 */                                           out.write("\n\t\t\t\t ");
/* 3447 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3448 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3451 */                                         if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3452 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3455 */                                       if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3456 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/*      */                                       }
/*      */                                       else {
/* 3459 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3460 */                                         out.write("</td>\n\t\t\t   </tr>\n\t\t\t   <tr>\n\t\t\t     <td style=\"height:11px;\"></td>\n\t\t\t   </tr>\n\t\t</table>\n\t</td>\n\n</tr>\n\n</table>\n\n</td>\n</tr>\n</table>\n\n\n");
/* 3461 */                                         if (_jspx_meth_c_005fset_005f0(_jspx_page_context)) return;
/*      */                                       }
/*      */                                     }
/* 3464 */                                   } } } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3465 */         out = _jspx_out;
/* 3466 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3467 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3468 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3471 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3477 */     PageContext pageContext = _jspx_page_context;
/* 3478 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3480 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3481 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3482 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 3484 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.as400.numberofbatchjobsonqueue");
/* 3485 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3486 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3487 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3488 */       return true;
/*      */     }
/* 3490 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3496 */     PageContext pageContext = _jspx_page_context;
/* 3497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3499 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3500 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3501 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*      */     
/* 3503 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.as400.numberofuserssignedon");
/* 3504 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3505 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3506 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3507 */       return true;
/*      */     }
/* 3509 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3510 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3515 */     PageContext pageContext = _jspx_page_context;
/* 3516 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3518 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 3519 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3520 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 3522 */     _jspx_th_c_005fset_005f0.setVar("datatype");
/*      */     
/* 3524 */     _jspx_th_c_005fset_005f0.setValue("2");
/*      */     
/* 3526 */     _jspx_th_c_005fset_005f0.setScope("session");
/* 3527 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3528 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3529 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3530 */       return true;
/*      */     }
/* 3532 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3533 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\as400\status_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */