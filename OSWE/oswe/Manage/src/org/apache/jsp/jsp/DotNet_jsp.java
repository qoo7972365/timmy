/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
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
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class DotNet_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   56 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   59 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   60 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   61 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   68 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   73 */     ArrayList list = null;
/*   74 */     StringBuffer sbf = new StringBuffer();
/*   75 */     ManagedApplication mo = new ManagedApplication();
/*   76 */     if (distinct)
/*      */     {
/*   78 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   82 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   85 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   87 */       ArrayList row = (ArrayList)list.get(i);
/*   88 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   89 */       if (distinct) {
/*   90 */         sbf.append(row.get(0));
/*      */       } else
/*   92 */         sbf.append(row.get(1));
/*   93 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   96 */     return sbf.toString(); }
/*      */   
/*   98 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  101 */     if (severity == null)
/*      */     {
/*  103 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  105 */     if (severity.equals("5"))
/*      */     {
/*  107 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  109 */     if (severity.equals("1"))
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  116 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  123 */     if (severity == null)
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  127 */     if (severity.equals("1"))
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  131 */     if (severity.equals("4"))
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  135 */     if (severity.equals("5"))
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  142 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  148 */     if (severity == null)
/*      */     {
/*  150 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  152 */     if (severity.equals("5"))
/*      */     {
/*  154 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  156 */     if (severity.equals("1"))
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  162 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  168 */     if (severity == null)
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  172 */     if (severity.equals("1"))
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  176 */     if (severity.equals("4"))
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  180 */     if (severity.equals("5"))
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  186 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  192 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  198 */     if (severity == 5)
/*      */     {
/*  200 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  202 */     if (severity == 1)
/*      */     {
/*  204 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  209 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  215 */     if (severity == null)
/*      */     {
/*  217 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  219 */     if (severity.equals("5"))
/*      */     {
/*  221 */       if (isAvailability) {
/*  222 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  225 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  228 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  230 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  232 */     if (severity.equals("1"))
/*      */     {
/*  234 */       if (isAvailability) {
/*  235 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  238 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  245 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  252 */     if (severity == null)
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  256 */     if (severity.equals("5"))
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  260 */     if (severity.equals("4"))
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  264 */     if (severity.equals("1"))
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  271 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  277 */     if (severity == null)
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  281 */     if (severity.equals("5"))
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  285 */     if (severity.equals("4"))
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  289 */     if (severity.equals("1"))
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  296 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  303 */     if (severity == null)
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  307 */     if (severity.equals("5"))
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  311 */     if (severity.equals("4"))
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  315 */     if (severity.equals("1"))
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  322 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  330 */     StringBuffer out = new StringBuffer();
/*  331 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  332 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  333 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  334 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  335 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  336 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  337 */     out.append("</tr>");
/*  338 */     out.append("</form></table>");
/*  339 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  346 */     if (val == null)
/*      */     {
/*  348 */       return "-";
/*      */     }
/*      */     
/*  351 */     String ret = FormatUtil.formatNumber(val);
/*  352 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  353 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  356 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  360 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  368 */     StringBuffer out = new StringBuffer();
/*  369 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  370 */     out.append("<tr>");
/*  371 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  373 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  375 */     out.append("</tr>");
/*  376 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  380 */       if (j % 2 == 0)
/*      */       {
/*  382 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  386 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  389 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  391 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  394 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  398 */       out.append("</tr>");
/*      */     }
/*  400 */     out.append("</table>");
/*  401 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  402 */     out.append("<tr>");
/*  403 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  404 */     out.append("</tr>");
/*  405 */     out.append("</table>");
/*  406 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  412 */     StringBuffer out = new StringBuffer();
/*  413 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  414 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  415 */     out.append("<tr>");
/*  416 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  417 */     out.append("<tr>");
/*  418 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  419 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  420 */     out.append("</tr>");
/*  421 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  424 */       out.append("<tr>");
/*  425 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  426 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  427 */       out.append("</tr>");
/*      */     }
/*      */     
/*  430 */     out.append("</table>");
/*  431 */     out.append("</table>");
/*  432 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  437 */     if (severity.equals("0"))
/*      */     {
/*  439 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  443 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  450 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  463 */     StringBuffer out = new StringBuffer();
/*  464 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  465 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  467 */       out.append("<tr>");
/*  468 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  469 */       out.append("</tr>");
/*      */       
/*      */ 
/*  472 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  474 */         String borderclass = "";
/*      */         
/*      */ 
/*  477 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  479 */         out.append("<tr>");
/*      */         
/*  481 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  482 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  483 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  489 */     out.append("</table><br>");
/*  490 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  491 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  493 */       List sLinks = secondLevelOfLinks[0];
/*  494 */       List sText = secondLevelOfLinks[1];
/*  495 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  498 */         out.append("<tr>");
/*  499 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  500 */         out.append("</tr>");
/*  501 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  503 */           String borderclass = "";
/*      */           
/*      */ 
/*  506 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  508 */           out.append("<tr>");
/*      */           
/*  510 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  511 */           if (sLinks.get(i).toString().length() == 0) {
/*  512 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  515 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  517 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  521 */     out.append("</table>");
/*  522 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  529 */     StringBuffer out = new StringBuffer();
/*  530 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  531 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  533 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  535 */         out.append("<tr>");
/*  536 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  537 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  541 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  543 */           String borderclass = "";
/*      */           
/*      */ 
/*  546 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  548 */           out.append("<tr>");
/*      */           
/*  550 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  551 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  552 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  555 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  558 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  563 */     out.append("</table><br>");
/*  564 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  565 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  567 */       List sLinks = secondLevelOfLinks[0];
/*  568 */       List sText = secondLevelOfLinks[1];
/*  569 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  572 */         out.append("<tr>");
/*  573 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  574 */         out.append("</tr>");
/*  575 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  577 */           String borderclass = "";
/*      */           
/*      */ 
/*  580 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  582 */           out.append("<tr>");
/*      */           
/*  584 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  585 */           if (sLinks.get(i).toString().length() == 0) {
/*  586 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  589 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  591 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  595 */     out.append("</table>");
/*  596 */     return out.toString();
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
/*  609 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  618 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  621 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  624 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  627 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  630 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  638 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  643 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  648 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  653 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  658 */     if (val != null)
/*      */     {
/*  660 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  664 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  669 */     if (val == null) {
/*  670 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  674 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  679 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  685 */     if (val != null)
/*      */     {
/*  687 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  691 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  697 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  702 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  706 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  711 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  716 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  721 */     String hostaddress = "";
/*  722 */     String ip = request.getHeader("x-forwarded-for");
/*  723 */     if (ip == null)
/*  724 */       ip = request.getRemoteAddr();
/*  725 */     InetAddress add = null;
/*  726 */     if (ip.equals("127.0.0.1")) {
/*  727 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  731 */       add = InetAddress.getByName(ip);
/*      */     }
/*  733 */     hostaddress = add.getHostName();
/*  734 */     if (hostaddress.indexOf('.') != -1) {
/*  735 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  736 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  740 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  745 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  751 */     if (severity == null)
/*      */     {
/*  753 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  755 */     if (severity.equals("5"))
/*      */     {
/*  757 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  759 */     if (severity.equals("1"))
/*      */     {
/*  761 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  766 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  771 */     ResultSet set = null;
/*  772 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  773 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  775 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  776 */       if (set.next()) { String str1;
/*  777 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  778 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  781 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  786 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  789 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  791 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  795 */     StringBuffer rca = new StringBuffer();
/*  796 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  797 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  800 */     int rcalength = key.length();
/*  801 */     String split = "6. ";
/*  802 */     int splitPresent = key.indexOf(split);
/*  803 */     String div1 = "";String div2 = "";
/*  804 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  806 */       if (rcalength > 180) {
/*  807 */         rca.append("<span class=\"rca-critical-text\">");
/*  808 */         getRCATrimmedText(key, rca);
/*  809 */         rca.append("</span>");
/*      */       } else {
/*  811 */         rca.append("<span class=\"rca-critical-text\">");
/*  812 */         rca.append(key);
/*  813 */         rca.append("</span>");
/*      */       }
/*  815 */       return rca.toString();
/*      */     }
/*  817 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  818 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  819 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  820 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  821 */     getRCATrimmedText(div1, rca);
/*  822 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  825 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  826 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  827 */     getRCATrimmedText(div2, rca);
/*  828 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  830 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  835 */     String[] st = msg.split("<br>");
/*  836 */     for (int i = 0; i < st.length; i++) {
/*  837 */       String s = st[i];
/*  838 */       if (s.length() > 180) {
/*  839 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  841 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  845 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  846 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  848 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  852 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  853 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  854 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  857 */       if (key == null) {
/*  858 */         return ret;
/*      */       }
/*      */       
/*  861 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  862 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  865 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  866 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  867 */       set = AMConnectionPool.executeQueryStmt(query);
/*  868 */       if (set.next())
/*      */       {
/*  870 */         String helpLink = set.getString("LINK");
/*  871 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  874 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  880 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  899 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  890 */         if (set != null) {
/*  891 */           AMConnectionPool.closeStatement(set);
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
/*  905 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  906 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  908 */       String entityStr = (String)keys.nextElement();
/*  909 */       String mmessage = temp.getProperty(entityStr);
/*  910 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  911 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  913 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  919 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  920 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  922 */       String entityStr = (String)keys.nextElement();
/*  923 */       String mmessage = temp.getProperty(entityStr);
/*  924 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  925 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  927 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  932 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  942 */     String des = new String();
/*  943 */     while (str.indexOf(find) != -1) {
/*  944 */       des = des + str.substring(0, str.indexOf(find));
/*  945 */       des = des + replace;
/*  946 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  948 */     des = des + str;
/*  949 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  956 */       if (alert == null)
/*      */       {
/*  958 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  960 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  962 */         return "&nbsp;";
/*      */       }
/*      */       
/*  965 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  967 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  970 */       int rcalength = test.length();
/*  971 */       if (rcalength < 300)
/*      */       {
/*  973 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  977 */       StringBuffer out = new StringBuffer();
/*  978 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  979 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  980 */       out.append("</div>");
/*  981 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  982 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  983 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  988 */       ex.printStackTrace();
/*      */     }
/*  990 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  996 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1001 */     ArrayList attribIDs = new ArrayList();
/* 1002 */     ArrayList resIDs = new ArrayList();
/* 1003 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1005 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1007 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1009 */       String resourceid = "";
/* 1010 */       String resourceType = "";
/* 1011 */       if (type == 2) {
/* 1012 */         resourceid = (String)row.get(0);
/* 1013 */         resourceType = (String)row.get(3);
/*      */       }
/* 1015 */       else if (type == 3) {
/* 1016 */         resourceid = (String)row.get(0);
/* 1017 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1020 */         resourceid = (String)row.get(6);
/* 1021 */         resourceType = (String)row.get(7);
/*      */       }
/* 1023 */       resIDs.add(resourceid);
/* 1024 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1025 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1027 */       String healthentity = null;
/* 1028 */       String availentity = null;
/* 1029 */       if (healthid != null) {
/* 1030 */         healthentity = resourceid + "_" + healthid;
/* 1031 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1034 */       if (availid != null) {
/* 1035 */         availentity = resourceid + "_" + availid;
/* 1036 */         entitylist.add(availentity);
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
/* 1050 */     Properties alert = getStatus(entitylist);
/* 1051 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1056 */     int size = monitorList.size();
/*      */     
/* 1058 */     String[] severity = new String[size];
/*      */     
/* 1060 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1062 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1063 */       String resourceName1 = (String)row1.get(7);
/* 1064 */       String resourceid1 = (String)row1.get(6);
/* 1065 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1066 */       if (severity[j] == null)
/*      */       {
/* 1068 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1072 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1074 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1076 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1079 */         if (sev > 0) {
/* 1080 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1081 */           monitorList.set(k, monitorList.get(j));
/* 1082 */           monitorList.set(j, t);
/* 1083 */           String temp = severity[k];
/* 1084 */           severity[k] = severity[j];
/* 1085 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1091 */     int z = 0;
/* 1092 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1095 */       int i = 0;
/* 1096 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1099 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1103 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1107 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1109 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1112 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1116 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1119 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1120 */       String resourceName1 = (String)row1.get(7);
/* 1121 */       String resourceid1 = (String)row1.get(6);
/* 1122 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1123 */       if (hseverity[j] == null)
/*      */       {
/* 1125 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1130 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1132 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1135 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1138 */         if (hsev > 0) {
/* 1139 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1140 */           monitorList.set(k, monitorList.get(j));
/* 1141 */           monitorList.set(j, t);
/* 1142 */           String temp1 = hseverity[k];
/* 1143 */           hseverity[k] = hseverity[j];
/* 1144 */           hseverity[j] = temp1;
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
/* 1156 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1157 */     boolean forInventory = false;
/* 1158 */     String trdisplay = "none";
/* 1159 */     String plusstyle = "inline";
/* 1160 */     String minusstyle = "none";
/* 1161 */     String haidTopLevel = "";
/* 1162 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1164 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1166 */         haidTopLevel = request.getParameter("haid");
/* 1167 */         forInventory = true;
/* 1168 */         trdisplay = "table-row;";
/* 1169 */         plusstyle = "none";
/* 1170 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1177 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1180 */     ArrayList listtoreturn = new ArrayList();
/* 1181 */     StringBuffer toreturn = new StringBuffer();
/* 1182 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1183 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1184 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1186 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1188 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1189 */       String childresid = (String)singlerow.get(0);
/* 1190 */       String childresname = (String)singlerow.get(1);
/* 1191 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1192 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1193 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1194 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1195 */       String unmanagestatus = (String)singlerow.get(5);
/* 1196 */       String actionstatus = (String)singlerow.get(6);
/* 1197 */       String linkclass = "monitorgp-links";
/* 1198 */       String titleforres = childresname;
/* 1199 */       String titilechildresname = childresname;
/* 1200 */       String childimg = "/images/trcont.png";
/* 1201 */       String flag = "enable";
/* 1202 */       String dcstarted = (String)singlerow.get(8);
/* 1203 */       String configMonitor = "";
/* 1204 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1205 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1207 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1209 */       if (singlerow.get(7) != null)
/*      */       {
/* 1211 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1213 */       String haiGroupType = "0";
/* 1214 */       if ("HAI".equals(childtype))
/*      */       {
/* 1216 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1218 */       childimg = "/images/trend.png";
/* 1219 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1220 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1221 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1223 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1225 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1227 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1228 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1231 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1233 */         linkclass = "disabledtext";
/* 1234 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1236 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1237 */       String availmouseover = "";
/* 1238 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1240 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1242 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1243 */       String healthmouseover = "";
/* 1244 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1246 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1249 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1250 */       int spacing = 0;
/* 1251 */       if (level >= 1)
/*      */       {
/* 1253 */         spacing = 40 * level;
/*      */       }
/* 1255 */       if (childtype.equals("HAI"))
/*      */       {
/* 1257 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1258 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1259 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1261 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1262 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1263 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1264 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1265 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1266 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1267 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1268 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1269 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1270 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1271 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1273 */         if (!forInventory)
/*      */         {
/* 1275 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1278 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1280 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1282 */           actions = editlink + actions;
/*      */         }
/* 1284 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1286 */           actions = actions + associatelink;
/*      */         }
/* 1288 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1289 */         String arrowimg = "";
/* 1290 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1292 */           actions = "";
/* 1293 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1294 */           checkbox = "";
/* 1295 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1297 */         if (isIt360)
/*      */         {
/* 1299 */           actionimg = "";
/* 1300 */           actions = "";
/* 1301 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1302 */           checkbox = "";
/*      */         }
/*      */         
/* 1305 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1307 */           actions = "";
/*      */         }
/* 1309 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1311 */           checkbox = "";
/*      */         }
/*      */         
/* 1314 */         String resourcelink = "";
/*      */         
/* 1316 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1318 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1322 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1325 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1331 */         if (!isIt360)
/*      */         {
/* 1333 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1337 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1340 */         toreturn.append("</tr>");
/* 1341 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1343 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1344 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1348 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1349 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1352 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1356 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1358 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1360 */             toreturn.append(assocMessage);
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1364 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1370 */         String resourcelink = null;
/* 1371 */         boolean hideEditLink = false;
/* 1372 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1374 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1375 */           hideEditLink = true;
/* 1376 */           if (isIt360)
/*      */           {
/* 1378 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1382 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1384 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1386 */           hideEditLink = true;
/* 1387 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1388 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1393 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1396 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1397 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1398 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1399 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1400 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1401 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1402 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1403 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1404 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1405 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1406 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1407 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1408 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1410 */         if (hideEditLink)
/*      */         {
/* 1412 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1414 */         if (!forInventory)
/*      */         {
/* 1416 */           removefromgroup = "";
/*      */         }
/* 1418 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1419 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1420 */           actions = actions + configcustomfields;
/*      */         }
/* 1422 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1424 */           actions = editlink + actions;
/*      */         }
/* 1426 */         String managedLink = "";
/* 1427 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1429 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1430 */           actions = "";
/* 1431 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1432 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1435 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1437 */           checkbox = "";
/*      */         }
/*      */         
/* 1440 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1442 */           actions = "";
/*      */         }
/* 1444 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1447 */         if (isIt360)
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1455 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1456 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1457 */         if (!isIt360)
/*      */         {
/* 1459 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1463 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1465 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1468 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1475 */       StringBuilder toreturn = new StringBuilder();
/* 1476 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1477 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1478 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1479 */       String title = "";
/* 1480 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1481 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1482 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1483 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1485 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1487 */       else if ("5".equals(severity))
/*      */       {
/* 1489 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1493 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1495 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1496 */       toreturn.append(v);
/*      */       
/* 1498 */       toreturn.append(link);
/* 1499 */       if (severity == null)
/*      */       {
/* 1501 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1503 */       else if (severity.equals("5"))
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1507 */       else if (severity.equals("4"))
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       else if (severity.equals("1"))
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1518 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1520 */       toreturn.append("</a>");
/* 1521 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1525 */       ex.printStackTrace();
/*      */     }
/* 1527 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1534 */       StringBuilder toreturn = new StringBuilder();
/* 1535 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1536 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1537 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1538 */       if (message == null)
/*      */       {
/* 1540 */         message = "";
/*      */       }
/*      */       
/* 1543 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1544 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1546 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1547 */       toreturn.append(v);
/*      */       
/* 1549 */       toreturn.append(link);
/*      */       
/* 1551 */       if (severity == null)
/*      */       {
/* 1553 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1555 */       else if (severity.equals("5"))
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1559 */       else if (severity.equals("1"))
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1566 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1568 */       toreturn.append("</a>");
/* 1569 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1575 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1578 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1579 */     if (invokeActions != null) {
/* 1580 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1581 */       while (iterator.hasNext()) {
/* 1582 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1583 */         if (actionmap.containsKey(actionid)) {
/* 1584 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1589 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1593 */     String actionLink = "";
/* 1594 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1595 */     String query = "";
/* 1596 */     ResultSet rs = null;
/* 1597 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1598 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1599 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1600 */       actionLink = "method=" + methodName;
/*      */     }
/* 1602 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1603 */       actionLink = methodName;
/*      */     }
/* 1605 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1606 */     Iterator itr = methodarglist.iterator();
/* 1607 */     boolean isfirstparam = true;
/* 1608 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1609 */     while (itr.hasNext()) {
/* 1610 */       HashMap argmap = (HashMap)itr.next();
/* 1611 */       String argtype = (String)argmap.get("TYPE");
/* 1612 */       String argname = (String)argmap.get("IDENTITY");
/* 1613 */       String paramname = (String)argmap.get("PARAMETER");
/* 1614 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1615 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1616 */         isfirstparam = false;
/* 1617 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1619 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1623 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1627 */         actionLink = actionLink + "&";
/*      */       }
/* 1629 */       String paramValue = null;
/* 1630 */       String tempargname = argname;
/* 1631 */       if (commonValues.getProperty(tempargname) != null) {
/* 1632 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1635 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1636 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1637 */           if (dbType.equals("mysql")) {
/* 1638 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1641 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1643 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1645 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1646 */             if (rs.next()) {
/* 1647 */               paramValue = rs.getString("VALUE");
/* 1648 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1652 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1656 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1659 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1664 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1665 */           paramValue = rowId;
/*      */         }
/* 1667 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1668 */           paramValue = managedObjectName;
/*      */         }
/* 1670 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1671 */           paramValue = resID;
/*      */         }
/* 1673 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1674 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1677 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1679 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1680 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1681 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1683 */     return actionLink;
/*      */   }
/*      */   
/* 1686 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1687 */     String dependentAttribute = null;
/* 1688 */     String align = "left";
/*      */     
/* 1690 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1691 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1692 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1693 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1694 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1695 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1696 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1697 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1698 */       align = "center";
/*      */     }
/*      */     
/* 1701 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1702 */     String actualdata = "";
/*      */     
/* 1704 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1705 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1706 */         actualdata = availValue;
/*      */       }
/* 1708 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1709 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1713 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1714 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1717 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1723 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1724 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1725 */       toreturn.append("<table>");
/* 1726 */       toreturn.append("<tr>");
/* 1727 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1728 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1729 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1730 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1731 */         String toolTip = "";
/* 1732 */         String hideClass = "";
/* 1733 */         String textStyle = "";
/* 1734 */         boolean isreferenced = true;
/* 1735 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1736 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1737 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1738 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1740 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1741 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1742 */           while (valueList.hasMoreTokens()) {
/* 1743 */             String dependentVal = valueList.nextToken();
/* 1744 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1745 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1746 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1748 */               toolTip = "";
/* 1749 */               hideClass = "";
/* 1750 */               isreferenced = false;
/* 1751 */               textStyle = "disabledtext";
/* 1752 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1756 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1757 */           toolTip = "";
/* 1758 */           hideClass = "";
/* 1759 */           isreferenced = false;
/* 1760 */           textStyle = "disabledtext";
/* 1761 */           if (dependentImageMap != null) {
/* 1762 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1763 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1766 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1770 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1771 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1772 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1773 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1774 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1775 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1777 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1778 */           if (isreferenced) {
/* 1779 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1783 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1784 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1785 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1786 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1787 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1788 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1790 */           toreturn.append("</span>");
/* 1791 */           toreturn.append("</a>");
/* 1792 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1795 */       toreturn.append("</tr>");
/* 1796 */       toreturn.append("</table>");
/* 1797 */       toreturn.append("</td>");
/*      */     } else {
/* 1799 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1802 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1806 */     String colTime = null;
/* 1807 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1808 */     if ((rows != null) && (rows.size() > 0)) {
/* 1809 */       Iterator<String> itr = rows.iterator();
/* 1810 */       String maxColQuery = "";
/* 1811 */       for (;;) { if (itr.hasNext()) {
/* 1812 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1813 */           ResultSet maxCol = null;
/*      */           try {
/* 1815 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1816 */             while (maxCol.next()) {
/* 1817 */               if (colTime == null) {
/* 1818 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1821 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1830 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1832 */               if (maxCol != null)
/* 1833 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1835 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1830 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1832 */               if (maxCol != null)
/* 1833 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1835 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1840 */     return colTime;
/*      */   }
/*      */   
/* 1843 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1844 */     tablename = null;
/* 1845 */     ResultSet rsTable = null;
/* 1846 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1848 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1849 */       while (rsTable.next()) {
/* 1850 */         tablename = rsTable.getString("DATATABLE");
/* 1851 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1852 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1865 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1856 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1859 */         if (rsTable != null)
/* 1860 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1862 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1868 */     String argsList = "";
/* 1869 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1871 */       if (showArgsMap.get(row) != null) {
/* 1872 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1873 */         if (showArgslist != null) {
/* 1874 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1875 */             if (argsList.trim().equals("")) {
/* 1876 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1879 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1886 */       e.printStackTrace();
/* 1887 */       return "";
/*      */     }
/* 1889 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1894 */     String argsList = "";
/* 1895 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1898 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1900 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1901 */         if (hideArgsList != null)
/*      */         {
/* 1903 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1905 */             if (argsList.trim().equals(""))
/*      */             {
/* 1907 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1911 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1919 */       ex.printStackTrace();
/*      */     }
/* 1921 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1925 */     StringBuilder toreturn = new StringBuilder();
/* 1926 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1933 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1934 */       Iterator itr = tActionList.iterator();
/* 1935 */       while (itr.hasNext()) {
/* 1936 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1937 */         String confirmmsg = "";
/* 1938 */         String link = "";
/* 1939 */         String isJSP = "NO";
/* 1940 */         HashMap tactionMap = (HashMap)itr.next();
/* 1941 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1942 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1943 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1944 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1945 */           (actionmap.containsKey(actionId))) {
/* 1946 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1947 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1948 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1949 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1950 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1952 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1958 */           if (isTableAction) {
/* 1959 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1962 */             tableName = "Link";
/* 1963 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1964 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1965 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1966 */             toreturn.append("</a></td>");
/*      */           }
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1977 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1983 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1985 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1986 */       Properties prop = (Properties)node.getUserObject();
/* 1987 */       String mgID = prop.getProperty("label");
/* 1988 */       String mgName = prop.getProperty("value");
/* 1989 */       String isParent = prop.getProperty("isParent");
/* 1990 */       int mgIDint = Integer.parseInt(mgID);
/* 1991 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1993 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1995 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1996 */       if (node.getChildCount() > 0)
/*      */       {
/* 1998 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2000 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2002 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2004 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2008 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2013 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2015 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2017 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2019 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2023 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2026 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2027 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2029 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2033 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2035 */       if (node.getChildCount() > 0)
/*      */       {
/* 2037 */         builder.append("<UL>");
/* 2038 */         printMGTree(node, builder);
/* 2039 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2044 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2045 */     StringBuffer toReturn = new StringBuffer();
/* 2046 */     String table = "-";
/*      */     try {
/* 2048 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2049 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2050 */       float total = 0.0F;
/* 2051 */       while (it.hasNext()) {
/* 2052 */         String attName = (String)it.next();
/* 2053 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2054 */         boolean roundOffData = false;
/* 2055 */         if ((data != null) && (!data.equals(""))) {
/* 2056 */           if (data.indexOf(",") != -1) {
/* 2057 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2060 */             float value = Float.parseFloat(data);
/* 2061 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2064 */             total += value;
/* 2065 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2068 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2073 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2074 */       while (attVsWidthList.hasNext()) {
/* 2075 */         String attName = (String)attVsWidthList.next();
/* 2076 */         String data = (String)attVsWidthProps.get(attName);
/* 2077 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2078 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2079 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2080 */         String className = (String)graphDetails.get("ClassName");
/* 2081 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2082 */         if (percentage < 1.0F)
/*      */         {
/* 2084 */           data = percentage + "";
/*      */         }
/* 2086 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2088 */       if (toReturn.length() > 0) {
/* 2089 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2093 */       e.printStackTrace();
/*      */     }
/* 2095 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2101 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2102 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2103 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2104 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2105 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2106 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2107 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2108 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2109 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2112 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2113 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2114 */       splitvalues[0] = multiplecondition.toString();
/* 2115 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2118 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2123 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2124 */     if (thresholdType != 3) {
/* 2125 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2126 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2127 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2128 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2129 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2130 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2132 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2133 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2134 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2135 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2136 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2137 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2139 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2140 */     if (updateSelected != null) {
/* 2141 */       updateSelected[0] = "selected";
/*      */     }
/* 2143 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2148 */       StringBuffer toreturn = new StringBuffer("");
/* 2149 */       if (commaSeparatedMsgId != null) {
/* 2150 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2151 */         int count = 0;
/* 2152 */         while (msgids.hasMoreTokens()) {
/* 2153 */           String id = msgids.nextToken();
/* 2154 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2155 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2156 */           count++;
/* 2157 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2158 */             if (toreturn.length() == 0) {
/* 2159 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2161 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2162 */             if (!image.trim().equals("")) {
/* 2163 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2165 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2166 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2169 */         if (toreturn.length() > 0) {
/* 2170 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2174 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2177 */       e.printStackTrace(); }
/* 2178 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2184 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2190 */   private static Map<String, Long> _jspx_dependants = new HashMap(8);
/* 2191 */   static { _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2194 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2195 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/* 2196 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2197 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/* 2198 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2227 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2231 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2253 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2257 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2259 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2260 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2261 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2262 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2263 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2268 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2269 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2270 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2271 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2272 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2273 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2274 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2275 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2276 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2277 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2284 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2287 */     JspWriter out = null;
/* 2288 */     Object page = this;
/* 2289 */     JspWriter _jspx_out = null;
/* 2290 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2294 */       response.setContentType("text/html;charset=UTF-8");
/* 2295 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2297 */       _jspx_page_context = pageContext;
/* 2298 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2299 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2300 */       session = pageContext.getSession();
/* 2301 */       out = pageContext.getOut();
/* 2302 */       _jspx_out = out;
/*      */       
/* 2304 */       out.write("<!DOCTYPE html>\n");
/* 2305 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n");
/*      */       
/* 2307 */       request.setAttribute("HelpKey", "Monitors .Net Details");
/*      */       
/* 2309 */       out.write(10);
/* 2310 */       out.write(10);
/* 2311 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2312 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2313 */       if (wlsGraph == null) {
/* 2314 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2315 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2317 */       out.write("\n\n\n\n\n\n\n\n");
/* 2318 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2320 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2321 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2322 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2324 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2326 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2328 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2330 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2331 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2332 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2333 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2336 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2337 */         String available = null;
/* 2338 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2339 */         out.write(10);
/*      */         
/* 2341 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2342 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2343 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2345 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2347 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2349 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2351 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2352 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2353 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2354 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2357 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2358 */           String unavailable = null;
/* 2359 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2360 */           out.write(10);
/*      */           
/* 2362 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2363 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2364 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2366 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2368 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2370 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2372 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2373 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2374 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2375 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2378 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2379 */             String unmanaged = null;
/* 2380 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2381 */             out.write(10);
/*      */             
/* 2383 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2384 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2385 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2387 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2389 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2391 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2393 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2394 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2395 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2396 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2399 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2400 */               String scheduled = null;
/* 2401 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2402 */               out.write(10);
/*      */               
/* 2404 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2405 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2406 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2408 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2410 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2412 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2414 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2415 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2416 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2417 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2420 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2421 */                 String critical = null;
/* 2422 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2423 */                 out.write(10);
/*      */                 
/* 2425 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2426 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2427 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2429 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2431 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2433 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2435 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2436 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2437 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2438 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2441 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2442 */                   String clear = null;
/* 2443 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2444 */                   out.write(10);
/*      */                   
/* 2446 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2447 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2448 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2450 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2452 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2454 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2456 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2457 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2458 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2459 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2462 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2463 */                     String warning = null;
/* 2464 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2465 */                     out.write(10);
/* 2466 */                     out.write(10);
/*      */                     
/* 2468 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2469 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2471 */                     out.write(10);
/* 2472 */                     out.write(10);
/* 2473 */                     out.write(10);
/* 2474 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */                     
/* 2476 */                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2477 */                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2478 */                     _jspx_th_c_005fif_005f0.setParent(null);
/*      */                     
/* 2480 */                     _jspx_th_c_005fif_005f0.setTest("${DotNetAgentAvailable == 'true'}");
/* 2481 */                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2482 */                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                       for (;;) {
/* 2484 */                         out.write(10);
/*      */                         
/* 2486 */                         request.setAttribute("hideLeftArea", "true");
/*      */                         
/* 2488 */                         out.write(10);
/* 2489 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2490 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2494 */                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2495 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                     }
/*      */                     else {
/* 2498 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2499 */                       out.write("\n<script type=\"text/javascript\">\n\nfunction showHide(tab,resourceId, parentId, insightParam)\n{\n\tif(tab==\"OverviewTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"customreplink-right\").className = \"tbSelected_Right\";\n\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbUnSelected_Middle\";\n");
/* 2500 */                       out.write("\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbUnSelected_Right\";\n\t\tjavascript:showDiv('OverviewTab');\n\t\tjavascript:hideDiv('insight_OverviewTab');\n\t\tjavascript:hideDiv('insight_transactionTab');\n\t   \tjavascript:hideDiv('insight_databaseTab');\n\t\tjavascript:hideDiv('insight_tracesTab');\n\t\tif(typeof insightParam == 'undefined')\n\t\t{\n\t\t\tvar url = document.location.href;\n\t\t\tvar pos = url.indexOf(\"#\");\n\t\t\tvar finalUrl = url.substring(0, pos);\n\t\t\tlocation.href = finalUrl;\t\n\t\t}\n\t}\n\telse if(tab==\"insight_OverviewTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n");
/* 2501 */                       out.write("\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbUnSelected_Right\";\n");
/* 2502 */                       out.write("\t\tjavascript:hideDiv('OverviewTab');\n\t\tjavascript:showDiv('insight_OverviewTab');\n\t\tjavascript:hideDiv('insight_transactionTab');\n\t   \tjavascript:hideDiv('insight_databaseTab');\n\t\tjavascript:hideDiv('insight_tracesTab');\n\t}\n\telse if(tab==\"insight_transactionTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbSelected_Right\";\n");
/* 2503 */                       out.write("\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbUnSelected_Right\";\n\t\t//document.location.hash = insightParam;\n\t\tjavascript:hideDiv('OverviewTab');\n\t\tjavascript:hideDiv('insight_OverviewTab');\n\t\tjavascript:showDiv('insight_transactionTab');\n\t   \tjavascript:hideDiv('insight_databaseTab');\n\t\tjavascript:hideDiv('insight_tracesTab');\n\t}\n\telse if(tab==\"insight_databaseTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n");
/* 2504 */                       out.write("\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbUnSelected_Right\";\n");
/* 2505 */                       out.write("\t\tjavascript:hideDiv('OverviewTab');\n\t\tjavascript:hideDiv('insight_OverviewTab');\n\t\tjavascript:hideDiv('insight_transactionTab');\n\t   \tjavascript:showDiv('insight_databaseTab');\n\t\tjavascript:hideDiv('insight_tracesTab');\n\t}\n\telse if(tab==\"insight_tracesTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbUnSelected_Right\";\n");
/* 2506 */                       out.write("\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbSelected_Right\";\n\t\tjavascript:hideDiv('OverviewTab');\n\t\tjavascript:hideDiv('insight_OverviewTab');\n\t\tjavascript:hideDiv('insight_transactionTab');\n\t   \tjavascript:hideDiv('insight_databaseTab');\n\t\tjavascript:showDiv('insight_tracesTab');\n\t}\n\tif(insightParam != null && insightParam != '')\n\t{\n\t\tdocument.location.hash = insightParam;\n\t}\n}\n\nfunction showDiv(divname)\n{\n\tvar na = divname.split(\"$\");\n\tfor(i=0;i<na.length;i++)\n\t{\n\t\tvar oDiv = document.getElementById(na[i]);\n\t\tif(oDiv)\n\t\t{\n\t\t\toDiv.style.display = \"block\";\n\t\t}\n\t}\n}\n\nfunction hideDiv(divname)\n");
/* 2507 */                       out.write("{\n\tvar na = divname.split(\"$\");\n\tfor(i=0;i<na.length;i++)\n\t{\n\t\tvar oDiv = document.getElementById(na[i]);\n\t\tif(oDiv)\n\t\t{\n\t\t\toDiv.style.display = \"none\";\n\t\t}\n\t}\n} \n</script> \n\n");
/*      */                       
/*      */ 
/*      */ 
/* 2511 */                       String name = null;
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 2516 */                       name = (String)request.getAttribute("name");
/* 2517 */                       String haid = null;
/*      */                       
/* 2519 */                       String resourceid = (String)request.getAttribute("resourceid");
/* 2520 */                       haid = (String)request.getAttribute("haid");
/* 2521 */                       String details = request.getParameter("details");
/*      */                       
/* 2523 */                       String showdata = (String)request.getAttribute("showdata");
/*      */                       
/* 2525 */                       if (details == null)
/*      */                       {
/* 2527 */                         details = "Availability";
/*      */                       }
/*      */                       
/* 2530 */                       String displayname = null;
/* 2531 */                       ArrayList attribIDs = new ArrayList();
/* 2532 */                       ArrayList resIDs = new ArrayList();
/* 2533 */                       for (int i = 3301; i <= 3352; i++)
/*      */                       {
/* 2535 */                         attribIDs.add("" + i);
/*      */                       }
/*      */                       
/* 2538 */                       out.write(10);
/* 2539 */                       out.write(10);
/*      */                       
/* 2541 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2542 */                       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2543 */                       _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                       
/* 2545 */                       _jspx_th_logic_005fnotEmpty_005f0.setName("dbdetails");
/* 2546 */                       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2547 */                       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                         for (;;) {
/* 2549 */                           out.write(10);
/*      */                           
/* 2551 */                           Hashtable dbinfo1 = (Hashtable)request.getAttribute("dbdetails");
/*      */                           
/* 2553 */                           for (Enumeration e1 = dbinfo1.keys(); e1.hasMoreElements();)
/*      */                           {
/* 2555 */                             String dbname = (String)e1.nextElement();
/* 2556 */                             Properties dbs = (Properties)dbinfo1.get(dbname);
/* 2557 */                             resIDs.add(dbs.getProperty("DBID"));
/*      */                           }
/*      */                           
/*      */ 
/* 2561 */                           out.write(10);
/* 2562 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2563 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2567 */                       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2568 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                       }
/*      */                       else {
/* 2571 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2572 */                         out.write(10);
/*      */                         
/* 2574 */                         resIDs.add(resourceid);
/* 2575 */                         Properties alert = getStatus(resIDs, attribIDs);
/* 2576 */                         HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */                         
/* 2578 */                         if (request.getAttribute("displayname") == null)
/*      */                         {
/* 2580 */                           displayname = request.getParameter("resourcename");
/*      */                         }
/*      */                         else
/*      */                         {
/* 2584 */                           displayname = (String)request.getAttribute("displayname");
/*      */                         }
/* 2586 */                         String redirect = null;
/* 2587 */                         if (details.equals("Availability"))
/*      */                         {
/* 2589 */                           redirect = "/DotNet.do?name=" + name + "&haid=" + haid + "&appName=" + request.getAttribute("appName") + "&resourceid=" + resourceid + "&details=" + details + "&resourcename=" + displayname + "";
/*      */                         }
/* 2591 */                         else if (details.equals("DB"))
/*      */                         {
/* 2593 */                           redirect = "/DotNet.do?name=" + name + "&haid=" + haid + "&appName=" + request.getAttribute("appName") + "&resourceid=" + resourceid + "&details=DB&dbid=" + request.getParameter("dbid") + "&resourcename=" + displayname + "&dbname=" + request.getParameter("dbname");
/*      */                         }
/* 2595 */                         String encodeurl = java.net.URLEncoder.encode(redirect);
/* 2596 */                         request.setAttribute("alert", alert);
/* 2597 */                         request.setAttribute("encodeurl", encodeurl);
/* 2598 */                         request.setAttribute("configured", "true");
/* 2599 */                         wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2600 */                         Properties data = (Properties)request.getAttribute("data");
/*      */                         
/* 2602 */                         out.write(10);
/* 2603 */                         if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */                           return;
/* 2605 */                         out.write(10);
/*      */                         
/* 2607 */                         InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2608 */                         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2609 */                         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                         
/* 2611 */                         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2612 */                         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2613 */                         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                           for (;;) {
/* 2615 */                             out.write(10);
/* 2616 */                             if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                               return;
/* 2618 */                             out.write(10);
/* 2619 */                             if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                               return;
/* 2621 */                             out.write(10);
/* 2622 */                             if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                               return;
/* 2624 */                             out.write(10);
/* 2625 */                             if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                               return;
/* 2627 */                             out.write(10);
/*      */                             
/* 2629 */                             PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2630 */                             _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2631 */                             _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                             
/* 2633 */                             _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                             
/* 2635 */                             _jspx_th_tiles_005fput_005f4.setType("string");
/* 2636 */                             int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2637 */                             if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2638 */                               if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2639 */                                 out = _jspx_page_context.pushBody();
/* 2640 */                                 _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 2641 */                                 _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2644 */                                 out.write(10);
/* 2645 */                                 out.write(10);
/*      */                                 
/* 2647 */                                 Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2648 */                                 String aid = request.getParameter("haid");
/* 2649 */                                 String haName = null;
/* 2650 */                                 if (aid != null)
/*      */                                 {
/* 2652 */                                   haName = (String)ht.get(aid);
/*      */                                 }
/*      */                                 
/*      */ 
/* 2656 */                                 out.write("\n\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n");
/*      */                                 
/* 2658 */                                 if (details.equals("DB"))
/*      */                                 {
/*      */ 
/*      */ 
/* 2662 */                                   out.write("\n\n\t\t\t");
/*      */                                   
/* 2664 */                                   IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2665 */                                   _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2666 */                                   _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                   
/* 2668 */                                   _jspx_th_c_005fif_005f3.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2669 */                                   int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2670 */                                   if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                     for (;;) {
/* 2672 */                                       out.write("\n\t\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2673 */                                       out.print(BreadcrumbUtil.getHomePage(request));
/* 2674 */                                       out.write(" &gt; ");
/* 2675 */                                       out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2676 */                                       out.write(" &gt; <span class=\"bcactive\"> ");
/* 2677 */                                       out.print(displayname);
/* 2678 */                                       out.write(32);
/* 2679 */                                       out.write(58);
/* 2680 */                                       out.write(32);
/* 2681 */                                       out.print(request.getParameter("dbname"));
/* 2682 */                                       out.write(" </span></td>\n\t\t\t");
/* 2683 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2684 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2688 */                                   if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2689 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                   }
/*      */                                   
/* 2692 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2693 */                                   out.write("\n\t\t\t");
/*      */                                   
/* 2695 */                                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2696 */                                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2697 */                                   _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                   
/* 2699 */                                   _jspx_th_c_005fif_005f4.setTest("${!empty param.haid && (!empty invalidhaid)}");
/* 2700 */                                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2701 */                                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                     for (;;) {
/* 2703 */                                       out.write("\n\t\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2704 */                                       out.print(BreadcrumbUtil.getMonitorsPage());
/* 2705 */                                       out.write(" &gt; ");
/* 2706 */                                       out.print(BreadcrumbUtil.getMonitorResourceTypes(".Net"));
/* 2707 */                                       out.write(" &gt; <a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2708 */                                       out.print(resourceid);
/* 2709 */                                       out.write("&haid=");
/* 2710 */                                       out.print(haid);
/* 2711 */                                       out.write("\" class=\"staticlinks\"> ");
/* 2712 */                                       out.print(displayname);
/* 2713 */                                       out.write(" </a> : <span class=\"bcactive\"> ");
/* 2714 */                                       out.print(request.getParameter("dbname"));
/* 2715 */                                       out.write("</span></td>\n\t\t\t");
/* 2716 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2717 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2721 */                                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2722 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                   }
/*      */                                   
/* 2725 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2726 */                                   out.write(10);
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 2732 */                                   out.write(10);
/* 2733 */                                   out.write(9);
/*      */                                   
/* 2735 */                                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2736 */                                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2737 */                                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                   
/* 2739 */                                   _jspx_th_c_005fif_005f5.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2740 */                                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2741 */                                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                     for (;;) {
/* 2743 */                                       out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2744 */                                       out.print(BreadcrumbUtil.getHomePage(request));
/* 2745 */                                       out.write(" &gt; ");
/* 2746 */                                       out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2747 */                                       out.write(" &gt; <span class=\"bcactive\"> ");
/* 2748 */                                       out.print(displayname);
/* 2749 */                                       out.write(32);
/* 2750 */                                       out.write(58);
/* 2751 */                                       out.write(32);
/* 2752 */                                       out.print(FormatUtil.getString("am.webclient.dotnet.snapshotview"));
/* 2753 */                                       out.write(" </span></td>\n\t");
/* 2754 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2755 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2759 */                                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2760 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                   }
/*      */                                   
/* 2763 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2764 */                                   out.write(10);
/* 2765 */                                   out.write(9);
/*      */                                   
/* 2767 */                                   IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2768 */                                   _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2769 */                                   _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                   
/* 2771 */                                   _jspx_th_c_005fif_005f6.setTest("${!empty param.haid && (!empty invalidhaid)}");
/* 2772 */                                   int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2773 */                                   if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                     for (;;) {
/* 2775 */                                       out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2776 */                                       out.print(BreadcrumbUtil.getMonitorsPage());
/* 2777 */                                       out.write(" &gt; ");
/* 2778 */                                       out.print(BreadcrumbUtil.getMonitorResourceTypes(".Net"));
/* 2779 */                                       out.write(" &gt; <span class=\"bcactive\"> ");
/* 2780 */                                       out.print(displayname);
/* 2781 */                                       out.write(32);
/* 2782 */                                       out.write(58);
/* 2783 */                                       out.write(32);
/* 2784 */                                       out.print(FormatUtil.getString("am.webclient.dotnet.snapshotview"));
/* 2785 */                                       out.write("</span></td>\n\t");
/* 2786 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2787 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2791 */                                   if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2792 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                   }
/*      */                                   
/* 2795 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2796 */                                   out.write(10);
/* 2797 */                                   out.write(10);
/*      */                                 }
/*      */                                 
/*      */ 
/* 2801 */                                 out.write("\n\t\t\t</tr>\n</table>\n\t\t");
/*      */                                 
/* 2803 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2804 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2805 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 2807 */                                 _jspx_th_c_005fif_005f7.setTest("${DotNetAgentAvailable == 'true'}");
/* 2808 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2809 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 2811 */                                     out.write("\n\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\">\n\t\t\t<tr class=\"tabBtmLine\">\n   \t\t \t\t<td>\n           \t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" id=\"InnerTab\">\n                 \t\t<tr>\n                   \t\t\t<td width=\"17\"></td>\n\t\t    \t\t\t\t<td>\n\t\t    \t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t<tr>\n                           \t\t\t\t<td id=\"customreplink-left\" class=\"tbSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t<td id=\"customreplink\" class=\"tbSelected_Middle\">\n\t\t\t\t   \t\t\t\t\t\t\t<a href=\"javascript:showHide('OverviewTab')\">&nbsp;<span class=\"tabLink\">");
/* 2812 */                                     out.print(FormatUtil.getString("Overview"));
/* 2813 */                                     out.write("</span></a>\n                           \t\t\t\t</td>\n                           \t\t\t\t<td id=\"customreplink-right\" class=\"tbSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t</tr>\n                     \t\t\t</table>\n                   \t\t\t</td>\n\t\t\t\t\t\t<td>\n                   \t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t\t<tr>\n                           \t\t\t\t\t<td id=\"insightOverviewreplink-left\" class=\"tbUnSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t\t<td id=\"insightOverviewreplink\" class=\"tbUnSelected_Middle\">\n\t\t\t\t   \t\t\t\t\t<a href=\"javascript:showHide('insight_OverviewTab','");
/* 2814 */                                     if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2816 */                                     out.write("', '");
/* 2817 */                                     if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2819 */                                     out.write("','I/O/");
/* 2820 */                                     if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2822 */                                     out.write("/TW=H')\">&nbsp;<span class=\"tabLink\">");
/* 2823 */                                     out.print(FormatUtil.getString("am.webclient.dotnet.apminsight.apdexOverview"));
/* 2824 */                                     out.write("</span></a></td>\n                           \t\t\t\t\t<td id=\"insightOverviewreplink-right\" class=\"tbUnSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t\t</tr>\n                     \t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td>\n                   \t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t\t<tr>\n                           \t\t\t\t\t<td id=\"insightTransactionreplink-left\" class=\"tbUnSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t\t<td id=\"insightTransactionreplink\" class=\"tbUnSelected_Middle\">\n\t\t\t\t   \t\t\t\t\t<a href=\"javascript:showHide('insight_transactionTab','");
/* 2825 */                                     if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2827 */                                     out.write("', '");
/* 2828 */                                     if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2830 */                                     out.write("','I/WT-G-RT/");
/* 2831 */                                     if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2833 */                                     out.write("/TW=H')\">&nbsp;<span class=\"tabLink\">");
/* 2834 */                                     out.print(FormatUtil.getString("am.webclient.dotnet.apminsight.webtransaction"));
/* 2835 */                                     out.write("</span></a></td>\n                           \t\t\t\t\t<td id=\"insightTransactionreplink-right\" class=\"tbUnSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t\t</tr>\n                     \t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td>\n                   \t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t\t<tr>\n                           \t\t\t\t\t<td id=\"insightDBreplink-left\" class=\"tbUnSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t\t<td id=\"insightDBreplink\" class=\"tbUnSelected_Middle\">\n\t\t\t\t   \t\t\t\t\t<a href=\"javascript:showHide('insight_databaseTab','");
/* 2836 */                                     if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2838 */                                     out.write("', '");
/* 2839 */                                     if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2841 */                                     out.write("','I/DB-G-RT/");
/* 2842 */                                     if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2844 */                                     out.write("/TW=H')\">&nbsp;<span class=\"tabLink\">");
/* 2845 */                                     out.print(FormatUtil.getString("am.webclient.dotnet.apminsight.database"));
/* 2846 */                                     out.write("</span></a></td>\n                           \t\t\t\t\t<td id=\"insightDBreplink-right\" class=\"tbUnSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t\t</tr>\n                     \t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td>\n                   \t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t\t<tr>\n                           \t\t\t\t\t<td id=\"insightTracereplink-left\" class=\"tbUnSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t\t<td id=\"insightTracereplink\" class=\"tbUnSelected_Middle\">\n\t\t\t\t   \t\t\t\t\t<a href=\"javascript:showHide('insight_tracesTab','");
/* 2847 */                                     if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2849 */                                     out.write("', '");
/* 2850 */                                     if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2852 */                                     out.write("','I/TR/");
/* 2853 */                                     if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 2855 */                                     out.write("/TW=H')\">&nbsp;<span class=\"tabLink\">");
/* 2856 */                                     out.print(FormatUtil.getString("am.webclient.dotnet.apminsight.traces"));
/* 2857 */                                     out.write("</span></a></td>\n                           \t\t\t\t\t<td id=\"insightTracereplink-right\" class=\"tbUnSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t\t</tr>\n                     \t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t  \t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t\t");
/* 2858 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2859 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2863 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2864 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 2867 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2868 */                                 out.write("\n\t\t<div id=\"OverviewTab\">\n\n");
/* 2869 */                                 if (_jspx_meth_c_005fif_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 2871 */                                 out.write(10);
/* 2872 */                                 if (_jspx_meth_c_005fif_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 2874 */                                 out.write(10);
/* 2875 */                                 out.write(10);
/*      */                                 
/* 2877 */                                 ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2878 */                                 _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2879 */                                 _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/* 2880 */                                 int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2881 */                                 if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                   for (;;) {
/* 2883 */                                     out.write(10);
/*      */                                     
/* 2885 */                                     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2886 */                                     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2887 */                                     _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                     
/* 2889 */                                     _jspx_th_c_005fwhen_005f0.setTest("${ param.alert!='true' && param.all!='true' }");
/* 2890 */                                     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2891 */                                     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                       for (;;) {
/* 2893 */                                         out.write(10);
/*      */                                         
/*      */ 
/* 2896 */                                         if (details.equals("Availability"))
/*      */                                         {
/*      */ 
/* 2899 */                                           out.write("\n<table width=\"99%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n  <tr>\n    <td valign=\"top\">\n      <table width=\"96%\"  border=\"0\" cellpadding=\"1\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n      <td  colspan=\"2\" class=\"tableheadingbborder\">");
/* 2900 */                                           out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2901 */                                           out.write(" <span class=\"resourceheading\">\n    \t </span></td>\n\t\t </tr>\n        \t<tr>\n\t\t   <td class=\"monitorinfoodd\">");
/* 2902 */                                           out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2903 */                                           out.write(" </td>\n\t\t   <td class=\"monitorinfoodd\">");
/* 2904 */                                           out.print(getTrimmedText(displayname, 30));
/* 2905 */                                           out.write("</td>\n\t\t   </tr>\n\t\t   ");
/* 2906 */                                           out.write("<!--$Id$-->\n");
/*      */                                           
/* 2908 */                                           String hostName = "localhost";
/*      */                                           try {
/* 2910 */                                             hostName = InetAddress.getLocalHost().getHostName();
/*      */                                           } catch (Exception ex) {
/* 2912 */                                             ex.printStackTrace();
/*      */                                           }
/* 2914 */                                           String portNumber = System.getProperty("webserver.port");
/* 2915 */                                           String styleClass = "monitorinfoodd";
/* 2916 */                                           if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2917 */                                             styleClass = "whitegrayborder-conf-mon";
/*      */                                           }
/*      */                                           
/* 2920 */                                           out.write(10);
/*      */                                           
/* 2922 */                                           PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2923 */                                           _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2924 */                                           _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                           
/* 2926 */                                           _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2927 */                                           int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2928 */                                           if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                             for (;;) {
/* 2930 */                                               out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2931 */                                               out.print(styleClass);
/* 2932 */                                               out.write(34);
/* 2933 */                                               out.write(62);
/* 2934 */                                               out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2935 */                                               out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2936 */                                               out.print(styleClass);
/* 2937 */                                               out.write(34);
/* 2938 */                                               out.write(62);
/* 2939 */                                               out.print(hostName);
/* 2940 */                                               out.write(95);
/* 2941 */                                               out.print(portNumber);
/* 2942 */                                               out.write("</td>\n</tr>\n");
/* 2943 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2944 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2948 */                                           if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2949 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                           }
/*      */                                           
/* 2952 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2953 */                                           out.write(10);
/* 2954 */                                           out.write("\n\t\t   ");
/*      */                                           
/* 2956 */                                           String healthStatus = alert.getProperty(resourceid + "#" + "3302");
/*      */                                           
/* 2958 */                                           out.write("\n\t\t   <tr>\n\t\t   <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2959 */                                           out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2960 */                                           out.write("</td>\n\t\t   <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2961 */                                           out.print(resourceid);
/* 2962 */                                           out.write("&attributeid=3302')\">");
/* 2963 */                                           out.print(getSeverityImageForHealth(healthStatus));
/* 2964 */                                           out.write("</a>\n\t\t   ");
/* 2965 */                                           out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "3302" + "#" + "MESSAGE"), "3302", alert.getProperty(resourceid + "#" + "3302"), resourceid));
/* 2966 */                                           out.write("\n\t\t   ");
/* 2967 */                                           if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "3302") != 0) {
/* 2968 */                                             out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2969 */                                             out.print(resourceid + "_3302");
/* 2970 */                                             out.write("&monitortype=.Net')\">");
/* 2971 */                                             out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2972 */                                             out.write("</a></span>\n            ");
/*      */                                           }
/* 2974 */                                           out.write("\n\t\t   </td>\n\t\t   </tr>\n\t\t   <tr>\n\t\t   <td class=\"monitorinfoodd\">");
/* 2975 */                                           out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2976 */                                           out.write(" </td>\n\t\t   <td class=\"monitorinfoodd\">");
/* 2977 */                                           out.print(FormatUtil.getString("am.webclient.dotnet.microsoft"));
/* 2978 */                                           out.write("</td>\n\t\t   </tr>\n\t\t\t\t");
/*      */                                           
/* 2980 */                                           EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2981 */                                           _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2982 */                                           _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                           
/* 2984 */                                           _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2985 */                                           int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2986 */                                           if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                                             for (;;) {
/* 2988 */                                               out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2989 */                                               out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2990 */                                               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">-&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2991 */                                               out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2992 */                                               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2993 */                                               out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2994 */                                               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2995 */                                               out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2996 */                                               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2997 */                                               int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2998 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3002 */                                           if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3003 */                                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                                           }
/*      */                                           
/* 3006 */                                           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3007 */                                           out.write("\n\t\t\t\t");
/*      */                                           
/* 3009 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3010 */                                           _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3011 */                                           _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                           
/* 3013 */                                           _jspx_th_logic_005fnotEmpty_005f1.setName("systeminfo");
/* 3014 */                                           int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3015 */                                           if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                             for (;;) {
/* 3017 */                                               out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 3018 */                                               out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3019 */                                               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 3020 */                                               out.print(systeminfo.get("host_resid"));
/* 3021 */                                               out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 3022 */                                               out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 30));
/* 3023 */                                               out.write("&nbsp;(");
/* 3024 */                                               out.print(systeminfo.get("HOSTIP"));
/* 3025 */                                               out.write(")</a></td>\n\t\t\t\t<!--<td class=\"monitorinfoodd\">");
/* 3026 */                                               out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 30));
/* 3027 */                                               out.write("&nbsp;(");
/* 3028 */                                               out.print(systeminfo.get("HOSTIP"));
/* 3029 */                                               out.write(")</td>-->\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 3030 */                                               out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3031 */                                               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 3032 */                                               out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 3033 */                                               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                                               
/* 3035 */                                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3036 */                                               _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3037 */                                               _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                               
/* 3039 */                                               _jspx_th_logic_005fnotEmpty_005f2.setName("recent5Alarms");
/* 3040 */                                               int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3041 */                                               if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                                 for (;;) {
/* 3043 */                                                   out.write("\n\t\t\t\t");
/*      */                                                   
/* 3045 */                                                   ArrayList recent = (ArrayList)((ArrayList)request.getAttribute("recent5Alarms")).get(0);
/*      */                                                   
/* 3047 */                                                   out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 3048 */                                                   out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 3049 */                                                   out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\"><a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 3050 */                                                   out.print(recent.get(2));
/* 3051 */                                                   out.write("&source=");
/* 3052 */                                                   out.print(recent.get(4));
/* 3053 */                                                   out.write("&category=");
/* 3054 */                                                   out.print(recent.get(0));
/* 3055 */                                                   out.write("&redirectto=");
/* 3056 */                                                   out.print(encodeurl);
/* 3057 */                                                   out.write("\"  class=\"resourcename\">");
/* 3058 */                                                   out.print(getTruncatedAlertMessage((String)recent.get(3)));
/* 3059 */                                                   out.write("</a></td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 3060 */                                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 3061 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3065 */                                               if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 3066 */                                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                                               }
/*      */                                               
/* 3069 */                                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3070 */                                               out.write("\n\t\t\t\t");
/*      */                                               
/* 3072 */                                               EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3073 */                                               _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3074 */                                               _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                               
/* 3076 */                                               _jspx_th_logic_005fempty_005f1.setName("recent5Alarms");
/* 3077 */                                               int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3078 */                                               if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                                                 for (;;) {
/* 3080 */                                                   out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 3081 */                                                   out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 3082 */                                                   out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 3083 */                                                   int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3084 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3088 */                                               if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3089 */                                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                                               }
/*      */                                               
/* 3092 */                                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3093 */                                               out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 3094 */                                               out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3095 */                                               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 3096 */                                               out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3097 */                                               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 3098 */                                               out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3099 */                                               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 3100 */                                               out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3101 */                                               out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t");
/* 3102 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3103 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3107 */                                           if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3108 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                           }
/*      */                                           
/* 3111 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3112 */                                           out.write("\n\t\t\t\t");
/* 3113 */                                           out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 3114 */                                           out.write("\n\t{\n\t\t");
/* 3115 */                                           if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3117 */                                           out.write(10);
/* 3118 */                                           out.write(9);
/* 3119 */                                           out.write(9);
/* 3120 */                                           if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3122 */                                           out.write("\n\t\tgetCustomFields('");
/* 3123 */                                           if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3125 */                                           out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 3126 */                                           out.write("\n\t}\n\n});\n</script>\n");
/* 3127 */                                           if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3129 */                                           out.write(10);
/* 3130 */                                           out.write(10);
/* 3131 */                                           if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3133 */                                           out.write(10);
/* 3134 */                                           out.write(10);
/* 3135 */                                           if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3137 */                                           out.write(10);
/* 3138 */                                           if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3140 */                                           out.write(10);
/* 3141 */                                           out.write(10);
/* 3142 */                                           out.write(10);
/* 3143 */                                           if (_jspx_meth_c_005fif_005f14(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3145 */                                           out.write(10);
/* 3146 */                                           out.write(10);
/* 3147 */                                           out.write(10);
/* 3148 */                                           if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3150 */                                           out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 3151 */                                           if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3153 */                                           out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 3154 */                                           if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3156 */                                           out.write("\" onclick=\"getCustomFields('");
/* 3157 */                                           if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3159 */                                           out.write(39);
/* 3160 */                                           out.write(44);
/* 3161 */                                           out.write(39);
/* 3162 */                                           if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3164 */                                           out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 3165 */                                           out.write("\n</td>\n</tr>\n\n\n");
/* 3166 */                                           out.write("\n        </table>\n        ");
/*      */                                           
/* 3168 */                                           PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3169 */                                           _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3170 */                                           _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                           
/* 3172 */                                           _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3173 */                                           int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3174 */                                           if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                             for (;;) {
/* 3176 */                                               out.write(10);
/* 3177 */                                               out.write(9);
/* 3178 */                                               out.write(9);
/*      */                                               
/* 3180 */                                               IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3181 */                                               _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3182 */                                               _jspx_th_c_005fif_005f16.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                                               
/* 3184 */                                               _jspx_th_c_005fif_005f16.setTest("${showdata=='1'}");
/* 3185 */                                               int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3186 */                                               if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                                                 for (;;) {
/* 3188 */                                                   out.write("\n<div align=\"center\"><a style=cursor:pointer;><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"javascript:toggleDiv('edit')\">\n\n<tr>\n              <td>&nbsp;</td>\n            </tr>\n            <tr>\n              <td><table width=\"75%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"getmoredatatable\">\n                  <tr>\n                    <td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img src=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\" border=\"0\" vspace=\"2\" hspace=\"5\"></td>\n                    <td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 3189 */                                                   out.print(FormatUtil.getString("am.webclient.configureimage.DotNet.text"));
/* 3190 */                                                   out.write("</td>\n                  </tr>\n                </table></td>\n            </tr>\n      </table></a></div>\n\t\t\t\t");
/* 3191 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3192 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3196 */                                               if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3197 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                                               }
/*      */                                               
/* 3200 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3201 */                                               out.write("\n\t\t\t\t");
/* 3202 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3203 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3207 */                                           if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3208 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                           }
/*      */                                           
/* 3211 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3212 */                                           out.write("\n        </td>\n    <td width=\"40%\" height=\"31\" class=\"bodytextbold\" valign=\"top\" >\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n        <tbody>\n        <tr>\n        <td colspan=\"4\" height=\"31\" class=\"tableheadingbborder\">\n      ");
/* 3213 */                                           out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3214 */                                           out.write(" <a name=\"Availability\" id=\"Availability\"></a></td>\n      </tr>\n\n<tr> <td colspan=\"4\">      <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n          \t<tr>\n          \t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3215 */                                           if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3217 */                                           out.write("&period=1&resourcename=");
/* 3218 */                                           if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3220 */                                           out.write("')\">\n      <img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3221 */                                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3222 */                                           out.write("\"></a></td>\n            <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3223 */                                           if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3225 */                                           out.write("&period=2&resourcename=");
/* 3226 */                                           if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 3228 */                                           out.write("')\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3229 */                                           out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3230 */                                           out.write("\"></a></td>\n      </tr>\n</table>\n</td>\n</tr>\n\n\n                <tr>\n\n                <td colspan=\"4\" align=\"center\">\n                 ");
/*      */                                           
/* 3232 */                                           AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3233 */                                           _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3234 */                                           _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                           
/* 3236 */                                           _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                                           
/* 3238 */                                           _jspx_th_awolf_005fpiechart_005f0.setWidth("280");
/*      */                                           
/* 3240 */                                           _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                                           
/* 3242 */                                           _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                                           
/* 3244 */                                           _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                                           
/* 3246 */                                           _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                                           
/* 3248 */                                           _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3249 */                                           int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3250 */                                           if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3251 */                                             if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3252 */                                               out = _jspx_page_context.pushBody();
/* 3253 */                                               _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3254 */                                               _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3257 */                                               out.write("\n\t\t                   ");
/*      */                                               
/* 3259 */                                               Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3260 */                                               _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3261 */                                               _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                               
/* 3263 */                                               _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3264 */                                               int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3265 */                                               if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3266 */                                                 if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3267 */                                                   out = _jspx_page_context.pushBody();
/* 3268 */                                                   _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3269 */                                                   _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3272 */                                                   out.write("\n\t\t                \t\t");
/*      */                                                   
/* 3274 */                                                   AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3275 */                                                   _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3276 */                                                   _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                                   
/* 3278 */                                                   _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                                   
/* 3280 */                                                   _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3281 */                                                   int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3282 */                                                   if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3283 */                                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                                   }
/*      */                                                   
/* 3286 */                                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3287 */                                                   out.write("\n\t\t                \t\t");
/*      */                                                   
/* 3289 */                                                   AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3290 */                                                   _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3291 */                                                   _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                                   
/* 3293 */                                                   _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                                   
/* 3295 */                                                   _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3296 */                                                   int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3297 */                                                   if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3298 */                                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                                   }
/*      */                                                   
/* 3301 */                                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3302 */                                                   out.write("\n\t\t                \t");
/* 3303 */                                                   int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3304 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3307 */                                                 if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3308 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3311 */                                               if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3312 */                                                 this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                               }
/*      */                                               
/* 3315 */                                               this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3316 */                                               out.write("\n    \t\t\t  ");
/* 3317 */                                               int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3318 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3321 */                                             if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3322 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3325 */                                           if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3326 */                                             this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                                           }
/*      */                                           
/* 3329 */                                           this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3330 */                                           out.write("\n                </td>\n        </tr>\n\t\t<tr>\n\t\t\t\t<td  width=\"49%\" class=\"yellowgrayborder\" colspan=\"2\">");
/* 3331 */                                           out.print(FormatUtil.getString("am.webclient.mssqldetails.currnetstatus"));
/* 3332 */                                           out.write("\n\t\t\t\t");
/*      */                                           
/* 3334 */                                           String avastatus = alert.getProperty(resourceid + "#" + "3301");
/*      */                                           
/* 3336 */                                           out.write("\n\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3337 */                                           out.print(resourceid);
/* 3338 */                                           out.write("&attributeid=3301')\">");
/* 3339 */                                           out.print(getSeverityImageForAvailability(avastatus));
/* 3340 */                                           out.write("</a></td>\n\t\t\t\t            <td width=\"50%\"  class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3341 */                                           out.print(resourceid);
/* 3342 */                                           out.write("&attributeIDs=3301,3302&attributeToSelect=3301&redirectto=");
/* 3343 */                                           out.print(encodeurl);
/* 3344 */                                           out.write("\" class=\"links\">");
/* 3345 */                                           out.print(ALERTCONFIG_TEXT);
/* 3346 */                                           out.write("</a>&nbsp;</td>\n\t\t</tr>\n\n\n        </tbody>\n      </table></td>\n  </tr>\n  </table>\n\n <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3347 */                                           out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 3348 */                                           out.write("</td></tr></table>\n\n");
/* 3349 */                                           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/DotNetDetails.jsp", out, false);
/* 3350 */                                           out.write("\n\n\n\n");
/*      */ 
/*      */                                         }
/* 3353 */                                         else if (details.equals("DB"))
/*      */                                         {
/*      */ 
/* 3356 */                                           System.out.println("Including the DotNetApps.jsp file");
/*      */                                           
/* 3358 */                                           out.write(10);
/* 3359 */                                           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/DotNetApps.jsp", out, false);
/* 3360 */                                           out.write(10);
/*      */                                         }
/*      */                                         
/*      */ 
/* 3364 */                                         out.write("\n\n\n\n  ");
/* 3365 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3366 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3370 */                                     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3371 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                     }
/*      */                                     
/* 3374 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3375 */                                     out.write(10);
/* 3376 */                                     out.write(32);
/* 3377 */                                     out.write(32);
/*      */                                     
/* 3379 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3380 */                                     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3381 */                                     _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 3382 */                                     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3383 */                                     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                       for (;;) {
/* 3385 */                                         out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n      <td align=\"center\" class=\"bodytextbold11\"><a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3386 */                                         if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                           return;
/* 3388 */                                         if (_jspx_meth_c_005fif_005f17(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                           return;
/* 3390 */                                         out.write("\" class=\"staticlinks\">");
/* 3391 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.gotosnapshot"));
/* 3392 */                                         out.write("</a></td>\n    </tr>\n  </table>\n  ");
/* 3393 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3394 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3398 */                                     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3399 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                     }
/*      */                                     
/* 3402 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3403 */                                     out.write(10);
/* 3404 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3405 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3409 */                                 if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3410 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                 }
/*      */                                 
/* 3413 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3414 */                                 out.write("\n<br>\n");
/* 3415 */                                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 3416 */                                 DialChartSupport dialGraph = null;
/* 3417 */                                 dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 3418 */                                 if (dialGraph == null) {
/* 3419 */                                   dialGraph = new DialChartSupport();
/* 3420 */                                   _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                                 }
/* 3422 */                                 out.write(10);
/*      */                                 
/*      */                                 try
/*      */                                 {
/* 3426 */                                   String hostos = (String)systeminfo.get("HOSTOS");
/* 3427 */                                   String hostname = (String)systeminfo.get("HOSTNAME");
/* 3428 */                                   String hostid = (String)systeminfo.get("host_resid");
/* 3429 */                                   boolean isConf = false;
/* 3430 */                                   if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 3431 */                                     isConf = true;
/*      */                                   }
/* 3433 */                                   com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 3434 */                                   Properties property = new Properties();
/* 3435 */                                   if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                                   {
/* 3437 */                                     property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 3438 */                                     if ((property != null) && (property.size() > 0))
/*      */                                     {
/* 3440 */                                       String cpuid = property.getProperty("cpuid");
/* 3441 */                                       String memid = property.getProperty("memid");
/* 3442 */                                       String diskid = property.getProperty("diskid");
/* 3443 */                                       String cpuvalue = property.getProperty("CPU Utilization");
/* 3444 */                                       String memvalue = property.getProperty("Memory Utilization");
/* 3445 */                                       String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 3446 */                                       String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 3447 */                                       String diskvalue = property.getProperty("Disk Utilization");
/* 3448 */                                       String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                       
/* 3450 */                                       if (!isConf) {
/* 3451 */                                         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 3452 */                                         out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 3453 */                                         out.write(45);
/* 3454 */                                         if (systeminfo.get("host_resid") != null) {
/* 3455 */                                           out.write("<a href=\"showresource.do?resourceid=");
/* 3456 */                                           out.print(hostid);
/* 3457 */                                           out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 3458 */                                           out.print(hostname);
/* 3459 */                                           out.write("</a>");
/* 3460 */                                         } else { out.println(hostname); }
/* 3461 */                                         out.write("</td>\t");
/* 3462 */                                         out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 3463 */                                         out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3464 */                                         out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                         
/*      */ 
/* 3467 */                                         if (cpuvalue != null)
/*      */                                         {
/*      */ 
/* 3470 */                                           dialGraph.setValue(Long.parseLong(cpuvalue));
/* 3471 */                                           out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3472 */                                           out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 3473 */                                           out.write(45);
/* 3474 */                                           out.print(cpuvalue);
/* 3475 */                                           out.write(" %'>\n\n");
/*      */                                           
/* 3477 */                                           DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 3478 */                                           _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 3479 */                                           _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 3481 */                                           _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                           
/* 3483 */                                           _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                           
/* 3485 */                                           _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                           
/* 3487 */                                           _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                           
/* 3489 */                                           _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                           
/* 3491 */                                           _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                           
/* 3493 */                                           _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                           
/* 3495 */                                           _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                           
/* 3497 */                                           _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                           
/* 3499 */                                           _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 3500 */                                           int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 3501 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 3502 */                                             if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 3503 */                                               out = _jspx_page_context.pushBody();
/* 3504 */                                               _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 3505 */                                               _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3508 */                                               out.write(10);
/* 3509 */                                               int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 3510 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3513 */                                             if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 3514 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3517 */                                           if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 3518 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                           }
/*      */                                           
/* 3521 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 3522 */                                           out.write("\n         </td>\n            ");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 3526 */                                           out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 3527 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3528 */                                           out.write(32);
/* 3529 */                                           out.write(62);
/* 3530 */                                           out.write(10);
/* 3531 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3532 */                                           out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                         }
/* 3534 */                                         out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 3535 */                                         if (cpuvalue != null)
/*      */                                         {
/* 3537 */                                           out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3538 */                                           out.print(hostid);
/* 3539 */                                           out.write("&attributeid=");
/* 3540 */                                           out.print(cpuid);
/* 3541 */                                           out.write("&period=-7')\" class='bodytextbold'>");
/* 3542 */                                           out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 3543 */                                           out.write(32);
/* 3544 */                                           out.write(45);
/* 3545 */                                           out.write(32);
/* 3546 */                                           out.print(cpuvalue);
/* 3547 */                                           out.write("</a> %\n");
/*      */                                         }
/* 3549 */                                         out.write("\n  </td>\n       </tr>\n       </table>");
/* 3550 */                                         out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 3551 */                                         out.write("</td>\n      <td width=\"30%\"> ");
/* 3552 */                                         out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3553 */                                         out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                         
/* 3555 */                                         if (memvalue != null)
/*      */                                         {
/*      */ 
/* 3558 */                                           dialGraph.setValue(Long.parseLong(memvalue));
/* 3559 */                                           out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3560 */                                           out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 3561 */                                           out.write(45);
/* 3562 */                                           out.print(memvalue);
/* 3563 */                                           out.write(" %' >\n\n");
/*      */                                           
/* 3565 */                                           DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 3566 */                                           _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 3567 */                                           _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 3569 */                                           _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                           
/* 3571 */                                           _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                           
/* 3573 */                                           _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                           
/* 3575 */                                           _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                           
/* 3577 */                                           _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                           
/* 3579 */                                           _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                           
/* 3581 */                                           _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                           
/* 3583 */                                           _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                           
/* 3585 */                                           _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                           
/* 3587 */                                           _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 3588 */                                           int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 3589 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 3590 */                                             if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 3591 */                                               out = _jspx_page_context.pushBody();
/* 3592 */                                               _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 3593 */                                               _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3596 */                                               out.write(32);
/* 3597 */                                               int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 3598 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3601 */                                             if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 3602 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3605 */                                           if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 3606 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                           }
/*      */                                           
/* 3609 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 3610 */                                           out.write(32);
/* 3611 */                                           out.write("\n            </td>\n            ");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 3615 */                                           out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 3616 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3617 */                                           out.write(" >\n\n");
/* 3618 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3619 */                                           out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                         }
/* 3621 */                                         out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 3622 */                                         if (memvalue != null)
/*      */                                         {
/* 3624 */                                           out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3625 */                                           out.print(hostid);
/* 3626 */                                           out.write("&attributeid=");
/* 3627 */                                           out.print(memid);
/* 3628 */                                           out.write("&period=-7')\" class='bodytextbold'>");
/* 3629 */                                           out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 3630 */                                           out.write(45);
/* 3631 */                                           out.print(memvalue);
/* 3632 */                                           out.write("</a> %\n  ");
/*      */                                         }
/* 3634 */                                         out.write("\n  </td>\n       </tr>\n    </table>");
/* 3635 */                                         out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 3636 */                                         out.write("</td>\n      <td width=\"30%\">");
/* 3637 */                                         out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3638 */                                         out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                         
/*      */ 
/* 3641 */                                         if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                         {
/*      */ 
/*      */ 
/* 3645 */                                           dialGraph.setValue(Long.parseLong(diskvalue));
/* 3646 */                                           out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3647 */                                           out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 3648 */                                           out.write(45);
/* 3649 */                                           out.print(diskvalue);
/* 3650 */                                           out.write("%' >\n");
/*      */                                           
/* 3652 */                                           DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 3653 */                                           _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 3654 */                                           _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 3656 */                                           _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                           
/* 3658 */                                           _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                           
/* 3660 */                                           _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                           
/* 3662 */                                           _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                           
/* 3664 */                                           _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                           
/* 3666 */                                           _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                           
/* 3668 */                                           _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                           
/* 3670 */                                           _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                           
/* 3672 */                                           _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                           
/* 3674 */                                           _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 3675 */                                           int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 3676 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 3677 */                                             if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 3678 */                                               out = _jspx_page_context.pushBody();
/* 3679 */                                               _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 3680 */                                               _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3683 */                                               out.write(32);
/* 3684 */                                               int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 3685 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3688 */                                             if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 3689 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3692 */                                           if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 3693 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                           }
/*      */                                           
/* 3696 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 3697 */                                           out.write(32);
/* 3698 */                                           out.write(32);
/* 3699 */                                           out.write("\n    </td>\n            ");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 3703 */                                           out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 3704 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3705 */                                           out.write(32);
/* 3706 */                                           out.write(62);
/* 3707 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3708 */                                           out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                         }
/* 3710 */                                         out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 3711 */                                         if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                         {
/* 3713 */                                           out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3714 */                                           out.print(hostid);
/* 3715 */                                           out.write("&attributeid=");
/* 3716 */                                           out.print(diskid);
/* 3717 */                                           out.write("&period=-7')\" class='bodytextbold'>");
/* 3718 */                                           out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 3719 */                                           out.write(45);
/* 3720 */                                           out.print(diskvalue);
/* 3721 */                                           out.write("</a> %\n     ");
/*      */                                         }
/* 3723 */                                         out.write("\n  </td>\n  </tr>\n</table>");
/* 3724 */                                         out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 3725 */                                         out.write("</td></tr></table>\n\n");
/*      */                                       } else {
/* 3727 */                                         out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 3728 */                                         out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 3729 */                                         out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 3730 */                                         out.print(systeminfo.get("host_resid"));
/* 3731 */                                         out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 3732 */                                         out.print(hostname);
/* 3733 */                                         out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 3734 */                                         if (cpuvalue != null)
/*      */                                         {
/*      */ 
/* 3737 */                                           dialGraph.setValue(Long.parseLong(cpuvalue));
/* 3738 */                                           out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                           
/* 3740 */                                           DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 3741 */                                           _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 3742 */                                           _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 3744 */                                           _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                           
/* 3746 */                                           _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                           
/* 3748 */                                           _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                           
/* 3750 */                                           _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                           
/* 3752 */                                           _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                           
/* 3754 */                                           _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                           
/* 3756 */                                           _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                           
/* 3758 */                                           _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                           
/* 3760 */                                           _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                           
/* 3762 */                                           _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 3763 */                                           int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 3764 */                                           if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 3765 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                           }
/*      */                                           
/* 3768 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 3769 */                                           out.write("\n         </td>\n     ");
/*      */                                         }
/*      */                                         else {
/* 3772 */                                           out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3773 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3774 */                                           out.write(39);
/* 3775 */                                           out.write(32);
/* 3776 */                                           out.write(62);
/* 3777 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3778 */                                           out.write("\n \t\t</td>\n\t\t");
/*      */                                         }
/* 3780 */                                         if (memvalue != null) {
/* 3781 */                                           dialGraph.setValue(Long.parseLong(memvalue));
/* 3782 */                                           out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                           
/* 3784 */                                           DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 3785 */                                           _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 3786 */                                           _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 3788 */                                           _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                           
/* 3790 */                                           _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                           
/* 3792 */                                           _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                           
/* 3794 */                                           _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                           
/* 3796 */                                           _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                           
/* 3798 */                                           _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                           
/* 3800 */                                           _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                           
/* 3802 */                                           _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                           
/* 3804 */                                           _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                           
/* 3806 */                                           _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 3807 */                                           int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 3808 */                                           if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 3809 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                           }
/*      */                                           
/* 3812 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 3813 */                                           out.write("\n            </td>\n         ");
/*      */                                         }
/*      */                                         else {
/* 3816 */                                           out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3817 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3818 */                                           out.write(39);
/* 3819 */                                           out.write(32);
/* 3820 */                                           out.write(62);
/* 3821 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3822 */                                           out.write("\n \t\t</td>\n\t\t");
/*      */                                         }
/* 3824 */                                         if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 3825 */                                           dialGraph.setValue(Long.parseLong(diskvalue));
/* 3826 */                                           out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                           
/* 3828 */                                           DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 3829 */                                           _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 3830 */                                           _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 3832 */                                           _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                           
/* 3834 */                                           _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                           
/* 3836 */                                           _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                           
/* 3838 */                                           _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                           
/* 3840 */                                           _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                           
/* 3842 */                                           _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                           
/* 3844 */                                           _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                           
/* 3846 */                                           _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                           
/* 3848 */                                           _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                           
/* 3850 */                                           _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 3851 */                                           int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 3852 */                                           if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 3853 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                           }
/*      */                                           
/* 3856 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 3857 */                                           out.write(32);
/* 3858 */                                           out.write("\n\t          </td>\n\t  ");
/*      */                                         }
/*      */                                         else {
/* 3861 */                                           out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3862 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3863 */                                           out.write(39);
/* 3864 */                                           out.write(32);
/* 3865 */                                           out.write(62);
/* 3866 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3867 */                                           out.write("\n \t\t</td>\n\t\t");
/*      */                                         }
/* 3869 */                                         out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3870 */                                         out.print(hostid);
/* 3871 */                                         out.write("&attributeid=");
/* 3872 */                                         out.print(cpuid);
/* 3873 */                                         out.write("&period=-7')\" class='tooltip'>");
/* 3874 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 3875 */                                         out.write(32);
/* 3876 */                                         out.write(45);
/* 3877 */                                         out.write(32);
/* 3878 */                                         if (cpuvalue != null) {
/* 3879 */                                           out.print(cpuvalue);
/*      */                                         }
/* 3881 */                                         out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3882 */                                         out.print(hostid);
/* 3883 */                                         out.write("&attributeid=");
/* 3884 */                                         out.print(memid);
/* 3885 */                                         out.write("&period=-7')\" class='tooltip'>");
/* 3886 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 3887 */                                         out.write(45);
/* 3888 */                                         if (memvalue != null) {
/* 3889 */                                           out.print(memvalue);
/*      */                                         }
/* 3891 */                                         out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3892 */                                         out.print(hostid);
/* 3893 */                                         out.write("&attributeid=");
/* 3894 */                                         out.print(diskid);
/* 3895 */                                         out.write("&period=-7')\" class='tooltip'>");
/* 3896 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 3897 */                                         out.write(45);
/* 3898 */                                         if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 3899 */                                           out.print(diskvalue);
/*      */                                         }
/* 3901 */                                         out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                       }
/* 3903 */                                       out.write(10);
/* 3904 */                                       out.write(10);
/*      */                                     }
/*      */                                     
/*      */                                   }
/*      */                                 }
/*      */                                 catch (Exception e)
/*      */                                 {
/* 3911 */                                   e.printStackTrace();
/*      */                                 }
/* 3913 */                                 out.write(10);
/* 3914 */                                 out.write("\n</div>\n");
/*      */                                 
/* 3916 */                                 IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3917 */                                 _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 3918 */                                 _jspx_th_c_005fif_005f18.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3920 */                                 _jspx_th_c_005fif_005f18.setTest("${DotNetAgentAvailable == true}");
/* 3921 */                                 int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 3922 */                                 if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                                   for (;;) {
/* 3924 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3926 */                                     String tablist = "overview, webtransaction, database, traces";
/*      */                                     
/* 3928 */                                     out.write(10);
/* 3929 */                                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 3930 */                                     out.write("\n<link href=\"/apminsight/style/apminsight-styles.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script src='/apminsight/js/includeJS.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('');\n\tvar _moduleName = \"/apminsight\";//No I18N\n</script>\n<div id=\"alertsTabLists\" style=\"display: none;\">");
/* 3931 */                                     out.print(tablist);
/* 3932 */                                     out.write("</div>\n<div id=\"TabContent\" style=\"width:99%\">\n<div id=\"insight_OverviewTab\" style=\"display:none;padding:5px;\"></div>\n<div id=\"insight_transactionTab\" style=\"display:none;padding:1px;\"></div>\n<div id=\"insight_databaseTab\" style=\"display:none;padding:1px;\"></div>\n<div id=\"insight_tracesTab\" style=\"display:none;padding:1px;\"></div>\n<div class=\"load-msg\" style=\"padding:0em 0em;\"><img src=\"/apminsight/images/pageloading.gif\" /></div>\n</div>\n");
/* 3933 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3934 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3938 */                                 if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3939 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                                 }
/*      */                                 
/* 3942 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3943 */                                 out.write(10);
/* 3944 */                                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 3945 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3948 */                               if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3949 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3952 */                             if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3953 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                             }
/*      */                             
/* 3956 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 3957 */                             out.write(10);
/* 3958 */                             if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                               return;
/* 3960 */                             out.write(10);
/* 3961 */                             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3962 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3966 */                         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3967 */                           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                         }
/*      */                         else {
/* 3970 */                           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3971 */                           out.write(10);
/* 3972 */                           if (_jspx_meth_c_005fif_005f19(_jspx_page_context))
/*      */                             return;
/* 3974 */                           out.write("\n</body>\n</html>\n");
/*      */                         }
/* 3976 */                       } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3977 */         out = _jspx_out;
/* 3978 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3979 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3980 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3983 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3989 */     PageContext pageContext = _jspx_page_context;
/* 3990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3992 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3993 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3994 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 3996 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3997 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3999 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 4000 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 4002 */           out.write(10);
/* 4003 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 4004 */             return true;
/* 4005 */           out.write(10);
/* 4006 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 4007 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4011 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 4012 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4015 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 4016 */         out = _jspx_page_context.popBody(); }
/* 4017 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4019 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 4020 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 4022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 4027 */     PageContext pageContext = _jspx_page_context;
/* 4028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4030 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 4031 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 4032 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 4034 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 4036 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 4037 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 4038 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 4039 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4040 */       return true;
/*      */     }
/* 4042 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4048 */     PageContext pageContext = _jspx_page_context;
/* 4049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4051 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4052 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4053 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4055 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4057 */     _jspx_th_tiles_005fput_005f0.setValue(".Net Monitor Details");
/* 4058 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4059 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4060 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4061 */       return true;
/*      */     }
/* 4063 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4069 */     PageContext pageContext = _jspx_page_context;
/* 4070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4072 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4073 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4074 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4076 */     _jspx_th_c_005fif_005f1.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4077 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4078 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4080 */         out.write(10);
/* 4081 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4082 */           return true;
/* 4083 */         out.write(10);
/* 4084 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4085 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4089 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4090 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4091 */       return true;
/*      */     }
/* 4093 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4099 */     PageContext pageContext = _jspx_page_context;
/* 4100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4102 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4103 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4104 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4106 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4108 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 4109 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4110 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4111 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4112 */       return true;
/*      */     }
/* 4114 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4115 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4120 */     PageContext pageContext = _jspx_page_context;
/* 4121 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4123 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4124 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 4125 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4127 */     _jspx_th_c_005fif_005f2.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4128 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 4129 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 4131 */         out.write(10);
/* 4132 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4133 */           return true;
/* 4134 */         out.write(10);
/* 4135 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 4136 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4140 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 4141 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4142 */       return true;
/*      */     }
/* 4144 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4150 */     PageContext pageContext = _jspx_page_context;
/* 4151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4153 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4154 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4155 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4157 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 4159 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 4160 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4161 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4162 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4163 */       return true;
/*      */     }
/* 4165 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4166 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4171 */     PageContext pageContext = _jspx_page_context;
/* 4172 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4174 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4175 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4176 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4178 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 4180 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/DotNetLeftPage.jsp");
/* 4181 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4182 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4183 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4184 */       return true;
/*      */     }
/* 4186 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4192 */     PageContext pageContext = _jspx_page_context;
/* 4193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4195 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4196 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4197 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4199 */     _jspx_th_c_005fout_005f0.setValue("${insightResourceID}");
/* 4200 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4201 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4202 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4203 */       return true;
/*      */     }
/* 4205 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4206 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4211 */     PageContext pageContext = _jspx_page_context;
/* 4212 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4214 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4215 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4216 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4218 */     _jspx_th_c_005fout_005f1.setValue("${insightApplicationID}");
/* 4219 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4220 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4221 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4222 */       return true;
/*      */     }
/* 4224 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4230 */     PageContext pageContext = _jspx_page_context;
/* 4231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4233 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4234 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4235 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4237 */     _jspx_th_c_005fout_005f2.setValue("${insightResourceID}");
/* 4238 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4239 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4240 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4241 */       return true;
/*      */     }
/* 4243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4249 */     PageContext pageContext = _jspx_page_context;
/* 4250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4252 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4253 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4254 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4256 */     _jspx_th_c_005fout_005f3.setValue("${insightResourceID}");
/* 4257 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4258 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4259 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4260 */       return true;
/*      */     }
/* 4262 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4268 */     PageContext pageContext = _jspx_page_context;
/* 4269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4271 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4272 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4273 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4275 */     _jspx_th_c_005fout_005f4.setValue("${insightApplicationID}");
/* 4276 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4277 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4278 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4279 */       return true;
/*      */     }
/* 4281 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4282 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4287 */     PageContext pageContext = _jspx_page_context;
/* 4288 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4290 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4291 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4292 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4294 */     _jspx_th_c_005fout_005f5.setValue("${insightResourceID}");
/* 4295 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4296 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4297 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4298 */       return true;
/*      */     }
/* 4300 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4301 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4306 */     PageContext pageContext = _jspx_page_context;
/* 4307 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4309 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4310 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4311 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4313 */     _jspx_th_c_005fout_005f6.setValue("${insightResourceID}");
/* 4314 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4315 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4316 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4317 */       return true;
/*      */     }
/* 4319 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4320 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4325 */     PageContext pageContext = _jspx_page_context;
/* 4326 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4328 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4329 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4330 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4332 */     _jspx_th_c_005fout_005f7.setValue("${insightApplicationID}");
/* 4333 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4334 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4335 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4336 */       return true;
/*      */     }
/* 4338 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4344 */     PageContext pageContext = _jspx_page_context;
/* 4345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4347 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4348 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4349 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4351 */     _jspx_th_c_005fout_005f8.setValue("${insightResourceID}");
/* 4352 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4353 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4354 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4355 */       return true;
/*      */     }
/* 4357 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4363 */     PageContext pageContext = _jspx_page_context;
/* 4364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4366 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4367 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4368 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4370 */     _jspx_th_c_005fout_005f9.setValue("${insightResourceID}");
/* 4371 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4372 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4373 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4374 */       return true;
/*      */     }
/* 4376 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4382 */     PageContext pageContext = _jspx_page_context;
/* 4383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4385 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4386 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4387 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4389 */     _jspx_th_c_005fout_005f10.setValue("${insightApplicationID}");
/* 4390 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4391 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4392 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4393 */       return true;
/*      */     }
/* 4395 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4396 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4401 */     PageContext pageContext = _jspx_page_context;
/* 4402 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4404 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4405 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4406 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4408 */     _jspx_th_c_005fout_005f11.setValue("${insightResourceID}");
/* 4409 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4410 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4411 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4412 */       return true;
/*      */     }
/* 4414 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4415 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4420 */     PageContext pageContext = _jspx_page_context;
/* 4421 */     JspWriter out = _jspx_page_context.getOut();
/* 4422 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 4423 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 4425 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4426 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4427 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4429 */     _jspx_th_c_005fif_005f8.setTest("${!empty showconfigdiv && (showconfigdiv == 'true')}");
/* 4430 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4431 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 4433 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: block\">\n");
/* 4434 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/DotNetConfig.jsp?reconfigure=true&configured=true", out, false);
/* 4435 */         out.write("\n<br>\n</div>\n");
/* 4436 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4437 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4441 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4442 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4443 */       return true;
/*      */     }
/* 4445 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4446 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4451 */     PageContext pageContext = _jspx_page_context;
/* 4452 */     JspWriter out = _jspx_page_context.getOut();
/* 4453 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 4454 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 4456 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4457 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4458 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4460 */     _jspx_th_c_005fif_005f9.setTest("${empty showconfigdiv}");
/* 4461 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4462 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 4464 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: none\">\n");
/* 4465 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/DotNetConfig.jsp?reconfigure=true&configured=true", out, false);
/* 4466 */         out.write("\n<br>\n</div>\n");
/* 4467 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4468 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4472 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4473 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4474 */       return true;
/*      */     }
/* 4476 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4477 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4482 */     PageContext pageContext = _jspx_page_context;
/* 4483 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4485 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4486 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 4487 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4489 */     _jspx_th_c_005fif_005f10.setTest("${not empty param.haid}");
/* 4490 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 4491 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 4493 */         out.write(10);
/* 4494 */         out.write(9);
/* 4495 */         out.write(9);
/* 4496 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 4497 */           return true;
/* 4498 */         out.write(10);
/* 4499 */         out.write(9);
/* 4500 */         out.write(9);
/* 4501 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4502 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4506 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4507 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4508 */       return true;
/*      */     }
/* 4510 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4516 */     PageContext pageContext = _jspx_page_context;
/* 4517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4519 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 4520 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4521 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4523 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 4525 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 4526 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4527 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4528 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4529 */         out = _jspx_page_context.pushBody();
/* 4530 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4531 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4534 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 4535 */           return true;
/* 4536 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4537 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4540 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4541 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4544 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4545 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 4546 */       return true;
/*      */     }
/* 4548 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 4549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4554 */     PageContext pageContext = _jspx_page_context;
/* 4555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4557 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4558 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4559 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4561 */     _jspx_th_c_005fout_005f12.setValue("${param.haid}");
/* 4562 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4563 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4564 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4565 */       return true;
/*      */     }
/* 4567 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4568 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4573 */     PageContext pageContext = _jspx_page_context;
/* 4574 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4576 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4577 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4578 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4580 */     _jspx_th_c_005fif_005f11.setTest("${not empty param.resourceid}");
/* 4581 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4582 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 4584 */         out.write(10);
/* 4585 */         out.write(9);
/* 4586 */         out.write(9);
/* 4587 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 4588 */           return true;
/* 4589 */         out.write(10);
/* 4590 */         out.write(9);
/* 4591 */         out.write(9);
/* 4592 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4593 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4597 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4598 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4599 */       return true;
/*      */     }
/* 4601 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4602 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4607 */     PageContext pageContext = _jspx_page_context;
/* 4608 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4610 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 4611 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4612 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4614 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 4616 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 4617 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4618 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4619 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4620 */         out = _jspx_page_context.pushBody();
/* 4621 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 4622 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4625 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 4626 */           return true;
/* 4627 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4628 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4631 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4632 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4635 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4636 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 4637 */       return true;
/*      */     }
/* 4639 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 4640 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4645 */     PageContext pageContext = _jspx_page_context;
/* 4646 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4648 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4649 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4650 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 4652 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 4653 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4654 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4655 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4656 */       return true;
/*      */     }
/* 4658 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4659 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4664 */     PageContext pageContext = _jspx_page_context;
/* 4665 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4667 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4668 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4669 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4671 */     _jspx_th_c_005fout_005f14.setValue("${myfield_paramresid}");
/* 4672 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4673 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4674 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4675 */       return true;
/*      */     }
/* 4677 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4678 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4683 */     PageContext pageContext = _jspx_page_context;
/* 4684 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4686 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4687 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 4688 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4690 */     _jspx_th_c_005fif_005f12.setTest("${not empty param.haid}");
/* 4691 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 4692 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 4694 */         out.write(10);
/* 4695 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 4696 */           return true;
/* 4697 */         out.write(10);
/* 4698 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 4699 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4703 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 4704 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4705 */       return true;
/*      */     }
/* 4707 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4713 */     PageContext pageContext = _jspx_page_context;
/* 4714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4716 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 4717 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 4718 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 4720 */     _jspx_th_c_005fset_005f2.setVar("myfield_resid");
/*      */     
/* 4722 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 4723 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 4724 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 4725 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4726 */         out = _jspx_page_context.pushBody();
/* 4727 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 4728 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4731 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 4732 */           return true;
/* 4733 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 4734 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4737 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4738 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4741 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 4742 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 4743 */       return true;
/*      */     }
/* 4745 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 4746 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4751 */     PageContext pageContext = _jspx_page_context;
/* 4752 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4754 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4755 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4756 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 4758 */     _jspx_th_c_005fout_005f15.setValue("${param.haid}");
/* 4759 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4760 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4761 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4762 */       return true;
/*      */     }
/* 4764 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4765 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4770 */     PageContext pageContext = _jspx_page_context;
/* 4771 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4773 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4774 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4775 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4777 */     _jspx_th_c_005fif_005f13.setTest("${not empty param.resourceid}");
/* 4778 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4779 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 4781 */         out.write(10);
/* 4782 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f13, _jspx_page_context))
/* 4783 */           return true;
/* 4784 */         out.write(10);
/* 4785 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4786 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4790 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4791 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4792 */       return true;
/*      */     }
/* 4794 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4800 */     PageContext pageContext = _jspx_page_context;
/* 4801 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4803 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 4804 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 4805 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 4807 */     _jspx_th_c_005fset_005f3.setVar("myfield_resid");
/*      */     
/* 4809 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 4810 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 4811 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 4812 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 4813 */         out = _jspx_page_context.pushBody();
/* 4814 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 4815 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4818 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 4819 */           return true;
/* 4820 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 4821 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4824 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 4825 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4828 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 4829 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 4830 */       return true;
/*      */     }
/* 4832 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 4833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4838 */     PageContext pageContext = _jspx_page_context;
/* 4839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4841 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4842 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4843 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 4845 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 4846 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4847 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4848 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4849 */       return true;
/*      */     }
/* 4851 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4857 */     PageContext pageContext = _jspx_page_context;
/* 4858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4860 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 4861 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 4862 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4864 */     _jspx_th_c_005fset_005f4.setVar("trstripclass");
/*      */     
/* 4866 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 4867 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 4868 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 4869 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 4870 */         out = _jspx_page_context.pushBody();
/* 4871 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 4872 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4875 */         if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 4876 */           return true;
/* 4877 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 4878 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4881 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 4882 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4885 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 4886 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 4887 */       return true;
/*      */     }
/* 4889 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 4890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4895 */     PageContext pageContext = _jspx_page_context;
/* 4896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4898 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4899 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4900 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 4902 */     _jspx_th_c_005fout_005f17.setValue("");
/* 4903 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4904 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4905 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4906 */       return true;
/*      */     }
/* 4908 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4914 */     PageContext pageContext = _jspx_page_context;
/* 4915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4917 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 4918 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 4919 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4921 */     _jspx_th_c_005fset_005f5.setVar("myfield_entity");
/*      */     
/* 4923 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 4924 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 4925 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 4926 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 4927 */         out = _jspx_page_context.pushBody();
/* 4928 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 4929 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4932 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 4933 */           return true;
/* 4934 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 4935 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4938 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 4939 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4942 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 4943 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 4944 */       return true;
/*      */     }
/* 4946 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 4947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4952 */     PageContext pageContext = _jspx_page_context;
/* 4953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4955 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4956 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4957 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 4959 */     _jspx_th_c_005fout_005f18.setValue("noalarms");
/* 4960 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4961 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4962 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4963 */       return true;
/*      */     }
/* 4965 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4971 */     PageContext pageContext = _jspx_page_context;
/* 4972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4974 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4975 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 4976 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4978 */     _jspx_th_c_005fif_005f14.setTest("${not empty param.entity}");
/* 4979 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 4980 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 4982 */         out.write(10);
/* 4983 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f14, _jspx_page_context))
/* 4984 */           return true;
/* 4985 */         out.write(10);
/* 4986 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 4987 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4991 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 4992 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4993 */       return true;
/*      */     }
/* 4995 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4996 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5001 */     PageContext pageContext = _jspx_page_context;
/* 5002 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5004 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5005 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 5006 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 5008 */     _jspx_th_c_005fset_005f6.setVar("myfield_entity");
/*      */     
/* 5010 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 5011 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 5012 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 5013 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5014 */         out = _jspx_page_context.pushBody();
/* 5015 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 5016 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5019 */         if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 5020 */           return true;
/* 5021 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 5022 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5025 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5026 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5029 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 5030 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 5031 */       return true;
/*      */     }
/* 5033 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 5034 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5039 */     PageContext pageContext = _jspx_page_context;
/* 5040 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5042 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5043 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5044 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 5046 */     _jspx_th_c_005fout_005f19.setValue("${param.entity}");
/* 5047 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5048 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5049 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5050 */       return true;
/*      */     }
/* 5052 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5058 */     PageContext pageContext = _jspx_page_context;
/* 5059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5061 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5062 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 5063 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5065 */     _jspx_th_c_005fif_005f15.setTest("${not empty param.includeClass}");
/* 5066 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 5067 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 5069 */         out.write(10);
/* 5070 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 5071 */           return true;
/* 5072 */         out.write(10);
/* 5073 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 5074 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5078 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 5079 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 5080 */       return true;
/*      */     }
/* 5082 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 5083 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5088 */     PageContext pageContext = _jspx_page_context;
/* 5089 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5091 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5092 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 5093 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 5095 */     _jspx_th_c_005fset_005f7.setVar("trstripclass");
/*      */     
/* 5097 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 5098 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 5099 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 5100 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 5101 */         out = _jspx_page_context.pushBody();
/* 5102 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 5103 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5106 */         if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 5107 */           return true;
/* 5108 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 5109 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5112 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 5113 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5116 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 5117 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 5118 */       return true;
/*      */     }
/* 5120 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 5121 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5126 */     PageContext pageContext = _jspx_page_context;
/* 5127 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5129 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5130 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5131 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 5133 */     _jspx_th_c_005fout_005f20.setValue("${param.includeClass}");
/* 5134 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5135 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5136 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5137 */       return true;
/*      */     }
/* 5139 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5140 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5145 */     PageContext pageContext = _jspx_page_context;
/* 5146 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5148 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5149 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5150 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5152 */     _jspx_th_c_005fout_005f21.setValue("${trstripclass}");
/* 5153 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5154 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5155 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5156 */       return true;
/*      */     }
/* 5158 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5159 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5164 */     PageContext pageContext = _jspx_page_context;
/* 5165 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5167 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5168 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 5169 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 5170 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 5171 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 5172 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5173 */         out = _jspx_page_context.pushBody();
/* 5174 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 5175 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5178 */         out.write("am.myfield.customfield.text");
/* 5179 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 5180 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5183 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5184 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5187 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 5188 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5189 */       return true;
/*      */     }
/* 5191 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5197 */     PageContext pageContext = _jspx_page_context;
/* 5198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5200 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5201 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5202 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5204 */     _jspx_th_c_005fout_005f22.setValue("${myfield_resid}");
/* 5205 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5206 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5207 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5208 */       return true;
/*      */     }
/* 5210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5216 */     PageContext pageContext = _jspx_page_context;
/* 5217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5219 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5220 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5221 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5223 */     _jspx_th_c_005fout_005f23.setValue("${myfield_entity}");
/* 5224 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5225 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5226 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5227 */       return true;
/*      */     }
/* 5229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5230 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5235 */     PageContext pageContext = _jspx_page_context;
/* 5236 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5238 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5239 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5240 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5242 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 5243 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5244 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5245 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5246 */       return true;
/*      */     }
/* 5248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5249 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5254 */     PageContext pageContext = _jspx_page_context;
/* 5255 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5257 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5258 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5259 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5261 */     _jspx_th_c_005fout_005f25.setValue("${param.resourcename}");
/* 5262 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5263 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5264 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5265 */       return true;
/*      */     }
/* 5267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5273 */     PageContext pageContext = _jspx_page_context;
/* 5274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5276 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5277 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5278 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5280 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 5281 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5282 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5283 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5284 */       return true;
/*      */     }
/* 5286 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5292 */     PageContext pageContext = _jspx_page_context;
/* 5293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5295 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5296 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5297 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5299 */     _jspx_th_c_005fout_005f27.setValue("${param.resourcename}");
/* 5300 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5301 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5302 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5303 */       return true;
/*      */     }
/* 5305 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5311 */     PageContext pageContext = _jspx_page_context;
/* 5312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5314 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5315 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5316 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5318 */     _jspx_th_c_005fout_005f28.setValue("${param.resourceid}");
/* 5319 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5320 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5321 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5322 */       return true;
/*      */     }
/* 5324 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5330 */     PageContext pageContext = _jspx_page_context;
/* 5331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5333 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5334 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 5335 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5337 */     _jspx_th_c_005fif_005f17.setTest("${ !empty param.haid && param.haid!='null' }");
/* 5338 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 5339 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 5341 */         out.write("&haid=");
/* 5342 */         if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fif_005f17, _jspx_page_context))
/* 5343 */           return true;
/* 5344 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 5345 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5349 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 5350 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 5351 */       return true;
/*      */     }
/* 5353 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 5354 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5359 */     PageContext pageContext = _jspx_page_context;
/* 5360 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5362 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5363 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5364 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5366 */     _jspx_th_c_005fout_005f29.setValue("${param.haid}");
/* 5367 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5368 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5369 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5370 */       return true;
/*      */     }
/* 5372 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5373 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5378 */     PageContext pageContext = _jspx_page_context;
/* 5379 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5381 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5382 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 5383 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5385 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 5387 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 5388 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 5389 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 5390 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 5391 */       return true;
/*      */     }
/* 5393 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 5394 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5399 */     PageContext pageContext = _jspx_page_context;
/* 5400 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5402 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5403 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 5404 */     _jspx_th_c_005fif_005f19.setParent(null);
/*      */     
/* 5406 */     _jspx_th_c_005fif_005f19.setTest("${DotNetAgentAvailable == 'true'}");
/* 5407 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 5408 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 5410 */         out.write("\n<script type=\"text/javascript\">\n$(document).ajaxComplete(function(event,request, settings) {\n\t\tif(settings.url.indexOf(\"myFields.do\") != -1)\n\t\t{\n\t\t\treturn;\n\t\t}\n\t\tif(settings.url.indexOf(\"showOverview\") != -1)\n\t\t{\n\t\t\tshowHide('insight_OverviewTab','");
/* 5411 */         if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 5412 */           return true;
/* 5413 */         out.write("', '");
/* 5414 */         if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 5415 */           return true;
/* 5416 */         out.write("');//No I18N\n\t\t}\n\t\telse if(settings.url.indexOf(\"showTransactions\") != -1)\n\t\t{\n\t\t\tshowHide('insight_transactionTab','");
/* 5417 */         if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 5418 */           return true;
/* 5419 */         out.write("', '");
/* 5420 */         if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 5421 */           return true;
/* 5422 */         out.write("');//No I18N\n\t\t}\n\t\telse if(settings.url.indexOf(\"showDBOperations\") != -1)\n\t\t{\n\t\t\tshowHide('insight_databaseTab','");
/* 5423 */         if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 5424 */           return true;
/* 5425 */         out.write("', '");
/* 5426 */         if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 5427 */           return true;
/* 5428 */         out.write("');//No I18N\n\t\t}\n\t\telse if(settings.url.indexOf(\"traces.do\") != -1)\n\t\t{\n\t\t\tshowHide('insight_tracesTab','");
/* 5429 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 5430 */           return true;
/* 5431 */         out.write("', '");
/* 5432 */         if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 5433 */           return true;
/* 5434 */         out.write("');//No I18N\n\t\t}\n\t\telse\n\t\t{\n\t\t\tshowHide('OverviewTab');//No I18N\n\t\t}\n\t\t$('table').remove('.SelectedAppBg');//No I18N\n });\n</script>\n");
/* 5435 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 5436 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5440 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 5441 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 5442 */       return true;
/*      */     }
/* 5444 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 5445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5450 */     PageContext pageContext = _jspx_page_context;
/* 5451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5453 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5454 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 5455 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 5457 */     _jspx_th_c_005fout_005f30.setValue("${insightResourceID}");
/* 5458 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 5459 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 5460 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5461 */       return true;
/*      */     }
/* 5463 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5464 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5469 */     PageContext pageContext = _jspx_page_context;
/* 5470 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5472 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5473 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 5474 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 5476 */     _jspx_th_c_005fout_005f31.setValue("${insightApplicationID}");
/* 5477 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 5478 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 5479 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5480 */       return true;
/*      */     }
/* 5482 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5488 */     PageContext pageContext = _jspx_page_context;
/* 5489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5491 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5492 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 5493 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 5495 */     _jspx_th_c_005fout_005f32.setValue("${insightResourceID}");
/* 5496 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 5497 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 5498 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5499 */       return true;
/*      */     }
/* 5501 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5502 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5507 */     PageContext pageContext = _jspx_page_context;
/* 5508 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5510 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5511 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 5512 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 5514 */     _jspx_th_c_005fout_005f33.setValue("${insightApplicationID}");
/* 5515 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 5516 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 5517 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5518 */       return true;
/*      */     }
/* 5520 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5526 */     PageContext pageContext = _jspx_page_context;
/* 5527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5529 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5530 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 5531 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 5533 */     _jspx_th_c_005fout_005f34.setValue("${insightResourceID}");
/* 5534 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 5535 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 5536 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5537 */       return true;
/*      */     }
/* 5539 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5540 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5545 */     PageContext pageContext = _jspx_page_context;
/* 5546 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5548 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5549 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 5550 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 5552 */     _jspx_th_c_005fout_005f35.setValue("${insightApplicationID}");
/* 5553 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 5554 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 5555 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5556 */       return true;
/*      */     }
/* 5558 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5564 */     PageContext pageContext = _jspx_page_context;
/* 5565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5567 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5568 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 5569 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 5571 */     _jspx_th_c_005fout_005f36.setValue("${insightResourceID}");
/* 5572 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 5573 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 5574 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5575 */       return true;
/*      */     }
/* 5577 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5578 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5583 */     PageContext pageContext = _jspx_page_context;
/* 5584 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5586 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5587 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 5588 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 5590 */     _jspx_th_c_005fout_005f37.setValue("${insightApplicationID}");
/* 5591 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 5592 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 5593 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5594 */       return true;
/*      */     }
/* 5596 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5597 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DotNet_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */