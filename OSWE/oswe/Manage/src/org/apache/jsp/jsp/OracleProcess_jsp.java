/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.OracleSgaGraph;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.oracle.bean.OracleBean;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.BarChart;
/*      */ import java.net.InetAddress;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEqualTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class OracleProcess_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 1378 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1423 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 1471 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/* 1982 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2203 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2207 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2221 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2225 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.release();
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.release();
/* 2229 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2231 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/* 2233 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.release();
/* 2234 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.release();
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.release();
/* 2237 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2244 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2247 */     JspWriter out = null;
/* 2248 */     Object page = this;
/* 2249 */     JspWriter _jspx_out = null;
/* 2250 */     PageContext _jspx_page_context = null;
/*      */     
/* 2252 */     Object _jspx_processDetails_1 = null;
/* 2253 */     Integer _jspx_m_1 = null;
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
/* 2266 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/* 2267 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2269 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2270 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2271 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2273 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2277 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2279 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2280 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2281 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2282 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2285 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2286 */         String available = null;
/* 2287 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2288 */         out.write(10);
/*      */         
/* 2290 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2291 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2292 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2294 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2298 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2300 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2301 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2302 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2303 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2306 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2307 */           String unavailable = null;
/* 2308 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2309 */           out.write(10);
/*      */           
/* 2311 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2312 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2313 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2315 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2319 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2321 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2322 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2323 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2324 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2327 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2328 */             String unmanaged = null;
/* 2329 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2330 */             out.write(10);
/*      */             
/* 2332 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2333 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2334 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2336 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2340 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2342 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2343 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2344 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2345 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2348 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2349 */               String scheduled = null;
/* 2350 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2351 */               out.write(10);
/*      */               
/* 2353 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2354 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2355 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2357 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2361 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2363 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2364 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2365 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2366 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2369 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2370 */                 String critical = null;
/* 2371 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2372 */                 out.write(10);
/*      */                 
/* 2374 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2375 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2376 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2378 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2382 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2384 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2385 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2386 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2387 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2390 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2391 */                   String clear = null;
/* 2392 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2393 */                   out.write(10);
/*      */                   
/* 2395 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2396 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2397 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2399 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2403 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2405 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2406 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2407 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2408 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2411 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2412 */                     String warning = null;
/* 2413 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2414 */                     out.write(10);
/* 2415 */                     out.write(10);
/*      */                     
/* 2417 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2418 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2420 */                     out.write(10);
/* 2421 */                     out.write(10);
/* 2422 */                     out.write(10);
/* 2423 */                     out.write(10);
/* 2424 */                     out.write(10);
/* 2425 */                     OracleBean databean = null;
/* 2426 */                     databean = (OracleBean)_jspx_page_context.getAttribute("databean", 2);
/* 2427 */                     if (databean == null) {
/* 2428 */                       databean = new OracleBean();
/* 2429 */                       _jspx_page_context.setAttribute("databean", databean, 2);
/*      */                     }
/* 2431 */                     out.write(10);
/* 2432 */                     OracleSgaGraph oraclegraph = null;
/* 2433 */                     oraclegraph = (OracleSgaGraph)_jspx_page_context.getAttribute("oraclegraph", 2);
/* 2434 */                     if (oraclegraph == null) {
/* 2435 */                       oraclegraph = new OracleSgaGraph();
/* 2436 */                       _jspx_page_context.setAttribute("oraclegraph", oraclegraph, 2);
/*      */                     }
/* 2438 */                     out.write(10);
/* 2439 */                     out.write(10);
/*      */                     
/* 2441 */                     databean.setmaxcollectiontime(request.getParameter("resourcename"));
/* 2442 */                     ArrayList list = new ArrayList();
/* 2443 */                     if (request.getParameter("resourcename") != null)
/*      */                     {
/* 2445 */                       list = databean.getPGAStatsPerProcess(request.getParameter("resourceid"), request.getParameter("resourcename"), request.getParameter("topN"));
/*      */                     }
/*      */                     
/*      */ 
/* 2449 */                     LinkedHashMap barGraphValues = (LinkedHashMap)list.get(1);
/* 2450 */                     oraclegraph.setGraphValues(barGraphValues);
/* 2451 */                     oraclegraph.setGraphType("BAR");
/* 2452 */                     pageContext.setAttribute("processList", (ArrayList)list.get(0));
/*      */                     
/*      */ 
/* 2455 */                     out.write(10);
/* 2456 */                     out.write(32);
/*      */                     
/* 2458 */                     EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 2459 */                     _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 2460 */                     _jspx_th_logic_005fequal_005f0.setParent(null);
/*      */                     
/* 2462 */                     _jspx_th_logic_005fequal_005f0.setName("topN");
/*      */                     
/* 2464 */                     _jspx_th_logic_005fequal_005f0.setValue("NULL");
/* 2465 */                     int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 2466 */                     if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */                       for (;;) {
/* 2468 */                         out.write("\n\t <link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n\t<link href=\"/images/");
/* 2469 */                         if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fequal_005f0, _jspx_page_context))
/*      */                           return;
/* 2471 */                         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\t<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\" height=\"55\">\n      <tbody><tr> <td><span class=\"headingboldwhite\">");
/* 2472 */                         out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.table.heading"));
/* 2473 */                         out.write("</span>\n\t\t\t\t\t\t\t\t<span class=\"headingwhite\"> </span> </td></tr>\n\t </tbody>\n\t</table>\n\t<br>\n");
/* 2474 */                         int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 2475 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2479 */                     if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 2480 */                       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/*      */                     }
/*      */                     else {
/* 2483 */                       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 2484 */                       out.write(9);
/* 2485 */                       out.write(10);
/*      */                       
/* 2487 */                       NotEqualTag _jspx_th_logic_005fnotEqual_005f0 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.get(NotEqualTag.class);
/* 2488 */                       _jspx_th_logic_005fnotEqual_005f0.setPageContext(_jspx_page_context);
/* 2489 */                       _jspx_th_logic_005fnotEqual_005f0.setParent(null);
/*      */                       
/* 2491 */                       _jspx_th_logic_005fnotEqual_005f0.setName("topN");
/*      */                       
/* 2493 */                       _jspx_th_logic_005fnotEqual_005f0.setValue("NULL");
/* 2494 */                       int _jspx_eval_logic_005fnotEqual_005f0 = _jspx_th_logic_005fnotEqual_005f0.doStartTag();
/* 2495 */                       if (_jspx_eval_logic_005fnotEqual_005f0 != 0) {
/*      */                         for (;;) {
/* 2497 */                           out.write("\n\t<br>\n\t\t<div class=\"apmconf-table-frame\">\n\t\t\t<div id=\"apmconf-tld-nav conf-mon-txt\"  style=\"height:30px; margin-left:10px;\">\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\">\n\t\t\t\t\t<tr>\t<td>\n\t\t\t\t\t\t<div style=\"display:inline;float:left\"><span class=\"conf-mon-txt\"> ");
/* 2498 */                           out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.topN.table.heading"));
/* 2499 */                           out.write("</span></div>\n\t\t\t\t\t</td></tr>\n\t\t\t\t</table>\t\n\t\t\t</div> \n\t\t\t<div style=\"overflow:auto;\">\t\t\t\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table\" width=\"100%\">\t\t\t\t\t   \n\t\t\t\t   \t <tr>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t<td align=\"center\" class=\"bodytext\" valign=\"top\">\n\t\t\t\t\t\t\t ");
/*      */                           
/* 2501 */                           BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 2502 */                           _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 2503 */                           _jspx_th_awolf_005fbarchart_005f0.setParent(_jspx_th_logic_005fnotEqual_005f0);
/*      */                           
/* 2505 */                           _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("oraclegraph");
/*      */                           
/* 2507 */                           _jspx_th_awolf_005fbarchart_005f0.setWidth("800");
/*      */                           
/* 2509 */                           _jspx_th_awolf_005fbarchart_005f0.setHeight("400");
/*      */                           
/* 2511 */                           _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */                           
/* 2513 */                           _jspx_th_awolf_005fbarchart_005f0.setUrl(false);
/*      */                           
/* 2515 */                           _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.column.process.heading"));
/*      */                           
/* 2517 */                           _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel(FormatUtil.getString("PGA Used"));
/*      */                           
/* 2519 */                           _jspx_th_awolf_005fbarchart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*      */                           
/* 2521 */                           _jspx_th_awolf_005fbarchart_005f0.setMaxBarWidth(0.05D);
/* 2522 */                           int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 2523 */                           if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 2524 */                             if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2525 */                               out = _jspx_page_context.pushBody();
/* 2526 */                               _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 2527 */                               _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2530 */                               out.write("\n\t\t\t\t\t\t\t");
/* 2531 */                               int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 2532 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2535 */                             if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2536 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2539 */                           if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 2540 */                             this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*      */                           }
/*      */                           
/* 2543 */                           this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 2544 */                           out.write(32);
/* 2545 */                           out.write(" \t\t\n\t\t\t\t\t\t</td>\t\n\t\t\t\t\t<tr>\t\n\t\t\t\t</table> \t\n\t\t\t</div>\t\n\t</div>\t\t\t \n\n<br>\n");
/* 2546 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f0.doAfterBody();
/* 2547 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2551 */                       if (_jspx_th_logic_005fnotEqual_005f0.doEndTag() == 5) {
/* 2552 */                         this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/*      */                       }
/*      */                       else {
/* 2555 */                         this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 2556 */                         out.write("\t\n\t<div class=\"apmconf-table-frame\">\n \t\t <div id=\"apmconf-tld-nav conf-mon-txt\"  style=\"height:30px; margin-left:10px;\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\">\n\t\t\t\t<tr>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<div style=\"display:inline;float:left\"><span class=\"conf-mon-txt\"> \n\t\t\t\t\t\t\t   \t");
/*      */                         
/* 2558 */                         EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 2559 */                         _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 2560 */                         _jspx_th_logic_005fequal_005f1.setParent(null);
/*      */                         
/* 2562 */                         _jspx_th_logic_005fequal_005f1.setName("topN");
/*      */                         
/* 2564 */                         _jspx_th_logic_005fequal_005f1.setValue("NULL");
/* 2565 */                         int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 2566 */                         if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */                           for (;;) {
/* 2568 */                             out.write("\n\t\t\t\t\t\t\t \t\t  \t");
/* 2569 */                             out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.pgastats.table.heading"));
/* 2570 */                             out.write(" \n\t\t\t\t\t\t\t   \t");
/* 2571 */                             int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 2572 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2576 */                         if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 2577 */                           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/*      */                         }
/*      */                         else {
/* 2580 */                           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 2581 */                           out.write("\n\t\t\t\t\t\t\t  \t");
/*      */                           
/* 2583 */                           NotEqualTag _jspx_th_logic_005fnotEqual_005f1 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.get(NotEqualTag.class);
/* 2584 */                           _jspx_th_logic_005fnotEqual_005f1.setPageContext(_jspx_page_context);
/* 2585 */                           _jspx_th_logic_005fnotEqual_005f1.setParent(null);
/*      */                           
/* 2587 */                           _jspx_th_logic_005fnotEqual_005f1.setName("topN");
/*      */                           
/* 2589 */                           _jspx_th_logic_005fnotEqual_005f1.setValue("NULL");
/* 2590 */                           int _jspx_eval_logic_005fnotEqual_005f1 = _jspx_th_logic_005fnotEqual_005f1.doStartTag();
/* 2591 */                           if (_jspx_eval_logic_005fnotEqual_005f1 != 0) {
/*      */                             for (;;) {
/* 2593 */                               out.write("\n\t\t\t\t\t\t\t  \t\t ");
/* 2594 */                               out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.pgastats.topN.table.heading"));
/* 2595 */                               out.write("\t\t\t\t  \t\t\n\t\t\t\t\t\t\t  \t");
/* 2596 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f1.doAfterBody();
/* 2597 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2601 */                           if (_jspx_th_logic_005fnotEqual_005f1.doEndTag() == 5) {
/* 2602 */                             this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1);
/*      */                           }
/*      */                           else {
/* 2605 */                             this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1);
/* 2606 */                             out.write("\t\n\t\t\t  \t\t\t\t </span></div>\n\t\t\t\t\t\t</td>\t\t\t\t\n\t\t\t\t</tr>\n\t\t\t</table>\t\n\t\t</div>\t\t\t  \t\n\t\t<div style=\"overflow:auto;\">  \n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table\" width=\"100%\" id=\"orclProcessDetails\">\t\n\t\t\t\t   ");
/*      */                             
/* 2608 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2609 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2610 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                             
/* 2612 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("processList");
/* 2613 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2614 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 2616 */                                 out.write("\t   \t \t\n\t\t\t\t   \t <tr>\t\t\t\n\t\t\t\t   \t \t\t<td height=\"26\" align=\"left\" width=\"10%\"  class=\"monitorinfoodd-conf apmconf-dullhead\">\t\t\t\t\n\t\t\t\t\t\t\t\t<a id=\"orclProcessDetails_header0\" href=\"#\" onclick=\"ts_resortTable(this,'orclProcessDetails',0);return false;\" \tclass=\"apmconf-dullhead\">");
/* 2617 */                                 out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.column.process.heading"));
/* 2618 */                                 out.write("&nbsp;<span class=\"sortarrow\">&nbsp;</span></a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td height=\"26\" align=\"left\" width=\"20%\"  class=\"monitorinfoodd-conf apmconf-dullhead\">\t\t\t\t\n\t\t\t\t\t\t\t\t<a id=\"orclProcessDetails_header1\" href=\"#\" onclick=\"ts_resortTable(this,'orclProcessDetails',0);return false;\" \tclass=\"apmconf-dullhead\">");
/* 2619 */                                 out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.column.program.heading"));
/* 2620 */                                 out.write("&nbsp;<span class=\"sortarrow\">&nbsp;</span></a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td height=\"26\" align=\"center\" width=\"15%\"  class=\"monitorinfoodd-conf apmconf-dullhead\">\t\t\t\t\n\t\t\t\t\t\t\t\t<a id=\"orclProcessDetails_header2\" href=\"#\" onclick=\"ts_resortTable(this,'orclProcessDetails',0);return false;\" \tclass=\"apmconf-dullhead\">");
/* 2621 */                                 out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.column.pgaUsed.heading"));
/* 2622 */                                 out.write("&nbsp;<span class=\"sortarrow\">&nbsp;</span></a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td height=\"26\"  align=\"center\" width=\"15%\"  class=\"monitorinfoodd-conf apmconf-dullhead\">\t\t\t\t\n\t\t\t\t\t\t\t\t<a id=\"orclProcessDetails_header3\" href=\"#\" onclick=\"ts_resortTable(this,'orclProcessDetails',0);return false;\" \tclass=\"apmconf-dullhead\">");
/* 2623 */                                 out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.column.allocated.heading"));
/* 2624 */                                 out.write("&nbsp;<span class=\"sortarrow\">&nbsp;</span></a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td height=\"26\"  align=\"center\" width=\"15%\"  class=\"monitorinfoodd-conf apmconf-dullhead\">\t\t\t\t\n\t\t\t\t\t\t\t\t<a id=\"orclProcessDetails_header4\" href=\"#\" onclick=\"ts_resortTable(this,'orclProcessDetails',0);return false;\" \tclass=\"apmconf-dullhead\">");
/* 2625 */                                 out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.column.maxUsed.heading"));
/* 2626 */                                 out.write("&nbsp;<span class=\"sortarrow\">&nbsp;</span></a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td height=\"26\"  align=\"center\" width=\"15%\"  class=\"monitorinfoodd-conf apmconf-dullhead\">\t\t\t\t\n\t\t\t\t\t\t\t\t<a id=\"orclProcessDetails_header4\" href=\"#\" onclick=\"ts_resortTable(this,'orclProcessDetails',0);return false;\" \tclass=\"apmconf-dullhead\">");
/* 2627 */                                 out.print(FormatUtil.getString("am.webclient.oracle.pgaPerProcess.column.freeable.heading"));
/* 2628 */                                 out.write("&nbsp;<span class=\"sortarrow\">&nbsp;</span></a>\n\t\t\t\t\t\t\t</td>\t\t\t\t   \t\t\t\n\t\t\t\t\t</tr>\t\n\t\t\t\t\t  ");
/*      */                                 
/* 2630 */                                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2631 */                                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2632 */                                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                 
/* 2634 */                                 _jspx_th_logic_005fiterate_005f0.setId("pList");
/*      */                                 
/* 2636 */                                 _jspx_th_logic_005fiterate_005f0.setName("processList");
/*      */                                 
/* 2638 */                                 _jspx_th_logic_005fiterate_005f0.setIndexId("n");
/* 2639 */                                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2640 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2641 */                                   Object pList = null;
/* 2642 */                                   Integer n = null;
/* 2643 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2644 */                                     out = _jspx_page_context.pushBody();
/* 2645 */                                     _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2646 */                                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                   }
/* 2648 */                                   pList = _jspx_page_context.findAttribute("pList");
/* 2649 */                                   n = (Integer)_jspx_page_context.findAttribute("n");
/*      */                                   for (;;) {
/* 2651 */                                     out.write("\t\n\t\t\t\t\t  \t\t<tr>\t\n\t\t\t\t\t\t\t ");
/*      */                                     
/* 2653 */                                     IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2654 */                                     _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2655 */                                     _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                     
/* 2657 */                                     _jspx_th_logic_005fiterate_005f1.setId("processDetails");
/*      */                                     
/* 2659 */                                     _jspx_th_logic_005fiterate_005f1.setName("pList");
/*      */                                     
/* 2661 */                                     _jspx_th_logic_005fiterate_005f1.setIndexId("m");
/* 2662 */                                     int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2663 */                                     if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2664 */                                       Object processDetails = null;
/* 2665 */                                       Integer m = null;
/* 2666 */                                       if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2667 */                                         out = _jspx_page_context.pushBody();
/* 2668 */                                         _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2669 */                                         _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                       }
/* 2671 */                                       processDetails = _jspx_page_context.findAttribute("processDetails");
/* 2672 */                                       m = (Integer)_jspx_page_context.findAttribute("m");
/*      */                                       for (;;) {
/* 2674 */                                         out.write("\t\n\t\t\t\t\t\t\t\t \t");
/* 2675 */                                         if (_jspx_meth_logic_005fequal_005f2(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                           return;
/* 2677 */                                         out.write("\t\n\t\t\t\t\t\t\t\t\t");
/* 2678 */                                         if (_jspx_meth_logic_005fequal_005f3(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                           return;
/* 2680 */                                         out.write("\t\n\t\t\t\t\t\t\t\t\t");
/* 2681 */                                         if (_jspx_meth_logic_005fequal_005f4(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                           return;
/* 2683 */                                         out.write("\t\n\t\t\t\t\t\t\t\t\t");
/* 2684 */                                         if (_jspx_meth_logic_005fequal_005f5(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                           return;
/* 2686 */                                         out.write("\t\n\t\t\t\t\t\t\t\t\t");
/* 2687 */                                         if (_jspx_meth_logic_005fequal_005f6(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                           return;
/* 2689 */                                         out.write("\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/* 2690 */                                         if (_jspx_meth_logic_005fequal_005f7(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                           return;
/* 2692 */                                         out.write("\t\n\t\t\t\t\t\t\t ");
/* 2693 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 2694 */                                         processDetails = _jspx_page_context.findAttribute("processDetails");
/* 2695 */                                         m = (Integer)_jspx_page_context.findAttribute("m");
/* 2696 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2699 */                                       if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2700 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2703 */                                     if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 2704 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                     }
/*      */                                     
/* 2707 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 2708 */                                     out.write("\n\t\t\t\t\t\t\t</tr>\t \n\t\t\t\t\t");
/* 2709 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2710 */                                     pList = _jspx_page_context.findAttribute("pList");
/* 2711 */                                     n = (Integer)_jspx_page_context.findAttribute("n");
/* 2712 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2715 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2716 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2719 */                                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2720 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                 }
/*      */                                 
/* 2723 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2724 */                                 out.write("\t\t \t\n\t\t\t\t");
/* 2725 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2726 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2730 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2731 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                             }
/*      */                             else {
/* 2734 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2735 */                               out.write("\n\t\t\t\t");
/*      */                               
/* 2737 */                               EmptyTag _jspx_th_logic_005fempty_005f4 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2738 */                               _jspx_th_logic_005fempty_005f4.setPageContext(_jspx_page_context);
/* 2739 */                               _jspx_th_logic_005fempty_005f4.setParent(null);
/*      */                               
/* 2741 */                               _jspx_th_logic_005fempty_005f4.setName("processList");
/* 2742 */                               int _jspx_eval_logic_005fempty_005f4 = _jspx_th_logic_005fempty_005f4.doStartTag();
/* 2743 */                               if (_jspx_eval_logic_005fempty_005f4 != 0) {
/*      */                                 for (;;) {
/* 2745 */                                   out.write("\n\t\t\t  \t\t<tr><td width=\"72%\" colspan=\"6\" height=\"29\" class=\"whitegrayborder-conf-mon\" align=\"center\">");
/* 2746 */                                   out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2747 */                                   out.write("</td> </tr>\n\t\t\t  ");
/* 2748 */                                   int evalDoAfterBody = _jspx_th_logic_005fempty_005f4.doAfterBody();
/* 2749 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2753 */                               if (_jspx_th_logic_005fempty_005f4.doEndTag() == 5) {
/* 2754 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4);
/*      */                               }
/*      */                               else {
/* 2757 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4);
/* 2758 */                                 out.write("\n\t</table>\t\n</div>\t\n");
/*      */                               }
/* 2760 */                             } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2761 */         out = _jspx_out;
/* 2762 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2763 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2764 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2767 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fequal_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2773 */     PageContext pageContext = _jspx_page_context;
/* 2774 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2776 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2777 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2778 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fequal_005f0);
/*      */     
/* 2780 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2782 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2783 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2784 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2785 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2786 */       return true;
/*      */     }
/* 2788 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f2(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2794 */     PageContext pageContext = _jspx_page_context;
/* 2795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2797 */     EqualTag _jspx_th_logic_005fequal_005f2 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2798 */     _jspx_th_logic_005fequal_005f2.setPageContext(_jspx_page_context);
/* 2799 */     _jspx_th_logic_005fequal_005f2.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 2801 */     _jspx_th_logic_005fequal_005f2.setValue("SPID");
/*      */     
/* 2803 */     _jspx_th_logic_005fequal_005f2.setProperty("key");
/*      */     
/* 2805 */     _jspx_th_logic_005fequal_005f2.setName("processDetails");
/* 2806 */     int _jspx_eval_logic_005fequal_005f2 = _jspx_th_logic_005fequal_005f2.doStartTag();
/* 2807 */     if (_jspx_eval_logic_005fequal_005f2 != 0) {
/*      */       for (;;) {
/* 2809 */         out.write("\n\t\t\t\t\t\t\t\t \t\t\t\t\t\t<td class=\"whitegrayborder-conf-mon\" align=\"left\"  style=\"padding-left:10px\">");
/* 2810 */         if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_logic_005fequal_005f2, _jspx_page_context))
/* 2811 */           return true;
/* 2812 */         out.write("</td>\t\n\t\t\t\t\t\t\t\t\t");
/* 2813 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f2.doAfterBody();
/* 2814 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2818 */     if (_jspx_th_logic_005fequal_005f2.doEndTag() == 5) {
/* 2819 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 2820 */       return true;
/*      */     }
/* 2822 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 2823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_logic_005fequal_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2828 */     PageContext pageContext = _jspx_page_context;
/* 2829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2831 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(WriteTag.class);
/* 2832 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2833 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_logic_005fequal_005f2);
/*      */     
/* 2835 */     _jspx_th_bean_005fwrite_005f0.setName("processDetails");
/*      */     
/* 2837 */     _jspx_th_bean_005fwrite_005f0.setProperty("value");
/*      */     
/* 2839 */     _jspx_th_bean_005fwrite_005f0.setFormat("#");
/* 2840 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2841 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2842 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2843 */       return true;
/*      */     }
/* 2845 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f3(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2851 */     PageContext pageContext = _jspx_page_context;
/* 2852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2854 */     EqualTag _jspx_th_logic_005fequal_005f3 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2855 */     _jspx_th_logic_005fequal_005f3.setPageContext(_jspx_page_context);
/* 2856 */     _jspx_th_logic_005fequal_005f3.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 2858 */     _jspx_th_logic_005fequal_005f3.setValue("PROGRAM");
/*      */     
/* 2860 */     _jspx_th_logic_005fequal_005f3.setProperty("key");
/*      */     
/* 2862 */     _jspx_th_logic_005fequal_005f3.setName("processDetails");
/* 2863 */     int _jspx_eval_logic_005fequal_005f3 = _jspx_th_logic_005fequal_005f3.doStartTag();
/* 2864 */     if (_jspx_eval_logic_005fequal_005f3 != 0) {
/*      */       for (;;) {
/* 2866 */         out.write("\n\t\t\t\t\t\t\t\t \t\t\t\t\t\t<td  class=\"whitegrayborder-conf-mon\" align=\"left\">");
/* 2867 */         if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_logic_005fequal_005f3, _jspx_page_context))
/* 2868 */           return true;
/* 2869 */         out.write("</td>\t\n\t\t\t\t\t\t\t\t\t");
/* 2870 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f3.doAfterBody();
/* 2871 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2875 */     if (_jspx_th_logic_005fequal_005f3.doEndTag() == 5) {
/* 2876 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f3);
/* 2877 */       return true;
/*      */     }
/* 2879 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f3);
/* 2880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_logic_005fequal_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2885 */     PageContext pageContext = _jspx_page_context;
/* 2886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2888 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 2889 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2890 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_logic_005fequal_005f3);
/*      */     
/* 2892 */     _jspx_th_bean_005fwrite_005f1.setName("processDetails");
/*      */     
/* 2894 */     _jspx_th_bean_005fwrite_005f1.setProperty("value");
/* 2895 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2896 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2897 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2898 */       return true;
/*      */     }
/* 2900 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f4(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2906 */     PageContext pageContext = _jspx_page_context;
/* 2907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2909 */     EqualTag _jspx_th_logic_005fequal_005f4 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2910 */     _jspx_th_logic_005fequal_005f4.setPageContext(_jspx_page_context);
/* 2911 */     _jspx_th_logic_005fequal_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 2913 */     _jspx_th_logic_005fequal_005f4.setValue("PGA_USED");
/*      */     
/* 2915 */     _jspx_th_logic_005fequal_005f4.setProperty("key");
/*      */     
/* 2917 */     _jspx_th_logic_005fequal_005f4.setName("processDetails");
/* 2918 */     int _jspx_eval_logic_005fequal_005f4 = _jspx_th_logic_005fequal_005f4.doStartTag();
/* 2919 */     if (_jspx_eval_logic_005fequal_005f4 != 0) {
/*      */       for (;;) {
/* 2921 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 2922 */         if (_jspx_meth_logic_005fempty_005f0(_jspx_th_logic_005fequal_005f4, _jspx_page_context))
/* 2923 */           return true;
/* 2924 */         out.write("\n\t\t\t\t\t\t\t\t \t\t");
/* 2925 */         if (_jspx_meth_logic_005fnotEmpty_005f1(_jspx_th_logic_005fequal_005f4, _jspx_page_context))
/* 2926 */           return true;
/* 2927 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 2928 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f4.doAfterBody();
/* 2929 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2933 */     if (_jspx_th_logic_005fequal_005f4.doEndTag() == 5) {
/* 2934 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f4);
/* 2935 */       return true;
/*      */     }
/* 2937 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f4);
/* 2938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f0(JspTag _jspx_th_logic_005fequal_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2943 */     PageContext pageContext = _jspx_page_context;
/* 2944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2946 */     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 2947 */     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2948 */     _jspx_th_logic_005fempty_005f0.setParent((Tag)_jspx_th_logic_005fequal_005f4);
/*      */     
/* 2950 */     _jspx_th_logic_005fempty_005f0.setName("processDetails");
/*      */     
/* 2952 */     _jspx_th_logic_005fempty_005f0.setProperty("value");
/* 2953 */     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2954 */     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */       for (;;) {
/* 2956 */         out.write("<td  class=\"whitegrayborder-conf-mon\" align=\"center\">-</td>");
/* 2957 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2958 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2962 */     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2963 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2964 */       return true;
/*      */     }
/* 2966 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f1(JspTag _jspx_th_logic_005fequal_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2972 */     PageContext pageContext = _jspx_page_context;
/* 2973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2975 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 2976 */     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2977 */     _jspx_th_logic_005fnotEmpty_005f1.setParent((Tag)_jspx_th_logic_005fequal_005f4);
/*      */     
/* 2979 */     _jspx_th_logic_005fnotEmpty_005f1.setName("processDetails");
/*      */     
/* 2981 */     _jspx_th_logic_005fnotEmpty_005f1.setProperty("value");
/* 2982 */     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2983 */     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */       for (;;) {
/* 2985 */         out.write("<td  class=\"whitegrayborder-conf-mon\" align=\"center\" style=\"padding-right:70px\">");
/* 2986 */         if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/* 2987 */           return true;
/* 2988 */         out.write("</td>\t");
/* 2989 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2990 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2994 */     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2995 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2996 */       return true;
/*      */     }
/* 2998 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2999 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3004 */     PageContext pageContext = _jspx_page_context;
/* 3005 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3007 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(WriteTag.class);
/* 3008 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 3009 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 3011 */     _jspx_th_bean_005fwrite_005f2.setName("processDetails");
/*      */     
/* 3013 */     _jspx_th_bean_005fwrite_005f2.setProperty("value");
/*      */     
/* 3015 */     _jspx_th_bean_005fwrite_005f2.setFormat("#.##");
/* 3016 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 3017 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 3018 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 3019 */       return true;
/*      */     }
/* 3021 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 3022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f5(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3027 */     PageContext pageContext = _jspx_page_context;
/* 3028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3030 */     EqualTag _jspx_th_logic_005fequal_005f5 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 3031 */     _jspx_th_logic_005fequal_005f5.setPageContext(_jspx_page_context);
/* 3032 */     _jspx_th_logic_005fequal_005f5.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 3034 */     _jspx_th_logic_005fequal_005f5.setValue("PGA_ALLOCATED");
/*      */     
/* 3036 */     _jspx_th_logic_005fequal_005f5.setProperty("key");
/*      */     
/* 3038 */     _jspx_th_logic_005fequal_005f5.setName("processDetails");
/* 3039 */     int _jspx_eval_logic_005fequal_005f5 = _jspx_th_logic_005fequal_005f5.doStartTag();
/* 3040 */     if (_jspx_eval_logic_005fequal_005f5 != 0) {
/*      */       for (;;) {
/* 3042 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 3043 */         if (_jspx_meth_logic_005fempty_005f1(_jspx_th_logic_005fequal_005f5, _jspx_page_context))
/* 3044 */           return true;
/* 3045 */         out.write("\n\t\t\t\t\t\t\t\t \t\t");
/* 3046 */         if (_jspx_meth_logic_005fnotEmpty_005f2(_jspx_th_logic_005fequal_005f5, _jspx_page_context))
/* 3047 */           return true;
/* 3048 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 3049 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f5.doAfterBody();
/* 3050 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3054 */     if (_jspx_th_logic_005fequal_005f5.doEndTag() == 5) {
/* 3055 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f5);
/* 3056 */       return true;
/*      */     }
/* 3058 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f5);
/* 3059 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f1(JspTag _jspx_th_logic_005fequal_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3064 */     PageContext pageContext = _jspx_page_context;
/* 3065 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3067 */     EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 3068 */     _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3069 */     _jspx_th_logic_005fempty_005f1.setParent((Tag)_jspx_th_logic_005fequal_005f5);
/*      */     
/* 3071 */     _jspx_th_logic_005fempty_005f1.setName("processDetails");
/*      */     
/* 3073 */     _jspx_th_logic_005fempty_005f1.setProperty("value");
/* 3074 */     int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3075 */     if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */       for (;;) {
/* 3077 */         out.write("<td  class=\"whitegrayborder-conf-mon\" align=\"center\">-</td>");
/* 3078 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3079 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3083 */     if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3084 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3085 */       return true;
/*      */     }
/* 3087 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f2(JspTag _jspx_th_logic_005fequal_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3093 */     PageContext pageContext = _jspx_page_context;
/* 3094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3096 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3097 */     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3098 */     _jspx_th_logic_005fnotEmpty_005f2.setParent((Tag)_jspx_th_logic_005fequal_005f5);
/*      */     
/* 3100 */     _jspx_th_logic_005fnotEmpty_005f2.setName("processDetails");
/*      */     
/* 3102 */     _jspx_th_logic_005fnotEmpty_005f2.setProperty("value");
/* 3103 */     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3104 */     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */       for (;;) {
/* 3106 */         out.write("<td  class=\"whitegrayborder-conf-mon\" align=\"center\" style=\"padding-right:70px\">");
/* 3107 */         if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/* 3108 */           return true;
/* 3109 */         out.write("</td>\t");
/* 3110 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 3111 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3115 */     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 3116 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3117 */       return true;
/*      */     }
/* 3119 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f3(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3125 */     PageContext pageContext = _jspx_page_context;
/* 3126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3128 */     WriteTag _jspx_th_bean_005fwrite_005f3 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(WriteTag.class);
/* 3129 */     _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
/* 3130 */     _jspx_th_bean_005fwrite_005f3.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 3132 */     _jspx_th_bean_005fwrite_005f3.setName("processDetails");
/*      */     
/* 3134 */     _jspx_th_bean_005fwrite_005f3.setProperty("value");
/*      */     
/* 3136 */     _jspx_th_bean_005fwrite_005f3.setFormat("#.##");
/* 3137 */     int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
/* 3138 */     if (_jspx_th_bean_005fwrite_005f3.doEndTag() == 5) {
/* 3139 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 3140 */       return true;
/*      */     }
/* 3142 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 3143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f6(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3148 */     PageContext pageContext = _jspx_page_context;
/* 3149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3151 */     EqualTag _jspx_th_logic_005fequal_005f6 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 3152 */     _jspx_th_logic_005fequal_005f6.setPageContext(_jspx_page_context);
/* 3153 */     _jspx_th_logic_005fequal_005f6.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 3155 */     _jspx_th_logic_005fequal_005f6.setValue("MAX_PGA");
/*      */     
/* 3157 */     _jspx_th_logic_005fequal_005f6.setProperty("key");
/*      */     
/* 3159 */     _jspx_th_logic_005fequal_005f6.setName("processDetails");
/* 3160 */     int _jspx_eval_logic_005fequal_005f6 = _jspx_th_logic_005fequal_005f6.doStartTag();
/* 3161 */     if (_jspx_eval_logic_005fequal_005f6 != 0) {
/*      */       for (;;) {
/* 3163 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 3164 */         if (_jspx_meth_logic_005fempty_005f2(_jspx_th_logic_005fequal_005f6, _jspx_page_context))
/* 3165 */           return true;
/* 3166 */         out.write("\n\t\t\t\t\t\t\t\t \t\t");
/* 3167 */         if (_jspx_meth_logic_005fnotEmpty_005f3(_jspx_th_logic_005fequal_005f6, _jspx_page_context))
/* 3168 */           return true;
/* 3169 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 3170 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f6.doAfterBody();
/* 3171 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3175 */     if (_jspx_th_logic_005fequal_005f6.doEndTag() == 5) {
/* 3176 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f6);
/* 3177 */       return true;
/*      */     }
/* 3179 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f6);
/* 3180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f2(JspTag _jspx_th_logic_005fequal_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3185 */     PageContext pageContext = _jspx_page_context;
/* 3186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3188 */     EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 3189 */     _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 3190 */     _jspx_th_logic_005fempty_005f2.setParent((Tag)_jspx_th_logic_005fequal_005f6);
/*      */     
/* 3192 */     _jspx_th_logic_005fempty_005f2.setName("processDetails");
/*      */     
/* 3194 */     _jspx_th_logic_005fempty_005f2.setProperty("value");
/* 3195 */     int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 3196 */     if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */       for (;;) {
/* 3198 */         out.write("<td  class=\"whitegrayborder-conf-mon\" align=\"center\">-</td>");
/* 3199 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 3200 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3204 */     if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 3205 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 3206 */       return true;
/*      */     }
/* 3208 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 3209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f3(JspTag _jspx_th_logic_005fequal_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3214 */     PageContext pageContext = _jspx_page_context;
/* 3215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3217 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3218 */     _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 3219 */     _jspx_th_logic_005fnotEmpty_005f3.setParent((Tag)_jspx_th_logic_005fequal_005f6);
/*      */     
/* 3221 */     _jspx_th_logic_005fnotEmpty_005f3.setName("processDetails");
/*      */     
/* 3223 */     _jspx_th_logic_005fnotEmpty_005f3.setProperty("value");
/* 3224 */     int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 3225 */     if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */       for (;;) {
/* 3227 */         out.write("<td class=\"whitegrayborder-conf-mon\" align=\"center\" style=\"padding-right:70px\">");
/* 3228 */         if (_jspx_meth_bean_005fwrite_005f4(_jspx_th_logic_005fnotEmpty_005f3, _jspx_page_context))
/* 3229 */           return true;
/* 3230 */         out.write("</td>\t");
/* 3231 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 3232 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3236 */     if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 3237 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 3238 */       return true;
/*      */     }
/* 3240 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 3241 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3246 */     PageContext pageContext = _jspx_page_context;
/* 3247 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3249 */     WriteTag _jspx_th_bean_005fwrite_005f4 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(WriteTag.class);
/* 3250 */     _jspx_th_bean_005fwrite_005f4.setPageContext(_jspx_page_context);
/* 3251 */     _jspx_th_bean_005fwrite_005f4.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f3);
/*      */     
/* 3253 */     _jspx_th_bean_005fwrite_005f4.setName("processDetails");
/*      */     
/* 3255 */     _jspx_th_bean_005fwrite_005f4.setProperty("value");
/*      */     
/* 3257 */     _jspx_th_bean_005fwrite_005f4.setFormat("#.##");
/* 3258 */     int _jspx_eval_bean_005fwrite_005f4 = _jspx_th_bean_005fwrite_005f4.doStartTag();
/* 3259 */     if (_jspx_th_bean_005fwrite_005f4.doEndTag() == 5) {
/* 3260 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f4);
/* 3261 */       return true;
/*      */     }
/* 3263 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f4);
/* 3264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f7(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3269 */     PageContext pageContext = _jspx_page_context;
/* 3270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3272 */     EqualTag _jspx_th_logic_005fequal_005f7 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 3273 */     _jspx_th_logic_005fequal_005f7.setPageContext(_jspx_page_context);
/* 3274 */     _jspx_th_logic_005fequal_005f7.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 3276 */     _jspx_th_logic_005fequal_005f7.setValue("FREEABLE_PGA");
/*      */     
/* 3278 */     _jspx_th_logic_005fequal_005f7.setProperty("key");
/*      */     
/* 3280 */     _jspx_th_logic_005fequal_005f7.setName("processDetails");
/* 3281 */     int _jspx_eval_logic_005fequal_005f7 = _jspx_th_logic_005fequal_005f7.doStartTag();
/* 3282 */     if (_jspx_eval_logic_005fequal_005f7 != 0) {
/*      */       for (;;) {
/* 3284 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 3285 */         if (_jspx_meth_logic_005fempty_005f3(_jspx_th_logic_005fequal_005f7, _jspx_page_context))
/* 3286 */           return true;
/* 3287 */         out.write("\n\t\t\t\t\t\t\t\t \t\t");
/* 3288 */         if (_jspx_meth_logic_005fnotEmpty_005f4(_jspx_th_logic_005fequal_005f7, _jspx_page_context))
/* 3289 */           return true;
/* 3290 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 3291 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f7.doAfterBody();
/* 3292 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3296 */     if (_jspx_th_logic_005fequal_005f7.doEndTag() == 5) {
/* 3297 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f7);
/* 3298 */       return true;
/*      */     }
/* 3300 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f7);
/* 3301 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f3(JspTag _jspx_th_logic_005fequal_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3306 */     PageContext pageContext = _jspx_page_context;
/* 3307 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3309 */     EmptyTag _jspx_th_logic_005fempty_005f3 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 3310 */     _jspx_th_logic_005fempty_005f3.setPageContext(_jspx_page_context);
/* 3311 */     _jspx_th_logic_005fempty_005f3.setParent((Tag)_jspx_th_logic_005fequal_005f7);
/*      */     
/* 3313 */     _jspx_th_logic_005fempty_005f3.setName("processDetails");
/*      */     
/* 3315 */     _jspx_th_logic_005fempty_005f3.setProperty("value");
/* 3316 */     int _jspx_eval_logic_005fempty_005f3 = _jspx_th_logic_005fempty_005f3.doStartTag();
/* 3317 */     if (_jspx_eval_logic_005fempty_005f3 != 0) {
/*      */       for (;;) {
/* 3319 */         out.write("<td  class=\"whitegrayborder-conf-mon\" align=\"center\">-</td>");
/* 3320 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f3.doAfterBody();
/* 3321 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3325 */     if (_jspx_th_logic_005fempty_005f3.doEndTag() == 5) {
/* 3326 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f3);
/* 3327 */       return true;
/*      */     }
/* 3329 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f3);
/* 3330 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f4(JspTag _jspx_th_logic_005fequal_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3335 */     PageContext pageContext = _jspx_page_context;
/* 3336 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3338 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3339 */     _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 3340 */     _jspx_th_logic_005fnotEmpty_005f4.setParent((Tag)_jspx_th_logic_005fequal_005f7);
/*      */     
/* 3342 */     _jspx_th_logic_005fnotEmpty_005f4.setName("processDetails");
/*      */     
/* 3344 */     _jspx_th_logic_005fnotEmpty_005f4.setProperty("value");
/* 3345 */     int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 3346 */     if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */       for (;;) {
/* 3348 */         out.write("<td class=\"whitegrayborder-conf-mon\" align=\"center\" style=\"padding-right:70px\">");
/* 3349 */         if (_jspx_meth_bean_005fwrite_005f5(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/* 3350 */           return true;
/* 3351 */         out.write("</td>\t");
/* 3352 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 3353 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3357 */     if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 3358 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 3359 */       return true;
/*      */     }
/* 3361 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 3362 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f5(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3367 */     PageContext pageContext = _jspx_page_context;
/* 3368 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3370 */     WriteTag _jspx_th_bean_005fwrite_005f5 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(WriteTag.class);
/* 3371 */     _jspx_th_bean_005fwrite_005f5.setPageContext(_jspx_page_context);
/* 3372 */     _jspx_th_bean_005fwrite_005f5.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 3374 */     _jspx_th_bean_005fwrite_005f5.setName("processDetails");
/*      */     
/* 3376 */     _jspx_th_bean_005fwrite_005f5.setProperty("value");
/*      */     
/* 3378 */     _jspx_th_bean_005fwrite_005f5.setFormat("#.##");
/* 3379 */     int _jspx_eval_bean_005fwrite_005f5 = _jspx_th_bean_005fwrite_005f5.doStartTag();
/* 3380 */     if (_jspx_th_bean_005fwrite_005f5.doEndTag() == 5) {
/* 3381 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f5);
/* 3382 */       return true;
/*      */     }
/* 3384 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f5);
/* 3385 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\OracleProcess_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */