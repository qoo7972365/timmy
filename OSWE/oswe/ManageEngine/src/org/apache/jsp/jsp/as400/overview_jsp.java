/*      */ package org.apache.jsp.jsp.as400;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.AS400Graphs;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.BarChart;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.StackBarChart;
/*      */ import java.net.InetAddress;
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
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ 
/*      */ public final class overview_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  718 */     InetAddress add = null;
/*  719 */     if (ip.equals("127.0.0.1")) {
/*  720 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  724 */       add = InetAddress.getByName(ip);
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
/* 2183 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2184 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2205 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2209 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2223 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2227 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2229 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2230 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2231 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.release();
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2234 */     this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors.release();
/* 2235 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2236 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2237 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2246 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2249 */     JspWriter out = null;
/* 2250 */     Object page = this;
/* 2251 */     JspWriter _jspx_out = null;
/* 2252 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2256 */       response.setContentType("text/html;charset=UTF-8");
/* 2257 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2259 */       _jspx_page_context = pageContext;
/* 2260 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2261 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2262 */       session = pageContext.getSession();
/* 2263 */       out = pageContext.getOut();
/* 2264 */       _jspx_out = out;
/*      */       
/* 2266 */       out.write("<!--$Id$--> \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2267 */       GetWLSGraph wlsGraph = null;
/* 2268 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2269 */       if (wlsGraph == null) {
/* 2270 */         wlsGraph = new GetWLSGraph();
/* 2271 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2273 */       out.write(10);
/* 2274 */       DialChartSupport dialGraph = null;
/* 2275 */       dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 2276 */       if (dialGraph == null) {
/* 2277 */         dialGraph = new DialChartSupport();
/* 2278 */         _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */       }
/* 2280 */       out.write(10);
/* 2281 */       AS400Graphs as400graph = null;
/* 2282 */       as400graph = (AS400Graphs)_jspx_page_context.getAttribute("as400graph", 1);
/* 2283 */       if (as400graph == null) {
/* 2284 */         as400graph = new AS400Graphs();
/* 2285 */         _jspx_page_context.setAttribute("as400graph", as400graph, 1);
/*      */       }
/* 2287 */       out.write(10);
/* 2288 */       com.adventnet.appmanager.bean.PerformanceBean perfgraph = null;
/* 2289 */       perfgraph = (com.adventnet.appmanager.bean.PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2290 */       if (perfgraph == null) {
/* 2291 */         perfgraph = new com.adventnet.appmanager.bean.PerformanceBean();
/* 2292 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2294 */       out.write(10);
/* 2295 */       com.adventnet.appmanager.bean.AS400DiskSpaceGraph as400diskgraph = null;
/* 2296 */       as400diskgraph = (com.adventnet.appmanager.bean.AS400DiskSpaceGraph)_jspx_page_context.getAttribute("as400diskgraph", 2);
/* 2297 */       if (as400diskgraph == null) {
/* 2298 */         as400diskgraph = new com.adventnet.appmanager.bean.AS400DiskSpaceGraph();
/* 2299 */         _jspx_page_context.setAttribute("as400diskgraph", as400diskgraph, 2);
/*      */       }
/* 2301 */       out.write("\n\n\n\n\n\n");
/* 2302 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2304 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2305 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2306 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2308 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2310 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2312 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2314 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2315 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2316 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2317 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2320 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2321 */         String available = null;
/* 2322 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2323 */         out.write(10);
/*      */         
/* 2325 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2326 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2327 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2329 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2331 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2333 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2335 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2336 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2337 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2338 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2341 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2342 */           String unavailable = null;
/* 2343 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2344 */           out.write(10);
/*      */           
/* 2346 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2347 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2348 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2350 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2352 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2354 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2356 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2357 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2358 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2359 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2362 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2363 */             String unmanaged = null;
/* 2364 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2365 */             out.write(10);
/*      */             
/* 2367 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2368 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2369 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2371 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2373 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2375 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2377 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2378 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2379 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2380 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2383 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2384 */               String scheduled = null;
/* 2385 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2386 */               out.write(10);
/*      */               
/* 2388 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2389 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2390 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2392 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2394 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2396 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2398 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2399 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2400 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2401 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2404 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2405 */                 String critical = null;
/* 2406 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2407 */                 out.write(10);
/*      */                 
/* 2409 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2410 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2411 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2413 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2415 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2417 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2419 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2420 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2421 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2422 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2425 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2426 */                   String clear = null;
/* 2427 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2428 */                   out.write(10);
/*      */                   
/* 2430 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2431 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2432 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2434 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2436 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2438 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2440 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2441 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2442 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2443 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2446 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2447 */                     String warning = null;
/* 2448 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2449 */                     out.write(10);
/* 2450 */                     out.write(10);
/*      */                     
/* 2452 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2453 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2455 */                     out.write(10);
/* 2456 */                     out.write(10);
/* 2457 */                     out.write(10);
/* 2458 */                     out.write("\n\n\n\n\n");
/*      */                     
/*      */ 
/* 2461 */                     String type = "AS400/iSeries";
/* 2462 */                     String servername1 = (String)request.getAttribute("servername1");
/* 2463 */                     String resourceid = request.getParameter("resourceid");
/* 2464 */                     as400graph.setresid(Integer.parseInt(resourceid));
/* 2465 */                     as400diskgraph.setResourceId(resourceid);
/*      */                     
/* 2467 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "&datatype=1");
/* 2468 */                     String displayname = (String)request.getAttribute("displayname");
/*      */                     
/* 2470 */                     String model1 = (String)request.getAttribute("model1");
/* 2471 */                     String version1 = (String)request.getAttribute("version1");
/* 2472 */                     String serialno1 = (String)request.getAttribute("serialno1");
/* 2473 */                     String securitylevel1 = (String)request.getAttribute("securitylevel1");
/* 2474 */                     String previoussystemend1 = (String)request.getAttribute("previoussystemend1");
/* 2475 */                     String autodeviceconfiguration = (String)request.getAttribute("autodeviceconfiguration");
/* 2476 */                     String systemconsole = (String)request.getAttribute("systemconsole");
/* 2477 */                     String jobmessagequeueinitialsize = (String)request.getAttribute("jobmessagequeueinitialsize");
/* 2478 */                     String jobmessaequeuemaximumsize = (String)request.getAttribute("jobmessaequeuemaximumsize");
/* 2479 */                     String spoolingcontrolinitialsize = (String)request.getAttribute("spoolingcontrolinitialsize");
/* 2480 */                     String maximumjobsallowed = (String)request.getAttribute("maximumjobsallowed");
/* 2481 */                     String passwordvaliddays = (String)request.getAttribute("passwordvaliddays");
/* 2482 */                     String queryprocessingtimelimit = (String)request.getAttribute("queryprocessingtimelimit");
/* 2483 */                     long responsetime = ((Long)request.getAttribute("responsetime")).longValue();
/*      */                     
/* 2485 */                     String ASP_PERCENTAGE = (String)request.getAttribute("ASP_PERCENTAGE");
/* 2486 */                     String DB_PERCENTAGE = (String)request.getAttribute("DB_PERCENTAGE");
/* 2487 */                     String PROCESSINGUNIT_PERCENTAGE = (String)request.getAttribute("PROCESSINGUNIT_PERCENTAGE");
/*      */                     
/*      */ 
/* 2490 */                     String PERMANENT_ADDRESS_PERCENTAGE = (String)request.getAttribute("PERMANENT_ADDRESS_PERCENTAGE");
/* 2491 */                     String TEMPORARY_ADDRESS_PERCENTAGE = (String)request.getAttribute("TEMPORARY_ADDRESS_PERCENTAGE");
/* 2492 */                     String CURRENTINTRACTIVEPERFORMANCEPER = (String)request.getAttribute("CURRENTINTRACTIVEPERFORMANCEPER");
/*      */                     
/*      */ 
/* 2495 */                     String SHAREDPROCESSINGPOOLPER = (String)request.getAttribute("SHAREDPROCESSINGPOOLPER");
/* 2496 */                     String UNCAPPEDCPUCAPACITYPER = (String)request.getAttribute("UNCAPPEDCPUCAPACITYPER");
/* 2497 */                     String CURRENTPROCESSINGCAPACITY = (String)request.getAttribute("CURRENTPROCESSINGCAPACITY");
/*      */                     
/* 2499 */                     String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2500 */                     String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/*      */                     
/* 2502 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2503 */                     ArrayList attribIDs = new ArrayList();
/*      */                     
/* 2505 */                     ArrayList resIDs = (ArrayList)request.getAttribute("resIDs");
/*      */                     
/* 2507 */                     ArrayList buffdata = (ArrayList)request.getAttribute("buffdata");
/*      */                     
/*      */ 
/* 2510 */                     for (int e = 0; e < buffdata.size(); e++)
/*      */                     {
/* 2512 */                       resIDs.add(buffdata.get(e));
/*      */                     }
/*      */                     
/*      */ 
/* 2516 */                     for (int i = 2701; i < 2800; i++)
/*      */                     {
/* 2518 */                       attribIDs.add("" + i);
/*      */                     }
/*      */                     
/* 2521 */                     attribIDs.add(Integer.valueOf(711));
/* 2522 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/*      */ 
/*      */ 
/* 2526 */                     ArrayList attrdata = new ArrayList();
/* 2527 */                     for (int i = 2746; i < 2749; i++)
/*      */                     {
/* 2529 */                       attrdata.add("" + i);
/*      */                     }
/* 2531 */                     attrdata.add(Integer.valueOf(2755));
/* 2532 */                     attrdata.add(Integer.valueOf(2756));
/* 2533 */                     attrdata.add(Integer.valueOf(2745));
/*      */                     
/* 2535 */                     String LASTDC = (String)request.getAttribute("LASTDC");
/* 2536 */                     String NEXTDC = (String)request.getAttribute("NEXTDC");
/* 2537 */                     boolean jobtypeg = ((Boolean)request.getAttribute("jobtypeg")).booleanValue();
/*      */                     
/*      */ 
/* 2540 */                     out.write("\n\n\n <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td width=\"50%\" valign=\"top\">\n\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"conf-mon-table\">\n <tr>\n    <td colspan=\"3\" height=\"26\" class=\"conf-mon-heading\" align=\"left\">");
/* 2541 */                     out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 2542 */                     out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='conf-link'\" class=\"mondetailsHeader\">\n    <td width=\"25%\" align=\"center\">\n\n<table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                     
/*      */ 
/*      */ 
/* 2546 */                     if ((ASP_PERCENTAGE != null) && (!ASP_PERCENTAGE.equals("-1")))
/*      */                     {
/* 2548 */                       String aspp = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + resourceid + "&attributeid=2717&period=0')";
/* 2549 */                       dialGraph.setValue(Long.parseLong(ASP_PERCENTAGE));
/* 2550 */                       out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 2551 */                       out.print(FormatUtil.getString("am.webclient.as400.sapp"));
/* 2552 */                       out.write(45);
/* 2553 */                       out.print(ASP_PERCENTAGE);
/* 2554 */                       out.write("%' >\n\n                 ");
/*      */                       
/* 2556 */                       DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 2557 */                       _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 2558 */                       _jspx_th_awolf_005fdialchart_005f0.setParent(null);
/*      */                       
/* 2560 */                       _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                       
/* 2562 */                       _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                       
/* 2564 */                       _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                       
/* 2566 */                       _jspx_th_awolf_005fdialchart_005f0.setLegend("true");
/*      */                       
/* 2568 */                       _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                       
/* 2570 */                       _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                       
/* 2572 */                       _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                       
/* 2574 */                       _jspx_th_awolf_005fdialchart_005f0.setLink(aspp);
/*      */                       
/* 2576 */                       _jspx_th_awolf_005fdialchart_005f0.setResourceId(resourceid);
/*      */                       
/* 2578 */                       _jspx_th_awolf_005fdialchart_005f0.setAttributeId("2717");
/* 2579 */                       int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 2580 */                       if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 2581 */                         if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 2582 */                           out = _jspx_page_context.pushBody();
/* 2583 */                           _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 2584 */                           _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2587 */                           out.write(10);
/* 2588 */                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 2589 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2592 */                         if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 2593 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2596 */                       if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 2597 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                       }
/*      */                       
/* 2600 */                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 2601 */                       out.write(" </td> ");
/* 2602 */                       out.write(" \n              </tr>\n            ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 2606 */                       out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"disabledtext\" align='center' title='");
/* 2607 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2608 */                       out.write(39);
/* 2609 */                       out.write(32);
/* 2610 */                       out.write(62);
/* 2611 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2612 */                       out.write(" - <b>");
/* 2613 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                         return;
/* 2615 */                       out.write("</b>\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                     }
/* 2617 */                     out.write("\n\n  <tr>\n\n  <td align='center' class='bodytext'>\n");
/* 2618 */                     if ((ASP_PERCENTAGE != null) && (!ASP_PERCENTAGE.equals("-1")))
/*      */                     {
/* 2620 */                       out.write("  <a onmouseover=\"ddrivetip(this,event,'Click here to view history data',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2621 */                       out.print(resourceid);
/* 2622 */                       out.write("&attributeid=2717&period=-7')\" class='new-monitordiv-link'> ");
/* 2623 */                       out.print(FormatUtil.getString("am.webclient.as400.sapp"));
/* 2624 */                       out.write(45);
/* 2625 */                       out.print(ASP_PERCENTAGE);
/* 2626 */                       out.write("%</a>\n     ");
/*      */                     }
/* 2628 */                     out.write("\n  </td>\n  </tr>\n</table></td>\n\n\n\n<td width=\"35%\" align=\"center\">\n\n<table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                     
/*      */ 
/* 2631 */                     if ((CURRENTINTRACTIVEPERFORMANCEPER != null) && (!CURRENTINTRACTIVEPERFORMANCEPER.equals("-1")))
/*      */                     {
/* 2633 */                       String cip = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + resourceid + "&attributeid=2815&period=0')";
/* 2634 */                       dialGraph.setValue(Long.parseLong(CURRENTINTRACTIVEPERFORMANCEPER));
/* 2635 */                       out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 2636 */                       out.print(FormatUtil.getString("am.webclient.as400.currentintp"));
/* 2637 */                       out.write(45);
/* 2638 */                       out.print(CURRENTINTRACTIVEPERFORMANCEPER);
/* 2639 */                       out.write("%' >\n\n");
/*      */                       
/* 2641 */                       DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 2642 */                       _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 2643 */                       _jspx_th_awolf_005fdialchart_005f1.setParent(null);
/*      */                       
/* 2645 */                       _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                       
/* 2647 */                       _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                       
/* 2649 */                       _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                       
/* 2651 */                       _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                       
/* 2653 */                       _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                       
/* 2655 */                       _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                       
/* 2657 */                       _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                       
/* 2659 */                       _jspx_th_awolf_005fdialchart_005f1.setLink(cip);
/*      */                       
/* 2661 */                       _jspx_th_awolf_005fdialchart_005f1.setResourceId(resourceid);
/*      */                       
/* 2663 */                       _jspx_th_awolf_005fdialchart_005f1.setAttributeId("2815");
/* 2664 */                       int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 2665 */                       if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 2666 */                         if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 2667 */                           out = _jspx_page_context.pushBody();
/* 2668 */                           _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 2669 */                           _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2672 */                           out.write(32);
/* 2673 */                           out.write(32);
/* 2674 */                           out.write(" \n            ");
/* 2675 */                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 2676 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2679 */                         if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 2680 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2683 */                       if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 2684 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                       }
/*      */                       
/* 2687 */                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 2688 */                       out.write(" </td> ");
/* 2689 */                       out.write(" \n               </tr>\n            ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 2693 */                       out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"disabledtext\" align='center' title='");
/* 2694 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2695 */                       out.write(39);
/* 2696 */                       out.write(32);
/* 2697 */                       out.write(62);
/* 2698 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2699 */                       out.write(" - <b>");
/* 2700 */                       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */                         return;
/* 2702 */                       out.write("</b>\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                     }
/* 2704 */                     out.write("\n\n  <tr>\n\n  <td align='center' class='bodytext'>\n");
/* 2705 */                     if ((CURRENTINTRACTIVEPERFORMANCEPER != null) && (!CURRENTINTRACTIVEPERFORMANCEPER.equals("-1")))
/*      */                     {
/* 2707 */                       out.write("<a onmouseover=\"ddrivetip(this,event,'Click here to view history data',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2708 */                       out.print(resourceid);
/* 2709 */                       out.write("&attributeid=2815&period=-7')\" class='new-monitordiv-link'>");
/* 2710 */                       out.print(FormatUtil.getString("am.webclient.as400.currentintp"));
/* 2711 */                       out.write(45);
/* 2712 */                       out.print(CURRENTINTRACTIVEPERFORMANCEPER);
/* 2713 */                       out.write("%</a>\n     ");
/*      */                     }
/* 2715 */                     out.write("\n  </td>\n  </tr>\n</table></td>\n\n\n\n    <td width=\"30%\" align=\"center\">\n\n<table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                     
/*      */ 
/* 2718 */                     if ((PROCESSINGUNIT_PERCENTAGE != null) && (!PROCESSINGUNIT_PERCENTAGE.equals("-1")))
/*      */                     {
/* 2720 */                       String pup = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + resourceid + "&attributeid=2720&period=0')";
/* 2721 */                       dialGraph.setValue(Long.parseLong(PROCESSINGUNIT_PERCENTAGE));
/* 2722 */                       out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 2723 */                       out.print(FormatUtil.getString("am.webclient.as400.processingunit"));
/* 2724 */                       out.write(45);
/* 2725 */                       out.print(PROCESSINGUNIT_PERCENTAGE);
/* 2726 */                       out.write("%' >\n\n");
/*      */                       
/* 2728 */                       DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 2729 */                       _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 2730 */                       _jspx_th_awolf_005fdialchart_005f2.setParent(null);
/*      */                       
/* 2732 */                       _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                       
/* 2734 */                       _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                       
/* 2736 */                       _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                       
/* 2738 */                       _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                       
/* 2740 */                       _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                       
/* 2742 */                       _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                       
/* 2744 */                       _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                       
/* 2746 */                       _jspx_th_awolf_005fdialchart_005f2.setLink(pup);
/*      */                       
/* 2748 */                       _jspx_th_awolf_005fdialchart_005f2.setResourceId(resourceid);
/*      */                       
/* 2750 */                       _jspx_th_awolf_005fdialchart_005f2.setAttributeId("2720");
/* 2751 */                       int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 2752 */                       if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 2753 */                         if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 2754 */                           out = _jspx_page_context.pushBody();
/* 2755 */                           _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 2756 */                           _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2759 */                           out.write(32);
/* 2760 */                           out.write(32);
/* 2761 */                           out.write(" \n            ");
/* 2762 */                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 2763 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2766 */                         if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 2767 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2770 */                       if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 2771 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                       }
/*      */                       
/* 2774 */                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 2775 */                       out.write(" </td>  ");
/* 2776 */                       out.write(" \n             </tr>\n            ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 2780 */                       out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"disabledtext\" align='center' title='");
/* 2781 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2782 */                       out.write(39);
/* 2783 */                       out.write(32);
/* 2784 */                       out.write(62);
/* 2785 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2786 */                       out.write(" - <b>");
/* 2787 */                       if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */                         return;
/* 2789 */                       out.write("</b>\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                     }
/* 2791 */                     out.write("\n\n  <tr>\n\n  <td align='center' class='bodytex'>\n");
/* 2792 */                     if ((PROCESSINGUNIT_PERCENTAGE != null) && (!PROCESSINGUNIT_PERCENTAGE.equals("-1")))
/*      */                     {
/* 2794 */                       out.write("<a onmouseover=\"ddrivetip(this,event,'Click here to view history data',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2795 */                       out.print(resourceid);
/* 2796 */                       out.write("&attributeid=2720&period=-7')\" class='new-monitordiv-link'>");
/* 2797 */                       out.print(FormatUtil.getString("am.webclient.as400.processingunit"));
/* 2798 */                       out.write(45);
/* 2799 */                       out.print(PROCESSINGUNIT_PERCENTAGE);
/* 2800 */                       out.write("%</a>\n     ");
/*      */                     }
/* 2802 */                     out.write("\n  </td>\n  </tr>\n</table></td>\n\n\n\n</tr>\n</table>\n\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"conf-mon-table\">\n <tr>\n    <td colspan=\"3\" height=\"26\" class=\"conf-mon-heading\" align=\"left\">");
/* 2803 */                     out.print(FormatUtil.getString("am.webclient.as400.systemstatus"));
/* 2804 */                     out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='conf-link'\" class=\"mondetailsHeader\">\n    <td width=\"30%\" align=\"center\">\n\n<table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                     
/*      */ 
/* 2807 */                     if ((PERMANENT_ADDRESS_PERCENTAGE != null) && (!PERMANENT_ADDRESS_PERCENTAGE.equals("-1")))
/*      */                     {
/* 2809 */                       String pap = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + resourceid + "&attributeid=2721&period=0')";
/* 2810 */                       dialGraph.setValue(Long.parseLong(PERMANENT_ADDRESS_PERCENTAGE));
/* 2811 */                       out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 2812 */                       out.print(FormatUtil.getString("am.webclient.as400.permanenetaddrp"));
/* 2813 */                       out.write(45);
/* 2814 */                       out.print(PERMANENT_ADDRESS_PERCENTAGE);
/* 2815 */                       out.write("%' >\n\n");
/*      */                       
/* 2817 */                       DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 2818 */                       _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 2819 */                       _jspx_th_awolf_005fdialchart_005f3.setParent(null);
/*      */                       
/* 2821 */                       _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                       
/* 2823 */                       _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                       
/* 2825 */                       _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                       
/* 2827 */                       _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                       
/* 2829 */                       _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                       
/* 2831 */                       _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                       
/* 2833 */                       _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                       
/* 2835 */                       _jspx_th_awolf_005fdialchart_005f3.setLink(pap);
/*      */                       
/* 2837 */                       _jspx_th_awolf_005fdialchart_005f3.setResourceId(resourceid);
/*      */                       
/* 2839 */                       _jspx_th_awolf_005fdialchart_005f3.setAttributeId("2721");
/* 2840 */                       int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 2841 */                       if (_jspx_eval_awolf_005fdialchart_005f3 != 0) {
/* 2842 */                         if (_jspx_eval_awolf_005fdialchart_005f3 != 1) {
/* 2843 */                           out = _jspx_page_context.pushBody();
/* 2844 */                           _jspx_th_awolf_005fdialchart_005f3.setBodyContent((BodyContent)out);
/* 2845 */                           _jspx_th_awolf_005fdialchart_005f3.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2848 */                           out.write(32);
/* 2849 */                           out.write(32);
/* 2850 */                           out.write(" \n            ");
/* 2851 */                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f3.doAfterBody();
/* 2852 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2855 */                         if (_jspx_eval_awolf_005fdialchart_005f3 != 1) {
/* 2856 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2859 */                       if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 2860 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                       }
/*      */                       
/* 2863 */                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 2864 */                       out.write(" </td>  ");
/* 2865 */                       out.write(" \n               </tr>\n            ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 2869 */                       out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"disabledtext\" align='center' title='");
/* 2870 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2871 */                       out.write(39);
/* 2872 */                       out.write(32);
/* 2873 */                       out.write(62);
/* 2874 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2875 */                       out.write(" - <b>");
/* 2876 */                       if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */                         return;
/* 2878 */                       out.write("</b>\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                     }
/* 2880 */                     out.write("\n\n  <tr>\n\n  <td align='center' class='bodytext'>\n");
/* 2881 */                     if ((PERMANENT_ADDRESS_PERCENTAGE != null) && (!PERMANENT_ADDRESS_PERCENTAGE.equals("-1")))
/*      */                     {
/* 2883 */                       out.write("<a onmouseover=\"ddrivetip(this,event,'Click here to view history data',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2884 */                       out.print(resourceid);
/* 2885 */                       out.write("&attributeid=2721&period=-7')\" class='new-monitordiv-link'>");
/* 2886 */                       out.print(FormatUtil.getString("am.webclient.as400.permanenetaddrp"));
/* 2887 */                       out.write(45);
/* 2888 */                       out.print(PERMANENT_ADDRESS_PERCENTAGE);
/* 2889 */                       out.write("%</a>\n     ");
/*      */                     }
/* 2891 */                     out.write("\n  </td>\n  </tr>\n</table></td>\n\n\n\n\n\n\n\n    <td width=\"30%\" align=\"center\">\n\n<table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                     
/*      */ 
/* 2894 */                     if ((TEMPORARY_ADDRESS_PERCENTAGE != null) && (!TEMPORARY_ADDRESS_PERCENTAGE.equals("-1")))
/*      */                     {
/* 2896 */                       String tap = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + resourceid + "&attributeid=2722&period=0')";
/* 2897 */                       dialGraph.setValue(Long.parseLong(TEMPORARY_ADDRESS_PERCENTAGE));
/* 2898 */                       out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 2899 */                       out.print(FormatUtil.getString("am.webclient.as400.temproraryaddrp"));
/* 2900 */                       out.write(45);
/* 2901 */                       out.print(TEMPORARY_ADDRESS_PERCENTAGE);
/* 2902 */                       out.write("%' >\n\n");
/*      */                       
/* 2904 */                       DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 2905 */                       _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 2906 */                       _jspx_th_awolf_005fdialchart_005f4.setParent(null);
/*      */                       
/* 2908 */                       _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                       
/* 2910 */                       _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                       
/* 2912 */                       _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                       
/* 2914 */                       _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                       
/* 2916 */                       _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                       
/* 2918 */                       _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                       
/* 2920 */                       _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                       
/* 2922 */                       _jspx_th_awolf_005fdialchart_005f4.setLink(tap);
/*      */                       
/* 2924 */                       _jspx_th_awolf_005fdialchart_005f4.setResourceId(resourceid);
/*      */                       
/* 2926 */                       _jspx_th_awolf_005fdialchart_005f4.setAttributeId("2722");
/* 2927 */                       int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 2928 */                       if (_jspx_eval_awolf_005fdialchart_005f4 != 0) {
/* 2929 */                         if (_jspx_eval_awolf_005fdialchart_005f4 != 1) {
/* 2930 */                           out = _jspx_page_context.pushBody();
/* 2931 */                           _jspx_th_awolf_005fdialchart_005f4.setBodyContent((BodyContent)out);
/* 2932 */                           _jspx_th_awolf_005fdialchart_005f4.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2935 */                           out.write(32);
/* 2936 */                           out.write(" \n            ");
/* 2937 */                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f4.doAfterBody();
/* 2938 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2941 */                         if (_jspx_eval_awolf_005fdialchart_005f4 != 1) {
/* 2942 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2945 */                       if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 2946 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                       }
/*      */                       
/* 2949 */                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 2950 */                       out.write(" </td>  ");
/* 2951 */                       out.write(" \n               </tr>\n            ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 2955 */                       out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"disabledtext\" align='center' title='");
/* 2956 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2957 */                       out.write(39);
/* 2958 */                       out.write(32);
/* 2959 */                       out.write(62);
/* 2960 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2961 */                       out.write(" - <b>");
/* 2962 */                       if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */                         return;
/* 2964 */                       out.write("</b>\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                     }
/* 2966 */                     out.write("\n\n  <tr>\n\n  <td align='center' class='bodytext'>\n");
/* 2967 */                     if ((TEMPORARY_ADDRESS_PERCENTAGE != null) && (!TEMPORARY_ADDRESS_PERCENTAGE.equals("-1")))
/*      */                     {
/* 2969 */                       out.write("<a onmouseover=\"ddrivetip(this,event,'Click here to view history data',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2970 */                       out.print(resourceid);
/* 2971 */                       out.write("&attributeid=2722&period=-7')\" class='new-monitordiv-link'>");
/* 2972 */                       out.print(FormatUtil.getString("am.webclient.as400.temproraryaddrp"));
/* 2973 */                       out.write(45);
/* 2974 */                       out.print(TEMPORARY_ADDRESS_PERCENTAGE);
/* 2975 */                       out.write("%</a\\>\n     ");
/*      */                     }
/* 2977 */                     out.write("\n  </td>\n  </tr>\n</table></td>\n\n\n\n    <td width=\"30%\" align=\"center\">\n\n<table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                     
/*      */ 
/* 2980 */                     if ((DB_PERCENTAGE != null) && (!DB_PERCENTAGE.equals("-1")))
/*      */                     {
/* 2982 */                       String dbp = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + resourceid + "&attributeid=711&period=0')";
/* 2983 */                       dialGraph.setValue(Long.parseLong(DB_PERCENTAGE));
/* 2984 */                       out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 2985 */                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 2986 */                       out.write(45);
/* 2987 */                       out.print(DB_PERCENTAGE);
/* 2988 */                       out.write("%' >\n\n");
/*      */                       
/* 2990 */                       DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 2991 */                       _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 2992 */                       _jspx_th_awolf_005fdialchart_005f5.setParent(null);
/*      */                       
/* 2994 */                       _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                       
/* 2996 */                       _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                       
/* 2998 */                       _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                       
/* 3000 */                       _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                       
/* 3002 */                       _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                       
/* 3004 */                       _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                       
/* 3006 */                       _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                       
/* 3008 */                       _jspx_th_awolf_005fdialchart_005f5.setResourceId(resourceid);
/*      */                       
/* 3010 */                       _jspx_th_awolf_005fdialchart_005f5.setAttributeId("711");
/* 3011 */                       int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 3012 */                       if (_jspx_eval_awolf_005fdialchart_005f5 != 0) {
/* 3013 */                         if (_jspx_eval_awolf_005fdialchart_005f5 != 1) {
/* 3014 */                           out = _jspx_page_context.pushBody();
/* 3015 */                           _jspx_th_awolf_005fdialchart_005f5.setBodyContent((BodyContent)out);
/* 3016 */                           _jspx_th_awolf_005fdialchart_005f5.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3019 */                           out.write(32);
/* 3020 */                           out.write(32);
/* 3021 */                           out.write(32);
/* 3022 */                           out.write(" \n            ");
/* 3023 */                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f5.doAfterBody();
/* 3024 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3027 */                         if (_jspx_eval_awolf_005fdialchart_005f5 != 1) {
/* 3028 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3031 */                       if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 3032 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                       }
/*      */                       
/* 3035 */                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 3036 */                       out.write(" </td>   ");
/* 3037 */                       out.write("\n             </tr>\n            ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 3041 */                       out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"disabledtext\" align='center' title='");
/* 3042 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3043 */                       out.write(39);
/* 3044 */                       out.write(32);
/* 3045 */                       out.write(62);
/* 3046 */                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3047 */                       out.write("- <b>");
/* 3048 */                       if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */                         return;
/* 3050 */                       out.write("</b>\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                     }
/* 3052 */                     out.write("\n\n  <tr>\n\n  <td align='center' class='bodytext'>\n");
/* 3053 */                     if ((DB_PERCENTAGE != null) && (!DB_PERCENTAGE.equals("-1")))
/*      */                     {
/* 3055 */                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 3056 */                       out.write(45);
/* 3057 */                       out.print(DB_PERCENTAGE);
/* 3058 */                       out.write("%\n     ");
/*      */                     }
/* 3060 */                     out.write("\n  </td>\n  </tr>\n</table></td>\n\n\n\n</tr>\n</table>\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\">\n         <tr>\n            <td colspan=\"2\" class=\"conf-mon-heading\" >");
/* 3061 */                     out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 3062 */                     out.write("</td>\n          </tr>\n          <tr>\n            <td width=\"30%\" class=\"monitorinfoodd\">");
/* 3063 */                     out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3064 */                     out.write("</td>\n            <td width=\"70%\" class=\"monitorinfoodd\" title=\"");
/* 3065 */                     out.print(displayname);
/* 3066 */                     out.write(34);
/* 3067 */                     out.write(62);
/* 3068 */                     out.print(getTrimmedText(displayname, 40));
/* 3069 */                     out.write("</td>\n          </tr>\n          <tr>\n            <td  class=\"monitorinfoodd\" >");
/* 3070 */                     out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3071 */                     out.write("</td>\n            <td  class=\"monitorinfoodd\" style=\"padding: 5px 5px 5px 5px;\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3072 */                     out.print(resourceid);
/* 3073 */                     out.write("&attributeid=2702')\">");
/* 3074 */                     out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2702")));
/* 3075 */                     out.write("</a>");
/* 3076 */                     out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "2702" + "#" + "MESSAGE"), "2702", alert.getProperty(resourceid + "#" + "2702"), resourceid));
/* 3077 */                     out.write("\n            ");
/* 3078 */                     if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "2702") != 0) {
/* 3079 */                       out.write("\n            <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 3080 */                       out.print(resourceid + "_2702");
/* 3081 */                       out.write("&monitortype=");
/* 3082 */                       out.print(type);
/* 3083 */                       out.write("')\">");
/* 3084 */                       out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 3085 */                       out.write("</a></span>\n            ");
/*      */                     }
/* 3087 */                     out.write("\n            </td>\n          </tr>\n          <tr>\n            <td  class=\"monitorinfoodd\">");
/* 3088 */                     out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 3089 */                     out.write("</td>\n            <td  class=\"monitorinfoodd\">");
/* 3090 */                     if (_jspx_meth_bean_005fmessage_005f0(_jspx_page_context))
/*      */                       return;
/* 3092 */                     out.write("</td>\n          </tr>\n         <tr>\n          <td  class=\"monitorinfoodd\">");
/* 3093 */                     out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3094 */                     out.write("</td>\n          <td  class=\"monitorinfoodd\">");
/* 3095 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 3097 */                     out.write("</td>\n         </tr>\n          <tr>\n\n           ");
/* 3098 */                     if (model1 != null) {
/* 3099 */                       out.write("\n            <td class=\"monitorinfoodd\">");
/* 3100 */                       out.print(FormatUtil.getString("am.webclient.as400.model"));
/* 3101 */                       out.write("</td>\n            <td class=\"monitorinfoodd\">");
/* 3102 */                       out.print(model1);
/* 3103 */                       out.write("</td>\n           ");
/*      */                     } else {
/* 3105 */                       out.write("\n            <td class=\"monitorinfoodd\">");
/* 3106 */                       out.print(FormatUtil.getString("am.webclient.as400.model"));
/* 3107 */                       out.write("</td>\n            <td class=\"monitorinfoodd\">-</td>\n           ");
/*      */                     }
/* 3109 */                     out.write("\n\n\n          </tr>\n\n          <tr>\n           ");
/* 3110 */                     if (serialno1 != null) {
/* 3111 */                       out.write("\n\t   <td  class=\"monitorinfoodd\">");
/* 3112 */                       out.print(FormatUtil.getString("am.webclient.as400.serial"));
/* 3113 */                       out.write("</td>\n\t   <td  class=\"monitorinfoodd\">");
/* 3114 */                       out.print(serialno1);
/* 3115 */                       out.write("</td>\n           ");
/*      */                     } else {
/* 3117 */                       out.write("\n\t   <td  class=\"monitorinfoodd\">");
/* 3118 */                       out.print(FormatUtil.getString("am.webclient.as400.serial"));
/* 3119 */                       out.write("</td>\n\t   <td  class=\"monitorinfoodd\">-</td>\n           ");
/*      */                     }
/* 3121 */                     out.write("\n          </tr>\n\n\n          <tr>\n            <td  class=\"monitorinfoodd\">");
/* 3122 */                     out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3123 */                     out.write("</td>\n            <td  class=\"monitorinfoodd\">");
/* 3124 */                     out.print(formatDT(LASTDC));
/* 3125 */                     out.write("</td>\n          </tr>\n          <tr>\n            <td  class=\"monitorinfoodd\">");
/* 3126 */                     out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3127 */                     out.write("</td>\n            <td  class=\"monitorinfoodd\">");
/* 3128 */                     out.print(formatDT(NEXTDC));
/* 3129 */                     out.write("</td>\n          </tr>\n          ");
/* 3130 */                     JspRuntimeLibrary.include(request, response, "../MyField_trstrip.jsp", out, false);
/* 3131 */                     out.write("\n          <tr>\n              <td colspan=\"2\" style=\"border-top:1px solid #ececec; height:27px;\">\n\n\t\t\t<div align=\"left\" id=\"divshow\" style=\"display:block;\" onclick=\"toggleSlide('divhide','divshow','subgroup');\">\n\t\t\t<span class=\"bodytext\" style=\"cursor: pointer;\">\n                            &nbsp; <img style=\"cursor: pointer;\" src=\"../images/icon_plus.gif\" width=\"9\" height=\"9\">&nbsp;");
/* 3132 */                     out.print(FormatUtil.getString("am.webclient.as400.showsystemconfig.text"));
/* 3133 */                     out.write("&nbsp;\n\t\t\t</span>\n\t\t\t</div>\n\n\t\t\t<div align=\"left\" id=\"divhide\" style=\"display:none;\" onclick=\"toggleSlide('divshow','divhide','subgroup');\">\n                         <span class=\"bodytext\" style=\"cursor: pointer;\">\n                            &nbsp; <img style=\"cursor: pointer;\" src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\">&nbsp;");
/* 3134 */                     out.print(FormatUtil.getString("am.webclient.as400.hidesystemconfig.text"));
/* 3135 */                     out.write("&nbsp;\n\t\t\t</span>\n\t\t\t</div>\n              </td>\n          </tr>\n                        <tr>\n                            <td colspan=\"2\">\n\t\t\t\t <div id=\"subgroup\" style=\"display: none; \">\n\t\t\t\t <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t<tr>\n          \t\t\t");
/* 3136 */                     if (securitylevel1 != null) {
/* 3137 */                       out.write("\n\t\t\t\t  <td style=\"border-top:1px solid #ececec;\" class=\"monitorinfoodd\" width=\"60%\">");
/* 3138 */                       out.print(FormatUtil.getString("am.webclient.as400.securitylevel"));
/* 3139 */                       out.write("</td>\n\t\t\t\t  <td style=\"border-top:1px solid #ececec;\" class=\"monitorinfoodd\" width=\"40%\">");
/* 3140 */                       out.print(securitylevel1);
/* 3141 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3143 */                       out.write("\n\t\t\t\t  <td style=\"border-top:1px solid #ececec;\" class=\"monitorinfoodd\" width=\"60%\">");
/* 3144 */                       out.print(FormatUtil.getString("am.webclient.as400.securitylevel"));
/* 3145 */                       out.write("</td>\n\t\t\t\t  <td style=\"border-top:1px solid #ececec;\" class=\"monitorinfoodd\" width=\"40%\">-</td>\n          \t\t\t ");
/*      */                     }
/* 3147 */                     out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n          \t\t\t ");
/* 3148 */                     if (version1 != null) {
/* 3149 */                       out.write("\n\t\t\t\t  <td class=\"monitorinfoodd\" >");
/* 3150 */                       out.print(FormatUtil.getString("am.webclient.as400.version"));
/* 3151 */                       out.write("</td>\n\t\t\t\t  <td class=\"monitorinfoodd\" >");
/* 3152 */                       out.print(version1);
/* 3153 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3155 */                       out.write("\n\t\t\t\t  <td class=\"monitorinfoodd\" >");
/* 3156 */                       out.print(FormatUtil.getString("am.webclient.as400.version"));
/* 3157 */                       out.write("</td>\n\t\t\t\t  <td class=\"monitorinfoodd\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3159 */                     out.write("\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n          \t\t\t ");
/* 3160 */                     if (previoussystemend1 != null) {
/* 3161 */                       out.write("\n\t\t\t\t  <td class=\"monitorinfoodd\" >");
/* 3162 */                       out.print(FormatUtil.getString("am.webclient.as400.previoussystemend"));
/* 3163 */                       out.write("</td>\n\t\t\t\t  <td class=\"monitorinfoodd\" >");
/* 3164 */                       out.print(previoussystemend1);
/* 3165 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3167 */                       out.write("\n\t\t\t\t  <td class=\"monitorinfoodd\" >");
/* 3168 */                       out.print(FormatUtil.getString("am.webclient.as400.previoussystemend"));
/* 3169 */                       out.write("</td>\n\t\t\t\t  <td class=\"monitorinfoodd\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3171 */                     out.write("\n\t\t\t\t</tr>\n\t\t\t\t <tr>\n          \t\t\t ");
/* 3172 */                     if (autodeviceconfiguration != null) {
/* 3173 */                       out.write("\n\t\t\t\t  <td class=\"monitorinfoodd\" >");
/* 3174 */                       out.print(FormatUtil.getString("am.webclient.as400.autodeviceconfiguration"));
/* 3175 */                       out.write("</td>\n\t\t\t\t  <td class=\"monitorinfoodd\" >");
/* 3176 */                       out.print(autodeviceconfiguration);
/* 3177 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3179 */                       out.write("\n\t\t\t\t  <td class=\"monitorinfoodd\" >");
/* 3180 */                       out.print(FormatUtil.getString("am.webclient.as400.autodeviceconfiguration"));
/* 3181 */                       out.write("</td>\n\t\t\t\t  <td class=\"monitorinfoodd\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3183 */                     out.write("\n\t\t\t\t </tr>\n\t\t\t\t <tr>\n          \t\t\t ");
/* 3184 */                     if (systemconsole != null) {
/* 3185 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3186 */                       out.print(FormatUtil.getString("am.webclient.as400.systemconsole"));
/* 3187 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3188 */                       out.print(systemconsole);
/* 3189 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3191 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3192 */                       out.print(FormatUtil.getString("am.webclient.as400.systemconsole"));
/* 3193 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3195 */                     out.write("\n\t\t\t\t </tr>\n\t\t\t\t <tr>\n          \t\t\t ");
/* 3196 */                     if (jobmessagequeueinitialsize != null) {
/* 3197 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3198 */                       out.print(FormatUtil.getString("am.webclient.as400.jobmessagequeueinitialsize"));
/* 3199 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3200 */                       out.print(jobmessagequeueinitialsize);
/* 3201 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3203 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3204 */                       out.print(FormatUtil.getString("am.webclient.as400.jobmessagequeueinitialsize"));
/* 3205 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3207 */                     out.write("\n\t\t\t\t </tr>\n\t\t\t\t <tr>\n          \t\t\t ");
/* 3208 */                     if (jobmessaequeuemaximumsize != null) {
/* 3209 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3210 */                       out.print(FormatUtil.getString("am.webclient.as400.jobmessagequeuemaximumsize"));
/* 3211 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3212 */                       out.print(jobmessaequeuemaximumsize);
/* 3213 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3215 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3216 */                       out.print(FormatUtil.getString("am.webclient.as400.jobmessagequeuemaximumsize"));
/* 3217 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3219 */                     out.write("\n\n\t\t\t\t </tr>\n\t\t\t\t <tr>\n          \t\t\t ");
/* 3220 */                     if (spoolingcontrolinitialsize != null) {
/* 3221 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3222 */                       out.print(FormatUtil.getString("am.webclient.as400.spoolingcontrolinitialsize"));
/* 3223 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3224 */                       out.print(spoolingcontrolinitialsize);
/* 3225 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3227 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3228 */                       out.print(FormatUtil.getString("am.webclient.as400.spoolingcontrolinitialsize"));
/* 3229 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3231 */                     out.write("\n\n\t\t\t\t </tr>\n\t\t\t\t <tr>\n          \t\t\t ");
/* 3232 */                     if (maximumjobsallowed != null) {
/* 3233 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3234 */                       out.print(FormatUtil.getString("am.webclient.as400.maximumjobsallowed"));
/* 3235 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3236 */                       out.print(maximumjobsallowed);
/* 3237 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3239 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3240 */                       out.print(FormatUtil.getString("am.webclient.as400.maximumjobsallowed"));
/* 3241 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3243 */                     out.write("\n\n\t\t\t\t </tr>\n\t\t\t\t<tr>\n          \t\t\t ");
/* 3244 */                     if (passwordvaliddays != null) {
/* 3245 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3246 */                       out.print(FormatUtil.getString("am.webclient.as400.passwordvaliddays"));
/* 3247 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3248 */                       out.print(passwordvaliddays);
/* 3249 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3251 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd\" >");
/* 3252 */                       out.print(FormatUtil.getString("am.webclient.as400.passwordvaliddays"));
/* 3253 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3255 */                     out.write("\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n          \t\t\t ");
/* 3256 */                     if (queryprocessingtimelimit != null) {
/* 3257 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd-noborder\" >");
/* 3258 */                       out.print(FormatUtil.getString("am.webclient.as400.queryprocessingtimelimit"));
/* 3259 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd-noborder\" >");
/* 3260 */                       out.print(queryprocessingtimelimit);
/* 3261 */                       out.write("</td>\n                                 ");
/*      */                     } else {
/* 3263 */                       out.write("\n\t\t\t\t   <td class=\"monitorinfoodd-noborder\" >");
/* 3264 */                       out.print(FormatUtil.getString("am.webclient.as400.queryprocessingtimelimit"));
/* 3265 */                       out.write("</td>\n\t\t\t\t   <td class=\"monitorinfoodd-noborder\" >-</td>\n          \t\t\t ");
/*      */                     }
/* 3267 */                     out.write("\n\t\t\t\t</tr>\n\t\t\t\t </table>\n\t\t\t\t </div>\n\t\t\t</td>\n\t\t\t</tr>\n \t\t\t</table>\n\n\n\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3268 */                     JspRuntimeLibrary.include(request, response, "../MyField_div.jsp", out, false);
/* 3269 */                     out.write("</td></tr></table>\n\n ");
/*      */                     
/* 3271 */                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3272 */                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3273 */                     _jspx_th_c_005fif_005f0.setParent(null);
/*      */                     
/* 3275 */                     _jspx_th_c_005fif_005f0.setTest("${not disableDisk}");
/* 3276 */                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3277 */                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                       for (;;) {
/* 3279 */                         out.write("\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"conf-mon-table\">\n <tr>\n  <td width=\"100%\" height=\"29\" class=\"conf-mon-heading\">");
/* 3280 */                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 3281 */                         out.write("<a name=\"Disk Utilization\"></a> </td>\n </tr>\n \n\t<tr>\n\t <td align=\"center\" style=\"padding : 20px 0px 20px 0px;\">\n      ");
/*      */                         
/* 3283 */                         StackBarChart _jspx_th_awolf_005fstackbarchart_005f0 = (StackBarChart)this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors.get(StackBarChart.class);
/* 3284 */                         _jspx_th_awolf_005fstackbarchart_005f0.setPageContext(_jspx_page_context);
/* 3285 */                         _jspx_th_awolf_005fstackbarchart_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 3287 */                         _jspx_th_awolf_005fstackbarchart_005f0.setDataSetProducer("as400diskgraph");
/*      */                         
/* 3289 */                         _jspx_th_awolf_005fstackbarchart_005f0.setWidth("600");
/*      */                         
/* 3291 */                         _jspx_th_awolf_005fstackbarchart_005f0.setHeight("280");
/*      */                         
/* 3293 */                         _jspx_th_awolf_005fstackbarchart_005f0.setLegend("true");
/*      */                         
/* 3295 */                         _jspx_th_awolf_005fstackbarchart_005f0.setUrl(false);
/*      */                         
/* 3297 */                         _jspx_th_awolf_005fstackbarchart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.diskpartition.text"));
/*      */                         
/* 3299 */                         _jspx_th_awolf_005fstackbarchart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.common.axisname.valueinper.text"));
/*      */                         
/* 3301 */                         _jspx_th_awolf_005fstackbarchart_005f0.setBarcolors(new java.awt.Paint[] { new java.awt.Color(255, 89, 89), new java.awt.Color(152, 255, 150) });
/* 3302 */                         int _jspx_eval_awolf_005fstackbarchart_005f0 = _jspx_th_awolf_005fstackbarchart_005f0.doStartTag();
/* 3303 */                         if (_jspx_eval_awolf_005fstackbarchart_005f0 != 0) {
/* 3304 */                           if (_jspx_eval_awolf_005fstackbarchart_005f0 != 1) {
/* 3305 */                             out = _jspx_page_context.pushBody();
/* 3306 */                             _jspx_th_awolf_005fstackbarchart_005f0.setBodyContent((BodyContent)out);
/* 3307 */                             _jspx_th_awolf_005fstackbarchart_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3310 */                             out.write("\n      ");
/* 3311 */                             int evalDoAfterBody = _jspx_th_awolf_005fstackbarchart_005f0.doAfterBody();
/* 3312 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3315 */                           if (_jspx_eval_awolf_005fstackbarchart_005f0 != 1) {
/* 3316 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3319 */                         if (_jspx_th_awolf_005fstackbarchart_005f0.doEndTag() == 5) {
/* 3320 */                           this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors.reuse(_jspx_th_awolf_005fstackbarchart_005f0); return;
/*      */                         }
/*      */                         
/* 3323 */                         this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors.reuse(_jspx_th_awolf_005fstackbarchart_005f0);
/* 3324 */                         out.write(32);
/* 3325 */                         out.write("\n     </td>\n    </tr>\n\n   </table>\n\n\n ");
/* 3326 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3327 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3331 */                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3332 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                     }
/*      */                     else {
/* 3335 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3336 */                       out.write(10);
/* 3337 */                       out.write(32);
/* 3338 */                       out.write(32);
/*      */                       
/* 3340 */                       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3341 */                       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3342 */                       _jspx_th_c_005fif_005f1.setParent(null);
/*      */                       
/* 3344 */                       _jspx_th_c_005fif_005f1.setTest("${not disablePool}");
/* 3345 */                       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3346 */                       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                         for (;;) {
/* 3348 */                           out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n        <tr>\n          <td class=\"conf-mon-heading\" >");
/* 3349 */                           out.print(FormatUtil.getString("am.webclient.as400.poolsize"));
/* 3350 */                           out.write("</td>\n        </tr>\n        <tr>\n          <td align=\"center\" style=\"padding : 20px 0px 20px 0px;\">\n            ");
/*      */                           
/* 3352 */                           wlsGraph.setParam(request.getParameter("resourceid"), "AS400_POOL");
/*      */                           
/* 3354 */                           out.write("\n            ");
/* 3355 */                           if (_jspx_meth_awolf_005fbarchart_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 3357 */                           out.write(" </td>   ");
/* 3358 */                           out.write("\n        </tr>\n      </table>\n\n\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n        <tr>\n           <td class=\"conf-mon-heading\" >");
/* 3359 */                           out.print(FormatUtil.getString("am.webclient.as400.dbpages"));
/* 3360 */                           out.write("</td>\n        </tr>\n        <tr>\n          <td align=\"center\" style=\"padding : 20px 0px 20px 0px;\">\n            ");
/*      */                           
/* 3362 */                           wlsGraph.setParam(request.getParameter("resourceid"), "AS400_DBPAGES");
/*      */                           
/* 3364 */                           out.write("\n            ");
/* 3365 */                           if (_jspx_meth_awolf_005fbarchart_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 3367 */                           out.write(" </td>   ");
/* 3368 */                           out.write("\n        </tr>\n      </table>\n\n\t      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n        <tr>\n           <td class=\"conf-mon-heading\" >");
/* 3369 */                           out.print(FormatUtil.getString("am.webclient.as400.dbfaults"));
/* 3370 */                           out.write("</td>\n        </tr>\n        <tr>\n          <td align=\"center\" style=\"padding : 20px 0px 20px 0px;\">\n            ");
/*      */                           
/* 3372 */                           wlsGraph.setParam(request.getParameter("resourceid"), "AS400_DBFAULTS");
/*      */                           
/* 3374 */                           out.write("\n            ");
/* 3375 */                           if (_jspx_meth_awolf_005fbarchart_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 3377 */                           out.write(" </td>   ");
/* 3378 */                           out.write("\n        </tr>\n      </table>\n ");
/* 3379 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3380 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3384 */                       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3385 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                       }
/*      */                       else {
/* 3388 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3389 */                         out.write("\n\n</td>\n\n\n\n\n\n<td width=\"1%\">&nbsp;</td>\n\n\n\n\n\n<td width=\"49%\" valign=\"top\">\n\n\n\n\n\n\n<div id=\"availabilitydiv\"></div>\n<div id=\"performancediv\"></div>\n\n\n\n");
/* 3390 */                         if (jobtypeg)
/*      */                         {
/* 3392 */                           out.write("\n\n    <table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\">\n     ");
/* 3393 */                           as400graph.settype("JOBTYPES");
/* 3394 */                           out.write("\n     <tr>\n     <td class=\"conf-mon-heading\" >");
/* 3395 */                           out.print(FormatUtil.getString("am.webclient.as400.jobcounts"));
/* 3396 */                           out.write("</td>\n     </tr>\n      <tr>\n        <td align=\"center\">\n          ");
/* 3397 */                           if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_page_context))
/*      */                             return;
/* 3399 */                           out.write(" </td> </tr>\n          <tr><td>&nbsp;</td></tr>\n    </table>\n\n    ");
/*      */                         } else {
/* 3401 */                           out.write("\n\n    <table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\">\n\t  <tr>\n\t    <td class=\"conf-mon-heading\" >");
/* 3402 */                           if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */                             return;
/* 3404 */                           out.write("</td>\n\t  </tr>\n\t  <tr>\n        <td height=\"150\" colspan=\"0\" class=\"disabledtext\" align='center' title='");
/* 3405 */                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3406 */                           out.write(39);
/* 3407 */                           out.write(32);
/* 3408 */                           out.write(62);
/* 3409 */                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3410 */                           out.write("          </td>\n\t </tr>\n    </table>\n\n    ");
/*      */                         }
/* 3412 */                         out.write(10);
/* 3413 */                         out.write(10);
/* 3414 */                         out.write(9);
/* 3415 */                         if ((SHAREDPROCESSINGPOOLPER != null) && (UNCAPPEDCPUCAPACITYPER != null) && (CURRENTPROCESSINGCAPACITY != null))
/*      */                         {
/* 3417 */                           out.write("\n\n<table width=\"100%\"  border=\"0\"  cellpadding=\"10\" cellspacing=\"0\" class=\"conf-mon-table\">\n\n     <tr>\n     <td colspan=\"3\" class=\"conf-mon-heading\" >");
/* 3418 */                           out.print(FormatUtil.getString("am.webclient.as400.systeminfo"));
/* 3419 */                           out.write("</td>\n     </tr>\n\n  <tr>\n\n\n\n\n  ");
/*      */                           
/* 3421 */                           if (SHAREDPROCESSINGPOOLPER != null)
/*      */                           {
/* 3423 */                             out.write("\n\n      <td width=\"30%\"  align=\"center\"  valign=\"middle\" >\n\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t  <tr>\n\t    <td align=\"center\"> ");
/* 3424 */                             JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(SHAREDPROCESSINGPOOLPER), request.getCharacterEncoding()), out, true);
/* 3425 */                             out.write(" </td>\n\t  </tr>\n\t</table>\n\t<span class=\"bodytext\" ><br>");
/* 3426 */                             out.print(FormatUtil.getString("am.webclient.as400.sharedprocessing"));
/* 3427 */                             out.write("</span>\n      </td>\n");
/*      */                           }
/* 3429 */                           out.write("\n\n\n   ");
/*      */                           
/* 3431 */                           if (UNCAPPEDCPUCAPACITYPER != null)
/*      */                           {
/* 3433 */                             out.write("\n\n\n       <td width=\"30%\" align=\"center\" valign=\"middle\" >\n\n \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n \t  <tr>\n \t    <td align=\"center\"> ");
/* 3434 */                             JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(UNCAPPEDCPUCAPACITYPER), request.getCharacterEncoding()), out, true);
/* 3435 */                             out.write(" </td>\n \t  </tr>\n \t</table>\n\t<span class=\"bodytext\" ><br>");
/* 3436 */                             out.print(FormatUtil.getString("am.webclient.as400.uncappedcpu"));
/* 3437 */                             out.write("</span>\n\n       </td>\n ");
/*      */                           }
/* 3439 */                           out.write("\n\n\n   ");
/*      */                           
/* 3441 */                           if (CURRENTPROCESSINGCAPACITY != null)
/*      */                           {
/* 3443 */                             out.write("\n\n       <td width=\"30%\" align=\"center\"  >\n\n \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n \t  <tr>\n \t    <td align=\"center\"> ");
/* 3444 */                             JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(CURRENTPROCESSINGCAPACITY), request.getCharacterEncoding()), out, true);
/* 3445 */                             out.write(" </td>\n \t  </tr>\n \t</table>\n\t<span class=\"bodytext\" style=\"text-align:left;\"><br>");
/* 3446 */                             out.print(FormatUtil.getString("am.webclient.as400.currentproccapacity"));
/* 3447 */                             out.write("</span>\n       </td>\n\n        ");
/*      */                           }
/* 3449 */                           out.write("\n\n \t  </tr>\n\n  </table>\n\n\n\n");
/*      */                         } else {
/* 3451 */                           out.write("\n    <table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n     <tr>\n     <td colspan=\"3\" class=\"conf-mon-heading\" >");
/* 3452 */                           if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */                             return;
/* 3454 */                           out.write("</td>\n     </tr>\n\t  <tr>\n        <td height=\"318\" colspan=\"0\" class=\"disabledtext\" align='center' title='");
/* 3455 */                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3456 */                           out.write(39);
/* 3457 */                           out.write(32);
/* 3458 */                           out.write(62);
/* 3459 */                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3460 */                           out.write("          </td>\n\t </tr>\n    </table>\n\n");
/*      */                         }
/* 3462 */                         out.write(10);
/* 3463 */                         out.write(10);
/* 3464 */                         out.write(32);
/*      */                         
/* 3466 */                         IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3467 */                         _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3468 */                         _jspx_th_c_005fif_005f2.setParent(null);
/*      */                         
/* 3470 */                         _jspx_th_c_005fif_005f2.setTest("${not disablePool}");
/* 3471 */                         int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3472 */                         if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                           for (;;) {
/* 3474 */                             out.write("\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n        <tr>\n          <td class=\"conf-mon-heading\" >");
/* 3475 */                             out.print(FormatUtil.getString("am.webclient.as400.reservedsize"));
/* 3476 */                             out.write("</td>\n        </tr>\n        <tr>\n          <td align=\"center\" style=\"padding : 20px 0px 20px 0px;\">\n            ");
/*      */                             
/* 3478 */                             wlsGraph.setParam(request.getParameter("resourceid"), "AS400_RESIZE");
/*      */                             
/* 3480 */                             out.write("\n            ");
/* 3481 */                             if (_jspx_meth_awolf_005fbarchart_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                               return;
/* 3483 */                             out.write(" </td>\n        </tr>\n      </table>\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n        <tr>\n          <td class=\"conf-mon-heading\" >");
/* 3484 */                             out.print(FormatUtil.getString("am.webclient.as400.nondbpages"));
/* 3485 */                             out.write("</td>\n        </tr>\n        <tr>\n          <td align=\"center\" style=\"padding : 20px 0px 20px 0px;\">\n            ");
/*      */                             
/* 3487 */                             wlsGraph.setParam(request.getParameter("resourceid"), "AS400_NDBPAGES");
/*      */                             
/* 3489 */                             out.write("\n            ");
/* 3490 */                             if (_jspx_meth_awolf_005fbarchart_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                               return;
/* 3492 */                             out.write(" </td>\n        </tr>\n      </table>\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n        <tr>\n          <td class=\"conf-mon-heading\" >");
/* 3493 */                             out.print(FormatUtil.getString("am.webclient.as400.nondbfaults"));
/* 3494 */                             out.write("</td>\n        </tr>\n        <tr>\n          <td align=\"center\" style=\"padding : 20px 0px 20px 0px;\">\n            ");
/*      */                             
/* 3496 */                             wlsGraph.setParam(request.getParameter("resourceid"), "AS400_NDBFAULTS");
/*      */                             
/* 3498 */                             out.write("\n            ");
/* 3499 */                             if (_jspx_meth_awolf_005fbarchart_005f5(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                               return;
/* 3501 */                             out.write(" </td>\n        </tr>\n      </table>\n");
/* 3502 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3503 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3507 */                         if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3508 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */                         }
/*      */                         else {
/* 3511 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3512 */                           out.write("\n\n\n\n</td>\n</tr>\n</table>\n<script>\n\nvar http=getHTTPObject();\nURL='/dashboard.do?method=generateAvailabilityHistory&type=");
/* 3513 */                           out.print(type);
/* 3514 */                           out.write("&resourceId=");
/* 3515 */                           out.print(resourceid);
/* 3516 */                           out.write("&period=4&isConfMonitor=true';//No I18N\nhttp.open(\"GET\",URL,true);\nhttp.onreadystatechange = handleAvailability;\nhttp.send(null);\nvar http1=getHTTPObject();\nURL1=\"/dashboard.do?method=generateHealthHistory&type=");
/* 3517 */                           out.print(type);
/* 3518 */                           out.write("&resourceId=");
/* 3519 */                           out.print(resourceid);
/* 3520 */                           out.write("&period=4&isConfMonitor=true\";//No I18N\nhttp1.open(\"GET\",URL1,true);\nhttp1.onreadystatechange = handlePerformance;\nhttp1.send(null);\n\nfunction handleAvailability()\n{\n\t//alert(\"http.readyState:\"+http.readyState);\n\tif(http.readyState == 4)\n        {\n\t\t//alert(\"http.status\"+http.status);\n                if (http.status == 200)\n                {\n\n                \tvar ele = document.getElementById(\"availabilitydiv\");\n                \tif(ele){\n\t\t\t\t\t\tele.innerHTML = http.responseText;\n                \t}\n\n\n\t\t}\n\t}\n}\nfunction handlePerformance()\n{\n\t//alert(\"http.readyState:\"+http1.readyState);\n\tif(http1.readyState == 4)\n        {\n\t\t//alert(\"http.status\"+http.status);\n                if (http1.status == 200)\n                {\n\n                \tvar ele = document.getElementById(\"performancediv\");\n                \tif(ele){\n\t\t\t\t\t\tele.innerHTML = http1.responseText;\n                \t}\n\n\n\t\t}\n\t}\n}\n\n\n\n</script>\n\n");
/* 3521 */                           if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */                             return;
/* 3523 */                           out.write(10);
/*      */                         }
/* 3525 */                       } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3526 */         out = _jspx_out;
/* 3527 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3528 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3529 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3532 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3538 */     PageContext pageContext = _jspx_page_context;
/* 3539 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3541 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f0 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3542 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3543 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 3545 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.as400.sapp");
/* 3546 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3547 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3548 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3549 */       return true;
/*      */     }
/* 3551 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3552 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3557 */     PageContext pageContext = _jspx_page_context;
/* 3558 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3560 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f1 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3561 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3562 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*      */     
/* 3564 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.as400.currentintp");
/* 3565 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3566 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3567 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3568 */       return true;
/*      */     }
/* 3570 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3571 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3576 */     PageContext pageContext = _jspx_page_context;
/* 3577 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3579 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f2 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3580 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3581 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/*      */     
/* 3583 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.as400.processingunit");
/* 3584 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3585 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3586 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3587 */       return true;
/*      */     }
/* 3589 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3590 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3595 */     PageContext pageContext = _jspx_page_context;
/* 3596 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3598 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f3 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3599 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3600 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/*      */     
/* 3602 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.as400.permanenetaddrp");
/* 3603 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3604 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3605 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3606 */       return true;
/*      */     }
/* 3608 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3609 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3614 */     PageContext pageContext = _jspx_page_context;
/* 3615 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3617 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f4 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3618 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3619 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/*      */     
/* 3621 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.as400.temproraryaddrp");
/* 3622 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3623 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3624 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3625 */       return true;
/*      */     }
/* 3627 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3628 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3633 */     PageContext pageContext = _jspx_page_context;
/* 3634 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3636 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f5 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3637 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3638 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/*      */     
/* 3640 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.hostResource.servers.diskutil");
/* 3641 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3642 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3643 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3644 */       return true;
/*      */     }
/* 3646 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3647 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3652 */     PageContext pageContext = _jspx_page_context;
/* 3653 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3655 */     org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 3656 */     _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3657 */     _jspx_th_bean_005fmessage_005f0.setParent(null);
/*      */     
/* 3659 */     _jspx_th_bean_005fmessage_005f0.setKey("am.webclient.as400.server");
/* 3660 */     int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 3661 */     if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 3662 */       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 3663 */       return true;
/*      */     }
/* 3665 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 3666 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3671 */     PageContext pageContext = _jspx_page_context;
/* 3672 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3674 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3675 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3676 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3678 */     _jspx_th_c_005fout_005f0.setValue("${resourcename}");
/* 3679 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3680 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3681 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3682 */       return true;
/*      */     }
/* 3684 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3685 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3690 */     PageContext pageContext = _jspx_page_context;
/* 3691 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3693 */     BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 3694 */     _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 3695 */     _jspx_th_awolf_005fbarchart_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3697 */     _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 3699 */     _jspx_th_awolf_005fbarchart_005f0.setWidth("600");
/*      */     
/* 3701 */     _jspx_th_awolf_005fbarchart_005f0.setHeight("280");
/*      */     
/* 3703 */     _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */     
/* 3705 */     _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel("Pool");
/*      */     
/* 3707 */     _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel("");
/* 3708 */     int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 3709 */     if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 3710 */       if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 3711 */         out = _jspx_page_context.pushBody();
/* 3712 */         _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 3713 */         _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3716 */         out.write("\n\t\t    ");
/* 3717 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fbarchart_005f0, _jspx_page_context))
/* 3718 */           return true;
/* 3719 */         out.write(9);
/* 3720 */         out.write("\n            ");
/* 3721 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 3722 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3725 */       if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 3726 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3729 */     if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 3730 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 3731 */       return true;
/*      */     }
/* 3733 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 3734 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fbarchart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3739 */     PageContext pageContext = _jspx_page_context;
/* 3740 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3742 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3743 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3744 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fbarchart_005f0);
/*      */     
/* 3746 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3747 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3748 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3749 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3750 */         out = _jspx_page_context.pushBody();
/* 3751 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3752 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3755 */         out.write("\n\t\t\t\t");
/* 3756 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3757 */           return true;
/* 3758 */         out.write("\n\t\t\t\t");
/* 3759 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3760 */           return true;
/* 3761 */         out.write("\n\t\t\t\t");
/* 3762 */         if (_jspx_meth_awolf_005fparam_005f2(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3763 */           return true;
/* 3764 */         out.write("\n\t\t\t\t");
/* 3765 */         if (_jspx_meth_awolf_005fparam_005f3(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3766 */           return true;
/* 3767 */         out.write("\n\t\t    ");
/* 3768 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3769 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3772 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3773 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3776 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3777 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3778 */       return true;
/*      */     }
/* 3780 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3781 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3786 */     PageContext pageContext = _jspx_page_context;
/* 3787 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3789 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3790 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3791 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3793 */     _jspx_th_awolf_005fparam_005f0.setName("0");
/*      */     
/* 3795 */     _jspx_th_awolf_005fparam_005f0.setValue("#C6454A");
/* 3796 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3797 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3798 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3799 */       return true;
/*      */     }
/* 3801 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3802 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3807 */     PageContext pageContext = _jspx_page_context;
/* 3808 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3810 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3811 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3812 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3814 */     _jspx_th_awolf_005fparam_005f1.setName("1");
/*      */     
/* 3816 */     _jspx_th_awolf_005fparam_005f1.setValue("#00FF00");
/* 3817 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3818 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3819 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3820 */       return true;
/*      */     }
/* 3822 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f2(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3828 */     PageContext pageContext = _jspx_page_context;
/* 3829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3831 */     AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3832 */     _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 3833 */     _jspx_th_awolf_005fparam_005f2.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3835 */     _jspx_th_awolf_005fparam_005f2.setName("2");
/*      */     
/* 3837 */     _jspx_th_awolf_005fparam_005f2.setValue("#FE7301");
/* 3838 */     int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 3839 */     if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 3840 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 3841 */       return true;
/*      */     }
/* 3843 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 3844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f3(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3849 */     PageContext pageContext = _jspx_page_context;
/* 3850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3852 */     AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3853 */     _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 3854 */     _jspx_th_awolf_005fparam_005f3.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3856 */     _jspx_th_awolf_005fparam_005f3.setName("3");
/*      */     
/* 3858 */     _jspx_th_awolf_005fparam_005f3.setValue("#589037");
/* 3859 */     int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 3860 */     if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 3861 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 3862 */       return true;
/*      */     }
/* 3864 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 3865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3870 */     PageContext pageContext = _jspx_page_context;
/* 3871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3873 */     BarChart _jspx_th_awolf_005fbarchart_005f1 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 3874 */     _jspx_th_awolf_005fbarchart_005f1.setPageContext(_jspx_page_context);
/* 3875 */     _jspx_th_awolf_005fbarchart_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3877 */     _jspx_th_awolf_005fbarchart_005f1.setDataSetProducer("wlsGraph");
/*      */     
/* 3879 */     _jspx_th_awolf_005fbarchart_005f1.setWidth("600");
/*      */     
/* 3881 */     _jspx_th_awolf_005fbarchart_005f1.setHeight("280");
/*      */     
/* 3883 */     _jspx_th_awolf_005fbarchart_005f1.setLegend("false");
/*      */     
/* 3885 */     _jspx_th_awolf_005fbarchart_005f1.setXaxisLabel("Pool");
/*      */     
/* 3887 */     _jspx_th_awolf_005fbarchart_005f1.setYaxisLabel("");
/* 3888 */     int _jspx_eval_awolf_005fbarchart_005f1 = _jspx_th_awolf_005fbarchart_005f1.doStartTag();
/* 3889 */     if (_jspx_eval_awolf_005fbarchart_005f1 != 0) {
/* 3890 */       if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 3891 */         out = _jspx_page_context.pushBody();
/* 3892 */         _jspx_th_awolf_005fbarchart_005f1.setBodyContent((BodyContent)out);
/* 3893 */         _jspx_th_awolf_005fbarchart_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3896 */         out.write("\n\t\t    ");
/* 3897 */         if (_jspx_meth_awolf_005fmap_005f1(_jspx_th_awolf_005fbarchart_005f1, _jspx_page_context))
/* 3898 */           return true;
/* 3899 */         out.write(32);
/* 3900 */         out.write(32);
/* 3901 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f1.doAfterBody();
/* 3902 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3905 */       if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 3906 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3909 */     if (_jspx_th_awolf_005fbarchart_005f1.doEndTag() == 5) {
/* 3910 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f1);
/* 3911 */       return true;
/*      */     }
/* 3913 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f1);
/* 3914 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f1(JspTag _jspx_th_awolf_005fbarchart_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3919 */     PageContext pageContext = _jspx_page_context;
/* 3920 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3922 */     Property _jspx_th_awolf_005fmap_005f1 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3923 */     _jspx_th_awolf_005fmap_005f1.setPageContext(_jspx_page_context);
/* 3924 */     _jspx_th_awolf_005fmap_005f1.setParent((Tag)_jspx_th_awolf_005fbarchart_005f1);
/*      */     
/* 3926 */     _jspx_th_awolf_005fmap_005f1.setId("color");
/* 3927 */     int _jspx_eval_awolf_005fmap_005f1 = _jspx_th_awolf_005fmap_005f1.doStartTag();
/* 3928 */     if (_jspx_eval_awolf_005fmap_005f1 != 0) {
/* 3929 */       if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 3930 */         out = _jspx_page_context.pushBody();
/* 3931 */         _jspx_th_awolf_005fmap_005f1.setBodyContent((BodyContent)out);
/* 3932 */         _jspx_th_awolf_005fmap_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3935 */         out.write("\n\t\t\t\t");
/* 3936 */         if (_jspx_meth_awolf_005fparam_005f4(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 3937 */           return true;
/* 3938 */         out.write("\n\t\t\t\t");
/* 3939 */         if (_jspx_meth_awolf_005fparam_005f5(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 3940 */           return true;
/* 3941 */         out.write("\n\t\t\t\t");
/* 3942 */         if (_jspx_meth_awolf_005fparam_005f6(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 3943 */           return true;
/* 3944 */         out.write("\n\t\t\t\t");
/* 3945 */         if (_jspx_meth_awolf_005fparam_005f7(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 3946 */           return true;
/* 3947 */         out.write("\n\t\t    ");
/* 3948 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f1.doAfterBody();
/* 3949 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3952 */       if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 3953 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3956 */     if (_jspx_th_awolf_005fmap_005f1.doEndTag() == 5) {
/* 3957 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 3958 */       return true;
/*      */     }
/* 3960 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 3961 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f4(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3966 */     PageContext pageContext = _jspx_page_context;
/* 3967 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3969 */     AMParam _jspx_th_awolf_005fparam_005f4 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3970 */     _jspx_th_awolf_005fparam_005f4.setPageContext(_jspx_page_context);
/* 3971 */     _jspx_th_awolf_005fparam_005f4.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 3973 */     _jspx_th_awolf_005fparam_005f4.setName("0");
/*      */     
/* 3975 */     _jspx_th_awolf_005fparam_005f4.setValue("#C6454A");
/* 3976 */     int _jspx_eval_awolf_005fparam_005f4 = _jspx_th_awolf_005fparam_005f4.doStartTag();
/* 3977 */     if (_jspx_th_awolf_005fparam_005f4.doEndTag() == 5) {
/* 3978 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 3979 */       return true;
/*      */     }
/* 3981 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 3982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f5(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3987 */     PageContext pageContext = _jspx_page_context;
/* 3988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3990 */     AMParam _jspx_th_awolf_005fparam_005f5 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3991 */     _jspx_th_awolf_005fparam_005f5.setPageContext(_jspx_page_context);
/* 3992 */     _jspx_th_awolf_005fparam_005f5.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 3994 */     _jspx_th_awolf_005fparam_005f5.setName("1");
/*      */     
/* 3996 */     _jspx_th_awolf_005fparam_005f5.setValue("#00FF00");
/* 3997 */     int _jspx_eval_awolf_005fparam_005f5 = _jspx_th_awolf_005fparam_005f5.doStartTag();
/* 3998 */     if (_jspx_th_awolf_005fparam_005f5.doEndTag() == 5) {
/* 3999 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5);
/* 4000 */       return true;
/*      */     }
/* 4002 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5);
/* 4003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f6(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4008 */     PageContext pageContext = _jspx_page_context;
/* 4009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4011 */     AMParam _jspx_th_awolf_005fparam_005f6 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4012 */     _jspx_th_awolf_005fparam_005f6.setPageContext(_jspx_page_context);
/* 4013 */     _jspx_th_awolf_005fparam_005f6.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 4015 */     _jspx_th_awolf_005fparam_005f6.setName("2");
/*      */     
/* 4017 */     _jspx_th_awolf_005fparam_005f6.setValue("#FE7301");
/* 4018 */     int _jspx_eval_awolf_005fparam_005f6 = _jspx_th_awolf_005fparam_005f6.doStartTag();
/* 4019 */     if (_jspx_th_awolf_005fparam_005f6.doEndTag() == 5) {
/* 4020 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f6);
/* 4021 */       return true;
/*      */     }
/* 4023 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f6);
/* 4024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f7(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4029 */     PageContext pageContext = _jspx_page_context;
/* 4030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4032 */     AMParam _jspx_th_awolf_005fparam_005f7 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4033 */     _jspx_th_awolf_005fparam_005f7.setPageContext(_jspx_page_context);
/* 4034 */     _jspx_th_awolf_005fparam_005f7.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 4036 */     _jspx_th_awolf_005fparam_005f7.setName("3");
/*      */     
/* 4038 */     _jspx_th_awolf_005fparam_005f7.setValue("#589037");
/* 4039 */     int _jspx_eval_awolf_005fparam_005f7 = _jspx_th_awolf_005fparam_005f7.doStartTag();
/* 4040 */     if (_jspx_th_awolf_005fparam_005f7.doEndTag() == 5) {
/* 4041 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f7);
/* 4042 */       return true;
/*      */     }
/* 4044 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f7);
/* 4045 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4050 */     PageContext pageContext = _jspx_page_context;
/* 4051 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4053 */     BarChart _jspx_th_awolf_005fbarchart_005f2 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 4054 */     _jspx_th_awolf_005fbarchart_005f2.setPageContext(_jspx_page_context);
/* 4055 */     _jspx_th_awolf_005fbarchart_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4057 */     _jspx_th_awolf_005fbarchart_005f2.setDataSetProducer("wlsGraph");
/*      */     
/* 4059 */     _jspx_th_awolf_005fbarchart_005f2.setWidth("600");
/*      */     
/* 4061 */     _jspx_th_awolf_005fbarchart_005f2.setHeight("280");
/*      */     
/* 4063 */     _jspx_th_awolf_005fbarchart_005f2.setLegend("false");
/*      */     
/* 4065 */     _jspx_th_awolf_005fbarchart_005f2.setXaxisLabel("Pool");
/*      */     
/* 4067 */     _jspx_th_awolf_005fbarchart_005f2.setYaxisLabel("");
/* 4068 */     int _jspx_eval_awolf_005fbarchart_005f2 = _jspx_th_awolf_005fbarchart_005f2.doStartTag();
/* 4069 */     if (_jspx_eval_awolf_005fbarchart_005f2 != 0) {
/* 4070 */       if (_jspx_eval_awolf_005fbarchart_005f2 != 1) {
/* 4071 */         out = _jspx_page_context.pushBody();
/* 4072 */         _jspx_th_awolf_005fbarchart_005f2.setBodyContent((BodyContent)out);
/* 4073 */         _jspx_th_awolf_005fbarchart_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4076 */         out.write("\n\t\t    ");
/* 4077 */         if (_jspx_meth_awolf_005fmap_005f2(_jspx_th_awolf_005fbarchart_005f2, _jspx_page_context))
/* 4078 */           return true;
/* 4079 */         out.write("\t    ");
/* 4080 */         out.write("\n            ");
/* 4081 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f2.doAfterBody();
/* 4082 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4085 */       if (_jspx_eval_awolf_005fbarchart_005f2 != 1) {
/* 4086 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4089 */     if (_jspx_th_awolf_005fbarchart_005f2.doEndTag() == 5) {
/* 4090 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f2);
/* 4091 */       return true;
/*      */     }
/* 4093 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f2);
/* 4094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f2(JspTag _jspx_th_awolf_005fbarchart_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4099 */     PageContext pageContext = _jspx_page_context;
/* 4100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4102 */     Property _jspx_th_awolf_005fmap_005f2 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4103 */     _jspx_th_awolf_005fmap_005f2.setPageContext(_jspx_page_context);
/* 4104 */     _jspx_th_awolf_005fmap_005f2.setParent((Tag)_jspx_th_awolf_005fbarchart_005f2);
/*      */     
/* 4106 */     _jspx_th_awolf_005fmap_005f2.setId("color");
/* 4107 */     int _jspx_eval_awolf_005fmap_005f2 = _jspx_th_awolf_005fmap_005f2.doStartTag();
/* 4108 */     if (_jspx_eval_awolf_005fmap_005f2 != 0) {
/* 4109 */       if (_jspx_eval_awolf_005fmap_005f2 != 1) {
/* 4110 */         out = _jspx_page_context.pushBody();
/* 4111 */         _jspx_th_awolf_005fmap_005f2.setBodyContent((BodyContent)out);
/* 4112 */         _jspx_th_awolf_005fmap_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4115 */         out.write("\n\t\t\t\t");
/* 4116 */         if (_jspx_meth_awolf_005fparam_005f8(_jspx_th_awolf_005fmap_005f2, _jspx_page_context))
/* 4117 */           return true;
/* 4118 */         out.write("\n\t\t\t\t");
/* 4119 */         if (_jspx_meth_awolf_005fparam_005f9(_jspx_th_awolf_005fmap_005f2, _jspx_page_context))
/* 4120 */           return true;
/* 4121 */         out.write("\n\t\t\t\t");
/* 4122 */         if (_jspx_meth_awolf_005fparam_005f10(_jspx_th_awolf_005fmap_005f2, _jspx_page_context))
/* 4123 */           return true;
/* 4124 */         out.write("\n\t\t\t\t");
/* 4125 */         if (_jspx_meth_awolf_005fparam_005f11(_jspx_th_awolf_005fmap_005f2, _jspx_page_context))
/* 4126 */           return true;
/* 4127 */         out.write("\n\t\t    ");
/* 4128 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f2.doAfterBody();
/* 4129 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4132 */       if (_jspx_eval_awolf_005fmap_005f2 != 1) {
/* 4133 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4136 */     if (_jspx_th_awolf_005fmap_005f2.doEndTag() == 5) {
/* 4137 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f2);
/* 4138 */       return true;
/*      */     }
/* 4140 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f2);
/* 4141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f8(JspTag _jspx_th_awolf_005fmap_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4146 */     PageContext pageContext = _jspx_page_context;
/* 4147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4149 */     AMParam _jspx_th_awolf_005fparam_005f8 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4150 */     _jspx_th_awolf_005fparam_005f8.setPageContext(_jspx_page_context);
/* 4151 */     _jspx_th_awolf_005fparam_005f8.setParent((Tag)_jspx_th_awolf_005fmap_005f2);
/*      */     
/* 4153 */     _jspx_th_awolf_005fparam_005f8.setName("0");
/*      */     
/* 4155 */     _jspx_th_awolf_005fparam_005f8.setValue("#C6454A");
/* 4156 */     int _jspx_eval_awolf_005fparam_005f8 = _jspx_th_awolf_005fparam_005f8.doStartTag();
/* 4157 */     if (_jspx_th_awolf_005fparam_005f8.doEndTag() == 5) {
/* 4158 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f8);
/* 4159 */       return true;
/*      */     }
/* 4161 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f8);
/* 4162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f9(JspTag _jspx_th_awolf_005fmap_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4167 */     PageContext pageContext = _jspx_page_context;
/* 4168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4170 */     AMParam _jspx_th_awolf_005fparam_005f9 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4171 */     _jspx_th_awolf_005fparam_005f9.setPageContext(_jspx_page_context);
/* 4172 */     _jspx_th_awolf_005fparam_005f9.setParent((Tag)_jspx_th_awolf_005fmap_005f2);
/*      */     
/* 4174 */     _jspx_th_awolf_005fparam_005f9.setName("1");
/*      */     
/* 4176 */     _jspx_th_awolf_005fparam_005f9.setValue("#00FF00");
/* 4177 */     int _jspx_eval_awolf_005fparam_005f9 = _jspx_th_awolf_005fparam_005f9.doStartTag();
/* 4178 */     if (_jspx_th_awolf_005fparam_005f9.doEndTag() == 5) {
/* 4179 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f9);
/* 4180 */       return true;
/*      */     }
/* 4182 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f9);
/* 4183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f10(JspTag _jspx_th_awolf_005fmap_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4188 */     PageContext pageContext = _jspx_page_context;
/* 4189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4191 */     AMParam _jspx_th_awolf_005fparam_005f10 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4192 */     _jspx_th_awolf_005fparam_005f10.setPageContext(_jspx_page_context);
/* 4193 */     _jspx_th_awolf_005fparam_005f10.setParent((Tag)_jspx_th_awolf_005fmap_005f2);
/*      */     
/* 4195 */     _jspx_th_awolf_005fparam_005f10.setName("2");
/*      */     
/* 4197 */     _jspx_th_awolf_005fparam_005f10.setValue("#FE7301");
/* 4198 */     int _jspx_eval_awolf_005fparam_005f10 = _jspx_th_awolf_005fparam_005f10.doStartTag();
/* 4199 */     if (_jspx_th_awolf_005fparam_005f10.doEndTag() == 5) {
/* 4200 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f10);
/* 4201 */       return true;
/*      */     }
/* 4203 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f10);
/* 4204 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f11(JspTag _jspx_th_awolf_005fmap_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4209 */     PageContext pageContext = _jspx_page_context;
/* 4210 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4212 */     AMParam _jspx_th_awolf_005fparam_005f11 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4213 */     _jspx_th_awolf_005fparam_005f11.setPageContext(_jspx_page_context);
/* 4214 */     _jspx_th_awolf_005fparam_005f11.setParent((Tag)_jspx_th_awolf_005fmap_005f2);
/*      */     
/* 4216 */     _jspx_th_awolf_005fparam_005f11.setName("3");
/*      */     
/* 4218 */     _jspx_th_awolf_005fparam_005f11.setValue("#589037");
/* 4219 */     int _jspx_eval_awolf_005fparam_005f11 = _jspx_th_awolf_005fparam_005f11.doStartTag();
/* 4220 */     if (_jspx_th_awolf_005fparam_005f11.doEndTag() == 5) {
/* 4221 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f11);
/* 4222 */       return true;
/*      */     }
/* 4224 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f11);
/* 4225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4230 */     PageContext pageContext = _jspx_page_context;
/* 4231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4233 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular.get(AMWolf.class);
/* 4234 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 4235 */     _jspx_th_awolf_005fpiechart_005f0.setParent(null);
/*      */     
/* 4237 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("as400graph");
/*      */     
/* 4239 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("540");
/*      */     
/* 4241 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("430");
/*      */     
/* 4243 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 4245 */     _jspx_th_awolf_005fpiechart_005f0.setLegendanchor("SOUTH");
/*      */     
/* 4247 */     _jspx_th_awolf_005fpiechart_005f0.setUnits(" ");
/*      */     
/* 4249 */     _jspx_th_awolf_005fpiechart_005f0.setCircular(true);
/*      */     
/* 4251 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(false);
/* 4252 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 4253 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 4254 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 4255 */         out = _jspx_page_context.pushBody();
/* 4256 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 4257 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4260 */         out.write("\n\n\n\t");
/* 4261 */         if (_jspx_meth_awolf_005fmap_005f3(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 4262 */           return true;
/* 4263 */         out.write("\n\n\n          ");
/* 4264 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 4265 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4268 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 4269 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4272 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 4273 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 4274 */       return true;
/*      */     }
/* 4276 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 4277 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f3(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4282 */     PageContext pageContext = _jspx_page_context;
/* 4283 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4285 */     Property _jspx_th_awolf_005fmap_005f3 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4286 */     _jspx_th_awolf_005fmap_005f3.setPageContext(_jspx_page_context);
/* 4287 */     _jspx_th_awolf_005fmap_005f3.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 4289 */     _jspx_th_awolf_005fmap_005f3.setId("color");
/* 4290 */     int _jspx_eval_awolf_005fmap_005f3 = _jspx_th_awolf_005fmap_005f3.doStartTag();
/* 4291 */     if (_jspx_eval_awolf_005fmap_005f3 != 0) {
/* 4292 */       if (_jspx_eval_awolf_005fmap_005f3 != 1) {
/* 4293 */         out = _jspx_page_context.pushBody();
/* 4294 */         _jspx_th_awolf_005fmap_005f3.setBodyContent((BodyContent)out);
/* 4295 */         _jspx_th_awolf_005fmap_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4298 */         out.write(10);
/* 4299 */         out.write(9);
/* 4300 */         if (_jspx_meth_awolf_005fparam_005f12(_jspx_th_awolf_005fmap_005f3, _jspx_page_context))
/* 4301 */           return true;
/* 4302 */         out.write(10);
/* 4303 */         out.write(9);
/* 4304 */         if (_jspx_meth_awolf_005fparam_005f13(_jspx_th_awolf_005fmap_005f3, _jspx_page_context))
/* 4305 */           return true;
/* 4306 */         out.write(10);
/* 4307 */         out.write(9);
/* 4308 */         if (_jspx_meth_awolf_005fparam_005f14(_jspx_th_awolf_005fmap_005f3, _jspx_page_context))
/* 4309 */           return true;
/* 4310 */         out.write(10);
/* 4311 */         out.write(9);
/* 4312 */         if (_jspx_meth_awolf_005fparam_005f15(_jspx_th_awolf_005fmap_005f3, _jspx_page_context))
/* 4313 */           return true;
/* 4314 */         out.write(10);
/* 4315 */         out.write(9);
/* 4316 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f3.doAfterBody();
/* 4317 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4320 */       if (_jspx_eval_awolf_005fmap_005f3 != 1) {
/* 4321 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4324 */     if (_jspx_th_awolf_005fmap_005f3.doEndTag() == 5) {
/* 4325 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f3);
/* 4326 */       return true;
/*      */     }
/* 4328 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f3);
/* 4329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f12(JspTag _jspx_th_awolf_005fmap_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4334 */     PageContext pageContext = _jspx_page_context;
/* 4335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4337 */     AMParam _jspx_th_awolf_005fparam_005f12 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4338 */     _jspx_th_awolf_005fparam_005f12.setPageContext(_jspx_page_context);
/* 4339 */     _jspx_th_awolf_005fparam_005f12.setParent((Tag)_jspx_th_awolf_005fmap_005f3);
/*      */     
/* 4341 */     _jspx_th_awolf_005fparam_005f12.setName("3");
/*      */     
/* 4343 */     _jspx_th_awolf_005fparam_005f12.setValue("#2F4F4F");
/* 4344 */     int _jspx_eval_awolf_005fparam_005f12 = _jspx_th_awolf_005fparam_005f12.doStartTag();
/* 4345 */     if (_jspx_th_awolf_005fparam_005f12.doEndTag() == 5) {
/* 4346 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f12);
/* 4347 */       return true;
/*      */     }
/* 4349 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f12);
/* 4350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f13(JspTag _jspx_th_awolf_005fmap_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4355 */     PageContext pageContext = _jspx_page_context;
/* 4356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4358 */     AMParam _jspx_th_awolf_005fparam_005f13 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4359 */     _jspx_th_awolf_005fparam_005f13.setPageContext(_jspx_page_context);
/* 4360 */     _jspx_th_awolf_005fparam_005f13.setParent((Tag)_jspx_th_awolf_005fmap_005f3);
/*      */     
/* 4362 */     _jspx_th_awolf_005fparam_005f13.setName("2");
/*      */     
/* 4364 */     _jspx_th_awolf_005fparam_005f13.setValue("#8B008B");
/* 4365 */     int _jspx_eval_awolf_005fparam_005f13 = _jspx_th_awolf_005fparam_005f13.doStartTag();
/* 4366 */     if (_jspx_th_awolf_005fparam_005f13.doEndTag() == 5) {
/* 4367 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f13);
/* 4368 */       return true;
/*      */     }
/* 4370 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f13);
/* 4371 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f14(JspTag _jspx_th_awolf_005fmap_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4376 */     PageContext pageContext = _jspx_page_context;
/* 4377 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4379 */     AMParam _jspx_th_awolf_005fparam_005f14 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4380 */     _jspx_th_awolf_005fparam_005f14.setPageContext(_jspx_page_context);
/* 4381 */     _jspx_th_awolf_005fparam_005f14.setParent((Tag)_jspx_th_awolf_005fmap_005f3);
/*      */     
/* 4383 */     _jspx_th_awolf_005fparam_005f14.setName("1");
/*      */     
/* 4385 */     _jspx_th_awolf_005fparam_005f14.setValue("#FF9900");
/* 4386 */     int _jspx_eval_awolf_005fparam_005f14 = _jspx_th_awolf_005fparam_005f14.doStartTag();
/* 4387 */     if (_jspx_th_awolf_005fparam_005f14.doEndTag() == 5) {
/* 4388 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f14);
/* 4389 */       return true;
/*      */     }
/* 4391 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f14);
/* 4392 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f15(JspTag _jspx_th_awolf_005fmap_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4397 */     PageContext pageContext = _jspx_page_context;
/* 4398 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4400 */     AMParam _jspx_th_awolf_005fparam_005f15 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4401 */     _jspx_th_awolf_005fparam_005f15.setPageContext(_jspx_page_context);
/* 4402 */     _jspx_th_awolf_005fparam_005f15.setParent((Tag)_jspx_th_awolf_005fmap_005f3);
/*      */     
/* 4404 */     _jspx_th_awolf_005fparam_005f15.setName("0");
/*      */     
/* 4406 */     _jspx_th_awolf_005fparam_005f15.setValue("#00CCFF");
/* 4407 */     int _jspx_eval_awolf_005fparam_005f15 = _jspx_th_awolf_005fparam_005f15.doStartTag();
/* 4408 */     if (_jspx_th_awolf_005fparam_005f15.doEndTag() == 5) {
/* 4409 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f15);
/* 4410 */       return true;
/*      */     }
/* 4412 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f15);
/* 4413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4418 */     PageContext pageContext = _jspx_page_context;
/* 4419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4421 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f6 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4422 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 4423 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/*      */     
/* 4425 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.as400.jobcounts");
/* 4426 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 4427 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 4428 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4429 */       return true;
/*      */     }
/* 4431 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4432 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4437 */     PageContext pageContext = _jspx_page_context;
/* 4438 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4440 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f7 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4441 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 4442 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/*      */     
/* 4444 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.as400.systeminfo");
/* 4445 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 4446 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 4447 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4448 */       return true;
/*      */     }
/* 4450 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4451 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4456 */     PageContext pageContext = _jspx_page_context;
/* 4457 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4459 */     BarChart _jspx_th_awolf_005fbarchart_005f3 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 4460 */     _jspx_th_awolf_005fbarchart_005f3.setPageContext(_jspx_page_context);
/* 4461 */     _jspx_th_awolf_005fbarchart_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4463 */     _jspx_th_awolf_005fbarchart_005f3.setDataSetProducer("wlsGraph");
/*      */     
/* 4465 */     _jspx_th_awolf_005fbarchart_005f3.setWidth("600");
/*      */     
/* 4467 */     _jspx_th_awolf_005fbarchart_005f3.setHeight("280");
/*      */     
/* 4469 */     _jspx_th_awolf_005fbarchart_005f3.setLegend("false");
/*      */     
/* 4471 */     _jspx_th_awolf_005fbarchart_005f3.setXaxisLabel("Pool");
/*      */     
/* 4473 */     _jspx_th_awolf_005fbarchart_005f3.setYaxisLabel("");
/* 4474 */     int _jspx_eval_awolf_005fbarchart_005f3 = _jspx_th_awolf_005fbarchart_005f3.doStartTag();
/* 4475 */     if (_jspx_eval_awolf_005fbarchart_005f3 != 0) {
/* 4476 */       if (_jspx_eval_awolf_005fbarchart_005f3 != 1) {
/* 4477 */         out = _jspx_page_context.pushBody();
/* 4478 */         _jspx_th_awolf_005fbarchart_005f3.setBodyContent((BodyContent)out);
/* 4479 */         _jspx_th_awolf_005fbarchart_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4482 */         out.write("\n\t\t    ");
/* 4483 */         if (_jspx_meth_awolf_005fmap_005f4(_jspx_th_awolf_005fbarchart_005f3, _jspx_page_context))
/* 4484 */           return true;
/* 4485 */         out.write("\n            ");
/* 4486 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f3.doAfterBody();
/* 4487 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4490 */       if (_jspx_eval_awolf_005fbarchart_005f3 != 1) {
/* 4491 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4494 */     if (_jspx_th_awolf_005fbarchart_005f3.doEndTag() == 5) {
/* 4495 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f3);
/* 4496 */       return true;
/*      */     }
/* 4498 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f3);
/* 4499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f4(JspTag _jspx_th_awolf_005fbarchart_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4504 */     PageContext pageContext = _jspx_page_context;
/* 4505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4507 */     Property _jspx_th_awolf_005fmap_005f4 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4508 */     _jspx_th_awolf_005fmap_005f4.setPageContext(_jspx_page_context);
/* 4509 */     _jspx_th_awolf_005fmap_005f4.setParent((Tag)_jspx_th_awolf_005fbarchart_005f3);
/*      */     
/* 4511 */     _jspx_th_awolf_005fmap_005f4.setId("color");
/* 4512 */     int _jspx_eval_awolf_005fmap_005f4 = _jspx_th_awolf_005fmap_005f4.doStartTag();
/* 4513 */     if (_jspx_eval_awolf_005fmap_005f4 != 0) {
/* 4514 */       if (_jspx_eval_awolf_005fmap_005f4 != 1) {
/* 4515 */         out = _jspx_page_context.pushBody();
/* 4516 */         _jspx_th_awolf_005fmap_005f4.setBodyContent((BodyContent)out);
/* 4517 */         _jspx_th_awolf_005fmap_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4520 */         out.write("\n\t\t\t\t");
/* 4521 */         if (_jspx_meth_awolf_005fparam_005f16(_jspx_th_awolf_005fmap_005f4, _jspx_page_context))
/* 4522 */           return true;
/* 4523 */         out.write("\n\t\t\t\t");
/* 4524 */         if (_jspx_meth_awolf_005fparam_005f17(_jspx_th_awolf_005fmap_005f4, _jspx_page_context))
/* 4525 */           return true;
/* 4526 */         out.write("\n\t\t\t\t");
/* 4527 */         if (_jspx_meth_awolf_005fparam_005f18(_jspx_th_awolf_005fmap_005f4, _jspx_page_context))
/* 4528 */           return true;
/* 4529 */         out.write("\n\t\t\t\t");
/* 4530 */         if (_jspx_meth_awolf_005fparam_005f19(_jspx_th_awolf_005fmap_005f4, _jspx_page_context))
/* 4531 */           return true;
/* 4532 */         out.write("\n\t\t    ");
/* 4533 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f4.doAfterBody();
/* 4534 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4537 */       if (_jspx_eval_awolf_005fmap_005f4 != 1) {
/* 4538 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4541 */     if (_jspx_th_awolf_005fmap_005f4.doEndTag() == 5) {
/* 4542 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f4);
/* 4543 */       return true;
/*      */     }
/* 4545 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f4);
/* 4546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f16(JspTag _jspx_th_awolf_005fmap_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4551 */     PageContext pageContext = _jspx_page_context;
/* 4552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4554 */     AMParam _jspx_th_awolf_005fparam_005f16 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4555 */     _jspx_th_awolf_005fparam_005f16.setPageContext(_jspx_page_context);
/* 4556 */     _jspx_th_awolf_005fparam_005f16.setParent((Tag)_jspx_th_awolf_005fmap_005f4);
/*      */     
/* 4558 */     _jspx_th_awolf_005fparam_005f16.setName("0");
/*      */     
/* 4560 */     _jspx_th_awolf_005fparam_005f16.setValue("#C6454A");
/* 4561 */     int _jspx_eval_awolf_005fparam_005f16 = _jspx_th_awolf_005fparam_005f16.doStartTag();
/* 4562 */     if (_jspx_th_awolf_005fparam_005f16.doEndTag() == 5) {
/* 4563 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f16);
/* 4564 */       return true;
/*      */     }
/* 4566 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f16);
/* 4567 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f17(JspTag _jspx_th_awolf_005fmap_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4572 */     PageContext pageContext = _jspx_page_context;
/* 4573 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4575 */     AMParam _jspx_th_awolf_005fparam_005f17 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4576 */     _jspx_th_awolf_005fparam_005f17.setPageContext(_jspx_page_context);
/* 4577 */     _jspx_th_awolf_005fparam_005f17.setParent((Tag)_jspx_th_awolf_005fmap_005f4);
/*      */     
/* 4579 */     _jspx_th_awolf_005fparam_005f17.setName("1");
/*      */     
/* 4581 */     _jspx_th_awolf_005fparam_005f17.setValue("#00FF00");
/* 4582 */     int _jspx_eval_awolf_005fparam_005f17 = _jspx_th_awolf_005fparam_005f17.doStartTag();
/* 4583 */     if (_jspx_th_awolf_005fparam_005f17.doEndTag() == 5) {
/* 4584 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f17);
/* 4585 */       return true;
/*      */     }
/* 4587 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f17);
/* 4588 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f18(JspTag _jspx_th_awolf_005fmap_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4593 */     PageContext pageContext = _jspx_page_context;
/* 4594 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4596 */     AMParam _jspx_th_awolf_005fparam_005f18 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4597 */     _jspx_th_awolf_005fparam_005f18.setPageContext(_jspx_page_context);
/* 4598 */     _jspx_th_awolf_005fparam_005f18.setParent((Tag)_jspx_th_awolf_005fmap_005f4);
/*      */     
/* 4600 */     _jspx_th_awolf_005fparam_005f18.setName("2");
/*      */     
/* 4602 */     _jspx_th_awolf_005fparam_005f18.setValue("#FE7301");
/* 4603 */     int _jspx_eval_awolf_005fparam_005f18 = _jspx_th_awolf_005fparam_005f18.doStartTag();
/* 4604 */     if (_jspx_th_awolf_005fparam_005f18.doEndTag() == 5) {
/* 4605 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f18);
/* 4606 */       return true;
/*      */     }
/* 4608 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f18);
/* 4609 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f19(JspTag _jspx_th_awolf_005fmap_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4614 */     PageContext pageContext = _jspx_page_context;
/* 4615 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4617 */     AMParam _jspx_th_awolf_005fparam_005f19 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4618 */     _jspx_th_awolf_005fparam_005f19.setPageContext(_jspx_page_context);
/* 4619 */     _jspx_th_awolf_005fparam_005f19.setParent((Tag)_jspx_th_awolf_005fmap_005f4);
/*      */     
/* 4621 */     _jspx_th_awolf_005fparam_005f19.setName("3");
/*      */     
/* 4623 */     _jspx_th_awolf_005fparam_005f19.setValue("#589037");
/* 4624 */     int _jspx_eval_awolf_005fparam_005f19 = _jspx_th_awolf_005fparam_005f19.doStartTag();
/* 4625 */     if (_jspx_th_awolf_005fparam_005f19.doEndTag() == 5) {
/* 4626 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f19);
/* 4627 */       return true;
/*      */     }
/* 4629 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f19);
/* 4630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4635 */     PageContext pageContext = _jspx_page_context;
/* 4636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4638 */     BarChart _jspx_th_awolf_005fbarchart_005f4 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 4639 */     _jspx_th_awolf_005fbarchart_005f4.setPageContext(_jspx_page_context);
/* 4640 */     _jspx_th_awolf_005fbarchart_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4642 */     _jspx_th_awolf_005fbarchart_005f4.setDataSetProducer("wlsGraph");
/*      */     
/* 4644 */     _jspx_th_awolf_005fbarchart_005f4.setWidth("600");
/*      */     
/* 4646 */     _jspx_th_awolf_005fbarchart_005f4.setHeight("280");
/*      */     
/* 4648 */     _jspx_th_awolf_005fbarchart_005f4.setLegend("false");
/*      */     
/* 4650 */     _jspx_th_awolf_005fbarchart_005f4.setXaxisLabel("Pool");
/*      */     
/* 4652 */     _jspx_th_awolf_005fbarchart_005f4.setYaxisLabel("");
/* 4653 */     int _jspx_eval_awolf_005fbarchart_005f4 = _jspx_th_awolf_005fbarchart_005f4.doStartTag();
/* 4654 */     if (_jspx_eval_awolf_005fbarchart_005f4 != 0) {
/* 4655 */       if (_jspx_eval_awolf_005fbarchart_005f4 != 1) {
/* 4656 */         out = _jspx_page_context.pushBody();
/* 4657 */         _jspx_th_awolf_005fbarchart_005f4.setBodyContent((BodyContent)out);
/* 4658 */         _jspx_th_awolf_005fbarchart_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4661 */         out.write("\n\t\t    ");
/* 4662 */         if (_jspx_meth_awolf_005fmap_005f5(_jspx_th_awolf_005fbarchart_005f4, _jspx_page_context))
/* 4663 */           return true;
/* 4664 */         out.write("\n            ");
/* 4665 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f4.doAfterBody();
/* 4666 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4669 */       if (_jspx_eval_awolf_005fbarchart_005f4 != 1) {
/* 4670 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4673 */     if (_jspx_th_awolf_005fbarchart_005f4.doEndTag() == 5) {
/* 4674 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f4);
/* 4675 */       return true;
/*      */     }
/* 4677 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f4);
/* 4678 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f5(JspTag _jspx_th_awolf_005fbarchart_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4683 */     PageContext pageContext = _jspx_page_context;
/* 4684 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4686 */     Property _jspx_th_awolf_005fmap_005f5 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4687 */     _jspx_th_awolf_005fmap_005f5.setPageContext(_jspx_page_context);
/* 4688 */     _jspx_th_awolf_005fmap_005f5.setParent((Tag)_jspx_th_awolf_005fbarchart_005f4);
/*      */     
/* 4690 */     _jspx_th_awolf_005fmap_005f5.setId("color");
/* 4691 */     int _jspx_eval_awolf_005fmap_005f5 = _jspx_th_awolf_005fmap_005f5.doStartTag();
/* 4692 */     if (_jspx_eval_awolf_005fmap_005f5 != 0) {
/* 4693 */       if (_jspx_eval_awolf_005fmap_005f5 != 1) {
/* 4694 */         out = _jspx_page_context.pushBody();
/* 4695 */         _jspx_th_awolf_005fmap_005f5.setBodyContent((BodyContent)out);
/* 4696 */         _jspx_th_awolf_005fmap_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4699 */         out.write("\n\t\t\t\t");
/* 4700 */         if (_jspx_meth_awolf_005fparam_005f20(_jspx_th_awolf_005fmap_005f5, _jspx_page_context))
/* 4701 */           return true;
/* 4702 */         out.write("\n\t\t\t\t");
/* 4703 */         if (_jspx_meth_awolf_005fparam_005f21(_jspx_th_awolf_005fmap_005f5, _jspx_page_context))
/* 4704 */           return true;
/* 4705 */         out.write("\n\t\t\t\t");
/* 4706 */         if (_jspx_meth_awolf_005fparam_005f22(_jspx_th_awolf_005fmap_005f5, _jspx_page_context))
/* 4707 */           return true;
/* 4708 */         out.write("\n\t\t\t\t");
/* 4709 */         if (_jspx_meth_awolf_005fparam_005f23(_jspx_th_awolf_005fmap_005f5, _jspx_page_context))
/* 4710 */           return true;
/* 4711 */         out.write("\n\t\t    ");
/* 4712 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f5.doAfterBody();
/* 4713 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4716 */       if (_jspx_eval_awolf_005fmap_005f5 != 1) {
/* 4717 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4720 */     if (_jspx_th_awolf_005fmap_005f5.doEndTag() == 5) {
/* 4721 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f5);
/* 4722 */       return true;
/*      */     }
/* 4724 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f5);
/* 4725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f20(JspTag _jspx_th_awolf_005fmap_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4730 */     PageContext pageContext = _jspx_page_context;
/* 4731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4733 */     AMParam _jspx_th_awolf_005fparam_005f20 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4734 */     _jspx_th_awolf_005fparam_005f20.setPageContext(_jspx_page_context);
/* 4735 */     _jspx_th_awolf_005fparam_005f20.setParent((Tag)_jspx_th_awolf_005fmap_005f5);
/*      */     
/* 4737 */     _jspx_th_awolf_005fparam_005f20.setName("0");
/*      */     
/* 4739 */     _jspx_th_awolf_005fparam_005f20.setValue("#C6454A");
/* 4740 */     int _jspx_eval_awolf_005fparam_005f20 = _jspx_th_awolf_005fparam_005f20.doStartTag();
/* 4741 */     if (_jspx_th_awolf_005fparam_005f20.doEndTag() == 5) {
/* 4742 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f20);
/* 4743 */       return true;
/*      */     }
/* 4745 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f20);
/* 4746 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f21(JspTag _jspx_th_awolf_005fmap_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4751 */     PageContext pageContext = _jspx_page_context;
/* 4752 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4754 */     AMParam _jspx_th_awolf_005fparam_005f21 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4755 */     _jspx_th_awolf_005fparam_005f21.setPageContext(_jspx_page_context);
/* 4756 */     _jspx_th_awolf_005fparam_005f21.setParent((Tag)_jspx_th_awolf_005fmap_005f5);
/*      */     
/* 4758 */     _jspx_th_awolf_005fparam_005f21.setName("1");
/*      */     
/* 4760 */     _jspx_th_awolf_005fparam_005f21.setValue("#00FF00");
/* 4761 */     int _jspx_eval_awolf_005fparam_005f21 = _jspx_th_awolf_005fparam_005f21.doStartTag();
/* 4762 */     if (_jspx_th_awolf_005fparam_005f21.doEndTag() == 5) {
/* 4763 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f21);
/* 4764 */       return true;
/*      */     }
/* 4766 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f21);
/* 4767 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f22(JspTag _jspx_th_awolf_005fmap_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4772 */     PageContext pageContext = _jspx_page_context;
/* 4773 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4775 */     AMParam _jspx_th_awolf_005fparam_005f22 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4776 */     _jspx_th_awolf_005fparam_005f22.setPageContext(_jspx_page_context);
/* 4777 */     _jspx_th_awolf_005fparam_005f22.setParent((Tag)_jspx_th_awolf_005fmap_005f5);
/*      */     
/* 4779 */     _jspx_th_awolf_005fparam_005f22.setName("2");
/*      */     
/* 4781 */     _jspx_th_awolf_005fparam_005f22.setValue("#FE7301");
/* 4782 */     int _jspx_eval_awolf_005fparam_005f22 = _jspx_th_awolf_005fparam_005f22.doStartTag();
/* 4783 */     if (_jspx_th_awolf_005fparam_005f22.doEndTag() == 5) {
/* 4784 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f22);
/* 4785 */       return true;
/*      */     }
/* 4787 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f22);
/* 4788 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f23(JspTag _jspx_th_awolf_005fmap_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4793 */     PageContext pageContext = _jspx_page_context;
/* 4794 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4796 */     AMParam _jspx_th_awolf_005fparam_005f23 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4797 */     _jspx_th_awolf_005fparam_005f23.setPageContext(_jspx_page_context);
/* 4798 */     _jspx_th_awolf_005fparam_005f23.setParent((Tag)_jspx_th_awolf_005fmap_005f5);
/*      */     
/* 4800 */     _jspx_th_awolf_005fparam_005f23.setName("3");
/*      */     
/* 4802 */     _jspx_th_awolf_005fparam_005f23.setValue("#589037");
/* 4803 */     int _jspx_eval_awolf_005fparam_005f23 = _jspx_th_awolf_005fparam_005f23.doStartTag();
/* 4804 */     if (_jspx_th_awolf_005fparam_005f23.doEndTag() == 5) {
/* 4805 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f23);
/* 4806 */       return true;
/*      */     }
/* 4808 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f23);
/* 4809 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f5(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4814 */     PageContext pageContext = _jspx_page_context;
/* 4815 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4817 */     BarChart _jspx_th_awolf_005fbarchart_005f5 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 4818 */     _jspx_th_awolf_005fbarchart_005f5.setPageContext(_jspx_page_context);
/* 4819 */     _jspx_th_awolf_005fbarchart_005f5.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4821 */     _jspx_th_awolf_005fbarchart_005f5.setDataSetProducer("wlsGraph");
/*      */     
/* 4823 */     _jspx_th_awolf_005fbarchart_005f5.setWidth("600");
/*      */     
/* 4825 */     _jspx_th_awolf_005fbarchart_005f5.setHeight("280");
/*      */     
/* 4827 */     _jspx_th_awolf_005fbarchart_005f5.setLegend("false");
/*      */     
/* 4829 */     _jspx_th_awolf_005fbarchart_005f5.setXaxisLabel("Pool");
/*      */     
/* 4831 */     _jspx_th_awolf_005fbarchart_005f5.setYaxisLabel("");
/* 4832 */     int _jspx_eval_awolf_005fbarchart_005f5 = _jspx_th_awolf_005fbarchart_005f5.doStartTag();
/* 4833 */     if (_jspx_eval_awolf_005fbarchart_005f5 != 0) {
/* 4834 */       if (_jspx_eval_awolf_005fbarchart_005f5 != 1) {
/* 4835 */         out = _jspx_page_context.pushBody();
/* 4836 */         _jspx_th_awolf_005fbarchart_005f5.setBodyContent((BodyContent)out);
/* 4837 */         _jspx_th_awolf_005fbarchart_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4840 */         out.write("\n\t\t    ");
/* 4841 */         if (_jspx_meth_awolf_005fmap_005f6(_jspx_th_awolf_005fbarchart_005f5, _jspx_page_context))
/* 4842 */           return true;
/* 4843 */         out.write("\n            ");
/* 4844 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f5.doAfterBody();
/* 4845 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4848 */       if (_jspx_eval_awolf_005fbarchart_005f5 != 1) {
/* 4849 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4852 */     if (_jspx_th_awolf_005fbarchart_005f5.doEndTag() == 5) {
/* 4853 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f5);
/* 4854 */       return true;
/*      */     }
/* 4856 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f5);
/* 4857 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f6(JspTag _jspx_th_awolf_005fbarchart_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4862 */     PageContext pageContext = _jspx_page_context;
/* 4863 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4865 */     Property _jspx_th_awolf_005fmap_005f6 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4866 */     _jspx_th_awolf_005fmap_005f6.setPageContext(_jspx_page_context);
/* 4867 */     _jspx_th_awolf_005fmap_005f6.setParent((Tag)_jspx_th_awolf_005fbarchart_005f5);
/*      */     
/* 4869 */     _jspx_th_awolf_005fmap_005f6.setId("color");
/* 4870 */     int _jspx_eval_awolf_005fmap_005f6 = _jspx_th_awolf_005fmap_005f6.doStartTag();
/* 4871 */     if (_jspx_eval_awolf_005fmap_005f6 != 0) {
/* 4872 */       if (_jspx_eval_awolf_005fmap_005f6 != 1) {
/* 4873 */         out = _jspx_page_context.pushBody();
/* 4874 */         _jspx_th_awolf_005fmap_005f6.setBodyContent((BodyContent)out);
/* 4875 */         _jspx_th_awolf_005fmap_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4878 */         out.write("\n\t\t\t\t");
/* 4879 */         if (_jspx_meth_awolf_005fparam_005f24(_jspx_th_awolf_005fmap_005f6, _jspx_page_context))
/* 4880 */           return true;
/* 4881 */         out.write("\n\t\t\t\t");
/* 4882 */         if (_jspx_meth_awolf_005fparam_005f25(_jspx_th_awolf_005fmap_005f6, _jspx_page_context))
/* 4883 */           return true;
/* 4884 */         out.write("\n\t\t\t\t");
/* 4885 */         if (_jspx_meth_awolf_005fparam_005f26(_jspx_th_awolf_005fmap_005f6, _jspx_page_context))
/* 4886 */           return true;
/* 4887 */         out.write("\n\t\t\t\t");
/* 4888 */         if (_jspx_meth_awolf_005fparam_005f27(_jspx_th_awolf_005fmap_005f6, _jspx_page_context))
/* 4889 */           return true;
/* 4890 */         out.write("\n\t\t    ");
/* 4891 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f6.doAfterBody();
/* 4892 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4895 */       if (_jspx_eval_awolf_005fmap_005f6 != 1) {
/* 4896 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4899 */     if (_jspx_th_awolf_005fmap_005f6.doEndTag() == 5) {
/* 4900 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f6);
/* 4901 */       return true;
/*      */     }
/* 4903 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f6);
/* 4904 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f24(JspTag _jspx_th_awolf_005fmap_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4909 */     PageContext pageContext = _jspx_page_context;
/* 4910 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4912 */     AMParam _jspx_th_awolf_005fparam_005f24 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4913 */     _jspx_th_awolf_005fparam_005f24.setPageContext(_jspx_page_context);
/* 4914 */     _jspx_th_awolf_005fparam_005f24.setParent((Tag)_jspx_th_awolf_005fmap_005f6);
/*      */     
/* 4916 */     _jspx_th_awolf_005fparam_005f24.setName("0");
/*      */     
/* 4918 */     _jspx_th_awolf_005fparam_005f24.setValue("#C6454A");
/* 4919 */     int _jspx_eval_awolf_005fparam_005f24 = _jspx_th_awolf_005fparam_005f24.doStartTag();
/* 4920 */     if (_jspx_th_awolf_005fparam_005f24.doEndTag() == 5) {
/* 4921 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f24);
/* 4922 */       return true;
/*      */     }
/* 4924 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f24);
/* 4925 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f25(JspTag _jspx_th_awolf_005fmap_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4930 */     PageContext pageContext = _jspx_page_context;
/* 4931 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4933 */     AMParam _jspx_th_awolf_005fparam_005f25 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4934 */     _jspx_th_awolf_005fparam_005f25.setPageContext(_jspx_page_context);
/* 4935 */     _jspx_th_awolf_005fparam_005f25.setParent((Tag)_jspx_th_awolf_005fmap_005f6);
/*      */     
/* 4937 */     _jspx_th_awolf_005fparam_005f25.setName("1");
/*      */     
/* 4939 */     _jspx_th_awolf_005fparam_005f25.setValue("#00FF00");
/* 4940 */     int _jspx_eval_awolf_005fparam_005f25 = _jspx_th_awolf_005fparam_005f25.doStartTag();
/* 4941 */     if (_jspx_th_awolf_005fparam_005f25.doEndTag() == 5) {
/* 4942 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f25);
/* 4943 */       return true;
/*      */     }
/* 4945 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f25);
/* 4946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f26(JspTag _jspx_th_awolf_005fmap_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4951 */     PageContext pageContext = _jspx_page_context;
/* 4952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4954 */     AMParam _jspx_th_awolf_005fparam_005f26 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4955 */     _jspx_th_awolf_005fparam_005f26.setPageContext(_jspx_page_context);
/* 4956 */     _jspx_th_awolf_005fparam_005f26.setParent((Tag)_jspx_th_awolf_005fmap_005f6);
/*      */     
/* 4958 */     _jspx_th_awolf_005fparam_005f26.setName("2");
/*      */     
/* 4960 */     _jspx_th_awolf_005fparam_005f26.setValue("#FE7301");
/* 4961 */     int _jspx_eval_awolf_005fparam_005f26 = _jspx_th_awolf_005fparam_005f26.doStartTag();
/* 4962 */     if (_jspx_th_awolf_005fparam_005f26.doEndTag() == 5) {
/* 4963 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f26);
/* 4964 */       return true;
/*      */     }
/* 4966 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f26);
/* 4967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f27(JspTag _jspx_th_awolf_005fmap_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4972 */     PageContext pageContext = _jspx_page_context;
/* 4973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4975 */     AMParam _jspx_th_awolf_005fparam_005f27 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4976 */     _jspx_th_awolf_005fparam_005f27.setPageContext(_jspx_page_context);
/* 4977 */     _jspx_th_awolf_005fparam_005f27.setParent((Tag)_jspx_th_awolf_005fmap_005f6);
/*      */     
/* 4979 */     _jspx_th_awolf_005fparam_005f27.setName("3");
/*      */     
/* 4981 */     _jspx_th_awolf_005fparam_005f27.setValue("#589037");
/* 4982 */     int _jspx_eval_awolf_005fparam_005f27 = _jspx_th_awolf_005fparam_005f27.doStartTag();
/* 4983 */     if (_jspx_th_awolf_005fparam_005f27.doEndTag() == 5) {
/* 4984 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f27);
/* 4985 */       return true;
/*      */     }
/* 4987 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f27);
/* 4988 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4993 */     PageContext pageContext = _jspx_page_context;
/* 4994 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4996 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 4997 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4998 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 5000 */     _jspx_th_c_005fset_005f0.setVar("datatype");
/*      */     
/* 5002 */     _jspx_th_c_005fset_005f0.setValue("1");
/*      */     
/* 5004 */     _jspx_th_c_005fset_005f0.setScope("session");
/* 5005 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5006 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5007 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 5008 */       return true;
/*      */     }
/* 5010 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 5011 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\as400\overview_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */