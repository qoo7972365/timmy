/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.jdk15.JDK15Graph;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class JDK15Overview_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   49 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   52 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   53 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   54 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   61 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   66 */     ArrayList list = null;
/*   67 */     StringBuffer sbf = new StringBuffer();
/*   68 */     ManagedApplication mo = new ManagedApplication();
/*   69 */     if (distinct)
/*      */     {
/*   71 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   75 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   78 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   80 */       ArrayList row = (ArrayList)list.get(i);
/*   81 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   82 */       if (distinct) {
/*   83 */         sbf.append(row.get(0));
/*      */       } else
/*   85 */         sbf.append(row.get(1));
/*   86 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   89 */     return sbf.toString(); }
/*      */   
/*   91 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   94 */     if (severity == null)
/*      */     {
/*   96 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   98 */     if (severity.equals("5"))
/*      */     {
/*  100 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  102 */     if (severity.equals("1"))
/*      */     {
/*  104 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  109 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  116 */     if (severity == null)
/*      */     {
/*  118 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  120 */     if (severity.equals("1"))
/*      */     {
/*  122 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  124 */     if (severity.equals("4"))
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  128 */     if (severity.equals("5"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  135 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  141 */     if (severity == null)
/*      */     {
/*  143 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  145 */     if (severity.equals("5"))
/*      */     {
/*  147 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  149 */     if (severity.equals("1"))
/*      */     {
/*  151 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  155 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  161 */     if (severity == null)
/*      */     {
/*  163 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  165 */     if (severity.equals("1"))
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  169 */     if (severity.equals("4"))
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  173 */     if (severity.equals("5"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  179 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  185 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  191 */     if (severity == 5)
/*      */     {
/*  193 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  195 */     if (severity == 1)
/*      */     {
/*  197 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  202 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  208 */     if (severity == null)
/*      */     {
/*  210 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  212 */     if (severity.equals("5"))
/*      */     {
/*  214 */       if (isAvailability) {
/*  215 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  218 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  221 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  223 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  225 */     if (severity.equals("1"))
/*      */     {
/*  227 */       if (isAvailability) {
/*  228 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  231 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  238 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  245 */     if (severity == null)
/*      */     {
/*  247 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  249 */     if (severity.equals("5"))
/*      */     {
/*  251 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  253 */     if (severity.equals("4"))
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  257 */     if (severity.equals("1"))
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  264 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  270 */     if (severity == null)
/*      */     {
/*  272 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  274 */     if (severity.equals("5"))
/*      */     {
/*  276 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  278 */     if (severity.equals("4"))
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("1"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  289 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  296 */     if (severity == null)
/*      */     {
/*  298 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  300 */     if (severity.equals("5"))
/*      */     {
/*  302 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  304 */     if (severity.equals("4"))
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  308 */     if (severity.equals("1"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  315 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  323 */     StringBuffer out = new StringBuffer();
/*  324 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  325 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  326 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  327 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  328 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  329 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  330 */     out.append("</tr>");
/*  331 */     out.append("</form></table>");
/*  332 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  339 */     if (val == null)
/*      */     {
/*  341 */       return "-";
/*      */     }
/*      */     
/*  344 */     String ret = FormatUtil.formatNumber(val);
/*  345 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  346 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  349 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  353 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  361 */     StringBuffer out = new StringBuffer();
/*  362 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  363 */     out.append("<tr>");
/*  364 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  366 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  368 */     out.append("</tr>");
/*  369 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  373 */       if (j % 2 == 0)
/*      */       {
/*  375 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  379 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  382 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  384 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  387 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  391 */       out.append("</tr>");
/*      */     }
/*  393 */     out.append("</table>");
/*  394 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  395 */     out.append("<tr>");
/*  396 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  397 */     out.append("</tr>");
/*  398 */     out.append("</table>");
/*  399 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  405 */     StringBuffer out = new StringBuffer();
/*  406 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  407 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  408 */     out.append("<tr>");
/*  409 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  410 */     out.append("<tr>");
/*  411 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  412 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  413 */     out.append("</tr>");
/*  414 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  417 */       out.append("<tr>");
/*  418 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  419 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  420 */       out.append("</tr>");
/*      */     }
/*      */     
/*  423 */     out.append("</table>");
/*  424 */     out.append("</table>");
/*  425 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  430 */     if (severity.equals("0"))
/*      */     {
/*  432 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  436 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  443 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  456 */     StringBuffer out = new StringBuffer();
/*  457 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  458 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  460 */       out.append("<tr>");
/*  461 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  462 */       out.append("</tr>");
/*      */       
/*      */ 
/*  465 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  467 */         String borderclass = "";
/*      */         
/*      */ 
/*  470 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  472 */         out.append("<tr>");
/*      */         
/*  474 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  475 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  476 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  482 */     out.append("</table><br>");
/*  483 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  484 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  486 */       List sLinks = secondLevelOfLinks[0];
/*  487 */       List sText = secondLevelOfLinks[1];
/*  488 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  491 */         out.append("<tr>");
/*  492 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  493 */         out.append("</tr>");
/*  494 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  496 */           String borderclass = "";
/*      */           
/*      */ 
/*  499 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  501 */           out.append("<tr>");
/*      */           
/*  503 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  504 */           if (sLinks.get(i).toString().length() == 0) {
/*  505 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  508 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  510 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  514 */     out.append("</table>");
/*  515 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  522 */     StringBuffer out = new StringBuffer();
/*  523 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  524 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  526 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  528 */         out.append("<tr>");
/*  529 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  530 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  534 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  536 */           String borderclass = "";
/*      */           
/*      */ 
/*  539 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  541 */           out.append("<tr>");
/*      */           
/*  543 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  544 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  545 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  548 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  551 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  556 */     out.append("</table><br>");
/*  557 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  558 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  560 */       List sLinks = secondLevelOfLinks[0];
/*  561 */       List sText = secondLevelOfLinks[1];
/*  562 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  565 */         out.append("<tr>");
/*  566 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  567 */         out.append("</tr>");
/*  568 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  570 */           String borderclass = "";
/*      */           
/*      */ 
/*  573 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  575 */           out.append("<tr>");
/*      */           
/*  577 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  578 */           if (sLinks.get(i).toString().length() == 0) {
/*  579 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  582 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  584 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  588 */     out.append("</table>");
/*  589 */     return out.toString();
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
/*  602 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  605 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  608 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  611 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  614 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  617 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  620 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  623 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  631 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  636 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  641 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  646 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  651 */     if (val != null)
/*      */     {
/*  653 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  657 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  662 */     if (val == null) {
/*  663 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  667 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  672 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  678 */     if (val != null)
/*      */     {
/*  680 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  684 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  690 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  695 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  699 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  704 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  709 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  714 */     String hostaddress = "";
/*  715 */     String ip = request.getHeader("x-forwarded-for");
/*  716 */     if (ip == null)
/*  717 */       ip = request.getRemoteAddr();
/*  718 */     java.net.InetAddress add = null;
/*  719 */     if (ip.equals("127.0.0.1")) {
/*  720 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  724 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  726 */     hostaddress = add.getHostName();
/*  727 */     if (hostaddress.indexOf('.') != -1) {
/*  728 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  729 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  733 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  738 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  744 */     if (severity == null)
/*      */     {
/*  746 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  748 */     if (severity.equals("5"))
/*      */     {
/*  750 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  752 */     if (severity.equals("1"))
/*      */     {
/*  754 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  759 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  764 */     ResultSet set = null;
/*  765 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  766 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  768 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  769 */       if (set.next()) { String str1;
/*  770 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  771 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  774 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  779 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  782 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  784 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  788 */     StringBuffer rca = new StringBuffer();
/*  789 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  790 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  793 */     int rcalength = key.length();
/*  794 */     String split = "6. ";
/*  795 */     int splitPresent = key.indexOf(split);
/*  796 */     String div1 = "";String div2 = "";
/*  797 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  799 */       if (rcalength > 180) {
/*  800 */         rca.append("<span class=\"rca-critical-text\">");
/*  801 */         getRCATrimmedText(key, rca);
/*  802 */         rca.append("</span>");
/*      */       } else {
/*  804 */         rca.append("<span class=\"rca-critical-text\">");
/*  805 */         rca.append(key);
/*  806 */         rca.append("</span>");
/*      */       }
/*  808 */       return rca.toString();
/*      */     }
/*  810 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  811 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  812 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  813 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  814 */     getRCATrimmedText(div1, rca);
/*  815 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  818 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  819 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  820 */     getRCATrimmedText(div2, rca);
/*  821 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  823 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  828 */     String[] st = msg.split("<br>");
/*  829 */     for (int i = 0; i < st.length; i++) {
/*  830 */       String s = st[i];
/*  831 */       if (s.length() > 180) {
/*  832 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  834 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  838 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  839 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  841 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  845 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  846 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  847 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  850 */       if (key == null) {
/*  851 */         return ret;
/*      */       }
/*      */       
/*  854 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  855 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  858 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  859 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  860 */       set = AMConnectionPool.executeQueryStmt(query);
/*  861 */       if (set.next())
/*      */       {
/*  863 */         String helpLink = set.getString("LINK");
/*  864 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  867 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  873 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  892 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  883 */         if (set != null) {
/*  884 */           AMConnectionPool.closeStatement(set);
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
/*  898 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  899 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  901 */       String entityStr = (String)keys.nextElement();
/*  902 */       String mmessage = temp.getProperty(entityStr);
/*  903 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  904 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  906 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  912 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  913 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  915 */       String entityStr = (String)keys.nextElement();
/*  916 */       String mmessage = temp.getProperty(entityStr);
/*  917 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  918 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  920 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  925 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  935 */     String des = new String();
/*  936 */     while (str.indexOf(find) != -1) {
/*  937 */       des = des + str.substring(0, str.indexOf(find));
/*  938 */       des = des + replace;
/*  939 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  941 */     des = des + str;
/*  942 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  949 */       if (alert == null)
/*      */       {
/*  951 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  953 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  955 */         return "&nbsp;";
/*      */       }
/*      */       
/*  958 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  960 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  963 */       int rcalength = test.length();
/*  964 */       if (rcalength < 300)
/*      */       {
/*  966 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  970 */       StringBuffer out = new StringBuffer();
/*  971 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  972 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  973 */       out.append("</div>");
/*  974 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  975 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  976 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  981 */       ex.printStackTrace();
/*      */     }
/*  983 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  989 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  994 */     ArrayList attribIDs = new ArrayList();
/*  995 */     ArrayList resIDs = new ArrayList();
/*  996 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  998 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1000 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1002 */       String resourceid = "";
/* 1003 */       String resourceType = "";
/* 1004 */       if (type == 2) {
/* 1005 */         resourceid = (String)row.get(0);
/* 1006 */         resourceType = (String)row.get(3);
/*      */       }
/* 1008 */       else if (type == 3) {
/* 1009 */         resourceid = (String)row.get(0);
/* 1010 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1013 */         resourceid = (String)row.get(6);
/* 1014 */         resourceType = (String)row.get(7);
/*      */       }
/* 1016 */       resIDs.add(resourceid);
/* 1017 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1018 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1020 */       String healthentity = null;
/* 1021 */       String availentity = null;
/* 1022 */       if (healthid != null) {
/* 1023 */         healthentity = resourceid + "_" + healthid;
/* 1024 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1027 */       if (availid != null) {
/* 1028 */         availentity = resourceid + "_" + availid;
/* 1029 */         entitylist.add(availentity);
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
/* 1043 */     Properties alert = getStatus(entitylist);
/* 1044 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1049 */     int size = monitorList.size();
/*      */     
/* 1051 */     String[] severity = new String[size];
/*      */     
/* 1053 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1055 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1056 */       String resourceName1 = (String)row1.get(7);
/* 1057 */       String resourceid1 = (String)row1.get(6);
/* 1058 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1059 */       if (severity[j] == null)
/*      */       {
/* 1061 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1065 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1067 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1069 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1072 */         if (sev > 0) {
/* 1073 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1074 */           monitorList.set(k, monitorList.get(j));
/* 1075 */           monitorList.set(j, t);
/* 1076 */           String temp = severity[k];
/* 1077 */           severity[k] = severity[j];
/* 1078 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1084 */     int z = 0;
/* 1085 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1088 */       int i = 0;
/* 1089 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1092 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1096 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1100 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1102 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1105 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1109 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1112 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1113 */       String resourceName1 = (String)row1.get(7);
/* 1114 */       String resourceid1 = (String)row1.get(6);
/* 1115 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1116 */       if (hseverity[j] == null)
/*      */       {
/* 1118 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1123 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1125 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1128 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1131 */         if (hsev > 0) {
/* 1132 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1133 */           monitorList.set(k, monitorList.get(j));
/* 1134 */           monitorList.set(j, t);
/* 1135 */           String temp1 = hseverity[k];
/* 1136 */           hseverity[k] = hseverity[j];
/* 1137 */           hseverity[j] = temp1;
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
/* 1149 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1150 */     boolean forInventory = false;
/* 1151 */     String trdisplay = "none";
/* 1152 */     String plusstyle = "inline";
/* 1153 */     String minusstyle = "none";
/* 1154 */     String haidTopLevel = "";
/* 1155 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1157 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1159 */         haidTopLevel = request.getParameter("haid");
/* 1160 */         forInventory = true;
/* 1161 */         trdisplay = "table-row;";
/* 1162 */         plusstyle = "none";
/* 1163 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1170 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1173 */     ArrayList listtoreturn = new ArrayList();
/* 1174 */     StringBuffer toreturn = new StringBuffer();
/* 1175 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1176 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1177 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1179 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1181 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1182 */       String childresid = (String)singlerow.get(0);
/* 1183 */       String childresname = (String)singlerow.get(1);
/* 1184 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1185 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1186 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1187 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1188 */       String unmanagestatus = (String)singlerow.get(5);
/* 1189 */       String actionstatus = (String)singlerow.get(6);
/* 1190 */       String linkclass = "monitorgp-links";
/* 1191 */       String titleforres = childresname;
/* 1192 */       String titilechildresname = childresname;
/* 1193 */       String childimg = "/images/trcont.png";
/* 1194 */       String flag = "enable";
/* 1195 */       String dcstarted = (String)singlerow.get(8);
/* 1196 */       String configMonitor = "";
/* 1197 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1198 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1200 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1202 */       if (singlerow.get(7) != null)
/*      */       {
/* 1204 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1206 */       String haiGroupType = "0";
/* 1207 */       if ("HAI".equals(childtype))
/*      */       {
/* 1209 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1211 */       childimg = "/images/trend.png";
/* 1212 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1213 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1214 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1216 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1218 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1220 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1221 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1224 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1226 */         linkclass = "disabledtext";
/* 1227 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1229 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1230 */       String availmouseover = "";
/* 1231 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1233 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1235 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1236 */       String healthmouseover = "";
/* 1237 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1239 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1242 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1243 */       int spacing = 0;
/* 1244 */       if (level >= 1)
/*      */       {
/* 1246 */         spacing = 40 * level;
/*      */       }
/* 1248 */       if (childtype.equals("HAI"))
/*      */       {
/* 1250 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1251 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1252 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1254 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1255 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1256 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1257 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1258 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1259 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1260 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1261 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1262 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1263 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1264 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1266 */         if (!forInventory)
/*      */         {
/* 1268 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1271 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1273 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1275 */           actions = editlink + actions;
/*      */         }
/* 1277 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1279 */           actions = actions + associatelink;
/*      */         }
/* 1281 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1282 */         String arrowimg = "";
/* 1283 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1285 */           actions = "";
/* 1286 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1287 */           checkbox = "";
/* 1288 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1290 */         if (isIt360)
/*      */         {
/* 1292 */           actionimg = "";
/* 1293 */           actions = "";
/* 1294 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1295 */           checkbox = "";
/*      */         }
/*      */         
/* 1298 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1300 */           actions = "";
/*      */         }
/* 1302 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1304 */           checkbox = "";
/*      */         }
/*      */         
/* 1307 */         String resourcelink = "";
/*      */         
/* 1309 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1311 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1315 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1318 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1324 */         if (!isIt360)
/*      */         {
/* 1326 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1330 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1333 */         toreturn.append("</tr>");
/* 1334 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1336 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1337 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1341 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1342 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1345 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1349 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1351 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1353 */             toreturn.append(assocMessage);
/* 1354 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1355 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1356 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1357 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1363 */         String resourcelink = null;
/* 1364 */         boolean hideEditLink = false;
/* 1365 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1367 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1368 */           hideEditLink = true;
/* 1369 */           if (isIt360)
/*      */           {
/* 1371 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1375 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1377 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1379 */           hideEditLink = true;
/* 1380 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1381 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1386 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1389 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1390 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1391 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1392 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1393 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1394 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1395 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1396 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1397 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1398 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1399 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1400 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1401 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1403 */         if (hideEditLink)
/*      */         {
/* 1405 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1407 */         if (!forInventory)
/*      */         {
/* 1409 */           removefromgroup = "";
/*      */         }
/* 1411 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1412 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1413 */           actions = actions + configcustomfields;
/*      */         }
/* 1415 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1417 */           actions = editlink + actions;
/*      */         }
/* 1419 */         String managedLink = "";
/* 1420 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1422 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1423 */           actions = "";
/* 1424 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1425 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1428 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1430 */           checkbox = "";
/*      */         }
/*      */         
/* 1433 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1435 */           actions = "";
/*      */         }
/* 1437 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1438 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1439 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1440 */         if (isIt360)
/*      */         {
/* 1442 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1446 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1448 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1449 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1450 */         if (!isIt360)
/*      */         {
/* 1452 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1456 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1458 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1461 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1468 */       StringBuilder toreturn = new StringBuilder();
/* 1469 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1470 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1471 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1472 */       String title = "";
/* 1473 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1474 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1475 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1476 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1478 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1480 */       else if ("5".equals(severity))
/*      */       {
/* 1482 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1486 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1488 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1489 */       toreturn.append(v);
/*      */       
/* 1491 */       toreturn.append(link);
/* 1492 */       if (severity == null)
/*      */       {
/* 1494 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1496 */       else if (severity.equals("5"))
/*      */       {
/* 1498 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1500 */       else if (severity.equals("4"))
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1504 */       else if (severity.equals("1"))
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1511 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1513 */       toreturn.append("</a>");
/* 1514 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1518 */       ex.printStackTrace();
/*      */     }
/* 1520 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1527 */       StringBuilder toreturn = new StringBuilder();
/* 1528 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1529 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1530 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1531 */       if (message == null)
/*      */       {
/* 1533 */         message = "";
/*      */       }
/*      */       
/* 1536 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1537 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1539 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1540 */       toreturn.append(v);
/*      */       
/* 1542 */       toreturn.append(link);
/*      */       
/* 1544 */       if (severity == null)
/*      */       {
/* 1546 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1548 */       else if (severity.equals("5"))
/*      */       {
/* 1550 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1552 */       else if (severity.equals("1"))
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1559 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1561 */       toreturn.append("</a>");
/* 1562 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1568 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1571 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1572 */     if (invokeActions != null) {
/* 1573 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1574 */       while (iterator.hasNext()) {
/* 1575 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1576 */         if (actionmap.containsKey(actionid)) {
/* 1577 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1582 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1586 */     String actionLink = "";
/* 1587 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1588 */     String query = "";
/* 1589 */     ResultSet rs = null;
/* 1590 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1591 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1592 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1593 */       actionLink = "method=" + methodName;
/*      */     }
/* 1595 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1596 */       actionLink = methodName;
/*      */     }
/* 1598 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1599 */     Iterator itr = methodarglist.iterator();
/* 1600 */     boolean isfirstparam = true;
/* 1601 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1602 */     while (itr.hasNext()) {
/* 1603 */       HashMap argmap = (HashMap)itr.next();
/* 1604 */       String argtype = (String)argmap.get("TYPE");
/* 1605 */       String argname = (String)argmap.get("IDENTITY");
/* 1606 */       String paramname = (String)argmap.get("PARAMETER");
/* 1607 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1608 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1609 */         isfirstparam = false;
/* 1610 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1612 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1616 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1620 */         actionLink = actionLink + "&";
/*      */       }
/* 1622 */       String paramValue = null;
/* 1623 */       String tempargname = argname;
/* 1624 */       if (commonValues.getProperty(tempargname) != null) {
/* 1625 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1628 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1629 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1630 */           if (dbType.equals("mysql")) {
/* 1631 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1634 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1636 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1638 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1639 */             if (rs.next()) {
/* 1640 */               paramValue = rs.getString("VALUE");
/* 1641 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1645 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1649 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1652 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1657 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1658 */           paramValue = rowId;
/*      */         }
/* 1660 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1661 */           paramValue = managedObjectName;
/*      */         }
/* 1663 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1664 */           paramValue = resID;
/*      */         }
/* 1666 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1667 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1670 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1672 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1673 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1674 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1676 */     return actionLink;
/*      */   }
/*      */   
/* 1679 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1680 */     String dependentAttribute = null;
/* 1681 */     String align = "left";
/*      */     
/* 1683 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1684 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1685 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1686 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1687 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1688 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1689 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1690 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1691 */       align = "center";
/*      */     }
/*      */     
/* 1694 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1695 */     String actualdata = "";
/*      */     
/* 1697 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1698 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1699 */         actualdata = availValue;
/*      */       }
/* 1701 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1702 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1706 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1707 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1710 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1716 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1717 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1718 */       toreturn.append("<table>");
/* 1719 */       toreturn.append("<tr>");
/* 1720 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1721 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1722 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1723 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1724 */         String toolTip = "";
/* 1725 */         String hideClass = "";
/* 1726 */         String textStyle = "";
/* 1727 */         boolean isreferenced = true;
/* 1728 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1729 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1730 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1731 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1733 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1734 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1735 */           while (valueList.hasMoreTokens()) {
/* 1736 */             String dependentVal = valueList.nextToken();
/* 1737 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1738 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1739 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1741 */               toolTip = "";
/* 1742 */               hideClass = "";
/* 1743 */               isreferenced = false;
/* 1744 */               textStyle = "disabledtext";
/* 1745 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1749 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1750 */           toolTip = "";
/* 1751 */           hideClass = "";
/* 1752 */           isreferenced = false;
/* 1753 */           textStyle = "disabledtext";
/* 1754 */           if (dependentImageMap != null) {
/* 1755 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1756 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1759 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1763 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1764 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1765 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1766 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1767 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1768 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1770 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1771 */           if (isreferenced) {
/* 1772 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1776 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1777 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1778 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1779 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1780 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1781 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1783 */           toreturn.append("</span>");
/* 1784 */           toreturn.append("</a>");
/* 1785 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1788 */       toreturn.append("</tr>");
/* 1789 */       toreturn.append("</table>");
/* 1790 */       toreturn.append("</td>");
/*      */     } else {
/* 1792 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1795 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1799 */     String colTime = null;
/* 1800 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1801 */     if ((rows != null) && (rows.size() > 0)) {
/* 1802 */       Iterator<String> itr = rows.iterator();
/* 1803 */       String maxColQuery = "";
/* 1804 */       for (;;) { if (itr.hasNext()) {
/* 1805 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1806 */           ResultSet maxCol = null;
/*      */           try {
/* 1808 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1809 */             while (maxCol.next()) {
/* 1810 */               if (colTime == null) {
/* 1811 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1814 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1823 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1825 */               if (maxCol != null)
/* 1826 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1828 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1823 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1825 */               if (maxCol != null)
/* 1826 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1828 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1833 */     return colTime;
/*      */   }
/*      */   
/* 1836 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1837 */     tablename = null;
/* 1838 */     ResultSet rsTable = null;
/* 1839 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1841 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1842 */       while (rsTable.next()) {
/* 1843 */         tablename = rsTable.getString("DATATABLE");
/* 1844 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1845 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1858 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1849 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1852 */         if (rsTable != null)
/* 1853 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1855 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1861 */     String argsList = "";
/* 1862 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1864 */       if (showArgsMap.get(row) != null) {
/* 1865 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1866 */         if (showArgslist != null) {
/* 1867 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1868 */             if (argsList.trim().equals("")) {
/* 1869 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1872 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1879 */       e.printStackTrace();
/* 1880 */       return "";
/*      */     }
/* 1882 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1887 */     String argsList = "";
/* 1888 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1891 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1893 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1894 */         if (hideArgsList != null)
/*      */         {
/* 1896 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1898 */             if (argsList.trim().equals(""))
/*      */             {
/* 1900 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1904 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1912 */       ex.printStackTrace();
/*      */     }
/* 1914 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1918 */     StringBuilder toreturn = new StringBuilder();
/* 1919 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1926 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1927 */       Iterator itr = tActionList.iterator();
/* 1928 */       while (itr.hasNext()) {
/* 1929 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1930 */         String confirmmsg = "";
/* 1931 */         String link = "";
/* 1932 */         String isJSP = "NO";
/* 1933 */         HashMap tactionMap = (HashMap)itr.next();
/* 1934 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1935 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1936 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1937 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1938 */           (actionmap.containsKey(actionId))) {
/* 1939 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1940 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1941 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1942 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1943 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1945 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1951 */           if (isTableAction) {
/* 1952 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1955 */             tableName = "Link";
/* 1956 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1957 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1958 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1959 */             toreturn.append("</a></td>");
/*      */           }
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1962 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1963 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1964 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1970 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1976 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1978 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1979 */       Properties prop = (Properties)node.getUserObject();
/* 1980 */       String mgID = prop.getProperty("label");
/* 1981 */       String mgName = prop.getProperty("value");
/* 1982 */       String isParent = prop.getProperty("isParent");
/* 1983 */       int mgIDint = Integer.parseInt(mgID);
/* 1984 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1986 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1988 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1989 */       if (node.getChildCount() > 0)
/*      */       {
/* 1991 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1993 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1995 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1997 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2001 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2006 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2008 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2010 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2012 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2016 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2019 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2020 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2022 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2026 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2028 */       if (node.getChildCount() > 0)
/*      */       {
/* 2030 */         builder.append("<UL>");
/* 2031 */         printMGTree(node, builder);
/* 2032 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2037 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2038 */     StringBuffer toReturn = new StringBuffer();
/* 2039 */     String table = "-";
/*      */     try {
/* 2041 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2042 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2043 */       float total = 0.0F;
/* 2044 */       while (it.hasNext()) {
/* 2045 */         String attName = (String)it.next();
/* 2046 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2047 */         boolean roundOffData = false;
/* 2048 */         if ((data != null) && (!data.equals(""))) {
/* 2049 */           if (data.indexOf(",") != -1) {
/* 2050 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2053 */             float value = Float.parseFloat(data);
/* 2054 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2057 */             total += value;
/* 2058 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2061 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2066 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2067 */       while (attVsWidthList.hasNext()) {
/* 2068 */         String attName = (String)attVsWidthList.next();
/* 2069 */         String data = (String)attVsWidthProps.get(attName);
/* 2070 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2071 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2072 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2073 */         String className = (String)graphDetails.get("ClassName");
/* 2074 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2075 */         if (percentage < 1.0F)
/*      */         {
/* 2077 */           data = percentage + "";
/*      */         }
/* 2079 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2081 */       if (toReturn.length() > 0) {
/* 2082 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2086 */       e.printStackTrace();
/*      */     }
/* 2088 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2094 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2095 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2096 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2097 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2098 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2099 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2100 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2101 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2102 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2105 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2106 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2107 */       splitvalues[0] = multiplecondition.toString();
/* 2108 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2111 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2116 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2117 */     if (thresholdType != 3) {
/* 2118 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2119 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2120 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2121 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2122 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2123 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2125 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2126 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2127 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2128 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2129 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2130 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2132 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2133 */     if (updateSelected != null) {
/* 2134 */       updateSelected[0] = "selected";
/*      */     }
/* 2136 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2141 */       StringBuffer toreturn = new StringBuffer("");
/* 2142 */       if (commaSeparatedMsgId != null) {
/* 2143 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2144 */         int count = 0;
/* 2145 */         while (msgids.hasMoreTokens()) {
/* 2146 */           String id = msgids.nextToken();
/* 2147 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2148 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2149 */           count++;
/* 2150 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2151 */             if (toreturn.length() == 0) {
/* 2152 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2154 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2155 */             if (!image.trim().equals("")) {
/* 2156 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2158 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2159 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2162 */         if (toreturn.length() > 0) {
/* 2163 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2167 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2170 */       e.printStackTrace(); }
/* 2171 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2177 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2183 */   private static Map<String, Long> _jspx_dependants = new HashMap(7);
/* 2184 */   static { _jspx_dependants.put("/jsp/jdkmemory.jsp", Long.valueOf(1473429417000L));
/* 2185 */     _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2186 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2187 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/* 2189 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2190 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2212 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2216 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2231 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2235 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.release();
/* 2237 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2238 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2242 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2243 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2244 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2245 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2247 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2248 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2255 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2258 */     JspWriter out = null;
/* 2259 */     Object page = this;
/* 2260 */     JspWriter _jspx_out = null;
/* 2261 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2265 */       response.setContentType("text/html;charset=UTF-8");
/* 2266 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2268 */       _jspx_page_context = pageContext;
/* 2269 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2270 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2271 */       session = pageContext.getSession();
/* 2272 */       out = pageContext.getOut();
/* 2273 */       _jspx_out = out;
/*      */       
/* 2275 */       out.write("<!--$Id$-->\n");
/*      */       
/* 2277 */       request.setAttribute("HelpKey", "Monitors JDK Details");
/*      */       
/* 2279 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2280 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2282 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2286 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2288 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2290 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2292 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2293 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2294 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2295 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2298 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2299 */         String available = null;
/* 2300 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2301 */         out.write(10);
/*      */         
/* 2303 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2307 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2309 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2311 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2313 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2314 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2315 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2316 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2319 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2320 */           String unavailable = null;
/* 2321 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2322 */           out.write(10);
/*      */           
/* 2324 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2328 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2330 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2332 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2334 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2335 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2336 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2337 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2340 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2341 */             String unmanaged = null;
/* 2342 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2343 */             out.write(10);
/*      */             
/* 2345 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2349 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2351 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2353 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2355 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2356 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2357 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2358 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2361 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2362 */               String scheduled = null;
/* 2363 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2364 */               out.write(10);
/*      */               
/* 2366 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2370 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2372 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2374 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2376 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2377 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2378 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2379 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2382 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2383 */                 String critical = null;
/* 2384 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2385 */                 out.write(10);
/*      */                 
/* 2387 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2391 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2393 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2395 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2397 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2398 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2399 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2400 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2403 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2404 */                   String clear = null;
/* 2405 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2406 */                   out.write(10);
/*      */                   
/* 2408 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2412 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2414 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2416 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2418 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2419 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2420 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2421 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2424 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2425 */                     String warning = null;
/* 2426 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2427 */                     out.write(10);
/* 2428 */                     out.write(10);
/*      */                     
/* 2430 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2431 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2433 */                     out.write(10);
/* 2434 */                     out.write(10);
/* 2435 */                     out.write(10);
/* 2436 */                     out.write(10);
/* 2437 */                     out.write(10);
/* 2438 */                     com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2439 */                     wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 2);
/* 2440 */                     if (wlsGraph == null) {
/* 2441 */                       wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2442 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 2);
/*      */                     }
/* 2444 */                     out.write(10);
/* 2445 */                     JDK15Graph jdk15Graph = null;
/* 2446 */                     jdk15Graph = (JDK15Graph)_jspx_page_context.getAttribute("jdk15Graph", 2);
/* 2447 */                     if (jdk15Graph == null) {
/* 2448 */                       jdk15Graph = new JDK15Graph();
/* 2449 */                       _jspx_page_context.setAttribute("jdk15Graph", jdk15Graph, 2);
/*      */                     }
/* 2451 */                     out.write(10);
/* 2452 */                     out.write(10);
/* 2453 */                     out.write(10);
/*      */                     
/* 2455 */                     String vendor = (String)request.getAttribute("vendor");
/* 2456 */                     boolean isMetaSpace = "true".equals((String)request.getAttribute("isMetaspace"));
/* 2457 */                     pageContext.setAttribute("isMetaSpace", Boolean.valueOf(isMetaSpace));
/* 2458 */                     ArrayList reportEnabledList = com.adventnet.appmanager.util.ReportUtil.getRepoEnabledAttribs();
/* 2459 */                     float downtime = 0.0F;
/* 2460 */                     HashMap osProps = (HashMap)request.getAttribute("osProp");
/*      */                     
/* 2462 */                     HashMap memoryProps = (HashMap)request.getAttribute("memoryProps");
/* 2463 */                     HashMap threadProps = (HashMap)request.getAttribute("threadProps");
/* 2464 */                     HashMap vmProps = (HashMap)request.getAttribute("vmProps");
/* 2465 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2466 */                     String resourceid = request.getParameter("resourceid");
/* 2467 */                     String encodeurl = URLEncoder.encode("/showresource.do?haid=" + request.getParameter("haid") + "&type=JDK1.5&method=showdetails&resourceid=" + resourceid + "&resourcename=" + request.getParameter("resourcename"));
/* 2468 */                     ArrayList attribIDs = new ArrayList();
/* 2469 */                     ArrayList resIDs = new ArrayList();
/* 2470 */                     resIDs.add(resourceid);
/* 2471 */                     for (int i = 3600; i < 3638; i++)
/*      */                     {
/* 2473 */                       attribIDs.add("" + i);
/*      */                     }
/*      */                     
/* 2476 */                     for (int i = 3659; i < 3666; i++)
/*      */                     {
/* 2478 */                       attribIDs.add("" + i);
/*      */                     }
/*      */                     
/*      */ 
/* 2482 */                     for (int i = 3668; i < 3693; i++)
/*      */                     {
/* 2484 */                       attribIDs.add("" + i);
/*      */                     }
/*      */                     
/*      */ 
/* 2488 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2489 */                     request.setAttribute("isfromresourcepage", "true");
/*      */                     
/* 2491 */                     String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2492 */                     String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/* 2493 */                     String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 2494 */                     String yaxis_resptime = FormatUtil.getString("am.webclient.jdk15.responsetime.text");
/* 2495 */                     String yaxis_memory = FormatUtil.getString("am.webclient.jdk15.graph.memory.text");
/* 2496 */                     String yaxis_threads = FormatUtil.getString("am.webclient.jdk15.graph.threads.text");
/* 2497 */                     String yaxis_memory1 = FormatUtil.getString("am.webclient.jdk15.memory.text") + " (" + FormatUtil.getString("MB") + ")";
/*      */                     
/* 2499 */                     wlsGraph.setParam(resourceid, "AVAILABILITY");
/*      */                     
/* 2501 */                     out.write("\n\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\">\n    <tr>\n          <td width=\"58%\" valign=\"top\">\n\t\t<table width=\"97%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n          <tr>\n            <td colspan=\"2\" class=\"tableheadingbborder\" >");
/* 2502 */                     out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2503 */                     out.write("</td>\n          </tr>\n          <tr>\n            <td width=\"35%\" class=\"monitorinfoodd\" >");
/* 2504 */                     out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2505 */                     out.write("</td>\n\t    <td width=\"65%\" class=\"monitorinfoodd\">");
/*      */                     
/* 2507 */                     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 2508 */                     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 2509 */                     _jspx_th_am_005fTruncate_005f0.setParent(null);
/*      */                     
/* 2511 */                     _jspx_th_am_005fTruncate_005f0.setTooltip("true");
/*      */                     
/* 2513 */                     _jspx_th_am_005fTruncate_005f0.setLength(35);
/* 2514 */                     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 2515 */                     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 2516 */                       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 2517 */                         out = _jspx_page_context.pushBody();
/* 2518 */                         _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 2519 */                         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/* 2522 */                         out.print(request.getAttribute("monitorname"));
/* 2523 */                         out.write(32);
/* 2524 */                         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 2525 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2528 */                       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 2529 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2532 */                     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 2533 */                       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/*      */                     }
/*      */                     else {
/* 2536 */                       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 2537 */                       out.write("</td>\n          </tr>\n\t  <tr>\n\t  <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2538 */                       out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2539 */                       out.write("</td>\n\t  <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2540 */                       out.print(resourceid);
/* 2541 */                       out.write("&attributeid=3601')\">");
/* 2542 */                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "3601")));
/* 2543 */                       out.write("</a>\n\t  ");
/* 2544 */                       out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "3601" + "#" + "MESSAGE"), "3601", alert.getProperty(resourceid + "#" + "3601"), resourceid));
/* 2545 */                       out.write("\n\t  ");
/* 2546 */                       if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "3601") != 0) {
/* 2547 */                         out.write("\n\t  <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2548 */                         out.print(resourceid + "_3601");
/* 2549 */                         out.write("&monitortype=JDK1.5')\">");
/* 2550 */                         out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2551 */                         out.write("</a></span>\n      ");
/*      */                       }
/* 2553 */                       out.write("\n\t  </td>\n\t  </tr>\n\t  <tr>\n\t  <td class=\"monitorinfoodd\">");
/* 2554 */                       out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2555 */                       out.write("</td>\n\t  <td class=\"monitorinfoodd\">");
/* 2556 */                       out.print(FormatUtil.getString("am.webclient.jdk15.servertype"));
/* 2557 */                       out.write("</td>\n\t  </tr>\n  ");
/*      */                       
/* 2559 */                       EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2560 */                       _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2561 */                       _jspx_th_logic_005fempty_005f0.setParent(null);
/*      */                       
/* 2563 */                       _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2564 */                       int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2565 */                       if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                         for (;;) {
/* 2567 */                           out.write("\n  <tr>\n  <td class=\"monitorinfoodd\">");
/* 2568 */                           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2569 */                           out.write("</td>\n  <td class=\"monitorinfoodd\">-&nbsp;</td>\n  </tr>\n  <tr>\n  <td class=\"monitorinfoodd\">");
/* 2570 */                           out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 2571 */                           out.write("</td>\n  <td class=\"monitorinfoodd\">-</td>\n  </tr>\n  ");
/* 2572 */                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2573 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2577 */                       if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2578 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/*      */                       }
/*      */                       else {
/* 2581 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2582 */                         out.write(10);
/*      */                         
/* 2584 */                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2585 */                         _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2586 */                         _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                         
/* 2588 */                         _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 2589 */                         int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2590 */                         if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                           for (;;) {
/* 2592 */                             out.write("\n<tr>\n<td class=\"monitorinfoodd\">");
/* 2593 */                             out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2594 */                             out.write("</td>\n");
/*      */                             
/* 2596 */                             if (systeminfo.get("host_resid") != null)
/*      */                             {
/* 2598 */                               out.write("\n\t\t    <td class=\"monitorinfoodd\"><a href=\"showresource.do?resourceid=");
/* 2599 */                               out.print(systeminfo.get("host_resid"));
/* 2600 */                               out.write("&method=showResourceForResourceID\" class=\"staticlinks\" title=\"");
/* 2601 */                               out.print(systeminfo.get("HOSTNAME"));
/* 2602 */                               out.write(34);
/* 2603 */                               out.write(32);
/* 2604 */                               out.write(62);
/* 2605 */                               out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2606 */                               out.write("&nbsp;(");
/* 2607 */                               out.print(systeminfo.get("HOSTIP"));
/* 2608 */                               out.write(")</a></td>\n\t\t\t");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 2613 */                               out.write("\n             <td class=\"monitorinfoodd\" title=\"");
/* 2614 */                               out.print(systeminfo.get("HOSTNAME"));
/* 2615 */                               out.write(34);
/* 2616 */                               out.write(32);
/* 2617 */                               out.write(62);
/* 2618 */                               out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2619 */                               out.write("&nbsp;(");
/* 2620 */                               out.print(systeminfo.get("HOSTIP"));
/* 2621 */                               out.write(")</td>\n\t\t\t");
/*      */                             }
/* 2623 */                             out.write("\n</tr>\n<tr>\n<td class=\"monitorinfoodd\">");
/* 2624 */                             out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 2625 */                             out.write("</td>\n<td class=\"monitorinfoodd\">");
/* 2626 */                             out.print(systeminfo.get("PORTNO"));
/* 2627 */                             out.write(" </td>\n</tr>\n\t");
/* 2628 */                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2629 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2633 */                         if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2634 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                         }
/*      */                         else {
/* 2637 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2638 */                           out.write("\n\n<tr>\n<td class=\"monitorinfoodd\">");
/* 2639 */                           out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2640 */                           out.write("</td>\n\n");
/*      */                           
/* 2642 */                           if (osProps.get("Operating System") != null)
/*      */                           {
/* 2644 */                             out.write("\n<td class=\"monitorinfoodd\">");
/* 2645 */                             out.print(osProps.get("Operating System"));
/* 2646 */                             out.write(" </td>\n");
/*      */                           } else {
/* 2648 */                             out.write("\n<td class=\"monitorinfoodd\">-</td>\n");
/*      */                           }
/* 2650 */                           out.write("\n\n</tr>\n<tr>\n<td class=\"monitorinfoodd\">");
/* 2651 */                           out.print(FormatUtil.getString("am.webclient.jdk15.jvm.text"));
/* 2652 */                           out.write("</td>\n\n");
/*      */                           
/* 2654 */                           if (osProps.get("Java Virtual Machine") != null)
/*      */                           {
/* 2656 */                             out.write("\n<td class=\"monitorinfoodd\">");
/* 2657 */                             out.print(osProps.get("Java Virtual Machine"));
/* 2658 */                             out.write(" </td>\n");
/*      */                           } else {
/* 2660 */                             out.write("\n<td class=\"monitorinfoodd\">-</td>\n");
/*      */                           }
/* 2662 */                           out.write("\n\n\n</tr>\n<tr>\n<td class=\"monitorinfoodd\">");
/* 2663 */                           out.print(FormatUtil.getString("am.webclient.jdk15.vendor.text"));
/* 2664 */                           out.write("</td>\n");
/*      */                           
/* 2666 */                           if (osProps.get("Vendor") != null)
/*      */                           {
/* 2668 */                             out.write("\n<td class=\"monitorinfoodd\">");
/* 2669 */                             out.print(osProps.get("Vendor"));
/* 2670 */                             out.write("</td>\n");
/*      */                           } else {
/* 2672 */                             out.write("\n<td class=\"monitorinfoodd\">-</td>\n");
/*      */                           }
/* 2674 */                           out.write("\n\n</tr>\n<tr>\n<td class=\"monitorinfoodd\">");
/* 2675 */                           out.print(FormatUtil.getString("am.webclient.jdk15.processorcount.text"));
/* 2676 */                           out.write("</td>\n");
/*      */                           
/* 2678 */                           if (osProps.get("Processors") != null)
/*      */                           {
/* 2680 */                             out.write("\n<td class=\"monitorinfoodd\">");
/* 2681 */                             out.print(osProps.get("Processors"));
/* 2682 */                             out.write("</td>\n");
/*      */                           } else {
/* 2684 */                             out.write("\n<td class=\"monitorinfoodd\">-</td>\n");
/*      */                           }
/* 2686 */                           out.write("\n\n</tr>\n\n\n\n");
/*      */                           
/* 2688 */                           EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2689 */                           _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 2690 */                           _jspx_th_logic_005fempty_005f1.setParent(null);
/*      */                           
/* 2692 */                           _jspx_th_logic_005fempty_005f1.setName("systeminfo");
/* 2693 */                           int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 2694 */                           if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                             for (;;) {
/* 2696 */                               out.write("\n<tr>\n<td class=\"monitorinfoeven\">");
/* 2697 */                               out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2698 */                               out.write("</td>\n<td class=\"monitorinfoeven\">-</td>\n</tr>\n<tr>\n<td class=\"monitorinfoodd\">");
/* 2699 */                               out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2700 */                               out.write("</td>\n<td class=\"monitorinfoodd\">-</td>\n</tr>\n");
/* 2701 */                               int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 2702 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2706 */                           if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 2707 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/*      */                           }
/*      */                           else {
/* 2710 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 2711 */                             out.write(10);
/*      */                             
/* 2713 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2714 */                             _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2715 */                             _jspx_th_logic_005fnotEmpty_005f1.setParent(null);
/*      */                             
/* 2717 */                             _jspx_th_logic_005fnotEmpty_005f1.setName("systeminfo");
/* 2718 */                             int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2719 */                             if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                               for (;;) {
/* 2721 */                                 out.write("\n<tr>\n<td class=\"monitorinfoeven\">");
/* 2722 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2723 */                                 out.write("</td>\n<td class=\"monitorinfoeven\">");
/* 2724 */                                 out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 2725 */                                 out.write("</td>\n</tr>\n<tr>\n<td class=\"monitorinfoodd\">");
/* 2726 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2727 */                                 out.write("</td>\n<td class=\"monitorinfoodd\">");
/* 2728 */                                 out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 2729 */                                 out.write("\n</td>\n</tr>\n");
/* 2730 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2731 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2735 */                             if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2736 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                             }
/*      */                             else {
/* 2739 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2740 */                               out.write("\n<tr>\n<td colspan=\"2\" align=\"right\" class=\"monitorinfoodd\"><b><a class=\"staticlinks\" href=\"javascript:MM_openBrWindow('/JavaRuntime.do?method=getThreadInfo&resourceid=");
/* 2741 */                               out.print(resourceid);
/* 2742 */                               out.write("','ThreadInfo','scrollbars=yes,resizable=yes')\">");
/* 2743 */                               out.print(FormatUtil.getString("am.webclient.jdk15.threadinfo.text"));
/* 2744 */                               out.write("</a></b></td>\n</tr>\n ");
/* 2745 */                               out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 2746 */                               out.write("\n\t{\n\t\t");
/* 2747 */                               if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                                 return;
/* 2749 */                               out.write(10);
/* 2750 */                               out.write(9);
/* 2751 */                               out.write(9);
/* 2752 */                               if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */                                 return;
/* 2754 */                               out.write("\n\t\tgetCustomFields('");
/* 2755 */                               if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */                                 return;
/* 2757 */                               out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 2758 */                               out.write("\n\t}\n\n});\n</script>\n");
/* 2759 */                               if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */                                 return;
/* 2761 */                               out.write(10);
/* 2762 */                               out.write(10);
/* 2763 */                               if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */                                 return;
/* 2765 */                               out.write(10);
/* 2766 */                               out.write(10);
/* 2767 */                               if (_jspx_meth_c_005fset_005f4(_jspx_page_context))
/*      */                                 return;
/* 2769 */                               out.write(10);
/* 2770 */                               if (_jspx_meth_c_005fset_005f5(_jspx_page_context))
/*      */                                 return;
/* 2772 */                               out.write(10);
/* 2773 */                               out.write(10);
/* 2774 */                               out.write(10);
/* 2775 */                               if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*      */                                 return;
/* 2777 */                               out.write(10);
/* 2778 */                               out.write(10);
/* 2779 */                               out.write(10);
/* 2780 */                               if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */                                 return;
/* 2782 */                               out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 2783 */                               if (_jspx_meth_c_005fout_005f9(_jspx_page_context))
/*      */                                 return;
/* 2785 */                               out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 2786 */                               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                                 return;
/* 2788 */                               out.write("\" onclick=\"getCustomFields('");
/* 2789 */                               if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*      */                                 return;
/* 2791 */                               out.write(39);
/* 2792 */                               out.write(44);
/* 2793 */                               out.write(39);
/* 2794 */                               if (_jspx_meth_c_005fout_005f11(_jspx_page_context))
/*      */                                 return;
/* 2796 */                               out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 2797 */                               out.write("\n</td>\n</tr>\n\n\n");
/* 2798 */                               out.write("\n  </table>\n\t  </td>\n\t  <td width=\"42%\" valign=\"top\">\n\t  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n          <tbody>\n            <tr>\n              <td height=\"36\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 2799 */                               out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 2800 */                               out.write("</td>\n            </tr>\n            <tr>\n              <td colspan=\"3\"> <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n                  <tr>\n                    <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2801 */                               if (_jspx_meth_c_005fout_005f12(_jspx_page_context))
/*      */                                 return;
/* 2803 */                               out.write("&period=1&resourcename=");
/* 2804 */                               if (_jspx_meth_c_005fout_005f13(_jspx_page_context))
/*      */                                 return;
/* 2806 */                               out.write("')\">\n                      <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2807 */                               out.print(seven_days_text);
/* 2808 */                               out.write("\"></a></td>\n                    <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2809 */                               if (_jspx_meth_c_005fout_005f14(_jspx_page_context))
/*      */                                 return;
/* 2811 */                               out.write("&period=2&resourcename=");
/* 2812 */                               if (_jspx_meth_c_005fout_005f15(_jspx_page_context))
/*      */                                 return;
/* 2814 */                               out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2815 */                               out.print(thiry_days_text);
/* 2816 */                               out.write("\"></a></td>\n                  </tr>\n                </table></td>\n            </tr>\n            <tr align=\"center\">\n              <td height=\"36\" colspan=\"3\" class=\"whitegrayborder\">");
/*      */                               
/* 2818 */                               AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 2819 */                               _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 2820 */                               _jspx_th_awolf_005fpiechart_005f0.setParent(null);
/*      */                               
/* 2822 */                               _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                               
/* 2824 */                               _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */                               
/* 2826 */                               _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                               
/* 2828 */                               _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                               
/* 2830 */                               _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                               
/* 2832 */                               _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                               
/* 2834 */                               _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 2835 */                               int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 2836 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 2837 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2838 */                                   out = _jspx_page_context.pushBody();
/* 2839 */                                   _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 2840 */                                   _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2843 */                                   out.write("\n                ");
/*      */                                   
/* 2845 */                                   Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 2846 */                                   _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 2847 */                                   _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                   
/* 2849 */                                   _jspx_th_awolf_005fmap_005f0.setId("color");
/* 2850 */                                   int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 2851 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 2852 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2853 */                                       out = _jspx_page_context.pushBody();
/* 2854 */                                       _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 2855 */                                       _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2858 */                                       out.write(32);
/*      */                                       
/* 2860 */                                       AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2861 */                                       _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 2862 */                                       _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 2864 */                                       _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                       
/* 2866 */                                       _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 2867 */                                       int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 2868 */                                       if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 2869 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                       }
/*      */                                       
/* 2872 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 2873 */                                       out.write("\n                ");
/*      */                                       
/* 2875 */                                       AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2876 */                                       _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 2877 */                                       _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 2879 */                                       _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                       
/* 2881 */                                       _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 2882 */                                       int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 2883 */                                       if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 2884 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                       }
/*      */                                       
/* 2887 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 2888 */                                       out.write(32);
/* 2889 */                                       int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 2890 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2893 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2894 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2897 */                                   if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 2898 */                                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                   }
/*      */                                   
/* 2901 */                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 2902 */                                   out.write("\n                ");
/* 2903 */                                   int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 2904 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2907 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2908 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2911 */                               if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 2912 */                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/*      */                               }
/*      */                               else {
/* 2915 */                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 2916 */                                 out.write("</td>\n            </tr>\n\t\t\t<tr>\n\t\t\t<td width=\"48%\" height=\"36\" class=\"yellowgrayborder\"><span class=\"bodytext\">");
/* 2917 */                                 out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 2918 */                                 out.write(" :</span>\n\t\t\t<a style=\"position:relative; top:2px;\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2919 */                                 out.print(resourceid);
/* 2920 */                                 out.write("&attributeid=3600&alertconfigurl=");
/* 2921 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=3600&attributeToSelect=3600&redirectto=" + encodeurl));
/* 2922 */                                 out.write("')\">\n\t\t\t");
/* 2923 */                                 out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "3600")));
/* 2924 */                                 out.write("</a>\n\t\t\t</td>\n\t\t\t<td width=\"51%\" align=\"right\" class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" border=\"0\" align=\"absmiddle\">&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2925 */                                 if (_jspx_meth_c_005fout_005f16(_jspx_page_context))
/*      */                                   return;
/* 2927 */                                 out.write("&attributeIDs=3600,3601&attributeToSelect=3600&redirectto=");
/* 2928 */                                 out.print(encodeurl);
/* 2929 */                                 out.write("' class=\"links\">");
/* 2930 */                                 out.print(ALERTCONFIG_TEXT);
/* 2931 */                                 out.write("</a>&nbsp;</td>\n\t\t\t</tr>\n          </tbody>\n        </table>\n\t  </td>\n\t</tr>\n</table>\n <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 2932 */                                 out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 2933 */                                 out.write("</td></tr></table>\n<br>\n<table width=\"99%\" height=\"299\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" height=\"31\" class=\"tableheadingbborder\">");
/* 2934 */                                 out.print(FormatUtil.getString("am.webclient.jdk15.responsetime.text"));
/* 2935 */                                 out.write("\n</td>\n</tr>\n<tr>\n");
/*      */                                 
/* 2937 */                                 jdk15Graph.setParameter(resourceid, "responsetime");
/*      */                                 
/* 2939 */                                 out.write("\n<td width=\"100%\" height=\"64\" colspan=\"2\" >\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n          <tr>\n\t              <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2940 */                                 if (_jspx_meth_c_005fout_005f17(_jspx_page_context))
/*      */                                   return;
/* 2942 */                                 out.write("&attributeid=3602&period=-7&resourcename=");
/* 2943 */                                 if (_jspx_meth_c_005fout_005f18(_jspx_page_context))
/*      */                                   return;
/* 2945 */                                 out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2946 */                                 out.print(seven_days_text);
/* 2947 */                                 out.write("\"></a></td>\n\t\t                  <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2948 */                                 if (_jspx_meth_c_005fout_005f19(_jspx_page_context))
/*      */                                   return;
/* 2950 */                                 out.write("&attributeid=3602&period=-30&resourcename=");
/* 2951 */                                 if (_jspx_meth_c_005fout_005f20(_jspx_page_context))
/*      */                                   return;
/* 2953 */                                 out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2954 */                                 out.print(thiry_days_text);
/* 2955 */                                 out.write("\"></a></td>\n\t</tr>\n\t<tr align=\"center\">\n\t<td colspan=\"2\"> ");
/*      */                                 
/* 2957 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 2958 */                                 _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 2959 */                                 _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */                                 
/* 2961 */                                 _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("jdk15Graph");
/*      */                                 
/* 2963 */                                 _jspx_th_awolf_005ftimechart_005f0.setWidth("550");
/*      */                                 
/* 2965 */                                 _jspx_th_awolf_005ftimechart_005f0.setHeight("175");
/*      */                                 
/* 2967 */                                 _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                                 
/* 2969 */                                 _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                                 
/* 2971 */                                 _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_resptime);
/* 2972 */                                 int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 2973 */                                 if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 2974 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2975 */                                     out = _jspx_page_context.pushBody();
/* 2976 */                                     _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 2977 */                                     _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 2980 */                                     out.write(10);
/* 2981 */                                     out.write(9);
/* 2982 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 2983 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2986 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2987 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2990 */                                 if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 2991 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*      */                                 }
/*      */                                 else {
/* 2994 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 2995 */                                   out.write(" </td>\n\t</tr>\n\t</table>\n</td>\n</tr>\n<tr>\n\t<td align=\"center\">\n\t<br>\n\t<table class=\"grayfullborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"70%\">\n\t<tr>\n\t<td class=\"columnheadingb\">");
/* 2996 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 2997 */                                   out.write("</td>\n\t<td class=\"columnheadingb\" align=\"center\">");
/* 2998 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 2999 */                                   out.write("&nbsp;(");
/* 3000 */                                   out.print(FormatUtil.getString("ms"));
/* 3001 */                                   out.write(")</td>\n\t<td class=\"columnheadingb\" align=\"center\">");
/* 3002 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 3003 */                                   out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 3004 */                                   out.print(FormatUtil.getString("am.webclient.jdk15.responsetime.text"));
/* 3005 */                                   out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"center\">");
/* 3006 */                                   out.print(vmProps.get("responsetime"));
/* 3007 */                                   out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3008 */                                   out.print(resourceid);
/* 3009 */                                   out.write("&attributeid=3602')\">");
/* 3010 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3602")));
/* 3011 */                                   out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td colspan=\"3\" align=\"right\"><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3012 */                                   out.print(resourceid);
/* 3013 */                                   out.write("&attributeIDs=3602&attributeToSelect=3602&redirectto=");
/* 3014 */                                   out.print(encodeurl);
/* 3015 */                                   out.write("\" class=\"staticlinks\">");
/* 3016 */                                   out.print(ALERTCONFIG_TEXT);
/* 3017 */                                   out.write("</a>&nbsp;</td>\n\t</tr>\n\t</table>\n\t<br>\n</td>\n</tr>\n</table>\n\n<br>\n\n<!--Memory and CPU graph starts here-->\n<table width=\"99%\" height=\"299\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n   <td height=\"31\" class=\"tableheadingbborder\">");
/* 3018 */                                   out.print(FormatUtil.getString("am.webclient.jdk15.memory.text"));
/* 3019 */                                   out.write("</td>\n   \n");
/* 3020 */                                   if ((!vendor.contains("BEA")) && (!vendor.contains("Oracle"))) {
/* 3021 */                                     out.write("  \n     <td height=\"31\" class=\"tableheadingbborder\" >");
/* 3022 */                                     out.print(FormatUtil.getString("am.webclient.jdk15.cpu.text"));
/* 3023 */                                     out.write(" </td>\n");
/*      */                                   } else {
/* 3025 */                                     out.write("     \n     <td height=\"31\" class=\"tableheadingbborder\" >");
/* 3026 */                                     out.print(FormatUtil.getString("am.webclient.jre.cpuload.text"));
/* 3027 */                                     out.write(" </td>\n");
/*      */                                   }
/* 3029 */                                   out.write("     \n  </tr>\n  <tr>\n    <td width=\"50%\" valign=\"top\" class=\"rborder\">\n      ");
/*      */                                   
/* 3031 */                                   jdk15Graph.setParameter(resourceid, "processmemory");
/*      */                                   
/* 3033 */                                   out.write("\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n        <tr>\n          <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3034 */                                   if (_jspx_meth_c_005fout_005f21(_jspx_page_context))
/*      */                                     return;
/* 3036 */                                   out.write("&attributeid=3608&period=-7&resourcename=");
/* 3037 */                                   if (_jspx_meth_c_005fout_005f22(_jspx_page_context))
/*      */                                     return;
/* 3039 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3040 */                                   out.print(seven_days_text);
/* 3041 */                                   out.write("\"></a></td>\n          <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3042 */                                   if (_jspx_meth_c_005fout_005f23(_jspx_page_context))
/*      */                                     return;
/* 3044 */                                   out.write("&attributeid=3608&period=-30&resourcename=");
/* 3045 */                                   if (_jspx_meth_c_005fout_005f24(_jspx_page_context))
/*      */                                     return;
/* 3047 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3048 */                                   out.print(thiry_days_text);
/* 3049 */                                   out.write("\"></a></td>\n        </tr>\n        <tr align=\"center\">\n          <td colspan=\"2\"> ");
/*      */                                   
/* 3051 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3052 */                                   _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3053 */                                   _jspx_th_awolf_005ftimechart_005f1.setParent(null);
/*      */                                   
/* 3055 */                                   _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("jdk15Graph");
/*      */                                   
/* 3057 */                                   _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                                   
/* 3059 */                                   _jspx_th_awolf_005ftimechart_005f1.setHeight("175");
/*      */                                   
/* 3061 */                                   _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                                   
/* 3063 */                                   _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                                   
/* 3065 */                                   _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(yaxis_memory1);
/* 3066 */                                   int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3067 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3068 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3069 */                                       out = _jspx_page_context.pushBody();
/* 3070 */                                       _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3071 */                                       _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3074 */                                       out.write("\n            ");
/* 3075 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3076 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3079 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3080 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3083 */                                   if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3084 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/*      */                                   }
/*      */                                   else {
/* 3087 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3088 */                                     out.write(" </td>\n        </tr>\n        <tr>\n          <td align=\"center\" colspan=\"2\"> <br> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n              <tr>\n                <td class=\"columnheadingtb\">");
/* 3089 */                                     out.print(FormatUtil.getString("table.heading.attribute"));
/* 3090 */                                     out.write("</td>\n                <td class=\"columnheadingtb\">");
/* 3091 */                                     out.print(FormatUtil.getString("am.javaruntime.used"));
/* 3092 */                                     out.write("&nbsp;(");
/* 3093 */                                     out.print(FormatUtil.getString("MB"));
/* 3094 */                                     out.write(")</td>\n                <td class=\"columnheadingtb\">");
/* 3095 */                                     out.print(FormatUtil.getString("table.heading.status"));
/* 3096 */                                     out.write("</td>\n                <td class=\"columnheadingtb\" align=\"center\">");
/* 3097 */                                     out.print(FormatUtil.getString("am.javaruntime.used"));
/* 3098 */                                     out.write("&nbsp;(&#37;)</td>\n                <td class=\"columnheadingtb\">");
/* 3099 */                                     out.print(FormatUtil.getString("table.heading.status"));
/* 3100 */                                     out.write("</td>\n              </tr>\n              <tr>\n                <td class=\"whitegrayborder\">");
/* 3101 */                                     out.print(FormatUtil.getString("am.webclient.jdk15.memory.text"));
/* 3102 */                                     out.write("</td>\n                <td class=\"whitegrayborder\" align=\"center\">");
/* 3103 */                                     out.print(memoryProps.get("processmemory"));
/* 3104 */                                     out.write("</td>\n                <td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3105 */                                     out.print(resourceid);
/* 3106 */                                     out.write("&attributeid=3608')\">");
/* 3107 */                                     out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3608")));
/* 3108 */                                     out.write("</a></td>\n                <td class=\"whitegrayborder\" align=\"center\">");
/* 3109 */                                     out.print(memoryProps.get("processmemoryper"));
/* 3110 */                                     out.write("</td>\n                <td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3111 */                                     out.print(resourceid);
/* 3112 */                                     out.write("&attributeid=3636')\">");
/* 3113 */                                     out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3636")));
/* 3114 */                                     out.write("</a></td>\n              </tr>\n              <tr>\n                <td colspan=\"5\" align=\"right\" ><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3115 */                                     out.print(resourceid);
/* 3116 */                                     out.write("&attributeIDs=3608,3636&attributeToSelect=3608&redirectto=");
/* 3117 */                                     out.print(encodeurl);
/* 3118 */                                     out.write("\" class=\"staticlinks\">");
/* 3119 */                                     out.print(ALERTCONFIG_TEXT);
/* 3120 */                                     out.write("</a>&nbsp;</td>\n              </tr>\n            </table></td>\n        </tr>\n      </table>\n    </td>\n    <td width=\"50%\" valign=\"top\">\n    \n    \n    \n");
/*      */                                     
/* 3122 */                                     if ((!vendor.contains("BEA")) && (!vendor.contains("Oracle"))) {
/* 3123 */                                       jdk15Graph.setParameter(resourceid, "processcpu");
/*      */                                       
/* 3125 */                                       out.write("\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n        <tr>\n          <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3126 */                                       if (_jspx_meth_c_005fout_005f25(_jspx_page_context))
/*      */                                         return;
/* 3128 */                                       out.write("&attributeid=3637&period=-7&resourcename=");
/* 3129 */                                       if (_jspx_meth_c_005fout_005f26(_jspx_page_context))
/*      */                                         return;
/* 3131 */                                       out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3132 */                                       out.print(seven_days_text);
/* 3133 */                                       out.write("\"></a></td>\n          <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3134 */                                       if (_jspx_meth_c_005fout_005f27(_jspx_page_context))
/*      */                                         return;
/* 3136 */                                       out.write("&attributeid=3637&period=-30&resourcename=");
/* 3137 */                                       if (_jspx_meth_c_005fout_005f28(_jspx_page_context))
/*      */                                         return;
/* 3139 */                                       out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3140 */                                       out.print(thiry_days_text);
/* 3141 */                                       out.write("\"></a></td>\n        </tr>\n        <tr align=\"center\">\n          <td colspan=\"2\"> ");
/*      */                                       
/* 3143 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3144 */                                       _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3145 */                                       _jspx_th_awolf_005ftimechart_005f2.setParent(null);
/*      */                                       
/* 3147 */                                       _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("jdk15Graph");
/*      */                                       
/* 3149 */                                       _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */                                       
/* 3151 */                                       _jspx_th_awolf_005ftimechart_005f2.setHeight("175");
/*      */                                       
/* 3153 */                                       _jspx_th_awolf_005ftimechart_005f2.setLegend("false");
/*      */                                       
/* 3155 */                                       _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(xaxis_time);
/*      */                                       
/* 3157 */                                       _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.jdk15.cpu.text"));
/* 3158 */                                       int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3159 */                                       if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3160 */                                         if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3161 */                                           out = _jspx_page_context.pushBody();
/* 3162 */                                           _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3163 */                                           _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3166 */                                           out.write("\n            ");
/* 3167 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3168 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3171 */                                         if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3172 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3175 */                                       if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3176 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                                       }
/*      */                                       
/* 3179 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3180 */                                       out.write(" </td>\n        </tr>\n        <tr>\n          <td align=\"center\" colspan=\"2\"> <br> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n              <tr>\n                <td class=\"columnheadingtb\">");
/* 3181 */                                       out.print(FormatUtil.getString("table.heading.attribute"));
/* 3182 */                                       out.write("</td>\n                <td class=\"columnheadingtb\" align=\"center\">");
/* 3183 */                                       out.print(FormatUtil.getString("am.javaruntime.used"));
/* 3184 */                                       out.write("(&#37;)</td>\n                <td class=\"columnheadingtb\">");
/* 3185 */                                       out.print(FormatUtil.getString("table.heading.status"));
/* 3186 */                                       out.write("</td>\n              </tr>\n              <tr>\n                <td class=\"whitegrayborder\">");
/* 3187 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.cpu.text"));
/* 3188 */                                       out.write("</td>\n                <td class=\"whitegrayborder\" align=\"center\">");
/* 3189 */                                       out.print(memoryProps.get("cpuper"));
/* 3190 */                                       out.write("</td>\n                <td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3191 */                                       out.print(resourceid);
/* 3192 */                                       out.write("&attributeid=3637')\">");
/* 3193 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3637")));
/* 3194 */                                       out.write("</a></td>\n              </tr>\n              <tr>\n                <td colspan=\"3\" align=\"right\" ><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3195 */                                       out.print(resourceid);
/* 3196 */                                       out.write("&attributeIDs=3637&attributeToSelect=3637&redirectto=");
/* 3197 */                                       out.print(encodeurl);
/* 3198 */                                       out.write("\" class=\"staticlinks\">");
/* 3199 */                                       out.print(ALERTCONFIG_TEXT);
/* 3200 */                                       out.write("</a>&nbsp;</td>\n              </tr>\n            </table></td>\n        </tr>\n      </table>\n      \n");
/*      */                                     }
/*      */                                     else {
/* 3203 */                                       jdk15Graph.setParameter(resourceid, "cpuload");
/*      */                                       
/* 3205 */                                       out.write("\n\n\n\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n        <tr>\n");
/* 3206 */                                       if (reportEnabledList.contains("3682")) {
/* 3207 */                                         out.write("\n\n          <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3208 */                                         if (_jspx_meth_c_005fout_005f29(_jspx_page_context))
/*      */                                           return;
/* 3210 */                                         out.write("&attributeid=3682&period=-7&resourcename=");
/* 3211 */                                         if (_jspx_meth_c_005fout_005f30(_jspx_page_context))
/*      */                                           return;
/* 3213 */                                         out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3214 */                                         out.print(seven_days_text);
/* 3215 */                                         out.write("\"></a></td>\n          <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3216 */                                         if (_jspx_meth_c_005fout_005f31(_jspx_page_context))
/*      */                                           return;
/* 3218 */                                         out.write("&attributeid=3682&period=-30&resourcename=");
/* 3219 */                                         if (_jspx_meth_c_005fout_005f32(_jspx_page_context))
/*      */                                           return;
/* 3221 */                                         out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3222 */                                         out.print(thiry_days_text);
/* 3223 */                                         out.write("\"></a></td>\n");
/*      */                                       } else {
/* 3225 */                                         out.write("\n\t\t  <td width=\"5%\"  align=\"right\">&nbsp;</td>\n\t\t  <td width=\"5%\"  align=\"right\">&nbsp;</td>\n");
/*      */                                       }
/* 3227 */                                       out.write("  \n    </tr>    \n        \n        <tr align=\"center\">\n          <td colspan=\"2\"> ");
/*      */                                       
/* 3229 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3230 */                                       _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3231 */                                       _jspx_th_awolf_005ftimechart_005f3.setParent(null);
/*      */                                       
/* 3233 */                                       _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("jdk15Graph");
/*      */                                       
/* 3235 */                                       _jspx_th_awolf_005ftimechart_005f3.setWidth("300");
/*      */                                       
/* 3237 */                                       _jspx_th_awolf_005ftimechart_005f3.setHeight("175");
/*      */                                       
/* 3239 */                                       _jspx_th_awolf_005ftimechart_005f3.setLegend("false");
/*      */                                       
/* 3241 */                                       _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(xaxis_time);
/*      */                                       
/* 3243 */                                       _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.jre.cpuload.text"));
/* 3244 */                                       int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3245 */                                       if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 3246 */                                         if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3247 */                                           out = _jspx_page_context.pushBody();
/* 3248 */                                           _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 3249 */                                           _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3252 */                                           out.write("\n            ");
/* 3253 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 3254 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3257 */                                         if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3258 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3261 */                                       if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3262 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                                       }
/*      */                                       
/* 3265 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3266 */                                       out.write("  </td>");
/* 3267 */                                       out.write("\n        </tr>\n        <tr>\n          <td align=\"center\" colspan=\"2\"> <br> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n              <tr>\n                <td class=\"columnheadingb\">");
/* 3268 */                                       out.print(FormatUtil.getString("table.heading.attribute"));
/* 3269 */                                       out.write("</td>\n                <td class=\"columnheadingb\" align=\"center\">");
/* 3270 */                                       out.print(FormatUtil.getString("am.javaruntime.used"));
/* 3271 */                                       out.write("(&#37;)</td>\n                <td class=\"columnheadingb\">");
/* 3272 */                                       out.print(FormatUtil.getString("table.heading.status"));
/* 3273 */                                       out.write("</td>\n              </tr>\n              <tr>\n                <td class=\"whitegrayborder\">");
/* 3274 */                                       out.print(FormatUtil.getString("am.webclient.jre.cpuload.text"));
/* 3275 */                                       out.write("</td>\n                <td class=\"whitegrayborder\" align=\"center\">");
/* 3276 */                                       out.print(memoryProps.get("cpuload"));
/* 3277 */                                       out.write("</td>\n                <td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3278 */                                       out.print(resourceid);
/* 3279 */                                       out.write("&attributeid=3682')\">");
/* 3280 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3682")));
/* 3281 */                                       out.write("</a></td>\n              </tr>\n              <tr>\n                <td colspan=\"3\" align=\"right\" ><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3282 */                                       out.print(resourceid);
/* 3283 */                                       out.write("&attributeIDs=3682&attributeToSelect=3682&redirectto=");
/* 3284 */                                       out.print(encodeurl);
/* 3285 */                                       out.write("\" class=\"staticlinks\">");
/* 3286 */                                       out.print(ALERTCONFIG_TEXT);
/* 3287 */                                       out.write("</a>&nbsp;</td>\n              </tr>\n            </table></td>\n        </tr>\n      </table>\n      \n      \n\n");
/*      */                                     }
/* 3289 */                                     out.write("   \n   \n   \n   </td>\n  </tr>\n</table>\n\n\n\n\n\n\n<!--Memory and CPU graph ends here-->\n<br>\n<table border=\"0\" width=\"99%\" class=\"lrtbdarkborder\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td class=\"tableheadingbborder\" >");
/* 3290 */                                     out.print(FormatUtil.getString("am.webclient.jdk15.memorypieheading.text"));
/* 3291 */                                     out.write("</td>\n <td class=\"tableheadingbborder\" >");
/* 3292 */                                     out.print(FormatUtil.getString("am.webclient.jdk15.memorybarheading.text"));
/* 3293 */                                     out.write("</td>\n</tr>\n<tr>\n<td width=\"50%\" valign=\"top\" class=\"rborder\">\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n        <tr>\n\t<td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 3294 */                                     if (_jspx_meth_c_005fout_005f33(_jspx_page_context))
/*      */                                       return;
/* 3296 */                                     out.write("&attributeid=3608&period=-7&resourcename=");
/* 3297 */                                     if (_jspx_meth_c_005fout_005f34(_jspx_page_context))
/*      */                                       return;
/* 3299 */                                     out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3300 */                                     out.print(seven_days_text);
/* 3301 */                                     out.write("\"></a></td>\n\t<td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 3302 */                                     if (_jspx_meth_c_005fout_005f35(_jspx_page_context))
/*      */                                       return;
/* 3304 */                                     out.write("&attributeid=3608&period=-30&resourcename=");
/* 3305 */                                     if (_jspx_meth_c_005fout_005f36(_jspx_page_context))
/*      */                                       return;
/* 3307 */                                     out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3308 */                                     out.print(thiry_days_text);
/* 3309 */                                     out.write("\"></a></td>\n        </tr>\n\t</table>\n</td>\n<td width=\"50%\" align=\"center\" valign=\"top\" rowspan=\"2\">\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n          <tr>\n          <td align=\"center\">\n");
/* 3310 */                                     out.write("<!--$Id$-->\n<html>\n<head>\n<title>Untitled Document</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"../images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n</head>\n\n\n<body>\n");
/*      */                                     
/* 3312 */                                     String vendor1 = (String)request.getAttribute("vendor");
/* 3313 */                                     boolean isMetaSpace1 = "true".equals((String)request.getAttribute("isMetaspace"));
/* 3314 */                                     pageContext.setAttribute("isMetaSpace1", Boolean.valueOf(isMetaSpace1));
/* 3315 */                                     long mspaceperg = 0L;long ccspaceperg = 0L;
/* 3316 */                                     HashMap memoryProps1 = (HashMap)request.getAttribute("memoryProps");
/*      */                                     
/* 3318 */                                     long eden = ((Long)memoryProps1.get("eden")).longValue();
/* 3319 */                                     long maxeden = ((Long)memoryProps1.get("maxeden")).longValue();
/* 3320 */                                     long edenper = (memoryProps1.get("edenper") != null) && ((memoryProps1.get("edenper").equals("-")) || (memoryProps1.get("edenper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("edenper")).longValue();
/* 3321 */                                     long survivor = ((Long)memoryProps1.get("survivor")).longValue();
/* 3322 */                                     long maxsurvivor = ((Long)memoryProps1.get("maxsurvivor")).longValue();
/* 3323 */                                     long survivorper = (memoryProps1.get("survivorper") != null) && ((memoryProps1.get("survivorper").equals("-")) || (memoryProps1.get("survivorper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("survivorper")).longValue();
/* 3324 */                                     long tengen = ((Long)memoryProps1.get("tengen")).longValue();
/* 3325 */                                     long maxtengen = ((Long)memoryProps1.get("maxtengen")).longValue();
/* 3326 */                                     long tengenper = (memoryProps1.get("tengenper") != null) && ((memoryProps1.get("tengenper").equals("-")) || (memoryProps1.get("tengenper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("tengenper")).longValue();
/*      */                                     
/* 3328 */                                     long permgen = ((Long)memoryProps1.get("permgen")).longValue();
/* 3329 */                                     long maxpermgen = ((Long)memoryProps1.get("maxpermgen")).longValue();
/* 3330 */                                     long permgenper = (memoryProps1.get("permgenper") != null) && ((memoryProps1.get("permgenper").equals("-")) || (memoryProps1.get("permgenper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("permgenper")).longValue();
/* 3331 */                                     long codecache = ((Long)memoryProps1.get("codecache")).longValue();
/* 3332 */                                     long maxcodecache = ((Long)memoryProps1.get("maxcodecache")).longValue();
/* 3333 */                                     long codecacheper = (memoryProps1.get("codecacheper") != null) && ((memoryProps1.get("codecacheper").equals("-")) || (memoryProps1.get("codecacheper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("codecacheper")).longValue();
/* 3334 */                                     long permgenro = ((Long)memoryProps1.get("permgenro")).longValue();
/* 3335 */                                     long maxpermgenro = ((Long)memoryProps1.get("maxpermgenro")).longValue();
/* 3336 */                                     long permgenroper = (memoryProps1.get("permgenroper") != null) && ((memoryProps1.get("permgenroper").equals("-")) || (memoryProps1.get("permgenroper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("permgenroper")).longValue();
/* 3337 */                                     long permgenrw = ((Long)memoryProps1.get("permgenrw")).longValue();
/* 3338 */                                     long maxpermgenrw = ((Long)memoryProps1.get("maxpermgenrw")).longValue();
/* 3339 */                                     long permgenrwper = (memoryProps1.get("permgenrwper") != null) && ((memoryProps1.get("permgenrwper").equals("-")) || (memoryProps1.get("permgenrwper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("permgenrwper")).longValue();
/*      */                                     
/* 3341 */                                     if (isMetaSpace1) {
/* 3342 */                                       mspaceperg = (memoryProps1.get("MSPACEPER") != null) && ((memoryProps1.get("MSPACEPER").equals("-")) || (memoryProps1.get("MSPACEPER").equals("-1"))) ? 0L : ((Long)memoryProps1.get("MSPACEPER")).longValue();
/* 3343 */                                       ccspaceperg = (memoryProps1.get("CCSPACEPER") != null) && ((memoryProps1.get("CCSPACEPER").equals("-")) || (memoryProps1.get("CCSPACEPER").equals("-1"))) ? 0L : ((Long)memoryProps1.get("CCSPACEPER")).longValue();
/*      */                                     }
/*      */                                     
/* 3346 */                                     if (vendor1.contains("Sun"))
/*      */                                     {
/*      */ 
/* 3349 */                                       out.write("\t\n<table height=\"135\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n   \n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n      <td height=\"20px\">&nbsp;</td>\n      <td><span class=\"bodytext\">ES</span></td>\n      <td><span class=\"bodytext\">SS</span></td>\n      <td><span class=\"bodytext\">TG</span></td>\n      </tr>\n        <tr>\n       \t<td align=\"right\"><img src=\"/images/percentages.gif\"></td>\t\n          <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 3350 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.eden.text"));
/* 3351 */                                       out.write(32);
/* 3352 */                                       out.write(45);
/* 3353 */                                       out.write(32);
/* 3354 */                                       out.print(edenper);
/* 3355 */                                       out.write("%\">\n\t  <table height=\"");
/* 3356 */                                       out.print(edenper * 116L / 100L);
/* 3357 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3358 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.survivor.text"));
/* 3359 */                                       out.write(32);
/* 3360 */                                       out.write(45);
/* 3361 */                                       out.write(32);
/* 3362 */                                       out.print(survivorper);
/* 3363 */                                       out.write("%\">\n\t  <table height=\"");
/* 3364 */                                       out.print(survivorper * 116L / 100L);
/* 3365 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t    </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3366 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.tenuredgen.text"));
/* 3367 */                                       out.write(32);
/* 3368 */                                       out.write(45);
/* 3369 */                                       out.write(32);
/* 3370 */                                       out.print(tengenper);
/* 3371 */                                       out.write("%\">\n\t  <table height=\"");
/* 3372 */                                       out.print(tengenper * 116L / 100L);
/* 3373 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n          </td>\n        </tr>\n        <tr align=\"center\">\n       \t<td><br></td>\t\n          <td colspan=\"3\" height=\"25\" class=\"columnheadingb\">");
/* 3374 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.heap.text"));
/* 3375 */                                       out.write("</td>\n        </tr>\n      </table></td>\n    <td>&nbsp;</td>\n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n            <td height=\"20px\"><span class=\"bodytext\">CC</span></td>");
/* 3376 */                                       out.write("\n        \t");
/* 3377 */                                       if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
/*      */                                         return;
/* 3379 */                                       if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
/*      */                                         return;
/* 3381 */                                       out.write("\n      </tr>\n        <tr> \n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3382 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.codecache.text"));
/* 3383 */                                       out.write(32);
/* 3384 */                                       out.write(45);
/* 3385 */                                       out.write(32);
/* 3386 */                                       out.print(codecacheper);
/* 3387 */                                       out.write("%\">\n\t  <table height=\"");
/* 3388 */                                       out.print(codecacheper * 116L / 100L);
/* 3389 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  \n\t ");
/*      */                                       
/* 3391 */                                       IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3392 */                                       _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3393 */                                       _jspx_th_c_005fif_005f8.setParent(null);
/*      */                                       
/* 3395 */                                       _jspx_th_c_005fif_005f8.setTest("${isMetaSpace1}");
/* 3396 */                                       int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3397 */                                       if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                         for (;;) {
/* 3399 */                                           out.write("\n       \n\t\t<td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3400 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.metaspace.text"));
/* 3401 */                                           out.write(32);
/* 3402 */                                           out.write(45);
/* 3403 */                                           out.write(32);
/* 3404 */                                           out.print(mspaceperg);
/* 3405 */                                           out.write("%\">\n\t\t<table height=\"");
/* 3406 */                                           out.print(permgenper * 116L / 100L);
/* 3407 */                                           out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t           </tr>\n\t\t         </table>\n\t\t</td>\n \n\t\t<td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3408 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.Compressed.text"));
/* 3409 */                                           out.write(32);
/* 3410 */                                           out.write(45);
/* 3411 */                                           out.write(32);
/* 3412 */                                           out.print(ccspaceperg);
/* 3413 */                                           out.write("%\">\n\t\t<table height=\"");
/* 3414 */                                           out.print(permgenper * 116L / 100L);
/* 3415 */                                           out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t           </tr>\n\t\t         </table>\n\t\t</td>\n       \n       ");
/* 3416 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3417 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3421 */                                       if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3422 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                       }
/*      */                                       
/* 3425 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*      */                                       
/* 3427 */                                       IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3428 */                                       _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3429 */                                       _jspx_th_c_005fif_005f9.setParent(null);
/*      */                                       
/* 3431 */                                       _jspx_th_c_005fif_005f9.setTest("${not isMetaSpace1}");
/* 3432 */                                       int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3433 */                                       if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                         for (;;) {
/* 3435 */                                           out.write("\n\t  <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3436 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.permgen.text"));
/* 3437 */                                           out.write(32);
/* 3438 */                                           out.write(45);
/* 3439 */                                           out.write(32);
/* 3440 */                                           out.print(permgenper);
/* 3441 */                                           out.write("%\">\n\t  <table height=\"");
/* 3442 */                                           out.print(permgenper * 116L / 100L);
/* 3443 */                                           out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  ");
/*      */                                           
/* 3445 */                                           if (permgenro != -1L)
/*      */                                           {
/* 3447 */                                             out.write("\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3448 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.permgenro.text"));
/* 3449 */                                             out.write(32);
/* 3450 */                                             out.write(45);
/* 3451 */                                             out.write(32);
/* 3452 */                                             out.print(permgenroper);
/* 3453 */                                             out.write("%\">\n\t  <table height=\"");
/* 3454 */                                             out.print(permgenroper * 116L / 100L);
/* 3455 */                                             out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3456 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.permgenrw.text"));
/* 3457 */                                             out.write(32);
/* 3458 */                                             out.write(45);
/* 3459 */                                             out.write(32);
/* 3460 */                                             out.print(permgenrwper);
/* 3461 */                                             out.write("%\">\n\t  <table height=\"");
/* 3462 */                                             out.print(permgenrwper * 116L / 100L);
/* 3463 */                                             out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  ");
/*      */                                           }
/* 3465 */                                           out.write("\n\t  ");
/* 3466 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3467 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3471 */                                       if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3472 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                       }
/*      */                                       
/* 3475 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3476 */                                       out.write("\n        </tr>\n        <tr align=\"center\"> \n          <td height=\"25\" colspan=\"4\" class=\"columnheadingb\">");
/* 3477 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.nonheap.text"));
/* 3478 */                                       out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n\n");
/* 3479 */                                     } else if (vendor1.contains("IBM")) {
/* 3480 */                                       long perjitcache = ((Long)memoryProps1.get("PERJITCCACHE")).longValue();
/* 3481 */                                       long perjitdcache = ((Long)memoryProps1.get("PERJITDCACHE")).longValue();
/* 3482 */                                       long perclassstor = ((Long)memoryProps1.get("PERCLASSSTOR")).longValue();
/* 3483 */                                       long pernonhpstor = ((Long)memoryProps1.get("PERNONHPSTOR")).longValue();
/* 3484 */                                       long perjavaheap = ((Long)memoryProps1.get("PERJAVAHEAP")).longValue();
/*      */                                       
/* 3486 */                                       out.write("\t\n\n\n<table height=\"135\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n   \n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n      <td height=\"20px\">&nbsp;</td>\n      <td><span class=\"bodytext\">JH</span></td>");
/* 3487 */                                       out.write("\n \n      </tr>\n        <tr>\n       \t<td align=\"right\"><img src=\"/images/percentages.gif\"></td>\t\n          <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 3488 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.javaheap.text1"));
/* 3489 */                                       out.write(32);
/* 3490 */                                       out.write(45);
/* 3491 */                                       out.write(32);
/* 3492 */                                       out.print(perjavaheap);
/* 3493 */                                       out.write("%\">\n\t       <table height=\"");
/* 3494 */                                       out.print(perjavaheap * 116L / 100L);
/* 3495 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n        </tr>\n        <tr align=\"center\">\n       \t<td><br></td>\t\n          <td colspan=\"3\" height=\"25\" class=\"columnheadingb\">");
/* 3496 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.heap.text"));
/* 3497 */                                       out.write("</td>\n        </tr>\n      </table></td>\n    <td>&nbsp;</td>\n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n            <td height=\"20px\"><span class=\"bodytext\">JCC</span></td>");
/* 3498 */                                       out.write("\n            <td><span class=\"bodytext\">JDC</span></td>");
/* 3499 */                                       out.write("\n            <td><span class=\"bodytext\">CS</span></td>");
/* 3500 */                                       out.write("\n            <td><span class=\"bodytext\">MS</span></td>");
/* 3501 */                                       out.write("\n      </tr>\n        <tr> \n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3502 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.jitcodecache.text"));
/* 3503 */                                       out.write(32);
/* 3504 */                                       out.write(45);
/* 3505 */                                       out.write(32);
/* 3506 */                                       out.print(perjitcache);
/* 3507 */                                       out.write("%\">\n\t  <table height=\"");
/* 3508 */                                       out.print(perjitcache * 116L / 100L);
/* 3509 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3510 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.jitdatacache.text"));
/* 3511 */                                       out.write(32);
/* 3512 */                                       out.write(45);
/* 3513 */                                       out.write(32);
/* 3514 */                                       out.print(perjitdcache);
/* 3515 */                                       out.write("%\">\n\t  <table height=\"");
/* 3516 */                                       out.print(perjitdcache * 116L / 100L);
/* 3517 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  \n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3518 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.classstorage.text"));
/* 3519 */                                       out.write(32);
/* 3520 */                                       out.write(45);
/* 3521 */                                       out.write(32);
/* 3522 */                                       out.print(perclassstor);
/* 3523 */                                       out.write("%\">\n\t  <table height=\"");
/* 3524 */                                       out.print(perclassstor * 116L / 100L);
/* 3525 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3526 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.nonheapstorage.text"));
/* 3527 */                                       out.write(32);
/* 3528 */                                       out.write(45);
/* 3529 */                                       out.write(32);
/* 3530 */                                       out.print(pernonhpstor);
/* 3531 */                                       out.write("%\">\n\t  <table height=\"");
/* 3532 */                                       out.print(pernonhpstor * 116L / 100L);
/* 3533 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  \n        </tr>\n        <tr align=\"center\"> \n          <td height=\"25\" colspan=\"4\" class=\"columnheadingb\">");
/* 3534 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.nonheap.text"));
/* 3535 */                                       out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n\n\n\n");
/* 3536 */                                     } else if ((vendor1.contains("BEA")) || (vendor1.contains("Oracle"))) {
/* 3537 */                                       long perjjitcache = ((Long)memoryProps1.get("PERJJAVAHEAP")).longValue();
/* 3538 */                                       long pernursery = ((Long)memoryProps1.get("PERNURSERY")).longValue();
/* 3539 */                                       long peroldspace = ((Long)memoryProps1.get("PEROLDSPACE")).longValue();
/* 3540 */                                       long perclassmem = ((Long)memoryProps1.get("PERCLASSMEM")).longValue();
/* 3541 */                                       long perclassblock = ((Long)memoryProps1.get("PERCLASSBLOCK")).longValue();
/* 3542 */                                       long nonheapper = (memoryProps1.get("nonheapper") != null) && ((memoryProps1.get("nonheapper").equals("-")) || (memoryProps1.get("nonheapper").equals("-1"))) ? 0L : ((Long)memoryProps1.get("nonheapper")).longValue();
/*      */                                       
/* 3544 */                                       out.write("\n\n<table height=\"135\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n   \n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n      <td height=\"20px\">&nbsp;</td>\n      <td><span class=\"bodytext\">JH</span></td>");
/* 3545 */                                       out.write("\n      <td><span class=\"bodytext\">NU</span></td>");
/* 3546 */                                       out.write("\n      <td><span class=\"bodytext\">OS</span></td>");
/* 3547 */                                       out.write("\n      </tr>\n        <tr>\n       \t<td align=\"right\"><img src=\"/images/percentages.gif\"></td>\t\n          <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 3548 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.javaheap.text1"));
/* 3549 */                                       out.write(32);
/* 3550 */                                       out.write(45);
/* 3551 */                                       out.write(32);
/* 3552 */                                       out.print(perjjitcache);
/* 3553 */                                       out.write("%\">\n\t       <table height=\"");
/* 3554 */                                       out.print(perjjitcache * 116L / 100L);
/* 3555 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 3556 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.nursery.text"));
/* 3557 */                                       out.write(32);
/* 3558 */                                       out.write(45);
/* 3559 */                                       out.write(32);
/* 3560 */                                       out.print(pernursery);
/* 3561 */                                       out.write("%\">\n\t       <table height=\"");
/* 3562 */                                       out.print(pernursery * 116L / 100L);
/* 3563 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t</tr>\n\t      </table>\n\t  </td>\n\t  <td background=\"../images/bg_unusedjvm.gif\" height=\"116\" valign=\"bottom\" title=\"");
/* 3564 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.oldspace.text"));
/* 3565 */                                       out.write(32);
/* 3566 */                                       out.write(45);
/* 3567 */                                       out.write(32);
/* 3568 */                                       out.print(peroldspace);
/* 3569 */                                       out.write("%\">\n\t       <table height=\"");
/* 3570 */                                       out.print(peroldspace * 116L / 100L);
/* 3571 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t</tr>\n\t      </table>\n\t  </td>\n        </tr>\n        <tr align=\"center\">\n       \t<td><br></td>\t\n          <td colspan=\"3\" height=\"25\" class=\"columnheadingb\">");
/* 3572 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.heap.text"));
/* 3573 */                                       out.write("</td>\n        </tr>\n      </table></td>\n    \n    <td> \n      <table width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">\n      <tr>\n            <td height=\"20px\"><span class=\"bodytext\">NHM</span></td>");
/* 3574 */                                       out.write("\n            <td height=\"20px\"><span class=\"bodytext\">CM</span></td>");
/* 3575 */                                       out.write("\n            <td height=\"20px\"><span class=\"bodytext\">CB</span></td>");
/* 3576 */                                       out.write("\n      </tr>\n        <tr> \n          <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3577 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.nonheap.text"));
/* 3578 */                                       out.write(32);
/* 3579 */                                       out.write(45);
/* 3580 */                                       out.write(32);
/* 3581 */                                       out.print(nonheapper);
/* 3582 */                                       out.write("%\">\n\t  <table height=\"");
/* 3583 */                                       out.print(nonheapper * 116L / 100L);
/* 3584 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3585 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.classmemory.text"));
/* 3586 */                                       out.write(32);
/* 3587 */                                       out.write(45);
/* 3588 */                                       out.write(32);
/* 3589 */                                       out.print(perclassmem);
/* 3590 */                                       out.write("%\">\n\t    <table height=\"");
/* 3591 */                                       out.print(perclassmem * 116L / 100L);
/* 3592 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t</tr>\n\t    </table>\n\t  </td>\n\t  <td background=\"../images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"116\" title=\"");
/* 3593 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.classblock.text"));
/* 3594 */                                       out.write(32);
/* 3595 */                                       out.write(45);
/* 3596 */                                       out.write(32);
/* 3597 */                                       out.print(perclassblock);
/* 3598 */                                       out.write("%\">\n\t    <table height=\"");
/* 3599 */                                       out.print(perclassblock * 116L / 100L);
/* 3600 */                                       out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t  <td background=\"../images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n\t\t</tr>\n\t    </table>\n\t  </td>\n        </tr>\n        <tr align=\"center\"> \n          <td height=\"25\" colspan=\"4\" class=\"columnheadingb\">");
/* 3601 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.nonheap.text"));
/* 3602 */                                       out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n\n");
/*      */                                     }
/* 3604 */                                     out.write("\n</body>\n</html>\n");
/* 3605 */                                     out.write("\n\t</td>\n\t</tr>\n\t\n");
/* 3606 */                                     if (vendor.contains("Sun")) {
/* 3607 */                                       out.write("\t\n\t<tr>\n\t      <td align=\"center\"> <br>\n            <table  border=\"0\" cellspacing=\"0\" cellpadding=\"2\" width=\"100%\">\n              <tr class=\"yellowgrayborder\">\n                <td width=\"49%\"><span class=\"bodytext\"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ES\n                  - </b>");
/* 3608 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.eden.text"));
/* 3609 */                                       out.write("</span></td>\n              \n              ");
/*      */                                       
/* 3611 */                                       IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3612 */                                       _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3613 */                                       _jspx_th_c_005fif_005f10.setParent(null);
/*      */                                       
/* 3615 */                                       _jspx_th_c_005fif_005f10.setTest("${isMetaSpace}");
/* 3616 */                                       int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3617 */                                       if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                         for (;;) {
/* 3619 */                                           out.write("\n           \t\t <td width=\"51%\"><span class=\"bodytext\"><b>MS - </b>");
/* 3620 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.metaspace.text"));
/* 3621 */                                           out.write("</span></td>");
/* 3622 */                                           out.write("\n              ");
/* 3623 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3624 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3628 */                                       if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3629 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                       }
/*      */                                       
/* 3632 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3633 */                                       out.write(32);
/*      */                                       
/* 3635 */                                       IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3636 */                                       _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3637 */                                       _jspx_th_c_005fif_005f11.setParent(null);
/*      */                                       
/* 3639 */                                       _jspx_th_c_005fif_005f11.setTest("${not isMetaSpace}");
/* 3640 */                                       int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3641 */                                       if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                         for (;;) {
/* 3643 */                                           out.write("\n               <td width=\"51%\"><span class=\"bodytext\"><b>PG - </b>");
/* 3644 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.permgen.text"));
/* 3645 */                                           out.write("</span></td>\n     \t\t  ");
/* 3646 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3647 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3651 */                                       if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3652 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                       }
/*      */                                       
/* 3655 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3656 */                                       out.write("\n              </tr>\n              <tr class=\"whitegrayborder\" >\n                <td><span class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>SS -</b>");
/* 3657 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.survivor.text"));
/* 3658 */                                       out.write("</span></td>\n                <td><span class=\"bodytext\"><b>CC - </b>");
/* 3659 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.codecache.text"));
/* 3660 */                                       out.write("</span></td>\n              </tr>\n              <tr  class=\"yellowgrayborder\">\n                <td><span class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>TG - </b>");
/* 3661 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.tenuredgen.text"));
/* 3662 */                                       out.write("</span></td>\n                \n              ");
/*      */                                       
/* 3664 */                                       IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3665 */                                       _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3666 */                                       _jspx_th_c_005fif_005f12.setParent(null);
/*      */                                       
/* 3668 */                                       _jspx_th_c_005fif_005f12.setTest("${isMetaSpace}");
/* 3669 */                                       int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3670 */                                       if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                         for (;;) {
/* 3672 */                                           out.write("\n           \t\t <td width=\"51%\"><span class=\"bodytext\"><b>CCS - </b>");
/* 3673 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.Compressed.text"));
/* 3674 */                                           out.write("</span></td>");
/* 3675 */                                           out.write("\n           \t\t");
/* 3676 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3677 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3681 */                                       if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3682 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                                       }
/*      */                                       
/* 3685 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3686 */                                       out.write(32);
/*      */                                       
/* 3688 */                                       IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3689 */                                       _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3690 */                                       _jspx_th_c_005fif_005f13.setParent(null);
/*      */                                       
/* 3692 */                                       _jspx_th_c_005fif_005f13.setTest("${not isMetaSpace}");
/* 3693 */                                       int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3694 */                                       if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                         for (;;) {
/* 3696 */                                           out.write("\n                <td><span class=\"bodytext\"><b>RO - </b>");
/* 3697 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.permgenro.text"));
/* 3698 */                                           out.write("</span></td>\n               ");
/* 3699 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3700 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3704 */                                       if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3705 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                       }
/*      */                                       
/* 3708 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3709 */                                       out.write("\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td>&nbsp;</td>\n             ");
/* 3710 */                                       if (_jspx_meth_c_005fif_005f14(_jspx_page_context))
/*      */                                         return;
/* 3712 */                                       out.write(32);
/*      */                                       
/* 3714 */                                       IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3715 */                                       _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3716 */                                       _jspx_th_c_005fif_005f15.setParent(null);
/*      */                                       
/* 3718 */                                       _jspx_th_c_005fif_005f15.setTest("${not isMetaSpace}");
/* 3719 */                                       int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3720 */                                       if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                         for (;;) {
/* 3722 */                                           out.write("\n                <td><span class=\"bodytext\"><b>RW - </b>");
/* 3723 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.permgenrw.text"));
/* 3724 */                                           out.write("</span></td>\n                ");
/* 3725 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3726 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3730 */                                       if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3731 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                       }
/*      */                                       
/* 3734 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3735 */                                       out.write("\n              </tr>\n            </table>\n\t</span>\n\t</td>\n\t</tr>\n");
/*      */                                     }
/* 3737 */                                     out.write(10);
/* 3738 */                                     out.write(32);
/* 3739 */                                     out.write(10);
/* 3740 */                                     if (vendor.contains("IBM")) {
/* 3741 */                                       out.write("\n\t\t<tr>\n\t      <td align=\"center\"> <br>\n            <table  border=\"0\" cellspacing=\"0\" cellpadding=\"2\" width=\"100%\">\n              <tr class=\"yellowgrayborder\">\n                <td width=\"49%\"><span class=\"bodytext\"><b style=\"padding-left:15px\">JH - </b>");
/* 3742 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.javaheap.text1"));
/* 3743 */                                       out.write("</span></td> ");
/* 3744 */                                       out.write("\n                <td width=\"51%\"><span class=\"bodytext\"><b>JCC - </b>");
/* 3745 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.jitcodecache.text"));
/* 3746 */                                       out.write("</span></td>");
/* 3747 */                                       out.write("\n              </tr>\n              <tr class=\"whitegrayborder\" >\n                <td><span class=\"bodytext\">&nbsp;</span></td>\n                <td><span class=\"bodytext\"><b>JDC - </b>");
/* 3748 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.jitdatacache.text"));
/* 3749 */                                       out.write("</span></td>");
/* 3750 */                                       out.write("\n              </tr>\n              <tr  class=\"yellowgrayborder\">\n                <td><span class=\"bodytext\" style=\"padding-left:2px\"></span></td>\n                <td><span class=\"bodytext\"><b>CS -</b>");
/* 3751 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.classstorage.text"));
/* 3752 */                                       out.write("</span></td>");
/* 3753 */                                       out.write("\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td>&nbsp;</td>\n                <td><span class=\"bodytext\"><b>MS -</b>");
/* 3754 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.nonheapstorage.text"));
/* 3755 */                                       out.write("</span></td>");
/* 3756 */                                       out.write("\n              </tr>\n            </table>\n\t</span>\n\t</td>\n\t</tr>\n");
/*      */                                     }
/* 3758 */                                     out.write(9);
/* 3759 */                                     out.write(10);
/* 3760 */                                     out.write(10);
/* 3761 */                                     if ((vendor.contains("BEA")) || (vendor.contains("Oracle"))) {
/* 3762 */                                       out.write("\n\t\n\t\t\t<tr>\n\t      <td align=\"center\"> <br>\n            <table  border=\"0\" cellspacing=\"0\" cellpadding=\"2\" width=\"100%\">\n              <tr class=\"yellowgrayborder\">\n                <td width=\"49%\"><span class=\"bodytext\"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JH - </b>");
/* 3763 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.javaheap.text"));
/* 3764 */                                       out.write("</span></td>");
/* 3765 */                                       out.write("\n                <td width=\"51%\"><span class=\"bodytext\"><b>NHM - </b>");
/* 3766 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.nonheapmemory.text"));
/* 3767 */                                       out.write("</span></td>");
/* 3768 */                                       out.write("\n              </tr>\n              <tr class=\"yellowgrayborder\">\n\t      \t<td width=\"49%\"><span class=\"bodytext\"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NU - </b>");
/* 3769 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.nursery.text"));
/* 3770 */                                       out.write("</span></td>");
/* 3771 */                                       out.write("\n\t      \t<td width=\"51%\"><span class=\"bodytext\"><b>CM - </b>");
/* 3772 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.classmemory.text"));
/* 3773 */                                       out.write("</span></td>");
/* 3774 */                                       out.write("\n\t      </tr>\n\t      <tr class=\"yellowgrayborder\">\n\t      \t<td width=\"49%\"><span class=\"bodytext\"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;OS - </b>");
/* 3775 */                                       out.print(FormatUtil.getString("am.webclient.javaruntime.oldspace.text"));
/* 3776 */                                       out.write("</span></td>");
/* 3777 */                                       out.write("\n\t      \t<td width=\"51%\"><span class=\"bodytext\"><b>CB - </b>");
/* 3778 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.classblock.text"));
/* 3779 */                                       out.write("</span></td>");
/* 3780 */                                       out.write("\n\t      </tr>\n            </table>\n\t</span>\n\t</td>\n\t</tr>\n\t\n");
/*      */                                     }
/* 3782 */                                     out.write("\t\n\n\n\t\n</table>\n<br>\n</td>\n</tr>\n<tr>\n<td width=\"48%\" align=\"center\" class=\"rborder\">\n");
/*      */                                     
/* 3784 */                                     jdk15Graph.setParameter(resourceid, "overallmemory");
/*      */                                     
/* 3786 */                                     out.write(10);
/*      */                                     
/* 3788 */                                     AMWolf _jspx_th_awolf_005fpiechart_005f1 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3789 */                                     _jspx_th_awolf_005fpiechart_005f1.setPageContext(_jspx_page_context);
/* 3790 */                                     _jspx_th_awolf_005fpiechart_005f1.setParent(null);
/*      */                                     
/* 3792 */                                     _jspx_th_awolf_005fpiechart_005f1.setDataSetProducer("jdk15Graph");
/*      */                                     
/* 3794 */                                     _jspx_th_awolf_005fpiechart_005f1.setWidth("375");
/*      */                                     
/* 3796 */                                     _jspx_th_awolf_005fpiechart_005f1.setHeight("200");
/*      */                                     
/* 3798 */                                     _jspx_th_awolf_005fpiechart_005f1.setLegend("true");
/*      */                                     
/* 3800 */                                     _jspx_th_awolf_005fpiechart_005f1.setUrl(true);
/*      */                                     
/* 3802 */                                     _jspx_th_awolf_005fpiechart_005f1.setUnits("MB");
/*      */                                     
/* 3804 */                                     _jspx_th_awolf_005fpiechart_005f1.setDecimal(true);
/* 3805 */                                     int _jspx_eval_awolf_005fpiechart_005f1 = _jspx_th_awolf_005fpiechart_005f1.doStartTag();
/* 3806 */                                     if (_jspx_eval_awolf_005fpiechart_005f1 != 0) {
/* 3807 */                                       if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 3808 */                                         out = _jspx_page_context.pushBody();
/* 3809 */                                         _jspx_th_awolf_005fpiechart_005f1.setBodyContent((BodyContent)out);
/* 3810 */                                         _jspx_th_awolf_005fpiechart_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3813 */                                         out.write(10);
/*      */                                         
/* 3815 */                                         Property _jspx_th_awolf_005fmap_005f1 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3816 */                                         _jspx_th_awolf_005fmap_005f1.setPageContext(_jspx_page_context);
/* 3817 */                                         _jspx_th_awolf_005fmap_005f1.setParent(_jspx_th_awolf_005fpiechart_005f1);
/*      */                                         
/* 3819 */                                         _jspx_th_awolf_005fmap_005f1.setId("color");
/* 3820 */                                         int _jspx_eval_awolf_005fmap_005f1 = _jspx_th_awolf_005fmap_005f1.doStartTag();
/* 3821 */                                         if (_jspx_eval_awolf_005fmap_005f1 != 0) {
/* 3822 */                                           if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 3823 */                                             out = _jspx_page_context.pushBody();
/* 3824 */                                             _jspx_th_awolf_005fmap_005f1.setBodyContent((BodyContent)out);
/* 3825 */                                             _jspx_th_awolf_005fmap_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3828 */                                             out.write(10);
/*      */                                             
/* 3830 */                                             AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3831 */                                             _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 3832 */                                             _jspx_th_awolf_005fparam_005f2.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                             
/* 3834 */                                             _jspx_th_awolf_005fparam_005f2.setName("2");
/*      */                                             
/* 3836 */                                             _jspx_th_awolf_005fparam_005f2.setValue(warning);
/* 3837 */                                             int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 3838 */                                             if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 3839 */                                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2); return;
/*      */                                             }
/*      */                                             
/* 3842 */                                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 3843 */                                             out.write(10);
/*      */                                             
/* 3845 */                                             AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3846 */                                             _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 3847 */                                             _jspx_th_awolf_005fparam_005f3.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                             
/* 3849 */                                             _jspx_th_awolf_005fparam_005f3.setName("1");
/*      */                                             
/* 3851 */                                             _jspx_th_awolf_005fparam_005f3.setValue(available);
/* 3852 */                                             int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 3853 */                                             if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 3854 */                                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3); return;
/*      */                                             }
/*      */                                             
/* 3857 */                                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 3858 */                                             out.write(10);
/*      */                                             
/* 3860 */                                             AMParam _jspx_th_awolf_005fparam_005f4 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3861 */                                             _jspx_th_awolf_005fparam_005f4.setPageContext(_jspx_page_context);
/* 3862 */                                             _jspx_th_awolf_005fparam_005f4.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                             
/* 3864 */                                             _jspx_th_awolf_005fparam_005f4.setName("0");
/*      */                                             
/* 3866 */                                             _jspx_th_awolf_005fparam_005f4.setValue(unavailable);
/* 3867 */                                             int _jspx_eval_awolf_005fparam_005f4 = _jspx_th_awolf_005fparam_005f4.doStartTag();
/* 3868 */                                             if (_jspx_th_awolf_005fparam_005f4.doEndTag() == 5) {
/* 3869 */                                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4); return;
/*      */                                             }
/*      */                                             
/* 3872 */                                             this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 3873 */                                             out.write(10);
/* 3874 */                                             int evalDoAfterBody = _jspx_th_awolf_005fmap_005f1.doAfterBody();
/* 3875 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3878 */                                           if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 3879 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3882 */                                         if (_jspx_th_awolf_005fmap_005f1.doEndTag() == 5) {
/* 3883 */                                           this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1); return;
/*      */                                         }
/*      */                                         
/* 3886 */                                         this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 3887 */                                         out.write(10);
/* 3888 */                                         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f1.doAfterBody();
/* 3889 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3892 */                                       if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 3893 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3896 */                                     if (_jspx_th_awolf_005fpiechart_005f1.doEndTag() == 5) {
/* 3897 */                                       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1);
/*      */                                     }
/*      */                                     else {
/* 3900 */                                       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1);
/* 3901 */                                       out.write("\n<br>\n</td>\n</tr>\n</table>\n\n<br>\n\n\n");
/* 3902 */                                       if (vendor.contains("Sun")) {
/* 3903 */                                         out.write("\n\n\n<table width=\"99%\" height=\"299\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" height=\"31\" class=\"tableheadingbborder\">");
/* 3904 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.heapmemory.text"));
/* 3905 */                                         out.write("\n</td>\n</tr>\n<tr>\n");
/*      */                                         
/* 3907 */                                         jdk15Graph.setParameter(resourceid, "heap");
/*      */                                         
/* 3909 */                                         out.write("\n<td width=\"100%\" height=\"64\" colspan=\"2\" >\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n          <tr>\n\t              <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 3910 */                                         if (_jspx_meth_c_005fout_005f37(_jspx_page_context))
/*      */                                           return;
/* 3912 */                                         out.write("&attributeid=3611&period=-7&resourcename=");
/* 3913 */                                         if (_jspx_meth_c_005fout_005f38(_jspx_page_context))
/*      */                                           return;
/* 3915 */                                         out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3916 */                                         out.print(seven_days_text);
/* 3917 */                                         out.write("\"></a></td>\n\t\t                  <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 3918 */                                         if (_jspx_meth_c_005fout_005f39(_jspx_page_context))
/*      */                                           return;
/* 3920 */                                         out.write("&attributeid=3611&period=-30&resourcename=");
/* 3921 */                                         if (_jspx_meth_c_005fout_005f40(_jspx_page_context))
/*      */                                           return;
/* 3923 */                                         out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3924 */                                         out.print(thiry_days_text);
/* 3925 */                                         out.write("\"></a></td>\n\t</tr>\n\t<tr align=\"center\">\n\t<td colspan=\"2\"> ");
/*      */                                         
/* 3927 */                                         TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3928 */                                         _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 3929 */                                         _jspx_th_awolf_005ftimechart_005f4.setParent(null);
/*      */                                         
/* 3931 */                                         _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("jdk15Graph");
/*      */                                         
/* 3933 */                                         _jspx_th_awolf_005ftimechart_005f4.setWidth("550");
/*      */                                         
/* 3935 */                                         _jspx_th_awolf_005ftimechart_005f4.setHeight("225");
/*      */                                         
/* 3937 */                                         _jspx_th_awolf_005ftimechart_005f4.setLegend("true");
/*      */                                         
/* 3939 */                                         _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel(xaxis_time);
/*      */                                         
/* 3941 */                                         _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel(yaxis_memory);
/* 3942 */                                         int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 3943 */                                         if (_jspx_eval_awolf_005ftimechart_005f4 != 0) {
/* 3944 */                                           if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3945 */                                             out = _jspx_page_context.pushBody();
/* 3946 */                                             _jspx_th_awolf_005ftimechart_005f4.setBodyContent((BodyContent)out);
/* 3947 */                                             _jspx_th_awolf_005ftimechart_005f4.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3950 */                                             out.write(10);
/* 3951 */                                             out.write(9);
/* 3952 */                                             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f4.doAfterBody();
/* 3953 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3956 */                                           if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3957 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3960 */                                         if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 3961 */                                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4); return;
/*      */                                         }
/*      */                                         
/* 3964 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 3965 */                                         out.write(" </td>\n\t</tr>\n\t</table>\n</td>\n\n \n</tr>\n<tr>\n\t<td align=\"center\">\n\t<br>\n\t<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t<tr>\n\t<td class=\"columnheadingtb\">");
/* 3966 */                                         out.print(FormatUtil.getString("table.heading.attribute"));
/* 3967 */                                         out.write("</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 3968 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.maxvalue.text"));
/* 3969 */                                         out.write("&nbsp;(");
/* 3970 */                                         out.print(FormatUtil.getString("MB"));
/* 3971 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 3972 */                                         out.print(FormatUtil.getString("am.javaruntime.committed"));
/* 3973 */                                         out.write("&nbsp;(");
/* 3974 */                                         out.print(FormatUtil.getString("MB"));
/* 3975 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 3976 */                                         out.print(FormatUtil.getString("am.javaruntime.initial"));
/* 3977 */                                         out.write("&nbsp;(");
/* 3978 */                                         out.print(FormatUtil.getString("MB"));
/* 3979 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 3980 */                                         out.print(FormatUtil.getString("am.javaruntime.used"));
/* 3981 */                                         out.write("&nbsp;(");
/* 3982 */                                         out.print(FormatUtil.getString("MB"));
/* 3983 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 3984 */                                         out.print(FormatUtil.getString("table.heading.status"));
/* 3985 */                                         out.write("</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 3986 */                                         out.print(FormatUtil.getString("am.javaruntime.used"));
/* 3987 */                                         out.write("&nbsp;(&#37;)</td>\n\t<td class=\"columnheadingtb\">");
/* 3988 */                                         out.print(FormatUtil.getString("table.heading.status"));
/* 3989 */                                         out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 3990 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.totalheap.text"));
/* 3991 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 3992 */                                         out.print(memoryProps.get("maxheap"));
/* 3993 */                                         out.write("</td>\n\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 3994 */                                         out.print(memoryProps.get("commitheap"));
/* 3995 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 3996 */                                         out.print(memoryProps.get("initialheap"));
/* 3997 */                                         out.write("</td>\n\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 3998 */                                         out.print(memoryProps.get("heap"));
/* 3999 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4000 */                                         out.print(resourceid);
/* 4001 */                                         out.write("&attributeid=3611')\">");
/* 4002 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3611")));
/* 4003 */                                         out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">");
/* 4004 */                                         out.print(memoryProps.get("heapper"));
/* 4005 */                                         out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4006 */                                         out.print(resourceid);
/* 4007 */                                         out.write("&attributeid=3626')\">");
/* 4008 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3626")));
/* 4009 */                                         out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4010 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.eden.text"));
/* 4011 */                                         out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4012 */                                         out.print(memoryProps.get("maxeden"));
/* 4013 */                                         out.write("</td>\n\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4014 */                                         out.print(memoryProps.get("commitden"));
/* 4015 */                                         out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4016 */                                         out.print(memoryProps.get("initden"));
/* 4017 */                                         out.write("</td>\n\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4018 */                                         out.print(memoryProps.get("eden"));
/* 4019 */                                         out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4020 */                                         out.print(resourceid);
/* 4021 */                                         out.write("&attributeid=3612')\">");
/* 4022 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3612")));
/* 4023 */                                         out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">");
/* 4024 */                                         out.print(memoryProps.get("edenper"));
/* 4025 */                                         out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4026 */                                         out.print(resourceid);
/* 4027 */                                         out.write("&attributeid=3627')\">");
/* 4028 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3627")));
/* 4029 */                                         out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 4030 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.survivor.text"));
/* 4031 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4032 */                                         out.print(memoryProps.get("maxsurvivor"));
/* 4033 */                                         out.write("</td>\n\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4034 */                                         out.print(memoryProps.get("commitsurvivor"));
/* 4035 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4036 */                                         out.print(memoryProps.get("initsurvivor"));
/* 4037 */                                         out.write("</td>\n\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4038 */                                         out.print(memoryProps.get("survivor"));
/* 4039 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4040 */                                         out.print(resourceid);
/* 4041 */                                         out.write("&attributeid=3613')\">");
/* 4042 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3613")));
/* 4043 */                                         out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">");
/* 4044 */                                         out.print(memoryProps.get("survivorper"));
/* 4045 */                                         out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4046 */                                         out.print(resourceid);
/* 4047 */                                         out.write("&attributeid=3628')\">");
/* 4048 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3628")));
/* 4049 */                                         out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4050 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.tenuredgen.text"));
/* 4051 */                                         out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4052 */                                         out.print(memoryProps.get("maxtengen"));
/* 4053 */                                         out.write("</td>\n\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4054 */                                         out.print(memoryProps.get("committengen"));
/* 4055 */                                         out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4056 */                                         out.print(memoryProps.get("inittengen"));
/* 4057 */                                         out.write("</td>\n\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4058 */                                         out.print(memoryProps.get("tengen"));
/* 4059 */                                         out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4060 */                                         out.print(resourceid);
/* 4061 */                                         out.write("&attributeid=3614')\">");
/* 4062 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3614")));
/* 4063 */                                         out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">");
/* 4064 */                                         out.print(memoryProps.get("tengenper"));
/* 4065 */                                         out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4066 */                                         out.print(resourceid);
/* 4067 */                                         out.write("&attributeid=3629')\">");
/* 4068 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3629")));
/* 4069 */                                         out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td colspan=\"8\" align=\"right\" ><img style=\"position:relative; top:1px; \" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4070 */                                         out.print(resourceid);
/* 4071 */                                         out.write("&attributeIDs=3611,3626,3612,3627,3613,3628,3614,3629&attributeToSelect=3611&redirectto=");
/* 4072 */                                         out.print(encodeurl);
/* 4073 */                                         out.write("\" class=\"staticlinks\">");
/* 4074 */                                         out.print(ALERTCONFIG_TEXT);
/* 4075 */                                         out.write("</a>&nbsp;</td>\n\t</tr>\n\t</table>\n\n</td>\n</tr>\n</table>\n<br>\n<table width=\"99%\" height=\"299\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" height=\"31\" class=\"tableheadingbborder\">");
/* 4076 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.nonheapmemory.text"));
/* 4077 */                                         out.write("\n</td>\n</tr>\n<tr>\n");
/*      */                                         
/* 4079 */                                         jdk15Graph.setParameter(resourceid, "nonheap");
/*      */                                         
/* 4081 */                                         out.write("\n<td width=\"100%\" height=\"64\" colspan=\"2\" >\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n          <tr>\n\t              <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 4082 */                                         if (_jspx_meth_c_005fout_005f41(_jspx_page_context))
/*      */                                           return;
/* 4084 */                                         out.write("&attributeid=3615&period=-7&resourcename=");
/* 4085 */                                         if (_jspx_meth_c_005fout_005f42(_jspx_page_context))
/*      */                                           return;
/* 4087 */                                         out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4088 */                                         out.print(seven_days_text);
/* 4089 */                                         out.write("\"></a></td>\n\t\t                  <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 4090 */                                         if (_jspx_meth_c_005fout_005f43(_jspx_page_context))
/*      */                                           return;
/* 4092 */                                         out.write("&attributeid=3615&period=-30&resourcename=");
/* 4093 */                                         if (_jspx_meth_c_005fout_005f44(_jspx_page_context))
/*      */                                           return;
/* 4095 */                                         out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4096 */                                         out.print(thiry_days_text);
/* 4097 */                                         out.write("\"></a></td>\n\t</tr>\n\t<tr align=\"center\">\n\t<td colspan=\"2\"> ");
/*      */                                         
/* 4099 */                                         TimeChart _jspx_th_awolf_005ftimechart_005f5 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4100 */                                         _jspx_th_awolf_005ftimechart_005f5.setPageContext(_jspx_page_context);
/* 4101 */                                         _jspx_th_awolf_005ftimechart_005f5.setParent(null);
/*      */                                         
/* 4103 */                                         _jspx_th_awolf_005ftimechart_005f5.setDataSetProducer("jdk15Graph");
/*      */                                         
/* 4105 */                                         _jspx_th_awolf_005ftimechart_005f5.setWidth("550");
/*      */                                         
/* 4107 */                                         _jspx_th_awolf_005ftimechart_005f5.setHeight("225");
/*      */                                         
/* 4109 */                                         _jspx_th_awolf_005ftimechart_005f5.setLegend("true");
/*      */                                         
/* 4111 */                                         _jspx_th_awolf_005ftimechart_005f5.setXaxisLabel(xaxis_time);
/*      */                                         
/* 4113 */                                         _jspx_th_awolf_005ftimechart_005f5.setYaxisLabel(yaxis_memory);
/* 4114 */                                         int _jspx_eval_awolf_005ftimechart_005f5 = _jspx_th_awolf_005ftimechart_005f5.doStartTag();
/* 4115 */                                         if (_jspx_eval_awolf_005ftimechart_005f5 != 0) {
/* 4116 */                                           if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 4117 */                                             out = _jspx_page_context.pushBody();
/* 4118 */                                             _jspx_th_awolf_005ftimechart_005f5.setBodyContent((BodyContent)out);
/* 4119 */                                             _jspx_th_awolf_005ftimechart_005f5.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4122 */                                             out.write(10);
/* 4123 */                                             out.write(9);
/* 4124 */                                             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f5.doAfterBody();
/* 4125 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4128 */                                           if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 4129 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4132 */                                         if (_jspx_th_awolf_005ftimechart_005f5.doEndTag() == 5) {
/* 4133 */                                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5); return;
/*      */                                         }
/*      */                                         
/* 4136 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5);
/* 4137 */                                         out.write(" </td>\n\t</tr>\n\t</table>\n</td>\n</tr><tr>\n\t<td align=\"center\"><br>\n\t<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t<tr>\n\t<td class=\"columnheadingtb\">");
/* 4138 */                                         out.print(FormatUtil.getString("table.heading.attribute"));
/* 4139 */                                         out.write("</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4140 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.maxvalue.text"));
/* 4141 */                                         out.write("&nbsp;(");
/* 4142 */                                         out.print(FormatUtil.getString("MB"));
/* 4143 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4144 */                                         out.print(FormatUtil.getString("am.javaruntime.committed"));
/* 4145 */                                         out.write("&nbsp;(");
/* 4146 */                                         out.print(FormatUtil.getString("MB"));
/* 4147 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4148 */                                         out.print(FormatUtil.getString("am.javaruntime.initial"));
/* 4149 */                                         out.write("&nbsp;(");
/* 4150 */                                         out.print(FormatUtil.getString("MB"));
/* 4151 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4152 */                                         out.print(FormatUtil.getString("am.javaruntime.used"));
/* 4153 */                                         out.write("&nbsp;(");
/* 4154 */                                         out.print(FormatUtil.getString("MB"));
/* 4155 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4156 */                                         out.print(FormatUtil.getString("table.heading.status"));
/* 4157 */                                         out.write("</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4158 */                                         out.print(FormatUtil.getString("am.javaruntime.used"));
/* 4159 */                                         out.write("&nbsp;(&#37;)</td>\n\t<td class=\"columnheadingtb\">");
/* 4160 */                                         out.print(FormatUtil.getString("table.heading.status"));
/* 4161 */                                         out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 4162 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.nonheapmemory.text"));
/* 4163 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4164 */                                         out.print(memoryProps.get("maxnonheap"));
/* 4165 */                                         out.write("</td>\n\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4166 */                                         out.print(memoryProps.get("commitnonheap"));
/* 4167 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4168 */                                         out.print(memoryProps.get("initialnonheap"));
/* 4169 */                                         out.write("</td>\n\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4170 */                                         out.print(memoryProps.get("nonheap"));
/* 4171 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4172 */                                         out.print(resourceid);
/* 4173 */                                         out.write("&attributeid=3615')\">");
/* 4174 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3615")));
/* 4175 */                                         out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">");
/* 4176 */                                         out.print(memoryProps.get("nonheapper"));
/* 4177 */                                         out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4178 */                                         out.print(resourceid);
/* 4179 */                                         out.write("&attributeid=3630')\">");
/* 4180 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3630")));
/* 4181 */                                         out.write("</a></td>\n\t</tr>\n \n\t");
/*      */                                         
/* 4183 */                                         IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4184 */                                         _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 4185 */                                         _jspx_th_c_005fif_005f16.setParent(null);
/*      */                                         
/* 4187 */                                         _jspx_th_c_005fif_005f16.setTest("${isMetaSpace}");
/* 4188 */                                         int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 4189 */                                         if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                                           for (;;) {
/* 4191 */                                             out.write("     \n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 4192 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.metaspace.text"));
/* 4193 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4194 */                                             out.print(memoryProps.get("MAXMSPACE"));
/* 4195 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4196 */                                             out.print(memoryProps.get("COMMITMSPACE"));
/* 4197 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4198 */                                             out.print(memoryProps.get("INITMSPACE"));
/* 4199 */                                             out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4200 */                                             out.print(memoryProps.get("MSPACE"));
/* 4201 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\"align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4202 */                                             out.print(resourceid);
/* 4203 */                                             out.write("&attributeid=3689')\">");
/* 4204 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3689")));
/* 4205 */                                             out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">");
/* 4206 */                                             out.print(memoryProps.get("MSPACEPER"));
/* 4207 */                                             out.write("</td>\n\t<td class=\"whitegrayborder\"align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4208 */                                             out.print(resourceid);
/* 4209 */                                             out.write("&attributeid=3690')\">");
/* 4210 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3690")));
/* 4211 */                                             out.write("</a></td>\n\t</tr>\n\n\t<tr> \n\t<td class=\"whitegrayborderbr\">");
/* 4212 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.Compressed.text"));
/* 4213 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4214 */                                             out.print(memoryProps.get("MAXCCSPACE"));
/* 4215 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4216 */                                             out.print(memoryProps.get("COMMITCCSPACE"));
/* 4217 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4218 */                                             out.print(memoryProps.get("INITCCSPACE"));
/* 4219 */                                             out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4220 */                                             out.print(memoryProps.get("CCSPACE"));
/* 4221 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\"align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4222 */                                             out.print(resourceid);
/* 4223 */                                             out.write("&attributeid=3691')\">");
/* 4224 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3691")));
/* 4225 */                                             out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">");
/* 4226 */                                             out.print(memoryProps.get("CCSPACEPER"));
/* 4227 */                                             out.write("</td>\n\t<td class=\"whitegrayborder\"align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4228 */                                             out.print(resourceid);
/* 4229 */                                             out.write("&attributeid=3692')\">");
/* 4230 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3692")));
/* 4231 */                                             out.write("</a></td>\n\t</tr>\n\t");
/* 4232 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 4233 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4237 */                                         if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 4238 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                                         }
/*      */                                         
/* 4241 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4242 */                                         out.write(32);
/*      */                                         
/* 4244 */                                         IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4245 */                                         _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 4246 */                                         _jspx_th_c_005fif_005f17.setParent(null);
/*      */                                         
/* 4248 */                                         _jspx_th_c_005fif_005f17.setTest("${not isMetaSpace}");
/* 4249 */                                         int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 4250 */                                         if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                           for (;;) {
/* 4252 */                                             out.write("\n         \n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4253 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.permgen.text"));
/* 4254 */                                             out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4255 */                                             out.print(memoryProps.get("maxpermgen"));
/* 4256 */                                             out.write("</td>\n\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4257 */                                             out.print(memoryProps.get("commitpermgen"));
/* 4258 */                                             out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4259 */                                             out.print(memoryProps.get("initpermgen"));
/* 4260 */                                             out.write("</td>\n\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4261 */                                             out.print(memoryProps.get("permgen"));
/* 4262 */                                             out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4263 */                                             out.print(resourceid);
/* 4264 */                                             out.write("&attributeid=3616')\">");
/* 4265 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3616")));
/* 4266 */                                             out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">");
/* 4267 */                                             out.print(memoryProps.get("permgenper"));
/* 4268 */                                             out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4269 */                                             out.print(resourceid);
/* 4270 */                                             out.write("&attributeid=3631')\">");
/* 4271 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3631")));
/* 4272 */                                             out.write("</a></td>\n\t</tr>\n\n\n\t<tr class=\"whitegrayborderbr\">\n\t<td class=\"whitegrayborderbr\">");
/* 4273 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.permgenro.text"));
/* 4274 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4275 */                                             out.print(memoryProps.get("maxpermgenro"));
/* 4276 */                                             out.write("</td>\n\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4277 */                                             out.print(memoryProps.get("commitpermgrnro"));
/* 4278 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4279 */                                             out.print(memoryProps.get("initpermgrnro"));
/* 4280 */                                             out.write("</td>\n\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4281 */                                             out.print(memoryProps.get("permgenro"));
/* 4282 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4283 */                                             out.print(resourceid);
/* 4284 */                                             out.write("&attributeid=3617')\">");
/* 4285 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3617")));
/* 4286 */                                             out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">");
/* 4287 */                                             out.print(memoryProps.get("permgenroper"));
/* 4288 */                                             out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4289 */                                             out.print(resourceid);
/* 4290 */                                             out.write("&attributeid=3617')\">");
/* 4291 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3632")));
/* 4292 */                                             out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4293 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.permgenrw.text"));
/* 4294 */                                             out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4295 */                                             out.print(memoryProps.get("maxpermgenrw"));
/* 4296 */                                             out.write("</td>\n\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4297 */                                             out.print(memoryProps.get("commitpermgrnrw"));
/* 4298 */                                             out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4299 */                                             out.print(memoryProps.get("initpermgrnrw"));
/* 4300 */                                             out.write("</td>\n\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4301 */                                             out.print(memoryProps.get("permgenrw"));
/* 4302 */                                             out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4303 */                                             out.print(resourceid);
/* 4304 */                                             out.write("&attributeid=3618')\">");
/* 4305 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3618")));
/* 4306 */                                             out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">");
/* 4307 */                                             out.print(memoryProps.get("permgenrwper"));
/* 4308 */                                             out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4309 */                                             out.print(resourceid);
/* 4310 */                                             out.write("&attributeid=3618')\">");
/* 4311 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3633")));
/* 4312 */                                             out.write("</a></td>\n\t</tr>\n\t\n\t");
/* 4313 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 4314 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4318 */                                         if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 4319 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                                         }
/*      */                                         
/* 4322 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4323 */                                         out.write("\n\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 4324 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.codecache.text"));
/* 4325 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4326 */                                         out.print(memoryProps.get("maxcodecache"));
/* 4327 */                                         out.write("</td>\n\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4328 */                                         out.print(memoryProps.get("commitcodecache"));
/* 4329 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4330 */                                         out.print(memoryProps.get("initcodecache"));
/* 4331 */                                         out.write("</td>\n\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4332 */                                         out.print(memoryProps.get("codecache"));
/* 4333 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\"align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4334 */                                         out.print(resourceid);
/* 4335 */                                         out.write("&attributeid=3619')\">");
/* 4336 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3619")));
/* 4337 */                                         out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">");
/* 4338 */                                         out.print(memoryProps.get("codecacheper"));
/* 4339 */                                         out.write("</td>\n\t<td class=\"whitegrayborder\"align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4340 */                                         out.print(resourceid);
/* 4341 */                                         out.write("&attributeid=3619')\">");
/* 4342 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3634")));
/* 4343 */                                         out.write("</a></td>\n\t</tr>\n\t<tr>\n\t");
/*      */                                         
/* 4345 */                                         IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4346 */                                         _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 4347 */                                         _jspx_th_c_005fif_005f18.setParent(null);
/*      */                                         
/* 4349 */                                         _jspx_th_c_005fif_005f18.setTest("${isMetaSpace}");
/* 4350 */                                         int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 4351 */                                         if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                                           for (;;) {
/* 4353 */                                             out.write("\n\t<td colspan=\"8\" align=\"right\" ><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4354 */                                             out.print(resourceid);
/* 4355 */                                             out.write("&attributeIDs=3615,3619,3630,3634,3689,3690,3691,3692&attributeToSelect=3615&redirectto=");
/* 4356 */                                             out.print(encodeurl);
/* 4357 */                                             out.write("\" class=\"staticlinks\">");
/* 4358 */                                             out.print(ALERTCONFIG_TEXT);
/* 4359 */                                             out.write("</a>&nbsp;</td>\n\t");
/* 4360 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 4361 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4365 */                                         if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 4366 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                                         }
/*      */                                         
/* 4369 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/*      */                                         
/* 4371 */                                         IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4372 */                                         _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 4373 */                                         _jspx_th_c_005fif_005f19.setParent(null);
/*      */                                         
/* 4375 */                                         _jspx_th_c_005fif_005f19.setTest("${not isMetaSpace}");
/* 4376 */                                         int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 4377 */                                         if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                                           for (;;) {
/* 4379 */                                             out.write("\n\t<td colspan=\"8\" align=\"right\" ><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4380 */                                             out.print(resourceid);
/* 4381 */                                             out.write("&attributeIDs=3615,3630,3616,3631,3617,3632,3618,3633,3619,3634&attributeToSelect=3615&redirectto=");
/* 4382 */                                             out.print(encodeurl);
/* 4383 */                                             out.write("\" class=\"staticlinks\">");
/* 4384 */                                             out.print(ALERTCONFIG_TEXT);
/* 4385 */                                             out.write("</a>&nbsp;</td>\n\t");
/* 4386 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 4387 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4391 */                                         if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 4392 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                                         }
/*      */                                         
/* 4395 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 4396 */                                         out.write("\n\t</tr>\n\t</table>\n\n</td>\n</tr>\n</table>\n\n");
/*      */                                       }
/* 4398 */                                       out.write("\n\n\n\n");
/* 4399 */                                       if ((vendor.contains("IBM")) || (vendor.contains("BEA")) || (vendor.contains("Oracle"))) {
/* 4400 */                                         out.write("\n\n<table width=\"99%\" height=\"299\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t<tr>\n\t\t<td colspan=\"2\" height=\"31\" class=\"tableheadingbborder\">");
/* 4401 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.heapmemory.text"));
/* 4402 */                                         out.write("\n\t\t</td>\n\t</tr>\n\t<tr>\n\t");
/*      */                                         
/* 4404 */                                         if (vendor.contains("IBM")) {
/* 4405 */                                           jdk15Graph.setParameter(resourceid, "ibmheap");
/*      */                                         } else {
/* 4407 */                                           jdk15Graph.setParameter(resourceid, "beaheap");
/*      */                                         }
/*      */                                         
/* 4410 */                                         out.write("\n\t\t<td width=\"100%\" height=\"64\" colspan=\"2\" >\n\t\t\t<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t\t  <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 4411 */                                         if (_jspx_meth_c_005fout_005f45(_jspx_page_context))
/*      */                                           return;
/* 4413 */                                         out.write("&attributeid=3611&period=-7&resourcename=");
/* 4414 */                                         if (_jspx_meth_c_005fout_005f46(_jspx_page_context))
/*      */                                           return;
/* 4416 */                                         out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4417 */                                         out.print(seven_days_text);
/* 4418 */                                         out.write("\"></a></td>\n\t\t\t\t\t\t  <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 4419 */                                         if (_jspx_meth_c_005fout_005f47(_jspx_page_context))
/*      */                                           return;
/* 4421 */                                         out.write("&attributeid=3611&period=-30&resourcename=");
/* 4422 */                                         if (_jspx_meth_c_005fout_005f48(_jspx_page_context))
/*      */                                           return;
/* 4424 */                                         out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4425 */                                         out.print(thiry_days_text);
/* 4426 */                                         out.write("\"></a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr align=\"center\">\n\t\t\t\t\t<td colspan=\"2\"> \n\t\t\t\t\t");
/*      */                                         
/* 4428 */                                         TimeChart _jspx_th_awolf_005ftimechart_005f6 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4429 */                                         _jspx_th_awolf_005ftimechart_005f6.setPageContext(_jspx_page_context);
/* 4430 */                                         _jspx_th_awolf_005ftimechart_005f6.setParent(null);
/*      */                                         
/* 4432 */                                         _jspx_th_awolf_005ftimechart_005f6.setDataSetProducer("jdk15Graph");
/*      */                                         
/* 4434 */                                         _jspx_th_awolf_005ftimechart_005f6.setWidth("550");
/*      */                                         
/* 4436 */                                         _jspx_th_awolf_005ftimechart_005f6.setHeight("225");
/*      */                                         
/* 4438 */                                         _jspx_th_awolf_005ftimechart_005f6.setLegend("true");
/*      */                                         
/* 4440 */                                         _jspx_th_awolf_005ftimechart_005f6.setXaxisLabel(xaxis_time);
/*      */                                         
/* 4442 */                                         _jspx_th_awolf_005ftimechart_005f6.setYaxisLabel(yaxis_memory);
/* 4443 */                                         int _jspx_eval_awolf_005ftimechart_005f6 = _jspx_th_awolf_005ftimechart_005f6.doStartTag();
/* 4444 */                                         if (_jspx_eval_awolf_005ftimechart_005f6 != 0) {
/* 4445 */                                           if (_jspx_eval_awolf_005ftimechart_005f6 != 1) {
/* 4446 */                                             out = _jspx_page_context.pushBody();
/* 4447 */                                             _jspx_th_awolf_005ftimechart_005f6.setBodyContent((BodyContent)out);
/* 4448 */                                             _jspx_th_awolf_005ftimechart_005f6.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4451 */                                             out.write("\n\t\t\t\t\t");
/* 4452 */                                             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f6.doAfterBody();
/* 4453 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4456 */                                           if (_jspx_eval_awolf_005ftimechart_005f6 != 1) {
/* 4457 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4460 */                                         if (_jspx_th_awolf_005ftimechart_005f6.doEndTag() == 5) {
/* 4461 */                                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f6); return;
/*      */                                         }
/*      */                                         
/* 4464 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f6);
/* 4465 */                                         out.write("</td>");
/* 4466 */                                         out.write("\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n<tr>\n\t<td align=\"center\">\n\t<br>\n\t<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t<tr>\n\t<td class=\"columnheadingtb\">");
/* 4467 */                                         out.print(FormatUtil.getString("table.heading.attribute"));
/* 4468 */                                         out.write("</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4469 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.maxvalue.text"));
/* 4470 */                                         out.write("&nbsp;(");
/* 4471 */                                         out.print(FormatUtil.getString("MB"));
/* 4472 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4473 */                                         out.print(FormatUtil.getString("am.javaruntime.committed"));
/* 4474 */                                         out.write("&nbsp;(");
/* 4475 */                                         out.print(FormatUtil.getString("MB"));
/* 4476 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4477 */                                         out.print(FormatUtil.getString("am.javaruntime.initial"));
/* 4478 */                                         out.write("&nbsp;(");
/* 4479 */                                         out.print(FormatUtil.getString("MB"));
/* 4480 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4481 */                                         out.print(FormatUtil.getString("am.javaruntime.used"));
/* 4482 */                                         out.write("&nbsp;(");
/* 4483 */                                         out.print(FormatUtil.getString("MB"));
/* 4484 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4485 */                                         out.print(FormatUtil.getString("table.heading.status"));
/* 4486 */                                         out.write("</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4487 */                                         out.print(FormatUtil.getString("am.javaruntime.used"));
/* 4488 */                                         out.write("&nbsp;(&#37;)</td>\n\t<td class=\"columnheadingtb\">");
/* 4489 */                                         out.print(FormatUtil.getString("table.heading.status"));
/* 4490 */                                         out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 4491 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.totalheap.text"));
/* 4492 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4493 */                                         out.print(memoryProps.get("maxheap"));
/* 4494 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4495 */                                         out.print(memoryProps.get("commitheap"));
/* 4496 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4497 */                                         out.print(memoryProps.get("initialheap"));
/* 4498 */                                         out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4499 */                                         out.print(memoryProps.get("heap"));
/* 4500 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4501 */                                         out.print(resourceid);
/* 4502 */                                         out.write("&attributeid=3611')\">");
/* 4503 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3611")));
/* 4504 */                                         out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">\n\t");
/* 4505 */                                         if (((Long)memoryProps.get("heapper")).longValue() != -1L) {
/* 4506 */                                           out.write(10);
/* 4507 */                                           out.write(9);
/* 4508 */                                           out.print(memoryProps.get("heapper"));
/* 4509 */                                           out.write(10);
/* 4510 */                                           out.write(9);
/*      */                                         } else {
/* 4512 */                                           out.write(45);
/*      */                                         }
/* 4514 */                                         out.write("\n\t</td>\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4515 */                                         out.print(resourceid);
/* 4516 */                                         out.write("&attributeid=3626')\">");
/* 4517 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3626")));
/* 4518 */                                         out.write("</a></td>\n\t</tr>\n");
/* 4519 */                                         if (vendor.contains("IBM")) {
/* 4520 */                                           out.write("\t\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4521 */                                           out.print(FormatUtil.getString("am.webclient.javaruntime.javaheap.text1"));
/* 4522 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4523 */                                           out.print(memoryProps.get("MAXJAVAHEAP"));
/* 4524 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4525 */                                           out.print(memoryProps.get("COMMITJAVAHEAP"));
/* 4526 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4527 */                                           out.print(memoryProps.get("INITJAVAHEAP"));
/* 4528 */                                           out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4529 */                                           out.print(memoryProps.get("JAVAHEAP"));
/* 4530 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4531 */                                           out.print(resourceid);
/* 4532 */                                           out.write("&attributeid=3678')\">");
/* 4533 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3678")));
/* 4534 */                                           out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">\n\t");
/* 4535 */                                           if (((Long)memoryProps.get("PERJAVAHEAP")).longValue() != -1L) {
/* 4536 */                                             out.write(10);
/* 4537 */                                             out.write(9);
/* 4538 */                                             out.print(memoryProps.get("PERJAVAHEAP"));
/* 4539 */                                             out.write(10);
/* 4540 */                                             out.write(9);
/*      */                                           } else {
/* 4542 */                                             out.write(45);
/*      */                                           }
/* 4544 */                                           out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4545 */                                           out.print(resourceid);
/* 4546 */                                           out.write("&attributeid=3679')\">");
/* 4547 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3679")));
/* 4548 */                                           out.write("</a></td>\n\t</tr>\n");
/*      */                                         }
/* 4550 */                                         out.write(9);
/* 4551 */                                         out.write(10);
/* 4552 */                                         out.write(10);
/* 4553 */                                         if ((vendor.contains("BEA")) || (vendor.contains("Oracle"))) {
/* 4554 */                                           out.write("\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4555 */                                           out.print(FormatUtil.getString("am.webclient.javaruntime.javaheap.text"));
/* 4556 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4557 */                                           out.print(memoryProps.get("MAXJJAVAHEAP"));
/* 4558 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4559 */                                           out.print(memoryProps.get("COMMITJJAVAHEAP"));
/* 4560 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4561 */                                           out.print(memoryProps.get("INITJJAVAHEAP"));
/* 4562 */                                           out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4563 */                                           out.print(memoryProps.get("JJAVAHEAP"));
/* 4564 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4565 */                                           out.print(resourceid);
/* 4566 */                                           out.write("&attributeid=3676')\">");
/* 4567 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3676")));
/* 4568 */                                           out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\"> \n\t\t");
/* 4569 */                                           if (((Long)memoryProps.get("PERJJAVAHEAP")).longValue() != -1L) {
/* 4570 */                                             out.write(10);
/* 4571 */                                             out.write(9);
/* 4572 */                                             out.print(memoryProps.get("PERJJAVAHEAP"));
/* 4573 */                                             out.write(10);
/* 4574 */                                             out.write(9);
/*      */                                           } else {
/* 4576 */                                             out.write(45);
/*      */                                           }
/* 4578 */                                           out.write("\n\t\n\t</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4579 */                                           out.print(resourceid);
/* 4580 */                                           out.write("&attributeid=3677')\">");
/* 4581 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3677")));
/* 4582 */                                           out.write("</a></td>\n\t</tr>\n\t \n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4583 */                                           out.print(FormatUtil.getString("am.webclient.javaruntime.nursery.text"));
/* 4584 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4585 */                                           out.print(memoryProps.get("MAXNURSERY"));
/* 4586 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4587 */                                           out.print(memoryProps.get("COMMITNURSERY"));
/* 4588 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4589 */                                           out.print(memoryProps.get("INITNURSERY"));
/* 4590 */                                           out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4591 */                                           out.print(memoryProps.get("NURSERY"));
/* 4592 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4593 */                                           out.print(resourceid);
/* 4594 */                                           out.write("&attributeid=3680')\">");
/* 4595 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3680")));
/* 4596 */                                           out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">\n\t\t\t");
/* 4597 */                                           if (((Long)memoryProps.get("PERNURSERY")).longValue() != -1L) {
/* 4598 */                                             out.write(10);
/* 4599 */                                             out.write(9);
/* 4600 */                                             out.print(memoryProps.get("PERNURSERY"));
/* 4601 */                                             out.write(10);
/* 4602 */                                             out.write(9);
/*      */                                           } else {
/* 4604 */                                             out.write(45);
/*      */                                           }
/* 4606 */                                           out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4607 */                                           out.print(resourceid);
/* 4608 */                                           out.write("&attributeid=3681')\">");
/* 4609 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3681")));
/* 4610 */                                           out.write("</a></td>\n\t</tr>\n\t \n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4611 */                                           out.print(FormatUtil.getString("am.webclient.javaruntime.oldspace.text"));
/* 4612 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4613 */                                           out.print(memoryProps.get("MAXOLDSPACE"));
/* 4614 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4615 */                                           out.print(memoryProps.get("COMMITOLDSPACE"));
/* 4616 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4617 */                                           out.print(memoryProps.get("INITOLDSPACE"));
/* 4618 */                                           out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4619 */                                           out.print(memoryProps.get("OLDSPACE"));
/* 4620 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4621 */                                           out.print(resourceid);
/* 4622 */                                           out.write("&attributeid=3683')\">");
/* 4623 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3683")));
/* 4624 */                                           out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">\n\t");
/* 4625 */                                           if (((Long)memoryProps.get("PEROLDSPACE")).longValue() != -1L) {
/* 4626 */                                             out.write(10);
/* 4627 */                                             out.write(9);
/* 4628 */                                             out.write(9);
/* 4629 */                                             out.print(memoryProps.get("PEROLDSPACE"));
/* 4630 */                                             out.write(10);
/* 4631 */                                             out.write(9);
/*      */                                           } else {
/* 4633 */                                             out.write(45);
/*      */                                           }
/* 4635 */                                           out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4636 */                                           out.print(resourceid);
/* 4637 */                                           out.write("&attributeid=3684')\">");
/* 4638 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3684")));
/* 4639 */                                           out.write("</a></td>\n\t</tr>\n\t \n");
/*      */                                         }
/* 4641 */                                         out.write("\n\n\t<tr>\n\t");
/* 4642 */                                         if (vendor.contains("IBM")) {
/* 4643 */                                           out.write("\n\t\t<td colspan=\"8\" align=\"right\" ><img style=\"position:relative; top:1px; \" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4644 */                                           out.print(resourceid);
/* 4645 */                                           out.write("&attributeIDs=3611,3678,3626,3679&attributeToSelect=3611&redirectto=");
/* 4646 */                                           out.print(encodeurl);
/* 4647 */                                           out.write("\" class=\"staticlinks\">");
/* 4648 */                                           out.print(ALERTCONFIG_TEXT);
/* 4649 */                                           out.write("</a>&nbsp;</td>\n\t");
/*      */                                         } else {
/* 4651 */                                           out.write("\n\t\t<td colspan=\"8\" align=\"right\" ><img style=\"position:relative; top:1px; \" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4652 */                                           out.print(resourceid);
/* 4653 */                                           out.write("&attributeIDs=3611,3676,3680,3683,3626,3677,3681,3684&attributeToSelect=3611&redirectto=");
/* 4654 */                                           out.print(encodeurl);
/* 4655 */                                           out.write("\" class=\"staticlinks\">");
/* 4656 */                                           out.print(ALERTCONFIG_TEXT);
/* 4657 */                                           out.write("</a>&nbsp;</td>\n\t");
/*      */                                         }
/* 4659 */                                         out.write("\n\t\n\t</tr>\n\t</table>\n\n</td>\n</tr>\n</table>\n\n\n<br>\n<table width=\"99%\" height=\"299\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" height=\"31\" class=\"tableheadingbborder\">");
/* 4660 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.nonheapmemory.text"));
/* 4661 */                                         out.write("\n</td>\n</tr>\n<tr>\n");
/*      */                                         
/* 4663 */                                         if (vendor.contains("IBM")) {
/* 4664 */                                           jdk15Graph.setParameter(resourceid, "ibmnonheap");
/*      */                                         } else {
/* 4666 */                                           jdk15Graph.setParameter(resourceid, "beanonheap");
/*      */                                         }
/*      */                                         
/* 4669 */                                         out.write("\n<td width=\"100%\" height=\"64\" colspan=\"2\" >\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n          <tr>\n\t              <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 4670 */                                         if (_jspx_meth_c_005fout_005f49(_jspx_page_context))
/*      */                                           return;
/* 4672 */                                         out.write("&attributeid=3615&period=-7&resourcename=");
/* 4673 */                                         if (_jspx_meth_c_005fout_005f50(_jspx_page_context))
/*      */                                           return;
/* 4675 */                                         out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4676 */                                         out.print(seven_days_text);
/* 4677 */                                         out.write("\"></a></td>\n\t\t                  <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 4678 */                                         if (_jspx_meth_c_005fout_005f51(_jspx_page_context))
/*      */                                           return;
/* 4680 */                                         out.write("&attributeid=3615&period=-30&resourcename=");
/* 4681 */                                         if (_jspx_meth_c_005fout_005f52(_jspx_page_context))
/*      */                                           return;
/* 4683 */                                         out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4684 */                                         out.print(thiry_days_text);
/* 4685 */                                         out.write("\"></a></td>\n\t</tr>\n\t<tr align=\"center\">\n\t<td colspan=\"2\"> ");
/*      */                                         
/* 4687 */                                         TimeChart _jspx_th_awolf_005ftimechart_005f7 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4688 */                                         _jspx_th_awolf_005ftimechart_005f7.setPageContext(_jspx_page_context);
/* 4689 */                                         _jspx_th_awolf_005ftimechart_005f7.setParent(null);
/*      */                                         
/* 4691 */                                         _jspx_th_awolf_005ftimechart_005f7.setDataSetProducer("jdk15Graph");
/*      */                                         
/* 4693 */                                         _jspx_th_awolf_005ftimechart_005f7.setWidth("550");
/*      */                                         
/* 4695 */                                         _jspx_th_awolf_005ftimechart_005f7.setHeight("225");
/*      */                                         
/* 4697 */                                         _jspx_th_awolf_005ftimechart_005f7.setLegend("true");
/*      */                                         
/* 4699 */                                         _jspx_th_awolf_005ftimechart_005f7.setXaxisLabel(xaxis_time);
/*      */                                         
/* 4701 */                                         _jspx_th_awolf_005ftimechart_005f7.setYaxisLabel(yaxis_memory);
/* 4702 */                                         int _jspx_eval_awolf_005ftimechart_005f7 = _jspx_th_awolf_005ftimechart_005f7.doStartTag();
/* 4703 */                                         if (_jspx_eval_awolf_005ftimechart_005f7 != 0) {
/* 4704 */                                           if (_jspx_eval_awolf_005ftimechart_005f7 != 1) {
/* 4705 */                                             out = _jspx_page_context.pushBody();
/* 4706 */                                             _jspx_th_awolf_005ftimechart_005f7.setBodyContent((BodyContent)out);
/* 4707 */                                             _jspx_th_awolf_005ftimechart_005f7.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4710 */                                             out.write(10);
/* 4711 */                                             out.write(9);
/* 4712 */                                             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f7.doAfterBody();
/* 4713 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4716 */                                           if (_jspx_eval_awolf_005ftimechart_005f7 != 1) {
/* 4717 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4720 */                                         if (_jspx_th_awolf_005ftimechart_005f7.doEndTag() == 5) {
/* 4721 */                                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f7); return;
/*      */                                         }
/*      */                                         
/* 4724 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f7);
/* 4725 */                                         out.write(" </td>");
/* 4726 */                                         out.write("\n\t</tr>\n\t</table>\n</td>\n</tr><tr>\n\t<td align=\"center\"><br>\n\t<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t<tr>\n\t<td class=\"columnheadingtb\">");
/* 4727 */                                         out.print(FormatUtil.getString("table.heading.attribute"));
/* 4728 */                                         out.write("</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4729 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.maxvalue.text"));
/* 4730 */                                         out.write("&nbsp;(");
/* 4731 */                                         out.print(FormatUtil.getString("MB"));
/* 4732 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4733 */                                         out.print(FormatUtil.getString("am.javaruntime.committed"));
/* 4734 */                                         out.write("&nbsp;(");
/* 4735 */                                         out.print(FormatUtil.getString("MB"));
/* 4736 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4737 */                                         out.print(FormatUtil.getString("am.javaruntime.initial"));
/* 4738 */                                         out.write("&nbsp;(");
/* 4739 */                                         out.print(FormatUtil.getString("MB"));
/* 4740 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4741 */                                         out.print(FormatUtil.getString("am.javaruntime.used"));
/* 4742 */                                         out.write("&nbsp;(");
/* 4743 */                                         out.print(FormatUtil.getString("MB"));
/* 4744 */                                         out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4745 */                                         out.print(FormatUtil.getString("table.heading.status"));
/* 4746 */                                         out.write("</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 4747 */                                         out.print(FormatUtil.getString("am.javaruntime.used"));
/* 4748 */                                         out.write("&nbsp;(&#37;)</td>\n\t<td class=\"columnheadingtb\">");
/* 4749 */                                         out.print(FormatUtil.getString("table.heading.status"));
/* 4750 */                                         out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 4751 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.nonheapmemory.text"));
/* 4752 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4753 */                                         out.print(memoryProps.get("maxnonheap"));
/* 4754 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4755 */                                         out.print(memoryProps.get("commitnonheap"));
/* 4756 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4757 */                                         out.print(memoryProps.get("initialnonheap"));
/* 4758 */                                         out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4759 */                                         out.print(memoryProps.get("nonheap"));
/* 4760 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4761 */                                         out.print(resourceid);
/* 4762 */                                         out.write("&attributeid=3615')\">");
/* 4763 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3615")));
/* 4764 */                                         out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">\n\t");
/*      */                                         
/* 4766 */                                         Long nonheapperval = Long.valueOf(-1L);
/*      */                                         try {
/* 4768 */                                           nonheapperval = Long.valueOf((memoryProps.get("nonheapper") instanceof Long) ? ((Long)memoryProps.get("nonheapper")).longValue() : ((Integer)memoryProps.get("nonheapper")).intValue());
/* 4769 */                                         } catch (Exception ex) { ex.printStackTrace(); }
/* 4770 */                                         if (nonheapperval.longValue() != -1L)
/*      */                                         {
/* 4772 */                                           out.write(10);
/* 4773 */                                           out.write(9);
/* 4774 */                                           out.print(memoryProps.get("nonheapper"));
/* 4775 */                                           out.write(10);
/* 4776 */                                           out.write(9);
/*      */                                         } else {
/* 4778 */                                           out.write(45);
/*      */                                         }
/* 4780 */                                         out.write("\n\t</td>\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4781 */                                         out.print(resourceid);
/* 4782 */                                         out.write("&attributeid=3630')\">");
/* 4783 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3630")));
/* 4784 */                                         out.write("</a></td>\n\t</tr>\n");
/* 4785 */                                         if (vendor.contains("IBM")) {
/* 4786 */                                           out.write("\t\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4787 */                                           out.print(FormatUtil.getString("am.webclient.javaruntime.jitcodecache.text"));
/* 4788 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4789 */                                           out.print(memoryProps.get("MAXJITCCACHE"));
/* 4790 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4791 */                                           out.print(memoryProps.get("COMMITJITCCACHE"));
/* 4792 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4793 */                                           out.print(memoryProps.get("INITJITCCACHE"));
/* 4794 */                                           out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4795 */                                           out.print(memoryProps.get("JITCCACHE"));
/* 4796 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4797 */                                           out.print(resourceid);
/* 4798 */                                           out.write("&attributeid=3668')\">");
/* 4799 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3668")));
/* 4800 */                                           out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">\n\t ");
/* 4801 */                                           if (((Long)memoryProps.get("PERJITCCACHE")).longValue() != -1L) {
/* 4802 */                                             out.write(10);
/* 4803 */                                             out.write(9);
/* 4804 */                                             out.print(memoryProps.get("PERJITCCACHE"));
/* 4805 */                                             out.write(10);
/* 4806 */                                             out.write(9);
/*      */                                           } else {
/* 4808 */                                             out.write(45);
/*      */                                           }
/* 4810 */                                           out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4811 */                                           out.print(resourceid);
/* 4812 */                                           out.write("&attributeid=3669')\">");
/* 4813 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3669")));
/* 4814 */                                           out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 4815 */                                           out.print(FormatUtil.getString("am.webclient.javaruntime.jitdatacache.text"));
/* 4816 */                                           out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4817 */                                           out.print(memoryProps.get("MAXJITDCACHE"));
/* 4818 */                                           out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4819 */                                           out.print(memoryProps.get("COMMITJITDCACHE"));
/* 4820 */                                           out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4821 */                                           out.print(memoryProps.get("INITJITDCACHE"));
/* 4822 */                                           out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4823 */                                           out.print(memoryProps.get("JITDCACHE"));
/* 4824 */                                           out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4825 */                                           out.print(resourceid);
/* 4826 */                                           out.write("&attributeid=3670')\">");
/* 4827 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3670")));
/* 4828 */                                           out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">\n\t");
/* 4829 */                                           if (((Long)memoryProps.get("PERJITDCACHE")).longValue() != -1L) {
/* 4830 */                                             out.write(10);
/* 4831 */                                             out.write(9);
/* 4832 */                                             out.print(memoryProps.get("PERJITDCACHE"));
/* 4833 */                                             out.write(10);
/* 4834 */                                             out.write(9);
/*      */                                           } else {
/* 4836 */                                             out.write(45);
/*      */                                           }
/* 4838 */                                           out.write("\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4839 */                                           out.print(resourceid);
/* 4840 */                                           out.write("&attributeid=3671')\">");
/* 4841 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3671")));
/* 4842 */                                           out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4843 */                                           out.print(FormatUtil.getString("am.webclient.javaruntime.classstorage.text"));
/* 4844 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4845 */                                           out.print(memoryProps.get("MAXCLASSSTOR"));
/* 4846 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4847 */                                           out.print(memoryProps.get("COMMITCLASSSTOR"));
/* 4848 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4849 */                                           out.print(memoryProps.get("INITCLASSSTOR"));
/* 4850 */                                           out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4851 */                                           out.print(memoryProps.get("CLASSSTOR"));
/* 4852 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4853 */                                           out.print(resourceid);
/* 4854 */                                           out.write("&attributeid=3672')\">");
/* 4855 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3672")));
/* 4856 */                                           out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">\n\t");
/* 4857 */                                           if (((Long)memoryProps.get("PERCLASSSTOR")).longValue() != -1L) {
/* 4858 */                                             out.write(10);
/* 4859 */                                             out.write(9);
/* 4860 */                                             out.print(memoryProps.get("PERCLASSSTOR"));
/* 4861 */                                             out.write(10);
/* 4862 */                                             out.write(9);
/*      */                                           } else {
/* 4864 */                                             out.write(45);
/*      */                                           }
/* 4866 */                                           out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4867 */                                           out.print(resourceid);
/* 4868 */                                           out.write("&attributeid=3673')\">");
/* 4869 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3673")));
/* 4870 */                                           out.write("</a></td>\n\t</tr>\n\t\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4871 */                                           out.print(FormatUtil.getString("am.webclient.javaruntime.nonheapstorage.text"));
/* 4872 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4873 */                                           out.print(memoryProps.get("MAXNONHPSTOR"));
/* 4874 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4875 */                                           out.print(memoryProps.get("COMMITNONHPSTOR"));
/* 4876 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4877 */                                           out.print(memoryProps.get("INITNONHPSTOR"));
/* 4878 */                                           out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4879 */                                           out.print(memoryProps.get("NONHPSTOR"));
/* 4880 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4881 */                                           out.print(resourceid);
/* 4882 */                                           out.write("&attributeid=3674')\">");
/* 4883 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3674")));
/* 4884 */                                           out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">\n\t\t");
/* 4885 */                                           if (((Long)memoryProps.get("PERNONHPSTOR")).longValue() != -1L) {
/* 4886 */                                             out.write(10);
/* 4887 */                                             out.write(9);
/* 4888 */                                             out.print(memoryProps.get("PERNONHPSTOR"));
/* 4889 */                                             out.write(10);
/* 4890 */                                             out.write(9);
/*      */                                           } else {
/* 4892 */                                             out.write(45);
/*      */                                           }
/* 4894 */                                           out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4895 */                                           out.print(resourceid);
/* 4896 */                                           out.write("&attributeid=3675')\">");
/* 4897 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3675")));
/* 4898 */                                           out.write("</a></td>\n\t</tr>\n");
/*      */                                         }
/* 4900 */                                         out.write(10);
/* 4901 */                                         if ((vendor.contains("BEA")) || (vendor.contains("Oracle"))) {
/* 4902 */                                           out.write("\n<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 4903 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.classmemory.text"));
/* 4904 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4905 */                                           out.print(memoryProps.get("MAXCLASSMEM"));
/* 4906 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4907 */                                           out.print(memoryProps.get("COMMITCLASSMEM"));
/* 4908 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4909 */                                           out.print(memoryProps.get("INITCLASSMEM"));
/* 4910 */                                           out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4911 */                                           out.print(memoryProps.get("CLASSMEM"));
/* 4912 */                                           out.write("</td>\n\t<td class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4913 */                                           out.print(resourceid);
/* 4914 */                                           out.write("&attributeid=3685')\">");
/* 4915 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3685")));
/* 4916 */                                           out.write("</a></td>\n\t<td class=\"yellowgrayborder\" align=\"right\">\n\t ");
/* 4917 */                                           if (((Long)memoryProps.get("PERCLASSMEM")).longValue() != -1L) {
/* 4918 */                                             out.write(10);
/* 4919 */                                             out.write(9);
/* 4920 */                                             out.print(memoryProps.get("PERCLASSMEM"));
/* 4921 */                                             out.write(10);
/* 4922 */                                             out.write(9);
/*      */                                           } else {
/* 4924 */                                             out.write(45);
/*      */                                           }
/* 4926 */                                           out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4927 */                                           out.print(resourceid);
/* 4928 */                                           out.write("&attributeid=3686')\">");
/* 4929 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3686")));
/* 4930 */                                           out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 4931 */                                           out.print(FormatUtil.getString("am.webclient.jdk15.classblock.text"));
/* 4932 */                                           out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4933 */                                           out.print(memoryProps.get("MAXCLASSBLOCK"));
/* 4934 */                                           out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4935 */                                           out.print(memoryProps.get("COMMITCLASSBLOCK"));
/* 4936 */                                           out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"right\" style=\"padding-right:50px\">");
/* 4937 */                                           out.print(memoryProps.get("INITCLASSBLOCK"));
/* 4938 */                                           out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"right\" style=\"padding-right:25px\">");
/* 4939 */                                           out.print(memoryProps.get("CLASSBLOCK"));
/* 4940 */                                           out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4941 */                                           out.print(resourceid);
/* 4942 */                                           out.write("&attributeid=3687')\">");
/* 4943 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3687")));
/* 4944 */                                           out.write("</a></td>\n\t<td class=\"whitegrayborder\" align=\"right\">\n\t");
/* 4945 */                                           if (((Long)memoryProps.get("PERCLASSBLOCK")).longValue() != -1L) {
/* 4946 */                                             out.write(10);
/* 4947 */                                             out.write(9);
/* 4948 */                                             out.print(memoryProps.get("PERCLASSBLOCK"));
/* 4949 */                                             out.write(10);
/* 4950 */                                             out.write(9);
/*      */                                           } else {
/* 4952 */                                             out.write(45);
/*      */                                           }
/* 4954 */                                           out.write("\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4955 */                                           out.print(resourceid);
/* 4956 */                                           out.write("&attributeid=3688')\">");
/* 4957 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3688")));
/* 4958 */                                           out.write("</a></td>\n\t</tr>\n");
/*      */                                         }
/* 4960 */                                         out.write("\n \n\t<tr>\n\t\n\t");
/* 4961 */                                         if (vendor.contains("IBM")) {
/* 4962 */                                           out.write("\n\t\t<td colspan=\"8\" align=\"right\" ><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4963 */                                           out.print(resourceid);
/* 4964 */                                           out.write("&attributeIDs=3615,3668,3670,3672,3674,3630,3669,3671,3673,3675&attributeToSelect=3615&redirectto=");
/* 4965 */                                           out.print(encodeurl);
/* 4966 */                                           out.write("\" class=\"staticlinks\">");
/* 4967 */                                           out.print(ALERTCONFIG_TEXT);
/* 4968 */                                           out.write("</a>&nbsp;</td>\n\t");
/*      */                                         } else {
/* 4970 */                                           out.write("\n\t    <td colspan=\"8\" align=\"right\" ><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4971 */                                           out.print(resourceid);
/* 4972 */                                           out.write("&attributeIDs=3615,3685,3687,3630,3686,3688&attributeToSelect=3615&redirectto=");
/* 4973 */                                           out.print(encodeurl);
/* 4974 */                                           out.write("\" class=\"staticlinks\">");
/* 4975 */                                           out.print(ALERTCONFIG_TEXT);
/* 4976 */                                           out.write("</a>&nbsp;</td>\n\t");
/*      */                                         }
/* 4978 */                                         out.write("\n\t\n\t\n\t\n\t</tr>\n\t</table>\n\n</td>\n</tr>\n</table>\n");
/*      */                                       }
/* 4980 */                                       out.write("\n\n\n<br>\n");
/*      */                                       
/* 4982 */                                       long maxfiledesc = ((Long)vmProps.get("maxfiledescriptor")).longValue();
/* 4983 */                                       long openfiledesc = ((Long)vmProps.get("openfiledescriptor")).longValue();
/*      */                                       
/* 4985 */                                       out.write("\n<table class=\"lrtbdarkborder\" width=\"99%\" cellpadding=\"0\" cellspacing=\"0\">\n<tr >\n<td colspan=\"2\" class=\"tableheadingbborder\">");
/* 4986 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.vmstats.text"));
/* 4987 */                                       out.write("</td>\n</tr>\n<tr>\n<td width=\"50%\">\n\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr class=\"columnheadingb\">\n\t<td class=\"columnheadingb\">");
/* 4988 */                                       out.print(FormatUtil.getString("table.heading.attribute"));
/* 4989 */                                       out.write("</td>\n\t<td class=\"columnheadingb\">");
/* 4990 */                                       out.print(FormatUtil.getString("table.heading.value"));
/* 4991 */                                       out.write("</td>\n\t<td class=\"columnheadingb\" width=\"5%\">");
/* 4992 */                                       out.print(FormatUtil.getString("table.heading.status"));
/* 4993 */                                       out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 4994 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.classesloaded.text"));
/* 4995 */                                       out.write("</td>\n\t<td class=\"whitegrayborderbr\">");
/* 4996 */                                       out.print(vmProps.get("classesloaded"));
/* 4997 */                                       out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4998 */                                       out.print(resourceid);
/* 4999 */                                       out.write("&attributeid=3603')\">");
/* 5000 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3603")));
/* 5001 */                                       out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 5002 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.classesunloaded.text"));
/* 5003 */                                       out.write("</td>\n\t<td class=\"yellowgrayborderbr\">");
/* 5004 */                                       out.print(vmProps.get("classesunloaded"));
/* 5005 */                                       out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5006 */                                       out.print(resourceid);
/* 5007 */                                       out.write("&attributeid=3604')\">");
/* 5008 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3604")));
/* 5009 */                                       out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 5010 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.gctimepercent.text"));
/* 5011 */                                       out.write("</td>\n\t<td class=\"whitegrayborderbr\">");
/* 5012 */                                       out.print(vmProps.get("gctimeper"));
/* 5013 */                                       out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5014 */                                       out.print(resourceid);
/* 5015 */                                       out.write("&attributeid=3605')\">");
/* 5016 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3605")));
/* 5017 */                                       out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 5018 */                                       out.print(FormatUtil.getString("am.webclient.jdk15.compiletimepercent.text"));
/* 5019 */                                       out.write("</td>\n\t<td class=\"yellowgrayborderbr\">");
/* 5020 */                                       out.print(vmProps.get("compiletimeper"));
/* 5021 */                                       out.write("</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5022 */                                       out.print(resourceid);
/* 5023 */                                       out.write("&attributeid=3606')\">");
/* 5024 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3606")));
/* 5025 */                                       out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td colspan=\"3\" align=\"right\" ><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5026 */                                       out.print(resourceid);
/* 5027 */                                       out.write("&attributeIDs=3603,3604,3605,3606&attributeToSelect=3606&redirectto=");
/* 5028 */                                       out.print(encodeurl);
/* 5029 */                                       out.write("\" class=\"staticlinks\">");
/* 5030 */                                       out.print(ALERTCONFIG_TEXT);
/* 5031 */                                       out.write("</a>&nbsp;</td>\n\t</tr>\n\t</table>\n</td>\n");
/*      */                                       
/* 5033 */                                       if (maxfiledesc != -1L)
/*      */                                       {
/* 5035 */                                         out.write("\n<td valign=\"top\">\n\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t<tr class=\"columnheadingb\">\n\t<td class=\"columnheadingb\">");
/* 5036 */                                         out.print(FormatUtil.getString("table.heading.attribute"));
/* 5037 */                                         out.write("</td>\n\t<td class=\"columnheadingb\">");
/* 5038 */                                         out.print(FormatUtil.getString("table.heading.value"));
/* 5039 */                                         out.write("</td>\n\t<td class=\"columnheadingb\" width=\"5%\">");
/* 5040 */                                         out.print(FormatUtil.getString("table.heading.status"));
/* 5041 */                                         out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 5042 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.maxfiledescriptor.text"));
/* 5043 */                                         out.write("</td>\n\t<td class=\"whitegrayborderbr\">\n\t");
/*      */                                         
/* 5045 */                                         if (maxfiledesc != -1L) {
/* 5046 */                                           out.println(maxfiledesc);
/*      */                                         } else {
/* 5048 */                                           out.println(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/*      */                                         }
/*      */                                         
/* 5051 */                                         out.write("\n\t</td>\n\t<td class=\"whitegrayborder\" align=\"center\">-</td>\n\t</tr>\n\t<tr>\n\t<td class=\"yellowgrayborderbr\">");
/* 5052 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.openfiledescriptor.text"));
/* 5053 */                                         out.write("</td>\n\t<td class=\"yellowgrayborderbr\">\n\t");
/* 5054 */                                         if (openfiledesc != -1L) {
/* 5055 */                                           out.println(openfiledesc);
/*      */                                         } else {
/* 5057 */                                           out.println(FormatUtil.getString("am.webclient.common.notapplicable.text"));
/*      */                                         }
/* 5059 */                                         out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5060 */                                         out.print(resourceid);
/* 5061 */                                         out.write("&attributeid=3607')\">");
/* 5062 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3607")));
/* 5063 */                                         out.write("</a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\"><br></td>\n\t<td class=\"whitegrayborderbr\"><br></td>\n\t<td class=\"whitegrayborder\"><br></td>\n\t</tr>\n\t<tr>\n\t<td class=\"yellowgrayborderbr\"><br></td>\n\t<td class=\"yellowgrayborderbr\"><br></td>\n\t<td class=\"yellowgrayborder\"><br></td>\n\t</tr>\n\t<tr>\n\t<td colspan=\"3\" align=\"right\"><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5064 */                                         out.print(resourceid);
/* 5065 */                                         out.write("&attributeIDs=3607&attributeToSelect=3607&redirectto=");
/* 5066 */                                         out.print(encodeurl);
/* 5067 */                                         out.write("\" class=\"staticlinks\">");
/* 5068 */                                         out.print(ALERTCONFIG_TEXT);
/* 5069 */                                         out.write("</a>&nbsp;</td>\n\t</tr>\n\t</table>\n</td>\n");
/*      */                                       }
/* 5071 */                                       out.write("\n</tr>\n</table>\n<br>\n\n\n\n\n\n");
/* 5072 */                                       if ((!vendor.contains("BEA")) && (!vendor.contains("Oracle"))) {
/* 5073 */                                         out.write("\t\n<table width=\"99%\" height=\"299\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" height=\"31\" class=\"tableheadingbborder\">");
/* 5074 */                                         out.print(FormatUtil.getString("am.javaruntime.host"));
/* 5075 */                                         out.write("&nbsp;");
/* 5076 */                                         out.print(FormatUtil.getString("am.javaruntime.memory"));
/* 5077 */                                         out.write("</td>");
/* 5078 */                                         out.write("\n</tr>\n<tr>\n");
/* 5079 */                                         if (vendor.contains("Sun")) {
/* 5080 */                                           jdk15Graph.setParameter(resourceid, "hostmemory");
/*      */                                         } else {
/* 5082 */                                           jdk15Graph.setParameter(resourceid, "ibmhostmemory");
/*      */                                         }
/* 5084 */                                         out.write("\n<td width=\"100%\" height=\"64\" colspan=\"2\" >\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n\n");
/* 5085 */                                         if (reportEnabledList.contains("3659")) {
/* 5086 */                                           out.write("\n\t               <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 5087 */                                           if (_jspx_meth_c_005fout_005f53(_jspx_page_context))
/*      */                                             return;
/* 5089 */                                           out.write("&attributeid=3659&period=-7&resourcename=");
/* 5090 */                                           if (_jspx_meth_c_005fout_005f54(_jspx_page_context))
/*      */                                             return;
/* 5092 */                                           out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 5093 */                                           out.print(seven_days_text);
/* 5094 */                                           out.write("\"></a></td>\n\t\t           <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&type=JDK1.5&resourceid=");
/* 5095 */                                           if (_jspx_meth_c_005fout_005f55(_jspx_page_context))
/*      */                                             return;
/* 5097 */                                           out.write("&attributeid=3659&period=-30&resourcename=");
/* 5098 */                                           if (_jspx_meth_c_005fout_005f56(_jspx_page_context))
/*      */                                             return;
/* 5100 */                                           out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 5101 */                                           out.print(thiry_days_text);
/* 5102 */                                           out.write("\"></a></td>\n");
/*      */                                         } else {
/* 5104 */                                           out.write("\n\t\t\t\t   <td width=\"5%\"  align=\"right\">&nbsp;</td>\n\t\t\t\t   <td width=\"5%\"  align=\"right\">&nbsp;</td>\n");
/*      */                                         }
/* 5106 */                                         out.write("\n\n\t </tr>\n\t<tr align=\"center\">\n\t<td colspan=\"2\"> ");
/*      */                                         
/* 5108 */                                         TimeChart _jspx_th_awolf_005ftimechart_005f8 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 5109 */                                         _jspx_th_awolf_005ftimechart_005f8.setPageContext(_jspx_page_context);
/* 5110 */                                         _jspx_th_awolf_005ftimechart_005f8.setParent(null);
/*      */                                         
/* 5112 */                                         _jspx_th_awolf_005ftimechart_005f8.setDataSetProducer("jdk15Graph");
/*      */                                         
/* 5114 */                                         _jspx_th_awolf_005ftimechart_005f8.setWidth("550");
/*      */                                         
/* 5116 */                                         _jspx_th_awolf_005ftimechart_005f8.setHeight("225");
/*      */                                         
/* 5118 */                                         _jspx_th_awolf_005ftimechart_005f8.setLegend("true");
/*      */                                         
/* 5120 */                                         _jspx_th_awolf_005ftimechart_005f8.setXaxisLabel(xaxis_time);
/*      */                                         
/* 5122 */                                         _jspx_th_awolf_005ftimechart_005f8.setYaxisLabel(yaxis_memory);
/* 5123 */                                         int _jspx_eval_awolf_005ftimechart_005f8 = _jspx_th_awolf_005ftimechart_005f8.doStartTag();
/* 5124 */                                         if (_jspx_eval_awolf_005ftimechart_005f8 != 0) {
/* 5125 */                                           if (_jspx_eval_awolf_005ftimechart_005f8 != 1) {
/* 5126 */                                             out = _jspx_page_context.pushBody();
/* 5127 */                                             _jspx_th_awolf_005ftimechart_005f8.setBodyContent((BodyContent)out);
/* 5128 */                                             _jspx_th_awolf_005ftimechart_005f8.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5131 */                                             out.write(10);
/* 5132 */                                             out.write(9);
/* 5133 */                                             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f8.doAfterBody();
/* 5134 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5137 */                                           if (_jspx_eval_awolf_005ftimechart_005f8 != 1) {
/* 5138 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5141 */                                         if (_jspx_th_awolf_005ftimechart_005f8.doEndTag() == 5) {
/* 5142 */                                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f8); return;
/*      */                                         }
/*      */                                         
/* 5145 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f8);
/* 5146 */                                         out.write(" </td>\n\t</tr>\n\t</table>\n</td>\n</tr>\n");
/* 5147 */                                         if ((!vendor.contains("BEA")) && (!vendor.contains("Oracle"))) {
/* 5148 */                                           out.write("\t\n<tr>\n\t<td align=\"center\">\n\t<br>\n\t<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n");
/* 5149 */                                           if ((vendor.contains("IBM")) || (vendor.contains("Sun"))) {
/* 5150 */                                             out.write("\t\n\t<tr>\n\t<td class=\"columnheadingtb\">");
/* 5151 */                                             out.print(FormatUtil.getString("table.heading.attribute"));
/* 5152 */                                             out.write("</td>\n\t<td class=\"columnheadingtb\" align=\"left\">");
/* 5153 */                                             out.print(FormatUtil.getString("Value"));
/* 5154 */                                             out.write("&nbsp;(");
/* 5155 */                                             out.print(FormatUtil.getString("MB"));
/* 5156 */                                             out.write(")</td>\n\t<td class=\"columnheadingtb\" align=\"center\">");
/* 5157 */                                             out.print(FormatUtil.getString("table.heading.status"));
/* 5158 */                                             out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 5159 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.totphysicalmem.text"));
/* 5160 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"left\" style=\"padding-right:30px\">");
/* 5161 */                                             out.print(memoryProps.get("totalMemory"));
/* 5162 */                                             out.write("</td>\n \t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5163 */                                             out.print(resourceid);
/* 5164 */                                             out.write("&attributeid=3659')\">");
/* 5165 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3659")));
/* 5166 */                                             out.write("</a></td>\n \t</tr>\n    <tr>\n\t<td class=\"whitegrayborderbr\">");
/* 5167 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.freephysicalmem.text"));
/* 5168 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"left\" style=\"padding-right:30px\">");
/* 5169 */                                             out.print(memoryProps.get("freeMemory"));
/* 5170 */                                             out.write("</td>\n \t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5171 */                                             out.print(resourceid);
/* 5172 */                                             out.write("&attributeid=3660')\">");
/* 5173 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3660")));
/* 5174 */                                             out.write("</a></td>\n \t</tr>\n"); }
/* 5175 */                                           if (vendor.contains("Sun")) {
/* 5176 */                                             out.write("\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 5177 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.totswapspace.text"));
/* 5178 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"left\" style=\"padding-right:30px\">");
/* 5179 */                                             out.print(memoryProps.get("totalswapspace"));
/* 5180 */                                             out.write("</td>\n \t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5181 */                                             out.print(resourceid);
/* 5182 */                                             out.write("&attributeid=3662')\">");
/* 5183 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3662")));
/* 5184 */                                             out.write("</a></td>\n \t</tr>\n\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 5185 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.freeswapspace.text"));
/* 5186 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"left\" style=\"padding-right:30px\">");
/* 5187 */                                             out.print(memoryProps.get("freeswapspace"));
/* 5188 */                                             out.write("</td>\n \t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5189 */                                             out.print(resourceid);
/* 5190 */                                             out.write("&attributeid=3663')\">");
/* 5191 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3663")));
/* 5192 */                                             out.write("</a></td>\n \t</tr>\n\n\t<tr>\n\t<td class=\"whitegrayborderbr\">");
/* 5193 */                                             out.print(FormatUtil.getString("am.webclient.jdk15.committedvirtualmem.text"));
/* 5194 */                                             out.write("</td>\n\t<td class=\"whitegrayborderbr\" align=\"left\" style=\"padding-right:30px\">");
/* 5195 */                                             out.print(memoryProps.get("commitedvirmem"));
/* 5196 */                                             out.write("</td>\n \t<td class=\"whitegrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5197 */                                             out.print(resourceid);
/* 5198 */                                             out.write("&attributeid=3665')\">");
/* 5199 */                                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3665")));
/* 5200 */                                             out.write("</a></td>\n \t</tr>\n \t<tr>\n"); }
/* 5201 */                                           if (vendor.contains("IBM")) {
/* 5202 */                                             out.write(" \n\t<td colspan=\"8\" align=\"right\"><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5203 */                                             out.print(resourceid);
/* 5204 */                                             out.write("&attributeIDs=3659,3660&attributeToSelect=3659&redirectto=");
/* 5205 */                                             out.print(encodeurl);
/* 5206 */                                             out.write("\" class=\"staticlinks\">");
/* 5207 */                                             out.print(ALERTCONFIG_TEXT);
/* 5208 */                                             out.write("</a>&nbsp;</td>\n");
/*      */                                           } else {
/* 5210 */                                             out.write("\t\n<td colspan=\"8\" align=\"right\"><img style=\"position:relative; top:1px;\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5211 */                                             out.print(resourceid);
/* 5212 */                                             out.write("&attributeIDs=3659,3660,3662,3663,3665&attributeToSelect=3659&redirectto=");
/* 5213 */                                             out.print(encodeurl);
/* 5214 */                                             out.write("\" class=\"staticlinks\">");
/* 5215 */                                             out.print(ALERTCONFIG_TEXT);
/* 5216 */                                             out.write("</a>&nbsp;</td>\n");
/*      */                                           }
/* 5218 */                                           out.write("</tr>\n\t</table>\n</td>\n</tr>\n");
/*      */                                         }
/* 5220 */                                         out.write("\n</table>\n\n");
/*      */                                       }
/* 5222 */                                       out.write("\n\n\n\n\n<br>\n\n");
/* 5223 */                                       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 5224 */                                       DialChartSupport dialGraph = null;
/* 5225 */                                       dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 5226 */                                       if (dialGraph == null) {
/* 5227 */                                         dialGraph = new DialChartSupport();
/* 5228 */                                         _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                                       }
/* 5230 */                                       out.write(10);
/*      */                                       
/*      */                                       try
/*      */                                       {
/* 5234 */                                         String hostos = (String)systeminfo.get("HOSTOS");
/* 5235 */                                         String hostname = (String)systeminfo.get("HOSTNAME");
/* 5236 */                                         String hostid = (String)systeminfo.get("host_resid");
/* 5237 */                                         boolean isConf = false;
/* 5238 */                                         if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 5239 */                                           isConf = true;
/*      */                                         }
/* 5241 */                                         com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 5242 */                                         Properties property = new Properties();
/* 5243 */                                         if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                                         {
/* 5245 */                                           property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 5246 */                                           if ((property != null) && (property.size() > 0))
/*      */                                           {
/* 5248 */                                             String cpuid = property.getProperty("cpuid");
/* 5249 */                                             String memid = property.getProperty("memid");
/* 5250 */                                             String diskid = property.getProperty("diskid");
/* 5251 */                                             String cpuvalue = property.getProperty("CPU Utilization");
/* 5252 */                                             String memvalue = property.getProperty("Memory Utilization");
/* 5253 */                                             String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 5254 */                                             String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 5255 */                                             String diskvalue = property.getProperty("Disk Utilization");
/* 5256 */                                             String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                             
/* 5258 */                                             if (!isConf) {
/* 5259 */                                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 5260 */                                               out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 5261 */                                               out.write(45);
/* 5262 */                                               if (systeminfo.get("host_resid") != null) {
/* 5263 */                                                 out.write("<a href=\"showresource.do?resourceid=");
/* 5264 */                                                 out.print(hostid);
/* 5265 */                                                 out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5266 */                                                 out.print(hostname);
/* 5267 */                                                 out.write("</a>");
/* 5268 */                                               } else { out.println(hostname); }
/* 5269 */                                               out.write("</td>\t");
/* 5270 */                                               out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 5271 */                                               out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5272 */                                               out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                               
/*      */ 
/* 5275 */                                               if (cpuvalue != null)
/*      */                                               {
/*      */ 
/* 5278 */                                                 dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5279 */                                                 out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5280 */                                                 out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5281 */                                                 out.write(45);
/* 5282 */                                                 out.print(cpuvalue);
/* 5283 */                                                 out.write(" %'>\n\n");
/*      */                                                 
/* 5285 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5286 */                                                 _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 5287 */                                                 _jspx_th_awolf_005fdialchart_005f0.setParent(null);
/*      */                                                 
/* 5289 */                                                 _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5291 */                                                 _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                                 
/* 5293 */                                                 _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                                 
/* 5295 */                                                 _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                                 
/* 5297 */                                                 _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                                 
/* 5299 */                                                 _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                                 
/* 5301 */                                                 _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                                 
/* 5303 */                                                 _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                                 
/* 5305 */                                                 _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                                 
/* 5307 */                                                 _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 5308 */                                                 int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 5309 */                                                 if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 5310 */                                                   if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5311 */                                                     out = _jspx_page_context.pushBody();
/* 5312 */                                                     _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 5313 */                                                     _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 5316 */                                                     out.write(10);
/* 5317 */                                                     int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 5318 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 5321 */                                                   if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5322 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 5325 */                                                 if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 5326 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                                 }
/*      */                                                 
/* 5329 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 5330 */                                                 out.write("\n         </td>\n            ");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 5334 */                                                 out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5335 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5336 */                                                 out.write(32);
/* 5337 */                                                 out.write(62);
/* 5338 */                                                 out.write(10);
/* 5339 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5340 */                                                 out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                               }
/* 5342 */                                               out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5343 */                                               if (cpuvalue != null)
/*      */                                               {
/* 5345 */                                                 out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5346 */                                                 out.print(hostid);
/* 5347 */                                                 out.write("&attributeid=");
/* 5348 */                                                 out.print(cpuid);
/* 5349 */                                                 out.write("&period=-7')\" class='bodytextbold'>");
/* 5350 */                                                 out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5351 */                                                 out.write(32);
/* 5352 */                                                 out.write(45);
/* 5353 */                                                 out.write(32);
/* 5354 */                                                 out.print(cpuvalue);
/* 5355 */                                                 out.write("</a> %\n");
/*      */                                               }
/* 5357 */                                               out.write("\n  </td>\n       </tr>\n       </table>");
/* 5358 */                                               out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5359 */                                               out.write("</td>\n      <td width=\"30%\"> ");
/* 5360 */                                               out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5361 */                                               out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                               
/* 5363 */                                               if (memvalue != null)
/*      */                                               {
/*      */ 
/* 5366 */                                                 dialGraph.setValue(Long.parseLong(memvalue));
/* 5367 */                                                 out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5368 */                                                 out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5369 */                                                 out.write(45);
/* 5370 */                                                 out.print(memvalue);
/* 5371 */                                                 out.write(" %' >\n\n");
/*      */                                                 
/* 5373 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5374 */                                                 _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 5375 */                                                 _jspx_th_awolf_005fdialchart_005f1.setParent(null);
/*      */                                                 
/* 5377 */                                                 _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5379 */                                                 _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                                 
/* 5381 */                                                 _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                                 
/* 5383 */                                                 _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                                 
/* 5385 */                                                 _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                                 
/* 5387 */                                                 _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                                 
/* 5389 */                                                 _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                                 
/* 5391 */                                                 _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                                 
/* 5393 */                                                 _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                                 
/* 5395 */                                                 _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 5396 */                                                 int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 5397 */                                                 if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 5398 */                                                   if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5399 */                                                     out = _jspx_page_context.pushBody();
/* 5400 */                                                     _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 5401 */                                                     _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 5404 */                                                     out.write(32);
/* 5405 */                                                     int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 5406 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 5409 */                                                   if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5410 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 5413 */                                                 if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 5414 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                                 }
/*      */                                                 
/* 5417 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 5418 */                                                 out.write(32);
/* 5419 */                                                 out.write("\n            </td>\n            ");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 5423 */                                                 out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5424 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5425 */                                                 out.write(" >\n\n");
/* 5426 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5427 */                                                 out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                               }
/* 5429 */                                               out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5430 */                                               if (memvalue != null)
/*      */                                               {
/* 5432 */                                                 out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5433 */                                                 out.print(hostid);
/* 5434 */                                                 out.write("&attributeid=");
/* 5435 */                                                 out.print(memid);
/* 5436 */                                                 out.write("&period=-7')\" class='bodytextbold'>");
/* 5437 */                                                 out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5438 */                                                 out.write(45);
/* 5439 */                                                 out.print(memvalue);
/* 5440 */                                                 out.write("</a> %\n  ");
/*      */                                               }
/* 5442 */                                               out.write("\n  </td>\n       </tr>\n    </table>");
/* 5443 */                                               out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5444 */                                               out.write("</td>\n      <td width=\"30%\">");
/* 5445 */                                               out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5446 */                                               out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                               
/*      */ 
/* 5449 */                                               if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                               {
/*      */ 
/*      */ 
/* 5453 */                                                 dialGraph.setValue(Long.parseLong(diskvalue));
/* 5454 */                                                 out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5455 */                                                 out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 5456 */                                                 out.write(45);
/* 5457 */                                                 out.print(diskvalue);
/* 5458 */                                                 out.write("%' >\n");
/*      */                                                 
/* 5460 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5461 */                                                 _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 5462 */                                                 _jspx_th_awolf_005fdialchart_005f2.setParent(null);
/*      */                                                 
/* 5464 */                                                 _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5466 */                                                 _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                                 
/* 5468 */                                                 _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                                 
/* 5470 */                                                 _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                                 
/* 5472 */                                                 _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                                 
/* 5474 */                                                 _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                                 
/* 5476 */                                                 _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                                 
/* 5478 */                                                 _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                                 
/* 5480 */                                                 _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                                 
/* 5482 */                                                 _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 5483 */                                                 int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 5484 */                                                 if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 5485 */                                                   if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5486 */                                                     out = _jspx_page_context.pushBody();
/* 5487 */                                                     _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 5488 */                                                     _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 5491 */                                                     out.write(32);
/* 5492 */                                                     int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 5493 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 5496 */                                                   if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5497 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 5500 */                                                 if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 5501 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                                 }
/*      */                                                 
/* 5504 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 5505 */                                                 out.write(32);
/* 5506 */                                                 out.write(32);
/* 5507 */                                                 out.write("\n    </td>\n            ");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 5511 */                                                 out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5512 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5513 */                                                 out.write(32);
/* 5514 */                                                 out.write(62);
/* 5515 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5516 */                                                 out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                               }
/* 5518 */                                               out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 5519 */                                               if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                               {
/* 5521 */                                                 out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5522 */                                                 out.print(hostid);
/* 5523 */                                                 out.write("&attributeid=");
/* 5524 */                                                 out.print(diskid);
/* 5525 */                                                 out.write("&period=-7')\" class='bodytextbold'>");
/* 5526 */                                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5527 */                                                 out.write(45);
/* 5528 */                                                 out.print(diskvalue);
/* 5529 */                                                 out.write("</a> %\n     ");
/*      */                                               }
/* 5531 */                                               out.write("\n  </td>\n  </tr>\n</table>");
/* 5532 */                                               out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5533 */                                               out.write("</td></tr></table>\n\n");
/*      */                                             } else {
/* 5535 */                                               out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 5536 */                                               out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 5537 */                                               out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 5538 */                                               out.print(systeminfo.get("host_resid"));
/* 5539 */                                               out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5540 */                                               out.print(hostname);
/* 5541 */                                               out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 5542 */                                               if (cpuvalue != null)
/*      */                                               {
/*      */ 
/* 5545 */                                                 dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5546 */                                                 out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                                 
/* 5548 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5549 */                                                 _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 5550 */                                                 _jspx_th_awolf_005fdialchart_005f3.setParent(null);
/*      */                                                 
/* 5552 */                                                 _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5554 */                                                 _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                                 
/* 5556 */                                                 _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                                 
/* 5558 */                                                 _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                                 
/* 5560 */                                                 _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                                 
/* 5562 */                                                 _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                                 
/* 5564 */                                                 _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                                 
/* 5566 */                                                 _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                                 
/* 5568 */                                                 _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                                 
/* 5570 */                                                 _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 5571 */                                                 int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 5572 */                                                 if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 5573 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                                 }
/*      */                                                 
/* 5576 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 5577 */                                                 out.write("\n         </td>\n     ");
/*      */                                               }
/*      */                                               else {
/* 5580 */                                                 out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5581 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5582 */                                                 out.write(39);
/* 5583 */                                                 out.write(32);
/* 5584 */                                                 out.write(62);
/* 5585 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5586 */                                                 out.write("\n \t\t</td>\n\t\t");
/*      */                                               }
/* 5588 */                                               if (memvalue != null) {
/* 5589 */                                                 dialGraph.setValue(Long.parseLong(memvalue));
/* 5590 */                                                 out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                                 
/* 5592 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5593 */                                                 _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 5594 */                                                 _jspx_th_awolf_005fdialchart_005f4.setParent(null);
/*      */                                                 
/* 5596 */                                                 _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5598 */                                                 _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                                 
/* 5600 */                                                 _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                                 
/* 5602 */                                                 _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                                 
/* 5604 */                                                 _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                                 
/* 5606 */                                                 _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                                 
/* 5608 */                                                 _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                                 
/* 5610 */                                                 _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                                 
/* 5612 */                                                 _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                                 
/* 5614 */                                                 _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 5615 */                                                 int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 5616 */                                                 if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 5617 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                                 }
/*      */                                                 
/* 5620 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 5621 */                                                 out.write("\n            </td>\n         ");
/*      */                                               }
/*      */                                               else {
/* 5624 */                                                 out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5625 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5626 */                                                 out.write(39);
/* 5627 */                                                 out.write(32);
/* 5628 */                                                 out.write(62);
/* 5629 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5630 */                                                 out.write("\n \t\t</td>\n\t\t");
/*      */                                               }
/* 5632 */                                               if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5633 */                                                 dialGraph.setValue(Long.parseLong(diskvalue));
/* 5634 */                                                 out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                                 
/* 5636 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5637 */                                                 _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 5638 */                                                 _jspx_th_awolf_005fdialchart_005f5.setParent(null);
/*      */                                                 
/* 5640 */                                                 _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5642 */                                                 _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                                 
/* 5644 */                                                 _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                                 
/* 5646 */                                                 _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                                 
/* 5648 */                                                 _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                                 
/* 5650 */                                                 _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                                 
/* 5652 */                                                 _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                                 
/* 5654 */                                                 _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                                 
/* 5656 */                                                 _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                                 
/* 5658 */                                                 _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 5659 */                                                 int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 5660 */                                                 if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 5661 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                                 }
/*      */                                                 
/* 5664 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 5665 */                                                 out.write(32);
/* 5666 */                                                 out.write("\n\t          </td>\n\t  ");
/*      */                                               }
/*      */                                               else {
/* 5669 */                                                 out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5670 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5671 */                                                 out.write(39);
/* 5672 */                                                 out.write(32);
/* 5673 */                                                 out.write(62);
/* 5674 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5675 */                                                 out.write("\n \t\t</td>\n\t\t");
/*      */                                               }
/* 5677 */                                               out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5678 */                                               out.print(hostid);
/* 5679 */                                               out.write("&attributeid=");
/* 5680 */                                               out.print(cpuid);
/* 5681 */                                               out.write("&period=-7')\" class='tooltip'>");
/* 5682 */                                               out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5683 */                                               out.write(32);
/* 5684 */                                               out.write(45);
/* 5685 */                                               out.write(32);
/* 5686 */                                               if (cpuvalue != null) {
/* 5687 */                                                 out.print(cpuvalue);
/*      */                                               }
/* 5689 */                                               out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5690 */                                               out.print(hostid);
/* 5691 */                                               out.write("&attributeid=");
/* 5692 */                                               out.print(memid);
/* 5693 */                                               out.write("&period=-7')\" class='tooltip'>");
/* 5694 */                                               out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5695 */                                               out.write(45);
/* 5696 */                                               if (memvalue != null) {
/* 5697 */                                                 out.print(memvalue);
/*      */                                               }
/* 5699 */                                               out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5700 */                                               out.print(hostid);
/* 5701 */                                               out.write("&attributeid=");
/* 5702 */                                               out.print(diskid);
/* 5703 */                                               out.write("&period=-7')\" class='tooltip'>");
/* 5704 */                                               out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5705 */                                               out.write(45);
/* 5706 */                                               if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5707 */                                                 out.print(diskvalue);
/*      */                                               }
/* 5709 */                                               out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                             }
/* 5711 */                                             out.write(10);
/* 5712 */                                             out.write(10);
/*      */                                           }
/*      */                                           
/*      */                                         }
/*      */                                       }
/*      */                                       catch (Exception e)
/*      */                                       {
/* 5719 */                                         e.printStackTrace();
/*      */                                       }
/* 5721 */                                       out.write(10);
/* 5722 */                                       out.write(10);
/*      */                                     }
/* 5724 */                                   } } } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5725 */         out = _jspx_out;
/* 5726 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5727 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5728 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5731 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5737 */     PageContext pageContext = _jspx_page_context;
/* 5738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5740 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5741 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5742 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 5744 */     _jspx_th_c_005fif_005f0.setTest("${not empty param.haid}");
/* 5745 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5746 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5748 */         out.write(10);
/* 5749 */         out.write(9);
/* 5750 */         out.write(9);
/* 5751 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 5752 */           return true;
/* 5753 */         out.write(10);
/* 5754 */         out.write(9);
/* 5755 */         out.write(9);
/* 5756 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5757 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5761 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5762 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5763 */       return true;
/*      */     }
/* 5765 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5771 */     PageContext pageContext = _jspx_page_context;
/* 5772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5774 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5775 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5776 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5778 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 5780 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 5781 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5782 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5783 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5784 */         out = _jspx_page_context.pushBody();
/* 5785 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5786 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5789 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5790 */           return true;
/* 5791 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5792 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5795 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5796 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5799 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5800 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5801 */       return true;
/*      */     }
/* 5803 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5809 */     PageContext pageContext = _jspx_page_context;
/* 5810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5812 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5813 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5814 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5816 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 5817 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5818 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5819 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5820 */       return true;
/*      */     }
/* 5822 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5828 */     PageContext pageContext = _jspx_page_context;
/* 5829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5831 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5832 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5833 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 5835 */     _jspx_th_c_005fif_005f1.setTest("${not empty param.resourceid}");
/* 5836 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5837 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5839 */         out.write(10);
/* 5840 */         out.write(9);
/* 5841 */         out.write(9);
/* 5842 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 5843 */           return true;
/* 5844 */         out.write(10);
/* 5845 */         out.write(9);
/* 5846 */         out.write(9);
/* 5847 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5848 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5852 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5853 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5854 */       return true;
/*      */     }
/* 5856 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5857 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5862 */     PageContext pageContext = _jspx_page_context;
/* 5863 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5865 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5866 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5867 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5869 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 5871 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 5872 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5873 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 5874 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5875 */         out = _jspx_page_context.pushBody();
/* 5876 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 5877 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5880 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 5881 */           return true;
/* 5882 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 5883 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5886 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5887 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5890 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5891 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5892 */       return true;
/*      */     }
/* 5894 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5895 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5900 */     PageContext pageContext = _jspx_page_context;
/* 5901 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5903 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5904 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5905 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 5907 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 5908 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5909 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5910 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5911 */       return true;
/*      */     }
/* 5913 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5914 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5919 */     PageContext pageContext = _jspx_page_context;
/* 5920 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5922 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5923 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5924 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 5926 */     _jspx_th_c_005fout_005f2.setValue("${myfield_paramresid}");
/* 5927 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5928 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5929 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5930 */       return true;
/*      */     }
/* 5932 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5938 */     PageContext pageContext = _jspx_page_context;
/* 5939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5941 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5942 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5943 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/* 5945 */     _jspx_th_c_005fif_005f2.setTest("${not empty param.haid}");
/* 5946 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5947 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5949 */         out.write(10);
/* 5950 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5951 */           return true;
/* 5952 */         out.write(10);
/* 5953 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5954 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5958 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5959 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5960 */       return true;
/*      */     }
/* 5962 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5968 */     PageContext pageContext = _jspx_page_context;
/* 5969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5971 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5972 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5973 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5975 */     _jspx_th_c_005fset_005f2.setVar("myfield_resid");
/*      */     
/* 5977 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 5978 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5979 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5980 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5981 */         out = _jspx_page_context.pushBody();
/* 5982 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5983 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5986 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5987 */           return true;
/* 5988 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5989 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5992 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5993 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5996 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5997 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5998 */       return true;
/*      */     }
/* 6000 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 6001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6006 */     PageContext pageContext = _jspx_page_context;
/* 6007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6009 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6010 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6011 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 6013 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 6014 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6015 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6016 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6017 */       return true;
/*      */     }
/* 6019 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6020 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6025 */     PageContext pageContext = _jspx_page_context;
/* 6026 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6028 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6029 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 6030 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 6032 */     _jspx_th_c_005fif_005f3.setTest("${not empty param.resourceid}");
/* 6033 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 6034 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 6036 */         out.write(10);
/* 6037 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 6038 */           return true;
/* 6039 */         out.write(10);
/* 6040 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 6041 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6045 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 6046 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6047 */       return true;
/*      */     }
/* 6049 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6055 */     PageContext pageContext = _jspx_page_context;
/* 6056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6058 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6059 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 6060 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6062 */     _jspx_th_c_005fset_005f3.setVar("myfield_resid");
/*      */     
/* 6064 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 6065 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 6066 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 6067 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 6068 */         out = _jspx_page_context.pushBody();
/* 6069 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 6070 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6073 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 6074 */           return true;
/* 6075 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 6076 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6079 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 6080 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6083 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 6084 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 6085 */       return true;
/*      */     }
/* 6087 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 6088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6093 */     PageContext pageContext = _jspx_page_context;
/* 6094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6096 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6097 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6098 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 6100 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 6101 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6102 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6103 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6104 */       return true;
/*      */     }
/* 6106 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6112 */     PageContext pageContext = _jspx_page_context;
/* 6113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6115 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6116 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 6117 */     _jspx_th_c_005fset_005f4.setParent(null);
/*      */     
/* 6119 */     _jspx_th_c_005fset_005f4.setVar("trstripclass");
/*      */     
/* 6121 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 6122 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 6123 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 6124 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6125 */         out = _jspx_page_context.pushBody();
/* 6126 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 6127 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6130 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 6131 */           return true;
/* 6132 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 6133 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6136 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6137 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6140 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 6141 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 6142 */       return true;
/*      */     }
/* 6144 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 6145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6150 */     PageContext pageContext = _jspx_page_context;
/* 6151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6153 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6154 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6155 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 6157 */     _jspx_th_c_005fout_005f5.setValue("");
/* 6158 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6159 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6160 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6161 */       return true;
/*      */     }
/* 6163 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6169 */     PageContext pageContext = _jspx_page_context;
/* 6170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6172 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6173 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 6174 */     _jspx_th_c_005fset_005f5.setParent(null);
/*      */     
/* 6176 */     _jspx_th_c_005fset_005f5.setVar("myfield_entity");
/*      */     
/* 6178 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 6179 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 6180 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 6181 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6182 */         out = _jspx_page_context.pushBody();
/* 6183 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 6184 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6187 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 6188 */           return true;
/* 6189 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 6190 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6193 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6194 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6197 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 6198 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 6199 */       return true;
/*      */     }
/* 6201 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 6202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6207 */     PageContext pageContext = _jspx_page_context;
/* 6208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6210 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6211 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6212 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 6214 */     _jspx_th_c_005fout_005f6.setValue("noalarms");
/* 6215 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6216 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6217 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6218 */       return true;
/*      */     }
/* 6220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6226 */     PageContext pageContext = _jspx_page_context;
/* 6227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6229 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6230 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 6231 */     _jspx_th_c_005fif_005f4.setParent(null);
/*      */     
/* 6233 */     _jspx_th_c_005fif_005f4.setTest("${not empty param.entity}");
/* 6234 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 6235 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 6237 */         out.write(10);
/* 6238 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6239 */           return true;
/* 6240 */         out.write(10);
/* 6241 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 6242 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6246 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 6247 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6248 */       return true;
/*      */     }
/* 6250 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6256 */     PageContext pageContext = _jspx_page_context;
/* 6257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6259 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6260 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 6261 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6263 */     _jspx_th_c_005fset_005f6.setVar("myfield_entity");
/*      */     
/* 6265 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 6266 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 6267 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 6268 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6269 */         out = _jspx_page_context.pushBody();
/* 6270 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 6271 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6274 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 6275 */           return true;
/* 6276 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 6277 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6280 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6281 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6284 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 6285 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 6286 */       return true;
/*      */     }
/* 6288 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 6289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6294 */     PageContext pageContext = _jspx_page_context;
/* 6295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6297 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6298 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 6299 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 6301 */     _jspx_th_c_005fout_005f7.setValue("${param.entity}");
/* 6302 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 6303 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 6304 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6305 */       return true;
/*      */     }
/* 6307 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6308 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6313 */     PageContext pageContext = _jspx_page_context;
/* 6314 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6316 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6317 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 6318 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 6320 */     _jspx_th_c_005fif_005f5.setTest("${not empty param.includeClass}");
/* 6321 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 6322 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 6324 */         out.write(10);
/* 6325 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 6326 */           return true;
/* 6327 */         out.write(10);
/* 6328 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 6329 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6333 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 6334 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6335 */       return true;
/*      */     }
/* 6337 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6343 */     PageContext pageContext = _jspx_page_context;
/* 6344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6346 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6347 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 6348 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 6350 */     _jspx_th_c_005fset_005f7.setVar("trstripclass");
/*      */     
/* 6352 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 6353 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 6354 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 6355 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6356 */         out = _jspx_page_context.pushBody();
/* 6357 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 6358 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6361 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 6362 */           return true;
/* 6363 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 6364 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6367 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6368 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6371 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 6372 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6373 */       return true;
/*      */     }
/* 6375 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6381 */     PageContext pageContext = _jspx_page_context;
/* 6382 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6384 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6385 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6386 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 6388 */     _jspx_th_c_005fout_005f8.setValue("${param.includeClass}");
/* 6389 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6390 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6391 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6392 */       return true;
/*      */     }
/* 6394 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6395 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6400 */     PageContext pageContext = _jspx_page_context;
/* 6401 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6403 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6404 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6405 */     _jspx_th_c_005fout_005f9.setParent(null);
/*      */     
/* 6407 */     _jspx_th_c_005fout_005f9.setValue("${trstripclass}");
/* 6408 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6409 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6410 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6411 */       return true;
/*      */     }
/* 6413 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6419 */     PageContext pageContext = _jspx_page_context;
/* 6420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6422 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6423 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6424 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 6425 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6426 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 6427 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6428 */         out = _jspx_page_context.pushBody();
/* 6429 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 6430 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6433 */         out.write("am.myfield.customfield.text");
/* 6434 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 6435 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6438 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6439 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6442 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6443 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6444 */       return true;
/*      */     }
/* 6446 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6447 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6452 */     PageContext pageContext = _jspx_page_context;
/* 6453 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6455 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6456 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6457 */     _jspx_th_c_005fout_005f10.setParent(null);
/*      */     
/* 6459 */     _jspx_th_c_005fout_005f10.setValue("${myfield_resid}");
/* 6460 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6461 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6462 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6463 */       return true;
/*      */     }
/* 6465 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6466 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6471 */     PageContext pageContext = _jspx_page_context;
/* 6472 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6474 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6475 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6476 */     _jspx_th_c_005fout_005f11.setParent(null);
/*      */     
/* 6478 */     _jspx_th_c_005fout_005f11.setValue("${myfield_entity}");
/* 6479 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6480 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6481 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6482 */       return true;
/*      */     }
/* 6484 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6485 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6490 */     PageContext pageContext = _jspx_page_context;
/* 6491 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6493 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6494 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6495 */     _jspx_th_c_005fout_005f12.setParent(null);
/*      */     
/* 6497 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 6498 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6499 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6500 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6501 */       return true;
/*      */     }
/* 6503 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6504 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6509 */     PageContext pageContext = _jspx_page_context;
/* 6510 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6512 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6513 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6514 */     _jspx_th_c_005fout_005f13.setParent(null);
/*      */     
/* 6516 */     _jspx_th_c_005fout_005f13.setValue("${param.resourcename}");
/* 6517 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6518 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6519 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6520 */       return true;
/*      */     }
/* 6522 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6523 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6528 */     PageContext pageContext = _jspx_page_context;
/* 6529 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6531 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6532 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6533 */     _jspx_th_c_005fout_005f14.setParent(null);
/*      */     
/* 6535 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 6536 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6537 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6538 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6539 */       return true;
/*      */     }
/* 6541 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6547 */     PageContext pageContext = _jspx_page_context;
/* 6548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6550 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6551 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6552 */     _jspx_th_c_005fout_005f15.setParent(null);
/*      */     
/* 6554 */     _jspx_th_c_005fout_005f15.setValue("${param.resourcename}");
/* 6555 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6556 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6557 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6558 */       return true;
/*      */     }
/* 6560 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6566 */     PageContext pageContext = _jspx_page_context;
/* 6567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6569 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6570 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6571 */     _jspx_th_c_005fout_005f16.setParent(null);
/*      */     
/* 6573 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 6574 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6575 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6576 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6577 */       return true;
/*      */     }
/* 6579 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6585 */     PageContext pageContext = _jspx_page_context;
/* 6586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6588 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6589 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6590 */     _jspx_th_c_005fout_005f17.setParent(null);
/*      */     
/* 6592 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 6593 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6594 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6595 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6596 */       return true;
/*      */     }
/* 6598 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6604 */     PageContext pageContext = _jspx_page_context;
/* 6605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6607 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6608 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6609 */     _jspx_th_c_005fout_005f18.setParent(null);
/*      */     
/* 6611 */     _jspx_th_c_005fout_005f18.setValue("${param.resourcename}");
/* 6612 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6613 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6614 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6615 */       return true;
/*      */     }
/* 6617 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6623 */     PageContext pageContext = _jspx_page_context;
/* 6624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6626 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6627 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6628 */     _jspx_th_c_005fout_005f19.setParent(null);
/*      */     
/* 6630 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 6631 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6632 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6633 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6634 */       return true;
/*      */     }
/* 6636 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6642 */     PageContext pageContext = _jspx_page_context;
/* 6643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6645 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6646 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6647 */     _jspx_th_c_005fout_005f20.setParent(null);
/*      */     
/* 6649 */     _jspx_th_c_005fout_005f20.setValue("${param.resourcename}");
/* 6650 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6651 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6652 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6653 */       return true;
/*      */     }
/* 6655 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6661 */     PageContext pageContext = _jspx_page_context;
/* 6662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6664 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6665 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6666 */     _jspx_th_c_005fout_005f21.setParent(null);
/*      */     
/* 6668 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 6669 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6670 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6671 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6672 */       return true;
/*      */     }
/* 6674 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6680 */     PageContext pageContext = _jspx_page_context;
/* 6681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6683 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6684 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6685 */     _jspx_th_c_005fout_005f22.setParent(null);
/*      */     
/* 6687 */     _jspx_th_c_005fout_005f22.setValue("${param.resourcename}");
/* 6688 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6689 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6690 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6691 */       return true;
/*      */     }
/* 6693 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6699 */     PageContext pageContext = _jspx_page_context;
/* 6700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6702 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6703 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 6704 */     _jspx_th_c_005fout_005f23.setParent(null);
/*      */     
/* 6706 */     _jspx_th_c_005fout_005f23.setValue("${param.resourceid}");
/* 6707 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 6708 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 6709 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6710 */       return true;
/*      */     }
/* 6712 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6718 */     PageContext pageContext = _jspx_page_context;
/* 6719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6721 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6722 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6723 */     _jspx_th_c_005fout_005f24.setParent(null);
/*      */     
/* 6725 */     _jspx_th_c_005fout_005f24.setValue("${param.resourcename}");
/* 6726 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6727 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6728 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6729 */       return true;
/*      */     }
/* 6731 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6737 */     PageContext pageContext = _jspx_page_context;
/* 6738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6740 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6741 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 6742 */     _jspx_th_c_005fout_005f25.setParent(null);
/*      */     
/* 6744 */     _jspx_th_c_005fout_005f25.setValue("${param.resourceid}");
/* 6745 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 6746 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 6747 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6748 */       return true;
/*      */     }
/* 6750 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6756 */     PageContext pageContext = _jspx_page_context;
/* 6757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6759 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6760 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 6761 */     _jspx_th_c_005fout_005f26.setParent(null);
/*      */     
/* 6763 */     _jspx_th_c_005fout_005f26.setValue("${param.resourcename}");
/* 6764 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 6765 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 6766 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6767 */       return true;
/*      */     }
/* 6769 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6775 */     PageContext pageContext = _jspx_page_context;
/* 6776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6778 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6779 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 6780 */     _jspx_th_c_005fout_005f27.setParent(null);
/*      */     
/* 6782 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 6783 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 6784 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 6785 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6786 */       return true;
/*      */     }
/* 6788 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6794 */     PageContext pageContext = _jspx_page_context;
/* 6795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6797 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6798 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 6799 */     _jspx_th_c_005fout_005f28.setParent(null);
/*      */     
/* 6801 */     _jspx_th_c_005fout_005f28.setValue("${param.resourcename}");
/* 6802 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 6803 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 6804 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6805 */       return true;
/*      */     }
/* 6807 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6808 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6813 */     PageContext pageContext = _jspx_page_context;
/* 6814 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6816 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6817 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 6818 */     _jspx_th_c_005fout_005f29.setParent(null);
/*      */     
/* 6820 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 6821 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 6822 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 6823 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6824 */       return true;
/*      */     }
/* 6826 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6827 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6832 */     PageContext pageContext = _jspx_page_context;
/* 6833 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6835 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6836 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 6837 */     _jspx_th_c_005fout_005f30.setParent(null);
/*      */     
/* 6839 */     _jspx_th_c_005fout_005f30.setValue("${param.resourcename}");
/* 6840 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 6841 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 6842 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6843 */       return true;
/*      */     }
/* 6845 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6851 */     PageContext pageContext = _jspx_page_context;
/* 6852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6854 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6855 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 6856 */     _jspx_th_c_005fout_005f31.setParent(null);
/*      */     
/* 6858 */     _jspx_th_c_005fout_005f31.setValue("${param.resourceid}");
/* 6859 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 6860 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 6861 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6862 */       return true;
/*      */     }
/* 6864 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6870 */     PageContext pageContext = _jspx_page_context;
/* 6871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6873 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6874 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 6875 */     _jspx_th_c_005fout_005f32.setParent(null);
/*      */     
/* 6877 */     _jspx_th_c_005fout_005f32.setValue("${param.resourcename}");
/* 6878 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 6879 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 6880 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6881 */       return true;
/*      */     }
/* 6883 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6884 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6889 */     PageContext pageContext = _jspx_page_context;
/* 6890 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6892 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6893 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 6894 */     _jspx_th_c_005fout_005f33.setParent(null);
/*      */     
/* 6896 */     _jspx_th_c_005fout_005f33.setValue("${param.resourceid}");
/* 6897 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 6898 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 6899 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6900 */       return true;
/*      */     }
/* 6902 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6903 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6908 */     PageContext pageContext = _jspx_page_context;
/* 6909 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6911 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6912 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 6913 */     _jspx_th_c_005fout_005f34.setParent(null);
/*      */     
/* 6915 */     _jspx_th_c_005fout_005f34.setValue("${param.resourcename}");
/* 6916 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 6917 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 6918 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6919 */       return true;
/*      */     }
/* 6921 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6922 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6927 */     PageContext pageContext = _jspx_page_context;
/* 6928 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6930 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6931 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 6932 */     _jspx_th_c_005fout_005f35.setParent(null);
/*      */     
/* 6934 */     _jspx_th_c_005fout_005f35.setValue("${param.resourceid}");
/* 6935 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 6936 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 6937 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6938 */       return true;
/*      */     }
/* 6940 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6941 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6946 */     PageContext pageContext = _jspx_page_context;
/* 6947 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6949 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6950 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 6951 */     _jspx_th_c_005fout_005f36.setParent(null);
/*      */     
/* 6953 */     _jspx_th_c_005fout_005f36.setValue("${param.resourcename}");
/* 6954 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 6955 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 6956 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6957 */       return true;
/*      */     }
/* 6959 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6960 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6965 */     PageContext pageContext = _jspx_page_context;
/* 6966 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6968 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6969 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 6970 */     _jspx_th_c_005fif_005f6.setParent(null);
/*      */     
/* 6972 */     _jspx_th_c_005fif_005f6.setTest("${isMetaSpace1}");
/* 6973 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 6974 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 6976 */         out.write("\n            <td><span class=\"bodytext\">MS</span></td>\n            <td><span class=\"bodytext\">CCS</span></td>\n            ");
/* 6977 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 6978 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6982 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 6983 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6984 */       return true;
/*      */     }
/* 6986 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6987 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6992 */     PageContext pageContext = _jspx_page_context;
/* 6993 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6995 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6996 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 6997 */     _jspx_th_c_005fif_005f7.setParent(null);
/*      */     
/* 6999 */     _jspx_th_c_005fif_005f7.setTest("${not isMetaSpace1}");
/* 7000 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 7001 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 7003 */         out.write("\n            <td><span class=\"bodytext\">PG</span></td>");
/* 7004 */         out.write("\n            <td><span class=\"bodytext\">RO</span></td>\n            <td><span class=\"bodytext\">RW</span></td>\n            ");
/* 7005 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 7006 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7010 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 7011 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 7012 */       return true;
/*      */     }
/* 7014 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 7015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7020 */     PageContext pageContext = _jspx_page_context;
/* 7021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7023 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7024 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 7025 */     _jspx_th_c_005fif_005f14.setParent(null);
/*      */     
/* 7027 */     _jspx_th_c_005fif_005f14.setTest("${isMetaSpace}");
/* 7028 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 7029 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 7031 */         out.write("\n           \t\t <td><span class=\"bodytext\"></td>\n           \t\t");
/* 7032 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 7033 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7037 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 7038 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 7039 */       return true;
/*      */     }
/* 7041 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 7042 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7047 */     PageContext pageContext = _jspx_page_context;
/* 7048 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7050 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7051 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 7052 */     _jspx_th_c_005fout_005f37.setParent(null);
/*      */     
/* 7054 */     _jspx_th_c_005fout_005f37.setValue("${param.resourceid}");
/* 7055 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 7056 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 7057 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7058 */       return true;
/*      */     }
/* 7060 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7061 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7066 */     PageContext pageContext = _jspx_page_context;
/* 7067 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7069 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7070 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 7071 */     _jspx_th_c_005fout_005f38.setParent(null);
/*      */     
/* 7073 */     _jspx_th_c_005fout_005f38.setValue("${param.resourcename}");
/* 7074 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 7075 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 7076 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7077 */       return true;
/*      */     }
/* 7079 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7080 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7085 */     PageContext pageContext = _jspx_page_context;
/* 7086 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7088 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7089 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 7090 */     _jspx_th_c_005fout_005f39.setParent(null);
/*      */     
/* 7092 */     _jspx_th_c_005fout_005f39.setValue("${param.resourceid}");
/* 7093 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 7094 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 7095 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7096 */       return true;
/*      */     }
/* 7098 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7099 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7104 */     PageContext pageContext = _jspx_page_context;
/* 7105 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7107 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7108 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 7109 */     _jspx_th_c_005fout_005f40.setParent(null);
/*      */     
/* 7111 */     _jspx_th_c_005fout_005f40.setValue("${param.resourcename}");
/* 7112 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 7113 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 7114 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7115 */       return true;
/*      */     }
/* 7117 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7118 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7123 */     PageContext pageContext = _jspx_page_context;
/* 7124 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7126 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7127 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 7128 */     _jspx_th_c_005fout_005f41.setParent(null);
/*      */     
/* 7130 */     _jspx_th_c_005fout_005f41.setValue("${param.resourceid}");
/* 7131 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 7132 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 7133 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 7134 */       return true;
/*      */     }
/* 7136 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 7137 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7142 */     PageContext pageContext = _jspx_page_context;
/* 7143 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7145 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7146 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 7147 */     _jspx_th_c_005fout_005f42.setParent(null);
/*      */     
/* 7149 */     _jspx_th_c_005fout_005f42.setValue("${param.resourcename}");
/* 7150 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 7151 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 7152 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 7153 */       return true;
/*      */     }
/* 7155 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 7156 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7161 */     PageContext pageContext = _jspx_page_context;
/* 7162 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7164 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7165 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 7166 */     _jspx_th_c_005fout_005f43.setParent(null);
/*      */     
/* 7168 */     _jspx_th_c_005fout_005f43.setValue("${param.resourceid}");
/* 7169 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 7170 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 7171 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 7172 */       return true;
/*      */     }
/* 7174 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 7175 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7180 */     PageContext pageContext = _jspx_page_context;
/* 7181 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7183 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7184 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 7185 */     _jspx_th_c_005fout_005f44.setParent(null);
/*      */     
/* 7187 */     _jspx_th_c_005fout_005f44.setValue("${param.resourcename}");
/* 7188 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 7189 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 7190 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 7191 */       return true;
/*      */     }
/* 7193 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 7194 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7199 */     PageContext pageContext = _jspx_page_context;
/* 7200 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7202 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7203 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 7204 */     _jspx_th_c_005fout_005f45.setParent(null);
/*      */     
/* 7206 */     _jspx_th_c_005fout_005f45.setValue("${param.resourceid}");
/* 7207 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 7208 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 7209 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 7210 */       return true;
/*      */     }
/* 7212 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 7213 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7218 */     PageContext pageContext = _jspx_page_context;
/* 7219 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7221 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7222 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 7223 */     _jspx_th_c_005fout_005f46.setParent(null);
/*      */     
/* 7225 */     _jspx_th_c_005fout_005f46.setValue("${param.resourcename}");
/* 7226 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 7227 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 7228 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 7229 */       return true;
/*      */     }
/* 7231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 7232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7237 */     PageContext pageContext = _jspx_page_context;
/* 7238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7240 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7241 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 7242 */     _jspx_th_c_005fout_005f47.setParent(null);
/*      */     
/* 7244 */     _jspx_th_c_005fout_005f47.setValue("${param.resourceid}");
/* 7245 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 7246 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 7247 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 7248 */       return true;
/*      */     }
/* 7250 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 7251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7256 */     PageContext pageContext = _jspx_page_context;
/* 7257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7259 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7260 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 7261 */     _jspx_th_c_005fout_005f48.setParent(null);
/*      */     
/* 7263 */     _jspx_th_c_005fout_005f48.setValue("${param.resourcename}");
/* 7264 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 7265 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 7266 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 7267 */       return true;
/*      */     }
/* 7269 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 7270 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7275 */     PageContext pageContext = _jspx_page_context;
/* 7276 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7278 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7279 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 7280 */     _jspx_th_c_005fout_005f49.setParent(null);
/*      */     
/* 7282 */     _jspx_th_c_005fout_005f49.setValue("${param.resourceid}");
/* 7283 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 7284 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 7285 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 7286 */       return true;
/*      */     }
/* 7288 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 7289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7294 */     PageContext pageContext = _jspx_page_context;
/* 7295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7297 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7298 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 7299 */     _jspx_th_c_005fout_005f50.setParent(null);
/*      */     
/* 7301 */     _jspx_th_c_005fout_005f50.setValue("${param.resourcename}");
/* 7302 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 7303 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 7304 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 7305 */       return true;
/*      */     }
/* 7307 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 7308 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7313 */     PageContext pageContext = _jspx_page_context;
/* 7314 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7316 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7317 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 7318 */     _jspx_th_c_005fout_005f51.setParent(null);
/*      */     
/* 7320 */     _jspx_th_c_005fout_005f51.setValue("${param.resourceid}");
/* 7321 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 7322 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 7323 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 7324 */       return true;
/*      */     }
/* 7326 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 7327 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7332 */     PageContext pageContext = _jspx_page_context;
/* 7333 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7335 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7336 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 7337 */     _jspx_th_c_005fout_005f52.setParent(null);
/*      */     
/* 7339 */     _jspx_th_c_005fout_005f52.setValue("${param.resourcename}");
/* 7340 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 7341 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 7342 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 7343 */       return true;
/*      */     }
/* 7345 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 7346 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7351 */     PageContext pageContext = _jspx_page_context;
/* 7352 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7354 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7355 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 7356 */     _jspx_th_c_005fout_005f53.setParent(null);
/*      */     
/* 7358 */     _jspx_th_c_005fout_005f53.setValue("${param.resourceid}");
/* 7359 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 7360 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 7361 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 7362 */       return true;
/*      */     }
/* 7364 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 7365 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f54(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7370 */     PageContext pageContext = _jspx_page_context;
/* 7371 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7373 */     OutTag _jspx_th_c_005fout_005f54 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7374 */     _jspx_th_c_005fout_005f54.setPageContext(_jspx_page_context);
/* 7375 */     _jspx_th_c_005fout_005f54.setParent(null);
/*      */     
/* 7377 */     _jspx_th_c_005fout_005f54.setValue("${param.resourcename}");
/* 7378 */     int _jspx_eval_c_005fout_005f54 = _jspx_th_c_005fout_005f54.doStartTag();
/* 7379 */     if (_jspx_th_c_005fout_005f54.doEndTag() == 5) {
/* 7380 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 7381 */       return true;
/*      */     }
/* 7383 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 7384 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f55(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7389 */     PageContext pageContext = _jspx_page_context;
/* 7390 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7392 */     OutTag _jspx_th_c_005fout_005f55 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7393 */     _jspx_th_c_005fout_005f55.setPageContext(_jspx_page_context);
/* 7394 */     _jspx_th_c_005fout_005f55.setParent(null);
/*      */     
/* 7396 */     _jspx_th_c_005fout_005f55.setValue("${param.resourceid}");
/* 7397 */     int _jspx_eval_c_005fout_005f55 = _jspx_th_c_005fout_005f55.doStartTag();
/* 7398 */     if (_jspx_th_c_005fout_005f55.doEndTag() == 5) {
/* 7399 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 7400 */       return true;
/*      */     }
/* 7402 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 7403 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f56(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7408 */     PageContext pageContext = _jspx_page_context;
/* 7409 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7411 */     OutTag _jspx_th_c_005fout_005f56 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7412 */     _jspx_th_c_005fout_005f56.setPageContext(_jspx_page_context);
/* 7413 */     _jspx_th_c_005fout_005f56.setParent(null);
/*      */     
/* 7415 */     _jspx_th_c_005fout_005f56.setValue("${param.resourcename}");
/* 7416 */     int _jspx_eval_c_005fout_005f56 = _jspx_th_c_005fout_005f56.doStartTag();
/* 7417 */     if (_jspx_th_c_005fout_005f56.doEndTag() == 5) {
/* 7418 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 7419 */       return true;
/*      */     }
/* 7421 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 7422 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\JDK15Overview_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */