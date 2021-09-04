/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.AMRegexUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
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
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.SkipPageException;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.HttpJspBase;
/*      */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.tomcat.InstanceManager;
/*      */ 
/*      */ public final class chooseresource_jsp extends HttpJspBase implements JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   78 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   81 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   82 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   83 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   90 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   95 */     ArrayList list = null;
/*   96 */     StringBuffer sbf = new StringBuffer();
/*   97 */     ManagedApplication mo = new ManagedApplication();
/*   98 */     if (distinct)
/*      */     {
/*  100 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*  104 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*  107 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*  109 */       ArrayList row = (ArrayList)list.get(i);
/*  110 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  111 */       if (distinct) {
/*  112 */         sbf.append(row.get(0));
/*      */       } else
/*  114 */         sbf.append(row.get(1));
/*  115 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  118 */     return sbf.toString(); }
/*      */   
/*  120 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  123 */     if (severity == null)
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  127 */     if (severity.equals("5"))
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  131 */     if (severity.equals("1"))
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  138 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  145 */     if (severity == null)
/*      */     {
/*  147 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  149 */     if (severity.equals("1"))
/*      */     {
/*  151 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  153 */     if (severity.equals("4"))
/*      */     {
/*  155 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  157 */     if (severity.equals("5"))
/*      */     {
/*  159 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  164 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  170 */     if (severity == null)
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  174 */     if (severity.equals("5"))
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  178 */     if (severity.equals("1"))
/*      */     {
/*  180 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  184 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  190 */     if (severity == null)
/*      */     {
/*  192 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  194 */     if (severity.equals("1"))
/*      */     {
/*  196 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  198 */     if (severity.equals("4"))
/*      */     {
/*  200 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  202 */     if (severity.equals("5"))
/*      */     {
/*  204 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  208 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  214 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  220 */     if (severity == 5)
/*      */     {
/*  222 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  224 */     if (severity == 1)
/*      */     {
/*  226 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  231 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  237 */     if (severity == null)
/*      */     {
/*  239 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  241 */     if (severity.equals("5"))
/*      */     {
/*  243 */       if (isAvailability) {
/*  244 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  247 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  250 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  254 */     if (severity.equals("1"))
/*      */     {
/*  256 */       if (isAvailability) {
/*  257 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  260 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  267 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  274 */     if (severity == null)
/*      */     {
/*  276 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  278 */     if (severity.equals("5"))
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("4"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  286 */     if (severity.equals("1"))
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  293 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  299 */     if (severity == null)
/*      */     {
/*  301 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  303 */     if (severity.equals("5"))
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  307 */     if (severity.equals("4"))
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  311 */     if (severity.equals("1"))
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  318 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  325 */     if (severity == null)
/*      */     {
/*  327 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  329 */     if (severity.equals("5"))
/*      */     {
/*  331 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  333 */     if (severity.equals("4"))
/*      */     {
/*  335 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  337 */     if (severity.equals("1"))
/*      */     {
/*  339 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  344 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  352 */     StringBuffer out = new StringBuffer();
/*  353 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  354 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  355 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  356 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  357 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  358 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  359 */     out.append("</tr>");
/*  360 */     out.append("</form></table>");
/*  361 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  368 */     if (val == null)
/*      */     {
/*  370 */       return "-";
/*      */     }
/*      */     
/*  373 */     String ret = FormatUtil.formatNumber(val);
/*  374 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  375 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  378 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  382 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  390 */     StringBuffer out = new StringBuffer();
/*  391 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  392 */     out.append("<tr>");
/*  393 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  395 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  397 */     out.append("</tr>");
/*  398 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  402 */       if (j % 2 == 0)
/*      */       {
/*  404 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  408 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  411 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  413 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  416 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  420 */       out.append("</tr>");
/*      */     }
/*  422 */     out.append("</table>");
/*  423 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  424 */     out.append("<tr>");
/*  425 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  426 */     out.append("</tr>");
/*  427 */     out.append("</table>");
/*  428 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  434 */     StringBuffer out = new StringBuffer();
/*  435 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  436 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  437 */     out.append("<tr>");
/*  438 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  439 */     out.append("<tr>");
/*  440 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  441 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  442 */     out.append("</tr>");
/*  443 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  446 */       out.append("<tr>");
/*  447 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  448 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  449 */       out.append("</tr>");
/*      */     }
/*      */     
/*  452 */     out.append("</table>");
/*  453 */     out.append("</table>");
/*  454 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  459 */     if (severity.equals("0"))
/*      */     {
/*  461 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  465 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  472 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  485 */     StringBuffer out = new StringBuffer();
/*  486 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  487 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  489 */       out.append("<tr>");
/*  490 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  491 */       out.append("</tr>");
/*      */       
/*      */ 
/*  494 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  496 */         String borderclass = "";
/*      */         
/*      */ 
/*  499 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  501 */         out.append("<tr>");
/*      */         
/*  503 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  504 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  505 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  511 */     out.append("</table><br>");
/*  512 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  513 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  515 */       List sLinks = secondLevelOfLinks[0];
/*  516 */       List sText = secondLevelOfLinks[1];
/*  517 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  520 */         out.append("<tr>");
/*  521 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  522 */         out.append("</tr>");
/*  523 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  525 */           String borderclass = "";
/*      */           
/*      */ 
/*  528 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  530 */           out.append("<tr>");
/*      */           
/*  532 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  533 */           if (sLinks.get(i).toString().length() == 0) {
/*  534 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  537 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  539 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  543 */     out.append("</table>");
/*  544 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  551 */     StringBuffer out = new StringBuffer();
/*  552 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  553 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  555 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  557 */         out.append("<tr>");
/*  558 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  559 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  563 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  565 */           String borderclass = "";
/*      */           
/*      */ 
/*  568 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  570 */           out.append("<tr>");
/*      */           
/*  572 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  573 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  574 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  577 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  580 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  585 */     out.append("</table><br>");
/*  586 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  587 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  589 */       List sLinks = secondLevelOfLinks[0];
/*  590 */       List sText = secondLevelOfLinks[1];
/*  591 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  594 */         out.append("<tr>");
/*  595 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  596 */         out.append("</tr>");
/*  597 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  599 */           String borderclass = "";
/*      */           
/*      */ 
/*  602 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  604 */           out.append("<tr>");
/*      */           
/*  606 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  607 */           if (sLinks.get(i).toString().length() == 0) {
/*  608 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  611 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  613 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  617 */     out.append("</table>");
/*  618 */     return out.toString();
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
/*  631 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  634 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  637 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  640 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  643 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  646 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  649 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  652 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  660 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  665 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  670 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  675 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  680 */     if (val != null)
/*      */     {
/*  682 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  686 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  691 */     if (val == null) {
/*  692 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  696 */       val = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  701 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  707 */     if (val != null)
/*      */     {
/*  709 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  713 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  719 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  724 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  728 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  733 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  738 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  743 */     String hostaddress = "";
/*  744 */     String ip = request.getHeader("x-forwarded-for");
/*  745 */     if (ip == null)
/*  746 */       ip = request.getRemoteAddr();
/*  747 */     InetAddress add = null;
/*  748 */     if (ip.equals("127.0.0.1")) {
/*  749 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  753 */       add = InetAddress.getByName(ip);
/*      */     }
/*  755 */     hostaddress = add.getHostName();
/*  756 */     if (hostaddress.indexOf('.') != -1) {
/*  757 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  758 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  762 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  767 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  773 */     if (severity == null)
/*      */     {
/*  775 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  777 */     if (severity.equals("5"))
/*      */     {
/*  779 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  781 */     if (severity.equals("1"))
/*      */     {
/*  783 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  788 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  793 */     ResultSet set = null;
/*  794 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  795 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  797 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  798 */       if (set.next()) { String str1;
/*  799 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  800 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  803 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  808 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  811 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  813 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  817 */     StringBuffer rca = new StringBuffer();
/*  818 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  819 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  822 */     int rcalength = key.length();
/*  823 */     String split = "6. ";
/*  824 */     int splitPresent = key.indexOf(split);
/*  825 */     String div1 = "";String div2 = "";
/*  826 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  828 */       if (rcalength > 180) {
/*  829 */         rca.append("<span class=\"rca-critical-text\">");
/*  830 */         getRCATrimmedText(key, rca);
/*  831 */         rca.append("</span>");
/*      */       } else {
/*  833 */         rca.append("<span class=\"rca-critical-text\">");
/*  834 */         rca.append(key);
/*  835 */         rca.append("</span>");
/*      */       }
/*  837 */       return rca.toString();
/*      */     }
/*  839 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  840 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  841 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  842 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  843 */     getRCATrimmedText(div1, rca);
/*  844 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  847 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  848 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  849 */     getRCATrimmedText(div2, rca);
/*  850 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  852 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  857 */     String[] st = msg.split("<br>");
/*  858 */     for (int i = 0; i < st.length; i++) {
/*  859 */       String s = st[i];
/*  860 */       if (s.length() > 180) {
/*  861 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  863 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  867 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  868 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  870 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  874 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  875 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  876 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  879 */       if (key == null) {
/*  880 */         return ret;
/*      */       }
/*      */       
/*  883 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  884 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  887 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  888 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  889 */       set = AMConnectionPool.executeQueryStmt(query);
/*  890 */       if (set.next())
/*      */       {
/*  892 */         String helpLink = set.getString("LINK");
/*  893 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  896 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  902 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  921 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  912 */         if (set != null) {
/*  913 */           AMConnectionPool.closeStatement(set);
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
/*  927 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  928 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  930 */       String entityStr = (String)keys.nextElement();
/*  931 */       String mmessage = temp.getProperty(entityStr);
/*  932 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  933 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  935 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  941 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  942 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  944 */       String entityStr = (String)keys.nextElement();
/*  945 */       String mmessage = temp.getProperty(entityStr);
/*  946 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  947 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  949 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  954 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  964 */     String des = new String();
/*  965 */     while (str.indexOf(find) != -1) {
/*  966 */       des = des + str.substring(0, str.indexOf(find));
/*  967 */       des = des + replace;
/*  968 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  970 */     des = des + str;
/*  971 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  978 */       if (alert == null)
/*      */       {
/*  980 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  982 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  984 */         return "&nbsp;";
/*      */       }
/*      */       
/*  987 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  989 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  992 */       int rcalength = test.length();
/*  993 */       if (rcalength < 300)
/*      */       {
/*  995 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  999 */       StringBuffer out = new StringBuffer();
/* 1000 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/* 1001 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/* 1002 */       out.append("</div>");
/* 1003 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/* 1004 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/* 1005 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1010 */       ex.printStackTrace();
/*      */     }
/* 1012 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1018 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1023 */     ArrayList attribIDs = new ArrayList();
/* 1024 */     ArrayList resIDs = new ArrayList();
/* 1025 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1027 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1029 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1031 */       String resourceid = "";
/* 1032 */       String resourceType = "";
/* 1033 */       if (type == 2) {
/* 1034 */         resourceid = (String)row.get(0);
/* 1035 */         resourceType = (String)row.get(3);
/*      */       }
/* 1037 */       else if (type == 3) {
/* 1038 */         resourceid = (String)row.get(0);
/* 1039 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1042 */         resourceid = (String)row.get(6);
/* 1043 */         resourceType = (String)row.get(7);
/*      */       }
/* 1045 */       resIDs.add(resourceid);
/* 1046 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1047 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1049 */       String healthentity = null;
/* 1050 */       String availentity = null;
/* 1051 */       if (healthid != null) {
/* 1052 */         healthentity = resourceid + "_" + healthid;
/* 1053 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1056 */       if (availid != null) {
/* 1057 */         availentity = resourceid + "_" + availid;
/* 1058 */         entitylist.add(availentity);
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
/* 1072 */     Properties alert = getStatus(entitylist);
/* 1073 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1078 */     int size = monitorList.size();
/*      */     
/* 1080 */     String[] severity = new String[size];
/*      */     
/* 1082 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1084 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1085 */       String resourceName1 = (String)row1.get(7);
/* 1086 */       String resourceid1 = (String)row1.get(6);
/* 1087 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1088 */       if (severity[j] == null)
/*      */       {
/* 1090 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1094 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1096 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1098 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1101 */         if (sev > 0) {
/* 1102 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1103 */           monitorList.set(k, monitorList.get(j));
/* 1104 */           monitorList.set(j, t);
/* 1105 */           String temp = severity[k];
/* 1106 */           severity[k] = severity[j];
/* 1107 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1113 */     int z = 0;
/* 1114 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1117 */       int i = 0;
/* 1118 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1121 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1125 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1129 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1131 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1134 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1138 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1141 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1142 */       String resourceName1 = (String)row1.get(7);
/* 1143 */       String resourceid1 = (String)row1.get(6);
/* 1144 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1145 */       if (hseverity[j] == null)
/*      */       {
/* 1147 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1152 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1154 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1157 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1160 */         if (hsev > 0) {
/* 1161 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1162 */           monitorList.set(k, monitorList.get(j));
/* 1163 */           monitorList.set(j, t);
/* 1164 */           String temp1 = hseverity[k];
/* 1165 */           hseverity[k] = hseverity[j];
/* 1166 */           hseverity[j] = temp1;
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
/* 1178 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1179 */     boolean forInventory = false;
/* 1180 */     String trdisplay = "none";
/* 1181 */     String plusstyle = "inline";
/* 1182 */     String minusstyle = "none";
/* 1183 */     String haidTopLevel = "";
/* 1184 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1186 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1188 */         haidTopLevel = request.getParameter("haid");
/* 1189 */         forInventory = true;
/* 1190 */         trdisplay = "table-row;";
/* 1191 */         plusstyle = "none";
/* 1192 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1199 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1202 */     ArrayList listtoreturn = new ArrayList();
/* 1203 */     StringBuffer toreturn = new StringBuffer();
/* 1204 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1205 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1206 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1208 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1210 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1211 */       String childresid = (String)singlerow.get(0);
/* 1212 */       String childresname = (String)singlerow.get(1);
/* 1213 */       childresname = ExtProdUtil.decodeString(childresname);
/* 1214 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1215 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1216 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1217 */       String unmanagestatus = (String)singlerow.get(5);
/* 1218 */       String actionstatus = (String)singlerow.get(6);
/* 1219 */       String linkclass = "monitorgp-links";
/* 1220 */       String titleforres = childresname;
/* 1221 */       String titilechildresname = childresname;
/* 1222 */       String childimg = "/images/trcont.png";
/* 1223 */       String flag = "enable";
/* 1224 */       String dcstarted = (String)singlerow.get(8);
/* 1225 */       String configMonitor = "";
/* 1226 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1227 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1229 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1231 */       if (singlerow.get(7) != null)
/*      */       {
/* 1233 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1235 */       String haiGroupType = "0";
/* 1236 */       if ("HAI".equals(childtype))
/*      */       {
/* 1238 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1240 */       childimg = "/images/trend.png";
/* 1241 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1242 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1243 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1245 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1247 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1249 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1250 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1253 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1255 */         linkclass = "disabledtext";
/* 1256 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1258 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1259 */       String availmouseover = "";
/* 1260 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1262 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1264 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1265 */       String healthmouseover = "";
/* 1266 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1268 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1271 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1272 */       int spacing = 0;
/* 1273 */       if (level >= 1)
/*      */       {
/* 1275 */         spacing = 40 * level;
/*      */       }
/* 1277 */       if (childtype.equals("HAI"))
/*      */       {
/* 1279 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1280 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1281 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1283 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1284 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1285 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1286 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1287 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1288 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1289 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1290 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1291 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1292 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1293 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1295 */         if (!forInventory)
/*      */         {
/* 1297 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1300 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1302 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1304 */           actions = editlink + actions;
/*      */         }
/* 1306 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1308 */           actions = actions + associatelink;
/*      */         }
/* 1310 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1311 */         String arrowimg = "";
/* 1312 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1314 */           actions = "";
/* 1315 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1316 */           checkbox = "";
/* 1317 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1319 */         if (isIt360)
/*      */         {
/* 1321 */           actionimg = "";
/* 1322 */           actions = "";
/* 1323 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1324 */           checkbox = "";
/*      */         }
/*      */         
/* 1327 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1329 */           actions = "";
/*      */         }
/* 1331 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1333 */           checkbox = "";
/*      */         }
/*      */         
/* 1336 */         String resourcelink = "";
/*      */         
/* 1338 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1340 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1344 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1347 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1348 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1349 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1350 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1351 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1352 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1353 */         if (!isIt360)
/*      */         {
/* 1355 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1359 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1362 */         toreturn.append("</tr>");
/* 1363 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1365 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1366 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1370 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1371 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1374 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1378 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1380 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1381 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1382 */             toreturn.append(assocMessage);
/* 1383 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1384 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1385 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1386 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1392 */         String resourcelink = null;
/* 1393 */         boolean hideEditLink = false;
/* 1394 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1396 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1397 */           hideEditLink = true;
/* 1398 */           if (isIt360)
/*      */           {
/* 1400 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1404 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1406 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1408 */           hideEditLink = true;
/* 1409 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1410 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1415 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1418 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1419 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1420 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1421 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1422 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1423 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1424 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1425 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1426 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1427 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1428 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1429 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1430 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1432 */         if (hideEditLink)
/*      */         {
/* 1434 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1436 */         if (!forInventory)
/*      */         {
/* 1438 */           removefromgroup = "";
/*      */         }
/* 1440 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1441 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1442 */           actions = actions + configcustomfields;
/*      */         }
/* 1444 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1446 */           actions = editlink + actions;
/*      */         }
/* 1448 */         String managedLink = "";
/* 1449 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1451 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1452 */           actions = "";
/* 1453 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1454 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1457 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1459 */           checkbox = "";
/*      */         }
/*      */         
/* 1462 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1464 */           actions = "";
/*      */         }
/* 1466 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1467 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1468 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1469 */         if (isIt360)
/*      */         {
/* 1471 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1475 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1477 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1478 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1479 */         if (!isIt360)
/*      */         {
/* 1481 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1485 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1487 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1490 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1497 */       StringBuilder toreturn = new StringBuilder();
/* 1498 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1499 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1500 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1501 */       String title = "";
/* 1502 */       message = EnterpriseUtil.decodeString(message);
/* 1503 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1504 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1505 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1507 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1509 */       else if ("5".equals(severity))
/*      */       {
/* 1511 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1515 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1517 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1518 */       toreturn.append(v);
/*      */       
/* 1520 */       toreturn.append(link);
/* 1521 */       if (severity == null)
/*      */       {
/* 1523 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1525 */       else if (severity.equals("5"))
/*      */       {
/* 1527 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1529 */       else if (severity.equals("4"))
/*      */       {
/* 1531 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1533 */       else if (severity.equals("1"))
/*      */       {
/* 1535 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1540 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1542 */       toreturn.append("</a>");
/* 1543 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1547 */       ex.printStackTrace();
/*      */     }
/* 1549 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1556 */       StringBuilder toreturn = new StringBuilder();
/* 1557 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1558 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1559 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1560 */       if (message == null)
/*      */       {
/* 1562 */         message = "";
/*      */       }
/*      */       
/* 1565 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1566 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1568 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1569 */       toreturn.append(v);
/*      */       
/* 1571 */       toreturn.append(link);
/*      */       
/* 1573 */       if (severity == null)
/*      */       {
/* 1575 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1577 */       else if (severity.equals("5"))
/*      */       {
/* 1579 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1581 */       else if (severity.equals("1"))
/*      */       {
/* 1583 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1588 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1590 */       toreturn.append("</a>");
/* 1591 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1597 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1600 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1601 */     if (invokeActions != null) {
/* 1602 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1603 */       while (iterator.hasNext()) {
/* 1604 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1605 */         if (actionmap.containsKey(actionid)) {
/* 1606 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1611 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1615 */     String actionLink = "";
/* 1616 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1617 */     String query = "";
/* 1618 */     ResultSet rs = null;
/* 1619 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1620 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1621 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1622 */       actionLink = "method=" + methodName;
/*      */     }
/* 1624 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1625 */       actionLink = methodName;
/*      */     }
/* 1627 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1628 */     Iterator itr = methodarglist.iterator();
/* 1629 */     boolean isfirstparam = true;
/* 1630 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1631 */     while (itr.hasNext()) {
/* 1632 */       HashMap argmap = (HashMap)itr.next();
/* 1633 */       String argtype = (String)argmap.get("TYPE");
/* 1634 */       String argname = (String)argmap.get("IDENTITY");
/* 1635 */       String paramname = (String)argmap.get("PARAMETER");
/* 1636 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1637 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1638 */         isfirstparam = false;
/* 1639 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1641 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1645 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1649 */         actionLink = actionLink + "&";
/*      */       }
/* 1651 */       String paramValue = null;
/* 1652 */       String tempargname = argname;
/* 1653 */       if (commonValues.getProperty(tempargname) != null) {
/* 1654 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1657 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1658 */           String dbType = DBQueryUtil.getDBType();
/* 1659 */           if (dbType.equals("mysql")) {
/* 1660 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1663 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1665 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1667 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1668 */             if (rs.next()) {
/* 1669 */               paramValue = rs.getString("VALUE");
/* 1670 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1674 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1678 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1681 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1686 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1687 */           paramValue = rowId;
/*      */         }
/* 1689 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1690 */           paramValue = managedObjectName;
/*      */         }
/* 1692 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1693 */           paramValue = resID;
/*      */         }
/* 1695 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1696 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1699 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1701 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1702 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1703 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1705 */     return actionLink;
/*      */   }
/*      */   
/* 1708 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1709 */     String dependentAttribute = null;
/* 1710 */     String align = "left";
/*      */     
/* 1712 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1713 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1714 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1715 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1716 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1717 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1718 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1719 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1720 */       align = "center";
/*      */     }
/*      */     
/* 1723 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1724 */     String actualdata = "";
/*      */     
/* 1726 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1727 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1728 */         actualdata = availValue;
/*      */       }
/* 1730 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1731 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1735 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1736 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1739 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1745 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1746 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1747 */       toreturn.append("<table>");
/* 1748 */       toreturn.append("<tr>");
/* 1749 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1750 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1751 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1752 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1753 */         String toolTip = "";
/* 1754 */         String hideClass = "";
/* 1755 */         String textStyle = "";
/* 1756 */         boolean isreferenced = true;
/* 1757 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1758 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1759 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1760 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1762 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1763 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1764 */           while (valueList.hasMoreTokens()) {
/* 1765 */             String dependentVal = valueList.nextToken();
/* 1766 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1767 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1768 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1770 */               toolTip = "";
/* 1771 */               hideClass = "";
/* 1772 */               isreferenced = false;
/* 1773 */               textStyle = "disabledtext";
/* 1774 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1778 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1779 */           toolTip = "";
/* 1780 */           hideClass = "";
/* 1781 */           isreferenced = false;
/* 1782 */           textStyle = "disabledtext";
/* 1783 */           if (dependentImageMap != null) {
/* 1784 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1785 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1788 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1792 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1793 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1794 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1795 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1796 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1797 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1799 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1800 */           if (isreferenced) {
/* 1801 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1805 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1806 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1807 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1808 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1809 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1810 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1812 */           toreturn.append("</span>");
/* 1813 */           toreturn.append("</a>");
/* 1814 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1817 */       toreturn.append("</tr>");
/* 1818 */       toreturn.append("</table>");
/* 1819 */       toreturn.append("</td>");
/*      */     } else {
/* 1821 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1824 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1828 */     String colTime = null;
/* 1829 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1830 */     if ((rows != null) && (rows.size() > 0)) {
/* 1831 */       Iterator<String> itr = rows.iterator();
/* 1832 */       String maxColQuery = "";
/* 1833 */       for (;;) { if (itr.hasNext()) {
/* 1834 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1835 */           ResultSet maxCol = null;
/*      */           try {
/* 1837 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1838 */             while (maxCol.next()) {
/* 1839 */               if (colTime == null) {
/* 1840 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1843 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1852 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1854 */               if (maxCol != null)
/* 1855 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1857 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1852 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1854 */               if (maxCol != null)
/* 1855 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1857 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1862 */     return colTime;
/*      */   }
/*      */   
/* 1865 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1866 */     tablename = null;
/* 1867 */     ResultSet rsTable = null;
/* 1868 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1870 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1871 */       while (rsTable.next()) {
/* 1872 */         tablename = rsTable.getString("DATATABLE");
/* 1873 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1874 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1887 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1878 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1881 */         if (rsTable != null)
/* 1882 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1884 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1890 */     String argsList = "";
/* 1891 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1893 */       if (showArgsMap.get(row) != null) {
/* 1894 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1895 */         if (showArgslist != null) {
/* 1896 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1897 */             if (argsList.trim().equals("")) {
/* 1898 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1901 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1908 */       e.printStackTrace();
/* 1909 */       return "";
/*      */     }
/* 1911 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1916 */     String argsList = "";
/* 1917 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1920 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1922 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1923 */         if (hideArgsList != null)
/*      */         {
/* 1925 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1927 */             if (argsList.trim().equals(""))
/*      */             {
/* 1929 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1933 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1941 */       ex.printStackTrace();
/*      */     }
/* 1943 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1947 */     StringBuilder toreturn = new StringBuilder();
/* 1948 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1955 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1956 */       Iterator itr = tActionList.iterator();
/* 1957 */       while (itr.hasNext()) {
/* 1958 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1959 */         String confirmmsg = "";
/* 1960 */         String link = "";
/* 1961 */         String isJSP = "NO";
/* 1962 */         HashMap tactionMap = (HashMap)itr.next();
/* 1963 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1964 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1965 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1966 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1967 */           (actionmap.containsKey(actionId))) {
/* 1968 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1969 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1970 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1971 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1972 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1974 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1980 */           if (isTableAction) {
/* 1981 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1984 */             tableName = "Link";
/* 1985 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1986 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1987 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1988 */             toreturn.append("</a></td>");
/*      */           }
/* 1990 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1991 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1992 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1993 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1999 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 2005 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 2007 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 2008 */       Properties prop = (Properties)node.getUserObject();
/* 2009 */       String mgID = prop.getProperty("label");
/* 2010 */       String mgName = prop.getProperty("value");
/* 2011 */       String isParent = prop.getProperty("isParent");
/* 2012 */       int mgIDint = Integer.parseInt(mgID);
/* 2013 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2015 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2017 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2018 */       if (node.getChildCount() > 0)
/*      */       {
/* 2020 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2022 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2024 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2026 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2030 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2035 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2037 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2039 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2041 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2045 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2048 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2049 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2051 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2055 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2057 */       if (node.getChildCount() > 0)
/*      */       {
/* 2059 */         builder.append("<UL>");
/* 2060 */         printMGTree(node, builder);
/* 2061 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2066 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2067 */     StringBuffer toReturn = new StringBuffer();
/* 2068 */     String table = "-";
/*      */     try {
/* 2070 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2071 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2072 */       float total = 0.0F;
/* 2073 */       while (it.hasNext()) {
/* 2074 */         String attName = (String)it.next();
/* 2075 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2076 */         boolean roundOffData = false;
/* 2077 */         if ((data != null) && (!data.equals(""))) {
/* 2078 */           if (data.indexOf(",") != -1) {
/* 2079 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2082 */             float value = Float.parseFloat(data);
/* 2083 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2086 */             total += value;
/* 2087 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2090 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2095 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2096 */       while (attVsWidthList.hasNext()) {
/* 2097 */         String attName = (String)attVsWidthList.next();
/* 2098 */         String data = (String)attVsWidthProps.get(attName);
/* 2099 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2100 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2101 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2102 */         String className = (String)graphDetails.get("ClassName");
/* 2103 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2104 */         if (percentage < 1.0F)
/*      */         {
/* 2106 */           data = percentage + "";
/*      */         }
/* 2108 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2110 */       if (toReturn.length() > 0) {
/* 2111 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2115 */       e.printStackTrace();
/*      */     }
/* 2117 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2123 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2124 */     List<String> criticalThresholdValues = AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2125 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2126 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2127 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2128 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2129 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2130 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2131 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2134 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2135 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2136 */       splitvalues[0] = multiplecondition.toString();
/* 2137 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2140 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2145 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2146 */     if (thresholdType != 3) {
/* 2147 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2148 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2149 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2150 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2151 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2152 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2154 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2155 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2156 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2157 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2158 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2159 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2161 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2162 */     if (updateSelected != null) {
/* 2163 */       updateSelected[0] = "selected";
/*      */     }
/* 2165 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2170 */       StringBuffer toreturn = new StringBuffer("");
/* 2171 */       if (commaSeparatedMsgId != null) {
/* 2172 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2173 */         int count = 0;
/* 2174 */         while (msgids.hasMoreTokens()) {
/* 2175 */           String id = msgids.nextToken();
/* 2176 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2177 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2178 */           count++;
/* 2179 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2180 */             if (toreturn.length() == 0) {
/* 2181 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2183 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2184 */             if (!image.trim().equals("")) {
/* 2185 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2187 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2188 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2191 */         if (toreturn.length() > 0) {
/* 2192 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2196 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2199 */       e.printStackTrace(); }
/* 2200 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2206 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2212 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2213 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2214 */     _jspx_dependants.put("/jsp/chooseStorageresource.jsp", Long.valueOf(1473429417000L));
/* 2215 */     _jspx_dependants.put("/jsp/chooseSite24x7resource.jsp", Long.valueOf(1473429417000L));
/* 2216 */     _jspx_dependants.put("/jsp/chooseExternalresource.jsp", Long.valueOf(1473429417000L));
/* 2217 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private ExpressionFactory _el_expressionfactory;
/*      */   private InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2242 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2246 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fcatch = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2264 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2268 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2269 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2270 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2271 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2272 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2273 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2275 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2276 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2277 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2278 */     this._005fjspx_005ftagPool_005fc_005fcatch.release();
/* 2279 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2280 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2281 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/* 2282 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2283 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.release();
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2291 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2294 */     JspWriter out = null;
/* 2295 */     Object page = this;
/* 2296 */     JspWriter _jspx_out = null;
/* 2297 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2301 */       response.setContentType("text/html;charset=UTF-8");
/* 2302 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2304 */       _jspx_page_context = pageContext;
/* 2305 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2306 */       ServletConfig config = pageContext.getServletConfig();
/* 2307 */       session = pageContext.getSession();
/* 2308 */       out = pageContext.getOut();
/* 2309 */       _jspx_out = out;
/*      */       
/* 2311 */       out.write("<!DOCTYPE html>\n");
/* 2312 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n");
/* 2313 */       ManagedApplication mo = null;
/* 2314 */       synchronized (application) {
/* 2315 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2316 */         if (mo == null) {
/* 2317 */           mo = new ManagedApplication();
/* 2318 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2321 */       out.write(10);
/* 2322 */       Hashtable motypedisplaynames = null;
/* 2323 */       synchronized (application) {
/* 2324 */         motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2325 */         if (motypedisplaynames == null) {
/* 2326 */           motypedisplaynames = new Hashtable();
/* 2327 */           _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */         }
/*      */       }
/* 2330 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n\n\n\n\n");
/*      */       
/*      */ 
/* 2333 */       String resourceName = request.getParameter("type");
/*      */       
/* 2335 */       String typeDisplayString = request.getParameter("type");
/* 2336 */       String appname = request.getParameter("name");
/* 2337 */       String haid = request.getParameter("haid");
/* 2338 */       String bgcolour = "class=\"whitegrayborder\"";
/* 2339 */       String prevpagelinktext = "am.webclient.monitorgroupdetails.backtomonitorgroup.text";
/* 2340 */       String prevpagelink = "/showapplication.do?haid=" + haid + "&method=showApplication";
/* 2341 */       String fromwhere = "detailspage";
/* 2342 */       Hashtable table = mo.getDistinctManagedObjects();
/*      */       
/* 2344 */       motypedisplaynames.put("All", "All Monitor");
/* 2345 */       String resName = (String)motypedisplaynames.get(resourceName);
/*      */       
/* 2347 */       if (resourceName.equals("RMI")) {
/* 2348 */         resName = "AdventNet JMX Agent";
/*      */       }
/* 2350 */       String selectedscheme = null;
/* 2351 */       boolean slimLayout = false;
/* 2352 */       if (session.getAttribute("selectedscheme") != null)
/*      */       {
/* 2354 */         selectedscheme = (String)session.getAttribute("selectedscheme");
/*      */       }
/*      */       
/* 2357 */       HashMap<String, String> trimmedNameMap = (HashMap)request.getAttribute("trimmedChildMonitorInfo");
/* 2358 */       if ((selectedscheme != null) && (selectedscheme.equals("slim")))
/*      */       {
/* 2360 */         slimLayout = true;
/*      */       }
/* 2362 */       if ((request.getAttribute("fromwhere") != null) && (request.getAttribute("fromwhere").equals("monitorgroupview")))
/*      */       {
/* 2364 */         prevpagelinktext = "am.webclient.monitorgroupview.back.text";
/* 2365 */         prevpagelink = "/showresource.do?method=showMonitorGroupView";
/* 2366 */         fromwhere = "monitorgroupview";
/*      */       }
/*      */       
/* 2369 */       out.write(10);
/*      */       
/* 2371 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2372 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2373 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2375 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2376 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2377 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2379 */           out.write(10);
/*      */           
/* 2381 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2382 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2383 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2385 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */           
/* 2387 */           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.monitorgroupdetails.chooseresourceheading.text"));
/* 2388 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2389 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2390 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2393 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2394 */           out.write(10);
/* 2395 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2397 */           out.write(10);
/* 2398 */           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2400 */           out.write(10);
/*      */           
/* 2402 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2403 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2404 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2406 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2408 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2409 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2410 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2411 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2412 */               out = _jspx_page_context.pushBody();
/* 2413 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2414 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2417 */               out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/chosen.jquery.min.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/jquery.dataTables.min.js\"></SCRIPT>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n\n");
/* 2418 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2420 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2421 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2422 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2424 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2426 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2428 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2430 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2431 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2432 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2433 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2436 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2437 */               String available = null;
/* 2438 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2439 */               out.write(10);
/*      */               
/* 2441 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2442 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2443 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2445 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2447 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2449 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2451 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2452 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2453 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2454 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2457 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2458 */               String unavailable = null;
/* 2459 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2460 */               out.write(10);
/*      */               
/* 2462 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2463 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2464 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2466 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2468 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2470 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2472 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2473 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2474 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2475 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2478 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2479 */               String unmanaged = null;
/* 2480 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2481 */               out.write(10);
/*      */               
/* 2483 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2484 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2485 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2487 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2489 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2491 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2493 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2494 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2495 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2496 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2499 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2500 */               String scheduled = null;
/* 2501 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2502 */               out.write(10);
/*      */               
/* 2504 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2505 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2506 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2508 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2510 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2512 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2514 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2515 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2516 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2517 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2520 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2521 */               String critical = null;
/* 2522 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2523 */               out.write(10);
/*      */               
/* 2525 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2526 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2527 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2529 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2531 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2533 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2535 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2536 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2537 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2538 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2541 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2542 */               String clear = null;
/* 2543 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2544 */               out.write(10);
/*      */               
/* 2546 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2547 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2548 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2550 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2552 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2554 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2556 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2557 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2558 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2559 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2562 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2563 */               String warning = null;
/* 2564 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2565 */               out.write(10);
/* 2566 */               out.write(10);
/*      */               
/* 2568 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2569 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2571 */               out.write(10);
/* 2572 */               out.write(10);
/* 2573 */               out.write(10);
/* 2574 */               out.write("\n\n\n<script>\nfunction BacktoMG()\n{\nlocation.href=\"/showapplication.do?haid=\"+document.forms[1].haid.value+\"&method=showApplication\"\n\n}\nfunction myOnLoad()\n{\n\tSORTTABLENAME = 'NewMG';\n\tvar numberOfColumnsToBeSorted =1;\n\tvar ignoreCheckBox =true;\n\tvar ignoreDefaultSorting=true;\n\tsortables_init(numberOfColumnsToBeSorted,ignoreCheckBox,ignoreDefaultSorting);\n\tmyOnLoad1();\n\n}\nfunction myOnLoad1()\n{\nSORTTABLENAME= 'NewMGSelected';\n        var numberOfColumnsToBeSorted2 =1;\n\tvar ignoreCheckBox =true;\n\tvar ignoreDefaultSorting2=true;\n\tsortables_init(numberOfColumnsToBeSorted2,ignoreCheckBox,ignoreDefaultSorting2);\n}\nfunction fnSelectAll(e,formindex, checkboxstr)\n{\n\tvar temp=formindex;\n\n\tif(");
/* 2575 */               out.print(slimLayout);
/* 2576 */               out.write(")\n\t{\n\n\t\ttemp=formindex-1;\n\t}\n\tToggleAll(e,document.forms[temp],checkboxstr)\n}\nfunction fnDisplayName(combo)\n{\n\tvar v=combo.options[combo.selectedIndex].text;\n\tdocument.forms[formindex].displayname.value=v;\n}\n\nfunction fnFormSubmit(a,temp,goback)\n{\n  \t    ");
/*      */               
/* 2578 */               PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2579 */               _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2580 */               _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2582 */               _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2583 */               int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2584 */               if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                 for (;;) {
/* 2586 */                   out.write("\n\t\t alert(\"");
/* 2587 */                   out.print(FormatUtil.getString("am.webclient.historydata.jsalertfordemo.text"));
/* 2588 */                   out.write("\")\n\t\t return\n\t\t");
/* 2589 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2590 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2594 */               if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2595 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */               }
/*      */               
/* 2598 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2599 */               out.write("\n        var name='selectedresource';\n        var msg = ' ");
/* 2600 */               out.print(FormatUtil.getString("add"));
/* 2601 */               out.write("';\n        if(temp == 2) {\n            name ='monitors';\n            msg = '");
/* 2602 */               out.print(FormatUtil.getString("remove"));
/* 2603 */               out.write("';\n\t\t}\n\t\tvar formindex=temp;\n\t\tif(");
/* 2604 */               out.print(slimLayout);
/* 2605 */               out.write(")\n\t\t{\n\t\t\tformindex=temp-1;\n\t\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 2606 */               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 2607 */               out.write(" ' + msg);\n\t\treturn;\n\t}\t\n\tif(goback)\n\t{\n\t\tdocument.getElementsByName(\"goback\")[0].value='true';\t\n\t}\n\telse\n\t{\n\t\tdocument.getElementsByName(\"goback\")[0].value='false';\t\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].submit();\n\t\n}\n\nvar lastChecked = null; \n$(document).ready(function() {\n\tjQuery(\".chosenselect\").chosen()\t// NO I18N \n    var $chkboxes = $('.chkbox');//No I18N\n    $chkboxes.click(function(e) {\n        if(!lastChecked) {\t\t\n            lastChecked = this;\n            return;\n        }\n\n        if(e.shiftKey) {\t\t    \n            var start = $chkboxes.index(this);\n            var end = $chkboxes.index(lastChecked); \n\n            $chkboxes.slice(Math.min(start,end), Math.max(start,end)+ 1).attr('checked', lastChecked.checked);//No I18N\n\n        }\n\n        lastChecked = this;//No I18N\n    });\n    \n    var table = $('#NewMG').DataTable( {\n        \"paging\":   false,\t\t// NO I18N \n        \"ordering\": false,\t\t// NO I18N \n        \"info\":     false\t\t// NO I18N \n    } );\n \t$('#NewMG_filter').css({\"display\":\"none\"});\t\t// NO I18N \n");
/* 2608 */               out.write(" \n\t$('#searchMonName').keyup(function(){\n      table.search($(this).val()).draw() ;\n\t})\n    $('#NewMG tbody').on( 'click', 'tr', function () {\t\t// NO I18N \n        $(this).toggleClass('selected');\t\t// NO I18N \n    } );\n});\n\n\n\nfunction fnFormSubmitMg(a,temp)\n{\n\tvar name='selectedresourceMg';\n\tvar msg = ' ");
/* 2609 */               out.print(FormatUtil.getString("add"));
/* 2610 */               out.write("';//No I18N\n\tif(temp == 2) {\n\t\tname ='monitorsMg';\n\t\tmsg = '");
/* 2611 */               out.print(FormatUtil.getString("remove"));
/* 2612 */               out.write("';//No I18N\n\t}\n    var formindex=temp;\n\tif(");
/* 2613 */               out.print(slimLayout);
/* 2614 */               out.write(")\n\t{\n\t  formindex=temp-1;\n\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 2615 */               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 2616 */               out.write(" ' + msg);\n\t\treturn;\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].MonitorGroup.value=\"MonitorGroup\";\n\tdocument.forms[formindex].submit();\n}\n\nfunction loadURL(selType) {\n\n\n         window.location.href='/showresource.do?haid=' + document.forms[1].haid.value + '&type=' + selType +'&method=getMonitorForm&typeSelectedFromCombo=true';\n}\n\n/*function myOnLoad()\n{\n  alert(\"HAI\");\n  loadOpManDevicesByAJAX();\n}\n\nfunction loadOpManDevicesByAJAX()\n{\n\tURL='/showresource.do?type=All&method=getOpManMonitorForm&haid=");
/* 2617 */               out.print(request.getParameter("haid"));
/* 2618 */               out.write("&name=");
/* 2619 */               out.print(request.getParameter("name"));
/* 2620 */               out.write("&type=");
/* 2621 */               out.print(request.getParameter("type"));
/* 2622 */               out.write("';\n\thttp2=getHTTPObject();\n\thttp2.onreadystatechange = getOpManDeviceDetails;\n\thttp2.open(\"GET\", URL, true);\n\thttp2.send(null);\n}\n\nfunction getOpManDeviceDetails()\n{\n\tif(http2.readyState == 4 && http2.status == 200)\n\t{\n\t\tdocument.getElementById(\"actionstatus\").innerHTML =\"&nbsp;\";\n\t\tdocument.getElementById(\"opmandevicedetails\").innerHTML = http2.responseText;\n\t}\n}*/\n\n</script>\n\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n\n");
/*      */               
/* 2624 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2625 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2626 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2628 */               _jspx_th_html_005fform_005f0.setAction("/showresource.do");
/*      */               
/* 2630 */               _jspx_th_html_005fform_005f0.setMethod("post");
/*      */               
/* 2632 */               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2633 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2634 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 2636 */                   out.write("\n<input type=\"hidden\" name=\"org.apache.struts.taglib.html.TOKEN\" value=\"");
/* 2637 */                   out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 2638 */                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"addResource\">\n<input type=\"hidden\" name=\"goback\" value=\"false\">\n<input type=\"hidden\" name=\"savetype\" value=\"1\">\n<input type=\"hidden\" name=\"monitortype\" value=\"network\">\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2639 */                   out.print(appname);
/* 2640 */                   out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2641 */                   out.print(haid);
/* 2642 */                   out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2643 */                   if (_jspx_meth_c_005fout_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2645 */                   out.write("\">\n<input type=\"hidden\" name=\"MonitorGroup\" value=\"\">\n<input type=\"hidden\" name=\"fromwhere\" value=\"");
/* 2646 */                   out.print(fromwhere);
/* 2647 */                   out.write(34);
/* 2648 */                   out.write(62);
/* 2649 */                   out.write(10);
/*      */                   
/* 2651 */                   String message1 = request.getParameter("message");
/* 2652 */                   String redirect = "/showresource.do?haid=" + haid + "&type=" + resourceName + "&method=getMonitorForm";
/* 2653 */                   String encodedurl = URLEncoder.encode(redirect);
/* 2654 */                   String initMonLink = "";
/* 2655 */                   String internaltype = null;
/* 2656 */                   if ((resourceName.equals("Linux")) || (resourceName.equals("WindowsNT")) || (resourceName.equals("Windows 2000")) || (resourceName.equals("SUN")) || (resourceName.equals("Sun Solaris")) || (resourceName.equals("Windows")) || (resourceName.equals("Unknown")))
/*      */                   {
/* 2658 */                     internaltype = "System";
/*      */                   }
/*      */                   else
/*      */                   {
/* 2662 */                     internaltype = resourceName;
/*      */                   }
/*      */                   
/* 2665 */                   initMonLink = "/adminAction.do?method=reloadHostDiscoveryForm&haid=" + haid + "&appname=" + appname + "&type=" + internaltype + "&addtoha=true&fromAssociate=true&redirectto=" + encodedurl;
/* 2666 */                   boolean allTypes = resourceName.equals("All");
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 2671 */                   out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n  ");
/*      */                   
/* 2673 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2674 */                   _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2675 */                   _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 2677 */                   _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2678 */                   int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2679 */                   if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                     for (;;) {
/* 2681 */                       out.write(10);
/* 2682 */                       out.write(32);
/* 2683 */                       out.write(32);
/* 2684 */                       out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                       
/* 2686 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2687 */                       _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2688 */                       _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                       
/* 2690 */                       _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 2691 */                       int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2692 */                       if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                         for (;;) {
/* 2694 */                           out.write(10);
/*      */                           
/*      */ 
/* 2697 */                           if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                           {
/*      */ 
/* 2700 */                             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2701 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2702 */                             out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2703 */                             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2704 */                             out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2705 */                             out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2706 */                             out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2707 */                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2708 */                             out.write("\n </span></td>\n  <tr>\n  ");
/*      */                             
/* 2710 */                             int failedNumber = 1;
/*      */                             
/* 2712 */                             out.write(10);
/*      */                             
/* 2714 */                             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2715 */                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2716 */                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                             
/* 2718 */                             _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                             
/* 2720 */                             _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                             
/* 2722 */                             _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                             
/* 2724 */                             _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2725 */                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2726 */                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2727 */                               ArrayList row = null;
/* 2728 */                               Integer i = null;
/* 2729 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2730 */                                 out = _jspx_page_context.pushBody();
/* 2731 */                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2732 */                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                               }
/* 2734 */                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2735 */                               i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                               for (;;) {
/* 2737 */                                 out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2738 */                                 out.print(row.get(0));
/* 2739 */                                 out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2740 */                                 out.print(row.get(1));
/* 2741 */                                 out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                 
/* 2743 */                                 if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                 {
/* 2745 */                                   request.setAttribute("isDiscoverySuccess", "true");
/*      */                                   
/* 2747 */                                   out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2748 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2749 */                                   out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2754 */                                   request.setAttribute("isDiscoverySuccess", "false");
/*      */                                   
/* 2756 */                                   out.write("\n      <img alt=\"");
/* 2757 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2758 */                                   out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2762 */                                 out.write("\n      <span class=\"bodytextbold\">");
/* 2763 */                                 out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2764 */                                 out.write("</span> </td>\n\n      ");
/*      */                                 
/* 2766 */                                 pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                 
/* 2768 */                                 out.write("\n                     ");
/*      */                                 
/* 2770 */                                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2771 */                                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2772 */                                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                 
/* 2774 */                                 _jspx_th_c_005fif_005f0.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2775 */                                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2776 */                                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                   for (;;) {
/* 2778 */                                     out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2779 */                                     out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2780 */                                     out.write("\n                                 ");
/* 2781 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2782 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2786 */                                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2787 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                 }
/*      */                                 
/* 2790 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2791 */                                 out.write("\n                                       ");
/*      */                                 
/* 2793 */                                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2794 */                                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2795 */                                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                 
/* 2797 */                                 _jspx_th_c_005fif_005f1.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2798 */                                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2799 */                                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                   for (;;) {
/* 2801 */                                     out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2802 */                                     out.print(row.get(3));
/* 2803 */                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                     
/* 2805 */                                     if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                     {
/* 2807 */                                       if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                       {
/* 2809 */                                         String fWhr = request.getParameter("hideFieldsForIT360");
/* 2810 */                                         if (fWhr == null)
/*      */                                         {
/* 2812 */                                           fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                         }
/*      */                                         
/* 2815 */                                         if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2816 */                                           (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                         {
/* 2818 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2819 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2820 */                                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                         }
/*      */                                       } }
/* 2823 */                                     if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                     {
/* 2825 */                                       failedNumber++;
/*      */                                       
/*      */ 
/* 2828 */                                       out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2829 */                                       out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/* 2830 */                                       out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                     }
/* 2832 */                                     out.write("\n                                                   ");
/* 2833 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2834 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2838 */                                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2839 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                 }
/*      */                                 
/* 2842 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2843 */                                 out.write(10);
/* 2844 */                                 out.write(10);
/* 2845 */                                 out.write(10);
/*      */                                 
/*      */ 
/* 2848 */                                 if (row.size() > 4)
/*      */                                 {
/*      */ 
/* 2851 */                                   out.write("<br>\n");
/* 2852 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2853 */                                   out.write(10);
/*      */                                 }
/*      */                                 
/*      */ 
/* 2857 */                                 out.write("\n</td>\n\n</tr>\n");
/* 2858 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2859 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2860 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/* 2861 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2864 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2865 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2868 */                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2869 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                             }
/*      */                             
/* 2872 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2873 */                             out.write("\n</table>\n");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 2878 */                             ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                             
/* 2880 */                             out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2881 */                             String mtype = (String)request.getAttribute("type");
/* 2882 */                             out.write(10);
/* 2883 */                             if (mtype.equals("File System Monitor")) {
/* 2884 */                               out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2885 */                               out.print(FormatUtil.getString("File/Directory Name"));
/* 2886 */                               out.write("</span> </td>\n");
/* 2887 */                             } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2888 */                               out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2889 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2890 */                               out.write("</span> </td>\n");
/*      */                             } else {
/* 2892 */                               out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2893 */                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2894 */                               out.write("</span> </td>\n");
/*      */                             }
/* 2896 */                             out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2897 */                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2898 */                             out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2899 */                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2900 */                             out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2901 */                             out.print(al1.get(0));
/* 2902 */                             out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                             
/* 2904 */                             if (al1.get(1).equals("Success"))
/*      */                             {
/* 2906 */                               request.setAttribute("isDiscoverySuccess", "true");
/*      */                               
/* 2908 */                               out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2909 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2910 */                               out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 2915 */                               request.setAttribute("isDiscoverySuccess", "false");
/*      */                               
/*      */ 
/* 2918 */                               out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 2922 */                             out.write("\n<span class=\"bodytextbold\">");
/* 2923 */                             out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2924 */                             out.write("</span> </td>\n");
/*      */                             
/* 2926 */                             if (al1.get(1).equals("Success"))
/*      */                             {
/* 2928 */                               boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2929 */                               if (isAdminServer) {
/* 2930 */                                 String masDisplayName = (String)al1.get(3);
/* 2931 */                                 String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                 
/* 2933 */                                 out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2934 */                                 out.print(format);
/* 2935 */                                 out.write("</td>\n");
/*      */                               }
/*      */                               else
/*      */                               {
/* 2939 */                                 out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2940 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2941 */                                 out.write("<br> ");
/* 2942 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2943 */                                 out.write("</td>\n");
/*      */                               }
/*      */                               
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 2950 */                               out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2951 */                               out.print(al1.get(2));
/* 2952 */                               out.write("</span></td>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 2956 */                             out.write("\n  </tr>\n</table>\n\n");
/*      */                           }
/*      */                           
/*      */ 
/* 2960 */                           out.write(10);
/* 2961 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2962 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2966 */                       if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2967 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                       }
/*      */                       
/* 2970 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2971 */                       out.write(10);
/* 2972 */                       out.write(10);
/* 2973 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2974 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2978 */                   if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2979 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                   }
/*      */                   
/* 2982 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2983 */                   out.write("\n\n\n\n\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td height=\"26\" valign=\"top\"  class=\"bodytext bcsign breadcrumb_btm_space\">\n\t");
/*      */                   
/* 2985 */                   CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch.get(CatchTag.class);
/* 2986 */                   _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 2987 */                   _jspx_th_c_005fcatch_005f0.setParent(_jspx_th_html_005fform_005f0);
/* 2988 */                   int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */                   try {
/* 2990 */                     int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 2991 */                     if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */                       for (;;) {
/* 2993 */                         out.write(10);
/* 2994 */                         out.write(9);
/* 2995 */                         request.setAttribute("breadcrumb", mo.getBreadCrumbForMO(haid));
/* 2996 */                         out.write(10);
/* 2997 */                         out.write(9);
/* 2998 */                         int evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 2999 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3003 */                     if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3011 */                       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3012 */                       this._005fjspx_005ftagPool_005fc_005fcatch.reuse(_jspx_th_c_005fcatch_005f0); return;
/*      */                     }
/*      */                   }
/*      */                   catch (Throwable _jspx_exception)
/*      */                   {
/*      */                     for (;;)
/*      */                     {
/* 3007 */                       int tmp5108_5107 = 0; int[] tmp5108_5105 = _jspx_push_body_count_c_005fcatch_005f0; int tmp5110_5109 = tmp5108_5105[tmp5108_5107];tmp5108_5105[tmp5108_5107] = (tmp5110_5109 - 1); if (tmp5110_5109 <= 0) break;
/* 3008 */                       out = _jspx_page_context.popBody(); }
/* 3009 */                     _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */                   } finally {
/* 3011 */                     _jspx_th_c_005fcatch_005f0.doFinally();
/* 3012 */                     this._005fjspx_005ftagPool_005fc_005fcatch.reuse(_jspx_th_c_005fcatch_005f0);
/*      */                   }
/* 3014 */                   out.write("\n\t<a href=\"/MyPage.do?method=viewDashBoard\" class=\"bcinactive\" >");
/* 3015 */                   out.print(FormatUtil.getString("am.webclient.hometab.text"));
/* 3016 */                   out.write("</a>\n\t&gt;\n                ");
/* 3017 */                   if (_jspx_meth_c_005fforEach_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3019 */                   out.write("\n    \t<span class='bcactivebig'>");
/* 3020 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3022 */                   out.write("</span>\n\n    </td>\n\n   <td align=\"right\">\n    <table >\n       <tr>\n        <td  class=\"buttons btn_back\" onClick=\"javascript:BacktoMG()\" align=\"center\" ><b>");
/* 3023 */                   out.print(FormatUtil.getString("am.webclient.monitorgroup.back"));
/* 3024 */                   out.write("</b></td>\n       </tr>\n    </table>\n   </td>\n\n  </tr>\n <tr><td colspan=\"2\">\n <table class=\"lrtbdarkborder\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"left\" width=\"99%\">\n<tbody><tr>\n\n<td class=\"actions-menu-left\"></td>\n<td class=\"bodytextbold tableheading\" align=\"left\" width=\"100%\">\n\n<span class='bodytext' style=\"font-weight: bold; vertical-align=middle;\">");
/* 3025 */                   out.print(FormatUtil.getString("am.monitorgroup.associatemonitors.txt"));
/* 3026 */                   out.write(58);
/* 3027 */                   out.write(32);
/* 3028 */                   if (_jspx_meth_c_005fout_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3030 */                   out.write("</span>\n\n</td>\n<td class=\"actions-menu-right\"></td>\n</tr>\n</tbody></table>\n \n </td></tr>\n  <tr>\n    <td  height=\"2\" colspan=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"12\"></td>\n  </tr>\n\n\n</table>\n ");
/*      */                   
/*      */ 
/* 3033 */                   if (request.getAttribute("showGroupOnly") == null)
/*      */                   {
/*      */ 
/* 3036 */                     out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    ");
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
/* 3047 */                     out.write("\n    <!--  <tr>\n                  <td class=\"bodytext\">Monitor Name</td>\n                  <td class=\"bodytext\"><input type=\"text\" name=\"monitorname\" value=\"\" size=\"36\" class=\"formtext\"  maxlength=\"100\"></td>\n                </tr> -->\n\n\n                \n    <tr>\n\t    <td colspan=\"7\"  width=\"49%\" valign=\"top\">\n\n\n\n\n\t    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" valign=\"top\" width=\"100%\">\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"AlarmHdrTopLeft\"/>\n\t\t\t\t\t<td class=\"AlarmHdrTopBg\">\n\n\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\t\t\t\t\t\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\" >\n\n\t\t\t\t\t<span class=\"bcactive\" > &nbsp;");
/* 3048 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.monitornotinmg.text"));
/* 3049 */                     out.write("</span></td>\n\t\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\">&nbsp;</td>\n                   ");
/* 3050 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 3051 */                       out.write("\n\t\t\t       <td valign=\"middle\"   class=\"bcactive\"> \n\t\t\t\t\t<div style=\"float:left\">&nbsp; <b  style=\"position:relative; bottom:0px;\">");
/* 3052 */                       out.print(FormatUtil.getString("am.webclient.filterby.text"));
/* 3053 */                       out.write("</b></div>\n\t\t\t\t   <div style=\"float:left; position:relative;bottom:2px; padding-left: 6px;\">\n\t\t\t\t   \t");
/* 3054 */                       if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 3056 */                       out.write("\n\t\t\t       </div>\n\t\t\t       </td>\n\t\t\t\t   ");
/*      */                     }
/* 3058 */                     out.write("\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t</table></td>\n\t\t\t\t\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t\t\t<td valign=\"top\">\n\n\n\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t\t    <tr>\n\n\t\t    <td style=\"padding-top:3px;\" class=\"AlarmboxedContent\" width=\"100%\" >\n\n\t\t    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t      <tr>\n\t\t          <td >\n\t\t           <!--text -->\n\t\t          </td>\n\t\t      </tr>\n\t\t      <tr>\n\t\t        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t        <tr>\n\n\t\t          <td class=\"AlarmInnerTopLeft\"/>\n\t\t          <td class=\"AlarmInnerTopBg\"/>\n\t\t          <td class=\"AlarmInnerTopRight\"/>\n\t\t        </tr>\n\t\t        <tr>\n\t\t          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\t\t                <td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t<tr>\n\n\t\t\t\t\t\t\t\t<td valign=\"top\"  width=\"100%\" >\n");
/* 3059 */                     out.write("\n\n\n\t\t\t</td>\n\t\t</tr>\n\n\t\t      <td  width=\"50%\" valign=\"top\"> ");
/*      */                     
/* 3061 */                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3062 */                     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3063 */                     _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 3065 */                     _jspx_th_logic_005fnotEmpty_005f2.setName("toconfigure");
/* 3066 */                     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3067 */                     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                       for (;;) {
/* 3069 */                         out.write("\n\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t          <tr>\n\n\t\t");
/* 3070 */                         if (allTypes) {
/* 3071 */                           out.write("\n\t\t<td colspan=\"3\">\n\t\t");
/*      */                         } else {
/* 3073 */                           out.write("\n\t\t<td colspan=\"2\" >\n\t\t");
/*      */                         }
/* 3075 */                         out.write("\n\t\t<table width=\"100%\" colspan=\"3\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\n\t\t<tr>\n\t\t   <td width=\"70%\" height=\"25\"   class=\"columnheadingnotop\" style=\"height:25px;\">\n\t\t   \t\t<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 3076 */                         out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 3077 */                         out.write("\" onClick=\"javascript:fnFormSubmit('2', '1')\"> &nbsp;&nbsp;&nbsp;");
/* 3078 */                         out.print(FormatUtil.getString("am.common.or.text"));
/* 3079 */                         out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 3080 */                         out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 3081 */                         out.write("\" onClick=\"javascript:fnFormSubmit('2', '1', 'true');\"></td><td align=\"right\"  class=\"columnheadingnotop\" style=\"padding-right:10px;\">&nbsp; ");
/* 3082 */                         if (!EnterpriseUtil.isAdminServer) {
/* 3083 */                           out.write("<input name=\"button\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 3084 */                           out.print(FormatUtil.getString("am.webclient.monitor.template.title"));
/* 3085 */                           out.write("\" onClick=\"window.location.href='/adminAction.do?method=reloadHostDiscoveryForm&type=Windows&restype=WindowsNT_Server&haid=");
/* 3086 */                           if (_jspx_meth_c_005fout_005f5(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/*      */                             return;
/* 3088 */                           out.write(39);
/* 3089 */                           out.write(34);
/* 3090 */                           out.write(62);
/*      */                         }
/* 3092 */                         out.write("</td>\n\t\t</tr>\n\t\t</table>\n\t\t</td>\n\t\t          </tr>\n\n\n\n\n\n\n\t\t</td>\n\t\t</tr>\n\n\n\t\t<td colspan=\"2\" >\n\n\n\t\t<table id=\"NewMG\" width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#fff;\">\n\t\t\t<thead>\t<tr><td colspan=\"3\" style=\"height:5px;\"></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td width=\"5%\" class=\" whitegrayborder\"><input type=\"checkbox\" class=\"chkbox\" onClick=\"javascript:fnSelectAll(this, '1', 'selectedresource')\" style=\"position:relative; left:1px;\"></td>\n\t\t\t\t<td width=\"75%\" class=\"bodytextbold whitegrayborder\" align=\"left\"><b class=\"bodytextbold\">");
/* 3093 */                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 3094 */                         out.write("</b>\n\t\t\t\t\t <input type=\"search\" id=\"searchMonName\" placeholder=\"Search By..\">\n\t\t\t\t</td>\n\t\t\t\t<td width=\"20%\" class=\"whitegrayborder\"><b class=\"bodytextbold\">");
/* 3095 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 3096 */                         out.write("</b></td>\n\n\n\t\t\t\t</tr>\n\t\t\t\t</thead>\n\t\t\t\t<tbody>\n\t\t          ");
/*      */                         
/* 3098 */                         IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3099 */                         _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 3100 */                         _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                         
/* 3102 */                         _jspx_th_logic_005fiterate_005f1.setName("toconfigure");
/*      */                         
/* 3104 */                         _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                         
/* 3106 */                         _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                         
/* 3108 */                         _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 3109 */                         int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 3110 */                         if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 3111 */                           ArrayList row = null;
/* 3112 */                           Integer j = null;
/* 3113 */                           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3114 */                             out = _jspx_page_context.pushBody();
/* 3115 */                             _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 3116 */                             _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                           }
/* 3118 */                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3119 */                           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                           for (;;) {
/* 3121 */                             out.write("\n\t\t          ");
/*      */                             
/* 3123 */                             String resid = (String)row.get(0);
/* 3124 */                             String name = (String)row.get(1);
/* 3125 */                             String image = (String)row.get(2);
/* 3126 */                             String toolTipTitle = name;
/* 3127 */                             if ((trimmedNameMap != null) && (trimmedNameMap.get(resid) != null))
/*      */                             {
/* 3129 */                               name = (String)trimmedNameMap.get(resid);
/*      */                             }
/*      */                             else
/*      */                             {
/* 3133 */                               name = FormatUtil.getTrimmedText(name, 45);
/*      */                             }
/* 3135 */                             if (j.intValue() % 2 == 0)
/*      */                             {
/* 3137 */                               bgcolour = "class=\"whitegrayborder\"";
/*      */                             }
/*      */                             else
/*      */                             {
/* 3141 */                               bgcolour = "class=\"yellowgrayborder\"";
/*      */                             }
/*      */                             
/* 3144 */                             out.write("\n\t\t          <tr>\n\t\t            <td width=\"5%\" ");
/* 3145 */                             out.print(bgcolour);
/* 3146 */                             out.write("><input type=\"checkbox\" class=\"chkbox\" name=\"selectedresource\" value=\"");
/* 3147 */                             out.print(resid);
/* 3148 */                             out.write("\"></td>\n\t\t\t    <td width=\"75%\" ");
/* 3149 */                             out.print(bgcolour);
/* 3150 */                             out.write(" align=\"left\"><a class=\"tooltip\" title=\"");
/* 3151 */                             out.print(toolTipTitle);
/* 3152 */                             out.write(34);
/* 3153 */                             out.write(62);
/* 3154 */                             out.print(name);
/* 3155 */                             out.write("</a></td>\n\t\t            <td width=\"20%\" ");
/* 3156 */                             out.print(bgcolour);
/* 3157 */                             out.write(" align=\"center\"><img src='");
/* 3158 */                             out.print(image);
/* 3159 */                             out.write("'></td>\n\t\t          </tr>\n\t\t          ");
/* 3160 */                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3161 */                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3162 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/* 3163 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3166 */                           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3167 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3170 */                         if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3171 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                         }
/*      */                         
/* 3174 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3175 */                         out.write("\n\t\t          </tbody>\n\t\t          </table>\n\n\t\t          </td>\n\n\n\t\t          <tr style=\"height:25px;\">\n\n\t\t            ");
/* 3176 */                         if (allTypes) {
/* 3177 */                           out.write("\n\t\t            <td colspan=\"3\" class=\"columnheadingnotop\" align=\"left\">\n\t\t            ");
/*      */                         } else {
/* 3179 */                           out.write("\n\t\t            <td  colspan=\"2\" class=\"columnheadingnotop\" align=\"left\">\n\t\t            ");
/*      */                         }
/* 3181 */                         out.write("\t\t        \n\t\t   \t\t<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 3182 */                         out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 3183 */                         out.write("\" onClick=\"javascript:fnFormSubmit('2', '1')\"> &nbsp;&nbsp;&nbsp;");
/* 3184 */                         out.print(FormatUtil.getString("am.common.or.text"));
/* 3185 */                         out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 3186 */                         out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 3187 */                         out.write("\" onClick=\"javascript:fnFormSubmit('2', '1', 'true');\">\t\t        \n\n\n\t\t\t</td>\n\t\t          </tr>\n\n\t\t        </table>\n\t\t        ");
/* 3188 */                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 3189 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3193 */                     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 3194 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                     }
/*      */                     
/* 3197 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3198 */                     out.write(32);
/*      */                     
/* 3200 */                     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3201 */                     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 3202 */                     _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 3204 */                     _jspx_th_logic_005fempty_005f0.setName("toconfigure");
/* 3205 */                     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 3206 */                     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                       for (;;) {
/* 3208 */                         out.write("\n\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"background-color:#fff;\">\n\t\t          <tr>\n\t\t<td >\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  ><tr>\n\t\t                <td colspan=\"2\" class=\"columnheading\">\n\n\n\t\t                </td>\n\n\t\t</tr></table>\n\t\t</td>\n\n\n\n\t\t          </tr>\n\t\t          <tr class=\"emptyTableMsg\">\n\n\t\t          <td  height=\"25\" class=\"bodytext\">&nbsp;");
/* 3209 */                         out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3210 */                         out.write(".</td>\n\t\t          </tr>\n\t\t        </table>\n\t\t        ");
/* 3211 */                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3212 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3216 */                     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3217 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                     }
/*      */                     
/* 3220 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3221 */                     out.write("\n\t\t\t\t");
/*      */                   }
/* 3223 */                   out.write("\n\t\t\t\t</td>\n\t\t\t\t");
/*      */                   
/* 3225 */                   if (request.getAttribute("showGroupOnly") != null)
/*      */                   {
/*      */ 
/* 3228 */                     out.write("\n\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t <tr>\n\t\t\t\t  <td width=\"49%\">\n\t\t\t\t   <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"right\" class=\"lrtbdarkborder\">\n\t\t\t\t    <tr>\n\t\t\t\t     <td class=\"tableheadingbborder\" height=\"25\">");
/* 3229 */                     out.print(FormatUtil.getString("haid.application.available.text"));
/* 3230 */                     out.write("</td>\n\t\t\t\t    </tr>\n\t\t\t\t\t");
/*      */                     
/* 3232 */                     EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3233 */                     _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3234 */                     _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 3236 */                     _jspx_th_logic_005fempty_005f1.setName("toconfigureMg");
/* 3237 */                     int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3238 */                     if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                       for (;;) {
/* 3240 */                         out.write("\n\t\t\t\t\t  <tr>\n\t\t\t\t\t   ");
/*      */                         
/* 3242 */                         if (request.getAttribute("errorMessage") != null)
/*      */                         {
/* 3244 */                           out.write("\n\t\t\t\t\t    <td  height=\"22\" class=\"emptyTableMsg\">&nbsp;<span>");
/* 3245 */                           out.print(request.getAttribute("errorMessage"));
/* 3246 */                           out.write(".</span></td>\n\t\t\t\t\t   ");
/*      */                         }
/*      */                         else {
/* 3249 */                           out.write("\n\t\t\t\t\t\t<td  height=\"22\" class=\"emptyTableMsg\">&nbsp;<span>");
/* 3250 */                           out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3251 */                           out.write(".</span></td>\n\t\t\t\t\t   ");
/*      */                         }
/* 3253 */                         out.write("\n\t\t\t\t\t  </tr>\n\t\t\t\t\t");
/* 3254 */                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3255 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3259 */                     if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3260 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                     }
/*      */                     
/* 3263 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3264 */                     out.write("\n\t\t\t\t\t");
/*      */                     
/* 3266 */                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3267 */                     _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 3268 */                     _jspx_th_logic_005fnotEmpty_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 3270 */                     _jspx_th_logic_005fnotEmpty_005f3.setName("toconfigureMg");
/* 3271 */                     int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 3272 */                     if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */                       for (;;) {
/* 3274 */                         out.write("\n\t\t\t\t    <tr>\n\t\t\t\t     <td class=\"columnheading\" ><input type=\"checkbox\" class=\"chkbox\" onClick=\"javascript:fnSelectAll(this, '1', 'selectedresourceMg')\">&nbsp;&nbsp;&nbsp;");
/* 3275 */                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 3276 */                         out.write("</td>\n\t\t\t\t    </tr>\n\t\t\t\t    <tr>\n\t\t\t\t     <td height=\"31\" class=\"tablebottom\" align=\"right\"><input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 3277 */                         out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 3278 */                         out.write("\" onClick=\"javascript:fnFormSubmitMg('2', '1')\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t    ");
/*      */                         
/* 3280 */                         IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3281 */                         _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 3282 */                         _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f3);
/*      */                         
/* 3284 */                         _jspx_th_logic_005fiterate_005f2.setName("toconfigureMg");
/*      */                         
/* 3286 */                         _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                         
/* 3288 */                         _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                         
/* 3290 */                         _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 3291 */                         int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 3292 */                         if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 3293 */                           ArrayList row = null;
/* 3294 */                           Integer j = null;
/* 3295 */                           if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3296 */                             out = _jspx_page_context.pushBody();
/* 3297 */                             _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 3298 */                             _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                           }
/* 3300 */                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3301 */                           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                           for (;;) {
/* 3303 */                             out.write("\n\t\t\t\t    ");
/*      */                             
/* 3305 */                             String resid = (String)row.get(0);
/* 3306 */                             String name = (String)row.get(1);
/* 3307 */                             String image = "";
/* 3308 */                             if (j.intValue() % 2 == 0)
/*      */                             {
/* 3310 */                               bgcolour = "class=\"whitegrayborder\"";
/*      */                             }
/*      */                             else
/*      */                             {
/* 3314 */                               bgcolour = "class=\"yellowgrayborder\"";
/*      */                             }
/*      */                             
/* 3317 */                             out.write("\n\t\t\t\t\t  <tr>\n\t\t\t\t\t   <td width=\"4%\" ");
/* 3318 */                             out.print(bgcolour);
/* 3319 */                             out.write("><input type=\"checkbox\" class=\"chkbox\" name=\"selectedresourceMg\" value=\"");
/* 3320 */                             out.print(resid);
/* 3321 */                             out.write("\"> &nbsp;&nbsp;<span class=\"bodytextbold\">");
/*      */                             
/* 3323 */                             Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 3324 */                             _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 3325 */                             _jspx_th_am_005fTruncate_005f0.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                             
/* 3327 */                             _jspx_th_am_005fTruncate_005f0.setTooltip("true");
/*      */                             
/* 3329 */                             _jspx_th_am_005fTruncate_005f0.setLength(50);
/* 3330 */                             int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 3331 */                             if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 3332 */                               if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3333 */                                 out = _jspx_page_context.pushBody();
/* 3334 */                                 _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 3335 */                                 _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3338 */                                 out.print(name);
/* 3339 */                                 int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 3340 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3343 */                               if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3344 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3347 */                             if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 3348 */                               this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0); return;
/*      */                             }
/*      */                             
/* 3351 */                             this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 3352 */                             out.write("</span></td> ");
/* 3353 */                             out.write("\n\t\t\t\t\t  </tr>\n\t\t\t\t\t ");
/* 3354 */                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 3355 */                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3356 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/* 3357 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3360 */                           if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3361 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3364 */                         if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 3365 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                         }
/*      */                         
/* 3368 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 3369 */                         out.write("\n\t\t\t\t\t <tr>\n\t\t\t\t\t  <td height=\"31\" class=\"tablebottom\" align=\"left\"><input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 3370 */                         out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 3371 */                         out.write("\" onClick=\"javascript:fnFormSubmitMg('2', '1')\">  </td> ");
/* 3372 */                         out.write("\n\t\t\t\t\t </tr>\n\t\t\t\t\t ");
/* 3373 */                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 3374 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3378 */                     if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 3379 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3); return;
/*      */                     }
/*      */                     
/* 3382 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 3383 */                     out.write("\n\t\t\t\t    </table>\n\t\t\t\t   </td>\n\t\t\t\t ");
/*      */                   }
/* 3385 */                   out.write(10);
/* 3386 */                   out.write(10);
/* 3387 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3388 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3392 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3393 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 3396 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3397 */               out.write("\n\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\n\n\n\n\t\t\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"25%\">\n\n\t\t\t\t\t\t\t\t<table width=\"100%\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td width=\"30%\" class=\"AlarmtxtSpace\" nowrap='nowrap' height=\"5\"></td>\n\n\t\t\t\t\t\t\t\t\t\t<td></td>\n\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"30%\">\t</td>\n\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t</table>\n\t\t            </td>\n\t\t          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\n\t\t        </tr>\n\n\t\t      </table></td>\n\t\t      </tr>\n\t\t     </table>\n\t\t     </td>\n\t\t  </tr>\n\t\t</table>\n\t\t</td>\n\t\t\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t\t\t\t\t<td class=\"AlarmCardMainBtmBg\"/>\n\t\t\t\t\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t\t\t\t\t</tr>\n\t\t\t</table>\n\n\n\n\n\n<td width=\"2%\"></td>\n<td valign=\"top\" width=\"49%\">\n");
/* 3398 */               boolean allTypes = resourceName.equals("All");
/* 3399 */               out.write(10);
/*      */               
/* 3401 */               FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3402 */               _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 3403 */               _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3405 */               _jspx_th_html_005fform_005f1.setAction("/removeMonitors.do");
/*      */               
/* 3407 */               _jspx_th_html_005fform_005f1.setMethod("post");
/*      */               
/* 3409 */               _jspx_th_html_005fform_005f1.setStyle("display:inline;");
/* 3410 */               int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 3411 */               if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                 for (;;) {
/* 3413 */                   out.write("\n<input type=\"hidden\" name=\"org.apache.struts.taglib.html.TOKEN\" value=\"");
/* 3414 */                   out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 3415 */                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"removeMonitors\">\n<input type=\"hidden\" name=\"savetype\" value=\"1\">\n<input type=\"hidden\" name=\"monitortype\" value=\"network\">\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 3416 */                   out.print(appname);
/* 3417 */                   out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 3418 */                   out.print(haid);
/* 3419 */                   out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 3420 */                   out.print(resourceName);
/* 3421 */                   out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 3422 */                   out.print(resourceName);
/* 3423 */                   out.write("\">\n<input type=\"hidden\" name=\"message\" value=\"\">\n<input type=\"hidden\" name=\"MonitorGroup\" value=\"\">\n<input type=\"hidden\" name=\"fromwhere\" value=\"");
/* 3424 */                   out.print(fromwhere);
/* 3425 */                   out.write("\">\n\n\n\n\n\n\n\n");
/*      */                   
/* 3427 */                   if (request.getAttribute("showGroupOnly") == null)
/*      */                   {
/*      */ 
/* 3430 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\t\t<table width=\"98%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"AlarmHdrTopLeft\"/>\n\t\t\t\t\t<td class=\"AlarmHdrTopBg\">\n\t\t\t\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td  valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\">\n\t\t\t<span class=\"bcactive\">&nbsp; ");
/* 3431 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.monitorinmg.text"));
/* 3432 */                     out.write("</span></td>\n\t\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\">&nbsp;</td>\n\t\t\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t</table></td>\n\t\t\t\t\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t\t\t<td valign=\"top\">\n\n\n\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t\t    <tr>\n\n\t\t    <td style=\"padding-top:3px;\" class=\"AlarmboxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t      <tr>\n\t\t          <td >\n\t\t           <!--text -->\n\t\t          </td>\n\t\t      </tr>\n\t\t      <tr>\n\t\t        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t        <tr>\n\n\t\t          <td class=\"AlarmInnerTopLeft\"/>\n\t\t          <td class=\"AlarmInnerTopBg\"/>\n\t\t          <td class=\"AlarmInnerTopRight\"/>\n\t\t        </tr>\n\t\t        <tr>\n\t\t          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\t\t                <td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n");
/* 3433 */                     out.write("\n\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t<tr>\n\n\t\t\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\n\t\t");
/*      */                     
/* 3435 */                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3436 */                     _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 3437 */                     _jspx_th_logic_005fnotEmpty_005f4.setParent(_jspx_th_html_005fform_005f1);
/*      */                     
/* 3439 */                     _jspx_th_logic_005fnotEmpty_005f4.setName("configured");
/* 3440 */                     int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 3441 */                     if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */                       for (;;) {
/* 3443 */                         out.write("\n\t\t        <table width=\"100%\"  class=\"lrtbdarkborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t          <tr style=\"height:25px;\">\n\t\t    ");
/* 3444 */                         if (allTypes) {
/* 3445 */                           out.write("\n\t\t            <td colspan=\"3\"  class=\"columnheadingnotop\" >\n\n\t\t    ");
/*      */                         } else {
/* 3447 */                           out.write("\n\t\t            <td colspan=\"2\" class=\"columnheadingnotop\">\n\n\n\t\t    ");
/*      */                         }
/* 3449 */                         out.write("\n\n\n\t\t");
/* 3450 */                         if (allTypes) {
/* 3451 */                           out.write("\n\t\t\t\t\t\t  \t  \t<span>\n\t\t\t\t\t\t  \t  \t");
/*      */                         } else {
/* 3453 */                           out.write("\n\t\t\t\t\t\t  \t  \t<span  >\n\t\t\t\t\t\t  \t  \t");
/*      */                         }
/* 3455 */                         out.write("\n\n\n\t\t\t\t\t\t  \t  \t<input name=\"button\" type=\"button\" class=\"buttons btn_remove\" value=\"");
/* 3456 */                         out.print(FormatUtil.getString("webclient.admin.modifyuserprofile.img.remove.alt"));
/* 3457 */                         out.write("\" onClick=\"javascript:fnFormSubmit('2', '2')\">\n\n\n\t\t\t\t\t\t  \t\t</span  >\n\n\n\n\n\n\n\n\n\t\t              </td>\n\n\n\n\n\t\t          </tr>\n\n\n\n\t\t        <td>\n\n\t\t        <table id=\"NewMGSelected\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background-color:#fff;\">\n\t\t\t\t\t<tr><td colspan=\"3\" style=\"height:5px;\"></td></tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"5%\" class=\"whitegrayborder\" align=\"left\"><input type=\"checkbox\" class=\"chkbox\" onClick=\"javascript:fnSelectAll(this, '2', 'monitors')\" style=\"position:relative; left:1px;\"></td>\n\t\t\t\t\t<td width=\"75%\" class=\"whitegrayborder\" align=\"left\"><b class=\"bodytextbold\">");
/* 3458 */                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 3459 */                         out.write("</b></td>\n\t\t\t\t\t<td width=\"20%\" class=\"whitegrayborder\"><b class=\"bodytextbold\">");
/* 3460 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 3461 */                         out.write("</b></td>\n\t\t\t\t\t</tr>\n\t\t\t          ");
/*      */                         
/* 3463 */                         IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3464 */                         _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 3465 */                         _jspx_th_logic_005fiterate_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                         
/* 3467 */                         _jspx_th_logic_005fiterate_005f3.setName("configured");
/*      */                         
/* 3469 */                         _jspx_th_logic_005fiterate_005f3.setId("row");
/*      */                         
/* 3471 */                         _jspx_th_logic_005fiterate_005f3.setIndexId("j");
/*      */                         
/* 3473 */                         _jspx_th_logic_005fiterate_005f3.setType("java.util.ArrayList");
/* 3474 */                         int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 3475 */                         if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 3476 */                           ArrayList row = null;
/* 3477 */                           Integer j = null;
/* 3478 */                           if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3479 */                             out = _jspx_page_context.pushBody();
/* 3480 */                             _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 3481 */                             _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                           }
/* 3483 */                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3484 */                           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                           for (;;) {
/* 3486 */                             out.write("\n\t\t\t          ");
/*      */                             
/* 3488 */                             String resid = (String)row.get(0);
/* 3489 */                             String name = (String)row.get(1);
/* 3490 */                             name = EnterpriseUtil.decodeString(name);
/* 3491 */                             String image = "";
/* 3492 */                             image = (String)row.get(2);
/* 3493 */                             String toolTipTitle = name;
/* 3494 */                             if ((trimmedNameMap != null) && (trimmedNameMap.get(resid) != null))
/*      */                             {
/* 3496 */                               name = (String)trimmedNameMap.get(resid);
/*      */                             }
/*      */                             else
/*      */                             {
/* 3500 */                               name = FormatUtil.getTrimmedText(name, 45);
/*      */                             }
/* 3502 */                             if (j.intValue() % 2 == 0)
/*      */                             {
/* 3504 */                               bgcolour = "class=\"whitegrayborder\"";
/*      */                             }
/*      */                             else
/*      */                             {
/* 3508 */                               bgcolour = "class=\"yellowgrayborder\"";
/*      */                             }
/*      */                             
/* 3511 */                             out.write("\n\t\t\t          <tr>\n\t\t\t        <td width=\"5%\" ");
/* 3512 */                             out.print(bgcolour);
/* 3513 */                             out.write("><input type=\"checkbox\" class=\"chkbox\" name=\"monitors\" value=\"");
/* 3514 */                             out.print(resid);
/* 3515 */                             out.write("\"></td>\n\t\t\t        <td width=\"75%\" ");
/* 3516 */                             out.print(bgcolour);
/* 3517 */                             out.write(" align=\"left\"><a class=\"tooltip\" title=\"");
/* 3518 */                             out.print(toolTipTitle);
/* 3519 */                             out.write(34);
/* 3520 */                             out.write(62);
/* 3521 */                             out.print(name);
/* 3522 */                             out.write("</a></td>\n\t\t\t\t\t<td width=\"20%\" ");
/* 3523 */                             out.print(bgcolour);
/* 3524 */                             out.write(" align=\"center\"><img src='..");
/* 3525 */                             out.print(image);
/* 3526 */                             out.write("'></td>\n\t\t\t          </tr>\n\t\t\t          ");
/* 3527 */                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 3528 */                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3529 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/* 3530 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3533 */                           if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3534 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3537 */                         if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 3538 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                         }
/*      */                         
/* 3541 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 3542 */                         out.write("\n\t\t\t          </table>\n\n\n\t\t        </td>\n\n\t\t          <tr style=\"height:25px;\">\n\n\t\t \t\t\t ");
/* 3543 */                         if (allTypes) {
/* 3544 */                           out.write("\n\t\t            <td height=\"31\" colspan=\"3\" class=\"columnheadingnotop\" align=\"left\">\n\t\t          ");
/*      */                         } else {
/* 3546 */                           out.write("\n\t\t            <td height=\"31\" colspan=\"2\" class=\"columnheadingnotop\" align=\"left\">\n\t\t          ");
/*      */                         }
/* 3548 */                         out.write("\n\n\n\t\t            <input name=\"button\" type=\"button\" class=\"buttons btn_remove\" value=\"");
/* 3549 */                         out.print(FormatUtil.getString("webclient.admin.modifyuserprofile.img.remove.alt"));
/* 3550 */                         out.write("\" onClick=\"javascript:fnFormSubmit('2', '2')\">\n\n\n\n\t\t\t</td>\n\t\t\t<td>\n\n\t\t\t</td>\n\n\t\t          </tr>\n\n\n\t\t        </table>\n\t\t        ");
/* 3551 */                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 3552 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3556 */                     if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 3557 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4); return;
/*      */                     }
/*      */                     
/* 3560 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 3561 */                     out.write(32);
/*      */                     
/* 3563 */                     EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3564 */                     _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 3565 */                     _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_html_005fform_005f1);
/*      */                     
/* 3567 */                     _jspx_th_logic_005fempty_005f2.setName("configured");
/* 3568 */                     int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 3569 */                     if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */                       for (;;) {
/* 3571 */                         out.write("\n\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"background-color:#fff;\">\n\t\t          <tr>\n\t\t            <td class=\"columnheading\" height=\"25\">\n\n\n\t\t</td>\n\t\t          </tr>\n\t\t          <tr>\n\t\t            <td  height=\"22\" class=\"emptyTableMsg\">&nbsp;<span  class=\"bodytext\">");
/* 3572 */                         out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3573 */                         out.write(".</span></td>\n\t\t          </tr>\n\t\t        </table>\n\t\t\t\t");
/* 3574 */                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 3575 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3579 */                     if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 3580 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */                     }
/*      */                     
/* 3583 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 3584 */                     out.write("\n\t\t    ");
/*      */                   }
/*      */                   
/*      */ 
/* 3588 */                   out.write("\n\n\t\t\t  ");
/*      */                   
/* 3590 */                   if (request.getAttribute("showGroupOnly") != null)
/*      */                   {
/*      */ 
/* 3593 */                     out.write("\n\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"right\" class=\"lrtbdarkborder\">\n\t\t\t\t <tr>\n\t\t\t\t  <td class=\"tableheadingbborder\" height=\"25\">");
/* 3594 */                     out.print(FormatUtil.getString("haid.application.presentgroup.text"));
/* 3595 */                     out.write("</td>\n\t\t\t\t </tr>\n\t\t\t\t ");
/*      */                     
/* 3597 */                     EmptyTag _jspx_th_logic_005fempty_005f3 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3598 */                     _jspx_th_logic_005fempty_005f3.setPageContext(_jspx_page_context);
/* 3599 */                     _jspx_th_logic_005fempty_005f3.setParent(_jspx_th_html_005fform_005f1);
/*      */                     
/* 3601 */                     _jspx_th_logic_005fempty_005f3.setName("configuredMg");
/* 3602 */                     int _jspx_eval_logic_005fempty_005f3 = _jspx_th_logic_005fempty_005f3.doStartTag();
/* 3603 */                     if (_jspx_eval_logic_005fempty_005f3 != 0) {
/*      */                       for (;;) {
/* 3605 */                         out.write("\n\t\t\t\t  <tr>\n\t\t\t\t   <td  height=\"22\" class=\"emptyTableMsg\">&nbsp;<span>");
/* 3606 */                         out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3607 */                         out.write(".</span></td>\n\t\t\t\t  </tr>\n\t\t\t\t ");
/* 3608 */                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f3.doAfterBody();
/* 3609 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3613 */                     if (_jspx_th_logic_005fempty_005f3.doEndTag() == 5) {
/* 3614 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f3); return;
/*      */                     }
/*      */                     
/* 3617 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f3);
/* 3618 */                     out.write("\n\t\t\t\t ");
/*      */                     
/* 3620 */                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f5 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3621 */                     _jspx_th_logic_005fnotEmpty_005f5.setPageContext(_jspx_page_context);
/* 3622 */                     _jspx_th_logic_005fnotEmpty_005f5.setParent(_jspx_th_html_005fform_005f1);
/*      */                     
/* 3624 */                     _jspx_th_logic_005fnotEmpty_005f5.setName("configuredMg");
/* 3625 */                     int _jspx_eval_logic_005fnotEmpty_005f5 = _jspx_th_logic_005fnotEmpty_005f5.doStartTag();
/* 3626 */                     if (_jspx_eval_logic_005fnotEmpty_005f5 != 0) {
/*      */                       for (;;) {
/* 3628 */                         out.write("\n\t\t\t\t <tr>\n\t\t\t\t  <td class=\"columnheading\" ><input type=\"checkbox\" class=\"chkbox\" onClick=\"javascript:fnSelectAll(this, '2', 'monitorsMg')\">&nbsp;&nbsp;&nbsp;");
/* 3629 */                         out.print(FormatUtil.getString("haid.application.name.text"));
/* 3630 */                         out.write("</td>\n\t\t\t\t </tr>\n\t\t\t\t <tr>\n\t\t\t\t  <td height=\"31\" class=\"tablebottom\"><input name=\"button\" type=\"button\" class=\"buttons btn_remove\"  value=\"");
/* 3631 */                         out.print(FormatUtil.getString("webclient.admin.modifyuserprofile.img.remove.alt"));
/* 3632 */                         out.write("\" onClick=\"javascript:fnFormSubmitMg('2', '2')\"></td>\n\t\t\t\t </tr>\n\t\t\t\t ");
/*      */                         
/* 3634 */                         IterateTag _jspx_th_logic_005fiterate_005f4 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3635 */                         _jspx_th_logic_005fiterate_005f4.setPageContext(_jspx_page_context);
/* 3636 */                         _jspx_th_logic_005fiterate_005f4.setParent(_jspx_th_logic_005fnotEmpty_005f5);
/*      */                         
/* 3638 */                         _jspx_th_logic_005fiterate_005f4.setName("configuredMg");
/*      */                         
/* 3640 */                         _jspx_th_logic_005fiterate_005f4.setId("row");
/*      */                         
/* 3642 */                         _jspx_th_logic_005fiterate_005f4.setIndexId("j");
/*      */                         
/* 3644 */                         _jspx_th_logic_005fiterate_005f4.setType("java.util.ArrayList");
/* 3645 */                         int _jspx_eval_logic_005fiterate_005f4 = _jspx_th_logic_005fiterate_005f4.doStartTag();
/* 3646 */                         if (_jspx_eval_logic_005fiterate_005f4 != 0) {
/* 3647 */                           ArrayList row = null;
/* 3648 */                           Integer j = null;
/* 3649 */                           if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 3650 */                             out = _jspx_page_context.pushBody();
/* 3651 */                             _jspx_th_logic_005fiterate_005f4.setBodyContent((BodyContent)out);
/* 3652 */                             _jspx_th_logic_005fiterate_005f4.doInitBody();
/*      */                           }
/* 3654 */                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3655 */                           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                           for (;;) {
/* 3657 */                             out.write("\n\t\t\t\t ");
/*      */                             
/* 3659 */                             String resid = (String)row.get(0);
/* 3660 */                             String name = (String)row.get(1);
/* 3661 */                             String image = "";
/* 3662 */                             if (j.intValue() % 2 == 0)
/*      */                             {
/* 3664 */                               bgcolour = "class=\"whitegrayborder\"";
/*      */                             }
/*      */                             else
/*      */                             {
/* 3668 */                               bgcolour = "class=\"yellowgrayborder\"";
/*      */                             }
/*      */                             
/* 3671 */                             out.write("\n\t\t          <tr>\n\t\t           <td width=\"4%\" ");
/* 3672 */                             out.print(bgcolour);
/* 3673 */                             out.write("><input type=\"checkbox\" class=\"chkbox\" name=\"monitorsMg\" value=\"");
/* 3674 */                             out.print(resid);
/* 3675 */                             out.write("\">&nbsp;&nbsp;&nbsp;");
/*      */                             
/* 3677 */                             Truncate _jspx_th_am_005fTruncate_005f1 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 3678 */                             _jspx_th_am_005fTruncate_005f1.setPageContext(_jspx_page_context);
/* 3679 */                             _jspx_th_am_005fTruncate_005f1.setParent(_jspx_th_logic_005fiterate_005f4);
/*      */                             
/* 3681 */                             _jspx_th_am_005fTruncate_005f1.setTooltip("true");
/*      */                             
/* 3683 */                             _jspx_th_am_005fTruncate_005f1.setLength(50);
/* 3684 */                             int _jspx_eval_am_005fTruncate_005f1 = _jspx_th_am_005fTruncate_005f1.doStartTag();
/* 3685 */                             if (_jspx_eval_am_005fTruncate_005f1 != 0) {
/* 3686 */                               if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 3687 */                                 out = _jspx_page_context.pushBody();
/* 3688 */                                 _jspx_th_am_005fTruncate_005f1.setBodyContent((BodyContent)out);
/* 3689 */                                 _jspx_th_am_005fTruncate_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3692 */                                 out.print(name);
/* 3693 */                                 int evalDoAfterBody = _jspx_th_am_005fTruncate_005f1.doAfterBody();
/* 3694 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3697 */                               if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 3698 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3701 */                             if (_jspx_th_am_005fTruncate_005f1.doEndTag() == 5) {
/* 3702 */                               this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f1); return;
/*      */                             }
/*      */                             
/* 3705 */                             this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 3706 */                             out.write("</td> ");
/* 3707 */                             out.write("\n\t\t          </tr>\n\t\t\t\t  ");
/* 3708 */                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f4.doAfterBody();
/* 3709 */                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3710 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/* 3711 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3714 */                           if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 3715 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3718 */                         if (_jspx_th_logic_005fiterate_005f4.doEndTag() == 5) {
/* 3719 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4); return;
/*      */                         }
/*      */                         
/* 3722 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4);
/* 3723 */                         out.write("\n\t\t\t\t  <tr>\n\t\t\t\t   <td height=\"31\" class=\"tablebottom\"><input name=\"button\" type=\"button\" class=\"buttons btn_remove\" value=\"");
/* 3724 */                         out.print(FormatUtil.getString("webclient.admin.modifyuserprofile.img.remove.alt"));
/* 3725 */                         out.write("\" onClick=\"javascript:fnFormSubmitMg('2', '2')\">\n\t\t\t\t  </tr>\n\t\t\t\t  ");
/* 3726 */                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f5.doAfterBody();
/* 3727 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3731 */                     if (_jspx_th_logic_005fnotEmpty_005f5.doEndTag() == 5) {
/* 3732 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5); return;
/*      */                     }
/*      */                     
/* 3735 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5);
/* 3736 */                     out.write("\n\t\t\t\t </table>\n\t\t\t\t </td>\n\t\t\t\t </tr>\n\t\t\t\t</table>\n\t\t\t  ");
/*      */                   }
/* 3738 */                   out.write(10);
/* 3739 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3740 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3744 */               if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3745 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */               }
/*      */               
/* 3748 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3749 */               out.write("\n\n\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\n\n\n\n\t\t\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"25%\">\n\n\t\t\t\t\t\t\t\t<table width=\"100%\"  cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td width=\"30%\" class=\"AlarmtxtSpace\" nowrap='nowrap' height=\"5\"></td>\n\n\t\t\t\t\t\t\t\t\t\t<td></td>\n\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"30%\">\t</td>\n\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t</table>\n\t\t            </td>\n\t\t          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\n\t\t        </tr>\n\n\t\t      </table></td>\n\t\t      </tr>\n\t\t     </table>\n\t\t     </td>\n\t\t  </tr>\n\t\t</table>\n\t\t</td>\n\t\t\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t\t\t\t\t<td class=\"AlarmCardMainBtmBg\"/>\n\t\t\t\t\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t\t\t\t\t</tr>\n\t\t\t</table>\n\n\n\n\n\t</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n  <!--<div id=\"actionstatus\" class=\"bodytext\">");
/* 3750 */               out.print(FormatUtil.getString("am.webclient.host.diskspace.fetchingmessage"));
/* 3751 */               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n    <div id=\"opmandevicedetails\"></div>-->\n\t");
/*      */               
/* 3753 */               FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/* 3754 */               String usrtype = fd.getUserType();
/* 3755 */               if ((!com.adventnet.appmanager.util.Constants.sqlManager) && (!DBUtil.isDelegatedAdmin(request.getRemoteUser())) && (
/* 3756 */                 (OEMUtil.getOEMString("product.name") == null) || (!OEMUtil.getOEMString("product.name").equals("IT360"))))
/*      */               {
/*      */ 
/* 3759 */                 out.write(10);
/* 3760 */                 out.write(32);
/* 3761 */                 out.write(32);
/* 3762 */                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */                 
/* 3764 */                 String resourceName1 = request.getParameter("type");
/* 3765 */                 String appname1 = request.getParameter("name");
/* 3766 */                 String haid1 = request.getParameter("haid");
/* 3767 */                 String bgcolour1 = "class=\"whitegrayborder\"";
/* 3768 */                 String prevpagelinktext1 = "am.webclient.monitorgroupdetails.backtomonitorgroup.text";
/* 3769 */                 String prevpagelink1 = "/showapplication.do?haid=" + haid1 + "&method=showApplication";
/* 3770 */                 String selectedscheme1 = null;
/* 3771 */                 boolean slimLayout1 = false;
/* 3772 */                 if (session.getAttribute("selectedscheme") != null)
/*      */                 {
/* 3774 */                   selectedscheme1 = (String)session.getAttribute("selectedscheme");
/*      */                 }
/* 3776 */                 if ((selectedscheme1 != null) && (selectedscheme1.equals("slim")))
/*      */                 {
/* 3778 */                   slimLayout1 = true;
/*      */                 }
/*      */                 
/*      */ 
/* 3782 */                 out.write("\n<script>\nfunction fnSelectAllOpMon(e,formindex, checkboxstr)\n{\n\tvar temp=formindex;\n\tif(");
/* 3783 */                 out.print(slimLayout1);
/* 3784 */                 out.write(")\n\t{\n\t\ttemp=formindex-1;\n\t}\n\tToggleAll(e,document.forms[temp],checkboxstr)\n}\nfunction fnDisplayName(combo)\n{\n\tvar v=combo.options[combo.selectedIndex].text;\n\tdocument.forms[formindex].displayname.value=v;\n}\nfunction fnOpMonFormSubmit(a,temp,goback)\n{\n        var name='selectedExternalresource';\n        var msg = ' ");
/* 3785 */                 out.print(FormatUtil.getString("add"));
/* 3786 */                 out.write("';\n        if(temp == 4) {\n            name ='ExtIntegDevice';\n            msg = '");
/* 3787 */                 out.print(FormatUtil.getString("remove"));
/* 3788 */                 out.write("';\n\t\t}\n\t\tvar formindex=temp;\n\t\tif(");
/* 3789 */                 out.print(slimLayout1);
/* 3790 */                 out.write(")\n\t\t{\n\t\t\tformindex=temp-1;\n\t\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 3791 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 3792 */                 out.write(" ' + msg);\n\t\treturn;\n\t}\t\n\tif(goback)\n\t{\n\t\tdocument.getElementsByName(\"goback\")[1].value='true';\t\n\t}\n\telse\n\t{\n\t\tdocument.getElementsByName(\"goback\")[1].value='false';\t\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].submit();\n}\n\nfunction fnFormSubmitMg(a,temp)\n{\n\tvar name='selectedresourceMg';\n\tvar msg = ' ");
/* 3793 */                 out.print(FormatUtil.getString("add"));
/* 3794 */                 out.write("';\n\tif(temp == 2) {\n\t\tname ='monitorsMg';\n\t\tmsg = '");
/* 3795 */                 out.print(FormatUtil.getString("remove"));
/* 3796 */                 out.write("';\n\t}\n    var formindex=temp;\n\tif(");
/* 3797 */                 out.print(slimLayout1);
/* 3798 */                 out.write(")\n\t{\n\t  formindex=temp-1;\n\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 3799 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 3800 */                 out.write(" ' + msg);\n\t\treturn;\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].MonitorGroup.value=\"MonitorGroup\";\n\tdocument.forms[formindex].submit();\n}\n\n\n</script>\n");
/*      */                 
/* 3802 */                 if ((!OEMUtil.isOEM()) || (com.adventnet.appmanager.util.Constants.isIt360) || ((OEMUtil.isOEM()) && ((List)request.getAttribute("devicetoconfigure") != null) && (((List)request.getAttribute("devicetoconfigure")).size() > 0)))
/*      */                 {
/*      */ 
/* 3805 */                   out.write("\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
/*      */                   
/* 3807 */                   FormTag _jspx_th_html_005fform_005f2 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3808 */                   _jspx_th_html_005fform_005f2.setPageContext(_jspx_page_context);
/* 3809 */                   _jspx_th_html_005fform_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 3811 */                   _jspx_th_html_005fform_005f2.setAction("/extDeviceAction.do");
/*      */                   
/* 3813 */                   _jspx_th_html_005fform_005f2.setMethod("post");
/*      */                   
/* 3815 */                   _jspx_th_html_005fform_005f2.setStyle("display:inline;");
/* 3816 */                   int _jspx_eval_html_005fform_005f2 = _jspx_th_html_005fform_005f2.doStartTag();
/* 3817 */                   if (_jspx_eval_html_005fform_005f2 != 0) {
/*      */                     for (;;) {
/* 3819 */                       out.write("\n<input type=\"hidden\" name=\"org.apache.struts.taglib.html.TOKEN\" value=\"");
/* 3820 */                       out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 3821 */                       out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"addExtIntegResource\">\n<input type=\"hidden\" name=\"goback\" value=\"false\">\n<input type=\"hidden\" name=\"savetype\" value=\"1\">\n<input type=\"hidden\" name=\"monitortype\" value=\"network\">\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 3822 */                       out.print(appname1);
/* 3823 */                       out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 3824 */                       out.print(haid1);
/* 3825 */                       out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 3826 */                       out.print(resourceName1);
/* 3827 */                       out.write(34);
/* 3828 */                       out.write(62);
/* 3829 */                       out.write(10);
/*      */                       
/* 3831 */                       String message1 = request.getParameter("message");
/* 3832 */                       String redirect = "/showresource.do?haid=" + haid1 + "&type=" + resourceName1 + "&method=getMonitorForm";
/* 3833 */                       String encodedurl = URLEncoder.encode(redirect);
/* 3834 */                       String initMonLink = "";
/* 3835 */                       String internaltype = null;
/*      */                       
/*      */ 
/* 3838 */                       boolean allTypes1 = resourceName1.equals("All");
/*      */                       
/* 3840 */                       out.write("\n\n\n\n\n\n\n<br><br>\n\n<table width=\"49%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\n\t\t\t<tr>\n\t\t\t<td class=\"AlarmHdrTopLeft\"/>\n\t\t\t<td class=\"AlarmHdrTopBg\">\n\n\n\t\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\n\t\t\t<tr>\n\t\t\t<td  valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\">\n\n\n\t\t<span class=\"bcactive\"> ");
/* 3841 */                       if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                       {
/* 3843 */                         out.write("\n\n\n\t\t\t\t\t\t    <td height=\"25\"  class=\"AlarmCardContentBg\">");
/* 3844 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.avlnwdevices.text"));
/* 3845 */                         out.write("</td>\n\t\t\t\t\t\t  ");
/*      */                       } else {
/* 3847 */                         out.write("\n\t\t\t\t\t\t\t   <td height=\"25\" class=\"AlarmCardContentBg\"><b>&nbsp;");
/* 3848 */                         out.print(FormatUtil.getString("Network Devices"));
/* 3849 */                         out.write("</b></td>\n\t\t\t\t\t\t  ");
/*      */                       }
/* 3851 */                       out.write("\n</span>\n\n\n\n\t\t\t</td>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\">\n\t\t\t</td>\n\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t</table></td>\n\t\t\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n\n    <td style=\"padding-top:3px;\" class=\"AlarmboxedContent\">\n    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n      <tr>\n          <td >\n           <!--text -->\n          </td>\n      </tr>\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n        <tr>\n\n          <td class=\"AlarmInnerTopLeft\"/>\n          <td class=\"AlarmInnerTopBg\"/>\n          <td class=\"AlarmInnerTopRight\"/>\n        </tr>\n        <tr>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n                <td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n");
/* 3852 */                       out.write("\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t\t\t\t<tr>\n\n\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t    <tr>\n\t\t\t\t      <td  width=\"50%\" valign=\"top\"> ");
/*      */                       
/* 3854 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f6 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3855 */                       _jspx_th_logic_005fnotEmpty_005f6.setPageContext(_jspx_page_context);
/* 3856 */                       _jspx_th_logic_005fnotEmpty_005f6.setParent(_jspx_th_html_005fform_005f2);
/*      */                       
/* 3858 */                       _jspx_th_logic_005fnotEmpty_005f6.setName("devicetoconfigure");
/* 3859 */                       int _jspx_eval_logic_005fnotEmpty_005f6 = _jspx_th_logic_005fnotEmpty_005f6.doStartTag();
/* 3860 */                       if (_jspx_eval_logic_005fnotEmpty_005f6 != 0) {
/*      */                         for (;;) {
/* 3862 */                           out.write("\n\t\t\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\"  style=\"background-color:#fff;\">\n\t\t\t\t          <tr>\n\n\t\t\t\t");
/* 3863 */                           if (allTypes1) {
/* 3864 */                             out.write("\n\t\t\t\t<td colspan=\"3\">\n\t\t\t\t");
/*      */                           } else {
/* 3866 */                             out.write("\n\t\t\t\t<td colspan=\"2\" >\n\t\t\t\t");
/*      */                           }
/* 3868 */                           out.write("\n\n\n\n\n\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  height=\"26\">\n\t\t\t\t\t<tr>\n\n\n\n\n\n\n\t\t\t\t");
/*      */                           
/* 3870 */                           int s = ((List)request.getAttribute("devicetoconfigure")).size();
/* 3871 */                           if (s > 0)
/*      */                           {
/* 3873 */                             out.write("\n\n\n\t\t\t\t            ");
/* 3874 */                             if (allTypes1) {
/* 3875 */                               out.write("\n\t\t\t\t            <td height=\"31\" colspan=\"3\" class=\"columnheadingnotop\" align=\"left\">\n\t\t\t\t            ");
/*      */                             } else {
/* 3877 */                               out.write("\n\t\t\t\t            <td height=\"31\" colspan=\"2\" class=\"columnheadingnotop\" align=\"left\">\n\t\t\t\t            ");
/*      */                             }
/* 3879 */                             out.write("\n\t\t\t\t    <input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 3880 */                             out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 3881 */                             out.write("\" onClick=\"javascript:fnOpMonFormSubmit('2', '3')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 3882 */                             out.print(FormatUtil.getString("am.common.or.text"));
/* 3883 */                             out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 3884 */                             out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 3885 */                             out.write("\" onClick=\"javascript:fnOpMonFormSubmit('2', '3', 'true')\">\n\t\t\t\t\t</td>\n\n\n\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 3889 */                           out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t          </tr>\n\n\n\n\n\t\t\t\t          <tr>\n\t\t\t\t            <td class=\" whitegrayborder\" width=\"5%\"><input type=\"checkbox\" onClick=\"javascript:fnSelectAllOpMon(this, '3', 'selectedExternalresource')\"></td>\n\t\t\t\t            <td class=\"bodytextbold whitegrayborder\" align=\"left\" width=\"75%\"><b>");
/* 3890 */                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 3891 */                           out.write("</b></td>\n\t\t\t\t            ");
/* 3892 */                           if (allTypes1) {
/* 3893 */                             out.write("\n\t\t\t\t            <td class=\"whitegrayborder\" width=\"20%\"><b>");
/* 3894 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 3895 */                             out.write("</b></td>\n\t\t\t\t            ");
/*      */                           }
/* 3897 */                           out.write("\n\n\n\t\t\t\t          </tr>\n\t\t\t\t          ");
/*      */                           
/* 3899 */                           IterateTag _jspx_th_logic_005fiterate_005f5 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3900 */                           _jspx_th_logic_005fiterate_005f5.setPageContext(_jspx_page_context);
/* 3901 */                           _jspx_th_logic_005fiterate_005f5.setParent(_jspx_th_logic_005fnotEmpty_005f6);
/*      */                           
/* 3903 */                           _jspx_th_logic_005fiterate_005f5.setName("devicetoconfigure");
/*      */                           
/* 3905 */                           _jspx_th_logic_005fiterate_005f5.setId("row");
/*      */                           
/* 3907 */                           _jspx_th_logic_005fiterate_005f5.setIndexId("j");
/*      */                           
/* 3909 */                           _jspx_th_logic_005fiterate_005f5.setType("java.util.ArrayList");
/* 3910 */                           int _jspx_eval_logic_005fiterate_005f5 = _jspx_th_logic_005fiterate_005f5.doStartTag();
/* 3911 */                           if (_jspx_eval_logic_005fiterate_005f5 != 0) {
/* 3912 */                             ArrayList row = null;
/* 3913 */                             Integer j = null;
/* 3914 */                             if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 3915 */                               out = _jspx_page_context.pushBody();
/* 3916 */                               _jspx_th_logic_005fiterate_005f5.setBodyContent((BodyContent)out);
/* 3917 */                               _jspx_th_logic_005fiterate_005f5.doInitBody();
/*      */                             }
/* 3919 */                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3920 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                             for (;;) {
/* 3922 */                               out.write("\n\t\t\t\t          ");
/*      */                               
/* 3924 */                               String deviceName = null;
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3931 */                               deviceName = (String)row.get(0) + "$" + (String)row.get(3);
/*      */                               
/* 3933 */                               String dispName = (String)row.get(1);
/* 3934 */                               dispName = EnterpriseUtil.decodeString(dispName);
/*      */                               
/* 3936 */                               String category = "";
/* 3937 */                               if (allTypes1)
/*      */                               {
/* 3939 */                                 category = (String)row.get(2);
/* 3940 */                                 int startIndex = category.indexOf("-");
/* 3941 */                                 category = category.substring(startIndex + 1, category.length());
/* 3942 */                                 deviceName = deviceName + "$" + category;
/*      */                               }
/* 3944 */                               if (j.intValue() % 2 == 0)
/*      */                               {
/* 3946 */                                 bgcolour1 = "class=\"whitegrayborder\"";
/*      */                               }
/*      */                               else
/*      */                               {
/* 3950 */                                 bgcolour1 = "class=\"yellowgrayborder\"";
/*      */                               }
/*      */                               
/* 3953 */                               out.write("\n\t\t\t\t          <tr>\n\t\t\t\t            <td width=\"2%\" ");
/* 3954 */                               out.print(bgcolour1);
/* 3955 */                               out.write("><input type=\"checkbox\" name=\"selectedExternalresource\" value=\"");
/* 3956 */                               out.print(deviceName);
/* 3957 */                               out.write("\"></td>\n\t\t\t\t            <td width=\"78%\" ");
/* 3958 */                               out.print(bgcolour1);
/* 3959 */                               out.write(" align=\"left\">");
/*      */                               
/* 3961 */                               Truncate _jspx_th_am_005fTruncate_005f2 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 3962 */                               _jspx_th_am_005fTruncate_005f2.setPageContext(_jspx_page_context);
/* 3963 */                               _jspx_th_am_005fTruncate_005f2.setParent(_jspx_th_logic_005fiterate_005f5);
/*      */                               
/* 3965 */                               _jspx_th_am_005fTruncate_005f2.setTooltip("true");
/*      */                               
/* 3967 */                               _jspx_th_am_005fTruncate_005f2.setLength(50);
/* 3968 */                               int _jspx_eval_am_005fTruncate_005f2 = _jspx_th_am_005fTruncate_005f2.doStartTag();
/* 3969 */                               if (_jspx_eval_am_005fTruncate_005f2 != 0) {
/* 3970 */                                 if (_jspx_eval_am_005fTruncate_005f2 != 1) {
/* 3971 */                                   out = _jspx_page_context.pushBody();
/* 3972 */                                   _jspx_th_am_005fTruncate_005f2.setBodyContent((BodyContent)out);
/* 3973 */                                   _jspx_th_am_005fTruncate_005f2.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3976 */                                   out.print(dispName);
/* 3977 */                                   int evalDoAfterBody = _jspx_th_am_005fTruncate_005f2.doAfterBody();
/* 3978 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3981 */                                 if (_jspx_eval_am_005fTruncate_005f2 != 1) {
/* 3982 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3985 */                               if (_jspx_th_am_005fTruncate_005f2.doEndTag() == 5) {
/* 3986 */                                 this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f2); return;
/*      */                               }
/*      */                               
/* 3989 */                               this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f2);
/* 3990 */                               out.write("</td>\n\n\t\t\t\t            ");
/* 3991 */                               if (allTypes1) {
/* 3992 */                                 out.write("\n\t\t\t\t\t\t\t\t<td width=\"20%\" ");
/* 3993 */                                 out.print(bgcolour1);
/* 3994 */                                 out.write(" align=\"left\">");
/* 3995 */                                 out.print(category);
/* 3996 */                                 out.write("</td>\n\t\t\t\t            ");
/*      */                               }
/* 3998 */                               out.write("\n\n\n\n\t\t\t\t          </tr>\n\t\t\t\t          ");
/* 3999 */                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f5.doAfterBody();
/* 4000 */                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4001 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 4002 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 4005 */                             if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 4006 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 4009 */                           if (_jspx_th_logic_005fiterate_005f5.doEndTag() == 5) {
/* 4010 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5); return;
/*      */                           }
/*      */                           
/* 4013 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5);
/* 4014 */                           out.write("\n\n\t\t\t\t          <tr>\n\t\t\t\t            ");
/* 4015 */                           if (allTypes1) {
/* 4016 */                             out.write("\n\t\t\t\t            <td colspan=\"3\" class=\"columnheadingnotop\" align=\"left\" style=\"height:26px;\">\n\t\t\t\t            ");
/*      */                           } else {
/* 4018 */                             out.write("\n\t\t\t\t            <td  colspan=\"2\" class=\"columnheadingnotop\" align=\"left\" style=\"height:26px;\">\n\t\t\t\t            ");
/*      */                           }
/* 4020 */                           out.write("\n\t\t\t\t            <input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4021 */                           out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 4022 */                           out.write("\" onClick=\"javascript:fnOpMonFormSubmit('2', '3')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 4023 */                           out.print(FormatUtil.getString("am.common.or.text"));
/* 4024 */                           out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4025 */                           out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 4026 */                           out.write("\" onClick=\"javascript:fnOpMonFormSubmit('2', '3', 'true')\">\n\t\t\t\t\t</td>\n\t\t\t\t          </tr>\n\n\t\t\t\t        </table>\n\t\t\t\t        ");
/* 4027 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f6.doAfterBody();
/* 4028 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4032 */                       if (_jspx_th_logic_005fnotEmpty_005f6.doEndTag() == 5) {
/* 4033 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6); return;
/*      */                       }
/*      */                       
/* 4036 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6);
/* 4037 */                       out.write(32);
/*      */                       
/* 4039 */                       EmptyTag _jspx_th_logic_005fempty_005f4 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4040 */                       _jspx_th_logic_005fempty_005f4.setPageContext(_jspx_page_context);
/* 4041 */                       _jspx_th_logic_005fempty_005f4.setParent(_jspx_th_html_005fform_005f2);
/*      */                       
/* 4043 */                       _jspx_th_logic_005fempty_005f4.setName("devicetoconfigure");
/* 4044 */                       int _jspx_eval_logic_005fempty_005f4 = _jspx_th_logic_005fempty_005f4.doStartTag();
/* 4045 */                       if (_jspx_eval_logic_005fempty_005f4 != 0) {
/*      */                         for (;;) {
/* 4047 */                           out.write("\n\t\t\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"background-color:#fff;\">\n\t\t\t\t          <tr>\n\t\t\t\t<td >\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\t\t\t\t\t<tr>\n\t\t\t\t\t ");
/* 4048 */                           if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                           {
/* 4050 */                             out.write("   <td height=\"25\"  colspan=\"2\" class=\"columnheading\">");
/* 4051 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.avlnwdevices.text"));
/* 4052 */                             out.write("</td>\n\t\t\t\t\t\t  ");
/*      */                           } else {
/* 4054 */                             out.write("\n\t\t\t\t\t\t\t   <td height=\"25\"  colspan=\"2\" class=\"columnheading\"></td>\n\t\t\t\t\t\t  ");
/*      */                           }
/* 4056 */                           out.write("\n\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\n\t\t\t\t          </tr>\n\t\t\t\t          <tr>\n\n\t\t\t\t          <td  height=\"25\"><span class=\"bodytext\"> &nbsp;");
/* 4057 */                           out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4058 */                           out.write(". </span></td>\n\t\t\t\t          </tr>\n\t\t\t\t        </table>\n\t\t\t\t        ");
/* 4059 */                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f4.doAfterBody();
/* 4060 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4064 */                       if (_jspx_th_logic_005fempty_005f4.doEndTag() == 5) {
/* 4065 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4); return;
/*      */                       }
/*      */                       
/* 4068 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4);
/* 4069 */                       out.write("\n\t\t\t\t\t\t</td>\n\n\t\t\t\n\n\t\t\t\t  </table>\n\t\t\t\t\n\n\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\n\n\n\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"25%\">\n\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"30%\">\t</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t</table>\n            </td>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t\t\t<td class=\"AlarmCardMainBtmBg\"/>\n\t\t\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n\n\t");
/* 4070 */                       int evalDoAfterBody = _jspx_th_html_005fform_005f2.doAfterBody();
/* 4071 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4075 */                   if (_jspx_th_html_005fform_005f2.doEndTag() == 5) {
/* 4076 */                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f2); return;
/*      */                   }
/*      */                   
/* 4079 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f2);
/* 4080 */                   out.write("\n\t</body>\n\t\n");
/*      */                 }
/* 4082 */                 out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 4083 */                 out.write(" \n  ");
/* 4084 */                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */                 
/* 4086 */                 String resourceName2 = request.getParameter("type");
/* 4087 */                 String appname2 = request.getParameter("name");
/* 4088 */                 String haid2 = request.getParameter("haid");
/* 4089 */                 String bgcolour2 = "class=\"whitegrayborder\"";
/* 4090 */                 String prevpagelinktext2 = "am.webclient.monitorgroupdetails.backtomonitorgroup.text";
/* 4091 */                 String prevpagelink2 = "/showapplication.do?haid=" + haid2 + "&method=showApplication";
/* 4092 */                 String selectedscheme3 = null;
/* 4093 */                 boolean slimLayout3 = false;
/* 4094 */                 if (session.getAttribute("selectedscheme") != null)
/*      */                 {
/* 4096 */                   selectedscheme3 = (String)session.getAttribute("selectedscheme");
/*      */                 }
/* 4098 */                 if ((selectedscheme3 != null) && (selectedscheme3.equals("slim")))
/*      */                 {
/* 4100 */                   slimLayout3 = true;
/*      */                 }
/*      */                 
/*      */ 
/* 4104 */                 out.write("\n<script>\n\nfunction fnSelectAllOpMon2(e,formindex, checkboxstr)\n{\n\tvar temp=formindex;\n\tif(");
/* 4105 */                 out.print(slimLayout3);
/* 4106 */                 out.write(")\n\t{\n\t\ttemp=formindex-1;\n\t}\n\tToggleAll(e,document.forms[temp],checkboxstr)\n}\nfunction fnDisplayName(combo)\n{\n\tvar v=combo.options[combo.selectedIndex].text;\n\tdocument.forms[formindex].displayname.value=v;\n}\nfunction fnOpMonFormSubmit2(a,temp,goback)\n{\n        var name='selectedStorageresource';//NO I18N\n        var msg = ' ");
/* 4107 */                 out.print(FormatUtil.getString("add"));
/* 4108 */                 out.write("';\n        // if(temp == 4) {\n            // name ='ExtIntegDevice';\n            // msg = '");
/* 4109 */                 out.print(FormatUtil.getString("remove"));
/* 4110 */                 out.write("';\n\t\t// }\n\t\tvar formindex=temp;\n\t\tif(");
/* 4111 */                 out.print(slimLayout3);
/* 4112 */                 out.write(")\n\t\t{\n\t\t\tformindex=temp-1;\n\t\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 4113 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 4114 */                 out.write(" ' + msg);\n\t\treturn;\n\t}\t\n\tif(goback)\n\t{\n\t\tdocument.getElementsByName(\"goback\")[2].value='true';\t\n\t}\n\telse\n\t{\n\t\tdocument.getElementsByName(\"goback\")[2].value='false';\t\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].submit();\n}\n\nfunction fnFormSubmitMg(a,temp)\n{\n\tvar name='selectedresourceMg';//NO I18N\n\tvar msg = ' ");
/* 4115 */                 out.print(FormatUtil.getString("add"));
/* 4116 */                 out.write("';\n\tif(temp == 2) {\n\t\tname ='monitorsMg';//NO I18N\n\t\tmsg = '");
/* 4117 */                 out.print(FormatUtil.getString("remove"));
/* 4118 */                 out.write("';\n\t}\n    var formindex=temp;\n\tif(");
/* 4119 */                 out.print(slimLayout3);
/* 4120 */                 out.write(")\n\t{\n\t  formindex=temp-1;\n\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 4121 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 4122 */                 out.write(" ' + msg);\n\t\treturn;\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].MonitorGroup.value=\"MonitorGroup\";\n\tdocument.forms[formindex].submit();\n}\n\n\n</script>\n");
/*      */                 
/* 4124 */                 if ((!OEMUtil.isOEM()) || (com.adventnet.appmanager.util.Constants.isIt360) || ((OEMUtil.isOEM()) && ((List)request.getAttribute("opstordevtoconfigure") != null) && (((List)request.getAttribute("opstordevtoconfigure")).size() > 0)))
/*      */                 {
/*      */ 
/* 4127 */                   out.write("\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
/*      */                   
/* 4129 */                   FormTag _jspx_th_html_005fform_005f3 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 4130 */                   _jspx_th_html_005fform_005f3.setPageContext(_jspx_page_context);
/* 4131 */                   _jspx_th_html_005fform_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 4133 */                   _jspx_th_html_005fform_005f3.setAction("/extDeviceAction.do");
/*      */                   
/* 4135 */                   _jspx_th_html_005fform_005f3.setMethod("post");
/*      */                   
/* 4137 */                   _jspx_th_html_005fform_005f3.setStyle("display:inline;");
/* 4138 */                   int _jspx_eval_html_005fform_005f3 = _jspx_th_html_005fform_005f3.doStartTag();
/* 4139 */                   if (_jspx_eval_html_005fform_005f3 != 0) {
/*      */                     for (;;) {
/* 4141 */                       out.write("\n<input type=\"hidden\" name=\"org.apache.struts.taglib.html.TOKEN\" value=\"");
/* 4142 */                       out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 4143 */                       out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"addExtIntegResource\">\n<input type=\"hidden\" name=\"goback\" value=\"false\">\n<input type=\"hidden\" name=\"savetype\" value=\"1\">\n<input type=\"hidden\" name=\"monitortype\" value=\"network\">\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 4144 */                       out.print(appname2);
/* 4145 */                       out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 4146 */                       out.print(haid2);
/* 4147 */                       out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 4148 */                       out.print(resourceName2);
/* 4149 */                       out.write(34);
/* 4150 */                       out.write(62);
/* 4151 */                       out.write(10);
/*      */                       
/* 4153 */                       String message2 = request.getParameter("message");
/* 4154 */                       String redirect = "/showresource.do?haid=" + haid2 + "&type=" + resourceName2 + "&method=getMonitorForm";
/* 4155 */                       String encodedurl = URLEncoder.encode(redirect);
/* 4156 */                       String initMonLink = "";
/* 4157 */                       String internaltype = null;
/*      */                       
/*      */ 
/* 4160 */                       boolean allTypes2 = resourceName2.equals("All");
/*      */                       
/* 4162 */                       out.write("\n<br><br>\n\n<table width=\"49%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\n\t\t\t<tr>\n\t\t\t<td class=\"AlarmHdrTopLeft\"/>\n\t\t\t<td class=\"AlarmHdrTopBg\">\n\n\n\t\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\n\t\t\t<tr>\n\t\t\t<td  valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\">\n\n\n\t\t<span class=\"bcactive\"> ");
/* 4163 */                       if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                       {
/* 4165 */                         out.write("\n\n\n\t\t\t\t\t\t    <td height=\"25\"  class=\"AlarmCardContentBg\">");
/* 4166 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.avlnwdevices.text"));
/* 4167 */                         out.write("</td>\n\t\t\t\t\t\t  ");
/*      */                       } else {
/* 4169 */                         out.write("\n\t\t\t\t\t\t\t   <td height=\"25\" class=\"AlarmCardContentBg\"><b>&nbsp;");
/* 4170 */                         out.print(FormatUtil.getString("Storage Devices"));
/* 4171 */                         out.write("</b></td>\n\t\t\t\t\t\t  ");
/*      */                       }
/* 4173 */                       out.write("\n</span>\n\n\n\n\t\t\t</td>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\">\n\t\t\t</td>\n\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t</table></td>\n\t\t\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n\n    <td style=\"padding-top:3px;\" class=\"AlarmboxedContent\">\n    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n      <tr>\n          <td >\n           <!--text -->\n          </td>\n      </tr>\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n        <tr>\n\n          <td class=\"AlarmInnerTopLeft\"/>\n          <td class=\"AlarmInnerTopBg\"/>\n          <td class=\"AlarmInnerTopRight\"/>\n        </tr>\n        <tr>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n                <td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n");
/* 4174 */                       out.write("\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t\t\t\t<tr>\n\n\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t    <tr>\n\t\t\t\t      <td  width=\"50%\" valign=\"top\"> ");
/*      */                       
/* 4176 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f7 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4177 */                       _jspx_th_logic_005fnotEmpty_005f7.setPageContext(_jspx_page_context);
/* 4178 */                       _jspx_th_logic_005fnotEmpty_005f7.setParent(_jspx_th_html_005fform_005f3);
/*      */                       
/* 4180 */                       _jspx_th_logic_005fnotEmpty_005f7.setName("opstordevtoconfigure");
/* 4181 */                       int _jspx_eval_logic_005fnotEmpty_005f7 = _jspx_th_logic_005fnotEmpty_005f7.doStartTag();
/* 4182 */                       if (_jspx_eval_logic_005fnotEmpty_005f7 != 0) {
/*      */                         for (;;) {
/* 4184 */                           out.write("\n\t\t\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\"  style=\"background-color:#fff;\">\n\t\t\t\t          <tr>\n\n\t\t\t\t");
/* 4185 */                           if (allTypes2) {
/* 4186 */                             out.write("\n\t\t\t\t<td colspan=\"3\">\n\t\t\t\t");
/*      */                           } else {
/* 4188 */                             out.write("\n\t\t\t\t<td colspan=\"2\" >\n\t\t\t\t");
/*      */                           }
/* 4190 */                           out.write("\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  height=\"26\">\n\t\t\t\t\t<tr>\n\t\t\t\t");
/*      */                           
/* 4192 */                           int s = ((List)request.getAttribute("opstordevtoconfigure")).size();
/* 4193 */                           if (s > 0)
/*      */                           {
/* 4195 */                             out.write("\n\n\n\t\t\t\t            ");
/* 4196 */                             if (allTypes2) {
/* 4197 */                               out.write("\n\t\t\t\t            <td height=\"31\" colspan=\"3\" class=\"columnheadingnotop\" align=\"left\">\n\t\t\t\t            ");
/*      */                             } else {
/* 4199 */                               out.write("\n\t\t\t\t            <td height=\"31\" colspan=\"2\" class=\"columnheadingnotop\" align=\"left\">\n\t\t\t\t            ");
/*      */                             }
/* 4201 */                             out.write("\n\t\t\t\t    <input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4202 */                             out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 4203 */                             out.write("\" onClick=\"javascript:fnOpMonFormSubmit2('2', '4')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 4204 */                             out.print(FormatUtil.getString("am.common.or.text"));
/* 4205 */                             out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4206 */                             out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 4207 */                             out.write("\" onClick=\"javascript:fnOpMonFormSubmit2('2', '4', 'true')\">\n\t\t\t\t\t</td>\n\n\n\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 4211 */                           out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t          </tr>\n\t\t\t\t          <tr>\n\t\t\t\t            <td class=\" whitegrayborder\" width=\"5%\"><input type=\"checkbox\" onClick=\"javascript:fnSelectAllOpMon2(this, '4', 'selectedStorageresource')\"></td>\n\t\t\t\t            <td class=\"bodytextbold whitegrayborder\" align=\"left\" width=\"75%\"><b>");
/* 4212 */                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 4213 */                           out.write("</b></td>\n\t\t\t\t            ");
/* 4214 */                           if (allTypes2) {
/* 4215 */                             out.write("\n\t\t\t\t            <td class=\"whitegrayborder\" width=\"20%\"><b>");
/* 4216 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 4217 */                             out.write("</b></td>\n\t\t\t\t            ");
/*      */                           }
/* 4219 */                           out.write("\n\t\t\t\t          </tr>\n\t\t\t\t          ");
/*      */                           
/* 4221 */                           IterateTag _jspx_th_logic_005fiterate_005f6 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4222 */                           _jspx_th_logic_005fiterate_005f6.setPageContext(_jspx_page_context);
/* 4223 */                           _jspx_th_logic_005fiterate_005f6.setParent(_jspx_th_logic_005fnotEmpty_005f7);
/*      */                           
/* 4225 */                           _jspx_th_logic_005fiterate_005f6.setName("opstordevtoconfigure");
/*      */                           
/* 4227 */                           _jspx_th_logic_005fiterate_005f6.setId("row");
/*      */                           
/* 4229 */                           _jspx_th_logic_005fiterate_005f6.setIndexId("j");
/*      */                           
/* 4231 */                           _jspx_th_logic_005fiterate_005f6.setType("java.util.ArrayList");
/* 4232 */                           int _jspx_eval_logic_005fiterate_005f6 = _jspx_th_logic_005fiterate_005f6.doStartTag();
/* 4233 */                           if (_jspx_eval_logic_005fiterate_005f6 != 0) {
/* 4234 */                             ArrayList row = null;
/* 4235 */                             Integer j = null;
/* 4236 */                             if (_jspx_eval_logic_005fiterate_005f6 != 1) {
/* 4237 */                               out = _jspx_page_context.pushBody();
/* 4238 */                               _jspx_th_logic_005fiterate_005f6.setBodyContent((BodyContent)out);
/* 4239 */                               _jspx_th_logic_005fiterate_005f6.doInitBody();
/*      */                             }
/* 4241 */                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4242 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                             for (;;) {
/* 4244 */                               out.write("\n\t\t\t\t          ");
/*      */                               
/* 4246 */                               String deviceName = null;
/* 4247 */                               deviceName = (String)row.get(0) + "$" + (String)row.get(3);
/* 4248 */                               String dispName = (String)row.get(1);
/* 4249 */                               dispName = EnterpriseUtil.decodeString(dispName);
/*      */                               
/* 4251 */                               String category = "";
/* 4252 */                               if (allTypes2)
/*      */                               {
/* 4254 */                                 category = (String)row.get(2);
/* 4255 */                                 int startIndex = category.indexOf("-");
/* 4256 */                                 category = category.substring(startIndex + 1, category.length());
/* 4257 */                                 deviceName = deviceName + "$" + category;
/*      */                               }
/* 4259 */                               if (j.intValue() % 2 == 0)
/*      */                               {
/* 4261 */                                 bgcolour2 = "class=\"whitegrayborder\"";
/*      */                               }
/*      */                               else
/*      */                               {
/* 4265 */                                 bgcolour2 = "class=\"yellowgrayborder\"";
/*      */                               }
/*      */                               
/* 4268 */                               out.write("\n\t\t\t\t          <tr>\n\t\t\t\t            <td width=\"2%\" ");
/* 4269 */                               out.print(bgcolour2);
/* 4270 */                               out.write("><input type=\"checkbox\" name=\"selectedStorageresource\" value=\"");
/* 4271 */                               out.print(deviceName);
/* 4272 */                               out.write("\"></td>\n\t\t\t\t            <td width=\"78%\" ");
/* 4273 */                               out.print(bgcolour2);
/* 4274 */                               out.write(" align=\"left\">");
/*      */                               
/* 4276 */                               Truncate _jspx_th_am_005fTruncate_005f3 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 4277 */                               _jspx_th_am_005fTruncate_005f3.setPageContext(_jspx_page_context);
/* 4278 */                               _jspx_th_am_005fTruncate_005f3.setParent(_jspx_th_logic_005fiterate_005f6);
/*      */                               
/* 4280 */                               _jspx_th_am_005fTruncate_005f3.setTooltip("true");
/*      */                               
/* 4282 */                               _jspx_th_am_005fTruncate_005f3.setLength(50);
/* 4283 */                               int _jspx_eval_am_005fTruncate_005f3 = _jspx_th_am_005fTruncate_005f3.doStartTag();
/* 4284 */                               if (_jspx_eval_am_005fTruncate_005f3 != 0) {
/* 4285 */                                 if (_jspx_eval_am_005fTruncate_005f3 != 1) {
/* 4286 */                                   out = _jspx_page_context.pushBody();
/* 4287 */                                   _jspx_th_am_005fTruncate_005f3.setBodyContent((BodyContent)out);
/* 4288 */                                   _jspx_th_am_005fTruncate_005f3.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 4291 */                                   out.print(dispName);
/* 4292 */                                   int evalDoAfterBody = _jspx_th_am_005fTruncate_005f3.doAfterBody();
/* 4293 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4296 */                                 if (_jspx_eval_am_005fTruncate_005f3 != 1) {
/* 4297 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4300 */                               if (_jspx_th_am_005fTruncate_005f3.doEndTag() == 5) {
/* 4301 */                                 this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f3); return;
/*      */                               }
/*      */                               
/* 4304 */                               this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f3);
/* 4305 */                               out.write("</td>");
/* 4306 */                               out.write("\n\n\t\t\t\t            ");
/* 4307 */                               if (allTypes2) {
/* 4308 */                                 out.write("\n\t\t\t\t\t\t\t\t<td width=\"20%\" ");
/* 4309 */                                 out.print(bgcolour2);
/* 4310 */                                 out.write(" align=\"left\">");
/* 4311 */                                 out.print(category);
/* 4312 */                                 out.write("</td>\n\t\t\t\t            ");
/*      */                               }
/* 4314 */                               out.write("\n\n\n\n\t\t\t\t          </tr>\n\t\t\t\t          ");
/* 4315 */                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f6.doAfterBody();
/* 4316 */                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4317 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 4318 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 4321 */                             if (_jspx_eval_logic_005fiterate_005f6 != 1) {
/* 4322 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 4325 */                           if (_jspx_th_logic_005fiterate_005f6.doEndTag() == 5) {
/* 4326 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f6); return;
/*      */                           }
/*      */                           
/* 4329 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f6);
/* 4330 */                           out.write("\n\n\t\t\t\t          <tr>\n\t\t\t\t            ");
/* 4331 */                           if (allTypes2) {
/* 4332 */                             out.write("\n\t\t\t\t            <td colspan=\"3\" class=\"columnheadingnotop\" align=\"left\" style=\"height:26px;\">\n\t\t\t\t            ");
/*      */                           } else {
/* 4334 */                             out.write("\n\t\t\t\t            <td  colspan=\"2\" class=\"columnheadingnotop\" align=\"left\" style=\"height:26px;\">\n\t\t\t\t            ");
/*      */                           }
/* 4336 */                           out.write("\n\t\t\t\t            <input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4337 */                           out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 4338 */                           out.write("\" onClick=\"javascript:fnOpMonFormSubmit2('2', '4')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 4339 */                           out.print(FormatUtil.getString("am.common.or.text"));
/* 4340 */                           out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4341 */                           out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 4342 */                           out.write("\" onClick=\"javascript:fnOpMonFormSubmit2('2', '4', 'true')\">\n\t\t\t\t\t</td>\n\t\t\t\t          </tr>\n\n\t\t\t\t        </table>\n\t\t\t\t        ");
/* 4343 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f7.doAfterBody();
/* 4344 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4348 */                       if (_jspx_th_logic_005fnotEmpty_005f7.doEndTag() == 5) {
/* 4349 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f7); return;
/*      */                       }
/*      */                       
/* 4352 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f7);
/* 4353 */                       out.write(32);
/*      */                       
/* 4355 */                       EmptyTag _jspx_th_logic_005fempty_005f5 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4356 */                       _jspx_th_logic_005fempty_005f5.setPageContext(_jspx_page_context);
/* 4357 */                       _jspx_th_logic_005fempty_005f5.setParent(_jspx_th_html_005fform_005f3);
/*      */                       
/* 4359 */                       _jspx_th_logic_005fempty_005f5.setName("opstordevtoconfigure");
/* 4360 */                       int _jspx_eval_logic_005fempty_005f5 = _jspx_th_logic_005fempty_005f5.doStartTag();
/* 4361 */                       if (_jspx_eval_logic_005fempty_005f5 != 0) {
/*      */                         for (;;) {
/* 4363 */                           out.write("\n\t\t\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"background-color:#fff;\">\n\t\t\t\t          <tr>\n\t\t\t\t<td >\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\t\t\t\t\t<tr>\n\t\t\t\t\t ");
/* 4364 */                           if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                           {
/* 4366 */                             out.write("   <td height=\"25\"  colspan=\"2\" class=\"columnheading\">");
/* 4367 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.avlnwdevices.text"));
/* 4368 */                             out.write("</td>\n\t\t\t\t\t\t  ");
/*      */                           } else {
/* 4370 */                             out.write("\n\t\t\t\t\t\t\t   <td height=\"25\"  colspan=\"2\" class=\"columnheading\"></td>\n\t\t\t\t\t\t  ");
/*      */                           }
/* 4372 */                           out.write("\n\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\n\t\t\t\t          </tr>\n\t\t\t\t          <tr>\n\n\t\t\t\t          <td  height=\"25\"><span class=\"bodytext\"> &nbsp;");
/* 4373 */                           out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4374 */                           out.write(". </span></td>\n\t\t\t\t          </tr>\n\t\t\t\t        </table>\n\t\t\t\t        ");
/* 4375 */                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f5.doAfterBody();
/* 4376 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4380 */                       if (_jspx_th_logic_005fempty_005f5.doEndTag() == 5) {
/* 4381 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5); return;
/*      */                       }
/*      */                       
/* 4384 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5);
/* 4385 */                       out.write("\n\t\t\t\t\t\t</td>\n\n\t\t\t\n\n\t\t\t\t  </table>\n\t\t\t\t\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"25%\">\n\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"30%\">\t</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t</table>\n            </td>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t\t\t<td class=\"AlarmCardMainBtmBg\"/>\n\t\t\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n\n\t");
/* 4386 */                       int evalDoAfterBody = _jspx_th_html_005fform_005f3.doAfterBody();
/* 4387 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4391 */                   if (_jspx_th_html_005fform_005f3.doEndTag() == 5) {
/* 4392 */                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f3); return;
/*      */                   }
/*      */                   
/* 4395 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f3);
/* 4396 */                   out.write("\n\t</body>\n\t\n");
/*      */                 }
/* 4398 */                 out.write(" \n  \t");
/* 4399 */                 if (!OEMUtil.isOEM()) {
/* 4400 */                   out.write("\n     \t");
/* 4401 */                   out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */                   
/* 4403 */                   String mgid = request.getParameter("haid");
/* 4404 */                   String selectedscheme2 = null;
/* 4405 */                   boolean slimLayout2 = false;
/* 4406 */                   if (session.getAttribute("selectedscheme") != null)
/*      */                   {
/* 4408 */                     selectedscheme2 = (String)session.getAttribute("selectedscheme");
/*      */                   }
/* 4410 */                   if ((selectedscheme2 != null) && (selectedscheme2.equals("slim")))
/*      */                   {
/* 4412 */                     slimLayout2 = true;
/*      */                   }
/*      */                   
/* 4415 */                   out.write("\n \n \n<script>\n \nfunction fnSelectAllOpMon1(e,formindex, checkboxstr)\n{\n\tvar temp=formindex;\n\tif(");
/* 4416 */                   out.print(slimLayout2);
/* 4417 */                   out.write(")\n\t{\n\t\ttemp=formindex-1;\n\t}\n\tToggleAll(e,document.forms[temp],checkboxstr)\n}\n\nfunction fnDisplayName1(combo)\n{\n\tvar v=combo.options[combo.selectedIndex].text;\n\tdocument.forms[formindex].displayname.value=v;\n}\n\nfunction fnOpMonFormSubmit1(a,temp,goback)\n{\n        var name='selectedSite24x7resource'; //No I18N\n        var msg = ' ");
/* 4418 */                   out.print(FormatUtil.getString("add"));
/* 4419 */                   out.write("';\n\t\tvar formindex=temp;\n\t\tif(");
/* 4420 */                   out.print(slimLayout2);
/* 4421 */                   out.write(")\n\t\t{\n\t\t\tformindex=temp-1;\n\t\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 4422 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 4423 */                   out.write(" ' + msg);\n\t\treturn;\n\t}\t\n\tif(goback)\n\t{\n\t\tdocument.getElementsByName(\"goback\")[3].value='true';\t\n\t}\n\telse\n\t{\n\t\tdocument.getElementsByName(\"goback\")[3].value='false';\t\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].submit();\n}\n\n</script> \n \n \n <br>\n <body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
/*      */                   
/* 4425 */                   FormTag _jspx_th_html_005fform_005f4 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 4426 */                   _jspx_th_html_005fform_005f4.setPageContext(_jspx_page_context);
/* 4427 */                   _jspx_th_html_005fform_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 4429 */                   _jspx_th_html_005fform_005f4.setAction("/extDeviceAction.do");
/*      */                   
/* 4431 */                   _jspx_th_html_005fform_005f4.setMethod("post");
/*      */                   
/* 4433 */                   _jspx_th_html_005fform_005f4.setStyle("display:inline;");
/* 4434 */                   int _jspx_eval_html_005fform_005f4 = _jspx_th_html_005fform_005f4.doStartTag();
/* 4435 */                   if (_jspx_eval_html_005fform_005f4 != 0) {
/*      */                     for (;;) {
/* 4437 */                       out.write("\n<input type=\"hidden\" name=\"org.apache.struts.taglib.html.TOKEN\" value=\"");
/* 4438 */                       out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 4439 */                       out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"addSite24x7Resource\">\n<input type=\"hidden\" name=\"goback\" value=\"false\">\n<input type=\"hidden\" name=\"savetype\" value=\"1\">\n<input type=\"hidden\" name=\"monitortype\" value=\"network\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 4440 */                       out.print(mgid);
/* 4441 */                       out.write("\">\n\n<table width=\"49%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n  <tr>\n\t\t<td class=\"AlarmHdrTopLeft\"/><td class=\"AlarmHdrTopBg\">\n \t\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\t\t\t<tr>\n\t\t\t<td  valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\">\n\t\t\t\t  <span class=\"bcactive\"> <td height=\"25\" class=\"AlarmCardContentBg\"><b>&nbsp;");
/* 4442 */                       out.print(FormatUtil.getString("am.webclient.site24x7.heading"));
/* 4443 */                       out.write("</b></td></span>\n\t\t\t</td>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\"></td><td valign=\"middle\" align=\"left\">&nbsp;</td></tr>\n\t\t\t</table>\n\t\t    </td>\n\t\t\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n</tr>\n<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n\n    <td style=\"padding-top:3px;\" class=\"AlarmboxedContent\">\n    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n      \n      </tr>\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n        <tr>\n\n          <td class=\"AlarmInnerTopLeft\"/>\n          <td class=\"AlarmInnerTopBg\"/>\n          <td class=\"AlarmInnerTopRight\"/>\n        </tr>\n        <tr>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n                <td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t\t\t\t<tr>\n");
/* 4444 */                       out.write("\n\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t    <tr>\n\t\t\t\t      <td  width=\"50%\" valign=\"top\">  \n\t\t\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\"  style=\"background-color:#fff;\">\n\t\t\t\t\t\t\t\t\t\t\t\t \n\n");
/* 4445 */                       if (_jspx_meth_c_005fset_005f0(_jspx_th_html_005fform_005f4, _jspx_page_context))
/*      */                         return;
/* 4447 */                       out.write("\n\t\t\t  \n  \n  \n ");
/*      */                       
/* 4449 */                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4450 */                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4451 */                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_html_005fform_005f4);
/*      */                       
/* 4453 */                       _jspx_th_c_005fforEach_005f1.setItems("${configuredSite24x7Mon}");
/*      */                       
/* 4455 */                       _jspx_th_c_005fforEach_005f1.setVar("row1");
/*      */                       
/* 4457 */                       _jspx_th_c_005fforEach_005f1.setVarStatus("rowcount1");
/* 4458 */                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                       try {
/* 4460 */                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4461 */                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                           for (;;) {
/* 4463 */                             out.write("\t\t\t\t\t\t  \n\t\t\t\t\t\n\t\t\t\t\t\n ");
/*      */                             
/* 4465 */                             ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4466 */                             _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 4467 */                             _jspx_th_c_005fforEach_005f2.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 4469 */                             _jspx_th_c_005fforEach_005f2.setItems("${row1.value}");
/*      */                             
/* 4471 */                             _jspx_th_c_005fforEach_005f2.setVar("row");
/*      */                             
/* 4473 */                             _jspx_th_c_005fforEach_005f2.setVarStatus("rowcount");
/* 4474 */                             int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */                             try {
/* 4476 */                               int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 4477 */                               if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */                                 for (;;) {
/* 4479 */                                   out.write("\n \n\t\t");
/*      */                                   
/* 4481 */                                   IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4482 */                                   _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 4483 */                                   _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fforEach_005f2);
/*      */                                   
/* 4485 */                                   _jspx_th_c_005fif_005f2.setTest("${temp == '1'}");
/* 4486 */                                   int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 4487 */                                   if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                     for (;;) {
/* 4489 */                                       out.write("\n\t\t\t \t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"3\">\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  height=\"26\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t  <td height=\"31\" colspan=\"3\" class=\"columnheadingnotop\" align=\"left\">\n\t\t\t\t\t\t\t\t\t\t<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4490 */                                       out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 4491 */                                       out.write("\" onClick=\"javascript:fnOpMonFormSubmit1('2', '5')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 4492 */                                       out.print(FormatUtil.getString("am.common.or.text"));
/* 4493 */                                       out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4494 */                                       out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 4495 */                                       out.write("\" onClick=\"javascript:fnOpMonFormSubmit1('2', '5', 'true')\">\n\t\t\t\t\t\t\t\t\t  </td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t   </td>\n\t\t\t\t\t\t</tr>\n \n\t\t\t\t\t\t <tr>\n\t\t\t\t            <td class=\" whitegrayborder\" width=\"5%\"><input type=\"checkbox\" onClick=\"javascript:fnSelectAllOpMon1(this, '5', 'selectedSite24x7resource')\"></td>\n\t\t\t\t            <td class=\"bodytextbold whitegrayborder\" align=\"left\" width=\"75%\"><b>");
/* 4496 */                                       out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 4497 */                                       out.write("</b></td>\n\t\t\t\t            <td class=\"whitegrayborder\" width=\"20%\"><b>");
/* 4498 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 4499 */                                       out.write("</b></td>\n\t\t\t\t          </tr>\t\t\t\t\t\t \n\t");
/* 4500 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 4501 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4505 */                                   if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 4506 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
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
/* 4538 */                                     _jspx_th_c_005fforEach_005f2.doFinally();
/* 4539 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
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
/* 4557 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 4558 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 4509 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4510 */                                   out.write(10);
/* 4511 */                                   out.write(9);
/* 4512 */                                   if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4538 */                                     _jspx_th_c_005fforEach_005f2.doFinally();
/* 4539 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
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
/* 4557 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 4558 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 4514 */                                   out.write("\n\t\t\t\t          <tr>\n\t\t\t\t            <td width=\"2%\" class=\"whitegrayborder\"><input type=\"checkbox\" name=\"selectedSite24x7resource\" value=\"");
/* 4515 */                                   if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4538 */                                     _jspx_th_c_005fforEach_005f2.doFinally();
/* 4539 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
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
/* 4557 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 4558 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 4517 */                                   out.write("\"/></td>\n\t\t\t\t            <td width=\"78%\" class=\"whitegrayborder\" align=\"left\">");
/* 4518 */                                   if (_jspx_meth_am_005fTruncate_005f4(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                   {
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
/*      */ 
/* 4538 */                                     _jspx_th_c_005fforEach_005f2.doFinally();
/* 4539 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
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
/* 4557 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 4558 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 4520 */                                   out.write("</td>");
/* 4521 */                                   out.write("\n                            <td width=\"20%\" class=\"whitegrayborder\" align=\"left\">");
/* 4522 */                                   if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                   {
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
/* 4538 */                                     _jspx_th_c_005fforEach_005f2.doFinally();
/* 4539 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
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
/* 4557 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 4558 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 4524 */                                   out.write("</td>\t \n\t\t\t\t          </tr>\n ");
/* 4525 */                                   int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 4526 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4530 */                               if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4538 */                                 _jspx_th_c_005fforEach_005f2.doFinally();
/* 4539 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
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
/* 4557 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4558 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                               }
/*      */                             }
/*      */                             catch (Throwable _jspx_exception)
/*      */                             {
/*      */                               for (;;)
/*      */                               {
/* 4534 */                                 int tmp14803_14802 = 0; int[] tmp14803_14800 = _jspx_push_body_count_c_005fforEach_005f2; int tmp14805_14804 = tmp14803_14800[tmp14803_14802];tmp14803_14800[tmp14803_14802] = (tmp14805_14804 - 1); if (tmp14805_14804 <= 0) break;
/* 4535 */                                 out = _jspx_page_context.popBody(); }
/* 4536 */                               _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */                             }
/*      */                             finally {}
/*      */                             
/*      */ 
/* 4541 */                             out.write(10);
/* 4542 */                             out.write(10);
/* 4543 */                             out.write(32);
/* 4544 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4545 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4549 */                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4557 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4558 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 4553 */                           int tmp14959_14958 = 0; int[] tmp14959_14956 = _jspx_push_body_count_c_005fforEach_005f1; int tmp14961_14960 = tmp14959_14956[tmp14959_14958];tmp14959_14956[tmp14959_14958] = (tmp14961_14960 - 1); if (tmp14961_14960 <= 0) break;
/* 4554 */                           out = _jspx_page_context.popBody(); }
/* 4555 */                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                       } finally {
/* 4557 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4558 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                       }
/* 4560 */                       out.write(" \n \t\n\t\n\n");
/*      */                       
/* 4562 */                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4563 */                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4564 */                       _jspx_th_c_005fif_005f3.setParent(_jspx_th_html_005fform_005f4);
/*      */                       
/* 4566 */                       _jspx_th_c_005fif_005f3.setTest("${temp == '2'}");
/* 4567 */                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4568 */                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                         for (;;) {
/* 4570 */                           out.write("\n\t\n\t\t                <tr>\n\t\t\t\t            <td colspan=\"3\" class=\"columnheadingnotop\" align=\"left\" style=\"height:26px;\"> \n\t\t\t\t            <input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4571 */                           out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 4572 */                           out.write("\" onClick=\"javascript:fnOpMonFormSubmit1('2', '5')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 4573 */                           out.print(FormatUtil.getString("am.common.or.text"));
/* 4574 */                           out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 4575 */                           out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 4576 */                           out.write("\" onClick=\"javascript:fnOpMonFormSubmit1('2', '5', 'true')\">\n\t\t\t\t      \t   </td>\n\t\t\t\t          </tr>\n\t\n");
/* 4577 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4578 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4582 */                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4583 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                       }
/*      */                       
/* 4586 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4587 */                       out.write("\n\t\n ");
/*      */                       
/* 4589 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4590 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 4591 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f4);
/*      */                       
/* 4593 */                       _jspx_th_c_005fif_005f4.setTest("${temp == '1'}");
/* 4594 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 4595 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/* 4597 */                           out.write("\n <tr>\n\t\t\t\t            <td class=\" whitegrayborder\" width=\"5%\" colspan=\"3\">");
/* 4598 */                           out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4599 */                           out.write("</td>\n\t\t\t\t          </tr>\t\t\t\t       \n");
/* 4600 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4601 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4605 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 4606 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/* 4609 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4610 */                       out.write("\n\n\n\t\t\t\t        </table>\n\t\t\t\t        \n\n \n\n\t       \n\t\t\t\t\t\t</td>\n \n\n\t\t\t\t  </table>\n\t\t\t \n\n\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\n\n\n\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"25%\">\n\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"30%\">\t</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t</table>\n            </td>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t\t\t<td class=\"AlarmCardMainBtmBg\"/>\n\t\t\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n \n");
/* 4611 */                       int evalDoAfterBody = _jspx_th_html_005fform_005f4.doAfterBody();
/* 4612 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4616 */                   if (_jspx_th_html_005fform_005f4.doEndTag() == 5) {
/* 4617 */                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f4); return;
/*      */                   }
/*      */                   
/* 4620 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f4);
/* 4621 */                   out.write(10);
/* 4622 */                   out.write(10);
/* 4623 */                   out.write(10);
/* 4624 */                   out.write("\n     ");
/*      */                 }
/* 4626 */                 out.write("\n   <br>\n  \n  \n  \t\t");
/*      */               }
/*      */               
/*      */ 
/* 4630 */               out.write("\n   <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n     <td align=\"right\">\n\t     <table >\n\t        <tr>\n\t        <td onClick=\"javascript:BacktoMG()\" class=\"buttons btn_back\" align=\"center\" ><b>");
/* 4631 */               out.print(FormatUtil.getString("am.webclient.monitorgroup.back"));
/* 4632 */               out.write("</b></td>\n\t        </tr>\n\t     </table>\n   </td>\n    </tr>\n   </table>\n\n");
/* 4633 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4634 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 4637 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4638 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 4641 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4642 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 4645 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4646 */           out.write(10);
/* 4647 */           out.write(32);
/* 4648 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 4650 */           out.write(10);
/* 4651 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4652 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4656 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4657 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 4660 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4661 */         out.write(10);
/* 4662 */         out.write(10);
/*      */       }
/* 4664 */     } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 4665 */         out = _jspx_out;
/* 4666 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4667 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 4668 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4671 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4677 */     PageContext pageContext = _jspx_page_context;
/* 4678 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4680 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4681 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4682 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4684 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4686 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 4687 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4688 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4689 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4690 */       return true;
/*      */     }
/* 4692 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4693 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4698 */     PageContext pageContext = _jspx_page_context;
/* 4699 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4701 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4702 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4703 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4705 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */     
/* 4707 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/AddMonitorsLeftPage.jsp");
/* 4708 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4709 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4710 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4711 */       return true;
/*      */     }
/* 4713 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4719 */     PageContext pageContext = _jspx_page_context;
/* 4720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4722 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4723 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4724 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4726 */     _jspx_th_c_005fout_005f0.setValue("${AMActionForm.type}");
/* 4727 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4728 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4729 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4730 */       return true;
/*      */     }
/* 4732 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4733 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4738 */     PageContext pageContext = _jspx_page_context;
/* 4739 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4741 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4742 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4743 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4745 */     _jspx_th_c_005fforEach_005f0.setItems("${breadcrumb}");
/*      */     
/* 4747 */     _jspx_th_c_005fforEach_005f0.setVar("eachmo");
/*      */     
/* 4749 */     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 4750 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 4752 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4753 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 4755 */           out.write("\n                <a href=\"/showapplication.do?method=showApplication&haid=");
/* 4756 */           boolean bool; if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4757 */             return true;
/* 4758 */           out.write("\" class=\"bcinactive\" >");
/* 4759 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4760 */             return true;
/* 4761 */           out.write("</a> &gt;\n                ");
/* 4762 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4763 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4767 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 4768 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4771 */         int tmp241_240 = 0; int[] tmp241_238 = _jspx_push_body_count_c_005fforEach_005f0; int tmp243_242 = tmp241_238[tmp241_240];tmp241_238[tmp241_240] = (tmp243_242 - 1); if (tmp243_242 <= 0) break;
/* 4772 */         out = _jspx_page_context.popBody(); }
/* 4773 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4775 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4776 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 4778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4783 */     PageContext pageContext = _jspx_page_context;
/* 4784 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4786 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4787 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4788 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4790 */     _jspx_th_c_005fout_005f1.setValue("${eachmo.resourceid}");
/* 4791 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4792 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4793 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4794 */       return true;
/*      */     }
/* 4796 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4802 */     PageContext pageContext = _jspx_page_context;
/* 4803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4805 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4806 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4807 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4809 */     _jspx_th_c_005fout_005f2.setValue("${eachmo.displayname}");
/* 4810 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4811 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4812 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4813 */       return true;
/*      */     }
/* 4815 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4821 */     PageContext pageContext = _jspx_page_context;
/* 4822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4824 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4825 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4826 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4828 */     _jspx_th_c_005fout_005f3.setValue("${applicationScope.applications[param.haid]}");
/* 4829 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4830 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4831 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4832 */       return true;
/*      */     }
/* 4834 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4835 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4840 */     PageContext pageContext = _jspx_page_context;
/* 4841 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4843 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4844 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4845 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4847 */     _jspx_th_c_005fout_005f4.setValue("${applicationScope.applications[param.haid]}");
/* 4848 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4849 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4850 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4851 */       return true;
/*      */     }
/* 4853 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4854 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4859 */     PageContext pageContext = _jspx_page_context;
/* 4860 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4862 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 4863 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 4864 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4866 */     _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */     
/* 4868 */     _jspx_th_html_005fselect_005f0.setOnchange("javascript:loadURL(document.forms[1].type.options[this.selectedIndex].value);");
/*      */     
/* 4870 */     _jspx_th_html_005fselect_005f0.setStyleClass("chosenselect");
/*      */     
/* 4872 */     _jspx_th_html_005fselect_005f0.setStyle("position:relative; bottom:5px;");
/* 4873 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 4874 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 4875 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4876 */         out = _jspx_page_context.pushBody();
/* 4877 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 4878 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4881 */         out.write("\n\t\t\t\t         ");
/* 4882 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 4883 */           return true;
/* 4884 */         out.write("\n\t\t\t       ");
/* 4885 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4886 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4889 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4890 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4893 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4894 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 4895 */       return true;
/*      */     }
/* 4897 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 4898 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4903 */     PageContext pageContext = _jspx_page_context;
/* 4904 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4906 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4907 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 4908 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 4910 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("resourceTypes");
/* 4911 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 4912 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 4913 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4914 */       return true;
/*      */     }
/* 4916 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4917 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4922 */     PageContext pageContext = _jspx_page_context;
/* 4923 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4925 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4926 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4927 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 4929 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 4930 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4931 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4932 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4933 */       return true;
/*      */     }
/* 4935 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4936 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_html_005fform_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4941 */     PageContext pageContext = _jspx_page_context;
/* 4942 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4944 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4945 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4946 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_html_005fform_005f4);
/*      */     
/* 4948 */     _jspx_th_c_005fset_005f0.setVar("temp");
/*      */     
/* 4950 */     _jspx_th_c_005fset_005f0.setValue("1");
/* 4951 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4952 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4953 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4954 */       return true;
/*      */     }
/* 4956 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 4962 */     PageContext pageContext = _jspx_page_context;
/* 4963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4965 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4966 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4967 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 4969 */     _jspx_th_c_005fset_005f1.setVar("temp");
/*      */     
/* 4971 */     _jspx_th_c_005fset_005f1.setValue("2");
/* 4972 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4973 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4974 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4975 */       return true;
/*      */     }
/* 4977 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4978 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 4983 */     PageContext pageContext = _jspx_page_context;
/* 4984 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4986 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4987 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4988 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 4990 */     _jspx_th_c_005fout_005f6.setValue("${row.key}");
/* 4991 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4992 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4993 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4994 */       return true;
/*      */     }
/* 4996 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f4(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5002 */     PageContext pageContext = _jspx_page_context;
/* 5003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5005 */     Truncate _jspx_th_am_005fTruncate_005f4 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 5006 */     _jspx_th_am_005fTruncate_005f4.setPageContext(_jspx_page_context);
/* 5007 */     _jspx_th_am_005fTruncate_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5009 */     _jspx_th_am_005fTruncate_005f4.setTooltip("true");
/*      */     
/* 5011 */     _jspx_th_am_005fTruncate_005f4.setLength(50);
/* 5012 */     int _jspx_eval_am_005fTruncate_005f4 = _jspx_th_am_005fTruncate_005f4.doStartTag();
/* 5013 */     if (_jspx_eval_am_005fTruncate_005f4 != 0) {
/* 5014 */       if (_jspx_eval_am_005fTruncate_005f4 != 1) {
/* 5015 */         out = _jspx_page_context.pushBody();
/* 5016 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 5017 */         _jspx_th_am_005fTruncate_005f4.setBodyContent((BodyContent)out);
/* 5018 */         _jspx_th_am_005fTruncate_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5021 */         out.write(32);
/* 5022 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_am_005fTruncate_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5023 */           return true;
/* 5024 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f4.doAfterBody();
/* 5025 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5028 */       if (_jspx_eval_am_005fTruncate_005f4 != 1) {
/* 5029 */         out = _jspx_page_context.popBody();
/* 5030 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 5033 */     if (_jspx_th_am_005fTruncate_005f4.doEndTag() == 5) {
/* 5034 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f4);
/* 5035 */       return true;
/*      */     }
/* 5037 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f4);
/* 5038 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_am_005fTruncate_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5043 */     PageContext pageContext = _jspx_page_context;
/* 5044 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5046 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5047 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5048 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_am_005fTruncate_005f4);
/*      */     
/* 5050 */     _jspx_th_c_005fout_005f7.setValue("${row.value['displayname']}");
/* 5051 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5052 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5053 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5054 */       return true;
/*      */     }
/* 5056 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5057 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5062 */     PageContext pageContext = _jspx_page_context;
/* 5063 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5065 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5066 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5067 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5069 */     _jspx_th_c_005fout_005f8.setValue("${row.value['monitortype']}");
/* 5070 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5071 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5072 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5073 */       return true;
/*      */     }
/* 5075 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5076 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5081 */     PageContext pageContext = _jspx_page_context;
/* 5082 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5084 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5085 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 5086 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5088 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 5090 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 5091 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 5092 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5093 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5094 */       return true;
/*      */     }
/* 5096 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5097 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\chooseresource_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */