/*      */ package org.apache.jsp.jsp.as400;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ 
/*      */ public final class spool_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2198 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2202 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2203 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2204 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2205 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2212 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2216 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2219 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2221 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2222 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.release();
/* 2223 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.release();
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2231 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2234 */     JspWriter out = null;
/* 2235 */     Object page = this;
/* 2236 */     JspWriter _jspx_out = null;
/* 2237 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2241 */       response.setContentType("text/html;charset=UTF-8");
/* 2242 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2244 */       _jspx_page_context = pageContext;
/* 2245 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2246 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2247 */       session = pageContext.getSession();
/* 2248 */       out = pageContext.getOut();
/* 2249 */       _jspx_out = out;
/*      */       
/* 2251 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2252 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2254 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2255 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2256 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2258 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2260 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2262 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2264 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2265 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2266 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2267 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2270 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2271 */         String available = null;
/* 2272 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2273 */         out.write(10);
/*      */         
/* 2275 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2276 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2277 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2279 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2281 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2283 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2285 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2286 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2287 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2288 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2291 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2292 */           String unavailable = null;
/* 2293 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2294 */           out.write(10);
/*      */           
/* 2296 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2297 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2298 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2300 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2302 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2304 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2306 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2307 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2308 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2309 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2312 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2313 */             String unmanaged = null;
/* 2314 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2315 */             out.write(10);
/*      */             
/* 2317 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2318 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2319 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2321 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2323 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2325 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2327 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2328 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2329 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2330 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2333 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2334 */               String scheduled = null;
/* 2335 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2336 */               out.write(10);
/*      */               
/* 2338 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2339 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2340 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2342 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2344 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2346 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2348 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2349 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2350 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2351 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2354 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2355 */                 String critical = null;
/* 2356 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2357 */                 out.write(10);
/*      */                 
/* 2359 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2360 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2361 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2363 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2365 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2367 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2369 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2370 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2371 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2372 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2375 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2376 */                   String clear = null;
/* 2377 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2378 */                   out.write(10);
/*      */                   
/* 2380 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2381 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2382 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2384 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2386 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2388 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2390 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2391 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2392 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2393 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2396 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2397 */                     String warning = null;
/* 2398 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2399 */                     out.write(10);
/* 2400 */                     out.write(10);
/*      */                     
/* 2402 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2403 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2405 */                     out.write(10);
/* 2406 */                     out.write(10);
/* 2407 */                     out.write(10);
/* 2408 */                     out.write("\n\n<script language=\"javascript\">\n    checkBoxListener();\n</script>\n");
/*      */                     
/* 2410 */                     String bgcolor = "";
/* 2411 */                     int tc = 0;
/* 2412 */                     String resourceid = request.getParameter("resourceid");
/* 2413 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "&datatype=6");
/* 2414 */                     String SPOOL_CLEAR = (String)request.getAttribute("SPOOL_CLEAR");
/* 2415 */                     String SPOOL_CRITICAL = (String)request.getAttribute("SPOOL_CRITICAL");
/* 2416 */                     String SPOOL_WARNING = (String)request.getAttribute("SPOOL_WARNING");
/*      */                     
/* 2418 */                     ArrayList resIDs = new ArrayList();
/* 2419 */                     resIDs.add(resourceid);
/*      */                     
/* 2421 */                     ArrayList<String> attribIDs = new ArrayList();
/* 2422 */                     for (int i = 2797; i < 2800; i++)
/*      */                     {
/* 2424 */                       attribIDs.add("" + i);
/*      */                     }
/* 2426 */                     attribIDs.add("2707");
/* 2427 */                     attribIDs.add("2708");
/* 2428 */                     attribIDs.add("2706");
/*      */                     
/* 2430 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/* 2432 */                     Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/* 2433 */                     String allowSpl = (String)globals.get("allowSPL");
/* 2434 */                     String allowAs400 = (String)globals.get("allowAS400");
/* 2435 */                     boolean allowAS400 = false;
/* 2436 */                     boolean allowSPL = false;
/*      */                     
/* 2438 */                     out.write(10);
/*      */                     
/* 2440 */                     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2441 */                     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2442 */                     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                     
/* 2444 */                     _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 2445 */                     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2446 */                     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2448 */                         out.write("\n    ");
/*      */                         
/* 2450 */                         if (allowSpl.equals("true"))
/*      */                         {
/* 2452 */                           allowSPL = true;
/*      */                         }
/*      */                         
/* 2455 */                         out.write(10);
/* 2456 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2457 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2461 */                     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2462 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                     }
/*      */                     else {
/* 2465 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2466 */                       out.write(10);
/*      */                       
/* 2468 */                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2469 */                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2470 */                       _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                       
/* 2472 */                       _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2473 */                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2474 */                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                         for (;;) {
/* 2476 */                           out.write("\n    ");
/*      */                           
/* 2478 */                           if ("true".equals(allowAs400))
/*      */                           {
/* 2480 */                             allowAS400 = true;
/*      */                           }
/*      */                           
/* 2483 */                           out.write(10);
/* 2484 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2485 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2489 */                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2490 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                       }
/*      */                       else {
/* 2493 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2494 */                         out.write("\n\n<div style=\"display:none;\" id=\"showoptions\" >\n    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"apmDblClickMenu\" onClick=\"closeDialog();\">\n        ");
/*      */                         
/* 2496 */                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2497 */                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2498 */                         _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */                         
/* 2500 */                         _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,OPERATOR");
/* 2501 */                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2502 */                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                           for (;;) {
/* 2504 */                             out.write("\n            <tr><td class=\"monitorinfoodd-noborder\"><a class='resourcename' href='");
/* 2505 */                             if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                               return;
/* 2507 */                             out.write("'target=_blank>");
/* 2508 */                             if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                               return;
/* 2510 */                             out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a class='resourcename' href='");
/* 2511 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                               return;
/* 2513 */                             out.write("'target=_blank>");
/* 2514 */                             if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                               return;
/* 2516 */                             out.write("</a></td></tr>\n            ");
/*      */                             
/* 2518 */                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2519 */                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2520 */                             _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                             
/* 2522 */                             _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 2523 */                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2524 */                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                               for (;;) {
/* 2526 */                                 out.write("\n                <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#configurespl\").click();'>");
/* 2527 */                                 out.print(ALERTCONFIG_TEXT);
/* 2528 */                                 out.write("</a></td></tr>\n                <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onclick='$(\"#enabledisable\").click();'>");
/* 2529 */                                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                                   return;
/* 2531 */                                 out.write("</a></td></tr>\n            ");
/* 2532 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2533 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2537 */                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2538 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                             }
/*      */                             
/* 2541 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2542 */                             out.write("\n            ");
/* 2543 */                             if ((allowAS400) || (allowSPL)) {
/* 2544 */                               out.write("\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#monitor\").val(\"Delete\").change();'>");
/* 2545 */                               if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                                 return;
/* 2547 */                               out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#monitor\").val(\"Hold\").change();'>");
/* 2548 */                               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                                 return;
/* 2550 */                               out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#monitor\").val(\"Release\").change();'>");
/* 2551 */                               if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                                 return;
/* 2553 */                               out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#monitor\").val(\"MoveToTop\").change();'>");
/* 2554 */                               if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                                 return;
/* 2556 */                               out.write("</a></td></tr>\n            ");
/*      */                             }
/* 2558 */                             out.write("\n        ");
/* 2559 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2560 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2564 */                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2565 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*      */                         }
/*      */                         else {
/* 2568 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2569 */                           out.write("\n    </table>\n</div>\n\n");
/*      */                           
/* 2571 */                           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2572 */                           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2573 */                           _jspx_th_c_005fif_005f0.setParent(null);
/*      */                           
/* 2575 */                           _jspx_th_c_005fif_005f0.setTest("${not disable}");
/* 2576 */                           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2577 */                           if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                             for (;;) {
/* 2579 */                               out.write("\n    <br>\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table-border\" onMouseOver=\"toggledivmo('div1',1)\" onMouseOut=\"toggledivmo('div1',0)\">\n\n        <tr>\n            <td colspan=\"6\" class=\"conf-mon-data-heading bborder\">");
/* 2580 */                               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2582 */                               out.write("</td>\n            <td  colspan=\"1\" align=\"right\" class=\"conf-mon-data-link bborder\">");
/*      */                               
/* 2584 */                               PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2585 */                               _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2586 */                               _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f0);
/*      */                               
/* 2588 */                               _jspx_th_logic_005fpresent_005f4.setRole("ADMIN,DEMO");
/* 2589 */                               int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2590 */                               if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                 for (;;) {
/* 2592 */                                   out.write("<div style=\"visibility: hidden;\" id=\"div1\"> <a href=");
/*      */                                   
/* 2594 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2595 */                                   _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2596 */                                   _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                   
/* 2598 */                                   _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2599 */                                   int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2600 */                                   if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                     for (;;) {
/* 2602 */                                       out.write("\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2603 */                                       out.print(resourceid);
/* 2604 */                                       out.write("&attributeIDs=2797,2798,2799&attributeToSelect=2797&redirectto=");
/* 2605 */                                       out.print(encodeurl);
/* 2606 */                                       out.write(34);
/* 2607 */                                       out.write(32);
/* 2608 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2609 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2613 */                                   if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2614 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                   }
/*      */                                   
/* 2617 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2618 */                                   if (_jspx_meth_logic_005fpresent_005f5(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                                     return;
/* 2620 */                                   out.write(" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" title=\"");
/* 2621 */                                   out.print(ALERTCONFIG_TEXT);
/* 2622 */                                   out.write("\"></a>&nbsp;&nbsp;\n            </div>");
/* 2623 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2624 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2628 */                               if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2629 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                               }
/*      */                               
/* 2632 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2633 */                               out.write("</td>\n        </tr>\n\n        <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n            <td width=\"24%\">\n                <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n                    <tr>\n                        <td width=\"55%\" class=\"monitorinfoeven-conf\" align=\"left\" style=\"padding-left:10px;\">");
/* 2634 */                               out.print(FormatUtil.getString("am.webclient.as400.spoolinclear"));
/* 2635 */                               out.write("</td>\n\n                        ");
/* 2636 */                               if (SPOOL_CLEAR != null) {
/* 2637 */                                 out.write("\n                        <td width=\"20%\" class=\"tooltip\" onmouseover=\"ddrivetip(this,event,'");
/* 2638 */                                 if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2640 */                                 out.write(32);
/* 2641 */                                 if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2643 */                                 out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" align=\"left\"><a onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=spoolFilter&resourceid=");
/* 2644 */                                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2646 */                                 out.write("&status=clear',1050,600,0,0); return false;\" class=\"new-monitordiv-link\" href=\"#\" >");
/* 2647 */                                 out.print(FormatUtil.formatNumber(SPOOL_CLEAR));
/* 2648 */                                 out.write("</a></td>\n                        ");
/*      */                               } else {
/* 2650 */                                 out.write("\n                        <td width=\"20%\" class=\"monitorinfoeven-conf\" align=\"left\">-</td>\n                        ");
/*      */                               }
/* 2652 */                               out.write("\n                        <td  width=\"15%\" class=\"monitorinfoeven-conf\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2653 */                               out.print(resourceid);
/* 2654 */                               out.write("&attributeid=2797')\">");
/* 2655 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2797")));
/* 2656 */                               out.write("</a>\n\n                    </tr>\n                </table>\n            </td>\n\n            <td class=\"monitorinfoeven-conf\" align=\"left\" width=\"1%\" ><img class=\"alarms-filter-arrow\" src=\"/images/sql-dotted-sep.gif\"></td>\n            <td width=\"24%\">\n                <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n                    <tr>\n                        <td width=\"55%\" class=\"monitorinfoeven-conf\" align=\"left\">");
/* 2657 */                               out.print(FormatUtil.getString("am.webclient.as400.spoolincritical"));
/* 2658 */                               out.write("</td>\n\n                        ");
/* 2659 */                               if (SPOOL_CRITICAL != null) {
/* 2660 */                                 out.write("\n                        <td width=\"20%\" class=\"tooltip\" onmouseover=\"ddrivetip(this,event,'");
/* 2661 */                                 if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2663 */                                 out.write(32);
/* 2664 */                                 if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2666 */                                 out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" align=\"left\"><a onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=spoolFilter&resourceid=");
/* 2667 */                                 if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2669 */                                 out.write("&status=critical',1050,600,0,0); return false;\" class=\"new-monitordiv-link\" href=\"#\" >");
/* 2670 */                                 out.print(FormatUtil.formatNumber(SPOOL_CRITICAL));
/* 2671 */                                 out.write("</a></td>\n                        ");
/*      */                               } else {
/* 2673 */                                 out.write("\n                        <td width=\"20%\" class=\"monitorinfoeven-conf\" align=\"left\">-</td>\n                        ");
/*      */                               }
/* 2675 */                               out.write("\n                        <td  width=\"15%\" class=\"monitorinfoeven-conf\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2676 */                               out.print(resourceid);
/* 2677 */                               out.write("&attributeid=2799')\">");
/* 2678 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2799")));
/* 2679 */                               out.write("</a></td>\n\n                    </tr>\n                </table>\n            </td>\n\n            <td class=\"monitorinfoeven-conf\" align=\"left\" width=\"1%\" ><img class=\"alarms-filter-arrow\" src=\"/images/sql-dotted-sep.gif\"></td>\n            <td width=\"24%\">\n                <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n                    <tr>\n                        <td width=\"55%\" class=\"monitorinfoeven-conf\" align=\"left\">");
/* 2680 */                               out.print(FormatUtil.getString("am.webclient.as400.spoolinwarning"));
/* 2681 */                               out.write("</td>\n\n                        ");
/* 2682 */                               if (SPOOL_WARNING != null) {
/* 2683 */                                 out.write("\n                        <td width=\"20%\" class=\"tooltip\" onmouseover=\"ddrivetip(this,event,'");
/* 2684 */                                 if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2686 */                                 out.write(32);
/* 2687 */                                 if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2689 */                                 out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" align=\"left\"><a onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=spoolFilter&resourceid=");
/* 2690 */                                 if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2692 */                                 out.write("&status=warning',1050,600,0,0); return false;\" class=\"new-monitordiv-link\" href=\"#\" >");
/* 2693 */                                 out.print(FormatUtil.formatNumber(SPOOL_WARNING));
/* 2694 */                                 out.write("</a></td>\n                        ");
/*      */                               } else {
/* 2696 */                                 out.write("\n                        <td width=\"20%\" class=\"monitorinfoeven-conf\" align=\"left\">-</td>\n                        ");
/*      */                               }
/* 2698 */                               out.write("\n                        <td width=\"15%\" class=\"monitorinfoeven-conf\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2699 */                               out.print(resourceid);
/* 2700 */                               out.write("&attributeid=2798')\">");
/* 2701 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2798")));
/* 2702 */                               out.write("</a></td>\n\n                    </tr>\n                </table>\n            </td>\n            <td class=\"monitorinfoeven-conf\" align=\"left\" width=\"1%\" ><img class=\"alarms-filter-arrow\" src=\"/images/sql-dotted-sep.gif\"></td>\n            <td width=\"25%\" class=\"monitorinfoeven-conf\" align=\"center\">\n                <table align=\"center\" width =\"60%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\">\n                    <tr>\n\n                        ");
/* 2703 */                               if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2705 */                               out.write("\n                        ");
/* 2706 */                               if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2708 */                               out.write("\n                        ");
/* 2709 */                               if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2711 */                               out.write("\n                    </tr>\n                </table>\n\n                <table align=\"bottom\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    <tr>\n                        <td class=\"clearCount\">");
/* 2712 */                               if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2714 */                               out.write(32);
/* 2715 */                               out.write(45);
/* 2716 */                               out.write(32);
/* 2717 */                               if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2719 */                               out.write("%</td>\n                        <td class=\"criticalCount\">");
/* 2720 */                               if (_jspx_meth_fmt_005fmessage_005f18(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2722 */                               out.write(32);
/* 2723 */                               out.write(45);
/* 2724 */                               out.write(32);
/* 2725 */                               if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2727 */                               out.write("%</td>\n                        <td class=\"warningCount\">");
/* 2728 */                               if (_jspx_meth_fmt_005fmessage_005f19(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2730 */                               out.write(32);
/* 2731 */                               out.write(45);
/* 2732 */                               out.write(32);
/* 2733 */                               if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2735 */                               out.write("%</td>\n                    </tr>\n                </table>\n\n            </td>\n        </tr>\n\n    </table>\n\n");
/* 2736 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2737 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2741 */                           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2742 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                           }
/*      */                           else {
/* 2745 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2746 */                             out.write("\n<br>\n\n");
/*      */                             
/* 2748 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2749 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2750 */                             _jspx_th_c_005fif_005f4.setParent(null);
/*      */                             
/* 2752 */                             _jspx_th_c_005fif_005f4.setTest("${disable}");
/* 2753 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2754 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/* 2756 */                                 out.write("\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  >\n        <tr>\n            <td  class='msg-status-tp-left-corn'></td>\n            <td class='msg-status-top-mid-bg'></td>\n            <td  class='msg-status-tp-right-corn'></td>\n        </tr>\n        <tr>\n            <td class='msg-status-left-bg'>&nbsp;</td>\n            <td  width='98%' class='msg-table-width'>\n                <table cellpadding='0' cellspacing='0' width='98%' border='0'>\n                    <tr>\n                        <td width='3%' class='msg-table-width-bg'>\n                            <img src='../images/icon_message_success.gif' alt='icon' height='20' width='20'></td>\n                        <td height=\"28\" class=\"msg-table-width\">&nbsp;");
/*      */                                 
/* 2758 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2759 */                                 _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2760 */                                 _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f4);
/*      */                                 
/* 2762 */                                 _jspx_th_logic_005fnotPresent_005f1.setRole("ADMIN,DEMO");
/* 2763 */                                 int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2764 */                                 if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                   for (;;)
/*      */                                   {
/* 2767 */                                     org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 2768 */                                     _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2769 */                                     _jspx_th_bean_005fmessage_005f0.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                     
/* 2771 */                                     _jspx_th_bean_005fmessage_005f0.setKey("am.webclient.enable.admin.text");
/*      */                                     
/* 2773 */                                     _jspx_th_bean_005fmessage_005f0.setArg0(FormatUtil.getString("am.webclient.as400innertabs.Spool"));
/* 2774 */                                     int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 2775 */                                     if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 2776 */                                       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0); return;
/*      */                                     }
/*      */                                     
/* 2779 */                                     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 2780 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2781 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2785 */                                 if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2786 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 2789 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2790 */                                 out.write("\n                            ");
/*      */                                 
/* 2792 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2793 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 2794 */                                 _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f4);
/*      */                                 
/* 2796 */                                 _jspx_th_logic_005fpresent_005f6.setRole("ADMIN,DEMO");
/* 2797 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 2798 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;)
/*      */                                   {
/* 2801 */                                     org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f1 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 2802 */                                     _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2803 */                                     _jspx_th_bean_005fmessage_005f1.setParent(_jspx_th_logic_005fpresent_005f6);
/*      */                                     
/* 2805 */                                     _jspx_th_bean_005fmessage_005f1.setKey("am.webclient.enable.text");
/*      */                                     
/* 2807 */                                     _jspx_th_bean_005fmessage_005f1.setArg0(FormatUtil.getString("am.webclient.as400innertabs.Spool"));
/*      */                                     
/* 2809 */                                     _jspx_th_bean_005fmessage_005f1.setArg1(resourceid);
/* 2810 */                                     int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
/* 2811 */                                     if (_jspx_th_bean_005fmessage_005f1.doEndTag() == 5) {
/* 2812 */                                       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1); return;
/*      */                                     }
/*      */                                     
/* 2815 */                                     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
/* 2816 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 2817 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2821 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 2822 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                 }
/*      */                                 
/* 2825 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 2826 */                                 out.write("</td>\n                    </tr>\n                </table>\n            </td>\n            <td class='msg-status-right-bg'></td>\n        </tr>\n        <tr>\n            <td class='msg-status-btm-left-corn'>&nbsp;</td>\n            <td class='msg-status-btm-mid-bg'>&nbsp;</td>\n            <td class='msg-status-btm-right-corn'>&nbsp;</td>\n        </tr>\n    </table>\n");
/* 2827 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2828 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2832 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2833 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*      */                             }
/*      */                             else {
/* 2836 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2837 */                               out.write("\n\n<form name=\"formSpool\" id=\"formSpool\" action=\"/as400.do?method=spoolAction\" method=\"post\">\n\n\n    <input type=\"hidden\" name=\"rowids\" id=\"rowids\" value=\"\">\n    <input type=\"hidden\" name=\"fn\" id=\"fn\" value=\"\">\n    <input type=\"hidden\" name=\"resourceid\" id=\"resourceid\" value=\"");
/* 2838 */                               out.print(resourceid);
/* 2839 */                               out.write("\">\n\n    <table   width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table-border\" onMouseOver=\"$('#div2').css('opacity',1);\" onMouseOut=\"$('#div2').css('opacity',0.5)\">\n        <tr>\n            <td width=\"10%\" class=\"conf-mon-data-heading\" NOWRAP>");
/* 2840 */                               out.print(FormatUtil.getString("am.webclient.as400.spooldetails"));
/* 2841 */                               out.write("</td>\n            <td class=\"conf-mon-data-link\"  align=\"left\">\n                ");
/* 2842 */                               if ((allowAS400) || (allowSPL)) {
/* 2843 */                                 out.write("\n                <table cellpadding=\"0\" cellspacing=\"0\">\n                    <tr>\n                        <td class=\"bodytextbold\">\n                            <select  id=\"monitor\" onchange=\"javascript:fnGetCheckAndSubmit(this)\" onmouseover=\"ddrivetip(this,event,'");
/* 2844 */                                 if (_jspx_meth_fmt_005fmessage_005f20(_jspx_page_context))
/*      */                                   return;
/* 2846 */                                 out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\">\n                                <option value=\"Actions\">");
/* 2847 */                                 if (_jspx_meth_fmt_005fmessage_005f21(_jspx_page_context))
/*      */                                   return;
/* 2849 */                                 out.write("</option>\n                                <option value=\"Delete\">");
/* 2850 */                                 if (_jspx_meth_fmt_005fmessage_005f22(_jspx_page_context))
/*      */                                   return;
/* 2852 */                                 out.write("</option>\n                                <option value=\"Hold\">");
/* 2853 */                                 if (_jspx_meth_fmt_005fmessage_005f23(_jspx_page_context))
/*      */                                   return;
/* 2855 */                                 out.write("</option>\n                                <option value=\"Release\">");
/* 2856 */                                 if (_jspx_meth_fmt_005fmessage_005f24(_jspx_page_context))
/*      */                                   return;
/* 2858 */                                 out.write("</option>\n                                <option value=\"MoveToTop\">");
/* 2859 */                                 if (_jspx_meth_fmt_005fmessage_005f25(_jspx_page_context))
/*      */                                   return;
/* 2861 */                                 out.write("</option>\n                            </select>\n                        </td>\n                    </tr>\n                </table>\n                ");
/*      */                               }
/* 2863 */                               out.write("\n            </td>\n            <td class=\"conf-mon-data-link\" align=\"right\">");
/*      */                               
/* 2865 */                               PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2866 */                               _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 2867 */                               _jspx_th_logic_005fpresent_005f7.setParent(null);
/*      */                               
/* 2869 */                               _jspx_th_logic_005fpresent_005f7.setRole("ADMIN,DEMO");
/* 2870 */                               int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 2871 */                               if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                 for (;;) {
/* 2873 */                                   out.write("<div style=\"opacity: 0.5;\" id=\"div2\" ><img title=\"");
/* 2874 */                                   out.print(ALERTCONFIG_TEXT);
/* 2875 */                                   out.write("\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\"><a id=\"configurespl\" onmouseover=\"ddrivetip(this,event,'");
/* 2876 */                                   if (_jspx_meth_fmt_005fmessage_005f26(_jspx_th_logic_005fpresent_005f7, _jspx_page_context))
/*      */                                     return;
/* 2878 */                                   out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" class=\"new-monitordiv-link\" onClick=");
/*      */                                   
/* 2880 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2881 */                                   _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 2882 */                                   _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_logic_005fpresent_005f7);
/*      */                                   
/* 2884 */                                   _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 2885 */                                   int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 2886 */                                   if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                     for (;;) {
/* 2888 */                                       out.write("\"window.location.href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2889 */                                       out.print(resourceid);
/* 2890 */                                       out.write("&attributeIDs=2708&attributeToSelect=2708&redirectto=");
/* 2891 */                                       out.print(encodeurl);
/* 2892 */                                       out.write(39);
/* 2893 */                                       out.write(34);
/* 2894 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 2895 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2899 */                                   if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 2900 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                   }
/*      */                                   
/* 2903 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 2904 */                                   out.write(32);
/* 2905 */                                   if (_jspx_meth_logic_005fpresent_005f8(_jspx_th_logic_005fpresent_005f7, _jspx_page_context))
/*      */                                     return;
/* 2907 */                                   out.write(32);
/* 2908 */                                   out.write(62);
/* 2909 */                                   out.print(ALERTCONFIG_TEXT);
/* 2910 */                                   out.write("</a><span style=\"color:#b7b7b7;font-family:Arial,Helvetica,Sans-serif;font-size:15px; padding:0px 5px 0px 5px;\">|</span><img title=\"");
/* 2911 */                                   if (_jspx_meth_fmt_005fmessage_005f27(_jspx_th_logic_005fpresent_005f7, _jspx_page_context))
/*      */                                     return;
/* 2913 */                                   out.write("\" src=\"/images/icon_disable.gif\" style=\"position:relative; top:1px; left:1px;\"><a id=\"enabledisable\" onmouseover=\"ddrivetip(this,event,'");
/* 2914 */                                   if (_jspx_meth_fmt_005fmessage_005f28(_jspx_th_logic_005fpresent_005f7, _jspx_page_context))
/*      */                                     return;
/* 2916 */                                   out.write(32);
/* 2917 */                                   if (_jspx_meth_fmt_005fmessage_005f29(_jspx_th_logic_005fpresent_005f7, _jspx_page_context))
/*      */                                     return;
/* 2919 */                                   out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" class=\"new-monitordiv-link\" onclick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=getenabledetails&type=AS400/iSeries&resourceid=");
/* 2920 */                                   if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fpresent_005f7, _jspx_page_context))
/*      */                                     return;
/* 2922 */                                   out.write("',850,400,0,0)\">");
/* 2923 */                                   if (_jspx_meth_fmt_005fmessage_005f30(_jspx_th_logic_005fpresent_005f7, _jspx_page_context))
/*      */                                     return;
/* 2925 */                                   out.write("</a>&nbsp;\n            </div>");
/* 2926 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 2927 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2931 */                               if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 2932 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/*      */                               }
/*      */                               else {
/* 2935 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 2936 */                                 out.write("</td>\n        </tr>\n    </table>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"spoolDetails\" class=\"lrbborder\" onMouseOver=\"$('#div2').css('opacity',1);\" onMouseOut=\"$('#div2').css('opacity',0.5)\" onClick=\"showOptions(this,'showoptions');\">\n\n        <tr>\n            ");
/* 2937 */                                 if ((allowAS400) || (allowSPL)) {
/* 2938 */                                   out.write("\n            <td class=\"monitorinfoodd\" align=\"center\">&nbsp;\n                <input class=\"checkall\" type=\"checkbox\" name=\"spoolSel\" id=\"spoolSel\" onClick=\"javascript:ToggleAll(this,this.form,'checkuncheck');\" align=\"absmiddle\"/><span style=\"padding-left:5px;\"></span></td>\n            ");
/*      */                                 }
/* 2940 */                                 out.write("\n\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2941 */                                 out.print(FormatUtil.getString("am.webclient.as400.spoolname"));
/* 2942 */                                 out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2943 */                                 out.print(FormatUtil.getString("am.webclient.as400.number"));
/* 2944 */                                 out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2945 */                                 out.print(FormatUtil.getString("am.webclient.as400.jobname"));
/* 2946 */                                 out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2947 */                                 out.print(FormatUtil.getString("am.webclient.as400.jobnumber"));
/* 2948 */                                 out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2949 */                                 out.print(FormatUtil.getString("am.webclient.as400.jobowner"));
/* 2950 */                                 out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2951 */                                 out.print(FormatUtil.getString("am.webclient.as400.status"));
/* 2952 */                                 out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2953 */                                 out.print(FormatUtil.getString("am.webclient.as400.printername"));
/* 2954 */                                 out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"right\" width=\"3%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2955 */                                 out.print(resourceid);
/* 2956 */                                 out.write("&attributeid=2708')\">");
/* 2957 */                                 out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2708")));
/* 2958 */                                 out.write("</a></td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2959 */                                 out.print(FormatUtil.getString("am.webclient.as400.totalpages"));
/* 2960 */                                 out.write("</td>\n            <td class=\"monitorinfoodd\" width=\"1%\" align=\"center\"><b>");
/* 2961 */                                 out.print(FormatUtil.getString("am.webclient.as400.view"));
/* 2962 */                                 out.write("</b>&nbsp;</td>\n        </tr>\n\n        ");
/*      */                                 
/* 2964 */                                 HashMap data = (HashMap)request.getAttribute("data");
/* 2965 */                                 if (!data.isEmpty())
/*      */                                 {
/* 2967 */                                   List k = (ArrayList)data.get("spool");
/* 2968 */                                   for (int j = 0; j < k.size(); j++)
/*      */                                   {
/* 2970 */                                     Map p1 = new HashMap();
/* 2971 */                                     p1 = (HashMap)k.get(j);
/*      */                                     
/*      */ 
/*      */ 
/* 2975 */                                     out.write("\n\n        <tr align=\"center\" onmouseout=\"this.className='mondetailsHeader'; toggledivmo('");
/* 2976 */                                     out.print(p1.get("ID"));
/* 2977 */                                     out.write("d',0)\" onmouseover=\"this.className='mondetailsHeaderHover'; toggledivmo('");
/* 2978 */                                     out.print(p1.get("ID"));
/* 2979 */                                     out.write("d',1)\" class=\"mondetailsHeader\">\n            ");
/* 2980 */                                     if ((allowAS400) || (allowSPL)) {
/* 2981 */                                       out.write("\n            <td class=\"monitorinfoodd\" align=\"center\"><input class=\"checkthis\" type=\"checkbox\" name=\"checkuncheck\" value=\"");
/* 2982 */                                       out.print(p1.get("ID"));
/* 2983 */                                       out.write("\" ></td>\n            ");
/*      */                                     }
/* 2985 */                                     out.write("\n\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2986 */                                     out.print(p1.get("NAME"));
/* 2987 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2988 */                                     out.print(p1.get("NUMBER"));
/* 2989 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2990 */                                     out.print(p1.get("JOB_NAME"));
/* 2991 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2992 */                                     out.print(p1.get("JOB_NUMBER"));
/* 2993 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2994 */                                     out.print(p1.get("JOB_OWNER"));
/* 2995 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2996 */                                     out.print(p1.get("STATUS"));
/* 2997 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 2998 */                                     out.print(p1.get("PRINTER_NAME"));
/* 2999 */                                     out.write("&nbsp;</td>\n            <td class=\"monitorinfoodd\">&nbsp;</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 3000 */                                     out.print(p1.get("TOTAL_PAGES"));
/* 3001 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"center\" valign=\"center\"><div style=\"visibility: hidden;\" id=\"");
/* 3002 */                                     out.print(p1.get("ID"));
/* 3003 */                                     out.write("d\" >");
/*      */                                     
/* 3005 */                                     PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3006 */                                     _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3007 */                                     _jspx_th_logic_005fpresent_005f9.setParent(null);
/*      */                                     
/* 3009 */                                     _jspx_th_logic_005fpresent_005f9.setRole("ADMIN,OPERATOR,DEMO");
/* 3010 */                                     int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3011 */                                     if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                       for (;;) {
/* 3013 */                                         out.write("<a href='#' onClick=");
/*      */                                         
/* 3015 */                                         PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3016 */                                         _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3017 */                                         _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_logic_005fpresent_005f9);
/*      */                                         
/* 3019 */                                         _jspx_th_logic_005fpresent_005f10.setRole("ADMIN,OPERATOR");
/* 3020 */                                         int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3021 */                                         if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                           for (;;) {
/* 3023 */                                             out.write("\"viewSpool('");
/* 3024 */                                             out.print(p1.get("ID"));
/* 3025 */                                             out.write(39);
/* 3026 */                                             out.write(44);
/* 3027 */                                             out.write(39);
/* 3028 */                                             if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fpresent_005f10, _jspx_page_context))
/*      */                                               return;
/* 3030 */                                             out.write("'); return false;\"");
/* 3031 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 3032 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3036 */                                         if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 3037 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                                         }
/*      */                                         
/* 3040 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 3041 */                                         if (_jspx_meth_logic_005fpresent_005f11(_jspx_th_logic_005fpresent_005f9, _jspx_page_context))
/*      */                                           return;
/* 3043 */                                         out.write("  class=\"resourcename\" >");
/* 3044 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3045 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3049 */                                     if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3050 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                                     }
/*      */                                     
/* 3053 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3054 */                                     out.write("<img src=\"/images/spool.gif\"  hspace=\"1\" vspace=\"1\" border=\"0\" title=\"");
/* 3055 */                                     if (_jspx_meth_fmt_005fmessage_005f31(_jspx_page_context))
/*      */                                       return;
/* 3057 */                                     out.write("\"></a>\n            </div></td>\n        </tr>\n        ");
/*      */                                     
/* 3059 */                                     tc++;
/*      */                                   }
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3064 */                                   out.write("\n        <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n            <td colspan=\"11\" class=\"monitorinfoodd\" align=\"center\"><b>");
/* 3065 */                                   out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3066 */                                   out.write("</b></td>\n        </tr>\n        ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3070 */                                 out.write("\n\n    </table>\n    ");
/* 3071 */                                 if ((tc > 15) && ((allowAS400) || (allowSPL)))
/*      */                                 {
/*      */ 
/* 3074 */                                   out.write("\n    <table   width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\" >\n        <tr>\n            <td class=\"conf-mon-data-link bodytextbold\">");
/* 3075 */                                   out.print(FormatUtil.getString("am.webclient.as400.spoolaction"));
/* 3076 */                                   out.write("&nbsp;\n                <select id=\"monitor1\" onchange=\"javascript:fnGetCheckAndSubmit(this)\" onmouseover=\"ddrivetip(this,event,'");
/* 3077 */                                   if (_jspx_meth_fmt_005fmessage_005f32(_jspx_page_context))
/*      */                                     return;
/* 3079 */                                   out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\">\n                    <option value=\"Actions\">");
/* 3080 */                                   if (_jspx_meth_fmt_005fmessage_005f33(_jspx_page_context))
/*      */                                     return;
/* 3082 */                                   out.write("</option>\n                    <option value=\"Delete\">");
/* 3083 */                                   if (_jspx_meth_fmt_005fmessage_005f34(_jspx_page_context))
/*      */                                     return;
/* 3085 */                                   out.write("</option>\n                    <option value=\"Hold\">");
/* 3086 */                                   if (_jspx_meth_fmt_005fmessage_005f35(_jspx_page_context))
/*      */                                     return;
/* 3088 */                                   out.write("</option>\n                    <option value=\"Release\">");
/* 3089 */                                   if (_jspx_meth_fmt_005fmessage_005f36(_jspx_page_context))
/*      */                                     return;
/* 3091 */                                   out.write("</option>\n                    <option value=\"MoveToTop\">");
/* 3092 */                                   if (_jspx_meth_fmt_005fmessage_005f37(_jspx_page_context))
/*      */                                     return;
/* 3094 */                                   out.write("</option>\n                </select>\n            </td>\n        </tr>\n    </table>\n    ");
/*      */                                 }
/* 3096 */                                 out.write("\n</form>\n<script language=\"javascript\">\n\n    SORTTABLENAME = 'spoolDetails'; //No I18N\n    var numberOfColumnsToBeSorted = 8;\n    sortables_init(numberOfColumnsToBeSorted,false,false,true);\n\n</script>\n");
/* 3097 */                                 if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */                                   return;
/* 3099 */                                 out.write(10);
/* 3100 */                                 out.write(10);
/* 3101 */                                 out.write(10);
/*      */                               }
/* 3103 */                             } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3104 */         out = _jspx_out;
/* 3105 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3106 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3107 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3110 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3116 */     PageContext pageContext = _jspx_page_context;
/* 3117 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3119 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3120 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3121 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3123 */     _jspx_th_c_005fout_005f0.setValue("${Debug_Info_Spool}");
/* 3124 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3125 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3126 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3127 */       return true;
/*      */     }
/* 3129 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3130 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3135 */     PageContext pageContext = _jspx_page_context;
/* 3136 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3138 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f0 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3139 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3140 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3142 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.as400.debug.info");
/* 3143 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3144 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3145 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3146 */       return true;
/*      */     }
/* 3148 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3149 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3154 */     PageContext pageContext = _jspx_page_context;
/* 3155 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3157 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3158 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3159 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3161 */     _jspx_th_c_005fout_005f1.setValue("${Debug_Info_Spool_Sum}");
/* 3162 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3163 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3164 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3165 */       return true;
/*      */     }
/* 3167 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3168 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3173 */     PageContext pageContext = _jspx_page_context;
/* 3174 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3176 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f1 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3177 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3178 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3180 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.as400.debug.summaryinfo");
/* 3181 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3182 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3183 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3184 */       return true;
/*      */     }
/* 3186 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3192 */     PageContext pageContext = _jspx_page_context;
/* 3193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3195 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f2 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3196 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3197 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3199 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.as400.disable.spool");
/* 3200 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3201 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3202 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3203 */       return true;
/*      */     }
/* 3205 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3206 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3211 */     PageContext pageContext = _jspx_page_context;
/* 3212 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3214 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f3 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3215 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3216 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3218 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.as400.delete");
/* 3219 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3220 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3221 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3222 */       return true;
/*      */     }
/* 3224 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3230 */     PageContext pageContext = _jspx_page_context;
/* 3231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3233 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f4 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3234 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3235 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3237 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.as400.hold");
/* 3238 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3239 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3240 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3241 */       return true;
/*      */     }
/* 3243 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3249 */     PageContext pageContext = _jspx_page_context;
/* 3250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3252 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f5 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3253 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3254 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3256 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.as400.release");
/* 3257 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3258 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3259 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3260 */       return true;
/*      */     }
/* 3262 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3268 */     PageContext pageContext = _jspx_page_context;
/* 3269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3271 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f6 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3272 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3273 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3275 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.as400.movetotop");
/* 3276 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3277 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3278 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3279 */       return true;
/*      */     }
/* 3281 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3282 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3287 */     PageContext pageContext = _jspx_page_context;
/* 3288 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3290 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f7 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3291 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3292 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3294 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.as400.spoolsummary");
/* 3295 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3296 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3297 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3298 */       return true;
/*      */     }
/* 3300 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3301 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3306 */     PageContext pageContext = _jspx_page_context;
/* 3307 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3309 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3310 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3311 */     _jspx_th_logic_005fpresent_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3313 */     _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3314 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3315 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 3317 */         out.write("\"javascript:alertUser();\"");
/* 3318 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3319 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3323 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3324 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3325 */       return true;
/*      */     }
/* 3327 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3328 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3333 */     PageContext pageContext = _jspx_page_context;
/* 3334 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3336 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f8 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3337 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3338 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3340 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.as400.tooltip.view");
/* 3341 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3342 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3343 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3344 */       return true;
/*      */     }
/* 3346 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3347 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3352 */     PageContext pageContext = _jspx_page_context;
/* 3353 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3355 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f9 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3356 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3357 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3359 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.as400.spoolinclear");
/* 3360 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3361 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3362 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3363 */       return true;
/*      */     }
/* 3365 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3366 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3371 */     PageContext pageContext = _jspx_page_context;
/* 3372 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3374 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3375 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3376 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3378 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 3379 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3380 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3381 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3382 */       return true;
/*      */     }
/* 3384 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3390 */     PageContext pageContext = _jspx_page_context;
/* 3391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3393 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f10 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3394 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3395 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3397 */     _jspx_th_fmt_005fmessage_005f10.setKey("am.webclient.as400.tooltip.view");
/* 3398 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 3399 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 3400 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3401 */       return true;
/*      */     }
/* 3403 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3404 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3409 */     PageContext pageContext = _jspx_page_context;
/* 3410 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3412 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f11 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3413 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 3414 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3416 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.webclient.as400.spoolincritical");
/* 3417 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 3418 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 3419 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3420 */       return true;
/*      */     }
/* 3422 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3423 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3428 */     PageContext pageContext = _jspx_page_context;
/* 3429 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3431 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3432 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3433 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3435 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 3436 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3437 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3438 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3439 */       return true;
/*      */     }
/* 3441 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3447 */     PageContext pageContext = _jspx_page_context;
/* 3448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3450 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f12 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3451 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 3452 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3454 */     _jspx_th_fmt_005fmessage_005f12.setKey("am.webclient.as400.tooltip.view");
/* 3455 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 3456 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 3457 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3458 */       return true;
/*      */     }
/* 3460 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3466 */     PageContext pageContext = _jspx_page_context;
/* 3467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3469 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f13 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3470 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 3471 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3473 */     _jspx_th_fmt_005fmessage_005f13.setKey("am.webclient.as400.spoolinwarning");
/* 3474 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 3475 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 3476 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3477 */       return true;
/*      */     }
/* 3479 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3480 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3485 */     PageContext pageContext = _jspx_page_context;
/* 3486 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3488 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3489 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3490 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3492 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 3493 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3494 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3495 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3496 */       return true;
/*      */     }
/* 3498 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3504 */     PageContext pageContext = _jspx_page_context;
/* 3505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3507 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3508 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3509 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3511 */     _jspx_th_c_005fif_005f1.setTest("${SPOOL_CLEAR gt 0}");
/* 3512 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3513 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3515 */         out.write("\n                            <td class=\"clearbar\" style=\"cursor: pointer;\" width=\"");
/* 3516 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3517 */           return true;
/* 3518 */         out.write("%\" title=\"");
/* 3519 */         if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3520 */           return true;
/* 3521 */         out.write(32);
/* 3522 */         out.write(45);
/* 3523 */         out.write(32);
/* 3524 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3525 */           return true;
/* 3526 */         out.write("%\" onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=spoolFilter&resourceid=");
/* 3527 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3528 */           return true;
/* 3529 */         out.write("&status=clear',1050,600,0,0); return false;\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n                        ");
/* 3530 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3531 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3535 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3536 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3537 */       return true;
/*      */     }
/* 3539 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3540 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3545 */     PageContext pageContext = _jspx_page_context;
/* 3546 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3548 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3549 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3550 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3552 */     _jspx_th_c_005fout_005f5.setValue("${SPOOL_CLEAR_PER}");
/* 3553 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3554 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3555 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3556 */       return true;
/*      */     }
/* 3558 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3564 */     PageContext pageContext = _jspx_page_context;
/* 3565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3567 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f14 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3568 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 3569 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3571 */     _jspx_th_fmt_005fmessage_005f14.setKey("am.webclient.as400.spoolinclear");
/* 3572 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 3573 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 3574 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3575 */       return true;
/*      */     }
/* 3577 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3578 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3583 */     PageContext pageContext = _jspx_page_context;
/* 3584 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3586 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3587 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3588 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3590 */     _jspx_th_c_005fout_005f6.setValue("${SPOOL_CLEAR_PER}");
/* 3591 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3592 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3593 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3594 */       return true;
/*      */     }
/* 3596 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3602 */     PageContext pageContext = _jspx_page_context;
/* 3603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3605 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3606 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3607 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3609 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 3610 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3611 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3612 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3613 */       return true;
/*      */     }
/* 3615 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3621 */     PageContext pageContext = _jspx_page_context;
/* 3622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3624 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3625 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3626 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3628 */     _jspx_th_c_005fif_005f2.setTest("${SPOOL_CRITICAL gt 0}");
/* 3629 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3630 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3632 */         out.write("\n                            <td class=\"criticalbar\" style=\"cursor: pointer;\" width=\"");
/* 3633 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3634 */           return true;
/* 3635 */         out.write("%\" title=\"");
/* 3636 */         if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3637 */           return true;
/* 3638 */         out.write(32);
/* 3639 */         out.write(45);
/* 3640 */         out.write(32);
/* 3641 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3642 */           return true;
/* 3643 */         out.write("%\" onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=spoolFilter&resourceid=");
/* 3644 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3645 */           return true;
/* 3646 */         out.write("&status=critical',1050,600,0,0); return false;\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n                        ");
/* 3647 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3648 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3652 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3653 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3654 */       return true;
/*      */     }
/* 3656 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3657 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3662 */     PageContext pageContext = _jspx_page_context;
/* 3663 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3665 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3666 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3667 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3669 */     _jspx_th_c_005fout_005f8.setValue("${SPOOL_CRITICAL_PER}");
/* 3670 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3671 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3672 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3673 */       return true;
/*      */     }
/* 3675 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3676 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3681 */     PageContext pageContext = _jspx_page_context;
/* 3682 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3684 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f15 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3685 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 3686 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3688 */     _jspx_th_fmt_005fmessage_005f15.setKey("am.webclient.as400.spoolincritical");
/* 3689 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 3690 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 3691 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3692 */       return true;
/*      */     }
/* 3694 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3695 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3700 */     PageContext pageContext = _jspx_page_context;
/* 3701 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3703 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3704 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3705 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3707 */     _jspx_th_c_005fout_005f9.setValue("${SPOOL_CRITICAL_PER}");
/* 3708 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3709 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3710 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3711 */       return true;
/*      */     }
/* 3713 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3719 */     PageContext pageContext = _jspx_page_context;
/* 3720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3722 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3723 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3724 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3726 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 3727 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3728 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3729 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3730 */       return true;
/*      */     }
/* 3732 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3733 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3738 */     PageContext pageContext = _jspx_page_context;
/* 3739 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3741 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3742 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3743 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3745 */     _jspx_th_c_005fif_005f3.setTest("${SPOOL_WARNING gt 0}");
/* 3746 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3747 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3749 */         out.write("\n                            <td class=\"warningbar\" style=\"cursor: pointer;\" width=\"");
/* 3750 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3751 */           return true;
/* 3752 */         out.write("%\" title=\"");
/* 3753 */         if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3754 */           return true;
/* 3755 */         out.write(32);
/* 3756 */         out.write(45);
/* 3757 */         out.write(32);
/* 3758 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3759 */           return true;
/* 3760 */         out.write("%\" onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=spoolFilter&resourceid=");
/* 3761 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3762 */           return true;
/* 3763 */         out.write("&status=warning',1050,600,0,0); return false;\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n                        ");
/* 3764 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3765 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3769 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3770 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3771 */       return true;
/*      */     }
/* 3773 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3779 */     PageContext pageContext = _jspx_page_context;
/* 3780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3782 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3783 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3784 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3786 */     _jspx_th_c_005fout_005f11.setValue("${SPOOL_WARNING_PER}");
/* 3787 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3788 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3789 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3790 */       return true;
/*      */     }
/* 3792 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3798 */     PageContext pageContext = _jspx_page_context;
/* 3799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3801 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f16 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3802 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 3803 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3805 */     _jspx_th_fmt_005fmessage_005f16.setKey("am.webclient.as400.spoolinwarning");
/* 3806 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 3807 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 3808 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3809 */       return true;
/*      */     }
/* 3811 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3817 */     PageContext pageContext = _jspx_page_context;
/* 3818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3820 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3821 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3822 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3824 */     _jspx_th_c_005fout_005f12.setValue("${SPOOL_WARNING_PER}");
/* 3825 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3826 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3827 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3828 */       return true;
/*      */     }
/* 3830 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3836 */     PageContext pageContext = _jspx_page_context;
/* 3837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3839 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3840 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 3841 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3843 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 3844 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 3845 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 3846 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3847 */       return true;
/*      */     }
/* 3849 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3855 */     PageContext pageContext = _jspx_page_context;
/* 3856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3858 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f17 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3859 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 3860 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3862 */     _jspx_th_fmt_005fmessage_005f17.setKey("Clear");
/* 3863 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 3864 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 3865 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3866 */       return true;
/*      */     }
/* 3868 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3874 */     PageContext pageContext = _jspx_page_context;
/* 3875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3877 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3878 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 3879 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3881 */     _jspx_th_c_005fout_005f14.setValue("${SPOOL_CLEAR_PER}");
/* 3882 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 3883 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 3884 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3885 */       return true;
/*      */     }
/* 3887 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3893 */     PageContext pageContext = _jspx_page_context;
/* 3894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3896 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f18 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3897 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 3898 */     _jspx_th_fmt_005fmessage_005f18.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3900 */     _jspx_th_fmt_005fmessage_005f18.setKey("Critical");
/* 3901 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 3902 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 3903 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 3904 */       return true;
/*      */     }
/* 3906 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 3907 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3912 */     PageContext pageContext = _jspx_page_context;
/* 3913 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3915 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3916 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 3917 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3919 */     _jspx_th_c_005fout_005f15.setValue("${SPOOL_CRITICAL_PER}");
/* 3920 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 3921 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 3922 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3923 */       return true;
/*      */     }
/* 3925 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3931 */     PageContext pageContext = _jspx_page_context;
/* 3932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3934 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f19 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3935 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 3936 */     _jspx_th_fmt_005fmessage_005f19.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3938 */     _jspx_th_fmt_005fmessage_005f19.setKey("Warning");
/* 3939 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 3940 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 3941 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 3942 */       return true;
/*      */     }
/* 3944 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 3945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3950 */     PageContext pageContext = _jspx_page_context;
/* 3951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3953 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3954 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 3955 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3957 */     _jspx_th_c_005fout_005f16.setValue("${SPOOL_WARNING_PER}");
/* 3958 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 3959 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 3960 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3961 */       return true;
/*      */     }
/* 3963 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3969 */     PageContext pageContext = _jspx_page_context;
/* 3970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3972 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f20 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3973 */     _jspx_th_fmt_005fmessage_005f20.setPageContext(_jspx_page_context);
/* 3974 */     _jspx_th_fmt_005fmessage_005f20.setParent(null);
/*      */     
/* 3976 */     _jspx_th_fmt_005fmessage_005f20.setKey("am.webclient.as400.tooltip.actions");
/* 3977 */     int _jspx_eval_fmt_005fmessage_005f20 = _jspx_th_fmt_005fmessage_005f20.doStartTag();
/* 3978 */     if (_jspx_th_fmt_005fmessage_005f20.doEndTag() == 5) {
/* 3979 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 3980 */       return true;
/*      */     }
/* 3982 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 3983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f21(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3988 */     PageContext pageContext = _jspx_page_context;
/* 3989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3991 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f21 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3992 */     _jspx_th_fmt_005fmessage_005f21.setPageContext(_jspx_page_context);
/* 3993 */     _jspx_th_fmt_005fmessage_005f21.setParent(null);
/*      */     
/* 3995 */     _jspx_th_fmt_005fmessage_005f21.setKey("am.webclient.as400.actions");
/* 3996 */     int _jspx_eval_fmt_005fmessage_005f21 = _jspx_th_fmt_005fmessage_005f21.doStartTag();
/* 3997 */     if (_jspx_th_fmt_005fmessage_005f21.doEndTag() == 5) {
/* 3998 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 3999 */       return true;
/*      */     }
/* 4001 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 4002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f22(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4007 */     PageContext pageContext = _jspx_page_context;
/* 4008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4010 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f22 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4011 */     _jspx_th_fmt_005fmessage_005f22.setPageContext(_jspx_page_context);
/* 4012 */     _jspx_th_fmt_005fmessage_005f22.setParent(null);
/*      */     
/* 4014 */     _jspx_th_fmt_005fmessage_005f22.setKey("am.webclient.as400.delete");
/* 4015 */     int _jspx_eval_fmt_005fmessage_005f22 = _jspx_th_fmt_005fmessage_005f22.doStartTag();
/* 4016 */     if (_jspx_th_fmt_005fmessage_005f22.doEndTag() == 5) {
/* 4017 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 4018 */       return true;
/*      */     }
/* 4020 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 4021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f23(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4026 */     PageContext pageContext = _jspx_page_context;
/* 4027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4029 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f23 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4030 */     _jspx_th_fmt_005fmessage_005f23.setPageContext(_jspx_page_context);
/* 4031 */     _jspx_th_fmt_005fmessage_005f23.setParent(null);
/*      */     
/* 4033 */     _jspx_th_fmt_005fmessage_005f23.setKey("am.webclient.as400.hold");
/* 4034 */     int _jspx_eval_fmt_005fmessage_005f23 = _jspx_th_fmt_005fmessage_005f23.doStartTag();
/* 4035 */     if (_jspx_th_fmt_005fmessage_005f23.doEndTag() == 5) {
/* 4036 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 4037 */       return true;
/*      */     }
/* 4039 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 4040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f24(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4045 */     PageContext pageContext = _jspx_page_context;
/* 4046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4048 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f24 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4049 */     _jspx_th_fmt_005fmessage_005f24.setPageContext(_jspx_page_context);
/* 4050 */     _jspx_th_fmt_005fmessage_005f24.setParent(null);
/*      */     
/* 4052 */     _jspx_th_fmt_005fmessage_005f24.setKey("am.webclient.as400.release");
/* 4053 */     int _jspx_eval_fmt_005fmessage_005f24 = _jspx_th_fmt_005fmessage_005f24.doStartTag();
/* 4054 */     if (_jspx_th_fmt_005fmessage_005f24.doEndTag() == 5) {
/* 4055 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 4056 */       return true;
/*      */     }
/* 4058 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 4059 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f25(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4064 */     PageContext pageContext = _jspx_page_context;
/* 4065 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4067 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f25 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4068 */     _jspx_th_fmt_005fmessage_005f25.setPageContext(_jspx_page_context);
/* 4069 */     _jspx_th_fmt_005fmessage_005f25.setParent(null);
/*      */     
/* 4071 */     _jspx_th_fmt_005fmessage_005f25.setKey("am.webclient.as400.movetotop");
/* 4072 */     int _jspx_eval_fmt_005fmessage_005f25 = _jspx_th_fmt_005fmessage_005f25.doStartTag();
/* 4073 */     if (_jspx_th_fmt_005fmessage_005f25.doEndTag() == 5) {
/* 4074 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 4075 */       return true;
/*      */     }
/* 4077 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 4078 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f26(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4083 */     PageContext pageContext = _jspx_page_context;
/* 4084 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4086 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f26 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4087 */     _jspx_th_fmt_005fmessage_005f26.setPageContext(_jspx_page_context);
/* 4088 */     _jspx_th_fmt_005fmessage_005f26.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 4090 */     _jspx_th_fmt_005fmessage_005f26.setKey("am.webclient.as400.tooltip.configure");
/* 4091 */     int _jspx_eval_fmt_005fmessage_005f26 = _jspx_th_fmt_005fmessage_005f26.doStartTag();
/* 4092 */     if (_jspx_th_fmt_005fmessage_005f26.doEndTag() == 5) {
/* 4093 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 4094 */       return true;
/*      */     }
/* 4096 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 4097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f8(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4102 */     PageContext pageContext = _jspx_page_context;
/* 4103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4105 */     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4106 */     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 4107 */     _jspx_th_logic_005fpresent_005f8.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 4109 */     _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 4110 */     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 4111 */     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */       for (;;) {
/* 4113 */         out.write("\"javascript:alertUser();\"");
/* 4114 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 4115 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4119 */     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 4120 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4121 */       return true;
/*      */     }
/* 4123 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4124 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f27(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4129 */     PageContext pageContext = _jspx_page_context;
/* 4130 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4132 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f27 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4133 */     _jspx_th_fmt_005fmessage_005f27.setPageContext(_jspx_page_context);
/* 4134 */     _jspx_th_fmt_005fmessage_005f27.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 4136 */     _jspx_th_fmt_005fmessage_005f27.setKey("am.webclient.as400.disable.spool");
/* 4137 */     int _jspx_eval_fmt_005fmessage_005f27 = _jspx_th_fmt_005fmessage_005f27.doStartTag();
/* 4138 */     if (_jspx_th_fmt_005fmessage_005f27.doEndTag() == 5) {
/* 4139 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 4140 */       return true;
/*      */     }
/* 4142 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 4143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f28(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4148 */     PageContext pageContext = _jspx_page_context;
/* 4149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4151 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f28 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4152 */     _jspx_th_fmt_005fmessage_005f28.setPageContext(_jspx_page_context);
/* 4153 */     _jspx_th_fmt_005fmessage_005f28.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 4155 */     _jspx_th_fmt_005fmessage_005f28.setKey("am.webclient.as400.tooltip");
/* 4156 */     int _jspx_eval_fmt_005fmessage_005f28 = _jspx_th_fmt_005fmessage_005f28.doStartTag();
/* 4157 */     if (_jspx_th_fmt_005fmessage_005f28.doEndTag() == 5) {
/* 4158 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 4159 */       return true;
/*      */     }
/* 4161 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 4162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f29(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4167 */     PageContext pageContext = _jspx_page_context;
/* 4168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4170 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f29 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4171 */     _jspx_th_fmt_005fmessage_005f29.setPageContext(_jspx_page_context);
/* 4172 */     _jspx_th_fmt_005fmessage_005f29.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 4174 */     _jspx_th_fmt_005fmessage_005f29.setKey("am.webclient.as400.disable.spool");
/* 4175 */     int _jspx_eval_fmt_005fmessage_005f29 = _jspx_th_fmt_005fmessage_005f29.doStartTag();
/* 4176 */     if (_jspx_th_fmt_005fmessage_005f29.doEndTag() == 5) {
/* 4177 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 4178 */       return true;
/*      */     }
/* 4180 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 4181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4186 */     PageContext pageContext = _jspx_page_context;
/* 4187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4189 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4190 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4191 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 4193 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 4194 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4195 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4196 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4197 */       return true;
/*      */     }
/* 4199 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f30(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4205 */     PageContext pageContext = _jspx_page_context;
/* 4206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4208 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f30 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4209 */     _jspx_th_fmt_005fmessage_005f30.setPageContext(_jspx_page_context);
/* 4210 */     _jspx_th_fmt_005fmessage_005f30.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 4212 */     _jspx_th_fmt_005fmessage_005f30.setKey("am.webclient.as400.disable.spool");
/* 4213 */     int _jspx_eval_fmt_005fmessage_005f30 = _jspx_th_fmt_005fmessage_005f30.doStartTag();
/* 4214 */     if (_jspx_th_fmt_005fmessage_005f30.doEndTag() == 5) {
/* 4215 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 4216 */       return true;
/*      */     }
/* 4218 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 4219 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fpresent_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4224 */     PageContext pageContext = _jspx_page_context;
/* 4225 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4227 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4228 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4229 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fpresent_005f10);
/*      */     
/* 4231 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 4232 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4233 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4234 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4235 */       return true;
/*      */     }
/* 4237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f11(JspTag _jspx_th_logic_005fpresent_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4243 */     PageContext pageContext = _jspx_page_context;
/* 4244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4246 */     PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4247 */     _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 4248 */     _jspx_th_logic_005fpresent_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f9);
/*      */     
/* 4250 */     _jspx_th_logic_005fpresent_005f11.setRole("DEMO");
/* 4251 */     int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 4252 */     if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */       for (;;) {
/* 4254 */         out.write("\"alertUser();\"");
/* 4255 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 4256 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4260 */     if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 4261 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4262 */       return true;
/*      */     }
/* 4264 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4265 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f31(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4270 */     PageContext pageContext = _jspx_page_context;
/* 4271 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4273 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f31 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4274 */     _jspx_th_fmt_005fmessage_005f31.setPageContext(_jspx_page_context);
/* 4275 */     _jspx_th_fmt_005fmessage_005f31.setParent(null);
/*      */     
/* 4277 */     _jspx_th_fmt_005fmessage_005f31.setKey("am.webclient.as400.spoolfileviewer");
/* 4278 */     int _jspx_eval_fmt_005fmessage_005f31 = _jspx_th_fmt_005fmessage_005f31.doStartTag();
/* 4279 */     if (_jspx_th_fmt_005fmessage_005f31.doEndTag() == 5) {
/* 4280 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 4281 */       return true;
/*      */     }
/* 4283 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 4284 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f32(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4289 */     PageContext pageContext = _jspx_page_context;
/* 4290 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4292 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f32 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4293 */     _jspx_th_fmt_005fmessage_005f32.setPageContext(_jspx_page_context);
/* 4294 */     _jspx_th_fmt_005fmessage_005f32.setParent(null);
/*      */     
/* 4296 */     _jspx_th_fmt_005fmessage_005f32.setKey("am.webclient.as400.tooltip.actions");
/* 4297 */     int _jspx_eval_fmt_005fmessage_005f32 = _jspx_th_fmt_005fmessage_005f32.doStartTag();
/* 4298 */     if (_jspx_th_fmt_005fmessage_005f32.doEndTag() == 5) {
/* 4299 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 4300 */       return true;
/*      */     }
/* 4302 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 4303 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f33(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4308 */     PageContext pageContext = _jspx_page_context;
/* 4309 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4311 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f33 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4312 */     _jspx_th_fmt_005fmessage_005f33.setPageContext(_jspx_page_context);
/* 4313 */     _jspx_th_fmt_005fmessage_005f33.setParent(null);
/*      */     
/* 4315 */     _jspx_th_fmt_005fmessage_005f33.setKey("am.webclient.as400.actions");
/* 4316 */     int _jspx_eval_fmt_005fmessage_005f33 = _jspx_th_fmt_005fmessage_005f33.doStartTag();
/* 4317 */     if (_jspx_th_fmt_005fmessage_005f33.doEndTag() == 5) {
/* 4318 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f33);
/* 4319 */       return true;
/*      */     }
/* 4321 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f33);
/* 4322 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f34(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4327 */     PageContext pageContext = _jspx_page_context;
/* 4328 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4330 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f34 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4331 */     _jspx_th_fmt_005fmessage_005f34.setPageContext(_jspx_page_context);
/* 4332 */     _jspx_th_fmt_005fmessage_005f34.setParent(null);
/*      */     
/* 4334 */     _jspx_th_fmt_005fmessage_005f34.setKey("am.webclient.as400.delete");
/* 4335 */     int _jspx_eval_fmt_005fmessage_005f34 = _jspx_th_fmt_005fmessage_005f34.doStartTag();
/* 4336 */     if (_jspx_th_fmt_005fmessage_005f34.doEndTag() == 5) {
/* 4337 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f34);
/* 4338 */       return true;
/*      */     }
/* 4340 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f34);
/* 4341 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f35(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4346 */     PageContext pageContext = _jspx_page_context;
/* 4347 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4349 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f35 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4350 */     _jspx_th_fmt_005fmessage_005f35.setPageContext(_jspx_page_context);
/* 4351 */     _jspx_th_fmt_005fmessage_005f35.setParent(null);
/*      */     
/* 4353 */     _jspx_th_fmt_005fmessage_005f35.setKey("am.webclient.as400.hold");
/* 4354 */     int _jspx_eval_fmt_005fmessage_005f35 = _jspx_th_fmt_005fmessage_005f35.doStartTag();
/* 4355 */     if (_jspx_th_fmt_005fmessage_005f35.doEndTag() == 5) {
/* 4356 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f35);
/* 4357 */       return true;
/*      */     }
/* 4359 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f35);
/* 4360 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f36(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4365 */     PageContext pageContext = _jspx_page_context;
/* 4366 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4368 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f36 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4369 */     _jspx_th_fmt_005fmessage_005f36.setPageContext(_jspx_page_context);
/* 4370 */     _jspx_th_fmt_005fmessage_005f36.setParent(null);
/*      */     
/* 4372 */     _jspx_th_fmt_005fmessage_005f36.setKey("am.webclient.as400.release");
/* 4373 */     int _jspx_eval_fmt_005fmessage_005f36 = _jspx_th_fmt_005fmessage_005f36.doStartTag();
/* 4374 */     if (_jspx_th_fmt_005fmessage_005f36.doEndTag() == 5) {
/* 4375 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f36);
/* 4376 */       return true;
/*      */     }
/* 4378 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f36);
/* 4379 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f37(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4384 */     PageContext pageContext = _jspx_page_context;
/* 4385 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4387 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f37 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4388 */     _jspx_th_fmt_005fmessage_005f37.setPageContext(_jspx_page_context);
/* 4389 */     _jspx_th_fmt_005fmessage_005f37.setParent(null);
/*      */     
/* 4391 */     _jspx_th_fmt_005fmessage_005f37.setKey("am.webclient.as400.movetotop");
/* 4392 */     int _jspx_eval_fmt_005fmessage_005f37 = _jspx_th_fmt_005fmessage_005f37.doStartTag();
/* 4393 */     if (_jspx_th_fmt_005fmessage_005f37.doEndTag() == 5) {
/* 4394 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f37);
/* 4395 */       return true;
/*      */     }
/* 4397 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f37);
/* 4398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4403 */     PageContext pageContext = _jspx_page_context;
/* 4404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4406 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 4407 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4408 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 4410 */     _jspx_th_c_005fset_005f0.setVar("datatype");
/*      */     
/* 4412 */     _jspx_th_c_005fset_005f0.setValue("6");
/*      */     
/* 4414 */     _jspx_th_c_005fset_005f0.setScope("session");
/* 4415 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4416 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4417 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4418 */       return true;
/*      */     }
/* 4420 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4421 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\as400\spool_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */