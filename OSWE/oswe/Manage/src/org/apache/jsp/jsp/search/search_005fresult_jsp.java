/*      */ package org.apache.jsp.jsp.search;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.utilities.search.renderer.RowEntry;
/*      */ import com.adventnet.utilities.search.renderer.TableStructure;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
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
/*      */ import javax.servlet.ServletConfig;
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
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class search_005fresult_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  352 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
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
/*  674 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
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
/*  820 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  821 */     getRCATrimmedText(div1, rca);
/*  822 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  825 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  826 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  846 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/*  861 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  862 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  865 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  866 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  867 */       set = AMConnectionPool.executeQueryStmt(query);
/*  868 */       if (set.next())
/*      */       {
/*  870 */         String helpLink = set.getString("LINK");
/*  871 */         DBUtil.searchLinks.put(key, helpLink);
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
/*  905 */     Properties temp = FaultUtil.getStatus(entitylist, false);
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
/*  919 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*  932 */     AMLog.debug("JSP : " + debugMessage);
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
/*  979 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1191 */       childresname = ExtProdUtil.decodeString(childresname);
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
/* 1295 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
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
/*      */           catch (SQLException e) {
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
/* 1830 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1832 */               if (maxCol != null)
/* 1833 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1835 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1830 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 1993 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
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
/* 2048 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
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
/*      */   private String getTableContent(String title, String desc)
/*      */     throws Exception
/*      */   {
/* 2188 */     StringBuffer sb = new StringBuffer();
/* 2189 */     sb.append("<table width=99% border=0  cellpadding=0 cellspacing=0 class=lrtbdarkborder>");
/* 2190 */     sb.append("<tr>");
/* 2191 */     sb.append("<td width=72% height=26 class=\"tableheading\">");
/* 2192 */     sb.append(FormatUtil.getString(title));
/*      */     
/*      */ 
/* 2195 */     sb.append("</td>");
/* 2196 */     sb.append("</tr>");
/* 2197 */     sb.append("</table>");
/* 2198 */     return sb.toString();
/*      */   }
/*      */   
/* 2201 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2207 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2208 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2221 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2225 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2231 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2235 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2237 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
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
/* 2261 */       ServletConfig config = pageContext.getServletConfig();
/* 2262 */       session = pageContext.getSession();
/* 2263 */       out = pageContext.getOut();
/* 2264 */       _jspx_out = out;
/*      */       
/* 2266 */       out.write("\n\n\n\n\n\n\n  \n\n \n\n\n\n\n\n\n\n");
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
/* 2423 */                     out.write("\n\n\n\n\n<script> \n\nfunction searchsubmit()\n{\nvar searchtext=document.frm_serch_web.query.value;\ndocument.frm_serch_web.action=\"/Search.do?query=\"+searchtext+\"&searchSubmit=Search\";\ndocument.frm_serch_web.target=\"_self\";\ndocument.frm_serch_web.submit();\n}\n\n</script>\n\n");
/*      */                     
/*      */ 
/* 2426 */                     boolean noSearchQry = false;
/* 2427 */                     String searchQuery1 = request.getParameter("query");
/* 2428 */                     if (searchQuery1 == null) {
/* 2429 */                       searchQuery1 = "";
/*      */                     }
/* 2431 */                     if (searchQuery1.trim().length() == 0) {
/* 2432 */                       noSearchQry = true;
/*      */                     }
/* 2434 */                     String search_results = FormatUtil.getString("webclient.topo.searchresult.header");
/* 2435 */                     HashMap extDeviceMap = null;
/* 2436 */                     if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*      */                     {
/* 2438 */                       extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink();
/* 2439 */                       if (OEMUtil.isRemove("enable.proxyForExtProd"))
/*      */                       {
/* 2441 */                         extDeviceMap = ExtProdUtil.appendServiceDetailsInMapLinks("OpManager", extDeviceMap);
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 2446 */                     out.write(10);
/* 2447 */                     out.write(10);
/* 2448 */                     out.write(10);
/*      */                     
/* 2450 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2451 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2452 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2454 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 2455 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2456 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2458 */                         out.write("\n        ");
/*      */                         
/* 2460 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2461 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2462 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2464 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2466 */                         _jspx_th_tiles_005fput_005f0.setValue(search_results);
/* 2467 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2468 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2469 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2472 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2473 */                         out.write("\n        ");
/* 2474 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2476 */                         out.write("\n        ");
/*      */                         
/* 2478 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2479 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2480 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2482 */                         _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                         
/* 2484 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2485 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2486 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2487 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2488 */                             out = _jspx_page_context.pushBody();
/* 2489 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2490 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2493 */                             out.write("\n\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2494 */                             out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2495 */                             out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 2496 */                             if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 2498 */                             out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n\n  </tr>\n          <tr> \n            <td colspan=\"2\" class=\"quicknote\">");
/* 2499 */                             out.print(FormatUtil.getString("am.webclient.search.quicknote.text"));
/* 2500 */                             out.write(" \n            </td>\n          </tr>\n        </table>\n\n\n        ");
/* 2501 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2502 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2505 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2506 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2509 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2510 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 2513 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2514 */                         out.write("\n\n        ");
/*      */                         
/* 2516 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2517 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2518 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2520 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 2522 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2523 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2524 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2525 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2526 */                             out = _jspx_page_context.pushBody();
/* 2527 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2528 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2531 */                             out.write(" \n\n\n\n\n");
/*      */                             
/* 2533 */                             List tablesToRender = (List)request.getAttribute("tablestorender");
/* 2534 */                             boolean resultsPresent = "true".equals(request.getAttribute("resultspresent"));
/* 2535 */                             TableStructure ts = null;
/* 2536 */                             int size = tablesToRender.size();
/*      */                             
/* 2538 */                             if (resultsPresent)
/*      */                             {
/* 2540 */                               for (int i = 0; i < size; i++) {
/* 2541 */                                 ts = (TableStructure)tablesToRender.get(i);
/* 2542 */                                 if (ts.getRowEntry().size() != 0)
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/* 2547 */                                   out.write("\n                \n                ");
/* 2548 */                                   if (!com.adventnet.appmanager.util.Constants.isIt360) out.println(getTableContent(ts.getTitle(), ts.getDescription()));
/* 2549 */                                   out.write("\n                <table width=\"99%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n                <tr>\n                ");
/*      */                                   
/* 2551 */                                   int size2 = ts.getColumnNames().size();
/* 2552 */                                   for (int j = 0; j < size2; j++)
/*      */                                   {
/* 2554 */                                     out.println("<td width=25% height=28 class=columnheadingnotop> " + FormatUtil.getString((String)ts.getColumnNames().get(j)) + "</td>");
/*      */                                   }
/*      */                                   
/* 2557 */                                   if ((EnterpriseUtil.isAdminServer()) && (size2 == 3))
/*      */                                   {
/* 2559 */                                     out.println("<td width=25% height=28 class=columnheadingnotop> " + FormatUtil.getString("am.webclient.adminserver.title") + "</td>");
/*      */                                   }
/*      */                                   
/* 2562 */                                   out.write("\n                </tr>\n\n                ");
/*      */                                   
/* 2564 */                                   String classNm = "whitegrayborder";
/* 2565 */                                   size2 = ts.getRowEntry().size();
/* 2566 */                                   for (int j = 0; j < size2; j++) {
/* 2567 */                                     RowEntry re = (RowEntry)ts.getRowEntry().get(j);
/* 2568 */                                     int size3 = re.getColumnValues().size();
/* 2569 */                                     out.println("<tr>");
/* 2570 */                                     boolean link = false;
/* 2571 */                                     boolean onClick = false;
/* 2572 */                                     if (j % 2 == 0)
/*      */                                     {
/* 2574 */                                       classNm = "whitegrayborder";
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2578 */                                       classNm = "yellowgrayborder";
/*      */                                     }
/*      */                                     
/* 2581 */                                     String resourceId = null;
/* 2582 */                                     for (int k = 0; k < size3; k++)
/*      */                                     {
/*      */ 
/* 2585 */                                       out.println("<td class=\"" + classNm + "\" >");
/* 2586 */                                       link = re.getColumnValueLinks().get(k).toString().length() > 0;
/* 2587 */                                       onClick = re.getOnClickLinks().get(k).toString().length() > 0;
/* 2588 */                                       if (link)
/*      */                                       {
/*      */                                         try
/*      */                                         {
/* 2592 */                                           String subString = re.getColumnValueLinks().get(k).toString().substring(28);
/* 2593 */                                           int endIndex = subString.indexOf("&");
/* 2594 */                                           resourceId = subString.substring(0, endIndex);
/*      */                                         }
/*      */                                         catch (Exception e)
/*      */                                         {
/* 2598 */                                           e.printStackTrace();
/*      */                                         }
/* 2600 */                                         if ((resourceId != null) && (extDeviceMap != null) && (extDeviceMap.get(resourceId) != null))
/*      */                                         {
/* 2602 */                                           if ("true".equals(OEMUtil.getOEMString("enable.extProdLink.popup")))
/*      */                                           {
/* 2604 */                                             out.println("<a href=\"javascript:MM_openBrWindow('" + extDeviceMap.get(resourceId) + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"staticlinks\" >");
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2608 */                                             String url = (String)extDeviceMap.get(resourceId);
/* 2609 */                                             out.println("<a href=\"" + url + "\" class=\"staticlinks\">");
/*      */                                           }
/*      */                                           
/*      */                                         }
/*      */                                         else {
/* 2614 */                                           out.println("<a href=\"" + re.getColumnValueLinks().get(k) + "\" class=\"staticlinks\">");
/*      */                                         }
/*      */                                         
/* 2617 */                                         if ((EnterpriseUtil.isAdminServer()) && (size3 == 3))
/*      */                                         {
/* 2619 */                                           String subString = re.getColumnValueLinks().get(k).toString().substring(28);
/* 2620 */                                           int endIndex = subString.indexOf("&");
/* 2621 */                                           resourceId = subString.substring(0, endIndex);
/*      */                                         }
/*      */                                       }
/* 2624 */                                       else if (onClick) {
/* 2625 */                                         if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                         {
/* 2627 */                                           String apmHostPortInfo = "://" + com.adventnet.appmanager.util.Constants.getAppHostName() + ":" + com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getwebserverport();
/* 2628 */                                           String apm_url = "http" + apmHostPortInfo + "/RecentAlarms.do?method=getAlarmsForResource&resourceid=" + resourceId + "&d=" + new Date();
/* 2629 */                                           out.println("<a href=\"javascript:MM_openBrWindow('" + apm_url + "','AlarmSummary','width=600,height=450, scrollbars=yes')\" class=\"staticlinks\" >");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 2633 */                                           out.println("<a href='#' onClick=\"" + re.getOnClickLinks().get(k) + "\" class=\"staticlinks\">");
/*      */                                         }
/*      */                                       }
/* 2636 */                                       out.println(re.getColumnValues().get(k));
/* 2637 */                                       if ((link) || (onClick))
/*      */                                       {
/* 2639 */                                         out.println("</a>");
/*      */                                       }
/* 2641 */                                       out.println("</td>");
/*      */                                     }
/* 2643 */                                     if ((resourceId != null) && (EnterpriseUtil.isAdminServer()))
/*      */                                     {
/* 2645 */                                       out.println("<td class=\"" + classNm + "\" >");
/* 2646 */                                       out.println(CommDBUtil.getManagedServerNameWithPort(resourceId));
/* 2647 */                                       out.println("</td>");
/*      */                                     }
/* 2649 */                                     out.println("</tr>");
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/* 2654 */                                   out.write("\n\n\n                </table>\n                ");
/* 2655 */                                   if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 2656 */                                     out.write("<br><br>");
/*      */                                   }
/* 2658 */                                   out.write(10);
/* 2659 */                                   out.write(10);
/*      */ 
/*      */                                 }
/*      */                                 
/*      */ 
/*      */                               }
/*      */                               
/*      */                             }
/* 2667 */                             else if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                             {
/* 2669 */                               out.write("\n \t\t<div>\n \t        \t<table>\n \t                \t<tr>\n \t                \t\t<td>\n \t                \t\t\t<font size=\"2\" face=\"Arial,Helvetica,sans-serif\">\n \t                         \t\t\tSorry your search returned no results.\n\t\t \t                        </font>\n\t\t \t                </td>\n\t\t \t        </tr>\n \t        \t</table>\n \t \t</div>\n \t");
/*      */                             }
/*      */                             
/* 2672 */                             out.write("\n\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/* 2673 */                             if ((!resultsPresent) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                             {
/* 2675 */                               String msgRes = "";
/* 2676 */                               if (noSearchQry) {
/* 2677 */                                 msgRes = FormatUtil.getString("am.webclient.search.boxempty.text");
/*      */                               }
/*      */                               else {
/* 2680 */                                 msgRes = FormatUtil.getString("am.webclient.search.noresults.text", new String[] { searchQuery1 });
/*      */                               }
/*      */                               
/*      */ 
/* 2684 */                               out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" class=\"messagebox\" >\n   <tr>\n    <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\"> <img src=\"/images/icon_message_failure.gif\" ></td>\n    <td><span class=\"bodytext\"> ");
/* 2685 */                               out.print(msgRes);
/* 2686 */                               out.write(" </span> </td>\n   </tr>\n  </table>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 2690 */                             out.write("\n        \n");
/*      */                             
/* 2692 */                             String search = FormatUtil.getString("webclient.common.searchcomponent.searchbutton.text");
/* 2693 */                             if (!OEMUtil.isOEM())
/*      */                             {
/*      */ 
/* 2696 */                               out.write("\n            <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"lrtbdarkborder\">\n                      <tr>             \n                          <td height=\"28\" class=\"message\">\n                          <form name=\"frm_serch_web\" method=GET >\n                          <input type=\"text\" size=\"50\" class=\"formtext\" name=\"query\" value=\"");
/* 2697 */                               out.print(searchQuery1);
/* 2698 */                               out.write("\">\n                          <input type=\"hidden\" name=\"domain\" value=\"all\">\n                          <input type=\"button\" class=\"buttons\" name=\"searchSubmit\" value=\"");
/* 2699 */                               out.print(search);
/* 2700 */                               out.write("\" onclick=\"javascript:searchsubmit();\">\n                          </form>\n                          </td>\n                     </tr>\n                     <tr>\n                          <td class=\"bodytextbold\"> ");
/* 2701 */                               out.print(FormatUtil.getString("webclient.common.continuesearchin.text", new String[] { searchQuery1 }));
/* 2702 */                               out.write("\t\t\t                                               \n                          </td>\n                     </tr>\n            </table>\n          ");
/*      */                             }
/* 2704 */                             out.write("\n          \n\n");
/* 2705 */                             out.write("\n\n<!-- Table for Search results Ends  here -->\n\n\n");
/* 2706 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2707 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2710 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2711 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2714 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2715 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 2718 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2719 */                         out.write(32);
/* 2720 */                         out.write(10);
/* 2721 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2723 */                         out.write(10);
/* 2724 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2725 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2729 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2730 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 2733 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2734 */                       out.write(10);
/* 2735 */                       out.write(10);
/*      */                     }
/* 2737 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2738 */         out = _jspx_out;
/* 2739 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2740 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2741 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2744 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2750 */     PageContext pageContext = _jspx_page_context;
/* 2751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2753 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2754 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2755 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2757 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2759 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 2760 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2761 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2762 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2763 */       return true;
/*      */     }
/* 2765 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2771 */     PageContext pageContext = _jspx_page_context;
/* 2772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2774 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2775 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2776 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 2778 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2780 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2781 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2782 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2783 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2784 */       return true;
/*      */     }
/* 2786 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2787 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2792 */     PageContext pageContext = _jspx_page_context;
/* 2793 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2795 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2796 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2797 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2799 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 2801 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 2802 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2803 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 2804 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 2805 */       return true;
/*      */     }
/* 2807 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 2808 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\search\search_005fresult_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */