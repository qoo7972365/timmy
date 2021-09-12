/*      */ package org.apache.jsp.jsp.includes;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
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
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class CommonDetailsHeader_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  810 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  811 */     getRCATrimmedText(div1, rca);
/*  812 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  815 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  816 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  969 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2194 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2198 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2199 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2200 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2201 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2202 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2203 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2204 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2208 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2209 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
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
/* 2239 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/* 2240 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2242 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2243 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2245 */       out.write(10);
/* 2246 */       out.write(10);
/* 2247 */       out.write(10);
/* 2248 */       out.write("\n\n\n<SCRIPT src=\"/template/calendar.js\" LANGUAGE=\"JavaScript1.2\"></SCRIPT>\n<SCRIPT src=\"/template/calendar-en.js\" LANGUAGE=\"JavaScript1.2\"></SCRIPT>\n<SCRIPT src=\"/template/calendar-setup.js\" LANGUAGE=\"JavaScript1.2\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/customfield.js\"></SCRIPT>\n<link href=\"../images/calendar-win2k-cold-1.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/jquery.ui.datepicker.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/customjquery.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */       
/* 2250 */       String moname = request.getParameter("monitorname");
/* 2251 */       String resourceName = moname;
/* 2252 */       String resID = request.getParameter("resID");
/* 2253 */       String healthId = request.getParameter("healthId");
/* 2254 */       String availabilityId = request.getParameter("availabilityId");
/* 2255 */       boolean isConfMonitor = (request.getParameter("isConfMonitor") != null) && (request.getParameter("isConfMonitor").equals("true"));
/* 2256 */       Properties availabilitydata = new com.adventnet.appmanager.reporting.actions.AMReportActions().calculateConfMonitorAvailabilityDetails(resID);
/* 2257 */       Properties ess_atts = new Properties();
/* 2258 */       ess_atts.setProperty("Availability", availabilityId);
/* 2259 */       ess_atts.setProperty("Health", healthId);
/* 2260 */       String baseid1 = request.getParameter("baseid1") != null ? request.getParameter("baseid1") : "";
/* 2261 */       ArrayList attribIDs = new ArrayList();
/* 2262 */       attribIDs.add(availabilityId);
/* 2263 */       attribIDs.add(healthId);
/* 2264 */       ArrayList resIDs = new ArrayList();
/* 2265 */       resIDs.add(resID);
/* 2266 */       Properties alert = getStatus(resIDs, attribIDs);
/*      */       
/* 2268 */       out.write("\n<script>\nvar reloadPeriod=\"");
/* 2269 */       out.print(request.getAttribute("reloadperiod") != null ? (String)request.getAttribute("reloadperiod") : "300");
/* 2270 */       out.write("\";\t\nfunction submitConfForm(){\n\t\t//document.getElementById('tabsForm').TimeUnit.value=document.getElementById(\"HeaderTimeUnit\").value;\t//0--today,15--day,16--week\n\t\t//document.getElementById('tabsForm').granularity.value=document.getElementById(\"Headergranularity\").value;\t\t\t//polledvalue,any range in minutes\n\t\tdocument.getElementById('tabsForm').customDate.value=document.getElementById(\"HeadercustomDate\").value;\t //date otherwise \" \"\n\t\t//document.getElementById('tabsForm').monthUnit.value=document.getElementById(\"HeadermonthUnit\").value; // if week selected \" month,year\", otherwise \" \"\n\t\t//document.getElementById('tabsForm').weekUnit.value=document.getElementById(\"HeaderweekUnit\").value;\t\t\t//week number in the month, otherwise \" \"\n\t\t var tabid=document.getElementById('tabsForm').tabId.value;\n\t\t var type=document.getElementById('tabsForm').type.value;\n\t\t var resId=document.getElementById('tabsForm').resourceid.value;\n\t\t var serverName=\"");
/* 2271 */       out.print(request.getAttribute("serverName") != null ? (String)request.getAttribute("serverName") : "");
/* 2272 */       out.write("\";\n\t\t if(tabid==0){\n\t\t\t tabid=1;\n\t\t }\t\n\t\t if(reloadPeriod/60==document.getElementById('tabsForm').granularity.value){\n\t\t\t document.getElementById('tabsForm').granularity.value=\"PolledData\"; //No I18N      \n\t\t }\n\t\t gettabData(tabid,type,resId,serverName,reloadPeriod);\n\t\t\n}\nvar cleanUpInterval=\"");
/* 2273 */       out.print(com.adventnet.appmanager.server.confmonitor.ConfMonitorConstants.getCleanupInteral());
/* 2274 */       out.write("\";\nfunction setFormValuesAndSubmit(buttonType,value){\n\t//alert(buttonType+\"::\"+value);\n\tif(buttonType=='button'){\t\n\t\tdocument.getElementById('tabsForm').TimeUnit.value=value;\t\t\n\t\tif(value!=15){\n\t\t\tdocument.getElementById(\"HeadercustomDate\").value=\"\";//No I18N\n\t\t}\t\t\n\t}\n\telse if(buttonType==\"Slider\"){\n\t\tdocument.getElementById('tabsForm').granularity.value=value;\t\t\n\t\t//alert(document.getElementById(\"HeadermonthUnit\").value);\n\t}\n\tsubmitConfForm();\n\t changeButtonStyles(value,value,buttonType);\n\t// alert(document.getElementById(\"Headergranularity\").value);\n}\nfunction populatePeriodForm(){    \n\tif(document.getElementById('tabsForm')){\n\t\tif(document.location.hash!=null && document.location.hash.indexOf('#tabId')!=-1){\t\t\n\t\t\t//document.getElementById('tabsForm').TimeUnit.value=document.getElementById(\"HeaderTimeUnit\").value;\t\n\t\t\t//document.getElementById('tabsForm').granularity.value=document.getElementById(\"Headergranularity\").value;\t\t\t\n\t\t\tdocument.getElementById('tabsForm').customDate.value=document.getElementById(\"HeadercustomDate\").value;\n");
/* 2275 */       out.write("\t\t\t//document.getElementById('tabsForm').monthUnit.value=document.getElementById(\"HeadermonthUnit\").value;\n\t\t\t//document.getElementById('tabsForm').weekUnit.value=document.getElementById(\"HeaderweekUnit\").value;\n\t\t}\n\t\telse{\n\t\t\tvar granularity=\"");
/* 2276 */       out.print(request.getAttribute("TimeValues") != null ? (String)((HashMap)request.getAttribute("TimeValues")).get("Granularity") : "");
/* 2277 */       out.write("\";\n\t\t\tvar periodicity=\"");
/* 2278 */       out.print(request.getAttribute("TimeValues") != null ? (String)((HashMap)request.getAttribute("TimeValues")).get("Periodicity") : "");
/* 2279 */       out.write("\";\n\t\t\tvar dateSelected=\"");
/* 2280 */       out.print(request.getAttribute("TimeValues") != null ? (String)((HashMap)request.getAttribute("TimeValues")).get("DateSelected") : "");
/* 2281 */       out.write("\";\n\t\t\tvar weekSelected=\"");
/* 2282 */       out.print(request.getAttribute("TimeValues") != null ? (String)((HashMap)request.getAttribute("TimeValues")).get("WeekSelected") : "");
/* 2283 */       out.write("\";\n\t\t\tvar yearMonth=\"");
/* 2284 */       out.print(request.getAttribute("TimeValues") != null ? (String)((HashMap)request.getAttribute("TimeValues")).get("YearMonth") : "");
/* 2285 */       out.write("\";\t\n\t\t\tdocument.getElementById('tabsForm').TimeUnit.value=periodicity;\t\n\t\t\tdocument.getElementById('tabsForm').granularity.value=granularity;\n\t\t\tdocument.getElementById('tabsForm').customDate.value=dateSelected;\n\t\t\tdocument.getElementById('tabsForm').monthUnit.value=yearMonth;\n\t\t\tdocument.getElementById('tabsForm').weekUnit.value=weekSelected;\n\t\t//\tdocument.getElementById(\"HeaderTimeUnit\").value=periodicity;\n\t\t\t//document.getElementById(\"Headergranularity\").value=granularity;\n\t\t\tdocument.getElementById(\"HeadercustomDate\").value=dateSelected;\n\t\t\t//document.getElementById(\"HeadermonthUnit\").value=yearMonth;\n\t\t\t//document.getElementById(\"HeaderweekUnit\").value=weekSelected;\n\t\t}\n\t\t\n\t}\n\t\n} \nfunction fnChangeStyle(obj,status,sides,periodicity){\n\tvar classSides;\t\t\n\tif(sides=='left'){\n\t\tclassSides=\"ui-corner-left\";//No I18N    \t\t\t\t\n\t}\n\telse if(sides=='right'){\n\t\tclassSides=\"ui-corner-right\";\t//No I18N    \t\t\t\t\n\t}\t\n\tvar selectedPeriod;\n\tif(document.location.hash!=null  && document.location.hash.indexOf('#tab')!=-1){\n\t\tvar temp=(document.location.hash.split(\"&\"));\n");
/* 2286 */       out.write("\t\t selectedPeriod=temp[1].split(\"-\")[1];\n\t}\n\telse if(document.getElementById(\"tabsForm\")){\n\t\tselectedPeriod=document.getElementById(\"tabsForm\").TimeUnit.value;\n\t}\n\tif(selectedPeriod==periodicity){\t\t\n\t\t\t\tclassSides=classSides+\" ui-state-active\"; //No I18N      \n\t}\n\tif(status=='in'){\t\t\n\t\tobj.className=\"ui-button ui-widget ui-state-hover ui-button-text-only \"+classSides;//No I18N    \t\t\t\t\n\t}\n\telse{\n\t\tobj.className=\"ui-button ui-widget ui-state-default ui-button-text-only \"+classSides;//No I18N    \t\t\t\t\n\t}\n\t\n}\nfunction setWeekDetails(monthAndYear,week){\t\n\tdocument.getElementById('tabsForm').monthUnit.value=monthAndYear;\t\t\n\tdocument.getElementById('tabsForm').weekUnit.value=week;\t\t\n\tsetFormValuesAndSubmit('button',16);//No I18N    \t\t\n\tcloseDialog();\n}\nfunction showWeek(){\n\tvar http=getHTTPObject();\n\tURL='showCustom.do?method=showMonthAndWeek&PRINTER_FRIENDLY=true';//No I18N\n\thttp.open(\"GET\",URL,true);\n\thttp.onreadystatechange = function (){\t\t\n\t\tif(http.readyState == 4)\n        {\t\t\n                if (http.status == 200)\n");
/* 2287 */       out.write("                {             \t\t\t\n                \t\tvar result=http.responseText;                \t\t\n                \t\tdocument.getElementById('customWeekDiv').innerHTML=result;                \n\t\t\t\t\t\tglobalShowMenuInDialogConf('customWeekHolder','customWeekDiv',80,-5);//No I18N    \t\t\t\t\n\t\t\t\t}\n           }\n};\n\thttp.send(null);\t\n\t\n}\nfunction changeYear(gotoDiv){\t\t        \n\tglobalShowMenuInDialogConf('customWeekHolder',gotoDiv,80,-5);//No I18N    \t\t\t\t\n}\nfunction changeButtonStyles(selectedPeriod,selectedGranule,buttonType){\n\tif(buttonType=='button' || buttonType=='both'){ \n\t\tif(selectedPeriod==15){\n\t\t\tfnChangeStyle(document.getElementById('conf-datepicker'),'out','both',15); //No I18N      \n\t\t\tdocument.getElementById('DayText').className=\"ui-button-text-active\";\t\t //No I18N      \t\n\t\t\tfnChangeStyle(document.getElementById('customWeekHolder'),'out','right',16); //No I18N      \n\t\t\tdocument.getElementById('weekText').className=\"ui-button-text\"; //No I18N      \n\t\t\tfnChangeStyle(document.getElementById('conf-buttonSet'),'out','left',0); //No I18N      \n");
/* 2288 */       out.write("\t\t\tdocument.getElementById('todayText').className=\"ui-button-text\"; //No I18N      \n\t\t}\n\t\t\n\t\telse if(selectedPeriod==16){\t\t\t\n\t\t\tfnChangeStyle(document.getElementById('customWeekHolder'),'out','right',16);\t\t\t //No I18N      \n\t\t\tdocument.getElementById('weekText').className=\"ui-button-text-active\"; //No I18N      \n\t\t\t\n\t\t\tfnChangeStyle(document.getElementById('conf-datepicker'),'out','both',15); //No I18N      \n\t\t\tdocument.getElementById('DayText').className=\"ui-button-text\"; //No I18N      \n\t\t\tfnChangeStyle(document.getElementById('conf-buttonSet'),'out','left',0); //No I18N      \n\t\t\tdocument.getElementById('todayText').className=\"ui-button-text\"; //No I18N      \n\t\t}\n\t\telse{\n\t\t\tfnChangeStyle(document.getElementById('conf-buttonSet'),'out','left',0); //No I18N      \n\t\t\tdocument.getElementById('todayText').className=\"ui-button-text-active\"; //No I18N      \n\t\t\t\n\t\t\tfnChangeStyle(document.getElementById('customWeekHolder'),'out','right',16); //No I18N      \n\t\t\tdocument.getElementById('weekText').className=\"ui-button-text\"; //No I18N      \n");
/* 2289 */       out.write("\t\t\tfnChangeStyle(document.getElementById('conf-datepicker'),'out','both',15); //No I18N      \n\t\t\tdocument.getElementById('DayText').className=\"ui-button-text\"; //No I18N      \n\t\t}\n\t}\n\tif(buttonType=='Slider' || buttonType=='both'){\n\t\tif(\"PolledData\"==selectedGranule){\n\t\t\tselectedGranule=reloadPeriod/60;\n\t\t}\n\t\t$( \".slider\" ).slider({ //No I18N      \n\t\t\t value: selectedGranule//No I18N\t\t\n\t   });\n\t\t var s = selectedGranule+\" \"+\"");
/* 2290 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */         return;
/* 2292 */       out.write("\"; //No I18N      \n\t\t  $( \"#slider-result\" ).html(s) //No I18N      \n\n\t\t}\n\t}\n</script>\n<form id=\"periodForm\" style=\"display:inline;\">\t\n<input type=\"hidden\" id=\"granularityValue\" name=\"granularityValue\" value=\"");
/* 2293 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2295 */       out.write("\"/>\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"width=\"100%\">\n\t<tr>\n\t\t<td>\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t");
/* 2296 */       if (isConfMonitor) {
/* 2297 */         out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t\n\t\t\t\t\t\t<td width=\"68%\" class=\"conf-top-band-gray conf-top-band-gray-noright\">\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<span class=\"conf-top-summary-heading\"> ");
/* 2298 */         out.print(getTrimmedText(moname, 35));
/* 2299 */         out.write("</span>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t <td width=\"15%\" class=\"conf-top-band-gray tooltip-apm conf-top-band-gray-noleft\">\t\t\t\t\t\t \n\t\t\t\t\t \n\t\t\t\t\t \n\t\t\t\t\t <div style=\"width:435px; float:right\" >\n\t\t\t\t\t<div  style=\"width:200px; float:left;\">\t\t\t \t\n\t\t\t\t\t \t\t<span style=\"font-size:10px;\" class=\"ui-buttonset\">\n\t\t\t\t\t \t\t");
/* 2300 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */           return;
/* 2302 */         out.write("\n\t\t\t\t\t \t \t<input type=\"hidden\" id=\"HeadercustomDate\" name=\"HeadercustomDate\" onChange=\"javascript:setFormValuesAndSubmit('button',15)\" style=\"position:absolute;\"/>\n\t\t\t\t\t \t ");
/*      */         
/* 2304 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2305 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2306 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/* 2307 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2308 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */           for (;;) {
/* 2310 */             out.write("\n\t\t\t\t\t \t\t");
/*      */             
/* 2312 */             WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2313 */             _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2314 */             _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/* 2316 */             _jspx_th_c_005fwhen_005f1.setTest("${perioicitySelected==15}");
/* 2317 */             int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2318 */             if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */               for (;;) {
/* 2320 */                 out.write("\t\t\t\t\t \t\t\t\t\t\t\t \t\t\n\t\t\t\t\t \t\t\t<label id=\"conf-datepicker\" style=\"width: 60px;\" class=\"ui-button ui-widget ui-state-default ui-button-text-only ui-state-active\"  onmouseOut=\"javascript:fnChangeStyle(this,'out','both',15);\" onmouseOver=\"javascript:fnChangeStyle(this,'in','both',15);\" title=\"");
/* 2321 */                 out.print(FormatUtil.getString("am.webclient.custom.date"));
/* 2322 */                 out.write("\"><span id=\"DayText\" class=\"ui-button-text-active\">");
/* 2323 */                 if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                   return;
/* 2325 */                 out.write("</span></label>\n\t\t\t\t\t \t\t");
/* 2326 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2327 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2331 */             if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2332 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */             }
/*      */             
/* 2335 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2336 */             out.write("\n\t\t\t\t\t \t\t");
/*      */             
/* 2338 */             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2339 */             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2340 */             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2341 */             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2342 */             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */               for (;;) {
/* 2344 */                 out.write("\n\t\t\t\t\t \t\t\t<label id=\"conf-datepicker\" style=\"width: 60px;\" class=\"ui-button ui-widget ui-state-default ui-button-text-only\" onmouseOut=\"javascript:fnChangeStyle(this,'out','both',15);\" onmouseOver=\"javascript:fnChangeStyle(this,'in','both',15);\" title=\"");
/* 2345 */                 out.print(FormatUtil.getString("am.webclient.custom.date"));
/* 2346 */                 out.write("\"><span id=\"DayText\" class=\"ui-button-text\">");
/* 2347 */                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                   return;
/* 2349 */                 out.write("</span></label>\n\t\t\t\t\t \t\t");
/* 2350 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2351 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2355 */             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2356 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */             }
/*      */             
/* 2359 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2360 */             out.write("\n\t\t\t\t\t \t ");
/* 2361 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2362 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2366 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2367 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */         }
/*      */         
/* 2370 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2371 */         out.write("\n\t\t\t\t\t \t ");
/*      */         
/* 2373 */         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2374 */         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2375 */         _jspx_th_c_005fchoose_005f2.setParent(null);
/* 2376 */         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2377 */         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */           for (;;) {
/* 2379 */             out.write("\n\t\t\t\t\t \t\t");
/*      */             
/* 2381 */             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2382 */             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2383 */             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */             
/* 2385 */             _jspx_th_c_005fwhen_005f2.setTest("${perioicitySelected==16}");
/* 2386 */             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2387 */             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */               for (;;) {
/* 2389 */                 out.write("\t\t\t\t\t \t\t\t\t\t\t\t \t\t\n\t\t\t\t\t \t\t\t<label id=\"customWeekHolder\" style=\"width: 60px;\" class=\"ui-button ui-widget ui-state-default ui-button-text-only ui-corner-right ui-state-active\"  onmouseOut=\"javascript:fnChangeStyle(this,'out','right',16);\" onmouseOver=\"javascript:fnChangeStyle(this,'in','right',16);\" title=\"");
/* 2390 */                 out.print(FormatUtil.getString("am.webclient.custom.week"));
/* 2391 */                 out.write("\" onClick=\"javascript:showWeek();\"><span id=\"weekText\" class=\"ui-button-text-active\">");
/* 2392 */                 if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/* 2394 */                 out.write("</span></label>\n\t\t\t\t\t \t\t");
/* 2395 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2396 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2400 */             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2401 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */             }
/*      */             
/* 2404 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2405 */             out.write("\n\t\t\t\t\t \t\t");
/*      */             
/* 2407 */             OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2408 */             _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2409 */             _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2410 */             int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2411 */             if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */               for (;;) {
/* 2413 */                 out.write("\n\t\t\t\t\t \t\t\t<label id=\"customWeekHolder\" style=\"width: 60px;\" class=\"ui-button ui-widget ui-state-default ui-button-text-only ui-corner-right\" onmouseOut=\"javascript:fnChangeStyle(this,'out','right',16);\" onmouseOver=\"javascript:fnChangeStyle(this,'in','right',16);\" title=\"");
/* 2414 */                 out.print(FormatUtil.getString("am.webclient.custom.week"));
/* 2415 */                 out.write("\" onClick=\"javascript:showWeek();\"><span id=\"weekText\" class=\"ui-button-text\">");
/* 2416 */                 if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/*      */                   return;
/* 2418 */                 out.write("</span></label>\n\t\t\t\t\t \t\t");
/* 2419 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2420 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2424 */             if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2425 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */             }
/*      */             
/* 2428 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2429 */             out.write("\n\t\t\t\t\t \t ");
/* 2430 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2431 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2435 */         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2436 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */         }
/*      */         
/* 2439 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2440 */         out.write("\n\t\t\t\t\t \t \t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t</span>\t\t\t\n\t\t\t\t\t</div>\n\t\t\t\t\t\t<div  style=\"width:160px; float:left; position:relative; top:5px;\">\n\t\t\t\t\t\t\t\t<div id=\"slider-result\" class=\"sliderresultText\" align=\"left\"  title=\"");
/* 2441 */         out.print(FormatUtil.getString("am.webclient.granularitypicker.value"));
/* 2442 */         out.write(34);
/* 2443 */         out.write(32);
/* 2444 */         out.write(62);
/* 2445 */         if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */           return;
/* 2447 */         out.write(32);
/* 2448 */         if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */           return;
/* 2450 */         out.write("</div>\t\n\t\t\t\t\t\t\t\t<div class=\"slider\" title=\"");
/* 2451 */         out.print(FormatUtil.getString("am.webclient.granularitypicker"));
/* 2452 */         out.write("\" ></div>\t\t</div></div>   \n\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\n\t\t\t\t\t\n\t\t\t\n\t\t\t    </tr>\n\t\t\t    <tr>\n\t\t\t    \t<td  colspan=\"3\" valign=\"top\"  class=\"conf-top-summary\" >\n\t\t\t    ");
/*      */       } else {
/* 2454 */         out.write("\n\t\t\t    <tr>\n\t\t\t\t\t<td class=\"conf-top-band-gray conf-top-band-gray-noright\"><span class=\"conf-top-summary-heading\"> ");
/* 2455 */         out.print(getTrimmedText(moname, 35));
/* 2456 */         out.write("</span></td>\n\t\t\t    </tr>\n\t\t\t    <tr>\n\t\t\t    \t<td  valign=\"top\"  class=\"conf-top-summary\" >\n\t\t\t    ");
/*      */       }
/* 2458 */       out.write("\n\t\t\t    \n\t\t\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\">\n\t\t\t\t\t\t\t<tr><td colspan=\"11\" height=\"2\"></td></tr>\n\t\t\t\t\t\t\t<tr class=\"confsummary-links\">\n\t\t\t\t\t\t\t\t<td width=\"13%\"  align=\"center\" class=\"conf-top-summary-inner\" >");
/* 2459 */       if (_jspx_meth_fmt_005fmessage_005f8(_jspx_page_context))
/*      */         return;
/* 2461 */       out.write("</td>\n\t\t\t    \t\t\t\t<td width=\"2%\" style=\"border-left:1px solid #d8d8d8;\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td width=\"13%\"  align=\"center\" class=\"conf-top-summary-inner\" >");
/* 2462 */       if (_jspx_meth_fmt_005fmessage_005f9(_jspx_page_context))
/*      */         return;
/* 2464 */       out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"2%\" style=\"border-left:1px solid #d8d8d8;\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td width=\"16%\"  align=\"center\" class=\"conf-top-summary-inner\">");
/* 2465 */       if (_jspx_meth_fmt_005fmessage_005f10(_jspx_page_context))
/*      */         return;
/* 2467 */       out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"2%\" style=\"border-left:1px solid #d8d8d8;\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td width=\"25%\"  align=\"left\" class=\"conf-top-summary-inner\">");
/* 2468 */       if (_jspx_meth_fmt_005fmessage_005f11(_jspx_page_context))
/*      */         return;
/* 2470 */       out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"2%\" style=\"border-left:1px solid #d8d8d8;\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td width=\"25%\" align=\"left\" class=\"conf-top-summary-inner\">");
/* 2471 */       if (_jspx_meth_fmt_005fmessage_005f12(_jspx_page_context))
/*      */         return;
/* 2473 */       out.write("</td>\n\t\t\t\t\t\t\t</tr>\t\t\t   \t\t\t\t\n\t\t\t   \t\t\t\t<tr  class=\"confsummary-links\" onmouseover=\"this.className='confsummaryHeaderHover'\" onmouseout=\"this.className='confsummaryHeader'\">\n\t\t\t\t\t\t\t\t<td class=\"conf-top-summary-values\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2474 */       out.print(resID);
/* 2475 */       out.write("&attributeid=");
/* 2476 */       out.print(healthId);
/* 2477 */       out.write("')\">");
/* 2478 */       out.print(getSeverityImageForConfMonitor(alert.getProperty(resID + "#" + healthId), false));
/* 2479 */       out.write("</a></td>\n\t\t\t\t\t\t\t\t<td width=\"2%\" style=\"border-left:1px solid #d8d8d8;\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td class=\"conf-top-summary-values\"  align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2480 */       out.print(resID);
/* 2481 */       out.write("&attributeid=");
/* 2482 */       out.print(availabilityId);
/* 2483 */       out.write("')\">");
/* 2484 */       out.print(getSeverityImageForConfMonitor(alert.getProperty(resID + "#" + availabilityId), true));
/* 2485 */       out.write("</a></td>\n\t\t\t\t\t\t\t\t<td width=\"2%\" style=\"border-left:1px solid #d8d8d8;\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td class=\"conf-top-summary-values\"  align=\"center\"><a href=\"javascript:void(0)\" onClick=\"javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&period=0&resourceid=");
/* 2486 */       out.print(resID);
/* 2487 */       out.write("')\">");
/* 2488 */       out.print(availabilitydata.getProperty("uptimepercentage") != null ? availabilitydata.getProperty("uptimepercentage") : "-");
/* 2489 */       out.write("%</a></td>\n\t\t\t\t\t\t\t\t<td width=\"2%\" style=\"border-left:1px solid #d8d8d8;\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td class=\"conf-top-summary-values\"  align=\"left\"><a href=\"javascript:void(0)\" onClick=\"javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&period=0&resourceid=");
/* 2490 */       out.print(resID);
/* 2491 */       out.write("')\">");
/* 2492 */       out.print(availabilitydata.getProperty("mtbf") != null ? availabilitydata.getProperty("mtbf") : "-");
/* 2493 */       out.write("</a></td>\n\t\t\t\t\t\t\t\t<td width=\"2%\" style=\"border-left:1px solid #d8d8d8;\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td class=\"conf-top-summary-values\"  align=\"left\">");
/* 2494 */       if (availabilitydata.getProperty("lastDownTime") == null) {
/* 2495 */         out.write(" -NA-");
/*      */       } else {
/* 2497 */         out.write("<a href=\"javascript:void(0)\" onClick=\"javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&period=0&resourceid=");
/* 2498 */         out.print(resID);
/* 2499 */         out.write("')\">");
/* 2500 */         out.print(availabilitydata.getProperty("lastDownTime"));
/*      */       }
/* 2502 */       out.write("</a></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t   \t\t\t</table>\n\t\t\t   \t\t</td>\t\n\t\t\t   \t</tr>\n\t\t\t</table>\n\t\t\t\t\t\t\t\t\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"center\"><img src=\"images/spacer.gif\" height=\"24\" width=\"711\"/>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t");
/* 2503 */       if ((alert.getProperty(resID + "#" + healthId) != null) && (alert.getProperty(resID + "#" + healthId).equals("1"))) {
/* 2504 */         out.write("\n\t\t\t<div id=\"rca-critical\" >\n\t\t\t\t<div class=\"on\" title=\"Monitor is Critical\">\n\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\" class=\"conf-top-summary-main\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"7%\" class=\"conf-top-summary-inner\" align=\"center\">");
/* 2505 */         out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2506 */         out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"conf-top-summary-values\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2507 */         out.print(resID);
/* 2508 */         out.write("&attributeid=");
/* 2509 */         out.print(healthId);
/* 2510 */         out.write("')\"><img src=\"images/icon_conf_hlt_critical.png\" style=\"position:relative; top:1px;\"/></a></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t</div>\n\t\t\t</div>\n\t\t\t<div  class=\"show-analysis\">\n\t\t\t\t<p><b class=\"bodytextbold\">");
/* 2511 */         out.print(FormatUtil.getString("am.mypage.health.monitor.critical.text", new String[] { moname }));
/* 2512 */         out.write(".&nbsp;");
/* 2513 */         out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 2514 */         out.write("</b></p>\n\t\t\t\t<p><span class=\"rca-critical-text\">");
/* 2515 */         out.print(FormatUtil.getString("am.webclient.rca.statussince"));
/* 2516 */         out.write(": &nbsp;<b>");
/* 2517 */         out.print(getConfHealthTime(alert.getProperty(resID + "#" + healthId + "#" + "TIME")));
/* 2518 */         out.write("</b></span> | <span class=\"rca-critical-text\">");
/* 2519 */         out.print(FormatUtil.getString("table.heading.attribute"));
/* 2520 */         out.write(":&nbsp;<b>");
/* 2521 */         out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2522 */         out.write("</b></span> | <span class=\"rca-critical-text\">");
/* 2523 */         out.print(FormatUtil.getString("am.webclient.rca.dependencyrule"));
/* 2524 */         out.write(":&nbsp;<b>");
/* 2525 */         out.print(getDependentChildAttribs(resID, healthId));
/* 2526 */         out.write("</b></span></p>\t\t\t\t\n\t\t\t\t<p>");
/* 2527 */         out.print(getConfHealthRCA(alert.getProperty(resID + "#" + healthId + "#" + "MESSAGE")));
/* 2528 */         out.write("</p>\n\t\t\t</div>\n\t\t\t");
/*      */       }
/* 2530 */       out.write("\n\t\t</td>\n\t</tr>\n\n</table>\n<div id=\"customWeekDiv\" style=\"display:none\"></div>\n</form>\n<script>\n\nvar e, i = 0;\n$( \".slider\" ).slider({//No I18N\n  \tanimate:true,\n        range: \"min\",//No I18N\n        value: ");
/* 2531 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */         return;
/* 2533 */       out.write(",//No I18N\n        min: 1,\n        max: 60,\n        step:1,    \n\n        //this gets a live reading of the value and prints it on the page\n        slide: function( event, ui ) {\n        \tvar s = ui.value+\" \"+\"");
/* 2534 */       if (_jspx_meth_fmt_005fmessage_005f13(_jspx_page_context))
/*      */         return;
/* 2536 */       out.write("\";\n            $( \"#slider-result\" ).html(s);//No I18N\n        },\n        //this updates the hidden form field so we can submit the data using a form\n        change: function(event, ui) {\n        \n        },\n        stop: function(event, ui) {         \t\n        \tsetFormValuesAndSubmit('Slider',ui.value);//No I18N\n        }\n\n        });\n\n$(function() {\n\t$( \"#HeadercustomDate\" ).datepicker({ minDate:-(cleanUpInterval) , maxDate: new Date(),dateFormat:\"mm-dd-yy\"});\t//No I18N\n});\n$('#conf-datepicker').click(function() {//No I18N\n    $('#HeadercustomDate').datepicker('show');    //No I18N\n});\n\nif(document.location.hash!=null  && document.location.hash.indexOf('#tab')!=-1){\n\t\tvar temp=(document.location.hash.split(\"&\"));\n\t\tvar selectedPeriod=temp[1].split(\"-\")[1];\n\t\tvar selectedGranule=temp[2].split(\"-\")[1];\t\t\n\t\tchangeButtonStyles(selectedPeriod,selectedGranule,\"both\");\t //No I18N      \n\t\t\n}\n\n\n</script>\n");
/*      */     } catch (Throwable t) {
/* 2538 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2539 */         out = _jspx_out;
/* 2540 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2541 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2542 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2545 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2551 */     PageContext pageContext = _jspx_page_context;
/* 2552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2554 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2555 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2556 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 2558 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.report.mins");
/* 2559 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2560 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2561 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2562 */       return true;
/*      */     }
/* 2564 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2570 */     PageContext pageContext = _jspx_page_context;
/* 2571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2573 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2574 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2575 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2577 */     _jspx_th_c_005fout_005f0.setValue("${granularitySelected}");
/* 2578 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2579 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2580 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2581 */       return true;
/*      */     }
/* 2583 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2589 */     PageContext pageContext = _jspx_page_context;
/* 2590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2592 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2593 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2594 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2595 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2596 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 2598 */         out.write("\n\t\t\t\t\t \t\t");
/* 2599 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2600 */           return true;
/* 2601 */         out.write("\n\t\t\t\t\t \t\t");
/* 2602 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2603 */           return true;
/* 2604 */         out.write("\n\t\t\t\t\t \t ");
/* 2605 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2606 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2610 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2611 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2612 */       return true;
/*      */     }
/* 2614 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2615 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2620 */     PageContext pageContext = _jspx_page_context;
/* 2621 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2623 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2624 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2625 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 2627 */     _jspx_th_c_005fwhen_005f0.setTest("${perioicitySelected==0}");
/* 2628 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2629 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 2631 */         out.write("\t\t\t\t\t \t\t\t\t\t\t\t \t\t\n\t\t\t\t\t \t\t\t<label id=\"conf-buttonSet\" style=\"width: 60px;\" class=\"ui-button ui-widget ui-state-default ui-button-text-only ui-corner-left ui-state-active\"  onmouseOut=\"javascript:fnChangeStyle(this,'out','left',0);\" onmouseOver=\"javascript:fnChangeStyle(this,'in','left',0);\" onClick=\"javascript:setFormValuesAndSubmit('button',0)\"><span id=\"todayText\" class=\"ui-button-text-active\">");
/* 2632 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 2633 */           return true;
/* 2634 */         out.write("</span></label>\n\t\t\t\t\t \t\t");
/* 2635 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2636 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2640 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2641 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2642 */       return true;
/*      */     }
/* 2644 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2650 */     PageContext pageContext = _jspx_page_context;
/* 2651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2653 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2654 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2655 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2657 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.period.today");
/* 2658 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2659 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2660 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2661 */       return true;
/*      */     }
/* 2663 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2669 */     PageContext pageContext = _jspx_page_context;
/* 2670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2672 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2673 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2674 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 2675 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2676 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 2678 */         out.write("\n\t\t\t\t\t \t\t\t<label id=\"conf-buttonSet\" style=\"width: 60px;\" class=\"ui-button ui-widget ui-state-default ui-button-text-only ui-corner-left\" onmouseOut=\"javascript:fnChangeStyle(this,'out','left',0);\" onmouseOver=\"javascript:fnChangeStyle(this,'in','left',0);\" onClick=\"javascript:setFormValuesAndSubmit('button',0)\"><span id=\"todayText\" class=\"ui-button-text\">");
/* 2679 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 2680 */           return true;
/* 2681 */         out.write("</span></label>\n\t\t\t\t\t \t\t");
/* 2682 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2683 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2687 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2688 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2689 */       return true;
/*      */     }
/* 2691 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2692 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2697 */     PageContext pageContext = _jspx_page_context;
/* 2698 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2700 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2701 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 2702 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 2704 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.period.today");
/* 2705 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 2706 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 2707 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2708 */       return true;
/*      */     }
/* 2710 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2711 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2716 */     PageContext pageContext = _jspx_page_context;
/* 2717 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2719 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2720 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 2721 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 2723 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.period.selectday");
/* 2724 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 2725 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 2726 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 2727 */       return true;
/*      */     }
/* 2729 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 2730 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2735 */     PageContext pageContext = _jspx_page_context;
/* 2736 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2738 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2739 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 2740 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 2742 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.period.selectday");
/* 2743 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 2744 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 2745 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 2746 */       return true;
/*      */     }
/* 2748 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 2749 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2754 */     PageContext pageContext = _jspx_page_context;
/* 2755 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2757 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2758 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 2759 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2761 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.period.selectweek");
/* 2762 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 2763 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 2764 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 2765 */       return true;
/*      */     }
/* 2767 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 2768 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2773 */     PageContext pageContext = _jspx_page_context;
/* 2774 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2776 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2777 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 2778 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2780 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.period.selectweek");
/* 2781 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 2782 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 2783 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 2784 */       return true;
/*      */     }
/* 2786 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 2787 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2792 */     PageContext pageContext = _jspx_page_context;
/* 2793 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2795 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2796 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2797 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 2799 */     _jspx_th_c_005fout_005f1.setValue("${granularitySelected}");
/* 2800 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2801 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2802 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2803 */       return true;
/*      */     }
/* 2805 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2806 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2811 */     PageContext pageContext = _jspx_page_context;
/* 2812 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2814 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2815 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 2816 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/*      */     
/* 2818 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.report.mins");
/* 2819 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 2820 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 2821 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 2822 */       return true;
/*      */     }
/* 2824 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 2825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2830 */     PageContext pageContext = _jspx_page_context;
/* 2831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2833 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2834 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 2835 */     _jspx_th_fmt_005fmessage_005f8.setParent(null);
/*      */     
/* 2837 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.common.health.text");
/* 2838 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 2839 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 2840 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 2841 */       return true;
/*      */     }
/* 2843 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 2844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2849 */     PageContext pageContext = _jspx_page_context;
/* 2850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2852 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2853 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 2854 */     _jspx_th_fmt_005fmessage_005f9.setParent(null);
/*      */     
/* 2856 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.common.availability.text");
/* 2857 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 2858 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 2859 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 2860 */       return true;
/*      */     }
/* 2862 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 2863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2868 */     PageContext pageContext = _jspx_page_context;
/* 2869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2871 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2872 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 2873 */     _jspx_th_fmt_005fmessage_005f10.setParent(null);
/*      */     
/* 2875 */     _jspx_th_fmt_005fmessage_005f10.setKey("am.webclient.common.todaysavailability.text");
/* 2876 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 2877 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 2878 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 2879 */       return true;
/*      */     }
/* 2881 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 2882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2887 */     PageContext pageContext = _jspx_page_context;
/* 2888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2890 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2891 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 2892 */     _jspx_th_fmt_005fmessage_005f11.setParent(null);
/*      */     
/* 2894 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.webclient.common.todaysuptime.text");
/* 2895 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 2896 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 2897 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 2898 */       return true;
/*      */     }
/* 2900 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 2901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2906 */     PageContext pageContext = _jspx_page_context;
/* 2907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2909 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2910 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 2911 */     _jspx_th_fmt_005fmessage_005f12.setParent(null);
/*      */     
/* 2913 */     _jspx_th_fmt_005fmessage_005f12.setKey("am.webclient.common.lastdowntime.text");
/* 2914 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 2915 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 2916 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 2917 */       return true;
/*      */     }
/* 2919 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 2920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2925 */     PageContext pageContext = _jspx_page_context;
/* 2926 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2928 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2929 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2930 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 2932 */     _jspx_th_c_005fout_005f2.setValue("${granularitySelected}");
/* 2933 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2934 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2935 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2936 */       return true;
/*      */     }
/* 2938 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2944 */     PageContext pageContext = _jspx_page_context;
/* 2945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2947 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2948 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 2949 */     _jspx_th_fmt_005fmessage_005f13.setParent(null);
/*      */     
/* 2951 */     _jspx_th_fmt_005fmessage_005f13.setKey("am.webclient.report.mins");
/* 2952 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 2953 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 2954 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 2955 */       return true;
/*      */     }
/* 2957 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 2958 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\CommonDetailsHeader_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */