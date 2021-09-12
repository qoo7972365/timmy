/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.tags.FormatTag;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.BarChart;
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
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class ShowEJBDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  664 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
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
/*  836 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/* 1470 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/* 1981 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/*      */ 
/*      */ 
/*      */   private String getBeanType(String Type)
/*      */   {
/* 2178 */     String beanType = Type;
/* 2179 */     String value = null;
/* 2180 */     if (beanType.equals("ENTITY"))
/* 2181 */       value = "<image src='/images/icon_ejb_entity.gif' alt='Entity Bean' >";
/* 2182 */     if (beanType.equals("STATELESS_SESSION"))
/* 2183 */       value = "<image src='/images/icon_ejb_stateless.gif' alt='Stateless Session Bean'>";
/* 2184 */     if (beanType.equals("STATEFUL_SESSION"))
/* 2185 */       value = "<image src='/images/icon_ejb_stateful.gif' alt='Statefull Session Bean'>";
/* 2186 */     if (beanType.equals("MESSAGE_DRIVEN")) {
/* 2187 */       value = "<image src='/images/icon_ejb_mbean.gif' alt='Message Driven Bean'>";
/*      */     }
/* 2189 */     return value;
/*      */   }
/*      */   
/*      */   private String getEarRemoved(String Name)
/*      */   {
/* 2194 */     StringTokenizer tokens = new StringTokenizer(Name, ".");
/* 2195 */     return tokens.nextToken();
/*      */   }
/*      */   
/*      */ 
/* 2199 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2205 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2206 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2222 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2235 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2240 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.release();
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/* 2246 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2253 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2256 */     JspWriter out = null;
/* 2257 */     Object page = this;
/* 2258 */     JspWriter _jspx_out = null;
/* 2259 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2263 */       response.setContentType("text/html;charset=UTF-8");
/* 2264 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2266 */       _jspx_page_context = pageContext;
/* 2267 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2268 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2269 */       session = pageContext.getSession();
/* 2270 */       out = pageContext.getOut();
/* 2271 */       _jspx_out = out;
/*      */       
/* 2273 */       out.write("<!--$Id$-->\n\n\n\n");
/* 2274 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2276 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2277 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2279 */       out.write(10);
/* 2280 */       out.write(10);
/* 2281 */       out.write(10);
/* 2282 */       out.write(10);
/* 2283 */       out.write(32);
/*      */       
/* 2285 */       request.setAttribute("HelpKey", "Monitors WebLogic EJB Details");
/*      */       
/* 2287 */       out.write(10);
/* 2288 */       GetWLSGraph wlsGraph = null;
/* 2289 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2290 */       if (wlsGraph == null) {
/* 2291 */         wlsGraph = new GetWLSGraph();
/* 2292 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2294 */       out.write(10);
/* 2295 */       GetDataFromDB dataHandler = null;
/* 2296 */       dataHandler = (GetDataFromDB)_jspx_page_context.getAttribute("dataHandler", 1);
/* 2297 */       if (dataHandler == null) {
/* 2298 */         dataHandler = new GetDataFromDB();
/* 2299 */         _jspx_page_context.setAttribute("dataHandler", dataHandler, 1);
/*      */       }
/* 2301 */       out.write("\n\n\n\n\n\n\n\n");
/*      */       
/* 2303 */       String encodeurl = URLEncoder.encode("/showEJB.do?method=getEJBData&type=WEBLOGIC-server&" + request.getQueryString());
/* 2304 */       ArrayList attribIDs = new ArrayList();
/* 2305 */       ArrayList resIDs = new ArrayList();
/* 2306 */       attribIDs.add("225");
/* 2307 */       String redirect_resid = request.getParameter("resourceid");
/* 2308 */       String redirect_to = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + redirect_resid);
/* 2309 */       String xaxis_ejb = FormatUtil.getString("am.webclient.weblogic.ejb.text");
/* 2310 */       String yaxis_beans = FormatUtil.getString("am.webclient.weblogic.beansinuse.text");
/* 2311 */       String yaxis_cachedbeans = FormatUtil.getString("am.webclient.weblogic.cachedbeanscount.text");
/* 2312 */       String yaxis_transactionbeans = FormatUtil.getString("am.webclient.weblogic.transactionscount.text");
/*      */       
/* 2314 */       out.write("\n\n\n\n");
/* 2315 */       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */         return;
/* 2317 */       out.write(10);
/* 2318 */       out.write(10);
/* 2319 */       out.write(10);
/*      */       
/* 2321 */       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2322 */       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2323 */       _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */       
/* 2325 */       _jspx_th_logic_005fnotEmpty_005f0.setName("data");
/* 2326 */       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2327 */       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */         for (;;) {
/* 2329 */           out.write(10);
/* 2330 */           out.write(10);
/*      */           
/* 2332 */           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2333 */           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2334 */           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/* 2336 */           _jspx_th_logic_005fiterate_005f0.setName("data");
/*      */           
/* 2338 */           _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */           
/* 2340 */           _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */           
/* 2342 */           _jspx_th_logic_005fiterate_005f0.setType("java.util.Properties");
/* 2343 */           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2344 */           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2345 */             Properties row = null;
/* 2346 */             Integer i = null;
/* 2347 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2348 */               out = _jspx_page_context.pushBody();
/* 2349 */               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2350 */               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */             }
/* 2352 */             row = (Properties)_jspx_page_context.findAttribute("row");
/* 2353 */             i = (Integer)_jspx_page_context.findAttribute("i");
/*      */             for (;;) {
/* 2355 */               out.write(10);
/*      */               
/* 2357 */               resIDs.add(row.getProperty("ID"));
/*      */               
/* 2359 */               out.write(10);
/* 2360 */               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2361 */               row = (Properties)_jspx_page_context.findAttribute("row");
/* 2362 */               i = (Integer)_jspx_page_context.findAttribute("i");
/* 2363 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2366 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2367 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2370 */           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2371 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */           }
/*      */           
/* 2374 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2375 */           out.write(10);
/* 2376 */           out.write(10);
/*      */           
/* 2378 */           Properties alert = getStatus(resIDs, attribIDs);
/*      */           
/* 2380 */           out.write("\n\n<!--table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n  <tr> \n    <td width=\"35%\" height=\"31\"> <span class=\"bodytextbold\">EJB Details for <span class=\"names\">");
/* 2381 */           if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */             return;
/* 2383 */           out.write("</span> \n      </span></td>\n  </tr>\n</table-->\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr> \n    <td width=\"72%\" height=\"26\" class=\"tableheadingtrans\">");
/* 2384 */           out.print(FormatUtil.getString("am.webclient.weblogic.ejbdetails.text"));
/* 2385 */           out.write("</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrborder\">\n  <tr> \n    <td width=\"5%\" class=\"columnheading\">");
/* 2386 */           out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2387 */           out.write("</td>\n    <td width=\"5%\" class=\"columnheading\">");
/* 2388 */           out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2389 */           out.write("</td>\n    <td width=\"6%\" class=\"columnheading\">");
/* 2390 */           out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2391 */           out.write("</td>\n    <td width=\"8%\" class=\"columnheading\">");
/* 2392 */           out.print(FormatUtil.getString("am.webclient.weblogic.activation.text"));
/* 2393 */           out.write(" </td>\n    <td width=\"8%\" align=\"center\" class=\"columnheading\">");
/* 2394 */           out.print(FormatUtil.getString("am.webclient.weblogic.passivation.text"));
/* 2395 */           out.write(" </td>\n    <td width=\"8%\" align=\"center\" class=\"columnheadingrightborder\">");
/* 2396 */           out.print(FormatUtil.getString("am.webclient.weblogic.threadswaiting.text"));
/* 2397 */           out.write("</td>\n    <td colspan=\"3\" align=\"center\" class=\"columnheadingrightborder\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr align=\"center\"> \n          <td height=\"19\" colspan=\"3\" class=\"bodytextbold\">");
/* 2398 */           out.print(FormatUtil.getString("am.webclient.weblogic.beans.text"));
/* 2399 */           out.write("</td>\n        </tr>\n        <tr align=\"center\"> \n          <td width=\"40%\" height=\"19\" class=\"sptrborder\">");
/* 2400 */           out.print(FormatUtil.getString("am.webclient.weblogic.cached.text"));
/* 2401 */           out.write("</td>\n          <td width=\"36%\" class=\"sptrborder\">");
/* 2402 */           out.print(FormatUtil.getString("am.webclient.weblogic.use.text"));
/* 2403 */           out.write("</td>\n          <td width=\"24%\" class=\"sptborder\"> ");
/* 2404 */           out.print(FormatUtil.getString("am.webclient.weblogic.idle.text"));
/* 2405 */           out.write("</td>\n        </tr>\n      </table></td>\n    <td colspan=\"3\" align=\"center\" class=\"columnheadingrightborder\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr align=\"center\"> \n          <td colspan=\"3\" class=\"bodytextbold\">");
/* 2406 */           out.print(FormatUtil.getString("am.webclient.weblogic.transaction.text"));
/* 2407 */           out.write("</td>\n        </tr>\n        <tr align=\"center\"> \n          <td width=\"28%\" class=\"sptrborder\">");
/* 2408 */           out.print(FormatUtil.getString("am.webclient.weblogic.timeout.text"));
/* 2409 */           out.write("</td>\n          <td width=\"37%\" class=\"sptrborder\">");
/* 2410 */           out.print(FormatUtil.getString("am.webclient.weblogic.rollback.text"));
/* 2411 */           out.write("</td>\n          <td width=\"35%\" class=\"sptborder\">");
/* 2412 */           out.print(FormatUtil.getString("am.webclient.weblogic.commited.text"));
/* 2413 */           out.write("</td>\n        </tr>\n      </table></td>\n    <td width=\"3%\" class=\"columnheading\">&nbsp;</td>\n  </tr>\n  ");
/*      */           
/* 2415 */           IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2416 */           _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2417 */           _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/* 2419 */           _jspx_th_logic_005fiterate_005f1.setName("data");
/*      */           
/* 2421 */           _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */           
/* 2423 */           _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/*      */           
/* 2425 */           _jspx_th_logic_005fiterate_005f1.setType("java.util.Properties");
/* 2426 */           int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2427 */           if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2428 */             Properties row = null;
/* 2429 */             Integer i = null;
/* 2430 */             if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2431 */               out = _jspx_page_context.pushBody();
/* 2432 */               _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2433 */               _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */             }
/* 2435 */             row = (Properties)_jspx_page_context.findAttribute("row");
/* 2436 */             i = (Integer)_jspx_page_context.findAttribute("i");
/*      */             for (;;) {
/* 2438 */               out.write(" \n  ");
/*      */               
/* 2440 */               int wncount = i.intValue();
/* 2441 */               int health = Integer.parseInt(row.getProperty("health"));
/* 2442 */               String bgclass = "yellowgrayborder";
/* 2443 */               if (wncount % 2 == 0)
/*      */               {
/* 2445 */                 bgclass = "whitegrayborder";
/*      */               }
/*      */               
/* 2448 */               out.write(10);
/* 2449 */               out.write(32);
/* 2450 */               out.write(32);
/*      */               
/* 2452 */               String tooltip = FormatUtil.getString("am.webclient.ejbdetails.tooltip") + row.getProperty("Name") + FormatUtil.getString("am.webclient.ejbdetails.tooltip1") + row.getProperty("jarName") + FormatUtil.getString("am.webclient.ejbdetails.tooltip2") + row.getProperty("earName");
/*      */               
/* 2454 */               out.write("\n  <tr> \n    <td class=\"");
/* 2455 */               out.print(bgclass);
/* 2456 */               out.write("\"><a title=\"");
/* 2457 */               out.print(tooltip);
/* 2458 */               out.write("\" class=\"tooltip\">");
/* 2459 */               out.print(getTrimmedText(row.getProperty("Name"), 15));
/* 2460 */               out.write("</a></td>\n    <td class=\"");
/* 2461 */               out.print(bgclass);
/* 2462 */               out.write("\"> <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2463 */               out.print(row.getProperty("ID"));
/* 2464 */               out.write("&attributeid=225')\"> \n      ");
/* 2465 */               out.print(getSeverityImageForHealth(alert.getProperty(row.getProperty("ID") + "#" + "225")));
/* 2466 */               out.write(" \n      </a> </td>\n    <td class=\"");
/* 2467 */               out.print(bgclass);
/* 2468 */               out.write(34);
/* 2469 */               out.write(62);
/* 2470 */               out.print(getBeanType(row.getProperty("type")));
/* 2471 */               out.write("</td>\n    <td align=\"center\" class=\"");
/* 2472 */               out.print(bgclass);
/* 2473 */               out.write(34);
/* 2474 */               out.write(62);
/*      */               
/* 2476 */               FormatTag _jspx_th_am_005fFormat_005f0 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 2477 */               _jspx_th_am_005fFormat_005f0.setPageContext(_jspx_page_context);
/* 2478 */               _jspx_th_am_005fFormat_005f0.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2480 */               _jspx_th_am_005fFormat_005f0.setType("Number");
/* 2481 */               int _jspx_eval_am_005fFormat_005f0 = _jspx_th_am_005fFormat_005f0.doStartTag();
/* 2482 */               if (_jspx_eval_am_005fFormat_005f0 != 0) {
/* 2483 */                 if (_jspx_eval_am_005fFormat_005f0 != 1) {
/* 2484 */                   out = _jspx_page_context.pushBody();
/* 2485 */                   _jspx_th_am_005fFormat_005f0.setBodyContent((BodyContent)out);
/* 2486 */                   _jspx_th_am_005fFormat_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2489 */                   out.print(row.getProperty("activation"));
/* 2490 */                   int evalDoAfterBody = _jspx_th_am_005fFormat_005f0.doAfterBody();
/* 2491 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2494 */                 if (_jspx_eval_am_005fFormat_005f0 != 1) {
/* 2495 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2498 */               if (_jspx_th_am_005fFormat_005f0.doEndTag() == 5) {
/* 2499 */                 this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f0); return;
/*      */               }
/*      */               
/* 2502 */               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f0);
/* 2503 */               out.write("</td>\n    <td align=\"center\" class=\"");
/* 2504 */               out.print(bgclass);
/* 2505 */               out.write(34);
/* 2506 */               out.write(62);
/*      */               
/* 2508 */               FormatTag _jspx_th_am_005fFormat_005f1 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 2509 */               _jspx_th_am_005fFormat_005f1.setPageContext(_jspx_page_context);
/* 2510 */               _jspx_th_am_005fFormat_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2512 */               _jspx_th_am_005fFormat_005f1.setType("Number");
/* 2513 */               int _jspx_eval_am_005fFormat_005f1 = _jspx_th_am_005fFormat_005f1.doStartTag();
/* 2514 */               if (_jspx_eval_am_005fFormat_005f1 != 0) {
/* 2515 */                 if (_jspx_eval_am_005fFormat_005f1 != 1) {
/* 2516 */                   out = _jspx_page_context.pushBody();
/* 2517 */                   _jspx_th_am_005fFormat_005f1.setBodyContent((BodyContent)out);
/* 2518 */                   _jspx_th_am_005fFormat_005f1.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2521 */                   out.print(row.getProperty("passivation"));
/* 2522 */                   int evalDoAfterBody = _jspx_th_am_005fFormat_005f1.doAfterBody();
/* 2523 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2526 */                 if (_jspx_eval_am_005fFormat_005f1 != 1) {
/* 2527 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2530 */               if (_jspx_th_am_005fFormat_005f1.doEndTag() == 5) {
/* 2531 */                 this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f1); return;
/*      */               }
/*      */               
/* 2534 */               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f1);
/* 2535 */               out.write("</td>\n    <td align=\"center\" class=\"");
/* 2536 */               out.print(bgclass);
/* 2537 */               out.write(34);
/* 2538 */               out.write(62);
/*      */               
/* 2540 */               FormatTag _jspx_th_am_005fFormat_005f2 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 2541 */               _jspx_th_am_005fFormat_005f2.setPageContext(_jspx_page_context);
/* 2542 */               _jspx_th_am_005fFormat_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2544 */               _jspx_th_am_005fFormat_005f2.setType("Number");
/* 2545 */               int _jspx_eval_am_005fFormat_005f2 = _jspx_th_am_005fFormat_005f2.doStartTag();
/* 2546 */               if (_jspx_eval_am_005fFormat_005f2 != 0) {
/* 2547 */                 if (_jspx_eval_am_005fFormat_005f2 != 1) {
/* 2548 */                   out = _jspx_page_context.pushBody();
/* 2549 */                   _jspx_th_am_005fFormat_005f2.setBodyContent((BodyContent)out);
/* 2550 */                   _jspx_th_am_005fFormat_005f2.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2553 */                   out.print(row.getProperty("threadswaiting"));
/* 2554 */                   int evalDoAfterBody = _jspx_th_am_005fFormat_005f2.doAfterBody();
/* 2555 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2558 */                 if (_jspx_eval_am_005fFormat_005f2 != 1) {
/* 2559 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2562 */               if (_jspx_th_am_005fFormat_005f2.doEndTag() == 5) {
/* 2563 */                 this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f2); return;
/*      */               }
/*      */               
/* 2566 */               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f2);
/* 2567 */               out.write("</td>\n    <td width=\"6%\" align=\"center\" class=\"");
/* 2568 */               out.print(bgclass);
/* 2569 */               out.write(34);
/* 2570 */               out.write(62);
/*      */               
/* 2572 */               FormatTag _jspx_th_am_005fFormat_005f3 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 2573 */               _jspx_th_am_005fFormat_005f3.setPageContext(_jspx_page_context);
/* 2574 */               _jspx_th_am_005fFormat_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2576 */               _jspx_th_am_005fFormat_005f3.setType("Number");
/* 2577 */               int _jspx_eval_am_005fFormat_005f3 = _jspx_th_am_005fFormat_005f3.doStartTag();
/* 2578 */               if (_jspx_eval_am_005fFormat_005f3 != 0) {
/* 2579 */                 if (_jspx_eval_am_005fFormat_005f3 != 1) {
/* 2580 */                   out = _jspx_page_context.pushBody();
/* 2581 */                   _jspx_th_am_005fFormat_005f3.setBodyContent((BodyContent)out);
/* 2582 */                   _jspx_th_am_005fFormat_005f3.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2585 */                   out.print(row.getProperty("cached"));
/* 2586 */                   int evalDoAfterBody = _jspx_th_am_005fFormat_005f3.doAfterBody();
/* 2587 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2590 */                 if (_jspx_eval_am_005fFormat_005f3 != 1) {
/* 2591 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2594 */               if (_jspx_th_am_005fFormat_005f3.doEndTag() == 5) {
/* 2595 */                 this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f3); return;
/*      */               }
/*      */               
/* 2598 */               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f3);
/* 2599 */               out.write("</td>\n    <td width=\"6%\" align=\"center\" class=\"");
/* 2600 */               out.print(bgclass);
/* 2601 */               out.write(34);
/* 2602 */               out.write(62);
/*      */               
/* 2604 */               FormatTag _jspx_th_am_005fFormat_005f4 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 2605 */               _jspx_th_am_005fFormat_005f4.setPageContext(_jspx_page_context);
/* 2606 */               _jspx_th_am_005fFormat_005f4.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2608 */               _jspx_th_am_005fFormat_005f4.setType("Number");
/* 2609 */               int _jspx_eval_am_005fFormat_005f4 = _jspx_th_am_005fFormat_005f4.doStartTag();
/* 2610 */               if (_jspx_eval_am_005fFormat_005f4 != 0) {
/* 2611 */                 if (_jspx_eval_am_005fFormat_005f4 != 1) {
/* 2612 */                   out = _jspx_page_context.pushBody();
/* 2613 */                   _jspx_th_am_005fFormat_005f4.setBodyContent((BodyContent)out);
/* 2614 */                   _jspx_th_am_005fFormat_005f4.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2617 */                   out.print(row.getProperty("beansinuse"));
/* 2618 */                   int evalDoAfterBody = _jspx_th_am_005fFormat_005f4.doAfterBody();
/* 2619 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2622 */                 if (_jspx_eval_am_005fFormat_005f4 != 1) {
/* 2623 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2626 */               if (_jspx_th_am_005fFormat_005f4.doEndTag() == 5) {
/* 2627 */                 this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f4); return;
/*      */               }
/*      */               
/* 2630 */               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f4);
/* 2631 */               out.write("</td>\n    <td width=\"6%\" align=\"center\" class=\"");
/* 2632 */               out.print(bgclass);
/* 2633 */               out.write(34);
/* 2634 */               out.write(62);
/*      */               
/* 2636 */               FormatTag _jspx_th_am_005fFormat_005f5 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 2637 */               _jspx_th_am_005fFormat_005f5.setPageContext(_jspx_page_context);
/* 2638 */               _jspx_th_am_005fFormat_005f5.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2640 */               _jspx_th_am_005fFormat_005f5.setType("Number");
/* 2641 */               int _jspx_eval_am_005fFormat_005f5 = _jspx_th_am_005fFormat_005f5.doStartTag();
/* 2642 */               if (_jspx_eval_am_005fFormat_005f5 != 0) {
/* 2643 */                 if (_jspx_eval_am_005fFormat_005f5 != 1) {
/* 2644 */                   out = _jspx_page_context.pushBody();
/* 2645 */                   _jspx_th_am_005fFormat_005f5.setBodyContent((BodyContent)out);
/* 2646 */                   _jspx_th_am_005fFormat_005f5.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2649 */                   out.print(row.getProperty("idlebeans"));
/* 2650 */                   int evalDoAfterBody = _jspx_th_am_005fFormat_005f5.doAfterBody();
/* 2651 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2654 */                 if (_jspx_eval_am_005fFormat_005f5 != 1) {
/* 2655 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2658 */               if (_jspx_th_am_005fFormat_005f5.doEndTag() == 5) {
/* 2659 */                 this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f5); return;
/*      */               }
/*      */               
/* 2662 */               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f5);
/* 2663 */               out.write("</td>\n    <td width=\"5%\" align=\"center\" class=\"");
/* 2664 */               out.print(bgclass);
/* 2665 */               out.write(34);
/* 2666 */               out.write(62);
/*      */               
/* 2668 */               FormatTag _jspx_th_am_005fFormat_005f6 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 2669 */               _jspx_th_am_005fFormat_005f6.setPageContext(_jspx_page_context);
/* 2670 */               _jspx_th_am_005fFormat_005f6.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2672 */               _jspx_th_am_005fFormat_005f6.setType("Number");
/* 2673 */               int _jspx_eval_am_005fFormat_005f6 = _jspx_th_am_005fFormat_005f6.doStartTag();
/* 2674 */               if (_jspx_eval_am_005fFormat_005f6 != 0) {
/* 2675 */                 if (_jspx_eval_am_005fFormat_005f6 != 1) {
/* 2676 */                   out = _jspx_page_context.pushBody();
/* 2677 */                   _jspx_th_am_005fFormat_005f6.setBodyContent((BodyContent)out);
/* 2678 */                   _jspx_th_am_005fFormat_005f6.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2681 */                   out.print(row.getProperty("txtimedout"));
/* 2682 */                   int evalDoAfterBody = _jspx_th_am_005fFormat_005f6.doAfterBody();
/* 2683 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2686 */                 if (_jspx_eval_am_005fFormat_005f6 != 1) {
/* 2687 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2690 */               if (_jspx_th_am_005fFormat_005f6.doEndTag() == 5) {
/* 2691 */                 this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f6); return;
/*      */               }
/*      */               
/* 2694 */               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f6);
/* 2695 */               out.write("</td>\n    <td width=\"7%\" align=\"center\" class=\"");
/* 2696 */               out.print(bgclass);
/* 2697 */               out.write(34);
/* 2698 */               out.write(62);
/*      */               
/* 2700 */               FormatTag _jspx_th_am_005fFormat_005f7 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 2701 */               _jspx_th_am_005fFormat_005f7.setPageContext(_jspx_page_context);
/* 2702 */               _jspx_th_am_005fFormat_005f7.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2704 */               _jspx_th_am_005fFormat_005f7.setType("Number");
/* 2705 */               int _jspx_eval_am_005fFormat_005f7 = _jspx_th_am_005fFormat_005f7.doStartTag();
/* 2706 */               if (_jspx_eval_am_005fFormat_005f7 != 0) {
/* 2707 */                 if (_jspx_eval_am_005fFormat_005f7 != 1) {
/* 2708 */                   out = _jspx_page_context.pushBody();
/* 2709 */                   _jspx_th_am_005fFormat_005f7.setBodyContent((BodyContent)out);
/* 2710 */                   _jspx_th_am_005fFormat_005f7.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2713 */                   out.print(row.getProperty("txrolledback"));
/* 2714 */                   int evalDoAfterBody = _jspx_th_am_005fFormat_005f7.doAfterBody();
/* 2715 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2718 */                 if (_jspx_eval_am_005fFormat_005f7 != 1) {
/* 2719 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2722 */               if (_jspx_th_am_005fFormat_005f7.doEndTag() == 5) {
/* 2723 */                 this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f7); return;
/*      */               }
/*      */               
/* 2726 */               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f7);
/* 2727 */               out.write("</td>\n    <td width=\"7%\" align=\"center\" class=\"");
/* 2728 */               out.print(bgclass);
/* 2729 */               out.write(34);
/* 2730 */               out.write(62);
/*      */               
/* 2732 */               FormatTag _jspx_th_am_005fFormat_005f8 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 2733 */               _jspx_th_am_005fFormat_005f8.setPageContext(_jspx_page_context);
/* 2734 */               _jspx_th_am_005fFormat_005f8.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2736 */               _jspx_th_am_005fFormat_005f8.setType("Number");
/* 2737 */               int _jspx_eval_am_005fFormat_005f8 = _jspx_th_am_005fFormat_005f8.doStartTag();
/* 2738 */               if (_jspx_eval_am_005fFormat_005f8 != 0) {
/* 2739 */                 if (_jspx_eval_am_005fFormat_005f8 != 1) {
/* 2740 */                   out = _jspx_page_context.pushBody();
/* 2741 */                   _jspx_th_am_005fFormat_005f8.setBodyContent((BodyContent)out);
/* 2742 */                   _jspx_th_am_005fFormat_005f8.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2745 */                   out.print(row.getProperty("txcommited"));
/* 2746 */                   int evalDoAfterBody = _jspx_th_am_005fFormat_005f8.doAfterBody();
/* 2747 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2750 */                 if (_jspx_eval_am_005fFormat_005f8 != 1) {
/* 2751 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2754 */               if (_jspx_th_am_005fFormat_005f8.doEndTag() == 5) {
/* 2755 */                 this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f8); return;
/*      */               }
/*      */               
/* 2758 */               this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f8);
/* 2759 */               out.write("</td>\n    ");
/*      */               
/* 2761 */               EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2762 */               _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 2763 */               _jspx_th_logic_005fequal_005f0.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2765 */               _jspx_th_logic_005fequal_005f0.setName("row");
/*      */               
/* 2767 */               _jspx_th_logic_005fequal_005f0.setProperty("type");
/*      */               
/* 2769 */               _jspx_th_logic_005fequal_005f0.setValue("ENTITY");
/* 2770 */               int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 2771 */               if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */                 for (;;) {
/* 2773 */                   out.write(" \n    <td class=\"");
/* 2774 */                   out.print(bgclass);
/* 2775 */                   out.write("\" align=right><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2776 */                   out.print(row.getProperty("ID"));
/* 2777 */                   out.write("&attributeIDs=201,203,204,206,207,208,209,210&attributeToSelect=201&redirectto=");
/* 2778 */                   out.print(redirect_to);
/* 2779 */                   out.write("' class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" hspace=\"4\" border=\"0\" align=\"absmiddle\"></a></td>\n    ");
/* 2780 */                   int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 2781 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2785 */               if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 2786 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0); return;
/*      */               }
/*      */               
/* 2789 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 2790 */               out.write(32);
/*      */               
/* 2792 */               EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2793 */               _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 2794 */               _jspx_th_logic_005fequal_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2796 */               _jspx_th_logic_005fequal_005f1.setName("row");
/*      */               
/* 2798 */               _jspx_th_logic_005fequal_005f1.setProperty("type");
/*      */               
/* 2800 */               _jspx_th_logic_005fequal_005f1.setValue("STATELESS_SESSION");
/* 2801 */               int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 2802 */               if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */                 for (;;) {
/* 2804 */                   out.write(" \n    <td width=\"2%\" align=right class=\"");
/* 2805 */                   out.print(bgclass);
/* 2806 */                   out.write("\"><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2807 */                   out.print(row.getProperty("ID"));
/* 2808 */                   out.write("&attributeIDs=206,207,208,209,210&attributeToSelect=206&redirectto=");
/* 2809 */                   out.print(redirect_to);
/* 2810 */                   out.write("' class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" hspace=\"4\" border=\"0\" align=\"absmiddle\"></a></td>\n    ");
/* 2811 */                   int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 2812 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2816 */               if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 2817 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1); return;
/*      */               }
/*      */               
/* 2820 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 2821 */               out.write(32);
/*      */               
/* 2823 */               EqualTag _jspx_th_logic_005fequal_005f2 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2824 */               _jspx_th_logic_005fequal_005f2.setPageContext(_jspx_page_context);
/* 2825 */               _jspx_th_logic_005fequal_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2827 */               _jspx_th_logic_005fequal_005f2.setName("row");
/*      */               
/* 2829 */               _jspx_th_logic_005fequal_005f2.setProperty("type");
/*      */               
/* 2831 */               _jspx_th_logic_005fequal_005f2.setValue("STATEFUL_SESSION");
/* 2832 */               int _jspx_eval_logic_005fequal_005f2 = _jspx_th_logic_005fequal_005f2.doStartTag();
/* 2833 */               if (_jspx_eval_logic_005fequal_005f2 != 0) {
/*      */                 for (;;) {
/* 2835 */                   out.write(" \n    <td width=\"2%\" align=right class=\"");
/* 2836 */                   out.print(bgclass);
/* 2837 */                   out.write("\"><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2838 */                   out.print(row.getProperty("ID"));
/* 2839 */                   out.write("&attributeIDs=201,203,204,206,207&attributeToSelect=201&redirectto=");
/* 2840 */                   out.print(redirect_to);
/* 2841 */                   out.write("' class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" hspace=\"4\" border=\"0\" align=\"absmiddle\"></a></td>\n    ");
/* 2842 */                   int evalDoAfterBody = _jspx_th_logic_005fequal_005f2.doAfterBody();
/* 2843 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2847 */               if (_jspx_th_logic_005fequal_005f2.doEndTag() == 5) {
/* 2848 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2); return;
/*      */               }
/*      */               
/* 2851 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 2852 */               out.write(32);
/*      */               
/* 2854 */               EqualTag _jspx_th_logic_005fequal_005f3 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2855 */               _jspx_th_logic_005fequal_005f3.setPageContext(_jspx_page_context);
/* 2856 */               _jspx_th_logic_005fequal_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */               
/* 2858 */               _jspx_th_logic_005fequal_005f3.setName("row");
/*      */               
/* 2860 */               _jspx_th_logic_005fequal_005f3.setProperty("type");
/*      */               
/* 2862 */               _jspx_th_logic_005fequal_005f3.setValue("MESSAGE_DRIVEN");
/* 2863 */               int _jspx_eval_logic_005fequal_005f3 = _jspx_th_logic_005fequal_005f3.doStartTag();
/* 2864 */               if (_jspx_eval_logic_005fequal_005f3 != 0) {
/*      */                 for (;;) {
/* 2866 */                   out.write(" \n    <td width=\"2%\" align=right class=\"");
/* 2867 */                   out.print(bgclass);
/* 2868 */                   out.write("\"><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2869 */                   out.print(row.getProperty("ID"));
/* 2870 */                   out.write("&attributeIDs=206,207,208,209,210&attributeToSelect=206&redirectto=");
/* 2871 */                   out.print(redirect_to);
/* 2872 */                   out.write("' class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" hspace=\"4\" border=\"0\" align=\"absmiddle\"></a></td>\n    ");
/* 2873 */                   int evalDoAfterBody = _jspx_th_logic_005fequal_005f3.doAfterBody();
/* 2874 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2878 */               if (_jspx_th_logic_005fequal_005f3.doEndTag() == 5) {
/* 2879 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f3); return;
/*      */               }
/*      */               
/* 2882 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f3);
/* 2883 */               out.write(" </tr>\n  ");
/* 2884 */               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 2885 */               row = (Properties)_jspx_page_context.findAttribute("row");
/* 2886 */               i = (Integer)_jspx_page_context.findAttribute("i");
/* 2887 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2890 */             if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2891 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2894 */           if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 2895 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */           }
/*      */           
/* 2898 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 2899 */           out.write(" \n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr> \n    <td width=\"72%\" height=\"31\" class=\"tablebottom\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr> \n          <td width=\"16%\" class=\"arial10\"><img src=\"/images/icon_ejb_entity.gif\" width=\"21\" height=\"13\" align=\"absmiddle\"> \n            - ");
/* 2900 */           out.print(FormatUtil.getString("am.webclient.websphere.ejb.entity"));
/* 2901 */           out.write("</td>\n          <td width=\"20%\" class=\"arial10\"><img src=\"/images/icon_ejb_stateless.gif\" align=\"absmiddle\"> \n            - ");
/* 2902 */           out.print(FormatUtil.getString("am.webclient.websphere.ejb.stateless"));
/* 2903 */           out.write("</td>\n          <td width=\"17%\" class=\"arial10\"><img src=\"/images/icon_ejb_stateful.gif\" align=\"absmiddle\"> \n            - ");
/* 2904 */           out.print(FormatUtil.getString("am.webclient.websphere.ejb.stateful"));
/* 2905 */           out.write("</td>\n          <td width=\"31%\" class=\"arial10\"><img src=\"/images/icon_ejb_mbean.gif\" align=\"absmiddle\"> \n            - ");
/* 2906 */           out.print(FormatUtil.getString("am.webclient.websphere.ejb.messagedriven"));
/* 2907 */           out.write("</td>\n          <td width=\"16%\" align=\"right\" class=\"arial10\"><a href=\"#top\" class=\"staticlinks\">");
/* 2908 */           out.print(FormatUtil.getString("am.webclient.common.top.text"));
/* 2909 */           out.write("</a>&nbsp;&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr> \n    <td>&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr> \n    <td width=\"52%\" height=\"200\" valign=\"top\" > <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr> \n          <td class=\"tableheading\">");
/* 2910 */           out.print(FormatUtil.getString("am.webclient.weblogic.top5ejbsinstance.text"));
/* 2911 */           out.write("</td>\n        </tr>\n        <tr> \n          <td> \n            ");
/*      */           
/* 2913 */           wlsGraph.setParam(request.getParameter("resourceid"), "EJB_POOL");
/*      */           
/* 2915 */           out.write("\n            ");
/*      */           
/* 2917 */           BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 2918 */           _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 2919 */           _jspx_th_awolf_005fbarchart_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/* 2921 */           _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("wlsGraph");
/*      */           
/* 2923 */           _jspx_th_awolf_005fbarchart_005f0.setWidth("300");
/*      */           
/* 2925 */           _jspx_th_awolf_005fbarchart_005f0.setHeight("180");
/*      */           
/* 2927 */           _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */           
/* 2929 */           _jspx_th_awolf_005fbarchart_005f0.setUrl(false);
/*      */           
/* 2931 */           _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel(xaxis_ejb);
/*      */           
/* 2933 */           _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel(yaxis_beans);
/* 2934 */           int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 2935 */           if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 2936 */             if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2937 */               out = _jspx_page_context.pushBody();
/* 2938 */               _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 2939 */               _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2942 */               out.write(" \n            ");
/* 2943 */               int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 2944 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2947 */             if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2948 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2951 */           if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 2952 */             this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*      */           }
/*      */           
/* 2955 */           this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 2956 */           out.write(" </td>\n        </tr>\n      </table></td>\n    <td width=\"48%\" height=\"200\" valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr> \n          <td class=\"Tableheading\">");
/* 2957 */           out.print(FormatUtil.getString("am.webclient.weblogic.cache.text"));
/* 2958 */           out.write("</td>\n        </tr>\n        <tr> \n          <td> \n            ");
/*      */           
/* 2960 */           wlsGraph.setParam(request.getParameter("resourceid"), "EJB_CACHE");
/*      */           
/* 2962 */           out.write("\n            ");
/*      */           
/* 2964 */           BarChart _jspx_th_awolf_005fbarchart_005f1 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 2965 */           _jspx_th_awolf_005fbarchart_005f1.setPageContext(_jspx_page_context);
/* 2966 */           _jspx_th_awolf_005fbarchart_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/* 2968 */           _jspx_th_awolf_005fbarchart_005f1.setDataSetProducer("wlsGraph");
/*      */           
/* 2970 */           _jspx_th_awolf_005fbarchart_005f1.setWidth("300");
/*      */           
/* 2972 */           _jspx_th_awolf_005fbarchart_005f1.setHeight("180");
/*      */           
/* 2974 */           _jspx_th_awolf_005fbarchart_005f1.setLegend("false");
/*      */           
/* 2976 */           _jspx_th_awolf_005fbarchart_005f1.setUrl(false);
/*      */           
/* 2978 */           _jspx_th_awolf_005fbarchart_005f1.setXaxisLabel(xaxis_ejb);
/*      */           
/* 2980 */           _jspx_th_awolf_005fbarchart_005f1.setYaxisLabel(yaxis_cachedbeans);
/* 2981 */           int _jspx_eval_awolf_005fbarchart_005f1 = _jspx_th_awolf_005fbarchart_005f1.doStartTag();
/* 2982 */           if (_jspx_eval_awolf_005fbarchart_005f1 != 0) {
/* 2983 */             if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 2984 */               out = _jspx_page_context.pushBody();
/* 2985 */               _jspx_th_awolf_005fbarchart_005f1.setBodyContent((BodyContent)out);
/* 2986 */               _jspx_th_awolf_005fbarchart_005f1.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2989 */               out.write(" \n            ");
/* 2990 */               int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f1.doAfterBody();
/* 2991 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2994 */             if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 2995 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2998 */           if (_jspx_th_awolf_005fbarchart_005f1.doEndTag() == 5) {
/* 2999 */             this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f1); return;
/*      */           }
/*      */           
/* 3002 */           this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f1);
/* 3003 */           out.write(" </td>\n        </tr>\n      </table></td>\n  </tr>\n  <tr> \n    <td height=\"19\" valign=\"top\" >&nbsp;</td>\n    <td height=\"19\" valign=\"top\">&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr> \n    <td width=\"72%\" height=\"26\" class=\"tableheading\">");
/* 3004 */           out.print(FormatUtil.getString("am.webclient.weblogic.top5ejbstransaction.text"));
/* 3005 */           out.write("</td>\n  </tr>\n</table>\n");
/*      */           
/* 3007 */           wlsGraph.setParam(request.getParameter("resourceid"), "EJB_TX");
/*      */           
/* 3009 */           out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" class=\"lrbborder\">\n  <tr> \n    <td width=\"50%\" height=\"200\" > ");
/*      */           
/* 3011 */           BarChart _jspx_th_awolf_005fbarchart_005f2 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 3012 */           _jspx_th_awolf_005fbarchart_005f2.setPageContext(_jspx_page_context);
/* 3013 */           _jspx_th_awolf_005fbarchart_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/* 3015 */           _jspx_th_awolf_005fbarchart_005f2.setDataSetProducer("wlsGraph");
/*      */           
/* 3017 */           _jspx_th_awolf_005fbarchart_005f2.setWidth("300");
/*      */           
/* 3019 */           _jspx_th_awolf_005fbarchart_005f2.setHeight("220");
/*      */           
/* 3021 */           _jspx_th_awolf_005fbarchart_005f2.setLegend("false");
/*      */           
/* 3023 */           _jspx_th_awolf_005fbarchart_005f2.setUrl(false);
/*      */           
/* 3025 */           _jspx_th_awolf_005fbarchart_005f2.setXaxisLabel(xaxis_ejb);
/*      */           
/* 3027 */           _jspx_th_awolf_005fbarchart_005f2.setYaxisLabel(yaxis_transactionbeans);
/* 3028 */           int _jspx_eval_awolf_005fbarchart_005f2 = _jspx_th_awolf_005fbarchart_005f2.doStartTag();
/* 3029 */           if (_jspx_eval_awolf_005fbarchart_005f2 != 0) {
/* 3030 */             if (_jspx_eval_awolf_005fbarchart_005f2 != 1) {
/* 3031 */               out = _jspx_page_context.pushBody();
/* 3032 */               _jspx_th_awolf_005fbarchart_005f2.setBodyContent((BodyContent)out);
/* 3033 */               _jspx_th_awolf_005fbarchart_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 3036 */               out.write(" \n      ");
/* 3037 */               int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f2.doAfterBody();
/* 3038 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3041 */             if (_jspx_eval_awolf_005fbarchart_005f2 != 1) {
/* 3042 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3045 */           if (_jspx_th_awolf_005fbarchart_005f2.doEndTag() == 5) {
/* 3046 */             this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f2); return;
/*      */           }
/*      */           
/* 3049 */           this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f2);
/* 3050 */           out.write(" </td>\n  </tr>\n</table>\n");
/* 3051 */           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3052 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3056 */       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3057 */         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */       }
/*      */       else {
/* 3060 */         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3061 */         out.write(32);
/* 3062 */         out.write(10);
/* 3063 */         out.write(10);
/* 3064 */         out.write(10);
/* 3065 */         out.write(10);
/*      */       }
/* 3067 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3068 */         out = _jspx_out;
/* 3069 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3070 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3071 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3074 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3080 */     PageContext pageContext = _jspx_page_context;
/* 3081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3083 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3084 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3085 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 3087 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3088 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3090 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3091 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3093 */           out.write(10);
/* 3094 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3095 */             return true;
/* 3096 */           out.write(10);
/* 3097 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3098 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3102 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3103 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3106 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 3107 */         out = _jspx_page_context.popBody(); }
/* 3108 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3110 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3111 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3113 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3118 */     PageContext pageContext = _jspx_page_context;
/* 3119 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3121 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3122 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3123 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3125 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3127 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3128 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3129 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3130 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3131 */       return true;
/*      */     }
/* 3133 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3139 */     PageContext pageContext = _jspx_page_context;
/* 3140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3142 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3143 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3144 */     _jspx_th_c_005fout_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 3146 */     _jspx_th_c_005fout_005f0.setValue("${param.resourcename}");
/* 3147 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3148 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3149 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3150 */       return true;
/*      */     }
/* 3152 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3153 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ShowEJBDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */