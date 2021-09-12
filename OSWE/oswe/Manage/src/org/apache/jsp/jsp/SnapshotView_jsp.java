/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.BarChart;
/*      */ import com.adventnet.awolf.tags.Property;
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
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*      */ 
/*      */ public final class SnapshotView_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 1387 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1432 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 1480 */       message = EnterpriseUtil.decodeString(message);
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
/* 1991 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
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
/*      */ 
/*      */   public String getGroupType(String resourceid)
/*      */   {
/* 2187 */     String groupType = FormatUtil.getString("am.webclient.common.util.MGSTR");
/* 2188 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2189 */     ResultSet rs = null;
/* 2190 */     String query = null;
/*      */     try
/*      */     {
/* 2193 */       query = "select TYPE from AM_HOLISTICAPPLICATION where TYPE=1 and AM_HOLISTICAPPLICATION.HAID ='" + resourceid + "'";
/* 2194 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2195 */       if (rs.next())
/*      */       {
/* 2197 */         groupType = FormatUtil.getString("am.webclient.reports.excel.subgroup.text");
/*      */       }
/* 2199 */       rs.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2203 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 2206 */     return groupType;
/*      */   }
/*      */   
/* 2209 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2215 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2216 */   static { _jspx_dependants.put("/jsp/includes/ApplicationLinks.jspf", Long.valueOf(1473429417000L));
/* 2217 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2218 */     _jspx_dependants.put("/jsp/includes/Recent5Alarms.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2247 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2251 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2273 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2277 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2278 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2279 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2280 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2281 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2282 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2283 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.release();
/* 2284 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2285 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2286 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2288 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fnobody.release();
/* 2289 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2290 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.release();
/* 2291 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2292 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2293 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2294 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2295 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2296 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.release();
/* 2297 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2304 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2307 */     JspWriter out = null;
/* 2308 */     Object page = this;
/* 2309 */     JspWriter _jspx_out = null;
/* 2310 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2314 */       response.setContentType("text/html;charset=UTF-8");
/* 2315 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2317 */       _jspx_page_context = pageContext;
/* 2318 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2319 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2320 */       session = pageContext.getSession();
/* 2321 */       out = pageContext.getOut();
/* 2322 */       _jspx_out = out;
/*      */       
/* 2324 */       out.write("<!DOCTYPE html>\n");
/* 2325 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/* 2326 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2328 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2329 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2330 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2332 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2334 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2336 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2338 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2339 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2340 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2341 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2344 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2345 */         String available = null;
/* 2346 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2347 */         out.write(10);
/*      */         
/* 2349 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2350 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2351 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2353 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2355 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2357 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2359 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2360 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2361 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2362 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2365 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2366 */           String unavailable = null;
/* 2367 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2368 */           out.write(10);
/*      */           
/* 2370 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2371 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2372 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2374 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2376 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2378 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2380 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2381 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2382 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2383 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2386 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2387 */             String unmanaged = null;
/* 2388 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2389 */             out.write(10);
/*      */             
/* 2391 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2392 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2393 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2395 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2397 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2399 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2401 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2402 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2403 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2404 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2407 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2408 */               String scheduled = null;
/* 2409 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2410 */               out.write(10);
/*      */               
/* 2412 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2413 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2414 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2416 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2418 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2420 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2422 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2423 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2424 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2425 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2428 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2429 */                 String critical = null;
/* 2430 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2431 */                 out.write(10);
/*      */                 
/* 2433 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2434 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2435 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2437 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2439 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2441 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2443 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2444 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2445 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2446 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2449 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2450 */                   String clear = null;
/* 2451 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2452 */                   out.write(10);
/*      */                   
/* 2454 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2455 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2456 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2458 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2460 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2462 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2464 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2465 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2466 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2467 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2470 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2471 */                     String warning = null;
/* 2472 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2473 */                     out.write(10);
/* 2474 */                     out.write(10);
/*      */                     
/* 2476 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2477 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2479 */                     out.write(10);
/* 2480 */                     out.write(10);
/* 2481 */                     out.write(10);
/* 2482 */                     out.write(10);
/* 2483 */                     ManagedApplication mo = null;
/* 2484 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 2);
/* 2485 */                     if (mo == null) {
/* 2486 */                       mo = new ManagedApplication();
/* 2487 */                       _jspx_page_context.setAttribute("mo", mo, 2);
/*      */                     }
/* 2489 */                     out.write(10);
/* 2490 */                     com.adventnet.appmanager.snapshot.bean.AlarmGraph alertgraph = null;
/* 2491 */                     alertgraph = (com.adventnet.appmanager.snapshot.bean.AlarmGraph)_jspx_page_context.getAttribute("alertgraph", 2);
/* 2492 */                     if (alertgraph == null) {
/* 2493 */                       alertgraph = new com.adventnet.appmanager.snapshot.bean.AlarmGraph();
/* 2494 */                       _jspx_page_context.setAttribute("alertgraph", alertgraph, 2);
/*      */                     }
/* 2496 */                     out.write("\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/overlib.js\"></SCRIPT>\n\t<script>\nfunction showApplicationAlarms(alertcount,haid)\n{\n\tvar d=new Date();\n\tif(alertcount!=0)\n\t{\n\t\tMM_openBrWindow('/RecentAlarms.do?method=getApplicationAlarms&haid='+haid+'&d='+d,'AlarmSummary','width=600,height=450, scrollbars=yes');\n\t}\n}\nfunction showParticularSeverityAlarms(alertcount,severity,haid)\n{\n\tvar d=new Date();\n\tif(alertcount!=0)\n\t{\n\t\tMM_openBrWindow('/RecentAlarms.do?method=getApplicationAlarms&haid='+haid+'&severity='+severity+'&d='+d,'AlarmSummary','width=600,height=450, scrollbars=yes');\n\t}\n}\n</script>\n");
/*      */                     
/* 2498 */                     ArrayList attribIDs = new ArrayList();
/* 2499 */                     ArrayList resIDs = new ArrayList();
/* 2500 */                     resIDs.add(request.getParameter("haid"));
/* 2501 */                     String encodeurl = URLEncoder.encode("/showapplication.do?method=showSnapshot&haid=" + request.getParameter("haid") + "");
/* 2502 */                     attribIDs.add("17");
/* 2503 */                     attribIDs.add("18");
/* 2504 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2505 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2506 */                     String creationdate = (String)((org.apache.struts.action.DynaActionForm)pageContext.findAttribute("applicationform")).get("creationdate");
/* 2507 */                     String lastmodified = (String)((org.apache.struts.action.DynaActionForm)pageContext.findAttribute("applicationform")).get("lastmodified");
/* 2508 */                     if ((lastmodified == null) || (lastmodified.equals("")))
/* 2509 */                       lastmodified = creationdate;
/* 2510 */                     pageContext.setAttribute("createdate", java.sql.Date.valueOf(creationdate));
/* 2511 */                     pageContext.setAttribute("lastmodified", java.sql.Date.valueOf(lastmodified));
/*      */                     
/* 2513 */                     out.write(10);
/*      */                     
/* 2515 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2516 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2517 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2519 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 2520 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2521 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2523 */                         out.write(10);
/* 2524 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2526 */                         out.write(10);
/* 2527 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2529 */                         out.write(10);
/*      */                         
/* 2531 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2532 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2533 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2535 */                         _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                         
/* 2537 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2538 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2539 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2540 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2541 */                             out = _jspx_page_context.pushBody();
/* 2542 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2543 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2546 */                             out.write("\n\n   ");
/* 2547 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                             
/* 2549 */                             int subGroupLevels1 = com.adventnet.appmanager.util.Constants.getSubGroupLevels();
/*      */                             
/* 2551 */                             out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*      */                             
/* 2553 */                             String requestpathnew = "/images/mm_menu3.jsp";
/* 2554 */                             String category_2 = String.valueOf(com.adventnet.appmanager.util.Constants.isMinimizedversion());
/* 2555 */                             pageContext.setAttribute("category_2", category_2);
/*      */                             
/* 2557 */                             out.write(10);
/* 2558 */                             JspRuntimeLibrary.include(request, response, requestpathnew, out, false);
/* 2559 */                             out.write("\n</script>\n  <SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n      ");
/*      */                             
/* 2561 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2562 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2563 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 2565 */                             _jspx_th_c_005fif_005f0.setTest("${!empty param.haid }");
/* 2566 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2567 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2569 */                                 out.write(" \n      \t  ");
/* 2570 */                                 if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2572 */                                 out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr> \n          <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 2573 */                                 out.print(FormatUtil.getString("am.webclient.applicationlinks.monitorgrouplinks.text"));
/* 2574 */                                 out.write("</td>\n        </tr>\n\t\t        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" > ");
/* 2575 */                                 if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2577 */                                 out.write(32);
/* 2578 */                                 if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2580 */                                 out.write(" </td>\n        </tr>\n\n\t");
/*      */                                 
/* 2582 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2583 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2584 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 2586 */                                 _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2587 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2588 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2590 */                                     out.write(" \t \n\t<tr> \t \n\t");
/*      */                                     
/* 2592 */                                     Vector ParentMos1 = (Vector)request.getAttribute("ParentMos");
/* 2593 */                                     if ((ParentMos1 != null) && (ParentMos1.size() < subGroupLevels1)) {
/* 2594 */                                       out.write(" \t \n\t\t<td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \t \n\t\t\t<a href=\"#\"  onClick=\"toggleDiv('subgroup');\" class=\"new-left-links\">");
/* 2595 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2596 */                                       out.write("</a> \t \n\t\t\t</td> \t \n\t\t\t");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2600 */                                       out.write(" \t \n\t<td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \t \n\t\t<a href=\"#\"   class=\"disabledtext\">");
/* 2601 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2602 */                                       out.write("</a> \t \n\t\t</td> \t \n\t\t");
/*      */                                     }
/* 2604 */                                     out.write(" \t \n\t\t</tr> \t \n\t\t");
/* 2605 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2606 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2610 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2611 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2614 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2615 */                                 out.write("\n\n       ");
/*      */                                 
/* 2617 */                                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2618 */                                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2619 */                                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 2621 */                                 _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 2622 */                                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2623 */                                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 2625 */                                     out.write("\n\t   <tr>\n\t    ");
/*      */                                     
/* 2627 */                                     Vector ParentMos2 = (Vector)request.getAttribute("ParentMos");
/* 2628 */                                     int mgResourceId = Integer.parseInt(request.getParameter("haid"));
/* 2629 */                                     if ((ParentMos2 != null) && (ParentMos2.size() < subGroupLevels1) && (mgResourceId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) {
/* 2630 */                                       out.write("\n\t\t  <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" >\n\t\t    <a href=\"#\"  onClick=\"toggleDiv('subgroup');\" class=\"new-left-links\">");
/* 2631 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2632 */                                       out.write("</a>\n\t\t  </td>\n\t\t  ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2636 */                                       out.write("\n\t\t   <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" >\n\t\t     <a href=\"#\"   class=\"disabledtext\">");
/* 2637 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2638 */                                       out.write("</a>\n\t\t   </td>\n\t\t   ");
/*      */                                     }
/* 2640 */                                     out.write("\n\t   </tr>\n\t   ");
/* 2641 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2642 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2646 */                                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2647 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 2650 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2651 */                                 out.write("\n\n        <tr> \n\t\t               ");
/*      */                                 
/* 2653 */                                 PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2654 */                                 _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2655 */                                 _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 2657 */                                 _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2658 */                                 int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2659 */                                 if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                   for (;;) {
/* 2661 */                                     out.write("\n\t\t                 <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > ");
/*      */                                     
/* 2663 */                                     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2664 */                                     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2665 */                                     _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                                     
/* 2667 */                                     _jspx_th_c_005fif_005f4.setTest("${param.method!='getHAProfiles'}");
/* 2668 */                                     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2669 */                                     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                       for (;;) {
/* 2671 */                                         out.write(" \n\t\t                   <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 2672 */                                         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                           return;
/* 2674 */                                         out.write("\" \n\t\t                   class=\"new-left-links\">");
/* 2675 */                                         out.print(ALERTCONFIG_TEXT);
/* 2676 */                                         out.write("</a>");
/* 2677 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2678 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2682 */                                     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2683 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                     }
/*      */                                     
/* 2686 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2687 */                                     out.write(32);
/*      */                                     
/* 2689 */                                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2690 */                                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2691 */                                     _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                                     
/* 2693 */                                     _jspx_th_c_005fif_005f5.setTest("${param.method=='getHAProfiles'}");
/* 2694 */                                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2695 */                                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                       for (;;) {
/* 2697 */                                         out.write(" \n\t\t                   ");
/* 2698 */                                         out.print(ALERTCONFIG_TEXT);
/* 2699 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2700 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2704 */                                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2705 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                     }
/*      */                                     
/* 2708 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2709 */                                     out.write("</td>\n\t\t                   ");
/* 2710 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2711 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2715 */                                 if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2716 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                 }
/*      */                                 
/* 2719 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2720 */                                 out.write("\t\n\t\t       \n\t\t                   ");
/*      */                                 
/* 2722 */                                 PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2723 */                                 _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2724 */                                 _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 2726 */                                 _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/* 2727 */                                 int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2728 */                                 if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                   for (;;) {
/* 2730 */                                     out.write("\n\t\t                   <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \n\t\t                   ");
/*      */                                     
/* 2732 */                                     AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2733 */                                     _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 2734 */                                     _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                     
/* 2736 */                                     _jspx_th_am_005fadminlink_005f0.setHref(ALERTCONFIG_TEXT);
/*      */                                     
/* 2738 */                                     _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 2739 */                                     int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 2740 */                                     if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 2741 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2742 */                                         out = _jspx_page_context.pushBody();
/* 2743 */                                         _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 2744 */                                         _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2747 */                                         out.print(FormatUtil.getString("am.webclient.toolbar.configurealert.text"));
/* 2748 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 2749 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2752 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2753 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2756 */                                     if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 2757 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                     }
/*      */                                     
/* 2760 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 2761 */                                     out.write("</td>\n\t\t                ");
/* 2762 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2763 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2767 */                                 if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2768 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                 }
/*      */                                 
/* 2771 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2772 */                                 out.write("\t\t                   \n       </tr>\n        \n\t\t");
/*      */                                 
/* 2774 */                                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2775 */                                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2776 */                                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 2778 */                                 _jspx_th_c_005fif_005f6.setTest("${category_2!='true'}");
/* 2779 */                                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2780 */                                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                   for (;;) {
/* 2782 */                                     out.write("\n          <tr>   ");
/*      */                                     
/* 2784 */                                     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2785 */                                     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2786 */                                     _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f6);
/*      */                                     
/* 2788 */                                     _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 2789 */                                     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2790 */                                     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                       for (;;) {
/* 2792 */                                         out.write("\n          ");
/* 2793 */                                         if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2794 */                                           out.write("\n          <td width=\"98%\" height=\"21\"  class=\"leftlinkstd\"  > \n          ");
/*      */                                         } else {
/* 2796 */                                           out.write("\n\t          <td width=\"98%\" height=\"21\"  class=\"leftlinkstd\"> \n\t          ");
/*      */                                         }
/* 2798 */                                         out.write("\n\t          ");
/*      */                                         
/* 2800 */                                         IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2801 */                                         _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2802 */                                         _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                         
/* 2804 */                                         _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN}");
/* 2805 */                                         int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2806 */                                         if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                           for (;;) {
/* 2808 */                                             out.write("\n\t          ");
/*      */                                             
/* 2810 */                                             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2811 */                                             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2812 */                                             _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f7);
/*      */                                             
/* 2814 */                                             _jspx_th_c_005fif_005f8.setTest("${param.method!='getMonitorForm' }");
/* 2815 */                                             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2816 */                                             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                               for (;;) {
/* 2818 */                                                 out.write(" \n\t            <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=");
/* 2819 */                                                 if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                                                   return;
/* 2821 */                                                 out.write("\" \n\t            class=\"new-left-links\" >");
/* 2822 */                                                 out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 2823 */                                                 out.write(32);
/* 2824 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2825 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2829 */                                             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2830 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                             }
/*      */                                             
/* 2833 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2834 */                                             out.write(" \n\t            ");
/*      */                                             
/* 2836 */                                             IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2837 */                                             _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2838 */                                             _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f7);
/*      */                                             
/* 2840 */                                             _jspx_th_c_005fif_005f9.setTest("${param.method=='getMonitorForm' || (empty ADMIN)}");
/* 2841 */                                             int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2842 */                                             if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                               for (;;) {
/* 2844 */                                                 out.write(" \n\t            ");
/* 2845 */                                                 out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 2846 */                                                 out.write(32);
/* 2847 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2848 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2852 */                                             if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2853 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                             }
/*      */                                             
/* 2856 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2857 */                                             out.write("\n\t            ");
/* 2858 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2859 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2863 */                                         if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2864 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                         }
/*      */                                         
/* 2867 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2868 */                                         out.write("\n\t            ");
/*      */                                         
/* 2870 */                                         IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2871 */                                         _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2872 */                                         _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                         
/* 2874 */                                         _jspx_th_c_005fif_005f10.setTest("${empty ADMIN}");
/* 2875 */                                         int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2876 */                                         if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                           for (;;) {
/* 2878 */                                             out.write("\n\t            ");
/*      */                                             
/* 2880 */                                             AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2881 */                                             _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 2882 */                                             _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fif_005f10);
/*      */                                             
/* 2884 */                                             _jspx_th_am_005fadminlink_005f1.setHref("/jsp/CreateApplication.jsp");
/*      */                                             
/* 2886 */                                             _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 2887 */                                             int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 2888 */                                             if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 2889 */                                               if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2890 */                                                 out = _jspx_page_context.pushBody();
/* 2891 */                                                 _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 2892 */                                                 _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 2895 */                                                 out.write("\n\t            \t");
/* 2896 */                                                 out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 2897 */                                                 out.write("\n\t            ");
/* 2898 */                                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 2899 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 2902 */                                               if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2903 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 2906 */                                             if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 2907 */                                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                             }
/*      */                                             
/* 2910 */                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 2911 */                                             out.write("  \t\n\t            ");
/* 2912 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2913 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2917 */                                         if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2918 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                         }
/*      */                                         
/* 2921 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2922 */                                         out.write("   \n\t            </td>\n\t             ");
/* 2923 */                                         if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2924 */                                           out.write("\n\t             <td class=\"leftlinkstd\"  > \n\t\t\t\t<A class=\"new-left-links\" href=\"#\" ></a>   \n\t\t\t\t</td>\n          \n          ");
/*      */                                         } else {
/* 2926 */                                           out.write("\n\t\t\t\t<td class=\"leftlinkstd\"  > \n\t\t\t\t<A class=\"new-left-links\" href=\"#\" ></a>   \n\t\t\t\t</td>\n\t\t\t\t  ");
/*      */                                         }
/* 2928 */                                         out.write("\n          ");
/* 2929 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2930 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2934 */                                     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2935 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                     }
/*      */                                     
/* 2938 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2939 */                                     out.write("\n        </tr>\n        ");
/* 2940 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2941 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2945 */                                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2946 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                 }
/*      */                                 
/* 2949 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2950 */                                 out.write("\n\n        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"/fault/AMAlarmView.do?displayName=Alerts&haid=");
/* 2951 */                                 if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2953 */                                 out.write("\" \n            class=\"new-left-links\">");
/* 2954 */                                 out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/* 2955 */                                 out.write(" </td>\n        </tr>\n        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"javascript:fnOpenNewWindow('/showReports.do?period=0&actionMethod=generateHAAvailabilityReport&haid=");
/* 2956 */                                 if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2958 */                                 out.write("')\" \n            class=\"new-left-links\"> ");
/* 2959 */                                 out.print(FormatUtil.getString("am.webclient.applicationlinks.applicationreport.text"));
/* 2960 */                                 out.write("</td>\n        </tr>\n        \n        <tr> \n        ");
/*      */                                 
/* 2962 */                                 String editlink = "/showapplication.do?method=editApplication&haid=" + request.getParameter("haid");
/*      */                                 
/* 2964 */                                 out.write(10);
/* 2965 */                                 out.write(9);
/* 2966 */                                 out.write(9);
/*      */                                 
/* 2968 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2969 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 2970 */                                 _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 2972 */                                 _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/* 2973 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 2974 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 2976 */                                     out.write("\n\t\t\t<td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"");
/* 2977 */                                     out.print(editlink);
/* 2978 */                                     out.write("\"\n\t\t\tClass=\"new-left-links\">");
/* 2979 */                                     out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 2980 */                                     out.write("</a></td>\n\t\t");
/* 2981 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 2982 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2986 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 2987 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 2990 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 2991 */                                 out.write("\t\n\t\t");
/*      */                                 
/* 2993 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2994 */                                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2995 */                                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 2997 */                                 _jspx_th_logic_005fnotPresent_005f0.setRole("ENTERPRISEADMIN");
/* 2998 */                                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2999 */                                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 3001 */                                     out.write("\n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >");
/*      */                                     
/* 3003 */                                     AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3004 */                                     _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 3005 */                                     _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                     
/* 3007 */                                     _jspx_th_am_005fadminlink_005f2.setHref(editlink);
/*      */                                     
/* 3009 */                                     _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 3010 */                                     int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 3011 */                                     if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 3012 */                                       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3013 */                                         out = _jspx_page_context.pushBody();
/* 3014 */                                         _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 3015 */                                         _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3018 */                                         out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 3019 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 3020 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3023 */                                       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3024 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3027 */                                     if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 3028 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                                     }
/*      */                                     
/* 3031 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 3032 */                                     out.write("</td>");
/* 3033 */                                     out.write(" \n\t\t");
/* 3034 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3035 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3039 */                                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3040 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 3043 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3044 */                                 out.write("\t\n        </tr>\n        \n        <tr> \n            <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >\n\t\t\t");
/*      */                                 
/* 3046 */                                 int mgResId = Integer.parseInt(request.getParameter("haid"));
/* 3047 */                                 if ((EnterpriseUtil.isAdminServer()) && (mgResId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE) && (request.isUserInRole("ENTERPRISEADMIN")))
/*      */                                 {
/*      */ 
/* 3050 */                                   out.write("\n\t\t\t\t<a href=\"javascript:applndelete()\" class=\"new-left-links\">");
/* 3051 */                                   out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3052 */                                   out.write("</a>\n\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3057 */                                   out.write(" \n\t\t");
/*      */                                   
/* 3059 */                                   AdminLink _jspx_th_am_005fadminlink_005f3 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3060 */                                   _jspx_th_am_005fadminlink_005f3.setPageContext(_jspx_page_context);
/* 3061 */                                   _jspx_th_am_005fadminlink_005f3.setParent(_jspx_th_c_005fif_005f0);
/*      */                                   
/* 3063 */                                   _jspx_th_am_005fadminlink_005f3.setHref("javascript:applndelete()");
/*      */                                   
/* 3065 */                                   _jspx_th_am_005fadminlink_005f3.setEnableClass("new-left-links");
/* 3066 */                                   int _jspx_eval_am_005fadminlink_005f3 = _jspx_th_am_005fadminlink_005f3.doStartTag();
/* 3067 */                                   if (_jspx_eval_am_005fadminlink_005f3 != 0) {
/* 3068 */                                     if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 3069 */                                       out = _jspx_page_context.pushBody();
/* 3070 */                                       _jspx_th_am_005fadminlink_005f3.setBodyContent((BodyContent)out);
/* 3071 */                                       _jspx_th_am_005fadminlink_005f3.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3074 */                                       out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3075 */                                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f3.doAfterBody();
/* 3076 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3079 */                                     if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 3080 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3083 */                                   if (_jspx_th_am_005fadminlink_005f3.doEndTag() == 5) {
/* 3084 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3); return;
/*      */                                   }
/*      */                                   
/* 3087 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3);
/* 3088 */                                   out.write(10);
/* 3089 */                                   out.write(9);
/* 3090 */                                   out.write(9);
/*      */                                 }
/* 3092 */                                 out.write("\n\t\t\t</td>\n        </tr>\n        \n\t\t\t");
/*      */                                 
/*      */ 
/* 3095 */                                 if (((EnterpriseUtil.isAdminServer()) && (mgResId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) || (!EnterpriseUtil.isAdminServer()))
/*      */                                 {
/*      */ 
/* 3098 */                                   out.write("\n\t        <tr> \n            <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >\n\t\t\t\t<a href=\"javascript:refreshstatus()\" class=\"new-left-links\">");
/* 3099 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.refresh.status.text"));
/* 3100 */                                   out.write("</a>&nbsp;<img  src=\"../images/icon_refresh.gif\" align=\"absmiddle\" border=\"0\"/>\n\t\t\t</td>\n\t\t\t </tr>\n\t\t\t");
/*      */                                 }
/* 3102 */                                 out.write("\n\t\t\n\t\t\t\n       \n\n        \n\n            </table>\n     ");
/* 3103 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3104 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3108 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3109 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                             }
/*      */                             
/* 3112 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3113 */                             out.write("\n     \n<script>\n\nfunction applndelete()\n{\n");
/*      */                             
/* 3115 */                             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3116 */                             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3117 */                             _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 3119 */                             _jspx_th_c_005fif_005f11.setTest("${MGtype=='1' }");
/* 3120 */                             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3121 */                             if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                               for (;;) {
/* 3123 */                                 out.write(" \n\tif(confirm('");
/* 3124 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertfordeletesub-group.text"));
/* 3125 */                                 out.write("')) {\n\tlocation.href = \"/manageApplications.do?method=delete&select=");
/* 3126 */                                 if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                   return;
/* 3128 */                                 out.write("\";\n\t}\n");
/* 3129 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3130 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3134 */                             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3135 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                             }
/*      */                             
/* 3138 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3139 */                             out.write("\n        \n");
/*      */                             
/* 3141 */                             IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3142 */                             _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3143 */                             _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 3145 */                             _jspx_th_c_005fif_005f12.setTest("${MGtype!='1' }");
/* 3146 */                             int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3147 */                             if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                               for (;;) {
/* 3149 */                                 out.write("         \n\tif(confirm('");
/* 3150 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertfordeletemg.text"));
/* 3151 */                                 out.write("'))\n\t{\n\t location.href = \"/manageApplications.do?method=delete&select=");
/* 3152 */                                 if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*      */                                   return;
/* 3154 */                                 out.write("\";\n\t}\n");
/* 3155 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3156 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3160 */                             if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3161 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                             }
/*      */                             
/* 3164 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3165 */                             out.write("\n} \nfunction refreshstatus()\n{\t\n\t location.href = \"/manageApplications.do?method=refreshNow&haid=");
/* 3166 */                             if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 3168 */                             out.write("\";\n}\n</script>\n");
/* 3169 */                             out.write("  \n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr> \n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 3170 */                             out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 3171 */                             out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 3172 */                             if (_jspx_meth_c_005fout_005f10(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 3174 */                             out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n  </tr>\n  <tr> \n    <td colspan=\"2\" class=\"quicknote\"> This page displays the overall status of the Business Application in a graphical view.</td>\n  </tr>\n</table>\n");
/* 3175 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3176 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3179 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3180 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3183 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3184 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 3187 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3188 */                         out.write(10);
/*      */                         
/* 3190 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3191 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3192 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 3194 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 3196 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 3197 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3198 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 3199 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3200 */                             out = _jspx_page_context.pushBody();
/* 3201 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 3202 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3205 */                             out.write("\n<html>\n<head>\n<title>SnapShot View of the Business Application</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n</head>\n\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
/*      */                             
/* 3207 */                             Hashtable MonitorsStatus = (Hashtable)request.getAttribute("MonitorsStatus");
/* 3208 */                             Properties MonitorsCount = (Properties)request.getAttribute("MonitorsCount");
/* 3209 */                             int total = 0;
/* 3210 */                             for (Enumeration e = MonitorsCount.keys(); e.hasMoreElements();)
/*      */                             {
/* 3212 */                               total += Integer.parseInt((String)MonitorsCount.get((String)e.nextElement()));
/*      */                             }
/* 3214 */                             String haid = request.getParameter("haid");
/*      */                             
/* 3216 */                             out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td class=\"monitorsheading\" height=\"22\">Snapshot View of <span class=\"names\">");
/* 3217 */                             if (_jspx_meth_c_005fout_005f11(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3219 */                             out.write("</span></td>\n  </tr>\n  <tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr> \n    <td colspan=\"3\" class=\"tableheadingbborder\">Current Status of Monitors</td>\n  </tr>\n  <tr align=\"center\"> \n    <td class=\"columnheading\">Custom Monitor \n      ");
/*      */                             
/* 3221 */                             if (MonitorsCount.containsKey("CAM"))
/*      */                             {
/*      */ 
/* 3224 */                               out.write("\n      (");
/* 3225 */                               out.print(MonitorsCount.getProperty("CAM"));
/* 3226 */                               out.write(") \n      ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 3232 */                               out.write("\n      (0) \n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 3236 */                             out.write("\n    </td>\n    <td height=\"39\" class=\"columnheading\">Http URL Monitors \n      ");
/*      */                             
/* 3238 */                             if (MonitorsCount.containsKey("URL"))
/*      */                             {
/*      */ 
/* 3241 */                               out.write("\n      (");
/* 3242 */                               out.print(MonitorsCount.getProperty("URL"));
/* 3243 */                               out.write(") \n      ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 3249 */                               out.write("\n      (0) \n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 3253 */                             out.write("\n    </td>\n    <td width=\"34%\" class=\"columnheading\">Application Servers \n      ");
/*      */                             
/* 3255 */                             if (MonitorsCount.containsKey("APP"))
/*      */                             {
/*      */ 
/* 3258 */                               out.write("\n      (");
/* 3259 */                               out.print(MonitorsCount.getProperty("APP"));
/* 3260 */                               out.write(") \n      ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 3266 */                               out.write("\n      (0) \n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 3270 */                             out.write("\n    </td>\n  </tr><tr><td height=\"120\">\n  <table width=\"75%\" border=\"0\" align=\"center\" height=\"100\" cellpadding=\"5\" cellspacing=\"0\" class=\"grayfullborder\">\n    <tr align=\"center\"> \n      ");
/*      */                             
/* 3272 */                             if (!MonitorsCount.containsKey("CAM"))
/*      */                             {
/*      */ 
/* 3275 */                               out.write("\n      <td colspan=\"3\"  class=\"yellowgrayborder\"><img src=\"../images/img_cam_gray.gif\"  border=\"0\" vspace=\"0\" ></td>\n    </tr>\n    ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 3280 */                               Properties p = (Properties)MonitorsStatus.get("CAM");
/*      */                               
/* 3282 */                               out.write("\n    <td colspan=\"3\"><a href=\"/showapplication.do?haid=");
/* 3283 */                               out.print(haid);
/* 3284 */                               out.write("&method=showApplication#Custom Application Management\"><img src=\"../images/img_cam.gif\" width=\"43\" height=\"29\" border=\"0\" vspace=\"0\"></a></td>\n    </tr>\n    <tr> \n      <td width=\"44%\" class=\"yellowgrayborder\">Availability :</td>\n      <td width=\"10%\" class=\"yellowgrayborder\">");
/* 3285 */                               out.print(getSeverityImageForAvailability(Integer.parseInt(p.getProperty("AvailStatus"))));
/* 3286 */                               out.write("</td>\n      <td width=\"39%\" height=\"18\" class=\"yellowgrayborder\" valign=\"top\"> \n        ");
/*      */                               
/* 3288 */                               if (p.getProperty("NotAvailable").equals("0"))
/*      */                               {
/*      */ 
/* 3291 */                                 out.write("\n        (");
/* 3292 */                                 out.print(MonitorsCount.getProperty("CAM"));
/* 3293 */                                 out.write(47);
/* 3294 */                                 out.print(MonitorsCount.getProperty("CAM"));
/* 3295 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3301 */                                 out.write("\n    (");
/* 3302 */                                 out.print(p.getProperty("NotAvailable"));
/* 3303 */                                 out.write(47);
/* 3304 */                                 out.print(MonitorsCount.getProperty("CAM"));
/* 3305 */                                 out.write(")</td> \n    ");
/*      */                               }
/*      */                               
/*      */ 
/* 3309 */                               out.write("</td></tr>\n    <tr> \n      <td class=\"yellowgrayborder\">Health :</td>\n      <td class=\"yellowgrayborder\">");
/* 3310 */                               out.print(getSeverityImage(Integer.parseInt(p.getProperty("HealthStatus"))));
/* 3311 */                               out.write("</td>\n      <td height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3313 */                               if (p.getProperty("HealthDown").equals("0"))
/*      */                               {
/*      */ 
/* 3316 */                                 out.write("\n        (");
/* 3317 */                                 out.print(MonitorsCount.getProperty("CAM"));
/* 3318 */                                 out.write(47);
/* 3319 */                                 out.print(MonitorsCount.getProperty("CAM"));
/* 3320 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3326 */                                 out.write("\n    (");
/* 3327 */                                 out.print(p.getProperty("HealthDown"));
/* 3328 */                                 out.write(47);
/* 3329 */                                 out.print(MonitorsCount.getProperty("CAM"));
/* 3330 */                                 out.write(")</td> </tr>\n    ");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 3335 */                             out.write("</td></tr>\n  </table></td><td  height=\"120\">\n  <table width=\"65%\" align=\"center\" height=\"100\"  cellpadding=\"5\" cellspacing=\"0\" class=\"grayfullborder\">\n    <tr align=\"center\"> \n      ");
/*      */                             
/* 3337 */                             if (!MonitorsCount.containsKey("URL"))
/*      */                             {
/*      */ 
/* 3340 */                               out.write("\n      <td colspan=\"3\" class=\"yellowgrayborder\"><img src=\"../images/img_url_gray.gif\" border=\"0\"></td>\n    </tr>\n    ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 3345 */                               Properties p = (Properties)MonitorsStatus.get("URL");
/*      */                               
/* 3347 */                               out.write("\n    <td colspan=\"3\" ><a href=\"/showapplication.do?haid=");
/* 3348 */                               out.print(haid);
/* 3349 */                               out.write("&method=showApplication#HTTP-URLs Monitored\"><img src=\"../images/img_url.gif\" width=\"43\" height=\"29\" border=\"0\"></a></td>\n    </tr>\n    <tr> \n      <td width=\"44%\" class=\"yellowgrayborder\">Availability :</td>\n      <td width=\"17%\" class=\"yellowgrayborder\">");
/* 3350 */                               out.print(getSeverityImageForAvailability(Integer.parseInt(p.getProperty("AvailStatus"))));
/* 3351 */                               out.write("</td>\n      <td width=\"39%\" height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3353 */                               if (p.getProperty("NotAvailable").equals("0"))
/*      */                               {
/*      */ 
/* 3356 */                                 out.write("\n        (");
/* 3357 */                                 out.print(MonitorsCount.getProperty("URL"));
/* 3358 */                                 out.write(47);
/* 3359 */                                 out.print(MonitorsCount.getProperty("URL"));
/* 3360 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3366 */                                 out.write("\n    (");
/* 3367 */                                 out.print(p.getProperty("NotAvailable"));
/* 3368 */                                 out.write(47);
/* 3369 */                                 out.print(MonitorsCount.getProperty("URL"));
/* 3370 */                                 out.write(")</td> \n    ");
/*      */                               }
/*      */                               
/*      */ 
/* 3374 */                               out.write("</td></tr>\n    <tr> \n      <td class=\"yellowgrayborder\">Health :</td>\n      <td class=\"yellowgrayborder\">");
/* 3375 */                               out.print(getSeverityImage(Integer.parseInt(p.getProperty("HealthStatus"))));
/* 3376 */                               out.write("</td>\n      <td height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3378 */                               if (p.getProperty("HealthDown").equals("0"))
/*      */                               {
/*      */ 
/* 3381 */                                 out.write("\n        (");
/* 3382 */                                 out.print(MonitorsCount.getProperty("URL"));
/* 3383 */                                 out.write(47);
/* 3384 */                                 out.print(MonitorsCount.getProperty("URL"));
/* 3385 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3391 */                                 out.write("\n    (");
/* 3392 */                                 out.print(p.getProperty("HealthDown"));
/* 3393 */                                 out.write(47);
/* 3394 */                                 out.print(MonitorsCount.getProperty("URL"));
/* 3395 */                                 out.write(")</td> </tr>\n    ");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 3400 */                             out.write("</td></tr>\n  </table></td><td  height=\"120\">\n  <table width=\"70%\" height=\"100\" border=\"0\" align=\"center\" cellpadding=\"5\" cellspacing=\"0\" class=\"grayfullborder\">\n    <tr align=\"center\"> \n      ");
/*      */                             
/* 3402 */                             if (!MonitorsCount.containsKey("APP"))
/*      */                             {
/*      */ 
/* 3405 */                               out.write("\n      <td colspan=\"3\" class=\"yellowgrayborder\"><img src=\"../images/img_applicationservers_gray.gif\"  border=\"0\"></td>\n    </tr>\n    ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 3410 */                               Properties p = (Properties)MonitorsStatus.get("APP");
/*      */                               
/* 3412 */                               out.write("\n    <td colspan=\"3\"><a href=\"/showapplication.do?haid=");
/* 3413 */                               out.print(haid);
/* 3414 */                               out.write("&method=showApplication#Application Servers\"><img src=\"../images/img_applicationservers.gif\" width=\"48\" height=\"46\" border=\"0\"></a></td>\n    </tr>\n    <tr> \n      <td width=\"44%\" class=\"yellowgrayborder\">Availability :</td>\n      <td width=\"10%\" class=\"yellowgrayborder\">");
/* 3415 */                               out.print(getSeverityImageForAvailability(Integer.parseInt(p.getProperty("AvailStatus"))));
/* 3416 */                               out.write("</td>\n      <td width=\"39%\" height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3418 */                               if (p.getProperty("NotAvailable").equals("0"))
/*      */                               {
/*      */ 
/* 3421 */                                 out.write("\n        (");
/* 3422 */                                 out.print(MonitorsCount.getProperty("APP"));
/* 3423 */                                 out.write(47);
/* 3424 */                                 out.print(MonitorsCount.getProperty("APP"));
/* 3425 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3431 */                                 out.write("\n    (");
/* 3432 */                                 out.print(p.getProperty("NotAvailable"));
/* 3433 */                                 out.write(47);
/* 3434 */                                 out.print(MonitorsCount.getProperty("APP"));
/* 3435 */                                 out.write(")</td> \n    ");
/*      */                               }
/*      */                               
/*      */ 
/* 3439 */                               out.write("</tr>\n    <tr> \n      <td class=\"yellowgrayborder\">Health :</td>\n      <td class=\"yellowgrayborder\">");
/* 3440 */                               out.print(getSeverityImage(Integer.parseInt(p.getProperty("HealthStatus"))));
/* 3441 */                               out.write("</td>\n      <td height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3443 */                               if (p.getProperty("HealthDown").equals("0"))
/*      */                               {
/*      */ 
/* 3446 */                                 out.write("\n        (");
/* 3447 */                                 out.print(MonitorsCount.getProperty("APP"));
/* 3448 */                                 out.write(47);
/* 3449 */                                 out.print(MonitorsCount.getProperty("APP"));
/* 3450 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3456 */                                 out.write("\n    (");
/* 3457 */                                 out.print(p.getProperty("HealthDown"));
/* 3458 */                                 out.write(47);
/* 3459 */                                 out.print(MonitorsCount.getProperty("APP"));
/* 3460 */                                 out.write(")</td> </tr>\n    ");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 3465 */                             out.write("\n  </table>\n  </tr>\n  <tr align=\"center\" > \n    <td width=\"31%\" class=\"columnheadingtb\">Database Servers \n      ");
/*      */                             
/* 3467 */                             if (MonitorsCount.containsKey("DBS"))
/*      */                             {
/*      */ 
/* 3470 */                               out.write("\n      (");
/* 3471 */                               out.print(MonitorsCount.getProperty("DBS"));
/* 3472 */                               out.write(") \n      ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 3478 */                               out.write("\n      (0) \n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 3482 */                             out.write("\n    </td>\n    <td class=\"columnheadingtb\" >Services \n      ");
/*      */                             
/* 3484 */                             if (MonitorsCount.containsKey("SER"))
/*      */                             {
/*      */ 
/* 3487 */                               out.write("\n      (");
/* 3488 */                               out.print(MonitorsCount.getProperty("SER"));
/* 3489 */                               out.write(") \n      ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 3495 */                               out.write("\n      (0) \n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 3499 */                             out.write("\n    </td>\n    <td width=\"35%\" height=\"18\" class=\"columnheadingtb\">System \n      ");
/*      */                             
/* 3501 */                             if (MonitorsCount.containsKey("SYS"))
/*      */                             {
/*      */ 
/* 3504 */                               out.write("\n      (");
/* 3505 */                               out.print(MonitorsCount.getProperty("SYS"));
/* 3506 */                               out.write(") \n      ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 3512 */                               out.write("\n      (0) \n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 3516 */                             out.write("\n    </td>\n  </tr><tr><td height=\"120\">\n  <table width=\"75%\" border=\"0\" align=\"center\"  height=\"100\" cellpadding=\"5\" cellspacing=\"0\" class=\"grayfullborder\">\n    <tr align=\"center\"> \n      ");
/*      */                             
/* 3518 */                             if (!MonitorsCount.containsKey("DBS"))
/*      */                             {
/*      */ 
/* 3521 */                               out.write("\n      <td colspan=\"3\" class=\"yellowgrayborder\"><img src=\"../images/img_databaseservers_gray.gif\"  border=\"0\"></td>\n    </tr>\n    ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 3526 */                               Properties p = (Properties)MonitorsStatus.get("DBS");
/*      */                               
/* 3528 */                               out.write("\n    <td colspan=\"3\"><a href=\"/showapplication.do?haid=");
/* 3529 */                               out.print(haid);
/* 3530 */                               out.write("&method=showApplication#Database Server\"><img src=\"../images/img_databaseservers.gif\" width=\"48\" height=\"46\" border=\"0\"></a></td>\n    </tr>\n    <tr> \n      <td width=\"44%\" class=\"yellowgrayborder\">Availability :</td>\n      <td width=\"17%\" class=\"yellowgrayborder\">");
/* 3531 */                               out.print(getSeverityImageForAvailability(Integer.parseInt(p.getProperty("AvailStatus"))));
/* 3532 */                               out.write("</td>\n      <td width=\"39%\" height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3534 */                               if (p.getProperty("NotAvailable").equals("0"))
/*      */                               {
/*      */ 
/* 3537 */                                 out.write("\n        (");
/* 3538 */                                 out.print(MonitorsCount.getProperty("DBS"));
/* 3539 */                                 out.write(47);
/* 3540 */                                 out.print(MonitorsCount.getProperty("DBS"));
/* 3541 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3547 */                                 out.write("\n    (");
/* 3548 */                                 out.print(p.getProperty("NotAvailable"));
/* 3549 */                                 out.write(47);
/* 3550 */                                 out.print(MonitorsCount.getProperty("DBS"));
/* 3551 */                                 out.write(")</td></tr> \n    ");
/*      */                               }
/*      */                               
/*      */ 
/* 3555 */                               out.write("\n    <tr> \n      <td class=\"yellowgrayborder\">Health :</td>\n      <td class=\"yellowgrayborder\">");
/* 3556 */                               out.print(getSeverityImage(Integer.parseInt(p.getProperty("HealthStatus"))));
/* 3557 */                               out.write("</td>\n      <td height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3559 */                               if (p.getProperty("HealthDown").equals("0"))
/*      */                               {
/*      */ 
/* 3562 */                                 out.write("\n        (");
/* 3563 */                                 out.print(MonitorsCount.getProperty("DBS"));
/* 3564 */                                 out.write(47);
/* 3565 */                                 out.print(MonitorsCount.getProperty("DBS"));
/* 3566 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3572 */                                 out.write("\n    (");
/* 3573 */                                 out.print(p.getProperty("HealthDown"));
/* 3574 */                                 out.write(47);
/* 3575 */                                 out.print(MonitorsCount.getProperty("DBS"));
/* 3576 */                                 out.write(")</td> </tr>\n    ");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 3581 */                             out.write("</td></tr>\n  </table></td><td>\n  <table width=\"70%\"  border=\"0\" align=\"center\" height=\"100\" cellpadding=\"5\" cellspacing=\"0\" class=\"grayfullborder\">\n    <tr align=\"center\"> \n      ");
/*      */                             
/* 3583 */                             if (!MonitorsCount.containsKey("SER"))
/*      */                             {
/*      */ 
/* 3586 */                               out.write("\n      <td colspan=\"3\" class=\"yellowgrayborder\"><img src=\"../images/img_services_gray.gif\"  border=\"0\"></td>\n    </tr>\n    ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 3591 */                               Properties p = (Properties)MonitorsStatus.get("SER");
/*      */                               
/* 3593 */                               out.write("\n    <td colspan=\"3\"><a href=\"/showapplication.do?haid=");
/* 3594 */                               out.print(haid);
/* 3595 */                               out.write("&method=showApplication#Services\"><img src=\"../images/img_services.gif\" width=\"43\" height=\"29\" border=\"0\"></a></td>\n    </tr>\n    <tr> \n      <td width=\"44%\" class=\"yellowgrayborder\">Availability :</td>\n      <td width=\"10%\" class=\"yellowgrayborder\">");
/* 3596 */                               out.print(getSeverityImageForAvailability(Integer.parseInt(p.getProperty("AvailStatus"))));
/* 3597 */                               out.write("</td>\n      <td width=\"39%\" height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3599 */                               if (p.getProperty("NotAvailable").equals("0"))
/*      */                               {
/*      */ 
/* 3602 */                                 out.write("\n        (");
/* 3603 */                                 out.print(MonitorsCount.getProperty("SER"));
/* 3604 */                                 out.write(47);
/* 3605 */                                 out.print(MonitorsCount.getProperty("SER"));
/* 3606 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3612 */                                 out.write("\n    (");
/* 3613 */                                 out.print(p.getProperty("NotAvailable"));
/* 3614 */                                 out.write(47);
/* 3615 */                                 out.print(MonitorsCount.getProperty("SER"));
/* 3616 */                                 out.write(")</td> \n    ");
/*      */                               }
/*      */                               
/*      */ 
/* 3620 */                               out.write("</td></tr>\n    <tr> \n      <td class=\"yellowgrayborder\">Health :</td>\n      <td class=\"yellowgrayborder\">");
/* 3621 */                               out.print(getSeverityImage(Integer.parseInt(p.getProperty("HealthStatus"))));
/* 3622 */                               out.write("</td>\n      <td height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3624 */                               if (p.getProperty("HealthDown").equals("0"))
/*      */                               {
/*      */ 
/* 3627 */                                 out.write("\n        (");
/* 3628 */                                 out.print(MonitorsCount.getProperty("SER"));
/* 3629 */                                 out.write(47);
/* 3630 */                                 out.print(MonitorsCount.getProperty("SER"));
/* 3631 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3637 */                                 out.write("\n    (");
/* 3638 */                                 out.print(p.getProperty("HealthDown"));
/* 3639 */                                 out.write(47);
/* 3640 */                                 out.print(MonitorsCount.getProperty("SER"));
/* 3641 */                                 out.write(")</td> </tr>\n    ");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 3646 */                             out.write("</td></tr>\n  </table></td><td height=\"84\">\n  <table width=\"65%\" border=\"0\" align=\"center\"  cellpadding=\"5\" height=\"100\" cellspacing=\"0\" class=\"grayfullborder\">\n    <tr align=\"center\"> \n      ");
/*      */                             
/* 3648 */                             if (!MonitorsCount.containsKey("SYS"))
/*      */                             {
/*      */ 
/* 3651 */                               out.write("\n      <td colspan=\"3\" class=\"yellowgrayborder\"><img src=\"../images/img_system_gray.gif\"  border=\"0\"></td>\n    </tr>\n    ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 3656 */                               Properties p = (Properties)MonitorsStatus.get("SYS");
/*      */                               
/* 3658 */                               out.write("\n    <td colspan=\"3\"><a href=\"/showapplication.do?haid=");
/* 3659 */                               out.print(haid);
/* 3660 */                               out.write("&method=showApplication#System\"><img src=\"../images/img_system.gif\" width=\"48\" height=\"46\" border=\"0\"></a></td>\n    </tr>\n    <tr> \n      <td width=\"44%\" class=\"yellowgrayborder\">Availability :</td>\n      <td width=\"10%\" class=\"yellowgrayborder\">");
/* 3661 */                               out.print(getSeverityImageForAvailability(Integer.parseInt(p.getProperty("AvailStatus"))));
/* 3662 */                               out.write("</td>\n      <td width=\"39%\" height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3664 */                               if (p.getProperty("NotAvailable").equals("0"))
/*      */                               {
/*      */ 
/* 3667 */                                 out.write("\n        (");
/* 3668 */                                 out.print(MonitorsCount.getProperty("SYS"));
/* 3669 */                                 out.write(47);
/* 3670 */                                 out.print(MonitorsCount.getProperty("SYS"));
/* 3671 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3677 */                                 out.write("\n    (");
/* 3678 */                                 out.print(p.getProperty("NotAvailable"));
/* 3679 */                                 out.write(47);
/* 3680 */                                 out.print(MonitorsCount.getProperty("SYS"));
/* 3681 */                                 out.write(")</td> \n    ");
/*      */                               }
/*      */                               
/*      */ 
/* 3685 */                               out.write("</tr>\n    <tr> \n      <td class=\"yellowgrayborder\">Health :</td>\n      <td class=\"yellowgrayborder\">");
/* 3686 */                               out.print(getSeverityImage(Integer.parseInt(p.getProperty("HealthStatus"))));
/* 3687 */                               out.write("</td>\n      <td height=\"18\" class=\"yellowgrayborder\"> \n        ");
/*      */                               
/* 3689 */                               if (p.getProperty("HealthDown").equals("0"))
/*      */                               {
/*      */ 
/* 3692 */                                 out.write("\n        (");
/* 3693 */                                 out.print(MonitorsCount.getProperty("SYS"));
/* 3694 */                                 out.write(47);
/* 3695 */                                 out.print(MonitorsCount.getProperty("SYS"));
/* 3696 */                                 out.write(")</td>\n    </tr>\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3702 */                                 out.write("\n    (");
/* 3703 */                                 out.print(p.getProperty("HealthDown"));
/* 3704 */                                 out.write(47);
/* 3705 */                                 out.print(MonitorsCount.getProperty("SYS"));
/* 3706 */                                 out.write(")</td> </tr>\n    ");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 3711 */                             out.write("\n  </table>\n  </tr>\n</table>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr> \n    <td class=\"tableheadingbborder\">Application Information</td>\n    <td class=\"tableheadingbborder\">Alert Details</td>\n  </tr>\n  <tr> \n    <td width=\"56%\" height=\"61\" valign=\"top\" class=\"rborder\"> \n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr> \n          <td width=\"29%\" class=\"whitegrayborderbr\">Application Name<br></td>\n          <td width=\"71%\" class=\"whitegrayborder\">&nbsp;");
/* 3712 */                             if (_jspx_meth_c_005fout_005f12(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3714 */                             out.write("</td>\n        </tr>\n        <tr> \n          <td class=\"yellowgrayborderbr\">Current Availability</td>\n          <td class=\"yellowgrayborder\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3715 */                             out.print(request.getParameter("haid"));
/* 3716 */                             out.write("&attributeid=17')\">&nbsp;");
/* 3717 */                             out.print(getSeverityImageForAvailability(alert.getProperty(request.getParameter("haid") + "#" + "17")));
/* 3718 */                             out.write("</a></td>\n        </tr>\n        <tr> \n          <td width=\"29%\" class=\"whitegrayborderbr\">Current Health<br></td>\n          <td width=\"71%\" class=\"whitegrayborder\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3719 */                             out.print(request.getParameter("haid"));
/* 3720 */                             out.write("&attributeid=18')\">&nbsp;");
/* 3721 */                             out.print(getSeverityImage(alert.getProperty(request.getParameter("haid") + "#" + "18")));
/* 3722 */                             out.write("</a></td>\n        </tr>\n        <tr> \n          <td class=\"yellowgrayborderbr\">Total Alerts<br></td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:showApplicationAlarms('");
/* 3723 */                             out.print(com.adventnet.appmanager.struts.beans.AlarmUtil.getApplicationAlertsForHA(request.getParameter("haid"), "All"));
/* 3724 */                             out.write(39);
/* 3725 */                             out.write(44);
/* 3726 */                             out.write(39);
/* 3727 */                             out.print(request.getParameter("haid"));
/* 3728 */                             out.write("')\" class=\"resourcename\">");
/* 3729 */                             out.print(com.adventnet.appmanager.struts.beans.AlarmUtil.getApplicationAlertsForHA(request.getParameter("haid"), "All"));
/* 3730 */                             out.write("</a></td>\n        </tr>\n        <tr> \n          <td class=\"whitegrayborderbr\">No. of Monitors</td>\n          <td class=\"whitegrayborder\">&nbsp;");
/* 3731 */                             out.print(total);
/* 3732 */                             out.write("</td>\n        </tr>\n        <tr> \n          <td class=\"yellowgrayborderbr\">Last Modified on</td>\n          <td class=\"yellowgrayborder\">&nbsp;");
/* 3733 */                             if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3735 */                             out.write("</td>\n        </tr>\n\t\t ");
/*      */                             
/* 3737 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3738 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3739 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3741 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 3742 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3743 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 3745 */                                 out.write("\n        <tr> \n          <td class=\"whitegrayborderbr\">Last Polled at</td>\n          <td class=\"whitegrayborder\">&nbsp;");
/* 3746 */                                 out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3747 */                                 out.write("</td>\n        </tr>\n        <tr> \n          <td class=\"yellowgrayborderbr\">Next Poll at</td>\n          <td class=\"yellowgrayborder\">&nbsp;");
/* 3748 */                                 out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3749 */                                 out.write("</td>\n        </tr>\n\t\t");
/* 3750 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3751 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3755 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3756 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 3759 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3760 */                             out.write("\n      </table>\n    </td>\n    <td width=\"44%\" align=\"center\"> \n      ");
/*      */                             
/*      */ 
/* 3763 */                             if (com.adventnet.appmanager.struts.beans.AlarmUtil.getApplicationAlertsForHA(request.getParameter("haid"), "All") == 0)
/*      */                             {
/*      */ 
/* 3766 */                               out.write("\n      <span class=\"bodytext\">No Alerts Available</span> \n      ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 3771 */                               alertgraph.sethaid(request.getParameter("haid"));
/*      */                               
/* 3773 */                               out.write(10);
/*      */                               
/* 3775 */                               BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 3776 */                               _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 3777 */                               _jspx_th_awolf_005fbarchart_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 3779 */                               _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("alertgraph");
/*      */                               
/* 3781 */                               _jspx_th_awolf_005fbarchart_005f0.setWidth("300");
/*      */                               
/* 3783 */                               _jspx_th_awolf_005fbarchart_005f0.setHeight("157");
/*      */                               
/* 3785 */                               _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */                               
/* 3787 */                               _jspx_th_awolf_005fbarchart_005f0.setUrl(false);
/*      */                               
/* 3789 */                               _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel("Severity");
/*      */                               
/* 3791 */                               _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel("Count");
/* 3792 */                               int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 3793 */                               if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 3794 */                                 if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 3795 */                                   out = _jspx_page_context.pushBody();
/* 3796 */                                   _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 3797 */                                   _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3800 */                                   out.write(10);
/*      */                                   
/* 3802 */                                   Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3803 */                                   _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3804 */                                   _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fbarchart_005f0);
/*      */                                   
/* 3806 */                                   _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3807 */                                   int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3808 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3809 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3810 */                                       out = _jspx_page_context.pushBody();
/* 3811 */                                       _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3812 */                                       _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3815 */                                       out.write(9);
/* 3816 */                                       out.write(10);
/*      */                                       
/* 3818 */                                       AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3819 */                                       _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3820 */                                       _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 3822 */                                       _jspx_th_awolf_005fparam_005f0.setName("0");
/*      */                                       
/* 3824 */                                       _jspx_th_awolf_005fparam_005f0.setValue(critical);
/* 3825 */                                       int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3826 */                                       if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3827 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                       }
/*      */                                       
/* 3830 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3831 */                                       out.write(32);
/* 3832 */                                       out.write(10);
/*      */                                       
/* 3834 */                                       AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3835 */                                       _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3836 */                                       _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 3838 */                                       _jspx_th_awolf_005fparam_005f1.setName("1");
/*      */                                       
/* 3840 */                                       _jspx_th_awolf_005fparam_005f1.setValue(warning);
/* 3841 */                                       int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3842 */                                       if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3843 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                       }
/*      */                                       
/* 3846 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3847 */                                       out.write("      \t\n");
/*      */                                       
/* 3849 */                                       AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3850 */                                       _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 3851 */                                       _jspx_th_awolf_005fparam_005f2.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 3853 */                                       _jspx_th_awolf_005fparam_005f2.setName("2");
/*      */                                       
/* 3855 */                                       _jspx_th_awolf_005fparam_005f2.setValue(clear);
/* 3856 */                                       int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 3857 */                                       if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 3858 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2); return;
/*      */                                       }
/*      */                                       
/* 3861 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 3862 */                                       out.write(32);
/* 3863 */                                       out.write(10);
/* 3864 */                                       int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3865 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3868 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3869 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3872 */                                   if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3873 */                                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                   }
/*      */                                   
/* 3876 */                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3877 */                                   out.write(10);
/* 3878 */                                   int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 3879 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3882 */                                 if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 3883 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3886 */                               if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 3887 */                                 this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*      */                               }
/*      */                               
/* 3890 */                               this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 3891 */                               out.write("\t\t\n\n       \n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 3895 */                             out.write("\n    </td>\n  </tr>\n</table>\n\n  ");
/* 3896 */                             out.write("<!--$Id$-->\n\n\n\n\n\n<script language=\"JavaScript1.2\" src=\"/webclient/fault/js/fault.js\"></script>\n");
/* 3897 */                             if (_jspx_meth_c_005fif_005f13(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3899 */                             out.write(10);
/*      */                             
/* 3901 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3902 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3903 */                             _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3905 */                             _jspx_th_c_005fif_005f14.setTest("${param.method !='showApplications'}");
/* 3906 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3907 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/* 3909 */                                 out.write("\n<script>\nfunction hideAllalarmPages()\n{\ndocument.getElementById(\"recentalarms\").style.display='none';\ndocument.getElementById(\"recentevents\").style.display='none';\ndocument.getElementById(\"recentdowntimes\").style.display='none';\n}\nfunction getRecentEvents(resourceid)\n{\nhideAllalarmPages();\ndocument.getElementById(\"recentevents\").style.display=\"inline\";\nvar url = \"/showapplication.do?method=recentEvents&resourceid=\"+resourceid;\nvar ajax1=getHTTPObject();\najax1.onreadystatechange =function () { setEventContent(ajax1);} ;//No I18N\najax1.open(\"GET\", url, true);//No I18N\najax1.send(null);\n}\nfunction getRecentAlarms(resourceid)\n{\nhideAllalarmPages();\ndocument.getElementById(\"recentalarms\").style.display='inline';\ndocument.getElementById(\"loadingg\").style.display=\"none\";\n}\nfunction setEventContent(ajax1)\n{\n        if(ajax1.readyState == 4)\n        {\n           if( ajax1.status == 200)\n           {\n               document.getElementById(\"recentevents\").innerHTML = ajax1.responseText;\n\t\tdocument.getElementById(\"recentevents\").style.display='inline';\n");
/* 3910 */                                 out.write("                document.getElementById(\"loadingg\").style.display=\"none\";\n           }\n        }\n\n\n}\nfunction getRecentDowntimes(resourceid)\n{\n        hideAllalarmPages();\n        var url = \"/showapplication.do?method=recentdownTimes&resourceid=\"+resourceid;\n\t$('#recentdowntimes').load(url, function() {//No I18N\n\tdocument.getElementById(\"recentdowntimes\").style.display=\"inline\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n\t});\n}\n\n</script>\n");
/* 3911 */                                 request.setAttribute("fullpercent", "true");
/* 3912 */                                 out.write(10);
/* 3913 */                                 JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.common.recentalerts.text,am.webclient.common.eventsummary.text,am.webclient.common.recentdowntimes.text", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.common.recentalerts.text,am.webclient.common.eventsummary.text,am.webclient.common.recentdowntimes.text", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getRecentAlarms,getRecentEvents,getRecentDowntimes", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.common.recentalerts.text", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(request.getParameter("haid")), request.getCharacterEncoding()), out, true);
/* 3914 */                                 out.write("\n<table><tr><td height=\"7\"></td></tr></table>\n<div id=\"recentevents\" style=\"dsplay:none\">\n</div>\n<div id=\"recentdowntimes\" style=\"dsplay:none\">\n</div>\n");
/* 3915 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3916 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3920 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3921 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                             }
/*      */                             
/* 3924 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3925 */                             out.write("\n\n<div id=\"recentalarms\">\n");
/*      */                             
/* 3927 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3928 */                             _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3929 */                             _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3931 */                             _jspx_th_logic_005fnotEmpty_005f1.setName("recent5Alarms");
/* 3932 */                             int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3933 */                             if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                               for (;;) {
/* 3935 */                                 out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  align=\"left\" class=\"lrtbdarkborder\">\n<tr >\n ");
/*      */                                 
/* 3937 */                                 ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3938 */                                 _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3939 */                                 _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/* 3940 */                                 int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3941 */                                 if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                   for (;;)
/*      */                                   {
/* 3944 */                                     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3945 */                                     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3946 */                                     _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                     
/* 3948 */                                     _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showApplications'}");
/* 3949 */                                     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3950 */                                     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                       for (;;) {
/* 3952 */                                         out.write("\n<td height=\"26\" colspan=\"6\" class=\"dragndroptblheading\" title=\"");
/* 3953 */                                         out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 3954 */                                         out.write(34);
/* 3955 */                                         out.write(62);
/* 3956 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.title"));
/* 3957 */                                         out.write("</td>\n");
/* 3958 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3959 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3963 */                                     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3964 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                     }
/*      */                                     
/* 3967 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3968 */                                     out.write(10);
/*      */                                     
/* 3970 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3971 */                                     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3972 */                                     _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 3973 */                                     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3974 */                                     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                       for (;;) {
/* 3976 */                                         out.write("\n<td height=\"26\" colspan=\"6\" class=\"tableheadingbborder\">");
/* 3977 */                                         out.print(FormatUtil.getString("am.webclient.alerttab.recentcrictical.text"));
/* 3978 */                                         out.write("</td>\n");
/* 3979 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3980 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3984 */                                     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3985 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                     }
/*      */                                     
/* 3988 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3989 */                                     out.write(10);
/* 3990 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3991 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3995 */                                 if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3996 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                 }
/*      */                                 
/* 3999 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4000 */                                 out.write("\n</tr>\n\n  <TR >\n\n   <TD width=\"28%\"  class=\"columnheading\" > ");
/* 4001 */                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 4002 */                                 out.write(" </TD>\n    <TD width=\"10%\"  class=\"columnheading\"> ");
/* 4003 */                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.type"));
/* 4004 */                                 out.write(" </TD>\n     <TD  width=\"7%\" align=\"left\"  class=\"columnheading\" > ");
/* 4005 */                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 4006 */                                 out.write(" </TD>\n    <TD  width=\"23%\" class=\"columnheading\"> ");
/* 4007 */                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 4008 */                                 out.write("</TD>\n    <td   width=\"17%\" class=\"columnheading\" >");
/* 4009 */                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/* 4010 */                                 out.write(" </TD >\n    <td   width=\"15%\" class=\"columnheading\" > ");
/* 4011 */                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.technician"));
/* 4012 */                                 out.write("</TD >\n  </TR>\n\n");
/* 4013 */                                 int j = 0;
/* 4014 */                                 out.write("\n   ");
/*      */                                 
/* 4016 */                                 HashMap extDeviceMap = null;
/* 4017 */                                 if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*      */                                 {
/* 4019 */                                   extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink(false);
/*      */                                 }
/* 4021 */                                 HashMap<String, String> trimmedInfo = (HashMap)request.getAttribute("trimmedChildMonitorInfo");
/*      */                                 
/* 4023 */                                 out.write("\n\n  ");
/*      */                                 
/* 4025 */                                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.get(IterateTag.class);
/* 4026 */                                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 4027 */                                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                 
/* 4029 */                                 _jspx_th_logic_005fiterate_005f0.setName("recent5Alarms");
/*      */                                 
/* 4031 */                                 _jspx_th_logic_005fiterate_005f0.setId("recentAlarm");
/*      */                                 
/* 4033 */                                 _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 4034 */                                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 4035 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 4036 */                                   ArrayList recentAlarm = null;
/* 4037 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4038 */                                     out = _jspx_page_context.pushBody();
/* 4039 */                                     _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 4040 */                                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                   }
/* 4042 */                                   recentAlarm = (ArrayList)_jspx_page_context.findAttribute("recentAlarm");
/*      */                                   for (;;) {
/* 4044 */                                     out.write(10);
/* 4045 */                                     out.write(32);
/* 4046 */                                     out.write(32);
/*      */                                     
/* 4048 */                                     String alarm = (String)recentAlarm.get(1);
/* 4049 */                                     if ((alarm.trim().equals("1")) || (alarm.trim().equals("4")))
/*      */                                     {
/* 4051 */                                       j++;
/* 4052 */                                       String wsSeverity = (String)recentAlarm.get(0);
/* 4053 */                                       String category = (String)recentAlarm.get(6);
/* 4054 */                                       String sourcetype = (String)recentAlarm.get(7);
/* 4055 */                                       String annotation = (String)recentAlarm.get(10);
/* 4056 */                                       String mmessage = (String)recentAlarm.get(3);
/* 4057 */                                       mmessage = com.adventnet.appmanager.util.ExtProdUtil.decodeString(mmessage);
/* 4058 */                                       mmessage = mmessage.replaceAll("\"", "&quot;");
/*      */                                       
/* 4060 */                                       String sourcename = (String)recentAlarm.get(4);
/* 4061 */                                       sourcename = sourcename.replaceAll("\"", "&quot;");
/* 4062 */                                       String resourcetype1 = (String)recentAlarm.get(7);
/*      */                                       
/* 4064 */                                       String url = null;
/*      */                                       
/* 4066 */                                       if (resourcetype1.equalsIgnoreCase("HAI"))
/*      */                                       {
/* 4068 */                                         url = "/showapplication.do?haid=" + category + "&method=showApplication";
/*      */ 
/*      */ 
/*      */                                       }
/* 4072 */                                       else if ((extDeviceMap != null) && (extDeviceMap.get(category) != null))
/*      */                                       {
/* 4074 */                                         url = (String)extDeviceMap.get(category);
/*      */                                       }
/* 4076 */                                       else if (sourcetype.contains("Site24x7"))
/*      */                                       {
/* 4078 */                                         url = "/extDeviceAction.do?method=site24x7Reports&resourceid=" + category;
/*      */                                       }
/* 4080 */                                       else if (com.adventnet.appmanager.util.ChildMOHandler.isChildMonitorTypeSupportedForMG(sourcetype))
/*      */                                       {
/* 4082 */                                         url = "/showapplication.do?resId=" + category + "&method=showChildApplicationDetail";
/*      */                                       } else {
/* 4084 */                                         url = "/showresource.do?resourceid=" + category + "&type=" + resourcetype1 + "&moname=" + sourcename + "&method=showdetails&resourcename=" + (String)recentAlarm.get(4) + "";
/*      */                                       }
/*      */                                       
/* 4087 */                                       if (j % 2 == 0)
/*      */                                       {
/* 4089 */                                         wsSeverity = "class=\"yellowgrayborder\"";
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4093 */                                         wsSeverity = "class=\"whitegrayborder\"";
/*      */                                       }
/*      */                                       
/*      */ 
/* 4097 */                                       out.write("\n\t\t\t<!--\n\t\t\tif ((Integer.parseInt((String)recentAlarm.get(1))==1))\n\t\t\t{\n\t\t\t\twsSeverity=\"class=\\\"errorgrayborder\\\"\";\n\t\t\t}\n\t\t-->\n\t\t\t<TR class=\"mgsummary-links\" onmouseover=\"this.className='mgsummaryHeaderHover'\"  onmouseout=\"this.className='mgsummaryHeader'\">\n\n\t\t\t<TD ");
/* 4098 */                                       out.print(wsSeverity);
/* 4099 */                                       out.write(" >\n\t\t\t");
/*      */                                       
/* 4101 */                                       if ((annotation != null) && (annotation.equalsIgnoreCase("YES")))
/*      */                                       {
/* 4103 */                                         out.write("\n\t\t\t\t<a href=\"javascript:MM_openBrWindow('/fault/AlarmDetails.do?method=viewAnnotationAndHistory&displayname=Annotations&popup=true&entity=");
/* 4104 */                                         out.print(recentAlarm.get(2));
/* 4105 */                                         out.write("','','resizable=yes,width=650,height=200')\" title=\"");
/* 4106 */                                         out.print(FormatUtil.getString("am.webclient.alerttab.recent5alarm.viewannotation.text"));
/* 4107 */                                         out.write("\"><img src=\"/images/icon_alert_annotated.gif\" style=\"position:relative; right:1px; top:3px;\"border=\"0\"></a>\n\t\t\t\t");
/*      */                                       } else {
/* 4109 */                                         out.write("\n\t\t\t\t\t<a href=\"javascript:openWindow('/fault/AlarmDetails.do?method=doAnnotate&entity=");
/* 4110 */                                         out.print(recentAlarm.get(2));
/* 4111 */                                         out.write("&userName=");
/* 4112 */                                         out.print(request.getRemoteUser());
/* 4113 */                                         out.write("&source=");
/* 4114 */                                         out.print(recentAlarm.get(6));
/* 4115 */                                         out.write("&category=");
/* 4116 */                                         out.print(recentAlarm.get(0));
/* 4117 */                                         out.write("&displayname=");
/* 4118 */                                         out.print(recentAlarm.get(4));
/* 4119 */                                         out.write("&redirectto=");
/* 4120 */                                         out.print(URLEncoder.encode(encodeurl));
/* 4121 */                                         out.write("&fromIcon=true','annotate','450','188')\" title=\"");
/* 4122 */                                         out.print(FormatUtil.getString("am.webclient.alerttab.recent5alarm.addannotation.text"));
/* 4123 */                                         out.write("\"><img src=\"/images/icon_alert_add_annotation.gif\" border=\"0\" ></a>\n\t\t\t\t\t");
/*      */                                       }
/* 4125 */                                       out.write("\n\t\t\t\t\t");
/*      */                                       
/* 4127 */                                       String extDispName = com.adventnet.appmanager.util.ExtProdUtil.decodeString((String)recentAlarm.get(4));
/* 4128 */                                       extDispName = extDispName.replaceAll("\"", "&quot;");
/* 4129 */                                       extDispName = extDispName.replaceAll("<", "&lt;");
/* 4130 */                                       extDispName = extDispName.replaceAll(">", "&gt;");
/* 4131 */                                       String trimmedExtDispName = getTrimmedText(extDispName, 40);
/* 4132 */                                       if (((extDeviceMap != null) && (extDeviceMap.get(category) != null)) || (sourcetype.contains("Site24x7")))
/*      */                                       {
/*      */ 
/*      */ 
/* 4136 */                                         out.write("\n\t\t\t\t\t\t<a class=\"mgsummary-links\" title=");
/* 4137 */                                         out.print(extDispName);
/* 4138 */                                         out.write(" href=\"javascript:MM_openBrWindow('");
/* 4139 */                                         out.print(url);
/* 4140 */                                         out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" > ");
/* 4141 */                                         out.print(trimmedExtDispName);
/* 4142 */                                         out.write("</a></TD>\n\t\t\t\t\t");
/*      */                                       }
/* 4144 */                                       else if (com.adventnet.appmanager.util.ChildMOHandler.isChildMonitorTypeSupportedForMG(sourcetype)) {
/* 4145 */                                         String toolTipTitle = extDispName;
/* 4146 */                                         if ((trimmedInfo != null) && (trimmedInfo.containsKey(category)))
/*      */                                         {
/* 4148 */                                           trimmedExtDispName = com.adventnet.appmanager.util.ExtProdUtil.decodeString((String)trimmedInfo.get(category));
/*      */                                         }
/*      */                                         
/* 4151 */                                         out.write("\n\t\t\t\t\t\t<a class=\"mgsummary-links\" title=\"");
/* 4152 */                                         out.print(toolTipTitle);
/* 4153 */                                         out.write("\"  href=\"javascript:MM_openBrWindow('");
/* 4154 */                                         out.print(url);
/* 4155 */                                         out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" > ");
/* 4156 */                                         out.print(trimmedExtDispName);
/* 4157 */                                         out.write("</a></TD>\n\t\t\t\t\t");
/*      */                                       } else {
/* 4159 */                                         out.write("\n\t\t\t\t\t   <a class=\"mgsummary-links\" title=");
/* 4160 */                                         out.print(extDispName);
/* 4161 */                                         out.write("  href=\"");
/* 4162 */                                         out.print(url);
/* 4163 */                                         out.write("\" > ");
/* 4164 */                                         out.print(trimmedExtDispName);
/* 4165 */                                         out.write("</a></TD>\n\t\t\t\t\t");
/*      */                                       }
/* 4167 */                                       out.write("\n\t\t\t\t\t\t");
/*      */                                       
/*      */ 
/* 4170 */                                       pageContext.setAttribute("type", sourcetype);
/* 4171 */                                       String ResType = (String)recentAlarm.get(9);
/* 4172 */                                       if (ResType.equals("Monitor Group"))
/*      */                                       {
/*      */ 
/* 4175 */                                         ResType = getGroupType(category);
/*      */                                       }
/*      */                                       
/*      */ 
/* 4179 */                                       out.write("\n\t\t\t\t\t\t<TD ");
/* 4180 */                                       out.print(wsSeverity);
/* 4181 */                                       out.write(" width=\"10%\"> <span class=\"footer\">");
/* 4182 */                                       out.print(FormatUtil.getString(ResType));
/* 4183 */                                       out.write("</span></TD>\n\t\t\t\t\t\t<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\n\n\t\t\t\t\t\t<TD ");
/* 4184 */                                       out.print(wsSeverity);
/* 4185 */                                       out.write(" align=\"left\" width=\"5%\"><a  href=\"javascript:void(0)\" onmouseover=\"ddrivetip(this,event,'");
/* 4186 */                                       out.print(mmessage);
/* 4187 */                                       out.write("<br>'+v+'");
/* 4188 */                                       out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 4189 */                                       out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4190 */                                       out.print(recentAlarm.get(6));
/* 4191 */                                       out.write("&attributeid=");
/* 4192 */                                       out.print(recentAlarm.get(0));
/* 4193 */                                       out.write("')\"> ");
/* 4194 */                                       out.print(getSeverityImageForHealth((String)recentAlarm.get(1)));
/* 4195 */                                       out.write(" </a>");
/* 4196 */                                       out.write("\n\n\n\t\t\t\t\t\t</TD>\n\n                       ");
/*      */                                       
/* 4198 */                                       if ((extDeviceMap != null) && (extDeviceMap.get(category) != null))
/*      */                                       {
/*      */ 
/* 4201 */                                         out.write("\n\t\t\t\t\t\t<TD ");
/* 4202 */                                         out.print(wsSeverity);
/* 4203 */                                         out.write(" > <a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&monitortype=");
/* 4204 */                                         out.print(resourcetype1);
/* 4205 */                                         out.write("&extDevice=true&entity=");
/* 4206 */                                         out.print(recentAlarm.get(2));
/* 4207 */                                         out.write("&source=");
/* 4208 */                                         out.print(recentAlarm.get(4));
/* 4209 */                                         out.write("&category=");
/* 4210 */                                         out.print(recentAlarm.get(0));
/* 4211 */                                         out.write("&redirectto=");
/* 4212 */                                         out.print(encodeurl);
/* 4213 */                                         out.write("\" class=\"mgsummary-links\"  title=\"");
/* 4214 */                                         out.print(removeBr(mmessage));
/* 4215 */                                         out.write(34);
/* 4216 */                                         out.write(32);
/* 4217 */                                         out.write(62);
/* 4218 */                                         out.print(getTruncatedAlertMessage(mmessage));
/* 4219 */                                         out.write(" </a></TD>\n\t\t\t\t\t   ");
/*      */                                       } else {
/* 4221 */                                         out.write("\n\t\t\t\t\t   <TD ");
/* 4222 */                                         out.print(wsSeverity);
/* 4223 */                                         out.write(" > <a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&monitortype=");
/* 4224 */                                         out.print(resourcetype1);
/* 4225 */                                         out.write("&entity=");
/* 4226 */                                         out.print(recentAlarm.get(2));
/* 4227 */                                         out.write("&source=");
/* 4228 */                                         out.print(recentAlarm.get(4));
/* 4229 */                                         out.write("&category=");
/* 4230 */                                         out.print(recentAlarm.get(0));
/* 4231 */                                         out.write("&redirectto=");
/* 4232 */                                         out.print(encodeurl);
/* 4233 */                                         out.write("\" class=\"mgsummary-links\"  title=\"");
/* 4234 */                                         out.print(removeBr((String)recentAlarm.get(3)));
/* 4235 */                                         out.write(34);
/* 4236 */                                         out.write(32);
/* 4237 */                                         out.write(62);
/* 4238 */                                         out.print(getTruncatedAlertMessage(mmessage));
/* 4239 */                                         out.write(" </a\n\t\t\t\t\t   ></TD>\n\t\t\t\t\t   ");
/*      */                                       }
/* 4241 */                                       out.write("\n\t\t\t\t\t\t<TD ");
/* 4242 */                                       out.print(wsSeverity);
/* 4243 */                                       out.write(" align=\"left\" >");
/* 4244 */                                       out.print(formatDT((String)recentAlarm.get(5)));
/* 4245 */                                       out.write("\n\n\t\t\t\t\t\t</TD>\n\t\t\t\t\t\t");
/*      */                                       
/* 4247 */                                       if ((recentAlarm.get(11) == null) || (((String)recentAlarm.get(11)).equalsIgnoreCase("null")))
/*      */                                       {
/*      */ 
/* 4250 */                                         out.write("\n\t\t\t\t\t\t\t\t<td ");
/* 4251 */                                         out.print(wsSeverity);
/* 4252 */                                         out.write(" title=\"");
/* 4253 */                                         out.print(FormatUtil.getString("am.webclient.alerttab.recent5alarm.pickupalert.text"));
/* 4254 */                                         out.write("\"><a href=\"/fault/AlarmOperations.do?methodCall=pickUpAlarm&selectedEntity=");
/* 4255 */                                         out.print(recentAlarm.get(2));
/* 4256 */                                         out.write("&redirectto=");
/* 4257 */                                         out.print(encodeurl);
/* 4258 */                                         out.write("&displayName=");
/* 4259 */                                         out.print(recentAlarm.get(4));
/* 4260 */                                         out.write("\" class=\"mgsummary-links\"><img src=\"/images/icon_alert_unacknowleged.gif\" border=\"0\" style=\"position:relative; top:3px; right:2px;\"><span class=\"mgsummary-links\">");
/* 4261 */                                         out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4262 */                                         out.write("</span></a></td>\n\t\t\t\t\t\t\t\t");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4266 */                                         out.write("\n\t\t\t\t\t\t\t\t<td ");
/* 4267 */                                         out.print(wsSeverity);
/* 4268 */                                         out.write(" title='");
/* 4269 */                                         out.print(FormatUtil.getString("am.webclient.alerttab.recent5alarm.pickupalertacknowledgement.text", new String[] { (String)recentAlarm.get(11), (String)recentAlarm.get(11) }));
/* 4270 */                                         out.write("'><a href=\"/fault/AlarmOperations.do?methodCall=unPickAlarm&selectedEntity=");
/* 4271 */                                         out.print(recentAlarm.get(2));
/* 4272 */                                         out.write("&redirectto=");
/* 4273 */                                         out.print(encodeurl);
/* 4274 */                                         out.write("&displayName=");
/* 4275 */                                         out.print(recentAlarm.get(4));
/* 4276 */                                         out.write("\" class=\"arial10\" ><img src=\"/images/icon_alert_acknowleged.gif\" border=\"0\"><span class=\"mgsummary-links\">");
/* 4277 */                                         out.print(recentAlarm.get(11));
/* 4278 */                                         out.write("</span></a></td>\n\t\t\t\t\t\t\t\t");
/*      */                                       }
/* 4280 */                                       out.write("\n\t\t\t\t\t\t\t\t</TR>\n\t\t\t\t\t\t\t\t");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4284 */                                     out.write(10);
/* 4285 */                                     out.write(32);
/* 4286 */                                     out.write(32);
/* 4287 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 4288 */                                     recentAlarm = (ArrayList)_jspx_page_context.findAttribute("recentAlarm");
/* 4289 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 4292 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4293 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 4296 */                                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 4297 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                 }
/*      */                                 
/* 4300 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 4301 */                                 out.write("\n\n\n\n</table>\n\n");
/* 4302 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 4303 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4307 */                             if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 4308 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                             }
/*      */                             
/* 4311 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 4312 */                             out.write(10);
/*      */                             
/* 4314 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4315 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 4316 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 4318 */                             _jspx_th_logic_005fempty_005f0.setName("recent5Alarms");
/* 4319 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 4320 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 4322 */                                 out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\"  class=\"lrtbdarkborder\">\n<tr>\n ");
/*      */                                 
/* 4324 */                                 ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4325 */                                 _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 4326 */                                 _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_logic_005fempty_005f0);
/* 4327 */                                 int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 4328 */                                 if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                   for (;;)
/*      */                                   {
/* 4331 */                                     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4332 */                                     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 4333 */                                     _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                     
/* 4335 */                                     _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showApplications'}");
/* 4336 */                                     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 4337 */                                     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                       for (;;) {
/* 4339 */                                         out.write("\n<td height=\"26\" class=\"dragndroptblheading\" title=\"");
/* 4340 */                                         out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 4341 */                                         out.write(34);
/* 4342 */                                         out.write(62);
/* 4343 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.title"));
/* 4344 */                                         out.write("</td>\n");
/* 4345 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 4346 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4350 */                                     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                     }
/*      */                                     
/* 4354 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 4355 */                                     out.write(10);
/*      */                                     
/* 4357 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4358 */                                     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 4359 */                                     _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 4360 */                                     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 4361 */                                     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                       for (;;) {
/* 4363 */                                         out.write("\n<td height=\"26\" class=\"tableheadingbborder\">");
/* 4364 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.title"));
/* 4365 */                                         out.write("</td>\n");
/* 4366 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 4367 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4371 */                                     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 4372 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                     }
/*      */                                     
/* 4375 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 4376 */                                     out.write(10);
/* 4377 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 4378 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4382 */                                 if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 4383 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                 }
/*      */                                 
/* 4386 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 4387 */                                 out.write("\n\n</tr>\n\n\t\t  <tr>\n\t\t  <td  height=\"40\" class=\"tdindent\">\n\t\t  <span class=\"bodytext\">\n\t\t  ");
/* 4388 */                                 out.print(FormatUtil.getString("am.webclient.alerttab.norecentalarmmessage.text"));
/* 4389 */                                 out.write("\n\t\t  </span></td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t  <td class=\"tablebottom\">&nbsp;\n\n\t\t  </td>\n\t\t  </table>\n");
/* 4390 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 4391 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4395 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 4396 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 4399 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 4400 */                             out.write("\n</div>\n");
/* 4401 */                             out.write(10);
/* 4402 */                             out.write(10);
/* 4403 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4404 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4407 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4408 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4411 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4412 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 4415 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4416 */                         out.write(10);
/* 4417 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 4419 */                         out.write(10);
/* 4420 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4421 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 4425 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4426 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 4429 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4430 */                       out.write("\n</body>\n</html>\n");
/*      */                     }
/* 4432 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4433 */         out = _jspx_out;
/* 4434 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4435 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4436 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4439 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4445 */     PageContext pageContext = _jspx_page_context;
/* 4446 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4448 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4449 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4450 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4452 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4454 */     _jspx_th_tiles_005fput_005f0.setValue("Snapshot View of Application");
/* 4455 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4456 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4457 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4458 */       return true;
/*      */     }
/* 4460 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4466 */     PageContext pageContext = _jspx_page_context;
/* 4467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4469 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4470 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4471 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4473 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4475 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 4476 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4477 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4478 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4479 */       return true;
/*      */     }
/* 4481 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4482 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4487 */     PageContext pageContext = _jspx_page_context;
/* 4488 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4490 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4491 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4492 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4494 */     _jspx_th_c_005fif_005f1.setTest("${param.method!='showApplication' && param.method!='showSnapshot' && param.method!='getHAProfiles' && param.method!='associateMonitors' && param.method!='getMonitorForm'}");
/* 4495 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4496 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4498 */         out.write(" \n\t  \t<br>\n\t  ");
/* 4499 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4500 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4504 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4505 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4506 */       return true;
/*      */     }
/* 4508 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4514 */     PageContext pageContext = _jspx_page_context;
/* 4515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4517 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4518 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 4519 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4521 */     _jspx_th_c_005fif_005f2.setTest("${param.method!='showApplication'}");
/* 4522 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 4523 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 4525 */         out.write("  \n           <a href=\"/showapplication.do?haid=");
/* 4526 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4527 */           return true;
/* 4528 */         out.write("&method=showApplication\" \n            class=\"new-left-links\"> ");
/* 4529 */         if (_jspx_meth_am_005fTruncate_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4530 */           return true;
/* 4531 */         out.write("</a>");
/* 4532 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 4533 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4537 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 4538 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4539 */       return true;
/*      */     }
/* 4541 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4547 */     PageContext pageContext = _jspx_page_context;
/* 4548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4550 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4551 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4552 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4554 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 4555 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4556 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4557 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4558 */       return true;
/*      */     }
/* 4560 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4566 */     PageContext pageContext = _jspx_page_context;
/* 4567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4569 */     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 4570 */     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 4571 */     _jspx_th_am_005fTruncate_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4573 */     _jspx_th_am_005fTruncate_005f0.setLength(25);
/* 4574 */     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 4575 */     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 4576 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 4577 */         out = _jspx_page_context.pushBody();
/* 4578 */         _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 4579 */         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4582 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_am_005fTruncate_005f0, _jspx_page_context))
/* 4583 */           return true;
/* 4584 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 4585 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4588 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 4589 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4592 */     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 4593 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 4594 */       return true;
/*      */     }
/* 4596 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 4597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_am_005fTruncate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4602 */     PageContext pageContext = _jspx_page_context;
/* 4603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4605 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4606 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4607 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_am_005fTruncate_005f0);
/*      */     
/* 4609 */     _jspx_th_c_005fout_005f1.setValue("${applicationScope.applications[param.haid]}");
/* 4610 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4611 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4612 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4613 */       return true;
/*      */     }
/* 4615 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4621 */     PageContext pageContext = _jspx_page_context;
/* 4622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4624 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4625 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4626 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4628 */     _jspx_th_c_005fif_005f3.setTest("${param.method=='showApplication'}");
/* 4629 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4630 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 4632 */         out.write(" \n          ");
/* 4633 */         if (_jspx_meth_am_005fTruncate_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 4634 */           return true;
/* 4635 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4636 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4640 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4641 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4642 */       return true;
/*      */     }
/* 4644 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4650 */     PageContext pageContext = _jspx_page_context;
/* 4651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4653 */     Truncate _jspx_th_am_005fTruncate_005f1 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 4654 */     _jspx_th_am_005fTruncate_005f1.setPageContext(_jspx_page_context);
/* 4655 */     _jspx_th_am_005fTruncate_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4657 */     _jspx_th_am_005fTruncate_005f1.setLength(25);
/* 4658 */     int _jspx_eval_am_005fTruncate_005f1 = _jspx_th_am_005fTruncate_005f1.doStartTag();
/* 4659 */     if (_jspx_eval_am_005fTruncate_005f1 != 0) {
/* 4660 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 4661 */         out = _jspx_page_context.pushBody();
/* 4662 */         _jspx_th_am_005fTruncate_005f1.setBodyContent((BodyContent)out);
/* 4663 */         _jspx_th_am_005fTruncate_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4666 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_am_005fTruncate_005f1, _jspx_page_context))
/* 4667 */           return true;
/* 4668 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f1.doAfterBody();
/* 4669 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4672 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 4673 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4676 */     if (_jspx_th_am_005fTruncate_005f1.doEndTag() == 5) {
/* 4677 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 4678 */       return true;
/*      */     }
/* 4680 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 4681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_am_005fTruncate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4686 */     PageContext pageContext = _jspx_page_context;
/* 4687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4689 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4690 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4691 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_am_005fTruncate_005f1);
/*      */     
/* 4693 */     _jspx_th_c_005fout_005f2.setValue("${applicationScope.applications[param.haid]}");
/* 4694 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4695 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4696 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4697 */       return true;
/*      */     }
/* 4699 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4705 */     PageContext pageContext = _jspx_page_context;
/* 4706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4708 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4709 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4710 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 4712 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 4713 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4714 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4715 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4716 */       return true;
/*      */     }
/* 4718 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4719 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4724 */     PageContext pageContext = _jspx_page_context;
/* 4725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4727 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4728 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4729 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 4731 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 4732 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4733 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4734 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4735 */       return true;
/*      */     }
/* 4737 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4743 */     PageContext pageContext = _jspx_page_context;
/* 4744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4746 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4747 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4748 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4750 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 4751 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4752 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4753 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4754 */       return true;
/*      */     }
/* 4756 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4762 */     PageContext pageContext = _jspx_page_context;
/* 4763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4765 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4766 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4767 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4769 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 4770 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4771 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4772 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4773 */       return true;
/*      */     }
/* 4775 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4781 */     PageContext pageContext = _jspx_page_context;
/* 4782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4784 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4785 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4786 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4788 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 4789 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4790 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4791 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4792 */       return true;
/*      */     }
/* 4794 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4800 */     PageContext pageContext = _jspx_page_context;
/* 4801 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4803 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4804 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4805 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 4807 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 4808 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4809 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4810 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4811 */       return true;
/*      */     }
/* 4813 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4819 */     PageContext pageContext = _jspx_page_context;
/* 4820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4822 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4823 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4824 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4826 */     _jspx_th_c_005fout_005f9.setValue("${param.haid}");
/* 4827 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4828 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4829 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4830 */       return true;
/*      */     }
/* 4832 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4838 */     PageContext pageContext = _jspx_page_context;
/* 4839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4841 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4842 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4843 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4845 */     _jspx_th_c_005fout_005f10.setValue("${selectedskin}");
/*      */     
/* 4847 */     _jspx_th_c_005fout_005f10.setDefault("${initParam.defaultSkin}");
/* 4848 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4849 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4850 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4851 */       return true;
/*      */     }
/* 4853 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4854 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4859 */     PageContext pageContext = _jspx_page_context;
/* 4860 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4862 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4863 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4864 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4866 */     _jspx_th_c_005fout_005f11.setValue("${applicationScope.applications[param.haid]}");
/* 4867 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4868 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4869 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4870 */       return true;
/*      */     }
/* 4872 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4878 */     PageContext pageContext = _jspx_page_context;
/* 4879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4881 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4882 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4883 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4885 */     _jspx_th_c_005fout_005f12.setValue("${applicationScope.applications[param.haid]}");
/* 4886 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4887 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4888 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4889 */       return true;
/*      */     }
/* 4891 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4897 */     PageContext pageContext = _jspx_page_context;
/* 4898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4900 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fnobody.get(FormatDateTag.class);
/* 4901 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 4902 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4904 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${lastmodified}");
/* 4905 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 4906 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 4907 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 4908 */       return true;
/*      */     }
/* 4910 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 4911 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4916 */     PageContext pageContext = _jspx_page_context;
/* 4917 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4919 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4920 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4921 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4923 */     _jspx_th_c_005fif_005f13.setTest("${param.method =='showApplications'}");
/* 4924 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4925 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 4927 */         out.write(10);
/* 4928 */         if (_jspx_meth_logic_005fpresent_005f6(_jspx_th_c_005fif_005f13, _jspx_page_context))
/* 4929 */           return true;
/* 4930 */         out.write(10);
/* 4931 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4932 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4936 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4937 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4938 */       return true;
/*      */     }
/* 4940 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4941 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4946 */     PageContext pageContext = _jspx_page_context;
/* 4947 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4949 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4950 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 4951 */     _jspx_th_logic_005fpresent_005f6.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 4953 */     _jspx_th_logic_005fpresent_005f6.setRole("OPERATOR");
/* 4954 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 4955 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 4957 */         out.write("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>");
/* 4958 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 4959 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4963 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 4964 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4965 */       return true;
/*      */     }
/* 4967 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4973 */     PageContext pageContext = _jspx_page_context;
/* 4974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4976 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4977 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4978 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4980 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 4982 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 4983 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4984 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4985 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4986 */       return true;
/*      */     }
/* 4988 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4989 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SnapshotView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */