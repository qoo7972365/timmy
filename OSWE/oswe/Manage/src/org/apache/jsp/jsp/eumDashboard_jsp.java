/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
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
/*      */ import java.util.Set;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ 
/*      */ public final class eumDashboard_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   47 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   50 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   51 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   52 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   59 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   64 */     ArrayList list = null;
/*   65 */     StringBuffer sbf = new StringBuffer();
/*   66 */     ManagedApplication mo = new ManagedApplication();
/*   67 */     if (distinct)
/*      */     {
/*   69 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   73 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   76 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   78 */       ArrayList row = (ArrayList)list.get(i);
/*   79 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   80 */       if (distinct) {
/*   81 */         sbf.append(row.get(0));
/*      */       } else
/*   83 */         sbf.append(row.get(1));
/*   84 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   87 */     return sbf.toString(); }
/*      */   
/*   89 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   92 */     if (severity == null)
/*      */     {
/*   94 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   96 */     if (severity.equals("5"))
/*      */     {
/*   98 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  100 */     if (severity.equals("1"))
/*      */     {
/*  102 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  107 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  114 */     if (severity == null)
/*      */     {
/*  116 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  118 */     if (severity.equals("1"))
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  122 */     if (severity.equals("4"))
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  126 */     if (severity.equals("5"))
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  133 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  139 */     if (severity == null)
/*      */     {
/*  141 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  143 */     if (severity.equals("5"))
/*      */     {
/*  145 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  147 */     if (severity.equals("1"))
/*      */     {
/*  149 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  153 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  159 */     if (severity == null)
/*      */     {
/*  161 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  163 */     if (severity.equals("1"))
/*      */     {
/*  165 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  167 */     if (severity.equals("4"))
/*      */     {
/*  169 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  171 */     if (severity.equals("5"))
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  177 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  183 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  189 */     if (severity == 5)
/*      */     {
/*  191 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  193 */     if (severity == 1)
/*      */     {
/*  195 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  200 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  206 */     if (severity == null)
/*      */     {
/*  208 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  210 */     if (severity.equals("5"))
/*      */     {
/*  212 */       if (isAvailability) {
/*  213 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  216 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  219 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  221 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  223 */     if (severity.equals("1"))
/*      */     {
/*  225 */       if (isAvailability) {
/*  226 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  229 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  236 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  243 */     if (severity == null)
/*      */     {
/*  245 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  247 */     if (severity.equals("5"))
/*      */     {
/*  249 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  251 */     if (severity.equals("4"))
/*      */     {
/*  253 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  255 */     if (severity.equals("1"))
/*      */     {
/*  257 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  262 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  268 */     if (severity == null)
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  272 */     if (severity.equals("5"))
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  276 */     if (severity.equals("4"))
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  280 */     if (severity.equals("1"))
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  287 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  294 */     if (severity == null)
/*      */     {
/*  296 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  298 */     if (severity.equals("5"))
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  302 */     if (severity.equals("4"))
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  306 */     if (severity.equals("1"))
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  313 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  321 */     StringBuffer out = new StringBuffer();
/*  322 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  323 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  324 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  325 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  326 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  327 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  328 */     out.append("</tr>");
/*  329 */     out.append("</form></table>");
/*  330 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  337 */     if (val == null)
/*      */     {
/*  339 */       return "-";
/*      */     }
/*      */     
/*  342 */     String ret = FormatUtil.formatNumber(val);
/*  343 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  344 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  347 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  351 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  359 */     StringBuffer out = new StringBuffer();
/*  360 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  361 */     out.append("<tr>");
/*  362 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  364 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  366 */     out.append("</tr>");
/*  367 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  371 */       if (j % 2 == 0)
/*      */       {
/*  373 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  377 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  380 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  382 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  385 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  389 */       out.append("</tr>");
/*      */     }
/*  391 */     out.append("</table>");
/*  392 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  393 */     out.append("<tr>");
/*  394 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  395 */     out.append("</tr>");
/*  396 */     out.append("</table>");
/*  397 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  403 */     StringBuffer out = new StringBuffer();
/*  404 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  405 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  406 */     out.append("<tr>");
/*  407 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  408 */     out.append("<tr>");
/*  409 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  410 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  411 */     out.append("</tr>");
/*  412 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  415 */       out.append("<tr>");
/*  416 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  417 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  418 */       out.append("</tr>");
/*      */     }
/*      */     
/*  421 */     out.append("</table>");
/*  422 */     out.append("</table>");
/*  423 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  428 */     if (severity.equals("0"))
/*      */     {
/*  430 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  434 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  441 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  454 */     StringBuffer out = new StringBuffer();
/*  455 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  456 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  458 */       out.append("<tr>");
/*  459 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  460 */       out.append("</tr>");
/*      */       
/*      */ 
/*  463 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  465 */         String borderclass = "";
/*      */         
/*      */ 
/*  468 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  470 */         out.append("<tr>");
/*      */         
/*  472 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  473 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  474 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  480 */     out.append("</table><br>");
/*  481 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  482 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  484 */       List sLinks = secondLevelOfLinks[0];
/*  485 */       List sText = secondLevelOfLinks[1];
/*  486 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  489 */         out.append("<tr>");
/*  490 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  491 */         out.append("</tr>");
/*  492 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  494 */           String borderclass = "";
/*      */           
/*      */ 
/*  497 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  499 */           out.append("<tr>");
/*      */           
/*  501 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  502 */           if (sLinks.get(i).toString().length() == 0) {
/*  503 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  506 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  508 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  512 */     out.append("</table>");
/*  513 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  520 */     StringBuffer out = new StringBuffer();
/*  521 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  522 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  524 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  526 */         out.append("<tr>");
/*  527 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  528 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  532 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  534 */           String borderclass = "";
/*      */           
/*      */ 
/*  537 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  539 */           out.append("<tr>");
/*      */           
/*  541 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  542 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  543 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  546 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  549 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  554 */     out.append("</table><br>");
/*  555 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  556 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  558 */       List sLinks = secondLevelOfLinks[0];
/*  559 */       List sText = secondLevelOfLinks[1];
/*  560 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  563 */         out.append("<tr>");
/*  564 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  565 */         out.append("</tr>");
/*  566 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  568 */           String borderclass = "";
/*      */           
/*      */ 
/*  571 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  573 */           out.append("<tr>");
/*      */           
/*  575 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  576 */           if (sLinks.get(i).toString().length() == 0) {
/*  577 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  580 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  582 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  586 */     out.append("</table>");
/*  587 */     return out.toString();
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
/*  600 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  603 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  606 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  615 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  618 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  621 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  629 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  634 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  639 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  644 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  649 */     if (val != null)
/*      */     {
/*  651 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  655 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  660 */     if (val == null) {
/*  661 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  665 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  670 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  676 */     if (val != null)
/*      */     {
/*  678 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  682 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  688 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  693 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  697 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  702 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  707 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  712 */     String hostaddress = "";
/*  713 */     String ip = request.getHeader("x-forwarded-for");
/*  714 */     if (ip == null)
/*  715 */       ip = request.getRemoteAddr();
/*  716 */     InetAddress add = null;
/*  717 */     if (ip.equals("127.0.0.1")) {
/*  718 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  722 */       add = InetAddress.getByName(ip);
/*      */     }
/*  724 */     hostaddress = add.getHostName();
/*  725 */     if (hostaddress.indexOf('.') != -1) {
/*  726 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  727 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  731 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  736 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  742 */     if (severity == null)
/*      */     {
/*  744 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  746 */     if (severity.equals("5"))
/*      */     {
/*  748 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  750 */     if (severity.equals("1"))
/*      */     {
/*  752 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  757 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  762 */     ResultSet set = null;
/*  763 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  764 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  766 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  767 */       if (set.next()) { String str1;
/*  768 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  769 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  772 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  777 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  780 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  782 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  786 */     StringBuffer rca = new StringBuffer();
/*  787 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  788 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  791 */     int rcalength = key.length();
/*  792 */     String split = "6. ";
/*  793 */     int splitPresent = key.indexOf(split);
/*  794 */     String div1 = "";String div2 = "";
/*  795 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  797 */       if (rcalength > 180) {
/*  798 */         rca.append("<span class=\"rca-critical-text\">");
/*  799 */         getRCATrimmedText(key, rca);
/*  800 */         rca.append("</span>");
/*      */       } else {
/*  802 */         rca.append("<span class=\"rca-critical-text\">");
/*  803 */         rca.append(key);
/*  804 */         rca.append("</span>");
/*      */       }
/*  806 */       return rca.toString();
/*      */     }
/*  808 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  809 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  810 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  811 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  812 */     getRCATrimmedText(div1, rca);
/*  813 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  816 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  817 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  818 */     getRCATrimmedText(div2, rca);
/*  819 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  821 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  826 */     String[] st = msg.split("<br>");
/*  827 */     for (int i = 0; i < st.length; i++) {
/*  828 */       String s = st[i];
/*  829 */       if (s.length() > 180) {
/*  830 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  832 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  836 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  837 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  839 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  843 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  844 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  845 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  848 */       if (key == null) {
/*  849 */         return ret;
/*      */       }
/*      */       
/*  852 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  853 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  856 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  857 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  858 */       set = AMConnectionPool.executeQueryStmt(query);
/*  859 */       if (set.next())
/*      */       {
/*  861 */         String helpLink = set.getString("LINK");
/*  862 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  865 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  871 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  890 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  881 */         if (set != null) {
/*  882 */           AMConnectionPool.closeStatement(set);
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
/*  896 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  897 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  899 */       String entityStr = (String)keys.nextElement();
/*  900 */       String mmessage = temp.getProperty(entityStr);
/*  901 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  902 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  904 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  910 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  911 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  913 */       String entityStr = (String)keys.nextElement();
/*  914 */       String mmessage = temp.getProperty(entityStr);
/*  915 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  916 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  918 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  923 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  933 */     String des = new String();
/*  934 */     while (str.indexOf(find) != -1) {
/*  935 */       des = des + str.substring(0, str.indexOf(find));
/*  936 */       des = des + replace;
/*  937 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  939 */     des = des + str;
/*  940 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  947 */       if (alert == null)
/*      */       {
/*  949 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  951 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  953 */         return "&nbsp;";
/*      */       }
/*      */       
/*  956 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  958 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  961 */       int rcalength = test.length();
/*  962 */       if (rcalength < 300)
/*      */       {
/*  964 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  968 */       StringBuffer out = new StringBuffer();
/*  969 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  970 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  971 */       out.append("</div>");
/*  972 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  973 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  974 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  979 */       ex.printStackTrace();
/*      */     }
/*  981 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  987 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  992 */     ArrayList attribIDs = new ArrayList();
/*  993 */     ArrayList resIDs = new ArrayList();
/*  994 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  996 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  998 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1000 */       String resourceid = "";
/* 1001 */       String resourceType = "";
/* 1002 */       if (type == 2) {
/* 1003 */         resourceid = (String)row.get(0);
/* 1004 */         resourceType = (String)row.get(3);
/*      */       }
/* 1006 */       else if (type == 3) {
/* 1007 */         resourceid = (String)row.get(0);
/* 1008 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1011 */         resourceid = (String)row.get(6);
/* 1012 */         resourceType = (String)row.get(7);
/*      */       }
/* 1014 */       resIDs.add(resourceid);
/* 1015 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1016 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1018 */       String healthentity = null;
/* 1019 */       String availentity = null;
/* 1020 */       if (healthid != null) {
/* 1021 */         healthentity = resourceid + "_" + healthid;
/* 1022 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1025 */       if (availid != null) {
/* 1026 */         availentity = resourceid + "_" + availid;
/* 1027 */         entitylist.add(availentity);
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
/* 1041 */     Properties alert = getStatus(entitylist);
/* 1042 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1047 */     int size = monitorList.size();
/*      */     
/* 1049 */     String[] severity = new String[size];
/*      */     
/* 1051 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1053 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1054 */       String resourceName1 = (String)row1.get(7);
/* 1055 */       String resourceid1 = (String)row1.get(6);
/* 1056 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1057 */       if (severity[j] == null)
/*      */       {
/* 1059 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1063 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1065 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1067 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1070 */         if (sev > 0) {
/* 1071 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1072 */           monitorList.set(k, monitorList.get(j));
/* 1073 */           monitorList.set(j, t);
/* 1074 */           String temp = severity[k];
/* 1075 */           severity[k] = severity[j];
/* 1076 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1082 */     int z = 0;
/* 1083 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1086 */       int i = 0;
/* 1087 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1090 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1094 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1098 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1100 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1103 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1107 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1110 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1111 */       String resourceName1 = (String)row1.get(7);
/* 1112 */       String resourceid1 = (String)row1.get(6);
/* 1113 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1114 */       if (hseverity[j] == null)
/*      */       {
/* 1116 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1121 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1123 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1126 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1129 */         if (hsev > 0) {
/* 1130 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1131 */           monitorList.set(k, monitorList.get(j));
/* 1132 */           monitorList.set(j, t);
/* 1133 */           String temp1 = hseverity[k];
/* 1134 */           hseverity[k] = hseverity[j];
/* 1135 */           hseverity[j] = temp1;
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
/* 1147 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1148 */     boolean forInventory = false;
/* 1149 */     String trdisplay = "none";
/* 1150 */     String plusstyle = "inline";
/* 1151 */     String minusstyle = "none";
/* 1152 */     String haidTopLevel = "";
/* 1153 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1155 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1157 */         haidTopLevel = request.getParameter("haid");
/* 1158 */         forInventory = true;
/* 1159 */         trdisplay = "table-row;";
/* 1160 */         plusstyle = "none";
/* 1161 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1168 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1171 */     ArrayList listtoreturn = new ArrayList();
/* 1172 */     StringBuffer toreturn = new StringBuffer();
/* 1173 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1174 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1175 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1177 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1179 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1180 */       String childresid = (String)singlerow.get(0);
/* 1181 */       String childresname = (String)singlerow.get(1);
/* 1182 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1183 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1184 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1185 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1186 */       String unmanagestatus = (String)singlerow.get(5);
/* 1187 */       String actionstatus = (String)singlerow.get(6);
/* 1188 */       String linkclass = "monitorgp-links";
/* 1189 */       String titleforres = childresname;
/* 1190 */       String titilechildresname = childresname;
/* 1191 */       String childimg = "/images/trcont.png";
/* 1192 */       String flag = "enable";
/* 1193 */       String dcstarted = (String)singlerow.get(8);
/* 1194 */       String configMonitor = "";
/* 1195 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1196 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1198 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1200 */       if (singlerow.get(7) != null)
/*      */       {
/* 1202 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1204 */       String haiGroupType = "0";
/* 1205 */       if ("HAI".equals(childtype))
/*      */       {
/* 1207 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1209 */       childimg = "/images/trend.png";
/* 1210 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1211 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1212 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1214 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1216 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1218 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1219 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1222 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1224 */         linkclass = "disabledtext";
/* 1225 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1227 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1228 */       String availmouseover = "";
/* 1229 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1231 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1233 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1234 */       String healthmouseover = "";
/* 1235 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1237 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1240 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1241 */       int spacing = 0;
/* 1242 */       if (level >= 1)
/*      */       {
/* 1244 */         spacing = 40 * level;
/*      */       }
/* 1246 */       if (childtype.equals("HAI"))
/*      */       {
/* 1248 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1249 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1250 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1252 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1253 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1254 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1255 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1256 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1257 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1258 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1259 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1260 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1261 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1262 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1264 */         if (!forInventory)
/*      */         {
/* 1266 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1269 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1271 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1273 */           actions = editlink + actions;
/*      */         }
/* 1275 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1277 */           actions = actions + associatelink;
/*      */         }
/* 1279 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1280 */         String arrowimg = "";
/* 1281 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1283 */           actions = "";
/* 1284 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1285 */           checkbox = "";
/* 1286 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1288 */         if (isIt360)
/*      */         {
/* 1290 */           actionimg = "";
/* 1291 */           actions = "";
/* 1292 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1293 */           checkbox = "";
/*      */         }
/*      */         
/* 1296 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1298 */           actions = "";
/*      */         }
/* 1300 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1302 */           checkbox = "";
/*      */         }
/*      */         
/* 1305 */         String resourcelink = "";
/*      */         
/* 1307 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1309 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1313 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1316 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1322 */         if (!isIt360)
/*      */         {
/* 1324 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1328 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1331 */         toreturn.append("</tr>");
/* 1332 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1334 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1335 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1339 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1340 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1343 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1347 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1349 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1350 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1351 */             toreturn.append(assocMessage);
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1354 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1355 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1361 */         String resourcelink = null;
/* 1362 */         boolean hideEditLink = false;
/* 1363 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1365 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1366 */           hideEditLink = true;
/* 1367 */           if (isIt360)
/*      */           {
/* 1369 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1373 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1375 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1377 */           hideEditLink = true;
/* 1378 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1379 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1384 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1387 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1388 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1389 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1390 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1391 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1392 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1393 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1394 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1395 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1396 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1397 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1398 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1399 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1401 */         if (hideEditLink)
/*      */         {
/* 1403 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1405 */         if (!forInventory)
/*      */         {
/* 1407 */           removefromgroup = "";
/*      */         }
/* 1409 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1410 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1411 */           actions = actions + configcustomfields;
/*      */         }
/* 1413 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1415 */           actions = editlink + actions;
/*      */         }
/* 1417 */         String managedLink = "";
/* 1418 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1420 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1421 */           actions = "";
/* 1422 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1423 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1426 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1428 */           checkbox = "";
/*      */         }
/*      */         
/* 1431 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1433 */           actions = "";
/*      */         }
/* 1435 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1436 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1437 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1438 */         if (isIt360)
/*      */         {
/* 1440 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1444 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1448 */         if (!isIt360)
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1456 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1459 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1466 */       StringBuilder toreturn = new StringBuilder();
/* 1467 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1468 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1469 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1470 */       String title = "";
/* 1471 */       message = EnterpriseUtil.decodeString(message);
/* 1472 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1473 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1474 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1476 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1478 */       else if ("5".equals(severity))
/*      */       {
/* 1480 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1484 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1486 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1487 */       toreturn.append(v);
/*      */       
/* 1489 */       toreturn.append(link);
/* 1490 */       if (severity == null)
/*      */       {
/* 1492 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1494 */       else if (severity.equals("5"))
/*      */       {
/* 1496 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1498 */       else if (severity.equals("4"))
/*      */       {
/* 1500 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1502 */       else if (severity.equals("1"))
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       toreturn.append("</a>");
/* 1512 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1516 */       ex.printStackTrace();
/*      */     }
/* 1518 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1525 */       StringBuilder toreturn = new StringBuilder();
/* 1526 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1527 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1528 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1529 */       if (message == null)
/*      */       {
/* 1531 */         message = "";
/*      */       }
/*      */       
/* 1534 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1535 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1537 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1538 */       toreturn.append(v);
/*      */       
/* 1540 */       toreturn.append(link);
/*      */       
/* 1542 */       if (severity == null)
/*      */       {
/* 1544 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1546 */       else if (severity.equals("5"))
/*      */       {
/* 1548 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1550 */       else if (severity.equals("1"))
/*      */       {
/* 1552 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1559 */       toreturn.append("</a>");
/* 1560 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1566 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1569 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1570 */     if (invokeActions != null) {
/* 1571 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1572 */       while (iterator.hasNext()) {
/* 1573 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1574 */         if (actionmap.containsKey(actionid)) {
/* 1575 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1580 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1584 */     String actionLink = "";
/* 1585 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1586 */     String query = "";
/* 1587 */     ResultSet rs = null;
/* 1588 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1589 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1590 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1591 */       actionLink = "method=" + methodName;
/*      */     }
/* 1593 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1594 */       actionLink = methodName;
/*      */     }
/* 1596 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1597 */     Iterator itr = methodarglist.iterator();
/* 1598 */     boolean isfirstparam = true;
/* 1599 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1600 */     while (itr.hasNext()) {
/* 1601 */       HashMap argmap = (HashMap)itr.next();
/* 1602 */       String argtype = (String)argmap.get("TYPE");
/* 1603 */       String argname = (String)argmap.get("IDENTITY");
/* 1604 */       String paramname = (String)argmap.get("PARAMETER");
/* 1605 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1606 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1607 */         isfirstparam = false;
/* 1608 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1610 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1614 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1618 */         actionLink = actionLink + "&";
/*      */       }
/* 1620 */       String paramValue = null;
/* 1621 */       String tempargname = argname;
/* 1622 */       if (commonValues.getProperty(tempargname) != null) {
/* 1623 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1626 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1627 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1628 */           if (dbType.equals("mysql")) {
/* 1629 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1632 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1634 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1636 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1637 */             if (rs.next()) {
/* 1638 */               paramValue = rs.getString("VALUE");
/* 1639 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1643 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1647 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1650 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1655 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1656 */           paramValue = rowId;
/*      */         }
/* 1658 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1659 */           paramValue = managedObjectName;
/*      */         }
/* 1661 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1662 */           paramValue = resID;
/*      */         }
/* 1664 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1665 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1668 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1670 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1671 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1672 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1674 */     return actionLink;
/*      */   }
/*      */   
/* 1677 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1678 */     String dependentAttribute = null;
/* 1679 */     String align = "left";
/*      */     
/* 1681 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1682 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1683 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1684 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1685 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1686 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1687 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1688 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1689 */       align = "center";
/*      */     }
/*      */     
/* 1692 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1693 */     String actualdata = "";
/*      */     
/* 1695 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1696 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1697 */         actualdata = availValue;
/*      */       }
/* 1699 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1700 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1704 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1705 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1708 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1714 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1715 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1716 */       toreturn.append("<table>");
/* 1717 */       toreturn.append("<tr>");
/* 1718 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1719 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1720 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1721 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1722 */         String toolTip = "";
/* 1723 */         String hideClass = "";
/* 1724 */         String textStyle = "";
/* 1725 */         boolean isreferenced = true;
/* 1726 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1727 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1728 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1729 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1731 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1732 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1733 */           while (valueList.hasMoreTokens()) {
/* 1734 */             String dependentVal = valueList.nextToken();
/* 1735 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1736 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1737 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1739 */               toolTip = "";
/* 1740 */               hideClass = "";
/* 1741 */               isreferenced = false;
/* 1742 */               textStyle = "disabledtext";
/* 1743 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1747 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1748 */           toolTip = "";
/* 1749 */           hideClass = "";
/* 1750 */           isreferenced = false;
/* 1751 */           textStyle = "disabledtext";
/* 1752 */           if (dependentImageMap != null) {
/* 1753 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1754 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1757 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1761 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1762 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1763 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1764 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1765 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1766 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1768 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1769 */           if (isreferenced) {
/* 1770 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1774 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1775 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1776 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1777 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1778 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1779 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1781 */           toreturn.append("</span>");
/* 1782 */           toreturn.append("</a>");
/* 1783 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1786 */       toreturn.append("</tr>");
/* 1787 */       toreturn.append("</table>");
/* 1788 */       toreturn.append("</td>");
/*      */     } else {
/* 1790 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1793 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1797 */     String colTime = null;
/* 1798 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1799 */     if ((rows != null) && (rows.size() > 0)) {
/* 1800 */       Iterator<String> itr = rows.iterator();
/* 1801 */       String maxColQuery = "";
/* 1802 */       for (;;) { if (itr.hasNext()) {
/* 1803 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1804 */           ResultSet maxCol = null;
/*      */           try {
/* 1806 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1807 */             while (maxCol.next()) {
/* 1808 */               if (colTime == null) {
/* 1809 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1812 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1821 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1823 */               if (maxCol != null)
/* 1824 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1826 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1821 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1823 */               if (maxCol != null)
/* 1824 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1826 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1831 */     return colTime;
/*      */   }
/*      */   
/* 1834 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1835 */     tablename = null;
/* 1836 */     ResultSet rsTable = null;
/* 1837 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1839 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1840 */       while (rsTable.next()) {
/* 1841 */         tablename = rsTable.getString("DATATABLE");
/* 1842 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1843 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1856 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1847 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1850 */         if (rsTable != null)
/* 1851 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1853 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1859 */     String argsList = "";
/* 1860 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1862 */       if (showArgsMap.get(row) != null) {
/* 1863 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1864 */         if (showArgslist != null) {
/* 1865 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1866 */             if (argsList.trim().equals("")) {
/* 1867 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1870 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1877 */       e.printStackTrace();
/* 1878 */       return "";
/*      */     }
/* 1880 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1885 */     String argsList = "";
/* 1886 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1889 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1891 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1892 */         if (hideArgsList != null)
/*      */         {
/* 1894 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1896 */             if (argsList.trim().equals(""))
/*      */             {
/* 1898 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1902 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1910 */       ex.printStackTrace();
/*      */     }
/* 1912 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1916 */     StringBuilder toreturn = new StringBuilder();
/* 1917 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1924 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1925 */       Iterator itr = tActionList.iterator();
/* 1926 */       while (itr.hasNext()) {
/* 1927 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1928 */         String confirmmsg = "";
/* 1929 */         String link = "";
/* 1930 */         String isJSP = "NO";
/* 1931 */         HashMap tactionMap = (HashMap)itr.next();
/* 1932 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1933 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1934 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1935 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1936 */           (actionmap.containsKey(actionId))) {
/* 1937 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1938 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1939 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1940 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1941 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1943 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1949 */           if (isTableAction) {
/* 1950 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1953 */             tableName = "Link";
/* 1954 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1955 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1956 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1957 */             toreturn.append("</a></td>");
/*      */           }
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1962 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1968 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1974 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1976 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1977 */       Properties prop = (Properties)node.getUserObject();
/* 1978 */       String mgID = prop.getProperty("label");
/* 1979 */       String mgName = prop.getProperty("value");
/* 1980 */       String isParent = prop.getProperty("isParent");
/* 1981 */       int mgIDint = Integer.parseInt(mgID);
/* 1982 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1984 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1986 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1987 */       if (node.getChildCount() > 0)
/*      */       {
/* 1989 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1991 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1993 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1995 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1999 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2004 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2006 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2008 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2010 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2014 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2017 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2018 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2020 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2024 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2026 */       if (node.getChildCount() > 0)
/*      */       {
/* 2028 */         builder.append("<UL>");
/* 2029 */         printMGTree(node, builder);
/* 2030 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2035 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2036 */     StringBuffer toReturn = new StringBuffer();
/* 2037 */     String table = "-";
/*      */     try {
/* 2039 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2040 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2041 */       float total = 0.0F;
/* 2042 */       while (it.hasNext()) {
/* 2043 */         String attName = (String)it.next();
/* 2044 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2045 */         boolean roundOffData = false;
/* 2046 */         if ((data != null) && (!data.equals(""))) {
/* 2047 */           if (data.indexOf(",") != -1) {
/* 2048 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2051 */             float value = Float.parseFloat(data);
/* 2052 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2055 */             total += value;
/* 2056 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2059 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2064 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2065 */       while (attVsWidthList.hasNext()) {
/* 2066 */         String attName = (String)attVsWidthList.next();
/* 2067 */         String data = (String)attVsWidthProps.get(attName);
/* 2068 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2069 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2070 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2071 */         String className = (String)graphDetails.get("ClassName");
/* 2072 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2073 */         if (percentage < 1.0F)
/*      */         {
/* 2075 */           data = percentage + "";
/*      */         }
/* 2077 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2079 */       if (toReturn.length() > 0) {
/* 2080 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2084 */       e.printStackTrace();
/*      */     }
/* 2086 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2092 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2093 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2094 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2095 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2096 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2097 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2098 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2099 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2100 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2103 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2104 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2105 */       splitvalues[0] = multiplecondition.toString();
/* 2106 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2109 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2114 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2115 */     if (thresholdType != 3) {
/* 2116 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2117 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2118 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2119 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2120 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2121 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2123 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2124 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2125 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2126 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2127 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2128 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2130 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2131 */     if (updateSelected != null) {
/* 2132 */       updateSelected[0] = "selected";
/*      */     }
/* 2134 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2139 */       StringBuffer toreturn = new StringBuffer("");
/* 2140 */       if (commaSeparatedMsgId != null) {
/* 2141 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2142 */         int count = 0;
/* 2143 */         while (msgids.hasMoreTokens()) {
/* 2144 */           String id = msgids.nextToken();
/* 2145 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2146 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2147 */           count++;
/* 2148 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2149 */             if (toreturn.length() == 0) {
/* 2150 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2152 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2153 */             if (!image.trim().equals("")) {
/* 2154 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2156 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2157 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2160 */         if (toreturn.length() > 0) {
/* 2161 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2165 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2168 */       e.printStackTrace(); }
/* 2169 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2175 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2181 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2182 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2200 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2204 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2205 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2215 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2219 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2220 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2221 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2222 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.release();
/* 2223 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2224 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005fname.release();
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2235 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2238 */     JspWriter out = null;
/* 2239 */     Object page = this;
/* 2240 */     JspWriter _jspx_out = null;
/* 2241 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2245 */       response.setContentType("text/html;charset=UTF-8");
/* 2246 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2248 */       _jspx_page_context = pageContext;
/* 2249 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2250 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2251 */       session = pageContext.getSession();
/* 2252 */       out = pageContext.getOut();
/* 2253 */       _jspx_out = out;
/*      */       
/* 2255 */       out.write("<!DOCTYPE html>\n");
/* 2256 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2257 */       out.write("\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n\n\n\n\n\n");
/* 2258 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2260 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2261 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2262 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2264 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2266 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2268 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2270 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2271 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2272 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2273 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2276 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2277 */         String available = null;
/* 2278 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2279 */         out.write(10);
/*      */         
/* 2281 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2282 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2283 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2285 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2287 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2289 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2291 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2292 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2293 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2294 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2297 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2298 */           String unavailable = null;
/* 2299 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2300 */           out.write(10);
/*      */           
/* 2302 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2303 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2304 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2306 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2308 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2310 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2312 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2313 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2314 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2315 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2318 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2319 */             String unmanaged = null;
/* 2320 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2321 */             out.write(10);
/*      */             
/* 2323 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2324 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2325 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2327 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2329 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2331 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2333 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2334 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2335 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2336 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2339 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2340 */               String scheduled = null;
/* 2341 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2342 */               out.write(10);
/*      */               
/* 2344 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2345 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2346 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2348 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2350 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2352 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2354 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2355 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2356 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2357 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2360 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2361 */                 String critical = null;
/* 2362 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2363 */                 out.write(10);
/*      */                 
/* 2365 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2366 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2367 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2369 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2371 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2373 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2375 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2376 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2377 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2378 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2381 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2382 */                   String clear = null;
/* 2383 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2384 */                   out.write(10);
/*      */                   
/* 2386 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2387 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2388 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2390 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2392 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2394 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2396 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2397 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2398 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2399 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2402 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2403 */                     String warning = null;
/* 2404 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2405 */                     out.write(10);
/* 2406 */                     out.write(10);
/*      */                     
/* 2408 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2409 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2411 */                     out.write(10);
/* 2412 */                     out.write(10);
/* 2413 */                     out.write(10);
/* 2414 */                     out.write("\n\n\n\n");
/* 2415 */                     Hashtable healthkeys = null;
/* 2416 */                     synchronized (application) {
/* 2417 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2418 */                       if (healthkeys == null) {
/* 2419 */                         healthkeys = new Hashtable();
/* 2420 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2423 */                     out.write(10);
/* 2424 */                     Hashtable motypedisplaynames = null;
/* 2425 */                     synchronized (application) {
/* 2426 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2427 */                       if (motypedisplaynames == null) {
/* 2428 */                         motypedisplaynames = new Hashtable();
/* 2429 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2432 */                     out.write("\n<script language=\"JavaScript1.2\" src=\"/template/sortTable.js\"></script>\n\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n\n<script  type=\"text/javascript\">\nfunction ReadCookie(cookie_name)\n{\n   var allcookies = document.cookie;\n   cookiearray  = allcookies.split(';');\n   for(var i=0; i<cookiearray.length; i++){\n      name = cookiearray[i].split('=')[0];\n      value = cookiearray[i].split('=')[1];\n\t  if($.trim(name) === $.trim(cookie_name))\n\t  return value;\n\t  \n   }\n}\n function set_cookie(name,cookievalue) {\n \ndocument.cookie=name+\"=\" + cookievalue;\n }\n</script>\n</head>\n\n");
/*      */                     
/* 2434 */                     request.setAttribute("HelpKey", "EUM");
/*      */                     try {
/* 2436 */                       Object parentVSagentIdVsResId = (LinkedHashMap)request.getAttribute("parentVSagentIdVsResId");
/* 2437 */                       Object agentIdVsDispName = (LinkedHashMap)request.getAttribute("agentIdVsDispName");
/* 2438 */                       LinkedHashMap<String, String> agentIdVsStatus = (LinkedHashMap)request.getAttribute("agentIdVsStatus");
/* 2439 */                       LinkedHashMap<String, String> idVsDispName = (LinkedHashMap)request.getAttribute("idVsDispName");
/* 2440 */                       HashMap<String, String> agentIdVsUrl = (HashMap)request.getAttribute("agentIdVsUrl");
/* 2441 */                       LinkedHashMap<String, Hashtable<Object, Object>> typeWiseAlertCountMap = (LinkedHashMap)request.getAttribute("typeWiseAlertCountMap");
/* 2442 */                       HashMap<String, String> resIdVsType = (HashMap)request.getAttribute("resIdVsType");
/* 2443 */                       ArrayList<String> parentList = (ArrayList)request.getAttribute("parentList");
/* 2444 */                       Properties healthProps = (Properties)request.getAttribute("healthProps");
/* 2445 */                       String eumSeverity = (String)request.getAttribute("eumSeverity");
/* 2446 */                       String resType = (String)request.getAttribute("resType");
/* 2447 */                       String typetoShow = !resType.equals("zxy") ? FormatUtil.getString((String)motypedisplaynames.get(resType)) : FormatUtil.getString("am.webclient.common.totalmonitors");
/* 2448 */                       Hashtable<Object, Object> ht = (Hashtable)typeWiseAlertCountMap.get(resType);
/*      */                       
/* 2450 */                       int totalClear = ((Integer)ht.get("Clear")).intValue();
/* 2451 */                       int totalWarning = ((Integer)ht.get("Warning")).intValue();
/* 2452 */                       int totalCritical = ((Integer)ht.get("Critical")).intValue();
/* 2453 */                       int totalCount = ((Integer)ht.get("Total")).intValue();
/*      */                       
/* 2455 */                       int clearper = ((Integer)ht.get("clearPercentage")).intValue();
/* 2456 */                       int criticalper = ((Integer)ht.get("criticalPercentage")).intValue();
/* 2457 */                       int warningper = ((Integer)ht.get("warningPercentage")).intValue();
/*      */                       
/* 2459 */                       out.write(10);
/*      */                       
/* 2461 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2462 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2463 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2465 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2466 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2467 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2469 */                           out.write(10);
/*      */                           
/* 2471 */                           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2472 */                           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2473 */                           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2475 */                           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                           
/* 2477 */                           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.eumdashboard.heading"));
/* 2478 */                           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2479 */                           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2480 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                           }
/*      */                           
/* 2483 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2484 */                           out.write(10);
/* 2485 */                           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2487 */                           out.write(10);
/* 2488 */                           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2490 */                           out.write(10);
/* 2491 */                           out.write(10);
/*      */                           
/* 2493 */                           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2494 */                           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2495 */                           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2497 */                           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                           
/* 2499 */                           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2500 */                           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2501 */                           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2502 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2503 */                               out = _jspx_page_context.pushBody();
/* 2504 */                               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2505 */                               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2508 */                               out.write("\n<body>\n");
/*      */                               
/* 2510 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 2511 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2512 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 2514 */                               _jspx_th_html_005fform_005f0.setAction("/showAgent.do");
/* 2515 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2516 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2518 */                                   out.write("\n\t<input type=\"hidden\" name=\"method\" value=\"showEumSnapshot\">\n\t<input type=\"hidden\" name=\"resTypeValue\" value=\"");
/* 2519 */                                   out.print(resType);
/* 2520 */                                   out.write("\">\n\t<input type=\"hidden\" name=\"severity\" value=\"");
/* 2521 */                                   out.print(eumSeverity);
/* 2522 */                                   out.write("\">\n<!-- EUM Shadow tabel starts!-->\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\">\n\t<tr>\n\t\t<td class=\"eum-top-head\" width=\"62%\">&nbsp;&nbsp;");
/* 2523 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEUMDashboardPage(resType, typetoShow, eumSeverity));
/* 2524 */                                   out.write("</td>\n\t\t");
/*      */                                   
/* 2526 */                                   PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2527 */                                   _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2528 */                                   _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2530 */                                   _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2531 */                                   int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2532 */                                   if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                     for (;;) {
/* 2534 */                                       out.write("\n\t\t\t<td class=\"bodytext\" style=\"padding-left:15px;\" align=\"right\">");
/* 2535 */                                       out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/* 2536 */                                       out.write(" :\n\t\t\t\t&nbsp;&nbsp;");
/*      */                                       
/* 2538 */                                       SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005fname.get(SelectTag.class);
/* 2539 */                                       _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2540 */                                       _jspx_th_html_005fselect_005f0.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                                       
/* 2542 */                                       _jspx_th_html_005fselect_005f0.setName("AMActionForm");
/*      */                                       
/* 2544 */                                       _jspx_th_html_005fselect_005f0.setProperty("selectedServer");
/*      */                                       
/* 2546 */                                       _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                       
/* 2548 */                                       _jspx_th_html_005fselect_005f0.setOnchange("javascript:showRelaventData(this);");
/* 2549 */                                       int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2550 */                                       if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2551 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2552 */                                           out = _jspx_page_context.pushBody();
/* 2553 */                                           _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2554 */                                           _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 2557 */                                           out.write("\n\t\t\t\t\t");
/*      */                                           
/* 2559 */                                           OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2560 */                                           _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2561 */                                           _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                           
/* 2563 */                                           _jspx_th_html_005foption_005f0.setValue("0");
/* 2564 */                                           int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2565 */                                           if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2566 */                                             if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2567 */                                               out = _jspx_page_context.pushBody();
/* 2568 */                                               _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2569 */                                               _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 2572 */                                               out.print(FormatUtil.getString("am.webclient.eumdashboard.allmanagedserver"));
/* 2573 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2574 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 2577 */                                             if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2578 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 2581 */                                           if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2582 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                           }
/*      */                                           
/* 2585 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2586 */                                           out.write("\n\t\t\t\t\t");
/* 2587 */                                           if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/*      */                                             return;
/* 2589 */                                           out.write("\n\t\t\t\t");
/* 2590 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 2591 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 2594 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2595 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 2598 */                                       if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 2599 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005fname.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                       }
/*      */                                       
/* 2602 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005fname.reuse(_jspx_th_html_005fselect_005f0);
/* 2603 */                                       out.write("\n\t\t\t</td>\n\t\t\t<td width=\"1\">&nbsp;|&nbsp;</td>\n\t\t");
/* 2604 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2605 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2609 */                                   if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2610 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                   }
/*      */                                   
/* 2613 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2614 */                                   out.write("\n\t\t<td class=\"eum-name-head\" width=\"7%\" align=\"right\"><a href=\"/showAgent.do?method=getAgentDetails\" class=\"eum-name-head onHoverCSS\">");
/* 2615 */                                   out.print(FormatUtil.getString("am.webclient.eumdashboard.location"));
/* 2616 */                                   out.write("</a>&nbsp;&nbsp;</td>\n\t</tr>\n</table>\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\">\n<tr>\n\n\n<!--EUM Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\" class=\"lrbtborder\">\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"vcenter-icons-bg\">\n<tr>\n<!-- EUM total Moniors tabel starts!-->\n<td class=\"eum-gray-bg\" align=\"center\" width=\"17%\">\n\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t<tr>\n\t\t<td width=\"20\"></td>\n\t\t<td colspan=\"23 align=\"left\" class=\"eum-total-monitors\">");
/* 2617 */                                   out.print(typetoShow);
/* 2618 */                                   out.write(32);
/* 2619 */                                   out.write(40);
/* 2620 */                                   out.print(totalCount);
/* 2621 */                                   out.write(")</td>\n\t</tr>\n\t<tr>\n\t\t<td colspan=\"3\" height=\"10\"></td>\n\t</tr>\n\t");
/* 2622 */                                   if (totalCritical > 0) {
/* 2623 */                                     out.write("\n\t<tr style=\"height: 20px;\" class=\"setSeverity\"  severityType=\"1\" resType=\"");
/* 2624 */                                     out.print(resType);
/* 2625 */                                     out.write("\">\n\t\t<td width=\"20\"></td>\n\t\t<td class=\"bulkmon-links onHoverCSS\" colspan=\"1\">");
/* 2626 */                                     out.print(FormatUtil.getString("Critical"));
/* 2627 */                                     out.write("</td>\n\t\t<td>:</td>\n\t\t<td class=\"bulkmon-links onHoverCSS\">");
/* 2628 */                                     out.print(totalCritical);
/* 2629 */                                     out.write("</td>\n\t\t<td class=\"onHoverCSS\" align=\"center\" title=\"");
/* 2630 */                                     out.print(FormatUtil.getString("Critical"));
/* 2631 */                                     out.write(32);
/* 2632 */                                     out.write(58);
/* 2633 */                                     out.write(32);
/* 2634 */                                     out.print(totalCritical);
/* 2635 */                                     out.write(" \" >\n\t\t\t<table style=\"border: 1px solid rgb(0, 0, 0);\" width=\"80\" cellpadding=\"0\" cellspacing=\"0\" height=\"10\">\n\t\t   \t<tbody>\n\t\t   \t\t<tr>\n\t\t         \t<td align=\"center\" style=\"background-color: #ff1616; background-position: 18px 50%;\" width=");
/* 2636 */                                     out.print(criticalper);
/* 2637 */                                     out.write(" height=\"10\"> </td>\n\t\t\t\t\t<td  align=\"center\" style=\"background-color: #ffffff;background-position: 18px 50%;\" width=");
/* 2638 */                                     out.print(100 - criticalper);
/* 2639 */                                     out.write(" height=\"10\"> </td>\n\t\t\t\t\t<td width=\"1%\"></td>\n\t\t       \t</tr>\n\t\t  \t</tbody>\n\t\t   \t</table>\n\t\t</td>\n\t</tr>\n\t"); }
/* 2640 */                                   if (totalWarning > 0) {
/* 2641 */                                     out.write("\n\t<tr style=\"height: 20px;\" class=\"setSeverity\"  severityType=\"4\" resType=\"");
/* 2642 */                                     out.print(resType);
/* 2643 */                                     out.write("\">\n\t\t<td width=\"20\"></td>\n\t\t<td class=\"bulkmon-links onHoverCSS\" colspan=\"1\">");
/* 2644 */                                     out.print(FormatUtil.getString("Warning"));
/* 2645 */                                     out.write("</td>\n\t\t<td>:</td>\n\t\t<td class=\"bulkmon-links onHoverCSS\">");
/* 2646 */                                     out.print(totalWarning);
/* 2647 */                                     out.write("</td>\n\t\t<td class=\"onHoverCSS\" align=\"center\" title=\"");
/* 2648 */                                     out.print(FormatUtil.getString("Warning"));
/* 2649 */                                     out.write(32);
/* 2650 */                                     out.write(58);
/* 2651 */                                     out.write(32);
/* 2652 */                                     out.print(totalWarning);
/* 2653 */                                     out.write(" \" >\n\t\t\t<table style=\"border: 1px solid rgb(0, 0, 0);\" width=\"80\" cellpadding=\"0\" cellspacing=\"0\" height=\"10\">\n\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"center\" class=\"barGraphBg\" style=\"background-color: #ea940e;  background-position: 42px 50%;\" width=");
/* 2654 */                                     out.print(warningper);
/* 2655 */                                     out.write(" height=\"10\"> </td>\n\t\t\t\t\t<td align=\"center\" style=\"background-color: #ffffff;  background-position: 18px 50%;\" width=");
/* 2656 */                                     out.print(100 - warningper);
/* 2657 */                                     out.write(" height=\"10\"> </td>\n\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t</tr>\n\t\t\t</tbody>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n\t"); }
/* 2658 */                                   if (totalClear > 0) {
/* 2659 */                                     out.write("\n\t<tr style=\"height: 20px;\" class=\"setSeverity\"  severityType=\"5\" resType=\"");
/* 2660 */                                     out.print(resType);
/* 2661 */                                     out.write("\">\n\t\t<td width=\"20\"></td>\n\t\t<td class=\"bulkmon-links onHoverCSS\" colspan=\"1\">");
/* 2662 */                                     out.print(FormatUtil.getString("Clear"));
/* 2663 */                                     out.write("</td>\n\t\t<td>:</td>\n\t\t<td class=\"bulkmon-links onHoverCSS\">");
/* 2664 */                                     out.print(totalClear);
/* 2665 */                                     out.write("</td>\n\t\t<td class=\"onHoverCSS\" align=\"center\" title=\"");
/* 2666 */                                     out.print(FormatUtil.getString("Clear"));
/* 2667 */                                     out.write(32);
/* 2668 */                                     out.write(58);
/* 2669 */                                     out.write(32);
/* 2670 */                                     out.print(totalClear);
/* 2671 */                                     out.write(" \" >\n\t\t\t<table style=\"border: 1px solid rgb(0, 0, 0);\" width=\"80\" cellpadding=\"0\" cellspacing=\"0\" height=\"10\">\n\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"center\" class=\"barGraphBg\" style=\"background-color: #38da43;  background-position: 42px 50%;\" width=\"");
/* 2672 */                                     out.print(clearper);
/* 2673 */                                     out.write("\" height=\"10\"> </td>\n\t\t\t\t\t<td align=\"center\" style=\"background-color: #ffffff;  background-position: 18px 50%;\" width=\"");
/* 2674 */                                     out.print(100 - clearper);
/* 2675 */                                     out.write("\" height=\"10\"> </td>\n\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t</tr>\n\t\t       \t</tbody>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n\t");
/*      */                                   }
/* 2677 */                                   out.write("\n\t</table>\n</td>\n\n<!-- EUM total Moniors tabel ends!-->\n\n<!-- mail server, ping, LDAP and other icons tabel starts!-->\n\n\n\t\t\t");
/*      */                                   
/* 2679 */                                   Iterator<String> alertStausIterator = typeWiseAlertCountMap.keySet().iterator();
/* 2680 */                                   int i = 0;
/* 2681 */                                   String bgClass = "";
/* 2682 */                                   while (alertStausIterator.hasNext())
/*      */                                   {
/* 2684 */                                     i++;
/* 2685 */                                     String topResType = (String)alertStausIterator.next();
/* 2686 */                                     if (!topResType.equals("zxy"))
/*      */                                     {
/* 2688 */                                       Hashtable<Object, Object> hash = (Hashtable)typeWiseAlertCountMap.get(topResType);
/* 2689 */                                       int total = ((Integer)hash.get("Total")).intValue();
/* 2690 */                                       String alertStatusDetail = hash.get("Critical") + "/" + total;
/* 2691 */                                       String textClassName = "disabledtext";
/* 2692 */                                       String typeClassName = "";
/* 2693 */                                       if (total > 0)
/*      */                                       {
/* 2695 */                                         textClassName = "eum-icons-txt onHoverCSS ";
/* 2696 */                                         typeClassName = "setResType ";
/* 2697 */                                         if (resType.equals(topResType))
/*      */                                         {
/* 2699 */                                           typeClassName = typeClassName + "eum-active-bg";
/* 2700 */                                           textClassName = textClassName + "eum-icons-txt-bold";
/*      */                                         }
/*      */                                       }
/*      */                                       
/*      */ 
/* 2705 */                                       out.write("\n\t\t\t\t<td width=\"");
/* 2706 */                                       out.print(80 / typeWiseAlertCountMap.size());
/* 2707 */                                       out.write("%\" align=\"center\" class=\"");
/* 2708 */                                       out.print(typeClassName);
/* 2709 */                                       out.write("\" resType=\"");
/* 2710 */                                       out.print(topResType);
/* 2711 */                                       out.write(34);
/* 2712 */                                       out.write(62);
/* 2713 */                                       out.write("\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" ");
/* 2714 */                                       if (i < typeWiseAlertCountMap.size() - 1) {
/* 2715 */                                         out.write("class=\"eumDBSeperator\"");
/*      */                                       }
/* 2717 */                                       out.write(">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td><img src=\"/images/spacer.gif\" width=\"15\" /></td>\n\t\t\t\t\t\t<td  align=\"center\"><img src=\"");
/* 2718 */                                       out.print(hash.get("imageName"));
/* 2719 */                                       out.write("\"/></td>\n\t\t\t\t\t\t<td><img src=\"/images/spacer.gif\" width=\"15\" /></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td></td>\n\t\t\t\t\t\t<td align=\"center\" class=\"");
/* 2720 */                                       out.print(textClassName);
/* 2721 */                                       out.write(34);
/* 2722 */                                       out.write(62);
/* 2723 */                                       out.print(FormatUtil.getString((String)motypedisplaynames.get(topResType)));
/* 2724 */                                       out.write("<div> ");
/* 2725 */                                       out.print(alertStatusDetail);
/* 2726 */                                       out.write("  </div></td>\n\t\t\t\t\t\t<td></td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\n\t\t\t");
/*      */                                     } }
/* 2728 */                                   out.write("\n\n<!--mail server, ping, LDAP  and other icons tabel ends!-->\n</tr>\n</table>\n\n\n<!--EUM Details tabel Starts!-->\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\n<tr>\n<td class=\"eum-bg\" width=\"100%\">\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n<tr style=\"height: 30px;\">\n<td width=\"20\" ></td>\n\n<td class=\"eum-name-head\" width=\"160\">\n\t");
/* 2729 */                                   out.print(FormatUtil.getString("am.webclient.eumdashboard.name"));
/* 2730 */                                   out.write("<span>&nbsp;<img src=\"/images/img_dboard_heading1.gif\" style=\"position:relative; top:4px;\"></span>\n\t");
/* 2731 */                                   out.print(FormatUtil.getString("am.webclient.eumdashboard.location"));
/* 2732 */                                   out.write("<span>&nbsp;<img src=\"/images/img_dboard_heading2.gif\"style=\"position:relative; top:5px;\"/></span>\n</td>\n\n");
/*      */                                   
/* 2734 */                                   Iterator<String> agentIterator = ((LinkedHashMap)agentIdVsDispName).keySet().iterator();
/* 2735 */                                   while (agentIterator.hasNext())
/*      */                                   {
/* 2737 */                                     String agentId = (String)agentIterator.next();
/* 2738 */                                     String agentName = (String)((LinkedHashMap)agentIdVsDispName).get(agentId);
/* 2739 */                                     boolean status = !((String)agentIdVsStatus.get(agentId)).equals("0");
/* 2740 */                                     if ((agentName.length() > 15) && (((LinkedHashMap)agentIdVsDispName).size() > 4))
/*      */                                     {
/* 2742 */                                       agentName = FormatUtil.getTrimmedText(agentName, 15);
/*      */                                     }
/*      */                                     
/* 2745 */                                     String agentUrl = (String)agentIdVsUrl.get(agentId);
/* 2746 */                                     String fontColor = status ? "color: red;" : "color: #585858;";
/*      */                                     
/* 2748 */                                     out.write("\n\t\t<td class=\"eum-details-top-subhead\" align=\"center\"\n\t\t\t");
/* 2749 */                                     if (status) {
/* 2750 */                                       out.write(" style=\"cursor: pointer;");
/* 2751 */                                       out.print(fontColor);
/* 2752 */                                       out.write("\" onMouseOver=\"ddrivetip(this,event,'");
/* 2753 */                                       out.print(FormatUtil.getString((String)((LinkedHashMap)agentIdVsDispName).get(agentId)) + "-" + FormatUtil.getString("am.webclient.eumdashboard.agentdown"));
/* 2754 */                                       out.write("',false,true,'#000000',100,'lightyellow')\" onmouseout=\"hideddrivetip()\" ");
/*      */                                     }
/*      */                                     else {
/* 2757 */                                       out.write(" title=\"");
/* 2758 */                                       out.print(FormatUtil.getString((String)((LinkedHashMap)agentIdVsDispName).get(agentId)));
/* 2759 */                                       out.write(34);
/* 2760 */                                       out.write(32);
/*      */                                     }
/* 2762 */                                     out.write(">\n\t\t\t");
/* 2763 */                                     if ((agentUrl != null) && (!status)) {
/* 2764 */                                       out.write(" <a href=\"");
/* 2765 */                                       out.print(agentUrl);
/* 2766 */                                       out.write("\" style=\"");
/* 2767 */                                       out.print(fontColor);
/* 2768 */                                       out.write("\" target=\"_blank\">");
/* 2769 */                                       out.print(agentName);
/* 2770 */                                       out.write("</a>");
/*      */                                     } else {
/* 2772 */                                       out.write("\n\t\t\t ");
/*      */                                       
/* 2774 */                                       if (agentName.indexOf("(Local)") > -1) {
/* 2775 */                                         agentName = agentName.substring(0, agentName.indexOf("(Local)")) + "(" + FormatUtil.getString("am.webclient.useradministration.usergroup.local.text") + ")";
/*      */                                       }
/*      */                                       
/* 2778 */                                       out.write("\n\t\t\t ");
/* 2779 */                                       out.print(agentName);
/*      */                                     }
/* 2781 */                                     out.write("\n\t\t</td>\n\t");
/*      */                                   }
/*      */                                   
/* 2784 */                                   out.write("\n</tr>\n\n");
/*      */                                   
/* 2786 */                                   if (parentList.isEmpty())
/*      */                                   {
/* 2788 */                                     out.write("\n\t\t<tr height=\"30\">\n\t\t\t<td colspan=\"");
/* 2789 */                                     out.print(6 + ((LinkedHashMap)agentIdVsDispName).keySet().size());
/* 2790 */                                     out.write("\" align=\"center\" class=\"emptyTableMsg\">\n\t\t\t\t");
/* 2791 */                                     out.print(FormatUtil.getString("am.webclient.eumdashboard.nomonitor"));
/* 2792 */                                     out.write("\n\t\t\t</td>\n\t\t</tr>\n\t");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2796 */                                     for (int j = 0; j < parentList.size(); j++)
/*      */                                     {
/*      */ 
/* 2799 */                                       String resID = (String)parentList.get(j);
/* 2800 */                                       String monitorName = (String)idVsDispName.get(resID);
/*      */                                       
/* 2802 */                                       if ((monitorName != null) && (!monitorName.equals("null")))
/*      */                                       {
/* 2804 */                                         out.write("\n\t\t\t<tr class=\"ajaxMapHeader\"  onmouseover=\"this.className='ajaxMapHeaderHover'\"  onmouseout=\"this.className='ajaxMapHeader'\">\n\t\t\t\t<td class=\"yellowgrayborder-dashborads setPadding\">&nbsp;</td>\n\t\t\t\t<td class=\"yellowgrayborder-dashborads setPadding\" title=\"");
/* 2805 */                                         out.print(monitorName);
/* 2806 */                                         out.write("\">\n\t\t\t\t\t<a href=\"/showresource.do?resourceid=");
/* 2807 */                                         out.print(resID);
/* 2808 */                                         out.write("&method=showResourceForResourceID\" class=\"widget-links fl\">");
/* 2809 */                                         out.print(FormatUtil.getTrimmedText(monitorName, 20));
/* 2810 */                                         out.write("</a>\n\t\t\t\t\t<span style=\"float: right;\"><a href=\"javascript:openReports('");
/* 2811 */                                         out.print(resID);
/* 2812 */                                         out.write(39);
/* 2813 */                                         out.write(44);
/* 2814 */                                         out.write(39);
/* 2815 */                                         out.print((String)resIdVsType.get(resID));
/* 2816 */                                         out.write("');\"><img src=\"/images/icon-anamoly-responsetime.gif\" border=\"0\"/></a></span>\n\t\t\t\t</td>\n\n\t\t\t");
/*      */                                         
/* 2818 */                                         agentIterator = ((LinkedHashMap)agentIdVsDispName).keySet().iterator();
/*      */                                         
/*      */ 
/* 2821 */                                         while (agentIterator.hasNext())
/*      */                                         {
/* 2823 */                                           String entity = "-1";
/* 2824 */                                           String agentId = (String)agentIterator.next();
/*      */                                           
/* 2826 */                                           String childMonId = null;
/* 2827 */                                           String childResType = null;
/* 2828 */                                           if (((LinkedHashMap)parentVSagentIdVsResId).containsKey(resID))
/*      */                                           {
/* 2830 */                                             childMonId = (String)((HashMap)((LinkedHashMap)parentVSagentIdVsResId).get(resID)).get(agentId);
/* 2831 */                                             childResType = (String)resIdVsType.get(childMonId);
/*      */                                           }
/*      */                                           
/*      */                                           try
/*      */                                           {
/* 2836 */                                             if (childMonId != null)
/*      */                                             {
/* 2838 */                                               entity = (childMonId + "_" + healthkeys.get(childResType)).replace("_", "#");
/*      */                                             }
/*      */                                           }
/*      */                                           catch (Exception m)
/*      */                                           {
/* 2843 */                                             m.printStackTrace();
/*      */                                           }
/* 2845 */                                           String imgPath = "/images/icon_health_unknown_plain.gif";
/* 2846 */                                           if ((childMonId != null) && (getSeverityImageForHealth(healthProps.getProperty(entity)).contains("icon_health_clear")))
/*      */                                           {
/* 2848 */                                             imgPath = "/images/icon_health_clear_plain.gif";
/*      */                                           }
/* 2850 */                                           else if ((childMonId != null) && (getSeverityImageForHealth(healthProps.getProperty(entity)).contains("icon_health_critical")))
/*      */                                           {
/* 2852 */                                             imgPath = "/images/icon_health_critical_plain.gif";
/*      */                                           }
/* 2854 */                                           else if ((childMonId != null) && (getSeverityImageForHealth(healthProps.getProperty(entity)).contains("icon_health_warning")))
/*      */                                           {
/* 2856 */                                             imgPath = "/images/icon_health_warning_plain.gif";
/*      */                                           }
/*      */                                           
/* 2859 */                                           out.write("\n\n  \t\t\t\t\t<td align=\"center\" class=\"yellowgrayborder-dashborads setPadding\">\n  \t\t\t\t\t");
/* 2860 */                                           if (childMonId == null)
/*      */                                           {
/* 2862 */                                             out.write("\n  \t\t\t\t\t\t-");
/* 2863 */                                             out.print(FormatUtil.getString("am.webclient.eumdashboard.childNotAvailable"));
/* 2864 */                                             out.write("-\n  \t\t\t\t\t");
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2868 */                                             out.write("\n\t\t\t\t\t\t<a style=\"float: none;\" href=\"/showresource.do?resourceid=");
/* 2869 */                                             out.print(childMonId);
/* 2870 */                                             out.write("&method=showResourceForResourceID\" onmouseover=\"ddrivetip(this,event,'");
/* 2871 */                                             out.print(healthProps.get(entity + "#" + "MESSAGE") == null ? FormatUtil.getString("am.webclient.eumdashboard.rcanotknown") : healthProps.get(entity + "#" + "MESSAGE"));
/* 2872 */                                             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"><img src=\"");
/* 2873 */                                             out.print(imgPath);
/* 2874 */                                             out.write("\"></img></a>\n  \t\t\t\t\t");
/*      */                                           }
/* 2876 */                                           out.write("\n  \t\t\t\t\t</td>\n  \t\t");
/*      */                                         }
/* 2878 */                                         out.write("\n\t\t\t\t</tr>\n\t\t\t");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 2883 */                                   out.write("\n\n</table>\n</td>\n</tr>\n</table>\n<!--EUM Details tabel Ends!-->\n\n</td>\n<!-- Inner tabel ends!-->\n</tr>\n</table>\n\n<a id=\"show_hide_help_card\" class=\"bodytext\" href=\" \" style=\"float:right;margin-right:12px\" ></a>\n\n<!---- Help card starts here-->\n\n<div id=\"help_card\" style=\"display:none\">\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" style=\"margin-top: 20px;\">\n\t<tr>\n\t\t<td class=\"helpCardHdrTopLeft\"/>\n\t\t<td class=\"helpCardHdrTopBg\">\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 2884 */                                   out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 2885 */                                   out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\t\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t</tr>\n\t<tr>\n\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t<td valign=\"top\">\n\t\t<!--//include your Helpcard template table here..-->\n\t\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td style=\"padding-top: 10px;\" class=\"boxedContent\">\n\t\t\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" class=\"help-content-bg\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerTopLeft\"/>\n\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerTopBg\"></td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerTopRight\"/>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg\">&nbsp;</td>\n");
/* 2886 */                                   out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg product-help\">\n\t\t\t\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"padding:10px;\">\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td><span class=\"bodytext\" style=\"text-align:justify;\">");
/* 2887 */                                   out.print(FormatUtil.getString("am.webclient.eumdashboard.helpcard"));
/* 2888 */                                   out.write("</span></td>\n\t\t\t\t\t\t\t\t\t\t\t<td style=\"width:10px;\"></td>\n\n\n\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n\t\t\t\t\t\t\t\t\t\t\t<div align=\"center\" ><b style=\"font-size:15px; line-height:45px;\">");
/* 2889 */                                   out.print(FormatUtil.getString("am.webclient.eumdashboard.withabbr.eum"));
/* 2890 */                                   out.write("</b></div>\n\n\t\t\t\t\t\t\t\t\t\t\t<table cellpaddin=\"0\" cellspacing=\"0\" class=\"eum-flow-bg-img\">\n                                          <tr>\n                                          <td valign=\"top\">\n                                          <table  cellpaddin=\"0\" cellspacing=\"0\" height=\"60\" width=\"609\">\n                                          <tr>\n                                            <td align=\"center\" class=\"eum-head-txt\" valign=\"top\" style=\"padding-top:20px; height:30px;\">");
/* 2891 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.eumenterprise"));
/* 2892 */                                   out.write("</td>\n                                            </tr>  \n                                               <tr>\n                                            <td align=\"center\" valign=\"absmiddle\" height=\"42\" style=\"padding:0px 0px 0px 100px; font-size:12px;\" class=\"at-a-glance\">");
/* 2893 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.eumhelpheading"));
/* 2894 */                                   out.write("</td>\n                                            </tr> \n                                            \n                                          </table>\n                                          \n                                          <table>\n                                          <tr>\n                                          <td><table  cellpaddin=\"0\" cellspacing=\"0\">\n                                        <tr>\n                                        <td  width=\"186\" valign=\"top\">\n                                        <table  cellpaddin=\"0\" cellspacing=\"0\" width=\"186\">\n                                        \n                                        <tr><td colspan=\"3\" style=\"height:43px;\"></td></tr>\n                                        <tr>\n                                        <td width=\"160\"></td>\n                                        <td width=\"97\" class=\"eumflowdiagram-font\" ><span style=\"position:relative; bottom:5px; right:3px;\">");
/* 2895 */                                   out.print(FormatUtil.getString("Mail Server"));
/* 2896 */                                   out.write("</span></td>\n                                        <td width=\"60\"></td>\n                                        </tr>\n                                        <tr><td colspan=\"3\" style=\"height:60px;\"></td></tr>\n                                        <tr>\n                                        <td></td>\n                                        <td class=\"eumflowdiagram-font\"><span style=\"position:relative; bottom:5px; right:5px;\">");
/* 2897 */                                   out.print(FormatUtil.getString("DNS Server"));
/* 2898 */                                   out.write("</span></td>\n                                        <td></td>\n                                        </tr>\n                                        <tr><td colspan=\"3\" style=\"height:52px;\"></td></tr>\n                                        <tr>\n                                        <td></td>\n                                        <td class=\"eumflowdiagram-font\"><span style=\"position:relative; bottom:5px; right:3px;\">");
/* 2899 */                                   out.print(FormatUtil.getString("am.webclient.rbm.intro.crmtext"));
/* 2900 */                                   out.write("</span></td>\n                                        <td></td>\n                                        </tr>\n                                        </table>\n                                        </td>\n                                        \n                                        </tr>\n                                        </table></td>\n                                        <td>\n                                        <table  cellpaddin=\"0\" cellspacing=\"0\" width=\"360\">                                             \n                                        <tr>\n                                        <td valign=\"top\">\n                                        <table cellpaddin=\"0\" cellspacing=\"0\">\n                                        <tr>\n                                        <td class=\"eumflowdiagram-font\" width=\"150\" align=\"center\" ><b style=\"font-size:11px;\">");
/* 2901 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.graphs"));
/* 2902 */                                   out.write("</b></td>                                             \n                                        <td class=\"eumflowdiagram-font\" width=\"177\"><b style=\"font-size:11px;\">");
/* 2903 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.rca"));
/* 2904 */                                   out.write("</b></td>\n                                        </tr>\n                                        <tr>\n                                        <td></td>\n                                        <td> <table  cellpaddin=\"0\" cellspacing=\"0\">\n                                        <tr>\n                                        <td width=\"35\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"15\"></td>\n                                        <td width=\"160\" class=\"eum-red-txt\"><b>");
/* 2905 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.crmhealth"));
/* 2906 */                                   out.write("</b></td>\n                                        </tr>\n                                         <tr>\n                                        <td ></td>\n                                        <td class=\"eum-red-txt\">");
/* 2907 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.qa"));
/* 2908 */                                   out.write("</td>\n                                        </tr>\n                                         <tr>\n                                        <td ></td>\n                                        <td class=\"eum-red-txt\">");
/* 2909 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.misc"));
/* 2910 */                                   out.write("</td>\n                                        </tr>\n                                        </table></td>\n                                        </tr>\n                                        </table>\n                                        </td>\n                                        </tr>\n                                        \n                                        <tr>\n                                        <td colspan=\"2\">\n                                        \n                                        <table cellpaddin=\"0\" cellspacing=\"0\">\n                                        <tr>\n                                        <td width=\"20\" height=\"70\"></td>\n                                        <td></td>\n                                        <td width=\"180\"></td>\n                                         <td></td>\n                                        </tr>\n                                        <tr>\n                                        <td></td>\n                                        <td class=\"eumflowdiagram-font\">");
/* 2911 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.headingalarm"));
/* 2912 */                                   out.write("</td>\n                                         <td class=\"eumflowdiagram-font\"><b style=\"position:relative; top:25px; left:40px;\">");
/* 2913 */                                   out.print(FormatUtil.getString("am.webclient.rbm.intro.apptext"));
/* 2914 */                                   out.write("</b></td>\n                                          <td class=\"eumflowdiagram-font\">");
/* 2915 */                                   out.print(FormatUtil.getString("Threshold"));
/* 2916 */                                   out.write("</td>\n                                        </tr>\n                                        <tr><td colspan=\"4\" height=\"40\"></td></tr>\n                                         <tr>\n                                        <td></td>\n                                        <td class=\"eumflowdiagram-font\">");
/* 2917 */                                   out.print(FormatUtil.getString("Anomaly"));
/* 2918 */                                   out.write(" </td>\n                                         <td class=\"eumflowdiagram-font\"></td>\n                                          <td class=\"eumflowdiagram-font\" align=\"center\">");
/* 2919 */                                   out.print(FormatUtil.getString("am.mypage.actions.button.text"));
/* 2920 */                                   out.write("</td>\n                                        </tr>\n                                        \n                                        </table>\n                                        \n                                        </td>\n                                        </tr>\n                                       \n                                        </table>\n                                        \n                                        </td>\n                                          </tr>\n                                          \n                                          <tr>\n                                          <td colspan=\"2\" height=\"237\">\n                                          <table cellpaddin=\"0\" cellspacing=\"0\">\n                                          <tr>\n                                          <td height=\"220\" width=\"145\"></td>\n                                          <td width=\"95\"></td>\n                                          <td width=\"150\"></td>\n                                           <td width=\"95\"></td>\n");
/* 2921 */                                   out.write("                                           <td width=\"100\"></td>\n                                          </tr>\n                                           <tr>\n                                          <td></td>\n\n                                            <td class=\"eumflowdiagram-font\"><b>");
/* 2922 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.branchoffice", new String[] { "1" }));
/* 2923 */                                   out.write("</b></td>\n                                            <td></td>\n                                             <td class=\"eumflowdiagram-font\"><b>");
/* 2924 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.branchoffice", new String[] { "2" }));
/* 2925 */                                   out.write("</b></td>\n                                             <td></td>\n                                          </tr>\n                                          <tr><td colspan=\"5\" height=\"50\"></td></tr>\n                                          <tr>\n                                          <td></td>\n                                          <td colspan=\"4\" class=\"eum-head-txt\">");
/* 2926 */                                   out.print(FormatUtil.getString("am.webclient.eum.helpcard.monitorbranchoffice"));
/* 2927 */                                   out.write("</td></tr>\n                                          </table>\n                                          </td>\n                                          </tr>\n                                          \n                                          \n                                          </table>\n                                          \n                                          \n                                          \n                                          </td>\n                                          </tr>                                        \n                                            </table>\n\t\t\t\t\t\t\t\t\t\t\t\n\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t</table>\n\n\n\n\n\t\t\t\t\t\t\t\t\t\t</td>\n\n\n\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t\t<td class=\"helpCardMainBtmBg\"/>\n");
/* 2928 */                                   out.write("\t\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\t\t\t</tr>\n</table>\n</div>\n<!-- Shadow tabel ends!-->\n");
/* 2929 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2930 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2934 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2935 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 2938 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2939 */                               out.write("\n</body>\n");
/* 2940 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2941 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2944 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2945 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2948 */                           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2949 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                           }
/*      */                           
/* 2952 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2953 */                           out.write(32);
/* 2954 */                           out.write(10);
/* 2955 */                           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2957 */                           out.write(10);
/* 2958 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2959 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2963 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2964 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                       }
/*      */                       
/* 2967 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2968 */                       out.write(10);
/* 2969 */                       out.write(10);
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2973 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 2976 */                     out.write("\n<script type=\"text/javascript\">\nfunction openReports(resId,resType)\n{\n\tMM_openBrWindow('/showReports.do?actionMethod=generateGlanceReport&period=0&Report=true&resourceType='+resType+'&attribute=glancereport&eumReport=true&eumMonId='+resId,'ExecutionTimeStatistic','resizable=yes,scrollbars=yes,width=950,height=700,top=15,left=15'); // No I18N\n}\n\njQuery(document).ready(function()\n{\n\tvar help_cookie=\"help_card\";// no I18N\n\tvar help_flag=ReadCookie(help_cookie);\n\tif(!(help_flag==\"true\"))\n\t{\n\t$( \"#help_card\" ).show();\n\t$(\"#show_hide_help_card\").attr(\"title\",\"");
/* 2977 */                     out.print(FormatUtil.getString("am.webclient.eumdashboard.helpcard.hide"));
/* 2978 */                     out.write("\"); // no I18N\n\t$(\"#show_hide_help_card\").text(\"");
/* 2979 */                     out.print(FormatUtil.getString("am.webclient.eumdashboard.helpcard.hide"));
/* 2980 */                     out.write("\");\n\t}\n\telse\n\t{\n\t$(\"#help_card\").hide(\"fast\");\n\t$(\"#show_hide_help_card\").attr(\"title\",\"");
/* 2981 */                     out.print(FormatUtil.getString("am.webclient.eumdashboard.helpcard.show"));
/* 2982 */                     out.write("\");//no I18N\n\t$(\"#show_hide_help_card\").text(\"");
/* 2983 */                     out.print(FormatUtil.getString("am.webclient.eumdashboard.helpcard.show"));
/* 2984 */                     out.write("\");\n\t}\t\n\t$('.onHoverCSS').hover( //NO I18N\n\t    function() {\n\t        $(this).css({'text-decoration':'underline','cursor':'pointer'});//No I18N\n\t    },\n\t    function() {\n\t        $(this).css({'text-decoration':'none','cursor':'normal'}); //No I18N\n\t    }\n    ),\n\n\n\tsortEumDashboard();\n\tjQuery('.setResType').click(function() //NO I18N\n   \t{\n\t   \tdocument.AMActionForm.resTypeValue.value=jQuery(this).attr(\"resType\"); //No I18N\n\t   \tdocument.AMActionForm.submit();\n \t}),\n \tjQuery('.setSeverity').click(function() //NO I18N\n   \t{\n\t   \tdocument.AMActionForm.severity.value=jQuery(this).attr(\"severityType\"); //No I18N\n\t   \tdocument.AMActionForm.resTypeValue.value=jQuery(this).attr(\"resType\"); //No I18N\n\t   \tdocument.AMActionForm.submit();\n \t}),\n\t\n\t$(\"#show_hide_help_card\").click(function(event){\n\tevent.preventDefault();\n\tset_cookie(\"help_card\",$( \"#help_card\" ).is(\":visible\"));// no I18n\n\tif($( \"#help_card\" ).is(\":visible\"))\n\t{\n\t$(\"#show_hide_help_card\").attr(\"title\",\"");
/* 2985 */                     out.print(FormatUtil.getString("am.webclient.eumdashboard.helpcard.show"));
/* 2986 */                     out.write("\");//no I18N\n\t$(\"#show_hide_help_card\").text(\"");
/* 2987 */                     out.print(FormatUtil.getString("am.webclient.eumdashboard.helpcard.show"));
/* 2988 */                     out.write("\");\n\t}\n\telse\n\t{\n\t\t$(\"#show_hide_help_card\").attr(\"title\",\"");
/* 2989 */                     out.print(FormatUtil.getString("am.webclient.eumdashboard.helpcard.hide"));
/* 2990 */                     out.write("\");//no I18N\n\t\t$(\"#show_hide_help_card\").text(\"");
/* 2991 */                     out.print(FormatUtil.getString("am.webclient.eumdashboard.helpcard.hide"));
/* 2992 */                     out.write("\");\n\t}\n\t$( \"#help_card\" ).toggle(\"slide\",{direction: 'right'},\"fast\");// no I18n\n\t});\n});\n\nfunction sortEumDashboard()\n{\n\tSORTTABLENAME = 'eumSnapTable'; //NO I18N\n\tvar numberOfColumnsToBeSorted=1;\n\tvar ignoreCheckBox = false;\n\tvar ignoreDefaultSorting=false;\n\tsortables_init(numberOfColumnsToBeSorted,ignoreCheckBox,ignoreDefaultSorting);\n}\n\nfunction showRelaventData(combo)\n{\n\tdocument.AMActionForm.submit();\n}\n</script>\n</html>\n");
/*      */                   }
/* 2994 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2995 */         out = _jspx_out;
/* 2996 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2997 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2998 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3001 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3007 */     PageContext pageContext = _jspx_page_context;
/* 3008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3010 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3011 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3012 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3014 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3016 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 3017 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3018 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3019 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3020 */       return true;
/*      */     }
/* 3022 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3023 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3028 */     PageContext pageContext = _jspx_page_context;
/* 3029 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3031 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.get(PutTag.class);
/* 3032 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3033 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3035 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */     
/* 3037 */     _jspx_th_tiles_005fput_005f2.setType("string");
/* 3038 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3039 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3040 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3041 */       return true;
/*      */     }
/* 3043 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3044 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3049 */     PageContext pageContext = _jspx_page_context;
/* 3050 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3052 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.get(OptionsCollectionTag.class);
/* 3053 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 3054 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 3056 */     _jspx_th_html_005foptionsCollection_005f0.setName("AMActionForm");
/*      */     
/* 3058 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("managedServers");
/* 3059 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 3060 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 3061 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3062 */       return true;
/*      */     }
/* 3064 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3065 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3070 */     PageContext pageContext = _jspx_page_context;
/* 3071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3073 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3074 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3075 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3077 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3079 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3080 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3081 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3082 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3083 */       return true;
/*      */     }
/* 3085 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3086 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\eumDashboard_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */